vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.javaone.AnimalLab') //< public
.needs(['org.ebayopensource.dsf.jst.validation.vjo.javaone.Lion','org.ebayopensource.dsf.jst.validation.vjo.javaone.Tiger',
    'org.ebayopensource.dsf.jst.validation.vjo.javaone.Liger'])
.props({
    //> public void main(String[] args)
    main:function(args){
        var leon=new this.vj$.Lion("Leon",400,true);
        var elita=new this.vj$.Tiger("Elita",300,false);
        elita.marryTo(leon);
        var jushin=new this.vj$.Liger(leon,elita,"Jushin",true);
        vjo.sysout.println(jushin.areParentsMarried());
        vjo.sysout.println(jushin.getFatherName());
        vjo.sysout.println(jushin.getMotherName());
        vjo.sysout.println(jushin.getWeight());
    }
})
.endType();