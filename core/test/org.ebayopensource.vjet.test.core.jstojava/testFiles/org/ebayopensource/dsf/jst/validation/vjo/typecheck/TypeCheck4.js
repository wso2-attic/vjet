vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.typecheck.TypeCheck4') //< public
.needs("org.ebayopensource.dsf.jst.validation.vjo.typecheck.TypeCheck3")
.props({
        //>public void bar() 
        bar : function(){
             var sample1 = new this.vj$.TypeCheck4(); //< TypeCheck4
             var sample2  = new this.vj$.TypeCheck3(); //< TypeCheck3
             sample1 = sample2;
        }
})

.endType();