vjo.ctype('astjst.DiamondImpl') //< public
.needs(['astjst.DiamondShape', 'astjst.DiamondPurity'
        	,'astjst.DiamondBean'])
.mixin('astjst.MDiamond')
.props({
  totalDiamonds : undefined //<Number
})
.protos({
	//>public Number getTotalCash()
	gettotalCash : function(){
			return this.vj$.DiamondImpl.totalCash;
	},
	
	//>public boolean isDiamondBougth(DiamondShape shape, DiamondPurity purity)
	isDiamondBougth : function (shape, purity) {
			return this.buyDiamond(shape, purity);
	},
	
	//>public boolean isDiamondSold(int id, Number value)
	isDiamondSold : function (id, value) {
			return this.sellDiamond(id, value);
	}
})
.endType();