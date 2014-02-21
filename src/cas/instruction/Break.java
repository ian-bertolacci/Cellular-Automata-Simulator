package cas.instruction;

public class Break extends Instruction{
	
	public enum Condition { TRUE, FALSE, UNCONDITIONAL };
	
	private Condition breakWhen;
	private int offset;
	private int sourceRegister;
	
	public Break(){ 
		this.type = InstructionType.BREAK;
	}
	
	public Break( Condition condition, int sourceReg, int jumpTo){
		this();
		this.setCondition( condition );
		this.setJumpIndex( jumpTo );
		this.setSouce( sourceReg );
	}
	
	public Condition getCondition(){
		return this.breakWhen;
	}
	
	public int getSource(){
		return this.sourceRegister;
	}
	
	public int getOffset(){
		return this.offset;
	}
	
	public String toString() {
		return "break R" +this.sourceRegister+" "+this.breakWhen +  "~> " + ( (this.offset >= 0)?"+"+this.offset:this.offset);
	}
	
	
	public void setCondition( Condition condition ){
		this.breakWhen = condition;
	}
	
	public void setJumpIndex( int offset ){
		this.offset = offset;
	}
	
	public void setSouce( int register ){
		this.sourceRegister = register;
	}

}
