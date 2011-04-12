vjo.ctype('staticPropAdvisor.StaticPropAdvisorTest1')
.inherits('staticPropAdvisor.StaticPropAdvisorAType')
.satisfies('staticPropAdvisor.StaticPropAdvisorIType')
.mixin('staticPropAdvisor.StaticPropAdvisorMType') 
.props({
	sprop1 : 20,
	sprop2 : null,
	

	getValue : function() {
		var x = document.getElementsByName("HOME"); 
		alert(x.xmlEncoding);
		i
	},
	

	 main: function() {
			var x = new Array();
			alert("Hi");
		},
	
	prop1 : 10,
	prop2 : new String('Test'),
	

	StringReplace : function() {
		var str = "Visit VJET";
		document.write(str.replace(/VJET/, "VJO", null));
	},
	

	AlertFunc : function() {
		alert("Test");
	},
	

	ArrayConcat : function() {
		var arr = new Array(3);
		arr[0] = "V4";
		arr[1] = "VJO";
		arr[3] = "VJET";
		var arr2 = new Array(3);
		arr2[0] = "Tech";
		arr2[1] = "JS";
		arr2[2] = "Authering";
		document.write(arr.concat(arr2));
	},
	

	ArraySort : function() {
		var arr = new Array(6); 
		arr[0] = "Jani";
		arr[1] = "Hege";
		arr[2] = "Stale";
		arr[3] = "Kai Jim";
		arr[4] = "Borge";
		arr[5] = "Tove";
		document.write(arr + " new line ");
		document.write(arr.sort(null));
	},
	

	startTime : function() {
		var today = new Date();
		var h = today.getHours();
		var m = today.getMinutes();
		var s = today.getSeconds();
		m = this.checkTime(m);
		s = this.checkTime(s);
		document.getElementsByName("txt").innerHTML = h+":"+m+":"+s;
	},
	

	checkTime : function(i) {
		if (i < 10) {
			i = "0" + i;
		}
		return i;
	},
	

	convert : function(degree, value) {
		var returnVal = 0.0;
		if(degree == "C"){
			returnVal = value * 9 / 5 + 32;
			returnVal = Math.round(returnVal);
		} else {
			returnVal = (value -32) * 5 / 9;
			returnVal = Math.round(returnVal);
		}
		return returnVal;
	},
	

	ArrayFunction : function() {
		var myarr=new Array();
		myarr[Math.pow(2,32)-2]="hi";
		assertTrue(myarr[Math.pow(2,32)-2]=="hi");
		var myarr2=new Array();
		myarr2[Math.pow(2,32)-2]='hi'; 
		assertTrue(myarr2.length);
		var myarr3=new Array(); 
		myarr3[Math.pow(2,32)-3]='hi';
		assertTrue(myarr3[Math.pow(2,32)-3]);
		var myarr4=new Array();
		myarr4[Math.pow(2,32)-3]='hi'; 
		assertTrue(myarr4.length==(Math.pow(2,32)-2));
		var myarr5=new Array();
		myarr5[Math.pow(2,31)-2]='hi'; 
		assertTrue(myarr5[Math.pow(2,31)-2]=="hi");
		var myarr6=new Array(); 
		myarr6[Math.pow(2,31)-2]='hi';             
		assertTrue((Math.pow(2,31)-1)==myarr6.length);
	}

})
.protos({
	property1 : "TestMe",
	property2 :100, 
	property3 : "TestMe",
	
	testFunction : function(){ 
		this.testFunction();
		var xx = null; 
	},
	
	piFunction1 :  function (arg1) {
	  
	},
  
   piFunction2 :  function (arg1, arg2) {
   
   }
})
.inits(function(){
	this.vj$.DebugType.sprop1 = 10;
	this.vj$.DebugType.sprop2 = new StaticPropAdvisorTest();
})
.endType();