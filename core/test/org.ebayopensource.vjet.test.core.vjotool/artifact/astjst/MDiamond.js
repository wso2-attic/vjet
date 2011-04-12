vjo.mtype('astjst.MDiamond') //< public
.satisfies('astjst.IDiamond')
.needs(['astjst.DiamondShape', 'astjst.DiamondPurity'
        ,'astjst.DiamondBean'])
.props({
  totalCash : 200000, //<Number
  
  //> public String getVandor()
  getVandor : function(){
		return this.vj$.IDiamond.vendor;
	},
	
	//>public String getSite()
	getSite : function(){
		return this.vj$.IDiamond.site;
	}
	
})
.protos({
	//>public boolean buyDiamond(DiamondShape shape, DiamondPurity purity) 
	buyDiamond : function(shape, purity){
		var vendor = this.vj$.MDiamond.getVandor();//<String
		var site = this.vj$.MDiamond.getSite();//<String
		var diamondBean = this.getDiamondBean(shape, purity); //< DiamondBean
		this.vj$.type.totalCash = this.vj$.type.totalCash - diamondBean.getPrice();
		return true;
	},
	
	//>public boolean sellDiamond(int diamondId, Number diamondValue)
	sellDiamond : function(diamondId, diamondValue){
		return true;
	},
	
	//>private DiamondBean getDiamondBean(DiamondShape shape, DiamondPurity purity)
	getDiamondBean : function(shape, purity){
			return new this.vj$.DiamondBean(1, "CoolBlue", shape, purity, 2000);
	}
})
.endType();