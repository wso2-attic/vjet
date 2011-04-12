vjo.ctype('BugJsFiles.GenericCtypeExtn') //<public
.inherits('BugJsFiles.GenericCtype') 
.props({
	//>public void main(String...) 
	main : function(){
		var v = new this.vj$.GenericCtypeExtn();//<GenericCtypeExtn
		v.compute("TestMe");
		var genericCtype = new this.vj$.GenericCtype();//<GenericCtype
		genericCtype.compute("test");
		this.vj$.GenericCtype.staticProp.big();
		var str = this.vj$.GenericCtype.staticProp.big();//<String
	}
})
.protos({
	constructs:function(){ //<public constructs()	
		var v1 = new this.vj$.GenericCtypeExtn();//<GenericCtypeExtn
		v1.compute("TestMe");
		var genericCtype1 = new this.vj$.GenericCtype();//<GenericCtype
		genericCtype1.compute("test");
		var str1 = this.vj$.GenericCtype.staticProp.big();//<String
		this.vj$.GenericCtype.staticProp.big();
	}
})
.endType();
