package automata;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import machine.VirtualMachine;
import tools.Tools;
public class Automata {

	int[] cellSpace;
	int cells;
	int size;
	int dimension;
	
	int states;
	int radius;
	int neighborhoodSize;
	int radialBounds;
	
	int[][] neighborhoodCoordinateSpace;
	
	VirtualMachine VM;
	
	
	
	public Automata(int edgeSize, int dimension, int radius, int states, VirtualMachine virtualMachine){
		this.size = edgeSize;
		this.dimension = dimension;
		this.cells = (int) Math.pow(size,dimension);
		this.cellSpace = new int[cells];
		
		this.radius = radius;
		this.neighborhoodSize = (int) Math.pow(2*this.radius +1,this.dimension); 
		this.radialBounds = (int) Math.floor( this.neighborhoodSize / 2 );
		
		this.states = states;
		
		this.buildNeigborhoodCoordinates();
		
		this.VM = virtualMachine;
		
			
	}
	
	public void buildNeigborhoodCoordinates(){
		this.neighborhoodCoordinateSpace = new int[this.cells][this.neighborhoodSize]; //collected offsets of all indexes of all neighborhoods of all cells
		
		for( int index = 0; index < cells; ++index ){ //iterate through all cells in cell space
			int[] neigborhood = new int[neighborhoodSize]; //collected indices for neighborhood
			int[] basedIndex = Tools.deciToBase(index, this.size, this.dimension); //index to space-index-vector
			int[] offSet = new int[this.dimension]; //offset-neigborhood space vector, seeded at (-r,...-r); ie starting at one corner of the neigborhood
			int[] correctIndex = new int[this.dimension]; //vector-index into cells space
			Arrays.fill(offSet, -radius);

			for( int i = 0; i < Math.pow(2*this.radius+1, this.dimension); ++i ){ //iterate through the vector-digit rotation of offSet

				for( int k = 0; k < this.dimension; ++k){ //vector addition of offSet and basedIndex into correctedIndex vector;
					correctIndex[k] = (offSet[k] + basedIndex[k] ) % this.size; //digit mod warping
					if(correctIndex[k] < 0 ) correctIndex[k] = size + correctIndex[k]; //inversion warping
				}
				
				int correctedIndex = Tools.baseToDeci(correctIndex, this.size); 	//conversion from vector-index to real index
				neigborhood[i] = correctedIndex; //put index into neighborhood indices collection
				offSet[0] += 1; //add to lowest magnitude

				for( int k = 0; offSet[k] > radius && i != Math.pow(2*this.radius+1, this.dimension)-1 ; ++k){ //do carry, only when nessecary: A carry, or series of carryes, only occurs when [0] carries.
					offSet[k] = -radius; //reset to lowest (rotate digit)
					offSet[k+1] += 1; //perform carry
				}
			}
			
			this.neighborhoodCoordinateSpace[index] = neigborhood; //set this neighborhoods indices into the global collection
		}
	}
	
	
	public Integer[] getNeigborhood(int index){
		int[] coordinates = this.neighborhoodCoordinateSpace[index];	//Grab the coordinate space for this indici's neighborhood
		Integer[] neigborhood = new Integer[this.neighborhoodSize];		//Local store for this indici's neighborhood states
		for( int i = 0; i < this.neighborhoodSize; ++i ){				//Iterate through neighborhood's coordinate space
			neigborhood[i] = this.cellSpace[ coordinates[i] ]; 			//Store neighborhood from the mesh to the local neighborhood
		}
		return neigborhood;	//Return the neighborhood
			
	}
	

	public int updateNeigborhood(Integer[] updateNeighborhood){
		this.VM.pushMemory( updateNeighborhood);
		Object ret = this.VM.execute();
		this.VM.reset();
		return ( (Integer) ret ).intValue(); 
	}
	
	public void update(){
		int[] nextCellSpace = new int[cells];
		for(int i = 0; i < this.cells; ++i){
			nextCellSpace[i] = this.updateNeigborhood( this.getNeigborhood(i) );
		}
		this.cellSpace = nextCellSpace;
	}
	
	public void update(int steps){
		for(int i = 0; i < steps; ++i){
			this.update();
		}
	}
	
	public void setCell(int index, int state){
		if(state >= this.states ) throw new RuntimeException("State must be < the number of defined states (0..." + (this.states-1) + ")" );
		this.cellSpace[index] = state;
	}
	
	
	public void generateRandomConfiguration(){
		Random generator =  new Random();
		for(int i = 0; i < cells; ++i){
			this.setCell(i,Math.abs( generator.nextInt() % this.states ));
		}
	}
	
	public String toString(){
		return Arrays.toString( this.cellSpace );
	}
	
	public void setConfiguration(int[] configuration){
		if(configuration.length != this.cells) throw new RuntimeException("Configuration must have exactly " + this.cells + "cells");
		this.cellSpace = configuration;
	}
	
	public int getSize(){
		return this.size;
	}
	
	public int getNumberCells(){
		return this.cells;
	}
	
	public int getDimension(){
		return this.dimension;
	}
	
	public int getStates(){
		return this.states;
	}
	
	public int[] getCellSpace(){
		return Arrays.copyOf( this.cellSpace, this.cellSpace.length);
	}
	
	
	

}
