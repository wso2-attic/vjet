vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.BugGenerics<T extends BugGenericsSimpleT>")
.needs("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.BugGenericsSimpleT")
.inherits("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.BugGenericsBase<T>")
.props({
	main: function(){
		var me = new this.vj$.BugGenerics();//<BugGenerics<BugGenericsSimpleT>
		var t = new this.vj$.BugGenericsSimpleT();//<BugGenericsSimpleT
		var t2 = me.bar(t);//<BugGenericsSimpleT
		me.shout(t); //yet supported
		me.shout(this);
		
		var meBad = new this.vj$.BugGenerics();//<BugGenerics<BugGenericsSimpleT>
		t2.doIt();
	}
})
.protos({

	//>public T bar(T)
	bar: function(t){
		return t;
	}
	
}).endType();