vjo.ctype('engine.javaone.TigerTest') //< public
.inherits('engine.javaone.Cat')
.protos({
    //> public constructs(String name, double weight, boolean male)
    constructs:function(name, weight, male){
		this.base(name, weight, male);
		this.m_gene=43;
    }
})
.endType();