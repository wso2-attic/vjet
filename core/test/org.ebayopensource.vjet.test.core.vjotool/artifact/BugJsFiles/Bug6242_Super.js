vjo.ctype('BugJsFiles.Bug6242_Super') //< public
.props({
    staticProp1: 10, //< public int
    staticProp2: 10, //< protected int
    staticProp3: 10, //< private int
    staticProp4: 10, //< int

    sampleJsMethod : function(){ //< public int sampleJsMethod()
        return this.staticProp2;
    }
})
.endType();

