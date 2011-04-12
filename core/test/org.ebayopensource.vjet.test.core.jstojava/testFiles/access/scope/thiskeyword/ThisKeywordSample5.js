vjo.ctype('access.scope.thiskeyword.ThisKeywordSample5')
.props({
	x : 10, //< public int
	
	//> public int getX()
	getX : function()
	{
		return this.vj$.ThisKeywordSample5.x;
	},

	//> public void main(String[] args)
	main : function(args)
	{
		vjo.sysout.println(this.vj$.ThisKeywordSample5.getX());
	}
})
.inits(
	 function(){
		this.vj$.ThisKeywordSample5.x = 10;
	}
)
.endType();
