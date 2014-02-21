package cas.instruction;

public class StoreIn extends Instruction{

	private int sourceRegister;
	private int offsetRegister;
	private int destinationIndex;
	
	public StoreIn(){ 
		this.type = InstructionType.STOREIN;
	}
	
	public StoreIn(int sourceReg, int destIndex, int offsetReg){
		this();
		this.setSource( sourceReg );
		this.setDestination( destIndex );
		this.setOffset( offsetReg );
	}
	
	public void setSource( int register ){
		this.sourceRegister = register;
	}
	
	public void setOffset( int register ){
		this.offsetRegister = register;
	}
	
	public void setDestination( int index ){
		this.destinationIndex = index;
	}
	
	
	public int source(){
		return this.sourceRegister;
	}
	
	public int destination(){
		return this.destinationIndex;
	}
	
	public int offset(){
		return this.offsetRegister;
	}
	
	public String toString() {
		return "Store R"+this.sourceRegister+" -> ["+this.destinationIndex+"] + R"+this.offsetRegister;
	}

}
