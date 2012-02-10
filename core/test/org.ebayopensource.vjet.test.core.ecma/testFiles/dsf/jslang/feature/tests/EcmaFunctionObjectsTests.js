vjo.ctype("dsf.jslang.feature.tests.EcmaFunctionObjectsTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({

/**
File Name:          15.3.1.1.js
ECMA Section:       15.3.1.1 The Function Constructor Called as a Function

Description:
When the Function function is called with some arguments p1, p2, . . . , pn, body
(where n might be 0, that is, there are no "p" arguments, and where body might
also not be provided), the following steps are taken:

1.  Create and return a new Function object exactly if the function constructor had
been called with the same arguments (15.3.2.1).

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_3_1_1__1:function(){

var SECTION = "15.3.1.1-1";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "The Function Constructor Called as a Function";

// writeHeaderToLog( SECTION + " "+ TITLE);

var MyObject = Function( "value", "this.value = value; this.valueOf =  Function( 'return this.value' ); this.toString =  Function( 'return String(this.value);' )" );


var myfunc = Function();
myfunc.toString = Object.prototype.toString;

//    not going to test toString here since it is implementation dependent.
//    this.TestCase( SECTION,  "myfunc.toString()",     "function anonymous() { }",    myfunc.toString() );

myfunc.toString = Object.prototype.toString;
this.TestCase(   SECTION,
"myfunc = Function(); myfunc.toString = Object.prototype.toString; myfunc.toString()",
"[object Function]",
myfunc.toString() );

this.TestCase( SECTION,
"myfunc.length",
0,
myfunc.length );

this.TestCase( SECTION,
"myfunc.prototype.toString()",
"[object Object]",
myfunc.prototype.toString() );

this.TestCase( SECTION,
"myfunc.prototype.constructor",
myfunc,
myfunc.prototype.constructor );

this.TestCase( SECTION,
"myfunc.arguments",
null,
myfunc.arguments );

this.TestCase( SECTION,
"var OBJ = new MyObject(true); OBJ.valueOf()",
true,
eval("var OBJ = new MyObject(true); OBJ.valueOf()") );
var OBJ;
this.TestCase( SECTION,
"OBJ.toString()",
"true",
OBJ.toString() );

this.TestCase( SECTION,
"OBJ.toString = Object.prototype.toString; OBJ.toString()",
"[object Object]",
eval("OBJ.toString = Object.prototype.toString; OBJ.toString()") );

this.TestCase( SECTION,
"MyObject.toString = Object.prototype.toString; MyObject.toString()",
"[object Function]",
eval("MyObject.toString = Object.prototype.toString; MyObject.toString()") );

this.TestCase( SECTION,
"MyObject.length",
1,
MyObject.length );

this.TestCase( SECTION,
"MyObject.prototype.constructor",
MyObject,
MyObject.prototype.constructor );

this.TestCase( SECTION,
"MyObject.arguments",
null,
MyObject.arguments );

// test();



},

/**
File Name:          15.3.1.1-2.js
ECMA Section:       15.3.1.1 The Function Constructor Called as a Function
Function(p1, p2, ..., pn, body )

Description:
When the Function function is called with some arguments p1, p2, . . . , pn,
body (where n might be 0, that is, there are no "p" arguments, and where body
might also not be provided), the following steps are taken:

1.  Create and return a new Function object exactly if the function constructor
had been called with the same arguments (15.3.2.1).

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_3_1_1__2:function(){

var SECTION = "15.3.1.1-2";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "The Function Constructor Called as a Function";

// writeHeaderToLog( SECTION + " "+ TITLE);

var myfunc1 =  Function("a","b","c", "return a+b+c" );
var myfunc2 =  Function("a, b, c",   "return a+b+c" );
var myfunc3 =  Function("a,b", "c",  "return a+b+c" );

myfunc1.toString = Object.prototype.toString;
myfunc2.toString = Object.prototype.toString;
myfunc3.toString = Object.prototype.toString;

this.TestCase( SECTION,
"myfunc1 =  Function('a','b','c'); myfunc.toString = Object.prototype.toString; myfunc.toString()",
"[object Function]",
myfunc1.toString() );

this.TestCase( SECTION,
"myfunc1.length",
3,
myfunc1.length );

this.TestCase( SECTION,
"myfunc1.prototype.toString()",
"[object Object]",
myfunc1.prototype.toString() );

this.TestCase( SECTION,
"myfunc1.prototype.constructor",
myfunc1,
myfunc1.prototype.constructor );

this.TestCase( SECTION,
"myfunc1.arguments",
null,
myfunc1.arguments );

this.TestCase( SECTION,
"myfunc1(1,2,3)",
6,
myfunc1(1,2,3) );

this.TestCase( SECTION,
"var MYPROPS = ''; for ( var p in myfunc1.prototype ) { MYPROPS += p; }; MYPROPS",
"",
eval("var MYPROPS = ''; for ( var p in myfunc1.prototype ) { MYPROPS += p; }; MYPROPS") );

this.TestCase( SECTION,
"myfunc2 =  Function('a','b','c'); myfunc.toString = Object.prototype.toString; myfunc.toString()",
"[object Function]",
myfunc2.toString() );

this.TestCase( SECTION,
"myfunc2.length",
3,
myfunc2.length );

this.TestCase( SECTION,
"myfunc2.prototype.toString()",
"[object Object]",
myfunc2.prototype.toString() );

this.TestCase( SECTION,
"myfunc2.prototype.constructor",
myfunc2,
myfunc2.prototype.constructor );

this.TestCase( SECTION,
"myfunc2.arguments",
null,
myfunc2.arguments );

this.TestCase( SECTION,
"myfunc2( 1000, 200, 30 )",
1230,
myfunc2(1000,200,30) );

this.TestCase( SECTION,
"var MYPROPS = ''; for ( var p in myfunc2.prototype ) { MYPROPS += p; }; MYPROPS",
"",
eval("var MYPROPS = ''; for ( var p in myfunc2.prototype ) { MYPROPS += p; }; MYPROPS") );

this.TestCase( SECTION,
"myfunc3 =  Function('a','b','c'); myfunc.toString = Object.prototype.toString; myfunc.toString()",
"[object Function]",
myfunc3.toString() );

this.TestCase( SECTION,
"myfunc3.length",
3,
myfunc3.length );

this.TestCase( SECTION,
"myfunc3.prototype.toString()",
"[object Object]",
myfunc3.prototype.toString() );

this.TestCase( SECTION,
"myfunc3.prototype.valueOf() +''",
"[object Object]",
myfunc3.prototype.valueOf() +'' );

this.TestCase( SECTION,
"myfunc3.prototype.constructor",
myfunc3,
myfunc3.prototype.constructor );

this.TestCase( SECTION,
"myfunc3.arguments",
null,
myfunc3.arguments );

this.TestCase( SECTION,
"myfunc3(-100,100,NaN)",
Number.NaN,
myfunc3(-100,100,NaN) );

this.TestCase( SECTION,
"var MYPROPS = ''; for ( var p in myfunc3.prototype ) { MYPROPS += p; }; MYPROPS",
"",
eval("var MYPROPS = ''; for ( var p in myfunc3.prototype ) { MYPROPS += p; }; MYPROPS") );

// test();

},

/**
File Name:          15.3.1.1-3.js
ECMA Section:       15.3.1.1 The Function Constructor Called as a Function

new Function(p1, p2, ..., pn, body )

Description:        The last argument specifies the body (executable code)
of a function; any preceeding arguments sepcify formal
parameters.

See the text for description of this section.

This test examples from the specification.

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_3_1_1__3:function(){

var SECTION = "15.3.1.1-3";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "The Function Constructor Called as a Function";

// writeHeaderToLog( SECTION + " "+ TITLE);

var args = "";
var i = 0;
for ( i = 0; i < 2000; i++ ) {
args += "arg"+i;
if ( i != 1999 ) {
args += ",";
}
}

var s = "";

for ( i = 0; i < 2000; i++ ) {
s += ".0005";
if ( i != 1999 ) {
s += ",";
}
}

var MyFunc = Function( args, "var r=0; for (var i = 0; i < MyFunc.length; i++ ) { if ( eval('arg'+i) == void 0) break; else r += eval('arg'+i); }; return r");
var MyObject = Function( args, "for (var i = 0; i < MyFunc.length; i++ ) { if ( eval('arg'+i) == void 0) break; eval('this.arg'+i +'=arg'+i); };");

var MY_OB = eval( "MyFunc("+ s +")" );

this.TestCase( SECTION, "MyFunc.length",                       2000,         MyFunc.length );
this.TestCase( SECTION, "var MY_OB = eval('MyFunc(s)')",       1,            MY_OB );
this.TestCase( SECTION, "var MY_OB = eval('MyFunc(s)')",       1,            eval("var MY_OB = MyFunc("+s+"); MY_OB") );

this.TestCase( SECTION, "MyObject.length",                       2000,         MyObject.length );

this.TestCase( SECTION, "FUN1 = Function( 'a','b','c', 'return FUN1.length' ); FUN1.length",     3, eval("FUN1 = Function( 'a','b','c', 'return FUN1.length' ); FUN1.length") );
this.TestCase( SECTION, "FUN1 = Function( 'a','b','c', 'return FUN1.length' ); FUN1()",          3, eval("FUN1 = Function( 'a','b','c', 'return FUN1.length' ); FUN1()") );
this.TestCase( SECTION, "FUN1 = Function( 'a','b','c', 'return FUN1.length' ); FUN1(1,2,3,4,5)", 3, eval("FUN1 = Function( 'a','b','c', 'return FUN1.length' ); FUN1(1,2,3,4,5)") );

// test();

},

/**
File Name:          15.3.2.1.js
ECMA Section:       15.3.2.1 The Function Constructor
new Function(p1, p2, ..., pn, body )

Description:        The last argument specifies the body (executable code)
of a function; any preceeding arguments sepcify formal
parameters.

See the text for description of this section.

This test examples from the specification.

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_3_2_1__1:function(){

var SECTION = "15.3.2.1";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "The Function Constructor";

// writeHeaderToLog( SECTION + " "+ TITLE);

var MyObject = new Function( "value", "this.value = value; this.valueOf = new Function( 'return this.value' ); this.toString = new Function( 'return String(this.value);' )" );

var myfunc = new Function();

//    not going to test toString here since it is implementation dependent.
//    this.TestCase( SECTION,  "myfunc.toString()",     "function anonymous() { }",    myfunc.toString() );

myfunc.toString = Object.prototype.toString;

this.TestCase( SECTION,  "myfunc = new Function(); myfunc.toString = Object.prototype.toString; myfunc.toString()",
"[object Function]",
myfunc.toString() );

this.TestCase( SECTION,
"myfunc.length",
0,
myfunc.length );

this.TestCase( SECTION,
"myfunc.prototype.toString()",
"[object Object]",
myfunc.prototype.toString() );

this.TestCase( SECTION,
"myfunc.prototype.constructor",
myfunc,
myfunc.prototype.constructor );

this.TestCase( SECTION,
"myfunc.arguments",
null,
myfunc.arguments );

this.TestCase( SECTION,
"var OBJ = new MyObject(true); OBJ.valueOf()",
true,
eval("var OBJ = new MyObject(true); OBJ.valueOf()") );
var OBJ;
this.TestCase( SECTION,
"OBJ.toString()",
"true",
OBJ.toString() );

this.TestCase( SECTION,
"OBJ.toString = Object.prototype.toString; OBJ.toString()", "[object Object]",
eval("OBJ.toString = Object.prototype.toString; OBJ.toString()") );

this.TestCase( SECTION,
"MyObject.toString = Object.prototype.toString; MyObject.toString()",
"[object Function]",
eval("MyObject.toString = Object.prototype.toString; MyObject.toString()") );

this.TestCase( SECTION,
"MyObject.length",
1,
MyObject.length );

this.TestCase( SECTION,
"MyObject.prototype.constructor",
MyObject,
MyObject.prototype.constructor );

this.TestCase( SECTION,
"MyObject.arguments",
null,
MyObject.arguments );

// test();

},

/**
File Name:          15.3.2.1.js
ECMA Section:       15.3.2.1 The Function Constructor
new Function(p1, p2, ..., pn, body )

Description:
Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_3_2_1__2:function(){

var SECTION = "15.3.2.1-2";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "The Function Constructor";

// writeHeaderToLog( SECTION + " "+ TITLE);


var myfunc1 = new Function("a","b","c", "return a+b+c" );
var myfunc2 = new Function("a, b, c",   "return a+b+c" );
var myfunc3 = new Function("a,b", "c",  "return a+b+c" );

myfunc1.toString = Object.prototype.toString;
myfunc2.toString = Object.prototype.toString;
myfunc3.toString = Object.prototype.toString;

this.TestCase( SECTION,  "myfunc1 = new Function('a','b','c'); myfunc.toString = Object.prototype.toString; myfunc.toString()",
"[object Function]",
myfunc1.toString() );

this.TestCase( SECTION,  "myfunc1.length",                            3,                      myfunc1.length );
this.TestCase( SECTION,  "myfunc1.prototype.toString()",              "[object Object]",      myfunc1.prototype.toString() );

this.TestCase( SECTION,  "myfunc1.prototype.constructor",             myfunc1,                myfunc1.prototype.constructor );
this.TestCase( SECTION,  "myfunc1.arguments",                         null,                   myfunc1.arguments );
this.TestCase( SECTION,  "myfunc1(1,2,3)",                            6,                      myfunc1(1,2,3) );
this.TestCase( SECTION,  "var MYPROPS = ''; for ( var p in myfunc1.prototype ) { MYPROPS += p; }; MYPROPS",
"",
eval("var MYPROPS = ''; for ( var p in myfunc1.prototype ) { MYPROPS += p; }; MYPROPS") );

this.TestCase( SECTION,  "myfunc2 = new Function('a','b','c'); myfunc.toString = Object.prototype.toString; myfunc.toString()",
"[object Function]",
myfunc2.toString() );
this.TestCase( SECTION,  "myfunc2.length",                            3,                      myfunc2.length );
this.TestCase( SECTION,  "myfunc2.prototype.toString()",              "[object Object]",      myfunc2.prototype.toString() );

this.TestCase( SECTION,  "myfunc2.prototype.constructor",             myfunc2,                 myfunc2.prototype.constructor );
this.TestCase( SECTION,  "myfunc2.arguments",                         null,                   myfunc2.arguments );
this.TestCase( SECTION,  "myfunc2( 1000, 200, 30 )",                 1230,                    myfunc2(1000,200,30) );
this.TestCase( SECTION,  "var MYPROPS = ''; for ( var p in myfunc2.prototype ) { MYPROPS += p; }; MYPROPS",
"",
eval("var MYPROPS = ''; for ( var p in myfunc2.prototype ) { MYPROPS += p; }; MYPROPS") );

this.TestCase( SECTION,  "myfunc3 = new Function('a','b','c'); myfunc.toString = Object.prototype.toString; myfunc.toString()",
"[object Function]",
myfunc3.toString() );
this.TestCase( SECTION,  "myfunc3.length",                            3,                      myfunc3.length );
this.TestCase( SECTION,  "myfunc3.prototype.toString()",              "[object Object]",      myfunc3.prototype.toString() );
this.TestCase( SECTION,  "myfunc3.prototype.valueOf() +''",           "[object Object]",      myfunc3.prototype.valueOf() +'' );
this.TestCase( SECTION,  "myfunc3.prototype.constructor",             myfunc3,                 myfunc3.prototype.constructor );
this.TestCase( SECTION,  "myfunc3.arguments",                         null,                   myfunc3.arguments );
this.TestCase( SECTION,  "myfunc3(-100,100,NaN)",                    Number.NaN,              myfunc3(-100,100,NaN) );

this.TestCase( SECTION,  "var MYPROPS = ''; for ( var p in myfunc3.prototype ) { MYPROPS += p; }; MYPROPS",
"",
eval("var MYPROPS = ''; for ( var p in myfunc3.prototype ) { MYPROPS += p; }; MYPROPS") );
// test();

},

/**
File Name:          15.3.2.1-3.js
ECMA Section:       15.3.2.1 The Function Constructor
new Function(p1, p2, ..., pn, body )

Description:        The last argument specifies the body (executable code)
of a function; any preceeding arguments sepcify formal
parameters.

See the text for description of this section.

This test examples from the specification.

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_3_2_1__3:function(){

var SECTION = "15.3.2.1-3";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "The Function Constructor";

// writeHeaderToLog( SECTION + " "+ TITLE);

var args = "";

var i;
for ( i = 0; i < 2000; i++ ) {
args += "arg"+i;
if ( i != 1999 ) {
args += ",";
}
}

var s = "";
for ( i = 0; i < 2000; i++ ) {
s += ".0005";
if ( i != 1999 ) {
s += ",";
}
}

var MyFunc = new Function( args, "var r=0; for (var i = 0; i < MyFunc.length; i++ ) { if ( eval('arg'+i) == void 0) break; else r += eval('arg'+i); }; return r");
var MyObject = new Function( args, "for (var i = 0; i < MyFunc.length; i++ ) { if ( eval('arg'+i) == void 0) break; eval('this.arg'+i +'=arg'+i); };");

this.TestCase( SECTION, "MyFunc.length",                       2000,         MyFunc.length );
this.TestCase( SECTION, "var MY_OB = eval('MyFunc(s)')",       1,            eval("var MY_OB = MyFunc("+s+"); MY_OB") );

this.TestCase( SECTION, "MyObject.length",                       2000,         MyObject.length );

this.TestCase( SECTION, "FUN1 = new Function( 'a','b','c', 'return FUN1.length' ); FUN1.length",     3, eval("FUN1 = new Function( 'a','b','c', 'return FUN1.length' ); FUN1.length") );
this.TestCase( SECTION, "FUN1 = new Function( 'a','b','c', 'return FUN1.length' ); FUN1()",          3, eval("FUN1 = new Function( 'a','b','c', 'return FUN1.length' ); FUN1()") );
this.TestCase( SECTION, "FUN1 = new Function( 'a','b','c', 'return FUN1.length' ); FUN1(1,2,3,4,5)", 3, eval("FUN1 = new Function( 'a','b','c', 'return FUN1.length' ); FUN1(1,2,3,4,5)") );

// test();

},

/**
File Name:          15.3.3.1-2.js
ECMA Section:       15.3.3.1 Properties of the Function Constructor
Function.prototype

Description:        The initial value of Function.prototype is the built-in
Function prototype object.

This property shall have the attributes [DontEnum |
DontDelete | ReadOnly]

This test the DontEnum property of Function.prototype.

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_3_3_1__2:function(){

var SECTION = "15.3.3.1-2";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "Function.prototype";

// writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase(   SECTION,
"var str='';for (prop in Function ) str += prop; str;",
"",
eval("var str='';for (prop in Function) str += prop; str;")
);
// test();

},

/**
File Name:          15.3.3.1-3.js
ECMA Section:       15.3.3.1 Properties of the Function Constructor
Function.prototype

Description:        The initial value of Function.prototype is the built-in
Function prototype object.

This property shall have the attributes [DontEnum |
DontDelete | ReadOnly]

This test the DontDelete property of Function.prototype.

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_3_3_1__3:function(){

var SECTION = "15.3.3.1-3";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "Function.prototype";

// writeHeaderToLog( SECTION + " "+ TITLE);


var FUN_PROTO ;

this.TestCase(   SECTION,
"delete Function.prototype",
false,
delete FUN_PROTO
);

this.TestCase(   SECTION,
"delete Function.prototype; Function.prototype",
FUN_PROTO,
eval("delete Function.prototype; Function.prototype")
);
// test();

},

/**
File Name:          15.3.3.1-4.js
ECMA Section:       15.3.3.1 Properties of the Function Constructor
Function.prototype

Description:        The initial value of Function.prototype is the built-in
Function prototype object.

This property shall have the attributes [DontEnum |
DontDelete | ReadOnly]

This test the ReadOnly property of Function.prototype.

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_3_3_1__4:function(){

var SECTION = "15.3.3.1-4";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "Function.prototype";

// writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase(   SECTION,
"Function.prototype = null; Function.prototype",
1,
eval("Function.prototype = null; Function.prototype")
);
// test();

},

/**
File Name:          15.3.3.2.js
ECMA Section:       15.3.3.2 Properties of the Function Constructor
Function.length

Description:        The length property is 1.

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_3_3_2:function(){


var SECTION = "15.3.3.2";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "Function.length";

// writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase(   SECTION,    "Function.length",  1, 3 );

// test();

},

/**
File Name:          15.3.4-1.js
ECMA Section:       15.3.4  Properties of the Function Prototype Object

Description:        The Function prototype object is itself a Function
object ( its [[Class]] is "Function") that, when
invoked, accepts any arguments and returns undefined.

The value of the internal [[Prototype]] property
object is the Object prototype object.

It is a function with an "empty body"; if it is
invoked, it merely returns undefined.

The Function prototype object does not have a valueOf
property of its own; however it inherits the valueOf
property from the Object prototype Object.

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_3_4__1:function(){


var SECTION = "15.3.4-1";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "Properties of the Function Prototype Object";

// writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"var myfunc = Function.prototype; myfunc.toString = Object.prototype.toString; myfunc.toString()",
"[object Function]",
eval("var myfunc = Function.prototype; myfunc.toString = Object.prototype.toString; myfunc.toString()"));

},

/**
File Name:          15.3.4.1.js
ECMA Section:       15.3.4.1  Function.prototype.constructor

Description:        The initial value of Function.prototype.constructor
is the built-in Function constructor.
Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_3_4_1:function(){


var SECTION = "15.3.4.1";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "Function.prototype.constructor";

// writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION, "Function.prototype.constructor",   Function,   3 );

// test();

},

/**
File Name:          15.3.4.js
ECMA Section:       15.3.4  Properties of the Function Prototype Object

Description:        The Function prototype object is itself a Function
object ( its [[Class]] is "Function") that, when
invoked, accepts any arguments and returns undefined.

The value of the internal [[Prototype]] property
object is the Object prototype object.

It is a function with an "empty body"; if it is
invoked, it merely returns undefined.

The Function prototype object does not have a valueOf
property of its own; however it inherits the valueOf
property from the Object prototype Object.

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_3_4:function(){

var SECTION = "15.3.4";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "Properties of the Function Prototype Object";

// writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase(   SECTION,
"var myfunc = Function.prototype; myfunc.toString = Object.prototype.toString; myfunc.toString()",
"[object Function]",
eval("var myfunc = Function.prototype; myfunc.toString = Object.prototype.toString; myfunc.toString()"));


//  this.TestCase( SECTION,  "Function.prototype.__proto__",     Object.prototype,           Function.prototype.__proto__ );
this.TestCase( SECTION,  "Function.prototype.valueOf",       Object.prototype.valueOf,   3);
this.TestCase( SECTION,  "Function.prototype()",             (void 0),                   2 );
this.TestCase( SECTION,  "Function.prototype(1,true,false,'string', new Date(),null)",  (void 0), 3 );

// test();

},

/**
File Name:          15.3.5-1.js
ECMA Section:       15.3.5 Properties of Function Instances
new Function(p1, p2, ..., pn, body )

Description:

15.3.5.1 length

The value of the length property is usually an integer that indicates
the "typical" number of arguments expected by the function. However,
the language permits the function to be invoked with some other number
of arguments. The behavior of a function when invoked on a number of
arguments other than the number specified by its length property depends
on the function.

15.3.5.2 prototype
The value of the prototype property is used to initialize the internal [[
Prototype]] property of a newly created object before the Function object
is invoked as a constructor for that newly created object.

15.3.5.3 arguments

The value of the arguments property is normally null if there is no
outstanding invocation of the function in progress (that is, the function has been called
but has not yet returned). When a non-internal Function object (15.3.2.1) is invoked, its
arguments property is "dynamically bound" to a newly created object that contains the
arguments on which it was invoked (see 10.1.6 and 10.1.8). Note that the use of this
property is discouraged; it is provided principally for compatibility with existing old code.

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_3_5__1:function(){


var SECTION = "15.3.5-1";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "Properties of Function Instances";

// writeHeaderToLog( SECTION + " "+TITLE);

var args = "";
var i ;
for (  i = 0; i < 2000; i++ ) {
args += "arg"+i;
if ( i != 1999 ) {
args += ",";
}
}

var s = "";

for (  i = 0; i < 2000; i++ ) {
s += ".0005";
if ( i != 1999 ) {
s += ",";
}
}

var MyFunc = new Function( args, "var r=0; for (var i = 0; i < MyFunc.length; i++ ) { if ( eval('arg'+i) == void 0) break; else r += eval('arg'+i); }; return r");
var MyObject = new Function( args, "for (var i = 0; i < MyFunc.length; i++ ) { if ( eval('arg'+i) == void 0) break; eval('this.arg'+i +'=arg'+i); };");


this.TestCase( SECTION, "MyFunc.length",                       2000,         MyFunc.length );
this.TestCase( SECTION, "var MY_OB = eval('MyFunc(s)')",       1,            eval("var MY_OB = MyFunc("+s+"); MY_OB") );
this.TestCase( SECTION, "MyFunc.prototype.toString()",       "[object Object]",  MyFunc.prototype.toString() );
this.TestCase( SECTION, "typeof MyFunc.prototype",           "object",           typeof MyFunc.prototype );


this.TestCase( SECTION, "MyObject.length",                       2000,         MyObject.length );

this.TestCase( SECTION, "FUN1 = new Function( 'a','b','c', 'return FUN1.length' ); FUN1.length",     3, eval("FUN1 = new Function( 'a','b','c', 'return FUN1.length' ); FUN1.length") );
this.TestCase( SECTION, "FUN1 = new Function( 'a','b','c', 'return FUN1.length' ); FUN1()",          3, eval("FUN1 = new Function( 'a','b','c', 'return FUN1.length' ); FUN1()") );
this.TestCase( SECTION, "FUN1 = new Function( 'a','b','c', 'return FUN1.length' ); FUN1(1,2,3,4,5)", 3, eval("FUN1 = new Function( 'a','b','c', 'return FUN1.length' ); FUN1(1,2,3,4,5)") );

// test();

},

/**
File Name:          15.3.5-1.js
ECMA Section:       15.3.5 Properties of Function Instances
new Function(p1, p2, ..., pn, body )

Description:

15.3.5.1 length

The value of the length property is usually an integer that indicates
the "typical" number of arguments expected by the function. However,
the language permits the function to be invoked with some other number
of arguments. The behavior of a function when invoked on a number of
arguments other than the number specified by its length property depends
on the function.

15.3.5.2 prototype
The value of the prototype property is used to initialize the internal [[
Prototype]] property of a newly created object before the Function object
is invoked as a constructor for that newly created object.

15.3.5.3 arguments

The value of the arguments property is normally null if there is no
outstanding invocation of the function in progress (that is, the function has been called
but has not yet returned). When a non-internal Function object (15.3.2.1) is invoked, its
arguments property is "dynamically bound" to a newly created object that contains the
arguments on which it was invoked (see 10.1.6 and 10.1.8). Note that the use of this
property is discouraged; it is provided principally for compatibility with existing old code.

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_3_5__2:function(){


var SECTION = "15.3.5-2";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "Properties of Function Instances";

// writeHeaderToLog( SECTION + " "+TITLE);

var MyObject = new Function( 'a', 'b', 'c', 'this.a = a; this.b = b; this.c = c; this.value = a+b+c; this.valueOf = new Function( "return this.value" )' );

this.TestCase( SECTION, "MyObject.length",                       3,          MyObject.length );
this.TestCase( SECTION, "typeof MyObject.prototype",             "object",   typeof MyObject.prototype );
this.TestCase( SECTION, "typeof MyObject.prototype.constructor", "function", typeof MyObject.prototype.constructor );
this.TestCase( SECTION, "MyObject.arguments",                     null,       MyObject.arguments );

// test();

},

/**
File Name:          15.3.5.1.js
ECMA Section:       Function.length
Description:

The value of the length property is usually an integer that indicates the
"typical" number of arguments expected by the function.  However, the
language permits the function to be invoked with some other number of
arguments. The behavior of a function when invoked on a number of arguments
other than the number specified by its length property depends on the function.

this test needs a 1.2 version check.

http://scopus.mcom.com/bugsplat/show_bug.cgi?id=104204


Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_3_5_1:function(){


var SECTION = "15.3.5.1";
var VERSION = "ECMA_1";
var TITLE   = "Function.length";
var BUGNUMBER="104204";
// startTest();

// writeHeaderToLog( SECTION + " "+ TITLE);

var f = new Function( "a","b", "c", "return f.length");

this.TestCase( SECTION,
'var f = new Function( "a","b", "c", "return f.length"); f()',
3,
f() );


this.TestCase( SECTION,
'var f = new Function( "a","b", "c", "return f.length"); f(1,2,3,4,5)',
3,
f(1,2,3,4,5) );

// test();


},

/**
File Name:          15.3.5.3.js
ECMA Section:       Function.arguments
Description:

The value of the arguments property is normally null if there is no
outstanding invocation of the function in progress (that is, the
function has been called but has not yet returned). When a non-internal
Function object (15.3.2.1) is invoked, its arguments property is
"dynamically bound" to a newly created object that contains the arguments
on which it was invoked (see 10.1.6 and 10.1.8). Note that the use of this
property is discouraged; it is provided principally for compatibility
with existing old code.

See sections 10.1.6 and 10.1.8 for more extensive tests.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_3_5_3:function(){


var SECTION = "15.3.5.3";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "Function.arguments";

// writeHeaderToLog( SECTION + " "+ TITLE);

var MYFUNCTION = new Function( "return this.arguments" );

this.TestCase( SECTION,  "var MYFUNCTION = new Function( 'return this.arguments' ); MYFUNCTION.arguments",   null,   MYFUNCTION.arguments );

// test();

}

}).endType()
