vjo.ctype('vjo.java.util.EnumSet<E extends Enum>') //< public abstract
.needs(['vjo.java.lang.ClassCastException','vjo.java.lang.IllegalArgumentException',
    'vjo.java.util.RegularEnumSet','vjo.java.util.JumboEnumSet','vjo.java.lang.ClassUtil'])
//> needs vjo.java.lang.Enum,vjo.java.util.Collection,vjo.java.util.Iterator,vjo.java.util.Set
.inherits('vjo.java.util.AbstractSet<E>')
.satisfies('vjo.java.lang.Cloneable')
.props({
    ZERO_LENGTH_ENUM_ARRAY:null, //< private Enum[]
    //> protected int bitCount(int i)
    bitCount:function(i){
        i=i-((i>>>1)&0x55555555);
        i=(i&0x33333333)+((i>>>2)&0x33333333);
        i=(i+(i>>>4))&0x0f0f0f0f;
        i=i+(i>>>8);
        i=i+(i>>>16);
        return i&0x3f;
    },
    //> protected int numberOfTrailingZeros(int x)
    numberOfTrailingZeros:function(x){
	    var y;
	    if (x === 0) return 32;
	    var n = 31;
	    y = x <<16; if (y !== 0) { n = n -16; x = y; }
	    y = x << 8; if (y !== 0) { n = n - 8; x = y; }
	    y = x << 4; if (y !== 0) { n = n - 4; x = y; }
	    y = x << 2; if (y !== 0) { n = n - 2; x = y; }
	    return n - ((x << 1) >>> 31);
    },   
    //> public <E extends Enum<E>> EnumSet<E> noneOf(vjo.Class<E> elementType)
    noneOf:function(elementType){
        var universe=this.vj$.ClassUtil.getEnumConstants(elementType); //<Enum[]
        if(universe===null){
            throw new this.vj$.ClassCastException(elementType+" not an enum");
        }
        if(universe.length<=32){//Change 64bit to 32 bit
            return new this.vj$.RegularEnumSet(elementType,universe);
        }else {
            return new this.vj$.JumboEnumSet(elementType,universe);
        }
    },
    //> public <E extends Enum<E>> EnumSet<E> allOf(vjo.Class<E> elementType)
    allOf:function(elementType){
        var result=this.noneOf(elementType); //<EnumSet
        result.addAll();
        return result;
    },
    //> public <E extends Enum<E>> EnumSet<E> copyOf(Collection<E> c)
    copyOf:function(c){
        if (c.isEmpty())
            throw new this.vj$.IllegalArgumentException("Collection is empty");
        var i = c.iterator();
        var first = i.next();
        var result = this.vj$.EnumSet.of(first);
        while (i.hasNext())
            result.add(i.next());
        return result;
    },
    //> public <E extends Enum<E>> EnumSet<E> complementOf(EnumSet<E> s)
    complementOf:function(s){
        var result=this.copyOf(s); //<EnumSet
        result.complement();
        return result;
    },
    //> public EnumSet<E> of(E e)
    //> public <E extends Enum<E>> EnumSet<E> of(E e1,E e2)
    //> public <E extends Enum<E>> EnumSet<E> of(E e1,E e2,E e3)
    //> public <E extends Enum<E>> EnumSet<E> of(E e1,E e2,E e3,E e4)
    //> public <E extends Enum<E>> EnumSet<E> of(E e1,E e2,E e3,E e4,E e5)
    //> public <E extends Enum<E>> EnumSet<E> of(E first,E... rest)
    of:function(e){
        if(arguments.length===1){
            if(arguments[0] instanceof Object){
                return this.vj$.EnumSet.of_1_0_EnumSet_ovld(arguments[0]);
            }
        }else if(arguments.length===2){
        	if(arguments[0] instanceof Object && arguments[1] instanceof Array){
                return this.vj$.EnumSet.of_2_0_EnumSet_ovld(arguments[0],arguments[1]);
            }else if(arguments[0] instanceof Object && arguments[1] instanceof Object){
                return this.vj$.EnumSet.of_2_1_EnumSet_ovld(arguments[0],arguments[1]);
            } 
        }else if(arguments.length===3){
            if(arguments[0] instanceof Object && arguments[1] instanceof Object && arguments[2] instanceof Object){
                return this.vj$.EnumSet.of_3_0_EnumSet_ovld(arguments[0],arguments[1],arguments[2]);
            }
        }else if(arguments.length===4){
            if(arguments[0] instanceof Object && arguments[1] instanceof Object && arguments[2] instanceof Object && arguments[3] instanceof Object){
                return this.vj$.EnumSet.of_4_0_EnumSet_ovld(arguments[0],arguments[1],arguments[2],arguments[3]);
            }
        }else if(arguments.length===5){
            if(arguments[0] instanceof Object && arguments[1] instanceof Object && arguments[2] instanceof Object && arguments[3] instanceof Object && arguments[4] instanceof Object){
                return this.vj$.EnumSet.of_5_0_EnumSet_ovld(arguments[0],arguments[1],arguments[2],arguments[3],arguments[4]);
            }
        }
    },
    //> private <E extends Enum<E>> EnumSet<E> of_1_0_EnumSet_ovld(E e)
    of_1_0_EnumSet_ovld:function(e){
        var result=this.noneOf(e.getDeclaringClass()); //<EnumSet
        result.add(e);
        return result;
    },
    //> private <E extends Enum<E>> EnumSet<E> of_2_1_EnumSet_ovld(E e1,E e2)
    of_2_1_EnumSet_ovld:function(e1,e2){
        var result=this.noneOf(e1.getDeclaringClass()); //<EnumSet
        result.add(e1);
        result.add(e2);
        return result;
    },
    //> private <E extends Enum<E>> EnumSet<E> of_3_0_EnumSet_ovld(E e1,E e2,E e3)
    of_3_0_EnumSet_ovld:function(e1,e2,e3){
        var result=this.noneOf(e1.getDeclaringClass()); //<EnumSet
        result.add(e1);
        result.add(e2);
        result.add(e3);
        return result;
    },
    //> private <E extends Enum<E>> EnumSet<E> of_4_0_EnumSet_ovld(E e1,E e2,E e3,E e4)
    of_4_0_EnumSet_ovld:function(e1,e2,e3,e4){
        var result=this.noneOf(e1.getDeclaringClass()); //<EnumSet
        result.add(e1);
        result.add(e2);
        result.add(e3);
        result.add(e4);
        return result;
    },
    //> private <E extends Enum<E>> EnumSet<E> of_5_0_EnumSet_ovld(E e1,E e2,E e3,E e4,E e5)
    of_5_0_EnumSet_ovld:function(e1,e2,e3,e4,e5){
        var result=this.noneOf(e1.getDeclaringClass()); //<EnumSet
        result.add(e1);
        result.add(e2);
        result.add(e3);
        result.add(e4);
        result.add(e5);
        return result;
    },
    //> private <E extends Enum<E>> EnumSet<E> of_2_0_EnumSet_ovld(E first,E... rest)
    of_2_0_EnumSet_ovld:function(first){
        var rest;
        if (arguments.length == 2 && arguments[1] instanceof Array){
            rest=arguments[1];
        }
        else {
            rest=[];
            for (var i=1; i<arguments.length; i++){
                rest[i-1]=arguments[i];
            }
        }
        var result= this.noneOf(first.getDeclaringClass()); //<EnumSet
        result.add(first);
        for (var i=0;i<rest.length;i++){
            var e=rest[i];
            result.add(e);
        }
        return result;
    },
    //> public <E extends Enum<E>> EnumSet<E> range(E from,E to)
    range:function(from,to){
        if(from.compareTo(to)>0){
            throw new this.vj$.IllegalArgumentException(from+" > "+to);
        }
        var result= this.noneOf(from.getDeclaringClass()); //<EnumSet
        result.addRange(from,to);
        return result;
    }
})
.protos({
    elementType:null, //< final vjo.Class<E> elementType
    universe:null, //< final Enum[]
    //> constructs(vjo.Class<E> elementType,Enum[] universe)
    constructs:function(elementType,universe){
        this.base();
        this.elementType=elementType;
        this.universe=universe;
    },
    //> abstract void addAll()
    //> public boolean addAll(Collection<? extends E> c)
    addAll:function(){
        if(arguments.length===1){
            return this.base.addAll.apply(this,arguments);
        }
    },
    //> abstract void addRange(E from,E to)
    addRange:function(from,to){
    },
    //> abstract void complement()
    complement:function(){
    },
    //> final void typeCheck(E e)
    typeCheck:function(e){
        var eClass=e.getClass(); //<Class
        if(eClass!==this.elementType&&this.vj$.ClassUtil.getSuperclass(eClass)!==this.elementType){
            throw new this.vj$.ClassCastException(eClass+" != "+this.elementType);
        }
    }
})
.inits(function(){
    this.vj$.EnumSet.ZERO_LENGTH_ENUM_ARRAY=vjo.createArray(null, 0);
})
.endType();