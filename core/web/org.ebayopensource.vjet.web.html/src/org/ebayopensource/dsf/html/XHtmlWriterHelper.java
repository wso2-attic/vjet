/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import org.w3c.dom.Node;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.common.trace.TraceCtx;
import org.ebayopensource.dsf.common.xml.IIndenter;
import org.ebayopensource.dsf.dom.util.DomToRawSaxGenerator;
import org.ebayopensource.dsf.dom.util.DomToRawSaxTraceGenerator;
import org.ebayopensource.dsf.html.dom.util.HtmlStreamWriter;
import org.ebayopensource.dsf.html.dom.util.HtmlWriter;
import org.ebayopensource.dsf.html.schemas.Xhtml10Transitional;

public class XHtmlWriterHelper {
	/**
	 * The default size of the Writer used is 128.  If you have larger
	 * output expectations use the write(Node, Writer) methods and pass in 
	 * an appropriately sized Writer.
	 * @param node
	 * @return
	 */
	public static String asString(final Node node) {
		// StringWriter is a wrapper on StringBuffer - StringBuffer's default
		// initial size = 16.
		final StringWriter sw = new StringWriter(128) ;
		write(node, sw) ;
		return sw.toString() ;
	}
	
	/**
	 * The default size of the Writer used is 128.  If you have larger
	 * output expectations use the write(Node, Writer) methods and pass in 
	 * an appropriately sized Writer.
	 * @param node
	 * @param indenter
	 * @return
	 */
	public static String asString(final Node node, final IIndenter indenter) {
		final StringWriter sw = new StringWriter() ;
		write(node, sw, indenter);
		return sw.toString() ;
	}
	
	public static void write(final Node node, final Writer writer) {
		write(node, writer, IIndenter.COMPACT);
	}
	
	
	public static void write(
		final Node node, final Writer writer, final IIndenter indenter)
	{
		final boolean haveInstrumenter = TraceCtx.ctx().haveInstrumenter();
		final HtmlStreamWriter htmlStreamWriter =
			new HtmlStreamWriter(writer, indenter);
		final HtmlWriter htmlWriter = new HtmlWriter(Xhtml10Transitional.getInstance(), htmlStreamWriter);
		if (haveInstrumenter) {
			final DomToRawSaxTraceGenerator saxGenerator 
				= new DomToRawSaxTraceGenerator(htmlWriter);
			try {
				saxGenerator.genEvents(node, htmlStreamWriter);
			} 
			catch (Exception e) {
				throw new DsfRuntimeException(e.getMessage(), e);
			}

		} 
		else {
			final DomToRawSaxGenerator saxGenerator 
				= new DomToRawSaxGenerator(htmlWriter);
			try {
				saxGenerator.genEvents(node, htmlStreamWriter);
			} 
			catch (Exception e) {
				throw new DsfRuntimeException(e.getMessage(), e);
			}
		}
	}
	
	public static byte[] asBytes(
		final Node node,
		final String encoding,
		final IIndenter indenter) throws UnsupportedEncodingException
	{
		final ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
		final Writer w = new OutputStreamWriter(baos, encoding);
		write(node, w, indenter);
		return baos.toByteArray();
	}
}
