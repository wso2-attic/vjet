vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug9650') //< public

.props({
	
		//>public void foo() 
	foo : function(){
		
		var result = Array(1 << 29).sort();
		
		var a = []; //< Array
		a.join();
		a.sort();

		var d = new Date(); //< Date
		d.setHours(10);
		d.setHours(10,9,8);
		
		Function.call();
		Function.apply();
		
	}
	
})
.protos({
	
})
.endType();