package drawing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class ImageProducer1D {

	public final static Color[] colorPallet = {Color.black, Color.blue, Color.red, Color.green, Color.yellow, Color.cyan, Color.ORANGE, Color.magenta, Color.pink };
	
	
	final private int cellEdgeLength;
	final private Color[] pallet;
	private BufferedImage canvas;
	private Graphics2D drawAble;

    public ImageProducer1D(int width, int height, int cellSize, int colors) {

    	
    	if(colors < 1 ){
    		throw new RuntimeException("Must have 1 or more colors");
    	} else if( colors > colorPallet.length+1 ){
    		
    		throw new RuntimeException("Too many colors, current pallet support is only for " + (colorPallet.length+1) + " colors.");
    		
    	} else {
    		pallet = Arrays.copyOf( this.colorPallet, colors);
    		pallet[ colors-1 ] = Color.white;
    	}
    	
    	this.cellEdgeLength = cellSize;
    	width = (width*cellEdgeLength);
    	height = (height*cellEdgeLength);
    	
    	canvas = new BufferedImage(width , height, BufferedImage.TYPE_INT_ARGB);
    	
    	drawAble = canvas.createGraphics();
    	drawAble.setColor(Color.yellow);

    	drawAble.fillRect(0, 0, width, height);
    	drawAble.setFont(new Font("Dialog", Font.PLAIN, 12));
    	
    	drawAble.dispose();
    	canvas.flush();

    }
    
    public void drawLattice(int[] lattice, int step){
    	drawAble= canvas.createGraphics();
    	for(int x = 0, i=0; i < lattice.length; x+=cellEdgeLength, i++){
    		drawAble.setColor( pallet[lattice[i]] );
    
    		drawAble.fillRect(x, step*cellEdgeLength, cellEdgeLength, cellEdgeLength);

    	}
    	
    	drawAble.dispose();
    	canvas.flush();
    }
    
    public void drawLattice(int[][] lattice){
    	drawAble= canvas.createGraphics();
    	for(int x = 0, i=0; i < lattice.length; x+=cellEdgeLength, i++){
    		for(int y = 0, j=0; j < lattice[i].length; y+=cellEdgeLength, j++){
    			
	    		drawAble.setColor( pallet[ lattice[i][j] ] );
	    
	    		drawAble.fillRect(x, y, cellEdgeLength, cellEdgeLength);

    		}
    	}
    	drawAble.dispose();
    	canvas.flush();
    }

    
    public void writeImage( String fileName ){

    	
		try{
	    	ImageIO.write(this.canvas, "png", new File(fileName+".png") );
		} catch (IOException e ){
			System.out.println("There was an error writing file " + fileName);
	    	System.exit(0);
		}
    }
    
    public Graphics getGraphics(){
    	return this.canvas.getGraphics();
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
