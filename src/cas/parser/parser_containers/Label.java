package cas.parser.parser_containers;

import cas.machine.VirtualMachine.VarType;

public class Label {
	private int position;
	private String name;
	private VarType type;
	
	Label(String name, int position, VarType type){
		this.position = position;
		this.name = name;
		this.type = type;
	}
	
	public String toString(){
		return this.name + "["+this.position+"]";
	}
	
	public boolean equals( Object obj){
		System.out.println("OBJ eqls");
		if( obj instanceof Label ){
			return ((Label) obj).name.equals(this.name); 
		}
		else return false; 
	}
	
	public boolean equals( String name){
		return name.equals(this.name); 
	}
	
	public int getPosition(){
		return this.position;
	}
	
	public String getName(){
		return this.name;
	}
	
	public VarType getType(){
		return this.type;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
