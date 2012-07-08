/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.vjo;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.generate.Indenter;
import org.ebayopensource.dsf.jsgen.shared.util.GeneratorJstHelper;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstOType;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.jst.ISynthesized;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstConstructor;
import org.ebayopensource.dsf.jst.declaration.JstFunctionRefType;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstObjectLiteralType;
import org.ebayopensource.dsf.jst.declaration.JstProperty;
import org.ebayopensource.dsf.jst.declaration.JstProxyMethod;
import org.ebayopensource.dsf.jst.declaration.JstProxyProperty;
import org.ebayopensource.dsf.jst.declaration.JstTypeReference;
import org.ebayopensource.dsf.jst.declaration.JstTypeWithArgs;
import org.ebayopensource.dsf.jst.expr.ObjCreationExpr;
import org.ebayopensource.dsf.jst.term.ArrayLiteral;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jst.util.DataTypeHelper;
import org.ebayopensource.dsf.jst.util.JstTypeHelper;
import org.ebayopensource.vjo.meta.VjoConvention;
import org.ebayopensource.vjo.meta.VjoKeywords;

/**
 * TODO: 1. add assertion 2. Method overloading to support other value types 3.
 * Support for different mode: obfuscate, compact, pretty, ... (In SourceWriter)
 * 4. Output Jsr 5. Browser variations
 * 
 * Notes: 1. Proxy is used only to support dotted coding style 2. This class is
 * NOT THREAD SAFE
 */
public class VjoGenerator extends BaseGenerator {

	public static final String END_TYPE_CLOSURE = ".endType()";

	private IJstType m_currentType;

	//
	// Constructor
	//
	public VjoGenerator(final GeneratorCtx ctx) {
		super(ctx);
	}

	//
	// API
	//
	public VjoGenerator writeVjo(final IJstType type) {

		if (getCtx().getConfig().shouldAddCodeGenAnnotation() && 
			!type.isEmbededType()
				&& !type.isLocalType() && !type.isAnonymous()) {
			//CodeGen annotation as block comment
			writeCodeGenCmt();
		}
		
		IJstType jstType = getJstType(type);

		if (jstType != null) {
			writeType(jstType);
		}

		return this;
	}

	/*
	 * public VjoGenerator writeVjo(final IJstType type, boolean forInnerType){
	 * VjoGenerator ret; if (forInnerType) { boolean tmp = m_isLocalType;
	 * m_isLocalType = true; ret = writeVjo(type); m_isLocalType = tmp; } else {
	 * ret = writeVjo(type); }
	 * 
	 * return ret; }
	 */
	public VjoGenerator writeType(final IJstType type) {

		m_currentType = type;
	
		
		// Type
		writeType(type.getName());

		if (type.isOType()) {
			writeOTypeDef();
		} else {
			// Needs
			List<? extends IJstType> needs = type.getImports();
			// for (IJstType need: needs){
			// if (!shouldExcludeNeed(type,need)) {
			// writeNeeds(need.getName());
			// }
			// }
			writeNeeds(needs, type);

			writeInactiveNeeds(type);
			
			// Extends
			List<? extends IJstType> inherits = type.getExtends();
			if (inherits.size() > 0) {
				for (IJstType t : inherits) {
					if (!isDefaultExtend(t)
							&& !GeneratorHelper.isSkipInherits(t)) {
						writeInherits(t);
					}
				}
			}

			// Implements
			List<? extends IJstType> impls = type.getSatisfies();
			if (impls != null && !impls.isEmpty()) {
				for (IJstType t : impls) {
					if (!GeneratorHelper.isSkipSatisfies(t)) {
						writeSatisfies(t);
					}
				}
			}
			
			// Implements
			List<? extends IJstType> mixins = type.getMixins();
			if (mixins != null && !mixins.isEmpty()) {
				for (IJstType t : mixins) {
				//	if (!GeneratorHelper.isSkipSatisfies(t)) {
						writeMixin(t);
				//	}
				}
			}
			
			List<? extends IJstType> expects = type.getExpects();
			if (expects != null && !expects.isEmpty()) {
				for (IJstType t : expects) {
				//	if (!GeneratorHelper.isSkipSatisfies(t)) {
						writeExpects(t);
				//	}
				}
			}

			// if (type.getModifiers().isFinal()){
			// writeFinal();
			// }

			// Props (ptys first, then mtds)
			List<IJstProperty> nvs = JstTypeHelper.getDeclaredProperties(type.getStaticProperties());
			nvs = getNonProxyProperties(nvs);  //Exclude mixin properties during gen
			List<? extends IJstMethod> mtds = JstTypeHelper.getDeclaredMethods(type.getStaticMethods());
			mtds = getNonProxyMethods(mtds); //Exclude mixin methods during gen
			List<? extends IJstType> embeds;
			if (type.isInterface()){
				embeds = type.getEmbededTypes();
			}
			else {
				embeds = type.getStaticEmbededTypes();
			}
			if (nvs.size() + mtds.size() + embeds.size() > 0) {
				startWriteProps();
				writePtys(nvs, mtds.size() + embeds.size() > 0);
				writeEmbeds(embeds, mtds.size() > 0);
				writeMtds(mtds);
				endWriteProps();
			}

			// Protos ((ptys first, then mtds)
			nvs = JstTypeHelper.getDeclaredProperties(type.getInstanceProperties());
			nvs = getNonProxyProperties(nvs);  //Exclude mixin properties during gen
			IJstMethod constructor = type.getConstructor();
			if (constructor instanceof ISynthesized) {
				constructor = null;
			}
			mtds = JstTypeHelper.getDeclaredMethods(type.getInstanceMethods());
			mtds = getNonProxyMethods(mtds); //Exclude mixin methods during gen
			if (type.isInterface()){
				embeds = Collections.emptyList();
			}
			else {
				embeds = type.getInstanceEmbededTypes();
			}
			List<IStmt> instanceInits = type.getInstanceInitializers();
			if (constructor != null
					|| nvs.size() + mtds.size() + embeds.size()
							+ instanceInits.size() > 0) {
				startWriteProtos();
				boolean hasConstructor = constructor != null
						|| !instanceInits.isEmpty();
				writePtys(nvs, hasConstructor
						|| mtds.size() + embeds.size() > 0);
				writeEmbeds(embeds, hasConstructor || mtds.size() > 0);
				if (hasConstructor) {
					writeConstructor(constructor, instanceInits,
							mtds.size() > 0);
				}
				writeMtds(mtds);
				endWriteProtos();
			}

			// Write values
			if (type.isEnum()) {
				writeEnumValues(type);
			}

			// Inits
			List<IStmt> initStmts = type.getStaticInitializers();
			if (initStmts.size() > 0) {
				startWriteInits();
				for (IStmt stmt : initStmts) {
					getStmtGenerator().writeStmt(stmt);
				}
				endWriteInits();
			}

		}

		if(type.isMetaType() && !type.isOType()){
			writeNewline();
			getWriter().append(".options({");
			indent();
			writeNewline(); 
			writeIndent();
			getWriter().append("metatype:" + true);
			outdent();
			writeNewline(); 
			getWriter().append("})");
		}
		
		if(type.isSingleton()){
			writeNewline();
			getWriter().append(".options({");
			indent();
			writeNewline(); 
			writeIndent();
			getWriter().append("singleton:" + true);
			outdent();
			writeNewline(); 
			getWriter().append("})");
		}
		
		if(type.getAliasTypeName()!=null ){
			writeNewline();
			getWriter().append(".options({");
			indent();
			writeNewline(); 
			writeIndent();
			getWriter().append("alias:" + type.getAliasTypeName());
			outdent();
			writeNewline(); 
			getWriter().append("})");
		}
		
		writeTypeClosure();

		if (type.getName() != null && !type.isEmbededType()) {
			getWriter().append(SEMI_COLON);
		}

		if (!type.isOType()) {
			// Sibling types
			for (IJstType t : type.getSiblingTypes()) {
				writeNewline();
				writeType(t);
			}
		}

		return this;
	}

	private void writeOTypeDef() {
		if (!getCurrentType().isOType()) {
			return;
		}
		startWriteOTypeDef();
		List<IJstOType> list = getCurrentType().getOTypes();
		IJstOType o;
		writeIndent();
		for (int i = 0; i < list.size(); i++) {
			o = list.get(i);
			if (o instanceof JstObjectLiteralType) {
				writeOType((JstObjectLiteralType) o);
			} else if (o instanceof JstFunctionRefType) {
				writeOType((JstFunctionRefType) o);
			} 
//			else if (o instanceof JstTypeRefType) {
//				writeOType((JstTypeRefType) o);
//			}
			if (i != list.size() - 1) {
				getWriter().write(",");
				// writeNewline();
				// writeIndent();

			}
		}
		endWriteOTypeDef();
	}

	private void writeOType(JstObjectLiteralType type) {
		getWriter().append(type.getSimpleName()).append(" : {");
		List<IJstProperty> props = type.getProperties();
		
		// writeNewline();
		indent();
		// writeIndent();
		
		if(type.hasOptionalFields()){
			List<IJstProperty> oprops =type.getOptionalFields();
			processProps(props, oprops);
		}else{
			processProps(props, null);
		}
		
		outdent();
		writeNewline();
		writeIndent();
		getWriter().append("}");
	}

	private void processProps(List<IJstProperty> props, List<IJstProperty> oprops) {
		int propSize=props.size();
		if (oprops!=null) {
			propSize += oprops.size();
			
		}
		writeDefs(props, propSize, false, 0);
		if(oprops!=null){
			writeDefs(oprops, propSize, true, props.size());
		}
	}

	private void writeDefs(List<IJstProperty> props, int propSize,
			boolean optional, int count) {
		for (int i = 0; i < props.size(); i++) {
			IJstProperty p = props.get(i);
			writeNewline();
			writeIndent();
			if(p.getDoc()!=null){
				printjsdoccomment(p);
			}
			getWriter().append(p.getName().getName()).append(" : ").append(
					p.getValue()!=null?p.getValue().toSimpleTermText():"null");
			if (count != propSize - 1) {
		//		writeNewline();
				getWriter().append(",");
				// writeNewline();
				// writeIndent();
			}
			getCtx().getProvider().getJsDocGenerator().writeJsDoc(p);
			
			if(optional){
				getWriter().append("?");
			}
			count++;
		//	writeNewline();
		}
	}

	private void writeOType(JstFunctionRefType type) {
		writeJsDoc(type.getMethodRef());
		writeNewline();
		writeIndent();
		getWriter().append(type.getMethodRef().getOriginalName()).append(" : ")
				.append("undefined");
		// writeNameFunc(type.getMethod(),false);
	}
//
//	private void writeOType(JstTypeRefType type) {
//
//	}

	private boolean shouldExcludeNeed(IJstType type, IJstType t) {
		if (isDefaultExtend(t) || isNativeType(t)) {
			return true;
		}
		if (!type.isEmbededType() && !type.isAnonymous()) {
			String name = t.getName();
			List<? extends IJstType> inherits = type.getExtends();
			if (inherits.size() > 0) {
				for (IJstType tp : inherits) {
					if (tp.getName().equals(name)) {
						return true;
					}
				}
			}

			// Implements
			List<? extends IJstType> impls = type.getSatisfies();
			if (impls != null && !impls.isEmpty()) {
				for (IJstType tp : impls) {
					if (tp.getName().equals(name)) {
						return true;
					}
				}
			}

			List<? extends IJstTypeReference> mxns = type.getMixinsRef();
			if (mxns != null && !mxns.isEmpty()) {
				for (IJstTypeReference tp : mxns) {
					if (tp.getReferencedType().getName().equals(name)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private boolean isNativeType(IJstType t) {
		if (DataTypeHelper.getNativeType(t.getName()) != null) {
			return true;
		}
		return false;
	}

	private boolean isDefaultExtend(IJstType t) {
		return ("vjo.Object".equals(t.getName()) 
				|| "vjo.Enum".equals(t.getName())
				|| org.ebayopensource.dsf.jsnative.global.Object.class.getName().equals(t.getName())
				);
	}

	public void writeAnonymousType(final IJstType anonType, final String baseName, final List<IExpr> args) {
		IJstType type = getJstType(anonType);

		if (type == null) {
			return;
		}

		m_currentType = type;
//		String baseType = null;
//		// Extends
//		List<? extends IJstType> inherites = type.getExtends();
//		if (inherites.size() > 0) {
//			baseType = inherites.get(0).getName();
//		}
//
//		// Implements
//		List<? extends IJstType> impls = type.getSatisfies();
//		if (impls != null && !impls.isEmpty()) {
//			baseType = impls.get(0).getName();
//		}

//		if (baseType == null)
//			return;
		String argString = "";
		if (args != null) {
			for (int i = 0; i < args.size(); i++) {
				if (i > 0) {
					argString += ",";
				}
				argString += args.get(i).toExprText();
			}
		}

		getWriter().append(VjoConvention.getAnonymousType(baseName, argString));

		IJstMethod constructor = type.getConstructor();
		if (constructor instanceof ISynthesized) {
			constructor = null;
		}
		List<IStmt> instanceInits = type.getInstanceInitializers();

		List<IJstProperty> nvs = type.getInstanceProperties();
		List<? extends IJstMethod> mtds = JstTypeHelper.getDeclaredMethods(type.getInstanceMethods());
		List<? extends IJstType> embeds = type.getInstanceEmbededTypes();
		boolean hasConstructor = constructor != null
				|| !instanceInits.isEmpty();
		if (hasConstructor || (nvs.size() + mtds.size() + embeds.size() > 0)) {
			startWriteProtos();// what do do about instance initializers
			writePtys(nvs, hasConstructor || mtds.size() + embeds.size() > 0);
			if (hasConstructor) {
				writeConstructor(constructor, instanceInits, mtds.size() > 0);
			}
			writeEmbeds(embeds, false || mtds.size() > 0);
			writeMtds(mtds);
			endWriteProtos();
		}
		
		writeTypeClosure();
	}

	private void writeTypeClosure() {
		writeNewline();
		writeIndent();
		getWriter().append(END_TYPE_CLOSURE);
	}

	private void writeEnumValues(IJstType type) {
		List<IJstProperty> enumValues = type.getEnumValues();
		int count = enumValues.size();
		if (count == 0) {
			return;
		}

		writeNewline();
		writeIndent();
		PrintWriter writer = getWriter();
		writer.append(".").append(VjoKeywords.VALUES).append("(");
		if (type.getConstructor() == null || type.getConstructor() instanceof ISynthesized) {
			writer.append("\"");
			for (int i = 0; i < count; i++) {
				if (i > 0) {
					writer.append(", ");
				}
				writer.append(enumValues.get(i).getName().getName());
			}
			writer.append("\"");
		} else {
			int n = type.getEnumValues().size();
			writer.append("{");// .append("/* Count:
			// ").append(String.valueOf(n)).append(" */");
			boolean addComma1 = false;
			for (int i = 0; i < n; i++) {
				if (addComma1) {
					writer.append(",");
				}
				writeNewline();
				indent();
				writeIndent();
				IJstProperty enumVal = type.getEnumValues().get(i);
				writer.append(enumVal.getName().getName()).append(" : ");
				if (enumVal.getInitializer() instanceof ObjCreationExpr) {
					ObjCreationExpr expr = (ObjCreationExpr) enumVal
							.getInitializer();
					writer.append("[");
					boolean addComma3 = false;
					for (IExpr argExpr : expr.getInvocationExpr().getArgs()) {
						if (argExpr instanceof ArrayLiteral) {
							//Note: This loop is deprecated, should always go to else condition
							boolean addComma2 = false;
							ArrayLiteral valExpr = (ArrayLiteral) argExpr;

							Iterator<IExpr> it = valExpr.getValues();
							while (it.hasNext()) {
								IExpr valLit = it.next();
								if (addComma2) {
									writer.append(", ");
								}
								writer.append(valLit.toExprText());
								addComma2 = true;
							}
						} else {
							if (addComma3) {
								writer.append(", ");
							}
							writer.append(argExpr.toExprText());
							addComma3 = true;
						}
					}
					writer.append("]");
					addComma1 = true;
				} else if (enumVal.getValue() != null){
					writer.append(enumVal.getValue().toSimpleTermText());
				}
				outdent();
			}
			// outdent();
			writeNewline();
			writer.append("}");
		}
		writer.append(")");
	}

	public VjoGenerator writeNeeds(final String type, final String alias) {
		writeNewline();
		if (alias != null && !alias.equals(type)) {
			getWriter().append(".").append(VjoKeywords.NEEDS).append("('")
					.append(type).append("','").append(alias).append("')");
		} else {
			getWriter().append(".").append(VjoKeywords.NEEDS).append("('")
					.append(type).append("')");
		}
		// writeIndent();
		return this;
	}

	private VjoGenerator writeInactiveNeeds(IJstType currentType) {
		final List<? extends IJstType> needs = currentType.getInactiveImports();
		if (needs==null || needs.size()==0) {
			return this;
		}
		StringBuffer sb = new StringBuffer();
		boolean first = true;
		for (IJstType type : needs) {
			if (isNativeType(type)) continue;
			if (!first) {
				sb.append(",");
			}
			sb.append(type.getName());
			first = false;
		}
		
		if (!first) {
			writeNewline();
			getWriter().append("//> needs ").append(sb);
		}
		return this;
	}
	
	public VjoGenerator writeNeeds(final List<? extends IJstType> needs,
			final IJstType type) {
		if (needs==null || needs.size()==0) {
			return this;
		} else if (needs.size()==1) {
			if (!shouldExcludeNeed(type, needs.get(0))) {
				writeNeeds(needs.get(0).getName(),needs.get(0).getAlias());
			}
			return this;
		}
		IJstType first = null;
		List<IJstType> alias = new ArrayList<IJstType>();
		StringBuffer sb = new StringBuffer();
		int i = 0;
		for (IJstType need : needs) {
			if (!shouldExcludeNeed(type, need)) {
				if (need.getAlias() != null
						&& (!need.getAlias().equals(need.getName()) || type
								.getImportsMap().get(need.getName()) != null)) {
					alias.add(need);
				}else {
					if (i>0) {
						sb.append(",");
						if (i%2 == 0) {
							sb.append(getNewline()).append(Indenter.TAB);
						}
						first = null;
					} else {
						first = need;
					}
					sb.append("'").append(need.getName()).append("'");
					i++;
				}
			}
		}

		if (first!=null) {
			writeNeeds(first.getName(), first.getAlias());
		} else if (sb.length() > 0) {
			writeNewline();
			getWriter().append(".").append(VjoKeywords.NEEDS).append("([")
					.append(sb.toString()).append("])");
		}
		for (IJstType need : alias) {
			String als = "";
			if (type.getImportsMap().get(need.getName()) == null) {
				als = need.getAlias();
			}
			writeNeeds(need.getName(), als);
		}
		return this;
	}

	public VjoGenerator writeType(final String type) {
		writeJsComment(m_currentType);
		if (m_currentType.isLocalType()) {
			writeIndent();
			String label;
			if (type == null) {
				label = "$anonymous";
			} else {
				label = type;
			}
			getWriter().append("var ").append(label).append(" = ");
		}
		if (m_currentType.getModifiers().isAbstract()) {
			getWriter().append(VjoKeywords.VJO).append(".");
			getWriter().append(VjoKeywords.CTYPE).append("(");
		} else if (m_currentType.isInterface()) {
			getWriter().append(VjoKeywords.VJO).append(".");
			getWriter().append(VjoKeywords.ITYPE).append("(");
		} else if (m_currentType.isEnum()) {
			getWriter().append(VjoKeywords.VJO).append(".");
			getWriter().append(VjoKeywords.ETYPE).append("(");
		} else if (m_currentType.isMixin()) {
			getWriter().append(VjoKeywords.VJO).append(".");
			getWriter().append(VjoKeywords.MTYPE).append("(");
		} else if (m_currentType.isOType()) {
			getWriter().append(VjoKeywords.VJO).append(".");
			getWriter().append(VjoKeywords.OTYPE).append("(");
		} else {
			//Default to ctype?
			getWriter().append(VjoKeywords.VJO).append(".");
			getWriter().append(VjoKeywords.CTYPE).append("(");
		}
		if (!m_currentType.isLocalType()) {
			if (type != null && !getCurrentType().isEmbededType()) {
				getWriter().append("'").append(type);
				getCtx().getProvider().getJsDocGenerator().writeParamTypes(m_currentType);
				getWriter().append("'");
			}
		}
		getWriter().append(")");
		writeJsDoc(m_currentType);
		return this;
	}

	private void writeJsComment(IJstType currentType) {
		if (currentType.getComments() == null) {
			return;
		}
		List<String> comments = currentType.getComments();
		for (String comment : comments) {
			getWriter().append(comment);
		}
		writeNewline();
	}

	public VjoGenerator writeInherits(final IJstType type) {
		writeNewline();
		writeIndent();
		getWriter().append(".").append(VjoKeywords.INHERITS).append("('")
				.append(type.getName())
				.append(GeneratorJstHelper.getArgsDecoration(type))
				.append("')");
		return this;
	}

	public VjoGenerator writeSatisfies(final IJstType type) {
		writeNewline();
		writeIndent();
		getWriter().append(".").append(VjoKeywords.SATISFIES).append("('")
				.append(type.getName())
				.append(GeneratorJstHelper.getArgsDecoration(type))
				.append("')");
		return this;
	}

	public VjoGenerator writeMixin(final IJstType type) {
		writeNewline();
		writeIndent();
		getWriter().append(".").append(VjoKeywords.MIXIN).append("('")
				.append(type.getName())
			//	.append(GeneratorJstHelper.getArgsDecoration(type))
				.append("')");
		return this;
	}

	public VjoGenerator writeExpects(final IJstType type) {
		writeNewline();
		writeIndent();
		getWriter().append(".").append(VjoKeywords.EXPECTS).append("('")
				.append(type.getName())
			//	.append(GeneratorJstHelper.getArgsDecoration(type))
				.append("')");
		return this;
	}


	public VjoGenerator writeSatisfies (final List<? extends IJstType> needs) {
		if (needs==null || needs.size()==0) {
			return this;
		} else if (needs.size()==0) {
			writeSatisfies(needs.get(0));
			return this;
		}
		StringBuffer sb = new StringBuffer();
		int i = 0;
		for (IJstType need : needs) {
			if (i>0) {
				sb.append(",");
				if (i%2 == 0) {
					sb.append(getNewline()).append(Indenter.TAB);
				}
			}
			sb.append("'").append(need.getName()).append("'");
			i++;
		}

		if (sb.length() > 0) {
			writeNewline();
			getWriter().append(".").append(VjoKeywords.SATISFIES).append("([")
					.append(sb.toString()).append("])");
		}
		return this;
	}
	
	public VjoGenerator startWriteProps() {
		writeNewline();
		writeIndent();
		getWriter().append(".").append(VjoKeywords.PROPS).append("({");
		indent();
		return this;
	}

	public VjoGenerator endWriteProps() {
		outdent();
		writeNewline();
		writeIndent();
		getWriter().append("})");
		return this;
	}

	public VjoGenerator startWriteProtos() {
		writeNewline();
		writeIndent();
		String meth = VjoKeywords.PROTOS;
		if (m_currentType.isOType()) {
			meth = VjoKeywords.DEFS;
		}
		getWriter().append(".").append(meth).append("({");
		indent();
		return this;
	}

	public VjoGenerator endWriteProtos() {
		outdent();
		writeNewline();
		writeIndent();
		getWriter().append("})");
		return this;
	}

	public VjoGenerator startWriteOTypeDef() {
		writeNewline();
		writeIndent();
		getWriter().append(".").append(VjoKeywords.DEFS).append("({");
		writeNewline();
		// writeIndent();
		indent();
		return this;
	}

	public VjoGenerator endWriteOTypeDef() {
		outdent();
		writeNewline();
		// writeIndent();
		getWriter().append("})");
		return this;
	}

	public VjoGenerator startWriteInits() {
		writeNewline();
		writeIndent();
		getWriter().append(".").append(VjoKeywords.INITS)
				.append("(function(){");
		indent();
		return this;
	}

	public VjoGenerator endWriteInits() {
		outdent();
		writeNewline();
		writeIndent();
		getWriter().append("})");
		return this;
	}

	public VjoGenerator writePtys(final List<IJstProperty> ptys, boolean hasMore) {
		IJstProperty pty;
		int size = ptys.size();
		Iterator<IJstProperty> nvs = ptys.iterator();
		int count = 0;
		while (nvs.hasNext()) {
			pty = nvs.next();
			writePty(pty, hasMore || count++ < size - 1);
			if (!suppressJsDoc()) {
				getCtx().getProvider().getJsDocGenerator().writeJsDoc(pty);
			}
		}
		return this;
	}

	public VjoGenerator writePty(final IJstProperty pty, boolean hasMore) {
		writeNewline();
		writeIndent();
		
		printjsdoccomment(pty);
		
		
		getWriter().append(((JstProperty) pty).toNVText());
		if (hasMore) {
			getWriter().append(COMMA);
		}
		return this;
	}

	private void printjsdoccomment(final IJstProperty pty) {
		if(pty.getDoc()!=null && pty.getDoc().getComment()!=null ){
			getWriter().append("/**").append(pty.getDoc().getComment()).append("*/");
			writeNewline();
			writeIndent();
		}
	}

	public VjoGenerator writeEmbeds(final List<? extends IJstType> embeds,
			boolean hasMore) {
		IJstType embed;
		int size = embeds.size();
		Iterator<? extends IJstType> itr = embeds.iterator();
		int count = 0;
		while (itr.hasNext()) {
			embed = itr.next();
			writeNewline();
			writeIndent();
			getWriter().append(embed.getSimpleName()).append(COLON);
			IJstType previousType = getCurrentType();
			writeVjo(embed);
			setCurrentType(previousType);
			if (hasMore || count++ < size - 1) {
				getWriter().append(COMMA);
			}
		}
		return this;
	}

	public VjoGenerator writeMtds(final List<? extends IJstMethod> mtds) {
		IJstMethod mtd;
		int size = mtds.size();
		Iterator<? extends IJstMethod> itr = mtds.iterator();
		int count = 0;
		while (itr.hasNext()) {
			mtd = itr.next();
			writeNameFunc(mtd, count++ < size - 1);
		}
		return this;
	}

	public VjoGenerator writeConstructor(final IJstMethod mtd,
			List<IStmt> instanceInits, boolean hasMore) {

		IJstMethod constructor = mtd;
		if (mtd == null) {
			constructor = new JstConstructor();
			constructor.getModifiers().setPublic();
		}

		// TODO generator should NOT be adding statements
		if (instanceInits != null) {
			JstBlock body = ((JstMethod) constructor).getBlock(true);
			for (int i = 0; i < instanceInits.size(); i++) {
				body.addStmt(i, instanceInits.get(i));
			}
		}
		((JstMethod) constructor).setIsConstructor(true);
		writeNameFunc(constructor, hasMore);
		return this;
	}

	public VjoGenerator writeNameFunc(final IJstMethod mtd, boolean hasMore) {


		writeComments(mtd);
		
	
		
		if(mtd.getDoc()!=null && mtd.getDoc().getComment()!=null ){
			writeNewline();
			getWriter().append("/**").append(mtd.getDoc().getComment()).append("*/");
		//	writeNewline();
			writeIndent();
		}
		if (mtd.getRtnType() != null || mtd instanceof JstConstructor
				|| mtd.isConstructor()) {
			// JsDoc
			writeJsDoc(mtd);
		}

		getMtdGenerator().writeMtd(mtd);

		// Close
		boolean isMetaType = isMetaMtd(mtd);
			getJsCoreGenerator().endWriteFunc(hasMore, isMetaType);
		

		return this;
	}

	private boolean isMetaMtd(final IJstMethod mtd) {
		if( mtd.getAnnotation("metaMethod")!=null){
			return true;
		}
		
		boolean isMetaType = mtd.getOwnerType()!=null ? mtd.getOwnerType().isMetaType():false;
	
		return isMetaType;
	}

	public VjoGenerator writeJsDoc(final IJstMethod mtd) {
		if (suppressJsDoc()) {
			return this;
		}
		getCtx().getProvider().getJsDocGenerator().writeJsDoc(mtd);
		// write overload methods JsDoc if exists
		List<IJstMethod> omtds = mtd.getOverloaded();
		if (omtds != null) {
			for (IJstMethod omtd : omtds) {
				if (!isSame(mtd, omtd)) {
					getCtx().getProvider().getJsDocGenerator().writeJsDoc(omtd,
							((JstMethod) omtd).getAccessScope(),
							mtd.getOriginalName());
				}
			}
		}
		return this;
	}

	public VjoGenerator writeJsDoc(final IJstType type) {

		if (suppressJsDoc()) {
			return this;
		}

		getCtx().getProvider().getJsDocGenerator().writeJsDoc(type);
		return this;
	}

	private static IJstType getJstType(IJstType type) {
		IJstType jstType = type;
		while (jstType != null) {
			if (jstType instanceof JstTypeWithArgs) {
				jstType = ((JstTypeWithArgs) jstType).getType();
			} else {
				break;
			}
		}

		if (jstType instanceof IJstType) {
			return ((IJstType) type);
		}
		return null;
	}

	//
	// Package protected
	//
	IJstType getCurrentType() {
		return m_currentType;
	}

	void setCurrentType(IJstType currentType) {
		m_currentType = currentType;
	}

	//
	// Private
	//
	private boolean suppressJsDoc() {
		if (m_currentType != null && m_currentType.getSimpleName() != null) {
			return false;
		}
		return true;
	}

	private boolean isSame(IJstMethod source, IJstMethod target) {
		List<JstArg> sourceParams = source.getArgs();
		List<JstArg> targetParams = target.getArgs();

		int sourceParamsCount = sourceParams.size();
		int targetParamsCount = targetParams.size();
		if (sourceParamsCount == targetParamsCount) {
			for (int i = 0; i < sourceParamsCount; i++) {
				JstArg sourceArg = sourceParams.get(i);
				JstArg targetArg = targetParams.get(i);
				if (sourceArg == null && targetArg != null) {
					return false;
				} else if (sourceArg != null && targetArg == null) {
					return false;
				} else if (!(sourceArg.getType().equals(targetArg.getType()))) {
					return false;
				}
			}
		} else {
			return false;
		}
		return true;
	}
	
	private void writeCodeGenCmt() {
		getWriter().append("/* ");
		writeCodeGenMarker(VjoGenerator.class);
		getWriter().append(" */");
		writeNewline();
	}

	private List<IJstProperty> getNonProxyProperties(List<IJstProperty> ptys) {
		Iterator<IJstProperty> nvs = ptys.iterator();
		IJstProperty pty;
		boolean useNew = false;
		List<IJstProperty> newList = new ArrayList<IJstProperty>();
		while (nvs.hasNext()) {
			pty = nvs.next();
			if (pty instanceof JstProxyProperty) {
				useNew = true;
			} else {
				newList.add(pty);
			}
		}
		return (useNew)?newList:ptys;
	}

	private List<? extends IJstMethod> getNonProxyMethods(
			final List<? extends IJstMethod> mtds) {
		List<IJstMethod> newList = new ArrayList<IJstMethod>();
		boolean useNew = false;
		Iterator<? extends IJstMethod> itr = mtds.iterator();
		IJstMethod mtd;
		while (itr.hasNext()) {
			mtd = itr.next();
			if (mtd instanceof JstProxyMethod) {
				useNew = true;
			} else {
				newList.add(mtd);
			}
		}
		return (useNew)?newList:mtds;
	}

}
