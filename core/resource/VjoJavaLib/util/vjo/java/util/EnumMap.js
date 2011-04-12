vjo.ctype('vjo.java.util.EnumMap<K extends Enum,V>') //< public
.needs(['vjo.java.lang.IllegalArgumentException','vjo.java.lang.ClassCastException',
    'vjo.java.lang.IllegalStateException','vjo.java.util.Map',
    'vjo.java.util.Set','vjo.java.util.AbstractSet',
    'vjo.java.util.Iterator','vjo.java.util.Collection',
    'vjo.java.util.AbstractCollection','vjo.java.util.NoSuchElementException',
    'vjo.java.lang.ClassUtil','vjo.java.lang.ObjectUtil'])
.needs('vjo.java.util.Arrays','')
.needs('vjo.java.lang.reflect.Array','')
.inherits('vjo.java.util.AbstractMap<K,V>')
.satisfies('vjo.java.io.Serializable')
.satisfies('vjo.java.lang.Cloneable')
//> needs(vjo.Enum)
.props({
    NULL:null, //< private final Object
    ZERO_LENGTH_ENUM_ARRAY:null, //< private vjo.Enum[]
    serialVersionUID:458661240069192865, //< private final long
    EnumMapIterator:vjo.ctype() //< public abstract EnumMapIterator<K extends Enum,V,T>
    .satisfies('vjo.java.util.Iterator<T>')
    .protos({
        index:0, //< int
        lastReturnedIndex:-1, //< int
        map:null, //< EnumMap<K,V> map
        //> public constructs(EnumMap<K,V> map)
        constructs:function(map){
            this.map=map;
        },
        //> public boolean hasNext()
        hasNext:function(){
            while(this.index<this.map.vals.length && this.map.vals[this.index]===null){
                this.index++;
            }
            return this.index!==this.map.vals.length;
        },
        //> public void remove()
        remove:function(){
            this.checkLastReturnedIndex();
            if(this.map.vals[this.lastReturnedIndex]!==null){
                this.map.vals[this.lastReturnedIndex]=null;
                this.map.size_--;
            }
            this.lastReturnedIndex=-1;
        },
        //> private void checkLastReturnedIndex()
        checkLastReturnedIndex:function(){
            if(this.lastReturnedIndex<0){
                throw new vjo.java.lang.IllegalStateException();
            }
        }
    })
    .endType()
})
.protos({
    keyType:null, //< private final vjo.Class<K> keyType
    keyUniverse:null, //< private K[]
    vals:null, //< private com.ebay.dsf.jsnative.global.Object[]
    size_:0, //< private int
    entrySet_:null, //< private Set<Entry<K,V>> entrySet_
    KeySet:vjo.ctype() //< private
    .inherits('vjo.java.util.AbstractSet<K>')
    .protos({
        //> public Iterator<K> iterator()
        iterator:function(){
            return new this.vj$.outer.KeyIterator(this.vj$.outer);
        },
        //> public int size()
        size:function(){
            return this.vj$.outer.size_;
        },
        //> public void clear()
        clear:function(){
            this.vj$.outer.clear();
        },
        //> public boolean contains(Object o)
        contains:function(o){
            if(arguments.length===1){
                if(arguments[0] instanceof Object){
                    return this.contains_1_0_KeySet_ovld(arguments[0]);
                }else if(this.base && this.base.contains){
                    return this.base.contains.apply(this,arguments);
                }
            }else if(this.base && this.base.contains){
                return this.base.contains.apply(this,arguments);
            }
        },
        //> private boolean contains_1_0_KeySet_ovld(Object o)
        contains_1_0_KeySet_ovld:function(o){
            return this.vj$.outer.containsKey(o);
        },
        //> public boolean remove(Object o)
        remove:function(o){
            if(arguments.length===1){
                if(arguments[0] instanceof Object){
                    return this.remove_1_0_KeySet_ovld(arguments[0]);
                }else if(this.base && this.base.remove){
                    return this.base.remove.apply(this,arguments);
                }
            }else if(this.base && this.base.remove){
                return this.base.remove.apply(this,arguments);
            }
        },
        //> private boolean remove_1_0_KeySet_ovld(Object o)
        remove_1_0_KeySet_ovld:function(o){
            var oldSize=this.vj$.outer.size_; //<int
            this.vj$.outer.remove(o);
            return this.vj$.outer.size_!==oldSize;
        }
    })
    .endType(),
    Values:vjo.ctype() //< private
    .inherits('vjo.java.util.AbstractCollection<V>')
    .protos({
        //> public Iterator<V> iterator()
        iterator:function(){
            return new this.vj$.outer.ValueIterator(this.vj$.outer);
        },
        //> public int size()
        size:function(){
            return this.vj$.outer.size_;
        },
        //> public void clear()
        clear:function(){
            this.vj$.outer.clear();
        },
        //> public boolean contains(Object o)
        contains:function(o){
            if(arguments.length===1){
                if(arguments[0] instanceof Object){
                    return this.contains_1_0_Values_ovld(arguments[0]);
                }else if(this.base && this.base.contains){
                    return this.base.contains.apply(this,arguments);
                }
            }else if(this.base && this.base.contains){
                return this.base.contains.apply(this,arguments);
            }
        },
        //> private boolean contains_1_0_Values_ovld(Object o)
        contains_1_0_Values_ovld:function(o){
            return this.vj$.outer.containsValue(o);
        },
        //> public boolean remove(Object o)
        remove:function(o){
            if(arguments.length===1){
                if(arguments[0] instanceof Object){
                    return this.remove_1_0_Values_ovld(arguments[0]);
                }else if(this.base && this.base.remove){
                    return this.base.remove.apply(this,arguments);
                }
            }else if(this.base && this.base.remove){
                return this.base.remove.apply(this,arguments);
            }
        },
        //> private boolean remove_1_0_Values_ovld(Object o)
        remove_1_0_Values_ovld:function(o){
            o=this.vj$.outer.maskNull(o);
            for (var i=0;i<this.vj$.outer.vals.length;i++){
                if(vjo.java.lang.ObjectUtil.equals(o,this.vj$.outer.vals[i])){
                    this.vj$.outer.vals[i]=null;
                    this.vj$.outer.size_--;
                    return true;
                }
            }
            return false;
        }
    })
    .endType(),
    EntrySet:vjo.ctype() //< private
    .inherits('vjo.java.util.AbstractSet<Entry>')
    .protos({
        //> public Iterator<Entry<K,V>> iterator()
        iterator:function(){
            return new this.vj$.outer.EntryIterator(this.vj$.outer);
        },
        //> public int size()
        size:function(){
            return this.vj$.outer.size_;
        },
        //> public void clear()
        clear:function(){
            this.vj$.outer.clear();
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
            return this.fillEntryArray(vjo.createArray(null, this.vj$.outer.size_));
        },
        //> private <T> T[] toArray_1_0_EntrySet_ovld(T[] a)
        toArray_1_0_EntrySet_ovld:function(a){
            var result=java.lang.reflect.Array.newInstance(a.getClass().getComponentType(),this.vj$.outer.size_); //<<Object[]
            return this.fillEntryArray(result);
        },
        //> private Object[] fillEntryArray(com.ebay.dsf.jsnative.global.Object[] a)
        fillEntryArray:function(a){
            var j=0; //<int
            for (var i=0;i<this.vj$.outer.vals.length;i++){
                if(this.vj$.outer.vals[i]!==null){
                    a[j++]=new this.vj$.AbstractMap.SimpleEntry(this.vj$.outer.keyUniverse[i],this.vj$.outer.unmaskNull(this.vj$.outer.vals[i]));
                }
            }
            return a;
        },
        //> public boolean contains(Object o)
        contains:function(o){
            if(arguments.length===1){
                if(arguments[0] instanceof Object){
                    return this.contains_1_0_EntrySet_ovld(arguments[0]);
                }else if(this.base && this.base.contains){
                    return this.base.contains.apply(this,arguments);
                }
            }else if(this.base && this.base.contains){
                return this.base.contains.apply(this,arguments);
            }
        },
        //> private boolean contains_1_0_EntrySet_ovld(Object o)
        contains_1_0_EntrySet_ovld:function(o){
            if(!(vjo.java.util.Map.Entry.clazz.isInstance(o))){
                return false;
            }
            var entry=o; //<<Entry
            return this.vj$.outer.containsMapping(entry.getKey(),entry.getValue());
        },
        //> public boolean remove(Object o)
        remove:function(o){
            if(arguments.length===1){
                if(arguments[0] instanceof Object){
                    return this.remove_1_0_EntrySet_ovld(arguments[0]);
                }else if(this.base && this.base.remove){
                    return this.base.remove.apply(this,arguments);
                }
            }else if(this.base && this.base.remove){
                return this.base.remove.apply(this,arguments);
            }
        },
        //> private boolean remove_1_0_EntrySet_ovld(Object o)
        remove_1_0_EntrySet_ovld:function(o){
            if(!(vjo.java.util.Map.Entry.clazz.isInstance(o))){
                return false;
            }
            var entry=o; //<<Entry
            return this.vj$.outer.removeMapping(entry.getKey(),entry.getValue());
        }
    })
    .endType(),
    KeyIterator:vjo.ctype() //< private
    .inherits('vjo.java.util.EnumMap.EnumMapIterator<K,V,K>')
    .protos({
        //> public constructs(EnumMap<K,V> map)
        constructs:function(map){
            this.base(map);
        },
        //> public K next()
        next:function(){
            if(!this.hasNext()){
                throw new this.vj$.NoSuchElementException();
            }
            this.lastReturnedIndex=this.index++;
            return this.map.keyUniverse[this.lastReturnedIndex];
        }
    })
    .endType(),
    ValueIterator:vjo.ctype() //< private
    .inherits('vjo.java.util.EnumMap.EnumMapIterator<K,V,V>')
    .protos({
        //> public constructs(EnumMap<K,V> map)
        constructs:function(map){
            this.base(map);
        },
        //> public V next()
        next:function(){
            if(!this.hasNext()){
                throw new this.vj$.NoSuchElementException();
            }
            this.lastReturnedIndex=this.index++;
            return this.map.unmaskNull(this.map.vals[this.lastReturnedIndex]);
        }
    })
    .endType(),
    EntryIterator:vjo.ctype() //< private
    .inherits('vjo.java.util.EnumMap.EnumMapIterator<K,V,Entry>')
    .satisfies('vjo.java.util.Map.Entry<K,V>')
    .protos({
        //> public constructs(EnumMap<K,V> map)
        constructs:function(map){
            this.base(map);
        },
        //> public Entry<K,V> next()
        next:function(){
            if(!this.hasNext()){
                throw new this.vj$.NoSuchElementException();
            }
            this.lastReturnedIndex=this.index++;
            return this;
        },
        //> public K getKey()
        getKey:function(){
            this.checkLastReturnedIndexForEntryUse();
            return this.map.keyUniverse[this.lastReturnedIndex];
        },
        //> public V getValue()
        getValue:function(){
            this.checkLastReturnedIndexForEntryUse();
            return this.map.unmaskNull(this.map.vals[this.lastReturnedIndex]);
        },
        //> public V setValue(V value)
        setValue:function(value){
            this.checkLastReturnedIndexForEntryUse();
            var oldValue=this.map.unmaskNull(this.map.vals[this.lastReturnedIndex]); //<V
            this.map.vals[this.lastReturnedIndex]=this.map.maskNull(value);
            return oldValue;
        },
        //> public boolean equals(Object o)
        equals:function(o){
            if(this.lastReturnedIndex<0){
                return o===this;
            }
            if(!(vjo.java.util.Map.Entry.clazz.isInstance(o))){
                return false;
            }
            var e=o; //<<Entry
            var ourValue=this.map.unmaskNull(this.map.vals[this.lastReturnedIndex]); //<V
            var hisValue=e.getValue(); //<Object
            return e.getKey()===this.map.keyUniverse[this.lastReturnedIndex]&&(ourValue===hisValue||(ourValue!==null&&ourValue.equals(hisValue)));
        },
        //> public int hashCode()
        hashCode:function(){
            if(this.lastReturnedIndex<0){
                return vjo.java.lang.ObjectUtil.hashCode(this.base);
            }
            var value=this.map.vals[this.lastReturnedIndex]; //<Object
            return this.map.keyUniverse[this.lastReturnedIndex].hashCode()^(value===vjo.java.util.EnumMap.NULL?0:vjo.java.lang.ObjectUtil.hashCode(value));
        },
        //> public String toString()
        toString:function(){
            if(this.lastReturnedIndex<0){
                return this.base.toString();
            }
            return this.map.keyUniverse[this.lastReturnedIndex]+"="+this.map.unmaskNull(this.map.vals[this.lastReturnedIndex]);
        },
        //> private void checkLastReturnedIndexForEntryUse()
        checkLastReturnedIndexForEntryUse:function(){
            if(this.lastReturnedIndex<0){
                throw new vjo.java.lang.IllegalStateException("Entry was removed");
            }
        }
    })
    .endType(),
    //> public constructs()
    //> public constructs(vjo.Class<K> keyType)
    //> public constructs(EnumMap<K,? extends V> m)
    //> public constructs(Map<K,? extends V> m)
    constructs:function(){
        if(arguments.length===1){
            if(arguments[0] instanceof vjo.Class){
                this.constructs_1_0_EnumMap_ovld(arguments[0]);
            }else if(arguments[0] instanceof vjo.java.util.EnumMap){
                this.constructs_1_1_EnumMap_ovld(arguments[0]);
            }else if(vjo.java.util.Map.clazz.isInstance(arguments[0])){
                this.constructs_1_2_EnumMap_ovld(arguments[0]);
            }
        }
    },
    //> private Object maskNull(Object value)
    maskNull:function(value){
        return (value===null?this.vj$.EnumMap.NULL:value);
    },
    //> private V unmaskNull(Object value)
    unmaskNull:function(value){
        return (value===this.vj$.EnumMap.NULL?null:value);
    },
    //> private constructs_1_0_EnumMap_ovld(vjo.Class<K> keyType)
    constructs_1_0_EnumMap_ovld:function(keyType){
        this.base();
        this.keyType=keyType;
        this.keyUniverse=vjo.java.lang.ClassUtil.getEnumConstants(keyType);
        this.vals=vjo.createArray(null, this.keyUniverse.length);
    },
    //> private constructs_1_1_EnumMap_ovld(EnumMap<K,? extends V> m)
    constructs_1_1_EnumMap_ovld:function(m){
        this.base();
        this.keyType=m.keyType;
        this.keyUniverse=m.keyUniverse;
        this.vals=this.cloneArray(m.vals);
        this.size_=m.size_;
    },
    //> private constructs_1_2_EnumMap_ovld(Map<K,? extends V> m)
    constructs_1_2_EnumMap_ovld:function(m){
        this.base();
        if(vjo.java.util.EnumMap.clazz.isInstance(m)){
            var em=m; //<<EnumMap
            this.keyType=em.keyType;
            this.keyUniverse=em.keyUniverse;
            this.vals=this.cloneArray(em.vals);
            this.size_=em.size_;
        }else {
            if(m.isEmpty()){
                throw new this.vj$.IllegalArgumentException("Specified map is empty");
            }
            this.keyType=m.keySet().iterator().next().getDeclaringClass();
            this.keyUniverse=vjo.java.lang.ClassUtil.getEnumConstants(this.keyType);
            this.vals=vjo.createArray(null, this.keyUniverse.length);
            this.putAll(m);
        }
    },
    //> public int size()
    size:function(){
        return this.size_;
    },
    //> private boolean containsMapping(Object key,Object value)
    containsMapping:function(key,value){
        return this.isValidKey(key)&&vjo.java.lang.ObjectUtil.equals(this.maskNull(value),this.vals[key.ordinal()]);
    },
    //> public V put(K key,V value)
    put:function(key,value){
        this.typeCheck(key);
        var index=key.ordinal(); //<int
        var oldValue=this.vals[index]; //<Object
        this.vals[index]=this.maskNull(value);
        if(oldValue===null){
            this.size_++;
        }
        return this.unmaskNull(oldValue);
    },
    //> private boolean removeMapping(Object key,Object value)
    removeMapping:function(key,value){
        if(!this.isValidKey(key)){
            return false;
        }
        var index=key.ordinal(); //<int
        if(vjo.java.lang.ObjectUtil.equals(this.maskNull(value),this.vals[index])){
            this.vals[index]=null;
            this.size_--;
            return true;
        }
        return false;
    },
    //> private boolean isValidKey(Object key)
    isValidKey:function(key){
        if(key===null){
            return false;
        }
        var keyClass=key.getClass(); //<Class
        return keyClass===this.keyType||vjo.java.lang.ClassUtil.getSuperclass(keyClass)===this.keyType;
    },
    //> public void putAll(Map<? extends K,? extends V> m)
    putAll:function(m){
        if(vjo.java.util.EnumMap.clazz.isInstance(m)){
            var em=m; //<<EnumMap
            if(em.keyType!==this.keyType){
                if(em.isEmpty()){
                    return;
                }
                throw new this.vj$.ClassCastException(em.keyType+" != "+this.keyType);
            }
            for (var i=0;i<this.keyUniverse.length;i++){
                var emValue=em.vals[i]; //<Object
                if(emValue!==null){
                    if(this.vals[i]===null){
                        this.size_++;
                    }
                    this.vals[i]=emValue;
                }
            }
        }else {
            this.base.putAll(m);
        }
    },
    //> public void clear()
    clear:function(){
        vjo.java.util.Arrays.fill(this.vals,null);
        this.size_=0;
    },
    //> public Set<K> keySet()
    keySet:function(){
        var ks=this.keySet_; //<Set
        if(ks!==null){
            return ks;
        }else {
            return this.keySet_=new this.KeySet();
        }
    },
    //> public Collection<V> values()
    values:function(){
        var vs=this.values_; //<Collection
        if(vs!==null){
            return vs;
        }else {
            return this.values_=new this.Values();
        }
    },
    //> public Set<Entry<K,V>> entrySet()
    entrySet:function(){
        var es=this.entrySet_; //<Set
        if(es!==null){
            return es;
        }else {
            return this.entrySet_=new this.EntrySet();
        }
    },
    //> private void typeCheck(K key)
    typeCheck:function(key){
        var keyClass=key.getClass(); //<Class
        if(keyClass!==this.keyType&&vjo.java.lang.ClassUtil.getSuperclass(keyClass)!==this.keyType){
            throw new this.vj$.ClassCastException(keyClass+" != "+this.keyType);
        }
    },
    //> private Object[] cloneArray(com.ebay.dsf.jsnative.global.Object[] a)
    cloneArray:function(a){
        var b=vjo.createArray(null, a.length); //<Object[]
        for (var i=0;i<a.length;i++){
            b[i]=a[i];
        }
        return b;
    },
    //> public boolean containsValue(Object value)
    containsValue:function(value){
        if(arguments.length===1){
            return this.containsValue_1_0_EnumMap_ovld(arguments[0]);
        }else if(this.base && this.base.containsValue){
            return this.base.containsValue.apply(this,arguments);
        }
    },
    //> private boolean containsValue_1_0_EnumMap_ovld(Object value)
    containsValue_1_0_EnumMap_ovld:function(value){
        value=this.maskNull(value);
        for (var val,_$i=0;_$i<this.vals.length;_$i++){
            val=this.vals[_$i];
            if(vjo.java.lang.ObjectUtil.equals(value,val)){
                return true;
            }
        }
        return false;
    },
    //> public boolean containsKey(Object key)
    containsKey:function(key){
        if(arguments.length===1){
            if(arguments[0] instanceof Object){
                return this.containsKey_1_0_EnumMap_ovld(arguments[0]);
            }else if(this.base && this.base.containsKey){
                return this.base.containsKey.apply(this,arguments);
            }
        }else if(this.base && this.base.containsKey){
            return this.base.containsKey.apply(this,arguments);
        }
    },
    //> private boolean containsKey_1_0_EnumMap_ovld(Object key)
    containsKey_1_0_EnumMap_ovld:function(key){
        return this.isValidKey(key)&&this.vals[key.ordinal()]!==null;
    },
    //> public V get(Object key)
    get:function(key){
        if(arguments.length===1){
            if(arguments[0] instanceof Object){
                return this.get_1_0_EnumMap_ovld(arguments[0]);
            }else if(this.base && this.base.get){
                return this.base.get.apply(this,arguments);
            }
        }else if(this.base && this.base.get){
            return this.base.get.apply(this,arguments);
        }
    },
    //> private V get_1_0_EnumMap_ovld(Object key)
    get_1_0_EnumMap_ovld:function(key){
        return (this.isValidKey(key)?this.unmaskNull(this.vals[key.ordinal()]):null);
    },
    //> public V remove(Object key)
    remove:function(key){
        if(arguments.length===1){
            if(arguments[0] instanceof Object){
                return this.remove_1_0_EnumMap_ovld(arguments[0]);
            }else if(this.base && this.base.remove){
                return this.base.remove.apply(this,arguments);
            }
        }else if(this.base && this.base.remove){
            return this.base.remove.apply(this,arguments);
        }
    },
    //> private V remove_1_0_EnumMap_ovld(Object key)
    remove_1_0_EnumMap_ovld:function(key){
        if(!this.isValidKey(key)){
            return null;
        }
        var index=key.ordinal(); //<int
        var oldValue=this.vals[index]; //<Object
        this.vals[index]=null;
        if(oldValue!==null){
            this.size_--;
        }
        return this.unmaskNull(oldValue);
    },
    //> public boolean equals(Object o)
    equals:function(o){
        if(arguments.length===1){
            if(arguments[0] instanceof Object){
                return this.equals_1_0_EnumMap_ovld(arguments[0]);
            }else if(this.base && this.base.equals){
                return this.base.equals.apply(this,arguments);
            }
        }else if(this.base && this.base.equals){
            return this.base.equals.apply(this,arguments);
        }
    },
    //> private boolean equals_1_0_EnumMap_ovld(Object o)
    equals_1_0_EnumMap_ovld:function(o){
        if(!(vjo.java.util.EnumMap.clazz.isInstance(o))){
            return vjo.java.lang.ObjectUtil.equals(this.base,o);
        }
        var em=o; //<<EnumMap
        if(em.keyType!==this.keyType){
            return this.size_===0&&em.size_===0;
        }
        for (var i=0;i<this.keyUniverse.length;i++){
            var ourValue=this.vals[i]; //<Object
            var hisValue=em.vals[i]; //<Object
            if(hisValue!==ourValue&&(hisValue===null|| !vjo.java.lang.ObjectUtil.equals(hisValue,ourValue))){
                return false;
            }
        }
        return true;
    }
})
.inits(function(){
    this.vj$.EnumMap.NULL=new Object();
    this.vj$.EnumMap.ZERO_LENGTH_ENUM_ARRAY=vjo.createArray(null, 0);
})
.endType();
