/* @com.ebay.dsf.resource.utils.CodeGen("VjoGenerator") */

vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.typecheck.returntype.TypecheckReturnTypeCorrect1') //< public
.protos({
	
	x : undefined, //< public int
	
	s_name : undefined, //< public String
	
	s_sex : undefined, //< public String

    //> int a()
    a:function(){
        return 10;
    },
    
    //> public String a1()
    a1:function(){
        return "HA"
    }
    
})
.endType();