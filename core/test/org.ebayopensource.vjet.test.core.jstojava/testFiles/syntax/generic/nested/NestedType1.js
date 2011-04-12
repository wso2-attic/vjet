vjo.ctype('syntax.generic.nested.NestedType1') //< public
.needs('syntax.generic.superType.SuperType1')
.props({
	 StaticInnerMethod:vjo.ctype() //< private StaticInnerMethod<K,V>
    .inherits('syntax.generic.superType.SuperType1')    
    .protos({
     }).endType()
})
.protos({
	
})
.endType();