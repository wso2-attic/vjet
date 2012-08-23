vjo.needs("search.TypeA")
vjo.ctype("search.TypeB")
.inherits("search.TypeA")
.props({
	maxNum : 100; //< private final int
	//>public void fot()
	fot : function() {
		var x = new this.vj$.TypeA(); //< search.TypeA
		x.doIt();
	}
})
.protos({
	//> protected void doIt()
	doIt : function() {
		this.initialize(0);
		while(this.count < this.vj$.TypeB.maxNum) {
			this.vj$.TypeA.printNum(this.count);
			this.count++;
		}
		pr
	}
})
.endType();