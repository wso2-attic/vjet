/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.parser.bootstrap;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IScriptUnit;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstFactory;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstProperty;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.meta.IJsCommentMeta;
import org.ebayopensource.dsf.jst.meta.JsTypingMeta;
import org.ebayopensource.dsf.jst.util.bootstrap.JsBuilderDef;
import org.ebayopensource.dsf.jst.util.bootstrap.JsBuilderDef.Section;
import org.ebayopensource.dsf.jstojava.parser.AstCompilationResult;
import org.ebayopensource.dsf.jstojava.parser.SyntaxTreeFactory2;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.jstojava.parser.bootstrap.BootstrapParser.StateInfo.OrderedState;
import org.ebayopensource.dsf.jstojava.parser.comments.CommentCollector;
import org.ebayopensource.dsf.jstojava.parser.comments.JsAttributed;
import org.ebayopensource.dsf.jstojava.parser.comments.JsFuncType;
import org.ebayopensource.dsf.jstojava.parser.comments.JsParam;
import org.ebayopensource.dsf.jstojava.report.DefaultErrorReporter;
import org.ebayopensource.dsf.jstojava.report.ErrorReporter;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.ebayopensource.dsf.jstojava.translator.robust.JstSourceUtil;
import org.ebayopensource.vjo.lib.LibManager;

import org.ebayopensource.dsf.common.Z;

public class BootstrapParser {

	private static final StateInfo ENDSTATE = new StateInfo(
			OrderedState.ORDERED, "END", 1000);

	public static Map<String, ? extends IJstType> createJstType(
			String groupName, File bootstrapFile, JsBuilderDef def,
			File topLevelExtApi) throws IOException {
		List<JsBuilderDef> list = new ArrayList<JsBuilderDef>(1);
		list.add(def);
		
		return createJstType(groupName, bootstrapFile, list, topLevelExtApi);
	}

	public static Map<String, ? extends IJstType> createJstType(
			String groupName, File bootstrapFile, List<JsBuilderDef> defs,
			File topLevelExtApi) throws IOException {

		Map<String, IJstType> typeList = new LinkedHashMap<String, IJstType>();
//		Collection<IJsCommentMeta> list = collectComments(bootstrapFile);

		// typeList.add(createTopLevelObj(topLevelObj, list));
		IJstType topLevel = createTopLevelObj(topLevelExtApi, groupName);
		
		
		

		typeList.put(topLevel.getName(), topLevel);

		// creating cascade/ builder api
		// only include methods with return type T
		typeList.putAll(processDefs(defs, bootstrapFile.toURI().toURL()));

		
		return typeList;
	}
	
	public static Map<String, ? extends IJstType> createJstType(
			String groupName, URL bootstrapFile, JsBuilderDef def,
			URL topLevelExtApi) throws IOException {
		List<JsBuilderDef> list = new ArrayList<JsBuilderDef>(1);
		list.add(def);
		
		return createJstType(groupName, bootstrapFile, list, topLevelExtApi);
	}
	
	public static Map<String, ? extends IJstType> createJstType(
			String groupName, URL bootstrapFile, List<JsBuilderDef> defs,
			URL topLevelExtApi, URL ... extentionTypes) throws IOException {

		Map<String, IJstType> typeList = new LinkedHashMap<String, IJstType>();
//		Collection<IJsCommentMeta> list = collectComments(bootstrapFile);

		typeList.putAll(addExtentionTypes(extentionTypes, groupName));
		// typeList.add(createTopLevelObj(topLevelObj, list));
		IJstType topLevel = createTopLevelObj(topLevelExtApi, groupName);

		typeList.put(topLevel.getName(), topLevel);
		

		// creating cascade/ builder api
		// only include methods with return type T
		typeList.putAll(processDefs(defs, bootstrapFile));
		
		dump(typeList.values());
		return typeList;
	}

	private static Map<? extends String, ? extends IJstType> addExtentionTypes(
			URL[] extentionTypes, String groupName) {
		Map<String, IJstType> mapOfTypes = new LinkedHashMap<String, IJstType>(extentionTypes.length);
		VjoParser p = new VjoParser();
		p.addLib(LibManager.getInstance().getJsNativeGlobalLib());
		
		for(URL extention: extentionTypes){
			IScriptUnit unit = p.parse(groupName, extention);
			
			fixUnit(unit);
			
			JstCache.getInstance().addType((JstType)unit.getType());

			mapOfTypes.put(unit.getType().getName(), unit.getType());
		}
		
		
		return mapOfTypes;
	}

	
	private static void fixUnit(IScriptUnit unit) {
		JstType t = (JstType)unit.getType();
		t.setImpliedImport(true);
		t.getModifiers().setStatic(true);
		if("vjo.Object".equals(t.getName())){
			t.removeExtend(t.getExtend());
			JstType object = JstCache.getInstance().getType("Object", false);
			t.addExtend(object);
			// make prototype dynamic for vjo.Object
			((JstProperty)t.getProperty("prototype", true)).setType(object);
			
//			System.out.println(unit.getType().getExtend());
		}
	}

	protected static void dump(Collection<IJstType> jstTypes) {
		for (IJstType jstType : jstTypes) {
			Z z = new Z();
			z.format(">> JstType");
			z.format("name", jstType.getName());
			z.format("modifiers", jstType.getModifiers());
			if (!jstType.getAnnotations().isEmpty()) {
				z.format("annotations", jstType.getAnnotations());
			}
			z.format("extends", jstType.getExtends());
			z.format("static props:");
			for (IJstProperty p : jstType.getAllPossibleProperties(true, true)) {
				if (!p.getAnnotations().isEmpty()) {
					z.format("\tprops-annotations", p.getAnnotations());
				}
				z.append("\t"+p.getModifiers().toString());
				z.append(" "+p.getType().getName());
				z.append(" "+ p.getName());
				z.format(";");
			}
			z.format("instance props:");
			for (IJstProperty p : jstType.getAllPossibleProperties(false, true)) {
				if (!p.getAnnotations().isEmpty()) {
					z.format("\tprops-annotations", p.getAnnotations());
				}
				z.append("\t"+p.getModifiers().toString());
//				z.append(" "+p.getType().getName());
				z.append(" "+ p.getName());
				z.format(";");
			}
			
			IJstMethod cons = jstType.getConstructor();
			if (cons != null) {
				z.format("constructor:");
				z.append("\t"+cons.getModifiers().toString());
				z.append(" " + cons.getName());
				z.append("(");
				Iterator iter = cons.getArgs().iterator();
				while(iter.hasNext()) {
					JstArg arg = (JstArg) iter.next();
					z.append(arg.getType().getName());
					z.append(" "+arg.getName());
					if (iter.hasNext()) {
						z.append(", ");
					}
				}
				z.format(");");
				for (IJstMethod over : cons.getOverloaded()) {
					z.format("overloaded-constructor:");
					z.append("\t"+over.getModifiers().toString());
					z.append(" " + over.getName());
					z.append("(");
					iter = over.getArgs().iterator();
					while(iter.hasNext()) {
						JstArg arg = (JstArg) iter.next();
						z.append(arg.getType().getName());
						z.append(" "+arg.getName());
						if (iter.hasNext()) {
							z.append(", ");
						}
					}
					z.format(");");
				}
			}
			z.format("static methods:");
			for (IJstMethod m : jstType.getMethods(true, true)) {
				if (!m.getAnnotations().isEmpty()) {
					z.format("\tmtd-annotations", m.getAnnotations());
				}
				z.append("\t"+m.getModifiers().toString()+" ");
				if (m.getRtnType() != null) {
					z.append(m.getRtnType().getName());
					z.append(" ");
				}
				z.append(m.getName());
				z.append("(");
				Iterator iter = m.getArgs().iterator();
				while(iter.hasNext()) {
					JstArg arg = (JstArg) iter.next();
					z.append(arg.getType().getName());
					z.append(" "+(arg.isVariable()?"...":"")+arg.getName());
					if (iter.hasNext()) {
						z.append(", ");
					}
				}
				z.format(");");
				for (IJstMethod over : m.getOverloaded()) {
					z.format("overloaded-method:");
					z.append("\t"+over.getModifiers().toString());
					if (over.getRtnType() != null) {
						z.append(" ");
						z.append(over.getRtnType().getName());
						z.append(" ");
					}
					z.append(" " + over.getName());
					z.append("(");
					iter = over.getArgs().iterator();
					while(iter.hasNext()) {
						JstArg arg = (JstArg) iter.next();
						z.append(arg.getType().getName());
						z.append(" "+arg.getName());
						if (iter.hasNext()) {
							z.append(", ");
						}
					}
					z.format(");");
				}
			}
			
			z.format("instance methods:");
			for (IJstMethod m : jstType.getMethods(false, true)) {
				if (!m.getAnnotations().isEmpty()) {
					z.format("\tmtd-annotations", m.getAnnotations());
				}
				z.append("\t"+m.getModifiers().toString()+" ");
				if (m.getRtnType() != null) {
					z.append(m.getRtnType().getName());
					z.append(" ");
				}
				z.append(m.getName());
				z.append("(");
				Iterator iter = m.getArgs().iterator();
				while(iter.hasNext()) {
					JstArg arg = (JstArg) iter.next();
					z.append(arg.getType().getName());
					z.append(" "+(arg.isVariable()?"...":"")+arg.getName());
					if (iter.hasNext()) {
						z.append(", ");
					}
				}
				z.format(");");
				for (IJstMethod over : m.getOverloaded()) {
					z.format("overloaded-method:");
					z.append("\t"+over.getModifiers().toString());
					if (over.getRtnType() != null) {
						z.append(" ");
						z.append(over.getRtnType().getName());
						z.append(" ");
					}
					z.append(" " + over.getName());
					z.append("(");
					iter = over.getArgs().iterator();
					while(iter.hasNext()) {
						JstArg arg = (JstArg) iter.next();
						z.append(arg.getType().getName());
						z.append(" "+arg.getName());
						if (iter.hasNext()) {
							z.append(", ");
						}
					}
					z.format(");");
				}
			}
			System.out.println(z);
		}
	}
	
	private static IJstType createTopLevelObj(File topLevelExtApi,
			String groupName) {
		VjoParser p = new VjoParser();
		p.addLib(LibManager.getInstance().getJsNativeGlobalLib());
		IScriptUnit su = p.parse(groupName, topLevelExtApi);
		
		
		return su.getType();
	}
	
	private static IJstType createTopLevelObj(URL topLevelExtApi,
			String groupName) {
		VjoParser p = new VjoParser();
		p.addLib(LibManager.getInstance().getJsNativeGlobalLib());
		IJstType type = p.parse(groupName, topLevelExtApi).getType();
		if(type instanceof JstType){
			((JstType)type).setImpliedImport(true);
		}
		return type;
	}

//	private static Map<String, IJstType> createCascade(List<JsBuilderDef> defs) {
//		
//		return processDefs(defs);
//	}

	// private static JstType createTopLevelObj(String topLevelObj,
	// Collection<IJsCommentMeta> list) {
	// // creating entry point
	// Map<String, List<JstMethod>> utilMtds = BootstrapParser
	// .createJstMethodMap(list, new IFilter(){
	// @Override
	// public boolean filter(JstMethod m) {
	// if(m.getRtnType().getName().equals("T")){
	// return true;
	// }
	// return false;
	// }
	//			
	// });
	//		
	// JstType topLevel = getType(topLevelObj);
	//	
	// for(String mtdName:utilMtds.keySet()){
	//			
	// List<JstMethod> mtds = utilMtds.get(mtdName);
	// if(mtds.size()>1){
	// handleOverloading(topLevel, null, mtds);
	// }else{
	// JstMethod jstMethod = mtds.get(0);
	// JstMethod copy = createNewJstMethod(jstMethod);
	// topLevel.addMethod(copy);
	// }
	//			
	// }
	//		
	//		
	// return topLevel;
	// }

	private static Map<String, IJstType> processDefs(List<JsBuilderDef> defs, URL bootstrapFile) {
		
		Collection<IJsCommentMeta> commentList = null;
		try {
			commentList = collectComments(bootstrapFile);
		} catch (IOException e) {
			e.printStackTrace(); //KEEPME
		}
		
		Map<String, IJstType> list = new LinkedHashMap<String, IJstType>();

		for (final JsBuilderDef def : defs) {
			
			Map<String, List<JstMethod>> map = createJstMethodMap(def,
					commentList, new IFilter() {
						@Override
						public boolean filter(JsBuilderDef def, JstMethod m) {
							if(m.getRtnType().getName().equals(def.getType().getName())){
								return false;
							}
							
							if (m.getRtnType().getName().equals("T")) {
								return false;
							}
							return true;
						}

					});
			
			list.putAll(processDef(def, map));
		}

		return list;
	}

	/**
	 * 
	 * for each section determine return type result return type + section
	 * 
	 * For each type starting at section name for that type add methods
	 * including overloads ensure that return type is correct
	 * 
	 * dealing with max and min? if(section max = 1) do not include in cascade
	 * 
	 * 
	 * 
	 * VJO.C C C1 C2 C3 C4 C5 C6 C7 VJO.C needs(0,*) 0 X VJO.C1 inherits(0,1) 1
	 * X VJO.C2 satisfies(1,*)2 X X X VJO.C3 props(1,1) 3 X X X VJO.C4
	 * protos(1,1) 4 X X X X VJO.C5 options(0,1) 5 X X X X X VJO.C6 inits(0,1) 6
	 * X X X X X X VJO.C7 endType(1,1) 7 X X X X X X X
	 * 
	 * 
	 * 
	 * 
	 * @param def
	 * @param map
	 * @return
	 */

	private static Map<String, ? extends JstType> processDef(JsBuilderDef def,
			Map<String, List<JstMethod>> map) {

		Map<String, JstType> types = new LinkedHashMap<String, JstType>();

//		List<Section> defs = def.getSections();
//		List<SectionTuple> tuples = new ArrayList<SectionTuple>(defs.size());

		// Map<String,SectionTuple> temp = new LinkedHashMap<String,
		// SectionTuple>();

		// define return types
		Map<String, StateInfo> states = calculateStates(def);
		printStates(states);
		

		// always add first type
		
//		System.out.println("*****************************");
//		System.out.println("maintype: \"" + mainType.getName() + "\"");
//		System.out.println("*****************************");

	
		Iterator<StateInfo> stItr = states.values().iterator();
		
		while (stItr.hasNext()) {
			StateInfo st = stItr.next();
			String baseName = def.getType().getName();
			JstType t = null;
			t = getType(st.m_id, baseName);
			t.setImpliedImport(true);
			t.clearMethods();
			
			for(SectionInfo si : st.m_sections){
			
				
				List<JstMethod> methods = map.get(si.m_section.getMtd());

//				for(JstMethod m:methods){
				if(methods!=null){
					addMethod(t, getType( si.m_nextState.m_id, baseName), methods);
				}
//				}
				
				
				
			}
			types.put(t.getName(), t);
			
//			Section d = defs.get(i);
//			List<JstMethod> methods = map.get(d.getMtd());
//			addMethodsToJst(mainType, d, tuples.get(i).getType(), methods);
		}
		
		
//		types.put(mainType.getName(), mainType);
//
//		for (int sectionId = 0; sectionId < defs.size(); sectionId++) {
//
//			JstType type = tuples.get(sectionId).getType();
//
//			if (types.get(type.getName()) != null) {
//				continue;
//			}
//
//			type.clearMethods();
//
//			// JstType type = JstCache.getInstance().getType(typeName, false);
////			System.out.println("*****************************");
////			System.out.println("type: \"" + type.getName() + "\"");
////			System.out.println("*****************************");
//
//			for (int i = sectionId; i < defs.size(); i++) {
//
//				// deal with any order
//
//				SectionTuple tup = tuples.get(i);
//				Section d = tup.getSection();
//				JstType rtnType = tup.getType();
//
//				// check section if it is anyOrder
//				if (tup.getId() == sectionId && sectionId != 0) {
//					addAnyOrderMtds(def, map, type, d, rtnType);
//				}
//
//				// if (tup.getId() == sectionId && sectionId != 0) {
//				// if(d.getMin()==1 && d.getMax()==1){
//				// System.out.println("skipping :" + d.getMtd());
//				// addAnyOrderMtds(def, map, type, d, rtnType);
//				// continue;
//				// }
//				//					
//				//					
//				// if (d.getMin()==0 && d.getMax()==1) {
//				// System.out.println("skipping :" + d.getMtd());
//				// addAnyOrderMtds(def, map, type, d, rtnType);
//				// continue;
//				// }
//				// }
//
////				System.out.println(d.getMtd());
//
//				List<JstMethod> methods = map.get(d.getMtd());
//
//				if (!type.getName().equals(rtnType.getName())) {
//					addMethodsToJst(type, d, rtnType, methods);
//				}
//			}
//
//			types.put(type.getName(), type);
//
//		}
		// JstType type = JstCache.getInstance().getType(def.getType() +
		// tuples.size()+1, false);
		// types.add(type);

		return types;
	}

	private static JstType getType(int id, String baseName) {
		JstType t;
		if(id==0){
			t = getType(baseName);				
		}else{
			t = getType(baseName + id);
			t.setMetaType(true);
			
		}
		return t;
	}

//	private static void addAnyOrderMtds(JsBuilderDef def,
//			Map<String, List<JstMethod>> map, JstType type, Section d,
//			JstType rtnType) {
//		if (d.isAnyOrder()) {
////			System.out.println(d.getMtd() + " : anyorder");
//			for (Section s : def.getAnyOrderGroup(d.getMtd())) {
//
//				if (d.getMtd() != s.getMtd()
//						&& type.getMethod(s.getMtd()) == null) {
//
////					System.out.println(s.getMtd());
//					List<JstMethod> methods = map.get(s.getMtd());
//					addMethodsToJst(type, s, rtnType, methods);
////					System.out.println("***" + s.getMtd() + " : anyorder");
//				}
//			}
//
//		}
//	}

//	private static void addMethodsToJst(JstType type, Section d,
//			JstType rtnType, List<JstMethod> methods) {
//		if (methods == null || methods.size() == 0) {
//			throw new DsfRuntimeException("missing mtd def: " + d.getMtd());
//		} else {
//
//			addMethod(type, rtnType, d, methods);
//		}
//	}



	private static void calculateNextState(
			Map<String, StateInfo> states, JsBuilderDef def) {

		// given states
		// loop through states
		// set return types
		// if section is state changer
		// create new JstType

		for (String state : states.keySet()) {
//			System.out.println("*****************************");
			StateInfo stateInfo = states.get(state);

//			if (stateInfo.m_orderedState == OrderedState.UNORDERED) {

				List<SectionInfo> sections = stateInfo.m_sections;

				for (SectionInfo sec : sections) {
					// need to determine the return type here
					determineNextState(state, sec, states, def);

				}

		}

	}

	private static void determineNextState(String state, SectionInfo info,
			Map<String, StateInfo> states, JsBuilderDef def) {

//		System.out.println("state =" + state);
//		System.out.println("section =" + info.m_section.getMtd());

		// String[] secs = state.split(",");
		StateInfo stateInfo = states.get(state);
		
		if(info.m_section.getMax()>1){
			info.m_nextState = stateInfo;
//			System.out.println("max > 1 - key = " + stateInfo.m_name);
			return;
		}

		List<SectionInfo> sections = stateInfo.m_sections;
		int lengthOfKey = sections.size() - 1;
		List<String> candidateKeys = new ArrayList<String>();
		for (String s : states.keySet()) {
			// System.out.println("key: " + s);

			if (s.contains(info.m_section.getMtd())) {
				continue;
			}

			candidateKeys.add(s);
			
			

		}

		StateInfo currentState = states.get(state);
		determineKey(currentState, info, states, lengthOfKey, candidateKeys, sections, def.getAnyOrderGroup(info.m_section.getMtd()));

	}

	private static void determineKey(StateInfo currentState, SectionInfo info,
			Map<String, StateInfo> states, int lengthOfKey,
			List<String> candidateKeys, List<SectionInfo> sections,
			List<Section> samegrp) {
		for (String candidateKey : candidateKeys) {
			// number of sections for key

			StateInfo candiateState = states.get(candidateKey);
			
			if(candiateState.m_id<currentState.m_id){
				continue;
			}			
			
//			System.out.println("length of key = " + lengthOfKey);
//			System.out.println("candidate Key = " + candidateKey);
			//if (candiateState.m_sections.size() == lengthOfKey) {

			
//			List<Section> samegrp = def.getAnyOrderGroup(section.getMtd());
//			for (Section s : sections) {
//				if(s.isAnyOrder() && isInSameGrp(s, samegrp)){
			
				boolean useKey = false;
				for (SectionInfo section : sections) {
					
					if(candiateState.m_sections.size()>lengthOfKey){
						continue;
					}
					
					
					if (candidateKey.indexOf(section.m_section.getMtd()) != -1) {
						useKey = true;
					}
					
					
					
//					if(samegrp!=null){
//						for(Section s: samegrp){
//							if(candidateKey.contains(s.getMtd())){
//								useKey = false;
//							}
//						}
//					}
					
					
				}

				if (useKey) {
//					System.out.println("FOUND Key = " + candidateKey);

					info.m_nextState = states.get(candidateKey);

					return;

				} else {
					info.m_nextState = ENDSTATE;

				}
//			} else {
//				info.m_nextState = ENDSTATE;
//			}

		}
	}

	/**
	 * Calculate the number of states and the sections to A B B C C C
	 * 
	 * 
	 * @param def
	 * @return
	 */
	private static Map<String, StateInfo> calculateStates(JsBuilderDef def) {

		Map<String, StateInfo> states = new LinkedHashMap<String, StateInfo>();

		int stateCount = 0;

		// calculate first state
		StringBuilder sb = new StringBuilder();
		StateInfo firstState = new StateInfo(OrderedState.ORDERED, def
				.getType().getName(), stateCount);
		addStateSections(null, def, 0, firstState, sb);
		addState(states, sb.toString(), firstState);
		stateCount++;
		Map<String, String> temp = new HashMap<String, String>();

		// calculate ordered states
		for (int i = 0; i < def.getSections().size(); i++) {
			Section sec = def.getSections().get(i);
			if (!sec.isAnyOrder() && sec.getMax() == 1) {

				StateInfo info2 = new StateInfo(OrderedState.ORDERED, "",
						stateCount);
				StringBuilder sb2 = new StringBuilder();
				addStateSections(sec, def, i, info2, sb2);
				addState(states, sb2.toString(), info2);
				info2.m_name = sb2.toString();
				stateCount++;
			} else if (sec.isAnyOrder() && sec.getMax() == 1) {

				// calculate unordered states
				if (temp.get(sec.getMtd()) != null) {
					continue;
				}

				List<Section> grps = def.getAnyOrderGroupMaxOne(sec.getMtd());
				// add to already Processed check
				for (Section s : grps) {
					temp.put(s.getMtd(), null);
				}

				int grpSize = grps.size();

				if (grpSize == 1) {
					StateInfo info3 = new StateInfo(OrderedState.UNORDERED, "",
							stateCount);

					List<Section> sections = def.getSections();
					StringBuilder key = new StringBuilder();
					boolean startNow = false;
					for (Section s : sections) {
						if(s.getMtd().equals(sec.getMtd())){
							startNow = true;
							continue;
						}
						
						if(startNow){
							key.append(s.getMtd()+",");
							SectionInfo secInfo = new SectionInfo();
							secInfo.m_section = s;
							info3.m_sections.add(secInfo);
						}

					}
					info3.m_name = key.toString();
					addState(states, key.toString(), info3);
					stateCount++;
					continue;
				}

				int[] indices;
				int r = grpSize - 1;
				while (r > 0) {
					CombinationGenerator gen = new CombinationGenerator(grps
							.size(), r);
					StringBuffer combination;
					while (gen.hasMore()) {
						combination = new StringBuffer();
						indices = gen.getNext();
						StateInfo info3 = new StateInfo(OrderedState.UNORDERED,
								"", stateCount);
						for (int j = 0; j < indices.length; j++) {

							SectionInfo sectionInfo = new SectionInfo();
							Section section = grps.get(indices[j]);
							sectionInfo.m_section = section;
							info3.m_sections.add(sectionInfo);
							combination.append(section.getMtd() + ",");

							if (j == indices.length - 1) {
								List<Section> sections = def
										.getNextOrderSections(section.getMtd());
								List<Section> samegrp = def.getAnyOrderGroup(section.getMtd());
								for (Section s : sections) {
									if(s.isAnyOrder() && isInSameGrp(s, samegrp)){
										continue;
									}
									
									SectionInfo secInfo = new SectionInfo();
									secInfo.m_section = s;
									info3.m_sections.add(secInfo);
									combination.append(s.getMtd() + ",");
								}

							}

						}

						String key = combination.toString().substring(0,
								combination.toString().lastIndexOf(','));
						info3.m_name = key;
						if(states.get(key)==null){
							states.put(key, info3);
							stateCount++;
						}
//						System.out.println("combination = " + key);
					}
					r--;
				}

			}

		}

		// add end state
		ENDSTATE.m_id = stateCount;
		states.put("END", ENDSTATE);

		calculateNextState(states, def);
		
		return states;

	}

	private static void addState(Map<String, StateInfo> states,
			String stateName, StateInfo firstState) {
		if(states.get(stateName)==null){
			states.put(stateName, firstState);
		}
	}

	private static boolean isInSameGrp(Section s, List<Section> samegrp) {
		for(Section sec:samegrp){
			if(sec.getMtd().equals(s.getMtd())){
				return true;
			}
		}
		return false;
	}

	private static void addStateSections(Section sec, JsBuilderDef def, int i,
			StateInfo info, StringBuilder sb) {
		for (int secId = i; secId < def.getSections().size(); secId++) {
			SectionInfo sinfo = new SectionInfo();
			Section section = def.getSections().get(secId);
//			if(sec !=null && section.equals(sec)){
//				continue;
//			}
			
			// if(section.isAnyOrder()==true){
			sb.append(section.getMtd()+ ",");
			sinfo.m_section = section;
			info.m_sections.add(sinfo);
			// }
		}
	}

	/*
	 * row 1 states col 1 section names col 2
	 */

	private static void printStates(Map<String, StateInfo> states) {
	
//		while (iter.hasNext()) {
//			StateInfo si = iter.next();
//			String key = si.m_name;
////			System.out.print(key + "\t id" + si.m_id + "\n\t sections: ");
//			for (SectionInfo sinfo : si.m_sections) {
////				System.out.print(sinfo.m_section.getMtd() + ",");
//			}
////			System.out.println();
//		}

		System.out.println("&&&&&&&&&&&&&&&&&&&&&&"); //KEEPME
		System.out.print("section\t"); //KEEPME
		Iterator<StateInfo> iter = states.values().iterator();
		int i = 0;
		StateInfo firstKey = null;
		while (iter.hasNext()) {
			StateInfo si = iter.next();
			if (i == 0) {
				firstKey = si;
			}
			String key = si.m_name;
			if (key.length() >= 3) {
				key = key.substring(0, 3);
				key = key + "(" + si.m_id + ")";
			}
			System.out.print(key + "\t"); //KEEPME
			i++;
		}
		System.out.println("");	//KEEPME
		// print section name and return value from each state
		for (SectionInfo info : firstKey.m_sections) {
			Section section = info.m_section;
			int max = section.getMtd().length();
			System.out.print(section.getMtd().substring(0, max>4?4:max) + "\t"); //KEEPME
			for (StateInfo state : states.values()) {
				SectionInfo section2 = state.getSection(section.getMtd());
				if (section2 != null && section2.m_nextState != null) {
					System.out.print(section2.m_nextState.m_id + "\t"); //KEEPME
				} else if (section2 == null) {
					System.out.print("no def" + "\t"); //KEEPME

				} else {
					System.out.print("unknown" + "\t"); //KEEPME

				}
			}
			System.out.println(); //KEEPME

		}

		System.out.println("\n&&&&&&&&&&&&&&&&&&&&&&"); //KEEPME
	}

	static class StateInfo {
		public int m_id;

		enum OrderedState {
			ORDERED, UNORDERED
		}

		String m_name;
		List<SectionInfo> m_sections = new ArrayList<SectionInfo>();
		OrderedState m_orderedState;

		public StateInfo(OrderedState state, String name, int id) {
			m_orderedState = state;
			m_name = name;
			m_id = id;
		}

		public SectionInfo getSection(String s) {
			for (SectionInfo sec : m_sections) {
				if (s.equals(sec.m_section.getMtd())) {
					return sec;
				}
			}
			return null;
		}

		public OrderedState getOrderedState() {
			return m_orderedState;
		}

	}

	static class SectionInfo {
		Section m_section;
		StateInfo m_nextState;

	}

	private static JstType getType(final List<JsTypingMeta> list) {
		JstType t=null;
		for(JsTypingMeta m: list){
			JstType typeFromCache = JstCache.getInstance().getType(m.getType());
			
			if(typeFromCache==null && m instanceof JsAttributed){
				JsAttributed jsa = (JsAttributed)m;
				
				typeFromCache = JstCache.getInstance().getType(jsa.getAttributor().getType() +"." + jsa.getName());
			}
			
	
			if (typeFromCache != null) {
				return typeFromCache;
			}
			
			
			
			System.out.println("creating empty type =" + list); //KEEPME
			t = JstFactory.getInstance().createJstType(m.getType(), true);
		}

		return t;
	}
	private static JstType getType(final String type) {
	
			JstType typeFromCache = JstCache.getInstance().getType(type);
			
			if (typeFromCache != null) {
				return typeFromCache;
			}
			
			System.out.println("creating empty type =" + type); //KEEPME
			return JstFactory.getInstance().createJstType(type, true);

	}

	private static void addMethod(JstType t, JstType rtnType,
			List<JstMethod> methods) {
		if(methods==null){
			return;
		}
		// assuming all comments are instance methods not static
		if (methods.size() > 1 || hasOptionalArgs(methods)) {
			handleOverloading(t, rtnType, methods);
		} else {
			JstMethod jstMethod = methods.get(0);
			JstMethod copy = createNewJstMethod(jstMethod);

			copy.setRtnType(rtnType);
			t.addMethod(copy);
		}
	}

	private static boolean hasOptionalArgs(List<JstMethod> methods) {
		for(JstMethod m:methods){
			for(JstArg a: m.getArgs()){
				if(a.isOptional()){
					return true;
				}
			}
		}
		return false;
	}

	private static void handleOverloading(JstType t, JstType rtnType,
			List<JstMethod> methods) {
		// TODO Auto-generated method stub
		IJstMethod first = methods.get(0);
		// determine aggregated args
		// List<JstArg> aggregatedArgs= new ArrayList();
		// for(IJstMethod mtd:methods){
		// mtd.getArgs().get(0);
		// }
		
		JstArg[] argArr = first.getArgs().toArray(new JstArg[0]);
		JstMethod dispatcher = createNewJstMethod(first, argArr);
		if (rtnType != null) {
			dispatcher.setRtnType(rtnType);
		}

		for (IJstMethod mtd : methods) {
			// TODO overloading
			if(first.getArgs().size()>0 && first.getArgs().get(0).isOptional()){
				argArr = new JstArg[0];
			}
			
			JstMethod copy = createNewJstMethod(mtd, argArr);
			if (rtnType != null) {
			
				copy.setRtnType(rtnType);
//				copy.setParent(t);
			}
			

			dispatcher.addOverloaded(copy);
			// do not add overloaded method in the current type
			// node tree
			copy.setParent(dispatcher, false);

		}

		TranslateHelper.fixArgsForDispatchMethod(dispatcher);

		t.addMethod(dispatcher);

	}

	private static JstMethod createNewJstMethod(IJstMethod first) {
		JstArg[] argArr = new JstArg[first.getArgs().size()];
		return createNewJstMethod(first, argArr);
	}
	private static JstMethod createNewJstMethod(IJstMethod first, JstArg[] argArr) {
		JstMethod dispatcher = new JstMethod(first.getName(), first
				.getModifiers(), first.getRtnType(), first.getArgs().toArray(
				argArr));
		return dispatcher;
	}

	public static Collection<IJsCommentMeta> collectComments(URL f)
			throws IOException {

		Map<?, ?> settings = Collections.EMPTY_MAP;

		String mysource = VjoParser.getContent(f);
		char[] charsource = mysource.toCharArray();
		// final String mysource = convertHTMLCommentsToJsComments(mysource);

		String encoding = null;
		AstCompilationResult astResult = SyntaxTreeFactory2
				.createASTCompilationResult(settings, charsource, f.toExternalForm(),
						encoding);
		JstSourceUtil jstSourceUtil = new JstSourceUtil(charsource);

		ErrorReporter reporter = new DefaultErrorReporter();
		CommentCollector cc = new CommentCollector();
		cc.handle(astResult.getCompilationUnitDeclaration(), reporter,
				jstSourceUtil);

		if (reporter.getErrors().size() > 0) {
			throw new DsfRuntimeException("file has syntax errors");
		}

		return cc.getCommentAllMeta();
	}

	public static Map<String, List<JstMethod>> createJstMethodMap(
			JsBuilderDef def, Collection<IJsCommentMeta> list, IFilter filter) {
		if (list.size() == 0) {
			return Collections.EMPTY_MAP;
		}

		Map<String, List<JstMethod>> map = new HashMap<String, List<JstMethod>>();

		processMtds(def, list, map, filter);
		return map;
	}

	private static void processMtds(JsBuilderDef def, Collection<IJsCommentMeta> list,
			Map<String, List<JstMethod>> map, IFilter filter) {
		for (IJsCommentMeta meta : list) {
			JstMethod mtd = new JstMethod(meta.getName());
			TranslateHelper.setModifiersFromMeta(meta, mtd.getModifiers());
			mtd.setHasJsAnnotation(true);
			JsFuncType funcType = (JsFuncType)meta.getTyping();
			mtd.setRtnType(getType(funcType.getReturnType().getType()));
			processArgs(meta, mtd);

			if (filter != null && filter.filter(def, mtd)) {
				continue;
			}
			if (map.get(meta.getName()) == null) {
				List<JstMethod> mtdList = new ArrayList<JstMethod>();
				mtdList.add(mtd);
				map.put(meta.getName(), mtdList);
			} else {
				List<JstMethod> mtdList = map.get(meta.getName());
				mtdList.add(mtd);
			}

		}
	}

	private static void processArgs(IJsCommentMeta meta, JstMethod mtd) {
		for (JsParam param : ((JsFuncType)meta.getTyping()).getParams()) {
			JstType type = getType(param.getTypes());
			JstArg arg = new JstArg(type, param.getName(), param.isVariable(),
					param.isOptional());
			mtd.addArg(arg);

		}
	}

	static class SectionTuple {

		private final Section m_section;
		private final int m_id;
		private final JstType m_type;

		public SectionTuple(Section section, int id, JstType t) {
			m_section = section;
			m_id = id;
			m_type = t;
		}

		public Section getSection() {
			return m_section;
		}

		public int getId() {
			return m_id;
		}

		public JstType getType() {
			return m_type;
		}

	}

}
