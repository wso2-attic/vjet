vjo.itype('vjo.java.util.Map<K,V>') //< public
.needs('vjo.java.util.Set','')
.needs('vjo.java.util.Collection','')
.props({
    Entry:vjo.itype() //< public Entry<K,V>
    .protos({
        //> public K getKey()
        getKey:function(){
        },
        //> public V getValue()
        getValue:function(){
        },
        //> public V setValue(V value)
        setValue:function(value){
        },
        //> public boolean equals(Object o)
        equals:function(o){
        },
        //> public int hashCode()
        hashCode:function(){
        }
    })
    .endType()
})
.protos({
    //> public int size()
    size:function(){
    },
    //> public boolean isEmpty()
    isEmpty:function(){
    },
    //> public boolean containsKey(Object key)
    containsKey:function(key){
    },
    //> public boolean containsValue(Object value)
    containsValue:function(value){
    },
    //> public V get(Object key)
    get:function(key){
    },
    //> public V put(K key,V value)
    put:function(key,value){
    },
    //> public V remove(Object key)
    remove:function(key){
    },
    //> public void putAll(Map<? extends K,? extends V> t)
    putAll:function(t){
    },
    //> public void clear()
    clear:function(){
    },
    //> public Set<K> keySet()
    keySet:function(){
    },
    //> public Collection<V> values()
    values:function(){
    },
    //> public Set<Map<K,V>> entrySet()
    entrySet:function(){
    },
    //> public boolean equals(Object o)
    equals:function(o){
    },
    //> public int hashCode()
    hashCode:function(){
    }
})
.endType();