vjo.ctype("fld2.C")
.needs("fld1.A")
.protos({
	c : function() {
		var inst = new this.vj$.A(); //< fld1.A
		inst.a();
	}
})
.endType();