vjo.ctype("search.TypeA")
.props({
	//> protected void printNum(int n)
	printNum : function(n) {
		vjo.sysout.println(n);
	}
})
.protos({
	count : 0, //< int
	
	//> public void initialize(int n)
	initialize : function(n) {
		this.count = n;
	},
	
	//> protected void doIt()
	doIt : function() {
		this.initialize(5);
		while(this.count > 0) {
			this.vj$.TypeA.printNum(this.count);
			this.count--;
		}
	}
	
})
.endType();