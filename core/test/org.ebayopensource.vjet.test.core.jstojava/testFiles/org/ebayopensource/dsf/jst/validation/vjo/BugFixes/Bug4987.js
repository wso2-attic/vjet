vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug4987') //< public
.props({
        //> private void foo()
        foo: function() {

        },
        
        //> public void bar()
        //> public void bar(String)
        bar: function(arg) {
        }
}).inits(function(){
	this.vj$.Bug4987.foo();
})
.endType();
