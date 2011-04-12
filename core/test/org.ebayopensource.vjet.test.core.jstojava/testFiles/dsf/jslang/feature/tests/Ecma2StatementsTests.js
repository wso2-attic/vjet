vjo.ctype("dsf.jslang.feature.tests.Ecma2StatementsTests")
.inherits("dsf.jslang.feature.tests.BaseTestEcma2")
.protos({

constructs: function() {
this.base();
},

/**
*  File Name:          dowhile-001
*  ECMA Section:
*  Description:        do...while statements
*
*
*
*  Author:             christine@netscape.com
*  Date:               11 August 1998
*/
test_dowhile__001: function() {
var SECTION = "dowhile-002";
var VERSION = "ECMA_2";
var TITLE   = "do...while with a labeled continue statement";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

this.LabeledContinue1( 0, 1 );
this.LabeledContinue1( 1, 1 );
this.LabeledContinue1( -1, 1 );
this.LabeledContinue1( 5, 5 );

//test();

},

/**
*  File Name:          dowhile-002
*  ECMA Section:
*  Description:        do...while statements
*
*  Verify that code after a labeled break is not executed.  Verify that
*  a labeled break breaks you out of the whole labeled block, and not
*  just the current iteration statement.
*
*  Author:             christine@netscape.com
*  Date:               11 August 1998
*/
test_dowhile__002: function() {
var SECTION = "dowhile-002";
var VERSION = "ECMA_2";
var TITLE   = "do...while with a labeled continue statement";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

this.LabeledContinue2( 0, 1 );
this.LabeledContinue2( 1, 1 );
this.LabeledContinue2( -1, 1 );
this.LabeledContinue2( 5, 5 );

//test();

},

/**
*  File Name:          dowhile-003
*  ECMA Section:
*  Description:        do...while statements
*
*  Test do while, when the while expression is a JavaScript Number object.
*
*
*  Author:             christine@netscape.com
*  Date:               11 August 1998
*/
test_dowhile__003: function() {
var SECTION = "dowhile-003";
var VERSION = "ECMA_2";
var TITLE   = "do...while with a labeled continue statement";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

this.DoWhile3( new DoWhileObject( 1, 1, 0 ));
this.DoWhile3( new DoWhileObject( 1000, 1000, 0 ));
this.DoWhile3( new DoWhileObject( 1001, 1001, 0 ));
this.DoWhile3( new DoWhileObject( 1002, 1001, 1 ));
this.DoWhile3( new DoWhileObject( -1, 1001, -1002 ));

//test();

function DoWhileObject( value, iterations, endvalue ) {
this.value = value;
this.iterations = iterations;
this.endvalue = endvalue;
}

},

/**
*  File Name:          dowhile-004
*  ECMA Section:
*  Description:        do...while statements
*
*  Test a labeled do...while.  Break out of the loop with no label
*  should break out of the loop, but not out of the label.
*
*  Author:             christine@netscape.com
*  Date:               11 August 1998
*/
test_dowhile__004: function() {
var SECTION = "dowhile-004";
var VERSION = "ECMA_2";
var TITLE   = "do...while with a labeled continue statement";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

this.DoWhile4( 0, 1 );
this.DoWhile4( 1, 1 );
this.DoWhile4( -1, 1 );
this.DoWhile4( 5, 5 );

//test();

},

/**
*  File Name:          dowhile-005
*  ECMA Section:
*  Description:        do...while statements
*
*  Test a labeled do...while.  Break out of the loop with no label
*  should break out of the loop, but not out of the label.
*
*  Currently causes an infinite loop in the monkey.  Uncomment the
*  print statement below and it works OK.
*
*  Author:             christine@netscape.com
*  Date:               26 August 1998
*/
test_dowhile__005: function() {
var SECTION = "dowhile-005";
var VERSION = "ECMA_2";
var TITLE   = "do...while with a labeled continue statement";
var BUGNUMBER = "316293";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

this.NestedLabel();


//test();

},

/**
*  File Name:          dowhile-006
*  ECMA Section:
*  Description:        do...while statements
*
*  A general do...while test.
*
*  Author:             christine@netscape.com
*  Date:               26 August 1998
*/
test_dowhile__006: function() {
var SECTION = "dowhile-006";
var VERSION = "ECMA_2";
var TITLE   = "do...while";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

this.DoWhile6( new DoWhileObject( false, false, 10 ) );
this.DoWhile6( new DoWhileObject( true, false, 2 ) );
this.DoWhile6( new DoWhileObject( false, true, 3 ) );
this.DoWhile6( new DoWhileObject( true, true, 4 ) );

//test();


function looping( object ) {
object.iterations--;

if ( object.iterations <= 0 ) {
return false;
} else {
return true;
}
}

function DoWhileObject( breakOut, breakIn, iterations, loops ) {
this.iterations = iterations;
this.loops = loops;
this.breakOut = breakOut;
this.breakIn  = breakIn;
this.looping  = looping;
}

},

/**
*  File Name:          dowhile-007
*  ECMA Section:
*  Description:        do...while statements
*
*  A general do...while test.
*
*  Author:             christine@netscape.com
*  Date:               26 August 1998
*/
test_dowhile__007: function() {
var SECTION = "dowhile-007";
var VERSION = "ECMA_2";
var TITLE   = "do...while";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

this.DoWhile7( new DoWhileObject( false, false, false, false ));
this.DoWhile7( new DoWhileObject( true, false, false, false ));
this.DoWhile7( new DoWhileObject( true, true, false, false ));
this.DoWhile7( new DoWhileObject( true, true, true, false ));
this.DoWhile7( new DoWhileObject( true, true, true, true ));
this.DoWhile7( new DoWhileObject( false, false, false, true ));
this.DoWhile7( new DoWhileObject( false, false, true, true ));
this.DoWhile7( new DoWhileObject( false, true, true, true ));
this.DoWhile7( new DoWhileObject( false, false, true, false ));

//test();

function DoWhileObject( out1, out2, out3, in1 ) {
this.breakOutOne = out1;
this.breakOutTwo = out2;
this.breakOutThree = out3;
this.breakIn = in1;
}

},

/**
*  File Name:          forin-001.js
*  ECMA Section:
*  Description:        The forin-001 statement
*
*  Verify that the property name is assigned to the property on the left
*  hand side of the for...in expression.
*
*  Author:             christine@netscape.com
*  Date:               28 August 1998
*/
test_forin__001: function() {
var SECTION = "forin-001";
var VERSION = "ECMA_2";
var TITLE   = "The for...in  statement";
var BUGNUMBER="330890";
var BUGNUMBER="http://scopus.mcom.com/bugsplat/show_bug.cgi?id=344855";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

this.ForIn_1( { length:4, company:"netscape", year:2000, 0:"zero" } );
this.ForIn_2( { length:4, company:"netscape", year:2000, 0:"zero" } );
this.ForIn_3( { length:4, company:"netscape", year:2000, 0:"zero" } );

//    ForIn_6({ length:4, company:"netscape", year:2000, 0:"zero" });
//    ForIn_7({ length:4, company:"netscape", year:2000, 0:"zero" });
this.ForIn_8({ length:4, company:"netscape", year:2000, 0:"zero" });

//test();

},

/**
*  File Name:          forin-002.js
*  ECMA Section:
*  Description:        The forin-001 statement
*
*  Verify that the property name is assigned to the property on the left
*  hand side of the for...in expression.
*
*  Author:             christine@netscape.com
*  Date:               28 August 1998
*/
test_forin__002: function() {
var SECTION = "forin-002";
var VERSION = "ECMA_2";
var TITLE   = "The for...in  statement";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

function MyObject( value ) {
this.value = value;
this.valueOf = new Function ( "return this.value" );
this.toString = new Function ( "return this.value + \"\"" );
this.toNumber = new Function ( "return this.value + 0" );
this.toBoolean = new Function ( "return Boolean( this.value )" );
}

this.ForIn_12(this);
this.ForIn_22(this);

this.ForIn_12(new MyObject(true));
this.ForIn_22(new MyObject(new Boolean(true)));

this.ForIn_22(3);

//test();

},

/**
*  File Name:          if-001.js
*  ECMA Section:
*  Description:        The if statement
*
*  Verify that assignment in the if expression is evaluated correctly.
*  Verifies the fix for bug http://scopus/bugsplat/show_bug.cgi?id=148822.
*
*  Author:             christine@netscape.com
*  Date:               28 August 1998
*/
test_if__001: function() {
var SECTION = "for-001";
var VERSION = "ECMA_2";
var TITLE   = "The if  statement";
var BUGNUMBER="148822";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

var a = 0;
var b = 0;
var result = "passed";

if ( a = b ) {
result = "failed:  a = b should return 0";
}

this.TestCase(
"", //SECTION
"if ( a = b ), where a and b are both equal to 0",
"passed",
result );


//test();

},

/**
*  File Name:          label-001.js
*  ECMA Section:
*  Description:        Labeled statements
*
*  Labeled break and continue within a for loop.
*
*
*  Author:             christine@netscape.com
*  Date:               11 August 1998
*/
test_label__001: function() {
var SECTION = "label-003";
var VERSION = "ECMA_2";
var TITLE   = "Labeled statements";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

this.LabelTest(0, 0);
this.LabelTest(1, 1);
this.LabelTest(-1, 1000);
this.LabelTest(false,  0);
this.LabelTest(true, 1);

//test();

},

/**
*  File Name:          label-002.js
*  ECMA Section:
*  Description:        Labeled statements
*
*  Labeled break and continue within a for-in loop.
*
*
*  Author:             christine@netscape.com
*  Date:               11 August 1998
*/
test_label__002: function() {
var SECTION = "label-002";
var VERSION = "ECMA_2";
var TITLE   = "Labeled statements";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

this.LabelTest2( { p1:"hi,", p2:" norris" }, "hi, norris", " norrishi," );
this.LabelTest2( { 0:"zero", 1:"one" }, "zeroone", "onezero" );

this.LabelTest22( { p1:"hi,", p2:" norris" }, "hi,", " norris" );
this.LabelTest22( { 0:"zero", 1:"one" }, "zero", "one" );

//test();

},

/**
*  File Name:          switch-001.js
*  ECMA Section:
*  Description:        The switch Statement
*
*  A simple switch test with no abrupt completions.
*
*  Author:             christine@netscape.com
*  Date:               11 August 1998
*
*/
test_switch__001: function() {
var SECTION = "switch-001";
var VERSION = "ECMA_2";
var TITLE   = "The switch statement";

var BUGNUMBER="315767";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

this.SwitchTest1( 0, 126 );
this.SwitchTest1( 1, 124 );
this.SwitchTest1( 2, 120 );
this.SwitchTest1( 3, 112 );
this.SwitchTest1( 4, 64 );
this.SwitchTest1( 5, 96 );
this.SwitchTest1( true, 96 );
this.SwitchTest1( false, 96 );
this.SwitchTest1( null, 96 );
this.SwitchTest1( void 0, 96 );
this.SwitchTest1( "0", 96 );

//test();

},

/**
*  File Name:          switch-002.js
*  ECMA Section:
*  Description:        The switch Statement
*
*  A simple switch test with no abrupt completions.
*
*  Author:             christine@netscape.com
*  Date:               11 August 1998
*
*/
test_switch__002: function() {
var SECTION = "switch-002";
var VERSION = "ECMA_2";
var TITLE   = "The switch statement";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

this.SwitchTest2( 0, 6 );
this.SwitchTest2( 1, 4 );
this.SwitchTest2( 2, 56 );
this.SwitchTest2( 3, 48 );
this.SwitchTest2( 4, 64 );
this.SwitchTest2( true, 32 );
this.SwitchTest2( false, 32 );
this.SwitchTest2( null, 32 );
this.SwitchTest2( void 0, 32 );
this.SwitchTest2( "0", 32 );

//test();

},

/**
*  File Name:          switch-003.js
*  ECMA Section:
*  Description:        The switch Statement
*
*  Attempt to verify that case statements are evaluated in source order
*
*  Author:             christine@netscape.com
*  Date:               11 August 1998
*
*/
test_switch__003: function() {
var SECTION = "switch-003";
var VERSION = "ECMA_2";
var TITLE   = "The switch statement";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

this.SwitchTest3( "a", "abc" );
this.SwitchTest3( "b", "bc" );
this.SwitchTest3( "c", "c" );
this.SwitchTest3( "d", "*abc" );
this.SwitchTest3( "v", "*abc" );
this.SwitchTest3( "w", "w*abc" );
this.SwitchTest3( "x", "xw*abc" );
this.SwitchTest3( "y", "yxw*abc" );
this.SwitchTest3( "z", "zyxw*abc" );
//    SwitchTest( new java.lang.String("z"), "*abc" );

//test();

},

/**
*  File Name:          switch-003.js
*  ECMA Section:
*  Description:        The switch Statement
*
*  This uses variables and objects as case expressions in switch statements.
* This verifies a bunch of bugs:
*
* http://scopus.mcom.com/bugsplat/show_bug.cgi?id=315988
* http://scopus.mcom.com/bugsplat/show_bug.cgi?id=315975
* http://scopus.mcom.com/bugsplat/show_bug.cgi?id=315954
*
*  Author:             christine@netscape.com
*  Date:               11 August 1998
*
*/
test_switch__004: function() {
var SECTION = "switch-003";
var VERSION = "ECMA_2";
var TITLE   = "The switch statement";
var BUGNUMBER= "315988";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

ONE = new Number(1);
ZERO = new Number(0);
var A = new String("A");
var B = new String("B");
TRUE = new Boolean( true );
FALSE = new Boolean( false );
UNDEFINED  = void 0;
NULL = null;

//add parameters A, B
this.SwitchTest4( A, B,  ZERO, "ZERO" );
this.SwitchTest4( A, B,  NULL, "NULL" );
this.SwitchTest4( A, B,  UNDEFINED, "UNDEFINED" );
this.SwitchTest4( A, B,  FALSE, "FALSE" );
this.SwitchTest4( A, B,  false,  "false" );
this.SwitchTest4( A, B,  0,      "0" );

this.SwitchTest4( A, B,  TRUE, "TRUE" );
this.SwitchTest4( A, B,  1,     "1" );
this.SwitchTest4( A, B,  ONE,   "ONE" );
this.SwitchTest4( A, B,  true,  "true" );

this.SwitchTest4( A, B,  "a",   "a" );
this.SwitchTest4( A, B,  A,     "A" );
this.SwitchTest4( A, B,  "b",   "b" );
this.SwitchTest4( A, B,  B,     "B" );
this.SwitchTest4( A, B,  new Boolean(true ), "default" );
this.SwitchTest4( A, B,  new Boolean(false ), "default" );
this.SwitchTest4( A, B,  new String( "A" ),   "default" );
this.SwitchTest4( A, B,  new Number( 0 ),     "default" );

//test();

},

/**
*  File Name:          try-001.js
*  ECMA Section:
*  Description:        The try statement
*
*  This test contains try, catch, and finally blocks.  An exception is
*  sometimes thrown by a function called from within the try block.
*
*  This test doesn't actually make any LiveConnect calls.
*
*
*  Author:             christine@netscape.com
*  Date:               11 August 1998
*/
test_try__001: function() {
var SECTION = "";
var VERSION = "ECMA_2";
var TITLE   = "The try statement";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

var INVALID_JAVA_INTEGER_VALUE = "Invalid value for java.lang.Integer constructor";

this.TryNewJavaInteger( "3.14159", INVALID_JAVA_INTEGER_VALUE );
this.TryNewJavaInteger( NaN, INVALID_JAVA_INTEGER_VALUE );
this.TryNewJavaInteger( 0,  0 );
this.TryNewJavaInteger( -1, -1 );
this.TryNewJavaInteger( 1,  1 );
this.TryNewJavaInteger( Infinity, Infinity );

//test();

},

/**
*  File Name:          try-003.js
*  ECMA Section:
*  Description:        The try statement
*
*  This test has a try with no catch, and a finally.
*
*  Author:             christine@netscape.com
*  Date:               11 August 1998
*/
test_test__003: function() {
var SECTION = "try-003";
var VERSION = "ECMA_2";
var TITLE   = "The try statement";
var BUGNUMBER="http://scopus.mcom.com/bugsplat/show_bug.cgi?id=313585";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

// Tests start here.

this.TrySomething( "x = \"hi\"", false );
this.TrySomething( "throw \"boo\"", true );
this.TrySomething( "throw 3", true );

//test();

},

/**
*  File Name:          try-004.js
*  ECMA Section:
*  Description:        The try statement
*
*  This test has a try with one catch block but no finally.
*
*  Author:             christine@netscape.com
*  Date:               11 August 1998
*/
test_try__004: function() {
var SECTION = "try-004";
var VERSION = "ECMA_2";
var TITLE   = "The try statement";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

this.TryToCatch( "Math.PI", Math.PI );
this.TryToCatch( "Thrower(5)",   "Caught 5" );
this.TryToCatch( "Thrower(\"some random exception\")", "Caught some random exception" );

//test();

},

/**
*  File Name:          try-005.js
*  ECMA Section:
*  Description:        The try statement
*
*  This test has a try with one catch block but no finally.  Same
*  as try-004, but the eval statement is called from a function, not
*  directly from within the try block.
*
*  Author:             christine@netscape.com
*  Date:               11 August 1998
*/
test_try__005: function() {
var SECTION = "try-005";
var VERSION = "ECMA_2";
var TITLE   = "The try statement";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

this.TryToCatch5( "Math.PI", Math.PI );
this.TryToCatch5( "Thrower(5)",   "Caught 5" );
this.TryToCatch5( "Thrower(\"some random exception\")", "Caught some random exception" );

//test();

},

/**
*  File Name:          try-006.js
*  ECMA Section:
*  Description:        The try statement
*
*  Throw an exception from within a With block in a try block.  Verify
*  that any expected exceptions are caught.
*
*  Author:             christine@netscape.com
*  Date:               11 August 1998
*/
test_try__006: function() {
var SECTION = "try-006";
var VERSION = "ECMA_2";
var TITLE   = "The try statement";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

/**
*  This is the "check" function for test objects that will
*  throw an exception.
*/
var EXCEPTION_STRING = "Exception thrown:";
function throwException() {
throw EXCEPTION_STRING +": " + this.valueOf();
}

/**
*  This is the "check" function for test objects that do not
*  throw an exception
*/
function noException() {
return this.valueOf();
}

/**
*  Add test cases here
*/
this.TryWith( new TryObject( "hello", throwException, true ));
this.TryWith( new TryObject( "hola",  noException, false ));

/**
*  Run the test.
*/

//test();

/**
*  This is the object that will be the "this" in a with block.
*/
function TryObject( value, fun, exception ) {
this.value = value;
this.exception = exception;

this.valueOf = new Function ( "return this.value" );
this.check = fun;
}

},


/**
*  File Name:          try-007.js
*  ECMA Section:
*  Description:        The try statement
*
*  This test has a for-in statement within a try block.
*
*
*  Author:             christine@netscape.com
*  Date:               11 August 1998
*/
test_try__007: function() {
var SECTION = "try-007";
var VERSION = "ECMA_2";
var TITLE   = "The try statement:  for-in";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

/**
*  This is the "check" function for test objects that will
*  throw an exception.
*/
var EXCEPTION_STRING = "Exception thrown:";
function throwException() {
throw EXCEPTION_STRING +": " + this.valueOf();
}

/**
*  This is the "check" function for test objects that do not
*  throw an exception
*/
function noException() {
return this.valueOf();
}

/**
*  Add test cases here
*/
this.TryForIn( new TryObject( "hello", throwException, true ));
this.TryForIn( new TryObject( "hola",  noException, false ));

/**
*  Run the test.
*/

//test();

/**
*  This is the object that will be the "this" in a with block.
*  The check function is either throwException() or noException().
*  See above.
*
*/
function TryObject( value, fun, exception ) {
this.value = value;
this.exception = exception;

this.check = fun;
this.valueOf = function () { return this.value; }
}
},

/**
*  File Name:          try-008.js
*  ECMA Section:
*  Description:        The try statement
*
*  This test has a try block in a constructor.
*
*
*  Author:             christine@netscape.com
*  Date:               11 August 1998
*/
test_try__008: function() {
var SECTION = "try-008";
var VERSION = "ECMA_2";
var TITLE   = "The try statement: try in a constructor";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

// add test cases

this.Integer( 3, false );
this.Integer( NaN, true );
this.Integer( 0, false );
this.Integer(Infinity, false );
this.Integer( -2.12, true );
this.Integer( Math.LN2, true );


//test();

},

/**
*  File Name:          try-009.js
*  ECMA Section:
*  Description:        The try statement
*
*  This test has a try block within a while block.  Verify that an exception
*  breaks out of the while.  I don't really know why this is an interesting
*  test case but Mike Shaver had two of these so what the hey.
*
*  Author:             christine@netscape.com
*  Date:               11 August 1998
*/
test_try__009: function() {
var SECTION = "try-009";
var VERSION = "ECMA_2";
var TITLE   = "The try statement: try in a while block";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

var EXCEPTION_STRING = "Exception thrown: ";
var NO_EXCEPTION_STRING = "No exception thrown: ";


this.TryInWhile( new TryObject( "hello", ThrowException, true ) );
this.TryInWhile( new TryObject( "aloha", NoException, false ));

//test();

function TryObject( value, throwFunction, result ) {
this.value = value;
this.thrower = throwFunction;
this.result = result;
}
function ThrowException() {
throw EXCEPTION_STRING + this.value;
}
function NoException() {
return NO_EXCEPTION_STRING + this.value;
}

},

/**
*  File Name:          try-010.js
*  ECMA Section:
*  Description:        The try statement
*
*  This has a try block nested in the try block.  Verify that the
*  exception is caught by the right try block, and all finally blocks
*  are executed.
*
*  Author:             christine@netscape.com
*  Date:               11 August 1998
*/
test_try__010: function() {
var SECTION = "try-010";
var VERSION = "ECMA_2";
var TITLE   = "The try statement: try in a tryblock";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

var EXCEPTION_STRING = "Exception thrown: ";
var NO_EXCEPTION_STRING = "No exception thrown:  ";


this.NestedTry( new TryObject( "No Exceptions Thrown",  NoException, NoException, 43 ) );
this.NestedTry( new TryObject( "Throw Exception in Outer Try", ThrowException, NoException, 48 ));
this.NestedTry( new TryObject( "Throw Exception in Inner Try", NoException, ThrowException, 45 ));
this.NestedTry( new TryObject( "Throw Exception in Both Trys", ThrowException, ThrowException, 48 ));

//test();

function TryObject( description, tryOne, tryTwo, result ) {
this.description = description;
this.tryOne = tryOne;
this.tryTwo = tryTwo;
this.result = result;
}

function ThrowException() {
throw EXCEPTION_STRING + this.value;
}
function NoException() {
return NO_EXCEPTION_STRING + this.value;
}

},

/**
*  File Name:          try-012.js
*  ECMA Section:
*  Description:        The try statement
*
*  This test has a try with no catch, and a finally.  This is like try-003,
*  but throws from a finally block, not the try block.
*
*  Author:             christine@netscape.com
*  Date:               11 August 1998
*/
test_try__012: function() {
var SECTION = "try-012";
var VERSION = "ECMA_2";
var TITLE   = "The try statement";
var BUGNUMBER="336872";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

// Tests start here.

this.TrySomething( "x = \"hi\"", true );
this.TrySomething( "throw \"boo\"", true );
this.TrySomething( "throw 3", true );

//test();

},

/**
*  File Name:          while-001
*  ECMA Section:
*  Description:        while statement
*
*  Verify that the while statement is not executed if the while expression is
*  false
*
*  Author:             christine@netscape.com
*  Date:               11 August 1998
*/
test_while__001: function() {
var SECTION = "while-001";
var VERSION = "ECMA_2";
var TITLE   = "while statement";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

this.DoWhile();
//test();

},

/**
*  File Name:          while-002
*  ECMA Section:
*  Description:        while statement
*
*  Verify that the while statement is not executed if the while expression is
*  false
*
*  Author:             christine@netscape.com
*  Date:               11 August 1998
*/
test_while__002: function() {
var SECTION = "while-002";
var VERSION = "ECMA_2";
var TITLE   = "while statement";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

this.DoWhile2( new DoWhileObject(
"while expression is null",
null,
"result = \"fail: should not have evaluated statements in while block;break"
) );

this.DoWhile2( new DoWhileObject(
"while expression is undefined",
void 0,
"result = \"fail: should not have evaluated statements in while block; break"
));

this.DoWhile2( new DoWhileObject(
"while expression is 0",
0,
"result = \"fail: should not have evaluated statements in while block; break;"
));

this.DoWhile2( new DoWhileObject(
"while expression is eval(\"\")",
eval(""),
"result = \"fail: should not have evaluated statements in while block; break"
));

this.DoWhile2( new DoWhileObject(
"while expression is NaN",
NaN,
"result = \"fail: should not have evaluated statements in while block; break"
));

//test();

function DoWhileObject( d, e, s ) {
this.description = d;
this.whileExpression = e;
this.statements = s;
}

},

/**
*  File Name:          while-003
*  ECMA Section:
*  Description:        while statement
*
*  The while expression evaluates to true, Statement returns abrupt completion.
*
*  Author:             christine@netscape.com
*  Date:               11 August 1998
*/
test_while__003: function() {
var SECTION = "while-003";
var VERSION = "ECMA_2";
var TITLE   = "while statement";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

this.DoWhile33( new DoWhileObject(
"while expression is true",
true,
"result = \"pass\";" ));

this.DoWhile33( new DoWhileObject(
"while expression is 1",
1,
"result = \"pass\";" ));

this.DoWhile33( new DoWhileObject(
"while expression is new Boolean(false)",
new Boolean(false),
"result = \"pass\";" ));

this.DoWhile33( new DoWhileObject(
"while expression is new Object()",
new Object(),
"result = \"pass\";" ));

this.DoWhile33( new DoWhileObject(
"while expression is \"hi\"",
"hi",
"result = \"pass\";" ));
/*
DoWhile( new DoWhileObject(
"while expression has a continue in it",
"true",
"if ( i == void 0 ) i = 0; result=\"pass\"; if ( ++i == 1 ) {continue;} else {break;} result=\"fail\";"
));
*/
//test();

function DoWhileObject( d, e, s ) {
this.description = d;
this.whileExpression = e;
this.statements = s;
}

},

/**
*  File Name:          while-004
*  ECMA Section:
*  Description:        while statement
*
*  Author:             christine@netscape.com
*  Date:               11 August 1998
*/
test_while__004: function() {
var SECTION = "while-004";
var VERSION = "ECMA_2";
var TITLE   = "while statement";
var BUGNUMBER="316725";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

this.DoWhile_14();
this.DoWhile_24();
this.DoWhile_34();
this.DoWhile_44();
this.DoWhile_54();

//test();

}


})
.endType();

