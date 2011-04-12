vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.typecheck.TypeCheck2') //< public
.protos({

    sth:0,//<Number
    //> Number a()
    a:function(){
        var x=10;//<Number
        x="String";
        
        var c;//<String
        c=this.b();//<<String
        
        this.sth = 100;//should be acceptable
        this.sth++;
        
        var str = this.sth + "this is going to be a string";
        str += "append";
        str = "cleaned up";
        
        var localType = vjo.ctype("localType").props({}).endType();//<<
        var localTypeObj = new localType();
        
        var objLiteral = {name:'value'};
       	str = objLiteral.toString();
        
    },
    
    //> TypeCheck2 b()
	b:function(){
		return new this.vj$.TypeCheck2();
	},
	
	//> TypeCheck2 m()
	m: function(){
		return null;
	}
})
.endType();