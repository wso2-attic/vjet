/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.Property;

/**
 * http://www.w3.org/TR/1999/REC-html401-19991224/interact/forms.html#edef-OPTION
 *
 */
@Alias("HTMLOptionElement")
@DOMSupport(DomLevel.ONE)
@JsMetatype
public interface HtmlOption extends HtmlElement {

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
