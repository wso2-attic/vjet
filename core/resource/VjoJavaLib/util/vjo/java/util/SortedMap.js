vjo.itype('vjo.java.util.SortedMap<K,V>') //< public
.needs('vjo.java.util.Comparator','')
.inherits('vjo.java.util.Map<K,V>')
.protos({
    //> public Comparator<? super K> comparator()
    comparator:function(){
    },
    //> public SortedMap<K,V> subMap(K fromKey,K toKey)
    subMap:function(fromKey,toKey){
    },
    //> public SortedMap<K,V> headMap(K toKey)
    headMap:function(toKey){
    },
    //> public SortedMap<K,V> tailMap(K fromKey)
    tailMap:function(fromKey){
    },
    //> public K firstKey()
    firstKey:function(){
    },
    //> public K lastKey()
    lastKey:function(){
    }
})
.endType();