vjo.itype("syntax.generic.Map<K, V>")
.protos({
	get: vjo.NEEDS_IMPL //<V get(K)
	,
	put: vjo.NEEDS_IMPL //<void put(K, V)
})
.endType();