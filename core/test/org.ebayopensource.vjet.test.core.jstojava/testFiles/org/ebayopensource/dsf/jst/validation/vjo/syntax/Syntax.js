vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.syntax.Syntax') //< public
.needs("org.ebayopensource.dsf.jst.validation.vjo.syntax.INoExist")
.needs("org.ebayopensource.dsf.jst.validation.vjo.syntax.CNoExist")
.needs("org.ebayopensource.dsf.jst.validation.vjo.syntax.MNoExist")
.satisfies("org.ebayopensource.dsf.jst.validation.vjo.syntax.INoExist")
.inherits("org.ebayopensource.dsf.jst.validation.vjo.syntax.CNoExist")
.mixin("org.ebayopensource.dsf.jst.validation.vjo.syntax.MNoExist")
.protos({
    //> Number a()
    a:function(){
        return 10;
    },
    
    //>C b()
	b:function(){
		return null;
	}
})
.props({
	p : "constant"
})
.endType();