package FlipBookPro;

import java.awt.Graphics2D;

public interface DrawableInterface {
	
	public Graphics2D draw(Graphics2D graphics);
	public Graphics2D redraw(Graphics2D graphics);
	public int getId();
	
}
