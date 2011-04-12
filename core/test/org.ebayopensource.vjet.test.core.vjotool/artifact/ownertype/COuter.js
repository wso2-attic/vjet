vjo.ctype('ownertype.COuter') //< public
.protos({
    CInner1:vjo.ctype() //< public
    .protos({
        //> public void foo1()
        foo1:function(){
        }
    })
    .endType(),
    CInner2:vjo.ctype() //< public
    .protos({
    	//> public void foo2()
        foo2:function(){
        	
        },
        CInnerInner2:vjo.ctype() //< public
        .protos({
            //> public void foo2()
            foo2:function(){
            }
        })
        .endType()
    })
    .endType(),
    //> public void foo()
    foo:function(){
    }
})
.endType();