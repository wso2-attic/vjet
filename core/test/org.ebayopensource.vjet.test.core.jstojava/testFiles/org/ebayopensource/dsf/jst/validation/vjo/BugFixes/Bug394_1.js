vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug394_1') //< public
.protos({
	var1 : undefined, //< private String

	//> public void foo(void str)
	foo: function(str)  
	{
		this.var1 = str;
	}
})
.props({
	//> public String greeting(void name)
	greeting: function(name)  
	{
		return 'Welcome ' + name;
	}
})
.endType();