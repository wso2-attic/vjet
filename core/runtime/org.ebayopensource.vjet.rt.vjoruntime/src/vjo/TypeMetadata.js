vjo.ctype('vjo.TypeMetadata') //< public
.needs('vjo.reflect.Type')
.needs('vjo.reflect.Field')
.needs('vjo.reflect.Method')
.needs('vjo.reflect.Constructor')
.protos({
	m_type: null,   //< Type
	m_fields: [], //< Field[]
	m_methods: [], //< Method[]
	m_constructors: [], //< Constructor[]
	
	_loaded : false,

	//> public
	constructs : function (pData) {
		this.load(pData);
	},
	
	//> public Type
    getType: function() {
        return this.m_type;
    },

    //> public int getModifiers()
    getModifiers : function () {
    	return this.m_type.getModifiers();
    },
    
	//> public Field[]
    getFields: function() { 
        return this.m_fields;
    },

	//> public Field[]
	getDeclaredFields: function() { 
		//TODO
	    return this.m_fields;
	},

	//> public Constructor[]
	getConstructors: function() {
	    return this.m_constructors;
	},

	//> public Method[]
	getMethods: function() {
	    return this.m_methods;
	},

	//> public Method[]
    getDeclaredMethods: function() {
    	//TODO
	    return this.m_methods;
	},
	
	//> public Object[]
	getAnnotations : function() {
		//TODO
		return [];
	},

	//> public Object[]
	getInterfaces : function() {
		//TODO
		return [];
	},

	load : function (pData) {
		if (!this._loaded) {
			//Load the type
			var dt = pData, i;
			//this.m_type = vjo.TypeMetadata.getClasses([dt.type])[0];
			/*
			 * Format:
			  	['typeName', modifiers]
			 */
			this.m_type = new this.vj$.Type(dt.type[0], dt.type[1]);
			var thisClass = vjo.Class.create(dt.type[0]);
			if (dt.fields && dt.fields.length) {
				/*
				 * Format:
					[
						['field1Name', 'field1Type', modifiers],
						['field2Name', 'field2Type', modifiers],
						['field3Name', 'field3Type', modifiers],
						...
					]
				 */
				for (var i=0,len=dt.fields.length; i<len; i++) {
					var f = dt.fields[i];
					this.m_fields[this.m_fields.length] = 
						new this.vj$.Field(thisClass, f[0], vjo.TypeMetadata.getClasses([f[1]])[0], f[2]);
				}
			}
			if (dt.methods && dt.methods.length) {
				/*
				 * Format:
					[
						['method1Name', ['param1Type', 'param2Type', ...], 'returnType', modifiers],
						['method2Name', ['param1Type', 'param2Type', ...], 'returnType', modifiers],
						...
					]
				 */
				for (var i=0,len=dt.methods.length; i<len; i++) {
					var m = dt.methods[i];
					this.m_methods[this.m_methods.length] = 
						new this.vj$.Method(thisClass, m[0], vjo.TypeMetadata.getClasses(m[1]), vjo.TypeMetadata.getClasses([m[2]])[0], m[3]);
				}
			}
			if (dt.constructors && dt.constructors.length) {
				/*
				 * Format:
					[
						[['type1', 'type2', ...], modifiers],
						[['type1', 'type2', ...], modifiers],
						...
					]
				 */
				for (var i=0,len=dt.constructors.length; i<len; i++) {
					var m = dt.constructors[i];
					this.m_constructors[this.m_constructors.length] = 
						new this.vj$.Constructor(thisClass, vjo.TypeMetadata.getClasses(m[0]), m[1]);
				}
			}
			this._loaded = true;
		}
	}
})
.props({

	getClasses : function (pMeta) {
		if (!pMeta || !(pMeta instanceof Array) ) {
			throw "Invalid data";
		}
		var len = pMeta.length;
		var a = vjo.createArray(null, len);
		for (var i=0; i<len; i++) {
			var t = pMeta[i];
			for (var itm in this._nativeTypes) {
				if (itm == t) {
					if (!this._nativeTypes[itm]) {
						this._nativeTypes[itm] = vjo.Class.create(itm);
					}
					a[i] = this._nativeTypes[itm];
					break;
				}
			}
			
			//Not a native type
			if (!a[i]) {
				var tp = vjo.getType(t);
				if (tp) {
					a[i] = tp.clazz; //< @SUPPRESSTYPECHECK		
				} else {
					//Type is not loaded yet, so create clazz for this type and cache it.
					a[i] = vjo.Class.create(t);
				}
			}
		}
		return a;
	},

	//TODO - Need to move these over to separate type
	_nativeTypes : {
		'void' : null,
		'String' : null,
		'int' : null,
		'boolean' : null,
		'Boolean' : null,
		'Date' : null,
		'Number' : null,
		'Array' : null,
		'RegExp' : null
	}
})
.endType() ;
