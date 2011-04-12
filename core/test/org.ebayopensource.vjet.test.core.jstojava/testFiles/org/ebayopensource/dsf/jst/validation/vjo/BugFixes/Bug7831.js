vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug7831')
.props({
        prop1:10,
        //>public void foo(String s, String s2) 
        foo : function(s,s2){
        	if (typeof s != 'undefined' 
        && typeof s2 != 'undefined' 
        && !vjo.dsf.assembly.VjClientAssembler.bBodyLoaded) {
        	}
   }
})
.endType();

