vjo.ctype('vjo.java.util.HashMap<K,V>') //< public
.needs(['vjo.java.lang.RuntimeException','vjo.java.lang.IllegalArgumentException',
    'vjo.java.lang.Float','vjo.java.lang.Math',
    'vjo.java.lang.Integer','vjo.java.util.NoSuchElementException',
    'vjo.java.lang.IllegalStateException','vjo.java.util.AbstractSet',
    'vjo.java.lang.Util','vjo.java.lang.ObjectUtil'])
.needs('vjo.java.util.ConcurrentModificationException','')
.needs('vjo.java.util.Iterator','')
.needs('vjo.java.util.Set','')
.needs('vjo.java.util.Collection','')
.needs('vjo.java.util.AbstractCollection','')
.inherits('vjo.java.util.AbstractMap<K,V>')
.satisfies('vjo.java.util.Map<K,V>')
.satisfies('vjo.java.lang.Cloneable')
.satisfies('vjo.java.io.Serializable')
.props({
    DEFAULT_INITIAL_CAPACITY:16, //< final int
    MAXIMUM_CAPACITY:0, //< final int
    DEFAULT_LOAD_FACTOR:0.75, //< final float
    serialVersionUID:362498820763181265, //< private final long
    Entry:vjo.ctype() //< public Entry<K,V>
    .satisfies('vjo.java.util.Map.Entry<K,V>')
    .protos({
        key:null, //< final K
        value:null, //< V
        hash:0, //< final int
        next:null, //< Entry<K,V> next
        //> constructs(int h,K k,V v,Entry<K,V> n)
        constructs:function(h,k,v,n){
            this.value=v;
            this.next=n;
            this.key=k;
            this.hash=h;
        },
        //> public K getKey()
        getKey:function(){
            return this.key;
        },
        //> public V getValue()
        getValue:function(){
            return this.value;
        },
        //> public V setValue(V newValue)
        setValue:function(newValue){
            var oldValue=this.value;
            this.value=newValue;
            return oldValue;
        },
        //> public boolean equals(Object o)
        equals:function(o){
            if(!(vjo.java.util.Map.Entry.clazz.isInstance(o))){
                return false;
            }
            var e=o;
            var k1=this.getKey();
            var k2=e.getKey();
            if(k1===k2 || (k1!==null&&vjo.java.lang.ObjectUtil.equals(k1,k2))){
                var v1=this.getValue();
                var v2=e.getValue();
                if(v1===v2 || (v1!==null&&vjo.java.lang.ObjectUtil.equals(v1,v2))){
                    return true;
                }
            }
            return false;
        },
        //> public int hashCode()
        hashCode:function(){
            return (this.key===null?0:vjo.java.lang.ObjectUtil.hashCode(this.key))^(this.value===null?0:vjo.java.lang.ObjectUtil.hashCode(this.value));
        },
        //> public String toString()
        toString:function(){
            return this.getKey()+"="+this.getValue();
        },
        //> void recordAccess(HashMap<K,V> m)
        recordAccess:function(m){
        },
        //> void recordRemoval(HashMap<K,V> m)
        recordRemoval:function(m){
        }
    })
    .endType(),
    HashIterator:vjo.ctype() //< private abstract HashIterator<K,V,E>
    .satisfies('vjo.java.util.Iterator<E>')
    .protos({
        next_:null, //< Entry<K,V> next_
        expectedModCount:0, //< int
        index:0, //< int
        current:null, //< Entry<K,V> current
        map:null, //< HashMap
        //> constructs(HashMap map)
        constructs:function(map){
            this.map=map;
            this.expectedModCount=map.modCount;
            var t=map.table;
            var i=t.length;
            var n=null;
            if(map.size_!==0){
                while(i>0 && (n=t[--i])===null){
                }
            }
            this.next_=n;
            this.index=i;
        },
        //> public boolean hasNext()
        hasNext:function(){
            return this.next_!==null;
        },
        //> Entry<K,V> nextEntry()
        nextEntry:function(){
            if(this.map.modCount!==this.expectedModCount){
                throw new vjo.java.util.ConcurrentModificationException();
            }
            var e=this.next_;
            if(e===null){
                throw new this.vj$.NoSuchElementException();
            }
            var n=e.next;
            var t=this.map.table;
            var i=this.index;
            while(n===null && i>0){
                n=t[--i];
            }
            this.index=i;
            this.next_=n;
            return this.current=e;
        },
        //> public void remove()
        remove:function(){
            if(this.current===null){
                throw new this.vj$.IllegalStateException();
            }
            if(this.map.modCount!==this.expectedModCount){
                throw new vjo.java.util.ConcurrentModificationException();
            }
            var k=this.current.key;
            this.current=null;
            this.map.removeEntryForKey(k);
            this.expectedModCount=this.map.modCount;
        }
    })
    .endType(),
    KeySet:vjo.ctype() //< private
    .inherits('vjo.java.util.AbstractSet<K>')
    .protos({
        map:null, //< HashMap
        //> constructs(HashMap hashMap)
        constructs:function(hashMap){
            this.map=hashMap;
        },
        //> public Iterator<K> iterator()
        iterator:function(){
            return this.map.newKeyIterator();
        },
        //> public int size()
        size:function(){
            return this.map.size_;
        },
        //> public boolean contains(Object o)
        contains:function(o){
            return this.map.containsKey(o);
        },
        //> public boolean remove(Object o)
        remove:function(o){
            return this.map.removeEntryForKey(o)!==null;
        },
        //> public void clear()
        clear:function(){
            this.map.clear();
        }
    })
    .endType(),
    Values:vjo.ctype() //< private
    .inherits('vjo.java.util.AbstractCollection<V>')
    .protos({
        map:null, //< HashMap
        //> constructs(HashMap hashMap)
        constructs:function(hashMap){
            this.map=hashMap;
        },
        //> public Iterator<V> iterator()
        iterator:function(){
            return this.map.newValueIterator();
        },
        //> public int size()
        size:function(){
            return this.map.size_;
        },
        //> public boolean contains(Object o)
        contains:function(o){
            return this.map.containsValue(o);
        },
        //> public void clear()
        clear:function(){
            this.map.clear();
        }
    })
    .endType(),
    EntrySet:vjo.ctype() //< private
    .inherits('vjo.java.util.AbstractSet')
    .protos({
        map:null, //< HashMap
        //> constructs(HashMap hashMap)
        constructs:function(hashMap){
            this.map=hashMap;
        },
        //> public Iterator iterator()
        iterator:function(){
            return this.map.newEntryIterator();
        },
        //> public boolean contains(Object o)
        contains:function(o){
            if(!(vjo.java.util.Map.Entry.clazz.isInstance(o))){
                return false;
            }
            var e=o;
            var candidate=this.map.getEntry(e.getKey());
            return candidate!==null&&vjo.java.lang.ObjectUtil.equals(candidate,e);
        },
        //> public boolean remove(Object o)
        remove:function(o){
            return this.map.removeMapping(o)!==null;
        },
        //> public int size()
        size:function(){
            return this.map.size_;
        },
        //> public void clear()
        clear:function(){
            this.map.clear();
        }
    })
    .endType(),
    //> int hash(Object x)
    hash:function(x){
        var h=(x===null?0:vjo.java.lang.ObjectUtil.hashCode(x));
        return h;
    },
    //> boolean eq(Object x,Object y)
    eq:function(x,y){
        return x===y||(x!==null&&vjo.java.lang.ObjectUtil.equals(x,y));
    },
    //> int indexFor(int h,int length)
    indexFor:function(h,length){
        return h&(length-1);
    }
})
.protos({
    table:null, //< Entry[]
    size_:0, //< int
    threshold:0, //< int
    loadFactor_:0, //< final float
    modCount:0, //< int
    contentionFlag:0, //< private int
    entrySet_:null, //< private Set<Map<K,V>> entrySet_
    ValueIterator:vjo.ctype() //< private
    .inherits('vjo.java.util.HashMap.HashIterator<K,V,V>')
    .protos({
        //> public constructs(HashMap map)
        constructs:function(map){
            this.base(map);
        },
        //> public V next()
        next:function(){
            return this.nextEntry().value;
        }
    })
    .endType(),
    KeyIterator:vjo.ctype() //< private
    .inherits('vjo.java.util.HashMap.HashIterator<K,V,K>')
    .protos({
        //> public constructs(HashMap map)
        constructs:function(map){
            this.base(map);
        },
        //> public K next()
        next:function(){
            return this.nextEntry().getKey();
        }
    })
    .endType(),
    EntryIterator:vjo.ctype() //< private
    .inherits('vjo.java.util.HashMap.HashIterator<K,V,Map>')
    .protos({
        //> public constructs(HashMap map)
        constructs:function(map){
            this.base(map);
        },
        //> public Map<K,V> next()
        next:function(){
            return this.nextEntry();
        }
    })
    .endType(),
    //> public constructs()
    //> public constructs(int initialCapacity,float loadFactor)
    //> public constructs(int initialCapacity)
    //> public constructs(Map<? extends K,? extends V> m)
    constructs:function(){
        if(arguments.length===2){
            this.constructs_2_0_HashMap_ovld(arguments[0],arguments[1]);
        }else if(arguments.length===1){
            if(typeof arguments[0]=="number"){
                this.constructs_1_0_HashMap_ovld(arguments[0]);
            }else if(vjo.java.util.Map.clazz.isInstance(arguments[0])){
                this.constructs_1_1_HashMap_ovld(arguments[0]);
            }
        }else if(arguments.length===0){
            this.constructs_0_0_HashMap_ovld();
        }
    },
    //> private void onEntry()
    onEntry:function(){
        switch(this.contentionFlag){
            case (0):
                this.contentionFlag=1;
                break;
            case (1):
                this.contentionFlag=2;
            case (2):
                throw new vjo.java.util.ConcurrentModificationException("concurrent access to HashMap attempted by theads");
            default: 
                throw new this.vj$.RuntimeException("Unexpected contentionFlag "+this.contentionFlag);
        }
    },
    //> private void onExit()
    onExit:function(){
        var oldContentionFlag=this.contentionFlag;
        this.contentionFlag=0;
        switch(oldContentionFlag){
            case (1):
                break;
            case (2):
                throw new vjo.java.util.ConcurrentModificationException("concurrent access to HashMap attempted by thread");
            default: 
                throw new this.vj$.RuntimeException("Unexpected contentionFlag "+oldContentionFlag);
        }
    },
    //> private constructs_2_0_HashMap_ovld(int initialCapacity,float loadFactor)
    constructs_2_0_HashMap_ovld:function(initialCapacity,loadFactor){
        this.base();
        if(initialCapacity<0){
            throw new this.vj$.IllegalArgumentException("Illegal initial capacity: "+initialCapacity);
        }
        if(initialCapacity>this.vj$.HashMap.MAXIMUM_CAPACITY){
            initialCapacity=this.vj$.HashMap.MAXIMUM_CAPACITY;
        }
        if(loadFactor<=0 || this.vj$.Float.isNaN(loadFactor)){
            throw new this.vj$.IllegalArgumentException("Illegal load factor: "+loadFactor);
        }
        var capacity=1;
        while(capacity<initialCapacity){
            capacity<<=1;
        }
        this.loadFactor_=loadFactor;
        this.threshold=this.vj$.Util.cast((capacity*loadFactor),'int');
        this.table=vjo.createArray(null, capacity);
        this.init();
    },
    //> private constructs_1_0_HashMap_ovld(int initialCapacity)
    constructs_1_0_HashMap_ovld:function(initialCapacity){
        this.constructs_2_0_HashMap_ovld(initialCapacity,this.vj$.HashMap.DEFAULT_LOAD_FACTOR);
    },
    //> private constructs_0_0_HashMap_ovld()
    constructs_0_0_HashMap_ovld:function(){
        this.base();
        this.loadFactor_=this.vj$.HashMap.DEFAULT_LOAD_FACTOR;
        this.threshold=this.vj$.Util.cast((this.vj$.HashMap.DEFAULT_INITIAL_CAPACITY*this.vj$.HashMap.DEFAULT_LOAD_FACTOR),'int');
        this.table=vjo.createArray(null, this.vj$.HashMap.DEFAULT_INITIAL_CAPACITY);
        this.init();
    },
    //> private constructs_1_1_HashMap_ovld(Map<? extends K,? extends V> m)
    constructs_1_1_HashMap_ovld:function(m){
        //This snippet will be handled by other mechanism
	    if(m === null) {
	  	    throw new vjo.java.lang.NullPointerException();
	    }

        this.constructs_2_0_HashMap_ovld(this.vj$.Math.max(this.vj$.Util.cast((m.size()/this.vj$.HashMap.DEFAULT_LOAD_FACTOR),'int')+1,this.vj$.HashMap.DEFAULT_INITIAL_CAPACITY),this.vj$.HashMap.DEFAULT_LOAD_FACTOR);
        this.putAllForCreate(m);
    },
    //> void init()
    init:function(){
    },
    //> public int size()
    size:function(){
        return this.size_;
    },
    //> public boolean isEmpty()
    isEmpty:function(){
        return this.size_===0;
    },
    //> public V get(Object key)
    get:function(key){
        var hash=this.vj$.HashMap.hash(key);
        var i=this.vj$.HashMap.indexFor(hash,this.table.length);
        var e=this.table[i];
        while(e!==null && !(e.hash===hash&&this.vj$.HashMap.eq(key,e.key))){
            e=e.next;
        }
        if(e===null){
            return null;
        }
        return e.value;
    },
    //> public boolean containsKey(Object key)
    containsKey:function(key){
        var hash=this.vj$.HashMap.hash(key);
        var i=this.vj$.HashMap.indexFor(hash,this.table.length);
        var e=this.table[i];
        while(e!==null){
            if(e.hash===hash && this.vj$.HashMap.eq(key,e.key)){
                return true;
            }
            e=e.next;
        }
        return false;
    },
    //> Entry<K,V> getEntry(Object key)
    getEntry:function(key){
        var hash=this.vj$.HashMap.hash(key);
        var i=this.vj$.HashMap.indexFor(hash,this.table.length);
        var e=this.table[i];
        while(e!==null && !(e.hash===hash&&this.vj$.HashMap.eq(key,e.key))){
            e=e.next;
        }
        return e;
    },
    //> public V put(K key,V value)
    put:function(key,value){
        if(typeof key === 'undefined') throw new this.vj$.IllegalArgumentException();
        var hash=this.vj$.HashMap.hash(key);
        var i=this.vj$.HashMap.indexFor(hash,this.table.length);
        for (var e=this.table[i];e!==null;e=e.next){
            if(e.hash===hash && this.vj$.HashMap.eq(key,e.key)){
                var oldValue=e.value;
                e.value=value;
                e.recordAccess(this);
                return oldValue;
            }
        }
        this.modCount++;
        this.addEntry(hash,key,value,i);
        return null;
    },
    //> private void putForCreate(K key,V value)
    putForCreate:function(key,value){
        var hash=this.vj$.HashMap.hash(key);
        var i=this.vj$.HashMap.indexFor(hash,this.table.length);
        for (var e=this.table[i];e!==null;e=e.next){
            if(e.hash===hash && this.vj$.HashMap.eq(key,e.key)){
                e.value=value;
                return;
            }
        }
        this.createEntry(hash,key,value,i);
    },
    //> void putAllForCreate(Map<? extends K,? extends V> m)
    putAllForCreate:function(m){
        for (var i=m.entrySet().iterator();i.hasNext();){
            var e=i.next();
            this.putForCreate(e.getKey(),e.getValue());
        }
    },
    //> void resize(int newCapacity)
    resize:function(newCapacity){
        var oldTable=this.table;
        var oldCapacity=oldTable.length;
        if(oldCapacity===this.vj$.HashMap.MAXIMUM_CAPACITY){
            this.threshold=this.vj$.Integer.MAX_VALUE;
            return;
        }
        var newTable=vjo.createArray(null, newCapacity);
        this.transfer(newTable);
        this.table=newTable;
        this.threshold=this.vj$.Util.cast((newCapacity*this.loadFactor_),'int');
    },
    //> void transfer(Entry[] newTable)
    transfer:function(newTable){
        this.onEntry();
        try {
            this.transfer0(newTable);
        }
        finally {
            this.onExit();
        }
    },
    //> private void transfer0(Entry[] newTable)
    transfer0:function(newTable){
        var src=this.table;
        var newCapacity=newTable.length;
        for (var j=0;j<src.length;j++){
            var e=src[j];
            if(e!==null){
                src[j]=null;
                do{
                    var next=e.next;
                    var i=this.vj$.HashMap.indexFor(e.hash,newCapacity);
                    e.next=newTable[i];
                    newTable[i]=e;
                    e=next;
                }while(e!==null);
            }
        }
    },
    //> public void putAll(Map<? extends K,? extends V> m)
    putAll:function(m){
        //This snippet will be handled by other mechanism
        if(m === null) {
  	        throw new vjo.java.lang.NullPointerException();
        }

       var numKeysToBeAdded=m.size();
        if(numKeysToBeAdded===0){
            return;
        }
        if(numKeysToBeAdded>this.threshold){
            var targetCapacity=this.vj$.Util.cast((parseFloat(numKeysToBeAdded/this.loadFactor_)+1),'int');
            if(targetCapacity>this.vj$.HashMap.MAXIMUM_CAPACITY){
                targetCapacity=this.vj$.HashMap.MAXIMUM_CAPACITY;
            }
            var newCapacity=this.table.length;
            while(newCapacity<targetCapacity){
                newCapacity<<=1;
            }
            if(newCapacity>this.table.length){
                this.resize(newCapacity);
            }
        }
        for (var i=m.entrySet().iterator();i.hasNext();){
            var e=i.next();
            this.put(e.getKey(),e.getValue());
        }
    },
    //> public V remove(Object key)
    remove:function(key){
        var e=this.removeEntryForKey(key);
        var empty = (e === null) || (typeof e === 'undefined');
        return (empty?null:e.value);
    },
    //> Entry<K,V> removeEntryForKey(Object key)
    removeEntryForKey:function(key){
        var hash=this.vj$.HashMap.hash(key);
        var i=this.vj$.HashMap.indexFor(hash,this.table.length);
        var prev=this.table[i];
        var e=prev;
        while(e!==null){
            var next=e.next;
            if(e.hash===hash && this.vj$.HashMap.eq(key,e.key)){
                this.modCount++;
                this.size_--;
                if(prev===e){
                    this.table[i]=next;
                }else {
                    prev.next=next;
                }
                e.recordRemoval(this);
                return e;
            }
            prev=e;
            e=next;
        }
        return e;
    },
    //> Entry<K,V> removeMapping(Object o)
    removeMapping:function(o){
        if(!(vjo.java.util.Map.clazz.isInstance(o))){
            return null;
        }
        var entry=o;
        var k=entry.getKey();
        var hash=this.vj$.HashMap.hash(k);
        var i=this.vj$.HashMap.indexFor(hash,this.table.length);
        var prev=this.table[i];
        var e=prev;
        while(e!==null){
            var next=e.next;
            if(e.hash===hash && vjo.java.lang.ObjectUtil.equals(e,entry)){
                this.modCount++;
                this.size_--;
                if(prev===e){
                    this.table[i]=next;
                }else {
                    prev.next=next;
                }
                e.recordRemoval(this);
                return e;
            }
            prev=e;
            e=next;
        }
        return e;
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
    //> public boolean containsValue(Object value)
    containsValue:function(value){
        if(value===null){
            return this.containsNullValue();
        }
        var tab=this.table;
        for (var i=0;i<tab.length;i++){
            for (var e=tab[i];e!==null;e=e.next){
                if(vjo.java.lang.ObjectUtil.equals(value,e.value)){
                    return true;
                }
            }
        }
        return false;
    },
    //> private boolean containsNullValue()
    containsNullValue:function(){
        var tab=this.table;
        for (var i=0;i<tab.length;i++){
            for (var e=tab[i];e!==null;e=e.next){
                if(e.value===null){
                    return true;
                }
            }
        }
        return false;
    },
    //> void addEntry(int hash,K key,V value,int bucketIndex)
    addEntry:function(hash,key,value,bucketIndex){
        var e=this.table[bucketIndex];
        this.table[bucketIndex]=new this.vj$.HashMap.Entry(hash,key,value,e);
        if(this.size_++>=this.threshold){
            this.resize(2*this.table.length);
        }
    },
    //> void createEntry(int hash,K key,V value,int bucketIndex)
    createEntry:function(hash,key,value,bucketIndex){
        var e=this.table[bucketIndex];
        this.table[bucketIndex]=new this.vj$.HashMap.Entry(hash,key,value,e);
        this.size_++;
    },
    //> Iterator<K> newKeyIterator()
    newKeyIterator:function(){
        return new this.KeyIterator(this);
    },
    //> Iterator<V> newValueIterator()
    newValueIterator:function(){
        return new this.ValueIterator(this);
    },
    //> Iterator<Map<K,V>> newEntryIterator()
    newEntryIterator:function(){
        return new this.EntryIterator(this);
    },
    //> public Set<K> keySet()
    keySet:function(){
        var ks=this.keySet_;
        return (ks!==null?ks:(keySet_=new this.vj$.HashMap.KeySet(this)));
    },
    //> public Collection<V> values()
    values:function(){
        var vs=this.values_;
        return (vs!==null?vs:(values_=new this.vj$.HashMap.Values(this)));
    },
    //> public Set<Map<K,V>> entrySet()
    entrySet:function(){
        var es=this.entrySet_;
        return (es!==null?es:(this.entrySet_=new this.vj$.HashMap.EntrySet(this)));
    },
    //> int capacity()
    capacity:function(){
        return this.table.length;
    },
    //> float loadFactor()
    loadFactor:function(){
        return this.loadFactor_;
    }
})
.inits(function(){
    this.vj$.HashMap.MAXIMUM_CAPACITY=1<<30;
})
.endType();