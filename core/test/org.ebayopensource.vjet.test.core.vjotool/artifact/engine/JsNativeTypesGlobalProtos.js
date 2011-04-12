vjo.ctype('engine.JsNativeTypesGlobalProtos') //< public
.protos({
    ArgumentsOuterVar : new Arguments().prototype,
	ArrayOuterVar : new Array().prototype,
	BooleanOuterVar : new Boolean().prototype,
	DateOuterVar : new Date().prototype,
	EnumeratorOuterVar : new Enumerator(new String()).prototype,
	ErrorOuterVar : new Error().prototype,
	EvalErrorOuterVar : new EvalError().prototype,
	FunctionOuterVar : new Function(new String()).prototype,
	NumberOuterVar : new Number().prototype,
	RangeErrorOuterVar : new RangeError().prototype,
	ReferenceErrorOuterVar : new ReferenceError().prototype,
	RegExpOuterVar : new RegExp().prototype,
	StringOuterVar : new String().prototype,
	SyntaxErrorOuterVar : new SyntaxError().prototype,
	TypeErrorOuterVar : new TypeError().prototype,
	URIErrorOuterVar : new URIError().prototype,

  //>public void func1() 
  func1 : function(){
  	 var ArgumentsVar = new Arguments(); //< Arguments
  	 ArgumentsVar.toString();
  	 var ArgumentsPropVar = ArgumentsVar.callee;
  	 
  	 var ArrayVar = new Array(1,2,3); //< Array
  	 ArrayVar.join("");
  	 var ArrayPropVar = ArrayVar.index;
  	 
  	 var BooleanVar = new Boolean(10); //< Boolean
  	 BooleanVar.toSource();
  	 var BooleanPropVar = BooleanVar.prototype;
  	 
  	 var DateVar = new Date();//< Date
  	 DateVar.toString();
  	 var DatePropVar = DateVar.prototype;
  	 
  	 var EnumeratorVar = new Enumerator(new Object());//<Enumerator
  	 EnumeratorVar.toString();
  	 var EnumeratorPropVar = EnumeratorVar.prototype;
  	 
  	 var ErrorVar = new Error("Test It"); //< Error
  	 ErrorVar.toString();
  	 var ErrorPropVar = ErrorVar.prototype;
  	 
	 var EvalErrorVar = new EvalError("Test It"); //< EvalError
	 EvalErrorVar.toString();
	 var EvalErrorPropVar = EvalErrorVar.prototype;
	 
	 var FunctionVar = new Function("Test", "Test", "Test", "Test"); //< Function
	 FunctionVar.toString();
	 var FunctionPropVar = FunctionVar.prototype;
	 
	 var NumberVar = new Number(24.5); //< Number
	 NumberVar.toString();
	 var NumberPropVar = NumberVar.prototype;
	 
	 var RangeErrorVar = new RangeError("Test It"); //< RangeError
	 RangeErrorVar.toString();
	 var RangeErrorPropVar = RangeErrorVar.prototype;
	 
	 var ReferenceErrorVar = new ReferenceError("Test It"); //< ReferenceError
	 ReferenceErrorVar.toString();
	 var ReferenceErrorPropVar = ReferenceErrorVar.prototype;
	 
	 var RegExpVar = new RegExp("Test It","Test it"); //< RegExp
	 RegExpVar.toString();
	 var RegExpPropVar = RegExpVar.prototype;
	 
	 var StringVar = new String("Test It"); //< String
	 StringVar.toString();
	 var StringPropVar = StringVar.prototype;
	 
	 var SyntaxErrorVar = new SyntaxError("Test It"); //< SyntaxError
	 SyntaxErrorVar.toString();
	 var SyntaxErrorPropVar = SyntaxErrorVar.prototype;
	 
	 var TypeErrorVar = new TypeError("Test It"); //< TypeError
	 TypeErrorVar.toString();
	 var TypeErrorPropVar = TypeErrorVar.prototype;
	 
	 var URIErrorVar = new URIError("Test It"); //< URIError
	 URIErrorVar.toString();
	 var URIErrorPropVar = URIErrorVar.prototype;
  }
})
.endType();