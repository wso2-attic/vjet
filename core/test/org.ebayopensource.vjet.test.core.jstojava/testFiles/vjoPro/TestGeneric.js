vjo.ctype('vjoPro.TestGeneric<E>')
.props({
	
	//> public boolean doIt(E)
	doIt:function(o){
		
	},
	//> public boolean add(E)
	add:function(o){
		this.doIt(o);
	},
	
})