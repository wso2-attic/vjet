/* @com.ebay.dsf.resource.utils.CodeGen("VjoGenerator") */

vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.typecheck.returntype.TypecheckReturnType3') //< public
.protos({
    //> Number a()
    a:function(){
        return this.b1();
   	 },
   	 
   	     //> Number a1()
    a1:function(){
        return this.b2();
   	 },


        //> public String b1()
        b1:function(){
                return "HA";
        },

        //> public Number b2()
        b2:function(){
                return "HA";
        }

})
.endType();