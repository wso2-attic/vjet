vjo.itype('comment.ITypeComment') //<pub<<1>>lic
.props({
	typeClass: 'ITypeComment'//<pub<<2>>lic fi<<4>>nal Str<<5>>ing
})
.protos({
	//>pub<<6>>lic v<<7>>oid comment(Str<<8>>ing... args)
	comment: vjo.NEEDS_IMPL,
	desc: vjo.NEEDS_IMPL
})
.endType();