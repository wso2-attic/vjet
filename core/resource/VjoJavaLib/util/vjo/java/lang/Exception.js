vjo.ctype("vjo.java.lang.Exception")
.protos({
    //> private String msg
    msg:null,
    //> public constructs(String [msg])
    constructs:function(msg){
        this.msg=msg || "";
    },
    
    //> public String toString()
    toString : function () {
    	return (this.getClass().getName() + ": \n" + this.msg);
    },
      //> public String getMessage()
    getMessage:function(){
        return this.msg;
    }
})
.endType();