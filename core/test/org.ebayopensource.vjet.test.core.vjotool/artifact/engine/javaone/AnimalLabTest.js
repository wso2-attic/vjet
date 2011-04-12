vjo.ctype('engine.javaone.AnimalLabTest') //< public
.needs(['<<46>>engine.javaone.Lion',
        '<<47>>engine.javaone.Tiger',
        '<<48>>engine.javaone.Liger'])
.props({
    //> public void main(String[] args)
    main<<49>>:function(args){
        var leon=<<50>>new <<51>>this.<<52>>vj$.<<53>>Lion("Leon",400,true);//< Lion
        var elita=new this.vj$.Tiger("Elita",300,false);//< Tiger
        elita.<<54>>marryTo(<<55>>leon);
        var jushin=new this.vj$.Liger(leon,elita,"Jushin",true);
    }
})
.endType();