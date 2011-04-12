vjo.ctype('vjo.reflect.Modifier') //< public
.props({
    PUBLIC:0x00000001, //< public final int
    PRIVATE:0x00000002, //< public final int
    PROTECTED:0x00000004, //< public final int
    STATIC:0x00000008, //< public final int
    FINAL:0x00000010, //< public final int

	INTERFACE:0x00000200, //< public final int
    ABSTRACT:0x00000400, //< public final int

	VARARGS:0x00000080, //< final int

	//> public boolean isPublic(int mod)
    isPublic:function(mod){
        return (mod&this.PUBLIC)!==0;
    },
    
    //> public boolean isPrivate(int mod)
    isPrivate:function(mod){
        return (mod&this.PRIVATE)!==0;
    },
    
    //> public boolean isProtected(int mod)
    isProtected:function(mod){
        return (mod&this.PROTECTED)!==0;
    },
    
    //> public boolean isStatic(int mod)
    isStatic:function(mod){
        return (mod&this.STATIC)!==0;
    },

    //> public boolean isFinal(int mod)
    isFinal:function(mod){
        return (mod&this.FINAL)!==0;
    },
    
    //> public boolean isInterface(int mod)
    isInterface:function(mod){
        return (mod&this.INTERFACE)!==0;
    },

    //> public boolean isAbstract(int mod)
    isAbstract:function(mod){
        return (mod&this.ABSTRACT)!==0;
    },
    
    //> public String toString(int mod)
    toString:function(mod){
        var len; //<int
        var sb = '';
        if((mod&this.PUBLIC)!==0){
            sb += 'public ';
        }
        if((mod&this.PROTECTED)!==0){
        	sb += 'protected ';
        }
        if((mod&this.PRIVATE)!==0){
        	sb += 'private ';
        }
        if((mod&this.ABSTRACT)!==0){
        	sb += 'abstract ';
        }
        if((mod&this.STATIC)!==0){
        	sb += 'static ';
        }
        if((mod&this.FINAL)!==0){
        	sb += 'final ';
        }
        if((mod&this.INTERFACE)!==0){
        	sb += 'interface ';
        }
        if((len=sb.length)>0){
            return sb.toString().substring(0,len-1);
        }
        return "";
    }
})
.endType();