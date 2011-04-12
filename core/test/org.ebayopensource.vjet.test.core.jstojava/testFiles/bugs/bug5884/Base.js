vjo.ctype('bugs.bug5884.Base') //< public
.props({
        staticProp1: 10,//<int
        staticProp2 : "test",//<String

    sampleJsMethod : function(){ //< public String sampleJsMethod()
        return this.staticProp2;
    }
})
.protos({
        instanceProp1 : 10,//<int
        instanceProp2 : "Test",//<String

        over : function(){ //< public String over()
                return "";
        }

})
.endType();
