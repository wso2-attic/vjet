vjo.ctype('engine.javaone.AnimalLab') //< public
.needs(['engine.javaone.Lion',
        'engine.javaone.Tiger',
        'engine.javaone.Liger'])
.props({
    //> public void main(String[] args)
    main:function(args){
        var leon=new this.vj$.Lion("Leon",400,true);//< Lion
        var elita=new this.vj$.Tiger("Elita",300,false);//< Tiger
        elita.marryTo(leon);
        var jushin=new this.vj$.Liger(leon,elita,"Jushin",true);
    }
})
.endType();