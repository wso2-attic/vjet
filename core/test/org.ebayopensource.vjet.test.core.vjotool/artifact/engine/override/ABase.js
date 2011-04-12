vjo.ctype('engine.override.ABase') //< public abstract
.props({

	//> public void pubStatAbstFunc()
	pubStatAbstFunc : vjo.NEEDS_IMPL,
	
	//> public void pubStatFunc() 
	pubStatFunc : function(){
	
	}
  
})
.protos({
	
	//> public void pubAbstFunc()
	pubAbstFunc : vjo.NEEDS_IMPL,
	
	//> public void pubFunc() 
	pubFunc : function(){
		
	},
	
	//> public final void pubFinalFunc() 
	pubFinalFunc : function(){
		
	},
	
	//>private void pvtFunc() 
	pvtFunc : function(){
		
	}
})
.endType();