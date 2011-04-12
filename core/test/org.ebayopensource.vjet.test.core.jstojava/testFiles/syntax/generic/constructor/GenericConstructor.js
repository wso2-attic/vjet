vjo.ctype('syntax.generic.constructor.GenericConstructor<E>') //< public
.props({  
	main: function(){
		//valid case
		var validGc = new this.vj$.GenericConstructor(new Date());//<GenericConstructor<Date>
		
		//invalid case
		var invalidGc = new this.vj$.GenericConstructor(new Date());//<GenericConstructor<String>
		
	}      
})
.protos({
	constructs: function(e){//<public constructs(E)
	
	}
})
.endType();