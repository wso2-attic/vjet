vjo.ctype("dsf.jslang.feature.tests.Ecma3ExceptionsTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({


reportCompare : function  (expectedValue, actualValue, statusItems) {
new this.vj$.BaseTest().TestCase( ' ',  statusItems, expectedValue,  actualValue);
},


inSection: function(x) {
var SECT_PREFIX = 'Section ';
var SECT_SUFFIX = ' of test - ';
return SECT_PREFIX + x + SECT_SUFFIX;
},


//-----------------------------------------------------------------------------
test_15_11_1_1:function(){

var gTestfile = '15.11.1.1.js';
var UBound = 0;
var BUGNUMBER = '';
var summary = 'Ensuring normal function call of Error (ECMA-262 Ed.3 15.11.1.1)';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];
var EMPTY_STRING = '';
var EXPECTED_FORMAT = 0;


function otherScope(msg)
{
return Error(msg);
}


status =this.inSection(1);
var err1 = Error('msg1');
actual = examineThis(err1, 'msg1');
expect = EXPECTED_FORMAT;
addThis();

status =this.inSection(2);
var err2 = otherScope('msg2');
actual = examineThis(err2, 'msg2');
expect = EXPECTED_FORMAT;
addThis();

status =this.inSection(3);
var err3 = otherScope('msg3');
actual = examineThis(err3, EMPTY_STRING);
expect = EXPECTED_FORMAT;
addThis();

status =this.inSection(4);
var err4 = eval("Error('msg4')");
actual = examineThis(err4, 'msg4');
expect = EXPECTED_FORMAT;
addThis();



//-----------------------------------------------------------------------------
test();
//-----------------------------------------------------------------------------



/*
* Searches err.toString() for err.name + ':' + err.message,
* with possible whitespace on each side of the colon sign.
*
* We allow for no colon in case err.message was not provided by the user.
* In such a case, SpiderMonkey and Rhino currently set err.message = '',
* as allowed for by ECMA 15.11.4.3. This makes |pattern| work in this case.
*
* If this is ever changed to a non-empty string, e.g. 'undefined',
* you may have to modify |pattern| to take that into account -
*
*/
function examineThis(err, msg)
{
var pattern = err.name + '\\s*:?\\s*' + msg;
return err.toString().search(RegExp(pattern));
}


function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
}


function test()
{
//   enterFunc ('test');
//   printBugNumber(BUGNUMBER);
//   printStatus (summary);

for (var i = 0; i < UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//   exitFunc ('test');
}

},

//-----------------------------------------------------------------------------
test_15_11_4_4__1:function(){

var gTestfile = '15.11.4.4-1.js';
var UBound = 0;
var BUGNUMBER = '(none)';
var summary = 'Testing Error.prototype.toString()';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];
var EMPTY_STRING = '';
var EXPECTED_FORMAT = 0;


status = this.inSection(1);
var err1 = new Error('msg1');
actual = examineThis(err1, 'msg1');
expect = EXPECTED_FORMAT;
addThis();

status = this.inSection(2);
var err2 = new Error(err1);
actual = examineThis(err2, err1);
expect = EXPECTED_FORMAT;
addThis();

status = this.inSection(3);
var err3 = new Error();
actual = examineThis(err3, EMPTY_STRING);
expect = EXPECTED_FORMAT;
addThis();

status = this.inSection(4);
var err4 = new Error(EMPTY_STRING);
actual = examineThis(err4, EMPTY_STRING);
expect = EXPECTED_FORMAT;
addThis();

// now generate a run-time error -
status = this.inSection(5);
try
{
eval('1=2');
}
catch(err5)
{
actual = examineThis(err5, '.*');
}
expect = EXPECTED_FORMAT;
addThis();



//-----------------------------------------------------------------------------
test();
//-----------------------------------------------------------------------------



/*
* Searches err.toString() for err.name + ':' + err.message,
* with possible whitespace on each side of the colon sign.
*
* We allow for no colon in case err.message was not provided by the user.
* In such a case, SpiderMonkey and Rhino currently set err.message = '',
* as allowed for by ECMA 15.11.4.3. This makes |pattern| work in this case.
*
* If this is ever changed to a non-empty string, e.g. 'undefined',
* you may have to modify |pattern| to take that into account -
*
*/
function examineThis(err, msg)
{
var pattern = err.name + '\\s*:?\\s*' + msg;
return err.toString().search(RegExp(pattern));
}


function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
}


function test()
{
//   enterFunc ('test');
//   printBugNumber(BUGNUMBER);
//   printStatus (summary);

for (var i = 0; i < UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//   exitFunc ('test');
}

},
//-----------------------------------------------------------------------------
test_15_11_7_6__001:function(){

var gTestfile = '15.11.7.6-001.js';
var UBound = 0;
var BUGNUMBER = 201989;
var summary = 'Prototype of predefined error objects should be DontEnum';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


/*
* Tests that |F.prototype| is not enumerable in |F|
*/
function testDontEnum(F)
{
var proto = F.prototype;

for (var prop in F)
{
if (F[prop] === proto)
return false;
}
return true;
}


var list = [
"Error",
"ConversionError",
"EvalError",
"RangeError",
"ReferenceError",
"SyntaxError",
"TypeError",
"URIError"
];

var i;//<Number
for (i in list)
{
var F = this[list[i]];

// Test for |F|; e.g. Rhino defines |ConversionError| while SM does not.
if (F)
{
status = 'Testing DontEnum attribute of |' + list[i] + '.prototype|';
actual = testDontEnum(F);
expect = true;
addThis();
}
}



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
test_15_11_7_6__002:function(){

var gTestfile = '15.11.7.6-002.js';
var UBound = 0;
var BUGNUMBER = 201989;
var summary = 'Prototype of predefined error objects should be DontDelete';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


/*
* Tests that |F.prototype| is DontDelete
*/
function testDontDelete(F)
{
var orig = F.prototype;
try
{
delete F.prototype;
}
catch (e)
{
}
return F.prototype === orig;
}


var list = [
"Error",
"ConversionError",
"EvalError",
"RangeError",
"ReferenceError",
"SyntaxError",
"TypeError",
"URIError"
];

var i;//<Number
for (i in list)
{
var F = this[list[i]];

// Test for |F|; e.g. Rhino defines |ConversionError| while SM does not.
if (F)
{
status = 'Testing DontDelete attribute of |' + list[i] + '.prototype|';
actual = testDontDelete(F);
expect = true;
addThis();
}
}



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
test_15_11_7_6__003:function(){

var gTestfile = '15.11.7.6-003.js';
var UBound = 0;
var BUGNUMBER = 201989;
var summary = 'Prototype of predefined error objects should be ReadOnly';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


/*
* Tests that |F.prototype| is ReadOnly
*/
function testReadOnly(F)
{
var orig = F.prototype;
try
{
F.prototype = new Object();
}
catch (e)
{
}
return F.prototype === orig;
}


var list = [
"Error",
"ConversionError",
"EvalError",
"RangeError",
"ReferenceError",
"SyntaxError",
"TypeError",
"URIError"
];

var i ;//<Number
for (i in list)
{
var F = this[list[i]];

// Test for |F|; e.g. Rhino defines |ConversionError| while SM does not.
if (F)
{
status = 'Testing ReadOnly attribute of |' + list[i] + '.prototype|';
actual = testReadOnly(F);
expect = true;
addThis();
}
}



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
test_binding__001:function(){

var gTestfile = 'binding-001.js';
var UBound = 0;
var BUGNUMBER = '(none)';
var summary = 'Testing binding of function names';
var ERR_REF_YES = 'ReferenceError';
var ERR_REF_NO = 'did NOT generate a ReferenceError';
var statusitems = [];
var actualvalues = [];
var expectedvalues = [];
var status = summary;
var actual = ERR_REF_NO;
var expect= ERR_REF_YES;


try
{
var f = function sum(){};
//  print(sum);
}
catch (e)
{
status = 'Section 1 of test';
actual = e instanceof ReferenceError;
expect = true;
addThis();


/*
* This test is more literal, and one day may not be valid.
* Searching for literal string "ReferenceError" in e.toString()
*/
status = 'Section 2 of test';
var match = e.toString().search(/ReferenceError/);
actual = (match > -1);
expect = true;
addThis();
}



//-----------------------------------------------------------------------------
test();
//-----------------------------------------------------------------------------


function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = isReferenceError(actual);
expectedvalues[UBound] = isReferenceError(expect);
UBound++;
}


function test()
{
//   enterFunc ('test');
//   printBugNumber(BUGNUMBER);
//   printStatus (summary);

for (var i = 0; i < UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//   exitFunc ('test');
}


// converts a Boolean result into a textual result -
function isReferenceError(bResult)
{
return bResult? ERR_REF_YES : ERR_REF_NO;
}

},
//-----------------------------------------------------------------------------
test_regress__181654:function(){

var gTestfile = 'regress-181654.js';
var UBound = 0;
var BUGNUMBER = '181654';
var summary = 'Calling toString for an object derived from the Error class should be possible.';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];
var EMPTY_STRING = '';
var EXPECTED_FORMAT = 0;


// derive MyError from Error
function MyError( msg )
{
this.message = msg;
}
MyError.prototype = new Error();
MyError.prototype.name = "MyError";


status = this.inSection(1);
var err1 = new MyError('msg1');
actual = examineThis(err1, 'msg1');
expect = EXPECTED_FORMAT;
addThis();

status = this.inSection(2);
var err2 = new MyError(String(err1));
actual = examineThis(err2, err1);
expect = EXPECTED_FORMAT;
addThis();

status = this.inSection(3);
var err3 = new MyError();
actual = examineThis(err3, EMPTY_STRING);
expect = EXPECTED_FORMAT;
addThis();

status = this.inSection(4);
var err4 = new MyError(EMPTY_STRING);
actual = examineThis(err4, EMPTY_STRING);
expect = EXPECTED_FORMAT;
addThis();

// now generate an error -
status = this.inSection(5);
try
{
throw new MyError("thrown");
}
catch(err5)
{
actual = examineThis(err5, "thrown");
}
expect = EXPECTED_FORMAT;
addThis();



//-----------------------------------------------------------------------------
test();
//-----------------------------------------------------------------------------



/*
* Searches err.toString() for err.name + ':' + err.message,
* with possible whitespace on each side of the colon sign.
*
* We allow for no colon in case err.message was not provided by the user.
* In such a case, SpiderMonkey and Rhino currently set err.message = '',
* as allowed for by ECMA 15.11.4.3. This makes |pattern| work in this case.
*
* If this is ever changed to a non-empty string, e.g. 'undefined',
* you may have to modify |pattern| to take that into account -
*
*/
function examineThis(err, msg)
{
var pattern = err.name + '\\s*:?\\s*' + msg;
return err.toString().search(RegExp(pattern));
}


function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
}


function test()
{
//   enterFunc ('test');
//   printBugNumber(BUGNUMBER);
//   printStatus (summary);

for (var i = 0; i < UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//   exitFunc ('test');
}

},
//-----------------------------------------------------------------------------
test_regress__181914:function(){

var gTestfile = 'regress-181914.js';
var UBound = 0;
var BUGNUMBER = '181914';
var summary = 'Calling a user-defined superconstructor';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];
var EMPTY_STRING = '';
var EXPECTED_FORMAT = 0;


// make a user-defined version of the Error constructor
function _Error(msg)
{
this.message = msg;
}
_Error.prototype = new Error();
_Error.prototype.name = '_Error';


// derive MyApplyError from _Error
function MyApplyError(msg)
{
if(this instanceof MyApplyError)
_Error.apply(this, arguments);
else
return new MyApplyError(msg);
}
MyApplyError.prototype = new _Error();
MyApplyError.prototype.name = "MyApplyError";


// derive MyCallError from _Error
function MyCallError(msg)
{
if(this instanceof MyCallError)
_Error.call(this, msg);
else
return new MyCallError(msg);
}
MyCallError.prototype = new _Error();
MyCallError.prototype.name = "MyCallError";


function otherScope(msg)
{
return MyApplyError(msg);
}


status = this.inSection(1);
var err1 = new MyApplyError('msg1');
actual = examineThis(err1, 'msg1');
expect = EXPECTED_FORMAT;
addThis();

status = this.inSection(2);
var err2 = new MyCallError('msg2');
actual = examineThis(err2, 'msg2');
expect = EXPECTED_FORMAT;
addThis();

status = this.inSection(3);
var err3 = MyApplyError('msg3');
actual = examineThis(err3, 'msg3');
expect = EXPECTED_FORMAT;
addThis();

status = this.inSection(4);
var err4 = MyCallError('msg4');
actual = examineThis(err4, 'msg4');
expect = EXPECTED_FORMAT;
addThis();

status = this.inSection(5);
var err5 = otherScope('msg5');
actual = examineThis(err5, 'msg5');
expect = EXPECTED_FORMAT;
addThis();

status = this.inSection(6);
var err6 = otherScope('msg6');
actual = examineThis(err6, EMPTY_STRING);
expect = EXPECTED_FORMAT;
addThis();

status = this.inSection(7);
var err7 = eval("MyApplyError('msg7')");
actual = examineThis(err7, 'msg7');
expect = EXPECTED_FORMAT;
addThis();

status = this.inSection(8);
var err8;
try
{
throw MyApplyError('msg8');
}
catch(e)
{
if(e instanceof Error)
err8 = e;
}
actual = examineThis(err8, 'msg8');
expect = EXPECTED_FORMAT;
addThis();



//-----------------------------------------------------------------------------
test();
//-----------------------------------------------------------------------------



// Searches |err.toString()| for |err.name + ':' + err.message|
function examineThis(err, msg)
{
var pattern = err.name + '\\s*:?\\s*' + msg;
return err.toString().search(RegExp(pattern));
}


function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
}


function test()
{
//   enterFunc ('test');
//   printBugNumber(BUGNUMBER);
//   printStatus (summary);

for (var i = 0; i < UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//   exitFunc ('test');
}

},
//-------------------------------------------------------------------------------------------------
test_regress__58946:function(){

var BUGNUMBER = '58946';
var stat =  'Testing a return statement inside a catch statement inside a function';

test();

function test() {
//   enterFunc ("test");
//   printBugNumber(BUGNUMBER);
//   printStatus (stat);

expect = 'PASS';

function f()
{
try
{
throw 'PASS';
}
catch(e)
{
return e;
}
}

actual = f();

this.reportCompare(expect, actual, stat);

//   exitFunc ("test");
}

},
//-----------------------------------------------------------------------------
test_regress__95101:function(){

var gTestfile = 'regress-95101.js';
var UBound = 0;
var BUGNUMBER = 95101;
var summary = 'Invoking an undefined function should produce a ReferenceError';
var msgERR_REF_YES = 'ReferenceError';
var msgERR_REF_NO = 'did NOT generate a ReferenceError';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


try
{
var xxxyyyzzz = null;
xxxyyyzzz();
}
catch (e)
{
status = 'Section 1 of test';
actual = e instanceof ReferenceError;
expect = true;
addThis();


/*
* This test is more literal, and may one day be invalid.
* Searching for literal string "ReferenceError" in e.toString()
*/
status = 'Section 2 of test';
var match = e.toString().search(/ReferenceError/);
actual = (match > -1);
expect = true;
addThis();
}



//-----------------------------------------------------------------------------
test();
//-----------------------------------------------------------------------------


function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = isReferenceError(actual);
expectedvalues[UBound] = isReferenceError(expect);
UBound++;
}


function test()
{
//   enterFunc ('test');
//   printBugNumber(BUGNUMBER);
//   printStatus (summary);

for (var i = 0; i < UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//   exitFunc ('test');
}


// converts a Boolean result into a textual result -
function isReferenceError(bResult)
{
return bResult? msgERR_REF_YES : msgERR_REF_NO;
}

}}).endType()

//    function inSection(x) {
//    	var SECT_PREFIX = 'Section ';
//	var SECT_SUFFIX = ' of test - ';
//        return SECT_PREFIX + x + SECT_SUFFIX;
//    }
//
//    	function this.reportCompare (expectedValue, actualValue, statusItems) {
//		this.TestCase( ' ',  statusItems, expectedValue,  actualValue);
//	}



