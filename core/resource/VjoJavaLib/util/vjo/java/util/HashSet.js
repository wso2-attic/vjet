vjo.ctype('vjo.java.util.HashSet<E>') //< public
.needs(['vjo.java.lang.Math','vjo.java.util.LinkedHashMap',
    'vjo.java.lang.Util','vjo.java.util.AbstractCollection'])
.needs('vjo.java.util.HashMap','')
.needs('vjo.java.util.Collection','')
.needs('vjo.java.util.Iterator','')
.inherits('vjo.java.util.AbstractSet<E>')
.satisfies('vjo.java.util.Set<E>')
.satisfies('vjo.java.lang.Cloneable')
.satisfies('vjo.java.io.Serializable')
.props({
    serialVersionUID:-5024744406713321676, //< final long
    PRESENT:null //< private final Object
})
.protos({
    map:null, //< private HashMap<E,Object> map
    //> public constructs()
    //> public constructs(Collection<? extends E> c)
    //> public constructs(int initialCapacity,float loadFactor)
    //> public constructs(int initialCapacity)
    //> constructs(int initialCapacity,float loadFactor,boolean dummy)
    constructs:function(){
        if(arguments.length===0){
            this.constructs_0_0_HashSet_ovld();
        }else if(arguments.length===1){
            if(typeof arguments[0]=="number"){
                this.constructs_1_1_HashSet_ovld(arguments[0]);
            } else if(vjo.java.util.Collection.clazz.isInstance(arguments[0])){
                this.constructs_1_0_HashSet_ovld(arguments[0]);
            }
        }else if(arguments.length===2){
            this.constructs_2_0_HashSet_ovld(arguments[0],arguments[1]);
        }else if(arguments.length===3){
            this.constructs_3_0_HashSet_ovld(arguments[0],arguments[1],arguments[2]);
        }
    },
    //> private constructs_0_0_HashSet_ovld()
    constructs_0_0_HashSet_ovld:function(){
        this.base();
        this.map=new vjo.java.util.HashMap();
    },
    //> private constructs_1_0_HashSet_ovld(Collection<? extends E> c)
    constructs_1_0_HashSet_ovld:function(c){
        this.base();
        this.map=new vjo.java.util.HashMap(this.vj$.Math.max(this.vj$.Util.cast((c.size()/.75),'int')+1,16));
        this.addAll(c);
    },
    //> private constructs_2_0_HashSet_ovld(int initialCapacity,float loadFactor)
    constructs_2_0_HashSet_ovld:function(initialCapacity,loadFactor){
        this.base();
        this.map=new vjo.java.util.HashMap(initialCapacity,loadFactor);
    },
    //> private constructs_1_1_HashSet_ovld(int initialCapacity)
    constructs_1_1_HashSet_ovld:function(initialCapacity){
        this.base();
        this.map=new vjo.java.util.HashMap(initialCapacity);
    },
    //> private constructs_3_0_HashSet_ovld(int initialCapacity,float loadFactor,boolean dummy)
    constructs_3_0_HashSet_ovld:function(initialCapacity,loadFactor,dummy){
        this.base();
        this.map=new this.vj$.LinkedHashMap(initialCapacity,loadFactor);
    },
    //> public Iterator<E> iterator()
    iterator:function(){
        return this.map.keySet().iterator();
    },
    //> public int size()
    size:function(){
        return this.map.size();
    },
    //> public boolean isEmpty()
    isEmpty:function(){
        return this.map.isEmpty();
    },
    //> public boolean add(E o)
    add:function(o){
        return this.map.put(o,this.vj$.HashSet.PRESENT)===null;
    },
    //> public void clear()
    clear:function(){
        this.map.clear();
    },
    //> public boolean contains(Object o)
    contains:function(o){
        if(arguments.length===1){
            if(arguments[0] instanceof Object){
                return this.contains_1_0_HashSet_ovld(arguments[0]);
            }else if(this.base && this.base.contains){
                return this.base.contains.apply(this,arguments);
            }
        }else if(this.base && this.base.contains){
            return this.base.contains.apply(this,arguments);
        }
    },
    //> private boolean contains_1_0_HashSet_ovld(Object o)
    contains_1_0_HashSet_ovld:function(o){
        return this.map.containsKey(o);
    },
    //> public boolean remove(Object o)
    remove:function(o){
        if(arguments.length===1){
            if(arguments[0] instanceof Object){
                return this.remove_1_0_HashSet_ovld(arguments[0]);
            }else if(this.base && this.base.remove){
                return this.base.remove.apply(this,arguments);
            }
        }else if(this.base && this.base.remove){
            return this.base.remove.apply(this,arguments);
        }
    },
    //> private boolean remove_1_0_HashSet_ovld(Object o)
    remove_1_0_HashSet_ovld:function(o){
        return this.map.remove(o)===this.vj$.HashSet.PRESENT;
    }
})
.inits(function(){
    this.vj$.HashSet.PRESENT=new Object();
})
.endType();