vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.ex.ValidationEx5') //< public abstract
.protos({
    age:0, //< private int
    m_mother:undefined, //< private String
    grow:null,
    //> public constructs(String mother)
    constructs:function(mother){
		this.grow = "sth";    
        this.m_mother=mother;
        this.age = 10;
    }
})
.endType();