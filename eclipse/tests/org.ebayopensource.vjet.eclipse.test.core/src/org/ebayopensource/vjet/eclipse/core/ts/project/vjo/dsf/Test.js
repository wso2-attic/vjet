vjo.ctype("vjo.dsf.Test")
.props({
        //>public  void hello()
        hello : function() {
                alert("test");

        }
}).protos({
	//>public void foo()
        foo:function(){

        };
        //>public  void bar()
        bar: function () {
            this.base.foo();
            vjo.$static(this).hello();
        }
})

