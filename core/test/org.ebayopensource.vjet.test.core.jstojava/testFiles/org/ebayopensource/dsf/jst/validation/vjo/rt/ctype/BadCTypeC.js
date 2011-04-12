vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.rt.ctype.BadCTypeC")
.props({
	//>public BadCTypeB bar()        
    bar : function(){
            var b; //<BadCTypeB
            b.foo();
            return b;
    }
})
.endType();