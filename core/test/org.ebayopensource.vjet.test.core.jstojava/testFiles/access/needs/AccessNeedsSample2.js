vjo.ctype('access.needs.AccessNeedsSample2')
.needs('access.needs.UnExist')
.props({
	//> public void main(String[] args)
	main : function (args) {
		document.writeln('main() called');
		this.vj$.UnExist.doA();
		
		//doA() can alternatively be called like this
		access.needs.UnExist.doA();
	}
})
.endType();