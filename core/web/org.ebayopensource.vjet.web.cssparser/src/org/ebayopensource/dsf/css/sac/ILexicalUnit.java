/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.sac;

public interface ILexicalUnit
{
	short SAC_OPERATOR_COMMA = 0;
	short SAC_OPERATOR_PLUS = 1;
	short SAC_OPERATOR_MINUS = 2;
	short SAC_OPERATOR_MULTIPLY = 3;
	short SAC_OPERATOR_SLASH = 4;
	short SAC_OPERATOR_MOD = 5;
	short SAC_OPERATOR_EXP = 6;
	short SAC_OPERATOR_LT = 7;
	short SAC_OPERATOR_GT = 8;
	short SAC_OPERATOR_LE = 9;
	short SAC_OPERATOR_GE = 10;
	short SAC_OPERATOR_TILDE = 11;
	short SAC_INHERIT = 12;
	short SAC_INTEGER = 13;
	short SAC_REAL = 14;
	short SAC_EM = 15;
	short SAC_EX = 16;
	short SAC_PIXEL = 17;
	short SAC_INCH = 18;
	short SAC_CENTIMETER = 19;
	short SAC_MILLIMETER = 20;
	short SAC_POINT = 21;
	short SAC_PICA = 22;
	short SAC_PERCENTAGE = 23;
	short SAC_URI = 24;
	short SAC_COUNTER_FUNCTION = 25;
	short SAC_COUNTERS_FUNCTION = 26;
	short SAC_RGBCOLOR = 27;
	short SAC_DEGREE = 28;
	short SAC_GRADIAN = 29;
	short SAC_RADIAN = 30;
	short SAC_MILLISECOND = 31;
	short SAC_SECOND = 32;
	short SAC_HERTZ = 33;
	short SAC_KILOHERTZ = 34;
	short SAC_IDENT = 35;
	short SAC_STRING_VALUE = 36;
	short SAC_ATTR = 37;
	short SAC_RECT_FUNCTION = 38;
	short SAC_UNICODERANGE = 39;
	short SAC_SUB_EXPRESSION = 40;
	short SAC_FUNCTION = 41;
	short SAC_DIMENSION = 42;
	short SAC_RGBACOLOR = 43 ;
	short SAC_HSLCOLOR = 44 ;
	short SAC_HSLACOLOR = 45 ;
	
	short SAC_LINEAR_GRADIENT = 46 ;
	short SAC_RADIAL_GRADIENT = 47 ;
	short SAC_WEBKIT_GRADIENT = 48 ;
	short SAC_MOZ_LINEAR_GRADIENT = 49 ;
	short SAC_MOZ_RADIAL_GRADIENT = 50 ;
	short SAC_MOZ_REPEATING_LINEAR_GRADIENT = 51 ;
	short SAC_MOZ_REPEATING_RADIAL_GRADIENT = 52 ;
	short SAC_COLOR_STOP = 53 ;	// used in gradients
	short SAC_TO = 54 ;			// used in gradients
	short SAC_FROM = 55 ;		// used in gradients
	

	short SAC_SCALE = 56 ;
	short SAC_SCALEX = 57 ;
	short SAC_SCALEY = 58 ;
	short SAC_SCALEZ = 59 ;
	short SAC_SCALE3D = 60 ;
	
	short SAC_ROTATE = 61 ;
	short SAC_ROTATEX = 62 ;
	short SAC_ROTATEY = 63 ;
	short SAC_ROTATEZ = 64 ;
	
	short SAC_SKEW = 65 ;
	short SAC_SKEWX = 66 ;
	short SAC_SKEWY = 67 ;
	
	short SAC_TRANSLATE = 68 ;
	short SAC_TRANSLATEX = 69 ;
	short SAC_TRANSLATEY = 70 ;
	short SAC_TRANSLATEZ = 71 ;
	short SAC_TRANSLATE3D = 72 ;

	short SAC_MATRIX = 73 ;
	short SAC_MATRIX3D = 74 ;

	short SAC_CUBIC_BEZIER = 75 ;
	
	short SAC_TRANSFORM = 76 ;
	short SAC_TRANSFORM_ORIGIN = 77 ;
	short SAC_MOZ_TRANSFORM = 78 ;
	short SAC_MOZ_TRANSFORM_ORIGIN = 79 ;
	short SAC_WEBKIT_TRANSFORM = 80 ;
	short SAC_WEBKIT_TRANSFORM_ORIGIN = 81 ;
	short SAC_O_TRANSFORM = 82 ;
	short SAC_O_TRANSFORM_ORIGIN = 83 ;
    
	short getLexicalUnitType();
    
	ILexicalUnit getNextLexicalUnit();
    
	ILexicalUnit getPreviousLexicalUnit();
    
	int getIntegerValue();
    
	float getFloatValue();
    
	String getDimensionUnitText();
    
	String getFunctionName();
    
	ILexicalUnit getParameters();
    
	String getStringValue();
    
	ILexicalUnit getSubValues();
}
