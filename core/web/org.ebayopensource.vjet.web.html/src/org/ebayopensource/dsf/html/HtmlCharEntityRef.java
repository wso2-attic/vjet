/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html;

import org.ebayopensource.dsf.dom.DRawString;

/**
 * See http://www.w3.org/TR/html4/sgml/entities.html#iso-88591
 * <BR>
 * Use the asRaw() method to get a DRawString() instance back with a the
 * value of this instance.  
 * <BR>
 * Example: HtmlCharEntityRef.euro.asRaw()
 */
public enum HtmlCharEntityRef {

	/** no-break space = non-breaking space,U+00A0 ISOnum */
	nbsp   ("&#160;"), 
	/** inverted exclamation mark, U+00A1 ISOnum */
	iexcl  ("&#161;"), 
	/** cent sign, U+00A2 ISOnum */
	cent   ("&#162;"), 
	/** pound sign, U+00A3 ISOnum */
	pound  ("&#163;"), 
	/** currency sign, U+00A4 ISOnum */
	curren ("&#164;"), 
	/** yen sign = yuan sign, U+00A5 ISOnum */
	yen    ("&#165;"), 
	/** broken bar = broken vertical bar, U+00A6 ISOnum */
	brvbar ("&#166;"), 
	/** section sign, U+00A7 ISOnum */
	sect   ("&#167;"), 
	/** diaeresis = spacing diaeresis, U+00A8 ISOdia */
	uml    ("&#168;"), 
	/** copyright sign, U+00A9 ISOnum */
	copy   ("&#169;"), 
	/** feminine ordinal indicator, U+00AA ISOnum */
	ordf   ("&#170;"), 
	/** left-pointing double angle quotation mark = left pointing guillemet, U+00AB ISOnum */
	laquo  ("&#171;"), 
	/** not sign, U+00AC ISOnum */
	not    ("&#172;"), 
	/** soft hyphen = discretionary hyphen, U+00AD ISOnum */
	shy    ("&#173;"), 
	/** registered sign = registered trade mark sign, U+00AE ISOnum */
	reg    ("&#174;"), 
	/** macron = spacing macron = overline = APL overbar, U+00AF ISOdia */
	macr   ("&#175;"), 
	/** degree sign, U+00B0 ISOnum */
	deg    ("&#176;"), 
	/** plus-minus sign = plus-or-minus sign, U+00B1 ISOnum */
	plusmn ("&#177;"), 
	/** superscript two = superscript digit two = squared, U+00B2 ISOnum */
	sup2   ("&#178;"), 
	/** superscript three = superscript digit three = cubed, U+00B3 ISOnum */
	sup3   ("&#179;"), 
	/** acute accent = spacing acute, U+00B4 ISOdia */
	acute  ("&#180;"), 
	/** micro sign, U+00B5 ISOnum */
	micro  ("&#181;"), 
	/** pilcrow sign = paragraph sign, U+00B6 ISOnum */
	para   ("&#182;"), 
	/** middle dot = Georgian comma = Greek middle dot, U+00B7 ISOnum */
	middot ("&#183;"), 
	/** cedilla = spacing cedilla, U+00B8 ISOdia */
	cedil  ("&#184;"), 
	/** superscript one = superscript digit one, U+00B9 ISOnum */
	sup1   ("&#185;"), 
	/** masculine ordinal indicator, U+00BA ISOnum */
	ordm   ("&#186;"), 
	/** right-pointing double angle quotation mark = right pointing guillemet, U+00BB ISOnum */
	raquo  ("&#187;"), 
	/** vulgar fraction one quarter = fraction one quarter, U+00BC ISOnum */
	frac14 ("&#188;"), 
	/** vulgar fraction one half = fraction one half, U+00BD ISOnum */
	frac12 ("&#189;"), 
	/** vulgar fraction three quarters = fraction three quarters, U+00BE ISOnum */
	frac34 ("&#190;"), 
	/** inverted question mark = turned question mark, U+00BF ISOnum */
	iquest ("&#191;"), 
	/** latin capital letter A with grave= latin capital letter A grave,U+00C0 ISOlat1 */
	Agrave ("&#192;"), 
	/** latin capital letter A with acute,U+00C1 ISOlat1 */
	Aacute ("&#193;"), 
	/** latin capital letter A with circumflex,U+00C2 ISOlat1 */
	Acirc  ("&#194;"), 
	/** latin capital letter A with tilde,U+00C3 ISOlat1 */
	Atilde ("&#195;"), 
	/** latin capital letter A with diaeresis,U+00C4 ISOlat1 */
	Auml   ("&#196;"), 
	/** latin capital letter A with ring above= latin capital letter A ring,U+00C5 ISOlat1 */
	Aring  ("&#197;"), 
	/** latin capital letter AE= latin capital ligature AE,U+00C6 ISOlat1 */
	AElig  ("&#198;"), 
	/** latin capital letter C with cedilla,U+00C7 ISOlat1 */
	Ccedil ("&#199;"), 
	/** latin capital letter E with grave,U+00C8 ISOlat1 */
	Egrave ("&#200;"), 
	/** latin capital letter E with acute,U+00C9 ISOlat1 */
	Eacute ("&#201;"), 
	/** latin capital letter E with circumflex,U+00CA ISOlat1 */
	Ecirc  ("&#202;"), 
	/** latin capital letter E with diaeresis,U+00CB ISOlat1 */
	Euml   ("&#203;"), 
	/** latin capital letter I with grave,U+00CC ISOlat1 */
	Igrave ("&#204;"), 
	/** latin capital letter I with acute,U+00CD ISOlat1 */
	Iacute ("&#205;"), 
	/** latin capital letter I with circumflex,U+00CE ISOlat1 */
	Icirc  ("&#206;"), 
	/** latin capital letter I with diaeresis,U+00CF ISOlat1 */
	Iuml   ("&#207;"), 
	/** latin capital letter ETH, U+00D0 ISOlat1 */
	ETH    ("&#208;"), 
	/** latin capital letter N with tilde,U+00D1 ISOlat1 */
	Ntilde ("&#209;"), 
	/** latin capital letter O with grave,U+00D2 ISOlat1 */
	Ograve ("&#210;"), 
	/** latin capital letter O with acute,U+00D3 ISOlat1 */
	Oacute ("&#211;"), 
	/** latin capital letter O with circumflex,U+00D4 ISOlat1 */
	Ocirc  ("&#212;"), 
	/** latin capital letter O with tilde,U+00D5 ISOlat1 */
	Otilde ("&#213;"), 
	/** latin capital letter O with diaeresis,U+00D6 ISOlat1 */
	Ouml   ("&#214;"), 
	/** multiplication sign, U+00D7 ISOnum */
	times  ("&#215;"), 
	/** latin capital letter O with stroke= latin capital letter O slash,U+00D8 ISOlat1 */
	Oslash ("&#216;"), 
	/** latin capital letter U with grave,U+00D9 ISOlat1 */
	Ugrave ("&#217;"), 
	/** latin capital letter U with acute,U+00DA ISOlat1 */
	Uacute ("&#218;"), 
	/** latin capital letter U with circumflex,U+00DB ISOlat1 */
	Ucirc  ("&#219;"), 
	/** latin capital letter U with diaeresis,U+00DC ISOlat1 */
	Uuml   ("&#220;"), 
	/** latin capital letter Y with acute,U+00DD ISOlat1 */
	Yacute ("&#221;"), 
	/** latin capital letter THORN,U+00DE ISOlat1 */
	THORN  ("&#222;"), 
	/** latin small letter sharp s = ess-zed,U+00DF ISOlat1 */
	szlig  ("&#223;"), 
	/** latin small letter a with grave= latin small letter a grave,U+00E0 ISOlat1 */
	agrave ("&#224;"), 
	/** latin small letter a with acute,U+00E1 ISOlat1 */
	aacute ("&#225;"), 
	/** latin small letter a with circumflex,U+00E2 ISOlat1 */
	acirc  ("&#226;"), 
	/** latin small letter a with tilde,U+00E3 ISOlat1 */
	atilde ("&#227;"), 
	/** latin small letter a with diaeresis,U+00E4 ISOlat1 */
	auml   ("&#228;"), 
	/** latin small letter a with ring above= latin small letter a ring,U+00E5 ISOlat1 */
	aring  ("&#229;"), 
	/** latin small letter ae= latin small ligature ae, U+00E6 ISOlat1 */
	aelig  ("&#230;"), 
	/** latin small letter c with cedilla,U+00E7 ISOlat1 */
	ccedil ("&#231;"), 
	/** latin small letter e with grave,U+00E8 ISOlat1 */
	egrave ("&#232;"), 
	/** latin small letter e with acute,U+00E9 ISOlat1 */
	eacute ("&#233;"), 
	/** latin small letter e with circumflex,U+00EA ISOlat1 */
	ecirc  ("&#234;"), 
	/** latin small letter e with diaeresis,U+00EB ISOlat1 */
	euml   ("&#235;"), 
	/** latin small letter i with grave,U+00EC ISOlat1 */
	igrave ("&#236;"), 
	/** latin small letter i with acute,U+00ED ISOlat1 */
	iacute ("&#237;"), 
	/** latin small letter i with circumflex,U+00EE ISOlat1 */
	icirc  ("&#238;"), 
	/** latin small letter i with diaeresis,U+00EF ISOlat1 */
	iuml   ("&#239;"), 
	/** latin small letter eth, U+00F0 ISOlat1 */
	eth    ("&#240;"), 
	/** latin small letter n with tilde,U+00F1 ISOlat1 */
	ntilde ("&#241;"), 
	/** latin small letter o with grave,U+00F2 ISOlat1 */
	ograve ("&#242;"), 
	/** latin small letter o with acute,U+00F3 ISOlat1 */
	oacute ("&#243;"), 
	/** latin small letter o with circumflex,U+00F4 ISOlat1 */
	ocirc  ("&#244;"), 
	/** latin small letter o with tilde,U+00F5 ISOlat1 */
	otilde ("&#245;"), 
	/** latin small letter o with diaeresis,U+00F6 ISOlat1 */
	ouml   ("&#246;"), 
	/** division sign, U+00F7 ISOnum */
	divide ("&#247;"), 
	/** latin small letter o with stroke,= latin small letter o slash,U+00F8 ISOlat1 */
	oslash ("&#248;"), 
	/** latin small letter u with grave,U+00F9 ISOlat1 */
	ugrave ("&#249;"), 
	/** latin small letter u with acute,U+00FA ISOlat1 */
	uacute ("&#250;"), 
	/** latin small letter u with circumflex,U+00FB ISOlat1 */
	ucirc  ("&#251;"), 
	/** latin small letter u with diaeresis,U+00FC ISOlat1 */
	uuml   ("&#252;"), 
	/** latin small letter y with acute,U+00FD ISOlat1 */
	yacute ("&#253;"), 
	/** latin small letter thorn,U+00FE ISOlat1 */
	thorn  ("&#254;"), 
	/** latin small letter y with diaeresis,U+00FF ISOlat1 */
	yuml   ("&#255;"), 
	
	/* Mathematical, Greek and Symbolic characters for HTML */
	
	// <!/** Latin Extended-B */
	/** latin small f with hook = function = florin, U+0192 ISOtech */
	fnof     ("&#402;"), 
	
	// <!/** Greek */
	/** greek capital letter alpha, U+0391 */
	Alpha    ("&#913;"), 
	/** greek capital letter beta, U+0392 */
	Beta     ("&#914;"), 
	/** greek capital letter gamma, U+0393 ISOgrk3 */
	Gamma    ("&#915;"), 
	/** greek capital letter delta, U+0394 ISOgrk3 */
	Delta    ("&#916;"), 
	/** greek capital letter epsilon, U+0395 */
	Epsilon  ("&#917;"), 
	/** greek capital letter zeta, U+0396 */
	Zeta     ("&#918;"), 
	/** greek capital letter eta, U+0397 */
	Eta      ("&#919;"), 
	/** greek capital letter theta, U+0398 ISOgrk3 */
	Theta    ("&#920;"), 
	/** greek capital letter iota, U+0399 */
	Iota     ("&#921;"), 
	/** greek capital letter kappa, U+039A */
	Kappa    ("&#922;"), 
	/** greek capital letter lambda, U+039B ISOgrk3 */
	Lambda   ("&#923;"), 
	/** greek capital letter mu, U+039C */
	Mu       ("&#924;"), 
	/** greek capital letter nu, U+039D */
	Nu       ("&#925;"), 
	/** greek capital letter xi, U+039E ISOgrk3 */
	Xi       ("&#926;"), 
	/** greek capital letter omicron, U+039F */
	Omicron  ("&#927;"), 
	/** greek capital letter pi, U+03A0 ISOgrk3 */
	Pi       ("&#928;"), 
	/** greek capital letter rho, U+03A1 */
	Rho      ("&#929;"), 
	// <!/** there is no Sigmaf, and no U+03A2 character either */
	/** greek capital letter sigma, U+03A3 ISOgrk3 */
	Sigma    ("&#931;"), 
	/** greek capital letter tau, U+03A4 */
	Tau      ("&#932;"), 
	/** greek capital letter upsilon, U+03A5 ISOgrk3 */
	Upsilon  ("&#933;"), 
	/** greek capital letter phi, U+03A6 ISOgrk3 */
	Phi      ("&#934;"), 
	/** greek capital letter chi, U+03A7 */
	Chi      ("&#935;"), 
	/** greek capital letter psi, U+03A8 ISOgrk3 */
	Psi      ("&#936;"), 
	/** greek capital letter omega, U+03A9 ISOgrk3 */
	Omega    ("&#937;"), 
	
	/** greek small letter alpha, U+03B1 ISOgrk3 */
	alpha    ("&#945;"), 
	/** greek small letter beta, U+03B2 ISOgrk3 */
	beta     ("&#946;"), 
	/** greek small letter gamma, U+03B3 ISOgrk3 */
	gamma    ("&#947;"), 
	/** greek small letter delta, U+03B4 ISOgrk3 */
	delta    ("&#948;"), 
	/** greek small letter epsilon, U+03B5 ISOgrk3 */
	epsilon  ("&#949;"), 
	/** greek small letter zeta, U+03B6 ISOgrk3 */
	zeta     ("&#950;"), 
	/** greek small letter eta, U+03B7 ISOgrk3 */
	eta      ("&#951;"), 
	/** greek small letter theta, U+03B8 ISOgrk3 */
	theta    ("&#952;"), 
	/** greek small letter iota, U+03B9 ISOgrk3 */
	iota     ("&#953;"), 
	/** greek small letter kappa, U+03BA ISOgrk3 */
	kappa    ("&#954;"), 
	/** greek small letter lambda, U+03BB ISOgrk3 */
	lambda   ("&#955;"), 
	/** greek small letter mu, U+03BC ISOgrk3 */
	mu       ("&#956;"), 
	/** greek small letter nu, U+03BD ISOgrk3 */
	nu       ("&#957;"), 
	/** greek small letter xi, U+03BE ISOgrk3 */
	xi       ("&#958;"), 
	/** greek small letter omicron, U+03BF NEW */
	omicron  ("&#959;"), 
	/** greek small letter pi, U+03C0 ISOgrk3 */
	pi       ("&#960;"), 
	/** greek small letter rho, U+03C1 ISOgrk3 */
	rho      ("&#961;"), 
	/** greek small letter final sigma, U+03C2 ISOgrk3 */
	sigmaf   ("&#962;"), 
	/** greek small letter sigma, U+03C3 ISOgrk3 */
	sigma    ("&#963;"), 
	/** greek small letter tau, U+03C4 ISOgrk3 */
	tau      ("&#964;"), 
	/** greek small letter upsilon, U+03C5 ISOgrk3 */
	upsilon  ("&#965;"), 
	/** greek small letter phi, U+03C6 ISOgrk3 */
	phi      ("&#966;"), 
	/** greek small letter chi, U+03C7 ISOgrk3 */
	chi      ("&#967;"), 
	/** greek small letter psi, U+03C8 ISOgrk3 */
	psi      ("&#968;"), 
	/** greek small letter omega, U+03C9 ISOgrk3 */
	omega    ("&#969;"), 
	/** greek small letter theta symbol, U+03D1 NEW */
	thetasym ("&#977;"), 
	/** greek upsilon with hook symbol, U+03D2 NEW */
	upsih    ("&#978;"), 
	/** greek pi symbol, U+03D6 ISOgrk3 */
	piv      ("&#982;"), 
	
	// <!/** General Punctuation */
	/** bullet = black small circle, U+2022 ISOpub  */
	bull     ("&#8226;"), 
	// <!/** bullet is NOT the same as bullet operator, U+2219 */
	/** horizontal ellipsis = three dot leader, U+2026 ISOpub  */
	hellip   ("&#8230;"), 
	/** prime = minutes = feet, U+2032 ISOtech */
	prime    ("&#8242;"), 
	/** double prime = seconds = inches, U+2033 ISOtech */
	Prime    ("&#8243;"), 
	/** overline = spacing overscore, U+203E NEW */
	oline    ("&#8254;"), 
	/** fraction slash, U+2044 NEW */
	frasl    ("&#8260;"), 
	
	// <!/** Letterlike Symbols */
	/** script capital P = power set = Weierstrass p, U+2118 ISOamso */
	weierp   ("&#8472;"), 
	/** blackletter capital I = imaginary part, U+2111 ISOamso */
	image    ("&#8465;"), 
	/** blackletter capital R = real part symbol, U+211C ISOamso */
	real     ("&#8476;"), 
	/** trade mark sign, U+2122 ISOnum */
	trade    ("&#8482;"), 
	/** alef symbol = first transfinite cardinal, U+2135 NEW */
	alefsym  ("&#8501;"), 
	// <!/** alef symbol is NOT the same as hebrew letter alef, U+05D0 although the same glyph could be used to depict both characters */
	
	// <!/** Arrows */
	/** leftwards arrow, U+2190 ISOnum */
	larr     ("&#8592;"), 
	/** upwards arrow, U+2191 ISOnum */
	uarr     ("&#8593;"), 
	/** rightwards arrow, U+2192 ISOnum */
	rarr     ("&#8594;"), 
	/** downwards arrow, U+2193 ISOnum */
	darr     ("&#8595;"), 
	/** left right arrow, U+2194 ISOamsa */
	harr     ("&#8596;"), 
	/** downwards arrow with corner leftwards = carriage return, U+21B5 NEW */
	crarr    ("&#8629;"), 
	/** leftwards double arrow, U+21D0 ISOtech */
	lArr     ("&#8656;"), 
	// <!/** ISO 10646 does not say that lArr is the same as the 'is implied by' arrow but also does not have any other character for that function. So ? lArr can be used for 'is implied by' as ISOtech suggests */
	/** rightwards double arrow, U+21D2 ISOtech */
	uArr     ("&#8657;"), 
	rArr     ("&#8658;"), 
	// <!/** ISO 10646 does not say this is the 'implies' character but does not have  another character with this function so ? rArr can be used for 'implies' as ISOtech suggests */
	/** downwards double arrow, U+21D3 ISOamsa */
	dArr     ("&#8659;"), 
	/** left right double arrow, U+21D4 ISOamsa */
	hArr     ("&#8660;"), 
	
	// <!/** Mathematical Operators */
	/** for all, U+2200 ISOtech */
	forall   ("&#8704;"), 
	/** partial differential, U+2202 ISOtech  */
	part     ("&#8706;"), 
	/** there exists, U+2203 ISOtech */
	exist    ("&#8707;"), 
	/** empty set = null set = diameter, U+2205 ISOamso */
	empty    ("&#8709;"), 
	/** nabla = backward difference, U+2207 ISOtech */
	nabla    ("&#8711;"), 
	/** element of, U+2208 ISOtech */
	isin     ("&#8712;"), 
	/** not an element of, U+2209 ISOtech */
	notin    ("&#8713;"), 
	/** contains as member, U+220B ISOtech */
//	 <!/** should there be a more memorable name than 'ni'? */
	ni       ("&#8715;"), 
	
	/** n-ary product = product sign, U+220F ISOamsb */
	prod     ("&#8719;"), 
	/** n-ary sumation, U+2211 ISOamsb */
	// <!/** prod is NOT the same character as U+03A0 'greek capital letter pi' though the same glyph might be used for both */
	sum      ("&#8721;"), 
	// <!/** sum is NOT the same character as U+03A3 'greek capital letter sigma' though the same glyph might be used for both */
	/** minus sign, U+2212 ISOtech */
	minus    ("&#8722;"), 
	/** asterisk operator, U+2217 ISOtech */
	lowast   ("&#8727;"), 
	/** square root = radical sign, U+221A ISOtech */
	radic    ("&#8730;"), 
	/** proportional to, U+221D ISOtech */
	prop     ("&#8733;"), 
	/** infinity, U+221E ISOtech */
	infin    ("&#8734;"), 
	/** angle, U+2220 ISOamso */
	ang      ("&#8736;"), 
	/** logical and = wedge, U+2227 ISOtech */
	and      ("&#8743;"), 
	/** logical or = vee, U+2228 ISOtech */
	or       ("&#8744;"), 
	/** intersection = cap, U+2229 ISOtech */
	cap      ("&#8745;"), 
	/** union = cup, U+222A ISOtech */
	cup      ("&#8746;"), 
	/** integral, U+222B ISOtech - actual is int but this collides with Java name space */
	integral      ("&#8747;"), 
	/** therefore, U+2234 ISOtech */
	there4   ("&#8756;"), 
	/** tilde operator = varies with = similar to, U+223C ISOtech */
	sim      ("&#8764;"), 
	// <!/** tilde operator is NOT the same character as the tilde, U+007E, although the same glyph might be used to represent both  */
	/** approximately equal to, U+2245 ISOtech */
	cong     ("&#8773;"), 
	/** almost equal to = asymptotic to, U+2248 ISOamsr */
	asymp    ("&#8776;"), 
	/** not equal to, U+2260 ISOtech */
	ne       ("&#8800;"), 
	/** identical to, U+2261 ISOtech */
	equiv    ("&#8801;"), 
	/** less-than or equal to, U+2264 ISOtech */
	le       ("&#8804;"), 
	/** greater-than or equal to, U+2265 ISOtech */
	ge       ("&#8805;"), 
	/** subset of, U+2282 ISOtech */
	sub      ("&#8834;"), 
	/** superset of, U+2283 ISOtech */
	sup      ("&#8835;"), 
	// <!/** note that nsup, 'not a superset of, U+2283' is not covered by the Symbol  font encoding and is not included. Should it be, for symmetry? It is in ISOamsn  */ 
	/** not a subset of, U+2284 ISOamsn */
	nsub     ("&#8836;"), 
	/** subset of or equal to, U+2286 ISOtech */
	sube     ("&#8838;"), 
	/** superset of or equal to, U+2287 ISOtech */
	supe     ("&#8839;"), 
	/** circled plus = direct sum, U+2295 ISOamsb */
	oplus    ("&#8853;"), 
	/** circled times = vector product, U+2297 ISOamsb */
	otimes   ("&#8855;"), 
	/** up tack = orthogonal to = perpendicular, U+22A5 ISOtech */
	perp     ("&#8869;"), 
	/** dot operator, U+22C5 ISOamsb */
	sdot     ("&#8901;"), 
	// <!/** dot operator is NOT the same character as U+00B7 middle dot */
	
	// <!/** Miscellaneous Technical */
	/** left ceiling = apl upstile, U+2308 ISOamsc  */
	lceil    ("&#8968;"), 
	/** right ceiling, U+2309 ISOamsc  */
	rceil    ("&#8969;"), 
	/** left floor = apl downstile, U+230A ISOamsc  */
	lfloor   ("&#8970;"), 
	/** right floor, U+230B ISOamsc  */
	rfloor   ("&#8971;"), 
	/** left-pointing angle bracket = bra, U+2329 ISOtech */
	lang     ("&#9001;"), 
	// <!/** lang is NOT the same character as U+003C 'less than' or U+2039 'single left-pointing angle quotation mark' */
	/** right-pointing angle bracket = ket, U+232A ISOtech */
	rang     ("&#9002;"), 
	// <!/** rang is NOT the same character as U+003E 'greater than'  or U+203A 'single right-pointing angle quotation mark' */
	
	// <!/** Geometric Shapes */
	/** lozenge, U+25CA ISOpub */
	loz      ("&#9674;"), 
	
	// <!/** Miscellaneous Symbols */
	/** black spade suit, U+2660 ISOpub */
	spades   ("&#9824;"), 
	// <!/** black here seems to mean filled as opposed to hollow */
	/** black club suit = shamrock, U+2663 ISOpub */
	clubs    ("&#9827;"), 
	/** black heart suit = valentine, U+2665 ISOpub */
	hearts   ("&#9829;"), 
	/** black diamond suit, U+2666 ISOpub */
	diams    ("&#9830;"), 
	
	/* Special characters for HTML */
	
	//<!/** C0 Controls and Basic Latin */
	/** quotation mark = APL quote, U+0022 ISOnum */
	quot    ("&#34;"), 
	/** ampersand, U+0026 ISOnum */
	amp     ("&#38;"),  
	/** less-than sign, U+003C ISOnum */
	lt      ("&#60;"),  
	/** greater-than sign, U+003E ISOnum */
	gt      ("&#62;"),  
	
	//<!/** Latin Extended-A */
	/** latin capital ligature OE, U+0152 ISOlat2 */
	OElig   ("&#338;"), 
	/** latin small ligature oe, U+0153 ISOlat2 */
	oelig   ("&#339;"), 
	//<!/** ligature is a misnomer, this is a separate character in some languages */
	/** latin capital letter S with caron, U+0160 ISOlat2 */
	Scaron  ("&#352;"), 
	/** latin small letter s with caron, U+0161 ISOlat2 */
	scaron  ("&#353;"), 
	/** latin capital letter Y with diaeresis, U+0178 ISOlat2 */
	Yuml    ("&#376;"), 
	
	//<!/** Spacing Modifier Letters */
	/** modifier letter circumflex accent, U+02C6 ISOpub */
	circ    ("&#710;"), 
	/** small tilde, U+02DC ISOdia */
	tilde   ("&#732;"), 
	
	//<!/** General Punctuation */
	/** en space, U+2002 ISOpub */
	ensp    ("&#8194;"), 
	/** em space, U+2003 ISOpub */
	emsp    ("&#8195;"), 
	/** thin space, U+2009 ISOpub */
	thinsp  ("&#8201;"), 
	/** zero width non-joiner, U+200C NEW RFC 2070 */
	zwnj    ("&#8204;"), 
	/** zero width joiner, U+200D NEW RFC 2070 */
	zwj     ("&#8205;"), 
	/** left-to-right mark, U+200E NEW RFC 2070 */
	lrm     ("&#8206;"), 
	/** right-to-left mark, U+200F NEW RFC 2070 */
	rlm     ("&#8207;"), 
	/** en dash, U+2013 ISOpub */
	ndash   ("&#8211;"), 
	/** em dash, U+2014 ISOpub */
	mdash   ("&#8212;"), 
	/** left single quotation mark, U+2018 ISOnum */
	lsquo   ("&#8216;"), 
	/** right single quotation mark, U+2019 ISOnum */
	rsquo   ("&#8217;"), 
	/** single low-9 quotation mark, U+201A NEW */
	sbquo   ("&#8218;"), 
	/** left double quotation mark, U+201C ISOnum */
	ldquo   ("&#8220;"), 
	/** right double quotation mark, U+201D ISOnum */
	rdquo   ("&#8221;"), 
	/** double low-9 quotation mark, U+201E NEW */
	bdquo   ("&#8222;"), 
	/** dagger, U+2020 ISOpub */
	dagger  ("&#8224;"), 
	/** double dagger, U+2021 ISOpub */
	Dagger  ("&#8225;"), 
	/** per mille sign, U+2030 ISOtech */
	permil  ("&#8240;"), 
	/** single left-pointing angle quotation mark, U+2039 ISO proposed */
	lsaquo  ("&#8249;"), 
	//<!/** lsaquo is proposed but not yet ISO standardized */
	/** single right-pointing angle quotation mark, U+203A ISO proposed */
	rsaquo  ("&#8250;"), 
	//<!/** rsaquo is proposed but not yet ISO standardized */
	/** euro sign, U+20AC NEW */
	euro   ("&#8364;")  ; 
	
	public final String value ;
	
	/** Answer the entity ref value for this enum.  The format is &#xxxx; */
	HtmlCharEntityRef(String _value) {
		value = _value ;
	}
	
	/** Answer the entity ref value for this enum being concattenated count times */
	public String repeated(final int count) {
		if (count <= 0) return "" ;
		final StringBuffer b = new StringBuffer(count * 7) ;
		for(int i=0; i < count; i++) {
			b.append(value) ;
		}
		return b.toString();
	}
	
	/** Answer a DRawString() instance with entity ref value for this enum */
	public DRawString asRaw() { 
		return new DRawString(value) ; 
	}
	
	/** Answer a DRawString() instance with entity ref value for this enum
	 * concatenated count times
	 */
	public DRawString asRaw(final int count) { 
		return new DRawString(repeated(count)) ; 
	}
}
