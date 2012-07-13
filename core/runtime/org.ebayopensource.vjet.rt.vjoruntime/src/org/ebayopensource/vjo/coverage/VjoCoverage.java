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
import java.util.HashMap;
import java.util.Set;

import org.ebayopensource.dsf.active.client.ScriptExecutor;
import org.ebayopensource.dsf.dap.rt.BaseScriptable;
import org.ebayopensource.dsf.json.serializer.SerializationException;
import org.ebayopensource.dsf.service.serializer.JsonSerializer;
import org.ebayopensource.dsf.util.JavaSourceLocator;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.ScriptableObject;
import org.ebayopensource.dsf.common.FileUtils;

public class VjoCoverage extends BaseScriptable {
		
		private static final long serialVersionUID = 1L;
		private static final String[] MTD_NAMES = {"jsCoverage","jsCoverageReport","jsFuncLineCount"};
		private static final String VJO_INSTANCE = "alap";
		private static final String COVERAGE_INSTANCE = "cov";
		
		public VjoCoverage() {
			defineFunctionProperties(MTD_NAMES);
		}
		
		public void attach(Context cx, Scriptable scope) {
			Object scriptal = Context.javaToJS(this, scope);
			String script = "var alap = {};";
			ScriptExecutor.executeScript(script, scope, cx);
			
			Scriptable vjo = (Scriptable)ScriptableObject.getProperty(scope, VJO_INSTANCE);
			ScriptableObject.putProperty(vjo, COVERAGE_INSTANCE, scriptal);
		}
		
		private HashMap <String, JsCoverageType> types = JsCoverageBean.types;
		
		public void jsCoverage(String type, String method, String lineNum){
			JsCoverageType covType = types.get(type);
			if(covType == null){
				covType = new JsCoverageType(type);
				covType.addFunction(method);
				types.put(type, covType);
			}
			
			if(covType.getFunction(method) == null){
				covType.addFunction(method);
			}				
			JsCoverageFunction covFunc = covType.getFunction(method);	
			covFunc.lineCovered(Integer.parseInt(lineNum));				
		}
		
		public void jsFuncLineCount(String type, String method, String lineNum){
			JsCoverageType covType = types.get(type);
			if(covType == null){
				covType = new JsCoverageType(type);
				types.put(type, covType);
			}
			
			if(covType.getFunction(method) == null){
				covType.addFunction(method);
			}				
			JsCoverageFunction covFunc = covType.getFunction(method);
			covFunc.setTotalLines(Integer.parseInt(lineNum));
		}
		
		public Set getTypeNames() {
			return types.keySet();
		}

		public void setTypes(HashMap <String, JsCoverageType> coverage) {
			this.types = coverage;
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

//		public void jsCoverageDump(){
//			String str = "coverage output<br>";
//			for(i in window._lines){
//				str += "key = " + i + " line frequency = " + window._lines[i] + "<br>";
//			}
//			var elem = document.getElementById("jscoverage");
//			elem.innerHTML=str;
//		};



		public void jsCoverageReport() throws UnsupportedEncodingException, FileNotFoundException, IOException{
			
			
			String str = "";
			boolean altTblBackgrd = true;
			String rowGrayBackgrd = "<TR CLASS=\"o\">";
			String rowBackgrd = "<TR>";
			int totalLineCount = 0;
			int coveredLineCount = 0;
			String htmlReportTemplate = FileUtils.readFile(JavaSourceLocator.getInstance().getSourceUrl(JsCoverageType.class).getPath().replace("JsCoverageType.java", "JsReportHtmlTemplate.html"), "cp1252");
//			if(vjo._isJsUnit() == true){
//			}
			
			int totalFunctionCount = 0;
			int coveredFunctionCount = 0;
			int coveredTypeCount = 0;
			for(JsCoverageType i : types.values()){
				if(altTblBackgrd == true){
					altTblBackgrd = false;
					str += rowGrayBackgrd;
				}
				else{
					altTblBackgrd = true;
					str += rowBackgrd;
				}
				
				coveredLineCount = coveredLineCount + i.getTypeCoverageLineCount();
				totalLineCount = totalLineCount + i.getTypeLineCount();
				totalFunctionCount = totalFunctionCount + i.getNumberOfTotalFunctions();
				coveredFunctionCount = coveredFunctionCount + i.getNumberOfCoveredFunctions();
				
				if(i.hasCoverage()){
					coveredTypeCount = coveredTypeCount + 1;
				}
				
				String lineCoverage = "0";
				if(i.getTypeLineCount() != 0){
					lineCoverage = formatNumber(((double)i.getTypeCoverageLineCount()/i.getTypeLineCount())*100);
				}
				
				String funcTotalCount = "0";
				if(i.getNumberOfTotalFunctions() != 0){
					funcTotalCount = formatNumber(((double)i.getNumberOfCoveredFunctions()/i.getNumberOfTotalFunctions())*100);
				}
				
				str += "<TD><a onclick=\"jsCoverage.jsDetailReport('" + i.getTypeName() + "')\">" + i.getTypeName() + "</a></TD><TD>WIP</TD><TD class=\"h\">" + funcTotalCount + "% (" + i.getNumberOfCoveredFunctions() + "/" + i.getNumberOfTotalFunctions() + ")</TD><TD>WIP</TD><TD class=\"h\">" + lineCoverage + "%  (" + i.getTypeCoverageLineCount() + "/" + i.getTypeLineCount() + ")</TD></TR>";
			}
			
			String html = htmlReportTemplate.replace("ENTER_TYPE_DATA", str);
			html = html.replace("<\\/script>", "</script>");
			String totalCoverage = "0";
			if(totalLineCount != 0){
				totalCoverage = formatNumber(((double)coveredLineCount/totalLineCount) * 100);
			}
			
			String totalFunc = "0";
			if(totalFunctionCount != 0){
				totalFunc = formatNumber(((double)coveredFunctionCount/totalFunctionCount)*100);
			}
			
			String totalTypes = "0";
			if(types.size() != 0){
				totalTypes = formatNumber(((double)coveredTypeCount/types.size())*100);
			}
			 
			String overallCoverage = "<TR><TD>all classes</TD><TD>" + totalTypes + "% (" + coveredTypeCount + "/" + types.size() + ")</TD><TD class=\"h\">" + totalFunc + "% (" + coveredFunctionCount + "/" + totalFunctionCount + ")</TD><TD>WIP</TD><TD class=\"h\">" + totalCoverage + "%  (" + coveredLineCount + "/" + totalLineCount + ")</TD></TR>";
			html = html.replace("TOTAL_COVERAGE", overallCoverage);
			JsonSerializer ser = JsonSerializer.getInstance();
			try {
				JsCoverageBean bean = new JsCoverageBean();
				String json = ser.serialize(types);
				Set keys = types.keySet();
				for(Object key : keys){
					json = json.replace(key + "\":{", key + "\":{\"javaClass\":\""+ JsCoverageType.class.getName() + "\",");
					HashMap <String, JsCoverageFunction> funcList = types.get(key).getTypeFunctionList();
					Set funcKeys = funcList.keySet();
					for(Object funcKey : funcKeys){
						json = json.replace(funcKey + "\":{", funcKey + "\":{\"javaClass\":\""+ JsCoverageFunction.class.getName() + "\",");
					}
				}
				System.out.println("JSON: " + json);
				String covjs = FileUtils.readFile(JavaSourceLocator.getInstance().getSourceUrl(JsCoverageType.class).getPath().replace("JsCoverageType.java", "Coverage.js"), "cp1252");
				html = html.replace("JSSLOT", "var jsCoverage = {};\njsCoverage.htmlReportTemplate = \"" + htmlReportTemplate.replaceAll("\"", "\\\\\"") + "\";\njsCoverage.types =" + json + "\n" + covjs);
				FileUtils.writeFile(JavaSourceLocator.getInstance().getSourceUrl(VjoCoverage.class).getPath().replace(".java", ".html"), html, "cp1252");
				FileUtils.writeFile(JavaSourceLocator.getInstance().getSourceUrl(VjoCoverage.class).getPath().replace(".java", "cov" + System.currentTimeMillis()+".txt"), json, "cp1252");
			} catch (SerializationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

//		vjo._jsDetailReport = function(typeName){
//			var type = vjo._coverageInfo.types[typeName];
//			var functions = type.typeFunctions;
//			var data = "";
//			var html = vjo._htmlReportTemplate;
//			for(func in functions){
//				var script = functions[func].instrJs.split("\n");
//				for(var i = 0; i < script.length; i++){
//					if(i == 0){
//						data = "<pre>" + script[i] + "</pre>";
//						html = html.replace("ENTER_TYPE_DATA", data + "ENTER_TYPE_DATA");
//					}
//					else if(script[i].indexOf("vjo._jsCoverage('") > -1){
//						var lineNum = script[i].substring(script[i].lastIndexOf(",")+1, script[i].lastIndexOf(")"));
//						if(functions[func].trackLineCoverage[lineNum] === "hit"){
//							data = "<pre style=\"background:#91DE49\">" + script[i+1] + "</pre>";
//						}else{
//							data = "<pre style=\"background:#FE92A7\">" + script[i+1] + "</pre>";
//						}
//						html = html.replace("ENTER_TYPE_DATA", data + "ENTER_TYPE_DATA");
//					}
//				}
//			}
//			var div = this + "<div>" + data + "</div>";
//			var x = window.open("about:blank");
//			x.document.location = "about:blank";
//			x.document.write(html);
//			x.document.close();
//			
//		}


		public String formatNumber(double val){
			String num = val + "";
			if(num.indexOf(".") > -1){
				num = num.substring(0, num.indexOf(".")+2);
			}
			return num;
		}

//		vjo._isJsUnit = function(){
//			if(typeof(vjo._json)=="undefined"){
//				return false;
//			}
//			for(i in vjo._json){
//				vjo._coverageInfo.types[vjo._json[i].typeName] = createCoverageType(vjo._json[i]);
//			}
//			return true;
//		};
		
		public static void main(String[] args) throws UnsupportedEncodingException, SerializationException {
			JsonSerializer ser = JsonSerializer.getInstance();
			try {
				String coverageData = FileUtils.readFile(JavaSourceLocator.getInstance().getSourceUrl(VjoCoverage.class).getPath().replace(".java", "cov1231231253577.txt"), "cp1252");
				HashMap <String, JsCoverageType> obj = (HashMap <String, JsCoverageType>) ser.deserialize(coverageData, HashMap.class, null);
				String coverageData2 = FileUtils.readFile(JavaSourceLocator.getInstance().getSourceUrl(VjoCoverage.class).getPath().replace(".java", "cov1231231311260.txt"), "cp1252");
				HashMap <String, JsCoverageType> obj2 = (HashMap <String, JsCoverageType>) ser.deserialize(coverageData2, HashMap.class, null);
//				JsCoverageBean obj2 = (JsCoverageBean) ser.deserialize(FileUtils.readFile(JavaSourceLocator.getInstance().getSourceUrl(VjoCoverage.class).getPath().replace(".java", "cov1231199387911.txt"), "cp1252"), JsCoverageBean.class, null);
				Set keys = obj.keySet();
				for(Object key : keys){
					HashMap <String, JsCoverageFunction> funcList = obj.get(key).getTypeFunctionList();
					HashMap <String, JsCoverageFunction> funcList2 = obj2.get(key).getTypeFunctionList();
					Set funcKeys = funcList.keySet();
					for(Object funcKey : funcKeys){
						JsCoverageFunction func = funcList.get(funcKey);
						JsCoverageFunction func2 = funcList2.get(funcKey);
						int [] lineCov = func.getTrackLineCoverage();
						int [] lineCov2 = func2.getTrackLineCoverage();
						for(int i=0; i<lineCov.length;i++){
							System.out.println(lineCov[i] + " + " +lineCov2[i]);
							if(lineCov[i] == 0 && lineCov2[i] > 0){
								func.setCurLineCovCount(func.getCurLineCovCount() + 1);
							}
							lineCov[i] = lineCov[i]+lineCov2[i];
							
							
						}
						func.setTrackLineCoverage(lineCov);
					}
				}
//				System.out.println("2:" + obj.get("bootstrap_3").getTypeCoverageLineCount());
				VjoCoverage cov =	new VjoCoverage();
				cov.types = obj;
				cov.jsCoverageReport();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
