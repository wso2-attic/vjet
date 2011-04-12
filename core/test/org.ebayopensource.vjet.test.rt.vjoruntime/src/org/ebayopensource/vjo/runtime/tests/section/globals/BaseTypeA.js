vjo.ctype('org.ebayopensource.vjo.runtime.tests.section.globals.BaseTypeA') //< public
.globals({
	G1: true,  //< boolean
	G2: 0,     //< double
	G3: function(arg1) {  //< double G3(double)
		return arg1 / 3.0 ;
	}
})
.props({
  
})
.protos({

})
.inits(function() {
	// Safe way to access G1 and G3
	var x = (G1 == true) ? 100 : 300 ;
	G2 = G3(x) ;
})
.endType();
