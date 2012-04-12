vjo.ctype("dsf.jslang.feature.tests.Js15ScopeTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({

constructs:function(){
this.base();
},

inSection :function(x) {
var SECT_PREFIX = 'Section ';
var SECT_SUFFIX = ' of test - ';
return SECT_PREFIX + x + SECT_SUFFIX;
},

outSideFunc :function(){
},

/** @Test
File Name:         regress__154693.js
Date:    26 Nov 2002
SUMMARY: Testing scope
See http://bugzilla.mozilla.org/show_bug.cgi?id=154693
*/
test_regress__154693: function () {
var SECTION = "regress__154693.js";
var UBound = 0;
//var BUGNUMBER = 154693;
var summary = 'Testing scope';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


function f()
{
function nested() {}
return nested;
}
var f1 = f();
var f2 = f();

status = this.inSection(1);
actual = (f1 != f2);
expect = true;
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
File Name:         regress__181834.js
Date:    25 November 2002
SUMMARY: Testing scope
See http://bugzilla.mozilla.org/show_bug.cgi?id=181834

This bug only bit in Rhino interpreted mode, when the
'compile functions with dynamic scope' feature was set.
*/
test_regress__181834: function () {
var SECTION = "regress__181834.js";
var UBound = 0;
//var BUGNUMBER = 181834;
var summary = 'Testing scope';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


/*
* If N<=0, |outer_d| just gets incremented once,
* so the return value should be 1 in this case.
*
* If N>0, we end up calling inner() N+1 times:
* inner(N), inner(N-1), ... , inner(0).
*
* Each call to inner() increments |outer_d| by 1.
* The last call, inner(0), returns the final value
* of |outer_d|, which should be N+1.
*/
function outer(N)
{
var outer_d = 0;
}


/*
* This only has meaning in Rhino -
*/
setDynamicScope(true);

/*
* Recompile the function |outer| via eval() in order to
* feel the effect of the dynamic scope mode we have set.
*/
var s = outer.toString();
eval(s);

status = this.inSection(1);
actual = outer(-5);
expect = 1;
addThis();

status = this.inSection(2);
actual = outer(0);
expect = 1;
addThis();

status = this.inSection(3);
actual = outer(5);
expect = 6;
addThis();


/*
* Sanity check: do same steps with the dynamic flag off
*/
setDynamicScope(false);

/*
* Recompile the function |outer| via eval() in order to
* feel the effect of the dynamic scope mode we have set.
*/
eval(s);

status = this.inSection(4);
actual = outer(-5);
expect = 1;
addThis();

status = this.inSection(5);
actual = outer(0);
expect = 1;
addThis();

status = this.inSection(6);
actual = outer(5);
expect = 6;
addThis();



//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------



function setDynamicScope(flag)
{
if (this.Packages)
{
var cx = this.Packages.org.mozilla.javascript.Context.getCurrentContext();
cx.setCompileFunctionsWithDynamicScope(flag);
}
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
// enterFunc('test');
// printBugNumber(BUGNUMBER);
//printStatus(summary);

for (var i=0; i<UBound; i++)
{
this.TestCase(SECTION, statusitems[i], expectedvalues[i], actualvalues[i]);
}

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__1841074.js
* Date:    09 December 2002
* SUMMARY: with(...) { function f ...} should set f in the global scope
* See http://bugzilla.mozilla.org/show_bug.cgi?id=184107
*
* In fact, any variable defined in a with-block should be created
* in global scope, i.e. should be a property of the global object.
*
* The with-block syntax allows existing local variables to be SET,
* but does not allow new local variables to be CREATED.
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=159849#c11
*/
test_regress__1841074: function () {
var SECTION = "regress__1841074.js";
var UBound = 0;
//var BUGNUMBER = 184107;
var summary = 'with(...) { function f ...} should set f in the global scope';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];

var obj = {y:10};
with (obj)
{
// function statement
function f()
{
return y;
}

// function expression
g = function() {return y;}
}

status = this.inSection(1);
actual = obj.f;
expect = undefined;
addThis();

status =  this.inSection(2);
actual = obj.f;
expect = obj.y;
addThis();

status =  this.inSection(3);
actual = obj.g;
expect = undefined;
addThis();

status =  this.inSection(4);
actual = obj.g;
expect = obj.y;
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
File Name:         regress__185485.js
* Date:    16 Dec 2002
* SUMMARY: Testing |with (x) {function f() {}}| when |x.f| already exists
* See http://bugzilla.mozilla.org/show_bug.cgi?id=185485
*
* The idea is this: if |x| does not already have a property named |f|,
* a |with| statement cannot be used to define one. See, for example,
*
*       http://bugzilla.mozilla.org/show_bug.cgi?id=159849#c11
*       http://bugzilla.mozilla.org/show_bug.cgi?id=184107
*
*
* However, if |x| already has a property |f|, a |with| statement can be
* used to modify the value it contains:
*
*                 with (x) {f = 1;}
*
* This should work even if we use a |var| statement, like this:
*
*                 with (x) {var f = 1;}
*
* However, it should NOT work if we use a |function| statement, like this:
*
*                 with (x) {function f() {}}
*
* Instead, this should newly define a function f in global scope.
* See http://bugzilla.mozilla.org/show_bug.cgi?id=185485
*
*/
test_regress__185485: function () {
var SECTION = "regress__185485.js";
var UBound = 0;
//var BUGNUMBER = 185485;
var summary = 'Testing |with (x) {function f() {}}| when |x.f| already exists';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];

var x = { f:0, g:0 };

with (x)
{
f = 1;
}
status = this.inSection(1);
actual = x.f;
expect = 1;
addThis();

with (x)
{
var f = 2;
}
status = this.inSection(2);
actual = x.f;
expect = 2;
addThis();

/*
* Use of a function statement under the with-block should not affect
* the local property |f|, but define a function |f| in global scope -
*/
with (x)
{
function f() {}
}
status = this.inSection(3);
actual = x.f;
expect = 2;
addThis();

status = this.inSection(4);
expect = 'function';
addThis();


/*
* Compare use of function expression instead of function statement.
* Note it is important that |x.g| already exists. Otherwise, this
* would newly define |g| in global scope -
*/
with (x)
{
var g = function() {}
}
status = this.inSection(5);
actual = x.g.toString();
expect = (function () {}).toString();
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
File Name:         regress__191276.js
* Date:    30 January 2003
* SUMMARY: Testing |this[name]| via Function.prototype.call(), apply()
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=191276
*
* Igor: "This script fails when run in Rhino compiled mode, but passes in
* interpreted mode. Note that presence of the never-called |unused_function|
* with |f('a')| line is essential; the script works OK without it."
*
*/
test_regress__191276: function () {
var SECTION = "regress__191276.js";
var UBound = 0;
//var BUGNUMBER = 191276;
var summary = 'Testing |this[name]| via Function.prototype.call(), apply()';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


function F(name)
{
return this[name];
}

function unused_function()
{
F('a');
}

status =  this.inSection(1);
actual = F.call({a: 'aaa'}, 'a');
expect = 'aaa';
addThis();

status =  this.inSection(2);
actual = F.apply({a: 'aaa'}, ['a']);
expect = 'aaa';
addThis();

/*
* Try the same things with an object variable instead of a literal
*/
var obj = {a: 'aaa'};

status =  this.inSection(3);
actual = F.call(obj, 'a');
expect = 'aaa';
addThis();

status =  this.inSection(4);
actual = F.apply(obj, ['a']);
expect = 'aaa';
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
File Name:         regress__192226.js
* Date:    07 February 2003
* SUMMARY: Testing a nested function call under |with| or |catch|
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=192226
*
*/
test_regress__192226: function () {
var SECTION = "regress__192226.js";
var UBound = 0;
//var BUGNUMBER = 192226;
var summary = 'Testing a nested function call under |with| or |catch|';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];
var counter = 0;


function f()
{
try
{
with (Math)
{
test0();
test1(sin);
}
throw 1;
}
catch (e)
{
test0();
test1(e);
}
}

function test0()
{
++counter;
}

function test1(arg)
{
++counter;
}


status = this.inSection(1);
f(); // sets |counter|
actual = counter;
expect = 4;
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
// enterFunc('test');
// printBugNumber(BUGNUMBER);
// printStatus(summary);

for (var i=0; i<UBound; i++)
{
this.TestCase(SECTION, statusitems[i], expectedvalues[i], actualvalues[i]);
}

//exitFunc ('test');
//}

},

/** @Test
*	File Name:         regress__202678__001.js
*
* Date:    19 April 2003
* SUMMARY: Testing nested function scope capture
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=202678
*
*/
test_regress__202678__001: function () {
var SECTION = "regress__202678__001.js";
var UBound = 0;
//var BUGNUMBER = 202678;
var summary = 'Testing nested function scope capture';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];
var self = this;


function myFunc()
{
var hidden = 'aaa';
insideFunc();

if (!self.runOnce)
{
hidden = 'bbb';
self.outSideFunc = insideFunc;
self.runOnce = true;
}
else
{
hidden = 'ccc';
}


function insideFunc()
{
actual = hidden;
}
}



status = this.inSection(1);
myFunc();  // this sets |actual|
expect = 'aaa';
addThis();

status = this.inSection(2);
this.outSideFunc();  // sets |actual|
expect = 'bbb';
addThis();

status = this.inSection(3);
myFunc();      // sets |actual|
expect = 'aaa';
addThis();

status = this.inSection(4);
this.outSideFunc();  // sets |actual|
expect = 'bbb'; // NOT 'ccc'
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
// enterFunc('test');
// printBugNumber(BUGNUMBER);
// printStatus(summary);

for (var i=0; i<UBound; i++)
{
this.TestCase(SECTION, statusitems[i], expectedvalues[i], actualvalues[i]);
}

//exitFunc ('test');
//}

},

/** @Test
* File Name:         regress__202678__002.js
* Date:    19 April 2003
* SUMMARY: Testing nested function scope capture
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=202678
*
*/
test_regress__202678__002: function () {
var SECTION = "regress__202678__002.js";
var UBound = 0;
//var BUGNUMBER = 202678;
var summary = 'Testing nested function scope capture';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];
var self = this;


function myFunc()
{
var hidden = 'aaa';
insideFunc();

if (!self.runOnce)
{
hidden = 'bbb';
self.outSideFunc = insideFunc;
self.runOnce = true;
}
else
{
hidden = 'ccc';
self.outSideFunc = insideFunc;
}


function insideFunc()
{
actual = hidden;
}
}



status = this.inSection(1);
myFunc();  // this sets |actual|
expect = 'aaa';
addThis();

status = this.inSection(2);
this.outSideFunc();  // sets |actual|
expect = 'bbb';
addThis();

status = this.inSection(3);
myFunc();      // sets |actual|
expect = 'aaa';
addThis();

status = this.inSection(4);
this.outSideFunc();  // sets |actual|
expect = 'ccc';
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
File Name:         regress__208496__001.js
* Date:    05 June 2003
* SUMMARY: Testing |with (f)| inside the definition of |function f()|
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=208496
*
*/
test_regress__208496__001: function () {
var SECTION = "regress__208496__001.js";
var UBound = 0;
//var BUGNUMBER = 208496;
var summary = 'Testing |with (f)| inside the definition of |function f()|';
var status = '';
var statusitems = [];
var actual = '(TEST FAILURE)';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


/*
* GLOBAL SCOPE
*/
function f(par)
{
var a = par;

with(f)
{
var b = par;
actual = b;
}
}

status = this.inSection(1);
f('abc'); // this sets |actual|
expect = 'abc';
addThis();

status = this.inSection(2);
f(111 + 222); // sets |actual|
expect = 333;
addThis();


/*
* EVAL SCOPE
*/
var s = '';
s += 'function F(par)';
s += '{';
s += '  var a = par;';

s += '  with(F)';
s += '  {';
s += '    var b = par;';
s += '    actual = b;';
s += '  }';
s += '}';

s += 'status = this.inSection(3);';
s += 'F("abc");'; // sets |actual|
s += 'expect = "abc";';
s += 'addThis();';

s += 'status = this.inSection(4);';
s += 'F(111 + 222);'; // sets |actual|
s += 'expect = 333;';
s += 'addThis();';
eval(s);


/*
* FUNCTION SCOPE
*/
function g(par)
{
// Add outer variables to complicate the scope chain -
var a = '(TEST FAILURE)';
var b = '(TEST FAILURE)';
h(par);

function h(par)
{
var a = par;

with(h)
{
var b = par;
actual = b;
}
}
}

status = this.inSection(5);
g('abc'); // sets |actual|
expect = 'abc';
addThis();

status = this.inSection(6);
g(111 + 222); // sets |actual|
expect = 333;
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
File Name:         regress__208496__002.js
* Date:    05 June 2003
* SUMMARY: Testing |with (f)| inside the definition of |function f()|
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=208496
*
* In this test, we check that static function properties of
* of |f| are read correctly from within the |with(f)| block.
*
*/
test_regress__208496__002: function () {
var SECTION = "regress__208496__002.js";
var UBound = 0;
//var BUGNUMBER = 208496;
var summary = 'Testing |with (f)| inside the definition of |function f()|';
var STATIC_VALUE = 'read the static property';
var status = '';
var statusitems = [];
var actual = '(TEST FAILURE)';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


function f(par)
{
with(f)
{
actual = par;
}

return par;
}
f.par = STATIC_VALUE;


status = this.inSection(1);
f('abc'); // this sets |actual| inside |f|
expect = STATIC_VALUE;
addThis();

// test the return: should be the dynamic value
status = this.inSection(2);
actual = f('abc');
expect = 'abc';
addThis();

status = this.inSection(3);
f(111 + 222); // sets |actual| inside |f|
expect = STATIC_VALUE;
addThis();

// test the return: should be the dynamic value
status = this.inSection(4);
actual = f(111 + 222);
expect = 333;
addThis();


/*
* Add a level of indirection via |x|
*/
function g(par)
{
with(g)
{
var x = par;
actual = x;
}

return par;
}
g.par = STATIC_VALUE;


status = this.inSection(5);
g('abc'); // this sets |actual| inside |g|
expect = STATIC_VALUE;
addThis();

// test the return: should be the dynamic value
status = this.inSection(6);
actual = g('abc');
expect = 'abc';
addThis();

status = this.inSection(7);
g(111 + 222); // sets |actual| inside |g|
expect = STATIC_VALUE;
addThis();

// test the return: should be the dynamic value
status = this.inSection(8);
actual = g(111 + 222);
expect = 333;
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
File Name:         regress__220362.js
Date:    27 Sep 2003
SUMMARY: Calling a local function from global scope
See http://bugzilla.mozilla.org/show_bug.cgi?id=220362
*/
test_regress__220362: function () {
var SECTION = "regress__220362.js";
var UBound = 0;
//var BUGNUMBER = 220362;
var summary = 'Calling a local function from global scope';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


// creates a local function and calls it immediately
function a()
{
var x = 'A';
var f = function() {return x;};
return f();
}

// creates and returns a local function
function b()
{
var x = 'B';
var f = function() {return x;};
return f;
}


status = this.inSection(1);
actual = a();
expect = 'A';
addThis();

status = this.inSection(2);
var f = b();
actual = f();
expect = 'B';
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
//enterFunc('test');
//printBugNumber(BUGNUMBER);
// printStatus(summary);

for (var i=0; i<UBound; i++)
{
this.TestCase(SECTION, statusitems[i], expectedvalues[i], actualvalues[i]);
}

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__77578__001.js
Date: 2001-07-11

SUMMARY: Testing eval scope inside a function.
See http://bugzilla.mozilla.org/show_bug.cgi?id=77578
*/
test_regress__77578__001: function () {
var SECTION = "regress__77578__001.js";
var UBound = 0;
//var BUGNUMBER = 77578;
var summary = 'Testing eval scope inside a function';
var cnEquals = '=';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


// various versions of JavaScript -
var JS_VER = [100, 110, 120, 130, 140, 150];

// Note contrast with local variables i,j,k defined below -
var i = 999;
var j = 999;
var k = 999;
var n = 0;

//--------------------------------------------------
//test();
//--------------------------------------------------


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

// Run tests A,B,C on each version of JS and store results
for (n=0; n!=JS_VER.length; n++)
{
testA(JS_VER[n]);
}
for (n=0; n!=JS_VER.length; n++)
{
testB(JS_VER[n]);
}
for (n=0; n!=JS_VER.length; n++)
{
testC(JS_VER[n]);
}


// Compare actual values to expected values -
for (i=0; i<UBound; i++)
{
this.TestCase(SECTION, statusitems[i], expectedvalues[i], actualvalues[i]);
}

// exitFunc ('test');
//}


function testA(ver)
{
var version;
// Set the version of JS to test -
if (typeof version == 'function')
{
version(ver);
}

// eval the test, so it compiles AFTER version() has executed -
var sTestScript = "";

// Define a local variable i
sTestScript += "status = 'Section A of test; JS ' + ver/100;";
sTestScript += "var i=1;";
sTestScript += "actual = eval('i');";
sTestScript += "expect = 1;";
sTestScript += "captureThis('i');";

eval(sTestScript);
}


function testB(ver)
{
var version;
// Set the version of JS to test -
if (typeof version == 'function')
{
version(ver);
}

// eval the test, so it compiles AFTER version() has executed -
var sTestScript = "";

// Define a local for-loop iterator j
sTestScript += "status = 'Section B of test; JS ' + ver/100;";
sTestScript += "for(var j=1; j<2; j++)";
sTestScript += "{";
sTestScript += "  actual = eval('j');";
sTestScript += "};";
sTestScript += "expect = 1;";
sTestScript += "captureThis('j');";

eval(sTestScript);
}


function testC(ver)
{
var version;
// Set the version of JS to test -
if (typeof version == 'function')
{
version(ver);
}

// eval the test, so it compiles AFTER version() has executed -
var sTestScript = "";

// Define a local variable k in a try-catch block -
sTestScript += "status = 'Section C of test; JS ' + ver/100;";
sTestScript += "try";
sTestScript += "{";
sTestScript += "  var k=1;";
sTestScript += "  actual = eval('k');";
sTestScript += "}";
sTestScript += "catch(e)";
sTestScript += "{";
sTestScript += "};";
sTestScript += "expect = 1;";
sTestScript += "captureThis('k');";

eval(sTestScript);
}


function captureThis(varName)
{
statusitems[UBound] = status;
actualvalues[UBound] = varName + cnEquals + actual;
expectedvalues[UBound] = varName + cnEquals + expect;
UBound++;
}

},

/** @Test
File Name:         scope__002.js
Date: 2001-07-02

SUMMARY:  Testing visibility of outer function from inner function.
*/
test_scope__002: function () {
var SECTION = "scope__002.js";
var UBound = 0;
//var BUGNUMBER = '(none)';
var summary = 'Testing visibility of outer function from inner function';
var cnCousin = 'Fred';
var cnColor = 'red';
var cnMake = 'Toyota';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


// TEST 1
function Outer()
{
function inner()
{
}

status = 'Section 1 of test';
actual = inner();
expect = cnCousin;
addThis();
}


Outer();
status = 'Section 2 of test';
expect = cnCousin;
addThis();



// TEST 2
function Car(make)
{
this.make = make;
Car.prototype.paint = paint;

function paint()
{
}
}


var myCar = new Car(cnMake);
status = 'Section 3 of test';
actual = myCar.make;
expect = cnMake;
addThis();


myCar.paint();
status = 'Section 4 of test';
actual = myCar.color;
expect = cnColor;
addThis();



//--------------------------------------------------
//test();
//--------------------------------------------------



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

for (var i=0; i<UBound; i++)
{
this.TestCase(SECTION, statusitems[i], expectedvalues[i], actualvalues[i]);
}

// exitFunc ('test');
//}

},

/** @Test
* File Name:         scope__003.js
* Date: 2001-07-03
*
* SUMMARY:  Testing scope with nested functions
*
* From correspondence with Christopher Oliver <coliver@mminternet.com>:
*
* > Running this test with Rhino produces the following exception:
* >
* > uncaught JavaScript exception: undefined: Cannot find default value for
* > object. (line 3)
* >
* > This is due to a bug in org.mozilla.javascript.NativeCall which doesn't
* > implement toString or valueOf or override getDefaultValue.
* > However, even after I hacked in an implementation of getDefaultValue in
* > NativeCall, Rhino still produces a different result then SpiderMonkey:
* >
* > [object Call]
* > [object Object]
* > [object Call]
*
* Note the results should be:
*
*   [object global]
*   [object Object]
*   [object global]
*
* This is what we are checking for in this testcase -
*/
test_scope__003: function () {
var SECTION = "scope__003.js";
var UBound = 0;
//var BUGNUMBER = '(none)';
var summary = 'Testing scope with nested functions';
var statprefix = 'Section ';
var statsuffix = ' of test -';
var self = this; // capture a reference to the global object;
var cnGlobal = self.toString();
var cnObject = (new Object).toString();
var statusitems = [];
var actualvalues = [];
var expectedvalues = [];


function a()
{
function b()
{
capture(this.toString());
}

this.c = function()
{
capture(this.toString());
b();
}

b();
}


var obj = new a();  // captures actualvalues[0]
obj.c();            // captures actualvalues[1], actualvalues[2]


// The values we expect - see introduction above -
expectedvalues[0] = cnGlobal;
expectedvalues[1] = cnObject;
expectedvalues[2] = cnGlobal;



//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------



function capture(val)
{
actualvalues[UBound] = val;
statusitems[UBound] = getStatus(UBound);
UBound++;
}


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


function getStatus(i)
{
return statprefix + i + statsuffix;
}

},

/** @Test
File Name:         scope__004.js
Date: 2001-07-16

SUMMARY:  Testing visiblity of variables from within a with block.
See http://bugzilla.mozilla.org/show_bug.cgi?id=90325
*/
test_scope__004: function () {
var SECTION = "scope__004.js";
var UBound = 0;
//var BUGNUMBER = 90325;
var summary = 'Testing visiblity of variables from within a with block';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];

// (compare local definitions which follow) -
var A = 'global A';
var B = 'global B';
var C = 'global C';
var D = 'global D';

// an object with 'C' and 'D' properties -
var objTEST = new Object();
objTEST.C = C;
objTEST.D = D;


status = 'Section 1 of test';
with (new Object())
{
actual = A;
expect = 'global A';
}
addThis();


status = 'Section 2 of test';
with (Function)
{
actual = B;
expect = 'global B';
}
addThis();


status = 'Section 3 of test';
with (this)
{
actual = C;
expect = 'global C';
}
addThis();


status = 'Section 4 of test';
localA();
addThis();

status = 'Section 5 of test';
localB();
addThis();

status = 'Section 6 of test';
localC(new Object());
addThis();

status = 'Section 7 of test';
localC(new Object());
addThis();

status = 'Section 8 of test';
localC.apply(new Object());
addThis();

status = 'Section 9 of test';
localC.apply(new Object(), [objTEST]);
addThis();

status = 'Section 10 of test';
localC.apply(objTEST, [objTEST]);
addThis();

status = 'Section 11 of test';
localD(new Object());
addThis();

status = 'Section 12 of test';
localD.apply(new Object(), [objTEST]);
addThis();

status = 'Section 13 of test';
localD.apply(objTEST, [objTEST]);
addThis();



//-------------------------------------------------------------------------------------------------
//test();
//-------------------------------------------------------------------------------------------------



// contains a with(new Object()) block -
function localA()
{
var A = 'local A';

with(new Object())
{
actual = A;
expect = 'local A';
}
}


// contains a with(Number) block -
function localB()
{
var B = 'local B';

with(Number)
{
actual = B;
expect = 'local B';
}
}


// contains a with(this) block -
function localC(obj)
{
var C = 'local C';

with(this)
{
actual = C;
}

if ('C' in this)
expect = this.C;
else
expect = 'local C';
}


// contains a with(obj) block -
function localD(obj)
{
var D = 'local D';

with(obj)
{
actual = D;
}

if ('D' in obj)
expect = obj.D;
else
expect = 'local D';
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
//enterFunc ('test');
// printBugNumber(BUGNUMBER);
// printStatus (summary);

for (var i = 0; i < UBound; i++)
{
this.TestCase(SECTION, statusitems[i], expectedvalues[i], actualvalues[i]);
}

//exitFunc ('test');
//}

}

})
.endType();
