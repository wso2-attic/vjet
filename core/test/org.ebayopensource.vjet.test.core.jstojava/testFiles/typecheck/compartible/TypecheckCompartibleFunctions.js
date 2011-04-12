vjo.ctype('typecheck.compartible.TypecheckCompartibleFunctions') //< public
.protos({
	
    //> void main()
    main:function(){
		//simple function definition, no overloading, no var length
        var f;//< Number function(int)
        f = this.foo; //this assignment should be good, signature exactly matched
        f = this.f1;  //this assignment should be good, RHS's return type is stricter than the LHS, and param types are looser
        f = this.f2;  //this assignment should be good, RHS's is overloaded with one of the signature exactly matched
        f = this.bar; //this assignment should be bad, RHS's param type isn't assignable from LHS's expecting argument
        
        //more complicated function definitions, with explicit overloading
        //> Number function(int)
        //> int function(String)
        var fo;
        fo = this.foo; //this assignment should be bad, RHS's only partially satisfies the LHS's signatures
        
        //> int function(String)
        //> Number function(int)
        var fo2;
        fo = fo2; //this assignment should be good, RHS matching all signatures defined by LHS only in different order
        
        //> int function(int)
        //> int function(String)
        var fo3;
        fo = fo3; //this assignment should be good, RHS matching all signatures defined by LHS only using more restrict return type and less restrict param types
        
        //against optional parameters
        //> void function(String?, int?)
        var fun;
        var fun1;//< void function(String, int)
        fun = fun1; //this assignment should be bad, LHS's implicit overloading signature wasn't satisfied
        
        //against variable lengthed parameters
        //> void function(int...)
        var fun2;
        //> void function(int)
        var fun3;
        fun2 = fun3; //this assignment should be bad, LHS's has the last parameter variable lengthed
        
        
        var c1 = this.corner1;//<void function(int, String...)
        var c2 = this.corner2;//<void function(int, String, String...)
        //this line should cause problem as c1(0) isn't satisified
        c1 = c2;
        //this line should not cause problem as c2(0), c2(0, ""), c2(0, ""...) all satisfied by c1
        c2 = c1;
        var c3 = function(i){//<int function(int)
        	return 0;
        };
        //both of the following 2 lines shouldn't cause problems as
        //c3(0, ""), c3(0, "", ""...) works exactly the same as c3(0)
        //also, though the return type doesn't match, but c1, c2 returns void, therefore what c3 returns doesn't affect the semantics
        c1 = c3;
        c2 = c3;
        
        
        var empty = function(){//<void empty()
        	
        };
        var varLength = function(p){//<void varLength(String...)
        	
        }
        //should be ok
        empty = varLength;
    },
    
	foo: function(i){//<Number foo(int)
		return 0;
	},
	
	f1: function(i){//<int f1(Number)
		return 0;
	},
	
	//>Number f2(String)
	//>Number f2(int)
	f2: function(x){
		return 0;
	},
	
	bar: function(s){//<Number bar(String)
		return 0;
	},
	
	corner1: function(){//<void corner1(int, String...)
		
	},
	
	corner2: function(){//<void corner2(int, String, String...)
		
	}
})
.endType();