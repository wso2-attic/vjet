vjo.mtype('engine.overload.MChild') //< public
.needs('engine.overload.CBase')
.needs('engine.overload.EBase')
.props({
	//>public void main() 
	main : function(){
		var cbase = new this.vj$.CBase(); //< CBase
		var pubVar = cbase.pubCompute();
		
		var ebase = this.vj$.EBase.MON; //< EBase
		ebase.pubCompute();
	}
})
.protos({

})
.endType();