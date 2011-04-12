/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.codegentools;

import java.io.File;
import java.io.IOException;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.jstojava.controller.BuildController;
import org.ebayopensource.dsf.jstojava.controller.GenerationConfig;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.jstojava.translator.TranslateConfig;
import org.ebayopensource.dsf.ts.type.TypeName;

public class JsToJavaGen {

	public enum JsGenTypes {
		JSR,
		NJP
	}
	
	private final static JsGenTypes[] DEFAULT_GEN_TARGETS = {JsGenTypes.JSR,JsGenTypes.NJP};

	/**
	 * // to generate all types for a source path I will update tool to do this
	 * JsToJavaGen projectpath sourcedirs 
	 * 
	 * // example 
	 * JsToJavaGen -projectPath="d:\cc\jearly_v4_yoda\v4darwin\VjLib\" -sourceDirs="src" 
	 * 
	 * //to generate one type for a source path I will update tool to do this:
	 * 
	 * JsToJavaGen sourcepath sourcedirs typeName [types to gen] 
	 * // example
	 * JsToJavaGen -projectPath="d:\cc\jearly_v4_yoda\v4darwin\VjLib\"
	 * -sourceDirs="src" -genOnlyThisType="vjox.example.MyType" 
	 * 
	 * // genConfig should be able to be specified to gen only what is required. 
	 * JsToJavaGen
	 * -projectPath="d:\cc\jearly_v4_yoda\v4darwin\VjLib\" -sourceDirs="src"
	 * -genOnlyThisType="vjox.example.MyType" -genConfig=JSR,NJP
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("--------------");
		for (int i = 0; i < args.length; i++) {
			System.out.println(">>>" + args[i]);
		}
		System.out.println("--------------");
			String onDemandLoc = checkAndGetOnDemandLocation(args);
			if (onDemandLoc!=null) {
				generate(onDemandLoc);
			} else {
				JsToJavaGenInfo info = parseArgs(args);
				generate(info);
			}
	}
	private static void generate(String location) {
		String ONDEMAND = "ONDEMAND";
		TranslateConfig cfg = new TranslateConfig();
		BuildController c = new BuildController();
		JstTypeSpaceMgr mgr = c.loadType(ONDEMAND,null);
		
//		TsLibLoader.loadDefaultLibs(mgr);
		cfg.setSkiptImplementation(true);
		VjoParser p = new VjoParser(cfg);
		IJstType t = p.parse(ONDEMAND, new File(location)).getType();
		mgr = c.loadType(ONDEMAND,t);
		
		GenerationConfig config = new GenerationConfig();
		config.setGenJsr(true);
		config.setGenNJP(true);
		IJstType type = mgr.getQueryExecutor().findType(new TypeName(ONDEMAND,t.getName()));
		if(type!=null){
			try {
				c.generate(type, config);
				c.printOut(System.out, c.getResult());
			} catch (IOException e) {
				e.printStackTrace();//KEEPME
			}
		} else {
			System.err.println("Problem generating " + location);;//KEEPME
		}
	}
	private static void generate(JsToJavaGenInfo info) {
		BuildController c = new BuildController();
		JstTypeSpaceMgr mgr = c.loadTypes("TEST", info.getProjectPath(), info.getSourcDirs());
		GenerationConfig config = new GenerationConfig();
		determineGenConfig(config,info);
		
		try {
			if(info.getOnlyThisType()==null){
				c.generateAll(config);
			}
			else{
				TypeName typeName = new TypeName("TEST",info.getOnlyThisType());
				
				IJstType type = mgr.getQueryExecutor().findType(typeName);
				if(type!=null){
					c.generate(type, config);
				}else{
					throw new DsfRuntimeException("type : " + info.getOnlyThisType() +" was not found in typespace");
				}
			}
			c.printOut(System.out, c.getResult());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();//KEEPME
		}
	}
	
	private static String checkAndGetOnDemandLocation(String[] args) {
		if (args.length==1 && args[0].indexOf("=")<0) {
			return args[0].replace('\\', '/') ;
		}
		return null;
	}
	private static void determineGenConfig(GenerationConfig config,
			JsToJavaGenInfo info) {
		JsGenTypes[] types = info.getGenTypes();
		if(types==null){
			return;
		}
		config.setGenJsr(false);
		config.setGenNJP(false);
		
		for(JsGenTypes type : types){
			if(type==null){
				continue;
			}
			if(type.equals(JsGenTypes.JSR)){
				config.setGenJsr(true);
			}
			if(type.equals(JsGenTypes.NJP)){
				config.setGenNJP(true);
			}
		}
		
	}

	static class JsToJavaGenInfo{
		private String m_projectPath;
		private String[] m_sourcDirs;
		private String m_onlyThisType;
		private JsGenTypes[] m_genTypes;
		public String getProjectPath() {
			return m_projectPath;
		}
		public String[] getSourcDirs() {
			return m_sourcDirs;
		}
		public String getOnlyThisType() {
			return m_onlyThisType;
		}
		public JsGenTypes[] getGenTypes() {
			return m_genTypes;
		}
		public void setProjectPath(String projectPath) {
			m_projectPath = projectPath;
		}
		public void setSourcDirs(String[] sourcDirs) {
			m_sourcDirs = sourcDirs;
		}
		public void setOnlyThisType(String onlyThisType) {
			m_onlyThisType = onlyThisType;
		}
		public void setGenTypes(JsGenTypes[] genTypes) {
			m_genTypes = genTypes;
		}
	}

	private static JsToJavaGenInfo parseArgs(String[] args) {
		JsToJavaGenInfo info = new JsToJavaGenInfo();
		for(String a: args){
			String[] split = a.split("=");
			
			handleProjectPath(info, a, split);
			handleSourceDir(info, a, split);
			handleGenOnlyOneType(info,a,split);
			handleGenConfig(info,a,split);
		}
		return info;
	}

	
	private static void handleGenConfig(JsToJavaGenInfo info, String a,
			String[] split) {
		JsGenTypes[] targets = new JsGenTypes[2];
		if(a.indexOf("-genConfig")!=-1){
			if(split[1].indexOf(",")!=-1){
				String[] types = split[1].split(",");
				for(String type:types){
					determineTargets(targets, type);
				}
			}else if(split[1]!=null){
				determineTargets(targets, split[1]);
			}else{
				targets = DEFAULT_GEN_TARGETS;
			}
		} else {
			targets = DEFAULT_GEN_TARGETS;
		}
		info.setGenTypes(targets);
	}

	private static void determineTargets(JsGenTypes[] targets, String type) {
		if(type.indexOf("JSR")!=-1){
			targets[0] = JsGenTypes.JSR;
		}
		if(type.indexOf("NJP")!=-1){
			targets[1] = JsGenTypes.NJP;
		}
	}

	private static void handleGenOnlyOneType(JsToJavaGenInfo info, String a,
			String[] split) {
		if(a.indexOf("-genOnlyThisType")!=-1){
			info.setOnlyThisType(split[1]);
		}
		
	}

	private static void handleProjectPath(JsToJavaGenInfo info, String a,
			String[] split) {
		if(a.indexOf("-projectPath")!=-1){
			info.setProjectPath(split[1]);
		}
	}

	private static void handleSourceDir(JsToJavaGenInfo info, String a,
			String[] split) {
		if(a.indexOf("-sourceDirs")!=-1){
			String[] srcDirs;
			if(split[1].indexOf(",")!=-1){
				srcDirs = split[1].split(",");
			}else{
				srcDirs = new String[]{split[1]};
			}
			
			info.setSourcDirs(srcDirs);
		}
	}

}
