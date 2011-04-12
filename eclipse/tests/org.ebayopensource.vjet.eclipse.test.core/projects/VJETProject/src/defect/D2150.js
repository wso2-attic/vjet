vjo.ctype("defect.D2150")
.props({
        //>public void main(String ... arguments)
        main : function() {
                new this();


        }       

})
.protos({
        //>public constructs()
        constructs : function() {
                vjo.sysout.println("hello vjo constructor");
        }
})
.endType();
