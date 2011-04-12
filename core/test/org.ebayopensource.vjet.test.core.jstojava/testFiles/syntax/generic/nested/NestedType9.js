vjo.ctype('syntax.generic.nested.NestedType9') //< public
.props({ 
	
})
.protos({
	 InstanceInnerMethod:vjo.ctype() //< private InstanceInnerMethod<T>
    .protos({
    	//>public T getM2(T) 
    	getM2 : function(v){
    		return null;
    	},
    	
    	//>public void getV2() 
    	getV2 : function(){
    		var s ;//<String
    		s = this.getM2(2);
    	}
     }).endType()  
})
.endType();