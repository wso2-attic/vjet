vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.BugNestedWith')
.props({
	main: function(){
		var t = new this.vj$.BugNestedWith();//<BugNestedWith
		var conflict = 'bad';
		with(t){
			with(t){
				alert(conflict);
				alert(peace);
			}
		}
	}
})
.protos({
	conflict: 100, //<public int
	peace: 'peace'
})
.endType();