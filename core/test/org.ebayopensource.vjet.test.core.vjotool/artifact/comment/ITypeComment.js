vjo.itype('comment.ITypeComment') //<public
.props({
	typeClass: 'ITypeComment'//<public final String
})
.protos({
	//>public void comment(String... args)
	comment: vjo.NEEDS_IMPL,
	desc: vjo.NEEDS_IMPL
})
.endType();