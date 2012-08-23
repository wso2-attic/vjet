vjo.needs("thisCompletion.A");
vjo.ctype("thisCompletion.B").protos({
 //> A a
    a:null,//<thisCompletion.A
    
    //> constructs()
    constructs:function(){
        this.a=new this.vj$.A();
        
    },
    //> public String fooB()
    fooB:function(){
         this.a.fooA();
    }


})