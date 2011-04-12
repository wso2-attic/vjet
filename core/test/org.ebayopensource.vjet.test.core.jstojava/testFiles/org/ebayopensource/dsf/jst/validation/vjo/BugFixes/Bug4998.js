vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug4998') //< public
.props({
        //>public void bar() 
        bar : function(){
        	msg();//<@SUPRESSTYPECHECK
        }

})
.endType();
