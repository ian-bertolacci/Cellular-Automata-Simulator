Cellular-Automata-Simulator
===========================
Version 1.0<br />
2/14/2014

Overview:<br />
Cellular Automata Simulator (CAS) is a base program for simulation of deterministic cellular automata on a square mesh that are programmed at run time. CAS includes the compiler for the automata language which is a distilled version of common functionalities from C family languages. Automata can be programmed for any (reasonable) mesh size and dimension.<br />

Automata language:<br />
The automata language (Mata) features a small subset of C family languages.<br />

In a Mata file, the first statement follows as:<br />

$states = #<br />
$dimension = #<br />
$radius = #<br />
;

where # is a number; and defines the mesh space. <br />
$state defines the number of states each cell can have. ( 0 < $states must be true )<br />
$dimension defines the dimensionality of the mesh. ( 0 < $dimension must be true )<br />
$radius defines the radius of the neighborhood. A neighborhood is a Moore neighborhood, of size 2^( (2r+1) ^ d ); which includes the center cell. ( 0 <= $radius must be true)<br />

Typical 2-d cellular automata with 2 states would be stated as:<br />

$states = 2<br />
$dimension = 2<br />
$radius = 1<br />
;

From here, the language can be treated as procedural C, including else-if block statements, for/while loops, variables and arrays. <br />
In this version of Mata, there are no switch, break, type cast, or function call statements.<br />

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


Note that the compiler is not the smartest, and it is possible to compile a program that does not have a return statement, but will break when the automata is run.<br />

Several automata rules are given in the “Automata Source Code” directory<br />



