vjo.ctype("dsf.jslang.feature.tests.Ecma2StringTests")
.inherits("dsf.jslang.feature.tests.BaseTestEcma2")
.protos({

constructs: function() {
this.base();
},

AddTestCase : function (d, e, a){
this.TestCase("", d, e, a);
},

/**
*  File Name:          match__001.js
*  ECMA Section:       15.6.4.9
*  Description:        Based on ECMA 2 Draft 7 February 1999
*
*  Author:             christine@netscape.com
*  Date:               19 February 1999
*
*  String.match( regexp )
*
*  If regexp is not an object of type RegExp, it is replaced with result
*  of the expression new RegExp(regexp). Let string denote the result of
*  converting the this value to a string.  If regexp.global is false,
*  return the result obtained by invoking RegExp.prototype.exec (see
*  section 15.7.5.3) on regexp with string as parameter.
*
*  Otherwise, set the regexp.lastIndex property to 0 and invoke
*  RegExp.prototype.exec repeatedly until there is no match. If there is a
*  match with an empty string (in other words, if the value of
*  regexp.lastIndex is left unchanged) increment regexp.lastIndex by 1.
*  The value returned is an array with the properties 0 through n-1
*  corresponding to the first element of the result of each matching
*  invocation of RegExp.prototype.exec.
*
*  Note that the match function is intentionally generic; it does not
*  require that its this value be a string object.  Therefore, it can be
*  transferred to other kinds of objects for use as a method.

*/
test_match__001 : function() {
var SECTION = "match__001";
var VERSION = "ECMA_2";
var TITLE   = "String.prototype.match( regexp )";
var s = null;//<Object

var lim = null;//<Object

var re = null;//Object

var z = null;//<Object
//startTest();

// the regexp argument is not a RegExp object
// this is not a string object

// cases in which the regexp global property is false

AddRegExpCases(this, 3, "3",   "1234567890", 1, 2, ["3"] );

// cases in which the regexp object global property is true

AddGlobalRegExpCases(this, /34/g, "/34/g", "343443444",  3, ["34", "34", "34"] );
AddGlobalRegExpCases(this, /\d{1}/g,  "/d{1}/g",  "123456abcde7890", 10,
["1", "2", "3", "4", "5", "6", "7", "8", "9", "0"] );

AddGlobalRegExpCases(this, /\d{2}/g,  "/d{2}/g",  "123456abcde7890", 5,
["12", "34", "56", "78", "90"] );

AddGlobalRegExpCases(this, /\D{2}/g,  "/d{2}/g",  "123456abcde7890", 2,
["ab", "cd"] );

//test();


function AddRegExpCases(obj,
regexp, str_regexp, string, length, index, matches_array ) {

obj.AddTestCase(
"( " + string  + " ).match(" + str_regexp +").length",
length,
string.match(regexp).length );

obj.AddTestCase(
"( " + string + " ).match(" + str_regexp +").index",
index,
string.match(regexp).index );

obj.AddTestCase(
"( " + string + " ).match(" + str_regexp +").input",
string,
string.match(regexp).input );

for ( var matches = 0; matches < matches_array.length; matches++ ) {
obj.AddTestCase(
"( " + string + " ).match(" + str_regexp +")[" + matches +"]",
matches_array[matches],
string.match(regexp)[matches] );
}
}

function AddGlobalRegExpCases(obj,
regexp, str_regexp, string, length, matches_array ) {

obj.AddTestCase(
"( " + string  + " ).match(" + str_regexp +").length",
length,
string.match(regexp).length );

for ( var matches = 0; matches < matches_array.length; matches++ ) {
obj.AddTestCase(
"( " + string + " ).match(" + str_regexp +")[" + matches +"]",
matches_array[matches],
string.match(regexp)[matches] );
}
}

},

/**
*  File Name:          match__002.js
*  ECMA Section:       15.6.4.9
*  Description:        Based on ECMA 2 Draft 7 February 1999
*
*  Author:             christine@netscape.com
*  Date:               19 February 1999
*  String.match( regexp )
*
*  If regexp is not an object of type RegExp, it is replaced with result
*  of the expression new RegExp(regexp). Let string denote the result of
*  converting the this value to a string.  If regexp.global is false,
*  return the result obtained by invoking RegExp.prototype.exec (see
*  section 15.7.5.3) on regexp with string as parameter.
*
*  Otherwise, set the regexp.lastIndex property to 0 and invoke
*  RegExp.prototype.exec repeatedly until there is no match. If there is a
*  match with an empty string (in other words, if the value of
*  regexp.lastIndex is left unchanged) increment regexp.lastIndex by 1.
*  The value returned is an array with the properties 0 through n-1
*  corresponding to the first element of the result of each matching
*  invocation of RegExp.prototype.exec.
*
*  Note that the match function is intentionally generic; it does not
*  require that its this value be a string object.  Therefore, it can be
*  transferred to other kinds of objects for use as a method.
*
*  This file tests cases in which regexp.global is false.  Therefore,
*  results should behave as regexp.exec with string passed as a parameter.
*
*/
test_match__002 : function() {
var SECTION = "match__002";
var VERSION = "ECMA_2";
var TITLE   = "String.prototype.match( regexp )";
var s = null;//<Object

var lim = null;//<Object

var re = null;//Object

var z = null;//<Object
//startTest();

// the regexp argument is not a RegExp object
// this is not a string object

AddRegExpCases(this, /([\d]{5})([-\ ]?[\d]{4})?$/,
"/([\d]{5})([-\ ]?[\d]{4})?$/",
"Boston, Mass. 02134",
14,
["02134", "02134", undefined]);

AddGlobalRegExpCases(this, /([\d]{5})([-\ ]?[\d]{4})?$/g,
"/([\d]{5})([-\ ]?[\d]{4})?$/g",
"Boston, Mass. 02134",
["02134"]);

// set the value of lastIndex
re = /([\d]{5})([-\ ]?[\d]{4})?$/;
re.lastIndex = 0;

s = "Boston, MA 02134";

AddRegExpCases(this, re,
"re = /([\d]{5})([-\ ]?[\d]{4})?$/; re.lastIndex =0",
s,
s.lastIndexOf("0"),
["02134", "02134", undefined]);


re.lastIndex = s.length;

AddRegExpCases(this, re,
"re = /([\d]{5})([-\ ]?[\d]{4})?$/; re.lastIndex = " +
s.length,
s,
s.lastIndexOf("0"),
["02134", "02134", undefined] );

re.lastIndex = s.lastIndexOf("0");

AddRegExpCases(this, re,
"re = /([\d]{5})([-\ ]?[\d]{4})?$/; re.lastIndex = " +
s.lastIndexOf("0"),
s,
s.lastIndexOf("0"),
["02134", "02134", undefined]);

re.lastIndex = s.lastIndexOf("0") + 1;

AddRegExpCases(this, re,
"re = /([\d]{5})([-\ ]?[\d]{4})?$/; re.lastIndex = " +
s.lastIndexOf("0") +1,
s,
s.lastIndexOf("0"),
["02134", "02134", undefined]);

//test();

function AddRegExpCases(obj,
regexp, str_regexp, string, index, matches_array ) {

// prevent a runtime error

if ( regexp.exec(string) == null || matches_array == null ) {
obj.AddTestCase(
string + ".match(" + regexp +")",
matches_array,
string.match(regexp) );

return;
}

obj.AddTestCase(
"( " + string  + " ).match(" + str_regexp +").length",
matches_array.length,
string.match(regexp).length );

obj.AddTestCase(
"( " + string + " ).match(" + str_regexp +").index",
index,
string.match(regexp).index );

obj.AddTestCase(
"( " + string + " ).match(" + str_regexp +").input",
string,
string.match(regexp).input );

var limit = matches_array.length > string.match(regexp).length ?
matches_array.length :
string.match(regexp).length;

for ( var matches = 0; matches < limit; matches++ ) {
obj.AddTestCase(
"( " + string + " ).match(" + str_regexp +")[" + matches +"]",
matches_array[matches],
string.match(regexp)[matches] );
}
}

function AddGlobalRegExpCases(obj,
regexp, str_regexp, string, matches_array ) {

// prevent a runtime error

if ( regexp.exec(string) == null || matches_array == null ) {
obj.AddTestCase(
regexp + ".exec(" + string +")",
matches_array,
regexp.exec(string) );

return;
}

obj.AddTestCase(
"( " + string  + " ).match(" + str_regexp +").length",
matches_array.length,
string.match(regexp).length );

var limit = matches_array.length > string.match(regexp).length ?
matches_array.length :
string.match(regexp).length;

for ( var matches = 0; matches < limit; matches++ ) {
obj.AddTestCase(
"( " + string + " ).match(" + str_regexp +")[" + matches +"]",
matches_array[matches],
string.match(regexp)[matches] );
}
}

},

/**
*  File Name:          match__003.js
*  ECMA Section:       15.6.4.9
*  Description:        Based on ECMA 2 Draft 7 February 1999
*
*  Author:             christine@netscape.com
*  Date:               19 February 1999
*  String.match( regexp )
*
*  If regexp is not an object of type RegExp, it is replaced with result
*  of the expression new RegExp(regexp). Let string denote the result of
*  converting the this value to a string.  If regexp.global is false,
*  return the result obtained by invoking RegExp.prototype.exec (see
*  section 15.7.5.3) on regexp with string as parameter.
*
*  Otherwise, set the regexp.lastIndex property to 0 and invoke
*  RegExp.prototype.exec repeatedly until there is no match. If there is a
*  match with an empty string (in other words, if the value of
*  regexp.lastIndex is left unchanged) increment regexp.lastIndex by 1.
*  The value returned is an array with the properties 0 through n-1
*  corresponding to the first element of the result of each matching
*  invocation of RegExp.prototype.exec.
*
*  Note that the match function is intentionally generic; it does not
*  require that its this value be a string object.  Therefore, it can be
*  transferred to other kinds of objects for use as a method.
*/
test_match__003 : function() {
var SECTION = "match__003";
var VERSION = "ECMA_2";
var TITLE   = "String.prototype.match( regexp )";
var s = null;//<Object

var lim = null;//<Object

var re = null;//Object

var z = null;//<Object
//startTest();

// the regexp argument is not a RegExp object
// this is not a string object


//  [if regexp.global is true] set the regexp.lastIndex property to 0 and
//  invoke RegExp.prototype.exec repeatedly until there is no match. If
//  there is a match with an empty string (in other words, if the value of
//  regexp.lastIndex is left unchanged) increment regexp.lastIndex by 1.
//  The value returned is an array with the properties 0 through n-1
//  corresponding to the first element of the result of each matching invocation
//  of RegExp.prototype.exec.


// set the value of lastIndex
re = /([\d]{5})([-\ ]?[\d]{4})?$/g;


s = "Boston, MA 02134";

AddGlobalRegExpCases(this, re,
"re = " + re,
s,
["02134" ]);

re.lastIndex = 0;

AddGlobalRegExpCases(this,
re,
"re = " + re + "; re.lastIndex = 0 ",
s,
["02134"]);


re.lastIndex = s.length;

AddGlobalRegExpCases(this,
re,
"re = " + re + "; re.lastIndex = " + s.length,
s,
["02134"] );

re.lastIndex = s.lastIndexOf("0");

AddGlobalRegExpCases(this,
re,
"re = "+ re +"; re.lastIndex = " + s.lastIndexOf("0"),
s,
["02134"]);

re.lastIndex = s.lastIndexOf("0") + 1;

AddGlobalRegExpCases(this,
re,
"re = " +re+ "; re.lastIndex = " + (s.lastIndexOf("0") +1),
s,
["02134"]);

//test();

function AddGlobalRegExpCases(obj,
regexp, str_regexp, string, matches_array ) {

// prevent a runtime error

if ( string.match(regexp) == null || matches_array == null ) {
obj.AddTestCase(
string + ".match(" + str_regexp +")",
matches_array,
string.match(regexp) );

return;
}

obj.AddTestCase(
"( " + string  + " ).match(" + str_regexp +").length",
matches_array.length,
string.match(regexp).length );

var limit = matches_array.length > string.match(regexp).length ?
matches_array.length :
string.match(regexp).length;

for ( var matches = 0; matches < limit; matches++ ) {
obj.AddTestCase(
"( " + string + " ).match(" + str_regexp +")[" + matches +"]",
matches_array[matches],
string.match(regexp)[matches] );
}
}

},

/**
*  File Name:         match__004.js
*  ECMA Section:       15.6.4.9
*  Description:        Based on ECMA 2 Draft 7 February 1999
*
*  Author:             christine@netscape.com
*  Date:               19 February 1999
*  String.match( regexp )
*
*  If regexp is not an object of type RegExp, it is replaced with result
*  of the expression new RegExp(regexp). Let string denote the result of
*  converting the this value to a string.  If regexp.global is false,
*  return the result obtained by invoking RegExp.prototype.exec (see
*  section 15.7.5.3) on regexp with string as parameter.
*
*  Otherwise, set the regexp.lastIndex property to 0 and invoke
*  RegExp.prototype.exec repeatedly until there is no match. If there is a
*  match with an empty string (in other words, if the value of
*  regexp.lastIndex is left unchanged) increment regexp.lastIndex by 1.
*  The value returned is an array with the properties 0 through n-1
*  corresponding to the first element of the result of each matching
*  invocation of RegExp.prototype.exec.
*
*  Note that the match function is intentionally generic; it does not
*  require that its this value be a string object.  Therefore, it can be
*  transferred to other kinds of objects for use as a method.
*
*
*  The match function should be intentionally generic, and not require
*  this to be a string.
*/
test_match__004 : function() {
var SECTION = "match__004";
var VERSION = "ECMA_2";
var TITLE   = "String.prototype.match( regexp )";
var s = null;//<Object

var lim = null;//<Object

var re = null;//Object

var z = null;//<Object
//var BUGNUMBER="http://scopus/bugsplat/show_bug.cgi?id=345818";

//startTest();

// set the value of lastIndex
re = /0./;
s = 10203040506070809000;

Number.prototype.match = String.prototype.match;

AddRegExpCases(this,  re,
"re = " + re ,
s,
String(s),
1,
["02"]);


re.lastIndex = 0;
AddRegExpCases(this,  re,
"re = " + re +" [lastIndex is " + re.lastIndex+"]",
s,
String(s),
1,
["02"]);
/*

re.lastIndex = s.length;

AddRegExpCases( re,
"re = /([\d]{5})([-\ ]?[\d]{4})?$/; re.lastIndex = " +
s.length,
s,
s.lastIndexOf("0"),
null );

re.lastIndex = s.lastIndexOf("0");

AddRegExpCases( re,
"re = /([\d]{5})([-\ ]?[\d]{4})?$/; re.lastIndex = " +
s.lastIndexOf("0"),
s,
s.lastIndexOf("0"),
["02134"]);

re.lastIndex = s.lastIndexOf("0") + 1;

AddRegExpCases( re,
"re = /([\d]{5})([-\ ]?[\d]{4})?$/; re.lastIndex = " +
s.lastIndexOf("0") +1,
s,
0,
null);
*/
//test();

function AddRegExpCases(obj,
regexp, str_regexp, string, str_string, index, matches_array ) {

// prevent a runtime error

if ( regexp.exec(string) == null || matches_array == null ) {
obj.AddTestCase(
string + ".match(" + regexp +")",
matches_array,
string.match(regexp) );

return;
}

obj.AddTestCase(
"( " + string  + " ).match(" + str_regexp +").length",
matches_array.length,
string.match(regexp).length );

obj.AddTestCase(
"( " + string + " ).match(" + str_regexp +").index",
index,
string.match(regexp).index );

obj.AddTestCase(
"( " + string + " ).match(" + str_regexp +").input",
str_string,
string.match(regexp).input );

var limit = matches_array.length > string.match(regexp).length ?
matches_array.length :
string.match(regexp).length;

for ( var matches = 0; matches < limit; matches++ ) {
obj.AddTestCase(
"( " + string + " ).match(" + str_regexp +")[" + matches +"]",
matches_array[matches],
string.match(regexp)[matches] );
}
}

function AddGlobalRegExpCases(obj,
regexp, str_regexp, string, matches_array ) {

// prevent a runtime error

if ( regexp.exec(string) == null || matches_array == null ) {
obj.AddTestCase(
regexp + ".exec(" + string +")",
matches_array,
regexp.exec(string) );

return;
}

obj.AddTestCase(
"( " + string  + " ).match(" + str_regexp +").length",
matches_array.length,
string.match(regexp).length );

var limit = matches_array.length > string.match(regexp).length ?
matches_array.length :
string.match(regexp).length;

for ( var matches = 0; matches < limit; matches++ ) {
obj.AddTestCase(
"( " + string + " ).match(" + str_regexp +")[" + matches +"]",
matches_array[matches],
string.match(regexp)[matches] );
}
}

},

/**
*  File Name:        split__001.js
*  ECMA Section:       15.6.4.9
*  Description:        Based on ECMA 2 Draft 7 February 1999
*
*  Author:             christine@netscape.com
*  Date:               19 February 1999
* Since regular expressions have been part of JavaScript since 1.2, there
* are already tests for regular expressions in the js1_2/regexp folder.
*
* These new tests try to supplement the existing tests, and verify that
* our implementation of RegExp conforms to the ECMA specification, but
* does not try to be as exhaustive as in previous tests.
*
* The [,limit] argument to String.split is new, and not covered in any
* existing tests.
*
* String.split cases are covered in ecma/String/15.5.4.8-*.js.
* String.split where separator is a RegExp are in
* js1_2/regexp/string_split.js
*/
test_split__001 : function() {
var SECTION = "split__001";
var VERSION = "ECMA_2";
var TITLE   = "String.prototype.split( regexp, [,limit] )";

//startTest();

// the separator is not supplied
// separator is undefined
// separator is an empty string

AddSplitCases(this, "splitme", "", "''", ["s", "p", "l", "i", "t", "m", "e"] );
AddSplitCases(this, "splitme", new RegExp(), "new RegExp()", ["s", "p", "l", "i", "t", "m", "e"] );

// separartor is a regexp
// separator regexp value global setting is set
// string is an empty string
// if separator is an empty string, split each by character

// this is not a String object

// limit is not a number
// limit is undefined
// limit is larger than 2^32-1
// limit is a negative number

//test();

function AddSplitCases(obj, string, separator, str_sep, split_array ) {

// verify that the result of split is an object of type Array
obj.AddTestCase(
"( " + string  + " ).split(" + str_sep +").constructor == Array",
true,
string.split(separator).constructor == Array );

// check the number of items in the array
obj.AddTestCase(
"( " + string  + " ).split(" + str_sep +").length",
split_array.length,
string.split(separator).length );

// check the value of each array item
var limit = (split_array.length > string.split(separator).length )
? split_array.length : string.split(separator).length;

for ( var matches = 0; matches < split_array.length; matches++ ) {
obj.AddTestCase(
"( " + string + " ).split(" + str_sep +")[" + matches +"]",
split_array[matches],
string.split( separator )[matches] );
}
}

function AddLimitedSplitCases(obj,
string, separator, str_sep, limit, str_limit, split_array ) {

// verify that the result of split is an object of type Array

obj.AddTestCase(
"( " + string  + " ).split(" + str_sep +", " + str_limit +
" ).constructor == Array",
true,
string.split(separator, limit).constructor == Array );

// check the length of the array

obj.AddTestCase(
"( " + string + " ).split(" + str_sep  +", " + str_limit + " ).length",
length,
string.split(separator).length );

// check the value of each array item

for ( var matches = 0; matches < split_array.length; matches++ ) {
obj.AddTestCase(
"( " + string + " ).split(" + str_sep +", " + str_limit + " )[" + matches +"]",
split_array[matches],
string.split( separator )[matches] );
}
}

},

/**
*  File Name:          split__002.js
*  ECMA Section:       15.6.4.9
*  Description:        Based on ECMA 2 Draft 7 February 1999
*
*  Author:             christine@netscape.com
*  Date:               19 February 1999
* Since regular expressions have been part of JavaScript since 1.2, there
* are already tests for regular expressions in the js1_2/regexp folder.
*
* These new tests try to supplement the existing tests, and verify that
* our implementation of RegExp conforms to the ECMA specification, but
* does not try to be as exhaustive as in previous tests.
*
* The [,limit] argument to String.split is new, and not covered in any
* existing tests.
*
* String.split cases are covered in ecma/String/15.5.4.8-*.js.
* String.split where separator is a RegExp are in
* js1_2/regexp/string_split.js
*/
test_split__002 : function() {
var SECTION = "split__002";
var VERSION = "ECMA_2";
var TITLE   = "String.prototype.split( regexp, [,limit] )";
var s = null;//<Object
var lim = null;//<Object
var re = null;//Object
var z = null;//<Object
var split_1 = null;
var split_2 = null;
var a = null;
var i = null;
var e = null;
var T = null;
var R = null;
var cap = null;

//startTest();

// the separator is not supplied
// separator is undefined
// separator is an empty string

//    AddSplitCases( "splitme", "", "''", ["s", "p", "l", "i", "t", "m", "e"] );
//    AddSplitCases( "splitme", new RegExp(), "new RegExp()", ["s", "p", "l", "i", "t", "m", "e"] );

// separator is an empty regexp
// separator is not supplied

CompareSplit(this, "hello", "ll" );

CompareSplit(this, "hello", "l" );
CompareSplit(this, "hello", "x" );
CompareSplit(this, "hello", "h" );
CompareSplit(this, "hello", "o" );
CompareSplit(this, "hello", "hello" );
CompareSplit(this, "hello", undefined );

CompareSplit(this, "hello", "");
CompareSplit(this, "hello", "hellothere" );

CompareSplit(this, new String("hello" ), "" );


Number.prototype.split = String.prototype.split;

CompareSplit(this, new Number(100111122133144155), 1 );
CompareSplitWithLimit(this,new Number(100111122133144155), 1, 1 );

CompareSplitWithLimit(this,new Number(100111122133144155), 1, 2 );
CompareSplitWithLimit(this,new Number(100111122133144155), 1, 0 );
CompareSplitWithLimit(this,new Number(100111122133144155), 1, 100 );
CompareSplitWithLimit(this,new Number(100111122133144155), 1, void 0 );
CompareSplitWithLimit(this,new Number(100111122133144155), 1, Math.pow(2,32)-1 );
CompareSplitWithLimit(this,new Number(100111122133144155), 1, "boo" );
CompareSplitWithLimit(this,new Number(100111122133144155), 1, -(Math.pow(2,32)-1) );
CompareSplitWithLimit(this, "hello", "l", NaN );
CompareSplitWithLimit(this, "hello", "l", 0 );
CompareSplitWithLimit(this, "hello", "l", 1 );
CompareSplitWithLimit(this, "hello", "l", 2 );
CompareSplitWithLimit(this, "hello", "l", 3 );
CompareSplitWithLimit(this, "hello", "l", 4 );


/*
CompareSplitWithLimit( "hello", "ll", 0 );
CompareSplitWithLimit( "hello", "ll", 1 );
CompareSplitWithLimit( "hello", "ll", 2 );
CompareSplit( "", " " );
CompareSplit( "" );
*/

// separartor is a regexp
// separator regexp value global setting is set
// string is an empty string
// if separator is an empty string, split each by character

// this is not a String object

// limit is not a number
// limit is undefined
// limit is larger than 2^32-1
// limit is a negative number

//test();

function CompareSplit(obj, string, separator ) {
split_1 = string.split( separator );
split_2 = string_split( string, separator, 64000 );

obj.AddTestCase(
"( " + string +".split(" + separator + ") ).length" ,
split_2.length,
split_1.length );

var limit = split_1.length > split_2.length ?
split_1.length : split_2.length;

for ( var split_item = 0; split_item < limit; split_item++ ) {
obj.AddTestCase(
string + ".split(" + separator + ")["+split_item+"]",
split_2[split_item],
split_1[split_item] );
}
}

function CompareSplitWithLimit(obj, string, separator, splitlimit ) {
split_1 = string.split( separator, splitlimit );
split_2 = string_split( string, separator, splitlimit );

obj.AddTestCase(
"( " + string +".split(" + separator + ", " + splitlimit+") ).length" ,
split_2.length,
split_1.length );

var limit = split_1.length > split_2.length ?
split_1.length : split_2.length;

for ( var split_item = 0; split_item < limit; split_item++ ) {
obj.AddTestCase(
string + ".split(" + separator  + ", " + splitlimit+")["+split_item+"]",
split_2[split_item],
split_1[split_item] );
}
}

function string_split ( __this, separator, limit ) {
var S = String(__this );					  // 1

var A = new Array();                          // 2

if ( limit == undefined ) {                   // 3
lim = Math.pow(2, 31 ) -1;
} else {
lim = ToUint32( limit );
}

var s = S.length;                              // 4
var p = 0;                                     // 5

if  ( separator == undefined ) {              // 8
A[0] = S;
return A;
}

if ( separator.constructor == RegExp )         // 6
R = separator;
else
R = separator.toString();

if (lim == 0) return A;                       // 7

if  ( separator == undefined ) {              // 8
A[0] = S;
return A;
}

if (s == 0) {		                          // 9
z = SplitMatch(R, S, 0);
if (z != false) return A;
A[0] = S;
return A;
}

var q = p;									  // 10
loop:
while (true ) {

if ( q == s ) break;					  // 11

z = SplitMatch(R, S, q);                  // 12

//print("Returned ", z);

if (z != false) {							// 13
e = z.endIndex;							// 14
cap = z.captures;						// 14
if (e != p) {							// 15
//print("S = ", S, ", p = ", p, ", q = ", q);
T = S.slice(p, q);					// 16
//print("T = ", T);
A[A.length] = T;					// 17
if (A.length == lim) return A;		// 18
p = e;								// 19
i = 0;								// 20
while (true) {						// 25
if (i == cap.length) {              // 21
q = p;                          // 10
continue loop;
}
i = i + 1;							// 22
A[A.length] = cap[i]				// 23
if (A.length == lim) return A;		// 24
}
}
}

q = q + 1;                               // 26
}

T = S.slice(p, q);
A[A.length] = T;
return A;
}

function SplitMatch(R, S, q)
{
if (R.constructor == RegExp) {			// 1
var reResult = R.match(S, q);		// 8
if (reResult == undefined)
return false;
else {
a = new Array(reResult.length - 1);
for (var i = 1; i < reResult.length; i++)
a[a.length] = reResult[i];
return { endIndex : reResult.index + reResult[0].length, captures : cap };
}
}
else {
var r = R.length;					// 2
s = S.length;						// 3
if ((q + r) > s) return false;		// 4
for (var i = 0; i < r; i++) {
//print("S.charAt(", q + i, ") = ", S.charAt(q + i), ", R.charAt(", i, ") = ", R.charAt(i));
if (S.charAt(q + i) != R.charAt(i))			// 5
return false;
}
cap = new Array();								// 6
return { endIndex : q + r, captures : cap };	// 7
}
}

function ToUint32( n ) {
n = Number( n );
var sign = ( n < 0 ) ? -1 : 1;

if ( Math.abs( n ) == 0
|| Math.abs( n ) == Number.POSITIVE_INFINITY
|| n != n) {
return 0;
}
n = sign * Math.floor( Math.abs(n) )

n = n % Math.pow(2,32);

if ( n < 0 ){
n += Math.pow(2,32);
}

return ( n );
}

},

/**
*  File Name:          split__003.js
*  ECMA Section:       15.6.4.9
*  Description:        Based on ECMA 2 Draft 7 February 1999
*
*  Author:             christine@netscape.com
*  Date:               19 February 1999
* Since regular expressions have been part of JavaScript since 1.2, there
* are already tests for regular expressions in the js1_2/regexp folder.
*
* These new tests try to supplement the existing tests, and verify that
* our implementation of RegExp conforms to the ECMA specification, but
* does not try to be as exhaustive as in previous tests.
*
* The [,limit] argument to String.split is new, and not covered in any
* existing tests.
*
* String.split cases are covered in ecma/String/15.5.4.8-*.js.
* String.split where separator is a RegExp are in
* js1_2/regexp/string_split.js
*/
test_split__003 : function() {
var SECTION = "split__003";
var VERSION = "ECMA_2";
var TITLE   = "String.prototype.split( regexp, [,limit] )";

//startTest();

// separator is a regexp
// separator regexp value global setting is set
// string is an empty string
// if separator is an empty string, split each by character


AddSplitCases(this, "hello", new RegExp, "new RegExp", ["h","e","l","l","o"] );

AddSplitCases(this, "hello", /l/, "/l/", ["he","","o"] );
AddLimitedSplitCases(this, "hello", /l/, "/l/", 0, [] );
AddLimitedSplitCases(this, "hello", /l/, "/l/", 1, ["he"] );
AddLimitedSplitCases(this, "hello", /l/, "/l/", 2, ["he",""] );
AddLimitedSplitCases(this, "hello", /l/, "/l/", 3, ["he","","o"] );
AddLimitedSplitCases(this, "hello", /l/, "/l/", 4, ["he","","o"] );
AddLimitedSplitCases(this, "hello", /l/, "/l/", void 0, ["he","","o"] );
AddLimitedSplitCases(this, "hello", /l/, "/l/", "hi", [] );
AddLimitedSplitCases(this, "hello", /l/, "/l/", undefined, ["he","","o"] );

AddSplitCases(this, "hello", new RegExp, "new RegExp", ["h","e","l","l","o"] );
AddLimitedSplitCases(this, "hello", new RegExp, "new RegExp", 0, [] );
AddLimitedSplitCases(this, "hello", new RegExp, "new RegExp", 1, ["h"] );
AddLimitedSplitCases(this, "hello", new RegExp, "new RegExp", 2, ["h","e"] );
AddLimitedSplitCases(this, "hello", new RegExp, "new RegExp", 3, ["h","e","l"] );
AddLimitedSplitCases(this, "hello", new RegExp, "new RegExp", 4, ["h","e","l","l"] );
AddLimitedSplitCases(this, "hello", new RegExp, "new RegExp", void 0,  ["h","e","l","l","o"] );
AddLimitedSplitCases(this, "hello", new RegExp, "new RegExp", "hi",  [] );
AddLimitedSplitCases(this, "hello", new RegExp, "new RegExp", undefined,  ["h","e","l","l","o"] );

//test();

function AddSplitCases(obj, string, separator, str_sep, split_array ) {
// verify that the result of split is an object of type Array
obj.AddTestCase(
"( " + string  + " ).split(" + str_sep +").constructor == Array",
true,
string.split(separator).constructor == Array );

// check the number of items in the array
obj.AddTestCase(
"( " + string  + " ).split(" + str_sep +").length",
split_array.length,
string.split(separator).length );

// check the value of each array item
var limit = (split_array.length > string.split(separator).length )
? split_array.length : string.split(separator).length;

for ( var matches = 0; matches < split_array.length; matches++ ) {
obj.AddTestCase(
"( " + string + " ).split(" + str_sep +")[" + matches +"]",
split_array[matches],
string.split( separator )[matches] );
}
}

function AddLimitedSplitCases(obj,
string, separator, str_sep, limit, split_array ) {

// verify that the result of split is an object of type Array

obj.AddTestCase(
"( " + string  + " ).split(" + str_sep +", " + limit +
" ).constructor == Array",
true,
string.split(separator, limit).constructor == Array );

// check the length of the array

obj.AddTestCase(
"( " + string + " ).split(" + str_sep  +", " + limit + " ).length",
split_array.length,
string.split(separator, limit).length );

// check the value of each array item

var slimit = (split_array.length > string.split(separator).length )
? split_array.length : string.split(separator, limit).length;

for ( var matches = 0; matches < slimit; matches++ ) {
obj.AddTestCase(
"( " + string + " ).split(" + str_sep +", " + limit + " )[" + matches +"]",
split_array[matches],
string.split( separator, limit )[matches] );
}
}

}

})
.endType();

