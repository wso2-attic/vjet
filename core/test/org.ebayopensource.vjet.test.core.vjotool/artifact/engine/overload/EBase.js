vjo.etype('engine.overload.EBase') //< public
.values({
	MON:[true, 'Monday'],
	TUE:[true, 'Tuesday'],
	WED:[true, 'Wednesday'],
	THU:[true, 'Thursday'],
	FRI:[true, 'Friday'],
	SAT:[false, 'Saturday'],
	SUN:[false, 'Sunday'] 
})
.props({
	
	//>private void pvtStaticCompute(int i, String s, Date d)
	//>private void pvtStaticCompute(int i, String s)
	//>private void pvtStaticCompute(int i)
	//>private void pvtStaticCompute() 
	pvtStaticCompute : function(){
	},
	
	//>public void pubStaticCompute(int i, String s, Date d)
	//>public void pubStaticCompute(int i, String s)
	//>public void pubStaticCompute(int i)
	//>public void pubStaticCompute() 
	pubStaticCompute : function(){
	},
	
	//>public void main() 
	main : function(){
		var ebase = this.vj$.EBase.MON; //< EBase
		ebase.pubCompute();
		this.pvtStaticCompute();
		
	}
  
})
.protos({
	
	//>private constructs()
	constructs : function(){
	},
	
	//>private void pvtCompute(int i, String s, Date d)
	//>private void pvtCompute(int i, String s)
	//>private void pvtCompute(int i)
	//>private void pvtCompute() 
	pvtCompute : function(){
	},
	
	//>public void pubCompute(int i, String s, Date d)
	//>public void pubCompute(int i, String s)
	//>public void pubCompute(int i)
	//>public void pubCompute() 
	pubCompute : function(){
	}

})
.endType();