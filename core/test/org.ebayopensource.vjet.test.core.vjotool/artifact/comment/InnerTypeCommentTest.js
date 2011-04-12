vjo.ctype('comment.InnerTypeComment') //< public
//>needs(comment.Type1)
//>needs(comment.Type2)
.props({
	//><<1>>private <<3>>final <<5>>InnerTypeComment.StaticI<<6>>nnerType1
	StaticInnerType1:vjo.ctype()
	.props({
		staticCount: 1.1 //<<<22>>final <<7>>double
	})
	.protos({
		//><<8>>public <<23>>final <<9>>Type1 hi(fin<<10>>al Ty<<11>>pe2 type2)
		hi : function(type2){
			var t = type2;//<<Ty<<12>>pe1
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
	//>prote<<13>>cted fi<<14>>nal Inner<<16>>TypeComment.Inner<<17>>Type
	InnerType:vjo.ctype()
	.protos({
		count: 12, //<long
		//><<18>>protected vo<<19>>id foo(fi<<20>>nal Type::Ty<<21>>pe1 t1)
		foo : function(t1){

		}
	}).endType()
})
.endType();