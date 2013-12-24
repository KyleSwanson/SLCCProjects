package FlipBookPro;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

import javax.swing.JComponent;

public class Slide extends JComponent implements MouseMotionListener,
		MouseListener {
	public int _id;

	public boolean redraw = false;

	private ArrayList<DrawableInterface> _drawn_points;
	private ArrayList<DrawableInterface> _removed_points;
	private Graphics2D _graphics_pane;
	private BufferedImage _image;

	private BufferedImage _offscreen_image;
	private Graphics2D _offscreen_graphics_pane;

	// I don't like storing this here, and in the UI...
	private ToolInformation _tool_info;
	private int _next_tool_id = 0;

	// Should be pretty easy to make this settable. - Done. We'll
	// probably/possibly want a button for this.
	private Color _background_color = Color.BLACK;

	public Slide(int id) {
		_id = id;

		addMouseMotionListener(this);
		addMouseListener(this);

		_drawn_points = new ArrayList<DrawableInterface>();
		_removed_points = new ArrayList<DrawableInterface>();
	}

	public Slide newInstance(int id) {
		// Deep copy vs shallow copy blah blah blah blah...
		Slide out = new Slide(id);
		ArrayList<DrawableInterface> di = new ArrayList<DrawableInterface>();
		di.addAll(_drawn_points);
		out._drawn_points = di;
		out._image = BufferedImageDeepCopy(_image);
		out._background_color = _background_color;
		out._tool_info = _tool_info;
		out._removed_points = _removed_points;

		out._graphics_pane = out._image.createGraphics();

		return out;
	}

	public void clearSlide() {
		if (_graphics_pane != null) {
			_graphics_pane.setColor(_background_color);
			_graphics_pane.fillRect(0, 0, getWidth(), getHeight());
		}

		repaint();
	}

	public void purgeSlide() {
		if (_graphics_pane != null) {
			_graphics_pane.setColor(_background_color);
			_graphics_pane.fillRect(0, 0, getWidth(), getHeight());
		}

		_drawn_points.clear();

		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (_image == null) {
			_image = new BufferedImage(getWidth(), getHeight(),
					BufferedImage.TYPE_INT_ARGB);
			_graphics_pane = (Graphics2D) _image.getGraphics();
			_graphics_pane.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			clearSlide();
		}

		if (redraw) {
			redraw();
			redraw = false;
		}

		g.drawImage(_image, 0, 0, null);
	}

	private void draw(DrawableInterface p) {
		p.draw(_graphics_pane);

		repaint();
	}

	public void redraw() {
		clearSlide();
		for (DrawableInterface di : _drawn_points) {
			di.redraw(_graphics_pane);
		}

		repaint();
	}

	public void offScreenRedraw() {
		_offscreen_image = new BufferedImage(640, 480,
				BufferedImage.TYPE_INT_ARGB);
		_offscreen_graphics_pane = (Graphics2D) _offscreen_image.getGraphics();
		_offscreen_graphics_pane.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		_offscreen_graphics_pane.setColor(_background_color);
		_offscreen_graphics_pane.fillRect(0, 0, 640, 480);

		for (DrawableInterface di : _drawn_points) {
			di.redraw(_offscreen_graphics_pane);
		}

	}

	public BufferedImage getOffscreenImage() {
		if (_image != null)
			return _image;
		if (_offscreen_image == null)
			offScreenRedraw();

		return _offscreen_image;
	}

	public void undo() {
		if (_drawn_points.size() > 0)
			_drawn_points.remove(_drawn_points.size() - 1);

		redraw();
	}

	public BufferedImage getImage() {
		return _image;
	}

	public void setImage(BufferedImage bi) {
		_image = bi;

		repaint();
	}

	public void setToolInformation(ToolInformation ti) {
		_tool_info = ti;
	}

	public ToolInformation getToolInformation() {
		return _tool_info;
	}

	public void setBackgroundColor(Color bColor) {
		_background_color = bColor;
	}

	public ArrayList<DrawableInterface> getDrawnPoints() {
		return _drawn_points;
	}

	public void setDrawnPoints(ArrayList<DrawableInterface> dp) {
		_drawn_points = dp;
	}

	public boolean needsRedraw() {
		return redraw;
	}

	private Point current;
	private Point end;

	private PencilTool pencil;
	private LineTool line;
	private EraserTool eraser;
	private RectangleTool rectangle;

	// Logic for tools goes here.
	public void onMouseMoved(MouseEvent e, int state) {
		if (state == ToolInformation.MOUSE_DRAGGING)
			onMouseDragging(e, state);

		if (state == ToolInformation.MOUSE_RELEASE)
			onMouseRelease(e, state);

		if (state == ToolInformation.MOUSE_CLICKED)
			onMouseClicked(e, state);
	}

	/*
	 * So this way kind of sucks. So many if statements. I have this zen way
	 * that would require a lot less code, and would be far more extensible.
	 * 
	 * But this late in the game I don't want to redo it all. - Kyle
	 * 
	 * Pretty much it would use the BaseTool object, get the type which would be
	 * either a 1 point or 2 point. Then just use generic methods to draw with.
	 */
	public void onMouseDragging(MouseEvent e, int state) {
		if (_tool_info.getType() == ToolInformation.PENCIL) {
			if (pencil != null) {
				pencil.addPoint(e.getPoint());
			} else {
				pencil = new PencilTool(_next_tool_id,
						_tool_info.newInstance(), e.getPoint());
				_drawn_points.add(pencil);
				// System.out.println("Drawn Point Added: "
				// + _drawn_points.toString());
			}

			draw(pencil);
		}

		if (_tool_info.getType() == ToolInformation.ERASER) {
			if (eraser != null) {
				eraser.addPoint(e.getPoint());
			} else {
				eraser = new EraserTool(_next_tool_id,
						_tool_info.newInstance(), e.getPoint());
				_drawn_points.add(eraser);
			}

			draw(eraser);
		}

		if (_tool_info.getType() == ToolInformation.LINE) {
			if (line != null) {
				line.setEnd(e.getPoint());
				redraw();
				draw(line);
			} else {
				line = new LineTool(_next_tool_id, _tool_info.newInstance(),
						e.getPoint());
			}
		}

		if (_tool_info.getType() == ToolInformation.RECTANGLE) {
			if (rectangle != null) {
				rectangle.setEnd(e.getPoint());
				redraw();
				draw(rectangle);
			} else {
				rectangle = new RectangleTool(_next_tool_id,
						_tool_info.newInstance(), e.getPoint());
			}
		}

	}

	public void onMouseRelease(MouseEvent e, int state) {
		if (_tool_info.getType() == ToolInformation.PENCIL) {
			pencil = null;
		}

		if (_tool_info.getType() == ToolInformation.ERASER) {
			eraser = null;
		}

		if (_tool_info.getType() == ToolInformation.LINE) {
			line.setEnd(e.getPoint());
			draw(line);

			_drawn_points.add(line);

			line = null;
		}

		if (_tool_info.getType() == ToolInformation.RECTANGLE) {
			rectangle.setEnd(e.getPoint());

			draw(rectangle);

			_drawn_points.add(rectangle);

			rectangle = null;
		}
	}

	public void onMouseClicked(MouseEvent e, int state) {
		if (_tool_info.getType() == ToolInformation.PENCIL) {
			PencilTool t = new PencilTool(_next_tool_id,
					_tool_info.newInstance(), e.getPoint());

			_drawn_points.add(t);

			draw(t);
		}

		if (_tool_info.getType() == ToolInformation.ERASER) {
			EraserTool es = new EraserTool(_next_tool_id,
					_tool_info.newInstance(), e.getPoint());

			_drawn_points.add(es);
			draw(es);
		}
	}

	/*
	 * These are a bunch of silly methods that java needs. You can safely ignore
	 * everything below this...
	 * 
	 * Seriously, ignore it.
	 */

	@Override
	public void mouseDragged(MouseEvent me) {
		onMouseMoved(me, ToolInformation.MOUSE_DRAGGING);
	}

	@Override
	public void mouseMoved(MouseEvent me) {
		// Nothing here.
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		onMouseMoved(me, ToolInformation.MOUSE_CLICKED);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// Nothing here.
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// Nothing here.
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// Nothing here.
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		onMouseMoved(me, ToolInformation.MOUSE_RELEASE);
	}

	@Override
	public String toString() {
		return String.format("Slide: id = %d", _id);
	}

	static BufferedImage BufferedImageDeepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

}
