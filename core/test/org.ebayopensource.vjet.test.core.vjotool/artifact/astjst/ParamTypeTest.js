vjo.ctype('astjst.ParamTypeTest<T,S>') //< public
.props({
    testStrInt:null, //< ParamTypeTest<String,Number> testStrInt
    testStrStr:null, //< ParamTypeTest<String,String> testStrStr
	//> public void main(String[] args)
	main:function(args){
	    this.testStrInt=new this("Abc",10);
	    this.testStrStr=new this("PQR","LMN");
	}
})
.protos({
	first:null, //< private T
	second:null, //< private S
	//> public constructs(T f,S s)
	constructs:function(f,s){
	    this.first=f;
	    this.second=s;
	}
})
.endType();