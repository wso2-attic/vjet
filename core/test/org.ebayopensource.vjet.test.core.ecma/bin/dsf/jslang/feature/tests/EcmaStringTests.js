vjo.ctype("dsf.jslang.feature.tests.EcmaStringTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({




//>public void fail()
fail : function(){
},

constructs: function() {
this.base();
},

/**
File Name:          15.5.1.js
ECMA Section:       15.5.1 The String Constructor called as a Function
15.5.1.1 String(value)
15.5.1.2 String()

Description:	When String is called as a function rather than as
a constructor, it performs a type conversion.
- String(value) returns a string value (not a String
object) computed by ToString(value)
- String() returns the empty string ""

Author:             christine@netscape.com
Date:               1 october 1997
*/

test_15_5_1: function() {
var SECTION = "15.5.1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The String Constructor Called as a Function";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,	"String('string primitive')",	"string primitive",	String('string primitive') );
this.TestCase( SECTION,	"String(void 0)",		"undefined",		String( void 0) );
this.TestCase( SECTION,	"String(null)",			    "null",			String( null ) );
this.TestCase( SECTION,	"String(true)",			    "true",			String( true) );
this.TestCase( SECTION,	"String(false)",		    "false",		String( false ) );
this.TestCase( SECTION,	"String(Boolean(true))",	"true",			String(Boolean(true)) );
this.TestCase( SECTION,	"String(Boolean(false))",	"false",		String(Boolean(false)) );
this.TestCase( SECTION,	"String(Boolean())",		"false",		String(Boolean(false)) );
this.TestCase( SECTION,	"String(new Array())",		"",			    String( new Array()) );
this.TestCase( SECTION,	"String(new Array(1,2,3))",	"1,2,3",		String( new Array(1,2,3)) );


this.TestCase( SECTION,    "String( Number.NaN )",       "NaN",                  String( Number.NaN ) );
this.TestCase( SECTION,    "String( 0 )",                "0",                    String( 0 ) );
this.TestCase( SECTION,    "String( -0 )",               "0",                   String( -0 ) );
this.TestCase( SECTION,    "String( Number.POSITIVE_INFINITY )", "Infinity",     String( Number.POSITIVE_INFINITY ) );
this.TestCase( SECTION,    "String( Number.NEGATIVE_INFINITY )", "-Infinity",    String( Number.NEGATIVE_INFINITY ) );
this.TestCase( SECTION,    "String( -1 )",               "-1",                   String( -1 ) );

// cases in step 6:  integers  1e21 > x >= 1 or -1 >= x > -1e21

this.TestCase( SECTION,    "String( 1 )",                    "1",                    String( 1 ) );
this.TestCase( SECTION,    "String( 10 )",                   "10",                   String( 10 ) );
this.TestCase( SECTION,    "String( 100 )",                  "100",                  String( 100 ) );
this.TestCase( SECTION,    "String( 1000 )",                 "1000",                 String( 1000 ) );
this.TestCase( SECTION,    "String( 10000 )",                "10000",                String( 10000 ) );
this.TestCase( SECTION,    "String( 10000000000 )",          "10000000000",          String( 10000000000 ) );
this.TestCase( SECTION,    "String( 10000000000000000000 )", "10000000000000000000", String( 10000000000000000000 ) );
this.TestCase( SECTION,    "String( 100000000000000000000 )","100000000000000000000",String( 100000000000000000000 ) );

this.TestCase( SECTION,    "String( 12345 )",                    "12345",                    String( 12345 ) );
this.TestCase( SECTION,    "String( 1234567890 )",               "1234567890",               String( 1234567890 ) );

this.TestCase( SECTION,    "String( -1 )",                       "-1",                       String( -1 ) );
this.TestCase( SECTION,    "String( -10 )",                      "-10",                      String( -10 ) );
this.TestCase( SECTION,    "String( -100 )",                     "-100",                     String( -100 ) );
this.TestCase( SECTION,    "String( -1000 )",                    "-1000",                    String( -1000 ) );
this.TestCase( SECTION,    "String( -1000000000 )",              "-1000000000",              String( -1000000000 ) );
this.TestCase( SECTION,    "String( -1000000000000000 )",        "-1000000000000000",        String( -1000000000000000 ) );
this.TestCase( SECTION,    "String( -100000000000000000000 )",   "-100000000000000000000",   String( -100000000000000000000 ) );
this.TestCase( SECTION,    "String( -1000000000000000000000 )",  "-1e+21",                   String( -1000000000000000000000 ) );

this.TestCase( SECTION,    "String( -12345 )",                    "-12345",                  String( -12345 ) );
this.TestCase( SECTION,    "String( -1234567890 )",               "-1234567890",             String( -1234567890 ) );

// cases in step 7: numbers with a fractional component, 1e21> x >1 or  -1 > x > -1e21,
this.TestCase( SECTION,    "String( 1.0000001 )",                "1.0000001",                String( 1.0000001 ) );


// cases in step 8:  fractions between 1 > x > -1, exclusive of 0 and -0

// cases in step 9:  numbers with 1 significant digit >= 1e+21 or <= 1e-6

this.TestCase( SECTION,    "String( 1000000000000000000000 )",   "1e+21",             String( 1000000000000000000000 ) );
this.TestCase( SECTION,    "String( 10000000000000000000000 )",   "1e+22",            String( 10000000000000000000000 ) );

//  cases in step 10:  numbers with more than 1 significant digit >= 1e+21 or <= 1e-6
this.TestCase( SECTION,    "String( 1.2345 )",                    "1.2345",                  String( 1.2345));
this.TestCase( SECTION,    "String( 1.234567890 )",               "1.23456789",             String( 1.234567890 ));

this.TestCase( SECTION,    "String( .12345 )",                   "0.12345",               String(.12345 )     );
this.TestCase( SECTION,    "String( .012345 )",                  "0.012345",              String(.012345)     );
this.TestCase( SECTION,    "String( .0012345 )",                 "0.0012345",             String(.0012345)    );
this.TestCase( SECTION,    "String( .00012345 )",                "0.00012345",            String(.00012345)   );
this.TestCase( SECTION,    "String( .000012345 )",               "0.000012345",           String(.000012345)  );
this.TestCase( SECTION,    "String( .0000012345 )",              "0.0000012345",          String(.0000012345) );
this.TestCase( SECTION,    "String( .00000012345 )",             "1.2345e-7",            String(.00000012345));

this.TestCase( "15.5.2",	"String()",			        "",			    String() );

//test();

},

/**
File Name:          15.5.2.js
ECMA Section:       15.5.2 The String Constructor
15.5.2.1 new String(value)
15.5.2.2 new String()

Description:	When String is called as part of a new expression, it
is a constructor; it initializes the newly constructed
object.

- The prototype property of the newly constructed
object is set to the original String prototype object,
the one that is the intial value of String.prototype
- The internal [[Class]] property of the object is "String"
- The value of the object is ToString(value).
- If no value is specified, its value is the empty string.

Author:             christine@netscape.com
Date:               1 october 1997
*/
test_15_5_2: function() {
var SECTION = "15.5.2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The String Constructor";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,	"typeof new String('string primitive')",	    "object",	        typeof new String('string primitive') );
this.TestCase( SECTION,	"var TESTSTRING = new String('string primitive'); TESTSTRING.toString=Object.prototype.toString;TESTSTRING.toString()", "[object String]",   eval("var TESTSTRING = new String('string primitive'); TESTSTRING.toString=Object.prototype.toString;TESTSTRING.toString()") );
this.TestCase( SECTION,  "(new String('string primitive')).valueOf()",   'string primitive', (new String('string primitive')).valueOf() );
this.TestCase( SECTION,  "(new String('string primitive')).substring",   String.prototype.substring,   (new String('string primitive')).substring );

this.TestCase( SECTION,	"typeof new String(void 0)",	                "object",	        typeof new String(void 0) );
this.TestCase( SECTION,	"var TESTSTRING = new String(void 0); TESTSTRING.toString=Object.prototype.toString;TESTSTRING.toString()", "[object String]",   eval("var TESTSTRING = new String(void 0); TESTSTRING.toString=Object.prototype.toString;TESTSTRING.toString()") );
this.TestCase( SECTION,  "(new String(void 0)).valueOf()",               "undefined", (new String(void 0)).valueOf() );
this.TestCase( SECTION,  "(new String(void 0)).toString",               String.prototype.toString,   (new String(void 0)).toString );

this.TestCase( SECTION,	"typeof new String(null)",	            "object",	        typeof new String(null) );
this.TestCase( SECTION,	"var TESTSTRING = new String(null); TESTSTRING.toString=Object.prototype.toString;TESTSTRING.toString()", "[object String]",   eval("var TESTSTRING = new String(null); TESTSTRING.toString=Object.prototype.toString;TESTSTRING.toString()") );
this.TestCase( SECTION,  "(new String(null)).valueOf()",         "null",             (new String(null)).valueOf() );
this.TestCase( SECTION,  "(new String(null)).valueOf",         String.prototype.valueOf,   (new String(null)).valueOf );

this.TestCase( SECTION,	"typeof new String(true)",	            "object",	        typeof new String(true) );
this.TestCase( SECTION,	"var TESTSTRING = new String(true); TESTSTRING.toString=Object.prototype.toString;TESTSTRING.toString()", "[object String]",   eval("var TESTSTRING = new String(true); TESTSTRING.toString=Object.prototype.toString;TESTSTRING.toString()") );
this.TestCase( SECTION,  "(new String(true)).valueOf()",         "true",             (new String(true)).valueOf() );
this.TestCase( SECTION,  "(new String(true)).charAt",         String.prototype.charAt,   (new String(true)).charAt );

this.TestCase( SECTION,	"typeof new String(false)",	            "object",	        typeof new String(false) );
this.TestCase( SECTION,	"var TESTSTRING = new String(false); TESTSTRING.toString=Object.prototype.toString;TESTSTRING.toString()", "[object String]",   eval("var TESTSTRING = new String(false); TESTSTRING.toString=Object.prototype.toString;TESTSTRING.toString()") );
this.TestCase( SECTION,  "(new String(false)).valueOf()",        "false",            (new String(false)).valueOf() );
this.TestCase( SECTION,  "(new String(false)).charCodeAt",        String.prototype.charCodeAt,   (new String(false)).charCodeAt );

this.TestCase( SECTION,	"typeof new String(new Boolean(true))",	       "object",	        typeof new String(new Boolean(true)) );
this.TestCase( SECTION,	"var TESTSTRING = new String(new Boolean(true)); TESTSTRING.toString=Object.prototype.toString;TESTSTRING.toString()", "[object String]",   eval("var TESTSTRING = new String(new Boolean(true)); TESTSTRING.toString=Object.prototype.toString;TESTSTRING.toString()") );
this.TestCase( SECTION,  "(new String(new Boolean(true))).valueOf()",   "true",              (new String(new Boolean(true))).valueOf() );
this.TestCase( SECTION,  "(new String(new Boolean(true))).indexOf",   String.prototype.indexOf,    (new String(new Boolean(true))).indexOf );

this.TestCase( SECTION,	"typeof new String()",	                        "object",	        typeof new String() );
this.TestCase( SECTION,	"var TESTSTRING = new String(); TESTSTRING.toString=Object.prototype.toString;TESTSTRING.toString()", "[object String]",   eval("var TESTSTRING = new String(); TESTSTRING.toString=Object.prototype.toString;TESTSTRING.toString()") );
this.TestCase( SECTION,  "(new String()).valueOf()",   '',                 (new String()).valueOf() );
this.TestCase( SECTION,  "(new String()).lastIndexOf",   String.prototype.lastIndexOf,   (new String()).lastIndexOf );

this.TestCase( SECTION,	"typeof new String('')",	    "object",	        typeof new String('') );
this.TestCase( SECTION,	"var TESTSTRING = new String(''); TESTSTRING.toString=Object.prototype.toString;TESTSTRING.toString()", "[object String]",   eval("var TESTSTRING = new String(''); TESTSTRING.toString=Object.prototype.toString;TESTSTRING.toString()") );
this.TestCase( SECTION,  "(new String('')).valueOf()",   '',                 (new String('')).valueOf() );
this.TestCase( SECTION,  "(new String('')).split",   String.prototype.split,   (new String('')).split );
//test();

},

/**
File Name:          15.5.3.1-1.js
ECMA Section:       15.5.3.1 Properties of the String Constructor

Description:        The initial value of String.prototype is the built-in
String prototype object.

This property shall have the attributes [ DontEnum,
DontDelete, ReadOnly]

This tests the DontEnum attribute.

Author:             christine@netscape.com
Date:               1 october 1997
*/
test_15_5_3_1__1: function() {
var SECTION = "15.5.3.1-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Properties of the String Constructor";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION, "String.prototype.length",   0,  String.prototype.length );

this.TestCase(   SECTION,
"var str='';for ( p in String ) { if ( p == 'prototype' ) str += p; } str",
"",
eval("var str='';for ( p in String ) { if ( p == 'prototype' ) str += p; } str") );

//test();

},

/**
File Name:          15.5.3.1-2.js
ECMA Section:       15.5.3.1 Properties of the String Constructor

Description:        The initial value of String.prototype is the built-in
String prototype object.

This property shall have the attributes [ DontEnum,
DontDelete, ReadOnly]

This tests the ReadOnly attribute.

Author:             christine@netscape.com
Date:               1 october 1997
*/
test_15_5_3_1__2: function() {
var SECTION = "15.5.3.1-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Properties of the String Constructor";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"String.prototype=null;String.prototype",
String.prototype,
eval("String.prototype=null;String.prototype") );

//test();

},

/**
File Name:          15.5.3.1-3.js
ECMA Section:       15.5.3.1 Properties of the String Constructor

Description:        The initial value of String.prototype is the built-in
String prototype object.

This property shall have the attributes [ DontEnum,
DontDelete, ReadOnly]

This tests the DontDelete attribute.

Author:             christine@netscape.com
Date:               1 october 1997
*/
test_15_5_3_1__3: function() {
var SECTION = "15.5.3.1-3";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Properties of the String Constructor";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,	"delete( String.prototype )",   false,   eval("delete ( String.prototype )") );

//test();

},

/**
File Name:          15.5.3.1-4.js
ECMA Section:       15.5.3.1 Properties of the String Constructor

Description:        The initial value of String.prototype is the built-in
String prototype object.

This property shall have the attributes [ DontEnum,
DontDelete, ReadOnly]

This tests the DontDelete attribute.

Author:             christine@netscape.com
Date:               1 october 1997
*/
test_15_5_3_1__4: function() {
var SECTION = "15.5.3.1-4";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Properties of the String Constructor";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,	"delete( String.prototype );String.prototype",   String.prototype,   eval("delete ( String.prototype );String.prototype") );

//test();

},

/**
File Name:          15.5.3.2-1.js
ECMA Section:       15.5.3.2  String.fromCharCode( char0, char1, ... )
Description:        Return a string value containing as many characters
as the number of arguments.  Each argument specifies
one character of the resulting string, with the first
argument specifying the first character, and so on,
from left to right.  An argument is converted to a
character by applying the operation ToUint16 and
regarding the resulting 16bit integeras the Unicode
encoding of a character.  If no arguments are supplied,
the result is the empty string.

This test covers Basic Latin (range U+0020 - U+007F)

Author:             christine@netscape.com
Date:               2 october 1997
*/
test_15_5_3_2__1: function() {
var SECTION = "15.5.3.2-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.fromCharCode()";

this.TestCase( SECTION,   "typeof String.fromCharCode",      "function", typeof String.fromCharCode );
this.TestCase( SECTION,   "typeof String.prototype.fromCharCode",        "undefined", typeof String.prototype.fromCharCode );
this.TestCase( SECTION,   "var x = new String(); typeof x.fromCharCode", "undefined", eval("var x = new String(); typeof x.fromCharCode") );
this.TestCase( SECTION,   "String.fromCharCode.length",      1,      String.fromCharCode.length );

this.TestCase( SECTION,    "String.fromCharCode()",          "",     String.fromCharCode() );
this.TestCase( SECTION,   "String.fromCharCode(0x0020)",     " ",   String.fromCharCode(0x0020) );
this.TestCase( SECTION,   "String.fromCharCode(0x0021)",     "!",   String.fromCharCode(0x0021) );
this.TestCase( SECTION,   "String.fromCharCode(0x0022)",     "\"",   String.fromCharCode(0x0022) );
this.TestCase( SECTION,   "String.fromCharCode(0x0023)",     "#",   String.fromCharCode(0x0023) );
this.TestCase( SECTION,   "String.fromCharCode(0x0024)",     "$",   String.fromCharCode(0x0024) );
this.TestCase( SECTION,   "String.fromCharCode(0x0025)",     "%",   String.fromCharCode(0x0025) );
this.TestCase( SECTION,   "String.fromCharCode(0x0026)",     "&",   String.fromCharCode(0x0026) );
this.TestCase( SECTION,   "String.fromCharCode(0x0027)",     "\'",   String.fromCharCode(0x0027) );
this.TestCase( SECTION,   "String.fromCharCode(0x0028)",     "(",   String.fromCharCode(0x0028) );
this.TestCase( SECTION,   "String.fromCharCode(0x0029)",     ")",   String.fromCharCode(0x0029) );
this.TestCase( SECTION,   "String.fromCharCode(0x002A)",     "*",   String.fromCharCode(0x002A) );
this.TestCase( SECTION,   "String.fromCharCode(0x002B)",     "+",   String.fromCharCode(0x002B) );
this.TestCase( SECTION,   "String.fromCharCode(0x002C)",     ",",   String.fromCharCode(0x002C) );
this.TestCase( SECTION,   "String.fromCharCode(0x002D)",     "-",   String.fromCharCode(0x002D) );
this.TestCase( SECTION,   "String.fromCharCode(0x002E)",     ".",   String.fromCharCode(0x002E) );
this.TestCase( SECTION,   "String.fromCharCode(0x002F)",     "/",   String.fromCharCode(0x002F) );

this.TestCase( SECTION,   "String.fromCharCode(0x0030)",     "0",   String.fromCharCode(0x0030) );
this.TestCase( SECTION,   "String.fromCharCode(0x0031)",     "1",   String.fromCharCode(0x0031) );
this.TestCase( SECTION,   "String.fromCharCode(0x0032)",     "2",   String.fromCharCode(0x0032) );
this.TestCase( SECTION,   "String.fromCharCode(0x0033)",     "3",   String.fromCharCode(0x0033) );
this.TestCase( SECTION,   "String.fromCharCode(0x0034)",     "4",   String.fromCharCode(0x0034) );
this.TestCase( SECTION,   "String.fromCharCode(0x0035)",     "5",   String.fromCharCode(0x0035) );
this.TestCase( SECTION,   "String.fromCharCode(0x0036)",     "6",   String.fromCharCode(0x0036) );
this.TestCase( SECTION,   "String.fromCharCode(0x0037)",     "7",   String.fromCharCode(0x0037) );
this.TestCase( SECTION,   "String.fromCharCode(0x0038)",     "8",   String.fromCharCode(0x0038) );
this.TestCase( SECTION,   "String.fromCharCode(0x0039)",     "9",   String.fromCharCode(0x0039) );
this.TestCase( SECTION,   "String.fromCharCode(0x003A)",     ":",   String.fromCharCode(0x003A) );
this.TestCase( SECTION,   "String.fromCharCode(0x003B)",     ";",   String.fromCharCode(0x003B) );
this.TestCase( SECTION,   "String.fromCharCode(0x003C)",     "<",   String.fromCharCode(0x003C) );
this.TestCase( SECTION,   "String.fromCharCode(0x003D)",     "=",   String.fromCharCode(0x003D) );
this.TestCase( SECTION,   "String.fromCharCode(0x003E)",     ">",   String.fromCharCode(0x003E) );
this.TestCase( SECTION,   "String.fromCharCode(0x003F)",     "?",   String.fromCharCode(0x003F) );

this.TestCase( SECTION,   "String.fromCharCode(0x0040)",     "@",   String.fromCharCode(0x0040) );
this.TestCase( SECTION,   "String.fromCharCode(0x0041)",     "A",   String.fromCharCode(0x0041) );
this.TestCase( SECTION,   "String.fromCharCode(0x0042)",     "B",   String.fromCharCode(0x0042) );
this.TestCase( SECTION,   "String.fromCharCode(0x0043)",     "C",   String.fromCharCode(0x0043) );
this.TestCase( SECTION,   "String.fromCharCode(0x0044)",     "D",   String.fromCharCode(0x0044) );
this.TestCase( SECTION,   "String.fromCharCode(0x0045)",     "E",   String.fromCharCode(0x0045) );
this.TestCase( SECTION,   "String.fromCharCode(0x0046)",     "F",   String.fromCharCode(0x0046) );
this.TestCase( SECTION,   "String.fromCharCode(0x0047)",     "G",   String.fromCharCode(0x0047) );
this.TestCase( SECTION,   "String.fromCharCode(0x0048)",     "H",   String.fromCharCode(0x0048) );
this.TestCase( SECTION,   "String.fromCharCode(0x0049)",     "I",   String.fromCharCode(0x0049) );
this.TestCase( SECTION,   "String.fromCharCode(0x004A)",     "J",   String.fromCharCode(0x004A) );
this.TestCase( SECTION,   "String.fromCharCode(0x004B)",     "K",   String.fromCharCode(0x004B) );
this.TestCase( SECTION,   "String.fromCharCode(0x004C)",     "L",   String.fromCharCode(0x004C) );
this.TestCase( SECTION,   "String.fromCharCode(0x004D)",     "M",   String.fromCharCode(0x004D) );
this.TestCase( SECTION,   "String.fromCharCode(0x004E)",     "N",   String.fromCharCode(0x004E) );
this.TestCase( SECTION,   "String.fromCharCode(0x004F)",     "O",   String.fromCharCode(0x004F) );

this.TestCase( SECTION,   "String.fromCharCode(0x0040)",     "@",   String.fromCharCode(0x0040) );
this.TestCase( SECTION,   "String.fromCharCode(0x0041)",     "A",   String.fromCharCode(0x0041) );
this.TestCase( SECTION,   "String.fromCharCode(0x0042)",     "B",   String.fromCharCode(0x0042) );
this.TestCase( SECTION,   "String.fromCharCode(0x0043)",     "C",   String.fromCharCode(0x0043) );
this.TestCase( SECTION,   "String.fromCharCode(0x0044)",     "D",   String.fromCharCode(0x0044) );
this.TestCase( SECTION,   "String.fromCharCode(0x0045)",     "E",   String.fromCharCode(0x0045) );
this.TestCase( SECTION,   "String.fromCharCode(0x0046)",     "F",   String.fromCharCode(0x0046) );
this.TestCase( SECTION,   "String.fromCharCode(0x0047)",     "G",   String.fromCharCode(0x0047) );
this.TestCase( SECTION,   "String.fromCharCode(0x0048)",     "H",   String.fromCharCode(0x0048) );
this.TestCase( SECTION,   "String.fromCharCode(0x0049)",     "I",   String.fromCharCode(0x0049) );
this.TestCase( SECTION,   "String.fromCharCode(0x004A)",     "J",   String.fromCharCode(0x004A) );
this.TestCase( SECTION,   "String.fromCharCode(0x004B)",     "K",   String.fromCharCode(0x004B) );
this.TestCase( SECTION,   "String.fromCharCode(0x004C)",     "L",   String.fromCharCode(0x004C) );
this.TestCase( SECTION,   "String.fromCharCode(0x004D)",     "M",   String.fromCharCode(0x004D) );
this.TestCase( SECTION,   "String.fromCharCode(0x004E)",     "N",   String.fromCharCode(0x004E) );
this.TestCase( SECTION,   "String.fromCharCode(0x004F)",     "O",   String.fromCharCode(0x004F) );

this.TestCase( SECTION,   "String.fromCharCode(0x0050)",     "P",   String.fromCharCode(0x0050) );
this.TestCase( SECTION,   "String.fromCharCode(0x0051)",     "Q",   String.fromCharCode(0x0051) );
this.TestCase( SECTION,   "String.fromCharCode(0x0052)",     "R",   String.fromCharCode(0x0052) );
this.TestCase( SECTION,   "String.fromCharCode(0x0053)",     "S",   String.fromCharCode(0x0053) );
this.TestCase( SECTION,   "String.fromCharCode(0x0054)",     "T",   String.fromCharCode(0x0054) );
this.TestCase( SECTION,   "String.fromCharCode(0x0055)",     "U",   String.fromCharCode(0x0055) );
this.TestCase( SECTION,   "String.fromCharCode(0x0056)",     "V",   String.fromCharCode(0x0056) );
this.TestCase( SECTION,   "String.fromCharCode(0x0057)",     "W",   String.fromCharCode(0x0057) );
this.TestCase( SECTION,   "String.fromCharCode(0x0058)",     "X",   String.fromCharCode(0x0058) );
this.TestCase( SECTION,   "String.fromCharCode(0x0059)",     "Y",   String.fromCharCode(0x0059) );
this.TestCase( SECTION,   "String.fromCharCode(0x005A)",     "Z",   String.fromCharCode(0x005A) );
this.TestCase( SECTION,   "String.fromCharCode(0x005B)",     "[",   String.fromCharCode(0x005B) );
this.TestCase( SECTION,   "String.fromCharCode(0x005C)",     "\\",   String.fromCharCode(0x005C) );
this.TestCase( SECTION,   "String.fromCharCode(0x005D)",     "]",   String.fromCharCode(0x005D) );
this.TestCase( SECTION,   "String.fromCharCode(0x005E)",     "^",   String.fromCharCode(0x005E) );
this.TestCase( SECTION,   "String.fromCharCode(0x005F)",     "_",   String.fromCharCode(0x005F) );

this.TestCase( SECTION,   "String.fromCharCode(0x0060)",     "`",   String.fromCharCode(0x0060) );
this.TestCase( SECTION,   "String.fromCharCode(0x0061)",     "a",   String.fromCharCode(0x0061) );
this.TestCase( SECTION,   "String.fromCharCode(0x0062)",     "b",   String.fromCharCode(0x0062) );
this.TestCase( SECTION,   "String.fromCharCode(0x0063)",     "c",   String.fromCharCode(0x0063) );
this.TestCase( SECTION,   "String.fromCharCode(0x0064)",     "d",   String.fromCharCode(0x0064) );
this.TestCase( SECTION,   "String.fromCharCode(0x0065)",     "e",   String.fromCharCode(0x0065) );
this.TestCase( SECTION,   "String.fromCharCode(0x0066)",     "f",   String.fromCharCode(0x0066) );
this.TestCase( SECTION,   "String.fromCharCode(0x0067)",     "g",   String.fromCharCode(0x0067) );
this.TestCase( SECTION,   "String.fromCharCode(0x0068)",     "h",   String.fromCharCode(0x0068) );
this.TestCase( SECTION,   "String.fromCharCode(0x0069)",     "i",   String.fromCharCode(0x0069) );
this.TestCase( SECTION,   "String.fromCharCode(0x006A)",     "j",   String.fromCharCode(0x006A) );
this.TestCase( SECTION,   "String.fromCharCode(0x006B)",     "k",   String.fromCharCode(0x006B) );
this.TestCase( SECTION,   "String.fromCharCode(0x006C)",     "l",   String.fromCharCode(0x006C) );
this.TestCase( SECTION,   "String.fromCharCode(0x006D)",     "m",   String.fromCharCode(0x006D) );
this.TestCase( SECTION,   "String.fromCharCode(0x006E)",     "n",   String.fromCharCode(0x006E) );
this.TestCase( SECTION,   "String.fromCharCode(0x006F)",     "o",   String.fromCharCode(0x006F) );

this.TestCase( SECTION,   "String.fromCharCode(0x0070)",     "p",   String.fromCharCode(0x0070) );
this.TestCase( SECTION,   "String.fromCharCode(0x0071)",     "q",   String.fromCharCode(0x0071) );
this.TestCase( SECTION,   "String.fromCharCode(0x0072)",     "r",   String.fromCharCode(0x0072) );
this.TestCase( SECTION,   "String.fromCharCode(0x0073)",     "s",   String.fromCharCode(0x0073) );
this.TestCase( SECTION,   "String.fromCharCode(0x0074)",     "t",   String.fromCharCode(0x0074) );
this.TestCase( SECTION,   "String.fromCharCode(0x0075)",     "u",   String.fromCharCode(0x0075) );
this.TestCase( SECTION,   "String.fromCharCode(0x0076)",     "v",   String.fromCharCode(0x0076) );
this.TestCase( SECTION,   "String.fromCharCode(0x0077)",     "w",   String.fromCharCode(0x0077) );
this.TestCase( SECTION,   "String.fromCharCode(0x0078)",     "x",   String.fromCharCode(0x0078) );
this.TestCase( SECTION,   "String.fromCharCode(0x0079)",     "y",   String.fromCharCode(0x0079) );
this.TestCase( SECTION,   "String.fromCharCode(0x007A)",     "z",   String.fromCharCode(0x007A) );
this.TestCase( SECTION,   "String.fromCharCode(0x007B)",     "{",   String.fromCharCode(0x007B) );
this.TestCase( SECTION,   "String.fromCharCode(0x007C)",     "|",   String.fromCharCode(0x007C) );
this.TestCase( SECTION,   "String.fromCharCode(0x007D)",     "}",   String.fromCharCode(0x007D) );
this.TestCase( SECTION,   "String.fromCharCode(0x007E)",     "~",   String.fromCharCode(0x007E) );
//    new TestCase( SECTION,   "String.fromCharCode(0x0020, 0x007F)",     "",   String.fromCharCode(0x0040, 0x007F) );

//test();

},

/**
File Name:          15.5.3.2-2.js
ECMA Section:       15.5.3.2  String.fromCharCode( char0, char1, ... )
Description:        Return a string value containing as many characters
as the number of arguments.  Each argument specifies
one character of the resulting string, with the first
argument specifying the first character, and so on,
from left to right.  An argument is converted to a
character by applying the operation ToUint16 and
regarding the resulting 16bit integeras the Unicode
encoding of a character.  If no arguments are supplied,
the result is the empty string.

This tests String.fromCharCode with multiple arguments.

Author:             christine@netscape.com
Date:               2 october 1997
*/
test_15_5_3_2__2: function() {
var SECTION = "15.5.3.2-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.fromCharCode()";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"var MYSTRING = String.fromCharCode(eval(\"var args=''; for ( i = 0x0020; i < 0x007f; i++ ) { args += ( i == 0x007e ) ? i : i + ', '; } args;\")); MYSTRING",
" !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~",
eval( "var MYSTRING = String.fromCharCode(" + eval("var args=''; for ( i = 0x0020; i < 0x007f; i++ ) { args += ( i == 0x007e ) ? i : i + ', '; } args;") +"); MYSTRING" ));

this.TestCase( SECTION,
"MYSTRING.length",
0x007f - 0x0020,
TITLE.length );

//test();

},

/**
File Name:          15.5.3.2-1.js
ECMA Section:       15.5.3.2  String.fromCharCode( char0, char1, ... )
Description:        Return a string value containing as many characters
as the number of arguments.  Each argument specifies
one character of the resulting string, with the first
argument specifying the first character, and so on,
from left to right.  An argument is converted to a
character by applying the operation ToUint16 and
regarding the resulting 16bit integeras the Unicode
encoding of a character.  If no arguments are supplied,
the result is the empty string.

This test covers Basic Latin (range U+0020 - U+007F)

Author:             christine@netscape.com
Date:               2 october 1997
*/
test_15_5_3_2__3: function() {
var SECTION = "15.5.3.2-3";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.fromCharCode()";

//writeHeaderToLog( SECTION + " "+ TITLE);

for ( CHARCODE = 0; CHARCODE < 256; CHARCODE++ ) {
this.TestCase(   SECTION,
"(String.fromCharCode(" + CHARCODE +")).charCodeAt(0)",
ToUint16(CHARCODE),
(String.fromCharCode(CHARCODE)).charCodeAt(0)
);
}
for ( CHARCODE = 256; CHARCODE < 65536; CHARCODE+=333 ) {
this.TestCase(   SECTION,
"(String.fromCharCode(" + CHARCODE +")).charCodeAt(0)",
ToUint16(CHARCODE),
(String.fromCharCode(CHARCODE)).charCodeAt(0)
);
}
for ( CHARCODE = 65535; CHARCODE < 65538; CHARCODE++ ) {
this.TestCase(   SECTION,
"(String.fromCharCode(" + CHARCODE +")).charCodeAt(0)",
ToUint16(CHARCODE),
(String.fromCharCode(CHARCODE)).charCodeAt(0)
);
}
for ( CHARCODE = Math.pow(2,32)-1; CHARCODE < Math.pow(2,32)+1; CHARCODE++ ) {
this.TestCase(   SECTION,
"(String.fromCharCode(" + CHARCODE +")).charCodeAt(0)",
ToUint16(CHARCODE),
(String.fromCharCode(CHARCODE)).charCodeAt(0)
);
}
for ( CHARCODE = 0; CHARCODE > -65536; CHARCODE-=3333 ) {
this.TestCase(   SECTION,
"(String.fromCharCode(" + CHARCODE +")).charCodeAt(0)",
ToUint16(CHARCODE),
(String.fromCharCode(CHARCODE)).charCodeAt(0)
);
}
this.TestCase( SECTION, "(String.fromCharCode(65535)).charCodeAt(0)",    65535,  (String.fromCharCode(65535)).charCodeAt(0) );
this.TestCase( SECTION, "(String.fromCharCode(65536)).charCodeAt(0)",    0,      (String.fromCharCode(65536)).charCodeAt(0) );
this.TestCase( SECTION, "(String.fromCharCode(65537)).charCodeAt(0)",    1,      (String.fromCharCode(65537)).charCodeAt(0) );

//test();

function ToUint16( num ) {
num = Number( num );
if ( isNaN( num ) || num == 0 || num == Number.POSITIVE_INFINITY || num == Number.NEGATIVE_INFINITY ) {
return 0;
}

var sign = ( num < 0 ) ? -1 : 1;

num = sign * Math.floor( Math.abs( num ) );
num = num % Math.pow(2,16);
num = ( num > -65536 && num < 0) ? 65536 + num : num;
return num;
}

},

/**
File Name:          15.5.3.1.js
ECMA Section:       15.5.3 Properties of the String Constructor

Description:	    The value of the internal [[Prototype]] property of
the String constructor is the Function prototype
object.

In addition to the internal [[Call]] and [[Construct]]
properties, the String constructor also has the length
property, as well as properties described in 15.5.3.1
and 15.5.3.2.

Author:             christine@netscape.com
Date:               1 october 1997
*/
test_15_5_3: function() {
var SECTION = "15.5.3";
var VERSION = "ECMA_2";
//startTest();
var passed = true;
//writeHeaderToLog( SECTION + " Properties of the String Constructor" );

this.TestCase( SECTION,	"String.length", 1,VERSION.length );
},

/**
File Name:          15.5.4.1.js
ECMA Section:       15.5.4.1 String.prototype.constructor

Description:
Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_5_4__1: function() {
var SECTION = "15.5.4.1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.constructor";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION, "String.prototype.constructor == String",  true, String.prototype.constructor == String );

this.TestCase( SECTION, "var STRING = new String.prototype.constructor('hi'); STRING.getClass = Object.prototype.toString; STRING.getClass()",
"[object String]",
eval("var STRING = new String.prototype.constructor('hi'); STRING.getClass = Object.prototype.toString; STRING.getClass()") );

//test();

},

/**
File Name:          15.5.4.10-1.js
ECMA Section:       15.5.4.10 String.prototype.substring( start, end )
Description:

15.5.4.10 String.prototype.substring(start, end)

Returns a substring of the result of converting this object to a string,
starting from character position start and running to character position
end of the string. The result is a string value, not a String object.

If either argument is NaN or negative, it is replaced with zero; if either
argument is larger than the length of the string, it is replaced with the
length of the string.

If start is larger than end, they are swapped.

When the substring method is called with two arguments start and end, the
following steps are taken:

1.  Call ToString, giving it the this value as its argument.
2.  Call ToInteger(start).
3.  Call ToInteger (end).
4.  Compute the number of characters in Result(1).
5.  Compute min(max(Result(2), 0), Result(4)).
6.  Compute min(max(Result(3), 0), Result(4)).
7.  Compute min(Result(5), Result(6)).
8.  Compute max(Result(5), Result(6)).
9.  Return a string whose length is the difference between Result(8) and
Result(7), containing characters from Result(1), namely the characters
with indices Result(7) through Result(8)1, in ascending order.

Note that the substring function is intentionally generic; it does not require
that its this value be a String object. Therefore it can be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_5_4_10__1: function() {
var SECTION = "15.5.4.10-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.substring( start, end )";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,  "String.prototype.substring.length",        2,          String.prototype.substring.length );
this.TestCase( SECTION,  "delete String.prototype.substring.length", false,      delete String.prototype.substring.length );
this.TestCase( SECTION,  "delete String.prototype.substring.length; String.prototype.substring.length", 2,      eval("delete String.prototype.substring.length; String.prototype.substring.length") );

// test cases for when substring is called with no arguments.

// this is a string object

this.TestCase(   SECTION,
"var s = new String('this is a string object'); typeof s.substring()",
"string",
eval("var s = new String('this is a string object'); typeof s.substring()") );

this.TestCase(   SECTION,
"var s = new String(''); s.substring(1,0)",
"",
eval("var s = new String(''); s.substring(1,0)") );

this.TestCase(   SECTION,
"var s = new String('this is a string object'); s.substring(true, false)",
"t",
eval("var s = new String('this is a string object'); s.substring(false, true)") );

this.TestCase(   SECTION,
"var s = new String('this is a string object'); s.substring(NaN, Infinity)",
"this is a string object",
eval("var s = new String('this is a string object'); s.substring(NaN, Infinity)") );


this.TestCase(   SECTION,
"var s = new String('this is a string object'); s.substring(Infinity, NaN)",
"this is a string object",
eval("var s = new String('this is a string object'); s.substring(Infinity, NaN)") );


this.TestCase(   SECTION,
"var s = new String('this is a string object'); s.substring(Infinity, Infinity)",
"",
eval("var s = new String('this is a string object'); s.substring(Infinity, Infinity)") );

this.TestCase(   SECTION,
"var s = new String('this is a string object'); s.substring(-0.01, 0)",
"",
eval("var s = new String('this is a string object'); s.substring(-0.01,0)") );


this.TestCase(   SECTION,
"var s = new String('this is a string object'); s.substring(s.length, s.length)",
"",
eval("var s = new String('this is a string object'); s.substring(s.length, s.length)") );

this.TestCase(   SECTION,
"var s = new String('this is a string object'); s.substring(s.length+1, 0)",
"this is a string object",
eval("var s = new String('this is a string object'); s.substring(s.length+1, 0)") );


this.TestCase(   SECTION,
"var s = new String('this is a string object'); s.substring(-Infinity, -Infinity)",
"",
eval("var s = new String('this is a string object'); s.substring(-Infinity, -Infinity)") );

// this is not a String object, start is not an integer


this.TestCase(   SECTION,
"var s = new Array(1,2,3,4,5); s.substring = String.prototype.substring; s.substring(Infinity,-Infinity)",
"1,2,3,4,5",
eval("var s = new Array(1,2,3,4,5); s.substring = String.prototype.substring; s.substring(Infinity,-Infinity)") );

this.TestCase(   SECTION,
"var s = new Array(1,2,3,4,5); s.substring = String.prototype.substring; s.substring(true, false)",
"1",
eval("var s = new Array(1,2,3,4,5); s.substring = String.prototype.substring; s.substring(true, false)") );

this.TestCase(   SECTION,
"var s = new Array(1,2,3,4,5); s.substring = String.prototype.substring; s.substring('4', '5')",
"3",
eval("var s = new Array(1,2,3,4,5); s.substring = String.prototype.substring; s.substring('4', '5')") );


// this is an object object
this.TestCase(   SECTION,
"var obj = new Object(); obj.substring = String.prototype.substring; obj.substring(8,0)",
"[object ",
eval("var obj = new Object(); obj.substring = String.prototype.substring; obj.substring(8,0)") );

this.TestCase(   SECTION,
"var obj = new Object(); obj.substring = String.prototype.substring; obj.substring(8,obj.toString().length)",
"Object]",
eval("var obj = new Object(); obj.substring = String.prototype.substring; obj.substring(8, obj.toString().length)") );

// this is a function object
this.TestCase(   SECTION,
"var obj = new Function(); obj.substring = String.prototype.substring; obj.toString = Object.prototype.toString; obj.substring(8, Infinity)",
"Function]",
eval("var obj = new Function(); obj.substring = String.prototype.substring; obj.toString = Object.prototype.toString; obj.substring(8,Infinity)") );
// this is a number object
this.TestCase(   SECTION,
"var obj = new Number(NaN); obj.substring = String.prototype.substring; obj.substring(Infinity, NaN)",
"NaN",
eval("var obj = new Number(NaN); obj.substring = String.prototype.substring; obj.substring(Infinity, NaN)") );

// this is the Math object
this.TestCase(   SECTION,
"var obj = Math; obj.substring = String.prototype.substring; obj.substring(Math.PI, -10)",
"[ob",
eval("var obj = Math; obj.substring = String.prototype.substring; obj.substring(Math.PI, -10)") );

// this is a Boolean object

this.TestCase(   SECTION,
"var obj = new Boolean(); obj.substring = String.prototype.substring; obj.substring(new Array(), new Boolean(1))",
"f",
eval("var obj = new Boolean(); obj.substring = String.prototype.substring; obj.substring(new Array(), new Boolean(1))") );

// this is a user defined object

this.TestCase( SECTION,
"var obj = new MyObject( void 0 ); obj.substring(0, 100)",
"undefined",
eval( "var obj = new MyObject( void 0 ); obj.substring(0,100)") );

//test();

function MyObject( value ) {
this.value = value;
this.substring = String.prototype.substring;
this.toString = new Function ( "return this.value+''" );
}

},

/**
File Name:          15.5.4.11-1.js
ECMA Section:       15.5.4.11 String.prototype.toLowerCase()
Description:

Returns a string equal in length to the length of the result of converting
this object to a string. The result is a string value, not a String object.

Every character of the result is equal to the corresponding character of the
string, unless that character has a Unicode 2.0 uppercase equivalent, in which
case the uppercase equivalent is used instead. (The canonical Unicode 2.0 case
mapping shall be used, which does not depend on implementation or locale.)

Note that the toLowerCase function is intentionally generic; it does not require
that its this value be a String object. Therefore it can be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_5_4_11__1: function() {
var SECTION = "15.5.4.11-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.toLowerCase()";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,  "String.prototype.toLowerCase.length",        0,          String.prototype.toLowerCase.length );
this.TestCase( SECTION,  "delete String.prototype.toLowerCase.length", false,      delete String.prototype.toLowerCase.length );
this.TestCase( SECTION,  "delete String.prototype.toLowerCase.length; String.prototype.toLowerCase.length", 0,      eval("delete String.prototype.toLowerCase.length; String.prototype.toLowerCase.length") );

// Basic Latin, Latin-1 Supplement, Latin Extended A
for ( i = 0; i <= 0x017f; i++ ) {
var U = new Unicode(i);
/*
new TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toLowerCase()",
String.fromCharCode(U.lower),
eval("var s = new String( String.fromCharCode("+i+") ); s.toLowerCase()") );
*/
this.TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toLowerCase().charCodeAt(0)",
U.lower,
eval("var s = new String( String.fromCharCode(i) ); s.toLowerCase().charCodeAt(0)") );

}

//test();

function MyObject( value ) {
this.value = value;
this.substring = String.prototype.substring;
this.toString = new Function ( "return this.value+''" );
}
function Unicode( c ) {
var u = GetUnicodeValues( c );
this.upper = u[0];
this.lower = u[1]
return this;
}
function GetUnicodeValues( c ) {
var u = new Array();

u[0] = c;
u[1] = c;

// upper case Basic Latin

if ( c >= 0x0041 && c <= 0x005A) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Basic Latin
if ( c >= 0x0061 && c <= 0x007a ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// upper case Latin-1 Supplement
if ( (c >= 0x00C0 && c <= 0x00D6) || (c >= 0x00D8 && c<=0x00DE) ) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Latin-1 Supplement
if ( (c >= 0x00E0 && c <= 0x00F6) || (c >= 0x00F8 && c <= 0x00FE) ) {
u[0] = c - 32;
u[1] = c;
return u;
}
if ( c == 0x00FF ) {
u[0] = 0x0178;
u[1] = c;
return u;
}
// Latin Extended A
if ( (c >= 0x0100 && c < 0x0138) || (c > 0x0149 && c < 0x0178) ) {
// special case for capital I
if ( c == 0x0130 ) {
u[0] = c;
u[1] = 0x0069;
return u;
}
if ( c == 0x0131 ) {
u[0] = 0x0049;
u[1] = c;
return u;
}

if ( c % 2 == 0 ) {
// if it's even, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's odd, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x0178 ) {
u[0] = c;
u[1] = 0x00FF;
return u;
}

if ( (c >= 0x0139 && c < 0x0149) || (c > 0x0178 && c < 0x017F) ) {
if ( c % 2 == 1 ) {
// if it's odd, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's even, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x017F ) {
u[0] = 0x0053;
u[1] = c;
}

// Latin Extended B
// need to improve this set

if ( c >= 0x0200 && c <= 0x0217 ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c+1;
} else {
u[0] = c-1;
u[1] = c;
}
return u;
}

// Latin Extended Additional
// Range: U+1E00 to U+1EFF
// http://www.unicode.org/Unicode.charts/glyphless/U1E00.html

// Spacing Modifier Leters
// Range: U+02B0 to U+02FF

// Combining Diacritical Marks
// Range: U+0300 to U+036F

// skip Greek for now
// Greek
// Range: U+0370 to U+03FF

// Cyrillic
// Range: U+0400 to U+04FF

if ( (c >= 0x0401 && c <= 0x040C) || ( c>= 0x040E && c <= 0x040F ) ) {
u[0] = c;
u[1] = c + 80;
return u;
}


if ( c >= 0x0410  && c <= 0x042F ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0x0430 && c<= 0x044F ) {
u[0] = c - 32;
u[1] = c;
return u;

}
if ( (c >= 0x0451 && c <= 0x045C) || (c >=0x045E && c<= 0x045F) ) {
u[0] = c -80;
u[1] = c;
return u;
}

if ( c >= 0x0460 && c <= 0x047F ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c +1;
} else {
u[0] = c - 1;
u[1] = c;
}
return u;
}

// Armenian
// Range: U+0530 to U+058F
if ( c >= 0x0531 && c <= 0x0556 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x0561 && c < 0x0587 ) {
u[0] = c - 48;
u[1] = c;
return u;
}

// Hebrew
// Range: U+0590 to U+05FF


// Arabic
// Range: U+0600 to U+06FF

// Devanagari
// Range: U+0900 to U+097F


// Bengali
// Range: U+0980 to U+09FF


// Gurmukhi
// Range: U+0A00 to U+0A7F


// Gujarati
// Range: U+0A80 to U+0AFF


// Oriya
// Range: U+0B00 to U+0B7F
// no capital / lower case


// Tamil
// Range: U+0B80 to U+0BFF
// no capital / lower case


// Telugu
// Range: U+0C00 to U+0C7F
// no capital / lower case


// Kannada
// Range: U+0C80 to U+0CFF
// no capital / lower case


// Malayalam
// Range: U+0D00 to U+0D7F

// Thai
// Range: U+0E00 to U+0E7F


// Lao
// Range: U+0E80 to U+0EFF


// Tibetan
// Range: U+0F00 to U+0FBF

// Georgian
// Range: U+10A0 to U+10F0
if ( c >= 0x10A0 && c <= 0x10C5 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x10D0 && c <= 0x10F5 ) {
u[0] = c;
u[1] = c;
return u;
}

// Hangul Jamo
// Range: U+1100 to U+11FF

// Greek Extended
// Range: U+1F00 to U+1FFF
// skip for now


// General Punctuation
// Range: U+2000 to U+206F

// Superscripts and Subscripts
// Range: U+2070 to U+209F

// Currency Symbols
// Range: U+20A0 to U+20CF


// Combining Diacritical Marks for Symbols
// Range: U+20D0 to U+20FF
// skip for now


// Number Forms
// Range: U+2150 to U+218F
// skip for now


// Arrows
// Range: U+2190 to U+21FF

// Mathematical Operators
// Range: U+2200 to U+22FF

// Miscellaneous Technical
// Range: U+2300 to U+23FF

// Control Pictures
// Range: U+2400 to U+243F

// Optical Character Recognition
// Range: U+2440 to U+245F

// Enclosed Alphanumerics
// Range: U+2460 to U+24FF

// Box Drawing
// Range: U+2500 to U+257F

// Block Elements
// Range: U+2580 to U+259F

// Geometric Shapes
// Range: U+25A0 to U+25FF

// Miscellaneous Symbols
// Range: U+2600 to U+26FF

// Dingbats
// Range: U+2700 to U+27BF

// CJK Symbols and Punctuation
// Range: U+3000 to U+303F

// Hiragana
// Range: U+3040 to U+309F

// Katakana
// Range: U+30A0 to U+30FF

// Bopomofo
// Range: U+3100 to U+312F

// Hangul Compatibility Jamo
// Range: U+3130 to U+318F

// Kanbun
// Range: U+3190 to U+319F


// Enclosed CJK Letters and Months
// Range: U+3200 to U+32FF

// CJK Compatibility
// Range: U+3300 to U+33FF

// Hangul Syllables
// Range: U+AC00 to U+D7A3

// High Surrogates
// Range: U+D800 to U+DB7F

// Private Use High Surrogates
// Range: U+DB80 to U+DBFF

// Low Surrogates
// Range: U+DC00 to U+DFFF

// Private Use Area
// Range: U+E000 to U+F8FF

// CJK Compatibility Ideographs
// Range: U+F900 to U+FAFF

// Alphabetic Presentation Forms
// Range: U+FB00 to U+FB4F

// Arabic Presentation Forms-A
// Range: U+FB50 to U+FDFF

// Combining Half Marks
// Range: U+FE20 to U+FE2F

// CJK Compatibility Forms
// Range: U+FE30 to U+FE4F

// Small Form Variants
// Range: U+FE50 to U+FE6F

// Arabic Presentation Forms-B
// Range: U+FE70 to U+FEFF

// Halfwidth and Fullwidth Forms
// Range: U+FF00 to U+FFEF

if ( c >= 0xFF21 && c <= 0xFF3A ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0xFF41 && c <= 0xFF5A ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// Specials
// Range: U+FFF0 to U+FFFF

return u;
}

function DecimalToHexString( n ) {
n = Number( n );
var h = "0x";

for ( var i = 3; i >= 0; i-- ) {
if ( n >= Math.pow(16, i) ){
var t = Math.floor( n  / Math.pow(16, i));
n -= t * Math.pow(16, i);
if ( t >= 10 ) {
if ( t == 10 ) {
h += "A";
}
if ( t == 11 ) {
h += "B";
}
if ( t == 12 ) {
h += "C";
}
if ( t == 13 ) {
h += "D";
}
if ( t == 14 ) {
h += "E";
}
if ( t == 15 ) {
h += "F";
}
} else {
h += String( t );
}
} else {
h += "0";
}
}

return h;
}

},

/**
File Name:          15.5.4.11-2.js
ECMA Section:       15.5.4.11 String.prototype.toLowerCase()
Description:

Returns a string equal in length to the length of the result of converting
this object to a string. The result is a string value, not a String object.

Every character of the result is equal to the corresponding character of the
string, unless that character has a Unicode 2.0 uppercase equivalent, in which
case the uppercase equivalent is used instead. (The canonical Unicode 2.0 case
mapping shall be used, which does not depend on implementation or locale.)

Note that the toLowerCase function is intentionally generic; it does not require
that its this value be a String object. Therefore it can be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_5_4_11__2: function() {
var SECTION = "15.5.4.11-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.toLowerCase()";

//writeHeaderToLog( SECTION + " "+ TITLE);

// Georgian
// Range: U+10A0 to U+10FF
for ( var i = 0x10A0; i <= 0x10FF; i++ ) {
var U = new Unicode( i );

/*
new TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toLowerCase()",
String.fromCharCode(U.lower),
eval("var s = new String( String.fromCharCode("+i+") ); s.toLowerCase()") );
*/
this.TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toLowerCase().charCodeAt(0)",
U.lower,
eval("var s = new String( String.fromCharCode(i) ); s.toLowerCase().charCodeAt(0)") );
}

//test();

function MyObject( value ) {
this.value = value;
this.substring = String.prototype.substring;
this.toString = new Function ( "return this.value+''" );
}
function Unicode( c ) {
var u = GetUnicodeValues( c );
this.upper = u[0];
this.lower = u[1]
return this;
}
function GetUnicodeValues( c ) {
var u = new Array();

u[0] = c;
u[1] = c;

// upper case Basic Latin

if ( c >= 0x0041 && c <= 0x005A) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Basic Latin
if ( c >= 0x0061 && c <= 0x007a ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// upper case Latin-1 Supplement
if ( (c >= 0x00C0 && c <= 0x00D6) || (c >= 0x00D8 && c<=0x00DE) ) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Latin-1 Supplement
if ( (c >= 0x00E0 && c <= 0x00F6) || (c >= 0x00F8 && c <= 0x00FE) ) {
u[0] = c - 32;
u[1] = c;
return u;
}
if ( c == 0x00FF ) {
u[0] = 0x0178;
u[1] = c;
return u;
}
// Latin Extended A
if ( (c >= 0x0100 && c < 0x0138) || (c > 0x0149 && c < 0x0178) ) {
// special case for capital I
if ( c == 0x0130 ) {
u[0] = c;
u[1] = 0x0069;
return u;
}
if ( c == 0x0131 ) {
u[0] = 0x0049;
u[1] = c;
return u;
}

if ( c % 2 == 0 ) {
// if it's even, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's odd, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x0178 ) {
u[0] = c;
u[1] = 0x00FF;
return u;
}

if ( (c >= 0x0139 && c < 0x0149) || (c > 0x0178 && c < 0x017F) ) {
if ( c % 2 == 1 ) {
// if it's odd, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's even, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x017F ) {
u[0] = 0x0053;
u[1] = c;
}

// Latin Extended B
// need to improve this set

if ( c >= 0x0200 && c <= 0x0217 ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c+1;
} else {
u[0] = c-1;
u[1] = c;
}
return u;
}

// Latin Extended Additional
// Range: U+1E00 to U+1EFF
// http://www.unicode.org/Unicode.charts/glyphless/U1E00.html

// Spacing Modifier Leters
// Range: U+02B0 to U+02FF

// Combining Diacritical Marks
// Range: U+0300 to U+036F

// skip Greek for now
// Greek
// Range: U+0370 to U+03FF

// Cyrillic
// Range: U+0400 to U+04FF

if ( (c >= 0x0401 && c <= 0x040C) || ( c>= 0x040E && c <= 0x040F ) ) {
u[0] = c;
u[1] = c + 80;
return u;
}


if ( c >= 0x0410  && c <= 0x042F ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0x0430 && c<= 0x044F ) {
u[0] = c - 32;
u[1] = c;
return u;

}
if ( (c >= 0x0451 && c <= 0x045C) || (c >=0x045E && c<= 0x045F) ) {
u[0] = c -80;
u[1] = c;
return u;
}

if ( c >= 0x0460 && c <= 0x047F ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c +1;
} else {
u[0] = c - 1;
u[1] = c;
}
return u;
}

// Armenian
// Range: U+0530 to U+058F
if ( c >= 0x0531 && c <= 0x0556 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x0561 && c < 0x0587 ) {
u[0] = c - 48;
u[1] = c;
return u;
}

// Hebrew
// Range: U+0590 to U+05FF


// Arabic
// Range: U+0600 to U+06FF

// Devanagari
// Range: U+0900 to U+097F


// Bengali
// Range: U+0980 to U+09FF


// Gurmukhi
// Range: U+0A00 to U+0A7F


// Gujarati
// Range: U+0A80 to U+0AFF


// Oriya
// Range: U+0B00 to U+0B7F
// no capital / lower case


// Tamil
// Range: U+0B80 to U+0BFF
// no capital / lower case


// Telugu
// Range: U+0C00 to U+0C7F
// no capital / lower case


// Kannada
// Range: U+0C80 to U+0CFF
// no capital / lower case


// Malayalam
// Range: U+0D00 to U+0D7F

// Thai
// Range: U+0E00 to U+0E7F


// Lao
// Range: U+0E80 to U+0EFF


// Tibetan
// Range: U+0F00 to U+0FBF

// Georgian
// Range: U+10A0 to U+10F0
if ( c >= 0x10A0 && c <= 0x10C5 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x10D0 && c <= 0x10F5 ) {
u[0] = c;
u[1] = c;
return u;
}

// Hangul Jamo
// Range: U+1100 to U+11FF

// Greek Extended
// Range: U+1F00 to U+1FFF
// skip for now


// General Punctuation
// Range: U+2000 to U+206F

// Superscripts and Subscripts
// Range: U+2070 to U+209F

// Currency Symbols
// Range: U+20A0 to U+20CF


// Combining Diacritical Marks for Symbols
// Range: U+20D0 to U+20FF
// skip for now


// Number Forms
// Range: U+2150 to U+218F
// skip for now


// Arrows
// Range: U+2190 to U+21FF

// Mathematical Operators
// Range: U+2200 to U+22FF

// Miscellaneous Technical
// Range: U+2300 to U+23FF

// Control Pictures
// Range: U+2400 to U+243F

// Optical Character Recognition
// Range: U+2440 to U+245F

// Enclosed Alphanumerics
// Range: U+2460 to U+24FF

// Box Drawing
// Range: U+2500 to U+257F

// Block Elements
// Range: U+2580 to U+259F

// Geometric Shapes
// Range: U+25A0 to U+25FF

// Miscellaneous Symbols
// Range: U+2600 to U+26FF

// Dingbats
// Range: U+2700 to U+27BF

// CJK Symbols and Punctuation
// Range: U+3000 to U+303F

// Hiragana
// Range: U+3040 to U+309F

// Katakana
// Range: U+30A0 to U+30FF

// Bopomofo
// Range: U+3100 to U+312F

// Hangul Compatibility Jamo
// Range: U+3130 to U+318F

// Kanbun
// Range: U+3190 to U+319F


// Enclosed CJK Letters and Months
// Range: U+3200 to U+32FF

// CJK Compatibility
// Range: U+3300 to U+33FF

// Hangul Syllables
// Range: U+AC00 to U+D7A3

// High Surrogates
// Range: U+D800 to U+DB7F

// Private Use High Surrogates
// Range: U+DB80 to U+DBFF

// Low Surrogates
// Range: U+DC00 to U+DFFF

// Private Use Area
// Range: U+E000 to U+F8FF

// CJK Compatibility Ideographs
// Range: U+F900 to U+FAFF

// Alphabetic Presentation Forms
// Range: U+FB00 to U+FB4F

// Arabic Presentation Forms-A
// Range: U+FB50 to U+FDFF

// Combining Half Marks
// Range: U+FE20 to U+FE2F

// CJK Compatibility Forms
// Range: U+FE30 to U+FE4F

// Small Form Variants
// Range: U+FE50 to U+FE6F

// Arabic Presentation Forms-B
// Range: U+FE70 to U+FEFF

// Halfwidth and Fullwidth Forms
// Range: U+FF00 to U+FFEF

if ( c >= 0xFF21 && c <= 0xFF3A ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0xFF41 && c <= 0xFF5A ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// Specials
// Range: U+FFF0 to U+FFFF

return u;
}

function DecimalToHexString( n ) {
n = Number( n );
var h = "0x";

for ( var i = 3; i >= 0; i-- ) {
if ( n >= Math.pow(16, i) ){
var t = Math.floor( n  / Math.pow(16, i));
n -= t * Math.pow(16, i);
if ( t >= 10 ) {
if ( t == 10 ) {
h += "A";
}
if ( t == 11 ) {
h += "B";
}
if ( t == 12 ) {
h += "C";
}
if ( t == 13 ) {
h += "D";
}
if ( t == 14 ) {
h += "E";
}
if ( t == 15 ) {
h += "F";
}
} else {
h += String( t );
}
} else {
h += "0";
}
}

return h;
}

},

/**
File Name:          15.5.4.11-2.js
ECMA Section:       15.5.4.11 String.prototype.toLowerCase()
Description:

Returns a string equal in length to the length of the result of converting
this object to a string. The result is a string value, not a String object.

Every character of the result is equal to the corresponding character of the
string, unless that character has a Unicode 2.0 uppercase equivalent, in which
case the uppercase equivalent is used instead. (The canonical Unicode 2.0 case
mapping shall be used, which does not depend on implementation or locale.)

Note that the toLowerCase function is intentionally generic; it does not require
that its this value be a String object. Therefore it can be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_5_4_11__2_WORKS: function() {
var SECTION = "15.5.4.11-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.toLowerCase()";

//writeHeaderToLog( SECTION + " "+ TITLE);

// Georgian
// Range: U+10A0 to U+10FF
for ( var i = 0x10A0; i <= 0x10FF; i++ ) {
var U = new Unicode( i );

/*
new TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toLowerCase()",
String.fromCharCode(U.lower),
eval("var s = new String( String.fromCharCode("+i+") ); s.toLowerCase()") );
*/
this.TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toLowerCase().charCodeAt(0)",
U.lower,
eval("var s = new String( String.fromCharCode("+i+") ); s.toLowerCase().charCodeAt(0)") );
}

//test();

function MyObject( value ) {
this.value = value;
this.substring = String.prototype.substring;
this.toString = new Function ( "return this.value+''" );
}
function Unicode( c ) {
var u = GetUnicodeValues( c );
this.upper = u[0];
this.lower = u[1]
return this;
}
function GetUnicodeValues( c ) {
var u = new Array();

u[0] = c;
u[1] = c;

// upper case Basic Latin

if ( c >= 0x0041 && c <= 0x005A) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Basic Latin
if ( c >= 0x0061 && c <= 0x007a ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// upper case Latin-1 Supplement
if ( (c >= 0x00C0 && c <= 0x00D6) || (c >= 0x00D8 && c<=0x00DE) ) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Latin-1 Supplement
if ( (c >= 0x00E0 && c <= 0x00F6) || (c >= 0x00F8 && c <= 0x00FE) ) {
u[0] = c - 32;
u[1] = c;
return u;
}
if ( c == 0x00FF ) {
u[0] = 0x0178;
u[1] = c;
return u;
}
// Latin Extended A
if ( (c >= 0x0100 && c < 0x0138) || (c > 0x0149 && c < 0x0178) ) {
// special case for capital I
if ( c == 0x0130 ) {
u[0] = c;
u[1] = 0x0069;
return u;
}
if ( c == 0x0131 ) {
u[0] = 0x0049;
u[1] = c;
return u;
}

if ( c % 2 == 0 ) {
// if it's even, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's odd, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x0178 ) {
u[0] = c;
u[1] = 0x00FF;
return u;
}

if ( (c >= 0x0139 && c < 0x0149) || (c > 0x0178 && c < 0x017F) ) {
if ( c % 2 == 1 ) {
// if it's odd, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's even, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x017F ) {
u[0] = 0x0053;
u[1] = c;
}

// Latin Extended B
// need to improve this set

if ( c >= 0x0200 && c <= 0x0217 ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c+1;
} else {
u[0] = c-1;
u[1] = c;
}
return u;
}

// Latin Extended Additional
// Range: U+1E00 to U+1EFF
// http://www.unicode.org/Unicode.charts/glyphless/U1E00.html

// Spacing Modifier Leters
// Range: U+02B0 to U+02FF

// Combining Diacritical Marks
// Range: U+0300 to U+036F

// skip Greek for now
// Greek
// Range: U+0370 to U+03FF

// Cyrillic
// Range: U+0400 to U+04FF

if ( (c >= 0x0401 && c <= 0x040C) || ( c>= 0x040E && c <= 0x040F ) ) {
u[0] = c;
u[1] = c + 80;
return u;
}


if ( c >= 0x0410  && c <= 0x042F ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0x0430 && c<= 0x044F ) {
u[0] = c - 32;
u[1] = c;
return u;

}
if ( (c >= 0x0451 && c <= 0x045C) || (c >=0x045E && c<= 0x045F) ) {
u[0] = c -80;
u[1] = c;
return u;
}

if ( c >= 0x0460 && c <= 0x047F ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c +1;
} else {
u[0] = c - 1;
u[1] = c;
}
return u;
}

// Armenian
// Range: U+0530 to U+058F
if ( c >= 0x0531 && c <= 0x0556 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x0561 && c < 0x0587 ) {
u[0] = c - 48;
u[1] = c;
return u;
}

// Hebrew
// Range: U+0590 to U+05FF


// Arabic
// Range: U+0600 to U+06FF

// Devanagari
// Range: U+0900 to U+097F


// Bengali
// Range: U+0980 to U+09FF


// Gurmukhi
// Range: U+0A00 to U+0A7F


// Gujarati
// Range: U+0A80 to U+0AFF


// Oriya
// Range: U+0B00 to U+0B7F
// no capital / lower case


// Tamil
// Range: U+0B80 to U+0BFF
// no capital / lower case


// Telugu
// Range: U+0C00 to U+0C7F
// no capital / lower case


// Kannada
// Range: U+0C80 to U+0CFF
// no capital / lower case


// Malayalam
// Range: U+0D00 to U+0D7F

// Thai
// Range: U+0E00 to U+0E7F


// Lao
// Range: U+0E80 to U+0EFF


// Tibetan
// Range: U+0F00 to U+0FBF

// Georgian
// Range: U+10A0 to U+10F0
if ( c >= 0x10A0 && c <= 0x10C5 ) {
u[0] = c + 48;
u[1] = c;
return u;
}
if ( c >= 0x10D0 && c <= 0x10F5 ) {
u[0] = c;
u[1] = c;
return u;
}

// Hangul Jamo
// Range: U+1100 to U+11FF

// Greek Extended
// Range: U+1F00 to U+1FFF
// skip for now


// General Punctuation
// Range: U+2000 to U+206F

// Superscripts and Subscripts
// Range: U+2070 to U+209F

// Currency Symbols
// Range: U+20A0 to U+20CF


// Combining Diacritical Marks for Symbols
// Range: U+20D0 to U+20FF
// skip for now


// Number Forms
// Range: U+2150 to U+218F
// skip for now


// Arrows
// Range: U+2190 to U+21FF

// Mathematical Operators
// Range: U+2200 to U+22FF

// Miscellaneous Technical
// Range: U+2300 to U+23FF

// Control Pictures
// Range: U+2400 to U+243F

// Optical Character Recognition
// Range: U+2440 to U+245F

// Enclosed Alphanumerics
// Range: U+2460 to U+24FF

// Box Drawing
// Range: U+2500 to U+257F

// Block Elements
// Range: U+2580 to U+259F

// Geometric Shapes
// Range: U+25A0 to U+25FF

// Miscellaneous Symbols
// Range: U+2600 to U+26FF

// Dingbats
// Range: U+2700 to U+27BF

// CJK Symbols and Punctuation
// Range: U+3000 to U+303F

// Hiragana
// Range: U+3040 to U+309F

// Katakana
// Range: U+30A0 to U+30FF

// Bopomofo
// Range: U+3100 to U+312F

// Hangul Compatibility Jamo
// Range: U+3130 to U+318F

// Kanbun
// Range: U+3190 to U+319F


// Enclosed CJK Letters and Months
// Range: U+3200 to U+32FF

// CJK Compatibility
// Range: U+3300 to U+33FF

// Hangul Syllables
// Range: U+AC00 to U+D7A3

// High Surrogates
// Range: U+D800 to U+DB7F

// Private Use High Surrogates
// Range: U+DB80 to U+DBFF

// Low Surrogates
// Range: U+DC00 to U+DFFF

// Private Use Area
// Range: U+E000 to U+F8FF

// CJK Compatibility Ideographs
// Range: U+F900 to U+FAFF

// Alphabetic Presentation Forms
// Range: U+FB00 to U+FB4F

// Arabic Presentation Forms-A
// Range: U+FB50 to U+FDFF

// Combining Half Marks
// Range: U+FE20 to U+FE2F

// CJK Compatibility Forms
// Range: U+FE30 to U+FE4F

// Small Form Variants
// Range: U+FE50 to U+FE6F

// Arabic Presentation Forms-B
// Range: U+FE70 to U+FEFF

// Halfwidth and Fullwidth Forms
// Range: U+FF00 to U+FFEF

if ( c >= 0xFF21 && c <= 0xFF3A ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0xFF41 && c <= 0xFF5A ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// Specials
// Range: U+FFF0 to U+FFFF

return u;
}

function DecimalToHexString( n ) {
n = Number( n );
var h = "0x";

for ( var i = 3; i >= 0; i-- ) {
if ( n >= Math.pow(16, i) ){
var t = Math.floor( n  / Math.pow(16, i));
n -= t * Math.pow(16, i);
if ( t >= 10 ) {
if ( t == 10 ) {
h += "A";
}
if ( t == 11 ) {
h += "B";
}
if ( t == 12 ) {
h += "C";
}
if ( t == 13 ) {
h += "D";
}
if ( t == 14 ) {
h += "E";
}
if ( t == 15 ) {
h += "F";
}
} else {
h += String( t );
}
} else {
h += "0";
}
}

return h;
}

},

/**
File Name:          15.5.4.11-2.js
ECMA Section:       15.5.4.11 String.prototype.toLowerCase()
Description:

Returns a string equal in length to the length of the result of converting
this object to a string. The result is a string value, not a String object.

Every character of the result is equal to the corresponding character of the
string, unless that character has a Unicode 2.0 uppercase equivalent, in which
case the uppercase equivalent is used instead. (The canonical Unicode 2.0 case
mapping shall be used, which does not depend on implementation or locale.)

Note that the toLowerCase function is intentionally generic; it does not require
that its this value be a String object. Therefore it can be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_5_4_11__3: function() {
var SECTION = "15.5.4.11-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.toLowerCase()";

//writeHeaderToLog( SECTION + " "+ TITLE);

// Halfwidth and Fullwidth Forms
// Range: U+FF00 to U+FFEF
for ( var i = 0xFF00; i <= 0xFFEF; i++ ) {
var U = new Unicode(i);
/*
new TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toLowerCase()",
String.fromCharCode(U.lower),
eval("var s = new String( String.fromCharCode("+i+") ); s.toLowerCase()") );
*/
this.TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toLowerCase().charCodeAt(0)",
U.lower,
eval("var s = new String( String.fromCharCode(i) ); s.toLowerCase().charCodeAt(0)") );
}

//test();

function MyObject( value ) {
this.value = value;
this.substring = String.prototype.substring;
this.toString = new Function ( "return this.value+''" );
}
function Unicode( c ) {
var u = GetUnicodeValues( c );
this.upper = u[0];
this.lower = u[1]
return this;
}
function GetUnicodeValues( c ) {
var u = new Array();

u[0] = c;
u[1] = c;

// upper case Basic Latin

if ( c >= 0x0041 && c <= 0x005A) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Basic Latin
if ( c >= 0x0061 && c <= 0x007a ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// upper case Latin-1 Supplement
if ( (c >= 0x00C0 && c <= 0x00D6) || (c >= 0x00D8 && c<=0x00DE) ) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Latin-1 Supplement
if ( (c >= 0x00E0 && c <= 0x00F6) || (c >= 0x00F8 && c <= 0x00FE) ) {
u[0] = c - 32;
u[1] = c;
return u;
}
if ( c == 0x00FF ) {
u[0] = 0x0178;
u[1] = c;
return u;
}
// Latin Extended A
if ( (c >= 0x0100 && c < 0x0138) || (c > 0x0149 && c < 0x0178) ) {
// special case for capital I
if ( c == 0x0130 ) {
u[0] = c;
u[1] = 0x0069;
return u;
}
if ( c == 0x0131 ) {
u[0] = 0x0049;
u[1] = c;
return u;
}

if ( c % 2 == 0 ) {
// if it's even, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's odd, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x0178 ) {
u[0] = c;
u[1] = 0x00FF;
return u;
}

if ( (c >= 0x0139 && c < 0x0149) || (c > 0x0178 && c < 0x017F) ) {
if ( c % 2 == 1 ) {
// if it's odd, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's even, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x017F ) {
u[0] = 0x0053;
u[1] = c;
}

// Latin Extended B
// need to improve this set

if ( c >= 0x0200 && c <= 0x0217 ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c+1;
} else {
u[0] = c-1;
u[1] = c;
}
return u;
}

// Latin Extended Additional
// Range: U+1E00 to U+1EFF
// http://www.unicode.org/Unicode.charts/glyphless/U1E00.html

// Spacing Modifier Leters
// Range: U+02B0 to U+02FF

// Combining Diacritical Marks
// Range: U+0300 to U+036F

// skip Greek for now
// Greek
// Range: U+0370 to U+03FF

// Cyrillic
// Range: U+0400 to U+04FF

if ( (c >= 0x0401 && c <= 0x040C) || ( c>= 0x040E && c <= 0x040F ) ) {
u[0] = c;
u[1] = c + 80;
return u;
}


if ( c >= 0x0410  && c <= 0x042F ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0x0430 && c<= 0x044F ) {
u[0] = c - 32;
u[1] = c;
return u;

}
if ( (c >= 0x0451 && c <= 0x045C) || (c >=0x045E && c<= 0x045F) ) {
u[0] = c -80;
u[1] = c;
return u;
}

if ( c >= 0x0460 && c <= 0x047F ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c +1;
} else {
u[0] = c - 1;
u[1] = c;
}
return u;
}

// Armenian
// Range: U+0530 to U+058F
if ( c >= 0x0531 && c <= 0x0556 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x0561 && c < 0x0587 ) {
u[0] = c - 48;
u[1] = c;
return u;
}

// Hebrew
// Range: U+0590 to U+05FF


// Arabic
// Range: U+0600 to U+06FF

// Devanagari
// Range: U+0900 to U+097F


// Bengali
// Range: U+0980 to U+09FF


// Gurmukhi
// Range: U+0A00 to U+0A7F


// Gujarati
// Range: U+0A80 to U+0AFF


// Oriya
// Range: U+0B00 to U+0B7F
// no capital / lower case


// Tamil
// Range: U+0B80 to U+0BFF
// no capital / lower case


// Telugu
// Range: U+0C00 to U+0C7F
// no capital / lower case


// Kannada
// Range: U+0C80 to U+0CFF
// no capital / lower case


// Malayalam
// Range: U+0D00 to U+0D7F

// Thai
// Range: U+0E00 to U+0E7F


// Lao
// Range: U+0E80 to U+0EFF


// Tibetan
// Range: U+0F00 to U+0FBF

// Georgian
// Range: U+10A0 to U+10F0
if ( c >= 0x10A0 && c <= 0x10C5 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x10D0 && c <= 0x10F5 ) {
u[0] = c;
u[1] = c;
return u;
}

// Hangul Jamo
// Range: U+1100 to U+11FF

// Greek Extended
// Range: U+1F00 to U+1FFF
// skip for now


// General Punctuation
// Range: U+2000 to U+206F

// Superscripts and Subscripts
// Range: U+2070 to U+209F

// Currency Symbols
// Range: U+20A0 to U+20CF


// Combining Diacritical Marks for Symbols
// Range: U+20D0 to U+20FF
// skip for now


// Number Forms
// Range: U+2150 to U+218F
// skip for now


// Arrows
// Range: U+2190 to U+21FF

// Mathematical Operators
// Range: U+2200 to U+22FF

// Miscellaneous Technical
// Range: U+2300 to U+23FF

// Control Pictures
// Range: U+2400 to U+243F

// Optical Character Recognition
// Range: U+2440 to U+245F

// Enclosed Alphanumerics
// Range: U+2460 to U+24FF

// Box Drawing
// Range: U+2500 to U+257F

// Block Elements
// Range: U+2580 to U+259F

// Geometric Shapes
// Range: U+25A0 to U+25FF

// Miscellaneous Symbols
// Range: U+2600 to U+26FF

// Dingbats
// Range: U+2700 to U+27BF

// CJK Symbols and Punctuation
// Range: U+3000 to U+303F

// Hiragana
// Range: U+3040 to U+309F

// Katakana
// Range: U+30A0 to U+30FF

// Bopomofo
// Range: U+3100 to U+312F

// Hangul Compatibility Jamo
// Range: U+3130 to U+318F

// Kanbun
// Range: U+3190 to U+319F


// Enclosed CJK Letters and Months
// Range: U+3200 to U+32FF

// CJK Compatibility
// Range: U+3300 to U+33FF

// Hangul Syllables
// Range: U+AC00 to U+D7A3

// High Surrogates
// Range: U+D800 to U+DB7F

// Private Use High Surrogates
// Range: U+DB80 to U+DBFF

// Low Surrogates
// Range: U+DC00 to U+DFFF

// Private Use Area
// Range: U+E000 to U+F8FF

// CJK Compatibility Ideographs
// Range: U+F900 to U+FAFF

// Alphabetic Presentation Forms
// Range: U+FB00 to U+FB4F

// Arabic Presentation Forms-A
// Range: U+FB50 to U+FDFF

// Combining Half Marks
// Range: U+FE20 to U+FE2F

// CJK Compatibility Forms
// Range: U+FE30 to U+FE4F

// Small Form Variants
// Range: U+FE50 to U+FE6F

// Arabic Presentation Forms-B
// Range: U+FE70 to U+FEFF

// Halfwidth and Fullwidth Forms
// Range: U+FF00 to U+FFEF

if ( c >= 0xFF21 && c <= 0xFF3A ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0xFF41 && c <= 0xFF5A ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// Specials
// Range: U+FFF0 to U+FFFF

return u;
}

function DecimalToHexString( n ) {
n = Number( n );
var h = "0x";

for ( var i = 3; i >= 0; i-- ) {
if ( n >= Math.pow(16, i) ){
var t = Math.floor( n  / Math.pow(16, i));
n -= t * Math.pow(16, i);
if ( t >= 10 ) {
if ( t == 10 ) {
h += "A";
}
if ( t == 11 ) {
h += "B";
}
if ( t == 12 ) {
h += "C";
}
if ( t == 13 ) {
h += "D";
}
if ( t == 14 ) {
h += "E";
}
if ( t == 15 ) {
h += "F";
}
} else {
h += String( t );
}
} else {
h += "0";
}
}

return h;
}

},

/**
File Name:          15.5.4.11-2.js
ECMA Section:       15.5.4.11 String.prototype.toLowerCase()
Description:

Returns a string equal in length to the length of the result of converting
this object to a string. The result is a string value, not a String object.

Every character of the result is equal to the corresponding character of the
string, unless that character has a Unicode 2.0 uppercase equivalent, in which
case the uppercase equivalent is used instead. (The canonical Unicode 2.0 case
mapping shall be used, which does not depend on implementation or locale.)

Note that the toLowerCase function is intentionally generic; it does not require
that its this value be a String object. Therefore it can be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_5_4_11__3_WORKS: function() {
var SECTION = "15.5.4.11-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.toLowerCase()";

//writeHeaderToLog( SECTION + " "+ TITLE);

// Halfwidth and Fullwidth Forms
// Range: U+FF00 to U+FFEF
for ( var i = 0xFF00; i <= 0xFFEF; i++ ) {
var U = new Unicode(i);
/*
new TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toLowerCase()",
String.fromCharCode(U.lower),
eval("var s = new String( String.fromCharCode("+i+") ); s.toLowerCase()") );
*/
this.TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toLowerCase().charCodeAt(0)",
U.lower,
eval("var s = new String( String.fromCharCode("+i+") ); s.toLowerCase().charCodeAt(0)") );
}

//test();

function MyObject( value ) {
this.value = value;
this.substring = String.prototype.substring;
this.toString = new Function ( "return this.value+''" );
}
function Unicode( c ) {
var u = GetUnicodeValues( c );
this.upper = u[0];
this.lower = u[1]
return this;
}
function GetUnicodeValues( c ) {
var u = new Array();

u[0] = c;
u[1] = c;

// upper case Basic Latin

if ( c >= 0x0041 && c <= 0x005A) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Basic Latin
if ( c >= 0x0061 && c <= 0x007a ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// upper case Latin-1 Supplement
if ( (c >= 0x00C0 && c <= 0x00D6) || (c >= 0x00D8 && c<=0x00DE) ) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Latin-1 Supplement
if ( (c >= 0x00E0 && c <= 0x00F6) || (c >= 0x00F8 && c <= 0x00FE) ) {
u[0] = c - 32;
u[1] = c;
return u;
}
if ( c == 0x00FF ) {
u[0] = 0x0178;
u[1] = c;
return u;
}
// Latin Extended A
if ( (c >= 0x0100 && c < 0x0138) || (c > 0x0149 && c < 0x0178) ) {
// special case for capital I
if ( c == 0x0130 ) {
u[0] = c;
u[1] = 0x0069;
return u;
}
if ( c == 0x0131 ) {
u[0] = 0x0049;
u[1] = c;
return u;
}

if ( c % 2 == 0 ) {
// if it's even, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's odd, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x0178 ) {
u[0] = c;
u[1] = 0x00FF;
return u;
}

if ( (c >= 0x0139 && c < 0x0149) || (c > 0x0178 && c < 0x017F) ) {
if ( c % 2 == 1 ) {
// if it's odd, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's even, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x017F ) {
u[0] = 0x0053;
u[1] = c;
}

// Latin Extended B
// need to improve this set

if ( c >= 0x0200 && c <= 0x0217 ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c+1;
} else {
u[0] = c-1;
u[1] = c;
}
return u;
}

// Latin Extended Additional
// Range: U+1E00 to U+1EFF
// http://www.unicode.org/Unicode.charts/glyphless/U1E00.html

// Spacing Modifier Leters
// Range: U+02B0 to U+02FF

// Combining Diacritical Marks
// Range: U+0300 to U+036F

// skip Greek for now
// Greek
// Range: U+0370 to U+03FF

// Cyrillic
// Range: U+0400 to U+04FF

if ( (c >= 0x0401 && c <= 0x040C) || ( c>= 0x040E && c <= 0x040F ) ) {
u[0] = c;
u[1] = c + 80;
return u;
}


if ( c >= 0x0410  && c <= 0x042F ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0x0430 && c<= 0x044F ) {
u[0] = c - 32;
u[1] = c;
return u;

}
if ( (c >= 0x0451 && c <= 0x045C) || (c >=0x045E && c<= 0x045F) ) {
u[0] = c -80;
u[1] = c;
return u;
}

if ( c >= 0x0460 && c <= 0x047F ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c +1;
} else {
u[0] = c - 1;
u[1] = c;
}
return u;
}

// Armenian
// Range: U+0530 to U+058F
if ( c >= 0x0531 && c <= 0x0556 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x0561 && c < 0x0587 ) {
u[0] = c - 48;
u[1] = c;
return u;
}

// Hebrew
// Range: U+0590 to U+05FF


// Arabic
// Range: U+0600 to U+06FF

// Devanagari
// Range: U+0900 to U+097F


// Bengali
// Range: U+0980 to U+09FF


// Gurmukhi
// Range: U+0A00 to U+0A7F


// Gujarati
// Range: U+0A80 to U+0AFF


// Oriya
// Range: U+0B00 to U+0B7F
// no capital / lower case


// Tamil
// Range: U+0B80 to U+0BFF
// no capital / lower case


// Telugu
// Range: U+0C00 to U+0C7F
// no capital / lower case


// Kannada
// Range: U+0C80 to U+0CFF
// no capital / lower case


// Malayalam
// Range: U+0D00 to U+0D7F

// Thai
// Range: U+0E00 to U+0E7F


// Lao
// Range: U+0E80 to U+0EFF


// Tibetan
// Range: U+0F00 to U+0FBF

// Georgian
// Range: U+10A0 to U+10F0
if ( c >= 0x10A0 && c <= 0x10C5 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x10D0 && c <= 0x10F5 ) {
u[0] = c;
u[1] = c;
return u;
}

// Hangul Jamo
// Range: U+1100 to U+11FF

// Greek Extended
// Range: U+1F00 to U+1FFF
// skip for now


// General Punctuation
// Range: U+2000 to U+206F

// Superscripts and Subscripts
// Range: U+2070 to U+209F

// Currency Symbols
// Range: U+20A0 to U+20CF


// Combining Diacritical Marks for Symbols
// Range: U+20D0 to U+20FF
// skip for now


// Number Forms
// Range: U+2150 to U+218F
// skip for now


// Arrows
// Range: U+2190 to U+21FF

// Mathematical Operators
// Range: U+2200 to U+22FF

// Miscellaneous Technical
// Range: U+2300 to U+23FF

// Control Pictures
// Range: U+2400 to U+243F

// Optical Character Recognition
// Range: U+2440 to U+245F

// Enclosed Alphanumerics
// Range: U+2460 to U+24FF

// Box Drawing
// Range: U+2500 to U+257F

// Block Elements
// Range: U+2580 to U+259F

// Geometric Shapes
// Range: U+25A0 to U+25FF

// Miscellaneous Symbols
// Range: U+2600 to U+26FF

// Dingbats
// Range: U+2700 to U+27BF

// CJK Symbols and Punctuation
// Range: U+3000 to U+303F

// Hiragana
// Range: U+3040 to U+309F

// Katakana
// Range: U+30A0 to U+30FF

// Bopomofo
// Range: U+3100 to U+312F

// Hangul Compatibility Jamo
// Range: U+3130 to U+318F

// Kanbun
// Range: U+3190 to U+319F


// Enclosed CJK Letters and Months
// Range: U+3200 to U+32FF

// CJK Compatibility
// Range: U+3300 to U+33FF

// Hangul Syllables
// Range: U+AC00 to U+D7A3

// High Surrogates
// Range: U+D800 to U+DB7F

// Private Use High Surrogates
// Range: U+DB80 to U+DBFF

// Low Surrogates
// Range: U+DC00 to U+DFFF

// Private Use Area
// Range: U+E000 to U+F8FF

// CJK Compatibility Ideographs
// Range: U+F900 to U+FAFF

// Alphabetic Presentation Forms
// Range: U+FB00 to U+FB4F

// Arabic Presentation Forms-A
// Range: U+FB50 to U+FDFF

// Combining Half Marks
// Range: U+FE20 to U+FE2F

// CJK Compatibility Forms
// Range: U+FE30 to U+FE4F

// Small Form Variants
// Range: U+FE50 to U+FE6F

// Arabic Presentation Forms-B
// Range: U+FE70 to U+FEFF

// Halfwidth and Fullwidth Forms
// Range: U+FF00 to U+FFEF

if ( c >= 0xFF21 && c <= 0xFF3A ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0xFF41 && c <= 0xFF5A ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// Specials
// Range: U+FFF0 to U+FFFF

return u;
}

function DecimalToHexString( n ) {
n = Number( n );
var h = "0x";

for ( var i = 3; i >= 0; i-- ) {
if ( n >= Math.pow(16, i) ){
var t = Math.floor( n  / Math.pow(16, i));
n -= t * Math.pow(16, i);
if ( t >= 10 ) {
if ( t == 10 ) {
h += "A";
}
if ( t == 11 ) {
h += "B";
}
if ( t == 12 ) {
h += "C";
}
if ( t == 13 ) {
h += "D";
}
if ( t == 14 ) {
h += "E";
}
if ( t == 15 ) {
h += "F";
}
} else {
h += String( t );
}
} else {
h += "0";
}
}

return h;
}

},

/**
File Name:          15.5.4.11-2.js
ECMA Section:       15.5.4.11 String.prototype.toLowerCase()
Description:

Returns a string equal in length to the length of the result of converting
this object to a string. The result is a string value, not a String object.

Every character of the result is equal to the corresponding character of the
string, unless that character has a Unicode 2.0 uppercase equivalent, in which
case the uppercase equivalent is used instead. (The canonical Unicode 2.0 case
mapping shall be used, which does not depend on implementation or locale.)

Note that the toLowerCase function is intentionally generic; it does not require
that its this value be a String object. Therefore it can be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_5_4_11__4: function() {
var SECTION = "15.5.4.11-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.toLowerCase()";

//writeHeaderToLog( SECTION + " "+ TITLE);

// Hiragana (no upper / lower case)
// Range: U+3040 to U+309F

for ( var i = 0x3040; i <= 0x309F; i++ ) {
var U = new Unicode( i );
/*
new TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toLowerCase()",
String.fromCharCode(U.lower),
eval("var s = new String( String.fromCharCode("+i+") ); s.toLowerCase()") );
*/
this.TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toLowerCase().charCodeAt(0)",
U.lower,
eval("var s = new String( String.fromCharCode(i) ); s.toLowerCase().charCodeAt(0)") );
}

//test();

function MyObject( value ) {
this.value = value;
this.substring = String.prototype.substring;
this.toString = new Function ( "return this.value+''" );
}
function Unicode( c ) {
this.upper = c;
this.lower = c;

// upper case Basic Latin

if ( c >= 0x0041 && c <= 0x005A) {
this.upper = c;
this.lower = c + 32;
return this;
}

// lower case Basic Latin
if ( c >= 0x0061 && c <= 0x007a ) {
this.upper = c - 32;
this.lower = c;
return this;
}

// upper case Latin-1 Supplement
if ( (c >= 0x00C0 && c <= 0x00D6) || (c >= 0x00D8 && c<=0x00DE) ) {
this.upper = c;
this.lower = c + 32;
return this;
}

// lower case Latin-1 Supplement
if ( (c >= 0x00E0 && c <= 0x00F6) || (c >= 0x00F8 && c <= 0x00FE) ) {
this.upper = c - 32;
this.lower = c;
return this;
}
if ( c == 0x00FF ) {
this.upper = 0x0178;
this.lower = c;
return this;
}
// Latin Extended A
if ( (c >= 0x0100 && c < 0x0138) || (c > 0x0149 && c < 0x0178) ) {
// special case for capital I
if ( c == 0x0130 ) {
this.upper = c;
this.lower = 0x0069;
return this;
}
if ( c == 0x0131 ) {
this.upper = 0x0049;
this.lower = c;
return this;
}

if ( c % 2 == 0 ) {
// if it's even, it's a capital and the lower case is c +1
this.upper = c;
this.lower = c+1;
} else {
// if it's odd, it's a lower case and upper case is c-1
this.upper = c-1;
this.lower = c;
}
return this;
}
if ( c == 0x0178 ) {
this.upper = c;
this.lower = 0x00FF;
return this;
}

if ( (c >= 0x0139 && c < 0x0149) || (c > 0x0178 && c < 0x017F) ) {
if ( c % 2 == 1 ) {
// if it's odd, it's a capital and the lower case is c +1
this.upper = c;
this.lower = c+1;
} else {
// if it's even, it's a lower case and upper case is c-1
this.upper = c-1;
this.lower = c;
}
return this;
}
if ( c == 0x017F ) {
this.upper = 0x0053;
this.lower = c;
}

// Latin Extended B
// need to improve this set

if ( c >= 0x0200 && c <= 0x0217 ) {
if ( c % 2 == 0 ) {
this.upper = c;
this.lower = c+1;
} else {
this.upper = c-1;
this.lower = c;
}
return this;
}

// Latin Extended Additional
// Range: U+1E00 to U+1EFF
// http://www.unicode.org/Unicode.charts/glyphless/U1E00.html

// Spacing Modifier Leters
// Range: U+02B0 to U+02FF

// Combining Diacritical Marks
// Range: U+0300 to U+036F

// skip Greek for now
// Greek
// Range: U+0370 to U+03FF

// Cyrillic
// Range: U+0400 to U+04FF

if ( (c >= 0x0401 && c <= 0x040C) || ( c>= 0x040E && c <= 0x040F ) ) {
this.upper = c;
this.lower = c + 80;
return this;
}


if ( c >= 0x0410  && c <= 0x042F ) {
this.upper = c;
this.lower = c + 32;
return this;
}

if ( c >= 0x0430 && c<= 0x044F ) {
this.upper = c - 32;
this.lower = c;
return this;

}
if ( (c >= 0x0451 && c <= 0x045C) || (c >=0x045E && c<= 0x045F) ) {
this.upper = c -80;
this.lower = c;
return this;
}

if ( c >= 0x0460 && c <= 0x047F ) {
if ( c % 2 == 0 ) {
this.upper = c;
this.lower = c +1;
} else {
this.upper = c - 1;
this.lower = c;
}
return this;
}

// Armenian
// Range: U+0530 to U+058F
if ( c >= 0x0531 && c <= 0x0556 ) {
this.upper = c;
this.lower = c + 48;
return this;
}
if ( c >= 0x0561 && c < 0x0587 ) {
this.upper = c - 48;
this.lower = c;
return this;
}

// Hebrew
// Range: U+0590 to U+05FF


// Arabic
// Range: U+0600 to U+06FF

// Devanagari
// Range: U+0900 to U+097F


// Bengali
// Range: U+0980 to U+09FF


// Gurmukhi
// Range: U+0A00 to U+0A7F


// Gujarati
// Range: U+0A80 to U+0AFF


// Oriya
// Range: U+0B00 to U+0B7F
// no capital / lower case


// Tamil
// Range: U+0B80 to U+0BFF
// no capital / lower case


// Telugu
// Range: U+0C00 to U+0C7F
// no capital / lower case


// Kannada
// Range: U+0C80 to U+0CFF
// no capital / lower case


// Malayalam
// Range: U+0D00 to U+0D7F

// Thai
// Range: U+0E00 to U+0E7F


// Lao
// Range: U+0E80 to U+0EFF


// Tibetan
// Range: U+0F00 to U+0FBF

// Georgian
// Range: U+10A0 to U+10F0
if ( c >= 0x10A0 && c <= 0x10C5 ) {
this.upper = c;
this.lower = c + 48;
return this;
}
if ( c >= 0x10D0 && c <= 0x10F5 ) {
this.upper = c;
this.lower = c;
return this;
}

// Hangul Jamo
// Range: U+1100 to U+11FF

// Greek Extended
// Range: U+1F00 to U+1FFF
// skip for now


// General Punctuation
// Range: U+2000 to U+206F

// Superscripts and Subscripts
// Range: U+2070 to U+209F

// Currency Symbols
// Range: U+20A0 to U+20CF


// Combining Diacritical Marks for Symbols
// Range: U+20D0 to U+20FF
// skip for now


// Number Forms
// Range: U+2150 to U+218F
// skip for now


// Arrows
// Range: U+2190 to U+21FF

// Mathematical Operators
// Range: U+2200 to U+22FF

// Miscellaneous Technical
// Range: U+2300 to U+23FF

// Control Pictures
// Range: U+2400 to U+243F

// Optical Character Recognition
// Range: U+2440 to U+245F

// Enclosed Alphanumerics
// Range: U+2460 to U+24FF

// Box Drawing
// Range: U+2500 to U+257F

// Block Elements
// Range: U+2580 to U+259F

// Geometric Shapes
// Range: U+25A0 to U+25FF

// Miscellaneous Symbols
// Range: U+2600 to U+26FF

// Dingbats
// Range: U+2700 to U+27BF

// CJK Symbols and Punctuation
// Range: U+3000 to U+303F

// Hiragana
// Range: U+3040 to U+309F

// Katakana
// Range: U+30A0 to U+30FF

// Bopomofo
// Range: U+3100 to U+312F

// Hangul Compatibility Jamo
// Range: U+3130 to U+318F

// Kanbun
// Range: U+3190 to U+319F


// Enclosed CJK Letters and Months
// Range: U+3200 to U+32FF

// CJK Compatibility
// Range: U+3300 to U+33FF

// Hangul Syllables
// Range: U+AC00 to U+D7A3

// High Surrogates
// Range: U+D800 to U+DB7F

// Private Use High Surrogates
// Range: U+DB80 to U+DBFF

// Low Surrogates
// Range: U+DC00 to U+DFFF

// Private Use Area
// Range: U+E000 to U+F8FF

// CJK Compatibility Ideographs
// Range: U+F900 to U+FAFF

// Alphabetic Presentation Forms
// Range: U+FB00 to U+FB4F

// Arabic Presentation Forms-A
// Range: U+FB50 to U+FDFF

// Combining Half Marks
// Range: U+FE20 to U+FE2F

// CJK Compatibility Forms
// Range: U+FE30 to U+FE4F

// Small Form Variants
// Range: U+FE50 to U+FE6F

// Arabic Presentation Forms-B
// Range: U+FE70 to U+FEFF

// Halfwidth and Fullwidth Forms
// Range: U+FF00 to U+FFEF

if ( c >= 0xFF21 && c <= 0xFF3A ) {
this.upper = c;
this.lower = c + 32;
return this;
}

if ( c >= 0xFF41 && c <= 0xFF5A ) {
this.upper = c - 32;
this.lower = c;
return this;
}

// Specials
// Range: U+FFF0 to U+FFFF

return this;
}

function DecimalToHexString( n ) {
n = Number( n );
var h = "0x";

for ( var i = 3; i >= 0; i-- ) {
if ( n >= Math.pow(16, i) ){
var t = Math.floor( n  / Math.pow(16, i));
n -= t * Math.pow(16, i);
if ( t >= 10 ) {
if ( t == 10 ) {
h += "A";
}
if ( t == 11 ) {
h += "B";
}
if ( t == 12 ) {
h += "C";
}
if ( t == 13 ) {
h += "D";
}
if ( t == 14 ) {
h += "E";
}
if ( t == 15 ) {
h += "F";
}
} else {
h += String( t );
}
} else {
h += "0";
}
}

return h;
}

},

/**
File Name:          15.5.4.11-2.js
ECMA Section:       15.5.4.11 String.prototype.toLowerCase()
Description:

Returns a string equal in length to the length of the result of converting
this object to a string. The result is a string value, not a String object.

Every character of the result is equal to the corresponding character of the
string, unless that character has a Unicode 2.0 uppercase equivalent, in which
case the uppercase equivalent is used instead. (The canonical Unicode 2.0 case
mapping shall be used, which does not depend on implementation or locale.)

Note that the toLowerCase function is intentionally generic; it does not require
that its this value be a String object. Therefore it can be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_5_4_11__4_WORKS: function() {
var SECTION = "15.5.4.11-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.toLowerCase()";

//writeHeaderToLog( SECTION + " "+ TITLE);

// Hiragana (no upper / lower case)
// Range: U+3040 to U+309F

for ( var i = 0x3040; i <= 0x309F; i++ ) {
var U = new Unicode( i );
/*
new TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toLowerCase()",
String.fromCharCode(U.lower),
eval("var s = new String( String.fromCharCode("+i+") ); s.toLowerCase()") );
*/
this.TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toLowerCase().charCodeAt(0)",
U.lower,
eval("var s = new String( String.fromCharCode("+i+") ); s.toLowerCase().charCodeAt(0)") );
}

//test();

function MyObject( value ) {
this.value = value;
this.substring = String.prototype.substring;
this.toString = new Function ( "return this.value+''" );
}
function Unicode( c ) {
this.upper = c;
this.lower = c;

// upper case Basic Latin

if ( c >= 0x0041 && c <= 0x005A) {
this.upper = c;
this.lower = c + 32;
return this;
}

// lower case Basic Latin
if ( c >= 0x0061 && c <= 0x007a ) {
this.upper = c - 32;
this.lower = c;
return this;
}

// upper case Latin-1 Supplement
if ( (c >= 0x00C0 && c <= 0x00D6) || (c >= 0x00D8 && c<=0x00DE) ) {
this.upper = c;
this.lower = c + 32;
return this;
}

// lower case Latin-1 Supplement
if ( (c >= 0x00E0 && c <= 0x00F6) || (c >= 0x00F8 && c <= 0x00FE) ) {
this.upper = c - 32;
this.lower = c;
return this;
}
if ( c == 0x00FF ) {
this.upper = 0x0178;
this.lower = c;
return this;
}
// Latin Extended A
if ( (c >= 0x0100 && c < 0x0138) || (c > 0x0149 && c < 0x0178) ) {
// special case for capital I
if ( c == 0x0130 ) {
this.upper = c;
this.lower = 0x0069;
return this;
}
if ( c == 0x0131 ) {
this.upper = 0x0049;
this.lower = c;
return this;
}

if ( c % 2 == 0 ) {
// if it's even, it's a capital and the lower case is c +1
this.upper = c;
this.lower = c+1;
} else {
// if it's odd, it's a lower case and upper case is c-1
this.upper = c-1;
this.lower = c;
}
return this;
}
if ( c == 0x0178 ) {
this.upper = c;
this.lower = 0x00FF;
return this;
}

if ( (c >= 0x0139 && c < 0x0149) || (c > 0x0178 && c < 0x017F) ) {
if ( c % 2 == 1 ) {
// if it's odd, it's a capital and the lower case is c +1
this.upper = c;
this.lower = c+1;
} else {
// if it's even, it's a lower case and upper case is c-1
this.upper = c-1;
this.lower = c;
}
return this;
}
if ( c == 0x017F ) {
this.upper = 0x0053;
this.lower = c;
}

// Latin Extended B
// need to improve this set

if ( c >= 0x0200 && c <= 0x0217 ) {
if ( c % 2 == 0 ) {
this.upper = c;
this.lower = c+1;
} else {
this.upper = c-1;
this.lower = c;
}
return this;
}

// Latin Extended Additional
// Range: U+1E00 to U+1EFF
// http://www.unicode.org/Unicode.charts/glyphless/U1E00.html

// Spacing Modifier Leters
// Range: U+02B0 to U+02FF

// Combining Diacritical Marks
// Range: U+0300 to U+036F

// skip Greek for now
// Greek
// Range: U+0370 to U+03FF

// Cyrillic
// Range: U+0400 to U+04FF

if ( (c >= 0x0401 && c <= 0x040C) || ( c>= 0x040E && c <= 0x040F ) ) {
this.upper = c;
this.lower = c + 80;
return this;
}


if ( c >= 0x0410  && c <= 0x042F ) {
this.upper = c;
this.lower = c + 32;
return this;
}

if ( c >= 0x0430 && c<= 0x044F ) {
this.upper = c - 32;
this.lower = c;
return this;

}
if ( (c >= 0x0451 && c <= 0x045C) || (c >=0x045E && c<= 0x045F) ) {
this.upper = c -80;
this.lower = c;
return this;
}

if ( c >= 0x0460 && c <= 0x047F ) {
if ( c % 2 == 0 ) {
this.upper = c;
this.lower = c +1;
} else {
this.upper = c - 1;
this.lower = c;
}
return this;
}

// Armenian
// Range: U+0530 to U+058F
if ( c >= 0x0531 && c <= 0x0556 ) {
this.upper = c;
this.lower = c + 48;
return this;
}
if ( c >= 0x0561 && c < 0x0587 ) {
this.upper = c - 48;
this.lower = c;
return this;
}

// Hebrew
// Range: U+0590 to U+05FF


// Arabic
// Range: U+0600 to U+06FF

// Devanagari
// Range: U+0900 to U+097F


// Bengali
// Range: U+0980 to U+09FF


// Gurmukhi
// Range: U+0A00 to U+0A7F


// Gujarati
// Range: U+0A80 to U+0AFF


// Oriya
// Range: U+0B00 to U+0B7F
// no capital / lower case


// Tamil
// Range: U+0B80 to U+0BFF
// no capital / lower case


// Telugu
// Range: U+0C00 to U+0C7F
// no capital / lower case


// Kannada
// Range: U+0C80 to U+0CFF
// no capital / lower case


// Malayalam
// Range: U+0D00 to U+0D7F

// Thai
// Range: U+0E00 to U+0E7F


// Lao
// Range: U+0E80 to U+0EFF


// Tibetan
// Range: U+0F00 to U+0FBF

// Georgian
// Range: U+10A0 to U+10F0
if ( c >= 0x10A0 && c <= 0x10C5 ) {
this.upper = c;
this.lower = c + 48;
return this;
}
if ( c >= 0x10D0 && c <= 0x10F5 ) {
this.upper = c;
this.lower = c;
return this;
}

// Hangul Jamo
// Range: U+1100 to U+11FF

// Greek Extended
// Range: U+1F00 to U+1FFF
// skip for now


// General Punctuation
// Range: U+2000 to U+206F

// Superscripts and Subscripts
// Range: U+2070 to U+209F

// Currency Symbols
// Range: U+20A0 to U+20CF


// Combining Diacritical Marks for Symbols
// Range: U+20D0 to U+20FF
// skip for now


// Number Forms
// Range: U+2150 to U+218F
// skip for now


// Arrows
// Range: U+2190 to U+21FF

// Mathematical Operators
// Range: U+2200 to U+22FF

// Miscellaneous Technical
// Range: U+2300 to U+23FF

// Control Pictures
// Range: U+2400 to U+243F

// Optical Character Recognition
// Range: U+2440 to U+245F

// Enclosed Alphanumerics
// Range: U+2460 to U+24FF

// Box Drawing
// Range: U+2500 to U+257F

// Block Elements
// Range: U+2580 to U+259F

// Geometric Shapes
// Range: U+25A0 to U+25FF

// Miscellaneous Symbols
// Range: U+2600 to U+26FF

// Dingbats
// Range: U+2700 to U+27BF

// CJK Symbols and Punctuation
// Range: U+3000 to U+303F

// Hiragana
// Range: U+3040 to U+309F

// Katakana
// Range: U+30A0 to U+30FF

// Bopomofo
// Range: U+3100 to U+312F

// Hangul Compatibility Jamo
// Range: U+3130 to U+318F

// Kanbun
// Range: U+3190 to U+319F


// Enclosed CJK Letters and Months
// Range: U+3200 to U+32FF

// CJK Compatibility
// Range: U+3300 to U+33FF

// Hangul Syllables
// Range: U+AC00 to U+D7A3

// High Surrogates
// Range: U+D800 to U+DB7F

// Private Use High Surrogates
// Range: U+DB80 to U+DBFF

// Low Surrogates
// Range: U+DC00 to U+DFFF

// Private Use Area
// Range: U+E000 to U+F8FF

// CJK Compatibility Ideographs
// Range: U+F900 to U+FAFF

// Alphabetic Presentation Forms
// Range: U+FB00 to U+FB4F

// Arabic Presentation Forms-A
// Range: U+FB50 to U+FDFF

// Combining Half Marks
// Range: U+FE20 to U+FE2F

// CJK Compatibility Forms
// Range: U+FE30 to U+FE4F

// Small Form Variants
// Range: U+FE50 to U+FE6F

// Arabic Presentation Forms-B
// Range: U+FE70 to U+FEFF

// Halfwidth and Fullwidth Forms
// Range: U+FF00 to U+FFEF

if ( c >= 0xFF21 && c <= 0xFF3A ) {
this.upper = c;
this.lower = c + 32;
return this;
}

if ( c >= 0xFF41 && c <= 0xFF5A ) {
this.upper = c - 32;
this.lower = c;
return this;
}

// Specials
// Range: U+FFF0 to U+FFFF

return this;
}

function DecimalToHexString( n ) {
n = Number( n );
var h = "0x";

for ( var i = 3; i >= 0; i-- ) {
if ( n >= Math.pow(16, i) ){
var t = Math.floor( n  / Math.pow(16, i));
n -= t * Math.pow(16, i);
if ( t >= 10 ) {
if ( t == 10 ) {
h += "A";
}
if ( t == 11 ) {
h += "B";
}
if ( t == 12 ) {
h += "C";
}
if ( t == 13 ) {
h += "D";
}
if ( t == 14 ) {
h += "E";
}
if ( t == 15 ) {
h += "F";
}
} else {
h += String( t );
}
} else {
h += "0";
}
}

return h;
}

},

/**
File Name:          15.5.4.11-5.js
ECMA Section:       15.5.4.11 String.prototype.toLowerCase()
Description:

Returns a string equal in length to the length of the result of converting
this object to a string. The result is a string value, not a String object.

Every character of the result is equal to the corresponding character of the
string, unless that character has a Unicode 2.0 uppercase equivalent, in which
case the uppercase equivalent is used instead. (The canonical Unicode 2.0 case
mapping shall be used, which does not depend on implementation or locale.)

Note that the toLowerCase function is intentionally generic; it does not require
that its this value be a String object. Therefore it can be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_5_4_11__5: function() {
var SECTION = "15.5.4.11-5";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.toLowerCase()";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,  "String.prototype.toLowerCase.length",        0,          String.prototype.toLowerCase.length );

this.TestCase( SECTION,  "delete String.prototype.toLowerCase.length", false,      delete String.prototype.toLowerCase.length );

this.TestCase( SECTION,  "delete String.prototype.toLowerCase.length; String.prototype.toLowerCase.length", 0,      eval("delete String.prototype.toLowerCase.length; String.prototype.toLowerCase.length") );

// Cyrillic (part)
// Range: U+0400 to U+04FF
for ( var i = 0x0400; i <= 0x047F; i++ ) {
var U = new Unicode( i );
/*
new TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toLowerCase()",
String.fromCharCode(U.lower),
eval("var s = new String( String.fromCharCode("+i+") ); s.toLowerCase()") );
*/
this.TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toLowerCase().charCodeAt(0)",
U.lower,
eval("var s = new String( String.fromCharCode(i) ); s.toLowerCase().charCodeAt(0)") );
}

//test();

function MyObject( value ) {
this.value = value;
this.substring = String.prototype.substring;
this.toString = new Function ( "return this.value+''" );
}
function Unicode( c ) {
var u = GetUnicodeValues( c );
this.upper = u[0];
this.lower = u[1]
return this;
}
function GetUnicodeValues( c ) {
var u = new Array();

u[0] = c;
u[1] = c;

// upper case Basic Latin

if ( c >= 0x0041 && c <= 0x005A) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Basic Latin
if ( c >= 0x0061 && c <= 0x007a ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// upper case Latin-1 Supplement
if ( (c >= 0x00C0 && c <= 0x00D6) || (c >= 0x00D8 && c<=0x00DE) ) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Latin-1 Supplement
if ( (c >= 0x00E0 && c <= 0x00F6) || (c >= 0x00F8 && c <= 0x00FE) ) {
u[0] = c - 32;
u[1] = c;
return u;
}
if ( c == 0x00FF ) {
u[0] = 0x0178;
u[1] = c;
return u;
}
// Latin Extended A
if ( (c >= 0x0100 && c < 0x0138) || (c > 0x0149 && c < 0x0178) ) {
// special case for capital I
if ( c == 0x0130 ) {
u[0] = c;
u[1] = 0x0069;
return u;
}
if ( c == 0x0131 ) {
u[0] = 0x0049;
u[1] = c;
return u;
}

if ( c % 2 == 0 ) {
// if it's even, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's odd, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x0178 ) {
u[0] = c;
u[1] = 0x00FF;
return u;
}

if ( (c >= 0x0139 && c < 0x0149) || (c > 0x0178 && c < 0x017F) ) {
if ( c % 2 == 1 ) {
// if it's odd, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's even, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x017F ) {
u[0] = 0x0053;
u[1] = c;
}

// Latin Extended B
// need to improve this set

if ( c >= 0x0200 && c <= 0x0217 ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c+1;
} else {
u[0] = c-1;
u[1] = c;
}
return u;
}

// Latin Extended Additional
// Range: U+1E00 to U+1EFF
// http://www.unicode.org/Unicode.charts/glyphless/U1E00.html

// Spacing Modifier Leters
// Range: U+02B0 to U+02FF

// Combining Diacritical Marks
// Range: U+0300 to U+036F

// skip Greek for now
// Greek
// Range: U+0370 to U+03FF

// Cyrillic
// Range: U+0400 to U+04FF

if ( (c >= 0x0401 && c <= 0x040C) || ( c>= 0x040E && c <= 0x040F ) ) {
u[0] = c;
u[1] = c + 80;
return u;
}


if ( c >= 0x0410  && c <= 0x042F ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0x0430 && c<= 0x044F ) {
u[0] = c - 32;
u[1] = c;
return u;

}
if ( (c >= 0x0451 && c <= 0x045C) || (c >=0x045E && c<= 0x045F) ) {
u[0] = c -80;
u[1] = c;
return u;
}

if ( c >= 0x0460 && c <= 0x047F ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c +1;
} else {
u[0] = c - 1;
u[1] = c;
}
return u;
}

// Armenian
// Range: U+0530 to U+058F
if ( c >= 0x0531 && c <= 0x0556 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x0561 && c < 0x0587 ) {
u[0] = c - 48;
u[1] = c;
return u;
}

// Hebrew
// Range: U+0590 to U+05FF


// Arabic
// Range: U+0600 to U+06FF

// Devanagari
// Range: U+0900 to U+097F


// Bengali
// Range: U+0980 to U+09FF


// Gurmukhi
// Range: U+0A00 to U+0A7F


// Gujarati
// Range: U+0A80 to U+0AFF


// Oriya
// Range: U+0B00 to U+0B7F
// no capital / lower case


// Tamil
// Range: U+0B80 to U+0BFF
// no capital / lower case


// Telugu
// Range: U+0C00 to U+0C7F
// no capital / lower case


// Kannada
// Range: U+0C80 to U+0CFF
// no capital / lower case


// Malayalam
// Range: U+0D00 to U+0D7F

// Thai
// Range: U+0E00 to U+0E7F


// Lao
// Range: U+0E80 to U+0EFF


// Tibetan
// Range: U+0F00 to U+0FBF

// Georgian
// Range: U+10A0 to U+10F0
if ( c >= 0x10A0 && c <= 0x10C5 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x10D0 && c <= 0x10F5 ) {
u[0] = c;
u[1] = c;
return u;
}

// Hangul Jamo
// Range: U+1100 to U+11FF

// Greek Extended
// Range: U+1F00 to U+1FFF
// skip for now


// General Punctuation
// Range: U+2000 to U+206F

// Superscripts and Subscripts
// Range: U+2070 to U+209F

// Currency Symbols
// Range: U+20A0 to U+20CF


// Combining Diacritical Marks for Symbols
// Range: U+20D0 to U+20FF
// skip for now


// Number Forms
// Range: U+2150 to U+218F
// skip for now


// Arrows
// Range: U+2190 to U+21FF

// Mathematical Operators
// Range: U+2200 to U+22FF

// Miscellaneous Technical
// Range: U+2300 to U+23FF

// Control Pictures
// Range: U+2400 to U+243F

// Optical Character Recognition
// Range: U+2440 to U+245F

// Enclosed Alphanumerics
// Range: U+2460 to U+24FF

// Box Drawing
// Range: U+2500 to U+257F

// Block Elements
// Range: U+2580 to U+259F

// Geometric Shapes
// Range: U+25A0 to U+25FF

// Miscellaneous Symbols
// Range: U+2600 to U+26FF

// Dingbats
// Range: U+2700 to U+27BF

// CJK Symbols and Punctuation
// Range: U+3000 to U+303F

// Hiragana
// Range: U+3040 to U+309F

// Katakana
// Range: U+30A0 to U+30FF

// Bopomofo
// Range: U+3100 to U+312F

// Hangul Compatibility Jamo
// Range: U+3130 to U+318F

// Kanbun
// Range: U+3190 to U+319F


// Enclosed CJK Letters and Months
// Range: U+3200 to U+32FF

// CJK Compatibility
// Range: U+3300 to U+33FF

// Hangul Syllables
// Range: U+AC00 to U+D7A3

// High Surrogates
// Range: U+D800 to U+DB7F

// Private Use High Surrogates
// Range: U+DB80 to U+DBFF

// Low Surrogates
// Range: U+DC00 to U+DFFF

// Private Use Area
// Range: U+E000 to U+F8FF

// CJK Compatibility Ideographs
// Range: U+F900 to U+FAFF

// Alphabetic Presentation Forms
// Range: U+FB00 to U+FB4F

// Arabic Presentation Forms-A
// Range: U+FB50 to U+FDFF

// Combining Half Marks
// Range: U+FE20 to U+FE2F

// CJK Compatibility Forms
// Range: U+FE30 to U+FE4F

// Small Form Variants
// Range: U+FE50 to U+FE6F

// Arabic Presentation Forms-B
// Range: U+FE70 to U+FEFF

// Halfwidth and Fullwidth Forms
// Range: U+FF00 to U+FFEF

if ( c >= 0xFF21 && c <= 0xFF3A ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0xFF41 && c <= 0xFF5A ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// Specials
// Range: U+FFF0 to U+FFFF

return u;
}

function DecimalToHexString( n ) {
n = Number( n );
var h = "0x";

for ( var i = 3; i >= 0; i-- ) {
if ( n >= Math.pow(16, i) ){
var t = Math.floor( n  / Math.pow(16, i));
n -= t * Math.pow(16, i);
if ( t >= 10 ) {
if ( t == 10 ) {
h += "A";
}
if ( t == 11 ) {
h += "B";
}
if ( t == 12 ) {
h += "C";
}
if ( t == 13 ) {
h += "D";
}
if ( t == 14 ) {
h += "E";
}
if ( t == 15 ) {
h += "F";
}
} else {
h += String( t );
}
} else {
h += "0";
}
}

return h;
}

},

/**
File Name:          15.5.4.11-5.js
ECMA Section:       15.5.4.11 String.prototype.toLowerCase()
Description:

Returns a string equal in length to the length of the result of converting
this object to a string. The result is a string value, not a String object.

Every character of the result is equal to the corresponding character of the
string, unless that character has a Unicode 2.0 uppercase equivalent, in which
case the uppercase equivalent is used instead. (The canonical Unicode 2.0 case
mapping shall be used, which does not depend on implementation or locale.)

Note that the toLowerCase function is intentionally generic; it does not require
that its this value be a String object. Therefore it can be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_5_4_11__5_WORKS: function() {
var SECTION = "15.5.4.11-5";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.toLowerCase()";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,  "String.prototype.toLowerCase.length",        0,          String.prototype.toLowerCase.length );

this.TestCase( SECTION,  "delete String.prototype.toLowerCase.length", false,      delete String.prototype.toLowerCase.length );

this.TestCase( SECTION,  "delete String.prototype.toLowerCase.length; String.prototype.toLowerCase.length", 0,      eval("delete String.prototype.toLowerCase.length; String.prototype.toLowerCase.length") );

// Cyrillic (part)
// Range: U+0400 to U+04FF
//      for ( var i = 0x0400; i <= 0x047F; i++ ) {
for ( var i = 0x0400; i <= 0x047F; i++ ) {
var U = new Unicode( i );
/*
new TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toLowerCase()",
String.fromCharCode(U.lower),
eval("var s = new String( String.fromCharCode("+i+") ); s.toLowerCase()") );
*/
this.TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toLowerCase().charCodeAt(0)",
U.lower,
eval("var s = new String( String.fromCharCode("+i+") ); s.toLowerCase().charCodeAt(0)") );
}

//test();

function MyObject( value ) {
this.value = value;
this.substring = String.prototype.substring;
this.toString = new Function ( "return this.value+''" );
}
function Unicode( c ) {
var u = GetUnicodeValues( c );
this.upper = u[0];
this.lower = u[1]
return this;
}
function GetUnicodeValues( c ) {
var u = new Array();

u[0] = c;
u[1] = c;

// upper case Basic Latin

if ( c >= 0x0041 && c <= 0x005A) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Basic Latin
if ( c >= 0x0061 && c <= 0x007a ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// upper case Latin-1 Supplement
if ( (c >= 0x00C0 && c <= 0x00D6) || (c >= 0x00D8 && c<=0x00DE) ) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Latin-1 Supplement
if ( (c >= 0x00E0 && c <= 0x00F6) || (c >= 0x00F8 && c <= 0x00FE) ) {
u[0] = c - 32;
u[1] = c;
return u;
}
if ( c == 0x00FF ) {
u[0] = 0x0178;
u[1] = c;
return u;
}
// Latin Extended A
if ( (c >= 0x0100 && c < 0x0138) || (c > 0x0149 && c < 0x0178) ) {
// special case for capital I
if ( c == 0x0130 ) {
u[0] = c;
u[1] = 0x0069;
return u;
}
if ( c == 0x0131 ) {
u[0] = 0x0049;
u[1] = c;
return u;
}

if ( c % 2 == 0 ) {
// if it's even, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's odd, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x0178 ) {
u[0] = c;
u[1] = 0x00FF;
return u;
}

if ( (c >= 0x0139 && c < 0x0149) || (c > 0x0178 && c < 0x017F) ) {
if ( c % 2 == 1 ) {
// if it's odd, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's even, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x017F ) {
u[0] = 0x0053;
u[1] = c;
}

// Latin Extended B
// need to improve this set

if ( c >= 0x0200 && c <= 0x0217 ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c+1;
} else {
u[0] = c-1;
u[1] = c;
}
return u;
}

// Latin Extended Additional
// Range: U+1E00 to U+1EFF
// http://www.unicode.org/Unicode.charts/glyphless/U1E00.html

// Spacing Modifier Leters
// Range: U+02B0 to U+02FF

// Combining Diacritical Marks
// Range: U+0300 to U+036F

// skip Greek for now
// Greek
// Range: U+0370 to U+03FF

// Cyrillic
// Range: U+0400 to U+04FF

//        if ( (c >= 0x0401 && c <= 0x040C) || ( c>= 0x040E && c <= 0x040F ) ) {
if ( (c >= 0x0400 && c <= 0x040C) || ( c>= 0x040D && c <= 0x040F ) ) {
u[0] = c;
u[1] = c + 80;
return u;
}


if ( c >= 0x0410  && c <= 0x042F ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0x0430 && c<= 0x044F ) {
u[0] = c - 32;
u[1] = c;
return u;

}
if ( (c >= 0x0451 && c <= 0x045C) || (c >=0x045E && c<= 0x045F) ) {
u[0] = c -80;
u[1] = c;
return u;
}

if ( c >= 0x0460 && c <= 0x047F ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c +1;
} else {
u[0] = c - 1;
u[1] = c;
}
return u;
}

// Armenian
// Range: U+0530 to U+058F
if ( c >= 0x0531 && c <= 0x0556 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x0561 && c < 0x0587 ) {
u[0] = c - 48;
u[1] = c;
return u;
}

// Hebrew
// Range: U+0590 to U+05FF


// Arabic
// Range: U+0600 to U+06FF

// Devanagari
// Range: U+0900 to U+097F


// Bengali
// Range: U+0980 to U+09FF


// Gurmukhi
// Range: U+0A00 to U+0A7F


// Gujarati
// Range: U+0A80 to U+0AFF


// Oriya
// Range: U+0B00 to U+0B7F
// no capital / lower case


// Tamil
// Range: U+0B80 to U+0BFF
// no capital / lower case


// Telugu
// Range: U+0C00 to U+0C7F
// no capital / lower case


// Kannada
// Range: U+0C80 to U+0CFF
// no capital / lower case


// Malayalam
// Range: U+0D00 to U+0D7F

// Thai
// Range: U+0E00 to U+0E7F


// Lao
// Range: U+0E80 to U+0EFF


// Tibetan
// Range: U+0F00 to U+0FBF

// Georgian
// Range: U+10A0 to U+10F0
if ( c >= 0x10A0 && c <= 0x10C5 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x10D0 && c <= 0x10F5 ) {
u[0] = c;
u[1] = c;
return u;
}

// Hangul Jamo
// Range: U+1100 to U+11FF

// Greek Extended
// Range: U+1F00 to U+1FFF
// skip for now


// General Punctuation
// Range: U+2000 to U+206F

// Superscripts and Subscripts
// Range: U+2070 to U+209F

// Currency Symbols
// Range: U+20A0 to U+20CF


// Combining Diacritical Marks for Symbols
// Range: U+20D0 to U+20FF
// skip for now


// Number Forms
// Range: U+2150 to U+218F
// skip for now


// Arrows
// Range: U+2190 to U+21FF

// Mathematical Operators
// Range: U+2200 to U+22FF

// Miscellaneous Technical
// Range: U+2300 to U+23FF

// Control Pictures
// Range: U+2400 to U+243F

// Optical Character Recognition
// Range: U+2440 to U+245F

// Enclosed Alphanumerics
// Range: U+2460 to U+24FF

// Box Drawing
// Range: U+2500 to U+257F

// Block Elements
// Range: U+2580 to U+259F

// Geometric Shapes
// Range: U+25A0 to U+25FF

// Miscellaneous Symbols
// Range: U+2600 to U+26FF

// Dingbats
// Range: U+2700 to U+27BF

// CJK Symbols and Punctuation
// Range: U+3000 to U+303F

// Hiragana
// Range: U+3040 to U+309F

// Katakana
// Range: U+30A0 to U+30FF

// Bopomofo
// Range: U+3100 to U+312F

// Hangul Compatibility Jamo
// Range: U+3130 to U+318F

// Kanbun
// Range: U+3190 to U+319F


// Enclosed CJK Letters and Months
// Range: U+3200 to U+32FF

// CJK Compatibility
// Range: U+3300 to U+33FF

// Hangul Syllables
// Range: U+AC00 to U+D7A3

// High Surrogates
// Range: U+D800 to U+DB7F

// Private Use High Surrogates
// Range: U+DB80 to U+DBFF

// Low Surrogates
// Range: U+DC00 to U+DFFF

// Private Use Area
// Range: U+E000 to U+F8FF

// CJK Compatibility Ideographs
// Range: U+F900 to U+FAFF

// Alphabetic Presentation Forms
// Range: U+FB00 to U+FB4F

// Arabic Presentation Forms-A
// Range: U+FB50 to U+FDFF

// Combining Half Marks
// Range: U+FE20 to U+FE2F

// CJK Compatibility Forms
// Range: U+FE30 to U+FE4F

// Small Form Variants
// Range: U+FE50 to U+FE6F

// Arabic Presentation Forms-B
// Range: U+FE70 to U+FEFF

// Halfwidth and Fullwidth Forms
// Range: U+FF00 to U+FFEF

if ( c >= 0xFF21 && c <= 0xFF3A ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0xFF41 && c <= 0xFF5A ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// Specials
// Range: U+FFF0 to U+FFFF

return u;
}

function DecimalToHexString( n ) {
n = Number( n );
var h = "0x";

for ( var i = 3; i >= 0; i-- ) {
if ( n >= Math.pow(16, i) ){
var t = Math.floor( n  / Math.pow(16, i));
n -= t * Math.pow(16, i);
if ( t >= 10 ) {
if ( t == 10 ) {
h += "A";
}
if ( t == 11 ) {
h += "B";
}
if ( t == 12 ) {
h += "C";
}
if ( t == 13 ) {
h += "D";
}
if ( t == 14 ) {
h += "E";
}
if ( t == 15 ) {
h += "F";
}
} else {
h += String( t );
}
} else {
h += "0";
}
}

return h;
}

},

/**
File Name:          15.5.4.11-6.js
ECMA Section:       15.5.4.11 String.prototype.toLowerCase()
Description:

Returns a string equal in length to the length of the result of converting
this object to a string. The result is a string value, not a String object.

Every character of the result is equal to the corresponding character of the
string, unless that character has a Unicode 2.0 uppercase equivalent, in which
case the uppercase equivalent is used instead. (The canonical Unicode 2.0 case
mapping shall be used, which does not depend on implementation or locale.)

Note that the toLowerCase function is intentionally generic; it does not require
that its this value be a String object. Therefore it can be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_5_4_11__6: function() {
var SECTION = "15.5.4.11-6";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.toLowerCase()";

//writeHeaderToLog( SECTION + " "+ TITLE);

// Armenian
// Range: U+0530 to U+058F
for ( var i = 0x0530; i <= 0x058F; i++ ) {

var U = new Unicode( i );
var u = null;
/*
new TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toLowerCase()",
String.fromCharCode(U.lower),
eval("var s = new String( String.fromCharCode("+i+") ); s.toLowerCase()") );
*/
this.TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toLowerCase().charCodeAt(0)",
U.lower,
eval("var s = new String( String.fromCharCode(i) ); s.toLowerCase().charCodeAt(0)") );

}

//test();

function MyObject( value ) {
this.value = value;
this.substring = String.prototype.substring;
this.toString = new Function ( "return this.value+''" );
}
function Unicode( c ) {
var u = GetUnicodeValues( c );
this.upper = u[0];
this.lower = u[1]
return this;
}
function GetUnicodeValues( c ) {
var u = new Array();

u[0] = c;
u[1] = c;

// upper case Basic Latin

if ( c >= 0x0041 && c <= 0x005A) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Basic Latin
if ( c >= 0x0061 && c <= 0x007a ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// upper case Latin-1 Supplement
if ( (c >= 0x00C0 && c <= 0x00D6) || (c >= 0x00D8 && c<=0x00DE) ) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Latin-1 Supplement
if ( (c >= 0x00E0 && c <= 0x00F6) || (c >= 0x00F8 && c <= 0x00FE) ) {
u[0] = c - 32;
u[1] = c;
return u;
}
if ( c == 0x00FF ) {
u[0] = 0x0178;
u[1] = c;
return u;
}
// Latin Extended A
if ( (c >= 0x0100 && c < 0x0138) || (c > 0x0149 && c < 0x0178) ) {
// special case for capital I
if ( c == 0x0130 ) {
u[0] = c;
u[1] = 0x0069;
return u;
}
if ( c == 0x0131 ) {
u[0] = 0x0049;
u[1] = c;
return u;
}

if ( c % 2 == 0 ) {
// if it's even, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's odd, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x0178 ) {
u[0] = c;
u[1] = 0x00FF;
return u;
}

if ( (c >= 0x0139 && c < 0x0149) || (c > 0x0178 && c < 0x017F) ) {
if ( c % 2 == 1 ) {
// if it's odd, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's even, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x017F ) {
u[0] = 0x0053;
u[1] = c;
}

// Latin Extended B
// need to improve this set

if ( c >= 0x0200 && c <= 0x0217 ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c+1;
} else {
u[0] = c-1;
u[1] = c;
}
return u;
}

// Latin Extended Additional
// Range: U+1E00 to U+1EFF
// http://www.unicode.org/Unicode.charts/glyphless/U1E00.html

// Spacing Modifier Leters
// Range: U+02B0 to U+02FF

// Combining Diacritical Marks
// Range: U+0300 to U+036F

// skip Greek for now
// Greek
// Range: U+0370 to U+03FF

// Cyrillic
// Range: U+0400 to U+04FF

if ( (c >= 0x0401 && c <= 0x040C) || ( c>= 0x040E && c <= 0x040F ) ) {
u[0] = c;
u[1] = c + 80;
return u;
}


if ( c >= 0x0410  && c <= 0x042F ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0x0430 && c<= 0x044F ) {
u[0] = c - 32;
u[1] = c;
return u;

}
if ( (c >= 0x0451 && c <= 0x045C) || (c >=0x045E && c<= 0x045F) ) {
u[0] = c -80;
u[1] = c;
return u;
}

if ( c >= 0x0460 && c <= 0x047F ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c +1;
} else {
u[0] = c - 1;
u[1] = c;
}
return u;
}

// Armenian
// Range: U+0530 to U+058F
if ( c >= 0x0531 && c <= 0x0556 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x0561 && c < 0x0587 ) {
u[0] = c - 48;
u[1] = c;
return u;
}

// Hebrew
// Range: U+0590 to U+05FF


// Arabic
// Range: U+0600 to U+06FF

// Devanagari
// Range: U+0900 to U+097F


// Bengali
// Range: U+0980 to U+09FF


// Gurmukhi
// Range: U+0A00 to U+0A7F


// Gujarati
// Range: U+0A80 to U+0AFF


// Oriya
// Range: U+0B00 to U+0B7F
// no capital / lower case


// Tamil
// Range: U+0B80 to U+0BFF
// no capital / lower case


// Telugu
// Range: U+0C00 to U+0C7F
// no capital / lower case


// Kannada
// Range: U+0C80 to U+0CFF
// no capital / lower case


// Malayalam
// Range: U+0D00 to U+0D7F

// Thai
// Range: U+0E00 to U+0E7F


// Lao
// Range: U+0E80 to U+0EFF


// Tibetan
// Range: U+0F00 to U+0FBF

// Georgian
// Range: U+10A0 to U+10F0
if ( c >= 0x10A0 && c <= 0x10C5 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x10D0 && c <= 0x10F5 ) {
u[0] = c;
u[1] = c;
return u;
}

// Hangul Jamo
// Range: U+1100 to U+11FF

// Greek Extended
// Range: U+1F00 to U+1FFF
// skip for now


// General Punctuation
// Range: U+2000 to U+206F

// Superscripts and Subscripts
// Range: U+2070 to U+209F

// Currency Symbols
// Range: U+20A0 to U+20CF


// Combining Diacritical Marks for Symbols
// Range: U+20D0 to U+20FF
// skip for now


// Number Forms
// Range: U+2150 to U+218F
// skip for now


// Arrows
// Range: U+2190 to U+21FF

// Mathematical Operators
// Range: U+2200 to U+22FF

// Miscellaneous Technical
// Range: U+2300 to U+23FF

// Control Pictures
// Range: U+2400 to U+243F

// Optical Character Recognition
// Range: U+2440 to U+245F

// Enclosed Alphanumerics
// Range: U+2460 to U+24FF

// Box Drawing
// Range: U+2500 to U+257F

// Block Elements
// Range: U+2580 to U+259F

// Geometric Shapes
// Range: U+25A0 to U+25FF

// Miscellaneous Symbols
// Range: U+2600 to U+26FF

// Dingbats
// Range: U+2700 to U+27BF

// CJK Symbols and Punctuation
// Range: U+3000 to U+303F

// Hiragana
// Range: U+3040 to U+309F

// Katakana
// Range: U+30A0 to U+30FF

// Bopomofo
// Range: U+3100 to U+312F

// Hangul Compatibility Jamo
// Range: U+3130 to U+318F

// Kanbun
// Range: U+3190 to U+319F


// Enclosed CJK Letters and Months
// Range: U+3200 to U+32FF

// CJK Compatibility
// Range: U+3300 to U+33FF

// Hangul Syllables
// Range: U+AC00 to U+D7A3

// High Surrogates
// Range: U+D800 to U+DB7F

// Private Use High Surrogates
// Range: U+DB80 to U+DBFF

// Low Surrogates
// Range: U+DC00 to U+DFFF

// Private Use Area
// Range: U+E000 to U+F8FF

// CJK Compatibility Ideographs
// Range: U+F900 to U+FAFF

// Alphabetic Presentation Forms
// Range: U+FB00 to U+FB4F

// Arabic Presentation Forms-A
// Range: U+FB50 to U+FDFF

// Combining Half Marks
// Range: U+FE20 to U+FE2F

// CJK Compatibility Forms
// Range: U+FE30 to U+FE4F

// Small Form Variants
// Range: U+FE50 to U+FE6F

// Arabic Presentation Forms-B
// Range: U+FE70 to U+FEFF

// Halfwidth and Fullwidth Forms
// Range: U+FF00 to U+FFEF

if ( c >= 0xFF21 && c <= 0xFF3A ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0xFF41 && c <= 0xFF5A ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// Specials
// Range: U+FFF0 to U+FFFF

return u;
}

function DecimalToHexString( n ) {
n = Number( n );
var h = "0x";

for ( var i = 3; i >= 0; i-- ) {
if ( n >= Math.pow(16, i) ){
var t = Math.floor( n  / Math.pow(16, i));
n -= t * Math.pow(16, i);
if ( t >= 10 ) {
if ( t == 10 ) {
h += "A";
}
if ( t == 11 ) {
h += "B";
}
if ( t == 12 ) {
h += "C";
}
if ( t == 13 ) {
h += "D";
}
if ( t == 14 ) {
h += "E";
}
if ( t == 15 ) {
h += "F";
}
} else {
h += String( t );
}
} else {
h += "0";
}
}

return h;
}

},

/**
File Name:          15.5.4.11-6.js
ECMA Section:       15.5.4.11 String.prototype.toLowerCase()
Description:

Returns a string equal in length to the length of the result of converting
this object to a string. The result is a string value, not a String object.

Every character of the result is equal to the corresponding character of the
string, unless that character has a Unicode 2.0 uppercase equivalent, in which
case the uppercase equivalent is used instead. (The canonical Unicode 2.0 case
mapping shall be used, which does not depend on implementation or locale.)

Note that the toLowerCase function is intentionally generic; it does not require
that its this value be a String object. Therefore it can be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_5_4_11__6_WORKS: function() {
var SECTION = "15.5.4.11-6";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.toLowerCase()";

//writeHeaderToLog( SECTION + " "+ TITLE);

// Armenian
// Range: U+0530 to U+058F
for ( var i = 0x0530; i <= 0x058F; i++ ) {

var U = new Unicode( i );
/*
new TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toLowerCase()",
String.fromCharCode(U.lower),
eval("var s = new String( String.fromCharCode("+i+") ); s.toLowerCase()") );
*/
this.TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toLowerCase().charCodeAt(0)",
U.lower,
eval("var s = new String( String.fromCharCode("+i+") ); s.toLowerCase().charCodeAt(0)") );

}

//test();

function MyObject( value ) {
this.value = value;
this.substring = String.prototype.substring;
this.toString = new Function ( "return this.value+''" );
}
function Unicode( c ) {
var u = GetUnicodeValues( c );
this.upper = u[0];
this.lower = u[1]
return this;
}
function GetUnicodeValues( c ) {
var u = new Array();

u[0] = c;
u[1] = c;

// upper case Basic Latin

if ( c >= 0x0041 && c <= 0x005A) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Basic Latin
if ( c >= 0x0061 && c <= 0x007a ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// upper case Latin-1 Supplement
if ( (c >= 0x00C0 && c <= 0x00D6) || (c >= 0x00D8 && c<=0x00DE) ) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Latin-1 Supplement
if ( (c >= 0x00E0 && c <= 0x00F6) || (c >= 0x00F8 && c <= 0x00FE) ) {
u[0] = c - 32;
u[1] = c;
return u;
}
if ( c == 0x00FF ) {
u[0] = 0x0178;
u[1] = c;
return u;
}
// Latin Extended A
if ( (c >= 0x0100 && c < 0x0138) || (c > 0x0149 && c < 0x0178) ) {
// special case for capital I
if ( c == 0x0130 ) {
u[0] = c;
u[1] = 0x0069;
return u;
}
if ( c == 0x0131 ) {
u[0] = 0x0049;
u[1] = c;
return u;
}

if ( c % 2 == 0 ) {
// if it's even, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's odd, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x0178 ) {
u[0] = c;
u[1] = 0x00FF;
return u;
}

if ( (c >= 0x0139 && c < 0x0149) || (c > 0x0178 && c < 0x017F) ) {
if ( c % 2 == 1 ) {
// if it's odd, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's even, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x017F ) {
u[0] = 0x0053;
u[1] = c;
}

// Latin Extended B
// need to improve this set

if ( c >= 0x0200 && c <= 0x0217 ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c+1;
} else {
u[0] = c-1;
u[1] = c;
}
return u;
}

// Latin Extended Additional
// Range: U+1E00 to U+1EFF
// http://www.unicode.org/Unicode.charts/glyphless/U1E00.html

// Spacing Modifier Leters
// Range: U+02B0 to U+02FF

// Combining Diacritical Marks
// Range: U+0300 to U+036F

// skip Greek for now
// Greek
// Range: U+0370 to U+03FF

// Cyrillic
// Range: U+0400 to U+04FF

if ( (c >= 0x0401 && c <= 0x040C) || ( c>= 0x040E && c <= 0x040F ) ) {
u[0] = c;
u[1] = c + 80;
return u;
}


if ( c >= 0x0410  && c <= 0x042F ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0x0430 && c<= 0x044F ) {
u[0] = c - 32;
u[1] = c;
return u;

}
if ( (c >= 0x0451 && c <= 0x045C) || (c >=0x045E && c<= 0x045F) ) {
u[0] = c -80;
u[1] = c;
return u;
}

if ( c >= 0x0460 && c <= 0x047F ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c +1;
} else {
u[0] = c - 1;
u[1] = c;
}
return u;
}

// Armenian
// Range: U+0530 to U+058F
if ( c >= 0x0531 && c <= 0x0556 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x0561 && c < 0x0587 ) {
u[0] = c - 48;
u[1] = c;
return u;
}

// Hebrew
// Range: U+0590 to U+05FF


// Arabic
// Range: U+0600 to U+06FF

// Devanagari
// Range: U+0900 to U+097F


// Bengali
// Range: U+0980 to U+09FF


// Gurmukhi
// Range: U+0A00 to U+0A7F


// Gujarati
// Range: U+0A80 to U+0AFF


// Oriya
// Range: U+0B00 to U+0B7F
// no capital / lower case


// Tamil
// Range: U+0B80 to U+0BFF
// no capital / lower case


// Telugu
// Range: U+0C00 to U+0C7F
// no capital / lower case


// Kannada
// Range: U+0C80 to U+0CFF
// no capital / lower case


// Malayalam
// Range: U+0D00 to U+0D7F

// Thai
// Range: U+0E00 to U+0E7F


// Lao
// Range: U+0E80 to U+0EFF


// Tibetan
// Range: U+0F00 to U+0FBF

// Georgian
// Range: U+10A0 to U+10F0
if ( c >= 0x10A0 && c <= 0x10C5 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x10D0 && c <= 0x10F5 ) {
u[0] = c;
u[1] = c;
return u;
}

// Hangul Jamo
// Range: U+1100 to U+11FF

// Greek Extended
// Range: U+1F00 to U+1FFF
// skip for now


// General Punctuation
// Range: U+2000 to U+206F

// Superscripts and Subscripts
// Range: U+2070 to U+209F

// Currency Symbols
// Range: U+20A0 to U+20CF


// Combining Diacritical Marks for Symbols
// Range: U+20D0 to U+20FF
// skip for now


// Number Forms
// Range: U+2150 to U+218F
// skip for now


// Arrows
// Range: U+2190 to U+21FF

// Mathematical Operators
// Range: U+2200 to U+22FF

// Miscellaneous Technical
// Range: U+2300 to U+23FF

// Control Pictures
// Range: U+2400 to U+243F

// Optical Character Recognition
// Range: U+2440 to U+245F

// Enclosed Alphanumerics
// Range: U+2460 to U+24FF

// Box Drawing
// Range: U+2500 to U+257F

// Block Elements
// Range: U+2580 to U+259F

// Geometric Shapes
// Range: U+25A0 to U+25FF

// Miscellaneous Symbols
// Range: U+2600 to U+26FF

// Dingbats
// Range: U+2700 to U+27BF

// CJK Symbols and Punctuation
// Range: U+3000 to U+303F

// Hiragana
// Range: U+3040 to U+309F

// Katakana
// Range: U+30A0 to U+30FF

// Bopomofo
// Range: U+3100 to U+312F

// Hangul Compatibility Jamo
// Range: U+3130 to U+318F

// Kanbun
// Range: U+3190 to U+319F


// Enclosed CJK Letters and Months
// Range: U+3200 to U+32FF

// CJK Compatibility
// Range: U+3300 to U+33FF

// Hangul Syllables
// Range: U+AC00 to U+D7A3

// High Surrogates
// Range: U+D800 to U+DB7F

// Private Use High Surrogates
// Range: U+DB80 to U+DBFF

// Low Surrogates
// Range: U+DC00 to U+DFFF

// Private Use Area
// Range: U+E000 to U+F8FF

// CJK Compatibility Ideographs
// Range: U+F900 to U+FAFF

// Alphabetic Presentation Forms
// Range: U+FB00 to U+FB4F

// Arabic Presentation Forms-A
// Range: U+FB50 to U+FDFF

// Combining Half Marks
// Range: U+FE20 to U+FE2F

// CJK Compatibility Forms
// Range: U+FE30 to U+FE4F

// Small Form Variants
// Range: U+FE50 to U+FE6F

// Arabic Presentation Forms-B
// Range: U+FE70 to U+FEFF

// Halfwidth and Fullwidth Forms
// Range: U+FF00 to U+FFEF

if ( c >= 0xFF21 && c <= 0xFF3A ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0xFF41 && c <= 0xFF5A ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// Specials
// Range: U+FFF0 to U+FFFF

return u;
}

function DecimalToHexString( n ) {
n = Number( n );
var h = "0x";

for ( var i = 3; i >= 0; i-- ) {
if ( n >= Math.pow(16, i) ){
var t = Math.floor( n  / Math.pow(16, i));
n -= t * Math.pow(16, i);
if ( t >= 10 ) {
if ( t == 10 ) {
h += "A";
}
if ( t == 11 ) {
h += "B";
}
if ( t == 12 ) {
h += "C";
}
if ( t == 13 ) {
h += "D";
}
if ( t == 14 ) {
h += "E";
}
if ( t == 15 ) {
h += "F";
}
} else {
h += String( t );
}
} else {
h += "0";
}
}

return h;
}

},

/**
File Name:          15.5.4.12-1.js
ECMA Section:       15.5.4.12 String.prototype.toUpperCase()
Description:

Returns a string equal in length to the length of the result of converting
this object to a string. The result is a string value, not a String object.

Every character of the result is equal to the corresponding character of the
string, unless that character has a Unicode 2.0 uppercase equivalent, in which
case the uppercase equivalent is used instead. (The canonical Unicode 2.0 case
mapping shall be used, which does not depend on implementation or locale.)

Note that the toUpperCase function is intentionally generic; it does not require
that its this value be a String object. Therefore it can be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_5_4_12__1: function() {
var SECTION = "15.5.4.12-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.toUpperCase()";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,  "String.prototype.toUpperCase.length",        0,          String.prototype.toUpperCase.length );
this.TestCase( SECTION,  "delete String.prototype.toUpperCase.length", false,      delete String.prototype.toUpperCase.length );
this.TestCase( SECTION,  "delete String.prototype.toupperCase.length; String.prototype.toupperCase.length", 0,      eval("delete String.prototype.toUpperCase.length; String.prototype.toUpperCase.length") );

// Basic Latin, Latin-1 Supplement, Latin Extended A
for ( i = 0; i <= 0x017f; i++ ) {
var U = new Unicode( i );

// XXX DF fails in java

if ( i == 0x00DF ) {
continue;
}


this.TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toUpperCase().charCodeAt(0)",
U.upper,
eval("var s = new String( String.fromCharCode(i) ); s.toUpperCase().charCodeAt(0)") );
}

//test();


function MyObject( value ) {
this.value = value;
this.substring = String.prototype.substring;
this.toString = new Function ( "return this.value+''" );
}
function Unicode( c ) {
var u = GetUnicodeValues( c );
this.upper = u[0];
this.lower = u[1]
return this;
}
function GetUnicodeValues( c ) {
var u = new Array();

u[0] = c;
u[1] = c;

// upper case Basic Latin

if ( c >= 0x0041 && c <= 0x005A) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Basic Latin
if ( c >= 0x0061 && c <= 0x007a ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// upper case Latin-1 Supplement
if ( (c >= 0x00C0 && c <= 0x00D6) || (c >= 0x00D8 && c<=0x00DE) ) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Latin-1 Supplement
if ( (c >= 0x00E0 && c <= 0x00F6) || (c >= 0x00F8 && c <= 0x00FE) ) {
u[0] = c - 32;
u[1] = c;
return u;
}
if ( c == 0x00FF ) {
u[0] = 0x0178;
u[1] = c;
return u;
}
// Latin Extended A
if ( (c >= 0x0100 && c < 0x0138) || (c > 0x0149 && c < 0x0178) ) {
// special case for capital I
if ( c == 0x0130 ) {
u[0] = c;
u[1] = 0x0069;
return u;
}
if ( c == 0x0131 ) {
u[0] = 0x0049;
u[1] = c;
return u;
}

if ( c % 2 == 0 ) {
// if it's even, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's odd, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x0178 ) {
u[0] = c;
u[1] = 0x00FF;
return u;
}

if ( (c >= 0x0139 && c < 0x0149) || (c > 0x0178 && c < 0x017F) ) {
if ( c % 2 == 1 ) {
// if it's odd, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's even, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x017F ) {
u[0] = 0x0053;
u[1] = c;
}

// Latin Extended B
// need to improve this set

if ( c >= 0x0200 && c <= 0x0217 ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c+1;
} else {
u[0] = c-1;
u[1] = c;
}
return u;
}

// Latin Extended Additional
// Range: U+1E00 to U+1EFF
// http://www.unicode.org/Unicode.charts/glyphless/U1E00.html

// Spacing Modifier Leters
// Range: U+02B0 to U+02FF

// Combining Diacritical Marks
// Range: U+0300 to U+036F

// skip Greek for now
// Greek
// Range: U+0370 to U+03FF

// Cyrillic
// Range: U+0400 to U+04FF

if ( (c >= 0x0401 && c <= 0x040C) || ( c>= 0x040E && c <= 0x040F ) ) {
u[0] = c;
u[1] = c + 80;
return u;
}


if ( c >= 0x0410  && c <= 0x042F ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0x0430 && c<= 0x044F ) {
u[0] = c - 32;
u[1] = c;
return u;

}
if ( (c >= 0x0451 && c <= 0x045C) || (c >=0x045E && c<= 0x045F) ) {
u[0] = c -80;
u[1] = c;
return u;
}

if ( c >= 0x0460 && c <= 0x047F ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c +1;
} else {
u[0] = c - 1;
u[1] = c;
}
return u;
}

// Armenian
// Range: U+0530 to U+058F
if ( c >= 0x0531 && c <= 0x0556 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x0561 && c < 0x0587 ) {
u[0] = c - 48;
u[1] = c;
return u;
}

// Hebrew
// Range: U+0590 to U+05FF


// Arabic
// Range: U+0600 to U+06FF

// Devanagari
// Range: U+0900 to U+097F


// Bengali
// Range: U+0980 to U+09FF


// Gurmukhi
// Range: U+0A00 to U+0A7F


// Gujarati
// Range: U+0A80 to U+0AFF


// Oriya
// Range: U+0B00 to U+0B7F
// no capital / lower case


// Tamil
// Range: U+0B80 to U+0BFF
// no capital / lower case


// Telugu
// Range: U+0C00 to U+0C7F
// no capital / lower case


// Kannada
// Range: U+0C80 to U+0CFF
// no capital / lower case


// Malayalam
// Range: U+0D00 to U+0D7F

// Thai
// Range: U+0E00 to U+0E7F


// Lao
// Range: U+0E80 to U+0EFF


// Tibetan
// Range: U+0F00 to U+0FBF

// Georgian
// Range: U+10A0 to U+10F0
if ( c >= 0x10A0 && c <= 0x10C5 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x10D0 && c <= 0x10F5 ) {
u[0] = c;
u[1] = c;
return u;
}

// Hangul Jamo
// Range: U+1100 to U+11FF

// Greek Extended
// Range: U+1F00 to U+1FFF
// skip for now


// General Punctuation
// Range: U+2000 to U+206F

// Superscripts and Subscripts
// Range: U+2070 to U+209F

// Currency Symbols
// Range: U+20A0 to U+20CF


// Combining Diacritical Marks for Symbols
// Range: U+20D0 to U+20FF
// skip for now


// Number Forms
// Range: U+2150 to U+218F
// skip for now


// Arrows
// Range: U+2190 to U+21FF

// Mathematical Operators
// Range: U+2200 to U+22FF

// Miscellaneous Technical
// Range: U+2300 to U+23FF

// Control Pictures
// Range: U+2400 to U+243F

// Optical Character Recognition
// Range: U+2440 to U+245F

// Enclosed Alphanumerics
// Range: U+2460 to U+24FF

// Box Drawing
// Range: U+2500 to U+257F

// Block Elements
// Range: U+2580 to U+259F

// Geometric Shapes
// Range: U+25A0 to U+25FF

// Miscellaneous Symbols
// Range: U+2600 to U+26FF

// Dingbats
// Range: U+2700 to U+27BF

// CJK Symbols and Punctuation
// Range: U+3000 to U+303F

// Hiragana
// Range: U+3040 to U+309F

// Katakana
// Range: U+30A0 to U+30FF

// Bopomofo
// Range: U+3100 to U+312F

// Hangul Compatibility Jamo
// Range: U+3130 to U+318F

// Kanbun
// Range: U+3190 to U+319F


// Enclosed CJK Letters and Months
// Range: U+3200 to U+32FF

// CJK Compatibility
// Range: U+3300 to U+33FF

// Hangul Syllables
// Range: U+AC00 to U+D7A3

// High Surrogates
// Range: U+D800 to U+DB7F

// Private Use High Surrogates
// Range: U+DB80 to U+DBFF

// Low Surrogates
// Range: U+DC00 to U+DFFF

// Private Use Area
// Range: U+E000 to U+F8FF

// CJK Compatibility Ideographs
// Range: U+F900 to U+FAFF

// Alphabetic Presentation Forms
// Range: U+FB00 to U+FB4F

// Arabic Presentation Forms-A
// Range: U+FB50 to U+FDFF

// Combining Half Marks
// Range: U+FE20 to U+FE2F

// CJK Compatibility Forms
// Range: U+FE30 to U+FE4F

// Small Form Variants
// Range: U+FE50 to U+FE6F

// Arabic Presentation Forms-B
// Range: U+FE70 to U+FEFF

// Halfwidth and Fullwidth Forms
// Range: U+FF00 to U+FFEF

if ( c >= 0xFF21 && c <= 0xFF3A ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0xFF41 && c <= 0xFF5A ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// Specials
// Range: U+FFF0 to U+FFFF

return u;
}

function DecimalToHexString( n ) {
n = Number( n );
var h = "0x";

for ( var i = 3; i >= 0; i-- ) {
if ( n >= Math.pow(16, i) ){
var t = Math.floor( n  / Math.pow(16, i));
n -= t * Math.pow(16, i);
if ( t >= 10 ) {
if ( t == 10 ) {
h += "A";
}
if ( t == 11 ) {
h += "B";
}
if ( t == 12 ) {
h += "C";
}
if ( t == 13 ) {
h += "D";
}
if ( t == 14 ) {
h += "E";
}
if ( t == 15 ) {
h += "F";
}
} else {
h += String( t );
}
} else {
h += "0";
}
}

return h;
}

},

/**
File Name:          15.5.4.12-1.js
ECMA Section:       15.5.4.12 String.prototype.toUpperCase()
Description:

Returns a string equal in length to the length of the result of converting
this object to a string. The result is a string value, not a String object.

Every character of the result is equal to the corresponding character of the
string, unless that character has a Unicode 2.0 uppercase equivalent, in which
case the uppercase equivalent is used instead. (The canonical Unicode 2.0 case
mapping shall be used, which does not depend on implementation or locale.)

Note that the toUpperCase function is intentionally generic; it does not require
that its this value be a String object. Therefore it can be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_5_4_12__1_WORKS: function() {
var SECTION = "15.5.4.12-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.toUpperCase()";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,  "String.prototype.toUpperCase.length",        0,          String.prototype.toUpperCase.length );
this.TestCase( SECTION,  "delete String.prototype.toUpperCase.length", false,      delete String.prototype.toUpperCase.length );
this.TestCase( SECTION,  "delete String.prototype.toupperCase.length; String.prototype.toupperCase.length", 0,      eval("delete String.prototype.toUpperCase.length; String.prototype.toUpperCase.length") );

// Basic Latin, Latin-1 Supplement, Latin Extended A
for ( i = 0; i <= 0x017f; i++ ) {
var U = new Unicode( i );

// XXX DF fails in java

if ( i == 0x00DF || i == 0x00B5 || i == 0x0149) {
continue;
}


this.TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toUpperCase().charCodeAt(0)",
U.upper,
eval("var s = new String( String.fromCharCode("+i+") ); s.toUpperCase().charCodeAt(0)") );

}

//test();

function MyObject( value ) {
this.value = value;
this.substring = String.prototype.substring;
this.toString = new Function ( "return this.value+''" );
}
function Unicode( c ) {
var u = GetUnicodeValues( c );
this.upper = u[0];
this.lower = u[1]
return this;
}
function GetUnicodeValues( c ) {
var u = new Array();

u[0] = c;
u[1] = c;

// upper case Basic Latin

if ( c >= 0x0041 && c <= 0x005A) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Basic Latin
if ( c >= 0x0061 && c <= 0x007a ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// upper case Latin-1 Supplement
if ( (c >= 0x00C0 && c <= 0x00D6) || (c >= 0x00D8 && c<=0x00DE) ) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Latin-1 Supplement
if ( (c >= 0x00E0 && c <= 0x00F6) || (c >= 0x00F8 && c <= 0x00FE) ) {
u[0] = c - 32;
u[1] = c;
return u;
}
if ( c == 0x00FF ) {
u[0] = 0x0178;
u[1] = c;
return u;
}
// Latin Extended A
if ( (c >= 0x0100 && c < 0x0138) || (c > 0x0149 && c < 0x0178) ) {
// special case for capital I
if ( c == 0x0130 ) {
u[0] = c;
u[1] = 0x0069;
return u;
}
if ( c == 0x0131 ) {
u[0] = 0x0049;
u[1] = c;
return u;
}

if ( c % 2 == 0 ) {
// if it's even, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's odd, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x0178 ) {
u[0] = c;
u[1] = 0x00FF;
return u;
}

if ( (c >= 0x0139 && c < 0x0149) || (c > 0x0178 && c < 0x017F) ) {
if ( c % 2 == 1 ) {
// if it's odd, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's even, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x017F ) {
u[0] = 0x0053;
u[1] = c;
}

// Latin Extended B
// need to improve this set

if ( c >= 0x0200 && c <= 0x0217 ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c+1;
} else {
u[0] = c-1;
u[1] = c;
}
return u;
}

// Latin Extended Additional
// Range: U+1E00 to U+1EFF
// http://www.unicode.org/Unicode.charts/glyphless/U1E00.html

// Spacing Modifier Leters
// Range: U+02B0 to U+02FF

// Combining Diacritical Marks
// Range: U+0300 to U+036F

// skip Greek for now
// Greek
// Range: U+0370 to U+03FF

// Cyrillic
// Range: U+0400 to U+04FF

if ( (c >= 0x0401 && c <= 0x040C) || ( c>= 0x040E && c <= 0x040F ) ) {
u[0] = c;
u[1] = c + 80;
return u;
}


if ( c >= 0x0410  && c <= 0x042F ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0x0430 && c<= 0x044F ) {
u[0] = c - 32;
u[1] = c;
return u;

}
if ( (c >= 0x0451 && c <= 0x045C) || (c >=0x045E && c<= 0x045F) ) {
u[0] = c -80;
u[1] = c;
return u;
}

if ( c >= 0x0460 && c <= 0x047F ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c +1;
} else {
u[0] = c - 1;
u[1] = c;
}
return u;
}

// Armenian
// Range: U+0530 to U+058F
if ( c >= 0x0531 && c <= 0x0556 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x0561 && c < 0x0587 ) {
u[0] = c - 48;
u[1] = c;
return u;
}

// Hebrew
// Range: U+0590 to U+05FF


// Arabic
// Range: U+0600 to U+06FF

// Devanagari
// Range: U+0900 to U+097F


// Bengali
// Range: U+0980 to U+09FF


// Gurmukhi
// Range: U+0A00 to U+0A7F


// Gujarati
// Range: U+0A80 to U+0AFF


// Oriya
// Range: U+0B00 to U+0B7F
// no capital / lower case


// Tamil
// Range: U+0B80 to U+0BFF
// no capital / lower case


// Telugu
// Range: U+0C00 to U+0C7F
// no capital / lower case


// Kannada
// Range: U+0C80 to U+0CFF
// no capital / lower case


// Malayalam
// Range: U+0D00 to U+0D7F

// Thai
// Range: U+0E00 to U+0E7F


// Lao
// Range: U+0E80 to U+0EFF


// Tibetan
// Range: U+0F00 to U+0FBF

// Georgian
// Range: U+10A0 to U+10F0
if ( c >= 0x10A0 && c <= 0x10C5 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x10D0 && c <= 0x10F5 ) {
u[0] = c;
u[1] = c;
return u;
}

// Hangul Jamo
// Range: U+1100 to U+11FF

// Greek Extended
// Range: U+1F00 to U+1FFF
// skip for now


// General Punctuation
// Range: U+2000 to U+206F

// Superscripts and Subscripts
// Range: U+2070 to U+209F

// Currency Symbols
// Range: U+20A0 to U+20CF


// Combining Diacritical Marks for Symbols
// Range: U+20D0 to U+20FF
// skip for now


// Number Forms
// Range: U+2150 to U+218F
// skip for now


// Arrows
// Range: U+2190 to U+21FF

// Mathematical Operators
// Range: U+2200 to U+22FF

// Miscellaneous Technical
// Range: U+2300 to U+23FF

// Control Pictures
// Range: U+2400 to U+243F

// Optical Character Recognition
// Range: U+2440 to U+245F

// Enclosed Alphanumerics
// Range: U+2460 to U+24FF

// Box Drawing
// Range: U+2500 to U+257F

// Block Elements
// Range: U+2580 to U+259F

// Geometric Shapes
// Range: U+25A0 to U+25FF

// Miscellaneous Symbols
// Range: U+2600 to U+26FF

// Dingbats
// Range: U+2700 to U+27BF

// CJK Symbols and Punctuation
// Range: U+3000 to U+303F

// Hiragana
// Range: U+3040 to U+309F

// Katakana
// Range: U+30A0 to U+30FF

// Bopomofo
// Range: U+3100 to U+312F

// Hangul Compatibility Jamo
// Range: U+3130 to U+318F

// Kanbun
// Range: U+3190 to U+319F


// Enclosed CJK Letters and Months
// Range: U+3200 to U+32FF

// CJK Compatibility
// Range: U+3300 to U+33FF

// Hangul Syllables
// Range: U+AC00 to U+D7A3

// High Surrogates
// Range: U+D800 to U+DB7F

// Private Use High Surrogates
// Range: U+DB80 to U+DBFF

// Low Surrogates
// Range: U+DC00 to U+DFFF

// Private Use Area
// Range: U+E000 to U+F8FF

// CJK Compatibility Ideographs
// Range: U+F900 to U+FAFF

// Alphabetic Presentation Forms
// Range: U+FB00 to U+FB4F

// Arabic Presentation Forms-A
// Range: U+FB50 to U+FDFF

// Combining Half Marks
// Range: U+FE20 to U+FE2F

// CJK Compatibility Forms
// Range: U+FE30 to U+FE4F

// Small Form Variants
// Range: U+FE50 to U+FE6F

// Arabic Presentation Forms-B
// Range: U+FE70 to U+FEFF

// Halfwidth and Fullwidth Forms
// Range: U+FF00 to U+FFEF

if ( c >= 0xFF21 && c <= 0xFF3A ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0xFF41 && c <= 0xFF5A ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// Specials
// Range: U+FFF0 to U+FFFF


return u;
}

function DecimalToHexString( n ) {
n = Number( n );
var h = "0x";

for ( var i = 3; i >= 0; i-- ) {
if ( n >= Math.pow(16, i) ){
var t = Math.floor( n  / Math.pow(16, i));
n -= t * Math.pow(16, i);
if ( t >= 10 ) {
if ( t == 10 ) {
h += "A";
}
if ( t == 11 ) {
h += "B";
}
if ( t == 12 ) {
h += "C";
}
if ( t == 13 ) {
h += "D";
}
if ( t == 14 ) {
h += "E";
}
if ( t == 15 ) {
h += "F";
}
} else {
h += String( t );
}
} else {
h += "0";
}
}

return h;
}

},

/**
File Name:          15.5.4.12-2.js
ECMA Section:       15.5.4.12 String.prototype.toUpperCase()
Description:

Returns a string equal in length to the length of the result of converting
this object to a string. The result is a string value, not a String object.

Every character of the result is equal to the corresponding character of the
string, unless that character has a Unicode 2.0 uppercase equivalent, in which
case the uppercase equivalent is used instead. (The canonical Unicode 2.0 case
mapping shall be used, which does not depend on implementation or locale.)

Note that the toUpperCase function is intentionally generic; it does not require
that its this value be a String object. Therefore it can be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_5_4_12__2: function() {
var SECTION = "15.5.4.12-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.toUpperCase()";

//writeHeaderToLog( SECTION + " "+ TITLE);

var TEST_STRING = "";
var EXPECT_STRING = "";

// basic latin test

for ( i = 0; i < 0x007A; i++ ) {
var u = new Unicode(i);
TEST_STRING += String.fromCharCode(i);
EXPECT_STRING += String.fromCharCode( u.upper );
}

// don't print out the value of the strings since they contain control
// characters that break the driver
var isEqual = EXPECT_STRING == (new String( TEST_STRING )).toUpperCase();

this.TestCase( SECTION,
"isEqual",
true,
isEqual);

//test();

function MyObject( value ) {
this.value = value;
this.substring = String.prototype.substring;
this.toString = new Function ( "return this.value+''" );
}
function Unicode( c ) {
var u = GetUnicodeValues( c );
this.upper = u[0];
this.lower = u[1]
return this;
}
function GetUnicodeValues( c ) {
var u = new Array();

u[0] = c;
u[1] = c;

// upper case Basic Latin

if ( c >= 0x0041 && c <= 0x005A) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Basic Latin
if ( c >= 0x0061 && c <= 0x007a ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// upper case Latin-1 Supplement
if ( (c >= 0x00C0 && c <= 0x00D6) || (c >= 0x00D8 && c<=0x00DE) ) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Latin-1 Supplement
if ( (c >= 0x00E0 && c <= 0x00F6) || (c >= 0x00F8 && c <= 0x00FE) ) {
u[0] = c - 32;
u[1] = c;
return u;
}
if ( c == 0x00FF ) {
u[0] = 0x0178;
u[1] = c;
return u;
}
// Latin Extended A
if ( (c >= 0x0100 && c < 0x0138) || (c > 0x0149 && c < 0x0178) ) {
// special case for capital I
if ( c == 0x0130 ) {
u[0] = c;
u[1] = 0x0069;
return u;
}
if ( c == 0x0131 ) {
u[0] = 0x0049;
u[1] = c;
return u;
}

if ( c % 2 == 0 ) {
// if it's even, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's odd, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x0178 ) {
u[0] = c;
u[1] = 0x00FF;
return u;
}

if ( (c >= 0x0139 && c < 0x0149) || (c > 0x0178 && c < 0x017F) ) {
if ( c % 2 == 1 ) {
// if it's odd, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's even, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x017F ) {
u[0] = 0x0053;
u[1] = c;
}

// Latin Extended B
// need to improve this set

if ( c >= 0x0200 && c <= 0x0217 ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c+1;
} else {
u[0] = c-1;
u[1] = c;
}
return u;
}

// Latin Extended Additional
// Range: U+1E00 to U+1EFF
// http://www.unicode.org/Unicode.charts/glyphless/U1E00.html

// Spacing Modifier Leters
// Range: U+02B0 to U+02FF

// Combining Diacritical Marks
// Range: U+0300 to U+036F

// skip Greek for now
// Greek
// Range: U+0370 to U+03FF

// Cyrillic
// Range: U+0400 to U+04FF

if ( (c >= 0x0401 && c <= 0x040C) || ( c>= 0x040E && c <= 0x040F ) ) {
u[0] = c;
u[1] = c + 80;
return u;
}


if ( c >= 0x0410  && c <= 0x042F ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0x0430 && c<= 0x044F ) {
u[0] = c - 32;
u[1] = c;
return u;

}
if ( (c >= 0x0451 && c <= 0x045C) || (c >=0x045E && c<= 0x045F) ) {
u[0] = c -80;
u[1] = c;
return u;
}

if ( c >= 0x0460 && c <= 0x047F ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c +1;
} else {
u[0] = c - 1;
u[1] = c;
}
return u;
}

// Armenian
// Range: U+0530 to U+058F
if ( c >= 0x0531 && c <= 0x0556 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x0561 && c < 0x0587 ) {
u[0] = c - 48;
u[1] = c;
return u;
}

// Hebrew
// Range: U+0590 to U+05FF


// Arabic
// Range: U+0600 to U+06FF

// Devanagari
// Range: U+0900 to U+097F


// Bengali
// Range: U+0980 to U+09FF


// Gurmukhi
// Range: U+0A00 to U+0A7F


// Gujarati
// Range: U+0A80 to U+0AFF


// Oriya
// Range: U+0B00 to U+0B7F
// no capital / lower case


// Tamil
// Range: U+0B80 to U+0BFF
// no capital / lower case


// Telugu
// Range: U+0C00 to U+0C7F
// no capital / lower case


// Kannada
// Range: U+0C80 to U+0CFF
// no capital / lower case


// Malayalam
// Range: U+0D00 to U+0D7F

// Thai
// Range: U+0E00 to U+0E7F


// Lao
// Range: U+0E80 to U+0EFF


// Tibetan
// Range: U+0F00 to U+0FBF

// Georgian
// Range: U+10A0 to U+10F0
if ( c >= 0x10A0 && c <= 0x10C5 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x10D0 && c <= 0x10F5 ) {
u[0] = c;
u[1] = c;
return u;
}

// Hangul Jamo
// Range: U+1100 to U+11FF

// Greek Extended
// Range: U+1F00 to U+1FFF
// skip for now


// General Punctuation
// Range: U+2000 to U+206F

// Superscripts and Subscripts
// Range: U+2070 to U+209F

// Currency Symbols
// Range: U+20A0 to U+20CF


// Combining Diacritical Marks for Symbols
// Range: U+20D0 to U+20FF
// skip for now


// Number Forms
// Range: U+2150 to U+218F
// skip for now


// Arrows
// Range: U+2190 to U+21FF

// Mathematical Operators
// Range: U+2200 to U+22FF

// Miscellaneous Technical
// Range: U+2300 to U+23FF

// Control Pictures
// Range: U+2400 to U+243F

// Optical Character Recognition
// Range: U+2440 to U+245F

// Enclosed Alphanumerics
// Range: U+2460 to U+24FF

// Box Drawing
// Range: U+2500 to U+257F

// Block Elements
// Range: U+2580 to U+259F

// Geometric Shapes
// Range: U+25A0 to U+25FF

// Miscellaneous Symbols
// Range: U+2600 to U+26FF

// Dingbats
// Range: U+2700 to U+27BF

// CJK Symbols and Punctuation
// Range: U+3000 to U+303F

// Hiragana
// Range: U+3040 to U+309F

// Katakana
// Range: U+30A0 to U+30FF

// Bopomofo
// Range: U+3100 to U+312F

// Hangul Compatibility Jamo
// Range: U+3130 to U+318F

// Kanbun
// Range: U+3190 to U+319F


// Enclosed CJK Letters and Months
// Range: U+3200 to U+32FF

// CJK Compatibility
// Range: U+3300 to U+33FF

// Hangul Syllables
// Range: U+AC00 to U+D7A3

// High Surrogates
// Range: U+D800 to U+DB7F

// Private Use High Surrogates
// Range: U+DB80 to U+DBFF

// Low Surrogates
// Range: U+DC00 to U+DFFF

// Private Use Area
// Range: U+E000 to U+F8FF

// CJK Compatibility Ideographs
// Range: U+F900 to U+FAFF

// Alphabetic Presentation Forms
// Range: U+FB00 to U+FB4F

// Arabic Presentation Forms-A
// Range: U+FB50 to U+FDFF

// Combining Half Marks
// Range: U+FE20 to U+FE2F

// CJK Compatibility Forms
// Range: U+FE30 to U+FE4F

// Small Form Variants
// Range: U+FE50 to U+FE6F

// Arabic Presentation Forms-B
// Range: U+FE70 to U+FEFF

// Halfwidth and Fullwidth Forms
// Range: U+FF00 to U+FFEF

if ( c >= 0xFF21 && c <= 0xFF3A ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0xFF41 && c <= 0xFF5A ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// Specials
// Range: U+FFF0 to U+FFFF

return u;
}

function DecimalToHexString( n ) {
n = Number( n );
var h = "0x";

for ( var i = 3; i >= 0; i-- ) {
if ( n >= Math.pow(16, i) ){
var t = Math.floor( n  / Math.pow(16, i));
n -= t * Math.pow(16, i);
if ( t >= 10 ) {
if ( t == 10 ) {
h += "A";
}
if ( t == 11 ) {
h += "B";
}
if ( t == 12 ) {
h += "C";
}
if ( t == 13 ) {
h += "D";
}
if ( t == 14 ) {
h += "E";
}
if ( t == 15 ) {
h += "F";
}
} else {
h += String( t );
}
} else {
h += "0";
}
}

return h;
}

},

/**
File Name:          15.5.4.12-3.js
ECMA Section:       15.5.4.12 String.prototype.toUpperCase()
Description:

Returns a string equal in length to the length of the result of converting
this object to a string. The result is a string value, not a String object.

Every character of the result is equal to the corresponding character of the
string, unless that character has a Unicode 2.0 uppercase equivalent, in which
case the uppercase equivalent is used instead. (The canonical Unicode 2.0 case
mapping shall be used, which does not depend on implementation or locale.)

Note that the toUpperCase function is intentionally generic; it does not require
that its this value be a String object. Therefore it can be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_5_4_12__3: function() {
var SECTION = "15.5.4.12-3";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.toUpperCase()";

//writeHeaderToLog( SECTION + " "+ TITLE);

// Georgian
// Range: U+10A0 to U+10FF
for ( var i = 0x10A0; i <= 0x10FF; i++ ) {
var U = new Unicode( i );
/*
new TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toUpperCase()",
String.fromCharCode(U.upper),
eval("var s = new String( String.fromCharCode("+i+") ); s.toUpperCase()") );
*/
this.TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toUpperCase().charCodeAt(0)",
U.upper,
eval("var s = new String( String.fromCharCode(i) ); s.toUpperCase().charCodeAt(0)") );

}

// Halfwidth and Fullwidth Forms
// Range: U+FF00 to U+FFEF
for ( i = 0xFF00; i <= 0xFFEF; i++ ) {
this.TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toUpperCase()",
eval( "var u = new Unicode( i ); String.fromCharCode(u.upper)" ),
eval("var s = new String( String.fromCharCode("+i+") ); s.toUpperCase()") );

this.TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toUpperCase().charCodeAt(0)",
eval( "var u = new Unicode( i ); u.upper" ),
eval("var s = new String( String.fromCharCode("+i+") ); s.toUpperCase().charCodeAt(0)") );
}

// Hiragana (no upper / lower case)
// Range: U+3040 to U+309F

for ( i = 0x3040; i <= 0x309F; i++ ) {
this.TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toUpperCase()",
eval( "var u = new Unicode( i ); String.fromCharCode(u.upper)" ),
eval("var s = new String( String.fromCharCode("+i+") ); s.toUpperCase()") );

this.TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toUpperCase().charCodeAt(0)",
eval( "var u = new Unicode( i ); u.upper" ),
eval("var s = new String( String.fromCharCode("+i+") ); s.toUpperCase().charCodeAt(0)") );
}


/*
var TEST_STRING = "";
var EXPECT_STRING = "";

// basic latin test

for ( i = 0; i < 0x007A; i++ ) {
var u = new Unicode(i);
TEST_STRING += String.fromCharCode(i);
EXPECT_STRING += String.fromCharCode( u.upper );
}
*/


//test();

function MyObject( value ) {
this.value = value;
this.substring = String.prototype.substring;
this.toString = new Function ( "return this.value+''" );
}
function Unicode( c ) {
var u = GetUnicodeValues( c );
this.upper = u[0];
this.lower = u[1]
return this;
}
function GetUnicodeValues( c ) {
var u = new Array();

u[0] = c;
u[1] = c;

// upper case Basic Latin

if ( c >= 0x0041 && c <= 0x005A) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Basic Latin
if ( c >= 0x0061 && c <= 0x007a ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// upper case Latin-1 Supplement
if ( (c >= 0x00C0 && c <= 0x00D6) || (c >= 0x00D8 && c<=0x00DE) ) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Latin-1 Supplement
if ( (c >= 0x00E0 && c <= 0x00F6) || (c >= 0x00F8 && c <= 0x00FE) ) {
u[0] = c - 32;
u[1] = c;
return u;
}
if ( c == 0x00FF ) {
u[0] = 0x0178;
u[1] = c;
return u;
}
// Latin Extended A
if ( (c >= 0x0100 && c < 0x0138) || (c > 0x0149 && c < 0x0178) ) {
// special case for capital I
if ( c == 0x0130 ) {
u[0] = c;
u[1] = 0x0069;
return u;
}
if ( c == 0x0131 ) {
u[0] = 0x0049;
u[1] = c;
return u;
}

if ( c % 2 == 0 ) {
// if it's even, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's odd, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x0178 ) {
u[0] = c;
u[1] = 0x00FF;
return u;
}

if ( (c >= 0x0139 && c < 0x0149) || (c > 0x0178 && c < 0x017F) ) {
if ( c % 2 == 1 ) {
// if it's odd, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's even, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x017F ) {
u[0] = 0x0053;
u[1] = c;
}

// Latin Extended B
// need to improve this set

if ( c >= 0x0200 && c <= 0x0217 ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c+1;
} else {
u[0] = c-1;
u[1] = c;
}
return u;
}

// Latin Extended Additional
// Range: U+1E00 to U+1EFF
// http://www.unicode.org/Unicode.charts/glyphless/U1E00.html

// Spacing Modifier Leters
// Range: U+02B0 to U+02FF

// Combining Diacritical Marks
// Range: U+0300 to U+036F

// skip Greek for now
// Greek
// Range: U+0370 to U+03FF

// Cyrillic
// Range: U+0400 to U+04FF

if ( (c >= 0x0401 && c <= 0x040C) || ( c>= 0x040E && c <= 0x040F ) ) {
u[0] = c;
u[1] = c + 80;
return u;
}


if ( c >= 0x0410  && c <= 0x042F ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0x0430 && c<= 0x044F ) {
u[0] = c - 32;
u[1] = c;
return u;

}
if ( (c >= 0x0451 && c <= 0x045C) || (c >=0x045E && c<= 0x045F) ) {
u[0] = c -80;
u[1] = c;
return u;
}

if ( c >= 0x0460 && c <= 0x047F ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c +1;
} else {
u[0] = c - 1;
u[1] = c;
}
return u;
}

// Armenian
// Range: U+0530 to U+058F
if ( c >= 0x0531 && c <= 0x0556 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x0561 && c < 0x0587 ) {
u[0] = c - 48;
u[1] = c;
return u;
}

// Hebrew
// Range: U+0590 to U+05FF


// Arabic
// Range: U+0600 to U+06FF

// Devanagari
// Range: U+0900 to U+097F


// Bengali
// Range: U+0980 to U+09FF


// Gurmukhi
// Range: U+0A00 to U+0A7F


// Gujarati
// Range: U+0A80 to U+0AFF


// Oriya
// Range: U+0B00 to U+0B7F
// no capital / lower case


// Tamil
// Range: U+0B80 to U+0BFF
// no capital / lower case


// Telugu
// Range: U+0C00 to U+0C7F
// no capital / lower case


// Kannada
// Range: U+0C80 to U+0CFF
// no capital / lower case


// Malayalam
// Range: U+0D00 to U+0D7F

// Thai
// Range: U+0E00 to U+0E7F


// Lao
// Range: U+0E80 to U+0EFF


// Tibetan
// Range: U+0F00 to U+0FBF

// Georgian
// Range: U+10A0 to U+10F0
if ( c >= 0x10A0 && c <= 0x10C5 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x10D0 && c <= 0x10F5 ) {
u[0] = c;
u[1] = c;
return u;
}

// Hangul Jamo
// Range: U+1100 to U+11FF

// Greek Extended
// Range: U+1F00 to U+1FFF
// skip for now


// General Punctuation
// Range: U+2000 to U+206F

// Superscripts and Subscripts
// Range: U+2070 to U+209F

// Currency Symbols
// Range: U+20A0 to U+20CF


// Combining Diacritical Marks for Symbols
// Range: U+20D0 to U+20FF
// skip for now


// Number Forms
// Range: U+2150 to U+218F
// skip for now


// Arrows
// Range: U+2190 to U+21FF

// Mathematical Operators
// Range: U+2200 to U+22FF

// Miscellaneous Technical
// Range: U+2300 to U+23FF

// Control Pictures
// Range: U+2400 to U+243F

// Optical Character Recognition
// Range: U+2440 to U+245F

// Enclosed Alphanumerics
// Range: U+2460 to U+24FF

// Box Drawing
// Range: U+2500 to U+257F

// Block Elements
// Range: U+2580 to U+259F

// Geometric Shapes
// Range: U+25A0 to U+25FF

// Miscellaneous Symbols
// Range: U+2600 to U+26FF

// Dingbats
// Range: U+2700 to U+27BF

// CJK Symbols and Punctuation
// Range: U+3000 to U+303F

// Hiragana
// Range: U+3040 to U+309F

// Katakana
// Range: U+30A0 to U+30FF

// Bopomofo
// Range: U+3100 to U+312F

// Hangul Compatibility Jamo
// Range: U+3130 to U+318F

// Kanbun
// Range: U+3190 to U+319F


// Enclosed CJK Letters and Months
// Range: U+3200 to U+32FF

// CJK Compatibility
// Range: U+3300 to U+33FF

// Hangul Syllables
// Range: U+AC00 to U+D7A3

// High Surrogates
// Range: U+D800 to U+DB7F

// Private Use High Surrogates
// Range: U+DB80 to U+DBFF

// Low Surrogates
// Range: U+DC00 to U+DFFF

// Private Use Area
// Range: U+E000 to U+F8FF

// CJK Compatibility Ideographs
// Range: U+F900 to U+FAFF

// Alphabetic Presentation Forms
// Range: U+FB00 to U+FB4F

// Arabic Presentation Forms-A
// Range: U+FB50 to U+FDFF

// Combining Half Marks
// Range: U+FE20 to U+FE2F

// CJK Compatibility Forms
// Range: U+FE30 to U+FE4F

// Small Form Variants
// Range: U+FE50 to U+FE6F

// Arabic Presentation Forms-B
// Range: U+FE70 to U+FEFF

// Halfwidth and Fullwidth Forms
// Range: U+FF00 to U+FFEF

if ( c >= 0xFF21 && c <= 0xFF3A ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0xFF41 && c <= 0xFF5A ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// Specials
// Range: U+FFF0 to U+FFFF

return u;
}

function DecimalToHexString( n ) {
n = Number( n );
var h = "0x";

for ( var i = 3; i >= 0; i-- ) {
if ( n >= Math.pow(16, i) ){
var t = Math.floor( n  / Math.pow(16, i));
n -= t * Math.pow(16, i);
if ( t >= 10 ) {
if ( t == 10 ) {
h += "A";
}
if ( t == 11 ) {
h += "B";
}
if ( t == 12 ) {
h += "C";
}
if ( t == 13 ) {
h += "D";
}
if ( t == 14 ) {
h += "E";
}
if ( t == 15 ) {
h += "F";
}
} else {
h += String( t );
}
} else {
h += "0";
}
}

return h;
}

},


/**
File Name:          15.5.4.12-3.js
ECMA Section:       15.5.4.12 String.prototype.toUpperCase()
Description:

Returns a string equal in length to the length of the result of converting
this object to a string. The result is a string value, not a String object.

Every character of the result is equal to the corresponding character of the
string, unless that character has a Unicode 2.0 uppercase equivalent, in which
case the uppercase equivalent is used instead. (The canonical Unicode 2.0 case
mapping shall be used, which does not depend on implementation or locale.)

Note that the toUpperCase function is intentionally generic; it does not require
that its this value be a String object. Therefore it can be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_5_4_12__3_WORKS: function() {
var SECTION = "15.5.4.12-3";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.toUpperCase()";

//writeHeaderToLog( SECTION + " "+ TITLE);

// Georgian
// Range: U+10A0 to U+10FF
for ( var i = 0x10A0; i <= 0x10FF; i++ ) {
var U = new Unicode( i );
/*
new TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toUpperCase()",
String.fromCharCode(U.upper),
eval("var s = new String( String.fromCharCode("+i+") ); s.toUpperCase()") );
*/
this.TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toUpperCase().charCodeAt(0)",
U.upper,
eval("var s = new String( String.fromCharCode("+i+") ); s.toUpperCase().charCodeAt(0)") );

}

// Halfwidth and Fullwidth Forms
// Range: U+FF00 to U+FFEF
for ( i = 0xFF00; i <= 0xFFEF; i++ ) {
this.TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toUpperCase()",
eval( "var u = new Unicode( i ); String.fromCharCode(u.upper)" ),
eval("var s = new String( String.fromCharCode("+i+") ); s.toUpperCase()") );

this.TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toUpperCase().charCodeAt(0)",
eval( "var u = new Unicode( i ); u.upper" ),
eval("var s = new String( String.fromCharCode("+i+") ); s.toUpperCase().charCodeAt(0)") );
}

// Hiragana (no upper / lower case)
// Range: U+3040 to U+309F

for ( i = 0x3040; i <= 0x309F; i++ ) {
this.TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toUpperCase()",
eval( "var u = new Unicode( i ); String.fromCharCode(u.upper)" ),
eval("var s = new String( String.fromCharCode("+i+") ); s.toUpperCase()") );

this.TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toUpperCase().charCodeAt(0)",
eval( "var u = new Unicode( i ); u.upper" ),
eval("var s = new String( String.fromCharCode("+i+") ); s.toUpperCase().charCodeAt(0)") );
}


/*
var TEST_STRING = "";
var EXPECT_STRING = "";

// basic latin test

for ( i = 0; i < 0x007A; i++ ) {
var u = new Unicode(i);
TEST_STRING += String.fromCharCode(i);
EXPECT_STRING += String.fromCharCode( u.upper );
}
*/


//test();

function MyObject( value ) {
this.value = value;
this.substring = String.prototype.substring;
this.toString = new Function ( "return this.value+''" );
}
function Unicode( c ) {
var u = GetUnicodeValues( c );
this.upper = u[0];
this.lower = u[1]
return this;
}
function GetUnicodeValues( c ) {
var u = new Array();

u[0] = c;
u[1] = c;

// upper case Basic Latin

if ( c >= 0x0041 && c <= 0x005A) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Basic Latin
if ( c >= 0x0061 && c <= 0x007a ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// upper case Latin-1 Supplement
if ( (c >= 0x00C0 && c <= 0x00D6) || (c >= 0x00D8 && c<=0x00DE) ) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Latin-1 Supplement
if ( (c >= 0x00E0 && c <= 0x00F6) || (c >= 0x00F8 && c <= 0x00FE) ) {
u[0] = c - 32;
u[1] = c;
return u;
}
if ( c == 0x00FF ) {
u[0] = 0x0178;
u[1] = c;
return u;
}
// Latin Extended A
if ( (c >= 0x0100 && c < 0x0138) || (c > 0x0149 && c < 0x0178) ) {
// special case for capital I
if ( c == 0x0130 ) {
u[0] = c;
u[1] = 0x0069;
return u;
}
if ( c == 0x0131 ) {
u[0] = 0x0049;
u[1] = c;
return u;
}

if ( c % 2 == 0 ) {
// if it's even, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's odd, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x0178 ) {
u[0] = c;
u[1] = 0x00FF;
return u;
}

if ( (c >= 0x0139 && c < 0x0149) || (c > 0x0178 && c < 0x017F) ) {
if ( c % 2 == 1 ) {
// if it's odd, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's even, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x017F ) {
u[0] = 0x0053;
u[1] = c;
}

// Latin Extended B
// need to improve this set

if ( c >= 0x0200 && c <= 0x0217 ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c+1;
} else {
u[0] = c-1;
u[1] = c;
}
return u;
}

// Latin Extended Additional
// Range: U+1E00 to U+1EFF
// http://www.unicode.org/Unicode.charts/glyphless/U1E00.html

// Spacing Modifier Leters
// Range: U+02B0 to U+02FF

// Combining Diacritical Marks
// Range: U+0300 to U+036F

// skip Greek for now
// Greek
// Range: U+0370 to U+03FF

// Cyrillic
// Range: U+0400 to U+04FF

if ( (c >= 0x0401 && c <= 0x040C) || ( c>= 0x040E && c <= 0x040F ) ) {
u[0] = c;
u[1] = c + 80;
return u;
}


if ( c >= 0x0410  && c <= 0x042F ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0x0430 && c<= 0x044F ) {
u[0] = c - 32;
u[1] = c;
return u;

}
if ( (c >= 0x0451 && c <= 0x045C) || (c >=0x045E && c<= 0x045F) ) {
u[0] = c -80;
u[1] = c;
return u;
}

if ( c >= 0x0460 && c <= 0x047F ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c +1;
} else {
u[0] = c - 1;
u[1] = c;
}
return u;
}

// Armenian
// Range: U+0530 to U+058F
if ( c >= 0x0531 && c <= 0x0556 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x0561 && c < 0x0587 ) {
u[0] = c - 48;
u[1] = c;
return u;
}

// Hebrew
// Range: U+0590 to U+05FF


// Arabic
// Range: U+0600 to U+06FF

// Devanagari
// Range: U+0900 to U+097F


// Bengali
// Range: U+0980 to U+09FF


// Gurmukhi
// Range: U+0A00 to U+0A7F


// Gujarati
// Range: U+0A80 to U+0AFF


// Oriya
// Range: U+0B00 to U+0B7F
// no capital / lower case


// Tamil
// Range: U+0B80 to U+0BFF
// no capital / lower case


// Telugu
// Range: U+0C00 to U+0C7F
// no capital / lower case


// Kannada
// Range: U+0C80 to U+0CFF
// no capital / lower case


// Malayalam
// Range: U+0D00 to U+0D7F

// Thai
// Range: U+0E00 to U+0E7F


// Lao
// Range: U+0E80 to U+0EFF


// Tibetan
// Range: U+0F00 to U+0FBF

// Georgian
// Range: U+10A0 to U+10F0
if ( c >= 0x10A0 && c <= 0x10C5 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x10D0 && c <= 0x10F5 ) {
u[0] = c;
u[1] = c;
return u;
}

// Hangul Jamo
// Range: U+1100 to U+11FF

// Greek Extended
// Range: U+1F00 to U+1FFF
// skip for now


// General Punctuation
// Range: U+2000 to U+206F

// Superscripts and Subscripts
// Range: U+2070 to U+209F

// Currency Symbols
// Range: U+20A0 to U+20CF


// Combining Diacritical Marks for Symbols
// Range: U+20D0 to U+20FF
// skip for now


// Number Forms
// Range: U+2150 to U+218F
// skip for now


// Arrows
// Range: U+2190 to U+21FF

// Mathematical Operators
// Range: U+2200 to U+22FF

// Miscellaneous Technical
// Range: U+2300 to U+23FF

// Control Pictures
// Range: U+2400 to U+243F

// Optical Character Recognition
// Range: U+2440 to U+245F

// Enclosed Alphanumerics
// Range: U+2460 to U+24FF

// Box Drawing
// Range: U+2500 to U+257F

// Block Elements
// Range: U+2580 to U+259F

// Geometric Shapes
// Range: U+25A0 to U+25FF

// Miscellaneous Symbols
// Range: U+2600 to U+26FF

// Dingbats
// Range: U+2700 to U+27BF

// CJK Symbols and Punctuation
// Range: U+3000 to U+303F

// Hiragana
// Range: U+3040 to U+309F

// Katakana
// Range: U+30A0 to U+30FF

// Bopomofo
// Range: U+3100 to U+312F

// Hangul Compatibility Jamo
// Range: U+3130 to U+318F

// Kanbun
// Range: U+3190 to U+319F


// Enclosed CJK Letters and Months
// Range: U+3200 to U+32FF

// CJK Compatibility
// Range: U+3300 to U+33FF

// Hangul Syllables
// Range: U+AC00 to U+D7A3

// High Surrogates
// Range: U+D800 to U+DB7F

// Private Use High Surrogates
// Range: U+DB80 to U+DBFF

// Low Surrogates
// Range: U+DC00 to U+DFFF

// Private Use Area
// Range: U+E000 to U+F8FF

// CJK Compatibility Ideographs
// Range: U+F900 to U+FAFF

// Alphabetic Presentation Forms
// Range: U+FB00 to U+FB4F

// Arabic Presentation Forms-A
// Range: U+FB50 to U+FDFF

// Combining Half Marks
// Range: U+FE20 to U+FE2F

// CJK Compatibility Forms
// Range: U+FE30 to U+FE4F

// Small Form Variants
// Range: U+FE50 to U+FE6F

// Arabic Presentation Forms-B
// Range: U+FE70 to U+FEFF

// Halfwidth and Fullwidth Forms
// Range: U+FF00 to U+FFEF

if ( c >= 0xFF21 && c <= 0xFF3A ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0xFF41 && c <= 0xFF5A ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// Specials
// Range: U+FFF0 to U+FFFF

return u;
}

function DecimalToHexString( n ) {
n = Number( n );
var h = "0x";

for ( var i = 3; i >= 0; i-- ) {
if ( n >= Math.pow(16, i) ){
var t = Math.floor( n  / Math.pow(16, i));
n -= t * Math.pow(16, i);
if ( t >= 10 ) {
if ( t == 10 ) {
h += "A";
}
if ( t == 11 ) {
h += "B";
}
if ( t == 12 ) {
h += "C";
}
if ( t == 13 ) {
h += "D";
}
if ( t == 14 ) {
h += "E";
}
if ( t == 15 ) {
h += "F";
}
} else {
h += String( t );
}
} else {
h += "0";
}
}

return h;
}

},

/**
File Name:          15.5.4.12-1.js
ECMA Section:       15.5.4.12 String.prototype.toUpperCase()
Description:

Returns a string equal in length to the length of the result of converting
this object to a string. The result is a string value, not a String object.

Every character of the result is equal to the corresponding character of the
string, unless that character has a Unicode 2.0 uppercase equivalent, in which
case the uppercase equivalent is used instead. (The canonical Unicode 2.0 case
mapping shall be used, which does not depend on implementation or locale.)

Note that the toUpperCase function is intentionally generic; it does not require
that its this value be a String object. Therefore it can be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_5_4_12__4: function() {
var SECTION = "15.5.4.12-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.toUpperCase()";

//writeHeaderToLog( SECTION + " "+ TITLE);

// Cyrillic (part)
// Range: U+0400 to U+04FF
for ( var i = 0x0400; i <= 0x047F; i++ ) {
var U =new Unicode( i );
/*
new TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toUpperCase()",
U.upper,
eval("var s = new String( String.fromCharCode("+i+") ); s.toUpperCase()") );
*/
this.TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toUpperCase().charCodeAt(0)",
U.upper,
eval("var s = new String( String.fromCharCode(i) ); s.toUpperCase().charCodeAt(0)") );

}

//test();

function MyObject( value ) {
this.value = value;
this.substring = String.prototype.substring;
this.toString = new Function ( "return this.value+''" );
}
function Unicode( c ) {
var u = GetUnicodeValues( c );
this.upper = u[0];
this.lower = u[1]
return this;
}
function GetUnicodeValues( c ) {
var u = new Array();

u[0] = c;
u[1] = c;

// upper case Basic Latin

if ( c >= 0x0041 && c <= 0x005A) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Basic Latin
if ( c >= 0x0061 && c <= 0x007a ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// upper case Latin-1 Supplement
if ( (c >= 0x00C0 && c <= 0x00D6) || (c >= 0x00D8 && c<=0x00DE) ) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Latin-1 Supplement
if ( (c >= 0x00E0 && c <= 0x00F6) || (c >= 0x00F8 && c <= 0x00FE) ) {
u[0] = c - 32;
u[1] = c;
return u;
}
if ( c == 0x00FF ) {
u[0] = 0x0178;
u[1] = c;
return u;
}
// Latin Extended A
if ( (c >= 0x0100 && c < 0x0138) || (c > 0x0149 && c < 0x0178) ) {
// special case for capital I
if ( c == 0x0130 ) {
u[0] = c;
u[1] = 0x0069;
return u;
}
if ( c == 0x0131 ) {
u[0] = 0x0049;
u[1] = c;
return u;
}

if ( c % 2 == 0 ) {
// if it's even, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's odd, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x0178 ) {
u[0] = c;
u[1] = 0x00FF;
return u;
}

if ( (c >= 0x0139 && c < 0x0149) || (c > 0x0178 && c < 0x017F) ) {
if ( c % 2 == 1 ) {
// if it's odd, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's even, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x017F ) {
u[0] = 0x0053;
u[1] = c;
}

// Latin Extended B
// need to improve this set

if ( c >= 0x0200 && c <= 0x0217 ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c+1;
} else {
u[0] = c-1;
u[1] = c;
}
return u;
}

// Latin Extended Additional
// Range: U+1E00 to U+1EFF
// http://www.unicode.org/Unicode.charts/glyphless/U1E00.html

// Spacing Modifier Leters
// Range: U+02B0 to U+02FF

// Combining Diacritical Marks
// Range: U+0300 to U+036F

// skip Greek for now
// Greek
// Range: U+0370 to U+03FF

// Cyrillic
// Range: U+0400 to U+04FF

if ( (c >= 0x0401 && c <= 0x040C) || ( c>= 0x040E && c <= 0x040F ) ) {
u[0] = c;
u[1] = c + 80;
return u;
}


if ( c >= 0x0410  && c <= 0x042F ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0x0430 && c<= 0x044F ) {
u[0] = c - 32;
u[1] = c;
return u;

}
if ( (c >= 0x0451 && c <= 0x045C) || (c >=0x045E && c<= 0x045F) ) {
u[0] = c -80;
u[1] = c;
return u;
}

if ( c >= 0x0460 && c <= 0x047F ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c +1;
} else {
u[0] = c - 1;
u[1] = c;
}
return u;
}

// Armenian
// Range: U+0530 to U+058F
if ( c >= 0x0531 && c <= 0x0556 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x0561 && c < 0x0587 ) {
u[0] = c - 48;
u[1] = c;
return u;
}

// Hebrew
// Range: U+0590 to U+05FF


// Arabic
// Range: U+0600 to U+06FF

// Devanagari
// Range: U+0900 to U+097F


// Bengali
// Range: U+0980 to U+09FF


// Gurmukhi
// Range: U+0A00 to U+0A7F


// Gujarati
// Range: U+0A80 to U+0AFF


// Oriya
// Range: U+0B00 to U+0B7F
// no capital / lower case


// Tamil
// Range: U+0B80 to U+0BFF
// no capital / lower case


// Telugu
// Range: U+0C00 to U+0C7F
// no capital / lower case


// Kannada
// Range: U+0C80 to U+0CFF
// no capital / lower case


// Malayalam
// Range: U+0D00 to U+0D7F

// Thai
// Range: U+0E00 to U+0E7F


// Lao
// Range: U+0E80 to U+0EFF


// Tibetan
// Range: U+0F00 to U+0FBF

// Georgian
// Range: U+10A0 to U+10F0
if ( c >= 0x10A0 && c <= 0x10C5 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x10D0 && c <= 0x10F5 ) {
u[0] = c;
u[1] = c;
return u;
}

// Hangul Jamo
// Range: U+1100 to U+11FF

// Greek Extended
// Range: U+1F00 to U+1FFF
// skip for now


// General Punctuation
// Range: U+2000 to U+206F

// Superscripts and Subscripts
// Range: U+2070 to U+209F

// Currency Symbols
// Range: U+20A0 to U+20CF


// Combining Diacritical Marks for Symbols
// Range: U+20D0 to U+20FF
// skip for now


// Number Forms
// Range: U+2150 to U+218F
// skip for now


// Arrows
// Range: U+2190 to U+21FF

// Mathematical Operators
// Range: U+2200 to U+22FF

// Miscellaneous Technical
// Range: U+2300 to U+23FF

// Control Pictures
// Range: U+2400 to U+243F

// Optical Character Recognition
// Range: U+2440 to U+245F

// Enclosed Alphanumerics
// Range: U+2460 to U+24FF

// Box Drawing
// Range: U+2500 to U+257F

// Block Elements
// Range: U+2580 to U+259F

// Geometric Shapes
// Range: U+25A0 to U+25FF

// Miscellaneous Symbols
// Range: U+2600 to U+26FF

// Dingbats
// Range: U+2700 to U+27BF

// CJK Symbols and Punctuation
// Range: U+3000 to U+303F

// Hiragana
// Range: U+3040 to U+309F

// Katakana
// Range: U+30A0 to U+30FF

// Bopomofo
// Range: U+3100 to U+312F

// Hangul Compatibility Jamo
// Range: U+3130 to U+318F

// Kanbun
// Range: U+3190 to U+319F


// Enclosed CJK Letters and Months
// Range: U+3200 to U+32FF

// CJK Compatibility
// Range: U+3300 to U+33FF

// Hangul Syllables
// Range: U+AC00 to U+D7A3

// High Surrogates
// Range: U+D800 to U+DB7F

// Private Use High Surrogates
// Range: U+DB80 to U+DBFF

// Low Surrogates
// Range: U+DC00 to U+DFFF

// Private Use Area
// Range: U+E000 to U+F8FF

// CJK Compatibility Ideographs
// Range: U+F900 to U+FAFF

// Alphabetic Presentation Forms
// Range: U+FB00 to U+FB4F

// Arabic Presentation Forms-A
// Range: U+FB50 to U+FDFF

// Combining Half Marks
// Range: U+FE20 to U+FE2F

// CJK Compatibility Forms
// Range: U+FE30 to U+FE4F

// Small Form Variants
// Range: U+FE50 to U+FE6F

// Arabic Presentation Forms-B
// Range: U+FE70 to U+FEFF

// Halfwidth and Fullwidth Forms
// Range: U+FF00 to U+FFEF

if ( c >= 0xFF21 && c <= 0xFF3A ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0xFF41 && c <= 0xFF5A ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// Specials
// Range: U+FFF0 to U+FFFF

return u;
}

function DecimalToHexString( n ) {
n = Number( n );
var h = "0x";

for ( var i = 3; i >= 0; i-- ) {
if ( n >= Math.pow(16, i) ){
var t = Math.floor( n  / Math.pow(16, i));
n -= t * Math.pow(16, i);
if ( t >= 10 ) {
if ( t == 10 ) {
h += "A";
}
if ( t == 11 ) {
h += "B";
}
if ( t == 12 ) {
h += "C";
}
if ( t == 13 ) {
h += "D";
}
if ( t == 14 ) {
h += "E";
}
if ( t == 15 ) {
h += "F";
}
} else {
h += String( t );
}
} else {
h += "0";
}
}

return h;
}

},

/**
File Name:          15.5.4.12-1.js
ECMA Section:       15.5.4.12 String.prototype.toUpperCase()
Description:

Returns a string equal in length to the length of the result of converting
this object to a string. The result is a string value, not a String object.

Every character of the result is equal to the corresponding character of the
string, unless that character has a Unicode 2.0 uppercase equivalent, in which
case the uppercase equivalent is used instead. (The canonical Unicode 2.0 case
mapping shall be used, which does not depend on implementation or locale.)

Note that the toUpperCase function is intentionally generic; it does not require
that its this value be a String object. Therefore it can be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_5_4_12__4_WORKS: function() {
var SECTION = "15.5.4.12-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.toUpperCase()";

//writeHeaderToLog( SECTION + " "+ TITLE);

// Cyrillic (part)
// Range: U+0400 to U+04FF
for ( var i = 0x0400; i <= 0x047F; i++ ) {
var U =new Unicode( i );
/*
new TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toUpperCase()",
U.upper,
eval("var s = new String( String.fromCharCode("+i+") ); s.toUpperCase()") );
*/
this.TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toUpperCase().charCodeAt(0)",
U.upper,
eval("var s = new String( String.fromCharCode("+i+") ); s.toUpperCase().charCodeAt(0)") );

}

//test();

function MyObject( value ) {
this.value = value;
this.substring = String.prototype.substring;
this.toString = new Function ( "return this.value+''" );
}
function Unicode( c ) {
var u = GetUnicodeValues( c );
this.upper = u[0];
this.lower = u[1]
return this;
}
function GetUnicodeValues( c ) {
var u = new Array();

u[0] = c;
u[1] = c;

// upper case Basic Latin

if ( c >= 0x0041 && c <= 0x005A) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Basic Latin
if ( c >= 0x0061 && c <= 0x007a ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// upper case Latin-1 Supplement
if ( (c >= 0x00C0 && c <= 0x00D6) || (c >= 0x00D8 && c<=0x00DE) ) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Latin-1 Supplement
if ( (c >= 0x00E0 && c <= 0x00F6) || (c >= 0x00F8 && c <= 0x00FE) ) {
u[0] = c - 32;
u[1] = c;
return u;
}
if ( c == 0x00FF ) {
u[0] = 0x0178;
u[1] = c;
return u;
}
// Latin Extended A
if ( (c >= 0x0100 && c < 0x0138) || (c > 0x0149 && c < 0x0178) ) {
// special case for capital I
if ( c == 0x0130 ) {
u[0] = c;
u[1] = 0x0069;
return u;
}
if ( c == 0x0131 ) {
u[0] = 0x0049;
u[1] = c;
return u;
}

if ( c % 2 == 0 ) {
// if it's even, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's odd, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x0178 ) {
u[0] = c;
u[1] = 0x00FF;
return u;
}

if ( (c >= 0x0139 && c < 0x0149) || (c > 0x0178 && c < 0x017F) ) {
if ( c % 2 == 1 ) {
// if it's odd, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's even, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x017F ) {
u[0] = 0x0053;
u[1] = c;
}

// Latin Extended B
// need to improve this set

if ( c >= 0x0200 && c <= 0x0217 ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c+1;
} else {
u[0] = c-1;
u[1] = c;
}
return u;
}

// Latin Extended Additional
// Range: U+1E00 to U+1EFF
// http://www.unicode.org/Unicode.charts/glyphless/U1E00.html

// Spacing Modifier Leters
// Range: U+02B0 to U+02FF

// Combining Diacritical Marks
// Range: U+0300 to U+036F

// skip Greek for now
// Greek
// Range: U+0370 to U+03FF

// Cyrillic
// Range: U+0400 to U+04FF

if ( (c >= 0x0401 && c <= 0x040C) || ( c>= 0x040E && c <= 0x040F ) ) {
u[0] = c;
u[1] = c + 80;
return u;
}


if ( c >= 0x0410  && c <= 0x042F ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0x0430 && c<= 0x044F ) {
u[0] = c - 32;
u[1] = c;
return u;

}
if ( (c >= 0x0450 && c <= 0x045C) || (c >=0x045D && c<= 0x045F) ) {
u[0] = c -80;
u[1] = c;
return u;
}

if ( c >= 0x0460 && c <= 0x047F ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c +1;
} else {
u[0] = c - 1;
u[1] = c;
}
return u;
}

// Armenian
// Range: U+0530 to U+058F
if ( c >= 0x0531 && c <= 0x0556 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x0561 && c < 0x0587 ) {
u[0] = c - 48;
u[1] = c;
return u;
}

// Hebrew
// Range: U+0590 to U+05FF


// Arabic
// Range: U+0600 to U+06FF

// Devanagari
// Range: U+0900 to U+097F


// Bengali
// Range: U+0980 to U+09FF


// Gurmukhi
// Range: U+0A00 to U+0A7F


// Gujarati
// Range: U+0A80 to U+0AFF


// Oriya
// Range: U+0B00 to U+0B7F
// no capital / lower case


// Tamil
// Range: U+0B80 to U+0BFF
// no capital / lower case


// Telugu
// Range: U+0C00 to U+0C7F
// no capital / lower case


// Kannada
// Range: U+0C80 to U+0CFF
// no capital / lower case


// Malayalam
// Range: U+0D00 to U+0D7F

// Thai
// Range: U+0E00 to U+0E7F


// Lao
// Range: U+0E80 to U+0EFF


// Tibetan
// Range: U+0F00 to U+0FBF

// Georgian
// Range: U+10A0 to U+10F0
if ( c >= 0x10A0 && c <= 0x10C5 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x10D0 && c <= 0x10F5 ) {
u[0] = c;
u[1] = c;
return u;
}

// Hangul Jamo
// Range: U+1100 to U+11FF

// Greek Extended
// Range: U+1F00 to U+1FFF
// skip for now


// General Punctuation
// Range: U+2000 to U+206F

// Superscripts and Subscripts
// Range: U+2070 to U+209F

// Currency Symbols
// Range: U+20A0 to U+20CF


// Combining Diacritical Marks for Symbols
// Range: U+20D0 to U+20FF
// skip for now


// Number Forms
// Range: U+2150 to U+218F
// skip for now


// Arrows
// Range: U+2190 to U+21FF

// Mathematical Operators
// Range: U+2200 to U+22FF

// Miscellaneous Technical
// Range: U+2300 to U+23FF

// Control Pictures
// Range: U+2400 to U+243F

// Optical Character Recognition
// Range: U+2440 to U+245F

// Enclosed Alphanumerics
// Range: U+2460 to U+24FF

// Box Drawing
// Range: U+2500 to U+257F

// Block Elements
// Range: U+2580 to U+259F

// Geometric Shapes
// Range: U+25A0 to U+25FF

// Miscellaneous Symbols
// Range: U+2600 to U+26FF

// Dingbats
// Range: U+2700 to U+27BF

// CJK Symbols and Punctuation
// Range: U+3000 to U+303F

// Hiragana
// Range: U+3040 to U+309F

// Katakana
// Range: U+30A0 to U+30FF

// Bopomofo
// Range: U+3100 to U+312F

// Hangul Compatibility Jamo
// Range: U+3130 to U+318F

// Kanbun
// Range: U+3190 to U+319F


// Enclosed CJK Letters and Months
// Range: U+3200 to U+32FF

// CJK Compatibility
// Range: U+3300 to U+33FF

// Hangul Syllables
// Range: U+AC00 to U+D7A3

// High Surrogates
// Range: U+D800 to U+DB7F

// Private Use High Surrogates
// Range: U+DB80 to U+DBFF

// Low Surrogates
// Range: U+DC00 to U+DFFF

// Private Use Area
// Range: U+E000 to U+F8FF

// CJK Compatibility Ideographs
// Range: U+F900 to U+FAFF

// Alphabetic Presentation Forms
// Range: U+FB00 to U+FB4F

// Arabic Presentation Forms-A
// Range: U+FB50 to U+FDFF

// Combining Half Marks
// Range: U+FE20 to U+FE2F

// CJK Compatibility Forms
// Range: U+FE30 to U+FE4F

// Small Form Variants
// Range: U+FE50 to U+FE6F

// Arabic Presentation Forms-B
// Range: U+FE70 to U+FEFF

// Halfwidth and Fullwidth Forms
// Range: U+FF00 to U+FFEF

if ( c >= 0xFF21 && c <= 0xFF3A ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0xFF41 && c <= 0xFF5A ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// Specials
// Range: U+FFF0 to U+FFFF

return u;
}

function DecimalToHexString( n ) {
n = Number( n );
var h = "0x";

for ( var i = 3; i >= 0; i-- ) {
if ( n >= Math.pow(16, i) ){
var t = Math.floor( n  / Math.pow(16, i));
n -= t * Math.pow(16, i);
if ( t >= 10 ) {
if ( t == 10 ) {
h += "A";
}
if ( t == 11 ) {
h += "B";
}
if ( t == 12 ) {
h += "C";
}
if ( t == 13 ) {
h += "D";
}
if ( t == 14 ) {
h += "E";
}
if ( t == 15 ) {
h += "F";
}
} else {
h += String( t );
}
} else {
h += "0";
}
}

return h;
}

},

/**
File Name:          15.5.4.12-1.js
ECMA Section:       15.5.4.12 String.prototype.toUpperCase()
Description:

Returns a string equal in length to the length of the result of converting
this object to a string. The result is a string value, not a String object.

Every character of the result is equal to the corresponding character of the
string, unless that character has a Unicode 2.0 uppercase equivalent, in which
case the uppercase equivalent is used instead. (The canonical Unicode 2.0 case
mapping shall be used, which does not depend on implementation or locale.)

Note that the toUpperCase function is intentionally generic; it does not require
that its this value be a String object. Therefore it can be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_5_4_12__5: function() {
var SECTION = "15.5.4.12-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.toUpperCase()";

//writeHeaderToLog( SECTION + " "+ TITLE);

// Armenian
// Range: U+0530 to U+058F
for ( var i = 0x0530; i <= 0x058F; i++ ) {
var U = new Unicode( i );
/*
new TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toUpperCase()",
String.fromCharCode(U.upper),
eval("var s = new String( String.fromCharCode("+i+") ); s.toUpperCase()") );
*/
this.TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toUpperCase().charCodeAt(0)",
U.upper,
eval("var s = new String( String.fromCharCode(i) ); s.toUpperCase().charCodeAt(0)") );

}

//test();

function MyObject( value ) {
this.value = value;
this.substring = String.prototype.substring;
this.toString = new Function ( "return this.value+''" );
}
function Unicode( c ) {
var u = GetUnicodeValues( c );
this.upper = u[0];
this.lower = u[1]
return this;
}
function GetUnicodeValues( c ) {
var u = new Array();

u[0] = c;
u[1] = c;

// upper case Basic Latin

if ( c >= 0x0041 && c <= 0x005A) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Basic Latin
if ( c >= 0x0061 && c <= 0x007a ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// upper case Latin-1 Supplement
if ( (c >= 0x00C0 && c <= 0x00D6) || (c >= 0x00D8 && c<=0x00DE) ) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Latin-1 Supplement
if ( (c >= 0x00E0 && c <= 0x00F6) || (c >= 0x00F8 && c <= 0x00FE) ) {
u[0] = c - 32;
u[1] = c;
return u;
}
if ( c == 0x00FF ) {
u[0] = 0x0178;
u[1] = c;
return u;
}
// Latin Extended A
if ( (c >= 0x0100 && c < 0x0138) || (c > 0x0149 && c < 0x0178) ) {
// special case for capital I
if ( c == 0x0130 ) {
u[0] = c;
u[1] = 0x0069;
return u;
}
if ( c == 0x0131 ) {
u[0] = 0x0049;
u[1] = c;
return u;
}

if ( c % 2 == 0 ) {
// if it's even, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's odd, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x0178 ) {
u[0] = c;
u[1] = 0x00FF;
return u;
}

if ( (c >= 0x0139 && c < 0x0149) || (c > 0x0178 && c < 0x017F) ) {
if ( c % 2 == 1 ) {
// if it's odd, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's even, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x017F ) {
u[0] = 0x0053;
u[1] = c;
}

// Latin Extended B
// need to improve this set

if ( c >= 0x0200 && c <= 0x0217 ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c+1;
} else {
u[0] = c-1;
u[1] = c;
}
return u;
}

// Latin Extended Additional
// Range: U+1E00 to U+1EFF
// http://www.unicode.org/Unicode.charts/glyphless/U1E00.html

// Spacing Modifier Leters
// Range: U+02B0 to U+02FF

// Combining Diacritical Marks
// Range: U+0300 to U+036F

// skip Greek for now
// Greek
// Range: U+0370 to U+03FF

// Cyrillic
// Range: U+0400 to U+04FF

if ( (c >= 0x0401 && c <= 0x040C) || ( c>= 0x040E && c <= 0x040F ) ) {
u[0] = c;
u[1] = c + 80;
return u;
}


if ( c >= 0x0410  && c <= 0x042F ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0x0430 && c<= 0x044F ) {
u[0] = c - 32;
u[1] = c;
return u;

}
if ( (c >= 0x0451 && c <= 0x045C) || (c >=0x045E && c<= 0x045F) ) {
u[0] = c -80;
u[1] = c;
return u;
}

if ( c >= 0x0460 && c <= 0x047F ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c +1;
} else {
u[0] = c - 1;
u[1] = c;
}
return u;
}

// Armenian
// Range: U+0530 to U+058F
if ( c >= 0x0531 && c <= 0x0556 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x0561 && c < 0x0587 ) {
u[0] = c - 48;
u[1] = c;
return u;
}

// Hebrew
// Range: U+0590 to U+05FF


// Arabic
// Range: U+0600 to U+06FF

// Devanagari
// Range: U+0900 to U+097F


// Bengali
// Range: U+0980 to U+09FF


// Gurmukhi
// Range: U+0A00 to U+0A7F


// Gujarati
// Range: U+0A80 to U+0AFF


// Oriya
// Range: U+0B00 to U+0B7F
// no capital / lower case


// Tamil
// Range: U+0B80 to U+0BFF
// no capital / lower case


// Telugu
// Range: U+0C00 to U+0C7F
// no capital / lower case


// Kannada
// Range: U+0C80 to U+0CFF
// no capital / lower case


// Malayalam
// Range: U+0D00 to U+0D7F

// Thai
// Range: U+0E00 to U+0E7F


// Lao
// Range: U+0E80 to U+0EFF


// Tibetan
// Range: U+0F00 to U+0FBF

// Georgian
// Range: U+10A0 to U+10F0
if ( c >= 0x10A0 && c <= 0x10C5 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x10D0 && c <= 0x10F5 ) {
u[0] = c;
u[1] = c;
return u;
}

// Hangul Jamo
// Range: U+1100 to U+11FF

// Greek Extended
// Range: U+1F00 to U+1FFF
// skip for now


// General Punctuation
// Range: U+2000 to U+206F

// Superscripts and Subscripts
// Range: U+2070 to U+209F

// Currency Symbols
// Range: U+20A0 to U+20CF


// Combining Diacritical Marks for Symbols
// Range: U+20D0 to U+20FF
// skip for now


// Number Forms
// Range: U+2150 to U+218F
// skip for now


// Arrows
// Range: U+2190 to U+21FF

// Mathematical Operators
// Range: U+2200 to U+22FF

// Miscellaneous Technical
// Range: U+2300 to U+23FF

// Control Pictures
// Range: U+2400 to U+243F

// Optical Character Recognition
// Range: U+2440 to U+245F

// Enclosed Alphanumerics
// Range: U+2460 to U+24FF

// Box Drawing
// Range: U+2500 to U+257F

// Block Elements
// Range: U+2580 to U+259F

// Geometric Shapes
// Range: U+25A0 to U+25FF

// Miscellaneous Symbols
// Range: U+2600 to U+26FF

// Dingbats
// Range: U+2700 to U+27BF

// CJK Symbols and Punctuation
// Range: U+3000 to U+303F

// Hiragana
// Range: U+3040 to U+309F

// Katakana
// Range: U+30A0 to U+30FF

// Bopomofo
// Range: U+3100 to U+312F

// Hangul Compatibility Jamo
// Range: U+3130 to U+318F

// Kanbun
// Range: U+3190 to U+319F


// Enclosed CJK Letters and Months
// Range: U+3200 to U+32FF

// CJK Compatibility
// Range: U+3300 to U+33FF

// Hangul Syllables
// Range: U+AC00 to U+D7A3

// High Surrogates
// Range: U+D800 to U+DB7F

// Private Use High Surrogates
// Range: U+DB80 to U+DBFF

// Low Surrogates
// Range: U+DC00 to U+DFFF

// Private Use Area
// Range: U+E000 to U+F8FF

// CJK Compatibility Ideographs
// Range: U+F900 to U+FAFF

// Alphabetic Presentation Forms
// Range: U+FB00 to U+FB4F

// Arabic Presentation Forms-A
// Range: U+FB50 to U+FDFF

// Combining Half Marks
// Range: U+FE20 to U+FE2F

// CJK Compatibility Forms
// Range: U+FE30 to U+FE4F

// Small Form Variants
// Range: U+FE50 to U+FE6F

// Arabic Presentation Forms-B
// Range: U+FE70 to U+FEFF

// Halfwidth and Fullwidth Forms
// Range: U+FF00 to U+FFEF

if ( c >= 0xFF21 && c <= 0xFF3A ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0xFF41 && c <= 0xFF5A ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// Specials
// Range: U+FFF0 to U+FFFF

return u;
}

function DecimalToHexString( n ) {
n = Number( n );
var h = "0x";

for ( var i = 3; i >= 0; i-- ) {
if ( n >= Math.pow(16, i) ){
var t = Math.floor( n  / Math.pow(16, i));
n -= t * Math.pow(16, i);
if ( t >= 10 ) {
if ( t == 10 ) {
h += "A";
}
if ( t == 11 ) {
h += "B";
}
if ( t == 12 ) {
h += "C";
}
if ( t == 13 ) {
h += "D";
}
if ( t == 14 ) {
h += "E";
}
if ( t == 15 ) {
h += "F";
}
} else {
h += String( t );
}
} else {
h += "0";
}
}

return h;
}

},

/**
File Name:          15.5.4.12-1.js
ECMA Section:       15.5.4.12 String.prototype.toUpperCase()
Description:

Returns a string equal in length to the length of the result of converting
this object to a string. The result is a string value, not a String object.

Every character of the result is equal to the corresponding character of the
string, unless that character has a Unicode 2.0 uppercase equivalent, in which
case the uppercase equivalent is used instead. (The canonical Unicode 2.0 case
mapping shall be used, which does not depend on implementation or locale.)

Note that the toUpperCase function is intentionally generic; it does not require
that its this value be a String object. Therefore it can be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_5_4_12__5_WORKS: function() {
var SECTION = "15.5.4.12-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.toUpperCase()";

//writeHeaderToLog( SECTION + " "+ TITLE);

// Armenian
// Range: U+0530 to U+058F
for ( var i = 0x0530; i <= 0x058F; i++ ) {
var U = new Unicode( i );

if (i == 0x0587) {
continue;
}

/*
new TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toUpperCase()",
String.fromCharCode(U.upper),
eval("var s = new String( String.fromCharCode("+i+") ); s.toUpperCase()") );
*/
this.TestCase(   SECTION,
"var s = new String( String.fromCharCode("+i+") ); s.toUpperCase().charCodeAt(0)",
U.upper,
eval("var s = new String( String.fromCharCode("+i+") ); s.toUpperCase().charCodeAt(0)") );
//TODO: debugging
//temp = "var s = new String( String.fromCharCode("+i+") ); s.toUpperCase().charCodeAt(0)";
//vjo.sysout.println("0x" + i.toString(16) + " exp: " + U.upper +
//                   " actual: " + eval(temp));
}

//test();

function MyObject( value ) {
this.value = value;
this.substring = String.prototype.substring;
this.toString = new Function ( "return this.value+''" );
}
function Unicode( c ) {
var u = GetUnicodeValues( c );
this.upper = u[0];
this.lower = u[1]
return this;
}
function GetUnicodeValues( c ) {
var u = new Array();

u[0] = c;
u[1] = c;

// upper case Basic Latin

if ( c >= 0x0041 && c <= 0x005A) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Basic Latin
if ( c >= 0x0061 && c <= 0x007a ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// upper case Latin-1 Supplement
if ( (c >= 0x00C0 && c <= 0x00D6) || (c >= 0x00D8 && c<=0x00DE) ) {
u[0] = c;
u[1] = c + 32;
return u;
}

// lower case Latin-1 Supplement
if ( (c >= 0x00E0 && c <= 0x00F6) || (c >= 0x00F8 && c <= 0x00FE) ) {
u[0] = c - 32;
u[1] = c;
return u;
}
if ( c == 0x00FF ) {
u[0] = 0x0178;
u[1] = c;
return u;
}
// Latin Extended A
if ( (c >= 0x0100 && c < 0x0138) || (c > 0x0149 && c < 0x0178) ) {
// special case for capital I
if ( c == 0x0130 ) {
u[0] = c;
u[1] = 0x0069;
return u;
}
if ( c == 0x0131 ) {
u[0] = 0x0049;
u[1] = c;
return u;
}

if ( c % 2 == 0 ) {
// if it's even, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's odd, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x0178 ) {
u[0] = c;
u[1] = 0x00FF;
return u;
}

if ( (c >= 0x0139 && c < 0x0149) || (c > 0x0178 && c < 0x017F) ) {
if ( c % 2 == 1 ) {
// if it's odd, it's a capital and the lower case is c +1
u[0] = c;
u[1] = c+1;
} else {
// if it's even, it's a lower case and upper case is c-1
u[0] = c-1;
u[1] = c;
}
return u;
}
if ( c == 0x017F ) {
u[0] = 0x0053;
u[1] = c;
}

// Latin Extended B
// need to improve this set

if ( c >= 0x0200 && c <= 0x0217 ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c+1;
} else {
u[0] = c-1;
u[1] = c;
}
return u;
}

// Latin Extended Additional
// Range: U+1E00 to U+1EFF
// http://www.unicode.org/Unicode.charts/glyphless/U1E00.html

// Spacing Modifier Leters
// Range: U+02B0 to U+02FF

// Combining Diacritical Marks
// Range: U+0300 to U+036F

// skip Greek for now
// Greek
// Range: U+0370 to U+03FF

// Cyrillic
// Range: U+0400 to U+04FF

if ( (c >= 0x0401 && c <= 0x040C) || ( c>= 0x040E && c <= 0x040F ) ) {
u[0] = c;
u[1] = c + 80;
return u;
}


if ( c >= 0x0410  && c <= 0x042F ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0x0430 && c<= 0x044F ) {
u[0] = c - 32;
u[1] = c;
return u;

}
if ( (c >= 0x0451 && c <= 0x045C) || (c >=0x045E && c<= 0x045F) ) {
u[0] = c -80;
u[1] = c;
return u;
}

if ( c >= 0x0460 && c <= 0x047F ) {
if ( c % 2 == 0 ) {
u[0] = c;
u[1] = c +1;
} else {
u[0] = c - 1;
u[1] = c;
}
return u;
}

// Armenian
// Range: U+0530 to U+058F
if ( c >= 0x0531 && c <= 0x0556 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x0561 && c < 0x0587 ) {
u[0] = c - 48;
u[1] = c;
return u;
}

// Hebrew
// Range: U+0590 to U+05FF


// Arabic
// Range: U+0600 to U+06FF

// Devanagari
// Range: U+0900 to U+097F


// Bengali
// Range: U+0980 to U+09FF


// Gurmukhi
// Range: U+0A00 to U+0A7F


// Gujarati
// Range: U+0A80 to U+0AFF


// Oriya
// Range: U+0B00 to U+0B7F
// no capital / lower case


// Tamil
// Range: U+0B80 to U+0BFF
// no capital / lower case


// Telugu
// Range: U+0C00 to U+0C7F
// no capital / lower case


// Kannada
// Range: U+0C80 to U+0CFF
// no capital / lower case


// Malayalam
// Range: U+0D00 to U+0D7F

// Thai
// Range: U+0E00 to U+0E7F


// Lao
// Range: U+0E80 to U+0EFF


// Tibetan
// Range: U+0F00 to U+0FBF

// Georgian
// Range: U+10A0 to U+10F0
if ( c >= 0x10A0 && c <= 0x10C5 ) {
u[0] = c;
u[1] = c + 48;
return u;
}
if ( c >= 0x10D0 && c <= 0x10F5 ) {
u[0] = c;
u[1] = c;
return u;
}

// Hangul Jamo
// Range: U+1100 to U+11FF

// Greek Extended
// Range: U+1F00 to U+1FFF
// skip for now


// General Punctuation
// Range: U+2000 to U+206F

// Superscripts and Subscripts
// Range: U+2070 to U+209F

// Currency Symbols
// Range: U+20A0 to U+20CF


// Combining Diacritical Marks for Symbols
// Range: U+20D0 to U+20FF
// skip for now


// Number Forms
// Range: U+2150 to U+218F
// skip for now


// Arrows
// Range: U+2190 to U+21FF

// Mathematical Operators
// Range: U+2200 to U+22FF

// Miscellaneous Technical
// Range: U+2300 to U+23FF

// Control Pictures
// Range: U+2400 to U+243F

// Optical Character Recognition
// Range: U+2440 to U+245F

// Enclosed Alphanumerics
// Range: U+2460 to U+24FF

// Box Drawing
// Range: U+2500 to U+257F

// Block Elements
// Range: U+2580 to U+259F

// Geometric Shapes
// Range: U+25A0 to U+25FF

// Miscellaneous Symbols
// Range: U+2600 to U+26FF

// Dingbats
// Range: U+2700 to U+27BF

// CJK Symbols and Punctuation
// Range: U+3000 to U+303F

// Hiragana
// Range: U+3040 to U+309F

// Katakana
// Range: U+30A0 to U+30FF

// Bopomofo
// Range: U+3100 to U+312F

// Hangul Compatibility Jamo
// Range: U+3130 to U+318F

// Kanbun
// Range: U+3190 to U+319F


// Enclosed CJK Letters and Months
// Range: U+3200 to U+32FF

// CJK Compatibility
// Range: U+3300 to U+33FF

// Hangul Syllables
// Range: U+AC00 to U+D7A3

// High Surrogates
// Range: U+D800 to U+DB7F

// Private Use High Surrogates
// Range: U+DB80 to U+DBFF

// Low Surrogates
// Range: U+DC00 to U+DFFF

// Private Use Area
// Range: U+E000 to U+F8FF

// CJK Compatibility Ideographs
// Range: U+F900 to U+FAFF

// Alphabetic Presentation Forms
// Range: U+FB00 to U+FB4F

// Arabic Presentation Forms-A
// Range: U+FB50 to U+FDFF

// Combining Half Marks
// Range: U+FE20 to U+FE2F

// CJK Compatibility Forms
// Range: U+FE30 to U+FE4F

// Small Form Variants
// Range: U+FE50 to U+FE6F

// Arabic Presentation Forms-B
// Range: U+FE70 to U+FEFF

// Halfwidth and Fullwidth Forms
// Range: U+FF00 to U+FFEF

if ( c >= 0xFF21 && c <= 0xFF3A ) {
u[0] = c;
u[1] = c + 32;
return u;
}

if ( c >= 0xFF41 && c <= 0xFF5A ) {
u[0] = c - 32;
u[1] = c;
return u;
}

// Specials
// Range: U+FFF0 to U+FFFF

return u;
}

function DecimalToHexString( n ) {
n = Number( n );
var h = "0x";

for ( var i = 3; i >= 0; i-- ) {
if ( n >= Math.pow(16, i) ){
var t = Math.floor( n  / Math.pow(16, i));
n -= t * Math.pow(16, i);
if ( t >= 10 ) {
if ( t == 10 ) {
h += "A";
}
if ( t == 11 ) {
h += "B";
}
if ( t == 12 ) {
h += "C";
}
if ( t == 13 ) {
h += "D";
}
if ( t == 14 ) {
h += "E";
}
if ( t == 15 ) {
h += "F";
}
} else {
h += String( t );
}
} else {
h += "0";
}
}

return h;
}

},

/**
File Name:          15.5.4.2-1.js
ECMA Section:       15.5.4.2 String.prototype.toString()

Description:        Returns this string value.  Note that, for a String
object, the toString() method happens to return the same
thing as the valueOf() method.

The toString function is not generic; it generates a
runtime error if its this value is not a String object.
Therefore it connot be transferred to the other kinds of
objects for use as a method.

Author:             christine@netscape.com
Date:               1 october 1997
*/
test_15_5_4_2__1: function() {
var SECTION = "15.5.4.2-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.toString";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,   "String.prototype.toString()",        "",     String.prototype.toString() );
this.TestCase( SECTION,   "(new String()).toString()",          "",     (new String()).toString() );
this.TestCase( SECTION,   "(new String(\"\")).toString()",      "",     (new String("")).toString() );
this.TestCase( SECTION,   "(new String( String() )).toString()","",    (new String(String())).toString() );
this.TestCase( SECTION,  "(new String( \"h e l l o\" )).toString()",       "h e l l o",    (new String("h e l l o")).toString() );
this.TestCase( SECTION,   "(new String( 0 )).toString()",       "0",    (new String(0)).toString() );

//test();

},

/**
File Name:          15.5.4.2-2-n.js
ECMA Section:       15.5.4.2 String.prototype.toString()

Description:        Returns this string value.  Note that, for a String
object, the toString() method happens to return the same
thing as the valueOf() method.

The toString function is not generic; it generates a
runtime error if its this value is not a String object.
Therefore it connot be transferred to the other kinds of
objects for use as a method.

Author:             christine@netscape.com
Date:               1 october 1997
*/
test_15_5_4_2__2__n: function() {
var SECTION = "15.5.4.2-3-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.toString";

//writeHeaderToLog( SECTION + " "+ TITLE);

var DESCRIPTION = "var tostr=String.prototype.toString; astring=new Number(); astring.toString = tostr; astring.toString()";
var EXPECTED = "error";

this.TestCase( SECTION,
"var tostr=String.prototype.toString; astring=new Number(); astring.toString = tostr; astring.toString()",
"error",
eval("var tostr=String.prototype.toString; astring=new Number(); astring.toString = tostr; astring.toString()") );

//test();

},

/**
File Name:          15.5.4.2-2-n.js
ECMA Section:       15.5.4.2 String.prototype.toString()

Description:        Returns this string value.  Note that, for a String
object, the toString() method happens to return the same
thing as the valueOf() method.

The toString function is not generic; it generates a
runtime error if its this value is not a String object.
Therefore it connot be transferred to the other kinds of
objects for use as a method.

Author:             christine@netscape.com
Date:               1 october 1997
*/
test_15_5_4_2__2__n_WORKS: function() {
var SECTION = "15.5.4.2-3-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.toString";

//writeHeaderToLog( SECTION + " "+ TITLE);

var DESCRIPTION = "var tostr=String.prototype.toString; astring=new Number(); astring.toString = tostr; astring.toString()";
var EXPECTED = "error";

try {
this.TestCase( SECTION,
"var tostr=String.prototype.toString; astring=new Number(); astring.toString = tostr; astring.toString()",
"error",
eval("var tostr=String.prototype.toString; astring=new Number(); astring.toString = tostr; astring.toString()") );
this.fail();
} catch (e) {
this.assertTrue(e.name == 'TypeError');
this.assertTrue(e.message == 'Method "toString" called on incompatible object.');
}

//test();

},

/**
File Name:          15.5.4.2-3.js
ECMA Section:       15.5.4.2 String.prototype.toString()

Description:        Returns this string value.  Note that, for a String
object, the toString() method happens to return the same
thing as the valueOf() method.

The toString function is not generic; it generates a
runtime error if its this value is not a String object.
Therefore it connot be transferred to the other kinds of
objects for use as a method.

Author:             christine@netscape.com
Date:               1 october 1997
*/
test_15_5_4_2__3: function() {
var SECTION = "15.5.4.2-3";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.toString";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"var tostr=String.prototype.toString; astring=new String(); astring.toString = tostr; astring.toString()",
"",
eval("var tostr=String.prototype.toString; astring=new String(); astring.toString = tostr; astring.toString()") );
this.TestCase( SECTION,
"var tostr=String.prototype.toString; astring=new String(0); astring.toString = tostr; astring.toString()",
"0",
eval("var tostr=String.prototype.toString; astring=new String(0); astring.toString = tostr; astring.toString()") );
this.TestCase( SECTION,
"var tostr=String.prototype.toString; astring=new String('hello'); astring.toString = tostr; astring.toString()",
"hello",
eval("var tostr=String.prototype.toString; astring=new String('hello'); astring.toString = tostr; astring.toString()") );
this.TestCase( SECTION,
"var tostr=String.prototype.toString; astring=new String(''); astring.toString = tostr; astring.toString()",
"",
eval("var tostr=String.prototype.toString; astring=new String(''); astring.toString = tostr; astring.toString()") );

//test();

},

/**
File Name:          15.5.4.2.js
ECMA Section:       15.5.4.2 String.prototype.toString

Description:
Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_5_4_2: function() {
var SECTION = "15.5.4.2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.tostring";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase(   SECTION,
"String.prototype.toString() == String.prototype.valueOf()",
true,
String.prototype.toString() == String.prototype.valueOf() );

this.TestCase(   SECTION, "String.prototype.toString()",     "",     String.prototype.toString() );
this.TestCase(   SECTION, "String.prototype.toString.length",    0,  String.prototype.toString.length );


this.TestCase(   SECTION,
"TESTSTRING = new String();TESTSTRING.valueOf() == TESTSTRING.toString()",
true,
eval("TESTSTRING = new String();TESTSTRING.valueOf() == TESTSTRING.toString()") );
this.TestCase(   SECTION,
"TESTSTRING = new String(true);TESTSTRING.valueOf() == TESTSTRING.toString()",
true,
eval("TESTSTRING = new String(true);TESTSTRING.valueOf() == TESTSTRING.toString()") );
this.TestCase(   SECTION,
"TESTSTRING = new String(false);TESTSTRING.valueOf() == TESTSTRING.toString()",
true,
eval("TESTSTRING = new String(false);TESTSTRING.valueOf() == TESTSTRING.toString()") );
this.TestCase(   SECTION,
"TESTSTRING = new String(Math.PI);TESTSTRING.valueOf() == TESTSTRING.toString()",
true,
eval("TESTSTRING = new String(Math.PI);TESTSTRING.valueOf() == TESTSTRING.toString()") );
this.TestCase(   SECTION,
"TESTSTRING = new String();TESTSTRING.valueOf() == TESTSTRING.toString()",
true,
eval("TESTSTRING = new String();TESTSTRING.valueOf() == TESTSTRING.toString()") );

//test();

},

/**
File Name:          15.5.4.3-1.js
ECMA Section:       15.5.4.3 String.prototype.valueOf()

Description:        Returns this string value.

The valueOf function is not generic; it generates a
runtime error if its this value is not a String object.
Therefore it connot be transferred to the other kinds of
objects for use as a method.

Author:             christine@netscape.com
Date:               1 october 1997
*/
test_15_5_4_3__1: function() {
var SECTION = "15.5.4.3-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.valueOf";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,   "String.prototype.valueOf.length", 0,      String.prototype.valueOf.length );

this.TestCase( SECTION,   "String.prototype.valueOf()",        "",     String.prototype.valueOf() );
this.TestCase( SECTION,   "(new String()).valueOf()",          "",     (new String()).valueOf() );
this.TestCase( SECTION,   "(new String(\"\")).valueOf()",      "",     (new String("")).valueOf() );
this.TestCase( SECTION,   "(new String( String() )).valueOf()","",    (new String(String())).valueOf() );
this.TestCase( SECTION,   "(new String( \"h e l l o\" )).valueOf()",       "h e l l o",    (new String("h e l l o")).valueOf() );
this.TestCase( SECTION,   "(new String( 0 )).valueOf()",       "0",    (new String(0)).valueOf() );

//test();

},

/**
File Name:          15.5.4.3-2.js
ECMA Section:       15.5.4.3 String.prototype.valueOf()

Description:        Returns this string value.

The valueOf function is not generic; it generates a
runtime error if its this value is not a String object.
Therefore it connot be transferred to the other kinds of
objects for use as a method.

Author:             christine@netscape.com
Date:               1 october 1997
*/
test_15_5_4_3__2: function() {
var SECTION = "15.5.4.3-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.valueOf";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"var valof=String.prototype.valueOf; astring=new String(); astring.valueOf = valof; astring.valof()",
"",
eval("var valof=String.prototype.valueOf; astring=new String(); astring.valueOf = valof; astring.valueOf()") );

this.TestCase( SECTION,
"var valof=String.prototype.valueOf; astring=new String(0); astring.valueOf = valof; astring.valof()",
"0",
eval("var valof=String.prototype.valueOf; astring=new String(0); astring.valueOf = valof; astring.valueOf()") );

this.TestCase( SECTION,
"var valof=String.prototype.valueOf; astring=new String('hello'); astring.valueOf = valof; astring.valof()",
"hello",
eval("var valof=String.prototype.valueOf; astring=new String('hello'); astring.valueOf = valof; astring.valueOf()") );

this.TestCase( SECTION,
"var valof=String.prototype.valueOf; astring=new String(''); astring.valueOf = valof; astring.valof()",
"",
eval("var valof=String.prototype.valueOf; astring=new String(''); astring.valueOf = valof; astring.valueOf()") );
/*
new TestCase( SECTION,
"var valof=String.prototype.valueOf; astring=new Number(); astring.valueOf = valof; astring.valof()",
"error",
eval("var valof=String.prototype.valueOf; astring=new Number(); astring.valueOf = valof; astring.valueOf()") );
*/

//test();

},

/**
File Name:          15.5.4.3-3-n.js
ECMA Section:       15.5.4.3 String.prototype.valueOf()

Description:        Returns this string value.

The valueOf function is not generic; it generates a
runtime error if its this value is not a String object.
Therefore it connot be transferred to the other kinds of
objects for use as a method.

Author:             christine@netscape.com
Date:               1 october 1997
*/
test_15_5_4_3__3__n: function() {
var SECTION = "15.5.4.3-3-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.valueOf";

//writeHeaderToLog( SECTION + " "+ TITLE);

var DESCRIPTION = "var valof=String.prototype.valueOf; astring=new Number(); astring.valueOf = valof; astring.valof()";
var EXPECTED = "error";

this.TestCase( SECTION,
"var valof=String.prototype.valueOf; astring=new Number(); astring.valueOf = valof; astring.valof()",
"error",
eval("var valof=String.prototype.valueOf; astring=new Number(); astring.valueOf = valof; astring.valueOf()") );

//test();

},

/**
File Name:          15.5.4.3-3-n.js
ECMA Section:       15.5.4.3 String.prototype.valueOf()

Description:        Returns this string value.

The valueOf function is not generic; it generates a
runtime error if its this value is not a String object.
Therefore it connot be transferred to the other kinds of
objects for use as a method.

Author:             christine@netscape.com
Date:               1 october 1997
*/
test_15_5_4_3__3__n_WORKS: function() {
var SECTION = "15.5.4.3-3-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.valueOf";

//writeHeaderToLog( SECTION + " "+ TITLE);

var DESCRIPTION = "var valof=String.prototype.valueOf; astring=new Number(); astring.valueOf = valof; astring.valof()";
var EXPECTED = "error";

try {
this.TestCase( SECTION,
"var valof=String.prototype.valueOf; astring=new Number(); astring.valueOf = valof; astring.valof()",
"error",
eval("var valof=String.prototype.valueOf; astring=new Number(); astring.valueOf = valof; astring.valueOf()") );
this.fail();
} catch(e) {
this.assertTrue(e.name == 'TypeError');
this.assertTrue(e.message == 'Method "valueOf" called on incompatible object.');
}

//test();

},

/**
File Name:          15.5.4.4-1.js
ECMA Section:       15.5.4.4 String.prototype.charAt(pos)
Description:        Returns a string containing the character at position
pos in the string.  If there is no character at that
string, the result is the empty string.  The result is
a string value, not a String object.

When the charAt method is called with one argument,
pos, the following steps are taken:
1. Call ToString, with this value as its argument
2. Call ToInteger pos

In this test, this is a String, pos is an integer, and
all pos are in range.

Author:             christine@netscape.com
Date:               2 october 1997
*/
test_15_5_4_4__1: function() {
var SECTION = "15.5.4.4-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.charAt";

//writeHeaderToLog( SECTION + " "+ TITLE);

var TEST_STRING = new String( " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~" );

var item = 0;
var i;

for (  i = 0x0020; i < 0x007e; i++, item++) {
this.TestCase( SECTION,
"TEST_STRING.charAt("+item+")",
String.fromCharCode( i ),
TEST_STRING.charAt( item ) );
}

for ( i = 0x0020; i < 0x007e; i++, item++) {
this.TestCase( SECTION,
"TEST_STRING.charAt("+item+") == TEST_STRING.substring( "+item +", "+ (item+1) + ")",
true,
TEST_STRING.charAt( item )  == TEST_STRING.substring( item, item+1 )
);
}

this.TestCase( SECTION,  "String.prototype.charAt.length",       1,  String.prototype.charAt.length );

//print( "TEST_STRING = new String(\" !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\")" );

//test();

},

/**
File Name:          15.5.4.4-1.js
ECMA Section:       15.5.4.4 String.prototype.charAt(pos)
Description:        Returns a string containing the character at position
pos in the string.  If there is no character at that
string, the result is the empty string.  The result is
a string value, not a String object.

When the charAt method is called with one argument,
pos, the following steps are taken:
1. Call ToString, with this value as its argument
2. Call ToInteger pos
3. Compute the number of characters  in Result(1)
4. If Result(2) is less than 0 is or not less than
Result(3), return the empty string
5. Return a string of length 1 containing one character
from result (1), the character at position Result(2).

Note that the charAt function is intentionally generic;
it does not require that its this value be a String
object.  Therefore it can be transferred to other kinds
of objects for use as a method.

Author:             christine@netscape.com
Date:               2 october 1997
*/
test_15_5_4_4__2: function() {
var SECTION = "15.5.4.4-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.charAt";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,     "x = new Boolean(true); x.charAt=String.prototype.charAt;x.charAt(0)", "t",    eval("x = new Boolean(true); x.charAt=String.prototype.charAt;x.charAt(0)") );
this.TestCase( SECTION,     "x = new Boolean(true); x.charAt=String.prototype.charAt;x.charAt(1)", "r",    eval("x = new Boolean(true); x.charAt=String.prototype.charAt;x.charAt(1)") );
this.TestCase( SECTION,     "x = new Boolean(true); x.charAt=String.prototype.charAt;x.charAt(2)", "u",    eval("x = new Boolean(true); x.charAt=String.prototype.charAt;x.charAt(2)") );
this.TestCase( SECTION,     "x = new Boolean(true); x.charAt=String.prototype.charAt;x.charAt(3)", "e",    eval("x = new Boolean(true); x.charAt=String.prototype.charAt;x.charAt(3)") );
this.TestCase( SECTION,     "x = new Boolean(true); x.charAt=String.prototype.charAt;x.charAt(4)", "",     eval("x = new Boolean(true); x.charAt=String.prototype.charAt;x.charAt(4)") );
this.TestCase( SECTION,     "x = new Boolean(true); x.charAt=String.prototype.charAt;x.charAt(-1)", "",    eval("x = new Boolean(true); x.charAt=String.prototype.charAt;x.charAt(-1)") );

this.TestCase( SECTION,     "x = new Boolean(true); x.charAt=String.prototype.charAt;x.charAt(true)", "r",    eval("x = new Boolean(true); x.charAt=String.prototype.charAt;x.charAt(true)") );
this.TestCase( SECTION,     "x = new Boolean(true); x.charAt=String.prototype.charAt;x.charAt(false)", "t",    eval("x = new Boolean(true); x.charAt=String.prototype.charAt;x.charAt(false)") );

this.TestCase( SECTION,     "x = new String(); x.charAt(0)",    "",     eval("x=new String();x.charAt(0)") );
this.TestCase( SECTION,     "x = new String(); x.charAt(1)",    "",     eval("x=new String();x.charAt(1)") );
this.TestCase( SECTION,     "x = new String(); x.charAt(-1)",   "",     eval("x=new String();x.charAt(-1)") );

this.TestCase( SECTION,     "x = new String(); x.charAt(NaN)",  "",     eval("x=new String();x.charAt(Number.NaN)") );
this.TestCase( SECTION,     "x = new String(); x.charAt(Number.POSITIVE_INFINITY)",   "",     eval("x=new String();x.charAt(Number.POSITIVE_INFINITY)") );
this.TestCase( SECTION,     "x = new String(); x.charAt(Number.NEGATIVE_INFINITY)",   "",     eval("x=new String();x.charAt(Number.NEGATIVE_INFINITY)") );

this.TestCase( SECTION,      "var MYOB = new MyObject(1234567890); MYOB.charAt(0)",  "1",        eval("var MYOB = new MyObject(1234567890); MYOB.charAt(0)") );
this.TestCase( SECTION,      "var MYOB = new MyObject(1234567890); MYOB.charAt(1)",  "2",        eval("var MYOB = new MyObject(1234567890); MYOB.charAt(1)") );
this.TestCase( SECTION,      "var MYOB = new MyObject(1234567890); MYOB.charAt(2)",  "3",        eval("var MYOB = new MyObject(1234567890); MYOB.charAt(2)") );
this.TestCase( SECTION,      "var MYOB = new MyObject(1234567890); MYOB.charAt(3)",  "4",        eval("var MYOB = new MyObject(1234567890); MYOB.charAt(3)") );
this.TestCase( SECTION,      "var MYOB = new MyObject(1234567890); MYOB.charAt(4)",  "5",        eval("var MYOB = new MyObject(1234567890); MYOB.charAt(4)") );
this.TestCase( SECTION,      "var MYOB = new MyObject(1234567890); MYOB.charAt(5)",  "6",        eval("var MYOB = new MyObject(1234567890); MYOB.charAt(5)") );
this.TestCase( SECTION,      "var MYOB = new MyObject(1234567890); MYOB.charAt(6)",  "7",        eval("var MYOB = new MyObject(1234567890); MYOB.charAt(6)") );
this.TestCase( SECTION,      "var MYOB = new MyObject(1234567890); MYOB.charAt(7)",  "8",        eval("var MYOB = new MyObject(1234567890); MYOB.charAt(7)") );
this.TestCase( SECTION,      "var MYOB = new MyObject(1234567890); MYOB.charAt(8)",  "9",        eval("var MYOB = new MyObject(1234567890); MYOB.charAt(8)") );
this.TestCase( SECTION,      "var MYOB = new MyObject(1234567890); MYOB.charAt(9)",  "0",        eval("var MYOB = new MyObject(1234567890); MYOB.charAt(9)") );
this.TestCase( SECTION,      "var MYOB = new MyObject(1234567890); MYOB.charAt(10)",  "",       eval("var MYOB = new MyObject(1234567890); MYOB.charAt(10)") );

this.TestCase( SECTION,      "var MYOB = new MyObject(1234567890); MYOB.charAt(Math.PI)",  "4",        eval("var MYOB = new MyObject(1234567890); MYOB.charAt(Math.PI)") );

// MyOtherObject.toString will return "[object Object]

this.TestCase( SECTION,      "var MYOB = new MyOtherObject(1234567890); MYOB.charAt(0)",  "[",        eval("var MYOB = new MyOtherObject(1234567890); MYOB.charAt(0)") );
this.TestCase( SECTION,      "var MYOB = new MyOtherObject(1234567890); MYOB.charAt(1)",  "o",        eval("var MYOB = new MyOtherObject(1234567890); MYOB.charAt(1)") );
this.TestCase( SECTION,      "var MYOB = new MyOtherObject(1234567890); MYOB.charAt(2)",  "b",        eval("var MYOB = new MyOtherObject(1234567890); MYOB.charAt(2)") );
this.TestCase( SECTION,      "var MYOB = new MyOtherObject(1234567890); MYOB.charAt(3)",  "j",        eval("var MYOB = new MyOtherObject(1234567890); MYOB.charAt(3)") );
this.TestCase( SECTION,      "var MYOB = new MyOtherObject(1234567890); MYOB.charAt(4)",  "e",        eval("var MYOB = new MyOtherObject(1234567890); MYOB.charAt(4)") );
this.TestCase( SECTION,      "var MYOB = new MyOtherObject(1234567890); MYOB.charAt(5)",  "c",        eval("var MYOB = new MyOtherObject(1234567890); MYOB.charAt(5)") );
this.TestCase( SECTION,      "var MYOB = new MyOtherObject(1234567890); MYOB.charAt(6)",  "t",        eval("var MYOB = new MyOtherObject(1234567890); MYOB.charAt(6)") );
this.TestCase( SECTION,      "var MYOB = new MyOtherObject(1234567890); MYOB.charAt(7)",  " ",        eval("var MYOB = new MyOtherObject(1234567890); MYOB.charAt(7)") );
this.TestCase( SECTION,      "var MYOB = new MyOtherObject(1234567890); MYOB.charAt(8)",  "O",        eval("var MYOB = new MyOtherObject(1234567890); MYOB.charAt(8)") );
this.TestCase( SECTION,      "var MYOB = new MyOtherObject(1234567890); MYOB.charAt(9)",  "b",        eval("var MYOB = new MyOtherObject(1234567890); MYOB.charAt(9)") );
this.TestCase( SECTION,      "var MYOB = new MyOtherObject(1234567890); MYOB.charAt(10)",  "j",        eval("var MYOB = new MyOtherObject(1234567890); MYOB.charAt(10)") );
this.TestCase( SECTION,      "var MYOB = new MyOtherObject(1234567890); MYOB.charAt(11)",  "e",        eval("var MYOB = new MyOtherObject(1234567890); MYOB.charAt(11)") );
this.TestCase( SECTION,      "var MYOB = new MyOtherObject(1234567890); MYOB.charAt(12)",  "c",        eval("var MYOB = new MyOtherObject(1234567890); MYOB.charAt(12)") );
this.TestCase( SECTION,      "var MYOB = new MyOtherObject(1234567890); MYOB.charAt(13)",  "t",        eval("var MYOB = new MyOtherObject(1234567890); MYOB.charAt(13)") );
this.TestCase( SECTION,      "var MYOB = new MyOtherObject(1234567890); MYOB.charAt(14)",  "]",        eval("var MYOB = new MyOtherObject(1234567890); MYOB.charAt(14)") );

//test();

function MyObject( value ) {
this.value      = value;
this.valueOf    = new Function( "return this.value;" );
this.toString   = new Function( "return this.value +''" );
this.charAt     = String.prototype.charAt;
}
function MyOtherObject(value) {
this.value      = value;
this.valueOf    = new Function( "return this.value;" );
this.charAt     = String.prototype.charAt;
}

},

/**
File Name:          15.5.4.4-3.js
ECMA Section:       15.5.4.4 String.prototype.charAt(pos)
Description:        Returns a string containing the character at position
pos in the string.  If there is no character at that
string, the result is the empty string.  The result is
a string value, not a String object.

When the charAt method is called with one argument,
pos, the following steps are taken:
1. Call ToString, with this value as its argument
2. Call ToInteger pos
3. Compute the number of characters  in Result(1)
4. If Result(2) is less than 0 is or not less than
Result(3), return the empty string
5. Return a string of length 1 containing one character
from result (1), the character at position Result(2).

Note that the charAt function is intentionally generic;
it does not require that its this value be a String
object.  Therefore it can be transferred to other kinds
of objects for use as a method.

This tests assiging charAt to a user-defined function.

Author:             christine@netscape.com
Date:               2 october 1997
*/
test_15_5_4_4__3: function() {
var SECTION = "15.5.4.4-3";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.charAt";

//writeHeaderToLog( SECTION + " "+ TITLE);

var foo = new MyObject('hello');


this.TestCase( SECTION, "var foo = new MyObject('hello'); ", "h", foo.charAt(0)  );
this.TestCase( SECTION, "var foo = new MyObject('hello'); ", "e", foo.charAt(1)  );
this.TestCase( SECTION, "var foo = new MyObject('hello'); ", "l", foo.charAt(2)  );
this.TestCase( SECTION, "var foo = new MyObject('hello'); ", "l", foo.charAt(3)  );
this.TestCase( SECTION, "var foo = new MyObject('hello'); ", "o", foo.charAt(4)  );
this.TestCase( SECTION, "var foo = new MyObject('hello'); ", "",  foo.charAt(-1)  );
this.TestCase( SECTION, "var foo = new MyObject('hello'); ", "",  foo.charAt(5)  );

var boo = new MyObject(true);

this.TestCase( SECTION, "var boo = new MyObject(true); ", "t", boo.charAt(0)  );
this.TestCase( SECTION, "var boo = new MyObject(true); ", "r", boo.charAt(1)  );
this.TestCase( SECTION, "var boo = new MyObject(true); ", "u", boo.charAt(2)  );
this.TestCase( SECTION, "var boo = new MyObject(true); ", "e", boo.charAt(3)  );

var noo = new MyObject( Math.PI );

this.TestCase( SECTION, "var noo = new MyObject(Math.PI); ", "3", noo.charAt(0)  );
this.TestCase( SECTION, "var noo = new MyObject(Math.PI); ", ".", noo.charAt(1)  );
this.TestCase( SECTION, "var noo = new MyObject(Math.PI); ", "1", noo.charAt(2)  );
this.TestCase( SECTION, "var noo = new MyObject(Math.PI); ", "4", noo.charAt(3)  );
this.TestCase( SECTION, "var noo = new MyObject(Math.PI); ", "1", noo.charAt(4)  );
this.TestCase( SECTION, "var noo = new MyObject(Math.PI); ", "5", noo.charAt(5)  );
this.TestCase( SECTION, "var noo = new MyObject(Math.PI); ", "9", noo.charAt(6)  );

//test();

function MyObject (v) {
this.value      = v;
this.toString   = new Function( "return this.value +'';" );
this.valueOf    = new Function( "return this.value" );
this.charAt     = String.prototype.charAt;
}

},

/**
File Name:          15.5.4.4-4.js
ECMA Section:       15.5.4.4 String.prototype.charAt(pos)
Description:        Returns a string containing the character at position
pos in the string.  If there is no character at that
string, the result is the empty string.  The result is
a string value, not a String object.

When the charAt method is called with one argument,
pos, the following steps are taken:
1. Call ToString, with this value as its argument
2. Call ToInteger pos
3. Compute the number of characters  in Result(1)
4. If Result(2) is less than 0 is or not less than
Result(3), return the empty string
5. Return a string of length 1 containing one character
from result (1), the character at position Result(2).

Note that the charAt function is intentionally generic;
it does not require that its this value be a String
object.  Therefore it can be transferred to other kinds
of objects for use as a method.

This tests assiging charAt to primitive types..

Author:             christine@netscape.com
Date:               2 october 1997
*/
test_15_5_4_4__4: function() {
var SECTION = "15.5.4.4-4";
var VERSION = "ECMA_2";
//startTest();
var TITLE   = "String.prototype.charAt";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,     "x = new Array(1,2,3); x.charAt = String.prototype.charAt; x.charAt(0)",            "1",     eval("x=new Array(1,2,3); x.charAt = String.prototype.charAt; x.charAt(0)") );
this.TestCase( SECTION,     "x = new Array(1,2,3); x.charAt = String.prototype.charAt; x.charAt(1)",            ",",     eval("x=new Array(1,2,3); x.charAt = String.prototype.charAt; x.charAt(1)") );
this.TestCase( SECTION,     "x = new Array(1,2,3); x.charAt = String.prototype.charAt; x.charAt(2)",            "2",     eval("x=new Array(1,2,3); x.charAt = String.prototype.charAt; x.charAt(2)") );
this.TestCase( SECTION,     "x = new Array(1,2,3); x.charAt = String.prototype.charAt; x.charAt(3)",            ",",     eval("x=new Array(1,2,3); x.charAt = String.prototype.charAt; x.charAt(3)") );
this.TestCase( SECTION,     "x = new Array(1,2,3); x.charAt = String.prototype.charAt; x.charAt(4)",            "3",     eval("x=new Array(1,2,3); x.charAt = String.prototype.charAt; x.charAt(4)") );

this.TestCase( SECTION,  "x = new Array(); x.charAt = String.prototype.charAt; x.charAt(0)",                    "",      eval("x = new Array(); x.charAt = String.prototype.charAt; x.charAt(0)") );

this.TestCase( SECTION,     "x = new Number(123); x.charAt = String.prototype.charAt; x.charAt(0)",            "1",     eval("x=new Number(123); x.charAt = String.prototype.charAt; x.charAt(0)") );
this.TestCase( SECTION,     "x = new Number(123); x.charAt = String.prototype.charAt; x.charAt(1)",            "2",     eval("x=new Number(123); x.charAt = String.prototype.charAt; x.charAt(1)") );
this.TestCase( SECTION,     "x = new Number(123); x.charAt = String.prototype.charAt; x.charAt(2)",            "3",     eval("x=new Number(123); x.charAt = String.prototype.charAt; x.charAt(2)") );

this.TestCase( SECTION,     "x = new Object(); x.charAt = String.prototype.charAt; x.charAt(0)",            "[",     eval("x=new Object(); x.charAt = String.prototype.charAt; x.charAt(0)") );
this.TestCase( SECTION,     "x = new Object(); x.charAt = String.prototype.charAt; x.charAt(1)",            "o",     eval("x=new Object(); x.charAt = String.prototype.charAt; x.charAt(1)") );
this.TestCase( SECTION,     "x = new Object(); x.charAt = String.prototype.charAt; x.charAt(2)",            "b",     eval("x=new Object(); x.charAt = String.prototype.charAt; x.charAt(2)") );
this.TestCase( SECTION,     "x = new Object(); x.charAt = String.prototype.charAt; x.charAt(3)",            "j",     eval("x=new Object(); x.charAt = String.prototype.charAt; x.charAt(3)") );
this.TestCase( SECTION,     "x = new Object(); x.charAt = String.prototype.charAt; x.charAt(4)",            "e",     eval("x=new Object(); x.charAt = String.prototype.charAt; x.charAt(4)") );
this.TestCase( SECTION,     "x = new Object(); x.charAt = String.prototype.charAt; x.charAt(5)",            "c",     eval("x=new Object(); x.charAt = String.prototype.charAt; x.charAt(5)") );
this.TestCase( SECTION,     "x = new Object(); x.charAt = String.prototype.charAt; x.charAt(6)",            "t",     eval("x=new Object(); x.charAt = String.prototype.charAt; x.charAt(6)") );
this.TestCase( SECTION,     "x = new Object(); x.charAt = String.prototype.charAt; x.charAt(7)",            " ",     eval("x=new Object(); x.charAt = String.prototype.charAt; x.charAt(7)") );
this.TestCase( SECTION,     "x = new Object(); x.charAt = String.prototype.charAt; x.charAt(8)",            "O",     eval("x=new Object(); x.charAt = String.prototype.charAt; x.charAt(8)") );
this.TestCase( SECTION,     "x = new Object(); x.charAt = String.prototype.charAt; x.charAt(9)",            "b",     eval("x=new Object(); x.charAt = String.prototype.charAt; x.charAt(9)") );
this.TestCase( SECTION,     "x = new Object(); x.charAt = String.prototype.charAt; x.charAt(10)",            "j",     eval("x=new Object(); x.charAt = String.prototype.charAt; x.charAt(10)") );
this.TestCase( SECTION,     "x = new Object(); x.charAt = String.prototype.charAt; x.charAt(11)",            "e",     eval("x=new Object(); x.charAt = String.prototype.charAt; x.charAt(11)") );
this.TestCase( SECTION,     "x = new Object(); x.charAt = String.prototype.charAt; x.charAt(12)",            "c",     eval("x=new Object(); x.charAt = String.prototype.charAt; x.charAt(12)") );
this.TestCase( SECTION,     "x = new Object(); x.charAt = String.prototype.charAt; x.charAt(13)",            "t",     eval("x=new Object(); x.charAt = String.prototype.charAt; x.charAt(13)") );
this.TestCase( SECTION,     "x = new Object(); x.charAt = String.prototype.charAt; x.charAt(14)",            "]",     eval("x=new Object(); x.charAt = String.prototype.charAt; x.charAt(14)") );

this.TestCase( SECTION,     "x = new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(0)",            "[",    eval("x=new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(0)") );
this.TestCase( SECTION,     "x = new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(1)",            "o",     eval("x=new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(1)") );
this.TestCase( SECTION,     "x = new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(2)",            "b",     eval("x=new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(2)") );
this.TestCase( SECTION,     "x = new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(3)",            "j",     eval("x=new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(3)") );
this.TestCase( SECTION,     "x = new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(4)",            "e",     eval("x=new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(4)") );
this.TestCase( SECTION,     "x = new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(5)",            "c",     eval("x=new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(5)") );
this.TestCase( SECTION,     "x = new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(6)",            "t",     eval("x=new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(6)") );
this.TestCase( SECTION,     "x = new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(7)",            " ",     eval("x=new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(7)") );
this.TestCase( SECTION,     "x = new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(8)",            "F",     eval("x=new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(8)") );
this.TestCase( SECTION,     "x = new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(9)",            "u",     eval("x=new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(9)") );
this.TestCase( SECTION,     "x = new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(10)",            "n",     eval("x=new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(10)") );
this.TestCase( SECTION,     "x = new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(11)",            "c",     eval("x=new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(11)") );
this.TestCase( SECTION,     "x = new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(12)",            "t",     eval("x=new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(12)") );
this.TestCase( SECTION,     "x = new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(13)",            "i",     eval("x=new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(13)") );
this.TestCase( SECTION,     "x = new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(14)",            "o",     eval("x=new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(14)") );
this.TestCase( SECTION,     "x = new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(15)",            "n",     eval("x=new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(15)") );
this.TestCase( SECTION,     "x = new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(16)",            "]",     eval("x=new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(16)") );
this.TestCase( SECTION,     "x = new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(17)",            "",     eval("x=new Function(); x.toString = Object.prototype.toString; x.charAt = String.prototype.charAt; x.charAt(17)") );

//test();

},

/**
File Name:          15.5.4.5.1.js
ECMA Section:       15.5.4.5 String.prototype.charCodeAt(pos)
Description:        Returns a number (a nonnegative integer less than 2^16)
representing the Unicode encoding of the character at
position pos in this string.  If there is no character
at that position, the number is NaN.

When the charCodeAt method is called with one argument
pos, the following steps are taken:
1. Call ToString, giving it the theis value as its
argument
2. Call ToInteger(pos)
3. Compute the number of characters in result(1).
4. If Result(2) is less than 0 or is not less than
Result(3), return NaN.
5. Return a value of Number type, of positive sign, whose
magnitude is the Unicode encoding of one character
from result 1, namely the characer at position Result
(2), where the first character in Result(1) is
considered to be at position 0.

Note that the charCodeAt funciton is intentionally
generic; it does not require that its this value be a
String object.  Therefore it can be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               2 october 1997
*/
test_15_5_4_5__1: function() {
var SECTION = "15.5.4.5-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.charCodeAt";

//writeHeaderToLog( SECTION + " "+ TITLE);

var TEST_STRING = new String( " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~" );

for ( j = 0, i = 0x0020; i < 0x007e; i++, j++ ) {
this.TestCase( SECTION, "TEST_STRING.charCodeAt("+j+")", i, TEST_STRING.charCodeAt( j ) );
}

this.TestCase( SECTION, 'TEST_STRING.charCodeAt('+i+')', NaN,    TEST_STRING.charCodeAt( i ) );


//test();

},

/**
File Name:          15.5.4.5.1.js
ECMA Section:       15.5.4.5 String.prototype.charCodeAt(pos)
Description:        Returns a number (a nonnegative integer less than 2^16)
representing the Unicode encoding of the character at
position pos in this string.  If there is no character
at that position, the number is NaN.

When the charCodeAt method is called with one argument
pos, the following steps are taken:
1. Call ToString, giving it the theis value as its
argument
2. Call ToInteger(pos)
3. Compute the number of characters in result(1).
4. If Result(2) is less than 0 or is not less than
Result(3), return NaN.
5. Return a value of Number type, of positive sign, whose
magnitude is the Unicode encoding of one character
from result 1, namely the characer at position Result
(2), where the first character in Result(1) is
considered to be at position 0.

Note that the charCodeAt funciton is intentionally
generic; it does not require that its this value be a
String object.  Therefore it can be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               2 october 1997
*/
test_15_5_4_5__2: function() {
var SECTION = "15.5.4.5-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.charCodeAt";

//writeHeaderToLog( SECTION + " "+ TITLE);

var TEST_STRING = new String( " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~" );

var x;

this.TestCase( SECTION,     "x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(0)", 0x0074,    eval("x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(0)") );
this.TestCase( SECTION,     "x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(1)", 0x0072,    eval("x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(1)") );
this.TestCase( SECTION,     "x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(2)", 0x0075,    eval("x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(2)") );
this.TestCase( SECTION,     "x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(3)", 0x0065,    eval("x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(3)") );
this.TestCase( SECTION,     "x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(4)", Number.NaN,     eval("x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(4)") );
this.TestCase( SECTION,     "x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(-1)", Number.NaN,    eval("x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(-1)") );

this.TestCase( SECTION,     "x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(true)",  0x0072,    eval("x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(true)") );
this.TestCase( SECTION,     "x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(false)", 0x0074,    eval("x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(false)") );

this.TestCase( SECTION,     "x = new String(); x.charCodeAt(0)",    Number.NaN,     eval("x=new String();x.charCodeAt(0)") );
this.TestCase( SECTION,     "x = new String(); x.charCodeAt(1)",    Number.NaN,     eval("x=new String();x.charCodeAt(1)") );
this.TestCase( SECTION,     "x = new String(); x.charCodeAt(-1)",   Number.NaN,     eval("x=new String();x.charCodeAt(-1)") );

this.TestCase( SECTION,     "x = new String(); x.charCodeAt(NaN)",                       Number.NaN,     eval("x=new String();x.charCodeAt(Number.NaN)") );
this.TestCase( SECTION,     "x = new String(); x.charCodeAt(Number.POSITIVE_INFINITY)",  Number.NaN,     eval("x=new String();x.charCodeAt(Number.POSITIVE_INFINITY)") );
this.TestCase( SECTION,     "x = new String(); x.charCodeAt(Number.NEGATIVE_INFINITY)",  Number.NaN,     eval("x=new String();x.charCodeAt(Number.NEGATIVE_INFINITY)") );

this.TestCase( SECTION,  "x = new Array(1,2,3); x.charCodeAt = String.prototype.charCodeAt; x.charCodeAt(0)",    0x0031,   eval("x = new Array(1,2,3); x.charCodeAt = String.prototype.charCodeAt; x.charCodeAt(0)") );
this.TestCase( SECTION,  "x = new Array(1,2,3); x.charCodeAt = String.prototype.charCodeAt; x.charCodeAt(1)",    0x002C,   eval("x = new Array(1,2,3); x.charCodeAt = String.prototype.charCodeAt; x.charCodeAt(1)") );
this.TestCase( SECTION,  "x = new Array(1,2,3); x.charCodeAt = String.prototype.charCodeAt; x.charCodeAt(2)",    0x0032,   eval("x = new Array(1,2,3); x.charCodeAt = String.prototype.charCodeAt; x.charCodeAt(2)") );
this.TestCase( SECTION,  "x = new Array(1,2,3); x.charCodeAt = String.prototype.charCodeAt; x.charCodeAt(3)",    0x002C,   eval("x = new Array(1,2,3); x.charCodeAt = String.prototype.charCodeAt; x.charCodeAt(3)") );
this.TestCase( SECTION,  "x = new Array(1,2,3); x.charCodeAt = String.prototype.charCodeAt; x.charCodeAt(4)",    0x0033,   eval("x = new Array(1,2,3); x.charCodeAt = String.prototype.charCodeAt; x.charCodeAt(4)") );
this.TestCase( SECTION,  "x = new Array(1,2,3); x.charCodeAt = String.prototype.charCodeAt; x.charCodeAt(5)",    NaN,   eval("x = new Array(1,2,3); x.charCodeAt = String.prototype.charCodeAt; x.charCodeAt(5)") );

this.TestCase( SECTION,  "x = new Function( 'this.charCodeAt = String.prototype.charCodeAt' ); f = new x(); f.charCodeAt(0)", 0x005B, eval("x = new Function( 'this.charCodeAt = String.prototype.charCodeAt' ); f = new x(); f.charCodeAt(0)") );
this.TestCase( SECTION,  "x = new Function( 'this.charCodeAt = String.prototype.charCodeAt' ); f = new x(); f.charCodeAt(1)", 0x006F, eval("x = new Function( 'this.charCodeAt = String.prototype.charCodeAt' ); f = new x(); f.charCodeAt(1)") );
this.TestCase( SECTION,  "x = new Function( 'this.charCodeAt = String.prototype.charCodeAt' ); f = new x(); f.charCodeAt(2)", 0x0062, eval("x = new Function( 'this.charCodeAt = String.prototype.charCodeAt' ); f = new x(); f.charCodeAt(2)") );
this.TestCase( SECTION,  "x = new Function( 'this.charCodeAt = String.prototype.charCodeAt' ); f = new x(); f.charCodeAt(3)", 0x006A, eval("x = new Function( 'this.charCodeAt = String.prototype.charCodeAt' ); f = new x(); f.charCodeAt(3)") );
this.TestCase( SECTION,  "x = new Function( 'this.charCodeAt = String.prototype.charCodeAt' ); f = new x(); f.charCodeAt(4)", 0x0065, eval("x = new Function( 'this.charCodeAt = String.prototype.charCodeAt' ); f = new x(); f.charCodeAt(4)") );
this.TestCase( SECTION,  "x = new Function( 'this.charCodeAt = String.prototype.charCodeAt' ); f = new x(); f.charCodeAt(5)", 0x0063, eval("x = new Function( 'this.charCodeAt = String.prototype.charCodeAt' ); f = new x(); f.charCodeAt(5)") );
this.TestCase( SECTION,  "x = new Function( 'this.charCodeAt = String.prototype.charCodeAt' ); f = new x(); f.charCodeAt(6)", 0x0074, eval("x = new Function( 'this.charCodeAt = String.prototype.charCodeAt' ); f = new x(); f.charCodeAt(6)") );

this.TestCase( SECTION,  "x = new Function( 'this.charCodeAt = String.prototype.charCodeAt' ); f = new x(); f.charCodeAt(7)", 0x0020, eval("x = new Function( 'this.charCodeAt = String.prototype.charCodeAt' ); f = new x(); f.charCodeAt(7)") );

this.TestCase( SECTION,  "x = new Function( 'this.charCodeAt = String.prototype.charCodeAt' ); f = new x(); f.charCodeAt(8)", 0x004F, eval("x = new Function( 'this.charCodeAt = String.prototype.charCodeAt' ); f = new x(); f.charCodeAt(8)") );
this.TestCase( SECTION,  "x = new Function( 'this.charCodeAt = String.prototype.charCodeAt' ); f = new x(); f.charCodeAt(9)", 0x0062, eval("x = new Function( 'this.charCodeAt = String.prototype.charCodeAt' ); f = new x(); f.charCodeAt(9)") );
this.TestCase( SECTION,  "x = new Function( 'this.charCodeAt = String.prototype.charCodeAt' ); f = new x(); f.charCodeAt(10)", 0x006A, eval("x = new Function( 'this.charCodeAt = String.prototype.charCodeAt' ); f = new x(); f.charCodeAt(10)") );

//test();

},

/**
File Name:          15.5.4.5-3.js
ECMA Section:       15.5.4.5 String.prototype.charCodeAt(pos)
Description:        Returns a number (a nonnegative integer less than 2^16)
representing the Unicode encoding of the character at
position pos in this string.  If there is no character
at that position, the number is NaN.

When the charCodeAt method is called with one argument
pos, the following steps are taken:
1. Call ToString, giving it the theis value as its
argument
2. Call ToInteger(pos)
3. Compute the number of characters in result(1).
4. If Result(2) is less than 0 or is not less than
Result(3), return NaN.
5. Return a value of Number type, of positive sign, whose
magnitude is the Unicode encoding of one character
from result 1, namely the characer at position Result
(2), where the first character in Result(1) is
considered to be at position 0.

Note that the charCodeAt funciton is intentionally
generic; it does not require that its this value be a
String object.  Therefore it can be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               2 october 1997
*/
test_15_5_4_5__3: function() {
var SECTION = "15.5.4.5-3";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.charCodeAt";

//writeHeaderToLog( SECTION + " "+ TITLE);

var TEST_STRING = new String( " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~" );


var foo = new MyObject('hello');

this.TestCase( SECTION, "var foo = new MyObject('hello');foo.charCodeAt(0)", 0x0068, foo.charCodeAt(0)  );
this.TestCase( SECTION, "var foo = new MyObject('hello');foo.charCodeAt(1)", 0x0065, foo.charCodeAt(1)  );
this.TestCase( SECTION, "var foo = new MyObject('hello');foo.charCodeAt(2)", 0x006c, foo.charCodeAt(2)  );
this.TestCase( SECTION, "var foo = new MyObject('hello');foo.charCodeAt(3)", 0x006c, foo.charCodeAt(3)  );
this.TestCase( SECTION, "var foo = new MyObject('hello');foo.charCodeAt(4)", 0x006f, foo.charCodeAt(4)  );
this.TestCase( SECTION, "var foo = new MyObject('hello');foo.charCodeAt(-1)", Number.NaN,  foo.charCodeAt(-1)  );
this.TestCase( SECTION, "var foo = new MyObject('hello');foo.charCodeAt(5)", Number.NaN,  foo.charCodeAt(5)  );

var boo = new MyObject(true);

this.TestCase( SECTION, "var boo = new MyObject(true);boo.charCodeAt(0)", 0x0074, boo.charCodeAt(0)  );
this.TestCase( SECTION, "var boo = new MyObject(true);boo.charCodeAt(1)", 0x0072, boo.charCodeAt(1)  );
this.TestCase( SECTION, "var boo = new MyObject(true);boo.charCodeAt(2)", 0x0075, boo.charCodeAt(2)  );
this.TestCase( SECTION, "var boo = new MyObject(true);boo.charCodeAt(3)", 0x0065, boo.charCodeAt(3)  );

var noo = new MyObject( Math.PI );

this.TestCase( SECTION, "var noo = new MyObject(Math.PI);noo.charCodeAt(0)", 0x0033, noo.charCodeAt(0)  );
this.TestCase( SECTION, "var noo = new MyObject(Math.PI);noo.charCodeAt(1)", 0x002E, noo.charCodeAt(1)  );
this.TestCase( SECTION, "var noo = new MyObject(Math.PI);noo.charCodeAt(2)", 0x0031, noo.charCodeAt(2)  );
this.TestCase( SECTION, "var noo = new MyObject(Math.PI);noo.charCodeAt(3)", 0x0034, noo.charCodeAt(3)  );
this.TestCase( SECTION, "var noo = new MyObject(Math.PI);noo.charCodeAt(4)", 0x0031, noo.charCodeAt(4)  );
this.TestCase( SECTION, "var noo = new MyObject(Math.PI);noo.charCodeAt(5)", 0x0035, noo.charCodeAt(5)  );
this.TestCase( SECTION, "var noo = new MyObject(Math.PI);noo.charCodeAt(6)", 0x0039, noo.charCodeAt(6)  );

noo = new MyObject( null );

this.TestCase( SECTION, "var noo = new MyObject(null);noo.charCodeAt(0)", 0x006E, noo.charCodeAt(0)  );
this.TestCase( SECTION, "var noo = new MyObject(null);noo.charCodeAt(1)", 0x0075, noo.charCodeAt(1)  );
this.TestCase( SECTION, "var noo = new MyObject(null);noo.charCodeAt(2)", 0x006C, noo.charCodeAt(2)  );
this.TestCase( SECTION, "var noo = new MyObject(null);noo.charCodeAt(3)", 0x006C, noo.charCodeAt(3)  );
this.TestCase( SECTION, "var noo = new MyObject(null);noo.charCodeAt(4)", NaN, noo.charCodeAt(4)  );

noo = new MyObject( void 0 );

this.TestCase( SECTION, "var noo = new MyObject(void 0);noo.charCodeAt(0)", 0x0075, noo.charCodeAt(0)  );
this.TestCase( SECTION, "var noo = new MyObject(void 0);noo.charCodeAt(1)", 0x006E, noo.charCodeAt(1)  );
this.TestCase( SECTION, "var noo = new MyObject(void 0);noo.charCodeAt(2)", 0x0064, noo.charCodeAt(2)  );
this.TestCase( SECTION, "var noo = new MyObject(void 0);noo.charCodeAt(3)", 0x0065, noo.charCodeAt(3)  );
this.TestCase( SECTION, "var noo = new MyObject(void 0);noo.charCodeAt(4)", 0x0066, noo.charCodeAt(4)  );

//test();


function MyObject (v) {
this.value      = v;
this.toString   = new Function ( "return this.value +\"\"" );
this.charCodeAt     = String.prototype.charCodeAt;
}

},

/**
File Name:          15.5.4.5-4.js
ECMA Section:       15.5.4.5 String.prototype.charCodeAt(pos)

Description:        Returns a nonnegative integer less than 2^16.

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_5_4_5__4: function() {
var VERSION = "0697";
//startTest();
var SECTION = "15.5.4.5-4";

//writeHeaderToLog( SECTION + " "+ TITLE);

var MAXCHARCODE = Math.pow(2,16);
var item=0, CHARCODE;

for ( CHARCODE=0; CHARCODE <256; CHARCODE++ ) {
this.TestCase( SECTION,
"(String.fromCharCode("+CHARCODE+")).charCodeAt(0)",
CHARCODE,
(String.fromCharCode(CHARCODE)).charCodeAt(0) );
}
for ( CHARCODE=256; CHARCODE < 65536; CHARCODE+=999 ) {
this.TestCase( SECTION,
"(String.fromCharCode("+CHARCODE+")).charCodeAt(0)",
CHARCODE,
(String.fromCharCode(CHARCODE)).charCodeAt(0) );
}

this.TestCase( SECTION, "(String.fromCharCode(65535)).charCodeAt(0)", 65535,     (String.fromCharCode(65535)).charCodeAt(0) );

//test();

},

/**
File Name:          15.5.4.5.1.js
ECMA Section:       15.5.4.5 String.prototype.charCodeAt(pos)
Description:        Returns a number (a nonnegative integer less than 2^16)
representing the Unicode encoding of the character at
position pos in this string.  If there is no character
at that position, the number is NaN.

When the charCodeAt method is called with one argument
pos, the following steps are taken:
1. Call ToString, giving it the theis value as its
argument
2. Call ToInteger(pos)
3. Compute the number of characters in result(1).
4. If Result(2) is less than 0 or is not less than
Result(3), return NaN.
5. Return a value of Number type, of positive sign, whose
magnitude is the Unicode encoding of one character
from result 1, namely the characer at position Result
(2), where the first character in Result(1) is
considered to be at position 0.

Note that the charCodeAt funciton is intentionally
generic; it does not require that its this value be a
String object.  Therefore it can be transferred to other
kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               2 october 1997
*/
test_15_5_4_5__5: function() {
var SECTION = "15.5.4.5-5";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.charCodeAt";

//writeHeaderToLog( SECTION + " "+ TITLE);

var TEST_STRING = "";

for ( var i = 0x0000; i < 255; i++ ) {
TEST_STRING += String.fromCharCode( i );
}

this.TestCase( SECTION,     "x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(0)", 0x0074,    eval("x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(0)") );
this.TestCase( SECTION,     "x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(1)", 0x0072,    eval("x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(1)") );
this.TestCase( SECTION,     "x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(2)", 0x0075,    eval("x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(2)") );
this.TestCase( SECTION,     "x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(3)", 0x0065,    eval("x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(3)") );
this.TestCase( SECTION,     "x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(4)", Number.NaN,     eval("x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(4)") );
this.TestCase( SECTION,     "x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(-1)", Number.NaN,    eval("x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(-1)") );

this.TestCase( SECTION,     "x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(true)",  0x0072,    eval("x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(true)") );
this.TestCase( SECTION,     "x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(false)", 0x0074,    eval("x = new Boolean(true); x.charCodeAt=String.prototype.charCodeAt;x.charCodeAt(false)") );

this.TestCase( SECTION,     "x = new String(); x.charCodeAt(0)",    Number.NaN,     eval("x=new String();x.charCodeAt(0)") );
this.TestCase( SECTION,     "x = new String(); x.charCodeAt(1)",    Number.NaN,     eval("x=new String();x.charCodeAt(1)") );
this.TestCase( SECTION,     "x = new String(); x.charCodeAt(-1)",   Number.NaN,     eval("x=new String();x.charCodeAt(-1)") );

this.TestCase( SECTION,     "x = new String(); x.charCodeAt(NaN)",  Number.NaN,     eval("x=new String();x.charCodeAt(Number.NaN)") );
this.TestCase( SECTION,     "x = new String(); x.charCodeAt(Number.POSITIVE_INFINITY)",   Number.NaN,     eval("x=new String();x.charCodeAt(Number.POSITIVE_INFINITY)") );
this.TestCase( SECTION,     "x = new String(); x.charCodeAt(Number.NEGATIVE_INFINITY)",   Number.NaN,     eval("x=new String();x.charCodeAt(Number.NEGATIVE_INFINITY)") );

for ( var j = 0; j < 255; j++ ) {
this.TestCase( SECTION,  "TEST_STRING.charCodeAt("+j+")",    j,     TEST_STRING.charCodeAt(j) );
}

//test();

},

/**
File Name:          15.5.4.6-1.js
ECMA Section:       15.5.4.6 String.prototype.indexOf( searchString, pos)
Description:        If the given searchString appears as a substring of the
result of converting this object to a string, at one or
more positions that are at or to the right of the
specified position, then the index of the leftmost such
position is returned; otherwise -1 is returned.  If
positionis undefined or not supplied, 0 is assumed, so
as to search all of the string.

When the indexOf method is called with two arguments,
searchString and pos, the following steps are taken:

1. Call ToString, giving it the this value as its
argument.
2. Call ToString(searchString).
3. Call ToInteger(position). (If position is undefined
or not supplied, this step produces the value 0).
4. Compute the number of characters in Result(1).
5. Compute min(max(Result(3), 0), Result(4)).
6. Compute the number of characters in the string that
is Result(2).
7. Compute the smallest possible integer k not smaller
than Result(5) such that k+Result(6) is not greater
than Result(4), and for all nonnegative integers j
less than Result(6), the character at position k+j
of Result(1) is the same as the character at position
j of Result(2); but if there is no such integer k,
then compute the value -1.
8. Return Result(7).

Note that the indexOf function is intentionally generic;
it does not require that its this value be a String object.
Therefore it can be transferred to other kinds of objects
for use as a method.

Author:             christine@netscape.com
Date:               2 october 1997
*/
test_15_5_4_6__1: function() {
var SECTION = "15.5.4.6-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.protoype.indexOf";

var TEST_STRING = new String( " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~" );

//writeHeaderToLog( SECTION + " "+ TITLE);

var j = 0;

for ( k = 0, i = 0x0020; i < 0x007e; i++, j++, k++ ) {
this.TestCase( SECTION,
"String.indexOf(" +String.fromCharCode(i)+ ", 0)",
k,
TEST_STRING.indexOf( String.fromCharCode(i), 0 ) );
}

for ( k = 0, i = 0x0020; i < 0x007e; i++, j++, k++ ) {
this.TestCase( SECTION,
"String.indexOf("+String.fromCharCode(i)+ ", "+ k +")",
k,
TEST_STRING.indexOf( String.fromCharCode(i), k ) );
}

for ( k = 0, i = 0x0020; i < 0x007e; i++, j++, k++ ) {
this.TestCase( SECTION,
"String.indexOf("+String.fromCharCode(i)+ ", "+k+1+")",
-1,
TEST_STRING.indexOf( String.fromCharCode(i), k+1 ) );
}

for ( k = 0, i = 0x0020; i < 0x007d; i++, j++, k++ ) {
this.TestCase( SECTION,
"String.indexOf("+(String.fromCharCode(i) +
String.fromCharCode(i+1)+
String.fromCharCode(i+2)) +", "+0+")",
k,
TEST_STRING.indexOf( (String.fromCharCode(i)+
String.fromCharCode(i+1)+
String.fromCharCode(i+2)),
0 ) );
}

for ( k = 0, i = 0x0020; i < 0x007d; i++, j++, k++ ) {
this.TestCase( SECTION,
"String.indexOf("+(String.fromCharCode(i) +
String.fromCharCode(i+1)+
String.fromCharCode(i+2)) +", "+ k +")",
k,
TEST_STRING.indexOf( (String.fromCharCode(i)+
String.fromCharCode(i+1)+
String.fromCharCode(i+2)),
k ) );
}
for ( k = 0, i = 0x0020; i < 0x007d; i++, j++, k++ ) {
this.TestCase( SECTION,
"String.indexOf("+(String.fromCharCode(i) +
String.fromCharCode(i+1)+
String.fromCharCode(i+2)) +", "+ k+1 +")",
-1,
TEST_STRING.indexOf( (String.fromCharCode(i)+
String.fromCharCode(i+1)+
String.fromCharCode(i+2)),
k+1 ) );
}

this.TestCase( SECTION,  "String.indexOf(" +TEST_STRING + ", 0 )", 0, TEST_STRING.indexOf( TEST_STRING, 0 ) );

this.TestCase( SECTION,  "String.indexOf(" +TEST_STRING + ", 1 )", -1, TEST_STRING.indexOf( TEST_STRING, 1 ));

//print( "TEST_STRING = new String(\" !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\")" );


//test();

},

/**
File Name:          15.5.4.6-1.js
ECMA Section:       15.5.4.6 String.prototype.indexOf( searchString, pos)
Description:        If the given searchString appears as a substring of the
result of converting this object to a string, at one or
more positions that are at or to the right of the
specified position, then the index of the leftmost such
position is returned; otherwise -1 is returned.  If
positionis undefined or not supplied, 0 is assumed, so
as to search all of the string.

When the indexOf method is called with two arguments,
searchString and pos, the following steps are taken:

1. Call ToString, giving it the this value as its
argument.
2. Call ToString(searchString).
3. Call ToInteger(position). (If position is undefined
or not supplied, this step produces the value 0).
4. Compute the number of characters in Result(1).
5. Compute min(max(Result(3), 0), Result(4)).
6. Compute the number of characters in the string that
is Result(2).
7. Compute the smallest possible integer k not smaller
than Result(5) such that k+Result(6) is not greater
than Result(4), and for all nonnegative integers j
less than Result(6), the character at position k+j
of Result(1) is the same as the character at position
j of Result(2); but if there is no such integer k,
then compute the value -1.
8. Return Result(7).

Note that the indexOf function is intentionally generic;
it does not require that its this value be a String object.
Therefore it can be transferred to other kinds of objects
for use as a method.

Author:             christine@netscape.com, pschwartau@netscape.com
Date:               02 October 1997
Modified:           14 July 2002
Reason:             See http://bugzilla.mozilla.org/show_bug.cgi?id=155289
ECMA-262 Ed.3  Section 15.5.4.7
The length property of the indexOf method is 1
*
*/
test_15_5_4_6__2: function() {
var SECTION = "15.5.4.6-2";
var VERSION = "ECMA_1";
var TITLE   = "String.protoype.indexOf";
var BUGNUMBER="105721";

//startTest();

//writeHeaderToLog( SECTION + " "+ TITLE);

// the following test regresses http://scopus/bugsplat/show_bug.cgi?id=105721

// regress http://scopus/bugsplat/show_bug.cgi?id=105721

var GLOBAL = "[object Object]";
this.TestCase( SECTION,
"function f() { return this; }; function g() { var h = f; return h(); }; g().toString()",
GLOBAL,
g().toString()
);


this.TestCase( SECTION, "String.prototype.indexOf.length",                                               1,     String.prototype.indexOf.length );
this.TestCase( SECTION, "String.prototype.indexOf.length = null; String.prototype.indexOf.length",       1,     eval("String.prototype.indexOf.length = null; String.prototype.indexOf.length") );
this.TestCase( SECTION, "delete String.prototype.indexOf.length",                                        false,  delete String.prototype.indexOf.length );
this.TestCase( SECTION, "delete String.prototype.indexOf.length; String.prototype.indexOf.length",       1,      eval("delete String.prototype.indexOf.length; String.prototype.indexOf.length") );

this.TestCase( SECTION,
"var s = new String(); s.indexOf()",
-1,
eval("var s = new String(); s.indexOf()") );

// some Unicode tests.

// generate a test string.

var TEST_STRING = "";
var u = null;
for ( u = 0x00A1; u <= 0x00FF; u++ ) {
TEST_STRING += String.fromCharCode( u );
}

for ( u = 0x00A1, i = 0; u <= 0x00FF; u++, i++ ) {
this.TestCase(   SECTION,
"TEST_STRING.indexOf( " + String.fromCharCode(u) + " )",
i,
TEST_STRING.indexOf( String.fromCharCode(u) ) );
}
for ( u = 0x00A1, i = 0; u <= 0x00FF; u++, i++ ) {
this.TestCase(   SECTION,
"TEST_STRING.indexOf( " + String.fromCharCode(u) + ", void 0 )",
i,
TEST_STRING.indexOf( String.fromCharCode(u), void 0 ) );
}



var foo = new MyObject('hello');

this.TestCase( SECTION, "var foo = new MyObject('hello');foo.indexOf('h')", 0, foo.indexOf("h")  );
this.TestCase( SECTION, "var foo = new MyObject('hello');foo.indexOf('e')", 1, foo.indexOf("e")  );
this.TestCase( SECTION, "var foo = new MyObject('hello');foo.indexOf('l')", 2, foo.indexOf("l")  );
this.TestCase( SECTION, "var foo = new MyObject('hello');foo.indexOf('l')", 2, foo.indexOf("l")  );
this.TestCase( SECTION, "var foo = new MyObject('hello');foo.indexOf('o')", 4, foo.indexOf("o")  );
this.TestCase( SECTION, "var foo = new MyObject('hello');foo.indexOf('X')", -1,  foo.indexOf("X")  );
this.TestCase( SECTION, "var foo = new MyObject('hello');foo.indexOf(5) ", -1,  foo.indexOf(5)  );

var boo = new MyObject(true);

this.TestCase( SECTION, "var boo = new MyObject(true);boo.indexOf('t')", 0, boo.indexOf("t")  );
this.TestCase( SECTION, "var boo = new MyObject(true);boo.indexOf('r')", 1, boo.indexOf("r")  );
this.TestCase( SECTION, "var boo = new MyObject(true);boo.indexOf('u')", 2, boo.indexOf("u")  );
this.TestCase( SECTION, "var boo = new MyObject(true);boo.indexOf('e')", 3, boo.indexOf("e")  );
this.TestCase( SECTION, "var boo = new MyObject(true);boo.indexOf('true')", 0, boo.indexOf("true")  );
this.TestCase( SECTION, "var boo = new MyObject(true);boo.indexOf('rue')", 1, boo.indexOf("rue")  );
this.TestCase( SECTION, "var boo = new MyObject(true);boo.indexOf('ue')", 2, boo.indexOf("ue")  );
this.TestCase( SECTION, "var boo = new MyObject(true);boo.indexOf('oy')", -1, boo.indexOf("oy")  );


var noo = new MyObject( Math.PI );
this.TestCase( SECTION, "var noo = new MyObject(Math.PI); noo.indexOf('3') ", 0, noo.indexOf('3')  );
this.TestCase( SECTION, "var noo = new MyObject(Math.PI); noo.indexOf('.') ", 1, noo.indexOf('.')  );
this.TestCase( SECTION, "var noo = new MyObject(Math.PI); noo.indexOf('1') ", 2, noo.indexOf('1')  );
this.TestCase( SECTION, "var noo = new MyObject(Math.PI); noo.indexOf('4') ", 3, noo.indexOf('4')  );
this.TestCase( SECTION, "var noo = new MyObject(Math.PI); noo.indexOf('1') ", 2, noo.indexOf('1')  );
this.TestCase( SECTION, "var noo = new MyObject(Math.PI); noo.indexOf('5') ", 5, noo.indexOf('5')  );
this.TestCase( SECTION, "var noo = new MyObject(Math.PI); noo.indexOf('9') ", 6, noo.indexOf('9')  );

this.TestCase( SECTION,
"var arr = new Array('new','zoo','revue'); arr.indexOf = String.prototype.indexOf; arr.indexOf('new')",
0,
eval("var arr = new Array('new','zoo','revue'); arr.indexOf = String.prototype.indexOf; arr.indexOf('new')") );

this.TestCase( SECTION,
"var arr = new Array('new','zoo','revue'); arr.indexOf = String.prototype.indexOf; arr.indexOf(',zoo,')",
3,
eval("var arr = new Array('new','zoo','revue'); arr.indexOf = String.prototype.indexOf; arr.indexOf(',zoo,')") );

this.TestCase( SECTION,
"var obj = new Object(); obj.indexOf = String.prototype.indexOf; obj.indexOf('[object Object]')",
0,
eval("var obj = new Object(); obj.indexOf = String.prototype.indexOf; obj.indexOf('[object Object]')") );

this.TestCase( SECTION,
"var obj = new Object(); obj.indexOf = String.prototype.indexOf; obj.indexOf('bject')",
2,
eval("var obj = new Object(); obj.indexOf = String.prototype.indexOf; obj.indexOf('bject')") );

this.TestCase( SECTION,
"var f = new Object( String.prototype.indexOf ); f('"+GLOBAL+"')",
0,
eval("var f = new Object( String.prototype.indexOf ); f('"+GLOBAL+"')") );

this.TestCase( SECTION,
"var f = new Function(); f.toString = Object.prototype.toString; f.indexOf = String.prototype.indexOf; f.indexOf('[object Function]')",
0,
eval("var f = new Function(); f.toString = Object.prototype.toString; f.indexOf = String.prototype.indexOf; f.indexOf('[object Function]')") );

this.TestCase( SECTION,
"var b = new Boolean(); b.indexOf = String.prototype.indexOf; b.indexOf('true')",
-1,
eval("var b = new Boolean(); b.indexOf = String.prototype.indexOf; b.indexOf('true')") );

this.TestCase( SECTION,
"var b = new Boolean(); b.indexOf = String.prototype.indexOf; b.indexOf('false', 1)",
-1,
eval("var b = new Boolean(); b.indexOf = String.prototype.indexOf; b.indexOf('false', 1)") );

this.TestCase( SECTION,
"var b = new Boolean(); b.indexOf = String.prototype.indexOf; b.indexOf('false', 0)",
0,
eval("var b = new Boolean(); b.indexOf = String.prototype.indexOf; b.indexOf('false', 0)") );

this.TestCase( SECTION,
"var n = new Number(1e21); n.indexOf = String.prototype.indexOf; n.indexOf('e')",
1,
eval("var n = new Number(1e21); n.indexOf = String.prototype.indexOf; n.indexOf('e')") );

this.TestCase( SECTION,
"var n = new Number(-Infinity); n.indexOf = String.prototype.indexOf; n.indexOf('-')",
0,
eval("var n = new Number(-Infinity); n.indexOf = String.prototype.indexOf; n.indexOf('-')") );

this.TestCase( SECTION,
"var n = new Number(0xFF); n.indexOf = String.prototype.indexOf; n.indexOf('5')",
1,
eval("var n = new Number(0xFF); n.indexOf = String.prototype.indexOf; n.indexOf('5')") );

this.TestCase( SECTION,
"var m = Math; m.indexOf = String.prototype.indexOf; m.indexOf( 'Math' )",
8,
eval("var m = Math; m.indexOf = String.prototype.indexOf; m.indexOf( 'Math' )") );

// new Date(0) has '31' or '01' at index 8 depending on whether tester is (GMT-) or (GMT+), respectively
this.TestCase( SECTION,
"var d = new Date(0); d.indexOf = String.prototype.indexOf; d.getTimezoneOffset()>0 ? d.indexOf('31') : d.indexOf('01')",
8,
eval("var d = new Date(0); d.indexOf = String.prototype.indexOf; d.getTimezoneOffset()>0 ? d.indexOf('31') : d.indexOf('01')") );

//test();

function f() {
return this;
}
function g() {
var h = f;
return h();
}

function MyObject (v) {
this.value      = v;
this.toString   = new Function ( "return this.value +\"\"");
this.indexOf     = String.prototype.indexOf;
}

},

/**
File Name:          15.5.4.7-1.js
ECMA Section:       15.5.4.7 String.prototype.lastIndexOf( searchString, pos)
Description:

If the given searchString appears as a substring of the result of
converting this object to a string, at one or more positions that are
at or to the left of the specified position, then the index of the
rightmost such position is returned; otherwise -1 is returned. If position
is undefined or not supplied, the length of this string value is assumed,
so as to search all of the string.

When the lastIndexOf method is called with two arguments searchString and
position, the following steps are taken:

1.Call ToString, giving it the this value as its argument.
2.Call ToString(searchString).
3.Call ToNumber(position). (If position is undefined or not supplied, this step produces the value NaN).
4.If Result(3) is NaN, use +; otherwise, call ToInteger(Result(3)).
5.Compute the number of characters in Result(1).
6.Compute min(max(Result(4), 0), Result(5)).
7.Compute the number of characters in the string that is Result(2).
8.Compute the largest possible integer k not larger than Result(6) such that k+Result(7) is not greater
than Result(5), and for all nonnegative integers j less than Result(7), the character at position k+j of
Result(1) is the same as the character at position j of Result(2); but if there is no such integer k, then
compute the value -1.

1.Return Result(8).

Note that the lastIndexOf function is intentionally generic; it does not require that its this value be a
String object. Therefore it can be transferred to other kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               2 october 1997
*/
test_15_5_4_7__1: function() {
var SECTION = "15.5.4.7-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.protoype.lastIndexOf";

var TEST_STRING = new String( " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~" );

//writeHeaderToLog( SECTION + " "+ TITLE);

var j = 0;

for ( k = 0, i = 0x0021; i < 0x007e; i++, j++, k++ ) {
this.TestCase( SECTION,
"String.lastIndexOf(" +String.fromCharCode(i)+ ", 0)",
-1,
TEST_STRING.lastIndexOf( String.fromCharCode(i), 0 ) );
}

for ( k = 0, i = 0x0020; i < 0x007e; i++, j++, k++ ) {
this.TestCase( SECTION,
"String.lastIndexOf("+String.fromCharCode(i)+ ", "+ k +")",
k,
TEST_STRING.lastIndexOf( String.fromCharCode(i), k ) );
}

for ( k = 0, i = 0x0020; i < 0x007e; i++, j++, k++ ) {
this.TestCase( SECTION,
"String.lastIndexOf("+String.fromCharCode(i)+ ", "+k+1+")",
k,
TEST_STRING.lastIndexOf( String.fromCharCode(i), k+1 ) );
}

for ( k = 9, i = 0x0021; i < 0x007d; i++, j++, k++ ) {
this.TestCase( SECTION,

"String.lastIndexOf("+(String.fromCharCode(i) +
String.fromCharCode(i+1)+
String.fromCharCode(i+2)) +", "+ 0 + ")",
LastIndexOf( TEST_STRING, String.fromCharCode(i) +
String.fromCharCode(i+1)+String.fromCharCode(i+2), 0),
TEST_STRING.lastIndexOf( (String.fromCharCode(i)+
String.fromCharCode(i+1)+
String.fromCharCode(i+2)),
0 ) );
}

for ( k = 0, i = 0x0020; i < 0x007d; i++, j++, k++ ) {
this.TestCase( SECTION,
"String.lastIndexOf("+(String.fromCharCode(i) +
String.fromCharCode(i+1)+
String.fromCharCode(i+2)) +", "+ k +")",
k,
TEST_STRING.lastIndexOf( (String.fromCharCode(i)+
String.fromCharCode(i+1)+
String.fromCharCode(i+2)),
k ) );
}
for ( k = 0, i = 0x0020; i < 0x007d; i++, j++, k++ ) {
this.TestCase( SECTION,
"String.lastIndexOf("+(String.fromCharCode(i) +
String.fromCharCode(i+1)+
String.fromCharCode(i+2)) +", "+ k+1 +")",
k,
TEST_STRING.lastIndexOf( (String.fromCharCode(i)+
String.fromCharCode(i+1)+
String.fromCharCode(i+2)),
k+1 ) );
}
for ( k = 0, i = 0x0020; i < 0x007d; i++, j++, k++ ) {
this.TestCase( SECTION,
"String.lastIndexOf("+
(String.fromCharCode(i) +
String.fromCharCode(i+1)+
String.fromCharCode(i+2)) +", "+ (k-1) +")",
LastIndexOf( TEST_STRING, String.fromCharCode(i) +
String.fromCharCode(i+1)+String.fromCharCode(i+2), k-1),
TEST_STRING.lastIndexOf( (String.fromCharCode(i)+
String.fromCharCode(i+1)+
String.fromCharCode(i+2)),
k-1 ) );
}

this.TestCase( SECTION,  "String.lastIndexOf(" +TEST_STRING + ", 0 )", 0, TEST_STRING.lastIndexOf( TEST_STRING, 0 ) );

// new TestCase( SECTION,  "String.lastIndexOf(" +TEST_STRING + ", 1 )", 0, TEST_STRING.lastIndexOf( TEST_STRING, 1 ));

this.TestCase( SECTION,  "String.lastIndexOf(" +TEST_STRING + ")", 0, TEST_STRING.lastIndexOf( TEST_STRING ));

//print( "TEST_STRING = new String(\" !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\")" );

//test();

function LastIndexOf( string, search, position ) {
var result5;
var result6;
var result7;
var result8;
string = String( string );
search = String( search );

position = Number( position )

if ( isNaN( position ) ) {
position = Infinity;
} else {
position = ToInteger( position );
}

result5= string.length;
result6 = Math.min(Math.max(position, 0), result5);
result7 = search.length;

if (result7 == 0) {
return Math.min(position, result5);
}

result8 = -1;

for ( k = 0; k <= result6; k++ ) {
if ( k+ result7 > result5 ) {
break;
}
for ( j = 0; j < result7; j++ ) {
if ( string.charAt(k+j) != search.charAt(j) ){
break;
}   else  {
if ( j == result7 -1 ) {
result8 = k;
}
}
}
}

return result8;
}
function ToInteger( n ) {
n = Number( n );
if ( isNaN(n) ) {
return 0;
}
if ( Math.abs(n) == 0 || Math.abs(n) == Infinity ) {
return n;
}

var sign = ( n < 0 ) ? -1 : 1;

return ( sign * Math.floor(Math.abs(n)) );
}

},

/**
File Name:          15.5.4.7-2.js
ECMA Section:       15.5.4.7 String.prototype.lastIndexOf( searchString, pos)
Description:

If the given searchString appears as a substring of the result of
converting this object to a string, at one or more positions that are
at or to the left of the specified position, then the index of the
rightmost such position is returned; otherwise -1 is returned. If position
is undefined or not supplied, the length of this string value is assumed,
so as to search all of the string.

When the lastIndexOf method is called with two arguments searchString and
position, the following steps are taken:

1.Call ToString, giving it the this value as its argument.
2.Call ToString(searchString).
3.Call ToNumber(position). (If position is undefined or not supplied, this step produces the value NaN).
4.If Result(3) is NaN, use +; otherwise, call ToInteger(Result(3)).
5.Compute the number of characters in Result(1).
6.Compute min(max(Result(4), 0), Result(5)).
7.Compute the number of characters in the string that is Result(2).
8.Compute the largest possible integer k not larger than Result(6) such that k+Result(7) is not greater
than Result(5), and for all nonnegative integers j less than Result(7), the character at position k+j of
Result(1) is the same as the character at position j of Result(2); but if there is no such integer k, then
compute the value -1.

1.Return Result(8).

Note that the lastIndexOf function is intentionally generic; it does not require that its this value be a
String object. Therefore it can be transferred to other kinds of objects for use as a method.

Author:             christine@netscape.com, pschwartau@netscape.com
Date:               02 October 1997
Modified:           14 July 2002
Reason:             See http://bugzilla.mozilla.org/show_bug.cgi?id=155289
ECMA-262 Ed.3  Section 15.5.4.8
The length property of the lastIndexOf method is 1
*
*/
test_15_5_4_7__2: function() {
var SECTION = "15.5.4.7-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.protoype.lastIndexOf";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION, "String.prototype.lastIndexOf.length",           1,          String.prototype.lastIndexOf.length );
this.TestCase( SECTION, "delete String.prototype.lastIndexOf.length",    false,      delete String.prototype.lastIndexOf.length );
this.TestCase( SECTION, "delete String.prototype.lastIndexOf.length; String.prototype.lastIndexOf.length",   1,  eval("delete String.prototype.lastIndexOf.length; String.prototype.lastIndexOf.length" ) );

this.TestCase( SECTION, "var s = new String(''); s.lastIndexOf('', 0)",          LastIndexOf("","",0),  eval("var s = new String(''); s.lastIndexOf('', 0)") );
this.TestCase( SECTION, "var s = new String(''); s.lastIndexOf('')",             LastIndexOf("",""),  eval("var s = new String(''); s.lastIndexOf('')") );
this.TestCase( SECTION, "var s = new String('hello'); s.lastIndexOf('', 0)",     LastIndexOf("hello","",0),  eval("var s = new String('hello'); s.lastIndexOf('',0)") );
this.TestCase( SECTION, "var s = new String('hello'); s.lastIndexOf('')",        LastIndexOf("hello",""),  eval("var s = new String('hello'); s.lastIndexOf('')") );

this.TestCase( SECTION, "var s = new String('hello'); s.lastIndexOf('ll')",     LastIndexOf("hello","ll"),  eval("var s = new String('hello'); s.lastIndexOf('ll')") );
this.TestCase( SECTION, "var s = new String('hello'); s.lastIndexOf('ll', 0)",  LastIndexOf("hello","ll",0),  eval("var s = new String('hello'); s.lastIndexOf('ll', 0)") );
this.TestCase( SECTION, "var s = new String('hello'); s.lastIndexOf('ll', 1)",  LastIndexOf("hello","ll",1),  eval("var s = new String('hello'); s.lastIndexOf('ll', 1)") );
this.TestCase( SECTION, "var s = new String('hello'); s.lastIndexOf('ll', 2)",  LastIndexOf("hello","ll",2),  eval("var s = new String('hello'); s.lastIndexOf('ll', 2)") );
this.TestCase( SECTION, "var s = new String('hello'); s.lastIndexOf('ll', 3)",  LastIndexOf("hello","ll",3),  eval("var s = new String('hello'); s.lastIndexOf('ll', 3)") );
this.TestCase( SECTION, "var s = new String('hello'); s.lastIndexOf('ll', 4)",  LastIndexOf("hello","ll",4),  eval("var s = new String('hello'); s.lastIndexOf('ll', 4)") );
this.TestCase( SECTION, "var s = new String('hello'); s.lastIndexOf('ll', 5)",  LastIndexOf("hello","ll",5),  eval("var s = new String('hello'); s.lastIndexOf('ll', 5)") );
this.TestCase( SECTION, "var s = new String('hello'); s.lastIndexOf('ll', 6)",  LastIndexOf("hello","ll",6),  eval("var s = new String('hello'); s.lastIndexOf('ll', 6)") );

this.TestCase( SECTION, "var s = new String('hello'); s.lastIndexOf('ll', 1.5)", LastIndexOf('hello','ll', 1.5), eval("var s = new String('hello'); s.lastIndexOf('ll', 1.5)") );
this.TestCase( SECTION, "var s = new String('hello'); s.lastIndexOf('ll', 2.5)", LastIndexOf('hello','ll', 2.5),  eval("var s = new String('hello'); s.lastIndexOf('ll', 2.5)") );
this.TestCase( SECTION, "var s = new String('hello'); s.lastIndexOf('ll', -1)",  LastIndexOf('hello','ll', -1), eval("var s = new String('hello'); s.lastIndexOf('ll', -1)") );
this.TestCase( SECTION, "var s = new String('hello'); s.lastIndexOf('ll', -1.5)",LastIndexOf('hello','ll', -1.5), eval("var s = new String('hello'); s.lastIndexOf('ll', -1.5)") );

this.TestCase( SECTION, "var s = new String('hello'); s.lastIndexOf('ll', -Infinity)",    LastIndexOf("hello","ll",-Infinity), eval("var s = new String('hello'); s.lastIndexOf('ll', -Infinity)") );
this.TestCase( SECTION, "var s = new String('hello'); s.lastIndexOf('ll', Infinity)",    LastIndexOf("hello","ll",Infinity), eval("var s = new String('hello'); s.lastIndexOf('ll', Infinity)") );
this.TestCase( SECTION, "var s = new String('hello'); s.lastIndexOf('ll', NaN)",    LastIndexOf("hello","ll",NaN), eval("var s = new String('hello'); s.lastIndexOf('ll', NaN)") );
this.TestCase( SECTION, "var s = new String('hello'); s.lastIndexOf('ll', -0)",    LastIndexOf("hello","ll",-0), eval("var s = new String('hello'); s.lastIndexOf('ll', -0)") );
for ( i = 0; i < ( "[object Object]" ).length; i++ ) {
this.TestCase(   SECTION,
"var o = new Object(); o.lastIndexOf = String.prototype.lastIndexOf; o.lastIndexOf('b', "+ i + ")",
( i < 2 ? -1 : ( i < 9  ? 2 : 9 )) ,
eval("var o = new Object(); o.lastIndexOf = String.prototype.lastIndexOf; o.lastIndexOf('b', "+ i + ")") );
}
for ( i = 0; i < 5; i ++ ) {
this.TestCase(   SECTION,
"var b = new Boolean(); b.lastIndexOf = String.prototype.lastIndexOf; b.lastIndexOf('l', "+ i + ")",
( i < 2 ? -1 : 2 ),
eval("var b = new Boolean(); b.lastIndexOf = String.prototype.lastIndexOf; b.lastIndexOf('l', "+ i + ")") );
}
for ( i = 0; i < 5; i ++ ) {
this.TestCase(   SECTION,
"var b = new Boolean(); b.toString = Object.prototype.toString; b.lastIndexOf = String.prototype.lastIndexOf; b.lastIndexOf('o', "+ i + ")",
( i < 1 ? -1 : ( i < 9 ? 1 : ( i < 10 ? 9 : 10 ) ) ),
eval("var b = new Boolean();  b.toString = Object.prototype.toString; b.lastIndexOf = String.prototype.lastIndexOf; b.lastIndexOf('o', "+ i + ")") );
}
for ( i = 0; i < 9; i++ ) {
this.TestCase(   SECTION,
"var n = new Number(Infinity); n.lastIndexOf = String.prototype.lastIndexOf; n.lastIndexOf( 'i', " + i + " )",
( i < 3 ? -1 : ( i < 5 ? 3 : 5 ) ),
eval("var n = new Number(Infinity); n.lastIndexOf = String.prototype.lastIndexOf; n.lastIndexOf( 'i', " + i + " )") );
}
var a = new Array( "abc","def","ghi","jkl","mno","pqr","stu","vwx","yz" );

for ( i = 0; i < (a.toString()).length; i++ ) {
this.TestCase( SECTION,
"var a = new Array( 'abc','def','ghi','jkl','mno','pqr','stu','vwx','yz' ); a.lastIndexOf = String.prototype.lastIndexOf; a.lastIndexOf( ',mno,p', "+i+" )",
( i < 15 ? -1 : 15 ),
eval("var a = new Array( 'abc','def','ghi','jkl','mno','pqr','stu','vwx','yz' ); a.lastIndexOf = String.prototype.lastIndexOf; a.lastIndexOf( ',mno,p', "+i+" )") );
}

for ( i = 0; i < 15; i ++ ) {
this.TestCase(   SECTION,
"var m = Math; m.lastIndexOf = String.prototype.lastIndexOf; m.lastIndexOf('t', "+ i + ")",
( i < 6 ? -1 : ( i < 10 ? 6 : 10 ) ),
eval("var m = Math; m.lastIndexOf = String.prototype.lastIndexOf; m.lastIndexOf('t', "+ i + ")") );
}
/*
for ( i = 0; i < 15; i++ ) {
new TestCase(   SECTION,
"var d = new Date(); d.lastIndexOf = String.prototype.lastIndexOf; d.lastIndexOf( '0' )",
)
}

*/

//test();

function LastIndexOf( string, search, position ) {
var result5;
var result6;
var result7;
var result8;
string = String( string );
search = String( search );

position = Number( position )

if ( isNaN( position ) ) {
position = Infinity;
} else {
position = ToInteger( position );
}

result5= string.length;
result6 = Math.min(Math.max(position, 0), result5);
result7 = search.length;

if (result7 == 0) {
return Math.min(position, result5);
}

result8 = -1;

for ( k = 0; k <= result6; k++ ) {
if ( k+ result7 > result5 ) {
break;
}
for ( j = 0; j < result7; j++ ) {
if ( string.charAt(k+j) != search.charAt(j) ){
break;
}   else  {
if ( j == result7 -1 ) {
result8 = k;
}
}
}
}

return result8;
}
function ToInteger( n ) {
n = Number( n );
if ( isNaN(n) ) {
return 0;
}
if ( Math.abs(n) == 0 || Math.abs(n) == Infinity ) {
return n;
}

var sign = ( n < 0 ) ? -1 : 1;

return ( sign * Math.floor(Math.abs(n)) );
}

},

/**
File Name:          15.5.4.8-1.js
ECMA Section:       15.5.4.8 String.prototype.split( separator )
Description:

Returns an Array object into which substrings of the result of converting
this object to a string have been stored. The substrings are determined by
searching from left to right for occurrences of the given separator; these
occurrences are not part of any substring in the returned array, but serve
to divide up this string value. The separator may be a string of any length.

As a special case, if the separator is the empty string, the string is split
up into individual characters; the length of the result array equals the
length of the string, and each substring contains one character.

If the separator is not supplied, then the result array contains just one
string, which is the string.

Author:    christine@netscape.com, pschwartau@netscape.com
Date:      12 November 1997
Modified:  14 July 2002
Reason:    See http://bugzilla.mozilla.org/show_bug.cgi?id=155289
ECMA-262 Ed.3  Section 15.5.4.14
The length property of the split method is 2
*
*/
test_15_5_4_8__1: function() {
var SECTION = "15.5.4.8-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.split";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,  "String.prototype.split.length",        2,          String.prototype.split.length );
this.TestCase( SECTION,  "delete String.prototype.split.length", false,      delete String.prototype.split.length );
this.TestCase( SECTION,  "delete String.prototype.split.length; String.prototype.split.length", 2,      eval("delete String.prototype.split.length; String.prototype.split.length") );

// test cases for when split is called with no arguments.

// this is a string object

this.TestCase(   SECTION,
"var s = new String('this is a string object'); typeof s.split()",
"object",
eval("var s = new String('this is a string object'); typeof s.split()") );

this.TestCase(   SECTION,
"var s = new String('this is a string object'); Array.prototype.getClass = Object.prototype.toString; (s.split()).getClass()",
"[object Array]",
eval("var s = new String('this is a string object'); Array.prototype.getClass = Object.prototype.toString; (s.split()).getClass()") );

this.TestCase(   SECTION,
"var s = new String('this is a string object'); s.split().length",
1,
eval("var s = new String('this is a string object'); s.split().length") );

this.TestCase(   SECTION,
"var s = new String('this is a string object'); s.split()[0]",
"this is a string object",
eval("var s = new String('this is a string object'); s.split()[0]") );

// this is an object object
this.TestCase(   SECTION,
"var obj = new Object(); obj.split = String.prototype.split; typeof obj.split()",
"object",
eval("var obj = new Object(); obj.split = String.prototype.split; typeof obj.split()") );

this.TestCase(   SECTION,
"var obj = new Object(); obj.split = String.prototype.split; Array.prototype.getClass = Object.prototype.toString; obj.getClass()",
"[object Array]",
eval("var obj = new Object(); obj.split = String.prototype.split; Array.prototype.getClass = Object.prototype.toString; obj.split().getClass()") );

this.TestCase(   SECTION,
"var obj = new Object(); obj.split = String.prototype.split; obj.split().length",
1,
eval("var obj = new Object(); obj.split = String.prototype.split; obj.split().length") );

this.TestCase(   SECTION,
"var obj = new Object(); obj.split = String.prototype.split; obj.split()[0]",
"[object Object]",
eval("var obj = new Object(); obj.split = String.prototype.split; obj.split()[0]") );

// this is a function object
this.TestCase(   SECTION,
"var obj = new Function(); obj.split = String.prototype.split; typeof obj.split()",
"object",
eval("var obj = new Function(); obj.split = String.prototype.split; typeof obj.split()") );

this.TestCase(   SECTION,
"var obj = new Function(); obj.split = String.prototype.split; Array.prototype.getClass = Object.prototype.toString; obj.getClass()",
"[object Array]",
eval("var obj = new Function(); obj.split = String.prototype.split; Array.prototype.getClass = Object.prototype.toString; obj.split().getClass()") );

this.TestCase(   SECTION,
"var obj = new Function(); obj.split = String.prototype.split; obj.split().length",
1,
eval("var obj = new Function(); obj.split = String.prototype.split; obj.split().length") );

this.TestCase(   SECTION,
"var obj = new Function(); obj.split = String.prototype.split; obj.toString = Object.prototype.toString; obj.split()[0]",
"[object Function]",
eval("var obj = new Function(); obj.split = String.prototype.split; obj.toString = Object.prototype.toString; obj.split()[0]") );

// this is a number object
this.TestCase(   SECTION,
"var obj = new Number(NaN); obj.split = String.prototype.split; typeof obj.split()",
"object",
eval("var obj = new Number(NaN); obj.split = String.prototype.split; typeof obj.split()") );

this.TestCase(   SECTION,
"var obj = new Number(Infinity); obj.split = String.prototype.split; Array.prototype.getClass = Object.prototype.toString; obj.getClass()",
"[object Array]",
eval("var obj = new Number(Infinity); obj.split = String.prototype.split; Array.prototype.getClass = Object.prototype.toString; obj.split().getClass()") );

this.TestCase(   SECTION,
"var obj = new Number(-1234567890); obj.split = String.prototype.split; obj.split().length",
1,
eval("var obj = new Number(-1234567890); obj.split = String.prototype.split; obj.split().length") );

this.TestCase(   SECTION,
"var obj = new Number(-1e21); obj.split = String.prototype.split; obj.split()[0]",
"-1e+21",
eval("var obj = new Number(-1e21); obj.split = String.prototype.split; obj.split()[0]") );


// this is the Math object
this.TestCase(   SECTION,
"var obj = Math; obj.split = String.prototype.split; typeof obj.split()",
"object",
eval("var obj = Math; obj.split = String.prototype.split; typeof obj.split()") );

this.TestCase(   SECTION,
"var obj = Math; obj.split = String.prototype.split; Array.prototype.getClass = Object.prototype.toString; obj.getClass()",
"[object Array]",
eval("var obj = Math; obj.split = String.prototype.split; Array.prototype.getClass = Object.prototype.toString; obj.split().getClass()") );

this.TestCase(   SECTION,
"var obj = Math; obj.split = String.prototype.split; obj.split().length",
1,
eval("var obj = Math; obj.split = String.prototype.split; obj.split().length") );

this.TestCase(   SECTION,
"var obj = Math; obj.split = String.prototype.split; obj.split()[0]",
"[object Math]",
eval("var obj = Math; obj.split = String.prototype.split; obj.split()[0]") );

// this is an array object
this.TestCase(   SECTION,
"var obj = new Array(1,2,3,4,5); obj.split = String.prototype.split; typeof obj.split()",
"object",
eval("var obj = new Array(1,2,3,4,5); obj.split = String.prototype.split; typeof obj.split()") );

this.TestCase(   SECTION,
"var obj = new Array(1,2,3,4,5); obj.split = String.prototype.split; Array.prototype.getClass = Object.prototype.toString; obj.getClass()",
"[object Array]",
eval("var obj = new Array(1,2,3,4,5); obj.split = String.prototype.split; Array.prototype.getClass = Object.prototype.toString; obj.split().getClass()") );

this.TestCase(   SECTION,
"var obj = new Array(1,2,3,4,5); obj.split = String.prototype.split; obj.split().length",
1,
eval("var obj = new Array(1,2,3,4,5); obj.split = String.prototype.split; obj.split().length") );

this.TestCase(   SECTION,
"var obj = new Array(1,2,3,4,5); obj.split = String.prototype.split; obj.split()[0]",
"1,2,3,4,5",
eval("var obj = new Array(1,2,3,4,5); obj.split = String.prototype.split; obj.split()[0]") );

// this is a Boolean object

this.TestCase(   SECTION,
"var obj = new Boolean(); obj.split = String.prototype.split; typeof obj.split()",
"object",
eval("var obj = new Boolean(); obj.split = String.prototype.split; typeof obj.split()") );

this.TestCase(   SECTION,
"var obj = new Boolean(); obj.split = String.prototype.split; Array.prototype.getClass = Object.prototype.toString; obj.getClass()",
"[object Array]",
eval("var obj = new Boolean(); obj.split = String.prototype.split; Array.prototype.getClass = Object.prototype.toString; obj.split().getClass()") );

this.TestCase(   SECTION,
"var obj = new Boolean(); obj.split = String.prototype.split; obj.split().length",
1,
eval("var obj = new Boolean(); obj.split = String.prototype.split; obj.split().length") );

this.TestCase(   SECTION,
"var obj = new Boolean(); obj.split = String.prototype.split; obj.split()[0]",
"false",
eval("var obj = new Boolean(); obj.split = String.prototype.split; obj.split()[0]") );

//test();

},

/**
File Name:          15.5.4.8-2.js
ECMA Section:       15.5.4.8 String.prototype.split( separator )
Description:

Returns an Array object into which substrings of the result of converting
this object to a string have been stored. The substrings are determined by
searching from left to right for occurrences of the given separator; these
occurrences are not part of any substring in the returned array, but serve
to divide up this string value. The separator may be a string of any length.

As a special case, if the separator is the empty string, the string is split
up into individual characters; the length of the result array equals the
length of the string, and each substring contains one character.

If the separator is not supplied, then the result array contains just one
string, which is the string.

When the split method is called with one argument separator, the following steps are taken:

1.   Call ToString, giving it the this value as its argument.
2.   Create a new Array object of length 0 and call it A.
3.   If separator is not supplied, call the [[Put]] method of A with 0 and
Result(1) as arguments, and then return A.
4.   Call ToString(separator).
5.   Compute the number of characters in Result(1).
6.   Compute the number of characters in the string that is Result(4).
7.   Let p be 0.
8.   If Result(6) is zero (the separator string is empty), go to step 17.
9.   Compute the smallest possible integer k not smaller than p such that
k+Result(6) is not greater than Result(5), and for all nonnegative
integers j less than Result(6), the character at position k+j of
Result(1) is the same as the character at position j of Result(2);
but if there is no such integer k, then go to step 14.
10.   Compute a string value equal to the substring of Result(1), consisting
of the characters at positions p through k1, inclusive.
11.   Call the [[Put]] method of A with A.length and Result(10) as arguments.
12.   Let p be k+Result(6).
13.   Go to step 9.
14.   Compute a string value equal to the substring of Result(1), consisting
of the characters from position p to the end of Result(1).
15.   Call the [[Put]] method of A with A.length and Result(14) as arguments.
16.   Return A.
17.   If p equals Result(5), return A.
18.   Compute a string value equal to the substring of Result(1), consisting of
the single character at position p.
19.   Call the [[Put]] method of A with A.length and Result(18) as arguments.
20.   Increase p by 1.
21.   Go to step 17.

Note that the split function is intentionally generic; it does not require that its this value be a String
object. Therefore it can be transferred to other kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_5_4_8__2: function() {
var SECTION = "15.5.4.8-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.split";

//writeHeaderToLog( SECTION + " "+ TITLE);

// case where separator is the empty string.

var TEST_STRING = "this is a string object";

this.TestCase(   SECTION,
"var s = new String( "+ TEST_STRING +" ); s.split('').length",
TEST_STRING.length,
eval("var s = new String( TEST_STRING ); s.split('').length") );
var i = 0;
for ( i = 0; i < TEST_STRING.length; i++ ) {

this.TestCase(   SECTION,
"var s = new String( "+TEST_STRING+" ); s.split('')["+i+"]",
TEST_STRING.charAt(i),
eval("var s = new String( TEST_STRING ); s.split('')["+i+"]") );
}

// case where the value of the separator is undefined.  in this case. the value of the separator
// should be ToString( separator ), or "undefined".

var EXPECT_STRING = new Array( "this", "is", "a", "string", "object" );
TEST_STRING = "thisundefinedisundefinedaundefinedstringundefinedobject";
EXPECT_STRING = new Array( "this", "is", "a", "string", "object" );

this.TestCase(   SECTION,
"var s = new String( "+ TEST_STRING +" ); s.split(void 0).length",
EXPECT_STRING.length,
eval("var s = new String( TEST_STRING ); s.split(void 0).length") );

for ( i = 0; i < EXPECT_STRING.length; i++ ) {
this.TestCase(   SECTION,
"var s = new String( "+TEST_STRING+" ); s.split(void 0)["+i+"]",
EXPECT_STRING[i],
eval("var s = new String( TEST_STRING ); s.split(void 0)["+i+"]") );
}

// case where the value of the separator is null.  in this case the value of the separator is "null".
TEST_STRING = "thisnullisnullanullstringnullobject";

this.TestCase(   SECTION,
"var s = new String( "+ TEST_STRING +" ); s.split(null).length",
EXPECT_STRING.length,
eval("var s = new String( TEST_STRING ); s.split(null).length") );

for ( i = 0; i < EXPECT_STRING.length; i++ ) {
this.TestCase(   SECTION,
"var s = new String( "+TEST_STRING+" ); s.split(null)["+i+"]",
EXPECT_STRING[i],
eval("var s = new String( TEST_STRING ); s.split(null)["+i+"]") );
}

// case where the value of the separator is a boolean.
TEST_STRING = "thistrueistrueatruestringtrueobject";
EXPECT_STRING = new Array( "this", "is", "a", "string", "object" );

this.TestCase(   SECTION,
"var s = new String( "+ TEST_STRING +" ); s.split(true).length",
EXPECT_STRING.length,
eval("var s = new String( TEST_STRING ); s.split(true).length") );

for ( i = 0; i < EXPECT_STRING.length; i++ ) {
this.TestCase(   SECTION,
"var s = new String( "+TEST_STRING+" ); s.split(true)["+i+"]",
EXPECT_STRING[i],
eval("var s = new String( TEST_STRING ); s.split(true)["+i+"]") );
}

// case where the value of the separator is a number
TEST_STRING = "this123is123a123string123object";
EXPECT_STRING = new Array( "this", "is", "a", "string", "object" );

this.TestCase(   SECTION,
"var s = new String( "+ TEST_STRING +" ); s.split(123).length",
EXPECT_STRING.length,
eval("var s = new String( TEST_STRING ); s.split(123).length") );

for ( i = 0; i < EXPECT_STRING.length; i++ ) {
this.TestCase(   SECTION,
"var s = new String( "+TEST_STRING+" ); s.split(123)["+i+"]",
EXPECT_STRING[i],
eval("var s = new String( TEST_STRING ); s.split(123)["+i+"]") );
}


// case where the value of the separator is a number
TEST_STRING = "this123is123a123string123object";
EXPECT_STRING = new Array( "this", "is", "a", "string", "object" );

this.TestCase(   SECTION,
"var s = new String( "+ TEST_STRING +" ); s.split(123).length",
EXPECT_STRING.length,
eval("var s = new String( TEST_STRING ); s.split(123).length") );

for ( i = 0; i < EXPECT_STRING.length; i++ ) {
this.TestCase(   SECTION,
"var s = new String( "+TEST_STRING+" ); s.split(123)["+i+"]",
EXPECT_STRING[i],
eval("var s = new String( TEST_STRING ); s.split(123)["+i+"]") );
}

// case where the separator is not in the string
TEST_STRING = "this is a string";
EXPECT_STRING = new Array( "this is a string" );

this.TestCase(   SECTION,
"var s = new String( " + TEST_STRING + " ); s.split(':').length",
1,
eval("var s = new String( TEST_STRING ); s.split(':').length") );

this.TestCase(   SECTION,
"var s = new String( " + TEST_STRING + " ); s.split(':')[0]",
TEST_STRING,
eval("var s = new String( TEST_STRING ); s.split(':')[0]") );

// case where part but not all of separator is in the string.
TEST_STRING = "this is a string";
EXPECT_STRING = new Array( "this is a string" );
this.TestCase(   SECTION,
"var s = new String( " + TEST_STRING + " ); s.split('strings').length",
1,
eval("var s = new String( TEST_STRING ); s.split('strings').length") );

this.TestCase(   SECTION,
"var s = new String( " + TEST_STRING + " ); s.split('strings')[0]",
TEST_STRING,
eval("var s = new String( TEST_STRING ); s.split('strings')[0]") );

// case where the separator is at the end of the string
TEST_STRING = "this is a string";
EXPECT_STRING = new Array( "this is a " );
this.TestCase(   SECTION,
"var s = new String( " + TEST_STRING + " ); s.split('string').length",
2,
eval("var s = new String( TEST_STRING ); s.split('string').length") );

for ( i = 0; i < EXPECT_STRING.length; i++ ) {
this.TestCase(   SECTION,
"var s = new String( "+TEST_STRING+" ); s.split('string')["+i+"]",
EXPECT_STRING[i],
eval("var s = new String( TEST_STRING ); s.split('string')["+i+"]") );
}

//test();

},

/**
File Name:          15.5.4.8-3.js
ECMA Section:       15.5.4.8 String.prototype.split( separator )
Description:

Returns an Array object into which substrings of the result of converting
this object to a string have been stored. The substrings are determined by
searching from left to right for occurrences of the given separator; these
occurrences are not part of any substring in the returned array, but serve
to divide up this string value. The separator may be a string of any length.

As a special case, if the separator is the empty string, the string is split
up into individual characters; the length of the result array equals the
length of the string, and each substring contains one character.

If the separator is not supplied, then the result array contains just one
string, which is the string.

When the split method is called with one argument separator, the following steps are taken:

1.   Call ToString, giving it the this value as its argument.
2.   Create a new Array object of length 0 and call it A.
3.   If separator is not supplied, call the [[Put]] method of A with 0 and
Result(1) as arguments, and then return A.
4.   Call ToString(separator).
5.   Compute the number of characters in Result(1).
6.   Compute the number of characters in the string that is Result(4).
7.   Let p be 0.
8.   If Result(6) is zero (the separator string is empty), go to step 17.
9.   Compute the smallest possible integer k not smaller than p such that
k+Result(6) is not greater than Result(5), and for all nonnegative
integers j less than Result(6), the character at position k+j of
Result(1) is the same as the character at position j of Result(2);
but if there is no such integer k, then go to step 14.
10.   Compute a string value equal to the substring of Result(1), consisting
of the characters at positions p through k1, inclusive.
11.   Call the [[Put]] method of A with A.length and Result(10) as arguments.
12.   Let p be k+Result(6).
13.   Go to step 9.
14.   Compute a string value equal to the substring of Result(1), consisting
of the characters from position p to the end of Result(1).
15.   Call the [[Put]] method of A with A.length and Result(14) as arguments.
16.   Return A.
17.   If p equals Result(5), return A.
18.   Compute a string value equal to the substring of Result(1), consisting of
the single character at position p.
19.   Call the [[Put]] method of A with A.length and Result(18) as arguments.
20.   Increase p by 1.
21.   Go to step 17.

Note that the split function is intentionally generic; it does not require that its this value be a String
object. Therefore it can be transferred to other kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_5_4_8__3: function() {
var SECTION = "15.5.4.8-3";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.split";

//writeHeaderToLog( SECTION + " "+ TITLE);

var TEST_STRING = "";
var EXPECT = new Array();

// this.toString is the empty string.

this.TestCase(   SECTION,
"var s = new String(); s.split().length",
1,
eval("var s = new String(); s.split().length") );

this.TestCase(   SECTION,
"var s = new String(); s.split()[0]",
"",
eval("var s = new String(); s.split()[0]") );

// this.toString() is the empty string, separator is specified.

this.TestCase(   SECTION,
"var s = new String(); s.split('').length",
0,
eval("var s = new String(); s.split('').length") );

this.TestCase(   SECTION,
"var s = new String(); s.split(' ').length",
1,
eval("var s = new String(); s.split(' ').length") );

// this to string is " "
this.TestCase(   SECTION,
"var s = new String(' '); s.split().length",
1,
eval("var s = new String(' '); s.split().length") );

this.TestCase(   SECTION,
"var s = new String(' '); s.split()[0]",
" ",
eval("var s = new String(' '); s.split()[0]") );

this.TestCase(   SECTION,
"var s = new String(' '); s.split('').length",
1,
eval("var s = new String(' '); s.split('').length") );

this.TestCase(   SECTION,
"var s = new String(' '); s.split('')[0]",
" ",
eval("var s = new String(' '); s.split('')[0]") );

this.TestCase(   SECTION,
"var s = new String(' '); s.split(' ').length",
2,
eval("var s = new String(' '); s.split(' ').length") );

this.TestCase(   SECTION,
"var s = new String(' '); s.split(' ')[0]",
"",
eval("var s = new String(' '); s.split(' ')[0]") );

this.TestCase(   SECTION,
"\"\".split(\"\").length",
0,
("".split("")).length );

this.TestCase(   SECTION,
"\"\".split(\"x\").length",
1,
("".split("x")).length );

this.TestCase(   SECTION,
"\"\".split(\"x\")[0]",
"",
("".split("x"))[0] );

//test();

function Split( string, separator ) {
string = String( string );

var A = new Array();

if ( arguments.length < 2 ) {
A[0] = string;
return A;
}

separator = String( separator );

var str_len = String( string ).length;
var sep_len = String( separator ).length;

var p = 0;
var k = 0;

if ( sep_len == 0 ) {
for ( ; p < str_len; p++ ) {
A[A.length] = String( string.charAt(p) );
}
}
return A;
}

},

/**
File Name:          15.5.4.9-1.js
ECMA Section:       15.5.4.9 String.prototype.substring( start )
Description:

15.5.4.9 String.prototype.substring(start)

Returns a substring of the result of converting this object to a string,
starting from character position start and running to the end of the
string. The result is a string value, not a String object.

If the argument is NaN or negative, it is replaced with zero; if the
argument is larger than the length of the string, it is replaced with the
length of the string.

When the substring method is called with one argument start, the following
steps are taken:

1.Call ToString, giving it the this value as its argument.
2.Call ToInteger(start).
3.Compute the number of characters in Result(1).
4.Compute min(max(Result(2), 0), Result(3)).
5.Return a string whose length is the difference between Result(3) and Result(4),
containing characters from Result(1), namely the characters with indices Result(4)
through Result(3)1, in ascending order.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_5_4_9__1: function() {
var SECTION = "15.5.4.9-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.prototype.substring( start )";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,  "String.prototype.substring.length",        2,          String.prototype.substring.length );
this.TestCase( SECTION,  "delete String.prototype.substring.length", false,      delete String.prototype.substring.length );
this.TestCase( SECTION,  "delete String.prototype.substring.length; String.prototype.substring.length", 2,      eval("delete String.prototype.substring.length; String.prototype.substring.length") );

// test cases for when substring is called with no arguments.

// this is a string object

this.TestCase(   SECTION,
"var s = new String('this is a string object'); typeof s.substring()",
"string",
eval("var s = new String('this is a string object'); typeof s.substring()") );

this.TestCase(   SECTION,
"var s = new String(''); s.substring()",
"",
eval("var s = new String(''); s.substring()") );


this.TestCase(   SECTION,
"var s = new String('this is a string object'); s.substring()",
"this is a string object",
eval("var s = new String('this is a string object'); s.substring()") );

this.TestCase(   SECTION,
"var s = new String('this is a string object'); s.substring(NaN)",
"this is a string object",
eval("var s = new String('this is a string object'); s.substring(NaN)") );


this.TestCase(   SECTION,
"var s = new String('this is a string object'); s.substring(-0.01)",
"this is a string object",
eval("var s = new String('this is a string object'); s.substring(-0.01)") );


this.TestCase(   SECTION,
"var s = new String('this is a string object'); s.substring(s.length)",
"",
eval("var s = new String('this is a string object'); s.substring(s.length)") );

this.TestCase(   SECTION,
"var s = new String('this is a string object'); s.substring(s.length+1)",
"",
eval("var s = new String('this is a string object'); s.substring(s.length+1)") );


this.TestCase(   SECTION,
"var s = new String('this is a string object'); s.substring(Infinity)",
"",
eval("var s = new String('this is a string object'); s.substring(Infinity)") );

this.TestCase(   SECTION,
"var s = new String('this is a string object'); s.substring(-Infinity)",
"this is a string object",
eval("var s = new String('this is a string object'); s.substring(-Infinity)") );

// this is not a String object, start is not an integer


this.TestCase(   SECTION,
"var s = new Array(1,2,3,4,5); s.substring = String.prototype.substring; s.substring()",
"1,2,3,4,5",
eval("var s = new Array(1,2,3,4,5); s.substring = String.prototype.substring; s.substring()") );

this.TestCase(   SECTION,
"var s = new Array(1,2,3,4,5); s.substring = String.prototype.substring; s.substring(true)",
",2,3,4,5",
eval("var s = new Array(1,2,3,4,5); s.substring = String.prototype.substring; s.substring(true)") );

this.TestCase(   SECTION,
"var s = new Array(1,2,3,4,5); s.substring = String.prototype.substring; s.substring('4')",
"3,4,5",
eval("var s = new Array(1,2,3,4,5); s.substring = String.prototype.substring; s.substring('4')") );

this.TestCase(   SECTION,
"var s = new Array(); s.substring = String.prototype.substring; s.substring('4')",
"",
eval("var s = new Array(); s.substring = String.prototype.substring; s.substring('4')") );

// this is an object object
this.TestCase(   SECTION,
"var obj = new Object(); obj.substring = String.prototype.substring; obj.substring(8)",
"Object]",
eval("var obj = new Object(); obj.substring = String.prototype.substring; obj.substring(8)") );

// this is a function object
this.TestCase(   SECTION,
"var obj = new Function(); obj.substring = String.prototype.substring; obj.toString = Object.prototype.toString; obj.substring(8)",
"Function]",
eval("var obj = new Function(); obj.substring = String.prototype.substring; obj.toString = Object.prototype.toString; obj.substring(8)") );
// this is a number object
this.TestCase(   SECTION,
"var obj = new Number(NaN); obj.substring = String.prototype.substring; obj.substring(false)",
"NaN",
eval("var obj = new Number(NaN); obj.substring = String.prototype.substring; obj.substring(false)") );

// this is the Math object
this.TestCase(   SECTION,
"var obj = Math; obj.substring = String.prototype.substring; obj.substring(Math.PI)",
"ject Math]",
eval("var obj = Math; obj.substring = String.prototype.substring; obj.substring(Math.PI)") );

// this is a Boolean object

this.TestCase(   SECTION,
"var obj = new Boolean(); obj.substring = String.prototype.substring; obj.substring(new Array())",
"false",
eval("var obj = new Boolean(); obj.substring = String.prototype.substring; obj.substring(new Array())") );

// this is a user defined object

this.TestCase( SECTION,
"var obj = new MyObject( null ); obj.substring(0)",
"null",
eval( "var obj = new MyObject( null ); obj.substring(0)") );

//test();

function MyObject( value ) {
this.value = value;
this.substring = String.prototype.substring;
this.toString = new Function ( "return this.value+''" );
}

},


/**
File Name:          15.5.4.js
ECMA Section:       15.5.4 Properties of the String prototype object

Description:
Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_5_4: function() {
var SECTION = "15.5.4";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Properties of the String Prototype objecta";

//writeHeaderToLog( SECTION + " "+ TITLE);


this.TestCase( SECTION,
"String.prototype.getClass = Object.prototype.toString; String.prototype.getClass()",
"[object String]",
eval("String.prototype.getClass = Object.prototype.toString; String.prototype.getClass()") );

delete String.prototype.getClass;

this.TestCase( SECTION,
"typeof String.prototype",
"object",
typeof String.prototype );

this.TestCase( SECTION,
"String.prototype.valueOf()",
"",
String.prototype.valueOf() );

this.TestCase( SECTION,
"String.prototype +''",
"",
String.prototype + '' );

this.TestCase( SECTION,
"String.prototype.length",
0,
String.prototype.length );

var prop;
var value;

value = '';
for (prop in "")
{
value += prop;
}
this.TestCase( SECTION,
'String "" has no enumerable properties',
'',
value );

value = '';
for (prop in String.prototype)
{
value += prop;
}
this.TestCase( SECTION,
'String.prototype has no enumerable properties',
'',
value );

//test();

},

/**
File Name:          15.5.5.1
ECMA Section:       String.length
Description:

The number of characters in the String value represented by this String
object.

Once a String object is created, this property is unchanging. It has the
attributes { DontEnum, DontDelete, ReadOnly }.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_5_5_1: function() {
var SECTION = "15.5.5.1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String.length";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase(   SECTION,
"var s = new String(); s.length",
0,
eval("var s = new String(); s.length") );

this.TestCase(   SECTION,
"var s = new String(); s.length = 10; s.length",
0,
eval("var s = new String(); s.length = 10; s.length") );

this.TestCase(   SECTION,
"var s = new String(); var props = ''; for ( var p in s ) {  props += p; };  props",
"",
eval("var s = new String(); var props = ''; for ( var p in s ) {  props += p; };  props") );

this.TestCase(   SECTION,
"var s = new String(); delete s.length",
false,
eval("var s = new String(); delete s.length") );

this.TestCase(   SECTION,
"var s = new String('hello'); delete s.length; s.length",
5,
eval("var s = new String('hello'); delete s.length; s.length") );

//test();

}

})
.endType();








