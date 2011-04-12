vjo.itype('vjo.java.util.SortedSet<E>') //< public
.needs('vjo.java.util.Comparator','')
.inherits('vjo.java.util.Set<E>')
.protos({
    //> public Comparator<? super E> comparator()
    comparator:function(){
    },
    //> public SortedSet<E> subSet(E fromElement,E toElement)
    subSet:function(fromElement,toElement){
    },
    //> public SortedSet<E> headSet(E toElement)
    headSet:function(toElement){
    },
    //> public SortedSet<E> tailSet(E fromElement)
    tailSet:function(fromElement){
    },
    //> public E first()
    first:function(){
    },
    //> public E last()
    last:function(){
    }
})
.endType();