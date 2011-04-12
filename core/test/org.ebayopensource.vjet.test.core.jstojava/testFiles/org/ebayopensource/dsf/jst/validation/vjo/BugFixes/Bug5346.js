vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug5346")
.props({
	ref: null,
	ref2: null,//<HTMLAnchorElement
	
	foo: function(){
		this.ref.style.left = "100px";
		this.ref2.style.left = "100px";
	}
})
.inits(function(){
	this.ref = document.getElementById("a");
	this.ref2 = document.getElementById("a");//<<HTMLAnchorElement
})
.endType();