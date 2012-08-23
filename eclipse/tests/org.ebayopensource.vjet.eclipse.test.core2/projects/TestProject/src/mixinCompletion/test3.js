vjo.ctype("mixinCompletion.test3")
.inherits("mixinCompletion.test2")
.props({
	stT : function() {
	}
})
.protos({
	t : 5; //< private int
	doT : function() {
		this.fooM();
		vjo.$static(this).stM();
	}
});