vjo.ctype("astjst.NeedsTestTest")
.needs("astjst.TestTest")
.protos({
    //> public void testMethod()
    testMethod:function(){       
        this.vj$.TestTest.func1();
    }
})
.endType();
