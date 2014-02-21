package cas.parser.parser_containers;

import java.util.ArrayList;
import java.util.Iterator;

import cas.instruction.Instruction;



public class CodeList {

	protected ArrayList< Instruction > instructionList;
	
	public CodeList() {
		this.instructionList = new ArrayList< Instruction >();
	}
	
	public CodeList( CodeList otherList){
		this.setInstructions( otherList.getInstructionList() );
	}
	
	public CodeList( ArrayList< Instruction > instructionArray ){
		this.setInstructions( instructionArray );
	}
	
	
	public ArrayList< Instruction > getInstructionList(){
		return this.instructionList;
	}
	
	public Instruction[] toArray(){
		Instruction[] array = new Instruction[instructionList.size()];
		Iterator<Instruction> iter = instructionList.iterator();
		for(int i = 0; iter.hasNext(); ++i){
			array[i] = iter.next();
		}
		
		return array;
	}
	
	public Instruction get(int index ){
		return this.instructionList.get(index);
	}
	
	public int size(){
		return this.instructionList.size();
	}
	
	public void append( Instruction instruction ){
		this.instructionList.add(instruction);
	}
	
	public void append( ArrayList<Instruction> instructions){
		Iterator<Instruction> iter = instructions.iterator();
		while(iter.hasNext()){
			this.append(iter.next());
		}
	}
	
	public void append( CodeList list ){
		this.append(list.getInstructionList());
	}
	
	public void prepend( Instruction instruction ){
		ArrayList< Instruction > newList = new ArrayList< Instruction >( this.instructionList.size()+1);
		newList.add(instruction);
		Iterator<Instruction> iter = this.instructionList.iterator();
		while(iter.hasNext()){
			newList.add(iter.next());
		}
		this.instructionList = newList;
	}
	
	public void prepend( ArrayList<Instruction> instructions ){
		Iterator<Instruction> iter = this.instructionList.iterator();
		while(iter.hasNext()){
			instructions.add(iter.next());
		}
		this.instructionList = instructions;
	}
	
	public void setInstructions( ArrayList<Instruction> instructions ){
		this.instructionList = instructions;
	}
	
	public void set(int index, Instruction element ){
		this.instructionList.set(index, element);
		//this.instructionList.s
	}

	public String toString(){
		String result = "";
		for( Iterator<Instruction> iter = this.getInstructionList().iterator(); iter.hasNext(); ){
			result += iter.next().toString()+"\n";
		}
		return result;
	}

}
