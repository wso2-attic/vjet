vjo.ctype('org.dojotoolkit.ext.Back') //< public
.globals({
	back : undefined //<type::Back
}, dojo)
.props({
	getHash : null, //<Object
	goBack : null, //<Object
	goForward : null, //<Object
	setHash : null, //<Object
	
	//>public void addToHistory(Object args) 
	addToHistory : vjo.NEEDS_IMPL,
	
	//>public void init() 
	init : vjo.NEEDS_IMPL,
	
	//>public void setInitialState(Object args) 
	setInitialState : vjo.NEEDS_IMPL	
})
.options({
	metatype:true
})
.endType();