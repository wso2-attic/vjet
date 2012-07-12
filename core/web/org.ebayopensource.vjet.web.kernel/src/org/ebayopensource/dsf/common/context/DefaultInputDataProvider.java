/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.ebayopensource.dsf.common.exceptions.DsfExceptionHelper;
import org.ebayopensource.dsf.common.trace.introspect.ITraceable;
import org.ebayopensource.dsf.common.xml.IXmlStreamWriter;

public class DefaultInputDataProvider implements IInputParamValueProvider, ITraceable {

	private Map<String,String[]> m_requestParams;
		
	//
	// Constructor
	//
	public DefaultInputDataProvider(final Map<String,String[]> inputParams){
		if (inputParams == null){
			m_requestParams = new HashMap<String,String[]>();
		}
		else {
			m_requestParams = inputParams;
		}
	}
	
	//
	// Satisfy ITraceable
	//
	public void writeState(final IXmlStreamWriter writer){

		if (m_requestParams == null || m_requestParams.size() == 0){
			return;
		}
		
		String[] values;
		StringBuilder concatenated;
		for (Entry<String,String[]> entry: m_requestParams.entrySet()){
			writer.writeStartElement(entry.getKey());
			values = entry.getValue();
			if (values == null || values.length == 0){
				continue;
			}
			if (values.length == 1){
				writer.writeRaw(values[0]);
				writer.writeEndElement();
				continue;
			}
			concatenated = new StringBuilder();
			for (int i=0; i<values.length; i++){
				concatenated.append(",").append(values[i]);
			}
			writer.writeRaw(concatenated.substring(1));
			writer.writeEndElement();
		}
	}
		
	//
	// Satisfy IInputParamValueProvider
	//
	public String getInputParamValue(final String key) {
		final String[] values = getInputParamValues(key);
		if (values != null && values.length > 0){
			return values[0];
		}
		
		return null;	
	}
			
	public String getInputParamValue(
		final String key, final String defaultValue)
	{
		final String value = getInputParamValue(key);
		return value != null ? value : defaultValue;
	}
			
	public String[] getInputParamValues(final String name) {

		if (name == null){
			return null;
		}
		
		final String key = name;//.toLowerCase();
		final Object value = m_requestParams.get(key);
		
		if (value == null){
			return null;
		}
		
		if (value instanceof String[]){
			return (String[])value;
		}
		
		if (value instanceof String){
			final String[] values = new String[1];
			values[0] = (String)value;
			return values;
		}

		DsfExceptionHelper.chuck("Invaid value type: " + value);
		return null;
	}
		
	public String[] getInputParamValues(
		final String key, final String[] defaultValues)
	{
		final String[] values = getInputParamValues(key);
		return (values != null) ? values : defaultValues;
	}
	
	public Map<String,String[]> getInputParamValues(){
		return m_requestParams;
	}
	
	//
	// API
	//
	public void setInputParamValue(final String key, final String value){
		if (key == null){
			DsfExceptionHelper.chuck("key cannot be null");
		}
		
		if (value == null){
			m_requestParams.put(key, null);
		}
		else{
			String[] values = {value};
			m_requestParams.put(key, values);
		}
	}
}
