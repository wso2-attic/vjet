vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug4839') //< public
.protos({
        //> public void foo(Bug4839 a)
		foo:function(a) {
			  var s = ""; //< String
			  var i = 10; //< int
			
			  s = a.get(0);
			
			  i = a.get(0);
		},
		
		//> public String get(int index)
		get : function(index){
			return "Test";
		} 

})
.endType();