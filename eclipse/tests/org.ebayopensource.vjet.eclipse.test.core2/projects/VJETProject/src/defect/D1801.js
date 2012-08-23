vjo.ctype("defect.D1801")
.props({
    //> public void main(String[])
    main:function(){
        vjo.sysout.println("main");
        vjo.sysout.println(arguments[0]);
        vjo.sysout.println(arguments[1]);
    }
})
.endType();
