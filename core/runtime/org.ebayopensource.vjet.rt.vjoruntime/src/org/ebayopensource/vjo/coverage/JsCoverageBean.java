/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.coverage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.ebayopensource.dsf.json.serializer.SerializationException;
import org.ebayopensource.dsf.service.serializer.JsonSerializer;
import org.ebayopensource.dsf.util.JavaSourceLocator;
import org.ebayopensource.dsf.common.FileUtils;


public class JsCoverageBean {
	public static HashMap <String, JsCoverageType> types = new HashMap <String, JsCoverageType>();
	
	public JsCoverageBean(){
	}
	
	public HashMap <String, JsCoverageType> getTypes() {
		return types;
	}
	
	public Collection <JsCoverageType> getTypesValues() {
		return types.values();
	}
	
	public JsCoverageType getType(String name) {
		return types.get(name);
	}
	
	public boolean addType(String name) {
		JsCoverageType type = new JsCoverageType(name);
		if(types.containsKey(name)){
			return false;
		}
		types.put(name, type);
		return true;
	}
	
	public boolean addType(JsCoverageType type) {
		if(types.containsKey(type.getTypeName())){
			return false;
		}
		types.put(type.getTypeName(), type);
		return true;
	}
	
	public Set getTypeNames() {
		return types.keySet();
	}

	public void setTypes(HashMap <String, JsCoverageType> coverage) {
		types = coverage;
	}
	
	public int getTypesCount(){
		return types.size();
	}

	public int getCoveredTypesCount(){
		int count = 0;
		for(JsCoverageType type : types.values()){
			if(type.hasCoverage()){
				count++;	
			}
		}
		return count;
	}
	
//	public void createCoverageType(String typeName) {
//		if(types.get(typeName) == null){
//			JsCoverageType type = new JsCoverageType(typeName);
//			for (var key in obj) {
//				jsonType[key] = obj[key];
//			}
//			
//			var functions = jsonType.typeFunctions;
//			for(var func in functions){
//				var newFunc = new aFunction("");
//				var oldFunc = jsonType.typeFunctions[func];
//				for(var key in oldFunc){
//					newFunc[key]=oldFunc[key];
//				}
//				jsonType.typeFunctions[func]=newFunc;
//			}
//			return jsonType;
//		}
//	}

	

//	public void jsCoverageDump(){
//		String str = "coverage output<br>";
//		for(i in window._lines){
//			str += "key = " + i + " line frequency = " + window._lines[i] + "<br>";
//		}
//		var elem = document.getElementById("jscoverage");
//		elem.innerHTML=str;
//	};



	public void jsCoverageReport() throws UnsupportedEncodingException, FileNotFoundException, IOException{
		String str = "";
		boolean altTblBackgrd = true;
		String rowGrayBackgrd = "<TR CLASS=\"o\">";
		String rowBackgrd = "<TR>";
		int totalLineCount = 0;
		int coveredLineCount = 0;
		String htmlReportTemplate = FileUtils.readFile(JavaSourceLocator.getInstance().getSourceUrl(JsCoverageType.class).getPath().replace("JsCoverageType.java", "JsReportHtmlTemplate.html"), "cp1252");
//		if(vjo._isJsUnit() == true){
//		}
		
		for(JsCoverageType i : types.values()){
			if(altTblBackgrd == true){
				altTblBackgrd = false;
				str += rowGrayBackgrd;
			}
			else{
				altTblBackgrd = true;
				str += rowBackgrd;
			}
//			if(typeof(vjo._coverageInfo.types[i]) == "undefined"){
//				vjo._coverageInfo.types[i] = new coverageType(i);
//			}
			String lineCoverage = formatNumber((i.getTypeCoverageLineCount()/i.getTypeLineCount())*100);
			coveredLineCount = coveredLineCount + i.getTypeCoverageLineCount();
			totalLineCount = totalLineCount + i.getTypeLineCount();
			str += "<TD><a onclick=\"vjo._jsDetailReport('" + i + "')\">" + i + "</a></TD><TD>WIP</TD><TD class=\"h\">" + formatNumber((i.getNumberOfCoveredFunctions()/i.getNumberOfTotalFunctions())*100) + "% (" + i.getNumberOfCoveredFunctions() + "/" + i.getNumberOfTotalFunctions() + ")</TD><TD>WIP</TD><TD class=\"h\">" + lineCoverage + "%  (" + i.getTypeCoverageLineCount() + "/" + i.getTypeLineCount() + ")</TD></TR>";
		}
		String html = htmlReportTemplate.replace("ENTER_TYPE_DATA", str);
		String totalCoverage = formatNumber((coveredLineCount/totalLineCount) * 100);
		int totalFunctionCount = 0;
//		for(id in typeFunctionCounts){
//			totalFunctionCount = totalFunctionCount + vjo._typeFunctionCounts[id];
//		}
		int coveredFunctionCount = 0;
//		for(id in vjo._coverageInfo.types){
//				coveredFunctionCount = coveredFunctionCount + vjo._coverageInfo.types[id].getNumberOfTotalFunctions();
//		}
		String overallCoverage = "<TR><TD>all classes</TD><TD>" + formatNumber((types.size()/types.size())*100) + "% (" + types.size() + "/" + types.size() + ")</TD><TD class=\"h\">" + formatNumber((coveredFunctionCount/totalFunctionCount)*100) + "% (" + coveredFunctionCount + "/" + totalFunctionCount + ")</TD><TD>WIP</TD><TD class=\"h\">" + totalCoverage + "%  (" + coveredLineCount + "/" + totalLineCount + ")</TD></TR>";
		html = html.replace("TOTAL_COVERAGE", overallCoverage);
//		var x = window.open("about:blank");
//		x.document.location = "about:blank";
//		x.document.write(html);
//		x.document.close();
	};

//	vjo._jsDetailReport = function(typeName){
//		var type = vjo._coverageInfo.types[typeName];
//		var functions = type.typeFunctions;
//		var data = "";
//		var html = vjo._htmlReportTemplate;
//		for(func in functions){
//			var script = functions[func].instrJs.split("\n");
//			for(var i = 0; i < script.length; i++){
//				if(i == 0){
//					data = "<pre>" + script[i] + "</pre>";
//					html = html.replace("ENTER_TYPE_DATA", data + "ENTER_TYPE_DATA");
//				}
//				else if(script[i].indexOf("vjo._jsCoverage('") > -1){
//					var lineNum = script[i].substring(script[i].lastIndexOf(",")+1, script[i].lastIndexOf(")"));
//					if(functions[func].trackLineCoverage[lineNum] === "hit"){
//						data = "<pre style=\"background:#91DE49\">" + script[i+1] + "</pre>";
//					}else{
//						data = "<pre style=\"background:#FE92A7\">" + script[i+1] + "</pre>";
//					}
//					html = html.replace("ENTER_TYPE_DATA", data + "ENTER_TYPE_DATA");
//				}
//			}
//		}
//		var div = this + "<div>" + data + "</div>";
//		var x = window.open("about:blank");
//		x.document.location = "about:blank";
//		x.document.write(html);
//		x.document.close();
//		
//	}


	public String formatNumber(double val){
		String num = val + "";
		if(num.indexOf("\\.") > -1){
			num = num.substring(0, num.indexOf("\\.")+2);
		}
		return num;
	}

//	vjo._isJsUnit = function(){
//		if(typeof(vjo._json)=="undefined"){
//			return false;
//		}
//		for(i in vjo._json){
//			vjo._coverageInfo.types[vjo._json[i].typeName] = createCoverageType(vjo._json[i]);
//		}
//		return true;
//	};
	
	public static void main(String[] args) throws UnsupportedEncodingException, SerializationException {
		JsCoverageBean m_coverageBean = new JsCoverageBean();
		JsCoverageType type = new JsCoverageType("alap");
		type.addFunction("funciton1");
		type.addFunction("funciton2");
		type.addFunction("funciton3");
		JsCoverageType type2 = new JsCoverageType("alap2");
		type.addFunction("BLAH1");
		type.addFunction("BLAH2");
		type.addFunction("BLAH3");
		JsCoverageType [] types = new JsCoverageType [] {type,type2};
		//m_coverageBean.setTypes(types);
		String json = JsonSerializer.getInstance().serialize(
				m_coverageBean.getTypes());
		System.out.println(json);
	}

}
