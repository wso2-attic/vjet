vjo.ctype("markOccurences.Bug1853")
.props({
	prop1 : 10,
	prop11 : "",
	//>public void func1() 
	func1 : function(){
		var prop1 = 20;
		prop1 = 50;
		this.prop1 = 50;
		this.prop1 = 90;
	}
})
.endType();