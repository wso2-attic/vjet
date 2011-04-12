/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.datatype;

/*
 * Class uses Character - wrapper for char
 * Wrapper test Areas - Character Class fields,methods,constructor 
 */
public class CharWrapperSrc {

	public void characterConstructors() {
		char v_char = 'A';
		// -- Unitialised
		Character v_characterObj;
		// --NULL
		Character v_characterObj0 = null;
		// --public java.lang.Character(char)
		Character v_characterObj1 = new java.lang.Character(v_char);
	}

	public void characterWrapperFields() {
		// --MIN_RADIX
		int v_character0 = Character.MIN_RADIX;
		// --MAX_RADIX
		int v_character1 = Character.MAX_RADIX;
		// --MIN_VALUE
		char v_character2 = Character.MIN_VALUE;
		// --MAX_VALUE
		char v_character3 = Character.MAX_VALUE;
		// --TYPE
		java.lang.Class<java.lang.Character> v_character4 = java.lang.Character.TYPE;
		// --UNASSIGNED
		byte v_character5 = Character.UNASSIGNED;
		// --UPPERCASE_LETTER
		byte v_character6 = Character.UPPERCASE_LETTER;
		// --LOWERCASE_LETTER
		byte v_character7 = Character.LOWERCASE_LETTER;
		// --TITLECASE_LETTER
		byte v_character8 = Character.TITLECASE_LETTER;
		// --MODIFIER_LETTER
		byte v_character9 = Character.MODIFIER_LETTER;
		// --OTHER_LETTER
		byte v_character10 = Character.OTHER_LETTER;
		// --NON_SPACING_MARK
		byte v_character11 = Character.NON_SPACING_MARK;
		// --ENCLOSING_MARK
		byte v_character12 = Character.ENCLOSING_MARK;
		// --COMBINING_SPACING_MARK
		byte v_character13 = Character.COMBINING_SPACING_MARK;
		// --DECIMAL_DIGIT_NUMBER
		byte v_character14 = Character.DECIMAL_DIGIT_NUMBER;
		// --LETTER_NUMBER
		byte v_character15 = Character.LETTER_NUMBER;
		// --OTHER_NUMBER
		byte v_character16 = Character.OTHER_NUMBER;
		// --SPACE_SEPARATOR
		byte v_character17 = Character.SPACE_SEPARATOR;
		// --LINE_SEPARATOR
		byte v_character18 = Character.LINE_SEPARATOR;
		// --PARAGRAPH_SEPARATOR
		byte v_character19 = Character.PARAGRAPH_SEPARATOR;
		// --CONTROL
		byte v_character20 = Character.CONTROL;
		// --FORMAT
		byte v_character21 = Character.FORMAT;
		// --PRIVATE_USE
		byte v_character22 = Character.PRIVATE_USE;
		// --SURROGATE
		byte v_character23 = Character.SURROGATE;
		// --DASH_PUNCTUATION
		byte v_character24 = Character.DASH_PUNCTUATION;
		// --START_PUNCTUATION
		byte v_character25 = Character.START_PUNCTUATION;
		// --END_PUNCTUATION
		byte v_character26 = Character.END_PUNCTUATION;
		// --CONNECTOR_PUNCTUATION
		byte v_character27 = Character.CONNECTOR_PUNCTUATION;
		// --OTHER_PUNCTUATION
		byte v_character28 = Character.OTHER_PUNCTUATION;
		// --MATH_SYMBOL
		byte v_character29 = Character.MATH_SYMBOL;
		// --CURRENCY_SYMBOL
		byte v_character30 = Character.CURRENCY_SYMBOL;
		// --MODIFIER_SYMBOL
		byte v_character31 = Character.MODIFIER_SYMBOL;
		// --OTHER_SYMBOL
		byte v_character32 = Character.OTHER_SYMBOL;
		// --INITIAL_QUOTE_PUNCTUATION
		byte v_character33 = Character.INITIAL_QUOTE_PUNCTUATION;
		// --FINAL_QUOTE_PUNCTUATION
		byte v_character34 = Character.FINAL_QUOTE_PUNCTUATION;
		// --DIRECTIONALITY_UNDEFINED
		byte v_character35 = Character.DIRECTIONALITY_UNDEFINED;
		// --DIRECTIONALITY_LEFT_TO_RIGHT
		byte v_character36 = Character.DIRECTIONALITY_LEFT_TO_RIGHT;
		// --DIRECTIONALITY_RIGHT_TO_LEFT
		byte v_character37 = Character.DIRECTIONALITY_RIGHT_TO_LEFT;
		// --DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC
		byte v_character38 = Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
		// --DIRECTIONALITY_EUROPEAN_NUMBER
		byte v_character39 = Character.DIRECTIONALITY_EUROPEAN_NUMBER;
		// --DIRECTIONALITY_EUROPEAN_NUMBER_SEPARATOR
		byte v_character40 = Character.DIRECTIONALITY_EUROPEAN_NUMBER_SEPARATOR;
		// --DIRECTIONALITY_EUROPEAN_NUMBER_TERMINATOR
		byte v_character41 = Character.DIRECTIONALITY_EUROPEAN_NUMBER_TERMINATOR;
		// --DIRECTIONALITY_ARABIC_NUMBER
		byte v_character42 = Character.DIRECTIONALITY_ARABIC_NUMBER;
		// --DIRECTIONALITY_COMMON_NUMBER_SEPARATOR
		byte v_character43 = Character.DIRECTIONALITY_COMMON_NUMBER_SEPARATOR;
		// --DIRECTIONALITY_NONSPACING_MARK
		byte v_character44 = Character.DIRECTIONALITY_NONSPACING_MARK;
		// --DIRECTIONALITY_BOUNDARY_NEUTRAL
		byte v_character45 = Character.DIRECTIONALITY_BOUNDARY_NEUTRAL;
		// --DIRECTIONALITY_PARAGRAPH_SEPARATOR
		byte v_character46 = Character.DIRECTIONALITY_PARAGRAPH_SEPARATOR;
		// --DIRECTIONALITY_SEGMENT_SEPARATOR
		byte v_character47 = Character.DIRECTIONALITY_SEGMENT_SEPARATOR;
		// --DIRECTIONALITY_WHITESPACE
		byte v_character48 = Character.DIRECTIONALITY_WHITESPACE;
		// --DIRECTIONALITY_OTHER_NEUTRALS
		byte v_character49 = Character.DIRECTIONALITY_OTHER_NEUTRALS;
		// --DIRECTIONALITY_LEFT_TO_RIGHT_EMBEDDING
		byte v_character50 = Character.DIRECTIONALITY_LEFT_TO_RIGHT_EMBEDDING;
		// --DIRECTIONALITY_LEFT_TO_RIGHT_OVERRIDE
		byte v_character51 = Character.DIRECTIONALITY_LEFT_TO_RIGHT_OVERRIDE;
		// --DIRECTIONALITY_RIGHT_TO_LEFT_EMBEDDING
		byte v_character52 = Character.DIRECTIONALITY_RIGHT_TO_LEFT_EMBEDDING;
		// --DIRECTIONALITY_RIGHT_TO_LEFT_OVERRIDE
		byte v_character53 = Character.DIRECTIONALITY_RIGHT_TO_LEFT_OVERRIDE;
		// --DIRECTIONALITY_POP_DIRECTIONAL_FORMAT
		byte v_character54 = Character.DIRECTIONALITY_POP_DIRECTIONAL_FORMAT;
		// --MIN_HIGH_SURROGATE
		char v_character55 = Character.MIN_HIGH_SURROGATE;
		// --MAX_HIGH_SURROGATE
		char v_character56 = Character.MAX_HIGH_SURROGATE;
		// --MIN_LOW_SURROGATE
		char v_character57 = Character.MIN_LOW_SURROGATE;
		// --MAX_LOW_SURROGATE
		char v_character58 = Character.MAX_LOW_SURROGATE;
		// --MIN_SURROGATE
		char v_character59 = Character.MIN_SURROGATE;
		// --MAX_SURROGATE
		char v_character60 = Character.MAX_SURROGATE;
		// --MIN_SUPPLEMENTARY_CODE_POINT
		int v_character61 = Character.MIN_SUPPLEMENTARY_CODE_POINT;
		// --MIN_CODE_POINT
		int v_character62 = Character.MIN_CODE_POINT;
		// --MAX_CODE_POINT
		int v_character63 = Character.MAX_CODE_POINT;
		// --SIZE
		int v_character64 = Character.SIZE;
	}

	public void characterWrapperMthd() throws Exception {

		int v_int = 900, v_int1 = 200;
		long v_long = 900;
		Object o = new Object();
		char v_char = 'A', v_char1 = 'B';
		Character v_CharacterObj1 = new Character(v_char);
		Character v_CharacterObj2 = new Character(v_char1);
		char[] v_charArr = { 'A', 'B' };
		CharSequence v_charseq = new String("ABC");

		// --equals
		boolean v_equals = v_CharacterObj1.equals(o);
		// --hashCode
		int v_hashCode = v_CharacterObj1.hashCode();
		// --toString
		String v_toString = v_CharacterObj1.toString();
		// --charValue
		char v_charValue = v_CharacterObj1.charValue();
		// --compareTo
		int v_compareTo = v_CharacterObj1.compareTo(v_CharacterObj2);
		// --compareTo
		// int v_compareTo1 = v_CharacterObj1.compareTo(o);
		// --getClass
		java.lang.Class v_getClass = v_CharacterObj1.getClass();
		// --notify
		v_CharacterObj1.notify();
		// --notifyAll
		v_CharacterObj1.notifyAll();
		// --wait
		v_CharacterObj1.wait();// throws java.lang.InterruptedException;
		// --wait
		v_CharacterObj1.wait(v_long);// throws
		// java.lang.InterruptedException;
		// --wait
		v_CharacterObj1.wait(v_long, v_int);// throws
		// java.lang.InterruptedException;
		// --valueOf (static)
		java.lang.Character v_valueOf = Character.valueOf(v_char);
		// --toString (static)
		java.lang.String v_toString1 = Character.toString(v_char);
		// --isValidCodePoint (static)
		boolean v_isValidCodePoint = Character.isValidCodePoint(v_int);
		// --isSupplementaryCodePoint (static)
		boolean v_isSupplementaryCodePoint = Character.isSupplementaryCodePoint(v_int);
		// --isHighSurrogate (static)
		boolean v_isHighSurrogate = Character.isHighSurrogate(v_char);
		// --isLowSurrogate (static)
		boolean v_isLowSurrogate = Character.isLowSurrogate(v_char);
		// --isSurrogatePair (static)
		boolean v_isSurrogatePair = Character.isSurrogatePair(v_char, v_char1);
		// --charCount (static)
		int v_charCount = Character.charCount(v_int);
		// --toCodePoint (static)
		int v_toCodePoint = Character.toCodePoint(v_char, v_char1);
		// --codePointAt (static)
		int v_codePointAt = Character.codePointAt(v_charseq, v_int);
		// --codePointAt (static)
		int v_codePointAt1 = Character.codePointAt(v_charArr, v_int);
		// --codePointAt (static)
		int v_codePointAt2 = Character.codePointAt(v_charArr, v_int, v_int1);
		// --codePointBefore (static)
		int v_codePointBefore = Character.codePointBefore(v_charseq, v_int);
		// --codePointBefore (static)
		int v_codePointBefore1 = Character.codePointBefore(v_charArr, v_int);
		// --codePointBefore (static)
		int v_codePointBefore2 = Character.codePointBefore(v_charArr, v_int, v_int1);
		// --toChars (static)
		int v_toChars = Character.toChars(v_int, v_charArr, v_int);
		// --toChars (static)
		char[] v_toChars1 = Character.toChars(v_int);
		// --codePointCount (static)
		int v_codePointCount = Character.codePointCount(v_charseq, v_int, v_int1);
		// --codePointCount (static)
		int v_codePointCount1 = Character.codePointCount(v_charArr, v_int, v_int1);
		// --offsetByCodePoints (static)
		int v_offsetByCodePoints = Character.offsetByCodePoints(v_charseq, v_int, v_int1);
		// --offsetByCodePoints (static)
		int v_offsetByCodePoints1 = Character.offsetByCodePoints(v_charArr, v_int, v_int1, v_int, v_int1);
		// --isLowerCase (static)
		boolean v_isLowerCase = Character.isLowerCase(v_char);
		// --isLowerCase (static)
		boolean v_isLowerCase1 = Character.isLowerCase(v_int);
		// --isUpperCase (static)
		boolean v_isUpperCase = Character.isUpperCase(v_char);
		// --isUpperCase (static)
		boolean v_isUpperCase1 = Character.isUpperCase(v_int);
		// --isTitleCase (static)
		boolean v_isTitleCase = Character.isTitleCase(v_char);
		// --isTitleCase (static)
		boolean v_isTitleCase1 = Character.isTitleCase(v_int);
		// --isDigit (static)
		boolean v_isDigit = Character.isDigit(v_char);
		// --isDigit (static)
		boolean v_isDigit1 = Character.isDigit(v_int);
		// --isDefined (static)
		boolean v_isDefined = Character.isDefined(v_char);
		// --isDefined (static)
		boolean v_isDefined1 = Character.isDefined(v_int);
		// --isLetter (static)
		boolean v_isLetter = Character.isLetter(v_char);
		// --isLetter (static)
		boolean v_isLetter1 = Character.isLetter(v_int);
		// --isLetterOrDigit (static)
		boolean v_isLetterOrDigit = Character.isLetterOrDigit(v_char);
		// --isLetterOrDigit (static)
		boolean v_isLetterOrDigit1 = Character.isLetterOrDigit(v_int);
		// --isJavaLetter (static)
		boolean v_isJavaLetter = Character.isJavaLetter(v_char);
		// --isJavaLetterOrDigit (static)
		boolean v_isJavaLetterOrDigit = Character.isJavaLetterOrDigit(v_char);
		// --isJavaIdentifierStart (static)
		boolean v_isJavaIdentifierStart = Character.isJavaIdentifierStart(v_char);
		// --isJavaIdentifierStart (static)
		boolean v_isJavaIdentifierStart1 = Character.isJavaIdentifierStart(v_int);
		// --isJavaIdentifierPart (static)
		boolean v_isJavaIdentifierPart = Character.isJavaIdentifierPart(v_char);
		// --isJavaIdentifierPart (static)
		boolean v_isJavaIdentifierPart1 = Character.isJavaIdentifierPart(v_int);
		// --isUnicodeIdentifierStart (static)
		boolean v_isUnicodeIdentifierStart = Character.isUnicodeIdentifierStart(v_char);
		// --isUnicodeIdentifierStart (static)
		boolean v_isUnicodeIdentifierStart1 = Character.isUnicodeIdentifierStart(v_int);
		// --isUnicodeIdentifierPart (static)
		boolean v_isUnicodeIdentifierPart = Character.isUnicodeIdentifierPart(v_char);
		// --isUnicodeIdentifierPart (static)
		boolean v_isUnicodeIdentifierPart1 = Character.isUnicodeIdentifierPart(v_int);
		// --isIdentifierIgnorable (static)
		boolean v_isIdentifierIgnorable = Character.isIdentifierIgnorable(v_char);
		// --isIdentifierIgnorable (static)
		boolean v_isIdentifierIgnorable1 = Character.isIdentifierIgnorable(v_int);
		// --toLowerCase (static)
		char v_toLowerCase = Character.toLowerCase(v_char);
		// --toLowerCase (static)
		int v_toLowerCase1 = Character.toLowerCase(v_int);
		// --toUpperCase (static)
		char v_toUpperCase = Character.toUpperCase(v_char);
		// --toUpperCase (static)
		int v_toUpperCase1 = Character.toUpperCase(v_int);
		// --toTitleCase (static)
		char v_toTitleCase = Character.toTitleCase(v_char);
		// --toTitleCase (static)
		int v_toTitleCase1 = Character.toTitleCase(v_int);
		// --digit (static)
		int v_digit = Character.digit(v_char, v_int);
		// --digit (static)
		int v_digit1 = Character.digit(v_int, v_int1);
		// --getNumericValue (static)
		int v_getNumericValue = Character.getNumericValue(v_char);
		// --getNumericValue (static)
		int v_getNumericValue1 = Character.getNumericValue(v_int);
		// --isSpace (static)
		boolean v_isSpace = Character.isSpace(v_char);
		// --isSpaceChar (static)
		boolean v_isSpaceChar = Character.isSpaceChar(v_char);
		// --isSpaceChar (static)
		boolean v_isSpaceChar1 = Character.isSpaceChar(v_int);
		// --isWhitespace (static)
		boolean v_isWhitespace = Character.isWhitespace(v_char);
		// --isWhitespace (static)
		boolean v_isWhitespace1 = Character.isWhitespace(v_int);
		// --isISOControl (static)
		boolean v_isISOControl = Character.isISOControl(v_char);
		// --isISOControl (static)
		boolean v_isISOControl1 = Character.isISOControl(v_int);
		// --getType (static)
		int v_getType = Character.getType(v_char);
		// --getType (static)
		int v_getType1 = Character.getType(v_int);
		// --forDigit (static)
		char v_forDigit = Character.forDigit(v_int, v_int1);
		// --getDirectionality (static)
		byte v_getDirectionality = Character.getDirectionality(v_char);
		// --getDirectionality (static)
		byte v_getDirectionality1 = Character.getDirectionality(v_int);
		// --isMirrored (static)
		boolean v_isMirrored = Character.isMirrored(v_char);
		// --isMirrored (static)
		boolean v_isMirrored1 = Character.isMirrored(v_int);
		// --reverseBytes (static)
		char v_reverseBytes = Character.reverseBytes(v_char);
	}

}
