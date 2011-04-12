vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.jsnative.BooleanLiteral")
.props({
	 //>public void doIt() 
     doIt : function(){
             var v = Boolean(true);//<Boolean
             var z = v.valueOf(); //<boolean
             var z1 = v.valueOf(); //<<boolean
             var z2 = v.valueOf(); //<< ; WRONG ERROR

             var a = true; //<boolean
             var a1 = false; //<boolean

     }

})
.endType();