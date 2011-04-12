vjo.ctype('vjo.java.util.TreeSet<E>') //< public
.needs(['vjo.java.util.Set','vjo.java.util.Collection',
    'vjo.java.util.Iterator','vjo.java.util.AbstractCollection'])
.needs('vjo.java.lang.NullPointerException','')
.needs('vjo.java.util.SortedMap','')
.needs('vjo.java.util.TreeMap','')
.needs('vjo.java.util.Comparator','')
.inherits('vjo.java.util.AbstractSet<E>')
.satisfies('vjo.java.util.SortedSet<E>')
.satisfies('vjo.java.lang.Cloneable')
.satisfies('vjo.java.io.Serializable')
.props({
    PRESENT:null, //< private final Object
    serialVersionUID:-2479143000061671589 //< private final long
})
.protos({
    m:null, //< private SortedMap<E,Object> m
    keySet:null, //< private Set<E> keySet
    //> public constructs()
    //> private constructs(SortedMap<E,Object> m)
    //> public constructs(Comparator<? super E> c)
    //> public constructs(Collection<? extends E> c)
    //> public constructs(SortedSet<E> s)
    constructs:function(){
        if(arguments.length===1){
            if(vjo.java.util.SortedMap.clazz.isInstance(arguments[0])){
                this.constructs_1_0_TreeSet_ovld(arguments[0]);
            }else if(vjo.java.util.Comparator.clazz.isInstance(arguments[0])){
                this.constructs_1_1_TreeSet_ovld(arguments[0]);
            }else if(vjo.java.util.SortedSet.clazz.isInstance(arguments[0])){
                this.constructs_1_3_TreeSet_ovld(arguments[0]);
            }else if(vjo.java.util.Collection.clazz.isInstance(arguments[0])){
                this.constructs_1_2_TreeSet_ovld(arguments[0]);
            }
        }else if(arguments.length===0){
            this.constructs_0_0_TreeSet_ovld();
        }
    },
    //> private constructs_1_0_TreeSet_ovld(SortedMap<E,Object> m)
    constructs_1_0_TreeSet_ovld:function(m){
        this.base();
        this.m=m;
        this.keySet=m.keySet();
    },
    //> private constructs_0_0_TreeSet_ovld()
    constructs_0_0_TreeSet_ovld:function(){
        this.constructs(new vjo.java.util.TreeMap());
    },
    //> private constructs_1_1_TreeSet_ovld(Comparator<? super E> c)
    constructs_1_1_TreeSet_ovld:function(c){
        this.constructs(new vjo.java.util.TreeMap(c));
    },
    //> private constructs_1_2_TreeSet_ovld(Collection<? extends E> c)
    constructs_1_2_TreeSet_ovld:function(c){
        this.constructs_0_0_TreeSet_ovld();
        this.addAll(c);
    },
    //> private constructs_1_3_TreeSet_ovld(SortedSet<E> s)
    constructs_1_3_TreeSet_ovld:function(s){
        //this.constructs(s.comparator());
        this.constructs_1_1_TreeSet_ovld(s.comparator());
        this.addAll(s);
    },
    //> public Iterator<E> iterator()
    iterator:function(){
        return this.keySet.iterator();
    },
    //> public int size()
    size:function(){
        return this.m.size();
    },
    //> public boolean isEmpty()
    isEmpty:function(){
        return this.m.isEmpty();
    },
    //> public boolean add(E o)
    add:function(o){
        return this.m.put(o,this.vj$.TreeSet.PRESENT)===null;
    },
    //> public void clear()
    clear:function(){
        this.m.clear();
    },
    //> public boolean addAll(Collection<? extends E> c)
    addAll:function(c){
        //eBay Modification
        if(c === null) {
            throw new this.vj$.NullPointerException();
        }

        if(this.m.size()===0&&c.size()>0&&vjo.java.util.SortedSet.clazz.isInstance(c)&&vjo.java.util.TreeMap.clazz.isInstance(this.m)){
            var set=c;
            var map=this.m;
            var cc=set.comparator();
            var mc=map.comparator();
            if(cc===mc||(cc!==null&&cc.equals(mc))){
                map.addAllForTreeSet(set,this.vj$.TreeSet.PRESENT);
                return true;
            }
        }
        return this.base.addAll(c);
    },
    //> public SortedSet<E> subSet(E fromElement,E toElement)
    subSet:function(fromElement,toElement){
        return new this.vj$.TreeSet(this.m.subMap(fromElement,toElement));
    },
    //> public SortedSet<E> headSet(E toElement)
    headSet:function(toElement){
        return new this.vj$.TreeSet(this.m.headMap(toElement));
    },
    //> public SortedSet<E> tailSet(E fromElement)
    tailSet:function(fromElement){
        return new this.vj$.TreeSet(this.m.tailMap(fromElement));
    },
    //> public Comparator<? super E> comparator()
    comparator:function(){
        return this.m.comparator();
    },
    //> public E first()
    first:function(){
        return this.m.firstKey();
    },
    //> public E last()
    last:function(){
        return this.m.lastKey();
    },
    //> public boolean contains(Object o)
    contains:function(o){
        if(arguments.length===1){
            if(arguments[0] instanceof Object){
                return this.contains_1_0_TreeSet_ovld(arguments[0]);
            }else if(this.base && this.base.contains){
                return this.base.contains.apply(this,arguments);
            }
        }else if(this.base && this.base.contains){
            return this.base.contains.apply(this,arguments);
        }
    },
    //> private boolean contains_1_0_TreeSet_ovld(Object o)
    contains_1_0_TreeSet_ovld:function(o){
        return this.m.containsKey(o);
    },
    //> public boolean remove(Object o)
    remove:function(o){
        if(arguments.length===1){
            if(arguments[0] instanceof Object){
                return this.remove_1_0_TreeSet_ovld(arguments[0]);
            }else if(this.base && this.base.remove){
                return this.base.remove.apply(this,arguments);
            }
        }else if(this.base && this.base.remove){
            return this.base.remove.apply(this,arguments);
        }
    },
    //> private boolean remove_1_0_TreeSet_ovld(Object o)
    remove_1_0_TreeSet_ovld:function(o){
        return this.m.remove(o)===this.vj$.TreeSet.PRESENT;
    }
})
.inits(function(){
    this.vj$.TreeSet.PRESENT=new Object();
})
.endType();