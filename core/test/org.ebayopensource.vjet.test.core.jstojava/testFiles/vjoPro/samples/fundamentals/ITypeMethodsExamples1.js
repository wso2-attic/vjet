vjo.itype('vjoPro.samples.fundamentals.ITypeMethodsExamples1')
.props({
//> void cool()
cool: function() {},

//> public void hot(boolean v)
hot:	function(v) {}
})
.protos ({
//> void foo()
foo: vjo.NEEDS_IMPL,

//> public boolean bar(int x)
bar: vjo.NEEDS_IMPL
})
.endType();
