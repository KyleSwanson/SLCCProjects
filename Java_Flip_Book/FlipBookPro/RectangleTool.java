package FlipBookPro;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class RectangleTool extends BaseTool implements DrawableInterface {

	private Point _start;
	private Point _end;

	public RectangleTool(int id, ToolInformation tool_info, Point start) {
		super(id, tool_info);
		setStart(start);
	}

	public void setStart(Point s) {
		_start = s;
	}

	public void setEnd(Point en) {
		_end = en;
	}

	@Override
	public Graphics2D draw(Graphics2D g) {
		g.setColor(_tool_info.getColor());
		g.setStroke(_tool_info.getStroke());

		Rectangle2D et;

		if (_end == null) {
			int cX = (int) _start.getX() - _tool_info.getSize() / 2;
			int cY = (int) _start.getY() - _tool_info.getSize() / 2;
			et = new Rectangle2D.Double(cX, cY, _tool_info.getSize(),
					_tool_info.getSize());
		} else {
			et = new Rectangle2D.Double();
			et.setFrameFromDiagonal(_start, _end);
		}

		g.draw(et);

		if (_tool_info.getFill()) {
			g.fill(et);
		}

		return g;
	}

	@Override
	public Graphics2D redraw(Graphics2D g) {
		return draw(g);
	}

	@Override
	public int getId() {
		return super._id;
	}

}
