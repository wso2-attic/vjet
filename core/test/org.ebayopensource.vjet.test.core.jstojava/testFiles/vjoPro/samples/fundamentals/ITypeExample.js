vjo.itype('vjoPro.samples.fundamentals.ITypeExample')
//snippet.protos.end
.props({

initialValue: undefined //<public int

//functions not allowed in props cascade for VjO itype
})
//snippet.props.end
//snippet.protos.begin
//snippet.staticinit.end
.protos ({
//properties not allowed in protos cascade for VjO itype

//> void foo()
foo : vjo.NEEDS_IMPL,

//> public boolean bar(int x)
bar : vjo.NEEDS_IMPL
})
//snippet.protos.end
//snippet.staticinit.begin
.inits(
function(){
/* use this section for static initialization */
this.initialValue = 100;
}
)
//snippet.protos.begin
.endType();
//snippet.staticinit.end
//snippet.protos.end
//snippet.itype.end
