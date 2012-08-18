vjo.ctype("dsf.jslang.feature.tests.Js15ArrayTests")
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

/** @Test

File Name:         11_1_4.js
Summery:       Elisons in Array literals should not be enumed
*/
test_11_1_4: function () {
var SECTION = "11_1_4.js";
//var BUGNUMBER = 260106;
var summary = 'Elisons in Array literals should not be enumed';
var actual = '';
var expect = '';
var status;
var prop;
var array;

//printBugNumber(BUGNUMBER);
//printStatus (summary);

status = summary + ' ' + this.inSection(1) + ' [,1] ';
array = [,1];
actual = '';
expect = '1';
for (prop in array)
{
if (prop != 'length')
{
actual += prop;
}
}
this.TestCase(SECTION, status, expect, actual);

status = summary + ' ' +  this.inSection(2) + ' [,,1] ';
array = [,,1];
actual = '';
expect = '2';
for (prop in array)
{
if (prop != 'length')
{
actual += prop;
}
}
this.TestCase(SECTION, status, expect, actual);

status = summary + ' ' +  this.inSection(3) + ' [1,] ';
array = [1,];
actual = '';
expect = '0';
for (prop in array)
{
if (prop != 'length')
{
actual += prop;
}
}
this.TestCase(SECTION, status, expect, actual);

status = summary + ' ' +  this.inSection(4) + ' [1,,] ';
array = [1,,];
actual = '';
expect = '0';
for (prop in array)
{
if (prop != 'length')
{
actual += prop;
}
}
this.TestCase(SECTION, status, expect, actual);
},

/** @Test

File Name:        array__001.js
Date: 24 September 2001

SUMMARY: Truncating arrays that have decimal property names.
From correspondence with Igor Bukanov <igor@icesoft.no>:
*/
test_array__001: function(){
var SECTION = "array__001.js";
var UBound = 0;
//var BUGNUMBER = '(none)';
var summary = 'Truncating arrays that have decimal property names';
var BIG_INDEX = 4294967290;
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];

var arr = Array(BIG_INDEX);
arr[BIG_INDEX - 1] = 'a';
arr[BIG_INDEX - 10000] = 'b';
arr[BIG_INDEX - 0.5] = 'c';  // not an array index - but a valid property name
// Truncate the array -
arr.length = BIG_INDEX - 5000;

// Enumerate its properties with for..in
var s = '';
var i ;//<Number
for ( i in arr)
{
s += arr[i];
}

/*
* We expect s == 'cb' or 'bc' (EcmaScript does not fix the order).
* Note 'c' is included: for..in includes ALL enumerable properties,
* not just array-index properties. The bug was: Rhino gave s == ''.
*/
status = this.inSection(1);
actual = sortThis(s);
expect = 'bc';
addThis();

//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

function sortThis(str)
{
var chars = str.split('');
chars = chars.sort();
return chars.join('');
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
//printBugNumber(BUGNUMBER);
//printStatus (summary);

for (i=0; i<UBound; i++)
{
this.TestCase(SECTION, statusitems[i], expectedvalues[i], actualvalues[i]);
}

//exitFunc ('test');
//}
},


/** @Test

File Name:        regress__101964.js

Date: 27 September 2001

SUMMARY: Performance: truncating even very large arrays should be fast!
See http://bugzilla.mozilla.org/show_bug.cgi?id=101964

Adjust this testcase if necessary. The FAST constant defines
an upper bound in milliseconds for any truncation to take.
*/
test_regress__101964: function(){
var SECTION = "regress__101964.js";
var UBound = 0;
//var BUGNUMBER = 101964;
var summary = 'Performance: truncating even very large arrays should be fast!';
var BIG = 10000000;
var LITTLE = 10;
var FAST = 50; // array truncation should be 50 ms or less to pass the test
var MSG_FAST = 'Truncation took less than ' + FAST + ' ms';
var MSG_SLOW = 'Truncation took ';
var MSG_MS = ' ms';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];

status = this.inSection(1);
var arr = Array(BIG);
var start = new Date();
arr.length = LITTLE;
actual = elapsedTime(start);
expect = FAST;
addThis();

//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

function elapsedTime(startTime)
{
return new Date() - startTime;
}


function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = isThisFast(actual);
expectedvalues[UBound] = isThisFast(expect);
UBound++;
}


function isThisFast(ms)
{
if (ms <= FAST)
return MSG_FAST;
return MSG_SLOW + ms + MSG_MS;
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

File Name:       regress__107138.js

Date: 29 October 2001

SUMMARY: Regression test for bug 107138
See http://bugzilla.mozilla.org/show_bug.cgi?id=107138

The bug: arr['1'] == undefined instead of arr['1'] == 'one'.
The bug was intermittent and did not always occur...

The cnSTRESS constant defines how many times to repeat this test.
*/
test_regress__107138: function(){
var SECTION = "regress__107138.js";
var UBound = 0;
var cnSTRESS = 10;
var cnDASH = '-';
//var BUGNUMBER = 107138;
var summary = 'Regression test for bug 107138';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


var arr = ['zero', 'one', 'two', 'three', 'four', 'five',
'six', 'seven', 'eight', 'nine', 'ten'];


// This bug was intermittent. Stress-test it.
for (var j=0; j<cnSTRESS; j++)
{
status = this.inSection(j + cnDASH + 1);
actual = arr[0];
expect = 'zero';
addThis();

status =  this.inSection(j + cnDASH + 2);
actual = arr['0'];
expect = 'zero';
addThis();

status =  this.inSection(j + cnDASH + 3);
actual = arr[1];
expect = 'one';
addThis();

status =  this.inSection(j + cnDASH + 4);
actual = arr['1'];
expect = 'one';
addThis();

status =  this.inSection(j + cnDASH + 5);
actual = arr[2];
expect = 'two';
addThis();

status =  this.inSection(j + cnDASH + 6);
actual = arr['2'];
expect = 'two';
addThis();

status =  this.inSection(j + cnDASH + 7);
actual = arr[3];
expect = 'three';
addThis();

status =  this.inSection(j + cnDASH + 8);
actual = arr['3'];
expect = 'three';
addThis();

status =  this.inSection(j + cnDASH + 9);
actual = arr[4];
expect = 'four';
addThis();

status =  this.inSection(j + cnDASH + 10);
actual = arr['4'];
expect = 'four';
addThis();

status =  this.inSection(j + cnDASH + 11);
actual = arr[5];
expect = 'five';
addThis();

status =  this.inSection(j + cnDASH + 12);
actual = arr['5'];
expect = 'five';
addThis();

status =  this.inSection(j + cnDASH + 13);
actual = arr[6];
expect = 'six';
addThis();

status =  this.inSection(j + cnDASH + 14);
actual = arr['6'];
expect = 'six';
addThis();

status =  this.inSection(j + cnDASH + 15);
actual = arr[7];
expect = 'seven';
addThis();

status =  this.inSection(j + cnDASH + 16);
actual = arr['7'];
expect = 'seven';
addThis();

status =  this.inSection(j + cnDASH + 17);
actual = arr[8];
expect = 'eight';
addThis();

status =  this.inSection(j + cnDASH + 18);
actual = arr['8'];
expect = 'eight';
addThis();

status =  this.inSection(j + cnDASH + 19);
actual = arr[9];
expect = 'nine';
addThis();

status =  this.inSection(j + cnDASH + 20);
actual = arr['9'];
expect = 'nine';
addThis();

status =  this.inSection(j + cnDASH + 21);
actual = arr[10];
expect = 'ten';
addThis();

status =  this.inSection(j + cnDASH + 22);
actual = arr['10'];
expect = 'ten';
addThis();
}


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

File Name:       regress__108440.js

Date: 30 October 2001
SUMMARY: Regression test for bug 108440
See http://bugzilla.mozilla.org/show_bug.cgi?id=108440

We shouldn't crash trying to add an array as an element of itself (!)

Brendan: "...it appears that Array.prototype.toString is unsafe,
and what's more, ECMA-262 Edition 3 has no helpful words about
avoiding recursive death on a cycle."
*/
test_regress__108440: function(){
var SECTION = "regress__108440.js";
//var BUGNUMBER = 108440;
var summary = "Shouldn't crash trying to add an array as an element of itself";
var self = this;
var temp = '';

//printBugNumber(BUGNUMBER);
//printStatus(summary);

/*
* Explicit test:
*/
var a=[];
temp = (a[a.length]=a);

/*
* Implicit test (one of the properties of |self| is |a|)
*/
a=[];
var prop;//<Number
for( prop in self)
{
temp = prop;
temp = (a[a.length] = self[prop]);
}

/*
* Stressful explicit test
*/
a=[];
for (var i=0; i<10; i++)
{
a[a.length] = a;
}

/*
* Test toString()
*/
a=[];
for (i=0; i<10; i++)
{
a[a.length] = a.toString();
}

/*
* Test toSource() - but Rhino doesn't have this, so try...catch it
*/
a=[];
try
{
for (var i=0; i<10; i++)
{
a[a.length] = a.toSource();
}
}
catch(e)
{
}
this.TestCase(SECTION, summary, 'No Crash','No Crash');
},

/** @Test

File Name:       regress__154338.js

Date:    26 June 2002
SUMMARY: Testing array.join() when separator is a variable, not a literal
See http://bugzilla.mozilla.org/show_bug.cgi?id=154338
*/
test_regress__154338: function(){
var SECTION = "regress__154338.js";
var UBound = 0;
//var BUGNUMBER = 154338;
var summary = 'Test array.join() when separator is a variable, not a literal';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


/*
* Note that x === 'H' and y === 'ome'.
*
* Yet for some reason, using |x| or |y| as the separator
* in arr.join() was causing out-of-memory errors, whereas
* using the literals 'H', 'ome' was not -
*
*/
var x = 'Home'[0];
var y = ('Home'.split('H'))[1];


status = this.inSection(1);
var arr = Array('a', 'b');
actual = arr.join('H');
expect = 'aHb';
addThis();

status =  this.inSection(2);
arr = Array('a', 'b');
actual = arr.join(x);
expect = 'aHb';
addThis();

status =  this.inSection(3);
arr = Array('a', 'b');
actual = arr.join('ome');
expect = 'aomeb';
addThis();

status =  this.inSection(4);
arr = Array('a', 'b');
actual = arr.join(y);
expect = 'aomeb';
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
//printBugNumber(BUGNUMBER);
// printStatus(summary);

for (var i=0; i<UBound; i++)
{
this.TestCase(SECTION, statusitems[i], expectedvalues[i], actualvalues[i]);
}

// exitFunc ('test');
//}
},

/**@Test
*
* File Name:       regress__157652.js
* Date:    16 July 2002
* SUMMARY: Testing that Array.sort() doesn't crash on very large arrays
* See http://bugzilla.mozilla.org/show_bug.cgi?id=157652
*
* How large can a JavaScript array be?
* ECMA-262 Ed.3 Final, Section 15.4.2.2 : new Array(len)
*
* This states that |len| must be a a uint32 (unsigned 32-bit integer).
* Note the UBound for uint32's is 2^32 -1 = 0xFFFFFFFF = 4,294,967,295.
*
* Check:
*              js> var arr = new Array(0xFFFFFFFF)
*              js> arr.length
*              4294967295
*
*              js> var arr = new Array(0x100000000)
*              RangeError: invalid array length
*
*
* We'll try the largest possible array first, then a couple others.
* We're just testing that we don't crash on Array.sort().
*
* Try to be good about memory by nulling each array variable after it is
* used. This will tell the garbage collector the memory is no longer needed.
*
* As of 2002-08-13, the JS shell runs out of memory no matter what we do,
* when trying to sort such large arrays.
*
* We only want to test that we don't CRASH on the sort. So it will be OK
* if we get the JS "out of memory" error. Note this terminates the test
* with exit code 3. Therefore we put
*
*                       |expectExitCode(3);|
*
* The only problem will arise if the JS shell ever DOES have enough memory
* to do the sort. Then this test will terminate with the normal exit code 0
* and fail.
*
* Right now, I can't see any other way to do this, because "out of memory"
* is not a catchable error: it cannot be trapped with try...catch.
*
*
* FURTHER HEADACHE: Rhino can't seem to handle the largest array: it hangs.
* So we skip this case in Rhino. Here is correspondence with Igor Bukanov.
* He explains that Rhino isn't actually hanging; it's doing the huge sort:
*
* Philip Schwartau wrote:
*
* > Hi,
* >
* > I'm getting a graceful OOM message on trying to sort certain large
* > arrays. But if the array is too big, Rhino simply hangs. Note that ECMA
* > allows array lengths to be anything less than Math.pow(2,32), so the
* > arrays I'm sorting are legal.
* >
* > Note below, I'm getting an instantaneous OOM error on arr.sort() for LEN
* > = Math.pow(2, 30). So shouldn't I also get one for every LEN between
* > that and Math.pow(2, 32)? For some reason, I start to hang with 100% CPU
* > as LEN hits, say, Math.pow(2, 31) and higher. SpiderMonkey gives OOM
* > messages for all of these. Should I file a bug on this?
*
*   Igor Bukanov wrote:
*
* This is due to different sorting algorithm Rhino uses when sorting
* arrays with length > Integer.MAX_VALUE. If length can fit Java int,
* Rhino first copies internal spare array to a temporary buffer, and then
* sorts it, otherwise it sorts array directly. In case of very spare
* arrays, that Array(big_number) generates, it is rather inefficient and
* generates OutOfMemory if length fits int. It may be worth in your case
* to optimize sorting to take into account array spareness, but then it
* would be a good idea to file a bug about ineficient sorting of spare
* arrays both in case of Rhino and SpiderMonkey as SM always uses a
* temporary buffer.
*
*/

test_regress__157652: function(){
var SECTION = "regress__157652.js";
//var BUGNUMBER = 157652;
var summary = "Testing that Array.sort() doesn't crash on very large arrays";
var expect = 'No Crash';
var actual = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus(summary);

//expectExitCode(0);
//expectExitCode(5);

var IN_RHINO = inRhino();

try
{
if (!IN_RHINO)
{
var a1=Array(0xFFFFFFFF);
a1.sort();
a1 = null;
}

var a2 = Array(0x40000000);
a2.sort();
a2=null;

var a3=Array(0x10000000/4);
a3.sort();
a3=null;
}
catch(ex)
{
// handle changed 1.9 branch behavior. see bug 422348
expect = 'InternalError: allocation size overflow';
actual = ex + '';
}

this.TestCase(SECTION, summary, expect, actual);
//reportCompare(expect, actual, summary);

/*
* Some tests need to know if we are in Rhino as opposed to SpiderMonkey
*/
function inRhino()
{
var defineClass;
return (typeof defineClass == "function");
}
},

/** @Test
* File Name:       regress__178722.js
* Date:    06 November 2002
* SUMMARY: arr.sort() should not output |undefined| when |arr| is empty
* See http://bugzilla.mozilla.org/show_bug.cgi?id=178722
*
* ECMA-262 Ed.3: 15.4.4.11 Array.prototype.sort (comparefn)
*
* 1. Call the [[Get]] method of this object with argument "length".
* 2. Call ToUint32(Result(1)).
* 3. Perform an implementation-dependent sequence of calls to the [[Get]],
*    [[Put]], and [[Delete]] methods of this object, etc. etc.
* 4. Return this object.
*
*
* Note that sort() is done in-place on |arr|. In other words, sort() is a
* "destructive" method rather than a "functional" method. The return value
* of |arr.sort()| and |arr| are the same object.
*
* If |arr| is an empty array, the return value of |arr.sort()| should be
* an empty array, not the value |undefined| as was occurring in bug 178722.
*
*/
test_regress__178722: function(){
var SECTION = "regress__178722.js";
var UBound = 0;
//var BUGNUMBER = 178722;
var summary = 'arr.sort() should not output |undefined| when |arr| is empty';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];
var arr;


// create empty array or pseudo-array objects in various ways
var arr1 = Array();
var arr2 = new Array();
var arr3 = [];
var arr4 = [1];
arr4.pop();


status = this.inSection(1);
arr = arr1.sort();
actual = arr instanceof Array && arr.length === 0 && arr === arr1;
expect = true;
addThis();

status = this.inSection(2);
arr = arr2.sort();
actual = arr instanceof Array && arr.length === 0 && arr === arr2;
expect = true;
addThis();

status = this.inSection(3);
arr = arr3.sort();
actual = arr instanceof Array && arr.length === 0 && arr === arr3;
expect = true;
addThis();

status = this.inSection(4);
arr = arr4.sort();
actual = arr instanceof Array && arr.length === 0 && arr === arr4;
expect = true;
addThis();

// now do the same thing, with non-default sorting:
function g() {return 1;}

status = this.inSection('1a');
arr = arr1.sort(g);
actual = arr instanceof Array && arr.length === 0 && arr === arr1;
expect = true;
addThis();

status = this.inSection('2a');
arr = arr2.sort(g);
actual = arr instanceof Array && arr.length === 0 && arr === arr2;
expect = true;
addThis();

status = this.inSection('3a');
arr = arr3.sort(g);
actual = arr instanceof Array && arr.length === 0 && arr === arr3;
expect = true;
addThis();

status = this.inSection('4a');
arr = arr4.sort(g);
actual = arr instanceof Array && arr.length === 0 && arr === arr4;
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

// exitFunc ('test');
//}
},

/** @Test

File Name:       regress__255555.js

SUMMARY: 'Array.prototype.sort(comparefn) never passes undefined to comparefn'
*/
test_regress__255555: function(){
var SECTION = "regress__255555.js";
//var BUGNUMBER = 255555;
var summary = 'Array.prototype.sort(comparefn) never passes undefined to comparefn';
var actual = 'not undefined';
var expect = 'not undefined';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

function comparefn(a,b)
{
if (typeof a == 'undefined')
{
actual = 'undefined';
return 1;
}
if (typeof b == 'undefined')
{
actual = 'undefined';
return -1;
}
return a - b;
}

var arry = [ 1, 2, undefined ].sort(comparefn)

this.TestCase(SECTION, summary, expect, actual);
},

/** @Test

File Name:       regress__299644.js

SUMMARY: 'Arrays with holes'
*/
test_regress__299644: function(){
var SECTION = "regress__299644.js";
//var BUGNUMBER = 299644;
var summary = 'Arrays with holes';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

actual = (new Array(10).concat()).length;
expect = 10;
this.TestCase(SECTION, '(new Array(10).concat()).length == 10', expect, actual);

var a = new Array(10);
actual = true;
expect = true;
for (var p in a)
{
actual = false;
break;
}
this.TestCase(SECTION, 'Array holes are not enumerable', expect, actual);
},

/** @Test

File Name:       regress__300858.js

SUMMARY: 'Do not crash when sorting array with holes'
*/
test_regress__300858: function(){
var SECTION = "regress__300858.js";
//var BUGNUMBER = 300858;
var summary = 'Do not crash when sorting array with holes';
var actual = 'No Crash';
var expect = 'No Crash';

var arry     = [];
arry[6]  = 'six';
arry[8]  = 'eight';
arry[9]  = 'nine';
arry[13] = 'thirteen';
arry[14] = 'fourteen';
arry[21] = 'twentyone';
arry.sort();

this.TestCase(SECTION, summary, expect, actual);
},

/** @Test

File Name:       regress__310351.js

SUMMARY: 'Convert host "list" objects to arrays'
*/
test_regress__310351: function(){
var SECTION = "regress__310351.js";
//var BUGNUMBER = 310351;
var summary = 'Convert host "list" objects to arrays';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var nodeList = [];
if (typeof document != 'undefined')
{
nodeList = document.getElementsByTagName('*');
}
else
{
//printStatus('test using dummy array since no document available');
}

var array = Array.prototype.slice.call(nodeList, 0);

expect = 'Array';
actual = array.constructor.name;

// nodeList is live and may change
var saveLength = nodeList.length;

this.TestCase(SECTION, summary + ': constructor test', expect, actual);

expect = saveLength;
actual = array.length;

this.TestCase(SECTION, summary + ': length test', expect, actual);
expect = true;
actual = true;

for (var i = 0; i < saveLength; i++)
{
if (array[i] != nodeList[i])
{
actual = false;
summary += ' Comparison failed: array[' + i + ']=' + array[i] +
', nodeList[' + i + ']=' + nodeList[i];
break;
}
}

this.TestCase(SECTION, summary + ': identical elements test', expect, actual);

},

/** @Test

File Name:       regress__311515.js

SUMMARY: 'Array.sort should skip holes and undefined during sort'
*/
test_regress__311515: function(){
var SECTION = "regress__311515.js";
//var BUGNUMBER = 311583;
var summary = 'Array.sort should skip holes and undefined during sort';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var a = [, 1, , 2, undefined];

actual = a.sort().toString();
expect = '1,2,,,';

this.TestCase(SECTION, summary, expect, actual);
},

/** @Test

File Name:       regress__313153.js

SUMMARY: 'generic native method dispatcher extra actual arguments'
*/
test_regress__313153: function(){
var SECTION = "regress__313153.js";
//var BUGNUMBER = 313153;
var summary = 'generic native method dispatcher extra actual arguments';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

expect = '1,2,3';
actual = (function (){return Array.concat.apply([], arguments)})(1,2,3).toString();

this.TestCase(SECTION, summary, expect, actual);
},

/** @Test

File Name:       regress__315509__01.js

SUMMARY: 'Array.prototype.unshift on Arrays with holes'
*/
test_regress__315509__01: function(){
var SECTION = "regress__315509__01.js";
//var BUGNUMBER = 315509;
var summary = 'Array.prototype.unshift on Arrays with holes';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var a = [0,1,2,3,4];
delete a[1];

expect = '0,,2,3,4';
actual = a.toString();

this.TestCase(SECTION, summary, expect, actual);

a.unshift('a','b');

expect = 'a,b,0,,2,3,4';
actual = a.toString();

this.TestCase(SECTION, summary, expect, actual);
},

/** @Test

File Name:       regress__330812.js

SUMMARY: 'Array.prototype.unshift on Arrays with holes'
*/
test_regress__330812: function(){
var SECTION = "regress__330812.js";
//var BUGNUMBER = 330812;
var summary = 'Making Array(1<<29).sort() less problematic';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

//expectExitCode(0);
//expectExitCode(3);

//printStatus('This test passes if the browser does not hang or crash');
//printStatus('This test expects exit code 0 or 3 to indicate out of memory');

try
{
var result = Array(1 << 29).sort();
}
catch(ex)
{
// handle changed 1.9 branch behavior. see bug 422348
expect = 'InternalError: allocation size overflow';
actual = ex + '';
}

this.TestCase(SECTION, summary, expect, actual);
},

/** @Test

File Name:       regress__345961.js

SUMMARY: 'Array.prototype.shift should preserve holes'
*/
test_regress__345961: function(){
var SECTION = "regress__345961.js";
//var BUGNUMBER = 345961;
var summary = 'Array.prototype.shift should preserve holes';
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

expect = false;

var array = new Array(2);
array.shift();
actual = array.hasOwnProperty();
this.TestCase(SECTION, summary, expect, actual);

array=Array(1);
array.shift(1); //<@SUPRESSTYPECHECK
actual = array.hasOwnProperty(1);
this.TestCase(SECTION, summary, expect, actual);

// exitFunc ('test');
//}
},

/** @Test

File Name:       regress__348810.js

SUMMARY: 'Do not crash when sorting an array of holes'
*/
test_regress__348810: function(){
var SECTION = "regress__348810.js";
//var BUGNUMBER = 348810;
var summary = 'Do not crash when sorting an array of holes';
var actual = 'No Crash';
var expect = 'No Crash';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

var a = Array(1);
a.sort();
this.TestCase(SECTION, summary, expect, actual);

// exitFunc ('test');
//}

},

/** @Test

File Name:       regress__350256__01.js

SUMMARY: 'Array.apply maximum arguments: 2^16'
*/
test_regress__350256__01: function(){
var SECTION = "regress__350256__01.js";
//var BUGNUMBER = 350256;
var summary = 'Array.apply maximum arguments: 2^16';
var actual = '';
var expect = '';


//-----------------------------------------------------------------------------
//test(Math.pow(2, 16));
//-----------------------------------------------------------------------------
var length = Math.pow(2, 16);
//function test(length)
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);


var a = new Array();
a[length - 2] = 'length-2';
a[length - 1] = 'length-1';

var b = Array.apply(null, a);

expect = length + ',length-2,length-1';
actual = b.length + "," + b[length - 2] + "," + b[length - 1];
this.TestCase(SECTION, summary, expect, actual);

function f() {
return arguments.length + "," + arguments[length - 2] + "," +
arguments[length - 1];
}

expect = length + ',length-2,length-1';
actual = f.apply(null, a);

this.TestCase(SECTION, summary, expect, actual);

//exitFunc ('test');
//}
},

/** @Test

File Name:       regress__350256__02.js

SUMMARY: 'Array.apply maximum arguments: 2^20'
*/
test_regress__350256__2: function(){
var SECTION = "regress__350256__02.js";
//var BUGNUMBER = 350256;
var summary = 'Array.apply maximum arguments: 2^20';
var actual = '';
var expect = '';


//-----------------------------------------------------------------------------
//test(Math.pow(2, 20));
//-----------------------------------------------------------------------------

var length = Math.pow(2, 20);

//function test(length)
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);


var a = new Array();
a[length - 2] = 'length-2';
a[length - 1] = 'length-1';

var b = Array.apply(null, a);

expect = length + ',length-2,length-1';
actual = b.length + "," + b[length - 2] + "," + b[length - 1];
this.TestCase(SECTION, summary, expect, actual);

function f() {
return arguments.length + "," + arguments[length - 2] + "," +
arguments[length - 1];
}

expect = length + ',length-2,length-1';
actual = f.apply(null, a);

this.TestCase(SECTION, summary, expect, actual);

//exitFunc ('test');
//}
},

/** @Test

File Name:       regress__350256__03.js

SUMMARY: 'Array.apply maximum arguments: 2^24-1'
*/
test_regress__350256__03: function(){
var SECTION = "regress__350256__03.js";
//var BUGNUMBER = 350256;
var summary = 'Array.apply maximum arguments: 2^24-1';
var actual = '';
var expect = '';


//-----------------------------------------------------------------------------
//test(Math.pow(2, 24)-1);
//-----------------------------------------------------------------------------

var length = Math.pow(2, 24)-1;

//function test(length)
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

try
{

var a = new Array();
a[length - 2] = 'length-2';
a[length - 1] = 'length-1';

var b = Array.apply(null, a);

expect = length + ',length-2,length-1';
actual = b.length + "," + b[length - 2] + "," + b[length - 1];
this.TestCase(SECTION, summary, expect, actual);

function f() {
return arguments.length + "," + arguments[length - 2] + "," +
arguments[length - 1];
}

expect = length + ',length-2,length-1';
actual = f.apply(null, a);

}
catch(ex)
{
expect = 'InternalError: script stack space quota is exhausted';
actual = ex + '';
print(actual);
}

this.TestCase(SECTION, summary, expect, actual);

//exitFunc ('test');
//}
},

/** @Test

File Name:       regress__360681__01.js

SUMMARY: 'Regression from bug 224128'
*/
test_regress__360681__01: function(){
var SECTION = "regress__360681__01.js";
//var BUGNUMBER = 360681;
var summary = 'Regression from bug 224128';
var actual = '';
var expect = '';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
// enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

expect = actual = 'No Crash';

var a = Array(3);
a[0] = 1;
a[1] = 2;
a.sort(function () { gc(); return 1; });

this.TestCase(SECTION, summary, expect, actual);

//exitFunc ('test');
//}

function gc (){
for (var i = 0; i != 4e6; ++i)
{
var tmp = i + 0.1;
}
}
},

/** @Test

File Name:       regress__360681__02.js

SUMMARY: 'Regression from bug 224128'
*/
test_regress__360681__02: function(){
var SECTION = "regress__360681__02.js";
//var BUGNUMBER = 360681;
var summary = 'Regression from bug 224128';
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

expect = actual = 'No Crash';

var N = 1000;

// Make an array with a hole at the end
var a = Array(N);
for (i = 0; i < N - 1; ++i)
a[i] = 1;

// array_sort due for array with N elements with allocates a temporary vector
// with 2*N. Lets create strings that on 32 and 64 bit CPU cause allocation
// of the same amount of memory + 1 word for their char arrays. After we GC
// strings with a reasonable malloc implementation that memory will be most
// likely reused in array_sort for the temporary vector. Then the bug causes
// accessing the one-beyond-the-aloocation word and re-interpretation of
// 0xFFF0FFF0 as GC thing.

var str1 = Array(2*(2*N + 1) + 1).join(String.fromCharCode(0xFFF0));
var str2 = Array(4*(2*N + 1) + 1).join(String.fromCharCode(0xFFF0));
gc();
str1 = str2 = null;
gc();

var firstCall = true;
a.sort(function (a, b) {
if (firstCall) {
firstCall = false;
gc();
}
return a - b;
});

this.TestCase(SECTION, summary, expect, actual);

// exitFunc ('test');
//}
function gc (){
for (var i = 0; i != 4e6; ++i)
{
var tmp = i + 0.1;
}
}
},

/** @Test

File Name:       regress__364104.js

SUMMARY: Array.prototype.indexOf, Array.prototype.lastIndexOf issues with the optional second fromIndex argument"
*/
test_regress__364104: function(){
var SECTION = "regress__364104.js";
//var BUGNUMBER     = "364104";
var summary = "Array.prototype.indexOf, Array.prototype.lastIndexOf issues " +
"with the optional second fromIndex argument";
var actual, expect;

//printBugNumber(BUGNUMBER);
//printStatus(summary);

/**************
* BEGIN TEST *
**************/

var failed = false;

try
{
// indexOf
if ([2].indexOf(2) != 0)
throw "indexOf: not finding 2!?";
if ([2].indexOf(2, 0) != 0)
throw "indexOf: not interpreting explicit second argument 0!";
if ([2].indexOf(2, 1) != -1)
throw "indexOf: ignoring second argument with value equal to array length!";
if ([2].indexOf(2, 2) != -1)
throw "indexOf: ignoring second argument greater than array length!";
if ([2].indexOf(2, 17) != -1)
throw "indexOf: ignoring large second argument!";
if ([2].indexOf(2, -5) != 0)
throw "indexOf: calculated fromIndex < 0, should search entire array!";
if ([2, 3].indexOf(2, -1) != -1)
throw "indexOf: not handling index == (-1 + 2), element 2 correctly!";
if ([2, 3].indexOf(3, -1) != 1)
throw "indexOf: not handling index == (-1 + 2), element 3 correctly!";

// lastIndexOf
if ([2].lastIndexOf(2) != 0)
throw "lastIndexOf: not finding 2!?";
if ([2].lastIndexOf(2, 1) != 0)
throw "lastIndexOf: not interpreting explicit second argument 1!?";
if ([2].lastIndexOf(2, 17) != 0)
throw "lastIndexOf: should have searched entire array!";
if ([2].lastIndexOf(2, -5) != -1)
throw "lastIndexOf: -5 + 1 < 0, so array shouldn't be searched!";
if ([2].lastIndexOf(2, -2) != -1)
throw "lastIndexOf: -2 + 1 < 0, so array shouldn't be searched!";
if ([2, 3].lastIndexOf(2, -1) != 0)
throw "lastIndexOf: not handling index == (-1 + 2), element 2 correctly!";
if ([2, 3].lastIndexOf(3, -1) != 1)
throw "lastIndexOf: not handling index == (-1 + 2), element 3 correctly!";
if ([2, 3].lastIndexOf(2, -2) != 0)
throw "lastIndexOf: not handling index == (-2 + 2), element 2 correctly!";
if ([2, 3].lastIndexOf(3, -2) != -1)
throw "lastIndexOf: not handling index == (-2 + 2), element 3 correctly!";
if ([2, 3].lastIndexOf(2, -3) != -1)
throw "lastIndexOf: calculated fromIndex < 0, shouldn't search array for 2!";
if ([2, 3].lastIndexOf(3, -3) != -1)
throw "lastIndexOf: calculated fromIndex < 0, shouldn't search array for 3!";
}
catch (e)
{
failed = e;
}


expect = false;
actual = failed;

this.TestCase(SECTION, summary, expect, actual);

},

/** @Test

File Name:       regress__422286.js

SUMMARY: Array slice when array\'s length is assigned
*/
test_regress__422286: function(){
var SECTION = "regress__422286.js";
//var BUGNUMBER = 422286;
var summary = 'Array slice when array\'s length is assigned';
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

Array(10000).slice(1);
a = Array(1);
a.length = 10000;
a.slice(1);
a = Array(1);
a.length = 10000;
a.slice(-1);

this.TestCase(SECTION, summary, expect, actual);

//exitFunc ('test');
//}
},

/** @Test

File Name:       regress__424954.js

SUMMARY: Do not crash with [].concat(null)
*/
test_regress__424954: function(){
var SECTION = "regress__424954.js";
//var BUGNUMBER = 424954;
var summary = 'Do not crash with [].concat(null)';
var actual = 'No Crash';
var expect = 'No Crash';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
// enterFunc ('test');
//printBugNumber(BUGNUMBER);
// printStatus (summary);

[].concat(null);

this.TestCase(SECTION, summary, expect, actual);

//exitFunc ('test');
//}

},

/** @Test

File Name:       regress__451483.js

SUMMARY: [].splice.call(0) == []
*/
test_regress__451483: function(){
var SECTION = "regress__451483.js";
//var BUGNUMBER = 451483;
var summary = '[].splice.call(0) == []';
var actual = '';
var expect = '';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
// enterFunc ('test');
// printBugNumber(BUGNUMBER);
// printStatus (summary);

expect = true;
var result = [].splice.call(0);
//print('[].splice.call(0) = ' + result);
actual = result instanceof Array && result.length == 0;

this.TestCase(SECTION, summary, expect, actual);

// exitFunc ('test');
//}
},

/** @Test

File Name:       regress__451906.js

SUMMARY:Index array by numeric string
*/
test_regress__451906: function(){
var SECTION = "regress__451906.js";
//var BUGNUMBER = 451906;
var summary = 'Index array by numeric string';
var actual = '';
var expect = '';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
// enterFunc ('test');
//printBugNumber(BUGNUMBER);
// printStatus (summary);

expect = 1;
var s=[1,2,3];
actual = s['0'];

this.TestCase(SECTION, summary, expect, actual);

// exitFunc ('test');
//}

},

/** @Test

File Name:       regress__456845.js

SUMMARY:JIT: popArrs[a].pop is not a functio
*/
test_regress__456845: function(){
var SECTION = "regress__456845.js";
//var BUGNUMBER = 456845;
var summary = 'JIT: popArrs[a].pop is not a function';
var actual = 'No Error';
var expect = 'No Error';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

jit(true);

try
{
var chars = '0123456789abcdef';
var size = 1000;
var mult = 100;

var arr = [];
var lsize = size;
while (lsize--) { arr.push(chars); }

var popArrs = [];
for (var i=0; i<mult; i++) { popArrs.push(arr.slice()); }


for(var a=0;a<mult;a++) {
var x; while (x = popArrs[a].pop()) {  }
}

jit(false);
}
catch(ex)
{
jit(false);
actual = ex + '';
}

this.TestCase(SECTION, summary, expect, actual);

//exitFunc ('test');
//}

},

/** @Test

File Name:       regress__94257.js

Date: 30 October 2001

SUMMARY: Regression test for bug 94257
See http://bugzilla.mozilla.org/show_bug.cgi?id=94257

Rhino used to crash on this code; specifically, on the line

arr[1+1] += 2;
*/
test_regress__94257: function(){
var SECTION = "regress__94257.js";
var UBound = 0;
//var BUGNUMBER = 94257;
var summary = "Making sure we don't crash on this code -";
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


var arr = new Array(6);
arr[1+1] = 1;
arr[1+1] += 2;


status = this.inSection(1);
actual = arr[1+1];
expect = 3;
addThis();

status =  this.inSection(2);
actual = arr[1+1+1];
expect = undefined;
addThis();

status =  this.inSection(3);
actual = arr[1];
expect = undefined;
addThis();


arr[1+2] = 'Hello';


status =  this.inSection(4);
actual = arr[1+1+1];
expect = 'Hello';
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

// exitFunc ('test');
//}

},

/** @Test

File Name:       regress__99120__01.js

SUMMARY:sort() should not be O(N^2) on sorted data
*/
test_regress__99120__01: function(){
var SECTION = "regress__99120__01.js";
//var BUGNUMBER = 99120;
var summary = 'sort() should not be O(N^2) on sorted data';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var data = {X:[], Y:[]};
for (var size = 5000; size <= 15000; size += 1000)
{
data.X.push(size);
data.Y.push(testSort(size));
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
this.TestCase(SECTION,  'BigO ' + order + ' < 2', true, order < 2);

function testSort(size)
{
var arry = new Array(size);
for (var i = 0; i < size; i++)
{
arry[i] = i;
}
var start = new Date();
arry.sort(compareFn);
var stop = new Date();
return stop - start;
}

function compareFn(a, b)
{
return a - b;
}

function gc (){
for (var i = 0; i != 4e6; ++i)
{
var tmp = i + 0.1;
}
}

},

/** @Test

File Name:       regress__99120__02.js

SUMMARY: sort() should not be O(N^2) on nearly sorted data
*/
test_regress__99120__02: function(){
var SECTION = "regress__99120__02.js";
//var BUGNUMBER = 99120;
var summary = 'sort() should not be O(N^2) on nearly sorted data';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var data = {X:[], Y:[]};
for (var size = 5000; size <= 15000; size += 1000)
{
data.X.push(size);
data.Y.push(testSort(size));
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
this.TestCase(SECTION, 'BigO ' + order + ' < 2', true, order < 2);

function testSort(size)
{
var arry = new Array(size);
for (var i = 0; i < size; i++)
{
arry[i] = i + '';
}
var start = new Date();
arry.sort();
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
/*
Calculate the "order" of a set of data points {X: [], Y: []}
by computing successive "derivatives" of the data until
the data is exhausted or the derivative is linear.
*/
BigO:function(data)
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
}


})
.endType();
