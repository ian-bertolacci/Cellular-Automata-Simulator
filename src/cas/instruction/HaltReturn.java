package instruction;

public class HaltReturn extends Instruction {

	protected int returnReg;
	
	public HaltReturn(){
		this.type = InstructionType.HALTRET;
	}
	
	public HaltReturn( int returnRegister ){
		this();
		this.setReturnRegister( returnRegister );
	}
	
	public int returnRegister(){
		return this.returnReg;
	}
	
	public void setReturnRegister( int register ){
		this.returnReg = register;
	}
	
	public String toString() {

		return "HALTRETURN R" + this.returnReg;
	}



}
