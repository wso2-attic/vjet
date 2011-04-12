vjo.ctype('vjo.java.lang.ObjectUtil')
.needs(['vjo.java.lang.BooleanUtil'])
.props({
    //> public boolean equals (Object o1,Object o2)
    equals:function(o1,o2){
		var t = this;
		if (o1 == null) {
			return (o2 == null);
		}else if(o2 == null){
			return (o1 == null);
		}
		//special logic to handle native boolean and vjo boolean
		//since vjo.java.lang.Boolean handcoded
		if(t.isBoolType(o1) && t.isVjo(o2) && o2.getClass().getSimpleName()==='Boolean'){ //native boolean and vjo boolean
			return (o1.valueOf() === o2.booleanValue());
		}if(t.isBoolType(o2) && t.isVjo(o1) && o1.getClass().getSimpleName()==='Boolean'){ //native boolean and vjo boolean
			return (o2.valueOf() === o1.booleanValue());
		}else if(t.isNative(o1)==t.isVjo(o2)){ //for native and vjo
			return false;
		}else if(t.isVjo(o1)) { //vjo and anything else
			return o1.equals(o2);
		//js native objects
		} else if (t.isNative(o1) && t.isNative(o2)) { //native string and anything else
			//string and number, boolean and number
			if((t.isStrType(o1) && t.isNumberType(o2)) 
				|| (t.isStrType(o2) && t.isNumberType(o1))
				|| (t.isBoolType(o1) && t.isNumberType(o2)) 
				|| (t.isBoolType(o2) && t.isNumberType(o1))	
				|| (t.isBoolType(o1) && t.isStrType(o2)) 
				|| (t.isBoolType(o2) && t.isStrType(o1))	
				){
				return (o1 === o2);
			//date and date
			}else if(t.isDateType(o1) && t.isDateType(o2)){
				return (o1.getTime() === o2.getTime());
			//string and string
			}else if(t.isStrType(o1) && t.isStrType(o2)){
				return (o1.toString() === o2.toString());
			//boolean and boolean
			}else if(t.isBoolType(o1) && t.isBoolType(o2)){
				return (o1.valueOf() === o2.valueOf());
			}else{
				//default
				return (o1 == o2);
			}
		} else {
			//default
			return (o1 === o2);
		}
    },
    isNative:function(o){
    	var t = this;
    	return ((  t.isDateType(o)
    			|| t.isStrType(o)
    			|| t.isNumberType(o)
    			|| t.isBoolType(o)
    			|| t.isObjType(o))
    			&& !this.isVjo(o)
    			);
	},
    isObjType: function(o){
		return (o instanceof Object 
			 || typeof o === 'object' 
		 );
	},
    isDateType: function(o){
		return (o instanceof Date);
	},
    isNumberType: function(o){
		return (o instanceof Number 
			 || typeof o === 'number' 
			 );
    },
    isStrType: function(o){
		return (o instanceof String 
			 || typeof o === 'string' 
		 	);
	},
    isBoolType: function(o){
		return (o instanceof Boolean 
			 || typeof o === 'boolean' 
	 		);
	},
    isVjo: function(o){
		return (typeof o.vj$ != 'undefined')
    },
    //>public int hashCode(Object o)
    hashCode: function (o) {
        if (o == null) {
		     return 0;
	    }
	    if (typeof o.hashCode != 'undefined') {
		    return o.hashCode();
	    }
	    if (typeof o == 'string' || o instanceof String) {
		    return o.length;
	    }
	    if (typeof o == 'boolean') {
            return (o?1231:1237);
        }
        if(o instanceof Boolean){
            return (vjo.java.lang.BooleanUtil.booleanValue(o) === true ? 1231 : 1237);
        }
        if (typeof o == 'number') {
	        return parseInt(o);
        }
        if (o instanceof Date) {
    		var ht = o.getTime();
    		//TODO - Due to JS bounderies, this is currently returning 0!
    		return (ht ^ (ht >> 32));
        }
        return -1;
    },
    //>public int compareTo(Object o1, Object o2)
   compareTo: function(o1, o2) {
        if(o1 && o1.compareTo) {
            return o1.compareTo(o2);
        } else if(o1 && o1.vauleOf && o2 && o2.vauleOf) {
    	    var v1 = o1.vauleOf(), v2 = o2.valueOf();
        	return (v1 > v2) - (v2 > v1);
        } else {
            return (o1 > o2) - (o2 > o1);
        }
    }
})
.endType();