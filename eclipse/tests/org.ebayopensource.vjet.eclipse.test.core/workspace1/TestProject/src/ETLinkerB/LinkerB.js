vjo.ctype("ETLinkerB.LinkerB")
.needs("ETLinkerB.LinkerC")
.inherits("ETLinkerA.LinkerA")
.protos({
	//>public String s
	s : "str",
	//>public LinkerC fc
	fc : new C(),
	//>public void func()
	func : function() {
		this.s;
		this.func();
		this.base.s;
		this.base.func();
		this.funcC().c;
		this.fc.c;
	},
	//>public LinkerC funcC()
	funcC : function() {
		
	}
}).endType();