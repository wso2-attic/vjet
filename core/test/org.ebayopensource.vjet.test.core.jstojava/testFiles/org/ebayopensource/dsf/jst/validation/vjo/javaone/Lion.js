vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.javaone.Lion') //< public
.inherits('org.ebayopensource.dsf.jst.validation.vjo.javaone.Cat')
.protos({
    //> public constructs(String name, double weight, boolean male)
    constructs:function(name, weight, male){
        this.base(name, weight, male);
        this.m_gene=56;
    }
})
.endType();