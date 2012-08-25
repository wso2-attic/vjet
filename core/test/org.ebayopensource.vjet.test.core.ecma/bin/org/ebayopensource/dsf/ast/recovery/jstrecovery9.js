vjo.ctype('test1') //< public
.needs('test2')
.inherits('Inheritance.A')
.satisfies('test1')
.protos({
    //> protected String getState()
    getState:function(){
    },
    f:function(param){
        with (this){
            assertEquals("boo called",this.vj$.WithTest.boo());
            assertTrue(typeof (th)) === "undefined");
               
        }
    }
})
.endType();
