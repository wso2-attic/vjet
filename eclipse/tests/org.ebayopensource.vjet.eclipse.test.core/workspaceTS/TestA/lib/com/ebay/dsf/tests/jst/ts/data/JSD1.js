vjo.ctype("com.ebay.dsf.tests.jst.ts.data.JSD1")
.needs("com.ebay.dsf.tests.jst.ts.data.JSC1")
.inherits("com.ebay.dsf.tests.jst.ts.data.JSC1")
.protos({
   
    d:10,
    
    addCD:function(){
        this.base.setC(10);
        return (this.d+this.getC());
    },
   
    getCityFromC:function(){
        this.base.setCity("San Jose");
        return this.base.getCity();
    },
    
    getStateFromA:function(){
        this.base.setState("California");
        return this.base.getState();
    }
})
.endType();