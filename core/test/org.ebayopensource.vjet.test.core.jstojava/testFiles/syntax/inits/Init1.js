vjo.ctype('syntax.inits.Init1')
.props({
	s1 : undefined, //<String
	finalvar : undefined//< final String
})
.protos({
	s2 : undefined, //<String
	//>public constructs()
	constructs : function(){
	}
})
.inits(function(){
	this.s1 = "DDD";
	this.s2 = "VSFF";
	this.finalvar = "SF";
})
.endType();