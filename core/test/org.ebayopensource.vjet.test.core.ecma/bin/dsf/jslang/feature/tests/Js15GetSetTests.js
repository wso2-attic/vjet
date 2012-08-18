vjo.ctype("dsf.jslang.feature.tests.Js15GetSetTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({
//test_getset__002:function(){
//
//
//var t = {
//  _y: "<initial y>",
//
//  y getter: function get_y ()
//  {
//    var rv;
//    if (typeof this._y == "string")
//      rv = "got " + this._y;
//    else
//      rv = this._y;
//
//    return rv;
//  },
//
//  y setter: function set_y (newVal)
//  {
//    this._y = newVal;
//  }
//}
//
//
//  test(t);
//
//function test(t)
//{
////   enterFunc ("test");
//
////   printStatus ("Basic Getter/ Setter test (object literal notation)");
//
//  reportCompare ("<initial y>", t._y, "y prototype check");
//
//  reportCompare ("got <initial y>", t.y, "y getter, before set");
//
//  t.y = "new y";
//  reportCompare ("got new y", t.y, "y getter, after set");
//
//  t.y = 2;
//  reportCompare (2, t.y, "y getter, after numeric set");
//
//  var d = new Date();
//  t.y = d;
//  reportCompare (d, t.y, "y getter, after date set");
//
//}
//
//},
//test_regress__353264:function(){
//
////-----------------------------------------------------------------------------
//var BUGNUMBER = 353264;
//var summary = 'Do not crash defining getter';
//var actual = 'No Crash';
//var expect = 'No Crash';
//
//// printBugNumber(BUGNUMBER);
//// printStatus (summary);
//
//this.x getter= function () { }; export x; x;
//
//reportCompare(expect, actual, summary);
//
//},
//test_regress__375976:function(){
//
//
////-----------------------------------------------------------------------------
//var BUGNUMBER = 375976;
//var summary = 'Do not crash with postincrement custom property';
//var actual = '';
//var expect = '';
//
//
////-----------------------------------------------------------------------------
//test();
////-----------------------------------------------------------------------------
//
//function test()
//{
////   enterFunc ('test');
////   printBugNumber(BUGNUMBER);
////   printStatus (summary);
//
//  this.__defineSetter__('x', gc);
//  this.__defineGetter__('x', Math.sin);
//  x = x++;
//
//  reportCompare(expect, actual, summary);
//
////   exitFunc ('test');
//}
//
//}
}).endType()


function reportCompare (expectedValue, actualValue, statusItems) {
new dsf.jslang.feature.tests.BaseTest().TestCase( ' ',  statusItems, expectedValue,  actualValue);
}
