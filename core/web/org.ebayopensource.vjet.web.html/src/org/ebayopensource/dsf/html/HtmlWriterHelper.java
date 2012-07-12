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
import org.ebayopensource.dsf.common.node.IAttributeMap;
import org.ebayopensource.dsf.common.trace.TraceCtx;
import org.ebayopensource.dsf.common.xml.IIndenter;
import org.ebayopensource.dsf.dom.DAttr;
import org.ebayopensource.dsf.dom.DNode;
import org.ebayopensource.dsf.dom.util.DomToRawSaxGenerator;
import org.ebayopensource.dsf.dom.util.DomToRawSaxTraceGenerator;
import org.ebayopensource.dsf.html.dom.util.HtmlStreamWriter;
import org.ebayopensource.dsf.html.dom.util.HtmlWriter;
import org.ebayopensource.dsf.html.dom.util.IHtmlStreamWriter;
import org.ebayopensource.dsf.html.dom.util.INodeEmitter;
import org.ebayopensource.dsf.html.dom.util.IRawSaxHandler;
import org.ebayopensource.dsf.html.schemas.ISchema;
/**
 * Provides methods to support rendering/encoding DSF graphs. Those methods can
 * return the HTML as a String as well as do the rendering to a Writer.
 * 
 */
public class HtmlWriterHelper {

	/**
	 * Outputs the given DOM tree as the HTML String.
	 * <p>
	 * The default size of the Writer used is 128. If you have larger output
	 * expectations, you can use <code>write()</code> series methods and pass in
	 * an appropriately sized Writer.
	 * <p>
	 * The default indent style is <code>COMPACT</code>
	 * 
	 * @param node 
	 *        a reference to the root node of the given DOM tree
	 * @return
	 *        the HTML string of the given DOM tree
	 * @see   #asString(Node, HtmlWriterCtx)
	 * @see   #asString(Node, IIndenter)
	 */
	public static String asString(final Node node) {
		final Writer sw = HtmlWriterCtx.createWriter();
		write(node, sw) ;
		return sw.toString() ;
	}
	public static String asPrettyString(final Node node) {
		return asString(node, new IIndenter.Pretty()) ;
	}
	
	/**
	 * Outputs the given DOM tree as the HTML String.
	 * <p>
	 * The default size of the Writer used is 128. If you have larger output
	 * expectations, you can use <code>write()</code> series methods and pass in
	 * an appropriately sized Writer.
	 * <p>
	 * This method accepts a {@link HtmlWriterCtx} object which holds 
	 * the specified writer and custom options used in pharsing time. 
	 * 
	 * @param node
	 *        a reference to the root node of the given DOM tree
	 * @param writerOpt
	 *        a <code>HtmlWriterOption</code> object which holds the customized 
	 *        options for the parser. This parameter can not be <code>null</code> 
	 * @return
	 *        the HTML string of the given DOM tree
	 * @see   HtmlWriterCtx
	 * @see   #asString(Node)
	 * @see   #asString(Node, IIndenter)
	 */
	public static String asString(final Node node, HtmlWriterCtx writerOpt) {		
		write(node, writerOpt) ;
		return writerOpt.getWriter().toString() ;
	}
	
	/**
	 * Outputs the given DOM tree as the HTML String.
	 * <p>
	 * The default size of the Writer used is 128. If you have larger output
	 * expectations, you can use <code>write()</code> series methods and pass in
	 * an appropriately sized Writer.
	 * <p>
	 * You can specify an output indenter style by using this method.
	 * 
	 * @param node
	 *        a reference to the root node of the given DOM tree
	 * @param indenter
	 *        indent style of the output string. There is no default indenter
	 *        when using this method and this parameter can not be 
	 *        <code>null</code> 
	 * @return
	 *        the HTML string of the given DOM tree
	 * @see   IIndenter
	 * @see   #asString(Node)
	 * @see   #asString(Node, HtmlWriterCtx)
	 */
	public static String asString(final Node node, final IIndenter indenter) {
		final StringWriter sw = new StringWriter() ;
		write(node, new HtmlWriterCtx(sw, indenter));
		return sw.toString() ;
	}
	
	/**
	 * Renders the given DOM tree to a Writer.
	 * <p>
	 * The <code>COMPACT</code> mode would be set as default indent style when
	 * calling this method.
	 * 	 
	 * @param node
	 * 		  a reference to the root node of the given DOM tree
	 * @param writer
	 * 		  a specified Writer. This parameter can not be <code>null</code>.
	 * @see   #write(Node, Writer, IIndenter)
	 * @see   #write(Node, Writer, IIndenter, ISchema)
	 * @see   #write(Node, HtmlWriterCtx)
	 */
	public static void write(final Node node, final Writer writer) {
		write(node, new HtmlWriterCtx(writer)) ;
	}
	
	/**
	 * Renders the given DOM tree to a Writer.
	 * <p>
	 * You can specify an output indenter style by using this method.
	 * 
	 * @param node
	 * 		  a reference to the root node of the given DOM tree
	 * @param writer
	 * 		  a specified Writer. This parameter can not be <code>null</code>
	 * @param indenter
	 *        indent style of the output string. There is no default indenter
	 *        when using this method and this parameter can not be 
	 *        <code>null</code>
	 * @see   IIndenter
	 * @see   #write(Node, Writer)
	 * @see   #write(Node, Writer, IIndenter, ISchema)
	 * @see   #write(Node, HtmlWriterCtx)
	 */
	public static void write(
		final Node node, final Writer writer, final IIndenter indenter)
	{
		final HtmlWriterCtx opts = new HtmlWriterCtx(writer, indenter) ;
		write(node, opts);
	}
	
	/**
	 * Renders the given DOM tree to a Writer.
	 * <p>
	 * You can specify an output indenter style and a customized schema used in 
	 * rendering the Writer by using this method.
	 *  
	 * @param node
	 * 		  a reference to the root node of the given DOM tree
	 * @param writer
	 * 		  a specified Writer. This parameter can not be <code>null</code>
	 * @param indenter
	 *        indent style of the output string. There is no default indenter
	 *        when using this method and this parameter can not be 
	 *        <code>null</code>
	 * @param schema
	 *        a custimized schema
	 * @see   IIndenter
	 * @see   #write(Node, Writer)
	 * @see   #write(Node, Writer, IIndenter)
	 * @see   #write(Node, HtmlWriterCtx)
	 */
	public static void write(
		final Node node, 
		final Writer writer,
		final IIndenter indenter, 
		final ISchema schema) 
	{
		final HtmlWriterCtx opts = new HtmlWriterCtx(writer, indenter, schema);
		write(node, opts) ; 			
	}

	/**
	 * Renders the given DOM tree to a Writer.
	 * <p>
	 * This is the basic implementation of <code>write()</code> series methods.
	 * It accepts an {@link HtmlWriterCtx} object which holds the specified
	 * writer and customized options for rendering. 
	 * 
	 * @param node
	 *        a reference to the root node of the given DOM tree
	 * @param wOptions
	 *        a <code>HtmlWriterOption</code> object which holds the customized 
	 *        options for the parser. This parameter can not be <code>null</code>
	 * @see   HtmlWriterCtx
	 * @see   #write(Node, Writer)
	 * @see   #write(Node, Writer, IIndenter)
	 * @see   #write(Node, Writer, IIndenter, ISchema)   
	 */
	public static void write(final Node node, final HtmlWriterCtx ctx) {		
//long x = System.currentTimeMillis();
		IHtmlStreamWriter writer = new HtmlStreamWriter(ctx);
		IRawSaxHandler rawSaxHandler = new HtmlWriter(writer, ctx);
		
//		final HtmlRawSaxGenOption rawSaxGenOpt = new HtmlRawSaxGenOption(ctx);
//		final IRawSaxHandler rawSaxHandler = rawSaxGenOpt.getRawSaxHandler();
				
		final boolean haveInstrumenter = TraceCtx.ctx().haveInstrumenter();
		final INodeEmitter saxGenerator = haveInstrumenter		 
			? new DomToRawSaxTraceGenerator(rawSaxHandler)		
			: new DomToRawSaxGenerator(rawSaxHandler);

//long xx = System.currentTimeMillis();
//System.out.println("timing before genEvent" + (xx-x));
		try {
			saxGenerator.genEvents(node, writer) ; //rawSaxGenOpt.getStreamWriter());
		} 
		catch (Exception e) {
			throw new DsfRuntimeException(e.getMessage(), e);
		}
//long xxx = System.currentTimeMillis();
//System.out.println("timing for genEvent" + (xxx-xx));
	}	
	
	/**
	 * Outputs the given DOM tree as byte array.
	 * <p>
	 * You can specify an output indenter style by using this method.
	 * 
	 * @param node
	 * 		  a reference to the root node of the given DOM tree
	 * @param encoding
	 * 		  a specified encoding charset of the document
	 * @param indenter
	 *        indent style of the output string. There is no default indenter
	 *        when using this method and this parameter can not be 
	 *        <code>null</code>
	 * @return
	 * 	      the byte array of the given DOM tree
	 * @throws 
	 *        UnsupportedEncodingException
	 * @see   IIndenter
	 * @see   #asBytes(Node, String, IIndenter, ISchema)
	 */
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
	
	/**
	 * Outputs raw string of the given DOM tree as byte array.
	 * <p>
	 * You can specify an output indenter style and a customized schema used in 
	 * rendering the Writer by using this method.
	 * 
	 * @param node
	 * 		  a reference to the root node of the given DOM tree
	 * @param encoding
	 * 		  a specified encoding charset of the document
	 * @param indenter
	 *        indent style of the output string. There is no default indenter
	 *        when using this method and this parameter can not be 
	 *        <code>null</code>
	 * @param schema
	 *        a custimized schema
	 * @return
	 * 	      Byte array of the given DOM tree
	 * @throws 
	 *        UnsupportedEncodingException
	 * @see   IIndenter
	 * @see   #asBytes(Node, String, IIndenter)
	 */
	public static byte[] asBytes(
		final Node node,
		final String encoding,
		final IIndenter indenter,
		final ISchema schema) throws UnsupportedEncodingException
	{
		final ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
		final Writer w = new OutputStreamWriter(baos, encoding);
		write(node, w, indenter, schema);
		//w.flush();
		return baos.toByteArray();
	}
	
	/**
	 *  Returns one line HTML String of the given node. It not include the 
	 *  node's children, with start tag and its attributes such as 
	 *  <code>&lt;th align="left" colspan="1" rowspan="1"&gt;</code>.
	 *  This API is pending L&P.
	 *  	
	 * @param node
	 *        a reference of the given node
	 * @return
	 *        the HTML String of the given node
	 */
	public static String asOneLineString(final DNode node) {
		final StringWriter sw = new StringWriter();
		final HtmlStreamWriter writer = new HtmlStreamWriter(sw, IIndenter.COMPACT);
		writer.writeStartElement(node.getNodeName());
		final IAttributeMap attrs = node.getDsfAttributes();
		for (final DAttr attr:attrs) {			
			writer.writeAttribute(attr.getNodeName(), attr.getValue());			
		}
		//writer.writeCharacters(">");
		return sw.toString() + ">";
	}
}
