vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug5061') //< public
.props({
        //> public void foo()
        foo: function() {
			var n1 = 1e23;
			var n2 = 2e22;
			var n3 = 1.6e24;
			
			alert(n1.toString());
			alert(n2.toString());
			alert(n3.toString());
        }
})
.endType();
