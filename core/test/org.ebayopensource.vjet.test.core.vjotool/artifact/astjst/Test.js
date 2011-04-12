vjo.ctype('astjst.Test') //> public
.props({
	Arr : new Array(), //< Array
	num : undefined, //< Number
	test : undefined, //< Test
	
	//>public void method1(int a)	
	//>public void method1(String a)
	 method1 : function(a) { 
		 var i = 0; //<Number
		 var arr = ["hi", 3, new Date()];  //<Array
		 
		 for (; i < arr.length; i ++) { 
		 	 alert(arr[i]); 
		}
		 
		var arr1 = new Array(3); //< Test[]
		var a1 = new this.vj$.Test(); //< Test
		var a2 = new this.vj$.Test(); //< Test
		var a3 = new this.vj$.Test(); //< Test
		arr1 = [a1,a2,a3];
		
		for(var j =0; j<arr1.length; j++){
			arr1[j].func1();
		}
		
		while(i<10){
			
		}
		
		if(true){
			
		}
		
		this.test = new this.vj$.Test();
		
		var obj = vjo.Object.clazz; //<<
		
	}	
	
})
.protos({
	//>public constructs()
	constructs : function(){
		this.vj$.sysout.println("Hello");
	},
	
	//>public void func1()
	func1 : function(){
		var localType = null; //< Object
		localType = vjo.ctype()
					.props({
						m_static : 1
					})
					.protos({
						m_instance : 2
					})
					.endType();
		var obj = new localType();
		
		var anonType = vjo.make(this, this.vj$.Test) 
			.protos({
				//> public void getAnonTypeProp()
				 getAnonTypeProp : function () {
				 }
			})
			.endType(); //<<
		anonType.getAnonTypeProp();
	},
	
	//> public void getAnonTypeProp()
	getAnonTypeProp : function(){},
	
	prop1 : 10, //<public int
	
	prop2 
})
.endType();