vjo.ctype("dsf.jslang.feature.tests.Js15ExtensionsTests")
.inherits("com.ebay.dsf.jslang.feature.tests.BaseTest")
.protos({

constructs:function(){
this.base();
},

reportCompare : function (e, a, d){
this.TestCase("", d, e, a);
},

compareSource : function (e, a, d){
this.TestCase("", d, e, a);
},

inSection:function(x) {
var SECT_PREFIX = 'Section ';
var SECT_SUFFIX = ' of test - ';
return SECT_PREFIX + x + SECT_SUFFIX;
},

gc : function () {
for (var i = 0; i != 4e6; ++i)
{
var tmp = i + 0.1;
}
},

options : function (aOptionName)
{
// return value of options() is a comma delimited list
// of the previously set values

var value = '';
for (var optionName in options.currvalues)
{
value += optionName + ',';
}
if (value)
{
value = value.substring(0, value.length-1);
}

if (aOptionName)
{
if (options.currvalues[aOptionName])
{
// option is set, toggle it to unset
delete options.currvalues[aOptionName];
options.preferences.setPref(aOptionName, false);
}
else
{
// option is not set, toggle it to set
options.currvalues[aOptionName] = true;
options.preferences.setPref(aOptionName, true);
}
}

return value;
},



/** @Test
File Name:         catchguard__001__n.js
DESCRIPTION = " the non-guarded catch should HAVE to appear last"
*/
test_catchguard__001__n : function () {
var SECTION = "catchguard__001__n.js";
//DESCRIPTION = " the non-guarded catch should HAVE to appear last";
//EXPECTED = "error";

//test();

//function test()
//{
//enterFunc ("test");

var EXCEPTION_DATA = "String exception";
var e;

//printStatus ("Catchguard syntax negative test.");

try
{
throw EXCEPTION_DATA;
}
catch (e) /* the non-guarded catch should HAVE to appear last */
{

}
//catch (e if true)
//{

//}
//catch (e if false)
//{

//}
//this.Testcase(SECTION,"Illegally constructed catchguard should have thrown an exception.",'PASS', 'FAIL');
this.reportCompare('PASS', 'FAIL',
"Illegally constructed catchguard should have thrown " +
"an exception.");

//exitFunc ("test");
//}

},

/** @Test
File Name:         catchguard__001.js
*/
test_catchguard__001 : function () {
var SECTION = "catchguard__001.js";
//test();

//function test()
//{
//enterFunc ("test");

var EXCEPTION_DATA = "String exception";
var e = "foo";
var caught = false;

//printStatus ("Basic catchguard test.");

try
{
throw EXCEPTION_DATA;
}
catch (e if true)
{
caught = true;
e = "this change should not propagate outside of this scope";
}
catch (e if false)
{
this.Testcase(SECTION,"Catch block (e if false) should not have executed.",'PASS', 'FAIL');
//reportCompare('PASS', 'FAIL', "Catch block (e if false) should not have executed.");
}
catch (e)
{
this.Testcase(SECTION,"Catch block (e) should not have executed.",'PASS', 'FAIL');
//reportCompare('PASS', 'FAIL', "Catch block (e) should not have executed.");
}

if (!caught)
this.Testcase(SECTION,"Exception was never caught",'PASS', 'FAIL');
//reportCompare('PASS', 'FAIL', "Exception was never caught.");

if (e != "foo")
this.Testcase(SECTION,"Exception data modified inside catch() scope should not be visible in the function scope",'PASS', 'FAIL');
//reportCompare('PASS', 'FAIL', "Exception data modified inside catch() scope should " +
//	  "not be visible in the function scope (e = '" +
//	  e + "'.)");

this.Testcase(SECTION," ",'PASS', 'PASS');
//reportCompare('PASS', 'PASS', '');
//exitFunc ("test");
//}

},

/** @Test
File Name:         catchguard__002.js
*/
test_catchguard__002 : function () {
var SECTION = "catchguard__002.js";
//test();

//function test()
//{
// enterFunc ("test");

var EXCEPTION_DATA = "String exception";
var e;
var caught = false;

//printStatus ("Basic catchguard test.");

try
{
throw EXCEPTION_DATA;
}
catch (e if true)
{
caught = true;
}
catch (e if true)
{
this.reportCompare('PASS', 'FAIL',
"Second (e if true) catch block should not have executed.");
}
catch (e)
{
this.reportCompare('PASS', 'FAIL', "Catch block (e) should not have executed.");
}

if (!caught)
this.reportCompare('PASS', 'FAIL', "Exception was never caught.");

this.reportCompare('PASS', 'PASS', 'Basic catchguard test');

//exitFunc ("test");
//}

},

/** @Test
File Name:         catchguard__003.js
*/
test_catchguard__003 : function () {
var SECTION = "catchguard__003.js";
//test();

//function test()
//{
//enterFunc ("test");

var EXCEPTION_DATA = "String exception";
var e = "foo", x = "foo";
var caught = false;

//printStatus ("Catchguard 'Common Scope' test.");

try
{
throw EXCEPTION_DATA;
}
catch (e if ((x = 1) && false))
{
this.reportCompare('PASS', 'FAIL',
"Catch block (e if ((x = 1) && false) should not " +
"have executed.");
}
catch (e if (x == 1))
{
caught = true;
}
catch (e)
{
this.reportCompare('PASS', 'FAIL',
"Same scope should be used across all catchguards.");
}

if (!caught)
this.reportCompare('PASS', 'FAIL',
"Exception was never caught.");

if (e != "foo")
this.reportCompare('PASS', 'FAIL',
"Exception data modified inside catch() scope should " +
"not be visible in the function scope (e ='" +
e + "'.)");

if (x != 1)
this.reportCompare('PASS', 'FAIL',
"Data modified in 'catchguard expression' should " +
"be visible in the function scope (x = '" +
x + "'.)");

this.reportCompare('PASS', 'PASS', 'Catchguard Common Scope test');

//exitFunc ("test");
//}

},

/** @Test
File Name:         getset__004.js
Date: 14 April 2001

SUMMARY: Testing  obj.__defineSetter__(), obj.__defineGetter__()
Note: this is a non-ECMA language extension
*/
test_getset__004 : function () {
var SECTION = "getset__004.js";
var UBound = 0;
//var BUGNUMBER = '(none)';
var summary = 'Testing  obj.__defineSetter__(), obj.__defineGetter__()';
var statprefix = 'Status: ';
var status = '';
var statusitems = [ ];
var actual = '';
var actualvalues = [ ];
var expect= '';
var expectedvalues = [ ];
var cnDEFAULT = 'default name';
var cnFRED = 'Fred';
var obj = {};
var obj2 = {};
var s = '';


// SECTION1: define getter/setter directly on an object (not its prototype)
obj = new Object();
obj.nameSETS = 0;
obj.nameGETS = 0;
obj.__defineSetter__('name', function(newValue) {this._name=newValue; this.nameSETS++;});
obj.__defineGetter__('name', function() {this.nameGETS++; return this._name;});

status = 'In SECTION1 of test after 0 sets, 0 gets';
actual = [obj.nameSETS,obj.nameGETS];
expect = [0,0];
addThis();

s = obj.name;
status = 'In SECTION1 of test after 0 sets, 1 get';
actual = [obj.nameSETS,obj.nameGETS];
expect = [0,1];
addThis();

obj.name = cnFRED;
status = 'In SECTION1 of test after 1 set, 1 get';
actual = [obj.nameSETS,obj.nameGETS];
expect = [1,1];
addThis();

obj.name = obj.name;
status = 'In SECTION1 of test after 2 sets, 2 gets';
actual = [obj.nameSETS,obj.nameGETS];
expect = [2,2];
addThis();


// SECTION2: define getter/setter in Object.prototype
Object.prototype.nameSETS = 0;
Object.prototype.nameGETS = 0;
Object.prototype.__defineSetter__('name', function(newValue) {this._name=newValue; this.nameSETS++;});
Object.prototype.__defineGetter__('name', function() {this.nameGETS++; return this._name;});

obj = new Object();
status = 'In SECTION2 of test after 0 sets, 0 gets';
actual = [obj.nameSETS,obj.nameGETS];
expect = [0,0];
addThis();

s = obj.name;
status = 'In SECTION2 of test after 0 sets, 1 get';
actual = [obj.nameSETS,obj.nameGETS];
expect = [0,1];
addThis();

obj.name = cnFRED;
status = 'In SECTION2 of test after 1 set, 1 get';
actual = [obj.nameSETS,obj.nameGETS];
expect = [1,1];
addThis();

obj.name = obj.name;
status = 'In SECTION2 of test after 2 sets, 2 gets';
actual = [obj.nameSETS,obj.nameGETS];
expect = [2,2];
addThis();


// SECTION 3: define getter/setter in prototype of user-defined constructor
function TestObject()
{
}
TestObject.prototype.nameSETS = 0;
TestObject.prototype.nameGETS = 0;
TestObject.prototype.__defineSetter__('name', function(newValue) {this._name=newValue; this.nameSETS++;});
TestObject.prototype.__defineGetter__('name', function() {this.nameGETS++; return this._name;});
TestObject.prototype.name = cnDEFAULT;

obj = new TestObject();
status = 'In SECTION3 of test after 1 set, 0 gets'; // (we set a default value in the prototype)
actual = [obj.nameSETS,obj.nameGETS];
expect = [1,0];
addThis();

s = obj.name;
status = 'In SECTION3 of test after 1 set, 1 get';
actual = [obj.nameSETS,obj.nameGETS];
expect = [1,1];
addThis();

obj.name = cnFRED;
status = 'In SECTION3 of test after 2 sets, 1 get';
actual = [obj.nameSETS,obj.nameGETS];
expect = [2,1];
addThis();

obj.name = obj.name;
status = 'In SECTION3 of test after 3 sets, 2 gets';
actual = [obj.nameSETS,obj.nameGETS];
expect = [3,2];
addThis();

obj2 = new TestObject();
status = 'obj2 = new TestObject() after 1 set, 0 gets';
actual = [obj2.nameSETS,obj2.nameGETS];
expect = [1,0]; // we set a default value in the prototype -
addThis();

// Use both obj and obj2  -
obj2.name = obj.name +  obj2.name;
status = 'obj2 = new TestObject() after 2 sets, 1 get';
actual = [obj2.nameSETS,obj2.nameGETS];
expect = [2,1];
addThis();

status = 'In SECTION3 of test after 3 sets, 3 gets';
actual = [obj.nameSETS,obj.nameGETS];
expect = [3,3];  // we left off at [3,2] above -
addThis();


//---------------------------------------------------------------------------------
//test();
//---------------------------------------------------------------------------------


function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual.toString();
expectedvalues[UBound] = expect.toString();
UBound++;
}


//function test()
//{
// enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

for (var i = 0; i < UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], getStatus(i));
}

//exitFunc ('test');
//}


function getStatus(i)
{
return statprefix + statusitems[i];
}

},

/** @Test
File Name:         getset__005.js
Date: 14 April 2001

SUMMARY: Testing  obj.__defineSetter__(), obj.__defineGetter__()
Note: this is a non-ECMA language extension

This test is the same as getset-004.js, except that here we
store the getter/setter functions in global variables.
*/
test_getset__005 : function () {
var SECTION = "getset__005.js";
var UBound = 0;
//var BUGNUMBER = '(none)';
var summary = 'Testing  obj.__defineSetter__(), obj.__defineGetter__()';
var statprefix = 'Status: ';
var status = '';
var statusitems = [ ];
var actual = '';
var actualvalues = [ ];
var expect= '';
var expectedvalues = [ ];
var cnName = 'name';
var cnDEFAULT = 'default name';
var cnFRED = 'Fred';
var obj = {};
var obj2 = {};
var s = '';


// The getter/setter functions we'll use in all three sections below -
var cnNameSetter = function(newValue) {this._name=newValue; this.nameSETS++;};
var cnNameGetter = function() {this.nameGETS++; return this._name;};


// SECTION1: define getter/setter directly on an object (not its prototype)
obj = new Object();
obj.nameSETS = 0;
obj.nameGETS = 0;
obj.__defineSetter__(cnName, cnNameSetter);
obj.__defineGetter__(cnName, cnNameGetter);

status = 'In SECTION1 of test after 0 sets, 0 gets';
actual = [obj.nameSETS,obj.nameGETS];
expect = [0,0];
addThis();

s = obj.name;
status = 'In SECTION1 of test after 0 sets, 1 get';
actual = [obj.nameSETS,obj.nameGETS];
expect = [0,1];
addThis();

obj.name = cnFRED;
status = 'In SECTION1 of test after 1 set, 1 get';
actual = [obj.nameSETS,obj.nameGETS];
expect = [1,1];
addThis();

obj.name = obj.name;
status = 'In SECTION1 of test after 2 sets, 2 gets';
actual = [obj.nameSETS,obj.nameGETS];
expect = [2,2];
addThis();


// SECTION2: define getter/setter in Object.prototype
Object.prototype.nameSETS = 0;
Object.prototype.nameGETS = 0;
Object.prototype.__defineSetter__(cnName, cnNameSetter);
Object.prototype.__defineGetter__(cnName, cnNameGetter);

obj = new Object();
status = 'In SECTION2 of test after 0 sets, 0 gets';
actual = [obj.nameSETS,obj.nameGETS];
expect = [0,0];
addThis();

s = obj.name;
status = 'In SECTION2 of test after 0 sets, 1 get';
actual = [obj.nameSETS,obj.nameGETS];
expect = [0,1];
addThis();

obj.name = cnFRED;
status = 'In SECTION2 of test after 1 set, 1 get';
actual = [obj.nameSETS,obj.nameGETS];
expect = [1,1];
addThis();

obj.name = obj.name;
status = 'In SECTION2 of test after 2 sets, 2 gets';
actual = [obj.nameSETS,obj.nameGETS];
expect = [2,2];
addThis();


// SECTION 3: define getter/setter in prototype of user-defined constructor
function TestObject()
{
}
TestObject.prototype.nameSETS = 0;
TestObject.prototype.nameGETS = 0;
TestObject.prototype.__defineSetter__(cnName, cnNameSetter);
TestObject.prototype.__defineGetter__(cnName, cnNameGetter);
TestObject.prototype.name = cnDEFAULT;

obj = new TestObject();
status = 'In SECTION3 of test after 1 set, 0 gets'; // (we set a default value in the prototype)
actual = [obj.nameSETS,obj.nameGETS];
expect = [1,0];
addThis();

s = obj.name;
status = 'In SECTION3 of test after 1 set, 1 get';
actual = [obj.nameSETS,obj.nameGETS];
expect = [1,1];
addThis();

obj.name = cnFRED;
status = 'In SECTION3 of test after 2 sets, 1 get';
actual = [obj.nameSETS,obj.nameGETS];
expect = [2,1];
addThis();

obj.name = obj.name;
status = 'In SECTION3 of test after 3 sets, 2 gets';
actual = [obj.nameSETS,obj.nameGETS];
expect = [3,2];
addThis();

obj2 = new TestObject();
status = 'obj2 = new TestObject() after 1 set, 0 gets';
actual = [obj2.nameSETS,obj2.nameGETS];
expect = [1,0]; // we set a default value in the prototype -
addThis();

// Use both obj and obj2  -
obj2.name = obj.name +  obj2.name;
status = 'obj2 = new TestObject() after 2 sets, 1 get';
actual = [obj2.nameSETS,obj2.nameGETS];
expect = [2,1];
addThis();

status = 'In SECTION3 of test after 3 sets, 3 gets';
actual = [obj.nameSETS,obj.nameGETS];
expect = [3,3];  // we left off at [3,2] above -
addThis();


//---------------------------------------------------------------------------------
//test();
//---------------------------------------------------------------------------------


function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual.toString();
expectedvalues[UBound] = expect.toString();
UBound++;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

for (var i = 0; i < UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], getStatus(i));
}

// exitFunc ('test');
//}


function getStatus(i)
{
return statprefix + statusitems[i];
}

},

/** @Test
File Name:         getset__006.js
Date: 14 April 2001

SUMMARY: Testing  obj.__lookupGetter__(), obj.__lookupSetter__()
See http://bugzilla.mozilla.org/show_bug.cgi?id=71992

Brendan: "I see no need to provide more than the minimum:
o.__lookupGetter__('p') returns the getter function for o.p,
or undefined if o.p has no getter.  Users can wrap and layer."
*/
test_getset__006 : function () {
var SECTION = "getset__006.js";
var UBound = 0;
//var BUGNUMBER = 71992;
var summary = 'Testing  obj.__lookupGetter__(), obj.__lookupSetter__()';
var statprefix = 'Status: ';
var status = '';
var statusitems = [ ];
var actual = '';
var actualvalues = [ ];
var expect= '';
var expectedvalues = [ ];
var cnName = 'name';
var cnColor = 'color';
var cnNonExistingProp = 'ASDF_#_$%';
var cnDEFAULT = 'default name';
var cnFRED = 'Fred';
var cnRED = 'red';
var obj = {};
var obj2 = {};
var s;


// The only setter and getter functions we'll use in the three sections below -
var cnNameSetter = function(newValue) {this._name=newValue; this.nameSETS++;};
var cnNameGetter = function() {this.nameGETS++; return this._name;};



// SECTION1: define getter/setter directly on an object (not its prototype)
obj = new Object();
obj.nameSETS = 0;
obj.nameGETS = 0;
obj.__defineSetter__(cnName, cnNameSetter);
obj.__defineGetter__(cnName, cnNameGetter);
obj.name = cnFRED;
obj.color = cnRED;

status ='In SECTION1 of test; looking up extant getter/setter';
actual = [obj.__lookupSetter__(cnName), obj.__lookupGetter__(cnName)];
expect = [cnNameSetter, cnNameGetter];
addThis();

status = 'In SECTION1 of test; looking up nonexistent getter/setter';
actual = [obj.__lookupSetter__(cnColor), obj.__lookupGetter__(cnColor)];
expect = [undefined, undefined];
addThis();

status = 'In SECTION1 of test; looking up getter/setter on nonexistent property';
actual = [obj.__lookupSetter__(cnNonExistingProp), obj.__lookupGetter__(cnNonExistingProp)];
expect = [undefined, undefined];
addThis();



// SECTION2: define getter/setter in Object.prototype
Object.prototype.nameSETS = 0;
Object.prototype.nameGETS = 0;
Object.prototype.__defineSetter__(cnName, cnNameSetter);
Object.prototype.__defineGetter__(cnName, cnNameGetter);

obj = new Object();
obj.name = cnFRED;
obj.color = cnRED;

status = 'In SECTION2 of test looking up extant getter/setter';
actual = [obj.__lookupSetter__(cnName), obj.__lookupGetter__(cnName)];
expect = [cnNameSetter, cnNameGetter];
addThis();

status = 'In SECTION2 of test; looking up nonexistent getter/setter';
actual = [obj.__lookupSetter__(cnColor), obj.__lookupGetter__(cnColor)];
expect = [undefined, undefined];
addThis();

status = 'In SECTION2 of test; looking up getter/setter on nonexistent property';
actual = [obj.__lookupSetter__(cnNonExistingProp), obj.__lookupGetter__(cnNonExistingProp)];
expect = [undefined, undefined];
addThis();



// SECTION 3: define getter/setter in prototype of user-defined constructor
function TestObject()
{
}
TestObject.prototype.nameSETS = 0;
TestObject.prototype.nameGETS = 0;
TestObject.prototype.__defineSetter__(cnName, cnNameSetter);
TestObject.prototype.__defineGetter__(cnName, cnNameGetter);
TestObject.prototype.name = cnDEFAULT;

obj = new TestObject();
obj.name = cnFRED;
obj.color = cnRED;

status = 'In SECTION3 of test looking up extant getter/setter';
actual = [obj.__lookupSetter__(cnName), obj.__lookupGetter__(cnName)];
expect = [cnNameSetter, cnNameGetter];
addThis();

status = 'In SECTION3 of test; looking up non-existent getter/setter';
actual = [obj.__lookupSetter__(cnColor), obj.__lookupGetter__(cnColor)];
expect = [undefined, undefined];
addThis();

status = 'In SECTION3 of test; looking up getter/setter on nonexistent property';
actual = [obj.__lookupSetter__(cnNonExistingProp), obj.__lookupGetter__(cnNonExistingProp)];
expect = [undefined, undefined];
addThis();



//---------------------------------------------------------------------------------
//test();
//---------------------------------------------------------------------------------


function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual.toString();
expectedvalues[UBound] = expect.toString();
UBound++;
}


//function test()
//{
// enterFunc ('test');
// printBugNumber(BUGNUMBER);
// printStatus (summary);

for (var i = 0; i < UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], getStatus(i));
}

// exitFunc ('test');
//}


function getStatus(i)
{
return statprefix + statusitems[i];
}

},

/** @Test
File Name:         no__such__method.js
*/
test_no__such__method : function () {
var SECTION = "no__such__method.js";
//var BUGNUMBER = 196097;
var summary = '__noSuchMethod__ handler';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var o = {
__noSuchMethod__: function (id, args)
{
return(id + '('+args.join(',')+')');
}
};

status = summary + ' ' + this.inSection(1) + ' ';
actual = o.foo(1,2,3);
expect = 'foo(1,2,3)';
this.reportCompare(expect, actual, status);

status = summary + ' ' + this.inSection(2) + ' ';
actual = o.bar(4,5);
expect = 'bar(4,5)';
this.reportCompare(expect, actual, status);

status = summary + ' ' + this.inSection(3) + ' ';
actual = o.baz();
expect = 'baz()';
this.reportCompare(expect, actual, status);

},

/** @Test
File Name:         regress__104077.js
* Date: 10 October 2001
* SUMMARY: Regression test for Bugzilla bug 104077
* See http://bugzilla.mozilla.org/show_bug.cgi?id=104077
* "JS crash: with/finally/return"
*
* Also http://bugzilla.mozilla.org/show_bug.cgi?id=120571
* "JS crash: try/catch/continue."
*
* SpiderMonkey crashed on this code - it shouldn't.
*
* NOTE: the finally-blocks below should execute even if their try-blocks
* have return or throw statements in them:
*
* ------- Additional Comment #76 From Mike Shaver 2001-12-07 01:21 -------
* finally trumps return, and all other control-flow constructs that cause
* program execution to jump out of the try block: throw, break, etc.  Once you
* enter a try block, you will execute the finally block after leaving the try,
* regardless of what happens to make you leave the try.
*
*/
test_regress__104077 : function () {
var SECTION = "regress__104077.js";
var UBound = 0;
//var BUGNUMBER = 104077;
var summary = "Just testing that we don't crash on with/finally/return -";
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


function addValues_3(obj)
{
var sum = 0;

with (obj)
{
try
{
sum = arg1 + arg2;
with (arg3)
{
while (sum < 10)
{
try
{
if (sum > 5)
return sum;
sum += 1;
}
catch (e)
{
sum += 1;
//print(e);
}
}
}
}
finally
{
try
{
sum +=1;
//print("In finally block of addValues_3() function: sum = " + sum);
}
catch (e if e == 42)
{
sum +=1;
//print('In finally catch block of addValues_3() function: sum = ' + sum + ', e = ' + e);
}
finally
{
sum +=1;
//print("In finally finally block of addValues_3() function: sum = " + sum);
return sum;
}
}
}
}

status = this.inSection(9);
obj = new Object();
obj.arg1 = 1;
obj.arg2 = 2;
obj.arg3 = new Object();
obj.arg3.a = 10;
obj.arg3.b = 20;
actual = addValues_3(obj);
expect = 8;
captureThis();




function addValues_4(obj)
{
var sum = 0;

with (obj)
{
try
{
sum = arg1 + arg2;
with (arg3)
{
while (sum < 10)
{
try
{
if (sum > 5)
return sum;
sum += 1;
}
catch (e)
{
sum += 1;
//print(e);
}
}
}
}
finally
{
try
{
sum += 1;
//print("In finally block of addValues_4() function: sum = " + sum);
}
catch (e if e == 42)
{
sum += 1;
//print("In 1st finally catch block of addValues_4() function: sum = " + sum + ", e = " + e);
}
catch (e if e == 43)
{
sum += 1;
//print("In 2nd finally catch block of addValues_4() function: sum = " + sum + ", e = " + e);
}
finally
{
sum += 1;
//print("In finally finally block of addValues_4() function: sum = " + sum);
return sum;
}
}
}
}

status = this.inSection(10);
obj = new Object();
obj.arg1 = 1;
obj.arg2 = 2;
obj.arg3 = new Object();
obj.arg3.a = 10;
obj.arg3.b = 20;
actual = addValues_4(obj);
expect = 8;
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
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

for (var i=0; i<UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

// exitFunc ('test');
//}

},

/** @Test
File Name:         regress__164697.js
*/
test_regress__164697 : function () {
var SECTION = "regress__164697.js";
//var BUGNUMBER = 164697;
var summary = '(instance.__parent__ == constructor.__parent__)';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

expect = 'true';

runtest(this,'{}', 'Object');
runtest(this,'new Object()', 'Object');

// see https://bugzilla.mozilla.org/show_bug.cgi?id=321669
// for why this test is not contained in a function.
actual = (function (){}).__proto__ == Function.prototype;
this.reportCompare('true', actual+'',
'(function (){}).__proto__ == Function.prototype');

runtest(this,'new Function(";")', 'Function');

runtest(this,'[]', 'Array');
runtest(this,'new Array()', 'Array');

runtest(this,'""', 'String');
runtest(this,'new String()', 'String');

runtest(this,'true', 'Boolean');
runtest(this,'new Boolean()', 'Boolean');

runtest(this,'1', 'Number');
runtest(this,'new Number("1")', 'Number');

runtest(this,'new Date()', 'Date');

runtest(this,'/x/', 'RegExp');
runtest(this,'new RegExp("x")', 'RegExp');

runtest(this,'new Error()', 'Error');

function runtest(obj,myinstance, myconstructor)
{
var expr;
var actual;

try
{
expr =  '(' + myinstance + ').__parent__ == ' +
myconstructor + '.__parent__';
//printStatus(expr);
actual = eval(expr).toString();
}
catch(ex)
{
actual = ex + '';
}

obj.reportCompare(expect, actual, expr);

try
{
expr =  '(' + myinstance + ').__proto__ == ' +
myconstructor + '.prototype';
//printStatus(expr);
actual = eval(expr).toString();
}
catch(ex)
{
actual = ex + '';
}

obj.reportCompare(expect, actual, expr);
}

},

/** @Test
File Name:         regress__178722.js
*
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
test_regress__178722 : function () {
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
function f () {return arguments};
var arr5 = f();
arr5.__proto__ = Array.prototype;


status = this.inSection(5);
arr = arr5.sort();
actual = arr instanceof Array && arr.length === 0 && arr === arr5;
expect = true;
addThis();


// now do the same thing, with non-default sorting:
function g() {return 1;}

status = this.inSection('5a');
arr = arr5.sort(g);
actual = arr instanceof Array && arr.length === 0 && arr === arr5;
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
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__192465.js
* Date:    10 February 2003
* SUMMARY: Object.toSource() recursion should check stack overflow
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=192465
*
* MODIFIED: 27 February 2003
*
* We are adding an early return to this testcase, since it is causing
* big problems on Linux RedHat8! For a discussion of this issue, see
* http://bugzilla.mozilla.org/show_bug.cgi?id=174341#c24 and following.
*
*
* MODIFIED: 20 March 2003
*
* Removed the early return and changed |N| below from 1000 to 90.
* Note |make_deep_nest(N)| returns an object graph of length N(N+1).
* So the graph has now been reduced from 1,001,000 to 8190.
*
* With this reduction, the bug still manifests on my WinNT and Linux
* boxes (crash due to stack overflow). So the testcase is again of use
* on those boxes. At the same time, Linux RedHat8 boxes can now run
* the test in a reasonable amount of time.
*/
test_regress__192465 : function () {
var SECTION = "regress__192465.js";
var UBound = 0;
//var BUGNUMBER = 192465;
var summary = 'Object.toSource() recursion should check stack overflow';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


/*
* We're just testing that this script will compile and run.
* Set both |actual| and |expect| to a dummy value.
*/
status = this.inSection(1);
var N = 90;
try
{
make_deep_nest(N);
}
catch (e)
{
// An exception is OK, as the runtime can throw one in response to too deep
// recursion. We haven't crashed; good! Continue on to set the dummy values -
}
actual = 1;
expect = 1;
addThis();



//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------


/*
* EXAMPLE:
*
* If the global variable |N| is 2, then for |level| == 0, 1, 2, the return
* value of this function will be toSource() of these objects, respectively:
*
* {next:{next:END}}
* {next:{next:{next:{next:END}}}}
* {next:{next:{next:{next:{next:{next:END}}}}}}
*
*/
function make_deep_nest(level)
{
var head = {};
var cursor = head;

for (var i=0; i!=N; ++i)
{
cursor.next = {};
cursor = cursor.next;
}

cursor.toSource = function()
{
if (level != 0)
return make_deep_nest(level - 1);
return "END";
}

return head.toSource();
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

},

/** @Test
File Name:         regress__220584.js
Date:    29 Sep 2003
SUMMARY: Testing __parent__ and __proto__ of Script object

See http://bugzilla.mozilla.org/show_bug.cgi?id=220584
*/
test_regress__220584 : function () {
var SECTION = "regress__220584.js";
var UBound = 0;
//var BUGNUMBER = 220584;
var summary = 'Testing __parent__ and __proto__ of Script object';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];
var s;


// invoke |Script| as a function
status = this.inSection(1);
if (typeof Script == 'undefined')
{
this.reportCompare("Script not defined, Test skipped.",
"Script not defined, Test skipped.",
summary);
}
else
{
s = Script('1;');
actual = s instanceof Object;
expect = true;
addThis();

status = this.inSection(2);
actual = (s.__parent__ == undefined) || (s.__parent__ == null);
expect = false;
addThis();

status = this.inSection(3);
actual = (s.__proto__ == undefined) || (s.__proto__ == null);
expect = false;
addThis();

status = this.inSection(4);
actual = (s + '').length > 0;
expect = true;
addThis();

}

// invoke |Script| as a constructor
status = this.inSection(5);
if (typeof Script == 'undefined')
{
//print('Test skipped. Script not defined.');
}
else
{
s = new Script('1;');

actual = s instanceof Object;
expect = true;
addThis();

status = this.inSection(6);
actual = (s.__parent__ == undefined) || (s.__parent__ == null);
expect = false;
addThis();

status = this.inSection(7);
actual = (s.__proto__ == undefined) || (s.__proto__ == null);
expect = false;
addThis();

status = this.inSection(8);
actual = (s + '').length > 0;
expect = true;
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

/** @Test
File Name:         regress__225831.js
Date:    15 Nov 2003
SUMMARY: Stressing the byte code generator

See http://bugzilla.mozilla.org/show_bug.cgi?id=225831
*/
test_regress__225831 : function () {
var SECTION = "regress__225831.js";
var UBound = 0;
//var BUGNUMBER = 225831;
var summary = 'Stressing the byte code generator';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


function f() { return {x: 0}; }

var N = 300;
var a = new Array(N + 1);
a[N] = 10;
a[0] = 100;


status = this.inSection(1);

// build string of the form ++(a[++f().x + ++f().x + ... + ++f().x]) which
// gives ++a[N]
var str = "".concat("++(a[", repeat_str("++f().x + ", (N - 1)), "++f().x])");

// Use Script constructor instead of simple eval to test Rhino optimizer mode
// because in Rhino, eval always uses interpreted mode.
if (typeof Script == 'undefined')
{
//print('Test skipped. Script not defined.');
}
else
{
var script = new Script(str);
script();

actual = a[N];
expect = 11;
}
addThis();

status = this.inSection(2);


// build string of the form (a[f().x-- + f().x-- + ... + f().x--])--
// which should give (a[0])--
if (typeof Script == 'undefined')
{
//print('Test skipped. Script not defined.');
}
else
{
str = "".concat("(a[", repeat_str("f().x-- + ", (N - 1)), "f().x--])--");
script = new Script(str);
script();

actual = a[0];
expect = 99;
}
addThis();


status = this.inSection(3);

// build string of the form [[1], [1], ..., [1]]
if (typeof Script == 'undefined')
{
//print('Test skipped. Script not defined.');
}
else
{
str = "".concat("[", repeat_str("[1], ", (N - 1)), "[1]]");
script = new Script(str);
script();

actual = uneval(script());
expect = str;
}
addThis();


status = this.inSection(4);

// build string of the form ({1:{a:1}, 2:{a:1}, ... N:{a:1}})
if (typeof Script == 'undefined')
{
//print('Test skipped. Script not defined.');
}
else
{
str = function() {
var arr = new Array(N+1);
arr[0] = "({";
for (var i = 1; i < N; ++i) {
arr[i] = i+":{a:1}, ";
}
arr[N] = N+":{a:1}})";
return "".concat.apply("", arr);
}();

script = new Script(str);
script();

actual = uneval(script());
expect = str;
}
addThis();




//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------



function repeat_str(str, repeat_count)
{
var arr = new Array(--repeat_count);
while (repeat_count != 0)
arr[--repeat_count] = str;
return str.concat.apply(str, arr);
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

},

/** @Test
File Name:         regress__226078.js
*/
test_regress__226078 : function () {
var SECTION = "regress__226078.js";
//var BUGNUMBER = 226078;
var summary = 'Do not Crash @ js_Interpret 3127f864';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);


function SetLangHead(l){
with(p){
for(var i=0 in x)
if(getElementById("TxtH"+i)!=undefined){}
//printStatus('huh');
}
}
x=[0,1,2,3];
p={getElementById: function (id){
//printStatus(uneval(this), id);
return undefined;}
};
SetLangHead(1);

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__226507.js
* Date:    24 Nov 2003
* SUMMARY: Testing for recursion check in js_EmitTree
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=226507
* Igor's comments:
*
* "For example, with N in the test  set to 35, I got on my RedHat
* Linux 10 box a segmentation fault from js after setting the stack limit
* to 100K. When I set the stack limit to 20K I still got the segmentation fault.
* Only after -s was changed to 15K, too-deep recursion was detected:
*

~/w/js/x> ulimit -s
100
~/w/js/x> js  fintest.js
Segmentation fault
~/w/js/x> js -S $((20*1024)) fintest.js
Segmentation fault
~/w/js/x> js -S $((15*1024)) fintest.js
fintest.js:19: InternalError: too much recursion

*
* After playing with numbers it seems that while processing try/finally the
* recursion in js_Emit takes 10 times more space the corresponding recursion
* in the parser."
*
*
* Note the use of the new -S option to the JS shell to limit stack size.
* See http://bugzilla.mozilla.org/show_bug.cgi?id=225061. This in turn
* can be passed to the JS shell by the test driver's -o option, as in:
*
* perl jsDriver.pl -e smdebug -fTEST.html -o "-S 100" -l js1_5/Regress
*
*/
test_regress__226507 : function () {
var SECTION = "regress__226507.js";
var UBound = 0;
//var BUGNUMBER = 226507;
var summary = 'Testing for recursion check in js_EmitTree';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


/*
* With stack limit 100K on Linux debug build even N=30 already can cause
* stack overflow; use 35 to trigger it for sure.
*/
var N = 350;

var counter = 0;
function f()
{
++counter;
}


/*
* Example: if N were 3, this is what |source|
* would end up looking like:
*
*     try { f(); } finally {
*     try { f(); } finally {
*     try { f(); } finally {
*     f(1,1,1,1);
*     }}}
*
*/
var source = "".concat(
repeat_str("try { f(); } finally {\n", N),
"f(",
repeat_str("1,", N),
"1);\n",
repeat_str("}", N));

// Repeat it for additional stress testing
source += source;

/*
* In Rhino, eval() always uses interpreted mode.
* To use compiled mode, use Script.exec() instead.
*/
if (typeof Script == 'undefined')
{
//print('Test skipped. Script not defined.');
expect = actual = 0;
}
else
{
try
{
var script = Script(source);
script();


status = this.inSection(1);
actual = counter;
expect = (N + 1) * 2;
}
catch(ex)
{
actual = ex + '';
}
}
addThis();


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------



function repeat_str(str, repeat_count)
{
var arr = new Array(--repeat_count);
while (repeat_count != 0)
arr[--repeat_count] = str;
return str.concat.apply(str, arr);
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

},

/** @Test
File Name:         regress__231518.js
*/
test_regress__231518 : function () {
var SECTION = "regress__231518.js";
//var BUGNUMBER = 231518;
var summary = 'decompiler must quote keywords and non-identifier property names';
var actual = '';
var expect = 'no error';
var status;
var object;
var result;

//printBugNumber(BUGNUMBER);
//printStatus (summary);


if (typeof uneval != 'undefined')
{
status = this.inSection(1) + ' eval(uneval({"if": false}))';

try
{
object = {'if': false };
result = uneval(object);
//printStatus('uneval returns ' + result);
eval(result);
actual = 'no error';
}
catch(e)
{
actual = 'error';
}

this.reportCompare(expect, actual, status);

status = this.inSection(2) + ' eval(uneval({"if": "then"}))';

try
{
object = {'if': "then" };
result = uneval(object);
//printStatus('uneval returns ' + result);
eval(result);
actual = 'no error';
}
catch(e)
{
actual = 'error';
}

this.reportCompare(expect, actual, status);

status = this.inSection(3) + ' eval(uneval(f))';

try
{
result = uneval(f);
//printStatus('uneval returns ' + result);
eval(result);
actual = 'no error';
}
catch(e)
{
actual = 'error';
}

this.reportCompare(expect, actual, status);

status = this.inSection(2) + ' eval(uneval(g))';

try
{
result = uneval(g);
//printStatus('uneval returns ' + result);
eval(result);
actual = 'no error';
}
catch(e)
{
actual = 'error';
}

this.reportCompare(expect, actual, status);
}

function f()
{
var obj = new Object();

obj['name']      = 'Just a name';
obj['if']        = false;
obj['some text'] = 'correct';
}

function g()
{
return {'if': "then"};
}


},

/** @Test
File Name:         regress__237461.js
*/
test_regress__237461 : function () {
var SECTION = "regress__237461.js";
//var BUGNUMBER = 237461;
var summary = 'don\'t crash with nested function collides with var';
var actual = 'Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

function g()
{
var core = {};
core.js = {};
core.js.init = function()
{
var loader = null;

function loader() {}
};
return core;
}

if (typeof Script == 'undefined')
{
//print('Test skipped. Script not defined.');
}
else
{
var s = new Script(""+g.toString());
try
{
var frozen = s.freeze(); // crash.
//printStatus("len:" + frozen.length);
}
catch(e)
{
}
}
actual = 'No Crash';

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__245148.js
*/
test_regress__245148 : function () {
var SECTION = "regress__245148.js";
//var BUGNUMBER = 245148;
var summary = '[null].toSource() == "[null]"';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

if (typeof Array.prototype.toSource != 'undefined')
{
expect = '[null]';
actual = [null].toSource();

this.reportCompare(expect, actual, summary);
}

},

/** @Test
File Name:         regress__245795.js
*/
test_regress__245795 : function () {
var SECTION = "regress__245795.js";
//var BUGNUMBER = 245795;
var summary = 'eval(uneval(function)) should be round-trippable';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

if (typeof uneval != 'undefined')
{
function a()
{
b=function() {};
}

var r = /function a\(\) \{ b = \(?function \(\) \{\s*\}\)?; \}/;
eval(uneval(a));

var v = a.toString().replace(/[ \n]+/g, ' ');

//printStatus("[" + v + "]");

expect = true;
actual = r.test(v);

this.reportCompare(expect, actual, summary);
}

},

/** @Test
File Name:         regress__254375.js
*/
test_regress__254375 : function () {
var SECTION = "regress__254375.js";
//var BUGNUMBER = 254375;
var summary = 'Object.toSource for negative number property names';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

if (typeof uneval != 'undefined')
{
try
{
expect = 'no error';
eval(uneval({'-1':true}));
actual = 'no error';
}
catch(e)
{
actual = 'error';
}

this.reportCompare(expect, actual, summary);
}

},

/** @Test
File Name:         regress__255245.js
*/
test_regress__255245 : function () {
var SECTION = "regress__255245.js";
//var BUGNUMBER = 255245;
var summary = 'Function.prototype.toSource/.toString show "setrval" instead of "return"';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

function f() {
try {
} catch (e) {
return false;
}
finally {
}
}

if (typeof f.toSource != 'undefined')
{
expect = -1;
actual = f.toSource().indexOf('setrval');

this.reportCompare(expect, actual, summary);
}

},

/** @Test
File Name:         regress__291213.js
*/
test_regress__291213 : function () {
var SECTION = "regress__291213.js";
//var BUGNUMBER = 291213;
var summary = 'Do not crash in args_resolve enumerating |arguments|';
var actual = 'No Crash';
var expect = 'No Crash';

var scriptCode = "var result = \"\" + arguments; " +
"for (i in arguments) " +
"result += \"\\\n  \" + i + \" \" + arguments[i]; result;";
var scripts = {};

if (typeof Script == 'undefined')
{
//print('Test skipped. Script not defined.');
}
else
{
scripts["A"] = new Script(scriptCode);

scripts["B"] = (function() {
return new Script(scriptCode);
})();

scripts["C"] = (function() {
function x() { "a"; }
return new Script(scriptCode);
})();

// any Object (window, document, new Array(), ...)
var anyObj = new Object();
scripts["D"] = (function() {
function x() { anyObj; }
return new Script(scriptCode);
})();

var result;
for (var i in scripts) {
try { result = scripts[i].exec(); }
catch (e) { result = e; }
//printStatus(i + ") " + result);
}
}
this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__303277.js
*/
test_regress__303277 : function () {
var SECTION = "regress__303277.js";
//var BUGNUMBER = 303277;
var summary = 'Do not crash with crash with a watchpoint for __proto__ property ';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var o = {};
o.watch("__proto__", function(){return null;});
o.__proto__ = null;

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__304897.js
*/
test_regress__304897 : function () {
var SECTION = "regress__304897.js";
//var BUGNUMBER = 304897;
var summary = 'uneval("\\t"), uneval("\\x09")';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

expect = '"\\t"';
actual = uneval('\t');
this.reportCompare(expect, actual, summary);

actual = uneval('\x09');
this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__306738.js
*/
test_regress__306738 : function () {
var SECTION = "regress__306738.js";
//var BUGNUMBER = 306738;
var summary = 'uneval() on objects with getter or setter';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

actual = uneval(
{
get foo()
{
return "foo";
}
});

expect = '({get foo() {return "foo";}})';

this.compareSource(expect, actual, summary);

},

/** @Test
File Name:         regress__311161.js
*/
test_regress__311161 : function () {
var SECTION = "regress__311161.js";
//var BUGNUMBER = 311161;
var summary = 'toSource exposes random memory or crashes';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);


var commands =
[{origCount:1, fun:(function anonymous() {allElements[2].style.background = "#fcd";})},
{origCount:2, fun:(function anonymous() {allElements[9].style.width = "20em";})},
{origCount:3, fun:(function anonymous() {allElements[4].style.width = "200%";})},
{origCount:4, fun:(function anonymous() {allElements[6].style.clear = "right";})},
{origCount:5, fun:(function anonymous() {allElements[8].style.visibility = "hidden";})},
{origCount:6, fun:(function anonymous() {allElements[1].style.overflow = "visible";})},
{origCount:7, fun:(function anonymous() {allElements[4].style.position = "fixed";})},
{origCount:8, fun:(function anonymous() {allElements[10].style.display = "-moz-inline-stack";})},
{origCount:9, fun:(function anonymous() {allElements[10].style.overflow = "auto";})},
{origCount:10, fun:(function anonymous() {allElements[11].style.color = "red";})},
{origCount:11, fun:(function anonymous() {allElements[4].style.height = "2em";})},
{origCount:12, fun:(function anonymous() {allElements[9].style.height = "100px";})},
{origCount:13, fun:(function anonymous() {allElements[5].style['float'] = "none";})},
{origCount:14, fun:(function anonymous() {allElements[9].style.color = "blue";})},
{origCount:15, fun:(function anonymous() {allElements[2].style.clear = "right";})},
{origCount:16, fun:(function anonymous() {allElements[1].style.height = "auto";})},
{origCount:17, fun:(function anonymous() {allElements[0].style.overflow = "hidden";})},
{origCount:18, fun:(function anonymous() {allElements[4].style.display = "table-row-group";})},
{origCount:19, fun:(function anonymous() {allElements[4].style.overflow = "auto";})},
{origCount:20, fun:(function anonymous() {allElements[7].style.height = "100px";})},
{origCount:21, fun:(function anonymous() {allElements[5].style.color = "green";})},
{origCount:22, fun:(function anonymous() {allElements[3].style.display = "-moz-grid-group";})},
{origCount:23, fun:(function anonymous() {allElements[7].style['float'] = "none";})},
{origCount:24, fun:(function anonymous() {allElements[10].style.position = "static";})},
{origCount:25, fun:(function anonymous() {allElements[3].style['float'] = "none";})},
{origCount:26, fun:(function anonymous() {allElements[4].style['float'] = "none";})},
{origCount:27, fun:(function anonymous() {allElements[8].style['float'] = "none";})},
{origCount:28, fun:(function anonymous() {allElements[5].style.visibility = "collapse";})},
{origCount:29, fun:(function anonymous() {allElements[1].style.position = "static";})},
{origCount:30, fun:(function anonymous() {allElements[2].style.color = "black";})},
{origCount:31, fun:(function anonymous() {allElements[0].style.position = "fixed";})},
{origCount:32, fun:(function anonymous() {allElements[0].style.display = "table-row-group";})},
{origCount:33, fun:(function anonymous() {allElements[9].style.position = "relative";})},
{origCount:34, fun:(function anonymous() {allElements[5].style.position = "static";})},
{origCount:35, fun:(function anonymous() {allElements[6].style.background = "transparent";})},
{origCount:36, fun:(function anonymous() {allElements[6].style.color = "blue";})},
{origCount:37, fun:(function anonymous() {allElements[9].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:38, fun:(function anonymous() {allElements[8].style.display = "-moz-grid";})},
{origCount:39, fun:(function anonymous() {allElements[9].style.color = "black";})},
{origCount:40, fun:(function anonymous() {allElements[4].style.position = "static";})},
{origCount:41, fun:(function anonymous() {allElements[10].style.height = "auto";})},
{origCount:42, fun:(function anonymous() {allElements[9].style.color = "green";})},
{origCount:43, fun:(function anonymous() {allElements[4].style.height = "auto";})},
{origCount:44, fun:(function anonymous() {allElements[2].style.clear = "both";})},
{origCount:45, fun:(function anonymous() {allElements[8].style.width = "1px";})},
{origCount:46, fun:(function anonymous() {allElements[2].style.visibility = "visible";})},
{origCount:47, fun:(function anonymous() {allElements[1].style.clear = "left";})},
{origCount:48, fun:(function anonymous() {allElements[11].style.overflow = "auto";})},
{origCount:49, fun:(function anonymous() {allElements[11].style['float'] = "left";})},
{origCount:50, fun:(function anonymous() {allElements[8].style['float'] = "left";})},
{origCount:51, fun:(function anonymous() {allElements[6].style.height = "10%";})},
{origCount:52, fun:(function anonymous() {allElements[11].style.display = "-moz-inline-stack";})},
{origCount:53, fun:(function anonymous() {allElements[3].style.clear = "left";})},
{origCount:54, fun:(function anonymous() {allElements[11].style.visibility = "hidden";})},
{origCount:55, fun:(function anonymous() {allElements[4].style['float'] = "right";})},
{origCount:56, fun:(function anonymous() {allElements[0].style.width = "1px";})},
{origCount:57, fun:(function anonymous() {allElements[3].style.height = "200%";})},
{origCount:58, fun:(function anonymous() {allElements[7].style.height = "10%";})},
{origCount:59, fun:(function anonymous() {allElements[4].style.clear = "none";})},
{origCount:60, fun:(function anonymous() {allElements[11].style['float'] = "none";})},
{origCount:61, fun:(function anonymous() {allElements[9].style['float'] = "left";})},
{origCount:62, fun:(function anonymous() {allElements[4].style.overflow = "scroll";})},
{origCount:63, fun:(function anonymous() {allElements[12].style.height = "200%";})},
{origCount:64, fun:(function anonymous() {allElements[2].style.color = "green";})},
{origCount:65, fun:(function anonymous() {allElements[3].style['float'] = "none";})},
{origCount:66, fun:(function anonymous() {allElements[10].style.background = "transparent";})},
{origCount:67, fun:(function anonymous() {allElements[0].style.height = "auto";})},
{origCount:68, fun:(function anonymous() {allElements[6].style.clear = "left";})},
{origCount:69, fun:(function anonymous() {allElements[7].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:70, fun:(function anonymous() {allElements[8].style.display = "-moz-popup";})},
{origCount:71, fun:(function anonymous() {allElements[2].style.height = "10%";})},
{origCount:72, fun:(function anonymous() {allElements[7].style.display = "table-cell";})},
{origCount:73, fun:(function anonymous() {allElements[3].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:74, fun:(function anonymous() {allElements[8].style.color = "red";})},
{origCount:75, fun:(function anonymous() {allElements[1].style.overflow = "auto";})},
{origCount:76, fun:(function anonymous() {allElements[1].style.background = "#fcd";})},
{origCount:77, fun:(function anonymous() {allElements[0].style.color = "red";})},
{origCount:78, fun:(function anonymous() {allElements[4].style.background = "#fcd";})},
{origCount:79, fun:(function anonymous() {allElements[5].style.position = "static";})},
{origCount:80, fun:(function anonymous() {allElements[8].style.clear = "both";})},
{origCount:81, fun:(function anonymous() {allElements[7].style.clear = "both";})},
{origCount:82, fun:(function anonymous() {allElements[5].style.clear = "both";})},
{origCount:83, fun:(function anonymous() {allElements[10].style.display = "-moz-grid-group";})},
{origCount:84, fun:(function anonymous() {allElements[12].style.clear = "right";})},
{origCount:85, fun:(function anonymous() {allElements[5].style['float'] = "left";})},
{origCount:86, fun:(function anonymous() {allElements[8].style.position = "absolute";})},
{origCount:87, fun:(function anonymous() {allElements[11].style.background = "#fcd";})},
{origCount:88, fun:(function anonymous() {allElements[9].style.position = "relative";})},
{origCount:89, fun:(function anonymous() {allElements[5].style.width = "20em";})},
{origCount:90, fun:(function anonymous() {allElements[6].style.position = "absolute";})},
{origCount:91, fun:(function anonymous() {allElements[5].style.overflow = "scroll";})},
{origCount:92, fun:(function anonymous() {allElements[6].style.background = "#fcd";})},
{origCount:93, fun:(function anonymous() {allElements[2].style.visibility = "visible";})},
{origCount:94, fun:(function anonymous() {allElements[11].style.background = "#fcd";})},
{origCount:95, fun:(function anonymous() {allElements[0].style.visibility = "hidden";})},
{origCount:96, fun:(function anonymous() {allElements[0].style.color = "blue";})},
{origCount:97, fun:(function anonymous() {allElements[3].style['float'] = "left";})},
{origCount:98, fun:(function anonymous() {allElements[3].style.height = "200%";})},
{origCount:99, fun:(function anonymous() {allElements[4].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:100, fun:(function anonymous() {allElements[12].style.width = "10%";})},
{origCount:101, fun:(function anonymous() {allElements[6].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:102, fun:(function anonymous() {allElements[5].style.width = "auto";})},
{origCount:103, fun:(function anonymous() {allElements[1].style.position = "static";})},
{origCount:104, fun:(function anonymous() {allElements[12].style['float'] = "right";})},
{origCount:105, fun:(function anonymous() {allElements[5].style['float'] = "right";})},
{origCount:106, fun:(function anonymous() {allElements[12].style.height = "200%";})},
{origCount:107, fun:(function anonymous() {allElements[11].style['float'] = "none";})},
{origCount:108, fun:(function anonymous() {allElements[9].style.width = "20em";})},
{origCount:109, fun:(function anonymous() {allElements[10].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:110, fun:(function anonymous() {allElements[7].style['float'] = "none";})},
{origCount:111, fun:(function anonymous() {allElements[6].style.visibility = "collapse";})},
{origCount:112, fun:(function anonymous() {allElements[11].style.height = "200%";})},
{origCount:113, fun:(function anonymous() {allElements[3].style.visibility = "visible";})},
{origCount:114, fun:(function anonymous() {allElements[12].style.width = "200%";})},
{origCount:115, fun:(function anonymous() {allElements[5].style.height = "10%";})},
{origCount:116, fun:(function anonymous() {allElements[1].style['float'] = "left";})},
{origCount:117, fun:(function anonymous() {allElements[5].style.overflow = "scroll";})},
{origCount:118, fun:(function anonymous() {allElements[9].style.width = "10%";})},
{origCount:119, fun:(function anonymous() {allElements[6].style.position = "static";})},
{origCount:120, fun:(function anonymous() {allElements[1].style.background = "#fcd";})},
{origCount:121, fun:(function anonymous() {allElements[12].style['float'] = "right";})},
{origCount:122, fun:(function anonymous() {allElements[7].style.width = "1px";})},
{origCount:123, fun:(function anonymous() {allElements[3].style.color = "blue";})},
{origCount:124, fun:(function anonymous() {allElements[6].style.background = "#fcd";})},
{origCount:125, fun:(function anonymous() {allElements[8].style.overflow = "auto";})},
{origCount:126, fun:(function anonymous() {allElements[1].style.overflow = "auto";})},
{origCount:127, fun:(function anonymous() {allElements[5].style['float'] = "none";})},
{origCount:128, fun:(function anonymous() {allElements[12].style.color = "green";})},
{origCount:129, fun:(function anonymous() {allElements[0].style.color = "black";})},
{origCount:130, fun:(function anonymous() {allElements[1].style.position = "relative";})},
{origCount:131, fun:(function anonymous() {allElements[9].style.overflow = "auto";})},
{origCount:132, fun:(function anonymous() {allElements[1].style.display = "table-row";})},
{origCount:133, fun:(function anonymous() {allElements[10].style['float'] = "right";})},
{origCount:134, fun:(function anonymous() {allElements[2].style.visibility = "hidden";})},
{origCount:135, fun:(function anonymous() {allElements[9].style.overflow = "auto";})},
{origCount:136, fun:(function anonymous() {allElements[9].style.clear = "none";})},
{origCount:137, fun:(function anonymous() {allElements[9].style.position = "absolute";})},
{origCount:138, fun:(function anonymous() {allElements[0].style.width = "10%";})},
{origCount:139, fun:(function anonymous() {allElements[1].style.height = "10%";})},
{origCount:140, fun:(function anonymous() {allElements[5].style.height = "auto";})},
{origCount:141, fun:(function anonymous() {allElements[4].style.position = "fixed";})},
{origCount:142, fun:(function anonymous() {allElements[3].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:143, fun:(function anonymous() {allElements[7].style.display = "table-header-group";})},
{origCount:144, fun:(function anonymous() {allElements[10].style.position = "fixed";})},
{origCount:145, fun:(function anonymous() {allElements[4].style.background = "transparent";})},
{origCount:146, fun:(function anonymous() {allElements[6].style.position = "relative";})},
{origCount:147, fun:(function anonymous() {allElements[10].style.clear = "both";})},
{origCount:148, fun:(function anonymous() {allElements[8].style.display = "table-header-group";})},
{origCount:149, fun:(function anonymous() {allElements[5].style.height = "200%";})},
{origCount:150, fun:(function anonymous() {allElements[7].style.height = "2em";})},
{origCount:151, fun:(function anonymous() {allElements[6].style.position = "relative";})},
{origCount:152, fun:(function anonymous() {allElements[7].style.height = "2em";})},
{origCount:153, fun:(function anonymous() {allElements[3].style.width = "10%";})},
{origCount:154, fun:(function anonymous() {allElements[12].style.color = "blue";})},
{origCount:155, fun:(function anonymous() {allElements[2].style.color = "green";})},
{origCount:156, fun:(function anonymous() {allElements[2].style.visibility = "visible";})},
{origCount:157, fun:(function anonymous() {allElements[6].style['float'] = "right";})},
{origCount:158, fun:(function anonymous() {allElements[6].style.visibility = "collapse";})},
{origCount:159, fun:(function anonymous() {allElements[8].style.position = "absolute";})},
{origCount:160, fun:(function anonymous() {allElements[3].style.height = "2em";})},
{origCount:161, fun:(function anonymous() {allElements[10].style.display = "-moz-grid-line";})},
{origCount:162, fun:(function anonymous() {allElements[9].style.color = "red";})},
{origCount:163, fun:(function anonymous() {allElements[6].style.overflow = "hidden";})},
{origCount:164, fun:(function anonymous() {allElements[4].style.overflow = "scroll";})},
{origCount:165, fun:(function anonymous() {allElements[11].style.height = "100px";})},
{origCount:166, fun:(function anonymous() {allElements[5].style.display = "table-footer-group";})},
{origCount:167, fun:(function anonymous() {allElements[5].style.color = "red";})},
{origCount:168, fun:(function anonymous() {allElements[3].style.width = "20em";})},
{origCount:169, fun:(function anonymous() {allElements[4].style['float'] = "right";})},
{origCount:170, fun:(function anonymous() {allElements[2].style.background = "transparent";})},
{origCount:171, fun:(function anonymous() {allElements[0].style.position = "fixed";})},
{origCount:172, fun:(function anonymous() {allElements[6].style.visibility = "hidden";})},
{origCount:173, fun:(function anonymous() {allElements[11].style['float'] = "right";})},
{origCount:174, fun:(function anonymous() {allElements[8].style.height = "200%";})},
{origCount:175, fun:(function anonymous() {allElements[1].style.position = "relative";})},
{origCount:176, fun:(function anonymous() {allElements[11].style.width = "auto";})},
{origCount:177, fun:(function anonymous() {allElements[2].style.background = "#fcd";})},
{origCount:178, fun:(function anonymous() {allElements[6].style.position = "absolute";})},
{origCount:179, fun:(function anonymous() {allElements[3].style.position = "absolute";})},
{origCount:180, fun:(function anonymous() {allElements[12].style['float'] = "right";})},
{origCount:181, fun:(function anonymous() {allElements[11].style.background = "transparent";})},
{origCount:182, fun:(function anonymous() {allElements[6].style.height = "200%";})},
{origCount:183, fun:(function anonymous() {allElements[2].style['float'] = "none";})},
{origCount:184, fun:(function anonymous() {allElements[5].style.position = "absolute";})},
{origCount:185, fun:(function anonymous() {allElements[8].style.color = "blue";})},
{origCount:186, fun:(function anonymous() {allElements[2].style['float'] = "left";})},
{origCount:187, fun:(function anonymous() {allElements[6].style.height = "200%";})},
{origCount:188, fun:(function anonymous() {allElements[0].style.width = "20em";})},
{origCount:189, fun:(function anonymous() {allElements[1].style.display = "table-row-group";})},
{origCount:190, fun:(function anonymous() {allElements[3].style.visibility = "hidden";})},
{origCount:191, fun:(function anonymous() {allElements[11].style.width = "10%";})},
{origCount:192, fun:(function anonymous() {allElements[4].style.width = "200%";})},
{origCount:193, fun:(function anonymous() {allElements[0].style['float'] = "right";})},
{origCount:194, fun:(function anonymous() {allElements[5].style.background = "#fcd";})},
{origCount:195, fun:(function anonymous() {allElements[12].style.visibility = "hidden";})},
{origCount:196, fun:(function anonymous() {allElements[0].style.display = "table-column";})},
{origCount:197, fun:(function anonymous() {allElements[0].style.width = "auto";})},
{origCount:198, fun:(function anonymous() {allElements[4].style.color = "green";})},
{origCount:199, fun:(function anonymous() {allElements[6].style.clear = "none";})},
{origCount:200, fun:(function anonymous() {allElements[10].style.overflow = "hidden";})},
{origCount:201, fun:(function anonymous() {allElements[9].style.visibility = "collapse";})},
{origCount:202, fun:(function anonymous() {allElements[9].style.height = "100px";})},
{origCount:203, fun:(function anonymous() {allElements[1].style.width = "auto";})},
{origCount:204, fun:(function anonymous() {allElements[4].style.position = "fixed";})},
{origCount:205, fun:(function anonymous() {allElements[11].style['float'] = "none";})},
{origCount:206, fun:(function anonymous() {allElements[1].style.clear = "right";})},
{origCount:207, fun:(function anonymous() {allElements[5].style.display = "-moz-stack";})},
{origCount:208, fun:(function anonymous() {allElements[3].style.color = "black";})},
{origCount:209, fun:(function anonymous() {allElements[1].style.background = "transparent";})},
{origCount:210, fun:(function anonymous() {allElements[3].style['float'] = "left";})},
{origCount:211, fun:(function anonymous() {allElements[2].style.height = "2em";})},
{origCount:212, fun:(function anonymous() {allElements[4].style.width = "auto";})},
{origCount:213, fun:(function anonymous() {allElements[0].style['float'] = "none";})},
{origCount:214, fun:(function anonymous() {allElements[10].style.display = "table-caption";})},
{origCount:215, fun:(function anonymous() {allElements[0].style.overflow = "auto";})},
{origCount:216, fun:(function anonymous() {allElements[0].style.color = "green";})},
{origCount:217, fun:(function anonymous() {allElements[5].style.background = "#fcd";})},
{origCount:218, fun:(function anonymous() {allElements[5].style.visibility = "hidden";})},
{origCount:219, fun:(function anonymous() {allElements[7].style.width = "200%";})},
{origCount:220, fun:(function anonymous() {allElements[2].style.background = "transparent";})},
{origCount:221, fun:(function anonymous() {allElements[10].style.visibility = "hidden";})},
{origCount:222, fun:(function anonymous() {allElements[10].style['float'] = "right";})},
{origCount:223, fun:(function anonymous() {allElements[6].style.position = "absolute";})},
{origCount:224, fun:(function anonymous() {allElements[5].style.background = "transparent";})},
{origCount:225, fun:(function anonymous() {allElements[12].style.overflow = "hidden";})},
{origCount:226, fun:(function anonymous() {allElements[7].style.clear = "left";})},
{origCount:227, fun:(function anonymous() {allElements[7].style.height = "200%";})},
{origCount:228, fun:(function anonymous() {allElements[5].style.position = "absolute";})},
{origCount:229, fun:(function anonymous() {allElements[7].style['float'] = "none";})},
{origCount:230, fun:(function anonymous() {allElements[5].style.clear = "both";})},
{origCount:231, fun:(function anonymous() {allElements[4].style.clear = "left";})},
{origCount:232, fun:(function anonymous() {allElements[10].style.position = "fixed";})},
{origCount:233, fun:(function anonymous() {allElements[2].style.overflow = "scroll";})},
{origCount:234, fun:(function anonymous() {allElements[12].style.background = "#fcd";})},
{origCount:235, fun:(function anonymous() {allElements[6].style.color = "black";})},
{origCount:236, fun:(function anonymous() {allElements[3].style.position = "absolute";})},
{origCount:237, fun:(function anonymous() {allElements[8].style.color = "red";})},
{origCount:238, fun:(function anonymous() {allElements[12].style.background = "transparent";})},
{origCount:239, fun:(function anonymous() {allElements[10].style['float'] = "none";})},
{origCount:240, fun:(function anonymous() {allElements[6].style['float'] = "right";})},
{origCount:241, fun:(function anonymous() {allElements[5].style['float'] = "none";})},
{origCount:242, fun:(function anonymous() {allElements[0].style.color = "red";})},
{origCount:243, fun:(function anonymous() {allElements[10].style['float'] = "none";})},
{origCount:244, fun:(function anonymous() {allElements[1].style.width = "1px";})},
{origCount:245, fun:(function anonymous() {allElements[3].style.position = "fixed";})},
{origCount:246, fun:(function anonymous() {allElements[11].style.clear = "left";})},
{origCount:247, fun:(function anonymous() {allElements[2].style.position = "absolute";})},
{origCount:248, fun:(function anonymous() {allElements[9].style.background = "#fcd";})},
{origCount:249, fun:(function anonymous() {allElements[11].style.position = "relative";})},
{origCount:250, fun:(function anonymous() {allElements[1].style.height = "100px";})},
{origCount:251, fun:(function anonymous() {allElements[9].style.background = "transparent";})},
{origCount:252, fun:(function anonymous() {allElements[2].style.display = "block";})},
{origCount:253, fun:(function anonymous() {allElements[12].style.background = "#fcd";})},
{origCount:254, fun:(function anonymous() {allElements[4].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:255, fun:(function anonymous() {allElements[12].style.color = "black";})},
{origCount:256, fun:(function anonymous() {allElements[0].style.height = "auto";})},
{origCount:257, fun:(function anonymous() {allElements[0].style.height = "100px";})},
{origCount:258, fun:(function anonymous() {allElements[5].style.clear = "right";})},
{origCount:259, fun:(function anonymous() {allElements[7].style.height = "100px";})},
{origCount:260, fun:(function anonymous() {allElements[11].style.background = "transparent";})},
{origCount:261, fun:(function anonymous() {allElements[11].style.width = "20em";})},
{origCount:262, fun:(function anonymous() {allElements[10].style.width = "1px";})},
{origCount:263, fun:(function anonymous() {allElements[3].style.clear = "left";})},
{origCount:264, fun:(function anonymous() {allElements[7].style['float'] = "left";})},
{origCount:265, fun:(function anonymous() {allElements[1].style['float'] = "none";})},
{origCount:266, fun:(function anonymous() {allElements[4].style.overflow = "scroll";})},
{origCount:267, fun:(function anonymous() {allElements[9].style.height = "auto";})},
{origCount:268, fun:(function anonymous() {allElements[7].style.background = "transparent";})},
{origCount:269, fun:(function anonymous() {allElements[5].style.display = "table";})},
{origCount:270, fun:(function anonymous() {allElements[7].style.width = "200%";})},
{origCount:271, fun:(function anonymous() {allElements[7].style.clear = "left";})},
{origCount:272, fun:(function anonymous() {allElements[9].style.visibility = "hidden";})},
{origCount:273, fun:(function anonymous() {allElements[6].style.height = "10%";})},
{origCount:274, fun:(function anonymous() {allElements[3].style.position = "fixed";})},
{origCount:275, fun:(function anonymous() {allElements[6].style.display = "block";})},
{origCount:276, fun:(function anonymous() {allElements[7].style.overflow = "visible";})},
{origCount:277, fun:(function anonymous() {allElements[12].style['float'] = "none";})},
{origCount:278, fun:(function anonymous() {allElements[0].style['float'] = "none";})},
{origCount:279, fun:(function anonymous() {allElements[2].style.height = "10%";})},
{origCount:280, fun:(function anonymous() {allElements[11].style.clear = "right";})},
{origCount:281, fun:(function anonymous() {allElements[6].style.clear = "both";})},
{origCount:282, fun:(function anonymous() {allElements[6].style.display = "-moz-box";})},
{origCount:283, fun:(function anonymous() {allElements[3].style.height = "100px";})},
{origCount:284, fun:(function anonymous() {allElements[2].style.color = "blue";})},
{origCount:285, fun:(function anonymous() {allElements[10].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:286, fun:(function anonymous() {allElements[4].style.background = "transparent";})},
{origCount:287, fun:(function anonymous() {allElements[5].style.height = "auto";})},
{origCount:288, fun:(function anonymous() {allElements[3].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:289, fun:(function anonymous() {allElements[5].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:290, fun:(function anonymous() {allElements[4].style.clear = "right";})},
{origCount:291, fun:(function anonymous() {allElements[3].style.overflow = "auto";})},
{origCount:292, fun:(function anonymous() {allElements[10].style.display = "-moz-stack";})},
{origCount:293, fun:(function anonymous() {allElements[2].style.color = "red";})},
{origCount:294, fun:(function anonymous() {allElements[0].style.display = "-moz-groupbox";})},
{origCount:295, fun:(function anonymous() {allElements[7].style.position = "fixed";})},
{origCount:296, fun:(function anonymous() {allElements[4].style.color = "green";})},
{origCount:297, fun:(function anonymous() {allElements[9].style.display = "-moz-box";})},
{origCount:298, fun:(function anonymous() {allElements[1].style.color = "green";})},
{origCount:299, fun:(function anonymous() {allElements[12].style.visibility = "hidden";})},
{origCount:300, fun:(function anonymous() {allElements[8].style.color = "red";})},
{origCount:301, fun:(function anonymous() {allElements[8].style['float'] = "left";})},
{origCount:302, fun:(function anonymous() {allElements[3].style.height = "2em";})},
{origCount:303, fun:(function anonymous() {allElements[1].style.width = "auto";})},
{origCount:304, fun:(function anonymous() {allElements[4].style.height = "10%";})},
{origCount:305, fun:(function anonymous() {allElements[8].style.width = "20em";})},
{origCount:306, fun:(function anonymous() {allElements[2].style.height = "2em";})},
{origCount:307, fun:(function anonymous() {allElements[7].style.color = "red";})},
{origCount:308, fun:(function anonymous() {allElements[2].style.display = "-moz-inline-box";})},
{origCount:309, fun:(function anonymous() {allElements[4].style.visibility = "visible";})},
{origCount:310, fun:(function anonymous() {allElements[7].style.display = "-moz-deck";})},
{origCount:311, fun:(function anonymous() {allElements[2].style.visibility = "hidden";})},
{origCount:312, fun:(function anonymous() {allElements[9].style.clear = "both";})},
{origCount:313, fun:(function anonymous() {allElements[6].style['float'] = "left";})},
{origCount:314, fun:(function anonymous() {allElements[12].style.position = "static";})},
{origCount:315, fun:(function anonymous() {allElements[6].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:316, fun:(function anonymous() {allElements[8].style.visibility = "visible";})},
{origCount:317, fun:(function anonymous() {allElements[8].style.background = "#fcd";})},
{origCount:318, fun:(function anonymous() {allElements[1].style.visibility = "collapse";})},
{origCount:319, fun:(function anonymous() {allElements[3].style.position = "static";})},
{origCount:320, fun:(function anonymous() {allElements[8].style.overflow = "hidden";})},
{origCount:321, fun:(function anonymous() {allElements[8].style.clear = "left";})},
{origCount:322, fun:(function anonymous() {allElements[8].style.position = "static";})},
{origCount:323, fun:(function anonymous() {allElements[1].style['float'] = "none";})},
{origCount:324, fun:(function anonymous() {allElements[5].style.visibility = "hidden";})},
{origCount:325, fun:(function anonymous() {allElements[12].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:326, fun:(function anonymous() {allElements[3].style.overflow = "visible";})},
{origCount:327, fun:(function anonymous() {allElements[8].style.visibility = "collapse";})},
{origCount:328, fun:(function anonymous() {allElements[7].style.position = "static";})},
{origCount:329, fun:(function anonymous() {allElements[5].style.visibility = "collapse";})},
{origCount:330, fun:(function anonymous() {allElements[8].style.visibility = "visible";})},
{origCount:331, fun:(function anonymous() {allElements[8].style.height = "auto";})},
{origCount:332, fun:(function anonymous() {allElements[10].style.overflow = "scroll";})},
{origCount:333, fun:(function anonymous() {allElements[7].style.overflow = "visible";})},
{origCount:334, fun:(function anonymous() {allElements[5].style.visibility = "visible";})},
{origCount:335, fun:(function anonymous() {allElements[8].style.position = "fixed";})},
{origCount:336, fun:(function anonymous() {allElements[10].style.display = "-moz-grid-line";})},
{origCount:337, fun:(function anonymous() {allElements[2].style['float'] = "left";})},
{origCount:338, fun:(function anonymous() {allElements[3].style.position = "absolute";})},
{origCount:339, fun:(function anonymous() {allElements[5].style.color = "green";})},
{origCount:340, fun:(function anonymous() {allElements[2].style.display = "-moz-groupbox";})},
{origCount:341, fun:(function anonymous() {allElements[10].style.overflow = "auto";})},
{origCount:342, fun:(function anonymous() {allElements[10].style['float'] = "left";})},
{origCount:343, fun:(function anonymous() {allElements[8].style.clear = "both";})},
{origCount:344, fun:(function anonymous() {allElements[8].style.clear = "right";})},
{origCount:345, fun:(function anonymous() {allElements[2].style.color = "blue";})},
{origCount:346, fun:(function anonymous() {allElements[10].style.height = "10%";})},
{origCount:347, fun:(function anonymous() {allElements[11].style.overflow = "hidden";})},
{origCount:348, fun:(function anonymous() {allElements[10].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:349, fun:(function anonymous() {allElements[0].style['float'] = "left";})},
{origCount:350, fun:(function anonymous() {allElements[11].style.width = "10%";})},
{origCount:351, fun:(function anonymous() {allElements[11].style.overflow = "hidden";})},
{origCount:352, fun:(function anonymous() {allElements[5].style.color = "green";})},
{origCount:353, fun:(function anonymous() {allElements[11].style.position = "relative";})},
{origCount:354, fun:(function anonymous() {allElements[9].style.position = "static";})},
{origCount:355, fun:(function anonymous() {allElements[4].style.height = "10%";})},
{origCount:356, fun:(function anonymous() {allElements[1].style.position = "fixed";})},
{origCount:357, fun:(function anonymous() {allElements[6].style.position = "fixed";})},
{origCount:358, fun:(function anonymous() {allElements[12].style.display = "block";})},
{origCount:359, fun:(function anonymous() {allElements[10].style.display = "-moz-inline-block";})},
{origCount:360, fun:(function anonymous() {allElements[6].style.height = "100px";})},
{origCount:361, fun:(function anonymous() {allElements[6].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:362, fun:(function anonymous() {allElements[2].style['float'] = "right";})},
{origCount:363, fun:(function anonymous() {allElements[0].style.display = "-moz-grid-group";})},
{origCount:364, fun:(function anonymous() {allElements[4].style.background = "#fcd";})},
{origCount:365, fun:(function anonymous() {allElements[8].style['float'] = "none";})},
{origCount:366, fun:(function anonymous() {allElements[3].style.position = "relative";})},
{origCount:367, fun:(function anonymous() {allElements[8].style.position = "static";})},
{origCount:368, fun:(function anonymous() {allElements[3].style.position = "relative";})},
{origCount:369, fun:(function anonymous() {allElements[5].style.width = "auto";})},
{origCount:370, fun:(function anonymous() {allElements[8].style.clear = "none";})},
{origCount:371, fun:(function anonymous() {allElements[4].style.color = "red";})},
{origCount:372, fun:(function anonymous() {allElements[11].style.width = "auto";})},
{origCount:373, fun:(function anonymous() {allElements[9].style['float'] = "right";})},
{origCount:374, fun:(function anonymous() {allElements[2].style.width = "20em";})},
{origCount:375, fun:(function anonymous() {allElements[10].style.position = "relative";})},
{origCount:376, fun:(function anonymous() {allElements[12].style.position = "relative";})},
{origCount:377, fun:(function anonymous() {allElements[0].style.display = "-moz-grid";})},
{origCount:378, fun:(function anonymous() {allElements[5].style.clear = "left";})},
{origCount:379, fun:(function anonymous() {allElements[8].style.color = "green";})},
{origCount:380, fun:(function anonymous() {allElements[0].style.clear = "both";})},
{origCount:381, fun:(function anonymous() {allElements[0].style['float'] = "left";})},
{origCount:382, fun:(function anonymous() {allElements[1].style.background = "#fcd";})},
{origCount:383, fun:(function anonymous() {allElements[7].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:384, fun:(function anonymous() {allElements[12].style.visibility = "hidden";})},
{origCount:385, fun:(function anonymous() {allElements[7].style['float'] = "right";})},
{origCount:386, fun:(function anonymous() {allElements[11].style.display = "table-row";})},
{origCount:387, fun:(function anonymous() {allElements[3].style.position = "absolute";})},
{origCount:388, fun:(function anonymous() {allElements[2].style.height = "200%";})},
{origCount:389, fun:(function anonymous() {allElements[1].style.clear = "none";})},
{origCount:390, fun:(function anonymous() {allElements[4].style.position = "static";})},
{origCount:391, fun:(function anonymous() {allElements[4].style.position = "relative";})},
{origCount:392, fun:(function anonymous() {allElements[7].style.position = "fixed";})},
{origCount:393, fun:(function anonymous() {allElements[4].style.background = "transparent";})},
{origCount:394, fun:(function anonymous() {allElements[2].style.height = "200%";})},
{origCount:395, fun:(function anonymous() {allElements[6].style.position = "relative";})},
{origCount:396, fun:(function anonymous() {allElements[8].style.overflow = "auto";})},
{origCount:397, fun:(function anonymous() {allElements[0].style.background = "transparent";})},
{origCount:398, fun:(function anonymous() {allElements[2].style.position = "static";})},
{origCount:399, fun:(function anonymous() {allElements[4].style['float'] = "none";})},
{origCount:400, fun:(function anonymous() {allElements[1].style.height = "200%";})},
{origCount:401, fun:(function anonymous() {allElements[10].style.color = "green";})},
{origCount:402, fun:(function anonymous() {allElements[11].style.overflow = "hidden";})},
{origCount:403, fun:(function anonymous() {allElements[8].style.height = "200%";})},
{origCount:404, fun:(function anonymous() {allElements[9].style.visibility = "hidden";})},
{origCount:405, fun:(function anonymous() {allElements[4].style.display = "block";})},
{origCount:406, fun:(function anonymous() {allElements[12].style.height = "200%";})},
{origCount:407, fun:(function anonymous() {allElements[0].style.width = "auto";})},
{origCount:408, fun:(function anonymous() {allElements[0].style.position = "static";})},
{origCount:409, fun:(function anonymous() {allElements[2].style['float'] = "right";})},
{origCount:410, fun:(function anonymous() {allElements[1].style.display = "-moz-grid-group";})},
{origCount:411, fun:(function anonymous() {allElements[2].style.visibility = "hidden";})},
{origCount:412, fun:(function anonymous() {allElements[9].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:413, fun:(function anonymous() {allElements[2].style.width = "auto";})},
{origCount:414, fun:(function anonymous() {allElements[0].style.display = "-moz-inline-box";})},
{origCount:415, fun:(function anonymous() {allElements[9].style.clear = "none";})},
{origCount:416, fun:(function anonymous() {allElements[6].style['float'] = "none";})},
{origCount:417, fun:(function anonymous() {allElements[12].style.visibility = "hidden";})},
{origCount:418, fun:(function anonymous() {allElements[5].style.position = "absolute";})},
{origCount:419, fun:(function anonymous() {allElements[3].style.width = "1px";})},
{origCount:420, fun:(function anonymous() {allElements[0].style.height = "2em";})},
{origCount:421, fun:(function anonymous() {allElements[0].style['float'] = "right";})},
{origCount:422, fun:(function anonymous() {allElements[10].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:423, fun:(function anonymous() {allElements[8].style.display = "-moz-inline-box";})},
{origCount:424, fun:(function anonymous() {allElements[12].style.clear = "none";})},
{origCount:425, fun:(function anonymous() {allElements[3].style.background = "transparent";})},
{origCount:426, fun:(function anonymous() {allElements[12].style.overflow = "scroll";})},
{origCount:427, fun:(function anonymous() {allElements[4].style.height = "200%";})},
{origCount:428, fun:(function anonymous() {allElements[12].style.visibility = "collapse";})},
{origCount:429, fun:(function anonymous() {allElements[2].style.clear = "right";})},
{origCount:430, fun:(function anonymous() {allElements[6].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:431, fun:(function anonymous() {allElements[2].style.color = "blue";})},
{origCount:432, fun:(function anonymous() {allElements[9].style.clear = "right";})},
{origCount:433, fun:(function anonymous() {allElements[7].style.background = "transparent";})},
{origCount:434, fun:(function anonymous() {allElements[1].style.width = "10%";})},
{origCount:435, fun:(function anonymous() {allElements[9].style.width = "10%";})},
{origCount:436, fun:(function anonymous() {allElements[11].style.display = "table-column-group";})},
{origCount:437, fun:(function anonymous() {allElements[0].style.visibility = "visible";})},
{origCount:438, fun:(function anonymous() {allElements[6].style.color = "black";})},
{origCount:439, fun:(function anonymous() {allElements[9].style.position = "relative";})},
{origCount:440, fun:(function anonymous() {allElements[1].style.visibility = "hidden";})},
{origCount:441, fun:(function anonymous() {allElements[2].style.overflow = "hidden";})},
{origCount:442, fun:(function anonymous() {allElements[3].style.color = "black";})},
{origCount:443, fun:(function anonymous() {allElements[9].style.height = "200%";})},
{origCount:444, fun:(function anonymous() {allElements[1].style.height = "200%";})},
{origCount:445, fun:(function anonymous() {allElements[9].style['float'] = "right";})},
{origCount:446, fun:(function anonymous() {allElements[1].style.color = "green";})},
{origCount:447, fun:(function anonymous() {allElements[6].style.clear = "left";})},
{origCount:448, fun:(function anonymous() {allElements[6].style.height = "2em";})},
{origCount:449, fun:(function anonymous() {allElements[5].style.overflow = "visible";})},
{origCount:450, fun:(function anonymous() {allElements[8].style.visibility = "collapse";})},
{origCount:451, fun:(function anonymous() {allElements[9].style.color = "blue";})},
{origCount:452, fun:(function anonymous() {allElements[12].style.height = "200%";})},
{origCount:453, fun:(function anonymous() {allElements[10].style.color = "red";})},
{origCount:454, fun:(function anonymous() {allElements[8].style.display = "table-cell";})},
{origCount:455, fun:(function anonymous() {allElements[12].style['float'] = "right";})},
{origCount:456, fun:(function anonymous() {allElements[2].style.overflow = "auto";})},
{origCount:457, fun:(function anonymous() {allElements[7].style['float'] = "none";})},
{origCount:458, fun:(function anonymous() {allElements[9].style.clear = "left";})},
{origCount:459, fun:(function anonymous() {allElements[12].style.clear = "right";})},
{origCount:460, fun:(function anonymous() {allElements[9].style.position = "absolute";})},
{origCount:461, fun:(function anonymous() {allElements[6].style.position = "fixed";})},
{origCount:462, fun:(function anonymous() {allElements[7].style.color = "blue";})},
{origCount:463, fun:(function anonymous() {allElements[5].style.position = "absolute";})},
{origCount:464, fun:(function anonymous() {allElements[5].style.display = "-moz-popup";})},
{origCount:465, fun:(function anonymous() {allElements[1].style.position = "static";})},
{origCount:466, fun:(function anonymous() {allElements[9].style.position = "absolute";})},
{origCount:467, fun:(function anonymous() {allElements[11].style.background = "transparent";})},
{origCount:468, fun:(function anonymous() {allElements[11].style.background = "#fcd";})},
{origCount:469, fun:(function anonymous() {allElements[1].style.background = "#fcd";})},
{origCount:470, fun:(function anonymous() {allElements[0].style.display = "table-row";})},
{origCount:471, fun:(function anonymous() {allElements[1].style.background = "#fcd";})},
{origCount:472, fun:(function anonymous() {allElements[8].style.position = "fixed";})},
{origCount:473, fun:(function anonymous() {allElements[2].style['float'] = "left";})},
{origCount:474, fun:(function anonymous() {allElements[1].style.color = "red";})},
{origCount:475, fun:(function anonymous() {allElements[9].style.height = "2em";})},
{origCount:476, fun:(function anonymous() {allElements[7].style.display = "-moz-grid";})},
{origCount:477, fun:(function anonymous() {allElements[0].style.height = "2em";})},
{origCount:478, fun:(function anonymous() {allElements[6].style.position = "absolute";})},
{origCount:479, fun:(function anonymous() {allElements[5].style.clear = "none";})},
{origCount:480, fun:(function anonymous() {allElements[3].style.overflow = "hidden";})},
{origCount:481, fun:(function anonymous() {allElements[3].style['float'] = "none";})},
{origCount:482, fun:(function anonymous() {allElements[0].style['float'] = "none";})},
{origCount:483, fun:(function anonymous() {allElements[11].style.height = "100px";})},
{origCount:484, fun:(function anonymous() {allElements[3].style.display = "-moz-inline-grid";})},
{origCount:485, fun:(function anonymous() {allElements[7].style.display = "block";})},
{origCount:486, fun:(function anonymous() {allElements[3].style.visibility = "visible";})},
{origCount:487, fun:(function anonymous() {allElements[9].style.clear = "left";})},
{origCount:488, fun:(function anonymous() {allElements[5].style.width = "200%";})},
{origCount:489, fun:(function anonymous() {allElements[8].style['float'] = "right";})},
{origCount:490, fun:(function anonymous() {allElements[12].style.height = "100px";})},
{origCount:491, fun:(function anonymous() {allElements[8].style.display = "-moz-deck";})},
{origCount:492, fun:(function anonymous() {allElements[3].style.clear = "right";})},
{origCount:493, fun:(function anonymous() {allElements[1].style['float'] = "none";})},
{origCount:494, fun:(function anonymous() {allElements[8].style.overflow = "visible";})},
{origCount:495, fun:(function anonymous() {allElements[4].style.height = "10%";})},
{origCount:496, fun:(function anonymous() {allElements[7].style.color = "red";})},
{origCount:497, fun:(function anonymous() {allElements[8].style.clear = "right";})},
{origCount:498, fun:(function anonymous() {allElements[2].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:499, fun:(function anonymous() {allElements[5].style.height = "100px";})},
{origCount:500, fun:(function anonymous() {allElements[11].style.clear = "none";})},
{origCount:501, fun:(function anonymous() {allElements[12].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:502, fun:(function anonymous() {allElements[0].style.display = "-moz-grid";})},
{origCount:503, fun:(function anonymous() {allElements[7].style.height = "100px";})},
{origCount:504, fun:(function anonymous() {allElements[12].style.visibility = "visible";})},
{origCount:505, fun:(function anonymous() {allElements[8].style.background = "#fcd";})},
{origCount:506, fun:(function anonymous() {allElements[0].style.color = "black";})},
{origCount:507, fun:(function anonymous() {allElements[6].style.overflow = "hidden";})},
{origCount:508, fun:(function anonymous() {allElements[6].style.background = "transparent";})},
{origCount:509, fun:(function anonymous() {allElements[5].style.color = "black";})},
{origCount:510, fun:(function anonymous() {allElements[9].style.background = "transparent";})},
{origCount:511, fun:(function anonymous() {allElements[10].style.position = "fixed";})},
{origCount:512, fun:(function anonymous() {allElements[0].style.clear = "right";})},
{origCount:513, fun:(function anonymous() {allElements[11].style.display = "table-caption";})},
{origCount:514, fun:(function anonymous() {allElements[10].style.clear = "right";})},
{origCount:515, fun:(function anonymous() {allElements[1].style.visibility = "hidden";})},
{origCount:516, fun:(function anonymous() {allElements[4].style.clear = "left";})},
{origCount:517, fun:(function anonymous() {allElements[10].style['float'] = "none";})},
{origCount:518, fun:(function anonymous() {allElements[12].style.overflow = "scroll";})},
{origCount:519, fun:(function anonymous() {allElements[3].style.width = "1px";})},
{origCount:520, fun:(function anonymous() {allElements[0].style.position = "fixed";})},
{origCount:521, fun:(function anonymous() {allElements[10].style.height = "200%";})},
{origCount:522, fun:(function anonymous() {allElements[11].style.position = "relative";})},
{origCount:523, fun:(function anonymous() {allElements[10].style.color = "black";})},
{origCount:524, fun:(function anonymous() {allElements[11].style.background = "transparent";})},
{origCount:525, fun:(function anonymous() {allElements[6].style.visibility = "collapse";})},
{origCount:526, fun:(function anonymous() {allElements[3].style.background = "transparent";})},
{origCount:527, fun:(function anonymous() {allElements[4].style.visibility = "visible";})},
{origCount:528, fun:(function anonymous() {allElements[5].style.background = "transparent";})},
{origCount:529, fun:(function anonymous() {allElements[8].style['float'] = "none";})},
{origCount:530, fun:(function anonymous() {allElements[8].style.height = "auto";})},
{origCount:531, fun:(function anonymous() {allElements[9].style.background = "#fcd";})},
{origCount:532, fun:(function anonymous() {allElements[4].style.height = "auto";})},
{origCount:533, fun:(function anonymous() {allElements[11].style.background = "#fcd";})},
{origCount:534, fun:(function anonymous() {allElements[10].style.width = "20em";})},
{origCount:535, fun:(function anonymous() {allElements[6].style.position = "fixed";})},
{origCount:536, fun:(function anonymous() {allElements[4].style['float'] = "left";})},
{origCount:537, fun:(function anonymous() {allElements[10].style.clear = "none";})},
{origCount:538, fun:(function anonymous() {allElements[4].style.height = "auto";})},
{origCount:539, fun:(function anonymous() {allElements[3].style.clear = "right";})},
{origCount:540, fun:(function anonymous() {allElements[1].style.width = "200%";})},
{origCount:541, fun:(function anonymous() {allElements[2].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:542, fun:(function anonymous() {allElements[12].style.clear = "left";})},
{origCount:543, fun:(function anonymous() {allElements[10].style.visibility = "hidden";})},
{origCount:544, fun:(function anonymous() {allElements[3].style.height = "auto";})},
{origCount:545, fun:(function anonymous() {allElements[7].style.visibility = "collapse";})},
{origCount:546, fun:(function anonymous() {allElements[4].style.width = "auto";})},
{origCount:547, fun:(function anonymous() {allElements[10].style.height = "auto";})},
{origCount:548, fun:(function anonymous() {allElements[6].style['float'] = "none";})},
{origCount:549, fun:(function anonymous() {allElements[10].style.overflow = "auto";})},
{origCount:550, fun:(function anonymous() {allElements[1].style.height = "auto";})},
{origCount:551, fun:(function anonymous() {allElements[11].style.overflow = "hidden";})},
{origCount:552, fun:(function anonymous() {allElements[6].style.background = "transparent";})},
{origCount:553, fun:(function anonymous() {allElements[4].style['float'] = "left";})},
{origCount:554, fun:(function anonymous() {allElements[12].style.height = "200%";})},
{origCount:555, fun:(function anonymous() {allElements[8].style.color = "green";})},
{origCount:556, fun:(function anonymous() {allElements[10].style.background = "#fcd";})},
{origCount:557, fun:(function anonymous() {allElements[0].style.overflow = "hidden";})},
{origCount:558, fun:(function anonymous() {allElements[6].style.overflow = "hidden";})},
{origCount:559, fun:(function anonymous() {allElements[10].style.clear = "right";})},
{origCount:560, fun:(function anonymous() {allElements[3].style.background = "transparent";})},
{origCount:561, fun:(function anonymous() {allElements[5].style.color = "green";})},
{origCount:562, fun:(function anonymous() {allElements[6].style.position = "static";})},
{origCount:563, fun:(function anonymous() {allElements[1].style.overflow = "hidden";})},
{origCount:564, fun:(function anonymous() {allElements[6].style.display = "inline";})},
{origCount:565, fun:(function anonymous() {allElements[2].style['float'] = "left";})},
{origCount:566, fun:(function anonymous() {allElements[7].style.visibility = "visible";})},
{origCount:567, fun:(function anonymous() {allElements[1].style.color = "blue";})},
{origCount:568, fun:(function anonymous() {allElements[1].style.clear = "both";})},
{origCount:569, fun:(function anonymous() {allElements[0].style.position = "relative";})},
{origCount:570, fun:(function anonymous() {allElements[5].style.height = "100px";})},
{origCount:571, fun:(function anonymous() {allElements[6].style.height = "auto";})},
{origCount:572, fun:(function anonymous() {allElements[10].style['float'] = "left";})},
{origCount:573, fun:(function anonymous() {allElements[8].style.position = "absolute";})},
{origCount:574, fun:(function anonymous() {allElements[7].style.background = "#fcd";})},
{origCount:575, fun:(function anonymous() {allElements[12].style.display = "-moz-popup";})},
{origCount:576, fun:(function anonymous() {allElements[2].style.position = "absolute";})},
{origCount:577, fun:(function anonymous() {allElements[9].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:578, fun:(function anonymous() {allElements[11].style.overflow = "visible";})},
{origCount:579, fun:(function anonymous() {allElements[2].style.display = "-moz-inline-grid";})},
{origCount:580, fun:(function anonymous() {allElements[0].style.display = "-moz-popup";})},
{origCount:581, fun:(function anonymous() {allElements[10].style['float'] = "right";})},
{origCount:582, fun:(function anonymous() {allElements[12].style.height = "10%";})},
{origCount:583, fun:(function anonymous() {allElements[10].style.position = "static";})},
{origCount:584, fun:(function anonymous() {allElements[12].style.height = "200%";})},
{origCount:585, fun:(function anonymous() {allElements[8].style.height = "auto";})},
{origCount:586, fun:(function anonymous() {allElements[4].style.color = "green";})},
{origCount:587, fun:(function anonymous() {allElements[7].style.color = "red";})},
{origCount:588, fun:(function anonymous() {allElements[7].style.visibility = "collapse";})},
{origCount:589, fun:(function anonymous() {allElements[11].style['float'] = "left";})},
{origCount:590, fun:(function anonymous() {allElements[11].style.visibility = "hidden";})},
{origCount:591, fun:(function anonymous() {allElements[12].style.overflow = "visible";})},
{origCount:592, fun:(function anonymous() {allElements[8].style['float'] = "none";})},
{origCount:593, fun:(function anonymous() {allElements[2].style.display = "table-cell";})},
{origCount:594, fun:(function anonymous() {allElements[1].style.color = "black";})},
{origCount:595, fun:(function anonymous() {allElements[11].style.color = "green";})},
{origCount:596, fun:(function anonymous() {allElements[9].style.color = "red";})},
{origCount:597, fun:(function anonymous() {allElements[3].style['float'] = "none";})},
{origCount:598, fun:(function anonymous() {allElements[10].style.display = "inline";})},
{origCount:599, fun:(function anonymous() {allElements[10].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:600, fun:(function anonymous() {allElements[7].style.width = "10%";})},
{origCount:601, fun:(function anonymous() {allElements[9].style['float'] = "left";})},
{origCount:602, fun:(function anonymous() {allElements[6].style.width = "10%";})},
{origCount:603, fun:(function anonymous() {allElements[5].style.position = "absolute";})},
{origCount:604, fun:(function anonymous() {allElements[11].style.position = "static";})},
{origCount:605, fun:(function anonymous() {allElements[3].style.clear = "none";})},
{origCount:606, fun:(function anonymous() {allElements[0].style['float'] = "right";})},
{origCount:607, fun:(function anonymous() {allElements[6].style.position = "static";})},
{origCount:608, fun:(function anonymous() {allElements[3].style.height = "2em";})},
{origCount:609, fun:(function anonymous() {allElements[7].style.width = "20em";})},
{origCount:610, fun:(function anonymous() {allElements[11].style.overflow = "scroll";})},
{origCount:611, fun:(function anonymous() {allElements[8].style.position = "relative";})},
{origCount:612, fun:(function anonymous() {allElements[4].style['float'] = "left";})},
{origCount:613, fun:(function anonymous() {allElements[3].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:614, fun:(function anonymous() {allElements[11].style.height = "auto";})},
{origCount:615, fun:(function anonymous() {allElements[7].style['float'] = "right";})},
{origCount:616, fun:(function anonymous() {allElements[10].style.overflow = "scroll";})},
{origCount:617, fun:(function anonymous() {allElements[0].style.color = "green";})},
{origCount:618, fun:(function anonymous() {allElements[7].style['float'] = "none";})},
{origCount:619, fun:(function anonymous() {allElements[11].style.height = "10%";})},
{origCount:620, fun:(function anonymous() {allElements[4].style.height = "200%";})},
{origCount:621, fun:(function anonymous() {allElements[6].style.display = "-moz-popup";})},
{origCount:622, fun:(function anonymous() {allElements[8].style.position = "relative";})},
{origCount:623, fun:(function anonymous() {allElements[3].style.width = "1px";})},
{origCount:624, fun:(function anonymous() {allElements[8].style.height = "auto";})},
{origCount:625, fun:(function anonymous() {allElements[5].style['float'] = "right";})},
{origCount:626, fun:(function anonymous() {allElements[10].style.background = "transparent";})},
{origCount:627, fun:(function anonymous() {allElements[4].style.visibility = "visible";})},
{origCount:628, fun:(function anonymous() {allElements[5].style.display = "list-item";})},
{origCount:629, fun:(function anonymous() {allElements[5].style.height = "100px";})},
{origCount:630, fun:(function anonymous() {allElements[9].style.background = "transparent";})},
{origCount:631, fun:(function anonymous() {allElements[11].style.clear = "both";})},
{origCount:632, fun:(function anonymous() {allElements[2].style.overflow = "visible";})},
{origCount:633, fun:(function anonymous() {allElements[1].style.visibility = "hidden";})},
{origCount:634, fun:(function anonymous() {allElements[1].style['float'] = "none";})},
{origCount:635, fun:(function anonymous() {allElements[6].style.height = "2em";})},
{origCount:636, fun:(function anonymous() {allElements[9].style.position = "relative";})},
{origCount:637, fun:(function anonymous() {allElements[3].style.clear = "left";})},
{origCount:638, fun:(function anonymous() {allElements[6].style.display = "table-header-group";})},
{origCount:639, fun:(function anonymous() {allElements[10].style.display = "-moz-box";})},
{origCount:640, fun:(function anonymous() {allElements[8].style.color = "blue";})},
{origCount:641, fun:(function anonymous() {allElements[6].style.width = "200%";})},
{origCount:642, fun:(function anonymous() {allElements[8].style['float'] = "none";})},
{origCount:643, fun:(function anonymous() {allElements[7].style.height = "10%";})},
{origCount:644, fun:(function anonymous() {allElements[8].style.width = "1px";})},
{origCount:645, fun:(function anonymous() {allElements[5].style.clear = "right";})},
{origCount:646, fun:(function anonymous() {allElements[2].style.display = "table-row-group";})},
{origCount:647, fun:(function anonymous() {allElements[4].style.color = "blue";})},
{origCount:648, fun:(function anonymous() {allElements[5].style.color = "red";})},
{origCount:649, fun:(function anonymous() {allElements[10].style.background = "transparent";})},
{origCount:650, fun:(function anonymous() {allElements[10].style.visibility = "visible";})},
{origCount:651, fun:(function anonymous() {allElements[12].style.height = "auto";})},
{origCount:652, fun:(function anonymous() {allElements[7].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:653, fun:(function anonymous() {allElements[2].style.visibility = "visible";})},
{origCount:654, fun:(function anonymous() {allElements[2].style.clear = "none";})},
{origCount:655, fun:(function anonymous() {allElements[11].style.position = "relative";})},
{origCount:656, fun:(function anonymous() {allElements[10].style.width = "200%";})},
{origCount:657, fun:(function anonymous() {allElements[4].style.overflow = "scroll";})},
{origCount:658, fun:(function anonymous() {allElements[12].style.clear = "none";})},
{origCount:659, fun:(function anonymous() {allElements[12].style['float'] = "none";})},
{origCount:660, fun:(function anonymous() {allElements[10].style.overflow = "scroll";})},
{origCount:661, fun:(function anonymous() {allElements[12].style.clear = "left";})},
{origCount:662, fun:(function anonymous() {allElements[10].style.clear = "right";})},
{origCount:663, fun:(function anonymous() {allElements[9].style.clear = "none";})},
{origCount:664, fun:(function anonymous() {allElements[2].style.overflow = "hidden";})},
{origCount:665, fun:(function anonymous() {allElements[7].style.overflow = "visible";})},
{origCount:666, fun:(function anonymous() {allElements[4].style.width = "1px";})},
{origCount:667, fun:(function anonymous() {allElements[11].style.color = "blue";})},
{origCount:668, fun:(function anonymous() {allElements[8].style.position = "relative";})},
{origCount:669, fun:(function anonymous() {allElements[12].style.color = "black";})},
{origCount:670, fun:(function anonymous() {allElements[4].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:671, fun:(function anonymous() {allElements[2].style['float'] = "right";})},
{origCount:672, fun:(function anonymous() {allElements[10].style['float'] = "left";})},
{origCount:673, fun:(function anonymous() {allElements[10].style.clear = "right";})},
{origCount:674, fun:(function anonymous() {allElements[5].style.color = "black";})},
{origCount:675, fun:(function anonymous() {allElements[2].style.clear = "right";})},
{origCount:676, fun:(function anonymous() {allElements[5].style.height = "200%";})},
{origCount:677, fun:(function anonymous() {allElements[8].style.position = "absolute";})},
{origCount:678, fun:(function anonymous() {allElements[3].style.clear = "none";})},
{origCount:679, fun:(function anonymous() {allElements[7].style.position = "relative";})},
{origCount:680, fun:(function anonymous() {allElements[1].style.background = "transparent";})},
{origCount:681, fun:(function anonymous() {allElements[3].style.position = "static";})},
{origCount:682, fun:(function anonymous() {allElements[5].style['float'] = "left";})},
{origCount:683, fun:(function anonymous() {allElements[0].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:684, fun:(function anonymous() {allElements[7].style.display = "-moz-grid-line";})},
{origCount:685, fun:(function anonymous() {allElements[3].style.background = "transparent";})},
{origCount:686, fun:(function anonymous() {allElements[9].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:687, fun:(function anonymous() {allElements[3].style.background = "#fcd";})},
{origCount:688, fun:(function anonymous() {allElements[4].style['float'] = "left";})},
{origCount:689, fun:(function anonymous() {allElements[5].style['float'] = "none";})},
{origCount:690, fun:(function anonymous() {allElements[10].style.display = "table-cell";})},
{origCount:691, fun:(function anonymous() {allElements[12].style.height = "200%";})},
{origCount:692, fun:(function anonymous() {allElements[3].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:693, fun:(function anonymous() {allElements[3].style.height = "200%";})},
{origCount:694, fun:(function anonymous() {allElements[2].style.height = "2em";})},
{origCount:695, fun:(function anonymous() {allElements[8].style.clear = "both";})},
{origCount:696, fun:(function anonymous() {allElements[11].style.clear = "none";})},
{origCount:697, fun:(function anonymous() {allElements[6].style.clear = "right";})},
{origCount:698, fun:(function anonymous() {allElements[9].style.color = "red";})},
{origCount:699, fun:(function anonymous() {allElements[1].style['float'] = "left";})},
{origCount:700, fun:(function anonymous() {allElements[12].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:701, fun:(function anonymous() {allElements[10].style.display = "-moz-deck";})},
{origCount:702, fun:(function anonymous() {allElements[12].style.height = "auto";})},
{origCount:703, fun:(function anonymous() {allElements[12].style.clear = "none";})},
{origCount:704, fun:(function anonymous() {allElements[1].style.visibility = "hidden";})},
{origCount:705, fun:(function anonymous() {allElements[11].style['float'] = "right";})},
{origCount:706, fun:(function anonymous() {allElements[8].style.overflow = "hidden";})},
{origCount:707, fun:(function anonymous() {allElements[11].style.display = "-moz-grid-group";})},
{origCount:708, fun:(function anonymous() {allElements[12].style.color = "black";})},
{origCount:709, fun:(function anonymous() {allElements[4].style.clear = "right";})},
{origCount:710, fun:(function anonymous() {allElements[4].style['float'] = "right";})},
{origCount:711, fun:(function anonymous() {allElements[7].style.height = "auto";})},
{origCount:712, fun:(function anonymous() {allElements[2].style.clear = "left";})},
{origCount:713, fun:(function anonymous() {allElements[11].style.clear = "right";})},
{origCount:714, fun:(function anonymous() {allElements[11].style.display = "table-header-group";})},
{origCount:715, fun:(function anonymous() {allElements[8].style.height = "2em";})},
{origCount:716, fun:(function anonymous() {allElements[7].style.color = "green";})},
{origCount:717, fun:(function anonymous() {allElements[1].style.width = "auto";})},
{origCount:718, fun:(function anonymous() {allElements[9].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:719, fun:(function anonymous() {allElements[10].style.height = "2em";})},
{origCount:720, fun:(function anonymous() {allElements[8].style.width = "auto";})},
{origCount:721, fun:(function anonymous() {allElements[10].style.background = "#fcd";})},
{origCount:722, fun:(function anonymous() {allElements[9].style.display = "table-row-group";})},
{origCount:723, fun:(function anonymous() {allElements[8].style.overflow = "scroll";})},
{origCount:724, fun:(function anonymous() {allElements[2].style.display = "table-caption";})},
{origCount:725, fun:(function anonymous() {allElements[7].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:726, fun:(function anonymous() {allElements[5].style.visibility = "collapse";})},
{origCount:727, fun:(function anonymous() {allElements[12].style.position = "absolute";})},
{origCount:728, fun:(function anonymous() {allElements[9].style.color = "red";})},
{origCount:729, fun:(function anonymous() {allElements[1].style.display = "table-row";})},
{origCount:730, fun:(function anonymous() {allElements[6].style.color = "black";})},
{origCount:731, fun:(function anonymous() {allElements[4].style.visibility = "visible";})},
{origCount:732, fun:(function anonymous() {allElements[0].style.color = "black";})},
{origCount:733, fun:(function anonymous() {allElements[0].style.clear = "both";})},
{origCount:734, fun:(function anonymous() {allElements[8].style['float'] = "none";})},
{origCount:735, fun:(function anonymous() {allElements[5].style.width = "20em";})},
{origCount:736, fun:(function anonymous() {allElements[9].style['float'] = "left";})},
{origCount:737, fun:(function anonymous() {allElements[12].style.height = "10%";})},
{origCount:738, fun:(function anonymous() {allElements[7].style.height = "10%";})},
{origCount:739, fun:(function anonymous() {allElements[12].style.color = "black";})},
{origCount:740, fun:(function anonymous() {allElements[7].style.visibility = "hidden";})},
{origCount:741, fun:(function anonymous() {allElements[9].style.visibility = "collapse";})},
{origCount:742, fun:(function anonymous() {allElements[11].style.display = "-moz-inline-grid";})},
{origCount:743, fun:(function anonymous() {allElements[7].style.position = "static";})},
{origCount:744, fun:(function anonymous() {allElements[0].style.display = "-moz-box";})},
{origCount:745, fun:(function anonymous() {allElements[11].style.clear = "both";})},
{origCount:746, fun:(function anonymous() {allElements[4].style.position = "fixed";})},
{origCount:747, fun:(function anonymous() {allElements[11].style.background = "#fcd";})},
{origCount:748, fun:(function anonymous() {allElements[0].style.position = "fixed";})},
{origCount:749, fun:(function anonymous() {allElements[0].style.width = "1px";})},
{origCount:750, fun:(function anonymous() {allElements[6].style.visibility = "hidden";})},
{origCount:751, fun:(function anonymous() {allElements[8].style.position = "absolute";})},
{origCount:752, fun:(function anonymous() {allElements[0].style.color = "green";})},
{origCount:753, fun:(function anonymous() {allElements[0].style.clear = "both";})},
{origCount:754, fun:(function anonymous() {allElements[0].style.overflow = "auto";})},
{origCount:755, fun:(function anonymous() {allElements[6].style.clear = "left";})},
{origCount:756, fun:(function anonymous() {allElements[10].style.position = "static";})},
{origCount:757, fun:(function anonymous() {allElements[4].style.background = "#fcd";})},
{origCount:758, fun:(function anonymous() {allElements[8].style.color = "black";})},
{origCount:759, fun:(function anonymous() {allElements[0].style.position = "relative";})},
{origCount:760, fun:(function anonymous() {allElements[12].style.overflow = "auto";})},
{origCount:761, fun:(function anonymous() {allElements[10].style.visibility = "hidden";})},
{origCount:762, fun:(function anonymous() {allElements[0].style.visibility = "collapse";})},
{origCount:763, fun:(function anonymous() {allElements[12].style.height = "100px";})},
{origCount:764, fun:(function anonymous() {allElements[2].style.overflow = "visible";})},
{origCount:765, fun:(function anonymous() {allElements[12].style.overflow = "auto";})},
{origCount:766, fun:(function anonymous() {allElements[10].style.position = "fixed";})},
{origCount:767, fun:(function anonymous() {allElements[0].style.overflow = "hidden";})},
{origCount:768, fun:(function anonymous() {allElements[1].style.display = "table-cell";})},
{origCount:769, fun:(function anonymous() {allElements[7].style.clear = "both";})},
{origCount:770, fun:(function anonymous() {allElements[8].style.position = "relative";})},
{origCount:771, fun:(function anonymous() {allElements[10].style.color = "red";})},
{origCount:772, fun:(function anonymous() {allElements[6].style.display = "-moz-inline-box";})},
{origCount:773, fun:(function anonymous() {allElements[2].style.overflow = "hidden";})},
{origCount:774, fun:(function anonymous() {allElements[2].style['float'] = "none";})},
{origCount:775, fun:(function anonymous() {allElements[0].style.clear = "left";})},
{origCount:776, fun:(function anonymous() {allElements[12].style.display = "table-cell";})},
{origCount:777, fun:(function anonymous() {allElements[7].style.background = "transparent";})},
{origCount:778, fun:(function anonymous() {allElements[2].style['float'] = "right";})},
{origCount:779, fun:(function anonymous() {allElements[3].style.overflow = "scroll";})},
{origCount:780, fun:(function anonymous() {allElements[2].style.width = "1px";})},
{origCount:781, fun:(function anonymous() {allElements[4].style.clear = "both";})},
{origCount:782, fun:(function anonymous() {allElements[3].style.height = "auto";})},
{origCount:783, fun:(function anonymous() {allElements[3].style.color = "green";})},
{origCount:784, fun:(function anonymous() {allElements[10].style.color = "red";})},
{origCount:785, fun:(function anonymous() {allElements[3].style.position = "static";})},
{origCount:786, fun:(function anonymous() {allElements[1].style.position = "absolute";})},
{origCount:787, fun:(function anonymous() {allElements[8].style.height = "100px";})},
{origCount:788, fun:(function anonymous() {allElements[6].style.overflow = "scroll";})},
{origCount:789, fun:(function anonymous() {allElements[11].style.position = "relative";})},
{origCount:790, fun:(function anonymous() {allElements[3].style.display = "-moz-grid-line";})},
{origCount:791, fun:(function anonymous() {allElements[2].style.visibility = "collapse";})},
{origCount:792, fun:(function anonymous() {allElements[11].style['float'] = "none";})},
{origCount:793, fun:(function anonymous() {allElements[11].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:794, fun:(function anonymous() {allElements[7].style['float'] = "right";})},
{origCount:795, fun:(function anonymous() {allElements[5].style.display = "table-column";})},
{origCount:796, fun:(function anonymous() {allElements[9].style.background = "transparent";})},
{origCount:797, fun:(function anonymous() {allElements[12].style['float'] = "right";})},
{origCount:798, fun:(function anonymous() {allElements[8].style.position = "static";})},
{origCount:799, fun:(function anonymous() {allElements[0].style.position = "fixed";})},
{origCount:800, fun:(function anonymous() {allElements[8].style.overflow = "visible";})},
{origCount:801, fun:(function anonymous() {allElements[10].style.height = "100px";})},
{origCount:802, fun:(function anonymous() {allElements[0].style.clear = "right";})},
{origCount:803, fun:(function anonymous() {allElements[9].style.color = "black";})},
{origCount:804, fun:(function anonymous() {allElements[3].style.width = "1px";})},
{origCount:805, fun:(function anonymous() {allElements[0].style.clear = "none";})},
{origCount:806, fun:(function anonymous() {allElements[7].style.width = "200%";})},
{origCount:807, fun:(function anonymous() {allElements[2].style.overflow = "visible";})},
{origCount:808, fun:(function anonymous() {allElements[4].style.overflow = "visible";})},
{origCount:809, fun:(function anonymous() {allElements[5].style.display = "table-row";})},
{origCount:810, fun:(function anonymous() {allElements[10].style.clear = "none";})},
{origCount:811, fun:(function anonymous() {allElements[0].style.color = "red";})},
{origCount:812, fun:(function anonymous() {allElements[5].style.clear = "right";})},
{origCount:813, fun:(function anonymous() {allElements[5].style['float'] = "none";})},
{origCount:814, fun:(function anonymous() {allElements[6].style.background = "#fcd";})},
{origCount:815, fun:(function anonymous() {allElements[12].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:816, fun:(function anonymous() {allElements[3].style.visibility = "visible";})},
{origCount:817, fun:(function anonymous() {allElements[11].style.clear = "none";})},
{origCount:818, fun:(function anonymous() {allElements[2].style.visibility = "visible";})},
{origCount:819, fun:(function anonymous() {allElements[8].style.position = "relative";})},
{origCount:820, fun:(function anonymous() {allElements[7].style.height = "auto";})},
{origCount:821, fun:(function anonymous() {allElements[5].style.clear = "both";})},
{origCount:822, fun:(function anonymous() {allElements[9].style.overflow = "auto";})},
{origCount:823, fun:(function anonymous() {allElements[9].style.position = "static";})},
{origCount:824, fun:(function anonymous() {allElements[11].style.position = "absolute";})},
{origCount:825, fun:(function anonymous() {allElements[9].style.width = "200%";})},
{origCount:826, fun:(function anonymous() {allElements[7].style['float'] = "none";})},
{origCount:827, fun:(function anonymous() {allElements[11].style.position = "static";})},
{origCount:828, fun:(function anonymous() {allElements[0].style.overflow = "hidden";})},
{origCount:829, fun:(function anonymous() {allElements[5].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:830, fun:(function anonymous() {allElements[6].style.position = "fixed";})},
{origCount:831, fun:(function anonymous() {allElements[9].style['float'] = "right";})},
{origCount:832, fun:(function anonymous() {allElements[6].style['float'] = "none";})},
{origCount:833, fun:(function anonymous() {allElements[2].style.background = "transparent";})},
{origCount:834, fun:(function anonymous() {allElements[3].style.overflow = "scroll";})},
{origCount:835, fun:(function anonymous() {allElements[0].style.height = "auto";})},
{origCount:836, fun:(function anonymous() {allElements[0].style.position = "static";})},
{origCount:837, fun:(function anonymous() {allElements[8].style.display = "-moz-grid-line";})},
{origCount:838, fun:(function anonymous() {allElements[4].style.height = "10%";})},
{origCount:839, fun:(function anonymous() {allElements[5].style.width = "1px";})},
{origCount:840, fun:(function anonymous() {allElements[4].style.position = "fixed";})},
{origCount:841, fun:(function anonymous() {allElements[7].style.clear = "none";})},
{origCount:842, fun:(function anonymous() {allElements[6].style.display = "table-column";})},
{origCount:843, fun:(function anonymous() {allElements[7].style.visibility = "visible";})},
{origCount:844, fun:(function anonymous() {allElements[1].style.background = "#fcd";})},
{origCount:845, fun:(function anonymous() {allElements[7].style.height = "2em";})},
{origCount:846, fun:(function anonymous() {allElements[5].style.display = "table-column";})},
{origCount:847, fun:(function anonymous() {allElements[0].style.clear = "both";})},
{origCount:848, fun:(function anonymous() {allElements[11].style['float'] = "right";})},
{origCount:849, fun:(function anonymous() {allElements[4].style.visibility = "visible";})},
{origCount:850, fun:(function anonymous() {allElements[9].style.overflow = "scroll";})},
{origCount:851, fun:(function anonymous() {allElements[8].style.height = "200%";})},
{origCount:852, fun:(function anonymous() {allElements[5].style.height = "200%";})},
{origCount:853, fun:(function anonymous() {allElements[5].style.clear = "none";})},
{origCount:854, fun:(function anonymous() {allElements[2].style.background = "#fcd";})},
{origCount:855, fun:(function anonymous() {allElements[12].style.visibility = "hidden";})},
{origCount:856, fun:(function anonymous() {allElements[4].style.clear = "both";})},
{origCount:857, fun:(function anonymous() {allElements[8].style.width = "10%";})},
{origCount:858, fun:(function anonymous() {allElements[4].style.color = "red";})},
{origCount:859, fun:(function anonymous() {allElements[9].style.height = "10%";})},
{origCount:860, fun:(function anonymous() {allElements[4].style.visibility = "hidden";})},
{origCount:861, fun:(function anonymous() {allElements[7].style.clear = "left";})},
{origCount:862, fun:(function anonymous() {allElements[11].style.background = "#fcd";})},
{origCount:863, fun:(function anonymous() {allElements[7].style.color = "green";})},
{origCount:864, fun:(function anonymous() {allElements[1].style.clear = "left";})},
{origCount:865, fun:(function anonymous() {allElements[12].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:866, fun:(function anonymous() {allElements[6].style.width = "auto";})},
{origCount:867, fun:(function anonymous() {allElements[1].style.height = "100px";})},
{origCount:868, fun:(function anonymous() {allElements[3].style.display = "-moz-inline-block";})},
{origCount:869, fun:(function anonymous() {allElements[5].style.visibility = "visible";})},
{origCount:870, fun:(function anonymous() {allElements[11].style.color = "blue";})},
{origCount:871, fun:(function anonymous() {allElements[1].style.position = "static";})},
{origCount:872, fun:(function anonymous() {allElements[6].style.visibility = "visible";})},
{origCount:873, fun:(function anonymous() {allElements[7].style.color = "red";})},
{origCount:874, fun:(function anonymous() {allElements[8].style.color = "blue";})},
{origCount:875, fun:(function anonymous() {allElements[1].style['float'] = "right";})},
{origCount:876, fun:(function anonymous() {allElements[6].style['float'] = "right";})},
{origCount:877, fun:(function anonymous() {allElements[1].style.clear = "left";})},
{origCount:878, fun:(function anonymous() {allElements[6].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:879, fun:(function anonymous() {allElements[11].style.display = "inline";})},
{origCount:880, fun:(function anonymous() {allElements[11].style['float'] = "none";})},
{origCount:881, fun:(function anonymous() {allElements[10].style.color = "black";})},
{origCount:882, fun:(function anonymous() {allElements[0].style.visibility = "hidden";})},
{origCount:883, fun:(function anonymous() {allElements[1].style.color = "green";})},
{origCount:884, fun:(function anonymous() {allElements[4].style.height = "10%";})},
{origCount:885, fun:(function anonymous() {allElements[2].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:886, fun:(function anonymous() {allElements[0].style.display = "list-item";})},
{origCount:887, fun:(function anonymous() {allElements[4].style['float'] = "left";})},
{origCount:888, fun:(function anonymous() {allElements[6].style.overflow = "hidden";})},
{origCount:889, fun:(function anonymous() {allElements[12].style.clear = "left";})},
{origCount:890, fun:(function anonymous() {allElements[1].style.clear = "none";})},
{origCount:891, fun:(function anonymous() {allElements[4].style.clear = "left";})},
{origCount:892, fun:(function anonymous() {allElements[1].style.position = "relative";})},
{origCount:893, fun:(function anonymous() {allElements[11].style.position = "absolute";})},
{origCount:894, fun:(function anonymous() {allElements[12].style.background = "#fcd";})},
{origCount:895, fun:(function anonymous() {allElements[10].style.position = "relative";})},
{origCount:896, fun:(function anonymous() {allElements[10].style.display = "-moz-box";})},
{origCount:897, fun:(function anonymous() {allElements[6].style.position = "fixed";})},
{origCount:898, fun:(function anonymous() {allElements[1].style.overflow = "scroll";})},
{origCount:899, fun:(function anonymous() {allElements[3].style.width = "10%";})},
{origCount:900, fun:(function anonymous() {allElements[3].style.background = "transparent";})},
{origCount:901, fun:(function anonymous() {allElements[6].style.background = "transparent";})},
{origCount:902, fun:(function anonymous() {allElements[5].style.visibility = "visible";})},
{origCount:903, fun:(function anonymous() {allElements[6].style.background = "#fcd";})},
{origCount:904, fun:(function anonymous() {allElements[0].style.overflow = "scroll";})},
{origCount:905, fun:(function anonymous() {allElements[7].style['float'] = "none";})},
{origCount:906, fun:(function anonymous() {allElements[6].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:907, fun:(function anonymous() {allElements[1].style.height = "200%";})},
{origCount:908, fun:(function anonymous() {allElements[12].style.display = "table-row";})},
{origCount:909, fun:(function anonymous() {allElements[5].style.height = "10%";})},
{origCount:910, fun:(function anonymous() {allElements[11].style.position = "relative";})},
{origCount:911, fun:(function anonymous() {allElements[10].style.display = "-moz-stack";})},
{origCount:912, fun:(function anonymous() {allElements[7].style.color = "green";})},
{origCount:913, fun:(function anonymous() {allElements[8].style.clear = "left";})},
{origCount:914, fun:(function anonymous() {allElements[5].style.clear = "right";})},
{origCount:915, fun:(function anonymous() {allElements[3].style['float'] = "left";})},
{origCount:916, fun:(function anonymous() {allElements[8].style.display = "table-header-group";})},
{origCount:917, fun:(function anonymous() {allElements[12].style.display = "-moz-grid-group";})},
{origCount:918, fun:(function anonymous() {allElements[8].style.position = "fixed";})},
{origCount:919, fun:(function anonymous() {allElements[1].style.clear = "none";})},
{origCount:920, fun:(function anonymous() {allElements[10].style.height = "10%";})},
{origCount:921, fun:(function anonymous() {allElements[0].style['float'] = "left";})},
{origCount:922, fun:(function anonymous() {allElements[4].style['float'] = "left";})},
{origCount:923, fun:(function anonymous() {allElements[0].style.display = "-moz-inline-grid";})},
{origCount:924, fun:(function anonymous() {allElements[8].style.clear = "left";})},
{origCount:925, fun:(function anonymous() {allElements[6].style.clear = "right";})},
{origCount:926, fun:(function anonymous() {allElements[0].style.overflow = "hidden";})},
{origCount:927, fun:(function anonymous() {allElements[9].style.height = "100px";})},
{origCount:928, fun:(function anonymous() {allElements[11].style.color = "blue";})},
{origCount:929, fun:(function anonymous() {allElements[0].style.clear = "left";})},
{origCount:930, fun:(function anonymous() {allElements[6].style.background = "#fcd";})},
{origCount:931, fun:(function anonymous() {allElements[10].style['float'] = "none";})},
{origCount:932, fun:(function anonymous() {allElements[3].style.display = "-moz-inline-box";})},
{origCount:933, fun:(function anonymous() {allElements[4].style.width = "1px";})},
{origCount:934, fun:(function anonymous() {allElements[5].style.display = "table-row";})},
{origCount:935, fun:(function anonymous() {allElements[12].style.height = "2em";})},
{origCount:936, fun:(function anonymous() {allElements[4].style.visibility = "collapse";})},
{origCount:937, fun:(function anonymous() {allElements[0].style.background = "transparent";})},
{origCount:938, fun:(function anonymous() {allElements[4].style.background = "#fcd";})},
{origCount:939, fun:(function anonymous() {allElements[11].style.overflow = "scroll";})},
{origCount:940, fun:(function anonymous() {allElements[10].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:941, fun:(function anonymous() {allElements[10].style.background = "#fcd";})},
{origCount:942, fun:(function anonymous() {allElements[0].style.width = "20em";})},
{origCount:943, fun:(function anonymous() {allElements[1].style.overflow = "scroll";})},
{origCount:944, fun:(function anonymous() {allElements[5].style.clear = "left";})},
{origCount:945, fun:(function anonymous() {allElements[3].style.display = "table";})},
{origCount:946, fun:(function anonymous() {allElements[2].style.display = "table-footer-group";})},
{origCount:947, fun:(function anonymous() {allElements[6].style.visibility = "visible";})},
{origCount:948, fun:(function anonymous() {allElements[9].style.display = "-moz-inline-block";})},
{origCount:949, fun:(function anonymous() {allElements[2].style.clear = "right";})},
{origCount:950, fun:(function anonymous() {allElements[4].style.overflow = "visible";})},
{origCount:951, fun:(function anonymous() {allElements[8].style.width = "200%";})},
{origCount:952, fun:(function anonymous() {allElements[5].style.overflow = "hidden";})},
{origCount:953, fun:(function anonymous() {allElements[2].style.height = "auto";})},
{origCount:954, fun:(function anonymous() {allElements[3].style.overflow = "visible";})},
{origCount:955, fun:(function anonymous() {allElements[2].style.color = "blue";})},
{origCount:956, fun:(function anonymous() {allElements[2].style.width = "10%";})},
{origCount:957, fun:(function anonymous() {allElements[11].style.visibility = "collapse";})},
{origCount:958, fun:(function anonymous() {allElements[7].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:959, fun:(function anonymous() {allElements[9].style.position = "fixed";})},
{origCount:960, fun:(function anonymous() {allElements[9].style.background = "transparent";})},
{origCount:961, fun:(function anonymous() {allElements[0].style.clear = "right";})},
{origCount:962, fun:(function anonymous() {allElements[0].style['float'] = "left";})},
{origCount:963, fun:(function anonymous() {allElements[1].style.width = "1px";})},
{origCount:964, fun:(function anonymous() {allElements[9].style.height = "2em";})},
{origCount:965, fun:(function anonymous() {allElements[3].style.width = "20em";})},
{origCount:966, fun:(function anonymous() {allElements[1].style.width = "200%";})},
{origCount:967, fun:(function anonymous() {allElements[10].style.overflow = "hidden";})},
{origCount:968, fun:(function anonymous() {allElements[9].style.clear = "both";})},
{origCount:969, fun:(function anonymous() {allElements[2].style.clear = "both";})},
{origCount:970, fun:(function anonymous() {allElements[9].style['float'] = "left";})},
{origCount:971, fun:(function anonymous() {allElements[8].style.clear = "left";})},
{origCount:972, fun:(function anonymous() {allElements[6].style.height = "auto";})},
{origCount:973, fun:(function anonymous() {allElements[7].style.background = "#fcd";})},
{origCount:974, fun:(function anonymous() {allElements[4].style.clear = "none";})},
{origCount:975, fun:(function anonymous() {allElements[2].style.position = "relative";})},
{origCount:976, fun:(function anonymous() {allElements[8].style['float'] = "left";})},
{origCount:977, fun:(function anonymous() {allElements[12].style.visibility = "hidden";})},
{origCount:978, fun:(function anonymous() {allElements[8].style.height = "100px";})},
{origCount:979, fun:(function anonymous() {allElements[2].style['float'] = "left";})},
{origCount:980, fun:(function anonymous() {allElements[11].style.clear = "left";})},
{origCount:981, fun:(function anonymous() {allElements[1].style.color = "blue";})},
{origCount:982, fun:(function anonymous() {allElements[6].style.height = "100px";})},
{origCount:983, fun:(function anonymous() {allElements[2].style.overflow = "scroll";})},
{origCount:984, fun:(function anonymous() {allElements[10].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:985, fun:(function anonymous() {allElements[9].style.clear = "both";})},
{origCount:986, fun:(function anonymous() {allElements[4].style.height = "10%";})},
{origCount:987, fun:(function anonymous() {allElements[0].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:988, fun:(function anonymous() {allElements[2].style.background = "transparent";})},
{origCount:989, fun:(function anonymous() {allElements[4].style.color = "green";})},
{origCount:990, fun:(function anonymous() {allElements[11].style.color = "green";})},
{origCount:991, fun:(function anonymous() {allElements[2].style.clear = "left";})},
{origCount:992, fun:(function anonymous() {allElements[8].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:993, fun:(function anonymous() {allElements[10].style.background = "transparent";})},
{origCount:994, fun:(function anonymous() {allElements[11].style.overflow = "auto";})},
{origCount:995, fun:(function anonymous() {allElements[5].style.overflow = "visible";})},
{origCount:996, fun:(function anonymous() {allElements[11].style.visibility = "collapse";})},
{origCount:997, fun:(function anonymous() {allElements[7].style.clear = "both";})},
{origCount:998, fun:(function anonymous() {allElements[12].style.position = "fixed";})},
{origCount:999, fun:(function anonymous() {allElements[5].style.color = "green";})},
{origCount:1000, fun:(function anonymous() {allElements[6].style.display = "-moz-box";})},
{origCount:1001, fun:(function anonymous() {allElements[5].style.overflow = "auto";})},
{origCount:1002, fun:(function anonymous() {allElements[9].style.height = "2em";})},
{origCount:1003, fun:(function anonymous() {allElements[11].style['float'] = "left";})},
{origCount:1004, fun:(function anonymous() {allElements[2].style['float'] = "none";})},
{origCount:1005, fun:(function anonymous() {allElements[0].style.overflow = "scroll";})},
{origCount:1006, fun:(function anonymous() {allElements[12].style.background = "transparent";})},
{origCount:1007, fun:(function anonymous() {allElements[4].style.visibility = "hidden";})},
{origCount:1008, fun:(function anonymous() {allElements[7].style.overflow = "scroll";})},
{origCount:1009, fun:(function anonymous() {allElements[1].style.width = "auto";})},
{origCount:1010, fun:(function anonymous() {allElements[3].style.overflow = "hidden";})},
{origCount:1011, fun:(function anonymous() {allElements[7].style.display = "table-header-group";})},
{origCount:1012, fun:(function anonymous() {allElements[5].style.display = "-moz-box";})},
{origCount:1013, fun:(function anonymous() {allElements[2].style['float'] = "left";})},
{origCount:1014, fun:(function anonymous() {allElements[3].style.height = "auto";})},
{origCount:1015, fun:(function anonymous() {allElements[2].style.overflow = "auto";})},
{origCount:1016, fun:(function anonymous() {allElements[3].style['float'] = "right";})},
{origCount:1017, fun:(function anonymous() {allElements[0].style.height = "2em";})},
{origCount:1018, fun:(function anonymous() {allElements[9].style.background = "transparent";})},
{origCount:1019, fun:(function anonymous() {allElements[11].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:1020, fun:(function anonymous() {allElements[12].style.visibility = "hidden";})},
{origCount:1021, fun:(function anonymous() {allElements[3].style.clear = "both";})},
{origCount:1022, fun:(function anonymous() {allElements[3].style.visibility = "visible";})},
{origCount:1023, fun:(function anonymous() {allElements[4].style.overflow = "auto";})},
{origCount:1024, fun:(function anonymous() {allElements[12].style['float'] = "right";})},
{origCount:1025, fun:(function anonymous() {allElements[7].style.display = "table";})},
{origCount:1026, fun:(function anonymous() {allElements[6].style.color = "blue";})},
{origCount:1027, fun:(function anonymous() {allElements[2].style.color = "black";})},
{origCount:1028, fun:(function anonymous() {allElements[1].style.color = "black";})},
{origCount:1029, fun:(function anonymous() {allElements[8].style['float'] = "right";})},
{origCount:1030, fun:(function anonymous() {allElements[2].style.display = "-moz-grid-group";})},
{origCount:1031, fun:(function anonymous() {allElements[1].style.background = "#fcd";})},
{origCount:1032, fun:(function anonymous() {allElements[12].style.height = "auto";})},
{origCount:1033, fun:(function anonymous() {allElements[1].style.clear = "both";})},
{origCount:1034, fun:(function anonymous() {allElements[11].style.width = "auto";})},
{origCount:1035, fun:(function anonymous() {allElements[10].style.position = "relative";})},
{origCount:1036, fun:(function anonymous() {allElements[3].style.position = "fixed";})},
{origCount:1037, fun:(function anonymous() {allElements[8].style.clear = "both";})},
{origCount:1038, fun:(function anonymous() {allElements[4].style['float'] = "left";})},
{origCount:1039, fun:(function anonymous() {allElements[11].style.overflow = "auto";})},
{origCount:1040, fun:(function anonymous() {allElements[7].style.height = "200%";})},
{origCount:1041, fun:(function anonymous() {allElements[11].style.width = "200%";})},
{origCount:1042, fun:(function anonymous() {allElements[3].style.overflow = "visible";})},
{origCount:1043, fun:(function anonymous() {allElements[0].style.position = "fixed";})},
{origCount:1044, fun:(function anonymous() {allElements[8].style.clear = "none";})},
{origCount:1045, fun:(function anonymous() {allElements[7].style.width = "10%";})},
{origCount:1046, fun:(function anonymous() {allElements[2].style.height = "100px";})},
{origCount:1047, fun:(function anonymous() {allElements[12].style.clear = "left";})},
{origCount:1048, fun:(function anonymous() {allElements[2].style.overflow = "visible";})},
{origCount:1049, fun:(function anonymous() {allElements[4].style.background = "transparent";})},
{origCount:1050, fun:(function anonymous() {allElements[11].style['float'] = "none";})},
{origCount:1051, fun:(function anonymous() {allElements[3].style['float'] = "right";})},
{origCount:1052, fun:(function anonymous() {allElements[9].style.height = "auto";})},
{origCount:1053, fun:(function anonymous() {allElements[11].style.display = "-moz-grid";})},
{origCount:1054, fun:(function anonymous() {allElements[0].style.position = "fixed";})},
{origCount:1055, fun:(function anonymous() {allElements[7].style.width = "20em";})},
{origCount:1056, fun:(function anonymous() {allElements[0].style.height = "100px";})},
{origCount:1057, fun:(function anonymous() {allElements[10].style.clear = "none";})},
{origCount:1058, fun:(function anonymous() {allElements[2].style.width = "10%";})},
{origCount:1059, fun:(function anonymous() {allElements[9].style.visibility = "collapse";})},
{origCount:1060, fun:(function anonymous() {allElements[10].style.display = "-moz-inline-stack";})},
{origCount:1061, fun:(function anonymous() {allElements[10].style.height = "200%";})},
{origCount:1062, fun:(function anonymous() {allElements[1].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:1063, fun:(function anonymous() {allElements[3].style.clear = "right";})},
{origCount:1064, fun:(function anonymous() {allElements[7].style.overflow = "auto";})},
{origCount:1065, fun:(function anonymous() {allElements[6].style.visibility = "visible";})},
{origCount:1066, fun:(function anonymous() {allElements[5].style['float'] = "right";})},
{origCount:1067, fun:(function anonymous() {allElements[11].style.height = "200%";})},
{origCount:1068, fun:(function anonymous() {allElements[1].style.position = "static";})},
{origCount:1069, fun:(function anonymous() {allElements[8].style.clear = "none";})},
{origCount:1070, fun:(function anonymous() {allElements[11].style.display = "-moz-groupbox";})},
{origCount:1071, fun:(function anonymous() {allElements[2].style.visibility = "visible";})},
{origCount:1072, fun:(function anonymous() {allElements[0].style.background = "transparent";})},
{origCount:1073, fun:(function anonymous() {allElements[10].style.width = "auto";})},
{origCount:1074, fun:(function anonymous() {allElements[12].style.clear = "right";})},
{origCount:1075, fun:(function anonymous() {allElements[12].style['float'] = "right";})},
{origCount:1076, fun:(function anonymous() {allElements[0].style.width = "200%";})},
{origCount:1077, fun:(function anonymous() {allElements[10].style.clear = "left";})},
{origCount:1078, fun:(function anonymous() {allElements[7].style.display = "-moz-deck";})},
{origCount:1079, fun:(function anonymous() {allElements[9].style.color = "green";})},
{origCount:1080, fun:(function anonymous() {allElements[10].style.color = "black";})},
{origCount:1081, fun:(function anonymous() {allElements[1].style.width = "200%";})},
{origCount:1082, fun:(function anonymous() {allElements[2].style.position = "fixed";})},
{origCount:1083, fun:(function anonymous() {allElements[3].style.height = "100px";})},
{origCount:1084, fun:(function anonymous() {allElements[12].style.background = "#fcd";})},
{origCount:1085, fun:(function anonymous() {allElements[7].style.visibility = "collapse";})},
{origCount:1086, fun:(function anonymous() {allElements[6].style.clear = "both";})},
{origCount:1087, fun:(function anonymous() {allElements[3].style.overflow = "visible";})},
{origCount:1088, fun:(function anonymous() {allElements[2].style.width = "10%";})},
{origCount:1089, fun:(function anonymous() {allElements[9].style.color = "red";})},
{origCount:1090, fun:(function anonymous() {allElements[3].style.display = "-moz-inline-stack";})},
{origCount:1091, fun:(function anonymous() {allElements[4].style['float'] = "right";})},
{origCount:1092, fun:(function anonymous() {allElements[2].style.overflow = "visible";})},
{origCount:1093, fun:(function anonymous() {allElements[4].style.clear = "none";})},
{origCount:1094, fun:(function anonymous() {allElements[1].style.display = "table-row";})},
{origCount:1095, fun:(function anonymous() {allElements[1].style.display = "-moz-deck";})},
{origCount:1096, fun:(function anonymous() {allElements[7].style.overflow = "visible";})},
{origCount:1097, fun:(function anonymous() {allElements[12].style.color = "black";})},
{origCount:1098, fun:(function anonymous() {allElements[9].style.width = "20em";})},
{origCount:1099, fun:(function anonymous() {allElements[3].style.color = "green";})},
{origCount:1100, fun:(function anonymous() {allElements[0].style.overflow = "auto";})},
{origCount:1101, fun:(function anonymous() {allElements[4].style.background = "#fcd";})},
{origCount:1102, fun:(function anonymous() {allElements[9].style.background = "#fcd";})},
{origCount:1103, fun:(function anonymous() {allElements[7].style.clear = "none";})},
{origCount:1104, fun:(function anonymous() {allElements[2].style['float'] = "none";})},
{origCount:1105, fun:(function anonymous() {allElements[2].style.clear = "none";})},
{origCount:1106, fun:(function anonymous() {allElements[10].style.color = "blue";})},
{origCount:1107, fun:(function anonymous() {allElements[7].style.clear = "none";})},
{origCount:1108, fun:(function anonymous() {allElements[10].style.height = "10%";})},
{origCount:1109, fun:(function anonymous() {allElements[0].style.overflow = "scroll";})},
{origCount:1110, fun:(function anonymous() {allElements[7].style.display = "-moz-grid-group";})},
{origCount:1111, fun:(function anonymous() {allElements[12].style.overflow = "visible";})},
{origCount:1112, fun:(function anonymous() {allElements[6].style.width = "20em";})},
{origCount:1113, fun:(function anonymous() {allElements[8].style.overflow = "auto";})},
{origCount:1114, fun:(function anonymous() {allElements[10].style['float'] = "none";})},
{origCount:1115, fun:(function anonymous() {allElements[5].style.width = "auto";})},
{origCount:1116, fun:(function anonymous() {allElements[11].style.display = "table-caption";})},
{origCount:1117, fun:(function anonymous() {allElements[8].style.width = "200%";})},
{origCount:1118, fun:(function anonymous() {allElements[1].style.width = "1px";})},
{origCount:1119, fun:(function anonymous() {allElements[8].style.background = "transparent";})},
{origCount:1120, fun:(function anonymous() {allElements[9].style['float'] = "none";})},
{origCount:1121, fun:(function anonymous() {allElements[9].style['float'] = "none";})},
{origCount:1122, fun:(function anonymous() {allElements[1].style.display = "list-item";})},
{origCount:1123, fun:(function anonymous() {allElements[3].style['float'] = "none";})},
{origCount:1124, fun:(function anonymous() {allElements[8].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:1125, fun:(function anonymous() {allElements[7].style.height = "auto";})},
{origCount:1126, fun:(function anonymous() {allElements[7].style.height = "10%";})},
{origCount:1127, fun:(function anonymous() {allElements[0].style.display = "-moz-inline-box";})},
{origCount:1128, fun:(function anonymous() {allElements[3].style.clear = "right";})},
{origCount:1129, fun:(function anonymous() {allElements[11].style.clear = "left";})},
{origCount:1130, fun:(function anonymous() {allElements[1].style.color = "black";})},
{origCount:1131, fun:(function anonymous() {allElements[5].style['float'] = "none";})},
{origCount:1132, fun:(function anonymous() {allElements[4].style.width = "10%";})},
{origCount:1133, fun:(function anonymous() {allElements[2].style.display = "-moz-grid";})},
{origCount:1134, fun:(function anonymous() {allElements[4].style.height = "100px";})},
{origCount:1135, fun:(function anonymous() {allElements[4].style.clear = "both";})},
{origCount:1136, fun:(function anonymous() {allElements[6].style.position = "static";})},
{origCount:1137, fun:(function anonymous() {allElements[2].style['float'] = "left";})},
{origCount:1138, fun:(function anonymous() {allElements[0].style.overflow = "scroll";})},
{origCount:1139, fun:(function anonymous() {allElements[3].style.display = "table-cell";})},
{origCount:1140, fun:(function anonymous() {allElements[4].style.color = "blue";})},
{origCount:1141, fun:(function anonymous() {allElements[9].style.clear = "left";})},
{origCount:1142, fun:(function anonymous() {allElements[9].style.clear = "none";})},
{origCount:1143, fun:(function anonymous() {allElements[11].style['float'] = "left";})},
{origCount:1144, fun:(function anonymous() {allElements[7].style.display = "-moz-inline-block";})},
{origCount:1145, fun:(function anonymous() {allElements[3].style.clear = "none";})},
{origCount:1146, fun:(function anonymous() {allElements[2].style.visibility = "collapse";})},
{origCount:1147, fun:(function anonymous() {allElements[12].style['float'] = "none";})},
{origCount:1148, fun:(function anonymous() {allElements[12].style.background = "transparent";})},
{origCount:1149, fun:(function anonymous() {allElements[6].style.width = "1px";})},
{origCount:1150, fun:(function anonymous() {allElements[1].style.width = "10%";})},
{origCount:1151, fun:(function anonymous() {allElements[1].style['float'] = "none";})},
{origCount:1152, fun:(function anonymous() {allElements[0].style.width = "1px";})},
{origCount:1153, fun:(function anonymous() {allElements[2].style.width = "20em";})},
{origCount:1154, fun:(function anonymous() {allElements[0].style.display = "-moz-popup";})},
{origCount:1155, fun:(function anonymous() {allElements[0].style.color = "red";})},
{origCount:1156, fun:(function anonymous() {allElements[6].style.visibility = "visible";})},
{origCount:1157, fun:(function anonymous() {allElements[12].style.background = "#fcd";})},
{origCount:1158, fun:(function anonymous() {allElements[9].style.visibility = "hidden";})},
{origCount:1159, fun:(function anonymous() {allElements[4].style.overflow = "scroll";})},
{origCount:1160, fun:(function anonymous() {allElements[1].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:1161, fun:(function anonymous() {allElements[6].style.display = "block";})},
{origCount:1162, fun:(function anonymous() {allElements[11].style.background = "#fcd";})},
{origCount:1163, fun:(function anonymous() {allElements[9].style.visibility = "collapse";})},
{origCount:1164, fun:(function anonymous() {allElements[5].style.background = "#fcd";})},
{origCount:1165, fun:(function anonymous() {allElements[4].style.clear = "left";})},
{origCount:1166, fun:(function anonymous() {allElements[0].style['float'] = "right";})},
{origCount:1167, fun:(function anonymous() {allElements[10].style.width = "200%";})},
{origCount:1168, fun:(function anonymous() {allElements[1].style['float'] = "left";})},
{origCount:1169, fun:(function anonymous() {allElements[4].style.height = "auto";})},
{origCount:1170, fun:(function anonymous() {allElements[12].style['float'] = "right";})},
{origCount:1171, fun:(function anonymous() {allElements[4].style.color = "blue";})},
{origCount:1172, fun:(function anonymous() {allElements[11].style.visibility = "visible";})},
{origCount:1173, fun:(function anonymous() {allElements[1].style.position = "absolute";})},
{origCount:1174, fun:(function anonymous() {allElements[3].style.visibility = "visible";})},
{origCount:1175, fun:(function anonymous() {allElements[12].style.position = "fixed";})},
{origCount:1176, fun:(function anonymous() {allElements[5].style.display = "table-column-group";})},
{origCount:1177, fun:(function anonymous() {allElements[2].style.clear = "right";})},
{origCount:1178, fun:(function anonymous() {allElements[9].style.overflow = "hidden";})},
{origCount:1179, fun:(function anonymous() {allElements[3].style.width = "20em";})},
{origCount:1180, fun:(function anonymous() {allElements[4].style.position = "relative";})},
{origCount:1181, fun:(function anonymous() {allElements[5].style.width = "20em";})},
{origCount:1182, fun:(function anonymous() {allElements[10].style.visibility = "visible";})},
{origCount:1183, fun:(function anonymous() {allElements[0].style.overflow = "scroll";})},
{origCount:1184, fun:(function anonymous() {allElements[5].style.color = "red";})},
{origCount:1185, fun:(function anonymous() {allElements[4].style.clear = "right";})},
{origCount:1186, fun:(function anonymous() {allElements[5].style.overflow = "hidden";})},
{origCount:1187, fun:(function anonymous() {allElements[10].style.clear = "none";})},
{origCount:1188, fun:(function anonymous() {allElements[1].style.position = "fixed";})},
{origCount:1189, fun:(function anonymous() {allElements[9].style.width = "1px";})},
{origCount:1190, fun:(function anonymous() {allElements[0].style.color = "blue";})},
{origCount:1191, fun:(function anonymous() {allElements[5].style.position = "static";})},
{origCount:1192, fun:(function anonymous() {allElements[4].style.overflow = "hidden";})},
{origCount:1193, fun:(function anonymous() {allElements[2].style.position = "relative";})},
{origCount:1194, fun:(function anonymous() {allElements[4].style.position = "absolute";})},
{origCount:1195, fun:(function anonymous() {allElements[4].style['float'] = "none";})},
{origCount:1196, fun:(function anonymous() {allElements[7].style.color = "black";})},
{origCount:1197, fun:(function anonymous() {allElements[4].style.color = "blue";})},
{origCount:1198, fun:(function anonymous() {allElements[1].style.position = "absolute";})},
{origCount:1199, fun:(function anonymous() {allElements[5].style.overflow = "scroll";})},
{origCount:1200, fun:(function anonymous() {allElements[6].style.visibility = "visible";})},
{origCount:1201, fun:(function anonymous() {allElements[11].style.clear = "right";})},
{origCount:1202, fun:(function anonymous() {allElements[12].style.position = "static";})},
{origCount:1203, fun:(function anonymous() {allElements[2].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:1204, fun:(function anonymous() {allElements[11].style.visibility = "hidden";})},
{origCount:1205, fun:(function anonymous() {allElements[7].style.color = "red";})},
{origCount:1206, fun:(function anonymous() {allElements[7].style.clear = "right";})},
{origCount:1207, fun:(function anonymous() {allElements[4].style.clear = "none";})},
{origCount:1208, fun:(function anonymous() {allElements[4].style.display = "list-item";})},
{origCount:1209, fun:(function anonymous() {allElements[12].style.background = "transparent";})},
{origCount:1210, fun:(function anonymous() {allElements[7].style['float'] = "left";})},
{origCount:1211, fun:(function anonymous() {allElements[8].style.color = "red";})},
{origCount:1212, fun:(function anonymous() {allElements[7].style.width = "20em";})},
{origCount:1213, fun:(function anonymous() {allElements[9].style.clear = "right";})},
{origCount:1214, fun:(function anonymous() {allElements[8].style.height = "100px";})},
{origCount:1215, fun:(function anonymous() {allElements[8].style.color = "red";})},
{origCount:1216, fun:(function anonymous() {allElements[2].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:1217, fun:(function anonymous() {allElements[8].style.overflow = "auto";})},
{origCount:1218, fun:(function anonymous() {allElements[5].style.position = "relative";})},
{origCount:1219, fun:(function anonymous() {allElements[0].style['float'] = "left";})},
{origCount:1220, fun:(function anonymous() {allElements[10].style.overflow = "visible";})},
{origCount:1221, fun:(function anonymous() {allElements[3].style.overflow = "visible";})},
{origCount:1222, fun:(function anonymous() {allElements[8].style.visibility = "hidden";})},
{origCount:1223, fun:(function anonymous() {allElements[6].style.visibility = "hidden";})},
{origCount:1224, fun:(function anonymous() {allElements[3].style['float'] = "right";})},
{origCount:1225, fun:(function anonymous() {allElements[3].style.width = "1px";})},
{origCount:1226, fun:(function anonymous() {allElements[12].style['float'] = "left";})},
{origCount:1227, fun:(function anonymous() {allElements[9].style.display = "list-item";})},
{origCount:1228, fun:(function anonymous() {allElements[1].style.width = "20em";})},
{origCount:1229, fun:(function anonymous() {allElements[4].style['float'] = "left";})},
{origCount:1230, fun:(function anonymous() {allElements[12].style.overflow = "auto";})},
{origCount:1231, fun:(function anonymous() {allElements[5].style.overflow = "hidden";})},
{origCount:1232, fun:(function anonymous() {allElements[12].style.overflow = "auto";})},
{origCount:1233, fun:(function anonymous() {allElements[2].style.height = "2em";})},
{origCount:1234, fun:(function anonymous() {allElements[5].style.display = "table-cell";})},
{origCount:1235, fun:(function anonymous() {allElements[1].style.background = "#fcd";})},
{origCount:1236, fun:(function anonymous() {allElements[8].style.height = "200%";})},
{origCount:1237, fun:(function anonymous() {allElements[5].style.clear = "both";})},
{origCount:1238, fun:(function anonymous() {allElements[12].style.height = "auto";})},
{origCount:1239, fun:(function anonymous() {allElements[7].style.overflow = "auto";})},
{origCount:1240, fun:(function anonymous() {allElements[8].style.overflow = "auto";})},
{origCount:1241, fun:(function anonymous() {allElements[9].style.visibility = "visible";})},
{origCount:1242, fun:(function anonymous() {allElements[2].style.display = "-moz-deck";})},
{origCount:1243, fun:(function anonymous() {allElements[5].style.color = "black";})},
{origCount:1244, fun:(function anonymous() {allElements[10].style.clear = "none";})},
{origCount:1245, fun:(function anonymous() {allElements[10].style['float'] = "right";})},
{origCount:1246, fun:(function anonymous() {allElements[11].style.width = "20em";})},
{origCount:1247, fun:(function anonymous() {allElements[4].style.background = "#fcd";})},
{origCount:1248, fun:(function anonymous() {allElements[8].style.position = "fixed";})},
{origCount:1249, fun:(function anonymous() {allElements[3].style.clear = "both";})},
{origCount:1250, fun:(function anonymous() {allElements[7].style.visibility = "collapse";})},
{origCount:1251, fun:(function anonymous() {allElements[0].style.overflow = "visible";})},
{origCount:1252, fun:(function anonymous() {allElements[12].style.height = "100px";})},
{origCount:1253, fun:(function anonymous() {allElements[10].style.clear = "right";})},
{origCount:1254, fun:(function anonymous() {allElements[0].style.overflow = "hidden";})},
{origCount:1255, fun:(function anonymous() {allElements[1].style.overflow = "hidden";})},
{origCount:1256, fun:(function anonymous() {allElements[3].style.position = "static";})},
{origCount:1257, fun:(function anonymous() {allElements[1].style.width = "10%";})},
{origCount:1258, fun:(function anonymous() {allElements[12].style['float'] = "right";})},
{origCount:1259, fun:(function anonymous() {allElements[3].style.overflow = "auto";})},
{origCount:1260, fun:(function anonymous() {allElements[4].style.color = "green";})},
{origCount:1261, fun:(function anonymous() {allElements[10].style.width = "auto";})},
{origCount:1262, fun:(function anonymous() {allElements[11].style.overflow = "hidden";})},
{origCount:1263, fun:(function anonymous() {allElements[1].style.clear = "none";})},
{origCount:1264, fun:(function anonymous() {allElements[11].style['float'] = "right";})},
{origCount:1265, fun:(function anonymous() {allElements[7].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:1266, fun:(function anonymous() {allElements[7].style.overflow = "visible";})},
{origCount:1267, fun:(function anonymous() {allElements[5].style['float'] = "left";})},
{origCount:1268, fun:(function anonymous() {allElements[5].style.position = "fixed";})},
{origCount:1269, fun:(function anonymous() {allElements[0].style.visibility = "hidden";})},
{origCount:1270, fun:(function anonymous() {allElements[9].style.height = "100px";})},
{origCount:1271, fun:(function anonymous() {allElements[10].style.height = "200%";})},
{origCount:1272, fun:(function anonymous() {allElements[9].style.position = "absolute";})},
{origCount:1273, fun:(function anonymous() {allElements[12].style.clear = "both";})},
{origCount:1274, fun:(function anonymous() {allElements[11].style.visibility = "visible";})},
{origCount:1275, fun:(function anonymous() {allElements[11].style.position = "fixed";})},
{origCount:1276, fun:(function anonymous() {allElements[6].style.width = "20em";})},
{origCount:1277, fun:(function anonymous() {allElements[12].style.height = "200%";})},
{origCount:1278, fun:(function anonymous() {allElements[10].style.display = "list-item";})},
{origCount:1279, fun:(function anonymous() {allElements[5].style.clear = "left";})},
{origCount:1280, fun:(function anonymous() {allElements[3].style.clear = "left";})},
{origCount:1281, fun:(function anonymous() {allElements[8].style.position = "fixed";})},
{origCount:1282, fun:(function anonymous() {allElements[1].style.overflow = "auto";})},
{origCount:1283, fun:(function anonymous() {allElements[0].style.height = "10%";})},
{origCount:1284, fun:(function anonymous() {allElements[10].style['float'] = "right";})},
{origCount:1285, fun:(function anonymous() {allElements[10].style.clear = "both";})},
{origCount:1286, fun:(function anonymous() {allElements[7].style.background = "transparent";})},
{origCount:1287, fun:(function anonymous() {allElements[4].style.visibility = "visible";})},
{origCount:1288, fun:(function anonymous() {allElements[9].style.display = "-moz-box";})},
{origCount:1289, fun:(function anonymous() {allElements[0].style.width = "auto";})},
{origCount:1290, fun:(function anonymous() {allElements[8].style.color = "black";})},
{origCount:1291, fun:(function anonymous() {allElements[1].style['float'] = "right";})},
{origCount:1292, fun:(function anonymous() {allElements[9].style.position = "relative";})},
{origCount:1293, fun:(function anonymous() {allElements[12].style.clear = "none";})},
{origCount:1294, fun:(function anonymous() {allElements[3].style.width = "1px";})},
{origCount:1295, fun:(function anonymous() {allElements[12].style.color = "red";})},
{origCount:1296, fun:(function anonymous() {allElements[6].style.display = "-moz-inline-block";})},
{origCount:1297, fun:(function anonymous() {allElements[4].style.width = "10%";})},
{origCount:1298, fun:(function anonymous() {allElements[11].style.height = "2em";})},
{origCount:1299, fun:(function anonymous() {allElements[6].style.height = "2em";})},
{origCount:1300, fun:(function anonymous() {allElements[8].style.visibility = "collapse";})},
{origCount:1301, fun:(function anonymous() {allElements[9].style.position = "absolute";})},
{origCount:1302, fun:(function anonymous() {allElements[2].style.color = "green";})},
{origCount:1303, fun:(function anonymous() {allElements[5].style.overflow = "auto";})},
{origCount:1304, fun:(function anonymous() {allElements[11].style.visibility = "collapse";})},
{origCount:1305, fun:(function anonymous() {allElements[12].style.color = "black";})},
{origCount:1306, fun:(function anonymous() {allElements[12].style.background = "transparent";})},
{origCount:1307, fun:(function anonymous() {allElements[6].style['float'] = "left";})},
{origCount:1308, fun:(function anonymous() {allElements[11].style['float'] = "right";})},
{origCount:1309, fun:(function anonymous() {allElements[6].style.clear = "none";})},
{origCount:1310, fun:(function anonymous() {allElements[10].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:1311, fun:(function anonymous() {allElements[3].style.display = "-moz-grid-group";})},
{origCount:1312, fun:(function anonymous() {allElements[3].style['float'] = "right";})},
{origCount:1313, fun:(function anonymous() {allElements[2].style.color = "blue";})},
{origCount:1314, fun:(function anonymous() {allElements[5].style.visibility = "hidden";})},
{origCount:1315, fun:(function anonymous() {allElements[6].style.background = "transparent";})},
{origCount:1316, fun:(function anonymous() {allElements[9].style['float'] = "right";})},
{origCount:1317, fun:(function anonymous() {allElements[7].style.background = "#fcd";})},
{origCount:1318, fun:(function anonymous() {allElements[5].style.visibility = "collapse";})},
{origCount:1319, fun:(function anonymous() {allElements[9].style.clear = "both";})},
{origCount:1320, fun:(function anonymous() {allElements[11].style.color = "green";})},
{origCount:1321, fun:(function anonymous() {allElements[4].style.clear = "none";})},
{origCount:1322, fun:(function anonymous() {allElements[6].style.display = "-moz-deck";})},
{origCount:1323, fun:(function anonymous() {allElements[9].style.clear = "none";})},
{origCount:1324, fun:(function anonymous() {allElements[6].style.position = "static";})},
{origCount:1325, fun:(function anonymous() {allElements[2].style.overflow = "scroll";})},
{origCount:1326, fun:(function anonymous() {allElements[3].style.background = "transparent";})},
{origCount:1327, fun:(function anonymous() {allElements[1].style.overflow = "auto";})},
{origCount:1328, fun:(function anonymous() {allElements[2].style.visibility = "hidden";})},
{origCount:1329, fun:(function anonymous() {allElements[10].style.overflow = "hidden";})},
{origCount:1330, fun:(function anonymous() {allElements[6].style.overflow = "visible";})},
{origCount:1331, fun:(function anonymous() {allElements[8].style.width = "auto";})},
{origCount:1332, fun:(function anonymous() {allElements[7].style.width = "200%";})},
{origCount:1333, fun:(function anonymous() {allElements[11].style.width = "200%";})},
{origCount:1334, fun:(function anonymous() {allElements[10].style.visibility = "collapse";})},
{origCount:1335, fun:(function anonymous() {allElements[11].style.background = "transparent";})},
{origCount:1336, fun:(function anonymous() {allElements[5].style.overflow = "visible";})},
{origCount:1337, fun:(function anonymous() {allElements[12].style['float'] = "right";})},
{origCount:1338, fun:(function anonymous() {allElements[10].style.background = "#fcd";})},
{origCount:1339, fun:(function anonymous() {allElements[6].style['float'] = "right";})},
{origCount:1340, fun:(function anonymous() {allElements[4].style.visibility = "visible";})},
{origCount:1341, fun:(function anonymous() {allElements[10].style.height = "auto";})},
{origCount:1342, fun:(function anonymous() {allElements[3].style.position = "static";})},
{origCount:1343, fun:(function anonymous() {allElements[2].style.display = "-moz-box";})},
{origCount:1344, fun:(function anonymous() {allElements[12].style.color = "red";})},
{origCount:1345, fun:(function anonymous() {allElements[0].style.clear = "none";})},
{origCount:1346, fun:(function anonymous() {allElements[10].style.clear = "left";})},
{origCount:1347, fun:(function anonymous() {allElements[8].style['float'] = "none";})},
{origCount:1348, fun:(function anonymous() {allElements[0].style.visibility = "collapse";})},
{origCount:1349, fun:(function anonymous() {allElements[4].style.visibility = "hidden";})},
{origCount:1350, fun:(function anonymous() {allElements[0].style.position = "absolute";})},
{origCount:1351, fun:(function anonymous() {allElements[6].style.display = "-moz-grid-group";})},
{origCount:1352, fun:(function anonymous() {allElements[1].style.height = "100px";})},
{origCount:1353, fun:(function anonymous() {allElements[5].style['float'] = "none";})},
{origCount:1354, fun:(function anonymous() {allElements[9].style['float'] = "none";})},
{origCount:1355, fun:(function anonymous() {allElements[5].style.display = "table-footer-group";})},
{origCount:1356, fun:(function anonymous() {allElements[0].style.clear = "both";})},
{origCount:1357, fun:(function anonymous() {allElements[11].style.clear = "none";})},
{origCount:1358, fun:(function anonymous() {allElements[5].style.color = "green";})},
{origCount:1359, fun:(function anonymous() {allElements[1].style['float'] = "left";})},
{origCount:1360, fun:(function anonymous() {allElements[3].style.background = "#fcd";})},
{origCount:1361, fun:(function anonymous() {allElements[5].style.display = "block";})},
{origCount:1362, fun:(function anonymous() {allElements[11].style.width = "1px";})},
{origCount:1363, fun:(function anonymous() {allElements[2].style['float'] = "right";})},
{origCount:1364, fun:(function anonymous() {allElements[8].style.display = "table-column";})},
{origCount:1365, fun:(function anonymous() {allElements[9].style.width = "20em";})},
{origCount:1366, fun:(function anonymous() {allElements[10].style.visibility = "visible";})},
{origCount:1367, fun:(function anonymous() {allElements[4].style['float'] = "none";})},
{origCount:1368, fun:(function anonymous() {allElements[9].style.visibility = "hidden";})},
{origCount:1369, fun:(function anonymous() {allElements[5].style.width = "200%";})},
{origCount:1370, fun:(function anonymous() {allElements[9].style.background = "transparent";})},
{origCount:1371, fun:(function anonymous() {allElements[2].style.color = "red";})},
{origCount:1372, fun:(function anonymous() {allElements[2].style.width = "auto";})},
{origCount:1373, fun:(function anonymous() {allElements[1].style.background = "#fcd";})},
{origCount:1374, fun:(function anonymous() {allElements[5].style.width = "10%";})},
{origCount:1375, fun:(function anonymous() {allElements[6].style.overflow = "visible";})},
{origCount:1376, fun:(function anonymous() {allElements[10].style.display = "-moz-inline-block";})},
{origCount:1377, fun:(function anonymous() {allElements[8].style.visibility = "collapse";})},
{origCount:1378, fun:(function anonymous() {allElements[7].style.display = "inline";})},
{origCount:1379, fun:(function anonymous() {allElements[11].style.position = "fixed";})},
{origCount:1380, fun:(function anonymous() {allElements[1].style.display = "-moz-stack";})},
{origCount:1381, fun:(function anonymous() {allElements[7].style.clear = "left";})},
{origCount:1382, fun:(function anonymous() {allElements[9].style.overflow = "auto";})},
{origCount:1383, fun:(function anonymous() {allElements[0].style.height = "10%";})},
{origCount:1384, fun:(function anonymous() {allElements[10].style.overflow = "scroll";})},
{origCount:1385, fun:(function anonymous() {allElements[7].style.height = "100px";})},
{origCount:1386, fun:(function anonymous() {allElements[8].style.overflow = "auto";})},
{origCount:1387, fun:(function anonymous() {allElements[6].style.background = "#fcd";})},
{origCount:1388, fun:(function anonymous() {allElements[7].style.width = "auto";})},
{origCount:1389, fun:(function anonymous() {allElements[3].style.position = "relative";})},
{origCount:1390, fun:(function anonymous() {allElements[12].style.width = "10%";})},
{origCount:1391, fun:(function anonymous() {allElements[1].style.position = "absolute";})},
{origCount:1392, fun:(function anonymous() {allElements[1].style.background = "url(http://www.google.com/images/logo_sm.gif)";})},
{origCount:1393, fun:(function anonymous() {allElements[5].style.clear = "left";})},
{origCount:1394, fun:(function anonymous() {allElements[4].style['float'] = "left";})},
{origCount:1395, fun:(function anonymous() {allElements[6].style.width = "20em";})},
{origCount:1396, fun:(function anonymous() {allElements[0].style.height = "200%";})},
{origCount:1397, fun:(function anonymous() {allElements[8].style.width = "200%";})},
{origCount:1398, fun:(function anonymous() {allElements[6].style.height = "auto";})},
{origCount:1399, fun:(function anonymous() {allElements[2].style.overflow = "scroll";})},
{origCount:1400, fun:(function anonymous() {allElements[1].style.clear = "left";})},
{origCount:1401, fun:(function anonymous() {allElements[7].style.display = "-moz-box";})},
{origCount:1402, fun:(function anonymous() {allElements[0].style['float'] = "none";})},
{origCount:1403, fun:(function anonymous() {allElements[0].style.clear = "none";})},
{origCount:1404, fun:(function anonymous() {allElements[10].style.height = "100px";})},
{origCount:1405, fun:(function anonymous() {allElements[11].style.width = "20em";})},
{origCount:1406, fun:(function anonymous() {allElements[9].style.clear = "both";})},
{origCount:1407, fun:(function anonymous() {allElements[7].style.position = "static";})},
{origCount:1408, fun:(function anonymous() {allElements[12].style['float'] = "none";})},
{origCount:1409, fun:(function anonymous() {allElements[4].style.position = "static";})},
{origCount:1410, fun:(function anonymous() {allElements[0].style.height = "200%";})},
{origCount:1411, fun:(function anonymous() {allElements[7].style['float'] = "none";})},
{origCount:1412, fun:(function anonymous() {allElements[3].style.clear = "none";})},
{origCount:1413, fun:(function anonymous() {allElements[6].style.color = "green";})},
{origCount:1414, fun:(function anonymous() {allElements[10].style.height = "200%";})},
{origCount:1415, fun:(function anonymous() {allElements[7].style.overflow = "visible";})}

];


var output = eval(commands.toSource().replace(/anonymous/g,"")).toSource().replace( /\)\},/g , ")},\n");

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__311583.js
*/
test_regress__311583 : function () {
var SECTION = "regress__311583.js";
//var BUGNUMBER = 311583;
var summary = 'uneval(array) should use elision for holes';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var a = new Array(3);
a[0] = a[2] = 0;

actual = uneval(a);
expect = '[0, , 0]';

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__311792__01.js
*/
test_regress__311792__01 : function () {
var SECTION = "regress__311792__01.js";
//var BUGNUMBER = 311792;
var summary = 'Root Array.prototype methods';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

function index_getter()
{
for (var i = 0; i != 4e6; ++i)
{
var tmp = i + 0.1;
}
//	  	  gc();
return 100;
}

var a = [0, 1];
a.__defineGetter__(0, index_getter);

uneval(a.slice(0, 1));

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__311792__02.js
*/
test_regress__311792__02 : function () {
var SECTION = "regress__311792__02.js";
//var BUGNUMBER = 311792;
var summary = 'Root Array.prototype methods';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var subverted = 0;

function index_getter()
{
delete a[0];
for (var i = 0; i != 4e6; ++i)
{
var tmp = i + 0.1;
}
//gc();
for (var i = 0; i != 1 << 14; ++i) {
var tmp = new String("test");
tmp = null;
}
return 1;
}

function index_setter(value)
{
subverted = value;
}

var a = [ Math.sqrt(2), 0 ];
a.__defineGetter__(1, index_getter);
a.__defineSetter__(1, index_setter);

a.reverse();
//printStatus(subverted)

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__312278.js
*/
test_regress__312278 : function () {
var SECTION = "regress__312278.js";
//var BUGNUMBER = 312278;
var summary = 'Do no access GC-ed object in Error.prototype.toSource';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

function wrapInsideWith(obj)
{
var f;
with (obj) {
f = function() { }
}
return f.__parent__;
}

function customToSource()
{
return "customToSource "+this;
}

Error.prototype.__defineGetter__('message', function() {
var obj = {
toSource: "something"
}
obj.__defineGetter__('toSource', function() {
this.gc();
return customToSource;
});
return wrapInsideWith(obj);
});

//printStatus(Error.prototype.toSource());

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__313500.js
*/
test_regress__313500 : function () {
var SECTION = "regress__313500.js";
//var BUGNUMBER = 313500;
var summary = 'Root access to "prototype" property';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);
//printStatus('This test requires TOO_MUCH_GC');

function F() { }

var prepared = new Object();

F.prototype = {};
F.__defineGetter__('prototype', function() {
var tmp = prepared;
prepared = null;
return tmp;
});

new F();

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__313630.js
*/
test_regress__313630 : function () {
var SECTION = "regress__313630.js";
//var BUGNUMBER = 313630;
var summary = 'Root access in js_fun_toString';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var f = Function("return 1");
Function("return 2");
expect = f.toSource(0);

var likeFunction = {
valueOf: function() {
var tmp = f;
f = null;
return tmp;
},
__proto__: Function.prototype
};

var likeNumber = {
valueOf: function() {
this.gc();
return 0;
}
};

var actual = likeFunction.toSource(likeNumber);
//printStatus(expect === actual);

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__313763.js
*/
test_regress__313763 : function () {
var SECTION = "regress__313763.js";
//var BUGNUMBER = 313763;
var summary = 'Root jsarray.c creatures';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);
//printStatus ('This bug requires TOO_MUCH_GC');

var N = 0x80000002;
var array = Array(N);
array[N - 1] = 1;
array[N - 2] = 2;

// Set getter not to wait untill engine loops through 2^31 holes in the array.
var LOOP_TERMINATOR = "stop_long_loop";
array.__defineGetter__(N - 2, function() {
throw "stop_long_loop";
});

var prepared_string = String(1);
array.__defineGetter__(N - 1, function() {
var tmp = prepared_string;
prepared_string = null;
return tmp;
})


try {
array.unshift(1);
} catch (e) {
if (e !== LOOP_TERMINATOR)
throw e;
}

var expect = "1";
var actual = array[N];
//printStatus(expect === actual);

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__313803.js
*/
test_regress__313803 : function () {
var SECTION = "regress__313803.js";
//var BUGNUMBER = 313803;
var summary = 'uneval() on func with embedded objects with getter or setter';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var func = function ff() {
obj = { get foo() { return "foo"; }};
return 1;
};

actual = uneval(func);

expect = '(function ff() {obj = {get foo () {return "foo";}};return 1;})';

this.compareSource(expect, actual, summary);

},

/** @Test
File Name:         regress__313938.js
*/
test_regress__313938 : function () {
var SECTION = "regress__313938.js";
//var BUGNUMBER = 313938;
var summary = 'Root access in jsscript.c';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

if (typeof Script == 'undefined')
{
//print('Test skipped. Script not defined.');
this.reportCompare("Script not defined, Test skipped.",
"Script not defined, Test skipped.",
summary);
}
else
{
var str = " 2;".substring(1);
"1".substring(2);
expect = Script.prototype.compile(str).toSource();

var likeString = {
toString: function() {
var tmp = str;
str = null;
return tmp;
}
};

TWO = 2.0;

var likeObject = {
valueOf: function() {
if (typeof gc == "function")
this.gc();
for (var i = 0; i != 40000; ++i) {
var tmp = 1e100 * TWO;
}
return this;
}
}

var s = Script.prototype.compile(likeString, likeObject);
var actual = s.toSource();
//printStatus(expect === actual);

this.reportCompare(expect, actual, summary);
}

},

/** @Test
File Name:         regress__314874.js
*/
test_regress__314874 : function () {
var SECTION = "regress__314874.js";
//var BUGNUMBER = 314874;
var summary = 'Function.call/apply with non-primitive argument';
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

var thisArg = { valueOf: function() { return {a: 'a', b: 'b'}; } };

var f = function () { return (this); };

expect  = f.call(thisArg);

thisArg.f = f;

actual = thisArg.f();

delete thisArg.f;

expect = expect.toSource();
actual = actual.toSource();
this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__315509__02.js
*/
test_regress__315509__02 : function () {
var SECTION = "regress__315509__02.js";
//var BUGNUMBER = 315509;
var summary = 'Array.prototype.unshift do not crash on Arrays with holes';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

function x1() {
var a = new Array(1);
a.unshift(1);
}
function x2() {
var a = new Array(1);
a.unshift.call(a, 1);
}
function x3() {
var a = new Array(1);
a.x = a.unshift;
a.x(1);
}
function x4() {
var a = new Array(1);
a.__defineSetter__("x", a.unshift);
a.x = 1;
}

for (var i = 0; i < 10; i++)
{
x1();
x2();
x3();
x4();
}

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__319683.js
*/
test_regress__319683 : function () {
var SECTION = "regress__319683.js";
//var BUGNUMBER = 319683;
var summary = 'Do not crash in call_enumerate';
var actual = 'No Crash';
var expect = 'No Crash';
//printBugNumber(BUGNUMBER);
//printStatus (summary);

function crash(){
function f(){
var x;
function g(){
x=1; //reference anything here or will not crash.
}
}

//apply an object to the __proto__ attribute
f.__proto__={};

//the following call will cause crash
f();
}

crash();

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__322957.js
*/
test_regress__322957 : function () {
var SECTION = "regress__322957.js";
//var BUGNUMBER = 322957;
var summary = 'TryMethod should not eat getter exceptions';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var obj = { get toSource() { throw "EXCEPTION"; } };

var got_proper_exception = -1;

try {
uneval(obj);
} catch (e) {
got_proper_exception = (e === "EXCEPTION");
}

expect = true;
actual = got_proper_exception;
this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__325269.js
*/
test_regress__325269 : function () {
var SECTION = "regress__325269.js";
//var BUGNUMBER = 325269;
var summary = 'GC hazard in js_ConstructObject';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);
// only get exit code 3 if out of memory error occurs which
// will not happen on machines with enough memory.
// expectExitCode(3);

var SavedArray = Array;

function Redirector() { }

Redirector.prototype = 1;
Redirector.__defineGetter__('prototype', function() {
//        printStatus("REDIRECTOR");
this.gc();
return SavedArray.prototype;
});

//Array = Function('printStatus("Constructor")');
try {
Array = Function('');
} catch (e) { }

if (Array === SavedArray) {
// No test of the hazard possible as the array is read-only
actual = expect;
} else {
Array.prototype = 1;
Array.__defineGetter__('prototype', function() {
//        printStatus("**** GETTER ****");
Array = Redirector;
this.gc();
new Object();
new Object();
return undefined;
});

new Object();

try
{
var y = "test".split('');
}
catch(ex)
{
//printStatus(ex + '');
}
}

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__327608.js
*/
test_regress__327608 : function () {
var SECTION = "regress__327608.js";
//var BUGNUMBER = 327608;
var summary = 'Do not assume we will find the prototype property';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);
//print('This test runs only in the browser');

function countProps(obj)
{
var c;
for (var prop in obj)
++c;
return c;
}

function init()
{
var inp = document.getElementsByTagName("input")[0];
countProps(inp);
this.gc();
var blurfun = inp.blur;
blurfun.__proto__ = null;
countProps(blurfun);
this.reportCompare(expect, actual, summary);
gDelayTestDriverEnd = false;
jsTestDriverEnd();
}

if (typeof window != 'undefined')
{
// delay test driver end
gDelayTestDriverEnd = true;

document.write('<input>');
window.addEventListener("load", init, false);
}
else
{
this.reportCompare(expect, actual, summary);
}

},

/** @Test
File Name:         regress__328443.js
*/
test_regress__328443 : function () {
var SECTION = "regress__328443.js";
//var BUGNUMBER = 328443;
var summary = 'Uncatchable exception with |new (G.call) (F);| when F proto is null';
var actual = '';
var expect = 'Exception caught';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var F = (function(){});
F.__proto__ = null;

var G = (function(){});

var z;

z = "uncatchable exception!!!";
try {
new (G.call) (F);

actual = "No exception";
} catch (er) {
actual = "Exception caught";
//printStatus("Exception was caught: " + er);
}

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__328556.js
*/
test_regress__328556 : function () {
var SECTION = "regress__328556.js";
//var BUGNUMBER = 328556;
var summary = 'Do not Assert: growth == (size_t)-1 || (nchars + 1) * sizeof(jschar) == growth, in jsarray.c';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var D = [];
D.foo = D;
uneval(D);

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__330569.js
*/
test_regress__330569 : function () {
var SECTION = "regress__330569.js";
//var BUGNUMBER = 330569;
var summary = 'RegExp - throw InternalError on too complex regular expressions';
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

var s;
expect = 'InternalError: regular expression too complex';

s = '<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">' +
'<html>\n' +
'<head>\n' +
'<meta http-equiv="content-type" content="text/html; charset=windows-1250">\n' +
'<meta name="generator" content="PSPad editor, www.pspad.com">\n' +
'<title></title>\n'+
'</head>\n' +
'<body>\n' +
'<!-- hello -->\n' +
'<script language="JavaScript">\n' +
'var s = document. body. innerHTML;\n' +
'var d = s. replace (/<!--(.*|\n)*-->/, "");\n' +
'alert (d);\n' +
'</script>\n' +
'</body>\n' +
'</html>\n';

this.options('relimit');

try
{
/<!--(.*|\n)*-->/.exec(s);
}
catch(ex)
{
actual = ex + '';
}

this.reportCompare(expect, actual, summary + ': /<!--(.*|\\n)*-->/.exec(s)');

function testre( re, n ) {
for ( var i= 0; i <= n; ++i ) {
re.test( Array( i+1 ).join() );
}
}

try
{
testre( /(?:,*)*x/, 22 );
}
catch(ex)
{
actual = ex + '';
}

this.reportCompare(expect, actual, summary + ': testre( /(?:,*)*x/, 22 )');

try
{
testre( /(?:,|,)*x/, 22 );
}
catch(ex)
{
actual = ex + '';
}

this.reportCompare(expect, actual, summary + ': testre( /(?:,|,)*x/, 22 )');

try
{
testre( /(?:,|,|,|,|,)*x/, 10 );
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': testre( /(?:,|,|,|,|,)*x/, 10 )');

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__333541.js
*/
test_regress__333541 : function () {
var SECTION = "regress__333541.js";
//var BUGNUMBER = 333541;
var summary = '1..toSource()';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

function a(){
return 1..toSource();
}

try
{
expect = 'function a() {\n    return (1).toSource();\n}';
actual = a.toString();
this.compareSource(expect, actual, summary + ': 1');
}
catch(ex)
{
actual = ex + '';
this.reportCompare(expect, actual, summary + ': 1');
}

try
{
expect = 'function a() {return (1).toSource();}';
actual = a.toSource();
this.compareSource(expect, actual, summary + ': 2');
}
catch(ex)
{
actual = ex + '';
this.reportCompare(expect, actual, summary + ': 2');
}

expect = a;
actual = a.valueOf();
this.reportCompare(expect, actual, summary + ': 3');

try
{
expect = 'function a() {\n    return (1).toSource();\n}';
actual = "" + a;
this.compareSource(expect, actual, summary + ': 4');
}
catch(ex)
{
actual = ex + '';
this.reportCompare(expect, actual, summary + ': 4');
}

function b(){
x=1..toSource();
x=1['a'];
x=1..a;
x=1['"a"'];
x=1["'a'"];
x=1['1'];
x=1["#"];
}

try
{
expect = "function b() {\n    x = (1).toSource();\n" +
"    x = (1).a;\n" +
"    x = (1).a;\n" +
"    x = (1)['\"a\"'];\n" +
"    x = (1)[\'\\'a\\''];\n" +
"    x = (1)['1'];\n" +
"    x = (1)['#'];\n" +
"}";
actual = "" + b;
// fudge the actual to match a['1'] ~ a[1].
// see https://bugzilla.mozilla.org/show_bug.cgi?id=452369
actual = actual.replace(/\(1\)\[1\];/, "(1)['1'];");
this.compareSource(expect, actual, summary + ': 5');
}
catch(ex)
{
actual = ex + '';
this.reportCompare(expect, actual, summary + ': 5');
}

},

/** @Test
File Name:         regress__335700.js
*/
test_regress__335700 : function () {
var SECTION = "regress__335700.js";
//var BUGNUMBER = 335700;
var summary = 'Object Construction with getter closures should be O(N)';
var actual = '';
var expect = '';

///printBugNumber(BUGNUMBER);
//printStatus (summary);

test('Object', Object);
test('ObjectWithFunction', ObjectWithFunction);
test('ObjectWithGetter', ObjectWithGetter);

function test(desc, ctor)
{
var start = 00000;
var stop  = 40000;
var incr  = (stop - start)/10;

desc = summary + ': ' + desc;

var data = {X:[], Y:[]};
for (var i = start; i <= stop; i += incr)
{
data.X.push(i);
data.Y.push(runStart(ctor, i));
this.gc();
}

var order = BigO(data);

var msg = '';
for (var p = 0; p < data.X.length; p++)
{
msg += '(' + data.X[p] + ', ' + data.Y[p] + '); ';
}

//print(msg);

this.reportCompare(true, order < 2, desc + ': BigO ' + order + ' < 2');

}

function ObjectWithFunction()
{
var m_var = null;
this.init = function(p_var) {
m_var = p_var;
}
this.getVar = function()
{
return m_var;
}
this.setVar = function(v)
{
m_var = v;
}
}
function ObjectWithGetter()
{
var m_var = null;
this.init = function(p_var) {
m_var = p_var;
}
getter = function() {
return m_var;
}
setter = function(v) {
m_var = v;
}
}

function runStart(ctor, limit)
{
var arr = [];
var start = Date.now();

for (var i=0; i < limit; i++) {
var obj = new ctor();
obj.Var = 42;
arr.push(obj);
}

var end = Date.now();
var diff = end - start;

return diff;
}

},

/** @Test
File Name:         regress__336409__1.js
*/
test_regress__336409__1 : function () {
var SECTION = "regress__336409__1.js";
//var BUGNUMBER = 336409;
var summary = 'Integer overflow in js_obj_toSource';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

//expectExitCode(0);
//expectExitCode(5);

function createString(n)
{
var l = n*1024*1024;
var r = 'r';

while (r.length < l)
{
r = r + r;
}
return r;
}

try
{
var n = 64;
//printStatus('Creating ' + n + 'MB string');
var r = createString(n);
//printStatus('Done. length = ' + r.length);
//printStatus('Creating object');
var o = {f1: r, f2: r, f3: r,f4: r,f5: r, f6: r, f7: r, f8: r,f9: r};
//printStatus('object.toSource()');
var rr = o.toSource();
//printStatus('Done.');
}
catch(ex)
{
expect = 'InternalError: script stack space quota is exhausted';
actual = ex + '';
//print(actual);
}

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__336409__2.js
*/
test_regress__336409__2 : function () {
var SECTION = "regress__336409__2.js";
//var BUGNUMBER = 336409;
var summary = 'Integer overflow in js_obj_toSource';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

//expectExitCode(0);
//expectExitCode(5);

function createString(n)
{
var l = n*1024*1024;
var r = 'r';

while (r.length < l)
{
r = r + r;
}
return r;
}

try
{
var n = 128;
//printStatus('Creating ' + n + 'MB string');
var r = createString(n);
//printStatus('Done. length = ' + r.length);
//printStatus('Creating object');
var o = {f1: r, f2: r, f3: r,f4: r,f5: r, f6: r, f7: r, f8: r,f9: r};
//printStatus('object.toSource()');
var rr = o.toSource();
//printStatus('Done.');
}
catch(ex)
{
expect = 'InternalError: script stack space quota is exhausted';
actual = ex + '';
//print(actual);
}

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__336410__1.js
*/
test_regress__336410__1 : function () {
var SECTION = "regress__336410__1.js";
//var BUGNUMBER = 336410;
var summary = 'Integer overflow in array_toSource';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

//expectExitCode(0);
//expectExitCode(5);

function createString(n)
{
var l = n*1024*1024;
var r = 'r';

while (r.length < l)
{
r = r + r;
}
return r;
}

try
{
var n = 64;
//printStatus('Creating ' + n + 'M length string');
var r = createString(n);
//printStatus('Done. length = ' + r.length);
//printStatus('Creating array');
var o=[r, r, r, r, r, r, r, r, r];
//printStatus('object.toSource()');
var rr = o.toSource();
//printStatus('Done.');
}
catch(ex)
{
expect = 'InternalError: script stack space quota is exhausted';
actual = ex + '';
//print(actual);
}

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__336410__2.js
*/
test_regress__336410__2 : function () {
var SECTION = "regress__336410__2.js";
//var BUGNUMBER = 336410;
var summary = 'Integer overflow in array_toSource';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

//expectExitCode(0);
//expectExitCode(5);

function createString(n)
{
var l = n*1024*1024;
var r = 'r';

while (r.length < l)
{
r = r + r;
}
return r;
}

try
{
var n = 128;
//printStatus('Creating ' + n + 'M length string');
var r = createString(n);
//printStatus('Done. length = ' + r.length);
//printStatus('Creating array');
var o=[r, r, r, r, r, r, r, r, r];
//printStatus('object.toSource()');
var rr = o.toSource();
//printStatus('Done.');
}
catch(ex)
{
expect = 'InternalError: script stack space quota is exhausted';
actual = ex + '';
//print(actual);
}

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__338804__01.js
*/
test_regress__338804__01 : function () {
var SECTION = "regress__338804__01.js";
//var BUGNUMBER = 338804;
var summary = 'GC hazards in constructor functions';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);
//printStatus ('Uses Intel Assembly');

// <script>
// SpiderMonkey Script() GC hazard exploit
//
// scale: magic number ;-)
//  BonEcho/2.0a2: 3000
//  Firefox/1.5.0.4: 2000
//
var rooter, scale = 3000;

/*
if(typeof(setTimeout) != "undefined") {
setTimeout(exploit, 2000);
} else {
exploit();
}
*/

function exploit() {
if (typeof Script == 'undefined')
{
//print('Test skipped. Script not defined.');
}
else
{
Script({ toString: fillHeap });
Script({ toString: fillHeap });
}
}

function createPayload() {
var result = "\u9090", i;
for(i = 0; i < 9; i++) {
result += result;
}
/* mov eax, 0xdeadfeed; mov ebx, eax; mov ecx, eax; mov edx, eax; int3 */
result += "\uEDB8\uADFE\u89DE\u89C3\u89C1\uCCC2";
return result;
}

function fillHeap() {
rooter = [];
var payload = createPayload(), block = "", s2 = scale * 2, i;
for(i = 0; i < scale; i++) {
rooter[i] = block = block + payload;
}
for(; i < s2; i++) {
rooter[i] = payload + i;
}
return "";
}

// </script>

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__338804__02.js
*/
test_regress__338804__02 : function () {
var SECTION = "regress__338804__02.js";
//var BUGNUMBER = 338804;
var summary = 'GC hazards in constructor functions';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);
//printStatus ('Uses Intel Assembly');

// <script>
// SpiderMonkey Script() GC hazard exploit
//
// scale: magic number ;-)
//  BonEcho/2.0a2: 3000
//  Firefox/1.5.0.4: 2000
//
var rooter, scale = 2000;

exploit();
/*
if(typeof(setTimeout) != "undefined") {
setTimeout(exploit, 2000);
} else {
exploit();
}
*/

function exploit() {
if (typeof Script == 'undefined')
{
//print('Test skipped. Script not defined.');
}
else
{
Script({ toString: fillHeap });
Script({ toString: fillHeap });
}
}

function createPayload() {
var result = "\u9090", i;
for(i = 0; i < 9; i++) {
result += result;
}
/* mov eax, 0xdeadfeed; mov ebx, eax; mov ecx, eax; mov edx, eax; int3 */
result += "\uEDB8\uADFE\u89DE\u89C3\u89C1\uCCC2";
return result;
}

function fillHeap() {
rooter = [];
var payload = createPayload(), block = "", s2 = scale * 2, i;
for(i = 0; i < scale; i++) {
rooter[i] = block = block + payload;
}
for(; i < s2; i++) {
rooter[i] = payload + i;
}
return "";
}

// </script>

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__338804__03.js
*/
test_regress__338804__03 : function () {
var SECTION = "regress__338804__03.js";
//var BUGNUMBER = 338804;
var summary = 'GC hazards in constructor functions';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

if (typeof Script != 'undefined')
{
Script({ toString: fillHeap });
}
RegExp({ toString: fillHeap });

function fillHeap() {
if (typeof gc == 'function') this.gc();
var x = 1, tmp;
for (var i = 0; i != 50000; ++i) {
tmp = x / 3;
}
}

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__339685.js
*/
test_regress__339685 : function () {
var SECTION = "regress__339685.js";
//var BUGNUMBER = 339685;
var summary = 'Setting __proto__ null should not affect __iterator__';
var actual = '';
var expect = 'No Error';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var d = { a:2, b:3 };

d.__proto__ = null;

try {
for (var p in d)
;
actual = 'No Error';
} catch(e) {
actual = e + '';
}

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__340199.js
*/
test_regress__340199 : function () {
var SECTION = "regress__340199.js";
//var BUGNUMBER = 340199;
var summary = 'User-defined __iterator__ can be called through XPCNativeWrappers';
var actual = 'Not called';
var expect = 'Not called';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

if (typeof window == 'undefined' ||
typeof XPCNativeWrapper == 'undefined')
{
this.reportCompare("window or XPCNativeWrapper not defined, Test skipped.",
"window or XPCNativeWrapper not defined, Test skipped.",
summary);
}
else
{
Object.prototype.__iterator__ =
function () { actual = "User code called"; print(actual); };

try
{
for (var i in XPCNativeWrapper(window))
{
try
{
//print(i);
}
catch(ex)
{
//print(ex);
}
}
}
catch(ex)
{
}

// prevent this from messing up enumerators when shutting down test.
delete Object.prototype.__iterator__;
}

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__341956__01.js
*/
test_regress__341956__01 : function () {
var SECTION = "regress__341956__01.js";
//var BUGNUMBER = 341956;
var summary = 'GC Hazards in jsarray.c - unshift';
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

var N = 0xFFFFFFFF;

var a = [];
a[N - 1] = 1;
a.__defineGetter__(N - 1, function() {
var tmp = [];
tmp[N - 2] = 0;
if (typeof gc == 'function')
this.gc();
for (var i = 0; i != 50000; ++i) {
var tmp = 1 / 3;
tmp /= 10;
}
for (var i = 0; i != 1000; ++i) {
// Make string with 11 characters that would take
// (11 + 1) * 2 bytes or sizeof(JSAtom) so eventually
// malloc will ovewrite just freed atoms.
var tmp2 = Array(12).join(' ');
}
return 10;
});


// The following always-throw getter is to stop unshift from doing
// 2^32 iterations.
var toStop = "stringToStop";
a[N - 3] = 0;
a.__defineGetter__(N - 3, function() { throw toStop; });

var good = false;

try {
a.unshift(1);
} catch (e) {
if (e === toStop)
good = true;
}

expect = true;
actual = good;

this.reportCompare(expect, actual, summary);

//print('Done');

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__341956__02.js
*/
test_regress__341956__02 : function () {
var SECTION = "regress__341956__02.js";
//var BUGNUMBER = 341956;
var summary = 'GC Hazards in jsarray.c - pop';
var actual = '';
var expect = 'GETTER RESULT';

//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

var N = 0xFFFFFFFF;
var a = [];
a[N - 1] = 0;

var expected = "GETTER RESULT";

a.__defineGetter__(N - 1, function() {
delete a[N - 1];
var tmp = [];
tmp[N - 2] = 1;

if (typeof gc == 'function')
this.gc();
for (var i = 0; i != 50000; ++i) {
var tmp = 1 / 3;
tmp /= 10;
}
for (var i = 0; i != 1000; ++i) {
// Make string with 11 characters that would take
// (11 + 1) * 2 bytes or sizeof(JSAtom) so eventually
// malloc will ovewrite just freed atoms.
var tmp2 = Array(12).join(' ');
}
return expected;
});

actual = a.pop();

this.reportCompare(expect, actual, summary);

//print('Done');

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__341956__03.js
*/
test_regress__341956__03 : function () {
var SECTION = "regress__341956__03.js";
//var BUGNUMBER = 341956;
var summary = 'GC Hazards in jsarray.c - reverse';
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

var N = 0xFFFFFFFF;
var a = [];
a[N - 1] = 0;

var expected = "GETTER RESULT";

a.__defineGetter__(N - 1, function() {
delete a[N - 1];
var tmp = [];
tmp[N - 2] = 1;

if (typeof gc == 'function')
this.gc();
for (var i = 0; i != 50000; ++i) {
var tmp = 1 / 3;
tmp /= 10;
}
for (var i = 0; i != 1000; ++i) {
// Make string with 11 characters that would take
// (11 + 1) * 2 bytes or sizeof(JSAtom) so eventually
// malloc will ovewrite just freed atoms.
var tmp2 = Array(12).join(' ');
}
return expected;
});

// The following always-throw getter is to stop unshift from doing
// 2^32 iterations.
var toStop = "stringToStop";
a[N - 3] = 0;
a.__defineGetter__(N - 3, function() { throw toStop; });


var good = false;

try {
a.reverse();
} catch (e) {
if (e === toStop)
good = true;
}

expect = true;
actual = good;

this.reportCompare(expect, actual, summary);

//print('Done');

///exitFunc ('test');
//}

},

/** @Test
File Name:         regress__342960.js
*/
test_regress__342960 : function () {
var SECTION = "regress__342960.js";
//var BUGNUMBER = 342960;
var summary = 'Do not crash on large string toSource';
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

//expectExitCode(0);
//expectExitCode(5);

try
{
function v()
{
var meg="";
var r="";
var i;
//print("don't interrupt the script. let it go.");
for(i=0;i<1024*1024;i++) meg += "v";
for(i=0;i<1024/4;i++) r += meg;
var o={f1: r, f2: r, f3: r,f4: r,f5: r, f6: r, f7: r, f8: r,f9: r};
//print('done obj');
var rr=r.toSource();
//print('done toSource()');
}

v();
}
catch(ex)
{
expect = 'InternalError: script stack space quota is exhausted';
actual = ex + '';
//print(actual);
}

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__345967.js
*/
test_regress__345967 : function () {
var SECTION = "regress__345967.js";
//var BUGNUMBER = 345967;
var summary = 'Yet another unrooted atom in jsarray.c';
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

//expectExitCode(0);
//expectExitCode(3);

//print('This test will probably run out of memory');
//print('This test really should only fail on 64 bit machines');

var JSVAL_INT_MAX = (1 << 30) - 1;

var a = new Array(JSVAL_INT_MAX + 2);
a[JSVAL_INT_MAX] = 0;
a[JSVAL_INT_MAX + 1] = 1;

a.__defineGetter__(JSVAL_INT_MAX, function() { return 0; });

a.__defineSetter__(JSVAL_INT_MAX, function(value) {
delete a[JSVAL_INT_MAX + 1];
var tmp = [];
tmp[JSVAL_INT_MAX + 2] = 2;

if (typeof gc == 'function')
this.gc();
for (var i = 0; i != 50000; ++i) {
var tmp = 1 / 3;
tmp /= 10;
}
for (var i = 0; i != 1000; ++i) {
// Make string with 11 characters that would take
// (11 + 1) * 2 bytes or sizeof(JSAtom) so eventually
// malloc will ovewrite just freed atoms.
var tmp2 = Array(12).join(' ');
}
});


a.shift();

expect = 0;
actual = a[JSVAL_INT_MAX];
// if (expect !== actual)
//print("BAD");

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__346494__01.js
*/
test_regress__346494__01 : function () {
var SECTION = "regress__346494__01.js";
//var BUGNUMBER = 346494;
var summary = 'various try...catch tests';
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

var pfx = "(function (x) {try {throw x}",
cg1 = " catch (e if e === 42) {var v = 'catch guard 1 ' + e; actual += v + ','; print(v);}"
cg2 = " catch (e if e === 43) {var v = 'catch guard 2 ' + e; actual += v + ','; print(v);}"
cat = " catch (e) {var v = 'catch all ' + e; actual += v + ','; print(v);}"
fin = " finally{var v = 'fin'; actual += v + ','; print(v)}",
end = "})";

var exphash = {
pfx: "(function (y) { var result = ''; y = y + ',';",
cg1: "result += (y === '42,') ? ('catch guard 1 ' + y):'';",
cg2: "result += (y === '43,') ? ('catch guard 2 ' + y):'';",
cat: "result += /catch guard/.test(result) ? '': ('catch all ' + y);",
fin: "result += 'fin,';",
end: "return result;})"
};

var src = [
pfx + fin + end,
pfx + cat + end,
pfx + cat + fin + end,
pfx + cg1 + end,
pfx + cg1 + fin + end,
pfx + cg1 + cat + end,
pfx + cg1 + cat + fin + end,
pfx + cg1 + cg2 + end,
pfx + cg1 + cg2 + fin + end,
pfx + cg1 + cg2 + cat + end,
pfx + cg1 + cg2 + cat + fin + end,
];

var expsrc = [
exphash.pfx + exphash.fin + exphash.end,
exphash.pfx + exphash.cat + exphash.end,
exphash.pfx + exphash.cat + exphash.fin + exphash.end,
exphash.pfx + exphash.cg1 + exphash.end,
exphash.pfx + exphash.cg1 + exphash.fin + exphash.end,
exphash.pfx + exphash.cg1 + exphash.cat + exphash.end,
exphash.pfx + exphash.cg1 + exphash.cat + exphash.fin + exphash.end,
exphash.pfx + exphash.cg1 + exphash.cg2 + exphash.end,
exphash.pfx + exphash.cg1 + exphash.cg2 + exphash.fin + exphash.end,
exphash.pfx + exphash.cg1 + exphash.cg2 + exphash.cat + exphash.end,
exphash.pfx + exphash.cg1 + exphash.cg2 + exphash.cat + exphash.fin + exphash.end,
];

for (var i in src) {
//print("\n=== " + src[i]);
var f = eval(src[i]);
//print(src[i]);
var exp = eval(expsrc[i]);
// dis(f);
//print('decompiling: ' + f);

actual = '';
try { expect = exp(42); f(42) } catch (e) {
//print('tried f(42), caught ' + e)
}
this.reportCompare(expect, actual, summary);

actual = '';
try { expect = exp(43); f(43) } catch (e) {
//print('tried f(43), caught ' + e)
}
this.reportCompare(expect, actual, summary);

actual = '';
try { expect = exp(44); f(44) } catch (e) {
//print('tried f(44), caught ' + e)
}
this.reportCompare(expect, actual, summary);
}


//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__346494.js
*/
test_regress__346494 : function () {
var SECTION = "regress__346494.js";
//var BUGNUMBER = 346494;
var summary = 'try-catch-finally scope';
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

function g()
{
try
{
throw "foo";
}
catch(e if e == "bar")
{
}
catch(e if e == "baz")
{
}
finally
{
}
}

expect = "foo";
try
{
g();
actual = 'No Exception';
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary);

expect =
'function g() {\n' +
'    try {\n' +
'        throw "foo";\n' +
'    } catch (e if e == "bar") {\n' +
'    } catch (e if e == "baz") {\n' +
'    } finally {\n' +
'    }\n' +
'}';

actual = g + '';
this.reportCompare(expect, actual, summary);

function h()
{
try
{
throw "foo";
}
catch(e if e == "bar")
{
}
catch(e)
{
}
finally
{
}
}

expect = "No Exception";
try
{
h();
actual = 'No Exception';
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary);

expect =
'function h() {\n' +
'    try {\n' +
'        throw "foo";\n' +
'    } catch (e if e == "bar") {\n' +
'    } catch (e) {\n' +
'    } finally {\n' +
'    }\n' +
'}';

actual = h + '';
this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__347306__02.js
*/
test_regress__347306__02 : function () {
var SECTION = "regress__347306__02.js";
//var BUGNUMBER = 347306;
var summary = 'toSource should not be O(N**2)';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var data = {X:[], Y:[]};
for (var size = 1000; size <= 10000; size += 1000)
{
data.X.push(size);
data.Y.push(testSource(size));
}

var order = BigO(data);

var msg = '';
for (var p = 0; p < data.X.length; p++)
{
msg += '(' + data.X[p] + ', ' + data.Y[p] + '); ';
}
//printStatus(msg);
//printStatus('Order: ' + order);
this.reportCompare(true, order < 2, 'BigO ' + order + ' < 2');

function testSource(n)
{
var funtext = "";

for (var i=0; i<n; ++i)
funtext += "alert(" + i + "); ";

var fun = new Function(funtext);

var start = new Date();

var s = fun.toSource();

var end = new Date();

//print("Size: " + n + ", Time: " + (end - start) + " ms");

return end - start;
}

},

/** @Test
File Name:         regress__348986.js
*/
test_regress__348986 : function () {
var SECTION = "regress__348986.js";
//var BUGNUMBER = 348986;
var summary = 'Recursion check of nested functions';
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

// Construct f(){function f(){function f(){...}}} with maximum
// nested function declaration still does not hit recursion limit.

var deepestFunction;

var n = findActionMax(function(n) {
var prefix="function f(){";
var suffix="}";
var source = Array(n+1).join(prefix) + Array(n+1).join(suffix);
try {
deepestFunction = Function(source);
return true;
} catch (e) {
if (!(e instanceof InternalError))
throw e;
return false;
}

});

if (n == 0)
throw "unexpected";

//print("Max nested function leveles:"+n);

n = findActionMax(function(n) {
try {
callAfterConsumingCStack(n, function() {});
return true;
} catch (e) {
if (!(e instanceof InternalError))
throw e;
return false;
}
});

//print("Max callAfterConsumingCStack levels:"+n);

// Here n is max possible value when callAfterConsumingCStack(n, emptyFunction)
// does not trigger stackOverflow. Decrease it slightly to give some C stack
// space for deepestFunction.toSource()

n = Math.max(0, n - 10);
try {
var src = callAfterConsumingCStack(n, function() {
return deepestFunction.toSource();
});
throw "Test failed to hit the recursion limit.";
} catch (e) {
if (!(e instanceof InternalError))
throw e;
}

//print('Done');
expect = true;
actual = true;
this.reportCompare(expect, true, summary);

//exitFunc ('test');
//}

function callAfterConsumingCStack(n, action)
{
var testObj = {
get propertyWithGetter() {
if (n == 0)
return action();
n--;
return this.propertyWithGetter;
}
};
return testObj.propertyWithGetter;
}


// Return the maximum positive value of N where action(N) still returns true
// or 0 if no such value exists.
function findActionMax(action)
{
var N, next, increase;

n = 0;
for (;;) {
var next = (n == 0 ? 1 : n * 2);
if (!isFinite(next) || !action(next))
break;
n = next;
}
if (n == 0)
return 0;

var increase = n / 2;
for (;;) {
var next = n + increase;
if (next == n)
break;
if (isFinite(next) && action(next)) {
n = next;
} else if (increase == 1) {
break;
} else {
increase = increase / 2;
}
}
return n;
}

},

/** @Test
File Name:         regress__349616.js
*/
test_regress__349616 : function () {
var SECTION = "regress__349616.js";
//var BUGNUMBER = 349616;
var summary = 'decompilation of getter keyword';
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

var f;
f = function() {
getter = function() { return 5; };
print(window.foo);
}

actual = f + '';
expect = 'function () {\n    window.foo getter= ' +
'function () {return 5;};\n    print(window.foo);\n}';

this.compareSource(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__350312__01.js
*/
test_regress__350312__01 : function () {
var SECTION = "regress__350312__01.js";
//var BUGNUMBER = 350312;
var summary = 'Accessing wrong stack slot with nested catch/finally';
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

var tmp;

function f()
{
try {
try {
throw 1;
} catch (e) {
throw e;
} finally {
tmp = true;
}
} catch (e) {
return e;
}
}

var ex = f();

var passed = ex === 1;
if (!passed) {
//print("Failed!");
//print("ex="+uneval(ex));
}
this.reportCompare(true, passed, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__350312__02.js
*/
test_regress__350312__02 : function () {
var SECTION = "regress__350312__02.js";
//var BUGNUMBER = 350312;
var summary = 'Accessing wrong stack slot with nested catch/finally';
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

function createPrint(obj)
{
return new Function("actual += " + obj + " + ','; " +
"print(" + obj + ");");
}

function createThrow(obj)
{
return new Function("throw " + obj + "; ");
}


function f(a, b, c)
{
try {
a();
} catch (e if e == null) {
b();
} finally {
c();
}
}

//print('test 1');
expect = 'a,c,';
actual = '';
try
{
f(createPrint("'a'"), createPrint("'b'"), createPrint("'c'"));
}
catch(ex)
{
actual += 'caught ' + ex;
}
this.reportCompare(expect, actual, summary + ': 1');

//print('test 2');
expect = 'c,caught a';
actual = '';
try
{
f(createThrow("'a'"), createPrint("'b'"), createPrint("'c'"));
}
catch(ex)
{
actual += 'caught ' + ex;
}
this.reportCompare(expect, actual, summary + ': 2');

//print('test 3');
expect = 'b,c,';
actual = '';
try
{
f(createThrow("null"), createPrint("'b'"), createPrint("'c'"));
}
catch(ex)
{
actual += 'caught ' + ex;
}
this.reportCompare(expect, actual, summary + ': 3');

//print('test 4');
expect = 'a,c,';
actual = '';
try
{
f(createPrint("'a'"), createThrow("'b'"), createPrint("'c'"));
}
catch(ex)
{
actual += 'caught ' + ex;
}
this.reportCompare(expect, actual, summary + ': 4');

//print('test 5');
expect = 'c,caught b';
actual = '';
try
{
f(createThrow("null"), createThrow("'b'"), createPrint("'c'"));
}
catch(ex)
{
actual += 'caught ' + ex;
}
this.reportCompare(expect, actual, summary + ': 5');

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__350312__03.js
*/
test_regress__350312__03 : function () {
var SECTION = "regress__350312__03.js";
//var BUGNUMBER = 350312;
var summary = 'Accessing wrong stack slot with nested catch/finally';
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

var pfx  = "(function (x) {try {if (x > 41) throw x}",
cg1a = " catch (e if e === 42) {var v = 'catch guard 1 ' + e; actual += v + ',';print(v);}"
cg1b = " catch (e if e === 42) {var v = 'catch guard 1 + throw ' + e; actual += v + ',';print(v); throw e;}"
cg2  = " catch (e if e === 43) {var v = 'catch guard 2 ' + e; actual += v + ',';print(v)}"
cat  = " catch (e) {var v = 'catch all ' + e; print(v); if (e == 44) throw e}"
fin  = " finally{var v = 'fin'; actual += v + ',';print(v)}",
end  = "})";

var exphash  = {
pfx: "(function (y) { var result = ''; y = y + ',';",
cg1a: " result += (y === '42,') ? ('catch guard 1 ' + y):'';",
cg1b: " result += (y === '42,') ? ('catch guard 1 + throw ' + y):'';",
cg2:  " result += (y === '43,') ? ('catch guard 2 ' + y):'';",
cat:  " result += (y > 41) ? ('catch all ' + y):'';",
fin:  " result += 'fin,';",
end:  "return result;})"
};

var src = [
pfx + fin + end,
pfx + cat + end,
pfx + cat + fin + end,
pfx + cg1a + end,
pfx + cg1a + fin + end,
pfx + cg1a + cat + end,
pfx + cg1a + cat + fin + end,
pfx + cg1a + cg2 + end,
pfx + cg1a + cg2 + fin + end,
pfx + cg1a + cg2 + cat + end,
pfx + cg1a + cg2 + cat + fin + end,
pfx + cg1b + end,
pfx + cg1b + fin + end,
pfx + cg1b + cat + end,
pfx + cg1b + cat + fin + end,
pfx + cg1b + cg2 + end,
pfx + cg1b + cg2 + fin + end,
pfx + cg1b + cg2 + cat + end,
pfx + cg1b + cg2 + cat + fin + end,
];

var expsrc = [
exphash.pfx + exphash.fin + exphash.end,
exphash.pfx + exphash.cat + exphash.end,
exphash.pfx + exphash.cat + exphash.fin + exphash.end,
exphash.pfx + exphash.cg1a + exphash.end,
exphash.pfx + exphash.cg1a + exphash.fin + exphash.end,
exphash.pfx + exphash.cg1a + exphash.cat + exphash.end,
exphash.pfx + exphash.cg1a + exphash.cat + exphash.fin + exphash.end,
exphash.pfx + exphash.cg1a + exphash.cg2 + exphash.end,
exphash.pfx + exphash.cg1a + exphash.cg2 + exphash.fin + exphash.end,
exphash.pfx + exphash.cg1a + exphash.cg2 + exphash.cat + exphash.end,
exphash.pfx + exphash.cg1a + exphash.cg2 + exphash.cat + exphash.fin + exphash.end,
exphash.pfx + exphash.cg1b + exphash.end,
exphash.pfx + exphash.cg1b + exphash.fin + exphash.end,
exphash.pfx + exphash.cg1b + exphash.cat + exphash.end,
exphash.pfx + exphash.cg1b + exphash.cat + exphash.fin + exphash.end,
exphash.pfx + exphash.cg1b + exphash.cg2 + exphash.end,
exphash.pfx + exphash.cg1b + exphash.cg2 + exphash.fin + exphash.end,
exphash.pfx + exphash.cg1b + exphash.cg2 + exphash.cat + exphash.end,
exphash.pfx + exphash.cg1b + exphash.cg2 + exphash.cat + exphash.fin + exphash.end,
];

for (var i in src) {
//print("\n=== " + i + ": " + src[i]);
var f = eval(src[i]);
var exp = eval(expsrc[i]);
// dis(f);
//print('decompiling: ' + f);
//print('decompiling exp: ' + exp);

actual = '';
try { expect = exp(41); f(41) } catch (e) {
//print('tried f(41), caught ' + e)
}
this.reportCompare(expect, actual, summary);

actual = '';
try { expect = exp(42); f(42) } catch (e) {
//print('tried f(42), caught ' + e)
}
this.reportCompare(expect, actual, summary);

actual = '';
try { expect = exp(43); f(43) } catch (e) {
//print('tried f(43), caught ' + e)
}
this.reportCompare(expect, actual, summary);

actual = '';
try { expect = exp(44); f(44) } catch (e) {
//print('tried f(44), caught ' + e)
}
this.reportCompare(expect, actual, summary);

actual = '';
try { expect = exp(45); f(45) } catch (e) {
//print('tried f(44), caught ' + e)
}
this.reportCompare(expect, actual, summary);

}

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__350531.js
*/
test_regress__350531 : function () {
var SECTION = "regress__350531.js";
//var BUGNUMBER = 350531;
var summary = 'exhaustively test parenthesization of binary operator subsets';
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

// Translated from permcomb.py, found at
// http://biotech.embl-ebi.ac.uk:8400/sw/common/share/python/examples/dstruct/classics/permcomb.py
// by searching for "permcomb.py".
//
// This shows bugs, gaps, and verbosities in JS compared to Python:
// 1. Lack of range([start, ] end[, step]).
// 2. ![] => false, indeed !<any-object> => false.
// 3. Missing append or push for strings (if append, then we'd want append for
//    arrays too).
// 4. Missing slice operator syntax s[i:j].
// 5. Lack of + for array concatenation.

String.prototype.push = function (str) { return this + str; };

function permute(list) {
if (!list.length)                                   // shuffle any sequence
return [list];                                    // empty sequence
var res = [];
for (var i = 0, n = list.length; i < n; i++) {      // delete current node
var rest = list.slice(0, i).concat(list.slice(i+1));
for each (var x in permute(rest))                 // permute the others
res.push(list.slice(i, i+1).concat(x));         // add node at front
}
return res;
}

function subset(list, size) {
if (size == 0 || !list.length)                      // order matters here
return [list.slice(0, 0)];                        // an empty sequence
var result = [];
for (var i = 0, n = list.length; i < n; i++) {
var pick = list.slice(i, i+1);                    // sequence slice
var rest = list.slice(0, i).concat(list.slice(i+1)); // keep [:i] part
for each (var x in subset(rest, size-1))
result.push(pick.concat(x));
}
return result;
}

function combo(list, size) {
if (size == 0 || !list.length)                      // order doesn't matter
return [list.slice(0, 0)];                        // xyz == yzx
var result = [];
for (var i = 0, n = (list.length - size) + 1; i < n; i++) {
// iff enough left
var pick = list.slice(i, i+1);
var rest = list.slice(i+1);                       // drop [:i] part
for each (var x in combo(rest, size - 1))
result.push(pick.concat(x));
}
return result;
}


// Generate all subsets of distinct binary operators and join them from left
// to right, parenthesizing minimally.  Decompile, recompile, compress spaces
// and compare to test correct parenthesization.

//  load("permcomb.js");

var bops = [
["=", "|=", "^=", "&=", "<<=", ">>=", ">>>=", "+=", "-=", "*=", "/=", "%="],
["||"],
["&&"],
["|"],
["^"],
["&"],
["==", "!=", "===", "!=="],
["<", "<=", ">=", ">", "in", "instanceof"],
["<<", ">>", ">>>"],
["+", "-"],
["*", "/", "%"],
];

var prec = {};
var aops = [];

for (var i = 0; i < bops.length; i++) {
for (var j = 0; j < bops[i].length; j++) {
var k = bops[i][j];
prec[k] = i;
aops.push(k);
}
}

// Theoretically all subsets of size 2 should be enough to test, but in case
// there's some large-scale bug, try up to 5 (or higher? The cost in memory is
// factorially explosive).
next_subset:
for (i = 2; i < 5; i++) {
var sets = subset(aops, i);
this.gc();

for each (var set in sets) {
//print('for each set in sets: ' + (uneval(set)) );
var src = "(function () {";
for (j in set) {
var op = set[j], op2 = set[j-1];

// Precedence 0 is for assignment ops, which are right-
// associative, so don't force left associativity using
// parentheses.
if (prec[op] && prec[op] < prec[op2])
src += "(";
}
src += "x ";
for (j in set) {
var op = set[j], op2 = set[j+1];

// Parenthesize only if not right-associative (precedence 0) and
// the next op is higher precedence than current.
var term = (prec[op] && prec[op] < prec[op2]) ? " x)" : " x";

src += op + term;
if (j < set.length - 1)
src += " ";
}
src += ";})";
try {
var ref = uneval(eval(src)).replace(/\s+/g, ' ');
if (ref != src) {
actual += "BROKEN! input: " + src + " output: " + ref + " ";
//print("BROKEN! input: " + src + " output: " + ref);
break next_subset;
}
} catch (e) {}
}
}

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__351102__01.js
*/
test_regress__351102__01 : function () {
var SECTION = "regress__351102__01.js";
//var BUGNUMBER = 351102;
var summary = 'try/catch-guard/finally GC issues';
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

var f;

f = function () {
try {
throw new Error('bad');
} catch (e if (e = null, this.gc(), false)) {
} catch (e) {
// e is dangling now
}
};

f();

this.reportCompare(expect, actual, summary + ': 1');

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__351102__02.js
*/
test_regress__351102__02 : function () {
var SECTION = "regress__351102__02.js";
//var BUGNUMBER = 351102;
var summary = 'try/catch-guard/finally GC issues';
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

var f;
f = function ()
{
var a = null;
try {
a();
} catch (e) {
}
return false;
};

try {
throw 1;
} catch (e if f()) {
} catch (e if e == 1) {
//print("GOOD");
} catch (e) {
//print("BAD: "+e);
}

this.reportCompare(expect, actual, summary + ': 2');
//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__351102__06.js
*/
test_regress__351102__06 : function () {
var SECTION = "regress__351102__06.js";
//var BUGNUMBER = 351102;
var summary = 'try/catch-guard/finally GC issues';
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

var f;
try
{
try { null.a } catch(e if (e = null, this.gc())) { }
}
catch(ex)
{
}
this.reportCompare(expect, actual, summary + ': 6');

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__351448.js
*/
test_regress__351448 : function () {
var SECTION = "regress__351448.js";
//var BUGNUMBER = 351448;
var summary = 'RegExp - throw InternalError on too complex regular expressions';
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

var strings = [
"/.X(.+)+X/.exec('bbbbXXaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa')",
"/.X(.+)+X/.exec('bbbbXcXaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa')",
"/.X(.+)+XX/.exec('bbbbXXXaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa')",
"/.X(.+)+XX/.exec('bbbbXcXXaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa')",
"/.X(.+)+[X]/.exec('bbbbXXaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa')",
"/.X(.+)+[X]/.exec('bbbbXcXaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa')",
"/.X(.+)+[X][X]/.exec('bbbbXXXaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa')",
"/.X(.+)+[X][X]/.exec('bbbbXcXXaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa')",
"/.XX(.+)+X/.exec('bbbbXXXaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa')",
"/.XX(.+)+X/.exec('bbbbXXcXaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa')",
"/.XX(.+)+X/.exec('bbbbXXcXaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa')",
"/.XX(.+)+[X]/.exec('bbbbXXXaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa')",
"/.XX(.+)+[X]/.exec('bbbbXXcXaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa')",
"/.[X](.+)+[X]/.exec('bbbbXXaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa')",
"/.[X](.+)+[X]/.exec('bbbbXcXaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa')",
"/.[X](.+)+[X][X]/.exec('bbbbXXXaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa')",
"/.[X](.+)+[X][X]/.exec('bbbbXcXXaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa')",
"/.[X][X](.+)+[X]/.exec('bbbbXXXaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa')",
"/.[X][X](.+)+[X]/.exec('bbbbXXcXaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa')"
];

expect = 'InternalError: regular expression too complex';

options('relimit');

for (var i = 0; i < strings.length; i++)
{
try
{
eval(strings[i]);
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': ' + strings[i]);
}

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__351463__01.js
*/
test_regress__351463__01 : function () {
var SECTION = "regress__351463__01.js";
//var BUGNUMBER = 351463;
var summary = 'Treat hyphens as not special adjacent to CharacterClassEscapes in character classes';
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

var r;
var s = 'a0- z';

r = '([\\d-\\s]+)';
expect = ['0- ', '0- '] + '';
actual = null;

try
{
actual = new RegExp(r).exec(s) + '';
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': /' + r + '/.exec("' + s + '")');

r = '([\\s-\\d]+)';
expect = ['0- ', '0- '] + '';
actual = null;

try
{
actual = new RegExp(r).exec(s) + '';
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': /' + r + '/.exec("' + s + '")');

r = '([\\D-\\s]+)';
expect = ['a', 'a'] + '';
actual = null;

try
{
actual = new RegExp(r).exec(s) + '';
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': /' + r + '/.exec("' + s + '")');

r = '([\\s-\\D]+)';
expect = ['a', 'a'] + '';
actual = null;

try
{
actual = new RegExp(r).exec(s) + '';
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': /' + r + '/.exec("' + s + '")');

r = '([\\d-\\S]+)';
expect = ['a0-', 'a0-'] + '';
actual = null;

try
{
actual = new RegExp(r).exec(s) + '';
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': /' + r + '/.exec("' + s + '")');

r = '([\\S-\\d]+)';
expect = ['a0-', 'a0-'] + '';
actual = null;

try
{
actual = new RegExp(r).exec(s) + '';
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': /' + r + '/.exec("' + s + '")');

r = '([\\D-\\S]+)';
expect = ['a0- z', 'a0- z'] + '';
actual = null;

try
{
actual = new RegExp(r).exec(s) + '';
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': /' + r + '/.exec("' + s + '")');

r = '([\\S-\\D]+)';
expect = ['a0- z', 'a0- z'] + '';
actual = null;

try
{
actual = new RegExp(r).exec(s) + '';
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': /' + r + '/.exec("' + s + '")');

// --

r = '([\\w-\\s]+)';
expect = ['a0- z', 'a0- z'] + '';
actual = null;

try
{
actual = new RegExp(r).exec(s) + '';
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': /' + r + '/.exec("' + s + '")');

r = '([\\s-\\w]+)';
expect = ['a0- z', 'a0- z'] + '';
actual = null;

try
{
actual = new RegExp(r).exec(s) + '';
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': /' + r + '/.exec("' + s + '")');

r = '([\\W-\\s]+)';
expect = ['- ', '- '] + '';
actual = null;

try
{
actual = new RegExp(r).exec(s) + '';
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': /' + r + '/.exec("' + s + '")');

r = '([\\s-\\W]+)';
expect = ['- ', '- '] + '';
actual = null;

try
{
actual = new RegExp(r).exec(s) + '';
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': /' + r + '/.exec("' + s + '")');

r = '([\\w-\\S]+)';
expect = ['a0-', 'a0-'] + '';
actual = null;

try
{
actual = new RegExp(r).exec(s) + '';
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': /' + r + '/.exec("' + s + '")');

r = '([\\S-\\w]+)';
expect = ['a0-', 'a0-'] + '';
actual = null;

try
{
actual = new RegExp(r).exec(s) + '';
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': /' + r + '/.exec("' + s + '")');

r = '([\\W-\\S]+)';
expect = ['a0- z', 'a0- z'] + '';
actual = null;

try
{
actual = new RegExp(r).exec(s) + '';
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': /' + r + '/.exec("' + s + '")');

r = '([\\S-\\W]+)';
expect = ['a0- z', 'a0- z'] + '';
actual = null;

try
{
actual = new RegExp(r).exec(s) + '';
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': /' + r + '/.exec("' + s + '")');
//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__351973.js
*/
test_regress__351973 : function () {
var SECTION = "regress__351973.js";
//var BUGNUMBER = 351973;
var summary = 'GC hazard with unrooted ids in Object.toSource';
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

function removeAllProperties(o)
{
for (var prop in o)
delete o[prop];
for (var i = 0; i != 50*1000; ++i) {
var tmp = Math.sqrt(i+0.2);
tmp = 0;
}
if (typeof gc == "function")
this.gc();
}

function run_test()
{

var o = {};
o.first = { toSource: function() { removeAllProperties(o); } };
for (var i = 0; i != 10; ++i) {
o[Math.sqrt(i + 0.1)] = 1;
}
return o.toSource();
}

//print(run_test());

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__352060.js
*/
test_regress__352060 : function () {
var SECTION = "regress__352060.js";
//var BUGNUMBER = 352060;
var summary = 'decompilation of getter, setter revisited';
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

var f;

f = function() { setter = function(){} }
expect = 'function() { foo setter = function(){}; }';
actual = f + '';
this.compareSource(expect, actual, summary);

f = function() { setter = function(){} }
expect = 'function() { foo.bar setter = function(){}; }';
actual = f + '';
this.compareSource(expect, actual, summary);

f = function(){ y = new Array(); getter = function(){}; }
expect = 'function(){ var y = new Array(); y[0] getter = function(){}; } ';
actual = f + '';
this.compareSource(expect, actual, summary);

/*f = function(){ var foo = <foo bar="baz"/>; foo.@bar getter = function(){}; }
expect = 'function(){ var foo = <foo bar="baz"/>; foo.@bar getter = function(){}; }';
actual = f + '';
this.compareSource(expect, actual, summary);
*/

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__352094.js
*/
test_regress__352094 : function () {
var SECTION = "regress__352094.js";
//var BUGNUMBER = 352094;
var summary = 'Do not crash with invalid setter usage';
var actual = 'No Crash';
var expect = 'SyntaxError: invalid setter usage';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

try
{
eval('(function(){ this.p setter = 0 })()');
}
catch(ex)
{
actual = ex + '';
}

this.reportCompare(expect, actual, summary);

try
{
eval('(function(){ this.p setter = 0 })()');
}
catch(ex)
{
actual = ex + '';
}

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__352261.js
*/
test_regress__352261 : function () {
var SECTION = "regress__352261.js";
//var BUGNUMBER = 352261;
var summary = 'Decompilation should preserve right associativity';
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

var g, h;

g = function(a,b,c) { return a - (b + c) }
expect = 'function(a,b,c) { return a - (b + c); }';
actual = g + '';
this.compareSource(expect, actual, summary);

h = eval(uneval(g));
expect = g(1, 10, 100);
actual = h(1, 10, 100);
this.reportCompare(expect, actual, summary);

var p, q;

p = function (a,b,c) { return a + (b - c) }
expect = 'function (a,b,c) { return a + (b - c);}';
actual = p + '';
this.compareSource(expect, actual, summary);

q = eval(uneval(p));
expect = p(3, "4", "5");
actual = q(3, "4", "5");
this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__352281.js
*/
test_regress__352281 : function () {
var SECTION = "regress__352281.js";
//var BUGNUMBER = 352281;
var summary = 'decompilation of |while| and function declaration';
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

var f, g;
f = function() { { while(0) function t() {  } } }
expect = 'function() { while(0) { function t() {  } }}';
actual = f + '';
this.compareSource(expect, actual, summary);

g = eval(uneval(actual));
actual = g + '';
this.compareSource(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__352291.js
*/
test_regress__352291 : function () {
var SECTION = "regress__352291.js";
//var BUGNUMBER = 352291;
var summary = 'disassembly of regular expression';
var actual = '';
var expect = 'TypeError: /g/g is not a function';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

if (typeof dis != 'function')
{
actual = expect = 'disassembly not supported, test skipped.';
}
else
{
try
{
dis(/g/g)
}
catch(ex)
{
actual = ex + '';
}
}
this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__352372.js
*/
test_regress__352372 : function () {
var SECTION = "regress__352372.js";
//var BUGNUMBER = 352372;
var summary = 'Do not assert eval("setter/*...")';
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

expect = 'ReferenceError: setter is not defined';
try
{
eval("setter/*\n*/;");
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, 'eval("setter/*\n*/;")');

try
{
eval("setter/*\n*/g");
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, 'eval("setter/*\n*/g")');

try
{
eval("setter/*\n*/ ;");
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, 'eval("setter/*\n*/ ;")');

try
{
eval("setter/*\n*/ g");
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, 'eval("setter/*\n*/ g")');

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__352455.js
*/
test_regress__352455 : function () {
var SECTION = "regress__352455.js";
//var BUGNUMBER = 352455;
var summary = 'Eval object with non-function getters/setters';
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

//print('If the test harness fails on this bug, the test fails.');

expect = 'SyntaxError: invalid getter usage';
z = ({});
try {
eval('z.x getter= /g/i;');
} catch(ex) {
actual = ex + '';
}
//print("This line should not be the last output you see.");
try {
//print(uneval(z));
} catch(e) {
//print("Threw!");
//print(e);
}

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__352604.js
*/
test_regress__352604 : function () {
var SECTION = "regress__352604.js";
//var BUGNUMBER = 352604;
var summary = 'Do not assert: !OBJ_GET_PROTO(cx, ctor)';
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

function f() {}
delete Function;
var g = new Function('');

expect = f.__proto__;
actual = g.__proto__;

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__353214.js
*/
test_regress__353214 : function () {
var SECTION = "regress__353214.js";
//var BUGNUMBER = 353214;
var summary = 'decompilation of |function() { (function ([x]) { })(); eval("return 3;") }|';
var actual = '';
var expect = '';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
///printStatus (summary);

var f = function() { (function ([x]) { })(); eval('return 3;') }
expect = 'function() { (function ([x]) { }()); eval("return 3;"); }';
actual = f + '';
this.compareSource(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__354297.js
*/
test_regress__354297 : function () {
var SECTION = "regress__354297.js";
//var BUGNUMBER = 354297;
var summary = 'getter/setter can be on index';
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

//print('This test requires GC_MARK_DEBUG');

var o = {}; o.__defineGetter__(1, Math.sin); this.gc()

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__354541__01.js
*/
test_regress__354541__01 : function () {
var SECTION = "regress__354541__01.js";
//var BUGNUMBER = 354541;
var summary = 'Regression to standard class constructors in case labels';
var actual = '';
var expect = '';


//printBugNumber(BUGNUMBER);
//printStatus (summary + ': top level');

String.prototype.trim = function() {
//print('hallo');
};

const S = String;
const Sp = String.prototype;

expect = 'No Error';
actual = 'No Error';

if (typeof Script == 'undefined')
{
//print('Test skipped. Script not defined.');
}
else
{
var s = Script('var tmp = function(o) { switch(o) { case String: case 1: return ""; } };  "".trim();');
s();
}

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__354541__02.js
*/
test_regress__354541__02 : function () {
var SECTION = "regress__354541__02.js";
//var BUGNUMBER = 354541;
var summary = 'Regression to standard class constructors in case labels';
var actual = '';
var expect = '';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary + ': in function');

String.prototype.trim = function() {
//print('hallo');
};

const S = String;
const Sp = String.prototype;

expect = 'No Error';
actual = 'No Error';
if (typeof Script == 'undefined')
{
//print('Test skipped. Script not defined.');
}
else
{
var s = Script('var tmp = function(o) { switch(o) { case String: case 1: return ""; } };  "".trim();');
s();
}

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__354541__03.js
*/
test_regress__354541__03 : function () {
var SECTION = "regress__354541__03.js";
//var BUGNUMBER = 354541;
var summary = 'Regression to standard class constructors in case labels';
var actual = '';
var expect = '';


//printBugNumber(BUGNUMBER);
//printStatus (summary + ': top level');

String.prototype.trim = function() {
//print('hallo');
};

String.prototype.trim = function() { return 'hallo'; };

const S = String;
const Sp = String.prototype;

expect = 'hallo';
var expectStringInvariant = true
var actualStringInvariant;
var expectStringPrototypeInvariant = true;
var actualStringPrototypeInvariant;

if (typeof Script == 'undefined')
{
//print('Test skipped. Script not defined.');
this.reportCompare("Script not defined, Test skipped.",
"Script not defined, Test skipped.",
summary);
}
else
{
var s = Script('var tmp = function(o) { switch(o) { case String: case 1: return ""; } }; actualStringInvariant = (String === S); actualStringPrototypeInvariant = (String.prototype === Sp); actual = "".trim();');
try
{
s();
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, 'trim() returned');
this.reportCompare(expectStringInvariant, actualStringInvariant,
'String invariant');
this.reportCompare(expectStringPrototypeInvariant,
actualStringPrototypeInvariant,
'String.prototype invariant');

}

},

/** @Test
File Name:         regress__354541__04.js
*/
test_regress__354541__04 : function () {
var SECTION = "regress__354541__04.js";
//var BUGNUMBER = 354541;
var summary = 'Regression to standard class constructors in case labels';
var actual = '';
var expect = '';

//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary + ': in function');

String.prototype.trim = function() { return 'hallo'; };

const S = String;
const Sp = String.prototype;

expect = 'hallo';
var expectStringInvariant = true;
var actualStringInvariant;
var expectStringPrototypeInvariant = true;
var actualStringPrototypeInvariant;

if (typeof Script == 'undefined')
{
//print('Test skipped. Script is not defined');
this.reportCompare("Script not defined, Test skipped.",
"Script not defined, Test skipped.",
summary);
}
else
{
s = Script('var tmp = function(o) { switch(o) { case String: case 1: return ""; } }; actualStringInvariant = (String === S); actualStringPrototypeInvariant = (String.prototype === Sp); actual = "".trim();');
try
{
s();
}
catch(ex)
{
actual = ex + '';
}

this.reportCompare(expect, actual, 'trim() returned');
this.reportCompare(expectStringInvariant, actualStringInvariant, 'String invariant');
this.reportCompare(expectStringPrototypeInvariant,
actualStringPrototypeInvariant,
'String.prototype invariant');
}

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__355339.js
*/
test_regress__355339 : function () {
var SECTION = "regress__355339.js";
//var BUGNUMBER = 355339;
var summary = 'Do not assert: sprop->setter != js_watch_set';
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
o = {};
o.watch("j", function(a,b,c) { print("*",a,b,c) });
o.unwatch("j");
o.watch("j", function(a,b,c) { print("*",a,b,c) });

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__355497.js
*/
test_regress__355497 : function () {
var SECTION = "regress__355497.js";
//var BUGNUMBER = 355497;
var summary = 'Do not overflow stack with Array.slice, getter';
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

expect = 'InternalError: too much recursion';

try
{
var a = { length: 1 };
a.__defineGetter__(0, [].slice);
a[0];
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': 1');

try
{
var b = { length: 1 };
b.__defineGetter__(0, function () { return Array.slice(b);});
b[0];
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': 2');

try
{
var c = [];
c.__defineSetter__(0, c.unshift);
c[0] = 1;
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': 3');
//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__355622.js
*/
test_regress__355622 : function () {
var SECTION = "regress__355622.js";
//var BUGNUMBER = 355622;
var summary = 'Do not assert: overwriting';
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

try
{
function() {  };
}
catch(ex)
{
print(ex + '');
}
this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__355655.js
*/
test_regress__355655 : function () {
var SECTION = "regress__355655.js";
//var BUGNUMBER = 355655;
var summary = 'running script can be recompiled';
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

if (typeof Script == 'undefined')
{
//print('Test skipped. Script not defined.');
}
else
{
expect = 'TypeError: cannot compile over a script that is currently executing';
actual = '';

try
{
t='1';s=Script('s.compile(t);print(t);');s();
}
catch(ex)
{
actual = ex + '';
}
}

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__355736.js
*/
/*
test_regress__355736 : function () {
var SECTION = "regress__355736.js";
//var BUGNUMBER = 355736;
var summary = 'Decompilation of "[reserved]" has extra quotes';
var actual = '';
var expect = '';
var f;

//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

f = function() { [super] = q; };
expect = 'function() { [super] = q; }';
actual = f + '';
this.compareSource(expect, actual, summary + ': 1');

f = function() { return { get super() { } } };
expect = 'function() { return { super getter : function() { } }; }';
actual = f + '';
this.compareSource(expect, actual, summary + ': 2');

f = function() { [goto] = a };
expect = 'function() { [goto] = a; }';
actual = f + '';
this.compareSource(expect, actual, summary + ': 3');

//exitFunc ('test');
//}

},*/

/** @Test
File Name:         regress__355820.js
*/
test_regress__355820 : function () {
var SECTION = "regress__355820.js";
//var BUGNUMBER = 355820;
var summary = 'Remove non-standard Script object';
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

//print('This test will fail in gecko prior to 1.9');

expect = 'undefined';
actual = typeof Script;

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__355982.js
*/
test_regress__355982 : function () {
var SECTION = "regress__355982.js";
//var BUGNUMBER = 355982;
var summary = 'Script("") should not fail';
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

expect = 'No Error';
actual = 'No Error';
try
{
if (typeof Script == 'undefined')
{
//print('Test skipped. Script not defined.');
}
else
{
Script('');
}
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__356085.js
*/
test_regress__356085 : function () {
var SECTION = "regress__356085.js";
//var BUGNUMBER = 356085;
var summary = 'js_obj_toString for getter/setter';
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

expect = '({ set p y() { } })';
actual = uneval({setter: function y() { } });

this.compareSource(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__356106.js
*/
test_regress__356106 : function () {
var SECTION = "regress__356106.js";
//var BUGNUMBER = 356106;
var summary = "Do not assert: rval[strlen(rval)-1] == '}'";
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

(function() { return ({setter: function(){} | 5 }) });

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__356378.js
*/
test_regress__356378 : function () {
var SECTION = "regress__356378.js";
//var BUGNUMBER = 356378;
var summary = 'var x; x getter= function () { };';
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

expect = 'SyntaxError: invalid getter usage';
try
{
eval('(function() { var x; x getter= function () { }; })();');
}
catch(ex)
{
actual = ex + '';
}

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__356402.js
*/
test_regress__356402 : function () {
var SECTION = "regress__356402.js";
//var BUGNUMBER = 356402;
var summary = 'Do not assert: slot < fp->nvars';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

if (typeof Script == 'undefined')
{
//print('Test skipped. Script not defined.');
}
else
{
(function() { new Script('for(var x in x) { }')(); })();
}
this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__358594__01.js
*/
test_regress__358594__01 : function () {
var SECTION = "regress__358594__01.js";
//var BUGNUMBER = 358594;
var summary = 'Do not crash on uneval(this).';
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

// don't crash|assert
function f() { }
f.__proto__ = this;
setter = f;
uneval(this);
this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__358594__02.js
*/
test_regress__358594__02 : function () {
var SECTION = "regress__358594__02.js";
//var BUGNUMBER = 358594;
var summary = 'Do not crash on uneval(this).';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

// don't crash|assert
function f() { }
f.__proto__ = this;
setter = f;
uneval(this);
this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__358594__03.js
*/
test_regress__358594__03 : function () {
var SECTION = "regress__358594__03.js";
//var BUGNUMBER = 358594;
var summary = 'Do not crash on uneval(this).';
var actual = '';
var expect = '';

//-----------------------------------------------------------------------------
////test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

// don't crash|assert
f = function () { };
f.__proto__ = this;
setter = f;
uneval(this);
this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__358594__04.js
*/
test_regress__358594__04 : function () {
var SECTION = "regress__358594__04.js";
//var BUGNUMBER = 358594;
var summary = 'Do not crash on uneval(this).';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

// don't crash|assert
f = function () { };
f.__proto__ = this;
setter = f;
uneval(this);
this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__358594__05.js
*/
test_regress__358594__05 : function () {
var SECTION = "regress__358594__05.js";
//var BUGNUMBER = 358594;
var summary = 'Do not crash on uneval(this).';
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

// don't crash|assert
f = function () { };
f.hhhhhhhhh = this;
setter = f;
uneval(this);
this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__358594__06.js
*/
test_regress__358594__06 : function () {
var SECTION = "regress__358594__06.js";
//var BUGNUMBER = 358594;
var summary = 'Do not crash on uneval(this).';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

// don't crash|assert
f = function () { };
f.hhhhhhhhh = this;
setter = f;
uneval(this);
this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__359024.js
*/
test_regress__359024 : function () {
var SECTION = "regress__359024.js";
//var BUGNUMBER = 359024;
var summary = 'Do not crash with Script...';
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

if (typeof Script == 'undefined')
{
//print(expect = actual = 'Test skipped. Script object required.');
}
else
{
var scri=new Script(" var s=new Date(); var a=0; for(var i=0;i<1024*1024;i++) {a=i } var e=new Date(); print('time2='+(e-s)/1000);");
scri.compile();
scri.exec();
}
this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__361346.js
*/
test_regress__361346 : function () {
var SECTION = "regress__361346.js";
//var BUGNUMBER = 361346;
var summary = 'Crash with setter, watch, GC';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

expect = actual = 'No Crash';

setter= new Function;
this.watch('x', function(){});
this.gc();
x = {};

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__361360.js
*/
test_regress__361360 : function () {
var SECTION = "regress__361360.js";
//var BUGNUMBER = 361360;
var summary = 'Do not assert: !caller || caller->pc involving setter and watch';
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

this.__defineSetter__('x', eval);
this.watch('x', function(){});
x = 3;

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__361552.js
*/
test_regress__361552 : function () {
var SECTION = "regress__361552.js";
//var BUGNUMBER = 361552;
var summary = 'Crash with setter, watch, Script';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

expect = actual = 'No Crash';

if (typeof Script == 'undefined')
{
//print('Test skipped. Script not defined.');
}
else
{
this.__defineSetter__('x', gc);
this.watch('x', new Script(''));
x = 3;
}
this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__361558.js
*/
test_regress__361558 : function () {
var SECTION = "regress__361558.js";
//var BUGNUMBER = 361558;
var summary = 'Do not assert: sprop->setter != js_watch_set';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

expect = actual = 'No Crash';

({}.__proto__.watch('x', print)); ({}.watch('x', print));

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__361571.js
*/
test_regress__361571 : function () {
var SECTION = "regress__361571.js";
//var BUGNUMBER = 361571;
var summary = 'Do not assert: fp->scopeChain == parent';
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

try
{
o = {};
o.__defineSetter__('y', eval);
o.watch('y', function () { return "";});
o.y = 1;
}
catch(ex)
{
//printStatus('Note eval can no longer be called directly');
expect = 'EvalError: function eval must be called directly, and not by way of a function of another name';
actual = ex + '';
}
this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__361856.js
*/
test_regress__361856 : function () {
var SECTION = "regress__361856.js";
//var BUGNUMBER = 361856;
var summary = 'Do not assert: overwriting @ js_AddScopeProperty';
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

function testit() {
var obj = {};
obj.watch("foo", function(){});
delete obj.foo;
obj = null;
this.gc();
}
testit();

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         XXXXXXX.js
*/
test_catchguard__001 : function () {
var SECTION = "catchguard__001.js";

},

/** @Test
File Name:         XXXXXXX.js
*/
test_catchguard__001 : function () {
var SECTION = "catchguard__001.js";

},

/** @Test
File Name:         XXXXXXX.js
*/
test_catchguard__001 : function () {
var SECTION = "catchguard__001.js";

},

/** @Test
File Name:         XXXXXXX.js
*/
test_catchguard__001 : function () {
var SECTION = "catchguard__001.js";

},

/** @Test
File Name:         XXXXXXX.js
*/
test_catchguard__001 : function () {
var SECTION = "catchguard__001.js";

},

/** @Test
File Name:         XXXXXXX.js
*/
test_catchguard__001 : function () {
var SECTION = "catchguard__001.js";

},

/** @Test
File Name:         XXXXXXX.js
*/
test_catchguard__001 : function () {
var SECTION = "catchguard__001.js";

},

/** @Test
File Name:         XXXXXXX.js
*/
test_catchguard__001 : function () {
var SECTION = "catchguard__001.js";

},

/** @Test
File Name:         XXXXXXX.js
*/
test_catchguard__001 : function () {
var SECTION = "catchguard__001.js";

},

/** @Test
File Name:         XXXXXXX.js
*/
test_catchguard__001 : function () {
var SECTION = "catchguard__001.js";

},

/** @Test
File Name:         XXXXXXX.js
*/
test_catchguard__001 : function () {
var SECTION = "catchguard__001.js";

},

/** @Test
File Name:         XXXXXXX.js
*/
test_catchguard__001 : function () {
var SECTION = "catchguard__001.js";

},

/** @Test
File Name:         XXXXXXX.js
*/
test_catchguard__001 : function () {
var SECTION = "catchguard__001.js";

},

/** @Test
File Name:         XXXXXXX.js
*/
test_catchguard__001 : function () {
var SECTION = "catchguard__001.js";

},

/** @Test
File Name:         XXXXXXX.js
*/
test_catchguard__001 : function () {
var SECTION = "catchguard__001.js";

},

/** @Test
File Name:         XXXXXXX.js
*/
test_catchguard__001 : function () {
var SECTION = "catchguard__001.js";

}



})
.endType();
