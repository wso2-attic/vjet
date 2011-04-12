vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug5466")
.props({
        staticProp1 : "Test",//< private String

        //>public void staticFunc1(String s1, String s2) 
        staticFunc1 : function(s1, s2){
                this.staticProp1 = s1;
                this.vj$.Bug5466.staticProp1 = s2;
                this.instanceProp1 = 20;
                this.vj$.Bug5466.instanceProp1 = 20;
        }
})
.protos({
        instanceProp1 : 10 //<public  int
})
.endType();
