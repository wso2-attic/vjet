vjo.ctype('vjo.java.util.ConcurrentModificationException') //< public
.inherits('vjo.java.lang.RuntimeException')
.props({
    serialVersionUID:1 //< private final long
})
.protos({
    //> public constructs()
    //> public constructs(String message)
    constructs:function(){
        if(arguments.length===0){
            this.constructs_0_0_ConcurrentModificationException_ovld();
        }else if(arguments.length===1){
            this.constructs_1_0_ConcurrentModificationException_ovld(arguments[0]);
        }
    },
    //> private constructs_0_0_ConcurrentModificationException_ovld()
    constructs_0_0_ConcurrentModificationException_ovld:function(){
        this.base();
    },
    //> private constructs_1_0_ConcurrentModificationException_ovld(String message)
    constructs_1_0_ConcurrentModificationException_ovld:function(message){
        this.base(message);
    }
})
.endType();