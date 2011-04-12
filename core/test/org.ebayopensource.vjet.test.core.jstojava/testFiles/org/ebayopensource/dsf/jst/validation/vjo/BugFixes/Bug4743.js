vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug4743') //< public
.needs('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.CTypeUtil')
.props({
        //>public void foo() 
        foo : function(){
               //this.vj$.util.staticFunc();
               var a = org.ebayopensource.dsf.jst.validation.vjo.BugFixes.CTypeUtil;
               a.staticFunc();
        }
})
.endType();
