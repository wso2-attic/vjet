vjo.needs("defect.TypeA")
vjo.ctype("defect.TypeB")
.inherits("defect.TypeA")
.props({
	maxNum : 100; //< private final int
	//>public void fot()
	fot : function() {
		var x = new this.vj$.TypeA(); //< defect.TypeA
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
	},
	
	//> public void sayHello()
	sayHello : function() {
		var inst = new TypeB();
		inst.fot();
	}
})
.endType();