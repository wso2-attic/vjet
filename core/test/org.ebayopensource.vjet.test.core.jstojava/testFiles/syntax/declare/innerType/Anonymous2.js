vjo.ctype('syntax.declare.innerType.Anonymous2')
.props({
	
	s2 : "VSF" //< public String
})
.protos({
	
	s1 : "DDSF",//<public String

	//>public void foo() 
	foo : function(){
		 var anon = vjo.make( this.vj$.Anonymous2, 'AnonymousType') // vjo.make()
		 .protos({
		 getAnonTypeProp : function () {
			 this.s1 = "VV";
		 },
		 getSourceTypeProp : function () {
		 this.vj$.Anonymous2.s2 = "SF";
		 }
		 })
		 .props({
			 //>public void anonStaticMethod() 
			 anonStaticMethod : function(){
			 }
		 })
		 .endType();
		 anon.getAnonTypeProp();
		 anon.getSourceTypeProp();
	}
})
.endType();