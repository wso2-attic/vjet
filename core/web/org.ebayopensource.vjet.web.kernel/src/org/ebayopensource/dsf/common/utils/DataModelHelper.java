/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.utils;

import java.io.StringWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

import org.ebayopensource.dsf.DsfTraceId;
import org.ebayopensource.dsf.common.trace.DataModelCtx;
import org.ebayopensource.dsf.common.trace.TraceCtx;
import org.ebayopensource.dsf.common.trace.introspect.JavaBeanTraceIntrospector;
import org.ebayopensource.dsf.common.tracer.ITracer;
import org.ebayopensource.dsf.common.xml.IIndenter;
import org.ebayopensource.dsf.common.xml.IXmlStreamWriter;
import org.ebayopensource.dsf.common.xml.XmlStreamWriter;
import org.ebayopensource.dsf.dom.DNode;
import org.ebayopensource.dsf.logger.Logger;
import org.apache.commons.codec.binary.Base64;;


/**
 * 
 * DataModelHelper, a helper class for v4 application to trace java bean object in order
 * to get a XML dump under v4 framework. It is a thread-safe class.
 * The data model tracing supported by this class is per request.
 * For detail, please read:
 * http://wiki.arch.ebay.com/index.php?page=v4DataModelDumping
 * 
 * 
 *
 */
public class DataModelHelper {
	public final static String DataModelScope="DataModel"; //TraceHelper.class.getName()
	private static Logger s_logger = Logger.getInstance(DataModelHelper.class);
	
	private static DataModelHelper s_singleton = new  DataModelHelper();
	
	private DataModelHelper() {
	}
	
	/**
	 * 
	 * @return the singleton DataModelHelper object.
	 */
	static public DataModelHelper getInstance() {
		return s_singleton;		
	}


	/**
	 * 
	 * @return is v4trace is on/off.
	 */
	static public boolean isTraceOn() {
		return TraceCtx.ctx().getTraceManager().isTraceOn();
	}
	
	/**
	 *  API to trace java bean object state, called by v4 application
     *  The object state of java bean is introspected by invoking the
     *  its public "getter" methods.
     * 
	 *  @param model  the java bean style model object
	 */
	public void traceDataModel(Object model) {
		traceDataModel(null,model);
	}
	
	
	/**
	 * the v4 app which wishes to have its data model viewable in Spyglass component view
	 * needs to register the component/model in this API.
	 * this is the preferred method to traceDataModel(Object model)
	 * 
	 * when this method is called, v4 framework will register the component/data model in
	 * a TLS registry (map) no matter what trace setting and Spyglass setting.
	 * 
	 * v4 app programming pattern:
	 * public void bar() {
	 *   DNode  component = <some component>;
	 *   Object model = <this component's data model>;  //1 model for each component
	 *   DataModelHelper.getHelper().traceDataModel(component,model);
	 * }
	 *
	 * 
	 */
	public void traceDataModel(DNode component, Object model) {		
		TraceCtx ctx = TraceCtx.ctx();	
		DataModelCtx dmCtx = DataModelCtx.getCtx();
		
		if (model!=null) {
			ITracer tracer = ctx.getTracer(DataModelScope); //could be a NoOPTracer
			tracer.traceDataModel(DsfTraceId.DATAMODEL, this, model);

			if (component!=null) {
				dmCtx.addComponentAndModel(component,model);
			}
		}
	}

	
	/**
	 * remove the DataModelCtx subcontext from DsfContext. 
	 * This method is useful in DataModelHelper unit test when TraceCtx can be changed in same thread.
	 * Refer DataModelHelperTests.java.
	 *
	 * v4 application has no use case for such method call.
	 * 
	 */
	public void resetHelperCtx() {
		DataModelCtx.setCtx(null);
	}
	

	/**
	 * @param component    a v4 component.
	 * @param encode whether to encoding the model dump
	 * @return a String from the data model xml dump for the component passed in.
	 *         if the component is not registered by v4 app, NULL is returned;
	 *         if the model registed is a non-public class, empty string is returned.
	 * 
	 * note:
	 *    For the data model xml string <DataModelXMLStr> returned by JavaBeanTraceIntrospector(model-object),
	 *    it contains XmlEncoder escaped substring(&lt;&gt;...).
	 *    In order to bi-direction conversion of data model string, we can't use XmlEncoder
	 *    as the encoder to encode the whole <DataModelXMLStr>.
	 *     
	 * this method is used by Spyglass component view integration--the trace instrumenter.
	 * a v4 application(not v4 framework) itself should not call it directly.
	 *
	 * internally base64 encoder is used. Encoding with URLEncoder("string","UTF-8") could be ok, but what 
	 * if the containing web page is not UTF-8? (for spyglass=cmp page view, it is charset=ISO-8859-1".)
	 */
	public String getDataModelAsXml(DNode component, boolean encode) 
	{
		DataModelCtx ctx = DataModelCtx.getCtx();
		Map<DNode,Object> map =  ctx.getComponentModelMap();
		Object model = map.get(component);

		return getDataModelAsXmlDirect(model,encode);
	}
	
	/**
	 * get xml dump for the data model passed in.
	 * 
	 * @param model data model object
	 * @encode encoding
	 */
	public String getDataModelAsXmlDirect(Object model, boolean encode) 
	{
		if (model==null)
			return null;

		JavaBeanTraceIntrospector introspector = JavaBeanTraceIntrospector.getDefault();
		
		StringWriter strWriter = new StringWriter();
		XmlStreamWriter xmlWriter = new XmlStreamWriter(strWriter,IIndenter.COMPACT);
		introspector.writeState(model, xmlWriter);
		xmlWriter.flush();
		String s = strWriter.toString();		
		if ("".equals(s))
			s = null;
		if (encode && s!=null) {
			s = new String(Base64.encodeBase64(s.getBytes())); //s = URLEncoder.encode(s,"UTF-8");
		}
		return s;
	}
	
	/**
	 * @param encodedXmlStr encoded xml str generated by getDataModelAsXml(DNode component, boolean encode)
	 * @return the data model str
	 */
	public String decodeDataModelStr(String encodedXmlStr) 
	{
		if (encodedXmlStr==null)
			return null;

		byte bytes[] = Base64.decodeBase64(encodedXmlStr.getBytes());
		return new String(bytes);
	}
	
	
	/**
	 * @param   the component registed before using traceDataModel(DNode component, Object model)
	 * @return  the data model object
	 */
	public Object getDataModel(DNode component) 
	{
		DataModelCtx ctx = DataModelCtx.getCtx();
		Map<DNode,Object> map =  ctx.getComponentModelMap();

		return map.get(component);
	}
	
	

	/**
	 * @param  modelInterface  the interface class
	 * @param  model  the data model object. Its class implemets the modelInterface
	 * @return a proxy object for model object. when v4 app invoks its method, the returned
	 *         object will be traced.
	 * 
	 * sample v4 application:
	 * public interface IFooModel {
	 *    ...
	 * }
	 * 
	 * public classs FooModelImpl implements IFooModel {
	 * }
	 * 
	 * void bar() {
	 *    FooModelImpl model = new FooModelImpl();  
	 *    IFooModel proxy = TraceHelper.getInstance().getProxy(IFooModel.class, model);
	 *    .....
	 * }
	 * 
	 */
	public <T> T getProxy(final Class<T> modelInterface, final Object model) {
		ClassLoader classLoader = model.getClass().getClassLoader();
		Class[] interfaces = new Class[] {modelInterface}; 

		InvocationHandler handler = new JavaBeanInvocationHandler(model);
		
        Object proxy = Proxy.newProxyInstance(classLoader, interfaces,handler );
        return (T) proxy;       
	}

	/**
	 * @param  model  the data model object. Its class implemets the modelInterface
	 * @return a proxy object for model object. v4 app is responsible to cast it to the 
	 *         interface @model class implements. when v4 app invoks its method, the returned
	 *         object will be traced.
	 * 
	 * sample v4 application:
	 * public interface IFooModel {
	 *    ...
	 * }
	 * 
	 * public classs FooModelImpl implements IFooModel {
	 * }
	 * 
	 * void bar() {
	 *    FooModelImpl model = new FooModelImpl();  
	 *    IFooModel proxy = (IFooModel)TraceHelper.getInstance().getProxy(model);
	 *    .....
	 * }
	 * 
	 */
	public Object getProxy(final Object model) {
		ClassLoader classLoader = model.getClass().getClassLoader();
		Class[] interfaces = model.getClass().getInterfaces(); 

		InvocationHandler handler = new JavaBeanInvocationHandler(model);
		
        Object proxy = Proxy.newProxyInstance(classLoader, interfaces,handler );
        return proxy;       
	}
	
	
  /**
   * a proxy invocation handler to intercept the call on v4 data Model (Java Bean)
   */
	static protected class JavaBeanInvocationHandler implements InvocationHandler {
		private Object model;
        
		public JavaBeanInvocationHandler(Object javaBean) {
			this.model = javaBean;
		}
		
		public Object invoke(Object proxy, Method m, Object[] args)
		{
			Object result=null;
			try {
				DataModelCtx ctx = DataModelCtx.getCtx();
				IXmlStreamWriter writer = ctx.getWriter();
				
	            //delegate to the java bean object
				JavaBeanTraceIntrospector introspector = JavaBeanTraceIntrospector.getDefault();
				result = introspector.doInvocation(writer,model,m,args); //get data model dump for getter which takes input parameters.
				
				if (isTraceOn() ) {
					if (result!=null) {
						if (writer!=null) {
							writer.writeStartElement(model.getClass().getSimpleName());
							writer.writeAttribute("method",m.getName() );
							introspector.writeState(result,writer);
							writer.writeEndElement();
						}
					}
				}
			} catch (Exception e) {
				s_logger.log(e);
			}
			return result;
		}		
	}
}
