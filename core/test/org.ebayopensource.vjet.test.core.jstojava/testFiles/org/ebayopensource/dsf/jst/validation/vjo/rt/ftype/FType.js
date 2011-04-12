vjo.ftype("org.ebayopensource.dsf.jst.validation.vjo.rt.ftype.FType")
.props({
	_invoke_: function(d){//< int _invoke_(Date)
		this.refs ++; //should result in an error as undefined field
		return 0;
	},
	
	refs: 0 //< public int
	,
	
	ref: function(){ //< public void ref()
		this.refs ++;
	}
})
.endType();