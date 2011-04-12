//> final public 
vjo.ctype("vjo.java.lang.Boolean")
.props({
	
})
.protos({
    //> final private boolean value
    value:false,
    //> public constructs(boolean s)
    //> public constructs(String s)
    constructs:function(s){
    	if (typeof s === 'string') {
    		this.value = (s==="true") ? true : false;
    	} else {
        	this.value = s;
        }
    },
    //> public boolean booleanValue()
    booleanValue : function() {
    	return this.value;
    },
    //> public String toString()
    toString : function() {
    	return this.value + '';
    },
    //> public int compareTo(Boolean b)
    compareTo : function(b) {
    	return (b.value == this.value ? 0 : (this.value ? 1 : -1));
    },
    //> public boolean equals(Object b)
    equals : function(b) {
    	if (b instanceof this.vj$.Boolean) {
    		return (b.value === this.value);
    	}
    	return false;
    }
})
.endType();