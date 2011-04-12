vjo.ctype('innertypes.CTypeWithMTypeTest') //< public
.props({
	staticOuterProp:"Test", //< String

	//> public String outerStaticFunc()
	outerStaticFunc:function(){
		vjo.sysout.println(this.staticOuterProp);
	    return "";
	},
	
	innerStaticMixin : <<0>>vjo.<<1>>mtype()
	.<<2>>props({
		msStaticprop1 : 10,//<int
		msStaticprop2 : "Test",//<String
		msStaticprop3 : "Test",//<String
		msStaticFunc1 :  function () { //<public void msStaticFunc1()
			<<3>>var v = <<4>>this.<<5>>msStaticprop1;//<int
			this.<<6>>msStaticprop2 = this.vj$.<<7>>CTypeWithMType.<<8>>outerStaticFunc().<<9>>big();//<String
			this.msStaticprop3 = this.vj$.CTypeWithMType.staticOuterProp.<<10>>big();//<String
			var obj = <<11>>new <<12>>this.vj$.CTypeWithMType(); //< CTypeWithMType
			<<13>>obj.<<14>>outerFunc1().<<15>>big();
		}
	})
	.protos({
		mStaticprop1 : 10,//<int
		mStaticprop2 : "Test",//<String
		mStaticFunc1 :  function () { //<public void mStaticFunc1()
			<<16>>this.<<17>>mStaticprop2 = <<18>>this.vj$.<<19>>innerStaticMixin.<<20>>msStaticprop2.<<21>>big();
			this.mStaticprop2 = this.vj$.CTypeWithMType.<<22>>staticOuterProp.<<23>>big();
			var str1 = this.mStaticprop2.<<24>>big();
			var str2 = this.vj$.CTypeWithMType.outerStaticFunc().<<25>>big();
			var obj = new this.vj$.CTypeWithMType(); //< CTypeWithMType
			<<26>>obj.<<27>>outerFunc1().<<28>>big();
		}
	})
	.<<29>>endType()
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
		<<30>>var obj = <<31>>new <<32>>this.<<33>>vj$.<<34>>CTypeWithMType();//<CTypeWithMType
		var innerObj = new <<35>>obj.<<36>>InnerCType();//<InnerCType
		<<37>>innerObj.<<38>>innerCtypeFunc();
		var str = this.vj$.type.<<68>>msFunc1();
	},
	
	innerMixin : vjo.<<39>>mtype()
	.props({
		msprop1 : 10,//<int
		msprop2 : "test",//<String
		msprop3 : "test",//<String
		msFunc1 :  function () { //<public void msStaticFunc1()
			<<40>>var v = <<41>>this.<<42>>msprop1;//<int
			this.<<43>>msprop2 = this.vj$.<<44>>CTypeWithMType.<<45>>outerStaticFunc().<<46>>big();//<String
			this.msprop3 = this.vj$.CTypeWithMType.staticOuterProp.<<47>>big();//<String
			var obj = new this.vj$.CTypeWithMType(); //< CTypeWithMType
			obj.<<48>>outerFunc1().<<49>>big();
		}
	})
	.protos({
		mprop1 : 10,//<int
		mprop2 : "Test",//<String
		mFunc1 :  function () { //<public void mStaticFunc1()
			var str = <<50>>this.<<51>>vj$.<<52>>innerMixin.<<53>>msprop2.<<54>>big();
			this.<<55>>mprop2 = this.<<56>>mprop2.<<57>>big();
			var str2 = this.vj$.CTypeWithMType.<<58>>outerStaticFunc().<<59>>big();
			var obj = new this.vj$.CTypeWithMType(); //< CTypeWithMType
			obj.<<60>>outerFunc1().<<61>>big();
		}
	})
	.endType(),
	
	InnerCType : vjo.<<62>>ctype()
	.<<63>>mixin('inner<<64>>types.CTypeWithMType.<<65>>innerStaticMixin')
	.<<66>>mixin('inner<<67>>types.CTypeWithMType.innerMixin')
	.protos({
		innerCtypeFunc : function(){ //<public void innerCtypeFunc()
			
		}
	})
	.endType()
})
.endType();