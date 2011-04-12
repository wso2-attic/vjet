
vjo.ctype('vjo.java.lang.NullPointerException') //< public
.inherits('vjo.java.lang.RuntimeException')
.protos({
    //> public constructs()
    //> public constructs(String s)
    constructs:function(){
        if(arguments.length===0){
            this.constructs_0_0_NullPointerException_ovld();
        }else if(arguments.length===1){
            this.constructs_1_0_NullPointerException_ovld(arguments[0]);
        }
    },
    //> private constructs_0_0_NullPointerException_ovld()
    constructs_0_0_NullPointerException_ovld:function(){
        this.base();
    },
    //> private constructs_1_0_NullPointerException_ovld(String s)
    constructs_1_0_NullPointerException_ovld:function(s){
        this.base(s);
    }
})
.endType();