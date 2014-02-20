package instruction;

public class Store extends Instruction{

	int sourceRegister;
	int destinationIndex;
	
	public Store(){
		this.type = InstructionType.STORE;
	}
	
	public Store(int sourceReg, int destIndex){
		this();
		this.setSource( sourceReg );
		this.setDestination( destIndex );
	}
	
	public void setSource( int register ){
		this.sourceRegister = register;
	}
	
	public void setDestination( int index ){
		this.destinationIndex = index;
	}
	
	public int getSource(){
		return this.sourceRegister;
	}
	
	
	public int getDestination(){
		return this.destinationIndex;
	}
	
	public String toString() {
		return "Store R"+this.sourceRegister+" -> ["+this.destinationIndex+"]";
	}

}
