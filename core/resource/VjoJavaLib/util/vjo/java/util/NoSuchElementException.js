vjo.ctype('vjo.java.util.NoSuchElementException') //< public
.inherits('vjo.java.lang.RuntimeException')
.props({
    serialVersionUID:1 //< private final long
})
.protos({
    //> public constructs()
    //> public constructs(String s)
    constructs:function(){
        if(arguments.length===0){
            this.constructs_0_0_NoSuchElementException_ovld();
        }else if(arguments.length===1){
            this.constructs_1_0_NoSuchElementException_ovld(arguments[0]);
        }
    },
    //> private constructs_0_0_NoSuchElementException_ovld()
    constructs_0_0_NoSuchElementException_ovld:function(){
        this.base();
    },
    //> private constructs_1_0_NoSuchElementException_ovld(String s)
    constructs_1_0_NoSuchElementException_ovld:function(s){
        this.base(s);
    }
})
.endType();