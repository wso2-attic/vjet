vjo.ctype('engine.javaone.LigerTest') //< public
.needs(['<<33>>engine.javaone.Lion',
        '<<34>>engine.javaone.Tiger'])
.protos({
    m_father:null, //< private Lion
    m_mother:null, //< private Tiger
    m_name:null, //< private String
    m_weight:0, //< private double
    m_gene:0, //< private int
    m_isMale:false, //< private boolean
    //> public constructs(Lion father,Tiger mother,String name,boolean isMale)
    constructs:function(father,mother,name,isMale){
        this.<<35>>m_father=father;
        this.m_mother=mother;
        this.m_name=name;
        this.m_isMale=isMale;
        this.m_gene=father.<<36>>getGene()|<<37>>mother.<<38>>getGene();
        this.m_weight=this.m_isMale?1.8*<<39>>father.<<40>>getWeight():1.5*mother.getWeight();
    },
    //> public Lion getFather()
    getFather:function(){
        return this.m_father;
    },
    //> public Tiger getMother()
    getMother:function(){
        return this.m_mother;
    },
    //> public String getName()
    getName:function(){
        return this.m_name;
    },
    //> public void setName(String name)
    setName:function(name){
        this.m_name=name;
    },
    //> public double getWeight()
    getWeight:function(){
        return this.m_weight;
    },
    //> public int getGene()
    getGene:function(){
        return this.m_gene;
    },
    //> public boolean isMale()
    isMale:function(){
        return this.m_isMale;
    },
    //> public String getFatherName()
    getFatherName:function(){
        return this.m_father.getName();
    },
    //> public String getMotherName()
    getMotherName:function(){
        return this.m_mother.getName();
    },
    //> public boolean areParentsMarried()
    areParentsMarried:function(){
        return <<41>>this.<<42>>m_father.<<43>>getSpouse()===<<44>>this.<<45>>m_mother;
    }
})
.endType();