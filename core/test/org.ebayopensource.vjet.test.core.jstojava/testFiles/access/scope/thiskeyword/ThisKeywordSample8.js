vjo.ctype('access.scope.thiskeyword.ThisKeywordSample8')
.inherits('access.scope.thiskeyword.ThisKeywordSample1')
.protos({
	x1 : 0, //< public int
	
	//> public void constructs(int val)
	constructs : function (val)
	{
		this.base(val*2);
		this.x1 = val;
	},

	//> public int getX()
	getX : function()
	{
		return this.x1;
	},
	
	//> public void getValues()
	getValues : function()
	{
		vjo.sysout.println(this.getX());
		vjo.sysout.println(this.base.getX());
	}
})
.props({
	//> public void main(String[] args)
	main : function(args)
	{
		var obj = new this.vj$.ThisKeywordSample8(10); //<ThisKeywordSample8
		obj.getValues();
	}
})
.endType();