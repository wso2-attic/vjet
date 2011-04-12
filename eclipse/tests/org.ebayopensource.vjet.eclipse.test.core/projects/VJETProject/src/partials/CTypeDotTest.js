vjo.ctype('partials.CTypeDotTest')
.props({
	sprop1 : 20, //< public int sprop1
	sprop2 : null, //< public partials.CTypeDotTest
	
	//>public void getValue()
	getValue : function() {
		var x = document.getElementsByName("HOME"); //<Document 
		alert(x.xmlEncoding);
	}
})
.protos({
	prop1 : 10, //< public int prop1
	prop2 : new String('Test'), //< private String
	
	//>public void StringReplace()
	StringReplace : function() {
		var str = "Visit VJET"; //< String
		document.write(str.replace(/VJET/, "VJO", null));
	},
	//>public constructs()
	constructs : function() {
		prop1 = 20;
	},
	//>public void AlertFunc()
	AlertFunc : function() {
		alert("Test");	
	},
	//>public void ArrayConcat()
	ArrayConcat : function() {
		var arr = new Array(3); //<Array
		arr[0] = "V4";
		arr[1] = "VJO";
		arr[3] = "VJET";
		var arr2 = new Array(3); //<Array
		arr2[0] = "Tech";
		arr2[1] = "JS";
		arr2[2] = "Authering";
		document.write(arr.concat(arr2));
	},
	//>public void ArraySort()
	ArraySort : function() {
		var arr = new Array(6); //<Array
		arr[0] = "Jani";
		arr[1] = "Hege";
		arr[2] = "Stale";
		arr[3] = "Kai Jim";
		arr[4] = "Borge";
		arr[5] = "Tove";
		document.write(arr + " new line ");
		document.write(arr.sort(null));
	},
	//>public void startTime()
	startTime : function() {
		var today = new Date(); //<Date
		var h = today.getHours();
		var m = today.getMinutes();
		var s = today.getSeconds();
		m = this.checkTime(m);
		s = this.checkTime(s);
		document.getElementsByName("txt").innerHTML = h+":"+m+":"+s;
	},
	//>public void checkTime(int i)
	checkTime : function(i) {
		if (i < 10) {
			i = "0" + i;
		}
		return i;
	},
	//>public int convert(String degree, int value)
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
	//>public void ArrayFunction()
	ArrayFunction : function() {
		var myarr=new Array(); //<Array
		myarr[Math.pow(2,32)-2]="hi";
		assertTrue(myarr[Math.pow(2,32)-2]=="hi");
		var myarr2=new Array(); //<Array
		myarr2[Math.pow(2,32)-2]='hi'; 
		assertTrue(myarr2.length);
		var myarr3=new Array(); //<Array
		myarr3[Math.pow(2,32)-3]='hi';
		assertTrue(myarr3[Math.pow(2,32)-3]);
    	var myarr4=new Array(); //<Array
    	myarr4[Math.pow(2,32)-3]='hi'; 
    	assertTrue(myarr4.length==(Math.pow(2,32)-2));
    	var myarr5=new Array(); //<Array
    	myarr5[Math.pow(2,31)-2]='hi'; 
    	assertTrue(myarr5[Math.pow(2,31)-2]=="hi");
    	var myarr6=new Array(); //<Array
    	myarr6[Math.pow(2,31)-2]='hi';             
    	assertTrue((Math.pow(2,31)-1)==myarr6.length);
	}
})
.endType();