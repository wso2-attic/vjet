vjo.mtype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug8851MType')
.props({
    //> public int x_simpleM
    x_simpleM:80,
    //> public int s_mthd1()
    s_mthd1:function(){
        return this.x_simpleM;
    },
    //> public int s_add(int arg0,int arg1)
    s_add:function(arg0,arg1){
        return arg0+arg1;
    }
})
.protos({
    //> private int rate_simpleM
    rate_simpleM:2,   //HERER IS UNUSED PRIVATE PROPERTY
    //> public int ns_multiply(int arg0,int arg1)
    ns_multiply:function(arg0,arg1){
        return arg0*arg1;
    },
    //> public int ns_addRate(int arg0)
    ns_addRate:function(arg0){
        return arg0+this.rate_simpleM;
    }
})
.endType();