vjo.ctype("thisCompletion.D")
.props({
    //> public void doStatic()
    doStatic:function(){
        vjo.sysout.println("doStatic");
    },
    //> public void main()
    main:function(){
        this.doStatic();
    }
})
.endType(); 