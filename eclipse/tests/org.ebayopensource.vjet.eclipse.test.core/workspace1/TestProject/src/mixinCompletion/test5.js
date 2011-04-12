vjo.ctype("mixinCompletion.test5")
.inherits("mixinCompletion.test4")
.props({
	stT : function() {
	}
})
.protos({
	t : 5; //< private int
	doT : function() {
		this.t = 4;
		this.vj$.test5.stT();
	}
});