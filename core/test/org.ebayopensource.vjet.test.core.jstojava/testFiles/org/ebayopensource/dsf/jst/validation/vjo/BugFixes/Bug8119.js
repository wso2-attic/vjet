vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug8119')
.protos({
	protfoo: function(){
	}
})
.props({
	//>public void foo(String)
	foo: function(arg1){
		protfoo();//should report undefined method here
	}
})
.endType();