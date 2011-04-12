vjo.ctype('access.needs.AccessNeedsSample1')
.needs('access.needs.BaseType')
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