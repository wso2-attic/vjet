vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug6358")
.props({
	
	foo: function(){
		var len = arguments.length;
		vjo.sysout.println(arguments.callee);
		vjo.sysout.println(arguments.caller);
		
		vjo.sysout.println(arguments[0]);
	}
})
.endType();