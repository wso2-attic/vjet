vjo.ctype('BugJsFiles.Bug5882') //< public
.inherits('BugJsFiles.BasejsFile')
.props({
        RR : "T" //<String
})
.protos({
    prop1 : 10, //< int
    prop2 : "Test", //< String

    testFunc : function(){//<public String testFunc()
        this.base.
        return "";
    }
})
.endType();