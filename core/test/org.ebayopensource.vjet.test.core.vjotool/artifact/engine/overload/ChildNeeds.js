vjo.ctype('engine.overload.ChildNeeds') //< public
.needs('engine.overload.CBase')
.needs('engine.overload.EBase')
.props({
	
	//>public void main() 
	main : function(){
		var cbase = new this.vj$.CBase(); //< CBase
		var cpubVar = cbase.pubCompute();
		
		var ebase = this.vj$.EBase.MON; //< EBase
		ebase.pubCompute();
		
	}
  
})
.protos({

})
.endType();