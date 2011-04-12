vjo.ctype("dsf.jslang.feature.tests.Ecma3RegressTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({

constructs:function(){
this.base();
},

/** @Test
File Name:         regress__385393__04.js
Summary:       Regression test for bug 385393
*/
test_regress__385393__04: function () {
var SECTION = "regress__385393__04.js";
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
'a'.replace(/a/g, eval);
}
catch(ex)
{
}

this.TestCase(SECTION, summary, expect, actual);

//exitFunc ('test');
//}
},

/** @Test
File Name:         regress__419152.js
Summary:       Shaver can not contain himself
*/
test_regress__419152: function () {
var SECTION = "regress__419152.js";
//var BUGNUMBER = 419152;
var summary = 'Shaver can not contain himself';
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

var a = [1,2,3];

a[5] = 6;
expect = '1,2,3,,,6:6';
actual = a + ':' + a.length;
this.TestCase(SECTION, summary + ': 1', expect, actual);

a = [1,2,3,4];
expect = 'undefined';
actual = a[-1] + '';
this.TestCase(SECTION, summary + ': 2', expect, actual);

a = [1,2,3];
a[-1] = 55;

expect = 3;
actual = a.length;
this.TestCase(SECTION, summary + ': 3', expect, actual);

expect = '1,2,3';
actual = a + '';
this.TestCase(SECTION, summary + ': 4', expect, actual);

expect = 55;
actual = a[-1];
this.TestCase(SECTION, summary + ': 5', expect, actual);

var s = "abcdef";

expect = 'undefined';
actual = s[-2] + '';
this.TestCase(SECTION, summary + ': 6', expect, actual);

//exitFunc ('test');
//}
},

/** @Test
File Name:         regress__420087.js
Summary:       Do not assert:  PCVCAP_MAKE(sprop->shape, 0, 0) == entry->vcap
*/
test_regress__420087: function () {
var SECTION = "regress__420087.js";
//var BUGNUMBER = 420087;
var summary = 'Do not assert:  PCVCAP_MAKE(sprop->shape, 0, 0) == entry->vcap';
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

var dict;

for (var i = 0; i < 2; i++)
dict = {p: 1, q: 1, p1:1};

this.TestCase(SECTION, summary, expect, actual);

// exitFunc ('test');
//}
},

/** @Test
File Name:         regress_420610.js
Summary:       Do not crash with eval("this.x")
*/
test_regress_420610: function () {
var SECTION = "regress_420610.js";
//var BUGNUMBER = 420610;
var summary = 'Do not crash with eval("this.x")';
var actual = 'No Crash';
var expect = 'No Crash';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

(function(){ eval("this.x") })();

this.TestCase(SECTION, summary, expect, actual);
},

/** @Test
File Name:         regress__441477__01.js
*/
test_regress__441477__01: function () {
var SECTION = "regress__441477__01.js";
//var BUGNUMBER = 441477-01;
var summary = '';
var actual = 'No Exception';
var expect = 'No Exception';


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
for (i = 0; i < 5;)
{
if (i > 5)
throw "bad";
i++;
continue;
}
}
catch(ex)
{
actual = ex + '';
}
this.TestCase(SECTION, summary, expect, actual);

//exitFunc ('test');
//}
}

})
.endType();
