vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug4446_1') //< public
.protos({
	a : 10,  //< public int
	b : 10.1,  //< public float
	c : undefined, //< public String
	d : undefined, //< private boolean
	
	//> public void test()
	test:function(){
		this.a = '10.1';
		this.a = 10.1;
		this.a = true;

		this.b = '10.1';
		this.b = 10;
		this.b = false;
	
		this.c = 10;
		this.c = 10.1;
		this.c = false;

		this.d = 10;
		this.d = 10.1;
		this.d = '10';
	}
})
.endType();