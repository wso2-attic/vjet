vjo.ctype("dsf.jslang.feature.tests.Ecma3ObjectTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({
cnNoClass : null,
cnObjectToString : Object.prototype.toString,
findType : function (obj)
{
return this.cnObjectToString.apply(obj);
},


// given '[object Number]',  return 'Number'
findClass : function (sType)
{
var re =  /^\[.*\s+(\w+)\s*\]$/;
var a = sType.match(re);

if (a && a[1])
return a[1];
return this.cnNoClass;
},


isObject : function (obj)
{
return obj instanceof Object;
},

reportCompare : function  (expectedValue, actualValue, statusItems) {
new this.vj$.BaseTest().TestCase( ' ',  statusItems, expectedValue,  actualValue);
},

// checks that it's safe to call findType()
getJSClass : function (obj)
{
if (this.isObject(obj))
return this.findClass(this.findType(obj));
return null;
},


inSection : function (x) {
var SECT_PREFIX = 'Section ';
var SECT_SUFFIX = ' of test - ';
return SECT_PREFIX + x + SECT_SUFFIX;
},

//-----------------------------------------------------------------------------
test_8_6_1__01:function(){

var BUGNUMBER = 315436;
var summary = 'In strict mode, setting a read-only property should generate a warning';

// printBugNumber(BUGNUMBER);
// printStatus (summary);

// enterFunc (String (BUGNUMBER));

// should throw an error in strict mode
var actual = '';
var expect = 's.length is read-only';
var status = summary + ': Throw if STRICT and WERROR is enabled';
var options = null;
if (!options().match(/strict/))
{
options('strict');
}
if (!options().match(/werror/))
{
options('werror');
}

try
{
var s = new String ('abc');
s.length = 0;
}
catch (e)
{
actual = e.message;
}

this.reportCompare(expect, actual, status);

// should not throw an error if in strict mode and WERROR is false

actual = 'did not throw';
expect = 'did not throw';
var status = summary + ': Do not throw if STRICT is enabled and WERROR is disabled';

// toggle werror off
options('werror');

try
{
s.length = 0;
}
catch (e)
{
actual = e.message;
}

this.reportCompare(expect, actual, status);

// should not throw an error if not in strict mode

actual = 'did not throw';
expect = 'did not throw';
var status = summary + ': Do not throw if not in strict mode';

// toggle strict off
options('strict');

try
{
s.length = 0;
}
catch (e)
{
actual = e.message;
}

this.reportCompare(expect, actual, status);

},
//-----------------------------------------------------------------------------
test_8_6_2_6__001:function(){

var gTestfile = '8.6.2.6-001.js';
var UBound = 0;
var BUGNUMBER = 167325;
var summary = "Test for TypeError on invalid default string value of object";
var TEST_PASSED = 'TypeError';
var TEST_FAILED = 'Generated an error, but NOT a TypeError!';
var TEST_FAILED_BADLY = 'Did not generate ANY error!!!';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


status = this.inSection(1);
expect = TEST_PASSED;
actual = TEST_FAILED_BADLY;
/*
* This should generate a TypeError. See ECMA reference
* at http://bugzilla.mozilla.org/show_bug.cgi?id=167325
*/
try
{
var obj = {toString: function() {return new Object();}}
obj == 'abc';
}
catch(e)
{
if (e instanceof TypeError)
actual = TEST_PASSED;
else
actual = TEST_FAILED;
}
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
//-----------------------------------------------------------------------------
test_class__001:function(){

var gTestfile = 'class-001.js';
var i = 0;
var UBound = 0;
var BUGNUMBER = '(none)';
var summary = 'Testing the internal [[Class]] property of objects';
var statprefix = 'Current object is: ';
var status = ''; var statusList = [ ];
var actual = ''; var actualvalue = [ ];
var expect= ''; var expectedvalue = [ ];
var GLOBAL = 'Window';

status = 'the global object';
actual = this.getJSClass(this);
expect = GLOBAL;
if (expect == 'Window' && actual == 'XPCCrossOriginWrapper')
{
//   print('Skipping global object due to XPCCrossOriginWrapper. See bug 390946');
}
else
{
addThis();
}

status = 'new Object()';
actual = this.getJSClass(new Object());
expect = 'Object';
addThis();

status = 'new Function()';
actual = this.getJSClass(new Function());
expect = 'Function';
addThis();

status = 'new Array()';
actual = this.getJSClass(new Array());
expect = 'Array';
addThis();

status = 'new String()';
actual = this.getJSClass(new String());
expect = 'String';
addThis();

status = 'new Boolean()';
actual = this.getJSClass(new Boolean());
expect = 'Boolean';
addThis();

status = 'new Number()';
actual = this.getJSClass(new Number());
expect = 'Number';
addThis();

status = 'Math';
actual = this.getJSClass(Math);  // can't use 'new' with the Math object (EMCA3, 15.8)
expect = 'Math';
addThis();

status = 'new Date()';
actual = this.getJSClass(new Date());
expect = 'Date';
addThis();

status = 'new RegExp()';
actual = this.getJSClass(new RegExp());
expect = 'RegExp';
addThis();

status = 'new Error()';
actual = this.getJSClass(new Error());
expect = 'Error';
addThis();



//---------------------------------------------------------------------------------
test();
//---------------------------------------------------------------------------------



function addThis()
{
statusList[UBound] = status;
actualvalue[UBound] = actual;
expectedvalue[UBound] = expect;
UBound++;
}


function test()
{
//   enterFunc ('test');
//   printBugNumber(BUGNUMBER);
//   printStatus (summary);

for (i = 0; i < UBound; i++)
{
this.reportCompare(expectedvalue[i], actualvalue[i], getStatus(i));
}

//   exitFunc ('test');
}


function getStatus(i)
{
return statprefix + statusList[i];
}

},
//-----------------------------------------------------------------------------
test_class__002:function(){

var gTestfile = 'class-002.js';
var i = 0;
var UBound = 0;
var BUGNUMBER = '(none)';
var summary = 'Testing the internal [[Class]] property of native constructors';
var statprefix = 'Current constructor is: ';
var status = ''; var statusList = [ ];
var actual = ''; var actualvalue = [ ];
var expect= ''; var expectedvalue = [ ];

/*
* We set the expect variable each time only for readability.
* We expect 'Function' every time; see discussion above -
*/
status = 'Object';
actual = this.getJSClass(Object);
expect = 'Function';
addThis();

status = 'Function';
actual = this.getJSClass(Function);
expect = 'Function';
addThis();

status = 'Array';
actual = this.getJSClass(Array);
expect = 'Function';
addThis();

status = 'String';
actual = this.getJSClass(String);
expect = 'Function';
addThis();

status = 'Boolean';
actual = this.getJSClass(Boolean);
expect = 'Function';
addThis();

status = 'Number';
actual = this.getJSClass(Number);
expect = 'Function';
addThis();

status = 'Date';
actual = this.getJSClass(Date);
expect = 'Function';
addThis();

status = 'RegExp';
actual = this.getJSClass(RegExp);
expect = 'Function';
addThis();

status = 'Error';
actual = this.getJSClass(Error);
expect = 'Function';
addThis();



//---------------------------------------------------------------------------------
test();
//---------------------------------------------------------------------------------



function addThis()
{
statusList[UBound] = status;
actualvalue[UBound] = actual;
expectedvalue[UBound] = expect;
UBound++;
}


function test()
{
//   enterFunc ('test');
//   printBugNumber(BUGNUMBER);
//   printStatus (summary);

for (i = 0; i < UBound; i++)
{
this.reportCompare(expectedvalue[i], actualvalue[i], getStatus(i));
}

//   exitFunc ('test');
}


function getStatus(i)
{
return statprefix + statusList[i];
}

},
//-----------------------------------------------------------------------------
test_class__003:function(){

var gTestfile = 'class-003.js';
var i = 0;
var UBound = 0;
var BUGNUMBER = 56868;
var summary = 'Testing the internal [[Class]] property of native error types';
var statprefix = 'Current object is: ';
var status = ''; var statusList = [ ];
var actual = ''; var actualvalue = [ ];
var expect= ''; var expectedvalue = [ ];

/*
* We set the expect variable each time only for readability.
* We expect 'Error' every time; see discussion above -
*/
status = 'new Error()';
actual = this.getJSClass(new Error());
expect = 'Error';
addThis();

status = 'new EvalError()';
actual = this.getJSClass(new EvalError());
expect = 'Error';
addThis();

status = 'new RangeError()';
actual = this.getJSClass(new RangeError());
expect = 'Error';
addThis();

status = 'new ReferenceError()';
actual = this.getJSClass(new ReferenceError());
expect = 'Error';
addThis();

status = 'new SyntaxError()';
actual = this.getJSClass(new SyntaxError());
expect = 'Error';
addThis();

status = 'new TypeError()';
actual = this.getJSClass(new TypeError());
expect = 'Error';
addThis();

status = 'new URIError()';
actual = this.getJSClass(new URIError());
expect = 'Error';
addThis();



//---------------------------------------------------------------------------------
test();
//---------------------------------------------------------------------------------



function addThis()
{
statusList[UBound] = status;
actualvalue[UBound] = actual;
expectedvalue[UBound] = expect;
UBound++;
}


function test()
{
//   enterFunc ('test');
//   printBugNumber(BUGNUMBER);
//   printStatus (summary);

for (i = 0; i < UBound; i++)
{
this.reportCompare(expectedvalue[i], actualvalue[i], getStatus(i));
}

//   exitFunc ('test');
}


function getStatus(i)
{
return statprefix + statusList[i];
}

},
//-----------------------------------------------------------------------------
test_class__004:function(){

var gTestfile = 'class-004.js';
var i = 0;
var UBound = 0;
var BUGNUMBER = 56868;
var summary = 'Testing the internal [[Class]] property of native error constructors';
var statprefix = 'Current constructor is: ';
var status = ''; var statusList = [ ];
var actual = ''; var actualvalue = [ ];
var expect= ''; var expectedvalue = [ ];

/*
* We set the expect variable each time only for readability.
* We expect 'Function' every time; see discussion above -
*/
status = 'Error';
actual = this.getJSClass(Error);
expect = 'Function';
addThis();

status = 'EvalError';
actual = this.getJSClass(EvalError);
expect = 'Function';
addThis();

status = 'RangeError';
actual = this.getJSClass(RangeError);
expect = 'Function';
addThis();

status = 'ReferenceError';
actual = this.getJSClass(ReferenceError);
expect = 'Function';
addThis();

status = 'SyntaxError';
actual = this.getJSClass(SyntaxError);
expect = 'Function';
addThis();

status = 'TypeError';
actual = this.getJSClass(TypeError);
expect = 'Function';
addThis();

status = 'URIError';
actual = this.getJSClass(URIError);
expect = 'Function';
addThis();



//---------------------------------------------------------------------------------
test();
//---------------------------------------------------------------------------------



function addThis()
{
statusList[UBound] = status;
actualvalue[UBound] = actual;
expectedvalue[UBound] = expect;
UBound++;
}


function test()
{
//   enterFunc ('test');
//   printBugNumber(BUGNUMBER);
//   printStatus (summary);

for (i = 0; i < UBound; i++)
{
this.reportCompare(expectedvalue[i], actualvalue[i], getStatus(i));
}

//   exitFunc ('test');
}


function getStatus(i)
{
return statprefix + statusList[i];
}

},
//-----------------------------------------------------------------------------
test_class__005:function(){

var gTestfile = 'class-005.js';
var i = 0;
var UBound = 0;
var BUGNUMBER = '(none)';
var summary = 'Testing the internal [[Class]] property of user-defined types';
var statprefix = 'Current user-defined type is: ';
var status = ''; var statusList = [ ];
var actual = ''; var actualvalue = [ ];
var expect= ''; var expectedvalue = [ ];


Calf.prototype= new Cow();

/*
* We set the expect variable each time only for readability.
* We expect 'Object' every time; see discussion above -
*/
status = 'new Cow()';
actual = this.getJSClass(new Cow());
expect = 'Object';
addThis();

status = 'new Calf()';
actual = this.getJSClass(new Calf());
expect = 'Object';
addThis();


//---------------------------------------------------------------------------------
test();
//---------------------------------------------------------------------------------


function addThis()
{
statusList[UBound] = status;
actualvalue[UBound] = actual;
expectedvalue[UBound] = expect;
UBound++;
}


function test()
{
//   enterFunc ('test');
//   printBugNumber(BUGNUMBER);
//   printStatus (summary);

for (i = 0; i < UBound; i++)
{
this.reportCompare(expectedvalue[i], actualvalue[i], getStatus(i));
}

//   exitFunc ('test');
}


function getStatus(i)
{
return statprefix + statusList[i];
}


function Cow(name)
{
this.name=name;
}


function Calf(name)
{
this.name=name;
}

},
//-----------------------------------------------------------------------------
test_regress__361274:function(){

var BUGNUMBER = 361274;
var summary = 'Embedded nulls in property names';
var actual = '';
var expect = '';


//-----------------------------------------------------------------------------
test();
//-----------------------------------------------------------------------------

function test()
{
//   enterFunc ('test');
//   printBugNumber(BUGNUMBER);
//   printStatus (summary);

var x='123'+'\0'+'456';
var y='123'+'\0'+'789';
var a={};
a[x]=1;
a[y]=2;

this.reportCompare(1, a[x], summary + ': 123\\0456');
this.reportCompare(2, a[y], summary + ': 123\\0789');

//   exitFunc ('test');
}

},
//-----------------------------------------------------------------------------
test_regress__385393__07:function(){

var BUGNUMBER = 385393;
var summary = 'Regression test for bug 385393';
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

try
{
(2).eval();
}
catch(ex)
{
}

this.reportCompare(expect, actual, summary);

//   exitFunc ('test');
}

},
//-----------------------------------------------------------------------------
test_regress__72773:function(){

var gTestfile = 'regress-72773.js';
var BUGNUMBER = 72773;
var summary = "Regression test: we shouldn't crash on this code";
var status = '';
var actual = '';
var expect = '';
var sToEval = '';

/*
* This code should produce an error, but not a crash.
*  'TypeError: Function.prototype.toString called on incompatible object'
*/
sToEval += 'function Cow(name){this.name = name;}'
sToEval += 'function Calf(str){this.name = str;}'
sToEval += 'Calf.prototype = Cow;'
sToEval += 'new Calf().toString();'

status = 'Trying to catch an expected error';
try
{
eval(sToEval);
}
catch(e)
{
actual = this.getJSClass(e);
expect = 'Error';
}


//----------------------------------------------------------------------------------------------
test();
//----------------------------------------------------------------------------------------------


function test()
{
//   enterFunc ('test');
//   printBugNumber(BUGNUMBER);
//   printStatus (summary);

this.reportCompare(expect, actual, status);

//   exitFunc ('test');
}

},
//-----------------------------------------------------------------------------
test_regress__79129__001:function(){

var gTestfile = 'regress-79129-001.js';
var BUGNUMBER = 79129;
var summary = "Regression test: we shouldn't crash on this code";

//-----------------------------------------------------------------------------
test();
//-----------------------------------------------------------------------------


function test()
{
//   enterFunc ('test');
//   printBugNumber(BUGNUMBER);
//   printStatus (summary);
tryThis();
this.reportCompare('No Crash', 'No Crash', 'Should not crash');
//   exitFunc ('test');
}


function tryThis()
{
obj={};
obj.a = obj.b = obj.c = 1;
delete obj.a;
delete obj.b;
delete obj.c;
obj.d = obj.e = 1;
obj.a=1;
obj.b=1;
obj.c=1;
obj.d=1;
obj.e=1;
}

}}).endType()



gTestsubsuite = 'Object';

var cnNoObject = 'Unexpected Error!!! Parameter to this function must be an object';
var cnNoClass = 'Unexpected Error!!! Cannot find Class property';
var cnObjectToString = Object.prototype.toString;
var GLOBAL = 'window';

// checks that it's safe to call findType()
function getJSType(obj)
{
if (isObject(obj))
return findType(obj);
return cnNoObject;
}







