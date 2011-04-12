vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug8715")
.props({
	
	//>public void main(String... args) 
	main : function(args){
		parseFloat(new String(1.0/0.0));
		parseFloat(new String(-1.0/0.0));
		parseFloat(new String(0.0/0.0));
	}
})
.endType();
