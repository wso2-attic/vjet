vjo.ctype("dsf.jslang.feature.tests.Ecma2ExpressionsTests")
.inherits("dsf.jslang.feature.tests.BaseTestEcma2")
.protos({

constructs: function() {
this.base();
},

/**
*  File Name:          StrictEquality-001.js
*  ECMA Section:       11.9.6.js
*  Description:
*
*  Author:             christine@netscape.com
*  Date:               4 september 1998
*/
test_strictEquality__001: function() {
var SECTION = "StrictEquality-001 - 11.9.6";
var VERSION = "ECMA_2";
var TITLE   =  "The strict equality operator ( === )";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);


// 1. If Type(x) is different from Type(y) return false

this.StrictEquality( true, new Boolean(true), false);
this.StrictEquality( new Boolean(), false, false );
this.StrictEquality( "", new String(),    false );
this.StrictEquality( new String("hi"), "hi", false );

// 2. If Type(x) is not Number go to step 9.

// 3. If x is NaN, return false
this.StrictEquality( NaN, NaN,   false );
this.StrictEquality( NaN, 0,     false );

// 4. If y is NaN, return false.
this.StrictEquality( 0,  NaN,    false );

// 5. if x is the same number value as y, return true

// 6. If x is +0 and y is -0, return true

// 7. If x is -0 and y is +0, return true

// 8. Return false.


// 9.  If Type(x) is String, then return true if x and y are exactly
//  the same sequence of characters ( same length and same characters
//  in corresponding positions.) Otherwise return false.

//  10. If Type(x) is Boolean, return true if x and y are both true or
//  both false. otherwise return false.


//  Return true if x and y refer to the same object.  Otherwise return
//  false.

// Return false.


//test();

/* see BaseTest.js
function StrictEquality( x, y, expect ) {
result = ( x === y );

this.TestCase(
SECTION,
x +" === " + y,
expect,
result );
}
*/

}

})
.endType();

