/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom.support;

/*
 * PERFORMANCE NOTE:
 * This was a 5 minute hack to get the idea of jif'ing into some early programming
 * examples.  There are many ways to optimize the jiffing from simple caching of
 * bean types/descriptors etc... to avoid lookups each time.  Other approaches 
 * can runtime creation of "assignment" classes or other such tricks...  We can
 * also do something like compiled RegExp, Format's or XPath exprs etc...  In those
 * cases we would create a custom applier for the given target (whose type would
 * need to be provided ahead of time:
 * XJiffy jiffy = XJiffy.compile(targetClass, "x:10;y:20;invert:true") ;
 * Shape s = new Shape() ;
 * jiffy.on(s) ;
 * 
 * Of course we can take the jiffying to other templatish levels such as being
 * able to have parameterized jif values.
 * 
 * String jif = "x:${xcoord};y:${ycoord};invert:${invert}" ;
 * XJiffy jiffy = XJiffy.compile(targetClass, jif) ;
 * jiffy.on(s, variableDataBean) ;
 */
import static java.util.Locale.ENGLISH;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;

/*
assign(target, assignments)

target is a class or object.  If class then static methods are assumed, else
instance methods are assumed.

assign(obj, "id:10;ok:true;temp:-8.09;name:Viper;color:#088100;quote:"I am") translates to:
obj.setId(10) ;
obj.setOk(true) ;
obj.setTemp(-8/09) ;
obj.setName("Viper");
obj.setColor("#088100") ;
obj.setQuote("\"I am\"") ; the value is quote escaped

We need to recognize that we have some common naming conventions that help
group certain attributes together.  For example in HTML hierarchy we have id -> setHtmlId/getHtmlId.
In the BSF we have the "b" prefixes.  Do we work around these?  Do we look for the
property in a specific order (ie non-prefixed and then prefixed?).  Of course we 
do have the problem where a prefixed name and standard property name would have
the same base and thus order would be important - this also brings up the question
of what if they do collide, and you want the other from whatever order we employ?
We you could use the fully qualified property name.  To do this then we would 
start with the naked property name first and then progress "down" the subclass
hierarchy in terms of naming conventions.  

We also have to figure out that the fully qualified name does "NOT" need prefixing

setNodeValue, getNodeValue       -- DOM
set[Dsf]ExportingLocalNames, XXX -- DSF DOM extensions
set[Html]Align, get[Html]Align   -- DSF HTML
set[B]Size, get[B]Size           -- BML
set[S]SBaselineShift,  get[S]BaselineShift -- SVG
set[Mml]Xref,  get[Mml]Xref      -- MML

The order of evaluation is from naked name, like "id", we then progress to
dsfId, htmlId, xyzId.  If we really had a choice of "id" and "htmlId" our order
would first find "id".  If we meant "htmlId", we should find it a property as
is and thus would not need the progression naming which would not yield meaningful
names like "dsfHtmlId", "htmlHtmlId", etc... 

We can define a jif by: propertyName, value [, progression...] where the progression
is one or more property prefixes to aid in the property name.  For performance
reasons, resolving the name first is important.  In many cases the HTML or B prefix
is the most-likely resolution to a JIF property name.  Not sure how to get around
this - order is right way, prefix is likely fastest way.

We may also want the option of continuing to apply jif's even if we don't find
a specific property.  This could be valuable where you can have a "block" of 
jif definitions you want applied and don't want to have to factor to "exactly"
the complete correct set to avoid any error during the applies.  This is probably
a global general setting and thus is really a high-policy decision.  Of course
people can use Jiffy directly, they could decide then on the error handling policy.

Since JIF does not need to exist at every class in the hierarchy, we employ them
at strategic places in the hierarchies - DNode, DElement, DBaseHtmlElement, etc...
Each of these jif's will provide the specific order of property and prefixes
to apply.

Currently we have 2 special characters to handle in the basic parsing of the jif String.
The ":" and the ";" are used as delineate the jif definitions.  If these values
were to occur in the actual value portion of the jif, it would currently break our
simplistic parsing.  
If we look at single and double quotes, we really don't need
to do anything in the value part of the jif since we treat all characters as
data.  Single or double quotes are no different than any other illegal Java identifier
characters if we are talking about the property name part.  We currently just 
let the property lookup fail for such illegal property names.
 */
public final class Jif {
//	public static void main(String[] args) {
//		// id and paging
//		DDiv elem = new DDiv() ;
//		String[] prefixes = {"Dsf", "Html"} ;
//		Jif.jif(elem, "id:aaa", false, prefixes) ;
//		out("c.getHtmlId(): " + elem.getHtmlId()) ;
//	}
//	static void out(Object o) {
//		System.out.println(o) ;
//	}
	
	/**
	 * Apply the jif formatted String to the passed in target.  The target and
	 * jif String must not be null.  The target can be a Class type or an
	 * instance.  If the jif is not well structured a runtime exception is thrown.
	 * If any of the jif name/value pairs does not equate to a target property
	 * or correct value for assignment a runtime exception is thrown.
	 */
	public static <T> T jif(
		final T target, final String jif, 
		final boolean throwOnSetPropertyFailure, String... prefixes)
	{
		JifTokenizer toker = new JifTokenizer(jif) ;
		while(toker.hasMoreElements()) {
			String unit = toker.nextElement();
			int colonIndex = unit.indexOf(':') ;
			if (colonIndex == -1) {
				throw new RuntimeException("bad unit in assign string: " + unit) ;
			}
			String propertyName = unit.substring(0, colonIndex).trim() ;
			// It is possible propertyName has dashes in it.  As a conveinence to
			// developers we will camelcase dashed property names for them.  This
			// is nice since the properties are often remembered by developers as their
			// "real" DOM property name which supports dashes, whereas our Java
			// environment cannot support such names and thus we represent the dashed
			// properties as camelcase.
			// Examples:
			//   v-ideographic   --> vIdeographic
			//   text-after-edge --> textAfterEdge
			propertyName = camelcase(propertyName) ;
			
			// value is "as-is", that means no trimming etc...
			String value = unit.substring(colonIndex + 1) ;
			
			// Need to handle cases where value has escaped colon //; in it...
			if (value.contains("//;")) {
				value = value.replace("//;", ";") ;
			}
			
			apply(target, unit, propertyName, value, throwOnSetPropertyFailure, prefixes);
		}
		return target ;
	}
	
	private static <T> void apply(
		final T target, final String unit, 
		final String propertyName, final String value, 
		final boolean throwOnSetPropertyFailure, String[] prefixes)
	{
		try {
			write(target, propertyName, value) ;
			return ; // found it first try
		}
		catch(Exception e) {
			// ignore - expected possibility
		}
		
		// loop over the property prefixes - used to adjust to various naming
		// schemes set[Dsf]Name(...), set[Html]Id(...), etc...
		final String firstLetterUpperCasePropName = 
			propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1) ;
		for(String prefix: prefixes) {
			String prefixedPropertyName = prefix + firstLetterUpperCasePropName;
			try {
				write(target, prefixedPropertyName, value) ;
				return ;  // got a prefix + property match
			}
			catch(Exception e) {
				// ignore - expected possibility
			}
		}
		
		// At this point we did not find a match for the property or property with
		// prefixes and now need to check the JIF policy for whether or not we
		// treat this as an error or not.  Note that we can also get other types
		// of errors such as:
		// 1. Method doesn't have args (ie not a setter property)
		// 2. Access control
		// 3. Unable to convert value to actual property type (NumberFormatException etc...)
		if (throwOnSetPropertyFailure) {
			String msg = "failed write of property: " + propertyName + " value: " + value ;
			throw new DsfRuntimeException(msg) ;
		}
	}
	
	private static void write(
		final Object target, final String name, final String value)
	{
		final Class<?> clz = target instanceof Class 
			? (Class<?>)target 
			: target.getClass() ;
		
		final PropertyDescriptor pd ;
		try {
			String setterName = name.substring(0, 1).toUpperCase(ENGLISH) 
				+ name.substring(1);
			setterName = "set" + setterName ;
			// The PropertyDescriptor will fail if it can't find a getter AND
			// setter.  So we use the constructor that lets us say we only want
			// it to look for the setter.
		    pd = new PropertyDescriptor(name, clz, null, setterName) ;;
		}
		catch(IntrospectionException e) {
			throw new RuntimeException(e) ;
		}
		
		final Class<?> propTypeClz = pd.getPropertyType() ;
		final Method writeMethod = pd.getWriteMethod() ;
	
		try {
			if (propTypeClz == String.class) {
				writeMethod.invoke(target, value) ;
			}
			else if (propTypeClz == int.class || propTypeClz == Integer.class) {
				writeMethod.invoke(target, Integer.valueOf(value)) ;
			}
			else if (propTypeClz == boolean.class || propTypeClz == Boolean.class) {
				writeMethod.invoke(target, Boolean.valueOf(value)) ;
			}
			else if (propTypeClz == double.class || propTypeClz == Double.class) {
				writeMethod.invoke(target, Double.valueOf(value)) ;
			}
			else if (propTypeClz == long.class || propTypeClz == Long.class) {
				writeMethod.invoke(target, Long.valueOf(value)) ;
			}
			else if (propTypeClz == byte.class || propTypeClz == Byte.class) {
				writeMethod.invoke(target, Byte.valueOf(value)) ;
			}
			else if (propTypeClz == short.class || propTypeClz == Short.class) {
				writeMethod.invoke(target, Short.valueOf(value)) ;
			}
			else if (propTypeClz == char.class || propTypeClz == Character.class) {
				writeMethod.invoke(target, value.charAt(0)) ;
			}
			else if (propTypeClz == float.class || propTypeClz == Float.class) {
				writeMethod.invoke(target, Float.valueOf(value)) ;
			}
			else { // treat as Object type
				writeMethod.invoke(target, value) ;	
			}
		}
		catch(IllegalAccessException e) {
			throw new RuntimeException(e) ;
		}
		catch(InvocationTargetException e) {
			throw new RuntimeException(e) ;
		}
		catch(NumberFormatException e) {
			throw new RuntimeException(e) ;
		}
	}
	
	private static String camelcase(String s) {
		int dashIndex = s.indexOf("-") ;
		if (dashIndex == -1) return s ;
		String answer = s.substring(0, dashIndex) ;
		String remainder = s.substring(dashIndex + 1) ;
		remainder = upperCaseFirstLetter(remainder) ;
		answer += remainder ;
		return camelcase(answer) ;
	}
	
	private static String upperCaseFirstLetter(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1) ;
	}
}


