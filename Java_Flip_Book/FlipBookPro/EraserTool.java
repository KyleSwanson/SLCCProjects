package FlipBookPro;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class EraserTool extends PencilTool implements DrawableInterface {

	public EraserTool(int id, ToolInformation tool_info, Point p) {
		super(id, tool_info, p);
		tool_info.setColor(Color.BLACK);
	}

	@Override
	public Graphics2D draw(Graphics2D graphics) {
		return super.draw(graphics);
	}

	@Override
	public Graphics2D redraw(Graphics2D graphics) {
		return super.redraw(graphics);
	}

	@Override
	public int getId() {
		return super.getId();
	}

}
