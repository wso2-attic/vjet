vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.typecheck.TypeCheck5') //< public
.needs("org.ebayopensource.dsf.jst.validation.vjo.typecheck.TypeCheck3")
.props({
        //>public void bar() 
        bar : function(){
             var sample1;//< TypeCheck5
             var sample2;//< TypeCheck3
             sample1 = sample2;
        }
})

.endType();