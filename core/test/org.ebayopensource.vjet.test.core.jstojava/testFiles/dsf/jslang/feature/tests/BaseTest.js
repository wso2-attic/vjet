vjo.ctype("dsf.jslang.feature.tests.BaseTest").props({
TIME_1970: 0,
TIME_2000:946684800000,
TIME_1900:-2208988800000,
msPerDay:86400000,
HoursPerDay: 24,
MinutesPerHour :    60,
SecondsPerMinute :  60,
msPerSecond :       1000,
msPerMinute :       60000,     //  msPerSecond * SecondsPerMinute
msPerHour :         3600000    //  msPerMinute * MinutesPerHour



}).protos({
name : null,
description :  null,
expect      :  null,
actual      :  null,
reason   : null,
constructs:function(){
// 	vjo.sysout.println("base constructor called");

},

//>public void assertTrue(Object)
assertTrue:function(Obj){
},

//>public void TestCase(String,String,Object,Object)
TestCase:function( n, d, e, a ) {
this.assertTrue(this.getTestCaseResult( this.expect,  this.actual ));

},

TestCaseAlloc:function( n, d, e, a) {

this.name = n;
this.description = d;
this.expect = e;
this.actual = a;
this.reason = '';
return this;
},

TestCaseAlloc2: function(n,d) {
this.name = n;
this.description = d;
this.expect = '';
this.actual = '';
this.reason = '';
return this;
},

writeTestCaseResult: function( expect, actual, string) {
var passed = this.getTestCaseResult( expect, actual );
this.assertTrue(passed);
return passed;
},

getTestCaseResult:function( expect, actual ) {
//  because ( NaN == NaN ) always returns false, need to do
//  a special compare to see if we got the right result.
if ( actual != actual ) {
if ( typeof actual == "object" ) {
actual = "NaN object";
} else {
actual = "NaN number";
}
}
if ( expect != expect ) {
if ( typeof expect == "object" ) {
expect = "NaN object";
} else {
expect = "NaN number";
}
}

var passed = ( expect == actual ) ? true : false;

//  if both objects are numbers
// need to replace w/ IEEE standard for rounding
if (    !passed
&& typeof(actual) == "number"
&& typeof(expect) == "number"
) {
if ( Math.abs(actual-expect) < 0.0000001 ) {
passed = true;
}
}

//  verify type is the same
if ( typeof(expect) != typeof(actual) ) {
passed = false;
}

return passed;

},

CheckItems : function ( R, A ) {
for ( var i = 0; i < R.length; i++ ) {
this.TestCase(
"",
"A["+i+ "]",
R[i],
A[i] );
}
},

CheckItems2:function( S ) {
eval( S );
var A;
var E = this.Sort( A );
this.TestCase(   "",
S +";  A.sort(); A.length",
E.length,
eval( S + "; A.sort(); A.length") );

for ( var i = 0; i < E.length; i++ ) {
this.TestCase(
"",
"A["+i+ "].toString()",
E[i] +"",
A[i] +"");

if ( A[i] == void 0 && typeof A[i] == "undefined" ) {
this.TestCase(
"",
"typeof A["+i+ "]",
typeof E[i],
typeof A[i] );
}
}
},

Sort:function( a ) {
for ( i = 0; i < a.length; i++ ) {
for ( j = i+1; j < a.length; j++ ) {
var lo = a[i];
var hi = a[j];
var c = this.Compare( lo, hi );
if ( c == 1 ) {
a[i] = hi;
a[j] = lo;
}
}
}
return a;
},
Compare:function( x, y ) {
if ( x == void 0 && y == void 0  && typeof x == "undefined" && typeof y == "undefined" ) {
return +0;
}
if ( x == void 0  && typeof x == "undefined" ) {
return 1;
}
if ( y == void 0 && typeof y == "undefined" ) {
return -1;
}
x = String(x);
y = String(y);
if ( x < y ) {
return -1;
}
if ( x > y ) {
return 1;
}
return 0;
},


ToUint32:function( n ) {
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
},

MakeDate: function( day, time ) {
if (    day == Number.POSITIVE_INFINITY ||
day == Number.NEGATIVE_INFINITY ||
day == Number.NaN ) {
return Number.NaN;
}
if (    time == Number.POSITIVE_INFINITY ||
time == Number.POSITIVE_INFINITY ||
day == Number.NaN) {
return Number.NaN;
}
return ( day * this.vj$.BaseTest.msPerDay ) + time;
}

})
.endType();
