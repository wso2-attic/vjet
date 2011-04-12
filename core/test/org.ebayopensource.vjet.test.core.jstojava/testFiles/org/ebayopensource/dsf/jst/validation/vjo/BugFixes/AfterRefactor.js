vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.AfterRefactor')
.props({
	p1: 100,//<int
	p2: [],//<String[]
	
	//>public void foo(int)
	foo: function(p){
		org.ebayopensource.dsf.jst.validation.vjo.BugFixes.AfterRefactor.p1 = 1000;
		var b = true;//<boolean
		b.toString();
		true.toString();
		var i = p;
		alert(i);

	}
})
.inits(function(){
	org.ebayopensource.dsf.jst.validation.vjo.BugFixes.AfterRefactor.p1 = 500;
})
.endType();