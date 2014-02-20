package instruction;

public class Jump extends Instruction{

	private int baseReg;
	
	public Jump(){
		this.type = InstructionType.JUMP;
	}
	
	public Jump( int baseReg ) {
		this();
		this.setBaseRegister( baseReg );
	}
	
	public int getBaseRegister(){
		return this.baseReg;
	}
	
	public String toString() {
		return "jmp R"+this.baseReg;
	}

	
	public void setBaseRegister( int register ){
		this.baseReg = register;
	}
	

}
