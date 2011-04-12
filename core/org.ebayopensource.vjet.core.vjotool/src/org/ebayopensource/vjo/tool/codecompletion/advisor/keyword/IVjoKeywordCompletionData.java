/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.advisor.keyword;

import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;

public interface IVjoKeywordCompletionData extends IVjoCcProposalData{


	boolean canComplete(String text);

//	/**
//	 * Gets cursor offset after completion.
//	 * 
//	 * @return cursor offset
//	 */
//	int getCursorOffsetAfterCompletion();
//
//
//	/**
//	 * Returns the name of this completion data
//	 * 
//	 * @return the name of this completion data.
//	 */
//	String getName();
//
//	/**
//	 * Sets the modifier flags relevant in the context.
//	 * <p>
//	 * If not set, defaults to none.
//	 * </p>
//	 * <p>
//	 * The completion engine creates instances of this class and sets its
//	 * properties; this method is not intended to be used by other clients.
//	 * </p>
//	 * 
//	 * @param flags
//	 *            the modifier flags, or <code>Flags.AccDefault</code> if none
//	 */
//	public void setFlags(int flags);
//
//	/**
//	 * Returns the modifier flags relevant in the context, or
//	 * <code>Flags.AccDefault</code> if none.
//	 * <p>
//	 * This field is available for the following kinds of completion proposals:
//	 * <ul>
//	 * <li><code>ANNOTATION_ATTRIBUT_REF</code> - modifier flags of the
//	 * attribute that is referenced;
//	 * <li><code>ANONYMOUS_CLASS_DECLARATION</code> - modifier flags of the
//	 * constructor that is referenced</li>
//	 * <li><code>FIELD_REF</code> - modifier flags of the field that is
//	 * referenced; <code>Flags.AccEnum</code> can be used to recognize
//	 * references to enum constants </li>
//	 * <li><code>KEYWORD</code> - modifier flag corrresponding to the
//	 * modifier keyword</li>
//	 * <li><code>LOCAL_VARIABLE_REF</code> - modifier flags of the local
//	 * variable that is referenced</li>
//	 * <li><code>METHOD_REF</code> - modifier flags of the method that is
//	 * referenced; <code>Flags.AccAnnotation</code> can be used to recognize
//	 * references to annotation type members </li>
//	 * <li><code>METHOD_DECLARATION</code> - modifier flags for the method
//	 * that is being implemented or overridden</li>
//	 * <li><code>TYPE_REF</code> - modifier flags of the type that is
//	 * referenced; <code>Flags.AccInterface</code> can be used to recognize
//	 * references to interfaces, <code>Flags.AccEnum</code> enum types, and
//	 * <code>Flags.AccAnnotation</code> annotation types </li>
//	 * <li><code>VARIABLE_DECLARATION</code> - modifier flags for the
//	 * variable being declared</li>
//	 * <li><code>POTENTIAL_METHOD_DECLARATION</code> - modifier flags for the
//	 * method that is being created</li>
//	 * </ul>
//	 * For other kinds of completion proposals, this method returns
//	 * <code>Flags.AccDefault</code>.
//	 * </p>
//	 * 
//	 * @return the modifier flags, or <code>Flags.AccDefault</code> if none
//	 */
//	public int getFlags();
//
//	/**
//	 * Returns the value of the type of this completion data.
//	 * 
//	 * @return the value of the type of this completion data.
//	 */
//	public int getKeywordType();
//
//	/**
//	 * Sets the new value for the type of this completion data.
//	 * 
//	 * @param type
//	 *            the new value for the type of this completion data.
//	 */
//	public void setKeywordType(int type);
//
//	public void setProposal(boolean isRender);
//
//	public boolean isProposal();
//
//	public int getRelevance();
//
//	public void setRelevance(int relevance);
}
