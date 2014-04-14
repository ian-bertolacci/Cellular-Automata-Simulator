Cellular-Automata-Simulator
===========================
Version 1.0<br />
2/20/2014

Overview:<br />
Cellular Automata Simulator (CAS) is a base program for simulation of deterministic 
cellular automata on a square mesh that are programmed at run time. 
CAS includes the compiler for the automata language which is a highly distilled 
version of common functionalities from C family languages. 
Automata can be programmed for any (reasonable) mesh size and dimension.<br />

Automata language:<br />
------------------
The automata language (Mata) features a small subset of C family languages.<br />

In a Mata file, the first statement follows as:<br />

$states = #<br />
$dimension = #<br />
$radius = #<br />
;

where # is a number; and defines the mesh space. <br />
$state defines the number of states each cell can have. ( 0 < $states must be true )<br />
$dimension defines the dimensionality of the mesh. ( 0 < $dimension must be true )<br />
$radius defines the radius of the neighborhood. A neighborhood is a Moore 
neighborhood, of size 2^( (2r+1) ^ d ); which includes the center cell. 
( 0 <= $radius must be true)<br />

Typical 2-d cellular automata with 2 states would be stated as:<br />

$states = 2<br />
$dimension = 2<br />
$radius = 1<br />
;

From here, the language can be treated as procedural C, including else-if block 
statements, for/while loops, variables and arrays. <br />
In this version of Mata, there are no switch, break, type cast, or function 
call statements.<br />

The remainder of the file is what will be executed when updating each cell.
The return value is the state of the cell at time t+1

There is a global variable, "neighborhood", that is an array containing the 
neighborhood states of the current cell <br />
The current cell is always at index = floor( ((2r+1)^2 ) / 2 )

The operators include:<br />
a < b	( a less than b )<br />
a <= b	( a less than or equal to b )<br />
a > c	( a greater than b )<br />a >= c	( a greater than or equal to b )<br />
a == b	( a equal to b )<br />
a != b	(a not equal to b )<br />
a & b	( a logical and b )<br />
a | b	( a logical or b )<br />
! a	( logical not a )<br />
a + b	( a plus b )<br />
a – b	( a minus b )<br />
a * b	( a times b)<br />
a / b	( a divided by b )<br />
a % b	( a modulus b )<br />

there are several types:<br />
int	( an integer )<br />
real 	( a floating point, real number )<br />
boole	( a boolean value )<br />

an they can be formed with arrays to create typed arrays, via the syntax:<br />
array:< type > < ID > [ < integer > ] ;<br />

for example:<br />
array:int myArray [ 100 ] ;<br />
declares an array of type int with 100 elements, named “myArray”<br />

Arrays are 0 indexed.

All automata must be terminated with a return statement via the syntax:<br />
return < int > ;<br />
since the return value is the cell state, 0 <= value < $states must be true 

Note that the compiler is not the smartest, and it is possible to compile a program 
that does not have a return statement or have terminating return statement. <br />
However, the program will break when termination of the automata code occurs 
without a return.<br />

Several automata rules are given in the “Automata Source Code” directory<br />


Compiling CAS
-------------
Using the included Makefile, the CAS module can be compiled into two different
versions of the module:
	+ CAS.jar: which is the base module, including the parser, virtual machine, and mesh <br />
	+ CAS_CUP-runtime_include.jar: which is the base module, with the inclusion of the 
java-cup run-time library as well, just to make running simpler <br />

The Makefile has several options: <br />
	+ all: which creates both versions of the module <br />
	+ CAS.jar: which creates the CAS.jar module  <br />
	+ CAS_CUP-runtime_include.jar: which creates the CAS_CUP-runtime_include.jar module <br />
	+ CAS_Setup which creates/cleans the directory used in setting up the module into a jar file <br />
	+ makeClassFiles: which compiles the java code <br />
	+ makeFromCUPandLEX: which generates the parser and lexer java code from Mata.cup and Mata.lex <br />
	+ makeBIN: which creates the directory used for compilation, and copies the code from source <br />
	+ getCUPandLEX: which downloads the jflex and CUP jar's from the net, extracts them <br />
and removes any tailings <br />
	+ clean: removes the bin/, CAS/ and CAS*.jar directories/files, if they exist <br />
	+ new: equivelent to `make clean; make all;` <br />
	+ cleanAll: performs clean and rm *.jar to clean out the downloaded jflex and CUP jars <br />
	
	
Programming with CAS
--------------------
While working and available for use, it is mildly cumbersome to use because of the
way it was originally programmed to work. 
That in mind take note that interface to the module could, and probably will, change
in the future. <br />
That said, here is the most basic tutorial on how the module can be utilized.

A most basic program would be this: <br />
```java
public class CASTest1{ 
	public static void main(String[] args){
		
		// Instantiate the compiler, given the file to compile
		MataParser compiler = new MataParser( "path/to/file/that/defines/theAutomaton" ); 
		
		
		// Perform the compiling, sets an internal variable, partialAutomata, 
		// with most of the data required for a complete Automata object 
		// Currently only missing the size of the mesh
		compiler.parser();
		
		
		// Refrence the created PartialAutomata object
		PartialAutomata partialMachine = compiler.partialAutomata;
		
		
		// Set the edge size of the mesh to be 150; meaning the
		// mesh will be 150^d cells large 
		// (note that size impacts performance in a large way)
		partialMachine.setSize( 150 );
		
		
		// Now that partialMachine is data complete, creation of the 
		// actual Automata that is able to run and update is possible.
		Automata machine = partialMachine.createAutomata();
		
		
		// By default, an Automata is created with an empty mesh
		// with each cell set to 0, commonly refered to as 'dead'.
		// Here we will create a randomly seeded mesh, where
		// each cell can take on the state where 0 <= state < $states
		machine.generateRandomConfiguration();
		
		// Alternatively, if it is known which cells are to be set to a
		// state, machine.setCell( index, state ) can be used to set the state
		// of the cell at index to state.
		// Note that index is the linearized index, so that if a 2d automata of size 4
		// would map coordinates (x,y) like: (0,0)-> 0, (3,0)-> 3, (0,1)->4, (0,2)-> 8
		// essentially, one can treat a coordinate system as a number in the base of the 
		// (edge size - 1) with $dimension digits. Translating that number into base 10 
		// gives the linearized index
		
		
		// Perform a single update on the automata.
		machine.update();
		
		// Alternatively, if multiple updates are required
		// machine.update( n ); will update the machine n times
		
	}
}
```

As more features, and as the interface is ironed out, more will be placed here,
but, unfortunately, for now, this will have to suffice.








