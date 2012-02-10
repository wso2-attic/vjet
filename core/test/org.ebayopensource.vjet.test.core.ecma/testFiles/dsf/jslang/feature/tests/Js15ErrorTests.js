vjo.ctype("dsf.jslang.feature.tests.Js15ErrorTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({

constructs:function(){
this.base();
},

/** @Test
File Name:         regress__354246.js
Summary:         calling Error constructor with object with bad toString
*/
test_regress__354246: function () {
var SECTION = "regress__354246.js";
//var BUGNUMBER = 354246;
var summary = 'calling Error constructor with object with bad toString';
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

expect = '13';

actual += '1';
try
{
new Error({toString: function() {  } });
}
catch(e)
{
}
actual += '3';
this.TestCase(SECTION, summary, expect, actual);

//exitFunc ('test');
//}
},

/** @Test
File Name:         regress__412324.js
Summary:         Allow function Error(){} for the love of Pete
*/
test_regress__412324: function () {
var SECTION = "regress__412324.js";
//var BUGNUMBER = 412324;
var summary = 'Allow function Error(){} for the love of Pete';
var actual = 'No Error';
var expect = 'No Error';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

function Error() {}

this.TestCase(SECTION, summary, expect, actual);

}

})
.endType();
