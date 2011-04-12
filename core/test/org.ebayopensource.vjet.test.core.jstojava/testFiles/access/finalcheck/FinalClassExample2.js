vjo.ctype('access.finalcheck.FinalClassExample2')
.needs('access.finalcheck.FinalClassExample')
.protos({
	InnerClass : vjo.ctype()
	.inherits('access.finalcheck.FinalClassExample')
	.protos({
		doIt : function()//< void doIt()
		{
    	}})
    	.endType()
})
.props({
})
.endType();
