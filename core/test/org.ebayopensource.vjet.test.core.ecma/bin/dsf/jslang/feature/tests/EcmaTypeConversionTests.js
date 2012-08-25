vjo.ctype("dsf.jslang.feature.tests.EcmaTypeConversionTests")
.inherits("com.ebay.dsf.jslang.feature.tests.BaseTest")
.protos({

MyObject : function ( value ) {
this.value = value;
this.valueOf = new Function ( "return this.value" );
}

ToInt16 : function ( num ) {
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



ToUint32 : function ( n ) {
n = Number( n );
var sign = ( n < 0 ) ? -1 : 1;

if ( Math.abs( n ) == 0 || Math.abs( n ) == Number.POSITIVE_INFINITY) {
return 0;
}
n = sign * Math.floor( Math.abs(n) )

n = n % Math.pow(2,32);

if ( n < 0 ){
n += Math.pow(2,32);
}

return ( n );
}


ToInt32 : function ( n ) {
n = Number( n );
var sign = ( n < 0 ) ? -1 : 1;

if ( Math.abs( n ) == 0 || Math.abs( n ) == Number.POSITIVE_INFINITY) {
return 0;
}

n = (sign * Math.floor( Math.abs(n) )) % Math.pow(2,32);
if ( sign == -1 ) {
n = ( n < -Math.pow(2,31) ) ? n + Math.pow(2,32) : n;
} else{
n = ( n >= Math.pow(2,31) ) ? n - Math.pow(2,32) : n;
}

return ( n );
}

print : function (str) {
}


/**
File Name:          9.2.js
ECMA Section:       9.2  Type Conversion:  ToBoolean
Description:        rules for converting an argument to a boolean.
undefined           false
Null                false
Boolean             input argument( no conversion )
Number              returns false for 0, -0, and NaN
otherwise return true
String              return false if the string is empty
(length is 0) otherwise the result is
true
Object              all return true

Author:             christine@netscape.com
Date:               14 july 1997
*/
test_9_2:function(){

var SECTION = "9.2";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "ToBoolean";

// writeHeaderToLog( SECTION + " "+ TITLE);

// special cases here

this.TestCase( SECTION,   "Boolean()",                     false,  Boolean() );
this.TestCase( SECTION,   "Boolean(var x)",                false,  Boolean(eval("var x")) );
this.TestCase( SECTION,   "Boolean(void 0)",               false,  Boolean(void 0) );
this.TestCase( SECTION,   "Boolean(null)",                 false,  Boolean(null) );
this.TestCase( SECTION,   "Boolean(false)",                false,  Boolean(false) );
this.TestCase( SECTION,   "Boolean(true)",                 true,   Boolean(true) );
this.TestCase( SECTION,   "Boolean(0)",                    false,  Boolean(0) );
this.TestCase( SECTION,   "Boolean(-0)",                   false,  Boolean(-0) );
this.TestCase( SECTION,   "Boolean(NaN)",                  false,  Boolean(Number.NaN) );
this.TestCase( SECTION,   "Boolean('')",                   false,  Boolean("") );

// normal test cases here

this.TestCase( SECTION,   "Boolean(Infinity)",             true,   Boolean(Number.POSITIVE_INFINITY) );
this.TestCase( SECTION,   "Boolean(-Infinity)",            true,   Boolean(Number.NEGATIVE_INFINITY) );
this.TestCase( SECTION,   "Boolean(Math.PI)",              true,   Boolean(Math.PI) );
this.TestCase( SECTION,   "Boolean(1)",                    true,   Boolean(1) );
this.TestCase( SECTION,   "Boolean(-1)",                   true,   Boolean(-1) );
this.TestCase( SECTION,   "Boolean([tab])",                true,   Boolean("\t") );
this.TestCase( SECTION,   "Boolean('0')",                  true,   Boolean("0") );
this.TestCase( SECTION,   "Boolean('string')",             true,   Boolean("string") );

// ToBoolean (object) should always return true.
this.TestCase( SECTION,   "Boolean(new String() )",        true,   Boolean(new String()) );
this.TestCase( SECTION,   "Boolean(new String('') )",      true,   Boolean(new String("")) );

this.TestCase( SECTION,   "Boolean(new Boolean(true))",    true,   Boolean(new Boolean(true)) );
this.TestCase( SECTION,   "Boolean(new Boolean(false))",   true,   Boolean(new Boolean(false)) );
this.TestCase( SECTION,   "Boolean(new Boolean() )",       true,   Boolean(new Boolean()) );

this.TestCase( SECTION,   "Boolean(new Array())",          true,   Boolean(new Array()) );

this.TestCase( SECTION,   "Boolean(new Number())",         true,   Boolean(new Number()) );
this.TestCase( SECTION,   "Boolean(new Number(-0))",       true,   Boolean(new Number(-0)) );
this.TestCase( SECTION,   "Boolean(new Number(0))",        true,   Boolean(new Number(0)) );
this.TestCase( SECTION,   "Boolean(new Number(NaN))",      true,   Boolean(new Number(Number.NaN)) );

this.TestCase( SECTION,   "Boolean(new Number(-1))",       true,   Boolean(new Number(-1)) );
this.TestCase( SECTION,   "Boolean(new Number(Infinity))", true,   Boolean(new Number(Number.POSITIVE_INFINITY)) );
this.TestCase( SECTION,   "Boolean(new Number(-Infinity))",true,   Boolean(new Number(Number.NEGATIVE_INFINITY)) );

this.TestCase( SECTION,    "Boolean(new Object())",       true,       Boolean(new Object()) );
this.TestCase( SECTION,    "Boolean(new Function())",     true,       Boolean(new Function()) );
this.TestCase( SECTION,    "Boolean(new Date())",         true,       Boolean(new Date()) );
this.TestCase( SECTION,    "Boolean(new Date(0))",         true,       Boolean(new Date(0)) );
this.TestCase( SECTION,    "Boolean(Math)",         true,       Boolean(Math) );

// bug 375793
this.TestCase( SECTION,
"NaN ? true : false",
false,
(NaN ? true : false) );
this.TestCase( SECTION,
"1000 % 0 ? true : false",
false,
(1000 % 0 ? true : false) );
this.TestCase( SECTION,
"(function(a,b){ return a % b ? true : false })(1000, 0)",
false,
((function(a,b){ return a % b ? true : false })(1000, 0)) );

this.TestCase( SECTION,
"(function(x) { return !(x) })(0/0)",
true,
((function(x) { return !(x) })(0/0)) );
this.TestCase( SECTION,
"!(0/0)",
true,
(!(0/0)) );
// test();


},

/**
File Name:          9.3-1.js
ECMA Section:       9.3  Type Conversion:  ToNumber
Description:        rules for converting an argument to a number.
see 9.3.1 for cases for converting strings to numbers.
special cases:
undefined           NaN
Null                NaN
Boolean             1 if true; +0 if false
Number              the argument ( no conversion )
String              see test 9.3.1
Object              see test 9.3-1


This tests ToNumber applied to the object type, except
if object is string.  See 9.3-2 for
ToNumber( String object).

Author:             christine@netscape.com
Date:               10 july 1997

*/
test_9_3__1:function(){

var SECTION = "9.3-1";
var VERSION = "ECMA_1";
// startTest();

// writeHeaderToLog( SECTION + " ToNumber");

// object is Number
this.TestCase( SECTION, "Number(new Number())", 0, Number(new Number()) );
this.TestCase( SECTION, "typeof Number(new Number())", "number", typeof Number(new Number()) );

this.TestCase( SECTION, "Number(new Number(Number.NaN))", Number.NaN, Number(new Number(Number.NaN)) );
this.TestCase( SECTION, "typeof Number(new Number(Number.NaN))","number", typeof Number(new Number(Number.NaN)) );

this.TestCase( SECTION, "Number(new Number(0))", 0, Number(new Number(0)) );
this.TestCase( SECTION, "typeof Number(new Number(0))", "number", typeof Number(new Number(0)) );

this.TestCase( SECTION, "Number(new Number(null))", 0, Number(new Number(null)) );
this.TestCase( SECTION, "typeof Number(new Number(null))", "number", typeof Number(new Number(null)) );


// this.TestCase( SECTION, "Number(new Number(void 0))", Number.NaN, Number(new Number(void 0)) );
this.TestCase( SECTION, "Number(new Number(true))", 1, Number(new Number(true)) );
this.TestCase( SECTION, "typeof Number(new Number(true))", "number", typeof Number(new Number(true)) );

this.TestCase( SECTION, "Number(new Number(false))", 0, Number(new Number(false)) );
this.TestCase( SECTION, "typeof Number(new Number(false))", "number", typeof Number(new Number(false)) );

// object is boolean
this.TestCase( SECTION, "Number(new Boolean(true))", 1, Number(new Boolean(true)) );
this.TestCase( SECTION, "typeof Number(new Boolean(true))", "number", typeof Number(new Boolean(true)) );

this.TestCase( SECTION, "Number(new Boolean(false))", 0, Number(new Boolean(false)) );
this.TestCase( SECTION, "typeof Number(new Boolean(false))", "number", typeof Number(new Boolean(false)) );

// object is array
this.TestCase( SECTION, "Number(new Array(2,4,8,16,32))", Number.NaN, Number(new Array(2,4,8,16,32)) );
this.TestCase( SECTION, "typeof Number(new Array(2,4,8,16,32))", "number", typeof Number(new Array(2,4,8,16,32)) );


},

/**
File Name:          9.3.1-1.js
ECMA Section:       9.3  Type Conversion:  ToNumber
Description:        rules for converting an argument to a number.
see 9.3.1 for cases for converting strings to numbers.
special cases:
undefined           NaN
Null                NaN
Boolean             1 if true; +0 if false
Number              the argument ( no conversion )
String              see test 9.3.1
Object              see test 9.3-1


This tests ToNumber applied to the string type

Author:             christine@netscape.com
Date:               10 july 1997

*/
test_9_3_1__1:function(){

var SECTION = "9.3.1-1";
var VERSION = "ECMA_1";
var TITLE   = "ToNumber applied to the String type";
var BUGNUMBER="77391";

// startTest();

// writeHeaderToLog( SECTION + " "+ TITLE);


//  StringNumericLiteral:::StrWhiteSpace:::StrWhiteSpaceChar StrWhiteSpace:::
//
//  Name    Unicode Value   Escape Sequence
//  <TAB>   0X0009          \t
//  <SP>    0X0020
//  <FF>    0X000C          \f
//  <VT>    0X000B
//  <CR>    0X000D          \r
//  <LF>    0X000A          \n
this.TestCase( SECTION,  "Number('')",           0,      Number("") );
this.TestCase( SECTION,  "Number(' ')",          0,      Number(" ") );
this.TestCase( SECTION,  "Number(\\t)",          0,      Number("\t") );
this.TestCase( SECTION,  "Number(\\n)",          0,      Number("\n") );
this.TestCase( SECTION,  "Number(\\r)",          0,      Number("\r") );
this.TestCase( SECTION,  "Number(\\f)",          0,      Number("\f") );

this.TestCase( SECTION,  "Number(String.fromCharCode(0x0009)",   0,  Number(String.fromCharCode(0x0009)) );
this.TestCase( SECTION,  "Number(String.fromCharCode(0x0020)",   0,  Number(String.fromCharCode(0x0020)) );
this.TestCase( SECTION,  "Number(String.fromCharCode(0x000C)",   0,  Number(String.fromCharCode(0x000C)) );
this.TestCase( SECTION,  "Number(String.fromCharCode(0x000B)",   0,  Number(String.fromCharCode(0x000B)) );
this.TestCase( SECTION,  "Number(String.fromCharCode(0x000D)",   0,  Number(String.fromCharCode(0x000D)) );
this.TestCase( SECTION,  "Number(String.fromCharCode(0x000A)",   0,  Number(String.fromCharCode(0x000A)) );

//  a StringNumericLiteral may be preceeded or followed by whitespace and/or
//  line terminators

this.TestCase( SECTION,  "Number( '   ' +  999 )",        999,    Number( '   '+999) );
this.TestCase( SECTION,  "Number( '\\n'  + 999 )",       999,    Number( '\n' +999) );
this.TestCase( SECTION,  "Number( '\\r'  + 999 )",       999,    Number( '\r' +999) );
this.TestCase( SECTION,  "Number( '\\t'  + 999 )",       999,    Number( '\t' +999) );
this.TestCase( SECTION,  "Number( '\\f'  + 999 )",       999,    Number( '\f' +999) );

this.TestCase( SECTION,  "Number( 999 + '   ' )",        999,    Number( 999+'   ') );
this.TestCase( SECTION,  "Number( 999 + '\\n' )",        999,    Number( 999+'\n' ) );
this.TestCase( SECTION,  "Number( 999 + '\\r' )",        999,    Number( 999+'\r' ) );
this.TestCase( SECTION,  "Number( 999 + '\\t' )",        999,    Number( 999+'\t' ) );
this.TestCase( SECTION,  "Number( 999 + '\\f' )",        999,    Number( 999+'\f' ) );

this.TestCase( SECTION,  "Number( '\\n'  + 999 + '\\n' )",         999,    Number( '\n' +999+'\n' ) );
this.TestCase( SECTION,  "Number( '\\r'  + 999 + '\\r' )",         999,    Number( '\r' +999+'\r' ) );
this.TestCase( SECTION,  "Number( '\\t'  + 999 + '\\t' )",         999,    Number( '\t' +999+'\t' ) );
this.TestCase( SECTION,  "Number( '\\f'  + 999 + '\\f' )",         999,    Number( '\f' +999+'\f' ) );

this.TestCase( SECTION,  "Number( '   ' +  '999' )",     999,    Number( '   '+'999') );
this.TestCase( SECTION,  "Number( '\\n'  + '999' )",       999,    Number( '\n' +'999') );
this.TestCase( SECTION,  "Number( '\\r'  + '999' )",       999,    Number( '\r' +'999') );
this.TestCase( SECTION,  "Number( '\\t'  + '999' )",       999,    Number( '\t' +'999') );
this.TestCase( SECTION,  "Number( '\\f'  + '999' )",       999,    Number( '\f' +'999') );

this.TestCase( SECTION,  "Number( '999' + '   ' )",        999,    Number( '999'+'   ') );
this.TestCase( SECTION,  "Number( '999' + '\\n' )",        999,    Number( '999'+'\n' ) );
this.TestCase( SECTION,  "Number( '999' + '\\r' )",        999,    Number( '999'+'\r' ) );
this.TestCase( SECTION,  "Number( '999' + '\\t' )",        999,    Number( '999'+'\t' ) );
this.TestCase( SECTION,  "Number( '999' + '\\f' )",        999,    Number( '999'+'\f' ) );

this.TestCase( SECTION,  "Number( '\\n'  + '999' + '\\n' )",         999,    Number( '\n' +'999'+'\n' ) );
this.TestCase( SECTION,  "Number( '\\r'  + '999' + '\\r' )",         999,    Number( '\r' +'999'+'\r' ) );
this.TestCase( SECTION,  "Number( '\\t'  + '999' + '\\t' )",         999,    Number( '\t' +'999'+'\t' ) );
this.TestCase( SECTION,  "Number( '\\f'  + '999' + '\\f' )",         999,    Number( '\f' +'999'+'\f' ) );

this.TestCase( SECTION,  "Number( String.fromCharCode(0x0009) +  '99' )",    99,     Number( String.fromCharCode(0x0009) +  '99' ) );
this.TestCase( SECTION,  "Number( String.fromCharCode(0x0020) +  '99' )",    99,     Number( String.fromCharCode(0x0020) +  '99' ) );
this.TestCase( SECTION,  "Number( String.fromCharCode(0x000C) +  '99' )",    99,     Number( String.fromCharCode(0x000C) +  '99' ) );
this.TestCase( SECTION,  "Number( String.fromCharCode(0x000B) +  '99' )",    99,     Number( String.fromCharCode(0x000B) +  '99' ) );
this.TestCase( SECTION,  "Number( String.fromCharCode(0x000D) +  '99' )",    99,     Number( String.fromCharCode(0x000D) +  '99' ) );
this.TestCase( SECTION,  "Number( String.fromCharCode(0x000A) +  '99' )",    99,     Number( String.fromCharCode(0x000A) +  '99' ) );

this.TestCase( SECTION,  "Number( String.fromCharCode(0x0009) +  '99' + String.fromCharCode(0x0009)",    99,     Number( String.fromCharCode(0x0009) +  '99' + String.fromCharCode(0x0009)) );
this.TestCase( SECTION,  "Number( String.fromCharCode(0x0020) +  '99' + String.fromCharCode(0x0020)",    99,     Number( String.fromCharCode(0x0009) +  '99' + String.fromCharCode(0x0020)) );
this.TestCase( SECTION,  "Number( String.fromCharCode(0x000C) +  '99' + String.fromCharCode(0x000C)",    99,     Number( String.fromCharCode(0x0009) +  '99' + String.fromCharCode(0x000C)) );
this.TestCase( SECTION,  "Number( String.fromCharCode(0x000D) +  '99' + String.fromCharCode(0x000D)",    99,     Number( String.fromCharCode(0x0009) +  '99' + String.fromCharCode(0x000D)) );
this.TestCase( SECTION,  "Number( String.fromCharCode(0x000B) +  '99' + String.fromCharCode(0x000B)",    99,     Number( String.fromCharCode(0x0009) +  '99' + String.fromCharCode(0x000B)) );
this.TestCase( SECTION,  "Number( String.fromCharCode(0x000A) +  '99' + String.fromCharCode(0x000A)",    99,     Number( String.fromCharCode(0x0009) +  '99' + String.fromCharCode(0x000A)) );

this.TestCase( SECTION,  "Number( '99' + String.fromCharCode(0x0009)",    99,     Number( '99' + String.fromCharCode(0x0009)) );
this.TestCase( SECTION,  "Number( '99' + String.fromCharCode(0x0020)",    99,     Number( '99' + String.fromCharCode(0x0020)) );
this.TestCase( SECTION,  "Number( '99' + String.fromCharCode(0x000C)",    99,     Number( '99' + String.fromCharCode(0x000C)) );
this.TestCase( SECTION,  "Number( '99' + String.fromCharCode(0x000D)",    99,     Number( '99' + String.fromCharCode(0x000D)) );
this.TestCase( SECTION,  "Number( '99' + String.fromCharCode(0x000B)",    99,     Number( '99' + String.fromCharCode(0x000B)) );
this.TestCase( SECTION,  "Number( '99' + String.fromCharCode(0x000A)",    99,     Number( '99' + String.fromCharCode(0x000A)) );

this.TestCase( SECTION,  "Number( String.fromCharCode(0x0009) +  99 )",    99,     Number( String.fromCharCode(0x0009) +  99 ) );
this.TestCase( SECTION,  "Number( String.fromCharCode(0x0020) +  99 )",    99,     Number( String.fromCharCode(0x0020) +  99 ) );
this.TestCase( SECTION,  "Number( String.fromCharCode(0x000C) +  99 )",    99,     Number( String.fromCharCode(0x000C) +  99 ) );
this.TestCase( SECTION,  "Number( String.fromCharCode(0x000B) +  99 )",    99,     Number( String.fromCharCode(0x000B) +  99 ) );
this.TestCase( SECTION,  "Number( String.fromCharCode(0x000D) +  99 )",    99,     Number( String.fromCharCode(0x000D) +  99 ) );
this.TestCase( SECTION,  "Number( String.fromCharCode(0x000A) +  99 )",    99,     Number( String.fromCharCode(0x000A) +  99 ) );

this.TestCase( SECTION,  "Number( String.fromCharCode(0x0009) +  99 + String.fromCharCode(0x0009)",    99,     Number( String.fromCharCode(0x0009) +  99 + String.fromCharCode(0x0009)) );
this.TestCase( SECTION,  "Number( String.fromCharCode(0x0020) +  99 + String.fromCharCode(0x0020)",    99,     Number( String.fromCharCode(0x0009) +  99 + String.fromCharCode(0x0020)) );
this.TestCase( SECTION,  "Number( String.fromCharCode(0x000C) +  99 + String.fromCharCode(0x000C)",    99,     Number( String.fromCharCode(0x0009) +  99 + String.fromCharCode(0x000C)) );
this.TestCase( SECTION,  "Number( String.fromCharCode(0x000D) +  99 + String.fromCharCode(0x000D)",    99,     Number( String.fromCharCode(0x0009) +  99 + String.fromCharCode(0x000D)) );
this.TestCase( SECTION,  "Number( String.fromCharCode(0x000B) +  99 + String.fromCharCode(0x000B)",    99,     Number( String.fromCharCode(0x0009) +  99 + String.fromCharCode(0x000B)) );
this.TestCase( SECTION,  "Number( String.fromCharCode(0x000A) +  99 + String.fromCharCode(0x000A)",    99,     Number( String.fromCharCode(0x0009) +  99 + String.fromCharCode(0x000A)) );

this.TestCase( SECTION,  "Number( 99 + String.fromCharCode(0x0009)",    99,     Number( 99 + String.fromCharCode(0x0009)) );
this.TestCase( SECTION,  "Number( 99 + String.fromCharCode(0x0020)",    99,     Number( 99 + String.fromCharCode(0x0020)) );
this.TestCase( SECTION,  "Number( 99 + String.fromCharCode(0x000C)",    99,     Number( 99 + String.fromCharCode(0x000C)) );
this.TestCase( SECTION,  "Number( 99 + String.fromCharCode(0x000D)",    99,     Number( 99 + String.fromCharCode(0x000D)) );
this.TestCase( SECTION,  "Number( 99 + String.fromCharCode(0x000B)",    99,     Number( 99 + String.fromCharCode(0x000B)) );
this.TestCase( SECTION,  "Number( 99 + String.fromCharCode(0x000A)",    99,     Number( 99 + String.fromCharCode(0x000A)) );


// StrNumericLiteral:::StrDecimalLiteral:::Infinity

this.TestCase( SECTION,  "Number('Infinity')",   Math.pow(10,10000),   Number("Infinity") );
this.TestCase( SECTION,  "Number('-Infinity')", -Math.pow(10,10000),   Number("-Infinity") );
this.TestCase( SECTION,  "Number('+Infinity')",  Math.pow(10,10000),   Number("+Infinity") );

// StrNumericLiteral:::   StrDecimalLiteral ::: DecimalDigits . DecimalDigits opt ExponentPart opt

this.TestCase( SECTION,  "Number('0')",          0,          Number("0") );
this.TestCase( SECTION,  "Number('-0')",         -0,         Number("-0") );
this.TestCase( SECTION,  "Number('+0')",          0,         Number("+0") );

this.TestCase( SECTION,  "Number('1')",          1,          Number("1") );
this.TestCase( SECTION,  "Number('-1')",         -1,         Number("-1") );
this.TestCase( SECTION,  "Number('+1')",          1,         Number("+1") );

this.TestCase( SECTION,  "Number('2')",          2,          Number("2") );
this.TestCase( SECTION,  "Number('-2')",         -2,         Number("-2") );
this.TestCase( SECTION,  "Number('+2')",          2,         Number("+2") );

this.TestCase( SECTION,  "Number('3')",          3,          Number("3") );
this.TestCase( SECTION,  "Number('-3')",         -3,         Number("-3") );
this.TestCase( SECTION,  "Number('+3')",          3,         Number("+3") );

this.TestCase( SECTION,  "Number('4')",          4,          Number("4") );
this.TestCase( SECTION,  "Number('-4')",         -4,         Number("-4") );
this.TestCase( SECTION,  "Number('+4')",          4,         Number("+4") );

this.TestCase( SECTION,  "Number('5')",          5,          Number("5") );
this.TestCase( SECTION,  "Number('-5')",         -5,         Number("-5") );
this.TestCase( SECTION,  "Number('+5')",          5,         Number("+5") );

this.TestCase( SECTION,  "Number('6')",          6,          Number("6") );
this.TestCase( SECTION,  "Number('-6')",         -6,         Number("-6") );
this.TestCase( SECTION,  "Number('+6')",          6,         Number("+6") );

this.TestCase( SECTION,  "Number('7')",          7,          Number("7") );
this.TestCase( SECTION,  "Number('-7')",         -7,         Number("-7") );
this.TestCase( SECTION,  "Number('+7')",          7,         Number("+7") );

this.TestCase( SECTION,  "Number('8')",          8,          Number("8") );
this.TestCase( SECTION,  "Number('-8')",         -8,         Number("-8") );
this.TestCase( SECTION,  "Number('+8')",          8,         Number("+8") );

this.TestCase( SECTION,  "Number('9')",          9,          Number("9") );
this.TestCase( SECTION,  "Number('-9')",         -9,         Number("-9") );
this.TestCase( SECTION,  "Number('+9')",          9,         Number("+9") );

this.TestCase( SECTION,  "Number('3.14159')",    3.14159,    Number("3.14159") );
this.TestCase( SECTION,  "Number('-3.14159')",   -3.14159,   Number("-3.14159") );
this.TestCase( SECTION,  "Number('+3.14159')",   3.14159,    Number("+3.14159") );

this.TestCase( SECTION,  "Number('3.')",         3,          Number("3.") );
this.TestCase( SECTION,  "Number('-3.')",        -3,         Number("-3.") );
this.TestCase( SECTION,  "Number('+3.')",        3,          Number("+3.") );

this.TestCase( SECTION,  "Number('3.e1')",       30,         Number("3.e1") );
this.TestCase( SECTION,  "Number('-3.e1')",      -30,        Number("-3.e1") );
this.TestCase( SECTION,  "Number('+3.e1')",      30,         Number("+3.e1") );

this.TestCase( SECTION,  "Number('3.e+1')",       30,         Number("3.e+1") );
this.TestCase( SECTION,  "Number('-3.e+1')",      -30,        Number("-3.e+1") );
this.TestCase( SECTION,  "Number('+3.e+1')",      30,         Number("+3.e+1") );

this.TestCase( SECTION,  "Number('3.e-1')",       .30,         Number("3.e-1") );
this.TestCase( SECTION,  "Number('-3.e-1')",      -.30,        Number("-3.e-1") );
this.TestCase( SECTION,  "Number('+3.e-1')",      .30,         Number("+3.e-1") );

// StrDecimalLiteral:::  .DecimalDigits ExponentPart opt

this.TestCase( SECTION,  "Number('.00001')",     0.00001,    Number(".00001") );
this.TestCase( SECTION,  "Number('+.00001')",    0.00001,    Number("+.00001") );
this.TestCase( SECTION,  "Number('-0.0001')",    -0.00001,   Number("-.00001") );

this.TestCase( SECTION,  "Number('.01e2')",      1,          Number(".01e2") );
this.TestCase( SECTION,  "Number('+.01e2')",     1,          Number("+.01e2") );
this.TestCase( SECTION,  "Number('-.01e2')",     -1,         Number("-.01e2") );

this.TestCase( SECTION,  "Number('.01e+2')",      1,         Number(".01e+2") );
this.TestCase( SECTION,  "Number('+.01e+2')",     1,         Number("+.01e+2") );
this.TestCase( SECTION,  "Number('-.01e+2')",     -1,        Number("-.01e+2") );

this.TestCase( SECTION,  "Number('.01e-2')",      0.0001,    Number(".01e-2") );
this.TestCase( SECTION,  "Number('+.01e-2')",     0.0001,    Number("+.01e-2") );
this.TestCase( SECTION,  "Number('-.01e-2')",     -0.0001,   Number("-.01e-2") );

//  StrDecimalLiteral:::    DecimalDigits ExponentPart opt

this.TestCase( SECTION,  "Number('1234e5')",     123400000,  Number("1234e5") );
this.TestCase( SECTION,  "Number('+1234e5')",    123400000,  Number("+1234e5") );
this.TestCase( SECTION,  "Number('-1234e5')",    -123400000, Number("-1234e5") );

this.TestCase( SECTION,  "Number('1234e+5')",    123400000,  Number("1234e+5") );
this.TestCase( SECTION,  "Number('+1234e+5')",   123400000,  Number("+1234e+5") );
this.TestCase( SECTION,  "Number('-1234e+5')",   -123400000, Number("-1234e+5") );

this.TestCase( SECTION,  "Number('1234e-5')",     0.01234,  Number("1234e-5") );
this.TestCase( SECTION,  "Number('+1234e-5')",    0.01234,  Number("+1234e-5") );
this.TestCase( SECTION,  "Number('-1234e-5')",    -0.01234, Number("-1234e-5") );

// StrNumericLiteral::: HexIntegerLiteral

this.TestCase( SECTION,  "Number('0x0')",        0,          Number("0x0"));
this.TestCase( SECTION,  "Number('0x1')",        1,          Number("0x1"));
this.TestCase( SECTION,  "Number('0x2')",        2,          Number("0x2"));
this.TestCase( SECTION,  "Number('0x3')",        3,          Number("0x3"));
this.TestCase( SECTION,  "Number('0x4')",        4,          Number("0x4"));
this.TestCase( SECTION,  "Number('0x5')",        5,          Number("0x5"));
this.TestCase( SECTION,  "Number('0x6')",        6,          Number("0x6"));
this.TestCase( SECTION,  "Number('0x7')",        7,          Number("0x7"));
this.TestCase( SECTION,  "Number('0x8')",        8,          Number("0x8"));
this.TestCase( SECTION,  "Number('0x9')",        9,          Number("0x9"));
this.TestCase( SECTION,  "Number('0xa')",        10,         Number("0xa"));
this.TestCase( SECTION,  "Number('0xb')",        11,         Number("0xb"));
this.TestCase( SECTION,  "Number('0xc')",        12,         Number("0xc"));
this.TestCase( SECTION,  "Number('0xd')",        13,         Number("0xd"));
this.TestCase( SECTION,  "Number('0xe')",        14,         Number("0xe"));
this.TestCase( SECTION,  "Number('0xf')",        15,         Number("0xf"));
this.TestCase( SECTION,  "Number('0xA')",        10,         Number("0xA"));
this.TestCase( SECTION,  "Number('0xB')",        11,         Number("0xB"));
this.TestCase( SECTION,  "Number('0xC')",        12,         Number("0xC"));
this.TestCase( SECTION,  "Number('0xD')",        13,         Number("0xD"));
this.TestCase( SECTION,  "Number('0xE')",        14,         Number("0xE"));
this.TestCase( SECTION,  "Number('0xF')",        15,         Number("0xF"));

this.TestCase( SECTION,  "Number('0X0')",        0,          Number("0X0"));
this.TestCase( SECTION,  "Number('0X1')",        1,          Number("0X1"));
this.TestCase( SECTION,  "Number('0X2')",        2,          Number("0X2"));
this.TestCase( SECTION,  "Number('0X3')",        3,          Number("0X3"));
this.TestCase( SECTION,  "Number('0X4')",        4,          Number("0X4"));
this.TestCase( SECTION,  "Number('0X5')",        5,          Number("0X5"));
this.TestCase( SECTION,  "Number('0X6')",        6,          Number("0X6"));
this.TestCase( SECTION,  "Number('0X7')",        7,          Number("0X7"));
this.TestCase( SECTION,  "Number('0X8')",        8,          Number("0X8"));
this.TestCase( SECTION,  "Number('0X9')",        9,          Number("0X9"));
this.TestCase( SECTION,  "Number('0Xa')",        10,         Number("0Xa"));
this.TestCase( SECTION,  "Number('0Xb')",        11,         Number("0Xb"));
this.TestCase( SECTION,  "Number('0Xc')",        12,         Number("0Xc"));
this.TestCase( SECTION,  "Number('0Xd')",        13,         Number("0Xd"));
this.TestCase( SECTION,  "Number('0Xe')",        14,         Number("0Xe"));
this.TestCase( SECTION,  "Number('0Xf')",        15,         Number("0Xf"));
this.TestCase( SECTION,  "Number('0XA')",        10,         Number("0XA"));
this.TestCase( SECTION,  "Number('0XB')",        11,         Number("0XB"));
this.TestCase( SECTION,  "Number('0XC')",        12,         Number("0XC"));
this.TestCase( SECTION,  "Number('0XD')",        13,         Number("0XD"));
this.TestCase( SECTION,  "Number('0XE')",        14,         Number("0XE"));
this.TestCase( SECTION,  "Number('0XF')",        15,         Number("0XF"));

// test();


},

/**
File Name:          9.3.1-2.js
ECMA Section:       9.3  Type Conversion:  ToNumber
Description:        rules for converting an argument to a number.
see 9.3.1 for cases for converting strings to numbers.
special cases:
undefined           NaN
Null                NaN
Boolean             1 if true; +0 if false
Number              the argument ( no conversion )
String              see test 9.3.1
Object              see test 9.3-1

This tests special cases of ToNumber(string) that are
not covered in 9.3.1-1.js.

Author:             christine@netscape.com
Date:               10 july 1997

*/
test_9_3_1__2:function(){

var SECTION = "9.3.1-2";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "ToNumber applied to the String type";

// writeHeaderToLog( SECTION + " "+ TITLE);

// A StringNumericLiteral may not use octal notation

this.TestCase( SECTION,  "Number(00)",        0,         Number("00"));
this.TestCase( SECTION,  "Number(01)",        1,         Number("01"));
this.TestCase( SECTION,  "Number(02)",        2,         Number("02"));
this.TestCase( SECTION,  "Number(03)",        3,         Number("03"));
this.TestCase( SECTION,  "Number(04)",        4,         Number("04"));
this.TestCase( SECTION,  "Number(05)",        5,         Number("05"));
this.TestCase( SECTION,  "Number(06)",        6,         Number("06"));
this.TestCase( SECTION,  "Number(07)",        7,         Number("07"));
this.TestCase( SECTION,  "Number(010)",       10,        Number("010"));
this.TestCase( SECTION,  "Number(011)",       11,        Number("011"));

// A StringNumericLIteral may have any number of leading 0 digits

this.TestCase( SECTION,  "Number(001)",        1,         Number("001"));
this.TestCase( SECTION,  "Number(0001)",       1,         Number("0001"));

// test();


},

/**
File Name:          9.3.1-3.js
ECMA Section:       9.3  Type Conversion:  ToNumber
Description:        rules for converting an argument to a number.
see 9.3.1 for cases for converting strings to numbers.
special cases:
undefined           NaN
Null                NaN
Boolean             1 if true; +0 if false
Number              the argument ( no conversion )
String              see test 9.3.1
Object              see test 9.3-1


Test cases provided by waldemar.


Author:             christine@netscape.com
Date:               10 june 1998

*/
test_9_3_1__3:function(){


var SECTION = "9.3.1-3";
var VERSION = "ECMA_1";
var BUGNUMBER="129087";

var TITLE   = "Number To String, String To Number";

// startTest();

// writeHeaderToLog( SECTION + " "+ TITLE);

// test case from http://scopus.mcom.com/bugsplat/show_bug.cgi?id=312954
var z = 0;

this.TestCase(
SECTION,
"var z = 0; print(1/-z)",
-Infinity,
1/-z );





// test cases from bug http://scopus.mcom.com/bugsplat/show_bug.cgi?id=122882



this.TestCase( SECTION,
'- -"0x80000000"',
2147483648,
- -"0x80000000" );

this.TestCase( SECTION,
'- -"0x100000000"',
4294967296,
- -"0x100000000" );

this.TestCase( SECTION,
'- "-0x123456789abcde8"',
81985529216486880,
- "-0x123456789abcde8" );

// Convert some large numbers to string


this.TestCase( SECTION,
"1e2000 +''",
"Infinity",
1e2000 +"" );

this.TestCase( SECTION,
"1e2000",
Infinity,
1e2000 );

this.TestCase( SECTION,
"-1e2000 +''",
"-Infinity",
-1e2000 +"" );

this.TestCase( SECTION,
"-\"1e2000\"",
-Infinity,
-"1e2000" );

this.TestCase( SECTION,
"-\"-1e2000\" +''",
"Infinity",
-"-1e2000" +"" );

this.TestCase( SECTION,
"1e-2000",
0,
1e-2000 );

this.TestCase( SECTION,
"1/1e-2000",
Infinity,
1/1e-2000 );

// convert some strings to large numbers

this.TestCase( SECTION,
"1/-1e-2000",
-Infinity,
1/-1e-2000 );

this.TestCase( SECTION,
"1/\"1e-2000\"",
Infinity,
1/"1e-2000" );

this.TestCase( SECTION,
"1/\"-1e-2000\"",
-Infinity,
1/"-1e-2000" );

this.TestCase( SECTION,
"parseFloat(\"1e2000\")",
Infinity,
parseFloat("1e2000") );

this.TestCase( SECTION,
"parseFloat(\"1e-2000\")",
0,
parseFloat("1e-2000") );

this.TestCase( SECTION,
"1.7976931348623157E+308",
1.7976931348623157e+308,
1.7976931348623157E+308 );

this.TestCase( SECTION,
"1.7976931348623158e+308",
1.7976931348623157e+308,
1.7976931348623158e+308 );

this.TestCase( SECTION,
"1.7976931348623159e+308",
Infinity,
1.7976931348623159e+308 );

s =
"17976931348623158079372897140530341507993413271003782693617377898044496829276475094664901797758720709633028641669288791094655554785194040263065748867150582068";

print("s = " + s);
print("-s = " + (-s));

this.TestCase( SECTION,
"s = " + s +"; s +="+
"\"190890200070838367627385484581771153176447573027006985557136695962284291481986083493647529271907416844436551070434271155969950809304288017790417449779\""+

+"; s",
"17976931348623158079372897140530341507993413271003782693617377898044496829276475094664901797758720709633028641669288791094655554785194040263065748867150582068190890200070838367627385484581771153176447573027006985557136695962284291481986083493647529271907416844436551070434271155969950809304288017790417449779",
s +=
"190890200070838367627385484581771153176447573027006985557136695962284291481986083493647529271907416844436551070434271155969950809304288017790417449779"
);

s1 = s+1;

print("s1 = " + s1);
print("-s1 = " + (-s1));

this.TestCase( SECTION,
"s1 = s+1; s1",
"179769313486231580793728971405303415079934132710037826936173778980444968292764750946649017977587207096330286416692887910946555547851940402630657488671505820681908902000708383676273854845817711531764475730270069855571366959622842914819860834936475292719074168444365510704342711559699508093042880177904174497791",
s1 );

/***** This answer is preferred but -Infinity is also acceptable here *****/

this.TestCase( SECTION,
"-s1 == Infinity || s1 == 1.7976931348623157e+308",
true,
-s1 == Infinity || s1 == 1.7976931348623157e+308 );

s2 = s + 2;

print("s2 = " + s2);
print("-s2 = " + (-s2));

this.TestCase( SECTION,
"s2 = s+2; s2",
"179769313486231580793728971405303415079934132710037826936173778980444968292764750946649017977587207096330286416692887910946555547851940402630657488671505820681908902000708383676273854845817711531764475730270069855571366959622842914819860834936475292719074168444365510704342711559699508093042880177904174497792",
s2 );

// ***** This answer is preferred but -1.7976931348623157e+308 is also acceptable here *****
this.TestCase( SECTION,
"-s2 == -Infinity || -s2 == -1.7976931348623157e+308 ",
true,
-s2 == -Infinity || -s2 == -1.7976931348623157e+308 );

s3 = s+3;

print("s3 = " + s3);
print("-s3 = " + (-s3));

this.TestCase( SECTION,
"s3 = s+3; s3",
"179769313486231580793728971405303415079934132710037826936173778980444968292764750946649017977587207096330286416692887910946555547851940402630657488671505820681908902000708383676273854845817711531764475730270069855571366959622842914819860834936475292719074168444365510704342711559699508093042880177904174497793",
s3 );

//***** This answer is preferred but -1.7976931348623157e+308 is also acceptable here *****

this.TestCase( SECTION,
"-s3 == -Infinity || -s3 == -1.7976931348623157e+308",
true,
-s3 == -Infinity || -s3 == -1.7976931348623157e+308 );


//***** This answer is preferred but Infinity is also acceptable here *****

this.TestCase( SECTION,
"parseInt(s1,10) == 1.7976931348623157e+308 || parseInt(s1,10) == Infinity",
true,
parseInt(s1,10) == 1.7976931348623157e+308 || parseInt(s1,10) == Infinity );

//***** This answer is preferred but 1.7976931348623157e+308 is also acceptable here *****
this.TestCase( SECTION,
"parseInt(s2,10) == Infinity || parseInt(s2,10) == 1.7976931348623157e+308",
true ,
parseInt(s2,10) == Infinity || parseInt(s2,10) == 1.7976931348623157e+308 );

//***** This answer is preferred but Infinity is also acceptable here *****

this.TestCase( SECTION,
"parseInt(s1) == 1.7976931348623157e+308 || parseInt(s1) == Infinity",
true,
parseInt(s1) == 1.7976931348623157e+308 || parseInt(s1) == Infinity);

//***** This answer is preferred but 1.7976931348623157e+308 is also acceptable here *****
this.TestCase( SECTION,
"parseInt(s2) == Infinity || parseInt(s2) == 1.7976931348623157e+308",
true,
parseInt(s2) == Infinity || parseInt(s2) == 1.7976931348623157e+308 );

this.TestCase( SECTION,
"0x12345678",
305419896,
0x12345678 );

this.TestCase( SECTION,
"0x80000000",
2147483648,
0x80000000 );

this.TestCase( SECTION,
"0xffffffff",
4294967295,
0xffffffff );

this.TestCase( SECTION,
"0x100000000",
4294967296,
0x100000000 );

this.TestCase( SECTION,
"077777777777777777",
2251799813685247,
077777777777777777 );

this.TestCase( SECTION,
"077777777777777776",
2251799813685246,
077777777777777776 );

this.TestCase( SECTION,
"0x1fffffffffffff",
9007199254740991,
0x1fffffffffffff );

this.TestCase( SECTION,
"0x20000000000000",
9007199254740992,
0x20000000000000 );

this.TestCase( SECTION,
"0x20123456789abc",
9027215253084860,
0x20123456789abc );

this.TestCase( SECTION,
"0x20123456789abd",
9027215253084860,
0x20123456789abd );

this.TestCase( SECTION,
"0x20123456789abe",
9027215253084862,
0x20123456789abe );

this.TestCase( SECTION,
"0x20123456789abf",
9027215253084864,
0x20123456789abf );

/***** These test the round-to-nearest-or-even-if-equally-close rule *****/

this.TestCase( SECTION,
"0x1000000000000080",
1152921504606847000,
0x1000000000000080 );

this.TestCase( SECTION,
"0x1000000000000081",
1152921504606847200,
0x1000000000000081 );

this.TestCase( SECTION,
"0x1000000000000100",
1152921504606847200,
0x1000000000000100 );
this.TestCase( SECTION,
"0x100000000000017f",
1152921504606847200,
0x100000000000017f );

this.TestCase( SECTION,
"0x1000000000000180",
1152921504606847500,
0x1000000000000180 );

this.TestCase( SECTION,
"0x1000000000000181",
1152921504606847500,
0x1000000000000181 );

this.TestCase( SECTION,
"0x10000000000001f0",
1152921504606847500,
0x10000000000001f0 );

this.TestCase( SECTION,
"0x1000000000000200",
1152921504606847500,
0x1000000000000200 );

this.TestCase( SECTION,
"0x100000000000027f",
1152921504606847500,
0x100000000000027f );

this.TestCase( SECTION,
"0x1000000000000280",
1152921504606847500,
0x1000000000000280 );

this.TestCase( SECTION,
"0x1000000000000281",
1152921504606847700,
0x1000000000000281 );

this.TestCase( SECTION,
"0x10000000000002ff",
1152921504606847700,
0x10000000000002ff );

this.TestCase( SECTION,
"0x1000000000000300",
1152921504606847700,
0x1000000000000300 );

this.TestCase( SECTION,
"0x10000000000000000",
18446744073709552000,
0x10000000000000000 );

this.TestCase( SECTION,
"parseInt(\"000000100000000100100011010001010110011110001001101010111100\",2)",
9027215253084860,
parseInt("000000100000000100100011010001010110011110001001101010111100",2) );

this.TestCase( SECTION,
"parseInt(\"000000100000000100100011010001010110011110001001101010111101\",2)",
9027215253084860,
parseInt("000000100000000100100011010001010110011110001001101010111101",2) );

this.TestCase( SECTION,
"parseInt(\"000000100000000100100011010001010110011110001001101010111111\",2)",
9027215253084864,
parseInt("000000100000000100100011010001010110011110001001101010111111",2) );

this.TestCase( SECTION,
"parseInt(\"0000001000000001001000110100010101100111100010011010101111010\",2)",
18054430506169720,
parseInt("0000001000000001001000110100010101100111100010011010101111010",2));

this.TestCase( SECTION,
"parseInt(\"0000001000000001001000110100010101100111100010011010101111011\",2)",
18054430506169724,
parseInt("0000001000000001001000110100010101100111100010011010101111011",2) );

this.TestCase( SECTION,
"parseInt(\"0000001000000001001000110100010101100111100010011010101111100\",2)",
18054430506169724,
parseInt("0000001000000001001000110100010101100111100010011010101111100",2));

this.TestCase( SECTION,
"parseInt(\"0000001000000001001000110100010101100111100010011010101111110\",2)",
18054430506169728,
parseInt("0000001000000001001000110100010101100111100010011010101111110",2));

this.TestCase( SECTION,
"parseInt(\"yz\",35)",
34,
parseInt("yz",35) );

this.TestCase( SECTION,
"parseInt(\"yz\",36)",
1259,
parseInt("yz",36) );

this.TestCase( SECTION,
"parseInt(\"yz\",37)",
NaN,
parseInt("yz",37) );

this.TestCase( SECTION,
"parseInt(\"+77\")",
77,
parseInt("+77") );

this.TestCase( SECTION,
"parseInt(\"-77\",9)",
-70,
parseInt("-77",9) );

this.TestCase( SECTION,
"parseInt(\"\\u20001234\\u2000\")",
1234,
parseInt("\u20001234\u2000") );

this.TestCase( SECTION,
"parseInt(\"123456789012345678\")",
123456789012345680,
parseInt("123456789012345678") );

this.TestCase( SECTION,
"parseInt(\"9\",8)",
NaN,
parseInt("9",8) );

this.TestCase( SECTION,
"parseInt(\"1e2\")",
1,
parseInt("1e2") );

this.TestCase( SECTION,
"parseInt(\"1.9999999999999999999\")",
1,
parseInt("1.9999999999999999999") );

this.TestCase( SECTION,
"parseInt(\"0x10\")",
16,
parseInt("0x10") );

this.TestCase( SECTION,
"parseInt(\"0x10\",10)",
0,
parseInt("0x10",10) );

this.TestCase( SECTION,
"parseInt(\"0022\")",
18,
parseInt("0022") );

this.TestCase( SECTION,
"parseInt(\"0022\",10)",
22,
parseInt("0022",10) );

this.TestCase( SECTION,
"parseInt(\"0x1000000000000080\")",
1152921504606847000,
parseInt("0x1000000000000080") );

this.TestCase( SECTION,
"parseInt(\"0x1000000000000081\")",
1152921504606847200,
parseInt("0x1000000000000081") );

s =
"0xFFFFFFFFFFFFF80000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";

this.TestCase( SECTION, "s = "+
"\"0xFFFFFFFFFFFFF80000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\";"+
"s",
"0xFFFFFFFFFFFFF80000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
s );


this.TestCase( SECTION, "s +="+
"\"0000000000000000000000000000000000000\"; s",
"0xFFFFFFFFFFFFF800000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
s += "0000000000000000000000000000000000000" );

this.TestCase( SECTION, "-s",
-1.7976931348623157e+308,
-s );

s =
"0xFFFFFFFFFFFFF80000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";

this.TestCase( SECTION, "s ="+
"\"0xFFFFFFFFFFFFF80000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\";"+
"s",
"0xFFFFFFFFFFFFF80000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
s );

this.TestCase( SECTION,
"s += \"0000000000000000000000000000000000001\"",
"0xFFFFFFFFFFFFF800000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001",
s += "0000000000000000000000000000000000001" );

this.TestCase( SECTION,
"-s",
-1.7976931348623157e+308,
-s );

s =
"0xFFFFFFFFFFFFFC0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";

this.TestCase( SECTION,
"s ="+
"\"0xFFFFFFFFFFFFFC0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\";"+
"s",
"0xFFFFFFFFFFFFFC0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
s );


this.TestCase( SECTION,
"s += \"0000000000000000000000000000000000000\"",
"0xFFFFFFFFFFFFFC00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
s += "0000000000000000000000000000000000000");


this.TestCase( SECTION,
"-s",
-Infinity,
-s );

s =
"0xFFFFFFFFFFFFFB0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";

this.TestCase( SECTION,
"s = "+
"\"0xFFFFFFFFFFFFFB0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\";s",
"0xFFFFFFFFFFFFFB0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
s);

this.TestCase( SECTION,
"s += \"0000000000000000000000000000000000001\"",
"0xFFFFFFFFFFFFFB00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001",
s += "0000000000000000000000000000000000001" );

this.TestCase( SECTION,
"-s",
-1.7976931348623157e+308,
-s );

this.TestCase( SECTION,
"s += \"0\"",
"0xFFFFFFFFFFFFFB000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000010",
s += "0" );

this.TestCase( SECTION,
"-s",
-Infinity,
-s );

this.TestCase( SECTION,
"parseInt(s)",
Infinity,
parseInt(s) );

this.TestCase( SECTION,
"parseInt(s,32)",
0,
parseInt(s,32) );

this.TestCase( SECTION,
"parseInt(s,36)",
Infinity,
parseInt(s,36) );

this.TestCase( SECTION,
"-\"\"",
0,
-"" );

this.TestCase( SECTION,
"-\" \"",
0,
-" " );

this.TestCase( SECTION,
"-\"999\"",
-999,
-"999" );

this.TestCase( SECTION,
"-\" 999\"",
-999,
-" 999" );

this.TestCase( SECTION,
"-\"\\t999\"",
-999,
-"\t999" );

this.TestCase( SECTION,
"-\"013  \"",
-13,
-"013  " );

this.TestCase( SECTION,
"-\"999\\t\"",
-999,
-"999\t" );

this.TestCase( SECTION,
"-\"-Infinity\"",
Infinity,
-"-Infinity" );

this.TestCase( SECTION,
"-\"+Infinity\"",
-Infinity,
-"+Infinity" );

this.TestCase( SECTION,
"-\"+Infiniti\"",
NaN,
-"+Infiniti" );

this.TestCase( SECTION,
"- -\"0x80000000\"",
2147483648,
- -"0x80000000" );

this.TestCase( SECTION,
"- -\"0x100000000\"",
4294967296,
- -"0x100000000" );

this.TestCase( SECTION,
"- \"-0x123456789abcde8\"",
81985529216486880,
- "-0x123456789abcde8" );

// the following two tests are not strictly ECMA 1.0

this.TestCase( SECTION,
"-\"\\u20001234\\u2001\"",
-1234,
-"\u20001234\u2001" );

this.TestCase( SECTION,
"-\"\\u20001234\\0\"",
NaN,
-"\u20001234\0" );

this.TestCase( SECTION,
"-\"0x10\"",
-16,
-"0x10" );

this.TestCase( SECTION,
"-\"+\"",
NaN,
-"+" );

this.TestCase( SECTION,
"-\"-\"",
NaN,
-"-" );

this.TestCase( SECTION,
"-\"-0-\"",
NaN,
-"-0-" );

this.TestCase( SECTION,
"-\"1e-\"",
NaN,
-"1e-" );

this.TestCase( SECTION,
"-\"1e-1\"",
-0.1,
-"1e-1" );

// test();



},

/**
File Name:          9.3.js
ECMA Section:       9.3  Type Conversion:  ToNumber
Description:        rules for converting an argument to a number.
see 9.3.1 for cases for converting strings to numbers.
special cases:
undefined           NaN
Null                NaN
Boolean             1 if true; +0 if false
Number              the argument ( no conversion )
String              see test 9.3.1
Object              see test 9.3-1

For ToNumber applied to the String type, see test 9.3.1.
For ToNumber applied to the object type, see test 9.3-1.

Author:             christine@netscape.com
Date:               10 july 1997

*/
test_9_3:function(){

var SECTION = "9.3";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "ToNumber";

// writeHeaderToLog( SECTION + " "+ TITLE);

// special cases here

this.TestCase( SECTION,   "Number()",                      0,              Number() );
this.TestCase( SECTION,   "Number(eval('var x'))",         Number.NaN,     Number(eval("var x")) );
this.TestCase( SECTION,   "Number(void 0)",                Number.NaN,     Number(void 0) );
this.TestCase( SECTION,   "Number(null)",                  0,              Number(null) );
this.TestCase( SECTION,   "Number(true)",                  1,              Number(true) );
this.TestCase( SECTION,   "Number(false)",                 0,              Number(false) );
this.TestCase( SECTION,   "Number(0)",                     0,              Number(0) );
this.TestCase( SECTION,   "Number(-0)",                    -0,             Number(-0) );
this.TestCase( SECTION,   "Number(1)",                     1,              Number(1) );
this.TestCase( SECTION,   "Number(-1)",                    -1,             Number(-1) );
this.TestCase( SECTION,   "Number(Number.MAX_VALUE)",      1.7976931348623157e308, Number(Number.MAX_VALUE) );
this.TestCase( SECTION,   "Number(Number.MIN_VALUE)",      5e-324,         Number(Number.MIN_VALUE) );

this.TestCase( SECTION,   "Number(Number.NaN)",                Number.NaN,                 Number(Number.NaN) );
this.TestCase( SECTION,   "Number(Number.POSITIVE_INFINITY)",  Number.POSITIVE_INFINITY,   Number(Number.POSITIVE_INFINITY) );
this.TestCase( SECTION,   "Number(Number.NEGATIVE_INFINITY)",  Number.NEGATIVE_INFINITY,   Number(Number.NEGATIVE_INFINITY) );

// test();

},

/**
File Name:          9.4-1.js
ECMA Section:       9.4 ToInteger
Description:        1.  Call ToNumber on the input argument
2.  If Result(1) is NaN, return +0
3.  If Result(1) is +0, -0, Infinity, or -Infinity,
return Result(1).
4.  Compute sign(Result(1)) * floor(abs(Result(1))).
5.  Return Result(4).

To test ToInteger, this test uses new Date(value),
15.9.3.7.  The Date constructor sets the [[Value]]
property of the new object to TimeClip(value), which
uses the rules:

TimeClip(time)
1. If time is not finite, return NaN
2. If abs(Result(1)) > 8.64e15, return NaN
3. Return an implementation dependent choice of either
ToInteger(Result(2)) or ToInteger(Result(2)) + (+0)
(Adding a positive 0 converts -0 to +0).

This tests ToInteger for values -8.64e15 > value > 8.64e15,
not including -0 and +0.

For additional special cases (0, +0, Infinity, -Infinity,
and NaN, see 9.4-2.js).  For value is String, see 9.4-3.js.

Author:             christine@netscape.com
Date:               10 july 1997

*/
test_9_4__1:function(){

var SECTION = "9.4-1";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "ToInteger";

// writeHeaderToLog( SECTION + " "+ TITLE);

// some special cases

this.TestCase( SECTION,  "td = new Date(Number.NaN); td.valueOf()",  Number.NaN, eval("td = new Date(Number.NaN); td.valueOf()") );
this.TestCase( SECTION,  "td = new Date(Infinity); td.valueOf()",    Number.NaN, eval("td = new Date(Number.POSITIVE_INFINITY); td.valueOf()") );
this.TestCase( SECTION,  "td = new Date(-Infinity); td.valueOf()",   Number.NaN, eval("td = new Date(Number.NEGATIVE_INFINITY); td.valueOf()") );
this.TestCase( SECTION,  "td = new Date(-0); td.valueOf()",          -0,         eval("td = new Date(-0); td.valueOf()" ) );
this.TestCase( SECTION,  "td = new Date(0); td.valueOf()",           0,          eval("td = new Date(0); td.valueOf()") );

// value is not an integer

this.TestCase( SECTION,  "td = new Date(3.14159); td.valueOf()",     3,          eval("td = new Date(3.14159); td.valueOf()") );
this.TestCase( SECTION,  "td = new Date(Math.PI); td.valueOf()",     3,          eval("td = new Date(Math.PI); td.valueOf()") );
this.TestCase( SECTION,  "td = new Date(-Math.PI);td.valueOf()",     -3,         eval("td = new Date(-Math.PI);td.valueOf()") );
this.TestCase( SECTION,  "td = new Date(3.14159e2); td.valueOf()",   314,        eval("td = new Date(3.14159e2); td.valueOf()") );

this.TestCase( SECTION,  "td = new Date(.692147e1); td.valueOf()",   6,          eval("td = new Date(.692147e1);td.valueOf()") );
this.TestCase( SECTION,  "td = new Date(-.692147e1);td.valueOf()",   -6,         eval("td = new Date(-.692147e1);td.valueOf()") );

// value is not a number

this.TestCase( SECTION,  "td = new Date(true); td.valueOf()",        1,          eval("td = new Date(true); td.valueOf()" ) );
this.TestCase( SECTION,  "td = new Date(false); td.valueOf()",       0,          eval("td = new Date(false); td.valueOf()") );

this.TestCase( SECTION,  "td = new Date(new Number(Math.PI)); td.valueOf()",  3, eval("td = new Date(new Number(Math.PI)); td.valueOf()") );
this.TestCase( SECTION,  "td = new Date(new Number(Math.PI)); td.valueOf()",  3, eval("td = new Date(new Number(Math.PI)); td.valueOf()") );

// edge cases
this.TestCase( SECTION,  "td = new Date(8.64e15); td.valueOf()",     8.64e15,    eval("td = new Date(8.64e15); td.valueOf()") );
this.TestCase( SECTION,  "td = new Date(-8.64e15); td.valueOf()",    -8.64e15,   eval("td = new Date(-8.64e15); td.valueOf()") );
this.TestCase( SECTION,  "td = new Date(8.64e-15); td.valueOf()",    0,          eval("td = new Date(8.64e-15); td.valueOf()") );
this.TestCase( SECTION,  "td = new Date(-8.64e-15); td.valueOf()",   0,          eval("td = new Date(-8.64e-15); td.valueOf()") );

// test();

},

/**
File Name:          9.4-1.js
ECMA Section:       9.4 ToInteger
Description:        1.  Call ToNumber on the input argument
2.  If Result(1) is NaN, return +0
3.  If Result(1) is +0, -0, Infinity, or -Infinity,
return Result(1).
4.  Compute sign(Result(1)) * floor(abs(Result(1))).
5.  Return Result(4).

To test ToInteger, this test uses new Date(value),
15.9.3.7.  The Date constructor sets the [[Value]]
property of the new object to TimeClip(value), which
uses the rules:

TimeClip(time)
1. If time is not finite, return NaN
2. If abs(Result(1)) > 8.64e15, return NaN
3. Return an implementation dependent choice of either
ToInteger(Result(2)) or ToInteger(Result(2)) + (+0)
(Adding a positive 0 converts -0 to +0).

This tests ToInteger for values -8.64e15 > value > 8.64e15,
not including -0 and +0.

For additional special cases (0, +0, Infinity, -Infinity,
and NaN, see 9.4-2.js).  For value is String, see 9.4-3.js.

Author:             christine@netscape.com
Date:               10 july 1997

*/
test_9_4__2:function(){

var SECTION = "9.4-1";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "ToInteger";

// writeHeaderToLog( SECTION + " "+ TITLE);

// some special cases

this.TestCase( SECTION,  "td = new Date(Number.NaN); td.valueOf()",  Number.NaN, eval("td = new Date(Number.NaN); td.valueOf()") );
this.TestCase( SECTION,  "td = new Date(Infinity); td.valueOf()",    Number.NaN, eval("td = new Date(Number.POSITIVE_INFINITY); td.valueOf()") );
this.TestCase( SECTION,  "td = new Date(-Infinity); td.valueOf()",   Number.NaN, eval("td = new Date(Number.NEGATIVE_INFINITY); td.valueOf()") );
this.TestCase( SECTION,  "td = new Date(-0); td.valueOf()",          -0,         eval("td = new Date(-0); td.valueOf()" ) );
this.TestCase( SECTION,  "td = new Date(0); td.valueOf()",           0,          eval("td = new Date(0); td.valueOf()") );

// value is not an integer

this.TestCase( SECTION,  "td = new Date(3.14159); td.valueOf()",     3,          eval("td = new Date(3.14159); td.valueOf()") );
this.TestCase( SECTION,  "td = new Date(Math.PI); td.valueOf()",     3,          eval("td = new Date(Math.PI); td.valueOf()") );
this.TestCase( SECTION,  "td = new Date(-Math.PI);td.valueOf()",     -3,         eval("td = new Date(-Math.PI);td.valueOf()") );
this.TestCase( SECTION,  "td = new Date(3.14159e2); td.valueOf()",   314,        eval("td = new Date(3.14159e2); td.valueOf()") );

this.TestCase( SECTION,  "td = new Date(.692147e1); td.valueOf()",   6,          eval("td = new Date(.692147e1);td.valueOf()") );
this.TestCase( SECTION,  "td = new Date(-.692147e1);td.valueOf()",   -6,         eval("td = new Date(-.692147e1);td.valueOf()") );

// value is not a number

this.TestCase( SECTION,  "td = new Date(true); td.valueOf()",        1,          eval("td = new Date(true); td.valueOf()" ) );
this.TestCase( SECTION,  "td = new Date(false); td.valueOf()",       0,          eval("td = new Date(false); td.valueOf()") );

this.TestCase( SECTION,  "td = new Date(new Number(Math.PI)); td.valueOf()",  3, eval("td = new Date(new Number(Math.PI)); td.valueOf()") );
this.TestCase( SECTION,  "td = new Date(new Number(Math.PI)); td.valueOf()",  3, eval("td = new Date(new Number(Math.PI)); td.valueOf()") );

// edge cases
this.TestCase( SECTION,  "td = new Date(8.64e15); td.valueOf()",     8.64e15,    eval("td = new Date(8.64e15); td.valueOf()") );
this.TestCase( SECTION,  "td = new Date(-8.64e15); td.valueOf()",    -8.64e15,   eval("td = new Date(-8.64e15); td.valueOf()") );
this.TestCase( SECTION,  "td = new Date(8.64e-15); td.valueOf()",    0,          eval("td = new Date(8.64e-15); td.valueOf()") );
this.TestCase( SECTION,  "td = new Date(-8.64e-15); td.valueOf()",   0,          eval("td = new Date(-8.64e-15); td.valueOf()") );

// test();

},

/**
File Name:          9.5-2.js
ECMA Section:       9.5  Type Conversion:  ToInt32
Description:        rules for converting an argument to a signed 32 bit integer

this test uses << 0 to convert the argument to a 32bit
integer.

The operator ToInt32 converts its argument to one of 2^32
integer values in the range -2^31 through 2^31 inclusive.
This operator functions as follows:

1 call ToNumber on argument
2 if result is NaN, 0, -0, return 0
3 compute (sign (result(1)) * floor(abs(result 1)))
4 compute result(3) modulo 2^32:
5 if result(4) is greater than or equal to 2^31, return
result(5)-2^32.  otherwise, return result(5)

special cases:
-0          returns 0
Infinity    returns 0
-Infinity   returns 0
ToInt32(ToUint32(x)) == ToInt32(x) for all values of x
Numbers greater than 2^31 (see step 5 above)
(note http://bugzilla.mozilla.org/show_bug.cgi?id=120083)

Author:             christine@netscape.com
Date:               17 july 1997
*/
test_9_5__2:function(){

var SECTION = "9.5-2";
var VERSION = "ECMA_1";
// startTest();

// writeHeaderToLog( SECTION + " ToInt32");

this.TestCase( SECTION,   "0 << 0",                        0,              0 << 0 );
this.TestCase( SECTION,   "-0 << 0",                       0,              -0 << 0 );
this.TestCase( SECTION,   "Infinity << 0",                 0,              "Infinity" << 0 );
this.TestCase( SECTION,   "-Infinity << 0",                0,              "-Infinity" << 0 );
this.TestCase( SECTION,   "Number.POSITIVE_INFINITY << 0", 0,              Number.POSITIVE_INFINITY << 0 );
this.TestCase( SECTION,   "Number.NEGATIVE_INFINITY << 0", 0,              Number.NEGATIVE_INFINITY << 0 );
this.TestCase( SECTION,   "Number.NaN << 0",               0,              Number.NaN << 0 );

this.TestCase( SECTION,   "Number.MIN_VALUE << 0",         0,              Number.MIN_VALUE << 0 );
this.TestCase( SECTION,   "-Number.MIN_VALUE << 0",        0,              -Number.MIN_VALUE << 0 );
this.TestCase( SECTION,   "0.1 << 0",                      0,              0.1 << 0 );
this.TestCase( SECTION,   "-0.1 << 0",                     0,              -0.1 << 0 );
this.TestCase( SECTION,   "1 << 0",                        1,              1 << 0 );
this.TestCase( SECTION,   "1.1 << 0",                      1,              1.1 << 0 );
this.TestCase( SECTION,   "-1 << 0",                     ToInt32(-1),             -1 << 0 );


this.TestCase( SECTION,   "2147483647 << 0",     ToInt32(2147483647),    2147483647 << 0 );
this.TestCase( SECTION,   "2147483648 << 0",     ToInt32(2147483648),    2147483648 << 0 );
this.TestCase( SECTION,   "2147483649 << 0",     ToInt32(2147483649),    2147483649 << 0 );

this.TestCase( SECTION,   "(Math.pow(2,31)-1) << 0", ToInt32(2147483647),    (Math.pow(2,31)-1) << 0 );
this.TestCase( SECTION,   "Math.pow(2,31) << 0",     ToInt32(2147483648),    Math.pow(2,31) << 0 );
this.TestCase( SECTION,   "(Math.pow(2,31)+1) << 0", ToInt32(2147483649),    (Math.pow(2,31)+1) << 0 );

this.TestCase( SECTION,   "(Math.pow(2,32)-1) << 0",   ToInt32(4294967295),    (Math.pow(2,32)-1) << 0 );
this.TestCase( SECTION,   "(Math.pow(2,32)) << 0",     ToInt32(4294967296),    (Math.pow(2,32)) << 0 );
this.TestCase( SECTION,   "(Math.pow(2,32)+1) << 0",   ToInt32(4294967297),    (Math.pow(2,32)+1) << 0 );

this.TestCase( SECTION,   "4294967295 << 0",     ToInt32(4294967295),    4294967295 << 0 );
this.TestCase( SECTION,   "4294967296 << 0",     ToInt32(4294967296),    4294967296 << 0 );
this.TestCase( SECTION,   "4294967297 << 0",     ToInt32(4294967297),    4294967297 << 0 );

this.TestCase( SECTION,   "'2147483647' << 0",   ToInt32(2147483647),    '2147483647' << 0 );
this.TestCase( SECTION,   "'2147483648' << 0",   ToInt32(2147483648),    '2147483648' << 0 );
this.TestCase( SECTION,   "'2147483649' << 0",   ToInt32(2147483649),    '2147483649' << 0 );

this.TestCase( SECTION,   "'4294967295' << 0",   ToInt32(4294967295),    '4294967295' << 0 );
this.TestCase( SECTION,   "'4294967296' << 0",   ToInt32(4294967296),    '4294967296' << 0 );
this.TestCase( SECTION,   "'4294967297' << 0",   ToInt32(4294967297),    '4294967297' << 0 );

this.TestCase( SECTION,   "-2147483647 << 0",    ToInt32(-2147483647),   -2147483647	<< 0 );
this.TestCase( SECTION,   "-2147483648 << 0",    ToInt32(-2147483648),   -2147483648 << 0 );
this.TestCase( SECTION,   "-2147483649 << 0",    ToInt32(-2147483649),   -2147483649 << 0 );

this.TestCase( SECTION,   "-4294967295 << 0",    ToInt32(-4294967295),   -4294967295 << 0 );
this.TestCase( SECTION,   "-4294967296 << 0",    ToInt32(-4294967296),   -4294967296 << 0 );
this.TestCase( SECTION,   "-4294967297 << 0",    ToInt32(-4294967297),   -4294967297 << 0 );

/*
* Numbers between 2^31 and 2^32 will have a negative ToInt32 per ECMA (see step 5 of introduction)
* (These are by stevechapel@earthlink.net; cf. http://bugzilla.mozilla.org/show_bug.cgi?id=120083)
*/
this.TestCase( SECTION,   "2147483648.25 << 0",  ToInt32(2147483648.25),   2147483648.25 << 0 );
this.TestCase( SECTION,   "2147483648.5 << 0",   ToInt32(2147483648.5),    2147483648.5 << 0 );
this.TestCase( SECTION,   "2147483648.75 << 0",  ToInt32(2147483648.75),   2147483648.75 << 0 );
this.TestCase( SECTION,   "4294967295.25 << 0",  ToInt32(4294967295.25),   4294967295.25 << 0 );
this.TestCase( SECTION,   "4294967295.5 << 0",   ToInt32(4294967295.5),    4294967295.5 << 0 );
this.TestCase( SECTION,   "4294967295.75 << 0",  ToInt32(4294967295.75),   4294967295.75 << 0 );
this.TestCase( SECTION,   "3000000000.25 << 0",  ToInt32(3000000000.25),   3000000000.25 << 0 );
this.TestCase( SECTION,   "3000000000.5 << 0",   ToInt32(3000000000.5),    3000000000.5 << 0 );
this.TestCase( SECTION,   "3000000000.75 << 0",  ToInt32(3000000000.75),   3000000000.75 << 0 );

/*
* Numbers between - 2^31 and - 2^32
*/
this.TestCase( SECTION,   "-2147483648.25 << 0",  ToInt32(-2147483648.25),   -2147483648.25 << 0 );
this.TestCase( SECTION,   "-2147483648.5 << 0",   ToInt32(-2147483648.5),    -2147483648.5 << 0 );
this.TestCase( SECTION,   "-2147483648.75 << 0",  ToInt32(-2147483648.75),   -2147483648.75 << 0 );
this.TestCase( SECTION,   "-4294967295.25 << 0",  ToInt32(-4294967295.25),   -4294967295.25 << 0 );
this.TestCase( SECTION,   "-4294967295.5 << 0",   ToInt32(-4294967295.5),    -4294967295.5 << 0 );
this.TestCase( SECTION,   "-4294967295.75 << 0",  ToInt32(-4294967295.75),   -4294967295.75 << 0 );
this.TestCase( SECTION,   "-3000000000.25 << 0",  ToInt32(-3000000000.25),   -3000000000.25 << 0 );
this.TestCase( SECTION,   "-3000000000.5 << 0",   ToInt32(-3000000000.5),    -3000000000.5 << 0 );
this.TestCase( SECTION,   "-3000000000.75 << 0",  ToInt32(-3000000000.75),   -3000000000.75 << 0 );


// test();
},

/**
File Name:          9.6.js
ECMA Section:       9.6  Type Conversion:  ToUint32
Description:        rules for converting an argument to an unsigned
32 bit integer

this test uses >>> 0 to convert the argument to
an unsigned 32bit integer.

1 call ToNumber on argument
2 if result is NaN, 0, -0, Infinity, -Infinity
return 0
3 compute (sign (result(1)) * floor(abs(result 1)))
4 compute result(3) modulo 2^32:
5 return result(4)

special cases:
-0          returns 0
Infinity    returns 0
-Infinity   returns 0
0           returns 0
ToInt32(ToUint32(x)) == ToInt32(x) for all values of x
** NEED TO DO THIS PART IN A SEPARATE TEST FILE **


Author:             christine@netscape.com
Date:               17 july 1997
*/
test_9_6:function(){


var SECTION = "9.6";
var VERSION = "ECMA_1";
// startTest();

// writeHeaderToLog( SECTION + " Type Conversion:  ToUint32");

this.TestCase( SECTION,    "0 >>> 0",                          0,          0 >>> 0 );
//    this.TestCase( SECTION,    "+0 >>> 0",                         0,          +0 >>> 0);
this.TestCase( SECTION,    "-0 >>> 0",                         0,          -0 >>> 0 );
this.TestCase( SECTION,    "'Infinity' >>> 0",                 0,          "Infinity" >>> 0 );
this.TestCase( SECTION,    "'-Infinity' >>> 0",                0,          "-Infinity" >>> 0);
this.TestCase( SECTION,    "'+Infinity' >>> 0",                0,          "+Infinity" >>> 0 );
this.TestCase( SECTION,    "Number.POSITIVE_INFINITY >>> 0",   0,          Number.POSITIVE_INFINITY >>> 0 );
this.TestCase( SECTION,    "Number.NEGATIVE_INFINITY >>> 0",   0,          Number.NEGATIVE_INFINITY >>> 0 );
this.TestCase( SECTION,    "Number.NaN >>> 0",                 0,          Number.NaN >>> 0 );

this.TestCase( SECTION,    "Number.MIN_VALUE >>> 0",           0,          Number.MIN_VALUE >>> 0 );
this.TestCase( SECTION,    "-Number.MIN_VALUE >>> 0",          0,          Number.MIN_VALUE >>> 0 );
this.TestCase( SECTION,    "0.1 >>> 0",                        0,          0.1 >>> 0 );
this.TestCase( SECTION,    "-0.1 >>> 0",                       0,          -0.1 >>> 0 );
this.TestCase( SECTION,    "1 >>> 0",                          1,          1 >>> 0 );
this.TestCase( SECTION,    "1.1 >>> 0",                        1,          1.1 >>> 0 );

this.TestCase( SECTION,    "-1.1 >>> 0",                       this.ToUint32(-1.1),       -1.1 >>> 0 );
this.TestCase( SECTION,    "-1 >>> 0",                         this.ToUint32(-1),         -1 >>> 0 );

this.TestCase( SECTION,    "2147483647 >>> 0",         this.ToUint32(2147483647),     2147483647 >>> 0 );
this.TestCase( SECTION,    "2147483648 >>> 0",         this.ToUint32(2147483648),     2147483648 >>> 0 );
this.TestCase( SECTION,    "2147483649 >>> 0",         this.ToUint32(2147483649),     2147483649 >>> 0 );

this.TestCase( SECTION,    "4294967295 >>> 0",         this.ToUint32(4294967295),     4294967295 >>> 0 );
this.TestCase( SECTION,    "4294967296 >>> 0",         this.ToUint32(4294967296),     4294967296 >>> 0 );
this.TestCase( SECTION,    "4294967297 >>> 0",         this.ToUint32(4294967297),     4294967297 >>> 0 );

this.TestCase( SECTION,    "-2147483647 >>> 0",        this.ToUint32(-2147483647),    -2147483647 >>> 0 );
this.TestCase( SECTION,    "-2147483648 >>> 0",        this.ToUint32(-2147483648),    -2147483648 >>> 0 );
this.TestCase( SECTION,    "-2147483649 >>> 0",        this.ToUint32(-2147483649),    -2147483649 >>> 0 );

this.TestCase( SECTION,    "-4294967295 >>> 0",        this.ToUint32(-4294967295),    -4294967295 >>> 0 );
this.TestCase( SECTION,    "-4294967296 >>> 0",        this.ToUint32(-4294967296),    -4294967296 >>> 0 );
this.TestCase( SECTION,    "-4294967297 >>> 0",        this.ToUint32(-4294967297),    -4294967297 >>> 0 );

this.TestCase( SECTION,    "'2147483647' >>> 0",       this.ToUint32(2147483647),     '2147483647' >>> 0 );
this.TestCase( SECTION,    "'2147483648' >>> 0",       this.ToUint32(2147483648),     '2147483648' >>> 0 );
this.TestCase( SECTION,    "'2147483649' >>> 0",       this.ToUint32(2147483649),     '2147483649' >>> 0 );

this.TestCase( SECTION,    "'4294967295' >>> 0",       this.ToUint32(4294967295),     '4294967295' >>> 0 );
this.TestCase( SECTION,    "'4294967296' >>> 0",       this.ToUint32(4294967296),     '4294967296' >>> 0 );
this.TestCase( SECTION,    "'4294967297' >>> 0",       this.ToUint32(4294967297),     '4294967297' >>> 0 );


// test();
},

/**
File Name:          9.7.js
ECMA Section:       9.7  Type Conversion:  ToInt16
Description:        rules for converting an argument to an unsigned
16 bit integer in the range 0 to 2^16-1.

this test uses String.prototype.fromCharCode() and
String.prototype.charCodeAt() to test ToInt16.

special cases:
-0          returns 0
Infinity    returns 0
-Infinity   returns 0
0           returns 0

Author:             christine@netscape.com
Date:               17 july 1997
*/
test_9_7:function(){

var SECTION = "9.7";
var VERSION = "ECMA_1";
// startTest();

// writeHeaderToLog( SECTION + " Type Conversion:  ToInt16");

/*
this.TestCase( "9.7",   "String.fromCharCode(0).charCodeAt(0)",          0,      String.fromCharCode(0).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(-0).charCodeAt(0)",         0,      String.fromCharCode(-0).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(1).charCodeAt(0)",          1,      String.fromCharCode(1).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(64).charCodeAt(0)",         64,     String.fromCharCode(64).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(126).charCodeAt(0)",        126,    String.fromCharCode(126).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(127).charCodeAt(0)",        127,    String.fromCharCode(127).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(128).charCodeAt(0)",        128,    String.fromCharCode(128).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(130).charCodeAt(0)",        130,    String.fromCharCode(130).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(255).charCodeAt(0)",        255,    String.fromCharCode(255).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(256).charCodeAt(0)",        256,    String.fromCharCode(256).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(Math.pow(2,16)-1).charCodeAt(0)",   65535,  String.fromCharCode(Math.pow(2,16)-1).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(Math.pow(2,16)).charCodeAt(0)",     0,      String.fromCharCode(Math.pow(2,16)).charCodeAt(0) );
*/


this.TestCase( "9.7",   "String.fromCharCode(0).charCodeAt(0)",          this.ToInt16(0),      String.fromCharCode(0).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(-0).charCodeAt(0)",         this.ToInt16(0),      String.fromCharCode(-0).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(1).charCodeAt(0)",          this.ToInt16(1),      String.fromCharCode(1).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(64).charCodeAt(0)",         this.ToInt16(64),     String.fromCharCode(64).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(126).charCodeAt(0)",        this.ToInt16(126),    String.fromCharCode(126).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(127).charCodeAt(0)",        this.ToInt16(127),    String.fromCharCode(127).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(128).charCodeAt(0)",        this.ToInt16(128),    String.fromCharCode(128).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(130).charCodeAt(0)",        this.ToInt16(130),    String.fromCharCode(130).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(255).charCodeAt(0)",        this.ToInt16(255),    String.fromCharCode(255).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(256).charCodeAt(0)",        this.ToInt16(256),    String.fromCharCode(256).charCodeAt(0) );

this.TestCase( "9.7",   "String.fromCharCode(Math.pow(2,16)-1).charCodeAt(0)",   65535,  String.fromCharCode(Math.pow(2,16)-1).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(Math.pow(2,16)).charCodeAt(0)",     0,      String.fromCharCode(Math.pow(2,16)).charCodeAt(0) );

this.TestCase( "9.7",   "String.fromCharCode(65535).charCodeAt(0)",     this.ToInt16(65535),      String.fromCharCode(65535).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(65536).charCodeAt(0)",     this.ToInt16(65536),      String.fromCharCode(65536).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(65537).charCodeAt(0)",     this.ToInt16(65537),      String.fromCharCode(65537).charCodeAt(0) );

this.TestCase( "9.7",   "String.fromCharCode(131071).charCodeAt(0)",     this.ToInt16(131071),    String.fromCharCode(131071).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(131072).charCodeAt(0)",     this.ToInt16(131072),    String.fromCharCode(131072).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(131073).charCodeAt(0)",     this.ToInt16(131073),    String.fromCharCode(131073).charCodeAt(0) );

this.TestCase( "9.7",   "String.fromCharCode('65535').charCodeAt(0)",     65535,             String.fromCharCode("65535").charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode('65536').charCodeAt(0)",     0,                 String.fromCharCode("65536").charCodeAt(0) );

this.TestCase( "9.7",   "String.fromCharCode(-1).charCodeAt(0)",         this.ToInt16(-1),        String.fromCharCode(-1).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(-64).charCodeAt(0)",        this.ToInt16(-64),       String.fromCharCode(-64).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(-126).charCodeAt(0)",       this.ToInt16(-126),      String.fromCharCode(-126).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(-127).charCodeAt(0)",       this.ToInt16(-127),      String.fromCharCode(-127).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(-128).charCodeAt(0)",       this.ToInt16(-128),      String.fromCharCode(-128).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(-130).charCodeAt(0)",       this.ToInt16(-130),      String.fromCharCode(-130).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(-255).charCodeAt(0)",       this.ToInt16(-255),      String.fromCharCode(-255).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(-256).charCodeAt(0)",       this.ToInt16(-256),      String.fromCharCode(-256).charCodeAt(0) );

this.TestCase( "9.7",   "String.fromCharCode(-Math.pow(2,16)-1).charCodeAt(0)",   65535,     String.fromCharCode(-Math.pow(2,16)-1).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(-Math.pow(2,16)).charCodeAt(0)",     0,         String.fromCharCode(-Math.pow(2,16)).charCodeAt(0) );

this.TestCase( "9.7",   "String.fromCharCode(-65535).charCodeAt(0)",     this.ToInt16(-65535),    String.fromCharCode(-65535).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(-65536).charCodeAt(0)",     this.ToInt16(-65536),    String.fromCharCode(-65536).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(-65537).charCodeAt(0)",     this.ToInt16(-65537),    String.fromCharCode(-65537).charCodeAt(0) );

this.TestCase( "9.7",   "String.fromCharCode(-131071).charCodeAt(0)",    this.ToInt16(-131071),   String.fromCharCode(-131071).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(-131072).charCodeAt(0)",    this.ToInt16(-131072),   String.fromCharCode(-131072).charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode(-131073).charCodeAt(0)",    this.ToInt16(-131073),   String.fromCharCode(-131073).charCodeAt(0) );

this.TestCase( "9.7",   "String.fromCharCode('-65535').charCodeAt(0)",   this.ToInt16(-65535),    String.fromCharCode("-65535").charCodeAt(0) );
this.TestCase( "9.7",   "String.fromCharCode('-65536').charCodeAt(0)",   this.ToInt16(-65536),    String.fromCharCode("-65536").charCodeAt(0) );


//    this.TestCase( "9.7",   "String.fromCharCode(2147483648).charCodeAt(0)", this.ToInt16(2147483648),      String.fromCharCode(2147483648).charCodeAt(0) );



//    the following test cases cause a runtime error.  see:  http://scopus.mcom.com/bugsplat/show_bug.cgi?id=78878

//    this.TestCase( "9.7",   "String.fromCharCode(Infinity).charCodeAt(0)",           0,      String.fromCharCode("Infinity").charCodeAt(0) );
//    this.TestCase( "9.7",   "String.fromCharCode(-Infinity).charCodeAt(0)",          0,      String.fromCharCode("-Infinity").charCodeAt(0) );
//    this.TestCase( "9.7",   "String.fromCharCode(NaN).charCodeAt(0)",                0,      String.fromCharCode(Number.NaN).charCodeAt(0) );
//    this.TestCase( "9.7",   "String.fromCharCode(Number.POSITIVE_INFINITY).charCodeAt(0)",   0,  String.fromCharCode(Number.POSITIVE_INFINITY).charCodeAt(0) );
//    this.TestCase( "9.7",   "String.fromCharCode(Number.NEGATIVE_INFINITY).charCodeAt(0)",   0,  String.fromCharCode(Number.NEGATIVE_INFINITY).charCodeAt(0) );

// test();

},

/**
File Name:          9.8.1.js
ECMA Section:       9.8.1 ToString Applied to the Number Type
Description:        The operator ToString convers a number m to string
as follows:

1.  if m is NaN, return the string "NaN"
2.  if m is +0 or -0, return the string "0"
3.  if m is less than zero, return the string
concatenation of the string "-" and ToString(-m).
4.  If m is Infinity, return the string "Infinity".
5.  Otherwise, let n, k, and s be integers such that
k >= 1, 10k1 <= s < 10k, the number value for s10nk
is m, and k is as small as possible. Note that k is
the number of digits in the decimal representation
of s, that s is not divisible by 10, and that the
least significant digit of s is not necessarily
uniquely determined by these criteria.
6.  If k <= n <= 21, return the string consisting of the
k digits of the decimal representation of s (in order,
with no leading zeroes), followed by n-k occurences
of the character '0'.
7.  If 0 < n <= 21, return the string consisting of the
most significant n digits of the decimal
representation of s, followed by a decimal point
'.', followed by the remaining kn digits of the
decimal representation of s.
8.  If 6 < n <= 0, return the string consisting of the
character '0', followed by a decimal point '.',
followed by n occurences of the character '0',
followed by the k digits of the decimal
representation of s.
9.  Otherwise, if k = 1, return the string consisting
of the single digit of s, followed by lowercase
character 'e', followed by a plus sign '+' or minus
sign '' according to whether n1 is positive or
negative, followed by the decimal representation
of the integer abs(n1) (with no leading zeros).
10.  Return the string consisting of the most significant
digit of the decimal representation of s, followed
by a decimal point '.', followed by the remaining k1
digits of the decimal representation of s, followed
by the lowercase character 'e', followed by a plus
sign '+' or minus sign '' according to whether n1 is
positive or negative, followed by the decimal
representation of the integer abs(n1) (with no
leading zeros).

Note that if x is any number value other than 0, then
ToNumber(ToString(x)) is exactly the same number value as x.

As noted, the least significant digit of s is not always
uniquely determined by the requirements listed in step 5.
The following specification for step 5 was considered, but
not adopted:

Author:         christine@netscape.com
Date:           10 july 1997
*/
test_9_8_1:function(){


var SECTION = "9.8.1";
var VERSION = "ECMA_1";
// startTest();

// writeHeaderToLog( SECTION + " ToString applied to the Number type");

this.TestCase( SECTION,    "Number.NaN",       "NaN",                  Number.NaN + "" );
this.TestCase( SECTION,    "0",                "0",                    0 + "" );
this.TestCase( SECTION,    "-0",               "0",                   -0 + "" );
this.TestCase( SECTION,    "Number.POSITIVE_INFINITY", "Infinity",     Number.POSITIVE_INFINITY + "" );
this.TestCase( SECTION,    "Number.NEGATIVE_INFINITY", "-Infinity",    Number.NEGATIVE_INFINITY + "" );
this.TestCase( SECTION,    "-1",               "-1",                   -1 + "" );

// cases in step 6:  integers  1e21 > x >= 1 or -1 >= x > -1e21

this.TestCase( SECTION,    "1",                    "1",                    1 + "" );
this.TestCase( SECTION,    "10",                   "10",                   10 + "" );
this.TestCase( SECTION,    "100",                  "100",                  100 + "" );
this.TestCase( SECTION,    "1000",                 "1000",                 1000 + "" );
this.TestCase( SECTION,    "10000",                "10000",                10000 + "" );
this.TestCase( SECTION,    "10000000000",          "10000000000",          10000000000 + "" );
this.TestCase( SECTION,    "10000000000000000000", "10000000000000000000", 10000000000000000000 + "" );
this.TestCase( SECTION,    "100000000000000000000","100000000000000000000",100000000000000000000 + "" );

this.TestCase( SECTION,    "12345",                    "12345",                    12345 + "" );
this.TestCase( SECTION,    "1234567890",               "1234567890",               1234567890 + "" );

this.TestCase( SECTION,    "-1",                       "-1",                       -1 + "" );
this.TestCase( SECTION,    "-10",                      "-10",                      -10 + "" );
this.TestCase( SECTION,    "-100",                     "-100",                     -100 + "" );
this.TestCase( SECTION,    "-1000",                    "-1000",                    -1000 + "" );
this.TestCase( SECTION,    "-1000000000",              "-1000000000",              -1000000000 + "" );
this.TestCase( SECTION,    "-1000000000000000",        "-1000000000000000",        -1000000000000000 + "" );
this.TestCase( SECTION,    "-100000000000000000000",   "-100000000000000000000",   -100000000000000000000 + "" );
this.TestCase( SECTION,    "-1000000000000000000000",  "-1e+21",                   -1000000000000000000000 + "" );

this.TestCase( SECTION,    "-12345",                    "-12345",                  -12345 + "" );
this.TestCase( SECTION,    "-1234567890",               "-1234567890",             -1234567890 + "" );

// cases in step 7: numbers with a fractional component, 1e21> x >1 or  -1 > x > -1e21,
this.TestCase( SECTION,    "1.0000001",                "1.0000001",                1.0000001 + "" );

// cases in step 8:  fractions between 1 > x > -1, exclusive of 0 and -0

// cases in step 9:  numbers with 1 significant digit >= 1e+21 or <= 1e-6

this.TestCase( SECTION,    "1000000000000000000000",   "1e+21",             1000000000000000000000 + "" );
this.TestCase( SECTION,    "10000000000000000000000",   "1e+22",            10000000000000000000000 + "" );

//  cases in step 10:  numbers with more than 1 significant digit >= 1e+21 or <= 1e-6

this.TestCase( SECTION,    "1.2345",                    "1.2345",                  String( 1.2345));
this.TestCase( SECTION,    "1.234567890",               "1.23456789",             String( 1.234567890 ));


this.TestCase( SECTION,    ".12345",                   "0.12345",                String(.12345 )     );
this.TestCase( SECTION,    ".012345",                  "0.012345",               String(.012345)     );
this.TestCase( SECTION,    ".0012345",                 "0.0012345",              String(.0012345)    );
this.TestCase( SECTION,    ".00012345",                "0.00012345",             String(.00012345)   );
this.TestCase( SECTION,    ".000012345",               "0.000012345",            String(.000012345)  );
this.TestCase( SECTION,    ".0000012345",              "0.0000012345",           String(.0000012345) );
this.TestCase( SECTION,    ".00000012345",             "1.2345e-7",              String(.00000012345));

this.TestCase( SECTION,    "-1e21",                    "-1e+21",                 String(-1e21) );

// test();


},

/**
File Name:          9.9-1.js
ECMA Section:       9.9  Type Conversion:  ToObject
Description:

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
test_9_9__1:function(){


var VERSION = "ECMA_1";
// startTest();
var SECTION = "9.9-1";

// writeHeaderToLog( SECTION + " Type Conversion: ToObject" );

this.TestCase( SECTION, "Object(true).valueOf()",    true,                   (Object(true)).valueOf() );
this.TestCase( SECTION, "typeof Object(true)",       "object",               typeof Object(true) );

this.TestCase( SECTION, "Object(false).valueOf()",    false,                  (Object(false)).valueOf() );
this.TestCase( SECTION, "typeof Object(false)",      "object",               typeof Object(false) );

this.TestCase( SECTION, "Object(0).valueOf()",       0,                      (Object(0)).valueOf() );
this.TestCase( SECTION, "typeof Object(0)",          "object",               typeof Object(0) );

this.TestCase( SECTION, "Object(-0).valueOf()",      -0,                     (Object(-0)).valueOf() );
this.TestCase( SECTION, "typeof Object(-0)",         "object",               typeof Object(-0) );

this.TestCase( SECTION, "Object(1).valueOf()",       1,                      (Object(1)).valueOf() );
this.TestCase( SECTION, "typeof Object(1)",          "object",               typeof Object(1) );

this.TestCase( SECTION, "Object(-1).valueOf()",      -1,                     (Object(-1)).valueOf() );
this.TestCase( SECTION, "typeof Object(-1)",         "object",               typeof Object(-1) );

this.TestCase( SECTION, "Object(Number.MAX_VALUE).valueOf()",    1.7976931348623157e308,         (Object(Number.MAX_VALUE)).valueOf() );
this.TestCase( SECTION, "typeof Object(Number.MAX_VALUE)",       "object",                       typeof Object(Number.MAX_VALUE) );

this.TestCase( SECTION, "Object(Number.MIN_VALUE).valueOf()",     5e-324,           (Object(Number.MIN_VALUE)).valueOf() );
this.TestCase( SECTION, "typeof Object(Number.MIN_VALUE)",       "object",         typeof Object(Number.MIN_VALUE) );

this.TestCase( SECTION, "Object(Number.POSITIVE_INFINITY).valueOf()",    Number.POSITIVE_INFINITY,       (Object(Number.POSITIVE_INFINITY)).valueOf() );
this.TestCase( SECTION, "typeof Object(Number.POSITIVE_INFINITY)",       "object",                       typeof Object(Number.POSITIVE_INFINITY) );

this.TestCase( SECTION, "Object(Number.NEGATIVE_INFINITY).valueOf()",    Number.NEGATIVE_INFINITY,       (Object(Number.NEGATIVE_INFINITY)).valueOf() );
this.TestCase( SECTION, "typeof Object(Number.NEGATIVE_INFINITY)",       "object",            typeof Object(Number.NEGATIVE_INFINITY) );

this.TestCase( SECTION, "Object(Number.NaN).valueOf()",      Number.NaN,                (Object(Number.NaN)).valueOf() );
this.TestCase( SECTION, "typeof Object(Number.NaN)",         "object",                  typeof Object(Number.NaN) );

this.TestCase( SECTION, "Object('a string').valueOf()",      "a string",         (Object("a string")).valueOf() );
this.TestCase( SECTION, "typeof Object('a string')",         "object",           typeof (Object("a string")) );

this.TestCase( SECTION, "Object('').valueOf()",              "",                 (Object("")).valueOf() );
this.TestCase( SECTION, "typeof Object('')",                 "object",           typeof (Object("")) );

this.TestCase( SECTION, "Object('\\r\\t\\b\\n\\v\\f').valueOf()",   "\r\t\b\n\v\f",   (Object("\r\t\b\n\v\f")).valueOf() );
this.TestCase( SECTION, "typeof Object('\\r\\t\\b\\n\\v\\f')",      "object",           typeof (Object("\\r\\t\\b\\n\\v\\f")) );

this.TestCase( SECTION,  "Object( '\\\'\\\"\\' ).valueOf()",      "\'\"\\",          (Object("\'\"\\")).valueOf() );
this.TestCase( SECTION,  "typeof Object( '\\\'\\\"\\' )",        "object",           typeof Object("\'\"\\") );

this.TestCase( SECTION, "Object( new MyObject(true) ).valueOf()",    true,           eval("Object( new MyObject(true) ).valueOf()") );
this.TestCase( SECTION, "typeof Object( new MyObject(true) )",       "object",       eval("typeof Object( new MyObject(true) )") );
this.TestCase( SECTION, "(Object( new MyObject(true) )).toString()",  "[object Object]",       eval("(Object( new MyObject(true) )).toString()") );

// test();

}

}).endType();

