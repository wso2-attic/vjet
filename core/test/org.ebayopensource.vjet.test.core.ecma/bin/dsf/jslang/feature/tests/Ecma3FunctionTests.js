vjo.ctype("dsf.jslang.feature.tests.Ecma3FunctionTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({

constructs: function() {
this.base();
},

inSection:function(x) {
var SECT_PREFIX = 'Section ';
var SECT_SUFFIX = ' of test - ';
return SECT_PREFIX + x + SECT_SUFFIX;
},

/** @Test
File Name:         15_3_4_3__1.js
Date:    21 May 2002
SUMMARY: ECMA conformance of Function.prototype.apply

Function.prototype.apply(thisArg, argArray)

See ECMA-262 Edition 3 Final, Section 15.3.4.3
*/
test_15_3_4_3__1: function () {
var SECTION = "15_3_4_3__1.js";

var UBound = 0;
//var BUGNUMBER = 145791;
var summary = 'Testing ECMA conformance of Function.prototype.apply';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];

function F0(a)
{
return "" + this + arguments.length;
}

function F1(a)
{
return "" + this + a;
}

function F2()
{
return "" + this;
}

/*
* Function.prototype.apply.length should return 2
*/
status = this.inSection(1);
actual = Function.prototype.apply.length;
expect = 2;
addThis();

/*
* When |thisArg| is not provided to the apply() method, the
* called function must be passed the global object as |this|
*/
status = this.inSection(2);
actual = F0.apply();
expect = "" + this + 0;
addThis();

/*
* If |argArray| is not provided to the apply() method, the
* called function should be invoked with an empty argument list
*/
status = this.inSection(3);
actual = F0.apply("");
expect = "" + "" + 0;
addThis();

status = this.inSection(4);
actual = F0.apply(true);
expect = "" + true + 0;
addThis();

/*
* Function.prototype.apply(x) and
* Function.prototype.apply(x, undefined) should return the same result
*/
status = this.inSection(5);
actual = F1.apply(0, undefined);
expect = F1.apply(0);
addThis();

status = this.inSection(6);
actual = F1.apply("", undefined);
expect = F1.apply("");
addThis();

status = this.inSection(7);
actual = F1.apply(null, undefined);
expect = F1.apply(null);
addThis();

status = this.inSection(8);
actual = F1.apply(undefined, undefined);
expect = F1.apply(undefined);
addThis();

/*
* Function.prototype.apply(x) and
* Function.prototype.apply(x, null) should return the same result
*/
status = this.inSection(9);
actual = F1.apply(0, null);
expect = F1.apply(0);
addThis();

status = this.inSection(10);
actual = F1.apply("", null);
expect = F1.apply("");
addThis();

status = this.inSection(11);
actual = F1.apply(null, null);
expect = F1.apply(null);
addThis();

status = this.inSection(12);
actual = F1.apply(undefined, null);
expect = F1.apply(undefined);
addThis();

/*
* Function.prototype.apply() and
* Function.prototype.apply(undefined) should return the same result
*/
status = this.inSection(13);
actual = F2.apply(undefined);
expect = F2.apply();
addThis();

/*
* Function.prototype.apply() and
* Function.prototype.apply(null) should return the same result
*/
status = this.inSection(14);
actual = F2.apply(null);
expect = F2.apply();
addThis();

//test();

function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
}

//function test()
//{
//enterFunc('test');
//printBugNumber(BUGNUMBER);
//printStatus(summary);

for (var i=0; i<UBound; i++)
{
this.TestCase(SECTION, statusitems[i], expectedvalues[i], actualvalues[i]);
}

//exitFunc ('test');
//}
},

/** @Test
File Name:         15_3_4_4__1.js
Date:    21 May 2002
SUMMARY: ECMA conformance of Function.prototype.call

Function.prototype.call(thisArg [,arg1 [,arg2, ...]])

See ECMA-262 Edition 3 Final, Section 15.3.4.4

Description:       Date.prototype.toLocaleString should not clamp year.
*/
test_15_3_4_4__1: function () {
var SECTION = "15_3_4_4__1.js";
var SECT_PREFIX = 'Section ';
var SECT_SUFFIX = ' of test - ';
var UBound = 0;
//var BUGNUMBER = 145791;
var summary = 'Testing ECMA conformance of Function.prototype.call';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];

function F0(a)
{
return "" + this + arguments.length;
}

function F1(a)
{
return "" + this + a;
}

function F2()
{
return "" + this;
}

/*
* Function.prototype.call.length should return 1
*/
status = this.inSection(1);
actual = Function.prototype.call.length;
expect = 1;
addThis();

/*
* When |thisArg| is not provided to the call() method, the
* called function must be passed the global object as |this|
*/
status = this.inSection(2);
actual = F0.call();
expect = "" + this + 0;
addThis();

/*
* If [,arg1 [,arg2, ...]] are not provided to the call() method,
* the called function should be invoked with an empty argument list
*/
status = this.inSection(3);
actual = F0.call("");
expect = "" + "" + 0;
addThis();

status = this.inSection(4);
actual = F0.call(true);
expect = "" + true + 0;
addThis();

/*
* Function.prototype.call(x) and
* Function.prototype.call(x, undefined) should return the same result
*/
status = this.inSection(5);
actual = F1.call(0, undefined);
expect = F1.call(0);
addThis();

status = this.inSection(6);
actual = F1.call("", undefined);
expect = F1.call("");
addThis();

status = this.inSection(7);
actual = F1.call(null, undefined);
expect = F1.call(null);
addThis();

status = this.inSection(8);
actual = F1.call(undefined, undefined);
expect = F1.call(undefined);
addThis();

/*
* Function.prototype.call() and
* Function.prototype.call(undefined) should return the same result
*/
status = this.inSection(9);
actual = F2.call(undefined);
expect = F2.call();
addThis();

/*
* Function.prototype.call() and
* Function.prototype.call(null) should return the same result
*/
status = this.inSection(10);
actual = F2.call(null);
expect = F2.call();
addThis();

//test();

function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
}

//function test()
//{
//enterFunc('test');
//printBugNumber(BUGNUMBER);
//printStatus(summary);

for (var i=0; i<UBound; i++)
{
this.TestCase(SECTION, statusitems[i], expectedvalues[i], actualvalues[i]);
}

//exitFunc ('test');
//}
},

/** @Test
File Name:         arguments__001.js
Date: 07 May 2001

SUMMARY:  Testing the arguments object

See http://bugzilla.mozilla.org/show_bug.cgi?id=72884
*/
test_arguments__001: function () {
var SECTION = "arguments__001.js";
var SECT_PREFIX = 'Section ';
var SECT_SUFFIX = ' of test - ';
var UBound = 0;
//var BUGNUMBER = 72884;
var summary = 'Testing the arguments object';
var status = '';
var statusitems = [ ];
var actual = '';
var actualvalues = [ ];
var expect= '';
var expectedvalues = [ ];
var a = '';

status = this.inSection(1);
function f()
{
delete arguments.length;
return arguments;
}

a = f();
actual = a instanceof Object;
expect = true;
addThis();

actual = a instanceof Array;
expect = false;
addThis();

actual = a.length;
expect = undefined;
addThis();

status = this.inSection(2);
a = f(1,2,3);
actual = a instanceof Object;
expect = true;
addThis();

actual = a instanceof Array;
expect = false;
addThis();

actual = a.length;
expect = undefined;
addThis();

actual = a[0];
expect = 1;
addThis();

actual = a[1];
expect = 2;
addThis();

actual = a[2];
expect = 3;
addThis();

status = this.inSection(3);
/*
* Brendan:
*
* Note that only callee and length can be overridden, so deleting an indexed
* property and asking for it again causes it to be recreated by args_resolve:
*
* function g(){delete arguments[0]; return arguments[0]}
* g(42)     // should this print 42?
*
* I'm not positive this violates ECMA, which allows in chapter 16 for extensions
* including properties (does it allow for magically reappearing properties?).  The
* delete operator successfully deletes arguments[0] and results in true, but that
* is not distinguishable from the case where arguments[0] was delegated to
* Arguments.prototype[0], which was how the bad old code worked.
*
* I'll ponder this last detail...
*
* UPDATE: Per ECMA-262, delete on an arguments[i] should succeed
* and remove that property from the arguments object, leaving any get
* of it after the delete to evaluate to undefined.
*/
function g()
{
delete arguments[0];
return arguments[0];
}
actual = g(42);
expect = undefined;  // not 42...
addThis();

//test();

function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
}

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

for (var i = 0; i < UBound; i++)
{
this.TestCase(SECTION, statusitems[i], expectedvalues[i], actualvalues[i]);
}

//exitFunc ('test');
//}

},

/** @Test
File Name:         arguments__002.js
Description:       Allow override of arguments
*/
test_arguments__002: function () {
var SECTION = "arguments__002.js";
//var BUGNUMBER = 383269;
var summary = 'Allow override of arguments';
var actual = '';
var expect = '';

//test();

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

var expect1 = '33,42';
var expect2 = 33;
var actual1 = '';
var actual2 = '';

function f(){
var a=arguments; actual1 = a[0]; arguments=42; actual1 += ',' + arguments; return a;
}

actual2 = f(33)[0];

expect = expect1 + ':' + expect2;
actual = actual1 + ':' + actual2;

this.TestCase(SECTION, summary, expect, actual);

//exitFunc ('test');

//}
},

/** @Test
File Name:         call__001.js
Date: 2001-07-13

SUMMARY: Applying Function.prototype.call to the Function object itself
*/
test_call__001: function () {
var SECTION = "call__001.js";
var UBound = 0;
//var BUGNUMBER = '(none)';
var summary = 'Applying Function.prototype.call to the Function object itself';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];
var self = this; // capture a reference to the global object
var cnOBJECT_GLOBAL = self.toString();
var cnOBJECT_OBJECT = (new Object).toString();
var cnHello = 'Hello';
var cnRed = 'red';
var objTEST = {color:cnRed};
var f = new Function();
var g = new Function();


f = Function.call(self, 'return cnHello');
g = Function.call(objTEST, 'return cnHello');

status = 'Section A of test';
actual = f();
expect = cnHello;
captureThis();

status = 'Section B of test';
actual = g();
expect = cnHello;
captureThis();


f = Function.call(self, 'return this.toString()');
g = Function.call(objTEST, 'return this.toString()');

status = 'Section C of test';
actual = f();
expect = cnOBJECT_GLOBAL;
captureThis();

status = 'Section D of test';
actual = g();
expect = cnOBJECT_GLOBAL;
captureThis();


f = Function.call(self, 'return this.color');
g = Function.call(objTEST, 'return this.color');

status = 'Section E of test';
actual = f();
expect = undefined;
captureThis();

status = 'Section F of test';
actual = g();
expect = undefined;
captureThis();



//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------


function captureThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
}


//function test()
//{
// enterFunc ('test');
// printBugNumber(BUGNUMBER);
// printStatus (summary);

for (var i = 0; i < UBound; i++)
{
this.TestCase(SECTION, statusitems[i], expectedvalues[i], actualvalues[i]);
}

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__131964.js
Date:    19 Mar 2002
SUMMARY: Function declarations in global or function scope are {DontDelete}.
Function declarations in eval scope are not {DontDelete}.

See http://bugzilla.mozilla.org/show_bug.cgi?id=131964
*/
test_regress__131964: function () {
var SECTION = "regress__131964.js";
var UBound = 0;
//var BUGNUMBER =   131964;
var summary = 'Functions defined in global or function scope are {DontDelete}';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];

status = this.inSection(1);
function f()
{
return 'f lives!';
}

delete f;

try
{
actual = f();
}
catch(e)
{
actual = 'f was deleted';
}

expect = 'f lives!';
addThis();

/*
* Try the same test in function scope -
*/
status = this.inSection(2);
function g()
{
function f()
{
return 'f lives!';
}
delete f;

try
{
actual = f();
}
catch(e)
{
actual = 'f was deleted';
}

expect = 'f lives!';
addThis();
}
g();

/*
* Try the same test in eval scope - here we EXPECT the function to be deleted (?)
*/

status = this.inSection(3);
var s = '';
s += 'function h()';
s += '{ ';
s += '  return "h lives!";';
s += '}';
s += 'delete h;';

s += 'try';
s += '{';
s += '  actual = h();';
s += '}';
s += 'catch(e)';
s += '{';
s += '  actual = "h was deleted";';
s += '}';

s += 'expect = "h was deleted";';
s += 'addThis();';
eval(s);

/*
* Define the function in eval scope, but delete it in global scope -
*/
status = this.inSection(4);
s = '';
s += 'function k()';
s += '{ ';
s += '  return "k lives!";';
s += '}';
eval(s);
var k = k;
delete k;

try
{
actual = k();
}
catch(e)
{
actual = 'k was deleted';
}

expect = 'k was deleted';
addThis();

//test();

function wasDeleted(functionName)
{
return functionName + ' was deleted...';
}

function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
}

//function test()
//{
//enterFunc('test');
//printBugNumber(BUGNUMBER);
//printStatus(summary);

for (var i=0; i<UBound; i++)
{
this.TestCase(SECTION, statusitems[i], expectedvalues[i], actualvalues[i]);
}

//exitFunc ('test');
//}
},

/**
File Name:          regress__137181.js
Date:    12 Apr 2002
SUMMARY: delete arguments[i] should break connection to local reference

See http://bugzilla.mozilla.org/show_bug.cgi?id=137181
*/
test_regress__137181: function() {
var SECTION = "regress__137181";
var UBound = 0;
//var BUGNUMBER = 137181;
var summary = 'delete arguments[i] should break connection to local reference';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];

status = this.inSection(1);
function f1(x)
{
x = 1;
delete arguments[0];
return x;
}
actual = f1(0); // (bug: Rhino was returning |undefined|)
expect = 1;
addThis();

status = this.inSection(2);
function f2(x)
{
x = 1;
delete arguments[0];
arguments[0] = -1;
return x;
}
actual = f2(0); // (bug: Rhino was returning -1)
expect = 1;
addThis();

//test();

function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
}

//function test()
//{
//enterFunc('test');
//printBugNumber(BUGNUMBER);
//printStatus(summary);

for (var i=0; i<UBound; i++)
{
this.TestCase(SECTION, statusitems[i], expectedvalues[i], actualvalues[i]);
}

//exitFunc ('test');
//}
},

/**
File Name:          regress__193555.js
Date:    17 February 2003
SUMMARY: Testing access to function name from inside function

See http://bugzilla.mozilla.org/show_bug.cgi?id=193555
*/
test_regress__193555: function() {
var SECTION = "regress__193555";
var UBound = 0;
//var BUGNUMBER = 193555;
var summary = 'Testing access to function name from inside function';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];

// test via function statement
status = this.inSection(1);
function f() {return f.toString();};
actual = f();
expect = f.toString();
addThis();

// test via function expression
status = this.inSection(2);
var x = function g() {return g.toString();};
actual = x();
expect = x.toString();
addThis();
var a =a;
// test via eval() outside function
status = this.inSection(3);
eval ('function a() {return a.toString();}');
actual = a();
expect = a.toString();
addThis();
var y=y;
status = this.inSection(4);
eval ('var y = function b() {return b.toString();}');
actual = y();
expect = y.toString();
addThis();
var c=c;
// test via eval() inside function
status = this.inSection(5);
function c() {return eval('c').toString();};
actual = c();
expect = c.toString();
addThis();

var d=d;
status = this.inSection(6);
var z = function d() {return eval('d').toString();};
actual = z();
expect = z.toString();
addThis();

// test via two evals!
var w = w;
status = this.inSection(7);
eval('var w = function e() {return eval("e").toString();}');
actual = w();
expect = w.toString();
addThis();

//test();

function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
}

//function test()
//{
//enterFunc('test');
//printBugNumber(BUGNUMBER);
//printStatus(summary);

for (var i=0; i<UBound; i++)
{
this.TestCase(SECTION, statusitems[i], expectedvalues[i], actualvalues[i]);
}

//exitFunc ('test');
//}
},

/**
File Name:          regress__313570.js
Summary: length of objects whose prototype chain includes a function
*/
test_regress__313570: function() {
var SECTION = "regress__313570";
//var BUGNUMBER = 313570;
var summary = 'length of objects whose prototype chain includes a function';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

function tmp() {}
tmp.prototype = function(a, b, c) {};
var obj = new tmp();

// arity
expect = 3;
actual = obj.length;
this.TestCase(SECTION, summary + ': arity', expect, actual);

// immutable
obj.length = 10;

expect = 3;
actual = obj.length;
this.TestCase(SECTION, summary + ': immutable', expect, actual);
},

/**
File Name:          regress__49286.js
Date: 2001-07-10

SUMMARY:  Invoking try...catch through Function.call
See  http://bugzilla.mozilla.org/show_bug.cgi?id=49286

1) Define a function with a try...catch block in it
2) Invoke the function via the call method of Function
3) Pass bad syntax to the try...catch block
4) We should catch the error!
*/
test_regress__49286: function() {
var SECTION = "regress__49286";
var UBound = 0;
//var BUGNUMBER = 49286;
var summary = 'Invoking try...catch through Function.call';
var cnErrorCaught = 'Error caught';
var cnErrorNotCaught = 'Error NOT caught';
var cnGoodSyntax = '1==2';
var cnBadSyntax = '1=2';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


var obj = new testObject();

status = 'Section A of test: direct call of f';
actual = f.call(obj);
expect = cnErrorCaught;
addThis();

status = 'Section B of test: indirect call of f';
actual = g.call(obj);
expect = cnErrorCaught;
addThis();



//-----------------------------------------
//test();
//-----------------------------------------


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

for (var i=0; i<UBound; i++)
{
this.TestCase(SECTION, statusitems[i], expectedvalues[i], actualvalues[i]);
}

//exitFunc ('test');
//}


// An object storing bad syntax as a property -
function testObject()
{
this.badSyntax = cnBadSyntax;
this.goodSyntax = cnGoodSyntax;
}


// A function wrapping a try...catch block
function f()
{
try
{
eval(this.badSyntax);
}
catch(e)
{
return cnErrorCaught;
}
return cnErrorNotCaught;
}


// A function wrapping a call to f -
function g()
{
return f.call(this);
}


function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
}
},

/**
File Name:          regress__58274.js
Date:    15 July 2002
SUMMARY: Testing functions with double-byte names
See http://bugzilla.mozilla.org/show_bug.cgi?id=58274

*/
test_regress__58274: function() {
var SECTION = "regress__58274";
var UBound = 0;
//var BUGNUMBER = 58274;
var summary = 'Testing functions with double-byte names';
var ERR = 'UNEXPECTED ERROR! \n';
var ERR_MALFORMED_NAME = ERR + 'Could not find function name in: \n\n';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];
var sEval;
var sName;


sEval = "function f\u02B2() {return 42;}";
eval(sEval);
sName = getFunctionName(f\u02B2);

// Test function call -
status = this.inSection(1);
actual = f\u02B2();
expect = 42;
addThis();

// Test both characters of function name -
status = this.inSection(2);
actual = sName[0];
expect = sEval[9];
addThis();

status = this.inSection(3);
actual = sName[1];
expect = sEval[10];
addThis();



sEval = "function f\u02B2\u0AAA () {return 84;}";
eval(sEval);
sName = getFunctionName(f\u02B2\u0AAA);

// Test function call -
status = this.inSection(4);
actual = f\u02B2\u0AAA();
expect = 84;
addThis();

// Test all three characters of function name -
status = this.inSection(5);
actual = sName[0];
expect = sEval[9];
addThis();

status = this.inSection(6);
actual = sName[1];
expect = sEval[10];
addThis();

status = this.inSection(7);
actual = sName[2];
expect = sEval[11];
addThis();




//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------



/*
* Goal: test that f.toString() contains the proper function name.
*
* Note, however, f.toString() is implementation-independent. For example,
* it may begin with '\nfunction' instead of 'function'. Therefore we use
* a regexp to make sure we extract the name properly.
*
* Here we assume that f has been defined by means of a function statement,
* and not a function expression (where it wouldn't have to have a name).
*
* Rhino uses a Unicode representation for f.toString(); whereas
* SpiderMonkey uses an ASCII representation, putting escape sequences
* for non-ASCII characters. For example, if a function is called f\u02B1,
* then in Rhino the toString() method will present a 2-character Unicode
* string for its name, whereas SpiderMonkey will present a 7-character
* ASCII string for its name: the string literal 'f\u02B1'.
*
* So we force the lexer to condense the string before using it.
* This will give uniform results in Rhino and SpiderMonkey.
*/
function getFunctionName(f)
{
var s = condenseStr(f.toString());
var re = /\s*function\s+(\S+)\s*\(/;
var arr = s.match(re);

if (!(arr && arr[1]))
return ERR_MALFORMED_NAME + s;
return arr[1];
}


/*
* This function is the opposite of functions like escape(), which take
* Unicode characters and return escape sequences for them. Here, we force
* the lexer to turn escape sequences back into single characters.
*
* Note we can't simply do |eval(str)|, since in practice |str| will be an
* identifier somewhere in the program (e.g. a function name); thus |eval(str)|
* would return the object that the identifier represents: not what we want.
*
* So we surround |str| lexicographically with quotes to force the lexer to
* evaluate it as a string. Have to strip out any linefeeds first, however -
*/
function condenseStr(str)
{
/*
* You won't be able to do the next step if |str| has
* any carriage returns or linefeeds in it. For example:
*
*  js> eval("'" + '\nHello' + "'");
*  1: SyntaxError: unterminated string literal:
*  1: '
*  1: ^
*
* So replace them with the empty string -
*/
str = str.replace(/[\r\n]/g, '')
return eval("'" + str + "'");
}


function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
}


//function test()
//{
//enterFunc('test');
//printBugNumber(BUGNUMBER);
//printStatus(summary);

for (var i=0; i<UBound; i++)
{
this.TestCase(SECTION, statusitems[i], expectedvalues[i], actualvalues[i]);
}

//exitFunc ('test');
//}
},

/**
File Name:          regress__85880.js
Date: 2001-06-14

SUMMARY: Regression test for Bugzilla bug 85880

Rhino interpreted mode was nulling out the arguments object of a
function if it happened to call another function inside its body.

See http://bugzilla.mozilla.org/show_bug.cgi?id=85880
*/
test_regress__85880: function() {
var SECTION = "regress__85880";
var UBound = 0;
//var BUGNUMBER = 85880;
var summary = 'Arguments object of g(){f()} should not be null';
var cnNonNull = 'Arguments != null';
var cnNull = 'Arguments == null';
var cnRecurse = true;
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


function f1(x)
{
}


function f2()
{
return f2.arguments;
}
status = 'Section A of test';
actual = (f2() == null);
expect = false;
addThis();

status = 'Section B of test';
actual = (f2(0) == null);
expect = false;
addThis();


function f3()
{
f1();
return f3.arguments;
}
status = 'Section C of test';
actual = (f3() == null);
expect = false;
addThis();

status = 'Section D of test';
actual = (f3(0) == null);
expect = false;
addThis();


function f4()
{
f1();
f2();
f3();
return f4.arguments;
}
status = 'Section E of test';
actual = (f4() == null);
expect = false;
addThis();

status = 'Section F of test';
actual = (f4(0) == null);
expect = false;
addThis();


function f5()
{
if (cnRecurse)
{
cnRecurse = false;
f5();
}
return f5.arguments;
}
status = 'Section G of test';
actual = (f5() == null);
expect = false;
addThis();

status = 'Section H of test';
actual = (f5(0) == null);
expect = false;
addThis();



//-------------------------------------------------------------------------------------------------
//test();
//-------------------------------------------------------------------------------------------------


function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = isThisNull(actual);
expectedvalues[UBound] = isThisNull(expect);
UBound++;
}


//function test()
//{
// enterFunc ('test');
// printBugNumber(BUGNUMBER);
// printStatus (summary);

for (var i = 0; i < UBound; i++)
{
this.TestCase(SECTION, statusitems[i], expectedvalues[i], actualvalues[i]);
}

// exitFunc ('test');
//}


function isThisNull(bool)
{
return bool? cnNull : cnNonNull
}
},

/**
File Name:          regress__94506.js
Date: 08 August 2001

SUMMARY: When we invoke a function, the arguments object should take
a back seat to any local identifier named "arguments".

See http://bugzilla.mozilla.org/show_bug.cgi?id=94506
*/
test_regress__94506: function() {
var SECTION = "regress__94506";
var UBound = 0;
//var BUGNUMBER = 94506;
var summary = 'Testing functions employing identifiers named "arguments"';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];
var TYPE_OBJECT = typeof new Object();
var arguments = 5555;


// use a parameter named "arguments"
function F1(arguments)
{
return arguments;
}


// use a local variable named "arguments"
function F2()
{
var arguments = 55;
return arguments;
}


// same thing in a different order. CHANGES THE RESULT!
function F3()
{
return arguments;
var arguments = 555;
}


// use the global variable above named "arguments"
function F4()
{
return arguments;
}



/*
* In Sections 1 and 2, expect the local identifier, not the arguments object.
* In Sections 3 and 4, expect the arguments object, not the the identifier.
*/

status = 'Section 1 of test';
actual = F1(5);
expect = 5;
addThis();


status = 'Section 2 of test';
actual = F2();
expect = 55;
addThis();


status = 'Section 3 of test';
actual = typeof F3();
expect = TYPE_OBJECT;
addThis();


status = 'Section 4 of test';
actual = typeof F4();
expect = TYPE_OBJECT;
addThis();


// Let's try calling F1 without providing a parameter -
status = 'Section 5 of test';
actual = F1();
expect = undefined;
addThis();


// Let's try calling F1 with too many parameters -
status = 'Section 6 of test';
actual = F1(3,33,333);
expect = 3;
addThis();



//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------


function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

for (var i = 0; i < UBound; i++)
{
this.TestCase(SECTION, statusitems[i], expectedvalues[i], actualvalues[i]);
}

//exitFunc ('test');
//}
},

/**
File Name:          regress__97921.js
Date: 08 August 2001

SUMMARY: When we invoke a function, the arguments object should take
a back seat to any local identifier named "arguments".

See http://bugzilla.mozilla.org/show_bug.cgi?id=94506
*/
test_regress__97921: function() {
var SECTION = "regress__97921";
var UBound = 0;
//var BUGNUMBER = 97921;
var summary = 'Testing with() statement with nested functions';
var cnYES = 'Inner value === outer value';
var cnNO = "Inner value !== outer value!";
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];
var outerValue = '';
var innerValue = '';
var useWith = '';


function F(i)
{
i = 0;
if(useWith) with(1){i;}
i++;

outerValue = i; // capture value of i in outer function
F1 = function() {innerValue = i;}; // capture value of i in inner function
F1();
}


status = this.inSection(1);
useWith=false;
F(); // call F without supplying the argument
actual = innerValue === outerValue;
expect = true;
addThis();

status = this.inSection(2);
useWith=true;
F(); // call F without supplying the argument
actual = innerValue === outerValue;
expect = true;
addThis();


function G(i)
{
i = 0;
with (new Object()) {i=100};
i++;

outerValue = i; // capture value of i in outer function
G1 = function() {innerValue = i;}; // capture value of i in inner function
G1();
}


status = this.inSection(3);
G(); // call G without supplying the argument
actual = innerValue === 101;
expect = true;
addThis();

status = this.inSection(4);
G(); // call G without supplying the argument
actual = innerValue === outerValue;
expect = true;
addThis();



//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------


function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = areTheseEqual(actual);
expectedvalues[UBound] = areTheseEqual(expect);
UBound++;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

for (var i = 0; i < UBound; i++)
{
this.TestCase(SECTION, statusitems[i], expectedvalues[i], actualvalues[i]);
}

//exitFunc ('test');
//}


function areTheseEqual(yes)
{
return yes? cnYES : cnNO
}
},

/**
File Name:          scope__001.js
Date: 28 May 2001

SUMMARY:  Functions are scoped statically, not dynamically

See ECMA Section 10.1.4 Scope Chain and Identifier Resolution
(This section defines the scope chain of an execution context)

See ECMA Section 12.10 The with Statement

See ECMA Section 13 Function Definition
(This section defines the scope chain of a function object as that
of the running execution context when the function was declared)
*/
test_scope__001: function() {
var SECTION = "scope__001";
var UBound = 0;
//var BUGNUMBER = '(none)';
var summary = 'Testing that functions are scoped statically, not dynamically';
var self = this;  // capture a reference to the global object
var status = '';
var statusitems = [ ];
var actual = '';
var actualvalues = [ ];
var expect= '';
var expectedvalues = [ ];

/*
* In this section the expected value is 1, not 2.
*
* Why? f captures its scope chain from when it's declared, and imposes that chain
* when it's executed. In other words, f's scope chain is from when it was compiled.
* Since f is a top-level function, this is the global object only. Hence 'a' resolves to 1.
*/
status = 'Section A of test';
var a = 1;
function f()
{
return a;
}
var obj = {a:2};
with (obj)
{
actual = f();
}
expect = 1;
addThis();


/*
* In this section the expected value is 2, not 1. That is because here
* f's associated scope chain now includes 'obj' before the global object.
*/
status = 'Section B of test';
var a = 1;
var obj = {a:2};
with (obj)
{
function f()
{
return a;
}
actual = f();
}
expect = 2;
addThis();


/*
* Like Section B , except that we call f outside the with block.
* By the principles explained above, we still expect 2 -
*/
status = 'Section C of test';
var a = 1;
var obj = {a:2};
with (obj)
{
function f()
{
return a;
}
}
actual = f();
expect = 2;
addThis();


/*
* Like Section C, but with one more level of indirection -
*/
status = 'Section D of test';
var a = 1;
var obj = {a:2, obj:{a:3}};
with (obj)
{
with (obj)
{
function f()
{
return a;
}
}
}
actual = f();
expect = 3;
addThis();


/*
* Like Section C, but here we actually delete obj before calling f.
* We still expect 2 -
*/
status = 'Section E of test';
var a = 1;
var obj = {a:2};
with (obj)
{
function f()
{
return a;
}
}
delete obj;
actual = f();
expect = 2;
addThis();


/*
* Like Section E. Here we redefine obj and call f under with (obj) -
* We still expect 2 -
*/
status = 'Section F of test';
var a = 1;
var obj = {a:2};
with (obj)
{
function f()
{
return a;
}
}
delete obj;
var obj = {a:3};
with (obj)
{
actual = f();
}
expect = 2;  // NOT 3 !!!
addThis();


/*
* Explicitly verify that f exists at global level, even though
* it was defined under the with(obj) block -
*/
status = 'Section G of test';
var a = 1;
var obj = {a:2};
with (obj)
{
function f()
{
return a;
}
}
actual = String([obj.hasOwnProperty('f'), self.hasOwnProperty('f')]);
expect = String([false, true]);
addThis();


/*
* Explicitly verify that f exists at global level, even though
* it was defined under the with(obj) block -
*/
status = 'Section H of test';
var a = 1;
var obj = {a:2};
with (obj)
{
function f()
{
return a;
}
}
actual = String(['f' in obj, 'f' in self]);
expect = String([false, true]);
addThis();



//-------------------------------------------------------------------------------------------------
//test();
//-------------------------------------------------------------------------------------------------


function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
resetTestVars();
}


function resetTestVars()
{
delete a;
delete obj;
delete f;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

for (var i = 0; i < UBound; i++)
{
this.TestCase(SECTION, statusitems[i], expectedvalues[i], actualvalues[i]);
}

//exitFunc ('test');
//}
},

/**
File Name:          scope__002.js
Date: 28 May 2001

SUMMARY:  Functions are scoped statically, not dynamically

See ECMA Section 10.1.4 Scope Chain and Identifier Resolution
(This section defines the scope chain of an execution context)

See ECMA Section 12.10 The with Statement

See ECMA Section 13 Function Definition
(This section defines the scope chain of a function object as that
of the running execution context when the function was declared)

Like scope-001.js, but using assignment var f = function expression
instead of a function declaration: function f() {} etc.
*/
test_scope__002: function() {
var SECTION = "scope__002";
var UBound = 0;
//var BUGNUMBER = '(none)';
var summary = 'Testing that functions are scoped statically, not dynamically';
var self = this;  // capture a reference to the global object
var status = '';
var statusitems = [ ];
var actual = '';
var actualvalues = [ ];
var expect= '';
var expectedvalues = [ ];


/*
* In this section the expected value is 1, not 2.
*
* Why? f captures its scope chain from when it's declared, and imposes that chain
* when it's executed. In other words, f's scope chain is from when it was compiled.
* Since f is a top-level function, this is the global object only. Hence 'a' resolves to 1.
*/
status = 'Section A of test';
var a = 1;
var f = function () {return a;};
var obj = {a:2};
with (obj)
{
actual = f();
}
expect = 1;
addThis();


/*
* In this section the expected value is 2, not 1. That is because here
* f's associated scope chain now includes 'obj' before the global object.
*/
status = 'Section B of test';
var a = 1;
var obj = {a:2};
with (obj)
{
var f = function () {return a;};
actual = f();
}
expect = 2;
addThis();


/*
* Like Section B , except that we call f outside the with block.
* By the principles explained above, we still expect 2 -
*/
status = 'Section C of test';
var a = 1;
var obj = {a:2};
with (obj)
{
var f = function () {return a;};
}
actual = f();
expect = 2;
addThis();


/*
* Like Section C, but with one more level of indirection -
*/
status = 'Section D of test';
var a = 1;
var obj = {a:2, obj:{a:3}};
with (obj)
{
with (obj)
{
var f = function () {return a;};
}
}
actual = f();
expect = 3;
addThis();


/*
* Like Section C, but here we actually delete obj before calling f.
* We still expect 2 -
*/
status = 'Section E of test';
var a = 1;
var obj = {a:2};
with (obj)
{
var f = function () {return a;};
}
delete obj;
actual = f();
expect = 2;
addThis();


/*
* Like Section E. Here we redefine obj and call f under with (obj) -
* We still expect 2 -
*/
status = 'Section F of test';
var a = 1;
var obj = {a:2};
with (obj)
{
var f = function () {return a;};
}
delete obj;
var obj = {a:3};
with (obj)
{
actual = f();
}
expect = 2;  // NOT 3 !!!
addThis();


/*
* Explicitly verify that f exists at global level, even though
* it was defined under the with(obj) block -
*/
status = 'Section G of test';
var a = 1;
var obj = {a:2};
with (obj)
{
var f = function () {return a;};
}
actual = String([obj.hasOwnProperty('f'), self.hasOwnProperty('f')]);
expect = String([false, true]);
addThis();


/*
* Explicitly verify that f exists at global level, even though
* it was defined under the with(obj) block -
*/
status = 'Section H of test';
var a = 1;
var obj = {a:2};
with (obj)
{
var f = function () {return a;};
}
actual = String(['f' in obj, 'f' in self]);
expect = String([false, true]);
addThis();



//-------------------------------------------------------------------------------------------------
//test();
//-------------------------------------------------------------------------------------------------


function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
resetTestVars();
}


function resetTestVars()
{
delete a;
delete obj;
delete f;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

for (var i = 0; i < UBound; i++)
{
this.TestCase(SECTION, statusitems[i], expectedvalues[i], actualvalues[i]);
}

//exitFunc ('test');
//}
}


}).endType()
