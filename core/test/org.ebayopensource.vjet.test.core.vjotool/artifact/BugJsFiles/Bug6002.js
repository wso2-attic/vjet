vjo.ctype('BugJsFiles.Bug6002') //< public
.needs('BugJsFiles.BasejsFile', 'My')
.props({
        RR : "T" //<String
})
.protos({
    prop1 : 10, //< int
    prop2 : "Test", //< String

    testFunc : function(){//<public String testFunc()
        var v = this.vj$.BasejsFile.staticProp2.
        return "";
    },

    //>public String testFun()
    testFun : function(){
        return this.base.testFun();
    }
})
.endType();