vjo.ctype('vjo.java.util.LinkedList<E>') //< public
.needs(['vjo.java.lang.IndexOutOfBoundsException','vjo.java.lang.IllegalStateException',
    'vjo.java.util.NoSuchElementException','vjo.java.lang.ObjectUtil','vjo.java.lang.Boolean'])
.needs('vjo.java.util.Collection','')
.needs('vjo.java.util.Iterator','')
.needs('vjo.java.util.ConcurrentModificationException','')
.inherits('vjo.java.util.AbstractSequentialList<E>')
.satisfies('vjo.java.util.List<E>')
.satisfies('vjo.java.util.Queue<E>')
.satisfies('vjo.java.lang.Cloneable')
.satisfies('vjo.java.io.Serializable')
.props({
    serialVersionUID:876323262645176354, //< private final long
    Entry:vjo.ctype() //< public Entry<E>
    .protos({
        element:null, //< E
        next:null, //< Entry<E> next
        previous:null, //< Entry<E> previous
        //> constructs(E element,Entry<E> next,Entry<E> previous)
        constructs:function(element,next,previous){
            this.element=element;
            this.next=next;
            this.previous=previous;
        }
    })
    .endType(),
    
	ListItr:vjo.ctype() //< private
	.satisfies('vjo.java.util.Iterator<E>')
	.protos({
	    lastReturned:null, //< private Entry<E> lastReturned
	    next_:null, //< private Entry<E> next_
	    nextIndex_:0, //< private int
	    expectedModCount:0, //< private int
	    list: null, //< private LinkedList<E> list
	    //> constructs(LinkedList<E>, int)
	    constructs:function(list, index){
	    	this.list = list;
	        this.lastReturned=list.header;
	        this.expectedModCount=list.modCount;
	        if(index<0||index>list.size_){
	            throw new this.vj$.IndexOutOfBoundsException("Index: "+index+", Size: "+list.size_);
	        }
	        if(index<(list.size_>>1)){
	            this.next_=list.header.next;
	            for (this.nextIndex_=0;this.nextIndex_<index;this.nextIndex_++){
	                this.next_=this.next_.next;
	            }
	        }else {
	            this.next_=list.header;
	            for (this.nextIndex_=list.size_;this.nextIndex_>index;this.nextIndex_--){
	                this.next_=this.next_.previous;
	            }
	        }
	    },
	    //> public boolean hasNext()
	    hasNext:function(){
	        return this.nextIndex_!==this.list.size_;
	    },
	    //> public E next()
	    next:function(){
	        this.checkForComodification();
	        if(this.nextIndex_===this.list.size_){
	            throw new vjo.java.util.NoSuchElementException();
	        }
	        this.lastReturned=this.next_;
	        this.next_=this.next_.next;
	        this.nextIndex_++;
	        return this.lastReturned.element;
	    },
	    //> public boolean hasPrevious()
	    hasPrevious:function(){
	        return this.nextIndex_!==0;
	    },
	    //> public E previous()
	    previous:function(){
	        if(this.nextIndex_===0){
	            throw new this.vj$.NoSuchElementException();
	        }
	        this.lastReturned=this.next_=this.next_.previous;
	        this.nextIndex_--;
	        this.checkForComodification();
	        return this.lastReturned.element;
	    },
	    //> public int nextIndex()
	    nextIndex:function(){
	        return this.nextIndex_;
	    },
	    //> public int previousIndex()
	    previousIndex:function(){
	        return this.nextIndex_-1;
	    },
	    //> public void remove()
	    remove:function(){
	        this.checkForComodification();
	        var lastNext=this.lastReturned.next;
	        try {
	            this.list.remove(this.lastReturned);
	        }
	        catch(e){
	            throw new this.vj$.IllegalStateException();
	        }
	        if(this.next_===this.lastReturned){
	            this.next_=lastNext;
	        }else {
	            this.nextIndex_--;
	        }
	        this.lastReturned=this.list.header;
	        this.expectedModCount++;
	    },
	    //> public void set(E o)
	    set:function(o){
	        if(this.lastReturned===this.list.header){
	            throw new this.vj$.IllegalStateException();
	        }
	        this.checkForComodification();
	        this.lastReturned.element=o;
	    },
	    //> public void add(E o)
	    add:function(o){
	        this.checkForComodification();
	        this.lastReturned=this.list.header;
	        this.list.addBefore(o,this.next_);
	        this.nextIndex_++;
	        this.expectedModCount++;
	    },
	    //> final void checkForComodification()
	    checkForComodification:function(){
	        if(this.list.modCount!==this.expectedModCount){
	            throw new this.vj$.ConcurrentModificationException();
	        }
	    }
	})
	.endType()
})
.protos({
    header:null, //< private Entry<E> header
    size_:0, //< private int
    
    //> public constructs()
    //> public constructs(Collection<? extends E> c)
    constructs:function(){
        this.header=new this.vj$.LinkedList.Entry(null,null,null);
        if(arguments.length===0){
            this.constructs_0_0_LinkedList_ovld();
        }else if(arguments.length===1){
            this.constructs_1_0_LinkedList_ovld(arguments[0]);
        }
    },
    //> private constructs_0_0_LinkedList_ovld()
    constructs_0_0_LinkedList_ovld:function(){
        this.base();
        this.header.next=this.header.previous=this.header;
    },
    //> private constructs_1_0_LinkedList_ovld(Collection<? extends E> c)
    constructs_1_0_LinkedList_ovld:function(c){
        this.constructs_0_0_LinkedList_ovld();
        this.addAll(c);
    },
    //> public E getFirst()
    getFirst:function(){
        if(this.size_===0){
            throw new this.vj$.NoSuchElementException();
        }
        return this.header.next.element;
    },
    //> public E getLast()
    getLast:function(){
        if(this.size_===0){
            throw new this.vj$.NoSuchElementException();
        }
        return this.header.previous.element;
    },
    //> public E removeFirst()
    removeFirst:function(){
        return this.remove(this.header.next);
    },
    //> public E removeLast()
    removeLast:function(){
        return this.remove(this.header.previous);
    },
    //> public void addFirst(E o)
    addFirst:function(o){
        this.addBefore(o,this.header.next);
    },
    //> public void addLast(E o)
    addLast:function(o){
        this.addBefore(o,this.header);
    },
    //> public boolean contains(Object o)
    contains:function(o){
        return this.indexOf(o)!== -1;
    },
    //> public int size()
    size:function(){
        return this.size_;
    },
    //> public boolean addAll(Collection<? extends E> c)
    //> public boolean addAll(int index,Collection<? extends E> c)
    addAll:function(c){
        if(arguments.length===1){
            if(vjo.java.util.Collection.clazz.isInstance(arguments[0])){
                return this.addAll_1_0_LinkedList_ovld(arguments[0]);
            }else if(this.base && this.base.addAll){
                return this.base.addAll.apply(this,arguments);
            }
        }else if(arguments.length===2){
            if(typeof arguments[0]=="number" && vjo.java.util.Collection.clazz.isInstance(arguments[1])){
                return this.addAll_2_0_LinkedList_ovld(arguments[0],arguments[1]);
            }else if(this.base && this.base.addAll){
                return this.base.addAll.apply(this,arguments);
            }
        }else if(this.base && this.base.addAll){
            return this.base.addAll.apply(this,arguments);
        }
    },
    //> private boolean addAll_1_0_LinkedList_ovld(Collection<? extends E> c)
    addAll_1_0_LinkedList_ovld:function(c){
        return this.addAll(this.size_,c);
    },
    //> private boolean addAll_2_0_LinkedList_ovld(int index,Collection<? extends E> c)
    addAll_2_0_LinkedList_ovld:function(index,c){
        if(index<0||index>this.size_){
            throw new this.vj$.IndexOutOfBoundsException("Index: "+index+", Size: "+this.size_);
        }
        var a=c.toArray();
        var numNew=a.length;
        if(numNew===0){
            return false;
        }
        this.modCount++;
        var successor=(index===this.size_?this.header:this.entry(index));
        var predecessor=successor.previous;
        for (var i=0;i<numNew;i++){
            var e=new this.vj$.LinkedList.Entry(a[i],successor,predecessor);
            predecessor.next=e;
            predecessor=e;
        }
        successor.previous=predecessor;
        this.size_+=numNew;
        return true;
    },
    //> public void clear()
    clear:function(){
        var e=this.header.next;
        while(e!==this.header){
            var next=e.next;
            e.next=e.previous=null;
            e.element=null;
            e=next;
        }
        this.header.next=this.header.previous=this.header;
        this.size_=0;
        this.modCount++;
    },
    //> public E get(int index)
    get:function(index){
        return this.entry(index).element;
    },
    //> public E set(int index,E element)
    set:function(index,element){
        var e=this.entry(index);
        var oldVal=e.element;
        e.element=element;
        return oldVal;
    },
    //> public boolean add(E o)
    //> public void add(int index,E element)
    add:function(o){
        if(arguments.length===1){
            return this.add_1_0_LinkedList_ovld(arguments[0]);
        }else if(arguments.length===2){
            if(typeof arguments[0]=="number"){
                this.add_2_0_LinkedList_ovld(arguments[0],arguments[1]);
            }
        }
    },
    //> private boolean add_1_0_LinkedList_ovld(E o)
    add_1_0_LinkedList_ovld:function(o){
        this.addBefore(o,this.header);
        return true;
    },
    //> private void add_2_0_LinkedList_ovld(int index,E element)
    add_2_0_LinkedList_ovld:function(index,element){
        this.addBefore(element,(index===this.size_?this.header:this.entry(index)));
    },
    //> public boolean remove(Object o)
    //> public E remove(int index)
    //> public E remove()
    //> private E remove(Entry<E> e)
    remove:function(o){
        if(arguments.length===1){
            if(typeof arguments[0]=="number"){
                return this.remove_1_1_LinkedList_ovld(arguments[0]);
            }else if(vjo.java.util.LinkedList.Entry.clazz.isInstance(arguments[0]) ){
                return this.remove_1_2_LinkedList_ovld(arguments[0]);
            }else if(arguments[0] instanceof Object){
                return this.remove_1_0_LinkedList_ovld(arguments[0]);
            }else if(this.base && this.base.remove){
                return this.base.remove.apply(this,arguments);
            }
        }else if(arguments.length===0){
            if(arguments.length==0){
                return this.remove_0_0_LinkedList_ovld();
            }else if(this.base && this.base.remove){
                return this.base.remove.apply(this,arguments);
            }
        }else if(this.base && this.base.remove){
            return this.base.remove.apply(this,arguments);
        }
    },
    //> private boolean remove_1_0_LinkedList_ovld(Object o)
    remove_1_0_LinkedList_ovld:function(o){
        if(o===null){
            for (var e=this.header.next;e!==this.header;e=e.next){
                if(e.element===null){
                    this.remove(e);
                    return true;
                }
            }
        }else {
            for (var e=this.header.next;e!==this.header;e=e.next){
                if(vjo.java.lang.ObjectUtil.equals(o,e.element)){
                    this.remove(e);
                    return true;
                }
            }
        }
        return false;
    },
    //> private E remove_1_1_LinkedList_ovld(int index)
    remove_1_1_LinkedList_ovld:function(index){
        return this.remove(this.entry(index));
    },
    //> private Entry<E> entry(int index)
    entry:function(index){
        if(index<0||index>=this.size_){
            throw new this.vj$.IndexOutOfBoundsException("Index: "+index+", Size: "+this.size_);
        }
        var e=this.header;
        if(index<(this.size_>>1)){
            for (var i=0;i<=index;i++){
                e=e.next;
            }
        }else {
            for (var i=this.size_;i>index;i--){
                e=e.previous;
            }
        }
        return e;
    },
    //> public int indexOf(Object o)
    indexOf:function(o){
        var index=0;
        if(o===null){
            for (var e=this.header.next;e!==this.header;e=e.next){
                if(e.element===null){
                    return index;
                }
                index++;
            }
        }else {
            for (var e=this.header.next;e!==this.header;e=e.next){
                if(vjo.java.lang.ObjectUtil.equals(o,e.element)){
                    return index;
                }
                index++;
            }
        }
        return -1;
    },
    //> public int lastIndexOf(Object o)
    lastIndexOf:function(o){
        var index=this.size_;
        if(o===null){
            for (var e=this.header.previous;e!==this.header;e=e.previous){
                index--;
                if(e.element===null){
                    return index;
                }
            }
        }else {
            for (var e=this.header.previous;e!==this.header;e=e.previous){
                index--;
                if(vjo.java.lang.ObjectUtil.equals(o,e.element)){
                    return index;
                }
            }
        }
        return -1;
    },
    //> public E peek()
    peek:function(){
        if(this.size_===0){
            return null;
        }
        return this.getFirst();
    },
    //> public E element()
    element:function(){
        return this.getFirst();
    },
    //> public E poll()
    poll:function(){
        if(this.size_===0){
            return null;
        }
        return this.removeFirst();
    },
    //> private E remove_0_0_LinkedList_ovld()
    remove_0_0_LinkedList_ovld:function(){
        return this.removeFirst();
    },
    //> public boolean offer(E o)
    offer:function(o){
        return this.add(o);
    },
    //> public Iterator<E> listIterator()
    //> public Iterator<E> listIterator(int index)
    listIterator:function(){
        if(arguments.length===1){
            if(typeof arguments[0]=="number"){
                return this.listIterator_1_0_LinkedList_ovld(arguments[0]);
            }else if(this.base && this.base.listIterator){
                return this.base.listIterator.apply(this,arguments);
            }
        }else if(this.base && this.base.listIterator){
            return this.base.listIterator.apply(this,arguments);
        }
    },
    //> public Iterator<E> listIterator(int index)
    listIterator_1_0_LinkedList_ovld:function(index){
        return new this.vj$.LinkedList.ListItr(this, index);
    },    
    //> private Entry<E> addBefore(E o,Entry<E> e)
    addBefore:function(o,e){
        var newEntry=new this.vj$.LinkedList.Entry(o,e,e.previous);
        newEntry.previous.next=newEntry;
        newEntry.next.previous=newEntry;
        this.size_++;
        this.modCount++;
        return newEntry;
    },
    //> private E remove_1_2_LinkedList_ovld(Entry<E> e)
    remove_1_2_LinkedList_ovld:function(e){
        if(e===this.header){
            throw new this.vj$.NoSuchElementException();
        }
        var result=e.element;
        e.previous.next=e.next;
        e.next.previous=e.previous;
        e.next=e.previous=null;
        e.element=null;
        this.size_--;
        this.modCount++;
        return result;
    },
    //> public Object[] toArray()
    //> public <T> T[] toArray(T[] a)
    toArray:function(){
        if(arguments.length===0){
            if(arguments.length==0){
                return this.toArray_0_0_LinkedList_ovld();
            }else if(this.base && this.base.toArray){
                return this.base.toArray.apply(this,arguments);
            }
        }else if(arguments.length===1){
            if(arguments[0] instanceof Array){
                return this.toArray_1_0_LinkedList_ovld(arguments[0]);
            }else if(this.base && this.base.toArray){
                return this.base.toArray.apply(this,arguments);
            }
        }else if(this.base && this.base.toArray){
            return this.base.toArray.apply(this,arguments);
        }
    },
    //> private Object[] toArray_0_0_LinkedList_ovld()
    toArray_0_0_LinkedList_ovld:function(){
        var result=vjo.createArray(null, this.size_);
        var i=0;
        for (var e=this.header.next;e!==this.header;e=e.next){
            result[i++]=e.element;
        }
        return result;
    },
    //> private <T> T[] toArray_1_0_LinkedList_ovld(T[] a)
    toArray_1_0_LinkedList_ovld:function(a){
        if(a.length<this.size_){
            a=vjo.java.lang.reflect.Array.newInstance(null,this.size_);
        }
        var i=0;
        var result=a;
        for (var e=this.header.next;e!==this.header;e=e.next){
            result[i++]=e.element;
        }
        if(a.length>this.size_){
            a[this.size_]=null;
        }
        return a;
    }
})
.endType();