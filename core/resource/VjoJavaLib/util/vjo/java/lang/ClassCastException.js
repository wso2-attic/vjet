
vjo.ctype('vjo.java.lang.ClassCastException') //< public
.inherits('vjo.java.lang.RuntimeException')
.props({
    serialVersionUID:-9223365651070458532 //< private final long
})
.protos({
    //> public constructs()
    //> public constructs(String detailMessage)
    //> public constructs(vjo.Class instanceClass,vjo.Class castClass)
    constructs:function(){
        if(arguments.length===0){
            this.constructs_0_0_ClassCastException_ovld();
        }else if(arguments.length===1){
            this.constructs_1_0_ClassCastException_ovld(arguments[0]);
        }else if(arguments.length===2){
            this.constructs_2_0_ClassCastException_ovld(arguments[0],arguments[1]);
        }
    },
    //> private constructs_0_0_ClassCastException_ovld()
    constructs_0_0_ClassCastException_ovld:function(){
        this.base();
    },
    //> private constructs_1_0_ClassCastException_ovld(String detailMessage)
    constructs_1_0_ClassCastException_ovld:function(detailMessage){
        this.base(detailMessage);
    },
    //> private constructs_2_0_ClassCastException_ovld(vjo.Class instanceClass,vjo.Class castClass)
    constructs_2_0_ClassCastException_ovld:function(instanceClass,castClass){
        this.base(instanceClass.getName()+" incompatible with "+castClass.getName());
    }
})
.endType();