vjo.ctype("com.ebay.dsf.tests.jst.ts.data.JSC1")
.needs("com.ebay.dsf.tests.jst.ts.data.JSB1")
.inherits("com.ebay.dsf.tests.jst.ts.data.JSB1")
.protos({
    
    c:0,
  
    city:null,
  
    setC:function(c){
        this.c=c;
    },
   
    getC:function(){
        return this.c;
    },
   
    setCity:function(cityName){
        this.city=cityName;
    },
  
    getCity:function(){
        return this.city;
    }
})
.endType();