vjo.ctype("markOccurences.TypeB")
.needs("markOccurences.TypeA")
.inherits("markOccurences.TypeA")
.props({
	stB : function() {
		this.vj$.TypeA.stFunc();
	}
})
.protos({
	funcB : function() {
		this.vj$.TypeA.stA = 0;
		this.vj$.TypeA.stFunc();
	}
})
.endType();