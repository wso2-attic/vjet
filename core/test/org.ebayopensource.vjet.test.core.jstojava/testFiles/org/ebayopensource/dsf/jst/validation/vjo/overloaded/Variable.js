vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.overloaded.Variable') //< public
.props({
	
	//> public void main(String... arguments)
	main:function(){
	    var x=new this.vj$.Variable(); //<Variable
	    var y=new this.vj$.Variable("one"); //<Variable 
	    var z=new this.vj$.Variable("one", "two"); //<Variable
	    
	    x.doIt();
	    y.doIt(1);
	    z.doIt(1, 2);
	}

})
.protos({
	name: "", //<private String
		
	//> public constructs(String... name)
	constructs:function(name){
	    this.name=name;
	},
	
	
	//> public String doIt(int... command)
	doIt: function(command){
		return "command";
	}
})
.endType();

