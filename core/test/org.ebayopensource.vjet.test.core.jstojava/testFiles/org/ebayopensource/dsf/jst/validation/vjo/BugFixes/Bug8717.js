vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug8717")
.props({
	sv : null//<final Number
})
.protos({
	si : null,//<final Number
	
	//>public constructs()
	constructs : function(){
		this.si = 2; 
		this.si = 3;
	}
})
.inits(function(){
	this.sv = 30;
	this.sv = 20;
})
.endType();
