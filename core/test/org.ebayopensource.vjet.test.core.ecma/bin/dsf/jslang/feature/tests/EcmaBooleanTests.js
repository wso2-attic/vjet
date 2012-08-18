vjo.ctype("dsf.jslang.feature.tests.EcmaBooleanTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({

/**
File Name:      15.6.1.js
ECMA Section:   15.6.1 The Boolean Function
15.6.1.1 Boolean( value )
15.6.1.2 Boolean ()
Description:    Boolean( value ) should return a Boolean value
not a Boolean object) computed by
Boolean.toBooleanValue( value)

15.6.1.2 Boolean() returns false

Author:         christine@netscape.com
Date:           27 jun 1997


Data File Fields:
VALUE       Argument passed to the Boolean function
TYPE        typeof VALUE (not used, but helpful in understanding
the data file)
E_RETURN    Expected return value of Boolean( VALUE )
*/
test_15_6_1:function(){

var SECTION = "15.6.1";
var VERSION = "ECMA_1";

var TITLE   = "The Boolean constructor called as a function: Boolean( value ) and Boolean()";


var array = new Array();
var item = 0;

this.TestCase( SECTION,   "Boolean(1)",         true,   Boolean(1) );
this.TestCase( SECTION,   "Boolean(0)",         false,  Boolean(0) );
this.TestCase( SECTION,   "Boolean(-1)",        true,   Boolean(-1) );
this.TestCase( SECTION,   "Boolean('1')",       true,   Boolean("1") );
this.TestCase( SECTION,   "Boolean('0')",       true,   Boolean("0") );
this.TestCase( SECTION,   "Boolean('-1')",      true,   Boolean("-1") );
this.TestCase( SECTION,   "Boolean(true)",      true,   Boolean(true) );
this.TestCase( SECTION,   "Boolean(false)",     false,  Boolean(false) );

this.TestCase( SECTION,   "Boolean('true')",    true,   Boolean("true") );
this.TestCase( SECTION,   "Boolean('false')",   true,   Boolean("false") );
this.TestCase( SECTION,   "Boolean(null)",      false,  Boolean(null) );

this.TestCase( SECTION,   "Boolean(-Infinity)", true,   Boolean(Number.NEGATIVE_INFINITY) );
this.TestCase( SECTION,   "Boolean(NaN)",       false,  Boolean(Number.NaN) );
this.TestCase( SECTION,   "Boolean(void(0))",   false,  Boolean( void(0) ) );
this.TestCase( SECTION,   "Boolean(x=0)",       false,  Boolean( x=0 ) );
this.TestCase( SECTION,   "Boolean(x=1)",       true,   Boolean( x=1 ) );
this.TestCase( SECTION,   "Boolean(x=false)",   false,  Boolean( x=false ) );
this.TestCase( SECTION,   "Boolean(x=true)",    true,   Boolean( x=true ) );
this.TestCase( SECTION,   "Boolean(x=null)",    false,  Boolean( x=null ) );
this.TestCase( SECTION,   "Boolean()",          false,  Boolean() );
//    array[item++] = new TestCase( SECTION,   "Boolean(var someVar)",     false,  Boolean( someVar ) );



},

/**
File Name:          15.6.2.js
ECMA Section:       15.6.2 The Boolean Constructor
15.6.2.1 new Boolean( value )
15.6.2.2 new Boolean()

This test verifies that the Boolean constructor
initializes a new object (typeof should return
"object").  The prototype of the new object should
be Boolean.prototype.  The value of the object
should be ToBoolean( value ) (a boolean value).

Description:
Author:             christine@netscape.com
Date:               june 27, 1997

*/


//>public void test_15_6_2()
test_15_6_2 : function() {
var SECTION = "15.6.2";
var VERSION = "ECMA_1";
var TITLE   = "15.6.2 The Boolean Constructor; 15.6.2.1 new Boolean( value ); 15.6.2.2 new Boolean()";


var array = new Array();
var item = 0;

this.TestCase( SECTION,   "typeof (new Boolean(1))",         "object",            typeof (new Boolean(1)) );
this.TestCase( SECTION,   "(new Boolean(1)).constructor",    Boolean.prototype.constructor,   (new Boolean(1)).constructor );
this.TestCase( SECTION,
"TESTBOOL=new Boolean(1);TESTBOOL.toString=Object.prototype.toString;TESTBOOL.toString()",
"[object Boolean]",
eval("TESTBOOL=new Boolean(1);TESTBOOL.toString=Object.prototype.toString;TESTBOOL.toString()") );
this.TestCase( SECTION,   "(new Boolean(1)).valueOf()",   true,       (new Boolean(1)).valueOf() );
this.TestCase( SECTION,   "typeof new Boolean(1)",         "object",   typeof new Boolean(1) );
this.TestCase( SECTION,   "(new Boolean(0)).constructor",    Boolean.prototype.constructor,   (new Boolean(0)).constructor );
this.TestCase( SECTION,
"TESTBOOL=new Boolean(0);TESTBOOL.toString=Object.prototype.toString;TESTBOOL.toString()",
"[object Boolean]",
eval("TESTBOOL=new Boolean(0);TESTBOOL.toString=Object.prototype.toString;TESTBOOL.toString()") );
this.TestCase( SECTION,   "(new Boolean(0)).valueOf()",   false,       (new Boolean(0)).valueOf() );
this.TestCase( SECTION,   "typeof new Boolean(0)",         "object",   typeof new Boolean(0) );
this.TestCase( SECTION,   "(new Boolean(-1)).constructor",    Boolean.prototype.constructor,   (new Boolean(-1)).constructor );
this.TestCase( SECTION,
"TESTBOOL=new Boolean(-1);TESTBOOL.toString=Object.prototype.toString;TESTBOOL.toString()",
"[object Boolean]",
eval("TESTBOOL=new Boolean(-1);TESTBOOL.toString=Object.prototype.toString;TESTBOOL.toString()") );
this.TestCase( SECTION,   "(new Boolean(-1)).valueOf()",   true,       (new Boolean(-1)).valueOf() );
this.TestCase( SECTION,   "typeof new Boolean(-1)",         "object",   typeof new Boolean(-1) );
this.TestCase( SECTION,   "(new Boolean('1')).constructor",    Boolean.prototype.constructor,   (new Boolean('1')).constructor );
this.TestCase( SECTION,
"TESTBOOL=new Boolean('1');TESTBOOL.toString=Object.prototype.toString;TESTBOOL.toString()",
"[object Boolean]",
eval("TESTBOOL=new Boolean('1');TESTBOOL.toString=Object.prototype.toString;TESTBOOL.toString()") );
this.TestCase( SECTION,   "(new Boolean('1')).valueOf()",   true,       (new Boolean('1')).valueOf() );
this.TestCase( SECTION,   "typeof new Boolean('1')",         "object",   typeof new Boolean('1') );
this.TestCase( SECTION,   "(new Boolean('0')).constructor",    Boolean.prototype.constructor,   (new Boolean('0')).constructor );
this.TestCase( SECTION,
"TESTBOOL=new Boolean('0');TESTBOOL.toString=Object.prototype.toString;TESTBOOL.toString()",
"[object Boolean]",
eval("TESTBOOL=new Boolean('0');TESTBOOL.toString=Object.prototype.toString;TESTBOOL.toString()") );
this.TestCase( SECTION,   "(new Boolean('0')).valueOf()",   true,       (new Boolean('0')).valueOf() );
this.TestCase( SECTION,   "typeof new Boolean('0')",         "object",   typeof new Boolean('0') );
this.TestCase( SECTION,   "(new Boolean('-1')).constructor",    Boolean.prototype.constructor,   (new Boolean('-1')).constructor );
this.TestCase( SECTION,
"TESTBOOL=new Boolean('-1');TESTBOOL.toString=Object.prototype.toString;TESTBOOL.toString()",
"[object Boolean]",
eval("TESTBOOL=new Boolean('-1');TESTBOOL.toString=Object.prototype.toString;TESTBOOL.toString()") );
this.TestCase( SECTION,   "(new Boolean('-1')).valueOf()",   true,       (new Boolean('-1')).valueOf() );
this.TestCase( SECTION,   "typeof new Boolean('-1')",         "object",   typeof new Boolean('-1') );
this.TestCase( SECTION,   "(new Boolean(new Boolean(true))).constructor",    Boolean.prototype.constructor,   (new Boolean(new Boolean(true))).constructor );
this.TestCase( SECTION,
"TESTBOOL=new Boolean(new Boolean(true));TESTBOOL.toString=Object.prototype.toString;TESTBOOL.toString()",
"[object Boolean]",
eval("TESTBOOL=new Boolean(new Boolean(true));TESTBOOL.toString=Object.prototype.toString;TESTBOOL.toString()") );
this.TestCase( SECTION,   "(new Boolean(new Boolean(true))).valueOf()",   true,       (new Boolean(new Boolean(true))).valueOf() );
this.TestCase( SECTION,   "typeof new Boolean(new Boolean(true))",         "object",   typeof new Boolean(new Boolean(true)) );
this.TestCase( SECTION,   "(new Boolean(Number.NaN)).constructor",    Boolean.prototype.constructor,   (new Boolean(Number.NaN)).constructor );
this.TestCase( SECTION,
"TESTBOOL=new Boolean(Number.NaN);TESTBOOL.toString=Object.prototype.toString;TESTBOOL.toString()",
"[object Boolean]",
eval("TESTBOOL=new Boolean(Number.NaN);TESTBOOL.toString=Object.prototype.toString;TESTBOOL.toString()") );
this.TestCase( SECTION,   "(new Boolean(Number.NaN)).valueOf()",   false,       (new Boolean(Number.NaN)).valueOf() );
this.TestCase( SECTION,   "typeof new Boolean(Number.NaN)",         "object",   typeof new Boolean(Number.NaN) );
this.TestCase( SECTION,   "(new Boolean(null)).constructor",    Boolean.prototype.constructor,   (new Boolean(null)).constructor );
this.TestCase( SECTION,
"TESTBOOL=new Boolean(null);TESTBOOL.toString=Object.prototype.toString;TESTBOOL.toString()",
"[object Boolean]",
eval("TESTBOOL=new Boolean(null);TESTBOOL.toString=Object.prototype.toString;TESTBOOL.toString()") );
this.TestCase( SECTION,   "(new Boolean(null)).valueOf()",   false,       (new Boolean(null)).valueOf() );
this.TestCase( SECTION,   "typeof new Boolean(null)",         "object",   typeof new Boolean(null) );
this.TestCase( SECTION,   "(new Boolean(void 0)).constructor",    Boolean.prototype.constructor,   (new Boolean(void 0)).constructor );
this.TestCase( SECTION,
"TESTBOOL=new Boolean(void 0);TESTBOOL.toString=Object.prototype.toString;TESTBOOL.toString()",
"[object Boolean]",
eval("TESTBOOL=new Boolean(void 0);TESTBOOL.toString=Object.prototype.toString;TESTBOOL.toString()") );
this.TestCase( SECTION,   "(new Boolean(void 0)).valueOf()",   false,       (new Boolean(void 0)).valueOf() );
this.TestCase( SECTION,   "typeof new Boolean(void 0)",         "object",   typeof new Boolean(void 0) );
this.TestCase( SECTION,   "(new Boolean(Number.POSITIVE_INFINITY)).constructor",    Boolean.prototype.constructor,   (new Boolean(Number.POSITIVE_INFINITY)).constructor );
this.TestCase( SECTION,
"TESTBOOL=new Boolean(Number.POSITIVE_INFINITY);TESTBOOL.toString=Object.prototype.toString;TESTBOOL.toString()",
"[object Boolean]",
eval("TESTBOOL=new Boolean(Number.POSITIVE_INFINITY);TESTBOOL.toString=Object.prototype.toString;TESTBOOL.toString()") );
this.TestCase( SECTION,   "(new Boolean(Number.POSITIVE_INFINITY)).valueOf()",   true,       (new Boolean(Number.POSITIVE_INFINITY)).valueOf() );
this.TestCase( SECTION,   "typeof new Boolean(Number.POSITIVE_INFINITY)",         "object",   typeof new Boolean(Number.POSITIVE_INFINITY) );
this.TestCase( SECTION,   "(new Boolean(Number.NEGATIVE_INFINITY)).constructor",    Boolean.prototype.constructor,   (new Boolean(Number.NEGATIVE_INFINITY)).constructor );
this.TestCase( SECTION,
"TESTBOOL=new Boolean(Number.NEGATIVE_INFINITY);TESTBOOL.toString=Object.prototype.toString;TESTBOOL.toString()",
"[object Boolean]",
eval("TESTBOOL=new Boolean(Number.NEGATIVE_INFINITY);TESTBOOL.toString=Object.prototype.toString;TESTBOOL.toString()") );
this.TestCase( SECTION,   "(new Boolean(Number.NEGATIVE_INFINITY)).valueOf()",   true,       (new Boolean(Number.NEGATIVE_INFINITY)).valueOf() );
this.TestCase( SECTION,   "typeof new Boolean(Number.NEGATIVE_INFINITY)",         "object",   typeof new Boolean(Number.NEGATIVE_INFINITY) );
this.TestCase( SECTION,   "(new Boolean(Number.NEGATIVE_INFINITY)).constructor",    Boolean.prototype.constructor,   (new Boolean(Number.NEGATIVE_INFINITY)).constructor );
this.TestCase( "15.6.2.2",
"TESTBOOL=new Boolean();TESTBOOL.toString=Object.prototype.toString;TESTBOOL.toString()",
"[object Boolean]",
eval("TESTBOOL=new Boolean();TESTBOOL.toString=Object.prototype.toString;TESTBOOL.toString()") );
this.TestCase( "15.6.2.2",   "(new Boolean()).valueOf()",   false,       (new Boolean()).valueOf() );
this.TestCase( "15.6.2.2",   "typeof new Boolean()",        "object",    typeof new Boolean() );


},

/**
File Name:          15.6.3.1-1.js
ECMA Section:       15.6.3 Boolean.prototype

Description:        The initial value of Boolean.prototype is the built-in
Boolean prototype object (15.6.4).

The property shall have the attributes [DontEnum,
DontDelete, ReadOnly ].

This tests the DontEnum property of Boolean.prototype

Author:             christine@netscape.com
Date:               june 27, 1997

*/

test_15_6_3_1_1:function(){
var SECTION = "15.6.3.1-1";
var VERSION = "ECMA_1";
var TITLE   = "Boolean.prototype";

var array = new Array();
var item = 0;

this.TestCase( SECTION,
"var str='';for ( p in Boolean ) { str += p } str;",
"",
eval("var str='';for ( p in Boolean ) { str += p } str;") );


},

/**
File Name:          15.6.3.1-2.js
ECMA Section:       15.6.3.1 Boolean.prototype

Description:        The initial valu eof Boolean.prototype is the built-in
Boolean prototype object (15.6.4).

The property shall have the attributes [DontEnum,
DontDelete, ReadOnly ].

This tests the DontDelete property of Boolean.prototype

Author:             christine@netscape.com
Date:               june 27, 1997

*/
test_15_6_3_1_2:function(){
var SECTION = "15.6.3.1-2";
var VERSION = "ECMA_1";

var TITLE   = "Boolean.prototype"


var array = new Array();
var item = 0;

this.TestCase( SECTION,
"delete( Boolean.prototype)",
false,
delete( Boolean.prototype) );




},

/**
File Name:          15.6.3.1-3.js
ECMA Section:       15.6.3.1 Boolean.prototype

Description:        The initial valu eof Boolean.prototype is the built-in
Boolean prototype object (15.6.4).

The property shall have the attributes [DontEnum,
DontDelete, ReadOnly ].

This tests the DontDelete property of Boolean.prototype

Author:             christine@netscape.com
Date:               june 27, 1997

*/
test_15_6_3_1_3:function(){
var SECTION = "15.6.3.1-3";
var VERSION = "ECMA_1";

var TITLE   = "Boolean.prototype"

var array = new Array();
var item = 0;

this.TestCase( SECTION,
"delete( Boolean.prototype); Boolean.prototype",
Boolean.prototype,
eval("delete( Boolean.prototype); Boolean.prototype") );


},

/**
File Name:          15.6.3.1-4.js
ECMA Section:       15.6.3.1 Properties of the Boolean Prototype Object

Description:        The initial value of Boolean.prototype is the built-in
Boolean prototype object (15.6.4).

The property shall have the attributes [DontEnum,
DontDelete, ReadOnly ].

This tests the ReadOnly property of Boolean.prototype

Author:             christine@netscape.com
Date:               30 september 1997

*/

//>public void test_15_6_3_1__4()
test_15_6_3_1__4 : function() {
var SECTION = "15.6.3.1-4";
var VERSION = "ECMA_1";
var TITLE   = "Boolean.prototype"

var BOOL_PROTO = Boolean.prototype;

this.TestCase( SECTION,
"var BOOL_PROTO = Boolean.prototype; Boolean.prototype=null; Boolean.prototype == BOOL_PROTO",
true,
eval("var BOOL_PROTO = Boolean.prototype; Boolean.prototype=null; Boolean.prototype == BOOL_PROTO") );

this.TestCase( SECTION,
"var BOOL_PROTO = Boolean.prototype; Boolean.prototype=null; Boolean.prototype == null",
false,
eval("var BOOL_PROTO = Boolean.prototype; Boolean.prototype=null; Boolean.prototype == null") );

},

/**
File Name:          15.6.3.1.js
ECMA Section:       15.6.3.1 Boolean.prototype

Description:        The initial valu eof Boolean.prototype is the built-in
Boolean prototype object (15.6.4).

The property shall have the attributes [DontEnum,
DontDelete, ReadOnly ].

It has the internal [[Call]] and [[Construct]]
properties (not tested), and the length property.

Author:             christine@netscape.com
Date:               june 27, 1997

*/

test_15_6_3_1:function(){
var SECTION = "15.6.3.1";
var VERSION = "ECMA_1";

var TITLE   = "Boolean.prototype";

this.TestCase( SECTION,  "Boolean.prototype.valueOf()",       false,   Boolean.prototype.valueOf() );
this.TestCase( SECTION,  "Boolean.length",          1,       Boolean.length );

},

/**
File Name:          15.6.4-1.js
ECMA Section:       15.6.4 Properties of the Boolean Prototype Object

Description:
The Boolean prototype object is itself a Boolean object (its [[Class]] is
"Boolean") whose value is false.

The value of the internal [[Prototype]] property of the Boolean prototype object
is the Object prototype object (15.2.3.1).

Author:             christine@netscape.com
Date:               30 september 1997

*/

test_15_6_4_1:function(){

var VERSION = "ECMA_1"

var SECTION = "15.6.4-1";


this.TestCase( SECTION, "typeof Boolean.prototype == typeof( new Boolean )", true,          typeof Boolean.prototype == typeof( new Boolean ) );
this.TestCase( SECTION, "typeof( Boolean.prototype )",              "object",               typeof(Boolean.prototype) );
this.TestCase( SECTION,
"Boolean.prototype.toString = Object.prototype.toString; Boolean.prototype.toString()",
"[object Boolean]",
eval("Boolean.prototype.toString = Object.prototype.toString; Boolean.prototype.toString()") );
this.TestCase( SECTION, "Boolean.prototype.valueOf()",               false,                  Boolean.prototype.valueOf() );


},
/**
File Name:          15.6.4.1.js
ECMA Section:       15.6.4.1 Boolean.prototype.constructor

Description:        The initial value of Boolean.prototype.constructor
is the built-in Boolean constructor.

Author:             christine@netscape.com
Date:               30 september 1997

*/
test_15_6_4__1:function(){
var SECTION = "15.6.4.1";
var VERSION = "ECMA_1";

var TITLE   = "Boolean.prototype.constructor"

this.TestCase( SECTION,
"( Boolean.prototype.constructor == Boolean )",
true ,
(Boolean.prototype.constructor == Boolean) );

},

/**
File Name:          15.6.4.2.js
ECMA Section:       15.6.4.2-1 Boolean.prototype.toString()
Description:        If this boolean value is true, then the string "true"
is returned; otherwise this boolean value must be false,
and the string "false" is returned.

The toString function is not generic; it generates
a runtime error if its this value is not a Boolean
object.  Therefore it cannot be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               june 27, 1997
*/

test_15_6_4_2__1:function(){

var SECTION = "15.6.4.2-1";
var VERSION = "ECMA_1";

var TITLE   = "Boolean.prototype.toString()"

vjo.sysout.println("(new Boolean(1)).toString()=" + (new Boolean(1)).toString());
this.TestCase( SECTION,   "new Boolean(1)",       "true",   (new Boolean(1)).toString() );
this.TestCase( SECTION,   "new Boolean(0)",       "false",  (new Boolean(0)).toString() );
this.TestCase( SECTION,   "new Boolean(-1)",      "true",   (new Boolean(-1)).toString() );
this.TestCase( SECTION,   "new Boolean('1')",     "true",   (new Boolean("1")).toString() );
this.TestCase( SECTION,   "new Boolean('0')",     "true",   (new Boolean("0")).toString() );
this.TestCase( SECTION,   "new Boolean(true)",    "true",   (new Boolean(true)).toString() );
this.TestCase( SECTION,   "new Boolean(false)",   "false",  (new Boolean(false)).toString() );
this.TestCase( SECTION,   "new Boolean('true')",  "true",   (new Boolean('true')).toString() );
this.TestCase( SECTION,   "new Boolean('false')", "true",   (new Boolean('false')).toString() );

this.TestCase( SECTION,   "new Boolean('')",      "false",  (new Boolean('')).toString() );
this.TestCase( SECTION,   "new Boolean(null)",    "false",  (new Boolean(null)).toString() );
this.TestCase( SECTION,   "new Boolean(void(0))", "false",  (new Boolean(void(0))).toString() );
this.TestCase( SECTION,   "new Boolean(-Infinity)", "true", (new Boolean(Number.NEGATIVE_INFINITY)).toString() );
this.TestCase( SECTION,   "new Boolean(NaN)",     "false",  (new Boolean(Number.NaN)).toString() );
this.TestCase( SECTION,   "new Boolean()",        "false",  (new Boolean()).toString() );
this.TestCase( SECTION,   "new Boolean(x=1)",     "true",   (new Boolean(x=1)).toString() );
this.TestCase( SECTION,   "new Boolean(x=0)",     "false",  (new Boolean(x=0)).toString() );
this.TestCase( SECTION,   "new Boolean(x=false)", "false",  (new Boolean(x=false)).toString() );
this.TestCase( SECTION,   "new Boolean(x=true)",  "true",   (new Boolean(x=true)).toString() );
this.TestCase( SECTION,   "new Boolean(x=null)",  "false",  (new Boolean(x=null)).toString() );
this.TestCase( SECTION,   "new Boolean(x='')",    "false",  (new Boolean(x="")).toString() );
this.TestCase( SECTION,   "new Boolean(x=' ')",   "true",   (new Boolean(x=" ")).toString() );

this.TestCase( SECTION,   "new Boolean(new MyObject(true))",     "true",   (new Boolean(new MyObject(true))).toString() );
this.TestCase( SECTION,   "new Boolean(new MyObject(false))",    "true",   (new Boolean(new MyObject(false))).toString() );


function MyObject( value ) {
this.value = value;
this.valueOf = new Function( "return this.value" );
return this;
}

},

/**
File Name:          15.6.4.2-2.js
ECMA Section:       15.6.4.2 Boolean.prototype.toString()
Description:        Returns this boolean value.

The toString function is not generic; it generates
a runtime error if its this value is not a Boolean
object.  Therefore it cannot be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               june 27, 1997
*/

test_15_6_4_2__2:function(){
var SECTION = "15.6.4.2-2";
var VERSION = "ECMA_1";
var TITLE   = "Boolean.prototype.toString()"

this.TestCase(   SECTION,
"tostr=Boolean.prototype.toString; x=new Boolean(); x.toString=tostr;x.toString()",
"false",
eval("tostr=Boolean.prototype.toString; x=new Boolean(); x.toString=tostr;x.toString()") );
this.TestCase(   SECTION,
"tostr=Boolean.prototype.toString; x=new Boolean(true); x.toString=tostr; x.toString()",
"true",
eval("tostr=Boolean.prototype.toString; x=new Boolean(true); x.toString=tostr; x.toString()") );
this.TestCase( SECTION,
"tostr=Boolean.prototype.toString; x=new Boolean(false); x.toString=tostr;x.toString()",
"false",
eval("tostr=Boolean.prototype.toString; x=new Boolean(); x.toString=tostr;x.toString()") );


},

/**
File Name:          15.6.4.2-3.js
ECMA Section:       15.6.4.2 Boolean.prototype.toString()
Description:        Returns this boolean value.

The toString function is not generic; it generates
a runtime error if its this value is not a Boolean
object.  Therefore it cannot be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               june 27, 1997
*/

test_15_6_4_2__3:function(){
var SECTION = "15.6.4.2-3";
var VERSION = "ECMA_1";

var TITLE   = "Boolean.prototype.toString()"

this.TestCase( SECTION, "tostr=Boolean.prototype.toString; x=true; x.toString=tostr;x.toString()", "true", eval("tostr=Boolean.prototype.toString; x=true; x.toString=tostr;x.toString()") );
this.TestCase( SECTION, "tostr=Boolean.prototype.toString; x=false; x.toString=tostr;x.toString()", "false", eval("tostr=Boolean.prototype.toString; x=false; x.toString=tostr;x.toString()") );

},

/**
File Name:          15.6.4.2-4.js
ECMA Section:       15.6.4.2 Boolean.prototype.toString()
Description:        Returns this boolean value.

The toString function is not generic; it generates
a runtime error if its this value is not a Boolean
object.  Therefore it cannot be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               june 27, 1997
*/

test_15_6_4_2__4__n:function(){
var SECTION = "15.6.4.2-4-n";
var VERSION = "ECMA_1";

var TITLE   = "Boolean.prototype.toString()";

DESCRIPTION = "tostr=Boolean.prototype.toString; x=new String( 'hello' ); x.toString=tostr; x.toString()";
EXPECTED = "error";

this.TestCase(   SECTION,
"tostr=Boolean.prototype.toString; x=new String( 'hello' ); x.toString=tostr; x.toString()",
"error",
eval("tostr=Boolean.prototype.toString; x=new String( 'hello' ); x.toString=tostr; x.toString()") );


},

/**
File Name:          15.6.4.3.js
ECMA Section:       15.6.4.3 Boolean.prototype.valueOf()
Description:        Returns this boolean value.

The valueOf function is not generic; it generates
a runtime error if its this value is not a Boolean
object.  Therefore it cannot be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               june 27, 1997
*/

test_15_6_4_3__1:function(){
var SECTION = "15.6.4.3-1";
var VERSION = "ECMA_1";

var TITLE   = "Boolean.prototype.valueOf()";

this.TestCase( SECTION,   "new Boolean(1)",       true,   (new Boolean(1)).valueOf() );

this.TestCase( SECTION,   "new Boolean(0)",       false,  (new Boolean(0)).valueOf() );
this.TestCase( SECTION,   "new Boolean(-1)",      true,   (new Boolean(-1)).valueOf() );
this.TestCase( SECTION,   "new Boolean('1')",     true,   (new Boolean("1")).valueOf() );
this.TestCase( SECTION,   "new Boolean('0')",     true,   (new Boolean("0")).valueOf() );
this.TestCase( SECTION,   "new Boolean(true)",    true,   (new Boolean(true)).valueOf() );
this.TestCase( SECTION,   "new Boolean(false)",   false,  (new Boolean(false)).valueOf() );
this.TestCase( SECTION,   "new Boolean('true')",  true,   (new Boolean("true")).valueOf() );
this.TestCase( SECTION,   "new Boolean('false')", true,   (new Boolean('false')).valueOf() );

this.TestCase( SECTION,   "new Boolean('')",      false,  (new Boolean('')).valueOf() );
this.TestCase( SECTION,   "new Boolean(null)",    false,  (new Boolean(null)).valueOf() );
this.TestCase( SECTION,   "new Boolean(void(0))", false,  (new Boolean(void(0))).valueOf() );
this.TestCase( SECTION,   "new Boolean(-Infinity)", true, (new Boolean(Number.NEGATIVE_INFINITY)).valueOf() );
this.TestCase( SECTION,   "new Boolean(NaN)",     false,  (new Boolean(Number.NaN)).valueOf() );
this.TestCase( SECTION,   "new Boolean()",        false,  (new Boolean()).valueOf() );

this.TestCase( SECTION,   "new Boolean(x=1)",     true,   (new Boolean(x=1)).valueOf() );
this.TestCase( SECTION,   "new Boolean(x=0)",     false,  (new Boolean(x=0)).valueOf() );
this.TestCase( SECTION,   "new Boolean(x=false)", false,  (new Boolean(x=false)).valueOf() );
this.TestCase( SECTION,   "new Boolean(x=true)",  true,   (new Boolean(x=true)).valueOf() );
this.TestCase( SECTION,   "new Boolean(x=null)",  false,  (new Boolean(x=null)).valueOf() );
this.TestCase( SECTION,   "new Boolean(x='')",    false,  (new Boolean(x="")).valueOf() );
this.TestCase( SECTION,   "new Boolean(x=' ')",   true,   (new Boolean(x=" ")).valueOf() );



},

/**
File Name:          15.6.4.3-2.js
ECMA Section:       15.6.4.3 Boolean.prototype.valueOf()
Description:        Returns this boolean value.

The valueOf function is not generic; it generates
a runtime error if its this value is not a Boolean
object.  Therefore it cannot be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               june 27, 1997
*/

test_15_6_4_3__2:function(){

var SECTION = "15.6.4.3-2";
var VERSION = "ECMA_1";
var TITLE   = "Boolean.prototype.valueOf()";

this.TestCase( SECTION, "valof=Boolean.prototype.valueOf; x=new Boolean(); x.valueOf=valof;x.valueOf()", false, eval("valof=Boolean.prototype.valueOf; x=new Boolean(); x.valueOf=valof;x.valueOf()") );
this.TestCase( SECTION, "valof=Boolean.prototype.valueOf; x=new Boolean(true); x.valueOf=valof;x.valueOf()", true, eval("valof=Boolean.prototype.valueOf; x=new Boolean(true); x.valueOf=valof;x.valueOf()") );

},

/**
File Name:          15.6.4.3-3.js
ECMA Section:       15.6.4.3 Boolean.prototype.valueOf()
Description:        Returns this boolean value.

The valueOf function is not generic; it generates
a runtime error if its this value is not a Boolean
object.  Therefore it cannot be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               june 27, 1997
*/
test_15_6_4_3__3:function(){
var SECTION = "15.6.4.3-3";
var VERSION = "ECMA_1";
var TITLE   = "Boolean.prototype.valueOf()";
this.TestCase( SECTION,
"x=true; x.valueOf=Boolean.prototype.valueOf;x.valueOf()",
true,
eval("x=true; x.valueOf=Boolean.prototype.valueOf;x.valueOf()") );

},

/**
File Name:          15.6.4.3-4.js
ECMA Section:       15.6.4.3 Boolean.prototype.valueOf()
Description:        Returns this boolean value.

The valueOf function is not generic; it generates
a runtime error if its this value is not a Boolean
object.  Therefore it cannot be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               june 27, 1997
*/

test_15_6_4_3__4__n:function(){
var SECTION = "15.6.4.3-4-n";
var VERSION = "ECMA_1";
var TITLE   = "Boolean.prototype.valueOf()";
var fail = fail;

DESCRIPTION = "valof=Boolean.prototype.valueOf; x=new String( 'hello' ); x.valueOf=valof;x.valueOf()"
EXPECTED = "error";


valof=Boolean.prototype.valueOf;
x=new String( 'hello' );
x.valueOf=valof;
var b = false;
try {
x.valueOf();
fail();
} catch (e) {
// do nothing expected block of code
b = true;
}
var assertTrue = assertTrue;
assertTrue(b);

},

/**
File Name:          15.6.4.3.js
ECMA Section:       15.6.4.3 Boolean.prototype.valueOf()
Description:        Returns this boolean value.

The valueOf function is not generic; it generates
a runtime error if its this value is not a Boolean
object.  Therefore it cannot be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               june 27, 1997
*/

test_15_6_4_3:function(){


this.TestCase( "15.8.6.4",   "new Boolean(1)",       true,   (new Boolean(1)).valueOf() );

this.TestCase( "15.8.6.4",   "new Boolean(0)",       false,  (new Boolean(0)).valueOf() );
this.TestCase( "15.8.6.4",   "new Boolean(-1)",      true,   (new Boolean(-1)).valueOf() );
this.TestCase( "15.8.6.4",   "new Boolean('1')",     true,   (new Boolean("1")).valueOf() );
this.TestCase( "15.8.6.4",   "new Boolean('0')",     true,   (new Boolean("0")).valueOf() );
this.TestCase( "15.8.6.4",   "new Boolean(true)",    true,   (new Boolean(true)).valueOf() );
this.TestCase( "15.8.6.4",   "new Boolean(false)",   false,  (new Boolean(false)).valueOf() );
this.TestCase( "15.8.6.4",   "new Boolean('true')",  true,   (new Boolean("true")).valueOf() );
this.TestCase( "15.8.6.4",   "new Boolean('false')", true,   (new Boolean('false')).valueOf() );

this.TestCase( "15.8.6.4",   "new Boolean('')",      false,  (new Boolean('')).valueOf() );
this.TestCase( "15.8.6.4",   "new Boolean(null)",    false,  (new Boolean(null)).valueOf() );
this.TestCase( "15.8.6.4",   "new Boolean(void(0))", false,  (new Boolean(void(0))).valueOf() );
this.TestCase( "15.8.6.4",   "new Boolean(-Infinity)", true, (new Boolean(Number.NEGATIVE_INFINITY)).valueOf() );
this.TestCase( "15.8.6.4",   "new Boolean(NaN)",     false,  (new Boolean(Number.NaN)).valueOf() );
this.TestCase( "15.8.6.4",   "new Boolean()",        false,  (new Boolean()).valueOf() );

this.TestCase( "15.8.6.4",   "new Boolean(x=1)",     true,   (new Boolean(x=1)).valueOf() );
this.TestCase( "15.8.6.4",   "new Boolean(x=0)",     false,  (new Boolean(x=0)).valueOf() );
this.TestCase( "15.8.6.4",   "new Boolean(x=false)", false,  (new Boolean(x=false)).valueOf() );
this.TestCase( "15.8.6.4",   "new Boolean(x=true)",  true,   (new Boolean(x=true)).valueOf() );
this.TestCase( "15.8.6.4",   "new Boolean(x=null)",  false,  (new Boolean(x=null)).valueOf() );
this.TestCase( "15.8.6.4",   "new Boolean(x='')",    false,  (new Boolean(x="")).valueOf() );
this.TestCase( "15.8.6.4",   "new Boolean(x=' ')",   true,   (new Boolean(x=" ")).valueOf() );



},

/**
File Name:          15.6.4.js
ECMA Section:       Properties of the Boolean Prototype Object
Description:
The Boolean prototype object is itself a Boolean object (its [[Class]] is "
Boolean") whose value is false.

The value of the internal [[Prototype]] property of the Boolean prototype
object is the Object prototype object (15.2.3.1).

In following descriptions of functions that are properties of the Boolean
prototype object, the phrase "this Boolean object" refers to the object that
is the this value for the invocation of the function; it is an error if
this does not refer to an object for which the value of the internal
[[Class]] property is "Boolean". Also, the phrase "this boolean value"
refers to the boolean value represented by this Boolean object, that is,
the value of the internal [[Value]] property of this Boolean object.

Author:             christine@netscape.com
Date:               12 november 1997
*/

test_15_6_4:function(){

var SECTION = "15.6.4";
var VERSION = "ECMA_1";
var TITLE   = "Properties of the Boolean Prototype Object";

this.TestCase( SECTION,
"Boolean.prototype == false",
true,
Boolean.prototype == false );

this.TestCase( SECTION,
"Boolean.prototype.toString = Object.prototype.toString; Boolean.prototype.toString()",
"[object Boolean]",
eval("Boolean.prototype.toString = Object.prototype.toString; Boolean.prototype.toString()") );


}







})
.endType();
