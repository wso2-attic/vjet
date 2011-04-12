vjo.ctype('vjoPro.samples.anon.AnonExample1')
.protos({
	value:'-', //< public String
	
	//> public void constructs(String val)
	constructs:function(val){
	
		this.value = val;
		document.writeln('AnonExample1 constructs called');
	},

	//> public String getVal()
	getVal:function(){
	
		return this.value;
	},


	makeAnon : function (){
		//snippet.anon.begin
		var anon = vjo.make(this,this.vj$.AnonExample1,'Anonymous Inner')
		//snippet.anon.end
		.protos({
			getAnonVal : function () {
				document.writeln(this.getVal());
			},
			getOuterVal : function () {
				document.writeln(this.vj$.parent.getVal());
			}
		})
		.endType();//<
	
		anon.getAnonVal();
		anon.getOuterVal();
	}
})
.props({
	//> public void main(String[] args)
	main:function(args) {
		var anonEx = new this.vj$.AnonExample1('AnonExample1 Outer');//<AnonExample1
		anonEx.makeAnon();
	}

})
.endType();
