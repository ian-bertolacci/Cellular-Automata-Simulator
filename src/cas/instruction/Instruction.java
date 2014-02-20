package instruction;

public abstract class Instruction {
	public enum InstructionType { ALLOCARRAY, BREAK, HALTRET, JUMP, LOAD, LOADIN, LDIMM, STORE, STOREIN, OPERATOR };
	InstructionType type;
	
	public InstructionType getType(){
		return this.type;
	}
	
	public abstract String toString();
	

}
