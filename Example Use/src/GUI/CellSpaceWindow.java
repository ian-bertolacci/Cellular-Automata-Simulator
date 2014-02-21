package GUI;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import drawing.ViewSpace;

import automata.Automata;

public class CellSpaceWindow  extends JFrame{
	

	ViewSpace cellViewSpace;
	private Automata machine;
	
	public CellSpaceWindow( Automata automata ){
		super( "Cell Space" );
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.setLayout( new FlowLayout() ); 
		
		this.machine = automata;
		
		cellViewSpace = ViewSpace.buildViewSpace( machine.getSize(), machine.getDimension(), machine.getStates(), 5);
		cellViewSpace.repaint();
		cellViewSpace.update( automata.getCellSpace() );
		
		add( cellViewSpace );
		this.setResizable( false );
		this.pack();
		this.setLocation(325, 45);
		
		
	}
	
	public void updateSpace(){
		this.machine.update();
	}
	
	public void updateView(){
		this.cellViewSpace.update( this.machine.getCellSpace() );
	}
	
	public void paint( Graphics g ){
		cellViewSpace.paint( g );
	}
	

}
