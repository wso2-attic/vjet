vjo.ctype('vjo.java.lang.StringBuffer')
.needs('vjo.java.lang.RuntimeException')
//< needs(vjo.java.lang.CharSequence)
//< needs(vjo.java.lang.StringIndexOutOfBoundsException)

.props({
	INITIAL_SIZE : 16
})
.protos({
	value : '', //< private String
	
	//> public void contructs()
	//> public void contructs(int)
	//> public void constructs(String)
	//> public void constructs(CharSequence sequence)
	constructs : function (val) {
		if (arguments.length == 1) {
			this.value = val.toString();
		}
	},
	
	//> public StringBuffer append (char[] chars)
	//> public StringBuffer append (char ch)
	//> public StringBuffer append (double value) 
	//> public StringBuffer append (float value)
	//> public StringBuffer append (int value)
	//> public StringBuffer append (long value)
	//> public StringBuffer append (Object value)
	//> public StringBuffer append (String string)
	//> public StringBuffer append (boolean value)
	//> public StringBuffer append(StringBuffer sbuffer)
	//> public StringBuffer append(CharSequence sequence)
	//> public StringBuffer append (char[] chars, int start, int length)
	//> public StringBuffer append(CharSequence sequence, int start, int end)
	append : function (val, start, length) {
		var l = arguments.length;
		if (l === 1) {
			if (typeof(val) === 'string') {
				this.value += val; 
			} else {
				//Must be array or CharSequence
				if (vjo.isArray(val)) {
					for (var i=0; i<val.length; i++) {
						this.value += val[i].toString();
					}
				} else {
					//TODO
				    if(val === false) {
					    this.value += 'false';
				    } else if(val && val.toString){
					    this.value += val.toString();
				    } else {
				    	if(typeof val === 'undefined') {
					        this.value += 'undefined';
				    	} else if(val === null) {
					        this.value += 'null';
				    	} else {
				            this.value += 'object';
				    	}
				    }
				}
			}
		} else if (l === 3) {
			if (start >= 0 && 0 <= length && length <= chars.length - start) {
				if (vjo.isArray(val)) {
					this.value += val.join('');
				} else {
				    if(val === false) {
					    this.value += 'false';
				    } else if(val && val.toString){
			            this.value += val.toString();
		            } else {
		    	        if(typeof val === 'undefined') {
			                this.value += 'undefined';
		    	        } else if(val === null) {
			                this.value += 'null';
		    	        } else {
		                    this.value += 'object';
		    	        }
		            }
				}
				this.value += s; 
			} 
			else 
				throw new this.vj$.RuntimeException("StringIndexOutOfBoundsException");
				//throw new StringIndexOutOfBoundsException();
			
		}
		return this;
	},
	
	//> public int capacity()
	capacity : function () {
		return this.value.length;
	},
	
	//>public char charAt(int index)
	charAt : function (index) {
		if (index < this.value.length && index >= 0) {
			return this.value.substr(index, 1);
		} else {
			throw new this.vj$.RuntimeException("StringIndexOutOfBoundsException");
			//throw new StringIndexOutOfBoundsException();
		}
	},
	
	/**
	* IE doesn't allow 'delete' name, its a reserved word. So adding _ at the end
	*/
	//>public StringBuffer delete_(int start, int end)
	delete_ : function (start, end) {
		if (start >= 0) {
			var count = this.length();
			if (end > count) end = count;
			if (end > start) {
				try {
					var s = this.value.substring(start, end);
					this.value = this.value.substring(0, start) + this.value.substring(end);
				} catch (e) {
					throw new this.vj$.RuntimeException("StringIndexOutOfBoundsException. " + e.toString());
				}
				return this;
			}
			if (start == end) return this;
		}
		throw new this.vj$.RuntimeException("StringIndexOutOfBoundsException");
	},
	
	//> public StringBuffer deleteCharAt(int location)
	deleteCharAt: function (location) {
		var count = this.length();
		if (0 <= location && location < count) {
			this.value = this.value.substring(0, location) + this.value.substring(location+1);  
		} else {
			throw new this.vj$.RuntimeException("StringIndexOutOfBoundsException");
		}
		
		return this;
	},
	
	//> public void ensureCapacity(int min)
	ensureCapacity : function (min) {
		
	},
	
	//> public void getChars(int start, int end, char [] buffer, int index)
	getChars : function (start, end, buffer, index) {
		
		return this.value.split("");
	},
	
	//> public StringBuffer insert(int index, char[] chars)
	//> public StringBuffer insert(int index, char ch)
	//> public StringBuffer insert(int index, double value)
	//> public StringBuffer insert(int index, float value)
	//> public StringBuffer insert(int index, int value)
	//> public StringBuffer insert(int index, long value)
	//> public StringBuffer insert(int index, Object value)
	//> public StringBuffer insert(int index, String string)
	//> public StringBuffer insert(int index, boolean value)
	//> public StringBuffer insert(int index, CharSequence sequence)
	//> public StringBuffer insert(int index, char[] chars, int start, int length)
	//> public StringBuffer insert(int index, CharSequence sequence, int start, int end)
	insert: function (index, chars, start, length) {
		var al = arguments.length;
		var s = '';
		if (al >= 2) {
			if (vjo.isArray(chars)) {
				s = chars.join("");
			} else {
				s = chars.toString();
			}
		}
		if (al === 2) {
			this.value = this.value.substring(0, index) + s + this.value.substring(index);
			return this;
		} else if (al === 4) {
			if (start >= 0 && 0 <= length && length <= chars.length - start) {
				s = s.substr(start, length);
				this.value = this.value.substring(0, index) + s + this.value.substring(index);
			}
			return this;
		}
		throw new this.vj$.RuntimeException("StringIndexOutOfBoundsException");
	},
	
	//> public int length ()
	length : function () {
		return (this.value.length);
	},
	
	//> public StringBuffer replace(int start, int end, String str)
	replace : function (start, end, str) {
		if (start <= end) {
			this.value = this.value.substring(0, start) + str + this.value.substring(end);
			return this;
		}
		throw new this.vj$.RuntimeException("StringIndexOutOfBoundsException");
	},
	
	//> public StringBuffer reverse()
	reverse : function () {
		if (this.length() < 2) {
			return this;
		}
		
		var rt = this.value.split("").reverse().join("");
		this.value = rt;
		return this;

	},
	
	//> public void setCharAt(int index, char ch)
	setCharAt : function (index, ch) {
		return this.replace(index, index+1, ch.toString());
	},
	
	//> public void setLength(int length)
	setLength : function (length) {
		
	},
	
	//> public String substring(int start)
	//> public String substring(int start, int end)
	substring : function (start, end) {
		if (arguments.length === 2) {
			return this.value.substring(start, end);
		} else {
			return this.value.substring(start);
		}
	},
	
	//> public String toString ()
	toString : function () {
		return this.value;
	},
	
	//> public CharSequence subSequence(int start, int end)
	subSequence : function (start, end) {
		return this.substring(start, end);
	},
	
	//> public int indexOf(String str, int start)
	//> public int indexOf(String str)
	indexOf : function (str, start) {
		return this.value.indexOf(str,(start||0));
	},
	
	//> public int lastIndexOf(String str)
	//> public int lastIndexOf(String str, int start)
	lastIndexOf : function (str, start) {
		return this.value.lastIndexOf(str, (start|0));
	},
	
	//> char[] getValue()
	getValue : function () {
		return this.value;
	},
	
	//> public void trimToSize()
	trimToSize : function () {
		
	},
	
	//> public int codePointAt(int index)
	codePointAt : function (index) {
		var count = this.length();
		if (index >= 0 && index < count) {
			var high = this.value.charCodeAt(index);
			if ((index + 1) < count && high >= 0xd800 && high <= 0xdbff) {
				var low = this.value.charCodeAt(index+1);;
				if (low >= 0xdc00 && low <= 0xdfff)
					return 0x10000 + ((high - 0xd800) << 10) + (low - 0xdc00);
			}
			return high;
		} else 
			throw new this.vj$.RuntimeException("StringIndexOutOfBoundsException");
	},
	
	//> public int codePointBefore(int index)
	codePointBefore : function (index) {
		var count = this.length();
		if (index > 0 && index <= count) {
			var low = this.value.charCodeAt(index);
			if (index > 1 && low >= 0xdc00 && low <= 0xdfff) {
				var high = this.value.charCodeAt(index - 2);
				if (high >= 0xd800 && high <= 0xdbff)
					return 0x10000 + ((high - 0xd800) << 10) + (low - 0xdc00);
			}
			return low;
		} else 
			throw new this.vj$.RuntimeException("StringIndexOutOfBoundsException");
	},
	
	//> public int codePointCount(int start, int end)
	codePointCount : function (start, end) {
		if (start >= 0 && start <= end && end <= count) {
			var count = 0;
			for (var i=start; i<end; i++) {
				var high = this.value.charCodeAt(i);
				if (i + 1 < end && high >= 0xd800 && high <= 0xdbff) {
					var low = this.value.charCodeAt(i+1);
					if (low >= 0xdc00 && low <= 0xdfff) i++;
				}
				count++;
			}
			return count;
		} else 
			throw new this.vj$.RuntimeException("IndexOutOfBoundsException");
	},
	
	//> public int offsetByCodePoints(int start, int codePointCount)
	offsetByCodePoints : function (start, codePointCount) {
		var count = this.length();
		if (start >= 0 && start <= count) {
			var index = start;
			if (codePointCount == 0) 
				return start;
			else if (codePointCount > 0) {
				for (var i=0; i<codePointCount; i++) {
					if (index == count) break;
					var high = this.value.charCodeAt(index);
					if ((index + 1) < count && high >= 0xd800 && high <= 0xdbff) {
						var low = this.value.charCodeAt(index + 1);
						if (low >= 0xdc00 && low <= 0xdfff) index++;
					}
					index++;
				}
			} else {
				for (var i=codePointCount; i<0; i++) {
					if (index < 1) break;
					var low = this.value.charCodeAt(index - 1);
					if (index > 1 && low >= 0xdc00 && low <= 0xdfff) {
						var high = this.value.charCodeAt(index - 2);
						if (high >= 0xd800 && high <= 0xdbff) index--;
					}
					index--;
				}
			}
			return index;
		} 
		throw new this.vj$.RuntimeException("IndexOutOfBoundsException");
	},
	
	//> public StringBuffer appendCodePoint(int codePoint)
	appendCodePoint : function (codePoint) {
		if (codePoint < 0 || codePoint >= 0x110000) {
			throw new this.vj$.RuntimeException("IllegalArgumentException");
		}
		if (codePoint < 0x10000) {
			this.value += String.fromCharCode(codePoint);
		} else {
			codePoint -= 0x10000;
			this.value += String.fromCharCode(0xd800 + (codePoint >> 10)) + String.fromCharCode(0xdc00 + (codePoint & 0x3ff));
		}
		return this;
	}

})
.endType();