vjo.ctype('syntax.generic.nested.NestedType13') //< public NestedType13<T>
.protos({
	 InstanceInnerMethod:vjo.ctype() //< private
    .protos({
    	
    	s : null,//<T
    	
    	//>public T getV2() 
    	getV2 : function(){
    		return this.vj$.outer.view(this.s);
    	}
     }).endType(),
     
    //>public T view(T) 
    view : function(t){
    	return t;
    }
     
})
.endType();