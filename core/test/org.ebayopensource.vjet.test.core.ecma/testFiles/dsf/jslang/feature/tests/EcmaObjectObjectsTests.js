vjo.ctype("dsf.jslang.feature.tests.EcmaObjectObjectsTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({

/**
File Name:          15.2.1.1.js
ECMA Section:       15.2.1.1  The Object Constructor Called as a Function:
Object(value)
Description:        When Object is called as a function rather than as a
constructor, the following steps are taken:

1.  If value is null or undefined, create and return a
new object with no properties other than internal
properties exactly as if the object constructor
had been called on that same value (15.2.2.1).
2.  Return ToObject (value), whose rules are:

undefined   generate a runtime error
null        generate a runtime error
boolean     create a new Boolean object whose default
value is the value of the boolean.
number      Create a new Number object whose default
value is the value of the number.
string      Create a new String object whose default
value is the value of the string.
object      Return the input argument (no conversion).

Author:             christine@netscape.com
Date:               17 july 1997
*/
test_15_2_1_1:function(){

var SECTION = "15.2.1.1";
var VERSION = "ECMA_1";
var TITLE   = "Object( value )";

var NULL_OBJECT;

this.TestCase( SECTION, "Object(null).valueOf()",    NULL_OBJECT,           (NULL_OBJECT).valueOf() );
this.TestCase( SECTION, "typeof Object(null)",       "object",               typeof (new Object()) );

var UNDEFINED_OBJECT = new Object();

this.TestCase( SECTION, "Object(void 0).valueOf()",    UNDEFINED_OBJECT,           (UNDEFINED_OBJECT).valueOf() );
this.TestCase( SECTION, "typeof Object(void 0)",       "object",               typeof (new Object()) );

this.TestCase( SECTION, "Object(true).valueOf()",    true,                   (new Object()).valueOf() );
this.TestCase( SECTION, "typeof Object(true)",       "object",               typeof Object(true) );
this.TestCase( SECTION, "var MYOB = Object(true); MYOB.toString = Object.prototype.toString; MYOB.toString()",  "[object Boolean]",      eval("var MYOB = Object(true); MYOB.toString = Object.prototype.toString; MYOB.toString()") );

this.TestCase( SECTION, "Object(false).valueOf()",    false,                  (new Object()).valueOf() );
this.TestCase( SECTION, "typeof Object(false)",      "object",               typeof Object(false) );
this.TestCase( SECTION, "var MYOB = Object(false); MYOB.toString = Object.prototype.toString; MYOB.toString()",  "[object Boolean]",      eval("var MYOB = Object(false); MYOB.toString = Object.prototype.toString; MYOB.toString()") );

this.TestCase( SECTION, "Object(0).valueOf()",       0,                      (new Object()).valueOf() );
this.TestCase( SECTION, "typeof Object(0)",          "object",               typeof new Object() );
this.TestCase( SECTION, "var MYOB = Object(0); MYOB.toString = Object.prototype.toString; MYOB.toString()",      "[object Number]",      eval("var MYOB = Object(0); MYOB.toString = Object.prototype.toString; MYOB.toString()") );

this.TestCase( SECTION, "Object(-0).valueOf()",      -0,                     (new Object()).valueOf() );
this.TestCase( SECTION, "typeof Object(-0)",         "object",               typeof new Object() );
this.TestCase( SECTION, "var MYOB = Object(-0); MYOB.toString = Object.prototype.toString; MYOB.toString()",     "[object Number]",      eval("var MYOB = Object(-0); MYOB.toString = Object.prototype.toString; MYOB.toString()") );

this.TestCase( SECTION, "Object(1).valueOf()",       1,                      (new Object()).valueOf() );
this.TestCase( SECTION, "typeof Object(1)",          "object",               typeof Object(1) );
this.TestCase( SECTION, "var MYOB = Object(1); MYOB.toString = Object.prototype.toString; MYOB.toString()",     "[object Number]",      eval("var MYOB = Object(1); MYOB.toString = Object.prototype.toString; MYOB.toString()") );

this.TestCase( SECTION, "Object(-1).valueOf()",      -1,                     (new Object()).valueOf() );
this.TestCase( SECTION, "typeof Object(-1)",         "object",               typeof new Object() );
this.TestCase( SECTION, "var MYOB = Object(-1); MYOB.toString = Object.prototype.toString; MYOB.toString()",     "[object Number]",      eval("var MYOB = Object(-1); MYOB.toString = Object.prototype.toString; MYOB.toString()") );

this.TestCase( SECTION, "Object(Number.MAX_VALUE).valueOf()",    1.7976931348623157e308,         (Object(Number.MAX_VALUE)).valueOf() );
this.TestCase( SECTION, "typeof Object(Number.MAX_VALUE)",       "object",                       typeof Object(Number.MAX_VALUE) );
this.TestCase( SECTION, "var MYOB = Object(Number.MAX_VALUE); MYOB.toString = Object.prototype.toString; MYOB.toString()",     "[object Number]",      eval("var MYOB = Object(Number.MAX_VALUE); MYOB.toString = Object.prototype.toString; MYOB.toString()") );

this.TestCase( SECTION, "Object(Number.MIN_VALUE).valueOf()",     5e-324,           (new Object()).valueOf() );
this.TestCase( SECTION, "typeof Object(Number.MIN_VALUE)",       "object",         typeof new Object());
this.TestCase( SECTION, "var MYOB = Object(Number.MIN_VALUE); MYOB.toString = Object.prototype.toString; MYOB.toString()",     "[object Number]",      eval("var MYOB = Object(Number.MIN_VALUE); MYOB.toString = Object.prototype.toString; MYOB.toString()") );

this.TestCase( SECTION, "Object(Number.POSITIVE_INFINITY).valueOf()",    Number.POSITIVE_INFINITY,       (Object(Number.POSITIVE_INFINITY)).valueOf() );
this.TestCase( SECTION, "typeof Object(Number.POSITIVE_INFINITY)",       "object",                       typeof Object(Number.POSITIVE_INFINITY) );
this.TestCase( SECTION, "var MYOB = Object(Number.POSITIVE_INFINITY); MYOB.toString = Object.prototype.toString; MYOB.toString()",     "[object Number]",      eval("var MYOB = Object(Number.POSITIVE_INFINITY); MYOB.toString = Object.prototype.toString; MYOB.toString()") );

this.TestCase( SECTION, "Object(Number.NEGATIVE_INFINITY).valueOf()",    Number.NEGATIVE_INFINITY,       (Object(Number.NEGATIVE_INFINITY)).valueOf() );
this.TestCase( SECTION, "typeof Object(Number.NEGATIVE_INFINITY)",       "object",            typeof Object(Number.NEGATIVE_INFINITY) );
this.TestCase( SECTION, "var MYOB = Object(Number.NEGATIVE_INFINITY); MYOB.toString = Object.prototype.toString; MYOB.toString()",     "[object Number]",      eval("var MYOB = Object(Number.NEGATIVE_INFINITY); MYOB.toString = Object.prototype.toString; MYOB.toString()") );

this.TestCase( SECTION, "Object(Number.NaN).valueOf()",      Number.NaN,                (Object(Number.NaN)).valueOf() );
this.TestCase( SECTION, "typeof Object(Number.NaN)",         "object",                  typeof Object(Number.NaN) );
this.TestCase( SECTION, "var MYOB = Object(Number.NaN); MYOB.toString = Object.prototype.toString; MYOB.toString()",     "[object Number]",      eval("var MYOB = Object(Number.NaN); MYOB.toString = Object.prototype.toString; MYOB.toString()") );

this.TestCase( SECTION, "Object('a string').valueOf()",      "a string",         (Object("a string")).valueOf() );
this.TestCase( SECTION, "typeof Object('a string')",         "object",           typeof (Object("a string")) );
this.TestCase( SECTION, "var MYOB = Object('a string'); MYOB.toString = Object.prototype.toString; MYOB.toString()",     "[object String]",      eval("var MYOB = Object('a string'); MYOB.toString = Object.prototype.toString; MYOB.toString()") );

this.TestCase( SECTION, "Object('').valueOf()",              "",                 (Object("")).valueOf() );
this.TestCase( SECTION, "typeof Object('')",                 "object",           typeof (Object("")) );
this.TestCase( SECTION, "var MYOB = Object(''); MYOB.toString = Object.prototype.toString; MYOB.toString()",     "[object String]",      eval("var MYOB = Object(''); MYOB.toString = Object.prototype.toString; MYOB.toString()") );

this.TestCase( SECTION, "Object('\\r\\t\\b\\n\\v\\f').valueOf()",   "\r\t\b\n\v\f",   (Object("\r\t\b\n\v\f")).valueOf() );
this.TestCase( SECTION, "typeof Object('\\r\\t\\b\\n\\v\\f')",      "object",           typeof (Object("\\r\\t\\b\\n\\v\\f")) );
this.TestCase( SECTION, "var MYOB = Object('\\r\\t\\b\\n\\v\\f'); MYOB.toString = Object.prototype.toString; MYOB.toString()",     "[object String]",      eval("var MYOB = Object('\\r\\t\\b\\n\\v\\f'); MYOB.toString = Object.prototype.toString; MYOB.toString()") );

this.TestCase( SECTION,  "Object( '\\\'\\\"\\' ).valueOf()",      "\'\"\\",          (Object("\'\"\\")).valueOf() );
this.TestCase( SECTION,  "typeof Object( '\\\'\\\"\\' )",        "object",           typeof Object("\'\"\\") );
//    this.TestCase( SECTION, "var MYOB = Object(  '\\\'\\\"\\' ); MYOB.toString = Object.prototype.toString; MYOB.toString()",     "[object String]",      eval("var MYOB = Object( '\\\'\\\"\\' ); MYOB.toString = Object.prototype.toString; MYOB.toString()") );

},

/**
File Name:          15.2.1.2.js
ECMA Section:       15.2.1.2  The Object Constructor Called as a Function:
Object(value)
Description:        When Object is called as a function rather than as a
constructor, the following steps are taken:

1.  If value is null or undefined, create and return a
new object with no proerties other than internal
properties exactly as if the object constructor
had been called on that same value (15.2.2.1).
2.  Return ToObject (value), whose rules are:

undefined   generate a runtime error
null        generate a runtime error
boolean     create a new Boolean object whose default
value is the value of the boolean.
number      Create a new Number object whose default
value is the value of the number.
string      Create a new String object whose default
value is the value of the string.
object      Return the input argument (no conversion).

Author:             christine@netscape.com
Date:               17 july 1997
*/

test_15_2_1_2:function(){

var SECTION = "15.2.1.2";
var VERSION = "ECMA_1";
var TITLE   = "Object()";

var MYOB = Object();

this.TestCase( SECTION, "var MYOB = Object(); MYOB.valueOf()",    MYOB,      MYOB.valueOf()      );
this.TestCase( SECTION, "typeof Object()",       "object",               typeof (Object(null)) );
this.TestCase( SECTION, "var MYOB = Object(); MYOB.toString()",    "[object Object]",       eval("var MYOB = Object(); MYOB.toString()") );

},


/**
File Name:          15.2.2.1.js
ECMA Section:       15.2.2.1 The Object Constructor:  new Object( value )

1.If the type of the value is not Object, go to step 4.
2.If the value is a native ECMAScript object, do not create a new object; simply return value.
3.If the value is a host object, then actions are taken and a result is returned in an
implementation-dependent manner that may depend on the host object.
4.If the type of the value is String, return ToObject(value).
5.If the type of the value is Boolean, return ToObject(value).
6.If the type of the value is Number, return ToObject(value).
7.(The type of the value must be Null or Undefined.) Create a new native ECMAScript object.
The [[Prototype]] property of the newly constructed object is set to the Object prototype object.
The [[Class]] property of the newly constructed object is set to "Object".
The newly constructed object has no [[Value]] property.
Return the newly created native object.

Description:        This does not test cases where the object is a host object.
Author:             christine@netscape.com
Date:               7 october 1997
*/

test_15_2_2_1:function(){

var SECTION = "15.2.2.1";
var VERSION = "ECMA_1";
var TITLE   = "new Object( value )";

this.TestCase( SECTION,  "typeof new Object(null)",      "object",           typeof new Object(null) );
this.TestCase( SECTION,  "MYOB = new Object(null); MYOB.toString = Object.prototype.toString; MYOB.toString()",  "[object Object]",   eval("MYOB = new Object(null); MYOB.toString = Object.prototype.toString; MYOB.toString()") );

this.TestCase( SECTION,  "typeof new Object(void 0)",      "object",           typeof new Object(void 0) );
this.TestCase( SECTION,  "MYOB = new Object(new Object(void 0)); MYOB.toString = Object.prototype.toString; MYOB.toString()",  "[object Object]",   eval("MYOB = new Object(new Object(void 0)); MYOB.toString = Object.prototype.toString; MYOB.toString()") );

this.TestCase( SECTION,  "typeof new Object('string')",      "object",           typeof new Object('string') );
this.TestCase( SECTION,  "MYOB = (new Object('string'); MYOB.toString = Object.prototype.toString; MYOB.toString()",  "[object String]",   eval("MYOB = new Object('string'); MYOB.toString = Object.prototype.toString; MYOB.toString()") );
this.TestCase( SECTION,  "(new Object('string').valueOf()",  "string",           (new Object('string')).valueOf() );

this.TestCase( SECTION,  "typeof new Object('')",            "object",           typeof new Object('') );
this.TestCase( SECTION,  "MYOB = (new Object(''); MYOB.toString = Object.prototype.toString; MYOB.toString()",  "[object String]",   eval("MYOB = new Object(''); MYOB.toString = Object.prototype.toString; MYOB.toString()") );
this.TestCase( SECTION,  "(new Object('').valueOf()",        "",                 (new Object('')).valueOf() );

this.TestCase( SECTION,  "typeof new Object(Number.NaN)",      "object",                 typeof new Object(Number.NaN) );
this.TestCase( SECTION,  "MYOB = (new Object(Number.NaN); MYOB.toString = Object.prototype.toString; MYOB.toString()",  "[object Number]",   eval("MYOB = new Object(Number.NaN); MYOB.toString = Object.prototype.toString; MYOB.toString()") );
this.TestCase( SECTION,  "(new Object(Number.NaN).valueOf()",  Number.NaN,               (new Object(Number.NaN)).valueOf() );

this.TestCase( SECTION,  "typeof new Object(0)",      "object",                 typeof new Object(0) );
this.TestCase( SECTION,  "MYOB = (new Object(0); MYOB.toString = Object.prototype.toString; MYOB.toString()",  "[object Number]",   eval("MYOB = new Object(0); MYOB.toString = Object.prototype.toString; MYOB.toString()") );
this.TestCase( SECTION,  "(new Object(0).valueOf()",  0,               (new Object(0)).valueOf() );

this.TestCase( SECTION,  "typeof new Object(-0)",      "object",                 typeof new Object(-0) );
this.TestCase( SECTION,  "MYOB = (new Object(-0); MYOB.toString = Object.prototype.toString; MYOB.toString()",  "[object Number]",   eval("MYOB = new Object(-0); MYOB.toString = Object.prototype.toString; MYOB.toString()") );
this.TestCase( SECTION,  "(new Object(-0).valueOf()",  -0,               (new Object(-0)).valueOf() );

this.TestCase( SECTION,  "typeof new Object(1)",      "object",                 typeof new Object(1) );
this.TestCase( SECTION,  "MYOB = (new Object(1); MYOB.toString = Object.prototype.toString; MYOB.toString()",  "[object Number]",   eval("MYOB = new Object(1); MYOB.toString = Object.prototype.toString; MYOB.toString()") );
this.TestCase( SECTION,  "(new Object(1).valueOf()",  1,               (new Object(1)).valueOf() );

this.TestCase( SECTION,  "typeof new Object(-1)",      "object",                 typeof new Object(-1) );
this.TestCase( SECTION,  "MYOB = (new Object(-1); MYOB.toString = Object.prototype.toString; MYOB.toString()",  "[object Number]",   eval("MYOB = new Object(-1); MYOB.toString = Object.prototype.toString; MYOB.toString()") );
this.TestCase( SECTION,  "(new Object(-1).valueOf()",  -1,               (new Object(-1)).valueOf() );

this.TestCase( SECTION,  "typeof new Object(true)",      "object",                 typeof new Object(true) );
this.TestCase( SECTION,  "MYOB = (new Object(true); MYOB.toString = Object.prototype.toString; MYOB.toString()",  "[object Boolean]",   eval("MYOB = new Object(true); MYOB.toString = Object.prototype.toString; MYOB.toString()") );
this.TestCase( SECTION,  "(new Object(true).valueOf()",  true,               (new Object(true)).valueOf() );

this.TestCase( SECTION,  "typeof new Object(false)",      "object",              typeof new Object(false) );
this.TestCase( SECTION,  "MYOB = (new Object(false); MYOB.toString = Object.prototype.toString; MYOB.toString()",  "[object Boolean]",   eval("MYOB = new Object(false); MYOB.toString = Object.prototype.toString; MYOB.toString()") );
this.TestCase( SECTION,  "(new Object(false).valueOf()",  false,                 (new Object(false)).valueOf() );

this.TestCase( SECTION,  "typeof new Object(Boolean())",         "object",               typeof new Object(Boolean()) );
this.TestCase( SECTION,  "MYOB = (new Object(Boolean()); MYOB.toString = Object.prototype.toString; MYOB.toString()",  "[object Boolean]",   eval("MYOB = new Object(Boolean()); MYOB.toString = Object.prototype.toString; MYOB.toString()") );
this.TestCase( SECTION,  "(new Object(Boolean()).valueOf()",     Boolean(),              (new Object(Boolean())).valueOf() );


var myglobal    = this;
var myobject    = new Object( "my new object" );
var myarray     = new Array();
var myboolean   = new Boolean();
var mynumber    = new Number();
var mystring    = new String();
var myobject    = new Object();
var myfunction  = new Function( "x", "return x");
var mymath      = Math;

this.TestCase( SECTION, "myglobal = new Object( this )",                     myglobal,       new Object(this) );
this.TestCase( SECTION, "myobject = new Object('my new object'); new Object(myobject)",            myobject,       new Object(myobject) );
this.TestCase( SECTION, "myarray = new Array(); new Object(myarray)",        myarray,        new Object(myarray) );
this.TestCase( SECTION, "myboolean = new Boolean(); new Object(myboolean)",  myboolean,      new Object(myboolean) );
this.TestCase( SECTION, "mynumber = new Number(); new Object(mynumber)",     mynumber,       new Object(mynumber) );
this.TestCase( SECTION, "mystring = new String9); new Object(mystring)",     mystring,       new Object(mystring) );
this.TestCase( SECTION, "myobject = new Object(); new Object(mynobject)",    myobject,       new Object(myobject) );
this.TestCase( SECTION, "myfunction = new Function(); new Object(myfunction)", myfunction,   new Object(myfunction) );
this.TestCase( SECTION, "mymath = Math; new Object(mymath)",                 mymath,         new Object(mymath) );
},


/**
File Name:          15.2.2.2.js
ECMA Section:       15.2.2.2 new Object()
Description:

When the Object constructor is called with no argument, the following
step is taken:

1.  Create a new native ECMAScript object.
The [[Prototype]] property of the newly constructed object is set to
the Object prototype object.

The [[Class]] property of the newly constructed object is set
to "Object".

The newly constructed object has no [[Value]] property.

Return the newly created native object.

Author:             christine@netscape.com
Date:               7 october 1997
*/
test_15_2_2_2:function(){
var SECTION = "15.2.2.2";
var VERSION = "ECMA_1";

var TITLE   = "new Object()";

this.TestCase( SECTION, "typeof new Object()",   "object",       typeof new Object() );
this.TestCase( SECTION, "Object.prototype.toString()",   "[object Object]",  Object.prototype.toString() );
this.TestCase( SECTION, "(new Object()).toString()",  "[object Object]",   (new Object()).toString() );

},


/**
File Name:          15.2.3-1.js
ECMA Section:       15.2.3 Properties of the Object Constructor

Description:        The value of the internal [[Prototype]] property of the
Object constructor is the Function prototype object.

Besides the call and construct propreties and the length
property, the Object constructor has properties described
in 15.2.3.1.

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_2_3__1:function(){
var SECTION = "15.2.3";
var VERSION = "ECMA_2";

this.TestCase( SECTION,  "Object.length",        1,                      Object.length );
},


/**
File Name:          15.2.3.1-1.js
ECMA Section:       15.2.3.1 Object.prototype

Description:        The initial value of Object.prototype is the built-in
Object prototype object.

This property shall have the attributes [ DontEnum,
DontDelete ReadOnly ]

This tests the [DontEnum] property of Object.prototype

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_2_3_1__1:function(){

var SECTION = "15.2.3.1-1";
var VERSION = "ECMA_1";
var TITLE   = "Object.prototype";

this.TestCase( SECTION,
"var str = '';for ( p in Object ) { str += p; }; str",
"",
eval( "var str = ''; for ( p in Object ) { str += p; }; str" ) );
},


/**
File Name:          15.2.3.1-2.js
ECMA Section:       15.2.3.1 Object.prototype

Description:        The initial value of Object.prototype is the built-in
Object prototype object.

This property shall have the attributes [ DontEnum,
DontDelete ReadOnly ]

This tests the [DontDelete] property of Object.prototype

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_2_3_1__2:function(){

var SECTION = "15.2.3.1-2";
var VERSION = "ECMA_1";
var TITLE   = "Object.prototype";

this.TestCase( SECTION,
"delete( Object.prototype )",
false,
eval("delete( Object.prototype )") );
},

/**
File Name:          15.2.3.1-3.js
ECMA Section:       15.2.3.1 Object.prototype

Description:        The initial value of Object.prototype is the built-in
Object prototype object.

This property shall have the attributes [ DontEnum,
DontDelete ReadOnly ]

This tests the [ReadOnly] property of Object.prototype

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_2_3_1__3:function(){

var SECTION = "15.2.3.1-3";
var VERSION = "ECMA_1";
var TITLE   = "Object.prototype";

this.TestCase( SECTION,
"Object.prototype = null; Object.prototype",
Object.prototype,
eval("Object.prototype = null; Object.prototype"));
},


/**
File Name:          15.2.3.1-4.js
ECMA Section:       15.2.3.1 Object.prototype

Description:        The initial value of Object.prototype is the built-in
Object prototype object.

This property shall have the attributes [ DontEnum,
DontDelete ReadOnly ]

This tests the [DontDelete] property of Object.prototype

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_2_3_1__4:function(){

var SECTION = "15.2.3.1-4";
var VERSION = "ECMA_1";
var TITLE   = "Object.prototype";


this.TestCase( SECTION,
"delete( Object.prototype ); Object.prototype",
Object.prototype,
eval("delete(Object.prototype); Object.prototype") );
},


/**
File Name:          15.2.3.js
ECMA Section:       15.2.3 Properties of the Object Constructor

Description:        The value of the internal [[Prototype]] property of the
Object constructor is the Function prototype object.

Besides the call and construct propreties and the length
property, the Object constructor has properties described
in 15.2.3.1.

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_2_3:function(){

var SECTION = "15.2.3";
var VERSION = "ECMA_1";
var TITLE   = "Properties of the Object Constructor";

//    this.TestCase( SECTION,  "Object.__proto__",     Function.prototype,     Object.__proto__ );
this.TestCase( SECTION,  "Object.length",        1,                      Object.length );
},


/**
File Name:          15.2.4.1.js
ECMA Section:       15.2.4 Object.prototype.constructor

Description:        The initial value of the Object.prototype.constructor
is the built-in Object constructor.

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_2_4_1:function(){
var SECTION = "15.2.4.1";
var VERSION = "ECMA_1";
var TITLE   = "Object.prototype.constructor";

this.TestCase( SECTION,
"Object.prototype.constructor",
Object,
Object.prototype.constructor );
},


/**
File Name:          15.2.4.2.js
ECMA Section:       15.2.4.2 Object.prototype.toString()

Description:        When the toString method is called, the following
steps are taken:
1.  Get the [[Class]] property of this object
2.  Call ToString( Result(1) )
3.  Compute a string value by concatenating the three
strings "[object " + Result(2) + "]"
4.  Return Result(3).

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_2_4_2:function(){

var SECTION = "15.2.4.2";
var VERSION = "ECMA_1";
var TITLE   = "Object.prototype.toString()";
var GLOBAL = "[object Object]";
this.TestCase( SECTION,  "(new Object()).toString()",    "[object Object]",  (new Object()).toString() );

this.TestCase( SECTION,  "myvar = this;  myvar.toString = Object.prototype.toString; myvar.toString()",
GLOBAL.replace(/ @ 0x[0-9a-fA-F]+ \(native @ 0x[0-9a-fA-F]+\)/, ''),
eval("myvar = this;  myvar.toString = Object.prototype.toString; myvar.toString()")
);

this.TestCase( SECTION,  "myvar = MyObject_15_2_4_2; myvar.toString = Object.prototype.toString; myvar.toString()",
"[object Function]",
eval("myvar = MyObject_15_2_4_2; myvar.toString = Object.prototype.toString; myvar.toString()") );

this.TestCase( SECTION,  "myvar = new MyObject_15_2_4_2( true ); myvar.toString = Object.prototype.toString; myvar.toString()",
'[object Object]',
eval("myvar = new MyObject_15_2_4_2( true ); myvar.toString = Object.prototype.toString; myvar.toString()") );

this.TestCase( SECTION,  "myvar = new Number(0); myvar.toString = Object.prototype.toString; myvar.toString()",
"[object Number]",
eval("myvar = new Number(0); myvar.toString = Object.prototype.toString; myvar.toString()") );

this.TestCase( SECTION,  "myvar = new String(''); myvar.toString = Object.prototype.toString; myvar.toString()",
"[object String]",
eval("myvar = new String(''); myvar.toString = Object.prototype.toString; myvar.toString()") );

this.TestCase( SECTION,  "myvar = Math; myvar.toString = Object.prototype.toString; myvar.toString()",
"[object Math]",
eval("myvar = Math; myvar.toString = Object.prototype.toString; myvar.toString()") );

this.TestCase( SECTION,  "myvar = new Function(); myvar.toString = Object.prototype.toString; myvar.toString()",
"[object Function]",
eval("myvar = new Function(); myvar.toString = Object.prototype.toString; myvar.toString()") );

this.TestCase( SECTION,  "myvar = new Array(); myvar.toString = Object.prototype.toString; myvar.toString()",
"[object Array]",
eval("myvar = new Array(); myvar.toString = Object.prototype.toString; myvar.toString()") );

this.TestCase( SECTION,  "myvar = new Boolean(); myvar.toString = Object.prototype.toString; myvar.toString()",
"[object Boolean]",
eval("myvar = new Boolean(); myvar.toString = Object.prototype.toString; myvar.toString()") );

this.TestCase( SECTION,  "myvar = new Date(); myvar.toString = Object.prototype.toString; myvar.toString()",
"[object Date]",
eval("myvar = new Date(); myvar.toString = Object.prototype.toString; myvar.toString()") );

this.TestCase( SECTION,  "var MYVAR = new Object( this ); MYVAR.toString()",
GLOBAL.replace(/ @ 0x[0-9a-fA-F]+ \(native @ 0x[0-9a-fA-F]+\)/, ''),
eval("var MYVAR = new Object( this ); MYVAR.toString()")
);

this.TestCase( SECTION,  "var MYVAR = new Object(); MYVAR.toString()",
"[object Object]",
eval("var MYVAR = new Object(); MYVAR.toString()") );

this.TestCase( SECTION,  "var MYVAR = new Object(void 0); MYVAR.toString()",
"[object Object]",
eval("var MYVAR = new Object(void 0); MYVAR.toString()") );

this.TestCase( SECTION,  "var MYVAR = new Object(null); MYVAR.toString()",
"[object Object]",
eval("var MYVAR = new Object(null); MYVAR.toString()") );
},


/**
File Name:          15.2.4.3.js
ECMA Section:       15.2.4.3 Object.prototype.valueOf()

Description:        As a rule, the valueOf method for an object simply
returns the object; but if the object is a "wrapper"
for a host object, as may perhaps be created by the
Object constructor, then the contained host object
should be returned.

This only covers native objects.

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_2_4_3:function(){

var SECTION = "15.2.4.3";
var VERSION = "ECMA_1";
var TITLE   = "Object.prototype.valueOf()";

var myarray = new Array();
myarray.valueOf = Object.prototype.valueOf;
var myboolean = new Boolean();
myboolean.valueOf = Object.prototype.valueOf;
var myfunction = new Function();
myfunction.valueOf = Object.prototype.valueOf;
var myobject = new Object();
myobject.valueOf = Object.prototype.valueOf;
var mymath = Math;
mymath.valueOf = Object.prototype.valueOf;
var mydate = new Date();
mydate.valueOf = Object.prototype.valueOf;
var mynumber = new Number();
mynumber.valueOf = Object.prototype.valueOf;
var mystring = new String();
mystring.valueOf = Object.prototype.valueOf;

this.TestCase( SECTION,  "Object.prototype.valueOf.length",      0,      Object.prototype.valueOf.length );

this.TestCase( SECTION,
"myarray = new Array(); myarray.valueOf = Object.prototype.valueOf; myarray.valueOf()",
myarray,
myarray.valueOf() );
this.TestCase( SECTION,
"myboolean = new Boolean(); myboolean.valueOf = Object.prototype.valueOf; myboolean.valueOf()",
myboolean,
myboolean.valueOf() );
this.TestCase( SECTION,
"myfunction = new Function(); myfunction.valueOf = Object.prototype.valueOf; myfunction.valueOf()",
myfunction,
myfunction.valueOf() );
this.TestCase( SECTION,
"myobject = new Object(); myobject.valueOf = Object.prototype.valueOf; myobject.valueOf()",
myobject,
myobject.valueOf() );
this.TestCase( SECTION,
"mymath = Math; mymath.valueOf = Object.prototype.valueOf; mymath.valueOf()",
mymath,
mymath.valueOf() );
this.TestCase( SECTION,
"mynumber = new Number(); mynumber.valueOf = Object.prototype.valueOf; mynumber.valueOf()",
mynumber,
mynumber.valueOf() );
this.TestCase( SECTION,
"mystring = new String(); mystring.valueOf = Object.prototype.valueOf; mystring.valueOf()",
mystring,
mystring.valueOf() );
this.TestCase( SECTION,
"mydate = new Date(); mydate.valueOf = Object.prototype.valueOf; mydate.valueOf()",
mydate,
mydate.valueOf() );

}

}).endType();


