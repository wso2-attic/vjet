vjo.ctype('syntax.generic.nested.NestedType6') //< public
.needs('syntax.generic.nested.NestedType5<E>')
.props({ 
	
})
.protos({
	 InstanceInnerMethod:vjo.ctype() //< private InstanceInnerMethod<T>
    .protos({
    	//>public void getM2(T) 
    	getM2 : function(v){
    		new this.vj$.NestedType5<E>();
    	}
     }).endType()  
})
.endType();