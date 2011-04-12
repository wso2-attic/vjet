vjo.ctype('vjo.java.util.AbstractMap<K,V>') //< public abstract
.needs(['vjo.java.lang.UnsupportedOperationException','vjo.java.lang.NullPointerException',
    'vjo.java.lang.StringBuffer','vjo.java.util.AbstractSet',
    'vjo.java.lang.ClassCastException','vjo.java.lang.ObjectUtil'])
.needs('vjo.java.util.Iterator','')
.needs('vjo.java.util.Set','')
.needs('vjo.java.util.Collection','')
.needs('vjo.java.util.AbstractCollection','')
.needs('vjo.java.util.Map','')
.satisfies('vjo.java.util.Map<K,V>')
.props({
    SimpleEntry:vjo.ctype() //< SimpleEntry<K,V>
    .satisfies('vjo.java.util.Map.Entry<K,V>')
    .props({
        //> private boolean eq(Object o1,Object o2)
        eq:function(o1,o2){
            return (o1===null?o2===null:vjo.java.lang.ObjectUtil.equals(o1,o2));
        }
    })
    .protos({
        key:null, //< K
        value:null, //< V
        //> public constructs()
        //> public constructs(K key,V value)
        //> public constructs(Entry<K,V> e)
        constructs:function(){
            if(arguments.length===2){
                this.constructs_2_0_SimpleEntry_ovld(arguments[0],arguments[1]);
            }else if(arguments.length===1){
                this.constructs_1_0_SimpleEntry_ovld(arguments[0]);
            }
        },
        //> private constructs_2_0_SimpleEntry_ovld(K key,V value)
        constructs_2_0_SimpleEntry_ovld:function(key,value){
            this.key=key;
            this.value=value;
        },
        //> private constructs_1_0_SimpleEntry_ovld(Entry<K,V> e)
        constructs_1_0_SimpleEntry_ovld:function(e){
            this.key=e.getKey();
            this.value=e.getValue();
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
            if(!(vjo.java.util.Map.clazz.isInstance(o))){
                return false;
            }
            var e=o;
            return this.vj$.AbstractMap.SimpleEntry.eq(this.key,e.getKey())
              &&this.vj$.AbstractMap.SimpleEntry.eq(this.value,e.getValue());
        },
        //> public int hashCode()
        hashCode:function(){
            return ((this.key===null)?0:this.key.hashCode())^((this.value===null)?0:this.value.hashCode());
        },
        //> public String toString()
        toString:function(){
            return this.key+"="+this.value;
        }
    })
    .endType()
})
.protos({
    keySet_:null, //< Set<K> keySet_
    values_:null, //< Collection<V> values_
    //> protected constructs()
    constructs:function(){
    },
    //> public int size()
    size:function(){
        return this.entrySet().size();
    },
    //> public boolean isEmpty()
    isEmpty:function(){
        return this.size()===0;
    },
    //> public boolean containsValue(Object value)
    containsValue:function(value){
        var i=this.entrySet().iterator();
        if(value===null){
            while(i.hasNext()){
                var e=i.next();
                if(e.getValue()===null){
                    return true;
                }
            }
        }else {
            while(i.hasNext()){
                var e=i.next();
                if(vjo.java.lang.ObjectUtil.equals(value,e.getValue())){
                    return true;
                }
            }
        }
        return false;
    },
    //> public boolean containsKey(Object key)
    containsKey:function(key){
        var i=this.entrySet().iterator();
        if(key===null){
            while(i.hasNext()){
                var e=i.next();
                if(e.getKey()===null){
                    return true;
                }
            }
        }else {
            while(i.hasNext()){
                var e=i.next();
                if(vjo.java.lang.ObjectUtil.equals(key,e.getKey())){
                    return true;
                }
            }
        }
        return false;
    },
    //> public V get(Object key)
    get:function(key){
        var i=this.entrySet().iterator();
        if(key===null){
            while(i.hasNext()){
                var e=i.next();
                if(e.getKey()===null){
                    return e.getValue();
                }
            }
        }else {
            while(i.hasNext()){
                var e=i.next();
                if(vjo.java.lang.ObjectUtil.equals(key,e.getKey())){
                    return e.getValue();
                }
            }
        }
        return null;
    },
    //> public V put(K key,V value)
    put:function(key,value){
        throw new this.vj$.UnsupportedOperationException();
    },
    //> public V remove(Object key)
    remove:function(key){
        var i=this.entrySet().iterator();
        var correctEntry=null;
        if(key===null){
            while(correctEntry===null && i.hasNext()){
                var e=i.next();
                if(e.getKey()===null){
                    correctEntry=e;
                }
            }
        }else {
            while(correctEntry===null && i.hasNext()){
                var e=i.next();
                if(vjo.java.lang.ObjectUtil.equals(key,e.getKey())){
                    correctEntry=e;
                }
            }
        }
        var oldValue=null;
        if(correctEntry!==null){
            oldValue=correctEntry.getValue();
            i.remove();
        }
        return oldValue;
    },
    //> public void putAll(Map<? extends K,? extends V> t)
    putAll:function(t){
        var i=t.entrySet().iterator();
        while(i.hasNext()){
            var e=i.next();
            this.put(e.getKey(),e.getValue());
        }
    },
    //> public void clear()
    clear:function(){
        this.entrySet().clear();
    },
    //> public Set<K> keySet()
    keySet:function(){
        if(this.keySet_===null){
            this.keySet_=
                vjo.make(this,this.vj$.AbstractSet)
                .protos({
                    iterator:function(){
                        return vjo.make(this,vjo.java.util.Iterator)
                            .protos({
                                i:null,
                                constructs:function(){
                                    this.i=this.entrySet().iterator();
                                },
                                hasNext:function(){
                                    return this.i.hasNext();
                                },
                                next:function(){
                                    return this.i.next().getKey();
                                },
                                remove:function(){
                                    this.i.remove();
                                }
                            })
                            .endType();
                    },
                    size:function(){
                        return this.vj$.parent.size();
                    },
                    contains:function(k){
                        return this.vj$.parent.containsKey(k);
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
                        return vjo.make(this,vjo.java.util.Iterator)
                            .protos({
                                i:null,
                                constructs:function(){
                                    this.i=this.entrySet().iterator();
                                },
                                hasNext:function(){
                                    return this.i.hasNext();
                                },
                                next:function(){
                                    return this.i.next().getValue();
                                },
                                remove:function(){
                                    this.i.remove();
                                }
                            })
                            .endType();
                    },
                    size:function(){
                        return this.vj$.parent.size();
                    },
                    contains:function(v){
                        return this.vj$.parent.containsValue(v);
                    }
                })
                .endType();
        }
        return this.values_;
    },
    //> public abstract Set<Entry<K,V>> entrySet()
    entrySet:function(){
    },
    //> public boolean equals(Object o)
    equals:function(o){
        if(o===this){
            return true;
        }
        if(!(vjo.java.util.Map.clazz.isInstance(o))){
            return false;
        }
        var t=o;
        if(t.size()!==this.size()){
            return false;
        }
        try {
            var i=this.entrySet().iterator();
            while(i.hasNext()){
                var e=i.next();
                var key=e.getKey();
                var value=e.getValue();
                if(value===null){
                    if(!(t.get(key)===null&&t.containsKey(key))){
                        return false;
                    }
                }else {
                    if(!vjo.java.lang.ObjectUtil.equals(value, t.get(key))){
                        return false;
                    }
                }
            }
        }
        catch(unused){
            if(unused instanceof this.vj$.ClassCastException){
                return false;
            }else if(unused instanceof this.vj$.NullPointerException){
                return false;
            }
        }
        return true;
    },
    //> public int hashCode()
    hashCode:function(){
        var h=0;
        var i=this.entrySet().iterator();
        while(i.hasNext()){
            h+=i.next().hashCode();
        }
        return h;
    },
    //> public String toString()
    toString:function(){
        var buf=new vjo.java.lang.StringBuffer();
        buf.append("{");
        var i=this.entrySet().iterator();
        var hasNext=i.hasNext();
        while(hasNext){
            var e=i.next();
            var key=e.getKey();
            var value=e.getValue();
            if(key===this){
                buf.append("(this Map)");
            }else {
                buf.append(key);
            }
            buf.append("=");
            if(value===this){
                buf.append("(this Map)");
            }else {
                buf.append(value);
            }
            hasNext=i.hasNext();
            if(hasNext){
                buf.append(", ");
            }
        }
        buf.append("}");
        return buf.toString();
    }
})
.endType();