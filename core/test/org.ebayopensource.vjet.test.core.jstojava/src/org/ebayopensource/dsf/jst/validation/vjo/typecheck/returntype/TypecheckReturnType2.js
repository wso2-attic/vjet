/* @com.ebay.dsf.resource.utils.CodeGen("VjoGenerator") */

vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.typecheck.returntype.TypecheckReturnType2') //< public
.protos({
    //> Number a()
    a:function(){

        //> public String
        var d;
        d = this.b2();//<<String
        // TypeMismatch here

        //> public Number
        var d1 = 0;
        d1 = this.b1();
            // TypeMismatch here
        
        return d1;
   	 },


        //> public String b1()
        b1:function(){
                return "HA";
        },

        //> public Number b2()
        b2:function(){
                return 34;
        }

})
.endType();