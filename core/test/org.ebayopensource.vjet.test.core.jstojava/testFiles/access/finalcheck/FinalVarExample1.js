vjo.ctype('access.finalcheck.FinalVarExample1')
.protos({

x : 0, //< final public int

//> final public void getX()
getX : function()
{
	this.x = 30;
},

//> final public void getX1(int)
getX1 : function(s)
{
	this.x = s;
	
	var finalVar = 100;//<final
	finalVar = -1;//should throw error
}
})
.endType();