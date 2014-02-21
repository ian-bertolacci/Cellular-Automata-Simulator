package drawing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;

public abstract class ViewSpace extends JPanel{

	public final static Color[] universalPallet = {Color.black, Color.gray, Color.blue, Color.red, Color.green, Color.yellow, Color.cyan, Color.ORANGE, Color.magenta, Color.pink };

	public final int size;
	public final int dimension;
	public final Color[] pallet;
	
	protected int cellEdgeLength;
	protected Graphics myGraphics;
	protected List< int[] > cellSpaceTimeline;
	
	
	
	protected ViewSpace( int size, int dimension, int colors, int cellEdgeLength){
		
		//Check and set size
		if( size < 1){
			throw new RuntimeException("Spatial size must be greater than 0");
		}
		this.size = size;
		
		//Check and set dimension
		if( dimension < 1 ){
			throw new RuntimeException("Spatial dimension must be greater than 0");
		}
		this.dimension = dimension;
		
		//Use setCellEdgeLength() to check and set edgeLength
		this.setCellEdgeLength(cellEdgeLength);
		
		//Check and set color pallet
		if(colors < 1 ){
    		throw new RuntimeException("Must have 1 or more colors");
    		
    	} else if( colors > universalPallet.length+1 ){
    		throw new RuntimeException("Too many colors, current pallet support is only for " + ( universalPallet.length+1 ) + " colors.");
    		
    	} else {
    		pallet = Arrays.copyOf( this.universalPallet, colors);
    		pallet[ colors-1 ] = Color.white;
    		
    	}
		
		
		
	}
	
	
	public static ViewSpace buildViewSpace( int size, int dimension, int colors, int cellEdgeLength){
		if( dimension == 1){
			return new ViewSpace1D(size, dimension, colors, cellEdgeLength );
		} else if( dimension == 2 ){
			return new ViewSpace2D(size, dimension, colors, cellEdgeLength );
		} else {
			
			if( dimension > 2){
				throw new RuntimeException( "Cannot build a view space with more than 2 dimensions.");
			} else if( dimension < 1 ){
				throw new RuntimeException( "Cannot build a view space with less than 2 dimensions.");
			}
			
		}
		
		return null;
	}
	
	
	public void setCellEdgeLength( int edgeLength ){
		if( edgeLength < 1 ){
			throw new RuntimeException("Cell edge length must be greater than 0");
		}
		
		this.cellEdgeLength = edgeLength;
	}
	
	public abstract void update( int[] newCellSpace );
	public abstract void renderToImage( String fileName );
	

}
