vjo.etype('access.innerClass.InnerclassInEType')
.props({
	staticCInner:vjo.ctype()
	.endType(),
	staticEInner:vjo.etype()
	.endType(),
	staticMInner:vjo.mtype()
	.endType(),
	staticAInner:vjo.ctype() //<abstract
	.endType(),
	staticOInner:vjo.otype()
	.endType(),
	staticIInner:vjo.itype()
	.endType()
	
})
.protos({
	instanceCInner:vjo.ctype()
	.endType(),
	instanceEInner:vjo.etype()
	.endType(),
	instanceMInner:vjo.mtype()
	.endType(),
	instanceAInner:vjo.ctype() //<abstract
	.endType(),
	instanceOInner:vjo.otype()
	.endType(),
	instanceIInner:vjo.itype()
	.endType()
})
.endType();