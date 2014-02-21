package cas.instruction;

public class Operator extends Instruction{

	public enum Operation { PLUS, MINUS, TIMES, DIVIDE, MOD, EQ, NEQ, LT, LE, AND, OR, NOT };
	
	private Operation operator;
	private int sourceRegister1;
	private int sourceRegister2;
	private int destinationRegister;
	
	public Operator(){
		this.type = InstructionType.OPERATOR;
	}
	
	public Operator( Operation operator, int sourceReg1, int sourceReg2, int destReg ){
		this();
		this.setOperator( operator );
		this.setSource1( sourceReg1 );
		this.setSource2( sourceReg2 );
		this.setDestination( destReg );
	}
	
	public int getSource1(){
		return this.sourceRegister1;
	}
	
	public int getSource2(){
		return this.sourceRegister2;
	}	
	
	public int getDestination(){
		return this.destinationRegister;
	}
	
	public Operation getOperator(){
		return this.operator;
	}
	
	
	public void setSource1( int register ){
		this.sourceRegister1 = register;
	}
	
	public void setSource2( int register ){
		this.sourceRegister2 = register;
	}
	
	public void setDestination( int register ){
		this.destinationRegister = register;
	}
	
	public void setOperator( Operation operator ){
		this.operator = operator;
	}
	
	public String toString() {
		return	"Operator("+this.operator+") R"+this.sourceRegister1+" R"+this.sourceRegister2+" := R"+this.destinationRegister;
	}

}
