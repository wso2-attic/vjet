vjo.ctype("dsf.jslang.feature.tests.Js14FunctionsTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({
test_function__001:function(){


/**
*  File Name:          function-001.js
*  Description:
*
* http://scopus.mcom.com/bugsplat/show_bug.cgi?id=324455
*
*  Earlier versions of JavaScript supported access to the arguments property
*  of the function object. This property held the arguments to the function.
*  function f() {
*      return f.arguments[0];    // deprecated
*  }
*  var x = f(3);    // x will be 3
*
* This feature is not a part of the final ECMA standard. Instead, scripts
* should simply use just "arguments":
*
* function f() {
*    return arguments[0];    // okay
* }
*
* var x = f(3);    // x will be 3
*
* Again, this feature was motivated by performance concerns. Access to the
* arguments property is not threadsafe, which is of particular concern in
* server environments. Also, the compiler can generate better code for
* functions because it can tell when the arguments are being accessed only by
* name and avoid setting up the arguments object.
*
*  Author:             christine@netscape.com
*  Date:               11 August 1998
*/
var SECTION = "function-001.js";
var VERSION = "JS1_4";
var TITLE   = "Accessing the arguments property of a function object";
var BUGNUMBER="324455";
// start//test();
// writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase(
SECTION,
"return function.arguments",
"P",
TestFunction_2("P", "A","S","S")[0] +"");


this.TestCase(
SECTION,
"return arguments",
"P",
TestFunction_1( "P", "A", "S", "S" )[0] +"");

this.TestCase(
SECTION,
"return arguments when function contains an arguments property",
"PASS",
TestFunction_3( "P", "A", "S", "S" ) +"");

this.TestCase(
SECTION,
"return function.arguments when function contains an arguments property",
"PASS",
TestFunction_4( "F", "A", "I", "L" ) +"");

//test();

function TestFunction_1( a, b, c, d ) {
return arguments;
}

function TestFunction_2( a, b, c, d ) {
return TestFunction_2.arguments;
}

function TestFunction_3( a, b, c, d ) {
var arguments = "PASS";
return arguments;
}

function TestFunction_4( a, b, c, d ) {
var arguments = "PASS";
return TestFunction_4.arguments;
}


}
}).endType()
