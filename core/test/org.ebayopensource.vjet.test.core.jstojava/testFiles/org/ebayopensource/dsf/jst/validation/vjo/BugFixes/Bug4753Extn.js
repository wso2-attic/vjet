vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug4753Extn') //< public 
.needs('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.CTypeUtil', 'util')
.props({
        //>public void foo() 
        foo : function(){
               this.vj$.noUtil.staticFunc();
        }
})
.endType();
