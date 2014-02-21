package cas.parser.parser_containers;

import java.util.ArrayList;

public class IndexableStack<T> {

	protected ArrayList<T> stack;
	
	
	public IndexableStack() {
		this.stack = new ArrayList<T>();
	}
	
	public int push( T object ) throws RuntimeException{
		this.stack.add( object );
		return this.poppable();
	}
	
	public int pop(){
		this.stack.remove(this.stack.size()-1);
		return this.poppable();
	}
	
	public T get(int i){
		if( i > this.poppable() || i < 0 ){
			throw new RuntimeException(i + " is not a valid index; 0-"+this.poppable());
		}
		return this.stack.get(i);
	}
	
	public void set(int i, T element){
		if( i > this.poppable() || i < 0 ){
			throw new RuntimeException(i + " is not a valid index; 0..."+this.poppable());
		}
		this.stack.set(i, element);
	}
	
	public int poppable(){
		return this.stack.size()-1;
	}
	
	public String toString(){
		
		return null;
	}
}
