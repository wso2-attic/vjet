vjo.ctype("dsf.jslang.feature.tests.EcmaExtensionsTests1")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({

TestFunction : function () {
var arg_proto = arguments.__proto__;
},
GetCallee : function () {
var c = arguments.callee;
return c;
},
GetArguments : function () {
var a = arguments;
return a;
},
GetLength : function () {
var l = arguments.length;
return l;
},
/**
function AnotherTestFunction() {
this.__proto__ = new Prototype();
return this;
}
*/
Prototype : function () {
},
TestObject : function () {
},
AnotherTestObject : function () {
},
AnotherTestFunction :function () {
},
MyObject : function ( value ) {
},


MyProtoValuelessObject : function (value) {
},

MyProtolessObject : function ( value ) {
},
MyValuelessObject : function (value) {
},
MyPrototypeObject : function (value) {
},


LastIndexOf : function ( string, search, position ) {
},
ToInteger : function ( n ) {
},



/**
File Name:          10.1.4-9.js
ECMA Section:       10.1.4 Scope Chain and Identifier Resolution
Description:
Every execution context has associated with it a scope chain. This is
logically a list of objects that are searched when binding an Identifier.
When control enters an execution context, the scope chain is created and
is populated with an initial set of objects, depending on the type of
code. When control leaves the execution context, the scope chain is
destroyed.

During execution, the scope chain of the execution context is affected
only by WithStatement. When execution enters a with block, the object
specified in the with statement is added to the front of the scope chain.
When execution leaves a with block, whether normally or via a break or
continue statement, the object is removed from the scope chain. The object
being removed will always be the first object in the scope chain.

During execution, the syntactic production PrimaryExpression : Identifier
is evaluated using the following algorithm:

1.  Get the next object in the scope chain. If there isn't one, go to step 5.
2.  Call the [[HasProperty]] method of Result(l), passing the Identifier as
the property.
3.  If Result(2) is true, return a value of type Reference whose base object
is Result(l) and whose property name is the Identifier.
4.  Go to step 1.
5.  Return a value of type Reference whose base object is null and whose
property name is the Identifier.
The result of binding an identifier is always a value of type Reference with
its member name component equal to the identifier string.
Author:             christine@netscape.com
Date:               12 november 1997
*/
test_10_1_4__9:function(){

var SECTION = "10.1.4-9";
var VERSION = "ECMA_2";
// startTest();

// writeHeaderToLog( SECTION + " Scope Chain and Identifier Resolution");

this.TestCase( SECTION, "NEW_PROPERTY =  " ,1,1);

// test();

},

/**
File Name:          10.1.6
ECMA Section:       Activation Object
Description:

If the function object being invoked has an arguments property, let x be
the value of that property; the activation object is also given an internal
property [[OldArguments]] whose initial value is x; otherwise, an arguments
property is created for the function object but the activation object is
not given an [[OldArguments]] property. Next, arguments object described
below (the same one stored in the arguments property of the activation
object) is used as the new value of the arguments property of the function
object. This new value is installed even if the arguments property already
exists and has the ReadOnly attribute (as it will for native Function
objects). (These actions are taken to provide compatibility with a form of
program syntax that is now discouraged: to access the arguments object for
function f within the body of f by using the expression f.arguments.
The recommended way to access the arguments object for function f within
the body of f is simply to refer to the variable arguments.)

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_10_1_6:function(){


var SECTION = "10.1.6";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "Activation Object";

// writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"(new TestObject(0,1,2,3,4,5)).length",
6,
(new TestObject(0,1,2,3,4,5)).length );

for ( i = 0; i < 6; i++ ) {

this.TestCase( SECTION,
"(new TestObject(0,1,2,3,4,5))["+i+"]",
i,
(new TestObject(0,1,2,3,4,5))[i]);
}


//    The current object already has an arguments property.
var ARG_STRING;
this.TestCase( SECTION,
"(new AnotherTestObject(1,2,3)).arguments",
ARG_STRING,
(new AnotherTestObject(1,2,3)).arguments );

//  The function invoked with [[Call]]

this.TestCase( SECTION,
"TestFunction(1,2,3)",
ARG_STRING,
this.TestFunction(ARG_STRING) + '' );


// test();

},

/**
File Name:          10.1.8
ECMA Section:       Arguments Object
Description:

When control enters an execution context for declared function code,
anonymous code, or implementation-supplied code, an arguments object is
created and initialized as follows:

The [[Prototype]] of the arguments object is to the original Object
prototype object, the one that is the initial value of Object.prototype
(section 15.2.3.1).

A property is created with name callee and property attributes {DontEnum}.
The initial value of this property is the function object being executed.
This allows anonymous functions to be recursive.

A property is created with name length and property attributes {DontEnum}.
The initial value of this property is the number of actual parameter values
supplied by the caller.

For each non-negative integer, iarg, less than the value of the length
property, a property is created with name ToString(iarg) and property
attributes { DontEnum }. The initial value of this property is the value
of the corresponding actual parameter supplied by the caller. The first
actual parameter value corresponds to iarg = 0, the second to iarg = 1 and
so on. In the case when iarg is less than the number of formal parameters
for the function object, this property shares its value with the
corresponding property of the activation object. This means that changing
this property changes the corresponding property of the activation object
and vice versa. The value sharing mechanism depends on the implementation.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_10_1_8__1:function(){


var SECTION = "10.1.8";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "Arguments Object";

// writeHeaderToLog( SECTION + " "+ TITLE);

var ARG_STRING = "value of the argument property";

this.TestCase( SECTION,
"GetCallee()",
1,1 );

var LIMIT = 100;
var i ;
for ( i = 0, args = "" ; i < LIMIT; i++ ) {
args += String(i) + ( i+1 < LIMIT ? "," : "" );

}

var LENGTH = eval( "GetLength("+ args +")" );

this.TestCase( SECTION,
"GetLength("+args+")",
100,
LENGTH );

var ARGUMENTS = eval( "GetArguments( " +args+")" );

for ( i = 0; i < 100; i++ ) {
this.TestCase( SECTION,
"GetArguments("+args+")["+i+"]",
i,
ARGUMENTS[i] );
}

// test();


},

/**
File Name:          11.6.1-1.js
ECMA Section:       11.6.1 The addition operator ( + )
Description:

The addition operator either performs string concatenation or numeric
addition.

The production AdditiveExpression : AdditiveExpression + MultiplicativeExpression
is evaluated as follows:

1.  Evaluate AdditiveExpression.
2.  Call GetValue(Result(1)).
3.  Evaluate MultiplicativeExpression.
4.  Call GetValue(Result(3)).
5.  Call ToPrimitive(Result(2)).
6.  Call ToPrimitive(Result(4)).
7.  If Type(Result(5)) is String or Type(Result(6)) is String, go to step 12.
(Note that this step differs from step 3 in the algorithm for comparison
for the relational operators in using or instead of and.)
8.  Call ToNumber(Result(5)).
9.  Call ToNumber(Result(6)).
10. Apply the addition operation to Result(8) and Result(9). See the discussion below (11.6.3).
11. Return Result(10).
12. Call ToString(Result(5)).
13. Call ToString(Result(6)).
14. Concatenate Result(12) followed by Result(13).
15. Return Result(14).

Note that no hint is provided in the calls to ToPrimitive in steps 5 and 6.
All native ECMAScript objects except Date objects handle the absence of a
hint as if the hint Number were given; Date objects handle the absence of a
hint as if the hint String were given. Host objects may handle the absence
of a hint in some other manner.

This test does not cover cases where the Additive or Mulplicative expression
ToPrimitive is string.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_6_1__1:function(){

var SECTION = "11.6.1-1";
var VERSION = "ECMA_1";
// startTest();

// writeHeaderToLog( SECTION + " The Addition operator ( + )");

// tests for "MyValuelessObject", where the value is
// set in the object's prototype, not the object itself.

this.TestCase(   SECTION,
"var EXP_1 = new MyValuelessObject(true); var EXP_2 = new MyValuelessObject(false); EXP_1 + EXP_2",
1,
eval("var EXP_1 = new MyValuelessObject(true); var EXP_2 = new MyValuelessObject(false); EXP_1 + EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new MyValuelessObject(new Boolean(true)); var EXP_2 = new MyValuelessObject(new Boolean(false)); EXP_1 + EXP_2",
"truefalse",
eval("var EXP_1 = new MyValuelessObject(new Boolean(true)); var EXP_2 = new MyValuelessObject(new Boolean(false)); EXP_1 + EXP_2") );

// tests for "MyValuelessObject", where the value is
// set in the object's prototype, not the object itself.


this.TestCase(   SECTION,
"var EXP_1 = new MyValuelessObject(100); var EXP_2 = new MyValuelessObject(-1); EXP_1 + EXP_2",
99,
eval("var EXP_1 = new MyValuelessObject(100); var EXP_2 = new MyValuelessObject(-1); EXP_1 + EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new MyValuelessObject(new Number(100)); var EXP_2 = new MyValuelessObject(new Number(-1)); EXP_1 + EXP_2",
"100-1",
eval("var EXP_1 = new MyValuelessObject(new Number(100)); var EXP_2 = new MyValuelessObject(new Number(-1)); EXP_1 + EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new MyValuelessObject( new MyValuelessObject( new Boolean(true) ) ); EXP_1 + EXP_1",
"truetrue",
eval("var EXP_1 = new MyValuelessObject( new MyValuelessObject( new Boolean(true) ) ); EXP_1 + EXP_1") );

// test();


},

/**
File Name:          11.6.1-2.js
ECMA Section:       11.6.1 The addition operator ( + )
Description:

The addition operator either performs string concatenation or numeric
addition.

The production AdditiveExpression : AdditiveExpression + MultiplicativeExpression
is evaluated as follows:

1.  Evaluate AdditiveExpression.
2.  Call GetValue(Result(1)).
3.  Evaluate MultiplicativeExpression.
4.  Call GetValue(Result(3)).
5.  Call ToPrimitive(Result(2)).
6.  Call ToPrimitive(Result(4)).
7.  If Type(Result(5)) is String or Type(Result(6)) is String, go to step 12.
(Note that this step differs from step 3 in the algorithm for comparison
for the relational operators in using or instead of and.)
8.  Call ToNumber(Result(5)).
9.  Call ToNumber(Result(6)).
10. Apply the addition operation to Result(8) and Result(9). See the discussion below (11.6.3).
11. Return Result(10).
12. Call ToString(Result(5)).
13. Call ToString(Result(6)).
14. Concatenate Result(12) followed by Result(13).
15. Return Result(14).

Note that no hint is provided in the calls to ToPrimitive in steps 5 and 6.
All native ECMAScript objects except Date objects handle the absence of a
hint as if the hint Number were given; Date objects handle the absence of a
hint as if the hint String were given. Host objects may handle the absence
of a hint in some other manner.

This test does only covers cases where the Additive or Mulplicative expression
ToPrimitive is a string.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_6_1__2:function(){

var SECTION = "11.6.1-2";
var VERSION = "ECMA_1";
// startTest();

// writeHeaderToLog( SECTION + " The Addition operator ( + )");

// tests for "MyValuelessObject", where the value is
// set in the object's prototype, not the object itself.

this.TestCase(   SECTION,
"var EXP_1 = new MyValuelessObject('string'); var EXP_2 = new MyValuelessObject(false); EXP_1 + EXP_2",
"stringfalse",
eval("var EXP_1 = new MyValuelessObject('string'); var EXP_2 = new MyValuelessObject(false); EXP_1 + EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new MyValuelessObject(new String('string')); var EXP_2 = new MyValuelessObject(new Boolean(false)); EXP_1 + EXP_2",
"stringfalse",
eval("var EXP_1 = new MyValuelessObject(new String('string')); var EXP_2 = new MyValuelessObject(new Boolean(false)); EXP_1 + EXP_2") );

// tests for "MyValuelessObject", where the value is
// set in the object's prototype, not the object itself.

this.TestCase(   SECTION,
"var EXP_1 = new MyValuelessObject(100); var EXP_2 = new MyValuelessObject('string'); EXP_1 + EXP_2",
"100string",
eval("var EXP_1 = new MyValuelessObject(100); var EXP_2 = new MyValuelessObject('string'); EXP_1 + EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new MyValuelessObject(new String('string')); var EXP_2 = new MyValuelessObject(new Number(-1)); EXP_1 + EXP_2",
"string-1",
eval("var EXP_1 = new MyValuelessObject(new String('string')); var EXP_2 = new MyValuelessObject(new Number(-1)); EXP_1 + EXP_2") );

// test();

},

/**
File Name:          11.6.1-3.js
ECMA Section:       11.6.1 The addition operator ( + )
Description:

The addition operator either performs string concatenation or numeric
addition.

The production AdditiveExpression : AdditiveExpression + MultiplicativeExpression
is evaluated as follows:

1.  Evaluate AdditiveExpression.
2.  Call GetValue(Result(1)).
3.  Evaluate MultiplicativeExpression.
4.  Call GetValue(Result(3)).
5.  Call ToPrimitive(Result(2)).
6.  Call ToPrimitive(Result(4)).
7.  If Type(Result(5)) is String or Type(Result(6)) is String, go to step 12.
(Note that this step differs from step 3 in the algorithm for comparison
for the relational operators in using or instead of and.)
8.  Call ToNumber(Result(5)).
9.  Call ToNumber(Result(6)).
10. Apply the addition operation to Result(8) and Result(9). See the discussion below (11.6.3).
11. Return Result(10).
12. Call ToString(Result(5)).
13. Call ToString(Result(6)).
14. Concatenate Result(12) followed by Result(13).
15. Return Result(14).

Note that no hint is provided in the calls to ToPrimitive in steps 5 and 6.
All native ECMAScript objects except Date objects handle the absence of a
hint as if the hint Number were given; Date objects handle the absence of a
hint as if the hint String were given. Host objects may handle the absence
of a hint in some other manner.

This test does only covers cases where the Additive or Mulplicative expression
is a Date.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_6_1__3:function(){

var SECTION = "11.6.1-3";
var VERSION = "ECMA_1";
// startTest();

// writeHeaderToLog( SECTION + " The Addition operator ( + )");

// tests for a boolean primitive and a boolean object, and
// "MyValuelessObject", where the value is set in the object's
// prototype, not the object itself.

var DATE1 = new Date();

var MYOB1 = new MyObject( DATE1 );
var MYOB2 = new MyValuelessObject( DATE1 );
var MYOB3 = new MyProtolessObject( DATE1 );
var MYOB4 = new MyProtoValuelessObject( DATE1 );

this.TestCase(   SECTION,
"MYOB2 = new MyValuelessObject(DATE1); MYOB3 + 'string'",
DATE1.toString() + "string",
MYOB2 + 'string' );

this.TestCase(   SECTION,
"MYOB2 = new MyValuelessObject(DATE1); MYOB3 + new String('string')",
DATE1.toString() + "string",
MYOB2 + new String('string') );
/*
this.TestCase(   SECTION,
"MYOB3 = new MyProtolessObject(DATE1); MYOB3 + new Boolean(true)",
DATE1.toString() + "true",
MYOB3 + new Boolean(true) );
*/

// test();


},

/**
File Name:          11.6.2-1.js
ECMA Section:       11.6.2 The Subtraction operator ( - )
Description:

The production AdditiveExpression : AdditiveExpression -
MultiplicativeExpression is evaluated as follows:

1.  Evaluate AdditiveExpression.
2.  Call GetValue(Result(1)).
3.  Evaluate MultiplicativeExpression.
4.  Call GetValue(Result(3)).
5.  Call ToNumber(Result(2)).
6.  Call ToNumber(Result(4)).
7.  Apply the subtraction operation to Result(5) and Result(6). See the
discussion below (11.6.3).
8.  Return Result(7).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_6_2__1:function(){

var SECTION = "11.6.2-1";
var VERSION = "ECMA_1";
// startTest();

// writeHeaderToLog( SECTION + " The subtraction operator ( - )");

// tests "MyValuelessObject", where the value is
// set in the object's prototype, not the object itself.


this.TestCase(   SECTION,
"var EXP_1 = new MyValuelessObject(true); var EXP_2 = new MyValuelessObject(false); EXP_1 - EXP_2",
1,
eval("var EXP_1 = new MyValuelessObject(true); var EXP_2 = new MyValuelessObject(false); EXP_1 - EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new MyValuelessObject(new Boolean(true)); var EXP_2 = new MyValuelessObject(new Boolean(false)); EXP_1 - EXP_2",
Number.NaN,
eval("var EXP_1 = new MyValuelessObject(new Boolean(true)); var EXP_2 = new MyValuelessObject(new Boolean(false)); EXP_1 - EXP_2") );

// tests "MyValuelessObject", where the value is
// set in the object's prototype, not the object itself.

this.TestCase(   SECTION,
"var EXP_1 = new MyValuelessObject(100); var EXP_2 = new MyValuelessObject(1); EXP_1 - EXP_2",
99,
eval("var EXP_1 = new MyValuelessObject(100); var EXP_2 = new MyValuelessObject(1); EXP_1 - EXP_2") );
/*
this.TestCase(   SECTION,
"var EXP_1 = new MyValuelessObject(new Number(100)); var EXP_2 = new MyValuelessObject(new Number(1)); EXP_1 - EXP_2",
Number.NaN,
eval("var EXP_1 = new MyValuelessObject(new Number(100)); var EXP_2 = new MyValuelessObject(new Number(1)); EXP_1 - EXP_2") );
*/

// same thing with string!

// test();


},

/**
File Name:          15.js
ECMA Section:       15 Native ECMAScript Objects
Description:        Every built-in prototype object has the Object prototype
object, which is the value of the expression
Object.prototype (15.2.3.1) as the value of its internal
[[Prototype]] property, except the Object prototype
object itself.

Every native object associated with a program-created
function also has the Object prototype object as the
value of its internal [[Prototype]] property.

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15__1:function(){

var SECTION = "15-1";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "Native ECMAScript Objects";

// writeHeaderToLog( SECTION + " "+ TITLE);
/*
this.TestCase( SECTION,  "Function.prototype.__proto__", Object.prototype,   Function.prototype.__proto__ );
this.TestCase( SECTION,  "Array.prototype.__proto__",    Object.prototype,   Array.prototype.__proto__ );
this.TestCase( SECTION,  "String.prototype.__proto__",   Object.prototype,   String.prototype.__proto__ );
this.TestCase( SECTION,  "Boolean.prototype.__proto__",  Object.prototype,   Boolean.prototype.__proto__ );
this.TestCase( SECTION,  "Number.prototype.__proto__",   Object.prototype,   Number.prototype.__proto__ );
//    this.TestCase( SECTION,  "Math.prototype.__proto__",     Object.prototype,   Math.prototype.__proto__ );
this.TestCase( SECTION,  "Date.prototype.__proto__",     Object.prototype,   Date.prototype.__proto__ );
this.TestCase( SECTION,  "TestCase.prototype.__proto__", Object.prototype,   TestCase.prototype.__proto__ );

this.TestCase( SECTION,  "MyObject.prototype.__proto__", Object.prototype,   MyObject.prototype.__proto__ );
*/



// test();

},

/**
File Name:          15-2.js
ECMA Section:       15 Native ECMAScript Objects

Description:        Every built-in function and every built-in constructor
has the Function prototype object, which is the value of
the expression Function.prototype as the value of its
internal [[Prototype]] property, except the Function
prototype object itself.

That is, the __proto__ property of builtin functions and
constructors should be the Function.prototype object.

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15__2:function(){

var SECTION = "15-2";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "Native ECMAScript Objects";

// writeHeaderToLog( SECTION + " "+ TITLE);
// test();

},

/**
File Name:          15.1.2.1-1.js
ECMA Section:       15.1.2.1 eval(x)

if x is not a string object, return x.
Description:
Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_1_2_1__1:function(){

var SECTION = "15.1.2.1-1";
var VERSION = "ECMA_1";
var TITLE   = "eval(x)";
var BUGNUMBER = "none";

// startTest();

// writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,      "eval.length",              1,              eval.length );
this.TestCase( SECTION,      "delete eval.length",       false,          delete eval.length );
this.TestCase( SECTION,      "var PROPS = ''; for ( p in eval ) { PROPS += p }; PROPS",  "prototype", eval("var PROPS = ''; for ( p in eval ) { PROPS += p }; PROPS") );
this.TestCase( SECTION,      "eval.length = null; eval.length",       1, eval( "eval.length = null; eval.length") );
//     this.TestCase( SECTION,     "eval.__proto__",                       Function.prototype,            eval.__proto__ );

// test cases where argument is not a string.  should return the argument.


// test();

},

/**
File Name:          15.2.1.1.js
ECMA Section:       15.2.1.1  The Object Constructor Called as a Function:
Object(value)
Description:        When Object is called as a function rather than as a
constructor, the following steps are taken:

1.  If value is null or undefined, create and return a
new object with no properties other than internal
properties exactly as if the object constructor
had been called on that same value (15.2.2.1).
2.  Return ToObject (value), whose rules are:

undefined   generate a runtime error
null        generate a runtime error
boolean     create a new Boolean object whose default
value is the value of the boolean.
number      Create a new Number object whose default
value is the value of the number.
string      Create a new String object whose default
value is the value of the string.
object      Return the input argument (no conversion).

Author:             christine@netscape.com
Date:               17 july 1997
*/
test_15_2_1_1:function(){


var SECTION = "15.2.1.1";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "Object( value )";

// writeHeaderToLog( SECTION + " "+ TITLE);


var NULL_OBJECT = Object(null);

// test();

},

/**
File Name:          15.2.3-1.js
ECMA Section:       15.2.3 Properties of the Object Constructor

Description:        The value of the internal [[Prototype]] property of the
Object constructor is the Function prototype object.

Besides the call and construct propreties and the length
property, the Object constructor has properties described
in 15.2.3.1.

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_2_3__1:function(){

var SECTION = "15.2.3";
var VERSION = "ECMA_2";
// startTest();

// writeHeaderToLog( SECTION + " Properties of the Object Constructor");

this.TestCase( SECTION,  "Object.__proto__",     Function.prototype,     Object.__proto__ );

// test();

},

/**
File Name:          15.2.4.js
ECMA Section:       15.2.4 Properties of the Object prototype object

Description:        The value of the internal [[Prototype]] property of
the Object prototype object is null

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_2_4:function(){


var SECTION = "15.2.4";
var VERSION = "ECMA_2";
// startTest();
var TITLE   = "Properties of the Object.prototype object";

// writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Object.prototype.__proto__",
null,
Object.prototype.__proto__ );

// test();


},

/**
File Name:          15.3.1.1.js
ECMA Section:       15.3.1.1 The Function Constructor Called as a Function

Description:
When the Function function is called with some arguments p1, p2, . . . , pn, body
(where n might be 0, that is, there are no "p" arguments, and where body might
also not be provided), the following steps are taken:

1.  Create and return a new Function object exactly if the function constructor had
been called with the same arguments (15.3.2.1).

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_3_1_1__1:function(){

var SECTION = "15.3.1.1-1";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "The Function Constructor Called as a Function";

// writeHeaderToLog( SECTION + " "+ TITLE);

var MyObject = Function( "value", "this.value = value; this.valueOf =  Function( 'return this.value' ); this.toString =  Function( 'return String(this.value);' )" );


var myfunc = Function();
myfunc.toString = Object.prototype.toString;

//    not going to test toString here since it is implementation dependent.
//    this.TestCase( SECTION,  "myfunc.toString()",     "function anonymous() { }",    myfunc.toString() );

myfunc.toString = Object.prototype.toString;

this.TestCase( SECTION,
"MyObject.__proto__ == Function.prototype",
true,
MyObject.__proto__ == Function.prototype );

// test();



},

/**
File Name:          15.3.1.1-2.js
ECMA Section:       15.3.1.1 The Function Constructor Called as a Function
Function(p1, p2, ..., pn, body )

Description:
When the Function function is called with some arguments p1, p2, . . . , pn,
body (where n might be 0, that is, there are no "p" arguments, and where body
might also not be provided), the following steps are taken:

1.  Create and return a new Function object exactly if the function constructor
had been called with the same arguments (15.3.2.1).

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_3_1_1__2:function(){

var SECTION = "15.3.1.1-2";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "The Function Constructor Called as a Function";

// writeHeaderToLog( SECTION + " "+ TITLE);

var myfunc2 =  Function("a, b, c",   "return a+b+c" );
var myfunc3 =  Function("a,b", "c",  "return a+b+c" );

myfunc2.toString = Object.prototype.toString;
myfunc3.toString = Object.prototype.toString;


this.TestCase( SECTION,
"myfunc2.__proto__",
Function.prototype,
myfunc2.__proto__ );

this.TestCase( SECTION,
"myfunc3.__proto__",
Function.prototype,
myfunc3.__proto__ );

// test();

},

/**
File Name:          15.3.2.1.js
ECMA Section:       15.3.2.1 The Function Constructor
new Function(p1, p2, ..., pn, body )

Description:        The last argument specifies the body (executable code)
of a function; any preceeding arguments sepcify formal
parameters.

See the text for description of this section.

This test examples from the specification.

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_3_2_1__1:function(){

var SECTION = "15.3.2.1";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "The Function Constructor";

// writeHeaderToLog( SECTION + " "+ TITLE);

var MyObject = new Function( "value", "this.value = value; this.valueOf = new Function( 'return this.value' ); this.toString = new Function( 'return String(this.value);' )" );

this.TestCase( SECTION,
"MyObject.__proto__ == Function.prototype",
true,
MyObject.__proto__ == Function.prototype );

// test();

},

/**
File Name:          15.3.2.1.js
ECMA Section:       15.3.2.1 The Function Constructor
new Function(p1, p2, ..., pn, body )

Description:
Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_3_2_1__2:function(){

var SECTION = "15.3.2.1-2";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "The Function Constructor";

// writeHeaderToLog( SECTION + " "+ TITLE);


var myfunc1 = new Function("a","b","c", "return a+b+c" );
var myfunc2 = new Function("a, b, c",   "return a+b+c" );
var myfunc3 = new Function("a,b", "c",  "return a+b+c" );

myfunc1.toString = Object.prototype.toString;
myfunc2.toString = Object.prototype.toString;
myfunc3.toString = Object.prototype.toString;


this.TestCase( SECTION,  "myfunc2.__proto__",                         Function.prototype,     myfunc2.__proto__ );

this.TestCase( SECTION,  "myfunc3.__proto__",                         Function.prototype,     myfunc3.__proto__ );

// test();

},

/**
File Name:          15.3.3.1-1.js
ECMA Section:       15.3.3.1 Properties of the Function Constructor
Function.prototype

Description:        The initial value of Function.prototype is the built-in
Function prototype object.

This property shall have the attributes [DontEnum |
DontDelete | ReadOnly]

This test the value of Function.prototype.

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_3_3_1__1:function(){

var SECTION = "15.3.3.1-1";
var VERSION = "ECMA_2";
// startTest();
var TITLE   = "Function.prototype";

// writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION, "Function.prototype == Function.__proto__",    true, Function.__proto__ == Function.prototype );

// test();

},

/**
File Name:          15.4.3.js
ECMA Section:       15.4.3 Properties of the Array Constructor
Description:        The value of the internal [[Prototype]] property of the
Array constructor is the Function prototype object.

Author:             christine@netscape.com
Date:               7 october 1997
*/
test_15_4_3:function(){


var SECTION = "15.4.3";
var VERSION = "ECMA_2";
// startTest();
var TITLE   = "Properties of the Array Constructor";

// writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Array.__proto__",
Function.prototype,
Array.__proto__ );

// test();

},

/**
File Name:          15.5.3.1.js
ECMA Section:       15.5.3 Properties of the String Constructor

Description:	    The value of the internal [[Prototype]] property of
the String constructor is the Function prototype
object.

In addition to the internal [[Call]] and [[Construct]]
properties, the String constructor also has the length
property, as well as properties described in 15.5.3.1
and 15.5.3.2.

Author:             christine@netscape.com
Date:               1 october 1997
*/
test_15_5_3:function(){


var SECTION = "15.5.3";
var VERSION = "ECMA_2";
// startTest();
var passed = true;
// writeHeaderToLog( SECTION + " Properties of the String Constructor" );

this.TestCase( SECTION,	"String.prototype",             Function.prototype,     String.__proto__ );

// test();

},

/**
File Name:          15.5.4.2.js
ECMA Section:       15.5.4.2 String.prototype.toString

Description:
Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_5_4_2:function(){

var SECTION = "15.5.4.2";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "String.prototype.tostring";

// writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION, "String.prototype.toString.__proto__",  Function.prototype, String.prototype.toString.__proto__ );

// test();

},

/**
File Name:          15.5.4.4-4.js
ECMA Section:       15.5.4.4 String.prototype.charAt(pos)
Description:        Returns a string containing the character at position
pos in the string.  If there is no character at that
string, the result is the empty string.  The result is
a string value, not a String object.

When the charAt method is called with one argument,
pos, the following steps are taken:
1. Call ToString, with this value as its argument
2. Call ToInteger pos
3. Compute the number of characters  in Result(1)
4. If Result(2) is less than 0 is or not less than
Result(3), return the empty string
5. Return a string of length 1 containing one character
from result (1), the character at position Result(2).

Note that the charAt function is intentionally generic;
it does not require that its this value be a String
object.  Therefore it can be transferred to other kinds
of objects for use as a method.

This tests assiging charAt to primitive types..

Author:             christine@netscape.com
Date:               2 october 1997
*/
test_15_5_4_4__4:function(){

var SECTION = "15.5.4.4-4";
var VERSION = "ECMA_2";
// startTest();
var TITLE   = "String.prototype.charAt";

// writeHeaderToLog( SECTION + " "+ TITLE);

/*
this.TestCase( SECTION,     "x = null; x.__proto.charAt = String.prototype.charAt; x.charAt(0)",            "n",     eval("x=null; x.__proto__.charAt = String.prototype.charAt; x.charAt(0)") );
this.TestCase( SECTION,     "x = null; x.__proto.charAt = String.prototype.charAt; x.charAt(1)",            "u",     eval("x=null; x.__proto__.charAt = String.prototype.charAt; x.charAt(1)") );
this.TestCase( SECTION,     "x = null; x.__proto.charAt = String.prototype.charAt; x.charAt(2)",            "l",     eval("x=null; x.__proto__.charAt = String.prototype.charAt; x.charAt(2)") );
this.TestCase( SECTION,     "x = null; x.__proto.charAt = String.prototype.charAt; x.charAt(3)",            "l",     eval("x=null; x.__proto__.charAt = String.prototype.charAt; x.charAt(3)") );

this.TestCase( SECTION,     "x = undefined; x.__proto.charAt = String.prototype.charAt; x.charAt(0)",            "u",     eval("x=undefined; x.__proto__.charAt = String.prototype.charAt; x.charAt(0)") );
this.TestCase( SECTION,     "x = undefined; x.__proto.charAt = String.prototype.charAt; x.charAt(1)",            "n",     eval("x=undefined; x.__proto__.charAt = String.prototype.charAt; x.charAt(1)") );
this.TestCase( SECTION,     "x = undefined; x.__proto.charAt = String.prototype.charAt; x.charAt(2)",            "d",     eval("x=undefined; x.__proto__.charAt = String.prototype.charAt; x.charAt(2)") );
this.TestCase( SECTION,     "x = undefined; x.__proto.charAt = String.prototype.charAt; x.charAt(3)",            "e",     eval("x=undefined; x.__proto__.charAt = String.prototype.charAt; x.charAt(3)") );
*/

this.TestCase( SECTION,     "x = false; x.__proto.charAt = String.prototype.charAt; x.charAt(0)",            "f",     eval("x=false; x.__proto__.charAt = String.prototype.charAt; x.charAt(0)") );
this.TestCase( SECTION,     "x = false; x.__proto.charAt = String.prototype.charAt; x.charAt(1)",            "a",     eval("x=false; x.__proto__.charAt = String.prototype.charAt; x.charAt(1)") );
this.TestCase( SECTION,     "x = false; x.__proto.charAt = String.prototype.charAt; x.charAt(2)",            "l",     eval("x=false; x.__proto__.charAt = String.prototype.charAt; x.charAt(2)") );
this.TestCase( SECTION,     "x = false; x.__proto.charAt = String.prototype.charAt; x.charAt(3)",            "s",     eval("x=false; x.__proto__.charAt = String.prototype.charAt; x.charAt(3)") );
this.TestCase( SECTION,     "x = false; x.__proto.charAt = String.prototype.charAt; x.charAt(4)",            "e",     eval("x=false; x.__proto__.charAt = String.prototype.charAt; x.charAt(4)") );

this.TestCase( SECTION,     "x = true; x.__proto.charAt = String.prototype.charAt; x.charAt(0)",            "t",     eval("x=true; x.__proto__.charAt = String.prototype.charAt; x.charAt(0)") );
this.TestCase( SECTION,     "x = true; x.__proto.charAt = String.prototype.charAt; x.charAt(1)",            "r",     eval("x=true; x.__proto__.charAt = String.prototype.charAt; x.charAt(1)") );
this.TestCase( SECTION,     "x = true; x.__proto.charAt = String.prototype.charAt; x.charAt(2)",            "u",     eval("x=true; x.__proto__.charAt = String.prototype.charAt; x.charAt(2)") );
this.TestCase( SECTION,     "x = true; x.__proto.charAt = String.prototype.charAt; x.charAt(3)",            "e",     eval("x=true; x.__proto__.charAt = String.prototype.charAt; x.charAt(3)") );

this.TestCase( SECTION,     "x = NaN; x.__proto.charAt = String.prototype.charAt; x.charAt(0)",            "N",     eval("x=NaN; x.__proto__.charAt = String.prototype.charAt; x.charAt(0)") );
this.TestCase( SECTION,     "x = NaN; x.__proto.charAt = String.prototype.charAt; x.charAt(1)",            "a",     eval("x=NaN; x.__proto__.charAt = String.prototype.charAt; x.charAt(1)") );
this.TestCase( SECTION,     "x = NaN; x.__proto.charAt = String.prototype.charAt; x.charAt(2)",            "N",     eval("x=NaN; x.__proto__.charAt = String.prototype.charAt; x.charAt(2)") );

this.TestCase( SECTION,     "x = 123; x.__proto.charAt = String.prototype.charAt; x.charAt(0)",            "1",     eval("x=123; x.__proto__.charAt = String.prototype.charAt; x.charAt(0)") );
this.TestCase( SECTION,     "x = 123; x.__proto.charAt = String.prototype.charAt; x.charAt(1)",            "2",     eval("x=123; x.__proto__.charAt = String.prototype.charAt; x.charAt(1)") );
this.TestCase( SECTION,     "x = 123; x.__proto.charAt = String.prototype.charAt; x.charAt(2)",            "3",     eval("x=123; x.__proto__.charAt = String.prototype.charAt; x.charAt(2)") );


// test();

},

/**
File Name:          15.5.4.5-6.js
ECMA Section:       15.5.4.5 String.prototype.charCodeAt(pos)
Description:        Returns a number (a nonnegative integer less than 2^16)
representing the Unicode encoding of the character at
position pos in this string.  If there is no character
at that position, the number is NaN.

When the charCodeAt method is called with one argument
pos, the following steps are taken:
1. Call ToString, giving it the theis value as its
argument
2. Call ToInteger(pos)
3. Compute the number of characters in result(1).
4. If Result(2) is less than 0 or is not less than
Result(3), return NaN.
5. Return a value of Number type, of positive sign, whose
magnitude is the Unicode encoding of one character
from result 1, namely the characer at position Result
(2), where the first character in Result(1) is
considered to be at position 0.

Note that the charCodeAt funciton is intentionally
generic; it does not require that its this value be a
String object.  Therefore it can be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               2 october 1997
*/
test_15_5_4_5__6:function(){

var SECTION = "15.5.4.5-6";
var VERSION = "ECMA_2";
// startTest();
var TITLE   = "String.prototype.charCodeAt";

// writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"var obj = true; obj.__proto__.charCodeAt = String.prototype.charCodeAt; var s = ''; for ( var i = 0; i < 4; i++ ) s+= String.fromCharCode( obj.charCodeAt(i) ); s",
"true",
eval("var obj = true; obj.__proto__.charCodeAt = String.prototype.charCodeAt; var s = ''; for ( var i = 0; i < 4; i++ ) s+= String.fromCharCode( obj.charCodeAt(i) ); s") );

this.TestCase( SECTION,
"var obj = 1234; obj.__proto__.charCodeAt = String.prototype.charCodeAt; var s = ''; for ( var i = 0; i < 4; i++ ) s+= String.fromCharCode( obj.charCodeAt(i) ); s",
"1234",
eval("var obj = 1234; obj.__proto__.charCodeAt = String.prototype.charCodeAt; var s = ''; for ( var i = 0; i < 4; i++ ) s+= String.fromCharCode( obj.charCodeAt(i) ); s") );

this.TestCase( SECTION,
"var obj = 'hello'; obj.__proto__.charCodeAt = String.prototype.charCodeAt; var s = ''; for ( var i = 0; i < 5; i++ ) s+= String.fromCharCode( obj.charCodeAt(i) ); s",
"hello",
eval("var obj = 'hello'; obj.__proto__.charCodeAt = String.prototype.charCodeAt; var s = ''; for ( var i = 0; i < 5; i++ ) s+= String.fromCharCode( obj.charCodeAt(i) ); s") );

// test();

},

/**
File Name:          15.5.4.7-3.js
ECMA Section:       15.5.4.7 String.prototype.lastIndexOf( searchString, pos)
Description:

If the given searchString appears as a substring of the result of
converting this object to a string, at one or more positions that are
at or to the left of the specified position, then the index of the
rightmost such position is returned; otherwise -1 is returned. If position
is undefined or not supplied, the length of this string value is assumed,
so as to search all of the string.

When the lastIndexOf method is called with two arguments searchString and
position, the following steps are taken:

1.Call ToString, giving it the this value as its argument.
2.Call ToString(searchString).
3.Call ToNumber(position). (If position is undefined or not supplied, this step produces the value NaN).
4.If Result(3) is NaN, use +; otherwise, call ToInteger(Result(3)).
5.Compute the number of characters in Result(1).
6.Compute min(max(Result(4), 0), Result(5)).
7.Compute the number of characters in the string that is Result(2).
8.Compute the largest possible integer k not larger than Result(6) such that k+Result(7) is not greater
than Result(5), and for all nonnegative integers j less than Result(7), the character at position k+j of
Result(1) is the same as the character at position j of Result(2); but if there is no such integer k, then
compute the value -1.

1.Return Result(8).

Note that the lastIndexOf function is intentionally generic; it does not require that its this value be a
String object. Therefore it can be transferred to other kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               2 october 1997
*/
test_15_5_4_7__3:function(){

var SECTION = "15.5.4.7-3";
var VERSION = "ECMA_2";
// startTest();
var TITLE   = "String.protoype.lastIndexOf";

// writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase(   SECTION,
"var b = true; b.__proto__.lastIndexOf = String.prototype.lastIndexOf; b.lastIndexOf('r', 0 )",
-1,
eval("var b = true; b.__proto__.lastIndexOf = String.prototype.lastIndexOf; b.lastIndexOf('r', 0 )") );

this.TestCase(   SECTION,
"var b = true; b.__proto__.lastIndexOf = String.prototype.lastIndexOf; b.lastIndexOf('r', 1 )",
1,
eval("var b = true; b.__proto__.lastIndexOf = String.prototype.lastIndexOf; b.lastIndexOf('r', 1 )") );

this.TestCase(   SECTION,
"var b = true; b.__proto__.lastIndexOf = String.prototype.lastIndexOf; b.lastIndexOf('r', 2 )",
1,
eval("var b = true; b.__proto__.lastIndexOf = String.prototype.lastIndexOf; b.lastIndexOf('r', 2 )") );

this.TestCase(   SECTION,
"var b = true; b.__proto__.lastIndexOf = String.prototype.lastIndexOf; b.lastIndexOf('r', 10 )",
1,
eval("var b = true; b.__proto__.lastIndexOf = String.prototype.lastIndexOf; b.lastIndexOf('r', 10 )") );

this.TestCase(   SECTION,
"var b = true; b.__proto__.lastIndexOf = String.prototype.lastIndexOf; b.lastIndexOf('r' )",
1,
eval("var b = true; b.__proto__.lastIndexOf = String.prototype.lastIndexOf; b.lastIndexOf('r' )") );

// test();

},

/**
File Name:          15.6.3.1-5.js
ECMA Section:       15.6.3.1 Boolean.prototype
Description:
Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_6_3_1__5:function(){

var VERSION = "ECMA_2";
// startTest();
var SECTION = "15.6.3.1-5";
var TITLE   = "Boolean.prototype"

//  writeHeaderToLog( SECTION + " " + TITLE );

this.TestCase( SECTION,  "Function.prototype == Boolean.__proto__",   true,   Function.prototype == Boolean.__proto__ );

// test();

},

/**
File Name:          15.6.3.js
ECMA Section:       15.6.3 Properties of the Boolean Constructor

Description:        The value of the internal prototype property is
the Function prototype object.

It has the internal [[Call]] and [[Construct]]
properties, and the length property.

Author:             christine@netscape.com
Date:               june 27, 1997

*/
test_15_6_3:function(){

var SECTION = "15.6.3";
var VERSION = "ECMA_2";
// startTest();
var TITLE   = "Properties of the Boolean Constructor"
//  writeHeaderToLog( SECTION + TITLE );


this.TestCase( SECTION,  "Boolean.__proto__ == Function.prototype",  true,   Boolean.__proto__ == Function.prototype );
this.TestCase( SECTION,  "Boolean.length",          1,                   Boolean.length );

// test();

},

/**
File Name:          15.6.4-2.js
ECMA Section:       15.6.4 Properties of the Boolean Prototype Object

Description:
The Boolean prototype object is itself a Boolean object (its [[Class]] is
"Boolean") whose value is false.

The value of the internal [[Prototype]] property of the Boolean prototype object
is the Object prototype object (15.2.3.1).

Author:             christine@netscape.com
Date:               30 september 1997

*/
test_15_6_4__2:function(){



var VERSION = "ECMA_2"
//  startTest();
var SECTION = "15.6.4-2";

// writeHeaderToLog( SECTION + " Properties of the Boolean Prototype Object");

this.TestCase( SECTION, "Boolean.prototype.__proto__",               Object.prototype,       Boolean.prototype.__proto__ );

// test();

},

/**
File Name:          15.7.3.js
15.7.3  Properties of the Number Constructor

Description:        The value of the internal [[Prototype]] property
of the Number constructor is the Function prototype
object.  The Number constructor also has the internal
[[Call]] and [[Construct]] properties, and the length
property.

Other properties are in subsequent tests.

Author:             christine@netscape.com
Date:               29 september 1997
*/
test_15_7_3:function(){


var SECTION = "15.7.3";
var VERSION = "ECMA_2";
// startTest();
var TITLE   = "Properties of the Number Constructor";

// writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase(SECTION,
"Number.__proto__",
Function.prototype,
Number.__proto__ );

// test();

},

/**
File Name:          15.7.4.js
ECMA Section:       15.7.4

Description:

The Number prototype object is itself a Number object (its [[Class]] is
"Number") whose value is +0.

The value of the internal [[Prototype]] property of the Number prototype
object is the Object prototype object (15.2.3.1).

In following descriptions of functions that are properties of the Number
prototype object, the phrase "this Number object" refers to the object
that is the this value for the invocation of the function; it is an error
if this does not refer to an object for which the value of the internal
[[Class]] property is "Number". Also, the phrase "this number value" refers
to the number value represented by this Number object, that is, the value
of the internal [[Value]] property of this Number object.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_7_4:function(){

var SECTION = "15.7.4";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "Properties of the Number Prototype Object";

// writeHeaderToLog( SECTION + " "+TITLE);

this.TestCase( SECTION,
"Number.prototype.toString=Object.prototype.toString;Number.prototype.toString()",
"[object Number]",
eval("Number.prototype.toString=Object.prototype.toString;Number.prototype.toString()") );

this.TestCase( SECTION,
"typeof Number.prototype",
"object",
typeof Number.prototype );

this.TestCase( SECTION,
"Number.prototype.valueOf()",
0,
Number.prototype.valueOf() );

//    The __proto__ property cannot be used in ECMA_1 tests.
//    this.TestCase( SECTION, "Number.prototype.__proto__",                        Object.prototype,   Number.prototype.__proto__ );
//    this.TestCase( SECTION, "Number.prototype.__proto__ == Object.prototype",    true,       Number.prototype.__proto__ == Object.prototype );

// test();

},

/**
File Name:          15.8-1.js
ECMA Section:       15.8 The Math Object

Description:

The Math object is merely a single object that has some named properties,
some of which are functions.

The value of the internal [[Prototype]] property of the Math object is the
Object prototype object (15.2.3.1).

The Math object does not have a [[Construct]] property; it is not possible
to use the Math object as a constructor with the new operator.

The Math object does not have a [[Call]] property; it is not possible to
invoke the Math object as a function.

Recall that, in this specification, the phrase "the number value for x" has
a technical meaning defined in section 8.5.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_8__1:function(){


var SECTION = "15.8-1";
var VERSION = "ECMA_2";
// startTest();
var TITLE   = "The Math Object";

// writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Math.__proto__ == Object.prototype",
true,
Math.__proto__ == Object.prototype );

this.TestCase( SECTION,
"Math.__proto__",
Object.prototype,
Math.__proto__ );

// test();

},

/**
File Name:          15.9.5.js
ECMA Section:       15.9.5 Properties of the Date prototype object
Description:

The Date prototype object is itself a Date object (its [[Class]] is
"Date") whose value is NaN.

The value of the internal [[Prototype]] property of the Date prototype
object is the Object prototype object (15.2.3.1).

In following descriptions of functions that are properties of the Date
prototype object, the phrase "this Date object" refers to the object that
is the this value for the invocation of the function; it is an error if
this does not refer to an object for which the value of the internal
[[Class]] property is "Date". Also, the phrase "this time value" refers
to the number value for the time represented by this Date object, that is,
the value of the internal [[Value]] property of this Date object.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5:function(){


var SECTION = "15.9.5";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "Properties of the Date Prototype Object";

// writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Date.prototype.__proto__ == Object.prototype",
true,
Date.prototype.__proto__ == Object.prototype );
// test();


},

/**
File Name:          8.6.2.1-1.js
ECMA Section:       8.6.2.1 Get (Value)
Description:

When the [[Get]] method of O is called with property name P, the following
steps are taken:

1.  If O doesn't have a property with name P, go to step 4.
2.  Get the value of the property.
3.  Return Result(2).
4.  If the [[Prototype]] of O is null, return undefined.
5.  Call the [[Get]] method of [[Prototype]] with property name P.
6.  Return Result(5).

This tests [[Get]] (Value).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_8_6_2_1__1:function(){

var SECTION = "8.6.2.1-1";
var VERSION = "ECMA_1";
// startTest();

// writeHeaderToLog( SECTION + " [[Get]] (Value)");

this.TestCase( SECTION,  "var OBJ = new MyValuelessObject(true); OBJ.valueOf()",     true,           eval("var OBJ = new MyValuelessObject(true); OBJ.valueOf()") );
//    this.TestCase( SECTION,  "var OBJ = new MyProtoValuelessObject(true); OBJ + ''",     "undefined",    eval("var OBJ = new MyProtoValuelessObject(); OBJ + ''") );
this.TestCase( SECTION,  "var OBJ = new MyProtolessObject(true); OBJ.valueOf()",     true,           eval("var OBJ = new MyProtolessObject(true); OBJ.valueOf()") );

this.TestCase( SECTION,  "var OBJ = new MyValuelessObject(Number.POSITIVE_INFINITY); OBJ.valueOf()",     Number.POSITIVE_INFINITY,           eval("var OBJ = new MyValuelessObject(Number.POSITIVE_INFINITY); OBJ.valueOf()") );
//    this.TestCase( SECTION,  "var OBJ = new MyProtoValuelessObject(Number.POSITIVE_INFINITY); OBJ + ''",     "undefined",                        eval("var OBJ = new MyProtoValuelessObject(); OBJ + ''") );
this.TestCase( SECTION,  "var OBJ = new MyProtolessObject(Number.POSITIVE_INFINITY); OBJ.valueOf()",     Number.POSITIVE_INFINITY,           eval("var OBJ = new MyProtolessObject(Number.POSITIVE_INFINITY); OBJ.valueOf()") );

this.TestCase( SECTION,  "var OBJ = new MyValuelessObject('string'); OBJ.valueOf()",     'string',           eval("var OBJ = new MyValuelessObject('string'); OBJ.valueOf()") );
//    this.TestCase( SECTION,  "var OBJ = new MyProtoValuelessObject('string'); OJ + ''",     "undefined",      eval("var OBJ = new MyProtoValuelessObject(); OBJ + ''") );
this.TestCase( SECTION,  "var OBJ = new MyProtolessObject('string'); OBJ.valueOf()",     'string',           eval("var OBJ = new MyProtolessObject('string'); OBJ.valueOf()") );

// test();

},

/**
File Name:          9.9-1.js
ECMA Section:       9.9  Type Conversion:  ToObject
Description:

undefined   generate a runtime error
null        generate a runtime error
boolean     create a new Boolean object whose default
value is the value of the boolean.
number      Create a new Number object whose default
value is the value of the number.
string      Create a new String object whose default
value is the value of the string.
object      Return the input argument (no conversion).
Author:             christine@netscape.com
Date:               17 july 1997
*/
test_9_9__1:function(){


var VERSION = "ECMA_1";
// startTest();
var SECTION = "9.9-1";

// writeHeaderToLog( SECTION + " Type Conversion: ToObject" );

this.TestCase( SECTION, "(Object(true)).__proto__",  Boolean.prototype,      (Object(true)).__proto__ );

this.TestCase( SECTION, "(Object(true)).__proto__",  Boolean.prototype,      (Object(true)).__proto__ );

this.TestCase( SECTION, "(Object(0)).__proto__",     Number.prototype,      (Object(0)).__proto__ );

this.TestCase( SECTION, "(Object(-0)).__proto__",    Number.prototype,      (Object(-0)).__proto__ );

this.TestCase( SECTION, "(Object(1)).__proto__",     Number.prototype,      (Object(1)).__proto__ );

this.TestCase( SECTION, "(Object(-1)).__proto__",    Number.prototype,      (Object(-1)).__proto__ );

this.TestCase( SECTION, "(Object(Number.MAX_VALUE)).__proto__",  Number.prototype,               (Object(Number.MAX_VALUE)).__proto__ );

this.TestCase( SECTION, "(Object(Number.MIN_VALUE)).__proto__",  Number.prototype, (Object(Number.MIN_VALUE)).__proto__ );

this.TestCase( SECTION, "(Object(Number.POSITIVE_INFINITY)).__proto__",  Number.prototype,               (Object(Number.POSITIVE_INFINITY)).__proto__ );

this.TestCase( SECTION, "(Object(Number.NEGATIVE_INFINITY)).__proto__",  Number.prototype,   (Object(Number.NEGATIVE_INFINITY)).__proto__ );

this.TestCase( SECTION, "(Object(Number.NaN)).__proto__",    Number.prototype,          (Object(Number.NaN)).__proto__ );

this.TestCase( SECTION, "(Object('a string')).__proto__",    String.prototype,   (Object("a string")).__proto__ );

this.TestCase( SECTION, "(Object('')).__proto__",            String.prototype,   (Object("")).__proto__ );

this.TestCase( SECTION, "(Object('\\r\\t\\b\\n\\v\\f')).__proto__", String.prototype,   (Object("\\r\\t\\b\\n\\v\\f")).__proto__ );

this.TestCase( SECTION,  "Object( '\\\'\\\"\\' ).__proto__",      String.prototype,   (Object("\'\"\\")).__proto__ );

this.TestCase( SECTION, "(Object( new MyObject(true) )).toString()",  "[object Object]",       eval("(Object( new MyObject(true) )).toString()") );

// test();

}
}).endType();

var arguments = "FAILED!";

var ARG_STRING = "value of the argument property";
