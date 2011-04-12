vjo.ctype('syntax.generic.nested.NestedType4') //< public NestedType4<E>
.needs('syntax.generic.superType.SuperType1')
.props({
})
.protos({
	 InstanceInnerMethod:vjo.ctype() //< private InstanceInnerMethod<T>
    .inherits('syntax.generic.superType.SuperType1<T>')    
    .protos({
     }).endType()  
})
.endType();