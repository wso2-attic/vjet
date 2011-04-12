vjo.ctype("dsf.jslang.feature.tests.EcmaExecutionContextsTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({


assertTrue : function(o){
},

constructs: function() {
this.base();
},

/**
File Name:          10.1.3-1.js
ECMA Section:       10.1.3
Description:

For each formal parameter, as defined in the FormalParameterList, create
a property of the variable object whose name is the Identifier and whose
attributes are determined by the type of code. The values of the
parameters are supplied by the caller. If the caller supplies fewer
parameter values than there are formal parameters, the extra formal
parameters have value undefined. If two or more formal parameters share
the same name, hence the same property, the corresponding property is
given the value that was supplied for the last parameter with this name.
If the value of this last parameter was not supplied by the caller,
the value of the corresponding property is undefined.


http://scopus.mcom.com/bugsplat/show_bug.cgi?id=104191

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_10_1_3__1: function() {
var SECTION = "10.1.3-1";
var VERSION = "ECMA_1";
var TITLE   = "Variable Instantiation:  Formal Parameters";
var BUGNUMBER="104191";
//startTest();

//writeHeaderToLog( SECTION + " "+ TITLE);

var myfun1 = new Function( "a", "a", "return a" );
var myfun2 = new Function( "a", "b", "a", "return a" );

function myfun3(a, b, a1) {
return a;
}

// myfun1, myfun2, myfun3 tostring


this.TestCase(
SECTION,
String(myfun2) +"; myfun2(2,4,8)",
8,
myfun2(2,4,8) );

this.TestCase(
SECTION,
"myfun2(2,4)",
void 0,
myfun2(2,4));

this.TestCase(
SECTION,
String(myfun3) +"; myfun3(2,4,8)",
8,
myfun3(2,4,8) );

this.TestCase(
SECTION,
"myfun3(2,4)",
void 0,
myfun3(2,4) );

//test();

},

/**
File Name:          10.1.3-1.js
ECMA Section:       10.1.3
Description:

Author:             mozilla@florian.loitsch.com
Date:               27 July 2005
*/
test_10_1_3__2: function() {
var SECTION = "10.1.3-2";
var VERSION = "ECMA_1";
var TITLE   = "Variable Instantiation:  Function Declarations";
var BUGNUMBER="299639";
//startTest();

//writeHeaderToLog( SECTION + " "+ TITLE);

function f(g)
{
function g() {
return "g";
};
return g;
}

this.TestCase(
SECTION,
"typeof f(\"parameter\")",
"function",
typeof f("parameter") );

//test();

},

/**
File Name:          10.1.3.js
ECMA Section:       10.1.3.js Variable Instantiation
Description:
Author:             christine@netscape.com
Date:               11 september 1997
*/
test_10_1_3: function() {
var SECTION = "10.1.3";
var VERSION = "ECMA_1";
var TITLE   = "Variable instantiation";
var BUGNUMBER = "20256";
//startTest();

//writeHeaderToLog( SECTION + " "+ TITLE);


// overriding a variable or function name with a function should succeed

this.TestCase(SECTION,
"function t() { return \"first\" };" +
"function t() { return \"second\" };t() ",
"second",
eval("function t() { return \"first\" };" +
"function t() { return \"second\" };t()"));


this.TestCase(SECTION,
"var t; function t(){}; typeof(t)",
"function",
eval("var t; function t(){}; typeof(t)"));


// formal parameter tests

this.TestCase(SECTION,
"function t1(a,b) { return b; }; t1( 4 );",
void 0,
eval("function t1(a,b) { return b; }; t1( 4 );") );

this.TestCase(SECTION,
"function t1(a,b) { return a; }; t1(4);",
4,
eval("function t1(a,b) { return a; }; t1(4)"));

this.TestCase(SECTION,
"function t1(a,b) { return a; }; t1();",
void 0,
eval("function t1(a,b) { return a; }; t1()"));

this.TestCase(SECTION,
"function t1(a,b) { return a; }; t1(1,2,4);",
1,
eval("function t1(a,b) { return a; }; t1(1,2,4)"));
/*

this.TestCase(SECTION, "function t1(a,a) { return a; }; t1( 4 );",
void 0,
eval("function t1(a,a) { return a; }; t1( 4 )"));

this.TestCase(SECTION,
"function t1(a,a) { return a; }; t1( 1,2 );",
2,
eval("function t1(a,a) { return a; }; t1( 1,2 )"));
*/
// variable declarations

this.TestCase(SECTION,
"function t1(a,b) { return a; }; t1( false, true );",
false,
eval("function t1(a,b) { return a; }; t1( false, true );"));

this.TestCase(SECTION,
"function t1(a,b) { return b; }; t1( false, true );",
true,
eval("function t1(a,b) { return b; }; t1( false, true );"));

this.TestCase(SECTION,
"function t1(a,b) { return a+b; }; t1( 4, 2 );",
6,
eval("function t1(a,b) { return a+b; }; t1( 4, 2 );"));

this.TestCase(SECTION,
"function t1(a,b) { return a+b; }; t1( 4 );",
Number.NaN,
eval("function t1(a,b) { return a+b; }; t1( 4 );"));

// overriding a function name with a variable should fail

this.TestCase(SECTION,
"function t() { return 'function' };" +
"var t = 'variable'; typeof(t)",
"string",
eval("function t() { return 'function' };" +
"var t = 'variable'; typeof(t)"));

// function as a constructor

this.TestCase(SECTION,
"function t1(a,b) { var a = b; return a; } t1(1,3);",
3,
eval("function t1(a, b){ var a = b; return a;}; t1(1,3)"));

this.TestCase(SECTION,
"function t2(a,b) { this.a = b;  } x  = new t2(1,3); x.a",
3,
eval("function t2(a,b) { this.a = b; };" +
"x = new t2(1,3); x.a"));

this.TestCase(SECTION,
"function t2(a,b) { this.a = a;  } x  = new t2(1,3); x.a",
1,
eval("function t2(a,b) { this.a = a; };" +
"x = new t2(1,3); x.a"));

this.TestCase(SECTION,
"function t2(a,b) { this.a = b; this.b = a; } " +
"x = new t2(1,3);x.a;",
3,
eval("function t2(a,b) { this.a = b; this.b = a; };" +
"x = new t2(1,3);x.a;"));

this.TestCase(SECTION,
"function t2(a,b) { this.a = b; this.b = a; }" +
"x = new t2(1,3);x.b;",
1,
eval("function t2(a,b) { this.a = b; this.b = a; };" +
"x = new t2(1,3);x.b;") );

//test();

},

/**
File Name:          10.1.4-1.js
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
test_10_1_4__1: function() {
var SECTION = "10.1.4-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Scope Chain and Identifier Resolution");

var gTestcases = new Array();
var testcase = new Object();

testcase = this.TestCaseAlloc2( "SECTION", "with MyObject, eval should return square of " );
gTestcases[0] = testcase;

//test();

//function test() {
for ( gTc=0; gTc < gTestcases.length; gTc++ ) {
var MYOBJECT = new MyObject();
var INPUT = 2;
gTestcases[gTc].description += "( " + INPUT +" )" ;

with ( MYOBJECT ) {
gTestcases[gTc].actual = eval( INPUT );
gTestcases[gTc].expect = Math.pow(INPUT,2);
}

gTestcases[gTc].passed = this.writeTestCaseResult(
gTestcases[gTc].expect,
gTestcases[gTc].actual,
gTestcases[gTc].description +" = "+
gTestcases[gTc].actual );

gTestcases[gTc].reason += ( gTestcases[gTc].passed ) ? "" : "wrong value ";
}
//stopTest();
//return ( gTestcases );
//}

function MyObject() {
this.eval = new Function( "x", "return(Math.pow(Number(x),2))" );
}

},

/**
File Name:          10.1.4-10.js
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
test_10_1_4__10: function() {
var SECTION = "10.1.4-10";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Scope Chain and Identifier Resolution");

var gTestcases = new Array();
var testcase = new Object();

testcase = this.TestCaseAlloc2( "SECTION", "MYOBJECT.toString()" );
gTestcases[0] = testcase;

//test();

//function test() {
for ( gTc=0; gTc < gTestcases.length; gTc++ ) {
var VALUE = 12345;
var MYOBJECT = new Number( VALUE );

with ( MYOBJECT ) {
gTestcases[gTc].actual = toString();
gTestcases[gTc].expect = String(VALUE);
}

gTestcases[gTc].passed = this.writeTestCaseResult(
gTestcases[gTc].expect,
gTestcases[gTc].actual,
gTestcases[gTc].description +" = "+
gTestcases[gTc].actual );

gTestcases[gTc].reason += ( gTestcases[gTc].passed ) ? "" : "wrong value ";
}
//stopTest();
//return ( gTestcases );
//}

},

/**
File Name:          10.1.4-1.js
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
test_10_1_4__2: function() {
var SECTION = "10.1.4-2";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Scope Chain and Identifier Resolution");

var gTestcases = new Array();
var testcase = new Object();

testcase = this.TestCaseAlloc2( "SECTION", "with MyObject, eval should return square of " );
gTestcases[0] = testcase;

//test();

//function test() {
for ( gTc=0; gTc < gTestcases.length; gTc++ ) {
var MYOBJECT = new MyObject();
var INPUT = 2;
gTestcases[gTc].description += "( "+INPUT +" )" ;

gTestcases[gTc].passed = this.writeTestCaseResult(
gTestcases[gTc].expect,
gTestcases[gTc].actual,
gTestcases[gTc].description +" = "+
gTestcases[gTc].actual );

gTestcases[gTc].reason += ( gTestcases[gTc].passed ) ? "" : "wrong value ";
}
//stopTest();
//return ( gTestcases );
//}

function MyObject() {
this.eval = new Function( "x", "return(Math.pow(Number(x),2))" );
}

},

/**
File Name:          10.1.4-1.js
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
test_10_1_4__3: function() {
var SECTION = "10.1.4-3";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Scope Chain and Identifier Resolution");

var gTestcases = new Array();
var testcase = new Object();

testcase = this.TestCaseAlloc2( "SECTION",
"with MyObject, eval should be [object Global].eval " );
gTestcases[0] = testcase;

//test();

//function test() {
for ( gTc=0; gTc < gTestcases.length; gTc++ ) {

var MYOBJECT = new MyObject();
var INPUT = 2;
gTestcases[gTc].description += ( INPUT +"" );

with ( MYOBJECT ) {
eval( INPUT );
}

gTestcases[gTc].passed = this.writeTestCaseResult(
gTestcases[gTc].expect,
gTestcases[gTc].actual,
gTestcases[gTc].description +" = "+
gTestcases[gTc].actual );

gTestcases[gTc].reason += ( gTestcases[gTc].passed ) ? "" : "wrong value ";
}
//stopTest();
//return ( gTestcases );
//}

function MyObject() {
this.eval = new Function( "x", "return(Math.pow(Number(x),2))" );
}

},

/**
File Name:          10.1.4-1.js
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
test_10_1_4__4: function() {
var SECTION = "10.1.4-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Scope Chain and Identifier Resolution");

var gTestcases = new Array();
var testcase = new Object();

testcase = this.TestCaseAlloc2( "SECTION",
"with MyObject, eval should be [object Global].eval " );
gTestcases[0] = testcase;

//test();

//function test() {
for ( gTc=0; gTc < gTestcases.length; gTc++ ) {

var MYOBJECT = new MyObject();
var INPUT = 2;
gTestcases[gTc].description += ( INPUT +"" );

with ( MYOBJECT ) {
eval( INPUT );
}

gTestcases[gTc].actual = eval( INPUT );
gTestcases[gTc].expect = INPUT;

gTestcases[gTc].passed = this.writeTestCaseResult(
gTestcases[gTc].expect,
gTestcases[gTc].actual,
gTestcases[gTc].description +" = "+
gTestcases[gTc].actual );

gTestcases[gTc].reason += ( gTestcases[gTc].passed ) ? "" : "wrong value ";
}
//stopTest();
//return ( gTestcases );
//}

function MyObject() {
this.eval = new Function( "x", "return(Math.pow(Number(x),2))" );
}

},

/**
File Name:          10.1.4-1.js
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
test_10_1_4__5: function() {
var SECTION = "10.1.4-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Scope Chain and Identifier Resolution");

var gTestcases = new Array();
var testcase = new Object();

testcase = this.TestCaseAlloc2( "SECTION",
"with MyObject, eval should be [object Global].eval " );
gTestcases[0] = testcase;

//test();

//function test() {
for ( gTc=0; gTc < gTestcases.length; gTc++ ) {

var MYOBJECT = new MyObject();
var INPUT = 2;
gTestcases[gTc].description += ( INPUT +"" );

with ( MYOBJECT ) {
eval = null;
}

gTestcases[gTc].actual = eval( INPUT );
gTestcases[gTc].expect = INPUT;

gTestcases[gTc].passed = this.writeTestCaseResult(
gTestcases[gTc].expect,
gTestcases[gTc].actual,
gTestcases[gTc].description +" = "+
gTestcases[gTc].actual );

gTestcases[gTc].reason += ( gTestcases[gTc].passed ) ? "" : "wrong value ";
}
//stopTest();
//return ( gTestcases );
//}

function MyObject() {
this.eval = new Function( "x", "return(Math.pow(Number(x),2))" );
}

},

/**
File Name:          10.1.4-1.js
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
test_10_1_4__6: function() {
var SECTION = "10.1.4-6";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Scope Chain and Identifier Resolution");


var testcase = this.TestCaseAlloc2( "SECTION",
"with MyObject, eval should be [object Global].eval " );

var MYOBJECT = new MyObject();
var INPUT = 2;
testcase.description += ( INPUT +"" );

with ( MYOBJECT ) {
;
}
testcase.actual = eval( INPUT );
testcase.expect = INPUT;

//test();

function MyObject() {
this.eval = new Function( "x", "return(Math.pow(Number(x),2))" );
}

},

/**
File Name:          10.1.4-7.js
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
test_10_1_4__7: function() {
var SECTION = "10.1.4-7";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Scope Chain and Identifier Resolution");

var gTestcases = new Array();
var testcase = new Object();

testcase = this.TestCaseAlloc2( "SECTION",
"with MyObject, eval should be [object Global].eval " );

//test();

//function test() {
for ( gTc=0; gTc < gTestcases.length; gTc++ ) {

var MYOBJECT = new MyObject();
var INPUT = 2;
gTestcases[gTc].description += ( INPUT +"" );

with ( MYOBJECT ) {
delete( eval );
gTestcases[gTc].actual = eval( INPUT );
gTestcases[gTc].expect = INPUT;
}

gTestcases[gTc].passed = this.writeTestCaseResult(
gTestcases[gTc].expect,
gTestcases[gTc].actual,
gTestcases[gTc].description +" = "+
gTestcases[gTc].actual );

gTestcases[gTc].reason += ( gTestcases[gTc].passed ) ? "" : "wrong value ";
}
//stopTest();
//return ( gTestcases );
//}

function MyObject() {
this.eval = new Function( "x", "return(Math.pow(Number(x),2))" );
}

},

/**
File Name:          10.1.4-1.js
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
test_10_1_4__8: function() {
var SECTION = "10.1.4-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Scope Chain and Identifier Resolution");

var gTestcases = new Array();
var testcase = new Object();

testcase = this.TestCaseAlloc2( "SECTION",
"with MyObject, eval should cube INPUT:  " );

//test();

//function test() {
for ( gTc=0; gTc < gTestcases.length; gTc++ ) {

var MYOBJECT = new MyObject();
var INPUT = 2;
gTestcases[gTc].description += ( INPUT +"" );

with ( MYOBJECT ) {
eval = new Function ( "x", "return(Math.pow(Number(x),3))" );

gTestcases[gTc].actual = eval( INPUT );
gTestcases[gTc].expect = Math.pow(INPUT,3);
}

gTestcases[gTc].passed = this.writeTestCaseResult(
gTestcases[gTc].expect,
gTestcases[gTc].actual,
gTestcases[gTc].description +" = "+
gTestcases[gTc].actual );

gTestcases[gTc].reason += ( gTestcases[gTc].passed ) ? "" : "wrong value ";
}
//stopTest();
//return ( gTestcases );
//}

function MyObject() {
this.eval = new Function( "x", "return(Math.pow(Number(x),2))" );
}

},

/**
File Name:          10.1.5-1.js
ECMA Section:       10.1.5 Global Object
Description:
There is a unique global object which is created before control enters
any execution context. Initially the global object has the following
properties:

Built-in objects such as Math, String, Date, parseInt, etc. These have
attributes { DontEnum }.

Additional host defined properties. This may include a property whose
value is the global object itself, for example window in HTML.

As control enters execution contexts, and as ECMAScript code is executed,
additional properties may be added to the global object and the initial
properties may be changed.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_10_1_5__1: function() {
var SECTION = "10.5.1-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Global Object");


var gTestcases = new Array();
var testcase = new Object();

testcase = this.TestCaseAlloc2( "SECTION", "Global Code check" );
gTestcases[0] = testcase;

if ( Object == null ) {
gTestcases[0].reason += " Object == null" ;
}
if ( Function == null ) {
gTestcases[0].reason += " Function == null";
}
if ( String == null ) {
gTestcases[0].reason += " String == null";
}
if ( Array == null ) {
gTestcases[0].reason += " Array == null";
}
if ( Number == null ) {
gTestcases[0].reason += " Function == null";
}
if ( Math == null ) {
gTestcases[0].reason += " Math == null";
}
if ( Boolean == null ) {
gTestcases[0].reason += " Boolean == null";
}
if ( Date  == null ) {
gTestcases[0].reason += " Date == null";
}
/*
if ( NaN == null ) {
gTestcases[0].reason += " NaN == null";
}
if ( Infinity == null ) {
gTestcases[0].reason += " Infinity == null";
}
*/
if ( eval == null ) {
gTestcases[0].reason += " eval == null";
}
if ( parseInt == null ) {
gTestcases[0].reason += " parseInt == null";
}

if ( gTestcases[0].reason != "" ) {
gTestcases[0].actual = "fail";
} else {
gTestcases[0].actual = "pass";
}
gTestcases[0].expect = "pass";

this.assertTrue(this.getTestCaseResult(gTestcases[0].expect,gTestcases[0].actual));

//test();

},

/**
File Name:          10.1.5-2.js
ECMA Section:       10.1.5 Global Object
Description:
There is a unique global object which is created before control enters
any execution context. Initially the global object has the following
properties:

Built-in objects such as Math, String, Date, parseInt, etc. These have
attributes { DontEnum }.

Additional host defined properties. This may include a property whose
value is the global object itself, for example window in HTML.

As control enters execution contexts, and as ECMAScript code is executed,
additional properties may be added to the global object and the initial
properties may be changed.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_10_1_5__2: function() {
var SECTION = "10.5.1-2";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Global Object");

var gTestcases = new Array();
var testcase = new Object();

testcase = this.TestCaseAlloc2( "SECTION", "Eval Code check" );
gTestcases[0] = testcase;

var EVAL_STRING = 'if ( Object == null ) { gTestcases[0].reason += " Object == null" ; }' +
'if ( Function == null ) { gTestcases[0].reason += " Function == null"; }' +
'if ( String == null ) { gTestcases[0].reason += " String == null"; }'   +
'if ( Array == null ) { gTestcases[0].reason += " Array == null"; }'     +
'if ( Number == null ) { gTestcases[0].reason += " Function == null";}'  +
'if ( Math == null ) { gTestcases[0].reason += " Math == null"; }'       +
'if ( Boolean == null ) { gTestcases[0].reason += " Boolean == null"; }' +
'if ( Date  == null ) { gTestcases[0].reason += " Date == null"; }'      +
'if ( eval == null ) { gTestcases[0].reason += " eval == null"; }'       +
'if ( parseInt == null ) { gTestcases[0].reason += " parseInt == null"; }' ;

eval( EVAL_STRING );

/*
if ( NaN == null ) {
gTestcases[0].reason += " NaN == null";
}
if ( Infinity == null ) {
gTestcases[0].reason += " Infinity == null";
}
*/

if ( gTestcases[0].reason != "" ) {
gTestcases[0].actual = "fail";
} else {
gTestcases[0].actual = "pass";
}
gTestcases[0].expect = "pass";

this.assertTrue(this.getTestCaseResult(gTestcases[0].expect,gTestcases[0].actual));

//test();

},

/**
File Name:          10.1.5-3.js
ECMA Section:       10.1.5 Global Object
Description:
There is a unique global object which is created before control enters
any execution context. Initially the global object has the following
properties:

Built-in objects such as Math, String, Date, parseInt, etc. These have
attributes { DontEnum }.

Additional host defined properties. This may include a property whose
value is the global object itself, for example window in HTML.

As control enters execution contexts, and as ECMAScript code is executed,
additional properties may be added to the global object and the initial
properties may be changed.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_10_1_5__3: function() {
var SECTION = "10.5.1-3";
var VERSION = "ECMA_1";
//startTest();
//writeHeaderToLog( SECTION + " Global Object");

var gTestcases = new Array();
var testcase = new Object();

testcase = this.TestCaseAlloc2( "SECTION", "Function Code check" );
gTestcases[0] = testcase;

//test();

//function test() {
if ( Object == null ) {
gTestcases[0].reason += " Object == null" ;
}
if ( Function == null ) {
gTestcases[0].reason += " Function == null";
}
if ( String == null ) {
gTestcases[0].reason += " String == null";
}
if ( Array == null ) {
gTestcases[0].reason += " Array == null";
}
if ( Number == null ) {
gTestcases[0].reason += " Function == null";
}
if ( Math == null ) {
gTestcases[0].reason += " Math == null";
}
if ( Boolean == null ) {
gTestcases[0].reason += " Boolean == null";
}
if ( Date  == null ) {
gTestcases[0].reason += " Date == null";
}
/*
if ( NaN == null ) {
gTestcases[0].reason += " NaN == null";
}
if ( Infinity == null ) {
gTestcases[0].reason += " Infinity == null";
}
*/
if ( eval == null ) {
gTestcases[0].reason += " eval == null";
}
if ( parseInt == null ) {
gTestcases[0].reason += " parseInt == null";
}

if ( gTestcases[0].reason != "" ) {
gTestcases[0].actual = "fail";
} else {
gTestcases[0].actual = "pass";
}
gTestcases[0].expect = "pass";

for ( gTc=0; gTc < gTestcases.length; gTc++ ) {

gTestcases[gTc].passed = this.writeTestCaseResult(
gTestcases[gTc].expect,
gTestcases[gTc].actual,
gTestcases[gTc].description +" = "+
gTestcases[gTc].actual );

gTestcases[gTc].reason += ( gTestcases[gTc].passed ) ? "" : "wrong value ";
}
//stopTest();
//return ( gTestcases );

},

/**
File Name:          10.1.5-4.js
ECMA Section:       10.1.5 Global Object
Description:
There is a unique global object which is created before control enters
any execution context. Initially the global object has the following
properties:

Built-in objects such as Math, String, Date, parseInt, etc. These have
attributes { DontEnum }.

Additional host defined properties. This may include a property whose
value is the global object itself, for example window in HTML.

As control enters execution contexts, and as ECMAScript code is executed,
additional properties may be added to the global object and the initial
properties may be changed.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_10_1_5__4: function() {
var SECTION = "10.5.1-4";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Global Object");

var gTestcases = new Array();
var testcase = new Object();

testcase = this.TestCaseAlloc2( "SECTION", "Anonymous Code check" );
gTestcases[0] = testcase;

var EVAL_STRING = 'if ( Object == null ) { gTestcases[0].reason += " Object == null" ; }' +
'if ( Function == null ) { gTestcases[0].reason += " Function == null"; }' +
'if ( String == null ) { gTestcases[0].reason += " String == null"; }'   +
'if ( Array == null ) { gTestcases[0].reason += " Array == null"; }'     +
'if ( Number == null ) { gTestcases[0].reason += " Function == null";}'  +
'if ( Math == null ) { gTestcases[0].reason += " Math == null"; }'       +
'if ( Boolean == null ) { gTestcases[0].reason += " Boolean == null"; }' +
'if ( Date  == null ) { gTestcases[0].reason += " Date == null"; }'      +
'if ( eval == null ) { gTestcases[0].reason += " eval == null"; }'       +
'if ( parseInt == null ) { gTestcases[0].reason += " parseInt == null"; }' ;

var NEW_FUNCTION = new Function( EVAL_STRING );

if ( gTestcases[0].reason != "" ) {
gTestcases[0].actual = "fail";
} else {
gTestcases[0].actual = "pass";
}
gTestcases[0].expect = "pass";

this.assertTrue(this.getTestCaseResult(gTestcases[0].expect,gTestcases[0].actual));

//test();

},

/**
File Name:          10.1.8-2
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
test_10_1_8__2: function() {
var SECTION = "10.1.8-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Arguments Object";

//writeHeaderToLog( SECTION + " "+ TITLE);

//  Tests for anonymous functions

var GetCallee       = new Function( "var c = arguments.callee; return c" );
var GetArguments    = new Function( "var a = arguments; return a" );
var GetLength       = new Function( "var l = arguments.length; return l" );

var ARG_STRING = "value of the argument property";

this.TestCase( SECTION,
"GetCallee()",
GetCallee,
GetCallee() );

var LIMIT = 100;
var i = 0;
for (  i = 0, args = "" ; i < LIMIT; i++ ) {
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

//test();

},

/**
File Name:          10.1.8-3
ECMA Section:       Arguments Object
Description:

The [[Prototype]] of the arguments object is to the original Object
prototype object, the one that is the initial value of Object.prototype
(section 15.2.3.1).

...

Test that "typeof arguments" is thus "object".

*/
test_10_1_8__3: function() {
var SECTION = "10.1.8-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Arguments Object";
//writeHeaderToLog( SECTION + " "+ TITLE);

var expected = "object";
var actual = (function () { return typeof arguments; })();

//      reportCompare(expected, actual, "typeof arguments == object");

this.assertTrue(expected == actual);

},

/**
File Name:          10.2.1.js
ECMA Section:       10.2.1 Global Code
Description:

The scope chain is created and initialized to contain the global object and
no others.

Variable instantiation is performed using the global object as the variable
object and using empty property attributes.

The this value is the global object.
Author:             christine@netscape.com
Date:               12 november 1997
*/
test_10_2_1: function() {
var SECTION = "10.2.1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Global Code";

//writeHeaderToLog( SECTION + " "+ TITLE);

var THIS = this;

var GLOBAL = this + '';
this.TestCase( SECTION,
"this +''",
GLOBAL,
THIS + "" );

var GLOBAL_PROPERTIES = new Array();
var i = 0;
var p;
for ( p in this ) {
GLOBAL_PROPERTIES[i++] = p;
}

for ( i = 0; i < GLOBAL_PROPERTIES.length; i++ ) {
this.TestCase( SECTION,
GLOBAL_PROPERTIES[i] +" == void 0",
false,
eval("GLOBAL_PROPERTIES["+i+"] == void 0"));
}

//test();

},

/**
File Name:          10.2.2-1.js
ECMA Section:       10.2.2 Eval Code
Description:

When control enters an execution context for eval code, the previous
active execution context, referred to as the calling context, is used to
determine the scope chain, the variable object, and the this value. If
there is no calling context, then initializing the scope chain, variable
instantiation, and determination of the this value are performed just as
for global code.

The scope chain is initialized to contain the same objects, in the same
order, as the calling context's scope chain.  This includes objects added
to the calling context's scope chain by WithStatement.

Variable instantiation is performed using the calling context's variable
object and using empty property attributes.

The this value is the same as the this value of the calling context.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_10_2_2__1: function() {
var SECTION = "10.2.2-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Eval Code";

//writeHeaderToLog( SECTION + " "+ TITLE);

var THIS = eval("this");

var GLOBAL = this + '';
this.TestCase( SECTION,
"this +''",
GLOBAL,
THIS + "" );

var GLOBAL_PROPERTIES = new Array();
var i = 0;
var p;
for ( p in THIS ) {
GLOBAL_PROPERTIES[i++] = p;
}

for ( i = 0; i < GLOBAL_PROPERTIES.length; i++ ) {
this.TestCase( SECTION,
GLOBAL_PROPERTIES[i] +" == THIS["+GLOBAL_PROPERTIES[i]+"]",
true,
eval(GLOBAL_PROPERTIES[i]) == eval( "THIS[GLOBAL_PROPERTIES[i]]") );
}

//  this in eval statements is the same as this value of the calling context

var RESULT = THIS == this;

this.TestCase( SECTION,
"eval( 'this == THIS' )",
true,
RESULT );

RESULT = THIS +'';

this.TestCase( SECTION,
"eval( 'this + \"\"' )",
GLOBAL,
RESULT );


this.TestCase( SECTION,
"eval( 'this == THIS' )",
true,
eval( "this == THIS" ) );

this.TestCase( SECTION,
"eval( 'this + \"\"' )",
GLOBAL,
eval( "this +''") );


//test();

},

/**
File Name:          10.2.2-2.js
ECMA Section:       10.2.2 Eval Code
Description:

When control enters an execution context for eval code, the previous
active execution context, referred to as the calling context, is used to
determine the scope chain, the variable object, and the this value. If
there is no calling context, then initializing the scope chain, variable
instantiation, and determination of the this value are performed just as
for global code.

The scope chain is initialized to contain the same objects, in the same
order, as the calling context's scope chain.  This includes objects added
to the calling context's scope chain by WithStatement.

Variable instantiation is performed using the calling context's variable
object and using empty property attributes.

The this value is the same as the this value of the calling context.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_10_2_2__2: function() {
var SECTION = "10.2.2-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Eval Code";

//writeHeaderToLog( SECTION + " "+ TITLE);

//  Test Objects

var OBJECT = new MyObject( "hello" );
var GLOBAL_PROPERTIES = new Array();
var i = 0;
var p;
for ( p in this ) {
GLOBAL_PROPERTIES[i++] = p;
}

with ( OBJECT ) {
var THIS = this;
this.TestCase( SECTION,
"eval( 'this == THIS' )",
true,
eval("this == THIS") );
this.TestCase( SECTION,
"this in a with() block",
GLOBAL,
this+"" );
this.TestCase( SECTION,
"new MyObject('hello').value",
"hello",
value );
this.TestCase( SECTION,
"eval(new MyObject('hello').value)",
"hello",
eval("value") );
this.TestCase( SECTION,
"new MyObject('hello').getClass()",
"[object Object]",
getClass() );
this.TestCase( SECTION,
"eval(new MyObject('hello').getClass())",
"[object Object]",
eval("getClass()") );
this.TestCase( SECTION,
"eval(new MyObject('hello').toString())",
"hello",
eval("toString()") );
this.TestCase( SECTION,
"eval('getClass') == Object.prototype.toString",
true,
eval("getClass") == Object.prototype.toString );

for ( i = 0; i < GLOBAL_PROPERTIES.length; i++ ) {
this.TestCase( SECTION, GLOBAL_PROPERTIES[i] +
" == THIS["+GLOBAL_PROPERTIES[i]+"]", true,
eval(GLOBAL_PROPERTIES[i]) == eval( "THIS[GLOBAL_PROPERTIES[i]]") );
}

}

//test();

function MyObject( value ) {
this.value = value;
this.getClass = Object.prototype.toString;
this.toString = new Function( "return this.value+''" );
return this;
}

},

/**
File Name:          10.2.3-1.js
ECMA Section:       10.2.3 Function and Anonymous Code
Description:

The scope chain is initialized to contain the activation object followed
by the global object. Variable instantiation is performed using the
activation by the global object. Variable instantiation is performed using
the activation object as the variable object and using property attributes
{ DontDelete }. The caller provides the this value. If the this value
provided by the caller is not an object (including the case where it is
null), then the this value is the global object.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_10_2_3__1: function() {
var SECTION = "10.2.3-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Eval Code";

//writeHeaderToLog( SECTION + " "+ TITLE);

var o = new MyObject("hello");

this.TestCase( SECTION,
"var o = new MyObject('hello'); o.THIS == x",
true,
o.THIS == o );

o = MyFunction();

this.TestCase( SECTION,
"var o = MyFunction(); o == this",
true,
o == this );

//test();

function MyFunction( value ) {
return this;
}
function MyObject( value ) {
this.THIS = this;
}

},

/**
File Name:          10.2.3-2.js
ECMA Section:       10.2.3 Function and Anonymous Code
Description:

The scope chain is initialized to contain the activation object followed
by the global object. Variable instantiation is performed using the
activation by the global object. Variable instantiation is performed using
the activation object as the variable object and using property attributes
{ DontDelete }. The caller provides the this value. If the this value
provided by the caller is not an object (including the case where it is
null), then the this value is the global object.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_10_2_3__2: function() {
var SECTION = "10.2.3-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Function and Anonymous Code";

//writeHeaderToLog( SECTION + " "+ TITLE);

var o = new MyObject("hello");

this.TestCase( SECTION,
"MyFunction(\"PASSED!\")",
"PASSED!",
MyFunction("PASSED!") );

o = MyFunction();

this.TestCase( SECTION,
"MyOtherFunction(true);",
false,
MyOtherFunction(true) );

//test();

function MyFunction( value ) {
var x = value;
delete x;
return x;
}
function MyOtherFunction(value) {
var x = value;
return delete x;
}
function MyObject( value ) {
this.THIS = this;
}

}

})
.endType();

