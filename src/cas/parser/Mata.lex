
package cas.parser;

import java_cup.runtime.*;
import java.io.IOException;

import cas.parser.MataSym;
import cas.parser.MataSym.*;

import cas.machine.VirtualMachine.VarType;


%%

%class MataLex

%unicode
%line
%column

// %public
%final
// %abstract

%cupsym parser.MataSym
%cup
// %cupdebug

%init{
	// TODO: code that goes to constructor
%init}

%{
	private Symbol sym(int type)
	{
		return sym(type, yytext());
	}

	private Symbol sym(int type, Object value)
	{
		return new Symbol(type, yyline, yycolumn, value);
	}

	private void error()
	throws IOException
	{
		throw new IOException("illegal text at line = "+yyline+", column = "+yycolumn+", text = '"+yytext()+"'");
	}
%}

%eofval{
	
  	return new Symbol(MataSym.EOF, null);
  	
%eofval}

%eof{
	
%eof}

%%

"="  { return new Symbol(MataSym.ASSIGN, null); }

"+"  { return new Symbol(MataSym.PLUS, null); }
"-"  { return new Symbol(MataSym.MINUS, null); }
"\*" { return new Symbol(MataSym.TIMES, null); }
"/"  { return new Symbol(MataSym.DIVIDE, null); }
"\%" { return new Symbol(MataSym.MOD, null); }

"<=" { return new Symbol(MataSym.LE, null); }
"<"  { return new Symbol(MataSym.LT , null); }
">=" { return new Symbol(MataSym.GE , null); }
">"  { return new Symbol(MataSym.GT , null); }
"!=" { return new Symbol(MataSym.NEQ , null); }
"==" { return new Symbol(MataSym.EQ , null); }

"&"	{ return new Symbol(MataSym.AND, null);}
"|"	{ return new Symbol(MataSym.OR, null);}
"!" { return new Symbol(MataSym.NOT, null);}



":" { return new Symbol(MataSym.COLON, null); }
";" { return new Symbol(MataSym.SEMICOLON, null); }

"{" { return new Symbol(MataSym.OBRAK , null); }
"}" { return new Symbol(MataSym.CBRAK , null); }
"(" { return new Symbol(MataSym.OPAREN , null); }
")" { return new Symbol(MataSym.CPAREN , null); }
"[" { return new Symbol(MataSym.OBRACE , null); }
"]" { return new Symbol(MataSym.CBRACE , null); }

"int"	{ return new Symbol(MataSym.TYPE, VarType.INT); }
"real"	{ return new Symbol(MataSym.TYPE, VarType.REAL); }
"boole"	{ return new Symbol(MataSym.TYPE, VarType.BOOLE); }
"array" { return new Symbol(MataSym.ARRAY, null); }

"if"	{ return new Symbol(MataSym.IF, null); }
"else"	{ return new Symbol(MataSym.ELSE, null); }
"for"	{ return new Symbol(MataSym.FOR, null); }
"while"	{ return new Symbol(MataSym.WHILE, null); }

"return" { return new Symbol(MataSym.RETURN, null); }

"true"|"false"
{
	//System.out.println("Boole: "+ Boolean.parseBoolean( yytext() ));
	return new Symbol(MataSym.BOOLE, Boolean.parseBoolean( yytext() ) );
	
}
"$states"	 { return new Symbol(MataSym.DEFSTATES, null);  }
"$dimension" { return new Symbol(MataSym.DEFDIMENSION, null); }
"$radius"	 { return new Symbol(MataSym.DEFRADIUS, null); }

"-"?[0-9]+ 		
{ 
	//System.out.println("int: "+Integer.parseInt( yytext() )); 
	return new Symbol( MataSym.INT, Integer.parseInt( yytext() ) )  ; 
}
						
("-"?[0-9]+\.[0-9]*)|("-"?[0-9]*\.[0-9]+)|("-"?[0-9]+)
{
	//System.out.println("double: "+Double.parseDouble( yytext() ));
	return new Symbol( MataSym.REAL, Double.parseDouble( yytext() ) );
}


[a-zA-Z]+[a-zA-Z0-9]*	
{ 
	//System.out.println( "ID: "+yytext()); 
	return new Symbol(MataSym.ID,  new String( yytext() ) ) ; 
}




[ \t\r\f\n] { /* ignore white space. */ }



. { System.err.println("Illegal character: "+yytext()); }

