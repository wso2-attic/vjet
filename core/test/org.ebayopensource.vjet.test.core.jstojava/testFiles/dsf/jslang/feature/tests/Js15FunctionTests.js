vjo.ctype("dsf.jslang.feature.tests.Js15FunctionTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({

reportCompare : function  (expectedValue, actualValue, statusItems) {
new this.vj$.BaseTest().TestCase( ' ',  statusItems, expectedValue,  actualValue);
},


inSection : function (x) {
var SECT_PREFIX = 'Section ';
var SECT_SUFFIX = ' of test - ';
return SECT_PREFIX + x + SECT_SUFFIX;
},

test_10_1_6__01:function(){

var p = '';

//-----------------------------------------------------------------------------
var BUGNUMBER = 293782;
var summary = 'Local variables should not be enumerable properties of the function';
var actual = '';
var expect = '';

// printBugNumber(BUGNUMBER);
// printStatus (summary);

function f()
{
var x,y
}

actual = '';

for (p in f)
{
actual += p + ',';
}
expect = 'prototype,';

this.reportCompare(expect, actual, summary);

},
test_10_1_6:function(){

//-----------------------------------------------------------------------------
var BUGNUMBER = 293782;
var summary = 'Local variables can cause predefined function object properties to be undefined';
var actual = '';
var expect = '';

// printBugNumber(BUGNUMBER);
// printStatus (summary);

function f()
{
var name=1;
}

expect = 'f';
actual = f.name;

this.reportCompare(expect, actual, summary);

},
test_15_3_4_4:function(){

//-----------------------------------------------------------------------------
var BUGNUMBER = 290488;
var summary = '15.3.4.4 - Function.prototype.call() Scope';
var actual = '';
var expect = '';
var description = '';
var GLOBAL = this;

// printBugNumber(BUGNUMBER);
// printStatus (summary);

// printStatus(this.inSection(1));

function func() { return this; }

description = 'top-level function: this == GLOBAL';
expect = GLOBAL;
actual = func.call();
this.reportCompare(expect, actual, description);

// printStatus(this.inSection(2));
var it;
function getBoundMethod()
{
return it.bindMethod("boundMethod", function () { return this; });
}

// it is a js shell only construction
if (typeof it != 'undefined')
{
description = 'bound method: this == GLOBAL';
var func = getBoundMethod();
expect = GLOBAL;
actual = func.call();
this.reportCompare(expect, actual, description);
}

},
test_regress__123371:function(){

//-----------------------------------------------------------------------------
var BUGNUMBER = 123371;
var summary = 'Do not crash when newline separates function name from arglist';
var actual = 'No Crash';
var expect = 'No Crash';


// printBugNumber(BUGNUMBER);
// printStatus (summary);

// printStatus
('function call succeeded');

this.reportCompare(expect, actual, summary);

},
test_regress__178389:function(){

//-----------------------------------------------------------------------------
var BUGNUMBER = 178389;
var summary = 'Function.prototype.toSource should not override Function.prototype.toString';
var actual = '';
var expect = '';

// printBugNumber(BUGNUMBER);
// printStatus (summary);

function f()
{
var g = function (){};
}

expect = f.toString();

actual = f.toString();

this.reportCompare(expect, actual, summary);

},
test_regress__222029__001:function(){

var UBound = 0;
var BUGNUMBER = 222029;
var summary = "Make our f.caller property match IE's wrt f.apply and f.call";
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


function f()
{
}


/*
* Call |f| directly
*/
function g()
{
return f();
}


/*
* Call |f| via |f.call|
*/
function gg()
{
return f.call();
}


/*
* Call |f| via |f.apply|
*/
function ggg()
{
return f.apply(this);
}


/*
* Shadow |p| on |Function.prototype.call|, |Function.prototype.apply|.
* In Sections 2 and 3 below, we no longer expect to recover this value -
*/
Function.prototype.call.p = "goodbye";
Function.prototype.apply.p = "goodbye";



status = this.inSection(1);
actual = g();
expect = "hello";
addThis();

status = this.inSection(2);
actual = gg();
expect = "hello";
addThis();

status = this.inSection(3);
actual = ggg();
expect = "hello";
addThis();




//-----------------------------------------------------------------------------
test();
//-----------------------------------------------------------------------------



function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
}


function test()
{
//   enterFunc('test');
//   printBugNumber(BUGNUMBER);
//   printStatus(summary);

for (var i=0; i<UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//   exitFunc ('test');
}

},
test_regress__222029__002:function(){

var UBound = 0;
var BUGNUMBER = 222029;
var summary = "Make our f.caller property match IE's wrt f.apply and f.call";
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];

/*
* Try to confuse the engine by adding a |p| property to everything!
*/
var p = 'global';
var o = {p:'object'};


function f(obj)
{
}


/*
* Call |f| directly
*/
function g(obj)
{
var p = 'local';
return f(obj);
}


/*
* Call |f| via |f.call|
*/
function gg(obj)
{
var p = 'local';
return f.call(obj, obj);
}


/*
* Call |f| via |f.apply|
*/
function ggg(obj)
{
var p = 'local';
return f.apply(obj, [obj]);
}


/*
* Shadow |p| on |Function.prototype.call|, |Function.prototype.apply|.
* In Sections 2 and 3 below, we no longer expect to recover this value -
*/
Function.prototype.call.p = "goodbye";
Function.prototype.apply.p = "goodbye";



status = this.inSection(1);
actual = g(o);
expect = "hello";
addThis();

status = this.inSection(2);
actual = gg(o);
expect = "hello";
addThis();

status = this.inSection(3);
actual = ggg(o);
expect = "hello";
addThis();




//-----------------------------------------------------------------------------
test();
//-----------------------------------------------------------------------------



function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
}


function test()
{
//   enterFunc('test');
//   printBugNumber(BUGNUMBER);
//   printStatus(summary);

for (var i=0; i<UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//   exitFunc ('test');
}

},
test_regress__292215:function(){

//-----------------------------------------------------------------------------
var BUGNUMBER = 292215;
var summary = 'Set arguments';
var actual = '';
var expect = '00012';

// printBugNumber(BUGNUMBER);
// printStatus (summary);


function zeroArguments () {
arguments[1] = '0';
actual += arguments[1];
}

function oneArgument (x) {
arguments[1] = '1';
actual += arguments[1];
}

function twoArguments (x,y) {
arguments[1] = '2';
actual += arguments[1];
}

zeroArguments();
zeroArguments(1);
zeroArguments('a', 'b');
oneArgument();
twoArguments();

this.reportCompare(expect, actual, summary);

},
test_regress__338001:function(){

//-----------------------------------------------------------------------------
var BUGNUMBER = 338001;
var summary = 'integer overflow in jsfun.c:Function';
var actual = 'No Crash';
var expect = /No Crash|InternalError: allocation size overflow|InternalError: script stack space quota is exhausted/;

// printBugNumber(BUGNUMBER);
// printStatus (summary);

//expectExitCode(0);
//expectExitCode(5);

var fe="f";

try
{
for (i=0; i<25; i++)
fe += fe;

var fu=new Function(
fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe,
fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe,
fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe,
fe, fe, fe, fe, fe, fe, fe, fe, fe, fe,
"done"
);
}
catch(ex)
{
// handle changed 1.9 branch behavior. see bug 422348
actual = ex + '';
}

// print('Done: ' + actual);

this.reportCompare(expect, actual, summary);

},
test_regress__338121__01:function(){

//-----------------------------------------------------------------------------
var BUGNUMBER = 338121;
var summary = 'Issues with JS_ARENA_ALLOCATE_CAST';
var actual = 'No Crash';
var expect = 'No Crash';

// printBugNumber(BUGNUMBER);
// printStatus (summary);

try
{
var fe="v";

for (i=0; i<25; i++)
fe += fe;

var fu=new Function(
fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe,
fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe,
"done"
);

//   print('Done');
}
catch(ex)
{
expect = 'InternalError: script stack space quota is exhausted';
actual = ex + '';
//   print(actual);
}

this.reportCompare(expect, actual, summary);

},
test_regress__338121__02:function(){

//-----------------------------------------------------------------------------
var BUGNUMBER = 338121;
var summary = 'Issues with JS_ARENA_ALLOCATE_CAST';
var actual = 'No Crash';
var expect = 'No Crash';

// printBugNumber(BUGNUMBER);
// printStatus (summary);

try
{
var fe="vv";

for (i=0; i<24; i++)
fe += fe;

var fu=new Function(
fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe,
fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe,
"done"
);

//alert("fu="+fu);
//print("fu="+fu);
var fuout = 'fu=' + fu;

//   print('Done');
}
catch(ex)
{
expect = 'InternalError: script stack space quota is exhausted';
actual = ex + '';
//   print(actual);
}

this.reportCompare(expect, actual, summary);

},
test_regress__338121__03:function(){

//-----------------------------------------------------------------------------
var BUGNUMBER = 338121;
var summary = 'Issues with JS_ARENA_ALLOCATE_CAST';
var actual = 'No Crash';
var expect = 'No Crash';

// printBugNumber(BUGNUMBER);
// printStatus (summary);

try
{
var fe="vv";

for (i=0; i<24; i++)
fe += fe;

var fu=new Function(
fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe,
fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe,
fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe, fe,
fe, fe, fe,
"done"
);

//alert("fu="+fu);
//print("fu="+fu);
var fuout = 'fu=' + fu;
}
catch(ex)
{
expect = 'InternalError: script stack space quota is exhausted';
actual = ex + '';
//   print('Caught ' + ex);
}
// print('Done');

this.reportCompare(expect, actual, summary);

},
test_regress__344052:function(){

//-----------------------------------------------------------------------------
var BUGNUMBER = 344052;
var summary = 'Function prototype - simple shared property';
var actual = '';
var expect = 'true';

function y(){};

//-----------------------------------------------------------------------------
test();
//-----------------------------------------------------------------------------

function test()
{
//   enterFunc ('test');
//   printBugNumber(BUGNUMBER);
//   printStatus (summary);

this.reportCompare(expect, actual, summary);

//   exitFunc ('test');
}

},
test_regress__364023:function(){

//-----------------------------------------------------------------------------
var BUGNUMBER = 364023;
var summary = 'Do not crash in JS_GetPrivate';
var actual = 'No Crash';
var expect = 'No Crash';


//-----------------------------------------------------------------------------
test();
//-----------------------------------------------------------------------------

function test()
{
//   enterFunc ('test');
//   printBugNumber(BUGNUMBER);
//   printStatus (summary);

function exploit() {
var code = "";
for(var i = 0; i < 0x10000; i++) {
if(i == 125) {
code += "void 0x10000050505050;\n";
} else {
code += "void " + (0x10000000000000 + i) + ";\n";
}
}
code += "function foo() {}\n";
eval(code);
}
exploit();

this.reportCompare(expect, actual, summary);

//   exitFunc ('test');
}

}}).endType()
