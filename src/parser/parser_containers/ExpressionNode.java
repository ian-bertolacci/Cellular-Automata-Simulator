package parser.parser_containers;

import instruction.Instruction;

public class ExpressionNode extends Expression{

	protected ExpressionNode left, right;
	protected int label;
	protected Instruction partialInstruction;
	
	public ExpressionNode() {
		super();
		this.setLabel(0);
	}
	
	public void setLeftNode( ExpressionNode leftNode ){
		this.left = leftNode;
	}
	
	public void setRightNode( ExpressionNode rightNode ){
		this.right = rightNode;
	}
	
	public void setPartialInstruction( Instruction instruction ){
		this.partialInstruction = instruction;
	}
	
	public void setLabel( int label ){
		this.label = label;
	}

	public void makeLabel() throws RuntimeException {
		if( !this.hasChildren() ){
			throw new RuntimeException("Unable to build label because node lacks children");
		}
		
		int leftLabel = (left != null)?left.label():0;
		int rightLabel = (right != null)?right.label():0;
		
		if( leftLabel == rightLabel ){
			this.setLabel( leftLabel + 1 );
		} else if( leftLabel > rightLabel) {
			this.setLabel( leftLabel );
		} else { //if( leftLabel < rightLabel )
			this.setLabel( rightLabel );
		}
		
	}
	
	public boolean hasChildren(){
		return (this.right != null) || (this.left != null);
		
	}
	
	public ExpressionNode getLeftNode(){
		return this.left;
	}
	
	public ExpressionNode getRightNode(){
		return this.right;
	}
	
	public int label(){
		return this.label;
	}
	
	
	public Instruction partialInstruction(){
		return this.partialInstruction;
	}
	
	public Expression getExpression(){
		return new Expression( this.instructionList, this.evalType, this.result);
	}
	
}
