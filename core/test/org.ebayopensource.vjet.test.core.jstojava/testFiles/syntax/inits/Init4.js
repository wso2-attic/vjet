vjo.ctype('syntax.inits.Init4')
.props({
	s1 : undefined, //<String
	finalVar2 : "Final2" //< final String
})
.protos({
	s2 : undefined, //<final String
	//>public constructs()
	constructs : function(){
	this.vj$.Init4.finalVar2 = "3";
		this.s2 = "v";
	}
})
.inits(function(){
	this.vj$.Init4.finalVar2 = "3";
		this.s2 = "V";
})
.endType();