vjo.ctype("dsf.format.ForTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({

test: function(){

var foo = 0;//<int
for ( foo = 0; foo < 10; foo++ ) {
}

for ( var bar = 0; bar < 10; bar++ ) {
}
}

}).endType()
