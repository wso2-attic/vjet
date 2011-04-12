/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import org.ebayopensource.dsf.javatojs.control.ExpressionTypeVisitor;
import org.ebayopensource.dsf.javatojs.trace.TranslateMsgId;
import org.ebayopensource.dsf.javatojs.translate.custom.CustomAttr;
import org.ebayopensource.dsf.javatojs.translate.custom.CustomInfo;
import org.ebayopensource.dsf.javatojs.translate.custom.anno.IAnnoProcessor;
import org.ebayopensource.dsf.jsgen.shared.classref.IClassR;
import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstDoc;
import org.ebayopensource.dsf.jst.declaration.JstFactory;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.declaration.JstProperty;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstVar;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.expr.FuncExpr;
import org.ebayopensource.dsf.jst.expr.ObjCreationExpr;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.term.SimpleLiteral;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.ISimpleTerm;
import org.ebayopensource.vjo.meta.VjoConvention;

public class FieldTranslator extends BaseTranslator {

	private ExpressionTypeVisitor m_autoBoxer = new ExpressionTypeVisitor();

	//
	// API
	//
	/**
	 * Process FieldDeclaration AST node into supplied JstType
	 * @param jstType JstType
	 * @param astField FieldDeclaration
	 */
	public IJstType processField(FieldDeclaration astField, final JstType jstType){
		
		TranslateCtx ctx = getCtx();					
		TranslateInfo tInfo = getCtx().getTranslateInfo(jstType);
		
		Iterator<VariableDeclarationFragment> ite = astField.fragments().iterator();	
		String fName;
		CustomInfo fInfo;
		boolean include = false;
		if (!tInfo.getMode().hasImplementation()){
			for (IAnnoProcessor ap: ctx.getConfig().getAnnoProcessors()){
				CustomInfo cInfo = ap.process(astField, jstType);
				while(ite.hasNext()){
					fName = ite.next().getName().getIdentifier();
					fInfo = tInfo.getFieldCustomInfo(fName);
					if (cInfo != null && cInfo != CustomInfo.NONE){
						if (fInfo == null || fInfo == CustomInfo.NONE){
							fInfo = new CustomInfo(cInfo);
							tInfo.addFieldCustomInfo(fName, fInfo);
						}
						else {
							fInfo = CustomInfo.update(fInfo, cInfo);
						}
					}
					if (!include && TranslateHelper.Property.includeFieldForDecl(astField, fInfo, jstType)){
						include = true;
					}
				}
				
				if (!include){
					return null;
				}
			}
		}
		else {
			while(ite.hasNext()){
				fName = ite.next().getName().getIdentifier();
				fInfo = tInfo.getFieldCustomInfo(fName);
				if (!include && TranslateHelper.Property.includeFieldForImpl(astField, fInfo, jstType)){
					include = true;
				}
			}
		}
		
		if (!include){
			return null;
		}
		
		IJstType fldType = getDataTypeTranslator().processType(astField.getType(), jstType);
		if (fldType == null){
			getLogger().logInfo(TranslateMsgId.NULL_RESULT, "failed translation", this, astField, jstType);
			return null;
		}

		if (!tInfo.getStatus().isDeclTranlationDone()){
			processFieldDecl(astField, fldType, jstType);
		}
		
		if (tInfo.getMode().hasImplementation()){
			if (tInfo.clearTypeRefs()){
				processFieldDecl(astField, fldType, jstType);
			}
			processFieldImpl(astField, fldType, jstType);
		}

		return fldType;
	}
	
	public void processEnumConstsDecl(final EnumConstantDeclaration astEnum, final JstType jstType){
		
		String name = astEnum.getName().toString();
		TranslateCtx ctx = getCtx();
		TranslateInfo tInfo =  getCtx().getTranslateInfo(jstType);	
		CustomInfo fInfo;
		if (!tInfo.getMode().hasImplementation()){
			CustomInfo cInfo;
			for (IAnnoProcessor ap: ctx.getConfig().getAnnoProcessors()){
				cInfo = ap.process(astEnum, jstType);
				String fName = astEnum.getName().toString();
				fInfo = tInfo.getFieldCustomInfo(fName);
				if (cInfo != null){
					if (fInfo == null || fInfo == CustomInfo.NONE){
						fInfo = new CustomInfo(cInfo);
						tInfo.addFieldCustomInfo(fName, fInfo);
					}
					else {
						fInfo = CustomInfo.update(fInfo, cInfo);
					}
				}
			}
		}
		
		if (tInfo.getFieldCustomInfo(name).isExcluded()){
			return;
		}
		
		IJstProperty pty = null;

		pty = jstType.getEnumValue(name);
		if (pty == null){
			// Add static, public property
			pty = createProperty(jstType, name, true, jstType);
			pty.getModifiers().setPublic();
			pty.getModifiers().setFinal();
			jstType.addEnumValue(pty);
		}
	}
	
	public void processEnumConstsImpl(final EnumConstantDeclaration astEnum, final JstType jstType){
		
		String name = astEnum.getName().toString();
		if (getCtx().getTranslateInfo(jstType).getFieldCustomInfo(name).isExcluded()){
			return;
		}
		
		List args = astEnum.arguments();
		IJstProperty pty = null;

		if (pty == null){
			pty = jstType.getEnumValue(name);
		}
		
		//Case where enum values are initialized through Constructor
//		SimpleLiteral keyArg = SimpleLiteral.get(name);
		IExpr[] valArg = new IExpr[args.size()];
		int counter = 0;
		for (Object itm: args){
			IExpr constValLit = null;
			if (itm instanceof ExpressionStatement){
				//TODO - Not supported yet
				//System.out.println("//>> Work in progress, expression in enum values not supported yet!");
				constValLit = SimpleLiteral.getStringLiteral(itm.toString());
			} else if (itm instanceof Expression) {
				constValLit = getExprTranslator().processExpression((Expression)itm, jstType);
			} else {
				//TODO - Is this default?
				constValLit = SimpleLiteral.getStringLiteral(itm.toString());
			}
//			if (constValLit instanceof JstLiteral) {
//				valArg.add((JstLiteral)constValLit);
//			}
			valArg[counter++] = constValLit;
		}

		ObjCreationExpr initializer = TranslateHelper.Expression
			.createObjCreationExpr(jstType, valArg);
		setInitializer(pty, initializer);
		AnonymousClassDeclaration anonymousClassDeclaration = astEnum.getAnonymousClassDeclaration();
		if (anonymousClassDeclaration != null) {
			ObjCreationExpr objCreation = getExprTranslator().toObjCreation(null,
					jstType, null, name, null, args, anonymousClassDeclaration, jstType);
			if (objCreation != null){
				IJstType anonymousType = objCreation.getAnonymousType();
				if (anonymousType != null){
					AssignExpr assignExpr;
					JstIdentifier lhs;
					JstIdentifier qualifier = 
						new JstIdentifier(name, VjoTranslateHelper.getStaticMemberQualifier(jstType, jstType));
					for (IJstProperty p: anonymousType.getProperties()){
						lhs = new JstIdentifier(p.getName().getName(), qualifier);
						if (p.getValue() != null && p.getValue() instanceof IExpr){
							jstType.addInit(new AssignExpr(lhs, (IExpr)p.getValue()), true);
						}
						else if (p.getInitializer() != null){
							jstType.addInit(new AssignExpr(lhs, p.getInitializer()), true);
						}
					}
					for (IJstMethod m: anonymousType.getMethods()){
						lhs = new JstIdentifier(m.getName().getName(), qualifier);
						assignExpr = new AssignExpr(lhs, new FuncExpr((JstMethod)m));
						jstType.addInit(assignExpr, true);
					}
				}
			}
		}	
	}
	
	private void setInitializer(IJstProperty pty, IExpr initializer) {
		if (pty instanceof JstProperty) {
			((JstProperty)pty).setInitializer(initializer);
		}
	}

	//
	// Package protected
	//
	JstProperty createProperty(final IJstType type, final String name, boolean isStatic, BaseJstNode parent){
		JstProperty pty;
		if (isStatic){
			pty = new JstProperty(type, name, new JstModifiers(JstModifiers.STATIC));
		}
		else {
			pty = new JstProperty(type, name);
		}
		pty.setParent(parent);
		return pty;
	}
	
	void initPtys(final IJstProperty pty, final IExpr initializer, final JstType jstType){
		if (initializer == null || pty == null){
			return;
		}
		List<IJstProperty> ptys = new ArrayList<IJstProperty>();
		ptys.add(pty);
		initPtys(ptys, initializer, jstType);
	}
	
	void initPtys(final Collection<IJstProperty> ptys, final IExpr initializer, final JstType jstType){
		
		if (initializer == null || ptys == null || ptys.size() == 0){
			return;
		}
		
		IJstProperty pty1 = ptys.iterator().next();
		if (pty1 == null){
			return;
		}
		
		VjoConvention conv = getCtx().getConfig().getVjoConvention();
		
		boolean isStatic = pty1.isStatic();
		String qualifier = VjoConvention.getThisPrefix();
		if (isStatic){
			qualifier = VjoConvention.getNameWithStaticThis(pty1.getOwnerType().getSimpleName());
		}
		Collection<IJstProperty> list = jstType.getProperties(isStatic);

		IJstType type = pty1.getType();

		if (ptys.size() > 1){
			JstVar tempVar = new JstVar(type, conv.getTempVar());
			JstIdentifier tempIdentifier = new JstIdentifier(tempVar.getName());
			AssignExpr tempExpr = new AssignExpr(tempVar, initializer);
			jstType.addInit(tempExpr, isStatic);
			for (IJstProperty p: list){
				if (ptys.contains(p)){
					String name = p.getName().getName();
					JstIdentifier identifier = new JstIdentifier(name);
					if (qualifier != null){
						identifier.setQualifier(new JstIdentifier(qualifier));
					}
					identifier.setJstBinding(p);
					AssignExpr assignExpr = new AssignExpr(identifier, tempIdentifier);
					jstType.addInit(assignExpr, isStatic);
					setInitializer(p, assignExpr);
				}
			}
		}
		else {
			JstIdentifier identifier = new JstIdentifier(pty1.getName().getName());
			if (qualifier != null){
				identifier.setQualifier(new JstIdentifier(qualifier));
			}
			identifier.setJstBinding(pty1);
			AssignExpr assignExpr = new AssignExpr(identifier, initializer);
			jstType.addInit(assignExpr, isStatic);
			setInitializer(pty1, assignExpr);
		}
	}
	
	//
	// Private
	//
	private void processFieldDecl(FieldDeclaration astField, IJstType fieldType, final JstType jstType){
		
		boolean isStatic = jstType.isInterface() || TranslateHelper.isStatic(astField.modifiers());
		VariableDeclarationFragment v;
		String name;
		IJstProperty pty;
		
		for (Object o: astField.fragments()){
			IJstType currentType = fieldType;
			if (o instanceof VariableDeclarationFragment){
				v = (VariableDeclarationFragment)o;
				
				if (v.getExtraDimensions() > 0) {
					for (int i=0; i<v.getExtraDimensions(); i++){
						currentType = JstFactory.getInstance().createJstArrayType(currentType, true);
					}
				}

				name = getNameTranslator().processVarName(v.getName(), jstType);
//				if (tInfo.getFieldCustomInfo(name).isExcluded()){
//					continue;
//				}
				if (!IClassR.INativeJsFuncProxy.equals(fieldType.getName())) {
					for (IJstMethod p: jstType.getMethods()) {
						if (p.getName().getName().equals(name)) {
							//System.err.println(fieldType.getName());
							getLogger().logError(TranslateMsgId.DUPLICATE_NAME, "Field name is same as other method name: " , this, astField,jstType);
						}
					}
				}
				jstType.getVarTable().addVarType(name, currentType);
				pty = jstType.getProperty(name, isStatic);
				if (pty == null){
					pty = createProperty(currentType, name, isStatic, jstType);
				}
				else if (pty instanceof JstProperty){
					((JstProperty)pty).setType(currentType);
				}
				JstModifiers modifiers = pty.getModifiers();
				for (Object m: astField.modifiers()){
					if (m instanceof Modifier){
						modifiers.merge(((Modifier)m).getKeyword().toFlagValue());
					}
					else if (m instanceof Annotation){
						getOtherTranslator().processAnnotation((Annotation) m, jstType);
						continue;
					}
					else {
						getLogger().logUnhandledNode(this, (ASTNode)m, jstType);
						continue;
					}
				}
				if (jstType.isInterface()){
					modifiers.setPublic();
				}
				
				// Javadoc
				processFieldJavadoc(astField, (JstProperty) pty);
	
				jstType.addProperty(pty);
			}
			else {
				getLogger().logUnhandledNode(this, (ASTNode)o, jstType);
			}
		}
	}
	
	private void processFieldJavadoc(FieldDeclaration astField, JstProperty pty) {
		Javadoc javadoc = astField.getJavadoc();
		if (javadoc == null) {
			return;
		}
		JstDoc jstDoc = new JstDoc(javadoc.toString());
		pty.setDoc(jstDoc);
		
	}

	private void processFieldImpl(FieldDeclaration astField, IJstType fieldType, final JstType jstType){
		
		boolean isStatic = jstType.isInterface() || TranslateHelper.isStatic(astField.modifiers());
		VariableDeclarationFragment v;
		String name;
		List<IJstProperty> ptys;
		IJstProperty pty;
		
		TranslateInfo tInfo = getCtx().getTranslateInfo(jstType);
		CustomInfo cInfo;
		
		boolean isFieldTypeJavaOnly = getCtx().isJavaOnly(fieldType);
		
		for (Object o: astField.fragments()){
			if (o instanceof VariableDeclarationFragment){
				v = (VariableDeclarationFragment)o;
				if (v.getInitializer() == null){
					continue;
				}
				
				name = v.getName().toString();
				cInfo = tInfo.getFieldCustomInfo(name);
				if (cInfo.isJavaOnly() || cInfo.isJSProxy() || cInfo.isMappedToJS() || cInfo.isMappedToVJO()){
					continue;
				}
				if (isFieldTypeJavaOnly){
					if (cInfo == CustomInfo.NONE){
						cInfo = new CustomInfo();
						tInfo.addFieldCustomInfo(name, cInfo);
					}
					cInfo.setAttr(CustomAttr.JAVA_ONLY);
					continue;
				}
				pty = jstType.getProperty(name, isStatic, false);
				
				ptys = new ArrayList<IJstProperty>();
				if (pty != null){
					ptys.add(pty);
				}
				
				Expression initializer = v.getInitializer();
				if (initializer instanceof Assignment){
					Assignment a = (Assignment)initializer;
					List<SimpleName> vars = new ArrayList<SimpleName>();
					initializer = getMostRight(a, vars, jstType);
					for (SimpleName var: vars){
						String ptyName = var.getIdentifier();
						pty = jstType.getProperty(ptyName, isStatic, false);
						if (pty == null){
							pty = createProperty(fieldType, name, isStatic, jstType);
							jstType.addProperty(pty);
						}
						ptys.add(pty);
					}
				}
	
				Collection<IJstProperty> list = jstType.getProperties();
				boolean isAnon = jstType.isAnonymous();
				
				ISimpleTerm simpleTerm = getExprTranslator()
					.toSimpleTerm(initializer, ptys.isEmpty() ? jstType : (BaseJstNode)ptys.get(0));
				
				if (simpleTerm != null){
					for (IJstProperty p: list){
						if (ptys.contains(p)){
							// Overwrite previous set simple value if any
							if (p instanceof JstProperty){
								if (simpleTerm instanceof JstIdentifier && !isAnon){
									initPtys(p, ((JstIdentifier)simpleTerm), jstType);
								}
								else {
									((JstProperty)p).setValue(simpleTerm);
									m_autoBoxer.autoBoxingVisit(p);
								}
							}
						}
					}
					continue;
				}
	
				IExpr expr = getExprTranslator().processExpression(initializer, ptys.isEmpty() ? jstType : (BaseJstNode)ptys.get(0));
				if (expr == null){
					getLogger().logError(TranslateMsgId.NULL_RESULT, "Null result when translating to IExpr from: " +  initializer, this, initializer, jstType);
					continue;
				}
				initPtys(ptys, expr, jstType);
			}
			else {
				getLogger().logUnhandledNode(this, (ASTNode)o, jstType);
			}
		}
	}
	
	private Expression getMostRight(final Assignment a, List<SimpleName> vars, final JstType jstType){

		Expression left = a.getLeftHandSide();
		if (!(left instanceof SimpleName)){
			getLogger().logUnhandledNode(this, left, jstType);
			return null;
		}
		// TODO: check duplicity
		vars.add((SimpleName)left);
		
		Expression right = a.getRightHandSide();
		if (right == null){
			return null;
		}
		
		if (right instanceof Assignment){
			return getMostRight((Assignment)right, vars, jstType);
		}
		else {
			return right;
		}
	}
}
