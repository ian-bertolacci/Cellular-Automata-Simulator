package parser.parser_containers;

import java.util.Iterator;
import java.util.Stack;
import machine.VirtualMachine.VarType;

public class SymbolStack {

	Stack< Label > labels;
	Stack< Integer > scopeFrames;
	int depth;
	
	public SymbolStack(){
		this.labels = new Stack< Label >();
		this.scopeFrames = new Stack<Integer>();
		this.depth = 0;
	}
	
	public int push( String name, VarType type ) throws RuntimeException{	//adds new label to the stack
		return this.push(name, type, 1);
	}
	
	public int push( String name, VarType type, int size ) throws RuntimeException{
		if(this.contains(name)) throw new RuntimeException("Attempting to re-add \""+ name+"\"");
		if(size <= 0) throw new RuntimeException(name + " must occupy a positive amount of space: "+size);
		int position = this.depth;
		labels.push( new Label(name, position, type ) );
		++this.depth;
		
		return position;
	}
	
	public void newScope(){
		this.scopeFrames.push(this.labels.size());
	}
	
	public int pop(){	
		int popTop = this.scopeFrames.pop().intValue();
		while(this.labels.size() > popTop){
			this.labels.pop();
			--this.depth;
		}
		return this.labels.size()-1;
	}
	
	public boolean contains(String name){
		Iterator<Label> iter = this.labels.iterator();
		while(iter.hasNext()){
			if( iter.next().equals(name) ) return true;
		}
		return false;
	}
	
	public Label getLabel(String name) throws RuntimeException{
		Iterator<Label> iter = this.labels.iterator();
		Label get = null;
		while(iter.hasNext()){
			get = iter.next();
			if( get.getName().equals(name) ) break; 
		}
		if( get == null || !get.getName().equals( name ) ) throw new RuntimeException("No such variable \""+ name+"\"");
		return get;
	}
	
	public String toString(){
		return "Lables: " + labels.toString() + "\nScopes: " + this.scopeFrames.toString();
	}
}
