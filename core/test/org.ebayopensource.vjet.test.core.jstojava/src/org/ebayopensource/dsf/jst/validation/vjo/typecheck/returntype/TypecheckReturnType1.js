/**/

vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.typecheck.returntype.TypecheckReturnType1') //< public
.protos({
    //> Number a()
    a:function(){
        var x=10; //<Number
        x ="String";
    },
    
    //> public String a1()
    a1:function(){
        var x=10; //<Number
        return x;
    },
    
    //> public TypecheckReturnType1 b()
	b:function(){
		return new this.vj$.TypecheckReturnType1();
	}
})
.endType();