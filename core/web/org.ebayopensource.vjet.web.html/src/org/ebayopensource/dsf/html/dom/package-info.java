/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
* The package wraps various HTML tags into corresponding DSF HTML tags. The DSF
* HTML tags are types of DOM element. The various HTML attributes, per tag, are
* implemented as DOM attributes. The DSF HTML is a set of Java types used to
* create, manipulate and generate HTML.
* <p>
* The V4 DOM 2.0 types can be found in the <code>org.ebayopensource.dsf.dom</code> 
* package.
* <p>
* The naming convention of DSF HTML tags are:
*  <li>Precede the Tag name with a "D" (for Darwin)
*  <li>Capitalize the first letter of the Tag. In the cases where the Tag has
*  only one letter
* <p>
* For example:
* <p>
* For the HTML tag &lt;div&gt; is wrapped as DDiv in this package
* <p>
* You can assemble these DSF HTML types into a DOM tree, which is then passed 
* to a rendering/encoding engine to generate an HTML fragment or entire 
* document.
* <p>
* Here is the code example:
* <p>
* <pre>
* 	DDiv div = new DDiv()
*              .add(new DH1("Some simple V4"))
*              .addBr()
*              .add(new DInput()
*              .setHtmlType(DInput.TYPE_BUTTON)
*              .setHtmlOnClick("alert('I was clicked!')")
*              .setHtmlValue("Press me"));
*   System.out.println(HtmlWriterHelper.asString(div, new IIndenter.Pretty()));
* </pre>
* <p>
* Besides DSF HTML tags, a set of related utility classes also support:
* <li>Rendering a DSF graph to HTML.
* <li>Parsing HTML text into a DSF object graph.
* <li>Ability to generate Java code that will reproduce a graph of DSF objects.
* <li>Properly parent a fragment of DSF all the way up to a valid HTML Document.
* <li>Execute a DSF graph as a hosted Web Page in a single line of code. 
* 
*/
package org.ebayopensource.dsf.html.dom;
