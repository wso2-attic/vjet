vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug8028')
.props({
	
	staticOuterProp:"Test",//<String
	
	//> public String outerStaticFunc()
	outerStaticFunc:function(){
		vjo.sysout.println(this.staticOuterProp);
		return "";
	},
	
	innerStaticMixin : vjo.mtype().props({
		msStaticProp1 : 10, //<int
		msStaticProp2 : "Test" //<String
	}).endType(),
	
	InnerCType: vjo.ctype().mixin('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug8028.innerStaticMixin').protos({
		innerCtypeFunc: function(){//<public void innerCtypeFunc()
		}
	}).endType()
})
.endType();