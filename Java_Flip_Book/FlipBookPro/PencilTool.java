package FlipBookPro;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

public class PencilTool extends BaseTool implements DrawableInterface {

	private List<Point> _points;

	public PencilTool(int id, ToolInformation tool_info, Point p) {
		super(id, tool_info);

		_points = new ArrayList<Point>();

		_points.add(p);
	}

	public void addPoint(Point p) {
		_points.add(p);
	}

	@Override
	public Graphics2D draw(Graphics2D g) {
		g.setColor(_tool_info.getColor());
		g.setStroke(_tool_info.getStroke());

		if (_points.size() - 1 >= 0 && _points.size() - 2 >= 0) {
			Point current = _points.get(_points.size() - 1);

			Point last = _points.get(_points.size() - 2);

			g.drawLine((int) current.getX(), (int) current.getY(),
					(int) last.getX(), (int) last.getY());
		} else if (_points.size() - 1 == 0) {
			Point c = _points.get(_points.size() - 1);

			g.drawLine((int) c.getX(), (int) c.getY(), (int) c.getX(),
					(int) c.getY());
		}

		return g;
	}

	@Override
	public Graphics2D redraw(Graphics2D g) {
		g.setColor(_tool_info.getColor());
		g.setStroke(_tool_info.getStroke());

		for (int x = 1; x < _points.size(); x++) {
			Point c = _points.get(x);
			Point p = _points.get(x - 1);

			g.drawLine((int) c.getX(), (int) c.getY(), (int) p.getX(),
					(int) p.getY());
		}
		
		if (_points.size() == 1) {
			Point c = _points.get(_points.size() - 1);

			g.drawLine((int) c.getX(), (int) c.getY(), (int) c.getX(),
					(int) c.getY());
		}

		return g;
		
	}

	@Override
	public int getId() {
		return _id;
	}

}
