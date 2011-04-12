/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/

package org.ebayopensource.dsf.javatojs.translate;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;

import org.ebayopensource.dsf.javatojs.translate.config.MethodKey;
import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstProperty;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.expr.ObjCreationExpr;
import org.ebayopensource.dsf.jst.reserved.JsCoreKeywords;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.vjo.meta.VjoConvention;

public class NameTranslator extends BaseTranslator {
	
	//
	// API
	//
	public String processVarName(final SimpleName astName, BaseJstNode jstNode){
		// TODO	
		return processVarName(astName.toString());
	}
	
	public String processVarName(String varName){
		String name = varName;
		if (JsCoreKeywords.isReservedKeyword(name)){
			name = name + getCtx().getConfig().getVjoConvention().getSurfixReservedWord();
			return name;
		}		
		return name;
	}
	
	public JstIdentifier processName(final Name astName, boolean skipCustomTranslation, BaseJstNode jstNode){
		return processName(astName, false, false, null, skipCustomTranslation, jstNode);
	}
	
	public JstIdentifier processName(final Name astName, IExpr qualifierExpr, boolean skipCustomTranslation, BaseJstNode jstNode){
		return processName(astName, false, false, qualifierExpr, skipCustomTranslation, jstNode);
	}
	
	public JstIdentifier processName(final Name astName, boolean hasSuper, boolean hasThis, 
			final IExpr qualifierExpr, boolean skipCustomTranslation, final BaseJstNode jstNode){
		
		JstIdentifier jstIdentifier = null;
		TranslateInfo tInfo = getCtx().getTranslateInfo(jstNode.getOwnerType());
		
		if (!skipCustomTranslation){
			jstIdentifier = processName(astName, hasSuper, hasThis, qualifierExpr, true, jstNode);
			JstIdentifier customized = getCustomTranslator().processIdentifier(astName, hasSuper, hasThis, qualifierExpr, jstIdentifier, jstNode);
			if (customized != null){
				return customized;
			}
			else {
				return jstIdentifier;
			}
		}
		
		if (astName instanceof QualifiedName){
			
			String name = astName.getFullyQualifiedName();
			IJstType jstType = tInfo.getType(name, false);
			if (jstType != null){
				jstIdentifier = new JstIdentifier(name);
				jstIdentifier.setJstBinding(jstType);
				jstIdentifier.setType(jstType);
				
				TranslateHelper.Type.validateTypeReference(jstType, astName, jstIdentifier, jstNode, this);
			
				return jstIdentifier;
			}
			
			QualifiedName qn = (QualifiedName)astName;
			
			JstIdentifier jstQualifier = processName(qn.getQualifier(), qualifierExpr, skipCustomTranslation, jstNode);
			if (jstQualifier == null){
				return processName(qn.getName(), qualifierExpr, skipCustomTranslation, jstNode);
			}
			else {
				jstIdentifier = processName(qn.getName(), jstQualifier, skipCustomTranslation, jstNode);
				if (jstIdentifier.getQualifier() == null){
					setNameQualifier(jstIdentifier, jstQualifier, jstNode);
					setNameIdentifierType(jstIdentifier, jstQualifier);
				}	
				return jstIdentifier;
			}
		}
		else if (astName instanceof SimpleName){
			
			String name = processVarName(astName.toString());
		
			if (qualifierExpr == null && !hasThis && !hasSuper){
				// Check local vars
				IJstType varType = TranslateHelper.Type.getLocalVarType(name, jstNode);
				if (varType != null){
					jstIdentifier = new JstIdentifier(name);
					setNameIdentifierType(jstIdentifier, jstNode);
					return jstIdentifier;
				}
				
				// Check imported static fields
				String typeName = getCtx().getTranslateInfo(jstNode.getRootType()).getImportedStaticRefTypeName(name);
				typeName = getCtx().getConfig().getPackageMapping().mapTo(typeName);
				IJstType refType = getDataTypeTranslator().findJstType(typeName, jstNode);
				if (refType != null){
					jstIdentifier = new JstIdentifier(name);
					jstIdentifier.setQualifier(VjoTranslateHelper.getStaticMemberQualifier(refType, jstNode));
					IJstProperty pty = TranslateHelper.Property.getProperty(refType, name);
					if (pty != null){
						jstIdentifier.setJstBinding(pty);
						jstIdentifier.setType(pty.getType());
					}
					TranslateHelper.Type.validateTypeReference(refType, astName, jstIdentifier, jstNode, this);
					return jstIdentifier;
				}
			}
	
			// Check enum const 
			jstIdentifier = getEnumConstIdentifier(name, hasSuper, hasThis, qualifierExpr, jstNode);
			if (jstIdentifier != null){
				return jstIdentifier;
			}

			// Check static type ref
			jstIdentifier = getTypeIndentifier(name, astName, hasSuper, hasThis, qualifierExpr, jstNode);
			if (jstIdentifier != null){
				return jstIdentifier;
			}
			
			// Check properties in the qualifierType or ownerTtype and their inheritance hierarchy
			jstIdentifier = getPropertyIdentifier(astName, hasSuper, hasThis, qualifierExpr, jstNode);
			if (jstIdentifier != null){
				return jstIdentifier;
			}
			
			return null;
		}
		else {
			getLogger().logUnhandledNode(this, (ASTNode)astName, jstNode);
			return null;
		}
	}
	
	public JstIdentifier processMtdName(final Name astName, List<IExpr> args, final IExpr qualifierExpr, final BaseJstNode jstNode){
		return processMtdName(astName, args, true, false, qualifierExpr, jstNode);			   
	}
	
	public JstIdentifier processMtdName(final Name astName, List<IExpr> args, boolean needSuper, boolean needThis, 
			final IExpr qualifierExpr, final BaseJstNode jstNode){
		
		if (astName instanceof SimpleName){
			String mtdName = processVarName(astName.toString());
			if (jstNode.getOwnerType() instanceof JstType){

				// Check imported static methods
				String typeName = getCtx().getTranslateInfo(jstNode.getRootType()).getImportedStaticRefTypeName(mtdName);
				typeName = getCtx().getConfig().getPackageMapping().mapTo(typeName);
				IJstType refType = getDataTypeTranslator().findJstType(typeName, jstNode);
				if (refType != null){
					JstIdentifier jstIdentifier = new JstIdentifier(mtdName);
					jstIdentifier.setQualifier(VjoTranslateHelper.getStaticMemberQualifier(refType, jstNode));
					IJstMethod mtd = TranslateHelper.Method.getMethod(astName.getParent(), refType, mtdName, args);
					if (mtd != null){
						jstIdentifier.setJstBinding(mtd);
						jstIdentifier.setType(mtd.getRtnType());
					}
					return jstIdentifier;
				}
			}
			return getMethodIdentifier(astName, args, needSuper, needThis, qualifierExpr, jstNode);
		}
		else if (astName instanceof QualifiedName){
			QualifiedName qn = (QualifiedName)astName;
			JstIdentifier qualifier = processMtdName(qn.getQualifier(), args, needSuper, needThis, qualifierExpr, jstNode);
			JstIdentifier identifier = processMtdName(qn.getName(), args, needSuper, needThis,  qualifierExpr, jstNode);
			if (identifier != null && qualifier != null){
				identifier.setQualifier(qualifier);	
				setMtdIdentifierType(identifier, qualifier);
			}
			return identifier;
		}
		else {
			getLogger().logUnhandledNode(this, (ASTNode)astName, jstNode);
			return null;
		}
	}
	
	//
	// Private
	//
	private JstIdentifier getTypeIndentifier(final String name, final Name astName, boolean needSuper, boolean needThis, 
			IExpr qualifierExpr, BaseJstNode jstNode){
		
		JstType ownerType = jstNode.getOwnerType();
		JstIdentifier jstIdentifier = null;
		JstIdentifier jstQualifier = null;
		
		// It's inner type of qualifierType
		if (qualifierExpr != null) {
			IJstType qualifierType = qualifierExpr.getResultType();
			if (qualifierType != null){
				if (qualifierType.getEmbededType(name) != null){
					IJstType embededType = qualifierType.getEmbededType(name);
					jstQualifier = VjoTranslateHelper.getStaticTypeQualifier(embededType, jstNode, true);
					jstIdentifier = new JstIdentifier(name);
					jstIdentifier.setJstBinding(embededType);
					jstIdentifier.setType(embededType);
					if (qualifierType instanceof JstType){
						TranslateHelper.Type.validateEmbeddedTypeReference(
							astName, (JstType)qualifierType, name, jstNode, jstNode, this);
					}
				}
				return jstIdentifier;
			}
		}
		else if (name.equals(ownerType.getSimpleName()) || name.equals(ownerType.getName())){
			jstQualifier = VjoTranslateHelper.getStaticTypeQualifier(ownerType, jstNode, true);
			jstIdentifier = jstQualifier;
			jstIdentifier.setJstBinding(ownerType);
			jstIdentifier.setType(ownerType);
			return jstIdentifier;
		}
		// It's outer type
		else if (ownerType.getOuterType() != null && 
			(name.equals(ownerType.getOuterType().getSimpleName()) || name.equals(ownerType.getOuterType().getName()))) {
			jstIdentifier = new JstIdentifier(getCtx().getConfig().getVjoConvention().getOuterStaticPrefix());
			jstIdentifier.setJstBinding(ownerType.getOuterType());
			jstIdentifier.setType(ownerType.getOuterType());
			return jstIdentifier;
		}
		// It's inner type of ownerType
		else if (ownerType.getEmbededType(name) != null) {
			IJstType embededType = ownerType.getEmbededType(name);
			jstQualifier = VjoTranslateHelper.getStaticTypeQualifier(embededType, jstNode, true);
			jstIdentifier = new JstIdentifier(name, jstQualifier);
			jstIdentifier.setJstBinding(embededType);
			jstIdentifier.setType(embededType);
			return jstIdentifier;
		}
		// It's a type?
		else {
			IJstType type = getDataTypeTranslator().findJstType(name, ownerType);				
			if (type != null && type.getPackage() != null){
				jstIdentifier = TranslateHelper.Type.createIdentifier(type, jstNode);
				jstIdentifier.setJstBinding(type);
				jstIdentifier.setType(type);
				TranslateHelper.Type.validateTypeReference(type, astName, jstIdentifier, jstNode, this);
				
				return jstIdentifier;
			}
		}
		
		return null;
	}
	
	private JstIdentifier getEnumConstIdentifier(final String name, boolean needSuper, boolean hasThis, 
			final IExpr qualifierExpr, final BaseJstNode jstNode){
	
		IJstProperty jstPty = null;
		IJstType ownerType = jstNode.getOwnerType();
		IJstType scopeType = ownerType;
		if (qualifierExpr != null && qualifierExpr.getResultType() != null){
			scopeType = qualifierExpr.getResultType();
		}
		
		if (!scopeType.isEnum()){
			return null;
		}
		
		jstPty = scopeType.getEnumValue(name);
		if (jstPty == null){
			return null;
		}
		
		JstIdentifier jstIdentifier = new JstIdentifier(name);
		jstIdentifier.setType(jstPty.getType());
		jstIdentifier.setJstBinding(jstPty);
		JstIdentifier jstQualifier = VjoTranslateHelper.getStaticPtyOrMtdQualifier(jstIdentifier, jstNode);
		setNameQualifier(jstIdentifier, jstQualifier, jstNode);
		
		setNameIdentifierType(jstIdentifier, jstNode);
	
		return jstIdentifier;
	}
	
	private JstIdentifier getPropertyIdentifier(final Name astName, boolean hasSuper, boolean hasThis, 
				final IExpr qualifierExpr, final BaseJstNode jstNode){
		String name = processVarName(astName.toString());
		IJstProperty jstPty = null;
		VjoConvention convension = getCtx().getConfig().getVjoConvention();
		
		JstIdentifier jstIdentifier = new JstIdentifier(name);
		JstIdentifier jstQualifier = null;
		
		IJstType ownerType = jstNode.getOwnerType();
		IJstType scopeType = ownerType;		
		if (qualifierExpr != null && qualifierExpr.getResultType() != null){
			scopeType = qualifierExpr.getResultType();
		}
		
		if (scopeType instanceof JstType){
			TranslateHelper.Property.validateFieldReference(astName, jstNode, (JstType)scopeType, this);
		}
		
		boolean isMember = false;
		boolean isInherited = false;
		boolean isOuters = false;
		
		// It is super
		if (hasSuper){
			jstPty = scopeType.getExtend().getStaticProperty(name, true);
			if (jstPty == null){
				jstPty = scopeType.getExtend().getInstanceProperty(name, true);
			}
			if (jstPty != null){
				isInherited = true;
			}
		}
		// It could be this or super
		else {
			//Check in current types before searching recursive
			jstPty = scopeType.getInstanceProperty(name, false);
			if (jstPty == null){
				jstPty = scopeType.getStaticProperty(name, false);
			}
			if (jstPty == null){
				jstPty = scopeType.getInstanceProperty(name, true);
				if (jstPty == null){
					jstPty = scopeType.getStaticProperty(name, true);
				}
			}
			
			if (jstPty != null){
				if (jstPty.getOwnerType() == scopeType){
					isMember = true;
				}
				else {
					isInherited = true;
				}
			}
		}
		
		// Check outer type & its hierarchy
		if (jstPty == null){
			IJstType outerType = getContainingType(scopeType);
			while (outerType != null){
				jstPty = outerType.getStaticProperty(name, true);
				if (jstPty == null){
					jstPty = outerType.getInstanceProperty(name, true);
				}
				if (jstPty != null){
					isOuters = true;
					break;
				}
				outerType = getContainingType(outerType);
			}
		}
		
		if (jstPty != null){
			jstIdentifier.setJstBinding(jstPty); 
			jstIdentifier.setType(jstPty.getType());
			if (needReCalcQualifier(qualifierExpr,jstPty.isStatic())){
				if (isMember){
					if (jstPty.getModifiers().isStatic()){
						jstQualifier = VjoTranslateHelper.getStaticPtyOrMtdQualifier(jstIdentifier, jstNode);
					}
					else {
						jstQualifier = new JstIdentifier(convension.getThisPrefix());
						jstQualifier.setJstBinding(ownerType);
						jstQualifier.setType(ownerType);
					}
				}
				else if (isInherited){
					if (jstPty.getModifiers().isStatic()){
						jstQualifier = VjoTranslateHelper.getStaticPtyOrMtdQualifier(jstIdentifier, jstNode);
					}
					else if (qualifierExpr == null){
						jstQualifier = new JstIdentifier(convension.getThisPrefix());
						
						//commenting out this logic as it performs same operation for both paths.
//						if (hasSuper){
//							jstQualifier = new JstIdentifier(convension.getThisPrefix()); // Current VJO RT doesn't support separate storage for inherited ptys
//						}
//						else {
//							jstQualifier = new JstIdentifier(convension.getThisPrefix());
//						}
					}
					if (jstQualifier != null){
						IJstType binding = jstPty.getOwnerType();
						jstQualifier.setJstBinding(binding);
						jstQualifier.setType(binding);
						if (binding != null && !ownerType.getRootType().getImportsMap().values().contains(binding)){
							getDataTypeTranslator().addImport(binding, (JstType)ownerType, binding.getSimpleName());
						}
					}
				}
				else if (isOuters){
					if (jstPty.getModifiers().isStatic()){
						jstQualifier = VjoTranslateHelper.getStaticPtyOrMtdQualifier(jstIdentifier, jstNode);
					}
					else if (qualifierExpr == null){
						jstQualifier = VjoTranslateHelper.getInstanceMemberQualifier(jstPty.getOwnerType(), jstNode);
					}
	
					if (jstQualifier != null){
						jstQualifier.setJstBinding(jstPty.getOwnerType());
						jstQualifier.setType(jstPty.getOwnerType());
					}
				}
				
				setNameQualifier(jstIdentifier, jstQualifier, jstNode);
			}
		}
		
		setNameIdentifierType(jstIdentifier, jstNode);

		return jstIdentifier;
	}
	
	private JstIdentifier getMethodIdentifier(final Name astName, List<IExpr> args, boolean hasSuper, boolean hasThis,
			final IExpr qualifierExpr, final BaseJstNode jstNode){
		String name = processVarName(astName.toString());
		VjoConvention convension = getCtx().getConfig().getVjoConvention();
		IJstType ownerType = jstNode.getOwnerType();
		IJstType scopeType = ownerType;
		if (qualifierExpr != null && qualifierExpr.getResultType() != null){
			scopeType = qualifierExpr.getResultType();
		}

		String qualifier = null;
		IJstMethod jstMtd = null;
		JstIdentifier jstIdentifier = new JstIdentifier(name);
		JstIdentifier jstQualifier = null;
		
		boolean isMember = false;
		boolean isInherited = false;
		boolean isOuters = false;
		boolean isAnon = ownerType.isAnonymous();
		
		if (hasSuper){
			if (scopeType.getExtend() != null){
				jstMtd = TranslateHelper.Method.getMethod(astName.getParent(), scopeType.getExtend(), name, args);
				if (jstMtd != null){
					isInherited = true;
				}
			}
		}
		else {
			jstMtd = TranslateHelper.Method.getMethod(astName.getParent(), scopeType, name, args);
			if (jstMtd != null){
				if (jstMtd.getOwnerType() == scopeType){
					isMember = true;
				}
				else {
					isInherited = true;
				}
			}
		}

		// Check outer type & its hierarchy
		if (jstMtd == null){
			IJstType outerType = scopeType.getOuterType();
			while (outerType != null){
				jstMtd = TranslateHelper.Method.getMethod(astName.getParent(), outerType, name, args);
				if (jstMtd != null){
					isOuters = true;
					break;
				}
				outerType = outerType.getOuterType();
			}
		}

		// Check container type
		if (isAnon && jstMtd==null) {
			IJstType containerType = scopeType.getExtend();
			//String indent = convension.getThisPrefix();
			StringBuffer buf = new StringBuffer(convension.getThisPrefix());
			while (containerType != null){
				jstMtd = containerType.getStaticMethod(name, true);
				if (jstMtd == null) {
					jstMtd = containerType.getInstanceMethod(name, true);
				}
				if (jstMtd != null) {
					jstIdentifier.setJstBinding(jstMtd);
					if (jstMtd.getModifiers().isStatic()){
						jstQualifier = VjoTranslateHelper.getStaticPtyOrMtdQualifier(jstIdentifier, jstNode);
					} else if (qualifierExpr == null){
						jstQualifier = new JstIdentifier(buf.toString());
					}
					if (jstQualifier!=null) {
						jstQualifier.setJstBinding(jstMtd.getOwnerType());
						jstQualifier.setType(jstMtd.getOwnerType());
						setNameQualifier(jstIdentifier, jstQualifier, jstNode);
						setMtdIdentifierType(jstIdentifier, scopeType);
						return jstIdentifier;
					}
				}
				if (containerType.isEmbededType()) {
					containerType = (IJstType) containerType.getParentNode();
					buf.append(convension.getParentInstancePrefix());
				} else {
					break;
				}
			}
		}
		
		
		if (jstMtd != null){
			jstIdentifier.setJstBinding(jstMtd);
			if (needReCalcQualifier(qualifierExpr, jstMtd.isStatic())){
				if (isMember){
					if (hasThis){
						jstQualifier = new JstIdentifier(convension.getThisPrefix());
						jstQualifier.setJstBinding(ownerType);
						jstQualifier.setType(ownerType);
					}
				}
				else if (isInherited){
					if (jstMtd.getModifiers().isStatic()){
						jstQualifier = VjoTranslateHelper.getStaticPtyOrMtdQualifier(jstIdentifier, jstNode);
					}
					else if (qualifierExpr == null){
						if (hasSuper){
							jstQualifier = new JstIdentifier(convension.getBasePrefix());
						}
						else if (hasThis){
							jstQualifier = new JstIdentifier(convension.getThisPrefix());
						}
					}
					if (jstQualifier != null){
						IJstType binding = jstMtd.getOwnerType();
						jstQualifier.setJstBinding(binding);
						jstQualifier.setType(binding);
						if (binding != null && !ownerType.getRootType().getImportsMap().values().contains(binding)){
							getDataTypeTranslator().addImport(binding, (JstType)ownerType, binding.getSimpleName());
						}
					}
				}
				else if (isOuters){
					if (jstMtd.getModifiers().isStatic()){
						jstQualifier = VjoTranslateHelper.getStaticPtyOrMtdQualifier(jstIdentifier, jstNode);
					}
					else if (qualifierExpr == null){
						jstQualifier = VjoTranslateHelper.getInstanceMemberQualifier(jstMtd.getOwnerType(), jstNode);
					}
	
					if (jstQualifier != null){
						jstQualifier.setJstBinding(jstMtd.getOwnerType());
						jstQualifier.setType(jstMtd.getOwnerType());
					}
				}
				
				if (jstQualifier == null){
					jstIdentifier.setJstBinding(jstMtd);
					if (jstMtd.getOwnerType() == scopeType.getOuterType()){
						if (hasThis){
							qualifier = convension.getOuterStaticPrefix();
						}
					}
					else {
						if (hasSuper){
							qualifier = convension.getOuterStaticPrefix() + "." + convension.getBasePrefix();
						}
					}
					jstQualifier = new JstIdentifier(qualifier);
					jstQualifier.setJstBinding(jstMtd.getOwnerType());
					jstQualifier.setType(jstMtd.getOwnerType());
				}
				setNameQualifier(jstIdentifier, jstQualifier, jstNode);
			}
		}

		setMtdIdentifierType(jstIdentifier, scopeType);
		
		if (scopeType instanceof JstType){
			TranslateHelper.Method.validateMethodReference(jstMtd, astName, jstNode, this);
		}
		
		return jstIdentifier;
	}
	
	private boolean needReCalcQualifier(final IExpr qualifierExpr, boolean isStatic){
		return (qualifierExpr == null
				|| (qualifierExpr instanceof JstIdentifier && (((JstIdentifier) qualifierExpr)
						.getJstBinding() instanceof IJstType || (isStatic && ((JstIdentifier) qualifierExpr)
						.getType() instanceof IJstType))) || (isStatic && qualifierExpr instanceof ObjCreationExpr && ((ObjCreationExpr) qualifierExpr)
				.getResultType() instanceof IJstType));
	}
	
	private void setNameQualifier(final JstIdentifier jstIdentifier, final JstIdentifier qualifier, final BaseJstNode jstNode){
		if (jstIdentifier == null || qualifier == null){
			return;
		}
		if (TranslateHelper.isStaticMember(jstIdentifier)){
			JstIdentifier staticQualifier = null;
			IJstMethod jstMtd = TranslateHelper.Method.getOwnerMethod(jstNode);
			if (jstMtd != null && jstMtd.getOwnerType() instanceof JstType){
				TranslateInfo tInfo = TranslateCtx.ctx().getTranslateInfo((JstType)jstMtd.getOwnerType());
				MethodKey mtdKey = MethodKey.genMethodKey(jstMtd);
				if (tInfo.getMethodCustomInfo(mtdKey).isForceFullyQualify()){
					IJstNode binding = jstIdentifier.getJstBinding();
					if (binding != null && binding instanceof JstProperty){
						JstType qualifierType = ((JstProperty)binding).getOwnerType();
						if (!qualifierType.isEmbededType()){
							staticQualifier = new JstIdentifier(qualifierType.getName());
							staticQualifier.setType(qualifierType);
							staticQualifier.setJstBinding(qualifierType);
						}
					}
				}
			}
			else {
				staticQualifier = VjoTranslateHelper.getStaticPtyOrMtdQualifier(qualifier, jstNode);
			}
			if (staticQualifier != null){
				jstIdentifier.setQualifier(staticQualifier);
				return;
			}
		}
		jstIdentifier.setQualifier(qualifier);
	}
	
	private void setNameIdentifierType(final JstIdentifier jstIdentifier, BaseJstNode jstNode){
		if (jstIdentifier.getType() != null || jstNode == null){
			return;
		}
		String name = processVarName(jstIdentifier.getName());
		IJstType jstType = TranslateHelper.Type.getVarType(name, jstNode);
		if (jstType != null){
			jstIdentifier.setType(jstType);
		}
	}
	
	private void setMtdIdentifierType(final JstIdentifier jstIdentifier, IJstNode jstNode){

		IJstNode binding = jstIdentifier.getJstBinding();
		if (binding == null || !(binding instanceof IJstMethod)){
			return;
		}
		IJstMethod jstMtd = (IJstMethod)binding;
		if (jstMtd != null){
			jstIdentifier.setType(jstMtd.getRtnType());
		}
	}
	
	private static IJstType getContainingType(final IJstType type) {
		if (type.isAnonymous()) {
			IJstNode node = type;
			while ((node=node.getParentNode())!=null) {
				if (node instanceof IJstType) {
					return (IJstType)node;
				}
			}
			return null;
		} else {
			return type.getOuterType();
		}
	}
	
	private boolean isStaticInvocation(final IExpr qualifierExpr){
		if (qualifierExpr != null && qualifierExpr.toExprText().startsWith(VjoConvention.getThisPrefix())){
			return true;
		} else
			return false;
		
	}
}
