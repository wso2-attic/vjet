vjo.ctype("dsf.jslang.feature.tests.Ecma2FunctionObjectsTests")
.inherits("dsf.jslang.feature.tests.BaseTestEcma2")
.protos({

constructs: function() {
this.base();
},

test_apply__001__n: function() {

//print("STATUS: f.apply crash test.");

//print("BUGNUMBER: 21836");

function f ()
{
}

var SECTION = "apply-001-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "f.apply(2,2) doesn't crash";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "f.apply(2,2) doesn't crash";
EXPECTED = "error";

this.TestCase( SECTION,  "f.apply(2,2) doesn't crash",     "error",    eval("f.apply(2,2)") );

//test();

},

test_apply__001__n_WORKS: function() {

//print("STATUS: f.apply crash test.");

//print("BUGNUMBER: 21836");

function f ()
{
}

var SECTION = "apply-001-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "f.apply(2,2) doesn't crash";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "f.apply(2,2) doesn't crash";
EXPECTED = "error";
var fail = null;
var assertTrue = null;
try {
this.TestCase( SECTION,  "f.apply(2,2) doesn't crash",     "error",    eval("f.apply(2,2)") );
fail();
} catch (e) {
assertTrue(e.name == 'TypeError');
assertTrue(e.message == 'second argument to Function.prototype.apply must be an array');
}

//test();

},

/**
File Name:          call-1.js
Section:            Function.prototype.call
Description:


Author:             christine@netscape.com
Date:               12 november 1997
*/
test_call__1: function() {
var SECTION = "call-1";
var VERSION = "ECMA_2";
var TITLE   = "Function.prototype.call";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

var GLOBAL = this + '';
this.TestCase( SECTION,
"ToString.call( this, this )",
GLOBAL,
ToString.call( this, this ) );

this.TestCase( SECTION,
"ToString.call( Boolean, Boolean.prototype )",
"false",
ToString.call( Boolean, Boolean.prototype ) );

this.TestCase( SECTION,
"ToString.call( Boolean, Boolean.prototype.valueOf() )",
"false",
ToString.call( Boolean, Boolean.prototype.valueOf() ) );

//test();

function ToString( obj ) {
return obj +"";
}

}

})
.endType();

