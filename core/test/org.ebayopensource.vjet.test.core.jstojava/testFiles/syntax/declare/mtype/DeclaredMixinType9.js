vjo.mtype("syntax.declare.mtype.DeclaredMixinType9")
.props({
	//> public int
	mixinId : 123456
	})
	.protos({
	//> public void showClass()
	showClass : function () {
	alert(this.getClass().toString());
	}
	})
	.endType();