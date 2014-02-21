package cas.parser.parser_containers;

import cas.automata.Automata;
import cas.machine.VirtualMachine;

public class PartialAutomata {
	private int size;
	private boolean setSize = false;
	
	private int states;
	private boolean setStates = false;
	
	private int dimension;
	private boolean setDimension = false;
	
	private int radius;
	private boolean setRadius = false;
	
	private CodeList assembly;
	private boolean setAssembly = false;
	
	public void setSize( int size ){
		this.size = size;
		this.setSize = true;
	}
	
	public int getSize(){
		return this.size;
	}
	
	public void setStates( int states ){
		this.states = states;
		this.setStates = true;
	}
	
	public int getStates(){
		return this.states;
	}
	
	public void setDimensions( int dimension ){
		this.dimension = dimension;
		this.setDimension = true;
	}
	
	public int getDimensions(){
		return this.dimension;
	}
	
	public void setRadius( int radius ){
		this.radius = radius;
		this.setRadius = true;
	}
	
	public int getRadius(){
		return this.radius;
	}
	
	public void setAssembly( CodeList assembly ){
		this.assembly = new CodeList( assembly );
		this.setAssembly = true;
	}
	
	public CodeList getAssembly(){
		return new CodeList( this.assembly );
	}
	
	public Automata createAutomata(){
		if( this.setAssembly && this.setDimension && this.setRadius && this.setSize && this.setStates ){
			return new Automata( this.getSize(), this.getDimensions(), this.getRadius(), this.getStates(), new VirtualMachine( this.getAssembly() ) );
		} else {
			return null;
		}
	}
	
}
