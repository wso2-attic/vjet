vjo.ctype('vjo.java.util.TreeMap<K,V>') //< public
.needs(['vjo.java.lang.IllegalArgumentException','vjo.java.lang.IllegalStateException',
    'vjo.java.lang.Comparable','vjo.java.util.NoSuchElementException',
    'vjo.java.util.AbstractSet','vjo.java.util.Map','vjo.java.lang.ObjectUtil',
    'vjo.java.lang.Boolean', 'vjo.java.lang.NullPointerException'])
.needs('vjo.java.util.Comparator','')
.needs('vjo.java.util.Map','')
.needs('vjo.java.util.Set','')
.needs('vjo.java.util.Iterator','')
.needs('vjo.java.util.Collection','')
.needs('vjo.java.util.AbstractCollection','')
.needs('vjo.java.util.ConcurrentModificationException','')
.needs('vjo.java.util.SortedSet','')
.needs('vjo.java.util.SortedMap','')
.inherits('vjo.java.util.AbstractMap<K,V>')
.satisfies('vjo.java.util.SortedMap<K,V>')
.satisfies('vjo.java.lang.Cloneable')
.satisfies('vjo.java.io.Serializable')
.props({
    RED:false, //< private final boolean
    BLACK:true, //< private final boolean
    serialVersionUID:919286545866124006, //< private final long
    SubMap:vjo.ctype() //< private SubMap<K,V>
    .inherits('vjo.java.util.AbstractMap<K,V>')
    .satisfies('vjo.java.util.SortedMap<K,V>')
    .satisfies('vjo.java.io.Serializable')
    .props({
        serialVersionUID:-6520786458950516097 //< private final long
    })
    .protos({
        map:null, //< private TreeMap<K,V> map
        fromStart:false, //< private boolean
        toEnd:false, //< private boolean
        fromKey:null, //< private K
        toKey:null, //< private K
        entrySet__:null, //< private Set<Map<K,V>> entrySet__
        //> constructs()
        //> constructs(TreeMap<K,V> map,K fromKey,K toKey)
        //> constructs(TreeMap<K,V> map,K key,boolean headMap)
        //> constructs(TreeMap<K,V> map,boolean fromStart,K fromKey,boolean toEnd,K toKey)
        constructs:function(){
            this.entrySet__=new this.vj$.TreeMap.EntrySetView(arguments[0],this);
            if(arguments.length===3){
                if(vjo.java.util.TreeMap.clazz.isInstance(arguments[0]) && typeof arguments[2]=="boolean"){
                    this.constructs_3_0_SubMap_ovld(arguments[0],arguments[1],arguments[2]);
                }else if(vjo.java.util.TreeMap.clazz.isInstance(arguments[0]) && arguments[1]){
                    this.constructs_3_1_SubMap_ovld(arguments[0],arguments[1],arguments[2]);
                }
            }else if(arguments.length===5){
                this.constructs_5_0_SubMap_ovld(arguments[0],arguments[1],arguments[2],arguments[3],arguments[4]);
            }
        },
        //> private constructs_3_1_SubMap_ovld(TreeMap<K,V> map,K key,boolean headMap)
        constructs_3_0_SubMap_ovld:function(map,key,headMap){
            this.base();
            this.map=map;
            this.map.compare(key,key);
            if(headMap){
                this.fromStart=true;
                this.toKey=key;
            }else {
                this.toEnd=true;
                this.fromKey=key;
            }
        },
        //> private constructs_3_0_SubMap_ovld(TreeMap<K,V> map,K fromKey,K toKey)
        constructs_3_1_SubMap_ovld:function(map,fromKey,toKey){
            this.base();
            this.map=map;
            if(this.map.compare(fromKey,toKey)>0){
                throw new this.vj$.IllegalArgumentException("fromKey > toKey");
            }
            this.fromKey=fromKey;
            this.toKey=toKey;
        },
        //> private constructs_5_0_SubMap_ovld(TreeMap<K,V> map,boolean fromStart,K fromKey,boolean toEnd,K toKey)
        constructs_5_0_SubMap_ovld:function(map,fromStart,fromKey,toEnd,toKey){
            this.base();
            this.map=map;
            this.fromStart=fromStart;
            this.fromKey=fromKey;
            this.toEnd=toEnd;
            this.toKey=toKey;
        },
        //> public boolean isEmpty()
        isEmpty:function(){
            return this.entrySet__.isEmpty();
        },
        //> public V put(K key,V value)
        put:function(key,value){
            if(!this.inRange(key)){
                throw new this.vj$.IllegalArgumentException("key out of range");
            }
            return this.map.put(key,value);
        },
        //> public Comparator<? super K> comparator()
        comparator:function(){
            return this.map.comparator_;
        },
        //> public K firstKey()
        firstKey:function(){
            var e=this.fromStart?this.map.firstEntry():this.map.getCeilEntry(this.fromKey);
            var first=this.vj$.TreeMap.key(e);
            if(!this.toEnd&&this.map.compare(first,this.toKey)>=0){
                throw (new this.vj$.NoSuchElementException());
            }
            return first;
        },
        //> public K lastKey()
        lastKey:function(){
            var e=this.toEnd?this.map.lastEntry():this.map.getPrecedingEntry(this.toKey);
            var last=this.vj$.TreeMap.key(e);
            if(!this.fromStart&&this.map.compare(last,this.fromKey)<0){
                throw (new this.vj$.NoSuchElementException());
            }
            return last;
        },
        //> public Set<Map<K,V>> entrySet()
        entrySet:function(){
            return this.entrySet__;
        },
        //> public SortedMap<K,V> subMap(K fromKey,K toKey)
        subMap:function(fromKey,toKey){
            if(!this.inRange2(fromKey)){
                throw new this.vj$.IllegalArgumentException("fromKey out of range");
            }
            if(!this.inRange2(toKey)){
                throw new this.vj$.IllegalArgumentException("toKey out of range");
            }
            return new this.vj$.SubMap(this.map,fromKey,toKey);
        },
        //> public SortedMap<K,V> headMap(K toKey)
        headMap:function(toKey){
            if(!this.inRange2(toKey)){
                throw new this.vj$.IllegalArgumentException("toKey out of range");
            }
            return new this.vj$.SubMap(this.map,this.fromStart,this.fromKey,false,toKey);
        },
        //> public SortedMap<K,V> tailMap(K fromKey)
        tailMap:function(fromKey){
            if(!this.inRange2(fromKey)){
                throw new this.vj$.IllegalArgumentException("fromKey out of range");
            }
            return new this.vj$.SubMap(this.map,false,fromKey,this.toEnd,this.toKey);
        },
        //> private boolean inRange(K key)
        inRange:function(key){
            return (this.fromStart||this.map.compare(key,this.fromKey)>=0)&&(this.toEnd||this.map.compare(key,this.toKey)<0);
        },
        //> private boolean inRange2(K key)
        inRange2:function(key){
            return (this.fromStart||this.map.compare(key,this.fromKey)>=0)&&(this.toEnd||this.map.compare(key,this.toKey)<=0);
        },
        //> public boolean containsKey(Object key)
        containsKey:function(key){
            if(arguments.length===1){
                return this.containsKey_1_0_SubMap_ovld(arguments[0]);
            }else if(this.base && this.base.containsKey){
                return this.base.containsKey.apply(this,arguments);
            }
        },
        //> private boolean containsKey_1_0_SubMap_ovld(Object key)
        containsKey_1_0_SubMap_ovld:function(key){
            return this.inRange(key)&&this.map.containsKey(key);
        },
        //> public V get(Object key)
        get:function(key){
            if(arguments.length===1){
                return this.get_1_0_SubMap_ovld(arguments[0]);
            }else if(this.base && this.base.get){
                return this.base.get.apply(this,arguments);
            }
        },
        //> private V get_1_0_SubMap_ovld(Object key)
        get_1_0_SubMap_ovld:function(key){
            if(!this.inRange(key)){
                return null;
            }
            return this.map.get(key);
        }
    })
    .endType(),
    EntrySetView:vjo.ctype() //< private EntrySetView<K,V>
    .inherits('vjo.java.util.AbstractSet<Map>')
    .protos({
        size__:-1, //< private int
        sizeModCount:0, //< private int
        map:null, //< private TreeMap<K,V> map
        submap:null, //< private SubMap<K,V> submap
        //> public constructs(TreeMap<K,V> map,SubMap<K,V> submap)
        constructs:function(map,submap){
            this.base();
            this.map=map;
            this.submap=submap;
        },
        //> public int size()
        size:function(){
            if(this.size__=== -1||this.sizeModCount!==this.map.modCount){
                this.size__=0;
                this.sizeModCount=this.map.modCount;
                var i=this.iterator();
                while(i.hasNext()){
                    this.size__++;
                    i.next();
                }
            }
            return this.size__;
        },
        //> public boolean isEmpty()
        isEmpty:function(){
            return !this.iterator().hasNext();
        },
        //> public Iterator<Map<K,V>> iterator()
        iterator:function(){
            return new this.vj$.TreeMap.SubMapEntryIterator(this.map,(this.submap.fromStart?this.map.firstEntry():this.map.getCeilEntry(this.submap.fromKey)),(this.submap.toEnd?null:this.map.getCeilEntry(this.submap.toKey)));
        },
        //> public boolean contains(Object o)
        contains:function(o){
            if(arguments.length===1){
                 return this.contains_1_0_EntrySetView_ovld(arguments[0]);
            }else if(this.base && this.base.contains){
                return this.base.contains.apply(this,arguments);
            }
        },
        //> private boolean contains_1_0_EntrySetView_ovld(Object o)
        contains_1_0_EntrySetView_ovld:function(o){
            if(!(vjo.getType('vjo.java.util.Map.Entry').clazz.isInstance(o))){
                return false;
            }
            var entry=o;
            var key=entry.getKey();
            if(!this.submap.inRange(key)){
                return false;
            }
            var node=this.map.getEntry(key);
            return node!==null&&this.map.valEquals(node.getValue(),entry.getValue());
        },
        //> public boolean remove(Object o)
        remove:function(o){
            if(arguments.length===1){
                return this.remove_1_0_EntrySetView_ovld(arguments[0]);
            }else if(this.base && this.base.remove){
                return this.base.remove.apply(this,arguments);
            }
        },
        //> private boolean remove_1_0_EntrySetView_ovld(Object o)
        remove_1_0_EntrySetView_ovld:function(o){
            if(!(vjo.getType('vjo.java.util.Map.Entry').clazz.isInstance(o))){
                return false;
            }
            var entry=o;
            var key=entry.getKey();
            if(!this.submap.inRange(key)){
                return false;
            }
            var node=this.map.getEntry(key);
            if(node!==null&&this.map.valEquals(node.getValue(),entry.getValue())){
                this.map.deleteEntry(node);
                return true;
            }
            return false;
        }
    })
    .endType(),
    PrivateEntryIterator:vjo.ctype() //< private abstract PrivateEntryIterator<T,K,V>
    .satisfies('vjo.java.util.Iterator<T>')
    .protos({
        expectedModCount:0, //< private int
        lastReturned:null, //< private Entry<K,V> lastReturned
        next_:null, //< Entry<K,V> next_
        map:null, //< protected TreeMap<K,V> map
        //> constructs()
        //> constructs(TreeMap<K,V> map)
        //> constructs(TreeMap<K,V> map,Entry<K,V> first)
        constructs:function(){
            if(arguments.length===1){
                this.constructs_1_0_PrivateEntryIterator_ovld(arguments[0]);
            }else if(arguments.length===2){
                this.constructs_2_0_PrivateEntryIterator_ovld(arguments[0],arguments[1]);
            }
        },
        //> private constructs_1_0_PrivateEntryIterator_ovld(TreeMap<K,V> map)
        constructs_1_0_PrivateEntryIterator_ovld:function(map){
            this.map=map;
            this.expectedModCount=map.modCount;
            this.next_=map.firstEntry();
        },
        //> private constructs_2_0_PrivateEntryIterator_ovld(TreeMap<K,V> map,Entry<K,V> first)
        constructs_2_0_PrivateEntryIterator_ovld:function(map,first){
            this.map=map;
            this.expectedModCount=map.modCount;
            this.next_=first;
        },
        //> public boolean hasNext()
        hasNext:function(){
            return this.next_!==null;
        },
        //> final Entry<K,V> nextEntry()
        nextEntry:function(){
            if(this.next_===null){
                throw new  this.vj$.NoSuchElementException();
            }
            if(this.map.modCount!==this.expectedModCount){
                throw new  vjo.java.util.ConcurrentModificationException();
            }
            this.lastReturned=this.next_;
            this.next_=this.map.successor(this.next_);
            return this.lastReturned;
        },
        //> public void remove()
        remove:function(){
            if(this.lastReturned===null){
                throw new this.vj$.IllegalStateException();
            }
            if(this.map.modCount!==this.expectedModCount){
                throw new  this.vj$.ConcurrentModificationException();
            }
            if(this.lastReturned.left!==null&&this.lastReturned.right!==null){
                this.next_=this.lastReturned;
            }
            this.map.deleteEntry(this.lastReturned);
            this.expectedModCount++;
            this.lastReturned=null;
        }
    })
    .endType(),
    SubMapEntryIterator:vjo.ctype() //< private SubMapEntryIterator<K,V>
    .inherits('vjo.java.util.TreeMap.PrivateEntryIterator<Map,K,V>')
    .protos({
        firstExcludedKey:null, //< private final K
        //> constructs(TreeMap<K,V> map,Entry<K,V> first,Entry<K,V> firstExcluded)
        constructs:function(map,first,firstExcluded){
            this.base(map,first);
            this.firstExcludedKey=(firstExcluded===null?null:firstExcluded.key);
        },
        //> public boolean hasNext()
        hasNext:function(){
            return this.next_!==null&&this.next_.key!==this.firstExcludedKey;
        },
        //> public Map<K,V> next()
        next:function(){
            if(this.next_===null||this.next_.key===this.firstExcludedKey){
                throw new this.vj$.NoSuchElementException();
            }
            return this.nextEntry();
        }
    })
    .endType(),
    Entry:vjo.ctype() //< public Entry<K,V>
    .satisfies('vjo.java.util.Map.Entry<K,V>')
    .protos({
        key:null, //< K
        value:null, //< V
        left:null, //< Entry<K,V> left
        right:null, //< Entry<K,V> right
        parent:null, //< Entry<K,V> parent
        color:false, //< boolean
        //> constructs(K key,V value,Entry<K,V> parent)
        constructs:function(key,value,parent){
            this.color=this.vj$.TreeMap.BLACK;
            this.key=key;
            this.value=value;
            this.parent=parent;
        },
        //> public K getKey()
        getKey:function(){
            return this.key;
        },
        //> public V getValue()
        getValue:function(){
            return this.value;
        },
        //> public V setValue(V value)
        setValue:function(value){
            var oldValue=this.value;
            this.value=value;
            return oldValue;
        },
        //> public boolean equals(Object o)
        equals:function(o){
            if(!(vjo.getType('vjo.java.util.Map.Entry').clazz.isInstance(o))){
                return false;
            }
            var e=o;
            return this.vj$.TreeMap.valEquals(this.key,e.getKey())&&this.vj$.TreeMap.valEquals(this.value,e.getValue());
        },
        //> public int hashCode()
        hashCode:function(){
            var keyHash=this.key===null?0:this.vj$.ObjectUtil.hashCode(this.key);
            var valueHash=this.value===null?0:this.vj$.ObjectUtil.hashCode(this.value);
            return keyHash^valueHash;
        },
        //> public String toString()
        toString:function(){
            return this.key+"="+this.value;
        }
    })
    .endType(),
    //> private <K> K key(Entry<K,?> e)
    key:function(e){
        if(e===null){
            throw new this.vj$.NoSuchElementException();
        }
        return e.key;
    },
    //> private boolean valEquals(Object o1,Object o2)
    valEquals:function(o1,o2){
        return (o1===null?o2===null:vjo.java.lang.ObjectUtil.equals(o1,o2));
    },
    //> private <K,V> boolean colorOf(Entry<K,V> p)
    colorOf:function(p){
        return (p===null?this.BLACK:p.color);
    },
    //> private <K,V> Entry<K,V> parentOf(Entry<K,V> p)
    parentOf:function(p){
        return (p===null?null:p.parent);
    },
    //> private <K,V> void setColor(Entry<K,V> p,boolean c)
    setColor:function(p,c){
        if(p!==null){
            p.color=c;
        }
    },
    //> private <K,V> Entry<K,V> leftOf(Entry<K,V> p)
    leftOf:function(p){
        return (p===null)?null:p.left;
    },
    //> private <K,V> Entry<K,V> rightOf(Entry<K,V> p)
    rightOf:function(p){
        return (p===null)?null:p.right;
    },
    //> private int computeRedLevel(int sz)
    computeRedLevel:function(sz){
        var level=0;
        for (var m=sz-1;m>=0;m=parseInt(m/2)-1){
            level++;
        }
        return level;
    }
})
.protos({
    comparator_:null, //< private Comparator<? super K> comparator_
    root:null, //< private Entry<K,V> root
    size_:0, //< private int
    modCount:0, //< private int
    entrySet_:null, //< private Set<Map<K,V>> entrySet_
    EntryIterator:vjo.ctype() //< private
    .inherits('vjo.java.util.TreeMap.PrivateEntryIterator<Map,K,V>')
    .protos({
        //> public constructs()
        constructs:function(){
            this.base(this.vj$.outer);
        },
        //> public Map<K,V> next()
        next:function(){
            return this.nextEntry();
        }
    })
    .endType(),
    KeyIterator:vjo.ctype() //< private
    .inherits('vjo.java.util.TreeMap.PrivateEntryIterator<K,K,V>')
    .protos({
        //> public constructs()
        constructs:function(){
            this.base(this.vj$.outer);
        },
        //> public K next()
        next:function(){
            return this.nextEntry().key;
        }
    })
    .endType(),
    ValueIterator:vjo.ctype() //< private
    .inherits('vjo.java.util.TreeMap.PrivateEntryIterator<V,K,V>')
    .protos({
        //> public constructs()
        constructs:function(){
            this.base(this.vj$.outer);
        },
        //> public V next()
        next:function(){
            return this.nextEntry().value;
        }
    })
    .endType(),
    //> public constructs()
    //> public constructs(Comparator<? super K> c)
    //> public constructs(Map<? extends K,? extends V> m)
    //> public constructs(SortedMap<K,? extends V> m)
    constructs:function(){
        if(arguments.length===0){
            this.constructs_0_0_TreeMap_ovld();
        }else if(arguments.length===1){
            if(vjo.java.util.Comparator.clazz.isInstance(arguments[0])){
                this.constructs_1_0_TreeMap_ovld(arguments[0]);
            }else if(vjo.java.util.SortedMap.clazz.isInstance(arguments[0])){
                this.constructs_1_2_TreeMap_ovld(arguments[0]);
            }else if(vjo.java.util.Map.clazz.isInstance(arguments[0])){
                this.constructs_1_1_TreeMap_ovld(arguments[0]);
            }
        }
    },
    //> private void incrementSize()
    incrementSize:function(){
        this.modCount++;
        this.size_++;
    },
    //> private void decrementSize()
    decrementSize:function(){
        this.modCount++;
        this.size_--;
    },
    //> private constructs_0_0_TreeMap_ovld()
    constructs_0_0_TreeMap_ovld:function(){
        this.base();
    },
    //> private constructs_1_0_TreeMap_ovld(Comparator<? super K> c)
    constructs_1_0_TreeMap_ovld:function(c){
        this.base();
        this.comparator_=c;
    },
    //> private constructs_1_1_TreeMap_ovld(Map<? extends K,? extends V> m)
    constructs_1_1_TreeMap_ovld:function(m){
        this.base();
        this.putAll(m);
    },
    //> private constructs_1_2_TreeMap_ovld(SortedMap<K,? extends V> m)
    constructs_1_2_TreeMap_ovld:function(m){
        this.base();
        this.comparator_=m.comparator();
        try {
            this.buildFromSorted(m.size(),m.entrySet().iterator(),null);
        }
        catch(cannotHappen){
        }
    },
    //> public int size()
    size:function(){
        return this.size_;
    },
    //> private boolean valueSearchNull(Entry n)
    valueSearchNull:function(n){
        if(n.value===null){
            return true;
        }
        return (n.left!==null&&this.valueSearchNull(n.left))||(n.right!==null&&this.valueSearchNull(n.right));
    },
    //> private boolean valueSearchNonNull(Entry n,Object value)
    valueSearchNonNull:function(n,value){
        if(vjo.java.lang.ObjectUtil.equals(value,n.value)){
            return true;
        }
        return (n.left!==null&&this.valueSearchNonNull(n.left,value))||(n.right!==null&&this.valueSearchNonNull(n.right,value));
    },
    //> public Comparator<? super K> comparator()
    comparator:function(){
        return this.comparator_;
    },
    //> public K firstKey()
    firstKey:function(){
        return this.vj$.TreeMap.key(this.firstEntry());
    },
    //> public K lastKey()
    lastKey:function(){
        return this.vj$.TreeMap.key(this.lastEntry());
    },
    //> public void putAll(Map<? extends K,? extends V> map)
    putAll:function(map){
        var mapSize=map.size();
        if(this.size_===0&&mapSize!==0&&vjo.java.util.SortedMap.clazz.isInstance(map)){
            var c=map.comparator();
            if(c===this.comparator_||(c!==null&&vjo.java.lang.ObjectUtil.equals(c, this.comparator_))){
                ++this.modCount;
                try {
                    this.buildFromSorted(mapSize,map.entrySet().iterator(),null);
                }
                catch(cannotHappen){
                }
                return;
            }
        }
        this.base.putAll(map);
    },
    //> private Entry<K,V> getEntry(Object key)
    getEntry:function(key){
        var p=this.root;
        var k=key;
        while(p!==null){
            var cmp=this.compare(k,p.key);
            if(cmp===0){
                return p;
            }else if(cmp<0){
                p=p.left;
            }else {
                p=p.right;
            }
        }
        return null;
    },
    //> private Entry<K,V> getCeilEntry(K key)
    getCeilEntry:function(key){
        var p=this.root;
        if(p===null){
            return null;
        }
        while(true){
            var cmp=this.compare(key,p.key);
            if(cmp===0){
                return p;
            }else if(cmp<0){
                if(p.left!==null){
                    p=p.left;
                }else {
                    return p;
                }
            }else {
                if(p.right!==null){
                    p=p.right;
                }else {
                    var parent=p.parent;
                    var ch=p;
                    while(parent!==null && ch===parent.right){
                        ch=parent;
                        parent=parent.parent;
                    }
                    return parent;
                }
            }
        }
    },
    //> private Entry<K,V> getPrecedingEntry(K key)
    getPrecedingEntry:function(key){
        var p=this.root;
        if(p===null){
            return null;
        }
        while(true){
            var cmp=this.compare(key,p.key);
            if(cmp>0){
                if(p.right!==null){
                    p=p.right;
                }else {
                    return p;
                }
            }else {
                if(p.left!==null){
                    p=p.left;
                }else {
                    var parent=p.parent;
                    var ch=p;
                    while(parent!==null && ch===parent.left){
                        ch=parent;
                        parent=parent.parent;
                    }
                    return parent;
                }
            }
        }
    },
    //> public V put(K key,V value)
    put:function(key,value){
        var t=this.root;
        if(t===null){
            this.incrementSize();
            this.root=new this.vj$.TreeMap.Entry(key,value,null);
            return null;
        }
        while(true){
            var cmp=this.compare(key,t.key);
            if(cmp===0){
                return t.setValue(value);
            }else if(cmp<0){
                if(t.left!==null){
                    t=t.left;
                }else {
                    this.incrementSize();
                    t.left=new this.vj$.TreeMap.Entry(key,value,t);
                    this.fixAfterInsertion(t.left);
                    return null;
                }
            }else {
                if(t.right!==null){
                    t=t.right;
                }else {
                    this.incrementSize();
                    t.right=new this.vj$.TreeMap.Entry(key,value,t);
                    this.fixAfterInsertion(t.right);
                    return null;
                }
            }
        }
    },
    //> public void clear()
    clear:function(){
        this.modCount++;
        this.size_=0;
        this.root=null;
    },
    //> public Set<K> keySet()
    keySet:function(){
        if(this.keySet_===null){
            this.keySet_=
                vjo.make(this,this.vj$.AbstractSet)
                .protos({
                    iterator:function(){
                        return new this.vj$.parent.KeyIterator();
                    },
                    size:function(){
                        return this.vj$.parent.size();
                    },
                    clear:function(){
                        this.vj$.parent.clear();
                    },
                    contains:function(o){
                        if(arguments.length===1){
                            return this.contains_1_0_null_ovld(arguments[0]);
                         }else if(this.base && this.base.contains){
                            return this.base.contains.apply(this,arguments);
                        }
                    },
                    contains_1_0_null_ovld:function(o){
                        return this.vj$.parent.containsKey(o);
                    },
                    remove:function(o){
                        if(arguments.length===1){
                            return this.remove_1_0_null_ovld(arguments[0]);
                        }else if(this.base && this.base.remove){
                            return this.base.remove.apply(this,arguments);
                        }
                    },
                    remove_1_0_null_ovld:function(o){
                        var oldSize=this.vj$.parent.size_;
                        this.vj$.parent.remove(o);
                        return this.vj$.parent.size_!==oldSize;
                    }
                })
                .endType();
        }
        return this.keySet_;
    },
    //> public Collection<V> values()
    values:function(){
        if(this.values_===null){
            this.values_=
                vjo.make(this,vjo.java.util.AbstractCollection)
                .protos({
                    iterator:function(){
                        return new this.vj$.parent.ValueIterator();
                    },
                    size:function(){
                        return this.vj$.parent.size();
                    },
                    clear:function(){
                        this.vj$.parent.clear();
                    },
                    contains:function(o){
                        if(arguments.length===1){
                            return this.contains_1_0_null_ovld(arguments[0]);
                        }else if(this.base && this.base.contains){
                            return this.base.contains.apply(this,arguments);
                        }
                    },
                    contains_1_0_null_ovld:function(o){
                        for (var e=this.vj$.parent.firstEntry();e!==null;e=this.vj$.parent.successor(e)){
                            if(this.vj$.TreeMap.valEquals(e.getValue(),o)){
                                return true;
                            }
                        }
                        return false;
                    },
                    remove:function(o){
                        if(arguments.length===1){
                            return this.remove_1_0_null_ovld(arguments[0]);
                        }else if(this.base && this.base.remove){
                            return this.base.remove.apply(this,arguments);
                        }
                    },
                    remove_1_0_null_ovld:function(o){
                        for (var e=this.vj$.parent.firstEntry();e!==null;e=this.vj$.parent.successor(e)){
                            if(this.vj$.TreeMap.valEquals(e.getValue(),o)){
                                this.vj$.parent.deleteEntry(e);
                                return true;
                            }
                        }
                        return false;
                    }
                })
                .endType();
        }
        return this.values_;
    },
    //> public Set<Map<K,V>> entrySet()
    entrySet:function(){
        if(this.entrySet_===null){
            this.entrySet_=
                vjo.make(this,this.vj$.AbstractSet)
                .protos({
                    iterator:function(){
                        return new this.vj$.parent.EntryIterator();
                    },
                    size:function(){
                        return this.vj$.parent.size();
                    },
                    clear:function(){
                        this.vj$.parent.clear();
                    },
                    contains:function(o){
                        if(arguments.length===1){
                            return this.contains_1_0_null_ovld(arguments[0]);
                        }else if(this.base && this.base.contains){
                            return this.base.contains.apply(this,arguments);
                        }
                    },
                    contains_1_0_null_ovld:function(o){
                        if(!(vjo.getType('vjo.java.util.Map.Entry').clazz.isInstance(o))){
                            return false;
                        }
                        var entry=o;
                        var value=entry.getValue();
                        var p=this.vj$.parent.getEntry(entry.getKey());
                        return p!==null&&this.vj$.TreeMap.valEquals(p.getValue(),value);
                    },
                    remove:function(o){
                        if(arguments.length===1){
                            return this.remove_1_0_null_ovld(arguments[0]);
                        }else if(this.base && this.base.remove){
                            return this.base.remove.apply(this,arguments);
                        }
                    },
                    remove_1_0_null_ovld:function(o){
                        if(!(vjo.getType('vjo.java.util.Map.Entry').clazz.isInstance(o))){
                            return false;
                        }
                        var entry=o;
                        var value=entry.getValue();
                        var p=this.vj$.parent.getEntry(entry.getKey());
                        if(p!==null&&this.vj$.TreeMap.valEquals(p.getValue(),value)){
                            this.vj$.parent.deleteEntry(p);
                            return true;
                        }
                        return false;
                    }
                })
                .endType();
        }
        return this.entrySet_;
    },
    //> public SortedMap<K,V> subMap(K fromKey,K toKey)
    subMap:function(fromKey,toKey){
        return new this.vj$.TreeMap.SubMap(this,fromKey,toKey);
    },
    //> public SortedMap<K,V> headMap(K toKey)
    headMap:function(toKey){
        return new this.vj$.TreeMap.SubMap(this,toKey,true);
    },
    //> public SortedMap<K,V> tailMap(K fromKey)
    tailMap:function(fromKey){
        return new this.vj$.TreeMap.SubMap(this,fromKey,false);
    },
    //> private int compare(K k1,K k2)
    compare:function(k1,k2){
        //eBay Modification
        if(k1===null || k2 === null) {
            throw new this.vj$.NullPointerException();
        }

        return (this.comparator_===null?vjo.java.lang.ObjectUtil.compareTo(k1, k2):this.comparator_.compare(k1,k2));
    },
    //> private Entry<K,V> firstEntry()
    firstEntry:function(){
        var p=this.root;
        if(p!==null){
            while(p.left!==null){
                p=p.left;
            }
        }
        return p;
    },
    //> private Entry<K,V> lastEntry()
    lastEntry:function(){
        var p=this.root;
        if(p!==null){
            while(p.right!==null){
                p=p.right;
            }
        }
        return p;
    },
    //> private Entry<K,V> successor(Entry<K,V> t)
    successor:function(t){
        if(t===null){
            return null;
        }else if(t.right!==null){
            var p=t.right;
            while(p.left!==null){
                p=p.left;
            }
            return p;
        }else {
            var p=t.parent;
            var ch=t;
            while(p!==null && ch===p.right){
                ch=p;
                p=p.parent;
            }
            return p;
        }
    },
    //> private void rotateLeft(Entry<K,V> p)
    rotateLeft:function(p){
        var r=p.right;
        p.right=r.left;
        if(r.left!==null){
            r.left.parent=p;
        }
        r.parent=p.parent;
        if(p.parent===null){
            this.root=r;
        }else if(p.parent.left===p){
            p.parent.left=r;
        }else {
            p.parent.right=r;
        }
        r.left=p;
        p.parent=r;
    },
    //> private void rotateRight(Entry<K,V> p)
    rotateRight:function(p){
        var l=p.left;
        p.left=l.right;
        if(l.right!==null){
            l.right.parent=p;
        }
        l.parent=p.parent;
        if(p.parent===null){
            this.root=l;
        }else if(p.parent.right===p){
            p.parent.right=l;
        }else {
            p.parent.left=l;
        }
        l.right=p;
        p.parent=l;
    },
    //> private void fixAfterInsertion(Entry<K,V> x)
    fixAfterInsertion:function(x){
        x.color=this.vj$.TreeMap.RED;
        while(x!==null&&x!==this.root && x.parent.color===this.vj$.TreeMap.RED){
            if(this.vj$.TreeMap.parentOf(x)===this.vj$.TreeMap.leftOf(this.vj$.TreeMap.parentOf(this.vj$.TreeMap.parentOf(x)))){
                var y=this.vj$.TreeMap.rightOf(this.vj$.TreeMap.parentOf(this.vj$.TreeMap.parentOf(x)));
                if(this.vj$.TreeMap.colorOf(y)===this.vj$.TreeMap.RED){
                    this.vj$.TreeMap.setColor(this.vj$.TreeMap.parentOf(x),this.vj$.TreeMap.BLACK);
                    this.vj$.TreeMap.setColor(y,this.vj$.TreeMap.BLACK);
                    this.vj$.TreeMap.setColor(this.vj$.TreeMap.parentOf(this.vj$.TreeMap.parentOf(x)),this.vj$.TreeMap.RED);
                    x=this.vj$.TreeMap.parentOf(this.vj$.TreeMap.parentOf(x));
                }else {
                    if(x===this.vj$.TreeMap.rightOf(this.vj$.TreeMap.parentOf(x))){
                        x=this.vj$.TreeMap.parentOf(x);
                        this.rotateLeft(x);
                    }
                    this.vj$.TreeMap.setColor(this.vj$.TreeMap.parentOf(x),this.vj$.TreeMap.BLACK);
                    this.vj$.TreeMap.setColor(this.vj$.TreeMap.parentOf(this.vj$.TreeMap.parentOf(x)),this.vj$.TreeMap.RED);
                    if(this.vj$.TreeMap.parentOf(this.vj$.TreeMap.parentOf(x))!==null){
                        this.rotateRight(this.vj$.TreeMap.parentOf(this.vj$.TreeMap.parentOf(x)));
                    }
                }
            }else {
                var y=this.vj$.TreeMap.leftOf(this.vj$.TreeMap.parentOf(this.vj$.TreeMap.parentOf(x)));
                if(this.vj$.TreeMap.colorOf(y)===this.vj$.TreeMap.RED){
                    this.vj$.TreeMap.setColor(this.vj$.TreeMap.parentOf(x),this.vj$.TreeMap.BLACK);
                    this.vj$.TreeMap.setColor(y,this.vj$.TreeMap.BLACK);
                    this.vj$.TreeMap.setColor(this.vj$.TreeMap.parentOf(this.vj$.TreeMap.parentOf(x)),this.vj$.TreeMap.RED);
                    x=this.vj$.TreeMap.parentOf(this.vj$.TreeMap.parentOf(x));
                }else {
                    if(x===this.vj$.TreeMap.leftOf(this.vj$.TreeMap.parentOf(x))){
                        x=this.vj$.TreeMap.parentOf(x);
                        this.rotateRight(x);
                    }
                    this.vj$.TreeMap.setColor(this.vj$.TreeMap.parentOf(x),this.vj$.TreeMap.BLACK);
                    this.vj$.TreeMap.setColor(this.vj$.TreeMap.parentOf(this.vj$.TreeMap.parentOf(x)),this.vj$.TreeMap.RED);
                    if(this.vj$.TreeMap.parentOf(this.vj$.TreeMap.parentOf(x))!==null){
                        this.rotateLeft(this.vj$.TreeMap.parentOf(this.vj$.TreeMap.parentOf(x)));
                    }
                }
            }
        }
        this.root.color=this.vj$.TreeMap.BLACK;
    },
    //> private void deleteEntry(Entry<K,V> p)
    deleteEntry:function(p){
        this.decrementSize();
        if(p.left!==null&&p.right!==null){
            var s=this.successor(p);
            p.key=s.key;
            p.value=s.value;
            p=s;
        }
        var replacement=(p.left!==null?p.left:p.right);
        if(replacement!==null){
            replacement.parent=p.parent;
            if(p.parent===null){
                this.root=replacement;
            }else if(p===p.parent.left){
                p.parent.left=replacement;
            }else {
                p.parent.right=replacement;
            }
            p.left=p.right=p.parent=null;
            if(p.color===this.vj$.TreeMap.BLACK){
                this.fixAfterDeletion(replacement);
            }
        }else if(p.parent===null){
            this.root=null;
        }else {
            if(p.color===this.vj$.TreeMap.BLACK){
                this.fixAfterDeletion(p);
            }
            if(p.parent!==null){
                if(p===p.parent.left){
                    p.parent.left=null;
                }else if(p===p.parent.right){
                    p.parent.right=null;
                }
                p.parent=null;
            }
        }
    },
    //> private void fixAfterDeletion(Entry<K,V> x)
    fixAfterDeletion:function(x){
        while(x!==this.root && this.vj$.TreeMap.colorOf(x)===this.vj$.TreeMap.BLACK){
            if(x===this.vj$.TreeMap.leftOf(this.vj$.TreeMap.parentOf(x))){
                var sib=this.vj$.TreeMap.rightOf(this.vj$.TreeMap.parentOf(x));
                if(this.vj$.TreeMap.colorOf(sib)===this.vj$.TreeMap.RED){
                    this.vj$.TreeMap.setColor(sib,this.vj$.TreeMap.BLACK);
                    this.vj$.TreeMap.setColor(this.vj$.TreeMap.parentOf(x),this.vj$.TreeMap.RED);
                    this.rotateLeft(this.vj$.TreeMap.parentOf(x));
                    sib=this.vj$.TreeMap.rightOf(this.vj$.TreeMap.parentOf(x));
                }
                if(this.vj$.TreeMap.colorOf(this.vj$.TreeMap.leftOf(sib))===this.vj$.TreeMap.BLACK&&this.vj$.TreeMap.colorOf(this.vj$.TreeMap.rightOf(sib))===this.vj$.TreeMap.BLACK){
                    this.vj$.TreeMap.setColor(sib,this.vj$.TreeMap.RED);
                    x=this.vj$.TreeMap.parentOf(x);
                }else {
                    if(this.vj$.TreeMap.colorOf(this.vj$.TreeMap.rightOf(sib))===this.vj$.TreeMap.BLACK){
                        this.vj$.TreeMap.setColor(this.vj$.TreeMap.leftOf(sib),this.vj$.TreeMap.BLACK);
                        this.vj$.TreeMap.setColor(sib,this.vj$.TreeMap.RED);
                        this.rotateRight(sib);
                        sib=this.vj$.TreeMap.rightOf(this.vj$.TreeMap.parentOf(x));
                    }
                    this.vj$.TreeMap.setColor(sib,this.vj$.TreeMap.colorOf(this.vj$.TreeMap.parentOf(x)));
                    this.vj$.TreeMap.setColor(this.vj$.TreeMap.parentOf(x),this.vj$.TreeMap.BLACK);
                    this.vj$.TreeMap.setColor(this.vj$.TreeMap.rightOf(sib),this.vj$.TreeMap.BLACK);
                    this.rotateLeft(this.vj$.TreeMap.parentOf(x));
                    x=this.root;
                }
            }else {
                var sib=this.vj$.TreeMap.leftOf(this.vj$.TreeMap.parentOf(x));
                if(this.vj$.TreeMap.colorOf(sib)===this.vj$.TreeMap.RED){
                    this.vj$.TreeMap.setColor(sib,this.vj$.TreeMap.BLACK);
                    this.vj$.TreeMap.setColor(this.vj$.TreeMap.parentOf(x),this.vj$.TreeMap.RED);
                    this.rotateRight(this.vj$.TreeMap.parentOf(x));
                    sib=this.vj$.TreeMap.leftOf(this.vj$.TreeMap.parentOf(x));
                }
                if(this.vj$.TreeMap.colorOf(this.vj$.TreeMap.rightOf(sib))===this.vj$.TreeMap.BLACK&&this.vj$.TreeMap.colorOf(this.vj$.TreeMap.leftOf(sib))===this.vj$.TreeMap.BLACK){
                    this.vj$.TreeMap.setColor(sib,this.vj$.TreeMap.RED);
                    x=this.vj$.TreeMap.parentOf(x);
                }else {
                    if(this.vj$.TreeMap.colorOf(this.vj$.TreeMap.leftOf(sib))===this.vj$.TreeMap.BLACK){
                        this.vj$.TreeMap.setColor(this.vj$.TreeMap.rightOf(sib),this.vj$.TreeMap.BLACK);
                        this.vj$.TreeMap.setColor(sib,this.vj$.TreeMap.RED);
                        this.rotateLeft(sib);
                        sib=this.vj$.TreeMap.leftOf(this.vj$.TreeMap.parentOf(x));
                    }
                    this.vj$.TreeMap.setColor(sib,this.vj$.TreeMap.colorOf(this.vj$.TreeMap.parentOf(x)));
                    this.vj$.TreeMap.setColor(this.vj$.TreeMap.parentOf(x),this.vj$.TreeMap.BLACK);
                    this.vj$.TreeMap.setColor(this.vj$.TreeMap.leftOf(sib),this.vj$.TreeMap.BLACK);
                    this.rotateRight(this.vj$.TreeMap.parentOf(x));
                    x=this.root;
                }
            }
        }
        this.vj$.TreeMap.setColor(x,this.vj$.TreeMap.BLACK);
    },
    //> void addAllForTreeSet(SortedSet<Map<K,V>> set,V defaultVal)
    addAllForTreeSet:function(set,defaultVal){
        try {
            this.buildFromSorted(set.size(),set.iterator(),defaultVal);
        }
        catch(cannotHappen){
        }
    },
    //> final private void buildFromSorted(int size,Iterator it,V defaultVal)
    //> final private Entry<K,V> buildFromSorted(int level,int lo,int hi,int redLevel,Iterator it,V defaultVal)
    buildFromSorted:function(size,it,defaultVal){
        if(arguments.length===3){
            if(typeof arguments[0]=="number" && vjo.java.util.Iterator.clazz.isInstance(arguments[1])){
                this.buildFromSorted_3_0_TreeMap_ovld(arguments[0],arguments[1],arguments[2]);
            }else if(this.base && this.base.buildFromSorted){
                this.base.buildFromSorted.apply(this,arguments);
            }
        }else if(arguments.length===6){
            if(typeof arguments[0]=="number" && typeof arguments[1]=="number" && typeof arguments[2]=="number" && typeof arguments[3]=="number" && vjo.java.util.Iterator.clazz.isInstance(arguments[4])){
                return this.buildFromSorted_6_0_TreeMap_ovld(arguments[0],arguments[1],arguments[2],arguments[3],arguments[4],arguments[5]);
            }else if(this.base && this.base.buildFromSorted){
                this.base.buildFromSorted.apply(this,arguments);
            }
        }else if(this.base && this.base.buildFromSorted){
            this.base.buildFromSorted.apply(this,arguments);
        }
    },
    //> private void buildFromSorted_3_0_TreeMap_ovld(int size,Iterator it,V defaultVal)
    buildFromSorted_3_0_TreeMap_ovld:function(size,it,defaultVal){
        this.size_=size;
        this.root=this.buildFromSorted(0,0,size-1,this.vj$.TreeMap.computeRedLevel(size),it,defaultVal);
    },
    //> final private Entry<K,V> buildFromSorted_6_0_TreeMap_ovld(int level,int lo,int hi,int redLevel,Iterator it,V defaultVal)
    buildFromSorted_6_0_TreeMap_ovld:function(level,lo,hi,redLevel,it,defaultVal){
        if(hi<lo){
            return null;
        }
        var mid=parseInt((lo+hi)/2);
        var left=null;
        if(lo<mid){
            left=this.buildFromSorted(level+1,lo,mid-1,redLevel,it,defaultVal);
        }
        var key;
        var value;
        if(defaultVal===null){
            var entry=it.next();
            key=entry.getKey();
            value=entry.getValue();
        }else {
            key=it.next();
            value=defaultVal;
        }
        var middle=new this.vj$.TreeMap.Entry(key,value,null);
        if(level===redLevel){
            middle.color=this.vj$.TreeMap.RED;
        }
        if(left!==null){
            middle.left=left;
            left.parent=middle;
        }
        if(mid<hi){
            var right=this.buildFromSorted(level+1,mid+1,hi,redLevel,it,defaultVal);
            middle.right=right;
            right.parent=middle;
        }
        return middle;
    },
    //> public boolean containsKey(Object key)
    containsKey:function(key){
        if(arguments.length===1){
            return this.containsKey_1_0_TreeMap_ovld(arguments[0]);
        }else if(this.base && this.base.containsKey){
            return this.base.containsKey.apply(this,arguments);
        }
    },
    //> private boolean containsKey_1_0_TreeMap_ovld(Object key)
    containsKey_1_0_TreeMap_ovld:function(key){
        return this.getEntry(key)!==null;
    },
    //> public boolean containsValue(Object value)
    containsValue:function(value){
        if(arguments.length===1){
            return this.containsValue_1_0_TreeMap_ovld(arguments[0]);
        }else if(this.base && this.base.containsValue){
            return this.base.containsValue.apply(this,arguments);
        }
    },
    //> private boolean containsValue_1_0_TreeMap_ovld(Object value)
    containsValue_1_0_TreeMap_ovld:function(value){
        return (this.root===null?false:(value===null?this.valueSearchNull(this.root):this.valueSearchNonNull(this.root,value)));
    },
    //> public V get(Object key)
    get:function(key){
        if(arguments.length===1){
            return this.get_1_0_TreeMap_ovld(arguments[0]);
        }else if(this.base && this.base.get){
            return this.base.get.apply(this,arguments);
        }
    },
    //> private V get_1_0_TreeMap_ovld(Object key)
    get_1_0_TreeMap_ovld:function(key){
        var p=this.getEntry(key);
        return (p===null?null:p.value);
    },
    //> public V remove(Object key)
    remove:function(key){
        if(arguments.length===1){
            return this.remove_1_0_TreeMap_ovld(arguments[0]);
        }else if(this.base && this.base.remove){
            return this.base.remove.apply(this,arguments);
        }
    },
    //> private V remove_1_0_TreeMap_ovld(Object key)
    remove_1_0_TreeMap_ovld:function(key){
        var p=this.getEntry(key);
        if(p===null){
            return null;
        }
        var oldValue=p.value;
        this.deleteEntry(p);
        return oldValue;
    }
})
.endType();