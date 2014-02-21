/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 *
 * @author Ian Bertolacci
 */
public class OptionsWindow extends JFrame {

    //View decs                   
    private JLabel delay_Label;
    private JCheckBox drawEachUpdate_CheckBox;
    private JSpinner drawTime_Spinner;
    private JLabel steps_Label;
    private JSpinner steps_Spinner;
    private JButton stop_Button;
    private JButton update_Button;
	private JButton printToImage_Button;
	
	private CellSpaceWindow actingWindow;
	boolean stop = false;
	private JTextField fileName_TextField;
	private int ranSteps;
	private String fileName;
	
    public OptionsWindow( String fileName, CellSpaceWindow actingWindow ) {
    	super("Command Window");
    	setResizable(false);
    	this.actingWindow = actingWindow;
    	initComponents();
    	this.setLocation(0, 45);
    	this.ranSteps = 0;
    	
    	this.fileName = fileName;
    	this.fileName_TextField.setText( this.fileName );
    }

    private void initComponents() {

        update_Button = new JButton();
        steps_Spinner = new JSpinner();
        steps_Label = new JLabel();
        drawEachUpdate_CheckBox = new JCheckBox();
        delay_Label = new JLabel();
        drawTime_Spinner = new JSpinner();
        stop_Button = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        update_Button.setText("Update");
        update_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_ButtonActionPerformed(evt);
            }
        });

        steps_Spinner.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

        steps_Label.setText("Steps");

        drawEachUpdate_CheckBox.setSelected(true);
        drawEachUpdate_CheckBox.setText("Draw Each Step");
        drawEachUpdate_CheckBox.setHorizontalTextPosition(SwingConstants.LEADING);
       
        drawEachUpdate_CheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drawEachUpdate_CheckBoxActionPerformed(evt);
            }
        });

        delay_Label.setText("Delay (Miliseconds)");

        drawTime_Spinner.setModel(new SpinnerNumberModel(1, 1, 1000, 1));

        stop_Button.setText("Cancel");
        stop_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stop_ButtonActionPerformed(evt);
            }
        });
        
        printToImage_Button = new JButton("Draw To Image");
        printToImage_Button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt ) {
        		printToImage_ButtonActionPerformed( evt );
        	}
        });
        
        fileName_TextField = new JTextField();
        fileName_TextField.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent arg0) {
        		fileName_TextField.setText("");
        	}
        });
        fileName_TextField.setText("File Name");
        fileName_TextField.setColumns(10);

        GroupLayout layout = new GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(delay_Label)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(drawTime_Spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(update_Button)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(stop_Button))
        						.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
        							.addGroup(Alignment.LEADING, layout.createSequentialGroup()
        								.addComponent(steps_Label)
        								.addPreferredGap(ComponentPlacement.RELATED)
        								.addComponent(steps_Spinner))
        							.addComponent(drawEachUpdate_CheckBox, Alignment.LEADING)))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(fileName_TextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(printToImage_Button))))
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(update_Button)
        				.addComponent(stop_Button)
        				.addComponent(printToImage_Button))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(steps_Spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(fileName_TextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(steps_Label))
        			.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addComponent(drawEachUpdate_CheckBox)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(delay_Label)
        				.addComponent(drawTime_Spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(28))
        );
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>                        

    private void drawEachUpdate_CheckBoxActionPerformed(java.awt.event.ActionEvent evt) {                                                        
        this.drawTime_Spinner.setEnabled( this.drawEachUpdate_CheckBox.isSelected() );
	
    }                                                       

    public void printToImage_ButtonActionPerformed( ActionEvent evt ){
    	String fileName = this.fileName_TextField.getText();
    	if( fileName.equals("File Name") ){
    		AlertDialog alert = new AlertDialog("Please enter a file name.");
    		alert.setVisible( true );
    	} else {
    		this.actingWindow.cellViewSpace.renderToImage( fileName+"_"+this.ranSteps+".png" );
    		
    	}
    }

    private void update_ButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	int steps = ( (Integer) steps_Spinner.getValue() ).intValue();
    	boolean repaint = (steps == 1) || drawEachUpdate_CheckBox.isSelected();
    	int sleep = ( (Integer) drawTime_Spinner.getValue()).intValue();
    	
    	this.update_Button.setEnabled( false );
    	this.printToImage_Button.setEnabled( false );
    	new UpdateThread( steps, repaint, sleep ).start();
    	
    	
    }                                             

    private void stop_ButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
        this.stop = true;
    }                                           

   
    private class PaintThread extends Thread {
    	
    	final boolean repaint;
    	final int sleep;
    	PaintThread( boolean repaint, int sleep ){
    		super();
    		this.repaint = repaint;
    		this.sleep = sleep;
    	}
    	
		public void run() {
			
			actingWindow.updateSpace();
			if( repaint ) actingWindow.updateView();
			synchronized( actingWindow.getGraphics() ){
				if( repaint ){
					actingWindow.paint( actingWindow.getGraphics() );
				}
				++ranSteps;
				try{
					sleep( this.sleep );
				}catch( Exception expt ){
					System.out.println("Error Sleeping");
					expt.printStackTrace();
				}
				
				
			}//synchronized
		}// run()
		
	}// PaintThread
    
    public class UpdateThread extends Thread {
    	
    	private final int steps;
    	private final boolean repaint;
    	private final int sleep;
    	
    	public UpdateThread(int steps, boolean doRepaint, int sleep ){
    		super();
    		this.steps = steps;
    		this.repaint = doRepaint;
    		this.sleep = sleep;
    		
    	}
    	
		public void run(){
	    	stop = false;			
	    	for( int i = 0; i < steps; ++i){
	    		//System.out.println(i);
	    		PaintThread thread = new PaintThread( this.repaint, this.sleep );
	    		thread.run();
	    		if( stop ){ 
	    			stop = false;
	    			break;
	    		}
	    	}
	    	
	    	if( !repaint){
	    		actingWindow.updateView();
	    		actingWindow.repaint();
	    	}
	    	printToImage_Button.setEnabled( true );
	    	update_Button.setEnabled( true );
		}
		
	}// Update Thread
}
