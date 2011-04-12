vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug5710")
.props({
		//>public Number foo(Number)
		foo: function(i){
			switch(i){
				case 0:
				case 1:
					return 100;
				case 2:
					return 1000;
				default:
					return 10000;
			}
			return -1;
		}
})
.endType();