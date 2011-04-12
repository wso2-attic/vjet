
vjo.ctype('vjo.java.lang.ArrayIndexOutOfBoundsException') //< public
.inherits('vjo.java.lang.IndexOutOfBoundsException')
.props({
    serialVersionUID:1 //< private final long
})
.protos({
    //> public constructs()
    //> public constructs(int index)
    //> public constructs(String s)
    constructs:function(){
        if(arguments.length===0){
            this.constructs_0_0_ArrayIndexOutOfBoundsException_ovld();
        }else if(arguments.length===1){
            if(typeof arguments[0]=="number"){
                this.constructs_1_0_ArrayIndexOutOfBoundsException_ovld(arguments[0]);
            }else if(arguments[0] instanceof String || typeof arguments[0]=="string"){
                this.constructs_1_1_ArrayIndexOutOfBoundsException_ovld(arguments[0]);
            }
        }
    },
    //> private constructs_0_0_ArrayIndexOutOfBoundsException_ovld()
    constructs_0_0_ArrayIndexOutOfBoundsException_ovld:function(){
        this.base();
    },
    //> private constructs_1_0_ArrayIndexOutOfBoundsException_ovld(int index)
    constructs_1_0_ArrayIndexOutOfBoundsException_ovld:function(index){
        this.base("Array index out of range: "+index);
    },
    //> private constructs_1_1_ArrayIndexOutOfBoundsException_ovld(String s)
    constructs_1_1_ArrayIndexOutOfBoundsException_ovld:function(s){
        this.base(s);
    }
})
.endType();