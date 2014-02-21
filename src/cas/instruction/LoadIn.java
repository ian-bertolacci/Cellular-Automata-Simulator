package cas.instruction;


public class LoadIn extends Instruction{

	private int sourceIndex;
	private int offsetRegister;
	private int destinationRegister;

	public LoadIn(){
		this.type = InstructionType.LOADIN;
	}

	public LoadIn( int sourceIndex, int offsetReg, int destReg){
		this();
		this.setSource( sourceIndex );
		this.setOffset( offsetReg );
		this.setDestination( destReg );
	}

	public int getSource(){
		return this.sourceIndex;
	}

	public int getOffset(){
		return this.offsetRegister;
	}
		
	public int getDestination(){
		return this.destinationRegister;
	}


	public void setSource( int memoryLocation ){
		this.sourceIndex = memoryLocation;
	}
	
	public void setOffset( int register ){
		this.offsetRegister = register;
	}
	
	public void setDestination( int register ){
		this.destinationRegister = register;
	}

	public String toString() {
		return "Load [" + this.sourceIndex +"] + R"+this.offsetRegister+ " -> R"+this.destinationRegister;		
	}



}
