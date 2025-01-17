package view;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.EventQueue;
import controller.LatexEditorController;
import util.FileParser;
import util.PathUtilization;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class LatexEditorView {

	private String pressedButton = "Volatile";
	private String nL = System.getProperty("line.separator");
	private LatexEditorController controller;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnRollBack;
	private JFrame mainFrame;
	private JRadioButton rdbtnToggleTracking;
	private JRadioButton rdbtnVolatile;
	private JRadioButton rdbtnStable;
	private JMenuBar menuBar;
	private JMenu mnCreateDocument;
	private JMenu mnAddLatexCommand;
	private JMenu mnAddEnumerationList;
	private JMenu mnAddTable;
	private JMenu mnDocumentManagement;
	private JMenuItem mntAddEnumerationListI;
	private JMenuItem mntmAddEnumerationListE;
	private JMenuItem mntmAddTableT;
	private JMenuItem mntmAddTableF;
	private JMenuItem mntmSaveDocument;
	private JMenuItem mntmLoadDocument;
	private JMenuItem mntmEditDocument;
	private JMenuItem mntmEmptyDocument;
	private JMenuItem mntmBasedOnTemplate;
	private JMenuItem mntmAddChapter;
	private JMenuItem mntmAddSection;
	private JMenuItem mntmAddSubsection;
	private JMenuItem mntmAddSubsubsection;
	private JTextArea textArea;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LatexEditorView window = new LatexEditorView();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LatexEditorView() {
		controller = new LatexEditorController();
		initialize();
		disableFocus();
		actionEvents();
		actionEventsOfStrategies();
		actionEventsOfAddCommand();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		JScrollPane scrollPane = new JScrollPane();
		mainFrame.setTitle("LatexEditor");
		mainFrame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
		mainFrame.setBounds(100, 100, 763, 562);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		rdbtnToggleTracking = new JRadioButton("Enabled Tracking");
		rdbtnToggleTracking.setSelected(true);
		
		rdbtnVolatile = new JRadioButton("Volatile ");
		buttonGroup.add(rdbtnVolatile);
		rdbtnVolatile.setSelected(true);
		
		rdbtnStable = new JRadioButton("Stable");
		buttonGroup.add(rdbtnStable);
		
		btnRollBack = new JButton("Roll Back");
		
		GroupLayout groupLayout = new GroupLayout(mainFrame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 729, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(rdbtnToggleTracking, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(rdbtnVolatile, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rdbtnStable, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnRollBack, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnToggleTracking)
						.addComponent(rdbtnVolatile)
						.addComponent(rdbtnStable)
						.addComponent(btnRollBack))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setTabSize(4);
		textArea.setFont(new Font("Arial", Font.BOLD, 16));
		scrollPane.setViewportView(textArea);
		mainFrame.getContentPane().setLayout(groupLayout);
		
		menuBar = new JMenuBar();
		mainFrame.setJMenuBar(menuBar);
		
		mnCreateDocument = new JMenu("Create Document");
		menuBar.add(mnCreateDocument);
		
		mntmEmptyDocument = new JMenuItem("Empty Document");
		mnCreateDocument.add(mntmEmptyDocument);
		
		mntmBasedOnTemplate = new JMenuItem("Based On Template");
		mnCreateDocument.add(mntmBasedOnTemplate);
		
		mnAddLatexCommand = new JMenu("Add Latex Command");
		menuBar.add(mnAddLatexCommand);
		
		mntmAddChapter = new JMenuItem("Add Chapter");
		mnAddLatexCommand.add(mntmAddChapter);
		
		mntmAddSection = new JMenuItem("Add Section");
		mnAddLatexCommand.add(mntmAddSection);
		
		mntmAddSubsection = new JMenuItem("Add Subsection");
		mnAddLatexCommand.add(mntmAddSubsection);
		
		mntmAddSubsubsection = new JMenuItem("Add Subsubsection");
		mnAddLatexCommand.add(mntmAddSubsubsection);
		
		mnAddEnumerationList = new JMenu("Add Enumeration List");
		mnAddLatexCommand.add(mnAddEnumerationList);
		
		mntAddEnumerationListI = new JMenuItem("itemize");
		mnAddEnumerationList.add(mntAddEnumerationListI);
		
		mntmAddEnumerationListE = new JMenuItem("enumerate");
		mnAddEnumerationList.add(mntmAddEnumerationListE);
		
		mnAddTable = new JMenu("Add Table");
		mnAddLatexCommand.add(mnAddTable);
		
		mntmAddTableT = new JMenuItem("table");
		mnAddTable.add(mntmAddTableT);
		
		mntmAddTableF = new JMenuItem("figure");
		mnAddTable.add(mntmAddTableF);
		
		mnDocumentManagement = new JMenu("Document Management");
		menuBar.add(mnDocumentManagement);
		
		mntmSaveDocument = new JMenuItem("Save Document");
		mnDocumentManagement.add(mntmSaveDocument);
		
		mntmLoadDocument = new JMenuItem("Load Document");
		mnDocumentManagement.add(mntmLoadDocument);
		
		mntmEditDocument = new JMenuItem("Edit Document");
		mnDocumentManagement.add(mntmEditDocument);
	}

	public void disableFocus() {
		btnRollBack.setFocusable(false);
		rdbtnVolatile.setFocusable(false);
		rdbtnStable.setFocusable(false);
		rdbtnToggleTracking.setFocusable(false);
	}
	
	public void actionEvents() {
		mntmEmptyDocument.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nameOfFile;
				if(!controller.getVersionsManager().isStableVersion()) {
					controller.editMapOfCommands("CreateCommand","","");
					controller.enact("CreateCommand");
					controller.updateHistory(controller.getCreateCommandDocument());
				}else {
					if(!((nameOfFile = JOptionPane.showInputDialog(null,"Save as: ")) == null)){
						controller.editMapOfCommands("CreateCommand","",nameOfFile);
						controller.enact("CreateCommand");
						//controller.updateHistory(FileParser.createDocument(new ArrayList<String>()));
						controller.updateHistory(controller.getCreateCommandDocument());
					}	
				}
			}
		});
		mntmBasedOnTemplate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				String nameOfFile;
				fc.setCurrentDirectory(new File(PathUtilization.PROJECT_PATH+PathUtilization.TEMPLATE_ID));
				if(!controller.getVersionsManager().isStableVersion()) {
					if(JFileChooser.APPROVE_OPTION == fc.showOpenDialog(mainFrame)
							 && fc.getSelectedFile().getName().endsWith(".tex")) {
						String nameOfSelectedFile =  fc.getSelectedFile().getName().replace(".tex", "");
						controller.editMapOfCommands("CreateCommand",nameOfSelectedFile,"");
						controller.enact("CreateCommand");
						//controller.updateHistory(FileParser.createDocument(fc.getSelectedFile()));
						controller.updateHistory(controller.getCreateCommandDocument());
						appendTextToArea(controller.getCurrentDocument().getContent());
					}
				}else{
					if(JFileChooser.APPROVE_OPTION == fc.showOpenDialog(mainFrame)
						 && fc.getSelectedFile().getName().endsWith(".tex")
						 && !((nameOfFile = JOptionPane.showInputDialog(null,"Save as: ")) == null)) {
						String nameOfSelectedFile =  fc.getSelectedFile().getName().replace(".tex", "");
						controller.editMapOfCommands("CreateCommand",nameOfSelectedFile,nameOfFile);
						controller.enact("CreateCommand");
						//controller.updateHistory(FileParser.createDocument(fc.getSelectedFile()));
						controller.updateHistory(controller.getCreateCommandDocument());
						appendTextToArea(controller.getCurrentDocument().getContent());
					}
				}
			}
		});
		mntmLoadDocument.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File(PathUtilization.OUTPUT_FOLDER_PATH));
				if(JFileChooser.APPROVE_OPTION == fc.showOpenDialog(mainFrame)
						 && fc.getSelectedFile().getName().endsWith(".tex")) {
					File selectedFile = fc.getSelectedFile();
					controller.editMapOfCommands("LoadCommand",selectedFile.getAbsolutePath(),"");
					controller.enact("LoadCommand");
					controller.updateHistory(FileParser.createDocument(
							FileParser.convertStringToArrayList(textArea.getText()+nL),
								controller.getLoadCommandsContents()));
					appendTextToArea(controller.getLoadCommandsContents());
				}
			}
		});
		mntmSaveDocument.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nameOfFile = "";
				if(controller.getTypeOfCurrentStrategy().equals("StableVersionsStrategy")) {
					//nameOfFile = JOptionPane.showInputDialog(null,"Save as: ");
					if(!((nameOfFile = JOptionPane.showInputDialog(null,"Save as: ")) == null)) {
						controller.editMapOfCommands("SaveCommand",nameOfFile,textArea.getText());
						controller.enact("SaveCommand");
						controller.updateHistory(FileParser.createDocument(
								FileParser.convertStringToArrayList(textArea.getText())));
					}
				}
			}
		});
		mntmEditDocument.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File(PathUtilization.OUTPUT_FOLDER_PATH));
				if(JFileChooser.APPROVE_OPTION == fc.showOpenDialog(mainFrame)
						 && fc.getSelectedFile().getName().endsWith(".tex")) {
					controller.editMapOfCommands("EditCommand",
							fc.getSelectedFile().getAbsolutePath(),textArea.getText());
					controller.enact("EditCommand");
				}
			}
		});
		rdbtnToggleTracking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.getVersionsManager().toggleTracking();
			}
		});
		btnRollBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!controller.getVersionsManager().canIRollBack()) {
					JOptionPane.showMessageDialog(null,"No previous versions to rollback");
					return;
				}
				controller.editMapOfCommands("RollBackCommand","", "");
				controller.enact("RollBackCommand");
				controller.setCurrentDocument(controller.getVersionsManager().getCurrentDocument());
				controller.getVersionsManager().putDocumentAfterEdit();
				textArea.setText("");
				appendOldVersion(controller.getVersionsManager().getCurrentDocument().getContent());
			}
		});
	}
	
	public void actionEventsOfAddCommand() {
		mntmAddChapter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.editMapOfCommands("AddLatexCommand","chapter",textArea.getText());
				controller.enact("AddLatexCommand");
				textArea.append(controller.getAddLatexCommandText());
				controller.updateCurrentDocument();
				controller.updateHistory();
			}
		});
		mntmAddSection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.editMapOfCommands("AddLatexCommand","section",textArea.getText());
				controller.enact("AddLatexCommand");
				textArea.append(controller.getAddLatexCommandText());
				controller.updateCurrentDocument();
				controller.updateHistory();
			}
		});
		mntmAddSubsection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.editMapOfCommands("AddLatexCommand","subsection",textArea.getText());
				controller.enact("AddLatexCommand");
				textArea.append(controller.getAddLatexCommandText());
				controller.updateCurrentDocument();
				controller.updateHistory();
			}
		});
		mntmAddSubsubsection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.editMapOfCommands("AddLatexCommand","subsubsection",textArea.getText());
				controller.enact("AddLatexCommand");
				textArea.append(controller.getAddLatexCommandText());
				controller.updateCurrentDocument();
				controller.updateHistory();
			}
		});
		mntAddEnumerationListI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.editMapOfCommands("AddLatexCommand","addEnumerationListI",textArea.getText());
				controller.enact("AddLatexCommand");
				textArea.append(controller.getAddLatexCommandText());
			}
		});
		mntmAddEnumerationListE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				controller.editMapOfCommands("AddLatexCommand","addEnumerationListE",textArea.getText());
				controller.enact("AddLatexCommand");
				textArea.append(controller.getAddLatexCommandText());
			}
		});
		mntmAddTableT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.editMapOfCommands("AddLatexCommand","addTableT",textArea.getText());
				controller.enact("AddLatexCommand");
				textArea.append(controller.getAddLatexCommandText());
			}
		});
		mntmAddTableF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.editMapOfCommands("AddLatexCommand","addTableF",textArea.getText());
				controller.enact("AddLatexCommand");
				textArea.append(controller.getAddLatexCommandText());
			}
		});
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == 10) {
					controller.updateHistory(FileParser.createDocument(
							FileParser.convertStringToArrayList(textArea.getText())));
				}
			}
		});
	}
	
	//pressedButton -> previous pressed button name
	//if(!pressedButton.contentEquals(buttonName)) -> to avoid Strategy recreation if the button is already selected
	public void actionEventsOfStrategies() {
		rdbtnVolatile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!pressedButton.contentEquals("Volatile")) {
					pressedButton = "Volatile";
					controller.editMapOfCommands("ChangeStrategyCommand", "Volatile", "");
					controller.enact("ChangeStrategyCommand");
				}
			}
		});
		rdbtnStable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!pressedButton.contentEquals("Stable")) {
					pressedButton = "Stable";
					controller.editMapOfCommands("ChangeStrategyCommand", "Stable", "");
					controller.enact("ChangeStrategyCommand");
				}
			}
		});
	}
	
	public void appendTextToArea(ArrayList<String> arr) {
		if(!textArea.getText().isEmpty()) {
			textArea.append(nL);
		}
		for (String i: arr) {
			textArea.append(i+nL);
		}
	}
	
	public void appendOldVersion(ArrayList<String> arr) {
		for (String i: arr) {
			if(!i.endsWith(nL) && !i.equals("")) {
				textArea.append(i+nL);
			}else {
				textArea.append(i);
			}
		}
	}
}
