vjo.ctype("vjo.java.lang.IntegerUtil")
.needs("vjo.java.lang.NumberFormatException")
.props({
	parseInt : function() {
		try{
			if (arguments.length == 2) {
				return parseInt(arguments[0], arguments[1]);
			} else if (arguments.length == 1) {
				return parseInt(arguments[0]);
			}
		} catch(e) {
			//TODO throw a specific error based on the error coming back from JS
			throw new vjo.java.lang.NumberFormatException("Error using parseInt");
		}
	}
})
.endType();