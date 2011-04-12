vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.BugStringConcat')
.protos({

	foo: function(){
		var p = 2;
		var piVal = 10;
		var b = "doIt";//<String
		return parseInt(b.substring(0, p) + piVal + b.substring(p + 1), 2);
	}
})
.endType();