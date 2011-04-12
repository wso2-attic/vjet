vjo.ctype('vjo.java.util.Collections') //< public
.needs(['vjo.java.lang.Comparable',
    'vjo.java.io.Serializable','vjo.java.lang.NullPointerException',
    'vjo.java.lang.UnsupportedOperationException','vjo.java.util.NoSuchElementException',
    'vjo.java.lang.ClassCastException',
    'vjo.java.lang.IndexOutOfBoundsException',
    'vjo.java.lang.IllegalArgumentException',
    'vjo.java.lang.System',
    'vjo.java.lang.reflect.Array',
    'vjo.java.util.RandomAccess',
    'vjo.java.util.List',
    'vjo.java.util.Map',
    'vjo.java.util.AbstractSet','vjo.java.util.Iterator',
    'vjo.java.util.AbstractList','vjo.java.util.AbstractMap',
    'vjo.java.lang.ObjectUtil', 'vjo.java.util.Random'])
.needs('vjo.java.util.Arrays','')
.needs('vjo.java.util.Iterator','')
.needs('vjo.java.util.Comparator','')
.needs('vjo.java.util.Collection','')
.needs('vjo.java.util.Set','')
.needs('vjo.java.util.SortedSet','')
.needs('vjo.java.util.SortedMap','')
.needs('vjo.java.util.Enumeration','')
.needs('vjo.java.util.ArrayList','')
.props({
    BINARYSEARCH_THRESHOLD:5000, //< private final int
    REVERSE_THRESHOLD:18, //< private final int
    SHUFFLE_THRESHOLD:5, //< private final int
    FILL_THRESHOLD:25, //< private final int
    ROTATE_THRESHOLD:100, //< private final int
    COPY_THRESHOLD:10, //< private final int
    REPLACEALL_THRESHOLD:11, //< private final int
    INDEXOFSUBLIST_THRESHOLD:35, //< private final int
    EMPTY_SET:null, //< public final Set
    EMPTY_LIST:null, //< public final List
    EMPTY_MAP:null, //< public final Map
    REVERSE_ORDER:null, //< private final Comparator
    SelfComparable:vjo.itype() //< public
    .inherits('vjo.java.lang.Comparable<SelfComparable>')
    .endType(),
    UnmodifiableCollection:vjo.ctype() //< public UnmodifiableCollection<E>
    .satisfies('vjo.java.util.Collection<E>')
    .satisfies('vjo.java.io.Serializable')
    .props({
        serialVersionUID:1820017752578914078 //< private final long
    })
    .protos({
        c:null, //< Collection<? extends E> c
        //> constructs(Collection<? extends E> c)
        constructs:function(c){
            if(c===null){
                throw new this.vj$.NullPointerException();
            }
            this.c=c;
        },
        //> public int size()
        size:function(){
            return this.c.size();
        },
        //> public boolean isEmpty()
        isEmpty:function(){
            return this.c.isEmpty();
        },
        //> public boolean contains(Object o)
        contains:function(o){
            return this.c.contains(o);
        },
        //> public Object[] toArray()
        //> public <T> T[] toArray(T[] a)
        toArray:function(){
            if(arguments.length===0){
                return this.toArray_0_0_UnmodifiableCollection_ovld();
            }else if(arguments.length===1){
                return this.toArray_1_0_UnmodifiableCollection_ovld(arguments[0]);
            }
        },
        //> private Object[] toArray_0_0_UnmodifiableCollection_ovld()
        toArray_0_0_UnmodifiableCollection_ovld:function(){
            return this.c.toArray();
        },
        //> private <T> T[] toArray_1_0_UnmodifiableCollection_ovld(T[] a)
        toArray_1_0_UnmodifiableCollection_ovld:function(a){
            return this.c.toArray(a);
        },
        //> public String toString()
        toString:function(){
            return this.c.toString();
        },
        //> public Iterator<E> iterator()
        iterator:function(){
            return vjo.make(this,this.vj$.Iterator)
                .protos({
                    i:null,
                    constructs:function(){
                        this.i=this.vj$.parent.c.iterator();
                    },
                    hasNext:function(){
                        return this.i.hasNext();
                    },
                    next:function(){
                        return this.i.next();
                    },
                    remove:function(){
                        throw new this.vj$.UnsupportedOperationException();
                    }
                })
                .endType();
        },
        //> public boolean add(E o)
        add:function(o){
            throw new this.vj$.UnsupportedOperationException();
        },
        //> public boolean remove(Object o)
        remove:function(o){
            throw new this.vj$.UnsupportedOperationException();
        },
        //> public boolean containsAll(Collection<?> coll)
        containsAll:function(coll){
            return this.c.containsAll(coll);
        },
        //> public boolean addAll(Collection<? extends E> coll)
        addAll:function(coll){
            throw new this.vj$.UnsupportedOperationException();
        },
        //> public boolean removeAll(Collection<?> coll)
        removeAll:function(coll){
            throw new this.vj$.UnsupportedOperationException();
        },
        //> public boolean retainAll(Collection<?> coll)
        retainAll:function(coll){
            throw new this.vj$.UnsupportedOperationException();
        },
        //> public void clear()
        clear:function(){
            throw new this.vj$.UnsupportedOperationException();
        }
    })
    .endType(),
    UnmodifiableSet:vjo.ctype() //< public UnmodifiableSet<E>
    .inherits('vjo.java.util.Collections.UnmodifiableCollection<E>')
    .satisfies('vjo.java.util.Set<E>')
    .satisfies('vjo.java.io.Serializable')
    .props({
        serialVersionUID:-9215047833775013803 //< private final long
    })
    .protos({
        //> constructs(Set<? extends E> s)
        constructs:function(s){
            this.base(s);
        },
        //> public boolean equals(Object o)
        equals:function(o){
            return vjo.java.lang.ObjectUtil.equals(this.c,o);
        },
        //> public int hashCode()
        hashCode:function(){
            return vjo.java.lang.ObjectUtil.hashCode(this.c);
        }
    })
    .endType(),
    UnmodifiableSortedSet:vjo.ctype() //< public UnmodifiableSortedSet<E>
    .inherits('vjo.java.util.Collections.UnmodifiableSet<E>')
    .satisfies('vjo.java.util.SortedSet<E>')
    .satisfies('vjo.java.io.Serializable')
    .props({
        serialVersionUID:-4929149591599911165 //< private final long
    })
    .protos({
        ss:null, //< private SortedSet<E> ss
        //> constructs(SortedSet<E> s)
        constructs:function(s){
            this.base(s);
            this.ss=s;
        },
        //> public Comparator<? super E> comparator()
        comparator:function(){
            return this.ss.comparator();
        },
        //> public SortedSet<E> subSet(E fromElement,E toElement)
        subSet:function(fromElement,toElement){
            return new this.vj$.UnmodifiableSortedSet(this.ss.subSet(fromElement,toElement));
        },
        //> public SortedSet<E> headSet(E toElement)
        headSet:function(toElement){
            return new this.vj$.UnmodifiableSortedSet(this.ss.headSet(toElement));
        },
        //> public SortedSet<E> tailSet(E fromElement)
        tailSet:function(fromElement){
            return new this.vj$.UnmodifiableSortedSet(this.ss.tailSet(fromElement));
        },
        //> public E first()
        first:function(){
            return this.ss.first();
        },
        //> public E last()
        last:function(){
            return this.ss.last();
        }
    })
    .endType(),
    UnmodifiableList:vjo.ctype() //< public UnmodifiableList<E>
    .inherits('vjo.java.util.Collections.UnmodifiableCollection<E>')
    .satisfies('vjo.java.util.List<E>')
    .props({
        serialVersionUID:-283967356065247728 //< final long
    })
    .protos({
        list:null, //< List<? extends E> list
        //> constructs(List<? extends E> list)
        constructs:function(list){
            this.base(list);
            this.list=list;
        },
        //> public boolean equals(Object o)
        equals:function(o){
            return this.list.equals(o);
        },
        //> public int hashCode()
        hashCode:function(){
            return this.list.hashCode();
        },
        //> public E get(int index)
        get:function(index){
            return this.list.get(index);
        },
        //> public E set(int index,E element)
        set:function(index,element){
            throw new this.vj$.UnsupportedOperationException();
        },
        //> public int indexOf(Object o)
        indexOf:function(o){
            return this.list.indexOf(o);
        },
        //> public int lastIndexOf(Object o)
        lastIndexOf:function(o){
            return this.list.lastIndexOf(o);
        },
        //> public Iterator<E> listIterator()
        //> public Iterator<E> listIterator(final int index)
        listIterator:function(){
            if(arguments.length===0){
                if(arguments.length==0){
                    return this.listIterator_0_0_UnmodifiableList_ovld();
                }else if(this.base && this.base.listIterator){
                    return this.base.listIterator.apply(this,arguments);
                }
            }else if(arguments.length===1){
                if(typeof arguments[0]=="number"){
                    return this.listIterator_1_0_UnmodifiableList_ovld(arguments[0]);
                }else if(this.base && this.base.listIterator){
                    return this.base.listIterator.apply(this,arguments);
                }
            }else if(this.base && this.base.listIterator){
                return this.base.listIterator.apply(this,arguments);
            }
        },
        //> private Iterator<E> listIterator_0_0_UnmodifiableList_ovld()
        listIterator_0_0_UnmodifiableList_ovld:function(){
            return this.listIterator(0);
        },
        //> private Iterator<E> listIterator_1_0_UnmodifiableList_ovld(final int index)
        listIterator_1_0_UnmodifiableList_ovld:function(index){
            return vjo.make(this,this.vj$.Iterator)
                .protos({
                    i:null,
                    constructs:function(){
                        this.i=this.vj$.parent.list.listIterator(index);
                    },
                    hasNext:function(){
                        return this.i.hasNext();
                    },
                    next:function(){
                        return this.i.next();
                    },
                    hasPrevious:function(){
                        return this.i.hasPrevious();
                    },
                    previous:function(){
                        return this.i.previous();
                    },
                    nextIndex:function(){
                        return this.i.nextIndex();
                    },
                    previousIndex:function(){
                        return this.i.previousIndex();
                    },
                    remove:function(){
                        throw new this.vj$.UnsupportedOperationException();
                    },
                    set:function(o){
                        throw new this.vj$.UnsupportedOperationException();
                    },
                    add:function(o){
                        throw new this.vj$.UnsupportedOperationException();
                    }
                })
                .endType();
        },
        //> public List<E> subList(int fromIndex,int toIndex)
        subList:function(fromIndex,toIndex){
            return new this.vj$.UnmodifiableList(this.list.subList(fromIndex,toIndex));
        },
        //> private Object readResolve()
        readResolve:function(){
            return (this.vj$.RandomAccess.isInstance(this.list)?new this.vj$.Collections.UnmodifiableRandomAccessList(this.list):this);
        },
        //> public void add(int index,E element)
        add:function(index,element){
            if(arguments.length===2){
                if(typeof arguments[0]=="number" && arguments[1] instanceof Object){
                    this.add_2_0_UnmodifiableList_ovld(arguments[0],arguments[1]);
                }else if(this.base && this.base.add){
                    this.base.add.apply(this,arguments);
                }
            }else if(this.base && this.base.add){
                this.base.add.apply(this,arguments);
            }
        },
        //> private void add_2_0_UnmodifiableList_ovld(int index,E element)
        add_2_0_UnmodifiableList_ovld:function(index,element){
            throw new this.vj$.UnsupportedOperationException();
        },
        //> public E remove(int index)
        remove:function(index){
            if(arguments.length===1){
                if(typeof arguments[0]=="number"){
                    return this.remove_1_0_UnmodifiableList_ovld(arguments[0]);
                }else if(this.base && this.base.remove){
                    return this.base.remove.apply(this,arguments);
                }
            }else if(this.base && this.base.remove){
                return this.base.remove.apply(this,arguments);
            }
        },
        //> private E remove_1_0_UnmodifiableList_ovld(int index)
        remove_1_0_UnmodifiableList_ovld:function(index){
            throw new this.vj$.UnsupportedOperationException();
        },
        //> public boolean addAll(int index,Collection<? extends E> c)
        addAll:function(index,c){
            if(arguments.length===2){
                if(typeof arguments[0]=="number" && vjo.java.util.Collection.isInstance(arguments[1])){
                    return this.addAll_2_0_UnmodifiableList_ovld(arguments[0],arguments[1]);
                }else if(this.base && this.base.addAll){
                    return this.base.addAll.apply(this,arguments);
                }
            }else if(this.base && this.base.addAll){
                return this.base.addAll.apply(this,arguments);
            }
        },
        //> private boolean addAll_2_0_UnmodifiableList_ovld(int index,Collection<? extends E> c)
        addAll_2_0_UnmodifiableList_ovld:function(index,c){
            throw new this.vj$.UnsupportedOperationException();
        }
    })
    .endType(),
    UnmodifiableRandomAccessList:vjo.ctype() //< public UnmodifiableRandomAccessList<E>
    .inherits('vjo.java.util.Collections.UnmodifiableList<E>')
    .satisfies('vjo.java.util.RandomAccess')
    .props({
        serialVersionUID:-2542308836966382001 //< private final long
    })
    .protos({
        //> constructs(List<? extends E> list)
        constructs:function(list){
            this.base(list);
        },
        //> public List<E> subList(int fromIndex,int toIndex)
        subList:function(fromIndex,toIndex){
            return new this.vj$.UnmodifiableRandomAccessList(this.vj$.list.subList(fromIndex,toIndex));
        },
        //> private Object writeReplace()
        writeReplace:function(){
            return new this.vj$.Collections.UnmodifiableList(this.vj$.list);
        }
    })
    .endType(),
    UnmodifiableMap:vjo.ctype() //< public UnmodifiableMap<K,V>
    .satisfies('vjo.java.util.Map<K,V>')
    .satisfies('vjo.java.io.Serializable')
    .props({
        serialVersionUID:-1034234728574286014, //< private final long
        UnmodifiableEntrySet:vjo.ctype() //< public UnmodifiableEntrySet<K,V>
        .inherits('vjo.java.util.Collections.UnmodifiableSet<vjo.java.util.Map.Entry>')
        .props({
            serialVersionUID:7854390611657943733, //< private final long
            UnmodifiableEntry:vjo.ctype() //< public UnmodifiableEntry<K,V>
            .satisfies('vjo.java.util.Map.Entry<K,V>')
            .protos({
                e:null, //< private vjo.java.util.Map.Entry<? extends K,? extends V> e
                //> constructs(vjo.java.util.Map.Entry<? extends K,? extends V> e)
                constructs:function(e){
                    this.e=e;
                },
                //> public K getKey()
                getKey:function(){
                    return this.e.getKey();
                },
                //> public V getValue()
                getValue:function(){
                    return this.e.getValue();
                },
                //> public V setValue(V value)
                setValue:function(value){
                    throw new this.vj$.UnsupportedOperationException();
                },
                //> public int hashCode()
                hashCode:function(){
                    return vjo.java.lang.ObjectUtil.hashCode(this.e);
                },
                //> public boolean equals(Object o)
                equals:function(o){
                    if(!(vjo.getType('vjo.java.util.Map').isInstance(o))){
                        return false;
                    }
                    var t=o;
                    return eq(this.e.getKey(),t.getKey())&&eq(this.e.getValue(),t.getValue());
                },
                //> public String toString()
                toString:function(){
                    return this.e.toString();
                }
            })
            .endType()
        })
        .protos({
            //> constructs(Set<? extends Map> s)
            constructs:function(s){
                this.base(s);
            },
            //> public Iterator<Map<K,V>> iterator()
            iterator:function(){
                return vjo.make(this,this)
                    .protos({
                        i:null,
                        constructs:function(){
                            this.i=this.vj$.parent.c.iterator();
                        },
                        hasNext:function(){
                            return this.i.hasNext();
                        },
                        next:function(){
                            return new this.vj$.Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableEntry(this.i.next());
                        },
                        remove:function(){
                            throw new this.vj$.parent();
                        }
                    })
                    .endType();
            },
            //> public Object[] toArray()
            //> public <T> T[] toArray(T[] a)
            toArray:function(){
                if(arguments.length===0){
                    if(arguments.length==0){
                        return this.toArray_0_0_UnmodifiableEntrySet_ovld();
                    }else if(this.base && this.base.toArray){
                        return this.base.toArray.apply(this,arguments);
                    }
                }else if(arguments.length===1){
                    if(arguments[0] instanceof Array){
                        return this.toArray_1_0_UnmodifiableEntrySet_ovld(arguments[0]);
                    }else if(this.base && this.base.toArray){
                        return this.base.toArray.apply(this,arguments);
                    }
                }else if(this.base && this.base.toArray){
                    return this.base.toArray.apply(this,arguments);
                }
            },
            //> private Object[] toArray_0_0_UnmodifiableEntrySet_ovld()
            toArray_0_0_UnmodifiableEntrySet_ovld:function(){
                var a=this.c.toArray();
                for (var i=0;i<a.length;i++){
                    a[i]=new this.vj$.UnmodifiableEntrySet.UnmodifiableEntry(a[i]);
                }
                return a;
            },
            //> private <T> T[] toArray_1_0_UnmodifiableEntrySet_ovld(T[] a)
            toArray_1_0_UnmodifiableEntrySet_ovld:function(a){
                var arr=this.c.toArray(a.length===0?a:vjo.java.lang.reflect.Array.newInstance(a.getClass().getComponentType(),0));
                for (var i=0;i<arr.length;i++){
                    arr[i]=new this.vj$.UnmodifiableEntrySet.UnmodifiableEntry(arr[i]);
                }
                if(arr.length>a.length){
                    return arr;
                }
                this.vj$.System.arraycopy(arr,0,a,0,arr.length);
                if(a.length>arr.length){
                    a[arr.length]=null;
                }
                return a;
            },
            //> public boolean contains(Object o)
            contains:function(o){
                if(!(vjo.getType('vjo.java.util.Map').isInstance(o))){
                    return false;
                }
                return this.c.contains(new this.vj$.UnmodifiableEntrySet.UnmodifiableEntry(o));
            },
            //> public boolean containsAll(Collection<?> coll)
            containsAll:function(coll){
                var e=coll.iterator();
                while(e.hasNext()){
                    if(!this.contains(e.next())){
                        return false;
                    }
                }
                return true;
            },
            //> public boolean equals(Object o)
            equals:function(o){
                if(o===this){
                    return true;
                }
                if(!(vjo.getType('vjo.java.util.Set').isInstance(o))){
                    return false;
                }
                var s=o;
                if(s.size()!==this.c.size()){
                    return false;
                }
                return containsAll(s);
            }
        })
        .endType()
    })
    .protos({
        m:null, //< private final Map<? extends K,? extends V> m
        keySet_:null, //< private Set<K> keySet_
        entrySet_:null, //< private Set<Map<K,V>> entrySet_
        values_:null, //< private Collection<V> values_
        //> constructs(Map<? extends K,? extends V> m)
        constructs:function(m){
            if(m===null){
                throw new this();
            }
            this.m=m;
        },
        //> public int size()
        size:function(){
            return this.m.size();
        },
        //> public boolean isEmpty()
        isEmpty:function(){
            return this.m.isEmpty();
        },
        //> public boolean containsKey(Object key)
        containsKey:function(key){
            return this.m.containsKey(key);
        },
        //> public boolean containsValue(Object val)
        containsValue:function(val){
            return this.m.containsValue(val);
        },
        //> public V get(Object key)
        get:function(key){
            return this.m.get(key);
        },
        //> public V put(K key,V value)
        put:function(key,value){
            throw new this();
        },
        //> public V remove(Object key)
        remove:function(key){
            throw new this();
        },
        //> public void putAll(Map<? extends K,? extends V> t)
        putAll:function(t){
            throw new this();
        },
        //> public void clear()
        clear:function(){
            throw new this();
        },
        //> public Set<K> keySet()
        keySet:function(){
            if(this.keySet_===null){
                this.keySet_=this.vj$.Collections.unmodifiableSet(this.m.keySet());
            }
            return this.keySet_;
        },
        //> public Set<Map<K,V>> entrySet()
        entrySet:function(){
            if(this.entrySet_===null){
                this.entrySet_=new this.vj$.UnmodifiableMap.UnmodifiableEntrySet(this.m.entrySet());
            }
            return this.entrySet_;
        },
        //> public Collection<V> values()
        values:function(){
            if(this.values_===null){
                this.values_=this.vj$.Collections.unmodifiableCollection(this.m.values());
            }
            return this.values_;
        },
        //> public boolean equals(Object o)
        equals:function(o){
            return vjo.java.lang.ObjectUtil.equals(this.m,o);
        },
        //> public int hashCode()
        hashCode:function(){
            return vjo.java.lang.ObjectUtil.hashCode(this.m);
        },
        //> public String toString()
        toString:function(){
            return this.m.toString();
        }
    })
    .endType(),
    UnmodifiableSortedMap:vjo.ctype() //< public UnmodifiableSortedMap<K,V>
    .inherits('vjo.java.util.Collections.UnmodifiableMap<K,V>')
    .satisfies('vjo.java.util.SortedMap<K,V>')
    .satisfies('vjo.java.io.Serializable')
    .props({
        serialVersionUID:-8806743815996713206 //< private final long
    })
    .protos({
        sm:null, //< private SortedMap<K,? extends V> sm
        //> constructs(SortedMap<K,? extends V> m)
        constructs:function(m){
            this.base(m);
            this.sm=m;
        },
        //> public Comparator<? super K> comparator()
        comparator:function(){
            return this.sm.comparator();
        },
        //> public SortedMap<K,V> subMap(K fromKey,K toKey)
        subMap:function(fromKey,toKey){
            return new this.vj$.UnmodifiableSortedMap(this.sm.subMap(fromKey,toKey));
        },
        //> public SortedMap<K,V> headMap(K toKey)
        headMap:function(toKey){
            return new this.vj$.UnmodifiableSortedMap(this.sm.headMap(toKey));
        },
        //> public SortedMap<K,V> tailMap(K fromKey)
        tailMap:function(fromKey){
            return new this.vj$.UnmodifiableSortedMap(this.sm.tailMap(fromKey));
        },
        //> public K firstKey()
        firstKey:function(){
            return this.sm.firstKey();
        },
        //> public K lastKey()
        lastKey:function(){
            return this.sm.lastKey();
        }
    })
    .endType(),
    CheckedCollection:vjo.ctype() //< public CheckedCollection<E>
    .satisfies('vjo.java.util.Collection<E>')
    .satisfies('vjo.java.io.Serializable')
    .props({
        serialVersionUID:1578914078182001775 //< private final long
    })
    .protos({
        c:null, //< final Collection<E> c
        type:null, //< final vjo.Class<E> type
        zeroLengthElementArray_:null, //< private E[]
        //> constructs(Collection<E> c,vjo.Class<E> type)
        constructs:function(c,type){
            if(c===null||type===null){
                throw new this.vj$.NullPointerException();
            }
            this.c=c;
            this.type=type;
        },
        //> void typeCheck(Object o)
        typeCheck:function(o){
            if(!this.type.isInstance(o)){
                throw new this.vj$.ClassCastException("Attempt to insert "+o.getClass()+" element into collection with element type "+this.type);
            }
        },
        //> public int size()
        size:function(){
            return this.c.size();
        },
        //> public boolean isEmpty()
        isEmpty:function(){
            return this.c.isEmpty();
        },
        //> public boolean contains(Object o)
        contains:function(o){
            return this.c.contains(o);
        },
        //> public Object[] toArray()
        //> public <T> T[] toArray(T[] a)
        toArray:function(){
            if(arguments.length===0){
                return this.toArray_0_0_CheckedCollection_ovld();
            }else if(arguments.length===1){
                return this.toArray_1_0_CheckedCollection_ovld(arguments[0]);
            }
        },
        //> private Object[] toArray_0_0_CheckedCollection_ovld()
        toArray_0_0_CheckedCollection_ovld:function(){
            return this.c.toArray();
        },
        //> private <T> T[] toArray_1_0_CheckedCollection_ovld(T[] a)
        toArray_1_0_CheckedCollection_ovld:function(a){
            return this.c.toArray(a);
        },
        //> public String toString()
        toString:function(){
            return this.c.toString();
        },
        //> public Iterator<E> iterator()
        iterator:function(){
            return this.c.iterator();
        },
        //> public boolean remove(Object o)
        remove:function(o){
            return this.c.remove(o);
        },
        //> public boolean containsAll(Collection<?> coll)
        containsAll:function(coll){
            return this.c.containsAll(coll);
        },
        //> public boolean removeAll(Collection<?> coll)
        removeAll:function(coll){
            return this.c.removeAll(coll);
        },
        //> public boolean retainAll(Collection<?> coll)
        retainAll:function(coll){
            return this.c.retainAll(coll);
        },
        //> public void clear()
        clear:function(){
            this.c.clear();
        },
        //> public boolean add(E o)
        add:function(o){
            this.typeCheck(o);
            return this.c.add(o);
        },
        //> public boolean addAll(Collection<? extends E> coll)
        addAll:function(coll){
            var a=null;
            try {
                a=coll.toArray(this.zeroLengthElementArray());
            }
            catch(e){
                throw new vjo.java.lang.ClassCastException();
            }
            var result=false;
            for (var e,_$i=0;_$i<a.length;_$i++){
                e=a[_$i];
                result|=this.c.add(e);
            }
            return result;
        },
        //> E[] zeroLengthElementArray()
        zeroLengthElementArray:function(){
            if(this.zeroLengthElementArray_===null){
                this.zeroLengthElementArray_=this.vj$.Array.newInstance(this.type,0);
            }
            return this.zeroLengthElementArray_;
        }
    })
    .endType(),
    CheckedSet:vjo.ctype() //< public CheckedSet<E>
    .inherits('vjo.java.util.Collections.CheckedCollection<E>')
    .satisfies('vjo.java.util.Set<E>')
    .satisfies('vjo.java.io.Serializable')
    .props({
        serialVersionUID:4694047833775013803 //< private final long
    })
    .protos({
        //> constructs(Set<E> s,vjo.Class<E> elementType)
        constructs:function(s,elementType){
            this.base(s,elementType);
        },
        //> public boolean equals(Object o)
        equals:function(o){
            return vjo.java.lang.ObjectUtil.equals(this.c,o);
        },
        //> public int hashCode()
        hashCode:function(){
            return vjo.java.lang.ObjectUtil.hashCode(this.c);
        }
    })
    .endType(),
    CheckedSortedSet:vjo.ctype() //< public CheckedSortedSet<E>
    .inherits('vjo.java.util.Collections.CheckedSet<E>')
    .satisfies('vjo.java.util.SortedSet<E>')
    .satisfies('vjo.java.io.Serializable')
    .props({
        serialVersionUID:1599911165492914959 //< private final long
    })
    .protos({
        ss:null, //< private final SortedSet<E> ss
        //> constructs(SortedSet<E> s,vjo.Class<E> type)
        constructs:function(s,type){
            this.base(s,type);
            this.ss=s;
        },
        //> public Comparator<? super E> comparator()
        comparator:function(){
            return this.ss.comparator();
        },
        //> public E first()
        first:function(){
            return this.ss.first();
        },
        //> public E last()
        last:function(){
            return this.ss.last();
        },
        //> public SortedSet<E> subSet(E fromElement,E toElement)
        subSet:function(fromElement,toElement){
            return new this.vj$.CheckedSortedSet(this.ss.subSet(fromElement,toElement),this.type);
        },
        //> public SortedSet<E> headSet(E toElement)
        headSet:function(toElement){
            return new this.vj$.CheckedSortedSet(this.ss.headSet(toElement),this.type);
        },
        //> public SortedSet<E> tailSet(E fromElement)
        tailSet:function(fromElement){
            return new this.vj$.CheckedSortedSet(this.ss.tailSet(fromElement),this.type);
        }
    })
    .endType(),
    CheckedList:vjo.ctype() //< public CheckedList<E>
    .inherits('vjo.java.util.Collections.CheckedCollection<E>')
    .satisfies('vjo.java.util.List<E>')
    .props({
        serialVersionUID:65247728283967356 //< final long
    })
    .protos({
        list:null, //< final List<E> list
        //> constructs(List<E> list,vjo.Class<E> type)
        constructs:function(list,type){
            this.base(list,type);
            this.vj$.list=list;
        },
        //> public boolean equals(Object o)
        equals:function(o){
            return this.vj$.list.equals(o);
        },
        //> public int hashCode()
        hashCode:function(){
            return this.vj$.list.hashCode();
        },
        //> public E get(int index)
        get:function(index){
            return this.vj$.list.get(index);
        },
        //> public int indexOf(Object o)
        indexOf:function(o){
            return this.vj$.list.indexOf(o);
        },
        //> public int lastIndexOf(Object o)
        lastIndexOf:function(o){
            return this.vj$.list.lastIndexOf(o);
        },
        //> public E set(int index,E element)
        set:function(index,element){
            this.typeCheck(element);
            return this.vj$.list.set(index,element);
        },
        //> public Iterator<E> listIterator()
        //> public Iterator<E> listIterator(final int index)
        listIterator:function(){
            if(arguments.length===0){
                if(arguments.length==0){
                    return this.listIterator_0_0_CheckedList_ovld();
                }else if(this.base && this.base.listIterator){
                    return this.base.listIterator.apply(this,arguments);
                }
            }else if(arguments.length===1){
                if(typeof arguments[0]=="number"){
                    return this.listIterator_1_0_CheckedList_ovld(arguments[0]);
                }else if(this.base && this.base.listIterator){
                    return this.base.listIterator.apply(this,arguments);
                }
            }else if(this.base && this.base.listIterator){
                return this.base.listIterator.apply(this,arguments);
            }
        },
        //> private Iterator<E> listIterator_0_0_CheckedList_ovld()
        listIterator_0_0_CheckedList_ovld:function(){
            return this.listIterator(0);
        },
        //> private Iterator<E> listIterator_1_0_CheckedList_ovld(final int index)
        listIterator_1_0_CheckedList_ovld:function(index){
            return vjo.make(this,this.vj$.Iterator)
                .protos({
                    i:null,
                    constructs:function(){
                        this.i=this.vj$.list.listIterator(index);
                    },
                    hasNext:function(){
                        return this.i.hasNext();
                    },
                    next:function(){
                        return this.i.next();
                    },
                    hasPrevious:function(){
                        return this.i.hasPrevious();
                    },
                    previous:function(){
                        return this.i.previous();
                    },
                    nextIndex:function(){
                        return this.i.nextIndex();
                    },
                    previousIndex:function(){
                        return this.i.previousIndex();
                    },
                    remove:function(){
                        this.i.remove();
                    },
                    set:function(o){
                        this.vj$.parent.typeCheck(o);
                        this.i.set(o);
                    },
                    add:function(o){
                    	this.vj$.parent.typeCheck(o);
                        this.i.add(o);
                    }
                })
                .endType();
        },
        //> public List<E> subList(int fromIndex,int toIndex)
        subList:function(fromIndex,toIndex){
            return new this.vj$.CheckedList(this.vj$.list.subList(fromIndex,toIndex),this.type);
        },
        //> public E remove(int index)
        remove:function(index){
            if(arguments.length===1){
                if(typeof arguments[0]=="number"){
                    return this.remove_1_0_CheckedList_ovld(arguments[0]);
                }else if(this.base && this.base.remove){
                    return this.base.remove.apply(this,arguments);
                }
            }else if(this.base && this.base.remove){
                return this.base.remove.apply(this,arguments);
            }
        },
        //> private E remove_1_0_CheckedList_ovld(int index)
        remove_1_0_CheckedList_ovld:function(index){
            return this.vj$.list.remove(index);
        },
        //> public void add(int index,E element)
        add:function(index,element){
            if(arguments.length===2){
                if(typeof arguments[0]=="number" && arguments[1] instanceof Object){
                    this.add_2_0_CheckedList_ovld(arguments[0],arguments[1]);
                }else if(this.base && this.base.add){
                    this.base.add.apply(this,arguments);
                }
            }else if(this.base && this.base.add){
                this.base.add.apply(this,arguments);
            }
        },
        //> private void add_2_0_CheckedList_ovld(int index,E element)
        add_2_0_CheckedList_ovld:function(index,element){
            this.typeCheck(element);
            this.vj$.list.add(index,element);
        },
        //> public boolean addAll(int index,Collection<? extends E> c)
        addAll:function(index,c){
            if(arguments.length===2){
                if(typeof arguments[0]=="number" && vjo.java.util.Collection.isInstance(arguments[1])){
                    return this.addAll_2_0_CheckedList_ovld(arguments[0],arguments[1]);
                }else if(this.base && this.base.addAll){
                    return this.base.addAll.apply(this,arguments);
                }
            }else if(this.base && this.base.addAll){
                return this.base.addAll.apply(this,arguments);
            }
        },
        //> private boolean addAll_2_0_CheckedList_ovld(int index,Collection<? extends E> c)
        addAll_2_0_CheckedList_ovld:function(index,c){
            var a=null;
            try {
                a=c.toArray(this.zeroLengthElementArray());
            }
            catch(e){
                throw new vjo.java.lang.ClassCastException();
            }
            return this.vj$.list.addAll(index,vjo.java.util.Arrays.asList(a));
        }
    })
    .endType(),
    CheckedRandomAccessList:vjo.ctype() //< public CheckedRandomAccessList<E>
    .inherits('vjo.java.util.Collections.CheckedList<E>')
    .satisfies('vjo.java.util.RandomAccess')
    .props({
        serialVersionUID:1638200125423088369 //< private final long
    })
    .protos({
        //> constructs(List<E> list,vjo.Class<E> type)
        constructs:function(list,type){
            this.base(list,type);
        },
        //> public List<E> subList(int fromIndex,int toIndex)
        subList:function(fromIndex,toIndex){
            return new this.vj$.CheckedRandomAccessList(this.vj$.list.subList(fromIndex,toIndex),this.type);
        }
    })
    .endType(),
    CheckedMap:vjo.ctype() //< public CheckedMap<K,V>
    .satisfies('vjo.java.util.Map<K,V>')
    .satisfies('vjo.java.io.Serializable')
    .props({
        serialVersionUID:5742860141034234728, //< private final long
        CheckedEntrySet:vjo.ctype() //< public CheckedEntrySet<K,V>
        .satisfies('vjo.java.util.Set<Map.Entry<K,V>>')
        .props({
            CheckedEntry:vjo.ctype() //< public CheckedEntry<K,V>
            .satisfies('vjo.java.util.Map.Entry<K,V>')
            .protos({
                e:null, //< private vjo.java.util.Map.Entry<K,V> e
                valueType:null, //< private vjo.Class<V> valueType
                //> constructs(vjo.java.util.Map.Entry<K,V> e,vjo.Class<V> valueType)
                constructs:function(e,valueType){
                    this.e=e;
                    this.valueType=valueType;
                },
                //> public K getKey()
                getKey:function(){
                    return this.e.getKey();
                },
                //> public V getValue()
                getValue:function(){
                    return this.e.getValue();
                },
                //> public int hashCode()
                hashCode:function(){
                    return vjo.java.lang.ObjectUtil.hashCode(this.e);
                },
                //> public String toString()
                toString:function(){
                    return this.e.toString();
                },
                //> public V setValue(V value)
                setValue:function(value){
                    if(!this.valueType.isInstance(value)){
                        throw new this.vj$.ClassCastException("Attempt to insert "+value.getClass()+" value into collection with value type "+this.valueType);
                    }
                    return this.e.setValue(value);
                },
                //> public boolean equals(Object o)
                equals:function(o){
                    if(!(vjo.getType('vjo.java.util.Map').isInstance(o))){
                        return false;
                    }
                    var t=o;
                    return eq(this.e.getKey(),t.getKey())&&eq(this.e.getValue(),t.getValue());
                }
            })
            .endType()
        })
        .protos({
            s:null, //< Set<Map<K,V>> s
            valueType:null, //< vjo.Class<V> valueType
            //> constructs(Set<Map<K,V>> s,vjo.Class<V> valueType)
            constructs:function(s,valueType){
                this.s=s;
                this.valueType=valueType;
            },
            //> public int size()
            size:function(){
                return this.s.size();
            },
            //> public boolean isEmpty()
            isEmpty:function(){
                return this.s.isEmpty();
            },
            //> public String toString()
            toString:function(){
                return this.s.toString();
            },
            //> public int hashCode()
            hashCode:function(){
                return vjo.java.lang.ObjectUtil.hashCode(this.s);
            },
            //> public boolean remove(Object o)
            remove:function(o){
                return this.s.remove(o);
            },
            //> public boolean removeAll(Collection<?> coll)
            removeAll:function(coll){
                return this.s.removeAll(coll);
            },
            //> public boolean retainAll(Collection<?> coll)
            retainAll:function(coll){
                return this.s.retainAll(coll);
            },
            //> public void clear()
            clear:function(){
                this.s.clear();
            },
            //> public boolean add(Map<K,V> o)
            add:function(o){
                throw new this();
            },
            //> public boolean addAll(Collection<? extends Map> coll)
            addAll:function(coll){
                throw new this();
            },
            //> public Iterator<Map<K,V>> iterator()
            iterator:function(){
                return vjo.make(this,this)
                    .protos({
                        i:null,
                        constructs:function(){
                            this.i=this.vj$.parent.s.iterator();
                        },
                        hasNext:function(){
                            return this.i.hasNext();
                        },
                        remove:function(){
                            this.i.remove();
                        },
                        next:function(){
                            return new this.vj$.Collections.CheckedMap.CheckedEntrySet.CheckedEntry(this.i.next(),this.vj$.parent.valueType);
                        }
                    })
                    .endType();
            },
            //> public Object[] toArray()
            //> public <T> T[] toArray(T[] a)
            toArray:function(){
                if(arguments.length===0){
                    return this.toArray_0_0_CheckedEntrySet_ovld();
                }else if(arguments.length===1){
                    return this.toArray_1_0_CheckedEntrySet_ovld(arguments[0]);
                }
            },
            //> private Object[] toArray_0_0_CheckedEntrySet_ovld()
            toArray_0_0_CheckedEntrySet_ovld:function(){
                var source=this.s.toArray();
                var dest=(this.vj$.CheckedEntrySet.CheckedEntry.clazz.isInstance(source.getClass().getComponentType())?source:vjo.createArray(null, source.length));
                for (var i=0;i<source.length;i++){
                    dest[i]=new this.vj$.CheckedEntrySet.CheckedEntry(source[i],this.valueType);
                }
                return dest;
            },
            //> private <T> T[] toArray_1_0_CheckedEntrySet_ovld(T[] a)
            toArray_1_0_CheckedEntrySet_ovld:function(a){
                var arr=this.s.toArray(a.length===0?a:this.vj$.Array.newInstance(a.getClass().getComponentType(),0));
                for (var i=0;i<arr.length;i++){
                    arr[i]=new this.vj$.CheckedEntrySet.CheckedEntry(arr[i],this.valueType);
                }
                if(arr.length>a.length){
                    return arr;
                }
                this.vj$.System.arraycopy(arr,0,a,0,arr.length);
                if(a.length>arr.length){
                    a[arr.length]=null;
                }
                return a;
            },
            //> public boolean contains(Object o)
            contains:function(o){
                if(!(vjo.getType('vjo.java.util.Map').isInstance(o))){
                    return false;
                }
                return this.s.contains(new this.vj$.CheckedEntrySet.CheckedEntry(o,this.valueType));
            },
            //> public boolean containsAll(Collection<?> coll)
            containsAll:function(coll){
                var e=coll.iterator();
                while(e.hasNext()){
                    if(!this.contains(e.next())){
                        return false;
                    }
                }
                return true;
            },
            //> public boolean equals(Object o)
            equals:function(o){
                if(o===this){
                    return true;
                }
                if(!(vjo.getType('vjo.java.util.Set').isInstance(o))){
                    return false;
                }
                var that=o;
                if(that.size()!==this.s.size()){
                    return false;
                }
                return containsAll(that);
            }
        })
        .endType()
    })
    .protos({
        m:null, //< private final Map<K,V> m
        keyType:null, //< final vjo.Class<K> keyType
        valueType:null, //< final vjo.Class<V> valueType
        zeroLengthKeyArray_:null, //< private K[]
        zeroLengthValueArray_:null, //< private V[]
        entrySet_:null, //< private Set<Map<K,V>> entrySet_
        //> constructs(Map<K,V> m,vjo.Class<K> keyType,vjo.Class<V> valueType)
        constructs:function(m,keyType,valueType){
            if(m===null||keyType===null||valueType===null){
                throw new this();
            }
            this.m=m;
            this.keyType=keyType;
            this.valueType=valueType;
        },
        //> private void typeCheck(Object key,Object value)
        typeCheck:function(key,value){
            if(!this.keyType.isInstance(key)){
                throw new this.vj$.ClassCastException("Attempt to insert "+key.getClass()+" key into collection with key type "+this.keyType);
            }
            if(!this.valueType.isInstance(value)){
                throw new this.vj$.ClassCastException("Attempt to insert "+value.getClass()+" value into collection with value type "+this.valueType);
            }
        },
        //> public int size()
        size:function(){
            return this.m.size();
        },
        //> public boolean isEmpty()
        isEmpty:function(){
            return this.m.isEmpty();
        },
        //> public boolean containsKey(Object key)
        containsKey:function(key){
            return this.m.containsKey(key);
        },
        //> public boolean containsValue(Object v)
        containsValue:function(v){
            return this.m.containsValue(v);
        },
        //> public V get(Object key)
        get:function(key){
            return this.m.get(key);
        },
        //> public V remove(Object key)
        remove:function(key){
            return this.m.remove(key);
        },
        //> public void clear()
        clear:function(){
            this.m.clear();
        },
        //> public Set<K> keySet()
        keySet:function(){
            return this.m.keySet();
        },
        //> public Collection<V> values()
        values:function(){
            return this.m.values();
        },
        //> public boolean equals(Object o)
        equals:function(o){
            return vjo.java.lang.ObjectUtil.equals(this.m,o);
        },
        //> public int hashCode()
        hashCode:function(){
            return vjo.java.lang.ObjectUtil.hashCode(this.m);
        },
        //> public String toString()
        toString:function(){
            return this.m.toString();
        },
        //> public V put(K key,V value)
        put:function(key,value){
            this.typeCheck(key,value);
            return this.m.put(key,value);
        },
        //> public void putAll(Map<? extends K,? extends V> t)
        putAll:function(t){
            var keys=null;
            try {
                keys=t.keySet().toArray(this.zeroLengthKeyArray());
            }
            catch(e){
                throw new this();
            }
            var values=null;
            try {
                values=t.values().toArray(this.zeroLengthValueArray());
            }
            catch(e){
                throw new this();
            }
            if(keys.length!==values.length){
                throw new this();
            }
            for (var i=0;i<keys.length;i++){
                this.m.put(keys[i],values[i]);
            }
        },
        //> private K[] zeroLengthKeyArray()
        zeroLengthKeyArray:function(){
            if(this.zeroLengthKeyArray_===null){
                this.zeroLengthKeyArray_=this.vj$.Array.newInstance(this.keyType,0);
            }
            return this.zeroLengthKeyArray_;
        },
        //> private V[] zeroLengthValueArray()
        zeroLengthValueArray:function(){
            if(this.zeroLengthValueArray_===null){
                this.zeroLengthValueArray_=this.vj$.Array.newInstance(this.valueType,0);
            }
            return this.zeroLengthValueArray_;
        },
        //> public Set<Map<K,V>> entrySet()
        entrySet:function(){
            if(this.entrySet_===null){
                this.entrySet_=new this.vj$.CheckedMap.CheckedEntrySet(this.m.entrySet(),this.valueType);
            }
            return this.entrySet_;
        }
    })
    .endType(),
    CheckedSortedMap:vjo.ctype() //< public CheckedSortedMap<K,V>
    .inherits('vjo.java.util.Collections.CheckedMap<K,V>')
    .satisfies('vjo.java.util.SortedMap<K,V>')
    .satisfies('vjo.java.io.Serializable')
    .props({
        serialVersionUID:1599671320688067438 //< private final long
    })
    .protos({
        sm:null, //< private SortedMap<K,V> sm
        //> constructs(SortedMap<K,V> m,vjo.Class<K> keyType,vjo.Class<V> valueType)
        constructs:function(m,keyType,valueType){
            this.base(m,keyType,valueType);
            this.sm=m;
        },
        //> public Comparator<? super K> comparator()
        comparator:function(){
            return this.sm.comparator();
        },
        //> public K firstKey()
        firstKey:function(){
            return this.sm.firstKey();
        },
        //> public K lastKey()
        lastKey:function(){
            return this.sm.lastKey();
        },
        //> public SortedMap<K,V> subMap(K fromKey,K toKey)
        subMap:function(fromKey,toKey){
            return new this.vj$.CheckedSortedMap(this.sm.subMap(fromKey,toKey),this.keyType,this.valueType);
        },
        //> public SortedMap<K,V> headMap(K toKey)
        headMap:function(toKey){
            return new this.vj$.CheckedSortedMap(this.sm.headMap(toKey),this.keyType,this.valueType);
        },
        //> public SortedMap<K,V> tailMap(K fromKey)
        tailMap:function(fromKey){
            return new this.vj$.CheckedSortedMap(this.sm.tailMap(fromKey),this.keyType,this.valueType);
        }
    })
    .endType(),
    EmptySet:vjo.ctype() //< private
    .inherits('vjo.java.util.AbstractSet<Object>')
    .satisfies('vjo.java.io.Serializable')
    .props({
        serialVersionUID:1582296315990362920 //< private final long
    })
    .protos({
        //> public Iterator<Object> iterator()
        iterator:function(){
            return vjo.make(this,this.vj$.Iterator)
                .protos({
                    hasNext:function(){
                        return false;
                    },
                    next:function(){
                        throw new this.vj$.NoSuchElementException();
                    },
                    remove:function(){
                        throw new this.vj$.UnsupportedOperationException();
                    }
                })
                .endType();
        },
        //> public int size()
        size:function(){
            return 0;
        },
        //> private Object readResolve()
        readResolve:function(){
            return this.vj$.Collections.EMPTY_SET;
        },
        //> public boolean contains(Object obj)
        contains:function(obj){
            if(arguments.length===1){
                if(arguments[0] instanceof Object){
                    return this.contains_1_0_EmptySet_ovld(arguments[0]);
                }else if(this.base && this.base.contains){
                    return this.base.contains.apply(this,arguments);
                }
            }else if(this.base && this.base.contains){
                return this.base.contains.apply(this,arguments);
            }
        },
        //> private boolean contains_1_0_EmptySet_ovld(Object obj)
        contains_1_0_EmptySet_ovld:function(obj){
            return false;
        }
    })
    .endType(),
    EmptyList:vjo.ctype() //< private
    .inherits('vjo.java.util.AbstractList<Object>')
    .satisfies('vjo.java.util.RandomAccess')
    .satisfies('vjo.java.io.Serializable')
    .props({
        serialVersionUID:8842843931221139166 //< private final long
    })
    .protos({
        //> public int size()
        size:function(){
            return 0;
        },
        //> public Object get(int index)
        get:function(index){
            throw new vjo.java.lang.IndexOutOfBoundsException("Index: "+index);
        },
        //> private Object readResolve()
        readResolve:function(){
            return this.vj$.Collections.EMPTY_LIST;
        },
        //> public boolean contains(Object obj)
        contains:function(obj){
            if(arguments.length===1){
                if(arguments[0] instanceof Object){
                    return this.contains_1_0_EmptyList_ovld(arguments[0]);
                }else if(this.base && this.base.contains){
                    return this.base.contains.apply(this,arguments);
                }
            }else if(this.base && this.base.contains){
                return this.base.contains.apply(this,arguments);
            }
        },
        //> private boolean contains_1_0_EmptyList_ovld(Object obj)
        contains_1_0_EmptyList_ovld:function(obj){
            return false;
        }
    })
    .endType(),
    EmptyMap:vjo.ctype() //< private
    .inherits('vjo.java.util.AbstractMap<Object,Object>')
    .satisfies('vjo.java.io.Serializable')
    .props({
        serialVersionUID:6428348081105594320 //< private final long
    })
    .protos({
        //> public int size()
        size:function(){
            return 0;
        },
        //> public boolean isEmpty()
        isEmpty:function(){
            return true;
        },
        //> public Set<Object> keySet()
        keySet:function(){
            return this.vj$.Collections.emptySet();
        },
        //> public Collection<Object> values()
        values:function(){
            return this.vj$.Collections.emptySet();
        },
        //> public Set<Map<Object,Object>> entrySet()
        entrySet:function(){
            return this.vj$.Collections.emptySet();
        },
        //> public int hashCode()
        hashCode:function(){
            return 0;
        },
        //> private Object readResolve()
        readResolve:function(){
            return this.vj$.Collections.EMPTY_MAP;
        },
        //> public boolean containsKey(Object key)
        containsKey:function(key){
            if(arguments.length===1){
                if(arguments[0] instanceof Object){
                    return this.containsKey_1_0_EmptyMap_ovld(arguments[0]);
                }else if(this.base && this.base.containsKey){
                    return this.base.containsKey.apply(this,arguments);
                }
            }else if(this.base && this.base.containsKey){
                return this.base.containsKey.apply(this,arguments);
            }
        },
        //> private boolean containsKey_1_0_EmptyMap_ovld(Object key)
        containsKey_1_0_EmptyMap_ovld:function(key){
            return false;
        },
        //> public boolean containsValue(Object value)
        containsValue:function(value){
            if(arguments.length===1){
                if(arguments[0] instanceof Object){
                    return this.containsValue_1_0_EmptyMap_ovld(arguments[0]);
                }else if(this.base && this.base.containsValue){
                    return this.base.containsValue.apply(this,arguments);
                }
            }else if(this.base && this.base.containsValue){
                return this.base.containsValue.apply(this,arguments);
            }
        },
        //> private boolean containsValue_1_0_EmptyMap_ovld(Object value)
        containsValue_1_0_EmptyMap_ovld:function(value){
            return false;
        },
        //> public Object get(Object key)
        get:function(key){
            if(arguments.length===1){
                if(arguments[0] instanceof Object){
                    return this.get_1_0_EmptyMap_ovld(arguments[0]);
                }else if(this.base && this.base.get){
                    return this.base.get.apply(this,arguments);
                }
            }else if(this.base && this.base.get){
                return this.base.get.apply(this,arguments);
            }
        },
        //> private Object get_1_0_EmptyMap_ovld(Object key)
        get_1_0_EmptyMap_ovld:function(key){
            return null;
        },
        //> public boolean equals(Object o)
        equals:function(o){
            if(arguments.length===1){
                if(arguments[0] instanceof Object){
                    return this.equals_1_0_EmptyMap_ovld(arguments[0]);
                }else if(this.base && this.base.equals){
                    return this.base.equals.apply(this,arguments);
                }
            }else if(this.base && this.base.equals){
                return this.base.equals.apply(this,arguments);
            }
        },
        //> private boolean equals_1_0_EmptyMap_ovld(Object o)
        equals_1_0_EmptyMap_ovld:function(o){
            return (vjo.getType('vjo.java.util.Map').isInstance(o))&&o.size()===0;
        }
    })
    .endType(),
    SingletonSet:vjo.ctype() //< public SingletonSet<E>
    .inherits('vjo.java.util.AbstractSet<E>')
    .satisfies('vjo.java.io.Serializable')
    .props({
        serialVersionUID:3193687207550431679 //< private final long
    })
    .protos({
        element:null, //< private final E
        //> constructs(E o)
        constructs:function(o){
            this.base();
            this.element=o;
        },
        //> public Iterator<E> iterator()
        iterator:function(){
            return vjo.make(this,this.vj$.Iterator)
                .protos({
                    hasNext_:true,
                    hasNext:function(){
                        return this.hasNext_;
                    },
                    next:function(){
                        if(this.hasNext_){
                            this.hasNext_=false;
                            return this.vj$.parent.element;
                        }
                        throw new this.vj$.NoSuchElementException();
                    },
                    remove:function(){
                        throw new this.vj$.UnsupportedOperationException();
                    }
                })
                .endType();
        },
        //> public int size()
        size:function(){
            return 1;
        },
        //> public boolean contains(Object o)
        contains:function(o){
            if(arguments.length===1){
                if(arguments[0] instanceof Object){
                    return this.contains_1_0_SingletonSet_ovld(arguments[0]);
                }else if(this.base && this.base.contains){
                    return this.base.contains.apply(this,arguments);
                }
            }else if(this.base && this.base.contains){
                return this.base.contains.apply(this,arguments);
            }
        },
        //> private boolean contains_1_0_SingletonSet_ovld(Object o)
        contains_1_0_SingletonSet_ovld:function(o){
            return this.vj$.Collections.eq(o,this.element);
        }
    })
    .endType(),
    SingletonList:vjo.ctype() //< public SingletonList<E>
    .inherits('vjo.java.util.AbstractList<E>')
    .satisfies('vjo.java.util.RandomAccess')
    .satisfies('vjo.java.io.Serializable')
    .props({
        serialVersionUID:3093736618740652951 //< final long
    })
    .protos({
        element:null, //< private final E
        //> constructs(E obj)
        constructs:function(obj){
            this.base();
            this.element=obj;
        },
        //> public int size()
        size:function(){
            return 1;
        },
        //> public E get(int index)
        get:function(index){
            if(index!==0){
                throw new vjo.java.lang.IndexOutOfBoundsException("Index: "+index+", Size: 1");
            }
            return this.element;
        },
        //> public boolean contains(Object obj)
        contains:function(obj){
            if(arguments.length===1){
                if(arguments[0] instanceof Object){
                    return this.contains_1_0_SingletonList_ovld(arguments[0]);
                }else if(this.base && this.base.contains){
                    return this.base.contains.apply(this,arguments);
                }
            }else if(this.base && this.base.contains){
                return this.base.contains.apply(this,arguments);
            }
        },
        //> private boolean contains_1_0_SingletonList_ovld(Object obj)
        contains_1_0_SingletonList_ovld:function(obj){
            return this.vj$.Collections.eq(obj,this.element);
        }
    })
    .endType(),
    SingletonMap:vjo.ctype() //< public SingletonMap<K,V>
    .inherits('vjo.java.util.AbstractMap<K,V>')
    .satisfies('vjo.java.io.Serializable')
    .props({
        serialVersionUID:-6979724477215052911, //< private final long
        ImmutableEntry:vjo.ctype() //< public ImmutableEntry<K,V>
        .satisfies('vjo.java.util.Map<K,V>')
        .protos({
            k:null, //< final K
            v:null, //< final V
            //> constructs(K key,V value)
            constructs:function(key,value){
                this.k=key;
                this.v=value;
            },
            //> public K getKey()
            getKey:function(){
                return this.k;
            },
            //> public V getValue()
            getValue:function(){
                return this.v;
            },
            //> public V setValue(V value)
            setValue:function(value){
                throw new this.vj$.UnsupportedOperationException();
            },
            //> public boolean equals(Object o)
            equals:function(o){
                if(!(vjo.getType('vjo.java.util.Map').isInstance(o))){
                    return false;
                }
                var e=o;
                return this.vj$.Collections.eq(e.getKey(),this.k)&&this.vj$.Collections.eq(e.getValue(),this.v);
            },
            //> public int hashCode()
            hashCode:function(){
                return ((this.k===null?0:this.k.hashCode())^(this.v===null?0:this.v.hashCode()));
            },
            //> public String toString()
            toString:function(){
                return this.k+"="+this.v;
            }
        })
        .endType()
    })
    .protos({
        k:null, //< private final K
        v:null, //< private final V
        keySet_:null, //< private Set<K> keySet_
        entrySet_:null, //< private Set<Map<K,V>> entrySet_
        values_:null, //< private Collection<V> values_
        //> constructs(K key,V value)
        constructs:function(key,value){
            this.base();
            this.k=key;
            this.v=value;
        },
        //> public int size()
        size:function(){
            return 1;
        },
        //> public boolean isEmpty()
        isEmpty:function(){
            return false;
        },
        //> public Set<K> keySet()
        keySet:function(){
            if(this.keySet_===null){
                this.keySet_=this.vj$.Collections.singletonSet(this.k);
            }
            return this.keySet_;
        },
        //> public Set<Map<K,V>> entrySet()
        entrySet:function(){
            if(this.entrySet_===null){
                this.entrySet_=this.vj$.Collections.singletonSet(new this.vj$.SingletonMap.ImmutableEntry(this.k,this.v));
            }
            return this.entrySet_;
        },
        //> public Collection<V> values()
        values:function(){
            if(this.values_===null){
                this.values_=this.vj$.Collections.singletonSet(this.v);
            }
            return this.values_;
        },
        //> public boolean containsKey(Object key)
        containsKey:function(key){
            if(arguments.length===1){
                if(arguments[0] instanceof Object){
                    return this.containsKey_1_0_SingletonMap_ovld(arguments[0]);
                }else if(this.base && this.base.containsKey){
                    return this.base.containsKey.apply(this,arguments);
                }
            }else if(this.base && this.base.containsKey){
                return this.base.containsKey.apply(this,arguments);
            }
        },
        //> private boolean containsKey_1_0_SingletonMap_ovld(Object key)
        containsKey_1_0_SingletonMap_ovld:function(key){
            return this.vj$.Collections.eq(key,this.k);
        },
        //> public boolean containsValue(Object value)
        containsValue:function(value){
            if(arguments.length===1){
                if(arguments[0] instanceof Object){
                    return this.containsValue_1_0_SingletonMap_ovld(arguments[0]);
                }else if(this.base && this.base.containsValue){
                    return this.base.containsValue.apply(this,arguments);
                }
            }else if(this.base && this.base.containsValue){
                return this.base.containsValue.apply(this,arguments);
            }
        },
        //> private boolean containsValue_1_0_SingletonMap_ovld(Object value)
        containsValue_1_0_SingletonMap_ovld:function(value){
            return this.vj$.Collections.eq(value,this.v);
        },
        //> public V get(Object key)
        get:function(key){
            if(arguments.length===1){
                if(arguments[0] instanceof Object){
                    return this.get_1_0_SingletonMap_ovld(arguments[0]);
                }else if(this.base && this.base.get){
                    return this.base.get.apply(this,arguments);
                }
            }else if(this.base && this.base.get){
                return this.base.get.apply(this,arguments);
            }
        },
        //> private V get_1_0_SingletonMap_ovld(Object key)
        get_1_0_SingletonMap_ovld:function(key){
            return (this.vj$.Collections.eq(key,this.k)?this.v:null);
        }
    })
    .endType(),
    CopiesList:vjo.ctype() //< public CopiesList<E>
    .inherits('vjo.java.util.AbstractList<E>')
    .satisfies('vjo.java.util.RandomAccess')
    .satisfies('vjo.java.io.Serializable')
    .props({
        serialVersionUID:2739099268398711800 //< final long
    })
    .protos({
        n:0, //< int
        element:null, //< E
        //> constructs(int n,E o)
        constructs:function(n,o){
            this.base();
            if(n<0){
                throw new vjo.java.lang.IllegalArgumentException("List length = "+n);
            }
            this.n=n;
            this.element=o;
        },
        //> public int size()
        size:function(){
            return this.n;
        },
        //> public E get(int index)
        get:function(index){
            if(index<0||index>=this.n){
                throw new vjo.java.lang.IndexOutOfBoundsException("Index: "+index+", Size: "+this.n);
            }
            return this.element;
        },
        //> public boolean contains(Object obj)
        contains:function(obj){
            if(arguments.length===1){
                if(arguments[0] instanceof Object){
                    return this.contains_1_0_CopiesList_ovld(arguments[0]);
                }else if(this.base && this.base.contains){
                    return this.base.contains.apply(this,arguments);
                }
            }else if(this.base && this.base.contains){
                return this.base.contains.apply(this,arguments);
            }
        },
        //> private boolean contains_1_0_CopiesList_ovld(Object obj)
        contains_1_0_CopiesList_ovld:function(obj){
            return this.n!==0&&this.vj$.Collections.eq(obj,this.element);
        }
    })
    .endType(),
    ReverseComparator:vjo.ctype() //< private ReverseComparator<T>
    .satisfies('vjo.java.util.Comparator<Comparable>')
    .satisfies('vjo.java.io.Serializable')
    .props({
        serialVersionUID:7207038068494060240 //< private final long
    })
    .protos({
        //> public int compare(Comparable<Object> c1,Comparable<Object> c2)
        compare:function(c1,c2){
            return vjo.java.lang.ObjectUtil.compareTo(c2, c1);
        }
    })
    .endType(),
    ReverseComparatorWithComparator:vjo.ctype() //< public ReverseComparatorWithComparator<T>
    .satisfies('vjo.java.util.Comparator<T>')
    .satisfies('vjo.java.io.Serializable')
    .props({
        serialVersionUID:4374092139857 //< private final long
    })
    .protos({
        cmp:null, //< private Comparator<T> cmp
        //> constructs(Comparator<T> cmp)
        constructs:function(cmp){
            this.cmp=cmp;
        },
        //> public int compare(T t1,T t2)
        compare:function(t1,t2){
            return this.cmp.compare(t2,t1);
        }
    })
    .endType(),
    //> public void sort(List<T> list)
    //> public <T extends Comparable<? super T>> void sort(List<T> list)
    //> public <T> void sort(List<T> list,Comparator<? super T> c)
    sort:function(list){
        if(arguments.length===1){
            this.vj$.Collections.sort_1_0_Collections_ovld(arguments[0]);
        }else if(arguments.length===2){
            this.vj$.Collections.sort_2_0_Collections_ovld(arguments[0],arguments[1]);
        }
    },
    //> private <T extends Comparable<? super T>> void sort_1_0_Collections_ovld(List<T> list)
    sort_1_0_Collections_ovld:function(list){
        var a=list.toArray();
        vjo.java.util.Arrays.sort(a);
        var i=list.listIterator();
        for (var j=0;j<a.length;j++){
            i.next();
            i.set(a[j]);
        }
    },
    //> private <T> void sort_2_0_Collections_ovld(List<T> list,Comparator<? super T> c)
    sort_2_0_Collections_ovld:function(list,c){
        var a=list.toArray();
        vjo.java.util.Arrays.sort(a,c);
        var i=list.listIterator();
        for (var j=0;j<a.length;j++){
            i.next();
            i.set(a[j]);
        }
    },
    //> private <T> T get(Iterator<? extends T> i,int index)
    get:function(i,index){
        var obj=null;
        var pos=i.nextIndex();
        if(pos<=index){
            do{
                obj=i.next();
            }while(pos++<index);
        }else {
            do{
                obj=i.previous();
            }while(--pos>index);
        }
        return obj;
    },
    //> public int binarySearch(List<? extends Comparable> list,T key)
    //> public <T> int binarySearch(List<? extends Comparable> list,T key)
    //> public <T> int binarySearch(List<? extends T> list,T key,Comparator<? super T> c)
    binarySearch:function(list,key){
        if(arguments.length===2){
            return this.vj$.Collections.binarySearch_2_0_Collections_ovld(arguments[0],arguments[1]);
        }else if(arguments.length===3){
            return this.vj$.Collections.binarySearch_3_0_Collections_ovld(arguments[0],arguments[1],arguments[2]);
        }
    },
    //> private <T> int binarySearch_2_0_Collections_ovld(List<? extends Comparable> list,T key)
    binarySearch_2_0_Collections_ovld:function(list,key){
        if(vjo.getType('vjo.java.util.RandomAccess').isInstance(list)||list.size()<this.BINARYSEARCH_THRESHOLD){
            return this.indexedBinarySearch(list,key);
        }else {
            return this.iteratorBinarySearch(list,key);
        }
    },
    //> private <T> int binarySearch_3_0_Collections_ovld(List<? extends T> list,T key,Comparator<? super T> c)
    binarySearch_3_0_Collections_ovld:function(list,key,c){
        if(c===null){
            return this.binarySearch(list,key);
        }
        if(vjo.getType('vjo.java.util.RandomAccess').isInstance(list)||list.size()<this.BINARYSEARCH_THRESHOLD){
            return this.indexedBinarySearch(list,key,c);
        }else {
            return this.iteratorBinarySearch(list,key,c);
        }
    },
    //> private int indexedBinarySearch(List<? extends Comparable> list,T key)
    //> private <T> int indexedBinarySearch(List<? extends Comparable> list,T key)
    //> private <T> int indexedBinarySearch(List<? extends T> l,T key,Comparator<? super T> c)
    indexedBinarySearch:function(list,key){
        if(arguments.length===2){
            return this.vj$.Collections.indexedBinarySearch_2_0_Collections_ovld(arguments[0],arguments[1]);
        }else if(arguments.length===3){
            return this.vj$.Collections.indexedBinarySearch_3_0_Collections_ovld(arguments[0],arguments[1],arguments[2]);
        }
    },
    //> private <T> int indexedBinarySearch_2_0_Collections_ovld(List<? extends Comparable> list,T key)
    indexedBinarySearch_2_0_Collections_ovld:function(list,key){
        var low=0;
        var high=list.size()-1;
        while(low<=high){
            var mid=(low+high)>>1;
            var midVal=list.get(mid);
            var cmp=vjo.java.lang.ObjectUtil.compareTo(midVal, key);
            if(cmp<0){
                low=mid+1;
            }else if(cmp>0){
                high=mid-1;
            }else {
                return mid;
            }
        }
        return -(low+1);
    },
    //> private <T> int indexedBinarySearch_3_0_Collections_ovld(List<? extends T> l,T key,Comparator<? super T> c)
    indexedBinarySearch_3_0_Collections_ovld:function(l,key,c){
        var low=0;
        var high=l.size()-1;
        while(low<=high){
            var mid=(low+high)>>1;
            var midVal=l.get(mid);
            var cmp=c.compare(midVal,key);
            if(cmp<0){
                low=mid+1;
            }else if(cmp>0){
                high=mid-1;
            }else {
                return mid;
            }
        }
        return -(low+1);
    },
    //> private int iteratorBinarySearch(List<? extends Comparable> list,T key)
    //> private <T> int iteratorBinarySearch(List<? extends Comparable> list,T key)
    //> private <T> int iteratorBinarySearch(List<? extends T> l,T key,Comparator<? super T> c)
    iteratorBinarySearch:function(list,key){
        if(arguments.length===2){
            return this.vj$.Collections.iteratorBinarySearch_2_0_Collections_ovld(arguments[0],arguments[1]);
        }else if(arguments.length===3){
            return this.vj$.Collections.iteratorBinarySearch_3_0_Collections_ovld(arguments[0],arguments[1],arguments[2]);
        }
    },
    //> private <T> int iteratorBinarySearch_2_0_Collections_ovld(List<? extends Comparable> list,T key)
    iteratorBinarySearch_2_0_Collections_ovld:function(list,key){
        var low=0;
        var high=list.size()-1;
        var i=list.listIterator();
        while(low<=high){
            var mid=(low+high)>>1;
            var midVal=this.get(i,mid);
            var cmp=vjo.java.lang.ObjectUtil.compareTo(midVal, key);
            if(cmp<0){
                low=mid+1;
            }else if(cmp>0){
                high=mid-1;
            }else {
                return mid;
            }
        }
        return -(low+1);
    },
    //> private <T> int iteratorBinarySearch_3_0_Collections_ovld(List<? extends T> l,T key,Comparator<? super T> c)
    iteratorBinarySearch_3_0_Collections_ovld:function(l,key,c){
        var low=0;
        var high=l.size()-1;
        var i=l.listIterator();
        while(low<=high){
            var mid=(low+high)>>1;
            var midVal=this.get(i,mid);
            var cmp=c.compare(midVal,key);
            if(cmp<0){
                low=mid+1;
            }else if(cmp>0){
                high=mid-1;
            }else {
                return mid;
            }
        }
        return -(low+1);
    },
    //> public void reverse(List<?> list)
    reverse:function(list){
        var size=list.size();
        if(size<this.REVERSE_THRESHOLD||vjo.getType('vjo.java.util.RandomAccess').isInstance(list)){
            for (var i=0,mid=size>>1,j=size-1;i<mid;i++,j--){
                this.swap(list,i,j);
            }
        }else {
            var fwd=list.listIterator();
            var rev=list.listIterator(size);
            for (var i=0,mid=list.size()>>1;i<mid;i++){
                var tmp=fwd.next();
                fwd.set(rev.previous());
                rev.set(tmp);
            }
        }
    },   
    r:null, //< private vjo.java.util.Random
    //> public void shuffle(vjo.java.util.List<?> list)
    //> public void shuffle(vjo.java.util.List<?> list,vjo.java.util.Random rnd)
    shuffle:function(list){
        if(arguments.length===1){
            this.vj$.Collections.shuffle_1_0_Collections_ovld(arguments[0]);
        }else if(arguments.length===2){
            this.vj$.Collections.shuffle_2_0_Collections_ovld(arguments[0],arguments[1]);
        }
    },
    //> private void shuffle_1_0_Test_ovld(vjo.java.util.List<?> list)
    shuffle_1_0_Collections_ovld:function(list){
        this.shuffle(list,this.r);
    },
    //> private void shuffle_2_0_Test_ovld(vjo.java.util.List<?> list,vjo.java.util.Random rnd)
    shuffle_2_0_Collections_ovld:function(list,rnd){
        var size=list.size(); //<int
        if(size<this.SHUFFLE_THRESHOLD||vjo.java.util.RandomAccess.isInstance(list)){
            for (var i=size;i>1;i--){
                this.swap(list,i-1,rnd.nextInt(i));
            }
        }else {
            var arr=list.toArray(); //<Object
            for (var i=size;i>1;i--){
                this.swap(arr,i-1,rnd.nextInt(i));
            }
            var it=list.listIterator(); //<Iterator
            for (var i=0;i<arr.length;i++){
                it.next();
                it.set(arr[i]);
            }
        }
    },
    //> public void swap(List<?> list,int i,int j)
    //> public void swap(List<?> list,int i,int j)
    //> private void swap(com.ebay.dsf.jsnative.global.Object[] arr,int i,int j)
    swap:function(list,i,j){
        if(arguments.length===3){
            if(vjo.java.util.List.isInstance(arguments[0]) && typeof arguments[1]=="number" && typeof arguments[2]=="number"){
                this.vj$.Collections.swap_3_0_Collections_ovld(arguments[0],arguments[1],arguments[2]);
            }else if(arguments[0] instanceof Array && typeof arguments[1]=="number" && typeof arguments[2]=="number"){
                this.vj$.Collections.swap_3_1_Collections_ovld(arguments[0],arguments[1],arguments[2]);
            }
        }
    },
    //> private void swap_3_0_Collections_ovld(List<?> list,int i,int j)
    swap_3_0_Collections_ovld:function(list,i,j){
        var l=list;
        l.set(i,l.set(j,l.get(i)));
    },
    //> private void swap_3_1_Collections_ovld(com.ebay.dsf.jsnative.global.Object[] arr,int i,int j)
    swap_3_1_Collections_ovld:function(arr,i,j){
        var tmp=arr[i];
        arr[i]=arr[j];
        arr[j]=tmp;
    },
    //> public <T> void fill(List<? super T> list,T obj)
    fill:function(list,obj){
        var size=list.size();
        if(size<this.FILL_THRESHOLD||vjo.getType('vjo.java.util.RandomAccess').isInstance(list)){
            for (var i=0;i<size;i++){
                list.set(i,obj);
            }
        }else {
            var itr=list.listIterator();
            for (var i=0;i<size;i++){
                itr.next();
                itr.set(obj);
            }
        }
    },
    //> public <T> void copy(List<? super T> dest,List<? extends T> src)
    copy:function(dest,src){
        var srcSize=src.size();
        if(srcSize>dest.size()){
            throw new this.vj$.IndexOutOfBoundsException("Source does not fit in dest");
        }
        if(srcSize<this.COPY_THRESHOLD||(vjo.getType('vjo.java.util.RandomAccess').isInstance(src)&&vjo.getType('vjo.java.util.RandomAccess').isInstance(dest))){
            for (var i=0;i<srcSize;i++){
                dest.set(i,src.get(i));
            }
        }else {
            var di=dest.listIterator();
            var si=src.listIterator();
            for (var i=0;i<srcSize;i++){
                di.next();
                di.set(si.next());
            }
        }
    },
    //> public T min(Collection<? extends T> coll)
    //> public <T extends Object> T min(Collection<? extends T> coll)
    //> public <T> T min(Collection<? extends T> coll,Comparator<? super T> comp)
    min:function(coll){
        if(arguments.length===1){
            return this.vj$.Collections.min_1_0_Collections_ovld(arguments[0]);
        }else if(arguments.length===2){
            return this.vj$.Collections.min_2_0_Collections_ovld(arguments[0],arguments[1]);
        }
    },
    //> private <T extends Object> T min_1_0_Collections_ovld(Collection<? extends T> coll)
    min_1_0_Collections_ovld:function(coll){
        var i=coll.iterator();
        var candidate=i.next();
        while(i.hasNext()){
            var next=i.next();
            if(vjo.java.lang.ObjectUtil.compareTo(next, candidate)<0){
                candidate=next;
            }
        }
        return candidate;
    },
    //> private <T> T min_2_0_Collections_ovld(Collection<? extends T> coll,Comparator<? super T> comp)
    min_2_0_Collections_ovld:function(coll,comp){
        if(comp===null){
            return this.min(coll);
        }
        var i=coll.iterator();
        var candidate=i.next();
        while(i.hasNext()){
            var next=i.next();
            if(comp.compare(next,candidate)<0){
                candidate=next;
            }
        }
        return candidate;
    },
    //> public T max(Collection<? extends T> coll)
    //> public <T extends Object> T max(Collection<? extends T> coll)
    //> public <T> T max(Collection<? extends T> coll,Comparator<? super T> comp)
    max:function(coll){
        if(arguments.length===1){
            return this.vj$.Collections.max_1_0_Collections_ovld(arguments[0]);
        }else if(arguments.length===2){
            return this.vj$.Collections.max_2_0_Collections_ovld(arguments[0],arguments[1]);
        }
    },
    //> private <T extends Object> T max_1_0_Collections_ovld(Collection<? extends T> coll)
    max_1_0_Collections_ovld:function(coll){
        var i=coll.iterator();
        var candidate=i.next();
        while(i.hasNext()){
            var next=i.next();
            if(vjo.java.lang.ObjectUtil.compareTo(next, candidate)>0){
                candidate=next;
            }
        }
        return candidate;
    },
    //> private <T> T max_2_0_Collections_ovld(Collection<? extends T> coll,Comparator<? super T> comp)
    max_2_0_Collections_ovld:function(coll,comp){
        if(comp===null){
            return this.max(coll);
        }
        var i=coll.iterator();
        var candidate=i.next();
        while(i.hasNext()){
            var next=i.next();
            if(comp.compare(next,candidate)>0){
                candidate=next;
            }
        }
        return candidate;
    },
    //> public void rotate(List<?> list,int distance)
    rotate:function(list,distance){
        if(vjo.getType('vjo.java.util.RandomAccess').isInstance(list)||list.size()<this.ROTATE_THRESHOLD){
            this.rotate1(list,distance);
        }else {
            this.rotate2(list,distance);
        }
    },
    //> private <T> void rotate1(List<T> list,int distance)
    rotate1:function(list,distance){
        var size=list.size();
        if(size===0){
            return;
        }
        distance=distance%size;
        if(distance<0){
            distance+=size;
        }
        if(distance===0){
            return;
        }
        for (var cycleStart=0,nMoved=0;nMoved!==size;cycleStart++){
            var displaced=list.get(cycleStart);
            var i=cycleStart;
            do{
                i+=distance;
                if(i>=size){
                    i-=size;
                }
                displaced=list.set(i,displaced);
                nMoved++;
            }while(i!==cycleStart);
        }
    },
    //> private void rotate2(List<?> list,int distance)
    rotate2:function(list,distance){
        var size=list.size();
        if(size===0){
            return;
        }
        var mid=-distance%size;
        if(mid<0){
            mid+=size;
        }
        if(mid===0){
            return;
        }
        this.reverse(list.subList(0,mid));
        this.reverse(list.subList(mid,size));
        this.reverse(list);
    },
    //> public <T> boolean replaceAll(List<T> list,T oldVal,T newVal)
    replaceAll:function(list,oldVal,newVal){
        var result=false;
        var size=list.size();
        if(size<this.REPLACEALL_THRESHOLD||vjo.getType('vjo.java.util.RandomAccess').isInstance(list)){
            if(oldVal===null){
                for (var i=0;i<size;i++){
                    if(list.get(i)===null){
                        list.set(i,newVal);
                        result=true;
                    }
                }
            }else {
                for (var i=0;i<size;i++){
                    if(oldVal.equals(list.get(i))){
                        list.set(i,newVal);
                        result=true;
                    }
                }
            }
        }else {
            var itr=list.listIterator();
            if(oldVal===null){
                for (var i=0;i<size;i++){
                    if(itr.next()===null){
                        itr.set(newVal);
                        result=true;
                    }
                }
            }else {
                for (var i=0;i<size;i++){
                    if(oldVal.equals(itr.next())){
                        itr.set(newVal);
                        result=true;
                    }
                }
            }
        }
        return result;
    },
    //> public int indexOfSubList(List<?> source,List<?> target)
    indexOfSubList:function(source,target){
        var sourceSize=source.size();
        var targetSize=target.size();
        var maxCandidate=sourceSize-targetSize;
        if(sourceSize<this.INDEXOFSUBLIST_THRESHOLD||(vjo.getType('vjo.java.util.RandomAccess').isInstance(source)&&vjo.getType('vjo.java.util.RandomAccess').isInstance(target))){
            nextCand:
            for (var candidate=0;candidate<=maxCandidate;candidate++){
                for (var i=0,j=candidate;i<targetSize;i++,j++){
                    if(!this.eq(target.get(i),source.get(j))){
                        continue nextCand;
                    }
                }
                return candidate;
            }
        }else {
            var si=source.listIterator();
            nextCand:
            for (var candidate=0;candidate<=maxCandidate;candidate++){
                var ti=target.listIterator();
                for (var i=0;i<targetSize;i++){
                    if(!this.eq(ti.next(),si.next())){
                        for (var j=0;j<i;j++){
                            si.previous();
                        }
                        continue nextCand;
                    }
                }
                return candidate;
            }
        }
        return -1;
    },
    //> public int lastIndexOfSubList(List<?> source,List<?> target)
    lastIndexOfSubList:function(source,target){
        var sourceSize=source.size();
        var targetSize=target.size();
        var maxCandidate=sourceSize-targetSize;
        if(sourceSize<this.INDEXOFSUBLIST_THRESHOLD||vjo.getType('vjo.java.util.RandomAccess').isInstance(source)){
            nextCand:
            for (var candidate=maxCandidate;candidate>=0;candidate--){
                for (var i=0,j=candidate;i<targetSize;i++,j++){
                    if(!this.eq(target.get(i),source.get(j))){
                        continue nextCand;
                    }
                }
                return candidate;
            }
        }else {
            if(maxCandidate<0){
                return -1;
            }
            var si=source.listIterator(maxCandidate);
            nextCand:
            for (var candidate=maxCandidate;candidate>=0;candidate--){
                var ti=target.listIterator();
                for (var i=0;i<targetSize;i++){
                    if(!this.eq(ti.next(),si.next())){
                        if(candidate!==0){
                            for (var j=0;j<=i+1;j++){
                                si.previous();
                            }
                        }
                        continue nextCand;
                    }
                }
                return candidate;
            }
        }
        return -1;
    },
    //> public <T> Collection<T> unmodifiableCollection(Collection<? extends T> c)
    unmodifiableCollection:function(c){
        return new this.UnmodifiableCollection(c);
    },
    //> public <T> Set<T> unmodifiableSet(Set<? extends T> s)
    unmodifiableSet:function(s){
        return new this.UnmodifiableSet(s);
    },
    //> public <T> SortedSet<T> unmodifiableSortedSet(SortedSet<T> s)
    unmodifiableSortedSet:function(s){
        return new this.UnmodifiableSortedSet(s);
    },
    //> public <T> List<T> unmodifiableList(List<? extends T> list)
    unmodifiableList:function(list){
		return (this.vj$.RandomAccess.isInstance(list)?new this.UnmodifiableRandomAccessList(list):new this.UnmodifiableList(list));
    },
    //> public <K,V> Map<K,V> unmodifiableMap(Map<? extends K,? extends V> m)
    unmodifiableMap:function(m){
        return new this.UnmodifiableMap(m);
    },
    //> public <K,V> SortedMap<K,V> unmodifiableSortedMap(SortedMap<K,? extends V> m)
    unmodifiableSortedMap:function(m){
        return new this.UnmodifiableSortedMap(m);
    },
    //> public <E> Collection<E> checkedCollection(Collection<E> c,vjo.Class<E> type)
    checkedCollection:function(c,type){
        return new this.CheckedCollection(c,type);
    },
    //> public <E> Set<E> checkedSet(Set<E> s,vjo.Class<E> type)
    checkedSet:function(s,type){
        return new this.CheckedSet(s,type);
    },
    //> public <E> SortedSet<E> checkedSortedSet(SortedSet<E> s,vjo.Class<E> type)
    checkedSortedSet:function(s,type){
        return new this.CheckedSortedSet(s,type);
    },
    //> public <E> List<E> checkedList(List<E> list,vjo.Class<E> type)
    checkedList:function(list,type){
        return (this.vj$.RandomAccess.isInstance(list)?new this.CheckedRandomAccessList(list,type):new this.CheckedList(list,type));
    },
    //> public <K,V> Map<K,V> checkedMap(Map<K,V> m,vjo.Class<K> keyType,vjo.Class<V> valueType)
    checkedMap:function(m,keyType,valueType){
        return new this.CheckedMap(m,keyType,valueType);
    },
    //> public <K,V> SortedMap<K,V> checkedSortedMap(SortedMap<K,V> m,vjo.Class<K> keyType,vjo.Class<V> valueType)
    checkedSortedMap:function(m,keyType,valueType){
        return new this.CheckedSortedMap(m,keyType,valueType);
    },
    //> final public <T> Set<T> emptySet()
    emptySet:function(){
        return this.EMPTY_SET;
    },
    //> final public <T> List<T> emptyList()
    emptyList:function(){
        return this.EMPTY_LIST;
    },
    //> final public <K,V> Map<K,V> emptyMap()
    emptyMap:function(){
        return this.EMPTY_MAP;
    },
    //> public <T> Set<T> singletonSet(T o)
    singletonSet:function(o){
        return new this.SingletonSet(o);
    },
    //> public <T> List<T> singletonList(T o)
    singletonList:function(o){
        return new this.SingletonList(o);
    },
    //> public <K,V> Map<K,V> singletonMap(K key,V value)
    singletonMap:function(key,value){
        return new this.SingletonMap(key,value);
    },
    //> public <T> List<T> nCopies(int n,T o)
    nCopies:function(n,o){
        return new this.CopiesList(n,o);
    },
    //> public Comparator<T> reverseOrder()
    //> public <T> Comparator<T> reverseOrder(Comparator<T> cmp)
    reverseOrder:function(){
        if(arguments.length===0){
            return this.vj$.Collections.reverseOrder_0_0_Collections_ovld();
        }else if(arguments.length===1){
            return this.vj$.Collections.reverseOrder_1_0_Collections_ovld(arguments[0]);
        }
    },
    //> private <T> Comparator<T> reverseOrder_0_0_Collections_ovld()
    reverseOrder_0_0_Collections_ovld:function(){
        return this.REVERSE_ORDER;
    },
    //> private <T> Comparator<T> reverseOrder_1_0_Collections_ovld(Comparator<T> cmp)
    reverseOrder_1_0_Collections_ovld:function(cmp){
        if(cmp===null){
            return new this.ReverseComparator();
        }
        return new this.ReverseComparatorWithComparator(cmp);
    },
    //> public <T> Enumeration<T> enumeration(final Collection<T> c)
    enumeration:function(c){
        return vjo.make(this,vjo.java.util.Enumeration)
            .protos({
                i:null,
                constructs:function(){
                    this.i=c.iterator();
                },
                hasMoreElements:function(){
                    return this.i.hasNext();
                },
                nextElement:function(){
                    return this.i.next();
                }
            })
            .endType();
    },
    //> public <T> ArrayList<T> list(Enumeration<T> e)
    list:function(e){
        var l=new vjo.java.util.ArrayList();
        while(e.hasMoreElements()){
            l.add(e.nextElement());
        }
        return l;
    },
    //> private boolean eq(Object o1,Object o2)
    eq:function(o1,o2){
        return (o1===null?o2===null:vjo.java.lang.ObjectUtil.equals(o1,o2));
    },
    //> public int frequency(Collection<?> c,Object o)
    frequency:function(c,o){
        if(!c) throw new this.vj$.NullPointerException();
        var result=0;
        if(o===null){
            var itr=c.iterator();
            while(itr.hasNext()){
                var e=itr.next();
                if(e===null){
                    result++;
                }
            }
        }else {
            var itr=c.iterator();
            while(itr.hasNext()){
                var e=itr.next();
                if(vjo.java.lang.ObjectUtil.equals(o,e)){
                    result++;
                }
            }
        }
        return result;
    },
    //> public boolean disjoint(Collection<?> c1,Collection<?> c2)
    disjoint:function(c1,c2){
        if((vjo.getType('vjo.java.util.Set').isInstance(c1))&&!(vjo.getType('vjo.java.util.Set').isInstance(c2))||(c1.size()>c2.size())){
            var tmp=c1;
            c1=c2;
            c2=tmp;
        }
        var itr=c1.iterator();
        while(itr.hasNext()){
            var e=itr.next();
            if(c2.contains(e)){
                return false;
            }
        }
        return true;
    },
    //> public <T> boolean addAll(Collection<? super T> c,T... a)
    addAll:function(c){
        var a;
        var arg = arguments, arg1 = arguments[1];
        if (arguments.length == 2) {
            if(arg1 instanceof Array){
                a=arg1;
            } else {
                if(!arg1) throw new this.vj$.NullPointerException();
            }
        }
        else {
            a=[];
            for (var i=1; i<arguments.length; i++){
                a[i-1]=arguments[i];
            }
        }
        var result=false;
        for (var e,i=0;i<a.length;i++){
            e=a[i];
            result=c.add(e) || result;
        }
        return result;
    }
})
.protos({
    //> private constructs()
    constructs:function(){
    }
})
.inits(function(){
    //this.vj$.Collections.r=new vjo.java.util.Random();
    this.vj$.Collections.EMPTY_SET=new this.EmptySet();
    this.vj$.Collections.EMPTY_LIST=new this.EmptyList();
    this.vj$.Collections.EMPTY_MAP=new this.EmptyMap();
    this.vj$.Collections.REVERSE_ORDER=new this.ReverseComparator();
    this.vj$.Collections.r=new vjo.java.util.Random();
})
.endType();