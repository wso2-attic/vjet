vjo.ctype('vjo.java.lang.Enum<E extends Enum<E>>') //< public abstract
.needs(['vjo.java.lang.System','vjo.java.lang.ClassCastException',
    'vjo.java.lang.Class'])
.satisfies('vjo.java.lang.Comparable<E>')
.satisfies('vjo.java.io.Serializable')
.protos({
    name_:null, //< private final String
    ordinal_:0, //< private final int
    //> protected constructs(String name,int ordinal)
    constructs:function(name,ordinal){
        this.name_=name;
        this.ordinal_=ordinal;
    },
    //> final public String name()
    name:function(){
        return this.name_;
    },
    //> final public int ordinal()
    ordinal:function(){
        return this.ordinal_;
    },
    //> public String toString()
    toString:function(){
        return this.name_;
    },
    //> final public boolean equals(Object other)
    equals:function(other){
        return this===other;
    },
    //> final public int hashCode()
    hashCode:function(){
        return this.vj$.System.identityHashCode(this);
    },
    //> final public int compareTo(E o)
    compareTo:function(o){
        var other=o;
        var self=this;
        if(self.getClass()!==other.getClass()&&self.getDeclaringClass()!==other.getDeclaringClass()){
            throw new this.vj$.ClassCastException();
        }
        return this.ordinal_-o.ordinal_;
    },
    //> final public Class<E> getDeclaringClass()
    getDeclaringClass:function(){
        var clazz=this.getClass();
        var zuper=clazz.getSuperclass();
        return (zuper===this.vj$.Enum.clazz)?clazz:zuper;
    }
})
.endType();