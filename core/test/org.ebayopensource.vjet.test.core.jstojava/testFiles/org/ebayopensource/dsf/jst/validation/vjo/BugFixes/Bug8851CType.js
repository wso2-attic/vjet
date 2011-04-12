vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug8851CType') //<abstract
.mixin('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug8851MType')
.props({
    //> public String y_ASimple
    y_ASimple:"In inASimple",
    //> public String s_getX()
    s_getX:function(){
        return this.y_ASimple+9;
    }
})
.protos({
    //> private int x_ASimple
    x_ASimple:20,
    //> int z_ASimple
    z_ASimple:208,
    //> public constructs()
    constructs:function(){
        this.base();
    },
    //> public int aSimple_mthd1()
    aSimple_mthd1:function(){
        return 7;
    },
    //> public int aSimple_mthd2()
    aSimple_mthd2:function(){
        return this.x_ASimple;
    },
    //> public abstract String ns_mthd3()
    ns_mthd3:function(){
    }
})
.endType();