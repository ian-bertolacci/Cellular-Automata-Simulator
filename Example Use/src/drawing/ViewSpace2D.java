package drawing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class ViewSpace2D extends ViewSpace {

	public ViewSpace2D( int size, int dimension, int colors, int cellEdgeSize) {
		super( size, dimension, colors, cellEdgeSize );
		this.cellSpaceTimeline = new LinkedList< int[] >();
		
		this.setPreferredSize( new Dimension( size*cellEdgeSize -10, size*cellEdgeSize-10) );
	}
	
	
	public void update(int[] newCellSpace) {
		this.cellSpaceTimeline.clear();
		int cells = (int) Math.pow( this.size, this.dimension );

		if( cells < newCellSpace.length ){
			throw new RuntimeException( "Given cell space has more cells (" + newCellSpace.length + ") than expected (" + cells );
			
		}else if( cells > newCellSpace.length ){
			throw new RuntimeException( "Given cell space has less cells (" + newCellSpace.length + ") than expected (" + cells );
			
		}
		
		for( int i = 0; i < cells; i += size ){
			this.cellSpaceTimeline.add( Arrays.copyOfRange(newCellSpace, i, i+size) );
			
		}
		
	}

	@Override
	public void renderToImage( String fileName ) {
		int height = this.size * this.cellEdgeLength;
		int width = height;
		
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
		
		if( System.getProperty("os.name").contains("Windows") ){
			
			int step = 0;
			for( int[] lattice : this.cellSpaceTimeline ){ //get a row of cell space
								
				for(int x = 0, i=0; i < lattice.length; x += this.cellEdgeLength, i++){
		    		g.setColor( pallet[ lattice[i] ] );
		    		
		    		
		    		g.fillRect(x+3, step*cellEdgeLength+26, cellEdgeLength, cellEdgeLength);
		
		    	}
				
				++step;
			}
			
		} else if(System.getProperty("os.name").equalsIgnoreCase("linux")) { 
						
			int step = 0;
			for( int[] lattice : this.cellSpaceTimeline ){ //get a row of cell space
								
				for(int x = 0, i=0; i < lattice.length; x += this.cellEdgeLength, i++){
		    		g.setColor( pallet[ lattice[i] ] );
		    		
		    		
		    		g.fillRect(x+1, step*cellEdgeLength+32, cellEdgeLength, cellEdgeLength);
		
		    	}
				
				++step;
			}
				

			
		}
		
	}// end paint()
	

}
