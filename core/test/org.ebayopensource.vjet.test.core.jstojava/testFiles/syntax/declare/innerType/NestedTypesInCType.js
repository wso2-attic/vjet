vjo.ctype('syntax.declare.innerType.NestedTypesInCType')
.props({
StaticNestedCType://> public StaticNestedCType
vjo.ctype()
.props({
//> public void doIt1()
doIt1:function(){
//Do nothing
}
})
.protos({
//> public void doIt2()
doIt2:function(){
//Do nothing
}
})
.endType(),
StaticNestedAbsCType://> public abstract StaticNestedAbsCType
vjo.ctype()
.props({
//> public void doIt1()
doIt1:function(){
//Do nothing
}
})
.protos({
//> public void doIt2()
doIt2:function(){
//Do nothing
}
})
.endType(),
StaticNestedIType://> public StaticNestedIType
vjo.itype()
.props({
// Only properties allowed in props for itype
//> final public String str1
str1 : ""
})
.protos({
// Only methods allowed in protos for itype
//> public void doIt()
doIt: vjo.NEEDS_IMPL

})
.endType(),
StaticNestedEType://> public StaticNestedEType
vjo.etype()
.values("val1, val2, val3, val4")
.endType()
})
.protos({
//> public String s2
s2:undefined,
AI1://> public AI1
vjo.itype()/*
.props({
//> final public String s3
s3:"",
//> public String s4
s4:""
})*/
.protos({
//> public void getIt()
getIt: vjo.NEEDS_IMPL

})
.endType(),
AC1://> public AC1
vjo.ctype()
.protos({
//> public String s7
s7:undefined,
//> public void getB1()
getB1:function(){
}
})
.endType(),
ACA1://> public abstract ACA1
vjo.ctype()
.protos({
//> public String s10
s10:undefined,
//> public void getB1()
getB1:function(){
}
})
.endType()
})
.endType();