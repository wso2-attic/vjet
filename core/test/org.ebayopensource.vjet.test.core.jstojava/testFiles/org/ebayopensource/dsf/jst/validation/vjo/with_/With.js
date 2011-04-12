vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.with_.With') //< public
.protos({
	myNumber:10,
	
    //> Number giveNumber(Number myNumber)
    giveNumber:function(myNumber){
        return 10;
    },
    
    //> void b()
	b:function(){
		with(this){
			giveNumber(myNumber);
		}
		with(org.ebayopensource.dsf.jst.validation.vjo.with_.With){
			what();
			giveStaticNumber();
		}
	}
})
.props({
    //> Number giveStaticNumber()
	giveStaticNumber: function(){
		return 10;
	}
})
.endType();