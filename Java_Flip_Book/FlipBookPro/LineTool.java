

package FlipBookPro;

import java.awt.Graphics2D;
import java.awt.Point;

public class LineTool extends BaseTool implements DrawableInterface {

	private Point _start;
	private Point _end;

	public LineTool(int id, ToolInformation tool_info, Point start) {
		super(id, tool_info);
		setStart(start);
	}

	public void setStart(Point point) {
		_start = point;
	}

	public void setEnd(Point point) {
		_end = point;
	}

	@Override
	public Graphics2D draw(Graphics2D g) {
		g.setColor(_tool_info.getColor());
		g.setStroke(_tool_info.getStroke());
		
		g.drawLine((int) _start.getX(), (int) _start.getY(), (int) _end.getX(),
				(int) _end.getY());

		return g;
	}

	@Override
	public Graphics2D redraw(Graphics2D g) {
		return draw(g);
	}

	@Override
	public int getId() {
		return _id;
	}
}
