vjo.mtype('partials.MTypeTemplateTest') \\< public
.props({
	sprop1 : 20, //< public int sprop1
	sprop2 : null, //< public partials.CTypeDotTest
	
	//>public void getValue()
	getValue : function() {
		var x = document.getElementsByName("HOME"); //<Document 
		alert(x.xmlEncoding);
		<<Correct Position>>
	}
	
})
.protos({
	prop1 : 10, //< public int prop1
	prop2 : new String('Test'), //< private String
	
	//>public void StringReplace()
	StringReplace : function() {
		var str = "Visit VJET"; //< String
		document.write(str.replace(/VJET/, "VJO", null));
		<<Correct Position>>
	},
	
	
	//>public constructs()
	constructs : function() {
		prop1 = 20;
		<<Correct Position>>
	},
	
	
	//>public void AlertFunc()
	AlertFunc : function() {
		alert("Test");
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
		for(var index=0; index<arr.length; index++) {
			<<Correct Position>>
		}
	},
	
	
	//>public void startTime()
	startTime : function() {<<Correct Position>>
		var today = new Date(); //<Date
		var h = today.getHours();
		var m = today.getMinutes();
		var s = today.getSeconds();
		m = this.checkTime(m);
		s = this.checkTime(s);
		this.checkTime();
		while (true) {
			<<Correct Position>>
			if (s > 20){
				<<Correct Position>>
				for(var ind=0; ind < s; ind++) {
					<<Correct Position>>
				}
			}
		}
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
			<<Correct Position>>
			returnVal = value * 9 / 5 + 32;
			returnVal = Math.round(returnVal);
		} else {
			returnVal = (value -32) * 5 / 9;
			<<Correct Position>>
			returnVal = Math.round(returnVal);
		}
		return returnVal;
	}
	
})
.endType();