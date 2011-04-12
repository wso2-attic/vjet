vjo.ctype('syntax.generic.nested.NestedType11') //< public
.needs('syntax.generic.HashSet')
//>needs(syntax.generic.Map)
.protos({
	 InstanceInnerMethod:vjo.ctype() //< private InstanceInnerMethod<T>
  .protos({
  	//>public void getV2() 
  	getV2 : function(){
  		var set = new this.vj$.HashSet();//<HashSet<Map>
  	}
   }).endType()  
})
.endType();