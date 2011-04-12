vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug8623")
.props({
	//>public void fd() 
	fd : function(){
		alert("D")	;
	}
})
.protos({
	//>public void foo(Object... os) 
	foo : function(os){
		if(arguments.length > 1){
			if(arguments[0] instanceof String){
			}
		}
	}
})
.endType();