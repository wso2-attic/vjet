vjo.ctype("dsf.jslang.feature.tests.EcmaExpressionsTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({

constructs: function() {
this.base();
},

/**
File Name:          11.1.1.js
ECMA Section:       11.1.1 The this keyword
Description:

The this keyword evaluates to the this value of the execution context.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_1_1: function() {
var SECTION = "11.1.1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " The this keyword");

var GLOBAL_OBJECT = this.toString();

// this in global code and eval(this) in global code should return the global object.

this.TestCase( SECTION,
"Global Code: this.toString()",
GLOBAL_OBJECT,
this.toString() );

this.TestCase( SECTION,
"Global Code:  eval('this.toString()')",
GLOBAL_OBJECT,
eval('this.toString()') );

// this in anonymous code called as a function should return the global object.
this.TestCase( SECTION,
"Anonymous Code: var MYFUNC = new Function('return this.toString()'); MYFUNC()",
GLOBAL_OBJECT,
eval("var MYFUNC = new Function('return this.toString()'); MYFUNC()") );

// eval( this ) in anonymous code called as a function should return that function's activation object

this.TestCase( SECTION,
"Anonymous Code: var MYFUNC = new Function('return (eval(\"this.toString()\")'); (MYFUNC()).toString()",
GLOBAL_OBJECT,
eval("var MYFUNC = new Function('return eval(\"this.toString()\")'); (MYFUNC()).toString()") );

// this and eval( this ) in anonymous code called as a constructor should return the object

this.TestCase( SECTION,
"Anonymous Code: var MYFUNC = new Function('this.THIS = this'); ((new MYFUNC()).THIS).toString()",
"[object Object]",
eval("var MYFUNC = new Function('this.THIS = this'); ((new MYFUNC()).THIS).toString()") );

this.TestCase( SECTION,
"Anonymous Code: var MYFUNC = new Function('this.THIS = this'); var FUN1 = new MYFUNC(); FUN1.THIS == FUN1",
true,
eval("var MYFUNC = new Function('this.THIS = this'); var FUN1 = new MYFUNC(); FUN1.THIS == FUN1") );

this.TestCase( SECTION,
"Anonymous Code: var MYFUNC = new Function('this.THIS = eval(\"this\")'); ((new MYFUNC().THIS).toString()",
"[object Object]",
eval("var MYFUNC = new Function('this.THIS = eval(\"this\")'); ((new MYFUNC()).THIS).toString()") );

this.TestCase( SECTION,
"Anonymous Code: var MYFUNC = new Function('this.THIS = eval(\"this\")'); var FUN1 = new MYFUNC(); FUN1.THIS == FUN1",
true,
eval("var MYFUNC = new Function('this.THIS = eval(\"this\")'); var FUN1 = new MYFUNC(); FUN1.THIS == FUN1") );

// this and eval(this) in function code called as a function should return the global object.
this.TestCase( SECTION,
"Function Code:  ReturnThis()",
GLOBAL_OBJECT,
ReturnThis() );

this.TestCase( SECTION,
"Function Code:  ReturnEvalThis()",
GLOBAL_OBJECT,
ReturnEvalThis() );

//  this and eval(this) in function code called as a contructor should return the object.
this.TestCase( SECTION,
"var MYOBJECT = new ReturnThis(); MYOBJECT.toString()",
"[object Object]",
eval("var MYOBJECT = new ReturnThis(); MYOBJECT.toString()") );

this.TestCase( SECTION,
"var MYOBJECT = new ReturnEvalThis(); MYOBJECT.toString()",
"[object Object]",
eval("var MYOBJECT = new ReturnEvalThis(); MYOBJECT.toString()") );

//test();

function ReturnThis() {
return this.toString();
}

function ReturnEvalThis() {
return( eval("this.toString()") );
}

},

/**
File Name:          11.10-1.js
ECMA Section:       11.10-1 Binary Bitwise Operators:  &
Description:
Semantics

The production A : A @ B, where @ is one of the bitwise operators in the
productions &, ^, | , is evaluated as follows:

1.  Evaluate A.
2.  Call GetValue(Result(1)).
3.  Evaluate B.
4.  Call GetValue(Result(3)).
5.  Call ToInt32(Result(2)).
6.  Call ToInt32(Result(4)).
7.  Apply the bitwise operator @ to Result(5) and Result(6). The result is
a signed 32 bit integer.
8.  Return Result(7).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_10__1: function() {
var SECTION = "11.10-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Binary Bitwise Operators:  &");

var shiftexp = 0;
var addexp = 0;

//    for ( shiftpow = 0; shiftpow < 33; shiftpow++ ) {
for ( shiftpow = 0; shiftpow < 1; shiftpow++ ) {
shiftexp += Math.pow( 2, shiftpow );

for ( addpow = 0; addpow < 33; addpow++ ) {
addexp += Math.pow(2, addpow);

this.TestCase( SECTION,
shiftexp + " & " + addexp,
And( shiftexp, addexp ),
shiftexp & addexp );
}
}

//test();

function ToInteger( n ) {
n = Number( n );
var sign = ( n < 0 ) ? -1 : 1;

if ( n != n ) {
return 0;
}
if ( Math.abs( n ) == 0 || Math.abs( n ) == Number.POSITIVE_INFINITY ) {
return n;
}
return ( sign * Math.floor(Math.abs(n)) );
}
function ToInt32( n ) {
n = Number( n );
var sign = ( n < 0 ) ? -1 : 1;

if ( Math.abs( n ) == 0 || Math.abs( n ) == Number.POSITIVE_INFINITY) {
return 0;
}

n = (sign * Math.floor( Math.abs(n) )) % Math.pow(2,32);
n = ( n >= Math.pow(2,31) ) ? n - Math.pow(2,32) : n;

return ( n );
}
function ToUint32( n ) {
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
function ToUint16( n ) {
var sign = ( n < 0 ) ? -1 : 1;

if ( Math.abs( n ) == 0 || Math.abs( n ) == Number.POSITIVE_INFINITY) {
return 0;
}

n = ( sign * Math.floor( Math.abs(n) ) ) % Math.pow(2,16);

if (n <0) {
n += Math.pow(2,16);
}

return ( n );
}
function Mask( b, n ) {
b = ToUint32BitString( b );
b = b.substring( b.length - n );
b = ToUint32Decimal( b );
return ( b );
}
function ToUint32BitString( n ) {
var b = "";
for ( p = 31; p >=0; p-- ) {
if ( n >= Math.pow(2,p) ) {
b += "1";
n -= Math.pow(2,p);
} else {
b += "0";
}
}
return b;
}
function ToInt32BitString( n ) {
var b = "";
var sign = ( n < 0 ) ? -1 : 1;

b += ( sign == 1 ) ? "0" : "1";

for ( p = 30; p >=0; p-- ) {
if ( (sign == 1 ) ? sign * n >= Math.pow(2,p) : sign * n > Math.pow(2,p) ) {
b += ( sign == 1 ) ? "1" : "0";
n -= sign * Math.pow( 2, p );
} else {
b += ( sign == 1 ) ? "0" : "1";
}
}

return b;
}
function ToInt32Decimal( bin ) {
var r = 0;
var sign;

if ( Number(bin.charAt(0)) == 0 ) {
sign = 1;
r = 0;
} else {
sign = -1;
r = -(Math.pow(2,31));
}

for ( var j = 0; j < 31; j++ ) {
r += Math.pow( 2, j ) * Number(bin.charAt(31-j));
}

return r;
}
function ToUint32Decimal( bin ) {
var r = 0;


for ( l = bin.length; l < 32; l++ ) {
bin = "0" + bin;
}

for ( j = 0; j < 31; j++ ) {
r += Math.pow( 2, j ) * Number(bin.charAt(31-j));

}

return r;
}
function And( s, a ) {
s = ToInt32( s );
a = ToInt32( a );

var bs = ToInt32BitString( s );
var ba = ToInt32BitString( a );

var result = "";

for ( var bit = 0; bit < bs.length; bit++ ) {
if ( bs.charAt(bit) == "1" && ba.charAt(bit) == "1" ) {
result += "1";
} else {
result += "0";
}
}
return ToInt32Decimal(result);
}
function Xor( s, a ) {
s = ToInt32( s );
a = ToInt32( a );

var bs = ToInt32BitString( s );
var ba = ToInt32BitString( a );

var result = "";

for ( var bit = 0; bit < bs.length; bit++ ) {
if ( (bs.charAt(bit) == "1" && ba.charAt(bit) == "0") ||
(bs.charAt(bit) == "0" && ba.charAt(bit) == "1")
) {
result += "1";
} else {
result += "0";
}
}

return ToInt32Decimal(result);
}
function Or( s, a ) {
s = ToInt32( s );
a = ToInt32( a );

var bs = ToInt32BitString( s );
var ba = ToInt32BitString( a );

var result = "";

for ( var bit = 0; bit < bs.length; bit++ ) {
if ( bs.charAt(bit) == "1" || ba.charAt(bit) == "1" ) {
result += "1";
} else {
result += "0";
}
}

return ToInt32Decimal(result);
}

},

/**
File Name:          11.10-2.js
ECMA Section:       11.10-2 Binary Bitwise Operators:  |
Description:
Semantics

The production A : A @ B, where @ is one of the bitwise operators in the
productions &, ^, | , is evaluated as follows:

1.  Evaluate A.
2.  Call GetValue(Result(1)).
3.  Evaluate B.
4.  Call GetValue(Result(3)).
5.  Call ToInt32(Result(2)).
6.  Call ToInt32(Result(4)).
7.  Apply the bitwise operator @ to Result(5) and Result(6). The result is
a signed 32 bit integer.
8.  Return Result(7).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_10__2: function() {
var SECTION = "11.10-2";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Binary Bitwise Operators:  |");

var shiftexp = 0;
var addexp = 0;

for ( shiftpow = 0; shiftpow < 33; shiftpow++ ) {
shiftexp += Math.pow( 2, shiftpow );

for ( addpow = 0; addpow < 33; addpow++ ) {
addexp += Math.pow(2, addpow);

this.TestCase( SECTION,
shiftexp + " | " + addexp,
Or( shiftexp, addexp ),
shiftexp | addexp );
}
}

//test();

function ToInteger( n ) {
n = Number( n );
var sign = ( n < 0 ) ? -1 : 1;

if ( n != n ) {
return 0;
}
if ( Math.abs( n ) == 0 || Math.abs( n ) == Number.POSITIVE_INFINITY ) {
return n;
}
return ( sign * Math.floor(Math.abs(n)) );
}
function ToInt32( n ) {
n = Number( n );
var sign = ( n < 0 ) ? -1 : 1;

if ( Math.abs( n ) == 0 || Math.abs( n ) == Number.POSITIVE_INFINITY) {
return 0;
}

n = (sign * Math.floor( Math.abs(n) )) % Math.pow(2,32);
n = ( n >= Math.pow(2,31) ) ? n - Math.pow(2,32) : n;

return ( n );
}
function ToUint32( n ) {
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
function ToUint16( n ) {
var sign = ( n < 0 ) ? -1 : 1;

if ( Math.abs( n ) == 0 || Math.abs( n ) == Number.POSITIVE_INFINITY) {
return 0;
}

n = ( sign * Math.floor( Math.abs(n) ) ) % Math.pow(2,16);

if (n <0) {
n += Math.pow(2,16);
}

return ( n );
}
function Mask( b, n ) {
b = ToUint32BitString( b );
b = b.substring( b.length - n );
b = ToUint32Decimal( b );
return ( b );
}
function ToUint32BitString( n ) {
var b = "";
for ( p = 31; p >=0; p-- ) {
if ( n >= Math.pow(2,p) ) {
b += "1";
n -= Math.pow(2,p);
} else {
b += "0";
}
}
return b;
}
function ToInt32BitString( n ) {
var b = "";
var sign = ( n < 0 ) ? -1 : 1;

b += ( sign == 1 ) ? "0" : "1";

for ( p = 30; p >=0; p-- ) {
if ( (sign == 1 ) ? sign * n >= Math.pow(2,p) : sign * n > Math.pow(2,p) ) {
b += ( sign == 1 ) ? "1" : "0";
n -= sign * Math.pow( 2, p );
} else {
b += ( sign == 1 ) ? "0" : "1";
}
}

return b;
}
function ToInt32Decimal( bin ) {
var r = 0;
var sign;

if ( Number(bin.charAt(0)) == 0 ) {
sign = 1;
r = 0;
} else {
sign = -1;
r = -(Math.pow(2,31));
}

for ( var j = 0; j < 31; j++ ) {
r += Math.pow( 2, j ) * Number(bin.charAt(31-j));
}

return r;
}
function ToUint32Decimal( bin ) {
var r = 0;


for ( l = bin.length; l < 32; l++ ) {
bin = "0" + bin;
}

for ( j = 0; j < 31; j++ ) {
r += Math.pow( 2, j ) * Number(bin.charAt(31-j));

}

return r;
}
function And( s, a ) {
s = ToInt32( s );
a = ToInt32( a );

var bs = ToInt32BitString( s );
var ba = ToInt32BitString( a );

var result = "";

for ( var bit = 0; bit < bs.length; bit++ ) {
if ( bs.charAt(bit) == "1" && ba.charAt(bit) == "1" ) {
result += "1";
} else {
result += "0";
}
}
return ToInt32Decimal(result);
}
function Xor( s, a ) {
s = ToInt32( s );
a = ToInt32( a );

var bs = ToInt32BitString( s );
var ba = ToInt32BitString( a );

var result = "";

for ( var bit = 0; bit < bs.length; bit++ ) {
if ( (bs.charAt(bit) == "1" && ba.charAt(bit) == "0") ||
(bs.charAt(bit) == "0" && ba.charAt(bit) == "1")
) {
result += "1";
} else {
result += "0";
}
}

return ToInt32Decimal(result);
}
function Or( s, a ) {
s = ToInt32( s );
a = ToInt32( a );

var bs = ToInt32BitString( s );
var ba = ToInt32BitString( a );

var result = "";

for ( var bit = 0; bit < bs.length; bit++ ) {
if ( bs.charAt(bit) == "1" || ba.charAt(bit) == "1" ) {
result += "1";
} else {
result += "0";
}
}

return ToInt32Decimal(result);
}

},

/**
File Name:          11.10-3.js
ECMA Section:       11.10-3 Binary Bitwise Operators:  ^
Description:
Semantics

The production A : A @ B, where @ is one of the bitwise operators in the
productions &, ^, | , is evaluated as follows:

1.  Evaluate A.
2.  Call GetValue(Result(1)).
3.  Evaluate B.
4.  Call GetValue(Result(3)).
5.  Call ToInt32(Result(2)).
6.  Call ToInt32(Result(4)).
7.  Apply the bitwise operator @ to Result(5) and Result(6). The result is
a signed 32 bit integer.
8.  Return Result(7).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_10__3: function() {
var SECTION = "11.10-3";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Binary Bitwise Operators:  ^");

var shiftexp = 0;
var addexp = 0;

for ( shiftpow = 0; shiftpow < 33; shiftpow++ ) {
shiftexp += Math.pow( 2, shiftpow );

for ( addpow = 0; addpow < 33; addpow++ ) {
addexp += Math.pow(2, addpow);

this.TestCase( SECTION,
shiftexp + " ^ " + addexp,
Xor( shiftexp, addexp ),
shiftexp ^ addexp );
}
}

//test();

function ToInteger( n ) {
n = Number( n );
var sign = ( n < 0 ) ? -1 : 1;

if ( n != n ) {
return 0;
}
if ( Math.abs( n ) == 0 || Math.abs( n ) == Number.POSITIVE_INFINITY ) {
return n;
}
return ( sign * Math.floor(Math.abs(n)) );
}
function ToInt32( n ) {
n = Number( n );
var sign = ( n < 0 ) ? -1 : 1;

if ( Math.abs( n ) == 0 || Math.abs( n ) == Number.POSITIVE_INFINITY) {
return 0;
}

n = (sign * Math.floor( Math.abs(n) )) % Math.pow(2,32);
n = ( n >= Math.pow(2,31) ) ? n - Math.pow(2,32) : n;

return ( n );
}
function ToUint32( n ) {
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
function ToUint16( n ) {
var sign = ( n < 0 ) ? -1 : 1;

if ( Math.abs( n ) == 0 || Math.abs( n ) == Number.POSITIVE_INFINITY) {
return 0;
}

n = ( sign * Math.floor( Math.abs(n) ) ) % Math.pow(2,16);

if (n <0) {
n += Math.pow(2,16);
}

return ( n );
}
function Mask( b, n ) {
b = ToUint32BitString( b );
b = b.substring( b.length - n );
b = ToUint32Decimal( b );
return ( b );
}
function ToUint32BitString( n ) {
var b = "";
for ( p = 31; p >=0; p-- ) {
if ( n >= Math.pow(2,p) ) {
b += "1";
n -= Math.pow(2,p);
} else {
b += "0";
}
}
return b;
}
function ToInt32BitString( n ) {
var b = "";
var sign = ( n < 0 ) ? -1 : 1;

b += ( sign == 1 ) ? "0" : "1";

for ( p = 30; p >=0; p-- ) {
if ( (sign == 1 ) ? sign * n >= Math.pow(2,p) : sign * n > Math.pow(2,p) ) {
b += ( sign == 1 ) ? "1" : "0";
n -= sign * Math.pow( 2, p );
} else {
b += ( sign == 1 ) ? "0" : "1";
}
}

return b;
}
function ToInt32Decimal( bin ) {
var r = 0;
var sign;

if ( Number(bin.charAt(0)) == 0 ) {
sign = 1;
r = 0;
} else {
sign = -1;
r = -(Math.pow(2,31));
}

for ( var j = 0; j < 31; j++ ) {
r += Math.pow( 2, j ) * Number(bin.charAt(31-j));
}

return r;
}
function ToUint32Decimal( bin ) {
var r = 0;

for ( l = bin.length; l < 32; l++ ) {
bin = "0" + bin;
}

for ( j = 0; j < 31; j++ ) {
r += Math.pow( 2, j ) * Number(bin.charAt(31-j));

}

return r;
}
function And( s, a ) {
s = ToInt32( s );
a = ToInt32( a );

var bs = ToInt32BitString( s );
var ba = ToInt32BitString( a );

var result = "";

for ( var bit = 0; bit < bs.length; bit++ ) {
if ( bs.charAt(bit) == "1" && ba.charAt(bit) == "1" ) {
result += "1";
} else {
result += "0";
}
}
return ToInt32Decimal(result);
}
function Xor( s, a ) {
s = ToInt32( s );
a = ToInt32( a );

var bs = ToInt32BitString( s );
var ba = ToInt32BitString( a );

var result = "";

for ( var bit = 0; bit < bs.length; bit++ ) {
if ( (bs.charAt(bit) == "1" && ba.charAt(bit) == "0") ||
(bs.charAt(bit) == "0" && ba.charAt(bit) == "1")
) {
result += "1";
} else {
result += "0";
}
}

return ToInt32Decimal(result);
}
function Or( s, a ) {
s = ToInt32( s );
a = ToInt32( a );

var bs = ToInt32BitString( s );
var ba = ToInt32BitString( a );

var result = "";

for ( var bit = 0; bit < bs.length; bit++ ) {
if ( bs.charAt(bit) == "1" || ba.charAt(bit) == "1" ) {
result += "1";
} else {
result += "0";
}
}

return ToInt32Decimal(result);
}

},

/**
File Name:          11.12.js
ECMA Section:       11.12 Conditional Operator
Description:
Logi

calORExpression ? AssignmentExpression : AssignmentExpression

Semantics

The production ConditionalExpression :
LogicalORExpression ? AssignmentExpression : AssignmentExpression
is evaluated as follows:

1.  Evaluate LogicalORExpression.
2.  Call GetValue(Result(1)).
3.  Call ToBoolean(Result(2)).
4.  If Result(3) is false, go to step 8.
5.  Evaluate the first AssignmentExpression.
6.  Call GetValue(Result(5)).
7.  Return Result(6).
8.  Evaluate the second AssignmentExpression.
9.  Call GetValue(Result(8)).
10.  Return Result(9).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_12_1: function() {
var SECTION = "11.12";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Conditional operator( ? : )");

this.TestCase( SECTION,
"true ? 'PASSED' : 'FAILED'",
"PASSED",
(true?"PASSED":"FAILED"));

this.TestCase( SECTION,
"false ? 'FAILED' : 'PASSED'",
"PASSED",
(false?"FAILED":"PASSED"));

this.TestCase( SECTION,
"1 ? 'PASSED' : 'FAILED'",
"PASSED",
(true?"PASSED":"FAILED"));

this.TestCase( SECTION,
"0 ? 'FAILED' : 'PASSED'",
"PASSED",
(false?"FAILED":"PASSED"));

this.TestCase( SECTION,
"-1 ? 'PASSED' : 'FAILED'",
"PASSED",
(true?"PASSED":"FAILED"));

this.TestCase( SECTION,
"NaN ? 'FAILED' : 'PASSED'",
"PASSED",
(Number.NaN?"FAILED":"PASSED"));

this.TestCase( SECTION,
"var VAR = true ? , : 'FAILED'",
"PASSED",
(VAR = true ? "PASSED" : "FAILED") );

//test();

},

/**
File Name:          11.12-2-n.js
ECMA Section:       11.12
Description:

The grammar for a ConditionalExpression in ECMAScript is a little bit
different from that in C and Java, which each allow the second
subexpression to be an Expression but restrict the third expression to
be a ConditionalExpression.  The motivation for this difference in
ECMAScript is to allow an assignment expression to be governed by either
arm of a conditional and to eliminate the confusing and fairly useless
case of a comma expression as the center expression.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_12_2__n: function() {
var SECTION = "11.12-2-n";
var VERSION = "ECMA_1";
//startTest();
//writeHeaderToLog( SECTION + " Conditional operator ( ? : )");

// the following expression should be an error in JS.

DESCRIPTION = "var MYVAR =  true ? 'EXPR1', 'EXPR2' : 'EXPR3'; MYVAR";
EXPECTED = "error";

this.TestCase( SECTION,
"var MYVAR =  true ? 'EXPR1', 'EXPR2' : 'EXPR3'; MYVAR",
"error",
eval("var MYVAR =  true ? 'EXPR1', 'EXPR2' : 'EXPR3'; MYVAR") );

//test();

},

/**
File Name:          11.12-2-n.js
ECMA Section:       11.12
Description:

The grammar for a ConditionalExpression in ECMAScript is a little bit
different from that in C and Java, which each allow the second
subexpression to be an Expression but restrict the third expression to
be a ConditionalExpression.  The motivation for this difference in
ECMAScript is to allow an assignment expression to be governed by either
arm of a conditional and to eliminate the confusing and fairly useless
case of a comma expression as the center expression.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_12_2__n_WORKS: function() {
var SECTION = "11.12-2-n";
var VERSION = "ECMA_1";
//startTest();
//writeHeaderToLog( SECTION + " Conditional operator ( ? : )");

// the following expression should be an error in JS.

DESCRIPTION = "var MYVAR =  true ? 'EXPR1', 'EXPR2' : 'EXPR3'; MYVAR";
EXPECTED = "error";

try {
this.TestCase( SECTION,
"var MYVAR =  true ? 'EXPR1', 'EXPR2' : 'EXPR3'; MYVAR",
"error",
eval("var MYVAR =  true ? 'EXPR1', 'EXPR2' : 'EXPR3'; MYVAR") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing : in conditional expression');
}

//test();

},

/**
File Name:          11.12-3.js
ECMA Section:       11.12
Description:

The grammar for a ConditionalExpression in ECMAScript is a little bit
different from that in C and Java, which each allow the second
subexpression to be an Expression but restrict the third expression to
be a ConditionalExpression.  The motivation for this difference in
ECMAScript is to allow an assignment expression to be governed by either
arm of a conditional and to eliminate the confusing and fairly useless
case of a comma expression as the center expression.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_12__3: function() {
var SECTION = "11.12-3";
var VERSION = "ECMA_1";
//startTest();
//writeHeaderToLog( SECTION + " Conditional operator ( ? : )");

// the following expression should NOT be an error in JS.

this.TestCase( SECTION,
"var MYVAR =  true ? ('FAIL1', 'PASSED') : 'FAIL2'; MYVAR",
"PASSED",
eval("var MYVAR =  true ? ('FAIL1', 'PASSED') : 'FAIL2'; MYVAR"));

//test();

},

/**
File Name:          11.12-4.js
ECMA Section:       11.12
Description:

The grammar for a ConditionalExpression in ECMAScript is a little bit
different from that in C and Java, which each allow the second
subexpression to be an Expression but restrict the third expression to
be a ConditionalExpression.  The motivation for this difference in
ECMAScript is to allow an assignment expression to be governed by either
arm of a conditional and to eliminate the confusing and fairly useless
case of a comma expression as the center expression.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_12__4: function() {
var SECTION = "11.12-4";
var VERSION = "ECMA_1";
//startTest();
//writeHeaderToLog( SECTION + " Conditional operator ( ? : )");

// the following expression should NOT be an error in JS.

this.TestCase( SECTION,
"true ? MYVAR1 = 'PASSED' : MYVAR1 = 'FAILED'; MYVAR1",
"PASSED",
eval("true ? MYVAR1 = 'PASSED' : MYVAR1 = 'FAILED'; MYVAR1") );

//test();

},

/**
File Name:          11.13.1.js
ECMA Section:       11.13.1 Simple assignment
Description:

11.13.1 Simple Assignment ( = )

The production AssignmentExpression :
LeftHandSideExpression = AssignmentExpression is evaluated as follows:

1.  Evaluate LeftHandSideExpression.
2.  Evaluate AssignmentExpression.
3.  Call GetValue(Result(2)).
4.  Call PutValue(Result(1), Result(3)).
5.  Return Result(3).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_13_1: function() {
var SECTION = "11.13.1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Simple Assignment ( = )");

this.TestCase( SECTION,
"SOMEVAR = true",
true,
SOMEVAR = true );

//test();

},

/**
File Name:          11.13.2-1.js
ECMA Section:       11.13.2 Compound Assignment: *=
Description:

*= /= %= += -= <<= >>= >>>= &= ^= |=

11.13.2 Compound assignment ( op= )

The production AssignmentExpression :
LeftHandSideExpression @ = AssignmentExpression, where @ represents one of
the operators indicated above, is evaluated as follows:

1.  Evaluate LeftHandSideExpression.
2.  Call GetValue(Result(1)).
3.  Evaluate AssignmentExpression.
4.  Call GetValue(Result(3)).
5.  Apply operator @ to Result(2) and Result(4).
6.  Call PutValue(Result(1), Result(5)).
7.  Return Result(5).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_13_2__1: function() {
var SECTION = "11.13.2-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Compound Assignment: *=");


// NaN cases

this.TestCase( SECTION,
"VAR1 = NaN; VAR2=1; VAR1 *= VAR2",
Number.NaN,
eval("VAR1 = Number.NaN; VAR2=1; VAR1 *= VAR2") );

this.TestCase( SECTION,
"VAR1 = NaN; VAR2=1; VAR1 *= VAR2; VAR1",
Number.NaN,
eval("VAR1 = Number.NaN; VAR2=1; VAR1 *= VAR2; VAR1") );

// number cases
this.TestCase( SECTION,
"VAR1 = 0; VAR2=1; VAR1 *= VAR2",
0,
eval("VAR1 = 0; VAR2=1; VAR1 *= VAR2") );

this.TestCase( SECTION,
"VAR1 = 0; VAR2=1; VAR1 *= VAR2;VAR1",
0,
eval("VAR1 = 0; VAR2=1; VAR1 *= VAR2;VAR1") );

this.TestCase( SECTION,
"VAR1 = 0xFF; VAR2 = 0xA, VAR1 *= VAR2",
2550,
eval("VAR1 = 0XFF; VAR2 = 0XA, VAR1 *= VAR2") );

// special multiplication cases

this.TestCase( SECTION,
"VAR1 = 0; VAR2= Infinity; VAR1 *= VAR2",
Number.NaN,
eval("VAR1 = 0; VAR2 = Number.POSITIVE_INFINITY; VAR1 *= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = -0; VAR2= Infinity; VAR1 *= VAR2",
Number.NaN,
eval("VAR1 = -0; VAR2 = Number.POSITIVE_INFINITY; VAR1 *= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = -0; VAR2= -Infinity; VAR1 *= VAR2",
Number.NaN,
eval("VAR1 = -0; VAR2 = Number.NEGATIVE_INFINITY; VAR1 *= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = 0; VAR2= -Infinity; VAR1 *= VAR2",
Number.NaN,
eval("VAR1 = 0; VAR2 = Number.NEGATIVE_INFINITY; VAR1 *= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = 0; VAR2= Infinity; VAR2 *= VAR1",
Number.NaN,
eval("VAR1 = 0; VAR2 = Number.POSITIVE_INFINITY; VAR2 *= VAR1; VAR2") );

this.TestCase( SECTION,
"VAR1 = -0; VAR2= Infinity; VAR2 *= VAR1",
Number.NaN,
eval("VAR1 = -0; VAR2 = Number.POSITIVE_INFINITY; VAR2 *= VAR1; VAR2") );

this.TestCase( SECTION,
"VAR1 = -0; VAR2= -Infinity; VAR2 *= VAR1",
Number.NaN,
eval("VAR1 = -0; VAR2 = Number.NEGATIVE_INFINITY; VAR2 *= VAR1; VAR2") );

this.TestCase( SECTION,
"VAR1 = 0; VAR2= -Infinity; VAR2 *= VAR1",
Number.NaN,
eval("VAR1 = 0; VAR2 = Number.NEGATIVE_INFINITY; VAR2 *= VAR1; VAR2") );

this.TestCase( SECTION,
"VAR1 = Infinity; VAR2= Infinity; VAR1 *= VAR2",
Number.POSITIVE_INFINITY,
eval("VAR1 = Number.POSITIVE_INFINITY; VAR2 = Number.POSITIVE_INFINITY; VAR1 *= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = Infinity; VAR2= -Infinity; VAR1 *= VAR2",
Number.NEGATIVE_INFINITY,
eval("VAR1 = Number.POSITIVE_INFINITY; VAR2 = Number.NEGATIVE_INFINITY; VAR1 *= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 =-Infinity; VAR2= Infinity; VAR1 *= VAR2",
Number.NEGATIVE_INFINITY,
eval("VAR1 = Number.NEGATIVE_INFINITY; VAR2 = Number.POSITIVE_INFINITY; VAR1 *= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 =-Infinity; VAR2=-Infinity; VAR1 *= VAR2",
Number.POSITIVE_INFINITY,
eval("VAR1 = Number.NEGATIVE_INFINITY; VAR2 = Number.NEGATIVE_INFINITY; VAR1 *= VAR2; VAR1") );

// string cases
this.TestCase( SECTION,
"VAR1 = 10; VAR2 = '255', VAR1 *= VAR2",
2550,
eval("VAR1 = 10; VAR2 = '255', VAR1 *= VAR2") );

this.TestCase( SECTION,
"VAR1 = '255'; VAR2 = 10, VAR1 *= VAR2",
2550,
eval("VAR1 = '255'; VAR2 = 10, VAR1 *= VAR2") );

this.TestCase( SECTION,
"VAR1 = 10; VAR2 = '0XFF', VAR1 *= VAR2",
2550,
eval("VAR1 = 10; VAR2 = '0XFF', VAR1 *= VAR2") );

this.TestCase( SECTION,
"VAR1 = '0xFF'; VAR2 = 0xA, VAR1 *= VAR2",
2550,
eval("VAR1 = '0XFF'; VAR2 = 0XA, VAR1 *= VAR2") );

this.TestCase( SECTION,
"VAR1 = '10'; VAR2 = '255', VAR1 *= VAR2",
2550,
eval("VAR1 = '10'; VAR2 = '255', VAR1 *= VAR2") );

this.TestCase( SECTION,
"VAR1 = '10'; VAR2 = '0XFF', VAR1 *= VAR2",
2550,
eval("VAR1 = '10'; VAR2 = '0XFF', VAR1 *= VAR2") );

this.TestCase( SECTION,
"VAR1 = '0xFF'; VAR2 = 0xA, VAR1 *= VAR2",
2550,
eval("VAR1 = '0XFF'; VAR2 = 0XA, VAR1 *= VAR2") );

// boolean cases
this.TestCase( SECTION,
"VAR1 = true; VAR2 = false; VAR1 *= VAR2",
0,
eval("VAR1 = true; VAR2 = false; VAR1 *= VAR2") );

this.TestCase( SECTION,
"VAR1 = true; VAR2 = true; VAR1 *= VAR2",
1,
eval("VAR1 = true; VAR2 = true; VAR1 *= VAR2") );

// object cases
this.TestCase( SECTION,
"VAR1 = new Boolean(true); VAR2 = 10; VAR1 *= VAR2;VAR1",
10,
eval("VAR1 = new Boolean(true); VAR2 = 10; VAR1 *= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = new Number(11); VAR2 = 10; VAR1 *= VAR2; VAR1",
110,
eval("VAR1 = new Number(11); VAR2 = 10; VAR1 *= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = new Number(11); VAR2 = new Number(10); VAR1 *= VAR2",
110,
eval("VAR1 = new Number(11); VAR2 = new Number(10); VAR1 *= VAR2") );

this.TestCase( SECTION,
"VAR1 = new String('15'); VAR2 = new String('0xF'); VAR1 *= VAR2",
225,
eval("VAR1 = String('15'); VAR2 = new String('0xF'); VAR1 *= VAR2") );

//test();

},

/**
File Name:          11.13.2-2js
ECMA Section:       11.13.2 Compound Assignment: /=
Description:

*= /= %= += -= <<= >>= >>>= &= ^= |=

11.13.2 Compound assignment ( op= )

The production AssignmentExpression :
LeftHandSideExpression @ = AssignmentExpression, where @ represents one of
the operators indicated above, is evaluated as follows:

1.  Evaluate LeftHandSideExpression.
2.  Call GetValue(Result(1)).
3.  Evaluate AssignmentExpression.
4.  Call GetValue(Result(3)).
5.  Apply operator @ to Result(2) and Result(4).
6.  Call PutValue(Result(1), Result(5)).
7.  Return Result(5).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_13_2__2: function() {
var SECTION = "11.13.2-2";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Compound Assignment: /=");


// NaN cases

this.TestCase( SECTION,
"VAR1 = NaN; VAR2=1; VAR1 /= VAR2",
Number.NaN,
eval("VAR1 = Number.NaN; VAR2=1; VAR1 /= VAR2") );

this.TestCase( SECTION,
"VAR1 = NaN; VAR2=1; VAR1 /= VAR2; VAR1",
Number.NaN,
eval("VAR1 = Number.NaN; VAR2=1; VAR1 /= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = NaN; VAR2=0; VAR1 /= VAR2",
Number.NaN,
eval("VAR1 = Number.NaN; VAR2=0; VAR1 /= VAR2") );

this.TestCase( SECTION,
"VAR1 = NaN; VAR2=0; VAR1 /= VAR2; VAR1",
Number.NaN,
eval("VAR1 = Number.NaN; VAR2=0; VAR1 /= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = 0; VAR2=NaN; VAR1 /= VAR2",
Number.NaN,
eval("VAR1 = 0; VAR2=Number.NaN; VAR1 /= VAR2") );

this.TestCase( SECTION,
"VAR1 = 0; VAR2=NaN; VAR1 /= VAR2; VAR1",
Number.NaN,
eval("VAR1 = 0; VAR2=Number.NaN; VAR1 /= VAR2; VAR1") );

// number cases
this.TestCase( SECTION,
"VAR1 = 0; VAR2=1; VAR1 /= VAR2",
0,
eval("VAR1 = 0; VAR2=1; VAR1 /= VAR2") );

this.TestCase( SECTION,
"VAR1 = 0; VAR2=1; VAR1 /= VAR2;VAR1",
0,
eval("VAR1 = 0; VAR2=1; VAR1 /= VAR2;VAR1") );

this.TestCase( SECTION,
"VAR1 = 0xFF; VAR2 = 0xA, VAR1 /= VAR2",
25.5,
eval("VAR1 = 0XFF; VAR2 = 0XA, VAR1 /= VAR2") );

// special division cases

this.TestCase( SECTION,
"VAR1 = 0; VAR2= Infinity; VAR1 /= VAR2",
0,
eval("VAR1 = 0; VAR2 = Number.POSITIVE_INFINITY; VAR1 /= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = -0; VAR2= Infinity; VAR1 /= VAR2",
0,
eval("VAR1 = -0; VAR2 = Number.POSITIVE_INFINITY; VAR1 /= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = -0; VAR2= -Infinity; VAR1 /= VAR2",
0,
eval("VAR1 = -0; VAR2 = Number.NEGATIVE_INFINITY; VAR1 /= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = 0; VAR2= -Infinity; VAR1 /= VAR2",
0,
eval("VAR1 = 0; VAR2 = Number.NEGATIVE_INFINITY; VAR1 /= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = 0; VAR2= Infinity; VAR2 /= VAR1",
Number.POSITIVE_INFINITY,
eval("VAR1 = 0; VAR2 = Number.POSITIVE_INFINITY; VAR2 /= VAR1; VAR2") );

this.TestCase( SECTION,
"VAR1 = -0; VAR2= Infinity; VAR2 /= VAR1",
Number.NEGATIVE_INFINITY,
eval("VAR1 = -0; VAR2 = Number.POSITIVE_INFINITY; VAR2 /= VAR1; VAR2") );

this.TestCase( SECTION,
"VAR1 = -0; VAR2= -Infinity; VAR2 /= VAR1",
Number.POSITIVE_INFINITY,
eval("VAR1 = -0; VAR2 = Number.NEGATIVE_INFINITY; VAR2 /= VAR1; VAR2") );

this.TestCase( SECTION,
"VAR1 = 0; VAR2= -Infinity; VAR2 /= VAR1",
Number.NEGATIVE_INFINITY,
eval("VAR1 = 0; VAR2 = Number.NEGATIVE_INFINITY; VAR2 /= VAR1; VAR2") );

this.TestCase( SECTION,
"VAR1 = Infinity; VAR2= Infinity; VAR1 /= VAR2",
Number.NaN,
eval("VAR1 = Number.POSITIVE_INFINITY; VAR2 = Number.POSITIVE_INFINITY; VAR1 /= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = Infinity; VAR2= -Infinity; VAR1 /= VAR2",
Number.NaN,
eval("VAR1 = Number.POSITIVE_INFINITY; VAR2 = Number.NEGATIVE_INFINITY; VAR1 /= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 =-Infinity; VAR2= Infinity; VAR1 /= VAR2",
Number.NaN,
eval("VAR1 = Number.NEGATIVE_INFINITY; VAR2 = Number.POSITIVE_INFINITY; VAR1 /= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 =-Infinity; VAR2=-Infinity; VAR1 /= VAR2",
Number.NaN,
eval("VAR1 = Number.NEGATIVE_INFINITY; VAR2 = Number.NEGATIVE_INFINITY; VAR1 /= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = 0; VAR2= 0; VAR1 /= VAR2",
Number.NaN,
eval("VAR1 = 0; VAR2 = 0; VAR1 /= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = 0; VAR2= -0; VAR1 /= VAR2",
Number.NaN,
eval("VAR1 = 0; VAR2 = -0; VAR1 /= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = -0; VAR2= 0; VAR1 /= VAR2",
Number.NaN,
eval("VAR1 = -0; VAR2 = 0; VAR1 /= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = -0; VAR2= -0; VAR1 /= VAR2",
Number.NaN,
eval("VAR1 = -0; VAR2 = -0; VAR1 /= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = 1; VAR2= 0; VAR1 /= VAR2",
Number.POSITIVE_INFINITY,
eval("VAR1 = 1; VAR2 = 0; VAR1 /= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = 1; VAR2= -0; VAR1 /= VAR2",
Number.NEGATIVE_INFINITY,
eval("VAR1 = 1; VAR2 = -0; VAR1 /= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = -1; VAR2= 0; VAR1 /= VAR2",
Number.NEGATIVE_INFINITY,
eval("VAR1 = -1; VAR2 = 0; VAR1 /= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = -1; VAR2= -0; VAR1 /= VAR2",
Number.POSITIVE_INFINITY,
eval("VAR1 = -1; VAR2 = -0; VAR1 /= VAR2; VAR1") );

// string cases
this.TestCase( SECTION,
"VAR1 = 1000; VAR2 = '10', VAR1 /= VAR2; VAR1",
100,
eval("VAR1 = 1000; VAR2 = '10', VAR1 /= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = '1000'; VAR2 = 10, VAR1 /= VAR2; VAR1",
100,
eval("VAR1 = '1000'; VAR2 = 10, VAR1 /= VAR2; VAR1") );
/*
new TestCase( SECTION,    "VAR1 = 10; VAR2 = '0XFF', VAR1 /= VAR2", 2550,       eval("VAR1 = 10; VAR2 = '0XFF', VAR1 /= VAR2") );
new TestCase( SECTION,    "VAR1 = '0xFF'; VAR2 = 0xA, VAR1 /= VAR2", 2550,      eval("VAR1 = '0XFF'; VAR2 = 0XA, VAR1 /= VAR2") );

new TestCase( SECTION,    "VAR1 = '10'; VAR2 = '255', VAR1 /= VAR2", 2550,      eval("VAR1 = '10'; VAR2 = '255', VAR1 /= VAR2") );
new TestCase( SECTION,    "VAR1 = '10'; VAR2 = '0XFF', VAR1 /= VAR2", 2550,     eval("VAR1 = '10'; VAR2 = '0XFF', VAR1 /= VAR2") );
new TestCase( SECTION,    "VAR1 = '0xFF'; VAR2 = 0xA, VAR1 /= VAR2", 2550,      eval("VAR1 = '0XFF'; VAR2 = 0XA, VAR1 /= VAR2") );

// boolean cases
new TestCase( SECTION,    "VAR1 = true; VAR2 = false; VAR1 /= VAR2",    0,      eval("VAR1 = true; VAR2 = false; VAR1 /= VAR2") );
new TestCase( SECTION,    "VAR1 = true; VAR2 = true; VAR1 /= VAR2",    1,      eval("VAR1 = true; VAR2 = true; VAR1 /= VAR2") );

// object cases
new TestCase( SECTION,    "VAR1 = new Boolean(true); VAR2 = 10; VAR1 /= VAR2;VAR1",    10,      eval("VAR1 = new Boolean(true); VAR2 = 10; VAR1 /= VAR2; VAR1") );
new TestCase( SECTION,    "VAR1 = new Number(11); VAR2 = 10; VAR1 /= VAR2; VAR1",    110,      eval("VAR1 = new Number(11); VAR2 = 10; VAR1 /= VAR2; VAR1") );
new TestCase( SECTION,    "VAR1 = new Number(11); VAR2 = new Number(10); VAR1 /= VAR2",    110,      eval("VAR1 = new Number(11); VAR2 = new Number(10); VAR1 /= VAR2") );
new TestCase( SECTION,    "VAR1 = new String('15'); VAR2 = new String('0xF'); VAR1 /= VAR2",    255,      eval("VAR1 = String('15'); VAR2 = new String('0xF'); VAR1 /= VAR2") );

*/

//test();

},

/**
File Name:          11.13.2-4.js
ECMA Section:       11.13.2 Compound Assignment: %=
Description:

*= /= %= += -= <<= >>= >>>= &= ^= |=

11.13.2 Compound assignment ( op= )

The production AssignmentExpression :
LeftHandSideExpression @ = AssignmentExpression, where @ represents one of
the operators indicated above, is evaluated as follows:

1.  Evaluate LeftHandSideExpression.
2.  Call GetValue(Result(1)).
3.  Evaluate AssignmentExpression.
4.  Call GetValue(Result(3)).
5.  Apply operator @ to Result(2) and Result(4).
6.  Call PutValue(Result(1), Result(5)).
7.  Return Result(5).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_13_2__3: function() {
var SECTION = "11.13.2-3";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Compound Assignment: +=");

// If either operand is NaN,  result is NaN

this.TestCase( SECTION,
"VAR1 = NaN; VAR2=1; VAR1 %= VAR2",
Number.NaN,
eval("VAR1 = Number.NaN; VAR2=1; VAR1 %= VAR2") );

this.TestCase( SECTION,
"VAR1 = NaN; VAR2=1; VAR1 %= VAR2; VAR1",
Number.NaN,
eval("VAR1 = Number.NaN; VAR2=1; VAR1 %= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = NaN; VAR2=0; VAR1 %= VAR2",
Number.NaN,
eval("VAR1 = Number.NaN; VAR2=0; VAR1 %= VAR2") );

this.TestCase( SECTION,
"VAR1 = NaN; VAR2=0; VAR1 %= VAR2; VAR1",
Number.NaN,
eval("VAR1 = Number.NaN; VAR2=0; VAR1 %= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = 0; VAR2=NaN; VAR1 %= VAR2",
Number.NaN,
eval("VAR1 = 0; VAR2=Number.NaN; VAR1 %= VAR2") );

this.TestCase( SECTION,
"VAR1 = 0; VAR2=NaN; VAR1 %= VAR2; VAR1",
Number.NaN,
eval("VAR1 = 0; VAR2=Number.NaN; VAR1 %= VAR2; VAR1") );

// if the dividend is infinity or the divisor is zero or both, the result is NaN

this.TestCase( SECTION,
"VAR1 = Infinity; VAR2= Infinity; VAR1 %= VAR2; VAR1",
Number.NaN,
eval("VAR1 = Number.POSITIVE_INFINITY; VAR2 = Number.POSITIVE_INFINITY; VAR1 %= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = Infinity; VAR2= -Infinity; VAR1 %= VAR2; VAR1",
Number.NaN,
eval("VAR1 = Number.POSITIVE_INFINITY; VAR2 = Number.NEGATIVE_INFINITY; VAR1 %= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 =-Infinity; VAR2= Infinity; VAR1 %= VAR2; VAR1",
Number.NaN,
eval("VAR1 = Number.NEGATIVE_INFINITY; VAR2 = Number.POSITIVE_INFINITY; VAR1 %= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 =-Infinity; VAR2=-Infinity; VAR1 %= VAR2; VAR1",
Number.NaN,
eval("VAR1 = Number.NEGATIVE_INFINITY; VAR2 = Number.NEGATIVE_INFINITY; VAR1 %= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = 0; VAR2= Infinity; VAR2 %= VAR1",
Number.NaN,
eval("VAR1 = 0; VAR2 = Number.POSITIVE_INFINITY; VAR2 %= VAR1; VAR2") );

this.TestCase( SECTION,
"VAR1 = -0; VAR2= Infinity; VAR2 %= VAR1",
Number.NaN,
eval("VAR1 = -0; VAR2 = Number.POSITIVE_INFINITY; VAR2 %= VAR1; VAR2") );

this.TestCase( SECTION,
"VAR1 = -0; VAR2= -Infinity; VAR2 %= VAR1",
Number.NaN,
eval("VAR1 = -0; VAR2 = Number.NEGATIVE_INFINITY; VAR2 %= VAR1; VAR2") );

this.TestCase( SECTION,
"VAR1 = 0; VAR2= -Infinity; VAR2 %= VAR1",
Number.NaN,
eval("VAR1 = 0; VAR2 = Number.NEGATIVE_INFINITY; VAR2 %= VAR1; VAR2") );

this.TestCase( SECTION,
"VAR1 = 1; VAR2= Infinity; VAR2 %= VAR1",
Number.NaN,
eval("VAR1 = 1; VAR2 = Number.POSITIVE_INFINITY; VAR2 %= VAR1; VAR2") );

this.TestCase( SECTION,
"VAR1 = -1; VAR2= Infinity; VAR2 %= VAR1",
Number.NaN,
eval("VAR1 = -1; VAR2 = Number.POSITIVE_INFINITY; VAR2 %= VAR1; VAR2") );

this.TestCase( SECTION,
"VAR1 = -1; VAR2= -Infinity; VAR2 %= VAR1",
Number.NaN,
eval("VAR1 = -1; VAR2 = Number.NEGATIVE_INFINITY; VAR2 %= VAR1; VAR2") );

this.TestCase( SECTION,
"VAR1 = 1; VAR2= -Infinity; VAR2 %= VAR1",
Number.NaN,
eval("VAR1 = 1; VAR2 = Number.NEGATIVE_INFINITY; VAR2 %= VAR1; VAR2") );

this.TestCase( SECTION,
"VAR1 = 0; VAR2= 0; VAR1 %= VAR2",
Number.NaN,
eval("VAR1 = 0; VAR2 = 0; VAR1 %= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = 0; VAR2= -0; VAR1 %= VAR2",
Number.NaN,
eval("VAR1 = 0; VAR2 = -0; VAR1 %= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = -0; VAR2= 0; VAR1 %= VAR2",
Number.NaN,
eval("VAR1 = -0; VAR2 = 0; VAR1 %= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = -0; VAR2= -0; VAR1 %= VAR2",
Number.NaN,
eval("VAR1 = -0; VAR2 = -0; VAR1 %= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = 1; VAR2= 0; VAR1 %= VAR2",
Number.NaN,
eval("VAR1 = 1; VAR2 = 0; VAR1 %= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = 1; VAR2= -0; VAR1 %= VAR2",
Number.NaN,
eval("VAR1 = 1; VAR2 = -0; VAR1 %= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = -1; VAR2= 0; VAR1 %= VAR2",
Number.NaN,
eval("VAR1 = -1; VAR2 = 0; VAR1 %= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = -1; VAR2= -0; VAR1 %= VAR2",
Number.NaN,
eval("VAR1 = -1; VAR2 = -0; VAR1 %= VAR2; VAR1") );

// if the dividend is finite and the divisor is an infinity, the result equals the dividend.

this.TestCase( SECTION,
"VAR1 = 0; VAR2= Infinity; VAR1 %= VAR2;VAR1",
0,
eval("VAR1 = 0; VAR2 = Number.POSITIVE_INFINITY; VAR1 %= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = -0; VAR2= Infinity; VAR1 %= VAR2;VAR1",
-0,
eval("VAR1 = -0; VAR2 = Number.POSITIVE_INFINITY; VAR1 %= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = -0; VAR2= -Infinity; VAR1 %= VAR2;VAR1",
-0,
eval("VAR1 = -0; VAR2 = Number.NEGATIVE_INFINITY; VAR1 %= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = 0; VAR2= -Infinity; VAR1 %= VAR2;VAR1",
0,
eval("VAR1 = 0; VAR2 = Number.NEGATIVE_INFINITY; VAR1 %= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = 1; VAR2= Infinity; VAR1 %= VAR2;VAR1",
1,
eval("VAR1 = 1; VAR2 = Number.POSITIVE_INFINITY; VAR1 %= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = -1; VAR2= Infinity; VAR1 %= VAR2;VAR1",
-1,
eval("VAR1 = -1; VAR2 = Number.POSITIVE_INFINITY; VAR1 %= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = -1; VAR2= -Infinity; VAR1 %= VAR2;VAR1",
-1,
eval("VAR1 = -1; VAR2 = Number.NEGATIVE_INFINITY; VAR1 %= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = 1; VAR2= -Infinity; VAR1 %= VAR2;VAR1",
1,
eval("VAR1 = 1; VAR2 = Number.NEGATIVE_INFINITY; VAR1 %= VAR2; VAR1") );

// if the dividend is a zero and the divisor is finite, the result is the same as the dividend

this.TestCase( SECTION,
"VAR1 = 0; VAR2= 1; VAR1 %= VAR2; VAR1",
0,
eval("VAR1 = 0; VAR2 = 1; VAR1 %= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = -0; VAR2= 1; VAR1 %= VAR2; VAR1",
-0,
eval("VAR1 = -0; VAR2 = 1; VAR1 %= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = -0; VAR2= -1; VAR1 %= VAR2; VAR1",
-0,
eval("VAR1 = -0; VAR2 = -1; VAR1 %= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = 0; VAR2= -1; VAR1 %= VAR2; VAR1",
0,
eval("VAR1 = 0; VAR2 = -1; VAR1 %= VAR2; VAR1") );

// string cases
this.TestCase( SECTION,
"VAR1 = 1000; VAR2 = '10', VAR1 %= VAR2; VAR1",
0,
eval("VAR1 = 1000; VAR2 = '10', VAR1 %= VAR2; VAR1") );

this.TestCase( SECTION,
"VAR1 = '1000'; VAR2 = 10, VAR1 %= VAR2; VAR1",
0,
eval("VAR1 = '1000'; VAR2 = 10, VAR1 %= VAR2; VAR1") );
/*
new TestCase( SECTION,    "VAR1 = 10; VAR2 = '0XFF', VAR1 %= VAR2", 2550,       eval("VAR1 = 10; VAR2 = '0XFF', VAR1 %= VAR2") );
new TestCase( SECTION,    "VAR1 = '0xFF'; VAR2 = 0xA, VAR1 %= VAR2", 2550,      eval("VAR1 = '0XFF'; VAR2 = 0XA, VAR1 %= VAR2") );

new TestCase( SECTION,    "VAR1 = '10'; VAR2 = '255', VAR1 %= VAR2", 2550,      eval("VAR1 = '10'; VAR2 = '255', VAR1 %= VAR2") );
new TestCase( SECTION,    "VAR1 = '10'; VAR2 = '0XFF', VAR1 %= VAR2", 2550,     eval("VAR1 = '10'; VAR2 = '0XFF', VAR1 %= VAR2") );
new TestCase( SECTION,    "VAR1 = '0xFF'; VAR2 = 0xA, VAR1 %= VAR2", 2550,      eval("VAR1 = '0XFF'; VAR2 = 0XA, VAR1 %= VAR2") );

// boolean cases
new TestCase( SECTION,    "VAR1 = true; VAR2 = false; VAR1 %= VAR2",    0,      eval("VAR1 = true; VAR2 = false; VAR1 %= VAR2") );
new TestCase( SECTION,    "VAR1 = true; VAR2 = true; VAR1 %= VAR2",    1,      eval("VAR1 = true; VAR2 = true; VAR1 %= VAR2") );

// object cases
new TestCase( SECTION,    "VAR1 = new Boolean(true); VAR2 = 10; VAR1 %= VAR2;VAR1",    10,      eval("VAR1 = new Boolean(true); VAR2 = 10; VAR1 %= VAR2; VAR1") );
new TestCase( SECTION,    "VAR1 = new Number(11); VAR2 = 10; VAR1 %= VAR2; VAR1",    110,      eval("VAR1 = new Number(11); VAR2 = 10; VAR1 %= VAR2; VAR1") );
new TestCase( SECTION,    "VAR1 = new Number(11); VAR2 = new Number(10); VAR1 %= VAR2",    110,      eval("VAR1 = new Number(11); VAR2 = new Number(10); VAR1 %= VAR2") );
new TestCase( SECTION,    "VAR1 = new String('15'); VAR2 = new String('0xF'); VAR1 %= VAR2",    255,      eval("VAR1 = String('15'); VAR2 = new String('0xF'); VAR1 %= VAR2") );

*/

//test();

},

/**
File Name:          11.13.2-4.js
ECMA Section:       11.13.2 Compound Assignment:+=
Description:

*= /= %= += -= <<= >>= >>>= &= ^= |=

11.13.2 Compound assignment ( op= )

The production AssignmentExpression :
LeftHandSideExpression @ = AssignmentExpression, where @ represents one of
the operators indicated above, is evaluated as follows:

1.  Evaluate LeftHandSideExpression.
2.  Call GetValue(Result(1)).
3.  Evaluate AssignmentExpression.
4.  Call GetValue(Result(3)).
5.  Apply operator @ to Result(2) and Result(4).
6.  Call PutValue(Result(1), Result(5)).
7.  Return Result(5).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_13_2__4: function() {
var SECTION = "11.13.2-4";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Compound Assignment: +=");

// If either operand is NaN,  result is NaN

this.TestCase( SECTION,    "VAR1 = NaN; VAR2=1; VAR1 += VAR2",       Number.NaN, eval("VAR1 = Number.NaN; VAR2=1; VAR1 += VAR2") );
this.TestCase( SECTION,    "VAR1 = NaN; VAR2=1; VAR1 += VAR2; VAR1", Number.NaN, eval("VAR1 = Number.NaN; VAR2=1; VAR1 += VAR2; VAR1") );
this.TestCase( SECTION,    "VAR1 = NaN; VAR2=0; VAR1 += VAR2",       Number.NaN, eval("VAR1 = Number.NaN; VAR2=0; VAR1 += VAR2") );
this.TestCase( SECTION,    "VAR1 = NaN; VAR2=0; VAR1 += VAR2; VAR1", Number.NaN, eval("VAR1 = Number.NaN; VAR2=0; VAR1 += VAR2; VAR1") );
this.TestCase( SECTION,    "VAR1 = 0; VAR2=NaN; VAR1 += VAR2",       Number.NaN, eval("VAR1 = 0; VAR2=Number.NaN; VAR1 += VAR2") );
this.TestCase( SECTION,    "VAR1 = 0; VAR2=NaN; VAR1 += VAR2; VAR1", Number.NaN, eval("VAR1 = 0; VAR2=Number.NaN; VAR1 += VAR2; VAR1") );

// the sum of two Infinities the same sign is the infinity of that sign
// the sum of two Infinities of opposite sign is NaN

this.TestCase( SECTION,    "VAR1 = Infinity; VAR2= Infinity; VAR1 += VAR2; VAR1",   Number.POSITIVE_INFINITY,        eval("VAR1 = Number.POSITIVE_INFINITY; VAR2 = Number.POSITIVE_INFINITY; VAR1 += VAR2; VAR1") );
this.TestCase( SECTION,    "VAR1 = Infinity; VAR2= -Infinity; VAR1 += VAR2; VAR1",  Number.NaN,                      eval("VAR1 = Number.POSITIVE_INFINITY; VAR2 = Number.NEGATIVE_INFINITY; VAR1 += VAR2; VAR1") );
this.TestCase( SECTION,    "VAR1 =-Infinity; VAR2= Infinity; VAR1 += VAR2; VAR1",   Number.NaN,                      eval("VAR1 = Number.NEGATIVE_INFINITY; VAR2 = Number.POSITIVE_INFINITY; VAR1 += VAR2; VAR1") );
this.TestCase( SECTION,    "VAR1 =-Infinity; VAR2=-Infinity; VAR1 += VAR2; VAR1",   Number.NEGATIVE_INFINITY,        eval("VAR1 = Number.NEGATIVE_INFINITY; VAR2 = Number.NEGATIVE_INFINITY; VAR1 += VAR2; VAR1") );

// the sum of an infinity and a finite value is equal to the infinite operand

this.TestCase( SECTION,    "VAR1 = 0; VAR2= Infinity; VAR1 += VAR2;VAR1",    Number.POSITIVE_INFINITY,      eval("VAR1 = 0; VAR2 = Number.POSITIVE_INFINITY; VAR1 += VAR2; VAR1") );
this.TestCase( SECTION,    "VAR1 = -0; VAR2= Infinity; VAR1 += VAR2;VAR1",   Number.POSITIVE_INFINITY,      eval("VAR1 = -0; VAR2 = Number.POSITIVE_INFINITY; VAR1 += VAR2; VAR1") );
this.TestCase( SECTION,    "VAR1 = -0; VAR2= -Infinity; VAR1 += VAR2;VAR1",  Number.NEGATIVE_INFINITY,      eval("VAR1 = -0; VAR2 = Number.NEGATIVE_INFINITY; VAR1 += VAR2; VAR1") );
this.TestCase( SECTION,    "VAR1 = 0; VAR2= -Infinity; VAR1 += VAR2;VAR1",   Number.NEGATIVE_INFINITY,      eval("VAR1 = 0; VAR2 = Number.NEGATIVE_INFINITY; VAR1 += VAR2; VAR1") );

// the sum of two negative zeros is -0. the sum of two positive zeros, or of two zeros of opposite sign, is +0

this.TestCase( SECTION,    "VAR1 = 0; VAR2= 0; VAR1 += VAR2",    0,      eval("VAR1 = 0; VAR2 = 0; VAR1 += VAR2; VAR1") );
this.TestCase( SECTION,    "VAR1 = 0; VAR2= -0; VAR1 += VAR2",   0,      eval("VAR1 = 0; VAR2 = -0; VAR1 += VAR2; VAR1") );
this.TestCase( SECTION,    "VAR1 = -0; VAR2= 0; VAR1 += VAR2",   0,      eval("VAR1 = -0; VAR2 = 0; VAR1 += VAR2; VAR1") );
this.TestCase( SECTION,    "VAR1 = -0; VAR2= -0; VAR1 += VAR2",  -0,      eval("VAR1 = -0; VAR2 = -0; VAR1 += VAR2; VAR1") );

//  the sum of a zero and a nonzero finite value is eqal to the nonzero operand

this.TestCase( SECTION,    "VAR1 = 0; VAR2= 1; VAR2 += VAR1; VAR2",    1,      eval("VAR1 = 0; VAR2 = 1; VAR2 += VAR1; VAR2") );
this.TestCase( SECTION,    "VAR1 = -0; VAR2= 1; VAR2 += VAR1; VAR2",   1,      eval("VAR1 = -0; VAR2 = 1; VAR2 += VAR1; VAR2") );
this.TestCase( SECTION,    "VAR1 = -0; VAR2= -1; VAR2 += VAR1; VAR2",  -1,      eval("VAR1 = -0; VAR2 = -1; VAR2 += VAR1; VAR2") );
this.TestCase( SECTION,    "VAR1 = 0; VAR2= -1; VAR2 += VAR1; VAR2",   -1,      eval("VAR1 = 0; VAR2 = -1; VAR2 += VAR1; VAR2") );

// the sum of a zero and a nozero finite value is equal to the nonzero operand.
this.TestCase( SECTION,    "VAR1 = 0; VAR2=1; VAR1 += VAR2",         1,          eval("VAR1 = 0; VAR2=1; VAR1 += VAR2") );
this.TestCase( SECTION,    "VAR1 = 0; VAR2=1; VAR1 += VAR2;VAR1",    1,          eval("VAR1 = 0; VAR2=1; VAR1 += VAR2;VAR1") );

// the sum of two nonzero finite values of the same magnitude and opposite sign is +0
this.TestCase( SECTION,    "VAR1 = Number.MAX_VALUE; VAR2= -Number.MAX_VALUE; VAR1 += VAR2; VAR1",    0,  eval("VAR1 = Number.MAX_VALUE; VAR2= -Number.MAX_VALUE; VAR1 += VAR2; VAR1") );
this.TestCase( SECTION,    "VAR1 = Number.MIN_VALUE; VAR2= -Number.MIN_VALUE; VAR1 += VAR2; VAR1",   0,  eval("VAR1 = Number.MIN_VALUE; VAR2= -Number.MIN_VALUE; VAR1 += VAR2; VAR1") );

/*
new TestCase( SECTION,    "VAR1 = 10; VAR2 = '0XFF', VAR1 += VAR2", 2550,       eval("VAR1 = 10; VAR2 = '0XFF', VAR1 += VAR2") );
new TestCase( SECTION,    "VAR1 = '0xFF'; VAR2 = 0xA, VAR1 += VAR2", 2550,      eval("VAR1 = '0XFF'; VAR2 = 0XA, VAR1 += VAR2") );

new TestCase( SECTION,    "VAR1 = '10'; VAR2 = '255', VAR1 += VAR2", 2550,      eval("VAR1 = '10'; VAR2 = '255', VAR1 += VAR2") );
new TestCase( SECTION,    "VAR1 = '10'; VAR2 = '0XFF', VAR1 += VAR2", 2550,     eval("VAR1 = '10'; VAR2 = '0XFF', VAR1 += VAR2") );
new TestCase( SECTION,    "VAR1 = '0xFF'; VAR2 = 0xA, VAR1 += VAR2", 2550,      eval("VAR1 = '0XFF'; VAR2 = 0XA, VAR1 += VAR2") );

// boolean cases
new TestCase( SECTION,    "VAR1 = true; VAR2 = false; VAR1 += VAR2",    0,      eval("VAR1 = true; VAR2 = false; VAR1 += VAR2") );
new TestCase( SECTION,    "VAR1 = true; VAR2 = true; VAR1 += VAR2",    1,      eval("VAR1 = true; VAR2 = true; VAR1 += VAR2") );

// object cases
new TestCase( SECTION,    "VAR1 = new Boolean(true); VAR2 = 10; VAR1 += VAR2;VAR1",    10,      eval("VAR1 = new Boolean(true); VAR2 = 10; VAR1 += VAR2; VAR1") );
new TestCase( SECTION,    "VAR1 = new Number(11); VAR2 = 10; VAR1 += VAR2; VAR1",    110,      eval("VAR1 = new Number(11); VAR2 = 10; VAR1 += VAR2; VAR1") );
new TestCase( SECTION,    "VAR1 = new Number(11); VAR2 = new Number(10); VAR1 += VAR2",    110,      eval("VAR1 = new Number(11); VAR2 = new Number(10); VAR1 += VAR2") );
new TestCase( SECTION,    "VAR1 = new String('15'); VAR2 = new String('0xF'); VAR1 += VAR2",    255,      eval("VAR1 = String('15'); VAR2 = new String('0xF'); VAR1 += VAR2") );

*/

//test();

},

/**
File Name:          11.13.2-5.js
ECMA Section:       11.13.2 Compound Assignment: -=
Description:

*= /= %= -= -= <<= >>= >>>= &= ^= |=

11.13.2 Compound assignment ( op= )

The production AssignmentExpression :
LeftHandSideExpression @ = AssignmentExpression, where @ represents one of
the operators indicated above, is evaluated as follows:

1.  Evaluate LeftHandSideExpression.
2.  Call GetValue(Result(1)).
3.  Evaluate AssignmentExpression.
4.  Call GetValue(Result(3)).
5.  Apply operator @ to Result(2) and Result(4).
6.  Call PutValue(Result(1), Result(5)).
7.  Return Result(5).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_13_2__5: function() {
var SECTION = "11.13.2-5";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Compound Assignment: -=");

// If either operand is NaN,  result is NaN

this.TestCase( SECTION,    "VAR1 = NaN; VAR2=1; VAR1 -= VAR2",       Number.NaN, eval("VAR1 = Number.NaN; VAR2=1; VAR1 -= VAR2") );
this.TestCase( SECTION,    "VAR1 = NaN; VAR2=1; VAR1 -= VAR2; VAR1", Number.NaN, eval("VAR1 = Number.NaN; VAR2=1; VAR1 -= VAR2; VAR1") );
this.TestCase( SECTION,    "VAR1 = NaN; VAR2=0; VAR1 -= VAR2",       Number.NaN, eval("VAR1 = Number.NaN; VAR2=0; VAR1 -= VAR2") );
this.TestCase( SECTION,    "VAR1 = NaN; VAR2=0; VAR1 -= VAR2; VAR1", Number.NaN, eval("VAR1 = Number.NaN; VAR2=0; VAR1 -= VAR2; VAR1") );
this.TestCase( SECTION,    "VAR1 = 0; VAR2=NaN; VAR1 -= VAR2",       Number.NaN, eval("VAR1 = 0; VAR2=Number.NaN; VAR1 -= VAR2") );
this.TestCase( SECTION,    "VAR1 = 0; VAR2=NaN; VAR1 -= VAR2; VAR1", Number.NaN, eval("VAR1 = 0; VAR2=Number.NaN; VAR1 -= VAR2; VAR1") );

// the sum of two Infinities the same sign is the infinity of that sign
// the sum of two Infinities of opposite sign is NaN

this.TestCase( SECTION,    "VAR1 = Infinity; VAR2= Infinity; VAR1 -= VAR2; VAR1",    Number.NaN,                 eval("VAR1 = Number.POSITIVE_INFINITY; VAR2 = Number.POSITIVE_INFINITY; VAR1 -= VAR2; VAR1") );
this.TestCase( SECTION,    "VAR1 = Infinity; VAR2= -Infinity; VAR1 -= VAR2; VAR1",   Number.POSITIVE_INFINITY,   eval("VAR1 = Number.POSITIVE_INFINITY; VAR2 = Number.NEGATIVE_INFINITY; VAR1 -= VAR2; VAR1") );
this.TestCase( SECTION,    "VAR1 =-Infinity; VAR2= Infinity; VAR1 -= VAR2; VAR1",    Number.NEGATIVE_INFINITY,   eval("VAR1 = Number.NEGATIVE_INFINITY; VAR2 = Number.POSITIVE_INFINITY; VAR1 -= VAR2; VAR1") );
this.TestCase( SECTION,    "VAR1 =-Infinity; VAR2=-Infinity; VAR1 -= VAR2; VAR1",    Number.NaN,                 eval("VAR1 = Number.NEGATIVE_INFINITY; VAR2 = Number.NEGATIVE_INFINITY; VAR1 -= VAR2; VAR1") );

// the sum of an infinity and a finite value is equal to the infinite operand

this.TestCase( SECTION,    "VAR1 = 0; VAR2= Infinity; VAR1 -= VAR2;VAR1",    Number.NEGATIVE_INFINITY,      eval("VAR1 = 0; VAR2 = Number.POSITIVE_INFINITY; VAR1 -= VAR2; VAR1") );
this.TestCase( SECTION,    "VAR1 = -0; VAR2= Infinity; VAR1 -= VAR2;VAR1",   Number.NEGATIVE_INFINITY,      eval("VAR1 = -0; VAR2 = Number.POSITIVE_INFINITY; VAR1 -= VAR2; VAR1") );
this.TestCase( SECTION,    "VAR1 = 0; VAR2= -Infinity; VAR1 -= VAR2;VAR1",   Number.POSITIVE_INFINITY,        eval("VAR1 = 0; VAR2 = Number.NEGATIVE_INFINITY; VAR1 -= VAR2; VAR1") );
this.TestCase( SECTION,    "VAR1 = -0; VAR2= -Infinity; VAR1 -= VAR2;VAR1",  Number.POSITIVE_INFINITY,        eval("VAR1 = -0; VAR2 = Number.NEGATIVE_INFINITY; VAR1 -= VAR2; VAR1") );

// the sum of two negative zeros is -0. the sum of two positive zeros, or of two zeros of opposite sign, is +0

this.TestCase( SECTION,    "VAR1 = 0; VAR2= -0; VAR1 -= VAR2",    0,      eval("VAR1 = 0; VAR2 = 0; VAR1 -= VAR2; VAR1") );
this.TestCase( SECTION,    "VAR1 = 0; VAR2= 0; VAR1 -= VAR2",   0,      eval("VAR1 = 0; VAR2 = -0; VAR1 -= VAR2; VAR1") );
this.TestCase( SECTION,    "VAR1 = -0; VAR2= -0; VAR1 -= VAR2",   0,      eval("VAR1 = -0; VAR2 = 0; VAR1 -= VAR2; VAR1") );
this.TestCase( SECTION,    "VAR1 = -0; VAR2= 0; VAR1 -= VAR2",  -0,      eval("VAR1 = -0; VAR2 = -0; VAR1 -= VAR2; VAR1") );

//  the sum of a zero and a nonzero finite value is eqal to the nonzero operand

this.TestCase( SECTION,    "VAR1 = 0; VAR2= -1; VAR1 -= VAR2; VAR1",    1,      eval("VAR1 = 0; VAR2 = -1; VAR1 -= VAR2; VAR1") );
this.TestCase( SECTION,    "VAR1 = -0; VAR2= -1; VAR1 -= VAR2; VAR1",   1,      eval("VAR1 = -0; VAR2 = -1; VAR1 -= VAR2; VAR1") );
this.TestCase( SECTION,    "VAR1 = -0; VAR2= 1; VAR1 -= VAR2; VAR1",  -1,      eval("VAR1 = -0; VAR2 = 1; VAR1 -= VAR2; VAR1") );
this.TestCase( SECTION,    "VAR1 = 0; VAR2= 1; VAR1 -= VAR2; VAR1",   -1,      eval("VAR1 = 0; VAR2 = 1; VAR1 -= VAR2; VAR1") );

// the sum of a zero and a nozero finite value is equal to the nonzero operand.
this.TestCase( SECTION,    "VAR1 = 0; VAR2=-1; VAR1 -= VAR2",         1,          eval("VAR1 = 0; VAR2=-1; VAR1 -= VAR2;VAR1") );
this.TestCase( SECTION,    "VAR1 = 0; VAR2=-1; VAR1 -= VAR2;VAR1",    1,          eval("VAR1 = 0; VAR2=-1; VAR1 -= VAR2;VAR1") );

// the sum of two nonzero finite values of the same magnitude and opposite sign is +0
this.TestCase( SECTION,    "VAR1 = Number.MAX_VALUE; VAR2= Number.MAX_VALUE; VAR1 -= VAR2; VAR1",    0,  eval("VAR1 = Number.MAX_VALUE; VAR2= Number.MAX_VALUE; VAR1 -= VAR2; VAR1") );
this.TestCase( SECTION,    "VAR1 = Number.MIN_VALUE; VAR2= Number.MIN_VALUE; VAR1 -= VAR2; VAR1",    0,  eval("VAR1 = Number.MIN_VALUE; VAR2= Number.MIN_VALUE; VAR1 -= VAR2; VAR1") );

/*
this.TestCase( SECTION,    "VAR1 = 10; VAR2 = '0XFF', VAR1 -= VAR2", 2550,       eval("VAR1 = 10; VAR2 = '0XFF', VAR1 -= VAR2") );
new TestCase( SECTION,    "VAR1 = '0xFF'; VAR2 = 0xA, VAR1 -= VAR2", 2550,      eval("VAR1 = '0XFF'; VAR2 = 0XA, VAR1 -= VAR2") );

new TestCase( SECTION,    "VAR1 = '10'; VAR2 = '255', VAR1 -= VAR2", 2550,      eval("VAR1 = '10'; VAR2 = '255', VAR1 -= VAR2") );
new TestCase( SECTION,    "VAR1 = '10'; VAR2 = '0XFF', VAR1 -= VAR2", 2550,     eval("VAR1 = '10'; VAR2 = '0XFF', VAR1 -= VAR2") );
new TestCase( SECTION,    "VAR1 = '0xFF'; VAR2 = 0xA, VAR1 -= VAR2", 2550,      eval("VAR1 = '0XFF'; VAR2 = 0XA, VAR1 -= VAR2") );

// boolean cases
new TestCase( SECTION,    "VAR1 = true; VAR2 = false; VAR1 -= VAR2",    0,      eval("VAR1 = true; VAR2 = false; VAR1 -= VAR2") );
new TestCase( SECTION,    "VAR1 = true; VAR2 = true; VAR1 -= VAR2",    1,      eval("VAR1 = true; VAR2 = true; VAR1 -= VAR2") );

// object cases
new TestCase( SECTION,    "VAR1 = new Boolean(true); VAR2 = 10; VAR1 -= VAR2;VAR1",    10,      eval("VAR1 = new Boolean(true); VAR2 = 10; VAR1 -= VAR2; VAR1") );
new TestCase( SECTION,    "VAR1 = new Number(11); VAR2 = 10; VAR1 -= VAR2; VAR1",    110,      eval("VAR1 = new Number(11); VAR2 = 10; VAR1 -= VAR2; VAR1") );
new TestCase( SECTION,    "VAR1 = new Number(11); VAR2 = new Number(10); VAR1 -= VAR2",    110,      eval("VAR1 = new Number(11); VAR2 = new Number(10); VAR1 -= VAR2") );
new TestCase( SECTION,    "VAR1 = new String('15'); VAR2 = new String('0xF'); VAR1 -= VAR2",    255,      eval("VAR1 = String('15'); VAR2 = new String('0xF'); VAR1 -= VAR2") );

*/

//test();

},

/**
File Name:          11.12.js
ECMA Section:       11.12 Conditional Operator
Description:
Logi

calORExpression ? AssignmentExpression : AssignmentExpression

Semantics

The production ConditionalExpression :
LogicalORExpression ? AssignmentExpression : AssignmentExpression
is evaluated as follows:

1.  Evaluate LogicalORExpression.
2.  Call GetValue(Result(1)).
3.  Call ToBoolean(Result(2)).
4.  If Result(3) is false, go to step 8.
5.  Evaluate the first AssignmentExpression.
6.  Call GetValue(Result(5)).
7.  Return Result(6).
8.  Evaluate the second AssignmentExpression.
9.  Call GetValue(Result(8)).
10.  Return Result(9).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_13: function() {
var SECTION = "11.12";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Conditional operator( ? : )");

this.TestCase( SECTION,    "true ? 'PASSED' : 'FAILED'",     "PASSED",       (true?"PASSED":"FAILED"));
this.TestCase( SECTION,    "false ? 'FAILED' : 'PASSED'",     "PASSED",      (false?"FAILED":"PASSED"));

this.TestCase( SECTION,    "1 ? 'PASSED' : 'FAILED'",     "PASSED",          (true?"PASSED":"FAILED"));
this.TestCase( SECTION,    "0 ? 'FAILED' : 'PASSED'",     "PASSED",          (false?"FAILED":"PASSED"));
this.TestCase( SECTION,    "-1 ? 'PASSED' : 'FAILED'",     "PASSED",          (true?"PASSED":"FAILED"));

this.TestCase( SECTION,    "NaN ? 'FAILED' : 'PASSED'",     "PASSED",          (Number.NaN?"FAILED":"PASSED"));

this.TestCase( SECTION,    "var VAR = true ? , : 'FAILED'", "PASSED",           (VAR = true ? "PASSED" : "FAILED") );

//test();

},

/**
File Name:          11.14-1.js
ECMA Section:       11.14 Comma operator (,)
Description:
Expression :

AssignmentExpression
Expression , AssignmentExpression

Semantics

The production Expression : Expression , AssignmentExpression is evaluated as follows:

1.  Evaluate Expression.
2.  Call GetValue(Result(1)).
3.  Evaluate AssignmentExpression.
4.  Call GetValue(Result(3)).
5.  Return Result(4).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_14__1: function() {
var SECTION = "11.14-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Comma operator (,)");

this.TestCase( SECTION,    "true, false",                    false,  eval("true, false") );
this.TestCase( SECTION,    "VAR1=true, VAR2=false",          false,  eval("VAR1=true, VAR2=false") );
this.TestCase( SECTION,    "VAR1=true, VAR2=false;VAR1",     true,   eval("VAR1=true, VAR2=false; VAR1") );

//test();

},

/**
File Name:          11.2.1-1.js
ECMA Section:       11.2.1 Property Accessors
Description:

Properties are accessed by name, using either the dot notation:
MemberExpression . Identifier
CallExpression . Identifier

or the bracket notation:    MemberExpression [ Expression ]
CallExpression [ Expression ]

The dot notation is explained by the following syntactic conversion:
MemberExpression . Identifier
is identical in its behavior to
MemberExpression [ <identifier-string> ]
and similarly
CallExpression . Identifier
is identical in its behavior to
CallExpression [ <identifier-string> ]
where <identifier-string> is a string literal containing the same sequence
of characters as the Identifier.

The production MemberExpression : MemberExpression [ Expression ] is
evaluated as follows:

1.  Evaluate MemberExpression.
2.  Call GetValue(Result(1)).
3.  Evaluate Expression.
4.  Call GetValue(Result(3)).
5.  Call ToObject(Result(2)).
6.  Call ToString(Result(4)).
7.  Return a value of type Reference whose base object is Result(5) and
whose property name is Result(6).

The production CallExpression : CallExpression [ Expression ] is evaluated
in exactly the same manner, except that the contained CallExpression is
evaluated in step 1.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_1__1: function() {
var SECTION = "11.2.1-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Property Accessors";
//writeHeaderToLog( SECTION + " "+TITLE );

// go through all Native Function objects, methods, and properties and get their typeof.

var PROPERTY = new Array();
var p = 0;

// properties and functions of the global object

PROPERTY[p++] = new Property( "this",   "NaN",          "number" );
PROPERTY[p++] = new Property( "this",   "Infinity",     "number" );
PROPERTY[p++] = new Property( "this",   "eval",         "function" );
PROPERTY[p++] = new Property( "this",   "parseInt",     "function" );
PROPERTY[p++] = new Property( "this",   "parseFloat",   "function" );
PROPERTY[p++] = new Property( "this",   "escape",       "function" );
PROPERTY[p++] = new Property( "this",   "unescape",     "function" );
PROPERTY[p++] = new Property( "this",   "isNaN",        "function" );
PROPERTY[p++] = new Property( "this",   "isFinite",     "function" );
PROPERTY[p++] = new Property( "this",   "Object",       "function" );
PROPERTY[p++] = new Property( "this",   "Number",       "function" );
PROPERTY[p++] = new Property( "this",   "Function",     "function" );
PROPERTY[p++] = new Property( "this",   "Array",        "function" );
PROPERTY[p++] = new Property( "this",   "String",       "function" );
PROPERTY[p++] = new Property( "this",   "Boolean",      "function" );
PROPERTY[p++] = new Property( "this",   "Date",         "function" );
PROPERTY[p++] = new Property( "this",   "Math",         "object" );

// properties and  methods of Object objects

PROPERTY[p++] = new Property( "Object", "prototype",    "object" );
PROPERTY[p++] = new Property( "Object", "toString",     "function" );
PROPERTY[p++] = new Property( "Object", "valueOf",      "function" );
PROPERTY[p++] = new Property( "Object", "constructor",  "function" );

// properties of the Function object

PROPERTY[p++] = new Property( "Function",   "prototype",    "function" );
PROPERTY[p++] = new Property( "Function.prototype",   "toString",     "function" );
PROPERTY[p++] = new Property( "Function.prototype",   "length",       "number" );
PROPERTY[p++] = new Property( "Function.prototype",   "valueOf",      "function" );

Function.prototype.myProperty = "hi";

PROPERTY[p++] = new Property( "Function.prototype",   "myProperty",   "string" );

// properties of the Array object
PROPERTY[p++] = new Property( "Array",      "prototype",    "object" );
PROPERTY[p++] = new Property( "Array",      "length",       "number" );
PROPERTY[p++] = new Property( "Array.prototype",      "constructor",  "function" );
PROPERTY[p++] = new Property( "Array.prototype",      "toString",     "function" );
PROPERTY[p++] = new Property( "Array.prototype",      "join",         "function" );
PROPERTY[p++] = new Property( "Array.prototype",      "reverse",      "function" );
PROPERTY[p++] = new Property( "Array.prototype",      "sort",         "function" );

// properties of the String object
PROPERTY[p++] = new Property( "String",     "prototype",    "object" );
PROPERTY[p++] = new Property( "String",     "fromCharCode", "function" );
PROPERTY[p++] = new Property( "String.prototype",     "toString",     "function" );
PROPERTY[p++] = new Property( "String.prototype",     "constructor",  "function" );
PROPERTY[p++] = new Property( "String.prototype",     "valueOf",      "function" );
PROPERTY[p++] = new Property( "String.prototype",     "charAt",       "function" );
PROPERTY[p++] = new Property( "String.prototype",     "charCodeAt",   "function" );
PROPERTY[p++] = new Property( "String.prototype",     "indexOf",      "function" );
PROPERTY[p++] = new Property( "String.prototype",     "lastIndexOf",  "function" );
PROPERTY[p++] = new Property( "String.prototype",     "split",        "function" );
PROPERTY[p++] = new Property( "String.prototype",     "substring",    "function" );
PROPERTY[p++] = new Property( "String.prototype",     "toLowerCase",  "function" );
PROPERTY[p++] = new Property( "String.prototype",     "toUpperCase",  "function" );
PROPERTY[p++] = new Property( "String.prototype",     "length",       "number" );

// properties of the Boolean object
PROPERTY[p++] = new Property( "Boolean",    "prototype",    "object" );
PROPERTY[p++] = new Property( "Boolean",    "constructor",  "function" );
PROPERTY[p++] = new Property( "Boolean.prototype",    "valueOf",      "function" );
PROPERTY[p++] = new Property( "Boolean.prototype",    "toString",     "function" );

// properties of the Number object

PROPERTY[p++] = new Property( "Number",     "MAX_VALUE",    "number" );
PROPERTY[p++] = new Property( "Number",     "MIN_VALUE",    "number" );
PROPERTY[p++] = new Property( "Number",     "NaN",          "number" );
PROPERTY[p++] = new Property( "Number",     "NEGATIVE_INFINITY",    "number" );
PROPERTY[p++] = new Property( "Number",     "POSITIVE_INFINITY",    "number" );
PROPERTY[p++] = new Property( "Number.prototype",     "toString",     "function" );
PROPERTY[p++] = new Property( "Number.prototype",     "constructor",  "function" );
PROPERTY[p++] = new Property( "Number.prototype",     "valueOf",        "function" );

// properties of the Math Object.
PROPERTY[p++] = new Property( "Math",   "E",        "number" );
PROPERTY[p++] = new Property( "Math",   "LN10",     "number" );
PROPERTY[p++] = new Property( "Math",   "LN2",      "number" );
PROPERTY[p++] = new Property( "Math",   "LOG2E",    "number" );
PROPERTY[p++] = new Property( "Math",   "LOG10E",   "number" );
PROPERTY[p++] = new Property( "Math",   "PI",       "number" );
PROPERTY[p++] = new Property( "Math",   "SQRT1_2",  "number" );
PROPERTY[p++] = new Property( "Math",   "SQRT2",    "number" );
PROPERTY[p++] = new Property( "Math",   "abs",      "function" );
PROPERTY[p++] = new Property( "Math",   "acos",     "function" );
PROPERTY[p++] = new Property( "Math",   "asin",     "function" );
PROPERTY[p++] = new Property( "Math",   "atan",     "function" );
PROPERTY[p++] = new Property( "Math",   "atan2",    "function" );
PROPERTY[p++] = new Property( "Math",   "ceil",     "function" );
PROPERTY[p++] = new Property( "Math",   "cos",      "function" );
PROPERTY[p++] = new Property( "Math",   "exp",      "function" );
PROPERTY[p++] = new Property( "Math",   "floor",    "function" );
PROPERTY[p++] = new Property( "Math",   "log",      "function" );
PROPERTY[p++] = new Property( "Math",   "max",      "function" );
PROPERTY[p++] = new Property( "Math",   "min",      "function" );
PROPERTY[p++] = new Property( "Math",   "pow",      "function" );
PROPERTY[p++] = new Property( "Math",   "random",   "function" );
PROPERTY[p++] = new Property( "Math",   "round",    "function" );
PROPERTY[p++] = new Property( "Math",   "sin",      "function" );
PROPERTY[p++] = new Property( "Math",   "sqrt",     "function" );
PROPERTY[p++] = new Property( "Math",   "tan",      "function" );

// properties of the Date object
PROPERTY[p++] = new Property( "Date",   "parse",        "function" );
PROPERTY[p++] = new Property( "Date",   "prototype",    "object" );
PROPERTY[p++] = new Property( "Date",   "UTC",          "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "constructor",    "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "toString",       "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "valueOf",        "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "getTime",        "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "getYear",        "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "getFullYear",    "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "getUTCFullYear", "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "getMonth",       "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "getUTCMonth",    "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "getDate",        "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "getUTCDate",     "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "getDay",         "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "getUTCDay",      "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "getHours",       "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "getUTCHours",    "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "getMinutes",     "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "getUTCMinutes",  "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "getSeconds",     "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "getUTCSeconds",  "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "getMilliseconds","function" );
PROPERTY[p++] = new Property( "Date.prototype",   "getUTCMilliseconds", "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "setTime",        "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "setMilliseconds","function" );
PROPERTY[p++] = new Property( "Date.prototype",   "setUTCMilliseconds", "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "setSeconds",     "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "setUTCSeconds",  "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "setMinutes",     "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "setUTCMinutes",  "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "setHours",       "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "setUTCHours",    "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "setDate",        "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "setUTCDate",     "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "setMonth",       "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "setUTCMonth",    "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "setFullYear",    "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "setUTCFullYear", "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "setYear",        "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "toLocaleString", "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "toUTCString",    "function" );
PROPERTY[p++] = new Property( "Date.prototype",   "toGMTString",    "function" );

for ( var i = 0, RESULT; i < PROPERTY.length; i++ ) {

if (PROPERTY[i].object == 'this') {
RESULT = eval("typeof " + PROPERTY[i].name);
}
else {
RESULT = eval("typeof " + PROPERTY[i].object + "." + PROPERTY[i].name );
}

this.TestCase( SECTION,
"typeof " + PROPERTY[i].object + "." + PROPERTY[i].name,
PROPERTY[i].type,
RESULT );

if (PROPERTY[i].object == 'this') {
continue;
}

RESULT = eval("typeof " + PROPERTY[i].object + "['" + PROPERTY[i].name +"']");
this.TestCase( SECTION,
"typeof " + PROPERTY[i].object + "['" + PROPERTY[i].name +"']",
PROPERTY[i].type,
RESULT );
}

//test();

function MyObject( arg0, arg1, arg2, arg3, arg4 ) {
this.name   = arg0;
}
function Property( object, name, type ) {
this.object = object;
this.name = name;
this.type = type;
}

},

/**
File Name:          11.2.1-2.js
ECMA Section:       11.2.1 Property Accessors
Description:

Properties are accessed by name, using either the dot notation:
MemberExpression . Identifier
CallExpression . Identifier

or the bracket notation:    MemberExpression [ Expression ]
CallExpression [ Expression ]

The dot notation is explained by the following syntactic conversion:
MemberExpression . Identifier
is identical in its behavior to
MemberExpression [ <identifier-string> ]
and similarly
CallExpression . Identifier
is identical in its behavior to
CallExpression [ <identifier-string> ]
where <identifier-string> is a string literal containing the same sequence
of characters as the Identifier.

The production MemberExpression : MemberExpression [ Expression ] is
evaluated as follows:

1.  Evaluate MemberExpression.
2.  Call GetValue(Result(1)).
3.  Evaluate Expression.
4.  Call GetValue(Result(3)).
5.  Call ToObject(Result(2)).
6.  Call ToString(Result(4)).
7.  Return a value of type Reference whose base object is Result(5) and
whose property name is Result(6).

The production CallExpression : CallExpression [ Expression ] is evaluated
in exactly the same manner, except that the contained CallExpression is
evaluated in step 1.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_1__2: function() {
var SECTION = "11.2.1-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Property Accessors";
//writeHeaderToLog( SECTION + " "+TITLE );

// go through all Native Function objects, methods, and properties and get their typeof.

var PROPERTY = new Array();
var p = 0;

// try to access properties of primitive types

PROPERTY[p++] = new Property(  "\"hi\"",    "hi",   "hi",   NaN );
PROPERTY[p++] = new Property(  NaN,         NaN,    "NaN",    NaN );
//    PROPERTY[p++] = new Property(  3,           3,      "3",    3  );
PROPERTY[p++] = new Property(  true,        true,      "true",    1 );
PROPERTY[p++] = new Property(  false,       false,      "false",    0 );

for ( var i = 0, RESULT; i < PROPERTY.length; i++ ) {
this.TestCase( SECTION,
PROPERTY[i].object + ".valueOf()",
PROPERTY[i].value,
eval( PROPERTY[i].object+ ".valueOf()" ) );

this.TestCase( SECTION,
PROPERTY[i].object + ".toString()",
PROPERTY[i].string,
eval( PROPERTY[i].object+ ".toString()" ) );

}

//test();

function MyObject( value ) {
this.value = value;
this.stringValue = value +"";
this.numberValue = Number(value);
return this;
}
function Property( object, value, string, number ) {
this.object = object;
this.string = String(value);
this.number = Number(value);
this.value = value;
}

},

/**
File Name:          11.2.1-2.js
ECMA Section:       11.2.1 Property Accessors
Description:

Properties are accessed by name, using either the dot notation:
MemberExpression . Identifier
CallExpression . Identifier

or the bracket notation:    MemberExpression [ Expression ]
CallExpression [ Expression ]

The dot notation is explained by the following syntactic conversion:
MemberExpression . Identifier
is identical in its behavior to
MemberExpression [ <identifier-string> ]
and similarly
CallExpression . Identifier
is identical in its behavior to
CallExpression [ <identifier-string> ]
where <identifier-string> is a string literal containing the same sequence
of characters as the Identifier.

The production MemberExpression : MemberExpression [ Expression ] is
evaluated as follows:

1.  Evaluate MemberExpression.
2.  Call GetValue(Result(1)).
3.  Evaluate Expression.
4.  Call GetValue(Result(3)).
5.  Call ToObject(Result(2)).
6.  Call ToString(Result(4)).
7.  Return a value of type Reference whose base object is Result(5) and
whose property name is Result(6).

The production CallExpression : CallExpression [ Expression ] is evaluated
in exactly the same manner, except that the contained CallExpression is
evaluated in step 1.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_1__3__n: function() {
var SECTION = "11.2.1-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Property Accessors";
//writeHeaderToLog( SECTION + " "+TITLE );

// go through all Native Function objects, methods, and properties and get their typeof.

var PROPERTY = new Array();
var p = 0;

// try to access properties of primitive types

PROPERTY[p++] = new Property(  "undefined",    void 0,   "undefined",   NaN );

for ( var i = 0, RESULT; i < PROPERTY.length; i++ ) {

DESCRIPTION = PROPERTY[i].object + ".valueOf()";
EXPECTED = "error";

this.TestCase( SECTION,
PROPERTY[i].object + ".valueOf()",
PROPERTY[i].value,
eval( PROPERTY[i].object+ ".valueOf()" ) );

this.TestCase( SECTION,
PROPERTY[i].object + ".toString()",
PROPERTY[i].string,
eval(PROPERTY[i].object+ ".toString()") );
}

//test();

function MyObject( value ) {
this.value = value;
this.stringValue = value +"";
this.numberValue = Number(value);
return this;
}

function Property( object, value, string, number ) {
this.object = object;
this.string = String(value);
this.number = Number(value);
this.value = value;
}

},

/**
File Name:          11.2.1-2.js
ECMA Section:       11.2.1 Property Accessors
Description:

Properties are accessed by name, using either the dot notation:
MemberExpression . Identifier
CallExpression . Identifier

or the bracket notation:    MemberExpression [ Expression ]
CallExpression [ Expression ]

The dot notation is explained by the following syntactic conversion:
MemberExpression . Identifier
is identical in its behavior to
MemberExpression [ <identifier-string> ]
and similarly
CallExpression . Identifier
is identical in its behavior to
CallExpression [ <identifier-string> ]
where <identifier-string> is a string literal containing the same sequence
of characters as the Identifier.

The production MemberExpression : MemberExpression [ Expression ] is
evaluated as follows:

1.  Evaluate MemberExpression.
2.  Call GetValue(Result(1)).
3.  Evaluate Expression.
4.  Call GetValue(Result(3)).
5.  Call ToObject(Result(2)).
6.  Call ToString(Result(4)).
7.  Return a value of type Reference whose base object is Result(5) and
whose property name is Result(6).

The production CallExpression : CallExpression [ Expression ] is evaluated
in exactly the same manner, except that the contained CallExpression is
evaluated in step 1.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_1__3__n_WORKS: function() {
var SECTION = "11.2.1-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Property Accessors";
//writeHeaderToLog( SECTION + " "+TITLE );

// go through all Native Function objects, methods, and properties and get their typeof.

var PROPERTY = new Array();
var p = 0;

// try to access properties of primitive types

PROPERTY[p++] = new Property(  "undefined",    void 0,   "undefined",   NaN );

for ( var i = 0, RESULT; i < PROPERTY.length; i++ ) {

DESCRIPTION = PROPERTY[i].object + ".valueOf()";
EXPECTED = "error";

try {
this.TestCase( SECTION,
PROPERTY[i].object + ".valueOf()",
PROPERTY[i].value,
eval( PROPERTY[i].object+ ".valueOf()" ) );
fail();
} catch (e) {
assertTrue(e.name == 'TypeError');
assertTrue(e.message == 'Cannot call method "valueOf" of undefined');
}

try {
this.TestCase( SECTION,
PROPERTY[i].object + ".toString()",
PROPERTY[i].string,
eval(PROPERTY[i].object+ ".toString()") );
fail();
} catch (e) {
assertTrue(e.name == 'TypeError');
assertTrue(e.message == 'Cannot call method "toString" of undefined');
}
}

//test();

function MyObject( value ) {
this.value = value;
this.stringValue = value +"";
this.numberValue = Number(value);
return this;
}

function Property( object, value, string, number ) {
this.object = object;
this.string = String(value);
this.number = Number(value);
this.value = value;
}

},

/**
File Name:          11.2.1-4-n.js
ECMA Section:       11.2.1 Property Accessors
Description:

Properties are accessed by name, using either the dot notation:
MemberExpression . Identifier
CallExpression . Identifier

or the bracket notation:    MemberExpression [ Expression ]
CallExpression [ Expression ]

The dot notation is explained by the following syntactic conversion:
MemberExpression . Identifier
is identical in its behavior to
MemberExpression [ <identifier-string> ]
and similarly
CallExpression . Identifier
is identical in its behavior to
CallExpression [ <identifier-string> ]
where <identifier-string> is a string literal containing the same sequence
of characters as the Identifier.

The production MemberExpression : MemberExpression [ Expression ] is
evaluated as follows:

1.  Evaluate MemberExpression.
2.  Call GetValue(Result(1)).
3.  Evaluate Expression.
4.  Call GetValue(Result(3)).
5.  Call ToObject(Result(2)).
6.  Call ToString(Result(4)).
7.  Return a value of type Reference whose base object is Result(5) and
whose property name is Result(6).

The production CallExpression : CallExpression [ Expression ] is evaluated
in exactly the same manner, except that the contained CallExpression is
evaluated in step 1.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_1__4__n: function() {
var SECTION = "11.2.1-4-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Property Accessors";
//writeHeaderToLog( SECTION + " "+TITLE );

// go through all Native Function objects, methods, and properties and get their typeof.

var PROPERTY = new Array();
var p = 0;

// try to access properties of primitive types

PROPERTY[p++] = new Property(  "null",    null,   "null",   0 );

for ( var i = 0, RESULT; i < PROPERTY.length; i++ ) {

DESCRIPTION = PROPERTY[i].object + ".valueOf()";
EXPECTED = "error";

this.TestCase( SECTION,
PROPERTY[i].object + ".valueOf()",
PROPERTY[i].value,
eval( PROPERTY[i].object+ ".valueOf()" ) );

this.TestCase( SECTION,
PROPERTY[i].object + ".toString()",
PROPERTY[i].string,
eval(PROPERTY[i].object+ ".toString()") );

}

//test();

function MyObject( value ) {
this.value = value;
this.stringValue = value +"";
this.numberValue = Number(value);
return this;
}
function Property( object, value, string, number ) {
this.object = object;
this.string = String(value);
this.number = Number(value);
this.value = value;
}

},

/**
File Name:          11.2.1-4-n.js
ECMA Section:       11.2.1 Property Accessors
Description:

Properties are accessed by name, using either the dot notation:
MemberExpression . Identifier
CallExpression . Identifier

or the bracket notation:    MemberExpression [ Expression ]
CallExpression [ Expression ]

The dot notation is explained by the following syntactic conversion:
MemberExpression . Identifier
is identical in its behavior to
MemberExpression [ <identifier-string> ]
and similarly
CallExpression . Identifier
is identical in its behavior to
CallExpression [ <identifier-string> ]
where <identifier-string> is a string literal containing the same sequence
of characters as the Identifier.

The production MemberExpression : MemberExpression [ Expression ] is
evaluated as follows:

1.  Evaluate MemberExpression.
2.  Call GetValue(Result(1)).
3.  Evaluate Expression.
4.  Call GetValue(Result(3)).
5.  Call ToObject(Result(2)).
6.  Call ToString(Result(4)).
7.  Return a value of type Reference whose base object is Result(5) and
whose property name is Result(6).

The production CallExpression : CallExpression [ Expression ] is evaluated
in exactly the same manner, except that the contained CallExpression is
evaluated in step 1.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_1__4__n_WORKS: function() {
var SECTION = "11.2.1-4-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Property Accessors";
//writeHeaderToLog( SECTION + " "+TITLE );

// go through all Native Function objects, methods, and properties and get their typeof.

var PROPERTY = new Array();
var p = 0;

// try to access properties of primitive types

PROPERTY[p++] = new Property(  "null",    null,   "null",   0 );

for ( var i = 0, RESULT; i < PROPERTY.length; i++ ) {

DESCRIPTION = PROPERTY[i].object + ".valueOf()";
EXPECTED = "error";

try {
this.TestCase( SECTION,
PROPERTY[i].object + ".valueOf()",
PROPERTY[i].value,
eval( PROPERTY[i].object+ ".valueOf()" ) );
fail();
} catch (e) {
assertTrue(e.name == 'TypeError');
assertTrue(e.message == 'Cannot call method "valueOf" of null');
}

try {
this.TestCase( SECTION,
PROPERTY[i].object + ".toString()",
PROPERTY[i].string,
eval(PROPERTY[i].object+ ".toString()") );
fail();
} catch (e) {
assertTrue(e.name == 'TypeError');
assertTrue(e.message == 'Cannot call method "toString" of null');
}

}

//test();

function MyObject( value ) {
this.value = value;
this.stringValue = value +"";
this.numberValue = Number(value);
return this;
}
function Property( object, value, string, number ) {
this.object = object;
this.string = String(value);
this.number = Number(value);
this.value = value;
}

},

/**
File Name:          11.2.1-5.js
ECMA Section:       11.2.1 Property Accessors
Description:

Properties are accessed by name, using either the dot notation:
MemberExpression . Identifier
CallExpression . Identifier

or the bracket notation:    MemberExpression [ Expression ]
CallExpression [ Expression ]

The dot notation is explained by the following syntactic conversion:
MemberExpression . Identifier
is identical in its behavior to
MemberExpression [ <identifier-string> ]
and similarly
CallExpression . Identifier
is identical in its behavior to
CallExpression [ <identifier-string> ]
where <identifier-string> is a string literal containing the same sequence
of characters as the Identifier.

The production MemberExpression : MemberExpression [ Expression ] is
evaluated as follows:

1.  Evaluate MemberExpression.
2.  Call GetValue(Result(1)).
3.  Evaluate Expression.
4.  Call GetValue(Result(3)).
5.  Call ToObject(Result(2)).
6.  Call ToString(Result(4)).
7.  Return a value of type Reference whose base object is Result(5) and
whose property name is Result(6).

The production CallExpression : CallExpression [ Expression ] is evaluated
in exactly the same manner, except that the contained CallExpression is
evaluated in step 1.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_1__5: function() {
var SECTION = "11.2.1-5";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Property Accessors";
//writeHeaderToLog( SECTION + " "+TITLE );

// go through all Native Function objects, methods, and properties and get their typeof.

var PROPERTY = new Array();
var p = 0;

// try to access properties of primitive types

PROPERTY[p++] = new Property(  new String("hi"),    "hi",   "hi",   NaN );
PROPERTY[p++] = new Property(  new Number(NaN),         NaN,    "NaN",    NaN );
PROPERTY[p++] = new Property(  new Number(3),           3,      "3",    3  );
PROPERTY[p++] = new Property(  new Boolean(true),        true,      "true",    1 );
PROPERTY[p++] = new Property(  new Boolean(false),       false,      "false",    0 );

for ( var i = 0, RESULT; i < PROPERTY.length; i++ ) {
this.TestCase( SECTION,
PROPERTY[i].object + ".valueOf()",
PROPERTY[i].value,
eval( "PROPERTY[i].object.valueOf()" ) );

this.TestCase( SECTION,
PROPERTY[i].object + ".toString()",
PROPERTY[i].string,
eval( "PROPERTY[i].object.toString()" ) );

}

//test();

function MyObject( value ) {
this.value = value;
this.stringValue = value +"";
this.numberValue = Number(value);
return this;
}
function Property( object, value, string, number ) {
this.object = object;
this.string = String(value);
this.number = Number(value);
this.value = value;
}

},

/**
File Name:          11.2.2-1.js
ECMA Section:       11.2.2. The new operator
Description:

MemberExpression:
PrimaryExpression
MemberExpression[Expression]
MemberExpression.Identifier
new MemberExpression Arguments

new NewExpression

The production NewExpression : new NewExpression is evaluated as follows:

1.   Evaluate NewExpression.
2.   Call GetValue(Result(1)).
3.   If Type(Result(2)) is not Object, generate a runtime error.
4.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
5.   Call the [[Construct]] method on Result(2), providing no arguments
(that is, an empty list of arguments).
6.   If Type(Result(5)) is not Object, generate a runtime error.
7.   Return Result(5).

The production MemberExpression : new MemberExpression Arguments is evaluated as follows:

1.   Evaluate MemberExpression.
2.   Call GetValue(Result(1)).
3.   Evaluate Arguments, producing an internal list of argument values
(section 0).
4.   If Type(Result(2)) is not Object, generate a runtime error.
5.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
6.   Call the [[Construct]] method on Result(2), providing the list
Result(3) as the argument values.
7.   If Type(Result(6)) is not Object, generate a runtime error.
8    .Return Result(6).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_2__1__n: function() {
var SECTION = "11.2.2-1-n.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The new operator";
//writeHeaderToLog( SECTION + " "+TITLE );

var OBJECT = new Object();

DESCRIPTION = "OBJECT = new Object; var o = new OBJECT()";
EXPECTED = "error";

this.TestCase( SECTION,
"OBJECT = new Object; var o = new OBJECT()",
"error",
eval("o = new OBJECT()") );

//test();

function TestFunction() {
return arguments;
}

},

/**
File Name:          11.2.2-1.js
ECMA Section:       11.2.2. The new operator
Description:

MemberExpression:
PrimaryExpression
MemberExpression[Expression]
MemberExpression.Identifier
new MemberExpression Arguments

new NewExpression

The production NewExpression : new NewExpression is evaluated as follows:

1.   Evaluate NewExpression.
2.   Call GetValue(Result(1)).
3.   If Type(Result(2)) is not Object, generate a runtime error.
4.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
5.   Call the [[Construct]] method on Result(2), providing no arguments
(that is, an empty list of arguments).
6.   If Type(Result(5)) is not Object, generate a runtime error.
7.   Return Result(5).

The production MemberExpression : new MemberExpression Arguments is evaluated as follows:

1.   Evaluate MemberExpression.
2.   Call GetValue(Result(1)).
3.   Evaluate Arguments, producing an internal list of argument values
(section 0).
4.   If Type(Result(2)) is not Object, generate a runtime error.
5.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
6.   Call the [[Construct]] method on Result(2), providing the list
Result(3) as the argument values.
7.   If Type(Result(6)) is not Object, generate a runtime error.
8    .Return Result(6).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_2__1__n_WORKS: function() {
var SECTION = "11.2.2-1-n.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The new operator";
//writeHeaderToLog( SECTION + " "+TITLE );

var OBJECT = new Object();

DESCRIPTION = "OBJECT = new Object; var o = new OBJECT()";
EXPECTED = "error";

try {
this.TestCase( SECTION,
"OBJECT = new Object; var o = new OBJECT()",
"error",
eval("o = new OBJECT()") );
fail();
} catch (e) {
assertTrue(e.name == 'TypeError');
assertTrue(e.message == '[object Object] is not a function, it is object.');
}

//test();

function TestFunction() {
return arguments;
}

},

/**
File Name:          11.2.2-1.js
ECMA Section:       11.2.2. The new operator
Description:

MemberExpression:
PrimaryExpression
MemberExpression[Expression]
MemberExpression.Identifier
new MemberExpression Arguments

new NewExpression

The production NewExpression : new NewExpression is evaluated as follows:

1.   Evaluate NewExpression.
2.   Call GetValue(Result(1)).
3.   If Type(Result(2)) is not Object, generate a runtime error.
4.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
5.   Call the [[Construct]] method on Result(2), providing no arguments
(that is, an empty list of arguments).
6.   If Type(Result(5)) is not Object, generate a runtime error.
7.   Return Result(5).

The production MemberExpression : new MemberExpression Arguments is evaluated as follows:

1.   Evaluate MemberExpression.
2.   Call GetValue(Result(1)).
3.   Evaluate Arguments, producing an internal list of argument values
(section 0).
4.   If Type(Result(2)) is not Object, generate a runtime error.
5.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
6.   Call the [[Construct]] method on Result(2), providing the list
Result(3) as the argument values.
7.   If Type(Result(6)) is not Object, generate a runtime error.
8    .Return Result(6).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_2__1: function() {
var SECTION = "11.2.2-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The new operator";

//writeHeaderToLog( SECTION + " "+TITLE );

this.TestCase( SECTION,
"(new TestFunction(0,1,2,3,4,5)).length",
6,
(new TestFunction(0,1,2,3,4,5)).length );

//test();

function TestFunction() {
return arguments;
}

},

/**
File Name:          11.2.2-9-n.js
ECMA Section:       11.2.2. The new operator
Description:

MemberExpression:
PrimaryExpression
MemberExpression[Expression]
MemberExpression.Identifier
new MemberExpression Arguments

new NewExpression

The production NewExpression : new NewExpression is evaluated as follows:

1.   Evaluate NewExpression.
2.   Call GetValue(Result(1)).
3.   If Type(Result(2)) is not Object, generate a runtime error.
4.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
5.   Call the [[Construct]] method on Result(2), providing no arguments
(that is, an empty list of arguments).
6.   If Type(Result(5)) is not Object, generate a runtime error.
7.   Return Result(5).

The production MemberExpression : new MemberExpression Arguments is evaluated as follows:

1.   Evaluate MemberExpression.
2.   Call GetValue(Result(1)).
3.   Evaluate Arguments, producing an internal list of argument values
(section 0).
4.   If Type(Result(2)) is not Object, generate a runtime error.
5.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
6.   Call the [[Construct]] method on Result(2), providing the list
Result(3) as the argument values.
7.   If Type(Result(6)) is not Object, generate a runtime error.
8    .Return Result(6).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_2__10__n: function() {
var SECTION = "11.2.2-9-n.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The new operator";
//writeHeaderToLog( SECTION + " "+TITLE );

DESCRIPTION = "var m = new Math()";
EXPECTED = "error";

this.TestCase( SECTION,
"var m = new Math()",
"error",
eval("m = new Math()") );

//test();

function TestFunction() {
return arguments;
}

},

/**
File Name:          11.2.2-9-n.js
ECMA Section:       11.2.2. The new operator
Description:

MemberExpression:
PrimaryExpression
MemberExpression[Expression]
MemberExpression.Identifier
new MemberExpression Arguments

new NewExpression

The production NewExpression : new NewExpression is evaluated as follows:

1.   Evaluate NewExpression.
2.   Call GetValue(Result(1)).
3.   If Type(Result(2)) is not Object, generate a runtime error.
4.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
5.   Call the [[Construct]] method on Result(2), providing no arguments
(that is, an empty list of arguments).
6.   If Type(Result(5)) is not Object, generate a runtime error.
7.   Return Result(5).

The production MemberExpression : new MemberExpression Arguments is evaluated as follows:

1.   Evaluate MemberExpression.
2.   Call GetValue(Result(1)).
3.   Evaluate Arguments, producing an internal list of argument values
(section 0).
4.   If Type(Result(2)) is not Object, generate a runtime error.
5.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
6.   Call the [[Construct]] method on Result(2), providing the list
Result(3) as the argument values.
7.   If Type(Result(6)) is not Object, generate a runtime error.
8    .Return Result(6).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_2__10__n_WORKS: function() {
var SECTION = "11.2.2-9-n.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The new operator";
//writeHeaderToLog( SECTION + " "+TITLE );

DESCRIPTION = "var m = new Math()";
EXPECTED = "error";

try {
this.TestCase( SECTION,
"var m = new Math()",
"error",
eval("m = new Math()") );
fail();
} catch (e) {
assertTrue(e.name == 'TypeError');
assertTrue(e.message.indexOf("NativeMath") >= 0);
assertTrue(e.message.indexOf("is not a function, it is object.") >= 0);
}

//test();

function TestFunction() {
return arguments;
}

},

/**
File Name:          11.2.2-9-n.js
ECMA Section:       11.2.2. The new operator
Description:

MemberExpression:
PrimaryExpression
MemberExpression[Expression]
MemberExpression.Identifier
new MemberExpression Arguments

new NewExpression

The production NewExpression : new NewExpression is evaluated as follows:

1.   Evaluate NewExpression.
2.   Call GetValue(Result(1)).
3.   If Type(Result(2)) is not Object, generate a runtime error.
4.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
5.   Call the [[Construct]] method on Result(2), providing no arguments
(that is, an empty list of arguments).
6.   If Type(Result(5)) is not Object, generate a runtime error.
7.   Return Result(5).

The production MemberExpression : new MemberExpression Arguments is evaluated as follows:

1.   Evaluate MemberExpression.
2.   Call GetValue(Result(1)).
3.   Evaluate Arguments, producing an internal list of argument values
(section 0).
4.   If Type(Result(2)) is not Object, generate a runtime error.
5.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
6.   Call the [[Construct]] method on Result(2), providing the list
Result(3) as the argument values.
7.   If Type(Result(6)) is not Object, generate a runtime error.
8    Return Result(6).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_2__11: function() {
var SECTION = "11.2.2-9-n.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The new operator";

//writeHeaderToLog( SECTION + " "+TITLE );

var FUNCTION = new Function();

this.TestCase( SECTION,
"var FUNCTION = new Function(); f = new FUNCTION(); typeof f",
"object",
eval("var FUNCTION = new Function(); f = new FUNCTION(); typeof f") );

this.TestCase( SECTION,
"var FUNCTION = new Function('return this'); f = new FUNCTION(); typeof f",
"object",
eval("var FUNCTION = new Function('return this'); f = new FUNCTION(); typeof f") );

//test();

},

/**
File Name:          11.2.2-2.js
ECMA Section:       11.2.2. The new operator
Description:

MemberExpression:
PrimaryExpression
MemberExpression[Expression]
MemberExpression.Identifier
new MemberExpression Arguments

new NewExpression

The production NewExpression : new NewExpression is evaluated as follows:

1.   Evaluate NewExpression.
2.   Call GetValue(Result(1)).
3.   If Type(Result(2)) is not Object, generate a runtime error.
4.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
5.   Call the [[Construct]] method on Result(2), providing no arguments
(that is, an empty list of arguments).
6.   If Type(Result(5)) is not Object, generate a runtime error.
7.   Return Result(5).

The production MemberExpression : new MemberExpression Arguments is evaluated as follows:

1.   Evaluate MemberExpression.
2.   Call GetValue(Result(1)).
3.   Evaluate Arguments, producing an internal list of argument values
(section 0).
4.   If Type(Result(2)) is not Object, generate a runtime error.
5.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
6.   Call the [[Construct]] method on Result(2), providing the list
Result(3) as the argument values.
7.   If Type(Result(6)) is not Object, generate a runtime error.
8    .Return Result(6).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_2__n: function() {
var SECTION = "11.2.2-2-n.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The new operator";

//writeHeaderToLog( SECTION + " "+TITLE );

var UNDEFINED = void 0;

DESCRIPTION = "UNDEFINED = void 0; var o = new UNDEFINED()";
EXPECTED = "error";

this.TestCase( SECTION,
"UNDEFINED = void 0; var o = new UNDEFINED()",
"error",
eval("o = new UNDEFINED()") );

//test();

function TestFunction() {
return arguments;
}

},

/**
File Name:          11.2.2-2.js
ECMA Section:       11.2.2. The new operator
Description:

MemberExpression:
PrimaryExpression
MemberExpression[Expression]
MemberExpression.Identifier
new MemberExpression Arguments

new NewExpression

The production NewExpression : new NewExpression is evaluated as follows:

1.   Evaluate NewExpression.
2.   Call GetValue(Result(1)).
3.   If Type(Result(2)) is not Object, generate a runtime error.
4.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
5.   Call the [[Construct]] method on Result(2), providing no arguments
(that is, an empty list of arguments).
6.   If Type(Result(5)) is not Object, generate a runtime error.
7.   Return Result(5).

The production MemberExpression : new MemberExpression Arguments is evaluated as follows:

1.   Evaluate MemberExpression.
2.   Call GetValue(Result(1)).
3.   Evaluate Arguments, producing an internal list of argument values
(section 0).
4.   If Type(Result(2)) is not Object, generate a runtime error.
5.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
6.   Call the [[Construct]] method on Result(2), providing the list
Result(3) as the argument values.
7.   If Type(Result(6)) is not Object, generate a runtime error.
8    .Return Result(6).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_2__n_WORKS: function() {
var SECTION = "11.2.2-2-n.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The new operator";

//writeHeaderToLog( SECTION + " "+TITLE );

var UNDEFINED = void 0;

DESCRIPTION = "UNDEFINED = void 0; var o = new UNDEFINED()";
EXPECTED = "error";

try {
this.TestCase( SECTION,
"UNDEFINED = void 0; var o = new UNDEFINED()",
"error",
eval("o = new UNDEFINED()") );
fail();
} catch (e) {
assertTrue(e.name == 'TypeError');
assertTrue(e.message.indexOf("Undefined") >= 0);
assertTrue(e.message.indexOf("is not a function, it is undefined.") >= 0);
}

//test();

function TestFunction() {
return arguments;
}

},

/**
File Name:          11.2.2-3-n.js
ECMA Section:       11.2.2. The new operator
Description:

MemberExpression:
PrimaryExpression
MemberExpression[Expression]
MemberExpression.Identifier
new MemberExpression Arguments

new NewExpression

The production NewExpression : new NewExpression is evaluated as follows:

1.   Evaluate NewExpression.
2.   Call GetValue(Result(1)).
3.   If Type(Result(2)) is not Object, generate a runtime error.
4.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
5.   Call the [[Construct]] method on Result(2), providing no arguments
(that is, an empty list of arguments).
6.   If Type(Result(5)) is not Object, generate a runtime error.
7.   Return Result(5).

The production MemberExpression : new MemberExpression Arguments is evaluated as follows:

1.   Evaluate MemberExpression.
2.   Call GetValue(Result(1)).
3.   Evaluate Arguments, producing an internal list of argument values
(section 0).
4.   If Type(Result(2)) is not Object, generate a runtime error.
5.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
6.   Call the [[Construct]] method on Result(2), providing the list
Result(3) as the argument values.
7.   If Type(Result(6)) is not Object, generate a runtime error.
8    .Return Result(6).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_2__3__n: function() {
var SECTION = "11.2.2-3-n.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The new operator";

//writeHeaderToLog( SECTION + " "+TITLE );

var DESCRIPTION = "NULL = null; var o = new NULL()";
var EXPECTED = "error";
var NULL = null;

this.TestCase( SECTION,
"NULL = null; var o = new NULL()",
"error",
eval("o = new NULL()") );

//test();

},

/**
File Name:          11.2.2-3-n.js
ECMA Section:       11.2.2. The new operator
Description:

MemberExpression:
PrimaryExpression
MemberExpression[Expression]
MemberExpression.Identifier
new MemberExpression Arguments

new NewExpression

The production NewExpression : new NewExpression is evaluated as follows:

1.   Evaluate NewExpression.
2.   Call GetValue(Result(1)).
3.   If Type(Result(2)) is not Object, generate a runtime error.
4.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
5.   Call the [[Construct]] method on Result(2), providing no arguments
(that is, an empty list of arguments).
6.   If Type(Result(5)) is not Object, generate a runtime error.
7.   Return Result(5).

The production MemberExpression : new MemberExpression Arguments is evaluated as follows:

1.   Evaluate MemberExpression.
2.   Call GetValue(Result(1)).
3.   Evaluate Arguments, producing an internal list of argument values
(section 0).
4.   If Type(Result(2)) is not Object, generate a runtime error.
5.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
6.   Call the [[Construct]] method on Result(2), providing the list
Result(3) as the argument values.
7.   If Type(Result(6)) is not Object, generate a runtime error.
8    .Return Result(6).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_2__3__n: function() {
var SECTION = "11.2.2-3-n.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The new operator";

//writeHeaderToLog( SECTION + " "+TITLE );

var DESCRIPTION = "NULL = null; var o = new NULL()";
var EXPECTED = "error";
var NULL = null;

this.TestCase( SECTION,
"NULL = null; var o = new NULL()",
"error",
eval("o = new NULL()") );

//test();

},

/**
File Name:          11.2.2-3-n.js
ECMA Section:       11.2.2. The new operator
Description:

MemberExpression:
PrimaryExpression
MemberExpression[Expression]
MemberExpression.Identifier
new MemberExpression Arguments

new NewExpression

The production NewExpression : new NewExpression is evaluated as follows:

1.   Evaluate NewExpression.
2.   Call GetValue(Result(1)).
3.   If Type(Result(2)) is not Object, generate a runtime error.
4.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
5.   Call the [[Construct]] method on Result(2), providing no arguments
(that is, an empty list of arguments).
6.   If Type(Result(5)) is not Object, generate a runtime error.
7.   Return Result(5).

The production MemberExpression : new MemberExpression Arguments is evaluated as follows:

1.   Evaluate MemberExpression.
2.   Call GetValue(Result(1)).
3.   Evaluate Arguments, producing an internal list of argument values
(section 0).
4.   If Type(Result(2)) is not Object, generate a runtime error.
5.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
6.   Call the [[Construct]] method on Result(2), providing the list
Result(3) as the argument values.
7.   If Type(Result(6)) is not Object, generate a runtime error.
8    .Return Result(6).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_2__3__n_WORKS: function() {
var SECTION = "11.2.2-3-n.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The new operator";

//writeHeaderToLog( SECTION + " "+TITLE );

var DESCRIPTION = "NULL = null; var o = new NULL()";
var EXPECTED = "error";
var NULL = null;

try {
this.TestCase( SECTION,
"NULL = null; var o = new NULL()",
"error",
eval("o = new NULL()") );
fail();
} catch (e) {
assertTrue(e.name == 'TypeError');
assertTrue(e.message == 'null is not a function, it is object.');
}

//test();

},

/**
File Name:          11.2.2-4-n.js
ECMA Section:       11.2.2. The new operator
Description:

MemberExpression:
PrimaryExpression
MemberExpression[Expression]
MemberExpression.Identifier
new MemberExpression Arguments

new NewExpression

The production NewExpression : new NewExpression is evaluated as follows:

1.   Evaluate NewExpression.
2.   Call GetValue(Result(1)).
3.   If Type(Result(2)) is not Object, generate a runtime error.
4.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
5.   Call the [[Construct]] method on Result(2), providing no arguments
(that is, an empty list of arguments).
6.   If Type(Result(5)) is not Object, generate a runtime error.
7.   Return Result(5).

The production MemberExpression : new MemberExpression Arguments is evaluated as follows:

1.   Evaluate MemberExpression.
2.   Call GetValue(Result(1)).
3.   Evaluate Arguments, producing an internal list of argument values
(section 0).
4.   If Type(Result(2)) is not Object, generate a runtime error.
5.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
6.   Call the [[Construct]] method on Result(2), providing the list
Result(3) as the argument values.
7.   If Type(Result(6)) is not Object, generate a runtime error.
8    .Return Result(6).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_2__4__n: function() {
var SECTION = "11.2.2-4-n.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The new operator";

//writeHeaderToLog( SECTION + " "+TITLE );

var STRING = "";

DESCRIPTION = "STRING = '', var s = new STRING()";
EXPECTED = "error";

this.TestCase( SECTION,
"STRING = '', var s = new STRING()",
"error",
eval("s = new STRING()") );

//test();

function TestFunction() {
return arguments;
}

},

/**
File Name:          11.2.2-4-n.js
ECMA Section:       11.2.2. The new operator
Description:

MemberExpression:
PrimaryExpression
MemberExpression[Expression]
MemberExpression.Identifier
new MemberExpression Arguments

new NewExpression

The production NewExpression : new NewExpression is evaluated as follows:

1.   Evaluate NewExpression.
2.   Call GetValue(Result(1)).
3.   If Type(Result(2)) is not Object, generate a runtime error.
4.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
5.   Call the [[Construct]] method on Result(2), providing no arguments
(that is, an empty list of arguments).
6.   If Type(Result(5)) is not Object, generate a runtime error.
7.   Return Result(5).

The production MemberExpression : new MemberExpression Arguments is evaluated as follows:

1.   Evaluate MemberExpression.
2.   Call GetValue(Result(1)).
3.   Evaluate Arguments, producing an internal list of argument values
(section 0).
4.   If Type(Result(2)) is not Object, generate a runtime error.
5.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
6.   Call the [[Construct]] method on Result(2), providing the list
Result(3) as the argument values.
7.   If Type(Result(6)) is not Object, generate a runtime error.
8    .Return Result(6).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_2__4__n_WORKS: function() {
var SECTION = "11.2.2-4-n.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The new operator";

//writeHeaderToLog( SECTION + " "+TITLE );

var STRING = "";

DESCRIPTION = "STRING = '', var s = new STRING()";
EXPECTED = "error";

try {
this.TestCase( SECTION,
"STRING = '', var s = new STRING()",
"error",
eval("s = new STRING()") );
fail();
} catch (e) {
assertTrue(e.name == 'TypeError');
assertTrue(e.message == ' is not a function, it is string.');
}

//test();

function TestFunction() {
return arguments;
}

},

/**
File Name:          11.2.2-5-n.js
ECMA Section:       11.2.2. The new operator
Description:

MemberExpression:
PrimaryExpression
MemberExpression[Expression]
MemberExpression.Identifier
new MemberExpression Arguments

new NewExpression

The production NewExpression : new NewExpression is evaluated as follows:

1.   Evaluate NewExpression.
2.   Call GetValue(Result(1)).
3.   If Type(Result(2)) is not Object, generate a runtime error.
4.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
5.   Call the [[Construct]] method on Result(2), providing no arguments
(that is, an empty list of arguments).
6.   If Type(Result(5)) is not Object, generate a runtime error.
7.   Return Result(5).

The production MemberExpression : new MemberExpression Arguments is evaluated as follows:

1.   Evaluate MemberExpression.
2.   Call GetValue(Result(1)).
3.   Evaluate Arguments, producing an internal list of argument values
(section 0).
4.   If Type(Result(2)) is not Object, generate a runtime error.
5.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
6.   Call the [[Construct]] method on Result(2), providing the list
Result(3) as the argument values.
7.   If Type(Result(6)) is not Object, generate a runtime error.
8    .Return Result(6).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_2__5__n: function() {
var SECTION = "11.2.2-5-n.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The new operator";

//writeHeaderToLog( SECTION + " "+TITLE );

var NUMBER = 0;

DESCRIPTION = "NUMBER=0, var n = new NUMBER()";
EXPECTED = "error";

this.TestCase( SECTION,
"NUMBER=0, var n = new NUMBER()",
"error",
eval("n = new NUMBER()") );

//test();

function TestFunction() {
return arguments;
}

},

/**
File Name:          11.2.2-5-n.js
ECMA Section:       11.2.2. The new operator
Description:

MemberExpression:
PrimaryExpression
MemberExpression[Expression]
MemberExpression.Identifier
new MemberExpression Arguments

new NewExpression

The production NewExpression : new NewExpression is evaluated as follows:

1.   Evaluate NewExpression.
2.   Call GetValue(Result(1)).
3.   If Type(Result(2)) is not Object, generate a runtime error.
4.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
5.   Call the [[Construct]] method on Result(2), providing no arguments
(that is, an empty list of arguments).
6.   If Type(Result(5)) is not Object, generate a runtime error.
7.   Return Result(5).

The production MemberExpression : new MemberExpression Arguments is evaluated as follows:

1.   Evaluate MemberExpression.
2.   Call GetValue(Result(1)).
3.   Evaluate Arguments, producing an internal list of argument values
(section 0).
4.   If Type(Result(2)) is not Object, generate a runtime error.
5.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
6.   Call the [[Construct]] method on Result(2), providing the list
Result(3) as the argument values.
7.   If Type(Result(6)) is not Object, generate a runtime error.
8    .Return Result(6).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_2__5__n_WORKS: function() {
var SECTION = "11.2.2-5-n.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The new operator";

//writeHeaderToLog( SECTION + " "+TITLE );

var NUMBER = 0;

DESCRIPTION = "NUMBER=0, var n = new NUMBER()";
EXPECTED = "error";

try {
this.TestCase( SECTION,
"NUMBER=0, var n = new NUMBER()",
"error",
eval("n = new NUMBER()") );
fail();
} catch (e) {
assertTrue(e.name == 'TypeError');
assertTrue(e.message == '0.0 is not a function, it is number.');
}

//test();

function TestFunction() {
return arguments;
}

},

/**
File Name:          11.2.2-6-n.js
ECMA Section:       11.2.2. The new operator
Description:

MemberExpression:
PrimaryExpression
MemberExpression[Expression]
MemberExpression.Identifier
new MemberExpression Arguments

new NewExpression

The production NewExpression : new NewExpression is evaluated as follows:

1.   Evaluate NewExpression.
2.   Call GetValue(Result(1)).
3.   If Type(Result(2)) is not Object, generate a runtime error.
4.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
5.   Call the [[Construct]] method on Result(2), providing no arguments
(that is, an empty list of arguments).
6.   If Type(Result(5)) is not Object, generate a runtime error.
7.   Return Result(5).

The production MemberExpression : new MemberExpression Arguments is evaluated as follows:

1.   Evaluate MemberExpression.
2.   Call GetValue(Result(1)).
3.   Evaluate Arguments, producing an internal list of argument values
(section 0).
4.   If Type(Result(2)) is not Object, generate a runtime error.
5.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
6.   Call the [[Construct]] method on Result(2), providing the list
Result(3) as the argument values.
7.   If Type(Result(6)) is not Object, generate a runtime error.
8    .Return Result(6).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_2__6__n: function() {
var SECTION = "11.2.2-6-n.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The new operator";

//writeHeaderToLog( SECTION + " "+ TITLE);

var BOOLEAN  = true;
DESCRIPTION = "BOOLEAN = true; var b = new BOOLEAN()";
EXPECTED = "error";

this.TestCase( SECTION,
"BOOLEAN = true; var b = new BOOLEAN()",
"error",
eval("b = new BOOLEAN()") );

//test();

function TestFunction() {
return arguments;
}

},

/**
File Name:          11.2.2-6-n.js
ECMA Section:       11.2.2. The new operator
Description:

MemberExpression:
PrimaryExpression
MemberExpression[Expression]
MemberExpression.Identifier
new MemberExpression Arguments

new NewExpression

The production NewExpression : new NewExpression is evaluated as follows:

1.   Evaluate NewExpression.
2.   Call GetValue(Result(1)).
3.   If Type(Result(2)) is not Object, generate a runtime error.
4.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
5.   Call the [[Construct]] method on Result(2), providing no arguments
(that is, an empty list of arguments).
6.   If Type(Result(5)) is not Object, generate a runtime error.
7.   Return Result(5).

The production MemberExpression : new MemberExpression Arguments is evaluated as follows:

1.   Evaluate MemberExpression.
2.   Call GetValue(Result(1)).
3.   Evaluate Arguments, producing an internal list of argument values
(section 0).
4.   If Type(Result(2)) is not Object, generate a runtime error.
5.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
6.   Call the [[Construct]] method on Result(2), providing the list
Result(3) as the argument values.
7.   If Type(Result(6)) is not Object, generate a runtime error.
8    .Return Result(6).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_2__6__n_WORKS: function() {
var SECTION = "11.2.2-6-n.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The new operator";

//writeHeaderToLog( SECTION + " "+ TITLE);

var BOOLEAN  = true;
DESCRIPTION = "BOOLEAN = true; var b = new BOOLEAN()";
EXPECTED = "error";

try {
this.TestCase( SECTION,
"BOOLEAN = true; var b = new BOOLEAN()",
"error",
eval("b = new BOOLEAN()") );
fail();
} catch (e) {
assertTrue(e.name == 'TypeError');
assertTrue(e.message == 'true is not a function, it is boolean.');
}

//test();

function TestFunction() {
return arguments;
}

},

/**
File Name:          11.2.2-6-n.js
ECMA Section:       11.2.2. The new operator
Description:

MemberExpression:
PrimaryExpression
MemberExpression[Expression]
MemberExpression.Identifier
new MemberExpression Arguments

new NewExpression

The production NewExpression : new NewExpression is evaluated as follows:

1.   Evaluate NewExpression.
2.   Call GetValue(Result(1)).
3.   If Type(Result(2)) is not Object, generate a runtime error.
4.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
5.   Call the [[Construct]] method on Result(2), providing no arguments
(that is, an empty list of arguments).
6.   If Type(Result(5)) is not Object, generate a runtime error.
7.   Return Result(5).

The production MemberExpression : new MemberExpression Arguments is evaluated as follows:

1.   Evaluate MemberExpression.
2.   Call GetValue(Result(1)).
3.   Evaluate Arguments, producing an internal list of argument values
(section 0).
4.   If Type(Result(2)) is not Object, generate a runtime error.
5.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
6.   Call the [[Construct]] method on Result(2), providing the list
Result(3) as the argument values.
7.   If Type(Result(6)) is not Object, generate a runtime error.
8    .Return Result(6).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_2__7__n: function() {
var SECTION = "11.2.2-6-n.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The new operator";

//writeHeaderToLog( SECTION + " "+ TITLE);

var STRING = new String("hi");

DESCRIPTION = "var STRING = new String('hi'); var s = new STRING()";
EXPECTED = "error";

this.TestCase( SECTION,
"var STRING = new String('hi'); var s = new STRING()",
"error",
eval("s = new STRING()") );

//test();

function TestFunction() {
return arguments;
}

},

/**
File Name:          11.2.2-6-n.js
ECMA Section:       11.2.2. The new operator
Description:

MemberExpression:
PrimaryExpression
MemberExpression[Expression]
MemberExpression.Identifier
new MemberExpression Arguments

new NewExpression

The production NewExpression : new NewExpression is evaluated as follows:

1.   Evaluate NewExpression.
2.   Call GetValue(Result(1)).
3.   If Type(Result(2)) is not Object, generate a runtime error.
4.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
5.   Call the [[Construct]] method on Result(2), providing no arguments
(that is, an empty list of arguments).
6.   If Type(Result(5)) is not Object, generate a runtime error.
7.   Return Result(5).

The production MemberExpression : new MemberExpression Arguments is evaluated as follows:

1.   Evaluate MemberExpression.
2.   Call GetValue(Result(1)).
3.   Evaluate Arguments, producing an internal list of argument values
(section 0).
4.   If Type(Result(2)) is not Object, generate a runtime error.
5.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
6.   Call the [[Construct]] method on Result(2), providing the list
Result(3) as the argument values.
7.   If Type(Result(6)) is not Object, generate a runtime error.
8    .Return Result(6).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_2__7__n_WORKS: function() {
var SECTION = "11.2.2-6-n.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The new operator";

//writeHeaderToLog( SECTION + " "+ TITLE);

var STRING = new String("hi");

DESCRIPTION = "var STRING = new String('hi'); var s = new STRING()";
EXPECTED = "error";

try {
this.TestCase( SECTION,
"var STRING = new String('hi'); var s = new STRING()",
"error",
eval("s = new STRING()") );
fail();
} catch (e) {
assertTrue(e.name == 'TypeError');
assertTrue(e.message.indexOf('is not a function, it is object.') >= 0);
}

//test();

function TestFunction() {
return arguments;
}

},

/**
File Name:          11.2.2-8-n.js
ECMA Section:       11.2.2. The new operator
Description:

MemberExpression:
PrimaryExpression
MemberExpression[Expression]
MemberExpression.Identifier
new MemberExpression Arguments

new NewExpression

The production NewExpression : new NewExpression is evaluated as follows:

1.   Evaluate NewExpression.
2.   Call GetValue(Result(1)).
3.   If Type(Result(2)) is not Object, generate a runtime error.
4.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
5.   Call the [[Construct]] method on Result(2), providing no arguments
(that is, an empty list of arguments).
6.   If Type(Result(5)) is not Object, generate a runtime error.
7.   Return Result(5).

The production MemberExpression : new MemberExpression Arguments is evaluated as follows:

1.   Evaluate MemberExpression.
2.   Call GetValue(Result(1)).
3.   Evaluate Arguments, producing an internal list of argument values
(section 0).
4.   If Type(Result(2)) is not Object, generate a runtime error.
5.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
6.   Call the [[Construct]] method on Result(2), providing the list
Result(3) as the argument values.
7.   If Type(Result(6)) is not Object, generate a runtime error.
8    .Return Result(6).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_2__8__n: function() {
var SECTION = "11.2.2-8-n.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The new operator";

//writeHeaderToLog( SECTION + " "+ TITLE);

var NUMBER = new Number(1);

DESCRIPTION = "var NUMBER = new Number(1); var n = new NUMBER()";
EXPECTED = "error";

this.TestCase( SECTION,
"var NUMBER = new Number(1); var n = new NUMBER()",
"error",
eval("n = new NUMBER()") );

//test();

function TestFunction() {
return arguments;
}

},

/**
File Name:          11.2.2-8-n.js
ECMA Section:       11.2.2. The new operator
Description:

MemberExpression:
PrimaryExpression
MemberExpression[Expression]
MemberExpression.Identifier
new MemberExpression Arguments

new NewExpression

The production NewExpression : new NewExpression is evaluated as follows:

1.   Evaluate NewExpression.
2.   Call GetValue(Result(1)).
3.   If Type(Result(2)) is not Object, generate a runtime error.
4.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
5.   Call the [[Construct]] method on Result(2), providing no arguments
(that is, an empty list of arguments).
6.   If Type(Result(5)) is not Object, generate a runtime error.
7.   Return Result(5).

The production MemberExpression : new MemberExpression Arguments is evaluated as follows:

1.   Evaluate MemberExpression.
2.   Call GetValue(Result(1)).
3.   Evaluate Arguments, producing an internal list of argument values
(section 0).
4.   If Type(Result(2)) is not Object, generate a runtime error.
5.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
6.   Call the [[Construct]] method on Result(2), providing the list
Result(3) as the argument values.
7.   If Type(Result(6)) is not Object, generate a runtime error.
8    .Return Result(6).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_2__8__n_WORKS: function() {
var SECTION = "11.2.2-8-n.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The new operator";

//writeHeaderToLog( SECTION + " "+ TITLE);

var NUMBER = new Number(1);

DESCRIPTION = "var NUMBER = new Number(1); var n = new NUMBER()";
EXPECTED = "error";

try {
this.TestCase( SECTION,
"var NUMBER = new Number(1); var n = new NUMBER()",
"error",
eval("n = new NUMBER()") );
fail();
} catch (e) {
assertTrue(e.name == 'TypeError');
assertTrue(e.message.indexOf('is not a function, it is object.') >= 0);
}

//test();

function TestFunction() {
return arguments;
}

},

/**
File Name:          11.2.2-9-n.js
ECMA Section:       11.2.2. The new operator
Description:

MemberExpression:
PrimaryExpression
MemberExpression[Expression]
MemberExpression.Identifier
new MemberExpression Arguments

new NewExpression

The production NewExpression : new NewExpression is evaluated as follows:

1.   Evaluate NewExpression.
2.   Call GetValue(Result(1)).
3.   If Type(Result(2)) is not Object, generate a runtime error.
4.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
5.   Call the [[Construct]] method on Result(2), providing no arguments
(that is, an empty list of arguments).
6.   If Type(Result(5)) is not Object, generate a runtime error.
7.   Return Result(5).

The production MemberExpression : new MemberExpression Arguments is evaluated as follows:

1.   Evaluate MemberExpression.
2.   Call GetValue(Result(1)).
3.   Evaluate Arguments, producing an internal list of argument values
(section 0).
4.   If Type(Result(2)) is not Object, generate a runtime error.
5.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
6.   Call the [[Construct]] method on Result(2), providing the list
Result(3) as the argument values.
7.   If Type(Result(6)) is not Object, generate a runtime error.
8    .Return Result(6).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_2__9__n:function() {
var SECTION = "11.2.2-9-n.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The new operator";

//writeHeaderToLog( SECTION + " "+ TITLE);

var BOOLEAN = new Boolean();

DESCRIPTION = "var BOOLEAN = new Boolean(); var b = new BOOLEAN()";
EXPECTED = "error";

this.TestCase( SECTION,
"var BOOLEAN = new Boolean(); var b = new BOOLEAN()",
"error",
eval("b = new BOOLEAN()") );

//test();

function TestFunction() {
return arguments;
}

},

/**
File Name:          11.2.2-9-n.js
ECMA Section:       11.2.2. The new operator
Description:

MemberExpression:
PrimaryExpression
MemberExpression[Expression]
MemberExpression.Identifier
new MemberExpression Arguments

new NewExpression

The production NewExpression : new NewExpression is evaluated as follows:

1.   Evaluate NewExpression.
2.   Call GetValue(Result(1)).
3.   If Type(Result(2)) is not Object, generate a runtime error.
4.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
5.   Call the [[Construct]] method on Result(2), providing no arguments
(that is, an empty list of arguments).
6.   If Type(Result(5)) is not Object, generate a runtime error.
7.   Return Result(5).

The production MemberExpression : new MemberExpression Arguments is evaluated as follows:

1.   Evaluate MemberExpression.
2.   Call GetValue(Result(1)).
3.   Evaluate Arguments, producing an internal list of argument values
(section 0).
4.   If Type(Result(2)) is not Object, generate a runtime error.
5.   If Result(2) does not implement the internal [[Construct]] method,
generate a runtime error.
6.   Call the [[Construct]] method on Result(2), providing the list
Result(3) as the argument values.
7.   If Type(Result(6)) is not Object, generate a runtime error.
8    .Return Result(6).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_2__9__n_WORKS:function() {
var SECTION = "11.2.2-9-n.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The new operator";

//writeHeaderToLog( SECTION + " "+ TITLE);

var BOOLEAN = new Boolean();

DESCRIPTION = "var BOOLEAN = new Boolean(); var b = new BOOLEAN()";
EXPECTED = "error";

try {
this.TestCase( SECTION,
"var BOOLEAN = new Boolean(); var b = new BOOLEAN()",
"error",
eval("b = new BOOLEAN()") );
fail();
} catch (e) {
assertTrue(e.name == 'TypeError');
assertTrue(e.message.indexOf("NativeBoolean") >= 0);
assertTrue(e.message.indexOf("is not a function, it is object.") >= 0);
}

//test();

function TestFunction() {
return arguments;
}

},

/**
File Name:          11.2.3-1.js
ECMA Section:       11.2.3. Function Calls
Description:

The production CallExpression : MemberExpression Arguments is evaluated as
follows:

1.Evaluate MemberExpression.
2.Evaluate Arguments, producing an internal list of argument values
(section 0).
3.Call GetValue(Result(1)).
4.If Type(Result(3)) is not Object, generate a runtime error.
5.If Result(3) does not implement the internal [[Call]] method, generate a
runtime error.
6.If Type(Result(1)) is Reference, Result(6) is GetBase(Result(1)). Otherwise,
Result(6) is null.
7.If Result(6) is an activation object, Result(7) is null. Otherwise, Result(7) is
the same as Result(6).
8.Call the [[Call]] method on Result(3), providing Result(7) as the this value
and providing the list Result(2) as the argument values.
9.Return Result(8).

The production CallExpression : CallExpression Arguments is evaluated in
exactly the same manner, except that the contained CallExpression is
evaluated in step 1.

Note: Result(8) will never be of type Reference if Result(3) is a native
ECMAScript object. Whether calling a host object can return a value of
type Reference is implementation-dependent.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_3__1: function() {
var SECTION = "11.2.3-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Function Calls";

//writeHeaderToLog( SECTION + " "+ TITLE);

/*  this.eval() is no longer legal syntax.
// MemberExpression : this

new TestCase( SECTION,
"this.eval()",
void 0,
this.eval() );

new TestCase( SECTION,
"this.eval('NaN')",
NaN,
this.eval("NaN") );
*/
// MemberExpression:  Identifier

var OBJECT = true;

this.TestCase( SECTION,
"OBJECT.toString()",
"true",
OBJECT.toString() );

// MemberExpression[ Expression]

this.TestCase( SECTION,
"(new Array())['length'].valueOf()",
0,
(new Array())["length"].valueOf() );

// MemberExpression . Identifier
this.TestCase( SECTION,
"(new Array()).length.valueOf()",
0,
(new Array()).length.valueOf() );
// new MemberExpression Arguments

this.TestCase( SECTION,
"(new Array(20))['length'].valueOf()",
20,
(new Array(20))["length"].valueOf() );

//test();

},

/**
File Name:          11.2.3-2-n.js
ECMA Section:       11.2.3. Function Calls
Description:

The production CallExpression : MemberExpression Arguments is evaluated as
follows:

1.Evaluate MemberExpression.
2.Evaluate Arguments, producing an internal list of argument values
(section 0).
3.Call GetValue(Result(1)).
4.If Type(Result(3)) is not Object, generate a runtime error.
5.If Result(3) does not implement the internal [[Call]] method, generate a
runtime error.
6.If Type(Result(1)) is Reference, Result(6) is GetBase(Result(1)). Otherwise,
Result(6) is null.
7.If Result(6) is an activation object, Result(7) is null. Otherwise, Result(7) is
the same as Result(6).
8.Call the [[Call]] method on Result(3), providing Result(7) as the this value
and providing the list Result(2) as the argument values.
9.Return Result(8).

The production CallExpression : CallExpression Arguments is evaluated in
exactly the same manner, except that the contained CallExpression is
evaluated in step 1.

Note: Result(8) will never be of type Reference if Result(3) is a native
ECMAScript object. Whether calling a host object can return a value of
type Reference is implementation-dependent.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_3__2__n: function() {
var SECTION = "11.2.3-2-n.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Function Calls";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"3.valueOf()",
3,
eval("3.valueOf()") );

this.TestCase( SECTION,
"(3).valueOf()",
3,
eval("(3).valueOf()") );

//test();

},

/**
File Name:          11.2.3-2-n.js
ECMA Section:       11.2.3. Function Calls
Description:

The production CallExpression : MemberExpression Arguments is evaluated as
follows:

1.Evaluate MemberExpression.
2.Evaluate Arguments, producing an internal list of argument values
(section 0).
3.Call GetValue(Result(1)).
4.If Type(Result(3)) is not Object, generate a runtime error.
5.If Result(3) does not implement the internal [[Call]] method, generate a
runtime error.
6.If Type(Result(1)) is Reference, Result(6) is GetBase(Result(1)). Otherwise,
Result(6) is null.
7.If Result(6) is an activation object, Result(7) is null. Otherwise, Result(7) is
the same as Result(6).
8.Call the [[Call]] method on Result(3), providing Result(7) as the this value
and providing the list Result(2) as the argument values.
9.Return Result(8).

The production CallExpression : CallExpression Arguments is evaluated in
exactly the same manner, except that the contained CallExpression is
evaluated in step 1.

Note: Result(8) will never be of type Reference if Result(3) is a native
ECMAScript object. Whether calling a host object can return a value of
type Reference is implementation-dependent.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_3__2__n_WORKS: function() {
var SECTION = "11.2.3-2-n.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Function Calls";

//writeHeaderToLog( SECTION + " "+ TITLE);

try {
this.TestCase( SECTION,
"3.valueOf()",
3,
eval("3.valueOf()") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing ; before statement');
}

this.TestCase( SECTION,
"(3).valueOf()",
3,
eval("(3).valueOf()") );

//test();

},

/**
File Name:          11.2.3-3-n.js
ECMA Section:       11.2.3. Function Calls
Description:

The production CallExpression : MemberExpression Arguments is evaluated as
follows:

1.Evaluate MemberExpression.
2.Evaluate Arguments, producing an internal list of argument values
(section 0).
3.Call GetValue(Result(1)).
4.If Type(Result(3)) is not Object, generate a runtime error.
5.If Result(3) does not implement the internal [[Call]] method, generate a
runtime error.
6.If Type(Result(1)) is Reference, Result(6) is GetBase(Result(1)). Otherwise,
Result(6) is null.
7.If Result(6) is an activation object, Result(7) is null. Otherwise, Result(7) is
the same as Result(6).
8.Call the [[Call]] method on Result(3), providing Result(7) as the this value
and providing the list Result(2) as the argument values.
9.Return Result(8).

The production CallExpression : CallExpression Arguments is evaluated in
exactly the same manner, except that the contained CallExpression is
evaluated in step 1.

Note: Result(8) will never be of type Reference if Result(3) is a native
ECMAScript object. Whether calling a host object can return a value of
type Reference is implementation-dependent.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_3__3__n: function() {
var SECTION = "11.2.3-3-n.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Function Calls";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "(void 0).valueOf()";
EXPECTED = "error";

this.TestCase( SECTION,
"(void 0).valueOf()",
"error",
eval("(void 0).valueOf()") );

//test();

},

/**
File Name:          11.2.3-3-n.js
ECMA Section:       11.2.3. Function Calls
Description:

The production CallExpression : MemberExpression Arguments is evaluated as
follows:

1.Evaluate MemberExpression.
2.Evaluate Arguments, producing an internal list of argument values
(section 0).
3.Call GetValue(Result(1)).
4.If Type(Result(3)) is not Object, generate a runtime error.
5.If Result(3) does not implement the internal [[Call]] method, generate a
runtime error.
6.If Type(Result(1)) is Reference, Result(6) is GetBase(Result(1)). Otherwise,
Result(6) is null.
7.If Result(6) is an activation object, Result(7) is null. Otherwise, Result(7) is
the same as Result(6).
8.Call the [[Call]] method on Result(3), providing Result(7) as the this value
and providing the list Result(2) as the argument values.
9.Return Result(8).

The production CallExpression : CallExpression Arguments is evaluated in
exactly the same manner, except that the contained CallExpression is
evaluated in step 1.

Note: Result(8) will never be of type Reference if Result(3) is a native
ECMAScript object. Whether calling a host object can return a value of
type Reference is implementation-dependent.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_3__3__n_WORKS: function() {
var SECTION = "11.2.3-3-n.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Function Calls";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "(void 0).valueOf()";
EXPECTED = "error";

try {
this.TestCase( SECTION,
"(void 0).valueOf()",
"error",
eval("(void 0).valueOf()") );
fail();
} catch (e) {
assertTrue(e.name == 'TypeError');
assertTrue(e.message == 'Cannot call method "valueOf" of undefined');
}

//test();

},

/**
File Name:          11.2.3-4-n.js
ECMA Section:       11.2.3. Function Calls
Description:

The production CallExpression : MemberExpression Arguments is evaluated as
follows:

1.Evaluate MemberExpression.
2.Evaluate Arguments, producing an internal list of argument values
(section 0).
3.Call GetValue(Result(1)).
4.If Type(Result(3)) is not Object, generate a runtime error.
5.If Result(3) does not implement the internal [[Call]] method, generate a
runtime error.
6.If Type(Result(1)) is Reference, Result(6) is GetBase(Result(1)). Otherwise,
Result(6) is null.
7.If Result(6) is an activation object, Result(7) is null. Otherwise, Result(7) is
the same as Result(6).
8.Call the [[Call]] method on Result(3), providing Result(7) as the this value
and providing the list Result(2) as the argument values.
9.Return Result(8).

The production CallExpression : CallExpression Arguments is evaluated in
exactly the same manner, except that the contained CallExpression is
evaluated in step 1.

Note: Result(8) will never be of type Reference if Result(3) is a native
ECMAScript object. Whether calling a host object can return a value of
type Reference is implementation-dependent.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_3__4__n: function() {
var SECTION = "11.2.3-4-n.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Function Calls";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "null.valueOf()";
EXPECTED = "error";

this.TestCase( SECTION,
"null.valueOf()",
"error",
eval("null.valueOf()") );

//test();

},

/**
File Name:          11.2.3-4-n.js
ECMA Section:       11.2.3. Function Calls
Description:

The production CallExpression : MemberExpression Arguments is evaluated as
follows:

1.Evaluate MemberExpression.
2.Evaluate Arguments, producing an internal list of argument values
(section 0).
3.Call GetValue(Result(1)).
4.If Type(Result(3)) is not Object, generate a runtime error.
5.If Result(3) does not implement the internal [[Call]] method, generate a
runtime error.
6.If Type(Result(1)) is Reference, Result(6) is GetBase(Result(1)). Otherwise,
Result(6) is null.
7.If Result(6) is an activation object, Result(7) is null. Otherwise, Result(7) is
the same as Result(6).
8.Call the [[Call]] method on Result(3), providing Result(7) as the this value
and providing the list Result(2) as the argument values.
9.Return Result(8).

The production CallExpression : CallExpression Arguments is evaluated in
exactly the same manner, except that the contained CallExpression is
evaluated in step 1.

Note: Result(8) will never be of type Reference if Result(3) is a native
ECMAScript object. Whether calling a host object can return a value of
type Reference is implementation-dependent.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_3__4__n_WORKS: function() {
var SECTION = "11.2.3-4-n.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Function Calls";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "null.valueOf()";
EXPECTED = "error";

try {
this.TestCase( SECTION,
"null.valueOf()",
"error",
eval("null.valueOf()") );
fail();
} catch (e) {
assertTrue(e.name == 'TypeError');
assertTrue(e.message == 'Cannot call method "valueOf" of null');
}

//test();

},

/**
File Name:          11.2.3-5-n.js
ECMA Section:       11.2.3. Function Calls
Description:

The production CallExpression : MemberExpression Arguments is evaluated as
follows:

1.  Evaluate MemberExpression.
2.  Evaluate Arguments, producing an internal list of argument values
(section 0).
3.  Call GetValue(Result(1)).
4.  If Type(Result(3)) is not Object, generate a runtime error.
5.  If Result(3) does not implement the internal [[Call]] method, generate a
runtime error.
6.  If Type(Result(1)) is Reference, Result(6) is GetBase(Result(1)). Otherwise,
Result(6) is null.
7.  If Result(6) is an activation object, Result(7) is null. Otherwise, Result(7) is
the same as Result(6).
8.  Call the [[Call]] method on Result(3), providing Result(7) as the this value
and providing the list Result(2) as the argument values.
9.  Return Result(8).

The production CallExpression : CallExpression Arguments is evaluated in
exactly the same manner, except that the contained CallExpression is
evaluated in step 1.

Note: Result(8) will never be of type Reference if Result(3) is a native
ECMAScript object. Whether calling a host object can return a value of
type Reference is implementation-dependent.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_2_3__5: function() {
var SECTION = "11.2.3-5";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Function Calls";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION, "true.valueOf()", true, true.valueOf() );

//test();

},

/**
File Name:          11.3.1.js
ECMA Section:       11.3.1 Postfix increment operator
Description:
The production MemberExpression : MemberExpression ++ is evaluated as
follows:

1.  Evaluate MemberExpression.
2.  Call GetValue(Result(1)).
3.  Call ToNumber(Result(2)).
4.  Add the value 1 to Result(3), using the same rules as for the +
operator (section 0).
5.  Call PutValue(Result(1), Result(4)).
6.  Return Result(3).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_3_1: function() {
var SECTION = "11.3.1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Postfix increment operator");

// special numbers
this.TestCase( SECTION,  "var MYVAR; MYVAR++",                       NaN,                            eval("var MYVAR; MYVAR++") );
this.TestCase( SECTION,  "var MYVAR= void 0; MYVAR++",               NaN,                            eval("var MYVAR=void 0; MYVAR++") );
this.TestCase( SECTION,  "var MYVAR=null; MYVAR++",                  0,                            eval("var MYVAR=null; MYVAR++") );
this.TestCase( SECTION,  "var MYVAR=true; MYVAR++",                  1,                            eval("var MYVAR=true; MYVAR++") );
this.TestCase( SECTION,  "var MYVAR=false; MYVAR++",                 0,                            eval("var MYVAR=false; MYVAR++") );

// verify return value

this.TestCase( SECTION,    "var MYVAR=Number.POSITIVE_INFINITY;MYVAR++", Number.POSITIVE_INFINITY,   eval("var MYVAR=Number.POSITIVE_INFINITY;MYVAR++") );
this.TestCase( SECTION,    "var MYVAR=Number.NEGATIVE_INFINITY;MYVAR++", Number.NEGATIVE_INFINITY,   eval("var MYVAR=Number.NEGATIVE_INFINITY;MYVAR++") );
this.TestCase( SECTION,    "var MYVAR=Number.NaN;MYVAR++",               Number.NaN,                 eval("var MYVAR=Number.NaN;MYVAR++") );

// verify value of variable

this.TestCase( SECTION,    "var MYVAR=Number.POSITIVE_INFINITY;MYVAR++;MYVAR", Number.POSITIVE_INFINITY,   eval("var MYVAR=Number.POSITIVE_INFINITY;MYVAR++;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=Number.NEGATIVE_INFINITY;MYVAR++;MYVAR", Number.NEGATIVE_INFINITY,   eval("var MYVAR=Number.NEGATIVE_INFINITY;MYVAR++;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=Number.NaN;MYVAR++;MYVAR",               Number.NaN,                 eval("var MYVAR=Number.NaN;MYVAR++;MYVAR") );

// number primitives
this.TestCase( SECTION,    "var MYVAR=0;MYVAR++",            0,          eval("var MYVAR=0;MYVAR++") );
this.TestCase( SECTION,    "var MYVAR=0.2345;MYVAR++",       0.2345,     eval("var MYVAR=0.2345;MYVAR++") );
this.TestCase( SECTION,    "var MYVAR=-0.2345;MYVAR++",      -0.2345,     eval("var MYVAR=-0.2345;MYVAR++") );

// verify value of variable

this.TestCase( SECTION,    "var MYVAR=0;MYVAR++;MYVAR",      1,          eval("var MYVAR=0;MYVAR++;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=0.2345;MYVAR++;MYVAR", 1.2345,     eval("var MYVAR=0.2345;MYVAR++;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=-0.2345;MYVAR++;MYVAR", 0.7655,   eval("var MYVAR=-0.2345;MYVAR++;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=0;MYVAR++;MYVAR",      1,   eval("var MYVAR=0;MYVAR++;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=0;MYVAR++;MYVAR",      1,   eval("var MYVAR=0;MYVAR++;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=0;MYVAR++;MYVAR",      1,   eval("var MYVAR=0;MYVAR++;MYVAR") );

// boolean values
// verify return value

this.TestCase( SECTION,    "var MYVAR=true;MYVAR++",         1,       eval("var MYVAR=true;MYVAR++") );
this.TestCase( SECTION,    "var MYVAR=false;MYVAR++",        0,      eval("var MYVAR=false;MYVAR++") );
// verify value of variable

this.TestCase( SECTION,    "var MYVAR=true;MYVAR++;MYVAR",   2,   eval("var MYVAR=true;MYVAR++;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=false;MYVAR++;MYVAR",  1,   eval("var MYVAR=false;MYVAR++;MYVAR") );

// boolean objects
// verify return value

this.TestCase( SECTION,    "var MYVAR=new Boolean(true);MYVAR++",         1,     eval("var MYVAR=true;MYVAR++") );
this.TestCase( SECTION,    "var MYVAR=new Boolean(false);MYVAR++",        0,     eval("var MYVAR=false;MYVAR++") );
// verify value of variable

this.TestCase( SECTION,    "var MYVAR=new Boolean(true);MYVAR++;MYVAR",   2,     eval("var MYVAR=new Boolean(true);MYVAR++;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new Boolean(false);MYVAR++;MYVAR",  1,     eval("var MYVAR=new Boolean(false);MYVAR++;MYVAR") );

// string primitives
this.TestCase( SECTION,    "var MYVAR='string';MYVAR++",         Number.NaN,     eval("var MYVAR='string';MYVAR++") );
this.TestCase( SECTION,    "var MYVAR='12345';MYVAR++",          12345,          eval("var MYVAR='12345';MYVAR++") );
this.TestCase( SECTION,    "var MYVAR='-12345';MYVAR++",         -12345,         eval("var MYVAR='-12345';MYVAR++") );
this.TestCase( SECTION,    "var MYVAR='0Xf';MYVAR++",            15,             eval("var MYVAR='0Xf';MYVAR++") );
this.TestCase( SECTION,    "var MYVAR='077';MYVAR++",            77,             eval("var MYVAR='077';MYVAR++") );
this.TestCase( SECTION,    "var MYVAR=''; MYVAR++",              0,              eval("var MYVAR='';MYVAR++") );

// verify value of variable

this.TestCase( SECTION,    "var MYVAR='string';MYVAR++;MYVAR",   Number.NaN,     eval("var MYVAR='string';MYVAR++;MYVAR") );
this.TestCase( SECTION,    "var MYVAR='12345';MYVAR++;MYVAR",    12346,          eval("var MYVAR='12345';MYVAR++;MYVAR") );
this.TestCase( SECTION,    "var MYVAR='-12345';MYVAR++;MYVAR",   -12344,          eval("var MYVAR='-12345';MYVAR++;MYVAR") );
this.TestCase( SECTION,    "var MYVAR='0xf';MYVAR++;MYVAR",      16,             eval("var MYVAR='0xf';MYVAR++;MYVAR") );
this.TestCase( SECTION,    "var MYVAR='077';MYVAR++;MYVAR",      78,             eval("var MYVAR='077';MYVAR++;MYVAR") );
this.TestCase( SECTION,    "var MYVAR='';MYVAR++;MYVAR",         1,              eval("var MYVAR='';MYVAR++;MYVAR") );

// string objects
this.TestCase( SECTION,    "var MYVAR=new String('string');MYVAR++",         Number.NaN,     eval("var MYVAR=new String('string');MYVAR++") );
this.TestCase( SECTION,    "var MYVAR=new String('12345');MYVAR++",          12345,          eval("var MYVAR=new String('12345');MYVAR++") );
this.TestCase( SECTION,    "var MYVAR=new String('-12345');MYVAR++",         -12345,         eval("var MYVAR=new String('-12345');MYVAR++") );
this.TestCase( SECTION,    "var MYVAR=new String('0Xf');MYVAR++",            15,             eval("var MYVAR=new String('0Xf');MYVAR++") );
this.TestCase( SECTION,    "var MYVAR=new String('077');MYVAR++",            77,             eval("var MYVAR=new String('077');MYVAR++") );
this.TestCase( SECTION,    "var MYVAR=new String(''); MYVAR++",              0,              eval("var MYVAR=new String('');MYVAR++") );

// verify value of variable

this.TestCase( SECTION,    "var MYVAR=new String('string');MYVAR++;MYVAR",   Number.NaN,     eval("var MYVAR=new String('string');MYVAR++;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String('12345');MYVAR++;MYVAR",    12346,          eval("var MYVAR=new String('12345');MYVAR++;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String('-12345');MYVAR++;MYVAR",   -12344,          eval("var MYVAR=new String('-12345');MYVAR++;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String('0xf');MYVAR++;MYVAR",      16,             eval("var MYVAR=new String('0xf');MYVAR++;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String('077');MYVAR++;MYVAR",      78,             eval("var MYVAR=new String('077');MYVAR++;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String('');MYVAR++;MYVAR",         1,              eval("var MYVAR=new String('');MYVAR++;MYVAR") );

//test();

},

/**
File Name:          11.3.2.js
ECMA Section:       11.3.2 Postfix decrement operator
Description:

11.3.2 Postfix decrement operator

The production MemberExpression : MemberExpression -- is evaluated as follows:
1.  Evaluate MemberExpression.
2.  Call GetValue(Result(1)).
3.  Call ToNumber(Result(2)).
4.  Subtract the value 1 from Result(3), using the same rules as for the -
operator (section 0).
5.  Call PutValue(Result(1), Result(4)).
6.  Return Result(3).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_3_2: function() {
var SECTION = "11.3.2";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Postfix decrement operator");

// special numbers
this.TestCase( SECTION,  "var MYVAR; MYVAR--",                       NaN,                            eval("var MYVAR; MYVAR--") );
this.TestCase( SECTION,  "var MYVAR= void 0; MYVAR--",               NaN,                            eval("var MYVAR=void 0; MYVAR--") );
this.TestCase( SECTION,  "var MYVAR=null; MYVAR--",                  0,                            eval("var MYVAR=null; MYVAR--") );
this.TestCase( SECTION,  "var MYVAR=true; MYVAR--",                  1,                            eval("var MYVAR=true; MYVAR--") );
this.TestCase( SECTION,  "var MYVAR=false; MYVAR--",                 0,                            eval("var MYVAR=false; MYVAR--") );

// verify return value

this.TestCase( SECTION,    "var MYVAR=Number.POSITIVE_INFINITY;MYVAR--", Number.POSITIVE_INFINITY,   eval("var MYVAR=Number.POSITIVE_INFINITY;MYVAR--") );
this.TestCase( SECTION,    "var MYVAR=Number.NEGATIVE_INFINITY;MYVAR--", Number.NEGATIVE_INFINITY,   eval("var MYVAR=Number.NEGATIVE_INFINITY;MYVAR--") );
this.TestCase( SECTION,    "var MYVAR=Number.NaN;MYVAR--",               Number.NaN,                 eval("var MYVAR=Number.NaN;MYVAR--") );

// verify value of variable

this.TestCase( SECTION,    "var MYVAR=Number.POSITIVE_INFINITY;MYVAR--;MYVAR", Number.POSITIVE_INFINITY,   eval("var MYVAR=Number.POSITIVE_INFINITY;MYVAR--;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=Number.NEGATIVE_INFINITY;MYVAR--;MYVAR", Number.NEGATIVE_INFINITY,   eval("var MYVAR=Number.NEGATIVE_INFINITY;MYVAR--;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=Number.NaN;MYVAR--;MYVAR",               Number.NaN,                 eval("var MYVAR=Number.NaN;MYVAR--;MYVAR") );

// number primitives
this.TestCase( SECTION,    "var MYVAR=0;MYVAR--",            0,          eval("var MYVAR=0;MYVAR--") );
this.TestCase( SECTION,    "var MYVAR=0.2345;MYVAR--",       0.2345,     eval("var MYVAR=0.2345;MYVAR--") );
this.TestCase( SECTION,    "var MYVAR=-0.2345;MYVAR--",      -0.2345,    eval("var MYVAR=-0.2345;MYVAR--") );

// verify value of variable

this.TestCase( SECTION,    "var MYVAR=0;MYVAR--;MYVAR",      -1,         eval("var MYVAR=0;MYVAR--;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=0.2345;MYVAR--;MYVAR", -0.7655,    eval("var MYVAR=0.2345;MYVAR--;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=-0.2345;MYVAR--;MYVAR", -1.2345,   eval("var MYVAR=-0.2345;MYVAR--;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=0;MYVAR--;MYVAR",      -1,   eval("var MYVAR=0;MYVAR--;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=0;MYVAR--;MYVAR",      -1,   eval("var MYVAR=0;MYVAR--;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=0;MYVAR--;MYVAR",      -1,   eval("var MYVAR=0;MYVAR--;MYVAR") );

// boolean values
// verify return value

this.TestCase( SECTION,    "var MYVAR=true;MYVAR--",         1,       eval("var MYVAR=true;MYVAR--") );
this.TestCase( SECTION,    "var MYVAR=false;MYVAR--",        0,      eval("var MYVAR=false;MYVAR--") );
// verify value of variable

this.TestCase( SECTION,    "var MYVAR=true;MYVAR--;MYVAR",   0,   eval("var MYVAR=true;MYVAR--;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=false;MYVAR--;MYVAR",  -1,   eval("var MYVAR=false;MYVAR--;MYVAR") );

// boolean objects
// verify return value

this.TestCase( SECTION,    "var MYVAR=new Boolean(true);MYVAR--",         1,     eval("var MYVAR=true;MYVAR--") );
this.TestCase( SECTION,    "var MYVAR=new Boolean(false);MYVAR--",        0,     eval("var MYVAR=false;MYVAR--") );
// verify value of variable

this.TestCase( SECTION,    "var MYVAR=new Boolean(true);MYVAR--;MYVAR",   0,     eval("var MYVAR=new Boolean(true);MYVAR--;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new Boolean(false);MYVAR--;MYVAR",  -1,     eval("var MYVAR=new Boolean(false);MYVAR--;MYVAR") );

// string primitives
this.TestCase( SECTION,    "var MYVAR='string';MYVAR--",         Number.NaN,     eval("var MYVAR='string';MYVAR--") );
this.TestCase( SECTION,    "var MYVAR='12345';MYVAR--",          12345,          eval("var MYVAR='12345';MYVAR--") );
this.TestCase( SECTION,    "var MYVAR='-12345';MYVAR--",         -12345,         eval("var MYVAR='-12345';MYVAR--") );
this.TestCase( SECTION,    "var MYVAR='0Xf';MYVAR--",            15,             eval("var MYVAR='0Xf';MYVAR--") );
this.TestCase( SECTION,    "var MYVAR='077';MYVAR--",            77,             eval("var MYVAR='077';MYVAR--") );
this.TestCase( SECTION,    "var MYVAR=''; MYVAR--",              0,              eval("var MYVAR='';MYVAR--") );

// verify value of variable

this.TestCase( SECTION,    "var MYVAR='string';MYVAR--;MYVAR",   Number.NaN,     eval("var MYVAR='string';MYVAR--;MYVAR") );
this.TestCase( SECTION,    "var MYVAR='12345';MYVAR--;MYVAR",    12344,          eval("var MYVAR='12345';MYVAR--;MYVAR") );
this.TestCase( SECTION,    "var MYVAR='-12345';MYVAR--;MYVAR",   -12346,          eval("var MYVAR='-12345';MYVAR--;MYVAR") );
this.TestCase( SECTION,    "var MYVAR='0xf';MYVAR--;MYVAR",      14,             eval("var MYVAR='0xf';MYVAR--;MYVAR") );
this.TestCase( SECTION,    "var MYVAR='077';MYVAR--;MYVAR",      76,             eval("var MYVAR='077';MYVAR--;MYVAR") );
this.TestCase( SECTION,    "var MYVAR='';MYVAR--;MYVAR",         -1,              eval("var MYVAR='';MYVAR--;MYVAR") );

// string objects
this.TestCase( SECTION,    "var MYVAR=new String('string');MYVAR--",         Number.NaN,     eval("var MYVAR=new String('string');MYVAR--") );
this.TestCase( SECTION,    "var MYVAR=new String('12345');MYVAR--",          12345,          eval("var MYVAR=new String('12345');MYVAR--") );
this.TestCase( SECTION,    "var MYVAR=new String('-12345');MYVAR--",         -12345,         eval("var MYVAR=new String('-12345');MYVAR--") );
this.TestCase( SECTION,    "var MYVAR=new String('0Xf');MYVAR--",            15,             eval("var MYVAR=new String('0Xf');MYVAR--") );
this.TestCase( SECTION,    "var MYVAR=new String('077');MYVAR--",            77,             eval("var MYVAR=new String('077');MYVAR--") );
this.TestCase( SECTION,    "var MYVAR=new String(''); MYVAR--",              0,              eval("var MYVAR=new String('');MYVAR--") );

// verify value of variable

this.TestCase( SECTION,    "var MYVAR=new String('string');MYVAR--;MYVAR",   Number.NaN,     eval("var MYVAR=new String('string');MYVAR--;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String('12345');MYVAR--;MYVAR",    12344,          eval("var MYVAR=new String('12345');MYVAR--;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String('-12345');MYVAR--;MYVAR",   -12346,          eval("var MYVAR=new String('-12345');MYVAR--;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String('0xf');MYVAR--;MYVAR",      14,             eval("var MYVAR=new String('0xf');MYVAR--;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String('077');MYVAR--;MYVAR",      76,             eval("var MYVAR=new String('077');MYVAR--;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String('');MYVAR--;MYVAR",         -1,              eval("var MYVAR=new String('');MYVAR--;MYVAR") );

//test();

},

/**
File Name:          11.4.1.js
ECMA Section:       11.4.1 the Delete Operator
Description:        returns true if the property could be deleted
returns false if it could not be deleted
Author:             christine@netscape.com
Date:               7 july 1997

*/
test_11_4_1: function() {
var SECTION = "11.4.1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The delete operator";

//writeHeaderToLog( SECTION + " "+ TITLE);

//    new TestCase( SECTION,   "x=[9,8,7];delete(x[2]);x.length",         2,             eval("x=[9,8,7];delete(x[2]);x.length") );
//    new TestCase( SECTION,   "x=[9,8,7];delete(x[2]);x.toString()",     "9,8",         eval("x=[9,8,7];delete(x[2]);x.toString()") );
this.TestCase( SECTION,   "x=new Date();delete x;typeof(x)",        "undefined",    eval("x=new Date();delete x;typeof(x)") );

//    array[item++] = new TestCase( SECTION,   "delete(x=new Date())",        true,   delete(x=new Date()) );
//    array[item++] = new TestCase( SECTION,   "delete('string primitive')",   true,   delete("string primitive") );
//    array[item++] = new TestCase( SECTION,   "delete(new String( 'string object' ) )",  true,   delete(new String("string object")) );
//    array[item++] = new TestCase( SECTION,   "delete(new Number(12345) )",  true,   delete(new Number(12345)) );
this.TestCase( SECTION,   "delete(Math.PI)",             false,   delete(Math.PI) );
//    array[item++] = new TestCase( SECTION,   "delete(null)",                true,   delete(null) );
//    array[item++] = new TestCase( SECTION,   "delete(void(0))",             true,   delete(void(0)) );

// variables declared with the var statement are not deletable.

var abc;
this.TestCase( SECTION,   "var abc; delete(abc)",        false,   delete abc );

this.TestCase(   SECTION,
"var OB = new MyObject(); for ( p in OB ) { delete p }",
true,
eval("var OB = new MyObject(); for ( p in OB ) { delete p }") );

//test();

function MyObject() {
this.prop1 = true;
this.prop2 = false;
this.prop3 = null;
this.prop4 = void 0;
this.prop5 = "hi";
this.prop6 = 42;
this.prop7 = new Date();
this.prop8 = Math.PI;
}

},

/**
File Name:          11.4.2.js
ECMA Section:       11.4.2 the Void Operator
Description:        always returns undefined (?)
Author:             christine@netscape.com
Date:               7 july 1997

*/
test_11_4_2: function() {
var SECTION = "11.4.2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The void operator";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,   "void(new String('string object'))",      void 0,  void(new String( 'string object' )) );
this.TestCase( SECTION,   "void('string primitive')",               void 0,  void("string primitive") );
this.TestCase( SECTION,   "void(Number.NaN)",                       void 0,  void(Number.NaN) );
this.TestCase( SECTION,   "void(Number.POSITIVE_INFINITY)",         void 0,  void(Number.POSITIVE_INFINITY) );
this.TestCase( SECTION,   "void(1)",                                void 0,  void(1) );
this.TestCase( SECTION,   "void(0)",                                void 0,  void(0) );
this.TestCase( SECTION,   "void(-1)",                               void 0,  void(-1) );
this.TestCase( SECTION,   "void(Number.NEGATIVE_INFINITY)",         void 0,  void(Number.NEGATIVE_INFINITY) );
this.TestCase( SECTION,   "void(Math.PI)",                          void 0,  void(Math.PI) );
this.TestCase( SECTION,   "void(true)",                             void 0,  void(true) );
this.TestCase( SECTION,   "void(false)",                            void 0,  void(false) );
this.TestCase( SECTION,   "void(null)",                             void 0,  void(null) );
this.TestCase( SECTION,   "void new String('string object')",      void 0,  void new String( 'string object' ) );
this.TestCase( SECTION,   "void 'string primitive'",               void 0,  void "string primitive" );
this.TestCase( SECTION,   "void Number.NaN",                       void 0,  void Number.NaN );
this.TestCase( SECTION,   "void Number.POSITIVE_INFINITY",         void 0,  void Number.POSITIVE_INFINITY );
this.TestCase( SECTION,   "void 1",                                void 0,  void 1 );
this.TestCase( SECTION,   "void 0",                                void 0,  void 0 );
this.TestCase( SECTION,   "void -1",                               void 0,  void -1 );
this.TestCase( SECTION,   "void Number.NEGATIVE_INFINITY",         void 0,  void Number.NEGATIVE_INFINITY );
this.TestCase( SECTION,   "void Math.PI",                          void 0,  void Math.PI );
this.TestCase( SECTION,   "void true",                             void 0,  void true );
this.TestCase( SECTION,   "void false",                            void 0,  void false );
this.TestCase( SECTION,   "void null",                             void 0,  void null );

//     array[item++] = new TestCase( SECTION,   "void()",                                 void 0,  void() );

//test();

},

/**
File Name:          typeof_1.js
ECMA Section:       11.4.3 typeof operator
Description:        typeof evaluates unary expressions:
undefined   "undefined"
null        "object"
Boolean     "boolean"
Number      "number"
String      "string"
Object      "object" [native, doesn't implement Call]
Object      "function" [native, implements [Call]]
Object      implementation dependent
[not sure how to test this]
Author:             christine@netscape.com
Date:               june 30, 1997

*/
test_11_4_3: function() {
var SECTION = "11.4.3";

var VERSION = "ECMA_1";
//startTest();

var TITLE   = " The typeof operator";
//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,     "typeof(void(0))",              "undefined",        typeof(void(0)) );
this.TestCase( SECTION,     "typeof(null)",                 "object",           typeof(null) );
this.TestCase( SECTION,     "typeof(true)",                 "boolean",          typeof(true) );
this.TestCase( SECTION,     "typeof(false)",                "boolean",          typeof(false) );
this.TestCase( SECTION,     "typeof(new Boolean())",        "object",           typeof(new Boolean()) );
this.TestCase( SECTION,     "typeof(new Boolean(true))",    "object",           typeof(new Boolean(true)) );
this.TestCase( SECTION,     "typeof(Boolean())",            "boolean",          typeof(Boolean()) );
this.TestCase( SECTION,     "typeof(Boolean(false))",       "boolean",          typeof(Boolean(false)) );
this.TestCase( SECTION,     "typeof(Boolean(true))",        "boolean",          typeof(Boolean(true)) );
this.TestCase( SECTION,     "typeof(NaN)",                  "number",           typeof(Number.NaN) );
this.TestCase( SECTION,     "typeof(Infinity)",             "number",           typeof(Number.POSITIVE_INFINITY) );
this.TestCase( SECTION,     "typeof(-Infinity)",            "number",           typeof(Number.NEGATIVE_INFINITY) );
this.TestCase( SECTION,     "typeof(Math.PI)",              "number",           typeof(Math.PI) );
this.TestCase( SECTION,     "typeof(0)",                    "number",           typeof(0) );
this.TestCase( SECTION,     "typeof(1)",                    "number",           typeof(1) );
this.TestCase( SECTION,     "typeof(-1)",                   "number",           typeof(-1) );
this.TestCase( SECTION,     "typeof('0')",                  "string",           typeof("0") );
this.TestCase( SECTION,     "typeof(Number())",             "number",           typeof(Number()) );
this.TestCase( SECTION,     "typeof(Number(0))",            "number",           typeof(Number(0)) );
this.TestCase( SECTION,     "typeof(Number(1))",            "number",           typeof(Number(1)) );
this.TestCase( SECTION,     "typeof(Nubmer(-1))",           "number",           typeof(Number(-1)) );
this.TestCase( SECTION,     "typeof(new Number())",         "object",           typeof(new Number()) );
this.TestCase( SECTION,     "typeof(new Number(0))",        "object",           typeof(new Number(0)) );
this.TestCase( SECTION,     "typeof(new Number(1))",        "object",           typeof(new Number(1)) );

// Math does not implement [[Construct]] or [[Call]] so its type is object.

this.TestCase( SECTION,     "typeof(Math)",                 "object",         typeof(Math) );

this.TestCase( SECTION,     "typeof(Number.prototype.toString)", "function",    typeof(Number.prototype.toString) );

this.TestCase( SECTION,     "typeof('a string')",           "string",           typeof("a string") );
this.TestCase( SECTION,     "typeof('')",                   "string",           typeof("") );
this.TestCase( SECTION,     "typeof(new Date())",           "object",           typeof(new Date()) );
this.TestCase( SECTION,     "typeof(new Array(1,2,3))",     "object",           typeof(new Array(1,2,3)) );
this.TestCase( SECTION,     "typeof(new String('string object'))",  "object",   typeof(new String("string object")) );
this.TestCase( SECTION,     "typeof(String('string primitive'))",    "string",  typeof(String("string primitive")) );
this.TestCase( SECTION,     "typeof(['array', 'of', 'strings'])",   "object",   typeof(["array", "of", "strings"]) );
this.TestCase( SECTION,     "typeof(new Function())",                "function",     typeof( new Function() ) );
this.TestCase( SECTION,     "typeof(parseInt)",                      "function",     typeof( parseInt ) );
//test() removed so need for this
//      this.TestCase( SECTION,     "typeof(test)",                          "function",     typeof( test ) );
this.TestCase( SECTION,     "typeof(String.fromCharCode)",           "function",     typeof( String.fromCharCode )  );


//test();

},

/**
File Name:          11.4.4.js
ECMA Section:       11.4.4 Prefix increment operator
Description:
The production UnaryExpression : ++ UnaryExpression is evaluated as
follows:

1.  Evaluate UnaryExpression.
2.  Call GetValue(Result(1)).
3.  Call ToNumber(Result(2)).
4.  Add the value 1 to Result(3), using the same rules as for the +
operator (section 11.6.3).
5.  Call PutValue(Result(1), Result(4)).
6.  Return Result(4).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_4_4: function() {
var SECTION = "11.4.4";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Prefix increment operator");

//  special case:  var is not defined

this.TestCase( SECTION,  "var MYVAR; ++MYVAR",                       NaN,                            eval("var MYVAR; ++MYVAR") );
this.TestCase( SECTION,  "var MYVAR= void 0; ++MYVAR",               NaN,                            eval("var MYVAR=void 0; ++MYVAR") );
this.TestCase( SECTION,  "var MYVAR=null; ++MYVAR",                  1,                            eval("var MYVAR=null; ++MYVAR") );
this.TestCase( SECTION,  "var MYVAR=true; ++MYVAR",                  2,                            eval("var MYVAR=true; ++MYVAR") );
this.TestCase( SECTION,  "var MYVAR=false; ++MYVAR",                 1,                            eval("var MYVAR=false; ++MYVAR") );

// special numbers
// verify return value

this.TestCase( SECTION,    "var MYVAR=Number.POSITIVE_INFINITY;++MYVAR", Number.POSITIVE_INFINITY,   eval("var MYVAR=Number.POSITIVE_INFINITY;++MYVAR") );
this.TestCase( SECTION,    "var MYVAR=Number.NEGATIVE_INFINITY;++MYVAR", Number.NEGATIVE_INFINITY,   eval("var MYVAR=Number.NEGATIVE_INFINITY;++MYVAR") );
this.TestCase( SECTION,    "var MYVAR=Number.NaN;++MYVAR",               Number.NaN,                 eval("var MYVAR=Number.NaN;++MYVAR") );

// verify value of variable

this.TestCase( SECTION,    "var MYVAR=Number.POSITIVE_INFINITY;++MYVAR;MYVAR", Number.POSITIVE_INFINITY,   eval("var MYVAR=Number.POSITIVE_INFINITY;++MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=Number.NEGATIVE_INFINITY;++MYVAR;MYVAR", Number.NEGATIVE_INFINITY,   eval("var MYVAR=Number.NEGATIVE_INFINITY;++MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=Number.NaN;++MYVAR;MYVAR",               Number.NaN,                 eval("var MYVAR=Number.NaN;++MYVAR;MYVAR") );


// number primitives
this.TestCase( SECTION,    "var MYVAR=0;++MYVAR",            1,          eval("var MYVAR=0;++MYVAR") );
this.TestCase( SECTION,    "var MYVAR=0.2345;++MYVAR",       1.2345,     eval("var MYVAR=0.2345;++MYVAR") );
this.TestCase( SECTION,    "var MYVAR=-0.2345;++MYVAR",      0.7655,     eval("var MYVAR=-0.2345;++MYVAR") );

// verify value of variable

this.TestCase( SECTION,    "var MYVAR=0;++MYVAR;MYVAR",      1,         eval("var MYVAR=0;++MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=0.2345;++MYVAR;MYVAR", 1.2345,    eval("var MYVAR=0.2345;++MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=-0.2345;++MYVAR;MYVAR", 0.7655,   eval("var MYVAR=-0.2345;++MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=0;++MYVAR;MYVAR",      1,   eval("var MYVAR=0;++MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=0;++MYVAR;MYVAR",      1,   eval("var MYVAR=0;++MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=0;++MYVAR;MYVAR",      1,   eval("var MYVAR=0;++MYVAR;MYVAR") );

// boolean values
// verify return value

this.TestCase( SECTION,    "var MYVAR=true;++MYVAR",         2,       eval("var MYVAR=true;++MYVAR") );
this.TestCase( SECTION,    "var MYVAR=false;++MYVAR",        1,      eval("var MYVAR=false;++MYVAR") );
// verify value of variable

this.TestCase( SECTION,    "var MYVAR=true;++MYVAR;MYVAR",   2,   eval("var MYVAR=true;++MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=false;++MYVAR;MYVAR",  1,   eval("var MYVAR=false;++MYVAR;MYVAR") );

// boolean objects
// verify return value

this.TestCase( SECTION,    "var MYVAR=new Boolean(true);++MYVAR",         2,     eval("var MYVAR=true;++MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new Boolean(false);++MYVAR",        1,     eval("var MYVAR=false;++MYVAR") );
// verify value of variable

this.TestCase( SECTION,    "var MYVAR=new Boolean(true);++MYVAR;MYVAR",   2,     eval("var MYVAR=new Boolean(true);++MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new Boolean(false);++MYVAR;MYVAR",  1,     eval("var MYVAR=new Boolean(false);++MYVAR;MYVAR") );

// string primitives
this.TestCase( SECTION,    "var MYVAR='string';++MYVAR",         Number.NaN,     eval("var MYVAR='string';++MYVAR") );
this.TestCase( SECTION,    "var MYVAR='12345';++MYVAR",          12346,          eval("var MYVAR='12345';++MYVAR") );
this.TestCase( SECTION,    "var MYVAR='-12345';++MYVAR",         -12344,         eval("var MYVAR='-12345';++MYVAR") );
this.TestCase( SECTION,    "var MYVAR='0Xf';++MYVAR",            16,             eval("var MYVAR='0Xf';++MYVAR") );
this.TestCase( SECTION,    "var MYVAR='077';++MYVAR",            78,             eval("var MYVAR='077';++MYVAR") );
this.TestCase( SECTION,    "var MYVAR=''; ++MYVAR",              1,              eval("var MYVAR='';++MYVAR") );

// verify value of variable

this.TestCase( SECTION,    "var MYVAR='string';++MYVAR;MYVAR",   Number.NaN,     eval("var MYVAR='string';++MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR='12345';++MYVAR;MYVAR",    12346,          eval("var MYVAR='12345';++MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR='-12345';++MYVAR;MYVAR",   -12344,          eval("var MYVAR='-12345';++MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR='0xf';++MYVAR;MYVAR",      16,             eval("var MYVAR='0xf';++MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR='077';++MYVAR;MYVAR",      78,             eval("var MYVAR='077';++MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR='';++MYVAR;MYVAR",         1,              eval("var MYVAR='';++MYVAR;MYVAR") );

// string objects
this.TestCase( SECTION,    "var MYVAR=new String('string');++MYVAR",         Number.NaN,     eval("var MYVAR=new String('string');++MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String('12345');++MYVAR",          12346,          eval("var MYVAR=new String('12345');++MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String('-12345');++MYVAR",         -12344,         eval("var MYVAR=new String('-12345');++MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String('0Xf');++MYVAR",            16,             eval("var MYVAR=new String('0Xf');++MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String('077');++MYVAR",            78,             eval("var MYVAR=new String('077');++MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String(''); ++MYVAR",              1,              eval("var MYVAR=new String('');++MYVAR") );

// verify value of variable

this.TestCase( SECTION,    "var MYVAR=new String('string');++MYVAR;MYVAR",   Number.NaN,     eval("var MYVAR=new String('string');++MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String('12345');++MYVAR;MYVAR",    12346,          eval("var MYVAR=new String('12345');++MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String('-12345');++MYVAR;MYVAR",   -12344,          eval("var MYVAR=new String('-12345');++MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String('0xf');++MYVAR;MYVAR",      16,             eval("var MYVAR=new String('0xf');++MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String('077');++MYVAR;MYVAR",      78,             eval("var MYVAR=new String('077');++MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String('');++MYVAR;MYVAR",         1,              eval("var MYVAR=new String('');++MYVAR;MYVAR") );

//test();

},

/**
File Name:          11.4.5.js
ECMA Section:       11.4.5 Prefix decrement operator
Description:

The production UnaryExpression : -- UnaryExpression is evaluated as follows:

1.Evaluate UnaryExpression.
2.Call GetValue(Result(1)).
3.Call ToNumber(Result(2)).
4.Subtract the value 1 from Result(3), using the same rules as for the - operator (section 11.6.3).
5.Call PutValue(Result(1), Result(4)).

1.Return Result(4).
Author:             christine@netscape.com
Date:        \       12 november 1997
*/
test_11_4_5: function() {
var SECTION = "11.4.5";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Prefix decrement operator");

//
this.TestCase( SECTION,  "var MYVAR; --MYVAR",                       NaN,                            eval("var MYVAR; --MYVAR") );
this.TestCase( SECTION,  "var MYVAR= void 0; --MYVAR",               NaN,                            eval("var MYVAR=void 0; --MYVAR") );
this.TestCase( SECTION,  "var MYVAR=null; --MYVAR",                  -1,                            eval("var MYVAR=null; --MYVAR") );
this.TestCase( SECTION,  "var MYVAR=true; --MYVAR",                  0,                            eval("var MYVAR=true; --MYVAR") );
this.TestCase( SECTION,  "var MYVAR=false; --MYVAR",                 -1,                            eval("var MYVAR=false; --MYVAR") );

// special numbers
// verify return value

this.TestCase( SECTION,    "var MYVAR=Number.POSITIVE_INFINITY;--MYVAR", Number.POSITIVE_INFINITY,   eval("var MYVAR=Number.POSITIVE_INFINITY;--MYVAR") );
this.TestCase( SECTION,    "var MYVAR=Number.NEGATIVE_INFINITY;--MYVAR", Number.NEGATIVE_INFINITY,   eval("var MYVAR=Number.NEGATIVE_INFINITY;--MYVAR") );
this.TestCase( SECTION,    "var MYVAR=Number.NaN;--MYVAR",               Number.NaN,                 eval("var MYVAR=Number.NaN;--MYVAR") );

// verify value of variable

this.TestCase( SECTION,    "var MYVAR=Number.POSITIVE_INFINITY;--MYVAR;MYVAR", Number.POSITIVE_INFINITY,   eval("var MYVAR=Number.POSITIVE_INFINITY;--MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=Number.NEGATIVE_INFINITY;--MYVAR;MYVAR", Number.NEGATIVE_INFINITY,   eval("var MYVAR=Number.NEGATIVE_INFINITY;--MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=Number.NaN;--MYVAR;MYVAR",               Number.NaN,                 eval("var MYVAR=Number.NaN;--MYVAR;MYVAR") );


// number primitives
this.TestCase( SECTION,    "var MYVAR=0;--MYVAR",            -1,         eval("var MYVAR=0;--MYVAR") );
this.TestCase( SECTION,    "var MYVAR=0.2345;--MYVAR",      -0.7655,     eval("var MYVAR=0.2345;--MYVAR") );
this.TestCase( SECTION,    "var MYVAR=-0.2345;--MYVAR",      -1.2345,    eval("var MYVAR=-0.2345;--MYVAR") );

// verify value of variable

this.TestCase( SECTION,    "var MYVAR=0;--MYVAR;MYVAR",      -1,         eval("var MYVAR=0;--MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=0.2345;--MYVAR;MYVAR", -0.7655,    eval("var MYVAR=0.2345;--MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=-0.2345;--MYVAR;MYVAR", -1.2345,   eval("var MYVAR=-0.2345;--MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=0;--MYVAR;MYVAR",      -1,   eval("var MYVAR=0;--MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=0;--MYVAR;MYVAR",      -1,   eval("var MYVAR=0;--MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=0;--MYVAR;MYVAR",      -1,   eval("var MYVAR=0;--MYVAR;MYVAR") );

// boolean values
// verify return value

this.TestCase( SECTION,    "var MYVAR=true;--MYVAR",         0,       eval("var MYVAR=true;--MYVAR") );
this.TestCase( SECTION,    "var MYVAR=false;--MYVAR",        -1,      eval("var MYVAR=false;--MYVAR") );
// verify value of variable

this.TestCase( SECTION,    "var MYVAR=true;--MYVAR;MYVAR",   0,   eval("var MYVAR=true;--MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=false;--MYVAR;MYVAR",  -1,   eval("var MYVAR=false;--MYVAR;MYVAR") );

// boolean objects
// verify return value

this.TestCase( SECTION,    "var MYVAR=new Boolean(true);--MYVAR",         0,     eval("var MYVAR=true;--MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new Boolean(false);--MYVAR",        -1,     eval("var MYVAR=false;--MYVAR") );
// verify value of variable

this.TestCase( SECTION,    "var MYVAR=new Boolean(true);--MYVAR;MYVAR",   0,     eval("var MYVAR=new Boolean(true);--MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new Boolean(false);--MYVAR;MYVAR",  -1,     eval("var MYVAR=new Boolean(false);--MYVAR;MYVAR") );

// string primitives
this.TestCase( SECTION,    "var MYVAR='string';--MYVAR",         Number.NaN,     eval("var MYVAR='string';--MYVAR") );
this.TestCase( SECTION,    "var MYVAR='12345';--MYVAR",          12344,          eval("var MYVAR='12345';--MYVAR") );
this.TestCase( SECTION,    "var MYVAR='-12345';--MYVAR",         -12346,         eval("var MYVAR='-12345';--MYVAR") );
this.TestCase( SECTION,    "var MYVAR='0Xf';--MYVAR",            14,             eval("var MYVAR='0Xf';--MYVAR") );
this.TestCase( SECTION,    "var MYVAR='077';--MYVAR",            76,             eval("var MYVAR='077';--MYVAR") );
this.TestCase( SECTION,    "var MYVAR=''; --MYVAR",              -1,              eval("var MYVAR='';--MYVAR") );

// verify value of variable

this.TestCase( SECTION,    "var MYVAR='string';--MYVAR;MYVAR",   Number.NaN,     eval("var MYVAR='string';--MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR='12345';--MYVAR;MYVAR",    12344,          eval("var MYVAR='12345';--MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR='-12345';--MYVAR;MYVAR",   -12346,          eval("var MYVAR='-12345';--MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR='0xf';--MYVAR;MYVAR",      14,             eval("var MYVAR='0xf';--MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR='077';--MYVAR;MYVAR",      76,             eval("var MYVAR='077';--MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR='';--MYVAR;MYVAR",         -1,              eval("var MYVAR='';--MYVAR;MYVAR") );

// string objects
this.TestCase( SECTION,    "var MYVAR=new String('string');--MYVAR",         Number.NaN,     eval("var MYVAR=new String('string');--MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String('12345');--MYVAR",          12344,          eval("var MYVAR=new String('12345');--MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String('-12345');--MYVAR",         -12346,         eval("var MYVAR=new String('-12345');--MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String('0Xf');--MYVAR",            14,             eval("var MYVAR=new String('0Xf');--MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String('077');--MYVAR",            76,             eval("var MYVAR=new String('077');--MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String(''); --MYVAR",              -1,              eval("var MYVAR=new String('');--MYVAR") );

// verify value of variable

this.TestCase( SECTION,    "var MYVAR=new String('string');--MYVAR;MYVAR",   Number.NaN,     eval("var MYVAR=new String('string');--MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String('12345');--MYVAR;MYVAR",    12344,          eval("var MYVAR=new String('12345');--MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String('-12345');--MYVAR;MYVAR",   -12346,          eval("var MYVAR=new String('-12345');--MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String('0xf');--MYVAR;MYVAR",      14,             eval("var MYVAR=new String('0xf');--MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String('077');--MYVAR;MYVAR",      76,             eval("var MYVAR=new String('077');--MYVAR;MYVAR") );
this.TestCase( SECTION,    "var MYVAR=new String('');--MYVAR;MYVAR",         -1,              eval("var MYVAR=new String('');--MYVAR;MYVAR") );

//test();

},

/**
File Name:          11.4.6.js
ECMA Section:       11.4.6 Unary + Operator
Description:        convert operand to Number type
Author:             christine@netscape.com
Date:               7 july 1997
*/
test_11_4_6: function() {
var SECTION = "11.4.6";
var VERSION = "ECMA_1";
var BUGNUMBER="77391";

//startTest();

//writeHeaderToLog( SECTION + " Unary + operator");

this.TestCase( SECTION,  "+('')",           0,      +("") );
this.TestCase( SECTION,  "+(' ')",          0,      +(" ") );
this.TestCase( SECTION,  "+(\\t)",          0,      +("\t") );
this.TestCase( SECTION,  "+(\\n)",          0,      +("\n") );
this.TestCase( SECTION,  "+(\\r)",          0,      +("\r") );
this.TestCase( SECTION,  "+(\\f)",          0,      +("\f") );

this.TestCase( SECTION,  "+(String.fromCharCode(0x0009)",   0,  +(String.fromCharCode(0x0009)) );
this.TestCase( SECTION,  "+(String.fromCharCode(0x0020)",   0,  +(String.fromCharCode(0x0020)) );
this.TestCase( SECTION,  "+(String.fromCharCode(0x000C)",   0,  +(String.fromCharCode(0x000C)) );
this.TestCase( SECTION,  "+(String.fromCharCode(0x000B)",   0,  +(String.fromCharCode(0x000B)) );
this.TestCase( SECTION,  "+(String.fromCharCode(0x000D)",   0,  +(String.fromCharCode(0x000D)) );
this.TestCase( SECTION,  "+(String.fromCharCode(0x000A)",   0,  +(String.fromCharCode(0x000A)) );

//  a StringNumericLiteral may be preceeded or followed by whitespace and/or
//  line terminators

this.TestCase( SECTION,  "+( '   ' +  999 )",        999,    +( '   '+999) );
this.TestCase( SECTION,  "+( '\\n'  + 999 )",       999,    +( '\n' +999) );
this.TestCase( SECTION,  "+( '\\r'  + 999 )",       999,    +( '\r' +999) );
this.TestCase( SECTION,  "+( '\\t'  + 999 )",       999,    +( '\t' +999) );
this.TestCase( SECTION,  "+( '\\f'  + 999 )",       999,    +( '\f' +999) );

this.TestCase( SECTION,  "+( 999 + '   ' )",        999,    +( 999+'   ') );
this.TestCase( SECTION,  "+( 999 + '\\n' )",        999,    +( 999+'\n' ) );
this.TestCase( SECTION,  "+( 999 + '\\r' )",        999,    +( 999+'\r' ) );
this.TestCase( SECTION,  "+( 999 + '\\t' )",        999,    +( 999+'\t' ) );
this.TestCase( SECTION,  "+( 999 + '\\f' )",        999,    +( 999+'\f' ) );

this.TestCase( SECTION,  "+( '\\n'  + 999 + '\\n' )",         999,    +( '\n' +999+'\n' ) );
this.TestCase( SECTION,  "+( '\\r'  + 999 + '\\r' )",         999,    +( '\r' +999+'\r' ) );
this.TestCase( SECTION,  "+( '\\t'  + 999 + '\\t' )",         999,    +( '\t' +999+'\t' ) );
this.TestCase( SECTION,  "+( '\\f'  + 999 + '\\f' )",         999,    +( '\f' +999+'\f' ) );

this.TestCase( SECTION,  "+( '   ' +  '999' )",     999,    +( '   '+'999') );
this.TestCase( SECTION,  "+( '\\n'  + '999' )",       999,    +( '\n' +'999') );
this.TestCase( SECTION,  "+( '\\r'  + '999' )",       999,    +( '\r' +'999') );
this.TestCase( SECTION,  "+( '\\t'  + '999' )",       999,    +( '\t' +'999') );
this.TestCase( SECTION,  "+( '\\f'  + '999' )",       999,    +( '\f' +'999') );

this.TestCase( SECTION,  "+( '999' + '   ' )",        999,    +( '999'+'   ') );
this.TestCase( SECTION,  "+( '999' + '\\n' )",        999,    +( '999'+'\n' ) );
this.TestCase( SECTION,  "+( '999' + '\\r' )",        999,    +( '999'+'\r' ) );
this.TestCase( SECTION,  "+( '999' + '\\t' )",        999,    +( '999'+'\t' ) );
this.TestCase( SECTION,  "+( '999' + '\\f' )",        999,    +( '999'+'\f' ) );

this.TestCase( SECTION,  "+( '\\n'  + '999' + '\\n' )",         999,    +( '\n' +'999'+'\n' ) );
this.TestCase( SECTION,  "+( '\\r'  + '999' + '\\r' )",         999,    +( '\r' +'999'+'\r' ) );
this.TestCase( SECTION,  "+( '\\t'  + '999' + '\\t' )",         999,    +( '\t' +'999'+'\t' ) );
this.TestCase( SECTION,  "+( '\\f'  + '999' + '\\f' )",         999,    +( '\f' +'999'+'\f' ) );

this.TestCase( SECTION,  "+( String.fromCharCode(0x0009) +  '99' )",    99,     +( String.fromCharCode(0x0009) +  '99' ) );
this.TestCase( SECTION,  "+( String.fromCharCode(0x0020) +  '99' )",    99,     +( String.fromCharCode(0x0020) +  '99' ) );
this.TestCase( SECTION,  "+( String.fromCharCode(0x000C) +  '99' )",    99,     +( String.fromCharCode(0x000C) +  '99' ) );
this.TestCase( SECTION,  "+( String.fromCharCode(0x000B) +  '99' )",    99,     +( String.fromCharCode(0x000B) +  '99' ) );
this.TestCase( SECTION,  "+( String.fromCharCode(0x000D) +  '99' )",    99,     +( String.fromCharCode(0x000D) +  '99' ) );
this.TestCase( SECTION,  "+( String.fromCharCode(0x000A) +  '99' )",    99,     +( String.fromCharCode(0x000A) +  '99' ) );

this.TestCase( SECTION,  "+( String.fromCharCode(0x0009) +  '99' + String.fromCharCode(0x0009)",    99,     +( String.fromCharCode(0x0009) +  '99' + String.fromCharCode(0x0009)) );
this.TestCase( SECTION,  "+( String.fromCharCode(0x0020) +  '99' + String.fromCharCode(0x0020)",    99,     +( String.fromCharCode(0x0009) +  '99' + String.fromCharCode(0x0020)) );
this.TestCase( SECTION,  "+( String.fromCharCode(0x000C) +  '99' + String.fromCharCode(0x000C)",    99,     +( String.fromCharCode(0x0009) +  '99' + String.fromCharCode(0x000C)) );
this.TestCase( SECTION,  "+( String.fromCharCode(0x000D) +  '99' + String.fromCharCode(0x000D)",    99,     +( String.fromCharCode(0x0009) +  '99' + String.fromCharCode(0x000D)) );
this.TestCase( SECTION,  "+( String.fromCharCode(0x000B) +  '99' + String.fromCharCode(0x000B)",    99,     +( String.fromCharCode(0x0009) +  '99' + String.fromCharCode(0x000B)) );
this.TestCase( SECTION,  "+( String.fromCharCode(0x000A) +  '99' + String.fromCharCode(0x000A)",    99,     +( String.fromCharCode(0x0009) +  '99' + String.fromCharCode(0x000A)) );

this.TestCase( SECTION,  "+( '99' + String.fromCharCode(0x0009)",    99,     +( '99' + String.fromCharCode(0x0009)) );
this.TestCase( SECTION,  "+( '99' + String.fromCharCode(0x0020)",    99,     +( '99' + String.fromCharCode(0x0020)) );
this.TestCase( SECTION,  "+( '99' + String.fromCharCode(0x000C)",    99,     +( '99' + String.fromCharCode(0x000C)) );
this.TestCase( SECTION,  "+( '99' + String.fromCharCode(0x000D)",    99,     +( '99' + String.fromCharCode(0x000D)) );
this.TestCase( SECTION,  "+( '99' + String.fromCharCode(0x000B)",    99,     +( '99' + String.fromCharCode(0x000B)) );
this.TestCase( SECTION,  "+( '99' + String.fromCharCode(0x000A)",    99,     +( '99' + String.fromCharCode(0x000A)) );

this.TestCase( SECTION,  "+( String.fromCharCode(0x0009) +  99 )",    99,     +( String.fromCharCode(0x0009) +  99 ) );
this.TestCase( SECTION,  "+( String.fromCharCode(0x0020) +  99 )",    99,     +( String.fromCharCode(0x0020) +  99 ) );
this.TestCase( SECTION,  "+( String.fromCharCode(0x000C) +  99 )",    99,     +( String.fromCharCode(0x000C) +  99 ) );
this.TestCase( SECTION,  "+( String.fromCharCode(0x000B) +  99 )",    99,     +( String.fromCharCode(0x000B) +  99 ) );
this.TestCase( SECTION,  "+( String.fromCharCode(0x000D) +  99 )",    99,     +( String.fromCharCode(0x000D) +  99 ) );
this.TestCase( SECTION,  "+( String.fromCharCode(0x000A) +  99 )",    99,     +( String.fromCharCode(0x000A) +  99 ) );

this.TestCase( SECTION,  "+( String.fromCharCode(0x0009) +  99 + String.fromCharCode(0x0009)",    99,     +( String.fromCharCode(0x0009) +  99 + String.fromCharCode(0x0009)) );
this.TestCase( SECTION,  "+( String.fromCharCode(0x0020) +  99 + String.fromCharCode(0x0020)",    99,     +( String.fromCharCode(0x0009) +  99 + String.fromCharCode(0x0020)) );
this.TestCase( SECTION,  "+( String.fromCharCode(0x000C) +  99 + String.fromCharCode(0x000C)",    99,     +( String.fromCharCode(0x0009) +  99 + String.fromCharCode(0x000C)) );
this.TestCase( SECTION,  "+( String.fromCharCode(0x000D) +  99 + String.fromCharCode(0x000D)",    99,     +( String.fromCharCode(0x0009) +  99 + String.fromCharCode(0x000D)) );
this.TestCase( SECTION,  "+( String.fromCharCode(0x000B) +  99 + String.fromCharCode(0x000B)",    99,     +( String.fromCharCode(0x0009) +  99 + String.fromCharCode(0x000B)) );
this.TestCase( SECTION,  "+( String.fromCharCode(0x000A) +  99 + String.fromCharCode(0x000A)",    99,     +( String.fromCharCode(0x0009) +  99 + String.fromCharCode(0x000A)) );

this.TestCase( SECTION,  "+( 99 + String.fromCharCode(0x0009)",    99,     +( 99 + String.fromCharCode(0x0009)) );
this.TestCase( SECTION,  "+( 99 + String.fromCharCode(0x0020)",    99,     +( 99 + String.fromCharCode(0x0020)) );
this.TestCase( SECTION,  "+( 99 + String.fromCharCode(0x000C)",    99,     +( 99 + String.fromCharCode(0x000C)) );
this.TestCase( SECTION,  "+( 99 + String.fromCharCode(0x000D)",    99,     +( 99 + String.fromCharCode(0x000D)) );
this.TestCase( SECTION,  "+( 99 + String.fromCharCode(0x000B)",    99,     +( 99 + String.fromCharCode(0x000B)) );
this.TestCase( SECTION,  "+( 99 + String.fromCharCode(0x000A)",    99,     +( 99 + String.fromCharCode(0x000A)) );


// StrNumericLiteral:::StrDecimalLiteral:::Infinity

this.TestCase( SECTION,  "+('Infinity')",   Math.pow(10,10000),   +("Infinity") );
this.TestCase( SECTION,  "+('-Infinity')", -Math.pow(10,10000),   +("-Infinity") );
this.TestCase( SECTION,  "+('+Infinity')",  Math.pow(10,10000),   +("+Infinity") );

// StrNumericLiteral:::   StrDecimalLiteral ::: DecimalDigits . DecimalDigits opt ExponentPart opt

this.TestCase( SECTION,  "+('0')",          0,          +("0") );
this.TestCase( SECTION,  "+('-0')",         -0,         +("-0") );
this.TestCase( SECTION,  "+('+0')",          0,         +("+0") );

this.TestCase( SECTION,  "+('1')",          1,          +("1") );
this.TestCase( SECTION,  "+('-1')",         -1,         +("-1") );
this.TestCase( SECTION,  "+('+1')",          1,         +("+1") );

this.TestCase( SECTION,  "+('2')",          2,          +("2") );
this.TestCase( SECTION,  "+('-2')",         -2,         +("-2") );
this.TestCase( SECTION,  "+('+2')",          2,         +("+2") );

this.TestCase( SECTION,  "+('3')",          3,          +("3") );
this.TestCase( SECTION,  "+('-3')",         -3,         +("-3") );
this.TestCase( SECTION,  "+('+3')",          3,         +("+3") );

this.TestCase( SECTION,  "+('4')",          4,          +("4") );
this.TestCase( SECTION,  "+('-4')",         -4,         +("-4") );
this.TestCase( SECTION,  "+('+4')",          4,         +("+4") );

this.TestCase( SECTION,  "+('5')",          5,          +("5") );
this.TestCase( SECTION,  "+('-5')",         -5,         +("-5") );
this.TestCase( SECTION,  "+('+5')",          5,         +("+5") );

this.TestCase( SECTION,  "+('6')",          6,          +("6") );
this.TestCase( SECTION,  "+('-6')",         -6,         +("-6") );
this.TestCase( SECTION,  "+('+6')",          6,         +("+6") );

this.TestCase( SECTION,  "+('7')",          7,          +("7") );
this.TestCase( SECTION,  "+('-7')",         -7,         +("-7") );
this.TestCase( SECTION,  "+('+7')",          7,         +("+7") );

this.TestCase( SECTION,  "+('8')",          8,          +("8") );
this.TestCase( SECTION,  "+('-8')",         -8,         +("-8") );
this.TestCase( SECTION,  "+('+8')",          8,         +("+8") );

this.TestCase( SECTION,  "+('9')",          9,          +("9") );
this.TestCase( SECTION,  "+('-9')",         -9,         +("-9") );
this.TestCase( SECTION,  "+('+9')",          9,         +("+9") );

this.TestCase( SECTION,  "+('3.14159')",    3.14159,    +("3.14159") );
this.TestCase( SECTION,  "+('-3.14159')",   -3.14159,   +("-3.14159") );
this.TestCase( SECTION,  "+('+3.14159')",   3.14159,    +("+3.14159") );

this.TestCase( SECTION,  "+('3.')",         3,          +("3.") );
this.TestCase( SECTION,  "+('-3.')",        -3,         +("-3.") );
this.TestCase( SECTION,  "+('+3.')",        3,          +("+3.") );

this.TestCase( SECTION,  "+('3.e1')",       30,         +("3.e1") );
this.TestCase( SECTION,  "+('-3.e1')",      -30,        +("-3.e1") );
this.TestCase( SECTION,  "+('+3.e1')",      30,         +("+3.e1") );

this.TestCase( SECTION,  "+('3.e+1')",       30,         +("3.e+1") );
this.TestCase( SECTION,  "+('-3.e+1')",      -30,        +("-3.e+1") );
this.TestCase( SECTION,  "+('+3.e+1')",      30,         +("+3.e+1") );

this.TestCase( SECTION,  "+('3.e-1')",       .30,         +("3.e-1") );
this.TestCase( SECTION,  "+('-3.e-1')",      -.30,        +("-3.e-1") );
this.TestCase( SECTION,  "+('+3.e-1')",      .30,         +("+3.e-1") );

// StrDecimalLiteral:::  .DecimalDigits ExponentPart opt

this.TestCase( SECTION,  "+('.00001')",     0.00001,    +(".00001") );
this.TestCase( SECTION,  "+('+.00001')",    0.00001,    +("+.00001") );
this.TestCase( SECTION,  "+('-0.0001')",    -0.00001,   +("-.00001") );

this.TestCase( SECTION,  "+('.01e2')",      1,          +(".01e2") );
this.TestCase( SECTION,  "+('+.01e2')",     1,          +("+.01e2") );
this.TestCase( SECTION,  "+('-.01e2')",     -1,         +("-.01e2") );

this.TestCase( SECTION,  "+('.01e+2')",      1,         +(".01e+2") );
this.TestCase( SECTION,  "+('+.01e+2')",     1,         +("+.01e+2") );
this.TestCase( SECTION,  "+('-.01e+2')",     -1,        +("-.01e+2") );

this.TestCase( SECTION,  "+('.01e-2')",      0.0001,    +(".01e-2") );
this.TestCase( SECTION,  "+('+.01e-2')",     0.0001,    +("+.01e-2") );
this.TestCase( SECTION,  "+('-.01e-2')",     -0.0001,   +("-.01e-2") );

//  StrDecimalLiteral:::    DecimalDigits ExponentPart opt

this.TestCase( SECTION,  "+('1234e5')",     123400000,  +("1234e5") );
this.TestCase( SECTION,  "+('+1234e5')",    123400000,  +("+1234e5") );
this.TestCase( SECTION,  "+('-1234e5')",    -123400000, +("-1234e5") );

this.TestCase( SECTION,  "+('1234e+5')",    123400000,  +("1234e+5") );
this.TestCase( SECTION,  "+('+1234e+5')",   123400000,  +("+1234e+5") );
this.TestCase( SECTION,  "+('-1234e+5')",   -123400000, +("-1234e+5") );

this.TestCase( SECTION,  "+('1234e-5')",     0.01234,  +("1234e-5") );
this.TestCase( SECTION,  "+('+1234e-5')",    0.01234,  +("+1234e-5") );
this.TestCase( SECTION,  "+('-1234e-5')",    -0.01234, +("-1234e-5") );

// StrNumericLiteral::: HexIntegerLiteral

this.TestCase( SECTION,  "+('0x0')",        0,          +("0x0"));
this.TestCase( SECTION,  "+('0x1')",        1,          +("0x1"));
this.TestCase( SECTION,  "+('0x2')",        2,          +("0x2"));
this.TestCase( SECTION,  "+('0x3')",        3,          +("0x3"));
this.TestCase( SECTION,  "+('0x4')",        4,          +("0x4"));
this.TestCase( SECTION,  "+('0x5')",        5,          +("0x5"));
this.TestCase( SECTION,  "+('0x6')",        6,          +("0x6"));
this.TestCase( SECTION,  "+('0x7')",        7,          +("0x7"));
this.TestCase( SECTION,  "+('0x8')",        8,          +("0x8"));
this.TestCase( SECTION,  "+('0x9')",        9,          +("0x9"));
this.TestCase( SECTION,  "+('0xa')",        10,         +("0xa"));
this.TestCase( SECTION,  "+('0xb')",        11,         +("0xb"));
this.TestCase( SECTION,  "+('0xc')",        12,         +("0xc"));
this.TestCase( SECTION,  "+('0xd')",        13,         +("0xd"));
this.TestCase( SECTION,  "+('0xe')",        14,         +("0xe"));
this.TestCase( SECTION,  "+('0xf')",        15,         +("0xf"));
this.TestCase( SECTION,  "+('0xA')",        10,         +("0xA"));
this.TestCase( SECTION,  "+('0xB')",        11,         +("0xB"));
this.TestCase( SECTION,  "+('0xC')",        12,         +("0xC"));
this.TestCase( SECTION,  "+('0xD')",        13,         +("0xD"));
this.TestCase( SECTION,  "+('0xE')",        14,         +("0xE"));
this.TestCase( SECTION,  "+('0xF')",        15,         +("0xF"));

this.TestCase( SECTION,  "+('0X0')",        0,          +("0X0"));
this.TestCase( SECTION,  "+('0X1')",        1,          +("0X1"));
this.TestCase( SECTION,  "+('0X2')",        2,          +("0X2"));
this.TestCase( SECTION,  "+('0X3')",        3,          +("0X3"));
this.TestCase( SECTION,  "+('0X4')",        4,          +("0X4"));
this.TestCase( SECTION,  "+('0X5')",        5,          +("0X5"));
this.TestCase( SECTION,  "+('0X6')",        6,          +("0X6"));
this.TestCase( SECTION,  "+('0X7')",        7,          +("0X7"));
this.TestCase( SECTION,  "+('0X8')",        8,          +("0X8"));
this.TestCase( SECTION,  "+('0X9')",        9,          +("0X9"));
this.TestCase( SECTION,  "+('0Xa')",        10,         +("0Xa"));
this.TestCase( SECTION,  "+('0Xb')",        11,         +("0Xb"));
this.TestCase( SECTION,  "+('0Xc')",        12,         +("0Xc"));
this.TestCase( SECTION,  "+('0Xd')",        13,         +("0Xd"));
this.TestCase( SECTION,  "+('0Xe')",        14,         +("0Xe"));
this.TestCase( SECTION,  "+('0Xf')",        15,         +("0Xf"));
this.TestCase( SECTION,  "+('0XA')",        10,         +("0XA"));
this.TestCase( SECTION,  "+('0XB')",        11,         +("0XB"));
this.TestCase( SECTION,  "+('0XC')",        12,         +("0XC"));
this.TestCase( SECTION,  "+('0XD')",        13,         +("0XD"));
this.TestCase( SECTION,  "+('0XE')",        14,         +("0XE"));
this.TestCase( SECTION,  "+('0XF')",        15,         +("0XF"));

//test();

},

/**
File Name:          11.4.7-01.js
ECMA Section:       11.4.7 Unary - Operator
Description:        convert operand to Number type and change sign
Author:             christine@netscape.com
Date:               7 july 1997
*/
test_11_4_7__01: function() {
var SECTION = "11.4.7";
var VERSION = "ECMA_1";
var BUGNUMBER="77391";

//startTest();

//writeHeaderToLog( SECTION + " Unary + operator");

this.TestCase( SECTION,  "-('')",           -0,      -("") );
this.TestCase( SECTION,  "-(' ')",          -0,      -(" ") );
this.TestCase( SECTION,  "-(\\t)",          -0,      -("\t") );
this.TestCase( SECTION,  "-(\\n)",          -0,      -("\n") );
this.TestCase( SECTION,  "-(\\r)",          -0,      -("\r") );
this.TestCase( SECTION,  "-(\\f)",          -0,      -("\f") );

this.TestCase( SECTION,  "-(String.fromCharCode(0x0009)",   -0,  -(String.fromCharCode(0x0009)) );
this.TestCase( SECTION,  "-(String.fromCharCode(0x0020)",   -0,  -(String.fromCharCode(0x0020)) );
this.TestCase( SECTION,  "-(String.fromCharCode(0x000C)",   -0,  -(String.fromCharCode(0x000C)) );
this.TestCase( SECTION,  "-(String.fromCharCode(0x000B)",   -0,  -(String.fromCharCode(0x000B)) );
this.TestCase( SECTION,  "-(String.fromCharCode(0x000D)",   -0,  -(String.fromCharCode(0x000D)) );
this.TestCase( SECTION,  "-(String.fromCharCode(0x000A)",   -0,  -(String.fromCharCode(0x000A)) );

//  a StringNumericLiteral may be preceeded or followed by whitespace and/or
//  line terminators

this.TestCase( SECTION,  "-( '   ' +  999 )",        -999,    -( '   '+999) );
this.TestCase( SECTION,  "-( '\\n'  + 999 )",       -999,    -( '\n' +999) );
this.TestCase( SECTION,  "-( '\\r'  + 999 )",       -999,    -( '\r' +999) );
this.TestCase( SECTION,  "-( '\\t'  + 999 )",       -999,    -( '\t' +999) );
this.TestCase( SECTION,  "-( '\\f'  + 999 )",       -999,    -( '\f' +999) );

this.TestCase( SECTION,  "-( 999 + '   ' )",        -999,    -( 999+'   ') );
this.TestCase( SECTION,  "-( 999 + '\\n' )",        -999,    -( 999+'\n' ) );
this.TestCase( SECTION,  "-( 999 + '\\r' )",        -999,    -( 999+'\r' ) );
this.TestCase( SECTION,  "-( 999 + '\\t' )",        -999,    -( 999+'\t' ) );
this.TestCase( SECTION,  "-( 999 + '\\f' )",        -999,    -( 999+'\f' ) );

this.TestCase( SECTION,  "-( '\\n'  + 999 + '\\n' )",         -999,    -( '\n' +999+'\n' ) );
this.TestCase( SECTION,  "-( '\\r'  + 999 + '\\r' )",         -999,    -( '\r' +999+'\r' ) );
this.TestCase( SECTION,  "-( '\\t'  + 999 + '\\t' )",         -999,    -( '\t' +999+'\t' ) );
this.TestCase( SECTION,  "-( '\\f'  + 999 + '\\f' )",         -999,    -( '\f' +999+'\f' ) );

this.TestCase( SECTION,  "-( '   ' +  '999' )",     -999,    -( '   '+'999') );
this.TestCase( SECTION,  "-( '\\n'  + '999' )",       -999,    -( '\n' +'999') );
this.TestCase( SECTION,  "-( '\\r'  + '999' )",       -999,    -( '\r' +'999') );
this.TestCase( SECTION,  "-( '\\t'  + '999' )",       -999,    -( '\t' +'999') );
this.TestCase( SECTION,  "-( '\\f'  + '999' )",       -999,    -( '\f' +'999') );

this.TestCase( SECTION,  "-( '999' + '   ' )",        -999,    -( '999'+'   ') );
this.TestCase( SECTION,  "-( '999' + '\\n' )",        -999,    -( '999'+'\n' ) );
this.TestCase( SECTION,  "-( '999' + '\\r' )",        -999,    -( '999'+'\r' ) );
this.TestCase( SECTION,  "-( '999' + '\\t' )",        -999,    -( '999'+'\t' ) );
this.TestCase( SECTION,  "-( '999' + '\\f' )",        -999,    -( '999'+'\f' ) );

this.TestCase( SECTION,  "-( '\\n'  + '999' + '\\n' )",         -999,    -( '\n' +'999'+'\n' ) );
this.TestCase( SECTION,  "-( '\\r'  + '999' + '\\r' )",         -999,    -( '\r' +'999'+'\r' ) );
this.TestCase( SECTION,  "-( '\\t'  + '999' + '\\t' )",         -999,    -( '\t' +'999'+'\t' ) );
this.TestCase( SECTION,  "-( '\\f'  + '999' + '\\f' )",         -999,    -( '\f' +'999'+'\f' ) );

this.TestCase( SECTION,  "-( String.fromCharCode(0x0009) +  '99' )",    -99,     -( String.fromCharCode(0x0009) +  '99' ) );
this.TestCase( SECTION,  "-( String.fromCharCode(0x0020) +  '99' )",    -99,     -( String.fromCharCode(0x0020) +  '99' ) );
this.TestCase( SECTION,  "-( String.fromCharCode(0x000C) +  '99' )",    -99,     -( String.fromCharCode(0x000C) +  '99' ) );
this.TestCase( SECTION,  "-( String.fromCharCode(0x000B) +  '99' )",    -99,     -( String.fromCharCode(0x000B) +  '99' ) );
this.TestCase( SECTION,  "-( String.fromCharCode(0x000D) +  '99' )",    -99,     -( String.fromCharCode(0x000D) +  '99' ) );
this.TestCase( SECTION,  "-( String.fromCharCode(0x000A) +  '99' )",    -99,     -( String.fromCharCode(0x000A) +  '99' ) );

this.TestCase( SECTION,  "-( String.fromCharCode(0x0009) +  '99' + String.fromCharCode(0x0009)",    -99,     -( String.fromCharCode(0x0009) +  '99' + String.fromCharCode(0x0009)) );
this.TestCase( SECTION,  "-( String.fromCharCode(0x0020) +  '99' + String.fromCharCode(0x0020)",    -99,     -( String.fromCharCode(0x0009) +  '99' + String.fromCharCode(0x0020)) );
this.TestCase( SECTION,  "-( String.fromCharCode(0x000C) +  '99' + String.fromCharCode(0x000C)",    -99,     -( String.fromCharCode(0x0009) +  '99' + String.fromCharCode(0x000C)) );
this.TestCase( SECTION,  "-( String.fromCharCode(0x000D) +  '99' + String.fromCharCode(0x000D)",    -99,     -( String.fromCharCode(0x0009) +  '99' + String.fromCharCode(0x000D)) );
this.TestCase( SECTION,  "-( String.fromCharCode(0x000B) +  '99' + String.fromCharCode(0x000B)",    -99,     -( String.fromCharCode(0x0009) +  '99' + String.fromCharCode(0x000B)) );
this.TestCase( SECTION,  "-( String.fromCharCode(0x000A) +  '99' + String.fromCharCode(0x000A)",    -99,     -( String.fromCharCode(0x0009) +  '99' + String.fromCharCode(0x000A)) );

this.TestCase( SECTION,  "-( '99' + String.fromCharCode(0x0009)",    -99,     -( '99' + String.fromCharCode(0x0009)) );
this.TestCase( SECTION,  "-( '99' + String.fromCharCode(0x0020)",    -99,     -( '99' + String.fromCharCode(0x0020)) );
this.TestCase( SECTION,  "-( '99' + String.fromCharCode(0x000C)",    -99,     -( '99' + String.fromCharCode(0x000C)) );
this.TestCase( SECTION,  "-( '99' + String.fromCharCode(0x000D)",    -99,     -( '99' + String.fromCharCode(0x000D)) );
this.TestCase( SECTION,  "-( '99' + String.fromCharCode(0x000B)",    -99,     -( '99' + String.fromCharCode(0x000B)) );
this.TestCase( SECTION,  "-( '99' + String.fromCharCode(0x000A)",    -99,     -( '99' + String.fromCharCode(0x000A)) );

this.TestCase( SECTION,  "-( String.fromCharCode(0x0009) +  99 )",    -99,     -( String.fromCharCode(0x0009) +  99 ) );
this.TestCase( SECTION,  "-( String.fromCharCode(0x0020) +  99 )",    -99,     -( String.fromCharCode(0x0020) +  99 ) );
this.TestCase( SECTION,  "-( String.fromCharCode(0x000C) +  99 )",    -99,     -( String.fromCharCode(0x000C) +  99 ) );
this.TestCase( SECTION,  "-( String.fromCharCode(0x000B) +  99 )",    -99,     -( String.fromCharCode(0x000B) +  99 ) );
this.TestCase( SECTION,  "-( String.fromCharCode(0x000D) +  99 )",    -99,     -( String.fromCharCode(0x000D) +  99 ) );
this.TestCase( SECTION,  "-( String.fromCharCode(0x000A) +  99 )",    -99,     -( String.fromCharCode(0x000A) +  99 ) );

this.TestCase( SECTION,  "-( String.fromCharCode(0x0009) +  99 + String.fromCharCode(0x0009)",    -99,     -( String.fromCharCode(0x0009) +  99 + String.fromCharCode(0x0009)) );
this.TestCase( SECTION,  "-( String.fromCharCode(0x0020) +  99 + String.fromCharCode(0x0020)",    -99,     -( String.fromCharCode(0x0009) +  99 + String.fromCharCode(0x0020)) );
this.TestCase( SECTION,  "-( String.fromCharCode(0x000C) +  99 + String.fromCharCode(0x000C)",    -99,     -( String.fromCharCode(0x0009) +  99 + String.fromCharCode(0x000C)) );
this.TestCase( SECTION,  "-( String.fromCharCode(0x000D) +  99 + String.fromCharCode(0x000D)",    -99,     -( String.fromCharCode(0x0009) +  99 + String.fromCharCode(0x000D)) );
this.TestCase( SECTION,  "-( String.fromCharCode(0x000B) +  99 + String.fromCharCode(0x000B)",    -99,     -( String.fromCharCode(0x0009) +  99 + String.fromCharCode(0x000B)) );
this.TestCase( SECTION,  "-( String.fromCharCode(0x000A) +  99 + String.fromCharCode(0x000A)",    -99,     -( String.fromCharCode(0x0009) +  99 + String.fromCharCode(0x000A)) );

this.TestCase( SECTION,  "-( 99 + String.fromCharCode(0x0009)",    -99,     -( 99 + String.fromCharCode(0x0009)) );
this.TestCase( SECTION,  "-( 99 + String.fromCharCode(0x0020)",    -99,     -( 99 + String.fromCharCode(0x0020)) );
this.TestCase( SECTION,  "-( 99 + String.fromCharCode(0x000C)",    -99,     -( 99 + String.fromCharCode(0x000C)) );
this.TestCase( SECTION,  "-( 99 + String.fromCharCode(0x000D)",    -99,     -( 99 + String.fromCharCode(0x000D)) );
this.TestCase( SECTION,  "-( 99 + String.fromCharCode(0x000B)",    -99,     -( 99 + String.fromCharCode(0x000B)) );
this.TestCase( SECTION,  "-( 99 + String.fromCharCode(0x000A)",    -99,     -( 99 + String.fromCharCode(0x000A)) );


// StrNumericLiteral:::StrDecimalLiteral:::Infinity

this.TestCase( SECTION,  "-('Infinity')",   -Math.pow(10,10000),   -("Infinity") );
this.TestCase( SECTION,  "-('-Infinity')", +Math.pow(10,10000),   -("-Infinity") );
this.TestCase( SECTION,  "-('+Infinity')",  -Math.pow(10,10000),   -("+Infinity") );

// StrNumericLiteral:::   StrDecimalLiteral ::: DecimalDigits . DecimalDigits opt ExponentPart opt

this.TestCase( SECTION,  "-('0')",          -0,          -("0") );
this.TestCase( SECTION,  "-('-0')",         +0,         -("-0") );
this.TestCase( SECTION,  "-('+0')",          -0,         -("+0") );

this.TestCase( SECTION,  "-('1')",          -1,          -("1") );
this.TestCase( SECTION,  "-('-1')",         +1,         -("-1") );
this.TestCase( SECTION,  "-('+1')",          -1,         -("+1") );

this.TestCase( SECTION,  "-('2')",          -2,          -("2") );
this.TestCase( SECTION,  "-('-2')",         +2,         -("-2") );
this.TestCase( SECTION,  "-('+2')",          -2,         -("+2") );

this.TestCase( SECTION,  "-('3')",          -3,          -("3") );
this.TestCase( SECTION,  "-('-3')",         +3,         -("-3") );
this.TestCase( SECTION,  "-('+3')",          -3,         -("+3") );

this.TestCase( SECTION,  "-('4')",          -4,          -("4") );
this.TestCase( SECTION,  "-('-4')",         +4,         -("-4") );
this.TestCase( SECTION,  "-('+4')",          -4,         -("+4") );

this.TestCase( SECTION,  "-('5')",          -5,          -("5") );
this.TestCase( SECTION,  "-('-5')",         +5,         -("-5") );
this.TestCase( SECTION,  "-('+5')",          -5,         -("+5") );

this.TestCase( SECTION,  "-('6')",          -6,          -("6") );
this.TestCase( SECTION,  "-('-6')",         +6,         -("-6") );
this.TestCase( SECTION,  "-('+6')",          -6,         -("+6") );

this.TestCase( SECTION,  "-('7')",          -7,          -("7") );
this.TestCase( SECTION,  "-('-7')",         +7,         -("-7") );
this.TestCase( SECTION,  "-('+7')",          -7,         -("+7") );

this.TestCase( SECTION,  "-('8')",          -8,          -("8") );
this.TestCase( SECTION,  "-('-8')",         +8,         -("-8") );
this.TestCase( SECTION,  "-('+8')",          -8,         -("+8") );

this.TestCase( SECTION,  "-('9')",          -9,          -("9") );
this.TestCase( SECTION,  "-('-9')",         +9,         -("-9") );
this.TestCase( SECTION,  "-('+9')",          -9,         -("+9") );

this.TestCase( SECTION,  "-('3.14159')",    -3.14159,    -("3.14159") );
this.TestCase( SECTION,  "-('-3.14159')",   +3.14159,   -("-3.14159") );
this.TestCase( SECTION,  "-('+3.14159')",   -3.14159,    -("+3.14159") );

this.TestCase( SECTION,  "-('3.')",         -3,          -("3.") );
this.TestCase( SECTION,  "-('-3.')",        +3,         -("-3.") );
this.TestCase( SECTION,  "-('+3.')",        -3,          -("+3.") );

this.TestCase( SECTION,  "-('3.e1')",       -30,         -("3.e1") );
this.TestCase( SECTION,  "-('-3.e1')",      +30,        -("-3.e1") );
this.TestCase( SECTION,  "-('+3.e1')",      -30,         -("+3.e1") );

this.TestCase( SECTION,  "-('3.e+1')",       -30,         -("3.e+1") );
this.TestCase( SECTION,  "-('-3.e+1')",      +30,        -("-3.e+1") );
this.TestCase( SECTION,  "-('+3.e+1')",      -30,         -("+3.e+1") );

this.TestCase( SECTION,  "-('3.e-1')",       -.30,         -("3.e-1") );
this.TestCase( SECTION,  "-('-3.e-1')",      +.30,        -("-3.e-1") );
this.TestCase( SECTION,  "-('+3.e-1')",      -.30,         -("+3.e-1") );

// StrDecimalLiteral:::  .DecimalDigits ExponentPart opt

this.TestCase( SECTION,  "-('.00001')",     -0.00001,    -(".00001") );
this.TestCase( SECTION,  "-('+.00001')",    -0.00001,    -("+.00001") );
this.TestCase( SECTION,  "-('-0.0001')",    +0.00001,   -("-.00001") );

this.TestCase( SECTION,  "-('.01e2')",      -1,          -(".01e2") );
this.TestCase( SECTION,  "-('+.01e2')",     -1,          -("+.01e2") );
this.TestCase( SECTION,  "-('-.01e2')",     +1,         -("-.01e2") );

this.TestCase( SECTION,  "-('.01e+2')",      -1,         -(".01e+2") );
this.TestCase( SECTION,  "-('+.01e+2')",     -1,         -("+.01e+2") );
this.TestCase( SECTION,  "-('-.01e+2')",     +1,        -("-.01e+2") );

this.TestCase( SECTION,  "-('.01e-2')",      -0.0001,    -(".01e-2") );
this.TestCase( SECTION,  "-('+.01e-2')",     -0.0001,    -("+.01e-2") );
this.TestCase( SECTION,  "-('-.01e-2')",     +0.0001,   -("-.01e-2") );

//  StrDecimalLiteral:::    DecimalDigits ExponentPart opt

this.TestCase( SECTION,  "-('1234e5')",     -123400000,  -("1234e5") );
this.TestCase( SECTION,  "-('+1234e5')",    -123400000,  -("+1234e5") );
this.TestCase( SECTION,  "-('-1234e5')",    +123400000, -("-1234e5") );

this.TestCase( SECTION,  "-('1234e+5')",    -123400000,  -("1234e+5") );
this.TestCase( SECTION,  "-('+1234e+5')",   -123400000,  -("+1234e+5") );
this.TestCase( SECTION,  "-('-1234e+5')",   +123400000, -("-1234e+5") );

this.TestCase( SECTION,  "-('1234e-5')",     -0.01234,  -("1234e-5") );
this.TestCase( SECTION,  "-('+1234e-5')",    -0.01234,  -("+1234e-5") );
this.TestCase( SECTION,  "-('-1234e-5')",    +0.01234, -("-1234e-5") );

// StrNumericLiteral::: HexIntegerLiteral

this.TestCase( SECTION,  "-('0x0')",        -0,          -("0x0"));
this.TestCase( SECTION,  "-('0x1')",        -1,          -("0x1"));
this.TestCase( SECTION,  "-('0x2')",        -2,          -("0x2"));
this.TestCase( SECTION,  "-('0x3')",        -3,          -("0x3"));
this.TestCase( SECTION,  "-('0x4')",        -4,          -("0x4"));
this.TestCase( SECTION,  "-('0x5')",        -5,          -("0x5"));
this.TestCase( SECTION,  "-('0x6')",        -6,          -("0x6"));
this.TestCase( SECTION,  "-('0x7')",        -7,          -("0x7"));
this.TestCase( SECTION,  "-('0x8')",        -8,          -("0x8"));
this.TestCase( SECTION,  "-('0x9')",        -9,          -("0x9"));
this.TestCase( SECTION,  "-('0xa')",        -10,         -("0xa"));
this.TestCase( SECTION,  "-('0xb')",        -11,         -("0xb"));
this.TestCase( SECTION,  "-('0xc')",        -12,         -("0xc"));
this.TestCase( SECTION,  "-('0xd')",        -13,         -("0xd"));
this.TestCase( SECTION,  "-('0xe')",        -14,         -("0xe"));
this.TestCase( SECTION,  "-('0xf')",        -15,         -("0xf"));
this.TestCase( SECTION,  "-('0xA')",        -10,         -("0xA"));
this.TestCase( SECTION,  "-('0xB')",        -11,         -("0xB"));
this.TestCase( SECTION,  "-('0xC')",        -12,         -("0xC"));
this.TestCase( SECTION,  "-('0xD')",        -13,         -("0xD"));
this.TestCase( SECTION,  "-('0xE')",        -14,         -("0xE"));
this.TestCase( SECTION,  "-('0xF')",        -15,         -("0xF"));

this.TestCase( SECTION,  "-('0X0')",        -0,          -("0X0"));
this.TestCase( SECTION,  "-('0X1')",        -1,          -("0X1"));
this.TestCase( SECTION,  "-('0X2')",        -2,          -("0X2"));
this.TestCase( SECTION,  "-('0X3')",        -3,          -("0X3"));
this.TestCase( SECTION,  "-('0X4')",        -4,          -("0X4"));
this.TestCase( SECTION,  "-('0X5')",        -5,          -("0X5"));
this.TestCase( SECTION,  "-('0X6')",        -6,          -("0X6"));
this.TestCase( SECTION,  "-('0X7')",        -7,          -("0X7"));
this.TestCase( SECTION,  "-('0X8')",        -8,          -("0X8"));
this.TestCase( SECTION,  "-('0X9')",        -9,          -("0X9"));
this.TestCase( SECTION,  "-('0Xa')",        -10,         -("0Xa"));
this.TestCase( SECTION,  "-('0Xb')",        -11,         -("0Xb"));
this.TestCase( SECTION,  "-('0Xc')",        -12,         -("0Xc"));
this.TestCase( SECTION,  "-('0Xd')",        -13,         -("0Xd"));
this.TestCase( SECTION,  "-('0Xe')",        -14,         -("0Xe"));
this.TestCase( SECTION,  "-('0Xf')",        -15,         -("0Xf"));
this.TestCase( SECTION,  "-('0XA')",        -10,         -("0XA"));
this.TestCase( SECTION,  "-('0XB')",        -11,         -("0XB"));
this.TestCase( SECTION,  "-('0XC')",        -12,         -("0XC"));
this.TestCase( SECTION,  "-('0XD')",        -13,         -("0XD"));
this.TestCase( SECTION,  "-('0XE')",        -14,         -("0XE"));
this.TestCase( SECTION,  "-('0XF')",        -15,         -("0XF"));

//test();

},

/**
*  File Name:          11.4.7-02.js
*  Reference:          https://bugzilla.mozilla.org/show_bug.cgi?id=432881
*  Description:        ecma 11.4.7
*/
test_11_4_7__02: function() {
var SECTION = "11.4.7";
var VERSION = "ECMA";
var TITLE   = "Unary - Operator";
var BUGNUMBER = "432881";

//startTest();

test_negation(0, -0.0);
test_negation(-0.0, 0);
test_negation(1, -1);
test_negation(1.0/0.0, -1.0/0.0);
test_negation(-1.0/0.0, 1.0/0.0);

//1073741824 == (1 << 30)
test_negation(1073741824, -1073741824);
test_negation(-1073741824, 1073741824);

//1073741824 == (1 << 30) - 1
test_negation(1073741823, -1073741823);
test_negation(-1073741823, 1073741823);

//1073741824 == (1 << 30)
test_negation(1073741824, -1073741824);
test_negation(-1073741824, 1073741824);

//1073741824 == (1 << 30) - 1
test_negation(1073741823, -1073741823);
test_negation(-1073741823, 1073741823);

//2147483648 == (1 << 31)
test_negation(2147483648, -2147483648);
test_negation(-2147483648, 2147483648);

//2147483648 == (1 << 31) - 1
test_negation(2147483647, -2147483647);
test_negation(-2147483647, 2147483647);

//test();

function test_negation(value, expected)
{
var actual = -value;
//reportCompare(expected, actual, '-(' + value + ') == ' + expected);

assertTrue(expected == actual);
}

},

/**
File Name:          11.4.8.js
ECMA Section:       11.4.8 Bitwise NOT Operator
Description:        flip bits up to 32 bits
no special cases
Author:             christine@netscape.com
Date:               7 july 1997

Data File Fields:
VALUE           value passed as an argument to the ~ operator
E_RESULT        expected return value of ~ VALUE;

Static variables:
none

*/
test_11_4_8: function() {
var SECTION = "11.4.8";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Bitwise Not operator");

for ( var i = 0; i < 35; i++ ) {
var p = Math.pow(2,i);

this.TestCase( SECTION, "~"+p,   Not(p),     ~p );

}
for ( i = 0; i < 35; i++ ) {
var p = -Math.pow(2,i);

this.TestCase( SECTION, "~"+p,   Not(p),     ~p );

}

//test();

function ToInteger( n ) {
n = Number( n );
var sign = ( n < 0 ) ? -1 : 1;

if ( n != n ) {
return 0;
}
if ( Math.abs( n ) == 0 || Math.abs( n ) == Number.POSITIVE_INFINITY ) {
return n;
}
return ( sign * Math.floor(Math.abs(n)) );
}
function ToInt32( n ) {
n = Number( n );
var sign = ( n < 0 ) ? -1 : 1;

if ( Math.abs( n ) == 0 || Math.abs( n ) == Number.POSITIVE_INFINITY) {
return 0;
}

n = (sign * Math.floor( Math.abs(n) )) % Math.pow(2,32);
n = ( n >= Math.pow(2,31) ) ? n - Math.pow(2,32) : n;

return ( n );
}
function ToUint32( n ) {
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
function ToUint16( n ) {
var sign = ( n < 0 ) ? -1 : 1;

if ( Math.abs( n ) == 0 || Math.abs( n ) == Number.POSITIVE_INFINITY) {
return 0;
}

n = ( sign * Math.floor( Math.abs(n) ) ) % Math.pow(2,16);

if (n <0) {
n += Math.pow(2,16);
}

return ( n );
}
function Mask( b, n ) {
b = ToUint32BitString( b );
b = b.substring( b.length - n );
b = ToUint32Decimal( b );
return ( b );
}
function ToUint32BitString( n ) {
var b = "";
for ( var p = 31; p >=0; p-- ) {
if ( n >= Math.pow(2,p) ) {
b += "1";
n -= Math.pow(2,p);
} else {
b += "0";
}
}
return b;
}
function ToInt32BitString( n ) {
var b = "";
var sign = ( n < 0 ) ? -1 : 1;

b += ( sign == 1 ) ? "0" : "1";

for ( var p = 30; p >=0; p-- ) {
if ( (sign == 1 ) ? sign * n >= Math.pow(2,p) : sign * n > Math.pow(2,p) ) {
b += ( sign == 1 ) ? "1" : "0";
n -= sign * Math.pow( 2, p );
} else {
b += ( sign == 1 ) ? "0" : "1";
}
}

return b;
}
function ToInt32Decimal( bin ) {
var r = 0;
var sign;

if ( Number(bin.charAt(0)) == 0 ) {
sign = 1;
r = 0;
} else {
sign = -1;
r = -(Math.pow(2,31));
}

for ( var j = 0; j < 31; j++ ) {
r += Math.pow( 2, j ) * Number(bin.charAt(31-j));
}

return r;
}
function ToUint32Decimal( bin ) {
var r = 0;

for ( var l = bin.length; l < 32; l++ ) {
bin = "0" + bin;
}

for ( var j = 0; j < 31; j++ ) {
r += Math.pow( 2, j ) * Number(bin.charAt(31-j));
}

return r;
}
function Not( n ) {
n = ToInt32(n);
n = ToInt32BitString(n);

var r = "";

for( var l = 0; l < n.length; l++  ) {
r += ( n.charAt(l) == "0" ) ? "1" : "0";
}

n = ToInt32Decimal(r);

return n;
}

},

/**
File Name:          11.4.9.js
ECMA Section:       11.4.9 Logical NOT Operator (!)
Description:        if the ToBoolean( VALUE ) result is true, return
true.  else return false.
Author:             christine@netscape.com
Date:               7 july 1997

Static variables:
none
*/
test_11_4_9: function() {
var SECTION = "11.4.9";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Logical NOT operator (!)";

//writeHeaderToLog( SECTION + " "+ TITLE);

//    version("130")


this.TestCase( SECTION,   "!(null)",                true,   !(null) );
this.TestCase( SECTION,   "!(var x)",               true,   !(eval("var x")) );
this.TestCase( SECTION,   "!(void 0)",              true,   !(void 0) );

this.TestCase( SECTION,   "!(false)",               true,   !(false) );
this.TestCase( SECTION,   "!(true)",                false,  !(true) );
this.TestCase( SECTION,   "!()",                    true,   !(eval()) );
this.TestCase( SECTION,   "!(0)",                   true,   !(0) );
this.TestCase( SECTION,   "!(-0)",                  true,   !(-0) );
this.TestCase( SECTION,   "!(NaN)",                 true,   !(Number.NaN) );
this.TestCase( SECTION,   "!(Infinity)",            false,  !(Number.POSITIVE_INFINITY) );
this.TestCase( SECTION,   "!(-Infinity)",           false,  !(Number.NEGATIVE_INFINITY) );
this.TestCase( SECTION,   "!(Math.PI)",             false,  !(Math.PI) );
this.TestCase( SECTION,   "!(1)",                   false,  !(1) );
this.TestCase( SECTION,   "!(-1)",                  false,  !(-1) );
this.TestCase( SECTION,   "!('')",                  true,   !("") );
this.TestCase( SECTION,   "!('\t')",                false,  !("\t") );
this.TestCase( SECTION,   "!('0')",                 false,  !("0") );
this.TestCase( SECTION,   "!('string')",            false,  !("string") );
this.TestCase( SECTION,   "!(new String(''))",      false,  !(new String("")) );
this.TestCase( SECTION,   "!(new String('string'))",    false,  !(new String("string")) );
this.TestCase( SECTION,   "!(new String())",        false,  !(new String()) );
this.TestCase( SECTION,   "!(new Boolean(true))",   false,   !(new Boolean(true)) );
this.TestCase( SECTION,   "!(new Boolean(false))",  false,   !(new Boolean(false)) );
this.TestCase( SECTION,   "!(new Array())",         false,  !(new Array()) );
this.TestCase( SECTION,   "!(new Array(1,2,3)",     false,  !(new Array(1,2,3)) );
this.TestCase( SECTION,   "!(new Number())",        false,  !(new Number()) );
this.TestCase( SECTION,   "!(new Number(0))",       false,  !(new Number(0)) );
this.TestCase( SECTION,   "!(new Number(NaN))",     false,  !(new Number(Number.NaN)) );
this.TestCase( SECTION,   "!(new Number(Infinity))", false, !(new Number(Number.POSITIVE_INFINITY)) );

//test();

},

/**
File Name:          11.5.1.js
ECMA Section:       11.5.1 Applying the * operator
Description:

11.5.1 Applying the * operator

The * operator performs multiplication, producing the product of its
operands. Multiplication is commutative. Multiplication is not always
associative in ECMAScript, because of finite precision.

The result of a floating-point multiplication is governed by the rules
of IEEE 754 double-precision arithmetic:

If either operand is NaN, the result is NaN.
The sign of the result is positive if both operands have the same sign,
negative if the operands have different signs.
Multiplication of an infinity by a zero results in NaN.
Multiplication of an infinity by an infinity results in an infinity.
The sign is determined by the rule already stated above.
Multiplication of an infinity by a finite non-zero value results in a
signed infinity. The sign is determined by the rule already stated above.
In the remaining cases, where neither an infinity or NaN is involved, the
product is computed and rounded to the nearest representable value using IEEE
754 round-to-nearest mode. If the magnitude is too large to represent,
the result is then an infinity of appropriate sign. If the magnitude is
oo small to represent, the result is then a zero
of appropriate sign. The ECMAScript language requires support of gradual
underflow as defined by IEEE 754.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_5_1: function() {
var SECTION = "11.5.1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Applying the * operator");

this.TestCase( SECTION,    "Number.NaN * Number.NaN",    Number.NaN,     Number.NaN * Number.NaN );
this.TestCase( SECTION,    "Number.NaN * 1",             Number.NaN,     Number.NaN * 1 );
this.TestCase( SECTION,    "1 * Number.NaN",             Number.NaN,     1 * Number.NaN );

this.TestCase( SECTION,    "Number.POSITIVE_INFINITY * 0",   Number.NaN, Number.POSITIVE_INFINITY * 0 );
this.TestCase( SECTION,    "Number.NEGATIVE_INFINITY * 0",   Number.NaN, Number.NEGATIVE_INFINITY * 0 );
this.TestCase( SECTION,    "0 * Number.POSITIVE_INFINITY",   Number.NaN, 0 * Number.POSITIVE_INFINITY );
this.TestCase( SECTION,    "0 * Number.NEGATIVE_INFINITY",   Number.NaN, 0 * Number.NEGATIVE_INFINITY );

this.TestCase( SECTION,    "-0 * Number.POSITIVE_INFINITY",  Number.NaN,   -0 * Number.POSITIVE_INFINITY );
this.TestCase( SECTION,    "-0 * Number.NEGATIVE_INFINITY",  Number.NaN,   -0 * Number.NEGATIVE_INFINITY );
this.TestCase( SECTION,    "Number.POSITIVE_INFINITY * -0",  Number.NaN,   Number.POSITIVE_INFINITY * -0 );
this.TestCase( SECTION,    "Number.NEGATIVE_INFINITY * -0",  Number.NaN,   Number.NEGATIVE_INFINITY * -0 );

this.TestCase( SECTION,    "0 * -0",                         -0,         0 * -0 );
this.TestCase( SECTION,    "-0 * 0",                         -0,         -0 * 0 );
this.TestCase( SECTION,    "-0 * -0",                        0,          -0 * -0 );
this.TestCase( SECTION,    "0 * 0",                          0,          0 * 0 );

this.TestCase( SECTION,    "Number.NEGATIVE_INFINITY * Number.NEGATIVE_INFINITY",    Number.POSITIVE_INFINITY,   Number.NEGATIVE_INFINITY * Number.NEGATIVE_INFINITY );
this.TestCase( SECTION,    "Number.POSITIVE_INFINITY * Number.NEGATIVE_INFINITY",    Number.NEGATIVE_INFINITY,   Number.POSITIVE_INFINITY * Number.NEGATIVE_INFINITY );
this.TestCase( SECTION,    "Number.NEGATIVE_INFINITY * Number.POSITIVE_INFINITY",    Number.NEGATIVE_INFINITY,   Number.NEGATIVE_INFINITY * Number.POSITIVE_INFINITY );
this.TestCase( SECTION,    "Number.POSITIVE_INFINITY * Number.POSITIVE_INFINITY",    Number.POSITIVE_INFINITY,   Number.POSITIVE_INFINITY * Number.POSITIVE_INFINITY );

this.TestCase( SECTION,    "Number.NEGATIVE_INFINITY * 1 ",                          Number.NEGATIVE_INFINITY,   Number.NEGATIVE_INFINITY * 1 );
this.TestCase( SECTION,    "Number.NEGATIVE_INFINITY * -1 ",                         Number.POSITIVE_INFINITY,   Number.NEGATIVE_INFINITY * -1 );
this.TestCase( SECTION,    "1 * Number.NEGATIVE_INFINITY",                           Number.NEGATIVE_INFINITY,   1 * Number.NEGATIVE_INFINITY );
this.TestCase( SECTION,    "-1 * Number.NEGATIVE_INFINITY",                          Number.POSITIVE_INFINITY,   -1 * Number.NEGATIVE_INFINITY );

this.TestCase( SECTION,    "Number.POSITIVE_INFINITY * 1 ",                          Number.POSITIVE_INFINITY,   Number.POSITIVE_INFINITY * 1 );
this.TestCase( SECTION,    "Number.POSITIVE_INFINITY * -1 ",                         Number.NEGATIVE_INFINITY,   Number.POSITIVE_INFINITY * -1 );
this.TestCase( SECTION,    "1 * Number.POSITIVE_INFINITY",                           Number.POSITIVE_INFINITY,   1 * Number.POSITIVE_INFINITY );
this.TestCase( SECTION,    "-1 * Number.POSITIVE_INFINITY",                          Number.NEGATIVE_INFINITY,   -1 * Number.POSITIVE_INFINITY );

//test();

},

/**
File Name:          11.5.2.js
ECMA Section:       11.5.2 Applying the / operator
Description:

The / operator performs division, producing the quotient of its operands.
The left operand is the dividend and the right operand is the divisor.
ECMAScript does not perform integer division. The operands and result of all
division operations are double-precision floating-point numbers.
The result of division is determined by the specification of IEEE 754 arithmetic:

If either operand is NaN, the result is NaN.
The sign of the result is positive if both operands have the same sign, negative if the operands have different
signs.
Division of an infinity by an infinity results in NaN.
Division of an infinity by a zero results in an infinity. The sign is determined by the rule already stated above.
Division of an infinity by a non-zero finite value results in a signed infinity. The sign is determined by the rule
already stated above.
Division of a finite value by an infinity results in zero. The sign is determined by the rule already stated above.
Division of a zero by a zero results in NaN; division of zero by any other finite value results in zero, with the sign
determined by the rule already stated above.
Division of a non-zero finite value by a zero results in a signed infinity. The sign is determined by the rule
already stated above.
In the remaining cases, where neither an infinity, nor a zero, nor NaN is involved, the quotient is computed and
rounded to the nearest representable value using IEEE 754 round-to-nearest mode. If the magnitude is too
large to represent, we say the operation overflows; the result is then an infinity of appropriate sign. If the
magnitude is too small to represent, we say the operation underflows and the result is a zero of the appropriate
sign. The ECMAScript language requires support of gradual underflow as defined by IEEE 754.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_5_2: function() {
var SECTION = "11.5.2";
var VERSION = "ECMA_1";
var BUGNUMBER="111202";
//startTest();

//writeHeaderToLog( SECTION + " Applying the / operator");

// if either operand is NaN, the result is NaN.

this.TestCase( SECTION,    "Number.NaN / Number.NaN",    Number.NaN,     Number.NaN / Number.NaN );
this.TestCase( SECTION,    "Number.NaN / 1",             Number.NaN,     Number.NaN / 1 );
this.TestCase( SECTION,    "1 / Number.NaN",             Number.NaN,     1 / Number.NaN );

this.TestCase( SECTION,    "Number.POSITIVE_INFINITY / Number.NaN",    Number.NaN,     Number.POSITIVE_INFINITY / Number.NaN );
this.TestCase( SECTION,    "Number.NEGATIVE_INFINITY / Number.NaN",    Number.NaN,     Number.NEGATIVE_INFINITY / Number.NaN );

// Division of an infinity by an infinity results in NaN.

this.TestCase( SECTION,    "Number.NEGATIVE_INFINITY / Number.NEGATIVE_INFINITY",    Number.NaN,   Number.NEGATIVE_INFINITY / Number.NEGATIVE_INFINITY );
this.TestCase( SECTION,    "Number.POSITIVE_INFINITY / Number.NEGATIVE_INFINITY",    Number.NaN,   Number.POSITIVE_INFINITY / Number.NEGATIVE_INFINITY );
this.TestCase( SECTION,    "Number.NEGATIVE_INFINITY / Number.POSITIVE_INFINITY",    Number.NaN,   Number.NEGATIVE_INFINITY / Number.POSITIVE_INFINITY );
this.TestCase( SECTION,    "Number.POSITIVE_INFINITY / Number.POSITIVE_INFINITY",    Number.NaN,   Number.POSITIVE_INFINITY / Number.POSITIVE_INFINITY );

// Division of an infinity by a zero results in an infinity.

this.TestCase( SECTION,    "Number.POSITIVE_INFINITY / 0",   Number.POSITIVE_INFINITY, Number.POSITIVE_INFINITY / 0 );
this.TestCase( SECTION,    "Number.NEGATIVE_INFINITY / 0",   Number.NEGATIVE_INFINITY, Number.NEGATIVE_INFINITY / 0 );
this.TestCase( SECTION,    "Number.POSITIVE_INFINITY / -0",  Number.NEGATIVE_INFINITY,   Number.POSITIVE_INFINITY / -0 );
this.TestCase( SECTION,    "Number.NEGATIVE_INFINITY / -0",  Number.POSITIVE_INFINITY,   Number.NEGATIVE_INFINITY / -0 );

// Division of an infinity by a non-zero finite value results in a signed infinity.

this.TestCase( SECTION,    "Number.NEGATIVE_INFINITY / 1 ",          Number.NEGATIVE_INFINITY,   Number.NEGATIVE_INFINITY / 1 );
this.TestCase( SECTION,    "Number.NEGATIVE_INFINITY / -1 ",         Number.POSITIVE_INFINITY,   Number.NEGATIVE_INFINITY / -1 );
this.TestCase( SECTION,    "Number.POSITIVE_INFINITY / 1 ",          Number.POSITIVE_INFINITY,   Number.POSITIVE_INFINITY / 1 );
this.TestCase( SECTION,    "Number.POSITIVE_INFINITY / -1 ",         Number.NEGATIVE_INFINITY,   Number.POSITIVE_INFINITY / -1 );

this.TestCase( SECTION,    "Number.NEGATIVE_INFINITY / Number.MAX_VALUE ",          Number.NEGATIVE_INFINITY,   Number.NEGATIVE_INFINITY / Number.MAX_VALUE );
this.TestCase( SECTION,    "Number.NEGATIVE_INFINITY / -Number.MAX_VALUE ",         Number.POSITIVE_INFINITY,   Number.NEGATIVE_INFINITY / -Number.MAX_VALUE );
this.TestCase( SECTION,    "Number.POSITIVE_INFINITY / Number.MAX_VALUE ",          Number.POSITIVE_INFINITY,   Number.POSITIVE_INFINITY / Number.MAX_VALUE );
this.TestCase( SECTION,    "Number.POSITIVE_INFINITY / -Number.MAX_VALUE ",         Number.NEGATIVE_INFINITY,   Number.POSITIVE_INFINITY / -Number.MAX_VALUE );

// Division of a finite value by an infinity results in zero.

this.TestCase( SECTION,    "1 / Number.NEGATIVE_INFINITY",   -0,             1 / Number.NEGATIVE_INFINITY );
this.TestCase( SECTION,    "1 / Number.POSITIVE_INFINITY",   0,              1 / Number.POSITIVE_INFINITY );
this.TestCase( SECTION,    "-1 / Number.POSITIVE_INFINITY",  -0,             -1 / Number.POSITIVE_INFINITY );
this.TestCase( SECTION,    "-1 / Number.NEGATIVE_INFINITY",  0,              -1 / Number.NEGATIVE_INFINITY );

this.TestCase( SECTION,    "Number.MAX_VALUE / Number.NEGATIVE_INFINITY",   -0,             Number.MAX_VALUE / Number.NEGATIVE_INFINITY );
this.TestCase( SECTION,    "Number.MAX_VALUE / Number.POSITIVE_INFINITY",   0,              Number.MAX_VALUE / Number.POSITIVE_INFINITY );
this.TestCase( SECTION,    "-Number.MAX_VALUE / Number.POSITIVE_INFINITY",  -0,             -Number.MAX_VALUE / Number.POSITIVE_INFINITY );
this.TestCase( SECTION,    "-Number.MAX_VALUE / Number.NEGATIVE_INFINITY",  0,              -Number.MAX_VALUE / Number.NEGATIVE_INFINITY );

// Division of a zero by a zero results in NaN

this.TestCase( SECTION,    "0 / -0",                         Number.NaN,     0 / -0 );
this.TestCase( SECTION,    "-0 / 0",                         Number.NaN,     -0 / 0 );
this.TestCase( SECTION,    "-0 / -0",                        Number.NaN,     -0 / -0 );
this.TestCase( SECTION,    "0 / 0",                          Number.NaN,     0 / 0 );

// division of zero by any other finite value results in zero

this.TestCase( SECTION,    "0 / 1",                          0,              0 / 1 );
this.TestCase( SECTION,    "0 / -1",                        -0,              0 / -1 );
this.TestCase( SECTION,    "-0 / 1",                        -0,              -0 / 1 );
this.TestCase( SECTION,    "-0 / -1",                       0,               -0 / -1 );

// Division of a non-zero finite value by a zero results in a signed infinity.

this.TestCase( SECTION,    "1 / 0",                          Number.POSITIVE_INFINITY,   1/0 );
this.TestCase( SECTION,    "1 / -0",                         Number.NEGATIVE_INFINITY,   1/-0 );
this.TestCase( SECTION,    "-1 / 0",                         Number.NEGATIVE_INFINITY,   -1/0 );
this.TestCase( SECTION,    "-1 / -0",                        Number.POSITIVE_INFINITY,   -1/-0 );

this.TestCase( SECTION,    "0 / Number.POSITIVE_INFINITY",   0,      0 / Number.POSITIVE_INFINITY );
this.TestCase( SECTION,    "0 / Number.NEGATIVE_INFINITY",   -0,     0 / Number.NEGATIVE_INFINITY );
this.TestCase( SECTION,    "-0 / Number.POSITIVE_INFINITY",  -0,     -0 / Number.POSITIVE_INFINITY );
this.TestCase( SECTION,    "-0 / Number.NEGATIVE_INFINITY",  0,      -0 / Number.NEGATIVE_INFINITY );

//test();

},

/**
File Name:          11.5.3.js
ECMA Section:       11.5.3 Applying the % operator
Description:

The binary % operator is said to yield the remainder of its operands from
an implied division; the left operand is the dividend and the right operand
is the divisor. In C and C++, the remainder operator accepts only integral
operands, but in ECMAScript, it also accepts floating-point operands.

The result of a floating-point remainder operation as computed by the %
operator is not the same as the "remainder" operation defined by IEEE 754.
The IEEE 754 "remainder" operation computes the remainder from a rounding
division, not a truncating division, and so its behavior is not analogous
to that of the usual integer remainder operator. Instead the ECMAScript
language defines % on floating-point operations to behave in a manner
analogous to that of the Java integer remainder operator; this may be
compared with the C library function fmod.

The result of a ECMAScript floating-point remainder operation is determined by the rules of IEEE arithmetic:

If either operand is NaN, the result is NaN.
The sign of the result equals the sign of the dividend.
If the dividend is an infinity, or the divisor is a zero, or both, the result is NaN.
If the dividend is finite and the divisor is an infinity, the result equals the dividend.
If the dividend is a zero and the divisor is finite, the result is the same as the dividend.
In the remaining cases, where neither an infinity, nor a zero, nor NaN is involved, the floating-point remainder r
from a dividend n and a divisor d is defined by the mathematical relation r = n (d * q) where q is an integer that
is negative only if n/d is negative and positive only if n/d is positive, and whose magnitude is as large as
possible without exceeding the magnitude of the true mathematical quotient of n and d.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_5_3: function() {
var SECTION = "11.5.3";
var VERSION = "ECMA_1";
var BUGNUMBER="111202";
//startTest();

//writeHeaderToLog( SECTION + " Applying the % operator");

// if either operand is NaN, the result is NaN.

this.TestCase( SECTION,    "Number.NaN % Number.NaN",    Number.NaN,     Number.NaN % Number.NaN );
this.TestCase( SECTION,    "Number.NaN % 1",             Number.NaN,     Number.NaN % 1 );
this.TestCase( SECTION,    "1 % Number.NaN",             Number.NaN,     1 % Number.NaN );

this.TestCase( SECTION,    "Number.POSITIVE_INFINITY % Number.NaN",    Number.NaN,     Number.POSITIVE_INFINITY % Number.NaN );
this.TestCase( SECTION,    "Number.NEGATIVE_INFINITY % Number.NaN",    Number.NaN,     Number.NEGATIVE_INFINITY % Number.NaN );

//  If the dividend is an infinity, or the divisor is a zero, or both, the result is NaN.
//  dividend is an infinity

this.TestCase( SECTION,    "Number.NEGATIVE_INFINITY % Number.NEGATIVE_INFINITY",    Number.NaN,   Number.NEGATIVE_INFINITY % Number.NEGATIVE_INFINITY );
this.TestCase( SECTION,    "Number.POSITIVE_INFINITY % Number.NEGATIVE_INFINITY",    Number.NaN,   Number.POSITIVE_INFINITY % Number.NEGATIVE_INFINITY );
this.TestCase( SECTION,    "Number.NEGATIVE_INFINITY % Number.POSITIVE_INFINITY",    Number.NaN,   Number.NEGATIVE_INFINITY % Number.POSITIVE_INFINITY );
this.TestCase( SECTION,    "Number.POSITIVE_INFINITY % Number.POSITIVE_INFINITY",    Number.NaN,   Number.POSITIVE_INFINITY % Number.POSITIVE_INFINITY );

this.TestCase( SECTION,    "Number.POSITIVE_INFINITY % 0",   Number.NaN,     Number.POSITIVE_INFINITY % 0 );
this.TestCase( SECTION,    "Number.NEGATIVE_INFINITY % 0",   Number.NaN,     Number.NEGATIVE_INFINITY % 0 );
this.TestCase( SECTION,    "Number.POSITIVE_INFINITY % -0",  Number.NaN,     Number.POSITIVE_INFINITY % -0 );
this.TestCase( SECTION,    "Number.NEGATIVE_INFINITY % -0",  Number.NaN,     Number.NEGATIVE_INFINITY % -0 );

this.TestCase( SECTION,    "Number.NEGATIVE_INFINITY % 1 ",  Number.NaN,     Number.NEGATIVE_INFINITY % 1 );
this.TestCase( SECTION,    "Number.NEGATIVE_INFINITY % -1 ", Number.NaN,     Number.NEGATIVE_INFINITY % -1 );
this.TestCase( SECTION,    "Number.POSITIVE_INFINITY % 1 ",  Number.NaN,     Number.POSITIVE_INFINITY % 1 );
this.TestCase( SECTION,    "Number.POSITIVE_INFINITY % -1 ", Number.NaN,     Number.POSITIVE_INFINITY % -1 );

this.TestCase( SECTION,    "Number.NEGATIVE_INFINITY % Number.MAX_VALUE ",   Number.NaN,   Number.NEGATIVE_INFINITY % Number.MAX_VALUE );
this.TestCase( SECTION,    "Number.NEGATIVE_INFINITY % -Number.MAX_VALUE ",  Number.NaN,   Number.NEGATIVE_INFINITY % -Number.MAX_VALUE );
this.TestCase( SECTION,    "Number.POSITIVE_INFINITY % Number.MAX_VALUE ",   Number.NaN,   Number.POSITIVE_INFINITY % Number.MAX_VALUE );
this.TestCase( SECTION,    "Number.POSITIVE_INFINITY % -Number.MAX_VALUE ",  Number.NaN,   Number.POSITIVE_INFINITY % -Number.MAX_VALUE );

// divisor is 0
this.TestCase( SECTION,    "0 % -0",                         Number.NaN,     0 % -0 );
this.TestCase( SECTION,    "-0 % 0",                         Number.NaN,     -0 % 0 );
this.TestCase( SECTION,    "-0 % -0",                        Number.NaN,     -0 % -0 );
this.TestCase( SECTION,    "0 % 0",                          Number.NaN,     0 % 0 );

this.TestCase( SECTION,    "1 % 0",                          Number.NaN,   1%0 );
this.TestCase( SECTION,    "1 % -0",                         Number.NaN,   1%-0 );
this.TestCase( SECTION,    "-1 % 0",                         Number.NaN,   -1%0 );
this.TestCase( SECTION,    "-1 % -0",                        Number.NaN,   -1%-0 );

this.TestCase( SECTION,    "Number.MAX_VALUE % 0",           Number.NaN,   Number.MAX_VALUE%0 );
this.TestCase( SECTION,    "Number.MAX_VALUE % -0",          Number.NaN,   Number.MAX_VALUE%-0 );
this.TestCase( SECTION,    "-Number.MAX_VALUE % 0",          Number.NaN,   -Number.MAX_VALUE%0 );
this.TestCase( SECTION,    "-Number.MAX_VALUE % -0",         Number.NaN,   -Number.MAX_VALUE%-0 );

// If the dividend is finite and the divisor is an infinity, the result equals the dividend.

this.TestCase( SECTION,    "1 % Number.NEGATIVE_INFINITY",   1,              1 % Number.NEGATIVE_INFINITY );
this.TestCase( SECTION,    "1 % Number.POSITIVE_INFINITY",   1,              1 % Number.POSITIVE_INFINITY );
this.TestCase( SECTION,    "-1 % Number.POSITIVE_INFINITY",  -1,             -1 % Number.POSITIVE_INFINITY );
this.TestCase( SECTION,    "-1 % Number.NEGATIVE_INFINITY",  -1,             -1 % Number.NEGATIVE_INFINITY );

this.TestCase( SECTION,    "Number.MAX_VALUE % Number.NEGATIVE_INFINITY",   Number.MAX_VALUE,    Number.MAX_VALUE % Number.NEGATIVE_INFINITY );
this.TestCase( SECTION,    "Number.MAX_VALUE % Number.POSITIVE_INFINITY",   Number.MAX_VALUE,    Number.MAX_VALUE % Number.POSITIVE_INFINITY );
this.TestCase( SECTION,    "-Number.MAX_VALUE % Number.POSITIVE_INFINITY",  -Number.MAX_VALUE,   -Number.MAX_VALUE % Number.POSITIVE_INFINITY );
this.TestCase( SECTION,    "-Number.MAX_VALUE % Number.NEGATIVE_INFINITY",  -Number.MAX_VALUE,   -Number.MAX_VALUE % Number.NEGATIVE_INFINITY );

this.TestCase( SECTION,    "0 % Number.POSITIVE_INFINITY",   0, 0 % Number.POSITIVE_INFINITY );
this.TestCase( SECTION,    "0 % Number.NEGATIVE_INFINITY",   0, 0 % Number.NEGATIVE_INFINITY );
this.TestCase( SECTION,    "-0 % Number.POSITIVE_INFINITY",  -0,   -0 % Number.POSITIVE_INFINITY );
this.TestCase( SECTION,    "-0 % Number.NEGATIVE_INFINITY",  -0,   -0 % Number.NEGATIVE_INFINITY );

// If the dividend is a zero and the divisor is finite, the result is the same as the dividend.

this.TestCase( SECTION,    "0 % 1",                          0,              0 % 1 );
this.TestCase( SECTION,    "0 % -1",                        -0,              0 % -1 );
this.TestCase( SECTION,    "-0 % 1",                        -0,              -0 % 1 );
this.TestCase( SECTION,    "-0 % -1",                       0,               -0 % -1 );

//        In the remaining cases, where neither an infinity, nor a zero, nor NaN is involved, the floating-point remainder r
//      from a dividend n and a divisor d is defined by the mathematical relation r = n (d * q) where q is an integer that
//      is negative only if n/d is negative and positive only if n/d is positive, and whose magnitude is as large as
//      possible without exceeding the magnitude of the true mathematical quotient of n and d.

//test();

},

/**
File Name:          11.6.1-1.js
ECMA Section:       11.6.1 The addition operator ( + )
Description:

The addition operator either performs string concatenation or numeric
addition.

The production AdditiveExpression : AdditiveExpression + MultiplicativeExpression
is evaluated as follows:

1.  Evaluate AdditiveExpression.
2.  Call GetValue(Result(1)).
3.  Evaluate MultiplicativeExpression.
4.  Call GetValue(Result(3)).
5.  Call ToPrimitive(Result(2)).
6.  Call ToPrimitive(Result(4)).
7.  If Type(Result(5)) is String or Type(Result(6)) is String, go to step 12.
(Note that this step differs from step 3 in the algorithm for comparison
for the relational operators in using or instead of and.)
8.  Call ToNumber(Result(5)).
9.  Call ToNumber(Result(6)).
10. Apply the addition operation to Result(8) and Result(9). See the discussion below (11.6.3).
11. Return Result(10).
12. Call ToString(Result(5)).
13. Call ToString(Result(6)).
14. Concatenate Result(12) followed by Result(13).
15. Return Result(14).

Note that no hint is provided in the calls to ToPrimitive in steps 5 and 6.
All native ECMAScript objects except Date objects handle the absence of a
hint as if the hint Number were given; Date objects handle the absence of a
hint as if the hint String were given. Host objects may handle the absence
of a hint in some other manner.

This test does not cover cases where the Additive or Mulplicative expression
ToPrimitive is string.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_6_1__1: function() {
var SECTION = "11.6.1-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " The Addition operator ( + )");

// tests for boolean primitive, boolean object, Object object, a "MyObject" whose value is
// a boolean primitive and a boolean object.

this.TestCase(   SECTION,
"var EXP_1 = true; var EXP_2 = false; EXP_1 + EXP_2",
1,
eval("var EXP_1 = true; var EXP_2 = false; EXP_1 + EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new Boolean(true); var EXP_2 = new Boolean(false); EXP_1 + EXP_2",
1,
eval("var EXP_1 = new Boolean(true); var EXP_2 = new Boolean(false); EXP_1 + EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new Object(true); var EXP_2 = new Object(false); EXP_1 + EXP_2",
1,
eval("var EXP_1 = new Object(true); var EXP_2 = new Object(false); EXP_1 + EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new Object(new Boolean(true)); var EXP_2 = new Object(new Boolean(false)); EXP_1 + EXP_2",
1,
eval("var EXP_1 = new Object(new Boolean(true)); var EXP_2 = new Object(new Boolean(false)); EXP_1 + EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new MyObject(true); var EXP_2 = new MyObject(false); EXP_1 + EXP_2",
1,
eval("var EXP_1 = new MyObject(true); var EXP_2 = new MyObject(false); EXP_1 + EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new MyObject(new Boolean(true)); var EXP_2 = new MyObject(new Boolean(false)); EXP_1 + EXP_2",
"[object Object][object Object]",
eval("var EXP_1 = new MyObject(new Boolean(true)); var EXP_2 = new MyObject(new Boolean(false)); EXP_1 + EXP_2") );

// tests for number primitive, number object, Object object, a "MyObject" whose value is
// a number primitive and a number object.

this.TestCase(   SECTION,
"var EXP_1 = 100; var EXP_2 = -1; EXP_1 + EXP_2",
99,
eval("var EXP_1 = 100; var EXP_2 = -1; EXP_1 + EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new Number(100); var EXP_2 = new Number(-1); EXP_1 + EXP_2",
99,
eval("var EXP_1 = new Number(100); var EXP_2 = new Number(-1); EXP_1 + EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new Object(100); var EXP_2 = new Object(-1); EXP_1 + EXP_2",
99,
eval("var EXP_1 = new Object(100); var EXP_2 = new Object(-1); EXP_1 + EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new Object(new Number(100)); var EXP_2 = new Object(new Number(-1)); EXP_1 + EXP_2",
99,
eval("var EXP_1 = new Object(new Number(100)); var EXP_2 = new Object(new Number(-1)); EXP_1 + EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new MyObject(100); var EXP_2 = new MyObject(-1); EXP_1 + EXP_2",
99,
eval("var EXP_1 = new MyObject(100); var EXP_2 = new MyObject(-1); EXP_1 + EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new MyObject(new Number(100)); var EXP_2 = new MyObject(new Number(-1)); EXP_1 + EXP_2",
"[object Object][object Object]",
eval("var EXP_1 = new MyObject(new Number(100)); var EXP_2 = new MyObject(new Number(-1)); EXP_1 + EXP_2") );


//test();

function MyObject( value ) {
this.valueOf = new Function( "return this.value" );
this.value = value;
}

},

/**
File Name:          11.6.1-2.js
ECMA Section:       11.6.1 The addition operator ( + )
Description:

The addition operator either performs string concatenation or numeric
addition.

The production AdditiveExpression : AdditiveExpression + MultiplicativeExpression
is evaluated as follows:

1.  Evaluate AdditiveExpression.
2.  Call GetValue(Result(1)).
3.  Evaluate MultiplicativeExpression.
4.  Call GetValue(Result(3)).
5.  Call ToPrimitive(Result(2)).
6.  Call ToPrimitive(Result(4)).
7.  If Type(Result(5)) is String or Type(Result(6)) is String, go to step 12.
(Note that this step differs from step 3 in the algorithm for comparison
for the relational operators in using or instead of and.)
8.  Call ToNumber(Result(5)).
9.  Call ToNumber(Result(6)).
10. Apply the addition operation to Result(8) and Result(9). See the discussion below (11.6.3).
11. Return Result(10).
12. Call ToString(Result(5)).
13. Call ToString(Result(6)).
14. Concatenate Result(12) followed by Result(13).
15. Return Result(14).

Note that no hint is provided in the calls to ToPrimitive in steps 5 and 6.
All native ECMAScript objects except Date objects handle the absence of a
hint as if the hint Number were given; Date objects handle the absence of a
hint as if the hint String were given. Host objects may handle the absence
of a hint in some other manner.

This test does only covers cases where the Additive or Mulplicative expression
ToPrimitive is a string.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_6_1__2: function() {
var SECTION = "11.6.1-2";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " The Addition operator ( + )");

// tests for boolean primitive, boolean object, Object object, a "MyObject" whose value is
// a boolean primitive and a boolean object.

this.TestCase(   SECTION,
"var EXP_1 = 'string'; var EXP_2 = false; EXP_1 + EXP_2",
"stringfalse",
eval("var EXP_1 = 'string'; var EXP_2 = false; EXP_1 + EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = true; var EXP_2 = 'string'; EXP_1 + EXP_2",
"truestring",
eval("var EXP_1 = true; var EXP_2 = 'string'; EXP_1 + EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new Boolean(true); var EXP_2 = new String('string'); EXP_1 + EXP_2",
"truestring",
eval("var EXP_1 = new Boolean(true); var EXP_2 = new String('string'); EXP_1 + EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new Object(true); var EXP_2 = new Object('string'); EXP_1 + EXP_2",
"truestring",
eval("var EXP_1 = new Object(true); var EXP_2 = new Object('string'); EXP_1 + EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new Object(new String('string')); var EXP_2 = new Object(new Boolean(false)); EXP_1 + EXP_2",
"stringfalse",
eval("var EXP_1 = new Object(new String('string')); var EXP_2 = new Object(new Boolean(false)); EXP_1 + EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new MyObject(true); var EXP_2 = new MyObject('string'); EXP_1 + EXP_2",
"truestring",
eval("var EXP_1 = new MyObject(true); var EXP_2 = new MyObject('string'); EXP_1 + EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new MyObject(new String('string')); var EXP_2 = new MyObject(new Boolean(false)); EXP_1 + EXP_2",
"[object Object][object Object]",
eval("var EXP_1 = new MyObject(new String('string')); var EXP_2 = new MyObject(new Boolean(false)); EXP_1 + EXP_2") );

// tests for number primitive, number object, Object object, a "MyObject" whose value is
// a number primitive and a number object.

this.TestCase(   SECTION,
"var EXP_1 = 100; var EXP_2 = 'string'; EXP_1 + EXP_2",
"100string",
eval("var EXP_1 = 100; var EXP_2 = 'string'; EXP_1 + EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new String('string'); var EXP_2 = new Number(-1); EXP_1 + EXP_2",
"string-1",
eval("var EXP_1 = new String('string'); var EXP_2 = new Number(-1); EXP_1 + EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new Object(100); var EXP_2 = new Object('string'); EXP_1 + EXP_2",
"100string",
eval("var EXP_1 = new Object(100); var EXP_2 = new Object('string'); EXP_1 + EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new Object(new String('string')); var EXP_2 = new Object(new Number(-1)); EXP_1 + EXP_2",
"string-1",
eval("var EXP_1 = new Object(new String('string')); var EXP_2 = new Object(new Number(-1)); EXP_1 + EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new MyObject(100); var EXP_2 = new MyObject('string'); EXP_1 + EXP_2",
"100string",
eval("var EXP_1 = new MyObject(100); var EXP_2 = new MyObject('string'); EXP_1 + EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new MyObject(new String('string')); var EXP_2 = new MyObject(new Number(-1)); EXP_1 + EXP_2",
"[object Object][object Object]",
eval("var EXP_1 = new MyObject(new String('string')); var EXP_2 = new MyObject(new Number(-1)); EXP_1 + EXP_2") );

//test();

function MyObject( value ) {
this.valueOf = new Function( "return this.value" );
this.value = value;
}

},

/**
File Name:          11.6.1-3.js
ECMA Section:       11.6.1 The addition operator ( + )
Description:

The addition operator either performs string concatenation or numeric
addition.

The production AdditiveExpression : AdditiveExpression + MultiplicativeExpression
is evaluated as follows:

1.  Evaluate AdditiveExpression.
2.  Call GetValue(Result(1)).
3.  Evaluate MultiplicativeExpression.
4.  Call GetValue(Result(3)).
5.  Call ToPrimitive(Result(2)).
6.  Call ToPrimitive(Result(4)).
7.  If Type(Result(5)) is String or Type(Result(6)) is String, go to step 12.
(Note that this step differs from step 3 in the algorithm for comparison
for the relational operators in using or instead of and.)
8.  Call ToNumber(Result(5)).
9.  Call ToNumber(Result(6)).
10. Apply the addition operation to Result(8) and Result(9). See the discussion below (11.6.3).
11. Return Result(10).
12. Call ToString(Result(5)).
13. Call ToString(Result(6)).
14. Concatenate Result(12) followed by Result(13).
15. Return Result(14).

Note that no hint is provided in the calls to ToPrimitive in steps 5 and 6.
All native ECMAScript objects except Date objects handle the absence of a
hint as if the hint Number were given; Date objects handle the absence of a
hint as if the hint String were given. Host objects may handle the absence
of a hint in some other manner.

This test does only covers cases where the Additive or Mulplicative expression
is a Date.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_6_1__3: function() {
var SECTION = "11.6.1-3";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " "+ TITLE);

// tests for boolean primitive, boolean object, Object object, a "MyObject" whose value is
// a boolean primitive and a boolean object.

var DATE1 = new Date();

this.TestCase(   SECTION,
"var DATE1 = new Date(); DATE1 + DATE1",
DATE1.toString() + DATE1.toString(),
DATE1 + DATE1 );

this.TestCase(   SECTION,
"var DATE1 = new Date(); DATE1 + 0",
DATE1.toString() + 0,
DATE1 + 0 );

this.TestCase(   SECTION,
"var DATE1 = new Date(); DATE1 + new Number(0)",
DATE1.toString() + 0,
DATE1 + new Number(0) );

this.TestCase(   SECTION,
"var DATE1 = new Date(); DATE1 + true",
DATE1.toString() + "true",
DATE1 + true );

this.TestCase(   SECTION,
"var DATE1 = new Date(); DATE1 + new Boolean(true)",
DATE1.toString() + "true",
DATE1 + new Boolean(true) );

this.TestCase(   SECTION,
"var DATE1 = new Date(); DATE1 + new Boolean(true)",
DATE1.toString() + "true",
DATE1 + new Boolean(true) );

var MYOB1 = new MyObject( DATE1 );

this.TestCase(   SECTION,
"MYOB1 = new MyObject(DATE1); MYOB1 + new Number(1)",
"[object Object]1",
MYOB1 + new Number(1) );

this.TestCase(   SECTION,
"MYOB1 = new MyObject(DATE1); MYOB1 + 1",
"[object Object]1",
MYOB1 + 1 );

this.TestCase(   SECTION,
"MYOB1 = new MyObject(DATE1); MYOB1 + true",
"[object Object]true",
MYOB1 + true );

//test();

function MyPrototypeObject(value) {
this.valueOf = new Function( "return this.value;" );
this.toString = new Function( "return (this.value + '');" );
this.value = value;
}
function MyObject( value ) {
this.valueOf = new Function( "return this.value" );
this.value = value;
}

},

/**
File Name:          11.6.2-1.js
ECMA Section:       11.6.2 The Subtraction operator ( - )
Description:

The production AdditiveExpression : AdditiveExpression -
MultiplicativeExpression is evaluated as follows:

1.  Evaluate AdditiveExpression.
2.  Call GetValue(Result(1)).
3.  Evaluate MultiplicativeExpression.
4.  Call GetValue(Result(3)).
5.  Call ToNumber(Result(2)).
6.  Call ToNumber(Result(4)).
7.  Apply the subtraction operation to Result(5) and Result(6). See the
discussion below (11.6.3).
8.  Return Result(7).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_6_2__1: function() {
var SECTION = "11.6.2-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " The subtraction operator ( - )");

// tests for boolean primitive, boolean object, Object object, a "MyObject" whose value is
// a boolean primitive and a boolean object.

this.TestCase(   SECTION,
"var EXP_1 = true; var EXP_2 = false; EXP_1 - EXP_2",
1,
eval("var EXP_1 = true; var EXP_2 = false; EXP_1 - EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new Boolean(true); var EXP_2 = new Boolean(false); EXP_1 - EXP_2",
1,
eval("var EXP_1 = new Boolean(true); var EXP_2 = new Boolean(false); EXP_1 - EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new Object(true); var EXP_2 = new Object(false); EXP_1 - EXP_2",
1,
eval("var EXP_1 = new Object(true); var EXP_2 = new Object(false); EXP_1 - EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new Object(new Boolean(true)); var EXP_2 = new Object(new Boolean(false)); EXP_1 - EXP_2",
1,
eval("var EXP_1 = new Object(new Boolean(true)); var EXP_2 = new Object(new Boolean(false)); EXP_1 - EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new MyObject(true); var EXP_2 = new MyObject(false); EXP_1 - EXP_2",
1,
eval("var EXP_1 = new MyObject(true); var EXP_2 = new MyObject(false); EXP_1 - EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new MyObject(new Boolean(true)); var EXP_2 = new MyObject(new Boolean(false)); EXP_1 - EXP_2",
Number.NaN,
eval("var EXP_1 = new MyObject(new Boolean(true)); var EXP_2 = new MyObject(new Boolean(false)); EXP_1 - EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new MyOtherObject(new Boolean(true)); var EXP_2 = new MyOtherObject(new Boolean(false)); EXP_1 - EXP_2",
Number.NaN,
eval("var EXP_1 = new MyOtherObject(new Boolean(true)); var EXP_2 = new MyOtherObject(new Boolean(false)); EXP_1 - EXP_2") );

// tests for number primitive, number object, Object object, a "MyObject" whose value is
// a number primitive and a number object.

this.TestCase(   SECTION,
"var EXP_1 = 100; var EXP_2 = 1; EXP_1 - EXP_2",
99,
eval("var EXP_1 = 100; var EXP_2 = 1; EXP_1 - EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new Number(100); var EXP_2 = new Number(1); EXP_1 - EXP_2",
99,
eval("var EXP_1 = new Number(100); var EXP_2 = new Number(1); EXP_1 - EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new Object(100); var EXP_2 = new Object(1); EXP_1 - EXP_2",
99,
eval("var EXP_1 = new Object(100); var EXP_2 = new Object(1); EXP_1 - EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new Object(new Number(100)); var EXP_2 = new Object(new Number(1)); EXP_1 - EXP_2",
99,
eval("var EXP_1 = new Object(new Number(100)); var EXP_2 = new Object(new Number(1)); EXP_1 - EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new MyObject(100); var EXP_2 = new MyObject(1); EXP_1 - EXP_2",
99,
eval("var EXP_1 = new MyObject(100); var EXP_2 = new MyObject(1); EXP_1 - EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new MyObject(new Number(100)); var EXP_2 = new MyObject(new Number(1)); EXP_1 - EXP_2",
Number.NaN,
eval("var EXP_1 = new MyObject(new Number(100)); var EXP_2 = new MyObject(new Number(1)); EXP_1 - EXP_2") );

this.TestCase(   SECTION,
"var EXP_1 = new MyOtherObject(new Number(100)); var EXP_2 = new MyOtherObject(new Number(1)); EXP_1 - EXP_2",
99,
eval("var EXP_1 = new MyOtherObject(new Number(100)); var EXP_2 = new MyOtherObject(new Number(1)); EXP_1 - EXP_2") );

// same thing with string!
this.TestCase(   SECTION,
"var EXP_1 = new MyOtherObject(new String('0xff')); var EXP_2 = new MyOtherObject(new String('1'); EXP_1 - EXP_2",
254,
eval("var EXP_1 = new MyOtherObject(new String('0xff')); var EXP_2 = new MyOtherObject(new String('1')); EXP_1 - EXP_2") );

//test();

function MyPrototypeObject(value) {
this.valueOf = new Function( "return this.value;" );
this.toString = new Function( "return (this.value + '');" );
this.value = value;
}
function MyObject( value ) {
this.valueOf = new Function( "return this.value" );
this.value = value;
}
function MyOtherObject( value ) {
this.valueOf = new Function( "return this.value" );
this.toString = new Function ( "return this.value + ''" );
this.value = value;
}

},

/**
File Name:          11.6.3.js
ECMA Section:       11.6.3 Applying the additive operators
(+, -) to numbers
Description:
The + operator performs addition when applied to two operands of numeric
type, producing the sum of the operands. The - operator performs
subtraction, producing the difference of two numeric operands.

Addition is a commutative operation, but not always associative.

The result of an addition is determined using the rules of IEEE 754
double-precision arithmetic:

If either operand is NaN, the result is NaN.
The sum of two infinities of opposite sign is NaN.
The sum of two infinities of the same sign is the infinity of that sign.
The sum of an infinity and a finite value is equal to the infinite operand.
The sum of two negative zeros is 0. The sum of two positive zeros, or of
two zeros of opposite sign, is +0.
The sum of a zero and a nonzero finite value is equal to the nonzero
operand.
The sum of two nonzero finite values of the same magnitude and opposite
sign is +0.
In the remaining cases, where neither an infinity, nor a zero, nor NaN is
involved, and the operands have the same sign or have different
magnitudes, the sum is computed and rounded to the nearest
representable value using IEEE 754 round-to-nearest mode. If the
magnitude is too large to represent, the operation overflows and
the result is then an infinity of appropriate sign. The ECMAScript
language requires support of gradual underflow as defined by IEEE 754.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_6_3: function() {
var SECTION = "11.6.3";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Applying the additive operators (+,-) to numbers");

this.TestCase( SECTION,    "Number.NaN + 1",     Number.NaN,     Number.NaN + 1 );
this.TestCase( SECTION,    "1 + Number.NaN",     Number.NaN,     1 + Number.NaN );

this.TestCase( SECTION,    "Number.NaN - 1",     Number.NaN,     Number.NaN - 1 );
this.TestCase( SECTION,    "1 - Number.NaN",     Number.NaN,     1 - Number.NaN );

this.TestCase( SECTION,  "Number.POSITIVE_INFINITY + Number.POSITIVE_INFINITY",  Number.POSITIVE_INFINITY,   Number.POSITIVE_INFINITY + Number.POSITIVE_INFINITY);
this.TestCase( SECTION,  "Number.NEGATIVE_INFINITY + Number.NEGATIVE_INFINITY",  Number.NEGATIVE_INFINITY,   Number.NEGATIVE_INFINITY + Number.NEGATIVE_INFINITY);

this.TestCase( SECTION,  "Number.POSITIVE_INFINITY + Number.NEGATIVE_INFINITY",  Number.NaN,     Number.POSITIVE_INFINITY + Number.NEGATIVE_INFINITY);
this.TestCase( SECTION,  "Number.NEGATIVE_INFINITY + Number.POSITIVE_INFINITY",  Number.NaN,     Number.NEGATIVE_INFINITY + Number.POSITIVE_INFINITY);

this.TestCase( SECTION,  "Number.POSITIVE_INFINITY - Number.POSITIVE_INFINITY",  Number.NaN,   Number.POSITIVE_INFINITY - Number.POSITIVE_INFINITY);
this.TestCase( SECTION,  "Number.NEGATIVE_INFINITY - Number.NEGATIVE_INFINITY",  Number.NaN,   Number.NEGATIVE_INFINITY - Number.NEGATIVE_INFINITY);

this.TestCase( SECTION,  "Number.POSITIVE_INFINITY - Number.NEGATIVE_INFINITY",  Number.POSITIVE_INFINITY,   Number.POSITIVE_INFINITY - Number.NEGATIVE_INFINITY);
this.TestCase( SECTION,  "Number.NEGATIVE_INFINITY - Number.POSITIVE_INFINITY",  Number.NEGATIVE_INFINITY,   Number.NEGATIVE_INFINITY - Number.POSITIVE_INFINITY);

this.TestCase( SECTION,  "-0 + -0",      -0,     -0 + -0 );
this.TestCase( SECTION,  "-0 - 0",       -0,     -0 - 0 );

this.TestCase( SECTION,  "0 + 0",        0,      0 + 0 );
this.TestCase( SECTION,  "0 + -0",       0,      0 + -0 );
this.TestCase( SECTION,  "0 - -0",       0,      0 - -0 );
this.TestCase( SECTION,  "0 - 0",        0,      0 - 0 );
this.TestCase( SECTION,  "-0 - -0",      0,     -0 - -0 );
this.TestCase( SECTION,  "-0 + 0",       0,     -0 + 0 );

this.TestCase( SECTION,  "Number.MAX_VALUE - Number.MAX_VALUE",      0,  Number.MAX_VALUE - Number.MAX_VALUE );
this.TestCase( SECTION,  "1/Number.MAX_VALUE - 1/Number.MAX_VALUE",  0,  1/Number.MAX_VALUE - 1/Number.MAX_VALUE );

this.TestCase( SECTION,  "Number.MIN_VALUE - Number.MIN_VALUE",      0,  Number.MIN_VALUE - Number.MIN_VALUE );

//test();

},

/**
File Name:          11.7.1.js
ECMA Section:       11.7.1 The Left Shift Operator ( << )
Description:
Performs a bitwise left shift operation on the left argument by the amount
specified by the right argument.

The production ShiftExpression : ShiftExpression << AdditiveExpression is
evaluated as follows:

1.  Evaluate ShiftExpression.
2.  Call GetValue(Result(1)).
3.  Evaluate AdditiveExpression.
4.  Call GetValue(Result(3)).
5.  Call ToInt32(Result(2)).
6.  Call ToUint32(Result(4)).
7.  Mask out all but the least significant 5 bits of Result(6), that is,
compute Result(6) & 0x1F.
8.  Left shift Result(5) by Result(7) bits. The result is a signed 32 bit
integer.
9.  Return Result(8).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_7_1: function() {
var SECTION = "11.7.1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " The left shift operator ( << )");

for ( power = 0; power < 33; power++ ) {
shiftexp = Math.pow( 2, power );

for ( addexp = 0; addexp < 33; addexp++ ) {
this.TestCase( SECTION,
shiftexp + " << " + addexp,
LeftShift( shiftexp, addexp ),
shiftexp << addexp );
}
}

//test();

function ToInteger( n ) {
n = Number( n );
var sign = ( n < 0 ) ? -1 : 1;

if ( n != n ) {
return 0;
}
if ( Math.abs( n ) == 0 || Math.abs( n ) == Number.POSITIVE_INFINITY ) {
return n;
}
return ( sign * Math.floor(Math.abs(n)) );
}
function ToInt32( n ) {
n = Number( n );
var sign = ( n < 0 ) ? -1 : 1;

if ( Math.abs( n ) == 0 || Math.abs( n ) == Number.POSITIVE_INFINITY) {
return 0;
}

n = (sign * Math.floor( Math.abs(n) )) % Math.pow(2,32);
n = ( n >= Math.pow(2,31) ) ? n - Math.pow(2,32) : n;

return ( n );
}
function ToUint32( n ) {
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
function ToUint16( n ) {
var sign = ( n < 0 ) ? -1 : 1;

if ( Math.abs( n ) == 0 || Math.abs( n ) == Number.POSITIVE_INFINITY) {
return 0;
}

n = ( sign * Math.floor( Math.abs(n) ) ) % Math.pow(2,16);

if (n <0) {
n += Math.pow(2,16);
}

return ( n );
}
function Mask( b, n ) {
b = ToUint32BitString( b );
b = b.substring( b.length - n );
b = ToUint32Decimal( b );
return ( b );
}
function ToUint32BitString( n ) {
var b = "";
for ( p = 31; p >=0; p-- ) {
if ( n >= Math.pow(2,p) ) {
b += "1";
n -= Math.pow(2,p);
} else {
b += "0";
}
}
return b;
}
function ToInt32BitString( n ) {
var b = "";
var sign = ( n < 0 ) ? -1 : 1;

b += ( sign == 1 ) ? "0" : "1";

for ( p = 30; p >=0; p-- ) {
if ( (sign == 1 ) ? sign * n >= Math.pow(2,p) : sign * n > Math.pow(2,p) ) {
b += ( sign == 1 ) ? "1" : "0";
n -= sign * Math.pow( 2, p );
} else {
b += ( sign == 1 ) ? "0" : "1";
}
}

return b;
}
function ToInt32Decimal( bin ) {
var r = 0;
var sign;

if ( Number(bin.charAt(0)) == 0 ) {
sign = 1;
r = 0;
} else {
sign = -1;
r = -(Math.pow(2,31));
}

for ( var j = 0; j < 31; j++ ) {
r += Math.pow( 2, j ) * Number(bin.charAt(31-j));
}

return r;
}
function ToUint32Decimal( bin ) {
var r = 0;


for ( l = bin.length; l < 32; l++ ) {
bin = "0" + bin;
}

for ( j = 0; j < 31; j++ ) {
r += Math.pow( 2, j ) * Number(bin.charAt(31-j));

}

return r;
}
function LeftShift( s, a ) {
var shift = ToInt32( s );
var add = ToUint32( a );
add = Mask( add, 5 );
var exp = LShift( shift, add );

return ( exp );
}
function LShift( s, a ) {
s = ToInt32BitString( s );

for ( var z = 0; z < a; z++ ) {
s += "0";
}

s = s.substring( a, s.length);

return ToInt32(ToInt32Decimal(s));
}
},

/**
File Name:          11.7.2.js
ECMA Section:       11.7.2  The signed right shift operator ( >> )
Description:
Performs a sign-filling bitwise right shift operation on the left argument
by the amount specified by the right argument.

The production ShiftExpression : ShiftExpression >> AdditiveExpression is
evaluated as follows:

1.  Evaluate ShiftExpression.
2.  Call GetValue(Result(1)).
3.  Evaluate AdditiveExpression.
4.  Call GetValue(Result(3)).
5.  Call ToInt32(Result(2)).
6.  Call ToUint32(Result(4)).
7.  Mask out all but the least significant 5 bits of Result(6), that is,
compute Result(6) & 0x1F.
8.  Perform sign-extending right shift of Result(5) by Result(7) bits. The
most significant bit is propagated. The result is a signed 32 bit
integer.
9.  Return Result(8).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_7_2: function() {
var SECTION = "11.7.2";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + "  The signed right shift operator ( >> )");

var power = 0;
var addexp = 0;

for ( power = 0; power <= 32; power++ ) {
shiftexp = Math.pow( 2, power );

for ( addexp = 0; addexp <= 32; addexp++ ) {
this.TestCase( SECTION,
shiftexp + " >> " + addexp,
SignedRightShift( shiftexp, addexp ),
shiftexp >> addexp );
}
}

for ( power = 0; power <= 32; power++ ) {
shiftexp = -Math.pow( 2, power );

for ( addexp = 0; addexp <= 32; addexp++ ) {
this.TestCase( SECTION,
shiftexp + " >> " + addexp,
SignedRightShift( shiftexp, addexp ),
shiftexp >> addexp );
}
}

//test();

function ToInteger( n ) {
n = Number( n );
var sign = ( n < 0 ) ? -1 : 1;

if ( n != n ) {
return 0;
}
if ( Math.abs( n ) == 0 || Math.abs( n ) == Number.POSITIVE_INFINITY ) {
return n;
}
return ( sign * Math.floor(Math.abs(n)) );
}
function ToInt32( n ) {
n = Number( n );
var sign = ( n < 0 ) ? -1 : 1;

if ( Math.abs( n ) == 0 || Math.abs( n ) == Number.POSITIVE_INFINITY) {
return 0;
}

n = (sign * Math.floor( Math.abs(n) )) % Math.pow(2,32);
n = ( n >= Math.pow(2,31) ) ? n - Math.pow(2,32) : n;

return ( n );
}
function ToUint32( n ) {
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
function ToUint16( n ) {
var sign = ( n < 0 ) ? -1 : 1;

if ( Math.abs( n ) == 0 || Math.abs( n ) == Number.POSITIVE_INFINITY) {
return 0;
}

n = ( sign * Math.floor( Math.abs(n) ) ) % Math.pow(2,16);

if (n <0) {
n += Math.pow(2,16);
}

return ( n );
}
function Mask( b, n ) {
b = ToUint32BitString( b );
b = b.substring( b.length - n );
b = ToUint32Decimal( b );
return ( b );
}
function ToUint32BitString( n ) {
var b = "";
for ( p = 31; p >=0; p-- ) {
if ( n >= Math.pow(2,p) ) {
b += "1";
n -= Math.pow(2,p);
} else {
b += "0";
}
}
return b;
}
function ToInt32BitString( n ) {
var b = "";
var sign = ( n < 0 ) ? -1 : 1;

b += ( sign == 1 ) ? "0" : "1";

for ( p = 30; p >=0; p-- ) {
if ( (sign == 1 ) ? sign * n >= Math.pow(2,p) : sign * n > Math.pow(2,p) ) {
b += ( sign == 1 ) ? "1" : "0";
n -= sign * Math.pow( 2, p );
} else {
b += ( sign == 1 ) ? "0" : "1";
}
}

return b;
}
function ToInt32Decimal( bin ) {
var r = 0;
var sign;

if ( Number(bin.charAt(0)) == 0 ) {
sign = 1;
r = 0;
} else {
sign = -1;
r = -(Math.pow(2,31));
}

for ( var j = 0; j < 31; j++ ) {
r += Math.pow( 2, j ) * Number(bin.charAt(31-j));
}

return r;
}
function ToUint32Decimal( bin ) {
var r = 0;

for ( l = bin.length; l < 32; l++ ) {
bin = "0" + bin;
}

for ( j = 0; j < 31; j++ ) {
r += Math.pow( 2, j ) * Number(bin.charAt(31-j));
}

return r;
}
function SignedRightShift( s, a ) {
s = ToInt32( s );
a = ToUint32( a );
a = Mask( a, 5 );
return ( SignedRShift( s, a ) );
}
function SignedRShift( s, a ) {
s = ToInt32BitString( s );

var firstbit = s.substring(0,1);

s = s.substring( 1, s.length );

for ( var z = 0; z < a; z++ ) {
s = firstbit + s;
}

s = s.substring( 0, s.length - a);

s = firstbit +s;


return ToInt32(ToInt32Decimal(s));
}

},

/**
File Name:          11.7.3.js
ECMA Section:       11.7.3  The unsigned right shift operator ( >>> )
Description:
11.7.3 The unsigned right shift operator ( >>> )
Performs a zero-filling bitwise right shift operation on the left argument
by the amount specified by the right argument.

The production ShiftExpression : ShiftExpression >>> AdditiveExpression is
evaluated as follows:

1.  Evaluate ShiftExpression.
2.  Call GetValue(Result(1)).
3.  Evaluate AdditiveExpression.
4.  Call GetValue(Result(3)).
5.  Call ToUint32(Result(2)).
6.  Call ToUint32(Result(4)).
7.  Mask out all but the least significant 5 bits of Result(6), that is,
compute Result(6) & 0x1F.
8.  Perform zero-filling right shift of Result(5) by Result(7) bits.
Vacated bits are filled with zero. The result is an unsigned 32 bit
integer.
9.  Return Result(8).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_7_3: function() {
var SECTION = "11.7.3";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + "  The unsigned right shift operator ( >>> )");

var addexp = 0;
var power = 0;

for ( power = 0; power <= 32; power++ ) {
shiftexp = Math.pow( 2, power );

for ( addexp = 0; addexp <= 32; addexp++ ) {
this.TestCase( SECTION,
shiftexp + " >>> " + addexp,
UnsignedRightShift( shiftexp, addexp ),
shiftexp >>> addexp );
}
}

//test();

function ToInteger( n ) {
n = Number( n );
var sign = ( n < 0 ) ? -1 : 1;

if ( n != n ) {
return 0;
}
if ( Math.abs( n ) == 0 || Math.abs( n ) == Number.POSITIVE_INFINITY ) {
return n;
}
return ( sign * Math.floor(Math.abs(n)) );
}
function ToInt32( n ) {
n = Number( n );
var sign = ( n < 0 ) ? -1 : 1;

if ( Math.abs( n ) == 0 || Math.abs( n ) == Number.POSITIVE_INFINITY) {
return 0;
}

n = (sign * Math.floor( Math.abs(n) )) % Math.pow(2,32);
n = ( n >= Math.pow(2,31) ) ? n - Math.pow(2,32) : n;

return ( n );
}
function ToUint32( n ) {
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
function ToUint16( n ) {
var sign = ( n < 0 ) ? -1 : 1;

if ( Math.abs( n ) == 0 || Math.abs( n ) == Number.POSITIVE_INFINITY) {
return 0;
}

n = ( sign * Math.floor( Math.abs(n) ) ) % Math.pow(2,16);

if (n <0) {
n += Math.pow(2,16);
}

return ( n );
}
function Mask( b, n ) {
b = ToUint32BitString( b );
b = b.substring( b.length - n );
b = ToUint32Decimal( b );
return ( b );
}
function ToUint32BitString( n ) {
var b = "";
for ( p = 31; p >=0; p-- ) {
if ( n >= Math.pow(2,p) ) {
b += "1";
n -= Math.pow(2,p);
} else {
b += "0";
}
}
return b;
}
function ToInt32BitString( n ) {
var b = "";
var sign = ( n < 0 ) ? -1 : 1;

b += ( sign == 1 ) ? "0" : "1";

for ( p = 30; p >=0; p-- ) {
if ( (sign == 1 ) ? sign * n >= Math.pow(2,p) : sign * n > Math.pow(2,p) ) {
b += ( sign == 1 ) ? "1" : "0";
n -= sign * Math.pow( 2, p );
} else {
b += ( sign == 1 ) ? "0" : "1";
}
}

return b;
}
function ToInt32Decimal( bin ) {
var r = 0;
var sign;

if ( Number(bin.charAt(0)) == 0 ) {
sign = 1;
r = 0;
} else {
sign = -1;
r = -(Math.pow(2,31));
}

for ( var j = 0; j < 31; j++ ) {
r += Math.pow( 2, j ) * Number(bin.charAt(31-j));
}

return r;
}
function ToUint32Decimal( bin ) {
var r = 0;


for ( l = bin.length; l < 32; l++ ) {
bin = "0" + bin;
}

for ( j = 0; j < 32; j++ ) {
r += Math.pow( 2, j ) * Number(bin.charAt(31-j));

}

return r;
}
function RShift( s, a ) {
s = ToUint32BitString( s );
for ( z = 0; z < a; z++ ) {
s = "0" + s;
}
s = s.substring( 0, s.length - a );

return ToUint32Decimal(s);
}
function UnsignedRightShift( s, a ) {
s = ToUint32( s );
a = ToUint32( a );
a = Mask( a, 5 );
return ( RShift( s, a ) );
}

},

/**
File Name:          11.8.1.js
ECMA Section:       11.8.1  The less-than operator ( < )
Description:


Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_8_1: function() {
var SECTION = "11.8.1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " The less-than operator ( < )");

this.TestCase( SECTION, "true < false",              false,      true < false );
this.TestCase( SECTION, "false < true",              true,       false < true );
this.TestCase( SECTION, "false < false",             false,      false < false );
this.TestCase( SECTION, "true < true",               false,      true < true );

this.TestCase( SECTION, "new Boolean(true) < new Boolean(true)",     false,  new Boolean(true) < new Boolean(true) );
this.TestCase( SECTION, "new Boolean(true) < new Boolean(false)",    false,  new Boolean(true) < new Boolean(false) );
this.TestCase( SECTION, "new Boolean(false) < new Boolean(true)",    true,   new Boolean(false) < new Boolean(true) );
this.TestCase( SECTION, "new Boolean(false) < new Boolean(false)",   false,  new Boolean(false) < new Boolean(false) );

this.TestCase( SECTION, "new MyObject(Infinity) < new MyObject(Infinity)",   false,  new MyObject( Number.POSITIVE_INFINITY ) < new MyObject( Number.POSITIVE_INFINITY) );
this.TestCase( SECTION, "new MyObject(-Infinity) < new MyObject(Infinity)",  true,   new MyObject( Number.NEGATIVE_INFINITY ) < new MyObject( Number.POSITIVE_INFINITY) );
this.TestCase( SECTION, "new MyObject(-Infinity) < new MyObject(-Infinity)", false,  new MyObject( Number.NEGATIVE_INFINITY ) < new MyObject( Number.NEGATIVE_INFINITY) );

this.TestCase( SECTION, "new MyValueObject(false) < new MyValueObject(true)",  true,   new MyValueObject(false) < new MyValueObject(true) );
this.TestCase( SECTION, "new MyValueObject(true) < new MyValueObject(true)",   false,  new MyValueObject(true) < new MyValueObject(true) );
this.TestCase( SECTION, "new MyValueObject(false) < new MyValueObject(false)", false,  new MyValueObject(false) < new MyValueObject(false) );

this.TestCase( SECTION, "new MyStringObject(false) < new MyStringObject(true)",  true,   new MyStringObject(false) < new MyStringObject(true) );
this.TestCase( SECTION, "new MyStringObject(true) < new MyStringObject(true)",   false,  new MyStringObject(true) < new MyStringObject(true) );
this.TestCase( SECTION, "new MyStringObject(false) < new MyStringObject(false)", false,  new MyStringObject(false) < new MyStringObject(false) );

this.TestCase( SECTION, "Number.NaN < Number.NaN",   false,     Number.NaN < Number.NaN );
this.TestCase( SECTION, "0 < Number.NaN",            false,     0 < Number.NaN );
this.TestCase( SECTION, "Number.NaN < 0",            false,     Number.NaN < 0 );

this.TestCase( SECTION, "0 < -0",                    false,      0 < -0 );
this.TestCase( SECTION, "-0 < 0",                    false,      -0 < 0 );

this.TestCase( SECTION, "Infinity < 0",                  false,      Number.POSITIVE_INFINITY < 0 );
this.TestCase( SECTION, "Infinity < Number.MAX_VALUE",   false,      Number.POSITIVE_INFINITY < Number.MAX_VALUE );
this.TestCase( SECTION, "Infinity < Infinity",           false,      Number.POSITIVE_INFINITY < Number.POSITIVE_INFINITY );

this.TestCase( SECTION, "0 < Infinity",                  true,       0 < Number.POSITIVE_INFINITY );
this.TestCase( SECTION, "Number.MAX_VALUE < Infinity",   true,       Number.MAX_VALUE < Number.POSITIVE_INFINITY );

this.TestCase( SECTION, "0 < -Infinity",                 false,      0 < Number.NEGATIVE_INFINITY );
this.TestCase( SECTION, "Number.MAX_VALUE < -Infinity",  false,      Number.MAX_VALUE < Number.NEGATIVE_INFINITY );
this.TestCase( SECTION, "-Infinity < -Infinity",         false,      Number.NEGATIVE_INFINITY < Number.NEGATIVE_INFINITY );

this.TestCase( SECTION, "-Infinity < 0",                 true,       Number.NEGATIVE_INFINITY < 0 );
this.TestCase( SECTION, "-Infinity < -Number.MAX_VALUE", true,       Number.NEGATIVE_INFINITY < -Number.MAX_VALUE );
this.TestCase( SECTION, "-Infinity < Number.MIN_VALUE",  true,       Number.NEGATIVE_INFINITY < Number.MIN_VALUE );

this.TestCase( SECTION, "'string' < 'string'",           false,       'string' < 'string' );
this.TestCase( SECTION, "'astring' < 'string'",          true,       'astring' < 'string' );
this.TestCase( SECTION, "'strings' < 'stringy'",         true,       'strings' < 'stringy' );
this.TestCase( SECTION, "'strings' < 'stringier'",       false,       'strings' < 'stringier' );
this.TestCase( SECTION, "'string' < 'astring'",          false,      'string' < 'astring' );
this.TestCase( SECTION, "'string' < 'strings'",          true,       'string' < 'strings' );

//test();

function MyObject(value) {
this.value = value;
this.valueOf = new Function( "return this.value" );
this.toString = new Function( "return this.value +''" );
}
function MyValueObject(value) {
this.value = value;
this.valueOf = new Function( "return this.value" );
}
function MyStringObject(value) {
this.value = value;
this.toString = new Function( "return this.value +''" );
}

},

/**
File Name:          11.8.2.js
ECMA Section:       11.8.2  The greater-than operator ( > )
Description:


Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_8_2: function() {
var SECTION = "11.8.2";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " The greater-than operator ( > )");

this.TestCase( SECTION, "true > false",              true,      true > false );
this.TestCase( SECTION, "false > true",              false,       false > true );
this.TestCase( SECTION, "false > false",             false,      false > false );
this.TestCase( SECTION, "true > true",               false,      true > true );

this.TestCase( SECTION, "new Boolean(true) > new Boolean(true)",     false,  new Boolean(true) > new Boolean(true) );
this.TestCase( SECTION, "new Boolean(true) > new Boolean(false)",    true,  new Boolean(true) > new Boolean(false) );
this.TestCase( SECTION, "new Boolean(false) > new Boolean(true)",    false,   new Boolean(false) > new Boolean(true) );
this.TestCase( SECTION, "new Boolean(false) > new Boolean(false)",   false,  new Boolean(false) > new Boolean(false) );

this.TestCase( SECTION, "new MyObject(Infinity) > new MyObject(Infinity)",   false,  new MyObject( Number.POSITIVE_INFINITY ) > new MyObject( Number.POSITIVE_INFINITY) );
this.TestCase( SECTION, "new MyObject(-Infinity) > new MyObject(Infinity)",  false,   new MyObject( Number.NEGATIVE_INFINITY ) > new MyObject( Number.POSITIVE_INFINITY) );
this.TestCase( SECTION, "new MyObject(-Infinity) > new MyObject(-Infinity)", false,  new MyObject( Number.NEGATIVE_INFINITY ) > new MyObject( Number.NEGATIVE_INFINITY) );

this.TestCase( SECTION, "new MyValueObject(false) > new MyValueObject(true)",  false,   new MyValueObject(false) > new MyValueObject(true) );
this.TestCase( SECTION, "new MyValueObject(true) > new MyValueObject(true)",   false,  new MyValueObject(true) > new MyValueObject(true) );
this.TestCase( SECTION, "new MyValueObject(false) > new MyValueObject(false)", false,  new MyValueObject(false) > new MyValueObject(false) );

this.TestCase( SECTION, "new MyStringObject(false) > new MyStringObject(true)",  false,   new MyStringObject(false) > new MyStringObject(true) );
this.TestCase( SECTION, "new MyStringObject(true) > new MyStringObject(true)",   false,  new MyStringObject(true) > new MyStringObject(true) );
this.TestCase( SECTION, "new MyStringObject(false) > new MyStringObject(false)", false,  new MyStringObject(false) > new MyStringObject(false) );

this.TestCase( SECTION, "Number.NaN > Number.NaN",   false,     Number.NaN > Number.NaN );
this.TestCase( SECTION, "0 > Number.NaN",            false,     0 > Number.NaN );
this.TestCase( SECTION, "Number.NaN > 0",            false,     Number.NaN > 0 );

this.TestCase( SECTION, "0 > -0",                    false,      0 > -0 );
this.TestCase( SECTION, "-0 > 0",                    false,      -0 > 0 );

this.TestCase( SECTION, "Infinity > 0",                  true,      Number.POSITIVE_INFINITY > 0 );
this.TestCase( SECTION, "Infinity > Number.MAX_VALUE",   true,      Number.POSITIVE_INFINITY > Number.MAX_VALUE );
this.TestCase( SECTION, "Infinity > Infinity",           false,      Number.POSITIVE_INFINITY > Number.POSITIVE_INFINITY );

this.TestCase( SECTION, "0 > Infinity",                  false,       0 > Number.POSITIVE_INFINITY );
this.TestCase( SECTION, "Number.MAX_VALUE > Infinity",   false,       Number.MAX_VALUE > Number.POSITIVE_INFINITY );

this.TestCase( SECTION, "0 > -Infinity",                 true,      0 > Number.NEGATIVE_INFINITY );
this.TestCase( SECTION, "Number.MAX_VALUE > -Infinity",  true,      Number.MAX_VALUE > Number.NEGATIVE_INFINITY );
this.TestCase( SECTION, "-Infinity > -Infinity",         false,      Number.NEGATIVE_INFINITY > Number.NEGATIVE_INFINITY );

this.TestCase( SECTION, "-Infinity > 0",                 false,       Number.NEGATIVE_INFINITY > 0 );
this.TestCase( SECTION, "-Infinity > -Number.MAX_VALUE", false,       Number.NEGATIVE_INFINITY > -Number.MAX_VALUE );
this.TestCase( SECTION, "-Infinity > Number.MIN_VALUE",  false,       Number.NEGATIVE_INFINITY > Number.MIN_VALUE );

this.TestCase( SECTION, "'string' > 'string'",           false,       'string' > 'string' );
this.TestCase( SECTION, "'astring' > 'string'",          false,       'astring' > 'string' );
this.TestCase( SECTION, "'strings' > 'stringy'",         false,       'strings' > 'stringy' );
this.TestCase( SECTION, "'strings' > 'stringier'",       true,       'strings' > 'stringier' );
this.TestCase( SECTION, "'string' > 'astring'",          true,      'string' > 'astring' );
this.TestCase( SECTION, "'string' > 'strings'",          false,       'string' > 'strings' );

//test();

function MyObject(value) {
this.value = value;
this.valueOf = new Function( "return this.value" );
this.toString = new Function( "return this.value +''" );
}
function MyValueObject(value) {
this.value = value;
this.valueOf = new Function( "return this.value" );
}
function MyStringObject(value) {
this.value = value;
this.toString = new Function( "return this.value +''" );
}

},

/**
File Name:          11.8.3.js
ECMA Section:       11.8.3  The less-than-or-equal operator ( <= )
Description:

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_8_3: function() {
var SECTION = "11.8.1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " The less-than-or-equal operator ( <= )");

this.TestCase( SECTION, "true <= false",              false,      true <= false );
this.TestCase( SECTION, "false <= true",              true,       false <= true );
this.TestCase( SECTION, "false <= false",             true,      false <= false );
this.TestCase( SECTION, "true <= true",               true,      true <= true );

this.TestCase( SECTION, "new Boolean(true) <= new Boolean(true)",     true,  new Boolean(true) <= new Boolean(true) );
this.TestCase( SECTION, "new Boolean(true) <= new Boolean(false)",    false,  new Boolean(true) <= new Boolean(false) );
this.TestCase( SECTION, "new Boolean(false) <= new Boolean(true)",    true,   new Boolean(false) <= new Boolean(true) );
this.TestCase( SECTION, "new Boolean(false) <= new Boolean(false)",   true,  new Boolean(false) <= new Boolean(false) );

this.TestCase( SECTION, "new MyObject(Infinity) <= new MyObject(Infinity)",   true,  new MyObject( Number.POSITIVE_INFINITY ) <= new MyObject( Number.POSITIVE_INFINITY) );
this.TestCase( SECTION, "new MyObject(-Infinity) <= new MyObject(Infinity)",  true,   new MyObject( Number.NEGATIVE_INFINITY ) <= new MyObject( Number.POSITIVE_INFINITY) );
this.TestCase( SECTION, "new MyObject(-Infinity) <= new MyObject(-Infinity)", true,  new MyObject( Number.NEGATIVE_INFINITY ) <= new MyObject( Number.NEGATIVE_INFINITY) );

this.TestCase( SECTION, "new MyValueObject(false) <= new MyValueObject(true)",  true,   new MyValueObject(false) <= new MyValueObject(true) );
this.TestCase( SECTION, "new MyValueObject(true) <= new MyValueObject(true)",   true,  new MyValueObject(true) <= new MyValueObject(true) );
this.TestCase( SECTION, "new MyValueObject(false) <= new MyValueObject(false)", true,  new MyValueObject(false) <= new MyValueObject(false) );

this.TestCase( SECTION, "new MyStringObject(false) <= new MyStringObject(true)",  true,   new MyStringObject(false) <= new MyStringObject(true) );
this.TestCase( SECTION, "new MyStringObject(true) <= new MyStringObject(true)",   true,  new MyStringObject(true) <= new MyStringObject(true) );
this.TestCase( SECTION, "new MyStringObject(false) <= new MyStringObject(false)", true,  new MyStringObject(false) <= new MyStringObject(false) );

this.TestCase( SECTION, "Number.NaN <= Number.NaN",   false,     Number.NaN <= Number.NaN );
this.TestCase( SECTION, "0 <= Number.NaN",            false,     0 <= Number.NaN );
this.TestCase( SECTION, "Number.NaN <= 0",            false,     Number.NaN <= 0 );

this.TestCase( SECTION, "0 <= -0",                    true,      0 <= -0 );
this.TestCase( SECTION, "-0 <= 0",                    true,      -0 <= 0 );

this.TestCase( SECTION, "Infinity <= 0",                  false,      Number.POSITIVE_INFINITY <= 0 );
this.TestCase( SECTION, "Infinity <= Number.MAX_VALUE",   false,      Number.POSITIVE_INFINITY <= Number.MAX_VALUE );
this.TestCase( SECTION, "Infinity <= Infinity",           true,       Number.POSITIVE_INFINITY <= Number.POSITIVE_INFINITY );

this.TestCase( SECTION, "0 <= Infinity",                  true,       0 <= Number.POSITIVE_INFINITY );
this.TestCase( SECTION, "Number.MAX_VALUE <= Infinity",   true,       Number.MAX_VALUE <= Number.POSITIVE_INFINITY );

this.TestCase( SECTION, "0 <= -Infinity",                 false,      0 <= Number.NEGATIVE_INFINITY );
this.TestCase( SECTION, "Number.MAX_VALUE <= -Infinity",  false,      Number.MAX_VALUE <= Number.NEGATIVE_INFINITY );
this.TestCase( SECTION, "-Infinity <= -Infinity",         true,       Number.NEGATIVE_INFINITY <= Number.NEGATIVE_INFINITY );

this.TestCase( SECTION, "-Infinity <= 0",                 true,       Number.NEGATIVE_INFINITY <= 0 );
this.TestCase( SECTION, "-Infinity <= -Number.MAX_VALUE", true,       Number.NEGATIVE_INFINITY <= -Number.MAX_VALUE );
this.TestCase( SECTION, "-Infinity <= Number.MIN_VALUE",  true,       Number.NEGATIVE_INFINITY <= Number.MIN_VALUE );

this.TestCase( SECTION, "'string' <= 'string'",           true,       'string' <= 'string' );
this.TestCase( SECTION, "'astring' <= 'string'",          true,       'astring' <= 'string' );
this.TestCase( SECTION, "'strings' <= 'stringy'",         true,       'strings' <= 'stringy' );
this.TestCase( SECTION, "'strings' <= 'stringier'",       false,       'strings' <= 'stringier' );
this.TestCase( SECTION, "'string' <= 'astring'",          false,      'string' <= 'astring' );
this.TestCase( SECTION, "'string' <= 'strings'",          true,       'string' <= 'strings' );

//test();

function MyObject(value) {
this.value = value;
this.valueOf = new Function( "return this.value" );
this.toString = new Function( "return this.value +''" );
}
function MyValueObject(value) {
this.value = value;
this.valueOf = new Function( "return this.value" );
}
function MyStringObject(value) {
this.value = value;
this.toString = new Function( "return this.value +''" );
}

},

/**
File Name:          11.8.4.js
ECMA Section:       11.8.4  The greater-than-or-equal operator ( >= )
Description:


Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_8_4: function() {
var SECTION = "11.8.4";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " The greater-than-or-equal operator ( >= )")

this.TestCase( SECTION, "true >= false",              true,      true >= false );
this.TestCase( SECTION, "false >= true",              false,     false >= true );
this.TestCase( SECTION, "false >= false",             true,      false >= false );
this.TestCase( SECTION, "true >= true",               true,      true >= true );

this.TestCase( SECTION, "new Boolean(true) >= new Boolean(true)",     true,  new Boolean(true) >= new Boolean(true) );
this.TestCase( SECTION, "new Boolean(true) >= new Boolean(false)",    true,  new Boolean(true) >= new Boolean(false) );
this.TestCase( SECTION, "new Boolean(false) >= new Boolean(true)",    false,   new Boolean(false) >= new Boolean(true) );
this.TestCase( SECTION, "new Boolean(false) >= new Boolean(false)",   true,  new Boolean(false) >= new Boolean(false) );

this.TestCase( SECTION, "new MyObject(Infinity) >= new MyObject(Infinity)",   true,  new MyObject( Number.POSITIVE_INFINITY ) >= new MyObject( Number.POSITIVE_INFINITY) );
this.TestCase( SECTION, "new MyObject(-Infinity) >= new MyObject(Infinity)",  false,   new MyObject( Number.NEGATIVE_INFINITY ) >= new MyObject( Number.POSITIVE_INFINITY) );
this.TestCase( SECTION, "new MyObject(-Infinity) >= new MyObject(-Infinity)", true,  new MyObject( Number.NEGATIVE_INFINITY ) >= new MyObject( Number.NEGATIVE_INFINITY) );

this.TestCase( SECTION, "new MyValueObject(false) >= new MyValueObject(true)",  false,   new MyValueObject(false) >= new MyValueObject(true) );
this.TestCase( SECTION, "new MyValueObject(true) >= new MyValueObject(true)",   true,  new MyValueObject(true) >= new MyValueObject(true) );
this.TestCase( SECTION, "new MyValueObject(false) >= new MyValueObject(false)", true,  new MyValueObject(false) >= new MyValueObject(false) );

this.TestCase( SECTION, "new MyStringObject(false) >= new MyStringObject(true)",  false,   new MyStringObject(false) >= new MyStringObject(true) );
this.TestCase( SECTION, "new MyStringObject(true) >= new MyStringObject(true)",   true,  new MyStringObject(true) >= new MyStringObject(true) );
this.TestCase( SECTION, "new MyStringObject(false) >= new MyStringObject(false)", true,  new MyStringObject(false) >= new MyStringObject(false) );

this.TestCase( SECTION, "Number.NaN >= Number.NaN",   false,     Number.NaN >= Number.NaN );
this.TestCase( SECTION, "0 >= Number.NaN",            false,     0 >= Number.NaN );
this.TestCase( SECTION, "Number.NaN >= 0",            false,     Number.NaN >= 0 );

this.TestCase( SECTION, "0 >= -0",                    true,      0 >= -0 );
this.TestCase( SECTION, "-0 >= 0",                    true,      -0 >= 0 );

this.TestCase( SECTION, "Infinity >= 0",                  true,      Number.POSITIVE_INFINITY >= 0 );
this.TestCase( SECTION, "Infinity >= Number.MAX_VALUE",   true,      Number.POSITIVE_INFINITY >= Number.MAX_VALUE );
this.TestCase( SECTION, "Infinity >= Infinity",           true,      Number.POSITIVE_INFINITY >= Number.POSITIVE_INFINITY );

this.TestCase( SECTION, "0 >= Infinity",                  false,       0 >= Number.POSITIVE_INFINITY );
this.TestCase( SECTION, "Number.MAX_VALUE >= Infinity",   false,       Number.MAX_VALUE >= Number.POSITIVE_INFINITY );

this.TestCase( SECTION, "0 >= -Infinity",                 true,      0 >= Number.NEGATIVE_INFINITY );
this.TestCase( SECTION, "Number.MAX_VALUE >= -Infinity",  true,      Number.MAX_VALUE >= Number.NEGATIVE_INFINITY );
this.TestCase( SECTION, "-Infinity >= -Infinity",         true,      Number.NEGATIVE_INFINITY >= Number.NEGATIVE_INFINITY );

this.TestCase( SECTION, "-Infinity >= 0",                 false,       Number.NEGATIVE_INFINITY >= 0 );
this.TestCase( SECTION, "-Infinity >= -Number.MAX_VALUE", false,       Number.NEGATIVE_INFINITY >= -Number.MAX_VALUE );
this.TestCase( SECTION, "-Infinity >= Number.MIN_VALUE",  false,       Number.NEGATIVE_INFINITY >= Number.MIN_VALUE );

this.TestCase( SECTION, "'string' > 'string'",           false,       'string' > 'string' );
this.TestCase( SECTION, "'astring' > 'string'",          false,       'astring' > 'string' );
this.TestCase( SECTION, "'strings' > 'stringy'",         false,       'strings' > 'stringy' );
this.TestCase( SECTION, "'strings' > 'stringier'",       true,       'strings' > 'stringier' );
this.TestCase( SECTION, "'string' > 'astring'",          true,      'string' > 'astring' );
this.TestCase( SECTION, "'string' > 'strings'",          false,       'string' > 'strings' );

//test();

function MyObject(value) {
this.value = value;
this.valueOf = new Function( "return this.value" );
this.toString = new Function( "return this.value +''" );
}
function MyValueObject(value) {
this.value = value;
this.valueOf = new Function( "return this.value" );
}
function MyStringObject(value) {
this.value = value;
this.toString = new Function( "return this.value +''" );
}

},

/**
File Name:          11.9.1.js
ECMA Section:       11.9.1 The equals operator ( == )
Description:

The production EqualityExpression:
EqualityExpression ==  RelationalExpression is evaluated as follows:

1.  Evaluate EqualityExpression.
2.  Call GetValue(Result(1)).
3.  Evaluate RelationalExpression.
4.  Call GetValue(Result(3)).
5.  Perform the comparison Result(4) == Result(2). (See section 11.9.3)
6.  Return Result(5).
Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_9_1: function() {
var SECTION = "11.9.1";
var VERSION = "ECMA_1";
var BUGNUMBER="77391";
//startTest();

//writeHeaderToLog( SECTION + " The equals operator ( == )");


// type x and type y are the same.  if type x is undefined or null, return true

this.TestCase( SECTION,    "void 0 = void 0",        true,   void 0 == void 0 );
this.TestCase( SECTION,    "null == null",           true,   null == null );

//  if x is NaN, return false. if y is NaN, return false.

this.TestCase( SECTION,    "NaN == NaN",             false,  Number.NaN == Number.NaN );
this.TestCase( SECTION,    "NaN == 0",               false,  Number.NaN == 0 );
this.TestCase( SECTION,    "0 == NaN",               false,  0 == Number.NaN );
this.TestCase( SECTION,    "NaN == Infinity",        false,  Number.NaN == Number.POSITIVE_INFINITY );
this.TestCase( SECTION,    "Infinity == NaN",        false,  Number.POSITIVE_INFINITY == Number.NaN );

// if x is the same number value as y, return true.

this.TestCase( SECTION,    "Number.MAX_VALUE == Number.MAX_VALUE",   true,   Number.MAX_VALUE == Number.MAX_VALUE );
this.TestCase( SECTION,    "Number.MIN_VALUE == Number.MIN_VALUE",   true,   Number.MIN_VALUE == Number.MIN_VALUE );
this.TestCase( SECTION,    "Number.POSITIVE_INFINITY == Number.POSITIVE_INFINITY",   true,   Number.POSITIVE_INFINITY == Number.POSITIVE_INFINITY );
this.TestCase( SECTION,    "Number.NEGATIVE_INFINITY == Number.NEGATIVE_INFINITY",   true,   Number.NEGATIVE_INFINITY == Number.NEGATIVE_INFINITY );

//  if xis 0 and y is -0, return true.   if x is -0 and y is 0, return true.

this.TestCase( SECTION,    "0 == 0",                 true,   0 == 0 );
this.TestCase( SECTION,    "0 == -0",                true,   0 == -0 );
this.TestCase( SECTION,    "-0 == 0",                true,   -0 == 0 );
this.TestCase( SECTION,    "-0 == -0",               true,   -0 == -0 );

// return false.

this.TestCase( SECTION,    "0.9 == 1",               false,  0.9 == 1 );
this.TestCase( SECTION,    "0.999999 == 1",          false,  0.999999 == 1 );
this.TestCase( SECTION,    "0.9999999999 == 1",      false,  0.9999999999 == 1 );
this.TestCase( SECTION,    "0.9999999999999 == 1",   false,  0.9999999999999 == 1 );

// type x and type y are the same type, but not numbers.


// x and y are strings.  return true if x and y are exactly the same sequence of characters.
// otherwise, return false.

this.TestCase( SECTION,    "'hello' == 'hello'",         true,   "hello" == "hello" );

// x and y are booleans.  return true if both are true or both are false.

this.TestCase( SECTION,    "true == true",               true,   true == true );
this.TestCase( SECTION,    "false == false",             true,   false == false );
this.TestCase( SECTION,    "true == false",              false,  true == false );
this.TestCase( SECTION,    "false == true",              false,  false == true );

// return true if x and y refer to the same object.  otherwise return false.

this.TestCase( SECTION,    "new MyObject(true) == new MyObject(true)",   false,  new MyObject(true) == new MyObject(true) );
this.TestCase( SECTION,    "new Boolean(true) == new Boolean(true)",     false,  new Boolean(true) == new Boolean(true) );
this.TestCase( SECTION,    "new Boolean(false) == new Boolean(false)",   false,  new Boolean(false) == new Boolean(false) );


this.TestCase( SECTION,    "x = new MyObject(true); y = x; z = x; z == y",   true,  eval("x = new MyObject(true); y = x; z = x; z == y") );
this.TestCase( SECTION,    "x = new MyObject(false); y = x; z = x; z == y",  true,  eval("x = new MyObject(false); y = x; z = x; z == y") );
this.TestCase( SECTION,    "x = new Boolean(true); y = x; z = x; z == y",   true,  eval("x = new Boolean(true); y = x; z = x; z == y") );
this.TestCase( SECTION,    "x = new Boolean(false); y = x; z = x; z == y",   true,  eval("x = new Boolean(false); y = x; z = x; z == y") );

this.TestCase( SECTION,    "new Boolean(true) == new Boolean(true)",     false,  new Boolean(true) == new Boolean(true) );
this.TestCase( SECTION,    "new Boolean(false) == new Boolean(false)",   false,  new Boolean(false) == new Boolean(false) );

// if x is null and y is undefined, return true.  if x is undefined and y is null return true.

this.TestCase( SECTION,    "null == void 0",             true,   null == void 0 );
this.TestCase( SECTION,    "void 0 == null",             true,   void 0 == null );

// if type(x) is Number and type(y) is string, return the result of the comparison x == ToNumber(y).

this.TestCase( SECTION,    "1 == '1'",                   true,   1 == '1' );
this.TestCase( SECTION,    "255 == '0xff'",               true,  255 == '0xff' );
this.TestCase( SECTION,    "0 == '\r'",                  true,   0 == "\r" );
this.TestCase( SECTION,    "1e19 == '1e19'",             true,   1e19 == "1e19" );


this.TestCase( SECTION,    "new Boolean(true) == true",  true,   true == new Boolean(true) );
this.TestCase( SECTION,    "new MyObject(true) == true", true,   true == new MyObject(true) );

this.TestCase( SECTION,    "new Boolean(false) == false",    true,   new Boolean(false) == false );
this.TestCase( SECTION,    "new MyObject(false) == false",   true,   new MyObject(false) == false );

this.TestCase( SECTION,    "true == new Boolean(true)",      true,   true == new Boolean(true) );
this.TestCase( SECTION,    "true == new MyObject(true)",     true,   true == new MyObject(true) );

this.TestCase( SECTION,    "false == new Boolean(false)",    true,   false == new Boolean(false) );
this.TestCase( SECTION,    "false == new MyObject(false)",   true,   false == new MyObject(false) );

//test();

function MyObject( value ) {
this.value = value;
this.valueOf = new Function( "return this.value" );
}

},

/**
File Name:          11.9.2.js
ECMA Section:       11.9.2 The equals operator ( == )
Description:

The production EqualityExpression:
EqualityExpression ==  RelationalExpression is evaluated as follows:

1.  Evaluate EqualityExpression.
2.  Call GetValue(Result(1)).
3.  Evaluate RelationalExpression.
4.  Call GetValue(Result(3)).
5.  Perform the comparison Result(4) == Result(2). (See section 11.9.3)
6.  Return Result(5).
Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_9_2: function() {
var SECTION = "11.9.2";
var VERSION = "ECMA_1";
var BUGNUMBER="77391";
//startTest();

//writeHeaderToLog( SECTION + " The equals operator ( == )");

// type x and type y are the same.  if type x is undefined or null, return true

this.TestCase( SECTION,    "void 0 == void 0",        false,   void 0 != void 0 );
this.TestCase( SECTION,    "null == null",           false,   null != null );

//  if x is NaN, return false. if y is NaN, return false.

this.TestCase( SECTION,    "NaN != NaN",             true,  Number.NaN != Number.NaN );
this.TestCase( SECTION,    "NaN != 0",               true,  Number.NaN != 0 );
this.TestCase( SECTION,    "0 != NaN",               true,  0 != Number.NaN );
this.TestCase( SECTION,    "NaN != Infinity",        true,  Number.NaN != Number.POSITIVE_INFINITY );
this.TestCase( SECTION,    "Infinity != NaN",        true,  Number.POSITIVE_INFINITY != Number.NaN );

// if x is the same number value as y, return true.

this.TestCase( SECTION,    "Number.MAX_VALUE != Number.MAX_VALUE",   false,   Number.MAX_VALUE != Number.MAX_VALUE );
this.TestCase( SECTION,    "Number.MIN_VALUE != Number.MIN_VALUE",   false,   Number.MIN_VALUE != Number.MIN_VALUE );
this.TestCase( SECTION,    "Number.POSITIVE_INFINITY != Number.POSITIVE_INFINITY",   false,   Number.POSITIVE_INFINITY != Number.POSITIVE_INFINITY );
this.TestCase( SECTION,    "Number.NEGATIVE_INFINITY != Number.NEGATIVE_INFINITY",   false,   Number.NEGATIVE_INFINITY != Number.NEGATIVE_INFINITY );

//  if xis 0 and y is -0, return true.   if x is -0 and y is 0, return true.

this.TestCase( SECTION,    "0 != 0",                 false,   0 != 0 );
this.TestCase( SECTION,    "0 != -0",                false,   0 != -0 );
this.TestCase( SECTION,    "-0 != 0",                false,   -0 != 0 );
this.TestCase( SECTION,    "-0 != -0",               false,   -0 != -0 );

// return false.

this.TestCase( SECTION,    "0.9 != 1",               true,  0.9 != 1 );
this.TestCase( SECTION,    "0.999999 != 1",          true,  0.999999 != 1 );
this.TestCase( SECTION,    "0.9999999999 != 1",      true,  0.9999999999 != 1 );
this.TestCase( SECTION,    "0.9999999999999 != 1",   true,  0.9999999999999 != 1 );

// type x and type y are the same type, but not numbers.


// x and y are strings.  return true if x and y are exactly the same sequence of characters.
// otherwise, return false.

this.TestCase( SECTION,    "'hello' != 'hello'",         false,   "hello" != "hello" );

// x and y are booleans.  return true if both are true or both are false.

this.TestCase( SECTION,    "true != true",               false,   true != true );
this.TestCase( SECTION,    "false != false",             false,   false != false );
this.TestCase( SECTION,    "true != false",              true,  true != false );
this.TestCase( SECTION,    "false != true",              true,  false != true );

// return true if x and y refer to the same object.  otherwise return false.

this.TestCase( SECTION,    "new MyObject(true) != new MyObject(true)",   true,  new MyObject(true) != new MyObject(true) );
this.TestCase( SECTION,    "new Boolean(true) != new Boolean(true)",     true,  new Boolean(true) != new Boolean(true) );
this.TestCase( SECTION,    "new Boolean(false) != new Boolean(false)",   true,  new Boolean(false) != new Boolean(false) );


this.TestCase( SECTION,    "x = new MyObject(true); y = x; z = x; z != y",   false,  eval("x = new MyObject(true); y = x; z = x; z != y") );
this.TestCase( SECTION,    "x = new MyObject(false); y = x; z = x; z != y",  false,  eval("x = new MyObject(false); y = x; z = x; z != y") );
this.TestCase( SECTION,    "x = new Boolean(true); y = x; z = x; z != y",   false,  eval("x = new Boolean(true); y = x; z = x; z != y") );
this.TestCase( SECTION,    "x = new Boolean(false); y = x; z = x; z != y",   false,  eval("x = new Boolean(false); y = x; z = x; z != y") );

this.TestCase( SECTION,    "new Boolean(true) != new Boolean(true)",     true,  new Boolean(true) != new Boolean(true) );
this.TestCase( SECTION,    "new Boolean(false) != new Boolean(false)",   true,  new Boolean(false) != new Boolean(false) );

// if x is null and y is undefined, return true.  if x is undefined and y is null return true.

this.TestCase( SECTION,    "null != void 0",             false,   null != void 0 );
this.TestCase( SECTION,    "void 0 != null",             false,   void 0 != null );

// if type(x) is Number and type(y) is string, return the result of the comparison x != ToNumber(y).

this.TestCase( SECTION,    "1 != '1'",                   false,   1 != '1' );
this.TestCase( SECTION,    "255 != '0xff'",               false,  255 != '0xff' );
this.TestCase( SECTION,    "0 != '\r'",                  false,   0 != "\r" );
this.TestCase( SECTION,    "1e19 != '1e19'",             false,   1e19 != "1e19" );


this.TestCase( SECTION,    "new Boolean(true) != true",  false,   true != new Boolean(true) );
this.TestCase( SECTION,    "new MyObject(true) != true", false,   true != new MyObject(true) );

this.TestCase( SECTION,    "new Boolean(false) != false",    false,   new Boolean(false) != false );
this.TestCase( SECTION,    "new MyObject(false) != false",   false,   new MyObject(false) != false );

this.TestCase( SECTION,    "true != new Boolean(true)",      false,   true != new Boolean(true) );
this.TestCase( SECTION,    "true != new MyObject(true)",     false,   true != new MyObject(true) );

this.TestCase( SECTION,    "false != new Boolean(false)",    false,   false != new Boolean(false) );
this.TestCase( SECTION,    "false != new MyObject(false)",   false,   false != new MyObject(false) );

//test();

function MyObject( value ) {
this.value = value;
this.valueOf = new Function( "return this.value" );
}

},

/**
File Name:          11.9.3.js
ECMA Section:       11.9.3 The equals operator ( == )
Description:

The production EqualityExpression:
EqualityExpression ==  RelationalExpression is evaluated as follows:

1.  Evaluate EqualityExpression.
2.  Call GetValue(Result(1)).
3.  Evaluate RelationalExpression.
4.  Call GetValue(Result(3)).
5.  Perform the comparison Result(4) == Result(2). (See section 11.9.3)
6.  Return Result(5).
Author:             christine@netscape.com
Date:               12 november 1997
*/
test_11_9_3: function() {
var SECTION = "11.9.3";
var VERSION = "ECMA_1";
var BUGNUMBER="77391";
//startTest();

//writeHeaderToLog( SECTION + " The equals operator ( == )");

// type x and type y are the same.  if type x is undefined or null, return true

this.TestCase( SECTION,    "void 0 = void 0",        true,   void 0 == void 0 );
this.TestCase( SECTION,    "null == null",           true,   null == null );

//  if x is NaN, return false. if y is NaN, return false.

this.TestCase( SECTION,    "NaN == NaN",             false,  Number.NaN == Number.NaN );
this.TestCase( SECTION,    "NaN == 0",               false,  Number.NaN == 0 );
this.TestCase( SECTION,    "0 == NaN",               false,  0 == Number.NaN );
this.TestCase( SECTION,    "NaN == Infinity",        false,  Number.NaN == Number.POSITIVE_INFINITY );
this.TestCase( SECTION,    "Infinity == NaN",        false,  Number.POSITIVE_INFINITY == Number.NaN );

// if x is the same number value as y, return true.

this.TestCase( SECTION,    "Number.MAX_VALUE == Number.MAX_VALUE",   true,   Number.MAX_VALUE == Number.MAX_VALUE );
this.TestCase( SECTION,    "Number.MIN_VALUE == Number.MIN_VALUE",   true,   Number.MIN_VALUE == Number.MIN_VALUE );
this.TestCase( SECTION,    "Number.POSITIVE_INFINITY == Number.POSITIVE_INFINITY",   true,   Number.POSITIVE_INFINITY == Number.POSITIVE_INFINITY );
this.TestCase( SECTION,    "Number.NEGATIVE_INFINITY == Number.NEGATIVE_INFINITY",   true,   Number.NEGATIVE_INFINITY == Number.NEGATIVE_INFINITY );

//  if xis 0 and y is -0, return true.   if x is -0 and y is 0, return true.

this.TestCase( SECTION,    "0 == 0",                 true,   0 == 0 );
this.TestCase( SECTION,    "0 == -0",                true,   0 == -0 );
this.TestCase( SECTION,    "-0 == 0",                true,   -0 == 0 );
this.TestCase( SECTION,    "-0 == -0",               true,   -0 == -0 );

// return false.

this.TestCase( SECTION,    "0.9 == 1",               false,  0.9 == 1 );
this.TestCase( SECTION,    "0.999999 == 1",          false,  0.999999 == 1 );
this.TestCase( SECTION,    "0.9999999999 == 1",      false,  0.9999999999 == 1 );
this.TestCase( SECTION,    "0.9999999999999 == 1",   false,  0.9999999999999 == 1 );

// type x and type y are the same type, but not numbers.


// x and y are strings.  return true if x and y are exactly the same sequence of characters.
// otherwise, return false.

this.TestCase( SECTION,    "'hello' == 'hello'",         true,   "hello" == "hello" );

// x and y are booleans.  return true if both are true or both are false.

this.TestCase( SECTION,    "true == true",               true,   true == true );
this.TestCase( SECTION,    "false == false",             true,   false == false );
this.TestCase( SECTION,    "true == false",              false,  true == false );
this.TestCase( SECTION,    "false == true",              false,  false == true );

// return true if x and y refer to the same object.  otherwise return false.

this.TestCase( SECTION,    "new MyObject(true) == new MyObject(true)",   false,  new MyObject(true) == new MyObject(true) );
this.TestCase( SECTION,    "new Boolean(true) == new Boolean(true)",     false,  new Boolean(true) == new Boolean(true) );
this.TestCase( SECTION,    "new Boolean(false) == new Boolean(false)",   false,  new Boolean(false) == new Boolean(false) );


this.TestCase( SECTION,    "x = new MyObject(true); y = x; z = x; z == y",   true,  eval("x = new MyObject(true); y = x; z = x; z == y") );
this.TestCase( SECTION,    "x = new MyObject(false); y = x; z = x; z == y",  true,  eval("x = new MyObject(false); y = x; z = x; z == y") );
this.TestCase( SECTION,    "x = new Boolean(true); y = x; z = x; z == y",   true,  eval("x = new Boolean(true); y = x; z = x; z == y") );
this.TestCase( SECTION,    "x = new Boolean(false); y = x; z = x; z == y",   true,  eval("x = new Boolean(false); y = x; z = x; z == y") );

this.TestCase( SECTION,    "new Boolean(true) == new Boolean(true)",     false,  new Boolean(true) == new Boolean(true) );
this.TestCase( SECTION,    "new Boolean(false) == new Boolean(false)",   false,  new Boolean(false) == new Boolean(false) );

// if x is null and y is undefined, return true.  if x is undefined and y is null return true.

this.TestCase( SECTION,    "null == void 0",             true,   null == void 0 );
this.TestCase( SECTION,    "void 0 == null",             true,   void 0 == null );

// if type(x) is Number and type(y) is string, return the result of the comparison x == ToNumber(y).

this.TestCase( SECTION,    "1 == '1'",                   true,   1 == '1' );
this.TestCase( SECTION,    "255 == '0xff'",               true,  255 == '0xff' );
this.TestCase( SECTION,    "0 == '\r'",                  true,   0 == "\r" );
this.TestCase( SECTION,    "1e19 == '1e19'",             true,   1e19 == "1e19" );


this.TestCase( SECTION,    "new Boolean(true) == true",  true,   true == new Boolean(true) );
this.TestCase( SECTION,    "new MyObject(true) == true", true,   true == new MyObject(true) );

this.TestCase( SECTION,    "new Boolean(false) == false",    true,   new Boolean(false) == false );
this.TestCase( SECTION,    "new MyObject(false) == false",   true,   new MyObject(false) == false );

this.TestCase( SECTION,    "true == new Boolean(true)",      true,   true == new Boolean(true) );
this.TestCase( SECTION,    "true == new MyObject(true)",     true,   true == new MyObject(true) );

this.TestCase( SECTION,    "false == new Boolean(false)",    true,   false == new Boolean(false) );
this.TestCase( SECTION,    "false == new MyObject(false)",   true,   false == new MyObject(false) );

//test();

function MyObject( value ) {
this.value = value;
this.valueOf = new Function( "return this.value" );
}

},

})
.endType();



