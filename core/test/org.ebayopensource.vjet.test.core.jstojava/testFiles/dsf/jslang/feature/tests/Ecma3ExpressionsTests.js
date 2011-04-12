vjo.ctype("dsf.jslang.feature.tests.Ecma3ExpressionsTests")
.inherits("dsf.jslang.feature.tests.BaseTestEcma3")
.protos({

constructs: function() {
this.base();
},

//-----------------------------------------------------------------------------
test_11_10__01: function() {
//var BUGNUMBER = 396969;
var summary = '11.10 - & should evaluate operands in order';
var actual = '';
var expect = '';

//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

expect = 'o.valueOf, p.valueOf';
var actualval;
var expectval = 2;

var o = {
valueOf: (function (){ actual += 'o.valueOf'; return this.value}),
value:42
};

var p = {
valueOf: (function (){ actual += ', p.valueOf'; return this.value}),
value:2
};

actualval = (o & p);

this.reportCompare(expectval, actualval, summary + ': value');
this.reportCompare(expect, actual, summary + ': order');

//exitFunc ('test');
//}
},

//-----------------------------------------------------------------------------
test_11_10__02: function() {
//var BUGNUMBER = 396969;
var summary = '11.10 - ^ should evaluate operands in order';
var actual = '';
var expect = '';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

expect = 'o.valueOf, p.valueOf';
var actualval;
var expectval = 40;

var o = {
valueOf: (function (){ actual += 'o.valueOf'; return this.value}),
value:42
};

var p = {
valueOf: (function (){ actual += ', p.valueOf'; return this.value}),
value:2
};

actualval = (o ^ p);

this.reportCompare(expectval, actualval, summary + ': value');
this.reportCompare(expect, actual, summary + ': order');

//exitFunc ('test');
//}
},

//-----------------------------------------------------------------------------
test_11_10__03: function() {
//var BUGNUMBER = 396969;
var summary = '11.10 - | should evaluate operands in order';
var actual = '';
var expect = '';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

expect = 'o.valueOf, p.valueOf';
var actualval;
var expectval = 42;

var o = {
valueOf: (function (){ actual += 'o.valueOf'; return this.value}),
value:42
};

var p = {
valueOf: (function (){ actual += ', p.valueOf'; return this.value}),
value:2
};

actualval = (o | p);

this.reportCompare(expectval, actualval, summary + ': value');
this.reportCompare(expect, actual, summary + ': order');

//exitFunc ('test');
//}
},

/*
*
* Date:    14 Mar 2003
* SUMMARY: Testing left-associativity of the + operator
*
* See ECMA-262 Ed.3, Section 11.6.1, "The Addition operator"
* See http://bugzilla.mozilla.org/show_bug.cgi?id=196290
*
* The upshot: |a + b + c| should always equal |(a + b) + c|
*
*/
//-----------------------------------------------------------------------------
test_11_6_1__1: function() {
//var gTestfile = '11.6.1-1.js';
var UBound = 0;
//var BUGNUMBER = 196290;
var summary = 'Testing left-associativity of the + operator';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


status = inSection(1);
actual = 1 + 1 + 'px';
expect = '2px';
addThis();

status = inSection(2);
actual = 'px' + 1 + 1;
expect = 'px11';
addThis();

status = inSection(3);
actual = 1 + 1 + 1 + 'px';
expect = '3px';
addThis();

status = inSection(4);
actual = 1 + 1 + 'a' + 1 + 1 + 'b';
expect = '2a11b';
addThis();

/*
* The next sections test the + operator via eval()
*/
status = inSection(5);
actual = sumThese(1, 1, 'a');
expect = '2a11b';
addThis();

status = inSection(6);
actual = sumThese(new Number(1), new Number(1), 'a');
expect = '2a';
addThis();

status = inSection(7);
actual = sumThese('a', new Number(1), new Number(1));
expect = 'a11';
addThis();



//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------



function inSection(x) {
var SECT_PREFIX = 'Section ';
var SECT_SUFFIX = ' of test - ';
return SECT_PREFIX + x + SECT_SUFFIX;
}

function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
}

/*
* Applies the + operator to the provided arguments via eval().
*
* Form an eval string of the form 'arg1 + arg2 + arg3', but
* remember to add double-quotes inside the eval string around
* any argument that is of string type. For example, suppose the
* arguments were 11, 'a', 22. Then the eval string should be
*
*              arg1 + quoteThis(arg2) + arg3
*
* If we didn't put double-quotes around the string argument,
* we'd get this for an eval string:
*
*                     '11 + a + 22'
*
* If we eval() this, we get 'ReferenceError: a is not defined'.
* With proper quoting, we get eval('11 + "a" + 22') as desired.
*/
function sumThese(arg1,arg2,arg3)
{
var sEval = '';
var arg;
var i;

var L = arguments.length;
for (i=0; i<L; i++)
{
arg = arguments[i];
if (typeof arg === 'string')
arg = quoteThis(arg);

if (i < L-1)
sEval += arg + ' + ';
else
sEval += arg;
}

return eval(sEval);
}


function quoteThis(x)
{
return '"' + x + '"';
}


//function test()
//{
//enterFunc('test');
//printBugNumber(BUGNUMBER);
//printStatus(summary);

for (var i=0; i<UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//exitFunc ('test');
//}

},

//-----------------------------------------------------------------------------
test_11_7_1__01: function() {
//var BUGNUMBER = 396969;
var summary = '11.7.1 - << should evaluate operands in order';
var actual = '';
var expect = '';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

expect = 'o.valueOf, p.valueOf';
var actualval;
var expectval = 168;

var o = {
valueOf: (function (){ actual += 'o.valueOf'; return this.value}),
value:42
};

var p = {
valueOf: (function (){ actual += ', p.valueOf'; return this.value}),
value:2
};

actualval = (o << p);

this.reportCompare(expectval, actualval, summary + ': value');
this.reportCompare(expect, actual, summary + ': order');

//exitFunc ('test');
//}

},

//-----------------------------------------------------------------------------
test_11_7_2__01: function() {
//var BUGNUMBER = 396969;
var summary = '11.7.2 - >> should evaluate operands in order';
var actual = '';
var expect = '';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

expect = 'o.valueOf, p.valueOf';
var actualval;
var expectval = 10;

var o = {
valueOf: (function (){ actual += 'o.valueOf'; return this.value}),
value:42
};

var p = {
valueOf: (function (){ actual += ', p.valueOf'; return this.value}),
value:2
};

actualval = (o >> p);

this.reportCompare(expectval, actualval, summary + ': value');
this.reportCompare(expect, actual, summary + ': order');

//exitFunc ('test');
//}

},

//-----------------------------------------------------------------------------
test_11_7_3__01: function() {
//var BUGNUMBER = 396969;
var summary = '11.7.3 - >>> should evaluate operands in order';
var actual = '';
var expect = '';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

expect = 'o.valueOf, p.valueOf';
var actualval;
var expectval = 10;

var o = {
valueOf: (function (){ actual += 'o.valueOf'; return this.value}),
value:42
};

var p = {
valueOf: (function (){ actual += ', p.valueOf'; return this.value}),
value:2
};

actualval = (o >>> p);

this.reportCompare(expectval, actualval, summary + ': value');
this.reportCompare(expect, actual, summary + ': order');

//exitFunc ('test');
//}

},

/*
*
* Date:    20 Feb 2002
* SUMMARY: Testing the comparison |undefined === null|
* See http://bugzilla.mozilla.org/show_bug.cgi?id=126722
*
*/
//-----------------------------------------------------------------------------
test_11_9_6__1: function() {
//var gTestfile = '11.9.6-1.js';
var UBound = 0;
//var BUGNUMBER = 126722;
var summary = 'Testing the comparison |undefined === null|';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


status = inSection(1);
if (undefined === null)
actual = true;
else
actual = false;
expect = false;
addThis();



status = inSection(2);
switch(true)
{
case (undefined === null) :
actual = true;
break;

default:
actual = false;
}
expect = false;
addThis();



status = inSection(3);
function f3(x)
{
var res = false;

switch(true)
{
case (x === null) :
res = true;
break;

default:
	;//do nothing
}

return res;
}

actual = f3(undefined);
expect = false;
addThis();



status = inSection(4);
function f4(arr)
{
var elt = '';
var res = false;

for (i=0; i<arr.length; i++)
{
elt = arr[i];

switch(true)
{
case (elt === null) :
res = true;
break;

default:
	;// do nothing
}
}

return res;
}

var arr = Array('a', undefined);
actual = f4(arr);
expect = false;
addThis();



status = inSection(5);
function f5(arr)
{
var len = arr.length;

for(var i=0; (arr[i]===undefined) && (i<len); i++)
; //do nothing

return i;
}

/*
* An array of 5 undefined elements. Note:
*
* The return value of eval(a STATEMENT) is undefined.
* A non-existent PROPERTY is undefined, not a ReferenceError.
* No undefined element exists AFTER trailing comma at end.
*
*/
var arrUndef = [ , undefined, eval('var x = 0'), this.NOT_A_PROPERTY, , ];
actual = f5(arrUndef);
expect = 5;
addThis();



status = inSection(6);
function f6(arr)
{
var len = arr.length;

for(var i=0; (arr[i]===null) && (i<len); i++)
; //do nothing

return i;
}

/*
* Use same array as above. This time we're comparing to |null|, so we expect 0
*/
actual = f6(arrUndef);
expect = 0;
addThis();




//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------



function inSection(x) {
var SECT_PREFIX = 'Section ';
var SECT_SUFFIX = ' of test - ';
return SECT_PREFIX + x + SECT_SUFFIX;
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
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//exitFunc ('test');
//}

}

})
.endType();




