vjo.ctype("thisCompletion.TypeC")
.protos({
    //> int a
    a:0,
    //> int b
    b:0,
    //> constructs(int x,int y)
    constructs:function(x,y){
        this.a=x;
        this.b=y;
    }
})
.endType();