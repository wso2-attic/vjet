vjo.ctype("access.scope.thiskeyword.ThisKeywordSample6")
.needs("access.scope.thiskeyword.ThisKeywordSample2")
.protos({
	x : 0, //< public int
	
	//> public int getX()
	getX : function()
	{
		return this.x;
	}
})
.props({
	//> public void main(String[] args)
	main : function(args)
	{
		var obj = new this.vj$.ThisKeywordSample6();
		var obj2 = this.vj$.ThisKeywordSample2.x;
		vjo.sysout.println(obj.getX());
	}
})
.endType();