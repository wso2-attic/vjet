vjo.needs("inheritance.TypeA")
vjo.ctype("inheritance.TypeB")
.inherits("inheritance.TypeA")
.protos({
    //> public void fooA()
    fooA:function(){}
})
.endType();
