vjo.ctype("dsf.jslang.feature.tests.Ecma3UnicodeTests")
.inherits("com.ebay.dsf.jslang.feature.tests.BaseTestEcma3")
.protos({

constructs: function() {
this.base();
},

//-----------------------------------------------------------------------------
test_regress__352044__01: function() {
//var BUGNUMBER = 352044;
var summary = 'issues with Unicode escape sequences in JavaScript source code';
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

expect = 'SyntaxError: illegal character';

try
{
var i = 1;
eval('i \\u002b= 1');
print(i);
}
catch(ex)
{
actual = ex + '';
}

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

//-----------------------------------------------------------------------------
test_regress__352044__01__n: function() {
//var BUGNUMBER = 352044;
var summary = 'issues with Unicode escape sequences in JavaScript source code';
var actual = 'No Error';
var expect = 'SyntaxError';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

//print('This test case is expected to throw an uncaught SyntaxError');

try
{
var i = 1;
i \u002b= 1;
print(i);
}
catch(ex)
{
actual = ex + '';
}

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

test_uc__001__n: function() {

//var gTestfile = 'uc-001-n.js';

//test();

//function test()
//{
//enterFunc ("test");

//printStatus ("Unicode Characters 1C-1F negative test.");
//printBugNumber (23612);

this.reportCompare ("error", eval ("'no'\u001C+' error'"),
"Unicode whitespace test (1C.)");
this.reportCompare ("error", eval ("'no'\u001D+' error'"),
"Unicode whitespace test (1D.)");
this.reportCompare ("error", eval ("'no'\u001E+' error'"),
"Unicode whitespace test (1E.)");
this.reportCompare ("error", eval ("'no'\u001F+' error'"),
"Unicode whitespace test (1F.)");

//exitFunc ("test");
//}

},

test__uc__002__n: function() {
//var gTestfile = 'uc-002-n.js';

DESCRIPTION = "Non-character escapes in identifiers negative test.";
EXPECTED = "error";

//enterFunc ("test");

//printStatus ("Non-character escapes in identifiers negative test.");
//printBugNumber (23607);

eval("\u0020 = 5");
this.reportCompare('PASS', 'FAIL', "Previous statement should have thrown an error.");

//exitFunc ("test");

},

test_uc__002: function() {
//var gTestfile = 'uc-002.js';

//test();

//function test()
//{
//enterFunc ("test");

//printStatus ("Unicode non-breaking space character test.");
//printBugNumber (23613);

this.reportCompare ("no error", eval("'no'\u00A0+ ' error'"),
"Unicode non-breaking space character test.");

var str = "\u00A0foo";
this.reportCompare (0, str.search(/^\sfoo$/),
"Unicode non-breaking space character regexp test.");

//exitFunc ("test");
//}

},

test_uc__003: function() {

//var gTestfile = 'uc-003.js';

//test();

//function test()
//{
//enterFunc ("test");

var \u0041 = 5;
var A\u03B2 = 15;
var c\u0061se = 25;

//printStatus ("Escapes in identifiers test.");
//printBugNumber (23608);
//printBugNumber (23607);

this.reportCompare (5, eval("\u0041"),
"Escaped ASCII Identifier test.");
this.reportCompare (6, eval("++\u0041"),
"Escaped ASCII Identifier test");
this.reportCompare (15, eval("A\u03B2"),
"Escaped non-ASCII Identifier test");
this.reportCompare (16, eval("++A\u03B2"),
"Escaped non-ASCII Identifier test");
this.reportCompare (25, eval("c\\u00" + "61se"),
"Escaped keyword Identifier test");
this.reportCompare (26, eval("++c\\u00" + "61se"),
"Escaped keyword Identifier test");

//exitFunc ("test");
//}

},

test_uc__004: function() {

//var gTestfile = 'uc-004.js';

//test();

//function test()
//{
//enterFunc ("test");

//printStatus ("Unicode Characters 1C-1F with regexps test.");
//printBugNumber (23612);

var ary = ["\u001Cfoo", "\u001Dfoo", "\u001Efoo", "\u001Ffoo"];

for (var i in ary)
{
this.reportCompare (0, ary[Number(i)].search(/^\Sfoo$/),
"Unicode characters 1C-1F in regexps, ary[" +
i + "] did not match \\S test (it should not.)");
this.reportCompare (-1, ary[Number(i)].search(/^\sfoo$/),
"Unicode characters 1C-1F in regexps, ary[" +
i + "] matched \\s test (it should not.)");
}

//exitFunc ("test");
//}

},

/*
*
* Date:    15 July 2002
* SUMMARY: Testing identifiers with double-byte names
* See http://bugzilla.mozilla.org/show_bug.cgi?id=58274
*
* Here is a sample of the problem:
*
*    js> function f\u02B1 () {}
*
*    js> f\u02B1.toSource();
*    function f�() {}
*
*    js> f\u02B1.toSource().toSource();
*    (new String("function f\xB1() {}"))
*
*
* See how the high-byte information (the 02) has been lost?
* The same thing was happening with the toString() method:
*
*    js> f\u02B1.toString();
*
*    function f�() {
*    }
*
*    js> f\u02B1.toString().toSource();
*    (new String("\nfunction f\xB1() {\n}\n"))
*
*/
//-----------------------------------------------------------------------------
test_uc__005: function() {
//var gTestfile = 'uc-005.js';
var UBound = 0;
//var BUGNUMBER = 58274;
var summary = 'Testing identifiers with double-byte names';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


/*
* Define a function that uses double-byte identifiers in
* "every possible way"
*
* Then recover each double-byte identifier via f.toString().
* To make this easier, put a 'Z' token before every one.
*
* Our eval string will be:
*
* sEval = "function Z\u02b1(Z\u02b2, b) {
*          try { Z\u02b3 : var Z\u02b4 = Z\u02b1; }
*          catch (Z\u02b5) { for (var Z\u02b6 in Z\u02b5)
*          {for (1; 1<0; Z\u02b7++) {new Array()[Z\u02b6] = 1;} };} }";
*
* It will be helpful to build this string in stages:
*/
var s0 =  'function Z';
var s1 =  '\u02b1(Z';
var s2 =  '\u02b2, b) {try { Z';
var s3 =  '\u02b3 : var Z';
var s4 =  '\u02b4 = Z';
var s5 =  '\u02b1; } catch (Z'
var s6 =  '\u02b5) { for (var Z';
var s7 =  '\u02b6 in Z';
var s8 =  '\u02b5){for (1; 1<0; Z';
var s9 =  '\u02b7++) {new Array()[Z';
var s10 = '\u02b6] = 1;} };} }';


/*
* Concatenate these and eval() to create the function Z\u02b1
*/
var sEval = s0 + s1 + s2 + s3 + s4 + s5 + s6 + s7 + s8 + s9 + s10;
eval(sEval);


/*
* Recover all the double-byte identifiers via Z\u02b1.toString().
* We'll recover the 1st one as arrID[1], the 2nd one as arrID[2],
* and so on ...
*/
var arrID = getIdentifiers(Z\u02b1);


/*
* Now check that we got back what we put in -
*/
status = inSection(1);
actual = arrID[1];
expect = s1.charAt(0);
addThis();

status = inSection(2);
actual = arrID[2];
expect = s2.charAt(0);
addThis();

status = inSection(3);
actual = arrID[3];
expect = s3.charAt(0);
addThis();

status = inSection(4);
actual = arrID[4];
expect = s4.charAt(0);
addThis();

status = inSection(5);
actual = arrID[5];
expect = s5.charAt(0);
addThis();

status = inSection(6);
actual = arrID[6];
expect = s6.charAt(0);
addThis();

status = inSection(7);
actual = arrID[7];
expect = s7.charAt(0);
addThis();

status = inSection(8);
actual = arrID[8];
expect = s8.charAt(0);
addThis();

status = inSection(9);
actual = arrID[9];
expect = s9.charAt(0);
addThis();

status = inSection(10);
actual = arrID[10];
expect = s10.charAt(0);
addThis();




//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------



/*
* Goal: recover the double-byte identifiers from f.toString()
* by getting the very next character after each 'Z' token.
*
* The return value will be an array |arr| indexed such that
* |arr[1]| is the 1st identifier, |arr[2]| the 2nd, and so on.
*
* Note, however, f.toString() is implementation-independent.
* For example, it may begin with '\nfunction' instead of 'function'.
*
* Rhino uses a Unicode representation for f.toString(); whereas
* SpiderMonkey uses an ASCII representation, putting escape sequences
* for non-ASCII characters. For example, if a function is called f\u02B1,
* then in Rhino the toString() method will present a 2-character Unicode
* string for its name, whereas SpiderMonkey will present a 7-character
* ASCII string for its name: the string literal 'f\u02B1'.
*
* So we force the lexer to condense the string before we use it.
* This will give uniform results in Rhino and SpiderMonkey.
*/
function getIdentifiers(f)
{
var str = condenseStr(f.toString());
var arr = str.split('Z');

/*
* The identifiers are the 1st char of each split substring
* EXCEPT the first one, which is just ('\n' +) 'function '.
*
* Thus note the 1st identifier will be stored in |arr[1]|,
* the 2nd one in |arr[2]|, etc., making the indexing easy -
*/
for (i in arr)
arr[i] = arr[i].charAt(0);
return arr;
}


/*
* This function is the opposite of a functions like escape(), which take
* Unicode characters and return escape sequences for them. Here, we force
* the lexer to turn escape sequences back into single characters.
*
* Note we can't simply do |eval(str)|, since in practice |str| will be an
* identifier somewhere in the program (e.g. a function name); thus |eval(str)|
* would return the object that the identifier represents: not what we want.
*
* So we surround |str| lexicographically with quotes to force the lexer to
* evaluate it as a string. Have to strip out any linefeeds first, however -
*/
function condenseStr(str)
{
/*
* You won't be able to do the next step if |str| has
* any carriage returns or linefeeds in it. For example:
*
*  js> eval("'" + '\nHello' + "'");
*  1: SyntaxError: unterminated string literal:
*  1: '
*  1: ^
*
* So replace them with the empty string -
*/
str = str.replace(/[\r\n]/g, '')
return eval("'" + str + "'")
}

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

}


})
.endType();

