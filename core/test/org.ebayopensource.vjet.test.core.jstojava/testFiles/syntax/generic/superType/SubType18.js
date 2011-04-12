vjo.ctype('syntax.generic.superType.SubType18<K,V>') //< public
.inherits('syntax.generic.superType.SuperType1')
.props({
})
.protos({
	//>public void test(K, V) 
	test : function(k, v){
		
	},
	
	s1 : null,//<K
	
	s2: null,//<V
	
	//>public K getName() 
	getName : function(){
		this.test(this.s1,this.s2);
		this.test("","");
		return this.s1;
	}
}) 
.endType();