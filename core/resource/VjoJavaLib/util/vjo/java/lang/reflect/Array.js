vjo.ctype("vjo.java.lang.reflect.Array")
.props({
	//>Object newInstance(Class  componentType, int size)
	newInstance : function() {
	    return new Array(arguments[1])
	}
	
})
.endType();