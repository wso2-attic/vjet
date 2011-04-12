vjo.ctype('syntax.interfaces.SatisfiesExample2')
.satisfies('syntax.interfaces.ITypeMethodsExamples1')
.props({

	//> void cool()
	cool: function() {
		alert('cool');
	},

	//> public void hot(boolean v)	
	hot:	function(v) {
		alert('hot');
	}	

})
.protos({
	//> public void foo()
	foo : function() {
		alert('foo');
	},

	//> public boolean bar(int x)
	bar : function(x) {
		alert('bar');
		return true;
	}

})
.endType();