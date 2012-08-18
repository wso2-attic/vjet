vjo.ctype("dsf.jslang.feature.tests.EcmaMathTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({

fail : function(){
},
//>public void assertTure(Object o)
assertTrue : function(o){
},

constructs: function() {
this.base();
},

/**
File Name:          15.8-2.js
ECMA Section:       15.8 The Math Object

Description:

The Math object is merely a single object that has some named properties,
some of which are functions.

The value of the internal [[Prototype]] property of the Math object is the
Object prototype object (15.2.3.1).

The Math object does not have a [[Construct]] property; it is not possible
to use the Math object as a constructor with the new operator.

The Math object does not have a [[Call]] property; it is not possible to
invoke the Math object as a function.

Recall that, in this specification, the phrase "the number value for x" has
a technical meaning defined in section 8.5.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_8__2__n: function() {
var SECTION = "15.8-2-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The Math Object";

//writeHeaderToLog( SECTION + " "+ TITLE);

var DESCRIPTION = "MYMATH = new Math()";
var EXPECTED = "error";

this.TestCase( SECTION,
"MYMATH = new Math()",
"error",
eval("MYMATH = new Math()"));

//test();

},

/**
File Name:          15.8-2.js
ECMA Section:       15.8 The Math Object

Description:

The Math object is merely a single object that has some named properties,
some of which are functions.

The value of the internal [[Prototype]] property of the Math object is the
Object prototype object (15.2.3.1).

The Math object does not have a [[Construct]] property; it is not possible
to use the Math object as a constructor with the new operator.

The Math object does not have a [[Call]] property; it is not possible to
invoke the Math object as a function.

Recall that, in this specification, the phrase "the number value for x" has
a technical meaning defined in section 8.5.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_8__2__n_WORKS: function() {
var SECTION = "15.8-2-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The Math Object";

//writeHeaderToLog( SECTION + " "+ TITLE);

var DESCRIPTION = "MYMATH = new Math()";
var EXPECTED = "error";

try {
this.TestCase( SECTION,
"MYMATH = new Math()",
"error",
eval("MYMATH = new Math()"));
this.fail();
} catch (e) {
this.assertTrue(e.name == 'TypeError');
this.assertTrue(e.message.indexOf('NativeMath') >= 0);
this.assertTrue(e.message.indexOf('is not a function, it is object.') >= 0);
}

//test();

},

/**
File Name:          15.8-3.js
ECMA Section:       15.8 The Math Object

Description:

The Math object is merely a single object that has some named properties,
some of which are functions.

The value of the internal [[Prototype]] property of the Math object is the
Object prototype object (15.2.3.1).

The Math object does not have a [[Construct]] property; it is not possible
to use the Math object as a constructor with the new operator.

The Math object does not have a [[Call]] property; it is not possible to
invoke the Math object as a function.

Recall that, in this specification, the phrase "the number value for x" has
a technical meaning defined in section 8.5.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_8__3__n: function() {
var SECTION = "15.8-3-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The Math Object";

//writeHeaderToLog( SECTION + " "+ TITLE);

var DESCRIPTION = "MYMATH = Math()";
var EXPECTED = "error";

this.TestCase( SECTION,
"MYMATH = Math()",
"error",
eval("MYMATH = Math()") );

//test();
},

/**
File Name:          15.8-3.js
ECMA Section:       15.8 The Math Object

Description:

The Math object is merely a single object that has some named properties,
some of which are functions.

The value of the internal [[Prototype]] property of the Math object is the
Object prototype object (15.2.3.1).

The Math object does not have a [[Construct]] property; it is not possible
to use the Math object as a constructor with the new operator.

The Math object does not have a [[Call]] property; it is not possible to
invoke the Math object as a function.

Recall that, in this specification, the phrase "the number value for x" has
a technical meaning defined in section 8.5.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_8__3__n_WORKS: function() {
var SECTION = "15.8-3-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The Math Object";

//writeHeaderToLog( SECTION + " "+ TITLE);

var DESCRIPTION = "MYMATH = Math()";
var EXPECTED = "error";

try {
this.TestCase( SECTION,
"MYMATH = Math()",
"error",
eval("MYMATH = Math()") );
this.fail();
} catch (e) {
this.assertTrue(e.name == 'TypeError');
this.assertTrue(e.message == 'Math is not a function, it is object.');
}

//test();
},

/**
File Name:          15.8.1.1-1.js
ECMA Section:       15.8.1.1.js
Description:        All value properties of the Math object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the ReadOnly attribute of Math.E

Author:             christine@netscape.com
Date:               16 september 1997
*/

test_15_8_1_1__1 : function() {
var SECTION = "15.8.1.1-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Math.E";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Math.E = 0; Math.E",
2.7182818284590452354,
eval("Math.E=0;Math.E") );

//test();

},

/**
File Name:          15.8.1.1-2.js
ECMA Section:       15.8.1.1.js
Description:        All value properties of the Math object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the DontDelete attribute of Math.E

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_8_1_1__2: function() {
var SECTION = "15.8.1.1-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Math.E";

//writeHeaderToLog( SECTION + " "+ TITLE);

var MATH_E = 2.7182818284590452354;

this.TestCase( SECTION,
"delete(Math.E)",
false,
eval("delete Math.E") );

this.TestCase( SECTION,
"delete(Math.E); Math.E",
MATH_E,
eval("delete Math.E; Math.E") );

//test();

},

/**
File Name:          15.8.1.2-1.js
ECMA Section:       15.8.2.js
Description:        All value properties of the Math object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the ReadOnly attribute of Math.LN10

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_8_1_2__1: function() {
var SECTION = "15.8.1.2-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Math.LN10";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Math.LN10=0; Math.LN10",
2.302585092994046,
eval("Math.LN10=0; Math.LN10") );

//test();

},

/**
File Name:          15.8.1.2-1.js
ECMA Section:       15.8.2.js
Description:        All value properties of the Math object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the DontDelete attribute of Math.LN10

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_8_1_2__2: function() {
var SECTION = "15.8.1.2-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Math.LN10";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"delete( Math.LN10 ); Math.LN10",
2.302585092994046,
eval("delete(Math.LN10); Math.LN10") );

this.TestCase( SECTION,
"delete( Math.LN10 ); ",
false,
eval("delete(Math.LN10)") );

//test();

},

/**
File Name:          15.8.1.3-1.js
ECMA Section:       15.8.1.3.js
Description:        All value properties of the Math object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the ReadOnly attribute of Math.LN2

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_8_1_3__1: function() {
var SECTION = "15.8.1.3-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Math.LN2";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Math.LN2=0; Math.LN2",
0.6931471805599453,
eval("Math.LN2=0; Math.LN2") );

//test();

},

/**
File Name:          15.8.1.3-3.js
ECMA Section:       15.8.1.3.js
Description:        All value properties of the Math object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the DontDelete attribute of Math.LN2

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_8_1_3__2: function() {
var SECTION = "15.8.1.3-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Math.LN2";

//writeHeaderToLog( SECTION + " "+ TITLE);

var MATH_LN2 = 0.6931471805599453;

this.TestCase( SECTION,
"delete(Math.LN2)",
false,
eval("delete(Math.LN2)") );

this.TestCase( SECTION,
"delete(Math.LN2); Math.LN2",
MATH_LN2,
eval("delete(Math.LN2); Math.LN2") );

//test();

},

/**
File Name:          15.8.1.4-1.js
ECMA Section:       15.8.1.4.js
Description:        All value properties of the Math object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the ReadOnly attribute of Math.LOG2E

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_8_1_4__1: function() {
var SECTION = "15.8.1.4-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Math.LOG2E";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Math.L0G2E=0; Math.LOG2E",
1.4426950408889634,
eval("Math.LOG2E=0; Math.LOG2E") );

//test();

},

/**
File Name:          15.8.1.4-2.js
ECMA Section:       15.8.1.4.js
Description:        All value properties of the Math object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the DontDelete attribute of Math.LOG2E

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_8_1_4__2: function() {
var SECTION = "15.8.1.4-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Math.LOG2E";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"delete(Math.L0G2E);Math.LOG2E",
1.4426950408889634,
eval("delete(Math.LOG2E);Math.LOG2E") );
this.TestCase( SECTION,
"delete(Math.L0G2E)",
false,
eval("delete(Math.LOG2E)") );

//test();

},

/**
File Name:          15.8.1.5-1.js
ECMA Section:       15.8.1.5.js
Description:        All value properties of the Math object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the ReadOnly attribute of Math.LOG10E

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_8_1_5__1: function() {
var SECTION = "15.8.1.5-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Math.LOG10E";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Math.LOG10E=0; Math.LOG10E",
0.4342944819032518,
eval("Math.LOG10E=0; Math.LOG10E") );

//test();

},

/**
File Name:          15.8.1.5-2.js
ECMA Section:       15.8.1.5.js
Description:        All value properties of the Math object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the DontDelete attribute of Math.LOG10E

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_8_1_5__2: function() {
var SECTION = "15.8.1.5-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Math.LOG10E";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"delete Math.LOG10E; Math.LOG10E",
0.4342944819032518,
eval("delete Math.LOG10E; Math.LOG10E") );

this.TestCase( SECTION,
"delete Math.LOG10E",
false,
eval("delete Math.LOG10E") );

//test();

},

/**
File Name:          15.8.1.6-1.js
ECMA Section:       15.8.1.6.js
Description:        All value properties of the Math object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the ReadOnly attribute of Math.PI

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_8_1_6__1: function() {
var SECTION = "15.8.1.6-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Math.PI";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Math.PI=0; Math.PI",
3.1415926535897923846,
eval("Math.PI=0; Math.PI") );

//test();

},

/**
File Name:          15.8.1.6-2.js
ECMA Section:       15.8.1.6.js
Description:        All value properties of the Math object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the DontDelete attribute of Math.PI

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_8_1_6__2: function() {
var SECTION = "15.8.1.6-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Math.PI";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"delete Math.PI; Math.PI",
3.1415926535897923846,
eval("delete Math.PI; Math.PI") );

this.TestCase( SECTION,
"delete Math.PI; Math.PI",
false,
eval("delete Math.PI") );

//test();

},

/**
File Name:          15.8.1.7-1.js
ECMA Section:       15.8.1.7.js
Description:        All value properties of the Math object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the ReadOnly attribute of Math.SQRT1_2

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_8_1_7__1: function() {
var SECTION = "15.8.1.7-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Math.SQRT1_2";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Math.SQRT1_2=0; Math.SQRT1_2",
0.7071067811865476,
eval("Math.SQRT1_2=0; Math.SQRT1_2") );

//test();

},

/**
File Name:          15.8.1.7-2.js
ECMA Section:       15.8.1.7.js
Description:        All value properties of the Math object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the DontDelete attribute of Math.SQRT1_2

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_8_1_7__2: function() {
var SECTION = "15.8.1.7-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Math.SQRT1_2";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"delete Math.SQRT1_2; Math.SQRT1_2",
0.7071067811865476,
eval("delete Math.SQRT1_2; Math.SQRT1_2") );

this.TestCase( SECTION,
"delete Math.SQRT1_2",
false,
eval("delete Math.SQRT1_2") );

//test();

},

/**
File Name:          15.8.1.8-1.js
ECMA Section:       15.8.1.8.js
Description:        All value properties of the Math object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the ReadOnly attribute of Math.SQRT2

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_8_1_8__1: function() {
var SECTION = "15.8.1.8-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Math.SQRT2";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Math.SQRT2=0; Math.SQRT2",
1.4142135623730951,
eval("Math.SQRT2=0; Math.SQRT2") );

//test();

},

/**
File Name:          15.8.1.8-2.js
ECMA Section:       15.8.1.8.js
Description:        All value properties of the Math object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the DontDelete attribute of Math.SQRT2

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_8_1_8__2: function() {
var SECTION = "15.8.1.8-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Math.SQRT2";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"delete Math.SQRT2; Math.SQRT2",
1.4142135623730951,
eval("delete Math.SQRT2; Math.SQRT2") );

this.TestCase( SECTION,
"delete Math.SQRT2",
false,
eval("delete Math.SQRT2") );

//test();

},

/**
File Name:          15.8.1.8-3.js
ECMA Section:       15.8.1.8.js
Description:        All value properties of the Math object should have
the attributes [DontEnum, DontDelete, ReadOnly]

this test checks the DontDelete attribute of Math.SQRT2

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_8_1_8__3: function() {
var SECTION = "15.8.1.8-3";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Math.SQRT2:  DontDelete");

this.TestCase( SECTION,
"delete Math.SQRT2",
false,
eval("delete Math.SQRT2") );

//test();

},

/**
File Name:          15.8.1.js
ECMA Section:       15.8.1.js   Value Properties of the Math Object
15.8.1.1    E
15.8.1.2    LN10
15.8.1.3    LN2
15.8.1.4    LOG2E
15.8.1.5    LOG10E
15.8.1.6    PI
15.8.1.7    SQRT1_2
15.8.1.8    SQRT2
Description:        verify the values of some math constants
Author:             christine@netscape.com
Date:               7 july 1997

*/
test_15_8_1: function() {
var SECTION = "15.8.1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Value Properties of the Math Object";

//writeHeaderToLog( SECTION + " "+ TITLE);


this.TestCase( "15.8.1.1", "Math.E",
2.7182818284590452354,
Math.E );

this.TestCase( "15.8.1.1",
"typeof Math.E",
"number",
typeof Math.E );

this.TestCase( "15.8.1.2",
"Math.LN10",
2.302585092994046,
Math.LN10 );

this.TestCase( "15.8.1.2",
"typeof Math.LN10",
"number",
typeof Math.LN10 );

this.TestCase( "15.8.1.3",
"Math.LN2",
0.6931471805599453,
Math.LN2 );

this.TestCase( "15.8.1.3",
"typeof Math.LN2",
"number",
typeof Math.LN2 );

this.TestCase( "15.8.1.4",
"Math.LOG2E",
1.4426950408889634,
Math.LOG2E );

this.TestCase( "15.8.1.4",
"typeof Math.LOG2E",
"number",
typeof Math.LOG2E );

this.TestCase( "15.8.1.5",
"Math.LOG10E",
0.4342944819032518,
Math.LOG10E);

this.TestCase( "15.8.1.5",
"typeof Math.LOG10E",
"number",
typeof Math.LOG10E);

this.TestCase( "15.8.1.6",
"Math.PI",
3.14159265358979323846,
Math.PI );

this.TestCase( "15.8.1.6",
"typeof Math.PI",
"number",
typeof Math.PI );

this.TestCase( "15.8.1.7",
"Math.SQRT1_2",
0.7071067811865476,
Math.SQRT1_2);

this.TestCase( "15.8.1.7",
"typeof Math.SQRT1_2",
"number",
typeof Math.SQRT1_2);

this.TestCase( "15.8.1.8",
"Math.SQRT2",
1.4142135623730951,
Math.SQRT2 );

this.TestCase( "15.8.1.8",
"typeof Math.SQRT2",
"number",
typeof Math.SQRT2 );

this.TestCase( SECTION,
"var MATHPROPS='';for( p in Math ){ MATHPROPS +=p; };MATHPROPS",
"",
eval("var MATHPROPS='';for( p in Math ){ MATHPROPS +=p; };MATHPROPS") );

//test();

},

/**
File Name:          15.8.2.1.js
ECMA Section:       15.8.2.1 abs( x )
Description:        return the absolute value of the argument,
which should be the magnitude of the argument
with a positive sign.
-   if x is NaN, return NaN
-   if x is -0, result is +0
-   if x is -Infinity, result is +Infinity
Author:             christine@netscape.com
Date:               7 july 1997
*/
test_15_8_2_1: function() {
var SECTION = "15.8.2.1";
var VERSION = "ECMA_1";
var TITLE   = "Math.abs()";
var BUGNUMBER = "77391";
//startTest();

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Math.abs.length",
1,
Math.abs.length );

this.TestCase( SECTION,
"Math.abs()",
Number.NaN,
Math.abs() );

this.TestCase( SECTION,
"Math.abs( void 0 )",
Number.NaN,
Math.abs(void 0) );

this.TestCase( SECTION,
"Math.abs( null )",
0,
Math.abs(null) );

this.TestCase( SECTION,
"Math.abs( true )",
1,
Math.abs(true) );

this.TestCase( SECTION,
"Math.abs( false )",
0,
Math.abs(false) );

this.TestCase( SECTION,
"Math.abs( string primitive)",
Number.NaN,
Math.abs("a string primitive") );

this.TestCase( SECTION,
"Math.abs( string object )",
Number.NaN,
Math.abs(new String( 'a String object' ))  );

this.TestCase( SECTION,
"Math.abs( Number.NaN )",
Number.NaN,
Math.abs(Number.NaN) );

this.TestCase( SECTION,
"Math.abs(0)",
0,
Math.abs( 0 ) );

this.TestCase( SECTION,
"Math.abs( -0 )",
0,
Math.abs(-0) );

this.TestCase( SECTION,
"Infinity/Math.abs(-0)",
Infinity,
Infinity/Math.abs(-0) );

this.TestCase( SECTION,
"Math.abs( -Infinity )",
Number.POSITIVE_INFINITY,
Math.abs( Number.NEGATIVE_INFINITY ) );

this.TestCase( SECTION,
"Math.abs( Infinity )",
Number.POSITIVE_INFINITY,
Math.abs( Number.POSITIVE_INFINITY ) );

this.TestCase( SECTION,
"Math.abs( - MAX_VALUE )",
Number.MAX_VALUE,
Math.abs( - Number.MAX_VALUE )       );

this.TestCase( SECTION,
"Math.abs( - MIN_VALUE )",
Number.MIN_VALUE,
Math.abs( -Number.MIN_VALUE )        );

this.TestCase( SECTION,
"Math.abs( MAX_VALUE )",
Number.MAX_VALUE,
Math.abs( Number.MAX_VALUE )       );

this.TestCase( SECTION,
"Math.abs( MIN_VALUE )",
Number.MIN_VALUE,
Math.abs( Number.MIN_VALUE )        );

this.TestCase( SECTION,
"Math.abs( -1 )",
1,
Math.abs( -1 )                       );

this.TestCase( SECTION,
"Math.abs( new Number( -1 ) )",
1,
Math.abs( new Number(-1) )           );

this.TestCase( SECTION,
"Math.abs( 1 )",
1,
Math.abs( 1 ) );

this.TestCase( SECTION,
"Math.abs( Math.PI )",
Math.PI,
Math.abs( Math.PI ) );

this.TestCase( SECTION,
"Math.abs( -Math.PI )",
Math.PI,
Math.abs( -Math.PI ) );

this.TestCase( SECTION,
"Math.abs(-1/100000000)",
1/100000000,
Math.abs(-1/100000000) );

this.TestCase( SECTION,
"Math.abs(-Math.pow(2,32))",
Math.pow(2,32),
Math.abs(-Math.pow(2,32)) );

this.TestCase( SECTION,
"Math.abs(Math.pow(2,32))",
Math.pow(2,32),
Math.abs(Math.pow(2,32)) );

this.TestCase( SECTION,
"Math.abs( -0xfff )",
4095,
Math.abs( -0xfff ) );

this.TestCase( SECTION,
"Math.abs( -0777 )",
511,
Math.abs(-0777 ) );

this.TestCase( SECTION,
"Math.abs('-1e-1')",
0.1,
Math.abs('-1e-1') );

this.TestCase( SECTION,
"Math.abs('0xff')",
255,
Math.abs('0xff') );

this.TestCase( SECTION,
"Math.abs('077')",
77,
Math.abs('077') );

this.TestCase( SECTION,
"Math.abs( 'Infinity' )",
Infinity,
Math.abs('Infinity') );

this.TestCase( SECTION,
"Math.abs( '-Infinity' )",
Infinity,
Math.abs('-Infinity') );

//test();

},

/**
File Name:          15.8.2.10.js
ECMA Section:       15.8.2.10  Math.log(x)
Description:        return an approximiation to the natural logarithm of
the argument.
special cases:
-   if arg is NaN       result is NaN
-   if arg is <0        result is NaN
-   if arg is 0 or -0   result is -Infinity
-   if arg is 1         result is 0
-   if arg is Infinity  result is Infinity
Author:             christine@netscape.com
Date:               7 july 1997
*/
test_15_8_2_10: function() {
var SECTION = "15.8.2.10";
var VERSION = "ECMA_1";
var TITLE   = "Math.log(x)";
var BUGNUMBER = "77391";

//startTest();

//writeHeaderToLog( SECTION + " "+ TITLE);


this.TestCase( SECTION,
"Math.log.length",
1,
Math.log.length );


this.TestCase( SECTION,
"Math.log()",
Number.NaN,
Math.log() );

this.TestCase( SECTION,
"Math.log(void 0)",
Number.NaN,
Math.log(void 0) );

this.TestCase( SECTION,
"Math.log(null)",
Number.NEGATIVE_INFINITY,
Math.log(null) );

this.TestCase( SECTION,
"Math.log(true)",
0,
Math.log(true) );

this.TestCase( SECTION,
"Math.log(false)",
-Infinity,
Math.log(false) );

this.TestCase( SECTION,
"Math.log('0')",
-Infinity,
Math.log('0') );

this.TestCase( SECTION,
"Math.log('1')",
0,
Math.log('1') );

this.TestCase( SECTION,
"Math.log('Infinity')",
Infinity,
Math.log("Infinity") );


this.TestCase( SECTION,
"Math.log(NaN)",
Number.NaN,
Math.log(Number.NaN) );

this.TestCase( SECTION,
"Math.log(-0.0000001)",
Number.NaN,
Math.log(-0.000001)  );

this.TestCase( SECTION,
"Math.log(-1)",
Number.NaN,
Math.log(-1)  );

this.TestCase( SECTION,
"Math.log(0)",
Number.NEGATIVE_INFINITY,
Math.log(0) );

this.TestCase( SECTION,
"Math.log(-0)",
Number.NEGATIVE_INFINITY,
Math.log(-0));

this.TestCase( SECTION,
"Math.log(1)",
0,
Math.log(1) );

this.TestCase( SECTION,
"Math.log(Infinity)",
Number.POSITIVE_INFINITY,
Math.log(Number.POSITIVE_INFINITY) );

this.TestCase( SECTION,
"Math.log(-Infinity)",
Number.NaN,
Math.log(Number.NEGATIVE_INFINITY) );

//test();

},

/**
File Name:          15.8.2.11.js
ECMA Section:       15.8.2.11 Math.max(x, y)
Description:        return the smaller of the two arguments.
special cases:
- if x is NaN or y is NaN   return NaN
- if x < y                  return x
- if y > x                  return y
- if x is +0 and y is +0    return +0
- if x is +0 and y is -0    return -0
- if x is -0 and y is +0    return -0
- if x is -0 and y is -0    return -0
Author:             christine@netscape.com
Date:               7 july 1997
*/
test_15_8_2_11: function() {
var SECTION = "15.8.2.11";
var VERSION = "ECMA_1";
var TITLE   = "Math.max(x, y)";
var BUGNUMBER="76439";
//startTest();

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Math.max.length",
2,
Math.max.length );

this.TestCase( SECTION,
"Math.max()",
-Infinity,
Math.max() );

this.TestCase( SECTION,
"Math.max(void 0, 1)",
Number.NaN,
Math.max( void 0, 1 ) );

this.TestCase( SECTION,
"Math.max(void 0, void 0)",
Number.NaN,
Math.max( void 0, void 0 ) );

this.TestCase( SECTION,
"Math.max(null, 1)",
1,
Math.max( null, 1 ) );

this.TestCase( SECTION,
"Math.max(-1, null)",
0,
Math.max( -1, null ) );

this.TestCase( SECTION,
"Math.max(true, false)",
1,
Math.max(true,false) );

this.TestCase( SECTION,
"Math.max('-99','99')",
99,
Math.max( "-99","99") );

this.TestCase( SECTION,
"Math.max(NaN, Infinity)",
Number.NaN,
Math.max(Number.NaN,Number.POSITIVE_INFINITY) );

this.TestCase( SECTION,
"Math.max(NaN, 0)",
Number.NaN,
Math.max(Number.NaN, 0) );

this.TestCase( SECTION,
"Math.max('a string', 0)",
Number.NaN,
Math.max("a string", 0) );

this.TestCase( SECTION,
"Math.max(NaN, 1)",
Number.NaN,
Math.max(Number.NaN,1) );

this.TestCase( SECTION,
"Math.max('a string',Infinity)",
Number.NaN,
Math.max("a string", Number.POSITIVE_INFINITY) );

this.TestCase( SECTION,
"Math.max(Infinity, NaN)",
Number.NaN,
Math.max( Number.POSITIVE_INFINITY, Number.NaN) );

this.TestCase( SECTION,
"Math.max(NaN, NaN)",
Number.NaN,
Math.max(Number.NaN, Number.NaN) );

this.TestCase( SECTION,
"Math.max(0,NaN)",
Number.NaN,
Math.max(0,Number.NaN) );

this.TestCase( SECTION,
"Math.max(1, NaN)",
Number.NaN,
Math.max(1, Number.NaN) );

this.TestCase( SECTION,
"Math.max(0,0)",
0,
Math.max(0,0) );

this.TestCase( SECTION,
"Math.max(0,-0)",
0,
Math.max(0,-0) );

this.TestCase( SECTION,
"Math.max(-0,0)",
0,
Math.max(-0,0) );

this.TestCase( SECTION,
"Math.max(-0,-0)",
-0,
Math.max(-0,-0) );

this.TestCase( SECTION,
"Infinity/Math.max(-0,-0)",
-Infinity,
Infinity/Math.max(-0,-0) );

this.TestCase( SECTION,
"Math.max(Infinity, Number.MAX_VALUE)", Number.POSITIVE_INFINITY,
Math.max(Number.POSITIVE_INFINITY, Number.MAX_VALUE) );

this.TestCase( SECTION,
"Math.max(Infinity, Infinity)",
Number.POSITIVE_INFINITY,
Math.max(Number.POSITIVE_INFINITY,Number.POSITIVE_INFINITY) );

this.TestCase( SECTION,
"Math.max(-Infinity,-Infinity)",
Number.NEGATIVE_INFINITY,
Math.max(Number.NEGATIVE_INFINITY,Number.NEGATIVE_INFINITY) );

this.TestCase( SECTION,
"Math.max(1,.99999999999999)",
1,
Math.max(1,.99999999999999) );

this.TestCase( SECTION,
"Math.max(-1,-.99999999999999)",
-.99999999999999,
Math.max(-1,-.99999999999999) );

//test();

},

/**
File Name:          15.8.2.12.js
ECMA Section:       15.8.2.12 Math.min(x, y)
Description:        return the smaller of the two arguments.
special cases:
- if x is NaN or y is NaN   return NaN
- if x < y                  return x
- if y > x                  return y
- if x is +0 and y is +0    return +0
- if x is +0 and y is -0    return -0
- if x is -0 and y is +0    return -0
- if x is -0 and y is -0    return -0
Author:             christine@netscape.com
Date:               7 july 1997
*/
test_15_8_2_12: function() {
var SECTION = "15.8.2.12";
var VERSION = "ECMA_1";
var TITLE   = "Math.min(x, y)";
var BUGNUMBER="76439";
//startTest();

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Math.min.length",
2,
Math.min.length );

this.TestCase( SECTION,
"Math.min()",
Infinity,
Math.min() );

this.TestCase( SECTION,
"Math.min(void 0, 1)",
Number.NaN,
Math.min( void 0, 1 ) );

this.TestCase( SECTION,
"Math.min(void 0, void 0)",
Number.NaN,
Math.min( void 0, void 0 ) );

this.TestCase( SECTION,
"Math.min(null, 1)",
0,
Math.min( null, 1 ) );

this.TestCase( SECTION,
"Math.min(-1, null)",
-1,
Math.min( -1, null ) );

this.TestCase( SECTION,
"Math.min(true, false)",
0,
Math.min(true,false) );

this.TestCase( SECTION,
"Math.min('-99','99')",
-99,
Math.min( "-99","99") );

this.TestCase( SECTION,
"Math.min(NaN,0)",
Number.NaN,
Math.min(Number.NaN,0) );

this.TestCase( SECTION,
"Math.min(NaN,1)",
Number.NaN,
Math.min(Number.NaN,1) );

this.TestCase( SECTION,
"Math.min(NaN,-1)",
Number.NaN,
Math.min(Number.NaN,-1) );

this.TestCase( SECTION,
"Math.min(0,NaN)",
Number.NaN,
Math.min(0,Number.NaN) );

this.TestCase( SECTION,
"Math.min(1,NaN)",
Number.NaN,
Math.min(1,Number.NaN) );

this.TestCase( SECTION,
"Math.min(-1,NaN)",
Number.NaN,
Math.min(-1,Number.NaN) );

this.TestCase( SECTION,
"Math.min(NaN,NaN)",
Number.NaN,
Math.min(Number.NaN,Number.NaN) );

this.TestCase( SECTION,
"Math.min(1,1.0000000001)",
1,
Math.min(1,1.0000000001) );

this.TestCase( SECTION,
"Math.min(1.0000000001,1)",
1,
Math.min(1.0000000001,1) );

this.TestCase( SECTION,
"Math.min(0,0)",
0,
Math.min(0,0) );

this.TestCase( SECTION,
"Math.min(0,-0)",
-0,
Math.min(0,-0) );

this.TestCase( SECTION,
"Math.min(-0,-0)",
-0,
Math.min(-0,-0) );

this.TestCase( SECTION,
"Infinity/Math.min(0,-0)",
-Infinity,
Infinity/Math.min(0,-0) );

this.TestCase( SECTION,
"Infinity/Math.min(-0,-0)",
-Infinity,
Infinity/Math.min(-0,-0) );

//test();

},

/**
File Name:          15.8.2.13.js
ECMA Section:       15.8.2.13 Math.pow(x, y)
Description:        return an approximation to the result of x
to the power of y.  there are many special cases;
refer to the spec.
Author:             christine@netscape.com
Date:               9 july 1997
*/
test_15_8_2_13: function() {
var SECTION = "15.8.2.13";
var VERSION = "ECMA_1";
var TITLE   = "Math.pow(x, y)";
var BUGNUMBER="77141";
//startTest();

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Math.pow.length",
2,
Math.pow.length );

this.TestCase( SECTION,
"Math.pow()",
Number.NaN,
Math.pow() );

this.TestCase( SECTION,
"Math.pow(null, null)",
1,
Math.pow(null,null) );

this.TestCase( SECTION,
"Math.pow(void 0, void 0)",
Number.NaN,
Math.pow(void 0, void 0));

this.TestCase( SECTION,
"Math.pow(true, false)",
1,
Math.pow(true, false) );

this.TestCase( SECTION,
"Math.pow(false,true)",
0,
Math.pow(false,true) );

this.TestCase( SECTION,
"Math.pow('2','32')",
4294967296,
Math.pow('2','32') );

this.TestCase( SECTION,
"Math.pow(1,NaN)",
Number.NaN,
Math.pow(1,Number.NaN) );

this.TestCase( SECTION,
"Math.pow(0,NaN)",
Number.NaN,
Math.pow(0,Number.NaN) );

this.TestCase( SECTION,
"Math.pow(NaN,0)",
1,
Math.pow(Number.NaN,0) );

this.TestCase( SECTION,
"Math.pow(NaN,-0)",
1,
Math.pow(Number.NaN,-0) );

this.TestCase( SECTION,
"Math.pow(NaN,1)",
Number.NaN,
Math.pow(Number.NaN, 1) );

this.TestCase( SECTION,
"Math.pow(NaN,.5)",
Number.NaN,
Math.pow(Number.NaN, .5) );

this.TestCase( SECTION,
"Math.pow(1.00000001, Infinity)",
Number.POSITIVE_INFINITY,
Math.pow(1.00000001, Number.POSITIVE_INFINITY) );

this.TestCase( SECTION,
"Math.pow(1.00000001, -Infinity)",
0,
Math.pow(1.00000001, Number.NEGATIVE_INFINITY) );

this.TestCase( SECTION,
"Math.pow(-1.00000001, Infinity)",
Number.POSITIVE_INFINITY,
Math.pow(-1.00000001,Number.POSITIVE_INFINITY) );

this.TestCase( SECTION,
"Math.pow(-1.00000001, -Infinity)",
0,
Math.pow(-1.00000001,Number.NEGATIVE_INFINITY) );

this.TestCase( SECTION,
"Math.pow(1, Infinity)",
Number.NaN,
Math.pow(1, Number.POSITIVE_INFINITY) );

this.TestCase( SECTION,
"Math.pow(1, -Infinity)",
Number.NaN,
Math.pow(1, Number.NEGATIVE_INFINITY) );

this.TestCase( SECTION,
"Math.pow(-1, Infinity)",
Number.NaN,
Math.pow(-1, Number.POSITIVE_INFINITY) );

this.TestCase( SECTION,
"Math.pow(-1, -Infinity)",
Number.NaN,
Math.pow(-1, Number.NEGATIVE_INFINITY) );

this.TestCase( SECTION,
"Math.pow(.0000000009, Infinity)",
0,
Math.pow(.0000000009, Number.POSITIVE_INFINITY) );

this.TestCase( SECTION,
"Math.pow(-.0000000009, Infinity)",
0,
Math.pow(-.0000000009, Number.POSITIVE_INFINITY) );

this.TestCase( SECTION,
"Math.pow(.0000000009, -Infinity)",
Number.POSITIVE_INFINITY,
Math.pow(-.0000000009, Number.NEGATIVE_INFINITY) );

this.TestCase( SECTION,
"Math.pow(Infinity, .00000000001)",
Number.POSITIVE_INFINITY,
Math.pow(Number.POSITIVE_INFINITY,.00000000001) );

this.TestCase( SECTION,
"Math.pow(Infinity, 1)",
Number.POSITIVE_INFINITY,
Math.pow(Number.POSITIVE_INFINITY, 1) );

this.TestCase( SECTION,
"Math.pow(Infinity, -.00000000001)",
0,
Math.pow(Number.POSITIVE_INFINITY, -.00000000001) );

this.TestCase( SECTION,
"Math.pow(Infinity, -1)",
0,
Math.pow(Number.POSITIVE_INFINITY, -1) );

this.TestCase( SECTION,
"Math.pow(-Infinity, 1)",
Number.NEGATIVE_INFINITY,
Math.pow(Number.NEGATIVE_INFINITY, 1) );

this.TestCase( SECTION,
"Math.pow(-Infinity, 333)",
Number.NEGATIVE_INFINITY,
Math.pow(Number.NEGATIVE_INFINITY, 333) );

this.TestCase( SECTION,
"Math.pow(Infinity, 2)",
Number.POSITIVE_INFINITY,
Math.pow(Number.POSITIVE_INFINITY, 2) );

this.TestCase( SECTION,
"Math.pow(-Infinity, 666)",
Number.POSITIVE_INFINITY,
Math.pow(Number.NEGATIVE_INFINITY, 666) );

this.TestCase( SECTION,
"Math.pow(-Infinity, 0.5)",
Number.POSITIVE_INFINITY,
Math.pow(Number.NEGATIVE_INFINITY, 0.5) );

this.TestCase( SECTION,
"Math.pow(-Infinity, Infinity)",
Number.POSITIVE_INFINITY,
Math.pow(Number.NEGATIVE_INFINITY, Number.POSITIVE_INFINITY) );

this.TestCase( SECTION,
"Math.pow(-Infinity, -1)",
-0,
Math.pow(Number.NEGATIVE_INFINITY, -1) );

this.TestCase( SECTION,
"Infinity/Math.pow(-Infinity, -1)",
-Infinity,
Infinity/Math.pow(Number.NEGATIVE_INFINITY, -1) );

this.TestCase( SECTION,
"Math.pow(-Infinity, -3)",
-0,
Math.pow(Number.NEGATIVE_INFINITY, -3) );

this.TestCase( SECTION,
"Math.pow(-Infinity, -2)",
0,
Math.pow(Number.NEGATIVE_INFINITY, -2) );

this.TestCase( SECTION,
"Math.pow(-Infinity, -0.5)",
0,
Math.pow(Number.NEGATIVE_INFINITY,-0.5) );

this.TestCase( SECTION,
"Math.pow(-Infinity, -Infinity)",
0,
Math.pow(Number.NEGATIVE_INFINITY, Number.NEGATIVE_INFINITY) );

this.TestCase( SECTION,
"Math.pow(0, 1)",
0,
Math.pow(0,1) );

this.TestCase( SECTION,
"Math.pow(0, 0)",
1,
Math.pow(0,0) );

this.TestCase( SECTION,
"Math.pow(1, 0)",
1,
Math.pow(1,0) );

this.TestCase( SECTION,
"Math.pow(-1, 0)",
1,
Math.pow(-1,0) );

this.TestCase( SECTION,
"Math.pow(0, 0.5)",
0,
Math.pow(0,0.5) );

this.TestCase( SECTION,
"Math.pow(0, 1000)",
0,
Math.pow(0,1000) );

this.TestCase( SECTION,
"Math.pow(0, Infinity)",
0,
Math.pow(0, Number.POSITIVE_INFINITY) );

this.TestCase( SECTION,
"Math.pow(0, -1)",
Number.POSITIVE_INFINITY,
Math.pow(0, -1) );

this.TestCase( SECTION,
"Math.pow(0, -0.5)",
Number.POSITIVE_INFINITY,
Math.pow(0, -0.5) );

this.TestCase( SECTION,
"Math.pow(0, -1000)",
Number.POSITIVE_INFINITY,
Math.pow(0, -1000) );

this.TestCase( SECTION,
"Math.pow(0, -Infinity)",
Number.POSITIVE_INFINITY,
Math.pow(0, Number.NEGATIVE_INFINITY) );

this.TestCase( SECTION,
"Math.pow(-0, 1)",
-0,
Math.pow(-0, 1) );

this.TestCase( SECTION,
"Math.pow(-0, 3)",
-0,
Math.pow(-0,3) );

this.TestCase( SECTION,
"Infinity/Math.pow(-0, 1)",
-Infinity,
Infinity/Math.pow(-0, 1) );

this.TestCase( SECTION,
"Infinity/Math.pow(-0, 3)",
-Infinity,
Infinity/Math.pow(-0,3) );

this.TestCase( SECTION,
"Math.pow(-0, 2)",
0,
Math.pow(-0,2) );

this.TestCase( SECTION,
"Math.pow(-0, Infinity)",
0,
Math.pow(-0, Number.POSITIVE_INFINITY) );

this.TestCase( SECTION,
"Math.pow(-0, -1)",
Number.NEGATIVE_INFINITY,
Math.pow(-0, -1) );

this.TestCase( SECTION,
"Math.pow(-0, -10001)",
Number.NEGATIVE_INFINITY,
Math.pow(-0, -10001) );

this.TestCase( SECTION,
"Math.pow(-0, -2)",
Number.POSITIVE_INFINITY,
Math.pow(-0, -2) );

this.TestCase( SECTION,
"Math.pow(-0, 0.5)",
0,
Math.pow(-0, 0.5) );

this.TestCase( SECTION,
"Math.pow(-0, Infinity)",
0,
Math.pow(-0, Number.POSITIVE_INFINITY) );

this.TestCase( SECTION,
"Math.pow(-1, 0.5)",
Number.NaN,
Math.pow(-1, 0.5) );

this.TestCase( SECTION,
"Math.pow(-1, NaN)",
Number.NaN,
Math.pow(-1, Number.NaN) );

this.TestCase( SECTION,
"Math.pow(-1, -0.5)",
Number.NaN,
Math.pow(-1, -0.5) );

//test();

},

/**
File Name:          15.8.2.14.js
ECMA Section:       15.8.2.14 Math.random()
returns a number value x with a positive sign
with 1 > x >= 0 with approximately uniform
distribution over that range, using an
implementation-dependent algorithm or strategy.
This function takes no arguments.

Description:
Author:             christine@netscape.com
Date:               16 september 1997
*/
test_15_8_2_14: function() {
var SECTION = "15.8.2.14";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Math.random()";

//writeHeaderToLog( SECTION + " "+ TITLE);

for ( var item = 0; item < 100; item++ ) {
var testcase = new Object();

testcase = this.TestCaseAlloc( SECTION,
"Math.random()",
"pass",
null );
testcase.reason = Math.random();
testcase.actual = "pass";

if ( ! ( testcase.reason >= 0) ) {
testcase.actual = "fail";
}

if ( ! (testcase.reason < 1) ) {
testcase.actual = "fail";
}

this.assertTrue(testcase.expect == testcase.actual);
}

//test();

},

/**
File Name:          15.8.2.15.js
ECMA Section:       15.8.2.15  Math.round(x)
Description:        return the greatest number value that is closest to the
argument and is an integer.  if two integers are equally
close to the argument. then the result is the number value
that is closer to Infinity.  if the argument is an integer,
return the argument.
special cases:
- if x is NaN       return NaN
- if x = +0         return +0
- if x = -0          return -0
- if x = Infinity   return Infinity
- if x = -Infinity  return -Infinity
- if 0 < x < 0.5    return 0
- if -0.5 <= x < 0  return -0
example:
Math.round( 3.5 ) == 4
Math.round( -3.5 ) == 3
also:
- Math.round(x) == Math.floor( x + 0.5 )
except if x = -0.  in that case, Math.round(x) = -0

and Math.floor( x+0.5 ) = +0


Author:             christine@netscape.com
Date:               7 july 1997
*/
test_15_8_2_15: function() {
var SECTION = "15.8.2.15";
var VERSION = "ECMA_1";
var TITLE   = "Math.round(x)";
var BUGNUMBER="331411";

var EXCLUDE = "true";

//startTest();

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Math.round.length",
1,
Math.round.length );

this.TestCase( SECTION,
"Math.round()",
Number.NaN,
Math.round() );

this.TestCase( SECTION,
"Math.round(null)",
0,
Math.round(0) );

this.TestCase( SECTION,
"Math.round(void 0)",
Number.NaN,
Math.round(void 0) );

this.TestCase( SECTION,
"Math.round(true)",
1,
Math.round(true) );

this.TestCase( SECTION,
"Math.round(false)",
0,
Math.round(false) );

this.TestCase( SECTION,
"Math.round('.99999')",
1,
Math.round('.99999') );

this.TestCase( SECTION,
"Math.round('12345e-2')",
123,
Math.round('12345e-2') );

this.TestCase( SECTION,
"Math.round(NaN)",
Number.NaN,
Math.round(Number.NaN) );

this.TestCase( SECTION,
"Math.round(0)",
0,
Math.round(0) );

this.TestCase( SECTION,
"Math.round(-0)",
-0,
Math.round(-0));

this.TestCase( SECTION,
"Infinity/Math.round(-0)",
-Infinity,
Infinity/Math.round(-0) );

this.TestCase( SECTION,
"Math.round(Infinity)",
Number.POSITIVE_INFINITY,
Math.round(Number.POSITIVE_INFINITY));

this.TestCase( SECTION,
"Math.round(-Infinity)",
Number.NEGATIVE_INFINITY,
Math.round(Number.NEGATIVE_INFINITY));

this.TestCase( SECTION,
"Math.round(0.49)",
0,
Math.round(0.49));

this.TestCase( SECTION,
"Math.round(0.5)",
1,
Math.round(0.5));

this.TestCase( SECTION,
"Math.round(0.51)",
1,
Math.round(0.51));

this.TestCase( SECTION,
"Math.round(-0.49)",
-0,
Math.round(-0.49));

this.TestCase( SECTION,
"Math.round(-0.5)",
-0,
Math.round(-0.5));

this.TestCase( SECTION,
"Infinity/Math.round(-0.49)",
-Infinity,
Infinity/Math.round(-0.49));

this.TestCase( SECTION,
"Infinity/Math.round(-0.5)",
-Infinity,
Infinity/Math.round(-0.5));

this.TestCase( SECTION,
"Math.round(-0.51)",
-1,
Math.round(-0.51));

this.TestCase( SECTION,
"Math.round(3.5)",
4,
Math.round(3.5));

this.TestCase( SECTION,
"Math.round(-3.5)",
-3,
Math.round(-3));

//test();

},

/**
File Name:          15.8.2.16.js
ECMA Section:       15.8.2.16 sin( x )
Description:        return an approximation to the sine of the
argument.  argument is expressed in radians
Author:             christine@netscape.com
Date:               7 july 1997

*/
test_15_8_2_16: function() {
var SECTION = "15.8.2.16";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Math.sin(x)";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Math.sin.length",
1,
Math.sin.length );

this.TestCase( SECTION,
"Math.sin()",
Number.NaN,
Math.sin() );

this.TestCase( SECTION,
"Math.sin(null)",
0,
Math.sin(null) );

this.TestCase( SECTION,
"Math.sin(void 0)",
Number.NaN,
Math.sin(void 0) );

this.TestCase( SECTION,
"Math.sin(false)",
0,
Math.sin(false) );

this.TestCase( SECTION,
"Math.sin('2.356194490192')",
0.7071067811865,
Math.sin('2.356194490192') );

this.TestCase( SECTION,
"Math.sin(NaN)",
Number.NaN,
Math.sin(Number.NaN) );

this.TestCase( SECTION,
"Math.sin(0)",
0,
Math.sin(0) );

this.TestCase( SECTION,
"Math.sin(-0)",
-0,
Math.sin(-0));

this.TestCase( SECTION,
"Math.sin(Infinity)",
Number.NaN,
Math.sin(Number.POSITIVE_INFINITY));

this.TestCase( SECTION,
"Math.sin(-Infinity)",
Number.NaN,
Math.sin(Number.NEGATIVE_INFINITY));

this.TestCase( SECTION,
"Math.sin(0.7853981633974)",
0.7071067811865,
Math.sin(0.7853981633974));

this.TestCase( SECTION,
"Math.sin(1.570796326795)",
1,
Math.sin(1.570796326795));

this.TestCase( SECTION,
"Math.sin(2.356194490192)",
0.7071067811865,
Math.sin(2.356194490192));

this.TestCase( SECTION,
"Math.sin(3.14159265359)",
0,
Math.sin(3.14159265359));

//test();

},

/**
File Name:          15.8.2.17.js
ECMA Section:       15.8.2.17  Math.sqrt(x)
Description:        return an approximation to the squareroot of the argument.
special cases:
-   if x is NaN         return NaN
-   if x < 0            return NaN
-   if x == 0           return 0
-   if x == -0          return -0
-   if x == Infinity    return Infinity
Author:             christine@netscape.com
Date:               7 july 1997
*/
test_15_8_2_17: function() {
var SECTION = "15.8.2.17";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Math.sqrt(x)";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Math.sqrt.length",
1,
Math.sqrt.length );

this.TestCase( SECTION,
"Math.sqrt()",
Number.NaN,
Math.sqrt() );

this.TestCase( SECTION,
"Math.sqrt(void 0)",
Number.NaN,
Math.sqrt(void 0) );

this.TestCase( SECTION,
"Math.sqrt(null)",
0,
Math.sqrt(null) );

this.TestCase( SECTION,
"Math.sqrt(true)",
1,
Math.sqrt(1) );

this.TestCase( SECTION,
"Math.sqrt(false)",
0,
Math.sqrt(false) );

this.TestCase( SECTION,
"Math.sqrt('225')",
15,
Math.sqrt('225') );

this.TestCase( SECTION,
"Math.sqrt(NaN)",
Number.NaN,
Math.sqrt(Number.NaN) );

this.TestCase( SECTION,
"Math.sqrt(-Infinity)",
Number.NaN,
Math.sqrt(Number.NEGATIVE_INFINITY));

this.TestCase( SECTION,
"Math.sqrt(-1)",
Number.NaN,
Math.sqrt(-1));

this.TestCase( SECTION,
"Math.sqrt(-0.5)",
Number.NaN,
Math.sqrt(-0.5));

this.TestCase( SECTION,
"Math.sqrt(0)",
0,
Math.sqrt(0));

this.TestCase( SECTION,
"Math.sqrt(-0)",
-0,
Math.sqrt(-0));

this.TestCase( SECTION,
"Infinity/Math.sqrt(-0)",
-Infinity,
Infinity/Math.sqrt(-0) );

this.TestCase( SECTION,
"Math.sqrt(Infinity)",
Number.POSITIVE_INFINITY,
Math.sqrt(Number.POSITIVE_INFINITY));

this.TestCase( SECTION,
"Math.sqrt(1)",
1,
Math.sqrt(1));

this.TestCase( SECTION,
"Math.sqrt(2)",
Math.SQRT2,
Math.sqrt(2));

this.TestCase( SECTION,
"Math.sqrt(0.5)",
Math.SQRT1_2,
Math.sqrt(0.5));

this.TestCase( SECTION,
"Math.sqrt(4)",
2,
Math.sqrt(4));

this.TestCase( SECTION,
"Math.sqrt(9)",
3,
Math.sqrt(9));

this.TestCase( SECTION,
"Math.sqrt(16)",
4,
Math.sqrt(16));

this.TestCase( SECTION,
"Math.sqrt(25)",
5,
Math.sqrt(25));

this.TestCase( SECTION,
"Math.sqrt(36)",
6,
Math.sqrt(36));

this.TestCase( SECTION,
"Math.sqrt(49)",
7,
Math.sqrt(49));

this.TestCase( SECTION,
"Math.sqrt(64)",
8,
Math.sqrt(64));

this.TestCase( SECTION,
"Math.sqrt(256)",
16,
Math.sqrt(256));

this.TestCase( SECTION,
"Math.sqrt(10000)",
100,
Math.sqrt(10000));

this.TestCase( SECTION,
"Math.sqrt(65536)",
256,
Math.sqrt(65536));

this.TestCase( SECTION,
"Math.sqrt(0.09)",
0.3,
Math.sqrt(0.09));

this.TestCase( SECTION,
"Math.sqrt(0.01)",
0.1,
Math.sqrt(0.01));

this.TestCase( SECTION,
"Math.sqrt(0.00000001)",
0.0001,
Math.sqrt(0.00000001));

//test();

},

/**
File Name:          15.8.2.18.js
ECMA Section:       15.8.2.18 tan( x )
Description:        return an approximation to the tan of the
argument.  argument is expressed in radians
special cases:
- if x is NaN           result is NaN
- if x is 0             result is 0
- if x is -0            result is -0
- if x is Infinity or -Infinity result is NaN
Author:             christine@netscape.com
Date:               7 july 1997
*/
test_15_8_2_18: function() {
var SECTION = "15.8.2.18";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Math.tan(x)";
var EXCLUDE = "true";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Math.tan.length",
1,
Math.tan.length );

this.TestCase( SECTION,
"Math.tan()",
Number.NaN,
Math.tan() );

this.TestCase( SECTION,
"Math.tan(void 0)",
Number.NaN,
Math.tan(void 0));

this.TestCase( SECTION,
"Math.tan(null)",
0,
Math.tan(null) );

this.TestCase( SECTION,
"Math.tan(false)",
0,
Math.tan(false) );

this.TestCase( SECTION,
"Math.tan(NaN)",
Number.NaN,
Math.tan(Number.NaN) );

this.TestCase( SECTION,
"Math.tan(0)",
0,
Math.tan(0));

this.TestCase( SECTION,
"Math.tan(-0)",
-0,
Math.tan(-0));

this.TestCase( SECTION,
"Math.tan(Infinity)",
Number.NaN,
Math.tan(Number.POSITIVE_INFINITY));

this.TestCase( SECTION,
"Math.tan(-Infinity)",
Number.NaN,
Math.tan(Number.NEGATIVE_INFINITY));

this.TestCase( SECTION,
"Math.tan(Math.PI/4)",
1,
Math.tan(Math.PI/4));

this.TestCase( SECTION,
"Math.tan(3*Math.PI/4)",
-1,
Math.tan(3*Math.PI/4));

this.TestCase( SECTION,
"Math.tan(Math.PI)",
-0,
Math.tan(Math.PI));

this.TestCase( SECTION,
"Math.tan(5*Math.PI/4)",
1,
Math.tan(5*Math.PI/4));

this.TestCase( SECTION,
"Math.tan(7*Math.PI/4)",
-1,
Math.tan(7*Math.PI/4));

this.TestCase( SECTION,
"Infinity/Math.tan(-0)",
-Infinity,
Infinity/Math.tan(-0) );

/*
Arctan (x) ~ PI/2 - 1/x   for large x.  For x = 1.6x10^16, 1/x is about the last binary digit of double precision PI/2.
That is to say, perturbing PI/2 by this much is about the smallest rounding error possible.

This suggests that the answer Christine is getting and a real Infinity are "adjacent" results from the tangent function.  I
suspect that tan (PI/2 + one ulp) is a negative result about the same size as tan (PI/2) and that this pair are the closest
results to infinity that the algorithm can deliver.

In any case, my call is that the answer we're seeing is "right".  I suggest the test pass on any result this size or larger.
= C =
*/

this.TestCase( SECTION,
"Math.tan(3*Math.PI/2) >= 5443000000000000",
true,
Math.tan(3*Math.PI/2) >= 5443000000000000 );

this.TestCase( SECTION,
"Math.tan(Math.PI/2) >= 5443000000000000",
true,
Math.tan(Math.PI/2) >= 5443000000000000 );

//test();

},

/**
File Name:          15.8.2.2.js
ECMA Section:       15.8.2.2 acos( x )
Description:        return an approximation to the arc cosine of the
argument.  the result is expressed in radians and
range is from +0 to +PI.  special cases:
- if x is NaN, return NaN
- if x > 1, the result is NaN
- if x < -1, the result is NaN
- if x == 1, the result is +0
Author:             christine@netscape.com
Date:               7 july 1997
*/
test_15_8_2_2: function() {
var SECTION = "15.8.2.2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Math.acos()";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Math.acos.length",
1,
Math.acos.length );

this.TestCase( SECTION,
"Math.acos(void 0)",
Number.NaN,
Math.acos(void 0) );

this.TestCase( SECTION,
"Math.acos()",
Number.NaN,
Math.acos() );

this.TestCase( SECTION,
"Math.acos(null)",
Math.PI/2,
Math.acos(null) );

this.TestCase( SECTION,
"Math.acos(NaN)",
Number.NaN,
Math.acos(Number.NaN) );

this.TestCase( SECTION,
"Math.acos(a string)",
Number.NaN,
Math.acos("a string") );

this.TestCase( SECTION,
"Math.acos('0')",
Math.PI/2,
Math.acos('0') );

this.TestCase( SECTION,
"Math.acos('1')",
0,
Math.acos('1') );

this.TestCase( SECTION,
"Math.acos('-1')",
Math.PI,
Math.acos('-1') );

this.TestCase( SECTION,
"Math.acos(1.00000001)",
Number.NaN,
Math.acos(1.00000001) );

this.TestCase( SECTION,
"Math.acos(11.00000001)",
Number.NaN,
Math.acos(-1.00000001) );

this.TestCase( SECTION,
"Math.acos(1)",
0,
Math.acos(1)          );

this.TestCase( SECTION,
"Math.acos(-1)",
Math.PI,
Math.acos(-1)         );

this.TestCase( SECTION,
"Math.acos(0)",
Math.PI/2,
Math.acos(0)          );

this.TestCase( SECTION,
"Math.acos(-0)",
Math.PI/2,
Math.acos(-0)         );

this.TestCase( SECTION,
"Math.acos(Math.SQRT1_2)",
Math.PI/4,
Math.acos(Math.SQRT1_2));

this.TestCase( SECTION,
"Math.acos(-Math.SQRT1_2)",
Math.PI/4*3,
Math.acos(-Math.SQRT1_2));

this.TestCase( SECTION,
"Math.acos(0.9999619230642)",
Math.PI/360,
Math.acos(0.9999619230642));

this.TestCase( SECTION,
"Math.acos(-3.0)",
Number.NaN,
Math.acos(-3.0));

//test();

},

/**
File Name:          15.8.2.3.js
ECMA Section:       15.8.2.3 asin( x )
Description:        return an approximation to the arc sine of the
argument.  the result is expressed in radians and
range is from -PI/2 to +PI/2.  special cases:
- if x is NaN,  the result is NaN
- if x > 1,     the result is NaN
- if x < -1,    the result is NaN
- if x == +0,   the result is +0
- if x == -0,   the result is -0
Author:             christine@netscape.com
Date:               7 july 1997

*/
test_15_8_2_3: function() {
var SECTION = "15.8.2.3";
var VERSION = "ECMA_1";
//startTest();

var TITLE   = "Math.asin()";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Math.asin()",
Number.NaN,
Math.asin() );

this.TestCase( SECTION,
"Math.asin(void 0)",
Number.NaN,
Math.asin(void 0) );

this.TestCase( SECTION,
"Math.asin(null)",
0,
Math.asin(null) );

this.TestCase( SECTION,
"Math.asin(NaN)",
Number.NaN,
Math.asin(Number.NaN)   );

this.TestCase( SECTION,
"Math.asin('string')",
Number.NaN,
Math.asin("string")     );

this.TestCase( SECTION,
"Math.asin('0')",
0,
Math.asin("0") );

this.TestCase( SECTION,
"Math.asin('1')",
Math.PI/2,
Math.asin("1") );

this.TestCase( SECTION,
"Math.asin('-1')",
-Math.PI/2,
Math.asin("-1") );

this.TestCase( SECTION,
"Math.asin(Math.SQRT1_2+'')",
Math.PI/4,
Math.asin(Math.SQRT1_2+'') );

this.TestCase( SECTION,
"Math.asin(-Math.SQRT1_2+'')",
-Math.PI/4,
Math.asin(-Math.SQRT1_2+'') );

this.TestCase( SECTION,
"Math.asin(1.000001)",
Number.NaN,
Math.asin(1.000001)     );

this.TestCase( SECTION,
"Math.asin(-1.000001)",
Number.NaN,
Math.asin(-1.000001)    );

this.TestCase( SECTION,
"Math.asin(0)",
0,
Math.asin(0)            );

this.TestCase( SECTION,
"Math.asin(-0)",
-0,
Math.asin(-0)           );

this.TestCase( SECTION,
"Infinity/Math.asin(-0)",
-Infinity,
Infinity/Math.asin(-0) );

this.TestCase( SECTION,
"Math.asin(1)",
Math.PI/2,
Math.asin(1)            );

this.TestCase( SECTION,
"Math.asin(-1)",
-Math.PI/2,
Math.asin(-1)            );

this.TestCase( SECTION,
"Math.asin(Math.SQRT1_2))",
Math.PI/4,
Math.asin(Math.SQRT1_2) );

this.TestCase( SECTION,
"Math.asin(-Math.SQRT1_2))",
-Math.PI/4,
Math.asin(-Math.SQRT1_2));

//test();

},

/**
File Name:          15.8.2.4.js
ECMA Section:       15.8.2.4 atan( x )
Description:        return an approximation to the arc tangent of the
argument.  the result is expressed in radians and
range is from -PI/2 to +PI/2.  special cases:
- if x is NaN,  the result is NaN
- if x == +0,   the result is +0
- if x == -0,   the result is -0
- if x == +Infinity,    the result is approximately +PI/2
- if x == -Infinity,    the result is approximately -PI/2
Author:             christine@netscape.com
Date:               7 july 1997

*/
test_15_8_2_4: function() {
var SECTION = "15.8.2.4";
var VERSION = "ECMA_1";
var TITLE   = "Math.atan()";
var BUGNUMBER="77391";

//startTest();

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Math.atan.length",
1,
Math.atan.length );

this.TestCase( SECTION,
"Math.atan()",
Number.NaN,
Math.atan() );

this.TestCase( SECTION,
"Math.atan(void 0)",
Number.NaN,
Math.atan(void 0) );

this.TestCase( SECTION,
"Math.atan(null)",
0,
Math.atan(null) );

this.TestCase( SECTION,
"Math.atan(NaN)",
Number.NaN,
Math.atan(Number.NaN) );

this.TestCase( SECTION,
"Math.atan('a string')",
Number.NaN,
Math.atan("a string") );

this.TestCase( SECTION,
"Math.atan('0')",
0,
Math.atan('0') );

this.TestCase( SECTION,
"Math.atan('1')",
Math.PI/4,
Math.atan('1') );

this.TestCase( SECTION,
"Math.atan('-1')",
-Math.PI/4,
Math.atan('-1') );

this.TestCase( SECTION,
"Math.atan('Infinity)",
Math.PI/2,
Math.atan('Infinity') );

this.TestCase( SECTION,
"Math.atan('-Infinity)",
-Math.PI/2,
Math.atan('-Infinity') );

this.TestCase( SECTION,
"Math.atan(0)",
0,
Math.atan(0)          );

this.TestCase( SECTION,
"Math.atan(-0)",
-0,
Math.atan(-0)         );

this.TestCase( SECTION,
"Infinity/Math.atan(-0)",
-Infinity,
Infinity/Math.atan(-0) );

this.TestCase( SECTION,
"Math.atan(Infinity)",
Math.PI/2,
Math.atan(Number.POSITIVE_INFINITY) );

this.TestCase( SECTION,
"Math.atan(-Infinity)",
-Math.PI/2,
Math.atan(Number.NEGATIVE_INFINITY) );

this.TestCase( SECTION,
"Math.atan(1)",
Math.PI/4,
Math.atan(1)          );

this.TestCase( SECTION,
"Math.atan(-1)",
-Math.PI/4,
Math.atan(-1)         );

//test();

},

/**
File Name:          15.8.2.5.js
ECMA Section:       15.8.2.5 atan2( y, x )
Description:

Author:             christine@netscape.com
Date:               7 july 1997

*/
test_15_8_2_5: function() {
var SECTION = "15.8.2.5";
var VERSION = "ECMA_1";
var TITLE   = "Math.atan2(x,y)";
var BUGNUMBER="76111";

//startTest();

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Math.atan2.length",
2,
Math.atan2.length );

this.TestCase( SECTION,
"Math.atan2(NaN, 0)",
Number.NaN,
Math.atan2(Number.NaN,0) );

this.TestCase( SECTION,
"Math.atan2(null, null)",
0,
Math.atan2(null, null) );

this.TestCase( SECTION,
"Math.atan2(void 0, void 0)",
Number.NaN,
Math.atan2(void 0, void 0) );

this.TestCase( SECTION,
"Math.atan2(0, NaN)",
Number.NaN,
Math.atan2(0,Number.NaN) );

this.TestCase( SECTION,
"Math.atan2(1, 0)",
Math.PI/2,
Math.atan2(1,0)          );

this.TestCase( SECTION,
"Math.atan2(1,-0)",
Math.PI/2,
Math.atan2(1,-0)         );

this.TestCase( SECTION,
"Math.atan2(0,0.001)",
0,
Math.atan2(0,0.001)      );

this.TestCase( SECTION,
"Math.atan2(0,0)",
0,
Math.atan2(0,0)          );

this.TestCase( SECTION,
"Math.atan2(0, -0)",
Math.PI,
Math.atan2(0,-0)         );

this.TestCase( SECTION,
"Math.atan2(0, -1)",
Math.PI,
Math.atan2(0, -1)        );

this.TestCase( SECTION,
"Math.atan2(-0, 1)",
-0,
Math.atan2(-0, 1)        );

this.TestCase( SECTION,
"Infinity/Math.atan2(-0, 1)",
-Infinity,
Infinity/Math.atan2(-0,1) );

this.TestCase( SECTION,
"Math.atan2(-0,	0)",
-0,
Math.atan2(-0,0)         );

this.TestCase( SECTION,
"Math.atan2(-0,	-0)",
-Math.PI,
Math.atan2(-0, -0)       );

this.TestCase( SECTION,
"Math.atan2(-0,	-1)",
-Math.PI,
Math.atan2(-0, -1)       );

this.TestCase( SECTION,
"Math.atan2(-1,	0)",
-Math.PI/2,
Math.atan2(-1, 0)        );

this.TestCase( SECTION,
"Math.atan2(-1,	-0)",
-Math.PI/2,
Math.atan2(-1, -0)       );

this.TestCase( SECTION,
"Math.atan2(1, Infinity)",
0,
Math.atan2(1, Number.POSITIVE_INFINITY) );

this.TestCase( SECTION,
"Math.atan2(1,-Infinity)",
Math.PI,
Math.atan2(1, Number.NEGATIVE_INFINITY) );

this.TestCase( SECTION,
"Math.atan2(-1, Infinity)",
-0,
Math.atan2(-1,Number.POSITIVE_INFINITY) );

this.TestCase( SECTION,
"Infinity/Math.atan2(-1, Infinity)",
-Infinity,
Infinity/Math.atan2(-1,Infinity) );

this.TestCase( SECTION,
"Math.atan2(-1,-Infinity)",
-Math.PI,
Math.atan2(-1,Number.NEGATIVE_INFINITY) );

this.TestCase( SECTION,
"Math.atan2(Infinity, 0)",
Math.PI/2,
Math.atan2(Number.POSITIVE_INFINITY, 0) );

this.TestCase( SECTION,
"Math.atan2(Infinity, 1)",
Math.PI/2,
Math.atan2(Number.POSITIVE_INFINITY, 1) );

this.TestCase( SECTION,
"Math.atan2(Infinity,-1)",
Math.PI/2,
Math.atan2(Number.POSITIVE_INFINITY,-1) );

this.TestCase( SECTION,
"Math.atan2(Infinity,-0)",
Math.PI/2,
Math.atan2(Number.POSITIVE_INFINITY,-0) );

this.TestCase( SECTION,
"Math.atan2(-Infinity, 0)",
-Math.PI/2,
Math.atan2(Number.NEGATIVE_INFINITY, 0) );

this.TestCase( SECTION,
"Math.atan2(-Infinity,-0)",
-Math.PI/2,
Math.atan2(Number.NEGATIVE_INFINITY,-0) );

this.TestCase( SECTION,
"Math.atan2(-Infinity, 1)",
-Math.PI/2,
Math.atan2(Number.NEGATIVE_INFINITY, 1) );

this.TestCase( SECTION,
"Math.atan2(-Infinity, -1)",
-Math.PI/2,
Math.atan2(Number.NEGATIVE_INFINITY,-1) );

this.TestCase( SECTION,
"Math.atan2(Infinity, Infinity)",
Math.PI/4,
Math.atan2(Number.POSITIVE_INFINITY, Number.POSITIVE_INFINITY) );

this.TestCase( SECTION,
"Math.atan2(Infinity, -Infinity)",
3*Math.PI/4,
Math.atan2(Number.POSITIVE_INFINITY, Number.NEGATIVE_INFINITY) );

this.TestCase( SECTION,
"Math.atan2(-Infinity, Infinity)",
-Math.PI/4,
Math.atan2(Number.NEGATIVE_INFINITY, Number.POSITIVE_INFINITY) );

this.TestCase( SECTION,
"Math.atan2(-Infinity, -Infinity)",
-3*Math.PI/4,
Math.atan2(Number.NEGATIVE_INFINITY, Number.NEGATIVE_INFINITY) );

this.TestCase( SECTION,
"Math.atan2(-1, 1)",
-Math.PI/4,
Math.atan2( -1, 1) );

//test();

},

/**
File Name:          15.8.2.6.js
ECMA Section:       15.8.2.6  Math.ceil(x)
Description:        return the smallest number value that is not less than the
argument and is equal to a mathematical integer.  if the
number is already an integer, return the number itself.
special cases:
- if x is NaN       return NaN
- if x = +0         return +0
- if x = 0          return -0
- if x = Infinity   return Infinity
- if x = -Infinity  return -Infinity
- if ( -1 < x < 0 ) return -0
also:
-   the value of Math.ceil(x) == -Math.ceil(-x)
Author:             christine@netscape.com
Date:               7 july 1997
*/
test_15_8_2_6: function() {
var SECTION = "15.8.2.6";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Math.ceil(x)";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Math.ceil.length",
1,
Math.ceil.length );

this.TestCase( SECTION,
"Math.ceil(NaN)",
Number.NaN,
Math.ceil(Number.NaN)   );

this.TestCase( SECTION,
"Math.ceil(null)",
0,
Math.ceil(null) );

this.TestCase( SECTION,
"Math.ceil()",
Number.NaN,
Math.ceil() );

this.TestCase( SECTION,
"Math.ceil(void 0)",
Number.NaN,
Math.ceil(void 0) );

this.TestCase( SECTION,
"Math.ceil('0')",
0,
Math.ceil('0')            );

this.TestCase( SECTION,
"Math.ceil('-0')",
-0,
Math.ceil('-0')           );

this.TestCase( SECTION,
"Infinity/Math.ceil('0')",
Infinity,
Infinity/Math.ceil('0'));

this.TestCase( SECTION,
"Infinity/Math.ceil('-0')",
-Infinity,
Infinity/Math.ceil('-0'));

this.TestCase( SECTION,
"Math.ceil(0)",
0,
Math.ceil(0)            );

this.TestCase( SECTION,
"Math.ceil(-0)",
-0,
Math.ceil(-0)           );

this.TestCase( SECTION,
"Infinity/Math.ceil(0)",
Infinity,
Infinity/Math.ceil(0));

this.TestCase( SECTION,
"Infinity/Math.ceil(-0)",
-Infinity,
Infinity/Math.ceil(-0));


this.TestCase( SECTION,
"Math.ceil(Infinity)",
Number.POSITIVE_INFINITY,
Math.ceil(Number.POSITIVE_INFINITY) );

this.TestCase( SECTION,
"Math.ceil(-Infinity)",
Number.NEGATIVE_INFINITY,
Math.ceil(Number.NEGATIVE_INFINITY) );

this.TestCase( SECTION,
"Math.ceil(-Number.MIN_VALUE)",
-0,
Math.ceil(-Number.MIN_VALUE) );

this.TestCase( SECTION,
"Infinity/Math.ceil(-Number.MIN_VALUE)",
-Infinity,
Infinity/Math.ceil(-Number.MIN_VALUE) );

this.TestCase( SECTION,
"Math.ceil(1)",
1,
Math.ceil(1)   );

this.TestCase( SECTION,
"Math.ceil(-1)",
-1,
Math.ceil(-1)   );

this.TestCase( SECTION,
"Math.ceil(-0.9)",
-0,
Math.ceil(-0.9) );

this.TestCase( SECTION,
"Infinity/Math.ceil(-0.9)",
-Infinity,
Infinity/Math.ceil(-0.9) );

this.TestCase( SECTION,
"Math.ceil(0.9 )",
1,
Math.ceil( 0.9) );

this.TestCase( SECTION,
"Math.ceil(-1.1)",
-1,
Math.ceil( -1.1));

this.TestCase( SECTION,
"Math.ceil( 1.1)",
2,
Math.ceil(  1.1));

this.TestCase( SECTION,
"Math.ceil(Infinity)",
-Math.floor(-Infinity),
Math.ceil(Number.POSITIVE_INFINITY) );

this.TestCase( SECTION,
"Math.ceil(-Infinity)",
-Math.floor(Infinity),
Math.ceil(Number.NEGATIVE_INFINITY) );

this.TestCase( SECTION,
"Math.ceil(-Number.MIN_VALUE)",
-Math.floor(Number.MIN_VALUE),
Math.ceil(-Number.MIN_VALUE) );

this.TestCase( SECTION,
"Math.ceil(1)",
-Math.floor(-1),
Math.ceil(1)   );

this.TestCase( SECTION,
"Math.ceil(-1)",
-Math.floor(1),
Math.ceil(-1)   );

this.TestCase( SECTION,
"Math.ceil(-0.9)",
-Math.floor(0.9),
Math.ceil(-0.9) );

this.TestCase( SECTION,
"Math.ceil(0.9 )",
-Math.floor(-0.9),
Math.ceil( 0.9) );

this.TestCase( SECTION,
"Math.ceil(-1.1)",
-Math.floor(1.1),
Math.ceil( -1.1));

this.TestCase( SECTION,
"Math.ceil( 1.1)",
-Math.floor(-1.1),
Math.ceil(  1.1));

//test();

},

/**
File Name:          15.8.2.7.js
ECMA Section:       15.8.2.7 cos( x )
Description:        return an approximation to the cosine of the
argument.  argument is expressed in radians
Author:             christine@netscape.com
Date:               7 july 1997

*/
test_15_8_2_7: function() {
var SECTION = "15.8.2.7";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Math.cos(x)";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Math.cos.length",
1,
Math.cos.length );

this.TestCase( SECTION,
"Math.cos()",
Number.NaN,
Math.cos() );

this.TestCase( SECTION,
"Math.cos(void 0)",
Number.NaN,
Math.cos(void 0) );

this.TestCase( SECTION,
"Math.cos(false)",
1,
Math.cos(false) );

this.TestCase( SECTION,
"Math.cos(null)",
1,
Math.cos(null) );

this.TestCase( SECTION,
"Math.cos('0')",
1,
Math.cos('0') );

this.TestCase( SECTION,
"Math.cos('Infinity')",
Number.NaN,
Math.cos("Infinity") );

this.TestCase( SECTION,
"Math.cos('3.14159265359')",
-1,
Math.cos('3.14159265359') );

this.TestCase( SECTION,
"Math.cos(NaN)",
Number.NaN,
Math.cos(Number.NaN)        );

this.TestCase( SECTION,
"Math.cos(0)",
1,
Math.cos(0)                 );

this.TestCase( SECTION,
"Math.cos(-0)",
1,
Math.cos(-0)                );

this.TestCase( SECTION,
"Math.cos(Infinity)",
Number.NaN,
Math.cos(Number.POSITIVE_INFINITY) );

this.TestCase( SECTION,
"Math.cos(-Infinity)",
Number.NaN,
Math.cos(Number.NEGATIVE_INFINITY) );

this.TestCase( SECTION,
"Math.cos(0.7853981633974)",
0.7071067811865,
Math.cos(0.7853981633974)   );

this.TestCase( SECTION,
"Math.cos(1.570796326795)",
0,
Math.cos(1.570796326795)    );

this.TestCase( SECTION,
"Math.cos(2.356194490192)",
-0.7071067811865,
Math.cos(2.356194490192)    );

this.TestCase( SECTION,
"Math.cos(3.14159265359)",
-1,
Math.cos(3.14159265359)     );

this.TestCase( SECTION,
"Math.cos(3.926990816987)",
-0.7071067811865,
Math.cos(3.926990816987)    );

this.TestCase( SECTION,
"Math.cos(4.712388980385)",
0,
Math.cos(4.712388980385)    );

this.TestCase( SECTION,
"Math.cos(5.497787143782)",
0.7071067811865,
Math.cos(5.497787143782)    );

this.TestCase( SECTION,
"Math.cos(Math.PI*2)",
1,
Math.cos(Math.PI*2)         );

this.TestCase( SECTION,
"Math.cos(Math.PI/4)",
Math.SQRT2/2,
Math.cos(Math.PI/4)         );

this.TestCase( SECTION,
"Math.cos(Math.PI/2)",
0,
Math.cos(Math.PI/2)         );

this.TestCase( SECTION,
"Math.cos(3*Math.PI/4)",
-Math.SQRT2/2,
Math.cos(3*Math.PI/4)       );

this.TestCase( SECTION,
"Math.cos(Math.PI)",
-1,
Math.cos(Math.PI)           );

this.TestCase( SECTION,
"Math.cos(5*Math.PI/4)",
-Math.SQRT2/2,
Math.cos(5*Math.PI/4)       );

this.TestCase( SECTION,
"Math.cos(3*Math.PI/2)",
0,
Math.cos(3*Math.PI/2)       );

this.TestCase( SECTION,
"Math.cos(7*Math.PI/4)",
Math.SQRT2/2,
Math.cos(7*Math.PI/4)       );

this.TestCase( SECTION,
"Math.cos(Math.PI*2)",
1,
Math.cos(2*Math.PI)         );

this.TestCase( SECTION,
"Math.cos(-0.7853981633974)",
0.7071067811865,
Math.cos(-0.7853981633974)  );

this.TestCase( SECTION,
"Math.cos(-1.570796326795)",
0,
Math.cos(-1.570796326795)   );

this.TestCase( SECTION,
"Math.cos(-2.3561944901920)",
-.7071067811865,
Math.cos(2.3561944901920)   );

this.TestCase( SECTION,
"Math.cos(-3.14159265359)",
-1,
Math.cos(3.14159265359)     );

this.TestCase( SECTION,
"Math.cos(-3.926990816987)",
-0.7071067811865,
Math.cos(3.926990816987)    );

this.TestCase( SECTION,
"Math.cos(-4.712388980385)",
0,
Math.cos(4.712388980385)    );

this.TestCase( SECTION,
"Math.cos(-5.497787143782)",
0.7071067811865,
Math.cos(5.497787143782)    );

this.TestCase( SECTION,
"Math.cos(-6.28318530718)",
1,
Math.cos(6.28318530718)     );

this.TestCase( SECTION,
"Math.cos(-Math.PI/4)",
Math.SQRT2/2,
Math.cos(-Math.PI/4)        );

this.TestCase( SECTION,
"Math.cos(-Math.PI/2)",
0,
Math.cos(-Math.PI/2)        );

this.TestCase( SECTION,
"Math.cos(-3*Math.PI/4)",
-Math.SQRT2/2,
Math.cos(-3*Math.PI/4)      );

this.TestCase( SECTION,
"Math.cos(-Math.PI)",
-1,
Math.cos(-Math.PI)          );

this.TestCase( SECTION,
"Math.cos(-5*Math.PI/4)",
-Math.SQRT2/2,
Math.cos(-5*Math.PI/4)      );

this.TestCase( SECTION,
"Math.cos(-3*Math.PI/2)",
0,
Math.cos(-3*Math.PI/2)      );

this.TestCase( SECTION,
"Math.cos(-7*Math.PI/4)",
Math.SQRT2/2,
Math.cos(-7*Math.PI/4)      );

this.TestCase( SECTION,
"Math.cos(-Math.PI*2)",
1,
Math.cos(-Math.PI*2)        );

//test();

},

/**
File Name:          15.8.2.8.js
ECMA Section:       15.8.2.8  Math.exp(x)
Description:        return an approximation to the exponential function of
the argument (e raised to the power of the argument)
special cases:
-   if x is NaN         return NaN
-   if x is 0           return 1
-   if x is -0          return 1
-   if x is Infinity    return Infinity
-   if x is -Infinity   return 0
Author:             christine@netscape.com
Date:               7 july 1997
*/
test_15_8_2_8: function() {
var SECTION = "15.8.2.8";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Math.exp(x)";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Math.exp.length",
1,
Math.exp.length );

this.TestCase( SECTION,
"Math.exp()",
Number.NaN,
Math.exp() );

this.TestCase( SECTION,
"Math.exp(null)",
1,
Math.exp(null) );

this.TestCase( SECTION,
"Math.exp(void 0)",
Number.NaN,
Math.exp(void 0) );

this.TestCase( SECTION,
"Math.exp(1)",
Math.E,
Math.exp(1) );

this.TestCase( SECTION,
"Math.exp(true)",
Math.E,
Math.exp(true) );

this.TestCase( SECTION,
"Math.exp(false)",
1,
Math.exp(false) );

this.TestCase( SECTION,
"Math.exp('1')",
Math.E,
Math.exp('1') );

this.TestCase( SECTION,
"Math.exp('0')",
1,
Math.exp('0') );

this.TestCase( SECTION,
"Math.exp(NaN)",
Number.NaN,
Math.exp(Number.NaN) );

this.TestCase( SECTION,
"Math.exp(0)",
1,
Math.exp(0)          );

this.TestCase( SECTION,
"Math.exp(-0)",
1,
Math.exp(-0)         );

this.TestCase( SECTION,
"Math.exp(Infinity)",
Number.POSITIVE_INFINITY,
Math.exp(Number.POSITIVE_INFINITY) );

this.TestCase( SECTION,
"Math.exp(-Infinity)",
0,
Math.exp(Number.NEGATIVE_INFINITY) );

//test();

},

/**
File Name:          15.8.2.9.js
ECMA Section:       15.8.2.9  Math.floor(x)
Description:        return the greatest number value that is not greater
than the argument and is equal to a mathematical integer.
if the number is already an integer, return the number
itself.  special cases:
- if x is NaN       return NaN
- if x = +0         return +0
- if x = -0          return -0
- if x = Infinity   return Infinity
- if x = -Infinity  return -Infinity
- if ( -1 < x < 0 ) return -0
also:
-   the value of Math.floor(x) == -Math.ceil(-x)
Author:             christine@netscape.com
Date:               7 july 1997
*/
test_15_8_2_9: function() {
var SECTION = "15.8.2.9";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Math.floor(x)";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Math.floor.length",
1,
Math.floor.length );

this.TestCase( SECTION,
"Math.floor()",
Number.NaN,
Math.floor() );

this.TestCase( SECTION,
"Math.floor(void 0)",
Number.NaN,
Math.floor(void 0) );

this.TestCase( SECTION,
"Math.floor(null)",
0,
Math.floor(null) );

this.TestCase( SECTION,
"Math.floor(true)",
1,
Math.floor(true) );

this.TestCase( SECTION,
"Math.floor(false)",
0,
Math.floor(false) );

this.TestCase( SECTION,
"Math.floor('1.1')",
1,
Math.floor("1.1") );

this.TestCase( SECTION,
"Math.floor('-1.1')",
-2,
Math.floor("-1.1") );

this.TestCase( SECTION,
"Math.floor('0.1')",
0,
Math.floor("0.1") );

this.TestCase( SECTION,
"Math.floor('-0.1')",
-1,
Math.floor("-0.1") );

this.TestCase( SECTION,
"Math.floor(NaN)",
Number.NaN,
Math.floor(Number.NaN)  );

this.TestCase( SECTION,
"Math.floor(NaN)==-Math.ceil(-NaN)",
false,
Math.floor(Number.NaN) == -Math.ceil(-Number.NaN) );

this.TestCase( SECTION,
"Math.floor(0)",
0,
Math.floor(0)           );

this.TestCase( SECTION,
"Math.floor(0)==-Math.ceil(-0)",
true,
Math.floor(0) == -Math.ceil(-0) );

this.TestCase( SECTION,
"Math.floor(-0)",
-0,
Math.floor(-0)          );

this.TestCase( SECTION,
"Infinity/Math.floor(-0)",
-Infinity,
Infinity/Math.floor(-0)          );

this.TestCase( SECTION,
"Math.floor(-0)==-Math.ceil(0)",
true,
Math.floor(-0)== -Math.ceil(0) );

this.TestCase( SECTION,
"Math.floor(Infinity)",
Number.POSITIVE_INFINITY,
Math.floor(Number.POSITIVE_INFINITY) );

this.TestCase( SECTION,
"Math.floor(Infinity)==-Math.ceil(-Infinity)",
true,
Math.floor(Number.POSITIVE_INFINITY) == -Math.ceil(Number.NEGATIVE_INFINITY) );

this.TestCase( SECTION,
"Math.floor(-Infinity)",
Number.NEGATIVE_INFINITY,
Math.floor(Number.NEGATIVE_INFINITY) );

this.TestCase( SECTION,
"Math.floor(-Infinity)==-Math.ceil(Infinity)",
true,
Math.floor(Number.NEGATIVE_INFINITY) == -Math.ceil(Number.POSITIVE_INFINITY) );

this.TestCase( SECTION,
"Math.floor(0.0000001)",
0,
Math.floor(0.0000001) );

this.TestCase( SECTION,
"Math.floor(0.0000001)==-Math.ceil(0.0000001)", true,
Math.floor(0.0000001)==-Math.ceil(-0.0000001) );

this.TestCase( SECTION,
"Math.floor(-0.0000001)",
-1,
Math.floor(-0.0000001) );

this.TestCase( SECTION,
"Math.floor(0.0000001)==-Math.ceil(0.0000001)",
true,
Math.floor(-0.0000001)==-Math.ceil(0.0000001) );

//test();

}

})
.endType();

