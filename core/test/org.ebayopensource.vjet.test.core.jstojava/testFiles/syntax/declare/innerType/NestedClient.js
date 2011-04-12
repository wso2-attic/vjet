vjo.ctype('syntax.declare.innerType.NestedClient') //< public
.needs('syntax.declare.innerType.NestedType1')
.props({
  //>public void main(String... args) 
  main : function(args){
	  var staticInnerType = new this.vj$.NestedType1.StaticInnerTypeName();
	  var outerType = new this.vj$.NestedType1() ; //< NestedType1
	  var innerType = new outerType.InstanceInnerType() ; // 
  }
})
.protos({

})
.endType();