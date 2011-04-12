vjo.ctype('innertypes.CTypeWithMType') //< public
.props({
	staticOuterProp:"Test", //< String

	//> public String outerStaticFunc()
	outerStaticFunc:function(){
		vjo.sysout.println(this.staticOuterProp);
	    return "";
	},
	
	innerStaticMixin : vjo.mtype()
	.props({
		msStaticprop1 : 10,//<int
		msStaticprop2 : "Test",//<String
		msStaticprop3 : "Test",//<String
		msStaticFunc1 :  function () { //<public void msStaticFunc1()
			var v = this.msStaticprop1;//<int
			this.msStaticprop2 = this.vj$.CTypeWithMType.outerStaticFunc().big();//<String
			this.msStaticprop3 = this.vj$.CTypeWithMType.staticOuterProp.big();//<String
			var obj = new this.vj$.CTypeWithMType(); //< CTypeWithMType
			obj.outerFunc1().big();
		}
	})
	.protos({
		mStaticprop1 : 10,//<int
		mStaticprop2 : "Test",//<String
		mStaticFunc1 :  function () { //<public void mStaticFunc1()
			this.mStaticprop2 = this.vj$.innerStaticMixin.msStaticprop2.big();
			this.mStaticprop2 = this.vj$.CTypeWithMType.staticOuterProp.big();
			var str1 = this.mStaticprop2.big();
			var str2 = this.vj$.CTypeWithMType.outerStaticFunc().big();
			var obj = new this.vj$.CTypeWithMType(); //< CTypeWithMType
			obj.outerFunc1().big();
		}
	})
	.endType()
})
.protos({
	outerProp:"test", //< String
	//> public String outerFunc1()
	outerFunc1:function(){
		vjo.sysout.println(this.outerProp);
	    return "";
	},
	//> public void outerFunc2()
	outerFunc2:function(){
		var obj = new this.vj$.CTypeWithMType();//<CTypeWithMType
		var innerObj = new obj.InnerCType();//<InnerCType
		innerObj.innerCtypeFunc();
		var str = this.vj$.type.msFunc1();
	},
	
	innerMixin : vjo.mtype()
	.props({
		msprop1 : 10,//<int
		msprop2 : "test",//<String
		msprop3 : "test",//<String
		msFunc1 :  function () { //<public void msStaticFunc1()
			var v = this.msprop1;//<int
			this.msprop2 = this.vj$.CTypeWithMType.outerStaticFunc().big();//<String
			this.msprop3 = this.vj$.CTypeWithMType.staticOuterProp.big();//<String
			var obj = new this.vj$.CTypeWithMType(); //< CTypeWithMType
			obj.outerFunc1().big();
		}
	})
	.protos({
		mprop1 : 10,//<int
		mprop2 : "Test",//<String
		mFunc1 :  function () { //<public void mStaticFunc1()
			var str = this.vj$.innerMixin.msprop2.big();
			this.mprop2 = this.mprop2.big();
			var str2 = this.vj$.CTypeWithMType.outerStaticFunc().big();
			var obj = new this.vj$.CTypeWithMType(); //< CTypeWithMType
			obj.outerFunc1().big();
		}
	})
	.endType(),
	
	InnerCType : vjo.ctype()
	.mixin('innertypes.CTypeWithMType.innerStaticMixin')
	.mixin('innertypes.CTypeWithMType.innerMixin')
	.protos({
		innerCtypeFunc : function(){ //<public void innerCtypeFunc()
			
		}
	})
	.endType()
})
.endType();
