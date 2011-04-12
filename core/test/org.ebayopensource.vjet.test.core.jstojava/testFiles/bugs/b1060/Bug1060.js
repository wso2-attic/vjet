vjo.ctype('bugs.b1060.Bug1060') //< public
.needs('bugs.b1060.DaysEnum')
.props({
  
})
.protos({
  //>public void foo() 
  foo : function(){
	vjo.sysout.println(this.vj$.DaysEnum.WED.name);
  	this.vj$.DaysEnum.WED = null;
  	vjo.sysout.println(this.vj$.DaysEnum.WED.name);
  }
})
.endType();