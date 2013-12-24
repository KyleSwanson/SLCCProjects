package FlipBookPro;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Flipbook {
	private ArrayList<Slide> _slides;
	private int _current_slide_index = 0;
	private int _next_id = 0;

	public Flipbook() {
		_slides = new ArrayList<Slide>();
		addSlide();
		_current_slide_index--;
	}

	public Slide getSlide() {
		return _slides.get(_current_slide_index);
	}

	public Slide getSlide(int index) {
		return _slides.get(index);
	}

	public Slide toFirst() {
		_current_slide_index = 0;

		return _slides.get(0);
	}

	public Slide nextSlide() {
		_current_slide_index++;

		if (_slides.size() == _current_slide_index) {
			return addSlide();
		}

		Slide output = _slides.get(_current_slide_index);

		return output;
	}

	public Slide previousSlide() {
		if (_current_slide_index > 0) {
			_current_slide_index--;
		} else {
			// Shouldn't ever get here now because of button being disabled
			// System.out.println("Already at slide 0");
		}

		Slide pre = _slides.get(_current_slide_index);

		return pre;
	}

	// TODO these three methods could have a lot of their functionality
	// combined.

	public Slide addSlideAfterCurrent() {
		Slide new_slide = new Slide(_next_id);
		_current_slide_index++;
		_slides.add(_current_slide_index, new_slide);
		_next_id++;

		return new_slide;
	}

	public Slide addDuplicateSlideAfterCurrent() {
		Slide new_slide = getSlide().newInstance(_next_id);

		_current_slide_index++;
		_slides.add(_current_slide_index, new_slide);
		_next_id++;

		return new_slide;
	}

	public Slide addSlide() {
		Slide new_slide = new Slide(_next_id);
		_slides.add(new_slide);
		_next_id++;

		return new_slide;
	}

	public Slide removeSlide() {
		_slides.remove(_current_slide_index);
		// If we are not at the start we display the slide before the current
		// one.
		if (_current_slide_index > 0)
			_current_slide_index--;
		// If the flipbook is empty, add a new one.
		if (length() == 0)
			addSlide();
		// If we are at the start, with slides after it, we display the slide
		// after the start. (So just leave index as is.

		return _slides.get(_current_slide_index);
	}

	public ArrayList<Slide> getSlides() {
		return _slides;
	}

	public void setSlides(ArrayList<Slide> s) {
		_slides = s;
	}

	public int length() {
		return _slides.size();
	}

	public int currentIndex() {
		return _current_slide_index;
	}

	public void clear() {
		_slides.clear();
		addSlide();
		_current_slide_index = 0;
	}
}
