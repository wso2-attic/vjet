/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.vjet.eclipse.internal.ui.view.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.mod.wst.jsdt.internal.compiler.CompilationResult;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.AllocationExpression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Assignment;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.FunctionExpression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.LocalDeclaration;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.MessageSend;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.MethodDeclaration;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ObjectLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ObjectLiteralField;

/**
 * 
 *
 */
class ASTTreeContentProvider implements ITreeContentProvider {
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	public Object[] getChildren(Object parentElement) {
		if (parentElement.getClass().isArray()) {
			return (Object[])parentElement;
		}
		
		List children = new ArrayList();
		
		if (parentElement instanceof CompilationUnitDeclaration) {
			//record unit declaration
			CompilationUnitDeclaration unitDeclaration = (CompilationUnitDeclaration)parentElement;
			
			Collections.addAll(children, unitDeclaration.getStatements());
			children.add(unitDeclaration.compilationResult);
			return children.toArray();
		}
		
		//CompilationResult
		if (parentElement instanceof CompilationResult) {
			CompilationResult compilationResult = (CompilationResult)parentElement;
			if (compilationResult.getAllProblems() != null)
				Collections.addAll(children, compilationResult.getAllProblems());
			return children.toArray();
		}
		
		//MessageSend
		if (parentElement instanceof MessageSend) {
			MessageSend messageSend = (MessageSend)parentElement;
			
			if (messageSend.getReceiver() != null)
				children.add(messageSend.getReceiver());
			if (messageSend.getArguments() != null)
				Collections.addAll(children, messageSend.getArguments());
			return children.toArray();
		}
		
		//ObjectLiteral
		if (parentElement instanceof ObjectLiteral) {
			ObjectLiteral objectLiteral = (ObjectLiteral)parentElement;
			
			if (objectLiteral.getFields() != null) 
				Collections.addAll(children, objectLiteral.getFields());
			return children.toArray();
		}
		
		//ObjectLiteralField
		if (parentElement instanceof ObjectLiteralField) {
			ObjectLiteralField literalField = (ObjectLiteralField)parentElement;
			
			if (literalField.getInitializer() != null)
				children.add(literalField.getInitializer());
			if (literalField.getFieldName() != null)
				children.add(literalField.getFieldName());
			if (literalField.getJsDoc() != null)
				children.add(literalField.getJsDoc());
			return children.toArray();
		}
		
		//FunctionExpression
		if (parentElement instanceof FunctionExpression) {
			FunctionExpression functionExpression = (FunctionExpression)parentElement;
			
			if (functionExpression.getMethodDeclaration() != null)
				children.add(functionExpression.getMethodDeclaration());
			return children.toArray();
		}
		
		//MethodDeclaration
		if (parentElement instanceof MethodDeclaration) {
			MethodDeclaration methodDeclaration = (MethodDeclaration)parentElement;
			
			if (methodDeclaration.getJsDoc() != null)
				children.add(methodDeclaration.getJsDoc());
			if (methodDeclaration.getArguments() != null)
				Collections.addAll(children, methodDeclaration.getArguments());
			if (methodDeclaration.getStatements() != null)
				Collections.addAll(children, methodDeclaration.getStatements());
			return children.toArray();
		}
		
		//LocalDeclaration
		if (parentElement instanceof LocalDeclaration) {
			LocalDeclaration localDeclaration = (LocalDeclaration)parentElement;
			
			if (localDeclaration.binding != null)
				children.add(localDeclaration.binding);
			if (localDeclaration.getInitialization() != null)
				children.add(localDeclaration.getInitialization());
			if (localDeclaration.getTypeBinding() != null)
				children.add(localDeclaration.getTypeBinding());
			return children.toArray();
		}
		
		//Assignment
		if (parentElement instanceof Assignment) {
			Assignment assignment = (Assignment)parentElement;
			
			if (assignment.getExpression() != null)
				children.add(assignment.getExpression());
			if (assignment.getLeftHandSide() != null)
				children.add(assignment.getLeftHandSide());
			return children.toArray();
		}
		
		//AllocationExpression
		if (parentElement instanceof AllocationExpression) {
			AllocationExpression allocationExpression = (AllocationExpression)parentElement;
			
			if (allocationExpression.getMember() != null)
				children.add(allocationExpression.getMember());
			if (allocationExpression.arguments != null && allocationExpression.arguments.length > 0)
				Collections.addAll(children, allocationExpression.arguments);
			return children.toArray();
		}
		return new Object[0];
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	public Object getParent(Object element) {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

}
