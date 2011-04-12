vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug5066")
.props({
	//>public void foo()
	foo: function(){
		alert(Array().length);
		alert(Array(1,2,3).length);
		alert(Array(123).length);
	}
})
.endType();