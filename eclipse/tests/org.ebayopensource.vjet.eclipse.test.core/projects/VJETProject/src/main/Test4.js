vjo.ctype("main.Test4")
.props({
    //> public int s_var1
    s_var1:0,
    //> public int s_var2
    s_var2:0,
    //> public void doStatic1(int z)
    doStatic1:function(z){
        this.s_var1=z;
    },
    //> public void doStatic2()
    doStatic2:function(){this.doStatic1(30);
    },
    //> public void main()
    main:function(){
        var testObj=new this();
        testObj.doIt(2,3,true);
    }
})
.protos({
    //> int m_var1
    m_var1:10,
    //> public int doIt(int x,int y,boolean isAdd)
    doIt:function(x,y,isAdd){
        var sum=0;
        if(isAdd){
            sum=x+y;
        }
        return sum;
    }
})
.endType();