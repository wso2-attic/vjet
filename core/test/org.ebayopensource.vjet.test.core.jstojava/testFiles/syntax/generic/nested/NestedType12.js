vjo.ctype('syntax.generic.nested.NestedType12') //< public
.needs('syntax.generic.HashMap')
//>needs(syntax.generic.Map)
.props({
	//>private void staticMehtod1() 
	staticMehtod1 : function(){
		
	}
})
.protos({
	 InstanceInnerMethod:vjo.ctype() //< private InstanceInnerMethod<T>
    .protos({
    	//>public void getV2() 
    	getV2 : function(){
    		this.vj$.outer.successor(new this.vj$.HashMap());
    		this.vj$.NestedType12.staticMehtod1();
    	}
     }).endType(),
     
	//> private Map<String,String> successor(Map<String,String> t)
    successor:function(t){
		return null;
    }
     
})
.endType();