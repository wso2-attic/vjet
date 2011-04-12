vjo.etype("syntax.declare.etype.ETypeSeq1")
.needs("syntax.declare.itype.ITypeExample")
.satisfies("syntax.declare.itype.ITypeExample")
.values('valueOne, valueTwo')
.props({
	//> public int
	initialValue: undefined

})
.inits(
	function(){
		/* use this section for static initialization */
		this.initialValue = 100;
	}
)
.protos({
})
.endType();