vjo.ctype("mixinCompletion.test2")
.mixin("mixinCompletion.test")
.props({
	stC : function() {
	}
})
.protos({
	doC : function() {
		this.fooM();
		this.vj$.test2.stC();
	}
});