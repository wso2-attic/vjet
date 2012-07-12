/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.w3c.dom.Node;

import org.ebayopensource.dsf.DSFRootAnchor;
import org.ebayopensource.dsf.dom.DAttr;
import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.html.dom.util.ISelfRender;
import org.ebayopensource.dsf.common.xml.IXmlStreamWriter;

public class DefaultNodeInstrumenter implements IInstrumentDElement {
	// Add non-standard attribute spycmpname=classNameOfNode and add a
	// "spyCmpBorder" to the class= attribute.
	// The appendTime attribute (spyAt) has been added already.
	public void startElement(final DElement node, final IXmlStreamWriter writer) {
		if (!node.getClass().getName().startsWith(
				DSFRootAnchor.class.getPackage().getName())
				|| node.getClass().getSimpleName().equals("InvisibleContainer")) {
			node.setAttribute("spycmpname", node.getClass().getName());
			String classAttr = node.getAttribute("class");
			node.setAttribute("class", classAttr + " spyCmpBorder");
			// Setup spec name if available
			String specName = getCompSpecName(node);
			if (specName.length() > 0) {
				node.setAttribute("spyspec", specName);
			}
		}
	}
	
	public void setAttributeValue(DElement node, DAttr attr, String value) {
		// no-op
	}

	// See if this component has a Spec. If so, use reflection to get
	// an instance of the spec and then get the class name of the spec
	// to save as an attribute.
	private String getCompSpecName(final DElement node) {
		Class[] clz = node.getClass().getInterfaces();
		for (int i = 0; i < clz.length; i++) {
		     if (clz[i].getSimpleName().equals("ISpecBasedComponent")) {
		    	 Class[] parameterTypes = new Class[] {};
		    	 try {
		    		 Method getSpecMethod = node.getClass().getMethod("getCompSpec", parameterTypes);
		    		 Object[] arguments = new Object[] {};
		    		 Object spec = getSpecMethod.invoke(node,arguments);
		    		 return spec.getClass().getName();
		    	 } 
		    	 catch (NoSuchMethodException e){
		    		 return ""; 
		    	 }  
		    	 catch (IllegalAccessException e) {
		             return "";
		         } 
		    	 catch (InvocationTargetException e) {
		             return "";
		         }
		     }
		  }
		return "";
	}

	public void endElement(final DElement node, final IXmlStreamWriter writer) {
		// System.out.println(node.toString());
	}

	// Add a span tag to hold the self render object. Tag will bear the
	// non-standard attribute
	// spycmpname=classNameOfNode and a class="spyCmpBorder" attribute.
	public void startSelfRender(
		final DElement node, final IXmlStreamWriter writer)
	{
		// Omit DContent objects as they happen to self-render but are not "interesting".
		if (node.getClass().getName().equals("org.ebayopensource.dsf.resource.content.runtime.DContent") ||
				node.getClass().getName().equals("com.ebay.darwin.component.core.page.BasePage")	) {
			return;
		}
		
		if (node.getNodeName().equalsIgnoreCase("BasePage")) {
			return;
		}	
			
		writer.writeStartElement("span") ; //HtmlTypeEnum.SPAN.getName());
		writer.writeAttribute("spycmpname", node.getClass().getName());
		writer.writeAttribute("class", "spyCmpBorder");
		writer.writeAttribute("spyAt", node.getAttribute("spyAt"));
		
		// Setup spec name if available
		final String specName = getCompSpecName(node);
		if (specName.length() > 0) {
			writer.writeAttribute("spyspec", specName);
		}

		//the "spydatamodelid" is defined in the page's data model SPAN node by 
		//DocProcessor::addDataModelToDOM method; add this attribute in self-render component
		//rendering.
		String spyDataModelId = node.getAttribute(DataModelCtx.SpyDataModelId);
		if (spyDataModelId!=null && !"".equals(spyDataModelId)) {
			writer.writeAttribute(DataModelCtx.SpyDataModelId, spyDataModelId);
		}
	}

	public void endSelfRender(
		final DElement node, final IXmlStreamWriter writer)
	{
		if (node.getClass().getName().equals("org.ebayopensource.dsf.resource.content.runtime.DContent") ||
				node.getClass().getName().equals("com.ebay.darwin.component.core.page.BasePage")	) {
			return;
		}		
		
		if (node.getNodeName().equalsIgnoreCase("BasePage")) {
			return;
		}	
		
		writer.writeEndElement();
	}

	// Add a non-standard spyat="nameOfClass:lineNumber" attribute to the
	// element if it is a component or self-render.
	public void appendElement(final Node parent, final Node node) {
		if (null == node) {
			return;
		}
		if (!node.getClass().getName().startsWith(
				DSFRootAnchor.class.getPackage().getName())
				|| node instanceof ISelfRender) {
			//	Omit DContent objects as they happen to self-render but are not "interesting".
			if (node.getClass().getName().equals("org.ebayopensource.dsf.resource.content.runtime.DContent")) {
				return;
			}
			
			StackTraceElement trace[] = Thread.currentThread().getStackTrace();
			// Backup in trace until we get out of dsf code back to caller.
			if(trace.length > 5) {
				int caller = 5;
				while (caller < trace.length) {
					if (trace[caller].getClassName().startsWith("org.ebayopensource.dsf.")) {
						caller++;
						continue; // still Dsf, keep backing up
					}
					else {
						//String spyAt = trace[caller].getClassName() + ":"
						//		+ trace[caller].getLineNumber();
						// System.out.println(spyAt);
						if (node instanceof DElement) {
							DElement de = (DElement) node;
							de.setAttribute("spyAt", trace[caller]
									.getClassName()
									+ ":" + trace[caller].getLineNumber());
						}
						return;
					}
				}
			}
		}
	}
}