vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.ex.ValidationEx3') //< public
.needs('org.ebayopensource.dsf.jst.validation.vjo.ex.VallidationEx4')
.props({
 
	//> public void main(String... arguments)
	main: function() {
 		var z = new this.vj$.VallidationEx4("Hello"); //<VallidationEx4

		var x = new this.vj$.VallidationEx4();//<VallidationEx4

		var y = new this.vj$.VallidationEx4();//<VallidationEx4

	} 
})
.protos({
	name:"",//<String

	//>public constructs(String name)
	constructs:function(name){
		this.name = name;
	}
})
.endType();