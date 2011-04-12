vjo.ctype('vjo.java.util.SubList<E>') //< public
.needs(['vjo.java.lang.IndexOutOfBoundsException','vjo.java.lang.IllegalArgumentException',
    'vjo.java.util.NoSuchElementException'])
.needs('vjo.java.util.Collection','')
.needs('vjo.java.util.Iterator','')
.needs('vjo.java.util.List','')
.needs('vjo.java.util.ConcurrentModificationException','')
.inherits('vjo.java.util.AbstractList<E>')
.protos({
    l:null, //< private AbstractList<E> l
    offset:0, //< private int
    size__:0, //< private int
    expectedModCount:0, //< private int
    //> constructs(AbstractList<E> list,int fromIndex,int toIndex)
    constructs:function(list,fromIndex,toIndex){
        this.base();
        if(fromIndex<0){
            throw new this.vj$.IndexOutOfBoundsException("fromIndex = "+fromIndex);
        }
        if(toIndex>list.size()){
            throw new this.vj$.IndexOutOfBoundsException("toIndex = "+toIndex);
        }
        if(fromIndex>toIndex){
            throw new this.vj$.IllegalArgumentException("fromIndex("+fromIndex+") > toIndex("+toIndex+")");
        }
        this.l=list;
        this.offset=fromIndex;
        this.size__=toIndex-fromIndex;
        this.expectedModCount=this.l.modCount;
    },
    //> public E set(int index,E element)
    set:function(index,element){
        this.rangeCheck(index);
        this.checkForComodification();
        return this.l.set(index+this.offset,element);
    },
    //> public E get(int index)
    get:function(index){
        this.rangeCheck(index);
        this.checkForComodification();
        return this.l.get(index+this.offset);
    },
    //> public int size()
    size:function(){
        this.checkForComodification();
        return this.size__;
    },
    //> public void add(int index,E element)
    add:function(index,element){
        if(index<0 || index>this.size__){
            throw new this.vj$.IndexOutOfBoundsException();
        }
        this.checkForComodification();
        this.l.add(index+this.offset,element);
        this.expectedModCount=this.l.modCount;
        this.size__++;
        this.modCount++;
    },
    //> public E remove(int index)
    remove:function(index){
        this.rangeCheck(index);
        this.checkForComodification();
        var result=this.l.remove(index+this.offset);
        this.expectedModCount=this.l.modCount;
        this.size__--;
        this.modCount++;
        return result;
    },
    //> protected void removeRange(int fromIndex,int toIndex)
    removeRange:function(fromIndex,toIndex){
        this.checkForComodification();
        this.l.removeRange(fromIndex+this.offset,toIndex+this.offset);
        this.expectedModCount=this.l.modCount;
        this.size__-=(toIndex-fromIndex);
        this.modCount++;
    },
    //> public boolean addAll(Collection<? extends E> c)
    //> public boolean addAll(int index,Collection<? extends E> c)
    addAll:function(c){
        if(arguments.length===1){
            if(vjo.java.util.Collection.clazz.isInstance(arguments[0])){
                return this.addAll_1_0_SubList_ovld(arguments[0]);
            }else if(this.base && this.base.addAll){
                return this.base.addAll.apply(this,arguments);
            }
        }else if(arguments.length===2){
            if(typeof arguments[0]=="number" && vjo.java.util.Collection.clazz.isInstance(arguments[1])){
                return this.addAll_2_0_SubList_ovld(arguments[0],arguments[1]);
            }else if(this.base && this.base.addAll){
                return this.base.addAll.apply(this,arguments);
            }
        }else if(this.base && this.base.addAll){
            return this.base.addAll.apply(this,arguments);
        }
    },
    //> private boolean addAll_1_0_SubList_ovld(Collection<? extends E> c)
    addAll_1_0_SubList_ovld:function(c){
        return this.addAll(this.size__,c);
    },
    //> private boolean addAll_2_0_SubList_ovld(int index,Collection<? extends E> c)
    addAll_2_0_SubList_ovld:function(index,c){
        if(index<0 || index>this.size__){
            throw new this.vj$.IndexOutOfBoundsException("Index: "+index+", Size: "+this.size__);
        }
        var cSize=c.size();
        if(cSize===0){
            return false;
        }
        this.checkForComodification();
        this.l.addAll(this.offset+index,c);
        this.expectedModCount=this.l.modCount;
        this.size__+=cSize;
        this.modCount++;
        return true;
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
                return this.listIterator_1_0_SubList_ovld(arguments[0]);
            }else if(this.base && this.base.listIterator){
                return this.base.listIterator.apply(this,arguments);
            }
        }else if(this.base && this.base.listIterator){
            return this.base.listIterator.apply(this,arguments);
        }
    },
    //> private Iterator<E> listIterator_1_0_SubList_ovld(final int index)
    listIterator_1_0_SubList_ovld:function(index){
        this.checkForComodification();
        if(index<0 || index>this.size__){
            throw new this.vj$.IndexOutOfBoundsException("Index: "+index+", Size: "+this.size__);
        }
        return vjo.make(this,vjo.java.util.Iterator)
            .protos({
                i:null,
                constructs:function(){
                    this.i=this.vj$.parent.l.listIterator(index+this.vj$.parent.offset);
                },
                hasNext:function(){
                    return this.nextIndex()<this.vj$.parent.size__;
                },
                next:function(){
                    if(this.hasNext()){
                        return this.i.next();
                    }else {
                        throw new this.vj$.NoSuchElementException();
                    }
                },
                hasPrevious:function(){
                    return this.previousIndex()>=0;
                },
                previous:function(){
                    if(this.hasPrevious()){
                        return this.i.previous();
                    }else {
                        throw new this.vj$.NoSuchElementException();
                    }
                },
                nextIndex:function(){
                    return this.i.nextIndex()-this.vj$.parent.offset;
                },
                previousIndex:function(){
                    return this.i.previousIndex()-this.vj$.parent.offset;
                },
                remove:function(){
                    this.i.remove();
                    this.vj$.parent.expectedModCount=this.vj$.parent.l.modCount;
                    this.vj$.parent.size__--;
                    this.modCount++;
                },
                set:function(o){
                    this.i.set(o);
                },
                add:function(o){
                    this.i.add(o);
                    this.vj$.parent.expectedModCount=this.vj$.parent.l.modCount;
                    this.vj$.parent.size__++;
                    this.modCount++;
                }
            })
            .endType();
    },
    //> public List<E> subList(int fromIndex,int toIndex)
    subList:function(fromIndex,toIndex){
        return new this.vj$.SubList(this,fromIndex,toIndex);
    },
    //> private void rangeCheck(int index)
    rangeCheck:function(index){
        if(index<0 || index>=this.size_){
            throw new this.vj$.IndexOutOfBoundsException("Index: "+index+",Size: "+this.size_);
        }
    },
    //> private void checkForComodification()
    checkForComodification:function(){
        if(this.l.modCount!==this.expectedModCount){
            throw new vjo.java.util.ConcurrentModificationException();
        }
    }
})
.endType();