vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug8977")
.needs("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug8949")
.protos({
	
	Bug8949:org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug8949,//<Type::Bug8949
	
	//>public String foo() 
	foo : function(args){
	    this.Bug8949;
		new this.Bug8949();
		return;
	}
})
.endType();
