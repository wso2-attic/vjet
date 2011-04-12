vjo.ctype("fld2.TypeB")
.needs("fld1.TypeA")
.protos({
	fooB : function() {
		//> fld1.TypeA
		var inst = new this.vj$.TypeA();
		inst.fooA();
	}
})
.endType();