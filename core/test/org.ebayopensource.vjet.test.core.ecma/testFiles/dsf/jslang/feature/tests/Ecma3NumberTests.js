vjo.ctype("dsf.jslang.feature.tests.Ecma3NumberTests")
.inherits("dsf.jslang.feature.tests.BaseTestEcma3")
.protos({

constructs: function() {
this.base();
},

//-----------------------------------------------------------------------------
test_15_7_4_2__01: function() {
//var BUGNUMBER = "411889";
var summary = "num.toString(), num.toString(10), and num.toString(undefined)" +
" should all be equivalent";
var actual, expect;

//printBugNumber(BUGNUMBER);
//printStatus(summary);

/**************
* BEGIN TEST *
**************/

var failed = false;

try
{
var noargs = 3.3.toString();
var tenarg = 3.3.toString(10);
var undefarg = 3.3.toString(undefined);

if (noargs !== tenarg)
throw "() !== (10): " + noargs + " !== " + tenarg;
if (tenarg !== undefarg)
throw "(10) !== (undefined): " + tenarg + " !== " + undefarg;
}
catch (e)
{
failed = e;
}

expect = false;
actual = failed;

this.reportCompare(expect, actual, summary);

expect = 1;
actual = 3.3.toString.length;
this.reportCompare(expect, actual, '3.3.toString.length should be 1');

},

//-----------------------------------------------------------------------------
test_15_7_4_3__01: function() {
//var BUGNUMBER = "412068";
var summary = "num.toLocalString incorrectly accesses its first argument " +
"even when no first argument has been given";
var actual, expect;

//printBugNumber(BUGNUMBER);
//printStatus(summary);

/**************
* BEGIN TEST *
**************/

var failed = false;

try
{
if ("3" !== 3..toLocalString())
throw '"3" should equal 3..toLocalString()';
if ("9" !== 9..toLocalString(8))
throw 'Number.prototype.toLocalString should ignore its first argument';
}
catch (e)
{
failed = e;
}

expect = false;
actual = failed;

this.reportCompare(expect, actual, summary);

},

//-----------------------------------------------------------------------------
test_15_7_4_3__02: function() {
//var BUGNUMBER = "446494";
var summary = "num.toLocalString should handle exponents";
var actual, expect;

//printBugNumber(BUGNUMBER);
//printStatus(summary);

expect = '1e-10';
actual = 1e-10.toLocalString();
this.reportCompare(expect, actual, summary + ': ' + expect);

expect = 'Infinity';
actual = Infinity.toLocalString();
this.reportCompare(expect, actual, summary + ': ' + expect);

},

/*
* Date: 2001-07-15
*
* SUMMARY: Testing Number.prototype.toFixed(fractionDigits)
* See EMCA 262 Edition 3 Section 15.7.4.5
*
* Also see http://bugzilla.mozilla.org/show_bug.cgi?id=90551
*
*/
//-----------------------------------------------------------------------------
test_15_7_4_5__1: function() {
//var gTestfile = '15.7.4.5-1.js';
var UBound = 0;
//var BUGNUMBER = '(none)';
var summary = 'Testing Number.prototype.toFixed(fractionDigits)';
var cnIsRangeError = 'instanceof RangeError';
var cnNotRangeError = 'NOT instanceof RangeError';
var cnNoErrorCaught = 'NO ERROR CAUGHT...';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];
var testNum = 234.2040506;


status = 'Section A of test: no error intended!';
actual = testNum.toFixed(4);
expect = '234.2041';
captureThis();


///////////////////////////    OOPS....    ///////////////////////////////
/*************************************************************************
* 15.7.4.5 Number.prototype.toFixed(fractionDigits)
*
* An implementation is permitted to extend the behaviour of toFixed
* for values of fractionDigits less than 0 or greater than 20. In this
* case toFixed would not necessarily throw RangeError for such values.

status = 'Section B of test: expect RangeError because fractionDigits < 0';
actual = catchError('testNum.toFixed(-4)');
expect = cnIsRangeError;
captureThis();

status = 'Section C of test: expect RangeError because fractionDigits > 20 ';
actual = catchError('testNum.toFixed(21)');
expect = cnIsRangeError;
captureThis();
*************************************************************************/


status = 'Section D of test: no error intended!';
actual =  0.00001.toFixed(2);
expect = '0.00';
captureThis();

status = 'Section E of test: no error intended!';
actual =  0.000000000000000000001.toFixed(20);
expect = '0.00000000000000000000';
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

for (var i = 0; i < UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//exitFunc ('test');
//}


function catchError(sEval)
{
try {eval(sEval);}
catch(e) {return isRangeError(e);}
return cnNoErrorCaught;
}


function isRangeError(obj)
{
if (obj instanceof RangeError)
return cnIsRangeError;
return cnNotRangeError;
}

},

/*
* Date: 2001-07-15
*
* SUMMARY: Testing Number.prototype.toExponential(fractionDigits)
* See EMCA 262 Edition 3 Section 15.7.4.6
*
* Also see http://bugzilla.mozilla.org/show_bug.cgi?id=90551
*
*/
//-----------------------------------------------------------------------------
test_15_7_4_6__1: function() {
//var gTestfile = '15.7.4.6-1.js';
var UBound = 0;
//var BUGNUMBER = '(none)';
var summary = 'Testing Number.prototype.toExponential(fractionDigits)';
var cnIsRangeError = 'instanceof RangeError';
var cnNotRangeError = 'NOT instanceof RangeError';
var cnNoErrorCaught = 'NO ERROR CAUGHT...';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];
var testNum = 77.1234;


status = 'Section A of test: no error intended!';
actual = testNum.toExponential(4);
expect = '7.7123e+1';
captureThis();

status = 'Section B of test: Infinity.toExponential() with out-of-range fractionDigits';
actual = Number.POSITIVE_INFINITY.toExponential(-3);
expect = 'Infinity';
captureThis();

status = 'Section C of test: -Infinity.toExponential() with out-of-range fractionDigits';
actual = Number.NEGATIVE_INFINITY.toExponential(-3);
expect = '-Infinity';
captureThis();

status = 'Section D of test: NaN.toExponential() with out-of-range fractionDigits';
actual = Number.NaN.toExponential(-3);
expect = 'NaN';
captureThis();


///////////////////////////    OOPS....    ///////////////////////////////
/*************************************************************************
* 15.7.4.6 Number.prototype.toExponential(fractionDigits)
*
* An implementation is permitted to extend the behaviour of toExponential
* for values of fractionDigits less than 0 or greater than 20. In this
* case toExponential would not necessarily throw RangeError for such values.

status = 'Section B of test: expect RangeError because fractionDigits < 0';
actual = catchError('testNum.toExponential(-4)');
expect = cnIsRangeError;
captureThis();

status = 'Section C of test: expect RangeError because fractionDigits > 20 ';
actual = catchError('testNum.toExponential(21)');
expect = cnIsRangeError;
captureThis();
*************************************************************************/



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

for (var i = 0; i < UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//exitFunc ('test');
//}


function catchError(sEval)
{
try {eval(sEval);}
catch(e) {return isRangeError(e);}
return cnNoErrorCaught;
}


function isRangeError(obj)
{
if (obj instanceof RangeError)
return cnIsRangeError;
return cnNotRangeError;
}

},

/*
* Date: 2001-07-15
*
* SUMMARY: Testing Number.prototype.toPrecision(precision)
* See EMCA 262 Edition 3 Section 15.7.4.7
*
* Also see http://bugzilla.mozilla.org/show_bug.cgi?id=90551
*
*/
//-----------------------------------------------------------------------------
test_15_7_4_7__1: function() {
//var gTestfile = '15.7.4.7-1.js';
var UBound = 0;
//var BUGNUMBER = '(none)';
var summary = 'Testing Number.prototype.toPrecision(precision)';
var cnIsRangeError = 'instanceof RangeError';
var cnNotRangeError = 'NOT instanceof RangeError';
var cnNoErrorCaught = 'NO ERROR CAUGHT...';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];
var testNum = 5.123456;


status = 'Section A of test: no error intended!';
actual = testNum.toPrecision(4);
expect = '5.123';
captureThis();

status = 'Section B of test: Infinity.toPrecision() with out-of-range fractionDigits';
actual = Number.POSITIVE_INFINITY.toPrecision(-3);
expect = 'Infinity';
captureThis();

status = 'Section C of test: -Infinity.toPrecision() with out-of-range fractionDigits';
actual = Number.NEGATIVE_INFINITY.toPrecision(-3);
expect = '-Infinity';
captureThis();

status = 'Section D of test: NaN.toPrecision() with out-of-range fractionDigits';
actual = Number.NaN.toPrecision(-3);
expect = 'NaN';
captureThis();


///////////////////////////    OOPS....    ///////////////////////////////
/*************************************************************************
* 15.7.4.7 Number.prototype.toPrecision(precision)
*
* An implementation is permitted to extend the behaviour of toPrecision
* for values of precision less than 1 or greater than 21. In this
* case toPrecision would not necessarily throw RangeError for such values.

status = 'Section B of test: expect RangeError because precision < 1';
actual = catchError('testNum.toPrecision(0)');
expect = cnIsRangeError;
captureThis();

status = 'Section C of test: expect RangeError because precision < 1';
actual = catchError('testNum.toPrecision(-4)');
expect = cnIsRangeError;
captureThis();

status = 'Section D of test: expect RangeError because precision > 21 ';
actual = catchError('testNum.toPrecision(22)');
expect = cnIsRangeError;
captureThis();
*************************************************************************/



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

for (var i = 0; i < UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//exitFunc ('test');
//}


function catchError(sEval)
{
try {eval(sEval);}
catch(e) {return isRangeError(e);}
return cnNoErrorCaught;
}


function isRangeError(obj)
{
if (obj instanceof RangeError)
return cnIsRangeError;
return cnNotRangeError;
}

},

//-----------------------------------------------------------------------------
test_15_7_4_7__2: function() {
//var BUGNUMBER = "411893";
var summary = "num.toPrecision(undefined) should equal num.toString()";
var actual, expect;

//printBugNumber(BUGNUMBER);
//printStatus(summary);

/**************
* BEGIN TEST *
**************/

var failed = false;

try
{
var prec = 3.3.toPrecision(undefined);
var str  = 3.3.toString();
if (prec !== str)
{
throw "not equal!  " +
"3.3.toPrecision(undefined) === '" + prec + "', " +
"3.3.toString() === '" + str + "'";
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

//-----------------------------------------------------------------------------
test_regress__442242__01: function() {
//var BUGNUMBER = 442242;
var summary = 'Do not assert: INT_FITS_IN_JSVAL(i)';
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

var i = 28800000;
-i;

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

}

})
.endType();




