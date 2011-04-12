vjo.ctype('vjo.java.util.PriorityQueue<E>') //< public
.needs(['vjo.java.lang.IllegalArgumentException','vjo.java.lang.System',
    'vjo.java.lang.NullPointerException','vjo.java.lang.Integer',
    'vjo.java.util.Queue','vjo.java.lang.Comparable',
    'vjo.java.util.NoSuchElementException','vjo.java.lang.IllegalStateException',
    'vjo.java.lang.Util'])
.needs('vjo.java.util.Comparator','')
.needs('vjo.java.util.Collection','')
.needs('vjo.java.util.SortedSet','')
.needs('vjo.java.util.Iterator','')
.needs('vjo.java.util.ArrayList','')
.needs('vjo.java.util.ConcurrentModificationException','')
.needs('vjo.java.lang.Math','')
.inherits('vjo.java.util.AbstractQueue<E>')
.satisfies('vjo.java.io.Serializable')
.props({
    serialVersionUID:-7720805057305804111, //< private final long
    DEFAULT_INITIAL_CAPACITY:11 //< private final int
})
.protos({
    queue:null, //< private Object[]
    size_:0, //< private int
    comparator_:null, //< private final Comparator<? super E> comparator_
    modCount:0, //< private int
    Itr:vjo.ctype() //< private
    .satisfies('vjo.java.util.Iterator<E>')
    .protos({
        cursor:1, //< private int
        lastRet:0, //< private int
        expectedModCount:0, //< private int
        forgetMeNot:null, //< private ArrayList<E> forgetMeNot
        lastRetElt:null, //< private Object
        //> private constructs()
        constructs:function(){
            this.expectedModCount=this.vj$.outer.modCount;
        },
        //> public boolean hasNext()
        hasNext:function(){
            return this.cursor<=this.vj$.outer.size_||this.forgetMeNot!==null;
        },
        //> public E next()
        next:function(){
            this.checkForComodification();
            var result;
            if(this.cursor<=this.vj$.outer.size_){
                result=this.vj$.outer.queue[this.cursor];
                this.lastRet=this.cursor++;
            }else if(this.forgetMeNot===null){
                throw new this.vj$.NoSuchElementException();
            }else {
                var remaining=this.forgetMeNot.size_();
                result=this.forgetMeNot.remove(remaining-1);
                if(remaining===1){
                    this.forgetMeNot=null;
                }
                this.lastRet=0;
                this.lastRetElt=result;
            }
            return result;
        },
        //> public void remove()
        remove:function(){
            this.checkForComodification();
            if(this.lastRet!==0){
                var moved=this.vj$.outer.removeAt(this.lastRet);
                this.lastRet=0;
                if(moved===null){
                    this.cursor--;
                }else {
                    if(this.forgetMeNot===null){
                        this.forgetMeNot=new vjo.java.util.ArrayList();
                    }
                    this.forgetMeNot.add(moved);
                }
            }else if(this.lastRetElt!==null){
                this.vj$.outer.remove(this.lastRetElt);
                this.lastRetElt=null;
            }else {
                throw new this.vj$.IllegalStateException();
            }
            this.expectedModCount=this.vj$.outer.modCount;
        },
        //> final void checkForComodification()
        checkForComodification:function(){
            if(this.vj$.outer.modCount!==this.expectedModCount){
                throw new vjo.java.util.ConcurrentModificationException();
            }
        }
    })
    .endType(),
    //> public constructs()
    //> public constructs(int initialCapacity)
    //> public constructs(int initialCapacity,Comparator<? super E> comparator)
    //> public constructs(Collection<? extends E> c)
    //> public constructs(PriorityQueue<? extends E> c)
    //> public constructs(SortedSet<? extends E> c)
    constructs:function(){
        if(arguments.length===0){
            this.constructs_0_0_PriorityQueue_ovld();
        }else if(arguments.length===1){
            if(typeof arguments[0]=="number"){
                this.constructs_1_0_PriorityQueue_ovld(arguments[0]);
            }else if(vjo.java.util.Collection.clazz.isInstance(arguments[0])){
                this.constructs_1_1_PriorityQueue_ovld(arguments[0]);
            }else if(vjo.java.util.PriorityQueue.clazz.isInstance(arguments[0])){
                this.constructs_1_2_PriorityQueue_ovld(arguments[0]);
            }else if(vjo.java.util.SortedSet.clazz.isInstance(arguments[0])){
                this.constructs_1_3_PriorityQueue_ovld(arguments[0]);
            }
        }else if(arguments.length===2){
            this.constructs_2_0_PriorityQueue_ovld(arguments[0],arguments[1]);
        }
    },
    //> private constructs_0_0_PriorityQueue_ovld()
    constructs_0_0_PriorityQueue_ovld:function(){
        this.constructs_2_0_PriorityQueue_ovld(this.vj$.PriorityQueue.DEFAULT_INITIAL_CAPACITY,null);
    },
    //> private constructs_1_0_PriorityQueue_ovld(int initialCapacity)
    constructs_1_0_PriorityQueue_ovld:function(initialCapacity){
        this.constructs_2_0_PriorityQueue_ovld(initialCapacity,null);
    },
    //> private constructs_2_0_PriorityQueue_ovld(int initialCapacity,Comparator<? super E> comparator)
    constructs_2_0_PriorityQueue_ovld:function(initialCapacity,comparator){
        this.base();
        if(initialCapacity<1){
            throw new this.vj$.IllegalArgumentException();
        }
        this.queue=vjo.createArray(null, initialCapacity+1);
        this.comparator_=comparator;
    },
    //> private void initializeArray(Collection<? extends E> c)
    initializeArray:function(c){
        var sz=c.size();
        var initialCapacity=this.vj$.Util.cast(Math.min(parseInt((sz*110)/100),this.vj$.Integer.MAX_VALUE-1),'int');
        if(initialCapacity<1){
            initialCapacity=1;
        }
        this.queue=vjo.createArray(null, initialCapacity+1);
    },
    //> private void fillFromSorted(Collection<? extends E> c)
    fillFromSorted:function(c){
	    for (var i=c.iterator();i.hasNext();){
	    	this.queue[++this.size_]=i.next();
		}
    },
    //> private void fillFromUnsorted(Collection<? extends E> c)
    fillFromUnsorted:function(c){
	    for (var i=c.iterator();i.hasNext();){
		    this.queue[++this.size_]=i.next();
		}
		this.heapify();
    },
    //> private constructs_1_1_PriorityQueue_ovld(Collection<? extends E> c)
    constructs_1_1_PriorityQueue_ovld:function(c){
        this.base();
        this.initializeArray(c);
        if(vjo.java.util.SortedSet.clazz.isInstance(c)){
            var s=c;
            this.comparator_=s.comparator();
            this.fillFromSorted(s);
        }else if(vjo.java.util.PriorityQueue.clazz.isInstance(c)){
            var s=c;
            this.comparator_=s.comparator();
            this.fillFromSorted(s);
        }else {
            this.comparator_=null;
            this.fillFromUnsorted(c);
        }
    },
    //> private constructs_1_2_PriorityQueue_ovld(PriorityQueue<? extends E> c)
    constructs_1_2_PriorityQueue_ovld:function(c){
        this.base();
        this.initializeArray(c);
        this.comparator_=c.comparator();
        this.fillFromSorted(c);
    },
    //> private constructs_1_3_PriorityQueue_ovld(SortedSet<? extends E> c)
    constructs_1_3_PriorityQueue_ovld:function(c){
        this.base();
        this.initializeArray(c);
        this.comparator_=c.comparator();
        this.fillFromSorted(c);
    },
    //> private void grow(int index)
    grow:function(index){
        var newlen=this.queue.length;
        if(index<newlen){
            return;
        }
        if(index===this.vj$.Integer.MAX_VALUE){
            throw new this.vj$.OutOfMemoryError();
        }
        while(newlen<=index){
            if(newlen>=parseInt(this.vj$.Integer.MAX_VALUE/2)){
                newlen=this.vj$.Integer.MAX_VALUE;
            }else {
                newlen<<=2;
            }
        }
        var newQueue=vjo.createArray(null, newlen);
        this.vj$.System.arraycopy(this.queue,0,newQueue,0,this.queue.length);
        this.queue=newQueue;
    },
    //> public boolean offer(E o)
    offer:function(o){
        if(o===null){
            throw new this.vj$.NullPointerException();
        }
        this.modCount++;
        ++this.size_;
        if(this.size_>=this.queue.length){
            this.grow(this.size_);
        }
        this.queue[this.size_]=o;
        this.fixUp(this.size_);
        return true;
    },
    //> public E peek()
    peek:function(){
        if(this.size_===0){
            return null;
        }
        return this.queue[1];
    },
    //> public boolean add(E o)
    add:function(o){
        return this.offer(o);
    },
    //> public Iterator<E> remove()
    //> public Iterator<E> remove(int index)
    remove:function(){
        if(arguments.length===1){
             return this.remove_1_0_AbstractQueue_ovld(arguments[0]);
        }else if(this.base && this.base.remove){
            return this.base.remove.apply(this,arguments);
        }
    },
    //> private boolean remove_1_0_AbstractQueue_ovld(Object o)
    remove_1_0_AbstractQueue_ovld:function(o){
        if(o===null){
            return false;
        }
        if(this.comparator_===null){
            for (var i=1;i<=this.size_;i++){
                if(vjo.java.lang.ObjectUtil.compareTo(this.queue[i], o)===0){
                    this.removeAt(i);
                    return true;
                }
            }
        }else {
            for (var i=1;i<=this.size_;i++){
                if(this.comparator_.compare(this.queue[i],o)===0){
                    this.removeAt(i);
                    return true;
                }
            }
        }
        return false;
    },
    //> public Iterator<E> iterator()
    iterator:function(){
        return new this.Itr();
    },
    //> public int size()
    size:function(){
        return this.size_;
    },
    //> public void clear()
    clear:function(){
        this.modCount++;
        for (var i=1;i<=this.size_;i++){
            this.queue[i]=null;
        }
        this.size_=0;
    },
    //> public E poll()
    poll:function(){
        if(this.size_===0){
            return null;
        }
        this.modCount++;
        var result=this.queue[1];
        this.queue[1]=this.queue[this.size_];
        this.queue[this.size_--]=null;
        if(this.size_>1){
            this.fixDown(1);
        }
        return result;
    },
    //> private E removeAt(int i)
    removeAt:function(i){
        this.modCount++;
        var moved=this.queue[this.size_];
        this.queue[i]=moved;
        this.queue[this.size_--]=null;
        if(i<=this.size_){
            this.fixDown(i);
            if(this.queue[i]===moved){
                this.fixUp(i);
                if(this.queue[i]!==moved){
                    return moved;
                }
            }
        }
        return null;
    },
    //> private void fixUp(int k)
    fixUp:function(k){
        if(this.comparator_===null){
            while(k>1){
                var j=k>>1;
                if(vjo.java.lang.ObjectUtil.compareTo(this.queue[j], this.queue[k])<=0){
                    break;
                }
                var tmp=this.queue[j];
                this.queue[j]=this.queue[k];
                this.queue[k]=tmp;
                k=j;
            }
        }else {
            while(k>1){
                var j=k>>>1;
                if(this.comparator_.compare(this.queue[j],this.queue[k])<=0){
                    break;
                }
                var tmp=this.queue[j];
                this.queue[j]=this.queue[k];
                this.queue[k]=tmp;
                k=j;
            }
        }
    },
    //> private void fixDown(int k)
    fixDown:function(k){
        var j;
        if(this.comparator_===null){
            while((j=k<<1)<=this.size_ && (j>0)){
                if(j<this.size_&&(vjo.java.lang.ObjectUtil.compareTo(this.queue[j], this.queue[j+1])>0)){
                    j++;
                }
                if(vjo.java.lang.ObjectUtil.compareTo(this.queue[k], this.queue[j])<=0){
                    break;
                }
                var tmp=this.queue[j];
                this.queue[j]=this.queue[k];
                this.queue[k]=tmp;
                k=j;
            }
        }else {
            while((j=k<<1)<=this.size_ && (j>0)){
                if(j<this.size_&&this.comparator_.compare(this.queue[j],this.queue[j+1])>0){
                    j++;
                }
                if(this.comparator_.compare(this.queue[k],this.queue[j])<=0){
                    break;
                }
                var tmp=this.queue[j];
                this.queue[j]=this.queue[k];
                this.queue[k]=tmp;
                k=j;
            }
        }
    },
    //> private void heapify()
    heapify:function(){
        for (var i=parseInt(this.size_/2);i>=1;i--){
            this.fixDown(i);
        }
    },
    //> public Comparator<? super E> comparator()
    comparator:function(){
        return this.comparator_;
    }
})
.endType();