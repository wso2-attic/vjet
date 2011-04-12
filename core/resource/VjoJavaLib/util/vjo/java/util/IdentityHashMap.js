vjo.ctype('vjo.java.util.IdentityHashMap<K,V>') //< public
.needs(['vjo.java.lang.IllegalArgumentException','vjo.java.lang.System',
    'vjo.java.lang.IllegalStateException','vjo.java.util.Map','vjo.java.util.NoSuchElementException',
    'vjo.java.util.AbstractSet','vjo.java.lang.Util','vjo.java.lang.ObjectUtil'])
.needs('vjo.java.util.Iterator','')
.needs('vjo.java.util.ConcurrentModificationException','')
.needs('vjo.java.util.Set','')
.needs('vjo.java.util.Collection','')
.needs('vjo.java.util.AbstractCollection','')
.needs('vjo.java.util.List','')
.needs('vjo.java.util.ArrayList','')
.inherits('vjo.java.util.AbstractMap<K,V>')
.satisfies('vjo.java.util.Map<K,V>')
.satisfies('vjo.java.io.Serializable')
.satisfies('vjo.java.lang.Cloneable')
.props({
    DEFAULT_CAPACITY:32, //< private final int
    MINIMUM_CAPACITY:4, //< private final int
    MAXIMUM_CAPACITY:0, //< private final int
    NULL_KEY:null, //< private final Object
    serialVersionUID:8188218128353913216, //< private final long
    IdentityHashMapIterator:vjo.ctype() //< public abstract IdentityHashMapIterator<K,V,T>
    .satisfies('vjo.java.util.Iterator<T>')
    .protos({
        index:0, //< int
        expectedModCount:0, //< int
        lastReturnedIndex:-1, //< int
        indexValid:false, //< boolean
        traversalTable:null, //< Object[]
        map:null, //< IdentityHashMap<K,V> map
        //> public constructs(IdentityHashMap<K,V> map)
        constructs:function(map){
            this.index=(map.size_!==0?0:map.table.length);
            this.expectedModCount=map.modCount;
            this.traversalTable=map.table;
            this.map=map;
        },
        //> public boolean hasNext()
        hasNext:function(){
            var tab=this.traversalTable;
            for (var i=this.index;i<tab.length;i+=2){
                var key=tab[i];
                if(key!==null){
                    this.index=i;
                    return this.indexValid=true;
                }
            }
            this.index=tab.length;
            return false;
        },
        //> protected int nextIndex()
        nextIndex:function(){
            if(this.map.modCount!==this.expectedModCount){
                throw new vjo.java.util.ConcurrentModificationException();
            }
            if(!this.indexValid&& !this.hasNext()){
                throw new vjo.java.util.NoSuchElementException();
            }
            this.indexValid=false;
            this.lastReturnedIndex=this.index;
            this.index+=2;
            return this.lastReturnedIndex;
        },
        //> public void remove()
        remove:function(){
            if(this.lastReturnedIndex=== -1){
                throw new vjo.java.lang.IllegalStateException();
            }
            if(this.map.modCount!==this.expectedModCount){
                throw new vjo.java.util.ConcurrentModificationException();
            }
            this.expectedModCount=++this.map.modCount;
            var deletedSlot=this.lastReturnedIndex;
            this.lastReturnedIndex=-1;
            this.map.size_--;
            this.index=deletedSlot;
            this.indexValid=false;
            var tab=this.traversalTable;
            var len=tab.length;
            var d=deletedSlot;
            var key=tab[d];
            tab[d]=null;
            tab[d+1]=null;
            if(tab!==this.map.table){
                this.map.remove(key);
                this.expectedModCount=this.map.modCount;
                return;
            }
            var item;
            for (var i=this.vj$.IdentityHashMap.nextKeyIndex(d,len);(item=tab[i])!==null;i=this.vj$.IdentityHashMap.nextKeyIndex(i,len)){
                var r=this.vj$.IdentityHashMap.hash(item,len);
                if((i<r&&(r<=d||d<=i))||(r<=d&&d<=i)){
                    if(i<deletedSlot&&d>=deletedSlot&&this.traversalTable===this.map.table){
                        var remaining=len-deletedSlot;
                        var newTable=vjo.createArray(null, remaining);
                        this.vj$.System.arraycopy(tab,deletedSlot,newTable,0,remaining);
                        this.traversalTable=newTable;
                        this.index=0;
                    }
                    tab[d]=item;
                    tab[d+1]=tab[i+1];
                    tab[i]=null;
                    tab[i+1]=null;
                    d=i;
                }
            }
        }
    })
    .endType(),
    KeyIterator:vjo.ctype() //< private KeyIterator<K,V>
    .inherits('vjo.java.util.IdentityHashMap.IdentityHashMapIterator<K,V,K>')
    .protos({
        //> public constructs(IdentityHashMap<K,V> map)
        constructs:function(map){
            this.base(map);
        },
        //> public K next()
        next:function(){
            return this.vj$.IdentityHashMap.unmaskNull(this.traversalTable[this.nextIndex()]);
        }
    })
    .endType(),
    ValueIterator:vjo.ctype() //< private ValueIterator<K,V>
    .inherits('vjo.java.util.IdentityHashMap.IdentityHashMapIterator<K,V,V>')
    .protos({
        //> public constructs(IdentityHashMap<K,V> map)
        constructs:function(map){
            this.base(map);
        },
        //> public V next()
        next:function(){
            return this.traversalTable[this.nextIndex()+1];
        }
    })
    .endType(),
    EntryIterator:vjo.ctype() //< private EntryIterator<K,V>
    .inherits('vjo.java.util.IdentityHashMap.IdentityHashMapIterator<K,V,Map>')
    .satisfies('vjo.java.util.Map<K,V>')
    .protos({
        //> public constructs(IdentityHashMap<K,V> map)
        constructs:function(map){
            this.base(map);
        },
        //> public Map<K,V> next()
        next:function(){
            this.nextIndex();
            return this;
        },
        //> public K getKey()
        getKey:function(){
            if(this.lastReturnedIndex<0){
                throw new this.vj$.IllegalStateException("Entry was removed");
            }
            return this.vj$.IdentityHashMap.unmaskNull(this.traversalTable[this.lastReturnedIndex]);
        },
        //> public V getValue()
        getValue:function(){
            if(this.lastReturnedIndex<0){
                throw new this.vj$.IllegalStateException("Entry was removed");
            }
            return this.traversalTable[this.lastReturnedIndex+1];
        },
        //> public V setValue(V value)
        setValue:function(value){
            if(this.lastReturnedIndex<0){
                throw new this.vj$.IllegalStateException("Entry was removed");
            }
            var oldValue=this.traversalTable[this.lastReturnedIndex+1];
            this.traversalTable[this.lastReturnedIndex+1]=value;
            if(this.traversalTable!==this.map.table){
                this.map.put(this.traversalTable[this.lastReturnedIndex],value);
            }
            return oldValue;
        },
        //> public boolean equals(Object o)
        equals:function(o){
            if(this.lastReturnedIndex<0){
                return vjo.java.lang.ObjectUtil.equals(this.base,o);
            }
            if(!(vjo.getType('vjo.java.util.Map').clazz.isInstance(o))){
                return false;
            }
            var e=o;
            return e.getKey()===this.getKey()&&e.getValue()===this.getValue();
        },
        //> public int hashCode()
        hashCode:function(){
            if(this.lastReturnedIndex<0){
                return vjo.java.lang.ObjectUtil.hashCode(this.base);
            }
            return this.vj$.System.identityHashCode(this.getKey())^this.vj$.System.identityHashCode(this.getValue());
        },
        //> public String toString()
        toString:function(){
            if(this.lastReturnedIndex<0){
                return this.base.toString();
            }
            return this.getKey()+"="+this.getValue();
        }
    })
    .endType(),
    KeySet:vjo.ctype() //< private KeySet<K,V>
    .inherits('vjo.java.util.AbstractSet<K>')
    .protos({
        map:null, //< IdentityHashMap<K,V> map
        //> public constructs(IdentityHashMap<K,V> map)
        constructs:function(map){
            this.base();
            this.map=map;
        },
        //> public Iterator<K> iterator()
        iterator:function(){
            return new this.vj$.IdentityHashMap.KeyIterator(this.map);
        },
        //> public int size()
        size:function(){
            return this.map.size_;
        },
        //> public boolean removeAll(Collection<?> c)
        removeAll:function(c){
            var modified=false;
            for (var i=this.iterator();i.hasNext();){
                if(c.contains(i.next())){
                    i.remove();
                    modified=true;
                }
            }
            return modified;
        },
        //> public void clear()
        clear:function(){
            this.map.clear();
        },
        //> public int hashCode()
        hashCode:function(){
            var result=0;
            var itr=this.iterator();
            while(itr.hasNext()){
                var key=itr.next();
                result+=this.vj$.System.identityHashCode(key);
            }
            return result;
        },
        //> public boolean contains(Object o)
        contains:function(o){
            if(arguments.length===1){
                return this.contains_1_0_KeySet_ovld(arguments[0]);
            }else if(this.base && this.base.contains){
                return this.base.contains.apply(this,arguments);
            }
        },
        //> private boolean contains_1_0_KeySet_ovld(Object o)
        contains_1_0_KeySet_ovld:function(o){
            return this.map.containsKey(o);
        },
        //> public boolean remove(Object o)
        remove:function(o){
            if(arguments.length===1){
                return this.remove_1_0_KeySet_ovld(arguments[0]);
            }else if(this.base && this.base.remove){
                return this.base.remove.apply(this,arguments);
            }
        },
        //> private boolean remove_1_0_KeySet_ovld(Object o)
        remove_1_0_KeySet_ovld:function(o){
            var oldSize=this.map.size_;
            this.map.remove(o);
            return this.map.size_!==oldSize;
        }
    })
    .endType(),
    Values:vjo.ctype() //< private Values<K,V>
    .inherits('vjo.java.util.AbstractCollection<V>')
    .protos({
        map:null, //< IdentityHashMap<K,V> map
        //> public constructs(IdentityHashMap<K,V> map)
        constructs:function(map){
            this.base();
            this.map=map;
        },
        //> public Iterator<V> iterator()
        iterator:function(){
            return new this.vj$.IdentityHashMap.ValueIterator(this.map);
        },
        //> public int size()
        size:function(){
            return this.map.size_;
        },
        //> public void clear()
        clear:function(){
            this.map.clear();
        },
        //> public boolean contains(Object o)
        contains:function(o){
            if(arguments.length===1){
                return this.contains_1_0_Values_ovld(arguments[0]);
            }else if(this.base && this.base.contains){
                return this.base.contains.apply(this,arguments);
            }
        },
        //> private boolean contains_1_0_Values_ovld(Object o)
        contains_1_0_Values_ovld:function(o){
            return this.map.containsValue(o);
        },
        //> public boolean remove(Object o)
        remove:function(o){
            if(arguments.length===1){
                return this.remove_1_0_Values_ovld(arguments[0]);
            }else if(this.base && this.base.remove){
                return this.base.remove.apply(this,arguments);
            }
        },
        //> private boolean remove_1_0_Values_ovld(Object o)
        remove_1_0_Values_ovld:function(o){
            for (var i=this.iterator();i.hasNext();){
                if(i.next()===o){
                    i.remove();
                    return true;
                }
            }
            return false;
        }
    })
    .endType(),
    EntrySet:vjo.ctype() //< private EntrySet<K,V>
    .inherits('vjo.java.util.AbstractSet<Map>')
    .protos({
        map:null, //< IdentityHashMap<K,V> map
        //> public constructs(IdentityHashMap<K,V> map)
        constructs:function(map){
            this.base();
            this.map=map;
        },
        //> public Iterator<Map<K,V>> iterator()
        iterator:function(){
            return new this.vj$.IdentityHashMap.EntryIterator(this.map);
        },
        //> public int size()
        size:function(){
            return this.map.size_;
        },
        //> public void clear()
        clear:function(){
            this.map.clear();
        },
        //> public boolean removeAll(Collection<?> c)
        removeAll:function(c){
            var modified=false;
            for (var i=this.iterator();i.hasNext();){
                if(c.contains(i.next())){
                    i.remove();
                    modified=true;
                }
            }
            return modified;
        },
        //> public Object[] toArray()
        //> public <T> T[] toArray(T[] a)
        toArray:function(){
            if(arguments.length===0){
                if(arguments.length==0){
                    return this.toArray_0_0_EntrySet_ovld();
                }else if(this.base && this.base.toArray){
                    return this.base.toArray.apply(this,arguments);
                }
            }else if(arguments.length===1){
                if(arguments[0] instanceof Array){
                    return this.toArray_1_0_EntrySet_ovld(arguments[0]);
                }else if(this.base && this.base.toArray){
                    return this.base.toArray.apply(this,arguments);
                }
            }else if(this.base && this.base.toArray){
                return this.base.toArray.apply(this,arguments);
            }
        },
        //> private Object[] toArray_0_0_EntrySet_ovld()
        toArray_0_0_EntrySet_ovld:function(){
            var c=new vjo.java.util.ArrayList(this.size());
            var itr=this.iterator();
            while(itr.hasNext()){
                var e=itr.next();
                c.add(new this.vj$.AbstractMap.SimpleEntry(e));
            }
            return c.toArray();
        },
        //> private <T> T[] toArray_1_0_EntrySet_ovld(T[] a)
        toArray_1_0_EntrySet_ovld:function(a){
            return this.toArray();
        },
        //> public boolean contains(Object o)
        contains:function(o){
            if(arguments.length===1){
                return this.contains_1_0_EntrySet_ovld(arguments[0]);
            }else if(this.base && this.base.contains){
                return this.base.contains.apply(this,arguments);
            }
        },
        //> private boolean contains_1_0_EntrySet_ovld(Object o)
        contains_1_0_EntrySet_ovld:function(o){
            if(!(vjo.getType('vjo.java.util.Map').clazz.isInstance(o))){
                return false;
            }
            var entry=o;
            return this.map.containsMapping(entry.getKey(),entry.getValue());
        },
        //> public boolean remove(Object o)
        remove:function(o){
            if(arguments.length===1){
                return this.remove_1_0_EntrySet_ovld(arguments[0]);
            }else if(this.base && this.base.remove){
                return this.base.remove.apply(this,arguments);
            }
        },
        //> private boolean remove_1_0_EntrySet_ovld(Object o)
        remove_1_0_EntrySet_ovld:function(o){
            if(!(vjo.getType('vjo.java.util.Map').clazz.isInstance(o))){
                return false;
            }
            var entry=o;
            return this.map.removeMapping(entry.getKey(),entry.getValue());
        }
    })
    .endType(),
    //> private Object maskNull(Object key)
    maskNull:function(key){
        return (key===null?this.NULL_KEY:key);
    },
    //> private Object unmaskNull(Object key)
    unmaskNull:function(key){
        return (key===this.NULL_KEY?null:key);
    },
    //> private int hash(Object x,int length)
    hash:function(x,length){
        var h=this.vj$.System.identityHashCode(x);
        return ((h<<1)-(h<<8))&(length-1);
    },
    //> private int nextKeyIndex(int i,int len)
    nextKeyIndex:function(i,len){
        return (i+2<len?i+2:0);
    }
})
.protos({
    table:null, //< private com.ebay.dsf.jsnative.global.Object[]
    size_:0, //< private int
    modCount:0, //< private int
    threshold:0, //< private int
    entrySet_:null, //< private Set<Map<K,V>> entrySet_
    //> public constructs()
    //> public constructs(int expectedMaxSize)
    //> public constructs(Map<? extends K,? extends V> m)
    constructs:function(){
        if(arguments.length===0){
            this.constructs_0_0_IdentityHashMap_ovld();
        }else if(arguments.length===1){
            if(typeof arguments[0]=="number"){
                this.constructs_1_0_IdentityHashMap_ovld(arguments[0]);
            }else if(vjo.java.util.Map.clazz.isInstance(arguments[0])){
                this.constructs_1_1_IdentityHashMap_ovld(arguments[0]);
            }
        }
    },
    //> private constructs_0_0_IdentityHashMap_ovld()
    constructs_0_0_IdentityHashMap_ovld:function(){
        this.base();
        this.init(this.vj$.IdentityHashMap.DEFAULT_CAPACITY);
    },
    //> private constructs_1_0_IdentityHashMap_ovld(int expectedMaxSize)
    constructs_1_0_IdentityHashMap_ovld:function(expectedMaxSize){
        this.base();
        if(expectedMaxSize<0){
            throw new this.vj$.IllegalArgumentException("expectedMaxSize is negative: "+expectedMaxSize);
        }
        this.init(this.capacity(expectedMaxSize));
    },
    //> private int capacity(int expectedMaxSize)
    capacity:function(expectedMaxSize){
        var minCapacity=parseInt(""+((3*expectedMaxSize)/2));
        var result;
        if(minCapacity>this.vj$.IdentityHashMap.MAXIMUM_CAPACITY||minCapacity<0){
            result=this.vj$.IdentityHashMap.MAXIMUM_CAPACITY;
        }else {
            result=this.vj$.IdentityHashMap.MINIMUM_CAPACITY;
            while(result<minCapacity){
                result<<=1;
            }
        }
        return result;
    },
    //> private void init(int initCapacity)
    init:function(initCapacity){
        this.threshold=parseInt(""+((initCapacity*2)/3));
        this.table=vjo.createArray(null, 2*initCapacity);
    },
    //> private constructs_1_1_IdentityHashMap_ovld(Map<? extends K,? extends V> m)
    constructs_1_1_IdentityHashMap_ovld:function(m){
        this.constructs(this.vj$.Util.cast(((1+m.size())*1.1),'int'));
        this.putAll(m);
    },
    //> public int size()
    size:function(){
        return this.size_;
    },
    //> public boolean isEmpty()
    isEmpty:function(){
        return this.size_===0;
    },
    //> private boolean containsMapping(Object key,Object value)
    containsMapping:function(key,value){
        var k=this.vj$.IdentityHashMap.maskNull(key);
        var tab=this.table;
        var len=tab.length;
        var i=this.vj$.IdentityHashMap.hash(k,len);
        while(true){
            var item=tab[i];
            if(item===k){
                return tab[i+1]===value;
            }
            if(item===null){
                return false;
            }
            i=this.vj$.IdentityHashMap.nextKeyIndex(i,len);
        }
    },
    //> public V put(K key,V value)
    put:function(key,value){
        var k=this.vj$.IdentityHashMap.maskNull(key);
        var tab=this.table;
        var len=tab.length;
        var i=this.vj$.IdentityHashMap.hash(k,len);
        var item;
        while((item=tab[i])!==null){
            if(item===k){
                var oldValue=tab[i+1];
                tab[i+1]=value;
                return oldValue;
            }
            i=this.vj$.IdentityHashMap.nextKeyIndex(i,len);
        }
        this.modCount++;
        tab[i]=k;
        tab[i+1]=value;
        if(++this.size_>=this.threshold){
            this.resize(len);
        }
        return null;
    },
    //> private void resize(int newCapacity)
    resize:function(newCapacity){
        var newLength=newCapacity*2;
        var oldTable=this.table;
        var oldLength=oldTable.length;
        if(oldLength===2*this.vj$.IdentityHashMap.MAXIMUM_CAPACITY){
            if(this.threshold===this.vj$.IdentityHashMap.MAXIMUM_CAPACITY-1){
                throw new this.vj$.IllegalStateException("Capacity exhausted.");
            }
            this.threshold=this.vj$.IdentityHashMap.MAXIMUM_CAPACITY-1;
            return;
        }
        if(oldLength>=newLength){
            return;
        }
        var newTable=vjo.createArray(null, newLength);
        this.threshold=parseInt(""+newLength/3);
        for (var j=0;j<oldLength;j+=2){
            var key=oldTable[j];
            if(key!==null){
                var value=oldTable[j+1];
                oldTable[j]=null;
                oldTable[j+1]=null;
                var i=this.vj$.IdentityHashMap.hash(key,newLength);
                while(newTable[i]!==null){
                    i=this.vj$.IdentityHashMap.nextKeyIndex(i,newLength);
                }
                newTable[i]=key;
                newTable[i+1]=value;
            }
        }
        this.table=newTable;
    },
    //> public void putAll(Map<? extends K,? extends V> t)
    putAll:function(t){
        var n=t.size();
        if(n===0){
            return;
        }
        if(n>this.threshold){
            this.resize(this.capacity(n));
        }
        var itr=t.entrySet().iterator();
        while(itr.hasNext()){
            var e=itr.next();
            this.put(e.getKey(),e.getValue());
        }
    },
    //> private boolean removeMapping(Object key,Object value)
    removeMapping:function(key,value){
        var k=this.vj$.IdentityHashMap.maskNull(key);
        var tab=this.table;
        var len=tab.length;
        var i=this.vj$.IdentityHashMap.hash(k,len);
        while(true){
            var item=tab[i];
            if(item===k){
                if(tab[i+1]!==value){
                    return false;
                }
                this.modCount++;
                this.size_--;
                tab[i]=null;
                tab[i+1]=null;
                this.closeDeletion(i);
                return true;
            }
            if(item===null){
                return false;
            }
            i=this.vj$.IdentityHashMap.nextKeyIndex(i,len);
        }
    },
    //> private void closeDeletion(int d)
    closeDeletion:function(d){
        var tab=this.table;
        var len=tab.length;
        var item;
        for (var i=this.vj$.IdentityHashMap.nextKeyIndex(d,len);(item=tab[i])!==null;i=this.vj$.IdentityHashMap.nextKeyIndex(i,len)){
            var r=this.vj$.IdentityHashMap.hash(item,len);
            if((i<r&&(r<=d||d<=i))||(r<=d&&d<=i)){
                tab[d]=item;
                tab[d+1]=tab[i+1];
                tab[i]=null;
                tab[i+1]=null;
                d=i;
            }
        }
    },
    //> public void clear()
    clear:function(){
        this.modCount++;
        var tab=this.table;
        for (var i=0;i<tab.length;i++){
            tab[i]=null;
        }
        this.size_=0;
    },
    //> public int hashCode()
    hashCode:function(){
        var result=0;
        var tab=this.table;
        for (var i=0;i<tab.length;i+=2){
            var key=tab[i];
            if(key!==null){
                var k=this.vj$.IdentityHashMap.unmaskNull(key);
                result+=this.vj$.System.identityHashCode(k)^this.vj$.System.identityHashCode(tab[i+1]);
            }
        }
        return result;
    },
    //> public Set<K> keySet()
    keySet:function(){
        var ks=this.keySet_;
        if(ks!==null){
            return ks;
        }else {
            return this.keySet_=new this.vj$.IdentityHashMap.KeySet(this);
        }
    },
    //> public Collection<V> values()
    values:function(){
        var vs=this.values_;
        if(vs!==null){
            return vs;
        }else {
            return this.values_=new this.vj$.IdentityHashMap.Values(this);
        }
    },
    //> public Set<Map<K,V>> entrySet()
    entrySet:function(){
        var es=this.entrySet_;
        if(es!==null){
            return es;
        }else {
            return this.entrySet_=new this.vj$.IdentityHashMap.EntrySet(this);
        }
    },
    //> public V get(Object key)
    get:function(key){
        if(arguments.length===1){
            return this.get_1_0_IdentityHashMap_ovld(arguments[0]);
        }else if(this.base && this.base.get){
            return this.base.get.apply(this,arguments);
        }
    },
    //> private V get_1_0_IdentityHashMap_ovld(Object key)
    get_1_0_IdentityHashMap_ovld:function(key){
        var k=this.vj$.IdentityHashMap.maskNull(key);
        var tab=this.table;
        var len=tab.length;
        var i=this.vj$.IdentityHashMap.hash(k,len);
        while(true){
            var item=tab[i];
            if(item===k){
                return tab[i+1];
            }
            if(item===null){
                return null;
            }
            i=this.vj$.IdentityHashMap.nextKeyIndex(i,len);
        }
    },
    //> public boolean containsKey(Object key)
    containsKey:function(key){
        if(arguments.length===1){
            return this.containsKey_1_0_IdentityHashMap_ovld(arguments[0]);
        }else if(this.base && this.base.containsKey){
            return this.base.containsKey.apply(this,arguments);
        }
    },
    //> private boolean containsKey_1_0_IdentityHashMap_ovld(Object key)
    containsKey_1_0_IdentityHashMap_ovld:function(key){
        var k=this.vj$.IdentityHashMap.maskNull(key);
        var tab=this.table;
        var len=tab.length;
        var i=this.vj$.IdentityHashMap.hash(k,len);
        while(true){
            var item=tab[i];            
            if(item===k){
                return true;
            }
            if(item===null){
                return false;
            }
            i=this.vj$.IdentityHashMap.nextKeyIndex(i,len);
        }
    },
    //> public boolean containsValue(Object value)
    containsValue:function(value){
        if(arguments.length===1){
            return this.containsValue_1_0_IdentityHashMap_ovld(arguments[0]);
        }else if(this.base && this.base.containsValue){
            return this.base.containsValue.apply(this,arguments);
        }
    },
    //> private boolean containsValue_1_0_IdentityHashMap_ovld(Object value)
    containsValue_1_0_IdentityHashMap_ovld:function(value){
        var tab=this.table;
        for (var i=1;i<tab.length;i+=2){
            if(tab[i]===value){
                return true;
            }
        }
        return false;
    },
    //> public V remove(Object key)
    remove:function(key){
        if(arguments.length===1){
            return this.remove_1_0_IdentityHashMap_ovld(arguments[0]);
        }else if(this.base && this.base.remove){
            return this.base.remove.apply(this,arguments);
        }
    },
    //> private V remove_1_0_IdentityHashMap_ovld(Object key)
    remove_1_0_IdentityHashMap_ovld:function(key){
        var k=this.vj$.IdentityHashMap.maskNull(key);
        var tab=this.table;
        var len=tab.length;
        var i=this.vj$.IdentityHashMap.hash(k,len);
        while(true){
            var item=tab[i];
            if(item===k){
                this.modCount++;
                this.size_--;
                var oldValue=tab[i+1];
                tab[i+1]=null;
                tab[i]=null;
                this.closeDeletion(i);
                return oldValue;
            }
            if(item===null){
                return null;
            }
            i=this.vj$.IdentityHashMap.nextKeyIndex(i,len);
        }
    },
    //> public boolean equals(Object o)
    equals:function(o){
        if(arguments.length===1){
            return this.equals_1_0_IdentityHashMap_ovld(arguments[0]);
        }else if(this.base && this.base.equals){
            return this.base.equals.apply(this,arguments);
        }
    },
    //> private boolean equals_1_0_IdentityHashMap_ovld(Object o)
    equals_1_0_IdentityHashMap_ovld:function(o){
        if(o===this){
            return true;
        }else if(vjo.java.util.IdentityHashMap.clazz.isInstance(o)){
            var m=o;
            if(m.size()!==this.size_){
                return false;
            }
            var tab=m.table;
            for (var i=0;i<tab.length;i+=2){
                var k=tab[i];
                if(k!==null&& !this.containsMapping(k,tab[i+1])){
                    return false;
                }
            }
            return true;
        }else if(vjo.getType('vjo.java.util.Map').clazz.isInstance(o)){
            var m=o;
            return vjo.java.lang.ObjectUtil.equals(this.entrySet(),m.entrySet());
        }else {
            return false;
        }
    }
})
.inits(function(){
    this.vj$.IdentityHashMap.MAXIMUM_CAPACITY=1<<29;
    this.vj$.IdentityHashMap.NULL_KEY=new Object();
})
.endType();