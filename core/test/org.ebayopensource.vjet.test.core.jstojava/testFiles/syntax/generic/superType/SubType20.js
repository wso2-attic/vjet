vjo.ctype('syntax.generic.superType.SubType20<T>') //< public
//>needs(syntax.generic.Collection)
.props({
})
.protos({
	//>public void test() 
	test : function(){
		var s = null;//<Collection<? extends SubType20>
		new this.UnmodifiableCollection(s);
	},
	
	UnmodifiableCollection:vjo.ctype() //< public UnmodifiableCollection<E>
    .props({
    })
    .protos({
        c:null, //< Collection<? extends E> 
        //> constructs(Collection<? extends E> )
        constructs:function(c){
            if(c===null){
            }
            this.c=c;
        }
    })
    .endType()
}) 
.endType();