package FlipBookPro;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class UserInterface extends javax.swing.JFrame implements ActionListener {
	private Flipbook _flipbook;
	private ToolInformation _tool_info;

	public UserInterface() {
		setIconImages(_app_icons);
		setTitle("FlipBook Pro");

		_flipbook = new Flipbook();
		_tool_info = new ToolInformation();

		initComponents();
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {
		_canvas = _flipbook.nextSlide();
//		 _canvas = new JPanel(); //------// This line allows WindowBuilder to
		// work
		_slidePlayer = new SlidePlayer(_flipbook, SlidePlayer.MED_PLAYBACK);
		_flipbook.getSlide().setToolInformation(_tool_info);

		// Set Default to Pencil
		_tool_info.setType(ToolInformation.PENCIL);

		_hori_top_layout = new JPanel();
		_vert_canvas_layout = new JPanel();
		_slide_count_label = new JLabel();
		_hori_canvas_layout = new JPanel();

		// //// Left Button Stuff
		_left_button = new JButton();
		_left_button.setToolTipText("Navigation button");
		_left_button.setActionCommand("slide_left");
		_left_button.addActionListener(this);
		_left_button.setIcon(_left_arrow_icon);
		_left_button.setEnabled(false);

		// //// Right Button Stuff
		_right_button = new JButton();
		_right_button.setToolTipText("Navigation button");
		_right_button.setActionCommand("slide_right");
		_right_button.addActionListener(this);
		_right_button.setIcon(_plus_icon);

		// //// Clear and Delete and Add and Duplicate Buttons Stuff
		_hori_clear_delete_layout = new JPanel();
		_filler_leftOfClear = new Box.Filler(new Dimension(0, 0),
				new Dimension(0, 0), new Dimension(10500, 0));
		_clear_button = new JButton();
		_clear_button.setToolTipText("Clears the current slide");
		_clear_button.setIcon(_clear_button_icon);
		_clear_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				clearSlideActionPerformed();
			}
		});
		_filler_betweenClearAndDelete = new Box.Filler(new Dimension(0, 0),
				new Dimension(0, 0), new Dimension(5500, 0));

		_delete_button = new JButton();
		_delete_button.setToolTipText("Deletes the current slide");
		_delete_button.setIcon(_delete_button_icon);
		_delete_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteSlideActionPerformed();
			}
		});
		_filler_betweenDeleteAndDuplicate = new Box.Filler(new Dimension(0, 0),
				new Dimension(0, 0), new Dimension(5500, 0));

		_addSlide_button = new JButton();
		_addSlide_button.setToolTipText("adds a new, blank slide after the current slide");
		_addSlide_button.setIcon(_add_slide_button_icon);
		_addSlide_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addSlideActionPerformed();
			}
		});
		_filler_rightOfAdd = new Box.Filler(new Dimension(0, 0), new Dimension(
				0, 0), new Dimension(10500, 0));

		_duplicate_button = new JButton();
		_duplicate_button.setToolTipText("Duplicates the current slide adding the new slide after the current slide.");
		_duplicate_button.setIcon(_duplicate_icon);
		_duplicate_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				duplicateSlideActionPerformed();
			}
		});
		_filler_betweenDuplicateAndAdd = new Box.Filler(new Dimension(0, 0),
				new Dimension(0, 0), new Dimension(5500, 0));

		// //// Tool Pane Stuff
		_toolPane_Separator = new JSeparator();
		_vert_button_layout = new JPanel();
		_tools_layout = new JPanel();
		_label_Type = new JLabel();
		_label_Size = new JLabel();
		_size_slider = new JSlider();
		_size_slider.setToolTipText("Tool size adjuster");
		_size_slider.setValue(_tool_info.getSize());
		_size_slider.setMaximum(100);
		_size_slider.setMinimum(1);
		_size_slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				_tool_info.setSize(_size_slider.getValue());
				_flipbook.getSlide().setToolInformation(_tool_info);
			}
		});
		_size_slider.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (e.getWheelRotation() < 0)
					_size_slider.setValue(_size_slider.getValue() + 1);
				else
					_size_slider.setValue(_size_slider.getValue() - 1);
			}
		});

		// //// Color Chooser Stuff
		_label_Color = new JLabel();
		_color_button = new JButton();
		_color_button.setToolTipText("The tools current color");
		_color_button.setBackground(_tool_info.getColor());
		_color_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorChooserActionPerformed();
			}
		});
		_playback_layout = new JPanel();
		_label_Speed = new JLabel();

		// //// Speed Radio Button Stuff
		_slowRadioButton = new JRadioButton();
		_slowRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_slidePlayer.setCurrentPlaybackSpeed(SlidePlayer.SLOW_PLAYBACK);
			}
		});
		_speedRadioButtonGroup.add(_slowRadioButton);
		_medRadioButton = new JRadioButton();
		_medRadioButton.setSelected(true);
		_medRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_slidePlayer.setCurrentPlaybackSpeed(SlidePlayer.MED_PLAYBACK);
			}
		});
		_speedRadioButtonGroup.add(_medRadioButton);
		_fastRadioButton = new JRadioButton();
		_fastRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_slidePlayer.setCurrentPlaybackSpeed(SlidePlayer.FAST_PLAYBACK);
			}
		});
		_speedRadioButtonGroup.add(_fastRadioButton);

		// //// Play Button Stuff
		_playFromCurrentButton = new JButton();
		_playFromCurrentButton.setToolTipText("Play from the current slide to the end of the flipbook");
		_playFromCurrentButton.setIcon(_play_from_current_icon);
		_playFromCurrentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playFromCurrent();
			}
		});
		_playBackwardsButton = new JButton();
		_playBackwardsButton.setToolTipText("Plays from the last slide in the flipbook to the first slide.");
		_playBackwardsButton.setIcon(_play_backwards_icon);
		_playBackwardsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playBackwards();
			}
		});

		// //// Top Menu Stuff
		_top_menu = new javax.swing.JMenuBar();
		_file_menu = new javax.swing.JMenu();
		_edit_menu = new javax.swing.JMenu();
		_help_menu = new javax.swing.JMenu();

		// //// Prompt user to make sure they aren't exiting accidentally
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				String[] choices = { "Save & Exit", "Exit", "Cancel" };

				int response = JOptionPane.showOptionDialog(
						null // Center in window.
						,
						"Any unsaved data will be lost. Are you sure you want to exit?" // Message
						, "Exit Confirmation" // Title in titlebar
						, JOptionPane.YES_NO_OPTION // Option type
						, JOptionPane.PLAIN_MESSAGE // messageType
						, null // Icon (none)
						, choices // Button text as above.
						, "You are a Ninja" // Default button's label
				);
				switch (response) {
				case 0:
					showSaveFileDialog();
					dispose();
					break;
				case 1:
					dispose();
					break;
				case 2:
					break;
				}
			}
		});

		setResizable(false);
		getContentPane().setLayout(new java.awt.GridLayout(1, 0));

		_hori_top_layout.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		_hori_top_layout.setLayout(new BoxLayout(_hori_top_layout,
				BoxLayout.LINE_AXIS));

		_vert_canvas_layout.setLayout(new BoxLayout(_vert_canvas_layout,
				BoxLayout.PAGE_AXIS));

		// //// Slide Count Stuff
		_slide_count_label
				.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		_slide_count_label.setText("Slide " + (_flipbook.currentIndex() + 1)
				+ " of " + (_flipbook.length()));
		_vert_canvas_layout.add(_slide_count_label);

		_hori_canvas_layout.setLayout(new BoxLayout(_hori_canvas_layout,
				BoxLayout.X_AXIS));
		_hori_canvas_layout.add(_left_button);

		// //// Canvas Stuff
		_canvas.setBackground(new java.awt.Color(102, 102, 102));
		setComponentMaxMinPrefferedSizes(_canvas);

		_canvasLayout = new javax.swing.GroupLayout(_canvas);
		_canvasLayout.setHorizontalGroup(_canvasLayout.createParallelGroup(
				Alignment.LEADING).addGap(0, 640, Short.MAX_VALUE));
		_canvasLayout.setVerticalGroup(_canvasLayout.createParallelGroup(
				Alignment.LEADING).addGap(0, 480, Short.MAX_VALUE));
		_canvas.setLayout(_canvasLayout);

		_hori_canvas_layout.add(_canvas);
		_hori_canvas_layout.add(_right_button);

		_vert_canvas_layout.add(_hori_canvas_layout);

		_hori_clear_delete_layout.setBorder(BorderFactory.createEmptyBorder(2,
				2, 2, 2));
		_hori_clear_delete_layout.setLayout(new BoxLayout(
				_hori_clear_delete_layout, javax.swing.BoxLayout.X_AXIS));
		_hori_clear_delete_layout.add(_filler_leftOfClear);

		// //// Clear Slide Button Stuff
		_clear_button.setText("Clear Slide");
		_hori_clear_delete_layout.add(_clear_button);
		_hori_clear_delete_layout.add(_filler_betweenClearAndDelete);

		// //// Delete Slide Button Stuff
		_delete_button.setText("Delete Slide");
		_hori_clear_delete_layout.add(_delete_button);
		_hori_clear_delete_layout.add(_filler_betweenDeleteAndDuplicate);

		// //// Duplicate Slide Button Stuff
		_duplicate_button.setText("Duplicate Slide");
		_hori_clear_delete_layout.add(_duplicate_button);
		_hori_clear_delete_layout.add(_filler_betweenDuplicateAndAdd);

		// //// Add Slide Button Stuff
		_addSlide_button.setText("Add Slide");
		_hori_clear_delete_layout.add(_addSlide_button);
		_hori_clear_delete_layout.add(_filler_rightOfAdd);

		_vert_canvas_layout.add(_hori_clear_delete_layout);

		_hori_top_layout.add(_vert_canvas_layout);

		_toolPane_Separator.setOrientation(SwingConstants.VERTICAL);
		_hori_top_layout.add(_toolPane_Separator);

		_vert_button_layout.setMaximumSize(new Dimension(250, 65534));
		_vert_button_layout.setMinimumSize(new Dimension(250, 305));
		_vert_button_layout.setName(""); // NOI18N
		_vert_button_layout.setPreferredSize(new Dimension(250, 305));
		_vert_button_layout.setLayout(new BoxLayout(_vert_button_layout,
				BoxLayout.PAGE_AXIS));

		// //// Tools Dropdown Stuff
		_tools_layout.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Tools"));
		_tools_layout.setLayout(new java.awt.GridLayout(0, 2));

		_label_Type.setText("Type");
		_tools_layout.add(_label_Type);

		// Ninja Button Stuff
		JButton _invisible_ninja_button = new JButton("I Am Ninja");
		_invisible_ninja_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "You are the uber ninja!");
			}
		});
		_invisible_ninja_button.setVisible(false);
		_tools_layout.add(_invisible_ninja_button);

		// Pencil Tool Button Stuff
		_tool_pencil_button = new JToggleButton("Pencil");
		_tool_pencil_button.setToolTipText("A tool for freehand drawing");
		_tool_pencil_button.setIcon(_brush_button_icon);
		_tool_pencil_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pencilToolSelectedActionPerformed();
			}
		});
		_toolsToggleButtonGroup.add(_tool_pencil_button);
		_tool_pencil_button.setSelected(true);
		_tools_layout.add(_tool_pencil_button);

		// Eraser Tool Button Stuff
		_tool_eraser_button = new JToggleButton("Eraser");
		_tool_eraser_button.setToolTipText("A tool for erasing lines which have already been drawn");
		_tool_eraser_button.setIcon(_eraser_button_icon);
		_tool_eraser_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eraserToolSelectedActionPerformed();
			}
		});
		_toolsToggleButtonGroup.add(_tool_eraser_button);
		_tools_layout.add(_tool_eraser_button);

		// Line Tool Button Stuff
		_tool_line_button = new JToggleButton("Line");
		_tool_line_button.setToolTipText("A tool for drawing straight lines");
		_tool_line_button.setIcon(_lines_button_icon);
		_tool_line_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lineToolSelectedActionPerformed();
			}
		});
		_toolsToggleButtonGroup.add(_tool_line_button);
		_tools_layout.add(_tool_line_button);

		// Rectangle Tool Button Stuff
		_tool_rectangle_button = new JToggleButton("Rectangle");
		_tool_rectangle_button.setToolTipText("A tool for drawing rectangles. Note - Use the Fill checkbox to change whether the rectangle is filled in or not.");
		_tool_rectangle_button.setIcon(_rectangle_button_icon);
		_tool_rectangle_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				squareToolSelectedActionPerformed();
			}
		});
		_toolsToggleButtonGroup.add(_tool_rectangle_button);
		_tools_layout.add(_tool_rectangle_button);

		// //// Tool Size Stuff
		_label_Size.setHorizontalAlignment(SwingConstants.LEFT);
		_label_Size.setText("Size");
		_tools_layout.add(_label_Size);
		_tools_layout.add(_size_slider);

		// //// Color Chooser Button Stuff
		_label_Color.setText("Color");
		_tools_layout.add(_label_Color);

		_tools_layout.add(_color_button);

		_vert_button_layout.add(_tools_layout);

		JLabel _label_fillShape = new JLabel("Fill");
		_tools_layout.add(_label_fillShape);

		_fill_toggle_button = new JToggleButton("Fill");
		_fill_toggle_button.setToolTipText("When selected, the current tool will draw with the tool being filled in. Note - used primarily with the Rectangle tool.");
		_fill_toggle_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fillToggle();
			}
		});
		_fill_toggle_button.setSelected(_tool_info.getFill());
		_tools_layout.add(_fill_toggle_button);

		_label_stroke = new JLabel("Stroke");
		_tools_layout.add(_label_stroke);

		_stroke_combo_box = new JComboBox();
		_stroke_combo_box.setToolTipText("Adjust the type of stroke for the current tool.");
		_stroke_combo_box.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				strokeComboBoxActionPerformed();
			}
		});
		_stroke_combo_box.setModel(new DefaultComboBoxModel(new String[] {
				"Round", "Square", "Bevel" }));
		_stroke_combo_box.setSelectedIndex(0);
		_tools_layout.add(_stroke_combo_box);

		// //// Playback Section Stuff
		_playback_layout.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Playback"));
		_playback_layout.setLayout(new java.awt.GridLayout(8, 0));

		_label_Speed.setHorizontalAlignment(SwingConstants.LEFT);
		_label_Speed.setText("Speed");
		_playback_layout.add(_label_Speed);

		// //// Speed Radio Button Stuff
		_slowRadioButton.setText("Slow");
		_playback_layout.add(_slowRadioButton);
		_medRadioButton.setText("Medium");
		_playback_layout.add(_medRadioButton);
		_fastRadioButton.setText("Fast");
		_playback_layout.add(_fastRadioButton);

		// //// Playback Buttons Stuff
		_playBackwardsButton.setText("Play Backwards");
		_playback_layout.add(_playBackwardsButton);

		_playContinuousButon = new JButton("Play Continuous");
		_playContinuousButon.setToolTipText("Plays from the beginning of the flipbook to the end in a continuous loop. Note - to stop playback, click in the canvas area.");
		_playContinuousButon.setIcon(_play_cont_icon);
		_playContinuousButon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				playRepeat();
			}
		});
		_playback_layout.add(_playContinuousButon);

		_playFromCurrentButton.setText("Play From Current");
		_playback_layout.add(_playFromCurrentButton);

		_playFromBeginningButton = new JButton("Play From Beginning");
		_playFromBeginningButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				playFromBeginning();
			}
		});
		_playFromBeginningButton.setToolTipText("Play from the beginning of the flipbook to the end of the flipbook");
		_playFromBeginningButton.setIcon(_play_from_beg_icon);
		_playback_layout.add(_playFromBeginningButton);

		_vert_button_layout.add(_playback_layout);

		_hori_top_layout.add(_vert_button_layout);

		getContentPane().add(_hori_top_layout);

		// //// File Menu Stuff
		_file_menu.setText("File");
		_top_menu.add(_file_menu);

		_menuItem_SaveAs = new JMenuItem("Save As...");
		_menuItem_SaveAs.setAccelerator(KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_S, java.awt.Event.CTRL_MASK));
		_menuItem_SaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showSaveFileDialog();
			}
		});

		_menuItem_New = new JMenuItem("New");
		_menuItem_New.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int confirmed = JOptionPane
						.showConfirmDialog(
								null,
								"Any unsaved data will be lost. "
										+ "Are you sure you want to create a new Flipbook?",
								"New Flipbook Confirmation",
								JOptionPane.YES_NO_OPTION);
				if (confirmed == JOptionPane.YES_OPTION)
					newFlipbookActionPerformed();
			}
		});
		_menuItem_New.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				InputEvent.CTRL_MASK));
		_file_menu.add(_menuItem_New);
		_file_menu.add(_menuItem_SaveAs);

		_menuItem_Open = new JMenuItem("Open");
		_menuItem_Open.setAccelerator(KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_O, java.awt.Event.CTRL_MASK));
		_menuItem_Open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showOpenFileDialog();
			}
		});
		_file_menu.add(_menuItem_Open);

		_menuItem_ExportMovie = new JMenuItem("Export Movie");
		_menuItem_ExportMovie.setAccelerator(KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_E, java.awt.Event.CTRL_MASK));
		_menuItem_ExportMovie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showExportFileDialog();
			}
		});
		_file_menu.add(_menuItem_ExportMovie);
		
		_menuItem_Exit = new JMenuItem("Exit");
		_menuItem_Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[] choices = { "Save & Exit", "Exit", "Cancel" };

				int response = JOptionPane.showOptionDialog(
						null // Center in window.
						,
						"Any unsaved data will be lost. Are you sure you want to exit?" // Message
						, "Exit Confirmation" // Title in titlebar
						, JOptionPane.YES_NO_OPTION // Option type
						, JOptionPane.PLAIN_MESSAGE // messageType
						, null // Icon (none)
						, choices // Button text as above.
						, "You are a Ninja" // Default button's label
				);
				switch (response) {
				case 0:
					showSaveFileDialog();
					dispose();
					break;
				case 1:
					dispose();
					break;
				case 2:
					break;
				}
			}
		});
		_menuItem_Exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
		_file_menu.add(_menuItem_Exit);

		// //// Edit Menu Stuff
		_edit_menu.setText("Edit");
		_top_menu.add(_edit_menu);

		_menuItem_Undo = new JMenuItem("Undo");
		_menuItem_Undo.setAccelerator(KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_Z, java.awt.Event.CTRL_MASK));
		_menuItem_Undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				undoActionPerformed();

			}
		});
		_edit_menu.add(_menuItem_Undo);

		_menuItem_ClearSlide = new JMenuItem("Clear Slide");
		_menuItem_ClearSlide.setAccelerator(KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_F, java.awt.Event.CTRL_MASK));
		_menuItem_ClearSlide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearSlideActionPerformed();
			}
		});
		_edit_menu.add(_menuItem_ClearSlide);

		_menuItem_DeleteSlide = new JMenuItem("Delete Slide");
		_menuItem_DeleteSlide.setAccelerator(KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_DELETE, java.awt.Event.CTRL_MASK));
		_menuItem_DeleteSlide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteSlideActionPerformed();
			}
		});
		_edit_menu.add(_menuItem_DeleteSlide);

		_menuItem_AddSlide = new JMenuItem("Add Slide");
		_menuItem_AddSlide.setAccelerator(KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_A, java.awt.Event.CTRL_MASK));
		_menuItem_AddSlide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addSlideActionPerformed();
			}
		});
		_edit_menu.add(_menuItem_AddSlide);

		_menuItem_DupSlide = new JMenuItem("Duplicate Slide");
		_menuItem_DupSlide.setAccelerator(KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_D, java.awt.Event.CTRL_MASK));
		_menuItem_DupSlide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				duplicateSlideActionPerformed();
			}
		});
		_edit_menu.add(_menuItem_DupSlide);

		_tool_menu = new JMenu("Tools");
		_top_menu.add(_tool_menu);

		_menuItem_NavLeft = new JMenuItem("Navigate Left");
		_menuItem_NavLeft.setActionCommand("slide_left");
		_menuItem_NavLeft.addActionListener(this);
		_menuItem_NavLeft.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_LEFT, 0));
		_tool_menu.add(_menuItem_NavLeft);

		_menuItem_NavRight = new JMenuItem("Navigate Right");
		_menuItem_NavRight.setActionCommand("slide_right");
		_menuItem_NavRight.addActionListener(this);
		_menuItem_NavRight.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_RIGHT, 0));
		_tool_menu.add(_menuItem_NavRight);

		_menuItem_SelPencil = new JMenuItem("Select Pencil Tool");
		_menuItem_SelPencil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pencilToolSelectedActionPerformed();
			}
		});
		_menuItem_SelPencil.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_1, 0));
		_tool_menu.add(_menuItem_SelPencil);

		_menuItem_SelEraser = new JMenuItem("Select Eraser Tool");
		_menuItem_SelEraser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eraserToolSelectedActionPerformed();
			}
		});
		_menuItem_SelEraser.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_2, 0));
		_tool_menu.add(_menuItem_SelEraser);

		_menuItem_SelLine = new JMenuItem("Select Line Tool");
		_menuItem_SelLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lineToolSelectedActionPerformed();
			}
		});
		_menuItem_SelLine.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3,
				0));
		_tool_menu.add(_menuItem_SelLine);

		_menuItem_SelRect = new JMenuItem("Select Rectangle Tool");
		_menuItem_SelRect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				squareToolSelectedActionPerformed();
			}
		});
		_menuItem_SelRect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4,
				0));
		_tool_menu.add(_menuItem_SelRect);

		_menuItem_ChooseColor = new JMenuItem("Choose Color");
		_menuItem_ChooseColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorChooserActionPerformed();
			}
		});
		_menuItem_ChooseColor.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_MINUS, 0));
		_tool_menu.add(_menuItem_ChooseColor);

		_playback_menu = new JMenu("Playback");
		_top_menu.add(_playback_menu);

		_menuItem_PlayFromBeginning = new JMenuItem("Play From Beginning");
		_menuItem_PlayFromBeginning.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playFromBeginning();
			}
		});
		_menuItem_PlayFromBeginning.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_P, InputEvent.CTRL_MASK));
		_playback_menu.add(_menuItem_PlayFromBeginning);

		_menuItem_PlayFromCurrent = new JMenuItem("Play From Current");
		_menuItem_PlayFromCurrent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playFromCurrent();
			}
		});
		_menuItem_PlayFromCurrent.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_L, InputEvent.CTRL_MASK));
		_playback_menu.add(_menuItem_PlayFromCurrent);

		_menuItem_PlayContinuous = new JMenuItem("Play Continuous");
		_menuItem_PlayContinuous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playRepeat();
			}
		});
		_menuItem_PlayContinuous.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_O, InputEvent.CTRL_MASK));
		_playback_menu.add(_menuItem_PlayContinuous);

		_menuItem_PlayBackwards = new JMenuItem("Play Backwards");
		_menuItem_PlayBackwards.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playBackwards();
			}
		});
		_menuItem_PlayBackwards.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_K, InputEvent.CTRL_MASK));
		_playback_menu.add(_menuItem_PlayBackwards);

		_help_menu.setText("Help");
		_top_menu.add(_help_menu);

		_menuItem_INeedHelp = new JMenuItem("I Need Help...");
		_menuItem_INeedHelp.setAccelerator(KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_H, java.awt.Event.CTRL_MASK));
		_menuItem_INeedHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, _helpDialogMessage);
			}
		});
		_help_menu.add(_menuItem_INeedHelp);

		_menuItem_Shortcuts = new JMenuItem("Shortcut List");
		_menuItem_Shortcuts.setAccelerator(KeyStroke
				.getKeyStroke(java.awt.event.KeyEvent.VK_BACK_SLASH,
						java.awt.Event.CTRL_MASK));
		_menuItem_Shortcuts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, _shortcutsDialogMessage);
			}
		});
		_help_menu.add(_menuItem_Shortcuts);

		setJMenuBar(_top_menu);

		pack();
	}

	public void newFlipbookActionPerformed() {
		_flipbook.clear();
		setCanvasToSlide(_flipbook.getSlide());

		updateSlideLabel();
		isLeftEnabled();
		rightArrowOrPlus();
	}

	public void duplicateSlideActionPerformed() {
		setCanvasToSlide(_flipbook.addDuplicateSlideAfterCurrent());
		_flipbook.getSlide().redraw();
		updateSlideLabel();
		isLeftEnabled();
		rightArrowOrPlus();
	}

	public void addSlideActionPerformed() {
		setCanvasToSlide(_flipbook.addSlideAfterCurrent());
		updateSlideLabel();
		isLeftEnabled();
		rightArrowOrPlus();
	}

	public void deleteSlideActionPerformed() {
		setCanvasToSlide(_flipbook.removeSlide());
		rightArrowOrPlus();
		isLeftEnabled();
		updateSlideLabel();
	}

	private void strokeComboBoxActionPerformed() {
		// Round - 0
		// Square - 1
		// Butt - 2

		int i = _stroke_combo_box.getSelectedIndex();

		if (i == 0) {
			_tool_info.setStroke(ToolInformation.ROUND);
		}

		if (i == 1) {
			_tool_info.setStroke(ToolInformation.SQUARE);
		}

		if (i == 2) {
			_tool_info.setStroke(ToolInformation.BUTT);
		}
	}

	public void clearSlideActionPerformed() {
		_flipbook.getSlide().purgeSlide();
	}

	public void undoActionPerformed() {
		_flipbook.getSlide().undo();
	}

	public void fillToggle() {
		boolean fill = _fill_toggle_button.isSelected();

		if (fill)
			_tool_info.setFill(true);

		else
			_tool_info.setFill(false);
	}

	public void colorChooserActionPerformed() {
		Color c = JColorChooser.showDialog(null, "Choose a Color",
				_tool_info.getColor());

		if (c != null) {
			_tool_info.setColor(c);
			_color_button.setBackground(_tool_info.getColor());
		}
	}

	private void pencilToolSelectedActionPerformed() {
		_tool_pencil_button.setSelected(true);
		_tool_info.setType(ToolInformation.PENCIL);
	}

	private void squareToolSelectedActionPerformed() {
		_tool_rectangle_button.setSelected(true);
		_tool_info.setType(ToolInformation.RECTANGLE);
	}

	private void eraserToolSelectedActionPerformed() {
		_tool_eraser_button.setSelected(true);
		_tool_info.setType(ToolInformation.ERASER);
	}

	private void lineToolSelectedActionPerformed() {
		_tool_line_button.setSelected(true);
		_tool_info.setType(ToolInformation.LINE);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("slide_right")) {
			setCanvasToSlide(_flipbook.nextSlide());
			rightArrowOrPlus();
			isLeftEnabled();
			updateSlideLabel();
		}

		else if (ae.getActionCommand().equals("slide_left")) {
			setCanvasToSlide(_flipbook.previousSlide());
			isLeftEnabled();
			rightArrowOrPlus();
			updateSlideLabel();
		}

	}

	private boolean isLeftEnabled() {
		// Logic to disable the left arrow
		if (_flipbook.currentIndex() == 0) {
			_left_button.setEnabled(false);
			return false;
		} else {
			_left_button.setEnabled(true);
			return true;
		}
	}

	private void rightArrowOrPlus() {
		// Logic to change --> to a + if last slide
		if (_flipbook.length() == _flipbook.currentIndex() + 1) {
			_right_button.setIcon(_plus_icon);
		} else {
			_right_button.setIcon(_right_arrow_icon);
		}

	}

	private void updateSlideLabel() {
		if (_slide_count_label != null) {
			// +1 to make it more user friendly so that it doesn't show slide 0
			// which isn't very intuitive to a non-programmer
			_slide_count_label.setText("Slide "
					+ (_flipbook.currentIndex() + 1) + " of "
					+ (_flipbook.length()));
		}
	}

	private void setCanvasToSlide(Slide s) {
		_flipbook.getSlide().setToolInformation(_tool_info);
		_hori_canvas_layout.remove(_canvas);
		_canvas = null;
		_canvas = s;
		_hori_canvas_layout.add(_canvas, 1);

		// Need to reset the size for the canvas. Kinda dirty in my opinion.
		setComponentMaxMinPrefferedSizes(_canvas);

		_canvas.revalidate();
		_canvas.repaint();

		_tool_info = s.getToolInformation();
	}

	public void swapCanvasWithPlayer(SlidePlayer sp) {
		_hori_canvas_layout.remove(_canvas);

		_hori_canvas_layout.add(sp, 1);
		sp.setVisible(true);

		setComponentMaxMinPrefferedSizes(sp);

		disableButtons();
		_slide_count_label.setText("Playing Slides");

		_hori_canvas_layout.revalidate();
		_hori_canvas_layout.invalidate();
		_hori_canvas_layout.repaint();

		sp.setListener(new PlaybackOverListener() {
			@Override
			public void playbackOver() {
				swapPlayerWithCanvas();
			}
		});
	}

	private void disableButtons() {
		_left_button.setEnabled(false);
		_right_button.setEnabled(false);
		_delete_button.setEnabled(false);
		_clear_button.setEnabled(false);
		_addSlide_button.setEnabled(false);
		_duplicate_button.setEnabled(false);

		_menuItem_NavLeft.setEnabled(false);
		_menuItem_NavRight.setEnabled(false);
		_menuItem_DupSlide.setEnabled(false);
		_menuItem_AddSlide.setEnabled(false);
		_menuItem_ClearSlide.setEnabled(false);
		_menuItem_DeleteSlide.setEnabled(false);
	}

	private void enableButtons() {
		isLeftEnabled();
		_right_button.setEnabled(true);
		_delete_button.setEnabled(true);
		_clear_button.setEnabled(true);
		_addSlide_button.setEnabled(true);
		_duplicate_button.setEnabled(true);

		_menuItem_NavLeft.setEnabled(true);
		_menuItem_NavRight.setEnabled(true);
		_menuItem_DupSlide.setEnabled(true);
		_menuItem_AddSlide.setEnabled(true);
		_menuItem_ClearSlide.setEnabled(true);
		_menuItem_DeleteSlide.setEnabled(true);
	}

	public void swapPlayerWithCanvas() {
		_hori_canvas_layout.remove(1);
		_hori_canvas_layout.add(_canvas, 1);

		// Need to reset the size for the canvas. Kinda dirty in my opinion.
		setComponentMaxMinPrefferedSizes(_canvas);

		enableButtons();
		updateSlideLabel();

		_canvas.revalidate();
		_canvas.repaint();
	}

	private void setComponentMaxMinPrefferedSizes(JComponent comp) {
		comp.setMaximumSize(new Dimension(640, 480));
		comp.setMinimumSize(new Dimension(640, 480));
		comp.setPreferredSize(new Dimension(640, 480));
	}

	private void playFromCurrent() {
		_slidePlayer.loadSlides(_flipbook);
		swapCanvasWithPlayer(_slidePlayer);
		_slidePlayer.playSlidesFromIndex(_flipbook.currentIndex());
	}

	private void playFromBeginning() {
		_slidePlayer.loadSlides(_flipbook);
		swapCanvasWithPlayer(_slidePlayer);
		_slidePlayer.playSlides();
	}

	private void playBackwards() {
		_slidePlayer.loadSlides(_flipbook);
		swapCanvasWithPlayer(_slidePlayer);
		_slidePlayer.playReverse();
	}

	private void playRepeat() {
		_slidePlayer.loadSlides(_flipbook);
		swapCanvasWithPlayer(_slidePlayer);
		_slidePlayer.playRepeat();
	}

	private void showExportFileDialog() {

		if (Export.findFFmpeg() == "dne") {
			System.out.println("libav not installed");
			JOptionPane
					.showMessageDialog(
							null,
							"The Export Movie feature requires Libav to be installed, and on your path.\n"
									+ "Please navigate to http://libav.org/download.html and install it before using this feature.\n" 
									+ "For help installing please visit our website for help with installing Libav");
			return;
		}

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Export Flipbook");
		
		DateFormat df = new SimpleDateFormat("HH-mm-ss");
		Date d = new Date();
		fileChooser.setSelectedFile(new File("Flipbook-" + df.format(d) + ".webm"));

		FileNameExtensionFilter webmFilter = new FileNameExtensionFilter(
				"WebM (*.webm)", "webm");
		fileChooser.addChoosableFileFilter(webmFilter);
		fileChooser.setFileFilter(webmFilter);

		int userSelection = fileChooser.showSaveDialog(this);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();

			Export.exportFlipbookToFolder(fileToSave.getAbsolutePath(),
					_slidePlayer.getPlaybackSpeed(), _flipbook);

		}
	}

	private void showSaveFileDialog() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save Flipbook As...");
		fileChooser.setSelectedFile(new File("Flipbook.flip"));

		FileNameExtensionFilter flipFilter = new FileNameExtensionFilter(
				"Flipbook Pro Files (*.flip)", "flip");
		fileChooser.addChoosableFileFilter(flipFilter);
		fileChooser.setFileFilter(flipFilter);

		int userSelection = fileChooser.showSaveDialog(this);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			System.out.println("Save as file: " + fileToSave.getAbsolutePath());
			SaveLoad.saveKylesIdea(_flipbook, fileToSave.getAbsolutePath());
		}
	}

	private void showOpenFileDialog() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Open Flipbook");
		fileChooser.setSelectedFile(new File("Flipbook.flip"));

		FileNameExtensionFilter flipFilter = new FileNameExtensionFilter(
				"Flipbook Pro Files (*.flip)", "flip");
		fileChooser.addChoosableFileFilter(flipFilter);
		fileChooser.setFileFilter(flipFilter);

		int userSelection = fileChooser.showOpenDialog(this);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToOpen = fileChooser.getSelectedFile();
			System.out.println("Open file: " + fileToOpen.getAbsolutePath());

			_flipbook.clear();

			_flipbook = SaveLoad.openKylesIdea(fileToOpen.getAbsolutePath());

			if (_flipbook != null) {
				setCanvasToSlide(_flipbook.toFirst());

				updateSlideLabel();
				isLeftEnabled();
				rightArrowOrPlus();
			} else {
				// Display a box.
			}
		}
	}

	private Slide _canvas;
//	 private JPanel _canvas; //------// This line allows WindowBuilder to work
	private SlidePlayer _slidePlayer;

	javax.swing.GroupLayout _canvasLayout;

	private JButton _clear_button;
	private JButton _color_button;
	private JButton _delete_button;
	private JButton _addSlide_button;
	private JButton _duplicate_button;
	private JButton _right_button;
	private JButton _left_button;
	private JButton _playFromCurrentButton;
	private JButton _playFromBeginningButton;
	private JButton _playBackwardsButton;
	private JButton _playContinuousButon;

	private final ButtonGroup _toolsToggleButtonGroup = new ButtonGroup();
	private JToggleButton _tool_pencil_button;
	private JToggleButton _tool_eraser_button;
	private JToggleButton _tool_line_button;
	private JToggleButton _tool_rectangle_button;
	private JToggleButton _fill_toggle_button;

	private final ButtonGroup _speedRadioButtonGroup = new ButtonGroup();
	private JRadioButton _slowRadioButton;
	private JRadioButton _medRadioButton;
	private JRadioButton _fastRadioButton;

	private JMenuBar _top_menu;
	private JMenu _edit_menu;
	private JMenu _file_menu;
	private JMenu _help_menu;
	private JMenu _tool_menu;
	private JMenu _playback_menu;

	private JMenuItem _menuItem_SaveAs;
	private JMenuItem _menuItem_Open;
	private JMenuItem _menuItem_ExportMovie;
	private JMenuItem _menuItem_INeedHelp;
	private JMenuItem _menuItem_Undo;
	private JMenuItem _menuItem_ClearSlide;
	private JMenuItem _menuItem_DeleteSlide;
	private JMenuItem _menuItem_AddSlide;
	private JMenuItem _menuItem_DupSlide;
	private JMenuItem _menuItem_SelPencil;
	private JMenuItem _menuItem_SelEraser;
	private JMenuItem _menuItem_SelLine;
	private JMenuItem _menuItem_SelRect;
	private JMenuItem _menuItem_NavLeft;
	private JMenuItem _menuItem_NavRight;
	private JMenuItem _menuItem_ChooseColor;
	private JMenuItem _menuItem_PlayFromBeginning;
	private JMenuItem _menuItem_PlayFromCurrent;
	private JMenuItem _menuItem_PlayContinuous;
	private JMenuItem _menuItem_PlayBackwards;
	private JMenuItem _menuItem_New;
	private JMenuItem _menuItem_Shortcuts;
	private JMenuItem _menuItem_Exit;

	private JPanel _hori_canvas_layout;
	private JPanel _hori_clear_delete_layout;
	private JPanel _hori_top_layout;
	private JPanel _playback_layout;
	private JPanel _tools_layout;
	private JPanel _vert_button_layout;
	private JPanel _vert_canvas_layout;

	private JLabel _slide_count_label;
	private JLabel _label_Size;
	private JLabel _label_Color;
	private JLabel _label_Type;
	private JLabel _label_Speed;
	private JLabel _label_stroke;
	private JComboBox _stroke_combo_box;

	private JSlider _size_slider;
	private JSeparator _toolPane_Separator;

	private Box.Filler _filler_betweenClearAndDelete;
	private Box.Filler _filler_betweenDeleteAndDuplicate;
	private Box.Filler _filler_betweenDuplicateAndAdd;
	private Box.Filler _filler_leftOfClear;
	private Box.Filler _filler_rightOfAdd;

	private String _helpDialogMessage = "-- Use the Left and Right arrows to navigate through the different slides.\n"
			+ "-- Use the '+' to add a new slide.\n"
			+ "-- Different tool options can be selected in the 'Tools' section.\n"
			+ "-- Different playback options can be selected in the 'Playback' section.\n"
			+ "-- During playback, certain buttons will be disabled. To exit playback mode and return"
			+ "to edit mode, simply click with your mouse in the canvas area.\n"
			+ "-- Why use WebM for exporting a movie and not AVI or mpeg?! Vist http://www.webmproject.org/about/ to "
			+ "learn more about the WebM format.";
	private String _shortcutsDialogMessage = "Shortcuts:\n\n"
			+ "New - Ctrl + N\n" + "Save - - Ctrl + S\n" + "Open - Ctrl + O\n"
			+ "Export Movie - Ctrl + E\n" + "Exit - Escape" + "Undo - Ctrl + Z\n"
			+ "Clear Slide - Ctrl + F\n" + "Delete Slide - Ctrl + Delete\n"
			+ "Add Slide - Ctrl + A\n" + "Duplicate Slide - Ctrl + D\n"
			+ "Navigate Left - Left\n" + "Navigate Right - Right\n"
			+ "Select Pencil Tool - 1\n" + "Select Eraser Tool - 2\n"
			+ "Select Line Tool - 3\n" + "Select Rectangle Tool - 4\n"
			+ "Choose Color - - (Minus)\n" + "Play From Beginning - Ctrl + P\n"
			+ "Play From Current - Ctrl + L\n" + "Play Continuous - Ctrl + O\n"
			+ "Play Backwards - Ctrl + K\n" + "I Need Help... - Ctrl + H\n";

	// /// Icons
	private ImageIcon _plus_icon = new ImageIcon(
			UserInterface.class.getResource("/res/list-add.png"));
	private ImageIcon _left_arrow_icon = new ImageIcon(
			UserInterface.class.getResource("/res/go-previous.png"));
	private ImageIcon _right_arrow_icon = new ImageIcon(
			UserInterface.class.getResource("/res/go-next.png"));
	private ImageIcon _play_cont_icon = new ImageIcon(
			UserInterface.class.getResource("/res/view-refresh-20-dark.png"));
	private ImageIcon _play_from_beg_icon = new ImageIcon(
			UserInterface.class.getResource("/res/media-playback-start-24.png"));
	private ImageIcon _play_from_current_icon = new ImageIcon(
			UserInterface.class.getResource("/res/media-playback-start-24.png"));
	private ImageIcon _play_backwards_icon = new ImageIcon(
			UserInterface.class.getResource("/res/media-seek-backward-24.png"));
	private ImageIcon _duplicate_icon = new ImageIcon(
			UserInterface.class.getResource("/res/stock_slide_duplicate.png"));
	private ImageIcon _add_slide_button_icon = new ImageIcon(
			UserInterface.class.getResource("/res/list-add-15.png"));
	private ImageIcon _delete_button_icon = new ImageIcon(
			UserInterface.class.getResource("/res/edit-delete-15.png"));
	private ImageIcon _clear_button_icon = new ImageIcon(
			UserInterface.class.getResource("/res/draw-eraser-15.png"));
	private ImageIcon _brush_button_icon = new ImageIcon(
			UserInterface.class.getResource("/res/draw-brush-15.png"));
	private ImageIcon _eraser_button_icon = new ImageIcon(
			UserInterface.class.getResource("/res/draw-eraser-15.png"));
	private ImageIcon _lines_button_icon = new ImageIcon(
			UserInterface.class.getResource("/res/lines-15.png"));
	private ImageIcon _rectangle_button_icon = new ImageIcon(
			UserInterface.class.getResource("/res/draw-rectangle-15.png"));

	private ArrayList<Image> _app_icons = getAppIcons();

	private ArrayList<Image> getAppIcons() {
		ArrayList<Image> icons = new ArrayList<Image>();
		icons.add(Toolkit.getDefaultToolkit().getImage(
				UserInterface.class.getResource("/res/icon_squares.png")));
		icons.add(Toolkit.getDefaultToolkit().getImage(
				UserInterface.class
						.getResource("/res/icon_squares_small-16.png")));
		return icons;
	}
}
