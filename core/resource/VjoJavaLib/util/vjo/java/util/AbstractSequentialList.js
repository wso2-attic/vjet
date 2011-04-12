vjo.ctype('vjo.java.util.AbstractSequentialList<E>') //< public abstract
.needs(['vjo.java.lang.IndexOutOfBoundsException','vjo.java.util.NoSuchElementException',
    'vjo.java.lang.NullPointerException'])
.needs('vjo.java.util.Iterator','')
.needs('vjo.java.util.Collection','')
.inherits('vjo.java.util.AbstractList<E>')
.protos({
    //> protected constructs()
    constructs:function(){
        this.base();
    },
    //> public E get(int index)
    get:function(index){
        var e=this.listIterator(index);
        try {
            return (e.next());
        }
        catch(exc){
            throw (new this.vj$.IndexOutOfBoundsException("Index: "+index));
        }
    },
    //> public E set(int index,E element)
    set:function(index,element){
        var e=this.listIterator(index);
        try {
            var oldVal=e.next();
            e.set(element);
            return oldVal;
        }
        catch(exc){
            throw (new this.vj$.IndexOutOfBoundsException("Index: "+index));
        }
    },
    //> public Iterator<E> iterator()
    iterator:function(){
        return this.listIterator();
    },
    //> public Iterator<E> listIterator()
    //> public Iterator<E> listIterator(int index)
    listIterator:function(){
        if(arguments.length===1){
            if(typeof arguments[0]=="number"){
                return this.listIterator_1_0_AbstractSequentialList_ovld(arguments[0]);
            }else if(this.base && this.base.listIterator){
                return this.base.listIterator.apply(this,arguments);
            }
        }else if(this.base && this.base.listIterator){
            return this.base.listIterator.apply(this,arguments);
        }
    },
    //> private Iterator<E> listIterator_1_0_AbstractSequentialList_ovld(int index)
    listIterator_1_0_AbstractSequentialList_ovld:function(index){
    },
    //> public void add(int index,E element)
    add:function(index,element){
        if(arguments.length===2){
            if(typeof arguments[0]=="number" && arguments[1] instanceof Object){
                this.add_2_0_AbstractSequentialList_ovld(arguments[0],arguments[1]);
            }else if(this.base && this.base.add){
                this.base.add.apply(this,arguments);
            }
        }else if(this.base && this.base.add){
            this.base.add.apply(this,arguments);
        }
    },
    //> private void add_2_0_AbstractSequentialList_ovld(int index,E element)
    add_2_0_AbstractSequentialList_ovld:function(index,element){
        var e=this.listIterator(index);
        e.add(element);
    },
    //> public E remove(int index)
    remove:function(index){
        if(arguments.length===1){
            if(typeof arguments[0]=="number"){
                return this.remove_1_0_AbstractSequentialList_ovld(arguments[0]);
            }else if(this.base && this.base.remove){
                return this.base.remove.apply(this,arguments);
            }
        }else if(this.base && this.base.remove){
            return this.base.remove.apply(this,arguments);
        }
    },
    //> private E remove_1_0_AbstractSequentialList_ovld(int index)
    remove_1_0_AbstractSequentialList_ovld:function(index){
        var e=this.listIterator(index);
        var outCast;
        try {
            outCast=e.next();
        }
        catch(exc){
            throw (new this.vj$.IndexOutOfBoundsException("Index: "+index));
        }
        e.remove();
        return outCast;
    },
    //> public boolean addAll(int index,Collection<? extends E> c)
    addAll:function(index,c){
        if(arguments.length===2){
            if(typeof arguments[0]=="number" && vjo.java.util.Collection.clazz.isInstance(arguments[1])){
                return this.addAll_2_0_AbstractSequentialList_ovld(arguments[0],arguments[1]);
            }else if(this.base && this.base.addAll){
                return this.base.addAll.apply(this,arguments);
            }
        }else if(this.base && this.base.addAll){
            return this.base.addAll.apply(this,arguments);
        }
    },
    //> private boolean addAll_2_0_AbstractSequentialList_ovld(int index,Collection<? extends E> c)
    addAll_2_0_AbstractSequentialList_ovld:function(index,c){
        //eBay Modification
        if(c  === null) {
            throw new this.vj$.NullPointerException();
        }
 
        var modified=false;
        var e1=this.listIterator(index);
        var e2=c.iterator();
        while(e2.hasNext()){
            e1.add(e2.next());
            modified=true;
        }
        return modified;
    }
})
.endType();