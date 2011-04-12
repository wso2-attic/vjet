vjo.ctype('syntax.generic.nested.NestedType10') //< public
.props({ 
		//>public void getM2(Object o) 
    	getM2 : function(v){
    	}
})
.protos({
	 InstanceInnerMethod:vjo.ctype() //< private InstanceInnerMethod<T>
    .protos({
    	//>public void getM2(Object o) 
    	getM2 : function(v){
    	},
    	
    	//>public void getV2() 
    	getV2 : function(){
    		var s ;//<T
    		this.vj$.NestedType10.getM2(s);
    		this.getM2(s);
    	}
     }).endType()  
})
.endType();