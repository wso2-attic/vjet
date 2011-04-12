/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.proxy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ebayopensource.dsf.dap.util.IterableJs;
import org.ebayopensource.dsf.javatojs.anno.AExclude;
import org.ebayopensource.dsf.javatojs.anno.ASupportJsForEachStmt;
import org.mozilla.mod.javascript.IJsJavaProxy;
import org.mozilla.mod.javascript.NativeArray;
import org.mozilla.mod.javascript.Scriptable;

/**
 * An IJsJavaProxy of Native JS Array
 */
@ASupportJsForEachStmt
public class Array implements IJsJavaProxy, IterableJs<Integer> {
	
	@AExclude
	private NativeArray m_nativeArr;
	@AExclude
	private static final Object[] EMPTY_ARGS = new Object[0];
	
	/* FRAMEWORK USAGE ONLY */
	@AExclude
	public Array(Scriptable nativeObj) {
		m_nativeArr = (NativeArray)nativeObj;
		m_nativeArr.put(JS_JAVA_PROXY, m_nativeArr, this);
	}
	
	public Array() {
		this(0);
	}
	
	public Array(int initLength) {
		this(NativeJsHelper.createNativeArray(initLength));
	}
	
	public Array(Object ...values) {
		this(values.length);
		for (int i = 0; i < values.length; i++) {
			put(i, values[i]);
		}
	}
	
	public Object get(int index) {
		Object value = m_nativeArr.get(index, m_nativeArr);
		if (value == null || value == Scriptable.NOT_FOUND) {
			return value;
		}
		return NativeJsHelper.convert(Object.class, value);
	}
	
	public void put(int index, Object value) {
		m_nativeArr.put(index, m_nativeArr, NativeJsHelper.toNative(value));
	}
	
	public int getLength() {
		return (int) m_nativeArr.getLength();
	}
	
	public Object pop() {
		return NativeJsHelper.convert(Object.class,
			NativeArray.callMethod(m_nativeArr, "pop", EMPTY_ARGS));
	}
	
	public int push(Object ...elements) {
		return NativeJsHelper.convert(Integer.class,
			NativeArray.callMethod(m_nativeArr, "push", NativeJsHelper.toNatives(elements)));
	}
	
	public Object shift() {
		return NativeJsHelper.convert(Object.class,
			NativeArray.callMethod(m_nativeArr, "shift", EMPTY_ARGS));
	}
	
	public int unshift(Object ...elements) {
		return NativeJsHelper.convert(Integer.class,
			NativeArray.callMethod(m_nativeArr, "unshift", NativeJsHelper.toNatives(elements)));
	}
	
	public Array reverse() {
		NativeArray.callMethod(m_nativeArr, "reverse", EMPTY_ARGS);
		return this;
	}
	public Array slice(int start){
		return slice(start,this.getLength());
	}
	public Array slice(int start, int end){
		Object[] args = new Object[2];
		args[0] = start;
		args[1] = end;
		Object obj = NativeArray.callMethod(m_nativeArr, "slice", NativeJsHelper.toNatives(args));
		return new Array((Scriptable)obj);
	}
	
	public Array sort() {
		NativeArray.callMethod(m_nativeArr, "sort", EMPTY_ARGS);
		return this;
	}
	
	public Array splice(int index, int howmany, Object ...elements) {
		Object[] args = new Object[2 + elements.length];
		args[0] = index;
		args[1] = howmany;
		int i = 2;
		for (Object elem : elements) {
			args[i++] = elem;
		}
		Object obj = NativeArray.callMethod(m_nativeArr, "splice", NativeJsHelper.toNatives(args));
		return new Array((Scriptable)obj);
	}
	
	public Array concat(Array ...arraies) {
		Object[] args = new Object[arraies.length];
		int i = 0;
		for (Array other : arraies) {
			args[i++] = other.m_nativeArr;
		}
		return new Array((Scriptable)NativeArray.callMethod(m_nativeArr, "concat", args));
	}
	
	public String join(String separator) {
		return NativeArray.callMethod(m_nativeArr, "join", new Object[] {separator}).toString();
	}
	
	public String join() {
		return join(",");
	}
	
	public String toString() {
		return NativeArray.callMethod(m_nativeArr, "toString", EMPTY_ARGS).toString();
	}
	//toSource is FireFox only for debugging purpose
//	public String toSource() {
//		return NativeArray.callMethod(m_nativeArr, "toSource", EMPTY_ARGS).toString();
//	}
	/* FRAMEWORK USAGE ONLY */
	@AExclude
	public Scriptable getJsNative() {
		return m_nativeArr;
	}

	/**
	 * Return an Iterator for all items in Array.
	 * Should be only used by java runtime for handling for-each-statement.
	 */
	/* FRAMEWORK USAGE ONLY */
	@AExclude
	public Iterator<Integer> iterator() {
		Object[] ids = m_nativeArr.getIds();
		List<Integer> valueList = new ArrayList<Integer>(ids.length);
		for (Object id : ids) {
			valueList.add(Integer.valueOf(id.toString()));
		}
		return valueList.iterator();
	}
	
	/**
	 * converts Java array to JavaScript Array
	 */
	public static <T> Array make(T[] values) {
		Array arr = new Array(values);
		for (int i = 0; i < values.length; i++) {
			arr.put(i, values[i]);
		}
		return arr;
	}
	
	public static Array make(int[] values){
		Array arr = new Array(values);
		for (int i = 0; i < values.length; i++) {
			arr.put(i, values[i]);
		}
		return arr;
	}
	
	public static Array make(double[] values){
		Array arr = new Array(values);
		for (int i = 0; i < values.length; i++) {
			arr.put(i, values[i]);
		}
		return arr;
	}
	
	public static Array make(boolean[] values){
		Array arr = new Array(values);
		for (int i = 0; i < values.length; i++) {
			arr.put(i, values[i]);
		}
		return arr;
	}

}
