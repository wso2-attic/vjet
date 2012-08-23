vjo.ctype("selection.C")
.props({
	stC : 1, //< int
	
	stFunc : function() {
		this.stC = 0;
		this.stFunc();
	}
})
.endType();