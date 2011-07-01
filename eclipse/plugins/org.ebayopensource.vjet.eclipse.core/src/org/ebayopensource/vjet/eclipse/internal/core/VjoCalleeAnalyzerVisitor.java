/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jesper Kamstrup Linnet (eclipse@kamstrup-linnet.dk) - initial API and implementation 
 *          (report 36180: Callers/Callees view)
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.core;

import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.core.IVjoSourceModule;
import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.ebayopensource.vjet.eclipse.core.ts.GenericVisitor;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.dltk.mod.ast.references.SimpleReference;
import org.eclipse.dltk.mod.core.IMethod;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.ISourceRange;
import org.eclipse.dltk.mod.core.ModelException;

class VjoCalleeAnalyzerVisitor extends GenericVisitor {
	private Map fSearchResults;
	private IMethod fMethod;
	private IJstType fCompilationUnit;
	private IProgressMonitor fProgressMonitor;
	private int fMethodEndPosition;
	private int fMethodStartPosition;

	VjoCalleeAnalyzerVisitor(IMethod method, IJstType jstType, IProgressMonitor progressMonitor) {
		fSearchResults = new HashMap();
		this.fMethod = method;
		this.fCompilationUnit = jstType;
		this.fProgressMonitor = progressMonitor;
		try {
			ISourceRange sourceRange = method.getSourceRange();
			this.fMethodStartPosition = sourceRange.getOffset();
			this.fMethodEndPosition = fMethodStartPosition + sourceRange.getLength();
		} catch (ModelException jme) {
			VjetPlugin.error("Model exception occurs when getting source range", jme);
		}
	}

	/**
	 * Method getCallees.
	 * 
	 * @return CallerElement
	 */
	public Map getCallees() {
		return fSearchResults;
	}

	// /* (non-Javadoc)
	// * @see
	// org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.ClassInstanceCreation)
	// */
	// public boolean visit(ClassInstanceCreation node) {
	// progressMonitorWorked(1);
	// if (!isFurtherTraversalNecessary(node)) {
	// return false;
	// }
	//
	// if (isNodeWithinMethod(node)) {
	// addMethodCall(node.resolveConstructorBinding(), node);
	// }
	//
	// return true;
	// }
	//
	// /**
	// * Find all constructor invocations (<code>this(...)</code>) from the
	// called method.
	// * Since we only traverse into the AST on the wanted method declaration,
	// this method
	// * should not hit on more constructor invocations than those in the wanted
	// method.
	// *
	// * @see
	// org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.ConstructorInvocation)
	// */
	// public boolean visit(ConstructorInvocation node) {
	// progressMonitorWorked(1);
	// if (!isFurtherTraversalNecessary(node)) {
	// return false;
	// }
	//
	// if (isNodeWithinMethod(node)) {
	// addMethodCall(node.resolveConstructorBinding(), node);
	// }
	//
	// return true;
	// }
	//
	// /**
	// * @see
	// org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.MethodDeclaration)
	// */
	// public boolean visit(MethodDeclaration node) {
	// progressMonitorWorked(1);
	// return isFurtherTraversalNecessary(node);
	// }
	//
	// /**
	// * Find all method invocations from the called method. Since we only
	// traverse into
	// * the AST on the wanted method declaration, this method should not hit on
	// more
	// * method invocations than those in the wanted method.
	// *
	// * @see
	// org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.MethodInvocation)
	// */
	// public boolean visit(MethodInvocation node) {
	// progressMonitorWorked(1);
	// if (!isFurtherTraversalNecessary(node)) {
	// return false;
	// }
	//
	// if (isNodeWithinMethod(node)) {
	// addMethodCall(node.resolveMethodBinding(), node);
	// }
	//
	// return true;
	// }
	//
	// /**
	// * Find invocations of the supertype's constructor from the called method
	// * (=constructor). Since we only traverse into the AST on the wanted
	// method
	// * declaration, this method should not hit on more method invocations than
	// those in
	// * the wanted method.
	// *
	// * @see
	// org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.SuperConstructorInvocation)
	// */
	// public boolean visit(SuperConstructorInvocation node) {
	// progressMonitorWorked(1);
	// if (!isFurtherTraversalNecessary(node)) {
	// return false;
	// }
	//
	// if (isNodeWithinMethod(node)) {
	// addMethodCall(node.resolveConstructorBinding(), node);
	// }
	//        
	// return true;
	// }
	//
	// /**
	// * Find all method invocations from the called method. Since we only
	// traverse into
	// * the AST on the wanted method declaration, this method should not hit on
	// more
	// * method invocations than those in the wanted method.
	// *
	// * @see
	// org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.MethodInvocation)
	// */
	// public boolean visit(SuperMethodInvocation node) {
	// progressMonitorWorked(1);
	// if (!isFurtherTraversalNecessary(node)) {
	// return false;
	// }
	//
	// if (isNodeWithinMethod(node)) {
	// addMethodCall(node.resolveMethodBinding(), node);
	// }
	//        
	// return true;
	// }
	//    
	// /**
	// * When an anonymous class declaration is reached, the traversal should
	// not go further since it's not
	// * supposed to consider calls inside the anonymous inner class as calls
	// from the outer method.
	// *
	// * @see
	// org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.AnonymousClassDeclaration)
	// */
	// public boolean visit(AnonymousClassDeclaration node) {
	// return isNodeEnclosingMethod(node);
	// }

	private void progressMonitorWorked(int work) {
		if (fProgressMonitor != null) {
			fProgressMonitor.worked(work);
			if (fProgressMonitor.isCanceled()) {
				throw new OperationCanceledException();
			}
		}
	}

	
	
	
	@Override
	public void visit(MtdInvocationExpr expr) {
		progressMonitorWorked(1);
		
		//what we've put here should be different than CodeAssistUtils.
		IModelElement[] modelElement=CodeassistUtils.getModelElementByMtdInvoExpr(expr, (IVjoSourceModule)this.fMethod.getSourceModule());
		if (modelElement != null&&modelElement instanceof IMethod[])
			addMethodCall( (IMethod[])modelElement, expr);

	}

	

	protected void addMethodCall(IMethod[] calledMethod, MtdInvocationExpr node) {
		JstSource jstSource = node.getMethodIdentifier().getSource();
		String methodName = node.getMethodIdentifier().toString();
		SimpleReference ref = null;
		if (jstSource != null) {
			ref = new SimpleReference(jstSource.getStartOffSet(), jstSource.getStartOffSet() + methodName.length(), node.toExprText());
		} else {
			ref = new SimpleReference(-1, 0, node.toExprText());
		}
		fSearchResults.put(ref,  calledMethod );
	}

//	protected void addMethodCall(IMethodBinding calledMethodBinding, ASTNode node) {
//		try {
//			if (calledMethodBinding != null) {
//				fProgressMonitor.worked(1);
//
//				ITypeBinding calledTypeBinding = calledMethodBinding.getDeclaringClass();
//				IType calledType = null;
//
//				if (!calledTypeBinding.isAnonymous()) {
//					calledType = (IType) calledTypeBinding.getJavaElement();
//				} else {
//					if (!"java.lang.Object".equals(calledTypeBinding.getSuperclass().getQualifiedName())) { //$NON-NLS-1$
//						calledType = (IType) calledTypeBinding.getSuperclass().getJavaElement();
//					} else {
//						calledType = (IType) calledTypeBinding.getInterfaces()[0].getJavaElement();
//					}
//				}
//
//				IMethod calledMethod = findIncludingSupertypes(calledMethodBinding, calledType, fProgressMonitor);
//
//				IMember referencedMember = null;
//				if (calledMethod == null) {
//					if (calledMethodBinding.isConstructor() && calledMethodBinding.getParameterTypes().length == 0) {
//						referencedMember = calledType;
//					}
//				} else {
//					if (calledType.isInterface()) {
//						calledMethod = findImplementingMethods(calledMethod);
//					}
//
//					if (!isIgnoredBySearchScope(calledMethod)) {
//						referencedMember = calledMethod;
//					}
//				}
//				final int position = node.getStartPosition();
//				final int number = fCompilationUnit.getLineNumber(position);
//				fSearchResults.addMember(fMethod, referencedMember, position, position + node.getLength(), number < 1 ? 1 : number);
//			}
//		} catch (JavaModelException jme) {
//			JavaPlugin.log(jme);
//		}
//	}

}
