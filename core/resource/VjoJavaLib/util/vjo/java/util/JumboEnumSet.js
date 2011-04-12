vjo.ctype('vjo.java.util.JumboEnumSet<E extends Enum>') //< public
.needs(['vjo.java.lang.Long','vjo.java.lang.IllegalStateException',
    'vjo.java.lang.ClassCastException','vjo.java.lang.Enum',
    'vjo.java.util.Iterator','vjo.java.util.NoSuchElementException',
    'vjo.java.util.Collection','vjo.java.lang.ClassUtil'])
.needs('vjo.java.util.Arrays','')
//>needs(vjo.Enum)
.inherits('vjo.java.util.EnumSet<E>')
.protos({
    elements:null, //< private long[]
    size_:0, //< private int
    EnumSetIterator:vjo.ctype() //< public EnumSetIterator<E extends Enum>
    .satisfies('vjo.java.util.Iterator<E>')
    .protos({
        unseen:0, //< long
        unseenIndex:0, //< int
        lastReturned:0, //< long
        lastReturnedIndex:0, //< int
        //> constructs()
        constructs:function(){
            this.unseen=this.vj$.outer.elements[0];
        },
        //> public boolean hasNext()
        hasNext:function(){
            while(this.unseen===0 && this.unseenIndex<this.vj$.outer.elements.length-1){
                this.unseen=this.vj$.outer.elements[++this.unseenIndex];
            }
            return this.unseen!==0;
        },
        //> public E next()
        next:function(){
            if(!this.hasNext()){
                throw new this.vj$.NoSuchElementException();
            }
            this.lastReturned=this.unseen& -this.unseen;
            this.lastReturnedIndex=this.unseenIndex;
            this.unseen-=this.lastReturned;
            return this.vj$.outer.universe[(this.lastReturnedIndex<<5)+this.vj$.EnumSet.numberOfTrailingZeros(this.lastReturned)];
        },
        //> public void remove()
        remove:function(){
            if(this.lastReturned===0){
                throw new this.vj$.IllegalStateException();
            }
            this.vj$.outer.elements[this.lastReturnedIndex]-=this.lastReturned;
            this.vj$.outer.size_--;
            this.lastReturned=0;
        }
    })
    .endType(),
    //> constructs(vjo.Class<E> elementType,Enum[] universe)
    constructs:function(elementType,universe){
        this.base(elementType,universe);
        this.elements=vjo.createArray(0, (universe.length+31)>>>5);
    },
    //> void addRange(E from,E to)
    addRange:function(from,to){
        var fromIndex=from.ordinal()>>>5; //<int
        var toIndex=to.ordinal()>>>5; //<int
        if(fromIndex===toIndex){
            this.elements[fromIndex]=(-1>>>(from.ordinal()-to.ordinal()-1))<<from.ordinal();
        }else {
            this.elements[fromIndex]=(-1<<from.ordinal());
            for (var i=fromIndex+1;i<toIndex;i++){
                this.elements[i]=-1;
            }
            this.elements[toIndex]=-1>>>(31-to.ordinal());
        }
        this.size_=to.ordinal()-from.ordinal()+1;
    },
    //> void complement()
    complement:function(){
        for (var i=0;i<this.elements.length;i++){
            this.elements[i]=~this.elements[i];
        }
        this.elements[this.elements.length-1]&=(-1>>> -this.universe.length);
        this.size_=this.universe.length-this.size_;
    },
    //> public Iterator<E> iterator()
    iterator:function(){
        return new this.EnumSetIterator();
    },
    //> public int size()
    size:function(){
        return this.size_;
    },
    //> public boolean isEmpty()
    isEmpty:function(){
        return this.size_===0;
    },
    //> public boolean contains(Object e)
    contains:function(e){
        if(e===null){
            return false;
        }
        var eClass=e.getClass(); //<Class
        if(eClass!==this.elementType&&this.vj$.ClassUtil.getSuperclass(eClass)!==this.elementType){
            return false;
        }
        var eOrdinal=e.ordinal(); //<int
        return (this.elements[eOrdinal>>>5]&(1<<eOrdinal))!==0;
    },
    //> public boolean add(E e)
    add:function(e){
        this.typeCheck(e);
        var eOrdinal=e.ordinal(); //<int
        var eWordNum=eOrdinal>>>5; //<int
        var oldElements=this.elements[eWordNum]; //<long
        this.elements[eWordNum]|=(1<<eOrdinal);
        var result=(this.elements[eWordNum]!==oldElements); //<boolean
        if(result){
            this.size_++;
        }
        return result;
    },
    //> public boolean remove(Object e)
    remove:function(e){
        if(e===null){
            return false;
        }
        var eClass=e.getClass(); //<Class
        if(eClass!==this.elementType&&this.vj$.ClassUtil.getSuperclass(eClass)!==this.elementType){
            return false;
        }
        var eOrdinal=e.ordinal(); //<int
        var eWordNum=eOrdinal>>>5; //<int
        var oldElements=this.elements[eWordNum]; //<long
        this.elements[eWordNum]&=~(1<<eOrdinal);
        var result=(this.elements[eWordNum]!==oldElements); //<boolean
        if(result){
            this.size_--;
        }
        return result;
    },
    //> public boolean containsAll(Collection<?> c)
    containsAll:function(c){
        if(!(vjo.java.util.JumboEnumSet.clazz.isInstance(c))){
            return  this.base.containsAll(c);
        }
        var es=c; //<<JumboEnumSet
        if(es.elementType!==this.elementType){
            return es.isEmpty();
        }
        for (var i=0;i<this.elements.length;i++){
            if((es.elements[i]&~this.elements[i])!==0){
                return false;
            }
        }
        return true;
    },
    //> public void addAll()
    //> public boolean addAll(Collection<? extends E> c)
    addAll:function(){
        if(arguments.length===0){
            if(arguments.length==0){
                this.addAll_0_0_JumboEnumSet_ovld();
            }else if(this.base && this.base.addAll){
                this.base.addAll.apply(this,arguments);
            }
        }else if(arguments.length===1){
            if(vjo.java.util.Collection.clazz.isInstance(arguments[0])){
                return this.addAll_1_0_JumboEnumSet_ovld(arguments[0]);
            }else if(this.base && this.base.addAll){
                this.base.addAll.apply(this,arguments);
            }
        }else if(this.base && this.base.addAll){
            this.base.addAll.apply(this,arguments);
        }
    },
    //> private void addAll_0_0_JumboEnumSet_ovld()
    addAll_0_0_JumboEnumSet_ovld:function(){
        for (var i=0;i<this.elements.length;i++){
            this.elements[i]=-1;
        }
        this.elements[this.elements.length-1]>>>=-this.universe.length;
        this.size_=this.universe.length;
    },
    //> private boolean addAll_1_0_JumboEnumSet_ovld(Collection<? extends E> c)
    addAll_1_0_JumboEnumSet_ovld:function(c){
        if(!(vjo.java.util.JumboEnumSet.clazz.isInstance(c))){
            return  this.base.addAll(c);
        }
        var es=c; //<<JumboEnumSet
        if(es.elementType!==this.elementType){
            if(es.isEmpty()){
                return false;
            }else {
                throw new this.vj$.ClassCastException(es.elementType+" != "+this.elementType);
            }
        }
        for (var i=0;i<this.elements.length;i++){
            this.elements[i]|=es.elements[i];
        }
        return this.recalculateSize();
    },
    //> public boolean removeAll(Collection<?> c)
    removeAll:function(c){
        if(!(vjo.java.util.JumboEnumSet.clazz.isInstance(c))){
            return  this.base.removeAll(c);
        }
        var es=c; //<<JumboEnumSet
        if(es.elementType!==this.elementType){
            return false;
        }
        for (var i=0;i<this.elements.length;i++){
            this.elements[i]&=~es.elements[i];
        }
        return this.recalculateSize();
    },
    //> public boolean retainAll(Collection<?> c)
    retainAll:function(c){
        if(!(vjo.java.util.JumboEnumSet.clazz.isInstance(c))){
            return  this.base.retainAll(c);
        }
        var es=c; //<<JumboEnumSet
        if(es.elementType!==this.elementType){
            this.clear();
            return true;
        }
        for (var i=0;i<this.elements.length;i++){
            this.elements[i]&=es.elements[i];
        }
        return this.recalculateSize();
    },
    //> public void clear()
    clear:function(){
        vjo.java.util.Arrays.fill(this.elements,0);
        this.size_=0;
    },
    //> public boolean equals(Object o)
    equals:function(o){
        if(!(vjo.java.util.JumboEnumSet.clazz.isInstance(o))){
            return this.base.equals(o);
        }
        var es=o; //<<JumboEnumSet
        if(es.elementType!==this.elementType){
            return this.size_===0&&es.size_===0;
        }
        return vjo.java.util.Arrays.equals(es.elements,this.elements);
    },
    //> private boolean recalculateSize()
    recalculateSize:function(){
        var oldSize=this.size_; //<int
        this.size_=0;
        for (var elt,_$i=0;_$i<this.elements.length;_$i++){
            elt=this.elements[_$i];
            this.size_+=this.vj$.EnumSet.bitCount(elt);
        }
        return this.size_!==oldSize;
    }
})
.endType();