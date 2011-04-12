vjo.itype('presenter.TestInnerCompletion') //< public
.props({
        InnerStaticCType : vjo.ctype()
        .props({

        })
        .protos({
        	validProp1 : 30, //< int
	        validProp2 : "Test", //< String
	
	        constructs:function(){ //<public constructs()
	                this.validProp2
	                var date = new Date();//<Date
	                date.toString();
	        }
        })
        .endType()
})
.protos ({

})
.endType();
