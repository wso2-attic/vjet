vjo.ctype("thisCompletion.TypeA")
.needs("thisCompletion.D")
.inherits("thisCompletion.D")
.props({
	doStatic : function() {
		this.vj$.D.doStatic();
	}
})
.protos({
	doIt : function() {
		this.vj$.TypeA.doStatic();
	}
})
.endType();