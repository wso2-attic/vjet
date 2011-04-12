vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug4991') //< public
.props({
        //>public void foo() 
        foo : function(){

        },
        //>public void bar() 
        bar : function(){
                var a = this.foo(); //<String 
        }
})
.endType();
