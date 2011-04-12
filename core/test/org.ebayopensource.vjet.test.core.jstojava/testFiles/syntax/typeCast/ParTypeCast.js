vjo.ctype('syntax.typeCast.ParTypeCast') //< public
.needs('syntax.typeCast.DeclarationTypeCast')
.protos({
    
    //>public void foo(ParTypeCast) 
    foo : function(t1){
    	
    },
    
    //>public void foo2() 
    foo2 : function(){
    	this.foo(new this.vj$.DeclarationTypeCast()/*<<ParTypeCast*/);
    }


}).endType();
