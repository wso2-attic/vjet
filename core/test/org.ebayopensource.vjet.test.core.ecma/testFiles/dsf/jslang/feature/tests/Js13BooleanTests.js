vjo.ctype("dsf.jslang.feature.tests.Js13BooleanTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({
test_boolean__001:function(){


/**
*  File Name:          boolean-001.js
*  Description:
*
*  In JavaScript 1.2, new Boolean(false) evaluates to false.
*
*  Author:             christine@netscape.com
*  Date:               11 August 1998
*/
var SECTION = "boolean-001.js";
var VERSION = "JS_1.3";
var TITLE   = "new Boolean(false) should evaluate to false";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

BooleanTest( "new Boolean(true)",  new Boolean(true),  true );
BooleanTest( "new Boolean(false)", new Boolean(false), true );
BooleanTest( "true",               true,               true );
BooleanTest( "false",              false,              false );


function BooleanTest( string, object, expect ) {
if ( object ) {
var result = true;
} else {
result = false;
}

new this.vj$.BaseTest().TestCase(
SECTION,
string,
expect,
result );
}


}
}).endType()
