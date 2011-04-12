vjo.ctype("dsf.format.UnicodeTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({

test: function(){

this.TestCase(  "",
'var s = "PAS\\u0022SED"; s',
"PAS\"SED",
eval('var s = "PAS\\u0022SED"; s') );

}


}).endType();
