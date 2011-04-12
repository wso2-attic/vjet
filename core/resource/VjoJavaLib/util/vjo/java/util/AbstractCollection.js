vjo.ctype('vjo.java.util.AbstractCollection<E>') //< public abstract
.needs(['vjo.java.lang.reflect.Array','vjo.java.lang.UnsupportedOperationException',
    'vjo.java.lang.StringBuffer','vjo.java.lang.ObjectUtil',
    'vjo.java.lang.NullPointerException', 'vjo.java.lang.StringUtil'])
.needs('vjo.java.util.Iterator','')
.satisfies('vjo.java.util.Collection<E>')
.protos({
    //> protected constructs()
    constructs:function(){
    },
    //> public abstract Iterator<E> iterator()
    iterator:function(){
    },
    //> public abstract int size()
    size:function(){
    },
    //> public boolean isEmpty()
    isEmpty:function(){
        return this.size()===0;
    },
    //> public boolean contains(Object o)
    contains:function(o){
        var e=this.iterator();
        if(o===null){
            while(e.hasNext()){
                if(e.next()===null){
                    return true;
                }
            }
        }else {
            while(e.hasNext()){
                if(vjo.java.lang.ObjectUtil.equals(o,e.next())){
                    return true;
                }
            }
        }
        return false;
    },
    //> public com.ebay.dsf.jsnative.global.Object[] toArray()
    //> public <T> T[] toArray(T[] a)
    toArray:function(){
        if(arguments.length===0){
            return this.toArray_0_0_AbstractCollection_ovld();
        }else if(arguments.length===1){
            return this.toArray_1_0_AbstractCollection_ovld(arguments[0]);
        }
    },
    //> private com.ebay.dsf.jsnative.global.Object[] toArray_0_0_AbstractCollection_ovld()
    toArray_0_0_AbstractCollection_ovld:function(){
        var result=vjo.createArray(null, this.size());
        var e=this.iterator();
        for (var i=0;e.hasNext();i++){
            result[i]=e.next();
        }
        return result;
    },
    //> private <T> T[] toArray_1_0_AbstractCollection_ovld(T[] a)
    toArray_1_0_AbstractCollection_ovld:function(a){
	    //eBay Modification
	    if(a === null) {
		    throw new this.vj$.NullPointerException();
	    }

        var size=this.size();
        if(a.length<size){
            a=this.vj$.Array.newInstance(null, size);
        }
        var it=this.iterator();
        var result=a;
        for (var i=0;i<size;i++){
            result[i]=it.next();
        }
        if(a.length>size){
            a[size]=null;
        }
        return a;
    },
    //> public boolean add(E o)
    add:function(o){
        throw new this.vj$.UnsupportedOperationException();
    },
    //> public boolean remove(Object o)
    remove:function(o){
        var e=this.iterator();
        if(o===null){
            while(e.hasNext()){
                if(e.next()===null){
                    e.remove();
                    return true;
                }
            }
        }else {
            while(e.hasNext()){
                if(vjo.java.lang.ObjectUtil.equals(o,e.next())){
                    e.remove();
                    return true;
                }
            }
        }
        return false;
    },
    //> public boolean containsAll(Collection<?> c)
    containsAll:function(c){
        //eBay Modification
        if(c  === null) {
	        throw new this.vj$.NullPointerException();
        }

        var e=c.iterator();
        while(e.hasNext()){
            if(!this.contains(e.next())){
                return false;
            }
        }
        return true;
    },
    //> public boolean addAll(Collection<? extends E> c)
    addAll:function(c){
        //eBay Modification
        if(c  === null) {
            throw new this.vj$.NullPointerException();
        }

        var modified=false;
        var e=c.iterator();
        while(e.hasNext()){
            if(this.add(e.next())){
                modified=true;
            }
        }
        return modified;
    },
    //> public boolean removeAll(Collection<?> c)
    removeAll:function(c){
        //eBay Modification
        if(c  === null) {
            throw new this.vj$.NullPointerException();
        }

        var modified=false;
        var e=this.iterator();
        while(e.hasNext()){
            if(c.contains(e.next())){
                e.remove();
                modified=true;
            }
        }
        return modified;
    },
    //> public boolean retainAll(Collection<?> c)
    retainAll:function(c){
        //eBay Modification
        if(c  === null) {
            throw new this.vj$.NullPointerException();
        }

        var modified=false;
        var e=this.iterator();
        while(e.hasNext()){
            if(!c.contains(e.next())){
                e.remove();
                modified=true;
            }
        }
        return modified;
    },
    //> public void clear()
    clear:function(){
        var e=this.iterator();
        while(e.hasNext()){
            e.next();
            e.remove();
        }
    },
    //> public String toString()
    toString:function(){
        var buf=new vjo.java.lang.StringBuffer();
        buf.append("[");
        var i=this.iterator();
        var hasNext=i.hasNext();
        while(hasNext){
            var o=i.next();
            buf.append(o===this?"(this Collection)":vjo.java.lang.StringUtil._valueOf(o));
            hasNext=i.hasNext();
            if(hasNext){
                buf.append(", ");
            }
        }
        buf.append("]");
        return buf.toString();
    }
})
.endType();