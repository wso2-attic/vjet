/* @com.ebay.dsf.resource.utils.CodeGen("VjoGenerator") */

vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.typecheck.array.ArrayType1Tester') //< public
.props({
    //> public void a()
    a:function(){
    	var pros = ["SHSH", 2, 23.42, true]; //< Array
    },
    
    //> public void a1()
    a1:function(){
    	var pros = ["1", 2];
    },
    
 	//> public void a2()
    a2:function(){
    	var pros = ["1", '2']; //<String[]
    },
    
    //> public void a3()
    a3:function(){
    	var pros = ["1", '2', true];
    },
    
    //> public void a5()
    a5:function(){
    	var pros = ["1", 23.02 ];
    }
})
.endType();