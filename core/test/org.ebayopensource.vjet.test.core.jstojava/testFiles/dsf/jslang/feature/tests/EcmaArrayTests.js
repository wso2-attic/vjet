vjo.ctype("dsf.jslang.feature.tests.EcmaArrayTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({

assertTrue : function(){
},

//> public constructs()
constructs:function(){
this.base();
},

/** @Test

File Name:          15.4-1.js
ECMA Section:       15.4 Array Objects

Description:        Every Array object has a length property whose value
is always an integer with positive sign and less than
Math.pow(2,32).

Author:             christine@netscape.com
Date:               28 october 1997

*/
//> public void test_15_4_1();
test_15_4_1:function(){
var myarr=new Array();
myarr[Math.pow(2,32)-2]="hi";
this.assertTrue(myarr[Math.pow(2,32)-2]=="hi");
var myarr2=new Array();
myarr2[Math.pow(2,32)-2]='hi';
this.assertTrue(myarr2.length);
var myarr3=new Array();
myarr3[Math.pow(2,32)-3]='hi';
this.assertTrue(myarr3[Math.pow(2,32)-3]);
var myarr4=new Array();
myarr4[Math.pow(2,32)-3]='hi';
this.assertTrue(myarr4.length==(Math.pow(2,32)-2));
var myarr5=new Array();
myarr5[Math.pow(2,31)-2]='hi';
this.assertTrue(myarr5[Math.pow(2,31)-2]=="hi");
var myarr6=new Array();
myarr6[Math.pow(2,31)-2]='hi';
this.assertTrue((Math.pow(2,31)-1)==myarr6.length);
var myarr7=new Array();
myarr7[Math.pow(2,31)-1]='hi';
this.assertTrue(myarr7[Math.pow(2,31)-1]=="hi");
var myarr8=new Array();
myarr8[Math.pow(2,31)-1]='hi';
this.assertTrue(myarr8.length==(Math.pow(2,31)))
var myarr9=new Array();
myarr9[Math.pow(2,31)]='hi';
this.assertTrue(myarr9[Math.pow(2,31)] == "hi");
var myarr10=new Array();
myarr10[Math.pow(2,31)]='hi';
this.assertTrue(myarr10.length==(Math.pow(2,31)+1));
var myarr11=new Array();
myarr11[Math.pow(2,30)-2]='hi';
this.assertTrue(myarr11[Math.pow(2,30)-2]=="hi");
var myarr12=new Array();
myarr12[Math.pow(2,30)-2]='hi';
this.assertTrue(myarr12.length==(Math.pow(2,30)-1));
},
/**
File Name:          15.4-2.js
ECMA Section:       15.4 Array Objects

Description:        Whenever a property is added whose name is an array
index, the length property is changed, if necessary,
to be one more than the numeric value of that array
index; and whenever the length property is changed,
every property whose name is an array index whose value
is not smaller  than the new length is automatically
deleted.  This constraint applies only to the Array
object itself, and is unaffected by length or array
index properties that may be inherited from its
prototype.

Author:             christine@netscape.com
Date:               28 october 1997

*/
test_15_4_2:function(){
var arr=new Array();
arr[Math.pow(2,16)]='hi';
this.assertTrue(arr.length == (Math.pow(2,16)+1));
var arr2=new Array();
arr2[Math.pow(2,30)-2]='hi';
this.assertTrue(arr2.length== Math.pow(2,30)-1);
var arr=new Array();
arr[Math.pow(2,30)-1]='hi';
this.assertTrue(arr.length== Math.pow(2,30));
var arr=new Array();
arr[Math.pow(2,30)]='hi';
this.assertTrue(arr.length==Math.pow(2,30)+1);
var arr=new Array();
arr[Math.pow(2,31)-2]='hi';
this.assertTrue(arr.length==Math.pow(2,31)-1)
var arr=new Array();
arr[Math.pow(2,31)-1]='hi';
this.assertTrue(arr.length==Math.pow(2,31));
var arr=new Array();
arr[Math.pow(2,31)]='hi';
this.assertTrue(arr.length==Math.pow(2,31)+1)
var arr=new Array(0,1,2,3,4,5);
arr.length=2;
this.assertTrue(String(arr)=="0,1")
var arr=new Array(0,1);
arr.length=3;
this.assertTrue(String(arr)=="0,1,");
},
/**
File Name:          15.4.1.js
ECMA Section:       15.4.1 The Array Constructor Called as a Function

Description:        When Array is called as a function rather than as a
constructor, it creates and initializes a new array
object.  Thus, the function call Array(...) is
equivalent to the object creationi new Array(...) with
the same arguments.

Author:             christine@netscape.com
Date:               7 october 1997
*/
test_15_4__1:function(){
var SECTION="15.4.1";
var VERSION="ECMA_1";
var TITLE="The Array Constructor Called as a Function";
this.TestCase(SECTION,"Array() +''","",Array()+"");
this.TestCase(SECTION,"typeof Array()","object",typeof Array());
this.TestCase(SECTION,"var arr = Array(); arr.getClass = Object.prototype.toString; arr.getClass()","[object Array]",eval("var arr = Array(); arr.getClass = Object.prototype.toString; arr.getClass()"));
this.TestCase(SECTION,"var arr = Array(); arr.toString == Array.prototype.toString",true,eval("var arr = Array(); arr.toString == Array.prototype.toString"));
this.TestCase(SECTION,"Array().length",0,Array().length);
this.TestCase(SECTION,"Array(1,2,3) +''","1,2,3",Array(1,2,3)+"");
this.TestCase(SECTION,"typeof Array(1,2,3)","object",typeof Array(1,2,3));
this.TestCase(SECTION,"var arr = Array(1,2,3); arr.getClass = Object.prototype.toString; arr.getClass()","[object Array]",eval("var arr = Array(1,2,3); arr.getClass = Object.prototype.toString; arr.getClass()"));
this.TestCase(SECTION,"var arr = Array(1,2,3); arr.toString == Array.prototype.toString",true,eval("var arr = Array(1,2,3); arr.toString == Array.prototype.toString"));
this.TestCase(SECTION,"Array(1,2,3).length",3,Array(1,2,3).length);
this.TestCase(SECTION,"typeof Array(12345)","object",typeof Array(12345));
this.TestCase(SECTION,"var arr = Array(12345); arr.getClass = Object.prototype.toString; arr.getClass()","[object Array]",eval("var arr = Array(12345); arr.getClass = Object.prototype.toString; arr.getClass()"));
this.TestCase(SECTION,"var arr = Array(1,2,3,4,5); arr.toString == Array.prototype.toString",true,eval("var arr = Array(1,2,3,4,5); arr.toString == Array.prototype.toString"));
this.TestCase(SECTION,"Array(12345).length",12345,Array(12345).length);
},
/**
File Name:          15.4.1.1.js
ECMA Section:       15.4.1 Array( item0, item1,... )

Description:        When Array is called as a function rather than as a
constructor, it creates and initializes a new array
object.  Thus, the function call Array(...) is
equivalent to the object creation new Array(...) with
the same arguments.

An array is created and returned as if by the expression
new Array( item0, item1, ... ).

Author:             christine@netscape.com
Date:               7 october 1997
*/
test_15_4_1_1:function(){
this.assertTrue(typeof Array(1,2)=="object");
this.assertTrue(typeof Array(1,2)=="object");
this.assertTrue((Array(1,2)).toString==Array.prototype.toString);
var arr=Array(1,2,3);
arr.toString=Object.prototype.toString;
this.assertTrue(arr.toString()=="[object Array]");
this.assertTrue((Array(1,2)).length==2);
var arr=(Array(1,2));
this.assertTrue(arr[0]==1)
var arr=(Array(1,2));
this.assertTrue(arr[1]==2)
var arr = (Array(1,2));
this.assertTrue(String(arr)=="1,2");

},

/**
File Name:          15.4.1.2.js
ECMA Section:       15.4.1.2 Array(len)

Description:        When Array is called as a function rather than as a
constructor, it creates and initializes a new array
object.  Thus, the function call Array(...) is
equivalent to the object creationi new Array(...) with
the same arguments.

An array is created and returned as if by the
expression new Array(len).

Author:             christine@netscape.com
Date:               7 october 1997
*/

test_15_4_1_2:function(){

this.assertTrue((Array()).length== 0)
this.assertTrue((Array(0)).length==0);
this.assertTrue((Array(1)).length==1);
this.assertTrue((Array(10)).length==10);
this.assertTrue((Array('1')).length==1);

this.assertTrue((Array(1000)).length==1000);
this.assertTrue((Array('1000')).length==1);
this.assertTrue((Array('1')).length==1);

this.assertTrue((Array(4294967295).length)==this.ToUint32(4294967295));
this.assertTrue((Array(Math.pow(2,31)-1)).length)==this.ToUint32(Math.pow(2,31)-1);

this.assertTrue((Array(Math.pow(2,31))).length==this.ToUint32(Math.pow(2,31)));

this.assertTrue((Array(Math.pow(2,31)+1)).length==this.ToUint32(Math.pow(2,31)+1));

this.assertTrue((Array('8589934592')).length==1);
this.assertTrue((Array('4294967296')).length==1);

this.assertTrue((Array(1073741823)).length==this.ToUint32(1073741823));
this.assertTrue((Array(1073741824)).length==this.ToUint32(1073741824));

this.assertTrue((Array('a string')).length==1);


},

//
//test_15_4_1_2:function(){
//
//	this.assertTrue((Array()).length==0);
//	this.assertTrue((Array(0)).length==0);
//	this.assertTrue((Array(1)).length==1);
//	this.assertTrue((Array(10)).length==10);
//	this.assertTrue(Array('1').length==1);
//	this.assertTrue((Array(1000)).length==1000);
//	this.assertTrue(Array('1000').length==1);
//	this.assertTrue(Array(4294967295).length==this.ToUint32(4294967295));
//	this.assertTrue((Array(Math.pow(2,31)-1)).length==this.ToUint32(Math.pow(2,31)-1));
//	this.assertTrue((Array(Math.pow(2,31))).length==this.ToUint32(Math.pow(2,31)));
//
//	this.assertTrue((Array(Math.pow(2,31))).length==this.ToUint32(Math.pow(2,31)))
//	this.assertTrue((Array(Math.pow(2,31)+1)).length==this.ToUint32(Math.pow(2,31)+1));
//	this.assertTrue((Array('8589934592')).length==1)
//	this.assertTrue((Array('4294967296')).length==1);
//	this.assertTrue((Array(1073741823)).length==this.ToUint32(1073741823))
//	this.assertTrue((Array(1073741824)).length==this.ToUint32(1073741824))
//	this.assertTrue((Array('a string')).length==1);
//
//
//},

/**
File Name:          15.4.1.3.js
ECMA Section:       15.4.1.3 Array()

Description:        When Array is called as a function rather than as a
constructor, it creates and initializes a new array
object.  Thus, the function call Array(...) is
equivalent to the object creationi new Array(...) with
the same arguments.

An array is created and returned as if by the
expression new Array(len).

Author:             christine@netscape.com
Date:               7 october 1997
*/

test_15_4_1_3:function(){

this.assertTrue(typeof Array()=="object");
MYARR = new Array();
MYARR.getClass =
Object.prototype.toString;
this.assertTrue(MYARR.getClass()=="[object Array]");
this.assertTrue((Array()).length==0);
this.assertTrue(Array().toString()=="");

},


/**
File Name:          15.4.2.1-1.js
ECMA Section:       15.4.2.1 new Array( item0, item1, ... )
Description:        This description only applies of the constructor is
given two or more arguments.

The [[Prototype]] property of the newly constructed
object is set to the original Array prototype object,
the one that is the initial value of Array.prototype
(15.4.3.1).

The [[Class]] property of the newly constructed object
is set to "Array".

The length property of the newly constructed object is
set to the number of arguments.

The 0 property of the newly constructed object is set
to item0... in general, for as many arguments as there
are, the k property of the newly constructed object is
set to argument k, where the first argument is
considered to be argument number 0.

This file tests the typeof the newly constructed object.

Author:             christine@netscape.com
Date:               7 october 1997
*/

test_15_4_2_1_1:function(){

this.assertTrue(typeof new Array(1,2)=="object");

this.assertTrue((new Array(1,2)).toString==Array.prototype.toString);

var arr = new Array(1,2,3);
arr.getClass = Object.prototype.toString;
this.assertTrue(arr.getClass()=="[object Array]");
this.assertTrue((new Array(1,2)).length==2);
var arr = (new Array(1,2));
this.assertTrue(arr[0]==1);

var arr = (new Array(1,2));
this.assertTrue(arr[1]==2);
var arr = (new Array(1,2));
this.assertTrue(String(arr)== "1,2");



},

test_15_4_2_1_2: function () {

var TEST_STRING = "new Array(";
var ARGUMENTS = ""
var TEST_LENGTH = Math.pow(2,10); //Math.pow(2,32);

for ( var index = 0; index < TEST_LENGTH; index++ ) {
ARGUMENTS += index;
ARGUMENTS += (index==(TEST_LENGTH-1) ) ? "" : ",";
}

TEST_STRING += ARGUMENTS + ")";

TEST_ARRAY = eval( TEST_STRING );

for ( var item = 0; item < TEST_LENGTH; item++ ) {

this.assertTrue(TEST_ARRAY[item]==item);

}
this.assertTrue( TEST_ARRAY +"" ==ARGUMENTS);

},

/**
File Name:          15.4.2.1-3.js
ECMA Section:       15.4.2.1 new Array( item0, item1, ... )
Description:        This description only applies of the constructor is
given two or more arguments.

The [[Prototype]] property of the newly constructed
object is set to the original Array prototype object,
the one that is the initial value of Array.prototype
(15.4.3.1).

The [[Class]] property of the newly constructed object
is set to "Array".

The length property of the newly constructed object is
set to the number of arguments.

The 0 property of the newly constructed object is set
to item0... in general, for as many arguments as there
are, the k property of the newly constructed object is
set to argument k, where the first argument is
considered to be argument number 0.

This test stresses the number of arguments presented to
the Array constructor.  Should support up to Math.pow
(2,32) arguments, since that is the maximum length of an
ECMAScript array.

***Change TEST_LENGTH to Math.pow(2,32) when larger array
lengths are supported.

Author:             christine@netscape.com
Date:               7 october 1997
*/

test_15_4_2_1_3:function(){
var TEST_STRING = "new Array(";
var ARGUMENTS = ""
var TEST_LENGTH = Math.pow(2,10); //Math.pow(2,32);

for ( var index = 0; index < TEST_LENGTH; index++ ) {
ARGUMENTS += index;
ARGUMENTS += (index==(TEST_LENGTH-1) ) ? "" : ",";
}

TEST_STRING += ARGUMENTS + ")";

TEST_ARRAY = eval( TEST_STRING );

for ( var item = 0; item < TEST_LENGTH; item++ ) {
this.assertTrue(TEST_ARRAY[item]==item);
}

this.assertTrue( TEST_ARRAY +""==ARGUMENTS);
this.assertTrue(Array.prototype.toString==TEST_ARRAY.toString );
this.assertTrue(Array.prototype.join==TEST_ARRAY.join );
this.assertTrue(Array.prototype.sort==TEST_ARRAY.sort );
this.assertTrue(Array.prototype.reverse==TEST_ARRAY.reverse );
this.assertTrue(TEST_LENGTH==TEST_ARRAY.length );
TEST_ARRAY.toString = Object.prototype.toString;
this.assertTrue("[object Array]"== ( TEST_ARRAY.toString()));


},

/**
File Name:          15.4.2.2-1.js
ECMA Section:       15.4.2.2 new Array(len)

Description:        This description only applies of the constructor is
given two or more arguments.

The [[Prototype]] property of the newly constructed
object is set to the original Array prototype object,
the one that is the initial value of Array.prototype(0)
(15.4.3.1).

The [[Class]] property of the newly constructed object
is set to "Array".

If the argument len is a number, then the length
property  of the newly constructed object is set to
ToUint32(len).

If the argument len is not a number, then the length
property of the newly constructed object is set to 1
and the 0 property of the newly constructed object is
set to len.

This file tests cases where len is a number.

The cases in this test need to be updated since the
ToUint32 description has changed.

Author:             christine@netscape.com
Date:               7 october 1997
*/

test_15_4_2_2_1:function(){

this.assertTrue(""==(new Array(0)).toString() );
this.assertTrue("object"==(typeof new Array(0)) );
this.assertTrue(0==(new Array(0)).length );
this.assertTrue(Array.prototype.toString==(new Array(0)).toString );
this.assertTrue(""==(new Array(1)).toString() );
this.assertTrue(1==(new Array(1)).length );
this.assertTrue(Array.prototype.toString==(new Array(1)).toString );
this.assertTrue(0==(new Array(-0)).length );
this.assertTrue(0==(new Array(0)).length );
this.assertTrue(10==(new Array(10)).length );
this.assertTrue(1==(new Array('1')).length );
this.assertTrue(1000==(new Array(1000)).length );
this.assertTrue(1==(new Array('1000')).length );
this.assertTrue(this.ToUint32(4294967295)==(new Array(4294967295)).length );
this.assertTrue(1==(new Array("8589934592")).length );
this.assertTrue(1==(new Array("4294967296")).length );
this.assertTrue(this.ToUint32(1073741824)==(new Array(1073741824)).length );

},

/**
File Name:          15.4.2.2-2.js
ECMA Section:       15.4.2.2 new Array(len)

Description:        This description only applies of the constructor is
given two or more arguments.

The [[Prototype]] property of the newly constructed
object is set to the original Array prototype object,
the one that is the initial value of Array.prototype(0)
(15.4.3.1).

The [[Class]] property of the newly constructed object
is set to "Array".

If the argument len is a number, then the length
property  of the newly constructed object is set to
ToUint32(len).

If the argument len is not a number, then the length
property of the newly constructed object is set to 1
and the 0 property of the newly constructed object is
set to len.

This file tests length of the newly constructed array
when len is not a number.

Author:             christine@netscape.com
Date:               7 october 1997
*/

test_15_4_2_2_2:function(){

this.assertTrue(1==(new Array(new Number(1073741823))).length );
this.assertTrue(1==(new Array(new Number(0))).length );
this.assertTrue(1==(new Array(new Number(1000))).length );
this.assertTrue(1==(new Array('mozilla, larryzilla, curlyzilla')).length );
this.assertTrue(1==(new Array(true)).length );
this.assertTrue(1==(new Array(false)).length);
this.assertTrue(1==(new Array(new Boolean(true))).length );
this.assertTrue(1==(new Array(new Boolean(false))).length );

},

/**
File Name:          15.4.2.3.js
ECMA Section:       15.4.2.3 new Array()
Description:        The [[Prototype]] property of the newly constructed
object is set to the origianl Array prototype object,
the one that is the initial value of Array.prototype.
The [[Class]] property of the new object is set to
"Array".  The length of the object is set to 0.

Author:             christine@netscape.com
Date:               7 october 1997
*/

test_15_4_2_3:function(){
this.assertTrue(""== (new Array()) +"" );
this.assertTrue("object"== (typeof new Array()) );

var arr = new Array();
arr.getClass = Object.prototype.toString;
this.assertTrue(arr.getClass()=="[object Array]");
this.assertTrue(0==(new Array()).length );
this.assertTrue((new Array()).toString==Array.prototype.toString );
this.assertTrue((new Array()).join ==Array.prototype.join );
this.assertTrue((new Array()).reverse ==Array.prototype.reverse );
this.assertTrue((new Array()).sort ==Array.prototype.sort );


},

/**
File Name:          15.4.3.1-1.js
ECMA Section:       15.4.3.1 Array.prototype
Description:        The initial value of Array.prototype is the built-in
Array prototype object (15.4.4).

Author:             christine@netscape.com
Date:               7 october 1997
*/

test_15_4_3_1_1:function(){

var ARRAY_PROTO = Array.prototype;

var props = '';
for (var  p in Array  ) {
props += p
}
this.assertTrue(props=="");

Array.prototype = null;
this.assertTrue(ARRAY_PROTO==Array.prototype)

assertFalse(delete Array.prototype);

delete Array.prototype;

this.assertTrue(ARRAY_PROTO==Array.prototype);


},

/**
File Name:          15.4.3.2.js
ECMA Section:       15.4.3.2 Array.length
Description:        The length property is 1.

Author:             christine@netscape.com
Date:               7 october 1997
*/

test_15_4_3_2:function(){
this.assertTrue(Array.length==1);
},

/**
File Name:          15.4.4.1.js
ECMA Section:       15.4.4.1 Array.prototype.constructor
Description:        The initial value of Array.prototype.constructor
is the built-in Array constructor.
Author:             christine@netscape.com
Date:               7 october 1997
*/

test_15_4_4_1:function(){
this.assertTrue(Array.prototype.constructor==Array);

},

/**
File Name:          15.4.4.2.js
ECMA Section:       15.4.4.2 Array.prototype.toString()
Description:        The elements of this object are converted to strings
and these strings are then concatenated, separated by
comma characters.  The result is the same as if the
built-in join method were invoiked for this object
with no argument.
Author:             christine@netscape.com
Date:               7 october 1997
*/

test_15_4_4_2:function(){

this.assertTrue(0==Array.prototype.toString.length);

this.assertTrue(""==(new Array()).toString() );
this.assertTrue(","==(new Array(2)).toString() );
this.assertTrue("0,1"==(new Array(0,1)).toString() );
this.assertTrue("NaN,Infinity,-Infinity"==(new Array( Number.NaN, Number.POSITIVE_INFINITY, Number.NEGATIVE_INFINITY)).toString() );
this.assertTrue("true,false"==(new Array(Boolean(1),Boolean(0))).toString() );
this.assertTrue(","==(new Array(void 0,null)).toString() );

var EXPECT_STRING = "";
var MYARR = new Array();

for ( var i = -50; i < 50; i+= 0.25 ) {
MYARR[MYARR.length] = i;
EXPECT_STRING += i +",";
}

EXPECT_STRING = EXPECT_STRING.substring( 0, EXPECT_STRING.length -1 );
this.assertTrue(EXPECT_STRING==MYARR.toString() );

},

/**
File Name:    15.4.4.3-1.js
ECMA Section: 15.4.4.3-1 Array.prototype.join()
Description:  The elements of this object are converted to strings and
these strings are then concatenated, separated by comma
characters. The result is the same as if the built-in join
method were invoiked for this object with no argument.
Author:       christine@netscape.com, pschwartau@netscape.com
Date:         07 October 1997
Modified:     14 July 2002
Reason:       See http://bugzilla.mozilla.org/show_bug.cgi?id=155285
ECMA-262 Ed.3  Section 15.4.4.5 Array.prototype.join()
Step 3: If |separator| is |undefined|, let |separator|
be the single-character string ","
*
*/

test_15_4_4_3_1:function(){


var ARR_PROTOTYPE = Array.prototype;

this.assertTrue(1==Array.prototype.join.length );
assertFalse( delete Array.prototype.join.length );
delete Array.prototype.join.length;
this.assertTrue(1==Array.prototype.join.length );

// case where array length is 0

var TEST_ARRAY = new Array();
this.assertTrue(""==TEST_ARRAY.join());

// array length is 0, but spearator is specified

var TEST_ARRAY = new Array();
this.assertTrue(""==TEST_ARRAY.join(' '));

// length is greater than 0, separator is supplied
var TEST_ARRAY = new Array(null, void 0, true, false, 123, new Object(), new Boolean(true) );
this.assertTrue("&&true&false&123&[object Object]&true"==TEST_ARRAY.join('&'));

// length is greater than 0, separator is empty string
var TEST_ARRAY = new Array(null, void 0, true, false, 123, new Object(), new Boolean(true) );
this.assertTrue("truefalse123[object Object]true"==TEST_ARRAY.join(''));


// length is greater than 0, separator is undefined
var TEST_ARRAY = new Array(null, void 0, true, false, 123, new Object(), new Boolean(true) );
this.assertTrue(",,true,false,123,[object Object],true"==TEST_ARRAY.join(void 0));



// length is greater than 0, separator is not supplied
var TEST_ARRAY = new Array(null, void 0, true, false, 123, new Object(), new Boolean(true) );
this.assertTrue(",,true,false,123,[object Object],true"==TEST_ARRAY.join());


// separator is a control character
var TEST_ARRAY = new Array(null, void 0, true, false, 123, new Object(), new Boolean(true) );
this.assertTrue(decodeURIComponent("%0B%0Btrue%0Bfalse%0B123%0B[object Object]%0Btrue")==TEST_ARRAY.join('\v'));

// length of array is 1
var TEST_ARRAY = new Array(true);
this.assertTrue(TEST_ARRAY.join('\v'));


SEPARATOR = "\t"
TEST_LENGTH = 100;
TEST_STRING = "";
ARGUMENTS = "";
TEST_RESULT = "";

for ( var index = 0; index < TEST_LENGTH; index++ ) {
ARGUMENTS   += index;
ARGUMENTS   += ( index==TEST_LENGTH -1 ) ? "" : ",";

TEST_RESULT += index;
TEST_RESULT += ( index==TEST_LENGTH -1 ) ? "" : SEPARATOR;
}

TEST_ARRAY = eval( "new Array( "+ARGUMENTS +")" );

this.assertTrue(TEST_RESULT==TEST_ARRAY.join( SEPARATOR ));
this.assertTrue("true,false,,,1e+21,1e-7"== (new Array( Boolean(true), Boolean(false), null,  void 0, Number(1e+21), Number(1e-7))).join());

var Object_1 = function( value ) {
this.array = value.split(",");
this.length = this.array.length;
for ( var i = 0; i < this.length; i++ ) {
this[i] = eval(this.array[i]);
}
this.join = Array.prototype.join;
this.getClass = Object.prototype.toString;
}


// this is not an Array object
var OB = new Object_1('true,false,111,0.5,1.23e6,NaN,void 0,null');
this.assertTrue("true:false:111:0.5:1230000:NaN::"==OB.join(':'));




},

/**
File Name:          15.4.4.4-1.js
ECMA Section:       15.4.4.4-1 Array.prototype.reverse()
Description:

The elements of the array are rearranged so as to reverse their order.
This object is returned as the result of the call.

1.   Call the [[Get]] method of this object with argument "length".
2.   Call ToUint32(Result(1)).
3.   Compute floor(Result(2)/2).
4.   Let k be 0.
5.   If k equals Result(3), return this object.
6.   Compute Result(2)k1.
7.   Call ToString(k).
8.   ToString(Result(6)).
9.   Call the [[Get]] method of this object with argument Result(7).
10.   Call the [[Get]] method of this object with argument Result(8).
11.   If this object has a property named by Result(8), go to step 12; but
if this object has no property named by Result(8), then go to either
step 12 or step 14, depending on the implementation.
12.   Call the [[Put]] method of this object with arguments Result(7) and
Result(10).
13.   Go to step 15.
14.   Call the [[Delete]] method on this object, providing Result(7) as the
name of the property to delete.
15.   If this object has a property named by Result(7), go to step 16; but if
this object has no property named by Result(7), then go to either step 16
or step 18, depending on the implementation.
16.   Call the [[Put]] method of this object with arguments Result(8) and
Result(9).
17.   Go to step 19.
18.   Call the [[Delete]] method on this object, providing Result(8) as the
name of the property to delete.
19.   Increase k by 1.
20.   Go to step 5.

Note that the reverse function is intentionally generic; it does not require
that its this value be an Array object. Therefore it can be transferred to other
kinds of objects for use as a method. Whether the reverse function can be applied
successfully to a host object is implementation dependent.

Note:   Array.prototype.reverse allows some flexibility in implementation
regarding array indices that have not been populated. This test covers the
cases in which unpopulated indices are not deleted, since the JavaScript
implementation does not delete uninitialzed indices.

Author:             christine@netscape.com
Date:               7 october 1997
*/

test_15_4_4_4_1:function(){

var SECTION = "15.4.4.4-1";
var VERSION = "ECMA_1";
var BUGNUMBER="123724";

this.assertTrue(typeof(this.TestCase)!="undefined");



Object_1 = function ( value ) {
this.array = value.split(",");
this.length = this.array.length;
for ( var i = 0; i < this.length; i++ ) {
this[i] = eval(this.array[i]);
}
this.join = Array.prototype.reverse;
this.getClass = Object.prototype.toString;
}

Reverse =function ( array ) {
var r2 = array.length;
var k = 0;
var r3 = Math.floor( r2/2 );
if ( r3==k ) {
return array;
}

for ( k = 0;  k < r3; k++ ) {
var r6 = r2 - k - 1;
//        var r7 = String( k );
var r7 = k;
var r8 = String( r6 );

var r9 = array[r7];
var r10 = array[r8];

array[r7] = r10;
array[r8] = r9;
}

return array;
}

Iterate =function( array ) {
for ( var i = 0; i < array.length; i++ ) {
//        print( i+": "+ array[String(i)] );
}
}

Object_1=function( value ) {
this.array = value.split(",");
this.length = this.array.length;
for ( var i = 0; i < this.length; i++ ) {
this[i] = this.array[i];
}
this.reverse = Array.prototype.reverse;
this.getClass = Object.prototype.toString;
}


var ARR_PROTOTYPE = Array.prototype;

this.assertTrue(0==Array.prototype.reverse.length );

assertFalse(delete Array.prototype.reverse.length );
delete Array.prototype.reverse.length;
this.assertTrue(0==Array.prototype.reverse.length);

// length of array is 0
var A = new Array();
A.reverse();
this.assertTrue(0==A.length);


// length of array is 1
var A = new Array(true);
var R = Reverse(A);

var A = new Array(true);   A.reverse();
this.assertTrue(A.length==R.length);


this.CheckItems( R, A );

// length of array is 2
var S = "var A = new Array( true,false )";
eval(S);
var R = Reverse(A);
this.assertTrue(R.length==eval( S + "; A.reverse(); A.length") );

this.CheckItems(  R, A );

// length of array is 3
var S = "var A = new Array( true,false,null )";
eval(S);
var R = Reverse(A);


this.TestCase( SECTION,
S +";  A.reverse(); A.length",
R.length,
eval( S + "; A.reverse(); A.length") );

this.CheckItems( R, A );

// length of array is 4
var S = "var A = new Array( true,false,null,void 0 )";
eval(S);
var R = Reverse(A);

this.TestCase( SECTION,
S +";  A.reverse(); A.length",
R.length,
eval( S + "; A.reverse(); A.length") );
this.CheckItems( R, A );


// some array indexes have not been set
var S = "var A = new Array(); A[8] = 'hi', A[3] = 'yo'";
eval(S);
var R = Reverse(A);

this.TestCase( SECTION,
S +";  A.reverse(); A.length",
R.length,
eval( S + "; A.reverse(); A.length") );

this.CheckItems( R, A );


var OBJECT_OBJECT = new Object();
var FUNCTION_OBJECT = new Function( 'return this' );
var BOOLEAN_OBJECT = new Boolean;
var DATE_OBJECT = new Date(0);
var STRING_OBJECT = new String('howdy');
var NUMBER_OBJECT = new Number(Math.PI);
var ARRAY_OBJECT= new Array(1000);

var args = "null, void 0, Math.pow(2,32), 1.234e-32, OBJECT_OBJECT, BOOLEAN_OBJECT, FUNCTION_OBJECT, DATE_OBJECT, STRING_OBJECT,"+
"ARRAY_OBJECT, NUMBER_OBJECT, Math, true, false, 123, '90210'";

var S = "var A = new Array("+args+")";
eval(S);
var R = Reverse(A);

this.TestCase( SECTION,
S +";  A.reverse(); A.length",
R.length,
eval( S + "; A.reverse(); A.length") );

this.CheckItems( R, A );

var limit = 1000;
var args = "";
for (var i = 0; i < limit; i++ ) {
args += i +"";
if ( i + 1 < limit ) {
args += ",";
}
}

var S = "var A = new Array("+args+")";
eval(S);
var R = Reverse(A);

this.TestCase( SECTION,
S +";  A.reverse(); A.length",
R.length,
eval( S + "; A.reverse(); A.length") );

this.CheckItems( R, A );

var S = "var MYOBJECT = new Object_1( \"void 0, 1, null, 2, \'\'\" )";
eval(S);
var R = Reverse( A );

this.TestCase( SECTION,
S +";  A.reverse(); A.length",
R.length,
eval( S + "; A.reverse(); A.length") );

this.CheckItems( R, A );

},

/**
File Name:          15.4.4.3-1.js
ECMA Section:       15.4.4.3-1 Array.prototype.reverse()
Description:

The elements of the array are rearranged so as to reverse their order.
This object is returned as the result of the call.

1.   Call the [[Get]] method of this object with argument "length".
2.   Call ToUint32(Result(1)).
3.   Compute floor(Result(2)/2).
4.   Let k be 0.
5.   If k equals Result(3), return this object.
6.   Compute Result(2)k1.
7.   Call ToString(k).
8.   ToString(Result(6)).
9.   Call the [[Get]] method of this object with argument Result(7).
10.   Call the [[Get]] method of this object with argument Result(8).
11.   If this object has a property named by Result(8), go to step 12; but
if this object has no property named by Result(8), then go to either
step 12 or step 14, depending on the implementation.
12.   Call the [[Put]] method of this object with arguments Result(7) and
Result(10).
13.   Go to step 15.
14.   Call the [[Delete]] method on this object, providing Result(7) as the
name of the property to delete.
15.   If this object has a property named by Result(7), go to step 16; but if
this object has no property named by Result(7), then go to either step 16
or step 18, depending on the implementation.
16.   Call the [[Put]] method of this object with arguments Result(8) and
Result(9).
17.   Go to step 19.
18.   Call the [[Delete]] method on this object, providing Result(8) as the
name of the property to delete.
19.   Increase k by 1.
20.   Go to step 5.

Note that the reverse function is intentionally generic; it does not require
that its this value be an Array object. Therefore it can be transferred to other
kinds of objects for use as a method. Whether the reverse function can be applied
successfully to a host object is implementation dependent.

Note:   Array.prototype.reverse allows some flexibility in implementation
regarding array indices that have not been populated. This test covers the
cases in which unpopulated indices are not deleted, since the JavaScript
implementation does not delete uninitialzed indices.

Author:             christine@netscape.com
Date:               7 october 1997
*/

test_15_4_4_4_2:function(){

var SECTION = "15.4.4.4-1";
var VERSION = "ECMA_1";

var ARR_PROTOTYPE = Array.prototype;

this.TestCase( SECTION, "Array.prototype.reverse.length",           0,      Array.prototype.reverse.length );
this.TestCase( SECTION, "delete Array.prototype.reverse.length",    false,  delete Array.prototype.reverse.length );
this.TestCase( SECTION, "delete Array.prototype.reverse.length; Array.prototype.reverse.length",    0, eval("delete Array.prototype.reverse.length; Array.prototype.reverse.length") );

// length of array is 0
this.TestCase(   SECTION,
"var A = new Array();   A.reverse(); A.length",
0,
eval("var A = new Array();   A.reverse(); A.length") );
},

/**
File Name:          15.4.4.5.js
ECMA Section:       Array.prototype.sort(comparefn)
Description:

This test file tests cases in which the compare function is not supplied.

The elements of this array are sorted. The sort is not necessarily stable.
If comparefn is provided, it should be a function that accepts two arguments
x and y and returns a negative value if x < y, zero if x = y, or a positive
value if x > y.

1.   Call the [[Get]] method of this object with argument "length".
2.   Call ToUint32(Result(1)).
1.  Perform an implementation-dependent sequence of calls to the
[[Get]] , [[Put]], and [[Delete]] methods of this object and
toSortCompare (described below), where the first argument for each call
to [[Get]], [[Put]] , or [[Delete]] is a nonnegative integer less
than Result(2) and where the arguments for calls to SortCompare are
results of previous calls to the [[Get]] method. After this sequence
is complete, this object must have the following two properties.
(1) There must be some mathematical permutation of the nonnegative
integers less than Result(2), such that for every nonnegative integer
j less than Result(2), if property old[j] existed, then new[(j)] is
exactly the same value as old[j],. but if property old[j] did not exist,
then new[(j)] either does not exist or exists with value undefined.
(2) If comparefn is not supplied or is a consistent comparison
function for the elements of this array, then for all nonnegative
integers j and k, each less than Result(2), if old[j] compares less
than old[k] (see SortCompare below), then (j) < (k). Here we use the
notation old[j] to refer to the hypothetical result of calling the [
[Get]] method of this object with argument j before this step is
executed, and the notation new[j] to refer to the hypothetical result
of calling the [[Get]] method of this object with argument j after this
step has been completely executed. A function is a consistent
comparison function for a set of values if (a) for any two of those
values (possibly the same value) considered as an ordered pair, it
always returns the same value when given that pair of values as its
two arguments, and the result of applying ToNumber to this value is
not NaN; (b) when considered as a relation, where the pair (x, y) is
considered to be in the relation if and only if applying the function
to x and y and then applying ToNumber to the result produces a
negative value, this relation is a partial order; and (c) when
considered as a different relation, where the pair (x, y) is considered
to be in the relation if and only if applying the function to x and y
and then applying ToNumber to the result produces a zero value (of either
sign), this relation is an equivalence relation. In this context, the
phrase "x compares less than y" means applying Result(2) to x and y and
then applying ToNumber to the result produces a negative value.
3.Return this object.

When the SortCompare operator is called with two arguments x and y, the following steps are taken:
1.If x and y are both undefined, return +0.
2.If x is undefined, return 1.
3.If y is undefined, return 1.
4.If the argument comparefn was not provided in the call to sort, go to step 7.
5.Call comparefn with arguments x and y.
6.Return Result(5).
7.Call ToString(x).
8.Call ToString(y).
9.If Result(7) < Result(8), return 1.
10.If Result(7) > Result(8), return 1.
11.Return +0.

Note that, because undefined always compared greater than any other value, undefined and nonexistent
property values always sort to the end of the result. It is implementation-dependent whether or not such
properties will exist or not at the end of the array when the sort is concluded.

Note that the sort function is intentionally generic; it does not require that its this value be an Array object.
Therefore it can be transferred to other kinds of objects for use as a method. Whether the sort function can be
applied successfully to a host object is implementation dependent .

Author:             christine@netscape.com
Date:               12 november 1997
*/

test_15_4_4_5_1:function(){

var S = new Array();
var item = 0;

// array is empty.
S[item++] = "var A = new Array()";

// array contains one item
S[item++] = "var A = new Array( true )";

// length of array is 2
S[item++] = "var A = new Array( true, false, new Boolean(true), new Boolean(false), 'true', 'false' )";

S[item++] = "var A = new Array(); A[3] = 'undefined'; A[6] = null; A[8] = 'null'; A[0] = void 0";

S[item] = "var A = new Array( ";

var limit = 0x0061;
for ( var i = 0x007A; i >= limit; i-- ) {
S[item] += "\'"+ String.fromCharCode(i) +"\'" ;
if ( i > limit ) {
S[item] += ",";
}
}

S[item] += ")";

item++;

for ( var i = 0; i < S.length; i++ ) {
this.CheckItems2( S[i] );
}


},

/**
File Name:          15.4.4.5-2.js
ECMA Section:       Array.prototype.sort(comparefn)
Description:

This test file tests cases in which the compare function is supplied.
In this cases, the sort creates a reverse sort.

The elements of this array are sorted. The sort is not necessarily stable.
If comparefn is provided, it should be a function that accepts two arguments
x and y and returns a negative value if x < y, zero if x = y, or a positive
value if x > y.

1.   Call the [[Get]] method of this object with argument "length".
2.   Call ToUint32(Result(1)).
1.  Perform an implementation-dependent sequence of calls to the
[[Get]] , [[Put]], and [[Delete]] methods of this object and
toSortCompare (described below), where the first argument for each call
to [[Get]], [[Put]] , or [[Delete]] is a nonnegative integer less
than Result(2) and where the arguments for calls to SortCompare are
results of previous calls to the [[Get]] method. After this sequence
is complete, this object must have the following two properties.
(1) There must be some mathematical permutation of the nonnegative
integers less than Result(2), such that for every nonnegative integer
j less than Result(2), if property old[j] existed, then new[(j)] is
exactly the same value as old[j],. but if property old[j] did not exist,
then new[(j)] either does not exist or exists with value undefined.
(2) If comparefn is not supplied or is a consistent comparison
function for the elements of this array, then for all nonnegative
integers j and k, each less than Result(2), if old[j] compares less
than old[k] (see SortCompare below), then (j) < (k). Here we use the
notation old[j] to refer to the hypothetical result of calling the [
[Get]] method of this object with argument j before this step is
executed, and the notation new[j] to refer to the hypothetical result
of calling the [[Get]] method of this object with argument j after this
step has been completely executed. A function is a consistent
comparison function for a set of values if (a) for any two of those
values (possibly the same value) considered as an ordered pair, it
always returns the same value when given that pair of values as its
two arguments, and the result of applying ToNumber to this value is
not NaN; (b) when considered as a relation, where the pair (x, y) is
considered to be in the relation if and only if applying the function
to x and y and then applying ToNumber to the result produces a
negative value, this relation is a partial order; and (c) when
considered as a different relation, where the pair (x, y) is considered
to be in the relation if and only if applying the function to x and y
and then applying ToNumber to the result produces a zero value (of either
sign), this relation is an equivalence relation. In this context, the
phrase "x compares less than y" means applying Result(2) to x and y and
then applying ToNumber to the result produces a negative value.
3.Return this object.

When the SortCompare operator is called with two arguments x and y, the following steps are taken:
1.If x and y are both undefined, return +0.
2.If x is undefined, return 1.
3.If y is undefined, return 1.
4.If the argument comparefn was not provided in the call to sort, go to step 7.
5.Call comparefn with arguments x and y.
6.Return Result(5).
7.Call ToString(x).
8.Call ToString(y).
9.If Result(7) < Result(8), return 1.
10.If Result(7) > Result(8), return 1.
11.Return +0.

Note that, because undefined always compared greater than any other value, undefined and nonexistent
property values always sort to the end of the result. It is implementation-dependent whether or not such
properties will exist or not at the end of the array when the sort is concluded.

Note that the sort function is intentionally generic; it does not require that its this value be an Array object.
Therefore it can be transferred to other kinds of objects for use as a method. Whether the sort function can be
applied successfully to a host object is implementation dependent .

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_4_4_5_2:function(){

var SECTION = "15.4.4.5-2";
var VERSION = "ECMA_1";

var TITLE   = "Array.prototype.sort(comparefn)";

var S = new Array();
var item = 0;

// array is empty.
S[item++] = "var A = new Array()";

// array contains one item
S[item++] = "var A = new Array( true )";

// length of array is 2
S[item++] = "var A = new Array( true, false, new Boolean(true), new Boolean(false), 'true', 'false' )";

S[item++] = "var A = new Array(); A[3] = 'undefined'; A[6] = null; A[8] = 'null'; A[0] = void 0";

S[item] = "var A = new Array( ";

var limit = 0x0061;
for ( var i = 0x007A; i >= limit; i-- ) {
S[item] += "\'"+ String.fromCharCode(i) +"\'" ;
if ( i > limit ) {
S[item] += ",";
}
}

S[item] += ")";

for ( var i = 0; i < S.length; i++ ) {
this.CheckItems2( S[i] );
}



},

/**
File Name:          15.4.4.5-3.js
ECMA Section:       Array.prototype.sort(comparefn)
Description:

This is a regression test for
http://scopus/bugsplat/show_bug.cgi?id=117144

Verify that sort is successfull, even if the sort compare function returns
a very large negative or positive value.

Author:             christine@netscape.com
Date:               12 november 1997
*/

test_15_4_4_5_3:function(){


comparefn1= function( x, y ) {
return x - y;
}


comparefn2=function( x, y ) {
return x.valueOf() - y.valueOf();
}

realsort=function ( x, y ) {
return ( x.valueOf()==y.valueOf() ? 0 : ( x.valueOf() > y.valueOf() ? 1 : -1 ) );
}
comparefn3 = function ( x, y ) {
return ( x==y ? 0 : ( x > y ? 1: -1 ) );
}
clone = function ( source, target ) {
for (i = 0; i < source.length; i++ ) {
target[i] = source[i];
}
}

stringsort = function ( x, y ) {
for ( var i = 0; i < x.toString().length; i++ ) {
var d = (x.toString()).charCodeAt(i) - (y.toString()).charCodeAt(i);
if ( d > 0 ) {
return 1;
} else {
if ( d < 0 ) {
return -1;
} else {
continue;
}
}

var d = x.length - y.length;

if  ( d > 0 ) {
return 1;
} else {
if ( d < 0 ) {
return -1;
}
}
}
return 0;
}

var SECTION = "15.4.4.5-3";
var VERSION = "ECMA_1";
var TITLE   = "Array.prototype.sort(comparefn)";


var array = new Array();

array[array.length] = new Date( this.vj$.EcmaArrayTests.TIME_2000 * Math.PI );
array[array.length] = new Date( this.vj$.EcmaArrayTests.TIME_2000 * 10 );
array[array.length] = new Date( this.vj$.EcmaArrayTests.TIME_1900 + this.vj$.EcmaArrayTests.TIME_1900  );
array[array.length] = new Date(0);
array[array.length] = new Date( this.vj$.EcmaArrayTests.TIME_2000 );
array[array.length] = new Date( this.vj$.EcmaArrayTests.TIME_1900 + this.vj$.EcmaArrayTests.TIME_1900 +this.vj$.EcmaArrayTests.TIME_1900 );
array[array.length] = new Date( this.vj$.EcmaArrayTests.TIME_1900 * Math.PI );
array[array.length] = new Date( this.vj$.EcmaArrayTests.TIME_1900 * 10 );
array[array.length] = new Date( this.vj$.EcmaArrayTests.TIME_1900 );
array[array.length] = new Date( this.vj$.EcmaArrayTests.TIME_2000 + this.vj$.EcmaArrayTests.TIME_2000 );
array[array.length] = new Date( 1899, 0, 1 );
array[array.length] = new Date( 2000, 1, 29 );
array[array.length] = new Date( 2000, 0, 1 );
array[array.length] = new Date( 1999, 11, 31 );

var testarr1 = new Array();
clone( array, testarr1 );
testarr1.sort( comparefn1 );

var testarr2 = new Array();
clone( array, testarr2 );
testarr2.sort( comparefn2 );

testarr3 = new Array();
clone( array, testarr3 );
testarr3.sort( comparefn3 );

// when there's no sort function, sort sorts by the toString value of Date.

var testarr4 = new Array();
clone( array, testarr4 );
testarr4.sort();

var realarr = new Array();
clone( array, realarr );
realarr.sort( realsort );

var stringarr = new Array();
clone( array, stringarr );
stringarr.sort( stringsort );

for ( var i = 0; i < array.length; i++) {
this.TestCase(
SECTION,
"testarr1["+i+"]",
realarr[i],
testarr1[i] );
}

for ( var i=0; i < array.length; i++) {
this.TestCase(
SECTION,
"testarr2["+i+"]",
realarr[i],
testarr2[i] );
}

for ( var i=0; i < array.length; i++) {
this.TestCase(
SECTION,
"testarr3["+i+"]",
realarr[i],
testarr3[i] );
}

for ( var i=0; i < array.length; i++) {
this.TestCase(
SECTION,
"testarr4["+i+"]",
stringarr[i].toString(),
testarr4[i].toString() );
}


},

test_15_4_4:function(){
var SECTION = "15.4.4";
var VERSION = "ECMA_1";
var TITLE   = "Properties of the Array Prototype Object";


this.TestCase( SECTION,	"Array.prototype.length",   0,          Array.prototype.length );

//  verify that prototype object is an Array object.
this.TestCase( SECTION,	"typeof Array.prototype",    "object",   typeof Array.prototype );

this.TestCase( SECTION,
"Array.prototype.toString = Object.prototype.toString; Array.prototype.toString()",
"[object Array]",
eval("Array.prototype.toString = Object.prototype.toString; Array.prototype.toString()") );



},

/**
File Name:          15.4.5.1-1.js
ECMA Section:       [[ Put]] (P, V)
Description:
Array objects use a variation of the [[Put]] method used for other native
ECMAScript objects (section 8.6.2.2).

Assume A is an Array object and P is a string.

When the [[Put]] method of A is called with property P and value V, the
following steps are taken:

1.  Call the [[CanPut]] method of A with name P.
2.  If Result(1) is false, return.
3.  If A doesn't have a property with name P, go to step 7.
4.  If P is "length", go to step 12.
5.  Set the value of property P of A to V.
6.  Go to step 8.
7.  Create a property with name P, set its value to V and give it empty
attributes.
8.  If P is not an array index, return.
9.  If A itself has a property (not an inherited property) named "length",
andToUint32(P) is less than the value of the length property of A, then
return.
10. Change (or set) the value of the length property of A to ToUint32(P)+1.
11. Return.
12. Compute ToUint32(V).
13. For every integer k that is less than the value of the length property
of A but not less than Result(12), if A itself has a property (not an
inherited property) named ToString(k), then delete that property.
14. Set the value of property P of A to Result(12).
15. Return.
Author:             christine@netscape.com
Date:               12 november 1997
*/

test_15_4_5_1_1:function(){
var SECTION = "15.4.5.1-1";
var VERSION = "ECMA_1";
var TITLE   = "Array [[Put]] (P, V)";


// P is "length"

this.TestCase(   SECTION,
"var A = new Array(); A.length = 1000; A.length",
1000,
eval("var A = new Array(); A.length = 1000; A.length") );

// A has Property P, and P is not length or an array index
this.TestCase(   SECTION,
"var A = new Array(1000); A.name = 'name of this array'; A.name",
'name of this array',
eval("var A = new Array(1000); A.name = 'name of this array'; A.name") );

this.TestCase(   SECTION,
"var A = new Array(1000); A.name = 'name of this array'; A.length",
1000,
eval("var A = new Array(1000); A.name = 'name of this array'; A.length") );


// A has Property P, P is not length, P is an array index, and ToUint32(p) is less than the
// value of length

this.TestCase(   SECTION,
"var A = new Array(1000); A[123] = 'hola'; A[123]",
'hola',
eval("var A = new Array(1000); A[123] = 'hola'; A[123]") );

this.TestCase(   SECTION,
"var A = new Array(1000); A[123] = 'hola'; A.length",
1000,
eval("var A = new Array(1000); A[123] = 'hola'; A.length") );


for ( var i = 0X0020, TEST_STRING = "var A = new Array( " ; i < 0x00ff; i++ ) {
TEST_STRING += "\'\\"+ String.fromCharCode( i ) +"\'";
if ( i < 0x00FF - 1   ) {
TEST_STRING += ",";
} else {
TEST_STRING += ");"
}
}

var LENGTH = 0x00ff - 0x0020;
vjo.sysout.println(TEST_STRING + " A[150] = 'hello'; A[150]");

this.TestCase(   SECTION,
TEST_STRING +" A[150] = 'hello'; A[150]",
'hello',
eval( TEST_STRING + " A[150] = 'hello'; A[150]" ) );

this.TestCase(   SECTION,
TEST_STRING +" A[150] = 'hello'; A[150]",
LENGTH,
eval( TEST_STRING + " A[150] = 'hello'; A.length" ) );

// A has Property P, P is not length, P is an array index, and ToUint32(p) is not less than the
// value of length

this.TestCase(   SECTION,
"var A = new Array(); A[123] = true; A.length",
124,
eval("var A = new Array(); A[123] = true; A.length") );

this.TestCase(   SECTION,
"var A = new Array(0,1,2,3,4,5,6,7,8,9,10); A[15] ='15'; A.length",
16,
eval("var A = new Array(0,1,2,3,4,5,6,7,8,9,10); A[15] ='15'; A.length") );

for ( var i = 0; i < A.length; i++ ) {
this.TestCase( SECTION,
"var A = new Array(0,1,2,3,4,5,6,7,8,9,10); A[15] ='15'; A[" +i +"]",
(i <= 10) ? i : ( i==15 ? '15' : void 0 ),
A[i] );
}
// P is not an array index, and P is not "length"

this.TestCase(   SECTION,
"var A = new Array(); A.join.length = 4; A.join.length",
1,
eval("var A = new Array(); A.join.length = 4; A.join.length") );

this.TestCase(   SECTION,
"var A = new Array(); A.join.length = 4; A.length",
0,
eval("var A = new Array(); A.join.length = 4; A.length") );



},

/**
File Name:          15.4.5.1-2.js
ECMA Section:       [[ Put]] (P, V)
Description:
Array objects use a variation of the [[Put]] method used for other native
ECMAScript objects (section 8.6.2.2).

Assume A is an Array object and P is a string.

When the [[Put]] method of A is called with property P and value V, the
following steps are taken:

1.  Call the [[CanPut]] method of A with name P.
2.  If Result(1) is false, return.
3.  If A doesn't have a property with name P, go to step 7.
4.  If P is "length", go to step 12.
5.  Set the value of property P of A to V.
6.  Go to step 8.
7.  Create a property with name P, set its value to V and give it empty
attributes.
8.  If P is not an array index, return.
9.  If A itself has a property (not an inherited property) named "length",
andToUint32(P) is less than the value of the length property of A, then
return.
10. Change (or set) the value of the length property of A to ToUint32(P)+1.
11. Return.
12. Compute ToUint32(V).
13. For every integer k that is less than the value of the length property
of A but not less than Result(12), if A itself has a property (not an
inherited property) named ToString(k), then delete that property.
14. Set the value of property P of A to Result(12).
15. Return.


These are gTestcases from Waldemar, detailed in
http://scopus.mcom.com/bugsplat/show_bug.cgi?id=123552

Author:             christine@netscape.com
Date:               15 June 1998
*/

//TODO original code good example of problem with definition and exec code that could cause problem
test_15_4_5_1_2:function(){



var SECTION = "15.4.5.1-2";
var VERSION = "ECMA_1";
var TITLE   = "Array [[Put]] (P,V)";


var a = new Array();



this.TestCase( SECTION,
"a[10]",
void 0,
a[10] );

this.TestCase( SECTION,
"a[3]",
void 0,
a[3] );

a[4] = "four";

this.TestCase( SECTION,
"a[4] = \"four\"; a[4]",
"four",
a[4] );

this.TestCase( SECTION,
"a[\"4\"]",
"four",
a["4"] );

this.TestCase( SECTION,
"a[\"4.00\"]",
void 0,
a["4.00"] );

this.TestCase( SECTION,
"a.length",
5,
a.length );


a["5000000000"] = 5;

this.TestCase( SECTION,
"a[\"5000000000\"] = 5; a.length",
5,
a.length );

this.TestCase( SECTION,
"a[\"-2\"] = -3; a.length",
5,
a.length );


this.addCase( "3.00", "three" );
this.addCase(  "00010", "eight" );
this.addCase( "37xyz", "thirty-five" );
this.addCase("5000000000", 5); // TODO figure out why this is failing
this.addCase( "-2", -3 );



},

addCase:function(a, arg,value){
var a = new Array();
a[arg] = value;

this.TestCase( "",
"a[\"" + arg + "\"] =  "+ value +"; a.length",
0,
a.length );

},

/**
File Name:          15.4.5.2-1.js
ECMA Section:       Array.length
Description:
15.4.5.2 length
The length property of this Array object is always numerically greater
than the name of every property whose name is an array index.

The length property has the attributes { DontEnum, DontDelete }.
Author:             christine@netscape.com
Date:               12 november 1997
*/

test_15_4_5_2_1:function(){
var SECTION = "15.4.5.2-1";
var VERSION = "ECMA_1";
var TITLE   = "Array.length";


this.TestCase(   SECTION,
"var A = new Array(); A.length",
0,
eval("var A = new Array(); A.length") );
this.TestCase(   SECTION,
"var A = new Array(); A[Math.pow(2,32)-2] = 'hi'; A.length",
Math.pow(2,32)-1,
eval("var A = new Array(); A[Math.pow(2,32)-2] = 'hi'; A.length") );
this.TestCase(   SECTION,
"var A = new Array(); A.length = 123; A.length",
123,
eval("var A = new Array(); A.length = 123; A.length") );
this.TestCase(   SECTION,
"var A = new Array(); A.length = 123; var PROPS = ''; for ( var p in A ) { PROPS += ( p == 'length' ? p : ''); } PROPS",
"",
eval("var A = new Array(); A.length = 123; var PROPS = ''; for ( var p in A ) { PROPS += ( p == 'length' ? p : ''); } PROPS") );
this.TestCase(   SECTION,
"var A = new Array(); A.length = 123; delete A.length",
false ,
eval("var A = new Array(); A.length = 123; delete A.length") );
this.TestCase(   SECTION,
"var A = new Array(); A.length = 123; delete A.length; A.length",
123,
eval("var A = new Array(); A.length = 123; delete A.length; A.length") );



},

/**
File Name:          15.4.5.2-2.js
ECMA Section:       Array.length
Description:
15.4.5.2 length
The length property of this Array object is always numerically greater
than the name of every property whose name is an array index.

The length property has the attributes { DontEnum, DontDelete }.

This test verifies that the Array.length property is not Read Only.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_4_5_2_2:function(){

var SECTION = "15.4.5.2-2";
var VERSION = "ECMA_1";
var TITLE   = "Array.length";

this.addCase2( new Array(), 0, Math.pow(2,14), Math.pow(2,14) );

this.addCase2( new Array(), 0, 1, 1 );

this.addCase2( new Array(Math.pow(2,12)), Math.pow(2,12), 0, 0 );
this.addCase2( new Array(Math.pow(2,13)), Math.pow(2,13), Math.pow(2,12), Math.pow(2,12) );
this.addCase2( new Array(Math.pow(2,12)), Math.pow(2,12), Math.pow(2,12), Math.pow(2,12) );
this.addCase2( new Array(Math.pow(2,14)), Math.pow(2,14), Math.pow(2,12), Math.pow(2,12) )

// some tests where array is not empty
// array is populated with strings
for ( var arg = "", i = 0; i < Math.pow(2,12); i++ ) {
arg +=  String(i) + ( i != Math.pow(2,12)-1 ? "," : "" );

}
//      print(i +":"+arg);

var a = eval( "new Array("+arg+")" );

this.addCase2( a, i, i, i );
this.addCase2( a, i, Math.pow(2,12)+i+1, Math.pow(2,12)+i+1, true );
this.addCase2( a, Math.pow(2,12)+5, 0, 0, true );

},


addCase2:function( object, old_len, set_len, new_len, checkitems ) {
object.length = set_len;

this.TestCase( "",
"array = new Array("+ old_len+"); array.length = " + set_len +
"; array.length",
new_len,
object.length );


if ( checkitems ) {
// verify that items between old and newlen are all undefined
if ( new_len < old_len ) {
var passed = true;
for ( var i = new_len; i < old_len; i++ ) {
if ( object[i] != void 0 ) {
passed = false;
}
}
this.TestCase( "",
"verify that array items have been deleted",
true,
passed );
}
if ( new_len > old_len ) {
var passed = true;
for ( var i = old_len; i < new_len; i++ ) {
if ( object[i] != void 0 ) {
passed = false;
}
}
this.TestCase( "",
"verify that new items are undefined",
true,
passed );

}
}

}
})
.endType();
