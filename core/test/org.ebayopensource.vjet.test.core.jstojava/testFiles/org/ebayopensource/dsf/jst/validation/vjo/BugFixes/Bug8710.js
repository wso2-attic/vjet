vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug8710")
.props({
})
.protos({

	//>public void foo(String) 
	foo : function(s){
	var v = 10;//<boolean
	var v1 = 10;//<Boolean
	}
})
.inits(function(){
})
.endType();
