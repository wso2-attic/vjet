vjo.ctype("dsf.jslang.feature.tests.Ecma3StringTests")
.inherits("dsf.jslang.feature.tests.BaseTestEcma3")
.protos({

constructs: function() {
this.base();
},

test_15_5_4_11: function() {
//var gTestfile = '15.5.4.11.js';
//var BUGNUMBER = 392378;
var summary = '15.5.4.11 - String.prototype.replace';
var rex, f, a, i;

this.reportCompare(
2,
String.prototype.replace.length,
"Section 1"
);

this.reportCompare(
"321",
String.prototype.replace.call(123, "123", "321"),
"Section 2"
);

this.reportCompare(
"ok",
"ok".replace(),
"Section 3"
);

this.reportCompare(
"undefined**",
"***".replace("*"),
"Section 4"
);

this.reportCompare(
"xnullz",
"xyz".replace("y", null),
"Section 5"
);

this.reportCompare(
"x123",
"xyz".replace("yz", 123),
"Section 6"
);

this.reportCompare(
"/x/g/x/g/x/g",
"xxx".replace(/x/g, /x/g),
"Section 7"
);

this.reportCompare(
"ok",
"undefined".replace(undefined, "ok"),
"Section 8"
);

this.reportCompare(
"ok",
"null".replace(null, "ok"),
"Section 9"
);

this.reportCompare(
"ok",
"123".replace(123, "ok"),
"Section 10"
);

this.reportCompare(
"xzyxyz",
"xyzxyz".replace("yz", "zy"),
"Section 11"
);

this.reportCompare(
"ok",
"(xyz)".replace("(xyz)", "ok"),
"Section 12"
);

this.reportCompare(
"*$&yzxyz",
"xyzxyz".replace("x", "*$$&"),
"Section 13"
);

this.reportCompare(
"xy*z*",
"xyz".replace("z", "*$&*"),
"Section 14"
);

this.reportCompare(
"xyxyzxyz",
"xyzxyzxyz".replace("zxy", "$`"),
"Section 15"
);

this.reportCompare(
"zxyzxyzzxyz",
"xyzxyz".replace("xy", "$'xyz"),
"Section 16"
);

this.reportCompare(
"$",
"xyzxyz".replace("xyzxyz", "$"),
"Section 17"
);

this.reportCompare(
"x$0$00xyz",
"xyzxyz".replace("yz", "$0$00"),
"Section 18"
);

// Result for $1/$01 .. $99 is implementation-defined if searchValue is no
// regular expression. $+ is a non-standard Mozilla extension.

this.reportCompare(
"$!$\"$-1$*$#$.$xyz$$",
"xyzxyz$$".replace("xyz", "$!$\"$-1$*$#$.$"),
"Section 19"
);

this.reportCompare(
"$$$&$$$&$&",
"$$$&".replace("$$", "$$$$$$&$&$$&"),
"Section 20"
);

this.reportCompare(
"yxx",
"xxx".replace(/x/, "y"),
"Section 21"
);

this.reportCompare(
"yyy",
"xxx".replace(/x/g, "y"),
"Section 22"
);

rex = /x/, rex.lastIndex = 1;
this.reportCompare(
"yxx1",
"xxx".replace(rex, "y") + rex.lastIndex,
"Section 23"
);

rex = /x/g, rex.lastIndex = 1;
this.reportCompare(
"yyy0",
"xxx".replace(rex, "y") + rex.lastIndex,
"Section 24"
);

rex = /y/, rex.lastIndex = 1;
this.reportCompare(
"xxx1",
"xxx".replace(rex, "y") + rex.lastIndex,
"Section 25"
);

rex = /y/g, rex.lastIndex = 1;
this.reportCompare(
"xxx0",
"xxx".replace(rex, "y") + rex.lastIndex,
"Section 26"
);

rex = /x?/, rex.lastIndex = 1;
this.reportCompare(
"(x)xx1",
"xxx".replace(rex, "($&)") + rex.lastIndex,
"Section 27"
);

rex = /x?/g, rex.lastIndex = 1;
this.reportCompare(
"(x)(x)(x)()0",
"xxx".replace(rex, "($&)") + rex.lastIndex,
"Section 28"
);

rex = /y?/, rex.lastIndex = 1;
this.reportCompare(
"()xxx1",
"xxx".replace(rex, "($&)") + rex.lastIndex,
"Section 29"
);

rex = /y?/g, rex.lastIndex = 1;
this.reportCompare(
"()x()x()x()0",
"xxx".replace(rex, "($&)") + rex.lastIndex,
"Section 30"
);

this.reportCompare(
"xy$0xy$zxy$zxyz$zxyz",
"xyzxyzxyz".replace(/zxy/, "$0$`$$$&$$$'$"),
"Section 31"
);

this.reportCompare(
"xy$0xy$zxy$zxyz$$0xyzxy$zxy$z$z",
"xyzxyzxyz".replace(/zxy/g, "$0$`$$$&$$$'$"),
"Section 32"
);

this.reportCompare(
"xyxyxyzxyxyxyz",
"xyzxyz".replace(/(((x)(y)()()))()()()(z)/g, "$01$2$3$04$5$6$7$8$09$10"),
"Section 33"
);

rex = RegExp(
"()()()()()()()()()()" +
"()()()()()()()()()()" +
"()()()()()()()()()()" +
"()()()()()()()()()()" +
"()()()()()()()()()()" +
"()()()()()()()()()()" +
"()()()()()()()()()()" +
"()()()()()()()()()()" +
"()()()()()()()()()()" +
"()()()()()()()()(y)");
this.reportCompare(
"x(y)z",
"xyz".replace(rex, "($99)"),
"Section 34"
);

rex = RegExp(
"()()()()()()()()()(x)" +
"()()()()()()()()()()" +
"()()()()()()()()()()" +
"()()()()()()()()()()" +
"()()()()()()()()()()" +
"()()()()()()()()()()" +
"()()()()()()()()()()" +
"()()()()()()()()()()" +
"()()()()()()()()()()" +
"()()()()()()()()()(y)");
this.reportCompare(
"(x0)z",
"xyz".replace(rex, "($100)"),
"Section 35"
);

this.reportCompare(
"xyz(XYZ)",
"xyzXYZ".replace(/XYZ/g, "($&)"),
"Section 36"
);

this.reportCompare(
"(xyz)(XYZ)",
"xyzXYZ".replace(/xYz/gi, "($&)"),
"Section 37"
);

this.reportCompare(
"xyz\rxyz\n",
"xyz\rxyz\n".replace(/xyz$/g, "($&)"),
"Section 38"
);

this.reportCompare(
"(xyz)\r(xyz)\n",
"xyz\rxyz\n".replace(/xyz$/gm, "($&)"),
"Section 39"
);

f = function () { return "failure" };

this.reportCompare(
"ok",
"ok".replace("x", f),
"Section 40"
);

this.reportCompare(
"ok",
"ok".replace(/(?=k)ok/, f),
"Section 41"
);

this.reportCompare(
"ok",
"ok".replace(/(?!)ok/, f),
"Section 42"
);

this.reportCompare(
"ok",
"ok".replace(/ok(?!$)/, f),
"Section 43"
);

f = function (sub, offs, str) {
return ["", sub, typeof sub, offs, typeof offs, str, typeof str, ""]
.join("|");
};

this.reportCompare(
"x|y|string|1|number|xyz|string|z",
"xyz".replace("y", f),
"Section 44"
);

this.reportCompare(
"x|(y)|string|1|number|x(y)z|string|z",
"x(y)z".replace("(y)", f),
"Section 45"
);

this.reportCompare(
"x|y*|string|1|number|xy*z|string|z",
"xy*z".replace("y*", f),
"Section 46"
);

this.reportCompare(
"12|3|string|2|number|12345|string|45",
String.prototype.replace.call(1.2345e4, 3, f),
"Section 47"
);

this.reportCompare(
"|x|string|0|number|xxx|string|xx",
"xxx".replace(/^x/g, f),
"Section 48"
);

this.reportCompare(
"xx|x|string|2|number|xxx|string|",
"xxx".replace(/x$/g, f),
"Section 49"
);

f = function (sub, paren, offs, str) {
return ["", sub, typeof sub, paren, typeof paren, offs, typeof offs,
str, typeof str, ""].join("|");
};

this.reportCompare(
"xy|z|string|z|string|2|number|xyz|string|",
"xyz".replace(/(z)/g, f),
"Section 50"
);

this.reportCompare(
"xyz||string||string|3|number|xyz|string|",
"xyz".replace(/($)/g, f),
"Section 51"
);

this.reportCompare(
"|xy|string|y|string|0|number|xyz|string|z",
"xyz".replace(/(?:x)(y)/g, f),
"Section 52"
);

this.reportCompare(
"|x|string|x|string|0|number|xyz|string|yz",
"xyz".replace(/((?=xy)x)/g, f),
"Section 53"
);

this.reportCompare(
"|x|string|x|string|0|number|xyz|string|yz",
"xyz".replace(/(x(?=y))/g, f),
"Section 54"
);

this.reportCompare(
"x|y|string|y|string|1|number|xyz|string|z",
"xyz".replace(/((?!x)y)/g, f),
"Section 55"
);

this.reportCompare(
"|x|string|x|string|0|number|xyz|string|" +
"|y|string||undefined|1|number|xyz|string|z",
"xyz".replace(/y|(x)/g, f),
"Section 56"
);

this.reportCompare(
"xy|z|string||string|2|number|xyz|string|",
"xyz".replace(/(z?)z/, f),
"Section 57"
);

this.reportCompare(
"xy|z|string||undefined|2|number|xyz|string|",
"xyz".replace(/(z)?z/, f),
"Section 58"
);

this.reportCompare(
"xy|z|string||undefined|2|number|xyz|string|",
"xyz".replace(/(z)?\1z/, f),
"Section 59"
);

this.reportCompare(
"xy|z|string||undefined|2|number|xyz|string|",
"xyz".replace(/\1(z)?z/, f),
"Section 60"
);

this.reportCompare(
"xy|z|string||string|2|number|xyz|string|",
"xyz".replace(/(z?\1)z/, f),
"Section 61"
);

f = function (sub, paren1, paren2, offs, str) {
return ["", sub, typeof sub, paren1, typeof paren1, paren2, typeof paren2,
offs, typeof offs, str, typeof str, ""].join("|");
};

this.reportCompare(
"x|y|string|y|string||undefined|1|number|xyz|string|z",
"xyz".replace(/(y)(\1)?/, f),
"Section 62"
);

this.reportCompare(
"x|yy|string|y|string|y|string|1|number|xyyz|string|z",
"xyyz".replace(/(y)(\1)?/g, f),
"Section 63"
);

this.reportCompare(
"x|y|string|y|string||undefined|1|number|xyyz|string|" +
"|y|string|y|string||undefined|2|number|xyyz|string|z",
"xyyz".replace(/(y)(\1)??/g, f),
"Section 64"
);

this.reportCompare(
"x|y|string|y|string|y|string|1|number|xyz|string|z",
"xyz".replace(/(?=(y))(\1)?/, f),
"Section 65"
);

this.reportCompare(
"xyy|z|string||undefined||string|3|number|xyyz|string|",
"xyyz".replace(/(?!(y)y)(\1)z/, f),
"Section 66"
);

rex = RegExp(
"()()()()()()()()()()" +
"()()()()()()()()()()" +
"()()()()()()()()()()" +
"()()()()()()()()()()" +
"()()()()()()()()()()" +
"()()()()()()()()()()" +
"()()()()()()()()()()" +
"()()()()()()()()()()" +
"()()()()()()()()()()" +
"()()()()()()()()()()(z)?(y)");
a = ["sub"];
for (i = 1; i <= 102; ++i)
a[i] = "p" + i;
a[103] = "offs";
a[104] = "str";
a[105] = "return ['', sub, typeof sub, offs, typeof offs, str, typeof str, " +
"p100, typeof p100, p101, typeof p101, p102, typeof p102, ''].join('|');";
f = Function.apply(null, a);
this.reportCompare(
"x|y|string|1|number|xyz|string||string||undefined|y|string|z",
"xyz".replace(rex, f),
"Section 67"
);

this.reportCompare(
"undefined",
"".replace(/.*/g, function () {}),
"Section 68"
);

this.reportCompare(
"nullxnullynullznull",
"xyz".replace(/.??/g, function () { return null; }),
"Section 69"
);

this.reportCompare(
"111",
"xyz".replace(/./g, function () { return 1; }),
"Section 70"
);

},

//-----------------------------------------------------------------------------
test_15_5_4_14: function() {
//var BUGNUMBER = 287630;
var summary = '15.5.4.14 - String.prototype.split(/()/)';
var actual = '';
var expect = ['a'].toString();

//printBugNumber(BUGNUMBER);
//printStatus (summary);

actual = 'a'.split(/()/).toString();

this.reportCompare(expect, actual, summary);

},

/*
* Date: 12 October 2001
*
* SUMMARY: Regression test for string.replace bug 104375
* See http://bugzilla.mozilla.org/show_bug.cgi?id=104375
*/
//-----------------------------------------------------------------------------
test_regress__104375: function() {
//var gTestfile = 'regress-104375.js';
var UBound = 0;
//var BUGNUMBER = 104375;
var summary = 'Testing string.replace() with backreferences';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


/*
* Use the regexp to replace 'uid=31' with 'uid=15'
*
* In the second parameter of string.replace() method,
* "$1" refers to the first backreference: 'uid='
*/
var str = 'uid=31';
var re = /(uid=)(\d+)/;

// try the numeric literal 15
status = inSection(1);
actual  = str.replace (re, "$1" + 15);
expect = 'uid=15';
addThis();

// try the string literal '15'
status = inSection(2);
actual  = str.replace (re, "$1" + '15');
expect = 'uid=15';
addThis();

// try a letter before the '15'
status = inSection(3);
actual  = str.replace (re, "$1" + 'A15');
expect = 'uid=A15';
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
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

for (var i=0; i<UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//exitFunc ('test');
//}

},

/*
*
* Date:    21 January 2003
* SUMMARY: Regression test for bug 189898
* See http://bugzilla.mozilla.org/show_bug.cgi?id=189898
*
*/
//-----------------------------------------------------------------------------
test_regress__189898: function() {
//var gTestfile = 'regress-189898.js';
var UBound = 0;
//var BUGNUMBER = 189898;
var summary = 'Regression test for bug 189898';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


status = inSection(1);
actual = 'XaXY'.replace('XY', '--')
expect = 'Xa--';
addThis();

status = inSection(2);
actual = '$a$^'.replace('$^', '--')
expect = '$a--';
addThis();

status = inSection(3);
actual = 'ababc'.replace('abc', '--')
expect = 'ab--';
addThis();

status = inSection(4);
actual = 'ababc'.replace('abc', '^$')
expect = 'ab^$';
addThis();



/*
* Same as above, but providing a regexp in the first parameter
* to String.prototype.replace() instead of a string.
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=83293
* for subtleties on this issue -
*/
status = inSection(5);
actual = 'XaXY'.replace(/XY/, '--')
expect = 'Xa--';
addThis();

status = inSection(6);
actual = 'XaXY'.replace(/XY/g, '--')
expect = 'Xa--';
addThis();

status = inSection(7);
actual = '$a$^'.replace(/\$\^/, '--')
expect = '$a--';
addThis();

status = inSection(8);
actual = '$a$^'.replace(/\$\^/g, '--')
expect = '$a--';
addThis();

status = inSection(9);
actual = 'ababc'.replace(/abc/, '--')
expect = 'ab--';
addThis();

status = inSection(10);
actual = 'ababc'.replace(/abc/g, '--')
expect = 'ab--';
addThis();

status = inSection(11);
actual = 'ababc'.replace(/abc/, '^$')
expect = 'ab^$';
addThis();

status = inSection(12);
actual = 'ababc'.replace(/abc/g, '^$')
expect = 'ab^$';
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

},

//-----------------------------------------------------------------------------
test_regress__304376: function() {
//var BUGNUMBER = 304376;
var summary = 'String.prototype should be readonly and permanent';
var actual = '';
var expect = '';
//printBugNumber(BUGNUMBER);
//printStatus (summary);

expect = 'TypeError';

var saveString = String;

try
{
// see if we can crash...
"".join();
actual = 'No Error';
}
catch(ex)
{
actual = ex.name;
//printStatus(ex + '');
}

this.reportCompare(expect, actual, summary);

},

//-----------------------------------------------------------------------------
test_regress__313567: function() {
//var BUGNUMBER = 313567;
var summary = 'String.prototype.length should not be generic';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var s = new String("1");
s.toString = function() {
return "22";
}
var expect = 1;
var actual = s.length;
//printStatus("expect="+expect+" actual="+actual);

this.reportCompare(expect, actual, summary);

},

//-----------------------------------------------------------------------------
test_regress__392378: function() {
//var BUGNUMBER = 392378;
var summary = 'Regular Expression Non-participating Capture Groups are inaccurate in edge cases';
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

expect = ["", undefined, ""] + '';
actual = "y".split(/(x)?\1y/) + '';
this.reportCompare(expect, actual, summary + ': "y".split(/(x)?\1y/)');

expect = ["", undefined, ""] + '';
actual = "y".split(/(x)?y/) + '';
this.reportCompare(expect, actual, summary + ': "y".split(/(x)?y/)');

expect = 'undefined';
actual = "y".replace(/(x)?\1y/, function($0, $1){ return String($1); }) + '';
this.reportCompare(expect, actual, summary + ': "y".replace(/(x)?\\1y/, function($0, $1){ return String($1); })');

expect = 'undefined';
actual = "y".replace(/(x)?y/, function($0, $1){ return String($1); }) + '';
this.reportCompare(expect, actual, summary + ': "y".replace(/(x)?y/, function($0, $1){ return String($1); })');

expect = 'undefined';
actual = "y".replace(/(x)?y/, function($0, $1){ return $1; }) + '';
this.reportCompare(expect, actual, summary + ': "y".replace(/(x)?y/, function($0, $1){ return $1; })');

//exitFunc ('test');
//}

},

/*
* Creation Date:   30 May 2001
* Correction Date: 14 Aug 2001
*
* SUMMARY:  Regression test for bugs 83293, 103351
* See http://bugzilla.mozilla.org/show_bug.cgi?id=83293
*     http://bugzilla.mozilla.org/show_bug.cgi?id=103351
*     http://bugzilla.mozilla.org/show_bug.cgi?id=92942
*
*
* ********************   CORRECTION !!!  *****************************
*
* When I originally wrote this test, I thought this was true:
* str.replace(strA, strB) == str.replace(new RegExp(strA),strB).
* See ECMA-262 Final Draft, 15.5.4.11 String.prototype.replace
*
* However, in http://bugzilla.mozilla.org/show_bug.cgi?id=83293
* Jim Ley points out the ECMA-262 Final Edition changed on this.
* String.prototype.replace (searchValue, replaceValue), if provided
* a searchValue that is not a RegExp, is NO LONGER to replace it with
*
*                  new RegExp(searchValue)
* but rather:
*                  String(searchValue)
*
* This puts the replace() method at variance with search() and match(),
* which continue to follow the RegExp conversion of the Final Draft.
* It also makes most of this testcase, as originally written, invalid.
**********************************************************************
*/

//-----------------------------------------------------------------------------
test_regress__83293: function() {
//var BUGNUMBER = 103351; // <--- (Outgrowth of original bug 83293)
var summ_OLD = 'Testing str.replace(strA, strB) == str.replace(new RegExp(strA),strB)';
var summ_NEW = 'Testing String.prototype.replace(x,y) when x is a string';
var summary = summ_NEW;
var status = '';
var actual = '';
var expect= '';
var cnEmptyString = '';
var str = 'abc';
var strA = cnEmptyString;
var strB = 'Z';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------


/*
* In this test, it's important to reportCompare() each other case
* BEFORE the last two cases are attempted. Don't store all results
* in an array and reportCompare() them at the end, as we usually do.
*
* When this bug was filed, str.replace(strA, strB) would return no value
* whatsoever if strA == cnEmptyString, and no error, either -
*/
//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

/*******************  THESE WERE INCORRECT; SEE ABOVE  ************************
status = 'Section A of test';
strA = 'a';
actual = str.replace(strA, strB);
expect = str.replace(new RegExp(strA), strB);
reportCompare(expect, actual, status);

status = 'Section B of test';
strA = 'x';
actual = str.replace(strA, strB);
expect = str.replace(new RegExp(strA), strB);
reportCompare(expect, actual, status);

status = 'Section C of test';
strA = undefined;
actual = str.replace(strA, strB);
expect = str.replace(new RegExp(strA), strB);
reportCompare(expect, actual, status);

status = 'Section D of test';
strA = null;
actual = str.replace(strA, strB);
expect = str.replace(new RegExp(strA), strB);
reportCompare(expect, actual, status);


* This example is from jim@jibbering.com (see Bugzilla bug 92942)
* It is a variation on the example below.
*
* Namely, we are using the regexp /$/ instead of the regexp //.
* The regexp /$/ means we should match the "empty string" at the
* end-boundary of the word, instead of the one at the beginning.
*
status = 'Section E of test';
var strJim = 'aa$aa';
strA = '$';
actual = strJim.replace(strA, strB);             // bug -> 'aaZaa'
expect = strJim.replace(new RegExp(strA), strB); // expect 'aa$aaZ'
reportCompare(expect, actual, status);


*
* Note: 'Zabc' is the result we expect for 'abc'.replace('', 'Z').
*
* The string '' is supposed to be equivalent to new RegExp('') = //.
* The regexp // means we should match the "empty string" conceived of
* at the beginning boundary of the word, before the first character.
*
status = 'Section F of test';
strA = cnEmptyString;
actual = str.replace(strA, strB);
expect = 'Zabc';
reportCompare(expect, actual, status);

status = 'Section G of test';
strA = cnEmptyString;
actual = str.replace(strA, strB);
expect = str.replace(new RegExp(strA), strB);
reportCompare(expect, actual, status);

*************************  END OF INCORRECT CASES ****************************/


//////////////////////////  OK, LET'S START OVER //////////////////////////////

status = 'Section 1 of test';
actual = 'abc'.replace('a', 'Z');
expect = 'Zbc';
this.reportCompare(expect, actual, status);

status = 'Section 2 of test';
actual = 'abc'.replace('b', 'Z');
expect = 'aZc';
this.reportCompare(expect, actual, status);

status = 'Section 3 of test';
actual = 'abc'.replace(undefined, 'Z');
expect = 'abc'; // String(undefined) == 'undefined'; no replacement possible
this.reportCompare(expect, actual, status);

status = 'Section 4 of test';
actual = 'abc'.replace(null, 'Z');
expect = 'abc'; // String(null) == 'null'; no replacement possible
this.reportCompare(expect, actual, status);

status = 'Section 5 of test';
actual = 'abc'.replace(true, 'Z');
expect = 'abc'; // String(true) == 'true'; no replacement possible
this.reportCompare(expect, actual, status);

status = 'Section 6 of test';
actual = 'abc'.replace(false, 'Z');
expect = 'abc'; // String(false) == 'false'; no replacement possible
this.reportCompare(expect, actual, status);

status = 'Section 7 of test';
actual = 'aa$aa'.replace('$', 'Z');
expect = 'aaZaa'; // NOT 'aa$aaZ' as in ECMA Final Draft; see above
this.reportCompare(expect, actual, status);

status = 'Section 8 of test';
actual = 'abc'.replace('.*', 'Z');
expect = 'abc';  // not 'Z' as in EMCA Final Draft
this.reportCompare(expect, actual, status);

status = 'Section 9 of test';
actual = 'abc'.replace('', 'Z');
expect = 'Zabc';  // Still expect 'Zabc' for this
this.reportCompare(expect, actual, status);

//exitFunc ('test');
//}

}

})
.endType();

