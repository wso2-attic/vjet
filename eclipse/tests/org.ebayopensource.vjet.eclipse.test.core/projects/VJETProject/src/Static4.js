vjo.ctype("Static4")
.props({
    //> int var1
    var1:6,
    //> int var2
    var2:9,
    //> int var3
    var3:0
})
.protos({
    //> int var4
    var4:0
    //> int showValues()
    showValues:function(){
        return this.vj$.Static4.var3;
    }
})
.inits(function(){
    {
        for (var cnt=0;cnt<this.var2;cnt++){
            this.var3+=this.var1;
        }
    }
})
.endType();