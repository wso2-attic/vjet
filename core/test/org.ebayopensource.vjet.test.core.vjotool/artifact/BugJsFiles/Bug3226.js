vjo.ctype('BugJsFiles.Bug3226')
.props({
	prop1 : 10,
	prop11 : "",
	//>public Number func1(String str1, Date date) 
	func1 : function(){
		var prop1 = 20;//<Number
		
		prop1 = 50;
		this.prop1 = 50;
		this.prop1 = 90;
		var a = new this.vj$.Bug3226();//<Bug3226
		BugJsFiles.Bug3226.func1("", null);
		return prop1;
	}
})
.protos({
	//>public constructs()
	constructs : function(){
		
	}
})
.endType();