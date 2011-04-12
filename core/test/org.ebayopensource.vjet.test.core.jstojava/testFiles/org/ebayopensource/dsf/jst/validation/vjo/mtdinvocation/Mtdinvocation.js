vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.mtdinvocation.Mtdinvocation')
.protos({
	D: 1, //<public int
	c:function(){//<public void c()
		this.b();
	},

	//> public void main(String[] args)
	main: function(args) {
		var x = 10; //<Number
		var y = 20; //<Number
		var d = new Date(); //<Date
		var z = x + y;
		vjo.sysout.println("hello2");
	},

	//> public boolean doIt(String msg)
	doIt:function(msg){
		alert(msg); 
		return false;
	},

	doIt3:function(){
		this.b;	
	}	
})
.props({
	b:function(){//<public void b()
		var c = D;		
		var d = new Date();
		var msg = "aaa";
		alert(msg);
		var x = new Date(); //<Date
		//  window.open(url,windowName,features,replace);
		
		var address= "test"; 
		var e = this.c;
		
		var sss = "sth";//<String
		sss = sss.toString();
		//sss.equals("sth"); can't be found, as equals belongs to vjo.Object instead of Object'
		
		var innerFunc = this.b;//<Function
		innerFunc();
		innerFunc.call(this);
		
		this.b();
	},

	c:function() {
		this.b();		
	}
})
.inits(function(){})
.endType();