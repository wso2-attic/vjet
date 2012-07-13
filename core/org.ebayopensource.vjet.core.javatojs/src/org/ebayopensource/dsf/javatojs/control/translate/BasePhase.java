/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.control.translate;

import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;

import org.ebayopensource.dsf.javatojs.parse.BaseTypeVisitor;
import org.ebayopensource.dsf.javatojs.trace.ITranslateTracer;
import org.ebayopensource.dsf.javatojs.trace.TranslateError;
import org.ebayopensource.dsf.javatojs.trace.TranslateMsgId;
import org.ebayopensource.dsf.javatojs.trace.TranslateTraceMgr;
import org.ebayopensource.dsf.javatojs.trace.TranslateTracer;
import org.ebayopensource.dsf.javatojs.translate.AstBinding;
import org.ebayopensource.dsf.javatojs.translate.DataTypeTranslator;
import org.ebayopensource.dsf.javatojs.translate.PackageTranslator;
import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.javatojs.translate.TranslateHelper;
import org.ebayopensource.dsf.javatojs.translate.TranslateInfo;
import org.ebayopensource.dsf.javatojs.translate.TranslationMode;
import org.ebayopensource.dsf.javatojs.translate.VjoTranslateHelper;
import org.ebayopensource.dsf.javatojs.translate.config.PackageMapping;
import org.ebayopensource.dsf.javatojs.util.AstBindingHelper;
import org.ebayopensource.dsf.javatojs.util.JavaToJsHelper;
import org.ebayopensource.dsf.jst.declaration.JstArray;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.util.DataTypeHelper;
import org.ebayopensource.dsf.util.JavaSourceLocator;
import org.ebayopensource.dsf.logger.LogLevel;
import org.ebayopensource.vjo.lib.LibManager;

public class BasePhase {

	private TranslateCtx m_ctx;
	private TranslationMode m_mode;
	private List<JstType> m_startingTypes;
	private List<JstType> m_dependentTypes;
	private List<StringWriter> m_writers;
	private List<TranslateError> m_directErrors;
	private Map<JstType,List<Throwable>> m_exceptions;
	
	private PackageMapping m_pkgMapping;
	private DataTypeTranslator m_dataTypeTranslator;
	
	//
	// Constructor
	//
	protected BasePhase(TranslationMode mode){
		assert mode != null : "mode cannot be null";
		m_ctx = TranslateCtx.ctx();
		m_mode = mode;
		
		m_pkgMapping = m_ctx.getConfig().getPackageMapping();
		m_dataTypeTranslator = m_ctx.getProvider().getDataTypeTranslator();
		
		if (m_ctx.isTraceEnabled()){
			m_writers = new ArrayList<StringWriter>();
		}
	}
	
	//
	// API
	//
	public List<JstType> getStartingTypes() {
		if (m_startingTypes == null){
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(m_startingTypes);
	}
	
	public List<TranslateError> getErrors(){
		if (m_directErrors == null){
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(m_directErrors);
	}
	
	public Map<JstType,List<Throwable>> getExceptions(){
		if (m_exceptions == null){
			return Collections.emptyMap();
		}
		return Collections.unmodifiableMap(m_exceptions);
	}
	
	//
	// Protected
	//
	protected TranslateCtx getCtx() {
		return m_ctx;
	}

	protected TranslationMode getMode() {
		return m_mode;
	}

	protected List<JstType> getDependentTypes() {
		if (m_dependentTypes == null){
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(m_dependentTypes);
	}

	protected void setStartingTypes(List<JstType> types){
		m_startingTypes = types;
	}

	protected void addStartingType(JstType type) {
		if (type == null){
			return;
		}
		if (m_startingTypes == null){
			m_startingTypes = new ArrayList<JstType>();
		}
		else if (m_startingTypes.contains(type)){
			return;
		}
		m_startingTypes.add(type);
	}

	protected void addDependency(BaseTypeVisitor visitor){
		JstType targetType = visitor.getType();
		if (targetType == null) return;
			
		// Process dependencies
		List<JstType> dependents = visitor.getDependency();
		if (dependents != null){
			for (JstType t: dependents){
				addDependentType(targetType, t);
			}
		}
		
		// Process unknown types
		TranslateInfo tInfo = m_ctx.getTranslateInfo(targetType);
		Map<String,JstType> unknownList = tInfo.getUnknownTypes();
		if (unknownList.isEmpty()){
			return;
		}
		
		List<String> importedPkgs = new ArrayList<String>();
		if (targetType.getPackage() != null){
			importedPkgs.add(m_ctx.getConfig().getPackageMapping().mapFrom(targetType.getPackage().getName()));
		}
		importedPkgs.addAll(tInfo.getImportedPkgs());
		
		PackageTranslator pkgTranslator = m_ctx.getProvider().getPackageTranslator();
		PackageMapping pkgMapping = m_ctx.getConfig().getPackageMapping();
		String clsName;
		JstType dependent;
		boolean resolved;
		Map<String,JstType> unresolvedList = new HashMap<String,JstType>();
		
		for (Entry<String,JstType> entry: unknownList.entrySet()){
			resolved = false;
			for (String pkg: importedPkgs){
				clsName = TranslateHelper.Type.resolveImplicitImport(pkg + "." + entry.getKey(), targetType);
				if (clsName == null){
					continue;
				}
				if (JstCache.getInstance().getType(pkgMapping.mapTo(clsName)) != null){
					resolved = true;
					break;
				}
				dependent = entry.getValue();
				addDependentType(targetType, dependent);
				if (dependent.getPackage() == null){
					dependent.setPackage(pkgTranslator.getPackage(pkg));
				}
				resolved = true;
				break;
			}
			if (!resolved){
				unresolvedList.put(entry.getKey(), entry.getValue());
			}
		}
		
		try {
			processUnresolved(targetType, unresolvedList);
		} catch (Exception e) {
			System.out.println("Problem while processing unresolved");
			e.printStackTrace();
		} 
	}
	
	protected void addError(TranslateError error){
		if (m_directErrors == null){
			m_directErrors = new ArrayList<TranslateError>();
		}
		m_directErrors.add(error);
	}
	
	protected void setExceptions(JstType type, List<Throwable> exceptions){
		if (exceptions.isEmpty()){
			return;
		}
		if (m_exceptions == null){
			m_exceptions = new LinkedHashMap<JstType,List<Throwable>>();
		}
		m_exceptions.put(type, exceptions);
	}
	
	protected ITranslateTracer getTracer(){
		return m_ctx.getTraceManager().getTracer();
	}
	
	protected ITranslateTracer getTracer(JstType type){
		if (m_ctx.isTraceEnabled()){
			StringWriter writer = new StringWriter();
			m_writers.add(writer);
			return m_ctx.getTraceManager().getTracer(writer);
		}
		else {
			return TranslateTracer.NO_OP;
		}
	}
	
	protected void mergeTraces(){
		if (!m_ctx.isTraceEnabled() || m_writers == null){
			return;
		}
		
		TranslateTraceMgr mgr = m_ctx.getTraceManager();
		for (StringWriter w: m_writers){
			mgr.append(w.toString());
		}
	}
	
	//
	// Private
	//
	private void addDependentType(final JstType targetType, final JstType dependentType) {
		if (dependentType == null || dependentType instanceof JstArray){
			return;
		}

		if (m_ctx.isJsType(dependentType)
				|| m_ctx.isMappedToJS(dependentType)
				|| m_ctx.isExcluded(dependentType)){
			return;
		}

		JstType jstType = dependentType;	
		String simpleName = jstType.getSimpleName();
		String fullName = jstType.getName();
		
		TranslateInfo targetTypeInfo = m_ctx.getTranslateInfo(targetType);
		TranslateInfo dependentTypeInfo = m_ctx.getTranslateInfo(dependentType);
		
		// Get original source name if mapped
		PackageMapping pkgMapping = m_ctx.getConfig().getPackageMapping();
		String srcName = pkgMapping.mapFrom(fullName);
		if (srcName == null){
			addError(new TranslateError(TranslateMsgId.SRC_NAME_IS_NULL, "srcName is null for '" + fullName + "'"));
			return;
		}
		
		DataTypeTranslator dataTypeTranslator = m_ctx.getProvider().getDataTypeTranslator();
		
		// Exclude lib, native and custom types
		if (LibManager.getInstance().hasType(fullName)
			|| DataTypeHelper.isPrimitiveType(simpleName)
			|| DataTypeHelper.isInJDK(fullName)
			|| VjoTranslateHelper.isVjoNativeType(fullName)
			|| VjoTranslateHelper.isVjoJdkType(fullName)
			|| m_ctx.isMappedToVJO(dependentType)
		){
			
			if (!addImport(targetType, jstType)){
				dataTypeTranslator.addImport(dependentType, targetType, fullName);
			}
			return;
		}
		
		// Exclude visited types
		TranslationMode mode = getMode();
		if (mode.hasDeclaration() && dependentTypeInfo.getMode().hasDeclaration()
				|| mode.hasDependency() && dependentTypeInfo.getMode().hasDependency()){
			
			addImport(targetType, jstType);
			
			return;
		}

		// Get source class name
		String clsName = TranslateHelper.Type.resolveEmbeddedType(srcName, targetType);
		if (clsName == null){
			clsName = TranslateHelper.Type.resolveImplicitImport(srcName, targetType);
		}
		if (clsName != null){
			if (clsName.equals(pkgMapping.mapFrom(targetType.getName()))){
				return;
			}
			
			if (m_ctx.isExcludedType(clsName)){
				targetTypeInfo.setType(dependentType.getSimpleName(), dependentType);
				//addError(new TranslateError(TranslateMsgId.EXCLUDED_TYPE, srcName + " excluded in policy"));
				return;
			}
		}
		else {
			if (m_ctx.isExcludedType(srcName)){
				targetTypeInfo.setType(dependentType.getSimpleName(), dependentType);
				return;
			}
			else {
				addError(new TranslateError(LogLevel.WARN, TranslateMsgId.SRC_NOT_FOUND, 
					"Source not found for '" + srcName + "' when translating " + targetType.getName()));
				return;
			}
		}

		String jstName = pkgMapping.mapTo(clsName);
		String pkg = TranslateHelper.getPkgName(jstName);

		if (clsName.equals(srcName)){
			JstType cachedType = JstCache.getInstance().addType(jstType, true);
			if (cachedType != jstType){
				targetTypeInfo.setClearTypeRefs(true);
				jstType = cachedType;
			}
			addImport(targetType, jstType);
		}
		else if (srcName.indexOf(".") < 0){
			jstType.setPackage(new JstPackage(pkg));
			JstType cachedType = JstCache.getInstance().addType(jstType, true);
			if (cachedType != jstType){
				targetTypeInfo.setClearTypeRefs(true);
				jstType = cachedType;
			}
			addImport(targetType, jstType);
		}
		else {
			jstType = JstCache.getInstance().getType(jstName, true);
			
			if (targetTypeInfo.getType(simpleName, false) != null){
				targetTypeInfo.removeType(simpleName);
			}
				
			if (targetTypeInfo.getType(srcName, false) != null){
				targetTypeInfo.removeType(srcName);
			}
			
			dataTypeTranslator.addImport(jstType, targetType, jstType.getSimpleName());
			targetTypeInfo.setClearTypeRefs(true);
		}
		
		// Add jstType as dependent
		if (m_dependentTypes == null){
			m_dependentTypes = new ArrayList<JstType>();
		}
		else if (m_dependentTypes.contains(jstType)){
			return;
		}
		m_dependentTypes.add(jstType);
	}
	
	private void processUnresolved(final JstType targetType, final Map<String,JstType> unResolvedTypes) throws MalformedURLException, IOException, URISyntaxException {
		if (unResolvedTypes.isEmpty()){
			return;
		}
		
		AstBinding astBinging = AstBindingHelper.getAstSrcBinding(targetType);
		if (astBinging == null){
			return;
		}
		
		URL pkgPath = astBinging.getPkgPath();
		String pkgName = astBinging.getPkgName();
		
		List<URL> fileList = new ArrayList<URL>();
		JavaToJsHelper.getDirectFiles(pkgPath, fileList,m_ctx.getConfig().getFileFilter());
		String filePath; 
		String clsName;
		
		// Collect files in the same package exclude target
		List<String> typesInSamePackage = new ArrayList<String>();
		for (URL file: fileList){
			filePath = file.getPath();
			int index = filePath.lastIndexOf("\\");
			if (index < 0){
				index = filePath.lastIndexOf("/");
			}
			clsName = filePath.substring(index+1);
			index = clsName.indexOf(".");
			if (index > 0){
				clsName = clsName.substring(0, index);
			}
			if (clsName.equals(targetType.getSimpleName())){
				continue;
			}
			typesInSamePackage.add(pkgName == null ? clsName : pkgName + "." + clsName);
		}
		
		PackageMapping pkgMapping = m_ctx.getConfig().getPackageMapping();
		DataTypeTranslator dataTypeTranslator = m_ctx.getProvider().getDataTypeTranslator();
		
		CompilationUnit cu;
		AbstractTypeDeclaration astType;
		JstType type;
		String typeName;
		boolean isResolved;
		
		// For each unresolved type
		for (Entry<String,JstType> entry: unResolvedTypes.entrySet()){
			typeName = entry.getKey();
			type = entry.getValue();
			isResolved = false;
			for (String srcName: typesInSamePackage){
				URL srcUrl = JavaSourceLocator.getInstance().getSourceUrl(srcName);
				String src = JavaSourceLocator.getInstance().getSource(srcUrl);
				if (src == null){
					addError(new TranslateError(TranslateMsgId.INVALID_PATH, "Source is null for " + srcName));
					continue;
				}
				cu = JavaToJsHelper.toAst(src); 
				// Check sibling types in other files of the same package
				for (Object o: cu.types()){
					if (o instanceof AbstractTypeDeclaration) {
						astType = (AbstractTypeDeclaration)o;
						if (astType.getName().toString().equals(typeName)){
            				type.setPackage(targetType.getPackage());
	            			dataTypeTranslator.addImport(type, targetType, type.getSimpleName());
	            			String jstName = pkgMapping.mapTo(srcName);
	            			JstType dType = JstCache.getInstance().getType(jstName, true);
	            			dType.addSiblingType(type);
	            			addDependentType(targetType, dType);
	            			isResolved = true;
	            			break;
						}
						for (Object bodyObj: astType.bodyDeclarations()){
							if (bodyObj instanceof AbstractTypeDeclaration) {
								astType = (AbstractTypeDeclaration)bodyObj;
		            			if (containType(astType, typeName)){
			            			String jstName = pkgMapping.mapTo(srcName);
			            			JstType dType = JstCache.getInstance().getType(jstName, true);
			            			if (!dType.hasInnerType(type.getSimpleName())){
				            			dType.addInnerType(type);
			            			}
				            		addDependentType(targetType, dType);
				            		isResolved = true;
				            		break;
								}
				        	} 
						}
	            	} 
				}
				if (isResolved){
					break;
				}
			}
		}
	}
	
	private boolean addImport(final JstType targetType, final JstType dependentType){
		TranslateInfo targetTypeInfo = m_ctx.getTranslateInfo(targetType);
		
		String simpleName = dependentType.getSimpleName();
		String fullName = dependentType.getName();
		String srcName = m_pkgMapping.mapFrom(fullName);
		
		if (targetTypeInfo.getType(srcName, false) != null){
			m_dataTypeTranslator.addImport(dependentType, targetType, fullName);
			return true;
		}
		else if (targetTypeInfo.getType(simpleName, false) != null){
			m_dataTypeTranslator.addImport(dependentType, targetType, simpleName);
			return true;
		}
		
		return false;
	}
	
	private boolean containType(final AbstractTypeDeclaration astType, final String typeName){

		if (astType.getName().toString().equals(typeName)){
			return true;
		}
		for (Object bodyObj: astType.bodyDeclarations()){
			if (bodyObj instanceof AbstractTypeDeclaration) {
				if (containType((AbstractTypeDeclaration)bodyObj, typeName)){
					return true;
				}
        	} 
		}
		return false;
	}
}
