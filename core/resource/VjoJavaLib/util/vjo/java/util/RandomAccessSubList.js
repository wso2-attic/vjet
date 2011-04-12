vjo.ctype('vjo.java.util.RandomAccessSubList<E>') //< public
.needs('vjo.java.util.AbstractList')
.needs('vjo.java.util.List','')
.inherits('vjo.java.util.SubList<E>')
.satisfies('vjo.java.util.RandomAccess')
.protos({
    //> constructs(AbstractList<E> list,int fromIndex,int toIndex)
    constructs:function(list,fromIndex,toIndex){
        this.base(list,fromIndex,toIndex);
    },
    //> public List<E> subList(int fromIndex,int toIndex)
    subList:function(fromIndex,toIndex){
        return new this.vj$.RandomAccessSubList(this,fromIndex,toIndex);
    }
})
.endType();