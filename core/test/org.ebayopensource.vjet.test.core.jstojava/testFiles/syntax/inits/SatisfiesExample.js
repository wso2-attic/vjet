vjo.ctype('syntax.inits.SatisfiesExample')
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

	//> public boolean bar12(int x)
	bar12 : function(x) {
		alert('bar');
		return true;
	}

})
.inits(function(){
	 
})
.endType();