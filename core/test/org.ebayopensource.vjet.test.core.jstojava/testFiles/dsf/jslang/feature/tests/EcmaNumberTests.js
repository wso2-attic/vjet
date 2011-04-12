vjo.ctype("dsf.jslang.feature.tests.EcmaNumberTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({

constructs: function() {
this.base();
},

//public void assertTrue(Object o1)
assertTrue : function( o1){
},

fail :function(){
},
/**
File Name:          15.7.1.js
ECMA Section:       15.7.1 The Number Constructor Called as a Function
15.7.1.1
15.7.1.2

Description:        When Number is called as a function rather than as a
constructor, it performs a type conversion.
15.7.1.1    Return a number value (not a Number object)
computed by ToNumber( value )
15.7.1.2    Number() returns 0.

need to add more test cases.  see the gTestcases for
TypeConversion ToNumber.

Author:             christine@netscape.com
Date:               29 september 1997
*/
test_15_7_1: function() {
var SECTION = "15.7.1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The Number Constructor Called as a Function";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase(SECTION, "Number()",                  0,          Number() );
this.TestCase(SECTION, "Number(void 0)",            Number.NaN,  Number(void 0) );
this.TestCase(SECTION, "Number(null)",              0,          Number(null) );
this.TestCase(SECTION, "Number()",                  0,          Number() );
this.TestCase(SECTION, "Number(new Number())",      0,          Number( new Number() ) );
this.TestCase(SECTION, "Number(0)",                 0,          Number(0) );
this.TestCase(SECTION, "Number(1)",                 1,          Number(1) );
this.TestCase(SECTION, "Number(-1)",                -1,         Number(-1) );
this.TestCase(SECTION, "Number(NaN)",               Number.NaN, Number( Number.NaN ) );
this.TestCase(SECTION, "Number('string')",          Number.NaN, Number( "string") );
this.TestCase(SECTION, "Number(new String())",      0,          Number( new String() ) );
this.TestCase(SECTION, "Number('')",                0,          Number( "" ) );
this.TestCase(SECTION, "Number(Infinity)",          Number.POSITIVE_INFINITY,   Number("Infinity") );

//test();

function MyObject( value ) {
this.value = value;
this.valueOf = new Function( "return this.value" );
}

},

/**
File Name:          15.7.2.js
ECMA Section:       15.7.2 The Number Constructor
15.7.2.1
15.7.2.2

Description:        15.7.2 When Number is called as part of a new
expression, it is a constructor:  it initializes
the newly created object.

15.7.2.1 The [[Prototype]] property of the newly
constructed object is set to othe original Number
prototype object, the one that is the initial value
of Number.prototype(0).  The [[Class]] property is
set to "Number".  The [[Value]] property of the
newly constructed object is set to ToNumber(value)

15.7.2.2 new Number().  same as in 15.7.2.1, except
the [[Value]] property is set to +0.

need to add more test cases.  see the gTestcases for
TypeConversion ToNumber.

Author:             christine@netscape.com
Date:               29 september 1997
*/
test_15_7_2: function() {
var SECTION = "15.7.2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The Number Constructor";

//writeHeaderToLog( SECTION + " "+ TITLE);

//  To verify that the object's prototype is the Number.prototype, check to see if the object's
//  constructor property is the same as Number.prototype.constructor.

this.TestCase(SECTION, "(new Number()).constructor",      Number.prototype.constructor,   (new Number()).constructor );

this.TestCase(SECTION, "typeof (new Number())",         "object",           typeof (new Number()) );
this.TestCase(SECTION,  "(new Number()).valueOf()",     0,                   (new Number()).valueOf() );
this.TestCase(SECTION,
"NUMB = new Number();NUMB.toString=Object.prototype.toString;NUMB.toString()",
"[object Number]",
eval("NUMB = new Number();NUMB.toString=Object.prototype.toString;NUMB.toString()") );

this.TestCase(SECTION, "(new Number(0)).constructor",     Number.prototype.constructor,    (new Number(0)).constructor );
this.TestCase(SECTION, "typeof (new Number(0))",         "object",           typeof (new Number(0)) );
this.TestCase(SECTION,  "(new Number(0)).valueOf()",     0,                   (new Number(0)).valueOf() );
this.TestCase(SECTION,
"NUMB = new Number(0);NUMB.toString=Object.prototype.toString;NUMB.toString()",
"[object Number]",
eval("NUMB = new Number(0);NUMB.toString=Object.prototype.toString;NUMB.toString()") );

this.TestCase(SECTION, "(new Number(1)).constructor",     Number.prototype.constructor,    (new Number(1)).constructor );
this.TestCase(SECTION, "typeof (new Number(1))",         "object",           typeof (new Number(1)) );
this.TestCase(SECTION,  "(new Number(1)).valueOf()",     1,                   (new Number(1)).valueOf() );
this.TestCase(SECTION,
"NUMB = new Number(1);NUMB.toString=Object.prototype.toString;NUMB.toString()",
"[object Number]",
eval("NUMB = new Number(1);NUMB.toString=Object.prototype.toString;NUMB.toString()") );

this.TestCase(SECTION, "(new Number(-1)).constructor",     Number.prototype.constructor,    (new Number(-1)).constructor );
this.TestCase(SECTION, "typeof (new Number(-1))",         "object",           typeof (new Number(-1)) );
this.TestCase(SECTION,  "(new Number(-1)).valueOf()",     -1,                   (new Number(-1)).valueOf() );
this.TestCase(SECTION,
"NUMB = new Number(-1);NUMB.toString=Object.prototype.toString;NUMB.toString()",
"[object Number]",
eval("NUMB = new Number(-1);NUMB.toString=Object.prototype.toString;NUMB.toString()") );

this.TestCase(SECTION, "(new Number(Number.NaN)).constructor",     Number.prototype.constructor,    (new Number(Number.NaN)).constructor );
this.TestCase(SECTION, "typeof (new Number(Number.NaN))",         "object",           typeof (new Number(Number.NaN)) );
this.TestCase(SECTION,  "(new Number(Number.NaN)).valueOf()",     Number.NaN,                   (new Number(Number.NaN)).valueOf() );
this.TestCase(SECTION,
"NUMB = new Number(Number.NaN);NUMB.toString=Object.prototype.toString;NUMB.toString()",
"[object Number]",
eval("NUMB = new Number(Number.NaN);NUMB.toString=Object.prototype.toString;NUMB.toString()") );

this.TestCase(SECTION, "(new Number('string')).constructor",     Number.prototype.constructor,    (new Number('string')).constructor );
this.TestCase(SECTION, "typeof (new Number('string'))",         "object",           typeof (new Number('string')) );
this.TestCase(SECTION,  "(new Number('string')).valueOf()",     Number.NaN,                   (new Number('string')).valueOf() );
this.TestCase(SECTION,
"NUMB = new Number('string');NUMB.toString=Object.prototype.toString;NUMB.toString()",
"[object Number]",
eval("NUMB = new Number('string');NUMB.toString=Object.prototype.toString;NUMB.toString()") );

this.TestCase(SECTION, "(new Number(new String())).constructor",     Number.prototype.constructor,    (new Number(new String())).constructor );
this.TestCase(SECTION, "typeof (new Number(new String()))",         "object",           typeof (new Number(new String())) );
this.TestCase(SECTION,  "(new Number(new String())).valueOf()",     0,                   (new Number(new String())).valueOf() );
this.TestCase(SECTION,
"NUMB = new Number(new String());NUMB.toString=Object.prototype.toString;NUMB.toString()",
"[object Number]",
eval("NUMB = new Number(new String());NUMB.toString=Object.prototype.toString;NUMB.toString()") );

this.TestCase(SECTION, "(new Number('')).constructor",     Number.prototype.constructor,    (new Number('')).constructor );
this.TestCase(SECTION, "typeof (new Number(''))",         "object",           typeof (new Number('')) );
this.TestCase(SECTION,  "(new Number('')).valueOf()",     0,                   (new Number('')).valueOf() );
this.TestCase(SECTION,
"NUMB = new Number('');NUMB.toString=Object.prototype.toString;NUMB.toString()",
"[object Number]",
eval("NUMB = new Number('');NUMB.toString=Object.prototype.toString;NUMB.toString()") );

this.TestCase(SECTION, "(new Number(Number.POSITIVE_INFINITY)).constructor",     Number.prototype.constructor,    (new Number(Number.POSITIVE_INFINITY)).constructor );
this.TestCase(SECTION, "typeof (new Number(Number.POSITIVE_INFINITY))",         "object",           typeof (new Number(Number.POSITIVE_INFINITY)) );
this.TestCase(SECTION,  "(new Number(Number.POSITIVE_INFINITY)).valueOf()",     Number.POSITIVE_INFINITY,    (new Number(Number.POSITIVE_INFINITY)).valueOf() );
this.TestCase(SECTION,
"NUMB = new Number(Number.POSITIVE_INFINITY);NUMB.toString=Object.prototype.toString;NUMB.toString()",
"[object Number]",
eval("NUMB = new Number(Number.POSITIVE_INFINITY);NUMB.toString=Object.prototype.toString;NUMB.toString()") );

this.TestCase(SECTION, "(new Number(Number.NEGATIVE_INFINITY)).constructor",     Number.prototype.constructor,    (new Number(Number.NEGATIVE_INFINITY)).constructor );
this.TestCase(SECTION, "typeof (new Number(Number.NEGATIVE_INFINITY))",         "object",           typeof (new Number(Number.NEGATIVE_INFINITY)) );
this.TestCase(SECTION,  "(new Number(Number.NEGATIVE_INFINITY)).valueOf()",     Number.NEGATIVE_INFINITY,                   (new Number(Number.NEGATIVE_INFINITY)).valueOf() );
this.TestCase(SECTION,
"NUMB = new Number(Number.NEGATIVE_INFINITY);NUMB.toString=Object.prototype.toString;NUMB.toString()",
"[object Number]",
eval("NUMB = new Number(Number.NEGATIVE_INFINITY);NUMB.toString=Object.prototype.toString;NUMB.toString()") );


this.TestCase(SECTION, "(new Number()).constructor",     Number.prototype.constructor,    (new Number()).constructor );
this.TestCase(SECTION, "typeof (new Number())",         "object",           typeof (new Number()) );
this.TestCase(SECTION,  "(new Number()).valueOf()",     0,                   (new Number()).valueOf() );
this.TestCase(SECTION,
"NUMB = new Number();NUMB.toString=Object.prototype.toString;NUMB.toString()",
"[object Number]",
eval("NUMB = new Number();NUMB.toString=Object.prototype.toString;NUMB.toString()") );

//test();

},

/**
File Name:          15.7.3.1-2.js
ECMA Section:       15.7.3.1 Number.prototype
Description:        All value properties of the Number object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the DontDelete attribute of Number.prototype

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_3_1__1: function() {
var SECTION = "15.7.3.1-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Number.prototype";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase(SECTION,
"var NUM_PROT = Number.prototype; delete( Number.prototype ); NUM_PROT == Number.prototype",
true,
eval("var NUM_PROT = Number.prototype; delete( Number.prototype ); NUM_PROT == Number.prototype") );

this.TestCase(SECTION,
"delete( Number.prototype )",
false,
eval("delete( Number.prototype )") );

//test();

},

/**
File Name:          15.7.3.1-2.js
ECMA Section:       15.7.3.1 Number.prototype
Description:        All value properties of the Number object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the ReadOnly attribute of Number.prototype

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_3_1__2: function() {
var SECTION = "15.7.3.1-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Number.prototype";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase(   SECTION,
"var NUM_PROT = Number.prototype; Number.prototype = null; Number.prototype == NUM_PROT",
true,
eval("var NUM_PROT = Number.prototype; Number.prototype = null; Number.prototype == NUM_PROT") );

this.TestCase(   SECTION,
"Number.prototype=0; Number.prototype",
Number.prototype,
eval("Number.prototype=0; Number.prototype") );

//test();

},

/**
File Name:          15.7.3.1-4.js
ECMA Section:       15.7.3.1 Number.prototype
Description:        All value properties of the Number object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the DontEnum attribute of Number.prototype

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_3_1__3: function() {
var VERSION = "ECMA_1";
//startTest();
var SECTION = "15.7.3.1-3";
var TITLE   = "Number.prototype";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase(
SECTION,
"var string = ''; for ( prop in Number ) { string += ( prop == 'prototype' ) ? prop: '' } string;",
"",
eval("var string = ''; for ( prop in Number ) { string += ( prop == 'prototype' ) ? prop : '' } string;")
);

//test();

},

/**
File Name:          15.7.3.2-1.js
ECMA Section:       15.7.3.2 Number.MAX_VALUE
Description:        All value properties of the Number object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the value of MAX_VALUE

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_3_2__1: function() {
var SECTION = "15.7.3.2-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE =  "Number.MAX_VALUE";

//writeHeaderToLog( SECTION + " Number.prototype.toString()");

this.TestCase( SECTION,
"Number.MAX_VALUE",
1.7976931348623157e308,
Number.MAX_VALUE );

//test();

},

/**
File Name:          15.7.3.2-2.js
ECMA Section:       15.7.3.2 Number.MAX_VALUE
Description:        All value properties of the Number object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the DontDelete attribute of Number.MAX_VALUE

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_3_2__2: function() {
var SECTION = "15.7.3.2-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE =  "Number.MAX_VALUE:  DontDelete Attribute";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"delete( Number.MAX_VALUE ); Number.MAX_VALUE",
1.7976931348623157e308,
eval("delete( Number.MAX_VALUE );Number.MAX_VALUE") );

this.TestCase( SECTION,
"delete( Number.MAX_VALUE )",
false,
eval("delete( Number.MAX_VALUE )") );

//test();

},

/**
File Name:          15.7.3.2-3.js
ECMA Section:       15.7.3.2 Number.MAX_VALUE
Description:        All value properties of the Number object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the ReadOnly attribute of Number.MAX_VALUE

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_3_2__3: function() {
var SECTION = "15.7.3.2-3";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Number.MAX_VALUE";

//writeHeaderToLog( SECTION + " "+ TITLE);

var MAX_VAL = 1.7976931348623157e308;

this.TestCase( SECTION,
"Number.MAX_VALUE=0; Number.MAX_VALUE",
MAX_VAL,
eval("Number.MAX_VALUE=0; Number.MAX_VALUE") );

//test();

},

/**
File Name:          15.7.3.2-4.js
ECMA Section:       15.7.3.2 Number.MAX_VALUE
Description:        All value properties of the Number object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the DontEnum attribute of Number.MAX_VALUE

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_3_2__4: function() {
var SECTION = "15.7.3.2-4";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Number.MAX_VALUE:  DontEnum Attribute";
//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"var string = ''; for ( prop in Number ) { string += ( prop == 'MAX_VALUE' ) ? prop : '' } string;",
"",
eval("var string = ''; for ( prop in Number ) { string += ( prop == 'MAX_VALUE' ) ? prop : '' } string;")
);

//test();

},

/**
File Name:          15.7.3.3-1.js
ECMA Section:       15.7.3.3 Number.MIN_VALUE
Description:        All value properties of the Number object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the value of Number.MIN_VALUE

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_3_3__1: function() {
var SECTION = "15.7.3.3-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Number.MIN_VALUE";

//writeHeaderToLog( SECTION + " "+ TITLE);

var MIN_VAL = 5e-324;

this.TestCase(  SECTION,
"Number.MIN_VALUE",
MIN_VAL,
Number.MIN_VALUE );

//test();

},

/**
File Name:          15.7.3.3-2.js
ECMA Section:       15.7.3.3 Number.MIN_VALUE
Description:        All value properties of the Number object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the DontDelete attribute of Number.MIN_VALUE

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_3_3__2: function() {
var SECTION = "15.7.3.3-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Number.MIN_VALUE";

//writeHeaderToLog( SECTION + " "+ TITLE);

var MIN_VAL = 5e-324;

this.TestCase(  SECTION,
"delete( Number.MIN_VALUE )",
false,
eval("delete( Number.MIN_VALUE )") );

this.TestCase(  SECTION,
"delete( Number.MIN_VALUE ); Number.MIN_VALUE",
MIN_VAL,
eval("delete( Number.MIN_VALUE );Number.MIN_VALUE") );

//test();

},

/**
File Name:          15.7.3.3-3.js
ECMA Section:       15.7.3.3 Number.MIN_VALUE
Description:        All value properties of the Number object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the ReadOnly attribute of Number.MIN_VALUE

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_3_3__3: function() {
var SECTION = "15.7.3.3-3";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Number.MIN_VALUE:  ReadOnly Attribute";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Number.MIN_VALUE=0; Number.MIN_VALUE",
Number.MIN_VALUE,
eval("Number.MIN_VALUE=0; Number.MIN_VALUE" ));

//test();

},

/**
File Name:          15.7.3.3-4.js
ECMA Section:       15.7.3.3 Number.MIN_VALUE
Description:        All value properties of the Number object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the DontEnum attribute of Number.MIN_VALUE

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_3_3__4: function() {
var SECTION = "15.7.3.3-4";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"var string = ''; for ( prop in Number ) { string += ( prop == 'MIN_VALUE' ) ? prop : '' } string;",
"",
eval("var string = ''; for ( prop in Number ) { string += ( prop == 'MIN_VALUE' ) ? prop : '' } string;")
);

//test();

},

/**
File Name:          15.7.3.4-1.js
ECMA Section:       15.7.3.4 Number.NaN
Description:        All value properties of the Number object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the value of Number.NaN

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_3_4__1: function() {
var SECTION = "15.7.3.4-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Number.NaN";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase(SECTION,
"NaN",
NaN,
Number.NaN );

//test();

},

/**
File Name:          15.7.3.4-2.js
ECMA Section:       15.7.3.4 Number.NaN
Description:        All value properties of the Number object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the DontDelete attribute of Number.NaN

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_3_4__2: function() {
var SECTION = "15.7.3.4-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Number.NaN";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase(SECTION,
"delete( Number.NaN ); Number.NaN",
NaN,
eval("delete( Number.NaN );Number.NaN" ));

this.TestCase( SECTION,
"delete( Number.NaN )",
false,
eval("delete( Number.NaN )") );

//test();

},

/**
File Name:          15.7.3.4-3.js
ECMA Section:       15.7.3.4 Number.NaN
Description:        All value properties of the Number object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the ReadOnly attribute of Number.NaN

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_3_4__3: function() {
var SECTION = "15.7.3.4-3";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Number.NaN";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Number.NaN=0; Number.NaN",
Number.NaN,
eval("Number.NaN=0; Number.NaN") );

//test();

},

/**
File Name:          15.7.3.4-4.js
ECMA Section:       15.7.3.4 Number.NaN
Description:        All value properties of the Number object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the DontEnum attribute of Number.NaN

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_3_4__4: function() {
var SECTION = "15.7.3.4-4";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Number.NaN";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"var string = ''; for ( prop in Number ) { string += ( prop == 'NaN' ) ? prop : '' } string;",
"",
eval("var string = ''; for ( prop in Number ) { string += ( prop == 'NaN' ) ? prop : '' } string;")
);

//test();

},

/**
File Name:          15.7.3.5-1.js
ECMA Section:       15.7.3.5 Number.NEGATIVE_INFINITY
Description:        All value properties of the Number object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the  value of Number.NEGATIVE_INFINITY

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_3_5__1: function() {
var SECTION = "15.7.3.5-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Number.NEGATIVE_INFINITY";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase(SECTION,
"Number.NEGATIVE_INFINITY",
-Infinity,
Number.NEGATIVE_INFINITY );

//test();

},

/**
File Name:          15.7.3.5-2.js
ECMA Section:       15.7.3.5 Number.NEGATIVE_INFINITY
Description:        All value properties of the Number object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the DontDelete attribute of Number.NEGATIVE_INFINITY

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_3_5__2: function() {
var SECTION = "15.7.3.5-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Number.NEGATIVE_INFINITY";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase(   SECTION,
"delete( Number.NEGATIVE_INFINITY )",
false,
eval("delete( Number.NEGATIVE_INFINITY )") );

this.TestCase(   SECTION,
"delete( Number.NEGATIVE_INFINITY ); Number.NEGATIVE_INFINITY",
-Infinity,
eval("delete( Number.NEGATIVE_INFINITY );Number.NEGATIVE_INFINITY") );

//test();

},

/**
File Name:          15.7.3.5-3.js
ECMA Section:       15.7.3.5 Number.NEGATIVE_INFINITY
Description:        All value properties of the Number object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the ReadOnly attribute of Number.NEGATIVE_INFINITY

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_3_5__3: function() {
var SECTION = "15.7.3.5-3";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Number.NEGATIVE_INFINITY";

//writeHeaderToLog( SECTION + " "+ TITLE); o

this.TestCase( SECTION,
"Number.NEGATIVE_INFINITY=0; Number.NEGATIVE_INFINITY",
-Infinity,
eval("Number.NEGATIVE_INFINITY=0; Number.NEGATIVE_INFINITY") );

//test();

},

/**
File Name:          15.7.3.5-4.js
ECMA Section:       15.7.3.5 Number.NEGATIVE_INFINITY
Description:        All value properties of the Number object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the DontEnum attribute of Number.NEGATIVE_INFINITY

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_3_5__4: function() {
var SECTION = "15.7.3.5-4";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Number.NEGATIVE_INFINITY";

//writeHeaderToLog( SECTION + " "+ TITLE); o

this.TestCase( SECTION,
"var string = ''; for ( prop in Number ) { string += ( prop == 'NEGATIVE_INFINITY' ) ? prop : '' } string;",
"",
eval("var string = ''; for ( prop in Number ) { string += ( prop == 'NEGATIVE_INFINITY' ) ? prop : '' } string;")
);

//test();

},

/**
File Name:          15.7.3.6-1.js
ECMA Section:       15.7.3.6 Number.POSITIVE_INFINITY
Description:        All value properties of the Number object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the value of Number.POSITIVE_INFINITY

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_3_6__1: function() {
var SECTION = "15.7.3.6-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Number.POSITIVE_INFINITY";

//writeHeaderToLog( SECTION + " "+ TITLE); o

this.TestCase( SECTION,
"Number.POSITIVE_INFINITY",
Infinity,
Number.POSITIVE_INFINITY );

//test();

},

/**
File Name:          15.7.3.6-2.js
ECMA Section:       15.7.3.6 Number.POSITIVE_INFINITY
Description:        All value properties of the Number object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the DontDelete attribute of Number.POSITIVE_INFINITY

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_3_6__2: function() {
var SECTION = "15.7.3.6-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Number.POSITIVE_INFINITY";

//writeHeaderToLog( SECTION + " "+ TITLE); o

this.TestCase(SECTION,
"delete( Number.POSITIVE_INFINITY )",
false,
eval("delete( Number.POSITIVE_INFINITY )") );

this.TestCase(SECTION,
"delete( Number.POSITIVE_INFINITY ); Number.POSITIVE_INFINITY",
Infinity,
eval("delete( Number.POSITIVE_INFINITY );Number.POSITIVE_INFINITY") );

//test();

},

/**
File Name:          15.7.3.6-3.js
ECMA Section:       15.7.3.6 Number.POSITIVE_INFINITY
Description:        All value properties of the Number object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the ReadOnly attribute of Number.POSITIVE_INFINITY

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_3_6__3: function() {
var SECTION = "15.7.3.6-3";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Number.POSITIVE_INFINITY";

//writeHeaderToLog( SECTION + " "+ TITLE); o

this.TestCase( SECTION,
"Number.POSITIVE_INFINITY=0; Number.POSITIVE_INFINITY",
Number.POSITIVE_INFINITY,
eval("Number.POSITIVE_INFINITY=0; Number.POSITIVE_INFINITY") );

//test();

},

/**
File Name:          15.7.3.6-4.js
ECMA Section:       15.7.3.6 Number.POSITIVE_INFINITY
Description:        All value properties of the Number object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the DontEnum attribute of Number.POSITIVE_INFINITY

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_3_6__4: function() {
var SECTION = "15.7.3.6-4";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Number.POSITIVE_INFINITY";

//writeHeaderToLog( SECTION + " "+ TITLE); o

this.TestCase( SECTION,
"var string = ''; for ( prop in Number ) { string += ( prop == 'POSITIVE_INFINITY' ) ? prop : '' } string;",
"",
eval("var string = ''; for ( prop in Number ) { string += ( prop == 'POSITIVE_INFINITY' ) ? prop : '' } string;")
);

//test();

},

/**
File Name:          15.7.3.js
15.7.3  Properties of the Number Constructor

Description:        The value of the internal [[Prototype]] property
of the Number constructor is the Function prototype
object.  The Number constructor also has the internal
[[Call]] and [[Construct]] properties, and the length
property.

Other properties are in subsequent tests.

Author:             christine@netscape.com
Date:               29 september 1997
*/
test_15_7_3: function() {
var SECTION = "15.7.3";
var VERSION = "ECMA_2";
//startTest();
var TITLE   = "Properties of the Number Constructor";

//writeHeaderToLog( SECTION + " "+ TITLE); o

this.TestCase(SECTION,
"Number.length",
1,
Number.length );

//test();

},

/**
File Name:          15.7.4-1.js
ECMA Section:       15.7.4.1 Properties of the Number Prototype Object
Description:
Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_4__1: function() {
var SECTION = "15.7.4-1";
var VERSION = "ECMA_1";
//startTest();
//writeHeaderToLog( SECTION + "Properties of the Number prototype object");

this.TestCase(SECTION, "Number.prototype.valueOf()",      0,                  Number.prototype.valueOf() );
this.TestCase(SECTION, "typeof(Number.prototype)",        "object",           typeof(Number.prototype) );
this.TestCase(SECTION, "Number.prototype.constructor == Number",    true,     Number.prototype.constructor == Number );
//    new TestCase(SECTION, "Number.prototype == Number.__proto__",      true,   Number.prototype == Number.__proto__ );

//test();

},

/**
File Name:          15.7.4.1.js
ECMA Section:       15.7.4.1.1 Number.prototype.constructor

Number.prototype.constructor is the built-in Number constructor.

Description:
Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_4_1: function() {
var SECTION = "15.7.4.1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Number.prototype.constructor";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase(   SECTION,
"Number.prototype.constructor",
Number,
Number.prototype.constructor );

//test();

},

/**
File Name:          15.7.4.2.js
ECMA Section:       15.7.4.2.1 Number.prototype.toString()
Description:
If the radix is the number 10 or not supplied, then this number value is
given as an argument to the ToString operator; the resulting string value
is returned.

If the radix is supplied and is an integer from 2 to 36, but not 10, the
result is a string, the choice of which is implementation dependent.

The toString function is not generic; it generates a runtime error if its
this value is not a Number object. Therefore it cannot be transferred to
other kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_4_2: function() {
var SECTION = "15.7.4.2-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " "+ TITLE);

//  the following two lines cause navigator to crash -- cmb 9/16/97
this.TestCase(SECTION,
"Number.prototype.toString()",
"0",
eval("Number.prototype.toString()") );

this.TestCase(SECTION,
"typeof(Number.prototype.toString())",
"string",
eval("typeof(Number.prototype.toString())") );

this.TestCase(SECTION,
"s = Number.prototype.toString; o = new Number(); o.toString = s; o.toString()",
"0",
eval("s = Number.prototype.toString; o = new Number(); o.toString = s; o.toString()") );

this.TestCase(SECTION,
"s = Number.prototype.toString; o = new Number(1); o.toString = s; o.toString()",
"1",
eval("s = Number.prototype.toString; o = new Number(1); o.toString = s; o.toString()") );

this.TestCase(SECTION,
"s = Number.prototype.toString; o = new Number(-1); o.toString = s; o.toString()",
"-1",
eval("s = Number.prototype.toString; o = new Number(-1); o.toString = s; o.toString()") );

this.TestCase(SECTION,
"var MYNUM = new Number(255); MYNUM.toString(10)",
"255",
eval("var MYNUM = new Number(255); MYNUM.toString(10)") );

this.TestCase(SECTION,
"var MYNUM = new Number(Number.NaN); MYNUM.toString(10)",
"NaN",
eval("var MYNUM = new Number(Number.NaN); MYNUM.toString(10)") );

this.TestCase(SECTION,
"var MYNUM = new Number(Infinity); MYNUM.toString(10)",
"Infinity",
eval("var MYNUM = new Number(Infinity); MYNUM.toString(10)") );

this.TestCase(SECTION,
"var MYNUM = new Number(-Infinity); MYNUM.toString(10)",
"-Infinity",
eval("var MYNUM = new Number(-Infinity); MYNUM.toString(10)") );

//test();

},

/**
File Name:          15.7.4.2-2-n.js
ECMA Section:       15.7.4.2.1 Number.prototype.toString()
Description:
If the radix is the number 10 or not supplied, then this number value is
given as an argument to the ToString operator; the resulting string value
is returned.

If the radix is supplied and is an integer from 2 to 36, but not 10, the
result is a string, the choice of which is implementation dependent.

The toString function is not generic; it generates a runtime error if its
this value is not a Number object. Therefore it cannot be transferred to
other kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_4_2__2__n: function() {
var SECTION = "15.7.4.2-2-n";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Number.prototype.toString()");

var DESCRIPTION = "o = new Object(); o.toString = Number.prototype.toString; o.toString()";
var EXPECTED = "error";

this.TestCase(SECTION,
"o = new Object(); o.toString = Number.prototype.toString; o.toString()",
"error",
eval("o = new Object(); o.toString = Number.prototype.toString; o.toString()") );

//    new TestCase(SECTION,  "o = new String(); o.toString = Number.prototype.toString; o.toString()",  "error",    eval("o = new String(); o.toString = Number.prototype.toString; o.toString()") );
//    new TestCase(SECTION,  "o = 3; o.toString = Number.prototype.toString; o.toString()",             "error",    eval("o = 3; o.toString = Number.prototype.toString; o.toString()") );

//test();

},

/**
File Name:          15.7.4.2-2-n.js
ECMA Section:       15.7.4.2.1 Number.prototype.toString()
Description:
If the radix is the number 10 or not supplied, then this number value is
given as an argument to the ToString operator; the resulting string value
is returned.

If the radix is supplied and is an integer from 2 to 36, but not 10, the
result is a string, the choice of which is implementation dependent.

The toString function is not generic; it generates a runtime error if its
this value is not a Number object. Therefore it cannot be transferred to
other kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_4_2__2__n_WORKS: function() {
var SECTION = "15.7.4.2-2-n";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Number.prototype.toString()");

var DESCRIPTION = "o = new Object(); o.toString = Number.prototype.toString; o.toString()";
var EXPECTED = "error";

try {
this.TestCase(SECTION,
"o = new Object(); o.toString = Number.prototype.toString; o.toString()",
"error",
eval("o = new Object(); o.toString = Number.prototype.toString; o.toString()") );
this.fail();
} catch (e) {
this.assertTrue(e.name == 'TypeError');
this.assertTrue(e.message == 'Method "toString" called on incompatible object.');
}

//    new TestCase(SECTION,  "o = new String(); o.toString = Number.prototype.toString; o.toString()",  "error",    eval("o = new String(); o.toString = Number.prototype.toString; o.toString()") );
//    new TestCase(SECTION,  "o = 3; o.toString = Number.prototype.toString; o.toString()",             "error",    eval("o = 3; o.toString = Number.prototype.toString; o.toString()") );

//test();

},

/**
File Name:          15.7.4.2-3-n.js
ECMA Section:       15.7.4.2.1 Number.prototype.toString()
Description:
If the radix is the number 10 or not supplied, then this number value is
given as an argument to the ToString operator; the resulting string value
is returned.

If the radix is supplied and is an integer from 2 to 36, but not 10, the
result is a string, the choice of which is implementation dependent.

The toString function is not generic; it generates a runtime error if its
this value is not a Number object. Therefore it cannot be transferred to
other kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_4_2__3__n: function() {
var SECTION = "15.7.4.2-3-n";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Number.prototype.toString()");

var DESCRIPTION = "o = new String(); o.toString = Number.prototype.toString; o.toString()";
var EXPECTED = "error";

this.TestCase(SECTION,
"o = new String(); o.toString = Number.prototype.toString; o.toString()",
"error",
eval("o = new String(); o.toString = Number.prototype.toString; o.toString()") );

//test();

},

/**
File Name:          15.7.4.2-3-n.js
ECMA Section:       15.7.4.2.1 Number.prototype.toString()
Description:
If the radix is the number 10 or not supplied, then this number value is
given as an argument to the ToString operator; the resulting string value
is returned.

If the radix is supplied and is an integer from 2 to 36, but not 10, the
result is a string, the choice of which is implementation dependent.

The toString function is not generic; it generates a runtime error if its
this value is not a Number object. Therefore it cannot be transferred to
other kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_4_2__3__n_WORKS: function() {
var SECTION = "15.7.4.2-3-n";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Number.prototype.toString()");

var DESCRIPTION = "o = new String(); o.toString = Number.prototype.toString; o.toString()";
var EXPECTED = "error";

try {
this.TestCase(SECTION,
"o = new String(); o.toString = Number.prototype.toString; o.toString()",
"error",
eval("o = new String(); o.toString = Number.prototype.toString; o.toString()") );
this.fail();
} catch (e) {
this.assertTrue(e.name == 'TypeError');
this.assertTrue(e.message == 'Method "toString" called on incompatible object.');
}

//test();

},

/**
File Name:          15.7.4.2-4.js
ECMA Section:       15.7.4.2.1 Number.prototype.toString()
Description:
If the radix is the number 10 or not supplied, then this number value is
given as an argument to the ToString operator; the resulting string value
is returned.

If the radix is supplied and is an integer from 2 to 36, but not 10, the
result is a string, the choice of which is implementation dependent.

The toString function is not generic; it generates a runtime error if its
this value is not a Number object. Therefore it cannot be transferred to
other kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_4_2__4: function() {
var SECTION = "15.7.4.2-4";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Number.prototype.toString()");

this.TestCase(SECTION,
"o = 3; o.toString = Number.prototype.toString; o.toString()",
"3",
eval("o = 3; o.toString = Number.prototype.toString; o.toString()") );

//test();

},

/**
File Name:          15.7.4.3-1.js
ECMA Section:       15.7.4.3.1 Number.prototype.valueOf()
Description:
Returns this number value.

The valueOf function is not generic; it generates a runtime error if its
this value is not a Number object. Therefore it cannot be transferred to
other kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_4_3__1: function() {
var SECTION = "15.7.4.3-1";
var VERSION = "ECMA_1";
//startTest();


//writeHeaderToLog( SECTION + " Number.prototype.valueOf()");

//  the following two line causes navigator to crash -- cmb 9/16/97
this.TestCase("SECTION",
"Number.prototype.valueOf()",
0,
eval("Number.prototype.valueOf()") );

this.TestCase("SECTION",
"(new Number(1)).valueOf()",
1,
eval("(new Number(1)).valueOf()") );

this.TestCase("SECTION",
"(new Number(-1)).valueOf()",
-1,
eval("(new Number(-1)).valueOf()") );

this.TestCase("SECTION",
"(new Number(0)).valueOf()",
0,
eval("(new Number(0)).valueOf()") );

this.TestCase("SECTION",
"(new Number(Number.POSITIVE_INFINITY)).valueOf()",
Number.POSITIVE_INFINITY,
eval("(new Number(Number.POSITIVE_INFINITY)).valueOf()") );

this.TestCase("SECTION",
"(new Number(Number.NaN)).valueOf()",
Number.NaN,
eval("(new Number(Number.NaN)).valueOf()") );

this.TestCase("SECTION",
"(new Number()).valueOf()",
0,
eval("(new Number()).valueOf()") );

//test();

},

/**
File Name:          15.7.4.3-2.js
ECMA Section:       15.7.4.3.1 Number.prototype.valueOf()
Description:
Returns this number value.

The valueOf function is not generic; it generates a runtime error if its
this value is not a Number object. Therefore it cannot be transferred to
other kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_4_3__2: function() {
var SECTION = "15.7.4.3-2";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Number.prototype.valueOf()");

this.TestCase(SECTION,
"v = Number.prototype.valueOf; num = 3; num.valueOf = v; num.valueOf()",
3,
eval("v = Number.prototype.valueOf; num = 3; num.valueOf = v; num.valueOf()") );

//test();

},

/**
File Name:          15.7.4.3-3.js
ECMA Section:       15.7.4.3.1 Number.prototype.valueOf()
Description:
Returns this number value.

The valueOf function is not generic; it generates a runtime error if its
this value is not a Number object. Therefore it cannot be transferred to
other kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_4_3__3__n: function() {
var SECTION = "15.7.4.3-3-n";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Number.prototype.valueOf()");

//    new TestCase("15.7.4.1", "v = Number.prototype.valueOf; num = 3; num.valueOf = v; num.valueOf()", "error",  eval("v = Number.prototype.valueOf; num = 3; num.valueOf = v; num.valueOf()") );

var DESCRIPTION = "v = Number.prototype.valueOf; o = new String('Infinity'); o.valueOf = v; o.valueOf()";
var EXPECTED = "error";

this.TestCase("15.7.4.1",
"v = Number.prototype.valueOf; o = new String('Infinity'); o.valueOf = v; o.valueOf()",
"error",
eval("v = Number.prototype.valueOf; o = new String('Infinity'); o.valueOf = v; o.valueOf()") );

//    new TestCase("15.7.4.1", "v = Number.prototype.valueOf; o = new Object(); o.valueOf = v; o.valueOf()", "error",  eval("v = Number.prototype.valueOf; o = new Object(); o.valueOf = v; o.valueOf()") );

//test();

},

/**
File Name:          15.7.4.3-3.js
ECMA Section:       15.7.4.3.1 Number.prototype.valueOf()
Description:
Returns this number value.

The valueOf function is not generic; it generates a runtime error if its
this value is not a Number object. Therefore it cannot be transferred to
other kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_7_4_3__3__n_WORKS: function() {
var SECTION = "15.7.4.3-3-n";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Number.prototype.valueOf()");

//    new TestCase("15.7.4.1", "v = Number.prototype.valueOf; num = 3; num.valueOf = v; num.valueOf()", "error",  eval("v = Number.prototype.valueOf; num = 3; num.valueOf = v; num.valueOf()") );

var DESCRIPTION = "v = Number.prototype.valueOf; o = new String('Infinity'); o.valueOf = v; o.valueOf()";
var EXPECTED = "error";

try {
this.TestCase("15.7.4.1",
"v = Number.prototype.valueOf; o = new String('Infinity'); o.valueOf = v; o.valueOf()",
"error",
eval("v = Number.prototype.valueOf; o = new String('Infinity'); o.valueOf = v; o.valueOf()") );
this.fail();
} catch (e) {
this.assertTrue(e.name == 'TypeError');
this.assertTrue(e.message == 'Method "valueOf" called on incompatible object.');
}

//    new TestCase("15.7.4.1", "v = Number.prototype.valueOf; o = new Object(); o.valueOf = v; o.valueOf()", "error",  eval("v = Number.prototype.valueOf; o = new Object(); o.valueOf = v; o.valueOf()") );

//test();

}

})
.endType();
