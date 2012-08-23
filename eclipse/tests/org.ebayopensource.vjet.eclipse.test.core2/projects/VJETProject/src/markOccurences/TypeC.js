vjo.ctype("markOccurences.TypeC")
.props({
	stC : 0, //< int
	
	stFunc : function() {
		this.stC = 1;
	}
})
.protos({
	foo : function() {
		this.vj$.TypeC.stC = 1;
	}
})
.endType();