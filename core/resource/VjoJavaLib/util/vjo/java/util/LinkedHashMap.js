vjo.ctype('vjo.java.util.LinkedHashMap<K,V>') //< public
.needs(['vjo.java.lang.IllegalStateException','vjo.java.util.NoSuchElementException',
    'vjo.java.lang.ObjectUtil'])
.needs('vjo.java.util.Iterator','')
.needs('vjo.java.util.ConcurrentModificationException','')
.inherits('vjo.java.util.HashMap<K,V>')
.satisfies('vjo.java.util.Map<K,V>')
.props({
    serialVersionUID:3801124242820219131, //< private final long
    Entry:vjo.ctype() //< public Entry<K,V>
    .inherits('vjo.java.util.HashMap.Entry<K,V>')
    .protos({
        before:null, //< Entry<K,V> before
        after:null, //< Entry<K,V> after
        //> constructs(int hash,K key,V value,Entry<K,V> next)
        constructs:function(hash,key,value,next){
            this.base(hash,key,value,next);
        },
        //> private void remove()
        remove:function(){
            this.before.after=this.after;
            this.after.before=this.before;
        },
        //> private void addBefore(Entry<K,V> existingEntry)
        addBefore:function(existingEntry){
            this.after=existingEntry;
            this.before=existingEntry.before;
            this.before.after=this;
            this.after.before=this;
        },
        //> void recordAccess(HashMap<K,V> m)
        recordAccess:function(m){
            if(arguments.length===1){
                if(vjo.java.util.HashMap.clazz.isInstance(arguments[0]) ){
                    this.recordAccess_1_0_Entry_ovld(arguments[0]);
                }else if(this.base && this.base.recordAccess){
                    this.base.recordAccess.apply(this,arguments);
                }
            }else if(this.base && this.base.recordAccess){
                this.base.recordAccess.apply(this,arguments);
            }
        },
        //> private void recordAccess_1_0_Entry_ovld(HashMap<K,V> m)
        recordAccess_1_0_Entry_ovld:function(m){
            var lm=m;
            if(lm.accessOrder){
                lm.modCount++;
                this.remove();
                this.addBefore(lm.header);
            }
        },
        //> void recordRemoval(HashMap<K,V> m)
        recordRemoval:function(m){
            if(arguments.length===1){
                if(arguments[0] instanceof vjo.java.util.HashMap){
                    this.recordRemoval_1_0_Entry_ovld(arguments[0]);
                }else if(this.base && this.base.recordRemoval){
                    this.base.recordRemoval.apply(this,arguments);
                }
            }else if(this.base && this.base.recordRemoval){
                this.base.recordRemoval.apply(this,arguments);
            }
        },
        //> private void recordRemoval_1_0_Entry_ovld(HashMap<K,V> m)
        recordRemoval_1_0_Entry_ovld:function(m){
            this.remove();
        }
    })
    .endType(),
    LinkedHashIterator:vjo.ctype() //< private abstract static LinkedHashIterator<K, V, T>
    .satisfies('vjo.java.util.Iterator<T>')
    .protos({
        nextEntry_:null, //< Entry<K,V> nextEntry_
        lastReturned:null, //< Entry<K,V> lastReturned
        expectedModCount:0, //< int
        map: null, //LinkedHashMap<K,V> map
        //> public constructs()
        constructs:function(map){
            this.map = map;
            this.nextEntry_=map.header.after;
            this.expectedModCount=map.modCount;
        },
        //> public boolean hasNext()
        hasNext:function(){
            return this.nextEntry_!==this.map.header;
        },
        //> public void remove()
        remove:function(){
            if(this.lastReturned===null){
                throw new vjo.java.lang.IllegalStateException();
            }
            if(this.map.modCount!==this.expectedModCount){
                throw new vjo.java.util.ConcurrentModificationException();
            }
            this.map.remove(this.lastReturned.key);
            this.lastReturned=null;
            this.expectedModCount=this.map.modCount;
        },
        //> Entry<K,V> nextEntry()
        nextEntry:function(){
            if(this.map.modCount!==this.expectedModCount){
                throw new vjo.java.util.ConcurrentModificationException();
            }
            if(this.nextEntry_===this.map.header){
                throw new this.vj$.NoSuchElementException();
            }
            var e=this.lastReturned=this.nextEntry_;
            this.nextEntry_=e.after;
            return e;
        }
    })
    .endType()
})
.protos({
    header:null, //< private Entry<K,V> header
    accessOrder:false, //< private final boolean
    KeyIterator:vjo.ctype() //< private
    .inherits('vjo.java.util.LinkedHashMap.LinkedHashIterator<K, V, K>')
    .protos({
        //> public  constructs()
        constructs:function(map){
            this.base(map);
        },
        //> public K next()
        next:function(){
            return this.nextEntry().getKey();
        }
    })
    .endType(),
    ValueIterator:vjo.ctype() //< private
    .inherits('vjo.java.util.LinkedHashMap.LinkedHashIterator<K, V, V>')
    .protos({
        //> public  constructs()
        constructs:function(map){
            this.base(map);
        },
        //> public V next()
        next:function(){
            return this.nextEntry().value;
        }
    })
    .endType(),
    EntryIterator:vjo.ctype() //< private
    .inherits('vjo.java.util.LinkedHashMap.LinkedHashIterator<K, V, vjo.java.util.Map.Entry<K,V>>')
    .protos({
        //> public  constructs()
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
    //> public constructs(int initialCapacity,float loadFactor,boolean accessOrder)
    constructs:function(){
        if(arguments.length===2){
            this.constructs_2_0_LinkedHashMap_ovld(arguments[0],arguments[1]);
        }else if(arguments.length===1){
            if(typeof arguments[0]=="number"){
                this.constructs_1_0_LinkedHashMap_ovld(arguments[0]);
            }else if(vjo.java.util.Map.clazz.isInstance(arguments[0])){
                this.constructs_1_1_LinkedHashMap_ovld(arguments[0]);
            }
        }else if(arguments.length===0){
            this.constructs_0_0_LinkedHashMap_ovld();
        }else if(arguments.length===3){
            this.constructs_3_0_LinkedHashMap_ovld(arguments[0],arguments[1],arguments[2]);
        }
    },
    //> private constructs_2_0_LinkedHashMap_ovld(int initialCapacity,float loadFactor)
    constructs_2_0_LinkedHashMap_ovld:function(initialCapacity,loadFactor){
        this.base(initialCapacity,loadFactor);
        this.accessOrder=false;
    },
    //> private constructs_1_0_LinkedHashMap_ovld(int initialCapacity)
    constructs_1_0_LinkedHashMap_ovld:function(initialCapacity){
        this.base(initialCapacity);
        this.accessOrder=false;
    },
    //> private constructs_0_0_LinkedHashMap_ovld()
    constructs_0_0_LinkedHashMap_ovld:function(){
        this.base();
        this.accessOrder=false;
    },
    //> private constructs_1_1_LinkedHashMap_ovld(Map<? extends K,? extends V> m)
    constructs_1_1_LinkedHashMap_ovld:function(m){
        this.base(m);
        this.accessOrder=false;
    },
    //> private constructs_3_0_LinkedHashMap_ovld(int initialCapacity,float loadFactor,boolean accessOrder)
    constructs_3_0_LinkedHashMap_ovld:function(initialCapacity,loadFactor,accessOrder){
        this.base(initialCapacity,loadFactor);
        this.accessOrder=accessOrder;
    },
    //> void init()
    init:function(){
        this.header=new this.vj$.LinkedHashMap.Entry(-1,null,null,null);
        this.header.before=this.header.after=this.header;
    },
    //> void transfer(Entry[] newTable)
    transfer:function(newTable){
        var newCapacity=newTable.length;
        for (var e=this.header.after;e!==this.header;e=e.after){
            var index=vjo.java.util.HashMap.indexFor(e.hash,newCapacity);
            e.next=newTable[index];
            newTable[index]=e;
        }
    },
    //> public void clear()
    clear:function(){
        this.base.clear();
        this.header.before=this.header.after=this.header;
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
    //> void addEntry(int hash,K key,V value,int bucketIndex)
    addEntry:function(hash,key,value,bucketIndex){
        this.createEntry(hash,key,value,bucketIndex);
        var eldest=this.header.after;
        if(this.removeEldestEntry(eldest)){
            this.removeEntryForKey(eldest.key);
        }else {
            if(this.size_>=this.threshold){
                this.resize(2*this.table.length);
            }
        }
    },
    //> void createEntry(int hash,K key,V value,int bucketIndex)
    createEntry:function(hash,key,value,bucketIndex){
        var old=this.table[bucketIndex];
        var e=new this.vj$.LinkedHashMap.Entry(hash,key,value,old);
        this.table[bucketIndex]=e;
        e.addBefore(this.header);
        this.size_++;
    },
    //> protected boolean removeEldestEntry(Map<K,V> eldest)
    removeEldestEntry:function(eldest){
        return false;
    },
    //> public boolean containsValue(Object value)
    containsValue:function(value){
        if(arguments.length===1){
            return this.containsValue_1_0_LinkedHashMap_ovld(arguments[0]);
        }else if(this.base && this.base.containsValue){
            return this.base.containsValue.apply(this,arguments);
        }
    },
    //> private boolean containsValue_1_0_LinkedHashMap_ovld(Object value)
    containsValue_1_0_LinkedHashMap_ovld:function(value){
        if(value===null){
            for (var e=this.header.after;e!==this.header;e=e.after){
                if(e.value===null){
                    return true;
                }
            }
        }else {
            for (var e=this.header.after;e!==this.header;e=e.after){
                if(vjo.java.lang.ObjectUtil.equals(value,e.value)){
                    return true;
                }
            }
        }
        return false;
    },
    //> public V get(Object key)
    get:function(key){
        if(arguments.length===1){
            return this.get_1_0_LinkedHashMap_ovld(arguments[0]);
        }else if(this.base && this.base.get){
            return this.base.get.apply(this,arguments);
        }
    },
    //> private V get_1_0_LinkedHashMap_ovld(Object key)
    get_1_0_LinkedHashMap_ovld:function(key){
        var e=this.getEntry(key);
        if(e===null){
            return null;
        }
        e.recordAccess(this);
        return e.value;
    }
})
.endType();