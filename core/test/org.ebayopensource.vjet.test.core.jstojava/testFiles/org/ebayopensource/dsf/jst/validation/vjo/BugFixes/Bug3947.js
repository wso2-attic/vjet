vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug3947') //< public
.props({
    StatInner:vjo.ctype() //< public
    .endType(),
    Day:vjo.etype() //< public
    .values("MON, TUES")
    .endType()
})
.protos({
    IInner:vjo.itype() //< public
    .endType(),
    Inner:vjo.ctype() //< private
    .protos({
        Inner_1:vjo.ctype() //< private
        .endType()
    })
    .endType(),
    Prot_Innner:vjo.ctype() //<
    .protos({
        Prot_Inner_1:vjo.ctype() //<
        .endType()
    })
    .endType(),
    AbsInner:vjo.ctype() //< public abstract
    .protos({
        //> abstract String methodX()
        methodX: vjo.NEEDS_IMPL
        
    })
    .endType(),
    Inner1:vjo.ctype() //< public
    .inherits("org.ebayopensource.dsf.tests.js.rt.clz.tc2.NestedStruc.AbsInner")
    .protos({
        //> String methodX()
        methodX:function(){
            return "in Inner1.AbsInner";
        }
    })
    .endType(),
    //> public String methodY()
    methodY:function(){
                var Point = vjo.ctype() //<
        .endType();
        var v_name=Point.clazz.getName();
        var v_simpleName=Point.clazz.getSimpleName();
        var v_toString=Point.clazz.toString();
        var v_isInterface=Point.clazz.isInterface();
        var v_packageName= Point.clazz.getPackageName();
         return v_name+":"+v_simpleName+":"+v_toString+":"+v_isInterface+":"+v_packageName;
    },
    //> public String methodZ()
    methodZ:function(){
        var v_name=vjo.getType('org.ebayopensource.dsf.tests.js.rt.clz.tc2.NestedStruc.Inner').clazz.getName();
        var v_simpleName=vjo.getType('org.ebayopensource.dsf.tests.js.rt.clz.tc2.NestedStruc.Inner').clazz.getSimpleName();
        var v_toString=vjo.getType('org.ebayopensource.dsf.tests.js.rt.clz.tc2.NestedStruc.Inner').clazz.toString();
        var v_isInterface=vjo.getType('org.ebayopensource.dsf.tests.js.rt.clz.tc2.NestedStruc.Inner').clazz.isInterface();
        var v_packageName=vjo.getType('org.ebayopensource.dsf.tests.js.rt.clz.tc2.NestedStruc.Inner').clazz.getPackageName();
          
        return v_name+":"+v_simpleName+":"+v_toString+":"+v_isInterface+":"+v_packageName;
    },
    //> public String methodX()
    methodX:function(){
        var v_name=vjo.getType('org.ebayopensource.dsf.tests.js.rt.clz.tc2.NestedStruc.Inner.Inner_1').clazz.getName();
        var v_simpleName=vjo.getType('org.ebayopensource.dsf.tests.js.rt.clz.tc2.NestedStruc.Inner.Inner_1').clazz.getSimpleName();
        var v_toString=vjo.getType('org.ebayopensource.dsf.tests.js.rt.clz.tc2.NestedStruc.Inner.Inner_1').clazz.toString();
        var v_isInterface=vjo.getType('org.ebayopensource.dsf.tests.js.rt.clz.tc2.NestedStruc.Inner.Inner_1').clazz.isInterface();
        var v_packageName=vjo.getType('org.ebayopensource.dsf.tests.js.rt.clz.tc2.NestedStruc.Inner.Inner_1').clazz.getPackageName();
        return v_name+":"+v_simpleName+":"+v_toString+":"+v_isInterface+":"+v_packageName;
    }
})
.endType();
//vjo.ctype("org.ebayopensource.dsf.tests.js.rt.clz.tc2.NestedSibling") //<
//.endType();