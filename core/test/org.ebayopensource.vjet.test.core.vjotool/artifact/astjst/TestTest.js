vjo.ctype('astjst.TestTest') //< public
//> needs astjst.OTest
.protos({
	outerFunc1 : function(){ //<public String outerFunc1()
	    var v = new this.vj$.InnerType(); //< TestTest.InnerType
	    v.innerFunc1();
	    return "";
	},
	
	InnerType : vjo.ctype()
	.protos({
	        //>public void innerFunc1() 
	        innerFunc1 : function(){
	        }
	})
	.endType(),
	
	//> public boolean showShapeDetails(OTest.Point point)
	showShapeDetails:function(point){
		alert(point.x + ' X ' + point.y);
		return false;
	},
	
	//> public boolean plot(OTest.createLine fn);
	plot : function(fn) {
		fn({x:1,y:2}, {x:10,y:20});
		return false;
	},
	
	//> public OTest.createLine
	createLine: function (p1, p2) {
	},
	
	//>public String foo(String a)
    //>public String foo(int a)
    foo : function (a) {
            return a;
    },

    invoke : function() {
            var c = this.foo("string").length;
    },
    
	staticFunc1 : function(s1, s2){ //< public String staticFunc1(String, String)
		vjo.sysout.println("object identity is preserved");
		{
		        if (true){}
		}
		return "hi";
	}

})
.props({
	//>public void func1()
	func1:function(){
		vjo.sysout.println("hi");
	},
	
	//> public String toString(boolean val)
	//> public String toString(Boolean val)
    toString : function (val) {
            var b;
            if (typeof val == 'boolean') {
                    b = val;
            } else {
                    b = val.valueOf(); 
            }
            return b ? "true" : "false";
    }
})
.endType();