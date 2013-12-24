package FlipBookPro;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SaveLoad {
	public static String saveKylesIdea(Flipbook flipbook, String filePath) {
		ArrayList<ArrayList<DrawableInterface>> slides = new ArrayList<ArrayList<DrawableInterface>>();

		for (Slide s : flipbook.getSlides()) {
			slides.add(s.getDrawnPoints());
		}

		try {
			FileOutputStream file_out = new FileOutputStream(filePath);
			ObjectOutputStream obj_out = new ObjectOutputStream(file_out);

			obj_out.writeObject(slides);
			obj_out.close();
			file_out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return filePath;
	}

	public static Flipbook openKylesIdea(String filePath) {
		ArrayList<ArrayList<DrawableInterface>> slide_array = null;

		try {
			FileInputStream file_in = new FileInputStream(filePath);
			ObjectInputStream obj_in = new ObjectInputStream(file_in);
			slide_array = (ArrayList<ArrayList<DrawableInterface>>) obj_in
					.readObject();

			file_in.close();
			obj_in.close();

			Flipbook flip = new Flipbook();
			flip.nextSlide();

			for (ArrayList<DrawableInterface> drawable_array : slide_array) {
				flip.getSlide().setDrawnPoints(drawable_array);

				flip.getSlide().redraw = true;
				flip.nextSlide();
			}

			// Must remove a slide at the end, since next slide will create a
			// new slide at next slide.
			flip.removeSlide();

			return flip;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
