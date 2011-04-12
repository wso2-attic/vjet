vjo.ctype('access.scope.var.AccessVar2Tester')
.protos({
	m_defaultDept:undefined, //< String
	
	m_publicSex:undefined, //< String
	
	m_publicAddress1:undefined, //< String
	
		InnerClass : vjo.ctype()
	.protos({
		doIt : function()//< void doIt()
		{
			document.writeln('Instance Inner Class doIt Called');
			this.m_publicSex = "String";
    		}
    	})
    	.endType()

})
.props({
	x : 10, //< int
	
	//> int getX()
	getX : function()
	{
		return this.x;
	}	
})
.endType();