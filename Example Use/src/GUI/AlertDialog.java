package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.Box;


public class AlertDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel message_Label;
	private String message;
	
	private void initComponents() {
		setBounds(100, 100, 180, 114);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		message_Label = new JLabel( message );
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
			
			contentPanel.add( message_Label );
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						okButton_ActionPerformed( arg0 );
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				Component horizontalStrut = Box.createHorizontalStrut(50);
				buttonPane.add(horizontalStrut);
			}
		}
	}
	
	private void okButton_ActionPerformed( ActionEvent evt ){
		this.dispose();
	}
	
	public AlertDialog(String alertMessage ){
		this.message = alertMessage;
		this.initComponents();
	}
	
	

}
