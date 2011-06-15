/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.Constructor;
import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.Property;

/**
 * http://www.w3.org/TR/1999/REC-html401-19991224/interact/forms.html#edef-OPTION
 *
 */
@DOMSupport(DomLevel.ZERO)
public interface Option extends HtmlElement {
	
	/**
	 * Constructor
	 */
	@Constructor void Option();
	
	/**
	 * Constructor
	 * @param text Specifies the text for the option
	 */
	@Constructor void Option(String text);
	
	/**
	 * Constructor
	 * @param text Specifies the text for the option
	 * @param value Specifies the value for the option
	 */
	@Constructor void Option(String text, Object value);
	
	/**
	 * Constructor
	 * @param text Specifies the text for the option
	 * @param value Specifies the value for the option
	 * @param defaultSelected A boolean specifying whether this option is initially selected.
	 */
	@Constructor void Option(String text, Object value, boolean defaultSelected);
	
	/**
	 * Constructor
	 * @param text Specifies the text for the option
	 * @param value Specifies the value for the option
	 * @param defaultSelected A boolean specifying whether this option is initially selected
	 * @param selected boolean that specifies whether this option is currently selected
	 */
	@Constructor void Option(String text, Object value, boolean defaultSelected, boolean selected);
	
	/**
	 * Constructor
	 * @param text Specifies the text for the option
	 * @param value Specifies the value for the option
	 * @param defaultSelected A boolean specifying whether this option is initially selected
	 * @param selected boolean that specifies whether this option is currently selected
	 */
	@Constructor void Option(String text, Object value, int defaultSelected, int selected);
	
	@Property  HtmlForm getForm();

	@Property  boolean getDefaultSelected();
	@Property  void setDefaultSelected(boolean defaultSelected);

	@Property  String getText();
	@Property  void setText(String text);
    
	@Property  int getIndex();

	@Property  boolean getDisabled();
	@Property  void setDisabled(boolean disabled);

	@Property  String getLabel();
	@Property  void setLabel(String label);

	@Property  boolean getSelected();
	@Property  void setSelected(boolean selected);

	@Property  String getValue();
	@Property  void setValue(String value);

}
