vjo.ctype('BugJsFiles.Bug6006') //< public 
.props({
    validStaticProp1 : 20, //< int
    validstaticProp2 : "String", //< String
    //> public void test()
    test : function () {
        alert(this.validstaticProp2.)
    }
})
.protos({
        prop1 : 10,//<int
        prop2 : "",//<String

        //>public void testFunc() 
        testFunc : function(){
                alert(this.prop2.big());
        }

})
.endType();
