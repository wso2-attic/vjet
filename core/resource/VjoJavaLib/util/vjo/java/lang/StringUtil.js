vjo.ctype("vjo.java.lang.StringUtil")
.needs('vjo.java.lang.StringFactory')
.needs('vjo.java.lang.CharSequence')
.needs('vjo.java.lang.StringBuffer')
.needs('vjo.java.util.Comparator')
.props({
	MIN_RADIX : 2,
	MAX_RADIX : 36,
	length:function(str){
		return str.length;
	},
	compareTo:function(strObj,str){
		var end = strObj.length < str.length ? strObj.length : str.length;
		var o1 = 0;
		var result;
		while (o1 < end) {
			if ((result = strObj.charCodeAt(o1) - str.charCodeAt(o1)) != 0) return result;
			o1++;
		}
		return strObj.length - str.length;
	},
	compareToIgnoreCase:function(strObj,str) {
		return this.compareTo(strObj.toLowerCase(), str.toLowerCase());
	},
	endsWith:function(strObj, str) {
		return this.equals(str, strObj.substring(strObj.length-str.length));
   	},
   	equals:function(strObj,str){
   		return strObj == str;
   	},
   	equalsIgnoreCase:function(strObj,str){
   		if (strObj == null && str == null)
   			return true;
   			
   		if (strObj == null || str == null) {
   			return false;
   		}
   		
   		return this.equals(strObj.toLowerCase(),str.toLowerCase());

   	},
    intern:function(strObj){
   		return strObj;
  	},
    //> public int indexOf (String str)
   	indexOf:function(strObj,str){
   		return strObj.indexOf(str);
  	}, 
  	lastIndexOf:function(strObj,str){
  		return strObj.lastIndexOf(str);
  	},
  	//> public boolean regionMatches(String strObj, int thisStart, String str, int start, int len)
  	regionMatches:function(strObj, thisStart, str, start, len){
  		 if(str === null) {
            throw new this.vj$.NullPointerException();
        }
  		if (start < 0 || str.length - start < len) return false;
  		if (thisStart < 0 || strObj.length - thisStart < len) return false;
  		if (len <= 0) return true;
  		var o1 = thisStart, o2 = start;
  		for (var i = 0; i < len; ++i) {
			if (strObj.charAt(o1 + i) != str.charAt(o2 + i))
				return false;
		}
  		return true;
  	},

  	replace:function(strObj,findStr,newStr){
		var res = strObj;
		while (res.indexOf(findStr) > -1) {
			res = res.replace(findStr,newStr);
		}
		return res;
  	},

 	startsWith : function(strObj, str, start) {
 		var s = start || 0;
		return this.equals(str, strObj.substring(s, str.length + s));
	},

 	toLowerCase:function(strObj) {
  		return strObj.toLowerCase();
  	},
  	toUpperCase:function(strObj) {
  		return strObj.toUpperCase();
  	},
  	matches:function(strObj,str){
  		//http://quickbugstage.arch.ebay.com/show_bug.cgi?id=4797
  		var arr = strObj.match(str), i;
  		if(arr){
  			for(i=0;i<arr.length;i++){
  				if(arr[i]===strObj){
  					return true;
  				}
  			}
  		}
  		return false;
  	},
  	replaceAll:function(strObj,str1,str2) {
  	  	return strObj.replace(new RegExp(str1,"g"),str2);
  	},
  	codePointBefore:function(strObj, index){
  		//http://quickbugstage.arch.ebay.com/show_bug.cgi?id=3730
  		return strObj.charCodeAt(index-1);
  	},
  	codePointCount:function(strObj,start,end){
  		//http://quickbugstage.arch.ebay.com/show_bug.cgi?id=3730
  		var i, code,count=0;
  		for(i=start;i<end;i++){
  			code = this.codePointBefore(strObj,i+1);
  			if(!isNaN(code)){
  				count++;
  			}
  		}
  		return count;
  	},
  	offsetByCodePoints:function(strObj,index,offset){
  		//TODO - Pending implementation.
  		return 0;
  	},
  	charAt:function(strObj,pos) {
  		//TODO - Pending implementation.
  		return null;
  	},
  	concat:function(strObj,str) {
  		strObj += str;
  		return strObj;
  	},
  	//> public static String copyValueOf(char[] str)
  	copyValueOf:function(str){
		return vjo.java.lang.StringFactory.build(str);
  	},
  	getChars:function(strObj, start,end,buffer,index){
  		//TODO - Pending implementation.
  		return null;
  	},
  	substring:function(strObj,start,end){
  		return strObj.substring.apply(strObj, arguments);
  	},
  
  	split:function(strObj, regex, limit){
  		//TODO - Pending implementation. JS String.split is slightly different from Java split
  		return strObj.split(regex, limit);
  	},

  	subSequence:function(strObj,startIndex,endIndex){
  		//TODO - Pending implementation.
  		return null;
  	},
    
  	toCharArray:function(strObj){
  		return strObj+'';
  	},
 	toString:function(strObj){
  		return strObj + '';
  	},
  	trim:function(s){		
	  	return s.replace(/^\s+|\s+$/g, '');
  	},
  	//> public boolean contentEquals(String strObj, StringBuffer str)
  	//> public boolean contentEquals(String strObj, CharSequence str)
  	contentEquals:function(strObj, str){
  		var name = typeof str;
  		if(vjo.java.lang.StringBuffer.clazz.isInstance(str)) {
	  		var size = str.length();
			if (strObj.length != size) return false;
			return this.regionMatches(strObj, 0, this.vj$.StringFactory.build(str.value), 0, size);
  		} else if(vjo.java.lang.CharSequence.clazz.isInstance(str)) {
  			if (strObj.length != str.length()) return false;
			for (var i=0; i<strObj.length; i++) {
				if (strObj.charAt(i) != str.charAt(i)) return false;
			}
			return true;
  		}
  	},
  	contains:function(strObj, str){
  		return (strObj.indexOf(str) > -1);
  	},
  	_valueOf:function(strObj){
  	    if(typeof strObj === 'number') {
            return strObj + "";
        } else if(typeof strObj === 'string') {
            return strObj;
        } else if(strObj === null) {
            return 'null';
        } else if(strObj.valueOf) {
            return strObj.valueOf() + "";
        } else if(vjo.Object.clazz.isInstance(strObj)){
            if(strObj.toString)
            	return strObj.toString();
            else
        	    return strObj.vj$._class + "[" + hashCode(o) + "]";
        } else if(strObj instanceof Object){
        	return "JsNative";
        }
  	    return 'undefined';
  	},
  	CaseInsensitiveComparator:vjo.ctype() //< private
    .satisfies('vjo.java.util.Comparator<String>')
    .protos({
    	//>public int compare(Object, Object)
    	compare:function(o1,o2){
    		return this.vj$.StringUtil.compareToIgnoreCase(o1, o2);
    	},
    	//>public boolean equals(Object)
    	equals:function(obj){
		    return this === obj;
	    }
    })
    .endType(),
    CASE_INSENSITIVE_ORDER:null
})
.inits(function(){
    this.vj$.StringUtil.CASE_INSENSITIVE_ORDER = new this.CaseInsensitiveComparator();
})
.endType();