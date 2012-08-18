vjo.ctype("dsf.jslang.feature.tests.Js12ArrayTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({

//> public void assertEquals(boolean b1,Object o1)
assertEquals : function(b1,o1){
},

constructs:function(){
this.base();
},

/** @Test
File Name:         general1.js
Description:  'This tests out some of the functionality on methods on the Array objects'

Author:       Nick Lerissa
Date:         Fri Feb 13 09:58:28 PST 1998
*/
test_general1: function () {
var SECTION = "general1.js";
//var VERSION = 'no version';
//startTest();
//var TITLE = 'String:push,unshift,shift';

//writeHeaderToLog('Executing script: general1.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

var array1 = [];

array1.push(123);            //array1 = [123]
array1.push("dog");          //array1 = [123,dog]
array1.push(-99);            //array1 = [123,dog,-99]
array1.push("cat");          //array1 = [123,dog,-99,cat]
this.TestCase( SECTION, "array1.pop()", array1.pop(),'cat');
//array1 = [123,dog,-99]
array1.push("mouse");        //array1 = [123,dog,-99,mouse]
this.TestCase( SECTION, "array1.shift()", array1.shift(),123);
//array1 = [dog,-99,mouse]
array1.unshift(96);          //array1 = [96,dog,-99,mouse]
this.TestCase( SECTION, "state of array", String([96,"dog",-99,"mouse"]), String(array1));
this.TestCase( SECTION, "array1.length", array1.length,4);
array1.shift();              //array1 = [dog,-99,mouse]
array1.shift();              //array1 = [-99,mouse]
array1.shift();              //array1 = [mouse]
this.TestCase( SECTION, "array1.shift()", array1.shift(),"mouse");
this.TestCase( SECTION, "array1.shift()", "undefined", String(array1.shift()));

//test();
},

/** @Test
File Name:         general2.js
Description:  'This tests out some of the functionality on methods on the Array objects'

Author:       Nick Lerissa
Date:         Fri Feb 13 09:58:28 PST 1998
*/
test_general2: function () {
var SECTION = "general2.js";
//var VERSION = 'no version';
//startTest();
//var TITLE = 'String:push,splice,concat,unshift,sort';

//writeHeaderToLog('Executing script: general2.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

var array1 = new Array();
var array2 = [];
var size   = 10;

// this for loop populates array1 and array2 as follows:
// array1 = [0,1,2,3,4,....,size - 2,size - 1]
// array2 = [size - 1, size - 2,...,4,3,2,1,0]
for (var i = 0; i < size; i++)
{
array1.push(i);
array2.push(size - 1 - i);
}

// the following for loop reverses the order of array1 so
// that it should be similarly ordered to array2
for (i = array1.length; i > 0; i--)
{
var array3 = array1.slice(1,i);
array1.splice(1,i-1);
array1 = array3.concat(array1);
}

// the following for loop reverses the order of array1
// and array2
for (i = 0; i < size; i++)
{
array1.push(array1.shift());
array2.unshift(array2.pop());
}

this.TestCase( SECTION, "Array.push,pop,shift,unshift,slice,splice", true,String(array1) == String(array2));
array1.sort();
array2.sort();
this.TestCase( SECTION, "Array.sort", true,String(array1) == String(array2));

//test();
},

/** @Test
File Name:         slice.js
Description:  'This tests out some of the functionality on methods on the Array objects'

Author:       Nick Lerissa
Date:         Fri Feb 13 09:58:28 PST 1998
*/
test_slice: function () {
var SECTION = "slice.js";
//var VERSION = 'no version';
//startTest();
//var TITLE = 'String:slice';

//writeHeaderToLog('Executing script: slice.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

var a = ['a','test string',456,9.34,new String("string object"),[],['h','i','j','k']];
var b = [1,2,3,4,5,6,7,8,9,0];

exhaustiveSliceTest("exhaustive slice test 1", a);
exhaustiveSliceTest("exhaustive slice test 2", b);

//test();

function mySlice(a, from, to)
{
var from2       = from;
var to2         = to;
var returnArray = [];
var i;

if (from2 < 0) from2 = a.length + from;
if (to2 < 0)   to2   = a.length + to;

if ((to2 > from2)&&(to2 > 0)&&(from2 < a.length))
{
if (from2 < 0)        from2 = 0;
if (to2 > a.length) to2 = a.length;

for (i = from2; i < to2; ++i) returnArray.push(a[i]);
}
return returnArray;
}

// This function tests the slice command on an Array
// passed in. The arguments passed into slice range in
// value from -5 to the length of the array + 4. Every
// combination of the two arguments is tested. The expected
// result of the slice(...) method is calculated and
// compared to the actual result from the slice(...) method.
// If the Arrays are not similar false is returned.
function exhaustiveSliceTest(testname, a)
{
var x = 0;
var y = 0;
var errorMessage;
var reason = "";
var passed = true;

for (x = -(2 + a.length); x <= (2 + a.length); x++)
for (y = (2 + a.length); y >= -(2 + a.length); y--)
{
var b  = a.slice(x,y);
var c = mySlice(a,x,y);

if (String(b) != String(c))
{
errorMessage =
"ERROR: 'TEST FAILED' ERROR: 'TEST FAILED' ERROR: 'TEST FAILED'\n" +
"            test: " + "a.slice(" + x + "," + y + ")\n" +
"               a: " + String(a) + "\n" +
"   actual result: " + String(b) + "\n" +
" expected result: " + String(c) + "\n";
//writeHeaderToLog(errorMessage);
reason = reason + errorMessage;
passed = false;
}
}
this.assertEquals(true, passed);
//var testCase = new TestCase(SECTION, testname, true, passed);
//if (passed == false)
//testCase.reason = reason;
//return testCase;
}
},

/** @Test
File Name:         splice1.js
Description:  'Tests Array.splice(x,y) w/no var args'

Author:       Nick Lerissa
Date:         Fri Feb 13 09:58:28 PST 1998
*/
test_splice1: function () {
var SECTION = "splice1.js";
//var VERSION = 'no version';
//var TITLE = 'String:splice 1';
//var BUGNUMBER="123795";

//startTest();
//writeHeaderToLog('Executing script: splice1.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

var a = ['a','test string',456,9.34,new String("string object"),[],['h','i','j','k']];
var b = [1,2,3,4,5,6,7,8,9,0];

exhaustiveSpliceTest("exhaustive splice w/no optional args 1",a);
exhaustiveSpliceTest("exhaustive splice w/no optional args 1",b);

//test();


function mySplice(testArray, splicedArray, first, len, elements)
{
var removedArray  = [];
var adjustedFirst = first;
var adjustedLen   = len;

if (adjustedFirst < 0) adjustedFirst = testArray.length + first;
if (adjustedFirst < 0) adjustedFirst = 0;

if (adjustedLen < 0) adjustedLen = 0;

for (i = 0; (i < adjustedFirst)&&(i < testArray.length); ++i)
splicedArray.push(testArray[i]);

if (adjustedFirst < testArray.length)
for (i = adjustedFirst; (i < adjustedFirst + adjustedLen) &&
(i < testArray.length); ++i)
{
removedArray.push(testArray[i]);
}

for (i = 0; i < elements.length; i++) splicedArray.push(elements[i]);

for (i = adjustedFirst + adjustedLen; i < testArray.length; i++)
splicedArray.push(testArray[i]);

return removedArray;
}

function exhaustiveSpliceTest(testname, testArray)
{
var errorMessage;
var passed = true;
var reason = "";

for (var first = -(testArray.length+2); first <= 2 + testArray.length; first++)
{
var actualSpliced   = [];
var expectedSpliced = [];
var actualRemoved   = [];
var expectedRemoved = [];

for (var len = 0; len < testArray.length + 2; len++)
{
actualSpliced   = [];
expectedSpliced = [];

for (var i = 0; i < testArray.length; ++i)
actualSpliced.push(testArray[i]);

actualRemoved   = actualSpliced.splice(first,len);
expectedRemoved = mySplice(testArray,expectedSpliced,first,len,[]);

var adjustedFirst = first;
if (adjustedFirst < 0) adjustedFirst = testArray.length + first;
if (adjustedFirst < 0) adjustedFirst = 0;

if (  (String(actualSpliced) != String(expectedSpliced))
||(String(actualRemoved) != String(expectedRemoved)))
{
if (  (String(actualSpliced) == String(expectedSpliced))
&&(String(actualRemoved) != String(expectedRemoved)) )
{
if ( (expectedRemoved.length == 1)
&&(String(actualRemoved) == String(expectedRemoved[0]))) continue;
if ( expectedRemoved.length == 0 && actualRemoved == void 0) continue;
}

errorMessage =
"ERROR: 'TEST FAILED'\n" +
"             test: " + "a.splice(" + first + "," + len + ",-97,new String('test arg'),[],9.8)\n" +
"                a: " + String(testArray) + "\n" +
"   actual spliced: " + String(actualSpliced) + "\n" +
" expected spliced: " + String(expectedSpliced) + "\n" +
"   actual removed: " + String(actualRemoved) + "\n" +
" expected removed: " + String(expectedRemoved) + "\n";
//writeHeaderToLog(errorMessage);
reason = reason + errorMessage;
passed = false;
}
}
}
this.assertEquals(true, passed);
//var testcase = new TestCase( SECTION, testname, true, passed);
//if (!passed)
//testcase.reason = reason;
//return testcase;
}
},

/** @Test
File Name:         splice2.js
Description:  'Tests Array.splice(x,y) w/4 var args'

Author:       Nick Lerissa
Date:         Fri Feb 13 09:58:28 PST 1998
*/
test_splice2: function () {
var SECTION = "splice2.js";
//var VERSION = 'no version';
//var TITLE = 'String:splice 2';
//var BUGNUMBER="123795";

//startTest();
//writeHeaderToLog('Executing script: splice2.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

var a = ['a','test string',456,9.34,new String("string object"),[],['h','i','j','k']];
var b = [1,2,3,4,5,6,7,8,9,0];

exhaustiveSpliceTestWithArgs("exhaustive splice w/2 optional args 1",a);
exhaustiveSpliceTestWithArgs("exhaustive splice w/2 optional args 2",b);

//test();


function mySplice(testArray, splicedArray, first, len, elements)
{
var removedArray  = [];
var adjustedFirst = first;
var adjustedLen   = len;

if (adjustedFirst < 0) adjustedFirst = testArray.length + first;
if (adjustedFirst < 0) adjustedFirst = 0;

if (adjustedLen < 0) adjustedLen = 0;

for (i = 0; (i < adjustedFirst)&&(i < testArray.length); ++i)
splicedArray.push(testArray[i]);

if (adjustedFirst < testArray.length)
for (i = adjustedFirst; (i < adjustedFirst + adjustedLen) && (i < testArray.length); ++i)
removedArray.push(testArray[i]);

for (i = 0; i < elements.length; i++) splicedArray.push(elements[i]);

for (i = adjustedFirst + adjustedLen; i < testArray.length; i++)
splicedArray.push(testArray[i]);

return removedArray;
}

function exhaustiveSpliceTestWithArgs(testname, testArray)
{
var passed = true;
var errorMessage;
var reason = "";
for (var first = -(testArray.length+2); first <= 2 + testArray.length; first++)
{
var actualSpliced   = [];
var expectedSpliced = [];
var actualRemoved   = [];
var expectedRemoved = [];

for (var len = 0; len < testArray.length + 2; len++)
{
actualSpliced   = [];
expectedSpliced = [];

for (var i = 0; i < testArray.length; ++i)
actualSpliced.push(testArray[i]);

actualRemoved   = actualSpliced.splice(first,len,-97,new String("test arg"),[],9.8);
expectedRemoved = mySplice(testArray,expectedSpliced,first,len,[-97,new String("test arg"),[],9.8]);

var adjustedFirst = first;
if (adjustedFirst < 0) adjustedFirst = testArray.length + first;
if (adjustedFirst < 0) adjustedFirst = 0;


if (  (String(actualSpliced) != String(expectedSpliced))
||(String(actualRemoved) != String(expectedRemoved)))
{
if (  (String(actualSpliced) == String(expectedSpliced))
&&(String(actualRemoved) != String(expectedRemoved)) )
{

if ( (expectedRemoved.length == 1)
&&(String(actualRemoved) == String(expectedRemoved[0]))) continue;
if ( expectedRemoved.length == 0  && actualRemoved == void 0 ) continue;
}

errorMessage =
"ERROR: 'TEST FAILED' ERROR: 'TEST FAILED' ERROR: 'TEST FAILED'\n" +
"             test: " + "a.splice(" + first + "," + len + ",-97,new String('test arg'),[],9.8)\n" +
"                a: " + String(testArray) + "\n" +
"   actual spliced: " + String(actualSpliced) + "\n" +
" expected spliced: " + String(expectedSpliced) + "\n" +
"   actual removed: " + String(actualRemoved) + "\n" +
" expected removed: " + String(expectedRemoved);
reason = reason + errorMessage;
//writeHeaderToLog(errorMessage);
passed = false;
}
}
}
this.assertEquals(true, passed);
//var testcase = new TestCase(SECTION, testname, true, passed);
//if (!passed) testcase.reason = reason;
//return testcase;
}
}

})
.endType();
