/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.dom.impl;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.DOMException;

import org.ebayopensource.dsf.css.dom.ICounter;
import org.ebayopensource.dsf.css.dom.ICssPrimitiveValue;
import org.ebayopensource.dsf.css.dom.ICssValue;
import org.ebayopensource.dsf.css.dom.ICssValueList;
import org.ebayopensource.dsf.css.dom.IHslColor;
import org.ebayopensource.dsf.css.dom.IHslaColor;
import org.ebayopensource.dsf.css.dom.IRect;
import org.ebayopensource.dsf.css.dom.IRgbColor;
import org.ebayopensource.dsf.css.dom.IRgbaColor;
import org.ebayopensource.dsf.css.parser.DCssBuilder;
import org.ebayopensource.dsf.css.parser.DLexicalUnit;
import org.ebayopensource.dsf.css.sac.ILexicalUnit;
import org.ebayopensource.dsf.css.sac.InputSource;

/**
 * @see org.w3c.dom.css.CSSPrimitiveValue
 * @see org.w3c.dom.css.CSSValueList
 */

public class DCssValue implements ICssPrimitiveValue, ICssValueList,
		Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Object m_value = null;

	public DCssValue(ILexicalUnit value, boolean forcePrimitive) {
		if ((value.getParameters() != null) && (value.getNextLexicalUnit() == null)) {
			switch(value.getLexicalUnitType()) {
				case ILexicalUnit.SAC_RECT_FUNCTION:
					m_value = new DCssRect(value.getParameters());
					break ;
				case ILexicalUnit.SAC_RGBCOLOR:
					m_value = new DCssRgbColor(value.getParameters());
					break ;
				case ILexicalUnit.SAC_RGBACOLOR:
					m_value = new DCssRgbaColor(value.getParameters());
					break ;
				case ILexicalUnit.SAC_HSLCOLOR:
					m_value = new DCssHslColor(value.getParameters());
					break ;
				case ILexicalUnit.SAC_HSLACOLOR:
					m_value = new DCssHslaColor(value.getParameters());
					break ;
				case ILexicalUnit.SAC_COUNTER_FUNCTION:
					m_value = new DCssCounter(false, value.getParameters());
					break ;
				case ILexicalUnit.SAC_COUNTERS_FUNCTION:
					m_value = new DCssCounter(true, value.getParameters());
					break ;
				case ILexicalUnit.SAC_LINEAR_GRADIENT:
					m_value = new DCssLinearGradient(value);
					break ;
				case ILexicalUnit.SAC_RADIAL_GRADIENT:
//throw new RuntimeException("DCssRadialGradient class needs to be implemented") ;
					m_value = new DCssLinearGradient(value);
//					m_value = new DCssRadialGradient(value.getParameters());
					break ;
				case ILexicalUnit.SAC_WEBKIT_GRADIENT:
//throw new RuntimeException("DCssWebkitGradient class needs to be implemented") ;
					m_value = new DCssLinearGradient(value);
//					m_value = new DCssWebkitGradient(value.getParameters());
					break ;
				case ILexicalUnit.SAC_MOZ_LINEAR_GRADIENT:
//throw new RuntimeException("DCssMozLinearGradient class needs to be implemented") ;
					m_value = new DCssLinearGradient(value);
//					m_value = new DCssMozLinearGradient(value.getParameters());
					break ;
				case ILexicalUnit.SAC_MOZ_RADIAL_GRADIENT:
//throw new RuntimeException("DCssMozRadialGradient class needs to be implemented") ;
					m_value = new DCssLinearGradient(value);
//					m_value = new DCssMozRadialGradient(value.getParameters());
					break ;
				case ILexicalUnit.SAC_MOZ_REPEATING_LINEAR_GRADIENT:
//throw new RuntimeException("DCssMozRepeatingLinearGradient class needs to be implemented") ;
					m_value = new DCssLinearGradient(value);
//					m_value = new DCssMozRepeatingLinearGradient(value.getParameters());
					break ;
				case ILexicalUnit.SAC_MOZ_REPEATING_RADIAL_GRADIENT:
//throw new RuntimeException("DCssMozRepeatingRadialGradient class needs to be implemented") ;
					m_value = new DCssLinearGradient(value);
//					m_value = new DCssMozRepeatingLinearGradient(value.getParameters());
					break ;
				case ILexicalUnit.SAC_COLOR_STOP:
				case ILexicalUnit.SAC_TO:
				case ILexicalUnit.SAC_FROM:
					
				case ILexicalUnit.SAC_SCALE :
				case ILexicalUnit.SAC_SCALEX :
				case ILexicalUnit.SAC_SCALEY :
				case ILexicalUnit.SAC_SCALEZ :
				case ILexicalUnit.SAC_SCALE3D :
					
				case ILexicalUnit.SAC_ROTATE :
				case ILexicalUnit.SAC_ROTATEX :
				case ILexicalUnit.SAC_ROTATEY :
				case ILexicalUnit.SAC_ROTATEZ :
					
				case ILexicalUnit.SAC_SKEW :
				case ILexicalUnit.SAC_SKEWX :
				case ILexicalUnit.SAC_SKEWY :
					
				case ILexicalUnit.SAC_TRANSLATE :
				case ILexicalUnit.SAC_TRANSLATEX :
				case ILexicalUnit.SAC_TRANSLATEY :
				case ILexicalUnit.SAC_TRANSLATEZ :
				case ILexicalUnit.SAC_TRANSLATE3D :
					
				case ILexicalUnit.SAC_MATRIX :
				case ILexicalUnit.SAC_MATRIX3D :
					
				case ILexicalUnit.SAC_CUBIC_BEZIER :
					
//				case ILexicalUnit.SAC_TRANSFORM :
//				case ILexicalUnit.SAC_TRANSFORM_ORIGIN :
//				case ILexicalUnit.SAC_MOZ_TRANSFORM :
//				case ILexicalUnit.SAC_MOZ_TRANSFORM_ORIGIN :
//				case ILexicalUnit.SAC_WEBKIT_TRANSFORM :
//				case ILexicalUnit.SAC_WEBKIT_TRANSFORM_ORIGIN :
//				case ILexicalUnit.SAC_O_TRANSFORM   :
//				case ILexicalUnit.SAC_O_TRANSFORM_ORIGIN :

					m_value = new DCssLinearGradient(value);
					break ;
				default:
					m_value = value;
			}
			
//			if (value.getLexicalUnitType() == ILexicalUnit.SAC_RECT_FUNCTION) {
//				m_value = new DCssRect(value.getParameters());
//			} 
//			else if (value.getLexicalUnitType() == ILexicalUnit.SAC_RGBCOLOR) {
//				m_value = new DCssRgbColor(value.getParameters());
//			} 
//			else if (value.getLexicalUnitType() == ILexicalUnit.SAC_RGBACOLOR) {
//				m_value = new DCssRgbaColor(value.getParameters());
//			} 
//			else if (value.getLexicalUnitType() == ILexicalUnit.SAC_HSLCOLOR) {
//				m_value = new DCssHslColor(value.getParameters());
//			} 
//			else if (value.getLexicalUnitType() == ILexicalUnit.SAC_HSLACOLOR) {
//				m_value = new DCssHslaColor(value.getParameters());
//			} 
//			else if (value.getLexicalUnitType() == ILexicalUnit.SAC_COUNTER_FUNCTION) {
//				m_value = new DCssCounter(false, value.getParameters());
//			} 
//			else if (value.getLexicalUnitType() == ILexicalUnit.SAC_COUNTERS_FUNCTION) {
//				m_value = new DCssCounter(true, value.getParameters());
//			} 
//			else {
//				m_value = value;
//			}
		} 
		else if (forcePrimitive || (value.getNextLexicalUnit() == null)) {
			// We need to be a CSSPrimitiveValue
			m_value = value;
		} 
		else {

			// We need to be a CSSValueList
			// Values in an "expr" can be seperated by "operator"s, which are
			// either '/' or ',' - ignore these operators
			List<ICssValue> v = new ArrayList<ICssValue>();
			ILexicalUnit lu = value;
			while (lu != null) {
				if ((lu.getLexicalUnitType() != ILexicalUnit.SAC_OPERATOR_COMMA)
						&& (lu.getLexicalUnitType() != ILexicalUnit.SAC_OPERATOR_SLASH)) {

					v.add(new DCssValue(lu, true));
				}

				lu = lu.getNextLexicalUnit();
			}
			m_value = v;
		}
	}

	public DCssValue(ILexicalUnit value) {
		this(value, false);
	}

	public String getCssText() {
		if (getCssValueType() == CSS_VALUE_LIST) {

			// Create the string from the LexicalUnits so we include the correct
			// operators in the string
			StringBuffer sb = new StringBuffer();
			List v = (List) m_value;
			ILexicalUnit lu = (ILexicalUnit) ((DCssValue) v.get(0)).m_value;
			while (lu != null) {
				sb.append(lu.toString());

				// Step to the next lexical unit, determining what spacing we
				// need to put around the operators
				ILexicalUnit prev = lu;
				lu = lu.getNextLexicalUnit();
				if ((lu != null)
						&& (lu.getLexicalUnitType() != ILexicalUnit.SAC_OPERATOR_COMMA)
						&& (lu.getLexicalUnitType() != ILexicalUnit.SAC_OPERATOR_SLASH)
						&& (prev.getLexicalUnitType() != ILexicalUnit.SAC_OPERATOR_SLASH)) {
					sb.append(" ");
				}
			}
			return sb.toString();
		}
		return m_value.toString();
	}

	public ICssValue setCssText(String cssText) throws DOMException {
		try {
			InputSource is = new InputSource(new StringReader(cssText));
			DCssBuilder parser = new DCssBuilder();
			DCssValue v2 = parser.parsePropertyValue(is);
			m_value = v2.m_value;
		} catch (Exception e) {
			throw new DCssException(DOMException.SYNTAX_ERR,
					DCssException.SYNTAX_ERROR, e.getMessage());
		}
		return this;
	}

	public short getCssValueType() {
		if (m_value instanceof List) {
			return CSS_VALUE_LIST;
		}

		if (m_value instanceof ILexicalUnit
				&& ((ILexicalUnit) m_value).getLexicalUnitType() 
					== ILexicalUnit.SAC_INHERIT) {
			return CSS_INHERIT;
		}
		return CSS_PRIMITIVE_VALUE;
	}

	public short getPrimitiveType() {
		if (m_value instanceof ILexicalUnit) {
			ILexicalUnit lu = (ILexicalUnit) m_value;
			switch (lu.getLexicalUnitType()) {
			case ILexicalUnit.SAC_INHERIT:
				return CSS_IDENT;
			case ILexicalUnit.SAC_INTEGER:
			case ILexicalUnit.SAC_REAL:
				return CSS_NUMBER;
			case ILexicalUnit.SAC_EM:
				return CSS_EMS;
			case ILexicalUnit.SAC_EX:
				return CSS_EXS;
			case ILexicalUnit.SAC_PIXEL:
				return CSS_PX;
			case ILexicalUnit.SAC_INCH:
				return CSS_IN;
			case ILexicalUnit.SAC_CENTIMETER:
				return CSS_CM;
			case ILexicalUnit.SAC_MILLIMETER:
				return CSS_MM;
			case ILexicalUnit.SAC_POINT:
				return CSS_PT;
			case ILexicalUnit.SAC_PICA:
				return CSS_PC;
			case ILexicalUnit.SAC_PERCENTAGE:
				return CSS_PERCENTAGE;
			case ILexicalUnit.SAC_URI:
				return CSS_URI;
			// case DLexicalUnit.SAC_COUNTER_FUNCTION:
			// case DLexicalUnit.SAC_COUNTERS_FUNCTION:
			// return CSS_COUNTER;
			// case DLexicalUnit.SAC_RGBCOLOR:
			// return CSS_RGBCOLOR;
			case ILexicalUnit.SAC_DEGREE:
				return CSS_DEG;
			case ILexicalUnit.SAC_GRADIAN:
				return CSS_GRAD;
			case ILexicalUnit.SAC_RADIAN:
				return CSS_RAD;
			case ILexicalUnit.SAC_MILLISECOND:
				return CSS_MS;
			case ILexicalUnit.SAC_SECOND:
				return CSS_S;
			case ILexicalUnit.SAC_HERTZ:
				return CSS_KHZ;
			case ILexicalUnit.SAC_KILOHERTZ:
				return CSS_HZ;
			case ILexicalUnit.SAC_IDENT:
				return CSS_IDENT;
			case ILexicalUnit.SAC_STRING_VALUE:
				return CSS_STRING;
			case ILexicalUnit.SAC_ATTR:
				return CSS_ATTR;
			// case DLexicalUnit.SAC_RECT_FUNCTION:
			// return CSS_RECT;
			case ILexicalUnit.SAC_UNICODERANGE:
			case ILexicalUnit.SAC_SUB_EXPRESSION:
			case ILexicalUnit.SAC_FUNCTION:
				return CSS_STRING;
			case ILexicalUnit.SAC_DIMENSION:
				return CSS_DIMENSION;
			}
		} 
		else if (m_value instanceof IRect) {
			return CSS_RECT;
		} 
		else if (m_value instanceof IRgbColor) {
			return CSS_RGBCOLOR;
		} 
		else if (m_value instanceof IRgbaColor) {
			return CSS_RGBACOLOR;
		} 
		else if (m_value instanceof IHslColor) {
			return CSS_HSLCOLOR;
		} 
		else if (m_value instanceof IHslaColor) {
			return CSS_HSLACOLOR;
		} 
		else if (m_value instanceof ICounter) {
			return CSS_COUNTER;
		}
		return CSS_UNKNOWN;
	}

	public void setFloatValue(short unitType, float floatValue)
			throws DOMException {
		m_value = DLexicalUnit.createNumber(null, floatValue);
	}

	public float getFloatValue(short unitType) throws DOMException {
		if (m_value instanceof ILexicalUnit) {
			ILexicalUnit lu = (ILexicalUnit) m_value;
			return lu.getFloatValue();
		}
		throw new DCssException(
			DOMException.INVALID_ACCESS_ERR, DCssException.FLOAT_ERROR);

		// We need to attempt a conversion
		// return 0;
	}

	public void setStringValue(short stringType, String stringValue)
			throws DOMException {
		switch (stringType) {
		case CSS_STRING:
			m_value = DLexicalUnit.createString(null, stringValue);
			break;
		case CSS_URI:
			m_value = DLexicalUnit.createURI(null, stringValue);
			break;
		case CSS_IDENT:
			m_value = DLexicalUnit.createIdent(null, stringValue);
			break;
		case CSS_ATTR:
			// _value = LexicalUnitImpl.createAttr(null, stringValue);
			// break;
			throw new DCssException(DOMException.NOT_SUPPORTED_ERR,
					DCssException.NOT_IMPLEMENTED);
		default:
			throw new DCssException(DOMException.INVALID_ACCESS_ERR,
					DCssException.STRING_ERROR);
		}
	}

	/**
	 * TODO: return a value for a list type
	 */
	public String getStringValue() throws DOMException {
		if (m_value instanceof ILexicalUnit) {
			ILexicalUnit lu = (ILexicalUnit) m_value;
			if ((lu.getLexicalUnitType() == ILexicalUnit.SAC_IDENT)
					|| (lu.getLexicalUnitType() == ILexicalUnit.SAC_STRING_VALUE)
					|| (lu.getLexicalUnitType() == ILexicalUnit.SAC_URI)
					|| (lu.getLexicalUnitType() == ILexicalUnit.SAC_ATTR)) {
				return lu.getStringValue();
			}
		} else if (m_value instanceof List) {
			return null;
		}

		throw new DCssException(DOMException.INVALID_ACCESS_ERR,
				DCssException.STRING_ERROR);
	}

	public ICounter getCounterValue() throws DOMException {
		if ((m_value instanceof ICounter) == false) {
			throw new DCssException(DOMException.INVALID_ACCESS_ERR,
					DCssException.COUNTER_ERROR);
		}
		return (ICounter) m_value;
	}

	public IRect getRectValue() throws DOMException {
		if ((m_value instanceof IRect) == false) {
			throw new DCssException(DOMException.INVALID_ACCESS_ERR,
					DCssException.RECT_ERROR);
		}
		return (IRect) m_value;
	}

	public IRgbColor getRGBColorValue() throws DOMException {
		if ((m_value instanceof IRgbColor) == false) {
			throw new DCssException(DOMException.INVALID_ACCESS_ERR, DCssException.RGBCOLOR_ERROR);
		}
		return (IRgbColor) m_value;
	}
	
	public IRgbaColor getRGBAColorValue() throws DOMException {
		if ((m_value instanceof IRgbaColor) == false) {
			throw new DCssException(DOMException.INVALID_ACCESS_ERR, DCssException.RGBACOLOR_ERROR);
		}
		return (IRgbaColor) m_value;
	}
	
	public IHslColor getHSLColorValue() throws DOMException {
		if ((m_value instanceof IHslColor) == false) {
			throw new DCssException(DOMException.INVALID_ACCESS_ERR, DCssException.HSLCOLOR_ERROR);
		}
		return (IHslColor) m_value;
	}
	
	public IHslaColor getHSLAColorValue() throws DOMException {
		if ((m_value instanceof IHslaColor) == false) {
			throw new DCssException(DOMException.INVALID_ACCESS_ERR, DCssException.HSLACOLOR_ERROR);
		}
		return (IHslaColor) m_value;
	}

	public int getLength() {
		return (m_value instanceof List) ? ((List) m_value).size() : 0;
	}

	public ICssValue item(int index) {
		return (m_value instanceof List) ? ((ICssValue) ((List) m_value)
				.get(index)) : null;
	}

	public String toString() {
		return getCssText();
	}

	public Object clone() throws CloneNotSupportedException {
		DCssValue copy = (DCssValue) super.clone();
		if (copy.m_value instanceof DCssRect) {
			copy.m_value = ((DCssRect) copy.m_value).clone();
		} else if (copy.m_value instanceof DCssRgbColor) {
			copy.m_value = ((DCssRgbColor) copy.m_value).clone();
		} else if (copy.m_value instanceof DCssCounter) {
			copy.m_value = ((DCssCounter) copy.m_value).clone();
		} else if (copy.m_value instanceof List) {
			copy.m_value = ((ArrayList) copy.m_value).clone();
		}
		return copy;
	}
}
