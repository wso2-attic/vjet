vjo.ctype('syntax.generic.superType.SubType14<E>') //< public
.inherits('syntax.generic.superType.SuperType1<E>')
.props({
})
.protos({
	//>public void getName(SuperType1<E>) 
	getName : function(su){
	},
	
	//>public SuperType1<E> getCity(SuperType1<E>) 
	getCity : function(w){
		return w?new this.vj$.SuperType1():new this.vj$.SubType14();
	}
})
.endType();