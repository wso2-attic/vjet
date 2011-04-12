vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug5678")
.props({
	staticProp1: "test",//< public String
	staticProp2: "test"//< public final String
})
.protos({

	//> public constructs()
	constructs: function(){
		this.vj$.Bug5678.staticProp1 = "";
		this.vj$.Bug5678.staticProp2 = "";
	},
	
	//> public void fun()
	fun: function(){
		this.vj$.Bug5678.staticProp1 = "";
		this.vj$.Bug5678.staticProp2 = "";
	}
})
.endType();