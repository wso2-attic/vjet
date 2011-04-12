vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug8624")
.props({
})
.protos({
	//>public void foo(String) 
	foo : function(model){
		  var v = Boolean(true);//<Boolean
		  var z1 = v.valueOf(); //<<boolean
	}
})
.endType();