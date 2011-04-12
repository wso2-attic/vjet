vjo.ctype('bugs.bug5884.BaseClient') //< public
.inherits('bugs.bug5884.Base')
.props({
        RR : "T" //<String
})
.protos({
    prop1 : 10, //< int
    prop2 : "Test", //< String

    testFunc : function(){//<public String testFunc()
        return "";
    },

    //>private String over()
    over : function(){
        var d = new Date();//<Date
        var day = d.getDay();//<Number
        return this.base.over();
    }
})
.endType();
