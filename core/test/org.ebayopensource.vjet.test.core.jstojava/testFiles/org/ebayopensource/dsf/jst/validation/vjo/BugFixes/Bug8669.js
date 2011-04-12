vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug8669")
.props({
})
.protos({
	//>public void foo(String) 
	foo : function(model){
		              var x = {};

            x.bar(function(n) {

                  var y = n;  //--- variable n undefined

            });
	}
})
.endType();