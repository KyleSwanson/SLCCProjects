package FlipBookPro;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.zip.ZipException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Export {
	public static String exportFlipbookToFolder(String output, int speed,
			Flipbook book) {
		// This should be a temp folder.
		File folder = createTempFolder();

		ArrayList<Slide> s = book.getSlides();

		for (int x = 0; x < s.size(); x++) {
			Slide slide = s.get(x);

			BufferedImage ts = slide.getOffscreenImage();
			File out = new File(folder, String.format("slide%03d.png", x + 1));
			try {
				ImageIO.write(ts, "png", out);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "An error occured during export of images:\n" + e.toString());
				break;
			}

		}

		// Good bye cross platform!
		return toWebM(folder.toString(), speed, output);
	}

	private static String toWebM(String temp_folder, int speed, String name) {
		// Sp is the number of frames per second.

		// SLOW_PLAYBACK = 500;
		// MED_PLAYBACK = 200;
		// FAST_PLAYBACK = 90;

		String sp = "";
		switch (speed) {
		case SlidePlayer.SLOW_PLAYBACK:
			sp = "2";
			break;
		case SlidePlayer.MED_PLAYBACK:
			sp = "5";
			break;
		case SlidePlayer.FAST_PLAYBACK:
			sp = "10";
			break;
		}

		// WebM mannnn, free and open web mannnn.
		try {

			String compile = findFFmpeg() + " -f image2 -r " + sp + " -i "
					+ temp_folder + "/slide%03d.png -vcodec libvpx " + name;
//			System.out.println(compile);

			Process p = Runtime.getRuntime().exec(compile);
//		} catch (IOException e) {
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "An error occured during export:\n" + e.getStackTrace());
		}

		return name + ".webm";
	}

	public static File createTempFolder() {
		File temp_folder = new File(System.getProperty("user.home"),
				".flipbook");

		if (!temp_folder.exists())
			temp_folder.mkdir();

		for (File c : temp_folder.listFiles()) {
			// Double check that theses are files we should remove.
			if (c.toString().contains("slide") && c.toString().contains(".png")) {
				c.delete();
			}
		}

		return temp_folder;
	}

	public static String findFFmpeg() {
		String path = "dne";

		if (checkForFFmpegOnPath()) {
			path = "avconv";
		}

//		System.out.println("FFMPEG at: " + path);

		// ffmpeg must be on their system path.
		return path;
	}

	private static boolean checkForFFmpegOnPath() {
		try {
			Runtime.getRuntime().exec("avconv");
		} catch (IOException e) {
			// e.printStackTrace();
			return false;
		}

		return true;
	}
}
