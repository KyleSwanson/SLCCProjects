package FlipBookPro;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Stroke;
import java.io.Serializable;

public class ToolInformation implements Serializable {
	// All of these static's have something in common...

	public final static int PENCIL = 49;
	public final static int LINE = 343;
	public final static int ERASER = 2401;
	public static final int RECTANGLE = 5764801;

	public final static int MOUSE_DRAGGING = 16807;
	public final static int MOUSE_RELEASE = 117649;
	public final static int MOUSE_CLICKED = 823543;

	public final static int ROUND = 2;
	public final static int SQUARE = 4;
	public final static int BUTT = 8;

	Color _color = Color.WHITE;
	int _size = 25;
	int _type = PENCIL;
	Boolean _fill = true;
	int _stroke_type = ROUND;

	public ToolInformation newInstance() {
		ToolInformation ti = new ToolInformation();

		ti.setColor(getColor());
		ti.setSize(_size);
		ti.setStroke(_stroke_type);
		ti.setFill(getFill());

		return ti;
	}

	public void setStroke(int s) {
		_stroke_type = s;

	}

	public Stroke getStroke() {
		if (_stroke_type == SQUARE) {
			return (Stroke) new BasicStroke(_size, BasicStroke.CAP_SQUARE,
					BasicStroke.JOIN_MITER);
		} else if (_stroke_type == BUTT) {
			return (Stroke) new BasicStroke(_size, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_BEVEL);
		} else {
			return (Stroke) new BasicStroke(_size, BasicStroke.CAP_ROUND,
					BasicStroke.JOIN_ROUND);
		}
	}

	public void setType(int t) {
		_type = t;
	}

	public int getType() {
		return _type;
	}

	public Color getColor() {
		return _color;
	}

	public void setColor(Color color) {
		this._color = color;
	}

	public int getSize() {
		return _size;
	}

	public void setSize(int size) {
		this._size = size;
	}

	public Boolean getFill() {
		return _fill;
	}

	public void setFill(Boolean fill) {
		this._fill = fill;
	}
}
