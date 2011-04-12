/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.javatojs.anno.AJavaOnly;
import org.ebayopensource.dsf.javatojs.anno.ARename;
import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.Property;

/**
 * http://www.w3.org/TR/REC-html40/struct/tables.html#edef-TR
 */
@Alias("HTMLTableRowElement")
@DOMSupport(DomLevel.ONE)
@JsMetatype
public interface HtmlTableRow extends HtmlElement {
	
	/** "left" */
	@AJavaOnly @ARename(name="'left'")
	String ALIGN_LEFT =    "left" ;
	/** "center" */
	@AJavaOnly @ARename(name="'center'")
	String ALIGN_CENTER =  "center" ; // ignoreHtmlKeyword
	/** "right" */
	@AJavaOnly @ARename(name="'right'")
	String ALIGN_RIGHT =   "right" ;
	/** "justify" */
	@AJavaOnly @ARename(name="'justify'")
	String ALIGN_JUSTIFY = "justify" ;
	/** "char" */
	@AJavaOnly @ARename(name="'char'")
	String ALIGN_CHAR =    "char" ;

	/** "top" */
	@AJavaOnly @ARename(name="'top'")
	String VALIGN_TOP =      "top" ;
	/** "middle" */
	@AJavaOnly @ARename(name="'middle'")
	String VALIGN_MIDDLE =   "middle" ;
	/** "bottom" */
	@AJavaOnly @ARename(name="'bottom'")
	String VALIGN_BOTTOM =   "bottom" ;
	/** "baseline" */
	@AJavaOnly @ARename(name="'baseline'")
	String VALIGN_BASELINE = "baseline" ;
	
	@Property int getRowIndex();

    @Property int getSectionRowIndex();

    @Property HtmlCollection getCells();

    @Property String getAlign();
    @Property void setAlign(String align);

    @Property String getBgColor();
    @Property void setBgColor(String bgColor);

    @Property String getCh();
    @Property void setCh(String ch);

    @Property String getChOff();
    @Property void setChOff(String chOff);

    @Property String getVAlign();
    @Property void setVAlign(String vAlign);
    
	/**
	 * Returns the onblur event handler code on the current element. 
	 * @see http://www.w3schools.com/jsref/jsref_onblur.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onblur")
	Object getOnBlur();
	
	/**
	 * Sets the onblur event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onblur.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onblur")
	void setOnBlur(Object functionRef);
	
	/**
	 * Returns the onfocus event handler code on the current element.
	 * @see http://www.w3schools.com/jsref/jsref_onfocus.asp 
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onfocus")
	Object getOnFocus();
	
	/**
	 * Sets the onfocus event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onfocus.asp 
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onfocus")
	void setOnFocus(Object functionRef);
	
	/**
	 * Returns the onkeydown event handler code on the current element. 
	 * @see http://www.w3schools.com/jsref/jsref_onkeydown.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onkeydown")
	Object getOnKeyDown();
	
	/**
	 * Sets the onkeydown event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onkeydown.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onkeydown")
	void setOnKeyDown(Object functionRef);
	
	/**
	 * Returns the onkeypress event handler code on the current element. 
	 * @see http://www.w3schools.com/jsref/jsref_onkeypress.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onkeypress")
	Object getOnKeyPress();
	
	/**
	 * Sets the onkeypress event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onkeypress.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onkeypress")
	void setOnKeyPress(Object functionRef);
	
	/**
	 * Returns the onkeyup event handler code on the current element. 
	 * @see http://www.w3schools.com/jsref/jsref_onkeyup.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onkeyup")
	Object getOnKeyUp();
	
	/**
	 * Sets the onkeyup event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onkeyup.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onkeyup")
	void setOnKeyUp(Object functionRef);
	
	/**
	 * Returns the onclick event handler code on the current element. 
	 * @see http://www.w3schools.com/jsref/jsref_onclick.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onclick")
	Object getOnClick();
	
	/**
	 * Sets the onclick event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onclick.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onclick")
	void setOnClick(Object functionRef);
	
	/**
	 * Returns the ondblclick event handler code on the current element. 
	 * @see http://www.w3schools.com/jsref/jsref_ondblclick.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="ondblclick")
	Object getOnDblClick();
	
	/**
	 * Sets the ondblclick event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_ondblclick.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="ondblclick")
	void setOnDblClick(Object functionRef);
	
	/**
	 * Returns the onmousedown event handler code on the current element. 
	 * @see http://www.w3schools.com/jsref/jsref_onmousedown.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onmousedown")
	Object getOnMouseDown();
	
	/**
	 * Sets the onmousedown event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onmousedown.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onmousedown")
	void setOnMouseDown(Object functionRef);
	
	/**
	 * Returns the onmouseup event handler code on the current element. 
	 * @see http://www.w3schools.com/jsref/jsref_onmouseup.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onmouseup")
	Object getOnMouseUp();
	
	/**
	 * Sets the onmouseup event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onmouseup.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onmouseup")
	void setOnMouseUp(Object functionRef);
	
	/**
	 * Returns the onmousemove event handler code on the current element. 
	 * @see http://www.w3schools.com/jsref/jsref_onmousemove.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onmousemove")
	Object getOnMouseMove();
	
	/**
	 * Sets the onmousemove event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onmousemove.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onmousemove")
	void setOnMouseMove(Object functionRef);
	
	/**
	 * Returns the onmouseout event handler code on the current element.
	 * @see http://www.w3schools.com/jsref/jsref_onmouseout.asp 
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onmouseout")
	Object getOnMouseOut();
	
	/**
	 * Sets the onmouseout event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onmouseout.asp 
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onmouseout")
	void setOnMouseOut(Object functionRef);
	
	/**
	 * Returns the onmouseover event handler code on the current element. 
	 * @see http://www.w3schools.com/jsref/jsref_onmouseover.asp 
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onmouseover")
	Object getOnMouseOver();
	
	/**
	 * Sets the onmouseover event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onmouseover.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onmouseover")
	void setOnMouseOver(Object functionRef);

    @Function HtmlTableCell insertCell(int index);

    @Function void deleteCell(int index);

}
