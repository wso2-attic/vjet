vjo.ctype('engine.overload.ABase') //< public abstract
.props({
	
})
.protos({
	//>public void abstFunc1(int i, String s, Date d)
	//>public void abstFunc1(int i, String s)
	//>public void abstFunc1(int i)
	//>public void abstFunc1()
	abstFunc1 : vjo.NEEDS_IMPL,
	
	
	//>public void nonAbstFunc1(int i, String s, Date d)
	//>public void nonAbstFunc1(int i, String s)
	//>public void nonAbstFunc1(int i)
	//>public void nonAbstFunc1() 
	nonAbstFunc1 : function(){
		
	},
	
	//>public void foo() 
	foo : function(){
		this.nonAbstFunc1();
	}
})
.endType();