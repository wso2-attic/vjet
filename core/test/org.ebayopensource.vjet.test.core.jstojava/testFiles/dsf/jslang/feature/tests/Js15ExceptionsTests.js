vjo.ctype("dsf.jslang.feature.tests.Js15ExceptionsTests")
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

inRhino : function  () {
return true;
},




test_catchguard__002__n:function(){


var DESCRIPTION = "var in catch clause should have caused an error.";
var EXPECTED = "error";

var expect;
var actual;

test();

function test()
{
//   enterFunc ("test");

var EXCEPTION_DATA = "String exception";
expect = "String exception";
//   printStatus ("Catchguard var declaration negative test.");

try
{
throw EXCEPTION_DATA;
}
catch ( e)
{
actual = e + '';
}

this.reportCompare(expect, actual, DESCRIPTION);

//   exitFunc ("test");
}

},
test_catchguard__003__n:function(){


var DESCRIPTION = "Illegally constructed catchguard should have thrown an exception.";
var EXPECTED = "error";

var expect;
var actual;

test();

function test()
{
//   enterFunc ("test");

var EXCEPTION_DATA = "String exception";
//   printStatus ("Catchguard syntax negative test #2.");

try
{
expect = "String exception: 1";
throw EXCEPTION_DATA;
}
catch (e)
{
actual = e + ': 1';
}
//  catch (e) /* two non-guarded catch statements shoud generate an error */
//  {
//    actual = e + ': 2';
//  }

this.reportCompare(expect, actual, DESCRIPTION);

//   exitFunc ("test");
}

},
test_errstack__001:function(){

var UBound = 0;
var BUGNUMBER = '(none)';
var summary = 'Testing Error.stack';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];
var myErr = '';
var stackFrames = '';


function A(x,y)
{
return B(x+1,y+1);
}

function B(x,z)
{
return C(x+1,z+1);
}

function C(x,y)
{
return D(x+1,y+1);
}

function D(x,z)
{
try
{
throw new Error('meep!');
}
catch (e)
{
return e;
}
}


myErr = A(44,13);
stackFrames = getStackFrames(myErr);
status = this.inSection(1);
actual = stackFrames[0].substring(0,1);
expect = '@';
addThis();

status = this.inSection(2);
actual = stackFrames[1].substring(0,9);
expect = 'A(44,13)@';
addThis();

status = this.inSection(3);
actual = stackFrames[2].substring(0,9);
expect = 'B(45,14)@';
addThis();

status = this.inSection(4);
actual = stackFrames[3].substring(0,9);
expect = 'C(46,15)@';
addThis();

status = this.inSection(5);
actual = stackFrames[4].substring(0,9);
expect = 'D(47,16)@';
addThis();



myErr = A('44:foo','13:bar');
stackFrames = getStackFrames(myErr);
status = this.inSection(6);
actual = stackFrames[0].substring(0,1);
expect = '@';
addThis();

status = this.inSection(7);
actual = stackFrames[1].substring(0,21);
expect = 'A("44:foo","13:bar")@';
addThis();

status = this.inSection(8);
actual = stackFrames[2].substring(0,23);
expect = 'B("44:foo1","13:bar1")@';
addThis();

status = this.inSection(9);
actual = stackFrames[3].substring(0,25);
expect = 'C("44:foo11","13:bar11")@';
addThis();

status = this.inSection(10);
actual = stackFrames[4].substring(0,27);
expect = 'D("44:foo111","13:bar111")@';;
addThis();



/*
* Make the first frame occur in a function with an empty name -
*/
myErr = function() { return A(44,13); } ();
stackFrames = getStackFrames(myErr);
status = this.inSection(11);
actual = stackFrames[0].substring(0,1);
expect = '@';
addThis();

status = this.inSection(12);
actual = stackFrames[1].substring(0,3);
expect = '()@';
addThis();

status = this.inSection(13);
actual = stackFrames[2].substring(0,9);
expect = 'A(44,13)@';
addThis();

// etc. for the rest of the frames as above



/*
* Make the first frame occur in a function with name 'anonymous' -
*/
var f = Function('return A(44,13);');
myErr = f();
stackFrames = getStackFrames(myErr);
status = this.inSection(14);
actual = stackFrames[0].substring(0,1);
expect = '@';
addThis();

status = this.inSection(15);
actual = stackFrames[1].substring(0,12);
expect = 'anonymous()@';
addThis();

status = this.inSection(16);
actual = stackFrames[2].substring(0,9);
expect = 'A(44,13)@';
addThis();

// etc. for the rest of the frames as above



/*
* Make a user-defined error via the Error() function -
*/
var message = 'Hi there!'; var fileName = 'file name'; var lineNumber = 0;
myErr = new Error(message, fileName, lineNumber);
stackFrames = getStackFrames(myErr);
status = this.inSection(17);
actual = stackFrames[0].substring(0,1);
expect = '@';
addThis();


/*
* Now use the |new| keyword. Re-use the same params -
*/
myErr = new Error(message, fileName, lineNumber);
stackFrames = getStackFrames(myErr);
status = this.inSection(18);
actual = stackFrames[0].substring(0,1);
expect = '@';
addThis();




//-----------------------------------------------------------------------------
test();
//-----------------------------------------------------------------------------



/*
* Split the string |err.stack| along its '\n' delimiter.
* As of 2002-02-28 |err.stack| ends with the delimiter, so
* the resulting array has an empty string as its last element.
*
* Pop that useless element off before doing anything.
* Then reverse the array, for convenience of indexing -
*/
function getStackFrames(err)
{
var arr = err.stack.split('\n');
arr.pop();
return arr.reverse();
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
test_regress__121658:function(){

var UBound = 0;
var BUGNUMBER = 121658;
var msg = '"Too much recursion" errors should be safely caught by try...catch';
var TEST_PASSED = 'i retained the value it had at location of error';
var TEST_FAILED = 'i did NOT retain this value';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];
var i;


function f()
{
++i;

// try...catch should catch the "too much recursion" error to ensue
try
{
f();
}
catch(e)
{
}
}

i=0;
f();
status = this.inSection(1);
actual = (i>0);
expect = true;
addThis();



// Now try in function scope -
function g()
{
f();
}

i=0;
g();
status = this.inSection(2);
actual = (i>0);
expect = true;
addThis();



// Now try in eval scope -
var sEval = 'function h(){++i; try{h();} catch(e){}}; i=0; h();';
eval(sEval);
status = this.inSection(3);
actual = (i>0);
expect = true;
addThis();



// Try in eval scope and mix functions up -
sEval = 'function a(){++i; try{h();} catch(e){}}; i=0; a();';
eval(sEval);
status = this.inSection(4);
actual = (i>0);
expect = true;
addThis();




//-----------------------------------------------------------------------------
test();
//-----------------------------------------------------------------------------



function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = formatThis(actual);
expectedvalues[UBound] = formatThis(expect);
UBound++;
}


function formatThis(bool)
{
return bool? TEST_PASSED : TEST_FAILED;
}


function test()
{
//   enterFunc('test');
//   printBugNumber(BUGNUMBER);
//   printStatus(msg);

for (var i=0; i<UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//   exitFunc ('test');
}

},
test_regress__123002:function(){

var LENGTH_RHINO = 1;
var LENGTH_SPIDERMONKEY = 3;
var UBound = 0;
var BUGNUMBER = 123002;
var summary = 'Testing Error.length';
var QUOTE = '"';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


/*
* Are we in Rhino or SpiderMonkey?
*/
var LENGTH_EXPECTED = this.inRhino()? LENGTH_RHINO : LENGTH_SPIDERMONKEY;

/*
* The various NativeError objects; see ECMA-262 Edition 3, Section 15.11.6
*/
var errObjects = [new Error(), new EvalError(), new RangeError(),
new ReferenceError(), new SyntaxError(), new TypeError(), new URIError()];

var i ;//<Number
for ( i in errObjects)
{
var err = errObjects[i];
status = this.inSection(quoteThis(err.name));
expect = LENGTH_EXPECTED;
addThis();
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


function quoteThis(text)
{
return QUOTE + text + QUOTE;
}

},
test_regress__232182:function(){

//-----------------------------------------------------------------------------

var BUGNUMBER = 232182;
var summary = 'Display non-ascii characters in JS exceptions';
var actual = '';
var expect = 'no error';

// printBugNumber(BUGNUMBER);
// printStatus (summary);

/*
* This test accesses an undefined Unicode symbol. If the code has not been
* compiled with JS_C_STRINGS_ARE_UTF8, the thrown error truncates Unicode
* characters to bytes. Accessing \u0440\u0441, therefore, results in a
* message talking about an undefined variable 'AB' (\x41\x42).
*/
var utf8Enabled = false;
try
{
}
catch (e)
{
utf8Enabled = (e.message.charAt (0) == '\u0441');
}

// Run the tests only if UTF-8 is enabled

// printStatus('UTF-8 is ' + (utf8Enabled?'':'not ') + 'enabled');

if (!utf8Enabled)
{
this.reportCompare('Not run', 'Not run', 'utf8 is not enabled');
}
else
{
status = summary + ': Throw Error with Unicode message';
expect = 'test \u0440\u0441';
try
{
throw Error (expect);
}
catch (e1)
{
actual = e1.message;
}
this.reportCompare(expect, actual, status);
var stringsAreUTF8;
var testUTF8;
var stringsAreUtf8;
var testUtf8;
var inShell = (typeof stringsAreUTF8 == "function");
if (!inShell)
{
inShell = (typeof stringsAreUtf8  == "function");
if (inShell)
{
stringsAreUTF8 = stringsAreUtf8;
testUTF8 = testUtf8;
}
}

if (inShell && stringsAreUTF8())
{
status = summary + ': UTF-8 test: bad UTF-08 sequence';
expect = 'Error';
actual = 'No error!';
try
{
testUTF8(1);
}
catch (e2)
{
actual = 'Error';
}
this.reportCompare(expect, actual, status);

status = summary + ': UTF-8 character too big to fit into Unicode surrogate pairs';
expect = 'Error';
actual = 'No error!';
try
{
testUTF8(2);
}
catch (e3)
{
actual = 'Error';
}
this.reportCompare(expect, actual, status);

status = summary + ': bad Unicode surrogate character';
expect = 'Error';
actual = 'No error!';
try
{
testUTF8(3);
}
catch (e4)
{
actual = 'Error';
}
this.reportCompare(expect, actual, status);
}

if (inShell)
{
status = summary + ': conversion target buffer overrun';
expect = 'Error';
actual = 'No error!';
try
{
testUTF8(4);
}
catch (e4)
{
actual = 'Error';
}
this.reportCompare(expect, actual, status);
}
}

},
test_regress__257751:function(){

//-----------------------------------------------------------------------------
var BUGNUMBER = 257751;
var summary = 'RegExp Syntax Errors should have lineNumber and fileName';
var actual = '';
var expect = '';

// printBugNumber(BUGNUMBER);
// printStatus (summary);

var status;
var re;

status = summary + ' ' + this.inSection(1) + ' RegExp("\\\\") ';
try
{
expect = 'Pass';
re = RegExp('\\');
}
catch(e)
{
if (e.fileName && e.lineNumber)
{
actual = 'Pass';
}
else
{
actual = 'Fail';
}
}
this.reportCompare(expect, actual, status);

status = summary + ' ' + this.inSection(2) + ' RegExp(")") ';
try
{
expect = 'Pass';
re = RegExp(')');
}
catch(e1)
{
if (e1.fileName && e1.lineNumber)
{
actual = 'Pass';
}
else
{
actual = 'Fail';
}
}
this.reportCompare(expect, actual, status);

status = summary + ' ' + this.inSection(3) + ' /\\\\/ ';
try
{
expect = 'Pass';
re = eval('/\\/');
}
catch(e2)
{
if (e2.fileName && e2.lineNumber)
{
actual = 'Pass';
}
else
{
actual = 'Fail';
}
}
this.reportCompare(expect, actual, status);

status = summary + ' ' + this.inSection(4) + ' /)/ ';
try
{
expect = 'Pass';
re = eval('/)/');
}
catch(e3)
{
if (e3.fileName && e3.lineNumber)
{
actual = 'Pass';
}
else
{
actual = 'Fail';
}
}
this.reportCompare(expect, actual, status);

},
test_regress__273931:function(){

//-----------------------------------------------------------------------------
var BUGNUMBER = 273931;
var summary = 'Pop scope chain in exception handling';
var actual = '';
var expect = 'ReferenceError';

// printBugNumber(BUGNUMBER);
// printStatus (summary);

status = summary + ' ' + this.inSection(1) + ' ';
try
{
with ({foo:"bar"})
throw 42;
}
catch (e)
{
try
{
}
catch(ee)
{
actual = ee.name;
}
}

this.reportCompare(expect, actual, status);

status = summary + ' ' + this.inSection(2) + ' ';
try
{
with ({foo:"bar"})
eval("throw 42");
}
catch (e2)
{
try
{
//     printStatus(foo);
}
catch(ee2)
{
actual = ee2.name;
}
}

this.reportCompare(expect, actual, status);

status = summary + ' ' + this.inSection(3) + ' ';
try
{
var s = "throw 42";
with ({foo:"bar"})
eval(s);
}
catch (e3)
{
try
{
//     printStatus(foo);
}
catch(ee3)
{
actual = ee3.name;
}
}

this.reportCompare(expect, actual, status);

},
test_regress__315147:function(){

//-----------------------------------------------------------------------------
var BUGNUMBER = 315147;
var summary = 'Error JSMSG_UNDEFINED_PROP should be JSEXN_REFERENCEERR';
var actual = '';
var expect = 'ReferenceError';

// printBugNumber(BUGNUMBER);
// printStatus (summary);


var o = {};

try
{
o.foo;
actual = 'no error';
}
catch(ex1)
{
actual = ex1.name;
}

this.reportCompare(expect, actual, summary);

},
test_regress__332472:function(){

//-----------------------------------------------------------------------------
var BUGNUMBER = 332472;
var summary = 'new RegExp() ignores string boundaries when throwing exceptions';
var actual = '';
//var expect = 'SyntaxError: invalid quantifier ?asdf';
var expect = 'SyntaxError: Invalid quantifier ?';

// printBugNumber(BUGNUMBER);
// printStatus (summary);

var str1 = "?asdf\nAnd you really shouldn't see this!";
var str2 = str1.substr(0, 5);
try {
new RegExp(str2);
}
catch(ex1) {
//   printStatus(ex);
actual = ex1 + '';
}

this.reportCompare(expect, actual, summary);

},
test_regress__333728:function(){

//-----------------------------------------------------------------------------
var BUGNUMBER = 333728;
var summary = 'Throw ReferenceErrors for typeof(...undef)';
var actual = '';
var expect = 'ReferenceError';

// printBugNumber(BUGNUMBER);
// printStatus (summary);

try
{
//  actual = typeof (0, undef);
}
catch(ex12)
{
actual = ex12.name;
}

//this.reportCompare(expect, actual, summary + ': typeof (0, undef)');
var undef;

try
{
actual = typeof (0 || undef);
}
catch(ex)
{
actual = ex.name;
}

this.reportCompare("undefined", actual, summary + ': typeof (0 || undef)');
//this.reportCompare(expect, actual, summary + ': typeof (0 || undef)');
try
{
actual = typeof (1 && undef);
}
catch(ex2)
{
actual = ex2.name;
}

//this.reportCompare(expect, actual, summary + ': typeof (1 && undef)');
this.reportCompare("undefined", actual, summary + ': typeof (0 || undef)');

/*
try
{
actual = typeof (0 ? 0 : undef);
}
catch(ex)
{
actual = ex.name;
}

this.reportCompare(expect, actual, summary + ': typeof (0 ? 0 : undef)');
*/

/*
try
{
actual = typeof (1 ? undef : 0);
}
catch(ex)
{
actual = ex.name;
}

this.reportCompare(expect, actual, summary + ': typeof (1 ? undef : 0)');
*/

try
{
actual = typeof (!this ? 0 : undef);
}
catch(ex4)
{
actual = ex4.name;
}

this.reportCompare(expect, actual, summary + ': typeof (!this ? 0 : undef)');

},
test_regress__342359:function(){

//-----------------------------------------------------------------------------
var BUGNUMBER = 342359;
var summary = 'Overriding ReferenceError should stick';
var actual = '';
var expect = '';

// printBugNumber(BUGNUMBER);
// printStatus (summary);

// work around bug 376957
var SavedReferenceError = ReferenceError;

try
{
}
catch(ex)
{
}

try
{
}
catch(ex5)
{
//   print(ex + '');
}

if (SavedReferenceError == ReferenceError)
{
actual = expect = 'Test ignored due to bug 376957';
}
else
{
expect = 5;
actual = ReferenceError;
}
this.reportCompare(expect, actual, summary);

},
test_regress__347674:function(){

//-----------------------------------------------------------------------------
var BUGNUMBER     = "347674";
var summary = "ReferenceError thrown when accessing exception bound in a " +
"catch block in a try block within that catch block";
var actual, expect;

// printBugNumber(BUGNUMBER);
// printStatus(summary);

/**************
* BEGIN TEST *
**************/

var failed = false;

function foo()
{
try
{
throw "32.9";
}
catch (e)
{
try
{
var errorCode = /^(\d+)\s+.*$/.exec(e)[1];
}
catch (e2)
{
void("*** internal error: e == " + e + ", e2 == " + e2);
throw e2;
}
}
}

try
{
try
{
foo();
}
catch (ex)
{
if (!(ex instanceof TypeError))
throw "Wrong value thrown!\n" +
"  expected: a TypeError ('32.9' doesn't match the regexp)\n" +
"  actual: " + ex;
}
}
catch (e)
{
failed = e;
}

expect = false;
actual = failed;

this.reportCompare(expect, actual, summary);

},
test_regress__350650__n:function(){

//-----------------------------------------------------------------------------
var BUGNUMBER = 350650;
var summary = 'js reports "uncaught exception';
var actual = 'Error';
var expect = 'Error';

//expectExitCode(3);

//-----------------------------------------------------------------------------
test();
//-----------------------------------------------------------------------------

function test()
{
//   enterFunc ('test');
//   printBugNumber(BUGNUMBER);
//   printStatus (summary);

function exc() { this.toString = function() { return "EXC"; } }
throw new exc();

this.reportCompare(expect, actual, summary);

//   exitFunc ('test');
}

},
test_regress__350837:function(){

//-----------------------------------------------------------------------------
var BUGNUMBER = 350837;
var summary = 'clear cx->throwing in finally';
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

expect = 'F';

function f()
{
actual = "F";
}

try
{
try {
throw 1;
} finally {
f.call(this);
}
}
catch(ex)
{
this.reportCompare(1, ex, summary);
}

this.reportCompare(expect, actual, summary);

//   exitFunc ('test');
}

}}).endType()

