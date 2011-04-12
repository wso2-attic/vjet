vjo.ctype('comment.CommentType') //< public
.needs('comment.Type1')
//<needs(comment.Type2)
.props({
	m_prop : "",//<public String 
	
	//>public void main(String... args)
	main : function(args){
		var v = new this.vj$.Type1();//<Type1;
		var v1 = null;//<type::Type2
	}
})
.protos({
	
})
.endType();