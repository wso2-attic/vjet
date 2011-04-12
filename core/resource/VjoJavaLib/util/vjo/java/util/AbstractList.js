vjo.ctype('vjo.java.util.AbstractList<E>') //< public abstract
.needs(['vjo.java.lang.UnsupportedOperationException','vjo.java.lang.IndexOutOfBoundsException',
    'vjo.java.util.NoSuchElementException','vjo.java.lang.IllegalStateException',
    'vjo.java.util.RandomAccess','vjo.java.util.RandomAccessSubList', 'vjo.java.lang.NullPointerException',
    'vjo.java.util.SubList','vjo.java.lang.ObjectUtil','vjo.java.util.List'])
.needs('vjo.java.util.Iterator','')
.needs('vjo.java.util.Collection','')
.needs('vjo.java.util.ConcurrentModificationException','')
.inherits('vjo.java.util.AbstractCollection<E>')
.satisfies('vjo.java.util.List<E>')
.props({
    Itr:vjo.ctype() //< public Itr<E>
    .satisfies('vjo.java.util.Iterator<E>')
    .protos({
        cursor:0, //< int
        lastRet:-1, //< int
        expectedModCount:0, //< int
        list:null, //< protected AbstractList<E> list
        //> public constructs(AbstractList<E> list)
        constructs:function(list){
            this.list=list;
            this.expectedModCount=list.modCount;
        },
        //> public boolean hasNext()
        hasNext:function(){
            return this.cursor!==this.list.size();
        },
        //> public E next()
        next:function(){
            this.checkForComodification();
            try {
                var next=this.list.get(this.cursor);
                this.lastRet=this.cursor++;
                return next;
            }
            catch(e){
                this.checkForComodification();
                throw new this.vj$.NoSuchElementException();
            }
        },
        //> public void remove()
        remove:function(){
            if(this.lastRet===-1){
                throw new this.vj$.IllegalStateException();
            }
            this.checkForComodification();
            try {
                this.list.remove(this.lastRet);
                if(this.lastRet<this.cursor){
                    this.cursor--;
                }
                this.lastRet=-1;
                this.expectedModCount=this.list.modCount;
            }
            catch(e){
                throw new vjo.java.util.ConcurrentModificationException();
            }
        },
        //> final void checkForComodification()
        checkForComodification:function(){
            if(this.list.modCount!==this.expectedModCount){
                throw new vjo.java.util.ConcurrentModificationException();
            }
        }
    })
    .endType()
})
.protos({
    modCount:0, //< protected int
    ListItr:vjo.ctype() //< private
    .inherits('vjo.java.util.AbstractList.Itr<E>')
    .satisfies('vjo.java.util.Iterator<E>')
    .protos({
        //> constructs(AbstractList<E> list,int index)
        constructs:function(list,index){
            this.base(list);
            this.cursor=index;
        },
        //> public boolean hasPrevious()
        hasPrevious:function(){
            return this.cursor!==0;
        },
        //> public E previous()
        previous:function(){
            this.checkForComodification();
            try {
                var i=this.cursor-1;
                var previous=this.list.get(i);
                this.lastRet=this.cursor=i;
                return previous;
            }
            catch(e){
                this.checkForComodification();
                throw new this.vj$.NoSuchElementException();
            }
        },
        //> public int nextIndex()
        nextIndex:function(){
            return this.cursor;
        },
        //> public int previousIndex()
        previousIndex:function(){
            return this.cursor-1;
        },
        //> public void set(E o)
        set:function(o){
            if(this.lastRet===-1){
                throw new this.vj$.IllegalStateException();
            }
            this.checkForComodification();
            try {
                this.list.set(this.lastRet,o);            	
                this.expectedModCount=this.list.modCount;
            }
            catch(e){
                throw new vjo.java.util.ConcurrentModificationException();
            }
        },
        //> public void add(E o)
        add:function(o){
            this.checkForComodification();
            try {
                this.list.add(this.cursor++,o);
                this.lastRet=-1;
                this.expectedModCount=this.list.modCount;
            }
            catch(e){
                throw new vjo.java.util.ConcurrentModificationException();
            }
        }
    })
    .endType(),
    //> protected constructs()
    constructs:function(){
        this.base();
    },
    //> public boolean add(E o)
    //> public void add(int index,E element)
    add:function(o){
        if(arguments.length===1){
            return this.add_1_0_AbstractList_ovld(arguments[0]);
        }else if(arguments.length===2){
            this.add_2_0_AbstractList_ovld(arguments[0],arguments[1]);
        }
    },
    //> private boolean add_1_0_AbstractList_ovld(E o)
    add_1_0_AbstractList_ovld:function(o){
        this.add(this.size(),o);
        return true;
    },
    //> private void add_2_0_AbstractList_ovld(int index,E element)
    add_2_0_AbstractList_ovld:function(index,element){
    	throw new this.vj$.UnsupportedOperationException();
    },
    //> public abstract E get(int index)
    get:function(index){
    },
    //> public E set(int index,E element)
    set:function(index,element){
        throw new this.vj$.UnsupportedOperationException();
    },
    //> public E remove(int index)
    remove:function(index){
        throw new this.vj$.UnsupportedOperationException();
    },
    //> public int indexOf(Object o)
    indexOf:function(o){
        var e=this.listIterator();
        if(o===null){
            while(e.hasNext()){
                if(e.next()===null){
                    return e.previousIndex();
                }
            }
        }else {
            while(e.hasNext()){
                if(vjo.java.lang.ObjectUtil.equals(o,e.next())){
                    return e.previousIndex();
                }
            }
        }
        return -1;
    },
    //> public int lastIndexOf(Object o)
    lastIndexOf:function(o){
        var e=listIterator(size());
        if(o===null){
            while(e.hasPrevious()){
                if(e.previous()===null){
                    return e.nextIndex();
                }
            }
        }else {
            while(e.hasPrevious()){
                if(vjo.java.lang.ObjectUtil.equals(o,e.previous())){
                    return e.nextIndex();
                }
            }
        }
        return -1;
    },
    //> public void clear()
    clear:function(){
        this.removeRange(0,this.size());
    },
    //> public boolean addAll(int index,Collection<? extends E> c)
    addAll:function(index,c){
        //eBay Modification
        if(c  === null) {
            throw new this.vj$.NullPointerException();
        }

        var modified=false;
        var e=c.iterator();
        while(e.hasNext()){
            this.add(index++,e.next());
            modified=true;
        }
        return modified;
    },
    //> public Iterator<E> iterator()
    iterator:function(){
        return new this.vj$.AbstractList.Itr(this);
    },
    //> public Iterator<E> listIterator()
    //> public Iterator<E> listIterator(final int index)
    listIterator:function(){
        if(arguments.length===0){
            return this.listIterator_0_0_AbstractList_ovld();
        }else if(arguments.length===1){
            if(typeof arguments[0]=="number"){
                return this.listIterator_1_0_AbstractList_ovld(arguments[0]);
            }
        }
    },
    //> private Iterator<E> listIterator_0_0_AbstractList_ovld()
    listIterator_0_0_AbstractList_ovld:function(){
        return this.listIterator(0);
    },
    //> private Iterator<E> listIterator_1_0_AbstractList_ovld(final int index)
    listIterator_1_0_AbstractList_ovld:function(index){
        if(index<0||index>this.size()){
            throw new this.vj$.IndexOutOfBoundsException("Index: "+index);
        }
        return new this.ListItr(this,index);
    },
    //> public List<E> subList(int fromIndex,int toIndex)
    subList:function(fromIndex,toIndex){
        return this.vj$.RandomAccess.clazz.isInstance(this)?new this.vj$.RandomAccessSubList(this,fromIndex,toIndex):new this.vj$.SubList(this,fromIndex,toIndex);
    },
    //> public boolean equals(Object o)
    equals:function(o){
        if(o===this){
            return true;
        }
        if(!vjo.java.util.List.clazz.isInstance(o)){
            return false;
        }
        var e1=this.listIterator();
        var e2=o.listIterator();
        while(e1.hasNext() && e2.hasNext()){
            var o1=e1.next();
            var o2=e2.next();
            if(!(o1===null?o2===null:vjo.java.lang.ObjectUtil.equals(o1,o2))){
                return false;
            }
        }
        return !(e1.hasNext() || e2.hasNext());
    },
    //> public int hashCode()
    hashCode:function(){
        var hashCode=1;
        var i=this.iterator();
        while(i.hasNext()){
            var obj=i.next();
            hashCode=31*hashCode+(obj===null?0:vjo.java.lang.ObjectUtil.hashCode(obj));
        }
        return hashCode;
    },
    //> protected void removeRange(int fromIndex,int toIndex)
    removeRange:function(fromIndex,toIndex){
        var it=this.listIterator(fromIndex);
        for (var i=0,n=toIndex-fromIndex;i<n;i++){
            it.next();
            it.remove();
        }
    }
})
.endType();