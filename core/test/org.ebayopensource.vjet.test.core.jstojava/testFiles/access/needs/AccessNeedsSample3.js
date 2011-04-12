vjo.ctype('access.needs.AccessNeedsSample3')
.props({
	//> public void main(String[] args)
	main : function (args) {
		document.writeln('main() called');
		this.vj$.BaseType.doA();
		
		//doA() can alternatively be called like this
		access.needs.BaseType.doA();
	}
})
.endType();