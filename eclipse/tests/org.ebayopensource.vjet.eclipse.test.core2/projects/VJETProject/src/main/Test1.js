vjo.ctype("main.Test1")
.protos({
 constructs : function () {
	
	},
 //>public String foo()
 foo : function() {
 	vjo.sysout.println("foo");
 	
 }
})
.props({
   //> public void stFoo()
    stFoo:function(){
    }
	main : function (args) {
	var test1=new this();
     test1.foo();
	
	}
});