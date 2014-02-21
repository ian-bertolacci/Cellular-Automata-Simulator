package GUI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import cas.parser.MataParser;
import cas.parser.parser_containers.PartialAutomata;
import cas.automata.Automata;

public class StartWindow {

	private JFrame frmSetup;
	private JTextField textField;
	private String filePath = noFileString;
	private static final String noFileString = "No File Selected";
	private String fileName;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	JLabel lblFileName;
	JSpinner sizeSpinner;
	JLabel lblEdgeSize;
	JButton btnOpenFile;
	JButton btnStart;
	JComboBox initalSpaceComboBox;
	
	
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartWindow window = new StartWindow();
					window.frmSetup.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the application.
	 */
	public StartWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSetup = new JFrame();
		frmSetup.setTitle("Setup");
		frmSetup.setBounds(100, 100, 450, 238);
		frmSetup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSetup.getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(10, 24, 414, 20);
		frmSetup.getContentPane().add(textField);
		textField.setColumns(10);
		textField.setText(noFileString);

		lblFileName = new JLabel("Rule File");
		lblFileName.setBounds(10, 11, 66, 14);
		frmSetup.getContentPane().add(lblFileName);

		sizeSpinner = new JSpinner();
		sizeSpinner.setModel(new SpinnerNumberModel(new Integer(125), new Integer(1), null, new Integer(1)));
		sizeSpinner.setBounds(10, 100, 88, 20);
		frmSetup.getContentPane().add(sizeSpinner);

		btnOpenFile = new JButton("Open File");
		btnOpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openButtonOnAction();

			}
		});
		btnOpenFile.setBounds(10, 44, 89, 23);
		frmSetup.getContentPane().add(btnOpenFile);

		lblEdgeSize = new JLabel("Edge Size");
		lblEdgeSize.setBounds(10, 83, 88, 14);
		frmSetup.getContentPane().add(lblEdgeSize);

		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startButtonOnAction();

			}
		});
		btnStart.setBounds(146, 154, 89, 23);
		frmSetup.getContentPane().add(btnStart);
		
		initalSpaceComboBox = new JComboBox();
		initalSpaceComboBox.setModel(new DefaultComboBoxModel(new String[] {"All Dead", "Middle Cell", "Middle Square", "Full Random", "Random Fill Square"}));
		initalSpaceComboBox.setBounds(10, 131, 88, 20);
		frmSetup.getContentPane().add(initalSpaceComboBox);
	}

	private void startButtonOnAction(){
		if( !this.filePath.equals(noFileString) ){
			try{
				//System.out.println( Toolkit.getDefaultToolkit().getScreenSize().toString() );
				//System.out.println(System.getProperty("os.name"));
				MataParser compiler = new MataParser(this.filePath);
				compiler.parse();
	
				PartialAutomata partialMachine = compiler.partialAutomata;
				partialMachine.setSize( ( (Integer) this.sizeSpinner.getValue()).intValue() );
			
				Automata machine = partialMachine.createAutomata();
				
				String chosenOption = (String) this.initalSpaceComboBox.getSelectedItem();
				System.out.println( chosenOption );
				switch( chosenOption ){
				case "All Dead":
					//do nothing.
					break;
					
				case "Middle Cell":
					if( machine.getDimension() == 2 ){
						int index = ( machine.getNumberCells() + (  machine.getNumberCells() % 2 == 0 ? machine.getSize() : 0 ) )/2;
						machine.setCell(  index, 1); 
						
					} else if( machine.getDimension() == 1 ){
						machine.setCell( machine.getSize()/2, 1); // 1-d middle
						
					} else {
						System.out.println("Invalid Dimensionality");
						
					}
					break;
					
				case "Middle Square":{
					int k = ( (Integer) this.sizeSpinner.getValue() ).intValue() / 8;
					for( int i = 3*k; i < 5 * k; ++i){
						for( int j = 3*k; j < 5* k; ++j  ){
							machine.setCell(i * machine.getSize() + j, 1);
						}
					}
					break;
				}
				
				case "Full Random":
					machine.generateRandomConfiguration();
					break;
					
				case "Random Fill Square":{
					Random rand = new Random();
					int k = ( (Integer) this.sizeSpinner.getValue() ).intValue() / 8;
					for( int i = 3*k; i < 5 * k; ++i){
						for( int j = 3*k; j < 5* k; ++j  ){
							machine.setCell(i * machine.getSize() + j, Math.abs( rand.nextInt() ) % machine.getStates() );
						}
					}
					break;
				}
				
				default:
					System.out.println( "\"" + chosenOption + "\" is not a valid options");
				
				
				}
				
				//machine.setCell(machine.getSize()/2, 1); // 1-d middle
				//machine.setCell(machine.getSize()-1, 1); // 1-d far edge
				//machine.setCell( (int) ( Math.pow( machine.getSize(), machine.getDimension() ) + machine.getSize() )/2, 2); // 2-d middle
	
				/*// 4x4
			machine.setCell( (int) ( Math.pow( machine.getSize(), machine.getDimension() ) + machine.getSize() )/2 + 2 , 1);
			machine.setCell( (int) ( Math.pow( machine.getSize(), machine.getDimension() ) + machine.getSize() )/2 + 1 , 1);
			machine.setCell( (int) ( Math.pow( machine.getSize(), machine.getDimension() ) + machine.getSize() )/2  , 1);
			machine.setCell( (int) ( Math.pow( machine.getSize(), machine.getDimension() ) + machine.getSize() )/2 - 1 , 1);
	
			machine.setCell( (int) ( Math.pow( machine.getSize(), machine.getDimension() ) + machine.getSize() )/2 + 2 + machine.getSize() , 1);
			machine.setCell( (int) ( Math.pow( machine.getSize(), machine.getDimension() ) + machine.getSize() )/2 + 1+ machine.getSize() , 1);
			machine.setCell( (int) ( Math.pow( machine.getSize(), machine.getDimension() ) + machine.getSize() )/2+ machine.getSize()  , 1);
			machine.setCell( (int) ( Math.pow( machine.getSize(), machine.getDimension() ) + machine.getSize() )/2 - 1+ machine.getSize() , 1);
	
			machine.setCell( (int) ( Math.pow( machine.getSize(), machine.getDimension() ) + machine.getSize() )/2 + 2+ 2*machine.getSize() , 1);
			machine.setCell( (int) ( Math.pow( machine.getSize(), machine.getDimension() ) + machine.getSize() )/2 + 1 +2*machine.getSize() , 1);
			machine.setCell( (int) ( Math.pow( machine.getSize(), machine.getDimension() ) + machine.getSize() )/2 +2*machine.getSize() , 1);
			machine.setCell( (int) ( Math.pow( machine.getSize(), machine.getDimension() ) + machine.getSize() )/2 - 1+2*machine.getSize() , 1);
	
			machine.setCell( (int) ( Math.pow( machine.getSize(), machine.getDimension() ) + machine.getSize() )/2 + 2+ 3*machine.getSize() , 1);
			machine.setCell( (int) ( Math.pow( machine.getSize(), machine.getDimension() ) + machine.getSize() )/2 + 1 +3*machine.getSize() , 1);
			machine.setCell( (int) ( Math.pow( machine.getSize(), machine.getDimension() ) + machine.getSize() )/2 +3*machine.getSize() , 1);
			machine.setCell( (int) ( Math.pow( machine.getSize(), machine.getDimension() ) + machine.getSize() )/2 - 1+3*machine.getSize() , 1);
			//*/
	
	
				/*//good for lichens
				machine.setCell( (int) ( Math.pow( machine.getSize(), machine.getDimension() ) + machine.getSize() )/2, 1);
				machine.setCell( (int) ( Math.pow( machine.getSize(), machine.getDimension() ) + machine.getSize()+2 )/2 - machine.getSize(), 1);
				machine.setCell( (int) ( Math.pow( machine.getSize(), machine.getDimension() ) + machine.getSize()+2 )/2 + machine.getSize()-1, 1);
				machine.setCell( (int) ( Math.pow( machine.getSize(), machine.getDimension() ) + machine.getSize()+2 )/2 + machine.getSize()-3, 1);
				machine.setCell( (int) ( Math.pow( machine.getSize(), machine.getDimension() ) + machine.getSize()+8 )/2 + machine.getSize()-3, 1);
				machine.setCell( (int) ( Math.pow( machine.getSize(), machine.getDimension() ) + machine.getSize()+4 )/2, 1);
				machine.setCell( (int) ( Math.pow( machine.getSize(), machine.getDimension() ) + machine.getSize()+6 )/2, 1);
				//*/		
	
				CellSpaceWindow cellWindow = new CellSpaceWindow( machine );
				OptionsWindow optionsWindow = new OptionsWindow( this.fileName, cellWindow );
				
				this.frmSetup.setVisible( false );
				optionsWindow.setVisible( true );
				cellWindow.setVisible( true );
				
				
			} catch(Exception e){
				e.printStackTrace();
				System.out.println("Some error.");
			}
		} else {
			System.out.println("Must Select File");
		}
	}

	private void openButtonOnAction(){
		JFileChooser chooser = new JFileChooser(  System.getProperty("user.dir") );
		int returnVal = chooser.showOpenDialog( frmSetup );
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			this.filePath = chooser.getSelectedFile().getPath();
			this.textField.setText( this.filePath );
			
			int startIndex = this.filePath.lastIndexOf("\\")+1;
			int endIndex = this.filePath.contains(".")? this.filePath.indexOf('.') : this.filePath.length() ;
			//System.out.println( startIndex +", "+endIndex);
			this.fileName = this.filePath.substring( startIndex , endIndex) ;
			
		}


	}
}
