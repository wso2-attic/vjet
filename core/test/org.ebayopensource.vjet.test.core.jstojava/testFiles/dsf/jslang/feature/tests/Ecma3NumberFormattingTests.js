vjo.ctype("dsf.jslang.feature.tests.Ecma3NumberFormattingTests")
.inherits("dsf.jslang.feature.tests.BaseTestEcma3")
.protos({

constructs: function() {
this.base();
},

test_tostring__001: function() {

//test();

//function test()
//{
var n0 = 1e23;
var n1 = 5e22;
var n2 = 1.6e24;

//printStatus ("Number formatting test.");
//printBugNumber ("11178");

this.reportCompare ("1e+23", n0.toString(), "1e23 toString()");
this.reportCompare ("5e+22", n1.toString(), "5e22 toString()");
this.reportCompare ("1.6e+24", n2.toString(), "1.6e24 toString()");

//}

}

})
.endType();




