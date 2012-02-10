vjo.ctype("dsf.jslang.feature.tests.Ecma2ExceptionsTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({

/**
File Name:          boolean-001.js
Description:        Corresponds to ecma/Boolean/15.6.4.2-4-n.js

The toString function is not generic; it generates
a runtime error if its this value is not a Boolean
object.  Therefore it cannot be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               june 27, 1997
*/
test_boolean__001:function(){

var SECTION = "boolean-001.js";
var VERSION = "JS1_4";
var TITLE   = "Boolean.prototype.toString()";
// startTest();
// writeHeaderToLog( SECTION +" "+ TITLE );

var exception = "No exception thrown";
var result = "Failed";

var TO_STRING = Boolean.prototype.toString;

try {
var s = new String("Not a Boolean");
s.toString = TO_STRING;
s.toString();
} catch ( e ) {
result = "Passed!";
exception = e.toString();
}

this.TestCase(
SECTION,
"Assigning Boolean.prototype.toString to a String object "+
"(threw " +exception +")",
"Passed!",
result );

// test();


},

/**
File Name:          boolean-001.js
Description:        Corresponds to ecma/Boolean/15.6.4.3-4-n.js

15.6.4.3 Boolean.prototype.valueOf()
Returns this boolean value.

The valueOf function is not generic; it generates
a runtime error if its this value is not a Boolean
object.  Therefore it cannot be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               09 september 1998
*/
test_boolean__002:function(){

var SECTION = "boolean-002.js";
var VERSION = "JS1_4";
var TITLE   = "Boolean.prototype.valueOf()";
// startTest();
// writeHeaderToLog( SECTION +" "+ TITLE );


var exception = "No exception thrown";
var result = "Failed";

var VALUE_OF = Boolean.prototype.valueOf;

try {
var s = new String("Not a Boolean");
s.valueOf = VALUE_OF;
s.valueOf();
} catch ( e ) {
result = "Passed!";
exception = e.toString();
}

this.TestCase(
SECTION,
"Assigning Boolean.prototype.valueOf to a String object "+
"(threw " +exception +")",
"Passed!",
result );

// test();


},

/**
File Name:          date-001.js
Corresponds To:     15.9.5.2-2.js
ECMA Section:       15.9.5.2 Date.prototype.toString
Description:
This function returns a string value. The contents of the string are
implementation dependent, but are intended to represent the Date in a
convenient, human-readable form in the current time zone.

The toString function is not generic; it generates a runtime error if its
this value is not a Date object. Therefore it cannot be transferred to
other kinds of objects for use as a method.


This verifies that calling toString on an object that is not a string
generates a runtime error.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_date__001:function(){

var SECTION = "date-001";
var VERSION = "JS1_4";
var TITLE   = "Date.prototype.toString";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
var OBJ = new Object( new Date(0) );
result = OBJ.toString();
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"OBJECT = new MyObject( new Date(0)) ; result = OBJ.toString()" +
" (threw " + exception +")",
expect,
result );

// test();

},

/**
File Name:          date-002.js
Corresponds To:     15.9.5.23-3-n.js
ECMA Section:       15.9.5.23
Description:        Date.prototype.setTime

1.  If the this value is not a Date object, generate a runtime error.
2.  Call ToNumber(time).
3.  Call TimeClip(Result(1)).
4.  Set the [[Value]] property of the this value to Result(2).
5.  Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_date__002:function(){

var SECTION = "date-002";
var VERSION = "JS1_4";
var TITLE   = "Date.prototype.setTime()";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
var MYDATE = new Date();
result = MYDATE.setTime(0);
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"MYDATE = new MyDate(); MYDATE.setTime(0)" +
" (threw " + exception +")",
expect,
result );

// test();


},

/**
File Name:          date-003.js
Corresponds To      15.9.5.3-1.js
ECMA Section:       15.9.5.3-1 Date.prototype.valueOf
Description:

The valueOf function returns a number, which is this time value.

The valueOf function is not generic; it generates a runtime error if
its this value is not a Date object.  Therefore it cannot be transferred
to other kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_date__003:function(){

var SECTION = "date-003";
var VERSION = "JS1_4";
var TITLE   = "Date.prototype.valueOf";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
var OBJ = new Object( new Date(0) );
result = OBJ.valueOf();
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"OBJ = new MyObject( new Date(0)); OBJ.valueOf()" +
" (threw " + exception +")",
expect,
result );

// test();

},

/**
File Name:          date-004.js
Corresponds To:     15.9.5.4-2-n.js
ECMA Section:       15.9.5.4-1 Date.prototype.getTime
Description:

1.  If the this value is not an object whose [[Class]] property is "Date",
generate a runtime error.
2.  Return this time value.
Author:             christine@netscape.com
Date:               12 november 1997
*/
test_date__004:function(){

var SECTION = "date-004";
var VERSION = "JS1_4";
var TITLE   = "Date.prototype.getTime";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
var MYDATE = new Date();
result = MYDATE.getTime();
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"MYDATE = new MyDate(); MYDATE.getTime()" +
" (threw " + exception +")",
expect,
result );

// test();

},

/**
*  File Name:          exception-001
*  ECMA Section:
*  Description:        Tests for JavaScript Standard Exceptions
*
*  Call error.
*
*  Author:             christine@netscape.com
*  Date:               31 August 1998
*/
test_exception__001:function(){
var SECTION = "exception-001";
var VERSION = "js1_4";
var TITLE   = "Tests for JavaScript Standard Exceptions:  CallError";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

// test();
result = "failed: no exception thrown";
exception = null;
try {
Math();
} catch ( e ) {
result = "passed:  threw exception",
exception = e.toString();
} finally {
this.TestCase(
SECTION,
"Math() [ exception is " + exception +" ]",
"passed:  threw exception",
result );
}



},

/**
*  File Name:          exception-002
*  ECMA Section:
*  Description:        Tests for JavaScript Standard Exceptions
*
*  Construct error.
*
*  Author:             christine@netscape.com
*  Date:               31 August 1998
*/
test_exception__002:function(){
var SECTION = "exception-002";
var VERSION = "js1_4";
var TITLE   = "Tests for JavaScript Standard Exceptions: ConstructError";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

result = "failed: no exception thrown";
exception = null;

try {
result = new Math();
} catch ( e ) {
result = "passed:  threw exception",
exception = e.toString();
} finally {
this.TestCase(
SECTION,
"new Math() [ exception is " + exception +" ]",
"passed:  threw exception",
result );
}


},

/**
*  File Name:          exception-003
*  ECMA Section:
*  Description:        Tests for JavaScript Standard Exceptions
*
*  Target error.
*
*  Author:             christine@netscape.com
*  Date:               31 August 1998
*/
test_exception__003:function(){
var SECTION = "exception-003";
var VERSION = "js1_4";
var TITLE   = "Tests for JavaScript Standard Exceptions: TargetError";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);
result = "failed: no exception thrown";
exception = null;

try {
string = new String("hi");
string.toString = Boolean.prototype.toString;
string.toString();
} catch ( e ) {
result = "passed:  threw exception",
exception = e.toString();
} finally {
this.TestCase(
SECTION,
"string = new String(\"hi\");"+
"string.toString = Boolean.prototype.toString" +
"string.toString() [ exception is " + exception +" ]",
"passed:  threw exception",
result );
}

},

/**
*  File Name:          exception-004
*  ECMA Section:
*  Description:        Tests for JavaScript Standard Exceptions
*
*  ToObject error.
*
*  Author:             christine@netscape.com
*  Date:               31 August 1998
*/
test_exception__004:function(){
var SECTION = "exception-004";
var VERSION = "js1_4";
var TITLE   = "Tests for JavaScript Standard Exceptions: ToObjectError";
result = "failed: no exception thrown";
exception = null;
var foo = null;
try {
result = foo["bar"];
} catch ( e ) {
result = "passed:  threw exception",
exception = e.toString();
} finally {
this.TestCase(
SECTION,
"foo[\"bar\"] [ exception is " + exception +" ]",
"passed:  threw exception",
result );
}


},

/**
*  File Name:          exception-005
*  ECMA Section:
*  Description:        Tests for JavaScript Standard Exceptions
*
*  ToObject error.
*
*  Author:             christine@netscape.com
*  Date:               31 August 1998
*/
test_exception__005:function(){
var SECTION = "exception-005";
var VERSION = "js1_4";
var TITLE   = "Tests for JavaScript Standard Exceptions: ToObjectError";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

result = "failed: no exception thrown";
exception = null;
var foo = null;
try {
result = foo["bar"];
} catch ( e ) {
result = "passed:  threw exception",
exception = e.toString();
} finally {
this.TestCase(
SECTION,
"foo[\"bar\"] [ exception is " + exception +" ]",
"passed:  threw exception",
result );
}

},

/**
*  File Name:          exception-006
*  ECMA Section:
*  Description:        Tests for JavaScript Standard Exceptions
*
*  ToPrimitive error.
*
*  Author:             christine@netscape.com
*  Date:               31 August 1998
*/
test_exception__006:function(){
var SECTION = "exception-006";
var VERSION = "js1_4";
var TITLE   = "Tests for JavaScript Standard Exceptions: TypeError";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);



/**
* Getting the [[DefaultValue]] of any instances of MyObject
* should result in a runtime error in ToPrimitive.
*/

result = "failed: no exception thrown";
exception = null;

try {
result = new Error() + new Error();
} catch ( e ) {
result = "passed:  threw exception",
exception = e.toString();
} finally {
this.TestCase(
SECTION,
"new MyObject() + new MyObject() [ exception is " + exception +" ]",
"passed:  threw exception",
result );
}


},

/**
*  File Name:          exception-007
*  ECMA Section:
*  Description:        Tests for JavaScript Standard Exceptions
*
*  DefaultValue error.
*
*  Author:             christine@netscape.com
*  Date:               31 August 1998
*/
test_exception__007:function(){
var SECTION = "exception-007";
var VERSION = "js1_4";
var TITLE   = "Tests for JavaScript Standard Exceptions:  TypeError";
var BUGNUMBER="318250";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

// test();


/**
* Getting the [[DefaultValue]] of any instances of MyObject
* should result in a runtime error in ToPrimitive.
*/

result = "failed: no exception thrown";
exception = null;

try {
result = new Error() + new Error();
} catch ( e ) {
result = "passed:  threw exception",
exception = e.toString();
} finally {
this.TestCase(
SECTION,
"new MyObject() + new MyObject() [ exception is " + exception +" ]",
"passed:  threw exception",
result );
}

},

/**
*  File Name:          exception-008
*  ECMA Section:
*  Description:        Tests for JavaScript Standard Exceptions
*
*  SyntaxError.
*
*  Author:             christine@netscape.com
*  Date:               31 August 1998
*/
test_exception__008:function(){
var SECTION = "exception-008";
var VERSION = "js1_4";
var TITLE   = "Tests for JavaScript Standard Exceptions: SyntaxError";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

result = "failed: no exception thrown";
exception = null;

try {
result = eval("continue;");
} catch ( e ) {
result = "passed:  threw exception",
exception = e.toString();
} finally {
this.TestCase(
SECTION,
"eval(\"continue\") [ exception is " + exception +" ]",
"passed:  threw exception",
result );
}

},

/**
*  File Name:          exception-009
*  ECMA Section:
*  Description:        Tests for JavaScript Standard Exceptions
*
*  Regression test for nested try blocks.
*
*  http://scopus.mcom.com/bugsplat/show_bug.cgi?id=312964
*
*  Author:             christine@netscape.com
*  Date:               31 August 1998
*/
test_exception__009:function(){
var SECTION = "exception-009";
var VERSION = "JS1_4";
var TITLE   = "Tests for JavaScript Standard Exceptions: SyntaxError";
var BUGNUMBER= "312964";
// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);
var Nested_exception_009 = null;
try {
expect = "passed:  no exception thrown";
result = expect;
Nested_exception_009();
} catch ( e ) {
result = "failed: threw " + e;
} finally {
this.TestCase(
SECTION,
"nested try",
expect,
result );
}


// test();

},

test_exception__010__n:function(){
var SECTION = "exception-010";
var VERSION = "ECMA_2";
// startTest();
var TITLE   = "Don't Crash throwing null";

// writeHeaderToLog( SECTION + " "+ TITLE);
//print("Null throw test.");
//print("BUGNUMBER: 21799");

DESCRIPTION = "throw null";
EXPECTED = "error";

this.TestCase( SECTION,  "throw null",     "error",    eval("throw null" ));

// test();

//print("FAILED!: Should have exited with uncaught exception.");



},

test_exception__011__n:function(){
var SECTION = "exception-011";
var VERSION = "ECMA_2";
// startTest();
var TITLE   = "Don't Crash throwing undefined";

// writeHeaderToLog( SECTION + " "+ TITLE);

print("Undefined throw test.");

DESCRIPTION = "throw undefined";
EXPECTED = "error";

this.TestCase( SECTION,  "throw undefined",  "error", eval("throw (void 0)") );

// test();

print("FAILED!: Should have exited with uncaught exception.");




},

/**
File Name:          expression-001.js
Corresponds to:     ecma/Expressions/11.12-2-n.js
ECMA Section:       11.12
Description:

The grammar for a ConditionalExpression in ECMAScript is a little bit
different from that in C and Java, which each allow the second
subexpression to be an Expression but restrict the third expression to
be a ConditionalExpression.  The motivation for this difference in
ECMAScript is to allow an assignment expression to be governed by either
arm of a conditional and to eliminate the confusing and fairly useless
case of a comma expression as the center expression.

Author:             christine@netscape.com
Date:               09 september 1998
*/
test_expression__001:function(){

var SECTION = "expression-001";
var VERSION = "JS1_4";
var TITLE   = "Conditional operator ( ? : )"
//  startTest();
// writeHeaderToLog( SECTION + " " + TITLE );

// the following expression should be an error in JS.

var result = "Failed"
var exception = "No exception was thrown";

try {
eval("var MY_VAR = true ? \"EXPR1\", \"EXPR2\" : \"EXPR3\"");
} catch ( e ) {
result = "Passed";
exception = e.toString();
}

this.TestCase(
SECTION,
"comma expression in a conditional statement "+
"(threw "+ exception +")",
"Passed",
result );


// test();

},

/**
File Name:          expressions-002.js
Corresponds to:     ecma/Expressions/11.2.1-3-n.js
ECMA Section:       11.2.1 Property Accessors
Description:

Try to access properties of an object whose value is undefined.

Author:             christine@netscape.com
Date:               09 september 1998
*/
test_expression__002:function(){

var SECTION = "expressions-002.js";
var VERSION = "JS1_4";
var TITLE   = "Property Accessors";
// writeHeaderToLog( SECTION + " "+TITLE );

// startTest();

// go through all Native Function objects, methods, and properties and get their typeof.

var PROPERTY = new Array();
var p = 0;

// try to access properties of primitive types

OBJECT = new Object(  "undefined",    void 0,   "undefined",   NaN );

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
result = OBJECT.value.valueOf();
} catch ( e ) {
result = expect;
exception = e.toString();
}


this.TestCase(
SECTION,
"Get the value of an object whose value is undefined "+
"(threw " + exception +")",
expect,
result );

// test();


},

/**
File Name:          expressions-003.js
Corresponds to:     ecma/Expressions/11.2.1-3-n.js
ECMA Section:       11.2.1 Property Accessors
Description:

Try to access properties of an object whose value is undefined.

Author:             christine@netscape.com
Date:               09 september 1998
*/
test_expression__003:function(){

var SECTION = "expressions-003.js";
var VERSION = "JS1_4";
var TITLE   = "Property Accessors";
// writeHeaderToLog( SECTION + " "+TITLE );

// startTest();

// try to access properties of primitive types

OBJECT = new Object(  "undefined",    void 0,   "undefined",   NaN );

var result    = "Failed";
var exception = "No exception thrown";
var expect    = "Passed";

try {
result = OBJECT.value.toString();
} catch ( e ) {
result = expect;
exception = e.toString();
}


this.TestCase(
SECTION,
"Get the toString value of an object whose value is undefined "+
"(threw " + exception +")",
expect,
result );

// test();


},

/**
File Name:          expression-004.js
Corresponds To:     11.2.1-4-n.js
ECMA Section:       11.2.1 Property Accessors
Description:

Author:             christine@netscape.com
Date:               09 september 1998
*/
test_expression__004:function(){

var SECTION = "expression-004";
var VERSION = "JS1_4";
var TITLE   = "Property Accessors";
// writeHeaderToLog( SECTION + " "+TITLE );
// startTest();

var OBJECT = new Object( "null", null, "null", 0 );

var result    = "Failed";
var exception = "No exception thrown";
var expect    = "Passed";

try {
result = OBJECT.value.toString();
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"Get the toString value of an object whose value is null "+
"(threw " + exception +")",
expect,
result );

// test();
},

/**
File Name:          expression-005.js
Corresponds To:     11.2.2-10-n.js
ECMA Section:       11.2.2. The new operator
Description:

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_expression__005:function(){


var SECTION = "expression-005";
var VERSION = "JS1_4";
var TITLE   = "The new operator";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var expect = "Passed";
var exception = "No exception thrown";

try {
result = new Math();
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"result= new Math() (threw " + exception + ")",
expect,
result );

// test();

},

/**
File Name:          expression-006.js
Corresponds to:     11.2.2-1-n.js
ECMA Section:       11.2.2. The new operator
Description:

http://scopus/bugsplat/show_bug.cgi?id=327765

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_expression__006:function(){

var SECTION = "expression-006.js";
var VERSION = "JS1_4";
var TITLE   = "The new operator";
var BUGNUMBER="327765";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
var OBJECT = new Object();
result = new OBJECT();
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"OBJECT = new Object; result = new OBJECT()" +
" (threw " + exception +")",
expect,
result );

// test();


},

/**
File Name:          expression-007.js
Corresponds To:     11.2.2-2-n.js
ECMA Section:       11.2.2. The new operator
Description:


Author:             christine@netscape.com
Date:               12 november 1997
*/
test_expression__007:function(){

var SECTION = "expression-007";
var VERSION = "JS1_4";
var TITLE   = "The new operator";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
UNDEFINED = void 0;
result = new UNDEFINED();
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"UNDEFINED = void 0; result = new UNDEFINED()" +
" (threw " + exception +")",
expect,
result );

// test();


},

/**
File Name:          expression-008
Corresponds To:     11.2.2-3-n.js
ECMA Section:       11.2.2. The new operator
Description:
Author:             christine@netscape.com
Date:               12 november 1997
*/
test_expression__008:function(){

var SECTION = "expression-008";
var VERSION = "JS1_4";
var TITLE   = "The new operator";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var NULL = null;
var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
result = new NULL();
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"NULL = null; result = new NULL()" +
" (threw " + exception +")",
expect,
result );

// test();

},

/**
File Name:          expression-009
Corresponds to:     ecma/Expressions/11.2.2-4-n.js
ECMA Section:       11.2.2. The new operator
Description:
Author:             christine@netscape.com
Date:               12 november 1997
*/
test_expression__009:function(){

var SECTION = "expression-009";
var VERSION = "JS1_4";
var TITLE   = "The new operator";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var STRING = "";

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
result = new STRING();
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"STRING = ''; result = new STRING()" +
" (threw " + exception +")",
expect,
result );

// test();

},

/**
File Name:          expression-010.js
Corresponds To:     11.2.2-5-n.js
ECMA Section:       11.2.2. The new operator
Description:
Author:             christine@netscape.com
Date:               12 november 1997
*/
test_expression__010:function(){

var SECTION = "expression-010";
var VERSION = "JS1_4";
var TITLE   = "The new operator";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var NUMBER = 0;

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
result = new NUMBER();
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"NUMBER=0, result = new NUMBER()" +
" (threw " + exception +")",
expect,
result );

// test();


},

/**
File Name:          expression-011.js
Corresponds To:     ecma/Expressions/11.2.2-6-n.js
ECMA Section:       11.2.2. The new operator
Description:
Author:             christine@netscape.com
Date:               12 november 1997
*/
test_expression__011:function(){

var SECTION = "expression-011";
var VERSION = "JS1_4";
var TITLE   = "The new operator";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var BOOLEAN  = true;

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
var OBJECT = new BOOLEAN();
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"BOOLEAN = true; result = new BOOLEAN()" +
" (threw " + exception +")",
expect,
result );

// test();


},

/**
File Name:          expression-012.js
Corresponds To:     ecma/Expressions/11.2.2-6-n.js
ECMA Section:       11.2.2. The new operator
Description:
http://scopus/bugsplat/show_bug.cgi?id=327765
Author:             christine@netscape.com
Date:               12 november 1997
*/
test_expression__012:function(){

var SECTION = "expression-012";
var VERSION = "JS1_4";
var TITLE   = "The new operator";
var BUGNUMBER= "327765";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var STRING = new String("hi");
var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
result = new STRING();
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"STRING = new String(\"hi\"); result = new STRING()" +
" (threw " + exception +")",
expect,
result );

// test();


},

/**
File Name:          expression-013.js
Corresponds To:     ecma/Expressions/11.2.2-8-n.js
ECMA Section:       11.2.2. The new operator
Description:
Author:             christine@netscape.com
Date:               12 november 1997
*/
test_expression__013:function(){

var SECTION = "expression-013";
var VERSION = "JS1_4";
var TITLE   = "The new operator";
var BUGNUMBER= "327765";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var NUMBER = new Number(1);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
result = new NUMBER();
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"NUMBER = new Number(1); result = new NUMBER()" +
" (threw " + exception +")",
expect,
result );

// test();


},

/**
File Name:          expression-014.js
Corresponds To:     ecma/Expressions/11.2.2-9-n.js
ECMA Section:       11.2.2. The new operator
Description:

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_expression__014:function(){

var SECTION = "expression-014.js";
var VERSION = "ECMA_1";
var TITLE   = "The new operator";
var BUGNUMBER= "327765";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var BOOLEAN = new Boolean();


var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
result = new BOOLEAN();
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"BOOLEAN = new Boolean(); result = new BOOLEAN()" +
" (threw " + exception +")",
expect,
result );

// test();


},

/**
File Name:          expression-015.js
Corresponds To:     ecma/Expressions/11.2.3-2-n.js
ECMA Section:       11.2.3. Function Calls
Description:
Author:             christine@netscape.com
Date:               12 november 1997
*/
test_expression__015:function(){

var SECTION = "expression-015";
var VERSION = "JS1_4";
var TITLE   = "Function Calls";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("result = 3.valueOf();");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"3.valueOf()" +
" (threw " + exception +")",
expect,
result );

// test();

},

/**
File Name:          expression-016.js
Corresponds To:     ecma/Expressions/11.2.3-3-n.js
ECMA Section:       11.2.3. Function Calls
Description:
Author:             christine@netscape.com
Date:               12 november 1997
*/
test_expression__016:function(){

var SECTION = "expression-016";
var VERSION = "JS1_4";
var TITLE   = "Function Calls";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
result = (void 0).valueOf();
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"(void 0).valueOf()" +
" (threw " + exception +")",
expect,
result );

// test();

},

/**
File Name:          expression-07.js
Corresponds To:     ecma/Expressions/11.2.3-4-n.js
ECMA Section:       11.2.3. Function Calls
Description:
Author:             christine@netscape.com
Date:               12 november 1997
*/
test_expression__017:function(){

var SECTION = "expression-017";
var VERSION = "JS1_4";
var TITLE   = "Function Calls";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";
var nullvalueOf = null;
try {
result = nullvalueOf();
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"null.valueOf()" +
" (threw " + exception +")",
expect,
result );

// test();

},

/**
File Name:          expression-019.js
Corresponds To:     11.2.2-7-n.js
ECMA Section:       11.2.2. The new operator
Description:

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_expression__019:function(){

var SECTION = "expression-019";
var VERSION = "JS1_4";
var TITLE   = "The new operator";
var BUGNUMBER= "327765";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
var STRING = new String("hi");
result = new STRING();
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"var STRING = new String(\"hi\"); result = new STRING();" +
" (threw " + exception + ")",
expect,
result );

// test();


},

/**
*  File Name:          boolean-001.js
*  Description:
*
*  http://scopus.mcom.com/bugsplat/show_bug.cgi?id=99232
*
*  eval("function f(){}function g(){}") at top level is an error for JS1.2
*     and above (missing ; between named function expressions), but declares f
*     and g as functions below 1.2.
*
* Fails to produce error regardless of version:
* js> version(100)
* 120
* js> eval("function f(){}function g(){}")
* js> version(120);
* 100
* js> eval("function f(){}function g(){}")
* js>
*  Author:             christine@netscape.com
*  Date:               11 August 1998
*/
test_function__001:function(){
var SECTION = "function-001.js";
var VERSION = "JS_12";
var TITLE   = "functions not separated by semicolons are errors in version 120 and higher";
var BUGNUMBER="10278";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "pass";
var exception = "no exception thrown";

try {
eval("function f(){}function g(){}");
} catch ( e ) {
result = "fail";
exception = e.toString();
}

this.TestCase(
SECTION,
"eval(\"function f(){}function g(){}\") (threw "+exception,
"pass",
result );

// test();


},

/**
File Name:          global-001
Corresponds To:     ecma/GlobalObject/15.1-1-n.js
ECMA Section:       The global object
Description:

The global object does not have a [[Construct]] property; it is not
possible to use the global object as a constructor with the new operator.


Author:             christine@netscape.com
Date:               12 november 1997
*/
test_global__001:function(){

var SECTION = "global-001";
var VERSION = "ECMA_1";
var TITLE   = "The Global Object";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
result = new this();
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"result = new this()" +
" (threw " + exception +")",
expect,
result );

// test();

},

/**
File Name:          global-002
Corresponds To:     ecma/GlobalObject/15.1-2-n.js
ECMA Section:       The global object
Description:

The global object does not have a [[Construct]] property; it is not
possible to use the global object as a constructor with the new operator.


Author:             christine@netscape.com
Date:               12 november 1997
*/
test_global__002:function(){

var SECTION = "global-002";
var VERSION = "JS1_4";
var TITLE   = "The Global Object";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
result = this();
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"result = this()" +
" (threw " + exception +")",
expect,
result );

// test();

},

/**
File Name:          lexical-001.js
CorrespondsTo:      ecma/LexicalConventions/7.2.js
ECMA Section:       7.2 Line Terminators
Description:        - readability
- separate tokens
- may occur between any two tokens
- cannot occur within any token, not even a string
- affect the process of automatic semicolon insertion.

white space characters are:
unicode     name            formal name     string representation
\u000A      line feed       <LF>            \n
\u000D      carriage return <CR>            \r

this test uses onerror to capture line numbers.  because
we use on error, we can only have one test case per file.

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_lexical__001:function(){

var SECTION = "lexical-001";
var VERSION = "JS1_4";
var TITLE   = "Line Terminators";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
result = eval("\r\n\expect");
} catch ( e ) {
exception = e.toString();
}

this.TestCase(
SECTION,
"OBJECT = new Object; result = new OBJECT()" +
" (threw " + exception +")",
expect,
result );

// test();

},

/**
File Name:          lexical-002.js
Corresponds To:     ecma/LexicalConventions/7.2-3-n.js
ECMA Section:       7.2 Line Terminators
Description:        - readability
- separate tokens
- may occur between any two tokens
- cannot occur within any token, not even a string
- affect the process of automatic semicolon insertion.

white space characters are:
unicode     name            formal name     string representation
\u000A      line feed       <LF>            \n
\u000D      carriage return <CR>            \r

this test uses onerror to capture line numbers.  because
we use on error, we can only have one test case per file.

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_lexical__002:function(){

var SECTION = "lexical-002";
var VERSION = "JS1_4";
var TITLE   = "Line Terminators";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
result = eval("\r\n\expect");
} catch ( e ) {
exception = e.toString();
}

this.TestCase(
SECTION,
"result=eval(\"\r\nexpect\")" +
" (threw " + exception +")",
expect,
result );

// test();

},

/**
File Name:          lexical-003.js
Corresponds To:     7.3-13-n.js
ECMA Section:       7.3 Comments
Description:

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_lexical__003:function(){

var SECTION = "lexical-003.js";
var VERSION = "JS1_4";
var TITLE   = "Comments";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("/*\n/* nested comment */\n*/\n");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"/*/*nested comment*/ */" +
" (threw " + exception +")",
expect,
result );

// test();


},

/**
File Name:          lexical-004.js
Corresponds To:     ecma/LexicalExpressions/7.4.1-1-n.js
ECMA Section:       7.4.1

Description:

Reserved words cannot be used as identifiers.

ReservedWord ::
Keyword
FutureReservedWord
NullLiteral
BooleanLiteral

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_lexical__004:function(){

var SECTION = "lexical-004";
var VERSION = "JS1_4";
var TITLE   = "Keywords";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("var null = true;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"var null = true" +
" (threw " + exception +")",
expect,
result );

// test();


},

/**
File Name:          lexical-005.js
Corresponds To:     7.4.1-2.js
ECMA Section:       7.4.1

Description:

Reserved words cannot be used as identifiers.

ReservedWord ::
Keyword
FutureReservedWord
NullLiteral
BooleanLiteral

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_lexical__005:function(){

var SECTION = "lexical-005";
var VERSION = "JS1_4";
var TITLE   = "Keywords";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("true = false;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"true = false" +
" (threw " + exception +")",
expect,
result );

// test();


},

/**
File Name:          lexical-006.js
Corresponds To:     7.4.2-1.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_lexical__006:function(){

var SECTION = "lexical-006";
var VERSION = "JS1_4";
var TITLE   = "Keywords";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("break = new Object();");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"break = new Object()" +
" (threw " + exception +")",
expect,
result );

// test();


},

/**
File Name:          lexical-005.js
Corresponds To:     7.4.1-3-n.js
ECMA Section:       7.4.1

Description:

Reserved words cannot be used as identifiers.

ReservedWord ::
Keyword
FutureReservedWord
NullLiteral
BooleanLiteral

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_lexical__007:function(){

var SECTION = "lexical-005";
var VERSION = "JS1_4";
var TITLE   = "Keywords";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("false = true;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"false = true" +
" (threw " + exception +")",
expect,
result );

// test();


},

/**
File Name:          lexical-008.js
Corresponds To:     7.4.3-1-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_lexical__008:function(){

var SECTION = "lexical-008.js";
var VERSION = "JS1_4";
var TITLE   = "Future Reserved Words";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("case = true;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"case = true" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-009
Corresponds To:     7.4.3-2-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_lexical__009:function(){

var SECTION = "lexical-009";
var VERSION = "ECMA_1";
var TITLE   = "Future Reserved Words";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("debugger = true;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"debugger = true" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-010.js
Corresponds To:     7.4.3-3-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_lexical__010:function(){

var SECTION = "lexical-010";
var VERSION = "ECMA_1";
var TITLE   = "Future Reserved Words";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("export = true;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"export = true" +
" (threw " + exception +")",
expect,
result );

// test();

},

/**
File Name:          lexical-011.js
Corresponds To:     7.4.3-4-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_lexical__011:function(){

var SECTION = "lexical-011";
var VERSION = "JS1_4";
var TITLE   = "Future Reserved Words";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

print("This test requires option javascript.options.strict enabled");
var options = null;
if (!options().match(/strict/))
{
options('strict');
}
if (!options().match(/werror/))
{
options('werror');
}

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("super = true;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"super = true" +
" (threw " + exception +")",
expect,
result );

// test();

},

/**
File Name:          lexical-012.js
Corresponds To:     7.4.3-5-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_lexical__012:function(){

var SECTION = "lexical-012";
var VERSION = "JS1_4";
var TITLE   = "Future Reserved Words";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("catch = true;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"catch = true" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-013.js
Corresponds To:     7.4.3-6-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_lexical__013:function(){

var SECTION = "lexical-013";
var VERSION = "JS1_4";
var TITLE   = "Future Reserved Words";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("default = true;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"default = true" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-014.js
Corresponds To:     7.4.3-7-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_lexical__014:function(){

var SECTION = "lexical-014.js";
var VERSION = "JS1_4";
var TITLE   = "Future Reserved Words";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

//print("This test requires option javascript.options.strict enabled");
var options = null;
if (!options().match(/strict/))
{
options('strict');
}
if (!options().match(/werror/))
{
options('werror');
}

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("extends = true;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"extends = true" +
" (threw " + exception +")",
expect,
result );

// test();

},

/**
File Name:          lexical-015.js
Corresponds To:     7.4.3-8-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_lexical__015:function(){

var SECTION = "lexical-015";
var VERSION = "JS1_4";
var TITLE   = "Future Reserved Words";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("switch = true;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"switch = true" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-016
Corresponds To:     7.4.3-9-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_lexical__016:function(){

var SECTION = "lexical-016";
var VERSION = "JS1_4";
var TITLE   = "Future Reserved Words";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

//print("This test requires option javascript.options.strict enabled");
var options = null;
if (!options().match(/strict/))
{
options('strict');
}
if (!options().match(/werror/))
{
options('werror');
}

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("class = true;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"class = true" +
" (threw " + exception +")",
expect,
result );

// test();

},

/**
File Name:          lexical-017.js
Corresponds To:     7.4.3-10-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_lexical__017:function(){

var SECTION = "lexical-017";
var VERSION = "JS1_4";
var TITLE   = "Future Reserved Words";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("do = true;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"do = true" +
" (threw " + exception +")",
expect,
result );

// test();




},

/**
File Name:          lexical-018
Corresponds To:     7.4.3-11-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_lexical__018:function(){

var SECTION = "lexical-018";
var VERSION = "JS1_4";
var TITLE   = "Future Reserved Words";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("finally = true;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"finally = true" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-019.js
Corresponds To:     7.4.3-12-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_lexical__019:function(){

var SECTION = "lexical-019";
var VERSION = "JS1_4";
var TITLE   = "Future Reserved Words";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("throw = true;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"throw = true" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-020.js
Corresponds To      7.4.3-13-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_lexical__020:function(){

var SECTION = "lexical-020";
var VERSION = "JS1_4";
var TITLE   = "Future Reserved Words";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("const = true;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"const = true" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-021.js
Corresponds To:     7.4.3-14-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_lexical__021:function(){

var SECTION = "lexical-021.js";
var VERSION = "ECMA_1";
var TITLE   = "Future Reserved Words";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

//print("This test requires option javascript.options.strict enabled");
var options = null;
if (!options().match(/strict/))
{
options('strict');
}
if (!options().match(/werror/))
{
options('werror');
}

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("enum = true;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"enum = true" +
" (threw " + exception +")",
expect,
result );

// test();

},

/**
File Name:          lexical-022
Corresponds To      7.4.3-15-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_lexical__022:function(){

var SECTION = "lexical-022.js";
var VERSION = "ECMA_1";
var TITLE   = "Future Reserved Words";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("import = true;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"import = true" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-023.js
Corresponds To:     7.4.3-16-n.js
ECMA Section:       7.4.3
Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_lexical__023:function(){

var SECTION = "lexical-023.js";
var VERSION = "ECMA_1";
var TITLE   = "Future Reserved Words";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("try = true;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"try = true" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-024
Corresponds To:     7.4.2-1-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_lexical__024:function(){

var SECTION = "lexical-024";
var VERSION = "JS1_4";
var TITLE   = "Keywords";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("var break;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"var break" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-025.js
Corresponds To      7.4.2-2-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_lexical__025:function(){

var SECTION = "lexical-025";
var VERSION = "JS1_4";
var TITLE   = "Keywords";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("var for;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"var for" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-026.js
Corresponds To:     7.4.2-3-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_lexical__026:function(){

var SECTION = "lexical-026";
var VERSION = "JS1_4";
var TITLE   = "Keywords";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("var new;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"var new" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-027.js
Corresponds To:     7.4.2-4-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

var

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_lexical__027:function(){

var SECTION = "lexical-027";
var VERSION = "JS1_4";
var TITLE   = "Keywords";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("var var;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"var var" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-028.js
Corresponds To:     7.4.2-5-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_lexical__028:function(){

var SECTION = "lexical-028";
var VERSION = "JS1_4";
var TITLE   = "Keywords";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("var continue=true;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"var continue=true" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-029.js
Corresponds To:     7.4.2-6.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_lexical__029:function(){

var SECTION = "lexical-029";
var VERSION = "JS1_4";
var TITLE   = "Keywords";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("var function = true;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"var function = true" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-030.js
Corresponds To:     7.4.2-7-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_lexical__030:function(){

var SECTION = "lexical-030";
var VERSION = "JS1_4";
var TITLE   = "Keywords";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("var return = true;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"var return = true" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-031.js
Corresponds To:     7.4.2-8-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_lexical__031:function(){

var SECTION = "lexical-031";
var VERSION = "JS1_4";
var TITLE   = "Keywords";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("var return;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"var return" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-032.js
Corresponds To:     7.4.2-9-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_lexical__032:function(){

var SECTION = "lexical-032";
var VERSION = "JS1_4";
var TITLE   = "Keywords";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("delete = true;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"delete = true" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-033.js
Corresponds To:     7.4.2-10.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_lexical__033:function(){

var SECTION = "lexical-033";
var VERSION = "JS1_4";
var TITLE   = "Keywords";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("if = true;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"if = true" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          7.4.2-11-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_lexical__034:function(){

var SECTION = "lexical-034";
var VERSION = "JS1_4";
var TITLE   = "Keywords";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("this = true");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"this = true" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-035.js
Correpsonds To:     7.4.2-12-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_lexical__035:function(){

var SECTION = "lexical-035";
var VERSION = "JS1_4";
var TITLE   = "Keywords";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("var while");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"var while" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-036.js
Corresponds To:     7.4.2-13-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_lexical__036:function(){

var SECTION = "lexical-036";
var VERSION = "JS1_4";
var TITLE   = "Keywords";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("else = true;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"else = true" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-037.js
Corresponds To:     7.4.2-14-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_lexical__037:function(){

var SECTION = "lexical-028";
var VERSION = "JS1_4";
var TITLE   = "Keywords";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("var in;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"var in" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-038.js
Corresponds To:     7.4.2-15-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_lexical__038:function(){

var SECTION = "lexical-038";
var VERSION = "JS1_4";
var TITLE   = "Keywords";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("typeof = true;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"typeof = true" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-039
Corresponds To:     7.5-2-n.js
ECMA Section:       7.5 Identifiers
Description:        Identifiers are of unlimited length
- can contain letters, a decimal digit, _, or $
- the first character cannot be a decimal digit
- identifiers are case sensitive

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_lexical__039:function(){

var SECTION = "lexical-039";
var VERSION = "JS1_4";
var TITLE   = "Identifiers";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("var 0abc;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"var 0abc" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-040.js
Corresponds To:     7.5-2.js
ECMA Section:       7.5 Identifiers
Description:        Identifiers are of unlimited length
- can contain letters, a decimal digit, _, or $
- the first character cannot be a decimal digit
- identifiers are case sensitive

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_lexical__040:function(){

var SECTION = "lexical-040";
var VERSION = "JS1_4";
var TITLE   = "Identifiers";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("var 1abc;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"var 1abc" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-041.js
Corresponds To:     7.5-8-n.js
ECMA Section:       7.5 Identifiers
Description:        Identifiers are of unlimited length
- can contain letters, a decimal digit, _, or $
- the first character cannot be a decimal digit
- identifiers are case sensitive

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_lexical__041:function(){

var SECTION = "lexical-041";
var VERSION = "ECMA_1";
var TITLE   = "Identifiers";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);
// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("var @abc;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"var @abc" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-042.js
Corresponds To:     7.5-9-n.js
ECMA Section:       7.5 Identifiers
Description:        Identifiers are of unlimited length
- can contain letters, a decimal digit, _, or $
- the first character cannot be a decimal digit
- identifiers are case sensitive

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_lexical__042:function(){

var SECTION = "lexical-042";
var VERSION = "JS1_4";
var TITLE   = "Identifiers";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("var 123;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"var 123" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-047.js
Corresponds To:     7.8.1-7-n.js
ECMA Section:       7.8.1
Description:
Author:             christine@netscape.com
Date:               15 september 1997
*/
test_lexical__047:function(){


var SECTION = "lexical-047";
var VERSION = "JS1_4";
var TITLE   = "for loops";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
var counter = 0;
eval("for ( counter = 0\n"
+ "counter <= 1\n"
+ "counter++ )\n"
+ "{\n"
+ "result += \":  got to inner loop\";\n"
+ "}\n");

} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"line breaks within a for expression" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-048.js
Corresponds To:     7.8.1-1.js
ECMA Section:       7.8.1 Rules of Automatic Semicolon Insertion
Description:
Author:             christine@netscape.com
Date:               15 september 1997
*/
test_lexical__048:function(){


var SECTION = "lexical-048";
var VERSION = "JS1_4";
var TITLE   = "The Rules of Automatic Semicolon Insertion";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
var counter = 0;
eval( "for ( counter = 0;\ncounter <= 1\ncounter++ ) {\nresult += \": got inside for loop\")");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"line breaks within a for expression" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-049
Corresponds To:     7.8.1-1.js
ECMA Section:       7.8.1 Rules of Automatic Semicolon Insertioin
Description:
Author:             christine@netscape.com
Date:               15 september 1997
*/
test_lexical__049:function(){

var SECTION = "lexical-049";
var VERSION = "JS1_4";
var TITLE   = "The Rules of Automatic Semicolon Insertion";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
var counter = 0;
eval("for ( counter = 0\n"
+ "counter <= 1;\n"
+ "counter++ )\n"
+ "{\n"
+ "result += \": got inside for loop\";\n"
+ "}\n");

} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"line breaks within a for expression" +
" (threw " + exception +")",
expect,
result );

// test();



},

/**
File Name:          lexical-050.js
Corresponds to:     7.8.2-1-n.js
ECMA Section:       7.8.2 Examples of Automatic Semicolon Insertion
Description:        compare some specific examples of the automatic
insertion rules in the EMCA specification.
Author:             christine@netscape.com
Date:               15 september 1997
*/
test_lexical__050:function(){


var SECTION = "lexical-050";
var VERSION = "JS1_4";
var TITLE   = "Examples of Automatic Semicolon Insertion";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("{ 1 2 } 3");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"{ 1 2 } 3" +
" (threw " + exception +")",
expect,
result );

// test();




},

/**
File Name:          lexical-051.js
Corresponds to:     7.8.2-3-n.js
ECMA Section:       7.8.2 Examples of Automatic Semicolon Insertion
Description:        compare some specific examples of the automatic
insertion rules in the EMCA specification.
Author:             christine@netscape.com
Date:               15 september 1997
*/
test_lexical__051:function(){


var SECTION = "lexical-051";
var VERSION = "JS1_4";
var TITLE   = "Examples of Automatic Semicolon Insertion";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("for (a; b\n) result += \": got to inner loop\";")
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"for (a; b\n)" +
" (threw " + exception +")",
expect,
result );

// test();




},

/**
File Name:          lexical-052.js
Corresponds to:     7.8.2-4-n.js
ECMA Section:       7.8.2 Examples of Automatic Semicolon Insertion
Description:        compare some specific examples of the automatic
insertion rules in the EMCA specification.
Author:             christine@netscape.com
Date:               15 september 1997
*/
test_lexical__052:function(){


var SECTION = "lexical-052";
var VERSION = "JS1_4";
var TITLE   = "Examples of Automatic Semicolon Insertion";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";
var MyFunction_lexical__052 = null;
try {
MyFunction_lexical__052();
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"calling return indirectly" +
" (threw " + exception +")",
expect,
result );

// test();


},

/**
File Name:          lexical-053.js
Corresponds to:     7.8.2-7-n.js
ECMA Section:       7.8.2 Examples of Automatic Semicolon Insertion
Description:        compare some specific examples of the automatic
insertion rules in the EMCA specification.
Author:             christine@netscape.com
Date:               15 september 1997
*/
test_lexical__053:function(){


var SECTION = "lexical-053";
var VERSION = "JS1_4";
var TITLE   = "Examples of Automatic Semicolon Insertion";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
a = true
b = false

eval('if (a > b)\nelse result += ": got to else statement"');
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"calling return indirectly" +
" (threw " + exception +")",
expect,
result );

// test();

},

/**
File Name:          lexical-054.js
Corresponds to:     7.8.2-7-n.js
ECMA Section:       7.8.2 Examples of Automatic Semicolon Insertion
Description:        compare some specific examples of the automatic
insertion rules in the EMCA specification.
Author:             christine@netscape.com
Date:               15 september 1997
*/
test_lexical__054:function(){


var SECTION = "lexical-054";
var VERSION = "JS1_4";
var TITLE   = "Examples of Automatic Semicolon Insertion";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
a=0;
b=1;
c=2;
d=3;
eval("if (a > b)\nelse c = d");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"if (a > b)\nelse c = d" +
" (threw " + exception +")",
expect,
result );

// test();

},

/**
File Name:          number-001
Corresponds To:     15.7.4.2-2-n.js
ECMA Section:       15.7.4.2.2 Number.prototype.toString()
Description:
If the radix is the number 10 or not supplied, then this number value is
given as an argument to the ToString operator; the resulting string value
is returned.

If the radix is supplied and is an integer from 2 to 36, but not 10, the
result is a string, the choice of which is implementation dependent.

The toString function is not generic; it generates a runtime error if its
this value is not a Number object. Therefore it cannot be transferred to
other kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_number__001:function(){

var SECTION = "number-001";
var VERSION = "JS1_4";
var TITLE   = "Exceptions for Number.toString()";

// startTest();
// writeHeaderToLog( SECTION + " Number.prototype.toString()");

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
object= new Object();
object.toString = Number.prototype.toString;
result = object.toString();
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"object = new Object(); object.toString = Number.prototype.toString; object.toString()" +
" (threw " + exception +")",
expect,
result );

// test();

},

/**
File Name:          number-002.js
Corresponds To:     ecma/Number/15.7.4.3-2-n.js
ECMA Section:       15.7.4.3.1 Number.prototype.valueOf()
Description:
Returns this number value.

The valueOf function is not generic; it generates a runtime error if its
this value is not a Number object. Therefore it cannot be transferred to
other kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_number__002:function(){

var SECTION = "number-002";
var VERSION = "JS1_4";
var TITLE   = "Exceptions for Number.valueOf()";

// startTest();
// writeHeaderToLog( SECTION + " Number.prototype.valueOf()");

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
object= new Object();
object.toString = Number.prototype.valueOf;
result = object.toString();
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"object = new Object(); object.valueOf = Number.prototype.valueOf; object.valueOf()" +
" (threw " + exception +")",
expect,
result );

// test();

},

/**
File Name:          number-003.js
Corresponds To:     15.7.4.3-3.js
ECMA Section:       15.7.4.3.1 Number.prototype.valueOf()
Description:
Returns this number value.

The valueOf function is not generic; it generates a runtime error if its
this value is not a Number object. Therefore it cannot be transferred to
other kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_number__003:function(){

var SECTION = "number-003";
var VERSION = "JS1_4";
var TITLE   = "Exceptions for Number.valueOf()";

// startTest();
// writeHeaderToLog( SECTION + " Number.prototype.valueOf()");

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
VALUE_OF = Number.prototype.valueOf;
OBJECT = new String("Infinity");
OBJECT.valueOf = VALUE_OF;
result = OBJECT.valueOf();
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"Assigning Number.prototype.valueOf as the valueOf of a String object " +
" (threw " + exception +")",
expect,
result );

// test();


},

/**
File Name:          statement-001.js
Corresponds To:     12.6.2-9-n.js
ECMA Section:       12.6.2 The for Statement

1. first expression is not present.
2. second expression is not present
3. third expression is not present


Author:             christine@netscape.com
Date:               15 september 1997
*/
test_statement__001:function(){


var SECTION = "statement-001.js";
//     var SECTION = "12.6.2-9-n";
var VERSION = "ECMA_1";
var TITLE   = "The for statement";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("for (i) {\n}");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"for(i) {}" +
" (threw " + exception +")",
expect,
result );

// test();

},

/**
File Name:          statement-002.js
Corresponds To:     12.6.3-1.js
ECMA Section:       12.6.3 The for...in Statement
Description:
The production IterationStatement : for ( LeftHandSideExpression in Expression )
Statement is evaluated as follows:

1.  Evaluate the Expression.
2.  Call GetValue(Result(1)).
3.  Call ToObject(Result(2)).
4.  Let C be "normal completion".
5.  Get the name of the next property of Result(3) that doesn't have the
DontEnum attribute. If there is no such property, go to step 14.
6.  Evaluate the LeftHandSideExpression ( it may be evaluated repeatedly).
7.  Call PutValue(Result(6), Result(5)).  PutValue( V, W ):
1.  If Type(V) is not Reference, generate a runtime error.
2.  Call GetBase(V).
3.  If Result(2) is null, go to step 6.
4.  Call the [[Put]] method of Result(2), passing GetPropertyName(V)
for the property name and W for the value.
5.  Return.
6.  Call the [[Put]] method for the global object, passing
GetPropertyName(V) for the property name and W for the value.
7.  Return.
8.  Evaluate Statement.
9.  If Result(8) is a value completion, change C to be "normal completion
after value V" where V is the value carried by Result(8).
10. If Result(8) is a break completion, go to step 14.
11. If Result(8) is a continue completion, go to step 5.
12. If Result(8) is a return completion, return Result(8).
13. Go to step 5.
14. Return C.

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_statement__002:function(){

var SECTION = "statement-002";
var VERSION = "JS1_4";
var TITLE   = "The for..in statement";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval(" for ( var i, p in this) { result += this[p]; }");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"more than one member expression" +
" (threw " + exception +")",
expect,
result );

// test();

},

/**
File Name:          statement-003
Corresponds To:     12.6.3-7-n.js
ECMA Section:       12.6.3 The for...in Statement
Description:
The production IterationStatement : for ( LeftHandSideExpression in Expression )
Statement is evaluated as follows:

1.  Evaluate the Expression.
2.  Call GetValue(Result(1)).
3.  Call ToObject(Result(2)).
4.  Let C be "normal completion".
5.  Get the name of the next property of Result(3) that doesn't have the
DontEnum attribute. If there is no such property, go to step 14.
6.  Evaluate the LeftHandSideExpression ( it may be evaluated repeatedly).
7.  Call PutValue(Result(6), Result(5)).  PutValue( V, W ):
1.  If Type(V) is not Reference, generate a runtime error.
2.  Call GetBase(V).
3.  If Result(2) is null, go to step 6.
4.  Call the [[Put]] method of Result(2), passing GetPropertyName(V)
for the property name and W for the value.
5.  Return.
6.  Call the [[Put]] method for the global object, passing
GetPropertyName(V) for the property name and W for the value.
7.  Return.
8.  Evaluate Statement.
9.  If Result(8) is a value completion, change C to be "normal completion
after value V" where V is the value carried by Result(8).
10. If Result(8) is a break completion, go to step 14.
11. If Result(8) is a continue completion, go to step 5.
12. If Result(8) is a return completion, return Result(8).
13. Go to step 5.
14. Return C.

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_statement__003:function(){

var SECTION = "statement-003";
var VERSION = "JS1_4";
var TITLE   = "The for..in statement";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
var o = new Object();
var result = 0;

eval("for ( this in o) {\n"
+ "result += this[p];\n"
+ "}\n");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"bad left-hand side expression" +
" (threw " + exception +")",
expect,
result );

// test();

},

/**
File Name:          statement-004.js
Corresponds To:     12.6.3-1.js
ECMA Section:       12.6.3 The for...in Statement
Description:
Author:             christine@netscape.com
Date:               11 september 1997
*/
test_statement__004:function(){

var SECTION = "statement-004";
var VERSION = "JS1_4";
var TITLE   = "The for..in statement";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
var o = new Object();

eval("for ( \"a\" in o) {\n"
+ "result += this[p];\n"
+ "}");

} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"bad left-hand side expression" +
" (threw " + exception +")",
expect,
result );

// test();

},

/**
File Name:          statement-005.js
Corresponds To:     12.6.3-8-n.js
ECMA Section:       12.6.3 The for...in Statement
Description:
Author:             christine@netscape.com
Date:               11 september 1997
*/
test_statement__005:function(){

var SECTION = "statement-005";
var VERSION = "JS1_4";
var TITLE   = "The for..in statement";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
var o = new Object();
result = 0;

eval("for (1 in o) {\n"
+ "result += this[p];"
+ "}\n");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"bad left-hand side expression" +
" (threw " + exception +")",
expect,
result );

// test();

},

/**
File Name:          statement-006.js
Corresponds To:     12.6.3-9-n.js
ECMA Section:       12.6.3 The for...in Statement
Description:

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_statement__006:function(){

var SECTION = "statement-006";
var VERSION = "JS1_4";
var TITLE   = "The for..in statement";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
var o = new Object();
var result = 0;
var foo = null;
for ( var o in foo) {
result += this[o];
}
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"object is not defined" +
" (threw " + exception +")",
expect,
result );

// test();


},

/**
File Name:          statement-007.js
Corresponds To:     12.7-1-n.js
ECMA Section:       12.7 The continue statement
Description:

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_statement__007:function(){

var SECTION = "statement-007";
var VERSION = "JS1_4";
var TITLE   = "The continue statement";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("continue;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"continue outside of an iteration statement" +
" (threw " + exception +")",
expect,
result );

// test();


},

/**
File Name:          statement-008.js
Corresponds To:     12.8-1-n.js
ECMA Section:       12.8 The break statement
Description:

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_statement__008:function(){

var SECTION = "statement-008";
var VERSION = "JS1_4";
var TITLE   = "The break in statement";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("break;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"break outside of an iteration statement" +
" (threw " + exception +")",
expect,
result );

// test();


},

/**
File Name:          12.9-1-n.js
ECMA Section:       12.9 The return statement
Description:

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_statement__009:function(){

var SECTION = "12.9-1-n";
var VERSION = "ECMA_1";
var TITLE   = "The return statement";

// startTest();
// writeHeaderToLog( SECTION + " The return statement");

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
eval("return;");
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"return outside of a function" +
" (threw " + exception +")",
expect,
result );

// test();


},

/**
File Name:          string-001.js
Corresponds To:     15.5.4.2-2-n.js
ECMA Section:       15.5.4.2 String.prototype.toString()

Description:        Returns this string value.  Note that, for a String
object, the toString() method happens to return the same
thing as the valueOf() method.

The toString function is not generic; it generates a
runtime error if its this value is not a String object.
Therefore it connot be transferred to the other kinds of
objects for use as a method.

Author:             christine@netscape.com
Date:               1 october 1997
*/
test_string__001:function(){

var SECTION = "string-001";
var VERSION = "JS1_4";
var TITLE   = "String.prototype.toString";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
OBJECT = new Object();
OBJECT.toString = String.prototype.toString();
result = OBJECT.toString();
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"OBJECT = new Object; "+
" OBJECT.toString = String.prototype.toString; OBJECT.toString()" +
" (threw " + exception +")",
expect,
result );

// test();


},

/**
File Name:          string-002.js
Corresponds To:     15.5.4.3-3-n.js
ECMA Section:       15.5.4.3 String.prototype.valueOf()

Description:        Returns this string value.

The valueOf function is not generic; it generates a
runtime error if its this value is not a String object.
Therefore it connot be transferred to the other kinds of
objects for use as a method.

Author:             christine@netscape.com
Date:               1 october 1997
*/
test_string__002:function(){

var SECTION = "string-002";
var VERSION = "JS1_4";
var TITLE   = "String.prototype.valueOf";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";

try {
var OBJECT =new Object();
OBJECT.valueOf = String.prototype.valueOf;
result = OBJECT.valueOf();
} catch ( e ) {
result = expect;
exception = e.toString();
}

this.TestCase(
SECTION,
"OBJECT = new Object; OBJECT.valueOf = String.prototype.valueOf;"+
"result = OBJECT.valueOf();" +
" (threw " + exception +")",
expect,
result );

// test();



}}).endType()


function MyObject( value ) {
this.value = value;
this.valueOf = new Function( "return this.value" );
this.toString = Date.prototype.toString;
return this;
}


function MyDate(value) {
this.value = value;
this.setTime = Date.prototype.setTime;
return this;
}


function MyObject_date_003( value ) {
this.value = value;
this.valueOf = Date.prototype.valueOf;
//  The following line causes an infinte loop
//    this.toString = new Function( "return this+\"\";");
return this;
}


function MyDate_data_004( value ) {
this.value = value;
this.getTime = Date.prototype.getTime;
}


function MyObject_exception_006() {
this.toString = void 0;
this.valueOf = void 0;
}


function MyObject_exception_007() {
this.toString = void 0;
this.valueOf = new Object();
}

function Nested_exception_009() {
try {
try {
} catch (a) {
} finally {
}
} catch (b) {
} finally {
}
}


function Property_expression_002( object, value, string, number ) {
this.object = object;
this.string = String(value);
this.number = Number(value);
this.valueOf = value;
}


function MyFunction_lexical__052() {
var s = "return";
eval(s);
}


function MyObject_statement_003() {
this.value = 2;
this[0] = 4;
return this;
}


function print(str) {
}
