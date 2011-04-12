vjo.ctype('syntax.generic.superType.SubType19<K,V>') //< public
.needs('syntax.generic.superType.SubType18')
.inherits('syntax.generic.superType.SuperType1<E>')
.props({
})
.protos({
	//>public void test(K, V) 
	test : function(ik, iv){
		var swr = new this.vj$.SubType18();//<SubType18<K,V>;
		swr.test(this.s1,this.s2);
	},
	
	s1 : null,//<K
	
	s2: null,//<V
	
	//>public K getName() 
	getName : function(){
		this.test(this.s1,this.s2);
		return this.s1;
	}
}) 
.endType();