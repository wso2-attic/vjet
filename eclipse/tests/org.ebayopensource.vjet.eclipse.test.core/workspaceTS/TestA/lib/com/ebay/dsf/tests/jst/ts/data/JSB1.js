vjo.ctype("com.ebay.dsf.tests.jst.ts.data.JSB1")
.needs("com.ebay.dsf.tests.jst.ts.data.JSA1")
.inherits("com.ebay.dsf.tests.jst.ts.data.JSA1")
.protos({
 
    b:10,
    
    getP:function(){
        return this.base.p;
    }
})
.endType();