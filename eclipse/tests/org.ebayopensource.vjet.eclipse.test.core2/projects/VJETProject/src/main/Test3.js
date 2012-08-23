vjo.ctype("main.Test3").protos({
 constructs : function (name) {
		this.name = name;
	},
 //>public String foo()
 foo : function() {
 	vjo.sysout.println("foo");
 	
 }
})
.props({
	main : function (args) {
	var test3 = new this("SimpleTest");	//call constructor
	test3.foo();
	}
});