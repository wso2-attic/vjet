vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug1074_1') //< public
.props({
	//> public void test()
	test: function()  
	{
		vjo.Enum.boo = "boo";
		for(var key in vjo.Enum){
			vjo.sysout.println(key);
		}
	}
})
.endType();