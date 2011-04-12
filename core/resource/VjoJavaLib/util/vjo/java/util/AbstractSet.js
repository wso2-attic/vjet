vjo.ctype('vjo.java.util.AbstractSet<E>') //< public abstract
.needs(['vjo.java.lang.NullPointerException','vjo.java.lang.ClassCastException'])
.needs('vjo.java.lang.ObjectUtil','')
.needs('vjo.java.util.Iterator','')
.needs('vjo.java.util.Collection','')
.inherits('vjo.java.util.AbstractCollection<E>')
.satisfies('vjo.java.util.Set<E>')
.protos({
    //> protected constructs()
    constructs:function(){
        this.base();
    },
    //> public boolean equals(Object o)
    equals:function(o){
        if(o===this){
            return true;
        }
        if(!(vjo.java.util.Set.clazz.isInstance(o))){
            return false;
        }
        var c=o;
        if(c.size()!==this.size()){
            return false;
        }
        try {
            return this.containsAll(c);
        }
        catch(unused){
            if(vjo.java.lang.ClassCastException.clazz.isInstance(unused)){
                return false;
            }else if(vjo.java.lang.NullPointerException.clazz.isInstance(unused)){
                return false;
            }
        }
    },
    //> public int hashCode()
    hashCode:function(){
        var h=0;
        var i=this.iterator();
        while(i.hasNext()){
            var obj=i.next();
            if(obj!==null){
                h+=vjo.java.lang.ObjectUtil.hashCode(obj);
            }
        }
        return h;
    },
    //> public boolean removeAll(Collection<?> c)
    removeAll:function(c){
        //eBay Modification
        if(c  === null) {
            throw new this.vj$.NullPointerException();
        }

        var modified=false;
        if(this.size()>c.size()){
            for (var i=c.iterator();i.hasNext();){
                modified = this.remove(i.next()) || modified;
            }
        }else {
            for (var i=this.iterator();i.hasNext();){
                if(c.contains(i.next())){
                    i.remove();
                    modified=true;
                }
            }
        }
        return modified;
    }
})
.endType();