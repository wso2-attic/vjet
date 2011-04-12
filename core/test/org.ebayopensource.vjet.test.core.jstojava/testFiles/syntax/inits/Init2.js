vjo.ctype('syntax.inits.Init2')
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
	this.finalvar = "SF";
	this.finalvar = "dfs";
})
.endType();