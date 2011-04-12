vjo.ctype('vjo.java.util.AbstractQueue<E>') //< public abstract
.needs(['vjo.java.lang.NullPointerException','vjo.java.lang.IllegalArgumentException',
    'vjo.java.lang.IllegalStateException','vjo.java.util.NoSuchElementException'])
.needs('vjo.java.util.Collection','')
.needs('vjo.java.util.Iterator','')
.inherits('vjo.java.util.AbstractCollection<E>')
.satisfies('vjo.java.util.Queue<E>')
.protos({
    //> protected constructs()
    constructs:function(){
        this.base();
    },
    //> public boolean add(E o)
    add:function(o){
        //eBay Modification
        if(o  === null) {
            throw new this.vj$.NullPointerException();
        }

        if(this.offer(o)){
            return true;
        }else {
            throw new this.vj$.IllegalStateException("Queue full");
        }
    },
    //> public E remove()
    //> public boolean remove(Object o)
    remove:function(){
        if(arguments.length===0){
            return this.remove_0_0_AbstractQueue_ovld();
        }else if(arguments.length===1){
             return this.remove_1_0_AbstractQueue_ovld(arguments[0]);
        }else if(this.base && this.base.remove){
            return this.base.remove.apply(this,arguments);
        }
    },
    //> private E remove_0_0_AbstractQueue_ovld()
    remove_0_0_AbstractQueue_ovld:function(){
        var x=this.poll();
        if(x!==null){
            return x;
        }else {
            throw new this.vj$.NoSuchElementException();
        }
    },
    //> private boolean remove_1_0_AbstractQueue_ovld(Object o)
    remove_1_0_AbstractQueue_ovld:function(o){
    },
    //> public E element()
    element:function(){
        var x=this.peek();
        if(x!==null){
            return x;
        }else {
            throw new this.vj$.NoSuchElementException();
        }
    },
    //> public void clear()
    clear:function(){
        while(this.poll()!==null){
        }
    },
    //> public boolean addAll(Collection<? extends E> c)
    addAll:function(c){
        if(c===null){
            throw new this.vj$.NullPointerException();
        }
        if(c===this){
            throw new this.vj$.IllegalArgumentException();
        }
        var modified=false;
        var e=c.iterator();
        while(e.hasNext()){
            if(this.add(e.next())){
                modified=true;
            }
        }
        return modified;
    }
})
.endType();