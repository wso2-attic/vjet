vjo.ctype("dsf.jslang.feature.tests.Ecma3ExecutionContextsTests")
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

/**
Date:    11 Feb 2002
File Name:          10_1_3__1.js
SUMMARY: Testing functions having duplicate formal parameter names

Note: given function f(x,x,x,x) {return x;}; f(1,2,3,4) should return 4.
See ECMA-262 3rd Edition Final Section 10.1.3: Variable Instantiation

Also see http://bugzilla.mozilla.org/show_bug.cgi?id=124900
*/
test_10_1_3__1: function() {
var SECTION = "10_1_3__1";
var SECT_PREFIX = 'Section ';
var SECT_SUFFIX = ' of test - ';
var UBound = 0;
//var BUGNUMBER = 124900;
var summary = 'Testing functions having duplicate formal parameter names';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];

function f1(x,x1)
{
return x;
}
status = this.inSection(1);
actual = f1(1,2);
expect = 2;
addThis();

function f2(x,x1,x2)
{
return x*x*x;
}
status = this.inSection(2);
actual = f2(1,2,3);
expect = 27;
addThis();

function f3(x,x1,x2,x3)
{
return 'a' + x + 'b' + x + 'c' + x ;
}
status = this.inSection(3);
actual = f3(1,2,3,4);
expect = 'a4b4c4';
addThis();

/*
* If the value of the last duplicate parameter is not provided by
* the function caller, the value of this parameter is undefined
*/
function f4(x,a,b,x1,z)
{
return x;
}
status = this.inSection(4);
actual = f4(1,2,3,4,5);
expect = undefined;
addThis();

/*
* f.toString() should preserve any duplicate formal parameter names that exist
*/
function f5(x1,x2,x3,x4)
{
}
status = this.inSection(5);
actual = f5.toString().match(/\((.*)\)/)[1];
actual = actual.replace(/\s/g, ''); // for definiteness, remove any white space
expect = 'x,x,x,x';
addThis();

function f6(x1,x2,x3,x4)
{
var ret = [];

for (var i=0; i<arguments.length; i++)
ret.push(arguments[i]);

return ret.toString();
}
status = this.inSection(6);
actual = f6(1,2,3,4);
expect = '1,2,3,4';
addThis();

/*
* This variation (assigning to x inside f) is from nboyd@atg.com
* See http://bugzilla.mozilla.org/show_bug.cgi?id=124900
*/
function f7(x,x2,x3,x4)
{
x = 999;
var ret = [];

for (var i=0; i<arguments.length; i++)
ret.push(arguments[i]);

return ret.toString();
}
status = this.inSection(7);
actual = f7(1,2,3,4);
expect = '1,2,3,999';
addThis();

/*
* Same as above, but with |var| keyword added -
*/
function f8(x,x1,x2,x3)
{
var x = 999;
var ret = [];

for (var i=0; i<arguments.length; i++)
ret.push(arguments[i]);

return ret.toString();
}
status = this.inSection(8);
actual = f8(1,2,3,4);
expect = '1,2,3,999';
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
this.TestCase(SECTION, statusitems[i],expectedvalues[i], actualvalues[i]);
}

//exitFunc ('test');
//}


},

/**
File Name:          10_1_3__2.js
SUMMARY: eval of function declaration should change existing variable
*/
test_10_1_3__2: function() {
var SECTION = "10_1_3__2";
//var BUGNUMBER = 395907;
var summary = 'eval of function declaration should change existing variable';
var actual = '';
var expect = '';

//test();

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

expect = 'typeof x: number, typeof x: function, x(): true';
var x = "string";
function f () {
var x = 0;
actual += 'typeof x: ' + (typeof x) + ', ';
eval('function x() { return true; }');
actual += 'typeof x: ' + (typeof x) + ', ';
actual += 'x(): ' + x();
}
f();

this.TestCase(SECTION, summary, expect, actual);

//exitFunc ('test');
//}
},

/**
File Name:          10_1_3.js
ECMA Section: 10.1.3: Variable Instantiation
FunctionDeclarations are processed before VariableDeclarations, and
VariableDeclarations don't replace existing values with undefined
*/
test_10_1_3: function() {
var SECTION = "10_1_3";

//test();

function f()
{
var x;

return typeof x;

function x()
{
return 7;
}
}

//function test()
//{
//enterFunc ("test");

//printStatus ("ECMA Section: 10.1.3: Variable Instantiation.");
//printBugNumber (17290);

this.TestCase(SECTION, "Declaration precedence test", "function", f());

//exitFunc("test");
//}
},

/**
File Name:          10_1_4__1.js
ECMA Section: 10.1.4.1 Entering An Execution Context
ECMA says:
* Global Code, Function Code
Variable instantiation is performed using the global object as the
variable object and using property attributes { DontDelete }.

* Eval Code
Variable instantiation is performed using the calling context's
variable object and using empty property attributes.
*/
test_10_1_4__1: function() {
var SECTION = "10_1_4__1";
var summary = '10.1.4.1 Entering An Execution Context';
var actual = '';
var expect = '';

//test();

//function test()
//{
//enterFunc ("test");
//printBugNumber(BUGNUMBER);
//printStatus (summary);

var y;
var x;
eval("var x = 1");

if (delete y)
this.TestCase(SECTION, "Expected *NOT* to be able to delete y", 'PASS', 'FAIL');

if (typeof x == "undefined")
this.TestCase(SECTION, "x did not remain defined after eval()", 'PASS', 'FAIL');
else if (x != 1)
this.TestCase(SECTION, "x did not retain it's value after eval()", 'PASS', 'FAIL');

if (!delete x)
this.TestCase(SECTION, "Expected to be able to delete x", 'PASS', 'FAIL');


this.TestCase(SECTION, '10.1.4.1 Entering An Execution Context', 'PASS', 'PASS');

//exitFunc("test");
//}
},

/**
File Name:          10_6_1__01.js
Summary: activation object never delegates to Object.prototype
*/
test_10_6_1__01: function() {
var SECTION = "10_6_1__01";
var SECT_PREFIX = 'Section ';
var SECT_SUFFIX = ' of test - ';
//var BUGNUMBER = 290774;
var summary = 'activation object never delegates to Object.prototype';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var toStringResult;
var evalResult;
var watchResult;
var parseIntResult;

var eval = 'fooEval';
var watch = undefined;
var parseInt = 'fooParseInt';

function toString()
{
return 'fooString';
}

function normal()
{
toStringResult = toString;
evalResult = eval;
watchResult = watch;
parseIntResult = parseInt;
}

function outerinnervar()
{
toStringResult = toString;
evalResult = eval;
watchResult = watch;
parseIntResult = parseInt;
function inner()
{
// addition of any statement
// which accesses a variable
// from the outer scope causes the bug
//printStatus(toString);
}
}

expect = true;

//printStatus('normal');
//printStatus('======');
normal();

//printStatus('toStringResult ' + toStringResult);
//printStatus('toString ' + toString);
actual = ((toStringResult + '') == (toString + ''));
this.TestCase(SECTION, this.inSection(1), expect, actual);

//printStatus('evalResult ' + evalResult);
//printStatus('eval ' + eval);
actual = ((evalResult + '') == (eval + ''));
this.TestCase(SECTION, this.inSection(2), expect, actual);

//printStatus('watchResult ' + watchResult);
//printStatus('watch ' + watch);
actual = ((watchResult + '') == (watch + ''));
this.TestCase(SECTION, this.inSection(3), expect, actual);

//printStatus('parseIntResult ' + parseIntResult);
//printStatus('parseInt ' + parseInt);
actual = ((parseIntResult + '') == (parseInt + ''));
this.TestCase(SECTION, this.inSection(4), expect, actual);

//printStatus('outerinner');
//printStatus('==========');

outerinnervar();

//printStatus('toStringResult ' + toStringResult);
//printStatus('toString ' + toString);
actual = ((toStringResult + '') == (toString + ''));
this.TestCase(SECTION, this.inSection(5), expect, actual);

//printStatus('evalResult ' + evalResult);
//printStatus('eval ' + eval);
actual = ((evalResult + '') == (eval + ''));
this.TestCase(SECTION, this.inSection(6), expect, actual);

//printStatus('watchResult ' + watchResult);
//printStatus('watch ' + watch);
actual = ((watchResult + '') == (watch + ''));
this.TestCase(SECTION, this.inSection(7), expect, actual);

//printStatus('parseIntResult ' + parseIntResult);
//printStatus('parseInt ' + parseInt);
actual = ((parseIntResult + '') == (parseInt + ''));
this.TestCase(SECTION, this.inSection(8), expect, actual);

},

/**
File Name:          regress__23346.js
*/
test_regress__23346: function() {
var SECTION = "regress__23346";

var CALL_CALLED = "PASSED";

//test();

function f(x)
{
if (x)
return call();
return "FAILED!";
}

function call()
{
return CALL_CALLED;
}

//function test()
//{
//enterFunc ("test");
//printStatus ("ECMA Section: 10.1.3: Variable Instantiation.");
//printBugNumber (23346);

this.TestCase(SECTION, "Unqualified reference should not see Function.prototype", "PASSED", f(true));

//exitFunc("test");
//}
},

/**
File Name:          regress_448595__01.js
Summary: scope chain var declaration with initialiser in |with| clauses
*/
test_regress_448595__01: function() {
var SECTION = "regress_448595__01";

//var BUGNUMBER = 448595;
var summary = 'scope chain var declaration with initialiser in |with| clauses';
var actual = '';
var expect = '';

//test();

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

var f;

expect = 'bar';

f = function(){
var e = "bar";
with({e:"foo"}) {
var e = "wibble";
};
actual = e;
}

f();

this.TestCase(SECTION, summary + ': with', expect, actual);

f = function(){
var e = "bar";
try
{
throw {e:"foo"};
}
catch(e) {
var e = "wibble";
};

actual = e;
}

f();

this.TestCase(SECTION, summary + ': catch', expect, actual);

//exitFunc ('test');
//}
}




})
.endType();

