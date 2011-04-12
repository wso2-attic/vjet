vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug5783")
.props({
    d:null, //< Object
    DoIt:
vjo.ctype() //< public
    .protos({
        D:
vjo.ctype() //< public
        .inherits('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug5783')
        .protos({
            d:null, //< Object
            //> public void s()
            s:function(){
                var v2=this.s();
                var v=this.s();
            }
        })
        .endType()
    })
    .endType()
})
.protos({
    //> public void testFunc(String arg,String arg1)
    //> protected void testFunc(String arg)
    //> public void testFunc()
    testFunc:function(arg,arg1){
    }
})
.inits(function(){
    var myA=this;
})
.endType();
