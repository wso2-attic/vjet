vjo.ctype('syntax.generic.superType.SubType23<K, V>') //< public
//>needs(syntax.generic.Map)
//>needs(syntax.generic.SortedMap)
.props({
})
.protos({
	//> public <K,V> void unmodifiableMap(Map<? extends K,? extends V> m)
  unmodifiableMap:function(m){
     new this.UnmodifiableMap(m);   
  },
	
	UnmodifiableMap:vjo.ctype() //< public UnmodifiableMap<K,V>
  .props({
  })
  .protos({
      //> constructs(Map<? extends K,? extends V> m)
      constructs:function(m){
          if(m===null){
          }
      }
  })
  .endType()
}) 
.endType();