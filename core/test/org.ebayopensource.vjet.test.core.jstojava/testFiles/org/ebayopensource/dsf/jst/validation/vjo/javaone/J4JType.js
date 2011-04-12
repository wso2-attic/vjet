vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.javaone.J4JType') //< public
.protos({
    m_data:null, //< private final ObjLiteral
    //> public constructs(ObjLiteral data)
    constructs:function(data){
        this.m_data=data;
        data["k2"]="VJET";
    },
    //> public Array modify(Array arr)
    modify:function(arr){
        vjo.sysout.println(arr.toString());
        arr.reverse();
        arr.pop();
        arr.push(this.m_data["k1"]);
        arr.push(this.m_data["k2"]);
        arr.concat([1,2,3]);
        vjo.sysout.println(arr.toString());
        return arr;
    }
})
.endType();