vjo.ctype('syntax.declare.innerType.Anonymous1')
.props({
})
.protos({
	//>public void foo() 
	foo : function(){
		 var anon = vjo.make( this.vj$.Anonymous1, 'Anonymous Type Property') // vjo.make()
		 .protos({
		 getAnonTypeProp : function () {
		 },
		 getSourceTypeProp : function () {
		 }
		 })
		 .endType();
		 anon.getAnonTypeProp();
		 anon.getSourceTypeProp();
	}
})
.endType();