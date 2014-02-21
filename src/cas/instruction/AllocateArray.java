package cas.instruction;

public class AllocateArray extends Instruction{
	
	private int sizeRegister;
	private int location;
	
	public AllocateArray() { 
		this.type = InstructionType.ALLOCARRAY;
	}
	
	public AllocateArray( int location, int sizeRegister ){
		this();
		this.setLocation( location );
		this.setSizeRegister( sizeRegister );
	}

	public int sizeRegister(){
		return this.sizeRegister;
	}
	
	public int location(){
		return this.location;
	}
	
	public void setLocation( int index ){
		this.location = index;
	}
	
	public void setSizeRegister( int sizeRegister ){
		this.sizeRegister = sizeRegister;
	}
	
	public String toString() {
		return "NewArray R"+this.sizeRegister()+" "+this.location;
	}
	
}
