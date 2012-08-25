vjo.ctype("dsf.jslang.feature.tests.Ecma3ArrayTests")
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

File Name:         15_4_4_11__01.js
ECMA Section:       15.4 Array Objects

Description:        Array.sort should not eat exceptions.
*/
test_15_4_4_11__01: function () {
var SECTION = "15_4_4_11__01.js";
var n = 0;
var array = [4,3,2,1];
var summary = 'Array.sort should not eat exceptions';
var expect = "e=1 n=1";
var actual = '';

try {
array.sort(function (){
throw ++n;
});

} catch (e) {
actual = ("e="+e+" n="+n);
}
this.TestCase(SECTION, expect, expect, actual);
},

/** @Test

File Name:        15_4_4_3__1.js

SUMMARY: Testing Array.prototype.toLocaleString()
See http://bugzilla.mozilla.org/show_bug.cgi?id=56883
See http://bugzilla.mozilla.org/show_bug.cgi?id=58031

By ECMA3 15.4.4.3, myArray.toLocaleString() means that toLocaleString()
should be applied to each element of the array, and the results should be
concatenated with an implementation-specific delimiter. For example:

myArray[0].toLocaleString()  +  ','  +  myArray[1].toLocaleString()  + etc.

In this testcase toLocaleString is a user-defined property of each
array element; therefore it is the function that should be
invoked. This function increments a global variable. Therefore the
end value of this variable should be myArray.length.
*/
test_15_4_4_3__1: function(){
var SECTION = "15_4_4_3__1.js";
var n = 0;
var actual = '';
var expect = '';
var obj = {toLocaleString: function() {n++}};
var myArray = [obj, obj, obj];
myArray.toLocaleString();

actual = n;
expect = 3;
this.TestCase(SECTION, expect, expect, actual);
},


/** @Test

File Name:        15_4_4_4__001.js

SUMMARY: Testing Array.prototype.concat()
See http://bugzilla.mozilla.org/show_bug.cgi?id=169795
*/
test_15_4_4_4__001: function(){
var SECTION = "15_4_4_4__001.js";
var UBound = 0;
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect = '';
var expectedvalues = [];
var x;
var SECT_PREFIX = 'Section ';
var SECT_SUFFIX = ' of test - ';

status = this.inSection(1);
x = "Hello";
actual = [].concat(x).toString();
expect = x.toString();
addThis();

status = this.inSection(2);
x = 999;
actual = [].concat(x).toString();
expect = x.toString();
addThis();

status = this.inSection(3);
x = /Hello/g;
actual = [].concat(x).toString();
expect = x.toString();
addThis();

status = this.inSection(4);
x = new Error("Hello");
actual = [].concat(x).toString();
expect = x.toString();
addThis();

status = this.inSection(5);
x = function() {return "Hello";};
actual = [].concat(x).toString();
expect = x.toString();
addThis();

status = this.inSection(6);
x = [function() {return "Hello";}];
actual = [].concat(x).toString();
expect = x.toString();
addThis();

status = this.inSection(7);
x = [1,2,3].concat([4,5,6]);
actual = [].concat(x).toString();
expect = x.toString();
addThis();

status = this.inSection(8);
x = eval('this');
actual = [].concat(x).toString();
expect = x.toString();
addThis();

status = this.inSection(9);
x={length:0};
actual = [].concat(x).toString();
expect = x.toString();
addThis();

status = this.inSection(10);
x={length:2, 0:0, 1:1};
actual = [].concat(x).toString();
expect = x.toString();
addThis();

function addThis() {
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
}

for (var i = 0; i < UBound; i++) {
this.TestCase(SECTION, expectedvalues[i], expectedvalues[i], actualvalues[i]);
}

},

/** @Test

File Name:       15_4_5_1__01.js

SUMMARY: array.length coverage
*/
test_15_4_5_1__01: function(){
var SECTION = "15_4_5_1__01.js";
var actual = '';
var expect = '';

var a = [];

expect = 'RangeError: invalid array length';
actual = '';
try {
a.length = -1;
} catch (ex) {
actual = ex + '';
}
this.TestCase(SECTION, expect, expect, actual);

actual = '';
try {
a.length = 12345678901234567890;
} catch (ex) {
actual = ex + '';
}
this.TestCase(SECTION, expect, expect, actual);

actual = '';
try {
a.length = 'a'; //<@SUPRESSTYPECHECK
} catch (ex) {
actual = ex + '';
}
this.TestCase(SECTION, expect, expect, actual);
},


/** @Test

File Name:       regress__101488.js

SUMMARY: Testing Array.prototype.toLocaleString()
See http://bugzilla.mozilla.org/show_bug.cgi?id=56883
See http://bugzilla.mozilla.org/show_bug.cgi?id=58031

By ECMA3 15.4.4.3, myArray.toLocaleString() means that toLocaleString()
should be applied to each element of the array, and the results should be
concatenated with an implementation-specific delimiter. For example:

myArray[0].toLocaleString()  +  ','  +  myArray[1].toLocaleString()  + etc.

In this testcase toLocaleString is a user-defined property of each
array element; therefore it is the function that should be
invoked. This function increments a global variable. Therefore the
end value of this variable should be myArray.length.
*/
test_regress__101488: function(){
var SECTION = "regress__101488.js";
var UBound = 0;
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];
var arr = [];
var SECT_PREFIX = 'Section ';
var SECT_SUFFIX = ' of test - ';

status = this.inSection(1);
arr = Array();
tryThis('arr.length = new Number(1);');
actual = arr.length;
expect = 1;
addThis();

status = this.inSection(2);
arr = Array(5);
tryThis('arr.length = new Number(1);');
actual = arr.length;
expect = 1;
addThis();

status = this.inSection(3);
arr = Array();
tryThis('arr.length = new Number(17);');
actual = arr.length;
expect = 17;
addThis();

status = this.inSection(4);
arr = Array(5);
tryThis('arr.length = new Number(17);');
actual = arr.length;
expect = 17;
addThis();

status = this.inSection(5);
arr = new Array();
tryThis('arr.length = new Number(1);');
actual = arr.length;
expect = 1;
addThis();

status = this.inSection(6);
arr = new Array(5);
tryThis('arr.length = new Number(1);');
actual = arr.length;
expect = 1;
addThis();

arr = new Array();
tryThis('arr.length = new Number(17);');
actual = arr.length;
expect = 17;
addThis();

status = this.inSection(7);
arr = new Array(5);
tryThis('arr.length = new Number(17);');
actual = arr.length;
expect = 17;
addThis();

// test();

function tryThis(s) {
try {
eval(s);
} catch(e) {
// keep going
}
}

function addThis() {
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
}

//	    function test() {
//	    	enterFunc ('test');
//	    	printBugNumber(BUGNUMBER);
//	    	printStatus (summary);
for (var i=0; i<UBound; i++) {
this.TestCase(SECTION, statusitems[i], expectedvalues[i], actualvalues[i]);
}
//	    }

},

/** @Test

File Name:       regress__130451.js

SUMMARY: Array.prototype.sort() should not (re-)define .length
See http://bugzilla.mozilla.org/show_bug.cgi?id=130451

From the ECMA-262 Edition 3 Final spec:

NOTE: The sort function is intentionally generic; it does not require that
its |this| value be an Array object. Therefore, it can be transferred to
other kinds of objects for use as a method. Whether the sort function can
be applied successfully to a host object is implementation-dependent.
The interesting parts of this testcase are the contrasting expectations for
Brendan's test below, when applied to Array objects vs. non-Array objects.
*/
test_regress__130451: function(){
var SECTION = "regress__130451.js";
var UBound = 0;
var BUGNUMBER = 130451;
var summary = 'Array.prototype.sort() should not (re-)define .length';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];
var arr = [];
var cmp = new Function();
var SECT_PREFIX = 'Section ';
var SECT_SUFFIX = ' of test - ';

/*
* First: test Array.prototype.sort() on Array objects
*/
status = this.inSection(1);
arr = [0,1,2,3];
cmp = function(x,y) {return x-y;};
actual = arr.sort(cmp).length;
expect = 4;
addThis();

status = this.inSection(2);
arr = [0,1,2,3];
cmp = function(x,y) {return y-x;};
actual = arr.sort(cmp).length;
expect = 4;
addThis();

status = this.inSection(3);
arr = [0,1,2,3];
cmp = function(x,y) {return x-y;};
arr.length = 1;
actual = arr.sort(cmp).length;
expect = 1;
addThis();

/*
* This test is by Brendan. Setting arr.length to
* 2 and then 4 should cause elements to be deleted.
*/
arr = [0,1,2,3];
cmp = function(x,y) {return x-y;};
arr.sort(cmp);

status = this.inSection(4);
actual = arr.join();
expect = '0,1,2,3';
addThis();

status = this.inSection(5);
actual = arr.length;
expect = 4;
addThis();

status = this.inSection(6);
arr.length = 2;
actual = arr.join();
expect = '0,1';
addThis();

status = this.inSection(7);
arr.length = 4;
actual = arr.join();
expect = '0,1,,';  //---- see how 2,3 have been lost
addThis();

/*
* Now test Array.prototype.sort() on non-Array objects
*/
status = this.inSection(8);
var obj = new Object();
obj.sort = Array.prototype.sort;
obj.length = 4;
obj[0] = 0;
obj[1] = 1;
obj[2] = 2;
obj[3] = 3;
cmp = function(x,y) {return x-y;};
actual = obj.sort(cmp).length;
expect = 4;
addThis();

/*
* Here again is Brendan's test. Unlike the array case
* above, the setting of obj.length to 2 and then 4
* should NOT cause elements to be deleted
*/
obj = new Object();
obj.sort = Array.prototype.sort;
obj.length = 4;
obj[0] = 3;
obj[1] = 2;
obj[2] = 1;
obj[3] = 0;
cmp = function(x,y) {return x-y;};
obj.sort(cmp);  //---- this is what triggered the buggy behavior below
obj.join = Array.prototype.join;

status = this.inSection(9);
actual = obj.join();
expect = '0,1,2,3';
addThis();

status = this.inSection(10);
actual = obj.length;
expect = 4;
addThis();

status = this.inSection(11);
obj.length = 2;
actual = obj.join();
expect = '0,1';
addThis();

/*
* Before this bug was fixed, |actual| held the value '0,1,,'
* as in the Array-object case at top. This bug only occurred
* if Array.prototype.sort() had been applied to |obj|,
* as we have done higher up.
*/
status = this.inSection(12);
obj.length = 4;
actual = obj.join();
expect = '0,1,2,3';
addThis();

//test();

function addThis() {
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
}

//	function test() {
//	    enterFunc('test');
//	    printBugNumber(BUGNUMBER);
//	    printStatus(summary);
for (var i=0; i<UBound; i++) {
this.TestCase(SECTION, statusitems[i], expectedvalues[i], actualvalues[i]);
}
//	    exitFunc ('test');
//        }
},

/** @Test

File Name:       regress__322135__01.js

SUMMARY: 'Array.prototype.push on Array with length 2^32-1'
*/
test_regress__322135__01: function(){
var SECTION = "regress__322135__01.js";
var actual = 'Completed';
var expect = 'Completed';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

//printStatus('This bug passes if it does not cause an out of memory error');
//printStatus('Other issues related to array length are not tested.');

var length = 4294967295;
var array = new Array(length);

//printStatus('before array.length = ' + array.length);

try {
array.push('Kibo');
} catch(ex) {
//printStatus(ex.name + ': ' + ex.message);
}
this.TestCase(SECTION, expect, expect, actual);

//expect = 'Kibo';
//actual = array[length];
//reportCompare(expect, actual, summary + ': element appended');

//expect = length;
//actual = array.length;
//reportCompare(expect, actual, summary + ': array length unchanged');
},

/** @Test

File Name:       regress__322135__02.js

SUMMARY: 'Array.prototype.concat on Array with length 2^32-1'
*/
test_regress__322135__02: function(){
var SECTION = "regress__322135__02.js";
var actual = 'Completed';
var expect = 'Completed';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

//printStatus('This bug passes if it does not cause an out of memory error');
//printStatus('Other issues related to array length are not tested.');

var length = 4294967295;
var array1 = new Array(length);
var array2 = ['Kibo'];
var array;

try {
array = array1.concat(array2);
} catch(ex) {
//printStatus(ex.name + ': ' + ex.message);
}

this.TestCase(SECTION, expect, expect, actual);
},

/** @Test

File Name:       regress__322135__03.js

SUMMARY: 'Array.prototype.splice on Array with length 2^32-1'
*/
test_regress__322135__03: function(){
var SECTION = "regress__322135__03.js";
var actual = 'Completed';
var expect = 'Completed';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

//printStatus('This bug passes if it does not cause an out of memory error');
//printStatus('Other issues related to array length are not tested.');

var length = 4294967295;
var array = new Array(length);
var array1 = ['Kibo'];
var array;

try {
array.splice(0, 0, array1);
} catch(ex) {
//printStatus(ex.name + ': ' + ex.message);
}
this.TestCase(SECTION, expect, expect, actual);

//expect = 'Kibo';
//actual = array[0];
//reportCompare(expect, actual, summary + ': element prepended');

//expect = length;
//actual = array.length;
//reportCompare(expect, actual, summary + ': array length unchanged');
},

/** @Test

File Name:       regress__322135__04.js

SUMMARY: 'Array.prototype.unshift on Array with length 2^32-1'
*/
test_regress__322135__04: function(){
var SECTION = "regress__322135__04.js";
var actual = 'Completed';
var expect = 'Completed';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

//printStatus('This bug passes if it does not cause an out of memory error');
//printStatus('Other issues related to array length are not tested.');

var length = 4294967295;
var array = new Array(length);

try {
array.unshift('Kibo');
} catch(ex) {
//printStatus(ex.name + ': ' + ex.message);
}
this.TestCase(SECTION, expect, expect, actual);

//expect = 'Kibo';
//actual = array[0];
//reportCompare(expect, actual, summary + ': first prepended');

//expect = length;
//actual = array.length;
//reportCompare(expect, actual, summary + ': array length unchanged');
},

/** @Test

File Name:       regress__387501.js

SUMMARY: 'Array.prototype.toString|toSource|toLocaleString is not generic'
*/
test_regress__387501: function(){
var SECTION = "regress__387501.js";
//var BUGNUMBER = 387501;
//var summary = 'Array.prototype.toString|toSource|toLocaleString is not generic';
var actual = '';
var expect = '';

//test();

//function test() {
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

try {
expect = 'TypeError: Array.prototype.toString called on incompatible String';
actual = Array.prototype.toString.call((new String('foo')));
} catch(ex) {
actual = ex + '';
}
var assertEquals = null;
assertEquals(expect, actual);
this.TestCase(SECTION, expect, expect, actual);

try {
expect = 'TypeError: Array.prototype.toLocaleString called on incompatible String';
actual = Array.prototype.toLocaleString.call((new String('foo')));
} catch(ex) {
actual = ex + '';
}
this.TestCase(SECTION, expect, expect, actual);

if (typeof Array.prototype.toSource != 'undefined') {
try {
expect = 'TypeError: Array.prototype.toSource called on incompatible String';
actual = Array.prototype.toSource.call((new String('foo')));
} catch(ex) {
actual = ex + '';
}
this.TestCase(SECTION, expect, expect, actual);
}

//exitFunc ('test');
//}
},

/** @Test

File Name:       regress__421325.js

SUMMARY: 'Dense Arrays and holes'
*/
test_regress__421325: function(){
var SECTION = "regress__421325.js";
//var BUGNUMBER = 421325;
//var summary = 'Dense Arrays and holes';
var actual = '';
var expect = '';

//test();

//function test() {
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

Array.prototype[1] = 'bar';

var a = [];
a[0]='foo';
a[2] = 'baz';
expect = 'foo,bar,baz';
actual = a + '';

this.TestCase(SECTION, expect, expect, actual);

//exitFunc ('test');
//}

},

/** @Test

File Name:       regress__430717.js

SUMMARY: 'Dense Arrays should inherit deleted elements from Array.prototype'
*/
test_regress__430717: function(){
var SECTION = "regress__430717.js";
//var BUGNUMBER = 430717;
//var summary = 'Dense Arrays should inherit deleted elements from Array.prototype';
var actual = '';
var expect = '';

//test();

//function test() {
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

Array.prototype[2] = "two";
var a = [0,1,2,3];
delete a[2];

expect = 'two';
actual = a[2];
this.TestCase(SECTION, expect, expect, actual);

//exitFunc ('test');
//}
}

})
.endType();
