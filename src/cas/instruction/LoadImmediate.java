package instruction;

public class LoadImmediate  extends Instruction{

	private int destinationRegister;
	private Object value;
	
	public LoadImmediate(){
		this.type = InstructionType.LDIMM;
	}
	
	public LoadImmediate(int destinationRegister, Object value){
		this();
		this.setDestination( destinationRegister );
		this.setValue( value );
	}
	
	public int getDestination(){
		return this.destinationRegister;
	}
	
	public Object getValue(){
		return this.value;
	}
	
	
	public void setDestination( int register ){
		this.destinationRegister = register;
	}
	
	public void setValue( Object value ){
		this.value = value;
	}
	
	public String toString() {
		return "#"+ value.toString()+" -> R" + this.destinationRegister;
	}
	

	
	

}
