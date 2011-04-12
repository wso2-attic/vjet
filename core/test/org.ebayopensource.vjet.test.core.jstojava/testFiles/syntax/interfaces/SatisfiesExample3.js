vjo.ctype('syntax.interfaces.SatisfiesExample3')
.satisfies('syntax.interfaces.ITypeStaticMembers1')
.props({

	//> void cool()
	cool: function() {
		this.vj$.ITypeStaticMembers1.s_start = 23;  //Can't change final one
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