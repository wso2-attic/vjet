vjo.ctype("dsf.jslang.feature.tests.Ecma3RegExpTests")
.inherits("com.ebay.dsf.jslang.feature.tests.BaseTestEcma3")
.protos({


MSG_PATTERN : '\nregexp = ',
MSG_STRING : '\nstring = ',
MSG_EXPECT : '\nExpect: ',
MSG_ACTUAL : '\nActual: ',
ERR_LENGTH : '\nERROR !!! match arrays have different lengths:',
ERR_MATCH : '\nERROR !!! regexp failed to give expected match array:',
ERR_NO_MATCH : '\nERROR !!! regexp FAILED to match anything !!!',
ERR_UNEXP_MATCH : '\nERROR !!! regexp MATCHED when we expected it to fail !!!',
CHAR_LBRACKET : '[',
CHAR_RBRACKET : ']',
CHAR_QT_DBL : '"',
CHAR_QT : "'",
CHAR_NL : '\n',
CHAR_COMMA : ',',
CHAR_SPACE : ' ',
TYPE_STRING : typeof 'abc',

constructs: function() {
this.base();
},

inSection : function(x) {
var SECT_PREFIX = 'Section ';
var SECT_SUFFIX = ' of test - ';
return SECT_PREFIX + x + SECT_SUFFIX;
},

reportCompare : function (e, a, d){
this.TestCase("", d, e, a);
},

gc : function () {
for (var i = 0; i != 4e6; ++i)
{
var tmp = i + 0.1;
}
},

testRegExp : function (statuses, patterns, strings, actualmatches, expectedmatches)
{
var status = '';
var pattern = new RegExp();
var string = '';
var actualmatch = new Array();
var expectedmatch = new Array();
var state = '';
var lActual = -1;
var lExpect = -1;


for (var i=0; i != patterns.length; i++)
{
status = statuses[i];
pattern = patterns[i];
string = strings[i];
actualmatch=actualmatches[i];
expectedmatch=expectedmatches[i];
state = this.getState(status, pattern, string);

description = status;

if(actualmatch)
{
actual = this.formatArray(actualmatch);
if(expectedmatch)
{
// expectedmatch and actualmatch are arrays -
lExpect = expectedmatch.length;
lActual = actualmatch.length;

var expected = this.formatArray(expectedmatch);

if (lActual != lExpect)
{
this.reportCompare(lExpect, lActual,
state + this.ERR_LENGTH +
this.MSG_EXPECT + expected +
this.MSG_ACTUAL + actual +
this.CHAR_NL
);
continue;
}

// OK, the arrays have same length -
if (expected != actual)
{
this.reportCompare(expected, actual,
state + this.ERR_MATCH +
this.MSG_EXPECT + expected +
this.MSG_ACTUAL + actual +
this.CHAR_NL
);
}
else
{
this.reportCompare(expected, actual, state)
}

}
else //expectedmatch is null - that is, we did not expect a match -
{
expected = expectedmatch;
this.reportCompare(expected, actual,
state + this.ERR_UNEXP_MATCH +
this.MSG_EXPECT + expectedmatch +
this.MSG_ACTUAL + actual +
this.CHAR_NL
);
}

}
else // actualmatch is null
{
if (expectedmatch)
{
actual = actualmatch;
this.reportCompare(expected, actual,
state + this.ERR_NO_MATCH +
this.MSG_EXPECT + expectedmatch +
this.MSG_ACTUAL + actualmatch +
this.CHAR_NL
);
}
else // we did not expect a match
{
// Being ultra-cautious. Presumably expectedmatch===actualmatch===null
expected = expectedmatch;
actual   = actualmatch;
this.reportCompare (expectedmatch, actualmatch, state);
}
}
}
},


getState : function (status, pattern, string)
{
/*
* Escape \n's, etc. to make them LITERAL in the presentation string.
* We don't have to worry about this in |pattern|; such escaping is
* done automatically by pattern.toString(), invoked implicitly below.
*
* One would like to simply do: string = string.replace(/(\s)/g, '\$1').
* However, the backreference $1 is not a literal string value,
* so this method doesn't work.
*
* Also tried string = string.replace(/(\s)/g, escape('$1'));
* but this just inserts the escape of the literal '$1', i.e. '%241'.
*/
string = string.replace(/\n/g, '\\n');
string = string.replace(/\r/g, '\\r');
string = string.replace(/\t/g, '\\t');
string = string.replace(/\v/g, '\\v');
string = string.replace(/\f/g, '\\f');

return (status + this.MSG_PATTERN + pattern + this.MSG_STRING + this.singleQuote(string));
},


/*
* If available, arr.toSource() gives more detail than arr.toString()
*
* var arr = Array(1,2,'3');
*
* arr.toSource()
* [1, 2, "3"]
*
* arr.toString()
* 1,2,3
*
* But toSource() doesn't exist in Rhino, so use our own imitation, below -
*
*/
formatArray : function (arr)
{
try
{
return arr.toSource();
}
catch(e)
{
return this.toSource(arr);
}
},


/*
* Imitate SpiderMonkey's arr.toSource() method:
*
* a) Double-quote each array element that is of string type
* b) Represent |undefined| and |null| by empty strings
* c) Delimit elements by a comma + single space
* d) Do not add delimiter at the end UNLESS the last element is |undefined|
* e) Add square brackets to the beginning and end of the string
*/
toSource : function (arr)
{
var delim = this.CHAR_COMMA + this.CHAR_SPACE;
var elt = '';
var ret = '';
var len = arr.length;

for (i=0; i<len; i++)
{
elt = arr[i];

switch(true)
{
case (typeof elt === this.TYPE_STRING) :
ret += this.doubleQuote(elt);
break;

case (elt === undefined || elt === null) :
break; // add nothing but the delimiter, below -

default:
ret += elt.toString();
}

if ((i < len-1) || (elt === undefined))
ret += delim;
}

return  this.CHAR_LBRACKET + ret + this.CHAR_RBRACKET;
},


doubleQuote : function (text)
{
return this.CHAR_QT_DBL + text + this.CHAR_QT_DBL;
},


singleQuote : function (text)
{
return this.CHAR_QT + text + this.CHAR_QT;
},

/*
Calculate the "order" of a set of data points {X: [], Y: []}
by computing successive "derivatives" of the data until
the data is exhausted or the derivative is linear.
*/
BigO : function (data)
{
var order = 0;
var origLength = data.X.length;

while (data.X.length > 2)
{
var lr = new LinearRegression(data);
if (lr.b > 1e-6)
{
// only increase the order if the slope
// is "great" enough
order++;
}

if (lr.r > 0.98 || lr.Syx < 1 || lr.b < 1e-6)
{
// terminate if close to a line lr.r
// small error lr.Syx
// small slope lr.b
break;
}
data = dataDeriv(data);
}

if (2 == origLength - order)
{
order = Number.POSITIVE_INFINITY;
}
function LinearRegression(data)
{
/*
y = a + bx
for data points (Xi, Yi); 0 <= i < n

b = (n*SUM(XiYi) - SUM(Xi)*SUM(Yi))/(n*SUM(Xi*Xi) - SUM(Xi)*SUM(Xi))
a = (SUM(Yi) - b*SUM(Xi))/n
*/
var i;

if (data.X.length != data.Y.length)
{
throw 'LinearRegression: data point length mismatch';
}
if (data.X.length < 3)
{
throw 'LinearRegression: data point length < 2';
}
var n = data.X.length;
var X = data.X;
var Y = data.Y;

this.Xavg = 0;
this.Yavg = 0;

var SUM_X  = 0;
var SUM_XY = 0;
var SUM_XX = 0;
var SUM_Y  = 0;
var SUM_YY = 0;

for (i = 0; i < n; i++)
{
SUM_X  += X[i];
SUM_XY += X[i]*Y[i];
SUM_XX += X[i]*X[i];
SUM_Y  += Y[i];
SUM_YY += Y[i]*Y[i];
}

this.b = (n * SUM_XY - SUM_X * SUM_Y)/(n * SUM_XX - SUM_X * SUM_X);
this.a = (SUM_Y - this.b * SUM_X)/n;

this.Xavg = SUM_X/n;
this.Yavg = SUM_Y/n;

var SUM_Ydiff2 = 0;
var SUM_Xdiff2 = 0;
var SUM_XdiffYdiff = 0;

for (i = 0; i < n; i++)
{
var Ydiff = Y[i] - this.Yavg;
var Xdiff = X[i] - this.Xavg;

SUM_Ydiff2 += Ydiff * Ydiff;
SUM_Xdiff2 += Xdiff * Xdiff;
SUM_XdiffYdiff += Xdiff * Ydiff;
}

var Syx2 = (SUM_Ydiff2 - Math.pow(SUM_XdiffYdiff/SUM_Xdiff2, 2))/(n - 2);
var r2   = Math.pow((n*SUM_XY - SUM_X * SUM_Y), 2) /
((n*SUM_XX - SUM_X*SUM_X)*(n*SUM_YY-SUM_Y*SUM_Y));

this.Syx = Math.sqrt(Syx2);
this.r = Math.sqrt(r2);

}

function dataDeriv(data)
{
if (data.X.length != data.Y.length)
{
throw 'length mismatch';
}
var length = data.X.length;

if (length < 2)
{
throw 'length ' + length + ' must be >= 2';
}
var X = data.X;
var Y = data.Y;

var deriv = {X: [], Y: [] };

for (var i = 0; i < length - 1; i++)
{
deriv.X[i] = (X[i] + X[i+1])/2;
deriv.Y[i] = (Y[i+1] - Y[i])/(X[i+1] - X[i]);
}
return deriv;
}

return order;
},




/**
File Name:          15_10_2__1.js
Date:    09 July 2002
SUMMARY: RegExp conformance test

These gTestcases are derived from the examples in the ECMA-262 Ed.3 spec
scattered through section 15.10.2
*/
test_15_10_2__1 : function() {
var i = 0;
//var BUGNUMBER = '(none)';
var summary = 'RegExp conformance test';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();


status = this.inSection(1);
pattern = /a|ab/;
string = 'abc';
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();

status = this.inSection(2);
pattern = /((a)|(ab))((c)|(bc))/;
string = 'abc';
actualmatch = string.match(pattern);
expectedmatch = Array('abc', 'a', 'a', undefined, 'bc', undefined, 'bc');
addThis();

status = this.inSection(3);
pattern = /a[a-z]{2,4}/;
string = 'abcdefghi';
actualmatch = string.match(pattern);
expectedmatch = Array('abcde');
addThis();

status = this.inSection(4);
pattern = /a[a-z]{2,4}?/;
string = 'abcdefghi';
actualmatch = string.match(pattern);
expectedmatch = Array('abc');
addThis();

status = this.inSection(5);
pattern = /(aa|aabaac|ba|b|c)*/;
string = 'aabaac';
actualmatch = string.match(pattern);
expectedmatch = Array('aaba', 'ba');
addThis();

status = this.inSection(6);
pattern = /^(a+)\1*,\1+$/;
string = 'aaaaaaaaaa,aaaaaaaaaaaaaaa';
actualmatch = string.match(pattern);
expectedmatch = Array('aaaaaaaaaa,aaaaaaaaaaaaaaa', 'aaaaa');
addThis();

status = this.inSection(7);
pattern = /(z)((a+)?(b+)?(c))*/;
string = 'zaacbbbcac';
actualmatch = string.match(pattern);
expectedmatch = Array('zaacbbbcac', 'z', 'ac', 'a', undefined, 'c');
addThis();

status = this.inSection(8);
pattern = /(a*)*/;
string = 'b';
actualmatch = string.match(pattern);
expectedmatch = Array('', undefined);
addThis();

status = this.inSection(9);
pattern = /(a*)b\1+/;
string = 'baaaac';
actualmatch = string.match(pattern);
expectedmatch = Array('b', '');
addThis();

status = this.inSection(10);
pattern = /(?=(a+))/;
string = 'baaabac';
actualmatch = string.match(pattern);
expectedmatch = Array('', 'aaa');
addThis();

status = this.inSection(11);
pattern = /(?=(a+))a*b\1/;
string = 'baaabac';
actualmatch = string.match(pattern);
expectedmatch = Array('aba', 'a');
addThis();

status = this.inSection(12);
pattern = /(.*?)a(?!(a+)b\2c)\2(.*)/;
string = 'baaabaac';
actualmatch = string.match(pattern);
expectedmatch = Array('baaabaac', 'ba', undefined, 'abaac');
addThis();

status = this.inSection(13);
pattern = /(?=(a+))/;
string = 'baaabac';
actualmatch = string.match(pattern);
expectedmatch = Array('', 'aaa');
addThis();



//-------------------------------------------------------------------------------------------------
//test();
//-------------------------------------------------------------------------------------------------


function addThis()
{
statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


//function test()
//{
// enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);
this.testRegExp(statusmessages, patterns, strings, actualmatches, expectedmatches);
//exitFunc ('test');
//}

},

/**
*  File Name:          15_10_2_12.js
*/
test_15_10_2_12 : function() {
//var BUGNUMBER = 378738;
var summary = '15.10.2.12 - CharacterClassEscape \d';
var actual = '';
var expect = '';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

expect = false;
actual = /\d/.test("\uFF11");

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/**
*  File Name:          15_10_3_1__1.js
* Date: 26 November 2000
*
*
* SUMMARY: Passing (RegExp object, flag) to RegExp() function.
* This test arose from Bugzilla bug 61266. The ECMA3 section is:
*
* 15.10.3 The RegExp Constructor Called as a Function
*
*   15.10.3.1 RegExp(pattern, flags)
*
*   If pattern is an object R whose [[Class]] property is "RegExp"
*   and flags is undefined, then return R unchanged.  Otherwise
*   call the RegExp constructor (section 15.10.4.1),  passing it the
*   pattern and flags arguments and return  the object constructed
*   by that constructor.
*
*
* The current test will check the first scenario outlined above:
*
*   "pattern" is itself a RegExp object R
*   "flags" is undefined
*
* The flags parameter will be undefined in the sense of not being
* provided. We check that RegExp(R) returns R  -
*/
test_15_10_3_1__1 : function() {
//var BUGNUMBER = '61266';
var summary = 'Passing (RegExp object,flag) to RegExp() function';
var statprefix = 'RegExp(new RegExp(';
var comma =  ', '; var singlequote = "'"; var closeparens = '))';
var cnSUCCESS = 'RegExp() returned the supplied RegExp object';
var cnFAILURE =  'RegExp() did NOT return the supplied RegExp object';
var i = -1; var j = -1; var s = ''; var f = '';
var obj = {};
var status = ''; var actual = ''; var expect = '';
var patterns = new Array();
var flags = new Array();


// various regular expressions to try -
patterns[0] = '';
patterns[1] = 'abc';
patterns[2] = '(.*)(3-1)\s\w';
patterns[3] = '(.*)(...)\\s\\w';
patterns[4] = '[^A-Za-z0-9_]';
patterns[5] = '[^\f\n\r\t\v](123.5)([4 - 8]$)';

// various flags to try -
flags[0] = 'i';
flags[1] = 'g';
flags[2] = 'm';
flags[3] = undefined;



//-------------------------------------------------------------------------------------------------
//test();
//-------------------------------------------------------------------------------------------------


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

for (i in patterns)
{
s = patterns[i];

for (j in flags)
{
f = flags[j];
status = getStatus(s, f);
obj = new RegExp(s, f);

actual = (obj == RegExp(obj))? cnSUCCESS : cnFAILURE;
expect = cnSUCCESS;
this.reportCompare (expect, actual, status);
}
}

//exitFunc ('test');
//}


function getStatus(regexp, flag)
{
return (statprefix  +  quote(regexp) +  comma  +   flag  +  closeparens);
}


function quote(text)
{
return (singlequote  +  text  + singlequote);
}

},

/**
*  File Name:          15_10_3_1__2.js
* Date: 26 November 2000
*
*
* SUMMARY: Passing (RegExp object, flag) to RegExp() function.
* This test arose from Bugzilla bug 61266. The ECMA3 section is:
*
* 15.10.3 The RegExp Constructor Called as a Function
*
*   15.10.3.1 RegExp(pattern, flags)
*
*   If pattern is an object R whose [[Class]] property is "RegExp"
*   and flags is undefined, then return R unchanged.  Otherwise
*   call the RegExp constructor (section 15.10.4.1),  passing it the
*   pattern and flags arguments and return  the object constructed
*   by that constructor.
*
*
* The current test will check the first scenario outlined above:
*
*   "pattern" is itself a RegExp object R
*   "flags" is undefined
*
* This test is identical to test 15.10.3.1-1.js, except here we do:
*
*                     RegExp(R, undefined);
*
* instead of:
*
*                     RegExp(R);
*
*
* We check that RegExp(R, undefined) returns R  -
*/
test_15_10_3_1__2 : function() {
//var BUGNUMBER = '61266';
var summary = 'Passing (RegExp object,flag) to RegExp() function';
var statprefix = 'RegExp(new RegExp(';
var comma =  ', '; var singlequote = "'"; var closeparens = '))';
var cnSUCCESS = 'RegExp() returned the supplied RegExp object';
var cnFAILURE =  'RegExp() did NOT return the supplied RegExp object';
var i = -1; var j = -1; var s = ''; var f = '';
var obj = {};
var status = ''; var actual = ''; var expect = '';
var patterns = new Array();
var flags = new Array();


// various regular expressions to try -
patterns[0] = '';
patterns[1] = 'abc';
patterns[2] = '(.*)(3-1)\s\w';
patterns[3] = '(.*)(...)\\s\\w';
patterns[4] = '[^A-Za-z0-9_]';
patterns[5] = '[^\f\n\r\t\v](123.5)([4 - 8]$)';

// various flags to try -
flags[0] = 'i';
flags[1] = 'g';
flags[2] = 'm';
flags[3] = undefined;



//-------------------------------------------------------------------------------------------------
//test();
//-------------------------------------------------------------------------------------------------


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

for (i in patterns)
{
s = patterns[i];

for (j in flags)
{
f = flags[j];
status = getStatus(s, f);
obj = new RegExp(s, f);

actual = (obj == RegExp(obj, undefined))? cnSUCCESS : cnFAILURE ;
expect = cnSUCCESS;
this.reportCompare (expect, actual, status);
}
}

//exitFunc ('test');
//}


function getStatus(regexp, flag)
{
return (statprefix  +  quote(regexp) +  comma  +   flag  +  closeparens);
}


function quote(text)
{
return (singlequote  +  text  + singlequote);
}

},

/**
*  File Name:          15_10_4_1__1.js
* Date: 26 November 2000
*
*
*SUMMARY: Passing a RegExp object to a RegExp() constructor.
*This test arose from Bugzilla bug 61266. The ECMA3 section is:
*
*  15.10.4.1 new RegExp(pattern, flags)
*
*  If pattern is an object R whose [[Class]] property is "RegExp" and
*  flags is undefined, then let P be the pattern used to construct R
*  and let F be the flags used to construct R. If pattern is an object R
*  whose [[Class]] property is "RegExp" and flags is not undefined,
*  then throw a TypeError exception. Otherwise, let P be the empty string
*  if pattern is undefined and ToString(pattern) otherwise, and let F be
*  the empty string if flags is undefined and ToString(flags) otherwise.
*
*
*The current test will check the first scenario outlined above:
*
*   "pattern" is itself a RegExp object R
*   "flags"  is undefined
*
* We check that a new RegExp object obj2 defined from these parameters
* is morally the same as the original RegExp object obj1. Of course, they
* can't be equal as objects - so we check their enumerable properties...
*
* In this test, the initial RegExp object obj1 will not include a
* flag. The flags parameter for obj2 will be undefined in the sense
* of not being provided.
*/
test_15_10_4_1__1 : function() {
//var BUGNUMBER = '61266';
var summary = 'Passing a RegExp object to a RegExp() constructor';
var statprefix = 'Applying RegExp() twice to pattern ';
var statsuffix =  '; testing property ';
var singlequote = "'";
var i = -1; var s = '';
var obj1 = {}; var obj2 = {};
var status = ''; var actual = ''; var expect = ''; var msg = '';
var patterns = new Array();


// various regular expressions to try -
patterns[0] = '';
patterns[1] = 'abc';
patterns[2] = '(.*)(3-1)\s\w';
patterns[3] = '(.*)(...)\\s\\w';
patterns[4] = '[^A-Za-z0-9_]';
patterns[5] = '[^\f\n\r\t\v](123.5)([4 - 8]$)';



//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

for (i in patterns)
{
s = patterns[i];
status =getStatus(s);
obj1 = new RegExp(s);
obj2 = new RegExp(obj1);

this.reportCompare (obj1 + '', obj2 + '', status);
}

//exitFunc ('test');
//}


function getStatus(regexp)
{
return (statprefix  +  quote(regexp) +  statsuffix);
}


function quote(text)
{
return (singlequote  +  text  + singlequote);
}

},

/**
*  File Name:          15_10_4_1__2.js
* Date: 26 November 2000
*
*
*SUMMARY: Passing a RegExp object to a RegExp() constructor.
*This test arose from Bugzilla bug 61266. The ECMA3 section is:
*
*  15.10.4.1 new RegExp(pattern, flags)
*
*  If pattern is an object R whose [[Class]] property is "RegExp" and
*  flags is undefined, then let P be the pattern used to construct R
*  and let F be the flags used to construct R. If pattern is an object R
*  whose [[Class]] property is "RegExp" and flags is not undefined,
*  then throw a TypeError exception. Otherwise, let P be the empty string
*  if pattern is undefined and ToString(pattern) otherwise, and let F be
*  the empty string if flags is undefined and ToString(flags) otherwise.
*
*
*The current test will check the first scenario outlined above:
*
*   "pattern" is itself a RegExp object R
*   "flags"  is undefined
*
* We check that a new RegExp object obj2 defined from these parameters
* is morally the same as the original RegExp object obj1. Of course, they
* can't be equal as objects - so we check their enumerable properties...
*
* In this test, the initial RegExp object obj1 will not include a
* flag.  This test is identical to test 15.10.4.1-1.js, except that
* here we use this syntax:
*
*                  obj2 = new RegExp(obj1, undefined);
*
* instead of:
*
*                  obj2 = new RegExp(obj1);
*/
test_15_10_4_1__2 : function() {
//var BUGNUMBER = '61266';
var summary = 'Passing a RegExp object to a RegExp() constructor';
var statprefix = 'Applying RegExp() twice to pattern ';
var statsuffix =  '; testing property ';
var singlequote = "'";
var i = -1; var s = '';
var obj1 = {}; var obj2 = {};
var status = ''; var actual = ''; var expect = ''; var msg = '';
var patterns = new Array();


// various regular expressions to try -
patterns[0] = '';
patterns[1] = 'abc';
patterns[2] = '(.*)(3-1)\s\w';
patterns[3] = '(.*)(...)\\s\\w';
patterns[4] = '[^A-Za-z0-9_]';
patterns[5] = '[^\f\n\r\t\v](123.5)([4 - 8]$)';



//-------------------------------------------------------------------------------------------------
//test();
//-------------------------------------------------------------------------------------------------


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

for (i in patterns)
{
s = patterns[i];
status =getStatus(s);
obj1 = new RegExp(s);
obj2 = new RegExp(obj1, undefined);  // see introduction to bug

this.reportCompare (obj1 + '', obj2 + '', status);
}

//exitFunc ('test');
//}


function getStatus(regexp)
{
return (statprefix  +  quote(regexp) +  statsuffix);
}


function quote(text)
{
return (singlequote  +  text  + singlequote);
}

},

/**
*  File Name:          15_10_4_1__3.js
* Date: 26 November 2000
*
*
*SUMMARY: Passing a RegExp object to a RegExp() constructor.
*This test arose from Bugzilla bug 61266. The ECMA3 section is:
*
*  15.10.4.1 new RegExp(pattern, flags)
*
*  If pattern is an object R whose [[Class]] property is "RegExp" and
*  flags is undefined, then let P be the pattern used to construct R
*  and let F be the flags used to construct R. If pattern is an object R
*  whose [[Class]] property is "RegExp" and flags is not undefined,
*  then throw a TypeError exception. Otherwise, let P be the empty string
*  if pattern is undefined and ToString(pattern) otherwise, and let F be
*  the empty string if flags is undefined and ToString(flags) otherwise.
*
*
*The current test will check the first scenario outlined above:
*
*   "pattern" is itself a RegExp object R
*   "flags"  is undefined
*
* We check that a new RegExp object obj2 defined from these parameters
* is morally the same as the original RegExp object obj1. Of course, they
* can't be equal as objects - so we check their enumerable properties...
*
* In this test, the initial RegExp obj1 will include a flag. The flags
* parameter for obj2  will be undefined in the sense of not being provided.
*/
test_15_10_4_1__3 : function() {
//var BUGNUMBER = '61266';
var summary = 'Passing a RegExp object to a RegExp() constructor';
var statprefix = 'Applying RegExp() twice to pattern ';
var statmiddle = ' and flag ';
var statsuffix =  '; testing property ';
var singlequote = "'";
var i = -1; var j = -1; var s = '';
var obj1 = {}; var obj2 = {};
var status = ''; var actual = ''; var expect = ''; var msg = '';
var patterns = new Array();
var flags = new Array();


// various regular expressions to try -
patterns[0] = '';
patterns[1] = 'abc';
patterns[2] = '(.*)(3-1)\s\w';
patterns[3] = '(.*)(...)\\s\\w';
patterns[4] = '[^A-Za-z0-9_]';
patterns[5] = '[^\f\n\r\t\v](123.5)([4 - 8]$)';

// various flags to try -
flags[0] = 'i';
flags[1] = 'g';
flags[2] = 'm';
flags[3] = undefined;



//-------------------------------------------------------------------------------------------------
//test();
//-------------------------------------------------------------------------------------------------


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

for (i in patterns)
{
s = patterns[i];

for (j in flags)
{
f = flags[j];
status = getStatus(s, f);
obj1 = new RegExp(s, f);
obj2 = new RegExp(obj1);

this.reportCompare (obj1 + '', obj2 + '', status);
}
}

//exitFunc ('test');
//}


function getStatus(regexp, flag)
{
return (statprefix  +  quote(regexp) +  statmiddle  +  flag  +  statsuffix);
}


function quote(text)
{
return (singlequote  +  text  + singlequote);
}

},

/**
*  File Name:          15_10_4_1__4.js
* Date: 26 November 2000
*
*
*SUMMARY: Passing a RegExp object to a RegExp() constructor.
*This test arose from Bugzilla bug 61266. The ECMA3 section is:
*
*  15.10.4.1 new RegExp(pattern, flags)
*
*  If pattern is an object R whose [[Class]] property is "RegExp" and
*  flags is undefined, then let P be the pattern used to construct R
*  and let F be the flags used to construct R. If pattern is an object R
*  whose [[Class]] property is "RegExp" and flags is not undefined,
*  then throw a TypeError exception. Otherwise, let P be the empty string
*  if pattern is undefined and ToString(pattern) otherwise, and let F be
*  the empty string if flags is undefined and ToString(flags) otherwise.
*
*
*The current test will check the first scenario outlined above:
*
*   "pattern" is itself a RegExp object R
*   "flags"  is undefined
*
* We check that a new RegExp object obj2 defined from these parameters
* is morally the same as the original RegExp object obj1. Of course, they
* can't be equal as objects - so we check their enumerable properties...
*
* In this test, the initial RegExp object obj1 will include a
* flag. This test is identical to test 15.10.4.1-3.js, except that
* here we use this syntax:
*
*                  obj2 = new RegExp(obj1, undefined);
*
* instead of:
*
*                  obj2 = new RegExp(obj1);
*/
test_15_10_4_1__4 : function() {
//var BUGNUMBER = '61266';
var summary = 'Passing a RegExp object to a RegExp() constructor';
var statprefix = 'Applying RegExp() twice to pattern ';
var statmiddle = ' and flag ';
var statsuffix =  '; testing property ';
var singlequote = "'";
var i = -1; var j = -1; var s = '';
var obj1 = {}; var obj2 = {};
var status = ''; var actual = ''; var expect = ''; var msg = '';
var patterns = new Array();
var flags = new Array();


// various regular expressions to try -
patterns[0] = '';
patterns[1] = 'abc';
patterns[2] = '(.*)(3-1)\s\w';
patterns[3] = '(.*)(...)\\s\\w';
patterns[4] = '[^A-Za-z0-9_]';
patterns[5] = '[^\f\n\r\t\v](123.5)([4 - 8]$)';

// various flags to try -
flags[0] = 'i';
flags[1] = 'g';
flags[2] = 'm';
flags[3] = undefined;



//-------------------------------------------------------------------------------------------------
//test();
//-------------------------------------------------------------------------------------------------


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
// printStatus (summary);

for (i in patterns)
{
s = patterns[i];

for (j in flags)
{
f = flags[j];
status = getStatus(s, f);
obj1 = new RegExp(s, f);
obj2 = new RegExp(obj1, undefined);  // see introduction to bug

this.reportCompare (obj1 + '', obj2 + '', status);
}
}

//exitFunc ('test');
//}


function getStatus(regexp, flag)
{
return (statprefix  +  quote(regexp) +  statmiddle  +  flag  +  statsuffix);
}


function quote(text)
{
return (singlequote  +  text  + singlequote);
}

},

/**
*  File Name:          15_10_4_1__5__n.js
* Date: 26 November 2000
*
*
*SUMMARY: Passing a RegExp object to a RegExp() constructor.
*This test arose from Bugzilla bug 61266. The ECMA3 section is:
*
*  15.10.4.1 new RegExp(pattern, flags)
*
*  If pattern is an object R whose [[Class]] property is "RegExp" and
*  flags is undefined, then let P be the pattern used to construct R
*  and let F be the flags used to construct R. If pattern is an object R
*  whose [[Class]] property is "RegExp" and flags is not undefined,
*  then throw a TypeError exception. Otherwise, let P be the empty string
*  if pattern is undefined and ToString(pattern) otherwise, and let F be
*  the empty string if flags is undefined and ToString(flags) otherwise.
*
*
*The current test will check the second scenario outlined above:
*
*   "pattern" is itself a RegExp object R
*   "flags" is NOT undefined
*
* This should throw an exception ... we test for this.
*/
test_15_10_4_1__5__n : function() {
//var BUGNUMBER = '61266';
var summary = 'Negative test: Passing (RegExp object, flag) to RegExp() constructor';
var statprefix = 'Passing RegExp object on pattern ';
var statsuffix =  '; passing flag ';
var cnFAILURE = 'Expected an exception to be thrown, but none was -';
var singlequote = "'";
var i = -1; var j = -1; var s = ''; var f = '';
var obj1 = {}; var obj2 = {};
var patterns = new Array();
var flags = new Array();


// various regular expressions to try -
patterns[0] = '';
patterns[1] = 'abc';
patterns[2] = '(.*)(3-1)\s\w';
patterns[3] = '(.*)(...)\\s\\w';
patterns[4] = '[^A-Za-z0-9_]';
patterns[5] = '[^\f\n\r\t\v](123.5)([4 - 8]$)';

// various flags to try -
flags[0] = 'i';
flags[1] = 'g';
flags[2] = 'm';


DESCRIPTION = "Negative test: Passing (RegExp object, flag) to RegExp() constructor"
EXPECTED = "error";


//-------------------------------------------------------------------------------------------------
//test();
//-------------------------------------------------------------------------------------------------


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

for (i in patterns)
{
s = patterns[i];

for (j in flags)
{
f = flags[j];
//printStatus(getStatus(s, f));
obj1 = new RegExp(s, f);
try {
obj2 = new RegExp(obj1, f);   // this should cause an exception
// WE SHOULD NEVER REACH THIS POINT -
this.reportCompare('PASS', 'FAIL', cnFAILURE);
} catch (e) {
// TODO: handle exception
}

}
}

//exitFunc ('test');
//}


function getStatus(regexp, flag)
{
return (statprefix  +  quote(regexp) +  statsuffix  +   flag);
}


function quote(text)
{
return (singlequote  +  text  + singlequote);
}

},

/**
*  File Name:          15_10_6_2__1.js
* Date: 23 October 2001
*
* SUMMARY: Testing regexps with the global flag set.
* NOT every substring fitting the given pattern will be matched.
* The parent string is CONSUMED as successive matches are found.
*
* From the ECMA-262 Final spec:
*
* 15.10.6.2 RegExp.prototype.exec(string)
* Performs a regular expression match of string against the regular
* expression and returns an Array object containing the results of
* the match, or null if the string did not match.
*
* The string ToString(string) is searched for an occurrence of the
* regular expression pattern as follows:
*
* 1.  Let S be the value of ToString(string).
* 2.  Let length be the length of S.
* 3.  Let lastIndex be the value of the lastIndex property.
* 4.  Let i be the value of ToInteger(lastIndex).
* 5.  If the global property is false, let i = 0.
* 6.  If i < 0 or i > length then set lastIndex to 0 and return null.
* 7.  Call [[Match]], giving it the arguments S and i.
*     If [[Match]] returned failure, go to step 8;
*     otherwise let r be its State result and go to step 10.
* 8.  Let i = i+1.
* 9.  Go to step 6.
* 10. Let e be r's endIndex value.
* 11. If the global property is true, set lastIndex to e.
*
*          etc.
*
*
* So when the global flag is set, |lastIndex| is incremented every time
* there is a match; not from i to i+1, but from i to "endIndex" e:
*
* e = (index of last input character matched so far by the pattern) + 1
*
* Thus in the example below, the first endIndex e occurs after the
* first match 'a b'. The next match will begin AFTER this, and so
* will NOT be 'b c', but rather 'c d'. Similarly, 'd e' won't be matched.
*/
test_15_10_6_2__1 : function() {
var i = 0;
//var BUGNUMBER = '(none)';
var summary = 'Testing regexps with the global flag set';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();


status = this.inSection(1);
string = 'a b c d e';
pattern = /\w\s\w/g;
actualmatch = string.match(pattern);
expectedmatch = ['a b','c d']; // see above explanation -
addThis();


status = this.inSection(2);
string = '12345678';
pattern = /\d\d\d/g;
actualmatch = string.match(pattern);
expectedmatch = ['123','456'];
addThis();



//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------



function addThis()
{
statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);
this.testRegExp(statusmessages, patterns, strings, actualmatches, expectedmatches);
//exitFunc ('test');
//}

},

/**
*  File Name:          15_10_6_2__2.js
* Date:    18 Feb 2002
* SUMMARY: Testing re.exec(str) when re.lastIndex is < 0 or > str.length
*
* Case 1: If re has the global flag set, then re(str) should be null
* Case 2: If re doesn't have this set, then re(str) should be unaffected
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=76717
*
*
* From the ECMA-262 Final spec:
*
* 15.10.6.2 RegExp.prototype.exec(string)
* Performs a regular expression match of string against the regular
* expression and returns an Array object containing the results of
* the match, or null if the string did not match.
*
* The string ToString(string) is searched for an occurrence of the
* regular expression pattern as follows:
*
* 1.  Let S be the value of ToString(string).
* 2.  Let length be the length of S.
* 3.  Let lastIndex be the value of the lastIndex property.
* 4.  Let i be the value of ToInteger(lastIndex).
* 5.  If the global property is false, let i = 0.
* 6.  If i < 0 or i > length then set lastIndex to 0 and return null.
* 7.  Call [[Match]], giving it the arguments S and i.
*     If [[Match]] returned failure, go to step 8;
*     otherwise let r be its State result and go to step 10.
* 8.  Let i = i+1.
* 9.  Go to step 6.
* 10. Let e be r's endIndex value.
* 11. If the global property is true, set lastIndex to e.
*
*          etc.
*
*
* So:
*
* A. If the global flag is not set, |lastIndex| is set to 0
*    before the match is attempted; thus the match is unaffected.
*
* B. If the global flag IS set and re.lastIndex is >= 0 and <= str.length,
*    |lastIndex| is incremented every time there is a match; not from
*    i to i+1, but from i to "endIndex" e:
*
*      e = (index of last input character matched so far by the pattern) + 1
*
*    The match is then attempted from this position in the string (Step 7).
*
* C. When the global flag IS set and re.lastIndex is < 0 or > str.length,
*    |lastIndex| is set to 0 and the match returns null.
*
*
* Note the |lastIndex| property is writeable, and may be set arbitrarily
* by the programmer - and we will do that below.
*/
test_15_10_6_2__2 : function() {
var i = 0;
//var BUGNUMBER = 76717;
var summary = 'Testing re.exec(str) when re.lastIndex is < 0 or > str.length';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();


/******************************************************************************
*
* Case 1 : when the global flag is set -
*
*****************************************************************************/
pattern = /abc/gi;
string = 'AbcaBcabC';

status = this.inSection(1);
actualmatch = pattern.exec(string);
expectedmatch = Array('Abc');
addThis();

status = this.inSection(2);
actualmatch = pattern.exec(string);
expectedmatch = Array('aBc');
addThis();

status = this.inSection(3);
actualmatch = pattern.exec(string);
expectedmatch = Array('abC');
addThis();

/*
* At this point |lastIndex| is > string.length, so the match should be null -
*/
status = this.inSection(4);
actualmatch = pattern.exec(string);
expectedmatch = null;
addThis();

/*
* Now let's set |lastIndex| to -1, so the match should again be null -
*/
status = this.inSection(5);
pattern.lastIndex = -1;
actualmatch = pattern.exec(string);
expectedmatch = null;
addThis();

/*
* Now try some edge-case values. Thanks to the work done in
* http://bugzilla.mozilla.org/show_bug.cgi?id=124339, |lastIndex|
* is now stored as a double instead of a uint32 (unsigned integer).
*
* Note 2^32 -1 is the upper bound for uint32's, but doubles can go
* all the way up to Number.MAX_VALUE. So that's why we need cases
* between those two numbers.
*/
status = this.inSection(6);
pattern.lastIndex = Math.pow(2,32);
actualmatch = pattern.exec(string);
expectedmatch = null;
addThis();

status = this.inSection(7);
pattern.lastIndex = -Math.pow(2,32);
actualmatch = pattern.exec(string);
expectedmatch = null;
addThis();

status = this.inSection(8);
pattern.lastIndex = Math.pow(2,32) + 1;
actualmatch = pattern.exec(string);
expectedmatch = null;
addThis();

status = this.inSection(9);
pattern.lastIndex = -(Math.pow(2,32) + 1);
actualmatch = pattern.exec(string);
expectedmatch = null;
addThis();

status = this.inSection(10);
pattern.lastIndex = Math.pow(2,32) * 2;
actualmatch = pattern.exec(string);
expectedmatch = null;
addThis();

status = this.inSection(11);
pattern.lastIndex = -Math.pow(2,32) * 2;
actualmatch = pattern.exec(string);
expectedmatch = null;
addThis();

status = this.inSection(12);
pattern.lastIndex = Math.pow(2,40);
actualmatch = pattern.exec(string);
expectedmatch = null;
addThis();

status = this.inSection(13);
pattern.lastIndex = -Math.pow(2,40);
actualmatch = pattern.exec(string);
expectedmatch = null;
addThis();

status = this.inSection(14);
pattern.lastIndex = Number.MAX_VALUE;
actualmatch = pattern.exec(string);
expectedmatch = null;
addThis();

status = this.inSection(15);
pattern.lastIndex = -Number.MAX_VALUE;
actualmatch = pattern.exec(string);
expectedmatch = null;
addThis();



/******************************************************************************
*
* Case 2: repeat all the above cases WITHOUT the global flag set.
* According to EMCA. |lastIndex| should get set to 0 before the match.
*
* Therefore re.exec(str) should be unaffected; thus our expected values
* below are now DIFFERENT when |lastIndex| is < 0 or > str.length
*
*****************************************************************************/

pattern = /abc/i;
string = 'AbcaBcabC';

status = this.inSection(16);
actualmatch = pattern.exec(string);
expectedmatch = Array('Abc');
addThis();

status = this.inSection(17);
actualmatch = pattern.exec(string);
expectedmatch = Array('Abc'); // NOT Array('aBc') as before -
addThis();

status = this.inSection(18);
actualmatch = pattern.exec(string);
expectedmatch = Array('Abc'); // NOT Array('abC') as before -
addThis();

/*
* At this point above, |lastIndex| WAS > string.length, but not here -
*/
status = this.inSection(19);
actualmatch = pattern.exec(string);
expectedmatch = Array('Abc') // NOT null as before -
addThis();

/*
* Now let's set |lastIndex| to -1
*/
status = this.inSection(20);
pattern.lastIndex = -1;
actualmatch = pattern.exec(string);
expectedmatch = Array('Abc') // NOT null as before -
addThis();

/*
* Now try some edge-case values. Thanks to the work done in
* http://bugzilla.mozilla.org/show_bug.cgi?id=124339, |lastIndex|
* is now stored as a double instead of a uint32 (unsigned integer).
*
* Note 2^32 -1 is the upper bound for uint32's, but doubles can go
* all the way up to Number.MAX_VALUE. So that's why we need cases
* between those two numbers.
*/
status = this.inSection(21);
pattern.lastIndex = Math.pow(2,32);
actualmatch = pattern.exec(string);
expectedmatch = Array('Abc') // NOT null as before -
addThis();

status = this.inSection(22);
pattern.lastIndex = -Math.pow(2,32);
actualmatch = pattern.exec(string);
expectedmatch = Array('Abc') // NOT null as before -
addThis();

status = this.inSection(23);
pattern.lastIndex = Math.pow(2,32) + 1;
actualmatch = pattern.exec(string);
expectedmatch = Array('Abc') // NOT null as before -
addThis();

status = this.inSection(24);
pattern.lastIndex = -(Math.pow(2,32) + 1);
actualmatch = pattern.exec(string);
expectedmatch = Array('Abc') // NOT null as before -
addThis();

status = this.inSection(25);
pattern.lastIndex = Math.pow(2,32) * 2;
actualmatch = pattern.exec(string);
expectedmatch = Array('Abc') // NOT null as before -
addThis();

status = this.inSection(26);
pattern.lastIndex = -Math.pow(2,32) * 2;
actualmatch = pattern.exec(string);
expectedmatch = Array('Abc') // NOT null as before -
addThis();

status = this.inSection(27);
pattern.lastIndex = Math.pow(2,40);
actualmatch = pattern.exec(string);
expectedmatch = Array('Abc') // NOT null as before -;
addThis();

status = this.inSection(28);
pattern.lastIndex = -Math.pow(2,40);
actualmatch = pattern.exec(string);
expectedmatch = Array('Abc') // NOT null as before -
addThis();

status = this.inSection(29);
pattern.lastIndex = Number.MAX_VALUE;
actualmatch = pattern.exec(string);
expectedmatch = Array('Abc') // NOT null as before -
addThis();

status = this.inSection(30);
pattern.lastIndex = -Number.MAX_VALUE;
actualmatch = pattern.exec(string);
expectedmatch = Array('Abc') // NOT null as before -
addThis();




//-------------------------------------------------------------------------------------------------
//test();
//-------------------------------------------------------------------------------------------------



function addThis()
{
statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);
this.testRegExp(statusmessages, patterns, strings, actualmatches, expectedmatches);
//exitFunc ('test');
//}

},

/**
*  File Name:          octal__001.js
* Date:    18 July 2002
* SUMMARY: Testing octal sequences in regexps
* See http://bugzilla.mozilla.org/show_bug.cgi?id=141078
*/
test_octal__001 : function() {
var i = 0;
//var BUGNUMBER = 141078;
var summary = 'Testing octal sequences in regexps';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();


status = this.inSection(1);
pattern = /\240/;
string = 'abc';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

/*
* In the following sections, we test the octal escape sequence '\052'.
* This is character code 42, representing the asterisk character '*'.
* The Unicode escape for it would be '\u002A', the hex escape '\x2A'.
*/
status = this.inSection(2);
pattern = /ab\052c/;
string = 'ab*c';
actualmatch = string.match(pattern);
expectedmatch = Array('ab*c');
addThis();

status = this.inSection(3);
pattern = /ab\052*c/;
string = 'abc';
actualmatch = string.match(pattern);
expectedmatch = Array('abc');
addThis();

status = this.inSection(4);
pattern = /ab(\052)+c/;
string = 'ab****c';
actualmatch = string.match(pattern);
expectedmatch = Array('ab****c', '*');
addThis();

status = this.inSection(5);
pattern = /ab((\052)+)c/;
string = 'ab****c';
actualmatch = string.match(pattern);
expectedmatch = Array('ab****c', '****', '*');
addThis();

status = this.inSection(6);
pattern = /(?:\052)c/;
string = 'ab****c';
actualmatch = string.match(pattern);
expectedmatch = Array('*c');
addThis();



//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------



function addThis()
{
statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


//function test()
//{
//enterFunc('test');
// printBugNumber(BUGNUMBER);
//printStatus(summary);
this.testRegExp(statusmessages, patterns, strings, actualmatches, expectedmatches);
//exitFunc ('test');
//}

},

/**
*  File Name:          octal__002.js
* Date:    31 July 2002
* SUMMARY: Testing regexps containing octal escape sequences
* This is an elaboration of mozilla/js/tests/ecma_2/RegExp/octal-003.js
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=141078
* for a reference on octal escape sequences in regexps.
*
* NOTE:
* We will use the identities '\011' === '\u0009' === '\x09' === '\t'
*
* The first is an octal escape sequence (\(0-3)OO; O an octal digit).
* See ECMA-262 Edition 2, Section 7.7.4 "String Literals". These were
* dropped in Edition 3 but we support them for backward compatibility.
*
* The second is a Unicode escape sequence (\uHHHH; H a hex digit).
* Since octal 11 = hex 9, the two escapes define the same character.
*
* The third is a hex escape sequence (\xHH; H a hex digit).
* Since hex 09 = hex 0009, this defines the same character.
*
* The fourth is the familiar escape sequence for a horizontal tab,
* defined in the ECMA spec as having Unicode value \u0009.
*/
test_octal__002 : function() {
var i = 0;
//var BUGNUMBER = 141078;
var summary = 'Testing regexps containing octal escape sequences';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();


/*
* Test a string containing the null character '\0' followed by the string '11'
*
*               'a' + String.fromCharCode(0) + '11';
*
* Note we can't simply write 'a\011', because '\011' would be interpreted
* as the octal escape sequence for the tab character (see above).
*
* We should get no match from the regexp /.\011/, because it should be
* looking for the octal escape sequence \011, i.e. the tab character -
*
*/
status = this.inSection(1);
pattern = /.\011/;
string = 'a' + String.fromCharCode(0) + '11';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();


/*
* Try same thing with 'xx' in place of '11'.
*
* Should get a match now, because the octal escape sequence in the regexp
* has been reduced from \011 to \0, and '\0' is present in the string -
*/
status = this.inSection(2);
pattern = /.\0xx/;
string = 'a' + String.fromCharCode(0) + 'xx';
actualmatch = string.match(pattern);
expectedmatch = Array(string);
addThis();


/*
* Same thing; don't use |String.fromCharCode(0)| this time.
* There is no ambiguity in '\0xx': it is the null character
* followed by two x's, no other interpretation is possible.
*/
status = this.inSection(3);
pattern = /.\0xx/;
string = 'a\0xx';
actualmatch = string.match(pattern);
expectedmatch = Array(string);
addThis();


/*
* This one should produce a match. The two-character string
* 'a' + '\011' is duplicated in the pattern and test string:
*/
status = this.inSection(4);
pattern = /.\011/;
string = 'a\011';
actualmatch = string.match(pattern);
expectedmatch = Array(string);
addThis();


/*
* Same as above, only now, for the second character of the string,
* use the Unicode escape '\u0009' instead of the octal escape '\011'
*/
status = this.inSection(5);
pattern = /.\011/;
string = 'a\u0009';
actualmatch = string.match(pattern);
expectedmatch = Array(string);
addThis();


/*
* Same as above, only now  for the second character of the string,
* use the hex escape '\x09' instead of the octal escape '\011'
*/
status = this.inSection(6);
pattern = /.\011/;
string = 'a\x09';
actualmatch = string.match(pattern);
expectedmatch = Array(string);
addThis();


/*
* Same as above, only now  for the second character of the string,
* use the escape '\t' instead of the octal escape '\011'
*/
status = this.inSection(7);
pattern = /.\011/;
string = 'a\t';
actualmatch = string.match(pattern);
expectedmatch = Array(string);
addThis();


/*
* Return to the string from Section 1.
*
* Unlike Section 1, use the RegExp() function to create the
* regexp pattern: null character followed by the string '11'.
*
* Since this is exactly what the string is, we should get a match -
*/
status = this.inSection(8);
string = 'a' + String.fromCharCode(0) + '11';
pattern = RegExp(string);
actualmatch = string.match(pattern);
expectedmatch = Array(string);
addThis();




//-------------------------------------------------------------------------------------------------
//test();
//-------------------------------------------------------------------------------------------------



function addThis()
{
statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);
this.testRegExp(statusmessages, patterns, strings, actualmatches, expectedmatches);
// exitFunc ('test');
//}

},

/**
*  File Name:          perlstress__001.js
* Date:    2002-07-07
* SUMMARY: Testing JS RegExp engine against Perl 5 RegExp engine.
* Adjust cnLBOUND, cnUBOUND below to restrict which sections are tested.
*
* This test was created by running various patterns and strings through the
* Perl 5 RegExp engine. We saved the results below to test the JS engine.
*
* NOTE: ECMA/JS and Perl do differ on certain points. We have either commented
* out such sections altogether, or modified them to fit what we expect from JS.
*
* EXAMPLES:
*
* - In JS, regexp captures (/(a) etc./) must hold |undefined| if not used.
*   See http://bugzilla.mozilla.org/show_bug.cgi?id=123437.
*   By contrast, in Perl, unmatched captures hold the empty string.
*   We have modified such sections accordingly. Example:

pattern = /^([^a-z])|(\^)$/;
string = '.';
actualmatch = string.match(pattern);
//expectedmatch = Array('.', '.', '');        <<<--- Perl
expectedmatch = Array('.', '.', undefined); <<<--- JS
addThis();


* - In JS, you can't refer to a capture before it's encountered & completed
*
* - Perl supports ] & ^] inside a [], ECMA does not
*
* - ECMA does support (?: (?= and (?! operators, but doesn't support (?<  etc.
*
* - ECMA doesn't support (?imsx or (?-imsx
*
* - ECMA doesn't support (?(condition)
*
* - Perl has \Z has end-of-line, ECMA doesn't
*
* - In ECMA, ^ matches only the empty string before the first character
*
* - In ECMA, $ matches only the empty string at end of input (unless multiline)
*
* - ECMA spec says that each atom in a range must be a single character
*
* - ECMA doesn't support \A
*
* - ECMA doesn't have rules for [:
*/
test_perlstress__001 : function() {
var i = 0;
//var BUGNUMBER = 85721;
var summary = 'Testing regular expression edge cases';
var cnSingleSpace = ' ';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();
var cnLBOUND = 1;
var cnUBOUND = 1000;


status = this.inSection(1);
pattern = /abc/;
string = 'abc';
actualmatch = string.match(pattern);
expectedmatch = Array('abc');
addThis();

status = this.inSection(2);
pattern = /abc/;
string = 'xabcy';
actualmatch = string.match(pattern);
expectedmatch = Array('abc');
addThis();

status = this.inSection(3);
pattern = /abc/;
string = 'ababc';
actualmatch = string.match(pattern);
expectedmatch = Array('abc');
addThis();

status = this.inSection(4);
pattern = /ab*c/;
string = 'abc';
actualmatch = string.match(pattern);
expectedmatch = Array('abc');
addThis();

status = this.inSection(5);
pattern = /ab*bc/;
string = 'abc';
actualmatch = string.match(pattern);
expectedmatch = Array('abc');
addThis();

status = this.inSection(6);
pattern = /ab*bc/;
string = 'abbc';
actualmatch = string.match(pattern);
expectedmatch = Array('abbc');
addThis();

status = this.inSection(7);
pattern = /ab*bc/;
string = 'abbbbc';
actualmatch = string.match(pattern);
expectedmatch = Array('abbbbc');
addThis();

status = this.inSection(8);
pattern = /.{1}/;
string = 'abbbbc';
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();

status = this.inSection(9);
pattern = /.{3,4}/;
string = 'abbbbc';
actualmatch = string.match(pattern);
expectedmatch = Array('abbb');
addThis();

status = this.inSection(10);
pattern = /ab{0,}bc/;
string = 'abbbbc';
actualmatch = string.match(pattern);
expectedmatch = Array('abbbbc');
addThis();

status = this.inSection(11);
pattern = /ab+bc/;
string = 'abbc';
actualmatch = string.match(pattern);
expectedmatch = Array('abbc');
addThis();

status = this.inSection(12);
pattern = /ab+bc/;
string = 'abbbbc';
actualmatch = string.match(pattern);
expectedmatch = Array('abbbbc');
addThis();

status = this.inSection(13);
pattern = /ab{1,}bc/;
string = 'abbbbc';
actualmatch = string.match(pattern);
expectedmatch = Array('abbbbc');
addThis();

status = this.inSection(14);
pattern = /ab{1,3}bc/;
string = 'abbbbc';
actualmatch = string.match(pattern);
expectedmatch = Array('abbbbc');
addThis();

status = this.inSection(15);
pattern = /ab{3,4}bc/;
string = 'abbbbc';
actualmatch = string.match(pattern);
expectedmatch = Array('abbbbc');
addThis();

status = this.inSection(16);
pattern = /ab?bc/;
string = 'abbc';
actualmatch = string.match(pattern);
expectedmatch = Array('abbc');
addThis();

status = this.inSection(17);
pattern = /ab?bc/;
string = 'abc';
actualmatch = string.match(pattern);
expectedmatch = Array('abc');
addThis();

status = this.inSection(18);
pattern = /ab{0,1}bc/;
string = 'abc';
actualmatch = string.match(pattern);
expectedmatch = Array('abc');
addThis();

status = this.inSection(19);
pattern = /ab?c/;
string = 'abc';
actualmatch = string.match(pattern);
expectedmatch = Array('abc');
addThis();

status = this.inSection(20);
pattern = /ab{0,1}c/;
string = 'abc';
actualmatch = string.match(pattern);
expectedmatch = Array('abc');
addThis();

status = this.inSection(21);
pattern = /^abc$/;
string = 'abc';
actualmatch = string.match(pattern);
expectedmatch = Array('abc');
addThis();

status = this.inSection(22);
pattern = /^abc/;
string = 'abcc';
actualmatch = string.match(pattern);
expectedmatch = Array('abc');
addThis();

status = this.inSection(23);
pattern = /abc$/;
string = 'aabc';
actualmatch = string.match(pattern);
expectedmatch = Array('abc');
addThis();

status = this.inSection(24);
pattern = /^/;
string = 'abc';
actualmatch = string.match(pattern);
expectedmatch = Array('');
addThis();

status = this.inSection(25);
pattern = /$/;
string = 'abc';
actualmatch = string.match(pattern);
expectedmatch = Array('');
addThis();

status = this.inSection(26);
pattern = /a.c/;
string = 'abc';
actualmatch = string.match(pattern);
expectedmatch = Array('abc');
addThis();

status = this.inSection(27);
pattern = /a.c/;
string = 'axc';
actualmatch = string.match(pattern);
expectedmatch = Array('axc');
addThis();

status = this.inSection(28);
pattern = /a.*c/;
string = 'axyzc';
actualmatch = string.match(pattern);
expectedmatch = Array('axyzc');
addThis();

status = this.inSection(29);
pattern = /a[bc]d/;
string = 'abd';
actualmatch = string.match(pattern);
expectedmatch = Array('abd');
addThis();

status = this.inSection(30);
pattern = /a[b-d]e/;
string = 'ace';
actualmatch = string.match(pattern);
expectedmatch = Array('ace');
addThis();

status = this.inSection(31);
pattern = /a[b-d]/;
string = 'aac';
actualmatch = string.match(pattern);
expectedmatch = Array('ac');
addThis();

status = this.inSection(32);
pattern = /a[-b]/;
string = 'a-';
actualmatch = string.match(pattern);
expectedmatch = Array('a-');
addThis();

status = this.inSection(33);
pattern = /a[b-]/;
string = 'a-';
actualmatch = string.match(pattern);
expectedmatch = Array('a-');
addThis();

status = this.inSection(34);
pattern = /a]/;
string = 'a]';
actualmatch = string.match(pattern);
expectedmatch = Array('a]');
addThis();

/* Perl supports ] & ^] inside a [], ECMA does not
pattern = /a[]]b/;
status = this.inSection(35);
string = 'a]b';
actualmatch = string.match(pattern);
expectedmatch = Array('a]b');
addThis();
*/

status = this.inSection(36);
pattern = /a[^bc]d/;
string = 'aed';
actualmatch = string.match(pattern);
expectedmatch = Array('aed');
addThis();

status = this.inSection(37);
pattern = /a[^-b]c/;
string = 'adc';
actualmatch = string.match(pattern);
expectedmatch = Array('adc');
addThis();

/* Perl supports ] & ^] inside a [], ECMA does not
status = this.inSection(38);
pattern = /a[^]b]c/;
string = 'adc';
actualmatch = string.match(pattern);
expectedmatch = Array('adc');
addThis();
*/

status = this.inSection(39);
pattern = /\ba\b/;
string = 'a-';
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();

status = this.inSection(40);
pattern = /\ba\b/;
string = '-a';
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();

status = this.inSection(41);
pattern = /\ba\b/;
string = '-a-';
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();

status = this.inSection(42);
pattern = /\By\b/;
string = 'xy';
actualmatch = string.match(pattern);
expectedmatch = Array('y');
addThis();

status = this.inSection(43);
pattern = /\by\B/;
string = 'yz';
actualmatch = string.match(pattern);
expectedmatch = Array('y');
addThis();

status = this.inSection(44);
pattern = /\By\B/;
string = 'xyz';
actualmatch = string.match(pattern);
expectedmatch = Array('y');
addThis();

status = this.inSection(45);
pattern = /\w/;
string = 'a';
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();

status = this.inSection(46);
pattern = /\W/;
string = '-';
actualmatch = string.match(pattern);
expectedmatch = Array('-');
addThis();

status = this.inSection(47);
pattern = /a\Sb/;
string = 'a-b';
actualmatch = string.match(pattern);
expectedmatch = Array('a-b');
addThis();

status = this.inSection(48);
pattern = /\d/;
string = '1';
actualmatch = string.match(pattern);
expectedmatch = Array('1');
addThis();

status = this.inSection(49);
pattern = /\D/;
string = '-';
actualmatch = string.match(pattern);
expectedmatch = Array('-');
addThis();

status = this.inSection(50);
pattern = /[\w]/;
string = 'a';
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();

status = this.inSection(51);
pattern = /[\W]/;
string = '-';
actualmatch = string.match(pattern);
expectedmatch = Array('-');
addThis();

status = this.inSection(52);
pattern = /a[\S]b/;
string = 'a-b';
actualmatch = string.match(pattern);
expectedmatch = Array('a-b');
addThis();

status = this.inSection(53);
pattern = /[\d]/;
string = '1';
actualmatch = string.match(pattern);
expectedmatch = Array('1');
addThis();

status = this.inSection(54);
pattern = /[\D]/;
string = '-';
actualmatch = string.match(pattern);
expectedmatch = Array('-');
addThis();

status = this.inSection(55);
pattern = /ab|cd/;
string = 'abc';
actualmatch = string.match(pattern);
expectedmatch = Array('ab');
addThis();

status = this.inSection(56);
pattern = /ab|cd/;
string = 'abcd';
actualmatch = string.match(pattern);
expectedmatch = Array('ab');
addThis();

status = this.inSection(57);
pattern = /()ef/;
string = 'def';
actualmatch = string.match(pattern);
expectedmatch = Array('ef', '');
addThis();

status = this.inSection(58);
pattern = /a\(b/;
string = 'a(b';
actualmatch = string.match(pattern);
expectedmatch = Array('a(b');
addThis();

status = this.inSection(59);
pattern = /a\(*b/;
string = 'ab';
actualmatch = string.match(pattern);
expectedmatch = Array('ab');
addThis();

status = this.inSection(60);
pattern = /a\(*b/;
string = 'a((b';
actualmatch = string.match(pattern);
expectedmatch = Array('a((b');
addThis();

status = this.inSection(61);
pattern = /a\\b/;
string = 'a\\b';
actualmatch = string.match(pattern);
expectedmatch = Array('a\\b');
addThis();

status = this.inSection(62);
pattern = /((a))/;
string = 'abc';
actualmatch = string.match(pattern);
expectedmatch = Array('a', 'a', 'a');
addThis();

status = this.inSection(63);
pattern = /(a)b(c)/;
string = 'abc';
actualmatch = string.match(pattern);
expectedmatch = Array('abc', 'a', 'c');
addThis();

status = this.inSection(64);
pattern = /a+b+c/;
string = 'aabbabc';
actualmatch = string.match(pattern);
expectedmatch = Array('abc');
addThis();

status = this.inSection(65);
pattern = /a{1,}b{1,}c/;
string = 'aabbabc';
actualmatch = string.match(pattern);
expectedmatch = Array('abc');
addThis();

status = this.inSection(66);
pattern = /a.+?c/;
string = 'abcabc';
actualmatch = string.match(pattern);
expectedmatch = Array('abc');
addThis();

status = this.inSection(67);
pattern = /(a+|b)*/;
string = 'ab';
actualmatch = string.match(pattern);
expectedmatch = Array('ab', 'b');
addThis();

status = this.inSection(68);
pattern = /(a+|b){0,}/;
string = 'ab';
actualmatch = string.match(pattern);
expectedmatch = Array('ab', 'b');
addThis();

status = this.inSection(69);
pattern = /(a+|b)+/;
string = 'ab';
actualmatch = string.match(pattern);
expectedmatch = Array('ab', 'b');
addThis();

status = this.inSection(70);
pattern = /(a+|b){1,}/;
string = 'ab';
actualmatch = string.match(pattern);
expectedmatch = Array('ab', 'b');
addThis();

status = this.inSection(71);
pattern = /(a+|b)?/;
string = 'ab';
actualmatch = string.match(pattern);
expectedmatch = Array('a', 'a');
addThis();

status = this.inSection(72);
pattern = /(a+|b){0,1}/;
string = 'ab';
actualmatch = string.match(pattern);
expectedmatch = Array('a', 'a');
addThis();

status = this.inSection(73);
pattern = /[^ab]*/;
string = 'cde';
actualmatch = string.match(pattern);
expectedmatch = Array('cde');
addThis();

status = this.inSection(74);
pattern = /([abc])*d/;
string = 'abbbcd';
actualmatch = string.match(pattern);
expectedmatch = Array('abbbcd', 'c');
addThis();

status = this.inSection(75);
pattern = /([abc])*bcd/;
string = 'abcd';
actualmatch = string.match(pattern);
expectedmatch = Array('abcd', 'a');
addThis();

status = this.inSection(76);
pattern = /a|b|c|d|e/;
string = 'e';
actualmatch = string.match(pattern);
expectedmatch = Array('e');
addThis();

status = this.inSection(77);
pattern = /(a|b|c|d|e)f/;
string = 'ef';
actualmatch = string.match(pattern);
expectedmatch = Array('ef', 'e');
addThis();

status = this.inSection(78);
pattern = /abcd*efg/;
string = 'abcdefg';
actualmatch = string.match(pattern);
expectedmatch = Array('abcdefg');
addThis();

status = this.inSection(79);
pattern = /ab*/;
string = 'xabyabbbz';
actualmatch = string.match(pattern);
expectedmatch = Array('ab');
addThis();

status = this.inSection(80);
pattern = /ab*/;
string = 'xayabbbz';
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();

status = this.inSection(81);
pattern = /(ab|cd)e/;
string = 'abcde';
actualmatch = string.match(pattern);
expectedmatch = Array('cde', 'cd');
addThis();

status = this.inSection(82);
pattern = /[abhgefdc]ij/;
string = 'hij';
actualmatch = string.match(pattern);
expectedmatch = Array('hij');
addThis();

status = this.inSection(83);
pattern = /(abc|)ef/;
string = 'abcdef';
actualmatch = string.match(pattern);
expectedmatch = Array('ef', '');
addThis();

status = this.inSection(84);
pattern = /(a|b)c*d/;
string = 'abcd';
actualmatch = string.match(pattern);
expectedmatch = Array('bcd', 'b');
addThis();

status = this.inSection(85);
pattern = /(ab|ab*)bc/;
string = 'abc';
actualmatch = string.match(pattern);
expectedmatch = Array('abc', 'a');
addThis();

status = this.inSection(86);
pattern = /a([bc]*)c*/;
string = 'abc';
actualmatch = string.match(pattern);
expectedmatch = Array('abc', 'bc');
addThis();

status = this.inSection(87);
pattern = /a([bc]*)(c*d)/;
string = 'abcd';
actualmatch = string.match(pattern);
expectedmatch = Array('abcd', 'bc', 'd');
addThis();

status = this.inSection(88);
pattern = /a([bc]+)(c*d)/;
string = 'abcd';
actualmatch = string.match(pattern);
expectedmatch = Array('abcd', 'bc', 'd');
addThis();

status = this.inSection(89);
pattern = /a([bc]*)(c+d)/;
string = 'abcd';
actualmatch = string.match(pattern);
expectedmatch = Array('abcd', 'b', 'cd');
addThis();

status = this.inSection(90);
pattern = /a[bcd]*dcdcde/;
string = 'adcdcde';
actualmatch = string.match(pattern);
expectedmatch = Array('adcdcde');
addThis();

status = this.inSection(91);
pattern = /(ab|a)b*c/;
string = 'abc';
actualmatch = string.match(pattern);
expectedmatch = Array('abc', 'ab');
addThis();

status = this.inSection(92);
pattern = /((a)(b)c)(d)/;
string = 'abcd';
actualmatch = string.match(pattern);
expectedmatch = Array('abcd', 'abc', 'a', 'b', 'd');
addThis();

status = this.inSection(93);
pattern = /[a-zA-Z_][a-zA-Z0-9_]*/;
string = 'alpha';
actualmatch = string.match(pattern);
expectedmatch = Array('alpha');
addThis();

status = this.inSection(94);
pattern = /^a(bc+|b[eh])g|.h$/;
string = 'abh';
actualmatch = string.match(pattern);
expectedmatch = Array('bh', undefined);
addThis();

status = this.inSection(95);
pattern = /(bc+d$|ef*g.|h?i(j|k))/;
string = 'effgz';
actualmatch = string.match(pattern);
expectedmatch = Array('effgz', 'effgz', undefined);
addThis();

status = this.inSection(96);
pattern = /(bc+d$|ef*g.|h?i(j|k))/;
string = 'ij';
actualmatch = string.match(pattern);
expectedmatch = Array('ij', 'ij', 'j');
addThis();

status = this.inSection(97);
pattern = /(bc+d$|ef*g.|h?i(j|k))/;
string = 'reffgz';
actualmatch = string.match(pattern);
expectedmatch = Array('effgz', 'effgz', undefined);
addThis();

status = this.inSection(98);
pattern = /((((((((((a))))))))))/;
string = 'a';
actualmatch = string.match(pattern);
expectedmatch = Array('a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a');
addThis();

status = this.inSection(99);
pattern = /((((((((((a))))))))))\10/;
string = 'aa';
actualmatch = string.match(pattern);
expectedmatch = Array('aa', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a');
addThis();

status = this.inSection(100);
pattern = /((((((((((a))))))))))/;
string = 'a!';
actualmatch = string.match(pattern);
expectedmatch = Array('a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a');
addThis();

status = this.inSection(101);
pattern = /(((((((((a)))))))))/;
string = 'a';
actualmatch = string.match(pattern);
expectedmatch = Array('a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a');
addThis();

status = this.inSection(102);
pattern = /(.*)c(.*)/;
string = 'abcde';
actualmatch = string.match(pattern);
expectedmatch = Array('abcde', 'ab', 'de');
addThis();

status = this.inSection(103);
pattern = /abcd/;
string = 'abcd';
actualmatch = string.match(pattern);
expectedmatch = Array('abcd');
addThis();

status = this.inSection(104);
pattern = /a(bc)d/;
string = 'abcd';
actualmatch = string.match(pattern);
expectedmatch = Array('abcd', 'bc');
addThis();

status = this.inSection(105);
pattern = /a[-]?c/;
string = 'ac';
actualmatch = string.match(pattern);
expectedmatch = Array('ac');
addThis();

status = this.inSection(106);
pattern = /(abc)\1/;
string = 'abcabc';
actualmatch = string.match(pattern);
expectedmatch = Array('abcabc', 'abc');
addThis();

status = this.inSection(107);
pattern = /([a-c]*)\1/;
string = 'abcabc';
actualmatch = string.match(pattern);
expectedmatch = Array('abcabc', 'abc');
addThis();

status = this.inSection(108);
pattern = /(a)|\1/;
string = 'a';
actualmatch = string.match(pattern);
expectedmatch = Array('a', 'a');
addThis();

status = this.inSection(109);
pattern = /(([a-c])b*?\2)*/;
string = 'ababbbcbc';
actualmatch = string.match(pattern);
expectedmatch = Array('ababb', 'bb', 'b');
addThis();

status = this.inSection(110);
pattern = /(([a-c])b*?\2){3}/;
string = 'ababbbcbc';
actualmatch = string.match(pattern);
expectedmatch = Array('ababbbcbc', 'cbc', 'c');
addThis();

/* Can't refer to a capture before it's encountered & completed
status = this.inSection(111);
pattern = /((\3|b)\2(a)x)+/;
string = 'aaaxabaxbaaxbbax';
actualmatch = string.match(pattern);
expectedmatch = Array('bbax', 'bbax', 'b', 'a');
addThis();

status = this.inSection(112);
pattern = /((\3|b)\2(a)){2,}/;
string = 'bbaababbabaaaaabbaaaabba';
actualmatch = string.match(pattern);
expectedmatch = Array('bbaaaabba', 'bba', 'b', 'a');
addThis();
*/

status = this.inSection(113);
pattern = /abc/i;
string = 'ABC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABC');
addThis();

status = this.inSection(114);
pattern = /abc/i;
string = 'XABCY';
actualmatch = string.match(pattern);
expectedmatch = Array('ABC');
addThis();

status = this.inSection(115);
pattern = /abc/i;
string = 'ABABC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABC');
addThis();

status = this.inSection(116);
pattern = /ab*c/i;
string = 'ABC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABC');
addThis();

status = this.inSection(117);
pattern = /ab*bc/i;
string = 'ABC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABC');
addThis();

status = this.inSection(118);
pattern = /ab*bc/i;
string = 'ABBC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABBC');
addThis();

status = this.inSection(119);
pattern = /ab*?bc/i;
string = 'ABBBBC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABBBBC');
addThis();

status = this.inSection(120);
pattern = /ab{0,}?bc/i;
string = 'ABBBBC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABBBBC');
addThis();

status = this.inSection(121);
pattern = /ab+?bc/i;
string = 'ABBC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABBC');
addThis();

status = this.inSection(122);
pattern = /ab+bc/i;
string = 'ABBBBC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABBBBC');
addThis();

status = this.inSection(123);
pattern = /ab{1,}?bc/i;
string = 'ABBBBC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABBBBC');
addThis();

status = this.inSection(124);
pattern = /ab{1,3}?bc/i;
string = 'ABBBBC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABBBBC');
addThis();

status = this.inSection(125);
pattern = /ab{3,4}?bc/i;
string = 'ABBBBC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABBBBC');
addThis();

status = this.inSection(126);
pattern = /ab??bc/i;
string = 'ABBC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABBC');
addThis();

status = this.inSection(127);
pattern = /ab??bc/i;
string = 'ABC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABC');
addThis();

status = this.inSection(128);
pattern = /ab{0,1}?bc/i;
string = 'ABC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABC');
addThis();

status = this.inSection(129);
pattern = /ab??c/i;
string = 'ABC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABC');
addThis();

status = this.inSection(130);
pattern = /ab{0,1}?c/i;
string = 'ABC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABC');
addThis();

status = this.inSection(131);
pattern = /^abc$/i;
string = 'ABC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABC');
addThis();

status = this.inSection(132);
pattern = /^abc/i;
string = 'ABCC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABC');
addThis();

status = this.inSection(133);
pattern = /abc$/i;
string = 'AABC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABC');
addThis();

status = this.inSection(134);
pattern = /^/i;
string = 'ABC';
actualmatch = string.match(pattern);
expectedmatch = Array('');
addThis();

status = this.inSection(135);
pattern = /$/i;
string = 'ABC';
actualmatch = string.match(pattern);
expectedmatch = Array('');
addThis();

status = this.inSection(136);
pattern = /a.c/i;
string = 'ABC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABC');
addThis();

status = this.inSection(137);
pattern = /a.c/i;
string = 'AXC';
actualmatch = string.match(pattern);
expectedmatch = Array('AXC');
addThis();

status = this.inSection(138);
pattern = /a.*?c/i;
string = 'AXYZC';
actualmatch = string.match(pattern);
expectedmatch = Array('AXYZC');
addThis();

status = this.inSection(139);
pattern = /a[bc]d/i;
string = 'ABD';
actualmatch = string.match(pattern);
expectedmatch = Array('ABD');
addThis();

status = this.inSection(140);
pattern = /a[b-d]e/i;
string = 'ACE';
actualmatch = string.match(pattern);
expectedmatch = Array('ACE');
addThis();

status = this.inSection(141);
pattern = /a[b-d]/i;
string = 'AAC';
actualmatch = string.match(pattern);
expectedmatch = Array('AC');
addThis();

status = this.inSection(142);
pattern = /a[-b]/i;
string = 'A-';
actualmatch = string.match(pattern);
expectedmatch = Array('A-');
addThis();

status = this.inSection(143);
pattern = /a[b-]/i;
string = 'A-';
actualmatch = string.match(pattern);
expectedmatch = Array('A-');
addThis();

status = this.inSection(144);
pattern = /a]/i;
string = 'A]';
actualmatch = string.match(pattern);
expectedmatch = Array('A]');
addThis();

/* Perl supports ] & ^] inside a [], ECMA does not
status = this.inSection(145);
pattern = /a[]]b/i;
string = 'A]B';
actualmatch = string.match(pattern);
expectedmatch = Array('A]B');
addThis();
*/

status = this.inSection(146);
pattern = /a[^bc]d/i;
string = 'AED';
actualmatch = string.match(pattern);
expectedmatch = Array('AED');
addThis();

status = this.inSection(147);
pattern = /a[^-b]c/i;
string = 'ADC';
actualmatch = string.match(pattern);
expectedmatch = Array('ADC');
addThis();

/* Perl supports ] & ^] inside a [], ECMA does not
status = this.inSection(148);
pattern = /a[^]b]c/i;
string = 'ADC';
actualmatch = string.match(pattern);
expectedmatch = Array('ADC');
addThis();
*/

status = this.inSection(149);
pattern = /ab|cd/i;
string = 'ABC';
actualmatch = string.match(pattern);
expectedmatch = Array('AB');
addThis();

status = this.inSection(150);
pattern = /ab|cd/i;
string = 'ABCD';
actualmatch = string.match(pattern);
expectedmatch = Array('AB');
addThis();

status = this.inSection(151);
pattern = /()ef/i;
string = 'DEF';
actualmatch = string.match(pattern);
expectedmatch = Array('EF', '');
addThis();

status = this.inSection(152);
pattern = /a\(b/i;
string = 'A(B';
actualmatch = string.match(pattern);
expectedmatch = Array('A(B');
addThis();

status = this.inSection(153);
pattern = /a\(*b/i;
string = 'AB';
actualmatch = string.match(pattern);
expectedmatch = Array('AB');
addThis();

status = this.inSection(154);
pattern = /a\(*b/i;
string = 'A((B';
actualmatch = string.match(pattern);
expectedmatch = Array('A((B');
addThis();

status = this.inSection(155);
pattern = /a\\b/i;
string = 'A\\B';
actualmatch = string.match(pattern);
expectedmatch = Array('A\\B');
addThis();

status = this.inSection(156);
pattern = /((a))/i;
string = 'ABC';
actualmatch = string.match(pattern);
expectedmatch = Array('A', 'A', 'A');
addThis();

status = this.inSection(157);
pattern = /(a)b(c)/i;
string = 'ABC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABC', 'A', 'C');
addThis();

status = this.inSection(158);
pattern = /a+b+c/i;
string = 'AABBABC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABC');
addThis();

status = this.inSection(159);
pattern = /a{1,}b{1,}c/i;
string = 'AABBABC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABC');
addThis();

status = this.inSection(160);
pattern = /a.+?c/i;
string = 'ABCABC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABC');
addThis();

status = this.inSection(161);
pattern = /a.*?c/i;
string = 'ABCABC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABC');
addThis();

status = this.inSection(162);
pattern = /a.{0,5}?c/i;
string = 'ABCABC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABC');
addThis();

status = this.inSection(163);
pattern = /(a+|b)*/i;
string = 'AB';
actualmatch = string.match(pattern);
expectedmatch = Array('AB', 'B');
addThis();

status = this.inSection(164);
pattern = /(a+|b){0,}/i;
string = 'AB';
actualmatch = string.match(pattern);
expectedmatch = Array('AB', 'B');
addThis();

status = this.inSection(165);
pattern = /(a+|b)+/i;
string = 'AB';
actualmatch = string.match(pattern);
expectedmatch = Array('AB', 'B');
addThis();

status = this.inSection(166);
pattern = /(a+|b){1,}/i;
string = 'AB';
actualmatch = string.match(pattern);
expectedmatch = Array('AB', 'B');
addThis();

status = this.inSection(167);
pattern = /(a+|b)?/i;
string = 'AB';
actualmatch = string.match(pattern);
expectedmatch = Array('A', 'A');
addThis();

status = this.inSection(168);
pattern = /(a+|b){0,1}/i;
string = 'AB';
actualmatch = string.match(pattern);
expectedmatch = Array('A', 'A');
addThis();

status = this.inSection(169);
pattern = /(a+|b){0,1}?/i;
string = 'AB';
actualmatch = string.match(pattern);
expectedmatch = Array('', undefined);
addThis();

status = this.inSection(170);
pattern = /[^ab]*/i;
string = 'CDE';
actualmatch = string.match(pattern);
expectedmatch = Array('CDE');
addThis();

status = this.inSection(171);
pattern = /([abc])*d/i;
string = 'ABBBCD';
actualmatch = string.match(pattern);
expectedmatch = Array('ABBBCD', 'C');
addThis();

status = this.inSection(172);
pattern = /([abc])*bcd/i;
string = 'ABCD';
actualmatch = string.match(pattern);
expectedmatch = Array('ABCD', 'A');
addThis();

status = this.inSection(173);
pattern = /a|b|c|d|e/i;
string = 'E';
actualmatch = string.match(pattern);
expectedmatch = Array('E');
addThis();

status = this.inSection(174);
pattern = /(a|b|c|d|e)f/i;
string = 'EF';
actualmatch = string.match(pattern);
expectedmatch = Array('EF', 'E');
addThis();

status = this.inSection(175);
pattern = /abcd*efg/i;
string = 'ABCDEFG';
actualmatch = string.match(pattern);
expectedmatch = Array('ABCDEFG');
addThis();

status = this.inSection(176);
pattern = /ab*/i;
string = 'XABYABBBZ';
actualmatch = string.match(pattern);
expectedmatch = Array('AB');
addThis();

status = this.inSection(177);
pattern = /ab*/i;
string = 'XAYABBBZ';
actualmatch = string.match(pattern);
expectedmatch = Array('A');
addThis();

status = this.inSection(178);
pattern = /(ab|cd)e/i;
string = 'ABCDE';
actualmatch = string.match(pattern);
expectedmatch = Array('CDE', 'CD');
addThis();

status = this.inSection(179);
pattern = /[abhgefdc]ij/i;
string = 'HIJ';
actualmatch = string.match(pattern);
expectedmatch = Array('HIJ');
addThis();

status = this.inSection(180);
pattern = /(abc|)ef/i;
string = 'ABCDEF';
actualmatch = string.match(pattern);
expectedmatch = Array('EF', '');
addThis();

status = this.inSection(181);
pattern = /(a|b)c*d/i;
string = 'ABCD';
actualmatch = string.match(pattern);
expectedmatch = Array('BCD', 'B');
addThis();

status = this.inSection(182);
pattern = /(ab|ab*)bc/i;
string = 'ABC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABC', 'A');
addThis();

status = this.inSection(183);
pattern = /a([bc]*)c*/i;
string = 'ABC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABC', 'BC');
addThis();

status = this.inSection(184);
pattern = /a([bc]*)(c*d)/i;
string = 'ABCD';
actualmatch = string.match(pattern);
expectedmatch = Array('ABCD', 'BC', 'D');
addThis();

status = this.inSection(185);
pattern = /a([bc]+)(c*d)/i;
string = 'ABCD';
actualmatch = string.match(pattern);
expectedmatch = Array('ABCD', 'BC', 'D');
addThis();

status = this.inSection(186);
pattern = /a([bc]*)(c+d)/i;
string = 'ABCD';
actualmatch = string.match(pattern);
expectedmatch = Array('ABCD', 'B', 'CD');
addThis();

status = this.inSection(187);
pattern = /a[bcd]*dcdcde/i;
string = 'ADCDCDE';
actualmatch = string.match(pattern);
expectedmatch = Array('ADCDCDE');
addThis();

status = this.inSection(188);
pattern = /(ab|a)b*c/i;
string = 'ABC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABC', 'AB');
addThis();

status = this.inSection(189);
pattern = /((a)(b)c)(d)/i;
string = 'ABCD';
actualmatch = string.match(pattern);
expectedmatch = Array('ABCD', 'ABC', 'A', 'B', 'D');
addThis();

status = this.inSection(190);
pattern = /[a-zA-Z_][a-zA-Z0-9_]*/i;
string = 'ALPHA';
actualmatch = string.match(pattern);
expectedmatch = Array('ALPHA');
addThis();

status = this.inSection(191);
pattern = /^a(bc+|b[eh])g|.h$/i;
string = 'ABH';
actualmatch = string.match(pattern);
expectedmatch = Array('BH', undefined);
addThis();

status = this.inSection(192);
pattern = /(bc+d$|ef*g.|h?i(j|k))/i;
string = 'EFFGZ';
actualmatch = string.match(pattern);
expectedmatch = Array('EFFGZ', 'EFFGZ', undefined);
addThis();

status = this.inSection(193);
pattern = /(bc+d$|ef*g.|h?i(j|k))/i;
string = 'IJ';
actualmatch = string.match(pattern);
expectedmatch = Array('IJ', 'IJ', 'J');
addThis();

status = this.inSection(194);
pattern = /(bc+d$|ef*g.|h?i(j|k))/i;
string = 'REFFGZ';
actualmatch = string.match(pattern);
expectedmatch = Array('EFFGZ', 'EFFGZ', undefined);
addThis();

status = this.inSection(195);
pattern = /((((((((((a))))))))))/i;
string = 'A';
actualmatch = string.match(pattern);
expectedmatch = Array('A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A');
addThis();

status = this.inSection(196);
pattern = /((((((((((a))))))))))\10/i;
string = 'AA';
actualmatch = string.match(pattern);
expectedmatch = Array('AA', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A');
addThis();

status = this.inSection(197);
pattern = /((((((((((a))))))))))/i;
string = 'A!';
actualmatch = string.match(pattern);
expectedmatch = Array('A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A');
addThis();

status = this.inSection(198);
pattern = /(((((((((a)))))))))/i;
string = 'A';
actualmatch = string.match(pattern);
expectedmatch = Array('A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A');
addThis();

status = this.inSection(199);
pattern = /(?:(?:(?:(?:(?:(?:(?:(?:(?:(a))))))))))/i;
string = 'A';
actualmatch = string.match(pattern);
expectedmatch = Array('A', 'A');
addThis();

status = this.inSection(200);
pattern = /(?:(?:(?:(?:(?:(?:(?:(?:(?:(a|b|c))))))))))/i;
string = 'C';
actualmatch = string.match(pattern);
expectedmatch = Array('C', 'C');
addThis();

status = this.inSection(201);
pattern = /(.*)c(.*)/i;
string = 'ABCDE';
actualmatch = string.match(pattern);
expectedmatch = Array('ABCDE', 'AB', 'DE');
addThis();

status = this.inSection(202);
pattern = /abcd/i;
string = 'ABCD';
actualmatch = string.match(pattern);
expectedmatch = Array('ABCD');
addThis();

status = this.inSection(203);
pattern = /a(bc)d/i;
string = 'ABCD';
actualmatch = string.match(pattern);
expectedmatch = Array('ABCD', 'BC');
addThis();

status = this.inSection(204);
pattern = /a[-]?c/i;
string = 'AC';
actualmatch = string.match(pattern);
expectedmatch = Array('AC');
addThis();

status = this.inSection(205);
pattern = /(abc)\1/i;
string = 'ABCABC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABCABC', 'ABC');
addThis();

status = this.inSection(206);
pattern = /([a-c]*)\1/i;
string = 'ABCABC';
actualmatch = string.match(pattern);
expectedmatch = Array('ABCABC', 'ABC');
addThis();

status = this.inSection(207);
pattern = /a(?!b)./;
string = 'abad';
actualmatch = string.match(pattern);
expectedmatch = Array('ad');
addThis();

status = this.inSection(208);
pattern = /a(?=d)./;
string = 'abad';
actualmatch = string.match(pattern);
expectedmatch = Array('ad');
addThis();

status = this.inSection(209);
pattern = /a(?=c|d)./;
string = 'abad';
actualmatch = string.match(pattern);
expectedmatch = Array('ad');
addThis();

status = this.inSection(210);
pattern = /a(?:b|c|d)(.)/;
string = 'ace';
actualmatch = string.match(pattern);
expectedmatch = Array('ace', 'e');
addThis();

status = this.inSection(211);
pattern = /a(?:b|c|d)*(.)/;
string = 'ace';
actualmatch = string.match(pattern);
expectedmatch = Array('ace', 'e');
addThis();

status = this.inSection(212);
pattern = /a(?:b|c|d)+?(.)/;
string = 'ace';
actualmatch = string.match(pattern);
expectedmatch = Array('ace', 'e');
addThis();

status = this.inSection(213);
pattern = /a(?:b|c|d)+?(.)/;
string = 'acdbcdbe';
actualmatch = string.match(pattern);
expectedmatch = Array('acd', 'd');
addThis();

status = this.inSection(214);
pattern = /a(?:b|c|d)+(.)/;
string = 'acdbcdbe';
actualmatch = string.match(pattern);
expectedmatch = Array('acdbcdbe', 'e');
addThis();

status = this.inSection(215);
pattern = /a(?:b|c|d){2}(.)/;
string = 'acdbcdbe';
actualmatch = string.match(pattern);
expectedmatch = Array('acdb', 'b');
addThis();

status = this.inSection(216);
pattern = /a(?:b|c|d){4,5}(.)/;
string = 'acdbcdbe';
actualmatch = string.match(pattern);
expectedmatch = Array('acdbcdb', 'b');
addThis();

status = this.inSection(217);
pattern = /a(?:b|c|d){4,5}?(.)/;
string = 'acdbcdbe';
actualmatch = string.match(pattern);
expectedmatch = Array('acdbcd', 'd');
addThis();

// MODIFIED - ECMA has different rules for paren contents
status = this.inSection(218);
pattern = /((foo)|(bar))*/;
string = 'foobar';
actualmatch = string.match(pattern);
//expectedmatch = Array('foobar', 'bar', 'foo', 'bar');
expectedmatch = Array('foobar', 'bar', undefined, 'bar');
addThis();

status = this.inSection(219);
pattern = /a(?:b|c|d){6,7}(.)/;
string = 'acdbcdbe';
actualmatch = string.match(pattern);
expectedmatch = Array('acdbcdbe', 'e');
addThis();

status = this.inSection(220);
pattern = /a(?:b|c|d){6,7}?(.)/;
string = 'acdbcdbe';
actualmatch = string.match(pattern);
expectedmatch = Array('acdbcdbe', 'e');
addThis();

status = this.inSection(221);
pattern = /a(?:b|c|d){5,6}(.)/;
string = 'acdbcdbe';
actualmatch = string.match(pattern);
expectedmatch = Array('acdbcdbe', 'e');
addThis();

status = this.inSection(222);
pattern = /a(?:b|c|d){5,6}?(.)/;
string = 'acdbcdbe';
actualmatch = string.match(pattern);
expectedmatch = Array('acdbcdb', 'b');
addThis();

status = this.inSection(223);
pattern = /a(?:b|c|d){5,7}(.)/;
string = 'acdbcdbe';
actualmatch = string.match(pattern);
expectedmatch = Array('acdbcdbe', 'e');
addThis();

status = this.inSection(224);
pattern = /a(?:b|c|d){5,7}?(.)/;
string = 'acdbcdbe';
actualmatch = string.match(pattern);
expectedmatch = Array('acdbcdb', 'b');
addThis();

status = this.inSection(225);
pattern = /a(?:b|(c|e){1,2}?|d)+?(.)/;
string = 'ace';
actualmatch = string.match(pattern);
expectedmatch = Array('ace', 'c', 'e');
addThis();

status = this.inSection(226);
pattern = /^(.+)?B/;
string = 'AB';
actualmatch = string.match(pattern);
expectedmatch = Array('AB', 'A');
addThis();

/* MODIFIED - ECMA has different rules for paren contents */
status = this.inSection(227);
pattern = /^([^a-z])|(\^)$/;
string = '.';
actualmatch = string.match(pattern);
//expectedmatch = Array('.', '.', '');
expectedmatch = Array('.', '.', undefined);
addThis();

status = this.inSection(228);
pattern = /^[<>]&/;
string = '<&OUT';
actualmatch = string.match(pattern);
expectedmatch = Array('<&');
addThis();

/* Can't refer to a capture before it's encountered & completed
status = this.inSection(229);
pattern = /^(a\1?){4}$/;
string = 'aaaaaaaaaa';
actualmatch = string.match(pattern);
expectedmatch = Array('aaaaaaaaaa', 'aaaa');
addThis();

status = this.inSection(230);
pattern = /^(a(?(1)\1)){4}$/;
string = 'aaaaaaaaaa';
actualmatch = string.match(pattern);
expectedmatch = Array('aaaaaaaaaa', 'aaaa');
addThis();
*/

status = this.inSection(231);
pattern = /((a{4})+)/;
string = 'aaaaaaaaa';
actualmatch = string.match(pattern);
expectedmatch = Array('aaaaaaaa', 'aaaaaaaa', 'aaaa');
addThis();

status = this.inSection(232);
pattern = /(((aa){2})+)/;
string = 'aaaaaaaaaa';
actualmatch = string.match(pattern);
expectedmatch = Array('aaaaaaaa', 'aaaaaaaa', 'aaaa', 'aa');
addThis();

status = this.inSection(233);
pattern = /(((a{2}){2})+)/;
string = 'aaaaaaaaaa';
actualmatch = string.match(pattern);
expectedmatch = Array('aaaaaaaa', 'aaaaaaaa', 'aaaa', 'aa');
addThis();

status = this.inSection(234);
pattern = /(?:(f)(o)(o)|(b)(a)(r))*/;
string = 'foobar';
actualmatch = string.match(pattern);
//expectedmatch = Array('foobar', 'f', 'o', 'o', 'b', 'a', 'r');
expectedmatch = Array('foobar', undefined, undefined, undefined, 'b', 'a', 'r');
addThis();

/* ECMA supports (?: (?= and (?! but doesn't support (?< etc.
status = this.inSection(235);
pattern = /(?<=a)b/;
string = 'ab';
actualmatch = string.match(pattern);
expectedmatch = Array('b');
addThis();

status = this.inSection(236);
pattern = /(?<!c)b/;
string = 'ab';
actualmatch = string.match(pattern);
expectedmatch = Array('b');
addThis();

status = this.inSection(237);
pattern = /(?<!c)b/;
string = 'b';
actualmatch = string.match(pattern);
expectedmatch = Array('b');
addThis();

status = this.inSection(238);
pattern = /(?<!c)b/;
string = 'b';
actualmatch = string.match(pattern);
expectedmatch = Array('b');
addThis();
*/

status = this.inSection(239);
pattern = /(?:..)*a/;
string = 'aba';
actualmatch = string.match(pattern);
expectedmatch = Array('aba');
addThis();

status = this.inSection(240);
pattern = /(?:..)*?a/;
string = 'aba';
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();

/*
* MODIFIED - ECMA has different rules for paren contents. Note
* this regexp has two non-capturing parens, and one capturing
*
* The issue: shouldn't the match be ['ab', undefined]? Because the
* '\1' matches the undefined value of the second iteration of the '*'
* (in which the 'b' part of the '|' matches). But Perl wants ['ab','b'].
*
* Answer: waldemar@netscape.com:
*
* The correct answer is ['ab', undefined].  Perl doesn't match
* ECMAScript here, and I'd say that Perl is wrong in this case.
*/
status = this.inSection(241);
pattern = /^(?:b|a(?=(.)))*\1/;
string = 'abc';
actualmatch = string.match(pattern);
//expectedmatch = Array('ab', 'b');
expectedmatch = Array('ab', undefined);
addThis();

status = this.inSection(242);
pattern = /^(){3,5}/;
string = 'abc';
actualmatch = string.match(pattern);
expectedmatch = Array('', '');
addThis();

status = this.inSection(243);
pattern = /^(a+)*ax/;
string = 'aax';
actualmatch = string.match(pattern);
expectedmatch = Array('aax', 'a');
addThis();

status = this.inSection(244);
pattern = /^((a|b)+)*ax/;
string = 'aax';
actualmatch = string.match(pattern);
expectedmatch = Array('aax', 'a', 'a');
addThis();

status = this.inSection(245);
pattern = /^((a|bc)+)*ax/;
string = 'aax';
actualmatch = string.match(pattern);
expectedmatch = Array('aax', 'a', 'a');
addThis();

/* MODIFIED - ECMA has different rules for paren contents */
status = this.inSection(246);
pattern = /(a|x)*ab/;
string = 'cab';
actualmatch = string.match(pattern);
//expectedmatch = Array('ab', '');
expectedmatch = Array('ab', undefined);
addThis();

status = this.inSection(247);
pattern = /(a)*ab/;
string = 'cab';
actualmatch = string.match(pattern);
expectedmatch = Array('ab', undefined);
addThis();

/* ECMA doesn't support (?imsx or (?-imsx
status = this.inSection(248);
pattern = /(?:(?i)a)b/;
string = 'ab';
actualmatch = string.match(pattern);
expectedmatch = Array('ab');
addThis();

status = this.inSection(249);
pattern = /((?i)a)b/;
string = 'ab';
actualmatch = string.match(pattern);
expectedmatch = Array('ab', 'a');
addThis();

status = this.inSection(250);
pattern = /(?:(?i)a)b/;
string = 'Ab';
actualmatch = string.match(pattern);
expectedmatch = Array('Ab');
addThis();

status = this.inSection(251);
pattern = /((?i)a)b/;
string = 'Ab';
actualmatch = string.match(pattern);
expectedmatch = Array('Ab', 'A');
addThis();

status = this.inSection(252);
pattern = /(?i:a)b/;
string = 'ab';
actualmatch = string.match(pattern);
expectedmatch = Array('ab');
addThis();

status = this.inSection(253);
pattern = /((?i:a))b/;
string = 'ab';
actualmatch = string.match(pattern);
expectedmatch = Array('ab', 'a');
addThis();

status = this.inSection(254);
pattern = /(?i:a)b/;
string = 'Ab';
actualmatch = string.match(pattern);
expectedmatch = Array('Ab');
addThis();

status = this.inSection(255);
pattern = /((?i:a))b/;
string = 'Ab';
actualmatch = string.match(pattern);
expectedmatch = Array('Ab', 'A');
addThis();

status = this.inSection(256);
pattern = /(?:(?-i)a)b/i;
string = 'ab';
actualmatch = string.match(pattern);
expectedmatch = Array('ab');
addThis();

status = this.inSection(257);
pattern = /((?-i)a)b/i;
string = 'ab';
actualmatch = string.match(pattern);
expectedmatch = Array('ab', 'a');
addThis();

status = this.inSection(258);
pattern = /(?:(?-i)a)b/i;
string = 'aB';
actualmatch = string.match(pattern);
expectedmatch = Array('aB');
addThis();

status = this.inSection(259);
pattern = /((?-i)a)b/i;
string = 'aB';
actualmatch = string.match(pattern);
expectedmatch = Array('aB', 'a');
addThis();

status = this.inSection(260);
pattern = /(?:(?-i)a)b/i;
string = 'aB';
actualmatch = string.match(pattern);
expectedmatch = Array('aB');
addThis();

status = this.inSection(261);
pattern = /((?-i)a)b/i;
string = 'aB';
actualmatch = string.match(pattern);
expectedmatch = Array('aB', 'a');
addThis();

status = this.inSection(262);
pattern = /(?-i:a)b/i;
string = 'ab';
actualmatch = string.match(pattern);
expectedmatch = Array('ab');
addThis();

status = this.inSection(263);
pattern = /((?-i:a))b/i;
string = 'ab';
actualmatch = string.match(pattern);
expectedmatch = Array('ab', 'a');
addThis();

status = this.inSection(264);
pattern = /(?-i:a)b/i;
string = 'aB';
actualmatch = string.match(pattern);
expectedmatch = Array('aB');
addThis();

status = this.inSection(265);
pattern = /((?-i:a))b/i;
string = 'aB';
actualmatch = string.match(pattern);
expectedmatch = Array('aB', 'a');
addThis();

status = this.inSection(266);
pattern = /(?-i:a)b/i;
string = 'aB';
actualmatch = string.match(pattern);
expectedmatch = Array('aB');
addThis();

status = this.inSection(267);
pattern = /((?-i:a))b/i;
string = 'aB';
actualmatch = string.match(pattern);
expectedmatch = Array('aB', 'a');
addThis();

status = this.inSection(268);
pattern = /((?s-i:a.))b/i;
string = 'a\nB';
actualmatch = string.match(pattern);
expectedmatch = Array('a\nB', 'a\n');
addThis();
*/

status = this.inSection(269);
pattern = /(?:c|d)(?:)(?:a(?:)(?:b)(?:b(?:))(?:b(?:)(?:b)))/;
string = 'cabbbb';
actualmatch = string.match(pattern);
expectedmatch = Array('cabbbb');
addThis();

status = this.inSection(270);
pattern = /(?:c|d)(?:)(?:aaaaaaaa(?:)(?:bbbbbbbb)(?:bbbbbbbb(?:))(?:bbbbbbbb(?:)(?:bbbbbbbb)))/;
string = 'caaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb';
actualmatch = string.match(pattern);
expectedmatch = Array('caaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb');
addThis();

status = this.inSection(271);
pattern = /(ab)\d\1/i;
string = 'Ab4ab';
actualmatch = string.match(pattern);
expectedmatch = Array('Ab4ab', 'Ab');
addThis();

status = this.inSection(272);
pattern = /(ab)\d\1/i;
string = 'ab4Ab';
actualmatch = string.match(pattern);
expectedmatch = Array('ab4Ab', 'ab');
addThis();

status = this.inSection(273);
pattern = /foo\w*\d{4}baz/;
string = 'foobar1234baz';
actualmatch = string.match(pattern);
expectedmatch = Array('foobar1234baz');
addThis();

status = this.inSection(274);
pattern = /x(~~)*(?:(?:F)?)?/;
string = 'x~~';
actualmatch = string.match(pattern);
expectedmatch = Array('x~~', '~~');
addThis();

/* Perl supports (?# but JS doesn't
status = this.inSection(275);
pattern = /^a(?#xxx){3}c/;
string = 'aaac';
actualmatch = string.match(pattern);
expectedmatch = Array('aaac');
addThis();
*/

/* ECMA doesn't support (?< etc
status = this.inSection(276);
pattern = /(?<![cd])[ab]/;
string = 'dbaacb';
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();

status = this.inSection(277);
pattern = /(?<!(c|d))[ab]/;
string = 'dbaacb';
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();

status = this.inSection(278);
pattern = /(?<!cd)[ab]/;
string = 'cdaccb';
actualmatch = string.match(pattern);
expectedmatch = Array('b');
addThis();

status = this.inSection(279);
pattern = /((?s)^a(.))((?m)^b$)/;
string = 'a\nb\nc\n';
actualmatch = string.match(pattern);
expectedmatch = Array('a\nb', 'a\n', '\n', 'b');
addThis();

status = this.inSection(280);
pattern = /((?m)^b$)/;
string = 'a\nb\nc\n';
actualmatch = string.match(pattern);
expectedmatch = Array('b', 'b');
addThis();

status = this.inSection(281);
pattern = /(?m)^b/;
string = 'a\nb\n';
actualmatch = string.match(pattern);
expectedmatch = Array('b');
addThis();

status = this.inSection(282);
pattern = /(?m)^(b)/;
string = 'a\nb\n';
actualmatch = string.match(pattern);
expectedmatch = Array('b', 'b');
addThis();

status = this.inSection(283);
pattern = /((?m)^b)/;
string = 'a\nb\n';
actualmatch = string.match(pattern);
expectedmatch = Array('b', 'b');
addThis();

status = this.inSection(284);
pattern = /\n((?m)^b)/;
string = 'a\nb\n';
actualmatch = string.match(pattern);
expectedmatch = Array('\nb', 'b');
addThis();

status = this.inSection(285);
pattern = /((?s).)c(?!.)/;
string = 'a\nb\nc\n';
actualmatch = string.match(pattern);
expectedmatch = Array('\nc', '\n');
addThis();

status = this.inSection(286);
pattern = /((?s).)c(?!.)/;
string = 'a\nb\nc\n';
actualmatch = string.match(pattern);
expectedmatch = Array('\nc', '\n');
addThis();

status = this.inSection(287);
pattern = /((?s)b.)c(?!.)/;
string = 'a\nb\nc\n';
actualmatch = string.match(pattern);
expectedmatch = Array('b\nc', 'b\n');
addThis();

status = this.inSection(288);
pattern = /((?s)b.)c(?!.)/;
string = 'a\nb\nc\n';
actualmatch = string.match(pattern);
expectedmatch = Array('b\nc', 'b\n');
addThis();

status = this.inSection(289);
pattern = /((?m)^b)/;
string = 'a\nb\nc\n';
actualmatch = string.match(pattern);
expectedmatch = Array('b', 'b');
addThis();
*/

/* ECMA doesn't support (?(condition)
status = this.inSection(290);
pattern = /(?(1)b|a)/;
string = 'a';
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();

status = this.inSection(291);
pattern = /(x)?(?(1)b|a)/;
string = 'a';
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();

status = this.inSection(292);
pattern = /()?(?(1)b|a)/;
string = 'a';
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();

status = this.inSection(293);
pattern = /()?(?(1)a|b)/;
string = 'a';
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();

status = this.inSection(294);
pattern = /^(\()?blah(?(1)(\)))$/;
string = '(blah)';
actualmatch = string.match(pattern);
expectedmatch = Array('(blah)', '(', ')');
addThis();

status = this.inSection(295);
pattern = /^(\()?blah(?(1)(\)))$/;
string = 'blah';
actualmatch = string.match(pattern);
expectedmatch = Array('blah');
addThis();

status = this.inSection(296);
pattern = /^(\(+)?blah(?(1)(\)))$/;
string = '(blah)';
actualmatch = string.match(pattern);
expectedmatch = Array('(blah)', '(', ')');
addThis();

status = this.inSection(297);
pattern = /^(\(+)?blah(?(1)(\)))$/;
string = 'blah';
actualmatch = string.match(pattern);
expectedmatch = Array('blah');
addThis();

status = this.inSection(298);
pattern = /(?(?!a)b|a)/;
string = 'a';
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();

status = this.inSection(299);
pattern = /(?(?=a)a|b)/;
string = 'a';
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();
*/

status = this.inSection(300);
pattern = /(?=(a+?))(\1ab)/;
string = 'aaab';
actualmatch = string.match(pattern);
expectedmatch = Array('aab', 'a', 'aab');
addThis();

status = this.inSection(301);
pattern = /(\w+:)+/;
string = 'one:';
actualmatch = string.match(pattern);
expectedmatch = Array('one:', 'one:');
addThis();

/* ECMA doesn't support (?< etc
status = this.inSection(302);
pattern = /$(?<=^(a))/;
string = 'a';
actualmatch = string.match(pattern);
expectedmatch = Array('', 'a');
addThis();
*/

status = this.inSection(303);
pattern = /(?=(a+?))(\1ab)/;
string = 'aaab';
actualmatch = string.match(pattern);
expectedmatch = Array('aab', 'a', 'aab');
addThis();

/* MODIFIED - ECMA has different rules for paren contents */
status = this.inSection(304);
pattern = /([\w:]+::)?(\w+)$/;
string = 'abcd';
actualmatch = string.match(pattern);
//expectedmatch = Array('abcd', '', 'abcd');
expectedmatch = Array('abcd', undefined, 'abcd');
addThis();

status = this.inSection(305);
pattern = /([\w:]+::)?(\w+)$/;
string = 'xy:z:::abcd';
actualmatch = string.match(pattern);
expectedmatch = Array('xy:z:::abcd', 'xy:z:::', 'abcd');
addThis();

status = this.inSection(306);
pattern = /^[^bcd]*(c+)/;
string = 'aexycd';
actualmatch = string.match(pattern);
expectedmatch = Array('aexyc', 'c');
addThis();

status = this.inSection(307);
pattern = /(a*)b+/;
string = 'caab';
actualmatch = string.match(pattern);
expectedmatch = Array('aab', 'aa');
addThis();

/* MODIFIED - ECMA has different rules for paren contents */
status = this.inSection(308);
pattern = /([\w:]+::)?(\w+)$/;
string = 'abcd';
actualmatch = string.match(pattern);
//expectedmatch = Array('abcd', '', 'abcd');
expectedmatch = Array('abcd', undefined, 'abcd');
addThis();

status = this.inSection(309);
pattern = /([\w:]+::)?(\w+)$/;
string = 'xy:z:::abcd';
actualmatch = string.match(pattern);
expectedmatch = Array('xy:z:::abcd', 'xy:z:::', 'abcd');
addThis();

status = this.inSection(310);
pattern = /^[^bcd]*(c+)/;
string = 'aexycd';
actualmatch = string.match(pattern);
expectedmatch = Array('aexyc', 'c');
addThis();

/* ECMA doesn't support (?>
status = this.inSection(311);
pattern = /(?>a+)b/;
string = 'aaab';
actualmatch = string.match(pattern);
expectedmatch = Array('aaab');
addThis();
*/

status = this.inSection(312);
pattern = /([[:]+)/;
string = 'a:[b]:';
actualmatch = string.match(pattern);
expectedmatch = Array(':[', ':[');
addThis();

status = this.inSection(313);
pattern = /([[=]+)/;
string = 'a=[b]=';
actualmatch = string.match(pattern);
expectedmatch = Array('=[', '=[');
addThis();

status = this.inSection(314);
pattern = /([[.]+)/;
string = 'a.[b].';
actualmatch = string.match(pattern);
expectedmatch = Array('.[', '.[');
addThis();

/* ECMA doesn't have rules for [:
status = this.inSection(315);
pattern = /[a[:]b[:c]/;
string = 'abc';
actualmatch = string.match(pattern);
expectedmatch = Array('abc');
addThis();
*/

/* ECMA doesn't support (?>
status = this.inSection(316);
pattern = /((?>a+)b)/;
string = 'aaab';
actualmatch = string.match(pattern);
expectedmatch = Array('aaab', 'aaab');
addThis();

status = this.inSection(317);
pattern = /(?>(a+))b/;
string = 'aaab';
actualmatch = string.match(pattern);
expectedmatch = Array('aaab', 'aaa');
addThis();

status = this.inSection(318);
pattern = /((?>[^()]+)|\([^()]*\))+/;
string = '((abc(ade)ufh()()x';
actualmatch = string.match(pattern);
expectedmatch = Array('abc(ade)ufh()()x', 'x');
addThis();
*/

/* Perl has \Z has end-of-line, ECMA doesn't
status = this.inSection(319);
pattern = /\Z/;
string = 'a\nb\n';
actualmatch = string.match(pattern);
expectedmatch = Array('');
addThis();

status = this.inSection(320);
pattern = /\z/;
string = 'a\nb\n';
actualmatch = string.match(pattern);
expectedmatch = Array('');
addThis();
*/

status = this.inSection(321);
pattern = /$/;
string = 'a\nb\n';
actualmatch = string.match(pattern);
expectedmatch = Array('');
addThis();

/* Perl has \Z has end-of-line, ECMA doesn't
status = this.inSection(322);
pattern = /\Z/;
string = 'b\na\n';
actualmatch = string.match(pattern);
expectedmatch = Array('');
addThis();

status = this.inSection(323);
pattern = /\z/;
string = 'b\na\n';
actualmatch = string.match(pattern);
expectedmatch = Array('');
addThis();
*/

status = this.inSection(324);
pattern = /$/;
string = 'b\na\n';
actualmatch = string.match(pattern);
expectedmatch = Array('');
addThis();

/* Perl has \Z has end-of-line, ECMA doesn't
status = this.inSection(325);
pattern = /\Z/;
string = 'b\na';
actualmatch = string.match(pattern);
expectedmatch = Array('');
addThis();

status = this.inSection(326);
pattern = /\z/;
string = 'b\na';
actualmatch = string.match(pattern);
expectedmatch = Array('');
addThis();
*/

status = this.inSection(327);
pattern = /$/;
string = 'b\na';
actualmatch = string.match(pattern);
expectedmatch = Array('');
addThis();

/* Perl has \Z has end-of-line, ECMA doesn't
status = this.inSection(328);
pattern = /\Z/m;
string = 'a\nb\n';
actualmatch = string.match(pattern);
expectedmatch = Array('');
addThis();

status = this.inSection(329);
pattern = /\z/m;
string = 'a\nb\n';
actualmatch = string.match(pattern);
expectedmatch = Array('');
addThis();
*/

status = this.inSection(330);
pattern = /$/m;
string = 'a\nb\n';
actualmatch = string.match(pattern);
expectedmatch = Array('');
addThis();

/* Perl has \Z has end-of-line, ECMA doesn't
status = this.inSection(331);
pattern = /\Z/m;
string = 'b\na\n';
actualmatch = string.match(pattern);
expectedmatch = Array('');
addThis();

status = this.inSection(332);
pattern = /\z/m;
string = 'b\na\n';
actualmatch = string.match(pattern);
expectedmatch = Array('');
addThis();
*/

status = this.inSection(333);
pattern = /$/m;
string = 'b\na\n';
actualmatch = string.match(pattern);
expectedmatch = Array('');
addThis();

/* Perl has \Z has end-of-line, ECMA doesn't
status = this.inSection(334);
pattern = /\Z/m;
string = 'b\na';
actualmatch = string.match(pattern);
expectedmatch = Array('');
addThis();

status = this.inSection(335);
pattern = /\z/m;
string = 'b\na';
actualmatch = string.match(pattern);
expectedmatch = Array('');
addThis();
*/

status = this.inSection(336);
pattern = /$/m;
string = 'b\na';
actualmatch = string.match(pattern);
expectedmatch = Array('');
addThis();

/* Perl has \Z has end-of-line, ECMA doesn't
status = this.inSection(337);
pattern = /a\Z/;
string = 'b\na\n';
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();
*/

/* $ only matches end of input unless multiline
status = this.inSection(338);
pattern = /a$/;
string = 'b\na\n';
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();
*/

/* Perl has \Z has end-of-line, ECMA doesn't
status = this.inSection(339);
pattern = /a\Z/;
string = 'b\na';
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();

status = this.inSection(340);
pattern = /a\z/;
string = 'b\na';
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();
*/

status = this.inSection(341);
pattern = /a$/;
string = 'b\na';
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();

status = this.inSection(342);
pattern = /a$/m;
string = 'a\nb\n';
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();

/* Perl has \Z has end-of-line, ECMA doesn't
status = this.inSection(343);
pattern = /a\Z/m;
string = 'b\na\n';
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();
*/

status = this.inSection(344);
pattern = /a$/m;
string = 'b\na\n';
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();

/* Perl has \Z has end-of-line, ECMA doesn't
status = this.inSection(345);
pattern = /a\Z/m;
string = 'b\na';
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();

status = this.inSection(346);
pattern = /a\z/m;
string = 'b\na';
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();
*/

status = this.inSection(347);
pattern = /a$/m;
string = 'b\na';
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();

/* Perl has \Z has end-of-line, ECMA doesn't
status = this.inSection(348);
pattern = /aa\Z/;
string = 'b\naa\n';
actualmatch = string.match(pattern);
expectedmatch = Array('aa');
addThis();
*/

/* $ only matches end of input unless multiline
status = this.inSection(349);
pattern = /aa$/;
string = 'b\naa\n';
actualmatch = string.match(pattern);
expectedmatch = Array('aa');
addThis();
*/

/* Perl has \Z has end-of-line, ECMA doesn't
status = this.inSection(350);
pattern = /aa\Z/;
string = 'b\naa';
actualmatch = string.match(pattern);
expectedmatch = Array('aa');
addThis();

status = this.inSection(351);
pattern = /aa\z/;
string = 'b\naa';
actualmatch = string.match(pattern);
expectedmatch = Array('aa');
addThis();
*/

status = this.inSection(352);
pattern = /aa$/;
string = 'b\naa';
actualmatch = string.match(pattern);
expectedmatch = Array('aa');
addThis();

status = this.inSection(353);
pattern = /aa$/m;
string = 'aa\nb\n';
actualmatch = string.match(pattern);
expectedmatch = Array('aa');
addThis();

/* Perl has \Z has end-of-line, ECMA doesn't
status = this.inSection(354);
pattern = /aa\Z/m;
string = 'b\naa\n';
actualmatch = string.match(pattern);
expectedmatch = Array('aa');
addThis();
*/

status = this.inSection(355);
pattern = /aa$/m;
string = 'b\naa\n';
actualmatch = string.match(pattern);
expectedmatch = Array('aa');
addThis();

/* Perl has \Z has end-of-line, ECMA doesn't
status = this.inSection(356);
pattern = /aa\Z/m;
string = 'b\naa';
actualmatch = string.match(pattern);
expectedmatch = Array('aa');
addThis();

status = this.inSection(357);
pattern = /aa\z/m;
string = 'b\naa';
actualmatch = string.match(pattern);
expectedmatch = Array('aa');
addThis();
*/

status = this.inSection(358);
pattern = /aa$/m;
string = 'b\naa';
actualmatch = string.match(pattern);
expectedmatch = Array('aa');
addThis();

/* Perl has \Z has end-of-line, ECMA doesn't
status = this.inSection(359);
pattern = /ab\Z/;
string = 'b\nab\n';
actualmatch = string.match(pattern);
expectedmatch = Array('ab');
addThis();
*/

/* $ only matches end of input unless multiline
status = this.inSection(360);
pattern = /ab$/;
string = 'b\nab\n';
actualmatch = string.match(pattern);
expectedmatch = Array('ab');
addThis();
*/

/* Perl has \Z has end-of-line, ECMA doesn't
status = this.inSection(361);
pattern = /ab\Z/;
string = 'b\nab';
actualmatch = string.match(pattern);
expectedmatch = Array('ab');
addThis();

status = this.inSection(362);
pattern = /ab\z/;
string = 'b\nab';
actualmatch = string.match(pattern);
expectedmatch = Array('ab');
addThis();
*/

status = this.inSection(363);
pattern = /ab$/;
string = 'b\nab';
actualmatch = string.match(pattern);
expectedmatch = Array('ab');
addThis();

status = this.inSection(364);
pattern = /ab$/m;
string = 'ab\nb\n';
actualmatch = string.match(pattern);
expectedmatch = Array('ab');
addThis();

/* Perl has \Z has end-of-line, ECMA doesn't
status = this.inSection(365);
pattern = /ab\Z/m;
string = 'b\nab\n';
actualmatch = string.match(pattern);
expectedmatch = Array('ab');
addThis();
*/

status = this.inSection(366);
pattern = /ab$/m;
string = 'b\nab\n';
actualmatch = string.match(pattern);
expectedmatch = Array('ab');
addThis();

/* Perl has \Z has end-of-line, ECMA doesn't
status = this.inSection(367);
pattern = /ab\Z/m;
string = 'b\nab';
actualmatch = string.match(pattern);
expectedmatch = Array('ab');
addThis();

status = this.inSection(368);
pattern = /ab\z/m;
string = 'b\nab';
actualmatch = string.match(pattern);
expectedmatch = Array('ab');
addThis();
*/

status = this.inSection(369);
pattern = /ab$/m;
string = 'b\nab';
actualmatch = string.match(pattern);
expectedmatch = Array('ab');
addThis();

/* Perl has \Z has end-of-line, ECMA doesn't
status = this.inSection(370);
pattern = /abb\Z/;
string = 'b\nabb\n';
actualmatch = string.match(pattern);
expectedmatch = Array('abb');
addThis();
*/

/* $ only matches end of input unless multiline
status = this.inSection(371);
pattern = /abb$/;
string = 'b\nabb\n';
actualmatch = string.match(pattern);
expectedmatch = Array('abb');
addThis();
*/

/* Perl has \Z has end-of-line, ECMA doesn't
status = this.inSection(372);
pattern = /abb\Z/;
string = 'b\nabb';
actualmatch = string.match(pattern);
expectedmatch = Array('abb');
addThis();

status = this.inSection(373);
pattern = /abb\z/;
string = 'b\nabb';
actualmatch = string.match(pattern);
expectedmatch = Array('abb');
addThis();
*/

status = this.inSection(374);
pattern = /abb$/;
string = 'b\nabb';
actualmatch = string.match(pattern);
expectedmatch = Array('abb');
addThis();

status = this.inSection(375);
pattern = /abb$/m;
string = 'abb\nb\n';
actualmatch = string.match(pattern);
expectedmatch = Array('abb');
addThis();

/* Perl has \Z has end-of-line, ECMA doesn't
status = this.inSection(376);
pattern = /abb\Z/m;
string = 'b\nabb\n';
actualmatch = string.match(pattern);
expectedmatch = Array('abb');
addThis();
*/

status = this.inSection(377);
pattern = /abb$/m;
string = 'b\nabb\n';
actualmatch = string.match(pattern);
expectedmatch = Array('abb');
addThis();

/* Perl has \Z has end-of-line, ECMA doesn't
status = this.inSection(378);
pattern = /abb\Z/m;
string = 'b\nabb';
actualmatch = string.match(pattern);
expectedmatch = Array('abb');
addThis();

status = this.inSection(379);
pattern = /abb\z/m;
string = 'b\nabb';
actualmatch = string.match(pattern);
expectedmatch = Array('abb');
addThis();
*/

status = this.inSection(380);
pattern = /abb$/m;
string = 'b\nabb';
actualmatch = string.match(pattern);
expectedmatch = Array('abb');
addThis();

status = this.inSection(381);
pattern = /(^|x)(c)/;
string = 'ca';
actualmatch = string.match(pattern);
expectedmatch = Array('c', '', 'c');
addThis();

status = this.inSection(382);
pattern = /foo.bart/;
string = 'foo.bart';
actualmatch = string.match(pattern);
expectedmatch = Array('foo.bart');
addThis();

status = this.inSection(383);
pattern = /^d[x][x][x]/m;
string = 'abcd\ndxxx';
actualmatch = string.match(pattern);
expectedmatch = Array('dxxx');
addThis();

status = this.inSection(384);
pattern = /tt+$/;
string = 'xxxtt';
actualmatch = string.match(pattern);
expectedmatch = Array('tt');
addThis();

/* ECMA spec says that each atom in a range must be a single character
status = this.inSection(385);
pattern = /([a-\d]+)/;
string = 'za-9z';
actualmatch = string.match(pattern);
expectedmatch = Array('9', '9');
addThis();

status = this.inSection(386);
pattern = /([\d-z]+)/;
string = 'a0-za';
actualmatch = string.match(pattern);
expectedmatch = Array('0-z', '0-z');
addThis();
*/

/* ECMA doesn't support [:
status = this.inSection(387);
pattern = /([a-[:digit:]]+)/;
string = 'za-9z';
actualmatch = string.match(pattern);
expectedmatch = Array('a-9', 'a-9');
addThis();

status = this.inSection(388);
pattern = /([[:digit:]-z]+)/;
string = '=0-z=';
actualmatch = string.match(pattern);
expectedmatch = Array('0-z', '0-z');
addThis();

status = this.inSection(389);
pattern = /([[:digit:]-[:alpha:]]+)/;
string = '=0-z=';
actualmatch = string.match(pattern);
expectedmatch = Array('0-z', '0-z');
addThis();
*/

status = this.inSection(390);
pattern = /(\d+\.\d+)/;
string = '3.1415926';
actualmatch = string.match(pattern);
expectedmatch = Array('3.1415926', '3.1415926');
addThis();

status = this.inSection(391);
pattern = /\.c(pp|xx|c)?$/i;
string = 'IO.c';
actualmatch = string.match(pattern);
expectedmatch = Array('.c', undefined);
addThis();

status = this.inSection(392);
pattern = /(\.c(pp|xx|c)?$)/i;
string = 'IO.c';
actualmatch = string.match(pattern);
expectedmatch = Array('.c', '.c', undefined);
addThis();

status = this.inSection(393);
pattern = /(^|a)b/;
string = 'ab';
actualmatch = string.match(pattern);
expectedmatch = Array('ab', 'a');
addThis();

status = this.inSection(394);
pattern = /^([ab]*?)(b)?(c)$/;
string = 'abac';
actualmatch = string.match(pattern);
expectedmatch = Array('abac', 'aba', undefined, 'c');
addThis();

status = this.inSection(395);
pattern = /^(?:.,){2}c/i;
string = 'a,b,c';
actualmatch = string.match(pattern);
expectedmatch = Array('a,b,c');
addThis();

status = this.inSection(396);
pattern = /^(.,){2}c/i;
string = 'a,b,c';
actualmatch = string.match(pattern);
expectedmatch =  Array('a,b,c', 'b,');
addThis();

status = this.inSection(397);
pattern = /^(?:[^,]*,){2}c/;
string = 'a,b,c';
actualmatch = string.match(pattern);
expectedmatch = Array('a,b,c');
addThis();

status = this.inSection(398);
pattern = /^([^,]*,){2}c/;
string = 'a,b,c';
actualmatch = string.match(pattern);
expectedmatch = Array('a,b,c', 'b,');
addThis();

status = this.inSection(399);
pattern = /^([^,]*,){3}d/;
string = 'aaa,b,c,d';
actualmatch = string.match(pattern);
expectedmatch = Array('aaa,b,c,d', 'c,');
addThis();

status = this.inSection(400);
pattern = /^([^,]*,){3,}d/;
string = 'aaa,b,c,d';
actualmatch = string.match(pattern);
expectedmatch = Array('aaa,b,c,d', 'c,');
addThis();

status = this.inSection(401);
pattern = /^([^,]*,){0,3}d/;
string = 'aaa,b,c,d';
actualmatch = string.match(pattern);
expectedmatch = Array('aaa,b,c,d', 'c,');
addThis();

status = this.inSection(402);
pattern = /^([^,]{1,3},){3}d/i;
string = 'aaa,b,c,d';
actualmatch = string.match(pattern);
expectedmatch = Array('aaa,b,c,d', 'c,');
addThis();

status = this.inSection(403);
pattern = /^([^,]{1,3},){3,}d/;
string = 'aaa,b,c,d';
actualmatch = string.match(pattern);
expectedmatch = Array('aaa,b,c,d', 'c,');
addThis();

status = this.inSection(404);
pattern = /^([^,]{1,3},){0,3}d/;
string = 'aaa,b,c,d';
actualmatch = string.match(pattern);
expectedmatch = Array('aaa,b,c,d', 'c,');
addThis();

status = this.inSection(405);
pattern = /^([^,]{1,},){3}d/;
string = 'aaa,b,c,d';
actualmatch = string.match(pattern);
expectedmatch = Array('aaa,b,c,d', 'c,');
addThis();

status = this.inSection(406);
pattern = /^([^,]{1,},){3,}d/;
string = 'aaa,b,c,d';
actualmatch = string.match(pattern);
expectedmatch = Array('aaa,b,c,d', 'c,');
addThis();

status = this.inSection(407);
pattern = /^([^,]{1,},){0,3}d/;
string = 'aaa,b,c,d';
actualmatch = string.match(pattern);
expectedmatch = Array('aaa,b,c,d', 'c,');
addThis();

status = this.inSection(408);
pattern = /^([^,]{0,3},){3}d/i;
string = 'aaa,b,c,d';
actualmatch = string.match(pattern);
expectedmatch = Array('aaa,b,c,d', 'c,');
addThis();

status = this.inSection(409);
pattern = /^([^,]{0,3},){3,}d/;
string = 'aaa,b,c,d';
actualmatch = string.match(pattern);
expectedmatch = Array('aaa,b,c,d', 'c,');
addThis();

status = this.inSection(410);
pattern = /^([^,]{0,3},){0,3}d/;
string = 'aaa,b,c,d';
actualmatch = string.match(pattern);
expectedmatch = Array('aaa,b,c,d', 'c,');
addThis();

/* ECMA doesn't support \A
status = this.inSection(411);
pattern = /(?!\A)x/m;
string = 'a\nxb\n';
actualmatch = string.match(pattern);
expectedmatch = Array('\n');
addThis();
*/

status = this.inSection(412);
pattern = /^(a(b)?)+$/;
string = 'aba';
actualmatch = string.match(pattern);
expectedmatch = Array('aba', 'a', undefined);
addThis();

status = this.inSection(413);
pattern = /^(aa(bb)?)+$/;
string = 'aabbaa';
actualmatch = string.match(pattern);
expectedmatch = Array('aabbaa', 'aa', undefined);
addThis();

status = this.inSection(414);
pattern = /^.{9}abc.*\n/m;
string = '123\nabcabcabcabc\n';
actualmatch = string.match(pattern);
expectedmatch = Array('abcabcabcabc\n');
addThis();

status = this.inSection(415);
pattern = /^(a)?a$/;
string = 'a';
actualmatch = string.match(pattern);
expectedmatch = Array('a', undefined);
addThis();

status = this.inSection(416);
pattern = /^(a\1?)(a\1?)(a\2?)(a\3?)$/;
string = 'aaaaaa';
actualmatch = string.match(pattern);
expectedmatch = Array('aaaaaa', 'a', 'aa', 'a', 'aa');
addThis();

/* Can't refer to a capture before it's encountered & completed
status = this.inSection(417);
pattern = /^(a\1?){4}$/;
string = 'aaaaaa';
actualmatch = string.match(pattern);
expectedmatch = Array('aaaaaa', 'aaa');
addThis();
*/

status = this.inSection(418);
pattern = /^(0+)?(?:x(1))?/;
string = 'x1';
actualmatch = string.match(pattern);
expectedmatch = Array('x1', undefined, '1');
addThis();

status = this.inSection(419);
pattern = /^([0-9a-fA-F]+)(?:x([0-9a-fA-F]+)?)(?:x([0-9a-fA-F]+))?/;
string = '012cxx0190';
actualmatch = string.match(pattern);
expectedmatch = Array('012cxx0190', '012c', undefined, '0190');
addThis();

status = this.inSection(420);
pattern = /^(b+?|a){1,2}c/;
string = 'bbbac';
actualmatch = string.match(pattern);
expectedmatch = Array('bbbac', 'a');
addThis();

status = this.inSection(421);
pattern = /^(b+?|a){1,2}c/;
string = 'bbbbac';
actualmatch = string.match(pattern);
expectedmatch = Array('bbbbac', 'a');
addThis();

status = this.inSection(422);
pattern = /((?:aaaa|bbbb)cccc)?/;
string = 'aaaacccc';
actualmatch = string.match(pattern);
expectedmatch = Array('aaaacccc', 'aaaacccc');
addThis();

status = this.inSection(423);
pattern = /((?:aaaa|bbbb)cccc)?/;
string = 'bbbbcccc';
actualmatch = string.match(pattern);
expectedmatch = Array('bbbbcccc', 'bbbbcccc');
addThis();




//-----------------------------------------------------------------------------
// test();
//-----------------------------------------------------------------------------



function addThis()
{
if(omitCurrentSection())
return;

statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


function omitCurrentSection()
{
try
{
// current section number is in global status variable
var n = status.match(/(\d+)/)[1];
return ((n < cnLBOUND) || (n > cnUBOUND));
}
catch(e)
{
return false;
}
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);
this.testRegExp(statusmessages, patterns, strings, actualmatches, expectedmatches);
//exitFunc ('test');
// }
},

/**
*  File Name:          perlstress__002.js
* Date:    2002-07-07
* SUMMARY: Testing JS RegExp engine against Perl 5 RegExp engine.
* Adjust cnLBOUND, cnUBOUND below to restrict which sections are tested.
*
* This test was created by running various patterns and strings through the
* Perl 5 RegExp engine. We saved the results below to test the JS engine.
*
* Each of the examples below is a negative test; that is, each produces a
* null match in Perl. Thus we set |expectedmatch| = |null| in each section.
*
* NOTE: ECMA/JS and Perl do differ on certain points. We have either commented
* out such sections altogether, or modified them to fit what we expect from JS.
*
* EXAMPLES:
*
* - ECMA does support (?: (?= and (?! operators, but doesn't support (?<  etc.
*
* - ECMA doesn't support (?(condition)
*/
test_perlstress__002 : function() {
var i = 0;
//var BUGNUMBER = 85721;
var summary = 'Testing regular expression edge cases';
var cnSingleSpace = ' ';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();
var cnLBOUND = 0;
var cnUBOUND = 1000;


status = this.inSection(1);
pattern = /abc/;
string = 'xbc';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(2);
pattern = /abc/;
string = 'axc';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(3);
pattern = /abc/;
string = 'abx';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(4);
pattern = /ab+bc/;
string = 'abc';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(5);
pattern = /ab+bc/;
string = 'abq';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(6);
pattern = /ab{1,}bc/;
string = 'abq';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(7);
pattern = /ab{4,5}bc/;
string = 'abbbbc';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(8);
pattern = /ab?bc/;
string = 'abbbbc';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(9);
pattern = /^abc$/;
string = 'abcc';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(10);
pattern = /^abc$/;
string = 'aabc';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(11);
pattern = /abc$/;
string = 'aabcd';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(12);
pattern = /a.*c/;
string = 'axyzd';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(13);
pattern = /a[bc]d/;
string = 'abc';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(14);
pattern = /a[b-d]e/;
string = 'abd';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(15);
pattern = /a[^bc]d/;
string = 'abd';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(16);
pattern = /a[^-b]c/;
string = 'a-c';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(17);
pattern = /a[^]b]c/;
string = 'a]c';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(18);
pattern = /\by\b/;
string = 'xy';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(19);
pattern = /\by\b/;
string = 'yz';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(20);
pattern = /\by\b/;
string = 'xyz';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(21);
pattern = /\Ba\B/;
string = 'a-';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(22);
pattern = /\Ba\B/;
string = '-a';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(23);
pattern = /\Ba\B/;
string = '-a-';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(24);
pattern = /\w/;
string = '-';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(25);
pattern = /\W/;
string = 'a';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(26);
pattern = /a\sb/;
string = 'a-b';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(27);
pattern = /\d/;
string = '-';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(28);
pattern = /\D/;
string = '1';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(29);
pattern = /[\w]/;
string = '-';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(30);
pattern = /[\W]/;
string = 'a';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(31);
pattern = /a[\s]b/;
string = 'a-b';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(32);
pattern = /[\d]/;
string = '-';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(33);
pattern = /[\D]/;
string = '1';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(34);
pattern = /$b/;
string = 'b';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(35);
pattern = /^(ab|cd)e/;
string = 'abcde';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(36);
pattern = /a[bcd]+dcdcde/;
string = 'adcdcde';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(37);
pattern = /(bc+d$|ef*g.|h?i(j|k))/;
string = 'effg';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(38);
pattern = /(bc+d$|ef*g.|h?i(j|k))/;
string = 'bcdd';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(39);
pattern = /[k]/;
string = 'ab';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

// MODIFIED - ECMA has different rules for paren contents.
status = this.inSection(40);
pattern = /(a)|\1/;
string = 'x';
actualmatch = string.match(pattern);
//expectedmatch = null;
expectedmatch = Array("", undefined);
addThis();

// MODIFIED - ECMA has different rules for paren contents.
status = this.inSection(41);
pattern = /((\3|b)\2(a)x)+/;
string = 'aaxabxbaxbbx';
actualmatch = string.match(pattern);
//expectedmatch = null;
expectedmatch = Array("ax", "ax", "", "a");
addThis();

status = this.inSection(42);
pattern = /abc/i;
string = 'XBC';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(43);
pattern = /abc/i;
string = 'AXC';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(44);
pattern = /abc/i;
string = 'ABX';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(45);
pattern = /ab+bc/i;
string = 'ABC';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(46);
pattern = /ab+bc/i;
string = 'ABQ';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(47);
pattern = /ab{1,}bc/i;
string = 'ABQ';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(48);
pattern = /ab{4,5}?bc/i;
string = 'ABBBBC';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(49);
pattern = /ab??bc/i;
string = 'ABBBBC';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(50);
pattern = /^abc$/i;
string = 'ABCC';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(51);
pattern = /^abc$/i;
string = 'AABC';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(52);
pattern = /a.*c/i;
string = 'AXYZD';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(53);
pattern = /a[bc]d/i;
string = 'ABC';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(54);
pattern = /a[b-d]e/i;
string = 'ABD';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(55);
pattern = /a[^bc]d/i;
string = 'ABD';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(56);
pattern = /a[^-b]c/i;
string = 'A-C';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(57);
pattern = /a[^]b]c/i;
string = 'A]C';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(58);
pattern = /$b/i;
string = 'B';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(59);
pattern = /^(ab|cd)e/i;
string = 'ABCDE';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(60);
pattern = /a[bcd]+dcdcde/i;
string = 'ADCDCDE';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(61);
pattern = /(bc+d$|ef*g.|h?i(j|k))/i;
string = 'EFFG';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(62);
pattern = /(bc+d$|ef*g.|h?i(j|k))/i;
string = 'BCDD';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(63);
pattern = /[k]/i;
string = 'AB';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(64);
pattern = /^(a\1?){4}$/;
string = 'aaaaaaaaa';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(65);
pattern = /^(a\1?){4}$/;
string = 'aaaaaaaaaaa';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

/* ECMA doesn't support (?(
status = this.inSection(66);
pattern = /^(a(?(1)\1)){4}$/;
string = 'aaaaaaaaa';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(67);
pattern = /^(a(?(1)\1)){4}$/;
string = 'aaaaaaaaaaa';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();
*/

/* ECMA doesn't support (?<
status = this.inSection(68);
pattern = /(?<=a)b/;
string = 'cb';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(69);
pattern = /(?<=a)b/;
string = 'b';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(70);
pattern = /(?<!c)b/;
string = 'cb';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();
*/

/* ECMA doesn't support (?(condition)
status = this.inSection(71);
pattern = /(?:(?i)a)b/;
string = 'aB';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(72);
pattern = /((?i)a)b/;
string = 'aB';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(73);
pattern = /(?i:a)b/;
string = 'aB';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(74);
pattern = /((?i:a))b/;
string = 'aB';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(75);
pattern = /(?:(?-i)a)b/i;
string = 'Ab';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(76);
pattern = /((?-i)a)b/i;
string = 'Ab';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(77);
pattern = /(?:(?-i)a)b/i;
string = 'AB';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(78);
pattern = /((?-i)a)b/i;
string = 'AB';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(79);
pattern = /(?-i:a)b/i;
string = 'Ab';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(80);
pattern = /((?-i:a))b/i;
string = 'Ab';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(81);
pattern = /(?-i:a)b/i;
string = 'AB';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(82);
pattern = /((?-i:a))b/i;
string = 'AB';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(83);
pattern = /((?-i:a.))b/i;
string = 'a\nB';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(84);
pattern = /((?s-i:a.))b/i;
string = 'B\nB';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();
*/

/* ECMA doesn't support (?<
status = this.inSection(85);
pattern = /(?<![cd])b/;
string = 'dbcb';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(86);
pattern = /(?<!(c|d))b/;
string = 'dbcb';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();
*/

status = this.inSection(87);
pattern = /^(?:a?b?)*$/;
string = 'a--';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(88);
pattern = /^b/;
string = 'a\nb\nc\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(89);
pattern = /()^b/;
string = 'a\nb\nc\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

/* ECMA doesn't support (?(
status = this.inSection(90);
pattern = /(?(1)a|b)/;
string = 'a';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(91);
pattern = /(x)?(?(1)a|b)/;
string = 'a';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(92);
pattern = /()(?(1)b|a)/;
string = 'a';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(93);
pattern = /^(\()?blah(?(1)(\)))$/;
string = 'blah)';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(94);
pattern = /^(\()?blah(?(1)(\)))$/;
string = '(blah';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(95);
pattern = /^(\(+)?blah(?(1)(\)))$/;
string = 'blah)';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(96);
pattern = /^(\(+)?blah(?(1)(\)))$/;
string = '(blah';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(97);
pattern = /(?(?{0})a|b)/;
string = 'a';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(98);
pattern = /(?(?{1})b|a)/;
string = 'a';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(99);
pattern = /(?(?!a)a|b)/;
string = 'a';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(100);
pattern = /(?(?=a)b|a)/;
string = 'a';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();
*/

status = this.inSection(101);
pattern = /^(?=(a+?))\1ab/;
string = 'aaab';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(102);
pattern = /^(?=(a+?))\1ab/;
string = 'aaab';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(103);
pattern = /([\w:]+::)?(\w+)$/;
string = 'abcd:';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(104);
pattern = /([\w:]+::)?(\w+)$/;
string = 'abcd:';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(105);
pattern = /(>a+)ab/;
string = 'aaab';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(106);
pattern = /a\Z/;
string = 'a\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(107);
pattern = /a\z/;
string = 'a\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(108);
pattern = /a$/;
string = 'a\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(109);
pattern = /a\z/;
string = 'b\na\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(110);
pattern = /a\z/m;
string = 'a\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(111);
pattern = /a\z/m;
string = 'b\na\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(112);
pattern = /aa\Z/;
string = 'aa\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(113);
pattern = /aa\z/;
string = 'aa\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(114);
pattern = /aa$/;
string = 'aa\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(115);
pattern = /aa\z/;
string = 'b\naa\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(116);
pattern = /aa\z/m;
string = 'aa\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(117);
pattern = /aa\z/m;
string = 'b\naa\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(118);
pattern = /aa\Z/;
string = 'ac\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(119);
pattern = /aa\z/;
string = 'ac\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(120);
pattern = /aa$/;
string = 'ac\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(121);
pattern = /aa\Z/;
string = 'b\nac\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(122);
pattern = /aa\z/;
string = 'b\nac\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(123);
pattern = /aa$/;
string = 'b\nac\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(124);
pattern = /aa\Z/;
string = 'b\nac';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(125);
pattern = /aa\z/;
string = 'b\nac';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(126);
pattern = /aa$/;
string = 'b\nac';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(127);
pattern = /aa\Z/m;
string = 'ac\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(128);
pattern = /aa\z/m;
string = 'ac\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(129);
pattern = /aa$/m;
string = 'ac\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(130);
pattern = /aa\Z/m;
string = 'b\nac\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(131);
pattern = /aa\z/m;
string = 'b\nac\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(132);
pattern = /aa$/m;
string = 'b\nac\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(133);
pattern = /aa\Z/m;
string = 'b\nac';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(134);
pattern = /aa\z/m;
string = 'b\nac';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(135);
pattern = /aa$/m;
string = 'b\nac';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(136);
pattern = /aa\Z/;
string = 'ca\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(137);
pattern = /aa\z/;
string = 'ca\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(138);
pattern = /aa$/;
string = 'ca\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(139);
pattern = /aa\Z/;
string = 'b\nca\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(140);
pattern = /aa\z/;
string = 'b\nca\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(141);
pattern = /aa$/;
string = 'b\nca\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(142);
pattern = /aa\Z/;
string = 'b\nca';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(143);
pattern = /aa\z/;
string = 'b\nca';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(144);
pattern = /aa$/;
string = 'b\nca';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(145);
pattern = /aa\Z/m;
string = 'ca\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(146);
pattern = /aa\z/m;
string = 'ca\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(147);
pattern = /aa$/m;
string = 'ca\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(148);
pattern = /aa\Z/m;
string = 'b\nca\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(149);
pattern = /aa\z/m;
string = 'b\nca\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(150);
pattern = /aa$/m;
string = 'b\nca\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(151);
pattern = /aa\Z/m;
string = 'b\nca';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(152);
pattern = /aa\z/m;
string = 'b\nca';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(153);
pattern = /aa$/m;
string = 'b\nca';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(154);
pattern = /ab\Z/;
string = 'ab\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(155);
pattern = /ab\z/;
string = 'ab\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(156);
pattern = /ab$/;
string = 'ab\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(157);
pattern = /ab\z/;
string = 'b\nab\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(158);
pattern = /ab\z/m;
string = 'ab\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(159);
pattern = /ab\z/m;
string = 'b\nab\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(160);
pattern = /ab\Z/;
string = 'ac\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(161);
pattern = /ab\z/;
string = 'ac\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(162);
pattern = /ab$/;
string = 'ac\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(163);
pattern = /ab\Z/;
string = 'b\nac\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(164);
pattern = /ab\z/;
string = 'b\nac\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(165);
pattern = /ab$/;
string = 'b\nac\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(166);
pattern = /ab\Z/;
string = 'b\nac';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(167);
pattern = /ab\z/;
string = 'b\nac';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(168);
pattern = /ab$/;
string = 'b\nac';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(169);
pattern = /ab\Z/m;
string = 'ac\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(170);
pattern = /ab\z/m;
string = 'ac\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(171);
pattern = /ab$/m;
string = 'ac\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(172);
pattern = /ab\Z/m;
string = 'b\nac\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(173);
pattern = /ab\z/m;
string = 'b\nac\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(174);
pattern = /ab$/m;
string = 'b\nac\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(175);
pattern = /ab\Z/m;
string = 'b\nac';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(176);
pattern = /ab\z/m;
string = 'b\nac';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(177);
pattern = /ab$/m;
string = 'b\nac';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(178);
pattern = /ab\Z/;
string = 'ca\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(179);
pattern = /ab\z/;
string = 'ca\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(180);
pattern = /ab$/;
string = 'ca\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(181);
pattern = /ab\Z/;
string = 'b\nca\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(182);
pattern = /ab\z/;
string = 'b\nca\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(183);
pattern = /ab$/;
string = 'b\nca\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(184);
pattern = /ab\Z/;
string = 'b\nca';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(185);
pattern = /ab\z/;
string = 'b\nca';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(186);
pattern = /ab$/;
string = 'b\nca';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(187);
pattern = /ab\Z/m;
string = 'ca\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(188);
pattern = /ab\z/m;
string = 'ca\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(189);
pattern = /ab$/m;
string = 'ca\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(190);
pattern = /ab\Z/m;
string = 'b\nca\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(191);
pattern = /ab\z/m;
string = 'b\nca\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(192);
pattern = /ab$/m;
string = 'b\nca\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(193);
pattern = /ab\Z/m;
string = 'b\nca';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(194);
pattern = /ab\z/m;
string = 'b\nca';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(195);
pattern = /ab$/m;
string = 'b\nca';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(196);
pattern = /abb\Z/;
string = 'abb\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(197);
pattern = /abb\z/;
string = 'abb\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(198);
pattern = /abb$/;
string = 'abb\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(199);
pattern = /abb\z/;
string = 'b\nabb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(200);
pattern = /abb\z/m;
string = 'abb\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(201);
pattern = /abb\z/m;
string = 'b\nabb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(202);
pattern = /abb\Z/;
string = 'ac\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(203);
pattern = /abb\z/;
string = 'ac\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(204);
pattern = /abb$/;
string = 'ac\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(205);
pattern = /abb\Z/;
string = 'b\nac\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(206);
pattern = /abb\z/;
string = 'b\nac\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(207);
pattern = /abb$/;
string = 'b\nac\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(208);
pattern = /abb\Z/;
string = 'b\nac';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(209);
pattern = /abb\z/;
string = 'b\nac';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(210);
pattern = /abb$/;
string = 'b\nac';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(211);
pattern = /abb\Z/m;
string = 'ac\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(212);
pattern = /abb\z/m;
string = 'ac\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(213);
pattern = /abb$/m;
string = 'ac\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(214);
pattern = /abb\Z/m;
string = 'b\nac\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(215);
pattern = /abb\z/m;
string = 'b\nac\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(216);
pattern = /abb$/m;
string = 'b\nac\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(217);
pattern = /abb\Z/m;
string = 'b\nac';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(218);
pattern = /abb\z/m;
string = 'b\nac';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(219);
pattern = /abb$/m;
string = 'b\nac';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(220);
pattern = /abb\Z/;
string = 'ca\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(221);
pattern = /abb\z/;
string = 'ca\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(222);
pattern = /abb$/;
string = 'ca\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(223);
pattern = /abb\Z/;
string = 'b\nca\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(224);
pattern = /abb\z/;
string = 'b\nca\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(225);
pattern = /abb$/;
string = 'b\nca\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(226);
pattern = /abb\Z/;
string = 'b\nca';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(227);
pattern = /abb\z/;
string = 'b\nca';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(228);
pattern = /abb$/;
string = 'b\nca';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(229);
pattern = /abb\Z/m;
string = 'ca\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(230);
pattern = /abb\z/m;
string = 'ca\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(231);
pattern = /abb$/m;
string = 'ca\nb\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(232);
pattern = /abb\Z/m;
string = 'b\nca\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(233);
pattern = /abb\z/m;
string = 'b\nca\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(234);
pattern = /abb$/m;
string = 'b\nca\n';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(235);
pattern = /abb\Z/m;
string = 'b\nca';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(236);
pattern = /abb\z/m;
string = 'b\nca';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(237);
pattern = /abb$/m;
string = 'b\nca';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(238);
pattern = /a*abc?xyz+pqr{3}ab{2,}xy{4,5}pq{0,6}AB{0,}zz/;
string = 'x';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(239);
pattern = /\GX.*X/;
string = 'aaaXbX';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(240);
pattern = /\.c(pp|xx|c)?$/i;
string = 'Changes';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(241);
pattern = /^([a-z]:)/;
string = 'C:/';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(242);
pattern = /(\w)?(abc)\1b/;
string = 'abcab';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

/* ECMA doesn't support (?(
status = this.inSection(243);
pattern = /^(a)?(?(1)a|b)+$/;
string = 'a';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();
*/



//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------



function addThis()
{
if(omitCurrentSection())
return;

statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


function omitCurrentSection()
{
try
{
// current section number is in global status variable
var n = status.match(/(\d+)/)[1];
return ((n < cnLBOUND) || (n > cnUBOUND));
}
catch(e)
{
return false;
}
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
// printStatus (summary);
this.testRegExp(statusmessages, patterns, strings, actualmatches, expectedmatches);
//exitFunc ('test');
//}

},

/**
*  File Name:          regress__100199.js
* Date: 17 September 2001
*
* SUMMARY: Regression test for Bugzilla bug 100199
* See http://bugzilla.mozilla.org/show_bug.cgi?id=100199
*
* The empty character class [] is a valid RegExp construct: the condition
* that a given character belong to a set containing no characters. As such,
* it can never be met and is always FALSE. Similarly, [^] is a condition
* that matches any given character and is always TRUE.
*
* Neither one of these conditions should cause syntax errors in a RegExp.
*/
test_regress__100199 : function() {
var i = 0;
//var BUGNUMBER = 100199;
var summary = '[], [^] are valid RegExp conditions. Should not cause errors -';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();


pattern = /[]/;
string = 'abc';
status = this.inSection(1);
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

string = '';
status = this.inSection(2);
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

string = '[';
status = this.inSection(3);
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

string = '/';
status = this.inSection(4);
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

string = '[';
status = this.inSection(5);
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

string = ']';
status = this.inSection(6);
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

string = '[]';
status = this.inSection(7);
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

string = '[ ]';
status = this.inSection(8);
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

string = '][';
status = this.inSection(9);
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();


pattern = /a[]/;
string = 'abc';
status = this.inSection(10);
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

string = '';
status = this.inSection(11);
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

string = 'a[';
status = this.inSection(12);
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

string = 'a[]';
status = this.inSection(13);
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

string = '[';
status = this.inSection(14);
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

string = ']';
status = this.inSection(15);
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

string = '[]';
status = this.inSection(16);
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

string = '[ ]';
status = this.inSection(17);
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

string = '][';
status = this.inSection(18);
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();


pattern = /[^]/;
string = 'abc';
status = this.inSection(19);
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();

string = '';
status = this.inSection(20);
actualmatch = string.match(pattern);
expectedmatch = null; //there are no characters to test against the condition
addThis();

string = '\/';
status = this.inSection(21);
actualmatch = string.match(pattern);
expectedmatch = Array('/');
addThis();

string = '\[';
status = this.inSection(22);
actualmatch = string.match(pattern);
expectedmatch = Array('[');
addThis();

string = '[';
status = this.inSection(23);
actualmatch = string.match(pattern);
expectedmatch = Array('[');
addThis();

string = ']';
status = this.inSection(24);
actualmatch = string.match(pattern);
expectedmatch = Array(']');
addThis();

string = '[]';
status = this.inSection(25);
actualmatch = string.match(pattern);
expectedmatch = Array('[');
addThis();

string = '[ ]';
status = this.inSection(26);
actualmatch = string.match(pattern);
expectedmatch = Array('[');
addThis();

string = '][';
status = this.inSection(27);
actualmatch = string.match(pattern);
expectedmatch = Array(']');
addThis();


pattern = /a[^]/;
string = 'abc';
status = this.inSection(28);
actualmatch = string.match(pattern);
expectedmatch = Array('ab');
addThis();

string = '';
status = this.inSection(29);
actualmatch = string.match(pattern);
expectedmatch = null; //there are no characters to test against the condition
addThis();

string = 'a[';
status = this.inSection(30);
actualmatch = string.match(pattern);
expectedmatch = Array('a[');
addThis();

string = 'a]';
status = this.inSection(31);
actualmatch = string.match(pattern);
expectedmatch = Array('a]');
addThis();

string = 'a[]';
status = this.inSection(32);
actualmatch = string.match(pattern);
expectedmatch = Array('a[');
addThis();

string = 'a[ ]';
status = this.inSection(33);
actualmatch = string.match(pattern);
expectedmatch = Array('a[');
addThis();

string = 'a][';
status = this.inSection(34);
actualmatch = string.match(pattern);
expectedmatch = Array('a]');
addThis();




//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------



function addThis()
{
statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);
//this.testRegExp(statusmessages, patterns, strings, actualmatches, expectedmatches);
//exitFunc ('test');
//}

},

/**
*  File Name:          regress__105972.js
* Date: 22 October 2001
*
* SUMMARY: Regression test for Bugzilla bug 105972:
* "/^.*?$/ will not match anything"
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=105972
*/
test_regress__105972 : function() {
var i = 0;
//var BUGNUMBER = 105972;
var summary = 'Regression test for Bugzilla bug 105972';
var cnEmptyString = '';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();


/*
* The bug: this match was coming up null in Rhino and SpiderMonkey.
* It should match the whole string. The reason:
*
* The * operator is greedy, but *? is non-greedy: it will stop
* at the simplest match it can find. But the pattern here asks us
* to match till the end of the string. So the simplest match must
* go all the way out to the end, and *? has no choice but to do it.
*/
status = this.inSection(1);
pattern = /^.*?$/;
string = 'Hello World';
actualmatch = string.match(pattern);
expectedmatch = Array(string);
addThis();


/*
* Leave off the '$' condition - here we expect the empty string.
* Unlike the above pattern, we don't have to match till the end of
* the string, so the non-greedy operator *? doesn't try to...
*/
status = this.inSection(2);
pattern = /^.*?/;
string = 'Hello World';
actualmatch = string.match(pattern);
expectedmatch = Array(cnEmptyString);
addThis();


/*
* Try '$' combined with an 'or' operator.
*
* The operator *? will consume the string from left to right,
* attempting to satisfy the condition (:|$). When it hits ':',
* the match will stop because the operator *? is non-greedy.
*
* The submatch $1 = (:|$) will contain the ':'
*/
status = this.inSection(3);
pattern = /^.*?(:|$)/;
string = 'Hello: World';
actualmatch = string.match(pattern);
expectedmatch = Array('Hello:', ':');
addThis();


/*
* Again, '$' combined with an 'or' operator.
*
* The operator * will consume the string from left to right,
* attempting to satisfy the condition (:|$). When it hits ':',
* the match will not stop since * is greedy. The match will
* continue until it hits $, the end-of-string boundary.
*
* The submatch $1 = (:|$) will contain the empty string
* conceived to exist at the end-of-string boundary.
*/
status = this.inSection(4);
pattern = /^.*(:|$)/;
string = 'Hello: World';
actualmatch = string.match(pattern);
expectedmatch = Array(string, cnEmptyString);
addThis();




//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------



function addThis()
{
statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);
this.testRegExp(statusmessages, patterns, strings, actualmatches, expectedmatches);
//exitFunc ('test');
//}

},

/**
*  File Name:          regress__119909.js
* Date:    14 Jan 2002
* SUMMARY: Shouldn't crash on regexps with many nested parentheses
* See http://bugzilla.mozilla.org/show_bug.cgi?id=119909
*/
test_regress__119909 : function() {
//var BUGNUMBER = 119909;
var summary = "Shouldn't crash on regexps with many nested parentheses";
var NO_BACKREFS = false;
var DO_BACKREFS = true;


//--------------------------------------------------
//test();
//--------------------------------------------------


//function test()
//{
//enterFunc('test');
//printBugNumber(BUGNUMBER);
//printStatus(summary);

testThis(500, NO_BACKREFS, 'hello', 'goodbye');
testThis(500, DO_BACKREFS, 'hello', 'goodbye');

this.reportCompare('No Crash', 'No Crash', '');

//exitFunc('test');
//}


/*
* Creates a regexp pattern like (((((((((hello)))))))))
* and tests str.search(), str.match(), str.replace()
*/
function testThis(numParens, doBackRefs, strOriginal, strReplace)
{
var openParen = doBackRefs? '(' : '(?:';
var closeParen = ')';
var pattern = '';

for (var i=0; i<numParens; i++) {pattern += openParen;}
pattern += strOriginal;
for (i=0; i<numParens; i++) {pattern += closeParen;}
var re = new RegExp(pattern);

var res = strOriginal.search(re);
res = strOriginal.match(re);
res = strOriginal.replace(re, strReplace);
}

},

/**
*  File Name:          regress__122076.js
* Date:    12 Feb 2002
* SUMMARY: Don't crash on invalid regexp literals /  \\/  /
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=122076
* The function checkURL() below sometimes caused a compile-time error:
*
*         SyntaxError: unterminated parenthetical (:
*
* However, sometimes it would cause a crash instead. The presence of
* other functions below is merely fodder to help provoke the crash.
* The constant |STRESS| is number of times we'll try to crash on this.
*/
test_regress__122076 : function() {
//var BUGNUMBER = 122076;
var summary = "Don't crash on invalid regexp literals /  \\/  /";
var STRESS = 10;
var sEval = '';

//printBugNumber(BUGNUMBER);
//printStatus(summary);


sEval += 'function checkDate()'
sEval += '{'
sEval += 'return (this.value.search(/^[012]?\d\/[0123]?\d\/[0]\d$/) != -1);'
sEval += '}'

sEval += 'function checkDNSName()'
sEval += '{'
sEval += '  return (this.value.search(/^([\w\-]+\.)+([\w\-]{2,3})$/) != -1);'
sEval += '}'

sEval += 'function checkEmail()'
sEval += '{'
sEval += '  return (this.value.search(/^([\w\-]+\.)*[\w\-]+@([\w\-]+\.)+([\w\-]{2,3})$/) != -1);'
sEval += '}'

sEval += 'function checkHostOrIP()'
sEval += '{'
sEval += '  if (this.value.search(/^([\w\-]+\.)+([\w\-]{2,3})$/) == -1)'
sEval += '    return (this.value.search(/^[1-2]?\d{1,2}\.[1-2]?\d{1,2}\.[1-2]?\d{1,2}\.[1-2]?\d{1,2}$/) != -1);'
sEval += '  else'
sEval += '    return true;'
sEval += '}'

sEval += 'function checkIPAddress()'
sEval += '{'
sEval += '  return (this.value.search(/^[1-2]?\d{1,2}\.[1-2]?\d{1,2}\.[1-2]?\d{1,2}\.[1-2]?\d{1,2}$/) != -1);'
sEval += '}'

sEval += 'function checkURL()'
sEval += '{'
sEval += '  return (this.value.search(/^(((https?)|(ftp)):\/\/([\-\w]+\.)+\w{2,4}(\/[%\-\w]+(\.\w{2,})?)*(([\w\-\.\?\\/\*\$+@&#;`~=%!]*)(\.\w{2,})?)*\/?)$/) != -1);'
sEval += '}'


for (var i=0; i<STRESS; i++)
{
try
{
eval(sEval);
}
catch(e)
{
}
}

this.reportCompare('No Crash', 'No Crash', '');

},

/**
*  File Name:          regress__123437.js
* Date:    04 Feb 2002
* SUMMARY: regexp backreferences must hold |undefined| if not used
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=123437 (SpiderMonkey)
* See http://bugzilla.mozilla.org/show_bug.cgi?id=123439 (Rhino)
*/
test_regress__123437 : function() {
var i = 0;
//var BUGNUMBER = 123437;
var summary = 'regexp backreferences must hold |undefined| if not used';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();


pattern = /(a)?a/;
string = 'a';
status = this.inSection(1);
actualmatch = string.match(pattern);
expectedmatch = Array('a', undefined);
addThis();

pattern = /a|(b)/;
string = 'a';
status = this.inSection(2);
actualmatch = string.match(pattern);
expectedmatch = Array('a', undefined);
addThis();

pattern = /(a)?(a)/;
string = 'a';
status = this.inSection(3);
actualmatch = string.match(pattern);
expectedmatch = Array('a', undefined, 'a');
addThis();



//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------



function addThis()
{
statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


//function test()
//{
// enterFunc ('test');
//printBugNumber(BUGNUMBER);
// printStatus (summary);
this.testRegExp(statusmessages, patterns, strings, actualmatches, expectedmatches);
//exitFunc ('test');
//}

},

/**
*  File Name:          regress__165353.js
* Date:    31 August 2002
* SUMMARY: RegExp conformance test
* See http://bugzilla.mozilla.org/show_bug.cgi?id=165353
*/
test_regress__165353 : function() {
var i = 0;
//var BUGNUMBER = 165353;
var summary = 'RegExp conformance test';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();


pattern = /^([a-z]+)*[a-z]$/;
status = this.inSection(1);
string = 'a';
actualmatch = string.match(pattern);
expectedmatch = Array('a', undefined);
addThis();

status = this.inSection(2);
string = 'ab';
actualmatch = string.match(pattern);
expectedmatch = Array('ab', 'a');
addThis();

status = this.inSection(3);
string = 'abc';
actualmatch = string.match(pattern);
expectedmatch = Array('abc', 'ab');
addThis();


string = 'www.netscape.com';
status = this.inSection(4);
pattern = /^(([a-z]+)*[a-z]\.)+[a-z]{2,}$/;
actualmatch = string.match(pattern);
expectedmatch = Array('www.netscape.com', 'netscape.', 'netscap');
addThis();

// add one more capturing parens to the previous regexp -
status = this.inSection(5);
pattern = /^(([a-z]+)*([a-z])\.)+[a-z]{2,}$/;
actualmatch = string.match(pattern);
expectedmatch = Array('www.netscape.com', 'netscape.', 'netscap', 'e');
addThis();



//-------------------------------------------------------------------------------------------------
//test();
//-------------------------------------------------------------------------------------------------


function addThis()
{
statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);
this.testRegExp(statusmessages, patterns, strings, actualmatches, expectedmatches);
//exitFunc ('test');
//}

},

/**
*  File Name:          regress__169497.js
* Date:    31 August 2002
* SUMMARY: RegExp conformance test
* See http://bugzilla.mozilla.org/show_bug.cgi?id=169497
*/
test_regress__169497 : function() {
var i = 0;
//var BUGNUMBER = 169497;
var summary = 'RegExp conformance test';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var sBody = '';
var sHTML = '';
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();

sBody += '<body onXXX="alert(event.type);">\n';
sBody += '<p>Kibology for all<\/p>\n';
sBody += '<p>All for Kibology<\/p>\n';
sBody += '<\/body>';

sHTML += '<html>\n';
sHTML += sBody;
sHTML += '\n<\/html>';

status = this.inSection(1);
string = sHTML;
pattern = /<body.*>((.*\n?)*?)<\/body>/i;
actualmatch = string.match(pattern);
expectedmatch = Array(sBody, '\n<p>Kibology for all</p>\n<p>All for Kibology</p>\n', '<p>All for Kibology</p>\n');
addThis();



//-------------------------------------------------------------------------------------------------
//test();
//-------------------------------------------------------------------------------------------------


function addThis()
{
statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);
this.testRegExp(statusmessages, patterns, strings, actualmatches, expectedmatches);
//exitFunc ('test');
//}

},

/**
*  File Name:          regress__169534.js
* Date:    20 Sep 2002
* SUMMARY: RegExp conformance test
* See http://bugzilla.mozilla.org/show_bug.cgi?id=169534
*/
test_regress__169534 : function() {
var UBound = 0;
//var BUGNUMBER = 169534;
var summary = 'RegExp conformance test';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


status = this.inSection(1);
var re = /(\|)([\w\x81-\xff ]*)(\|)([\/a-z][\w:\/\.]*\.[a-z]{3,4})(\|)/ig;
var str = "To sign up click |here|https://www.xxxx.org/subscribe.htm|";
actual = str.replace(re, '<a href="$4">$2</a>');
expect = 'To sign up click <a href="https://www.xxxx.org/subscribe.htm">here</a>';
addThis();



//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------



function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
}


//function test()
//{
//enterFunc('test');
//printBugNumber(BUGNUMBER);
//printStatus(summary);

for (var i=0; i<UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//exitFunc ('test');
//}

},

/**
*  File Name:          regress__187133.js
* Date:    06 January 2003
* SUMMARY: RegExp conformance test
* See http://bugzilla.mozilla.org/show_bug.cgi?id=187133
*
* The tests here employ the regular expression construct:
*
*                   (?!pattern)
*
* This is a "zero-width lookahead negative assertion".
* From the Perl documentation:
*
*   For example, /foo(?!bar)/ matches any occurrence
*   of 'foo' that isn't followed by 'bar'.
*
* It is "zero-width" means that it does not consume any characters and that
* the parens are non-capturing. A non-null match array in the example above
* will have only have length 1, not 2.
*/
test_regress__187133 : function() {
var i = 0;
//var BUGNUMBER = 187133;
var summary = 'RegExp conformance test';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();


pattern = /(\.(?!com|org)|\/)/;
status = this.inSection(1);
string = 'ah.info';
actualmatch = string.match(pattern);
expectedmatch = ['.', '.'];
addThis();

status = this.inSection(2);
string = 'ah/info';
actualmatch = string.match(pattern);
expectedmatch = ['/', '/'];
addThis();

status = this.inSection(3);
string = 'ah.com';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();


pattern = /(?!a|b)|c/;
status = this.inSection(4);
string = '';
actualmatch = string.match(pattern);
expectedmatch = [''];
addThis();

status = this.inSection(5);
string = 'bc';
actualmatch = string.match(pattern);
expectedmatch = [''];
addThis();

status = this.inSection(6);
string = 'd';
actualmatch = string.match(pattern);
expectedmatch = [''];
addThis();




//-------------------------------------------------------------------------------------------------
//test();
//-------------------------------------------------------------------------------------------------


function addThis()
{
statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);
this.testRegExp(statusmessages, patterns, strings, actualmatches, expectedmatches);
//exitFunc ('test');
//}

},

/**
*  File Name:          regress__188206.js
* Date:    21 January 2003
* SUMMARY: Invalid use of regexp quantifiers should generate SyntaxErrors
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=188206
* and http://bugzilla.mozilla.org/show_bug.cgi?id=85721#c48 etc.
* and http://bugzilla.mozilla.org/show_bug.cgi?id=190685
* and http://bugzilla.mozilla.org/show_bug.cgi?id=197451
*/
test_regress__188206 : function() {
var UBound = 0;
//var BUGNUMBER = 188206;
var summary = 'Invalid use of regexp quantifiers should generate SyntaxErrors';
var TEST_PASSED = 'SyntaxError';
var TEST_FAILED = 'Generated an error, but NOT a SyntaxError!';
var TEST_FAILED_BADLY = 'Did not generate ANY error!!!';
var CHECK_PASSED = 'Should not generate an error';
var CHECK_FAILED = 'Generated an error!';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


/*
* All the following are invalid uses of regexp quantifiers and
* should generate SyntaxErrors. That's what we're testing for.
*
* To allow the test to compile and run, we have to hide the errors
* inside eval strings, and check they are caught at run-time -
*
*/
status = this.inSection(1);
testThis(' /a**/ ');

status = this.inSection(2);
testThis(' /a***/ ');

status = this.inSection(3);
testThis(' /a++/ ');

status = this.inSection(4);
testThis(' /a+++/ ');

/*
* The ? quantifier, unlike * or +, may appear twice in succession.
* Thus we need at least three in a row to provoke a SyntaxError -
*/

status = this.inSection(5);
testThis(' /a???/ ');

status = this.inSection(6);
testThis(' /a????/ ');


/*
* Now do some weird things on the left side of the regexps -
*/
status = this.inSection(9);
testThis(' /+a/ ');

status = this.inSection(10);
testThis(' /++a/ ');

status = this.inSection(11);
testThis(' /?a/ ');

status = this.inSection(12);
testThis(' /??a/ ');


/*
* Misusing the {DecmalDigits} quantifier - according to BOTH ECMA and Perl.
*
* Just as with the * and + quantifiers above, can't have two {DecmalDigits}
* quantifiers in succession - it's a SyntaxError.
*/
status = this.inSection(28);
testThis(' /x{1}{1}/ ');

status = this.inSection(29);
testThis(' /x{1,}{1}/ ');

status = this.inSection(30);
testThis(' /x{1,2}{1}/ ');

status = this.inSection(31);
testThis(' /x{1}{1,}/ ');

status = this.inSection(32);
testThis(' /x{1,}{1,}/ ');

status = this.inSection(33);
testThis(' /x{1,2}{1,}/ ');

status = this.inSection(34);
testThis(' /x{1}{1,2}/ ');

status = this.inSection(35);
testThis(' /x{1,}{1,2}/ ');

status = this.inSection(36);
testThis(' /x{1,2}{1,2}/ ');



//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------



/*
* Invalid syntax should generate a SyntaxError
*/
function testThis(sInvalidSyntax)
{
expect = TEST_PASSED;
actual = TEST_FAILED_BADLY;

try
{
eval(sInvalidSyntax);
}
catch(e)
{
if (e instanceof SyntaxError)
actual = TEST_PASSED;
else
actual = TEST_FAILED;
}

statusitems[UBound] = status;
expectedvalues[UBound] = expect;
actualvalues[UBound] = actual;
UBound++;
}


/*
* Allowed syntax shouldn't generate any errors
*/
function checkThis(sAllowedSyntax)
{
expect = CHECK_PASSED;
actual = CHECK_PASSED;

try
{
eval(sAllowedSyntax);
}
catch(e)
{
actual = CHECK_FAILED;
}

statusitems[UBound] = status;
expectedvalues[UBound] = expect;
actualvalues[UBound] = actual;
UBound++;
}


//function test()
//{
//enterFunc('test');
//printBugNumber(BUGNUMBER);
//printStatus(summary);

for (var i=0; i<UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//exitFunc ('test');
//}

},

/**
*  File Name:          regress__191479.js
* Date:    31 January 2003
* SUMMARY: Testing regular expressions of form /(x|y){n,}/
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=191479
*/
test_regress__191479 : function() {
var i = 0;
//var BUGNUMBER = 191479;
var summary = 'Testing regular expressions of form /(x|y){n,}/';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();


status = this.inSection(1);
string = '12 3 45';
pattern = /(\d|\d\s){2,}/;
actualmatch = string.match(pattern);
expectedmatch = Array('12', '2');
addThis();

status = this.inSection(2);
string = '12 3 45';
pattern = /(\d|\d\s){4,}/;
actualmatch = string.match(pattern);
expectedmatch = Array(string, '5');
addThis();

status = this.inSection(3);
string = '12 3 45';
pattern = /(\d|\d\s)+/;
actualmatch = string.match(pattern);
expectedmatch = Array('12', '2');
addThis();

status = this.inSection(4);
string = '12 3 45';
pattern = /(\d\s?){4,}/;
actualmatch = string.match(pattern);
expectedmatch = Array(string, '5');
addThis();

/*
* Let's reverse the operands in Sections 1-3 above -
*/
status = this.inSection(5);
string = '12 3 45';
pattern = /(\d\s|\d){2,}/;
actualmatch = string.match(pattern);
expectedmatch = Array(string, '5');
addThis();

status = this.inSection(6);
string = '12 3 45';
pattern = /(\d\s|\d){4,}/;
actualmatch = string.match(pattern);
expectedmatch = Array(string, '5');
addThis();

status = this.inSection(7);
string = '12 3 45';
pattern = /(\d\s|\d)+/;
actualmatch = string.match(pattern);
expectedmatch = Array(string, '5');
addThis();


/*
* Let's take all 7 sections above and make each quantifer non-greedy.
*
* This is done by appending ? to it. It doesn't change the meaning of
* the quantifier, but makes it non-greedy, which affects the results -
*/
status = this.inSection(8);
string = '12 3 45';
pattern = /(\d|\d\s){2,}?/;
actualmatch = string.match(pattern);
expectedmatch = Array('12', '2');
addThis();

status = this.inSection(9);
string = '12 3 45';
pattern = /(\d|\d\s){4,}?/;
actualmatch = string.match(pattern);
expectedmatch = Array('12 3 4', '4');
addThis();

status = this.inSection(10);
string = '12 3 45';
pattern = /(\d|\d\s)+?/;
actualmatch = string.match(pattern);
expectedmatch = Array('1', '1');
addThis();

status = this.inSection(11);
string = '12 3 45';
pattern = /(\d\s?){4,}?/;
actualmatch = string.match(pattern);
expectedmatch = Array('12 3 4', '4');
addThis();

status = this.inSection(12);
string = '12 3 45';
pattern = /(\d\s|\d){2,}?/;
actualmatch = string.match(pattern);
expectedmatch = Array('12 ', '2 ');
addThis();

status = this.inSection(13);
string = '12 3 45';
pattern = /(\d\s|\d){4,}?/;
actualmatch = string.match(pattern);
expectedmatch = Array('12 3 4', '4');
addThis();

status = this.inSection(14);
string = '12 3 45';
pattern = /(\d\s|\d)+?/;
actualmatch = string.match(pattern);
expectedmatch = Array('1', '1');
addThis();



//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------



function addThis()
{
statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);
this.testRegExp(statusmessages, patterns, strings, actualmatches, expectedmatches);
//exitFunc ('test');
//}

},

/**
*  File Name:          regress__202564.js
* Date:    18 April 2003
* SUMMARY: Testing regexp with many backreferences
* See http://bugzilla.mozilla.org/show_bug.cgi?id=202564
*
* Note that in Section 1 below, we expect the 1st and 4th backreferences
* to hold |undefined| instead of the empty strings one gets in Perl and IE6.
* This is because per ECMA, regexp backreferences must hold |undefined|
* if not used. See http://bugzilla.mozilla.org/show_bug.cgi?id=123437.
*/
test_regress__202564 : function() {
var i = 0;
//var BUGNUMBER = 202564;
var summary = 'Testing regexp with many backreferences';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();


status = this.inSection(1);
string = 'Seattle, WA to Buckley, WA';
pattern = /(?:(.+), )?(.+), (..) to (?:(.+), )?(.+), (..)/;
actualmatch = string.match(pattern);
expectedmatch = Array(string, undefined, "Seattle", "WA", undefined, "Buckley", "WA");
addThis();



//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------



function addThis()
{
statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);
this.testRegExp(statusmessages, patterns, strings, actualmatches, expectedmatches);
//exitFunc ('test');
//}

},

/**
*  File Name:          regress__209067.js
* Date:    12 June 2003
* SUMMARY: Testing complicated str.replace()
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=209067
*/
test_regress__209067 : function() {
var UBound = 0;
//var BUGNUMBER = 209067;
var summary = 'Testing complicated str.replace()';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


function formatHTML(h)
{
// a replace function used in the succeeding lines -
function S(s)
{
return s.replace(/</g,'&lt;').replace(/>/g,'&gt;');
}

h+='\n';
h=h.replace(/&([^\s]+;)/g,'&lt;&amp;$1&gt;');
h=h.replace(new RegExp('<!-'+'-[\\s\\S]*-'+'->','g'), S);
h=h.replace(/"[^"]*"/g,S);
h=h.replace(/'[^']*'/g,S);


h=h.replace(/<([^>]*)>/g,
function(s,p)
{
if(s.match(/!doctype/i))
return'<span class=doctype>&lt;' + p + '&gt;</span>';

p=p.replace(/\\'/g,'\\&#39;').replace(/\\"/g,'\\&#34;').replace(/^\s/,'');
p=p.replace(/(\s)([^<]+)$/g,
function(s,p1,p2)
{
p2=p2.replace(/(=)(\s*[^"'][^\s]*)(\s|$)/g,'$1<span class=attribute-value>$2</span>$3');
p2=p2.replace(/("[^"]*")/g,'<span class=attribute-value>$1</span>');
p2=p2.replace(/('[^']*')/g,'<span class=attribute-value>$1</span>');
return p1 + '<span class=attribute-name>'+p2+'</span>';
}
)

return'&lt;<span class=' + (s.match(/<\s*\//)?'end-tag':'start-tag') + '>' + p + '</span>&gt;';
}
)


h=h.replace(/&lt;(&[^\s]+;)&gt;/g,'<span class=entity>$1</span>');
h=h.replace(/(&lt;!--[\s\S]*--&gt;)/g,'<span class=comment>$1</span>');


numer=1;
h=h.replace(/(.*\n)/g,
function(s,p)
{
return (numer++) +'. ' + p;
}
)


return'<span class=text>' + h + '</span>';
}



/*
* sanity check
*/
status = this.inSection(1);
actual = formatHTML('abc');
expect = '<span class=text>1. abc\n</span>';
addThis();


/*
* The real test: can we run this without crashing?
* We are not validating the result, just running it.
*/
status = this.inSection(2);
var HUGE_TEST_STRING = hugeString();
formatHTML(HUGE_TEST_STRING);




//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------



function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
}


//function test()
//{
//enterFunc('test');
//printBugNumber(BUGNUMBER);
//printStatus(summary);

for (var i=0; i<UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//exitFunc ('test');
//}


function hugeString()
{
var s = '';

s += '<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">';
s += '<html lang="en">';
s += '<head>';
s += '	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1">';
s += '	<meta http-equiv="refresh" content="1800">';
s += '	<title>CNN.com</title>';
s += '	<link rel="Start" href="/">';
s += '	<link rel="Search" href="/search/">';
s += '	<link rel="stylesheet" href="http://i.cnn.net/cnn/.element/ssi/css/1.0/main.css" type="text/css">';
s += '	<script language="JavaScript1.2" src="http://i.cnn.net/cnn/.element/ssi/js/1.0/main.js" type="text/javascript"></script>';
s += '<script language="JavaScript1.1" src="http://ar.atwola.com/file/adsWrapper.js"></script>';
s += '<style type="text/css">';
s += '<!--';
s += '.aoltextad { text-align: justify; font-size: 12px; color: black; font-family: Georgia, sans-serif }';
s += '-->';
s += '</style>';
s += '<script language="JavaScript1.1" type="text/javascript" src="http://ar.atwola.com/file/adsPopup2.js"></script>';
s += '<script language="JavaScript">';
s += 'document.adoffset = 0;';
s += 'document.adPopupDomain = "www.cnn.com";';
s += 'document.adPopupFile = "/cnn_adspaces/adsPopup2.html";';
s += 'document.adPopupInterval = "P24";';
s += 'document.adPopunderInterval = "P24";';
s += 'adSetOther("&TVAR="+escape("class=us.low"));';
s += '</script>';
s += '';
s += '	';
s += '</head>';
s += '<body class="cnnMainPage">';
s += '';
s += '';
s += '';
s += '<a name="top_of_page"></a>';
s += '<a href="#ContentArea"><img src="http://i.cnn.net/cnn/images/1.gif" alt="Click here to skip to main content." width="10" height="1" border="0" align="right"></a>';
s += '<table width="770" border="0" cellpadding="0" cellspacing="0" style="speak: none">';
s += '	<col width="229">';
s += '	<col width="73">';
s += '	<col width="468">';
s += '	<tr>';
s += '		<td colspan="3"><!--';
s += '[[!~~ netscape hat ~~]][[table border="0" cellpadding="0" cellspacing="0" width="100%"]][[tr]][[td]][[script Language="Javascript" SRC="http://toolbar.aol.com/dashboard.twhat?dom=cnn" type="text/javascript"]][[/script]][[/td]][[/tr]][[/table]]';
s += '';
s += '[[div]][[img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="1" height="2" border="0"]][[/div]]';
s += '-->';
s += '		</td>';
s += '	</tr>';
s += '	<tr valign="bottom">';
s += '		<td width="229" style="speak: normal"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/logo/cnn.gif" alt="CNN.com" width="229" height="52" border="0"></td>';
s += '		<td width="73"></td>';
s += '		<td width="468" align="right">';
s += '			<!-- home/bottom.468x60 -->';
s += '<script language="JavaScript1.1">';
s += '<!--';
s += 'adSetTarget("_top");';
s += 'htmlAdWH( (new Array(93103287,93103287,93103300,93103300))[document.adoffset||0] , 468, 60);';
s += '//-->';
s += '</script>';
s += '<noscript><a href="http://ar.atwola.com/link/93103287/aol" target="_top"><img src="http://ar.atwola.com/image/93103287/aol" alt="Click Here" width="468" height="60" border="0"></a></noscript> ';
s += '';
s += '';
s += '';
s += '';
s += '		</td>';
s += '	</tr>';
s += '	<tr><td colspan="3"><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="1" height="2"></td></tr>';
s += '	<tr>';
s += '		<td colspan="3">';
s += '</td>';
s += '	</tr>';
s += '	<tr><td colspan="3" bgcolor="#CC0000"><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="1" height="3"></td></tr>';
s += '	<tr>';
s += '		<td colspan="3">';
s += '';
s += '<table width="770" border="0" cellpadding="0" cellspacing="0">';
s += '	<form action="http://search.cnn.com/cnn/search" method="get" onsubmit="return CNN_validateSearchForm(this);">';
s += '<input type="hidden" name="source" value="cnn">';
s += '<input type="hidden" name="invocationType" value="search/top">';
s += '	<tr><td colspan="4"><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="1" height="1" border="0"></td></tr>';
s += '	<tr><td colspan="4" bgcolor="#003366"><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="1" height="3" border="0"></td></tr>';
s += '	<tr>';
s += '		<td rowspan="2"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/searchbar/bar.search.gif" alt="SEARCH" width="110" height="27" border="0"></td>';
s += '		<td colspan="2"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/searchbar/bar.top.bevel.gif" alt="" width="653" height="3" border="0"></td>';
s += '		<td rowspan="2"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/searchbar/bar.right.bevel.gif" alt="" width="7" height="27" border="0"></td>';
s += '	</tr>';
s += '	<tr bgcolor="#B6D8E0">';
s += '		<td><table border="0" cellpadding="0" cellspacing="0">';
s += '				<tr>';
s += '					<td>&nbsp;&nbsp;</td>';
s += '					<td nowrap><span class="cnnFormTextB" style="color:#369">The Web</span></td>';
s += '					<td><input type="radio" name="sites" value="google" checked></td>';
s += '					<td>&nbsp;&nbsp;</td>';
s += '					<td><span class="cnnFormTextB" style="color:#369;">CNN.com</span></td>';
s += '					<td><input type="radio" name="sites" value="cnn"></td>';
s += '					<td>&nbsp;&nbsp;</td>';
s += '					<td><input type="text" name="query" class="cnnFormText" value="" title="Enter text to search for and click Search" size="35" maxlength="40" style="width: 280px"></td>';
s += '					<td>&nbsp;<input type="Submit" value="Search" class="cnnNavButton" style="padding: 0px; margin: 0px; width: 50px"></td>';
s += '				</tr>';
s += '			</table></td>';
s += '		<td align="right"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/searchbar/bar.google.gif" alt="enhanced by Google" width="137" height="24" border="0"></td>';
s += '	</tr>';
s += '	<tr><td colspan="4"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/searchbar/bar.bottom.bevel.gif" alt="" width="770" height="3" border="0"></td></tr>';
s += '	</form>';
s += '</table>';
s += '		</td>';
s += '	</tr>';
s += '';
s += '';
s += '</table>';
s += '';
s += '<table width="770" border="0" cellpadding="0" cellspacing="0">';
s += '	<col width="126" align="left" valign="top">';
s += '	<col width="10">';
s += '	<col width="280">';
s += '	<col width="10">';
s += '	<col width="344">';
s += '	<tr valign="top">';
s += '		<td rowspan="5" width="126" style="speak: none"><table id="cnnNavBar" width="126" bgcolor="#EEEEEE" border="0" cellpadding="0" cellspacing="0" summary="CNN.com Navigation">';
s += '	<col width="8" align="left" valign="top">';
s += '	<col width="118" align="left" valign="top">';
s += '	<tr bgcolor="#CCCCCC" class="cnnNavHiliteRow"><td width="8" class="swath">&nbsp;</td>';
s += '		<td class="cnnNavHilite" onClick="CNN_goTo("/")"><div class="cnnNavText"><a href="/">Home Page</a></div></td></tr>';
s += '	<tr class="cnnNavRow"><td class="swath">&nbsp;</td>';
s += '		<td class="cnnNav" onMouseOver="CNN_navBar(this,1,1)" onMouseOut="CNN_navBar(this,0,1)" onClick="CNN_navBarClick(this,1,"/WORLD/")"><div class="cnnNavText"><a href="/WORLD/">World</a></div></td></tr>';
s += '	<tr class="cnnNavRow"><td class="swath">&nbsp;</td>';
s += '		<td class="cnnNav" onMouseOver="CNN_navBar(this,1,1)" onMouseOut="CNN_navBar(this,0,1)" onClick="CNN_navBarClick(this,1,"/US/")"><div class="cnnNavText"><a href="/US/">U.S.</a></div></td></tr>';
s += '	<tr class="cnnNavRow"><td class="swath">&nbsp;</td>';
s += '		<td class="cnnNav" onMouseOver="CNN_navBar(this,1,1)" onMouseOut="CNN_navBar(this,0,1)" onClick="CNN_navBarClick(this,1,"/WEATHER/")"><div class="cnnNavText"><a href="/WEATHER/">Weather</a></div></td></tr>';
s += '	<tr class="cnnNavRow"><td class="swath">&nbsp;</td>';
s += '		<td class="cnnNav" onMouseOver="CNN_navBar(this,1,1)" onMouseOut="CNN_navBar(this,0,1)" onClick="CNN_navBarClick(this,1,"/money/")"><div class="cnnNavText"><a href="/money/">Business</a>&nbsp;<a href="/money/"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/nav_at_money.gif" alt="at CNN/Money" width="51" height="5" border="0"></a></div></td></tr>';
s += '	<tr class="cnnNavRow"><td class="swath">&nbsp;</td>';
s += '		<td class="cnnNav" onMouseOver="CNN_navBar(this,1,1)" onMouseOut="CNN_navBar(this,0,1)" onClick="CNN_navBarClick(this,1,"/cnnsi/")"><div class="cnnNavText"><a href="/si/">Sports</a>&nbsp;<a href="/si/"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/nav_at_si.gif" alt="at SI.com" width="50" height="5" border="0"></a></div></td></tr>';
s += '	<tr class="cnnNavRow"><td class="swath">&nbsp;</td>';
s += '		<td class="cnnNav" onMouseOver="CNN_navBar(this,1,1)" onMouseOut="CNN_navBar(this,0,1)" onClick="CNN_navBarClick(this,1,"/ALLPOLITICS/")"><div class="cnnNavText"><a href="/ALLPOLITICS/">Politics</a></div></td></tr>';
s += '	<tr class="cnnNavRow"><td class="swath">&nbsp;</td>';
s += '		<td class="cnnNav" onMouseOver="CNN_navBar(this,1,1)" onMouseOut="CNN_navBar(this,0,1)" onClick="CNN_navBarClick(this,1,"/LAW/")"><div class="cnnNavText"><a href="/LAW/">Law</a></div></td></tr>';
s += '	<tr class="cnnNavRow"><td class="swath">&nbsp;</td>';
s += '		<td class="cnnNav" onMouseOver="CNN_navBar(this,1,1)" onMouseOut="CNN_navBar(this,0,1)" onClick="CNN_navBarClick(this,1,"/TECH/")"><div class="cnnNavText"><a href="/TECH/">Technology</a></div></td></tr>';
s += '	<tr class="cnnNavRow"><td class="swath">&nbsp;</td>';
s += '		<td class="cnnNav" onMouseOver="CNN_navBar(this,1,1)" onMouseOut="CNN_navBar(this,0,1)" onClick="CNN_navBarClick(this,1,"/TECH/space/")"><div class="cnnNavText"><a href="/TECH/space/">Science &amp; Space</a></div></td></tr>';
s += '	<tr class="cnnNavRow"><td class="swath">&nbsp;</td>';
s += '		<td class="cnnNav" onMouseOver="CNN_navBar(this,1,1)" onMouseOut="CNN_navBar(this,0,1)" onClick="CNN_navBarClick(this,1,"/HEALTH/")"><div class="cnnNavText"><a href="/HEALTH/">Health</a></div></td></tr>';
s += '	<tr class="cnnNavRow"><td class="swath">&nbsp;</td>';
s += '		<td class="cnnNav" onMouseOver="CNN_navBar(this,1,1)" onMouseOut="CNN_navBar(this,0,1)" onClick="CNN_navBarClick(this,1,"/SHOWBIZ/")"><div class="cnnNavText"><a href="/SHOWBIZ/">Entertainment</a></div></td></tr>';
s += '	<tr class="cnnNavRow"><td class="swath">&nbsp;</td>';
s += '		<td class="cnnNav" onMouseOver="CNN_navBar(this,1,1)" onMouseOut="CNN_navBar(this,0,1)" onClick="CNN_navBarClick(this,1,"/TRAVEL/")"><div class="cnnNavText"><a href="/TRAVEL/">Travel</a></div></td></tr>';
s += '	<tr class="cnnNavRow"><td class="swath">&nbsp;</td>';
s += '		<td class="cnnNav" onMouseOver="CNN_navBar(this,1,1)" onMouseOut="CNN_navBar(this,0,1)" onClick="CNN_navBarClick(this,1,"/EDUCATION/")"><div class="cnnNavText"><a href="/EDUCATION/">Education</a></div></td></tr>';
s += '	<tr class="cnnNavRow"><td class="swath">&nbsp;</td>';
s += '		<td class="cnnNav" onMouseOver="CNN_navBar(this,1,1)" onMouseOut="CNN_navBar(this,0,1)" onClick="CNN_navBarClick(this,1,"/SPECIALS/")"><div class="cnnNavText"><a href="/SPECIALS/">Special Reports</a></div></td></tr>';
s += '	<tr bgcolor="#FFFFFF"><td class="cnnNavAd" colspan="2" align="center"><!-- home/left.120x90 -->';
s += '<script language="JavaScript1.1">';
s += '<!--';
s += 'adSetTarget("_top");';
s += 'htmlAdWH( (new Array(93166917,93166917,93170132,93170132))[document.adoffset||0] , 120, 90);';
s += '//-->';
s += '</script><noscript><a href="http://ar.atwola.com/link/93166917/aol" target="_top"><img src="http://ar.atwola.com/image/93166917/aol" alt="Click here for our advertiser" width="120" height="90" border="0"></a></noscript></td></tr>';
s += '	<tr bgcolor="#999999" class="cnnNavGroupRow">';
s += '		<td colspan="2" class="cnnNavGroup"><div class="cnnNavText">SERVICES</div></td></tr>';
s += '	<tr class="cnnNavOtherRow"><td class="swath">&nbsp;</td>';
s += '		<td class="cnnNavOther" onMouseOver="CNN_navBar(this,1,0)" onMouseOut="CNN_navBar(this,0,0)" onClick="CNN_navBarClick(this,0,"/video/")"><div class="cnnNavText"><a href="/video/">Video</a></div></td></tr>';
s += '	<tr class="cnnNavOtherRow"><td class="swath">&nbsp;</td>';
s += '		<td class="cnnNavOther" onMouseOver="CNN_navBar(this,1,0)" onMouseOut="CNN_navBar(this,0,0)" onClick="CNN_navBarClick(this,0,"/EMAIL/")"><div class="cnnNavText"><a href="/EMAIL/">E-Mail Services</a></div></td></tr>';
s += '	<tr class="cnnNavOtherRow"><td class="swath">&nbsp;</td>';
s += '		<td class="cnnNavOther" onMouseOver="CNN_navBar(this,1,0)" onMouseOut="CNN_navBar(this,0,0)" onClick="CNN_navBarClick(this,0,"/mobile/CNNtoGO/")"><div class="cnnNavText"><a href="/mobile/CNNtoGO/">CNN To Go</a></div></td></tr>';
s += '	<tr bgcolor="#999999" class="cnnNavGroupRow">';
s += '		<td colspan="2" class="cnnNavGroup" style="background-color: #445B60"><div class="cnnNavText" style="color: #fff">SEARCH</div></td></tr>';
s += '	<tr bgcolor="#CCCCCC"><td colspan="2" class="cnnNavSearch" style="background-color:#B6D8E0">';
s += '';
s += '<form action="http://search.cnn.com/cnn/search" method="get" name="nav_bottom_search" onSubmit="return CNN_validateSearchForm(this)" style="margin: 0px;">';
s += '	<input type="hidden" name="sites" value="cnn">';
s += '	<input type="hidden" name="source" value="cnn">';
s += '	<input type="hidden" name="invocationType" value="side/bottom">';
s += '<table width="100%" border="0" cellpadding="0" cellspacing="4">';
s += '	<tr><td colspan="2"><table width="100%" border="0" cellpadding="0" cellspacing="0">';
s += '			<tr>';
s += '				<td align="left"><span class="cnnFormTextB" style="color: #369">Web</span></td>';
s += '				<td><input type="radio" name="sites" value="google" checked></td>';
s += '				<td align="right"><span class="cnnFormTextB" style="color: #369">CNN.com</span></td>';
s += '				<td><input type="radio" name="sites" value="cnn"></td>';
s += '			</tr>';
s += '		</table></td></tr>';
s += '	<tr><td colspan="2"><input type="text" name="query" class="cnnFormText" value="" title="Enter text to search for and click Search" size="7" maxlength="40" style="width: 100%"></td></tr>';
s += '	<tr valign="top">';
s += '		<td><input type="submit" value="Search" class="cnnNavButton" style="padding: 0px; margin: 0px; width: 50px"></td>';
s += '		<td align="right"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/sect/SEARCH/nav.search.gif" alt="enhanced by Google" width="54" height="27"></td>';
s += '	</tr>';
s += '</table>';
s += '';
s += '';
s += '';
s += '</td></form></tr>';
s += '</table>';
s += '';
s += '		</td>';
s += '		<td rowspan="5" width="10"><a name="ContentArea"></a><img id="accessibilityPixel" src="http://i.cnn.net/cnn/images/1.gif" alt="" width="1" height="7" border="0"></td>';
s += '		<td colspan="3" valign="middle">';
s += '			<table border="0" cellpadding="0" cellspacing="0" width="100%">';
s += '				<tr>';
s += '					<td valign="top" nowrap><div class="cnnFinePrint" style="color: #333;padding:6px;padding-left:0px;">Updated: 05:53 p.m. EDT (2153 GMT) June 12, 2003</div></td>';
s += '					<td align="right" nowrap class="cnnt1link"><a href="http://edition.cnn.com/">Visit International Edition</a>&nbsp;</td>';
s += '				</tr><!--include virtual="/.element/ssi/sect/MAIN/1.0/banner.html"-->';
s += '			</table>';
s += '		</td>';
s += '	</tr>';
s += '	<tr valign="top">';
s += '		<td rowspan="2" width="280" bgcolor="#EAEFF4">';
s += '';
s += '<!-- T1 -->';
s += '					';
s += '					<a href="/2003/SHOWBIZ/Movies/06/12/obit.peck/index.html"><img src="http://i.cnn.net/cnn/2003/SHOWBIZ/Movies/06/12/obit.peck/top.peck.obit.jpg" alt="Oscar-winner Peck dies" width="280" height="210" border="0" hspace="0" vspace="0"></a>';
s += '';
s += '						<div class="cnnMainT1">';
s += '		<h2 style="font-size:20px;"><a href="/2003/SHOWBIZ/Movies/06/12/obit.peck/index.html">Oscar-winner Peck dies</a></h2>';
s += '<p>';
s += 'Actor Gregory Peck, who won an Oscar for his portrayal of upstanding lawyer Atticus Finch in 1962s "To Kill a Mockingbird," has died at age 87. Peck was best known for roles of dignified statesmen and people who followed a strong code of ethics. But he also could play against type. All told, Peck was nominated for five Academy Awards.';
s += '</p>';
s += '		<p>';
s += '			<b><a href="/2003/SHOWBIZ/Movies/06/12/obit.peck/index.html" class="cnnt1link">FULL STORY</a></b>';
s += '		</p>';
s += '';
s += '';
s += '';
s += '&#8226; <span class="cnnBodyText" style="font-weight:bold;color:#333;">Video: </span><img src="http://i.cnn.net/cnn/.element/img/1.0/misc/premium.gif" alt="premium content" width="9" height="11" hspace="0" vspace="0" border="0" align="absmiddle">  <a href="javascript:LaunchVideo("/showbiz/2003/06/12/peck.obit.affl.","300k");">A leading mans leading man</a><br>';
s += '';
s += '';
s += '';
s += '		';
s += '&#8226; <span class="cnnBodyText" style="font-weight:bold;color:#333">Interactive: </span> <a href="javascript:CNN_openPopup("/interactive/entertainment/0306/peck.obit/frameset.exclude.html","620x430","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,width=620,height=430")">Gregory Peck through the years</a><br>';
s += '';
s += '	';
s += '&#8226;  <a href="http://www.cnn.com/2003/SHOWBIZ/Movies/06/12/peck.filmography/index.html" target="new">Gregory Peck filmography</a><img src="http://i.cnn.net/cnn/.element/img/1.0/misc/icon.external.links.gif" alt="external link" width="20" height="13" vspace="1" hspace="4" border="0" align="top"><br>';
s += '';
s += '	';
s += '&#8226;  <a href="http://www.cnn.com/2003/SHOWBIZ/Movies/06/04/heroes.villains.ap/index.html" target="new">Pecks Finch chararcter AFIs top hero</a><img src="http://i.cnn.net/cnn/.element/img/1.0/misc/icon.external.links.gif" alt="external link" width="20" height="13" vspace="1" hspace="4" border="0" align="top"><br>';
s += '	</div>';
s += '';
s += '<!-- /T1 -->';
s += '		</td>';
s += '		';
s += '		<td rowspan="2" width="10"><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="10" height="1"></td>';
s += '		<td width="344">';
s += '';
s += '';
s += '';
s += '';
s += '<!-- T2 -->';
s += '';
s += '<div><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/px_c00.gif" alt="" width="344" height="2"></div>';
s += '<table width="344" border="0" cellpadding="0" cellspacing="0">';
s += '	<tr>';
s += '		<td width="285" class="cnnTabbedBoxHeader" style="padding-left:0px;"><span class="cnnBigPrint"><b>MORE TOP STORIES</b></span></td>';
s += ' 		<td width="59" class="cnnTabbedBoxTab" align="right" bgcolor="#336699"><a href="/userpicks"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/userpicks.gif" alt=" Hot Stories " width="59" height="11" border="0"></a></td>';
s += '	</tr>';
s += '</table>';
s += '<div style="padding:6px;padding-left:0px;">';
s += '';
s += '	';
s += '<div class="cnnMainNewT2">&#8226; <a href="/2003/WORLD/meast/06/12/mideast/index.html">7 dead in new Gaza strike</a>';
s += '| <img src="http://i.cnn.net/cnn/.element/img/1.0/misc/premium.gif" alt="premium content" width="9" height="11" hspace="0" vspace="0" border="0" align="absmiddle"> <a href="javascript:LaunchVideo("/world/2003/06/11/cb.bush.roadmap.ap.","300k");">Video</a><br></div>';
s += '';
s += '	';
s += '<div class="cnnMainNewT2">&#8226; <a href="/2003/WORLD/meast/06/12/sprj.irq.main/index.html">U.S. helicopter, jet down in Iraqi raid</a>';
s += '| <img src="http://i.cnn.net/cnn/.element/img/1.0/misc/premium.gif" alt="premium content" width="9" height="11" hspace="0" vspace="0" border="0" align="absmiddle"> <a href="javascript:LaunchVideo("/iraq/2003/06/11/bw.iraq.oil.cnn.","300k");">Video</a><br></div>';
s += '';
s += '	';
s += '<div class="cnnMainNewT2">&#8226; <a href="/2003/SHOWBIZ/TV/06/12/obit.brinkley/index.html">Television icon David Brinkley dead at 82</a><br></div>';
s += '';
s += '	';
s += '<div class="cnnMainNewT2">&#8226; <a href="/2003/LAW/06/12/peterson.case/index.html">Peterson search warrants will be made public in July</a><br></div>';
s += '';
s += '	';
s += '<div class="cnnMainNewT2">&#8226; <a href="/2003/WORLD/asiapcf/east/06/12/okinawa.rape/index.html">U.S. Marine held in new Okinawa rape case</a><br></div>';
s += '';
s += '	';
s += '<div class="cnnMainNewT2">&#8226; <a href="/2003/TECH/space/06/12/sprj.colu.bolts.ap/index.html">New threat discovered for shuttle launches</a><br></div>';
s += '';
s += '	';
s += '<div class="cnnMainNewT2">&#8226; <a href="/2003/SHOWBIZ/TV/06/12/television.sopranos.reut/index.html">"Soprano" Gandolfini shares his wealth with castmates</a><br></div>';
s += '<!--[[div class="cnnMainNewT2"]]&#8226;&nbsp;[[b]][[span style="color:#C00;"]]CNN[[/span]]Radio:[[/b]]&nbsp;[[a href="javascript:CNN_openPopup("/audio/radio/preferences.html","radioplayer","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,width=200,height=124")"]]Bush on Medicare[[/a]]&nbsp;[[a href="javascript:CNN_openPopup("/audio/radio/preferences.html","radioplayer","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,width=200,height=124")"]][[img src="http://i.a.cnn.net/cnn/.element/img/1.0/misc/live.video.gif" alt="" width="61" height="14" vspace="0" hspace="2" align="absmiddle" border="0"]][[/a]][[img src="http://i.a.cnn.net/cnn/.element/img/1.0/misc/audio.gif" alt="" width="10" height="10" vspace="0" hspace="2" align="absmiddle"]][[br]][[/div]]--></div>';
s += '';
s += '<!-- /T2 -->';
s += '<div><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="1" height="10"></div>';
s += '';
s += '<!--include virtual="/.element/ssi/misc/1.0/war.zone.smmap.txt"-->';
s += '<!-- =========== CNN Radio/Video Box =========== -->';
s += '<!-- top line -->	';
s += '<div><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/px_ccc.gif" alt="" width="344" height="1"></div>';
s += '<!-- /top line -->';
s += ' <table width="344" border="0" cellpadding="0" cellspacing="0">';
s += '	<tr valign="top">';
s += '<!-- left-side line -->	';
s += '		<td bgcolor="#CCCCCC" width="1"><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="1" height="30" hspace="0" vspace="0" border="0"></td>';
s += '<!-- /left-side line -->	';
s += '<!-- CNNRadio cell -->';
s += '        <td width="114"><div class="cnn6pxPad">';
s += '        <span class="cnnBigPrint" style="color:#C00;font-weight:bold;">CNN</span><span class="cnnBigPrint" style="color:#000;font-weight:bold;">RADIO</span>';
s += '<div class="cnnMainNewT2"><a href="javascript:CNN_openPopup("/audio/radio/preferences.html","radioplayer","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,width=200,height=124")">Listen to latest updates</a><img src="http://i.a.cnn.net/cnn/.element/img/1.0/misc/audio.gif" alt="" width="10" height="10" vspace="0" hspace="2" align="absmiddle">';
s += '<div><img src="http://i.a.cnn.net/cnn/images/1.gif" alt="" width="1" height="5" hspace="0" vspace="0"></div>';
s += '<!--';
s += '[[span class="cnnFinePrint"]]sponsored by:[[/span]][[br]][[center]]';
s += '[[!~~#include virtual="/cnn_adspaces/home/war_in_iraq/sponsor.88x31.ad"~~]]';
s += ' [[/center]]';
s += '-->';
s += ' </div></td>';
s += '<!-- /CNNRadio cell --> ';
s += '<!-- center line -->  ';
s += '		<td bgcolor="#CCCCCC" width="1"><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="1" height="1" hspace="0" vspace="0" border="0"></td>';
s += '<!-- /center line --> ';
s += '<!-- video cell --> ';
s += '       <td width="227"><div class="cnn6pxPad">';
s += '<!-- video box -->       ';
s += '<table width="215" border="0" cellpadding="0" cellspacing="0">';
s += '   <tr valign="top">';
s += '    <td width="144"><span class="cnnBigPrint" style="font-weight:bold;">VIDEO</span></td>';
s += '    <td width="6"><img src="http://i.a.cnn.net/cnn/images/1.gif" alt="" width="6" height="1" hspace="0" vspace="0"></td>';
s += '	<td width="65"><a href="/video/"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/more.video.blue.gif" alt="MORE VIDEO" width="62" height="11" hspace="0" vspace="0" border="0"></a></td></tr>';
s += '   <tr>';
s += '    <td width="215" colspan="3"><img src="http://i.a.cnn.net/cnn/images/1.gif" alt="" width="1" height="2" hspace="0" vspace="0"></td></tr>';
s += '  <tr valign="top">';
s += '    <td><div class="cnnBodyText">';
s += '     	Soldier broke dozens of hearts over e-mail<br>';
s += '     <img src="http://i.a.cnn.net/cnn/images/icons/premium.gif" align="middle" alt="premium content" width="9" height="11" hspace="0" vspace="1" border="0">&nbsp;<a href="javascript:LaunchVideo("/offbeat/2003/06/12/ms.casanova.col.ap.","300k");" class="cnnVideoLink">PLAY VIDEO</a></div>';
s += '  </td>';
s += '<td width="3"><img src="http://i.a.cnn.net/cnn/images/1.gif" alt="" width="3" height="1" hspace="0" vspace="0"></td>  ';
s += '  <td width="65" align="right">';
s += '    <a href="javascript:LaunchVideo("/offbeat/2003/06/12/ms.casanova.col.ap.","300k");"><img src="http://i.cnn.net/cnn/video/offbeat/2003/06/12/ms.casanova.col.vs.kndu.jpg" alt="" width="65" height="49" border="0" vspace="2" hspace="0"></a>';
s += '  </td></tr>';
s += '</table>';
s += ' <!-- /video box -->        ';
s += '       </div></td>';
s += '<!-- /video cell -->        ';
s += '<!-- right-side line -->       ';
s += '<td bgcolor="#CCCCCC" width="1"><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="1" height="1" hspace="0" vspace="0" border="0"></td>';
s += '<!-- /right-side line -->  ';
s += '		</tr>';
s += '  </table>';
s += '';
s += '<!-- bottom line -->';
s += '<div><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/px_ccc.gif" alt="" width="344" height="1"></div>';
s += '<!-- /bottom line -->';
s += '<!-- =========== /CNN Radio/Video Box =========== -->';
s += '';
s += '<div><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="1" height="10"></div>';
s += '<div><img src="http://i.cnn.net/cnn/.element/img/1.0/main/px_c00.gif" alt="" width="344" height="2"></div>';
s += '<table width="344" border="0" cellpadding="0" cellspacing="0">';
s += '	<tr>';
s += '		<td width="260" class="cnnTabbedBoxHeader" style="padding-left:0px;"><span class="cnnBigPrint"><b>ON THE SCENE</b></span></td>';
s += '		<td width="84" class="cnnTabbedBoxTab" align="right" bgcolor="#336699" style="padding: 0px 3px;"><a href="/LAW/"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/superlinks/law.gif" alt="more reports" height="11" border="0" hspace="2" vspace="2" align="right"></a></td>';
s += '	</tr>';
s += '</table>';
s += '';
s += '<table width="344" border="0" cellpadding="5" cellspacing="0">';
s += '	<tr valign="top">';
s += '		<td style="padding-left:0px;">                                                                                                                                <b>Jeffrey Toobin:</b> "It takes guts" for Peterson defense to subpoena judge over wiretap issue.';
s += '<a href="/2003/LAW/06/12/otsc.toobin/index.html">Full Story</a></td>';
s += '';
s += '<td width="65" align="right" style="padding-left:6px;"><a href="/2003/LAW/06/12/otsc.toobin/index.html"><img src="http://i.cnn.net/cnn/2003/LAW/06/12/otsc.toobin/tz.toobin.jpg" alt="image" width="65" height="49" border="0" hspace="0" vspace="0"></a></td>';
s += '	</tr>';
s += '</table>';
s += '<div><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="1" height="10"></div>';
s += '		</td>';
s += '	</tr>';
s += '	<tr valign="bottom">';
s += '		<td>';
s += '<table width="344" border="0" cellpadding="0" cellspacing="0">';
s += '	<tr>';
s += '		<td width="267" nowrap style="color: #c00; padding-left: 6px"><span class="cnnBigPrint" style="vertical-align: top"><b>BUSINESS</b></span>';
s += '			<a href="/money/"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/at_cnnmoney.gif" alt=" at CNN/Money " width="100" height="15" border="0"></a></td>';
s += '		<td width="77" align="right"><a href="/money/"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/business.news.blue.gif" alt=" Business News " width="77" height="11" border="0"></a></td>';
s += '	</tr>';
s += '</table>';
s += '';
s += '<table width="344" bgcolor="#EEEEEE" border="0" cellpadding="0" cellspacing="0" style="border: solid 1px #ddd">';
s += '	<tr valign="top">';
s += '		<td>';
s += '			<table width="100%" border="0" cellpadding="0" cellspacing="4">';
s += '				<tr>';
s += '					<td colspan="3"><span class="cnnMenuText"><b>STOCK/FUND QUOTES: </b></span></td>';
s += '				</tr><form action="http://qs.money.cnn.com/tq/stockquote" method="get" style="margin: 0px;">';
s += '				<tr>';
s += '					<td><span class="cnnFinePrint">enter symbol</span></td>';
s += '					<td><input type="text" name="symbols" size="7" maxlength="40" class="cnnMenuText" title="Enter stock/fund symbol or name to get a quote"></td>';
s += '					<td><input type="submit" value="GET" class="cnnNavButton"></td>';
s += '				</tr></form>';
s += '			</table>';
s += '			<table width="100%" border="0" cellpadding="0" cellspacing="4">';
s += '				<tr valign="top">';
s += '					<td><span class="cnnFinePrint">sponsored by:</span></td>';
s += '					<td align="right"><!--<a href="/money/news/specials/rebuild_iraq/"><img src="http://i.a.cnn.net/cnn/2003/images/04/17/money.box.gif" ALT="" width="150" height="31" HSPACE="0" VSPACE="0" border="0" align="left"></a>--><a href="http://ar.atwola.com/link/93103306/aol"><img src="http://ar.atwola.com/image/93103306/aol" alt="Click Here" width="88" height="31" border="0" hspace="0" vspace="0"></a></td>';
s += '				</tr>';
s += '			</table>';
s += '			</td>';
s += '		<td class="cnnMainMarketBox">		<table width="100%" border="0" cellpadding="4" cellspacing="0" summary="Market data from CNNmoney">';
s += '			<tr class="noBottomBorder">';
s += '				<td colspan="5"><span class="cnnMainMarketCell"><span class="cnnMenuText"><b><a href="/money/markets/">MARKETS:</a></b></span> <!-- 16:30:15 -->';
s += '';
s += '4:30pm ET, 6/12</span></td>';
s += '			</tr>';
s += '			<tr class="noTopBorder">';
s += '				<td><span class="cnnMainMarketCell"><a href="/money/markets/dow.html" title="Dow Jones Industrial Average">DJIA</a></span></td>';
s += '								<td><img src="http://i.cnn.net/cnn/.element/img/1.0/main/arrow_up.gif" alt="" width="9" height="9"></td>';
s += '				<td align="right" nowrap><span class="cnnMainMarketCell">+13.30</span></td>';
s += '				<td align="right" nowrap><span class="cnnMainMarketCell">9196.50</span></td>';
s += '				<td align="right" nowrap><span class="cnnMainMarketCell">+ 0.14%</span></td>';
s += '';
s += '			</tr>';
s += '			<tr>';
s += '				<td><span class="cnnMainMarketCell"><a href="/money/markets/nasdaq.html" title="NASDAQ">NAS</a></span></td>';
s += '								<td><img src="http://i.cnn.net/cnn/.element/img/1.0/main/arrow_up.gif" alt="" width="9" height="9"></td>';
s += '				<td align="right" nowrap><span class="cnnMainMarketCell">+ 7.60</span></td>';
s += '				<td align="right" nowrap><span class="cnnMainMarketCell">1653.62</span></td>';
s += '				<td align="right" nowrap><span class="cnnMainMarketCell">+ 0.46%</span></td>';
s += '';
s += '			</tr>';
s += '			<tr class="noBottomBorder">';
s += '				<td><span class="cnnMainMarketCell"><a href="/money/markets/sandp.html" title="S&amp;P 500">S&amp;P</a></span></td>';
s += '								<td><img src="http://i.cnn.net/cnn/.element/img/1.0/main/arrow_up.gif" alt="" width="9" height="9"></td>';
s += '				<td align="right" nowrap><span class="cnnMainMarketCell">+ 1.03</span></td>';
s += '				<td align="right" nowrap><span class="cnnMainMarketCell">998.51</span></td>';
s += '				<td align="right" nowrap><span class="cnnMainMarketCell">+ 0.10%</span></td>';
s += '';
s += '			</tr>';
s += '		</table>';
s += '</td>';
s += '	</tr>';
s += '</table>';
s += '';
s += '</td>';
s += '	</tr>';
s += '	<tr>';
s += '		<td colspan="3"><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="1" height="4"></td>';
s += '	</tr>';
s += '	<tr align="center" valign="bottom">';
s += '		<td width="280" bgcolor="#EEEEEE"><a href="/linkto/ftn.nytimes1.html"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/ftn.280x32.ny.times.gif" width="255" height="32" alt="" border="0"></a></td>';
s += '<td width="10"><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="10" height="1"></td>';
s += '		<td width="344" bgcolor="#EEEEEE"><a href="/linkto/ftn.bn3.html"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/ftn.345x32.breaking.news.gif" width="340" height="32" alt="" border="0"></a></td>';
s += '	</tr>';
s += '';
s += '</table>';
s += '';
s += '';
s += '<div><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="1" height="10"></div>';
s += '';
s += '';
s += '<table width="770" border="0" cellpadding="0" cellspacing="0">';
s += '	<col width="10">';
s += '	<col width="483" align="left" valign="top">';
s += '	<col width="10">';
s += '	<col width="267" align="left" valign="top">';
s += '	<tr valign="top">';
s += '		<td rowspan="2"><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="10" height="1"></td>';
s += '		<td valign="top">';
s += '			<table border="0" cellpadding="0" cellspacing="0">';
s += '				<tr valign="top">';
s += '					<td width="238">';
s += '						<div><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/px_c00.gif" alt="" width="238" height="2"></div>';
s += '';
s += '';
s += '';
s += '';
s += '';
s += '';
s += '						<table width="238" border="0" cellpadding="0" cellspacing="0">';
s += '							<tr>';
s += '						<td width="132" class="cnnTabbedBoxHeader" style="padding-left:0px;"><span class="cnnBigPrint"><b>MORE REAL TV</b></span></td>';
s += '						<td width="106" class="cnnTabbedBoxTab" align="right" bgcolor="#336699" style="padding: 0px 3px;"><a href="/SHOWBIZ"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/entertainment.news.gif" alt="More Entertainment" border="0" width="102" height="11" hspace="2" vspace="2" align="right"></a></td>';
s += '					</tr>';
s += '				</table>';
s += '				<div><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="238" height="5" vspace="0" hspace="0"></div>';
s += '						<table width="238" border="0" cellpadding="0" cellspacing="0">';
s += '							<tr valign="top">';
s += '								<td><div class="cnn6pxTpad">';
s += '	';
s += ' <a href="/2003/SHOWBIZ/06/11/eye.ent.voyeurs/index.html">Go ahead, follow me</a><br>';
s += 'New reality series and the movie debut of "Idol" finalists';
s += '								</div></td>';
s += '								<td width="71" align="right"><a href="/2003/SHOWBIZ/06/11/eye.ent.voyeurs/index.html"><img src="http://i.a.cnn.net/cnn/2003/SHOWBIZ/06/11/eye.ent.voyeurs/tz.movies.gif" alt="Go ahead, follow me" width="65" height="49" border="0" vspace="6"></a></td>';
s += '							</tr>';
s += '						</table>';
s += '';
s += '';
s += '';
s += '';
s += '';
s += '';
s += '			';
s += '				<div><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="238" height="5" vspace="0" hspace="0"></div>';
s += '<!--include virtual="/.element/ssi/video/section_teases/topvideos_include.txt"-->';
s += '					</td>';
s += '					<td><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="7" height="1"></td>';
s += '					<td width="238">';
s += '						<div><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/px_c00.gif" alt="" width="238" height="2"></div>';
s += '';
s += '';
s += '';
s += '';
s += '';
s += '';
s += '						<table width="238" border="0" cellpadding="0" cellspacing="0">';
s += '							<tr>';
s += '						<td width="157" class="cnnTabbedBoxHeader" style="padding-left:0px;"><span class="cnnBigPrint"><b>GIFT IDEAS</b></span></td>';
s += '						<td width="81" class="cnnTabbedBoxTab" align="right" bgcolor="#336699" style="padding: 0px 3px;"><a href="/money"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/superlinks/business.gif" alt="Business News" border="0" width="77" height="11" hspace="2" vspace="2" align="right"></a></td>';
s += '					</tr>';
s += '				</table>';
s += '				<div><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="238" height="5" vspace="0" hspace="0"></div>';
s += '						<table width="238" border="0" cellpadding="0" cellspacing="0">';
s += '							<tr valign="top">';
s += '								<td><div class="cnn6pxTpad">';
s += '';
s += '';
s += '<span class="cnnBodyText" style="font-weight:bold;">CNN/Money: </span> <a href="/money/2003/06/12/news/companies/fathers_day/index.htm?cnn=yes">Fathers Day</a><br>';
s += 'Smaller is better --from digital cameras to iPod';
s += '								</div></td>';
s += '								<td width="71" align="right"><a href="/money/2003/06/12/news/companies/fathers_day/index.htm?cnn=yes"><img src="http://i.a.cnn.net/cnn/images/programming.boxes/tz.money.dads.day.watch.jpg" alt="Fathers Day" width="65" height="49" border="0" vspace="6"></a></td>';
s += '							</tr>';
s += '						</table>';
s += '					</td>';
s += '				</tr>';
s += '			</table>';
s += '				<div><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="238" height="10" vspace="0" hspace="0"></div>			';
s += '<table width="483" border="0" cellspacing="0" cellpadding="0">';
s += '	<tr valign="top">';
s += '		<td rowspan="9"><br></td>';
s += '		<td width="238"><a href="/US/"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/us.gif" alt="U.S. News: " width="238" height="15" border="0"></a><br><div class="cnnMathis.inSections">';
s += '';
s += '	';
s += '&#8226;&nbsp;<a href="/2003/US/South/06/11/miami.rapist/index.html">Miami police link 4 rapes to serial rapist</a><br>';
s += '';
s += '	';
s += '&#8226;&nbsp;<a href="/2003/LAW/06/12/mistaken.identity.ap/index.html">Woman mistaken for fugitive jailed</a><br>';
s += '';
s += '	';
s += '&#8226;&nbsp;<a href="/2003/US/Northeast/06/12/woman.impaled.ap/index.html">Pregnant woman impaled on mic stand</a><br>';
s += '		</div></td>';
s += '		<td rowspan="7" width="7"><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="7" height="1"></td>';
s += '		<td width="238"><a href="/WORLD/"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/world.gif" alt="World News: " width="238" height="15" border="0"></a><br><div class="cnnMathis.inSections">';
s += '';
s += '	';
s += '&#8226;&nbsp;<a href="/2003/WORLD/europe/06/12/nato.bases/index.html">NATO reshapes for new era</a><br>';
s += '';
s += '	';
s += '&#8226;&nbsp;<a href="/2003/WORLD/africa/06/12/congo.democratic/index.html">U.N. reviews Bunia peace force</a><br>';
s += '';
s += '';
s += '';
s += '&#8226;&nbsp;<span class="cnnBodyText" style="font-weight:bold;color:#900;">TIME.com: </span><a href="/time/magazine/article/0,9171,1101030616-457361,00.html?CNN=yes" target="new">Saddams curtain trail</a><img src="http://i.cnn.net/cnn/.element/img/1.0/misc/icon.external.links.gif" alt="external link" width="20" height="13" vspace="1" hspace="4" border="0" align="top"><br>';
s += '		</div></td>';
s += '	</tr><tr valign="top">';
s += '		<td width="238"><a href="/TECH/"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/technology.gif" alt="Sci-Tech News: " width="238" height="15" border="0"></a><br><div class="cnnMathis.inSections">';
s += '';
s += '	';
s += '&#8226;&nbsp;<a href="/2003/TECH/ptech/06/11/bus2.ptech.dvd.maker/index.html">Another reason to throw out your VCR</a><br>';
s += '';
s += '	';
s += '&#8226;&nbsp;<a href="/2003/TECH/ptech/06/12/korea.samsung.reut/index.html">Flat screen TV prices dropping</a><br>';
s += '		</div></td>';
s += '		<td width="238"><a href="/SHOWBIZ/"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/entertainment.gif" alt="Entertainment News: " width="238" height="15" border="0"></a><br><div class="cnnMathis.inSections">';
s += '';
s += '	';
s += '&#8226;&nbsp;<a href="/2003/SHOWBIZ/TV/06/12/cnn.obrien/index.html">CNN hires Soledad OBrien for "AM"</a><br>';
s += '';
s += '	';
s += '&#8226;&nbsp;<a href="/2003/SHOWBIZ/TV/06/11/batchelor.troubles.ap/index.html">Dating show star let go by law firm</a><br>';
s += '		</div></td>';
s += '	</tr><tr valign="top">';
s += '		<td width="238"><a href="/ALLPOLITICS/"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/politics.gif" alt="Politics News: " width="238" height="15" border="0"></a><br><div class="cnnMathis.inSections">';
s += '';
s += '	';
s += '&#8226;&nbsp;<a href="/2003/ALLPOLITICS/06/11/schwarzenegger.ap/index.html">Schwarzenegger on California politics</a><br>';
s += '';
s += '	';
s += '&#8226;&nbsp;<a href="/2003/ALLPOLITICS/06/12/tax.credit.ap/index.html">House approves extension on child tax credit</a><br>';
s += '		</div></td>';
s += '		<td width="238"><a href="/LAW/"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/law.gif" alt="Law News: " width="238" height="15" border="0"></a><br><div class="cnnMathis.inSections">';
s += '';
s += '	';
s += '&#8226;&nbsp;<a href="/2003/LAW/06/12/plaintiff.advances.ap/index.html">Court bars cash advances to plaintiffs</a><br>';
s += '';
s += '	';
s += '&#8226;&nbsp;<a href="/2003/LAW/06/11/jackson.lawsuit.ap/index.html">Lawsuit against Jackson settled</a><br>';
s += '		</div></td>';
s += '	</tr><tr valign="top">';
s += '		<td width="238"><a href="/HEALTH/"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/health.gif" alt="Health News: " width="238" height="15" border="0"></a><br><div class="cnnMathis.inSections">';
s += '';
s += '	';
s += '&#8226;&nbsp;<a href="/2003/HEALTH/06/12/monkeypox.ap/index.html">Monkeypox spreading person-to-person?</a><br>';
s += '';
s += '	';
s += '&#8226;&nbsp;<a href="/2003/HEALTH/06/12/quick.xray.ap/index.html">A full body X-ray in 13 seconds</a><br>';
s += '		</div></td>';
s += '		<td width="238"><a href="/TECH/space/"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/space.gif" alt="Space News: " width="238" height="15" border="0"></a><br><div class="cnnMathis.inSections">';
s += '';
s += '	';
s += '&#8226;&nbsp;<a href="/2003/TECH/science/06/12/hydrogen.ozone.ap/index.html">Hydrogen fuel may disturb ozone layer</a><br>';
s += '';
s += '	';
s += '&#8226;&nbsp;<a href="/2003/TECH/space/06/12/sprj.colu.bolts.ap/index.html">New threat found for shuttle launches</a><br>';
s += '		</div></td>';
s += '	</tr><tr valign="top">';
s += '		<td width="238"><a href="/TRAVEL/"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/travel.gif" alt="Travel News: " width="238" height="15" border="0"></a><br><div class="cnnMathis.inSections">';
s += '';
s += '	';
s += '&#8226;&nbsp;<a href="/2003/TRAVEL/DESTINATIONS/06/12/walk.across.america.ap/index.html">Walking America from coast to coast</a><br>';
s += '';
s += '	';
s += '&#8226;&nbsp;<a href="/2003/TRAVEL/06/11/bi.airlines.executives.reut/index.html">Airline execs not seeing sunny skies yet</a><br>';
s += '		</div></td>';
s += '		<td width="238"><a href="/EDUCATION/"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/education.gif" alt="Education News: " width="238" height="15" border="0"></a><br><div class="cnnMathis.inSections">';
s += '';
s += '	';
s += '&#8226;&nbsp;<a href="/2003/EDUCATION/06/12/arabs.prom.ap/index.html">Arab students seek prom balance</a><br>';
s += '';
s += '	';
s += '&#8226;&nbsp;<a href="/2003/EDUCATION/06/11/school.fundraising.ap/index.html">Public schools turn to upscale fundraising</a><br>';
s += '		</div></td>';
s += '	</tr><tr valign="top">';
s += '		<td width="238"><a href="/si/index.html?cnn=yes"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/sports.gif" alt="Sports News: " width="238" height="15" border="0"></a><br><div class="cnnMathis.inSections">';
s += '';
s += '&#8226;&nbsp;<a href="/cnnsi/golfonline/2003/us_open/news/2003/06/12/open_thursday_ap">Woods eyes third U.S. Open title</a><br>';
s += '&#8226;&nbsp;<a href="/cnnsi/basketball/news/2003/06/12/jordan_ruling_ap">Judge denies Jordan&#039;s former lover $5M payoff</a><br>';
s += '		</div></td>';
s += '		<td width="238"><a href="/money/"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/business.gif" alt="Business News: " width="238" height="15" border="0"></a><br><div class="cnnMathis.inSections">';
s += '&#8226;&nbsp;<a href="/money/2003/06/12/pf/saving/duppies/index.htm">Here come the "Duppies"</a><br>';
s += '&#8226;&nbsp;<a href="/money/2003/06/12/technology/oracle/index.htm">Oracle beats estimates</a><br>';
s += '		</div></td>';
s += '	</tr>';
s += '</table>';
s += '		</td>';
s += '		<td><img src="http://i.cnn.net/cnn/images/1.gif" width="10" hspace="0" vspace="0" alt=""></td>';
s += '		<td valign="top">';
s += '		<div><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/px_c00.gif" alt="" width="267" height="2"></div>';
s += '				';
s += '<table width="267" border="0" cellpadding="0" cellspacing="0">';
s += '	<tr>';
s += '		<td width="173" bgcolor="#003366"><div class="cnnBlueBoxHeader"><span class="cnnBigPrint"><b>WATCH CNN TV</b></span></div></td>';
s += '		<td width="25" class="cnnBlueBoxHeader" align="right"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/misc/diagonal.gif" width="25" height="19" alt=""></td>';
s += '		<td width="69" class="cnnBlueBoxTab" align="right" bgcolor="#336699"><a href="/CNN/Programs/"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/tv.schedule.gif" alt="On CNN TV" border="0" width="65" height="11" hspace="2" vspace="2" align="right"></a></td>';
s += '	</tr>';
s += '</table>';
s += '<table width="267" bgcolor="#EEEEEE" border="0" cellpadding="4" cellspacing="0">';
s += '	<tr valign="top">';
s += '		<td><a href="/CNN/Programs/american.morning/"><img src="http://i.cnn.net/cnn/CNN/Programs/includes/showbox/images/2003/05/tz.hemmer.jpg" alt="American Morning, 7 a.m. ET" width="65" height="49" border="0" align="right"></a><a href="/CNN/Programs/american.morning/"><b>American Morning (7 a.m. ET):</b></a> Tomorrow, singer Carnie Wilson talks about her new book, "Im Still Hungry."';
s += '		</td>';
s += '	</tr>';
s += '</table>';
s += '';
s += '<!--';
s += '[[table width="267" border="0" cellpadding="0" cellspacing="0"]]';
s += '[[tr]][[td width="173" bgcolor="#003366"]][[div class="cnnBlueBoxHeader"]][[span class="cnnBigPrint"]][[b]]WATCH CNN TV[[/b]][[/span]][[/div]][[/td]][[td width="25" class="cnnBlueBoxHeader" align="right"]][[img src="http://i.a.cnn.net/cnn/.element/img/1.0/misc/diagonal.gif" width="25" height="19" alt=""]][[/td]][[td width="69" class="cnnBlueBoxTab" align="right" bgcolor="#336699"]][[a href="/CNN/Programs/"]][[img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/tv.schedule.gif" alt="On CNN TV" border="0" width="65" height="11" hspace="2" vspace="2" align="right"]][[/a]][[/td]][[/tr]][[/table]][[table width="267" bgcolor="#EEEEEE" border="0" cellpadding="4" cellspacing="0"]][[tr valign="top"]][[td]]';
s += '[[img src="http://i.cnn.net/cnn/2003/images/05/31/tz.bw.jpg" alt="" width="65" height="49" border="0" align="right"]]';
s += '	';
s += '[[b]] CNN Presents: The Hunt for Eric Robert Rudolph (8 p.m. ET)[[/b]][[br]]Latest on his capture.';
s += '					[[/td]]';
s += '				[[/tr]]';
s += '			[[/table]]';
s += '-->';
s += '';
s += '				<div><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="1" height="10"></div>	';
s += '';
s += '';
s += '';
s += '';
s += '';
s += '';
s += '				<div><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/px_c00.gif" alt="" width="267" height="2"></div>';
s += '				<table width="267" border="0" cellpadding="0" cellspacing="0">';
s += '					<tr>';
s += '						<td width="184" bgcolor="#003366"><div class="cnnBlueBoxHeader"><span class="cnnBigPrint"><b>ANALYSIS</b></span></div></td>';
s += '						<td width="25" class="cnnBlueBoxHeader" align="right"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/misc/diagonal.gif" width="25" height="19" alt=""></td>';
s += '						<td width="58" class="cnnBlueBoxTab" align="right" bgcolor="#336699"><a href="/US"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/superlinks/us.gif" alt="U.S. News" border="0" width="54" height="11" hspace="2" vspace="2" align="right"></a></td>';
s += '					</tr>';
s += '				</table>';
s += '				<table width="267" bgcolor="#EEEEEE" border="0" cellpadding="4" cellspacing="0">';
s += '					<tr valign="top">';
s += '						<td>';
s += '<a href="/2003/US/06/12/nyt.safire/index.html"><img src="http://i.a.cnn.net/cnn/2003/US/06/12/nyt.safire/tz.stewart.jpg" alt="Fight It, Martha" width="65" height="49" border="0" align="right"></a>';
s += '';
s += '';
s += '<span class="cnnBodyText" style="font-weight:bold;color:#000;">NYTimes: </span> <a href="/2003/US/06/12/nyt.safire/index.html">Fight It, Martha</a><br>';
s += 'William Safire: I hope Martha Stewart beats this bum rap';
s += '';
s += '';
s += '';
s += '';
s += '					</td>';
s += '				</tr>';
s += '			</table>';
s += '			<div><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="1" height="10"></div>';
s += '				<div><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/px_c00.gif" alt="" width="267" height="2"></div>';
s += '				<table width="267" border="0" cellpadding="0" cellspacing="0">';
s += '					<tr>';
s += '						<td width="164" bgcolor="#003366"><div class="cnnBlueBoxHeader"><span class="cnnBigPrint"><b>OFFBEAT</b></span></div></td>';
s += '						<td width="25" class="cnnBlueBoxHeader" align="right"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/misc/diagonal.gif" width="25" height="19" alt=""></td>';
s += '						<td width="78" class="cnnBlueBoxTab" align="right" bgcolor="#336699"><a href="/offbeat"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/superlinks/offbeat.gif" alt="more offbeat" width="74" height="11" border="0" hspace="2" vspace="2" align="right"></a></td>';
s += '					</tr>';
s += '				</table>';
s += '				<table width="267" bgcolor="#DDDDDD" border="0" cellpadding="4" cellspacing="0">';
s += '					<tr valign="top">';
s += '						<td>';
s += '<a href="/2003/HEALTH/06/12/offbeat.china.sperm.ap/index.html"><img src="http://i.a.cnn.net/cnn/2003/HEALTH/06/12/offbeat.china.sperm.ap/tz.china.sperm.jpg" alt="Waiting list" width="65" height="49" border="0" align="right"></a>';
s += '	';
s += ' <a href="/2003/HEALTH/06/12/offbeat.china.sperm.ap/index.html">Waiting list</a><br>';
s += 'Chinas "smart sperm" bank needs donors';
s += '					</td>';
s += '				</tr>';
s += '			</table>';
s += '			<div><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="1" height="10"></div>';
s += '';
s += '			<table width="267" bgcolor="#999999" border="0" cellpadding="0" cellspacing="0">';
s += '				<tr>';
s += '					<td>';
s += '						<table width="100%" border="0" cellpadding="4" cellspacing="1">';
s += '							<tr>';
s += '								<td bgcolor="#EEEEEE" class="cnnMainWeatherBox"><a name="weatherBox"></a>';
s += '';
s += '';
s += '';
s += '';
s += '';
s += '';
s += '<table width="257" border="0" cellpadding="1" cellspacing="0">';
s += '<form method="get" action="http://weather.cnn.com/weather/search" style="margin: 0px">';
s += '<input type="hidden" name="mode" value="hplwp">';
s += '  <tr>';
s += '    <td bgcolor="#FFFFFF"><table width="255" bgcolor="#EAEFF4" border="0" cellpadding="4" cellspacing="0">';
s += '        <tr>';
s += '          <td colspan="2" class="cnnWEATHERrow">&nbsp;<span class="cnnBigPrint">WEATHER</span></td>';
s += '        </tr>';
s += '        <tr>';
s += '          <td colspan="2" class="cnnBodyText">Get your hometown weather on the home page! <b>Enter city name or U.S. Zip Code:</b></td>';
s += '        </tr>';
s += '        <tr>';
s += '          <td><input class="cnnFormText" type="text" size="12" name="wsearch" value="" style="width:100px;"></td>';
s += '          <td><input class="cnnNavButton" type="submit" value="PERSONALIZE"></td>';
s += '        </tr>';
s += '        <tr>';
s += '          <td class="cnnBodyText" colspan="2">Or <a href="javascript:CNN_openPopup("http://weather.cnn.com/weather/select.popup/content2.jsp?mode=hplwp", "weather", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,width=260,height=250")"><b>select location from a list</b></a></td>';
s += '        </tr>';
s += '    </table></td>';
s += '  </tr>';
s += '</form>';
s += '</table>';
s += '';
s += '';
s += '';
s += '								</td>';
s += '							</tr>';
s += '							<tr>';
s += '								<td bgcolor="#EEEEEE">';
s += '									<table width="100%" border="0" cellpadding="0" cellspacing="2">';
s += '										<tr>';
s += '											<td><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/quickvote.gif" alt="Quick Vote" width="107" height="24" border="0"></td>';
s += '											<td width="88" align="right"><!-- ad home/quickvote/sponsor.88x31 -->';
s += '<!-- ad commented while aol investigates 3/31/03 5:40 a.m. lk -->';
s += '<a href="http://ar.atwola.com/link/93101912/aol"><img src="http://ar.atwola.com/image/93101912/aol" alt="Click Here" width="88" height="31" border="0" hspace="0" vspace="0"></a>';
s += '</td>';
s += '										</tr>';
s += '									</table>';
s += '<table width="100%" cellspacing="0" cellpadding="1" border="0"><form target="popuppoll" method="post" action="http://polls.cnn.com/poll">';
s += '<INPUT TYPE=HIDDEN NAME="poll_id" VALUE="3966">';
s += '<tr><td colspan="2" align="left"><span class="cnnBodyText">Should an international peacekeeping force be sent to the Mideast?<br></span></td></tr>';
s += '<tr valign="top">';
s += '<td><span class="cnnBodyText">Yes</span>';
s += '</td><td align="right"><input value="1" type="radio" name="question_1"></td></tr>';
s += '<tr valign="top">';
s += '<td><span class="cnnBodyText">No</span>';
s += '</td><td align="right"><input value="2" type="radio" name="question_1"></td></tr>';
s += '<!-- /end Question 1 -->';
s += '<tr>';
s += '<td colspan="2">';
s += '<table width="100%" cellspacing="0" cellpadding="0" border="0"><tr><td><span class="cnnInterfaceLink"><nobr><a href="javascript:CNN_openPopup("/POLLSERVER/results/3966.html","popuppoll","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,width=510,height=400")">VIEW RESULTS</a></nobr></span></td>';
s += '<td align="right"><input class="cnnFormButton" onclick="CNN_openPopup("","popuppoll","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,width=510,height=400")" value="VOTE" type="SUBMIT"></td></tr></table></td></tr>';
s += '</form></table>';
s += '';
s += '								</td>';
s += '							</tr>';
s += '</table>';
s += '';
s += '					</td>';
s += '				</tr>';
s += '			</table>';
s += '		<!-- /right --></td>';
s += '	</tr>';
s += '	<tr>';
s += '		<td colspan="3" valign="bottom">		<img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/px_ccc.gif" alt="" width="483" height="1">		</td>';
s += '	</tr>';
s += '</table>';
s += '<table width="770" border="0" cellpadding="0" cellspacing="0" summary="Links to stories from CNN partners">';
s += '	<col width="10">';
s += '	<col width="250" align="left" valign="top">';
s += '	<col width="5">';
s += '	<col width="250" align="left" valign="top">';
s += '	<col width="5">';
s += '	<col width="250" align="left" valign="top">';
s += '	<tr><td colspan="6"><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="1" height="2"></td></tr>';
s += '	<tr valign="top">';
s += '		<td rowspan="6" width="10"><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="10" height="1"></td>';
s += '		<td colspan="3"><span class="cnnMenuText" style="font-size: 12px"><b style="color: #c00">From our Partners</b></span>';
s += '			<img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/icon_external.gif" alt=" External site icon " width="20" height="13" border="0" align="middle"></td>';
s += '		<td colspan="2"></td>';
s += '	</tr>';
s += '	<tr><td colspan="5"><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="1" height="2"></td></tr>';
s += '	<tr><td colspan="5" bgcolor="#CCCCCC"><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="1" height="1"></td></tr>';
s += '	<tr><td colspan="5"><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="1" height="2"></td></tr>';
s += '	<tr valign="top">';
s += '		<td class="cnnMathis.inSections" width="250">';
s += '<a href="/time/" target="new"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/partner_time.gif" alt="Time: " width="70" height="17" border="0"></a><br><div style="margin-top: 4px">	&#8226;&nbsp;<a target="new" href="/time/magazine/article/0,9171,1101030616-457387,00.html?CNN=yes">Where the Jobs Are</a><br>	&#8226;&nbsp;<a target="new" href="/time/magazine/article/0,9171,1101030616-457373,00.html?CNN=yes">Of Dogs and Men</a><br>	&#8226;&nbsp;<a target="new" href="/time/photoessays/gunmen/?CNN=yes">Photo Essay: Fighting the Peace</a><br></div><table border="0"><tr><td><img height="1" width="1" alt="" src="http://i.cnn.net/cnn/images/1.gif"/></td></tr><tr bgcolor="#dddddd"><td>&nbsp;&nbsp;<a target="new" href="/linkto/time.main.html">Subscribe to TIME</a>&nbsp;&nbsp;</td></tr></table>		</td>';
s += '		<td width="5"><br></td>';
s += '		<td class="cnnMathis.inSections" width="250">';
s += '<a href="/cnnsi/index.html?cnn=yes"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/partner_si.gif" alt="CNNsi.com: " width="138" height="17" border="0"></a><br><div style="margin-top: 4px">';
s += '&#8226;&nbsp;Marty Burns: <a target="new" href="/cnnsi/inside_game/marty_burns/news/2003/06/11/burns_game4/">Nets pull out all stops</a><br>';
s += '&#8226;&nbsp;Michael Farber: <a target="new" href="/cnnsi/inside_game/michael_farber/news/2003/06/11/farber_wrapup/">Sens look good for "04</a><br>';
s += '&#8226;&nbsp;Tim Layden: <a target="new" href="/cnnsi/inside_game/tim_layden/news/2003/06/11/layden_neuheisel/">NFL or bust for Neuheisel</a><br>';
s += '</div>';
s += '<table border="0"><tr><td><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="1" height="1"></td></tr><tr bgcolor="#dddddd"><td>&nbsp;&nbsp;<a href="http://subs.timeinc.net/CampaignHandler/si_cnnsi?source_id=19">Subscribe to Sports Illustrated</a>&nbsp;&nbsp;</td></tr></table>';
s += '		</td>';
s += '		<td width="5"><br></td>';
s += '		<td class="cnnMathis.inSections" width="250">';
s += '<a href="/linkto/nyt/main.banner.html" target="new"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/partners_nyt.gif" alt="New York Times: " width="105" height="17" border="0"></a><br><div style="margin-top: 4px">	&#8226;&nbsp;<a target="new" href="/linkto/nyt/story/1.0612.html">U.S. Widens Checks at Foreign Ports</a><br>	&#8226;&nbsp;<a target="new" href="/linkto/nyt/story/2.0612.html">Rumsfeld: Iran Developing Nuclear Arms</a><br>	&#8226;&nbsp;<a target="new" href="/linkto/nyt/story/3.0612.html">Vandalism, "Improvements" Mar Great Wall</a><br></div><table border="0"><tr><td><img height="1" width="1" alt="" src="http://i.cnn.net/cnn/images/1.gif"/></td></tr><tr bgcolor="#dddddd"><td>&nbsp;&nbsp;<a target="new" href="/linkto/nyt.main.html">Get 50% OFF the NY Times</a>&nbsp;&nbsp;</td></tr></table>		</td>';
s += '	</tr>';
s += '';
s += '</table>';
s += '<div><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="1" height="2"></div>';
s += '';
s += '<table width="770" border="0" cellpadding="0" cellspacing="0">';
s += '	<tr>';
s += '		<td width="10"><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="10" height="10"></td>';
s += '		<td width="760">';
s += '<!-- floor -->';
s += '';
s += '<table width="100%" border="0" cellpadding="0" cellspacing="0"><tr><td bgcolor="#999999"><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="1" height="1"></td></tr></table>';
s += '';
s += '<div><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="1" height="1"></div>';
s += '';
s += '<table width="100%" bgcolor="#DEDEDE" border="0" cellpadding="3" cellspacing="0">';
s += '	<tr> ';
s += '		<td><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="5" height="5"></td>';
s += '		<td><a href="http://edition.cnn.com/" class="cnnFormTextB" onClick="clickEdLink()" style="color:#000;">International Edition</a></td>';
s += '<form>';
s += '		<td><select title="CNN.com is available in different languages" class="cnnMenuText" name="languages" size="1" style="font-weight: bold; vertical-align: middle" onChange="if (this.options[selectedIndex].value != "") location.href=this.options[selectedIndex].value">';
s += '				<option value="" disabled selected>Languages</option>';
s += '				<option value="" disabled>---------</option>';
s += '				<option value="/cnnes/">Spanish</option>';
s += '				<option value="http://cnn.de/">German</option>';
s += '				<option value="http://cnnitalia.it/">Italian</option>';
s += '				<option value="http://www.joins.com/cnn/">Korean</option>';
s += '				<option value="http://arabic.cnn.com/">Arabic</option>';
s += '				<option value="http://www.CNN.co.jp/">Japanese</option>';
s += '			</select></td>';
s += '</form>';
s += '		<td><a href="/CNN/Programs/" class="cnnFormTextB" style="color:#000;">CNN TV</a></td>';
s += '		<td><a href="/CNNI/" class="cnnFormTextB" style="color:#000;">CNN International</a></td>';
s += '		<td><a href="/HLN/" class="cnnFormTextB" style="color:#000;">Headline News</a></td>';
s += '		<td><a href="/TRANSCRIPTS/" class="cnnFormTextB" style="color:#000;">Transcripts</a></td>';
s += '		<td><a href="/services/preferences/" title="Customize your CNN.com experience" class="cnnFormTextB" style="color:#000;">Preferences</a></td>';
s += '		<td><a href="/INDEX/about.us/" class="cnnFormTextB" style="color:#000;">About CNN.com</a></td>';
s += '	</tr>';
s += '</table>';
s += '';
s += '<div><img src="http://i.cnn.net/cnn/images/1.gif" alt="" width="1" height="1"></div>';
s += '';
s += '<table width="100%" bgcolor="#EFEFEF" border="0" cellpadding="4" cellspacing="0">';
s += '	<tr valign="top"> ';
s += '		<td style="padding-left:10px"><div class="cnnSectCopyright">';
s += '<b>&copy; 2003 Cable News Network LP, LLLP.</b><br>';
s += 'An AOL Time Warner Company. All Rights Reserved.<br>';
s += '<a href="/interactive_legal.html">Terms</a> under which this service is provided to you.<br>';
s += 'Read our <a href="/privacy.html">privacy guidelines</a>. <a href="/feedback/">Contact us</a>.';
s += '		</div></td>';
s += '		<td align="right"><table border="0" cellpadding="4" cellspacing="0">';
s += '				<tr> ';
s += '					<td rowspan="2" align="middle"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/sect/SEARCH/dotted.line.gif" alt="" width="7" height="46"></td>';
s += '					<td><img src="http://i.a.cnn.net/cnn/.element/img/1.0/misc/icon.external.links.gif" alt="external link" width="20" height="13"></td>';
s += '					<td><div class="cnnSectExtSites">All external sites will open in a new browser.<br>';
s += '							CNN.com does not endorse external sites.</div></td>';
s += '					<td rowspan="2" align="middle"><img src="http://i.a.cnn.net/cnn/.element/img/1.0/sect/SEARCH/dotted.line.gif" alt="" width="7" height="46"></td>';
s += '					<td rowspan="2"><!-- home/powered_by/sponsor.88x31 -->';
s += '<script language="JavaScript1.1">';
s += '<!--';
s += 'adSetTarget("_top");';
s += 'htmlAdWH( (new Array(93103308,93103308,93103308,93103308))[document.adoffset||0] , 88, 31);';
s += '//-->';
s += '</script><noscript><a href="http://ar.atwola.com/link/93103308/aol" target="_top"><img src="http://ar.atwola.com/image/93103308/aol" alt="Click here for our advertiser" width="88" height="31" border="0"></a></noscript>';
s += '</td>';
s += '				</tr>';
s += '				<tr valign="top"> ';
s += '					<td><img src="http://i.a.cnn.net/cnn/.element/img/1.0/main/icon_premium.gif" alt=" Premium content icon " width="9" height="11"></td>';
s += '					<td><span class="cnnSectExtSites">Denotes premium content.</span></td>';
s += '				</tr>';
s += '			</table></td>';
s += '	</tr>';
s += '</table>';
s += '';
s += '<!-- /floor --></td>';
s += '	</tr>';
s += '</table>';
s += '';
s += '';
s += '';
s += '<!-- popunder ad generic/popunder_launch.720x300 -->';
s += '<script language="JavaScript1.1" type="text/javascript">';
s += '<!--';
s += 'if (document.adPopupFile) {';
s += '	if (document.adPopupInterval == null) {';
s += '		document.adPopupInterval = "0";';
s += '	}';
s += '	if (document.adPopunderInterval == null) {';
s += '		document.adPopunderInterval = document.adPopupInterval;';
s += '	}';
s += '	if (document.adPopupDomain != null) {';
s += '		adSetPopDm(document.adPopupDomain);';
s += '	}';
s += '	adSetPopupWH("93162673", "720", "300", document.adPopupFile, document.adPopunderInterval, 20, 50, -1);';
s += '}';
s += '// -->';
s += '</script>';
s += '	';
s += '<!-- home/bottom.eyeblaster -->';
s += '<script language="JavaScript1.1" type="text/javascript">';
s += '<!--';
s += 'var MacPPC = (navigator.platform == "MacPPC") ? true : false;';
s += 'if (!MacPPC) {';
s += 'adSetType("J");';
s += 'htmlAdWH( (new Array(93137910,93137910,93137910,93137910))[document.adoffset||0], 101, 1);';
s += 'adSetType("");';
s += '}';
s += '// -->';
s += '</script>';
s += '';
s += '<script language="JavaScript1.1" src="http://ar.atwola.com/file/adsEnd.js"></script>';
s += '';
s += '<img src="/cookie.crumb" alt="" width="1" height="1">';
s += '<!--include virtual="/virtual/2002/main/survey.html"-->';
s += '</body>';
s += '</html>';

return s;
}
},

/**
*  File Name:          regress__209919.js
* Date:    19 June 2003
* SUMMARY: Testing regexp submatches with quantifiers
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=209919
*/
test_regress__209919 : function() {
var i = 0;
//var BUGNUMBER = 209919;
var summary = 'Testing regexp submatches with quantifiers';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();


/*
* Waldemar: "ECMA-262 15.10.2.5, third algorithm, step 2.1 states that
* once the minimum repeat count (which is 0 for *, 1 for +, etc.) has
* been satisfied, an atom being repeated must not match the empty string."
*
* In this example, the minimum repeat count is 0, so the last thing the
* capturing parens is permitted to contain is the 'a'. It may NOT go on
* to capture the '' at the $ position of 'a', even though '' satifies
* the condition b*
*
*/
status = this.inSection(1);
string = 'a';
pattern = /(a|b*)*/;
actualmatch = string.match(pattern);
expectedmatch = Array(string, 'a');
addThis();


/*
* In this example, the minimum repeat count is 5, so the capturing parens
* captures the 'a', then goes on to capture the '' at the $ position of 'a'
* 4 times before it has to stop. Therefore the last thing it contains is ''.
*/
status = this.inSection(2);
string = 'a';
pattern = /(a|b*){5,}/;
actualmatch = string.match(pattern);
expectedmatch = Array(string, '');
addThis();


/*
* Reduction of the above examples to contain only the condition b*
* inside the capturing parens. This can be even harder to grasp!
*
* The global match is the '' at the ^ position of 'a', but the parens
* is NOT permitted to capture it since the minimum repeat count is 0!
*/
status = this.inSection(3);
string = 'a';
pattern = /(b*)*/;
actualmatch = string.match(pattern);
expectedmatch = Array('', undefined);
addThis();


/*
* Here we have used the + quantifier (repeat count 1) outside the parens.
* Therefore the parens must capture at least once before stopping, so it
* does capture the '' this time -
*/
status = this.inSection(4);
string = 'a';
pattern = /(b*)+/;
actualmatch = string.match(pattern);
expectedmatch = Array('', '');
addThis();


/*
* More complex examples -
*/
pattern = /^\-?(\d{1,}|\.{0,})*(\,\d{1,})?$/;

status = this.inSection(5);
string = '100.00';
actualmatch = string.match(pattern);
expectedmatch = Array(string, '00', undefined);
addThis();

status = this.inSection(6);
string = '100,00';
actualmatch = string.match(pattern);
expectedmatch = Array(string, '100', ',00');
addThis();

status = this.inSection(7);
string = '1.000,00';
actualmatch = string.match(pattern);
expectedmatch = Array(string, '000', ',00');
addThis();




//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------



function addThis()
{
statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);
this.testRegExp(statusmessages, patterns, strings, actualmatches, expectedmatches);
//exitFunc ('test');
//}

},

/**
*  File Name:          regress__216591.js
* Date:    19 August 2003
* SUMMARY: Regexp conformance test
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=216591
*/
test_regress__216591 : function() {
var i = 0;
//var BUGNUMBER = 216591;
var summary = 'Regexp conformance test';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();


status = this.inSection(1);
string = 'a {result.data.DATA} b';
pattern = /\{(([a-z0-9\-_]+?\.)+?)([a-z0-9\-_]+?)\}/i;
actualmatch = string.match(pattern);
expectedmatch = Array('{result.data.DATA}', 'result.data.', 'data.', 'DATA');
addThis();

/*
* Add a global flag to the regexp. In Perl 5, this gives the same results as above. Compare:
*
* [ ] perl -e '"a {result.data.DATA} b" =~ /\{(([a-z0-9\-_]+?\.)+?)([a-z0-9\-_]+?)\}/i;  print("$&, $1, $2, $3");'
* {result.data.DATA}, result.data., data., DATA
*
* [ ] perl -e '"a {result.data.DATA} b" =~ /\{(([a-z0-9\-_]+?\.)+?)([a-z0-9\-_]+?)\}/gi; print("$&, $1, $2, $3");'
* {result.data.DATA}, result.data., data., DATA
*
*
* But in JavaScript, there will no longer be any sub-captures:
*/
status = this.inSection(2);
string = 'a {result.data.DATA} b';
pattern = /\{(([a-z0-9\-_]+?\.)+?)([a-z0-9\-_]+?)\}/gi;
actualmatch = string.match(pattern);
expectedmatch = Array('{result.data.DATA}');
addThis();




//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------



function addThis()
{
statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);
this.testRegExp(statusmessages, patterns, strings, actualmatches, expectedmatches);
//exitFunc ('test');
//}

},

/**
*  File Name:          regress__220367__001.js
* Date:    26 September 2003
* SUMMARY: Regexp conformance test
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=220367
*/
test_regress__220367__001 : function() {
var i = 0;
//var BUGNUMBER = 220367;
var summary = 'Regexp conformance test';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();


status = this.inSection(1);
string = 'a';
pattern = /(a)|(b)/;
actualmatch = string.match(pattern);
expectedmatch = Array(string, 'a', undefined);
addThis();

status = this.inSection(2);
string = 'b';
pattern = /(a)|(b)/;
actualmatch = string.match(pattern);
expectedmatch = Array(string, undefined, 'b');
addThis();



//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------



function addThis()
{
statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
// printStatus (summary);
this.testRegExp(statusmessages, patterns, strings, actualmatches, expectedmatches);
//exitFunc ('test');
//}

},

/**
*  File Name:          regress__223273.js
* Date:    23 October 2003
* SUMMARY: Unescaped, unbalanced parens in a regexp should cause SyntaxError.
*
* The same would also be true for unescaped, unbalanced brackets or braces
* if we followed the ECMA-262 Ed. 3 spec on this. But it was decided for
* backward compatibility reasons to follow Perl 5, which permits
*
* 1. an unescaped, unbalanced right bracket ]
* 2. an unescaped, unbalanced left brace    {
* 3. an unescaped, unbalanced right brace   }
*
* If any of these should occur, Perl treats each as a literal
* character.  Therefore we permit all three of these cases, even
* though not ECMA-compliant.  Note Perl errors on an unescaped,
* unbalanced left bracket; so will we.
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=223273
*/
test_regress__223273 : function() {
var UBound = 0;
//var BUGNUMBER = 223273;
var summary = 'Unescaped, unbalanced parens in regexp should be a SyntaxError';
var TEST_PASSED = 'SyntaxError';
var TEST_FAILED = 'Generated an error, but NOT a SyntaxError!';
var TEST_FAILED_BADLY = 'Did not generate ANY error!!!';
var CHECK_PASSED = 'Should not generate an error';
var CHECK_FAILED = 'Generated an error!';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


/*
* All the following contain unescaped, unbalanced parens and
* should generate SyntaxErrors. That's what we're testing for.
*
* To allow the test to compile and run, we have to hide the errors
* inside eval strings, and check they are caught at run-time.
*
* Inside such strings, remember to escape any escape character!
*/
status = this.inSection(1);
testThis(' /(/ ');

status = this.inSection(2);
testThis(' /)/ ');

status = this.inSection(3);
testThis(' /(abc\\)def(g/ ');

status = this.inSection(4);
testThis(' /\\(abc)def)g/ ');


/*
* These regexp patterns are correct and should not generate
* any errors. Note we use checkThis() instead of testThis().
*/
status = this.inSection(5);
checkThis(' /\\(/ ');

status = this.inSection(6);
checkThis(' /\\)/ ');

status = this.inSection(7);
checkThis(' /(abc)def\\(g/ ');

status = this.inSection(8);
checkThis(' /(abc\\)def)g/ ');

status = this.inSection(9);
checkThis(' /(abc(\\))def)g/ ');

status = this.inSection(10);
checkThis(' /(abc([x\\)yz]+)def)g/ ');



/*
* Unescaped, unbalanced left brackets should be a SyntaxError
*/
status = this.inSection(11);
testThis(' /[/ ');

status = this.inSection(12);
testThis(' /[abc\\]def[g/ ');


/*
* We permit unescaped, unbalanced right brackets, as does Perl.
* No error should result, even though this is not ECMA-compliant.
* Note we use checkThis() instead of testThis().
*/
status = this.inSection(13);
checkThis(' /]/ ');

status = this.inSection(14);
checkThis(' /\\[abc]def]g/ ');


/*
* These regexp patterns are correct and should not generate
* any errors. Note we use checkThis() instead of testThis().
*/
status = this.inSection(15);
checkThis(' /\\[/ ');

status = this.inSection(16);
checkThis(' /\\]/ ');

status = this.inSection(17);
checkThis(' /[abc]def\\[g/ ');

status = this.inSection(18);
checkThis(' /[abc\\]def]g/ ');

status = this.inSection(19);
checkThis(' /(abc[\\]]def)g/ ');

status = this.inSection(20);
checkThis(' /[abc(x\\]yz+)def]g/ ');



/*
* Run some tests for unbalanced braces. We again follow Perl, and
* thus permit unescaped unbalanced braces - both left and right,
* even though this is not ECMA-compliant.
*
* Note we use checkThis() instead of testThis().
*/
status = this.inSection(21);
checkThis(' /abc{def/ ');

status = this.inSection(22);
checkThis(' /abc}def/ ');

status = this.inSection(23);
checkThis(' /a{2}bc{def/ ');

status = this.inSection(24);
checkThis(' /a}b{3}c}def/ ');


/*
* These regexp patterns are correct and should not generate
* any errors. Note we use checkThis() instead of testThis().
*/
status = this.inSection(25);
checkThis(' /abc\\{def/ ');

status = this.inSection(26);
checkThis(' /abc\\}def/ ');

status = this.inSection(27);
checkThis(' /a{2}bc\\{def/ ');

status = this.inSection(28);
checkThis(' /a\\}b{3}c\\}def/ ');




//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------




/*
* Invalid syntax should generate a SyntaxError
*/
function testThis(sInvalidSyntax)
{
expect = TEST_PASSED;
actual = TEST_FAILED_BADLY;

try
{
eval(sInvalidSyntax);
}
catch(e)
{
if (e instanceof SyntaxError)
actual = TEST_PASSED;
else
actual = TEST_FAILED;
}

statusitems[UBound] = status;
expectedvalues[UBound] = expect;
actualvalues[UBound] = actual;
UBound++;
}


/*
* Valid syntax shouldn't generate any errors
*/
function checkThis(sValidSyntax)
{
expect = CHECK_PASSED;
actual = CHECK_PASSED;

try
{
eval(sValidSyntax);
}
catch(e)
{
actual = CHECK_FAILED;
}

statusitems[UBound] = status;
expectedvalues[UBound] = expect;
actualvalues[UBound] = actual;
UBound++;
}


//function test()
//{
//enterFunc('test');
//printBugNumber(BUGNUMBER);
//printStatus(summary);

for (var i=0; i<UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//exitFunc ('test');
//}

},

/**
*  File Name:          regress__223535.js
* Date:    24 October 2003
* SUMMARY: Testing regexps with empty alternatives
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=223535
*/
test_regress__223535 : function() {
var i = 0;
//var BUGNUMBER = 223535;
var summary = 'Testing regexps with empty alternatives';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();


string = 'a';
status = this.inSection(1);
pattern = /a|/;
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();

status = this.inSection(2);
pattern = /|a/;
actualmatch = string.match(pattern);
expectedmatch = Array('');
addThis();

status = this.inSection(3);
pattern = /|/;
actualmatch = string.match(pattern);
expectedmatch = Array('');
addThis();

status = this.inSection(4);
pattern = /(a|)/;
actualmatch = string.match(pattern);
expectedmatch = Array('a', 'a');
addThis();

status = this.inSection(5);
pattern = /(a||)/;
actualmatch = string.match(pattern);
expectedmatch = Array('a', 'a');
addThis();

status = this.inSection(6);
pattern = /(|a)/;
actualmatch = string.match(pattern);
expectedmatch = Array('', '');
addThis();

status = this.inSection(7);
pattern = /(|a|)/;
actualmatch = string.match(pattern);
expectedmatch = Array('', '');
addThis();



//-------------------------------------------------------------------------------------------------
//test();
//-------------------------------------------------------------------------------------------------



function addThis()
{
statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);
this.testRegExp(statusmessages, patterns, strings, actualmatches, expectedmatches);
//exitFunc ('test');
//}

},

/**
*  File Name:          regress__224676.js
* Date:    04 November 2003
* SUMMARY: Testing regexps with various disjunction + character class patterns
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=224676
*/
test_regress__224676 : function() {
var i = 0;
//var BUGNUMBER = 224676;
var summary = 'Regexps with various disjunction + character class patterns';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();


string = 'ZZZxZZZ';
status = this.inSection(1);
pattern = /[x]|x/;
actualmatch = string.match(pattern);
expectedmatch = Array('x');
addThis();

status = this.inSection(2);
pattern = /x|[x]/;
actualmatch = string.match(pattern);
expectedmatch = Array('x');
addThis();


string = 'ZZZxbZZZ';
status = this.inSection(3);
pattern = /a|[x]b/;
actualmatch = string.match(pattern);
expectedmatch = Array('xb');
addThis();

status = this.inSection(4);
pattern = /[x]b|a/;
actualmatch = string.match(pattern);
expectedmatch = Array('xb');
addThis();

status = this.inSection(5);
pattern = /([x]b|a)/;
actualmatch = string.match(pattern);
expectedmatch = Array('xb', 'xb');
addThis();

status = this.inSection(6);
pattern = /([x]b|a)|a/;
actualmatch = string.match(pattern);
expectedmatch = Array('xb', 'xb');
addThis();

status = this.inSection(7);
pattern = /^[x]b|a/;
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();


string = 'xb';
status = this.inSection(8);
pattern = /^[x]b|a/;
actualmatch = string.match(pattern);
expectedmatch = Array('xb');
addThis();


string = 'ZZZxbZZZ';
status = this.inSection(9);
pattern = /([x]b)|a/;
actualmatch = string.match(pattern);
expectedmatch = Array('xb', 'xb');
addThis();

status = this.inSection(10);
pattern = /()[x]b|a/;
actualmatch = string.match(pattern);
expectedmatch = Array('xb', '');
addThis();

status = this.inSection(11);
pattern = /x[b]|a/;
actualmatch = string.match(pattern);
expectedmatch = Array('xb');
addThis();

status = this.inSection(12);
pattern = /[x]{1}b|a/;
actualmatch = string.match(pattern);
expectedmatch = Array('xb');
addThis();

status = this.inSection(13);
pattern = /[x]b|a|a/;
actualmatch = string.match(pattern);
expectedmatch = Array('xb');
addThis();

status = this.inSection(14);
pattern = /[x]b|[a]/;
actualmatch = string.match(pattern);
expectedmatch = Array('xb');
addThis();

status = this.inSection(15);
pattern = /[x]b|a+/;
actualmatch = string.match(pattern);
expectedmatch = Array('xb');
addThis();

status = this.inSection(16);
pattern = /[x]b|a{1}/;
actualmatch = string.match(pattern);
expectedmatch = Array('xb');
addThis();

status = this.inSection(17);
pattern = /[x]b|(a)/;
actualmatch = string.match(pattern);
expectedmatch = Array('xb', undefined);
addThis();

status = this.inSection(18);
pattern = /[x]b|()a/;
actualmatch = string.match(pattern);
expectedmatch = Array('xb', undefined);
addThis();

status = this.inSection(19);
pattern = /[x]b|^a/;
actualmatch = string.match(pattern);
expectedmatch = Array('xb');
addThis();

status = this.inSection(20);
pattern = /a|[^b]b/;
actualmatch = string.match(pattern);
expectedmatch = Array('xb');
addThis();

status = this.inSection(21);
pattern = /a|[^b]{1}b/;
actualmatch = string.match(pattern);
expectedmatch = Array('xb');
addThis();


string = 'hallo\";'
status = this.inSection(22);
pattern = /^((\\[^\x00-\x1f]|[^\x00-\x1f"\\])*)"/;
actualmatch = string.match(pattern);
expectedmatch = Array('hallo"', 'hallo', 'o');
addThis();




//-------------------------------------------------------------------------------------------------
//test();
//-------------------------------------------------------------------------------------------------



function addThis()
{
statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);
this.testRegExp(statusmessages, patterns, strings, actualmatches, expectedmatches);
//exitFunc ('test');
//}

},

/**
*  File Name:          regress__225289.js
* Date:    10 November 2003
* SUMMARY: Testing regexps with complementary alternatives
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=225289
*/
test_regress__225289 : function() {
var i = 0;
//var BUGNUMBER = 225289;
var summary = 'Testing regexps with complementary alternatives';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();


// this pattern should match any string!
pattern = /a|[^a]/;

status = this.inSection(1);
string = 'a';
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();

status = this.inSection(2);
string = '';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(3);
string = '()';
actualmatch = string.match(pattern);
expectedmatch = Array('(');
addThis();


pattern = /(a|[^a])/;

status = this.inSection(4);
string = 'a';
actualmatch = string.match(pattern);
expectedmatch = Array('a', 'a');
addThis();

status = this.inSection(5);
string = '';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(6);
string = '()';
actualmatch = string.match(pattern);
expectedmatch = Array('(', '(');
addThis();


pattern = /(a)|([^a])/;

status = this.inSection(7);
string = 'a';
actualmatch = string.match(pattern);
expectedmatch = Array('a', 'a', undefined);
addThis();

status = this.inSection(8);
string = '';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(9);
string = '()';
actualmatch = string.match(pattern);
expectedmatch = Array('(', undefined, '(');
addThis();


// note this pattern has one non-capturing parens
pattern = /((?:a|[^a])*)/g;

status = this.inSection(10);
string = 'a';
actualmatch = string.match(pattern);
expectedmatch = Array('a', ''); // see bug 225289 comment 6
addThis();

status = this.inSection(11);
string = '';
actualmatch = string.match(pattern);
expectedmatch = Array(''); // see bug 225289 comment 9
addThis();

status = this.inSection(12);
string = '()';
actualmatch = string.match(pattern);
expectedmatch = Array('()', ''); // see bug 225289 comment 6
addThis();




//-------------------------------------------------------------------------------------------------
//test();
//-------------------------------------------------------------------------------------------------



function addThis()
{
statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);
this.testRegExp(statusmessages, patterns, strings, actualmatches, expectedmatches);
//exitFunc ('test');
//}

},

/**
*  File Name:          regress__225343.js
* Date:    11 November 2003
* SUMMARY: Testing regexp character classes and the case-insensitive flag
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=225343
*/
test_regress__225343 : function() {
var i = 0;
//var BUGNUMBER = 225343;
var summary = 'Testing regexp character classes and the case-insensitive flag';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();


status = this.inSection(1);
string = 'a';
pattern = /[A]/i;
actualmatch = string.match(pattern);
expectedmatch = Array('a');
addThis();

status = this.inSection(2);
string = 'A';
pattern = /[a]/i;
actualmatch = string.match(pattern);
expectedmatch = Array('A');
addThis();

status = this.inSection(3);
string = '123abc123';
pattern = /([A-Z]+)/i;
actualmatch = string.match(pattern);
expectedmatch = Array('abc', 'abc');
addThis();

status = this.inSection(4);
string = '123abc123';
pattern = /([A-Z])+/i;
actualmatch = string.match(pattern);
expectedmatch = Array('abc', 'c');
addThis();

status = this.inSection(5);
string = 'abc@test.com';
pattern = /^[-!#$%&\'*+\.\/0-9=?A-Z^_`{|}~]+@([-0-9A-Z]+\.)+([0-9A-Z]){2,4}$/i;
actualmatch = string.match(pattern);
expectedmatch = Array('abc@test.com', 'test.', 'm');
addThis();



//-------------------------------------------------------------------------------------------------
//test();
//-------------------------------------------------------------------------------------------------



function addThis()
{
statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);
this.testRegExp(statusmessages, patterns, strings, actualmatches, expectedmatches);
//exitFunc ('test');
//}

},

/**
*  File Name:          regress__24712.js
*/
test_regress__24712 : function() {
//test();

//function test()
//{
//enterFunc ("test");

//printBugNumber (24712);

var re = /([\S]+([ \t]+[\S]+)*)[ \t]*=[ \t]*[\S]+/;
var result = re.exec("Course_Creator = Test") + '';

this.reportCompare('Course_Creator = Test,Course_Creator,', result, 'exec() returned null');

//exitFunc ("test");

//}

},

/**
*  File Name:          regress__285219.js
*/
test_regress__285219 : function() {
//var BUGNUMBER = 285219;
var summary = 'Do not crash on RangeError: reserved slot out of range';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var o = {hi: 'there'};
eval("var r = /re(1)(2)(3)/g", o);

this.reportCompare(expect, actual, summary);

},

/**
*  File Name:          regress__28686.js
*/
test_regress__28686 : function() {
//test();

//function test()
//{
//enterFunc ("test");

//printBugNumber (28686);

var str = 'foo "bar" baz';
this.reportCompare ('foo \\"bar\\" baz', str.replace(/([\'\"])/g, "\\$1"),
"str.replace failed.");

//exitFunc ("test");

//}

},

/**
*  File Name:          regress__289669.js
*/
test_regress__289669 : function() {
//var BUGNUMBER = 289669;
var summary = 'O(N^2) behavior on String.replace(/RegExp/, ...)';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);


var data = {X: [], Y:[]};

function replace(str) {
var stra=str.replace(new RegExp('<a>','g'),"<span id=\"neurodna\" style=\"background-color:blue\"/>");
stra=stra.replace(new RegExp('</a>','g'),"</span><br>");
}

function runTest(obj) {
for (var j = 1000; j <= 10000; j += 1000)
{
neurodna(obj,j);
}
}

function neurodna(obj, limit) {
var prepare="<go>";
for(var i=0;i<limit;i++) {
prepare += "<a>neurodna</a>";
}
prepare+="</go>";
var da1=new Date();
replace(prepare);
var da2=new Date();
data.X.push(limit);
data.Y.push(da2-da1);
obj.gc();
}

runTest(this);

var order = this.BigO(data);

var msg = '';
for (var p = 0; p < data.X.length; p++)
{
msg += '(' + data.X[p] + ', ' + data.Y[p] + '); ';
}
//printStatus(msg);
//printStatus('Order: ' + order);
this.reportCompare(true, order < 2, summary + ' BigO ' + order + ' < 2');

},

/**
*  File Name:          regress__307456.js
*/
test_regress__307456 : function() {
//var BUGNUMBER = 307456;
var summary = 'Do not Freeze with RegExp';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var data='<!---<->---->\n\n<><>--!!!<><><><><><>\n!!<>\n\n<>\n<><><><>!\n\n\n\n--\n--\n--\n\n--\n--\n\n\n-------\n--\n--\n\n\n--\n\n\n\n----\n\n\n\n--\n\n\n-\n\n\n-\n\n-\n\n-\n\n-\n-\n\n----\n\n-\n\n\n\n\n-\n\n\n\n\n\n\n\n\n-----\n\n\n-\n------\n-------\n\n----\n\n\n\n!\n\n\n\n\n\n\n\n!!!\n\n\n--------\n\n\n\n-\n\n\n-\n--\n\n----\n\n\n\n\n\n-\n\n\n----\n\n\n\n\n\n--------\n!\n\n\n\n\n-\n---\n--\n\n----\n\n-\n\n-\n\n-\n\n\n\n-----\n\n\n\n-\n\n\n-\n\n\n--\n-\n\n\n-\n\n----\n\n---\n\n---\n\n----\n\n\n\n---\n\n-++\n\n-------<>\n\n-!\n\n--\n\n----!-\n\n\n\n';

//printStatus(data);
data=data.replace(RegExp('<!--(\\n[^\\n]|[^-]|-[^-]|--[^>])*-->', 'g'), '');
//printStatus(data);

this.reportCompare(expect, actual, summary);

},

/**
*  File Name:          regress__309840.js
*/
test_regress__309840 : function() {
//var BUGNUMBER = 309840;
var summary = 'Treat / in a literal regexp class as valid';
var actual = 'No error';
var expect = 'No error';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

try
{
var re = eval('/[/]/');
}
catch(e)
{
actual = e.toString();
}

this.reportCompare(expect, actual, summary);

},

/**
*  File Name:          regress__311414.js
*/
test_regress__311414 : function() {
//var BUGNUMBER = 311414;
var summary = 'RegExp captured tail match should be O(N)';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

function q1(n) {
var c = [];
c[n] = 1;
c = c.join(" ");
var d = Date.now();
var e = c.match(/(.*)foo$/);
var f = Date.now();
return (f - d);
}

function q2(n) {
var c = [];
c[n] = 1;
c = c.join(" ");
var d = Date.now();
var e = /foo$/.test(c) && c.match(/(.*)foo$/);
var f = Date.now();
return (f - d);
}

var data1 = {X:[], Y:[]};
var data2 = {X:[], Y:[]};

for (var x = 500; x < 5000; x += 500)
{
var y1 = q1(x);
var y2 = q2(x);
data1.X.push(x);
data1.Y.push(y1);
data2.X.push(x);
data2.Y.push(y2);
this.gc();
}

var order1 = this.BigO(data1);
var order2 =  this.BigO(data2);

var msg = '';
for (var p = 0; p < data1.X.length; p++)
{
msg += '(' + data1.X[p] + ', ' + data1.Y[p] + '); ';
}
//printStatus(msg);
//printStatus('Order: ' + order1);
this.reportCompare(true, order1 < 2 , summary + ' BigO ' + order1 + ' < 2');

msg = '';
for (var p = 0; p < data2.X.length; p++)
{
msg += '(' + data2.X[p] + ', ' + data2.Y[p] + '); ';
}
//printStatus(msg);
//printStatus('Order: ' + order2);
this.reportCompare(true, order2 < 2 , summary + ' BigO ' + order2 + ' < 2');

},

/**
*  File Name:          regress__312351.js
*/
test_regress__312351 : function() {
//var BUGNUMBER = 312351;
var summary = 'Do not crash on RegExp(null)';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var x = RegExp(null);

this.reportCompare(expect, actual, summary);

},

/**
*  File Name:          regress__31316.js
* Date: 01 May 2001
*
* SUMMARY:  Regression test for Bugzilla bug 31316:
* "Rhino: Regexp matches return garbage"
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=31316
*/
test_regress__31316 : function() {
var i = 0;
//var BUGNUMBER = 31316;
var summary = 'Regression test for Bugzilla bug 31316';
var cnEmptyString = '';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();


status = this.inSection(1);
pattern = /<([^\/<>][^<>]*[^\/])>|<([^\/<>])>/;
string = '<p>Some<br />test</p>';
actualmatch = string.match(pattern);
expectedmatch = Array('<p>', undefined, 'p');
addThis();


//-------------------------------------------------------------------------------------------------
//test();
//-------------------------------------------------------------------------------------------------


function addThis()
{
statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
// printStatus (summary);
this.testRegExp(statusmessages, patterns, strings, actualmatches, expectedmatches);
// exitFunc ('test');
//}

},

/**
*  File Name:          regress__330684.js
*/
test_regress__330684 : function() {
//var BUGNUMBER = 330684;
var summary = 'Do not hang on RegExp';
var actual = 'Do not hang on RegExp';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var re = /^(?:(?:%[0-9A-Fa-f]{2})*[!\$&'\*-;=\?-Z_a-z]*)+$/;
var url = "http://tw.yimg.com/a/tw/wenchuan/cam_240x400_381615_030806_2.swf?clickTAG=javascript:VRECopenWindow(1)";

//printStatus(re.test(url));

this.reportCompare(expect, actual, summary);

},

/**
*  File Name:          regress__334158.js
*/
test_regress__334158 : function() {
//var BUGNUMBER = 334158;
var summary = 'Parse error in control letter escapes (RegExp)';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

expect = true;
actual = /\ca/.test( "\x01" );
this.reportCompare(expect, actual, summary + ':/\ca/.test( "\x01" )');

expect = false;
actual = /\ca/.test( "\\ca" );
this.reportCompare(expect, actual, summary + ': /\ca/.test( "\\ca" )');



},

/**
*  File Name:          regress__346090.js
*/
test_regress__346090 : function() {
//var BUGNUMBER = 346090;
var summary = 'Do not crash with this regexp';
var actual = 'No Crash';
var expect = 'No Crash';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

var r = /%((h[^l]+)|(l[^h]+)){0,2}?a/g;
r.exec('%lld %d');

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/**
*  File Name:          regress__367888.js
*/
test_regress__367888 : function() {
//var BUGNUMBER = 367888;
var summary = 'RegExp /(|)??x/g.exec("y") barfs';
var actual = '';
var expect = '';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
// printStatus (summary);

expect = null;
actual = /(|)??x/g.exec("y");

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/**
*  File Name:          regress__375642.js
*/
test_regress__375642 : function() {
//var BUGNUMBER = 375642;
var summary = 'RegExp /(?:a??)+?/.exec("")';
var actual = '';
var expect = '';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

/(?:a??)+?/.exec("")

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/**
*  File Name:          regress__375711.js
*/
test_regress__375711 : function() {
//var BUGNUMBER = 375711;
var summary = 'Do not assert with /[Q-b]/i.exec("")';
var actual = '';
var expect = '';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

var s;

// see bug 416933
//print('see bug 416933 for changed behavior on Gecko 1.9');

try
{
s = '/[Q-b]/.exec("")';
expect = 'No Error';
//print(s + ' expect ' + expect);
eval(s);
actual = 'No Error';
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': ' + s);

try
{
s ='/[Q-b]/i.exec("")';
expect = 'No Error';
//print(s + ' expect ' + expect);
eval(s);
actual = 'No Error';
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': ' + s);

try
{
s = '/[q-b]/.exec("")';
expect = 'SyntaxError: invalid range in character class';
//print(s + ' expect ' + expect);
eval(s);
actual = 'No Error';
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': ' + s);

try
{
s ='/[q-b]/i.exec("")';
expect = 'SyntaxError: invalid range in character class';
//print(s + ' expect ' + expect);
eval(s);
actual = 'No Error';
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': ' + s);

//exitFunc ('test');
//}

},

/**
*  File Name:          regress__375715__01__n.js
*/
test_regress__375715__01__n : function() {
//var BUGNUMBER = 375715;
var summary = 'Do not assert: (c2 <= cs->length) && (c1 <= c2)';
var actual = '';
var expect = '';

//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

// note that the assertion does not fire if the regexp is
// evald or used in new RegExp, so this test must be an -n
// with uncaught SyntaxError.

/[\Wb-G]/.exec("");
this.reportCompare(expect, actual, summary + ' /[\Wb-G]/.exec("")');

// exitFunc ('test');
//}

},

/**
*  File Name:          regress__375715__02.js
*/
test_regress__375715__02 : function() {
//var BUGNUMBER = 375715;
var summary = 'Do not assert: (c2 <= cs->length) && (c1 <= c2)';
var actual = '';
var expect = '';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

/[\s-:]/;
this.reportCompare(expect, actual, summary + '/[\s-:]/');

//exitFunc ('test');
//}

},

/**
*  File Name:          regress__375715__03.js
*/
test_regress__375715__03 : function() {
//var BUGNUMBER = 375715;
var summary = 'Do not assert: (c2 <= cs->length) && (c1 <= c2)';
var actual = '';
var expect = '';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

/[_-t]/i.exec("");
this.reportCompare(expect, actual, summary + '/[_-t]/i.exec("")');

//exitFunc ('test');
//}

},

/**
*  File Name:          regress__375715__04.js
*/
test_regress__375715__04 : function() {
//var BUGNUMBER = 375715;
var summary = 'Do not assert: (c2 <= cs->length) && (c1 <= c2)';
var actual = '';
var expect = '';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

try
{
expect = 'SyntaxError: invalid range in character class';
(new RegExp("[\xDF-\xC7]]", "i")).exec("");
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + '(new RegExp("[\xDF-\xC7]]", "i")).exec("")');

// exitFunc ('test');
//}

},

/**
*  File Name:          regress_57572.js
* Date: 28 December 2000
*
* SUMMARY: Testing regular expressions containing the ? character.
* Arose from Bugzilla bug 57572: "RegExp with ? matches incorrectly"
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=57572
*/
test_regress_57572 : function() {
var i = 0;
//var BUGNUMBER = 57572;
var summary = 'Testing regular expressions containing "?"';
var cnEmptyString = ''; var cnSingleSpace = ' ';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();


status = this.inSection(1);
pattern = /(\S+)?(.*)/;
string = 'Test this';
actualmatch = string.match(pattern);
expectedmatch = Array(string, 'Test', ' this');  //single space in front of 'this'
addThis();

status = this.inSection(2);
pattern = /(\S+)? ?(.*)/;  //single space between the ? characters
string= 'Test this';
actualmatch = string.match(pattern);
expectedmatch = Array(string, 'Test', 'this');  //NO space in front of 'this'
addThis();

status = this.inSection(3);
pattern = /(\S+)?(.*)/;
string = 'Stupid phrase, with six - (short) words';
actualmatch = string.match(pattern);
expectedmatch = Array(string, 'Stupid', ' phrase, with six - (short) words');  //single space in front of 'phrase'
addThis();

status = this.inSection(4);
pattern = /(\S+)? ?(.*)/;  //single space between the ? characters
string = 'Stupid phrase, with six - (short) words';
actualmatch = string.match(pattern);
expectedmatch = Array(string, 'Stupid', 'phrase, with six - (short) words');  //NO space in front of 'phrase'
addThis();


// let's add an extra back-reference this time - three instead of two -
status = this.inSection(5);
pattern = /(\S+)?( ?)(.*)/;  //single space before second ? character
string = 'Stupid phrase, with six - (short) words';
actualmatch = string.match(pattern);
expectedmatch = Array(string, 'Stupid', cnSingleSpace, 'phrase, with six - (short) words');
addThis();

status = this.inSection(6);
pattern = /^(\S+)?( ?)(B+)$/;  //single space before second ? character
string = 'AAABBB';
actualmatch = string.match(pattern);
expectedmatch = Array(string, 'AAABB', cnEmptyString, 'B');
addThis();

status = this.inSection(7);
pattern = /(\S+)?(!?)(.*)/;
string = 'WOW !!! !!!';
actualmatch = string.match(pattern);
expectedmatch = Array(string, 'WOW', cnEmptyString, ' !!! !!!');
addThis();

status = this.inSection(8);
pattern = /(.+)?(!?)(!+)/;
string = 'WOW !!! !!!';
actualmatch = string.match(pattern);
expectedmatch = Array(string, 'WOW !!! !!', cnEmptyString, '!');
addThis();



//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------



function addThis()
{
statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);
this.testRegExp(statusmessages, patterns, strings, actualmatches, expectedmatches);
//exitFunc ('test');
//}

},

/**
*  File Name:          regress__57631.js
* Date: 26 November 2000
*
*
* SUMMARY:  This test arose from Bugzilla bug 57631:
* "RegExp with invalid pattern or invalid flag causes segfault"
*
* Either error should throw an exception of type SyntaxError,
* and we check to see that it does...
*/
test_regress__57631 : function() {
//var BUGNUMBER = '57631';
var summary = 'Testing new RegExp(pattern,flag) with illegal pattern or flag';
var statprefix = 'Testing for error creating illegal RegExp object on pattern ';
var statsuffix =  'and flag ';
var cnSUCCESS = 'SyntaxError';
var cnFAILURE = 'not a SyntaxError';
var singlequote = "'";
var i = -1; var j = -1; var s = ''; var f = '';
var obj = {};
var status = ''; var actual = ''; var expect = ''; var msg = '';
var legalpatterns = new Array(); var illegalpatterns = new Array();
var legalflags = new Array();  var illegalflags = new Array();


// valid regular expressions to try -
legalpatterns[0] = '';
legalpatterns[1] = 'abc';
legalpatterns[2] = '(.*)(3-1)\s\w';
legalpatterns[3] = '(.*)(...)\\s\\w';
legalpatterns[4] = '[^A-Za-z0-9_]';
legalpatterns[5] = '[^\f\n\r\t\v](123.5)([4 - 8]$)';

// invalid regular expressions to try -
illegalpatterns[0] = '(?)';
illegalpatterns[1] = '(a';
illegalpatterns[2] = '( ]';
//illegalpatterns[3] = '\d{1,s}';

// valid flags to try -
legalflags[0] = 'i';
legalflags[1] = 'g';
legalflags[2] = 'm';
legalflags[3] = undefined;

// invalid flags to try -
illegalflags[0] = 'a';
illegalflags[1] = 123;
illegalflags[2] = new RegExp();



//-------------------------------------------------------------------------------------------------
//test();
//-------------------------------------------------------------------------------------------------


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

testIllegalRegExps(this,legalpatterns, illegalflags);
testIllegalRegExps(this,illegalpatterns, legalflags);
testIllegalRegExps(this,illegalpatterns, illegalflags);

//exitFunc ('test');
//}


// This function will only be called where all the patterns are illegal, or all the flags
function testIllegalRegExps(obj, patterns, flags)
{
for (i in patterns)
{
s = patterns[i];

for (j in flags)
{
f = flags[j];
status = getStatus(s, f);
actual = cnFAILURE;
expect = cnSUCCESS;

try
{
// This should cause an exception if either s or f is illegal -
eval('obj = new RegExp(s, f);');
}
catch(e)
{
// We expect to get a SyntaxError - test for this:
if (e instanceof SyntaxError)
actual = cnSUCCESS;
}

obj.reportCompare(expect, actual, status);
}
}
}


function getStatus(regexp, flag)
{
return (statprefix  +  quote(regexp) +  statsuffix  +   quote(flag));
}


function quote(text)
{
return (singlequote  +  text  + singlequote);
}

},

/**
*  File Name:          regress__67773.js
* Date: 06 February 2001
*
* SUMMARY:  Arose from Bugzilla bug 67773:
* "Regular subexpressions followed by + failing to run to completion"
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=67773
* See http://bugzilla.mozilla.org/show_bug.cgi?id=69989
*/
test_regress__67773 : function() {
var i = 0;
//var BUGNUMBER = 67773;
var summary = 'Testing regular subexpressions followed by ? or +\n';
var cnSingleSpace = ' ';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();


pattern = /^(\S+)?( ?)(B+)$/;  //single space before second ? character
status = this.inSection(1);
string = 'AAABBB AAABBB ';  //single space at middle and at end -
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(2);
string = 'AAABBB BBB';  //single space in the middle
actualmatch = string.match(pattern);
expectedmatch = Array(string,  'AAABBB', cnSingleSpace,  'BBB');
addThis();

status = this.inSection(3);
string = 'AAABBB AAABBB';  //single space in the middle
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();


pattern = /^(A+B)+$/;
status = this.inSection(4);
string = 'AABAAB';
actualmatch = string.match(pattern);
expectedmatch = Array(string,  'AAB');
addThis();

status = this.inSection(5);
string = 'ABAABAAAAAAB';
actualmatch = string.match(pattern);
expectedmatch = Array(string,  'AAAAAAB');
addThis();

status = this.inSection(6);
string = 'ABAABAABAB';
actualmatch = string.match(pattern);
expectedmatch = Array(string,  'AB');
addThis();

status = this.inSection(7);
string = 'ABAABAABABB';
actualmatch = string.match(pattern);
expectedmatch = null;   // because string doesn't match at end
addThis();


pattern = /^(A+1)+$/;
status = this.inSection(8);
string = 'AA1AA1';
actualmatch = string.match(pattern);
expectedmatch = Array(string,  'AA1');
addThis();


pattern = /^(\w+\-)+$/;
status = this.inSection(9);
string = '';
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(10);
string = 'bla-';
actualmatch = string.match(pattern);
expectedmatch = Array(string, string);
addThis();

status = this.inSection(11);
string = 'bla-bla';  // hyphen missing at end -
actualmatch = string.match(pattern);
expectedmatch = null;  //because string doesn't match at end
addThis();

status = this.inSection(12);
string = 'bla-bla-';
actualmatch = string.match(pattern);
expectedmatch = Array(string, 'bla-');
addThis();


pattern = /^(\S+)+(A+)$/;
status = this.inSection(13);
string = 'asdldflkjAAA';
actualmatch = string.match(pattern);
expectedmatch = Array(string, 'asdldflkjAA', 'A');
addThis();

status = this.inSection(14);
string = 'asdldflkj AAA'; // space in middle
actualmatch = string.match(pattern);
expectedmatch = null;  //because of the space
addThis();


pattern = /^(\S+)+(\d+)$/;
status = this.inSection(15);
string = 'asdldflkj122211';
actualmatch = string.match(pattern);
expectedmatch = Array(string, 'asdldflkj12221', '1');
addThis();

status = this.inSection(16);
string = 'asdldflkj1111111aaa1';
actualmatch = string.match(pattern);
expectedmatch = Array(string, 'asdldflkj1111111aaa', '1');
addThis();


/*
* This one comes from Stephen Ostermiller.
* See http://bugzilla.mozilla.org/show_bug.cgi?id=69989
*/
pattern = /^[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)+$/;
status = this.inSection(17);
string = 'some.host.tld';
actualmatch = string.match(pattern);
expectedmatch = Array(string, '.tld', '.');
addThis();



//-------------------------------------------------------------------------------------------------
//test();
//-------------------------------------------------------------------------------------------------



function addThis()
{
statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);
this.testRegExp(statusmessages, patterns, strings, actualmatches, expectedmatches);
//exitFunc ('test');
//}

},

/**
*  File Name:          regress__72964.js
* Date: 2001-07-17
*
* SUMMARY: Regression test for Bugzilla bug 72964:
* "String method for pattern matching failed for Chinese Simplified (GB2312)"
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=72964
*/
test_regress__72964 : function() {
var i = 0;
//var BUGNUMBER = 72964;
var summary = 'Testing regular expressions containing non-Latin1 characters';
var cnSingleSpace = ' ';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();


pattern = /[\S]+/;
// 4 low Unicode chars = Latin1; whole string should match
status = this.inSection(1);
string = '\u00BF\u00CD\u00BB\u00A7';
actualmatch = string.match(pattern);
expectedmatch = Array(string);
addThis();

// Now put a space in the middle; first half of string should match
status = this.inSection(2);
string = '\u00BF\u00CD \u00BB\u00A7';
actualmatch = string.match(pattern);
expectedmatch = Array('\u00BF\u00CD');
addThis();


// 4 high Unicode chars = non-Latin1; whole string should match
status = this.inSection(3);
string = '\u4e00\uac00\u4e03\u4e00';
actualmatch = string.match(pattern);
expectedmatch = Array(string);
addThis();

// Now put a space in the middle; first half of string should match
status = this.inSection(4);
string = '\u4e00\uac00 \u4e03\u4e00';
actualmatch = string.match(pattern);
expectedmatch = Array('\u4e00\uac00');
addThis();



//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------



function addThis()
{
statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);
this.testRegExp(statusmessages, patterns, strings, actualmatches, expectedmatches);
//exitFunc ('test');
//}

},

/**
*  File Name:          regress__76683.js
* Date: 01 May 2001
*
* SUMMARY: Regression test for Bugzilla bug 76683 on Rhino:
* "RegExp regression (NullPointerException)"
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=76683
*/
test_regress__76683 : function() {
var i = 0;
//var BUGNUMBER = 76683;
var summary = 'Regression test for Bugzilla bug 76683';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();


/*
* Rhino (2001-04-19) crashed on the 3rd regular expression below.
* It didn't matter what the string was. No problem in SpiderMonkey -
*/
string = 'abc';
status = this.inSection(1);
pattern = /(<!--([^-]|-[^-]|--[^>])*-->)|(<([\$\w:\.\-]+)((([ ][^\/>]*)?\/>)|(([ ][^>]*)?>)))/;
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

status = this.inSection(2);
pattern = /(<!--([^-]|-[^-]|--[^>])*-->)|(<(tagPattern)((([ ][^\/>]*)?\/>)|(([ ][^>]*)?>)))/;
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();

// This was the one causing a Rhino crash -
status = this.inSection(3);
pattern = /(<!--([^-]|-[^-]|--[^>])*-->)|(<(tagPattern)((([ ][^\/>]*)?\/>)|(([ ][^>]*)?>)))|(<\/tagPattern[^>]*>)/;
actualmatch = string.match(pattern);
expectedmatch = null;
addThis();



//-------------------------------------------------------------------------------------------------
//test();
//-------------------------------------------------------------------------------------------------



function addThis()
{
statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);
this.testRegExp(statusmessages, patterns, strings, actualmatches, expectedmatches);
//exitFunc ('test');
//}

},

/**
*  File Name:          regress__78156.js
* Date: 06 February 2001
*
* SUMMARY:  Arose from Bugzilla bug 78156:
* "m flag of regular expression does not work with $"
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=78156
*
* The m flag means a regular expression should search strings
* across multiple lines, i.e. across '\n', '\r'.
*/
test_regress__78156 : function() {
var i = 0;
//var BUGNUMBER = 78156;
var summary = 'Testing regular expressions with  ^, $, and the m flag -';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();

/*
* All patterns have an m flag; all strings are multiline.
* Looking for digit characters at beginning/end of lines.
*/

string = 'aaa\n789\r\nccc\r\n345';
status = this.inSection(1);
pattern = /^\d/gm;
actualmatch = string.match(pattern);
expectedmatch = ['7','3'];
addThis();

status = this.inSection(2);
pattern = /\d$/gm;
actualmatch = string.match(pattern);
expectedmatch = ['9','5'];
addThis();

string = 'aaa\n789\r\nccc\r\nddd';
status = this.inSection(3);
pattern = /^\d/gm;
actualmatch = string.match(pattern);
expectedmatch = ['7'];
addThis();

status = this.inSection(4);
pattern = /\d$/gm;
actualmatch = string.match(pattern);
expectedmatch = ['9'];
addThis();



//-------------------------------------------------------------------------------------------------
//test();
//-------------------------------------------------------------------------------------------------



function addThis()
{
statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);
this.testRegExp(statusmessages, patterns, strings, actualmatches, expectedmatches);
//exitFunc ('test');
//}

},

/**
*  File Name:          regress__85721.js
* Date:    14 Feb 2002
* SUMMARY: Performance: Regexp performance degraded from 4.7
* See http://bugzilla.mozilla.org/show_bug.cgi?id=85721
*
* Adjust this testcase if necessary. The FAST constant defines
* an upper bound in milliseconds for any execution to take.
*/
test_regress__85721 : function() {
//var BUGNUMBER = 85721;
var summary = 'Performance: execution of regular expression';
var FAST = 100; // execution should be 100 ms or less to pass the test
var MSG_FAST = 'Execution took less than ' + FAST + ' ms';
var MSG_SLOW = 'Execution took ';
var MSG_MS = ' ms';
var str = '';
var re = '';
var status = '';
var actual = '';
var expect= '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);


function elapsedTime(startTime)
{
return new Date() - startTime;
}


function isThisFast(ms)
{
if (ms <= FAST)
return MSG_FAST;
return MSG_SLOW + ms + MSG_MS;
}



/*
* The first regexp. We'll test for performance (Section 1) and accuracy (Section 2).
*/
str='<sql:connection id="conn1"> <sql:url>www.m.com</sql:url> <sql:driver>drive.class</sql:driver>\n<sql:userId>foo</sql:userId> <sql:password>goo</sql:password> </sql:connection>';
re = /<sql:connection id="([^\r\n]*?)">\s*<sql:url>\s*([^\r\n]*?)\s*<\/sql:url>\s*<sql:driver>\s*([^\r\n]*?)\s*<\/sql:driver>\s*(\s*<sql:userId>\s*([^\r\n]*?)\s*<\/sql:userId>\s*)?\s*(\s*<sql:password>\s*([^\r\n]*?)\s*<\/sql:password>\s*)?\s*<\/sql:connection>/;
expect = Array("<sql:connection id=\"conn1\"> <sql:url>www.m.com</sql:url> <sql:driver>drive.class</sql:driver>\n<sql:userId>foo</sql:userId> <sql:password>goo</sql:password> </sql:connection>","conn1","www.m.com","drive.class","<sql:userId>foo</sql:userId> ","foo","<sql:password>goo</sql:password> ","goo");

/*
*  Check performance -
*/
status = this.inSection(1);
var start = new Date();
var result = re.exec(str);
actual = elapsedTime(start);
this.reportCompare(isThisFast(FAST), isThisFast(actual), status);

/*
*  Check accuracy -
*/
status = this.inSection(2);
this.testRegExp([status], [re], [str], [result], [expect]);



/*
* The second regexp (HUGE!). We'll test for performance (Section 3) and accuracy (Section 4).
* It comes from the O'Reilly book "Mastering Regular Expressions" by Jeffrey Friedl, Appendix B
*/

//# Some things for avoiding backslashitis later on.
$esc        = '\\\\';
$Period      = '\.';
$space      = '\040';              $tab         = '\t';
$OpenBR     = '\\[';               $CloseBR     = '\\]';
$OpenParen  = '\\(';               $CloseParen  = '\\)';
$NonASCII   = '\x80-\xff';         $ctrl        = '\000-\037';
$CRlist     = '\n\015';  //# note: this should really be only \015.
// Items 19, 20, 21
$qtext = '[^' + $esc + $NonASCII + $CRlist + '\"]';						  // # for within "..."
$dtext = '[^' + $esc + $NonASCII + $CRlist + $OpenBR + $CloseBR + ']';    // # for within [...]
$quoted_pair = $esc + '[^' + $NonASCII + ']';							  // # an escaped character

//##############################################################################
//# Items 22 and 23, comment.
//# Impossible to do properly with a regex, I make do by allowing at most one level of nesting.
$ctext   =  '[^' + $esc + $NonASCII + $CRlist + '()]';

//# $Cnested matches one non-nested comment.
//# It is unrolled, with normal of $ctext, special of $quoted_pair.
$Cnested =
$OpenParen +                                 // #  (
$ctext + '*' +                            // #     normal*
'(?:' + $quoted_pair + $ctext + '*)*' +   // #     (special normal*)*
$CloseParen;                                 // #                       )


//# $comment allows one level of nested parentheses
//# It is unrolled, with normal of $ctext, special of ($quoted_pair|$Cnested)
$comment =
$OpenParen +                                           // #  (
$ctext + '*' +                                     // #     normal*
'(?:' +                                            // #       (
'(?:' + $quoted_pair + '|' + $Cnested + ')' +   // #         special
$ctext + '*' +                                 // #         normal*
')*' +                                             // #            )*
$CloseParen;                                           // #                )


//##############################################################################
//# $X is optional whitespace/comments.
$X =
'[' + $space + $tab + ']*' +					       // # Nab whitespace.
'(?:' + $comment + '[' + $space + $tab + ']*)*';    // # If comment found, allow more spaces.


//# Item 10: atom
$atom_char   = '[^(' + $space + '<>\@,;:\".' + $esc + $OpenBR + $CloseBR + $ctrl + $NonASCII + ']';
$atom =
$atom_char + '+' +            // # some number of atom characters...
'(?!' + $atom_char + ')';     // # ..not followed by something that could be part of an atom

// # Item 11: doublequoted string, unrolled.
$quoted_str =
'\"' +                                         // # "
$qtext + '*' +                              // #   normal
'(?:' + $quoted_pair + $qtext + '*)*' +     // #   ( special normal* )*
'\"';                                          // # "

//# Item 7: word is an atom or quoted string
$word =
'(?:' +
$atom +                // # Atom
'|' +                  //     #  or
$quoted_str +          // # Quoted string
')'

//# Item 12: domain-ref is just an atom
$domain_ref  = $atom;

//# Item 13: domain-literal is like a quoted string, but [...] instead of  "..."
$domain_lit  =
$OpenBR +								   	     // # [
'(?:' + $dtext + '|' + $quoted_pair + ')*' +     // #    stuff
$CloseBR;                                        // #           ]

// # Item 9: sub-domain is a domain-ref or domain-literal
$sub_domain  =
'(?:' +
$domain_ref +
'|' +
$domain_lit +
')' +
$X;                 // # optional trailing comments

// # Item 6: domain is a list of subdomains separated by dots.
$domain =
$sub_domain +
'(?:' +
$Period + $X + $sub_domain +
')*';

//# Item 8: a route. A bunch of "@ $domain" separated by commas, followed by a colon.
$route =
'\@' + $X + $domain +
'(?:,' + $X + '\@' + $X + $domain + ')*' +  // # additional domains
':' +
$X;					// # optional trailing comments

//# Item 6: local-part is a bunch of $word separated by periods
$local_part =
$word + $X
'(?:' +
$Period + $X + $word + $X +		// # additional words
')*';

// # Item 2: addr-spec is local@domain
$addr_spec  =
$local_part + '\@' + $X + $domain;

//# Item 4: route-addr is <route? addr-spec>
$route_addr =
'<' + $X +                     // # <
'(?:' + $route + ')?' +     // #       optional route
$addr_spec +                // #       address spec
'>';                           // #                 >

//# Item 3: phrase........
$phrase_ctrl = '\000-\010\012-\037'; // # like ctrl, but without tab

//# Like atom-char, but without listing space, and uses phrase_ctrl.
//# Since the class is negated, this matches the same as atom-char plus space and tab
$phrase_char =
'[^()<>\@,;:\".' + $esc + $OpenBR + $CloseBR + $NonASCII + $phrase_ctrl + ']';

// # We've worked it so that $word, $comment, and $quoted_str to not consume trailing $X
// # because we take care of it manually.
$phrase =
$word +                                                  // # leading word
$phrase_char + '*' +                                     // # "normal" atoms and/or spaces
'(?:' +
'(?:' + $comment + '|' + $quoted_str + ')' +          // # "special" comment or quoted string
$phrase_char + '*' +                                  // #  more "normal"
')*';

// ## Item #1: mailbox is an addr_spec or a phrase/route_addr
$mailbox =
$X +                                // # optional leading comment
'(?:' +
$phrase + $route_addr +     // # name and address
'|' +                       //     #  or
$addr_spec +                // # address
')';


//###########################################################################


re = new RegExp($mailbox, "g");
str = 'Jeffy<"That Tall Guy"@ora.com (this address is no longer active)>';
expect = Array('Jeffy<"That Tall Guy"@ora.com (this address is no longer active)>');

/*
*  Check performance -
*/
status = this.inSection(3);
var start = new Date();
var result = re.exec(str);
actual = elapsedTime(start);
this.reportCompare(isThisFast(FAST), isThisFast(actual), status);

/*
*  Check accuracy -
*/
status = this.inSection(4);
this.testRegExp([status], [re], [str], [result], [expect]);

},

/**
*  File Name:          regress__87231.js
* Date: 22 June 2001
*
* SUMMARY:  Regression test for Bugzilla bug 87231:
* "Regular expression /(A)?(A.*)/ picks 'A' twice"
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=87231
* Key case:
*
*            pattern = /^(A)?(A.*)$/;
*            string = 'A';
*            expectedmatch = Array('A', '', 'A');
*
*
* We expect the 1st subexpression (A)? NOT to consume the single 'A'.
* Recall that "?" means "match 0 or 1 times". Here, it should NOT do
* greedy matching: it should match 0 times instead of 1. This allows
* the 2nd subexpression to make the only match it can: the single 'A'.
* Such "altruism" is the only way there can be a successful global match...
*/
test_regress__87231 : function() {
var i = 0;
//var BUGNUMBER = 87231;
var cnEmptyString = '';
var summary = 'Testing regular expression /(A)?(A.*)/';
var status = '';
var statusmessages = new Array();
var pattern = '';
var patterns = new Array();
var string = '';
var strings = new Array();
var actualmatch = '';
var actualmatches = new Array();
var expectedmatch = '';
var expectedmatches = new Array();


pattern = /^(A)?(A.*)$/;
status = this.inSection(1);
string = 'AAA';
actualmatch = string.match(pattern);
expectedmatch = Array('AAA', 'A', 'AA');
addThis();

status = this.inSection(2);
string = 'AA';
actualmatch = string.match(pattern);
expectedmatch = Array('AA', 'A', 'A');
addThis();

status = this.inSection(3);
string = 'A';
actualmatch = string.match(pattern);
expectedmatch = Array('A', undefined, 'A'); // 'altruistic' case: see above
addThis();


pattern = /(A)?(A.*)/;
var strL = 'zxcasd;fl\\\  ^';
var strR = 'aaAAaaaf;lrlrzs';

status = this.inSection(4);
string =  strL + 'AAA' + strR;
actualmatch = string.match(pattern);
expectedmatch = Array('AAA' + strR, 'A', 'AA' + strR);
addThis();

status = this.inSection(5);
string =  strL + 'AA' + strR;
actualmatch = string.match(pattern);
expectedmatch = Array('AA' + strR, 'A', 'A' + strR);
addThis();

status = this.inSection(6);
string =  strL + 'A' + strR;
actualmatch = string.match(pattern);
expectedmatch = Array('A' + strR, undefined, 'A' + strR); // 'altruistic' case: see above
addThis();



//-------------------------------------------------------------------------------------------------
//test();
//-------------------------------------------------------------------------------------------------



function addThis()
{
statusmessages[i] = status;
patterns[i] = pattern;
strings[i] = string;
actualmatches[i] = actualmatch;
expectedmatches[i] = expectedmatch;
i++;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);
this.testRegExp(statusmessages, patterns, strings, actualmatches, expectedmatches);
//exitFunc ('test');
//}

},

/**
*  File Name:          regress__98306.js
* Date: 04 September 2001
*
* SUMMARY: Regression test for Bugzilla bug 98306
* "JS parser crashes in ParseAtom for script using Regexp()"
*
* See http://bugzilla.mozilla.org/show_bug.cgi?id=98306
*/
test_regress__98306 : function() {
//var BUGNUMBER = 98306;
var summary = "Testing that we don't crash on this code -";
var cnUBOUND = 10;
var re;
var s;


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
// printStatus (summary);

s = '"Hello".match(/[/]/)';
tryThis(s);

s = 're = /[/';
tryThis(s);

s = 're = /[/]/';
tryThis(s);

s = 're = /[//]/';
tryThis(s);

this.reportCompare('No Crash', 'No Crash', '');
//exitFunc ('test');
//}


// Try to provoke a crash -
function tryThis(sCode)
{
// sometimes more than one attempt is necessary -
for (var i=0; i<cnUBOUND; i++)
{
try
{
eval(sCode);
}
catch(e)
{
// do nothing; keep going -
}
}
}

}

})
.endType();




