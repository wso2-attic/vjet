vjo.ctype('BugJsFiles.Bug6123') //< public 
.props({
        validStaticProp1 : 20, //< int
        validStaticProp2 : "String", //< String

        main : function(){ //< public void main(String...)
        }
})
.protos({
        validProp1 : 30, //< int
        validProp2 : "Test", //< String

        constructs:function(){ //<public constructs()
                this.validProp2
                var date = new Date();//<Date
                date.toString();
        }
})
.
.endType();
