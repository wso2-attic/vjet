//
//// @Package com.ebay.vjo
//
//(function () {
//alap.cov.jsCoverage("bootstrap_3", "n/a","0");
//	if(typeof(vjo)=="object" && vjo._v == 1.4){
//alap.cov.jsCoverage("bootstrap_3", "n/a","1");
//		return;
//	}
//	
//	var win = this; 
//	
//	vjo = {
//		loader : null,
//		_v : 1.4,
//		global : this,
//		_bScope : null,
//		_typeMap : {},
//		_bSubClass : false,
//		_callStack : [],
//		/**
//		 * @Depricated. You should use this.vjo.<SimpleName> to make a static reference
//		 */
//		$static : function (scp) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","2");
//			if (scp==null || scp == win) return scp;
//alap.cov.jsCoverage("bootstrap_3", "n/a","3");
//			if (scp.vjo != 'undefined' && typeof scp.vjo.$s != 'undefined') {
//alap.cov.jsCoverage("bootstrap_3", "n/a","4");
//				return scp.vjo.$s;
//			} else {
//alap.cov.jsCoverage("bootstrap_3", "n/a","5");
//				return scp.constructor;
//			}
//		},
//		/**
//		 * @Depricated. You should use this.vjo.<SimpleName> to make a reference to "needed" import
//		 */
//		$ns : function (scp) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","6");
//			var $s = vjo.$static(scp);
//alap.cov.jsCoverage("bootstrap_3", "n/a","7");
//			return ($s && $s.vjo)? $s.vjo.b : scp;
//		},
//		/**
//		 * answers the qustion is given object of type array
//		 */
//		isArray : function (obj) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","8");
//			if (!obj) return false;
//alap.cov.jsCoverage("bootstrap_3", "n/a","9");
//			return (obj.constructor == Array || (typeof obj == 'object' && obj.join && obj.splice));
//		},
//		/**
//		 * Returns vjo type of given class name
//		 */
//		getType : function(clz) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","10");
//			if (!clz) return;
//			var obj = vjo._typeMap[clz], idx = clz.lastIndexOf("."), 
//				cn = (idx>-1) ? clz.substring(idx+1) : clz;
//alap.cov.jsCoverage("bootstrap_3", "n/a","12");
//			if (obj) {
//				return obj.pkg[cn]
//			}
//alap.cov.jsCoverage("bootstrap_3", "n/a","13");
//			return;
//		},
//		mixin : function (mtype,target) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","14");
//			var mxn = this.getType(mtype);
//alap.cov.jsCoverage("bootstrap_3", "n/a","15");
//			if (mxn) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","16");
//				if (mxn.vjo && mxn.vjo._type === 'mtype') {
//alap.cov.jsCoverage("bootstrap_3", "n/a","17");
//					if (mxn._props) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","18");
//						throw 'cannot mixin static props to an instance';
//					}
//alap.cov.jsCoverage("bootstrap_3", "n/a","19");
//					this.extend(target,mxn._protos);
//				}
//			}
//		},
//		proxy : function (fn,context) { //proxy a call, to be called at a later time
//			return function () {
//alap.cov.jsCoverage("bootstrap_3", "n/a","20");
//				return fn.apply(context,arguments);
//			}
//		},
//		make : function (context,clz) {//used to create an anonymous class
//			var a = arguments, len = a.length, tp = (typeof clz == 'function' && clz.vjo) ? clz : this.getType(clz), _vjo = {};
//alap.cov.jsCoverage("bootstrap_3", "n/a","21");
//			if (len<2 || !tp) throw "context and valid type are required";
//			_forEach(context.vjo,function(val,key){
//				if (typeof val == 'function' && val.vjo && val.vjo._type) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","22");
//					this[key] = val;
//				}
//alap.cov.jsCoverage("bootstrap_3", "n/a","23");
//			},_vjo);
//alap.cov.jsCoverage("bootstrap_3", "n/a","24");
//			var args = Array.prototype.slice.call(a,2,len);
//			return {
//				protos : function (obj) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","25");
//					var t = vjo.ctype(), clztype = tp.vjo._type;
//alap.cov.jsCoverage("bootstrap_3", "n/a","26");
//					if (clztype==='itype') t.satisfies(tp);
//					else if (clztype==='ctype' || clztype==='atype') t.inherits(tp);
//					else throw 'incompatible anonomyous type';
//alap.cov.jsCoverage("bootstrap_3", "n/a","27");
//					t.protos(obj);
//alap.cov.jsCoverage("bootstrap_3", "n/a","28");
//					t.__donotconstruct = true;
//alap.cov.jsCoverage("bootstrap_3", "n/a","29");
//					var rv = new t;
//alap.cov.jsCoverage("bootstrap_3", "n/a","30");
//					rv.vjo = t.vjo;
//alap.cov.jsCoverage("bootstrap_3", "n/a","31");
//					vjo.extend(rv.vjo,_vjo);
//					(function () {//call super constructor no matter what;
//alap.cov.jsCoverage("bootstrap_3", "n/a","32");
//						if (this.base) this.base.apply(this,arguments);
//alap.cov.jsCoverage("bootstrap_3", "n/a","33");
//					}).apply(rv,args);
//					if (rv.base && rv._getBase) {//update base, so we're not pointing at shared prototype base
//						var fn = function(){};
//alap.cov.jsCoverage("bootstrap_3", "n/a","34");
//						fn.prototype = rv._getBase();
//						rv.base = new fn; 
//alap.cov.jsCoverage("bootstrap_3", "n/a","35");
//						rv.base._parent = rv;
//					}
//alap.cov.jsCoverage("bootstrap_3", "n/a","36");
//					return rv;
//				}
//			};
//			
//		},
//		needs : function (clz,alias) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","37");
//		    if (!clz) return;
//alap.cov.jsCoverage("bootstrap_3", "n/a","38");
//		    if (!this._bScope) {
//			    this._bScope = {};
//		    }
//		    var pObj = _createPkg(clz), cls = pObj.className,
//		    tp = pObj.pkg[cls];
//alap.cov.jsCoverage("bootstrap_3", "n/a","40");
//		    if (!tp) {
//			    if (this.loader != null)  {
//				    var tmpScope = this._bScope; //save the current stack
//alap.cov.jsCoverage("bootstrap_3", "n/a","41");
//				    this._bScope = null;
//alap.cov.jsCoverage("bootstrap_3", "n/a","42");
//				    this.loader.load(clz);
//				    this._bScope = tmpScope; //restore the stack
//alap.cov.jsCoverage("bootstrap_3", "n/a","43");
//				    tp = pObj.pkg[cls];
//			    }
//		    }
//alap.cov.jsCoverage("bootstrap_3", "n/a","44");
//		    if (tp) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","45");
//			    if (typeof alias == "string" && alias!="") {
//alap.cov.jsCoverage("bootstrap_3", "n/a","46");
//				    cls = alias;
//			    }
//alap.cov.jsCoverage("bootstrap_3", "n/a","47");
//			    this._bScope[cls] = tp;
//		    }
//		},
//		forEach: _forEach,
//		extend : function () {
//alap.cov.jsCoverage("bootstrap_3", "n/a","48");
//			var a = arguments, len = a.length, target, source;
//			if (len==1) {//extend vjo
//alap.cov.jsCoverage("bootstrap_3", "n/a","49");
//				target = this;
//alap.cov.jsCoverage("bootstrap_3", "n/a","50");
//				source = a[0];
//			} else {
//alap.cov.jsCoverage("bootstrap_3", "n/a","51");
//				target = a[0];
//alap.cov.jsCoverage("bootstrap_3", "n/a","52");
//				source = a[1];
//			}
//	
//alap.cov.jsCoverage("bootstrap_3", "n/a","53");
//			for (var name in source) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","54");
//				var copy = source[name];
//				if (copy !== undefined){
//alap.cov.jsCoverage("bootstrap_3", "n/a","55");
//					target[name] = copy;
//}
//			}
//		},
//		needsLib : function () {
//		}
//	};
//	
//	
//	//===========
//	// OBJECT
//	//===========
//	vjo.Object = function () {
//		this.vjo = {_class:'vjo.Object',_type:'ctype',Object:vjo.Object};
//	};
//	vjo.Object.prototype = {
//		_hashCode : -1,
//		constructs : function () {
//		},
//		getClass : _getClazz,
//		/**
//		 * Answers an integer hash code for the receiver. Any two
//		 * objects which answer <code>true</code> when passed to
//		 * <code>.equals</code> must answer the same value for this
//		 * method.
//		 *
//		 * @author		OTI
//		 * @version		initial
//		 *
//		 * @return		int
//		 *					the receiver's hash.
//		 *
//		 * @see			#equals
//		 */
//		hashCode : function () {
//alap.cov.jsCoverage("bootstrap_3", "n/a","56");
//			if (this._hashCode == -1) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","57");
//				this._hashCode = ++vjo.Object._hashCounter;
//			} 
//alap.cov.jsCoverage("bootstrap_3", "n/a","58");
//			return this._hashCode;
//		},
//			
//		/**
//		 * Compares the argument to the receiver, and answers true
//		 * if they represent the <em>same</em> object using a class
//		 * specific comparison. The implementation in Object answers
//		 * true only if the argument is the exact same object as the
//		 * receiver (==).
//		 *
//		 * @param		o Object
//		 *					the object to compare with this object.
//		 * @return		boolean
//		 *					<code>true</code>
//		 *						if the object is the same as this object
//		 *					<code>false</code>
//		 *						if it is different from this object.
//		 * @see			#hashCode
//		 */
//		equals : function (o) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","59");
//			return (this === o);
//		},
//		
//		/**
//		 * Answers a string containing a concise, human-readable
//		 * description of the receiver.
//		 *
//		 * @return		String
//		 *					a printable representation for the receiver.
//		 */
//		toString : function () {
//alap.cov.jsCoverage("bootstrap_3", "n/a","60");
//			return this.getClass().getName() + "@" + this.hashCode().toString(16);
//		}
//	};
//	vjo.extend(vjo.Object,{
//		vjo : {_class:'vjo.Object',_type:'ctype',Object:vjo.Object},
//
//		_hashCounter : 0,
//		instanceOf : function (o) {
//			//TODO: mac ie 5? need to support? currently tier 3 browser
//alap.cov.jsCoverage("bootstrap_3", "n/a","61");
//			return (o instanceof this);
//		}
//	});
//
//
//	//===========
//	// CLASS
//	//===========
//	/**
//	* Represents a Class object definition
//	*/
//	vjo.Class = function (clz, typ){
//		this._name = clz,
//		this._type = typ || "ctype",
//		this.vjo = {_class:'vjo.Class',_type:'ctype',Class:vjo.Class};
//	};	
//alap.cov.jsCoverage("bootstrap_3", "n/a","62");
//	vjo.Class.prototype = new vjo.Object();
//	vjo.extend(vjo.Class.prototype, {
//		/**
//		 * Answers the name of the class which the receiver represents.
//		 *
//		 * @return		the receiver's full name including the package path.
//		 *
//		 */
//		getName : function () {
//alap.cov.jsCoverage("bootstrap_3", "n/a","63");
//			var n = this._name;
//alap.cov.jsCoverage("bootstrap_3", "n/a","64");
//			if (n) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","65");
//				return n;
//			}
//			
//alap.cov.jsCoverage("bootstrap_3", "n/a","66");
//			return null;
//		},
//		
//		/**
//		 * Return the simple name of this Class. The simple name does not include
//		 * the package or the name of the enclosing class. The simple name of an
//		 * anonymous class is "".
//		 *
//		 * @return the simple name
//		 *
//		 */
//		getSimpleName : function () {
//			// either a base class, or anonymous class
//			// remove the package name
//alap.cov.jsCoverage("bootstrap_3", "n/a","67");
//			var n = this.getName();
//alap.cov.jsCoverage("bootstrap_3", "n/a","68");
//			var idx = n.lastIndexOf('.');
//alap.cov.jsCoverage("bootstrap_3", "n/a","69");
//			if (idx != -1) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","70");
//				n = n.substring(idx+1);
//			}
//alap.cov.jsCoverage("bootstrap_3", "n/a","71");
//			return n;
//		},
//	
//		/**
//		 * Answers the name of the package to which the receiver belongs.
//		 * For example, Object.class.getPackageName() returns "vjo.dsf".
//		 *
//		 * @return		the receiver's package name.
//		 *
//		 */
//		getPackageName : function () {
//alap.cov.jsCoverage("bootstrap_3", "n/a","72");
//			var n = this.getName();
//alap.cov.jsCoverage("bootstrap_3", "n/a","73");
//			if (n != null) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","74");
//				var i = n.lastIndexOf('.');
//alap.cov.jsCoverage("bootstrap_3", "n/a","75");
//				if (i >= 0) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","76");
//					return n.substring(0, i);
//				}
//			}
//alap.cov.jsCoverage("bootstrap_3", "n/a","77");
//			return "";
//		},
//		
//		/**
//		 * Answers true if the receiver represents an itype.
//		 *
//		 * @return		<code>true</code>
//		 *					if the receiver represents an interface
//		 *              <code>false</code>
//		 *                  if it does not represent an interface
//		 */
//		isInterface : function () {
//alap.cov.jsCoverage("bootstrap_3", "n/a","78");
//			return (this._type === "itype");
//		},
//		
//		/**
//		 * Returns true if the obj is an instance of this class.
//		 */
//		isInstance : function (obj) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","79");
//			var tp = vjo.getType(this.getName());
//alap.cov.jsCoverage("bootstrap_3", "n/a","80");
//			if (tp) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","81");
//				return tp.instanceOf(obj);
//			}
//alap.cov.jsCoverage("bootstrap_3", "n/a","82");
//			return false;
//		},
//		
//		/**
//		 * Answers a string containing a concise, human-readable
//		 * description of the receiver.
//		 *
//		 * @return		a printable representation for the receiver.
//		 */
//		toString : function () {
//alap.cov.jsCoverage("bootstrap_3", "n/a","83");
//			return (this.isInterface() ? "interface " : "class ") + this.getName();
//		},
//		getClass : _getClazz
//	});
//	
//	vjo.extend(vjo.Class,{
//		/**
//		 * Answers a Class object which represents the type
//		 * named by the argument. The name should be the name
//		 * of a type as described in the type definition.
//		 *
//		 * @param		clz	The name of the non-base type class to find
//		 * @return		the named Class
//		 * @throws		Error if a class is not found with the passed name
//		 *
//		 */
//		forName : function (clz) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","84");
//			var err = "Type not found for '" + clz + "'";
//			try {
//alap.cov.jsCoverage("bootstrap_3", "n/a","85");
//				var o = eval(clz);
//alap.cov.jsCoverage("bootstrap_3", "n/a","86");
//				if (o && o.clazz) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","87");
//					return o.clazz;
//				}
//alap.cov.jsCoverage("bootstrap_3", "n/a","88");
//			} catch (e) {
//			}
//alap.cov.jsCoverage("bootstrap_3", "n/a","89");
//			throw err;
//		},
//		clazz : new vjo.Class("vjo.Class", "ctype")
//	});
//	
//alap.cov.jsCoverage("bootstrap_3", "n/a","90");
//	vjo.Object.clazz = new vjo.Class("vjo.Object", "ctype");
//	
//	var ActiveInitMgr = {
//		map : {},
//		inits : {},
//		needs : {},
//		stack : [],
//		inners : {},
//		loaded : {},
//		addType : function (clz) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","91");
//			if (!this.map[clz]) this.map[clz] = [];
//		},
//		addDep : function (clz,dep) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","92");
//			if (!clz) return;
//			
//alap.cov.jsCoverage("bootstrap_3", "n/a","93");
//			this.addType(clz);
//alap.cov.jsCoverage("bootstrap_3", "n/a","94");
//			var aD = this.map[clz];
//alap.cov.jsCoverage("bootstrap_3", "n/a","95");
//			aD[aD.length] = dep;
//			if (vjo.loader) { 
//alap.cov.jsCoverage("bootstrap_3", "n/a","96");
//				var stk = this.stack;
//alap.cov.jsCoverage("bootstrap_3", "n/a","97");
//				if (stk.length==0) stk.push(clz);
//alap.cov.jsCoverage("bootstrap_3", "n/a","98");
//				if (stk[stk.length-1]===clz) stk.push(dep);
//			}
//			
//		},
//		addInner : function (clz,fn) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","99");
//			if (!this.inners[clz]) this.inners[clz] = [];
//alap.cov.jsCoverage("bootstrap_3", "n/a","100");
//			var ins = this.inners[clz];
//alap.cov.jsCoverage("bootstrap_3", "n/a","101");
//			ins.push(fn);
//		},
//		execInners : function (clz) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","102");
//			var ins = this.inners[clz];
//alap.cov.jsCoverage("bootstrap_3", "n/a","103");
//			if (ins) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","104");
//				var len = ins.length;
//alap.cov.jsCoverage("bootstrap_3", "n/a","105");
//				for (var i=0; i<len; i++) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","106");
//					var init = ins[i];
//alap.cov.jsCoverage("bootstrap_3", "n/a","107");
//					if (init) init();
//				}
//			}
//alap.cov.jsCoverage("bootstrap_3", "n/a","108");
//			this.inners[clz] = null;
//		},
//		removeDep : function (clz,dep) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","109");
//			if (!vjo.loader) return;
//alap.cov.jsCoverage("bootstrap_3", "n/a","110");
//			var stk = this.stack;
//alap.cov.jsCoverage("bootstrap_3", "n/a","111");
//			if (stk[stk.length-1]===dep) stk.pop();
//		},
//		addNeeds : function (clz) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","112");
//			var n = this.needs[clz];
//alap.cov.jsCoverage("bootstrap_3", "n/a","113");
//			if (n) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","114");
//				while (n.length>0) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","115");
//					n.pop()();
//				}
//alap.cov.jsCoverage("bootstrap_3", "n/a","116");
//				this.needs[clz] = null;
//			}
//		},
//		deferNeed : function (clz,fn) {
//			var n = this.needs
//alap.cov.jsCoverage("bootstrap_3", "n/a","117");
//			if (!n[clz]) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","118");
//				n[clz] = [];
//			}
//alap.cov.jsCoverage("bootstrap_3", "n/a","119");
//			n[clz].push(fn);
//		},
//		init : function (clz,fn) {//debugger;
//alap.cov.jsCoverage("bootstrap_3", "n/a","120");
//			this.inits[clz] = fn;
//alap.cov.jsCoverage("bootstrap_3", "n/a","121");
//			return true;
//		},
//		load : function(clz) {//debugger;
//alap.cov.jsCoverage("bootstrap_3", "n/a","122");
//			if (!clz || this.loaded[clz]) return;
//alap.cov.jsCoverage("bootstrap_3", "n/a","123");
//			var deps = this.map[clz], ins = this.inits;
//alap.cov.jsCoverage("bootstrap_3", "n/a","124");
//			if (vjo.loader) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","125");
//				var stk = this.stack, len = stk.length;
//alap.cov.jsCoverage("bootstrap_3", "n/a","126");
//				if (len > 0 && stk[len-1]===clz) stk.pop();
//			}
//alap.cov.jsCoverage("bootstrap_3", "n/a","127");
//			if (deps && !this.hasCirDep(clz)) {//debugger;
//alap.cov.jsCoverage("bootstrap_3", "n/a","128");
//				var stk = [];
//				this._pushDep(clz,stk,{});
//alap.cov.jsCoverage("bootstrap_3", "n/a","129");
//				while(stk.length>0) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","130");
//					var dep = stk.pop(), fn = ins[dep];
//alap.cov.jsCoverage("bootstrap_3", "n/a","131");
//					this.addNeeds(dep);
//alap.cov.jsCoverage("bootstrap_3", "n/a","132");
//					if (fn) fn();
//alap.cov.jsCoverage("bootstrap_3", "n/a","133");
//					ins[dep] = null;
//alap.cov.jsCoverage("bootstrap_3", "n/a","134");
//					this.execInners(dep);
//alap.cov.jsCoverage("bootstrap_3", "n/a","135");
//					this.loaded[dep] = true;
//				}
//			}
//			if ((!deps || deps.length==0)) {//no dependencies
//			
//alap.cov.jsCoverage("bootstrap_3", "n/a","136");
//				this.addNeeds(clz);
//alap.cov.jsCoverage("bootstrap_3", "n/a","137");
//				if (ins[clz]) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","138");
//					ins[clz]();
//alap.cov.jsCoverage("bootstrap_3", "n/a","139");
//					ins[clz] = null;
//				}
//alap.cov.jsCoverage("bootstrap_3", "n/a","140");
//				this.execInners(clz);
//alap.cov.jsCoverage("bootstrap_3", "n/a","141");
//				this.loaded[clz] = true;
//			}
//			
//		},
//		_pushDep : function (clz,stack,visited) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","142");
//			var arr = this.map[clz];//, fn = this.inits[clz];
//alap.cov.jsCoverage("bootstrap_3", "n/a","143");
//			stack.push(clz);
//alap.cov.jsCoverage("bootstrap_3", "n/a","144");
//			visited[clz] = true;
//alap.cov.jsCoverage("bootstrap_3", "n/a","145");
//			if (!arr || arr.length===0) return;
//alap.cov.jsCoverage("bootstrap_3", "n/a","146");
//			var len = arr.length, i=0;
//alap.cov.jsCoverage("bootstrap_3", "n/a","147");
//			for (; i<len; i++) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","148");
//				var key = arr[i];
//alap.cov.jsCoverage("bootstrap_3", "n/a","149");
//				if (!visited[key]) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","150");
//					//visited[key] = true;
//alap.cov.jsCoverage("bootstrap_3", "n/a","151");
//					this._pushDep(key,stack,visited);
//				}
//			}
//alap.cov.jsCoverage("bootstrap_3", "n/a","152");
//			return;
//		},
//		_hasCirDep : function (clz,start,visited) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","153");
//			var arr = this.map[start];
//alap.cov.jsCoverage("bootstrap_3", "n/a","154");
//			if (!arr || arr.length===0) return false;
//alap.cov.jsCoverage("bootstrap_3", "n/a","155");
//			var len = arr.length, i=0;
//alap.cov.jsCoverage("bootstrap_3", "n/a","156");
//			for (; i<len; i++) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","157");
//				var key = arr[i];
//alap.cov.jsCoverage("bootstrap_3", "n/a","158");
//				if (vjo.loader){
//alap.cov.jsCoverage("bootstrap_3", "n/a","159");
//					if (key === clz) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","160");
//						return true;
//					}
//				} else {
//alap.cov.jsCoverage("bootstrap_3", "n/a","161");
//					//var pkg = vjo._typeMap[key];
//alap.cov.jsCoverage("bootstrap_3", "n/a","162");
//					if (!vjo.getType(key)) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","163");
//						return true;
//					}
//				}
//alap.cov.jsCoverage("bootstrap_3", "n/a","164");
//				if (!visited[key]) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","165");
//					visited[key] = true;
//alap.cov.jsCoverage("bootstrap_3", "n/a","166");
//					if (this._hasCirDep(clz,key,visited)){
//alap.cov.jsCoverage("bootstrap_3", "n/a","167");
//						return true;
//					}
//				}
//			}
//alap.cov.jsCoverage("bootstrap_3", "n/a","168");
//			return false;
//		},
//		hasCirDep : function (clz) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","169");
//			var aD = this.map[clz], bInline = (!vjo.loader);
//alap.cov.jsCoverage("bootstrap_3", "n/a","170");
//			if (aD) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","171");
//				var len = aD.length;
//alap.cov.jsCoverage("bootstrap_3", "n/a","172");
//				if (bInline) {
//					return this._hasCirDep(clz,clz,{});
//				} else {
//					var stk = this.stack, len2 = stk.length
//alap.cov.jsCoverage("bootstrap_3", "n/a","173");
//					for (var i=0; i< len; i++) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","174");
//						var dep = aD[i];
//alap.cov.jsCoverage("bootstrap_3", "n/a","175");
//						for (var j=0; j<len2; j++) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","176");
//							if (stk[j] === dep) return true;
//						}
//					}
//alap.cov.jsCoverage("bootstrap_3", "n/a","177");
//					if (len2>0) {
//						return this._hasCirDep(clz,clz,{});
//					}
//				}
//			} 
//			
//alap.cov.jsCoverage("bootstrap_3", "n/a","178");
//			return false;
//		}
//	}
//	
//	var TypeDepMgr = {
//		map : {},
//		inherit : {},
//		proto : {},
//		addDep : function (clz,fn,base) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","179");
//			this.map[clz] = base;
//alap.cov.jsCoverage("bootstrap_3", "n/a","180");
//			this.inherit[clz] = fn;
//		},
//		deferProto : function (clz,fn) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","181");
//			if (!this.isDefered(clz)) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","182");
//				return false;
//			}
//alap.cov.jsCoverage("bootstrap_3", "n/a","183");
//			if (!this.proto[clz]) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","184");
//				this.proto[clz] = [];
//			} 
//alap.cov.jsCoverage("bootstrap_3", "n/a","185");
//			this.proto[clz].push(fn);
//alap.cov.jsCoverage("bootstrap_3", "n/a","186");
//			return true;
//		},
//		isDefered : function (clz) {
//			return (typeof this.inherit[clz] == 'function');
//		},
//		init : function (clz) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","187");
//			var inits = [], proto = [], dep, fn = this.inherit[clz], tmp = clz, rv = false, pfn = this.proto[clz];
//alap.cov.jsCoverage("bootstrap_3", "n/a","188");
//			if (fn) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","189");
//				rv = true;
//alap.cov.jsCoverage("bootstrap_3", "n/a","190");
//				inits.push(fn);
//alap.cov.jsCoverage("bootstrap_3", "n/a","191");
//				proto.push(pfn);
//				delete this.inherit[clz]; //remove, already initialized
//alap.cov.jsCoverage("bootstrap_3", "n/a","192");
//				delete this.proto[clz];
//			}
//			else return;
//alap.cov.jsCoverage("bootstrap_3", "n/a","193");
//			while (dep = this.map[tmp]) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","194");
//				fn = this.inherit[dep];
//alap.cov.jsCoverage("bootstrap_3", "n/a","195");
//				pfn = this.proto[dep];
//alap.cov.jsCoverage("bootstrap_3", "n/a","196");
//				if (fn) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","197");
//					inits.push(fn);
//alap.cov.jsCoverage("bootstrap_3", "n/a","198");
//					proto.push(pfn);
//					delete this.inherit[tmp]; //remove, already initialized
//alap.cov.jsCoverage("bootstrap_3", "n/a","199");
//					delete this.proto[tmp];
//				}
//alap.cov.jsCoverage("bootstrap_3", "n/a","200");
//				tmp = dep;
//			}
//alap.cov.jsCoverage("bootstrap_3", "n/a","201");
//			while (inits.length>0) {
//				inits.pop()(); //execute init
//alap.cov.jsCoverage("bootstrap_3", "n/a","202");
//				var p = proto.pop();
//				if (p && p.length>0) {//add props
//					_forEach(p, function (val,key,obj) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","203");
//						val();
//					});
//				}
//			}
//alap.cov.jsCoverage("bootstrap_3", "n/a","204");
//			return rv;
//		}
//	};
//		
//	var BaseProps = {
//		needs : function (clz,alias) {
//			if (!clz || this.vjo._isInner) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","205");
//				return this;
//}
//alap.cov.jsCoverage("bootstrap_3", "n/a","206");
//			var clzs = [], useAlias = false;
//alap.cov.jsCoverage("bootstrap_3", "n/a","207");
//			if (typeof clz == 'string') {
//alap.cov.jsCoverage("bootstrap_3", "n/a","208");
//				clzs = [clz];
//alap.cov.jsCoverage("bootstrap_3", "n/a","209");
//				useAlias = (alias) ? true : false;
//			} else if (vjo.isArray(clz)){
//alap.cov.jsCoverage("bootstrap_3", "n/a","210");
//				clzs = clz;
//			}
//			_forEach(clzs,function(val,key,obj){
//				var cl = val, pObj = vjo._typeMap[clz], idx = cl.lastIndexOf("."), 
//				cn = (idx>-1) ? cl.substring(idx+1) : cl, tp = (pObj) ? pObj.pkg[cn] : null;
//alap.cov.jsCoverage("bootstrap_3", "n/a","212");
//				ActiveInitMgr.addDep(this.vjo._class,cl);
//alap.cov.jsCoverage("bootstrap_3", "n/a","213");
//				if (!tp && vjo.loader) {
//					var tmpScope = vjo._bScope; //save the current stack
//alap.cov.jsCoverage("bootstrap_3", "n/a","214");
//					vjo._bScope = null;
//alap.cov.jsCoverage("bootstrap_3", "n/a","215");
//					vjo.loader.load(cl);
//					vjo._bScope = tmpScope; //restore the stack
//alap.cov.jsCoverage("bootstrap_3", "n/a","216");
//					//tp = pObj.pkg[cn];
//					pObj = vjo._typeMap[clz]
//alap.cov.jsCoverage("bootstrap_3", "n/a","217");
//					tp = (pObj) ? pObj.pkg[cn] : null;
//				}	
//alap.cov.jsCoverage("bootstrap_3", "n/a","218");
//				ActiveInitMgr.removeDep(this.vjo._class,cl);
//				if (alias!==""){//blank alias should not get added to namespace
//					if (tp) {//TODO: check for duplicates?
//						var nm = (useAlias)? alias : cn
//alap.cov.jsCoverage("bootstrap_3", "n/a","219");
//						if (this.vjo[nm] && this.vjo[nm]!==tp) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","220");
//							throw "Name collision with type '" + nm + "' in need list.";
//						}
//						this.vjo[nm] = tp
//					} else {
//						ActiveInitMgr.deferNeed(this.vjo._class,(function(shortname,fullname,ctx){
//							return function(){
//alap.cov.jsCoverage("bootstrap_3", "n/a","221");
//								var tp = vjo.getType(fullname);
//alap.cov.jsCoverage("bootstrap_3", "n/a","222");
//								if (this.vjo[shortname] && this.vjo[shortname]!==tp) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","223");
//									throw "Name collision with " + nm + "in need list.";
//								}		
//alap.cov.jsCoverage("bootstrap_3", "n/a","224");
//								ctx.vjo[shortname] = vjo.getType(fullname);
//							}
//alap.cov.jsCoverage("bootstrap_3", "n/a","225");
//						})((useAlias)? alias : cn,cl,this));
//					}
//				}
//alap.cov.jsCoverage("bootstrap_3", "n/a","226");
//			},this);
//alap.cov.jsCoverage("bootstrap_3", "n/a","227");
//			return this;
//		},
//		props : function (obj) {
//			_forEach(obj,function(val,key,obj){
//alap.cov.jsCoverage("bootstrap_3", "n/a","228");
//				if (_isValidProp(key)) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","229");
//					var o = this[key] = val;
//alap.cov.jsCoverage("bootstrap_3", "n/a","230");
//					if (_addInner(this,o,'s_inners',key)) {//debugger;
//alap.cov.jsCoverage("bootstrap_3", "n/a","231");
//						if (this.vjo[key]) throw "'" + key + "' in type '" + this.vjo._class + "' conflicts with needed type name";
//					    var _v = {};
//alap.cov.jsCoverage("bootstrap_3", "n/a","232");
//					    vjo.extend(_v,this.vjo);
//alap.cov.jsCoverage("bootstrap_3", "n/a","233");
//					    delete _v.s_inners;
//alap.cov.jsCoverage("bootstrap_3", "n/a","234");
//					    vjo.extend(_v,o.vjo);
//alap.cov.jsCoverage("bootstrap_3", "n/a","235");
//					    _v[key] = o;
//alap.cov.jsCoverage("bootstrap_3", "n/a","236");
//					    o.vjo = _v;
//alap.cov.jsCoverage("bootstrap_3", "n/a","237");
//					    //o.vjo.outer = base;
//alap.cov.jsCoverage("bootstrap_3", "n/a","238");
//					    if (!this.vjo._isInner) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","239");
//					    	if (o.vjo._init) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","240");
//					    		ActiveInitMgr.addInner(this.vjo._class,o.vjo._init);
//					    	}
//alap.cov.jsCoverage("bootstrap_3", "n/a","241");
//					    	var rt = this.vjo._class;
//						_updateInners(rt,rt+"."+key,o)
//					    }
//					}
//					else {
//					    if (typeof o == 'function' && !o._name && !o.vjo) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","242");
//					    	o._name = key;
//					    }
//					}
//				}
//				
//alap.cov.jsCoverage("bootstrap_3", "n/a","243");
//			},this);
//alap.cov.jsCoverage("bootstrap_3", "n/a","244");
//			return this;
//		},
//		protos : function (obj) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","245");
//			//bCanInherit = false;
//			var fn = vjo.proxy(function(){
//				_forEach(obj,function(val,key,obj){
//alap.cov.jsCoverage("bootstrap_3", "n/a","246");
//					if (key!='base' && key!='b') {
//alap.cov.jsCoverage("bootstrap_3", "n/a","247");
//						var prev = this.prototype[key], isType = _isVjoType(val);
//						if (prev && typeof prev == 'function' && key.indexOf("constructs")!=0 && key.indexOf("_ovld")!=(key.length-5) && !_isVjoType(prev) && typeof val == 'function' && !isType) {
//							this.prototype[key] = (function (func,type) {
//								return function () {
//									//if a base class calls a overridden method from the
//									//derived class, we must update the vjo namespace
//									var cbase = this.base; //keep base instance
//alap.cov.jsCoverage("bootstrap_3", "n/a","248");
//									this.base = (type.prototype._getBase)? type.prototype._getBase() : null;
//									if (this.base) this.base._parent = this; //keep toplevel scope
//									var t = {};
//alap.cov.jsCoverage("bootstrap_3", "n/a","249");
//									t.vjo = this.vjo;
//alap.cov.jsCoverage("bootstrap_3", "n/a","250");
//									_fixScope(type,this);
//alap.cov.jsCoverage("bootstrap_3", "n/a","251");
//									var rv = func.apply(this,arguments);
//alap.cov.jsCoverage("bootstrap_3", "n/a","252");
//									_fixScope(t,this);
//alap.cov.jsCoverage("bootstrap_3", "n/a","253");
//									this.base = cbase;
//alap.cov.jsCoverage("bootstrap_3", "n/a","254");
//									return rv;
//								};
//								
//alap.cov.jsCoverage("bootstrap_3", "n/a","255");
//							})(val,this);
//						} else {
//alap.cov.jsCoverage("bootstrap_3", "n/a","256");
//							this.prototype[key] = val;
//						}
//alap.cov.jsCoverage("bootstrap_3", "n/a","257");
//						if (!_addInner(this,val,'_inners',key)) {
//							if (typeof val == 'function' && !val._name && !isType) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","258");
//					    			val._name = key;
//							}
//						} else {
//alap.cov.jsCoverage("bootstrap_3", "n/a","259");
//							if (this.vjo[key]) throw "'" + key + "' in type '" + this.vjo._class + "' conflicts with needed type name";
//						}
//					}
//alap.cov.jsCoverage("bootstrap_3", "n/a","260");
//			},this);
//			//For IE
//alap.cov.jsCoverage("bootstrap_3", "n/a","261");
//			if (obj.toString != Object.prototype.toString) {
//				//It has custom toString method!
//alap.cov.jsCoverage("bootstrap_3", "n/a","262");
//				this.prototype.toString = obj.toString;
//			}
//alap.cov.jsCoverage("bootstrap_3", "n/a","263");
//			},this);
//			
//alap.cov.jsCoverage("bootstrap_3", "n/a","264");
//			if (!TypeDepMgr.deferProto(this.vjo._class,fn)) {
//				fn(); //not deferred, add proto
//			}
//alap.cov.jsCoverage("bootstrap_3", "n/a","265");
//			return this;
//		},
//		instanceOf : function (obj) {//debugger;
//			//TODO: mac ie 5? need to support? currently tier 3 browser
//alap.cov.jsCoverage("bootstrap_3", "n/a","266");
//			return (obj instanceof this);
//		},
//		inits : function (func) {
//		    //the class may have already been created, so there's no need
//		    //to call static initializer once again.
//alap.cov.jsCoverage("bootstrap_3", "n/a","267");
//		    var pObj = vjo.getType(this.vjo._class);
//		    if (typeof pObj == 'function' || this.vjo._isInner) {
//			var fn = (function (scp, fn) {
//				return function () {
//alap.cov.jsCoverage("bootstrap_3", "n/a","268");
//					fn.call(scp);
//				}
//alap.cov.jsCoverage("bootstrap_3", "n/a","269");
//			})(this, func);
//alap.cov.jsCoverage("bootstrap_3", "n/a","270");
//			if (this.vjo._isInner) this.vjo._init = fn;
//			else ActiveInitMgr.init(this.vjo._class,fn);
//alap.cov.jsCoverage("bootstrap_3", "n/a","271");
//			    //func.call(this);
//		    }
//alap.cov.jsCoverage("bootstrap_3", "n/a","272");
//		    ActiveInitMgr.load(this.vjo._class);
//alap.cov.jsCoverage("bootstrap_3", "n/a","273");
//		    return this;
//		},
//		endType : function () {
//alap.cov.jsCoverage("bootstrap_3", "n/a","274");
//			_updateInnerEtypes(this.vjo);
//alap.cov.jsCoverage("bootstrap_3", "n/a","275");
//			ActiveInitMgr.load(this.vjo._class);
//alap.cov.jsCoverage("bootstrap_3", "n/a","276");
//			if (vjo.validateType) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","277");
//				vjo.validateType(this);
//			}
//alap.cov.jsCoverage("bootstrap_3", "n/a","278");
//			return this;
//		}};
//	
//	var BaseInheritance = {
//		satisfies : function (type) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","279");
//			var clzs = [];
//alap.cov.jsCoverage("bootstrap_3", "n/a","280");
//			if (vjo.isArray(type)){
//alap.cov.jsCoverage("bootstrap_3", "n/a","281");
//				clzs = type;
//			} else {
//alap.cov.jsCoverage("bootstrap_3", "n/a","282");
//				clzs = [type];
//			}
//			
//			_forEach(clzs,function(val,key,obj){
//alap.cov.jsCoverage("bootstrap_3", "n/a","283");
//				var len = this._satisfiers.length, cl = val, type;
//alap.cov.jsCoverage("bootstrap_3", "n/a","284");
//				if (_isVjoType(cl)) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","285");
//					type = cl;
//					var clz = type.vjo._class || "", idx = clz.lastIndexOf("."), 
//					cn = (idx>-1) ? clz.substring(idx+1) : clz;
//alap.cov.jsCoverage("bootstrap_3", "n/a","287");
//					if (cn) this.vjo[cn] = type;
//				} else {
//					this.needs(cl); //make sure class is loaded
//				}
//				
//alap.cov.jsCoverage("bootstrap_3", "n/a","288");
//				var iface = (type) ? type : vjo.getType(cl);
//				if (iface) {//TODO: validate
//alap.cov.jsCoverage("bootstrap_3", "n/a","289");
//					this._satisfiers[len] = cl;
//					for (var i in iface) {	
//alap.cov.jsCoverage("bootstrap_3", "n/a","290");
//						var val = iface[i];
//						if (_isValidProp(i) && (typeof val == "number" 
//							|| typeof val == "string" || typeof val == "boolean")) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","292");
//							this[i] = val;
//						}
//					}
//				}
//alap.cov.jsCoverage("bootstrap_3", "n/a","293");
//			},this);
//			
//alap.cov.jsCoverage("bootstrap_3", "n/a","294");
//			return this;
//		},
//		//Depricated
//		/*mixinProps : function (mType) { //Depricated
//			//var pObj = vjo.createPkg(mType), 
//			
//alap.cov.jsCoverage("bootstrap_3", "n/a","295");
//			this.needs(mType);
//alap.cov.jsCoverage("bootstrap_3", "n/a","296");
//			var mxn = vjo.getType(mType);
//alap.cov.jsCoverage("bootstrap_3", "n/a","297");
//			if (!mxn || !mxn.vjo || mxn.vjo._type!='mtype') return this;
//alap.cov.jsCoverage("bootstrap_3", "n/a","298");
//			if (!this._mixinProps) this._mixinProps = [];
//alap.cov.jsCoverage("bootstrap_3", "n/a","299");
//			this._mixinProps[this._mixinProps.length] = mxn._props;
//			//TODO: what to do with collisions?
//alap.cov.jsCoverage("bootstrap_3", "n/a","300");
//			this.props(mxn._props);
//alap.cov.jsCoverage("bootstrap_3", "n/a","301");
//			_copyNS(mxn.vjo,this.vjo);
//alap.cov.jsCoverage("bootstrap_3", "n/a","302");
//			this._expects = (mxn._expects) ? mxn._expects : null;
//alap.cov.jsCoverage("bootstrap_3", "n/a","303");
//			return this;
//		},*/
//		mixin : function (mType) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","304");
//			//var pObj = vjo.createPkg(mType), mxn = pObj.pkg[pObj.className];
//alap.cov.jsCoverage("bootstrap_3", "n/a","305");
//			this.needs(mType);
//alap.cov.jsCoverage("bootstrap_3", "n/a","306");
//			var mxn = vjo.getType(mType);
//alap.cov.jsCoverage("bootstrap_3", "n/a","307");
//			if (!mxn || !mxn.vjo || mxn.vjo._type!='mtype') return this;
//alap.cov.jsCoverage("bootstrap_3", "n/a","308");
//			if (!this._mixins) this._mixins = [];
//alap.cov.jsCoverage("bootstrap_3", "n/a","309");
//			if (!this._mixinProps) this._mixinProps = [];
//alap.cov.jsCoverage("bootstrap_3", "n/a","310");
//			this._mixins[this._mixins.length] = mxn._protos;
//alap.cov.jsCoverage("bootstrap_3", "n/a","311");
//			this._mixinProps[this._mixinProps.length] = mxn._props;
//			//TODO: what to do with collisions?
//alap.cov.jsCoverage("bootstrap_3", "n/a","312");
//			this.protos(mxn._protos);
//alap.cov.jsCoverage("bootstrap_3", "n/a","313");
//			this.props(mxn._props);
//alap.cov.jsCoverage("bootstrap_3", "n/a","314");
//			//_copyNS(mxn.vjo,this.vjo);
//alap.cov.jsCoverage("bootstrap_3", "n/a","315");
//			this._expects = (mxn._expects) ? mxn._expects : null;
//alap.cov.jsCoverage("bootstrap_3", "n/a","316");
//			return this;
//		},
//		inherits : function (supClass, isB) { //check order if inherits is called after proto or props
//alap.cov.jsCoverage("bootstrap_3", "n/a","317");
//			if (!isB && !_isValidInh(supClass)) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","318");
//				throw "Cannot inherit from '" + supClass + "'";
//			}
//alap.cov.jsCoverage("bootstrap_3", "n/a","319");
//		    var isVO = ("vjo.Object" === supClass);
//alap.cov.jsCoverage("bootstrap_3", "n/a","320");
//		    if (!isB && isVO) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","321");
//		        return this;
//		    }
//alap.cov.jsCoverage("bootstrap_3", "n/a","322");
//			var type;
//alap.cov.jsCoverage("bootstrap_3", "n/a","323");
//			if (_isVjoType(supClass)) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","324");
//				type = supClass;
//				var clz = type.vjo._class || "", idx = clz.lastIndexOf("."), 
//				cn = (idx>-1) ? clz.substring(idx+1) : clz;
//alap.cov.jsCoverage("bootstrap_3", "n/a","326");
//				if (cn) this.vjo[cn] = type;
//			} else {
//alap.cov.jsCoverage("bootstrap_3", "n/a","327");
//				if (isVO) {
//					type = vjo.Object;				
//				} else {
//					this.needs(supClass); //make sure class is loaded
//alap.cov.jsCoverage("bootstrap_3", "n/a","328");
//					type = (this.vjo[supClass]) ? this.vjo[supClass] : vjo.getType(supClass);
//				}
//			}
//			
//			if (this._inherits) throw "cannot have more than one inherits"; //error - Cannot have more than one inherits!
//			else this._inherits = supClass;
//			
//			var fn = vjo.proxy(function () {
//alap.cov.jsCoverage("bootstrap_3", "n/a","329");
//					//var type = vjo.getType(supClass);
//alap.cov.jsCoverage("bootstrap_3", "n/a","330");
//					_createInherits(this,type);
//alap.cov.jsCoverage("bootstrap_3", "n/a","331");
//				}, this);
//alap.cov.jsCoverage("bootstrap_3", "n/a","332");
//			if (type) fn(isB);
//			else TypeDepMgr.addDep(this.vjo._class,fn,supClass);
//alap.cov.jsCoverage("bootstrap_3", "n/a","333");
//			return this;
//		}};
//		
//	
//	vjo.ctype = function (clz) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","334");
//		var base = _createType(clz);
//alap.cov.jsCoverage("bootstrap_3", "n/a","335");
//		vjo.extend(base,BaseProps);
//alap.cov.jsCoverage("bootstrap_3", "n/a","336");
//		vjo.extend(base,BaseInheritance);
//alap.cov.jsCoverage("bootstrap_3", "n/a","337");
//		base.vjo._type = 'ctype';
//alap.cov.jsCoverage("bootstrap_3", "n/a","338");
//		base._satisfiers = [];
//alap.cov.jsCoverage("bootstrap_3", "n/a","339");
//		base._inherits = null;
//		base.singleton = function () {
//			return base;  //TODO: self instantiate
//		};
//		
//		base.makeFinal = function () {
//alap.cov.jsCoverage("bootstrap_3", "n/a","340");
//		    return base;
//		};
//		
//alap.cov.jsCoverage("bootstrap_3", "n/a","341");
//		base.inherits("vjo.Object",true);
//alap.cov.jsCoverage("bootstrap_3", "n/a","342");
//		base._inherits = null;
//alap.cov.jsCoverage("bootstrap_3", "n/a","343");
//		_createClazz(base);
//
//		//if class not specified, return class
//alap.cov.jsCoverage("bootstrap_3", "n/a","344");
//		base.vjo._isInner = (!clz || _isInnerClass(clz));
//alap.cov.jsCoverage("bootstrap_3", "n/a","345");
//		if (!clz) return base;
//		//setup shorthand scope, based on needs
//		//base.vjo = {};
//alap.cov.jsCoverage("bootstrap_3", "n/a","346");
//		var pObj = _createPkg(clz);
//		if (vjo._bScope) {//backwards compat, remove
//alap.cov.jsCoverage("bootstrap_3", "n/a","347");
//			vjo.extend(base.vjo,vjo._bScope);
//		}
//alap.cov.jsCoverage("bootstrap_3", "n/a","348");
//		base.vjo.b = (vjo._bScope) ? vjo._bScope : null;
//		//base.vjo.$s = base; //TODO: remove
//alap.cov.jsCoverage("bootstrap_3", "n/a","349");
//		base.vjo[pObj.className] = base;
//alap.cov.jsCoverage("bootstrap_3", "n/a","350");
//		//base.b = vjo._bScope;
//alap.cov.jsCoverage("bootstrap_3", "n/a","351");
//		vjo._bScope = null;
//		
//		//if class already exists, just return the type. do not override existing class
//alap.cov.jsCoverage("bootstrap_3", "n/a","352");
//		var tp = (pObj.pkg[pObj.className])? base : (pObj.pkg[pObj.className] = base);
//		return tp; 
//	};
//		
//	vjo.atype = function (clz) {
//		    //should not have a constructor
//alap.cov.jsCoverage("bootstrap_3", "n/a","353");
//		    var type = this.ctype(clz);
//alap.cov.jsCoverage("bootstrap_3", "n/a","354");
//		    type.vjo._type = 'atype';
//alap.cov.jsCoverage("bootstrap_3", "n/a","355");
//		    return type;
//		}
//	
//	vjo.itype = function (clz) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","356");
//		var base = _createType(clz,true);
//alap.cov.jsCoverage("bootstrap_3", "n/a","357");
//		vjo.extend(base,BaseProps);
//alap.cov.jsCoverage("bootstrap_3", "n/a","358");
//		base.vjo._type = 'itype';
//		base.instanceOf = function (obj) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","359");
//			    var rv = true, proto = this.prototype;
//alap.cov.jsCoverage("bootstrap_3", "n/a","360");
//			    for (var prop in proto) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","361");
//				    if (_isValidInst(prop) && typeof obj[prop] == 'undefined') {
//alap.cov.jsCoverage("bootstrap_3", "n/a","362");
//					    rv = false;
//alap.cov.jsCoverage("bootstrap_3", "n/a","363");
//					    break;
//				    }
//			    }
//alap.cov.jsCoverage("bootstrap_3", "n/a","364");
//			    return rv;
//		};
//		base.inherits = function (supClass) { //check order if inherits is called after proto or props
//alap.cov.jsCoverage("bootstrap_3", "n/a","365");
//			if (!_isValidInh(supClass)) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","366");
//				throw "Cannot inherit from '" + supClass + "'";
//			}
//			this.needs(supClass); //make sure class is loaded
//			var type = (this.vjo[supClass]) ? this.vjo[supClass] : 
//alap.cov.jsCoverage("bootstrap_3", "n/a","367");
//			    (this.vjo.b && this.vjo.b[supClass]) ? this.vjo.b[supClass] : vjo.getType(supClass);
//			var fn = vjo.proxy(function () {
//alap.cov.jsCoverage("bootstrap_3", "n/a","368");
//					var type = vjo.getType(supClass);
//alap.cov.jsCoverage("bootstrap_3", "n/a","369");
//					_createInherits(this,type);
//alap.cov.jsCoverage("bootstrap_3", "n/a","370");
//				}, this);
//alap.cov.jsCoverage("bootstrap_3", "n/a","371");
//			if (type) fn();
//			else TypeDepMgr.addDep(this.vjo._class,fn,supClass);
//alap.cov.jsCoverage("bootstrap_3", "n/a","372");
//			return this;
//		};
//		
//alap.cov.jsCoverage("bootstrap_3", "n/a","373");
//		_createClazz(base);
//alap.cov.jsCoverage("bootstrap_3", "n/a","374");
//		//debugger;
//		//if class not specified, return class
//alap.cov.jsCoverage("bootstrap_3", "n/a","375");
//		if (!clz || _isInnerClass(clz)) base.vjo._isInner = true;
//alap.cov.jsCoverage("bootstrap_3", "n/a","376");
//		if (!clz) return base;
//alap.cov.jsCoverage("bootstrap_3", "n/a","377");
//		var pObj = _createPkg(clz);
//alap.cov.jsCoverage("bootstrap_3", "n/a","378");
//		base.vjo[pObj.className] = base;
//		//if class already exists, just return the type. do not override existing class
//		return (pObj.pkg[pObj.className])? base : (pObj.pkg[pObj.className] = base); 
//	};
//		
//	function _addMixinMethods(to, methods, ns) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","379");
//		if (!methods || typeof methods != 'object') return;
//alap.cov.jsCoverage("bootstrap_3", "n/a","380");
//		var b = true;
//alap.cov.jsCoverage("bootstrap_3", "n/a","381");
//		for (var i in methods) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","382");
//			b = false;
//alap.cov.jsCoverage("bootstrap_3", "n/a","383");
//			if (!reservedMProp[i]) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","384");
//				var m = methods[i];
//				to[i] = (typeof m != 'function') ? m : (function (method,ns) {
//					return function () {//debugger;
//						var clz = ns._class, tp = vjo._typeMap[clz], b = typeof this == 'function';
//alap.cov.jsCoverage("bootstrap_3", "n/a","385");
//						var _v = this.vjo;
//alap.cov.jsCoverage("bootstrap_3", "n/a","386");
//						this.vjo = ns;
//alap.cov.jsCoverage("bootstrap_3", "n/a","387");
//						if (!this.vjo[tp.className]) {
//							//TODO update _class/_clazz and update target class method
//							//of anything in expects
//alap.cov.jsCoverage("bootstrap_3", "n/a","388");
//							this.vjo[tp.className] = (b) ? this : this.constructor;
//						}
//alap.cov.jsCoverage("bootstrap_3", "n/a","389");
//						var rv = method.apply(this,arguments);
//alap.cov.jsCoverage("bootstrap_3", "n/a","390");
//						this.vjo = _v;
//alap.cov.jsCoverage("bootstrap_3", "n/a","391");
//						return rv;
//					};
//alap.cov.jsCoverage("bootstrap_3", "n/a","392");
//				})(m,ns);
//			}
//		}
//alap.cov.jsCoverage("bootstrap_3", "n/a","393");
//		return b;
//	};
//	
//	vjo.mtype = function (clz) {
//		var base = {
//		    vjo : { _type : 'mtype', _class:clz },
//		    _props : null,
//		    _protos : {},
//		    _expects : "",
//		    _satisfiers : [],
//		    needs : function () {
//alap.cov.jsCoverage("bootstrap_3", "n/a","394");
//		    	return BaseProps.needs.apply(this,arguments);
//		    },
//		    props : function (props) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","395");
//		    	var p;
//alap.cov.jsCoverage("bootstrap_3", "n/a","396");
//		    	if (!this._props) {
//				p = {}; 
//			} else {
//alap.cov.jsCoverage("bootstrap_3", "n/a","397");
//				p = this._props;
//			}
//alap.cov.jsCoverage("bootstrap_3", "n/a","398");
//			if (!_addMixinMethods(p,props,this.vjo)) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","399");
//				if (!this._props) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","400");
//					this._props = p;
//				} 
//			}
//alap.cov.jsCoverage("bootstrap_3", "n/a","401");
//			return this;
//		    },
//		    protos :  function (protos) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","402");
//			_addMixinMethods(this._protos,protos,this.vjo);
//alap.cov.jsCoverage("bootstrap_3", "n/a","403");
//			return this;
//		    },
//		    expects : function (clz) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","404");
//			this._expects = clz;
//alap.cov.jsCoverage("bootstrap_3", "n/a","405");
//			return this;
//		    },
//		    satisfies : function (clz) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","406");
//			this._satisfiers[this._satisfiers.length] = clz;
//alap.cov.jsCoverage("bootstrap_3", "n/a","407");
//			return this;
//		    },
//			
//		    endType : function () {
//			//TODO - Process mtype endType here...
//alap.cov.jsCoverage("bootstrap_3", "n/a","408");
//			return this;
//		    }
//		};
//		
//		//if class not specified, return class
//alap.cov.jsCoverage("bootstrap_3", "n/a","409");
//		if (!clz || _isInnerClass(clz)) base.vjo._isInner = true;
//alap.cov.jsCoverage("bootstrap_3", "n/a","410");
//		if (!clz) return base;
//		
//alap.cov.jsCoverage("bootstrap_3", "n/a","411");
//		var pObj = _createPkg(clz);
//		//if class already exists, just return the type. do not override existing class
//		return (pObj.pkg[pObj.className])? base : (pObj.pkg[pObj.className] = base); 
//	};
//	
//	vjo.etype = function (clz) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","412");
//		_createEnum();
//		var base = function (args) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","413");
//		    this.vjo = base.vjo;
//
//alap.cov.jsCoverage("bootstrap_3", "n/a","414");
//			if (args != false) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","415");
//				if (!this.constructs) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","416");
//					throw "'" + this.vjo._class + "' cannot be instantiated";
//				}
//alap.cov.jsCoverage("bootstrap_3", "n/a","417");
//				var rv = this.constructs.apply(this,args);
//			}
//		};
//
//		base.vjo = {_class:clz, _type:'etype'};
//alap.cov.jsCoverage("bootstrap_3", "n/a","418");
//		base.vjo._isInner = (!clz || _isInnerClass(clz));
//alap.cov.jsCoverage("bootstrap_3", "n/a","419");
//		vjo.extend(base,BaseProps);
//		base.satisfies = function () {
//alap.cov.jsCoverage("bootstrap_3", "n/a","420");
//			return BaseInheritance.satisfies.apply(this, arguments);
//		};
//alap.cov.jsCoverage("bootstrap_3", "n/a","421");
//		base._satisfiers = [];
//alap.cov.jsCoverage("bootstrap_3", "n/a","422");
//		base._enums = [];
//alap.cov.jsCoverage("bootstrap_3", "n/a","423");
//		base.from = vjo.Enum.from;
//		vjo.extend(base, {inherits : BaseInheritance.inherits, mixin : BaseInheritance.mixin, mixinProps : BaseInheritance.mixinProps});
//alap.cov.jsCoverage("bootstrap_3", "n/a","424");
//		base.inherits("vjo.Enum", true);
//		base.inherits = function () {
//alap.cov.jsCoverage("bootstrap_3", "n/a","425");
//			throw "Invalid type definition. etype cannot be inheritted from another type";
//		};
//alap.cov.jsCoverage("bootstrap_3", "n/a","426");
//		base.prototype.toString = vjo.Enum.prototype.name;
//
//		base.values = function (vals) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","427");
//			if (arguments.length == 0) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","428");
//				if (typeof this._enums.slice != 'undefined') {
//alap.cov.jsCoverage("bootstrap_3", "n/a","429");
//					return this._enums.slice();
//				} else {
//alap.cov.jsCoverage("bootstrap_3", "n/a","430");
//					var a = [];
//alap.cov.jsCoverage("bootstrap_3", "n/a","431");
//					for (var i = 0; i < this._enums.length; i++) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","432");
//						if (typeof this._enums[i] != 'undefined') {
//alap.cov.jsCoverage("bootstrap_3", "n/a","433");
//							a[i] = this._enums[i];
//						}
//					}
//alap.cov.jsCoverage("bootstrap_3", "n/a","434");
//					return a;
//				}
//			} else {
//alap.cov.jsCoverage("bootstrap_3", "n/a","435");
//		    	var ord = 0;
//alap.cov.jsCoverage("bootstrap_3", "n/a","436");
//		    	if (typeof vals == "string" && vals.length > 0) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","437");
//					while (vals.indexOf(" ") > -1) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","438");
//						vals = vals.replace(" ", "");
//					}
//	    			//Simple case...
//	    			//  ex: .values("MON,TUE,WED")
//	    			// or .values("MON:1, TUE, WED")
//alap.cov.jsCoverage("bootstrap_3", "n/a","439");
//		    		if (vals.indexOf(",") > 0) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","440");
//		    			var a = vals.split(","), t;
//alap.cov.jsCoverage("bootstrap_3", "n/a","441");
//			    		if (a[0] && a[0].indexOf(":") > 0) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","442");
//			    			throw "Invalid labels for etype values: " + a[0];
//			    		}
//alap.cov.jsCoverage("bootstrap_3", "n/a","443");
//		    			for (var i = 0, l = a.length; i < l; i++) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","444");
//		    				var eV = a[i];
//alap.cov.jsCoverage("bootstrap_3", "n/a","445");
//		    				if (i==0 && t && t.length>0) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","446");
//		    					eV = t[0];
//		    				} else {
//alap.cov.jsCoverage("bootstrap_3", "n/a","447");
//		    					if (a[i].indexOf(":")>-1) {
//		    						//Error case
//alap.cov.jsCoverage("bootstrap_3", "n/a","448");
//		    						eV = a[i].split(":")[0];
//		    					}
//		    				}
//alap.cov.jsCoverage("bootstrap_3", "n/a","449");
//		    				this._enums[this._enums.length] = new base(false);
//alap.cov.jsCoverage("bootstrap_3", "n/a","450");
//		    				this._enums[this._enums.length - 1]._name = eV;
//		    			}
//		    		} else {
//alap.cov.jsCoverage("bootstrap_3", "n/a","451");
//	    				this._enums[0] = new base(false);
//alap.cov.jsCoverage("bootstrap_3", "n/a","452");
//	    				this._enums[0]._name = vals;
//		    		}
//		    	} else {
//					//Complex case...
//					//  ex: values({MON:[true],TUE:[true],SUN:[false]})
//alap.cov.jsCoverage("bootstrap_3", "n/a","453");
//					for (var itm in vals) {
//						this._enums[this._enums.length] = new base(vals[itm]);//TODO
//alap.cov.jsCoverage("bootstrap_3", "n/a","454");
//						this._enums[this._enums.length - 1]._name = itm;
//					}
//		    	}
//		    	
//		    	//Create static refs for enums
//alap.cov.jsCoverage("bootstrap_3", "n/a","455");
//		    	for (var i = 0, l = this._enums.length; i < l; i++) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","456");
//		    		if (typeof this._enums[i] != 'undefined') {
//alap.cov.jsCoverage("bootstrap_3", "n/a","457");
//		    			var nm = this._enums[i]._name;
//alap.cov.jsCoverage("bootstrap_3", "n/a","458");
//						if (this[nm]) {
//							//Validate props members against values
//alap.cov.jsCoverage("bootstrap_3", "n/a","459");
//							throw "Invalid prop member. Cannot use etype value as prop member.";
//						}
//alap.cov.jsCoverage("bootstrap_3", "n/a","460");
//			    		this[nm] = this._enums[i];
//alap.cov.jsCoverage("bootstrap_3", "n/a","461");
//			    		this[nm]._ord = ord++;
//		    		}
//		    	}
//			}
//alap.cov.jsCoverage("bootstrap_3", "n/a","462");
//			return this;
//			
//		};
//		    
//		base.endType = function () {
//alap.cov.jsCoverage("bootstrap_3", "n/a","463");
//			_updateInnerEtypes(this.vjo);
//			
//alap.cov.jsCoverage("bootstrap_3", "n/a","464");
//			ActiveInitMgr.load(this.vjo._class);
//			//Prevent the instantiation of this object
//alap.cov.jsCoverage("bootstrap_3", "n/a","465");
//			if (this.prototype.constructs) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","466");
//				this.prototype.constructs = null;
//			}
//alap.cov.jsCoverage("bootstrap_3", "n/a","467");
//			return this;
//		}
//alap.cov.jsCoverage("bootstrap_3", "n/a","468");
//		_createClazz(base);
//			
//		
//	    //if class not specified, return class
//alap.cov.jsCoverage("bootstrap_3", "n/a","469");
//		if (!clz) return base;
//		
//alap.cov.jsCoverage("bootstrap_3", "n/a","470");
//		var pObj = _createPkg(clz);
//alap.cov.jsCoverage("bootstrap_3", "n/a","471");
//		base.vjo.b = (vjo._bScope) ? vjo._bScope : null;
//alap.cov.jsCoverage("bootstrap_3", "n/a","472");
//		base.vjo[pObj.className] = base;
//alap.cov.jsCoverage("bootstrap_3", "n/a","473");
//		vjo._bScope = null;
//		
//		return (pObj.pkg[pObj.className])? base : (pObj.pkg[pObj.className] = base); 
//	};
//	
//	vjo.extend({
//		sysout : { //do nothing or proxy to firebug console
//			print : function() {
//alap.cov.jsCoverage("bootstrap_3", "n/a","474");
//			    if (typeof console != "undefined") {
//alap.cov.jsCoverage("bootstrap_3", "n/a","475");
//				    console.info.apply(this, arguments);
//			    }
//			},
//			println : function() {
//alap.cov.jsCoverage("bootstrap_3", "n/a","476");
//			    if (typeof console != "undefined") {
//alap.cov.jsCoverage("bootstrap_3", "n/a","477");
//				    console.info.apply(this, arguments);
//			    }
//			},
//			printStackTrace : function() {}
//		},
//		syserr : {
//			print : function() {
//alap.cov.jsCoverage("bootstrap_3", "n/a","478");
//				if (typeof console != "undefined") {
//alap.cov.jsCoverage("bootstrap_3", "n/a","479");
//					console.warn.apply(this, arguments);
//				}
//			},
//			println : function() {
//alap.cov.jsCoverage("bootstrap_3", "n/a","480");
//				if (typeof console != "undefined") {
//alap.cov.jsCoverage("bootstrap_3", "n/a","481");
//					console.warn.apply(this, arguments);
//				}
//			},
//			printStackTrace : function() {}
//		},
//		jsunit : {
//		    assertEquals : function(){},
//		    assertTrue : function(){},
//		    assertFalse : function(){}
//		}
//	});
//	
//	vjo.type = vjo.ctype; //backwards compatibility
//	
//	/*
//		reserved words
//	*/
//	var reservedProp = {}, reservedProto = {}, reservedMProp = {}, reservedClz = {}, reservedInh = {};
//	_forEach("props protos inherits prototype inits satisfies b _mixins mixin _mixinProps mixinProps _inherits _satisfiers singleton makeFinal instanceOf endType _errors needs vjo".split(" "),
//		function (val,key,obj) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","482");
//			this[val] = true;
//alap.cov.jsCoverage("bootstrap_3", "n/a","483");
//		},reservedProp);
//	_forEach("constructs getClass _getBase base vjo".split(" "),
//		function (val,key,obj) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","484");
//			this[val] = true;
//alap.cov.jsCoverage("bootstrap_3", "n/a","485");
//		},reservedProto);
//	_forEach("props protos _props _protos vjo _expects expects _satisfiers satisfies endType".split(" "),
//		function (val,key,obj) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","486");
//			this[val] = true;
//alap.cov.jsCoverage("bootstrap_3", "n/a","487");
//		},reservedMProp);
//	_forEach("vjo.Class vjo.Object".split(" "),
//		function (val,key,obj) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","488");
//			this[val] = true;
//alap.cov.jsCoverage("bootstrap_3", "n/a","489");
//		},reservedClz);
//	
//	/*
//		utility methods used by bootstrap
//	*/
//	function _createType(clz, isI) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","490");
//		isI = isI || false;
//alap.cov.jsCoverage("bootstrap_3", "n/a","491");
//		if (!_isValidClz(clz)) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","492");
//			throw "Invalid type name '" + clz + "'";
//		}
//alap.cov.jsCoverage("bootstrap_3", "n/a","493");
//		var oType = _createPkg(clz);//, /*bCanInherit = true,*/ _mixinProps = [], _mixins = [];
//		var base = function(isB) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","494");
//			this.constructor = base;
//			
//alap.cov.jsCoverage("bootstrap_3", "n/a","495");
//			if (TypeDepMgr.init(base.vjo._class)) {
//				_forEach(base.prototype,function(val,key,object){
//					if (_isValidInst(key)){
//alap.cov.jsCoverage("bootstrap_3", "n/a","496");
//						this[key] = val;
//}
//alap.cov.jsCoverage("bootstrap_3", "n/a","497");
//				},this);
//			}
//			//assign needed types from class
//alap.cov.jsCoverage("bootstrap_3", "n/a","498");
//			this.vjo = base.vjo;
//alap.cov.jsCoverage("bootstrap_3", "n/a","499");
//			var t = this.vjo._type;
//alap.cov.jsCoverage("bootstrap_3", "n/a","500");
//			if (!isB && (t == 'itype' || t == 'atype' || t == 'mtype')) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","501");
//			    throw this.vjo._class + " cannot be instantiated";
//			}
//				
//alap.cov.jsCoverage("bootstrap_3", "n/a","502");
//			_processInners(this,base.vjo);
//			if (!base.__donotconstruct) {	
//				
//alap.cov.jsCoverage("bootstrap_3", "n/a","503");
//				if (!this.constructs) {
//					this.constructs = function () {};
//				}
//				
//alap.cov.jsCoverage("bootstrap_3", "n/a","504");
//				var fn,c=this.constructs, dconstruct = false;
//alap.cov.jsCoverage("bootstrap_3", "n/a","505");
//				if (this.base && this._getBase) {
//					fn = function(){};
//alap.cov.jsCoverage("bootstrap_3", "n/a","506");
//					fn.prototype = this._getBase();
//alap.cov.jsCoverage("bootstrap_3", "n/a","507");
//					dconstruct = fn.prototype._constructs || false;
//				}
//alap.cov.jsCoverage("bootstrap_3", "n/a","508");
//				var cstr = c.toString();
//				if (dconstruct && cstr.indexOf("this.base(")===-1 && 
//					cstr.indexOf("this.constructs")===-1  && this.base) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","510");
//					this.base();
//				}
//				
//alap.cov.jsCoverage("bootstrap_3", "n/a","511");
//				var rv = c.apply(this,arguments);
//				//this.base constructor can no longer be used. just add base methods.
//				if (fn) {//update base, so we're not pointing at shared prototype base
//					//var fn = function(){};
//alap.cov.jsCoverage("bootstrap_3", "n/a","512");
//					//fn.prototype = this._getBase();
//					this.base = new fn; 
//alap.cov.jsCoverage("bootstrap_3", "n/a","513");
//					this.base._parent = this;
//				}
//alap.cov.jsCoverage("bootstrap_3", "n/a","514");
//				if (rv) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","515");
//					return rv;
//				}
//			}
//			// jce: placeholder to fix lint error
//alap.cov.jsCoverage("bootstrap_3", "n/a","516");
//			return null;
//		};
//alap.cov.jsCoverage("bootstrap_3", "n/a","517");
//		base._name="base";
//		base.vjo = {_class:clz};
//		
//alap.cov.jsCoverage("bootstrap_3", "n/a","518");
//		return base;
//	}
//	
//	function _createClazz(typ) {
//		//Init class for the type
//alap.cov.jsCoverage("bootstrap_3", "n/a","519");
//		typ.clazz = new vjo.Class(typ.vjo._class, typ.vjo._type);
//		if (typ.prototype){
//alap.cov.jsCoverage("bootstrap_3", "n/a","520");
//		typ.prototype.getClass = _getClazz;
//}
//		else
//alap.cov.jsCoverage("bootstrap_3", "n/a","521");
//			typ.getClass = _getClazz;
//	}
//	
//	function _createInherits(derived,type) {
//	    /**
//	        type - Super class
//	        derived - Current class
//	    */
//alap.cov.jsCoverage("bootstrap_3", "n/a","522");
//		var ptype = type.prototype;
//alap.cov.jsCoverage("bootstrap_3", "n/a","523");
//		type.__donotconstruct = true;
//alap.cov.jsCoverage("bootstrap_3", "n/a","524");
//		var cls = new type(true);
//alap.cov.jsCoverage("bootstrap_3", "n/a","525");
//		delete type.__donotconstruct;
//		cls.constructs = null; //do not want to pull in super constructor
//alap.cov.jsCoverage("bootstrap_3", "n/a","526");
//		cls.constructor = derived;
//alap.cov.jsCoverage("bootstrap_3", "n/a","527");
//		//cls.getClass = tmp;
//		var baseRef = {};
//		cls.base = function () {
//			var cbase = this.base, ptype = type.prototype, gb = ptype._getBase, c = ptype.constructs; 
//alap.cov.jsCoverage("bootstrap_3", "n/a","528");
//			if (ptype.base) this.base = ptype.base;
//alap.cov.jsCoverage("bootstrap_3", "n/a","529");
//			var cstr = (c) ? c.toString() : "", b = (cstr.indexOf("this.base(")===-1 && cstr.indexOf("this.constructs")===-1);
//			if (gb && gb()._constructs && b) this.base(); 
//alap.cov.jsCoverage("bootstrap_3", "n/a","530");
//			if (ptype.constructs) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","531");
//				_fixScope(type,this);
//alap.cov.jsCoverage("bootstrap_3", "n/a","532");
//				ptype.constructs.apply(this,arguments);
//alap.cov.jsCoverage("bootstrap_3", "n/a","533");
//				_fixScope(derived,this);
//			}
//alap.cov.jsCoverage("bootstrap_3", "n/a","534");
//			this.base = cbase;
//		}
//		cls._getBase = function() {
//alap.cov.jsCoverage("bootstrap_3", "n/a","535");
//			return baseRef;
//		};
//		
//		
//		function createBaseRef(type,func,der) { //create base types
//			return function () { //debugger;
//alap.cov.jsCoverage("bootstrap_3", "n/a","536");
//				var scp = (this._parent) ? this._parent : this;
//				var cbase = scp.base; //keep base instance
//alap.cov.jsCoverage("bootstrap_3", "n/a","537");
//				scp.base = (type.prototype._getBase)? type.prototype._getBase() : null;
//				if (scp.base) scp.base._parent = scp; //keep toplevel scope
//alap.cov.jsCoverage("bootstrap_3", "n/a","538");
//				_fixScope(type,scp);
//alap.cov.jsCoverage("bootstrap_3", "n/a","539");
//				var rv = func.apply(scp,arguments);
//alap.cov.jsCoverage("bootstrap_3", "n/a","540");
//				_fixScope(der,scp);
//alap.cov.jsCoverage("bootstrap_3", "n/a","541");
//				scp.base = cbase;
//alap.cov.jsCoverage("bootstrap_3", "n/a","542");
//				return rv;
//			};
//		}
//
//alap.cov.jsCoverage("bootstrap_3", "n/a","543");
//		if (baseRef.toString != vjo.Object.prototype.toString) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","544");
//			baseRef.toString = createBaseRef(type,ptype.toString,derived);
//		}
//		//add protos methods/
//alap.cov.jsCoverage("bootstrap_3", "n/a","545");
//		for (var i in ptype) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","546");
//			var pt = ptype[i];
//			if (i==='constructs' && typeof pt === 'function') {
//alap.cov.jsCoverage("bootstrap_3", "n/a","547");
//				if (pt.length===0) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","548");
//					baseRef._constructs = true;
//				}
//			} else if (ptype == vjo.Object.prototype || _isValidInst(i)) {
//				if (typeof pt === 'function' && !pt.vjo) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","549");
//					baseRef[i] = createBaseRef(type,pt,derived);
//					
//alap.cov.jsCoverage("bootstrap_3", "n/a","550");
//					var fnStr = pt.toString();
//alap.cov.jsCoverage("bootstrap_3", "n/a","551");
//					if (fnStr.indexOf('this.base.'+i+'(')!=-1) {
//						cls[i] = (function (fn) { //create chained methods
//							return function () {
//alap.cov.jsCoverage("bootstrap_3", "n/a","552");
//								return fn.apply(this,arguments);
//							}
//alap.cov.jsCoverage("bootstrap_3", "n/a","553");
//						})(baseRef[i]);
//					} else if (!pt.vjo) {
//						cls[i] = (function (type,func,der) { //create chained methods
//							return function () {
//								_fixScope(type,this)
//alap.cov.jsCoverage("bootstrap_3", "n/a","554");
//								var rv = func.apply(this,arguments);
//alap.cov.jsCoverage("bootstrap_3", "n/a","555");
//								_fixScope(der,this);
//alap.cov.jsCoverage("bootstrap_3", "n/a","556");
//								return rv;
//alap.cov.jsCoverage("bootstrap_3", "n/a","557");
//								//return fn.apply(this,arguments);
//							}
//alap.cov.jsCoverage("bootstrap_3", "n/a","558");
//						})(type,pt,derived);
//					} else {
//alap.cov.jsCoverage("bootstrap_3", "n/a","559");
//						cls[i] = pt;
//					}
//				} else {//TODO add inners
//alap.cov.jsCoverage("bootstrap_3", "n/a","560");
//					if (pt && pt.vjo && pt.vjo._type && pt.vjo._isInner) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","561");
//						if (!derived.vjo._inners) {
//							derived.vjo._inners = {};
//						}
//alap.cov.jsCoverage("bootstrap_3", "n/a","562");
//						derived.vjo._inners[i] = pt;
//					}
//alap.cov.jsCoverage("bootstrap_3", "n/a","563");
//					cls[i] = pt;
//				}
//			}
//		}
//		
//alap.cov.jsCoverage("bootstrap_3", "n/a","564");
//		derived.prototype = cls;
//		derived.protos({
//			getClass : cls.getClass
//		})
//	}
//	
//	function _isVjoType(clz) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","565");
//		if (clz && clz.vjo && clz.vjo._type) {
//			if (typeof clz === 'function') return true;
//			else if (clz.vjo._type === 'mtype') return true
//		}
//alap.cov.jsCoverage("bootstrap_3", "n/a","566");
//		return false;
//	}
//	
//	function _createPkg (clz) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","567");
//		if (!clz) return null;
//		if (vjo._typeMap[clz]) //if have pkg, return
//			return vjo._typeMap[clz]
//alap.cov.jsCoverage("bootstrap_3", "n/a","568");
//		var names = clz.split('.'), len = names.length;
//		var pkg = vjo.global; //TODO: update with scope
//alap.cov.jsCoverage("bootstrap_3", "n/a","569");
//		for (var i=0;i<len-1 && pkg && names[i];i++){
//			pkg = (pkg[names[i]]) ? pkg[names[i]] : pkg[names[i]] = {};
//		}
//		vjo._typeMap[clz] = {pkg:pkg,className:(len>0)?names[len-1]:""}
//alap.cov.jsCoverage("bootstrap_3", "n/a","570");
//		return vjo._typeMap[clz];
//	}
//	
//	function _createEnum() {
//		//Enum created already!
//		if (typeof vjo.Enum == "function") {
//alap.cov.jsCoverage("bootstrap_3", "n/a","571");
//			return;
//		}
//alap.cov.jsCoverage("bootstrap_3", "n/a","572");
//		var nm = "vjo.Enum";
//		var baseEnum = vjo.atype(nm)
//		.props({
//			from : function () {
//alap.cov.jsCoverage("bootstrap_3", "n/a","573");
//				if (!arguments[0]) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","574");
//					throw "Invalid argument value: " + arguments[0];
//				}
//alap.cov.jsCoverage("bootstrap_3", "n/a","575");
//				var s = arguments[0];
//alap.cov.jsCoverage("bootstrap_3", "n/a","576");
//				if (arguments.length == 2) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","577");
//					s = arguments[1];
//alap.cov.jsCoverage("bootstrap_3", "n/a","578");
//					if (s) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","579");
//						var clz = arguments[0];
//						try {
//alap.cov.jsCoverage("bootstrap_3", "n/a","580");
//							var o = eval(arguments[0].getName());
//alap.cov.jsCoverage("bootstrap_3", "n/a","581");
//							if (o[s]) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","582");
//								return o[s];
//							}
//						} 
//						catch (a) {
//						}
//					}
//alap.cov.jsCoverage("bootstrap_3", "n/a","584");
//					throw "No enum const " + arguments[0].getName() + "." + s;
//				} else {
//alap.cov.jsCoverage("bootstrap_3", "n/a","585");
//					if (this[s]) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","586");
//						return this[s];
//					}
//				}
//alap.cov.jsCoverage("bootstrap_3", "n/a","587");
//				throw "No enum const " + s;
//			}
//		})
//		.protos({
//			_name : null,
//			_ord : -1,
//			name : function () {
//alap.cov.jsCoverage("bootstrap_3", "n/a","588");
//				return this._name;
//			},
//			ordinal : function () {
//alap.cov.jsCoverage("bootstrap_3", "n/a","589");
//				return this._ord;
//			},
//			compareTo : function (o) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","590");
//				if (o == null) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","591");
//					throw "compare to Etype value cannot be null";
//				}
//alap.cov.jsCoverage("bootstrap_3", "n/a","592");
//				return (this.ordinal() - o.ordinal());
//			},
//			equals : function (o) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","593");
//				return (this===o);
//			}	
//		})
//		.endType();	
//alap.cov.jsCoverage("bootstrap_3", "n/a","594");
//		reservedClz[nm] = true;
//alap.cov.jsCoverage("bootstrap_3", "n/a","595");
//		reservedInh[nm] = true;
//	}
//	
//	//Added to Object at runtime
//	function _getClazz() {
//alap.cov.jsCoverage("bootstrap_3", "n/a","596");
//		var n = this.vjo._class;
//alap.cov.jsCoverage("bootstrap_3", "n/a","597");
//		var idx = n.lastIndexOf('.');
//alap.cov.jsCoverage("bootstrap_3", "n/a","598");
//		if (idx != -1) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","599");
//			var clz = n.substring(idx+1);
//alap.cov.jsCoverage("bootstrap_3", "n/a","600");
//			if (this.vjo[clz]) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","601");
//				return this.vjo[clz].clazz;
//			}
//		} 
//		//Error case...
//alap.cov.jsCoverage("bootstrap_3", "n/a","602");
//		return null;
//alap.cov.jsCoverage("bootstrap_3", "n/a","603");
////		return this.vjo.clazz;
//	}
//	
//	function _updateInners(rootclz, clzname, inner) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","604");
//		if (inner && inner.vjo) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","605");
//			inner.vjo._class = clzname;
//alap.cov.jsCoverage("bootstrap_3", "n/a","606");
//			if (inner.clazz) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","607");
//				inner.clazz._name = clzname;
//			}
//alap.cov.jsCoverage("bootstrap_3", "n/a","608");
//			if (inner.vjo.s_inners) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","609");
//			var idx = clzname.lastIndexOf('.'), snm = clzname.substring(idx+1);
//alap.cov.jsCoverage("bootstrap_3", "n/a","610");
//			_createPkg(clzname).pkg[snm] = inner;
//			_forEach(inner.vjo.s_inners,function(val,key) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","611");
//			//vjo.sysout.println(key);
//				_forEach(inner.vjo,function(val,key){
//alap.cov.jsCoverage("bootstrap_3", "n/a","612");
//					if (!this[key]&&val&&val.vjo) this[key] = val;
//alap.cov.jsCoverage("bootstrap_3", "n/a","613");
//				},val.vjo);
//alap.cov.jsCoverage("bootstrap_3", "n/a","614");
//				if (val.vjo && val.vjo._init) ActiveInitMgr.addInner(rootclz,val.vjo._init);
//				_updateInners(rootclz,clzname+"."+key,val);	
//			});
//		}
//	}
//	}
//	
//	function _updateInnerEtypes(typeVjo) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","615");
//		if (!typeVjo._class) return;
//		
//alap.cov.jsCoverage("bootstrap_3", "n/a","616");
//		if (typeVjo.s_inners) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","617");
//			for (var k in typeVjo.s_inners) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","618");
//				if (typeVjo.s_inners[k].vjo._type == 'etype') {
//alap.cov.jsCoverage("bootstrap_3", "n/a","619");
//					for (var i=0;i<typeVjo.s_inners[k]._enums.length; i++) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","620");
//						typeVjo.s_inners[k]._enums[i].vjo = typeVjo.s_inners[k].vjo;
//alap.cov.jsCoverage("bootstrap_3", "n/a","621");
//						_updateInnerEtypes(typeVjo.s_inners[k]._enums[i].vjo);
//					}
//				}
//alap.cov.jsCoverage("bootstrap_3", "n/a","622");
//				_updateInnerEtypes(typeVjo.s_inners[k].vjo);
//			}
//		}
//alap.cov.jsCoverage("bootstrap_3", "n/a","623");
//		if (typeVjo._inners) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","624");
//			for (var k in typeVjo._inners) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","625");
//				if (typeVjo._inners[k].vjo._type == 'etype') {
//alap.cov.jsCoverage("bootstrap_3", "n/a","626");
//					if (!typeVjo._inners[k].vjo._class) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","627");
//						typeVjo._inners[k].clazz._name = typeVjo._inners[k].vjo._class = typeVjo._class + "." + k;
//					}
//alap.cov.jsCoverage("bootstrap_3", "n/a","628");
//					for (var i=0;i<typeVjo._inners[k]._enums.length; i++) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","629");
//						typeVjo._inners[k]._enums[i].vjo = typeVjo._inners[k].vjo;
//alap.cov.jsCoverage("bootstrap_3", "n/a","630");
//						_updateInnerEtypes(typeVjo._inners[k]._enums[i].vjo);
//					}
//				}
//alap.cov.jsCoverage("bootstrap_3", "n/a","631");
//				_updateInnerEtypes(typeVjo._inners[k].vjo);
//			}
//		}
//	}
//	
//	function _addInner(clz,inner,store,key) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","632");
//		if (!clz || !inner || !key) return false;
//alap.cov.jsCoverage("bootstrap_3", "n/a","633");
//		if (inner.vjo && inner.vjo._type && inner.vjo._isInner) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","634");
//		    if (!inner.vjo._class && clz.vjo._class) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","635");
//		    	var cn = inner.vjo._class = clz.vjo._class + "." + key;
//alap.cov.jsCoverage("bootstrap_3", "n/a","636");
//		    	if (inner.clazz) {
//					inner.clazz._name = cn;	//Update class info as well
//		    	}
//alap.cov.jsCoverage("bootstrap_3", "n/a","637");
//		    	_createPkg(cn).pkg[key] = inner;
//alap.cov.jsCoverage("bootstrap_3", "n/a","638");
//		    	//inner.vjo[key] = inner;
//		    }
//alap.cov.jsCoverage("bootstrap_3", "n/a","639");
//		    if(store) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","640");
//		        if (!clz.vjo[store]) {
//					clz.vjo[store] = {};
//			    }
//alap.cov.jsCoverage("bootstrap_3", "n/a","641");
//			    clz.vjo[store][key] = inner;
//			}
//alap.cov.jsCoverage("bootstrap_3", "n/a","642");
//			return true;
//		}
//alap.cov.jsCoverage("bootstrap_3", "n/a","643");
//		return false;
//	}
//	
//	function _isValidInst (pVal) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","644");
//		return !(reservedProto[pVal]===true);
//	}
//	
//	function _isValidClz(pVal) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","645");
//		return !(reservedClz[pVal]===true);
//	}
//	
//	function _isValidInh(pVal) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","646");
//		return !(reservedInh[pVal]===true);
//	}
//	function _forEach(object, block, context) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","647");
//		if (!object) return;
//alap.cov.jsCoverage("bootstrap_3", "n/a","648");
//		var name, i = 0, len = object.length;
//alap.cov.jsCoverage("bootstrap_3", "n/a","649");
//		if ( !vjo.isArray(object) ) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","650");
//			for ( name in object ) {
//				if ( block.call(context,object[name],name,object) === false ){
//alap.cov.jsCoverage("bootstrap_3", "n/a","651");
//					break;
//}
//			}
//		} else {
//alap.cov.jsCoverage("bootstrap_3", "n/a","652");
//			for (; i<len; i++) {
//				if (block.call(context,object[i],i,object)===false){
//alap.cov.jsCoverage("bootstrap_3", "n/a","653");
//						break;
//}
//			}
//		}
//alap.cov.jsCoverage("bootstrap_3", "n/a","654");
//		return object;
//	}
//	
//	function _processInners(context,basevjo) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","655");
//		var inners = (basevjo) ? basevjo._inners : null;
//alap.cov.jsCoverage("bootstrap_3", "n/a","656");
//		if (!context || !basevjo || !inners || inners.length==0) return;
//alap.cov.jsCoverage("bootstrap_3", "n/a","657");
//		for (var k in inners) {
//			context[k] = (function (t,type,nm) {
//				return function () {//debugger;
//alap.cov.jsCoverage("bootstrap_3", "n/a","658");
//					var s = type.prototype;//, c = s.constructs;
//alap.cov.jsCoverage("bootstrap_3", "n/a","659");
//					//s.constructs = null;
//alap.cov.jsCoverage("bootstrap_3", "n/a","660");
//					type.__donotconstruct=true;
//alap.cov.jsCoverage("bootstrap_3", "n/a","661");
//					var tp = new type();
//alap.cov.jsCoverage("bootstrap_3", "n/a","662");
//					delete type.__donotconstruct;
//					var _v = {};
//alap.cov.jsCoverage("bootstrap_3", "n/a","663");
//					vjo.extend(_v,basevjo);
//alap.cov.jsCoverage("bootstrap_3", "n/a","664");
//					delete _v._inners;
//					vjo.extend(_v,type.vjo); 
//alap.cov.jsCoverage("bootstrap_3", "n/a","665");
//					_v[nm] = type;
//alap.cov.jsCoverage("bootstrap_3", "n/a","666");
//					tp.vjo = _v;
//alap.cov.jsCoverage("bootstrap_3", "n/a","667");
//					tp.vjo.outer = t;
//alap.cov.jsCoverage("bootstrap_3", "n/a","668");
//					_processInners(tp,_v);
//alap.cov.jsCoverage("bootstrap_3", "n/a","669");
//					type.vjo._class = tp.vjo._class = t.vjo._class + "." + nm;
//alap.cov.jsCoverage("bootstrap_3", "n/a","670");
//					if (type.clazz && !type.clazz._name) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","671");
//						type.clazz._name = tp.vjo._class;
//					} else {
//alap.cov.jsCoverage("bootstrap_3", "n/a","672");
//						_createClazz(type);
//					}
//					
//alap.cov.jsCoverage("bootstrap_3", "n/a","673");
//					if (s.constructs) s.constructs.apply(tp,arguments);
//alap.cov.jsCoverage("bootstrap_3", "n/a","674");
//					return tp;
//				}
//alap.cov.jsCoverage("bootstrap_3", "n/a","675");
//			})(context,inners[k],k);
//alap.cov.jsCoverage("bootstrap_3", "n/a","676");
//			context[k].vjo = inners[k].vjo;
//alap.cov.jsCoverage("bootstrap_3", "n/a","677");
//			context[k].clazz = inners[k].clazz;
//		}
//	}
//	
//	function _copyNS(from,to) {
//		_forEach(from,function(val,key){
//alap.cov.jsCoverage("bootstrap_3", "n/a","678");
//			if (_isVjoType(val)) {
//				if (this[key] && this[key]!==val) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","679");
//					throw key + " is already defined in the current namespace";
//}
//alap.cov.jsCoverage("bootstrap_3", "n/a","680");
//				this[key] = val;
//			}
//alap.cov.jsCoverage("bootstrap_3", "n/a","681");
//		},to);
//	}
//	
//	function _isInnerClass (clz) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","682");
//		if (!clz) return true;
//		else if (clz.indexOf('.')==-1) return false;
//alap.cov.jsCoverage("bootstrap_3", "n/a","683");
//		var tp = clz;
//alap.cov.jsCoverage("bootstrap_3", "n/a","684");
//		while ((i = tp.lastIndexOf('.'))>0) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","685");
//			tp = tp.substring(0,i);
//			if (vjo._typeMap[tp]){
//alap.cov.jsCoverage("bootstrap_3", "n/a","686");
//				return true;
//}
//		}
//alap.cov.jsCoverage("bootstrap_3", "n/a","687");
//		return false;
//	}
//	function _isValidProp (pVal) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","688");
//		return !(reservedProp[pVal]===true);
//	}
//	function _fixScope(from,to) {
//alap.cov.jsCoverage("bootstrap_3", "n/a","689");
//		to.vjo = from.vjo;
//	}
//alap.cov.jsCoverage("bootstrap_3", "n/a","690");
//})();