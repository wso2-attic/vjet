vjo.ctype('<<7>>engine.javaone.CatTest') //< public
.<<8>>satisfies('<<9>>engine.javaone.IMate')
.<<10>>props({
	SPECIES<<11>>:"Cat", //<final String
	maxage:100 //<public Number
})
.<<12>>protos({
    m_name:null, //< protected String
	m_weight:0, //<protected double
	m_male:true, //<protected boolean
	m_gene:7, //<protected int
	m_spouse:null, //<private Cat
    //> public constructs(String name, double weight, boolean male)
    con<<13>>structs:function(name, weight, male){
    	<<14>>this.<<15>>m_name=<<16>>name;
        this.<<17>>m_weight=weight;
        this.m_male=<<18>>male;
    },
    //> public String getName()
    getName<<19>>:function(){
    	ret<<20>>urn <<21>>this.<<22>>m_name;
    },
    //> public double getWeight()
    getWeight:function(){
        return this.m_weight;
    },
    //> public boolean isMale()
    isMale:function(){
        return this.m_male;
    },
    //> public int getGene()
    getGene:function(){
        return this.m_gene;
    },
    //> public boolean marryTo(IMate spouse)
    marryTo:function(spouse) {
    	<<23>>this.<<24>>m_spouse = <<25>>spouse;
    	spouse.<<26>>m_spouse = <<27>>this;
    	return true;
    },
    //> public Cat getSpouse()
    getSpouse:function() {
    	return this.m_spouse;
    }
})
.endType();