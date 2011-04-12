vjo.itype('astjst.IDiamond') //< public
.needs(['astjst.DiamondShape', 'astjst.DiamondPurity'])
.props({
  vendor : "blueniles",//<String
  site : "ebay" //<String
})
.protos({
	//>public boolean buyDiamond(DiamondShape shape, DiamondPurity purity) 
	buyDiamond : vjo.NEEDS_IMPL,
	//>public boolean sellDiamond(int diamondId, Number diamondValue)
	sellDiamond : vjo.NEEDS_IMPL
})
.inits(function(){
//	this.vendor = "blueniles";
//	this.site = "ebay";
	
})
.endType();