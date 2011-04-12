vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug4007_1') //< public
.protos({
	x : 10,  //< public int
	
	s : 10,  //< public int
	
	c : undefined, //< public String
	
	//> public String
	d : undefined,
	
	//> Number a()
	a:function(){
	   
	   this.x ="String";
	   
	   this.s = 'String';
	   
	   this.c=this.b();
	   
	   this.d = 20;
	},
	
	//> org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug4007_1 b()
	b:function(){
		return new org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug4007_1();
	}
})
.endType();