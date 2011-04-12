vjo.itype('syntax.declare.itype.ITypeExample1')
.inherits('syntax.declare.itype.ITypeExample')
.props({
	//> public int
	initialValue: undefined

})
.protos ({

})
.inits(
	function(){
		/* use this section for static initialization */
		this.initialValue = 100;
	}
)
.endType();
