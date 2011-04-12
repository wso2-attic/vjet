vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug5685")
.props({
	staticProp1: "test",//< public String
	staticProp2: "test",//< public final String
	boolProp: true, //<boolean
	
	staticFunc1: function(s1, s2){//< public String staticFunc1(String, String)
		vjo.sysout.println("object identity is preserved");
		
		{
			if(true){
			
			}
		}
		
		return this.vj$.Bug5685.staticProp1;
	}
})
.endType();