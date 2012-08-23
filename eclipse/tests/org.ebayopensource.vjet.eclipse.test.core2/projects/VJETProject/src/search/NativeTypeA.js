
vjo.ctype('search.NativeTypeA') //< public
.props({
    //> protected void printNum(int n)
    printNum:function(n){
        vjo.sysout.println(n);
        Array.reverse();
        var elementId="input1";
        document.getElementById(elementId);
    }
})
.protos({
    count:0, //< int
    //> public void initialize(int n)
    initialize:function(n){
        this.count=n;
    },
    //> protected void doIt()
    doIt:function(){
        this.initialize(5);
        while(count>0){
            this.vj$.NativeTypeA.printNum(count);
            count--;
        }
        Array.reverse();
    }
})
.endType();