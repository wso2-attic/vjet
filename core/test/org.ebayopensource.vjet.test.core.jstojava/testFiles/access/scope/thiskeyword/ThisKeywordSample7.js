vjo.ctype('access.scope.thiskeyword.ThisKeywordSample7')
.needs('access.scope.thiskeyword.ThisKeywordSample2')
.protos({
	x : 0, //< public int
	
	//> public void constructs()
	constructs : function ()
	{
		this.x = this.vj$.ThisKeywordSample2.x;
	},

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
		var obj = new this.vj$.ThisKeywordSample7();
		vjo.sysout.println(obj.getX());
	}
})
.endType();