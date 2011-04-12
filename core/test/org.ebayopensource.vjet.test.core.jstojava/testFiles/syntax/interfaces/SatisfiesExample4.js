vjo.ctype('syntax.interfaces.SatisfiesExample4')
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

	//> public String bar(int x)
	bar : function(x) {
		alert('bar');
		return "";
	}

})
.endType();