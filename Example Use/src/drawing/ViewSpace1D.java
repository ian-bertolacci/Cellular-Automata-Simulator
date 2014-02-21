package drawing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class ViewSpace1D extends ViewSpace {

	
	
	public ViewSpace1D( int size, int dimension, int colors, int cellEdgeSize) {
		super( size, dimension, colors, cellEdgeSize );
		this.cellSpaceTimeline = new LinkedList< int[] >();
		
		this.setPreferredSize( new Dimension(size*cellEdgeSize-10, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()-100 ) );
		//System.out.println( this.getPreferredSize().toString() );
	}

	public void update(int[] newCellSpace) {
		int[] copyCells = Arrays.copyOf( newCellSpace, newCellSpace.length );
		this.cellSpaceTimeline.add( copyCells );
	}

	@Override
	public void renderToImage( String fileName ) {
		int height = this.cellSpaceTimeline.size() * this.cellEdgeLength;
		int width = this.size * this.cellEdgeLength;
		
		BufferedImage canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB );
    	Graphics2D drawingSpace = canvas.createGraphics();
    	
    	int step = 0;
    	for( int[] lattice : this.cellSpaceTimeline ){
    		
			for(int x = 0, i=0; i < lattice.length; x += this.cellEdgeLength, i++){
				drawingSpace.setColor( pallet[ lattice[i] ] );
				drawingSpace.fillRect(x, step*cellEdgeLength, cellEdgeLength, cellEdgeLength);
	
	    	}
			
			++step;
    	}
    	drawingSpace.dispose();
    	canvas.flush();
    	
		try{
	    	ImageIO.write( canvas, "png", new File( fileName ) );
	    	
		} catch (IOException e ){
			System.err.println("There was an error writing file "+ fileName);
	    	
		}
		
	}
	

	
	public void paint( Graphics g ){
		//Draw background
		if( System.getProperty("os.name").contains("Windows") ){
			if( this.cellSpaceTimeline.size() > 0 ){
	
				int step = this.cellSpaceTimeline.size();
				int[] lattice = this.cellSpaceTimeline.get( step-1 );
					
				for(int x = 0, i=0; i < lattice.length; x += this.cellEdgeLength, i++){
		    		g.setColor( pallet[ lattice[i] ] );
		    		
		    		
		    		g.fillRect(x+3, step*cellEdgeLength+21, cellEdgeLength, cellEdgeLength);
		
		    	}
				
				++step;
				
			} else {
				System.out.println("Start");
				g.setColor( Color.GREEN );
				g.fillRect(0, 0, 100000, 100000);
				
			}
			
		} else if(System.getProperty("os.name").equalsIgnoreCase("linux")) {
			if( this.cellSpaceTimeline.size() > 0 ){
				
				int step = this.cellSpaceTimeline.size();
				int[] lattice = this.cellSpaceTimeline.get( step-1 );
					
				for(int x = 0, i=0; i < lattice.length; x += this.cellEdgeLength, i++){
		    		g.setColor( pallet[ lattice[i] ] );
		    		
		    		
		    		g.fillRect(x+1, step*cellEdgeLength+31, cellEdgeLength, cellEdgeLength);
		
		    	}
				
				++step;
				
			} else {
				System.out.println("Start");
				g.setColor( Color.GREEN );
				g.fillRect(0, 0, 100000, 100000);
				
			}
			
		}
		
	}
	

}
