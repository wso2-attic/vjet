/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.ui;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.reserved.JsCoreKeywords;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.core.IVjoSourceModule;
import org.ebayopensource.vjet.eclipse.core.VjoLanguageToolkit;
import org.ebayopensource.vjet.eclipse.internal.codeassist.select.VjoSelectionEngine;
import org.ebayopensource.vjet.eclipse.internal.ui.editor.VjoEditor;
import org.ebayopensource.vjo.tool.codecompletion.CodeCompletionUtils;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.mod.codeassist.ISelectionEngine;
import org.eclipse.dltk.mod.core.DLTKLanguageManager;
import org.eclipse.dltk.mod.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.internal.core.VjoSourceModule;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRewriteTarget;
import org.eclipse.jface.text.link.LinkedModeModel;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.DocumentChange;
import org.eclipse.ltk.core.refactoring.TextChange;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.TextEdit;

public class VjetUIUtils {

	/**
	 * Answer whether type is mixed
	 * @return boolean
	 */
	public static boolean isMixin(IType type) {
		ISourceModule sourceModule = type.getSourceModule();
		IJstType jstType = null;
		if (sourceModule instanceof VjoSourceModule) {
			jstType = ((IVjoSourceModule)sourceModule).getJstType();
		} else {
			String name = type.getScriptFolder().getElementName() + '.'
			+ type.getElementName();
			String groupName = type.getScriptProject().getElementName();
			jstType = TypeSpaceMgr.findType(groupName, name);
		}

		if(jstType == null)
			return false;
		
		return (jstType.hasMixins());
	}
	
	/**
	 * Get JstType from current vjo editor
	 * 
	 * @return
	 */
	public static IJstType getJstType(VjoEditor editor) {
		if (editor == null) {
			return null;
		}
		IModelElement modelElement = editor.getInputModelElement();
		if (modelElement instanceof IVjoSourceModule) {
			return ((IVjoSourceModule) modelElement).getJstType();
		} else {
			return null;
		}

	}
	
	/**
	 * Get document from VjoEditor
	 * @param vjoEditor
	 * @return
	 */
	public static IDocument getDocument(VjoEditor vjoEditor) {
		return vjoEditor.getScriptSourceViewer().getDocument();
	}


	public static int getCommentOffset(JstVars node, IDocument document) {
		int offset = node.getSource().getEndOffSet();
		try {
			char c = document.getChar(offset);
			while (c != ';' && c != '\r' && c != '\n') {
				offset++;
				c = document.getChar(offset);
			}
			if (c == '\r' || c == '\n') {
				return offset;
			} else if (c == ';') {
				return offset + 1;
			} else {
				return -1;
			}
		} catch (BadLocationException e) {
			return -1;
		}
	}
	public static int getCommentOffset(IJstProperty node, IDocument document) {
		int offset = node.getSource().getEndOffSet();
		try {
			char c = document.getChar(offset);
			while (c != ';' && c != '\r' && c != '\n') {
				offset++;
				c = document.getChar(offset);
			}
			if (c == '\r' || c == '\n') {
				return offset;
			} else if (c == ';') {
				return offset + 1;
			} else {
				return -1;
			}
		} catch (BadLocationException e) {
			return -1;
		}
	}
	public static int getCommentOffset(IJstMethod method, IDocument document) {
		int offset = method.getSource().getStartOffSet();
		try {
			int line = document.getLineOfOffset(offset);
			if (line == 0) {
				return -1;
			} else {
				offset = document.getLineOffset(line-1);
			}
			char c = document.getChar(offset);
			while (c != '\r' && c != '\n') {
				offset++;
				c = document.getChar(offset);
			}
			if (c == '\r' || c == '\n') {
				return offset;
			} else if (c == ';') {
				return offset + 1;
			} else {
				return -1;
			}
		} catch (BadLocationException e) {
			return -1;
		}
	}

	public static String getCommentText(JstVars node) {
		IJstType type = node.getType();
		if (type != null) {
			return CodeCompletionUtils.getAliasOrTypeName(node.getOwnerType(), type);
		} else {
			return "";
		}
	}

	public static String getCommentText(IJstMethod method) {
		
		String s  = "";
		if (!method.isConstructor()) {
			s = CodeassistUtils.calculateRtnType(method) + " ";
			
		}
		return JsCoreKeywords.EXT_PUBLIC + " " + s  + method.getName() + "()";
	}
	
	

	public static String getCommentText(IJstProperty node) {
		IJstType type = node.getType();
		if (type != null) {
			return CodeCompletionUtils.getAliasOrTypeName(node.getOwnerType(), type);
		} else {
			return "";
		}
	}

	public static void performChange(VjoEditor activeEditor, String text, int offset)
			throws CoreException {
		IDocument document = activeEditor.getScriptSourceViewer().getDocument();
		Change change = createTextChange(activeEditor, text, offset);
		if (change == null) {
			return;
		}
		IRewriteTarget rewriteTarget = null;
		try {
			if (change != null) {
				if (document != null) {
					LinkedModeModel.closeAllModels(document);
				}
				if (activeEditor != null) {
					rewriteTarget = (IRewriteTarget) activeEditor
							.getAdapter(IRewriteTarget.class);
					if (rewriteTarget != null) {
						rewriteTarget.beginCompoundChange();
					}
				}

				change.initializeValidationData(new NullProgressMonitor());
				change.perform(new NullProgressMonitor());
			}
		} finally {
			if (rewriteTarget != null) {
				rewriteTarget.endCompoundChange();
			}

			if (change != null) {
				change.dispose();
			}
		}
	}

	public static TextChange createTextChange(VjoEditor editor, String text,
			int offset) throws CoreException {
		TextChange change;
		change = new DocumentChange(text, editor.getViewer().getDocument());
		TextEdit rootEdit = new MultiTextEdit();
		change.setEdit(rootEdit);

		InsertEdit edit = new InsertEdit(offset, text);
		rootEdit.addChild(edit);
		return change;
	}

	/**
	 * Get the first matched node which can be commented
	 * @param node
	 * @return
	 */
	public static IJstNode getCommentableJstNode(IJstNode node) {
		while (node != null && !(node instanceof IJstMethod)
				&& !(node instanceof IJstProperty)
				&& !(node instanceof JstVars)) {
			node = node.getParentNode();
		}
		return node;
	}

	/**
	 * 
	 * @return the registered VJO selection engine
	 */
	public static VjoSelectionEngine getSelectionEngine() {
		IDLTKLanguageToolkit toolKit = VjoLanguageToolkit.getDefault();
		ISelectionEngine selectionEngine = DLTKLanguageManager
				.getSelectionEngine(toolKit.getNatureId());
		if (!(selectionEngine instanceof VjoSelectionEngine)) {
			return null;
		}
		VjoSelectionEngine vjoSelectionEngine = (VjoSelectionEngine) selectionEngine;
		return vjoSelectionEngine;
	}
	
	
}
