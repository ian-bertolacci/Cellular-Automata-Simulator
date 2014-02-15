Cellular-Automata-Simulator
===========================
Version 1.0
2/14/2014

Overview:
Cellular Automata Simulator (CAS) is a base program for simulation of deterministic cellular automata on a square mesh that are programmed at run time. CAS includes the compiler for the automata language which is a distilled version of common functionalities from C family languages. Automata can be programmed for any (reasonable) mesh size and dimension.

Automata language:
The automata language (Mata) features a small subset of C family languages.

In a Mata file, the first statement follows as:

$states = #
$dimension = #
$radius = #
;

where # is a number; and defines the mesh space. 
$state defines the number of states each cell can have. ( 0 < $states must be true )
$dimension defines the dimensionality of the mesh. ( 0 < $dimension must be true )
$radius defines the radius of the neighborhood. A neighborhood is a Moore neighborhood, of size 2^( (2r+1) ^ d ); which includes the center cell. ( 0 <= $radius must be true)

Typical 2-d cellular automata with 2 states would be stated as:

$states = 2
$dimension = 2
$radius = 1
;

From here, the language can be treated as procedural C, including else-if block statements, for/while loops, variables and arrays. 
In this version of Mata, there are no switch, break, type cast, or function call statements.

The operators include:
a < b	( a less than b )
a <= b	( a less than or equal to b )
a > c	( a greater than b )
a >= c	( a greater than or equal to b )
a == b	( a equal to b )
a != b	(a not equal to b )
a & b	( a logical and b )
a | b	( a logical or b )
! a	( logical not a )
a + b	( a plus b )
a – b	( a minus b )
a * b	( a times b)
a / b	( a divided by b )
a % b	( a modulus b )

there are several types:
int	( an integer )
real 	( a floating point, real number )
boole	( a boolean value )

an they can be formed with arrays to create typed arrays, via the syntax:
array:< type > < ID > [ < integer > ] ;

for example:
array:int myArray [ 100 ] ;
declares an array of type int with 100 elements, named “myArray”

Arrays are 0 indexed.


All automata must be terminated with a return statement via the syntax:
return < int > ;


Note that the compiler is not the smartest, and it is possible to compile a program that does not have a return statement, but will break when the automata is run.

Several automata rules are given in the “Automata Source Code” directory



