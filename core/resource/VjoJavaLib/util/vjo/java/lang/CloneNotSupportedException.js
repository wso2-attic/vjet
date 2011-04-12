vjo.ctype("vjo.java.lang.CloneNotSupportedException")
.inherits("vjo.java.lang.Exception")
.protos({
    //> public constructs(String [msg])
    constructs:function(msg){
        this.base(msg || "");
    }
})
.endType();