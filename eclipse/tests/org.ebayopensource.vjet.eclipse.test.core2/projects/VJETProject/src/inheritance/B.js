vjo.ctype("inheritance.B")
.inherits("inheritance.A")

.protos({
    //> public void getStateName()
    foo1:function(){
       this.base.setState("Florida");
    }
});
