vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug8671")
.protos({
	//>public void foo() 
	foo : function(){
		x = 1, y = 2;
		alert(y);
	}
})
.endType();