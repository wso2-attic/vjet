/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom;

/*
 * HTML 5.0 new tag organization
 * 
Most elements that are used in the body of documents and applications are categorized as flow content.

    * a
    * abbr
    * address
    * area (if it is a descendant of a map element)
    * article
    * aside
    * audio
    * b
    * bb
    * bdo
    * blockquote
    * br
    * button
    * canvas
    * cite
    * code
    * command
    * datagrid
    * datalist
    * del
    * details
    * dfn
    * dialog
    * div
    * dl
    * em
    * embed
    * fieldset
    * figure
    * footer
    * form
    * h1
    * h2
    * h3
    * h4
    * h5
    * h6
    * header
    * hgroup
    * hr
    * i
    * iframe
    * img
    * input
    * ins
    * kbd
    * keygen
    * label
    * link (if the itemprop attribute is present)
    * map
    * mark
    * math
    * menu
    * meta (if the itemprop attribute is present)
    * meter
    * nav
    * noscript
    * object
    * ol
    * output
    * p
    * pre
    * progress
    * q
    * ruby
    * samp
    * script
    * section
    * select
    * small
    * span
    * strong
    * style (if the scoped attribute is present)
    * sub
    * sup
    * svg
    * table
    * textarea
    * time
    * ul
    * var
    * video
    * Text

As a general rule, elements whose content model allows any flow content should have either at least one descendant text node that is not inter-element whitespace, or at least one descendant element node that is embedded content. For the purposes of this requirement, del elements and their descendants must not be counted as contributing to the ancestors of the del element.

This requirement is not a hard requirement, however, as there are many cases where an element can be empty legitimately, for example when it is used as a placeholder which will later be filled in by a script, or when the element is part of a template and would on most pages be filled in but on some pages is not relevant.
 */
public interface IFlowContent {

}
