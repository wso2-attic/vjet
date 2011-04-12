/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*
 * LexicalUnitImpl.java
 *
 * Modified from Open-Source CSS Parser (Steady State Software) under
 * GNU Lesser General Public License.
 * 
 */

package org.ebayopensource.dsf.css.parser;

import java.io.Serializable;

import org.ebayopensource.dsf.css.dom.impl.DCssRgbColor;
import org.ebayopensource.dsf.css.sac.ILexicalUnit;


public class DLexicalUnit implements ILexicalUnit, Serializable {

	private static final long serialVersionUID = 1L;
	private short m_type;
	
    private String m_stringVal;
    private float m_floatVal;
    private String m_dimension;
    private String m_function;
    
    private ILexicalUnit m_next;
    private ILexicalUnit m_prev;
    private ILexicalUnit m_params;

    //
    // Constructor(s)
    //
    protected DLexicalUnit(final ILexicalUnit previous, final short type) {
        m_type = type;
        m_prev = previous;
        if (m_prev != null) {
            ((DLexicalUnit)m_prev).m_next = this;
        }
    }

    /**
     * Integer
     */
    protected DLexicalUnit(ILexicalUnit previous, int value) {
        this(previous, SAC_INTEGER);
        m_floatVal = value;
    }

    /**
     * Dimension
     */
    protected DLexicalUnit(ILexicalUnit previous, short type, float value) {
        this(previous, type);
        m_floatVal = value;
    }

    /**
     * Unknown dimension
     */
    protected DLexicalUnit(
        ILexicalUnit previous,
        short type,
        String dimension,
        float value)
    {
        this(previous, type);
        m_dimension = dimension;
        m_floatVal = value;
    }

    /**
     * String
     */
    protected DLexicalUnit(ILexicalUnit previous, short type, String value) {
        this(previous, type);
        m_stringVal = value;
    }

    /**
     * Function
     */
    protected DLexicalUnit(
        final ILexicalUnit previous,
        final short type,
        final String name,
        final ILexicalUnit params)
    {
        this(previous, type);
        m_function = name;
        m_params = params;
    }

    public short getLexicalUnitType() {
        return m_type;
    }
    
    public ILexicalUnit getNextLexicalUnit() {
        return m_next;
    }
    
    public ILexicalUnit getPreviousLexicalUnit() {
        return m_prev;
    }
    
    public int getIntegerValue() {
        return (int) m_floatVal;
    }
    
    public float getFloatValue() {
        return m_floatVal;
    }
    
    public String getDimensionUnitText() {
        switch (m_type) {
        case SAC_EM:
            return "em";
        case SAC_EX:
            return "ex";
        case SAC_PIXEL:
            return "px";
        case SAC_INCH:
            return "in";
        case SAC_CENTIMETER:
            return "cm";
        case SAC_MILLIMETER:
            return "mm";
        case SAC_POINT:
            return "pt";
        case SAC_PICA:
            return "pc";
        case SAC_PERCENTAGE:
            return "%";
        case SAC_DEGREE:
            return "deg";
        case SAC_GRADIAN:
            return "grad";
        case SAC_RADIAN:
            return "rad";
        case SAC_MILLISECOND:
            return "ms";
        case SAC_SECOND:
            return "s";
        case SAC_HERTZ:
            return "Hz";
        case SAC_KILOHERTZ:
            return "kHz";
        case SAC_DIMENSION:
            return m_dimension;
        }
        return "";
    }
    
    public String getFunctionName() {
        return m_function;
    }
    
    public boolean isFunction() {
    	return getFunctionName() != null ;
    }
    
    public ILexicalUnit getParameters() {
        return m_params;
    }

    public String getStringValue() {
        return m_stringVal;
    }

    public ILexicalUnit getSubValues() {
        return m_params;
    }
    
    //
    // Override(s) from Object
    //
    @Override
    /** MrP - Do NOT change this impl as it is used by code gen, sigh... */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        switch (m_type) {
        case SAC_OPERATOR_COMMA:
            sb.append(",");
            break;
        case SAC_OPERATOR_PLUS:
            sb.append("+");
            break;
        case SAC_OPERATOR_MINUS:
            sb.append("-");
            break;
        case SAC_OPERATOR_MULTIPLY:
            sb.append("*");
            break;
        case SAC_OPERATOR_SLASH:
            sb.append("/");
            break;
        case SAC_OPERATOR_MOD:
            sb.append("%");
            break;
        case SAC_OPERATOR_EXP:
            sb.append("^");
            break;
        case SAC_OPERATOR_LT:
            sb.append("<");
            break;
        case SAC_OPERATOR_GT:
            sb.append(">");
            break;
        case SAC_OPERATOR_LE:
            sb.append("<=");
            break;
        case SAC_OPERATOR_GE:
            sb.append(">=");
            break;
        case SAC_OPERATOR_TILDE:
            sb.append("~");
            break;
        case SAC_INHERIT:
            sb.append("inherit");
            break;
        case SAC_INTEGER:
            sb.append(String.valueOf(getIntegerValue()));
            break;
        case SAC_REAL:
            sb.append(trimFloat(getFloatValue()));
            break;
        case SAC_EM:
        case SAC_EX:
        case SAC_PIXEL:
        case SAC_INCH:
        case SAC_CENTIMETER:
        case SAC_MILLIMETER:
        case SAC_POINT:
        case SAC_PICA:
        case SAC_PERCENTAGE:
        case SAC_DEGREE:
        case SAC_GRADIAN:
        case SAC_RADIAN:
        case SAC_MILLISECOND:
        case SAC_SECOND:
        case SAC_HERTZ:
        case SAC_KILOHERTZ:
        case SAC_DIMENSION:
            sb.append(trimFloat(getFloatValue()))
              .append(getDimensionUnitText());
            break;
        case SAC_URI:
            sb.append("url(").append(getStringValue()).append(")");
            break;
        case SAC_COUNTER_FUNCTION:
            sb.append("counter(");
            appendParams(sb, m_params);
            sb.append(")");
            break;
        case SAC_COUNTERS_FUNCTION:
            sb.append("counters(");
            appendParams(sb, m_params);
            sb.append(")");
            break;
        case SAC_RGBCOLOR:
        	DCssRgbColor c = new DCssRgbColor(m_params);
        	sb.append(c.toString());
        	// old code--emits rgb() function instead of #rrggbb notation 
//          sb.append("rgb(");
//          appendParams(sb, m_params);
//          sb.append(")");
        	break;
        case SAC_IDENT:
            sb.append(getStringValue());
            break;
        case SAC_STRING_VALUE:
            sb.append("\"").append(getStringValue()).append("\"");
            break;
        case SAC_ATTR:
            sb.append("attr(");
            appendParams(sb, m_params);
            sb.append(")");
            break;
        case SAC_RECT_FUNCTION:
            sb.append("rect(");
            appendParams(sb, m_params);
            sb.append(")");
            break;
        case SAC_UNICODERANGE:
            sb.append(getStringValue());
            break;
        case SAC_SUB_EXPRESSION:
            sb.append(getStringValue());
            break;
//      case SAC_RGBCOLOR:
        case SAC_HSLCOLOR:
        case SAC_HSLACOLOR:
        case SAC_FUNCTION:
        case SAC_LINEAR_GRADIENT:
        case SAC_RADIAL_GRADIENT:
        case SAC_WEBKIT_GRADIENT:
        case SAC_MOZ_LINEAR_GRADIENT:
        case SAC_MOZ_RADIAL_GRADIENT:
        case SAC_MOZ_REPEATING_LINEAR_GRADIENT:
        case SAC_MOZ_REPEATING_RADIAL_GRADIENT:
        case SAC_RGBACOLOR:

        case SAC_COLOR_STOP:
        case SAC_TO:
        case SAC_FROM:
        	
        case SAC_SCALE:
        case SAC_SCALEX:
        case SAC_SCALEY:
        case SAC_SCALEZ:
        case SAC_SCALE3D:
        	
        case SAC_ROTATE:
        case SAC_ROTATEX:
        case SAC_ROTATEY:
        case SAC_ROTATEZ:
        	
        case SAC_SKEW:
        case SAC_SKEWX:
        case SAC_SKEWY:
        	
        case SAC_TRANSLATE:
        case SAC_TRANSLATEX:
        case SAC_TRANSLATEY:
        case SAC_TRANSLATEZ:
        case SAC_TRANSLATE3D:
        	
        case SAC_MATRIX:
        case SAC_MATRIX3D:

        case SAC_CUBIC_BEZIER:
        	emitFunction(sb, m_params) ;
//            sb.append(getFunctionName());
//            sb.append("(") ;
//            appendParams(sb, m_params);
//            sb.append(")");
            break;
        }
        return sb.toString();
    }
    
    private void emitFunction(StringBuffer sb, ILexicalUnit lu) {
        sb.append(getFunctionName());
        sb.append("(") ;
        appendParams(sb, lu);
        sb.append(")");  
    }

    public String toDebugString() {
        StringBuffer sb = new StringBuffer();
        switch (m_type) {
        case SAC_OPERATOR_COMMA:
            sb.append("SAC_OPERATOR_COMMA");
            break;
        case SAC_OPERATOR_PLUS:
            sb.append("SAC_OPERATOR_PLUS");
            break;
        case SAC_OPERATOR_MINUS:
            sb.append("SAC_OPERATOR_MINUS");
            break;
        case SAC_OPERATOR_MULTIPLY:
            sb.append("SAC_OPERATOR_MULTIPLY");
            break;
        case SAC_OPERATOR_SLASH:
            sb.append("SAC_OPERATOR_SLASH");
            break;
        case SAC_OPERATOR_MOD:
            sb.append("SAC_OPERATOR_MOD");
            break;
        case SAC_OPERATOR_EXP:
            sb.append("SAC_OPERATOR_EXP");
            break;
        case SAC_OPERATOR_LT:
            sb.append("SAC_OPERATOR_LT");
            break;
        case SAC_OPERATOR_GT:
            sb.append("SAC_OPERATOR_GT");
            break;
        case SAC_OPERATOR_LE:
            sb.append("SAC_OPERATOR_LE");
            break;
        case SAC_OPERATOR_GE:
            sb.append("SAC_OPERATOR_GE");
            break;
        case SAC_OPERATOR_TILDE:
            sb.append("SAC_OPERATOR_TILDE");
            break;
        case SAC_INHERIT:
            sb.append("SAC_INHERIT");
            break;
        case SAC_INTEGER:
            sb.append("SAC_INTEGER(")
                .append(String.valueOf(getIntegerValue()))
                .append(")");
            break;
        case SAC_REAL:
            sb.append("SAC_REAL(")
                .append(trimFloat(getFloatValue()))
                .append(")");
            break;
        case SAC_EM:
            sb.append("SAC_EM(")
                .append(trimFloat(getFloatValue()))
                .append(getDimensionUnitText())
                .append(")");
            break;
        case SAC_EX:
            sb.append("SAC_EX(")
                .append(trimFloat(getFloatValue()))
                .append(getDimensionUnitText())
                .append(")");
            break;
        case SAC_PIXEL:
            sb.append("SAC_PIXEL(")
                .append(trimFloat(getFloatValue()))
                .append(getDimensionUnitText())
                .append(")");
            break;
        case SAC_INCH:
            sb.append("SAC_INCH(")
                .append(trimFloat(getFloatValue()))
                .append(getDimensionUnitText())
                .append(")");
            break;
        case SAC_CENTIMETER:
            sb.append("SAC_CENTIMETER(")
                .append(trimFloat(getFloatValue()))
                .append(getDimensionUnitText())
                .append(")");
            break;
        case SAC_MILLIMETER:
            sb.append("SAC_MILLIMETER(")
                .append(trimFloat(getFloatValue()))
                .append(getDimensionUnitText())
                .append(")");
            break;
        case SAC_POINT:
            sb.append("SAC_POINT(")
                .append(trimFloat(getFloatValue()))
                .append(getDimensionUnitText())
                .append(")");
            break;
        case SAC_PICA:
            sb.append("SAC_PICA(")
                .append(trimFloat(getFloatValue()))
                .append(getDimensionUnitText())
                .append(")");
            break;
        case SAC_PERCENTAGE:
            sb.append("SAC_PERCENTAGE(")
                .append(trimFloat(getFloatValue()))
                .append(getDimensionUnitText())
                .append(")");
            break;
        case SAC_DEGREE:
            sb.append("SAC_DEGREE(")
                .append(trimFloat(getFloatValue()))
                .append(getDimensionUnitText())
                .append(")");
            break;
        case SAC_GRADIAN:
            sb.append("SAC_GRADIAN(")
                .append(trimFloat(getFloatValue()))
                .append(getDimensionUnitText())
                .append(")");
            break;
        case SAC_RADIAN:
            sb.append("SAC_RADIAN(")
                .append(trimFloat(getFloatValue()))
                .append(getDimensionUnitText())
                .append(")");
            break;
        case SAC_MILLISECOND:
            sb.append("SAC_MILLISECOND(")
                .append(trimFloat(getFloatValue()))
                .append(getDimensionUnitText())
                .append(")");
            break;
        case SAC_SECOND:
            sb.append("SAC_SECOND(")
                .append(trimFloat(getFloatValue()))
                .append(getDimensionUnitText())
                .append(")");
            break;
        case SAC_HERTZ:
            sb.append("SAC_HERTZ(")
                .append(trimFloat(getFloatValue()))
                .append(getDimensionUnitText())
                .append(")");
            break;
        case SAC_KILOHERTZ:
            sb.append("SAC_KILOHERTZ(")
                .append(trimFloat(getFloatValue()))
                .append(getDimensionUnitText())
                .append(")");
            break;
        case SAC_DIMENSION:
            sb.append("SAC_DIMENSION(")
                .append(trimFloat(getFloatValue()))
                .append(getDimensionUnitText())
                .append(")");
            break;
        case SAC_URI:
            sb.append("SAC_URI(url(")
                .append(getStringValue())
                .append("))");
            break;
        case SAC_COUNTER_FUNCTION:
            sb.append("SAC_COUNTER_FUNCTION(counter(");
            appendParams(sb, m_params);
            sb.append("))");
            break;
        case SAC_COUNTERS_FUNCTION:
            sb.append("SAC_COUNTERS_FUNCTION(counters(");
            appendParams(sb, m_params);
            sb.append("))");
            break;
        case SAC_RGBCOLOR:
            sb.append("SAC_RGBCOLOR(rgb(");
            appendParams(sb, m_params);
            sb.append("))");
            break;
        case SAC_IDENT:
            sb.append("SAC_IDENT(")
                .append(getStringValue())
                .append(")");
            break;
        case SAC_STRING_VALUE:
            sb.append("SAC_STRING_VALUE(\"")
                .append(getStringValue())
                .append("\")");
            break;
        case SAC_ATTR:
            sb.append("SAC_ATTR(attr(");
            appendParams(sb, m_params);
            sb.append("))");
            break;
        case SAC_RECT_FUNCTION:
            sb.append("SAC_RECT_FUNCTION(rect(");
            appendParams(sb, m_params);
            sb.append("))");
            break;
        case SAC_UNICODERANGE:
            sb.append("SAC_UNICODERANGE(")
                .append(getStringValue())
                .append(")");
            break;
        case SAC_SUB_EXPRESSION:
            sb.append("SAC_SUB_EXPRESSION(")
                .append(getStringValue())
                .append(")");
            break;
        case SAC_FUNCTION:
            sb.append("SAC_FUNCTION(")
                .append(getFunctionName())
                .append("(");
            appendParams(sb, m_params);
            sb.append("))");
            break;
        }
        return sb.toString();
    }

    private boolean shouldAddSpace(String nextLuString) {
    	if (",".equals(nextLuString)) return false ;
    	if (")".equals(nextLuString)) return false ;
    	if (";".equals(nextLuString)) return false ;
    	return true ;
    }
    
    private void appendParams(StringBuffer sb, ILexicalUnit first) {
        ILexicalUnit lu = first;
        while (lu != null) { 
        	String funcName = lu.getFunctionName() ;
        	String luAsString = lu.toString();
        	if (funcName != null) {
                sb.append(luAsString) ;
                addSpaceIfNecessary(sb, lu);
        	}
        	else {
	            sb.append(luAsString);
	            addSpaceIfNecessary(sb, lu);
        	}
            lu = lu.getNextLexicalUnit();
        }
    }

    // We need to accomodate rules like:
    // linear-gradient(top 10px, ...)
    // Note that "top" and "10px" need a separating space
	private void addSpaceIfNecessary(final StringBuffer sb, final ILexicalUnit lu) {
		ILexicalUnit next = lu.getNextLexicalUnit() ;
		if (next != null) {
			String nextLuAsString = next.toString() ;
			if (shouldAddSpace(nextLuAsString)) sb.append(" ") ;
		}
	}
    
    private String trimFloat(float f) {
        String s = String.valueOf(getFloatValue());
        return (f - (int) f != 0) ? s : s.substring(0, s.length() - 2);
    }
    
    public static ILexicalUnit createNumber(ILexicalUnit prev, float f) {
        if (f > (int) f) {
            return new DLexicalUnit(prev, ILexicalUnit.SAC_REAL, f);
        }
        return new DLexicalUnit(prev, (int) f);
        
    }
    
    public static ILexicalUnit createPercentage(ILexicalUnit prev, float f) {
        return new DLexicalUnit(prev, ILexicalUnit.SAC_PERCENTAGE, f);
    }
    
    public static ILexicalUnit createPixel(ILexicalUnit prev, float f) {
        return new DLexicalUnit(prev, ILexicalUnit.SAC_PIXEL, f);
    }
    
    public static ILexicalUnit createCentimeter(ILexicalUnit prev, float f) {
        return new DLexicalUnit(prev, ILexicalUnit.SAC_CENTIMETER, f);
    }
    
    public static ILexicalUnit createMillimeter(ILexicalUnit prev, float f) {
        return new DLexicalUnit(prev, ILexicalUnit.SAC_MILLIMETER, f);
    }
    
    public static ILexicalUnit createInch(ILexicalUnit prev, float f) {
        return new DLexicalUnit(prev, ILexicalUnit.SAC_INCH, f);
    }
    
    public static ILexicalUnit createPoint(ILexicalUnit prev, float f) {
        return new DLexicalUnit(prev, ILexicalUnit.SAC_POINT, f);
    }
    
    public static ILexicalUnit createPica(ILexicalUnit prev, float f) {
        return new DLexicalUnit(prev, ILexicalUnit.SAC_PICA, f);
    }
    
    public static ILexicalUnit createEm(ILexicalUnit prev, float f) {
        return new DLexicalUnit(prev, ILexicalUnit.SAC_EM, f);
    }
    
    public static ILexicalUnit createEx(ILexicalUnit prev, float f) {
        return new DLexicalUnit(prev, ILexicalUnit.SAC_EX, f);
    }
    
    public static ILexicalUnit createDegree(ILexicalUnit prev, float f) {
        return new DLexicalUnit(prev, ILexicalUnit.SAC_DEGREE, f);
    }
    
    public static ILexicalUnit createRadian(ILexicalUnit prev, float f) {
        return new DLexicalUnit(prev, ILexicalUnit.SAC_RADIAN, f);
    }
    
    public static ILexicalUnit createGradian(ILexicalUnit prev, float f) {
        return new DLexicalUnit(prev, ILexicalUnit.SAC_GRADIAN, f);
    }
    
    public static ILexicalUnit createMillisecond(ILexicalUnit prev, float f) {
        return new DLexicalUnit(prev, ILexicalUnit.SAC_MILLISECOND, f);
    }
    
    public static ILexicalUnit createSecond(ILexicalUnit prev, float f) {
        return new DLexicalUnit(prev, ILexicalUnit.SAC_SECOND, f);
    }
    
    public static ILexicalUnit createHertz(ILexicalUnit prev, float f) {
        return new DLexicalUnit(prev, ILexicalUnit.SAC_HERTZ, f);
    }
    
    public static ILexicalUnit createDimension(ILexicalUnit prev, float f, String dim) {
        return new DLexicalUnit(prev, ILexicalUnit.SAC_DIMENSION, dim, f);
    }
    
    public static ILexicalUnit createKiloHertz(ILexicalUnit prev, float f) {
        return new DLexicalUnit(prev, ILexicalUnit.SAC_KILOHERTZ, f);
    }
    
    public static ILexicalUnit createCounter(ILexicalUnit prev, ILexicalUnit params) {
        return new DLexicalUnit(prev, ILexicalUnit.SAC_COUNTER_FUNCTION, "counter", params);
    }
    
    public static ILexicalUnit createCounters(ILexicalUnit prev, ILexicalUnit params) {
        return new DLexicalUnit(prev, ILexicalUnit.SAC_COUNTERS_FUNCTION, "counters", params);
    }
    
    public static ILexicalUnit createAttr(ILexicalUnit prev, ILexicalUnit params) {
        return new DLexicalUnit(prev, ILexicalUnit.SAC_ATTR, "attr", params);
    }
    
    public static ILexicalUnit createRect(ILexicalUnit prev, ILexicalUnit params) {
        return new DLexicalUnit(prev, ILexicalUnit.SAC_RECT_FUNCTION, "rect", params);
    }
    
    public static ILexicalUnit createRgbColor(ILexicalUnit prev, ILexicalUnit params) {
        return new DLexicalUnit(prev, ILexicalUnit.SAC_RGBCOLOR, "rgb", params);
    }
    
    public static ILexicalUnit createRgbaColor(ILexicalUnit prev, ILexicalUnit params) {
        return new DLexicalUnit(prev, ILexicalUnit.SAC_RGBACOLOR, "rgba", params);
    }
    
    public static ILexicalUnit createHslColor(ILexicalUnit prev, ILexicalUnit params) {
        return new DLexicalUnit(prev, ILexicalUnit.SAC_HSLCOLOR, "hsl", params);
    }
    
    public static ILexicalUnit createHslaColor(ILexicalUnit prev, ILexicalUnit params) {
        return new DLexicalUnit(prev, ILexicalUnit.SAC_HSLACOLOR, "hsla", params);
    }
    
    /**
     * Functions can be very simple to very complex.  Complex examples would
     * be the various transform functions or gradient functions.
     */
    public static ILexicalUnit createFunction(ILexicalUnit prev, String name, ILexicalUnit params) {
//    	if (name.equals("transform")) {
//    		return new DLexicalUnit(prev, ILexicalUnit.SAC_TRANSFORM, "transform", params);
//    	}
//    	if (name.equals("transform-origin")) {
//    		return new DLexicalUnit(prev, ILexicalUnit.SAC_TRANSFORM_ORIGIN, "transform-origin", params);
//    	}
//    	if (name.equals("-moz-transform")) {
//    		return new DLexicalUnit(prev, ILexicalUnit.SAC_MOZ_TRANSFORM, "-moz-transform", params);
//    	}
//    	if (name.equals("-moz-transform-origin")) {
//    		return new DLexicalUnit(prev, ILexicalUnit.SAC_MOZ_TRANSFORM_ORIGIN, "-moz-transform-origin", params);
//    	}
//    	if (name.equals("-webkit-transform")) {
//    		return new DLexicalUnit(prev, ILexicalUnit.SAC_WEBKIT_TRANSFORM, "-webkit-transform", params);
//    	}
//    	if (name.equals("-webkit-transform-origin")) {
//    		return new DLexicalUnit(prev, ILexicalUnit.SAC_WEBKIT_TRANSFORM_ORIGIN, "-webkit-transform-origin", params);
//    	}
//    	if (name.equals("-o-transform")) {
//    		return new DLexicalUnit(prev, ILexicalUnit.SAC_O_TRANSFORM, "-o-transform", params);
//    	}
//    	if (name.equals("-o-transform-origin")) {
//    		return new DLexicalUnit(prev, ILexicalUnit.SAC_O_TRANSFORM_ORIGIN, "-o-transform-origin", params);
//    	}
    	
    	if (name.equals("scale")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_SCALE, "scale", params);
    	}
    	if (name.equals("scalex")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_SCALEX, "scaleX", params);
    	}
    	if (name.equals("scaley")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_SCALEY, "scaleY", params);
    	}
    	if (name.equals("scalez")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_SCALEZ, "scaleZ", params);
    	}
    	if (name.equals("scale3d")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_SCALE3D, "scale3d", params);
    	}
    	
    	if (name.equals("rotate")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_ROTATE, "rotate", params);
    	}
    	if (name.equals("rotatex")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_ROTATEX, "rotateX", params);
    	}
    	if (name.equals("rotatey")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_ROTATEY, "rotateY", params);
    	}
    	if (name.equals("rotatez")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_ROTATEZ, "rotateZ", params);
    	}
    	
    	if (name.equals("skew")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_SKEW, "skew", params);
    	}
    	if (name.equals("skewx")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_SKEWX, "skewX", params);
    	}
    	if (name.equals("skewy")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_SKEWY, "skewY", params);
    	}
    	
    	if (name.equals("translate")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_TRANSLATE, "translate", params);
    	}
    	if (name.equals("translatex")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_TRANSLATEX, "translateX", params);
    	}
    	if (name.equals("translatey")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_TRANSLATEY, "translateY", params);
    	}
    	if (name.equals("translatez")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_TRANSLATEZ, "translateZ", params);
    	}
    	if (name.equals("translate")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_TRANSLATE, "translate", params);
    	}

    	if (name.equals("matrix")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_MATRIX, "matrix", params);
    	}
    	if (name.equals("matrix3d")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_MATRIX3D, "matrix3d", params);
    	}
    	
    	if (name.equals("cubic-bezier")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_COLOR_STOP, "cubic-bezier", params);
    	}
    	
    	if (name.equals("color-stop")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_CUBIC_BEZIER, "color-stop", params);
    	}
    	if (name.equals("to")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_TO, "to", params);
    	}
    	if (name.equals("from")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_FROM, "from", params);
    	}
    	
    	if (name.equals("linear-gradient")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_LINEAR_GRADIENT, "linear-gradient", params);
    	}
    	else if (name.equals("radial-gradient")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_RADIAL_GRADIENT, "radial-gradient", params);
    	}
    	else if (name.equals("-webkit-gradient")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_WEBKIT_GRADIENT, "-webkit-gradient", params);
    	}
    	else if (name.equals("-moz-linear-gradient")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_MOZ_LINEAR_GRADIENT, "-moz-linear-gradient", params);
    	}
    	else if (name.equals("-moz-repeating-linear-gradient")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_MOZ_REPEATING_LINEAR_GRADIENT, "-moz-repeating-linear-gradient", params);
    	}
    	else if (name.equals("-moz-radial-gradient")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_MOZ_RADIAL_GRADIENT, "-moz-radial-gradient", params);
    	}
    	else if (name.equals("-moz-repeating-radial-gradient")) {
    		return new DLexicalUnit(prev, ILexicalUnit.SAC_MOZ_REPEATING_RADIAL_GRADIENT, "-moz-repeating-radial-gradient", params);
    	}
        return new DLexicalUnit(prev, ILexicalUnit.SAC_FUNCTION, name, params);
    }

    public static ILexicalUnit createString(ILexicalUnit prev, String value) {
        return new DLexicalUnit(prev, ILexicalUnit.SAC_STRING_VALUE, value);
    }
    
    public static ILexicalUnit createIdent(ILexicalUnit prev, String value) {
        return new DLexicalUnit(prev, ILexicalUnit.SAC_IDENT, value);
    }
    
    public static ILexicalUnit createURI(ILexicalUnit prev, String value) {
        return new DLexicalUnit(prev, ILexicalUnit.SAC_URI, value);
    }
    
    public static ILexicalUnit createComma(ILexicalUnit prev) {
        return new DLexicalUnit(prev, SAC_OPERATOR_COMMA);
    }
}
