vjo.needs("thisCompletion.TypeC")
vjo.ctype("thisCompletion.TypeD")
.inherits("thisCompletion.TypeC")
.protos({
    //> constructs(int x,int y)
    constructs:function(x,y){
        this.base(x,y);
    }
})
.endType();