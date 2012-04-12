vjo.ctype("dsf.jslang.feature.tests.Js15ObjectTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({
reportCompare : function  (expectedValue, actualValue, statusItems) {
new this.vj$.BaseTest().TestCase( ' ',  statusItems, expectedValue,  actualValue);
},

inSection : function (x) {
var SECT_PREFIX = 'Section ';
var SECT_SUFFIX = ' of test - ';
return SECT_PREFIX + x + SECT_SUFFIX;
},

test_regress__137000:function(){

var UBound = 0;
var BUGNUMBER = 137000;
var summary = 'Function param or local var with same name as a function prop';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


/*
* Note use of 'x' both for the parameter to f,
* and as a property name for |f| as an object
*/
function f(x)
{
}

var status = this.inSection(1);
f.x = 12;
actual = f.x;
expect = 12;
addThis();



/*
* A more elaborate example, using the call() method
* to chain constructors from child to parent.
*
* The key point is the use of the same name 'p' for both
* the parameter to the constructor, and as a property name
*/
function parentObject(p)
{
this.p = 1;
}

function childObject()
{
parentObject.call(this);
}
childObject.prototype = parentObject;

status = this.inSection(2);
var objParent = new parentObject();
actual = objParent.p;
expect = 1;
addThis();

status = this.inSection(3);
var objChild = new childObject();
actual = objChild.p;
expect = 1;
addThis();



/*
* A similar set-up. Here the same name is being used for
* the parameter to both the Base and Child constructors,
*/
function Base(id)
{
}

function Child(id)
{
this.prop = id;
}
Child.prototype=Base;

status = this.inSection(4);
var c1 = new Child('child1');
actual = c1.prop;
expect = 'child1';
addThis();



/*
* Use same identifier as a property name, too -
*/
function BaseX(id)
{
}

function ChildX(id)
{
this.id = id;
}
ChildX.prototype=BaseX;

status = this.inSection(5);
c1 = new ChildX('child1');
actual = c1.id;
expect = 'child1';
addThis();



/*
* From http://bugzilla.mozilla.org/show_bug.cgi?id=150032
*
* Here the same name is being used both for a local variable
* declared in g(), and as a property name for |g| as an object
*/
function g()
{
var propA = g.propA;
var propB = g.propC;

this.getVarA = function() {return propA;}
this.getVarB = function() {return propB;}
}
g.propA = 'A';
g.propB = 'B';
g.propC = 'C';
var obj = new g();

status = this.inSection(6);
actual = obj.getVarA(); // this one was returning 'undefined'
expect = 'A';
addThis();

status = this.inSection(7);
actual = obj.getVarB(); // this one is easy; it never failed
expect = 'C';
addThis();



/*
* By martin.honnen@gmx.de
* From http://bugzilla.mozilla.org/show_bug.cgi?id=150859
*
* Here the same name is being used for a local var in F
* and as a property name for |F| as an object
*
* Twist: the property is added via another function.
*/
function setFProperty(val)
{
F.propA = val;
}

function F()
{
var propA = 'Local variable in F';
}

status = this.inSection(8);
setFProperty('Hello');
actual = F.propA; // this was returning 'undefined'
expect = 'Hello';
addThis();




//-----------------------------------------------------------------------------
test();
//-----------------------------------------------------------------------------



function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
}


function test()
{
//   enterFunc('test');
//   printBugNumber(BUGNUMBER);
//   printStatus(summary);

for (var i=0; i<UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//   exitFunc ('test');
}

},
test_regress__192105:function(){

var UBound = 0;
var BUGNUMBER = 192105;
var summary = 'Using |instanceof| to check if f() is called as constructor';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


/*
* This function is the heart of the test. It sets the result
* variable |actual|, which we will compare against |expect|.
*
* Note |actual| will be set to |true| or |false| according
* to whether or not this function is called as a constructor;
* i.e. whether it is called via the |new| keyword or not -
*/
function f()
{
actual = (this instanceof f);
}


/*
* Call f as a constructor from global scope
*/
status = this.inSection(1);
new f(); // sets |actual|
expect = true;
addThis();

/*
* Now, not as a constructor
*/
status = this.inSection(2);
f(); // sets |actual|
expect = false;
addThis();


/*
* Call f as a constructor from function scope
*/
function F()
{
new f();
}
status = this.inSection(3);
F(); // sets |actual|
expect = true;
addThis();

/*
* Now, not as a constructor
*/
function G()
{
f();
}
status = this.inSection(4);
G(); // sets |actual|
expect = false;
addThis();


/*
* Now make F() and G() methods of an object
*/
var obj = {F:F, G:G};
status = this.inSection(5);
obj.F(); // sets |actual|
expect = true;
addThis();

status = this.inSection(6);
obj.G(); // sets |actual|
expect = false;
addThis();


/*
* Now call F() and G() from yet other functions, and use eval()
*/
function A()
{
eval('F();');
}
status = this.inSection(7);
A(); // sets |actual|
expect = true;
addThis();


function B()
{
eval('G();');
}
status = this.inSection(8);
B(); // sets |actual|
expect = false;
addThis();




//-----------------------------------------------------------------------------
test();
//-----------------------------------------------------------------------------



function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
}


function test()
{
//   enterFunc('test');
//   printBugNumber(BUGNUMBER);
//   printStatus(summary);

for (var i=0; i<UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//   exitFunc ('test');
}

},
test_regress__308806__01:function(){

//-----------------------------------------------------------------------------
var BUGNUMBER = 308806;
var summary = 'Object.prototype.toLocaleString() should track Object.prototype.toString() ';
var actual = '';
var expect = '';

// printBugNumber(BUGNUMBER);
// printStatus (summary);

var o = {toString: function() { return 'foo'; }};

expect = o.toString();
actual = o.toLocaleString();

this.reportCompare(expect, actual, summary);

},
test_regress__338709:function(){

//-----------------------------------------------------------------------------
var BUGNUMBER = 338709;
var summary = 'ReadOnly properties should not be overwritten by using ' +
'Object and try..throw..catch';
var actual = '';
var expect = '';

// printBugNumber(BUGNUMBER);
// printStatus (summary);

Object = function () { return Math };
expect = Math.LN2;
try
{
throw 1990;
}
catch (LN2)
{
}
actual = Math.LN2;
// print("Math.LN2 = " + Math.LN2)
this.reportCompare(expect, actual, summary);

var s = new String("abc");
Object = function () { return s };
expect = s.length;
try
{
throw -8
}
catch (length)
{
}
actual = s.length;
// print("length of '" + s + "' = " + s.length)
this.reportCompare(expect, actual, summary);

var re = /xy/m;
Object = function () { return re };
expect = re.multiline;
try
{
throw false
}
catch (multiline)
{
}
actual = re.multiline;
// print("re.multiline = " + re.multiline)
this.reportCompare(expect, actual, summary);

if ("document" in this) {
// Let the document be its own documentElement.
Object = function () { return document }
expect = document.documentElement + '';
try
{
throw document;
}
catch (documentElement)
{
}
actual = document.documentElement + '';
//   print("document.documentElement = " + document.documentElement)
}
else
Object = this.constructor

this.reportCompare(expect, actual, summary);

},
test_regress__362872__01:function(){

//-----------------------------------------------------------------------------
var BUGNUMBER = 362872;
var summary = 'script should not drop watchpoint that is in use';
var actual = 'No Crash';
var expect = 'No Crash';


//-----------------------------------------------------------------------------
test();
//-----------------------------------------------------------------------------

function test()
{
//   enterFunc ('test');
//   printBugNumber(BUGNUMBER);
//   printStatus (summary);

function exploit() {
var rooter = {}, object = {}, filler1 = "", filler2 = "\u5555";
for(var i = 0; i < 32/2-2; i++) { filler1 += "\u5050"; }
object.watch("foo", function(){
object.unwatch("foo");
object.unwatch("foo");
for(var i = 0; i < 8 * 1024; i++) {
rooter[i] = filler1 + filler2;
}
});
object.foo = "bar";
}
exploit();


this.reportCompare(expect, actual, summary);

//   exitFunc ('test');
}

},
test_regress__362872__02:function(){

//-----------------------------------------------------------------------------
var BUGNUMBER = 362872;
var summary = 'script should not drop watchpoint that is in use';
var actual = 'No Crash';
var expect = 'No Crash';

// printBugNumber(BUGNUMBER);
// printStatus (summary);
var x;
this.watch('x', function f() {
//              print("before");
x = 3;
//              print("after");
});
x = 3;

this.reportCompare(expect, actual, summary);

},
test_regress__382503:function(){

//-----------------------------------------------------------------------------
var BUGNUMBER = 382503;
var summary = 'Do not assert: with prototype=regexp';
var actual = '';
var expect = '';

// printBugNumber(BUGNUMBER);
// printStatus (summary);

function f(x)
{
prototype = /a/;

if (x) {
return /b/;
return /c/;
} else {
return /d/;
}
}

void f(false);

this.reportCompare(expect, actual, summary);

},
test_regress__382532:function(){

//-----------------------------------------------------------------------------
var BUGNUMBER = 382532;
var summary = 'instanceof,... broken by use of |prototype| in heavyweight constructor';
var actual = '';
var expect = '';


//-----------------------------------------------------------------------------
test();
//-----------------------------------------------------------------------------

function test()
{
//   enterFunc ('test');
//   printBugNumber(BUGNUMBER);
//   printStatus (summary);

var prototype;

function Bug() {
var func = function () { x; };
prototype;
}

expect = true;
actual = (new Bug instanceof Bug);

this.reportCompare(expect, actual, summary);

//   exitFunc ('test');
}

},
test_regress__90596__003:function(){

var UBound = 0;
var BUGNUMBER = 90596;
var summary = '[DontEnum] props (if overridden) should appear in for-in loops';
var cnCOMMA = ',';
var cnCOLON = ':';
var cnLBRACE = '{';
var cnRBRACE = '}';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];
var obj = {};


status = this.inSection(1);
obj = {toString:9};
actual = enumerateThis(obj);
expect = '{toString:9}';
addThis();

status = this.inSection(2);
obj = {hasOwnProperty:"Hi"};
actual = enumerateThis(obj);
expect = '{hasOwnProperty:"Hi"}';
addThis();

status = this.inSection(3);
obj = {toString:9, hasOwnProperty:"Hi"};
actual = enumerateThis(obj);
expect = '{toString:9, hasOwnProperty:"Hi"}';
addThis();

status = this.inSection(4);
obj = {prop1:1, toString:9, hasOwnProperty:"Hi"};
actual = enumerateThis(obj);
expect = '{prop1:1, toString:9, hasOwnProperty:"Hi"}';
addThis();


// TRY THE SAME THING IN EVAL CODE
var s = '';

status = this.inSection(5);
s = 'obj = {toString:9}';
eval(s);
actual = enumerateThis(obj);
expect = '{toString:9}';
addThis();

status = this.inSection(6);
s = 'obj = {hasOwnProperty:"Hi"}';
eval(s);
actual = enumerateThis(obj);
expect = '{hasOwnProperty:"Hi"}';
addThis();

status = this.inSection(7);
s = 'obj = {toString:9, hasOwnProperty:"Hi"}';
eval(s);
actual = enumerateThis(obj);
expect = '{toString:9, hasOwnProperty:"Hi"}';
addThis();

status = this.inSection(8);
s = 'obj = {prop1:1, toString:9, hasOwnProperty:"Hi"}';
eval(s);
actual = enumerateThis(obj);
expect = '{prop1:1, toString:9, hasOwnProperty:"Hi"}';
addThis();


// TRY THE SAME THING IN FUNCTION CODE
function A()
{
status = this.inSection(9);
var s = 'obj = {toString:9}';
eval(s);
actual = enumerateThis(obj);
expect = '{toString:9}';
addThis();
}
A();

function B()
{
status = this.inSection(10);
var s = 'obj = {hasOwnProperty:"Hi"}';
eval(s);
actual = enumerateThis(obj);
expect = '{hasOwnProperty:"Hi"}';
addThis();
}
B();

function C()
{
status = this.inSection(11);
var s = 'obj = {toString:9, hasOwnProperty:"Hi"}';
eval(s);
actual = enumerateThis(obj);
expect = '{toString:9, hasOwnProperty:"Hi"}';
addThis();
}
C();

function D()
{
status = this.inSection(12);
var s = 'obj = {prop1:1, toString:9, hasOwnProperty:"Hi"}';
eval(s);
actual = enumerateThis(obj);
expect = '{prop1:1, toString:9, hasOwnProperty:"Hi"}';
addThis();
}
D();



//-----------------------------------------------------------------------------
test();
//-----------------------------------------------------------------------------



function enumerateThis(obj)
{
var arr = new Array();
var prop;//<Number
for ( prop in obj)
{
arr.push(prop + cnCOLON + obj[prop]);
}

var ret = addBraces(String(arr));
return ret;
}


function addBraces(text)
{
return cnLBRACE + text + cnRBRACE;
}


/*
* Sort properties alphabetically so the test will work in Rhino
*/
function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = sortThis(actual);
expectedvalues[UBound] = sortThis(expect);
UBound++;
}


/*
* Takes a string of the form '{"c", "b", "a", 2}' and returns '{2,a,b,c}'
*/
function sortThis(sList)
{
sList = compactThis(sList);
sList = stripBraces(sList);
var arr = sList.split(cnCOMMA);
arr = arr.sort();
var ret = String(arr);
ret = addBraces(ret);
return ret;
}


/*
* Strips out any whitespace or quotes from the text -
*/
function compactThis(text)
{
var charCode = 0;
var ret = '';

for (var i=0; i<text.length; i++)
{
charCode = text.charCodeAt(i);

if (!isWhiteSpace(charCode) && !isQuote(charCode))
ret += text.charAt(i);
}

return ret;
}


function isWhiteSpace(charCode)
{
switch (charCode)
{
case (0x0009):
case (0x000B):
case (0x000C):
case (0x0020):
case (0x000A):  // '\n'
case (0x000D):  // '\r'
return true;
break;

default:
return false;
}
}


function isQuote(charCode)
{
switch (charCode)
{
case (0x0027): // single quote
case (0x0022): // double quote
return true;
break;

default:
return false;
}
}


/*
* strips off braces at beginning and end of text -
*/
function stripBraces(text)
{
// remember to escape the braces...
var arr = text.match(/^\{(.*)\}$/);

// defend against a null match...
if (arr != null && arr[1] != null)
return arr[1];
return text;
}


function test()
{
//   enterFunc ('test');
//   printBugNumber(BUGNUMBER);
//   printStatus (summary);

for (var i=0; i<UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//   exitFunc ('test');
}

}}).endType()
