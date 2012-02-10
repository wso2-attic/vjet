vjo.ctype("dsf.jslang.feature.tests.Ecma2RegExpTests")
.inherits("dsf.jslang.feature.tests.BaseTestEcma2")
.protos({

constructs: function() {
this.base();
},

AddTestCase : function (d, e, a){
this.TestCase("", d, e, a);
},

/**
*  File Name:          constructor__001.js
*  ECMA Section:       15.7.3.3
*  Description:        Based on ECMA 2 Draft 7 February 1999
*
*  Author:             christine@netscape.com
*  Date:               19 February 1999
*/
test_constructor__001 : function() {
var SECTION = "constructor__001";
var VERSION = "ECMA_2";
var TITLE   = "new RegExp()";

//startTest();

/*
* for each test case, verify:
* - verify that [[Class]] property is RegExp
* - prototype property should be set to RegExp.prototype
* - source is set to the empty string
* - global property is set to false
* - ignoreCase property is set to false
* - multiline property is set to false
* - lastIndex property is set to 0
*/

RegExp.prototype.getClassProperty = Object.prototype.toString;
var re = new RegExp();

this.AddTestCase(
"RegExp.prototype.getClassProperty = Object.prototype.toString; " +
"(new RegExp()).getClassProperty()",
"[object RegExp]",
re.getClassProperty() );

this.AddTestCase(
"(new RegExp()).source",
"",
re.source );

this.AddTestCase(
"(new RegExp()).global",
false,
re.global );

this.AddTestCase(
"(new RegExp()).ignoreCase",
false,
re.ignoreCase );

this.AddTestCase(
"(new RegExp()).multiline",
false,
re.multiline );

this.AddTestCase(
"(new RegExp()).lastIndex",
0,
re.lastIndex );

//test()

},

/**
*  File Name:          exec__002.js
*  ECMA Section:       15.7.5.3
*  Description:        Based on ECMA 2 Draft 7 February 1999
*
*  Test cases provided by rogerl@netscape.com
*
*  Author:             christine@netscape.com
*  Date:               19 February 1999
*/
test_exec__002 : function() {
var SECTION = "exec__002";
var VERSION = "ECMA_2";
var TITLE   = "RegExp.prototype.exec(string)";

//startTest();

/*
* for each test case, verify:
* - type of object returned
* - length of the returned array
* - value of lastIndex
* - value of index
* - value of input
* - value of the array indices
*/

AddRegExpCases(this,
/(a|d|q|)x/i,
"bcaDxqy",
3,
["Dx", "D"] );

AddRegExpCases(this,
/(a|(e|q))(x|y)/,
"bcaddxqy",
6,
["qy","q","q","y"] );


AddRegExpCases(this,
/a+b+d/,
"aabbeeaabbs",
0,
null );

AddRegExpCases(this,
/a*b/,
"aaadaabaaa",
4,
["aab"] );

AddRegExpCases(this,
/a*b/,
"dddb",
3,
["b"] );

AddRegExpCases(this,
/a*b/,
"xxx",
0,
null );

AddRegExpCases(this,
/x\d\dy/,
"abcx45ysss235",
3,
["x45y"] );

AddRegExpCases(this,
/[^abc]def[abc]+/,
"abxdefbb",
2,
["xdefbb"] );

AddRegExpCases(this,
/(a*)baa/,
"ccdaaabaxaabaa",
9,
["aabaa", "aa"] );

AddRegExpCases(this,
/(a*)baa/,
"aabaa",
0,
["aabaa", "aa"] );

AddRegExpCases(this,
/q(a|b)*q/,
"xxqababqyy",
2,
["qababq", "b"] );

AddRegExpCases(this,
/(a(.|[^d])c)*/,
"adcaxc",
0,
["adcaxc", "axc", "x"] );

AddRegExpCases(this,
/(a*)b\1/,
"abaaaxaabaayy",
0,
["aba", "a"] );

AddRegExpCases(this,
/(a*)b\1/,
"abaaaxaabaayy",
0,
["aba", "a"] );

AddRegExpCases(this,
/(a*)b\1/,
"cccdaaabaxaabaayy",
6,
["aba", "a"] );

AddRegExpCases(this,
/(a*)b\1/,
"cccdaaabqxaabaayy",
7,
["b", ""] );

AddRegExpCases(this,
/"(.|[^"\\\\])*"/,
'xx\"makudonarudo\"yy',
2,
["\"makudonarudo\"", "o"] );

AddRegExpCases(this,
/"(.|[^"\\\\])*"/,
"xx\"ma\"yy",
2,
["\"ma\"", "a"] );

//test();

function AddRegExpCases(obj,
regexp, pattern, index, matches_array ) {

// prevent a runtime error

if ( regexp.exec(pattern) == null || matches_array == null ) {
obj.AddTestCase(
regexp + ".exec(" + pattern +")",
matches_array,
regexp.exec(pattern) );

return;
}
obj.AddTestCase(
regexp + ".exec(" + pattern +").length",
matches_array.length,
regexp.exec(pattern).length );

obj.AddTestCase(
regexp + ".exec(" + pattern +").index",
index,
regexp.exec(pattern).index );

obj.AddTestCase(
regexp + ".exec(" + pattern +").input",
pattern,
regexp.exec(pattern).input );

obj.AddTestCase(
regexp + ".exec(" + pattern +").toString()",
matches_array.toString(),
regexp.exec(pattern).toString() );
/*
var limit = matches_array.length > regexp.exec(pattern).length
? matches_array.length
: regexp.exec(pattern).length;

for ( var matches = 0; matches < limit; matches++ ) {
AddTestCase(
regexp + ".exec(" + pattern +")[" + matches +"]",
matches_array[matches],
regexp.exec(pattern)[matches] );
}
*/
}

},

/**
*  File Name:          function__001.js
*  ECMA Section:       15.7.2.1
*  Description:        Based on ECMA 2 Draft 7 February 1999
*
*  Author:             christine@netscape.com
*  Date:               19 February 1999
*/
test_function__001 : function() {
var SECTION = "function__001";
var VERSION = "ECMA_2";
var TITLE   = "RegExp( pattern, flags )";

//startTest();

/*
* for each test case, verify:
* - verify that [[Class]] property is RegExp
* - prototype property should be set to RegExp.prototype
* - source is set to the empty string
* - global property is set to false
* - ignoreCase property is set to false
* - multiline property is set to false
* - lastIndex property is set to 0
*/

RegExp.prototype.getClassProperty = Object.prototype.toString;
var re = new RegExp();

this.AddTestCase(
"RegExp.prototype.getClassProperty = Object.prototype.toString; " +
"(new RegExp()).getClassProperty()",
"[object RegExp]",
re.getClassProperty() );

this.AddTestCase(
"(new RegExp()).source",
"",
re.source );

this.AddTestCase(
"(new RegExp()).global",
false,
re.global );

this.AddTestCase(
"(new RegExp()).ignoreCase",
false,
re.ignoreCase );

this.AddTestCase(
"(new RegExp()).multiline",
false,
re.multiline );

this.AddTestCase(
"(new RegExp()).lastIndex",
0,
re.lastIndex );

//test()

},

/**
*  File Name:          hex__001.js
*  ECMA Section:       15.7.3.1
*  Description:        Based on ECMA 2 Draft 7 February 1999
*  Positive test cases for constructing a RegExp object
*  Author:             christine@netscape.com
*  Date:               19 February 1999
*/
test_hex__001 : function() {
var SECTION = "hex__001";
var VERSION = "ECMA_2";
var TITLE   = "RegExp patterns that contain HexicdecimalEscapeSequences";

//startTest();

// These examples come from 15.7.1, HexidecimalEscapeSequence

AddRegExpCases(this, new RegExp("\x41"),  "new RegExp('\\x41')",  "A",  "A", 1, 0, ["A"] );
AddRegExpCases(this, new RegExp("\x412"),"new RegExp('\\x412')", "A2", "A2", 1, 0, ["A2"] );
AddRegExpCases(this, new RegExp("\x1g"), "new RegExp('\\x1g')",  "x1g","x1g", 1, 0, ["x1g"] );

AddRegExpCases(this, new RegExp("A"),  "new RegExp('A')",  "\x41",  "\\x41",  1, 0, ["A"] );
AddRegExpCases(this, new RegExp("A"),  "new RegExp('A')",  "\x412", "\\x412", 1, 0, ["A"] );
AddRegExpCases(this, new RegExp("^x"), "new RegExp('^x')", "x412",  "x412",   1, 0, ["x"]);
AddRegExpCases(this, new RegExp("A"),  "new RegExp('A')",  "A2",    "A2",     1, 0, ["A"] );

//test();

function AddRegExpCases(obj,
regexp, str_regexp, pattern, str_pattern, length, index, matches_array ) {

// prevent a runtime error

if ( regexp.exec(pattern) == null || matches_array == null ) {
obj.AddTestCase(
str_regexp + ".exec(" + pattern +")",
matches_array,
regexp.exec(pattern) );

return;
}

obj.AddTestCase(
str_regexp + ".exec(" + str_pattern +").length",
length,
regexp.exec(pattern).length );

obj.AddTestCase(
str_regexp + ".exec(" + str_pattern +").index",
index,
regexp.exec(pattern).index );

obj.AddTestCase(
str_regexp + ".exec(" + str_pattern +").input",
pattern,
regexp.exec(pattern).input );

for ( var matches = 0; matches < matches_array.length; matches++ ) {
obj.AddTestCase(
str_regexp + ".exec(" + str_pattern +")[" + matches +"]",
matches_array[matches],
regexp.exec(pattern)[matches] );
}
}

},

/**
*  File Name:          multiline__001.js
*  ECMA Section:
*  Description:        Based on ECMA 2 Draft 7 February 1999
*
*  Date:               19 February 1999
*/
test_multiline__001 : function() {
var SECTION = "multiline__001";
var VERSION = "ECMA_2";
var TITLE   = "RegExp: multiline flag";
//var BUGNUMBER="343901";

//startTest();

var woodpeckers = "ivory-billed\ndowny\nhairy\nacorn\nyellow-bellied sapsucker\n" +
"northern flicker\npileated\n";

AddRegExpCases(this, /.*[y]$/m, woodpeckers, woodpeckers.indexOf("downy"), ["downy"] );

AddRegExpCases(this, /.*[d]$/m, woodpeckers, woodpeckers.indexOf("ivory-billed"), ["ivory-billed"] );

//test();


function AddRegExpCases
(obj, regexp, pattern, index, matches_array ) {

// prevent a runtime error

if ( regexp.exec(pattern) == null || matches_array == null ) {
obj.AddTestCase(
regexp + ".exec(" + pattern +")",
matches_array,
regexp.exec(pattern) );

return;
}

obj.AddTestCase(
regexp.toString() + ".exec(" + pattern +").length",
matches_array.length,
regexp.exec(pattern).length );

obj.AddTestCase(
regexp.toString() + ".exec(" + pattern +").index",
index,
regexp.exec(pattern).index );

obj.AddTestCase(
regexp + ".exec(" + pattern +").input",
pattern,
regexp.exec(pattern).input );


for ( var matches = 0; matches < matches_array.length; matches++ ) {
obj.AddTestCase(
regexp + ".exec(" + pattern +")[" + matches +"]",
matches_array[matches],
regexp.exec(pattern)[matches] );
}
}

},

/**
*  File Name:          octal__001.js
*  ECMA Section:       15.7.1
*  Description:        Based on ECMA 2 Draft 7 February 1999
*  Simple test cases for matching OctalEscapeSequences.
*  Author:             christine@netscape.com
*  Date:               19 February 1999
*/
test_octal__001 : function() {
var SECTION = "octal__001";
var VERSION = "ECMA_2";
var TITLE   = "RegExp patterns that contain OctalEscapeSequences";
//var BUGNUMBER="http://scopus/bugsplat/show_bug.cgi?id=346196";

//startTest();


// backreference
AddRegExpCases(this,
/(.)\1/,
"/(.)\\1/",
"HI!!",
"HI!",
2,
["!!", "!"] );

//test();

function AddRegExpCases(obj,
regexp, str_regexp, pattern, str_pattern, index, matches_array ) {

// prevent a runtime error

if ( regexp.exec(pattern) == null || matches_array == null ) {
obj.AddTestCase(
regexp + ".exec(" + str_pattern +")",
matches_array,
regexp.exec(pattern) );

return;
}
obj.AddTestCase(
str_regexp + ".exec(" + str_pattern +").length",
matches_array.length,
regexp.exec(pattern).length );

obj.AddTestCase(
str_regexp + ".exec(" + str_pattern +").index",
index,
regexp.exec(pattern).index );

obj.AddTestCase(
str_regexp + ".exec(" + str_pattern +").input",
pattern,
regexp.exec(pattern).input );

obj.AddTestCase(
str_regexp + ".exec(" + str_pattern +").toString()",
matches_array.toString(),
regexp.exec(pattern).toString() );
/*
var limit = matches_array.length > regexp.exec(pattern).length
? matches_array.length
: regexp.exec(pattern).length;

for ( var matches = 0; matches < limit; matches++ ) {
AddTestCase(
str_regexp + ".exec(" + str_pattern +")[" + matches +"]",
matches_array[matches],
regexp.exec(pattern)[matches] );
}
*/
}

},

/**
*  File Name:          octal__002.js
*  ECMA Section:       15.7.1
*  Description:        Based on ECMA 2 Draft 7 February 1999
*  Simple test cases for matching OctalEscapeSequences.
*  Author:             christine@netscape.com
*  Date:               19 February 1999
*/
test_octal__002 : function() {
var SECTION = "octal__002";
var VERSION = "ECMA_2";
var TITLE   = "RegExp patterns that contain OctalEscapeSequences";
//var BUGNUMBER="http://scopus/bugsplat/show_bug.cgi?id=346189";

//startTest();

// backreference
AddRegExpCases(this,
/(.)(.)(.)(.)(.)(.)(.)(.)\8/,
"/(.)(.)(.)(.)(.)(.)(.)(.)\\8",
"aabbccaaabbbccc",
"aabbccaaabbbccc",
0,
["aabbccaaa", "a", "a", "b", "b", "c", "c", "a", "a"] );

AddRegExpCases(this,
/(.)(.)(.)(.)(.)(.)(.)(.)(.)\9/,
"/(.)(.)(.)(.)(.)(.)(.)(.)\\9",
"aabbccaabbcc",
"aabbccaabbcc",
0,
["aabbccaabb", "a", "a", "b", "b", "c", "c", "a", "a", "b"] );

AddRegExpCases(this,
/(.)(.)(.)(.)(.)(.)(.)(.)(.)\8/,
"/(.)(.)(.)(.)(.)(.)(.)(.)(.)\\8",
"aabbccaababcc",
"aabbccaababcc",
0,
["aabbccaaba", "a", "a", "b", "b", "c", "c", "a", "a", "b"] );

//test();

function AddRegExpCases(obj,
regexp, str_regexp, pattern, str_pattern, index, matches_array ) {

// prevent a runtime error

if ( regexp.exec(pattern) == null || matches_array == null ) {
obj.AddTestCase(
regexp + ".exec(" + str_pattern +")",
matches_array,
regexp.exec(pattern) );

return;
}
obj.AddTestCase(
str_regexp + ".exec(" + str_pattern +").length",
matches_array.length,
regexp.exec(pattern).length );

obj.AddTestCase(
str_regexp + ".exec(" + str_pattern +").index",
index,
regexp.exec(pattern).index );

obj.AddTestCase(
str_regexp + ".exec(" + str_pattern +").input",
pattern,
regexp.exec(pattern).input );

obj.AddTestCase(
str_regexp + ".exec(" + str_pattern +").toString()",
matches_array.toString(),
regexp.exec(pattern).toString() );
/*
var limit = matches_array.length > regexp.exec(pattern).length
? matches_array.length
: regexp.exec(pattern).length;

for ( var matches = 0; matches < limit; matches++ ) {
AddTestCase(
str_regexp + ".exec(" + str_pattern +")[" + matches +"]",
matches_array[matches],
regexp.exec(pattern)[matches] );
}
*/
}

},

/**
*  File Name:          octal__003.js
*  ECMA Section:       15.7.1
*  Description:        Based on ECMA 2 Draft 7 February 1999
*  Simple test cases for matching OctalEscapeSequences.
*  Author:             christine@netscape.com
*  Date:               19 February 1999
*
*  Revised:            02 August 2002
*  Author:             pschwartau@netscape.com
*
*  WHY:  the original test expected the regexp /.\011/
*        to match 'a' + String.fromCharCode(0) + '11'
*
*  This is incorrect: the string is a 4-character string consisting of
*  the characters <'a'>, <nul>, <'1'>, <'1'>. By contrast, the \011 in the
*  regexp should be parsed as a single token: it is the octal escape sequence
*  for the horizontal tab character '\t' === '\u0009' === '\x09' === '\011'.
*
*  So the regexp consists of 2 characters: <any-character>, <'\t'>.
*  There is no match between the regexp and the string.
*
*  See the testcase ecma_3/RegExp/octal-002.js for an elaboration.
*/
test_octal__003 : function() {
var SECTION = "octal__003";
var VERSION = "ECMA_2";
var TITLE   = "RegExp patterns that contain OctalEscapeSequences";
//var BUGNUMBER="http://scopus/bugsplat/show_bug.cgi?id=346132";

//startTest();

AddRegExpCases(this, /.\011/, "/\\011/", "a" + String.fromCharCode(0) + "11", "a\\011", 0, null );

//test();

function AddRegExpCases(obj,
regexp, str_regexp, pattern, str_pattern, index, matches_array ) {

// prevent a runtime error

if ( regexp.exec(pattern) == null || matches_array == null ) {
obj.AddTestCase(
regexp + ".exec(" + str_pattern +")",
matches_array,
regexp.exec(pattern) );

return;
}
obj.AddTestCase(
str_regexp + ".exec(" + str_pattern +").length",
matches_array.length,
regexp.exec(pattern).length );

obj.AddTestCase(
str_regexp + ".exec(" + str_pattern +").index",
index,
regexp.exec(pattern).index );

obj.AddTestCase(
str_regexp + ".exec(" + str_pattern +").input",
escape(pattern),
escape(regexp.exec(pattern).input) );

obj.AddTestCase(
str_regexp + ".exec(" + str_pattern +").toString()",
matches_array.toString(),
escape(regexp.exec(pattern).toString()) );

var limit = matches_array.length > regexp.exec(pattern).length
? matches_array.length
: regexp.exec(pattern).length;

for ( var matches = 0; matches < limit; matches++ ) {
obj.AddTestCase(
str_regexp + ".exec(" + str_pattern +")[" + matches +"]",
matches_array[matches],
escape(regexp.exec(pattern)[matches]) );
}

}

},

/**
*  File Name:          properties__001.js
*  ECMA Section:       15.7.6.js
*  Description:        Based on ECMA 2 Draft 7 February 1999
*
*  Author:             christine@netscape.com
*  Date:               19 February 1999
*/
test_properties__001 : function() {
var SECTION = "properties__001";
var VERSION = "ECMA_2";
var TITLE   = "Properties of RegExp Instances";
//var BUGNUMBER ="";

//startTest();

AddRegExpCases(this, new RegExp, "",   false, false, false, 0 );
AddRegExpCases(this, /.*/,       ".*", false, false, false, 0 );
AddRegExpCases(this, /[\d]{5}/g, "[\\d]{5}", true, false, false, 0 );
AddRegExpCases(this, /[\S]?$/i,  "[\\S]?$", false, true, false, 0 );
AddRegExpCases(this, /^([a-z]*)[^\w\s\f\n\r]+/m,  "^([a-z]*)[^\\w\\s\\f\\n\\r]+", false, false, true, 0 );
AddRegExpCases(this, /[\D]{1,5}[\ -][\d]/gi,      "[\\D]{1,5}[\\ -][\\d]", true, true, false, 0 );
AddRegExpCases(this, /[a-zA-Z0-9]*/gm, "[a-zA-Z0-9]*", true, false, true, 0 );
AddRegExpCases(this, /x|y|z/gim, "x|y|z", true, true, true, 0 );

AddRegExpCases(this, /\u0051/im, "\\u0051", false, true, true, 0 );
AddRegExpCases(this, /\x45/gm, "\\x45", true, false, true, 0 );
AddRegExpCases(this, /\097/gi, "\\097", true, true, false, 0 );

//test();

function AddRegExpCases(obj, re, s, g, i, m, l ) {

obj.AddTestCase( re + ".test == RegExp.prototype.test",
true,
re.test == RegExp.prototype.test );

obj.AddTestCase( re + ".toString == RegExp.prototype.toString",
true,
re.toString == RegExp.prototype.toString );

obj.AddTestCase( re + ".contructor == RegExp.prototype.constructor",
true,
re.constructor == RegExp.prototype.constructor );

obj.AddTestCase( re + ".compile == RegExp.prototype.compile",
true,
re.compile == RegExp.prototype.compile );

obj.AddTestCase( re + ".exec == RegExp.prototype.exec",
true,
re.exec == RegExp.prototype.exec );

// properties

obj.AddTestCase( re + ".source",
s,
re.source );

/*
* http://bugzilla.mozilla.org/show_bug.cgi?id=225550 changed
* the behavior of toString() and toSource() on empty regexps.
* So branch if |s| is the empty string -
*/
var S = s? s : '(?:)';

obj.AddTestCase( re + ".toString()",
"/" + S +"/" + (g?"g":"") + (i?"i":"") +(m?"m":""),
re.toString() );

obj.AddTestCase( re + ".global",
g,
re.global );

obj.AddTestCase( re + ".ignoreCase",
i,
re.ignoreCase );

obj.AddTestCase( re + ".multiline",
m,
re.multiline);

obj.AddTestCase( re + ".lastIndex",
l,
re.lastIndex  );
}

},

/**
*  File Name:          properties__002.js
*  ECMA Section:       15.7.6.js
*  Description:        Based on ECMA 2 Draft 7 February 1999
*
*  Author:             christine@netscape.com
*  Date:               19 February 1999

*/
test_properties__002 : function() {
var SECTION = "properties__002";
var VERSION = "ECMA_2";
var TITLE   = "Properties of RegExp Instances";
//var BUGNUMBER ="124339";

//startTest();

re_1 = /\cA?/g;
re_1.lastIndex = Math.pow(2,31);
AddRegExpCases(this, re_1, "\\cA?", true, false, false, Math.pow(2,31) );

re_2 = /\w*/i;
re_2.lastIndex = Math.pow(2,32) -1;
AddRegExpCases(this, re_2, "\\w*", false, true, false, Math.pow(2,32)-1 );

re_3 = /\*{0,80}/m;
re_3.lastIndex = Math.pow(2,31) -1;
AddRegExpCases(this, re_3, "\\*{0,80}", false, false, true, Math.pow(2,31) -1 );

re_4 = /^./gim;
re_4.lastIndex = Math.pow(2,30) -1;
AddRegExpCases(this, re_4, "^.", true, true, true, Math.pow(2,30) -1 );

re_5 = /\B/;
re_5.lastIndex = Math.pow(2,30);
AddRegExpCases(this, re_5, "\\B", false, false, false, Math.pow(2,30) );

/*
* Brendan: "need to test cases Math.pow(2,32) and greater to see
* whether they round-trip." Reason: thanks to the work done in
* http://bugzilla.mozilla.org/show_bug.cgi?id=124339, lastIndex
* is now stored as a double instead of a uint32 (unsigned integer).
*
* Note 2^32 -1 is the upper bound for uint32's, but doubles can go
* all the way up to Number.MAX_VALUE. So that's why we need cases
* between those two numbers.
*
*/
re_6 = /\B/;
re_6.lastIndex = Math.pow(2,32);
AddRegExpCases(this, re_6, "\\B", false, false, false, Math.pow(2,32) );

re_7 = /\B/;
re_7.lastIndex = Math.pow(2,32) + 1;
AddRegExpCases(this, re_7, "\\B", false, false, false, Math.pow(2,32) + 1 );

re_8 = /\B/;
re_8.lastIndex = Math.pow(2,32) * 2;
AddRegExpCases(this, re_8, "\\B", false, false, false, Math.pow(2,32) * 2 );

re_9 = /\B/;
re_9.lastIndex = Math.pow(2,40);
AddRegExpCases(this, re_9, "\\B", false, false, false, Math.pow(2,40) );

re_10 = /\B/;
re_10.lastIndex = Number.MAX_VALUE;
AddRegExpCases(this, re_10, "\\B", false, false, false, Number.MAX_VALUE );



//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------



function AddRegExpCases(obj, re, s, g, i, m, l ){

obj.AddTestCase( re + ".test == RegExp.prototype.test",
true,
re.test == RegExp.prototype.test );

obj.AddTestCase( re + ".toString == RegExp.prototype.toString",
true,
re.toString == RegExp.prototype.toString );

obj.AddTestCase( re + ".contructor == RegExp.prototype.constructor",
true,
re.constructor == RegExp.prototype.constructor );

obj.AddTestCase( re + ".compile == RegExp.prototype.compile",
true,
re.compile == RegExp.prototype.compile );

obj.AddTestCase( re + ".exec == RegExp.prototype.exec",
true,
re.exec == RegExp.prototype.exec );

// properties

obj.AddTestCase( re + ".source",
s,
re.source );

obj.AddTestCase( re + ".toString()",
"/" + s +"/" + (g?"g":"") + (i?"i":"") +(m?"m":""),
re.toString() );

obj.AddTestCase( re + ".global",
g,
re.global );

obj.AddTestCase( re + ".ignoreCase",
i,
re.ignoreCase );

obj.AddTestCase( re + ".multiline",
m,
re.multiline);

obj.AddTestCase( re + ".lastIndex",
l,
re.lastIndex  );
}

},

/**
*  File Name:          regexp__enumerate__001.js
*  ECMA V2 Section:
Description:        Regression Test.

If instance Native Object have properties that are enumerable,
JavaScript enumerated through the properties twice. This only
happened if objects had been instantiated, but their properties
had not been enumerated.  ie, the object inherited properties
from its prototype that are enumerated.

In the core JavaScript, this is only a problem with RegExp
objects, since the inherited properties of most core JavaScript
objects are not enumerated.

Author:             christine@netscape.com, pschwartau@netscape.com
Date:               12 November 1997
Modified:           14 July 2002
Reason:             See http://bugzilla.mozilla.org/show_bug.cgi?id=155291
ECMA-262 Ed.3  Sections 15.10.7.1 through 15.10.7.5
RegExp properties should be DontEnum
*/
test_regexp__enumerate__001 : function() {
var SECTION = "regexp__enumerate__001";
var VERSION = "ECMA_2";
var TITLE   = "Regression Test for Enumerating Properties";

//var BUGNUMBER="339403";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

/*
*  This test expects RegExp instances to have four enumerated properties:
*  source, global, ignoreCase, and lastIndex
*
*  99.01.25:  now they also have a multiLine instance property.
*
*/


var r = new RegExp();

var e = new Array();

var t = new TestRegExp();
var p;//<Number
for ( p in r ) { e[e.length] = { property:p, value:r[p] }; t.addProperty( p, r[p]) };

this.TestCase( SECTION,
"r = new RegExp(); e = new Array(); "+
"for ( p in r ) { e[e.length] = { property:p, value:r[p] }; e.length",
0,
e.length );

//test();

function TestRegExp() {
this.addProperty = addProperty;
}

function addProperty(name, value) {
var pass = false;

if ( eval("this."+name) != void 0 ) {
pass = true;
} else {
eval( "this."+ name+" = "+ false );
}

new TestCase( SECTION,
"Property: " + name +" already enumerated?",
false,
pass );
var gTestcases = null;
if ( gTestcases[ gTestcases.length-1].passed == false ) {
gTestcases[gTestcases.length-1].reason = "property already enumerated";

}

}

},

/**
*  File Name:          regress__001.js
*  ECMA Section:       N/A
*  Description:        Regression test case:
*  JS regexp anchoring on empty match bug
*  http://bugzilla.mozilla.org/show_bug.cgi?id=2157
*
*  Author:             christine@netscape.com
*  Date:               19 February 1999
*/
test_regress__001 : function() {
var SECTION = "regress__001";
var VERSION = "ECMA_2";
var TITLE   = "JS regexp anchoring on empty match bug";
//var BUGNUMBER = "2157";

//startTest();

AddRegExpCases(this, /a||b/(''),
"//a||b/('')",
1,
[''] );

//test();

function AddRegExpCases(obj, regexp, str_regexp, length, matches_array ) {

obj.AddTestCase(
"( " + str_regexp + " ).length",
regexp.length,
regexp.length );


for ( var matches = 0; matches < matches_array.length; matches++ ) {
obj.AddTestCase(
"( " + str_regexp + " )[" + matches +"]",
matches_array[matches],
regexp[matches] );
}
}

},

/**
*  File Name:          unicode__001.js
*  ECMA Section:       15.7.3.1
*  Description:        Based on ECMA 2 Draft 7 February 1999
*  Positive test cases for constructing a RegExp object
*  Author:             christine@netscape.com
*  Date:               19 February 1999
*/
test_unicode__001 : function() {
var SECTION = "unicode__001";
var VERSION = "ECMA_2";
var TITLE   = "new RegExp( pattern, flags )";

//startTest();

// These examples come from 15.7.1, UnicodeEscapeSequence

AddRegExpCases(this, /\u0041/, "/\\u0041/",   "A", "A", 1, 0, ["A"] );
AddRegExpCases(this, /\u00412/, "/\\u00412/", "A2", "A2", 1, 0, ["A2"] );
AddRegExpCases(this, /\u00412/, "/\\u00412/", "A2", "A2", 1, 0, ["A2"] );
//AddRegExpCases(this, /\u001g/, "/\\u001g/", "u001g", "u001g", 1, 0, ["u001g"] );
//AddRegExpCases(this, /A/,  "/A/",  "\u0041", "\\u0041",   1, 0, ["A"] );
AddRegExpCases(this, /A/,  "/A/",  "\u00412", "\\u00412", 1, 0, ["A"] );
AddRegExpCases(this, /A2/, "/A2/", "\u00412", "\\u00412", 1, 0, ["A2"]);
AddRegExpCases(this, /A/,  "/A/",  "A2",      "A2",       1, 0, ["A"] );

//test();

function AddRegExpCases(obj,
regexp, str_regexp, pattern, str_pattern, length, index, matches_array ) {

obj.AddTestCase(
str_regexp + " .exec(" + str_pattern +").length",
length,
regexp.exec(pattern).length );

obj.AddTestCase(
str_regexp + " .exec(" + str_pattern +").index",
index,
regexp.exec(pattern).index );

obj.AddTestCase(
str_regexp + " .exec(" + str_pattern +").input",
pattern,
regexp.exec(pattern).input );

for ( var matches = 0; matches < matches_array.length; matches++ ) {
obj.AddTestCase(
str_regexp + " .exec(" + str_pattern +")[" + matches +"]",
matches_array[matches],
regexp.exec(pattern)[matches] );
}
}

}


})
.endType();

