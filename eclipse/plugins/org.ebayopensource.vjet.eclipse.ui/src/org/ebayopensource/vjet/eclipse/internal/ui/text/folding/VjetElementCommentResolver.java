/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.text.folding;

import org.eclipse.dltk.mod.core.IField;
import org.eclipse.dltk.mod.core.IMethod;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IModelElementVisitor;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.ISourceRange;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.core.SourceRefElement;
import org.eclipse.dltk.mod.internal.core.SourceType;
import org.eclipse.dltk.mod.internal.core.VjoSourceModule;
import org.eclipse.dltk.mod.ui.text.folding.IElementCommentResolver;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;

public class VjetElementCommentResolver implements IElementCommentResolver {

	/**
	 * Determines the element that related with the clicked comment
	 * 
	 * @throws ModelException
	 */
	protected IModelElement getContainingElement(IModelElement el, int offset, int length) throws ModelException {
		final PositionVisitor visitor = new PositionVisitor(offset, length);
		el.accept(visitor);
		return visitor.result;
	}

	/**
	 * Returns the model element that the comment corresponds to
	 */
	public IModelElement getElementByCommentPosition(ISourceModule content, int offset, int length) {
		try {
			return getElementByCommentPositionImpl(content, offset, length);
		} catch (BadLocationException e1) {
			return null;
		} catch (ModelException e) {
			return null;
		}
	}

	protected IModelElement getElementByCommentPositionImpl(ISourceModule content, int offset, int length) throws BadLocationException, ModelException {

		Document d = new Document(content.getSource());

		// Determine that the desired position is inside a comment
		// if (!checkIfPositionIsComment(d, offset))
		// return null;

		// Determine the innermost element that contains the clicked comment
		// (for example, class declaration)
		IModelElement el = getContainingElement(content, offset, length);

		// If the comment is inside a method, we do not need to process further
		if (el != null && el.getElementType() == IModelElement.METHOD)
			return el;

		// Determine the position after which the search will be stopped - for
		// example, EOF or end of the class declaration
		int sourceRangeEnd = getSourceRangeEnd(d, el);

		// Search for first non-comment element after the clicked comment
		IModelElement res = searchForNonCommentElement(d, content, offset + length, sourceRangeEnd);
		if (res == null)
			return el;
		return res;
	}

	protected int getSourceRangeEnd(Document d, IModelElement el) throws ModelException {

		int sourceRangeEnd = d.getLength();

		// If the comment is inside a class, we need to stop searching the
		// element once we leave the class boundaries
		if (el != null && el.getElementType() == IModelElement.TYPE) {
			SourceType t = (SourceType) el;
			sourceRangeEnd = t.getSourceRange().getOffset() + t.getSourceRange().getLength();

		}
		return sourceRangeEnd;
	}

//	protected boolean checkIfPositionIsComment(Document d, int offset) throws BadLocationException {
//		int line = d.getLineOfOffset(offset);
//		int q = d.getLineOffset(line);
//
//		while (q < d.getLength() && Character.isWhitespace(d.getChar(q)) && q <= offset) {
//			q++;
//		}
//
//		if (d.getChar(q) != '#') {
//			/* First non-space char is not a comment start, so stop processing */
//			return false;
//		}
//
//		return true;
//	}

	protected IModelElement searchForNonCommentElement(Document d, ISourceModule content, int endOfCommentOffset, int lowerbound) throws BadLocationException,
			ModelException {
		IModelElement res = null;
		int off = endOfCommentOffset;
		int line = d.getLineOfOffset(off);
		off = d.getLineOffset(line);
		while (off < lowerbound) {

			while (off < lowerbound - 1 && Character.isWhitespace(d.getChar(off))) {
				off++;
			}

			if (d.getChar(off) != '#') {
				// It's neither a comment nor whitespace, so we can get the
				// model element at this position
				res = content.getElementAt(off);
				break;
			}
			line++;
			off = d.getLineOffset(line);

		}

		return res;
	}

	/**
	 * Visitor to search the AST for elements that contain the clicked comment
	 */
	private static class PositionVisitor implements IModelElementVisitor {

		IModelElement result = null;
		private final int commentOffset;
		private final int commentLength;
		private int lowestOffset = -1;

		public PositionVisitor(int offset, int length) {
			this.commentOffset = offset;
			this.commentLength = length;
		}

		public boolean visit(IModelElement el) {

			//only the below 4 model Element can have javadoc
			if (el instanceof IType || el instanceof IMethod || el instanceof ISourceModule || el instanceof IField) {
				ISourceRange range = null;

				try {
					if (el instanceof VjoSourceModule) {
						range = ((VjoSourceModule) el).getSourceRange();
					}

					if (el instanceof SourceRefElement) {
						// The comment is entirely inside the element
						range = ((SourceRefElement) el).getSourceRange();

					}

					if (range != null) {

						//is containing by current method or type element
						if (commentOffset >= range.getOffset() && commentOffset + commentLength <= range.getOffset() + range.getLength()) {
							if (el.getElementType() == IModelElement.METHOD) {
								//in a method, is not javadoc?
								result = el;
								return false;
							}
						}

						if (commentOffset + commentLength <= range.getOffset()) {
							int offsetDistance = range.getOffset() - commentOffset;
							if (result == null || offsetDistance < lowestOffset) {
								
								if (el instanceof VjoSourceModule) {
									result = ((VjoSourceModule) el).getTypes()[0];
									lowestOffset = offsetDistance;
								}

								if (el.getElementType() == IModelElement.METHOD || el.getElementType() == IModelElement.TYPE
										|| el.getElementType() == IModelElement.FIELD) {

									result = el;
									lowestOffset = offsetDistance;
								}

							}
						}
					}
				} catch (ModelException e) {
					e.printStackTrace();
				}

				return true;
			}

			return false;
		}

	}

}
