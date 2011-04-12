vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.javaone.MyJsType")
.protos({
 	m_index: 0, //<int
 	//>public void constructs(int)
 	constructs: function(index) {
 		this.m_index = index;
 	},
 	//>public void doit()
 	doit: function() {
 		alert(this.m_index);
 	}
})
.props({
	s_counter: 0, //<int
	//>public void update(boolean)
	update: function(increament) {
		alert(increament? this.s_counter++ : this.s_counter--);
	}
})
.endType();