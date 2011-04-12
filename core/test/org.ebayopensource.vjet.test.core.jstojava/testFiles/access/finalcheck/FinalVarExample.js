vjo.ctype('access.finalcheck.FinalVarExample')
.protos({
	
x : 100, //<public final int
   
//>public void constructs(String x1)
constructs :function(x1){  
},

//> final public int getX()
getX : function()
{   
	//> final String   
	var x = "String";       
	return this.x;          
}
})
.endType();