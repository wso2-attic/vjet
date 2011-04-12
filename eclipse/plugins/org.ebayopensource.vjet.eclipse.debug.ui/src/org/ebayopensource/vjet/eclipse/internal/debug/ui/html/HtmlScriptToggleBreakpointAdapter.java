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
package org.ebayopensource.vjet.eclipse.internal.debug.ui.html;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.model.ILineBreakpoint;
import org.eclipse.dltk.mod.debug.ui.breakpoints.ScriptToggleBreakpointAdapter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionList;
import org.eclipse.wst.xml.core.internal.regions.DOMRegionContext;
import org.eclipse.wst.xml.core.internal.text.XMLStructuredDocumentRegion;

import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.ebayopensource.vjet.eclipse.internal.debug.VjetDebugConstants;

/**
 * 
 *
 */
public class HtmlScriptToggleBreakpointAdapter extends
		ScriptToggleBreakpointAdapter {

	/* (non-Javadoc)
	 * @see org.eclipse.dltk.mod.debug.ui.breakpoints.ScriptToggleBreakpointAdapter#getDebugModelId()
	 */
	@Override
	protected String getDebugModelId() {
		return VjetDebugConstants.DEBUG_MODEL_ID;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.actions.IToggleBreakpointsTargetExtension#canToggleBreakpoints(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public boolean canToggleBreakpoints(IWorkbenchPart part,
			ISelection selection) {
		//check for script nature
		if (!this.isInVjoProject(part))
			return false;
		
		//determine whether in wst html structured document or not
		IStructuredDocument structuredDocument = this.getStructuredDocument(part);
		if (structuredDocument == null)
			return false;
		
		//determine whether in html script region or not
		IStructuredDocumentRegion structuredDocumentRegion = structuredDocument.getRegionAtCharacterOffset(((ITextSelection)selection).getOffset());
		if (!this.isInHtmlScriptBlock(structuredDocumentRegion))
			return false;
		
		return true;
	}

	private boolean isInVjoProject(IWorkbenchPart part) {
		try {
			IFileEditorInput fileEditorInput = (IFileEditorInput)((IEditorPart)part).getEditorInput();
			IFile htmlFile = fileEditorInput.getFile();
			boolean isVJOProject = htmlFile.getProject().getDescription().hasNature(VjoNature.NATURE_ID);
			return isVJOProject;
		}
		catch (CoreException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private IStructuredDocument getStructuredDocument(IWorkbenchPart part) {
		IEditorInput editorInput = ((TextEditor)part).getEditorInput();
		IDocument document = ((TextEditor)part).getDocumentProvider().getDocument(editorInput);
		if (document instanceof IStructuredDocument)
			return (IStructuredDocument)document;
		else
			return null;
	}
	
	private boolean isInHtmlScriptBlock(IStructuredDocumentRegion structuredDocumentRegion) {
		if (!(DOMRegionContext.BLOCK_TEXT == structuredDocumentRegion.getType()))
			return false;
		
		IStructuredDocumentRegion scriptBeginRegion = this.getRelatedScriptBeginRegion(structuredDocumentRegion);
		IStructuredDocumentRegion scriptEndRegion = this.getRelatedScriptEndRegion(structuredDocumentRegion);
		
		return scriptBeginRegion != null && scriptEndRegion != null;
	}
	
	private IStructuredDocumentRegion getRelatedScriptBeginRegion(IStructuredDocumentRegion structuredDocumentRegion) {
		while (structuredDocumentRegion.getPrevious() != null) {
			structuredDocumentRegion = structuredDocumentRegion.getPrevious();
			
			if (structuredDocumentRegion instanceof XMLStructuredDocumentRegion
					&& structuredDocumentRegion.getFirstRegion().getType() == DOMRegionContext.XML_TAG_OPEN) {
				//check tag name
				ITextRegionList textRegionList = structuredDocumentRegion.getRegions();
				for (Iterator iterator = textRegionList.iterator(); iterator.hasNext();) {
					ITextRegion textRegion = (ITextRegion) iterator.next();
					
					if (DOMRegionContext.XML_TAG_NAME == textRegion.getType() 
							&& structuredDocumentRegion.getText(textRegion).equals("script"))
						return structuredDocumentRegion;
				}
			}
		}
		return null;
	}
	
	private IStructuredDocumentRegion getRelatedScriptEndRegion(IStructuredDocumentRegion structuredDocumentRegion) {
		while (structuredDocumentRegion.getNext() != null) {
			structuredDocumentRegion = structuredDocumentRegion.getNext();
			if (structuredDocumentRegion instanceof XMLStructuredDocumentRegion 
					&& "</script>".equals(structuredDocumentRegion.getText()))
				return structuredDocumentRegion;
			
//			if (structuredDocumentRegion instanceof XMLStructuredDocumentRegion
//					&& structuredDocumentRegion.getLastRegion().getType() == DOMRegionContext.XML_TAG_CLOSE) {
//				//check tag name
//				ITextRegionList textRegionList = structuredDocumentRegion.getRegions();
//				for (Iterator iterator = textRegionList.iterator(); iterator.hasNext();) {
//					ITextRegion textRegion = (ITextRegion) iterator.next();
//					
//					if (DOMRegionContext.XML_TAG_NAME == textRegion.getType() 
//							&& structuredDocumentRegion.getText(textRegion).equals("script"))
//						return structuredDocumentRegion;
//				}
//			}
			
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.actions.IToggleBreakpointsTargetExtension#toggleBreakpoints(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public void toggleBreakpoints(IWorkbenchPart part, ISelection selection)
			throws CoreException {
		IStructuredDocument structuredDocument = this.getStructuredDocument(part);
		IStructuredDocumentRegion structuredDocumentRegion = structuredDocument.getRegionAtCharacterOffset(((ITextSelection)selection).getOffset());
		
		//compute corresponding script start line number
		int scriptStartLineNumber = this.getScriptStartLineNumber(structuredDocumentRegion);
		
		//compute document line number
		int selectionLineNumber = ((ITextSelection)selection).getStartLine() + 1;
		
		//create script breakpoint and attach script offset line number
		ILineBreakpoint breakpoint = HtmlBreakpointUtils.findLineBreakpoint((TextEditor)part, selectionLineNumber);
		if (breakpoint != null) {
			breakpoint.delete();
			return;
		}
		
		HtmlBreakpointUtils.addLineBreakpoint((TextEditor)part, selectionLineNumber);
	}
	
	private int getScriptStartLineNumber(IStructuredDocumentRegion selectedRegion) {
		IStructuredDocumentRegion scripBeginRegion = this.getRelatedScriptBeginRegion(selectedRegion);
		int scriptStartDocumentLineNumber = selectedRegion.getParentDocument().getLineOfOffset(scripBeginRegion.getStartOffset());
		
		return scriptStartDocumentLineNumber;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.actions.IToggleBreakpointsTarget#canToggleMethodBreakpoints(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public boolean canToggleMethodBreakpoints(IWorkbenchPart part,
			ISelection selection) {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.actions.IToggleBreakpointsTarget#canToggleWatchpoints(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public boolean canToggleWatchpoints(IWorkbenchPart part,
			ISelection selection) {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.actions.IToggleBreakpointsTarget#toggleMethodBreakpoints(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public void toggleMethodBreakpoints(IWorkbenchPart part,
			ISelection selection) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.actions.IToggleBreakpointsTarget#toggleWatchpoints(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public void toggleWatchpoints(IWorkbenchPart part, ISelection selection)
			throws CoreException {
	}

}
