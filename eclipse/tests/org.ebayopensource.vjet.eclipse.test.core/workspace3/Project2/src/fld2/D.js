vjo.ctype("fld2.D")
.needs("fld1.B")
.protos({
	d: function() {
		var inst = new this.vj$.B(); //< fld1.B
		inst.b();
	}
})
.endType();