package cas.machine;

import java.util.Arrays;

import cas.instruction.*;
import cas.instruction.Break.Condition;
import cas.parser.parser_containers.CodeList;
import cas.parser.parser_containers.IndexableStack;

public class VirtualMachine {

	public enum VarType { INT, REAL, BOOLE };
	
	private IndexableStack<Object> memory;
	private Object registers[];
	private Instruction instructions[];
	int currentInstruction;
	private boolean halt;
	
	public VirtualMachine(){
		instructions = null;
		memory = new IndexableStack<Object>();
		registers = new Object[32];
		this.currentInstruction = 0;
		this.halt = false;
	}
	
	public VirtualMachine( CodeList list ){
		this();
		this.instructions = list.toArray();
	}
	
	public VirtualMachine(Instruction instructions[]){
		this();
		this.instructions = instructions;
	}
	
	public Object execute(){
		Object ret = null;
		while( (this.currentInstruction < this.instructions.length) && !halt ){
			ret = step();
		}
		return ret;
	}
	
	
	public Object step() throws RuntimeException { 
		if(this.halt) throw new RuntimeException("Machine is haulted.");
		Instruction instr = this.instructions[this.currentInstruction];
		++this.currentInstruction;
		
		switch( instr.getType() ){
			
			case ALLOCARRAY: //Allocate array instruction
				AllocateArray aaInstr = (AllocateArray) instr;
				while( this.memory.poppable() < aaInstr.location() ){
					this.memory.push(null);
				}
				int size = ((Integer)this.registers[aaInstr.sizeRegister()]).intValue();
				this.memory.set(aaInstr.location(), new Object[ size ] );
				break;
				
							
			case BREAK: //Break instruction
				Break bInstr = (Break) instr; 
				Condition condition = bInstr.getCondition();
				boolean actual =  (bInstr.getSource() != -1)?( (Boolean) this.registers[bInstr.getSource()] ).booleanValue():false;
				if( (condition == Condition.UNCONDITIONAL) || ( actual && condition == Condition.TRUE) || ( !actual && condition == Condition.FALSE) ){
					this.currentInstruction += bInstr.getOffset();
				}
				break;
			
			case HALTRET: //Halt return instruction
				this.halt = true;
				HaltReturn hrInstr = (HaltReturn) instr;
				return this.registers[hrInstr.returnRegister()];
				
			case JUMP: //Jump instruction
				Jump jInstr = (Jump) instr;
				this.currentInstruction = ((Integer) jInstr.getBaseRegister() ).intValue();
				break;
				
			case LOAD: //Load from memory instruction
				Load lInstr = (Load) instr;
				this.registers[lInstr.getDestination()] = this.memory.get( lInstr.getSource() );
				break;
				
			case LDIMM: //Load immediate instruction
				LoadImmediate limmInstr = (LoadImmediate) instr;
				this.registers[limmInstr.getDestination()] = limmInstr.getValue();
				break;
				
			case LOADIN:
				LoadIn liInstr = (LoadIn) instr;
				Object[] ldArray = (Object[]) this.memory.get( liInstr.getSource() );
				int offSet = ((Integer) this.registers[liInstr.getOffset()]).intValue();
				this.registers[liInstr.getDestination()] = ldArray[offSet];
				break;
				
			case OPERATOR:{ //Operator instruction
				Operator oInstr = (Operator) instr;
				Object valueSR1 = this.registers[oInstr.getSource1()];
				Object valueSR2 = this.registers[oInstr.getSource2()];
				int destReg = oInstr.getDestination();
				switch(oInstr.getOperator()){
					
					case PLUS:{ //Operator + instruction
						if( valueSR1 instanceof Integer && valueSR2 instanceof Integer ){
							this.registers[destReg] = new Integer( ((Integer) valueSR1).intValue() + ((Integer) valueSR2).intValue() );
						} else if( valueSR1 instanceof Double && valueSR2 instanceof Double ){
							this.registers[destReg] = new Double( ((Double) valueSR1).doubleValue() + ((Double) valueSR2).doubleValue() );
						} else {
							throw new RuntimeException( "Unexpected Type(s): " + valueSR1.getClass() +"\n"+valueSR2.getClass() );
						}
						break;
					}
					
					case MINUS:{	//Operator - instruction
						if( valueSR1 instanceof Integer && valueSR2 instanceof Integer ){
							this.registers[destReg] = new Integer( ((Integer) valueSR1).intValue() - ((Integer) valueSR2).intValue() );
						} else if( valueSR1 instanceof Double && valueSR2 instanceof Double ){
							this.registers[destReg] = new Double( ((Double) valueSR1).doubleValue() - ((Double) valueSR2).doubleValue() );
						} else {
							throw new RuntimeException( "Unexpected Type(s): " + valueSR1.getClass() +"\n"+valueSR2.getClass() );
						}
						break;
					}
					
					case TIMES:{	//Opeartor * instruction
						if( valueSR1 instanceof Integer && valueSR2 instanceof Integer ){
							this.registers[destReg] = new Integer( ((Integer) valueSR1).intValue() * ((Integer) valueSR2).intValue() );
						} else if( valueSR1 instanceof Double && valueSR2 instanceof Double ){
							this.registers[destReg] = new Double( ((Double) valueSR1).doubleValue() * ((Double) valueSR2).doubleValue() );
						} else {
							throw new RuntimeException( "Unexpected Type(s): " + valueSR1.getClass() +"\n"+valueSR2.getClass() );
						}
						break;					}
					
					case DIVIDE:{	//Operator / instruction
						if( valueSR1 instanceof Integer && valueSR2 instanceof Integer ){
							this.registers[destReg] = new Integer( ((Integer) valueSR1).intValue() / ((Integer) valueSR2).intValue() );
						} else if( valueSR1 instanceof Double && valueSR2 instanceof Double ){
							this.registers[destReg] = new Double( ((Double) valueSR1).doubleValue() / ((Double) valueSR2).doubleValue() );
						} else {
							throw new RuntimeException( "Unexpected Type(s): " + valueSR1.getClass() +"\n"+valueSR2.getClass() );
						}
						break;
					}
					
					case MOD:{		//Opeartor % instruction
						if( valueSR1 instanceof Integer && valueSR2 instanceof Integer ){
							this.registers[destReg] = new Integer( ((Integer) valueSR1).intValue() % ((Integer) valueSR2).intValue() );
						} else if( valueSR1 instanceof Double && valueSR2 instanceof Double ){
							this.registers[destReg] = new Double( ((Double) valueSR1).doubleValue() % ((Double) valueSR2).doubleValue() );
						} else {
							throw new RuntimeException( "Unexpected Type(s): " + valueSR1.getClass() +"\n"+valueSR2.getClass() );
						}
						break;
					}
					
					case EQ:{		//Operator == instruction
						Boolean value;
						if( valueSR1 instanceof Integer && valueSR2 instanceof Integer ){
							value = new Boolean( ((Integer) valueSR1).intValue() == ((Integer) valueSR2).intValue() );
						} else if( valueSR1 instanceof Double && valueSR2 instanceof Double ){
							value = new Boolean( ((Double) valueSR1).doubleValue() == ((Double) valueSR2).doubleValue() );
						} else if( valueSR1 instanceof Boolean && valueSR2 instanceof Boolean ){
							value = new Boolean( ((Boolean) valueSR1).booleanValue() == ((Boolean) valueSR2).booleanValue() );
						} else {
							throw new RuntimeException( "Unexpected Type(s): " + valueSR1.getClass() +"\n"+valueSR2.getClass() );
						}
						this.registers[destReg] = value;
						break;
					}
					
					case NEQ:{		//Operator != instruction
						Boolean value;
						if( valueSR1 instanceof Integer && valueSR2 instanceof Integer ){
							value = new Boolean( ((Integer) valueSR1).intValue() != ((Integer) valueSR2).intValue() );
						} else if( valueSR1 instanceof Double && valueSR2 instanceof Double ){
							value = new Boolean( ((Double) valueSR1).doubleValue() != ((Double) valueSR2).doubleValue() );
						} else if( valueSR1 instanceof Boolean && valueSR2 instanceof Boolean ){
							value = new Boolean( ((Boolean) valueSR1).booleanValue() != ((Boolean) valueSR2).booleanValue() );
						} else {
							throw new RuntimeException( "Unexpected Type(s): " + valueSR1.getClass() +"\n"+valueSR2.getClass() );
						}
						this.registers[destReg] = value;
						break;
					}
					
					case LE:{		//Operator <= instruction
						Boolean value;
						if( valueSR1 instanceof Integer && valueSR2 instanceof Integer ){
							value = new Boolean( ((Integer) valueSR1).intValue() <= ((Integer) valueSR2).intValue() );
						} else if( valueSR1 instanceof Double && valueSR2 instanceof Double ){
							value = new Boolean( ((Double) valueSR1).doubleValue() <= ((Double) valueSR2).doubleValue() );
						} else {
							throw new RuntimeException( "Unexpected Type(s): " + valueSR1.getClass() +"\n"+valueSR2.getClass() );
						}
						this.registers[destReg] = value;
						break;
					}
					
					case LT:{		//Operator < instruction
						Boolean value;
						if( valueSR1 instanceof Integer && valueSR2 instanceof Integer ){
							value = new Boolean( ((Integer) valueSR1).intValue() < ((Integer) valueSR2).intValue() );
						} else if( valueSR1 instanceof Double && valueSR2 instanceof Double ){
							value = new Boolean( ((Double) valueSR1).doubleValue() < ((Double) valueSR2).doubleValue() );
						} else {
							throw new RuntimeException( "Unexpected Type(s): " + valueSR1.getClass() +"\n"+valueSR2.getClass() );
						}
						this.registers[destReg] = value;
						break;
					}
					
					case AND:{		//Opeartor & instruction
						if( valueSR1 instanceof Boolean && valueSR2 instanceof Boolean ){
							this.registers[destReg] = new Boolean( ((Boolean) valueSR1).booleanValue() && ((Boolean) valueSR2).booleanValue() );
						}else {
							throw new RuntimeException( "Unexpected Type(s): " + valueSR1.getClass() +"\n"+valueSR2.getClass() +"\n@ "+instr.toString() );
						}
						break;
					}
					
					case OR:{		//Operator | instruction
						if( valueSR1 instanceof Boolean && valueSR2 instanceof Boolean ){
							this.registers[destReg] = new Boolean( ((Boolean) valueSR1).booleanValue() || ((Boolean) valueSR2).booleanValue() );
						}else {
							throw new RuntimeException( "Unexpected Type(s): " + valueSR1.getClass() +"\n"+valueSR2.getClass() );
						}
						break;
					}
					
					case NOT:{		//Operator ! instruction
						Boolean value;
						if( valueSR1 instanceof Boolean){
							value = new Boolean( !((Boolean) valueSR1).booleanValue());
						}
						else if( valueSR2 instanceof Boolean ){
							value = new Boolean( !((Boolean) valueSR2).booleanValue());
						}else {
							throw new RuntimeException( "Unexpected Type(s): " + valueSR1.getClass() +"\n"+valueSR2.getClass() );
						}
						this.registers[destReg] = value;
						break;
					}
				}//end switch(optype)
				
				break;
			}//end case OPERATION
			
			case STORE:		//Store to memory instruction
				Store sInstr = (Store) instr;
				while( this.memory.poppable() < sInstr.getDestination() ){
					this.memory.push(null);
				}
				this.memory.set( sInstr.getDestination(), this.registers[sInstr.getSource()] );
				break;
				
			case STOREIN:	//Store array instruction
				StoreIn siInstr = (StoreIn) instr;
				Object[] stArray = (Object[]) this.memory.get(siInstr.destination());
				stArray[ ((Integer)this.registers[siInstr.offset()]).intValue()] = this.registers[siInstr.source()];
				break;

			default:
				System.out.println( "Unexpected InstructionType \""+instr.getType()+"\"");
				break;
		}
		return null;
		
	}
	
	public void insertMemory(int i, Object element){
		this.memory.set(i,element);
	}
	
	public void pushMemory(Object element){
		this.memory.push(element);
	}
	
	public void reset(){
		this.halt = false;
		memory = new IndexableStack<Object>();
		registers = new Object[32];
		this.currentInstruction = 0;
	}
}
