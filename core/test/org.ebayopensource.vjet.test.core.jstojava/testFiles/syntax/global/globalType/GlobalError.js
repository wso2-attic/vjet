vjo.ctype('syntax.global.globalType.GlobalError') //< public
.props({
  
})
.protos({
//>public void foo() 
foo : function(){
	alert("dd");
	alert(arguments);
	alert(document);
	alert(form);
	alert(dec);
	alert(new Error());
	alert(new EvalError());
	alert(new RangeError());
	alert(new ReferenceError());
	alert(new SyntaxError());
	alert(new TypeError());
	alert(new URIError());
	alert(new DOMException());
	alert(new EventException());
}
})
.endType();