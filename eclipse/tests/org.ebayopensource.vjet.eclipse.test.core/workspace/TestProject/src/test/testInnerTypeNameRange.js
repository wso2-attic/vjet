vjo.ctype("test.testInnerType")
.needs("test.MyMType")
.inherits("test.MyAType")
.protos({
        innerM : vjo.ctype()
                .mixin("test.MyMType")
                .endType()
})
.props({
        innerM2 : vjo.ctype()
                .mixin("test.MyMType")
                .endType()
})
.endType();