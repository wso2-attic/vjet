vjo.ctype('syntax.generic.superType.SubType21<T>') //< public
//>needs(syntax.generic.Set)
.props({
})
.protos({
	//>public void test(Set<? extends T>) 
	test : function(v){
		var s = null;//<Set<? extends T>
		new this.UnmodifiableCollection(v);
	},
	
	UnmodifiableCollection:vjo.ctype() //< public UnmodifiableCollection<E>
    .props({
    })
    .protos({
        c:null, //< Set<? extends E> 
        //> constructs(Set<? extends E> )
        constructs:function(c){
            if(c===null){
            }
            this.c=c;
        }
    })
    .endType()
}) 
.endType();