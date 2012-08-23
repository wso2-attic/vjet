vjo.ctype("markOccurences.TypeA")
.props({
	stA : 0, //<int
	
	stFunc : function() {
	}
})
.protos({
	func : function() {
		this.vj$.TypeA.stA = 1;
		this.vj$.TypeA.stFunc();
	}
})
.endType();