package FlipBookPro;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.Timer;

public class SlidePlayer extends JComponent implements MouseListener {
	static final int SLOW_PLAYBACK = 500; // 2 fps.
	static final int MED_PLAYBACK = 200; // 5 fps.
	static final int FAST_PLAYBACK = 100; // 10 fps.

	static final int DEFAULT_PLAYBACK_SPEED = MED_PLAYBACK;
	private int _current_playback_speed;

	private Flipbook _flipbook;
	private int _current_index;

	private PlaybackOverListener _playback_over_listener;

	private Slide _slide_to_paint;
	private Slide _blank_slide = new Slide(0);

	private Timer _slide_player;
	private ActionListener _forward_performer;
	private ActionListener _repeat_performer;
	private ActionListener _reverse_performer;

	public SlidePlayer(Flipbook flipbook) {
		repaint();

		loadSlides(flipbook);
		addMouseListener(this);

		_forward_performer = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (_current_index == _flipbook.length() - 1) {
					_slide_player.stop();

					// This next line will make it so that it swaps back the
					// drawable canvas. I like having to click more.
					// _playback_over_listener.playbackOver();
				}

				// System.out.println(_flipbook.getSlide());
				paintSlide(_flipbook.getSlide(_current_index));
				_current_index++;
			}
		};

		_repeat_performer = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (_current_index == _flipbook.length()) {
					_current_index = 0;
				}

				paintSlide(_flipbook.getSlide(_current_index));
				_current_index++;
			}
		};

		_reverse_performer = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (_current_index == 0) {
					_slide_player.stop();
				}

				paintSlide(_flipbook.getSlide(_current_index));
				_current_index--;
			}
		};
	}

	public SlidePlayer(Flipbook flipbook, int playbackSpeed) {
		this(flipbook);
		this.setCurrentPlaybackSpeed(playbackSpeed);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// System.out.println("Painting the Slide Player Area");

		if (_slide_to_paint != null) {
			if (_slide_to_paint.redraw)
				_slide_to_paint.offScreenRedraw();

			g.drawImage(_slide_to_paint.getOffscreenImage(), 0, 0, null);
		} else {
			_blank_slide.clearSlide();
			g.drawImage(_blank_slide.getImage(), 0, 0, null);
		}
	}

	/**
	 * Load the slides to be played
	 * 
	 * @param flipbook
	 */
	public void loadSlides(Flipbook flipbook) {
		repaint();
		revalidate();

		this._flipbook = flipbook;
	}
	
	private void stopPlayer() {
		if (_slide_player != null) {
			_slide_player.stop();
		}
	}

	/**
	 * Play the slides from the first index. Note - MUST loadSlides() before
	 * calling this
	 */
	public void playSlides() {
		_current_index = 0;
		stopPlayer();
		_slide_player = new Timer(_current_playback_speed, _forward_performer);
		_slide_player.start();
	}

	/**
	 * Play the slides from the provided index. Note - MUST loadSlides() before
	 * calling this
	 */
	public void playSlidesFromIndex(int index) {
		_current_index = index;
		stopPlayer();
		_slide_player = new Timer(_current_playback_speed, _forward_performer);
		_slide_player.start();
	}

	public void playReverse() {
		_current_index = _flipbook.length() - 1;
		stopPlayer();
		_slide_player = new Timer(_current_playback_speed, _reverse_performer);
		_slide_player.start();
	}

	public void playRepeat() {
		_current_index = 0;
		stopPlayer();
		_slide_player = new Timer(_current_playback_speed, _repeat_performer);
		_slide_player.start();
	}

	public void setCurrentPlaybackSpeed(int speed) {
		_current_playback_speed = speed;
	}

	public int getPlaybackSpeed() {
		return _current_playback_speed;
	}

	public void setListener(PlaybackOverListener l) {
		_playback_over_listener = l;
	}

	public void stopPlayback() {
		_slide_player.stop();
		_slide_to_paint = null;
		repaint();
		// This will tell the user interface's listener that it is done!
		_playback_over_listener.playbackOver();
	}

	private void paintSlide(Slide s) {
		_slide_to_paint = s;
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		stopPlayback();
	}

	// These are not needed, they only exist so we can get to mouseClicked
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		stopPlayback();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
