vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.BugNestedFunction') //< public
.protos({
	foo: function(){
		function f(p1, p2){
			function g(){
				alert(p1);
				alert(p2);
				f('a', 'b');
			}
		}
	}
})
.endType();
