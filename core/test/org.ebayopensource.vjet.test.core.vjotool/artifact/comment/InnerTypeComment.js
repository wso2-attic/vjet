vjo.ctype('comment.InnerTypeComment') //< public
//>needs(comment.Type1)
//>needs(comment.Type2)
.props({
	//>private final InnerTypeComment.StaticInnerType1
	StaticInnerType1:vjo.ctype()
	.props({
		staticCount: 1.1 //<final double
	})
	.protos({
		//>public final Type1 hi(final Type2 type2)
		hi : function(type2){
			var t = type2;//<<Type1
			return t;
		},
		//>Type::Object run()
		run : function(){
			return Object;
		}
	}).endType(),
	//>InnerTypeComment.StaticInnertType2
	StaticInnerType2:vjo.ctype()
	.props({
		
	})
	.protos({
		
	})
	.endType()
})
.protos({
	//>protected final InnerTypeComment.InnerType
	InnerType:vjo.ctype()
	.protos({
		count: 12, //<long
		//>protected void foo(final Type::Type1 t1)
		foo : function(t1){

		}
	}).endType()
})
.endType();