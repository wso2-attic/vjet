/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace;

import org.w3c.dom.Attr;
import org.w3c.dom.Node;

import org.ebayopensource.af.common.types.RawString;
import org.ebayopensource.dsf.DSFRootAnchor;
import org.ebayopensource.dsf.dom.DAttr;
import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.dom.DRawString;
import org.ebayopensource.dsf.dom.DText;
import org.ebayopensource.dsf.html.dom.DScript;
import org.ebayopensource.dsf.html.dom.DSelect;
import org.ebayopensource.dsf.common.xml.IXmlStreamWriter;

public class ContentTraceInstrumenter implements IInstrumentDElement {
	
	public ContentTraceInstrumenter () {
	}
	
	public void appendElement(final Node parent, final Node child) {
		if (parent.getClass().getName().startsWith(
				DSFRootAnchor.class.getPackage().getName())) {

			// Omit node objects that are other than Element node which are not
			// "interesting".
			if (parent.getNodeType() != Node.ELEMENT_NODE) {
				return;
			}

			// if we are appending text nodes under this parent node, put them in the trace table
			//
			String value = null;
			StringBuilder spyTr = new StringBuilder(100);
			String text = null;
			DElement de = (DElement)parent;
			String instrument = null;
			
			if (child instanceof DText) {
				
				if (child instanceof DRawString) {
					value = ((DRawString)child).getRawString().getString();
				}
				else {
					value = child.getNodeValue();
				}
				
				if (value != null && value.length() > 0) {
					StringBuilder childSpyTr = new StringBuilder(100);
					text = buildSpyTrace(value, childSpyTr);
					
					if (childSpyTr.length() > 0) {
						if (child instanceof DRawString) { // set back the original text by removing all instruments to display properly
							((DRawString)child).setRawString(new RawString(text));
						}
						else {
							child.setNodeValue(text); // set back the original text by removing all instruments to display properly
						}
					}
					de = (DElement) parent; // set SpyTr on parent
					
					String parentAttr = ((DElement)parent).getAttribute("spyTr");
					String childAttr = childSpyTr.toString();
					
					spyTr.append(parentAttr).append(childAttr);	
					instrument = childAttr;
				}
			}
			else if (child instanceof IContentTrace) {

				de = (DElement) parent; // set SpyTr on parent	
				
				String traceIdx = ((IContentTrace)child).getContentInstrumentation();

				if (traceIdx == null) {
					traceIdx = "";
				}
				String parentAttr = ((DElement)parent).getAttribute("spyTr");
				
				spyTr.append(parentAttr).append(traceIdx);	
				instrument = traceIdx;
			}
			else if (parent instanceof DSelect && child instanceof DElement) {
				
				String childAttr = ((DElement)child).getAttribute("spyTr");
				String parentAttr = ((DElement)parent).getAttribute("spyTr");
				spyTr.append(parentAttr).append(childAttr);	
				de = (DElement) parent;
				instrument = childAttr;
			}
			
			// set spyTr attribute on the element node
			if (de != null && !(de instanceof DScript)) {
				String attr = "";
				if (spyTr.length() > 0)
					attr = spyTr.toString().trim();
				
				if (attr.length() > 0) {
					de.setAttribute("spyTr", attr + " "); // append a space at end of spyTr
					setContentTraceTextInstrumentInfo(de, instrument);
				}
			}
		}
	}
	
	public void setAttributeValue(DElement node, DAttr attr, String value) {
		if (value != null && value.length() > 0) {
			StringBuilder attrTr = new StringBuilder(100);			
			String text = buildSpyTrace(value, attrTr);
			
			if (attrTr.length() > 0) {
				attr.setOldValue(text); // remove spy trace from the attribute value
			
				if (node != null && !(node instanceof DScript)) {
					StringBuilder spyTr = new StringBuilder(100);
					String spyAttr = attrTr.toString();
					
					String parentAttr = node.getAttribute("spyTr");
					
					if (findDuplicateTr(parentAttr, spyAttr)) {
						spyTr.append(parentAttr);
					}
					else {
						spyTr.append(parentAttr).append(spyAttr);
					}
					node.setAttribute("spyTr", spyTr.toString().trim() + " ");
					setContentTraceAttributeInstrumentInfo(node, attr, spyAttr);
				}
			}
		}		
	}
	
	private boolean findDuplicateTr(String parentSpyTr, String attrSpyTr) {
		
		if (parentSpyTr == null || parentSpyTr.length() == 0)
			return false;
		
		String[] parent = parentSpyTr.split(" ");
		String[] attrTr = attrSpyTr.split(" ");
		
		for (int i = 0; i < parent.length; i++) {
			if (parent[i].equals(attrTr[0])) {
				return true;
			}
		}
		
		return false;		
	}
	
	private void setContentTraceAttributeInstrumentInfo(DElement element, DAttr attr, String instrument) {
		DAttr attrNode = getContentTraceAttrNode(element);
		
		ContentTraceInfo cti = (ContentTraceInfo)attrNode.getObjectValue();
		cti.setAttributeInstrument(attr.getName(), instrument);
		
	}
	
	private void setContentTraceTextInstrumentInfo(DElement element, String instrument) {
		DAttr attrNode = getContentTraceAttrNode(element);
		
		ContentTraceInfo cti = (ContentTraceInfo)attrNode.getObjectValue();
		cti.setTextInstrument(instrument);
	}
	
	private DAttr getContentTraceAttrNode(DElement element) {
		
		Attr attr = element.getAttributeNode("ContentTr_V4cid");
		
		if (attr == null) {
			DAttr attrNode = new DAttr("ContentTr_V4cid", "empty");
			ContentTraceInfo cti = new ContentTraceInfo();
			attrNode.setObjectValue(cti);
			element.setAttributeNode(attrNode);
			return attrNode;
		}
		else {
			return (DAttr)attr;
		}
	}	
	
	private String buildSpyTrace(String instrumentedString, StringBuilder spyTr) {
		ContentTracker.InstrumentedText[] instrumentedTexts = ContentTracker.getAllTracebacks(instrumentedString);
				
		if (instrumentedTexts.length > 0) {
			StringBuilder builder = new StringBuilder(100);
			
			for (int i = 0; i < instrumentedTexts.length; i++) {
				builder.append(instrumentedTexts[i].getText()); // re-append the original text
				
				Integer idx = instrumentedTexts[i].getIdx();
				if((idx != null) && (idx.intValue() >= 0)) {
					spyTr.append(idx).append(" ");
				}
			}
			
			return builder.toString();
		}
		else {
			return instrumentedString; // no instrument found
		}
		
	}

	public void startSelfRender(DElement node, IXmlStreamWriter writer) {
		// generate the JSON scripts to define the content trace back table during the rendering. Also See: BUGDB00730894
		String id = node.getAttribute("id");

		if (id != null && id.equals("SpyglassTracebackTbl"))
		{
			generateContentTracebackTable(node);
		}
	}
	
	public void endSelfRender(DElement node, IXmlStreamWriter writer) {
	}
	
	// define content trace back table in JS during the rendering
	private void generateContentTracebackTable(DElement node) {
		boolean hasContentTracker = TraceCtx.ctx().haveContentTracker();
		
		if (hasContentTracker) {
			ContentTracker tracker = TraceCtx.ctx().getContentTracker();
			
			if( tracker.getTraceBackMap().size() > 0)
			{
				String[] localList = new String[tracker.getTraceBackMap().size()];
				for(Object key : tracker.getTraceBackMap().keySet() ){
					localList[(tracker.getTraceBackMap().get(key)).intValue()] = (String)key;
				}
				StringBuffer sbuf = new StringBuffer("var spyglassTracebackTbl = [ ");
				
				for( String trace: localList)
				{
					sbuf.append("\"").append(trace).append("\",\n");
				}
				sbuf.append("\"\" ]");
				
				node.add(sbuf.toString());
			}	
		}
		
	}

	public void startElement(DElement node, IXmlStreamWriter writer) {
		
		DAttr attr = (DAttr)node.getAttributeNode("ContentTr_V4cid");
		
		if (attr != null) {
			Object value = attr.getObjectValue();

			if (value != null && value instanceof ContentTraceInfo) {

				ContentTraceInfo cti = (ContentTraceInfo)value;
				attr.setObjectValue(null); // clear the obj value
			
				// now set the string value of the attribbute by generating the JSON
				// scripts using instrumentations in the ContentTraceInfo obj
				StringBuilder spyTr = new StringBuilder(100);
				String attrList = cti.getAttrInsList();
				String textList = cti.getTextInsList();
			
				spyTr.append("{NS:").append(cti.getNameSpace()).append(", attr {")
				.append(attrList).append("}, ").append("text [").append(textList)
				.append("]}");
			
				attr.setValue(spyTr.toString());
			}
		}
		
		// generate the JSON scripts to define the content trace back table during the rendering
		/*String id = node.getAttribute("id");
		
		if (id != null && id.equals("SpyglassTracebackTbl"))
			generateContentTracebackTable(node);*/

	}

	public void endElement(DElement node, IXmlStreamWriter writer) {
	}
}
