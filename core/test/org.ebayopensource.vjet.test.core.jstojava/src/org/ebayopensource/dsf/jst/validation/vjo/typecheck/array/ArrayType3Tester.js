/* @com.ebay.dsf.resource.utils.CodeGen("VjoGenerator") */

vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.typecheck.array.ArrayType3Tester') //< public
.props({
    //> public void a()
    a:function(){
    	var v1 = new Array(1,2,3,4);
    },
    
    //> public void a1()
    a1:function(){
    	var v1 = new Array("v1","v2");
    },
    
    //> public Number a2()
    a2:function(){
    	var v1 = new Array("v1",20, true);
    }
})
.endType();