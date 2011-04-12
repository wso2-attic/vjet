vjo.ctype("dsf.jslang.feature.tests.Js15ExtensionsMoreTests")
.inherits("com.ebay.dsf.jslang.feature.tests.BaseTest")
.protos({

reportCompare : function (e, a, d){
this.TestCase("", d, e, a);
},

compareSource : function (e, a, d){
this.TestCase("", d, e, a);
},

inSection : function(x) {
var SECT_PREFIX = 'Section ';
var SECT_SUFFIX = ' of test - ';
return SECT_PREFIX + x + SECT_SUFFIX;
},

gc : function () {
for (var i = 0; i != 4e6; ++i)
{
var tmp = i + 0.1;
}
},

options : function (aOptionName)
{
// return value of options() is a comma delimited list
// of the previously set values

var value = '';
for (var optionName in this.options.currvalues)
{
value += optionName + ',';
}
if (value)
{
value = value.substring(0, value.length-1);
}

if (aOptionName)
{
if (this.options.currvalues[aOptionName])
{
// option is set, toggle it to unset
delete this.options.currvalues[aOptionName];
this.options.preferences.setPref(aOptionName, false);
}
else
{
// option is not set, toggle it to set
this.options.currvalues[aOptionName] = true;
this.options.preferences.setPref(aOptionName, true);
}
}

return value;
},

jit : function (on)
{
if (on && !this.options().match(/jit/))
{
this.options('jit');
}
else if (!on && this.options().match(/jit/))
{
this.options('jit');
}
},

toLocaleFormat : function(fmt) {
var year = this.getFullYear().toString();
var mon  = (this.getMonth() + 1).toString()
var day  = (this.getDate()).toString();

if (mon.length < 2)
{
mon = '0' + mon;
}
if (day.length < 2)
{
day = '0' + day;
}
return year + mon + day;
},



constructs:function(){
this.base();
},

/** @Test
File Name:         regress__361964.js

*/
test_regress__361964: function () {
//var BUGNUMBER = 361964;
var summary = 'Crash [@ MarkGCThingChildren] involving watch and setter';
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

if (typeof document == 'undefined')
{
document = {};
}

if (typeof alert == 'undefined')
{
//alert = print;
}

// Crash:
document.watch("title", function(a,b,c,d) {
return { toString : function() { alert(1); } };
});
document.title = "xxx";

// No crash:
document.watch("title", function() {
return { toString : function() { alert(1); } };
});
document.title = "xxx";

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__363258.js

*/
test_regress__363258: function () {
//var BUGNUMBER = 363258;
var summary = 'Timer resolution';
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

var start = 0;
var stop  = 0;
var i;
var limit = 0;
var incr  = 10;
var resolution = 5;

while (stop - start == 0)
{
limit += incr;
start = Date.now();
for (i = 0; i < limit; i++) {}
stop = Date.now();
}

//print('limit=' + limit + ', resolution=' + resolution + ', time=' + (stop - start));

expect = true;
actual = (stop - start <= resolution);

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__363988.js

*/
test_regress__363988: function () {
//var BUGNUMBER = 363988;
var summary = 'Do not crash at JS_GetPrivate with large script';
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

function crash() {
var town = new Array;

for (var i = 0; i < 0x4001; ++i) {
var si = String(i);
town[i] = [ si, "x" + si, "y" + si, "z" + si ];
}

return "town=" + uneval(town) + ";function f() {}";
}

if (typeof document != "undefined")
{
// this is required to reproduce the crash.
document.write("<script>", crash(), "<\/script>");
}
else
{
crash();
}

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__365527.js

*/
test_regress__365527: function () {
//var BUGNUMBER = 365527;
var summary = 'JSOP_ARGUMENTS should set obj register';
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


counter = 500*1000;

var obj;

function getter()
{
obj = { get x() {
return getter();
}, counter: counter};
return obj;
}


var x;

function g()
{
x += this.counter;
if (--counter == 0)
throw "Done";
}


function f()
{
arguments=g;
try {
for (;;) {
arguments();
obj.x;
}
} catch (e) {
//print(e);
}
}


getter();
f();

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__365692.js

*/
test_regress__365692: function () {
//var BUGNUMBER = 365692;
var summary = 'getter/setter bytecodes should support atoms over 64k';
var actual = 'No Crash';
var expect = 'No Crash';


//printBugNumber(BUGNUMBER);
//printStatus (summary);

function g()
{
return 10;
}

try
{
var N = 100*1000;
var src = 'var x = ["';
var array = Array(N);
for (var i = 0; i != N; ++i)
array[i] = i;
src += array.join('","')+'"]; x.a getter = g; return x.a;';
var f = Function(src);
if (f() != 10)
throw "Unexpected result";
}
catch(ex)
{
if (ex == "Unexpected result")
{
actual = ex;
}
}
this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__365869.js

*/
test_regress__365869: function () {
//var BUGNUMBER = 365869;
var summary = 'strict warning for object literal with duplicate propery names';
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

if (!this.options().match(/strict/))
{
this.options('strict');
}
if (!this.options().match(/werror/))
{
this.options('werror');
}

try
{
expect = 'TypeError: redeclaration of property a';
var o = {a:4, a:5};
// syntax warning, need to eval to catch
actual = 'No warning';
}
catch(ex)
{
actual = ex + '';
//print(ex);
}

this.reportCompare(expect, actual, summary);

//print('test crash from bug 371292 Comment 3');

try
{
expect = 'TypeError: redeclaration of property 1';
var o1 = {1:1, 1:2};
// syntax warning, need to eval to catch
actual = 'No warning';
}
catch(ex)
{
actual = ex + '';
//print(ex);
}

this.reportCompare(expect, actual, summary);


//print('test crash from bug 371292 Comment 9');

try
{
expect = 'TypeError: redeclaration of const 5';
"012345".__defineSetter__(5, function(){});
}
catch(ex)
{
actual = ex + '';
//print(ex);
}

this.reportCompare(expect, actual, summary);


//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__366288.js

*/
test_regress__366288: function () {
//var BUGNUMBER = 366288;
var summary = 'Do not assert !SPROP_HAS_STUB_GETTER with __defineSetter__';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

this.__defineSetter__("x", function(){});
x = 3;

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__366292.js

*/
test_regress__366292: function () {
//var BUGNUMBER = 366292;
var summary = '__defineSetter__ and JSPROP_SHARED regression';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

expect = 'undefined';
this.__defineSetter__("x", function(){});
actual = String(x);

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__366396.js

*/
test_regress__366396: function () {
//var BUGNUMBER = 366396;
var summary = 'Do not assert !SPROP_HAS_STUB_GETTER on Setter with %=';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

this.__defineSetter__("x", function() {}); x %= 5;

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__367118__01.js

*/
test_regress__367118__01: function () {
//var BUGNUMBER = 367118;
var summary = 'memory corruption in script_compile';
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

if (typeof Script == 'undefined')
{
//print('Test skipped. Script or toSource not defined');
}
else
{
var s = new Script("");
var o = {
toString : function() {
s.compile("");
Array(11).join(Array(11).join(Array(101).join("aaaaa")));
return "a";
}
};
s.compile(o);
}
this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__367118__02.js

*/
test_regress__367118__02: function () {
//var BUGNUMBER = 367118;
var summary = 'memory corruption in script_compile';
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

if (typeof Script == 'undefined')
{
//print('Test skipped. Script or toSource not defined');
}
else
{
var s = new Script("");
var o = {
toString : function() {
s.compile("");
//print(1);
return "a";
}
};
s.compile(o);
}
this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__367119__01.js

*/
test_regress__367119__01: function () {
//var BUGNUMBER = 367119;
var summary = 'memory corruption in script_exec';
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

if (typeof Script == 'undefined')
{
//print('Test skipped. Script or toSource not defined');
}
else
{
var s = new Script("");
var o = {
valueOf : function() {
s.compile("");
Array(11).join(Array(11).join(Array(101).join("aaaaa")));
return {};
}
};
//s.exec(o);
}
this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__367119__02.js

*/
test_regress__367119__02: function () {
//var BUGNUMBER = 367119;
var summary = 'memory corruption in script_exec';
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

if (typeof Script == 'undefined')
{
//print('Test skipped. Script or toSource not defined');
}
else
{
var s = new Script("");
var o = {
valueOf : function() {
s.compile("");
//print(1);
return {};
}
};
//s.exec(o);
}
this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__367120__01.js

*/
test_regress__367120__01: function () {
//var BUGNUMBER = 367120;
var summary = 'memory corruption in script_toSource';
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

if (typeof Script == 'undefined' || !('toSource' in {}))
{
//print('Test skipped. Script or toSource not defined');
}
else
{
var s = new Script("");
var o = {
valueOf : function() {
s.compile("");
Array(11).join(Array(11).join(Array(101).join("aaaaa")));
return;
}
};
s.toSource(o);
}
this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__367120__02.js

*/
test_regress__367120__02: function () {
//var BUGNUMBER = 367120;
var summary = 'memory corruption in script_toString';
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

if (typeof Script == 'undefined')
{
//print('Test skipped. Script not defined.');
}
else
{
var s = new Script("");
var o = {
valueOf : function() {
s.compile("");
Array(11).join(Array(11).join(Array(101).join("aaaaa")));
return;
}
};
s.toString(o);
}
this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__367121.js

*/
test_regress__367121: function () {
//var BUGNUMBER = 367121;
var summary = 'self modifying script detection';
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

if (typeof window == 'undefined')
{
actual = expect = 'Test skipped - Test must be run in the browser.';
this.reportCompare(expect, actual, summary);
}
else if (typeof Script == 'undefined')
{
actual = expect = 'Test skipped - Test requires Script object..';
this.reportCompare(expect, actual, summary);
}
else
{
gDelayTestDriverEnd = true;
}

//exitFunc ('test');
//}

function handleLoad()
{
var iframe = document.body.appendChild(document.createElement('iframe'));
var d = iframe.contentDocument;

d.addEventListener("test", function(e) {
s.compile("");
Array(11).join(Array(11).join(Array(101).join("aaaaa")));
}, true);

var e = d.createEvent("Events");
e.initEvent("test", true, true);
var s = new Script("d.dispatchEvent(e);");
s.exec();

gDelayTestDriverEnd = false;
this.reportCompare(expect, actual, summary);
jsTestDriverEnd();
}

if (typeof window != 'undefined')
{
window.onload = handleLoad;
}

},

/** @Test
File Name:         regress__367501__01.js

*/
test_regress__367501__01: function () {
//var BUGNUMBER = 367501;
var summary = 'getter/setter issues';
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
expect = 'undefined';
var a = { set x() {} };
actual = a.x + '';
}
catch(ex)
{
}
this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__367501__02.js

*/
test_regress__367501__02: function () {
//var BUGNUMBER = 367501;
var summary = 'getter/setter crashes';
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
expect = 'undefined';
var a = { set x() {} };
for (var i = 0; i < 92169 - 3; ++i) a[i] = 1;
actual = a.x + '';
actual = a.x + '';
}
catch(ex)
{
}
this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__367501__03.js

*/
test_regress__367501__03: function () {
//var BUGNUMBER = 367501;
var summary = 'getter/setter crashes';
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
expect = actual = 'No Crash';
var a = { set x() {} };
for (var i = 0; i < 0x4bf20 - 3; ++i) a[i] = 1;
a.x;
a.x.x;
}
catch(ex)
{
}

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__367501__04.js

*/
test_regress__367501__04: function () {
//var BUGNUMBER = 367501;
var summary = 'getter/setter crashes';
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
expect = actual = 'No Crash';
var a = { set x() {} };
for (var i = 0; i < 0x10050c - 3; ++i) a[i] = 1;
a.x;
typeof a.x;
}
catch(ex)
{
}

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__367589.js

*/
test_regress__367589: function () {
//var BUGNUMBER = 367589;
var summary = 'Do not assert !SPROP_HAS_STUB_SETTER(sprop) || (sprop->attrs & JSPROP_GETTER)';
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

if (typeof window != 'undefined')
{
gDelayTestDriverEnd = true;
document.write('<button id="button" onclick="document.getElementsByTagName(\'button\')[0] = \'wtf\';">Crash</button>');
window.addEventListener('load', crash, false);
}
else
{
this.reportCompare(expect, actual, summary);
}

//exitFunc ('test');
//}

function crash()
{
document.getElementById('button').click();
setTimeout(checkCrash, 0);
}

function checkCrash()
{
gDelayTestDriverEnd = false;
this.reportCompare(expect, actual, summary);
jsTestDriverEnd();
}

},



/** @Test
File Name:         regress__367923.js

*/
test_regress__367923: function () {
//var BUGNUMBER = 367923;
var summary = 'strict warning for variable redeclares argument';
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

//print('This test will fail in Gecko prior to 1.9');

if (!this.options().match(/strict/))
{
this.options('strict');
}
if (!this.options().match(/werror/))
{
this.options('werror');
}

try
{
expect = 'TypeError: variable v redeclares argument';
// syntax warning, need to eval to catch
eval("(function (v) { var v; })(1)");
actual = 'No warning';
}
catch(ex)
{
actual = ex + '';
//print(ex);
}

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__368859.js

*/
test_regress__368859: function () {
//var BUGNUMBER = 368859;
var summary = 'large sharp variable numbers should not be rounded down.';
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

expect = 'SyntaxError: overlarge sharp variable number';

try
{
//print(eval('(function(){ return #65535#, #65536#; })'));
}
catch(ex)
{
actual = ex + '';
}

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__369404.js

*/
test_regress__369404: function () {
//var BUGNUMBER = 369404;
var summary = 'Do not assert: !SPROP_HAS_STUB_SETTER(sprop) || (sprop->attrs & JSPROP_GETTER) ';
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

if (typeof window != 'undefined')
{
gDelayTestDriverEnd = true;
document.write('<span id="r"> </span>' +
'<script>' +
'f = function(){};' +
'f.prototype = document.getElementById("r").childNodes;' +
'j = new f();' +
'j[0] = null;' +
'</script>');
window.addEventListener('load', crash, false);
}
else
{
this.reportCompare(expect, actual, summary);
}

//exitFunc ('test');
//}

function crash()
{
gDelayTestDriverEnd = false;
this.reportCompare(expect, actual, summary);
jsTestDriverEnd();
}

},

/** @Test
File Name:         regress__371636.js

*/
test_regress__371636: function () {
//var BUGNUMBER = 371636;
var summary = 'Numeric sort performance';
var actual = false;
var expect = '(tint/tstr < 3)=true';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

function testint(power)
{
var N = 1 << power;
var a = new Array(N);
for (var i = 0; i != N; ++i)
a[i] = (N-1) & (0x9E3779B9 * i);
var now = Date.now;
var t = now();
a.sort();
return now() - t;
}

function teststr(power)
{
var N = 1 << power;
var a = new Array(N);
for (var i = 0; i != N; ++i)
a[i] = String((N-1) & (0x9E3779B9 * i));
var now = Date.now;
var t = now();
a.sort();
return now() - t;
}

var tint = testint(18);
var tstr = teststr(18);
//print('int: ' + tint, 'str: ' + tstr, 'int/str: ' + (tint/tstr).toFixed(2));

actual = '(tint/tstr < 3)=' + (tint/tstr < 3);
this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__372309.js

*/
test_regress__372309: function () {
//var BUGNUMBER = 372309;
var summary = 'Root new array objects';
var actual = 'No Crash';
var expect = 'No Crash';

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

var width = 600;
var height = 600;

var img1canvas = document.createElement("canvas");
var img2canvas = document.createElement("canvas");

img1canvas.width = img2canvas.width = width;
img1canvas.height = img2canvas.height = height;
img1canvas.getContext("2d").getImageData(0, 0, width, height).data;
img2canvas.getContext("2d").getImageData(0, 0, width, height).data;

this.reportCompare(expect, actual, summary);
gDelayTestDriverEnd = false;
jsTestDriverEnd();

//exitFunc ('test');
//}

if (typeof window != 'undefined')
{
// delay test driver end
gDelayTestDriverEnd = true;

window.addEventListener("load", test, false);
}
else
{
this.reportCompare(expect, actual, summary);
}


},

/** @Test
File Name:         regress__374589.js

*/
test_regress__374589: function () {
//var BUGNUMBER = 374589;
var summary = 'Do not assert decompiling try { } catch(x if true) { } ' +
'catch(y) { } finally { this.a.b; }';
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

var f = function () {
try { } catch(x if true) { } catch(y) { } finally { this.a.b; } };

expect = 'function () { try { } catch(x if true) { } catch(y) { } ' +
'finally { this.a.b; } }';

actual = f + '';
this.compareSource(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__375183.js

*/
test_regress__375183: function () {
///var BUGNUMBER = 375184;
var summary = '__noSuchMethod__ should not allocate beyond fp->script->depth';
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

var obj = { get __noSuchMethod__() {
//print("Executed");
return new Object();
}};

try
{
obj.x();
}
catch(ex)
{
}

this.reportCompare(expect, actual, summary + ':1');

obj = { __noSuchMethod__: {} };
try
{
obj.x();
}
catch(ex)
{
}

this.reportCompare(expect, actual, summary + ':2');

obj = { }
obj.__noSuchMethod__ = {};
try
{
obj.x();
}
catch(ex)
{
}

this.reportCompare(expect, actual, summary + ':3');

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__375344.js

*/
test_regress__375344: function () {
//var BUGNUMBER = 375344;
var summary = 'accessing prototype of DOM objects should throw catchable error';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

if (typeof HTMLElement != 'undefined')
{
expect = /Exception... "Illegal operation on WrappedNative prototype object"/;
try
{
//print(HTMLElement.prototype.nodeName );
}
catch(ex)
{
actual = ex + '';
//print(actual);
}
reportMatch(expect, actual, summary);
}
else
{
expect = actual = 'Test can only run in a Gecko 1.9 browser or later.';
//print(actual);
this.reportCompare(expect, actual, summary);
}

},

/** @Test
File Name:         regress__375801.js

*/
test_regress__375801: function () {
//var BUGNUMBER = 375801;
var summary = 'uneval should use "(void 0)" instead of "undefined"';
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

expect = '({a: (void 0)})'
actual = uneval({a: undefined})
this.compareSource(expect, actual, summary + ': uneval');

expect = 'function() {({a: undefined});}';
actual = (function() {({a: undefined});}).toString();
this.compareSource(expect, actual, summary + ': toString');

expect = '(function () {({a: undefined});})';
actual = (function () {({a: undefined});}).toSource();
this.compareSource(expect, actual, summary + ': toSource');

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__376052.js

*/
test_regress__376052: function () {
//var BUGNUMBER = 376052;
var summary = 'javascript.options.anonfunfix to allow function (){} expressions';
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

if (typeof window != 'undefined')
{
//print('Test skipped. anonfunfix not configurable in browser.');
this.reportCompare(expect, actual, summary);
}
else
{
expect = 'No Error';
try
{
eval('function () {1;}');
actual = 'No Error';
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': 1');

this.options('anonfunfix');

expect = 'No Error';
try
{
eval('(function () {1;})');
actual = 'No Error';
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': 2');

expect = 'SyntaxError: syntax error';
try
{
eval('function () {1;}');
actual = 'No Error';
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': 3');

}

//exitFunc ('test');
//}

},



/** @Test
File Name:         regress__380581.js

*/
test_regress__380581: function () {
//var BUGNUMBER = 380581;
var summary = 'Incorrect uneval with setter in object literal';
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

//expect = '({ set x () {}})';
//actual = uneval({x setter: eval("(function () { })") });
//this.compareSource(expect, actual, summary);

expect = '(function() { })';
actual = uneval(eval("(function() { })"));
this.compareSource(expect, actual, summary);

expect = '(function() { })';
actual = uneval(eval("(function() { })"));
this.compareSource(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__380831.js

*/
test_regress__380831: function () {
//var BUGNUMBER = 380831;
var summary = 'uneval trying to output a getter function that is a sharp definition';
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

var f;

expect = '( { b getter : # 1 = ( function ( ) { } ) , c getter : # 1 # } )';
a = {};
f = function() { };
a.b = f;
a.c = f;
actual = uneval(a);
this.compareSource(expect, actual, summary);

expect = 'function ( ) { return { get x ( ) { } } ; }';
f = function() { return { x : function(){} } };
actual = f + '';
this.compareSource(expect, actual, summary);

//expect = 'function ( ) { return { x getter : # 1 = function ( ) { } } ; }';
//f = function() { return { x getter: #1=function(){} } };
//actual = f + '';
//this.compareSource(expect, actual, summary);

//expect = 'function ( ) { return { x getter : # 1 # } ; }';
//f = function() { return { x getter: #1# } };
//actual = f + '';
//this.compareSource(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__380889.js

*/
test_regress__380889: function () {
//var BUGNUMBER = 380889;
var summary = 'Source disassembler assumes SRC_SWITCH has jump table';
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

function f(i)
{
switch(i){
case 1:
case xyzzy:
}
}

if (typeof dis != 'undefined')
{
dis(f);
}

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__381205.js

*/
test_regress__381205: function () {
//var BUGNUMBER = 381205;
var summary = 'uneval with special getter functions';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

expect = '({get x p() {print(4);}})';
//getter function p() { print(4) }
actual =  uneval({getter: this.__lookupGetter__("p")});
this.reportCompare(expect, actual, summary + ': global');

},

/** @Test
File Name:         regress__381211.js

*/
test_regress__381211: function () {
//var BUGNUMBER = 381211;
var summary = 'uneval with getter';
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

expect = '( { get x() {} } )';
actual = uneval({get x(){}});
this.compareSource(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__381304.js

*/
test_regress__381304: function () {
//var BUGNUMBER = 381304;
var summary = 'getter/setter with keywords';
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

var obj;

//print('1');

obj = {
//set inn(value) {this.for = value;},
//get inn() {return this.for;}
};

expect = '({get inn() {return this.for;}, set inn(value) {this.for = value;}})';
actual = obj.toSource();
this.compareSource(expect, actual, summary + ': 1');

//print('2');

obj = {
//set in(value) {this.for = value;},
//get in() {return this.for;}
};

expect = '( { in getter : ( function ( ) { return this . for ; } ) , in setter : ( function ( value ) { this . for = value ; } ) } )';
actual = obj.toSource();
this.compareSource(expect, actual, summary + ': 2');

//print('3');

obj = {
//get in(value) {this.for = value;},
//set in() {return this.for;}
};

expect = '( { in getter : ( function ( value ) { this . for = value ; } ) , in setter : ( function ( ) { return this . for ; } ) } ) ';
actual = obj.toSource();
this.compareSource(expect, actual, summary + ': 3');

//print('4');

obj = {
//set inn(value) {this.for = value;},
//get in() {return this.for;}
};

expect = '( { set inn ( value ) { this . for = value ; } , in getter : ( function ( ) { return this . for ; } ) } )';
actual = obj.toSource();
this.compareSource(expect, actual, summary + ': 4');

//print('5');

obj = {
//set in(value) {this.for = value;},
//get inn() {return this.for;}
};

expect = ' ( { in setter : ( function ( value ) { this . for = value ; } ) , get inn ( ) { return this . for ; } } ) ';
actual = obj.toSource();
this.compareSource(expect, actual, summary + ': 5');
//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__382509.js

*/
test_regress__382509: function () {
//var BUGNUMBER = 382509;
var summary = 'Disallow non-global indirect eval';
var actual = '';
var expect = '';

var global = typeof window == 'undefined' ? this : window;
var object = {};

//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

if (this.options().match(/strict/))
{
this.options('strict');
}
if (this.options().match(/werror/))
{
this.options('werror');
}

global.foo = eval;
global.a   = 'global';
expect = 'global indirect';
actual = global.foo('a+" indirect"');
this.reportCompare(expect, actual, summary + ': global indirect');

object.foo = eval;
object.a   = 'local';
expect = 'EvalError: function eval must be called directly, and not by way of a function of another name';
try
{
actual = object.foo('a+" indirect"');
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': local indirect');

this.options('strict');
this.options('werror');

try
{
var foo = eval;
//print("foo(1+1)" + foo('1+1'));
actual = 'No Error';
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': strict, rename warning');

this.options('strict');
this.options('werror');

expect = 'No Error';
try
{
var foo = eval;
foo('1+1');
actual = 'No Error';
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': not strict, no rename warning');

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__383965.js

*/
test_regress__383965: function () {
//var BUGNUMBER = 383965;
var summary = 'getter function with toSource';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

expect = /({get aaa :{}})|({aaa:{prototype:{}}})/;

//getter function aaa(){};
var obj = {};
var gett = this.__lookupGetter__("aaa");
gett.__proto__ = obj;
obj.__defineGetter__("aaa", gett);
actual = obj.toSource();

reportMatch(expect, actual, summary);

},

/** @Test
File Name:         regress__384680.js

*/
test_regress__384680: function () {
//var BUGNUMBER = 384680;
var summary = 'Round-trip change in decompilation with paren useless expression';
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

expect = 'function() {}';

var f = (function() { (3); });
actual = f + '';
this.compareSource(expect, actual, summary + ': f');

var g = eval('(' + f + ')');
actual = g + '';
this.compareSource(expect, actual, summary + ': g');

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__385134.js

*/
test_regress__385134: function () {
//var BUGNUMBER = 385134;
var summary = 'Do not crash with setter, watch, uneval';
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

if (typeof this.__defineSetter__ != 'undefined' &&
typeof this.watch != 'undefined' &&
typeof uneval != 'undefined')
{
this.__defineSetter__(0, function(){});
this.watch(0, function(){});
uneval(this);
}
this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__385393__02.js

*/
test_regress__385393__02: function () {
//var BUGNUMBER = 385393;
var summary = 'Regression test for bug 385393';
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

try
{
(4).__lookupGetter__("w");
}
catch(ex)
{
}

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__385393__09.js

*/
test_regress__385393__09: function () {
//var BUGNUMBER = 385393;
var summary = 'Regression test for bug 385393';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

eval("this.__defineSetter__('x', this.gc); this.watch('x', [].slice); x = 1;");

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__390597.js

*/
test_regress__390597: function () {
//var BUGNUMBER = 390597;
var summary = 'watch point + eval-as-setter allows access to dead JSStackFrame';
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

function exploit() {
try
{
var obj = this, args = null;
obj.__defineSetter__("evil", eval);
obj.watch("evil", function() { return "args = arguments;"; });
obj.evil = null;
eval("print(args[0]);");
}
catch(ex)
{
//print('Caught ' + ex);
}
}
exploit();

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__394967.js

*/
test_regress__394967: function () {
//var BUGNUMBER = 394967;
var summary = 'Do not assert: !JSVAL_IS_PRIMITIVE(vp[1])';
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

if (typeof evalcx == 'undefined')
{
//print('Skipping. This test requires evalcx.');
}
else
{
var sandbox = evalcx("");
try
{
evalcx("(1)()", sandbox);
}
catch(ex)
{
}
}

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__396326.js

*/
test_regress__396326: function () {
//var BUGNUMBER = 396326;
var summary = 'Do not assert trying to disassemble get(var|arg) prop';
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

if (typeof dis == 'undefined')
{
//print('disassembly not supported. test skipped.');
this.reportCompare(expect, actual, summary);
}
else
{
function f1() { var v; return v.prop };
dis(f1);
this.reportCompare(expect, actual, summary +
': function f1() { var v; return v.prop };');

function f2(arg) { return arg.prop };
dis(f2);
this.reportCompare(expect, actual, summary +
': function f2(arg) { return arg.prop };');

function f3() { return this.prop };
dis(f3);
this.reportCompare(expect, actual, summary +
': function f3() { return this.prop };');
}

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__406572.js

*/
test_regress__406572: function () {
//var BUGNUMBER = 406572;
var summary = 'JSOP_CLOSURE unconditionally replaces properties of the variable object - Browser only';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

if (typeof window != 'undefined')
{
try
{
expect = 'TypeError: redeclaration of const document';
var d = document;

d.writeln(uneval(document));
document = 1;
d.writeln(uneval(document));

if (1)
function document() { return 1; }

d.writeln(uneval(document));
}
catch(ex)
{
actual = ex + '';
//print(actual);
}
}
else
{
expect = actual = 'Test can only run in a Gecko 1.9 browser or later.';
//print(actual);
}
this.reportCompare(expect, actual, summary);
},

/** @Test
File Name:         regress__407019.js

*/
test_regress__407019: function () {
//var BUGNUMBER = 407019;
var summary = 'Do not assert: !JS_IsExceptionPending(cx) - Browser only';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

if (typeof window != 'undefined' && typeof window.Option == 'function' && '__proto__' in window.Option)
{
try
{
expect = /Illegal operation on WrappedNative prototype object/;
window.Option("u", window.Option.__proto__);
for (p in document) { }
actual = 'No Error';
}
catch(ex)
{
actual = ex + '';
}
reportMatch(expect, actual, summary);
}
else
{
expect = actual = 'Test can only run in a Gecko 1.9 browser or later.';
//print(actual);
this.reportCompare(expect, actual, summary);
}

},

/** @Test
File Name:         regress__407501.js

*/
test_regress__407501: function () {
//var BUGNUMBER = 407501;
var summary = 'JSOP_NEWINIT lacks SAVE_SP_AND_PC ';
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

if (typeof gczeal == 'function')
{
gczeal(2);
}

var a = [[[[[[[0]]]]]]];
if (uneval(a).length == 0)
throw "Unexpected result";

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__407720.js

*/
test_regress__407720: function () {
//var BUGNUMBER = 407720;
var summary = 'js_FindClassObject causes crashes with getter/setter - Browser only';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

// stop the test after 60 seconds
var start = new Date();

if (typeof document != 'undefined')
{
// delay test driver end
gDelayTestDriverEnd = true;
document.write('<iframe onload="onLoad()"><\/iframe>');
}
else
{
actual = 'No Crash';
this.reportCompare(expect, actual, summary);
}

function onLoad()
{

if ( (new Date() - start) < 60*1000)
{
var x = frames[0].Window.prototype;
x.a = x.b = x.c = 1;
x.__defineGetter__("HTML document.all class", function() {});
frames[0].document.all;

// retry
frames[0].location = "about:blank";
}
else
{
actual = 'No Crash';

this.reportCompare(expect, actual, summary);
gDelayTestDriverEnd = false;
jsTestDriverEnd();
}
}

},

/** @Test
File Name:         regress__412926.js

*/
test_regress__412926: function () {
//var BUGNUMBER = 412926;
var summary = 'JS_ValueToId(cx, JSVAL_NULL) should return atom for "null" string';
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

actual = expect = 'No Errors';

var obj = { 'null': 1 };

var errors = [];

if (!obj.hasOwnProperty(null))
errors.push('null property is not owned');

if (!obj.propertyIsEnumerable(null))
errors.push('null property is not enumerable');

var getter_was_called = false;
obj.__defineGetter__(null, function() { getter_was_called = true; return 1; });
obj['null'];

if (!getter_was_called)
errors.push('getter was not assigned to the null property');

var setter_was_called = false;
obj.__defineSetter__(null, function() { setter_was_called = true; });
obj['null'] = 2;

if (!setter_was_called)
errors.push('setter was not assigned to the null property');

if (errors.length)
actual = errors.join('; ');

this.gc();

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__414755.js

*/
test_regress__414755: function () {
//var BUGNUMBER = 414755;
var summary = 'GC hazard due to missing SAVE_SP_AND_PC';
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

function f()
{
var a = 1e10;
var b = 2e10;
var c = 3e10;

return (a*2) * ((b*2) * c);
}

if (typeof gczeal != 'undefined')
{
expect = f();

gczeal(2);

actual = f();
}
else
{
expect = actual = 'Test requires gczeal, skipped.';
}

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__416354.js

*/
test_regress__416354: function () {
//var BUGNUMBER = 416354;
var summary = 'GC hazard due to missing SAVE_SP_AND_PC';
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

function f(a, b, c)
{
return (-a) * ((-b) * (-c));
}

if (typeof gczeal != 'undefined')
{
expect = f(1.5, 1.25, 1.125);
gczeal(2);
actual = f(1.5, 1.25, 1.125);
}
else
{
expect = actual = 'Test requires gczeal, skipped.';
}

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__416460.js

*/
test_regress__416460: function () {
//var BUGNUMBER = 416460;
var summary = 'Do not assert: SCOPE_GET_PROPERTY(OBJ_SCOPE(pobj), ATOM_TO_JSID(atom))';
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

/a/.__proto__.__proto__ = { "2": 3 };
var b = /b/;
b["2"];
b["2"];

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__416834.js

*/
test_regress__416834: function () {
//var BUGNUMBER = 416834;
var summary = 'Do not assert: !entry || entry->kpc == ((PCVCAP_TAG(entry->vcap) > 1) ? (jsbytecode *) JSID_TO_ATOM(id) : cx->fp->pc)';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

this.__proto__.x = eval;
for (i = 0; i < 16; ++i)
delete eval;
(function w() { x = 1; })();

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__418730.js

*/
test_regress__418730: function () {
//var BUGNUMBER = 418730;
var summary = 'export * should not halt script';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

for (var i = 0; i < 60; ++i)
this["v" + i] = true;

expect = 'PASS';
actual = 'FAIL';

//try {
//print("GO");
//export *;
//print("PASS (1)");
//} catch(e) {
//print("PASS (2)");
//print(e);
//}

actual = 'PASS';

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__420612.js

*/
test_regress__420612: function () {
//var BUGNUMBER = 420612;
var summary = 'Do not assert: obj == pobj';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);
try
{
this.__proto__ = [];
this.unwatch("x");
}
catch(ex)
{
//print(ex + '');
if (typeof window != 'undefined')
{
expect = 'Error: invalid __proto__ value (can only be set to null)';
}
actual = ex + '';
}

//this.reportCompare(expect, actual, summary);
this.TestCase("", summary, expect, actual)

},

/** @Test
File Name:         regress__420869__01.js

*/
test_regress__420869__01: function () {
//var BUGNUMBER = 424683;
var summary = 'Throw too much recursion instead of script stack space quota';
var actual = 'No Error';
var expect = 'InternalError: too much recursion';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

function f(i) {
if (i == 0)
return 1;
return i*f(i-1);
}

try
{
f();
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__421621.js

*/
test_regress__421621: function () {
//var BUGNUMBER = 421621;
var summary = 'Do not assert with setter, export/import: (sprop)->slot != SPROP_INVALID_SLOT || !SPROP_HAS_STUB_SETTER(sprop)';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var th = this;
this.__defineSetter__('x', function () {});
//export *;
//import th.*;
x;

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__422137.js

*/
test_regress__422137: function () {
//var BUGNUMBER = 422137;
var summary = 'Do not assert or bogo OOM with debugger trap on JOF_CALL bytecode';
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

function f() { return a(); }

if (typeof trap == 'function')
{
trap(f, 0, "print('trap')");
}
f + '';

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__422592.js

*/
test_regress__422592: function () {
//var BUGNUMBER = 422592;
var summary = 'js.c dis/dissrc should not kill script execution';
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

if (typeof dis == 'undefined')
{
expect = actual = 'Test requires function dis. Not tested';
//print(expect);
}
else
{
expect = 'Completed';
actual = 'Not Completed';
//print('Before dis');
try
{
dis(print);
}
catch(ex)
{
//print(ex + '');
}
//print('After dis');
actual = 'Completed';
}
this.reportCompare(expect, actual, summary);

if (typeof dissrc == 'undefined')
{
expect = actual = 'Test requires function dissrc. Not tested';
//print(expect);
}
else
{
//print('Before dissrc');
expect = 'Completed';
actual = 'Not Completed';
try
{
dissrc(print);
}
catch(ex)
{
//print(ex + '');
}
//print('After dissrc');
actual = 'Completed';
}
this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__424257.js

*/
test_regress__424257: function () {
//var BUGNUMBER = 424257;
var summary = 'Do not assert: op2 == JSOP_INITELEM';
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

try
{
eval("var x; while(x getter={});");
}
catch(ex)
{
expect = 'SyntaxError: invalid getter usage';
actual = ex + '';
}

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__424683__01.js

*/
test_regress__424683__01: function () {
//var BUGNUMBER = 424683;
var summary = 'Throw too much recursion instead of script stack space quota';
var actual = 'No Error';
var expect = 'InternalError: too much recursion';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

function f() { f(); }

try
{
f();
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__426711.js

*/
test_regress__426711: function () {
//var BUGNUMBER = 426711;
var summary = 'Setting window.__count__ causes a crash';
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

if (typeof window != 'undefined' && '__count__' in window)
{
window.__count__ = 0;
}
else
{
expect = actual = 'Test skipped. Requires window.__count__';
}
this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__429264.js

*/
test_regress__429264: function () {
//var BUGNUMBER = 429264;
var summary = 'Do not assert: top < ss->printer->script->depth';
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

function f() { for(; 1; ) { } }
if (typeof trap == 'function')
{
trap(f, 0, "");
}
f + '';

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__431428.js

*/
test_regress__431428: function () {
//var BUGNUMBER = 431428;
var summary = 'Do not crash with for..in, trap';
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

function f() {
for ( var a in [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17]) { }
}

if (typeof trap == 'function')
{
"" + f;
trap(f, 0, "");
"" + f;
}

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__432075.js

*/
test_regress__432075: function () {
var BUGNUMBER = 432075;
var summary = 'A function decompiles as [object Function] after export/import';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var a = Function;
var t = this;
//export *;
//import t.*;
Function = a;

expect = 'function anonymous() {}';
actual = (new Function("")) + '';

this.compareSource(expect, actual, summary);

},

/** @Test
File Name:         regress__434837__01.js

*/
test_regress__434837__01: function () {
//var BUGNUMBER = 434837;
var summary = '|this| in accessors in prototype chain of array';
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
expect = true;
actual = null;
x = [ "one", "two" ];
Array.prototype.__defineGetter__('test1', function() { actual = (this === x); });
x.test1;
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': x.test1');

try
{
expect = false;
actual = null;
Array.prototype.__defineGetter__('test2', function() { actual = (this === Array.prototype) });
x.test2;
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': x.test2');

Array.prototype.__defineGetter__('test3', function() { actual = (this === x) });
Array.prototype.__defineSetter__('test3', function() { actual = (this === x) });

try
{
expect = true;
actual = null;
x.test3;
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': x.test3 (1)');

try
{
expect = true;
actual = null;
x.test3 = 5;
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': x.test3 = 5');

try
{
expect = true;
actual = null;
x.test3;
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': x.test3 (2)');

try
{
var y = ['a', 'b', 'c', 'd'];
expect = 4;
actual = y.__count__;
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': y.__count__');

try
{
expect = 0;
actual = [].__count__;
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': [].__count__');

try
{
expect = 1;
actual = [1].__count__;
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': [1].__count__');

try
{
expect = 9;
actual = [1,2,3,4,5,6,7,8,9].__count__;
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': [1].__count__');

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__435345__01.js

*/
test_regress__435345__01: function () {
//var BUGNUMBER = 435345;
var summary = 'Watch the length property of arrays';
var actual = '';
var expect = '';

// see http://developer.mozilla.org/en/docs/Core_JavaScript_1.5_Reference:Objects:Object:watch

//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

var arr;

try
{
expect = 'watcher: propname=length, oldval=0, newval=1; ';
actual = '';
arr = [];
arr.watch('length', watcher);
arr[0] = '0';
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': 1');

try
{
expect = 'watcher: propname=length, oldval=1, newval=2; ' +
'watcher: propname=length, oldval=2, newval=2; ';
actual = '';
arr.push(5);
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': 2');

try
{
expect = 'watcher: propname=length, oldval=2, newval=1; ';
actual = '';
arr.pop();
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': 3');

try
{
expect = 'watcher: propname=length, oldval=1, newval=2; ';
actual = '';
arr.length++;
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': 4');

try
{
expect = 'watcher: propname=length, oldval=2, newval=5; ';
actual = '';
arr.length = 5;
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': 5');

//exitFunc ('test');
//}

function watcher(propname, oldval, newval)
{
actual += 'watcher: propname=' + propname + ', oldval=' + oldval +
', newval=' + newval + '; ';

return newval;
}

},

/** @Test
File Name:         regress__435497__01.js

*/
test_regress__435497__01: function () {
//var BUGNUMBER = 435497;
var summary = 'Do not assert op2 == JSOP_INITELEM';
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

try
{
eval('(function() { x, x setter = 0, y; const x; })();');
}
catch(ex)
{
}

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__435497__02.js

*/
test_regress__435497__02: function () {
//var BUGNUMBER = 435497;
var summary = 'Do not assert op2 == JSOP_INITELEM';
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

try
{
eval('(function() { x setter = 0, y; const x; })();');
}
catch(ex)
{
}

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__435497__03.js

*/
test_regress__435497__03: function () {
//var BUGNUMBER = 435497;
var summary = 'Do not assert op2 == JSOP_INITELEM';
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

try
{
eval('(function() { x getter= function(){} ; var x5, x = 0x99; })();');
}
catch(ex)
{
}

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__437288__01.js

*/
test_regress__437288__01: function () {
//var BUGNUMBER = 437288;
var summary = 'for loop turning into a while loop';
var actual = 'No Hang';
var expect = 'No Hang';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

(function() { const x = 1; for (x in null); })();

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__44009.js
Date: 26 Feb 2001
See http://bugzilla.mozilla.org/show_bug.cgi?id=44009

SUMMARY:  Testing that we don't crash on obj.toSource()
*/
test_regress__44009: function () {
//var BUGNUMBER = 44009;
var summary = "Testing that we don't crash on obj.toSource()";
var obj1 = {};
var sToSource = '';
var self = this;  //capture a reference to the global JS object -



//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

var obj2 = {};

// test various objects and scopes -
testThis(self);
testThis(this);
testThis(obj1);
testThis(obj2);

this.reportCompare('No Crash', 'No Crash', '');

//exitFunc ('test');
//}


// We're just testing that we don't crash by doing this -
function testThis(obj)
{
sToSource = obj.toSource();
obj.prop = obj;
sToSource = obj.toSource();
}

},

/** @Test
File Name:         regress__443569.js

*/
test_regress__443569: function () {
//var BUGNUMBER = 443569;
var summary = 'Do not assert: OBJ_IS_NATIVE(obj)';
var actual = 'No Crash';
var expect = 'No Crash';


//printBugNumber(BUGNUMBER);
//printStatus (summary);

if (typeof window != 'undefined')
{
// delay test driver end
gDelayTestDriverEnd = true;

window.addEventListener("load", boom, false);
}
else
{
this.reportCompare(expect, actual, summary);
}

function boom()
{
var r = RegExp.prototype;
r["-1"] = 0;
Array.prototype.__proto__ = r;
[]["-1"];

this.reportCompare(expect, actual, summary);

gDelayTestDriverEnd = false;
jsTestDriverEnd();
}

},

/** @Test
File Name:         regress__446386.js

*/
test_regress__446386: function () {
//var BUGNUMBER = 446386;
var summary = 'Do not crash throwing error without compiler pseudo-frame';
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

if (typeof evalcx == 'undefined')
{
//print(expect = actual = 'Test skipped. evalcx required.');
}
else {
try
{
try {
evalcx(".");
throw "must throw";
} catch (e) {
if (e.name != "SyntaxError")
throw e;
}
}
catch(ex)
{
actual = ex + '';
}
}

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__452168.js

*/
test_regress__452168: function () {
//var BUGNUMBER = 452168;
var summary = 'Do not crash with gczeal 2, JIT: @ avmplus::List or @ nanojit::LirBuffer::validate';
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

if (typeof gczeal == 'undefined')
{
expect = actual = 'Test requires gczeal, skipped.';
}
else
{
this.jit(true);
gczeal(2);

var a, b; gczeal(2); (function() { for (var p in this) { } })();

gczeal(0);
this.jit(false);
}
this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__452178.js

*/
test_regress__452178: function () {
//var BUGNUMBER = 452178;
var summary = 'Do not assert with JIT: !(sprop->attrs & JSPROP_SHARED)';
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

this.jit(true);

getter= function(){}; for (var j = 0; j < 4; ++j) q = 1;

this.jit(false);

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__452329.js

*/
test_regress__452329: function () {
//var BUGNUMBER = 452329;
var summary = 'Do not assert: *data->pc == JSOP_CALL || *data->pc == JSOP_NEW';
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

this.__defineGetter__("x", "".match); if (x) 3;

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__452338.js

*/
test_regress__452338: function () {
//var BUGNUMBER = 452338;
var summary = 'Do not assert with JIT: obj2 == obj';
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

this.jit(true);

for (var j = 0; j < 4; ++j) __count__ = 3;

this.jit(false);

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__452372.js

*/
test_regress__452372: function () {
//var BUGNUMBER = 452372;
var summary = 'Do not assert with JIT: entry->kpc == (jsbytecode*) atom';
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

this.jit(true);

eval("(function() { for (var j = 0; j < 4; ++j) { /x/.__parent__; } })")();

this.jit(false);

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__452565.js

*/
test_regress__452565: function () {
//var BUGNUMBER = 452565;
var summary = 'Do not assert with JIT: !(sprop->attrs & JSPROP_READONLY)';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

this.jit(true);

const c; (function() { for (var j=0;j<5;++j) { c = 1; } })();

this.jit(false);

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__453249.js

*/
test_regress__453249: function () {
//var BUGNUMBER = 453249;
var summary = 'Do not assert with JIT: s0->isQuad()';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

this.jit(true);

this.__proto__.a = 3; for (var j = 0; j < 4; ++j) { [a]; }

this.a = 3; for (var j = 0; j < 4; ++j) { [a]; }

this.jit(false);

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__454040.js

*/
test_regress__454040: function () {
//var BUGNUMBER = 454040;
var summary = 'Do not crash @ js_ComputeFilename';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

try
{
this.__defineGetter__("x", Function);
this.__defineSetter__("x", Function);
this.watch("x", x.__proto__);
x = 1;
}
catch(ex)
{
//print(ex + '');
}
this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__455380.js

*/
test_regress__455380: function () {
//var BUGNUMBER = 455380;
var summary = 'Do not assert with JIT: !lhs->isQuad() && !rhs->isQuad()';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

this.jit(true);

const IS_TOKEN_ARRAY =
[0, 0, 0, 0, 0, 0, 0, 0, //   0
0, 0, 0, 0, 0, 0, 0, 0, //   8
0, 0, 0, 0, 0, 0, 0, 0, //  16
0, 0, 0, 0, 0, 0, 0, 0, //  24

0, 1, 0, 1, 1, 1, 1, 1, //  32
0, 0, 1, 1, 0, 1, 1, 0, //  40
1, 1, 1, 1, 1, 1, 1, 1, //  48
1, 1, 0, 0, 0, 0, 0, 0, //  56

0, 1, 1, 1, 1, 1, 1, 1, //  64
1, 1, 1, 1, 1, 1, 1, 1, //  72
1, 1, 1, 1, 1, 1, 1, 1, //  80
1, 1, 1, 0, 0, 0, 1, 1, //  88

1, 1, 1, 1, 1, 1, 1, 1, //  96
1, 1, 1, 1, 1, 1, 1, 1, // 104
1, 1, 1, 1, 1, 1, 1, 1, // 112
1, 1, 1, 0, 1, 0, 1];   // 120

const headerUtils = {
normalizeFieldName: function(fieldName)
{
if (fieldName == "")
throw "error: empty string";

for (var i = 0, sz = fieldName.length; i < sz; i++)
{
if (!IS_TOKEN_ARRAY[fieldName.charCodeAt(i)])
{
throw (fieldName + " is not a valid header field name!");
}
}

return fieldName.toLowerCase();
}
};

headerUtils.normalizeFieldName("Host");

this.jit(false);

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__455408.js

*/
test_regress__455408: function () {
//var BUGNUMBER = 455408;
var summary = 'Do not assert with JIT: "Should not move data from GPR to XMM": false';
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

this.jit(true);

for (var j = 0; j < 5; ++j) { if (({}).__proto__ = 1) { } }

this.jit(false);

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__455413.js

*/
test_regress__455413: function () {
//var BUGNUMBER = 455413;
var summary = 'Do not assert with JIT: (m != JSVAL_INT) || isInt32(*vp)';
var actual = 'No Crash';
var expect = 'No Crash';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//printBugNumber(BUGNUMBER);
//printStatus (summary);

this.jit(true);

this.watch('x', Math.pow); (function() { for(var j=0;j<4;++j){x=1;} })();

this.jit(false);

this.reportCompare(expect, actual, summary);

},

/** @Test
File Name:         regress__459606.js

*/
test_regress__459606: function () {
//var BUGNUMBER = 459606;
var summary = '((0.1).toFixed()).toSource()';
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

expect = '(new String("0"))';
actual = ((0.1).toFixed()).toSource();

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__465276.js

*/
test_regress__465276: function () {
//var BUGNUMBER = 465276;
var summary = '((1 * (1))|""';
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

this.jit(true);

expect = '[1, 1, 1, 1, 1, 1, 1, 1, 1, 1]';
empty = [];
out = [];
for (var j=0;j<10;++j) { empty[42]; out.push((1 * (1)) | ""); }
//print(actual = uneval(out));

this.jit(false);

this.reportCompare(expect, actual, summary);

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__50447__1.js
SUMMARY: New properties fileName, lineNumber have been added to Error objects
in SpiderMonkey. These are non-ECMA extensions and do not exist in Rhino.

See http://bugzilla.mozilla.org/show_bug.cgi?id=50447

2005-04-05 Modified by bclary to support changes to error reporting
which set default values for the error's fileName and
lineNumber properties.
*/
test_regress__50447__1: function () {
//var BUGNUMBER = 50447;
var summary = 'Test (non-ECMA) Error object properties fileName, lineNumber';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

testRealError(this);
test1(this);
test2(this);
test3(this);
test4(this);

//exitFunc('test');
//}


function testRealError(obj)
{
/* throw a real error, and see what it looks like */
//enterFunc ("testRealError");

try
{
blabla;
}
catch (e)
{
if (e.fileName.search (/-50447-1\.js$/i) == -1)
obj.reportCompare('PASS', 'FAIL', "expected fileName to end with '-50447-1.js'");

obj.reportCompare(87, e.lineNumber,
"lineNumber property returned unexpected value.");
}

//exitFunc ("testRealError");
}


function test1(obj)
{
/* generate an error with msg, file, and lineno properties */
//enterFunc ("test1");

var e = new InternalError ("msg", "file", 2);
obj.reportCompare ("(new InternalError(\"msg\", \"file\", 2))",
e.toSource(),
"toSource() returned unexpected result.");
obj.reportCompare ("file", e.fileName,
"fileName property returned unexpected value.");
obj.reportCompare (2, e.lineNumber,
"lineNumber property returned unexpected value.");

//exitFunc ("test1");
}


function test2(obj)
{
/* generate an error with only msg property */
//enterFunc ("test2");

/* note this test incorporates the path to the
test file and assumes the path to the test case
is a subdirectory of the directory containing jsDriver.pl
*/
var expectedLine = 141;
var expectedFileName = 'js1_5/extensions/regress-50447-1.js';
if (typeof document == "undefined")
{
expectedFileName = './' + expectedFileName;
}
else
{
expectedFileName = document.location.href.
replace(/[^\/]*(\?.*)$/, '') +
expectedFileName;
}
var e = new InternalError ("msg");
obj.reportCompare ("(new InternalError(\"msg\", \"" +
expectedFileName + "\", " + expectedLine + "))",
e.toSource(),
"toSource() returned unexpected result.");
obj.reportCompare (expectedFileName, e.fileName,
"fileName property returned unexpected value.");
obj.reportCompare (expectedLine, e.lineNumber,
"lineNumber property returned unexpected value.");

//exitFunc ("test2");
}


function test3(obj)
{
/* generate an error with only msg and lineNo properties */

/* note this test incorporates the path to the
test file and assumes the path to the test case
is a subdirectory of the directory containing jsDriver.pl
*/

//enterFunc ("test3");

var expectedFileName = 'js1_5/extensions/regress-50447-1.js';
if (typeof document == "undefined")
{
expectedFileName = './' + expectedFileName;
}
else
{
expectedFileName = document.location.href.
replace(/[^\/]*(\?.*)$/, '') +
expectedFileName;
}

var e = new InternalError ("msg");
e.lineNumber = 10;
obj.reportCompare ("(new InternalError(\"msg\", \"" +
expectedFileName + "\", 10))",
e.toSource(),
"toSource() returned unexpected result.");
obj.reportCompare (expectedFileName, e.fileName,
"fileName property returned unexpected value.");
obj.reportCompare (10, e.lineNumber,
"lineNumber property returned unexpected value.");

//exitFunc ("test3");
}


function test4(obj)
{
/* generate an error with only msg and filename properties */
//enterFunc ("test4");

var expectedLine = 200;

var e = new InternalError ("msg", "file");
obj.reportCompare ("(new InternalError(\"msg\", \"file\", " + expectedLine + "))",
e.toSource(),
"toSource() returned unexpected result.");
obj.reportCompare ("file", e.fileName,
"fileName property returned unexpected value.");
obj.reportCompare (expectedLine, e.lineNumber,
"lineNumber property returned unexpected value.");

//exitFunc ("test4");
}

},

/** @Test
File Name:         regress__90596__001.js
Date: 28 August 2001

SUMMARY: A [DontEnum] prop, if overridden, should appear in toSource().
See http://bugzilla.mozilla.org/show_bug.cgi?id=90596

NOTE: some inefficiencies in the test are made for the sake of readability.
Sorting properties alphabetically is done for definiteness in comparisons.
*/
test_regress__90596__001: function () {
var UBound = 0;
//var BUGNUMBER = 90596;
var summary = 'A [DontEnum] prop, if overridden, should appear in toSource()';
var cnCOMMA = ',';
var cnLBRACE = '{';
var cnRBRACE = '}';
var cnLPAREN = '(';
var cnRPAREN = ')';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];
var obj = {};


status = this.inSection(1);
obj = {toString:9};
actual = obj.toSource();
expect = '({toString:9})';
addThis();

status = this.inSection(2);
obj = {hasOwnProperty:"Hi"};
actual = obj.toSource();
expect = '({hasOwnProperty:"Hi"})';
addThis();

status = this.inSection(3);
obj = {toString:9, hasOwnProperty:"Hi"};
actual = obj.toSource();
expect = '({toString:9, hasOwnProperty:"Hi"})';
addThis();

status = this.inSection(4);
obj = {prop1:1, toString:9, hasOwnProperty:"Hi"};
actual = obj.toSource();
expect = '({prop1:1, toString:9, hasOwnProperty:"Hi"})';
addThis();


// TRY THE SAME THING IN EVAL CODE
var s = '';

status = this.inSection(5);
s = 'obj = {toString:9}';
eval(s);
actual = obj.toSource();
expect = '({toString:9})';
addThis();

status = this.inSection(6);
s = 'obj = {hasOwnProperty:"Hi"}';
eval(s);
actual = obj.toSource();
expect = '({hasOwnProperty:"Hi"})';
addThis();

status = this.inSection(7);
s = 'obj = {toString:9, hasOwnProperty:"Hi"}';
eval(s);
actual = obj.toSource();
expect = '({toString:9, hasOwnProperty:"Hi"})';
addThis();

status = this.inSection(8);
s = 'obj = {prop1:1, toString:9, hasOwnProperty:"Hi"}';
eval(s);
actual = obj.toSource();
expect = '({prop1:1, toString:9, hasOwnProperty:"Hi"})';
addThis();


// TRY THE SAME THING IN FUNCTION CODE
function A(obj)
{
status = obj.inSection(9);
var s = 'obj = {toString:9}';
eval(s);
actual = obj.toSource();
expect = '({toString:9})';
addThis();
}
A(this);

function B(obj)
{
status = obj.inSection(10);
var s = 'obj = {hasOwnProperty:"Hi"}';
eval(s);
actual = obj.toSource();
expect = '({hasOwnProperty:"Hi"})';
addThis();
}
B(this);

function C(obj)
{
status = obj.inSection(11);
var s = 'obj = {toString:9, hasOwnProperty:"Hi"}';
eval(s);
actual = obj.toSource();
expect = '({toString:9, hasOwnProperty:"Hi"})';
addThis();
}
C(this);

function D(obj)
{
status = obj.inSection(12);
var s = 'obj = {prop1:1, toString:9, hasOwnProperty:"Hi"}';
eval(s);
actual = obj.toSource();
expect = '({prop1:1, toString:9, hasOwnProperty:"Hi"})';
addThis();
}
D(this);



//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------



/*
* Sort properties alphabetically -
*/
function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = sortThis(actual);
expectedvalues[UBound] = sortThis(expect);
UBound++;
}


/*
* Takes string of form '({"c", "b", "a", 2})' and returns '({"a","b","c",2})'
*/
function sortThis(sList)
{
sList = compactThis(sList);
sList = stripParens(sList);
sList = stripBraces(sList);
var arr = sList.split(cnCOMMA);
arr = arr.sort();
var ret = String(arr);
ret = addBraces(ret);
ret = addParens(ret);
return ret;
}


/*
* Strips out any whitespace from the text -
*/
function compactThis(text)
{
var charCode = 0;
var ret = '';

for (var i=0; i<text.length; i++)
{
charCode = text.charCodeAt(i);

if (!isWhiteSpace(charCode))
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


/*
* strips off parens at beginning and end of text -
*/
function stripParens(text)
{
// remember to escape the parens...
var arr = text.match(/^\((.*)\)$/);

// defend against a null match...
if (arr != null && arr[1] != null)
return arr[1];
return text;
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


function addBraces(text)
{
return cnLBRACE + text + cnRBRACE;
}


function addParens(text)
{
return cnLPAREN + text + cnRPAREN;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

for (var i=0; i<UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__90596__002.js
Date: 28 August 2001

SUMMARY: A [DontEnum] prop, if overridden, should appear in uneval().
See http://bugzilla.mozilla.org/show_bug.cgi?id=90596

NOTE: some inefficiencies in the test are made for the sake of readability.
Sorting properties alphabetically is done for definiteness in comparisons.
*/
test_regress__90596__002: function () {
var UBound = 0;
//var BUGNUMBER = 90596;
var summary = 'A [DontEnum] prop, if overridden, should appear in uneval()';
var cnCOMMA = ',';
var cnLBRACE = '{';
var cnRBRACE = '}';
var cnLPAREN = '(';
var cnRPAREN = ')';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];
var obj = {};


status = this.inSection(1);
obj = {toString:9};
actual = uneval(obj);
expect = '({toString:9})';
addThis();

status = this.inSection(2);
obj = {hasOwnProperty:"Hi"};
actual = uneval(obj);
expect = '({hasOwnProperty:"Hi"})';
addThis();

status = this.inSection(3);
obj = {toString:9, hasOwnProperty:"Hi"};
actual = uneval(obj);
expect = '({toString:9, hasOwnProperty:"Hi"})';
addThis();

status = this.inSection(4);
obj = {prop1:1, toString:9, hasOwnProperty:"Hi"};
actual = uneval(obj);
expect = '({prop1:1, toString:9, hasOwnProperty:"Hi"})';
addThis();


// TRY THE SAME THING IN EVAL CODE
var s = '';

status = this.inSection(5);
s = 'obj = {toString:9}';
eval(s);
actual = uneval(obj);
expect = '({toString:9})';
addThis();

status = this.inSection(6);
s = 'obj = {hasOwnProperty:"Hi"}';
eval(s);
actual = uneval(obj);
expect = '({hasOwnProperty:"Hi"})';
addThis();

status = this.inSection(7);
s = 'obj = {toString:9, hasOwnProperty:"Hi"}';
eval(s);
actual = uneval(obj);
expect = '({toString:9, hasOwnProperty:"Hi"})';
addThis();

status = this.inSection(8);
s = 'obj = {prop1:1, toString:9, hasOwnProperty:"Hi"}';
eval(s);
actual = uneval(obj);
expect = '({prop1:1, toString:9, hasOwnProperty:"Hi"})';
addThis();


// TRY THE SAME THING IN FUNCTION CODE
function A(obj)
{
status = obj.inSection(9);
var s = 'obj = {toString:9}';
eval(s);
actual = uneval(obj);
expect = '({toString:9})';
addThis();
}
A(this);

function B(obj)
{
status = obj.inSection(10);
var s = 'obj = {hasOwnProperty:"Hi"}';
eval(s);
actual = uneval(obj);
expect = '({hasOwnProperty:"Hi"})';
addThis();
}
B(this);

function C(obj)
{
status = obj.inSection(11);
var s = 'obj = {toString:9, hasOwnProperty:"Hi"}';
eval(s);
actual = uneval(obj);
expect = '({toString:9, hasOwnProperty:"Hi"})';
addThis();
}
C(this);

function D(obj)
{
status = obj.inSection(12);
var s = 'obj = {prop1:1, toString:9, hasOwnProperty:"Hi"}';
eval(s);
actual = uneval(obj);
expect = '({prop1:1, toString:9, hasOwnProperty:"Hi"})';
addThis();
}
D(this);



//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------



/*
* Sort properties alphabetically -
*/
function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = sortThis(actual);
expectedvalues[UBound] = sortThis(expect);
UBound++;
}


/*
* Takes string of form '({"c", "b", "a", 2})' and returns '({"a","b","c",2})'
*/
function sortThis(sList)
{
sList = compactThis(sList);
sList = stripParens(sList);
sList = stripBraces(sList);
var arr = sList.split(cnCOMMA);
arr = arr.sort();
var ret = String(arr);
ret = addBraces(ret);
ret = addParens(ret);
return ret;
}


/*
* Strips out any whitespace from the text -
*/
function compactThis(text)
{
var charCode = 0;
var ret = '';

for (var i=0; i<text.length; i++)
{
charCode = text.charCodeAt(i);

if (!isWhiteSpace(charCode))
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


/*
* strips off parens at beginning and end of text -
*/
function stripParens(text)
{
// remember to escape the parens...
var arr = text.match(/^\((.*)\)$/);

// defend against a null match...
if (arr != null && arr[1] != null)
return arr[1];
return text;
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


function addBraces(text)
{
return cnLBRACE + text + cnRBRACE;
}


function addParens(text)
{
return cnLPAREN + text + cnRPAREN;
}


//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

for (var i=0; i<UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__96284__001.js
Date: 03 September 2001

SUMMARY: Double quotes should be escaped in Error.prototype.toSource()
See http://bugzilla.mozilla.org/show_bug.cgi?id=96284

The real point here is this: we should be able to reconstruct an object
from its toSource() property. We'll test this on various types of objects.

Method: define obj2 = eval(obj1.toSource()) and verify that
obj2.toSource() == obj1.toSource().
*/
test_regress__96284__001: function () {
var UBound = 0;
//var BUGNUMBER = 96284;
var summary = 'Double quotes should be escaped in Error.prototype.toSource()';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];
var obj1 = {};
var obj2 = {};
var cnTestString = '"This is a \" STUPID \" test string!!!"\\';


// various NativeError objects -
status = this.inSection(1);
obj1 = Error(cnTestString);
obj2 = eval(obj1.toSource());
actual = obj2.toSource();
expect = obj1.toSource();
addThis();

status = this.inSection(2);
obj1 = EvalError(cnTestString);
obj2 = eval(obj1.toSource());
actual = obj2.toSource();
expect = obj1.toSource();
addThis();

status = this.inSection(3);
obj1 = RangeError(cnTestString);
obj2 = eval(obj1.toSource());
actual = obj2.toSource();
expect = obj1.toSource();
addThis();

status = this.inSection(4);
obj1 = ReferenceError(cnTestString);
obj2 = eval(obj1.toSource());
actual = obj2.toSource();
expect = obj1.toSource();
addThis();

status = this.inSection(5);
obj1 = SyntaxError(cnTestString);
obj2 = eval(obj1.toSource());
actual = obj2.toSource();
expect = obj1.toSource();
addThis();

status = this.inSection(6);
obj1 = TypeError(cnTestString);
obj2 = eval(obj1.toSource());
actual = obj2.toSource();
expect = obj1.toSource();
addThis();

status = this.inSection(7);
obj1 = URIError(cnTestString);
obj2 = eval(obj1.toSource());
actual = obj2.toSource();
expect = obj1.toSource();
addThis();


// other types of objects -
status = this.inSection(8);
obj1 = new String(cnTestString);
obj2 = eval(obj1.toSource());
actual = obj2.toSource();
expect = obj1.toSource();
addThis();

status = this.inSection(9);
obj1 = {color:'red', texture:cnTestString, hasOwnProperty:42};
obj2 = eval(obj1.toSource());
actual = obj2.toSource();
expect = obj1.toSource();
addThis();

status = this.inSection(10);
obj1 = function(x) {function g(y){return y+1;} return g(x);};
obj2 = eval(obj1.toSource());
actual = obj2.toSource();
expect = obj1.toSource();
addThis();

status = this.inSection(11);
obj1 = new Number(eval('6'));
obj2 = eval(obj1.toSource());
actual = obj2.toSource();
expect = obj1.toSource();
addThis();

status = this.inSection(12);
obj1 = /ad;(lf)kj(2309\/\/)\/\//;
obj2 = eval(obj1.toSource());
actual = obj2.toSource();
expect = obj1.toSource();
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
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

for (var i = 0; i < UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//exitFunc ('test');
//}

},

/** @Test
File Name:         regress__96284__002.js
Date: 03 September 2001

SUMMARY: Double quotes should be escaped in uneval(new Error('""'))
See http://bugzilla.mozilla.org/show_bug.cgi?id=96284

The real point here is this: we should be able to reconstruct an object
obj from uneval(obj). We'll test this on various types of objects.

Method: define obj2 = eval(uneval(obj1)) and verify that
obj2.toSource() == obj1.toSource().
*/
test_regress__96284__002: function () {
var UBound = 0;
//var BUGNUMBER = 96284;
var summary = 'Double quotes should be escaped in Error.prototype.toSource()';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];
var obj1 = {};
var obj2 = {};
var cnTestString = '"This is a \" STUPID \" test string!!!"\\';


// various NativeError objects -
status = this.inSection(1);
obj1 = Error(cnTestString);
obj2 = eval(uneval(obj1));
actual = obj2.toSource();
expect = obj1.toSource();
addThis();

status = this.inSection(2);
obj1 = EvalError(cnTestString);
obj2 = eval(uneval(obj1));
actual = obj2.toSource();
expect = obj1.toSource();
addThis();

status = this.inSection(3);
obj1 = RangeError(cnTestString);
obj2 = eval(uneval(obj1));
actual = obj2.toSource();
expect = obj1.toSource();
addThis();

status = this.inSection(4);
obj1 = ReferenceError(cnTestString);
obj2 = eval(uneval(obj1));
actual = obj2.toSource();
expect = obj1.toSource();
addThis();

status = this.inSection(5);
obj1 = SyntaxError(cnTestString);
obj2 = eval(uneval(obj1));
actual = obj2.toSource();
expect = obj1.toSource();
addThis();

status = this.inSection(6);
obj1 = TypeError(cnTestString);
obj2 = eval(uneval(obj1));
actual = obj2.toSource();
expect = obj1.toSource();
addThis();

status = this.inSection(7);
obj1 = URIError(cnTestString);
obj2 = eval(uneval(obj1));
actual = obj2.toSource();
expect = obj1.toSource();
addThis();


// other types of objects -
status = this.inSection(8);
obj1 = new String(cnTestString);
obj2 = eval(uneval(obj1));
actual = obj2.toSource();
expect = obj1.toSource();
addThis();

status = this.inSection(9);
obj1 = {color:'red', texture:cnTestString, hasOwnProperty:42};
obj2 = eval(uneval(obj1));
actual = obj2.toSource();
expect = obj1.toSource();
addThis();

status = this.inSection(10);
obj1 = function(x) {function g(y){return y+1;} return g(x);};
obj2 = eval(uneval(obj1));
actual = obj2.toSource();
expect = obj1.toSource();
addThis();

status = this.inSection(11);
obj1 = new Number(eval('6'));
obj2 = eval(uneval(obj1));
actual = obj2.toSource();
expect = obj1.toSource();
addThis();

status = this.inSection(12);
obj1 = /ad;(lf)kj(2309\/\/)\/\//;
obj2 = eval(uneval(obj1));
actual = obj2.toSource();
expect = obj1.toSource();
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
///enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

for (var i = 0; i < UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//exitFunc ('test');
//}

},

/** @Test
File Name:         scope__001.js

*/
test_scope__001: function () {
//var BUGNUMBER = '53268';
var status = 'Testing scope after changing obj.__proto__';
var expect= '';
var actual = '';
var obj = {};
const five = 5;


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------


//function test()
//{
//enterFunc ("test");
//printBugNumber(BUGNUMBER);
//printStatus (status);


status= 'Step 1:  setting obj.__proto__ = global object';
obj.__proto__ = this;

actual = obj.five;
expect=5;
this.reportCompare (expect, actual, status);

obj.five=1;
actual = obj.five;
expect=5;
this.reportCompare (expect, actual, status);



status= 'Step 2:  setting obj.__proto__ = null';
obj.__proto__ = null;

actual = obj.five;
expect=undefined;
this.reportCompare (expect, actual, status);

obj.five=2;
actual = obj.five;
expect=2;
this.reportCompare (expect, actual, status);



status= 'Step 3:  setting obj.__proto__  to global object again';
obj.__proto__ = this;

actual = obj.five;
expect=2;  //<--- (FROM STEP 2 ABOVE)
this.reportCompare (expect, actual, status);

obj.five=3;
actual = obj.five;
expect=3;
this.reportCompare (expect, actual, status);



status= 'Step 4:  setting obj.__proto__   to  null again';
obj.__proto__ = null;

actual = obj.five;
expect=3;  //<--- (FROM STEP 3 ABOVE)
this.reportCompare (expect, actual, status);

obj.five=4;
actual = obj.five;
expect=4;
this.reportCompare (expect, actual, status);


//exitFunc ("test");
//}

},

/** @Test
File Name:         toLocaleFormat__01.js

*/
test_toLocaleFormat__01: function () {
//var BUGNUMBER = 291494;
var summary = 'Date.prototype.toLocaleFormat extension';
var actual = '';
var expect = '';
var temp;

/*
* SpiderMonkey only.
*
* When the output of toLocaleFormat exceeds 100 bytes toLocaleFormat
* defaults to using toString to produce the result.
*/

//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

var date = new Date("06/05/2005 00:00:00 GMT-0000");

date.toLocaleFormat = function(fmt) {
var year = this.getFullYear().toString();
var mon  = (this.getMonth() + 1).toString()
var day  = (this.getDate()).toString();

if (mon.length < 2)
{
mon = '0' + mon;
}
if (day.length < 2)
{
day = '0' + day;
}
return year + mon + day;
}

expect = date.getTimezoneOffset() > 0 ? 'Sat' : 'Sun';
actual = date.toLocaleFormat('%a');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%a")');

expect = date.getTimezoneOffset() > 0 ? 'Saturday' : 'Sunday';
actual = date.toLocaleFormat('%A');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%A")');

expect = 'Jun';
actual = date.toLocaleFormat('%b');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%b")');

expect = 'June';
actual = date.toLocaleFormat('%B');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%B")');

expect = (date.getTimezoneOffset() > 0) ? '04' : '05';
actual = date.toLocaleFormat('%d');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%d")');

expect = '0';
actual = String((Number(date.toLocaleFormat('%H')) +
date.getTimezoneOffset()/60) % 24);
this.reportCompare(expect, actual, 'Date.toLocaleFormat(%H)');

expect = '12';
actual = String(Number(date.toLocaleFormat('%I')) +
date.getTimezoneOffset()/60);
this.reportCompare(expect, actual, 'Date.toLocaleFormat(%I)');

expect = String(155 + ((date.getTimezoneOffset() > 0) ? 0 : 1));
actual = date.toLocaleFormat('%j');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%j")');

expect = '06';
actual = date.toLocaleFormat('%m');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%m")');

expect = '00';
actual = date.toLocaleFormat('%M');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%M")');

expect = true;
temp   = date.toLocaleFormat('%p');
actual = temp == 'AM' || date.toLocaleFormat('%p') == 'PM';
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%p") is AM or PM');

expect = '00';
actual = date.toLocaleFormat('%S');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%S")');

expect = String(22 + ((date.getTimezoneOffset() > 0) ? 0 : 1));
actual = date.toLocaleFormat('%U');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%U")');

expect = String((6 + ((date.getTimezoneOffset() > 0) ? 0 : 1))%7);
actual = date.toLocaleFormat('%w');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%w")');

expect = '22';
actual = date.toLocaleFormat('%W');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%W")');

expect = date.toLocaleTimeString();
actual = date.toLocaleFormat('%X');
this.reportCompare(expect, actual, 'Date.toLocaleTimeString() == ' +
'Date.toLocaleFormat("%X")');

expect = '05';
actual = date.toLocaleFormat('%y');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%y")');

expect = '2005';
actual = date.toLocaleFormat('%Y');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%Y")');

expect = '%';
actual = date.toLocaleFormat('%%');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%%")');


expect = '1899 99';
temp='%Y %y';
actual = new Date(0, 0, 0, 13, 14, 15, 0).toLocaleFormat(temp);
this.reportCompare(expect, actual, 'Date.toLocaleFormat("'+temp+'")');

expect = '1899189918991899189918991899189918991899189918991899189918991899189918991899189918991899';
temp = '%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y';
actual = new Date(0, 0, 0, 13, 14, 15, 0).toLocaleFormat(temp);
this.reportCompare(expect, actual, 'Date.toLocaleFormat("'+temp+'")');

expect = 'xxx189918991899189918991899189918991899189918991899189918991899189918991899189918991899189918991899';
temp = 'xxx%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y';
actual = new Date(0, 0, 0, 13, 14, 15, 0).toLocaleFormat(temp);
this.reportCompare(expect, actual, 'Date.toLocaleFormat("'+temp+'")');

expect = new Date(0, 0, 0, 13, 14, 15, 0).toString();
temp = 'xxxx%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y';
actual = new Date(0, 0, 0, 13, 14, 15, 0).toLocaleFormat(temp);
this.reportCompare(expect, actual, 'Date.toLocaleFormat("'+temp+'")');

expect = 'xxxx189918991899189918991899189918991899';
temp = 'xxxx%Y%Y%Y%Y%Y%Y%Y%Y%Y';
actual = new Date(0, 0, 0, 13, 14, 15, 0).toLocaleFormat(temp);
this.reportCompare(expect, actual, 'Date.toLocaleFormat("'+temp+'")');


expect = '-51 49';
temp = '%Y %y';
actual = new Date(-51, 0).toLocaleFormat(temp);
this.reportCompare(expect, actual, 'Date.toLocaleFormat("'+temp+'")');

expect = '-51-51-51-51-51-51-51-51-51-51-51-51-51-51-51-51-51-51-51-51-51-51';
temp = '%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y';
actual = new Date(-51, 0).toLocaleFormat(temp);
this.reportCompare(expect, actual, 'Date.toLocaleFormat("'+temp+'")');

expect = 'xxx-51-51-51-51-51-51-51-51-51-51-51-51-51-51-51-51-51-51-51-51-51-51-51-51';
temp = 'xxx%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y';
actual = new Date(-51, 0).toLocaleFormat(temp);
this.reportCompare(expect, actual, 'Date.toLocaleFormat("'+temp+'")');

expect = new Date(-51, 0).toString();
temp = 'xxxx%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y';
actual = new Date(-51, 0).toLocaleFormat(temp);
this.reportCompare(expect, actual, 'Date.toLocaleFormat("'+temp+'")');


expect = '1851 51';
temp = '%Y %y';
actual = new Date(1851, 0).toLocaleFormat(temp);
this.reportCompare(expect, actual, 'Date.toLocaleFormat("'+temp+'")');

expect = '1851185118511851185118511851185118511851185118511851185118511851185118511851185118511851';
temp = '%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y';
actual = new Date(1851, 0).toLocaleFormat(temp);
this.reportCompare(expect, actual, 'Date.toLocaleFormat("'+temp+'")');

expect = 'xxx185118511851185118511851185118511851185118511851185118511851185118511851185118511851185118511851';
temp = 'xxx%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y';
actual = new Date(1851, 0).toLocaleFormat(temp);
this.reportCompare(expect, actual, 'Date.toLocaleFormat("'+temp+'")');

expect = new Date(1851, 0).toString();
temp = 'xxxx%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y';
actual = new Date(1851, 0).toLocaleFormat(temp);
this.reportCompare(expect, actual, 'Date.toLocaleFormat("'+temp+'")');


expect = '-1 99';
temp = '%Y %y';
actual = new Date(-1, 0).toLocaleFormat(temp);
this.reportCompare(expect, actual, 'Date.toLocaleFormat("'+temp+'")');

expect = '-100 00';
temp = '%Y %y';
actual = new Date(-100, 0).toLocaleFormat(temp);
this.reportCompare(expect, actual, 'Date.toLocaleFormat("'+temp+'")');

expect = '1900 00';
temp = '%Y %y';
actual = new Date(0, 0).toLocaleFormat(temp);
this.reportCompare(expect, actual, 'Date.toLocaleFormat("'+temp+'")');

expect = '1901 01';
temp = '%Y %y';
actual = new Date(1, 0).toLocaleFormat(temp);
this.reportCompare(expect, actual, 'Date.toLocaleFormat("'+temp+'")');

expect = '1970 70';
temp = '%Y %y';
actual = new Date(1970, 0).toLocaleFormat(temp);
this.reportCompare(expect, actual, 'Date.toLocaleFormat("'+temp+'")');


expect = new Date(32767, 0).toString();
temp = 'xxxx%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y';
actual = new Date(32767, 0).toLocaleFormat(temp);
this.reportCompare(expect, actual, 'Date.toLocaleFormat("'+temp+'")');

expect = '32767327673276732767327673276732767327673276732767327673276732767327673276732767327673276732767';
temp = '%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y';
actual = new Date(32767, 0).toLocaleFormat(temp);
this.reportCompare(expect, actual, 'Date.toLocaleFormat("'+temp+'")');

expect = 'xxxx32767327673276732767327673276732767327673276732767327673276732767327673276732767327673276732767';
temp = 'xxxx%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y';
actual = new Date(32767, 0).toLocaleFormat(temp);
this.reportCompare(expect, actual, 'Date.toLocaleFormat("'+temp+'")');

expect = new Date(32767, 0).toString();
temp = 'xxxxx%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y';
actual = new Date(32767, 0).toLocaleFormat(temp);
this.reportCompare(expect, actual, 'Date.toLocaleFormat("'+temp+'")');


expect = '-9999-9999-9999-9999-9999-9999-9999-9999-9999-9999-9999-9999-9999-9999-9999-9999-9999-9999-9999';
temp = '%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y';
actual = new Date(-9999, 0).toLocaleFormat(temp);
this.reportCompare(expect, actual, 'Date.toLocaleFormat("'+temp+'")');

expect = 'xxxx-9999-9999-9999-9999-9999-9999-9999-9999-9999-9999-9999-9999-9999-9999-9999-9999-9999-9999-9999';
temp = 'xxxx%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y';
actual = new Date(-9999, 0).toLocaleFormat(temp);
this.reportCompare(expect, actual, 'Date.toLocaleFormat("'+temp+'")');

expect = new Date(-9999, 0).toString();
temp = 'xxxx%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y%Y';
actual = new Date(-9999, 0).toLocaleFormat(temp);
this.reportCompare(expect, actual, 'Date.toLocaleFormat("'+temp+'")');

},

/** @Test
File Name:         toLocaleFormat__02.js

*/
test_toLocaleFormat__02: function () {
//var BUGNUMBER = 291494;
var summary = 'Date.prototype.toLocaleFormat extension';
var actual = '';
var expect = '';
var temp;

/*
* SpiderMonkey only.
*
* This test uses format strings which are not supported cross
* platform and are expected to fail on at least some platforms
* however they all currently pass on Linux (Fedora Core 6). They are
* included here in order to increase coverage for cases where a crash
* may occur.  These failures will be tracked in the
* mozilla/js/tests/public-failures.txt list.
*
*/

//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

var date = new Date("06/05/2005 00:00:00 GMT-0000");

date.toLocaleFormat = function(fmt) {
var year = this.getFullYear().toString();
var mon  = (this.getMonth() + 1).toString()
var day  = (this.getDate()).toString();

if (mon.length < 2)
{
mon = '0' + mon;
}
if (day.length < 2)
{
day = '0' + day;
}
return year + mon + day;
}

expect = '20';
actual = date.toLocaleFormat('%C');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%C")');

expect = date.toLocaleFormat('%C%y');
actual = date.toLocaleFormat('%Y');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%C%y") == ' +
'Date.toLocaleFormat("%Y")');

expect = date.toLocaleFormat('%m/%d/%y');
actual = date.toLocaleFormat('%D');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%m/%d/%y") == ' +
'Date.toLocaleFormat("%D")');

expect = (date.getTimezoneOffset() > 0) ? ' 4' : ' 5';
actual = date.toLocaleFormat('%e');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%e")');

expect = date.toLocaleFormat('%Y-%m-%d');
actual = date.toLocaleFormat('%F');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%Y-%m-%d") == ' +
'Date.toLocaleFormat("%F")');

expect = '05';
actual = date.toLocaleFormat('%g');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%g")');

expect = '2005';
actual = date.toLocaleFormat('%G');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%G")');

expect = date.toLocaleFormat('%b');
actual = date.toLocaleFormat('%h');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%b") == ' +
'Date.toLocaleFormat("%h")');

expect = '\n';
actual = date.toLocaleFormat('%n');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%n") == "\\n"');

expect = date.toLocaleFormat('%I:%M:%S %p');
actual = date.toLocaleFormat('%r');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%I:%M:%S %p") == ' +
'Date.toLocaleFormat("%r")');

expect = date.toLocaleFormat('%H:%M');
actual = date.toLocaleFormat('%R');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%H:%M") == ' +
'Date.toLocaleFormat("%R")');

expect = '\t';
actual = date.toLocaleFormat('%t');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%t") == "\\t"');

expect = date.toLocaleFormat('%H:%M:%S');
actual = date.toLocaleFormat('%T');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%H:%M:%S") == ' +
'Date.toLocaleFormat("%T")');

expect = String(6 + ((date.getTimezoneOffset() > 0) ? 0 : 1));
actual = date.toLocaleFormat('%u');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%u")');

expect = '22';
actual = date.toLocaleFormat('%V');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%V")');

//print('Note: For Date.toLocaleFormat("%m/%d/%y") == Date.toLocaleFormat("%x") ' +
//      'to pass in Windows, the Regional Setting for the short date must be ' +
//      'set to mm/dd/yyyy');
expect = date.toLocaleFormat('%m/%d/%Y');
actual = date.toLocaleFormat('%x');
this.reportCompare(expect, actual, 'Date.toLocaleFormat("%m/%d/%Y") == ' +
'Date.toLocaleFormat("%x")');


}

})
.endType();
