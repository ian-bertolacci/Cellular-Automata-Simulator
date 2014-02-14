package parser.parser_containers;

import instruction.Instruction;
import java.util.ArrayList;
import java.util.Iterator;

import machine.VirtualMachine.VarType;

public class Expression extends CodeList{
	
	
	protected VarType evalType;
	protected int result;
	
	public Expression(){
		super();
		this.setResultLocation(-1);
	}
	
	public Expression( Expression otherExpression ){
		super(otherExpression.instructionList);
		this.setEvalType( otherExpression.evalType() );
		this.setResultLocation( otherExpression.getResultLocation() );
	}
	
	public Expression(ArrayList< Instruction > instructions, VarType evalType, int result){
		super(instructions);
		this.setEvalType(evalType);
		this.setResultLocation(result);
	}

	
	public int getResultLocation(){
		return this.result;
	}
	
	public boolean hasResult(){
		return (this.result == -1)?false:true;
	}
	
	public VarType evalType(){
		return this.evalType;
	}
	
	
	public void setResultLocation( int result){
		this.result = result;
	}
	
	public void nullResultLocation(){
		this.result = -1;
	}
	
	public void setEvalType( VarType type ){
		this.evalType = type;
	}
	
	
	
	public String toString(){
		String result = "";
		result += "Type: " +this.evalType() + "\n"+
		"ResultStore: " + this.getResultLocation() +"\n";
		result += super.toString();
		return result;
	}
	


}
