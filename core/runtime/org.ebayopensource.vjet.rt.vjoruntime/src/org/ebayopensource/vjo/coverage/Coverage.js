//jsCoverage.getTypesCount = function(){
//	var count = 0;	
//	for(singleType in jsCoverage.types){
//		count++;	
//	}
//	return count;
//};
//
//jsCoverage.coverageInfo.getLength = function(){
//	var count = 0;	
//	for(coverageType in this.types){
//		count++;	
//	}
//	return count;
//};
//function createCoverageType(obj) {
//	if(typeof(jsCoverage.coverageInfo.types[obj.typeName]) == "undefined"){
//		var jsonType = new coverageType("");
//		for (var key in obj) {
//			jsonType[key] = obj[key];
//		}
//		
//		var functions = jsonType.typeFunctions;
//		for(var func in functions){
//			var newFunc = new aFunction("");
//			var oldFunc = jsonType.typeFunctions[func];
//			for(var key in oldFunc){
//				newFunc[key]=oldFunc[key];
//			}
//			jsonType.typeFunctions[func]=newFunc;
//		}
//		return jsonType;
//	}
//}
//
//function coverageType(name){
//	this.typeName = name;	
//	this.typeFunctions = [];	
//	this.currentLineCoverage = 0;
//	this.totalLines = 0;	
//	
//	this.hasFunction = function(functionName){
//		for(i in this.typeFunctions){
//			if(this.typeFunctions[i].functionName === functionName){
//				return true;
//			}
//		}
//		return false;	
//	};	
//	
//	this.getFunction = function(functionName){
//		for(i in this.typeFunctions){
//			if(this.typeFunctions[i].functionName === functionName){
//				return this.typeFunctions[i];
//			}
//		}
//		return null;
//	};
//	
//	this.getTypeLineCount = function(){
//		this.totalLines = 0;		
//		for(func in this.typeFunctions){
//			var test = this.typeFunctions[func];
//			this.totalLines += this.typeFunctions[func].totalLines;		
//		}
//		return this.totalLines;	
//	};
//	
//	this.getTypeCoverageLineCount = function(){
//		this.currentLineCoverage = 0;		
//		for(func in this.typeFunctions){
//			var test = this.typeFunctions[func];
//			this.currentLineCoverage += this.typeFunctions[func].currentLineCoverage;		
//		}
//		return this.currentLineCoverage;	
//	};		
//	
//	this.getNumberOfTotalFunctions = function(){
//		var count = 0;
//		for(funcObj in this.typeFunctions){
//			count++;
//		}
//		return count;	
//	}
//	this.getNumberOfCoveredFunctions = function(){
//		var count = 0;
//		for(funcObj in this.typeFunctions){
//			if(this.typeFunctions[funcObj].currentLineCoverage > 0){
//				count++;
//			}
//		}
//		return count;	
//	}
//}
//	
//function aFunction(name){
//	this.functionName = name;
//	this.trackLineCoverage = [];
//	this.trackLineCoverage[0] = "Not Used";
//	this.currentLineCoverage = 0;
//	this.totalLines = 0;
//	this.instrJs = "";
//	
//	this.addLineCoverage = function(lineNumber){
//		if(typeof(this.trackLineCoverage[lineNumber]) == "undefined"){
//			this.trackLineCoverage[lineNumber] = "hit";
//			this.currentLineCoverage = this.currentLineCoverage + 1;
//		}
//	};
//}



//jsCoverage.jsCoverage = function(t,m,l){
//	var myType = jsCoverage.coverageInfo.types[t];
//	if(typeof(myType) == "undefined"){
//		myType = new coverageType(t);
//		myType.addFunction(m);
//		jsCoverage.coverageInfo.types[t] = myType;
//	}
//	if(myType.hasFunction(m)){
//		myType.getFunction(m).addLineCoverage(l);
//	}
//	else{
//		myType.addFunction(m);
//		myType.getFunction(m).addLineCoverage(l);
//	}
//}

//jsCoverage.jsCoverageReport = function(){
//	var str = "";
//	var altTblBackgrd = true;
//	var rowGrayBackgrd = "<TR CLASS=\"o\">";
//	var rowBackgrd = "<TR>";
//	var totalLineCount = 0;
//	var coveredLineCount = 0;
//	if(jsCoverage.isJsUnit() == true){
//	}
//	for(i in jsCoverage.coverageInfo.types){
//		if(altTblBackgrd == true){
//			altTblBackgrd = false;
//			str += rowGrayBackgrd;
//		}
//		else{
//			altTblBackgrd = true;
//			str += rowBackgrd;
//		}
//		if(typeof(jsCoverage.coverageInfo.types[i]) == "undefined"){
//			jsCoverage.coverageInfo.types[i] = new coverageType(i);
//		}
//		var lineCoverage = jsCoverage.formatNumber((jsCoverage.coverageInfo.types[i].getTypeCoverageLineCount()/jsCoverage.coverageInfo.types[i].getTypeLineCount())*100);
//		coveredLineCount = coveredLineCount + jsCoverage.coverageInfo.types[i].getTypeCoverageLineCount();
//		totalLineCount = totalLineCount + jsCoverage.coverageInfo.types[i].getTypeLineCount();
//		str += "<TD><a onclick=\"jsCoverage.jsDetailReport('" + i + "')\">" + i + "</a></TD><TD>WIP</TD><TD class=\"h\">" + jsCoverage.formatNumber((jsCoverage.coverageInfo.types[i].getNumberOfCoveredFunctions()/jsCoverage.coverageInfo.types[i].getNumberOfTotalFunctions())*100) + "% (" + jsCoverage.coverageInfo.types[i].getNumberOfCoveredFunctions() + "/" + jsCoverage.coverageInfo.types[i].getNumberOfTotalFunctions() + ")</TD><TD>WIP</TD><TD class=\"h\">" + lineCoverage + "%  (" + jsCoverage.coverageInfo.types[i].getTypeCoverageLineCount() + "/" + jsCoverage.coverageInfo.types[i].getTypeLineCount() + ")</TD></TR>";
//	}
//	var html = jsCoverage.htmlReportTemplate.replace("ENTER_TYPE_DATA", str);
//	var totalCoverage = jsCoverage.formatNumber((coveredLineCount/totalLineCount) * 100);
//	var totalFunctionCount = 0;
//	for(id in jsCoverage.typeFunctionCounts){
//		totalFunctionCount = totalFunctionCount + jsCoverage.typeFunctionCounts[id];
//	}
//	var coveredFunctionCount = 0;
//	for(id in jsCoverage.coverageInfo.types){
//			coveredFunctionCount = coveredFunctionCount + jsCoverage.coverageInfo.types[id].getNumberOfTotalFunctions();
//	}
//	var overallCoverage = "<TR><TD>all classes</TD><TD>" + jsCoverage.formatNumber((jsCoverage.coverageInfo.getLength()/jsCoverage.getTypesCount())*100) + "% (" + jsCoverage.coverageInfo.getLength() + "/" + jsCoverage.getTypesCount() + ")</TD><TD class=\"h\">" + jsCoverage.formatNumber((coveredFunctionCount/totalFunctionCount)*100) + "% (" + coveredFunctionCount + "/" + totalFunctionCount + ")</TD><TD>WIP</TD><TD class=\"h\">" + totalCoverage + "%  (" + coveredLineCount + "/" + totalLineCount + ")</TD></TR>";
//	html = html.replace("TOTAL_COVERAGE", overallCoverage);
//	var x = window.open("about:blank");
//	x.document.location = "about:blank";
//	x.document.write(html);
//	x.document.close();
//};

jsCoverage.jsDetailReport = function(typeName){
	var type = jsCoverage.types[typeName];
	var type = jsCoverage.types[typeName];
	var functions = type.typeFunctionList;
	var data = "";
	var html = jsCoverage.htmlReportTemplate;
	var altTblBackgrd = true;
	for(var key in functions){
		var func = functions[key].js;
		func = func.replace(new RegExp("\\\r", "g"),"");
		var script = func.split("\n");
		var pre = "<pre>";
		var green = false;
		var hitCount;
		for(var i = 0; i < script.length; i++){
			
			if(script[i] == "    " || i == 0){
				//in case of blank line do nothing.
			}
			else if(script[i].indexOf("cov.jsCoverage(") > -1){
				var lineNum = script[i].substring(script[i].lastIndexOf(",")+1, script[i].lastIndexOf(")"));
				lineNum = lineNum.substring(lineNum.indexOf("\"")+1, lineNum.lastIndexOf("\""));
				if(functions[key].trackLineCoverage[lineNum] > 0){
					green = true;
					hitCount = functions[key].trackLineCoverage[lineNum];
				}else{
					green = false;
				}
			}else if(green == true && script[i-1].indexOf("cov.jsCoverage(") > -1){
				pre  += "<div style=\"background:#91DE49\">" + script[i] + " [HITCOUNT: " + hitCount +"]</div>";
			}else if(green == false && script[i-1].indexOf("cov.jsCoverage(") > -1){
				pre += "<div style=\"background:#FE92A7\">" + script[i] + "</div>";
			}else{
				pre += "<div style=\"background:#FFFFFF\">" + script[i] + "</div>";
			}
		}
		var tr = "";
		if(altTblBackgrd == true){
			altTblBackgrd = false;
			tr = "<TR CLASS=\"o\">";
		}
		else{
			altTblBackgrd = true;
			tr = "<TR>";
		}
	
		var classPer = "n/a";
		var methodPer = type.numberOfCoveredFunctions/type.numberOfTotalFunctions * 100;
		var linePer = functions[key].curLineCovCount/functions[key].totalLines * 100;
		html = html.replace("ENTER_TYPE_DATA", tr + "<TD>" + functions[key].functionName + "</TD><TD>" + classPer + "</TD><TD>n/a</TD><TD>WIP</TD><TD>" + linePer + "% (" + functions[key].curLineCovCount + "/" + functions[key].totalLines + ")</TD></TR>ENTER_TYPE_DATA");
		pre += "</pre>";
		html = html.replace("</BODY>", pre + "\n</BODY>");
	}
	
	var ttlFuncCov = type.typeCoverageLineCount/type.typeLineCount * 100;
	html = html.replace("TOTAL_COVERAGE","<TR><TD>" + type.typeName + "</TD><TD>100% (1/1)</TD><TD>" + methodPer + "% (" + type.numberOfCoveredFunctions + "/" + type.numberOfTotalFunctions + ")</TD><TD>WIP</TD><TD>" + ttlFuncCov + "% (" + type.typeCoverageLineCount + "/" + type.typeLineCount + ")</TD></TR>");
	
	//remove the overall stats summary section.
	html = html.replace("<H3>OVERALL STATS SUMMARY</H3><TABLE CLASS=\"it\" CELLSPACING=\"0\"><TR><TD>total packages:</TD><TD>WIP (Work In Progress)</TD></TR><TR><TD>total executable files:</TD><TD>WIP</TD></TR><TR><TD>total classes:</TD><TD>WIP</TD></TR><TR><TD>total methods:</TD><TD>WIP</TD></TR><TR><TD>total executable lines:</TD><TD>WIP</TD></TR></TABLE>", "");
	html = html.replace("ENTER_TYPE_DATA", "");
	var x = window.open("about:blank");
	x.document.location = "about:blank";
	x.document.write(html);
	x.document.close();
}

//
//jsCoverage.formatNumber = function(num){
//	num = num + "";
//	if(num.indexOf("\.") > -1){
//		num = num.substring(0, num.indexOf("\.")+2);
//	}
//	return num;
//};
//
//jsCoverage.isJsUnit = function(){
//	if(typeof(jsCoverage.json)=="undefined"){
//		return false;
//	}
//	for(i in jsCoverage.json){
//		jsCoverage.coverageInfo.types[jsCoverage.json[i].typeName] = createCoverageType(jsCoverage.json[i]);
//	}
//	return true;
//};
