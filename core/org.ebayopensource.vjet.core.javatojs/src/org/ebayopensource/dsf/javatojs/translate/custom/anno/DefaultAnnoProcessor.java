/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.custom.anno;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import org.ebayopensource.dsf.javatojs.anno.ACustomizedAs;
import org.ebayopensource.dsf.javatojs.anno.AExclude;
import org.ebayopensource.dsf.javatojs.anno.AForceFullyQualified;
import org.ebayopensource.dsf.javatojs.anno.AJavaOnly;
import org.ebayopensource.dsf.javatojs.anno.AJsProxy;
import org.ebayopensource.dsf.javatojs.anno.AMappedToJS;
import org.ebayopensource.dsf.javatojs.anno.AMappedToVJO;
import org.ebayopensource.dsf.javatojs.anno.AProperty;
import org.ebayopensource.dsf.javatojs.anno.ARename;
import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.javatojs.translate.TranslateHelper;
import org.ebayopensource.dsf.javatojs.translate.TranslateInfo;
import org.ebayopensource.dsf.javatojs.translate.config.MethodKey;
import org.ebayopensource.dsf.javatojs.translate.custom.CustomAttr;
import org.ebayopensource.dsf.javatojs.translate.custom.CustomInfo;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.BaseCustomMetaProvider;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.CustomField;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.CustomMethod;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.CustomType;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.ICustomMetaProvider;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstType;

public class DefaultAnnoProcessor extends BaseAnnoProcessor 
	implements IAnnoProcessor {
	
	private ICustomMetaProvider m_metaProvider = new MetaProvider();
	
	//
	// Satisfy IAnnoProcessor
	//
	@Override
	public CustomInfo process(final ASTNode astNode, final JstType jstType){

		List<Annotation> annos = getAnnotations(astNode);
		if (annos.isEmpty()){
			return null;
		}
		
		CustomInfo cInfo = new CustomInfo();
		boolean forceFullyQualified = false;

		String annoName;
		for (Annotation anno: annos){
		
			annoName = anno.getTypeName().toString();
			
			if (annoName.equals(AExclude.class.getSimpleName())) {				
				cInfo.setAttr(CustomAttr.EXCLUDED);				
			}
			else if (annoName.equals(AJavaOnly.class.getSimpleName())) {
				cInfo.setAttr(CustomAttr.JAVA_ONLY);					
			}
			else if (annoName.equals(AJsProxy.class.getSimpleName())) {
				cInfo.setAttr(CustomAttr.JS_PROXY);		
			}
			else if (annoName.equals(AMappedToJS.class.getSimpleName())) {
				cInfo.setAttr(CustomAttr.MAPPED_TO_JS);		
				process(astNode, jstType, anno, cInfo);
			}
			else if (annoName.equals(AMappedToVJO.class.getSimpleName())) {
				cInfo.setAttr(CustomAttr.MAPPED_TO_VJO);
				process(astNode, jstType, anno, cInfo);
			}
			else if (annoName.equals(ACustomizedAs.class.getSimpleName())) {
				MemberValuePair mvPair;
				for (Object pair: ((NormalAnnotation)anno).values()){
					if (pair instanceof MemberValuePair){
						mvPair = (MemberValuePair)pair;
						if (mvPair.getName().toString().equals("type")){
							Type asAstType = ((TypeLiteral)mvPair.getValue()).getType();
							IJstType asJstType = TranslateCtx.ctx().getProvider().getDataTypeTranslator()
								.processType(asAstType, jstType);
							cInfo.setAsType(asJstType);
						}
						else if (mvPair.getName().toString().equals("name")){
							cInfo.setAsName(((StringLiteral)mvPair.getValue()).getLiteralValue());
						}
					}
				}
			}
			else if (annoName.equals(ARename.class.getSimpleName())){
				process(astNode, jstType, anno, cInfo);
			}
			else if (annoName.equals(AProperty.class.getSimpleName())){
				if (astNode instanceof MethodDeclaration){
					genMetaForPty((MethodDeclaration)astNode, jstType, anno);
				}
			}
			else if (annoName.equals(AForceFullyQualified.class.getSimpleName())){
				if (astNode instanceof MethodDeclaration){
					forceFullyQualified = TranslateHelper.isStatic(
						((MethodDeclaration)astNode).modifiers());
				}
				
			}
		}
		
		if (forceFullyQualified){
			cInfo.setForceFullyQualify(true);
		}
		
		return cInfo;
	}
	
	//
	// API
	//
	public ICustomMetaProvider getMetaProvider(){
		return m_metaProvider;
	}
	
	//
	// Private 
	//
	private List<Annotation> getAnnotations(ASTNode astNode){
		
		List<?> modifiers = null;
		
		if (astNode instanceof AbstractTypeDeclaration){
			modifiers = ((AbstractTypeDeclaration)astNode).modifiers();
		}
		else if (astNode instanceof FieldDeclaration){
			modifiers = ((FieldDeclaration)astNode).modifiers();
		}
		else if (astNode instanceof EnumConstantDeclaration){
			modifiers = ((EnumConstantDeclaration)astNode).modifiers();
		}
		else if (astNode instanceof MethodDeclaration){
			modifiers = ((MethodDeclaration)astNode).modifiers();
		}
		
		if (modifiers == null || modifiers.isEmpty()){
			return Collections.emptyList();
		}
		
		List<Annotation> annos = new ArrayList<Annotation>();
		for (Object m: modifiers){
			if (m instanceof Annotation){ 
				annos.add((Annotation)m);
			}
		}
		
		return annos;
	}
	
	private static final String ANNO_NAME = "name";
	private static final String ANNO_VALUE = "value";
	private List<MemberValuePair> getAnnoMemberPairs(final Annotation anno){
		
		if (!(anno instanceof NormalAnnotation)){
			return Collections.emptyList();
		}
		
		NormalAnnotation nAnno = (NormalAnnotation)anno;
		MemberValuePair mvPair;
		String name;
		List<MemberValuePair> annoList = new ArrayList<MemberValuePair>();
		
		for (Object pair: nAnno.values()){
			if (pair instanceof MemberValuePair){
				mvPair = (MemberValuePair)pair;
				name = getName(mvPair);
				if (ANNO_NAME.equals(name) || ANNO_VALUE.equals(name)){
					annoList.add(mvPair);
				}
			}
		}
		
		return annoList;
	}
	
	private String getName(MemberValuePair pair){
		return ((MemberValuePair)pair).getName().toString();
	}
	
	private String getValue(Expression value, ASTNode astNode, final JstType jstType){
		if (value == null){
			return null;
		}
		String v = null;
		if (value instanceof QualifiedName){
			v = getValue((QualifiedName)value, astNode, jstType);
		}
		else if (value instanceof StringLiteral){
			v = ((StringLiteral)value).getLiteralValue();
		}
		else {
			v = value.toString();
		}
		return v;
	}
	
	private String getValue(QualifiedName name, ASTNode astNode, final JstType jstType){
		
		TranslateInfo tInfo = TranslateCtx.ctx().getTranslateInfo(jstType);
		
		String typeName = name.getQualifier().getFullyQualifiedName();
		String fullName = tInfo.getImported(typeName);
		try {
			Class<?> toClass = Class.forName(fullName);
			Field field = toClass.getField(name.getName().toString());
			return (String)field.get(toClass);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	private void process(final ASTNode astNode, final JstType jstType, final Annotation anno, final CustomInfo cInfo){

		CustomType cType = getCustomType(jstType);
		
		if (astNode instanceof AbstractTypeDeclaration){
			process((AbstractTypeDeclaration)astNode, jstType, anno, cType, cInfo);
		}
		else if (astNode instanceof FieldDeclaration){
			process((FieldDeclaration)astNode, jstType, anno, cType, cInfo);
		}
		else if (astNode instanceof EnumConstantDeclaration){
			// TODO
		}
		else if (astNode instanceof MethodDeclaration){
			process((MethodDeclaration)astNode, jstType, anno, cType, cInfo);
		}
	}
	
	private void process(AbstractTypeDeclaration astNode, 
			final JstType jstType, 
			final Annotation anno, 
			final CustomType cType, 
			final CustomInfo cInfo){
		String name;
		String value = null;
		if (anno instanceof NormalAnnotation){
			List<MemberValuePair> annos = getAnnoMemberPairs(anno);
			if (annos.size() > 0){
				for (MemberValuePair pair: annos){
					name = getName(pair);
					if (ANNO_NAME.equals(name) || ANNO_VALUE.equals(name)){
						if (pair.getValue() instanceof QualifiedName){
							value = getValue((QualifiedName)pair.getValue(), astNode, jstType);
						}
						else {
							value = getValue(pair.getValue(), astNode, jstType);
						}
						break;
					}
				}
				cInfo.setName(value);
			}
		}
		else if(anno instanceof SingleMemberAnnotation){
			value = getValue(((SingleMemberAnnotation)anno).getValue(), astNode, jstType);
			cInfo.setName(value);
		}
		else {
			value = astNode.getName().toString();
		}
		
		if (value == null){
			return;
		}
		cType.setAttr(cInfo.getAttr());
		cType.setJstName(value);
	}
	
	private void process(FieldDeclaration astNode, 
			final JstType jstType, 
			final Annotation anno, 
			final CustomType cType, 
			final CustomInfo cInfo){
		
		String value = null;
		if (anno instanceof NormalAnnotation){
			List<MemberValuePair> annos = getAnnoMemberPairs(anno);
			String name;
			if (annos.size() > 0){
				for (MemberValuePair pair: annos){
					name = getName(pair);
					if (ANNO_NAME.equals(name) || ANNO_VALUE.equals(name)){
						if (pair.getValue() instanceof QualifiedName){
							value = getValue((QualifiedName)pair.getValue(), astNode, jstType);
						}
						else {
							value = getValue(pair.getValue(), astNode, jstType);
						}
						cInfo.setName(value);
						break;
					}
				}
			}
		}
		else if(anno instanceof SingleMemberAnnotation){
			value = getValue(((SingleMemberAnnotation)anno).getValue(), astNode, jstType);
			cInfo.setName(value);
		}
		
		if (value == null){
			return;
		}
		
		String fName = null;
		String tName = null;
		
		if (value != null){
			cInfo.setName(value);
			int index = value.lastIndexOf(".");
			if (index < 0){
				fName = value;
			}
			else {
				fName = value.substring(index + 1, value.length());
				tName = value.substring(0, index);
			}
		}
		
		VariableDeclarationFragment v;
		String vName;
		CustomField cField;
		for (Object o: astNode.fragments()){
			if (o instanceof VariableDeclarationFragment){
				v = (VariableDeclarationFragment)o;
				vName = v.getName().toString();
				cField = cType.getCustomField(vName);
				if (cField == null){
					cField = new CustomField(vName);
					cType.addCustomField(cField);
				}
				cField.setAttr(cInfo.getAttr());
				cField.setJstName(fName);
				cField.setJstOwnerTypeName(tName);
			}
		}
	}
	
	private void process(MethodDeclaration astNode, 
			final JstType jstType, 
			final Annotation anno, 
			final CustomType cType,
			final CustomInfo cInfo){
		MethodKey mtdKey = MethodKey.genMethodKey(astNode);
		CustomMethod cMtd = cType.getCustomMethod(mtdKey);
		if (cMtd == null){
			cMtd = new CustomMethod(mtdKey);
			cType.addCustomMethod(cMtd);
		}
		
		String name;
		String value = null;
		if (anno instanceof NormalAnnotation){
			List<MemberValuePair> annos = getAnnoMemberPairs(anno);
			if (annos.size() > 0){
				for (MemberValuePair pair: annos){
					name = getName(pair);
					if (ANNO_NAME.equals(name) || ANNO_VALUE.equals(name)){
						if (pair.getValue() instanceof QualifiedName){
							value = getValue((QualifiedName)pair.getValue(), astNode, jstType);
						}
						else {
							value = getValue(pair.getValue(), astNode, jstType);
						}
						break;
					}
				}
			}
		}
		else if(anno instanceof SingleMemberAnnotation){
			value = getValue(((SingleMemberAnnotation)anno).getValue(), astNode, jstType);
			cInfo.setName(value);
		}
		else if (cInfo.isMappedToJS()
				|| cInfo.isMappedToVJO()){
			value = astNode.getName().toString();
		}
		
		if (value == null){
			return;
		}
		
		cMtd.setAttr(cInfo.getAttr());
		int index = value.lastIndexOf(".");
		if (index > 0){
			cMtd.setJstOwnerTypeName(value.substring(0, index));
			cMtd.setJstName(value.substring(index+1));
			cInfo.setName(value);
		}
		else {
			cMtd.setJstName(value);
			cInfo.setName(value);
		}
	}
	
	private void genMetaForPty(MethodDeclaration astNode, final JstType jstType, final Annotation anno){
		
		CustomType cType = getCustomType(jstType);
		MethodKey mtdKey = MethodKey.genMethodKey(astNode);
		CustomMethod cMtd = cType.getCustomMethod(mtdKey);
		if (cMtd == null){
			cMtd = new CustomMethod(mtdKey);
			cType.addCustomMethod(cMtd);
		}
		
		cMtd.setIsProperty(true);

		if (anno instanceof NormalAnnotation){
			NormalAnnotation normalAnno = (NormalAnnotation)anno;
			List<?> annos = normalAnno.values();
			MemberValuePair pair = (MemberValuePair)annos.get(0);
			String name = getValue(pair.getValue(), astNode, jstType);
			if (name != null){
				cMtd.setJstName(name);
				return;
			}
		}
		
		String mtdName = cMtd.getJstName();
		if(mtdName.startsWith("get")){
			mtdName = mtdName.substring(3,4).toLowerCase() + mtdName.substring(4, mtdName.length());
			cMtd.setJstName(mtdName);
		}
		else if(mtdName.startsWith("set")){
			mtdName = mtdName.substring(3,4).toLowerCase() + mtdName.substring(4, mtdName.length());
			cMtd.setJstName(mtdName);
		}
	}
	
	private CustomType getCustomType(IJstNode jstNode){
		if (jstNode == null){
			return null;
		}
		MetaProvider metaProvider = (MetaProvider)getMetaProvider();
		IJstType ownerType = jstNode.getOwnerType();
		CustomType cType = metaProvider.getCustomType(ownerType.getName());

		if (cType == null){
			cType = new CustomType(ownerType.getName());
			metaProvider.addCustomType(ownerType.getName(), cType);
		}
		
		return cType;
	}
	
	//
	// Embedded
	//
	private static class MetaProvider extends BaseCustomMetaProvider implements ICustomMetaProvider {
		
	}
}