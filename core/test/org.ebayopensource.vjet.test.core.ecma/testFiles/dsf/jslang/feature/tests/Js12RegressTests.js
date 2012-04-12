vjo.ctype("dsf.jslang.feature.tests.Js12RegressTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({
//-----------------------------------------------------------------------------
//>public void reportCompare(Object o1, Object o2, Object o3)
reportCompare : function(o1,o2,o3){
},
test_regress__144834:function(){

var types = [];
function inspect(object) {
var prop;
for ( prop in object) {
var x = object[prop];
types[types.length] = (typeof x);
}
}

var o = {a: 1, b: 2};
inspect(o);

this.reportCompare( "inspect(o),length",   2,       types.length );
this.reportCompare( "inspect(o)[0]",      "number", types[0] );
this.reportCompare( "inspect(o)[1]",      "number", types[1] );

var types_2 = [];

function inspect_again(object) {
var prop;
for (prop in object) {
types_2[types_2.length] = (typeof object[prop]);
}
}

inspect_again(o);
this.reportCompare( "inspect_again(o),length",   2,       types.length );
this.reportCompare( "inspect_again(o)[0]",      "number", types[0] );
this.reportCompare( "inspect_again(o)[1]",      "number", types[1] );
},



test_regress__7703:function(){

var gTestfile = 'regress-144834.js';
var BUGNUMBER = 144834;
var summary = 'Local var having same name as switch label inside function';

// print(BUGNUMBER);
// print(summary);


function RedrawSched()
{
var MinBound;
var i;
switch (i)
{
case MinBound :;
}
}


/*
* Also try eval scope -
*/
var s = '';
s += 'function RedrawSched()';
s += '{';
s += '  var MinBound;';
s += '';
s += '  switch (i)';
s += '  {';
s += '    case MinBound :';
s += '  }';
s += '}';
eval(s);

this.reportCompare('Do not crash', 'No Crash', 'No Crash');



}}).endType()


function reportCompare (statusItems, expectedValue, actualValue ) {
new com.ebay.dsf.jslang.feature.tests.BaseTest().TestCase( ' ',  statusItems, expectedValue,  actualValue);
}

