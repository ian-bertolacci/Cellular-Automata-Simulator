package cas.instruction;


public class Load  extends Instruction{

	private int sourceIndex;
	private int destinationRegister;
	
	public Load(){
		this.type = InstructionType.LOAD;
	}
	
	public Load( int sourceIndex, int destReg){
		this();
		this.setSource( sourceIndex );
		this.setDestination( destReg );
	}
	
	public int getSource(){
		return this.sourceIndex;
	}
	
	public int getDestination(){
		return this.destinationRegister;
	}
	
	
	public void setSource( int memoryLocation ){
		this.sourceIndex = memoryLocation;
	}
	
	public void setDestination( int register ){
		this.destinationRegister = register;
	}
	
	public String toString() {
		return "Load [" + this.sourceIndex +"] -> R"+this.destinationRegister;		
	}

}
