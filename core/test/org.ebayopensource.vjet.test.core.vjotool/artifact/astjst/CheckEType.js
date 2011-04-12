vjo.etype('astjst.CheckEType') //< public
.satisfies('astjst.IDiamond')
.needs('astjst.DiamondShape','astjst.DiamondPurity')
.values('Start, Middle, End')
.protos({
	//>public boolean sellDiamond(int diamondId, Number diamondValue)
	sellDiamond : function(diamondId, diamondValue){
		return null;
	},
	
	//>public boolean buyDiamond(DiamondShape shape, DiamondPurity purity)
	buyDiamond : function(shape, purity){
		var x = this.vj$.CheckEType.Start;//<CheckEType
	    var y = this.vj$.CheckEType.Middle;//<CheckEType
		return null;
	}
})
.props({
	//>public void main(String args)
	main: function(args) {
        var out = vjo.sysout.println ; //<Function
        out();
        out(this.Middle) ;
    }
})
.endType();