vjo.ctype('vjo.java.lang.Number') //< public abstract
.needs('vjo.java.lang.Util')
.satisfies('vjo.java.io.Serializable')
.props({
    serialVersionUID:-8742448824652078965 //< private final long
})
.protos({
    //> public int intValue()
    intValue:vjo.NEEDS_IMPL,
    //> public long longValue()
    longValue:vjo.NEEDS_IMPL,
    //> public float floatValue()
    floatValue:vjo.NEEDS_IMPL,

    //> public double doubleValue()
    doubleValue:vjo.NEEDS_IMPL,

    //> public byte byteValue()
    byteValue:vjo.NEEDS_IMPL,
    
    //> public short shortValue()
    shortValue:vjo.NEEDS_IMPL
})
.endType();