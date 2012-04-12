vjo.ctype("dsf.jslang.feature.tests.Ecma2LexicalConventionsTests")
.inherits("dsf.jslang.feature.tests.BaseTestEcma2")
.protos({

constructs: function() {
this.base();
},

/**
*  File Name:
*  ECMA Section:
*  Description:
*
*
*  Author:             christine@netscape.com
*  Date:               11 August 1998
*/
test_keywords__1: function() {
var SECTION = "";
var VERSION = "ECMA_2";
var TITLE   = "Keywords";

//startTest();

//print("This test requires option javascript.options.strict enabled");
var options = null;
if (!options().match(/strict/))
{
options('strict');
}
if (!options().match(/werror/))
{
options('werror');
}

var result = "failed";

try {
eval("super;");
}
catch (x) {
if (x instanceof SyntaxError)
result = x.name;
}

this.TestCase(
"using the expression \"super\" shouldn't cause js to crash",
"SyntaxError",
result );

//test();
},

/**
*  File Name:          LexicalConventions/regexp-literals-001.js
*  ECMA Section:       7.8.5
*  Description:
*
*
*  Author:             christine@netscape.com
*  Date:               11 August 1998
*/
test_regexp__literals__001: function() {
var SECTION = "LexicalConventions/regexp-literals-001.js";
var VERSION = "ECMA_2";
var TITLE   = "Regular Expression Literals";

//startTest();

// Regular Expression Literals may not be empty; // should be regarded
// as a comment, not a RegExp literal.

s = //;

"passed";

this.TestCase(
"// should be a comment, not a regular expression literal",
"passed",
String(s));

this.TestCase(
"// typeof object should be type of object declared on following line",
"passed",
(typeof s) == "string" ? "passed" : "failed" );

this.TestCase(
"// should not return an object of the type RegExp",
"passed",
(typeof s == "object") ? "failed" : "passed" );

//test();

},

/**
*  File Name:          LexicalConventions/regexp-literals-002.js
*  ECMA Section:       7.8.5
*  Description:        Based on ECMA 2 Draft 8 October 1999
*
*  Author:             christine@netscape.com
*  Date:               19 February 1999
*/
test_regexp__literals__002: function() {
var SECTION = "LexicalConventions/regexp-literals-002.js";
var VERSION = "ECMA_2";
var TITLE   = "Regular Expression Literals";

//startTest();

// A regular expression literal represents an object of type RegExp.

this.TestCase(
"// A regular expression literal represents an object of type RegExp.",
"true",
(/x*/ instanceof RegExp).toString() );

//test();

}



})
.endType();

