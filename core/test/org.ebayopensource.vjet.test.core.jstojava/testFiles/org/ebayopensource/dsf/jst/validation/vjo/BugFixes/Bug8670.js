vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug8670")
.props({
})
.protos({
	//>public void foo(String) 
	foo : function(model){
var x = {};

            x.bar(function() {

                  return 1;  //--- {int} needs to be compatible with return

            });

	}
})
.endType();