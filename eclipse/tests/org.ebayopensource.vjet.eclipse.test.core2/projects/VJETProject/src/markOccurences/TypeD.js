vjo.ctype("markOccurences.TypeD")
.props({
	stFunc : function() {
	} 
})
.protos({
	//> public void d(int i)
	d : function (i) {
		vjo.sysout.println(i);
	},
	
	foo : function() {
		this.d();
		this.vj$.TypeD.stFunc();
	}
})
.endType();