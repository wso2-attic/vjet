vjo.ctype('vjo.java.util.ArrayList<E>') //< public
.needs(['vjo.java.lang.IllegalArgumentException','vjo.java.lang.Math',
    'vjo.java.lang.System','vjo.java.lang.reflect.Array',
    'vjo.java.lang.IndexOutOfBoundsException','vjo.java.lang.Integer',
    'vjo.java.lang.Util','vjo.java.lang.ObjectUtil',
    'vjo.java.lang.ArrayIndexOutOfBoundsException'])
.needs('vjo.java.util.Collection','')
.inherits('vjo.java.util.AbstractList<E>')
.satisfies('vjo.java.util.List<E>')
.satisfies('vjo.java.util.RandomAccess')
.satisfies('vjo.java.lang.Cloneable')
.satisfies('vjo.java.io.Serializable')
.props({
    serialVersionUID:8683452581122892189 //< private final long
})
.protos({
    elementData:null, //< private E[]
    elemsSize:0, //< private int
    //> public constructs()
    //> public constructs(int initialCapacity)
    //> public constructs(Collection<? extends E> c)
    constructs:function(){
        if(arguments.length===1){
            //This snippet will be handled by other mechanism
    	    if(arguments[0] === null) {
    	  	    throw new vjo.java.lang.NullPointerException();
    	    }

            if(typeof arguments[0]=="number"){
                this.constructs_1_0_ArrayList_ovld(arguments[0]);
            }else if(vjo.java.util.Collection.clazz.isInstance(arguments[0])){
                this.constructs_1_1_ArrayList_ovld(arguments[0]);
            }
        }else if(arguments.length===0){
            this.constructs_0_0_ArrayList_ovld();
        }
    },
    //> private constructs_1_0_ArrayList_ovld(int initialCapacity)
    constructs_1_0_ArrayList_ovld:function(initialCapacity){
        this.base();
        if(initialCapacity<0){
            throw new this.vj$.IllegalArgumentException("Illegal Capacity: "+initialCapacity);
        }
        this.elementData=vjo.createArray(null, initialCapacity);
    },
    //> private constructs_0_0_ArrayList_ovld()
    constructs_0_0_ArrayList_ovld:function(){
        this.constructs_1_0_ArrayList_ovld(10);
    },
    //> private constructs_1_1_ArrayList_ovld(Collection<? extends E> c)
    constructs_1_1_ArrayList_ovld:function(c){
        this.base();
        this.elemsSize=c.size();
        this.elementData=vjo.createArray(null, this.vj$.Util.cast(this.vj$.Math.min(parseInt((this.elemsSize*110)/100),this.vj$.Integer.MAX_VALUE),'int'));
        c.toArray(this.elementData);
    },
    //> public void trimToSize()
    trimToSize:function(){
        this.modCount++;
        var oldCapacity=this.elementData.length;
        if(this.elemsSize<oldCapacity){
            var oldData=this.elementData;
            this.elementData=vjo.createArray(null, this.elemsSize);
            this.vj$.System.arraycopy(oldData,0,this.elementData,0,this.elemsSize);
        }
    },
    //> public void ensureCapacity(int minCapacity)
    ensureCapacity:function(minCapacity){
        this.modCount++;
        var oldCapacity=this.elementData.length;
        if(minCapacity>oldCapacity){
            var oldData=this.elementData;
            var newCapacity=parseInt((oldCapacity*3)/2)+1;
            if(newCapacity<minCapacity){
                newCapacity=minCapacity;
            }
            this.elementData=vjo.createArray(null, newCapacity);
            this.vj$.System.arraycopy(oldData,0,this.elementData,0,this.elemsSize);
        }
    },
    //> public int size()
    size:function(){
        return this.elemsSize;
    },
    //> public boolean isEmpty()
    isEmpty:function(){
        return this.elemsSize===0;
    },
    //> public boolean contains(Object elem)
    contains:function(elem){
        return this.indexOf(elem)>=0;
    },
    //> public int indexOf(Object elem)
    indexOf:function(elem){
        if(elem===null){
            for (var i=0;i<this.elemsSize;i++){
                if(this.elementData[i]===null){
                    return i;
                }
            }
        }else {
            for (var i=0;i<this.elemsSize;i++){
                if(vjo.java.lang.ObjectUtil.equals(elem,this.elementData[i])){
                    return i;
                }
            }
        }
        return -1;
    },
    //> public int lastIndexOf(Object elem)
    lastIndexOf:function(elem){
        if(elem===null){
            for (var i=this.elemsSize-1;i>=0;i--){
                if(this.elementData[i]===null){
                    return i;
                }
            }
        }else {
            for (var i=this.elemsSize-1;i>=0;i--){
                if(vjo.java.lang.ObjectUtil.equals(elem,this.elementData[i])){
                    return i;
                }
            }
        }
        return -1;
    },
    //> public com.ebay.dsf.jsnative.global.Object[] toArray()
    //> public <T> T[] toArray(T[] a)
    toArray:function(){
        if(arguments.length===0){
            return this.toArray_0_0_ArrayList_ovld();
        }else if(arguments.length===1){
            if(arguments[0] instanceof Array){
                return this.toArray_1_0_ArrayList_ovld(arguments[0]);
            }
        }
    },
    //> private com.ebay.dsf.jsnative.global.Object[] toArray_0_0_ArrayList_ovld()
    toArray_0_0_ArrayList_ovld:function(){
        var result=vjo.createArray(null, this.elemsSize);
        this.vj$.System.arraycopy(this.elementData,0,result,0,this.elemsSize);
        return result;
    },
    //> private <T> T[] toArray_1_0_ArrayList_ovld(T[] a)
    toArray_1_0_ArrayList_ovld:function(a){
        if(a.length<this.elemsSize){
            a=this.vj$.Array.newInstance(a.getClass().getComponentType(),this.elemsSize);
        }
        this.vj$.System.arraycopy(this.elementData,0,a,0,this.elemsSize);
        if(a.length>this.elemsSize){
            a[this.elemsSize]=null;
        }
        return a;
    },
    //> public E get(int index)
    get:function(index){
        this.RangeCheck(index);
        return this.elementData[index];
    },
    //> public E set(int index,E element)
    set:function(index,element){
        this.RangeCheck(index);
        var oldValue=this.elementData[index];
        this.elementData[index]=element;
        return oldValue;
    },
    //> public boolean add(E o)
    //> public void add(int index,E element)
    add:function(o){
    	if(arguments.length===1){
            return this.add_1_0_ArrayList_ovld(arguments[0]);
        }else if(arguments.length===2){
            if(typeof arguments[0]=="number"){
                this.add_2_0_ArrayList_ovld(arguments[0],arguments[1]);
            }
        }
    },
    //> private boolean add_1_0_ArrayList_ovld(E o)
    add_1_0_ArrayList_ovld:function(o){
        this.ensureCapacity(this.elemsSize+1);
        this.elementData[this.elemsSize++]=o;
        return true;
    },
    //> private void add_2_0_ArrayList_ovld(int index,E element)
    add_2_0_ArrayList_ovld:function(index,element){
        if(index>this.elemsSize || index<0){
            throw new this.vj$.IndexOutOfBoundsException("Index: "+index+", Size: "+this.elemsSize);
        }
        this.ensureCapacity(this.elemsSize+1);
        this.vj$.System.arraycopy(this.elementData,index,this.elementData,index+1,this.elemsSize-index);
        this.elementData[index]=element;
        this.elemsSize++;
    },
    //> public E remove(int index)
    //> public boolean remove(Object o)
    remove:function(index){
        if(arguments.length===1){
            if(typeof arguments[0]=="number"){
                return this.remove_1_0_ArrayList_ovld(arguments[0]);
            } else {
                return this.remove_1_1_ArrayList_ovld(arguments[0]);
            }
        }
    },
    //> private E remove_1_0_ArrayList_ovld(int index)
    remove_1_0_ArrayList_ovld:function(index){
        this.RangeCheck(index);
        this.modCount++;
        var oldValue=this.elementData[index];
        var numMoved=this.elemsSize-index-1;
        if(numMoved>0){
            this.vj$.System.arraycopy(this.elementData,index+1,this.elementData,index,numMoved);
        }
        this.elementData[--this.elemsSize]=null;
        return oldValue;
    },
    //> private boolean remove_1_1_ArrayList_ovld(Object o)
    remove_1_1_ArrayList_ovld:function(o){
        if(o===null){
            for (var index=0;index<this.elemsSize;index++){
                if(this.elementData[index]===null){
                    this.fastRemove(index);
                    return true;
                }
            }
        }else {
            for (var index=0;index<this.elemsSize;index++){
                if(vjo.java.lang.ObjectUtil.equals(o,this.elementData[index])){
                    this.fastRemove(index);
                    return true;
                }
            }
        }
        return false;
    },
    //> private void fastRemove(int index)
    fastRemove:function(index){
        this.modCount++;
        var numMoved=this.elemsSize-index-1;
        if(numMoved>0){
            this.vj$.System.arraycopy(this.elementData,index+1,this.elementData,index,numMoved);
        }
        this.elementData[--this.elemsSize]=null;
    },
    //> public void clear()
    clear:function(){
        this.modCount++;
        for (var i=0;i<this.elemsSize;i++){
            this.elementData[i]=null;
        }
        this.elemsSize=0;
    },
    //> public boolean addAll(Collection<? extends E> c)
    //> public boolean addAll(int index,Collection<? extends E> c)
    addAll:function(c){
        if(arguments.length===1){
            //This snippet will be handled by other mechanism
	        if(arguments[0] === null) {
	  	         throw new vjo.java.lang.NullPointerException();
	        }

            if(vjo.java.util.Collection.clazz.isInstance(arguments[0])){
                return this.addAll_1_0_ArrayList_ovld(arguments[0]);
            }
        }else if(arguments.length===2){
            //This snippet will be handled by other mechanism
            if(arguments[1] === null) {
  	             throw new vjo.java.lang.NullPointerException();
            }

            if(typeof arguments[0]=="number" && vjo.java.util.Collection.clazz.isInstance(arguments[1])){
                return this.addAll_2_0_ArrayList_ovld(arguments[0],arguments[1]);
            }
        }
    },
    //> private boolean addAll_1_0_ArrayList_ovld(Collection<? extends E> c)
    addAll_1_0_ArrayList_ovld:function(c){
        var a=c.toArray();
        var numNew=a.length;
        this.ensureCapacity(this.elemsSize+numNew);
        this.vj$.System.arraycopy(a,0,this.elementData,this.elemsSize,numNew);
        this.elemsSize+=numNew;
        return numNew!==0;
    },
    //> private boolean addAll_2_0_ArrayList_ovld(int index,Collection<? extends E> c)
    addAll_2_0_ArrayList_ovld:function(index,c){
        if(index>this.elemsSize || index<0){
            throw new this.vj$.IndexOutOfBoundsException("Index: "+index+", Size: "+this.elemsSize);
        }
        var a=c.toArray();
        var numNew=a.length;
        this.ensureCapacity(this.elemsSize+numNew);
        var numMoved=this.elemsSize-index;
        if(numMoved>0){
            this.vj$.System.arraycopy(this.elementData,index,this.elementData,index+numNew,numMoved);
        }
        this.vj$.System.arraycopy(a,0,this.elementData,index,numNew);
        this.elemsSize+=numNew;
        return numNew!==0;
    },
    //> protected void removeRange(int fromIndex,int toIndex)
    removeRange:function(fromIndex,toIndex){
        this.modCount++;
        var numMoved=this.elemsSize-toIndex;
        this.vj$.System.arraycopy(this.elementData,toIndex,this.elementData,fromIndex,numMoved);
        var newSize=this.elemsSize-(toIndex-fromIndex);
        while(this.elemsSize!==newSize){
            this.elementData[--this.elemsSize]=null;
        }
    },
    //> private void RangeCheck(int index)
    RangeCheck:function(index){
        if(index < 0)
	         throw new this.vj$.ArrayIndexOutOfBoundsException("Negative index: "+index);

        if(index>=this.elemsSize){
            throw new this.vj$.IndexOutOfBoundsException("Index: "+index+", Size: "+this.elemsSize);
        }
    }
})
.endType();