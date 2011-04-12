
vjo.ctype('vjo.java.lang.IndexOutOfBoundsException') //< public
.inherits('vjo.java.lang.RuntimeException')
.props({
    serialVersionUID:1 //< private final long
})
.protos({
    //> public constructs()
    //> public constructs(String s)
    constructs:function(){
        if(arguments.length===0){
            this.constructs_0_0_IndexOutOfBoundsException_ovld();
        }else if(arguments.length===1){
            this.constructs_1_0_IndexOutOfBoundsException_ovld(arguments[0]);
        }
    },
    //> private constructs_0_0_IndexOutOfBoundsException_ovld()
    constructs_0_0_IndexOutOfBoundsException_ovld:function(){
        this.base();
    },
    //> private constructs_1_0_IndexOutOfBoundsException_ovld(String s)
    constructs_1_0_IndexOutOfBoundsException_ovld:function(s){
        this.base(s);
    }
})
.endType();