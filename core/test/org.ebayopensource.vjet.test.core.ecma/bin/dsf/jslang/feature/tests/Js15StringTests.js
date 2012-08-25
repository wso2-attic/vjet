vjo.ctype("dsf.jslang.feature.tests.Js15StringTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({

constructs:function(){
this.base();
},

inSection:function(x) {
var SECT_PREFIX = 'Section ';
var SECT_SUFFIX = ' of test - ';
return SECT_PREFIX + x + SECT_SUFFIX;
},

/*
Calculate the "order" of a set of data points {X: [], Y: []}
by computing successive "derivatives" of the data until
the data is exhausted or the derivative is linear.
*/
BigO:function (data)
{
var order = 0;
var origLength = data.X.length;

while (data.X.length > 2)
{
var lr = new LinearRegression(data);
if (lr.b > 1e-6)
{
// only increase the order if the slope
// is "great" enough
order++;
}

if (lr.r > 0.98 || lr.Syx < 1 || lr.b < 1e-6)
{
// terminate if close to a line lr.r
// small error lr.Syx
// small slope lr.b
break;
}
data = dataDeriv(data);
}

if (2 == origLength - order)
{
order = Number.POSITIVE_INFINITY;
}
return order;

function LinearRegression(data)
{
/*
y = a + bx
for data points (Xi, Yi); 0 <= i < n

b = (n*SUM(XiYi) - SUM(Xi)*SUM(Yi))/(n*SUM(Xi*Xi) - SUM(Xi)*SUM(Xi))
a = (SUM(Yi) - b*SUM(Xi))/n
*/
var i;

if (data.X.length != data.Y.length)
{
throw 'LinearRegression: data point length mismatch';
}
if (data.X.length < 3)
{
throw 'LinearRegression: data point length < 2';
}
var n = data.X.length;
var X = data.X;
var Y = data.Y;

this.Xavg = 0;
this.Yavg = 0;

var SUM_X  = 0;
var SUM_XY = 0;
var SUM_XX = 0;
var SUM_Y  = 0;
var SUM_YY = 0;

for (i = 0; i < n; i++)
{
SUM_X  += X[i];
SUM_XY += X[i]*Y[i];
SUM_XX += X[i]*X[i];
SUM_Y  += Y[i];
SUM_YY += Y[i]*Y[i];
}

this.b = (n * SUM_XY - SUM_X * SUM_Y)/(n * SUM_XX - SUM_X * SUM_X);
this.a = (SUM_Y - this.b * SUM_X)/n;

this.Xavg = SUM_X/n;
this.Yavg = SUM_Y/n;

var SUM_Ydiff2 = 0;
var SUM_Xdiff2 = 0;
var SUM_XdiffYdiff = 0;

for (i = 0; i < n; i++)
{
var Ydiff = Y[i] - this.Yavg;
var Xdiff = X[i] - this.Xavg;

SUM_Ydiff2 += Ydiff * Ydiff;
SUM_Xdiff2 += Xdiff * Xdiff;
SUM_XdiffYdiff += Xdiff * Ydiff;
}

var Syx2 = (SUM_Ydiff2 - Math.pow(SUM_XdiffYdiff/SUM_Xdiff2, 2))/(n - 2);
var r2   = Math.pow((n*SUM_XY - SUM_X * SUM_Y), 2) /
((n*SUM_XX - SUM_X*SUM_X)*(n*SUM_YY-SUM_Y*SUM_Y));

this.Syx = Math.sqrt(Syx2);
this.r = Math.sqrt(r2);

}

function dataDeriv(data)
{
if (data.X.length != data.Y.length)
{
throw 'length mismatch';
}
var length = data.X.length;

if (length < 2)
{
throw 'length ' + length + ' must be >= 2';
}
var X = data.X;
var Y = data.Y;

var deriv = {X: [], Y: [] };

for (var i = 0; i < length - 1; i++)
{
deriv.X[i] = (X[i] + X[i+1])/2;
deriv.Y[i] = (Y[i+1] - Y[i])/(X[i+1] - X[i]);
}
return deriv;
}

return 0;
},


/** @Test
File Name:         regress__107771.js
Date: 31 October 2001

SUMMARY: Regression test for bug 107771
See http://bugzilla.mozilla.org/show_bug.cgi?id=107771

The bug: Section 1 passed, but Sections 2-5 all failed with |actual| == 12
*/
test_regress__107771: function () {
var SECTION = "regress__107771.js";
var UBound = 0;
//var BUGNUMBER = 107771;
var summary = "Regression test for bug 107771";
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];
var str = '';
var k = -9999;


status = this.inSection(1);
str = "AAA//BBB/CCC/";
k = str.lastIndexOf('/');
actual = k;
expect = 12;

status = this.inSection(2);
str = str.substring(0, k);
k = str.lastIndexOf('/');
actual = k;
expect = 8;
addThis();

status = this.inSection(3);
str = str.substring(0, k);
k = str.lastIndexOf('/');
actual = k;
expect = 4;
addThis();

status = this.inSection(4);
str = str.substring(0, k);
k = str.lastIndexOf('/');
actual = k;
expect = 3;
addThis();

status = this.inSection(5);
str = str.substring(0, k);
k = str.lastIndexOf('/');
actual = k;
expect = -1;
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

for (var i=0; i<UBound; i++)
{
this.TestCase(SECTION, statusitems[i], expectedvalues[i], actualvalues[i]);
}

//exitFunc ('test');
//}
},

/** @Test
File Name:         regress__112626.js
Summary:         Do not crash String.split(regexp) when regexp contains parens
*/
test_regress__112626: function () {
var SECTION = "regress__112626.js";
//var BUGNUMBER = 112626;
var summary = 'Do not crash String.split(regexp) when regexp contains parens';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var _cs='2001-01-01';
var curTime = _cs.split(/([- :])/);

this.TestCase(SECTION, summary, expect, actual);
},

/** @Test
File Name:         regress__157334__01.js
Summary:        String concat should not be O(N**2)
*/
test_regress__157334__01: function () {
var SECTION = "regress__157334__01.js";
//var BUGNUMBER = 56940;
var summary = 'String concat should not be O(N**2)';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var data = {X:[], Y:[]};
for (var size = 1000; size < 10000; size += 1000)
{
data.X.push(size);
data.Y.push(concat(size));
gc();
}

var order = this.BigO(data);

var msg = '';
for (var p = 0; p < data.X.length; p++)
{
msg += '(' + data.X[p] + ', ' + data.Y[p] + '); ';
}
//printStatus(msg);
//printStatus('Order: ' + order);
this.TestCase(SECTION, 'this.BigO ' + order + ' < 2',true, order < 2);

function concat(size)
{
var c='qwertyuiop';
var x='';
var y='';
var loop;

for (loop = 0; loop < size; loop++)
{
x += c;
}

y = x + 'y';
x = x + 'x';

var start = new Date();
for (loop = 0; loop < 1000; loop++)
{
var z = x + y + loop.toString();
}
var stop = new Date();
return stop - start;
}

function gc (){
for (var i = 0; i != 4e6; ++i)
{
var tmp = i + 0.1;
}
}
},

/** @Test
* File Name:         regress__179068.js
* Date:    09 November 2002
* SUMMARY: Test that interpreter can handle string literals exceeding 64K
* See http://bugzilla.mozilla.org/show_bug.cgi?id=179068
*
* Test that the interpreter can handle string literals exceeding 64K limit.
* For that the script passes to eval() "str ='LONG_STRING_LITERAL';" where
* LONG_STRING_LITERAL is a string with 200K chars.
*
*   Igor Bukanov explains the technique used below:
*
* > Philip Schwartau wrote:
* >...
* > Here is the heart of the testcase:
* >
* >   // Generate 200K long string
* >   var long_str = duplicate(LONG_STR_SEED, N);
* >   var str = "";
* >   eval("str='".concat(long_str, "';"));
* >   var test_is_ok = (str.length == LONG_STR_SEED.length * N);
* >
* >
* > The testcase creates two identical strings, |long_str| and |str|. It
* > uses eval() simply to assign the value of |long_str| to |str|. Why is
* > it necessary to have the variable |str|, then? Why not just create
* > |long_str| and test it? Wouldn't this be enough:
* >
* >   // Generate 200K long string
* >   var long_str = duplicate(LONG_STR_SEED, N);
* >   var test_is_ok = (long_str.length == LONG_STR_SEED.length * N);
* >
* > Or do we specifically need to test eval() to exercise the interpreter?
*
* The reason for eval is to test string literals like in 'a string literal
* with 100 000 characters...', Rhino deals fine with strings generated at
* run time where lengths > 64K. Without eval it would be necessary to have
* a test file excedding 64K which is not that polite for CVS and then a
* special treatment for the compiled mode in Rhino should be added.
*
*
* >
* > If so, is it important to use the concat() method in the assignment, as
* > you have done: |eval("str='".concat(long_str, "';"))|, or can we simply
* > do |eval("str = long_str;")| ?
*
* The concat is a replacement for eval("str='"+long_str+"';"), but as
* long_str is huge, this leads to constructing first a new string via
* "str='"+long_str and then another one via ("str='"+long_str) + "';"
* which takes time under JDK 1.1 on a something like StrongArm 200MHz.
* Calling concat makes less copies, that is why it is used in the
* duplicate function and this is faster then doing recursion like in the
* test case to test that 64K different string literals can be handled.
*
*/
test_regress__179068: function () {
var SECTION = "regress__179068.js";
var UBound = 0;
//var BUGNUMBER = 179068;
var summary = 'Test that interpreter can handle string literals exceeding 64K';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];
var LONG_STR_SEED = "0123456789";
var N = 20 * 1024;
var str = "";


// Generate 200K long string and assign it to |str| via eval()
var long_str = duplicate(LONG_STR_SEED, N);
eval("str='".concat(long_str, "';"));

status = this.inSection(1);
actual = str.length == LONG_STR_SEED.length * N
expect = true;
addThis();



//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------



function duplicate(str, count)
{
var tmp = new Array(count);

while (count != 0)
tmp[--count] = str;

return String.prototype.concat.apply("", tmp);
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
// printStatus(summary);

for (var i=0; i<UBound; i++)
{
this.TestCase(SECTION, statusitems[i], expectedvalues[i], actualvalues[i]);
}

// exitFunc ('test');
//}

},

/** @Test
File Name:         regress__314890.js
Summary:         String == should short circuit for object identify
*/
test_regress__314890: function () {
var SECTION = "regress__314890.js";
//var BUGNUMBER = 314890;
var summary = 'String == should short circuit for object identify';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var s1 = 'x';
var s2;
var count = 0;

var data = {X:[], Y:[]};
for (var power = 0; power < 20; power++)
{
s1 = s1 + s1;
s2 = s1;

data.X.push(s1.length);
var start = new Date();
for (var count = 0; count < 1000; count++)
{
if (s1 == s2)
{
++count;
}
}
var stop = new Date();
data.Y.push(stop - start);
gc();
}

var order = this.BigO(data);

var msg = '';
for (var p = 0; p < data.X.length; p++)
{
msg += '(' + data.X[p] + ', ' + data.Y[p] + '); ';
}
//printStatus(msg);
//printStatus('Order: ' + order);
this.TestCase(SECTION,  'this.BigO ' + order + ' < 1', true, order < 1);

this.TestCase(SECTION, summary, expect, actual);

function gc (){
for (var i = 0; i != 4e6; ++i)
{
var tmp = i + 0.1;
}
}

},

/** @Test
File Name:         regress__322772.js
Summary:         String == should short circuit for length
*/
test_regress__322772: function () {
var SECTION = "regress__322772.js";
//var BUGNUMBER = 322772;
var summary = 'String == should short circuit for length';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var s1 = 'xx';
var s2 = '';
var count = 0;

var data = {X:[], Y:[]};
for (var power = 0; power < 20; power++)
{
s1 = s1 + s1;
s2 = s1 + 'y';

data.X.push(s1.length);
var start = new Date();
for (var count = 0; count < 1000; count++)
{
if (s1 == s2)
{
++count;
}
}
var stop = new Date();
data.Y.push(stop - start);
gc();
}

var order = this.BigO(data);

var msg = '';
for (var p = 0; p < data.X.length; p++)
{
msg += '(' + data.X[p] + ', ' + data.Y[p] + '); ';
}
//printStatus(msg);
//printStatus('Order: ' + order);
this.TestCase(SECTION,  'this.BigO ' + order + ' < 1', true, order < 1);

this.TestCase(SECTION, summary, expect, actual);

function gc (){
for (var i = 0; i != 4e6; ++i)
{
var tmp = i + 0.1;
}
}
},

/** @Test
File Name:         regress__56940__01.js
Summary:         String concat should not be O(N**2)
*/
test_regress__56940__01: function () {
var SECTION = "regress__56940__01.js";
//var BUGNUMBER = 56940;
var summary = 'String concat should not be O(N**2)';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var data = {X:[], Y:[]};
for (var size = 1000; size <= 10000; size += 1000)
{
data.X.push(size);
data.Y.push(concat(size));
gc();
}

var order = this.BigO(data);

var msg = '';
for (var p = 0; p < data.X.length; p++)
{
msg += '(' + data.X[p] + ', ' + data.Y[p] + '); ';
}
//printStatus(msg);
//printStatus('Order: ' + order);
this.TestCase(SECTION,  'this.BigO ' + order + ' < 2', true, order < 2);

function concat(size)
{
var x = '';
var y = 'Mozilla Mozilla Mozilla Mozilla ';
var z = 'goober ';
var start = new Date();

for (var loop = 0; loop < size; loop++)
{
x = x + y + z + y + z + y + z;
}
var stop = new Date();
return stop - start;
}

function gc (){
for (var i = 0; i != 4e6; ++i)
{
var tmp = i + 0.1;
}
}

},

/** @Test
File Name:         regress__56940__02.js
Summary:         String concat should not be O(N**2)
*/
test_regress__56940__02: function () {
var SECTION = "regress__56940__02.js";
//var BUGNUMBER = 56940;
var summary = 'String concat should not be O(N**2)';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var data = {X:[], Y:[]};
for (var size = 1000; size <= 10000; size += 1000)
{
data.X.push(size);
data.Y.push(concat(size));
gc();
}

var order = this.BigO(data);

var msg = '';
for (var p = 0; p < data.X.length; p++)
{
msg += '(' + data.X[p] + ', ' + data.Y[p] + '); ';
}
//printStatus(msg);
//printStatus('Order: ' + order);
this.TestCase(SECTION,  'this.BigO ' + order + ' < 2', true, order < 2);

function concat(size)
{
var x = '';
var y = 'Mozilla Mozilla Mozilla Mozilla ';
var z = 'goober ';
var start = new Date();

for (var loop = 0; loop < size; loop++)
{
x += y + z + y + z + y + z;
}
var stop = new Date();
return stop - start;
}

function gc (){
for (var i = 0; i != 4e6; ++i)
{
var tmp = i + 0.1;
}
}
}

})
.endType();
