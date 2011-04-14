vjo.ctype('org.dojotoolkit.Dojo') //< public
//<needs(org.dojotoolkit.DojoAuxiliary)
.globals({
	dojo : undefined //<<type::Dojo
})
.props({
	baseUrl : null, //<public String
	boxModel : null, //<public String
	global : null, //<public Object
	isAir : null, //<public Number
	isBrowser : false, //<public boolean
	isChrome : null, //<public Number
	isFF : null, //<public Number
	isIE : null, //<public Number
	isKhtml : null, //<public Number
	isMoz : null, //<public Object
	isMozilla : null, //<public Number
	isOpera : null, //<public Number
	isQuirks : null, //<public boolean
	isRhino : null, //<public Object
	isSafari : null, //<public Number
	isSpidermonkey : null, //<public Object
	isWebKit : null, //<public Number
	jaxer : null, //<public Object
	locale : null, //<public Object
	NodeListFx : null, //<public Object
	NodeListHtml : null, //<public Object
	nonDebugProvide : null, //<public Object
	parser : null, //<public Object
	requireAfterIf : null, //<public Object
	robot : null, //<public Object
	robotx : null, //<public Object
	tests : null, //<public Object
	toJsonIndentStr : null, //<public String
	
	//CONSTRUCTS
	
	//>public Object require(String moduleName, boolean? omitModuleCheck) 
	require : vjo.NEEDS_IMPL,
	
	//FUNCTIONS
	
	//>public void addClass({Node|String} node, {String|Array} class)
	addClass : vjo.NEEDS_IMPL,
	
	//>public void addOnLoad(Object obj, {Function|String} func)
	//>public void addOnLoad(Function func)
	addOnLoad : vjo.NEEDS_IMPL,
	
	//>public void addOnUnload(Object obj, {Function|String} func)
	//>public void addOnUnload(Function func)
	addOnUnload : vjo.NEEDS_IMPL,
	
	//>public void addOnWindowUnload(Object obj, {Function|String} func)
	//>public void addOnWindowUnload(Function func)
	addOnWindowUnload : vjo.NEEDS_IMPL,
	
	//>public void anim({String|Node} node, Object properties, int? duration, (boolean fn(int))? easing, (void fn(Node))? onEnd, int? delay)
	anim : vjo.NEEDS_IMPL,
	
	//>public _Animation animateProperty(DojoAuxiliary::animArgs args)
	animateProperty : vjo.NEEDS_IMPL,	

	//>public String attr({Element|String} node, String name)
	//>public void attr({Element|String} node, String name, String value)
	attr : vjo.NEEDS_IMPL,
	
	//>public Color blendColors(Dojo.Color start, Dojo.Color end, Number weight, Color? obj)
	blendColors : vjo.NEEDS_IMPL,
	
	//>public HTMLElement body()
	body : vjo.NEEDS_IMPL,
	
	//>public HTMLElement byId(String id, Document? doc)
	byId : vjo.NEEDS_IMPL,
	
	//>public Object clone(Object o)
	clone : vjo.NEEDS_IMPL,
	
	//>public Color colorFromArray(Array a, Dojo.Color? obj)
	colorFromArray : vjo.NEEDS_IMPL,
	
	//>public Color colorFromHex(String color, Dojo.Color? obj)
	colorFromHex : vjo.NEEDS_IMPL,

	//>public Color colorFromRgb(String color, Dojo.Color? obj)
	colorFromRgb : vjo.NEEDS_IMPL,
	
	//>public Color colorFromString(String color, Dojo.Color? obj)
	colorFromString : vjo.NEEDS_IMPL,
	
	//>public Handle connect(Object obj, String event, Object context, {Function|String} method, boolean? dontFix)
	//>public Handle connect(Object obj, String event, {Function|String} method, boolean? dontFix)
	//>public Handle connect(String event, Object context, {Function|String} method, boolean? dontFix)
	//>public Handle connect(String event, {Function|String} method, boolean? dontFix)
	connect : vjo.NEEDS_IMPL,
	
	//>public Handle connectPublisher(String topic, Object obj, String event)
	//>public Handle connectPublisher(String topic, String event)
	connectPublisher : vjo.NEEDS_IMPL,
	
	//>public Object contentBox({Node|String} node)
	//>public void contentBox({Node|String} node, Object box)
	contentBox : vjo.NEEDS_IMPL,
	
	//>public DojoAuxiliary::coords coords({Node|String} node, boolean? includeScroll)
	coords : vjo.NEEDS_IMPL,
	
	//>public HTMLElement create(String tag, Object? attrs, HTMLElement? refNode, String? pos)
	//>public HTMLElement create(Node node, Object attrs, HTMLElement? refNode, String? pos)
	create : vjo.NEEDS_IMPL,
	
	//delegate is different from instance mixin
	//>public Object delegate(Object obj, Object props)
	delegate : vjo.NEEDS_IMPL,	
	
	//>public void deprecated(String behaviour, String? extra, String? removal)
	deprecated : vjo.NEEDS_IMPL,
	
	//>public void destroy({Node|String} node)
	destroy : vjo.NEEDS_IMPL,
	
	//>public void disconnect(Handle handle)
	disconnect : vjo.NEEDS_IMPL,
	
	//>public void empty({Element|String} node)
	empty : vjo.NEEDS_IMPL,
	
	//>public Object eval(String scriptFragment)
	eval : vjo.NEEDS_IMPL,
	
	//>public void every({Array|String} arr, {Function|String} callback, Object thisObject)
	every : vjo.NEEDS_IMPL,
	
	//>public boolean exists(String name, Object? obj)
	exists : vjo.NEEDS_IMPL,
	
	//>public void exit(String exitcode)
	exit : vjo.NEEDS_IMPL,
	
	//>public void experiment(String moduleName, String? extra)
	experiment : vjo.NEEDS_IMPL,

	//>public Object extend(Object constructor, Object... props)
	extend : vjo.NEEDS_IMPL,	
	
	//>public _Animation fadeIn(DojoAuxiliary::fadeArgs args)
	fadeIn : vjo.NEEDS_IMPL,
	
	//>public _Animation fadeOut(DojoAuxiliary::fadeArgs args)
	fadeOut : vjo.NEEDS_IMPL,
	
	//>public Array filter(Array arr, {(boolean fn(Object item))|String} callback, Object? thisObject)
	filter : vjo.NEEDS_IMPL,
	
	//>public void fixEvent(Event evt, HTMLElement sender)
	fixEvent : vjo.NEEDS_IMPL,
	
	//>public void forEach({Array|String} arr, {Function|String} callback, Object? thisObject)
	forEach : vjo.NEEDS_IMPL,
	
	//>public String formToJson({Node|String} formNode, boolean? prettyPrint)
	formToJson : vjo.NEEDS_IMPL,
	
	//>public Object formToObject({Node|String} formNode)
	formToObject : vjo.NEEDS_IMPL,
	
	//>public String formToQuery({Node|String} formNode)
	formToQuery : vjo.NEEDS_IMPL,
	
	//>public Object fromJson(String json)
	fromJson : vjo.NEEDS_IMPL,
	
	//>public DojoAuxiliary::CSS2Properties getComputedStyle(Node node)
	getComputedStyle : vjo.NEEDS_IMPL,
	
	//>public Object getObject(String name, boolean? create, Object? context)
	getObject : vjo.NEEDS_IMPL,
	
	//>public boolean hasAttr({Node|String} node, String name)
	hasAttr : vjo.NEEDS_IMPL,
	
	//>public boolean hasClass({Node|String} node, String classStr)
	hasClass : vjo.NEEDS_IMPL,
	
	//>public Function hitch(Object scope, {Function|String} method)
	hitch : vjo.NEEDS_IMPL,
	
	//>public int indexOf(Array array, Object value, int? fromIndex, boolean? findLast)
	indexOf : vjo.NEEDS_IMPL,

	//>public boolean isAlien(Object it)
	isAlien : vjo.NEEDS_IMPL,

	//>public boolean isArray(Object it)
	isArray : vjo.NEEDS_IMPL,

	//>public boolean isArrayLike(Object it)
	isArrayLike : vjo.NEEDS_IMPL,

	//>public boolean isDescendant({Node|String} node, {Node|String} ancestor)
	isDescendant : vjo.NEEDS_IMPL,

	//>public boolean isFunction(Object it)
	isFunction : vjo.NEEDS_IMPL,

	//>public boolean isObject(Object it)
	isObject : vjo.NEEDS_IMPL,

	//>public boolean isString(Object it)
	isString : vjo.NEEDS_IMPL,
	
	//>public int lastIndexOf(Array array, Object value, int? fromIndex)
	lastIndexOf : vjo.NEEDS_IMPL,

	//>public void loadInit(Function init)
	loadInit : vjo.NEEDS_IMPL,

	//>public Array map({Array|String} arr, {Function|String} callback, Object? thisObject)
	map : vjo.NEEDS_IMPL,

	//>public DojoAuxiliary::margin marginBox({Node|String} node)
	//>public void marginBox({Node|String} node, DojoAuxiliary::margin box)
	marginBox : vjo.NEEDS_IMPL,

	//>public Object mixin(Object obj, Object... props)
	mixin : vjo.NEEDS_IMPL,

	//>public String moduleUrl(String module, {Dojo._Url|String} url)
	moduleUrl : vjo.NEEDS_IMPL,

	//>public String objectToQuery(Object map)
	objectToQuery : vjo.NEEDS_IMPL,

	//>public Function partial({Function|String} method)
	partial : vjo.NEEDS_IMPL,

	//>public boolean place({Node|String} node, {Node|String} refNode, {int|String} position)
	place : vjo.NEEDS_IMPL,

	//>public void platformRequire(Object modMap)
	platformRequire : vjo.NEEDS_IMPL,

	//>public void popContext()
	popContext : vjo.NEEDS_IMPL,

	//>public Object provide(String resourceName)
	provide : vjo.NEEDS_IMPL,

	//>public void publish(String topic, Array args)
	publish : vjo.NEEDS_IMPL,

	//>public Array pushContext(Object? g, Document? d)
	//>public Array pushContext(String frameId)
	pushContext : vjo.NEEDS_IMPL,

	//>public NodeList query(String selector, {Node|String}? root, Function? listCtor)
	query : vjo.NEEDS_IMPL,

	//>public Object queryToObject(String str)
	queryToObject : vjo.NEEDS_IMPL,

	//>public void rawXhrPost()
	rawXhrPost : vjo.NEEDS_IMPL,

	//>public void rawXhrPut()
	rawXhrPut : vjo.NEEDS_IMPL,

	//>public void registerModulePath(String module, String prefix)
	registerModulePath : vjo.NEEDS_IMPL,

	//>public void removeAttr({Node|String} node, String name)
	removeAttr : vjo.NEEDS_IMPL,


	//>public void removeClass({Node|String} node, String classStr)
	removeClass : vjo.NEEDS_IMPL,

	//>public void requireIf(boolean condition, String resource)
	requireIf : vjo.NEEDS_IMPL,

	//>public void requireLocalization(String moduleName, String bundleName, String? locale, String? availableFlatLocales)
	requireLocalization : vjo.NEEDS_IMPL,

	//>public void setContext(Object globalObject, Document globalDocument)
	setContext : vjo.NEEDS_IMPL,

	//>public Object setObject(String name, Object value, Object? context)
	setObject : vjo.NEEDS_IMPL,

	//>public void setSelectable({Node|String} node, boolean selectable)
	setSelectable : vjo.NEEDS_IMPL,

	//>public boolean some({Array|String} arr, {Function|String} callback, Object? thisObject)
	some : vjo.NEEDS_IMPL,

	//>public void stopEvent(Event evt)
	stopEvent : vjo.NEEDS_IMPL,

	//>public ObjLiteral style({String|HTMLElement} node)
	//>public Object style({String|HTMLElement} node, String style)
	//>public void style({String|HTMLElement} node, String style, String value)
	//>public void style({String|HTMLElement} node, ObjLiteral styles)
	style : vjo.NEEDS_IMPL,

	//>public Dojo.Handle subscribe(String topic, Object context, {Function|String} method)
	subscribe : vjo.NEEDS_IMPL,

	//>public void toggleClass({HTMLElement|String} node, String classStr, boolean? condition)
	toggleClass : vjo.NEEDS_IMPL,

	//>public String toJson(Object it, boolean? prettyPrint, String? _indentStr)
	toJson : vjo.NEEDS_IMPL,

	//>public String trim(String str)
	trim : vjo.NEEDS_IMPL,

	//>public void unloaded()
	unloaded : vjo.NEEDS_IMPL,

	//>public void unsubscribe(Dojo.Handle handle)
	unsubscribe : vjo.NEEDS_IMPL,
	
	//>public void when(Object promiseOrValue, (void fn(Object))? callback, (void fn(Object))? errback, Function? progressHandler)
	when : vjo.NEEDS_IMPL,

	//>public void withDoc(Document documentObject, Function callback, Object? thisObject, Array? cbArguments)
	withDoc : vjo.NEEDS_IMPL,

	//>public void withGlobal(Object globalObject, Function callback, Object? thisObject, Array? cbArguments)
	withGlobal : vjo.NEEDS_IMPL,

	//TODO
	//public void xdRequiredLocalization

	//>public Dojo.Deferred xhr(String method, DojoAuxiliary::xhrArgs args, boolean? hasBody)
	xhr : vjo.NEEDS_IMPL,

	//>public Dojo.Deferred xhrDelete(DojoAuxiliary::xhrArgs args)
	xhrDelete : vjo.NEEDS_IMPL,

	//>public Dojo.Deferred xhrGet(DojoAuxiliary::xhrArgs args)
	xhrGet : vjo.NEEDS_IMPL,

	//>public Dojo.Deferred xhrPost(DojoAuxiliary::xhrArgs args)
	xhrPost : vjo.NEEDS_IMPL,

	//>public Dojo.Deferred xhrPut(DojoAuxiliary::xhrArgs args)
	xhrPut : vjo.NEEDS_IMPL,
	
	
	//INNER TYPES
	AdapterRegistry : vjo.ctype().protos({ //<public
		//>public constructs(boolean? returnWrappers)
		constructs : vjo.NEEDS_IMPL,

		//>public void match(Object... args)
		match : vjo.NEEDS_IMPL,

		//>public void register(String name, Function check, Function wrap, boolean? directReturn, boolean? override)
		register : vjo.NEEDS_IMPL,
	
		//>public void unregister(String name)
		unregister : vjo.NEEDS_IMPL,
		
		pairs : null, //<public Array
		returnWrappers : false //<public boolean
		
	}).endType(),
	
	behavior : vjo.ctype().protos({ //<public

		//>public void add(ObjLiteral behaviorObj)
		add : vjo.NEEDS_IMPL,

		//>public void apply()
		apply : vjo.NEEDS_IMPL
		
	}).endType(),
	
	config: vjo.ctype().props({ //<public
		addOnLoad : null, //<public Object
		consoleLogFuncs : null, //<public Array
		debugContainerId : null, //<public String
		debugHeight : null, //<public Number
		dojoBlankHtmlUrl : null, //<public Object
		extraLocale : null, //<public String[]
		forceGfxRenderer : null, //<public String
		hashPollFrequency : null, //<public Number
		ieForceActiveXXhr : null, //<public Object
		ioPublish : null, //<public Object
		modulePaths : null, //<public Object
		timezoneFileBasePath : null, //<public Object
		timezoneLoadingScheme : null, //<public String
		useXDomain : null //<public Object
	}).endType(),
		
	Color : vjo.ctype().protos({ //<public
		
		//>public constructs(Array rgbOrRgbaValues)
		//>public constructs(String colorNameOrHex)
		//>public constructs(Object rgbaProperty)
		constructs : vjo.NEEDS_IMPL,

		//>public Color sanitize()
		sanitize : vjo.NEEDS_IMPL,
		
		//>public Color setColor(Array rgbOrRgbaValues)
		//>public Color setColor(String colorNameOrHex)
		//>public Color setColor(Object rgbaProperty)
		setColor : vjo.NEEDS_IMPL,

		//>public String toCss(boolean? includeAlpha)
		toCss : vjo.NEEDS_IMPL,
		
		//>public String toHex()
		toHex : vjo.NEEDS_IMPL,
		
		//>public String toRgb()
		toRgb : vjo.NEEDS_IMPL,
		
		//>public String toRgba()
		toRgba : vjo.NEEDS_IMPL,
	
		//>public String toString()
		toString : vjo.NEEDS_IMPL,
		
		a : 0, //<public int
		b : 0, //<public int
		g : 0, //<public int
		r : 0 //<public int
	}).endType(),

	Deferred : vjo.ctype().protos({ //<public
		
		//>public Deferred addBoth(Function cb)
		//>public Deferred addBoth(Object obj, String cbfn)
		addBoth : vjo.NEEDS_IMPL,
		
		//>public Deferred addCallback(Function cb)
		//>public Deferred addCallback(Object obj, String cbfn)
		addCallback : vjo.NEEDS_IMPL,
						
		//>public Deferred addCallbacks(Function cb, Function eb)
		addCallbacks : vjo.NEEDS_IMPL,
				
		//>public Deferred addErrback(Function eb)
		//>public Deferred addErrback(Object obj, String ebfn)
		addErrback : vjo.NEEDS_IMPL,
		
		//>public void callback(Object res)
		callback : vjo.NEEDS_IMPL,
		
		//>public void errback(Error res)
		errback : vjo.NEEDS_IMPL,
		
		canceller : null, //<public Object
		chain : null, //<public Array
		fired : null, //<public Number
		id : null, //<public Object
		paused : null, //<public Number
		results : null, //<public Array
		silentlyCancelled : null //<public boolean
		
	}).endType(),
	
	_Url : vjo.ctype().protos({ //<public
		//>public String toString()
		toString : vjo.NEEDS_IMPL,
		
		authority : null, //<public Object		
		fragment : null, //<public Object		
		host : null, //<public Object		
		password : null, //<public Object		
		path : null, //<public Object		
		port : null, //<public Object		
		query : null, //<public Object
		scheme : null, //<public Object
		uri : null, //<public Object
		user : null //<public Object
	}).endType(),
	
	_Animation : vjo.ctype().protos({ //<public
		//>public void easing()
		easing : vjo.NEEDS_IMPL,
		
		//>public _Animation gotoPercent(float percent, boolean? andPlay)
		gotoPercent : vjo.NEEDS_IMPL,
		
		//>public _Animation pause()
		pause : vjo.NEEDS_IMPL,
		
		//>public _Animation play(int? delay, boolean? gotoStart)
		play : vjo.NEEDS_IMPL,

		//>public String status()
		status : vjo.NEEDS_IMPL,
		
		//>public _Animation stop(boolean? gotoEnd)
		stop : vjo.NEEDS_IMPL
	
	}).endType(),
	
	Handle : vjo.ctype().endType() //<public
})
.options({
	metatype:true
})
.endType();
