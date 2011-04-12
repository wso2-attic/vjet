vjo.ctype("mixinCompletion.test4")
.mixinProps("mixinCompletion.test")
.props({
	stC : function() {
	}
})
.protos({
	c : 4; //<private int
	
	doC : function() {
		this.c = 1;
		this.vj$.test4.stC();
	}
});