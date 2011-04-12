vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.with_.With2') //< public
.protos({
    //> Number giveNumber(Number myNumber)
    giveNumber:function(myNumber){
		with(this){
			return 10;
		}
    },
    
    //> Number giveNumber1(Number myNumber)
    giveNumber1:function(myNumber){
		with(this){
			return "";
		}
    },
    
    //> String giveNumber2(Number myNumber)
    giveNumber2:function(myNumber){
		with(this){
			return 3;
		}
    }
    
})
.props({
})
.endType();