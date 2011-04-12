vjo.ctype('engine.overload.ChildInheritsABase') //< public
.inherits('engine.overload.ABase')
.props({
	
	//>public void main() 
	main : function(){
		var abase = new this.vj$.ChildInheritsABase(); //< ChildInheritsABase
		abase.abstFunc1();
	}
})
.protos({
	//>public void abstFunc1()
	//>public void abstFunc1(int i)
	//>public void abstFunc1(int i, String s)  
	//>public void abstFunc1(int i, String s, Date d)
	abstFunc1 : function(){
		return this.base.abstFunc1();
	}
})
.endType();