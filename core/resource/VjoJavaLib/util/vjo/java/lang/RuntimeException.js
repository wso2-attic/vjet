vjo.ctype("vjo.java.lang.RuntimeException")
.inherits("vjo.java.lang.Exception")
.protos({
    //> private String msg
    msg:null,
    //> public constructs(String [msg])
    constructs:function(msg){
        this.msg=msg || "";
    },
     //> public String getMessage()
    getMessage:function(){
        return this.msg;
    }
})
.endType();