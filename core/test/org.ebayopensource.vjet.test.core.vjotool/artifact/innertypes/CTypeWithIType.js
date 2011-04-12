vjo.ctype('innertypes.CTypeWithIType') //< public
.props({
    staticOuterProp:"Test", //< String

    InnerStaticInterface:vjo.itype() //< public
    .props({
        innerISProp:null, //< public final String
        innerISProp1:null //< public String
    })
    .protos({
        //> public void innerIFunc()
        innerISFunc:vjo.NEEDS_IMPL
    })
    .inits(function(){
    	this.vj$.CTypeWithIType.InnerStaticInterface.innerISProp=this.vj$.CTypeWithIType.outerStaticFunc().big();
        this.vj$.CTypeWithIType.InnerStaticInterface.innerISProp1=this.vj$.CTypeWithIType.staticOuterProp.big();
    })
    .endType(),
    //> public String outerStaticFunc()
    outerStaticFunc:function(){
    	vjo.sysout.println(this.staticOuterProp);
    	return "";
    }
})
.protos({
    outerProp:"test", //< String
    //> public String outerFunc1()
    outerFunc1:function(){
    	vjo.sysout.println(this.outerProp);
        return "";
    },
    //> public void outerFunc2()
    outerFunc2:function(){
    	var innerCType=new this.InnerCType(); //<InnerCType
    	innerCType.innerISFunc();
    },
    
    InnerInterface:vjo.itype() //< public
    .props({
        innerIProp:null, //< public final String
        innerIProp1:null //< public String
    })
    .protos({
        //> public void innerIFunc()
        innerIFunc:vjo.NEEDS_IMPL
    })
    .inits(function(){
		this.vj$.InnerInterface.innerIProp=this.vj$.CTypeWithIType.outerStaticFunc().big();
		this.vj$.InnerInterface.innerIProp1=this.vj$.CTypeWithIType.staticOuterProp.big();
    })
    .endType(),
    
	InnerCType:vjo.ctype() //< public
	.satisfies('innertypes.CTypeWithIType.InnerInterface')
	.satisfies('innertypes.CTypeWithIType.InnerStaticInterface')
	.protos({
	    //> public void innerIFunc()
	    innerISFunc:function(){
	    	var s=this.vj$.InnerInterface.innerIProp1; //<String
            var s1=this.vj$.CTypeWithIType.InnerStaticInterface.innerISProp; //<String
	    },
	    //> public void innerIFunc1()
	    innerIFunc:function(){
	    }
	})
	.endType()
})
.endType();
