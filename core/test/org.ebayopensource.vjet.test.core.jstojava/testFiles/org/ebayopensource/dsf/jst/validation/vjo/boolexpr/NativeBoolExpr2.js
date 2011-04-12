vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.boolexpr.NativeBoolExpr2') //< public
.props({
	
	numProperty: 10, //<int
	
	//> public void main(String... arguments)
	main:function(){
		var num = this.numProperty;
		var obj = new this.vj$.NativeBoolExpr2();//<NativeBoolExpr2
		var mix = obj || num;
	},
	
	//> public void testAttrRemoveCallback(MouseEvent e)
    testAttrRemoveCallback:function(e){
        var elem=e.target || window.event.srcElement; 
    }
	

})
.endType();

