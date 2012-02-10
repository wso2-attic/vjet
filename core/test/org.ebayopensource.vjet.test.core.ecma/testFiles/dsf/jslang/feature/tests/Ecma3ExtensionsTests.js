vjo.ctype("dsf.jslang.feature.tests.Ecma3ExtensionsTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({
reportCompare :  function (expectedValue, actualValue, statusItems) {
new dsf.jslang.feature.tests.BaseTest().TestCase( ' ',  statusItems, expectedValue,  actualValue);
},

compareSource : function(expectedValue, actualValue, statusItems) {
new dsf.jslang.feature.tests.BaseTest().TestCase( ' ',  statusItems, expectedValue,  actualValue);
},

uneval : function(obj){
},
quit : function(){
},

inSection :function (x) {
var SECT_PREFIX = 'Section ';
var SECT_SUFFIX = ' of test - ';
return SECT_PREFIX + x + SECT_SUFFIX;
},
//-----------------------------------------------------------------------------
test_10_1_3__2:function(){

var gTestfile = '10.1.3-2.js';
var UBound = 0;
var BUGNUMBER = '(none)';
var summary = 'Testing functions having duplicate formal parameter names';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];
var OBJ = new Object();
var OBJ_TYPE = OBJ.toString();

/*
* Exit if the implementation doesn't support toSource() or uneval(),
* since these are non-ECMA extensions to the language -
*/
try
{
if (!OBJ.toSource || !this.uneval(OBJ))
this.quit();
}
catch(e)
{
this.quit();
}


/*
* OK, now begin the test. Just checking that we don't crash on these -
*/
function f1(x1,x2,x3,x4)
{
var ret = eval(arguments.toSource());
return ret.toString();
}
status = this.inSection(1);
actual = f1(1,2,3,4);
expect = OBJ_TYPE;
addThis();


/*
* Same thing, but preface |arguments| with the function name
*/
function f2(x1,x2,x3,x4)
{
var ret = eval(f2.arguments.toSource());
return ret.toString();
}
status = this.inSection(2);
actual = f2(1,2,3,4);
expect = OBJ_TYPE;
addThis();


function f3(x1,x2,x3,x4)
{
var ret = eval(this.uneval(arguments));
return ret.toString();
}
status = this.inSection(3);
actual = f3(1,2,3,4);
expect = OBJ_TYPE;
addThis();


/*
* Same thing, but preface |arguments| with the function name
*/
function f4(x1,x2,x3,x4)
{
var ret = eval(this.uneval(f4.arguments));
return ret.toString();
}
status = this.inSection(4);
actual = f4(1,2,3,4);
expect = OBJ_TYPE;
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
test_7_9_1:function(){

var BUGNUMBER = 402386;
var summary = 'Automatic Semicolon insertion in restricted statements';
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

var code;

code = '(function() { label1: for (;;) { continue \n label1; }})';
expect ='(function () {label1:for (; ; ) {continue;label1;}})';
actual = this.uneval(eval(code));
this.compareSource(expect, actual, summary + ': ' + code);

code = '(function() { label2: for (;;) { break \n label2; }})';
expect ='(function () {label2:for (; ; ) {break;label2;}})';
actual = this.uneval(eval(code));
this.compareSource(expect, actual, summary + ': ' + code);

code = '(function() { return \n x++; })';
expect = 'Automatic Semicolon insertion in restricted statements: (function() { return   x++; })';
actual = this.uneval(eval(code));
this.compareSource(expect, actual, summary + ': ' + code);

//  print('see bug 256617');
code = '(function() { throw \n x++; })';
//  expect = '(function() { throw ; x++; })';
expect = 'SyntaxError: Line terminator is not allowed between the throw keyword and throw expression.';
try { this.uneval(eval(code)); } catch(ex) { actual = ex + ''; };
//  this.compareSource(expect, actual, summary + ': ' + code);
this.reportCompare(expect, actual, summary + ': ' + code);

//   exitFunc ('test');
}


},
//-----------------------------------------------------------------------------
test_regress__103087:function(){

var gTestfile = 'regress-103087.js';
var UBound = 0;
var BUGNUMBER = 103087;
var summary = "Testing that we don't crash on any of these regexps -";
var re = '';
var lm = '';
var lc = '';
var rc = '';


// the regexps are built in pieces  -
var NameStrt = "[A-Za-z_:]|[^\\x00-\\x7F]";
var NameChar = "[A-Za-z0-9_:.-]|[^\\x00-\\x7F]";
var Name = "(" + NameStrt + ")(" + NameChar + ")*";
var TextSE = "[^<]+";
var UntilHyphen = "[^-]*-";
var Until2Hyphens = UntilHyphen + "([^-]" + UntilHyphen + ")*-";
var CommentCE = Until2Hyphens + ">?";
var UntilRSBs = "[^]]*]([^]]+])*]+";
var CDATA_CE = UntilRSBs + "([^]>]" + UntilRSBs + ")*>";
var S = "[ \\n\\t\\r]+";
var QuoteSE = '"[^"]' + "*" + '"' + "|'[^']*'";
var DT_IdentSE = S + Name + "(" + S + "(" + Name + "|" + QuoteSE + "))*";
var MarkupDeclCE = "([^]\"'><]+|" + QuoteSE + ")*>";
var S1 = "[\\n\\r\\t ]";
var UntilQMs = "[^?]*\\?+";
var PI_Tail = "\\?>|" + S1 + UntilQMs + "([^>?]" + UntilQMs + ")*>";
var DT_ItemSE = "<(!(--" + Until2Hyphens + ">|[^-]" + MarkupDeclCE + ")|\\?" + Name + "(" + PI_Tail + "))|%" + Name + ";|" + S;
var DocTypeCE = DT_IdentSE + "(" + S + ")?(\\[(" + DT_ItemSE + ")*](" + S + ")?)?>?";
var DeclCE = "--(" + CommentCE + ")?|\\[CDATA\\[(" + CDATA_CE + ")?|DOCTYPE(" + DocTypeCE + ")?";
var PI_CE = Name + "(" + PI_Tail + ")?";
var EndTagCE = Name + "(" + S + ")?>?";
var AttValSE = '"[^<"]' + "*" + '"' + "|'[^<']*'";
var ElemTagCE = Name + "(" + S + Name + "(" + S + ")?=(" + S + ")?(" + AttValSE + "))*(" + S + ")?/?>?";
var MarkupSPE = "<(!(" + DeclCE + ")?|\\?(" + PI_CE + ")?|/(" + EndTagCE + ")?|(" + ElemTagCE + ")?)";
var XML_SPE = TextSE + "|" + MarkupSPE;
var CommentRE = "<!--" + Until2Hyphens + ">";
var CommentSPE = "<!--(" + CommentCE + ")?";
var PI_RE = "<\\?" + Name + "(" + PI_Tail + ")";
var Erroneous_PI_SE = "<\\?[^?]*(\\?[^>]+)*\\?>";
var PI_SPE = "<\\?(" + PI_CE + ")?";
var CDATA_RE = "<!\\[CDATA\\[" + CDATA_CE;
var CDATA_SPE = "<!\\[CDATA\\[(" + CDATA_CE + ")?";
var ElemTagSE = "<(" + NameStrt + ")([^<>\"']+|" + AttValSE + ")*>";
var ElemTagRE = "<" + Name + "(" + S + Name + "(" + S + ")?=(" + S + ")?(" + AttValSE + "))*(" + S + ")?/?>";
var ElemTagSPE = "<" + ElemTagCE;
var EndTagRE = "</" + Name + "(" + S + ")?>";
var EndTagSPE = "</(" + EndTagCE + ")?";
var DocTypeSPE = "<!DOCTYPE(" + DocTypeCE + ")?";
var PERef_APE = "%(" + Name + ";?)?";
var HexPart = "x([0-9a-fA-F]+;?)?";
var NumPart = "#([0-9]+;?|" + HexPart + ")?";
var CGRef_APE = "&(" + Name + ";?|" + NumPart + ")?";
var Text_PE = CGRef_APE + "|[^&]+";
var EntityValue_PE = CGRef_APE + "|" + PERef_APE + "|[^%&]+";


var rePatterns = new Array(AttValSE, CDATA_CE, CDATA_RE, CDATA_SPE, CGRef_APE, CommentCE, CommentRE, CommentSPE, DT_IdentSE, DT_ItemSE, DeclCE, DocTypeCE, DocTypeSPE, ElemTagCE, ElemTagRE, ElemTagSE, ElemTagSPE, EndTagCE, EndTagRE, EndTagSPE, EntityValue_PE, Erroneous_PI_SE, HexPart, MarkupDeclCE, MarkupSPE, Name, NameChar, NameStrt, NumPart, PERef_APE, PI_CE, PI_RE, PI_SPE, PI_Tail, QuoteSE, S, S1, TextSE, Text_PE, Until2Hyphens, UntilHyphen, UntilQMs, UntilRSBs, XML_SPE);


// here's a big string to test the regexps on -
var str = '';
str += '<html xmlns="http://www.w3.org/1999/xhtml"' + '\n';
str += '      xmlns:xlink="http://www.w3.org/XML/XLink/0.9">' + '\n';
str += '  <head><title>Three Namespaces</title></head>' + '\n';
str += '  <body>' + '\n';
str += '    <h1 align="center">An Ellipse and a Rectangle</h1>' + '\n';
str += '    <svg xmlns="http://www.w3.org/Graphics/SVG/SVG-19991203.dtd" ' + '\n';
str += '         width="12cm" height="10cm">' + '\n';
str += '      <ellipse rx="110" ry="130" />' + '\n';
str += '      <rect x="4cm" y="1cm" width="3cm" height="6cm" />' + '\n';
str += '    </svg>' + '\n';
str += '    <p xlink:type="simple" xlink:href="ellipses.html">' + '\n';
str += '      More about ellipses' + '\n';
str += '    </p>' + '\n';
str += '    <p xlink:type="simple" xlink:href="rectangles.html">' + '\n';
str += '      More about rectangles' + '\n';
str += '    </p>' + '\n';
str += '    <hr/>' + '\n';
str += '    <p>Last Modified February 13, 2000</p>    ' + '\n';
str += '  </body>' + '\n';
str += '</html>';



//-----------------------------------------------------------------------------
test();
//-----------------------------------------------------------------------------



function test()
{
//   enterFunc ('test');
//   printBugNumber(BUGNUMBER);
//   printStatus (summary);

for (var i=0; i<rePatterns.length; i++)
{
status = this.inSection(i);
re = new RegExp(rePatterns[i]);

// Test that we don't crash on any of these -
re.exec(str);
getResults();

// Just for the heck of it, test the current leftContext
re.exec(lc);
getResults();

// Test the current rightContext
re.exec(rc);
getResults();
}

this.reportCompare('No Crash', 'No Crash', '');

//   exitFunc ('test');
}


function getResults()
{
lm = RegExp.lastMatch;
lc = RegExp.leftContext;
rc = RegExp.rightContext;
}

},
//-----------------------------------------------------------------------------
test_regress__188206__01:function(){

var gTestfile = 'regress-188206-01.js';
var UBound = 0;
var BUGNUMBER = 188206;
var summary = 'Invalid use of regexp quantifiers should generate SyntaxErrors';
var TEST_PASSED = 'SyntaxError';
var TEST_FAILED = 'Generated an error, but NOT a SyntaxError!';
var TEST_FAILED_BADLY = 'Did not generate ANY error!!!';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


/*
* Now do some weird things on the left side of the regexps -
*/
status = this.inSection(7);
testThis(' /*a/ ');

status = this.inSection(8);
testThis(' /**a/ ');


//-----------------------------------------------------------------------------
test();
//-----------------------------------------------------------------------------

/*
* Invalid syntax should generate a SyntaxError
*/
function testThis(sInvalidSyntax)
{
expect = TEST_PASSED;
actual = TEST_FAILED_BADLY;

try
{
eval(sInvalidSyntax);
}
catch(e1)
{
if (e1 instanceof SyntaxError)
actual = TEST_PASSED;
else
actual = TEST_FAILED;
}

statusitems[UBound] = status;
expectedvalues[UBound] = expect;
actualvalues[UBound] = actual;
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
test_regress__188206__02:function(){

var gTestfile = 'regress-188206-02.js';
var UBound = 0;
var BUGNUMBER = 188206;
var summary = 'Invalid use of regexp quantifiers should generate SyntaxErrors';
var CHECK_PASSED = 'Should not generate an error';
var CHECK_FAILED = 'Generated an error!';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


/*
* Misusing the {DecmalDigits} quantifier - according to ECMA,
* but not according to Perl.
*
* ECMA-262 Edition 3 prohibits the use of unescaped braces in
* regexp patterns, unless they form part of a quantifier.
*
* Hovever, Perl does not prohibit this. If not used as part
* of a quantifer, Perl treats braces literally.
*
* We decided to follow Perl on this for backward compatibility.
* See http://bugzilla.mozilla.org/show_bug.cgi?id=190685.
*
* Therefore NONE of the following ECMA violations should generate
* a SyntaxError. Note we use checkThis() instead of testThis().
*/
status = this.inSection(13);
checkThis(' /a*{/ ');

status = this.inSection(14);
checkThis(' /a{}/ ');

status = this.inSection(15);
checkThis(' /{a/ ');

status = this.inSection(16);
checkThis(' /}a/ ');

status = this.inSection(17);
checkThis(' /x{abc}/ ');

status = this.inSection(18);
checkThis(' /{{0}/ ');

status = this.inSection(19);
checkThis(' /{{1}/ ');

status = this.inSection(20);
checkThis(' /x{{0}/ ');

status = this.inSection(21);
checkThis(' /x{{1}/ ');

status = this.inSection(22);
checkThis(' /x{{0}}/ ');

status = this.inSection(23);
checkThis(' /x{{1}}/ ');

status = this.inSection(24);
checkThis(' /x{{0}}/ ');

status = this.inSection(25);
checkThis(' /x{{1}}/ ');

status = this.inSection(26);
checkThis(' /x{{0}}/ ');

status = this.inSection(27);
checkThis(' /x{{1}}/ ');


//-----------------------------------------------------------------------------
test();
//-----------------------------------------------------------------------------



/*
* Allowed syntax shouldn't generate any errors
*/
function checkThis(sAllowedSyntax)
{
expect = CHECK_PASSED;
actual = CHECK_PASSED;

try
{
eval(sAllowedSyntax);
}
catch(e2)
{
actual = CHECK_FAILED;
}

statusitems[UBound] = status;
expectedvalues[UBound] = expect;
actualvalues[UBound] = actual;
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
test_regress__220367__002:function(){

var gTestfile = 'regress-220367-002.js';
var UBound = 0;
var BUGNUMBER = 220367;
var summary = 'Regexp conformance test';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];

var re = /(a)|(b)/;

re.test('a');
status = this.inSection(1);
actual = RegExp.$1;
expect = 'a';
addThis();

status = this.inSection(2);
actual = RegExp.$2;
expect = '';
addThis();

re.test('b');
status = this.inSection(3);
actual = RegExp.$1;
expect = '';
addThis();

status = this.inSection(4);
actual = RegExp.$2;
expect = 'b';
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
test_regress__228087:function(){

var gTestfile = 'regress-228087.js';
var i = 0;
var BUGNUMBER = 228087;
var summary = 'Testing regexps with unescaped braces';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();
var e;


string = 'foo {1} foo {2} foo';

// try an example with the braces escaped
status = this.inSection(1);
try
{
pattern = new RegExp('\{1.*\}', 'g');
actualmatch = string.match(pattern);
}
catch (e3)
{
pattern = 'error';
actualmatch = '';
}
expectedmatch = Array('{1} foo {2}');
addThis();

// just like Section 1, without the braces being escaped
status = this.inSection(2);
try
{
pattern = new RegExp('{1.*}', 'g');
actualmatch = string.match(pattern);
}
catch (e4)
{
pattern = 'error';
actualmatch = '';
}
expectedmatch = Array('{1} foo {2}');
addThis();

// try an example with the braces escaped
status = this.inSection(3);
try
{
pattern = new RegExp('\{1[.!\}]*\}', 'g');
actualmatch = string.match(pattern);
}
catch (e5)
{
pattern = 'error';
actualmatch = '';
}
expectedmatch = Array('{1}');
addThis();

// just like Section 3, without the braces being escaped
status = this.inSection(4);
try
{
pattern = new RegExp('{1[.!}]*}', 'g');
actualmatch = string.match(pattern);
}
catch (e6)
{
pattern = 'error';
actualmatch = '';
}
expectedmatch = Array('{1}');
addThis();


string = 'abccccc{3 }c{ 3}c{3, }c{3 ,}c{3 ,4}c{3, 4}c{3,4 }de';

// use braces in a normal quantifier construct
status = this.inSection(5);
try
{
pattern = new RegExp('c{3}');
actualmatch = string.match(pattern);
}
catch (e7)
{
pattern = 'error';
actualmatch = '';
}
expectedmatch = Array('ccc');
addThis();

// now disrupt the quantifer - the braces should now be interpreted literally
status = this.inSection(6);
try
{
pattern = new RegExp('c{3 }');
actualmatch = string.match(pattern);
}
catch (e8)
{
pattern = 'error';
actualmatch = '';
}
expectedmatch = Array('c{3 }');
addThis();

status = this.inSection(7);
try
{
pattern = new RegExp('c{3.}');
actualmatch = string.match(pattern);
}
catch (e9)
{
pattern = 'error';
actualmatch = '';
}
expectedmatch = Array('c{3 }');
addThis();

status = this.inSection(8);
try
{
// need to escape the \ in \s since
// this has been converted to a constructor call
// instead of a literal regexp
pattern = new RegExp('c{3\\s}');
actualmatch = string.match(pattern);
}
catch (e10)
{
pattern = 'error';
actualmatch = '';
}
expectedmatch = Array('c{3 }');
addThis();

status = this.inSection(9);
try
{
pattern = new RegExp('c{3[ ]}');
actualmatch = string.match(pattern);
}
catch (e11)
{
pattern = 'error';
actualmatch = '';
}
expectedmatch = Array('c{3 }');
addThis();

status = this.inSection(10);
try
{
pattern = new RegExp('c{ 3}');
actualmatch = string.match(pattern);
}
catch (e12)
{
pattern = 'error';
actualmatch = '';
}
expectedmatch = Array('c{ 3}');
addThis();

// using braces in a normal quantifier construct again
status = this.inSection(11);
try
{
pattern = new RegExp('c{3,}');
actualmatch = string.match(pattern);
}
catch (e13)
{
pattern = 'error';
actualmatch = '';
}
expectedmatch = Array('ccccc');
addThis();

// now disrupt it - the braces should now be interpreted literally
status = this.inSection(12);
try
{
pattern = new RegExp('c{3, }');
actualmatch = string.match(pattern);
}
catch (e14)
{
pattern = 'error';
actualmatch = '';
}
expectedmatch = Array('c{3, }');
addThis();

status = this.inSection(13);
try
{
pattern = new RegExp('c{3 ,}');
actualmatch = string.match(pattern);
}
catch (e15)
{
pattern = 'error';
actualmatch = '';
}
expectedmatch = Array('c{3 ,}');
addThis();

// using braces in a normal quantifier construct again
status = this.inSection(14);
try
{
pattern = new RegExp('c{3,4}');
actualmatch = string.match(pattern);
}
catch (e16)
{
pattern = 'error';
actualmatch = '';
}
expectedmatch = Array('cccc');
addThis();

// now disrupt it - the braces should now be interpreted literally
status = this.inSection(15);
try
{
pattern = new RegExp('c{3 ,4}');
actualmatch = string.match(pattern);
}
catch (e17)
{
pattern = 'error';
actualmatch = '';
}
expectedmatch = Array('c{3 ,4}');
addThis();

status = this.inSection(16);
try
{
pattern = new RegExp('c{3, 4}');
actualmatch = string.match(pattern);
}
catch (e18)
{
pattern = 'error';
actualmatch = '';
}
expectedmatch = Array('c{3, 4}');
addThis();

status = this.inSection(17);
try
{
pattern = new RegExp('c{3,4 }');
actualmatch = string.match(pattern);
}
catch (e19)
{
pattern = 'error';
actualmatch = '';
}
expectedmatch = Array('c{3,4 }');
addThis();




//-------------------------------------------------------------------------------------------------
test();
//-------------------------------------------------------------------------------------------------



function addThis()
{
statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


function test()
{
}

},
//-----------------------------------------------------------------------------
test_regress__274152:function(){

var BUGNUMBER = 274152;
var summary = 'Do not ignore unicode format-control characters';
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

expect = 'SyntaxError: illegal character';

var formatcontrolchars = ['\u200C',
'\u200D',
'\u200E',
'\u0600',
'\u0601',
'\u0602',
'\u0603',
'\u06DD',
'\u070F'];

for (var i = 0; i < formatcontrolchars.length; i++)
{
try
{
eval("hi" + formatcontrolchars[i] + "there = 'howdie';");
}
catch(ex1)
{
actual = ex1 + '';
}

this.reportCompare(expect, actual, summary + ': ' + i);
}

//   exitFunc ('test');
}

},
//-----------------------------------------------------------------------------
test_regress__320854:function(){

var BUGNUMBER = 320854;
var summary = 'o.hasOwnProperty("length") should not lie when o has function in proto chain';
var actual = '';
var expect = '';

// printBugNumber(BUGNUMBER);
// printStatus (summary);

var o = {__proto__:function(){}};

expect = false;
actual = o.hasOwnProperty('length')

this.reportCompare(expect, actual, summary);

},
//-----------------------------------------------------------------------------
test_regress__327170:function(){

var BUGNUMBER = 327170;
var summary = 'Reuse of RegExp in string.replace(rx.compile(...), function() { rx.compile(...); }) causes a crash';
var actual = 'No Crash';
var expect = 'No Crash';

// printBugNumber(BUGNUMBER);
// printStatus (summary);

var g_rx = /(?:)/;

"this is a test-string".replace(g_rx.compile("test", "g"),
function()
{
// re-use of the g_rx RegExp object,
// that's currently in use by the replace fn.
g_rx.compile("string", "g");
});

this.reportCompare(expect, actual, summary);

},
//-----------------------------------------------------------------------------
test_regress__368516:function(){

var BUGNUMBER = 368516;
var summary = 'Treat unicode BOM characters as whitespace';
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

var bomchars = ['\uFFFE',
'\uFEFF'];
var hithere = null;
for (var i = 0; i < bomchars.length; i++)
{
expect = 'howdie';
actual = '';

try
{
eval("var" + bomchars[i] + "hithere = 'howdie';");
actual = hithere;
}
catch(ex2)
{
actual = ex2 + '';
}

this.reportCompare(expect, actual, summary + ': ' + i);
}

//   exitFunc ('test');
}

},
//-----------------------------------------------------------------------------
test_regress__385393__03:function(){

var BUGNUMBER = 385393;
var summary = 'Regression test for bug 385393';
var actual = '';
var expect = '';
var y = null;
var f = null;


//-----------------------------------------------------------------------------
test();
//-----------------------------------------------------------------------------

function test()
{
//   enterFunc ('test');
//   printBugNumber(BUGNUMBER);
//   printStatus (summary);

f = (function() { new (delete y) });
eval(this.uneval(f))

this.reportCompare(expect, actual, summary);

//   exitFunc ('test');
}

},
//-----------------------------------------------------------------------------
test_regress__429248:function(){

var BUGNUMBER = 429248;
var summary = 'Do not assert: 0';
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

function c() { do{}while(0) }

c + '';

this.reportCompare(expect, actual, summary);

//   exitFunc ('test');
}

},
//-----------------------------------------------------------------------------
test_regress__430740:function(){

var BUGNUMBER = 430740;
var summary = 'Do not strip format-control characters from string literals';
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

function doevil() {
//    print('evildone');
return 'evildone';
}

expect = 'a%E2%80%8D,+doevil()%5D)//';
actual += eval("(['a\\\u200d', '+doevil()])//'])");
actual = encodeURI(actual);
this.reportCompare(expect, actual, summary);

expect = 'a%EF%BF%BE,+doevil()%5D)//';
actual = eval("(['a\\\ufffe', '+doevil()])//'])");
actual = encodeURI(actual);
this.reportCompare(expect, actual, summary);

//   exitFunc ('test');
}

}}).endType()



