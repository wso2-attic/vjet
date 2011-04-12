vjo.itype('engine.overload.IBase') //< public
.props({
	
})
.protos({
	//>public String func(int i, String s, Date d)
	//>public String func(int i, String s)
	//>public String func(int i)
	//>public String func() 
	func : vjo.NEEDS_IMPL
})
.endType();