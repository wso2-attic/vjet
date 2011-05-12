/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.compiler;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ebayopensource.dsf.jst.IJstGlobalFunc;
import org.ebayopensource.dsf.jst.IJstGlobalProp;
import org.ebayopensource.dsf.jst.IJstGlobalVar;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstConstructor;
import org.ebayopensource.dsf.jst.declaration.JstFuncType;
import org.ebayopensource.dsf.jst.declaration.JstFunctionRefType;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.declaration.JstObjectLiteralType;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.declaration.JstProperty;
import org.ebayopensource.dsf.jst.declaration.JstProxyMethod;
import org.ebayopensource.dsf.jst.declaration.JstProxyProperty;
import org.ebayopensource.dsf.jst.declaration.JstVar;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.declaration.TopLevelVarTable;
import org.ebayopensource.dsf.jst.declaration.VarTable;
import org.ebayopensource.dsf.jst.expr.ArrayAccessExpr;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.expr.BoolExpr;
import org.ebayopensource.dsf.jst.expr.FieldAccessExpr;
import org.ebayopensource.dsf.jst.expr.FuncExpr;
import org.ebayopensource.dsf.jst.expr.InfixExpr;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.expr.ParenthesizedExpr;
import org.ebayopensource.dsf.jst.expr.PostfixExpr;
import org.ebayopensource.dsf.jst.expr.PrefixExpr;
import org.ebayopensource.dsf.jst.stmt.CatchStmt;
import org.ebayopensource.dsf.jst.stmt.ExprStmt;
import org.ebayopensource.dsf.jst.stmt.ForStmt;
import org.ebayopensource.dsf.jst.stmt.IfStmt;
import org.ebayopensource.dsf.jst.stmt.RtnStmt;
import org.ebayopensource.dsf.jst.stmt.SwitchStmt;
import org.ebayopensource.dsf.jst.stmt.SwitchStmt.CaseStmt;
import org.ebayopensource.dsf.jst.stmt.ThrowStmt;
import org.ebayopensource.dsf.jst.stmt.TryStmt;
import org.ebayopensource.dsf.jst.stmt.WhileStmt;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.term.NV;
import org.ebayopensource.dsf.jst.term.ObjLiteral;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.IInitializer;
import org.ebayopensource.dsf.jst.token.ILHS;
import org.ebayopensource.dsf.jst.token.ISimpleTerm;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.jst.ts.util.JstTypeDependencyCollector;
import org.ebayopensource.dsf.jst.util.JstTypeHelper;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjet.eclipse.compiler.IJSSourceElementRequestor;
import org.ebayopensource.vjet.eclipse.compiler.IJSSourceElementRequestor.JSMethodInfo;
import org.ebayopensource.vjet.eclipse.compiler.IJSSourceElementRequestor.JSTypeInfo;
import org.ebayopensource.vjet.eclipse.core.ClassFileConstants;
import org.ebayopensource.vjet.eclipse.core.IVjoSourceModule;
import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.ebayopensource.vjet.eclipse.internal.core.util.Util;
import org.ebayopensource.vjo.tool.codecompletion.CodeCompletionUtils;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.mod.ast.Modifiers;
import org.eclipse.dltk.mod.compiler.ISourceElementRequestor;
import org.eclipse.dltk.mod.compiler.env.ISourceModule;
import org.eclipse.dltk.mod.compiler.problem.IProblemReporter;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.IScriptProject;
import org.eclipse.dltk.mod.core.ISourceElementParser;
import org.eclipse.dltk.mod.core.ISourceElementParserExtension;
import org.eclipse.dltk.mod.core.ISourceModuleInfoCache.ISourceModuleInfo;
import org.eclipse.dltk.mod.core.search.indexing.VjoSourceIndexerRequestor;
import org.eclipse.dltk.mod.internal.core.VjoSourceModule;

/**
 * 
 * 
 */
public class VjoSourceElementParser implements ISourceElementParser,
		ISourceElementParserExtension {
	private static final int ZERO_OFFSET = -1;

//	private IProblemReporter fReporter = null;

	private IJSSourceElementRequestor fRequestor = null;

//	private String m_typeName;

	// private IScriptProject m_project;

	private ISourceModule sourceModule;

	private TypeSpaceMgr typeSpaceMgr = TypeSpaceMgr.getInstance();

	public VjoSourceElementParser() {
		// System.out.println("VJO source element parser created!");
	}

	public void parseSourceModule(char[] contents, ISourceModuleInfo info,
			char[] filename) {
		// if (fReporter != null) {
		// fReporter.clearMarkers();
		// }

		// add by eric 4.27,2009 to make sure the exitModule is called to tell
		// parser if any syntax errors found
		fRequestor.enterModule();
		try {
			IJstType type = getType(contents, filename);
			if (type == null) {
				return;
			}
			int length = contents != null ? contents.length : 0;
			processType(length, type);
			fRequestor.exitModule(length);// add by eric
		} catch (Exception e) {
			IStatus status = new Status(IStatus.ERROR, VjetPlugin.PLUGIN_ID,
					IStatus.ERROR, "Error parsing source module", e);
			DLTKCore.getDefault().getLog().log(status);
		}
	}

	public void parse(char[] contents, VjoSourceModule module) {
		TypeSpaceMgr mgr = TypeSpaceMgr.getInstance();
		IJstType type = mgr.findType(module.getTypeName());
		if (type != null) {
			fRequestor.enterModule();
			processType(contents.length, type);
		}
	}

	/**
	 * @param contents
	 * @param type
	 */
	private void processType(int contentsLength, IJstType type) {
		// Package!!!
		processPackage(type);

		// Imports!!!
		// TODO probably change this implementation - unnecessary
		// convertation to char[][] and back
		JstTypeDependencyCollector c = new JstTypeDependencyCollector();
		Collection<? extends IJstTypeReference> imports = c.getDependencyRefs(type).values();
//		processImports(c.getDependency(type).values());

		// // imported libs
		// imports = lastMeta.getNeedLibs();
		 processImports(imports);
		//

		processTypeBody(contentsLength, type);
	}

	private void processTypeBody(int contentsLength, IJstType type) {
		// Type!!!
		JSTypeInfo typeInfo = new JSTypeInfo();
		processTypeName(type, typeInfo);

		setTypeModifiers(type, typeInfo);

		// // flags e.g. interface
		//
		processSupertypes(type, typeInfo);

		fRequestor.enterType(typeInfo);

		// // Fields!!!
		processTypeFields(type);

		// // Methods!!!
		processTypeMethods(type);

		// Constructor!!!
		processConstructor(type);

		// Collection<JsFunctionMeta> staticMethods = lastMeta
		// .getStaticMethodsAsCollection();
		// processFunctions(staticMethods, true, typeName);
		//
		// // Initializer
		processInitializer(type);

		processInnerTypes(type);

		// add by patrick
		// Global Vars !!!
		processGlobals(type);
		// end add
		
		// TODO correct end offset
		// fRequestor.exitType(typeInfo.nameSourceEnd);
		fRequestor.exitType(contentsLength);
	}

	// add by patrick
	private void processGlobals(IJstType type) {
		List<? extends IJstGlobalVar> globalVars = type.getGlobalVars();
		processGlobalVars(globalVars);
	}

	private void processGlobalVars(List<? extends IJstGlobalVar> globalVars) {
		Set<IJstGlobalFunc> globalFunctions = new HashSet<IJstGlobalFunc>();
		for (IJstGlobalVar globalVar : globalVars) {
			if(globalVar.isFunc()){
				IJstGlobalFunc globalFunction = globalVar.getFunction();
				if(globalFunctions.contains(globalFunction)){
					processGlobalFunc(globalFunction, true);
				}else{
					processGlobalFunc(globalFunction, false);
				}
				globalFunctions.add(globalFunction);
			}else{
				processGlobalProp(globalVar.getProperty());
			}
		}
	}

	private void processGlobalProp(IJstGlobalProp globalProperty) {
		processField(globalProperty, false, true);
	}

	private void processGlobalFunc(IJstGlobalFunc globalFunc, boolean overload) {
		processMethod(globalFunc, overload, true);
	}
	// end add

	
	private void processInnerTypes(IJstType type) {
		List<? extends IJstType> innerTypes = type.getEmbededTypes();
		if (innerTypes != null && !innerTypes.isEmpty()) {
			for (IJstType innerType : innerTypes) {
				JstSource source = innerType.getSource();
				if (source != null) {
					processTypeBody(source.getEndOffSet(), innerType);
				}
			}
		}
	}

	/**
	 * @param type
	 */
	private void processInitializer(IJstType type) {
		JstBlock block = type.getInitBlock();
//		List<IStmt> inits = type.getStaticInitializers();
		if (block != null && block.getSource()!=null) {
			// TODO fix source ref here
			int start = block.getSource().getStartOffSet();
			int end = block.getSource().getEndOffSet();
			
			
			VarTable vars = block.getVarTable();
			Map<String,IJstNode> varMap = null;
			
			
			if(vars instanceof TopLevelVarTable){
				varMap = ((TopLevelVarTable)vars).getSelfVarNodes();
			}else{
				varMap = vars.getVarNodes();
			}
			
			if(varMap.isEmpty()){
				return;
			}
			
			fRequestor.enterInitializer(start, Modifiers.AccStatic);
			
//			for(Map.Entry<String,IJstNode> varz : varMap.entrySet()){
//				String varName = varz.getKey();
//				IJstNode node = varz.getValue();
//				if(node instanceof JstIdentifier){
//				
//					IJstNode jstBinding = ((JstIdentifier) node).getJstBinding();
//					if(jstBinding instanceof JstFuncType){
//						processMethod(((JstFuncType)jstBinding).getFunction(), varName);
//					}else{
//						processIdentifier(((JstIdentifier) node).getType(), (JstIdentifier)node);
//					}
//				}
//				else if(node instanceof JstMethod){
//					processMethod((JstMethod)node, varName);
//				}
////				processLocalVarDecl(jstVars);
//			}
			
			processStatements(block);
			
			
			
			fRequestor.exitInitializer(end);
		}
	}

//	private int getInitializersEnd() {
//		int start = 0;
//
//		if (sourceModule != null) {
//			String s = String.valueOf(sourceModule.getContentsAsCharArray());
//			start = s.indexOf("inits");
//			if (start != -1) {
//				start = s.indexOf("}", start);
//				if (start != -1) {
//					start = s.indexOf(")", start);
//				}
//			}
//		}
//
//		return start;
//	}

//	private int getInitializersStart() {
//		int start = 0;
//
//		if (sourceModule != null) {
//			String s = String.valueOf(sourceModule.getContentsAsCharArray());
//			start = s.indexOf("inits");
//		}
//
//		return start;
//	}

	/**
	 * @param type
	 */
	private void processConstructor(IJstType type) {
		 IJstMethod orginalConstructor = type.getConstructor();

		// Changed by Oliver. 2009-10-19. Include all overloaded constructors
		// but not the dispatcher constructor.
		Collection<? extends IJstMethod> constructorList = JstTypeHelper.getSignatureConstructors(type);
		
		Collection<IJstMethod> constructors = new ArrayList<IJstMethod>();
		for (IJstMethod constructor : constructorList) {
			if (constructor != null
					&& !CodeCompletionUtils.isSynthesizedElement(constructor)) {
				constructors.add(constructor);
				
				/*
				 * add by kevin:
				 * currently, the constructor method from JstTypeHelper.getSignatureConstructors(type) has no source
				 */ 
				if (constructor.getSource() == null)
					((JstMethod)constructor).setSource(orginalConstructor.getSource());
				if (constructor.getName().getSource() == null)
					constructor.getName().setSource(orginalConstructor.getName().getSource());
			}
		}
		processMethods(constructors);
	}

	/**
	 * rewritten by Kevin on 2009-10-20
	 * 
	 * @param type
	 */
	private void processTypeMethods(IJstType type) {
		 final List<IJstMethod> nonConstructorMethods = new ArrayList<IJstMethod>(JstTypeHelper.getSignatureMethods(type));
//		 if(type.isOType()){
//			 final List<IJstOType> otypes = type.getOTypes();
//			 if(otypes != null){
//				 for(IJstOType otype : otypes){
//					 if(otype instanceof JstFunctionRefType){
//						 nonConstructorMethods.add((JstMethod)((JstFunctionRefType)otype).getMethodRef());
//					 }
//				 }
//			 }
//		 }
		 processMethods(nonConstructorMethods);
//		processMethods(filterDispatchTypeMethod(type));
	}

	/**
	 * Sometimes there is the dispatched method in overloaded method, so filter
	 * dispatch method. 
	 * 
	 * @param type
	 * @return
	 */
	private List<IJstMethod> filterDispatchTypeMethod(IJstType type) {

		List<IJstMethod> methods = new ArrayList<IJstMethod>();
		IJstMethod dispacthMethod = null;
		for (IJstMethod mtd : type.getMethods()) {
			if (mtd.isDispatcher()) {
				dispacthMethod = mtd;
				methods.addAll(mtd.getOverloaded());
			} else {
				methods.add(mtd);
			}
			methods.remove(dispacthMethod);
		}
		return methods;
	}

	/**
	 * @param type
	 */
	private void processTypeFields(IJstType type) {
		Collection<IJstProperty> properties = type.getStaticProperties();
		// Iterator<JsPropertyMeta> propsIterator = lastMeta
		// .getStaticProperties();
		processFields(properties, true);

		properties = type.getInstanceProperties();
		processFields(properties, false);

		properties = type.getEnumValues();
		processFields(properties, false);
	}

	/**
	 * @param type
	 * @param typeInfo
	 */
	private void processSupertypes(IJstType type, JSTypeInfo typeInfo) {
		List<? extends IJstType> inheritedTypes = type.getExtends();
		String[] inherits = getTypesNames(inheritedTypes);
		if (type.isInterface()) {
			typeInfo.superinterfaces = inherits;
		} else {
			typeInfo.superclasses = inherits;

			List<? extends IJstType> implementedInterfaces = type
					.getSatisfies();
			typeInfo.superinterfaces = getTypesNames(implementedInterfaces);
		}
	}

	/**
	 * @param type
	 * @param typeInfo
	 */
	private void setTypeModifiers(IJstType type, JSTypeInfo typeInfo) {
		JstModifiers modifiers = type.getModifiers();
		typeInfo.modifiers = Modifiers.AccPublic;// TODO check for other
		if (modifiers.isFinal()) {
			typeInfo.modifiers |= Modifiers.AccFinal;
		}
		if (modifiers.isAbstract()) {
			typeInfo.modifiers |= Modifiers.AccAbstract;
		}
		if (type.isInterface()) {
			typeInfo.modifiers |= ClassFileConstants.AccInterface;
		} else if (type.isEnum()) {
			typeInfo.modifiers |= ClassFileConstants.AccEnum;
		} else if (type.isMixin()) {
			typeInfo.modifiers |= ClassFileConstants.AccModule;
		} else if (type.getModifiers().isStatic()) {
			typeInfo.modifiers |= Modifiers.AccStatic;
		}
	}

	/**
	 * @param type
	 * @param typeInfo
	 */
	private void processTypeName(IJstType type, JSTypeInfo typeInfo) {
		JstSource typeNameSource = type.getSource();
		// TODO set correct type start offset
		if (typeNameSource != null) {
			typeInfo.declarationStart = typeNameSource.getStartOffSet();
			typeInfo.nameSourceStart = typeNameSource.getStartOffSet();
			typeInfo.nameSourceEnd = typeNameSource.getEndOffSet();
		}

		String typeName = type.getSimpleName();
		if (typeName == null) {
			typeName = "";
		}

		typeInfo.name = typeName;
//		this.m_typeName = typeName;
	}

	/**
	 * @param type
	 */
	private void processPackage(IJstType type) {
		JstPackage pack = type.getPackage();
		if (pack != null) {
			String packageName = pack.getName();
			JstSource source = pack.getSource();
			int declarationStart = source != null ? source.getStartOffSet()
					: -1;
			int declarationEnd = source != null ? source.getEndOffSet() : -1;
			fRequestor.acceptPackage(declarationStart, declarationEnd,
					packageName != null ? packageName.toCharArray()
							: new char[0]);
		}
	}

	/**
	 * @param contents
	 * @param filename
	 * @return
	 */
	private IJstType getType(char[] contents, char[] filename) {
		IJstType type = null;

		IVjoSourceModule module = (IVjoSourceModule) sourceModule;
		type = module.getJstType();
		if (type == null) {

			// Added by Oliver. 2009-09-21. It the give type is native, we
			// re-parse it again against the temporarily file.

			// TODO disable menu native parser.
			// if (CodeassistUtils.isNativeType(type)) {
			// String name = String.valueOf(filename).substring(3,
			// filename.length - 3);
			// SourceTypeName sourceTypeName = new SourceTypeName(
			// JstTypeSpaceMgr.JS_NATIVE_GRP, name);
			// File file = Util.getNativeTypeCacheFile(sourceTypeName);
			//
			// VjoParser vjoParser = new VjoParser();
			// IScriptUnit unit = vjoParser.parse(
			// JstTypeSpaceMgr.JS_NATIVE_GRP, file);
			//
			// type = unit.getType();
			// }

			// SourceTypeName name = module.getTypeName();
			// reportErrors(filename, name);
//		}
//		else {
			String name = String.valueOf(filename);
			TypeName typeName;
			typeName = new TypeName(JstTypeSpaceMgr.JS_NATIVE_GRP, name);
			TypeSpaceMgr mgr = TypeSpaceMgr.getInstance();
			type = mgr.findType(typeName);
		}

		fRequestor.enterModule();

		return type;
	}

	// private void reportErrors(char[] filename, SourceTypeName name) {
	// ErrorList errors;
	// errors = typeSpaceMgr.getErrors(name.groupName(), name.typeName());
	// reportErrors(filename, errors);
	// }

	// private void reportErrors(char[] fileName, ErrorList errorList) {
	// if (errorList == null || fReporter == null) {
	// return;
	// }
	//
	// Iterator e = errorList.listIterator();
	// while (e.hasNext()) {
	// ErrorObject eo = (ErrorObject) e.next();
	// String sBegin = eo.getParameters().getValueByName(
	// DefaultErrorReporter.BEGIN);
	// String sEnd = eo.getParameters().getValueByName(
	// DefaultErrorReporter.END);
	// String sLine = eo.getParameters().getValueByName("line");
	// String sColumn = eo.getParameters().getValueByName("column");
	// Integer begin = sBegin == null ? 0 : Integer.valueOf(sBegin);
	// Integer end = sBegin == null ? 0 : Integer.valueOf(sEnd);
	// Integer line = sBegin == null ? 0 : Integer.valueOf(sLine);
	// Integer column = sBegin == null ? 0 : Integer.valueOf(sColumn);
	// fReporter.reportProblem(new DefaultProblem(new String(fileName),
	// eo.getParameters().getValueByName("message"), 0, null,
	// ProblemSeverities.Error, begin, end, line, column));
	// }
	// }

	private void processFields(Collection<IJstProperty> properties,
			boolean isProps) {
		Iterator<IJstProperty> iterator = properties.iterator();
		while (iterator.hasNext()) {
			IJstProperty jsPropertyMeta = iterator.next();
			if (CodeCompletionUtils.isSynthesizedElement(jsPropertyMeta)) {
				continue;
			}
			/* 
			 * modify by patrick
			 * extract the process statements to an overridden method
			 */
			processField(jsPropertyMeta, isProps, false);
		}
	}
	
	
	// add by patrick
	private void processField(IJstProperty jsPropertyMeta, boolean isProps, boolean isGlobal){
		IJSSourceElementRequestor.JSFieldInfo fieldInfo = new IJSSourceElementRequestor.JSFieldInfo();
		fieldInfo.name = jsPropertyMeta.getName().getName();

		final IJstType propertyType = jsPropertyMeta.getType();
		if (propertyType != null) {
			String fieldType = null;
			if(propertyType instanceof JstFuncType){
				fieldType = getFullMethodString(fieldInfo.name, ((JstFuncType)propertyType).getFunction(), jsPropertyMeta.getOwnerType(), false); 
			}
			else{
				fieldType = jsPropertyMeta.getType().getName();
			}
			fieldInfo.m_type = fieldType;
			// fieldInfo.declarationStart = jsPropertyMeta.getType()
			// .getLineNo() + 1;
		} else {
			fieldInfo.m_type = "Object";
		}

		JstSource nameSource = jsPropertyMeta.getName().getSource();
		JstSource source = jsPropertyMeta.getSource();
		fieldInfo.declarationStart = source != null ? source
				.getStartOffSet() : ZERO_OFFSET;
		JstModifiers jstModifiers = jsPropertyMeta.getModifiers();
		// modify by patrick for global property support
		fieldInfo.modifiers = isGlobal ? Modifiers.AccGlobal : Util
				.getModifiers(jstModifiers);
		if (isProps) {
			// fieldInfo.modifiers |= Modifiers.AccFinal;
			fieldInfo.m_initializationSource = getSourceWithoutQuates(jsPropertyMeta
					.getValue());
		}

		int start = nameSource != null ? nameSource.getStartOffSet()
				: ZERO_OFFSET;
		int end = getEndOffset(nameSource);

		fieldInfo.nameSourceStart = start;
		fieldInfo.nameSourceEnd = end;

		// add by xingzhu, reset the actually resource
		if (jsPropertyMeta instanceof JstProxyProperty) {
			IJstType actualOwnerType = jsPropertyMeta.getOwnerType();

			/*
			 * user group name plus type name to get the corresponding
			 * resource, in case of types in different groups have the same
			 * type name.
			 */
			String groupName = actualOwnerType.getPackage().getGroupName();
			URI uri = typeSpaceMgr.getTypeToFileMap().get(
					groupName + "#" + actualOwnerType.getName());
			if (uri != null) {
				IFile actualResource = ResourcesPlugin.getWorkspace()
						.getRoot().getFile(new Path(uri.toString()));
				if (actualResource.exists())
					fieldInfo.resource = actualResource;
			}
		}
		
		fRequestor.enterField(fieldInfo);
		// Added by Eric.Ma 20100527 for show sub property for OTYPE on out line view
		// Make sure below code execute after entire current field.
		if (jsPropertyMeta instanceof JstProperty 
				&& jsPropertyMeta.getType() instanceof JstObjectLiteralType
				&& jsPropertyMeta.getType().getRootType().isOType()){
			List<IJstProperty> properties = jsPropertyMeta.getType().getProperties();
			for (IJstProperty iJstProperty : properties) {
				processField(iJstProperty, false, false);
			}
		}
		// End of added.
		fRequestor.exitField(getEndOffset(source));
	}
	// end add
	
	private int getEndOffset(JstSource source) {
		return source != null ? source.getEndOffSet() : ZERO_OFFSET;
	}

	private static String getSourceWithoutQuates(ISimpleTerm value) {
		if (value != null) {
			char[] val = value.toSimpleTermText().toCharArray();
			if (val.length > 0) {
				if (val[0] == '"' && val[val.length - 1] == '"'
						|| val[0] == '\'' && val[val.length - 1] == '\'') {
					return new String(val, 1, val.length - 2);
				} else {
					return value.toSimpleTermText();
				}
			}
		}
		return null;
	}

	private void processMethods(Collection<? extends IJstMethod> functions) {
		Set<String> methodSet = new HashSet<String>();

		for (IJstMethod method : functions) {
			String methodName = method.getName().getName();

			if (methodSet.contains(methodName))
				processMethod(method, true);
			else
				processMethod(method, false);

			// record method name that has been processed
			methodSet.add(methodName);
		}
	}

	private void processMethod(IJstMethod method, boolean overload) {
		/* 
		 * modify by patrick
		 * extract the process statements to an overload method
		 */
		processMethod(method, overload, false);
	}

	/*
	 * to handle anonomous functions
	 */
	private void processMethod(IJstMethod method, String name) {
		JSMethodInfo methodInfo = new JSMethodInfo();	
		methodInfo.name = name;
		if (method.getRtnType() != null) {
			methodInfo.m_returnType = method.getRtnType().getName();
		} else {
			methodInfo.m_returnType = "void";
		}
		
		List<JstArg> parameters = method.getArgs();
		String[] paramNames = new String[parameters.size()];
		String[] paramTypes = new String[parameters.size()];
		boolean[] isVariables = new boolean[parameters.size()];

		int i = 0;
		List<IJSSourceElementRequestor.JSFieldInfo> args = new ArrayList<IJSSourceElementRequestor.JSFieldInfo>();
		for (JstArg jsParam : parameters) {
			IJSSourceElementRequestor.JSFieldInfo argInfo = new IJSSourceElementRequestor.JSFieldInfo();
			argInfo.name = jsParam.getName();
			argInfo.m_type = jsParam.getType().getName();
			JstSource source = jsParam.getSource();
			argInfo.declarationStart = getStartOffSet(source);
			argInfo.nameSourceStart = getStartOffSet(source);
			argInfo.nameSourceEnd = getEndOffSet(source);
			argInfo.declarationStart = getStartOffSet(source);
			args.add(argInfo);
			paramNames[i] = jsParam.getName();
			paramTypes[i] = jsParam.getType().getName();
			isVariables[i] = jsParam.isVariable();
			i++;
		}

		methodInfo.parameterNames = paramNames;
		methodInfo.m_parameterTypes = paramTypes;
		methodInfo.b_isVariables = isVariables;

		// TODO Get correct method declarationStart.
		JstSource source = method.getSource();
		methodInfo.declarationStart = getStartOffSet(source);
		// modify by patrick for global method support
//		methodInfo.modifiers = isGlobal ? Modifiers.AccGlobal : Util
//				.getModifiers(method.getModifiers());
		// test for static???

//		// TODO Get correct nameSourceStart and nameSourceEnd.
		JstSource nameSource = method.getName().getSource();
		if(nameSource==null){
			nameSource = source;
		}
//
//		// The overloaded method or overloaded constructor does not have the
//		// source, so get the dispatched
//		// method's source as the overloaded's method source.
//		if (nameSource == null && method.getParentNode() != null) {
//			// modify by kevin, fix NPE and ClassCastException (IJstMethod to
//			// IJstType)
//			String methodName = method.getName().getName();
//			IJstType type = method.getOwnerType();
//
//			// process overloaded method that has no name source
//			if (type.getMethod(methodName) != null)
//				nameSource = type.getMethod(methodName).getName().getSource();
//
//			// process constructor that has no name source, fix NPE
//			if (type.getConstructor() != null
//					&& type.getConstructor().getName().equals(methodName))
//				nameSource = type.getConstructor().getName().getSource();
//		}
//
		methodInfo.nameSourceStart = getStartOffSet(nameSource);
		methodInfo.nameSourceEnd = getEndOffSet(nameSource);

		// add by xingzhu, reset actual resource
//		if (method instanceof JstProxyMethod) {
//			IJstType actualOwnerType = method.getOwnerType();
//
//			String groupName = actualOwnerType.getPackage().getGroupName();
//			URI uri = typeSpaceMgr.getTypeToFileMap().get(
//					groupName + "#" + actualOwnerType.getName());
//			if (uri != null) {
//				IFile actualResource = ResourcesPlugin.getWorkspace().getRoot()
//						.getFile(new Path(uri.toString()));
//				if (actualResource.exists())
//					methodInfo.resource = actualResource;
//			}
//		}
		
		// process arguments
		fRequestor.enterMethod(methodInfo);
		for (IJSSourceElementRequestor.JSFieldInfo fieldInfo : args) {
			if (fieldInfo.name != null) {
				fRequestor.enterField(fieldInfo);
				fRequestor.exitField(fieldInfo.nameSourceEnd);
			}
		}
//		if (!overload)
		processStatements(method.getBlock());
		// TODO Get correct method declaration end
		fRequestor.exitMethod(getEndOffSet(source));
		
		
	}
	private void processMethod(IJstMethod method, boolean overload,
			boolean isGlobal) {
		JSMethodInfo methodInfo = new JSMethodInfo();
		methodInfo.m_isConstructor = "constructs".equals(method.getName()
				.getName());

		if (methodInfo.m_isConstructor) {
			// use actual "constructs" name rather than type name to allow
			// search
			methodInfo.name = method.getName().getName(); // this.m_typeName;
			methodInfo.m_returnType = "void";
		} else {
			methodInfo.name = method.getName().getName();
			if (method.getRtnType() != null) {
				methodInfo.m_returnType = method.getRtnType().getName();
				// TODO remove this when fixed in parser
				if (methodInfo.m_returnType == null) {
					methodInfo.m_returnType = "void";
				}
			} else {
				methodInfo.m_returnType = "void";
			}
		}

		List<JstArg> parameters = method.getArgs();
		String[] paramNames = new String[parameters.size()];
		String[] paramTypes = new String[parameters.size()];
		boolean[] isVariables = new boolean[parameters.size()];

		int i = 0;
		List<IJSSourceElementRequestor.JSFieldInfo> args = new ArrayList<IJSSourceElementRequestor.JSFieldInfo>();
		for (JstArg jsParam : parameters) {
			IJSSourceElementRequestor.JSFieldInfo argInfo = new IJSSourceElementRequestor.JSFieldInfo();
			argInfo.name = jsParam.getName();
			argInfo.m_type = jsParam.getType().getName();
			JstSource source = jsParam.getSource();
			argInfo.declarationStart = getStartOffSet(source);
			argInfo.nameSourceStart = getStartOffSet(source);
			argInfo.nameSourceEnd = getEndOffSet(source);
			argInfo.declarationStart = getStartOffSet(source);
			args.add(argInfo);
			paramNames[i] = jsParam.getName();
			paramTypes[i] = jsParam.getType().getName();
			isVariables[i] = jsParam.isVariable();
			i++;
		}

		methodInfo.parameterNames = paramNames;
		methodInfo.m_parameterTypes = paramTypes;
		methodInfo.b_isVariables = isVariables;

		// TODO Get correct method declarationStart.
		JstSource source = method.getSource();
		methodInfo.declarationStart = getStartOffSet(source);
		// modify by patrick for global method support
		methodInfo.modifiers = isGlobal ? Modifiers.AccGlobal : Util
				.getModifiers(method.getModifiers());
		// test for static???

		// TODO Get correct nameSourceStart and nameSourceEnd.
		JstSource nameSource = method.getName().getSource();

		// The overloaded method or overloaded constructor does not have the
		// source, so get the dispatched
		// method's source as the overloaded's method source.
		if (nameSource == null && method.getParentNode() != null) {
			// modify by kevin, fix NPE and ClassCastException (IJstMethod to
			// IJstType)
			String methodName = method.getName().getName();
			IJstType type = method.getOwnerType();

			// process overloaded method that has no name source
			if (type.getMethod(methodName) != null)
				nameSource = type.getMethod(methodName).getName().getSource();

			// process constructor that has no name source, fix NPE
			if (type.getConstructor() != null
					&& type.getConstructor().getName().equals(methodName))
				nameSource = type.getConstructor().getName().getSource();
		}

		methodInfo.nameSourceStart = getStartOffSet(nameSource);
		methodInfo.nameSourceEnd = getEndOffSet(nameSource);

		// add by xingzhu, reset actual resource
		if (method instanceof JstProxyMethod) {
			IJstType actualOwnerType = method.getOwnerType();

			String groupName = actualOwnerType.getPackage().getGroupName();
			URI uri = typeSpaceMgr.getTypeToFileMap().get(
					groupName + "#" + actualOwnerType.getName());
			if (uri != null) {
				IFile actualResource = ResourcesPlugin.getWorkspace().getRoot()
						.getFile(new Path(uri.toString()));
				if (actualResource.exists())
					methodInfo.resource = actualResource;
			}
		}
		
		// process arguments
		fRequestor.enterMethod(methodInfo);
		for (IJSSourceElementRequestor.JSFieldInfo fieldInfo : args) {
			if (fieldInfo.name != null) {
				fRequestor.enterField(fieldInfo);
				fRequestor.exitField(fieldInfo.nameSourceEnd);
			}
		}
		if (!overload)
			processStatements(method.getBlock());
		// TODO Get correct method declaration end
		fRequestor.exitMethod(getEndOffSet(source));
	}

	private int getEndOffSet(JstSource source) {
		return source != null ? source.getEndOffSet() : ZERO_OFFSET;
	}

	private int getStartOffSet(JstSource source) {
		return source != null ? source.getStartOffSet() : ZERO_OFFSET;
	}

	private void processImports(Collection<? extends IJstTypeReference> imports) {
		for (IJstTypeReference importedType : imports) {
			String importDecl = importedType.getReferencedType().getName();
			boolean isOnDemand = false;
			int flag = 0;
//			if(importedType.getReferencedType().getSource() ==null){
//				isOnDemand = true;
////				flag = 1;
//			}
			if (importDecl == null) {
				continue;
			}
			String[] segments = importDecl.split("\\.");
			int len =  segments.length;
			char[][] tokens = new char[len][];
			for (int i = 0; i < len; i++) {
				tokens[i] = segments[i].toCharArray();
			}
			JstSource source = importedType.getSource();
			int start = source == null ? ZERO_OFFSET : source.getStartOffSet();
			int end = source == null ? ZERO_OFFSET : source.getEndOffSet();
			fRequestor.acceptImport(start, end, tokens, isOnDemand, flag);
		}
	}

	private void processStatements(JstBlock block) {
		if (block != null) {
			List<IStmt> statements = block.getStmts();
			for (IStmt statement : statements) {
				processStatement(statement);
			}
		}
	}

	private void processStatement(IJstNode statement) {
		// TODO other types of expressions
		if (statement instanceof AssignExpr) {
			AssignExpr assign = (AssignExpr) statement;
			processAssignExpr(assign);
		} else if (statement instanceof ForStmt) {
			ForStmt forStatement = (ForStmt) statement;
			// initializers first
			// TODO unify processing with JstVars
			// processStatement(forStatement.getInitializers());

			IInitializer initializer = forStatement.getInitializers();
			if (initializer != null) {
				List<AssignExpr> initializers = initializer.getAssignments();
				for (AssignExpr assignExpr : initializers) {
					processAssignExpr(assignExpr);
				}
			}

			// condition next
			processStatement(forStatement.getCondition());

			// updaters
			List<IExpr> updaters = forStatement.getUpdaters();
			for (IExpr updater : updaters) {
				processExpression(updater);
			}
			// block last
			processStatements(forStatement.getBody());
		} else if (statement instanceof WhileStmt) {
			WhileStmt whileStmt = (WhileStmt) statement;
			// condition first
			processExpression(whileStmt.getCondition());
			// body next
			processStatements(whileStmt.getBody());
		} else if (statement instanceof TryStmt) {
			TryStmt tryStmt = (TryStmt) statement;
			// try block
			processStatements(tryStmt.getBody());
			// catch statements
			List<IStmt> catchStmts = tryStmt.getCatchBlock(true).getStmts();
			for (IStmt catchStmt : catchStmts) {
				processStatement(catchStmt);
			}
			// finally block
			processStatements(tryStmt.getFinallyBlock(false));
		} else if (statement instanceof CatchStmt) {
			CatchStmt catchStmt = (CatchStmt) statement;
			processJstVar(catchStmt.getException());

			// body of catch statement
			processStatements(catchStmt.getBody());
		} else if (statement instanceof SwitchStmt) {
			SwitchStmt switchStmt = (SwitchStmt) statement;
			processExpression(switchStmt.getExpr());

			List<IStmt> statements = switchStmt.getBody().getStmts();
			if (statements != null) {
				for (IStmt stmt : statements) {
					processStatement(stmt);
				}
			}
		} else if (statement instanceof CaseStmt) {
			processExpression(((CaseStmt) statement).getExpr());
		} else if (statement instanceof IfStmt) {
			IfStmt ifStmt = (IfStmt) statement;
			// condition
			processExpression(ifStmt.getCondition());
			// if block
			processStatements(ifStmt.getBody());
			// else-if statements
			if(ifStmt.getElseIfBlock(false)!=null){
				List<IStmt> elseIfStmts = ifStmt.getElseIfBlock(false).getStmts();
				if (elseIfStmts != null) {
					for (IStmt elseIfStmt : elseIfStmts) {
						processStatement(elseIfStmt);
					}
				}
				}
			// else block
			processStatements(ifStmt.getElseBlock(false));
		} else if (statement instanceof ThrowStmt) {
			ThrowStmt throwStmt = (ThrowStmt) statement;
			processExpression(throwStmt.getExpression());
		} else if (statement instanceof RtnStmt) {
			RtnStmt returnStmt = (RtnStmt) statement;
			processExpression(returnStmt.getExpression());
		} else if (statement instanceof MtdInvocationExpr) {
			processExpression((IExpr) statement);
			if (((MtdInvocationExpr) statement).getQualifyExpr() != null) {
				processExpression(((MtdInvocationExpr) statement)
						.getQualifyExpr());
			}
		} else if (statement instanceof JstVars) {
			processLocalVarDecl((JstVars) statement);
		} else if (statement instanceof ExprStmt) {
			processExpression(((ExprStmt) statement).getExpr());
		}else{
			if(VjetPlugin.DEBUG){
				System.err.println("DID NOT PROCESS STATEMENT: " + statement.getClass().getName()+":" + statement);
			}
		}
	}

	private void processJstVar(JstVar jstVar) {
		JstSource source = jstVar.getSource();
		IJSSourceElementRequestor.JSFieldInfo fieldInfo = new IJSSourceElementRequestor.JSFieldInfo();
		fieldInfo.name = jstVar.getName();
		fieldInfo.declarationStart = source.getStartOffSet();
		fieldInfo.nameSourceStart = source.getStartOffSet();
		fieldInfo.nameSourceEnd = source.getEndOffSet();
		IJstType type = jstVar.getType();
		String typeName = "Object";
		if (type != null) {
			typeName = type.getName();
		}
		fieldInfo.m_type = typeName;
		fRequestor.enterField(fieldInfo);
		fRequestor.exitField(fieldInfo.nameSourceEnd);
	}

	private void processLocalVarDecl(JstVars jstVars) {
		List<AssignExpr> inits = jstVars.getAssignments();
		for (int i = 0; i < inits.size(); i++) {
			AssignExpr assignExpr = inits.get(i);
			JstIdentifier localVar = (JstIdentifier) assignExpr.getLHS();
			IJstType type = jstVars.getType();
			JstSource source = localVar.getSource();
			IJSSourceElementRequestor.JSFieldInfo fieldInfo = new IJSSourceElementRequestor.JSFieldInfo();
			fieldInfo.name = localVar.getName();
			fieldInfo.declarationStart = source.getStartOffSet();
			fieldInfo.nameSourceStart = source.getStartOffSet();
			fieldInfo.nameSourceEnd = source.getEndOffSet();
			String typeName = "Object";
			if (type != null) {
				if(type instanceof JstFuncType){
					typeName = getFullMethodString(localVar.getName(), ((JstFuncType)type).getFunction(), jstVars.getOwnerType(), false);
				}
				else if(type instanceof JstFunctionRefType){
					typeName = getFullMethodString(localVar.getName(), ((JstFunctionRefType)type).getMethodRef(), jstVars.getOwnerType(), false);
				}
				else{
					typeName = type.getName();
				}
			}
			fieldInfo.m_type = typeName;
			fRequestor.enterField(fieldInfo);
			processExpression(assignExpr.getExpr());
			fRequestor.exitField(assignExpr.getSource().getEndOffSet());
			
			
		}
	}
	
	public static String getFullMethodString(IJstMethod method,
			final IJstType ownerType, final boolean optional) {
		return getFullMethodString(method.getName().getName(), method, ownerType, optional);
	}
	
	public static String getFullMethodString(String name, IJstMethod method,
			final IJstType ownerType, final boolean optional) {
		final StringBuilder strBldr = new StringBuilder();

		name = renameInvoke(method, name);

		if (method instanceof JstConstructor) {
			JstConstructor c = (JstConstructor) method;
			name = c.getOwnerType().getName();
		}

		strBldr.append(name);
		strBldr.append("(");
		IJstType ref = method.getRtnType();
		String oname = "";
		if (ownerType != null) {
			oname = ownerType.getSimpleName();
		}
		String aname = getJstArgsString(method);
		if (aname.length() > 0) {
			strBldr.append(aname);
		}
		strBldr.append(")");
		if (optional) {
			strBldr.append(" ? ");
		}
		if (ref != null) {
			final String rname = ref.getSimpleName();
			strBldr.append(" ").append(rname);
		}
		strBldr.append(" - ");
		strBldr.append(oname);
		return strBldr.toString();

	}

	public static String renameInvoke(IJstMethod method, String name) {		
		if("_invoke_".equals(name)) {
			IJstType ownerType = method.getOwnerType();
			if (ownerType != null && ownerType.isFType()) {
				name = ownerType.getSimpleName();
			}
		}
		return name;
	}
	
	public static String getJstArgsString(IJstMethod method) {
		StringBuffer buffer = new StringBuffer();
		List<JstArg> args = method.getArgs();
		if (args != null && !args.isEmpty()) {
			Iterator<JstArg> it = args.iterator();
			while (it.hasNext()) {
				JstArg arg = it.next();
				IJstType type = arg.getType();
				if (type != null) {
					if(type instanceof JstFuncType){
						buffer.append(getFullMethodString(arg.getName(), ((JstFuncType)type).getFunction(), arg.getOwnerType(), false));
					}
					else if(type instanceof JstFunctionRefType){
						buffer.append(getFullMethodString(arg.getName(), ((JstFunctionRefType)type).getMethodRef(), arg.getOwnerType(), false));
					}
					else{
						buffer.append(type.getSimpleName());
					}
				} else {
					buffer.append("Object");
				}
				buffer.append(" " + arg.getName());
				buffer.append(", ");
			} 
		}
		String result = buffer.toString();
		if (result.length() > 2) {
			result = result.substring(0, result.length() - 2);
		}
		return result;
	}

	private void processIdentifier(IJstType type, JstIdentifier identifier) {
		JstSource source = identifier.getSource();
		IJSSourceElementRequestor.JSFieldInfo fieldInfo = new IJSSourceElementRequestor.JSFieldInfo();
		fieldInfo.name = identifier.getName();
		fieldInfo.declarationStart = source.getStartOffSet();
		fieldInfo.nameSourceStart = source.getStartOffSet();
		fieldInfo.nameSourceEnd = source.getEndOffSet();
		String typeName = "Object";
		if (type != null) {
			typeName = type.getName();
		}
		fieldInfo.m_type = typeName;
		fRequestor.enterField(fieldInfo);
		fRequestor.exitField(fieldInfo.nameSourceEnd);
	}

	private void processAssignExpr(AssignExpr assignExpr) {
		ILHS lhs = assignExpr.getLHS();
		if (lhs instanceof IExpr) {
			processExpression((IExpr) lhs);
		} else if (lhs instanceof JstVar) {
			processJstVar((JstVar) lhs);
		}

		processExpression(assignExpr.getExpr());
	}

	private void processExpression(IExpr expression) {
		if (expression == null) {
			return;
		}
		if (expression instanceof JstIdentifier) {
			JstIdentifier identifier = (JstIdentifier) expression;
			if ("this".equals(identifier.getName())
					|| "self".equals(identifier.getName())
					|| "base".equals(identifier.getName())) {
				return;
			}
			JstSource source = identifier.getSource();
			
//			IJSSourceElementRequestor.JSFieldInfo fieldInfo = new IJSSourceElementRequestor.JSFieldInfo();
//			fieldInfo.name = identifier.getName();
//			fieldInfo.declarationStart = source.getStartOffSet();
//			fieldInfo.nameSourceStart = source.getStartOffSet();
//			fieldInfo.nameSourceEnd = source.getEndOffSet();
//			IJstType type = identifier.getType();
//			String typeName = "Object";
//			if (type != null) {
//				typeName = type.getName();
//			}
//			fieldInfo.m_type = typeName;
//			fRequestor.enterField(fieldInfo);
//			fRequestor.exitField(fieldInfo.nameSourceEnd);
//			
			
//			fRequestor.enterField(fieldInfo);
			fRequestor.acceptFieldReference(identifier.getName().toCharArray(),
					source.getStartOffSet());
		} else if (expression instanceof MtdInvocationExpr) {
			MtdInvocationExpr mtdInvocation = (MtdInvocationExpr) expression;

			JstSource source = mtdInvocation.getMethodIdentifier().getSource();
			// anonymous method source will be null
			int startOffset = source == null ? -1 : source.getStartOffSet();
			int endOffset = source == null ? -1 : source.getEndOffSet();
			fRequestor.acceptMethodReference(mtdInvocation
					.getMethodIdentifier().toExprText().toCharArray(),
					mtdInvocation.getArgs().size(), startOffset, endOffset);
			// fRequestor.acceptMethodReference(mtdInvocation
			// .getMethodIdentifier().toExprText().toCharArray(),
			// mtdInvocation.getArgs().size(), source.getStartOffSet(),
			// source.getEndOffSet());

			if (mtdInvocation.getQualifyExpr() != null) {
				processExpression(mtdInvocation.getQualifyExpr());
			}
		} else if (expression instanceof BoolExpr) {
			BoolExpr boolExpr = (BoolExpr) expression;
			processExpression(boolExpr.getLeft());
			processExpression(boolExpr.getRight());
		} else if (expression instanceof PostfixExpr) {
			processExpression(((PostfixExpr) expression).getIdentifier());
		} else if (expression instanceof InfixExpr) {
			InfixExpr infixExpr = (InfixExpr) expression;
			processExpression(infixExpr.getLeft());
			processExpression(infixExpr.getRight());
		} else if (expression instanceof ParenthesizedExpr) {
			processExpression(((ParenthesizedExpr) expression).getExpression());
		} else if (expression instanceof PrefixExpr) {
			processExpression(((PrefixExpr) expression).getIdentifier());
		} else if (expression instanceof FieldAccessExpr) {
			processExpression(((FieldAccessExpr) expression).getName());
			processExpression(((FieldAccessExpr) expression).getExpr());
		} else if (expression instanceof ArrayAccessExpr) {
			processExpression(((ArrayAccessExpr) expression).getExpr());
			processExpression(((ArrayAccessExpr) expression).getIndex());
		} else if(expression instanceof ObjLiteral){
			// TODO handle obj literal expressions
			processNVs((ObjLiteral)expression);
			
		} else if (expression instanceof FuncExpr) {
			
			// TODO handl function expressions
//			processExpression(((FuncExpr) expression).);
//			processExpression(((FuncExpr) expression));
		}
	}

	private void processNVs(ObjLiteral expression) {
		
		
		for(NV nv: ( expression).getNVs()){
			
			JstIdentifier identifier = nv.getIdentifier();
			IJstProperty p = expression.getResultType().getProperty(identifier.getName());
			IJstMethod m = expression.getResultType().getMethod(identifier.getName());
			
			JstSource source = nv.getIdentifier().getSource();
			IJSSourceElementRequestor.JSFieldInfo fieldInfo = new IJSSourceElementRequestor.JSFieldInfo();
			fieldInfo.name = identifier.getName();
			fieldInfo.declarationStart = source.getStartOffSet();
			fieldInfo.nameSourceStart = identifier.getSource().getStartOffSet();
			fieldInfo.nameSourceEnd = identifier.getSource().getEndOffSet();
			
		
			IJstType type = null;
			if(p!=null){
				 type = p.getType();
			}
			else if(m!=null){
				// TODO we should use method info here not field info
				type = m.getRtnType();
				
			}
			
			String typeName = "Object";
			if (type != null) {
				typeName = type.getName();
			}
			fieldInfo.m_type = typeName;
			
			fRequestor.enterField(fieldInfo);
			if(nv.getValue() instanceof ObjLiteral){
				processNVs((ObjLiteral)nv.getValue());
				fRequestor.exitField(fieldInfo.nameSourceEnd);		
			}else{
				fRequestor.exitField(fieldInfo.nameSourceEnd);					
			}
			
			
//				processExpression(nv.getValue());
		}
	}

	public void setReporter(IProblemReporter reporter) {
//		this.fReporter = reporter;
	}

	public void setRequestor(ISourceElementRequestor requestor) {
		
		if(requestor instanceof IJSSourceElementRequestor){
			this.fRequestor = (IJSSourceElementRequestor) requestor;
		}
		// do nothing without more info
	}

	private static String[] getTypesNames(List<? extends IJstType> types) {
		String[] names = new String[types.size()];
		int idx = 0;
		for (IJstType type : types) {
			names[idx++] = type.getName();
		}
		return names;
	}

	public void parseSourceModule(ISourceModule module, ISourceModuleInfo mifo) {
		if (fRequestor instanceof VjoSourceIndexerRequestor) {
			// don't waste time on indexing
			return;
		}
		this.sourceModule = module;
		parseSourceModule(module.getContentsAsCharArray(), mifo, module
				.getFileName());
	}

	public void setScriptProject(IScriptProject project) {
		// this.m_project = project;

	}
}