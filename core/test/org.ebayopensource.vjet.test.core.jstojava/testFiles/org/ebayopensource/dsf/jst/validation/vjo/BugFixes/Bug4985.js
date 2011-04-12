vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug4985') //< public
.props({
        //> public void foo()
        foo: function() {
			this.bar("a", null, "b");
        },
        
        //> private void bar(String, String, String)
        bar: function(arg) {
        }
})
.endType();
