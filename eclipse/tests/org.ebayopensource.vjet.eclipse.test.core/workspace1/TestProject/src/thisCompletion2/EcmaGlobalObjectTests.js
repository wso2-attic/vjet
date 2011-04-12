/* -*- Mode: C++; tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*- */
/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Mozilla Communicator client code, released
 * March 31, 1998.
 *
 * The Initial Developer of the Original Code is
 * Netscape Communications Corporation.
 * Portions created by the Initial Developer are Copyright (C) 1998
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either the GNU General Public License Version 2 or later (the "GPL"), or
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 *
 * ***** END LICENSE BLOCK ***** */

vjo.ctype("thisCompletion2.EcmaGlobalObjectTests")
.inherits("com.ebay.dsf.jslang.feature.tests.BaseTest")
.protos({

/**
   File Name:          15.1-1-n.js
   ECMA Section:       The global object
   Description:

   The global object does not have a [[Construct]] property; it is not
   possible to use the global object as a constructor with the new operator.


   Author:             christine@netscape.com
   Date:               12 november 1997
*/
test_15_1__1__n:function(){


var SECTION = "15.1-1-n";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "The Global Object";

// writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var MY_GLOBAL = new this()";
EXPECTED = "error";

this.TestCase(   SECTION,
		"var MY_GLOBAL = new this()",
		"error",
		eval("var MY_GLOBAL = new this()") );

// test();


},

/**
   File Name:          15.1-2-n.js
   ECMA Section:       The global object
   Description:

   The global object does not have a [[Call]] property; it is not possible
   to invoke the global object as a function.

   Author:             christine@netscape.com
   Date:               12 november 1997
*/
test_15_1__2__n:function(){


var SECTION = "15.1-2-n";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "The Global Object";

// writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var MY_GLOBAL = this()";
EXPECTED = "error";

this.TestCase(   SECTION,
		"var MY_GLOBAL = this()",
		"error",
		eval("var MY_GLOBAL = this()") );
// test();

},

/**
   File Name:          15.1.1.1.js
   ECMA Section:       15.1.1.1 NaN

   Description:        The initial value of NaN is NaN.

   Author:             christine@netscape.com
   Date:               28 october 1997

*/
test_15_1_1_1:function(){

var SECTION = "15.1.1.1";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "NaN";

// writeHeaderToLog( SECTION + " "+ TITLE);


this.TestCase( SECTION, "NaN",               Number.NaN,     NaN );
this.TestCase( SECTION, "this.NaN",          Number.NaN,     this.NaN );
this.TestCase( SECTION, "typeof NaN",        "number",       typeof NaN );

// test();

},

/**
   File Name:          15.1.1.2.js
   ECMA Section:       15.1.1.2 Infinity

   Description:        The initial value of Infinity is +Infinity.

   Author:             christine@netscape.com
   Date:               28 october 1997

*/
test_15_1_1_2:function(){

var SECTION = "15.1.1.2";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "Infinity";

// writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION, "Infinity",               Number.POSITIVE_INFINITY,      Infinity );
this.TestCase( SECTION, "this.Infinity",          Number.POSITIVE_INFINITY,      this.Infinity );
this.TestCase( SECTION, "typeof Infinity",        "number",                      typeof Infinity );

// test();

},

/**
   File Name:          15.1.2.1-2.js
   ECMA Section:       15.1.2.1 eval(x)

   Parse x as an ECMAScript Program.  If the parse fails,
   generate a runtime error.  Evaluate the program.  If
   result is "Normal completion after value V", return
   the value V.  Else, return undefined.
   Description:
   Author:             christine@netscape.com
   Date:               16 september 1997
*/
test_15_1_2_1__2:function(){

var SECTION = "15.1.2.1-2";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "eval(x)";
var BUGNUMBER = "none";

// writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase(    SECTION,
		 "d = new Date(0); with (d) { x = getUTCMonth() +'/'+ getUTCDate() +'/'+ getUTCFullYear(); } x",
		 "0/1/1970",
		 eval( "d = new Date(0); with (d) { x = getUTCMonth() +'/'+ getUTCDate() +'/'+ getUTCFullYear(); } x" ));

// test();

},

/**
   File Name:          15.1.2.2-1.js
   ECMA Section:       15.1.2.2 Function properties of the global object
   parseInt( string, radix )

   Description:

   The parseInt function produces an integer value dictated by intepretation
   of the contents of the string argument according to the specified radix.

   When the parseInt function is called, the following steps are taken:

   1.   Call ToString(string).
   2.   Compute a substring of Result(1) consisting of the leftmost character
   that is not a StrWhiteSpaceChar and all characters to the right of
   that character. (In other words, remove leading whitespace.)
   3.   Let sign be 1.
   4.   If Result(2) is not empty and the first character of Result(2) is a
   minus sign -, let sign be -1.
   5.   If Result(2) is not empty and the first character of Result(2) is a
   plus sign + or a minus sign -, then Result(5) is the substring of
   Result(2) produced by removing the first character; otherwise, Result(5)
   is Result(2).
   6.   If the radix argument is not supplied, go to step 12.
   7.   Call ToInt32(radix).
   8.   If Result(7) is zero, go to step 12; otherwise, if Result(7) < 2 or
   Result(7) > 36, return NaN.
   9.   Let R be Result(7).
   10.   If R = 16 and the length of Result(5) is at least 2 and the first two
   characters of Result(5) are either "0x" or "0X", let S be the substring
   of Result(5) consisting of all but the first two characters; otherwise,
   let S be Result(5).
   11.   Go to step 22.
   12.   If Result(5) is empty or the first character of Result(5) is not 0,
   go to step 20.
   13.   If the length of Result(5) is at least 2 and the second character of
   Result(5) is x or X, go to step 17.
   14.   Let R be 8.
   15.   Let S be Result(5).
   16.   Go to step 22.
   17.   Let R be 16.
   18.   Let S be the substring of Result(5) consisting of all but the first
   two characters.
   19.   Go to step 22.
   20.   Let R be 10.
   21.   Let S be Result(5).
   22.   If S contains any character that is not a radix-R digit, then let Z be
   the substring of S consisting of all characters to the left of the
   leftmost such character; otherwise, let Z be S.
   23.   If Z is empty, return NaN.
   24.   Compute the mathematical integer value that is represented by Z in
   radix-R notation. (But if R is 10 and Z contains more than 20
   significant digits, every digit after the 20th may be replaced by a 0
   digit, at the option of the implementation; and if R is not 2, 4, 8,
   10, 16, or 32, then Result(24) may be an implementation-dependent
   approximation to the mathematical integer value that is represented
   by Z in radix-R notation.)
   25.   Compute the number value for Result(24).
   26.   Return sign Result(25).

   Note that parseInt may interpret only a leading portion of the string as
   an integer value; it ignores any characters that cannot be interpreted as
   part of the notation of an integer, and no indication is given that any
   such characters were ignored.

   Author:             christine@netscape.com
   Date:               28 october 1997

*/
test_15_1_2_2__1:function(){

var SECTION = "15.1.2.2-1";
var VERSION = "ECMA_1";
var TITLE   = "parseInt(string, radix)";
var BUGNUMBER = "none";

// startTest();

// writeHeaderToLog( SECTION + " "+ TITLE);

var HEX_STRING = "0x0";
var HEX_VALUE = 0;

this.TestCase( SECTION, 
	      "parseInt.length",     
	      2,     
	      parseInt.length );

this.TestCase( SECTION, 
	      "parseInt.length = 0; parseInt.length",    
	      2,     
	      eval("parseInt.length = 0; parseInt.length") );

this.TestCase( SECTION, 
	      "var PROPS=''; for ( var p in parseInt ) { PROPS += p; }; PROPS",   "prototype",
	      eval("var PROPS=''; for ( var p in parseInt ) { PROPS += p; }; PROPS") );

this.TestCase( SECTION, 
	      "delete parseInt.length",  
	      false, 
	      delete parseInt.length );

this.TestCase( SECTION, 
	      "delete parseInt.length; parseInt.length", 
	      2, 
	      eval("delete parseInt.length; parseInt.length") );

this.TestCase( SECTION, 
	      "parseInt.length = null; parseInt.length", 
	      2, 
	      eval("parseInt.length = null; parseInt.length") );

this.TestCase( SECTION, 
	      "parseInt()",      
	      NaN,   
	      parseInt() );

this.TestCase( SECTION, 
	      "parseInt('')",    
	      NaN,   
	      parseInt("") );

this.TestCase( SECTION, 
	      "parseInt('','')", 
	      NaN,   
	      parseInt("","") );

this.TestCase( SECTION,
	      "parseInt(\"     0xabcdef     ",
	      11259375,
	      parseInt( "      0xabcdef     " ));

this.TestCase( SECTION,
	      "parseInt(\"     0XABCDEF     ",
	      11259375,
	      parseInt( "      0XABCDEF     " ) );

this.TestCase( SECTION,
	      "parseInt( 0xabcdef )",
	      11259375,
	      parseInt( "0xabcdef") );

this.TestCase( SECTION,
	      "parseInt( 0XABCDEF )",
	      11259375,
	      parseInt( "0XABCDEF") );

for ( HEX_STRING = "0x0", HEX_VALUE = 0, POWER = 0; POWER < 15; POWER++, HEX_STRING = HEX_STRING +"f" ) {
  this.TestCase( SECTION, "parseInt("+HEX_STRING+")",    HEX_VALUE,  parseInt(HEX_STRING) );
  HEX_VALUE += Math.pow(16,POWER)*15;
}
for ( HEX_STRING = "0X0", HEX_VALUE = 0, POWER = 0; POWER < 15; POWER++, HEX_STRING = HEX_STRING +"f" ) {
  this.TestCase( SECTION, "parseInt("+HEX_STRING+")",    HEX_VALUE,  parseInt(HEX_STRING) );
  HEX_VALUE += Math.pow(16,POWER)*15;
}
for ( HEX_STRING = "0x0", HEX_VALUE = 0, POWER = 0; POWER < 15; POWER++, HEX_STRING = HEX_STRING +"f" ) {
  this.TestCase( SECTION, "parseInt("+HEX_STRING+",16)",    HEX_VALUE,  parseInt(HEX_STRING,16) );
  HEX_VALUE += Math.pow(16,POWER)*15;
}
for ( HEX_STRING = "0x0", HEX_VALUE = 0, POWER = 0; POWER < 15; POWER++, HEX_STRING = HEX_STRING +"f" ) {
  this.TestCase( SECTION, "parseInt("+HEX_STRING+",16)",    HEX_VALUE,  parseInt(HEX_STRING,16) );
  HEX_VALUE += Math.pow(16,POWER)*15;
}
for ( HEX_STRING = "0x0", HEX_VALUE = 0, POWER = 0; POWER < 15; POWER++, HEX_STRING = HEX_STRING +"f" ) {
  this.TestCase( SECTION, "parseInt("+HEX_STRING+",null)",    HEX_VALUE,  parseInt(HEX_STRING,null) );
  HEX_VALUE += Math.pow(16,POWER)*15;
}
for ( HEX_STRING = "0x0", HEX_VALUE = 0, POWER = 0; POWER < 15; POWER++, HEX_STRING = HEX_STRING +"f" ) {
  this.TestCase( SECTION, "parseInt("+HEX_STRING+", void 0)",    HEX_VALUE,  parseInt(HEX_STRING, void 0) );
  HEX_VALUE += Math.pow(16,POWER)*15;
}

// a few tests with spaces

for ( var space = " ", HEX_STRING = "0x0", HEX_VALUE = 0, POWER = 0;
      POWER < 15;
      POWER++, HEX_STRING = HEX_STRING +"f", space += " ")
{
  this.TestCase( SECTION, "parseInt("+space+HEX_STRING+space+", void 0)",    HEX_VALUE,  parseInt(space+HEX_STRING+space, void 0) );
  HEX_VALUE += Math.pow(16,POWER)*15;
}

// a few tests with negative numbers
for ( HEX_STRING = "-0x0", HEX_VALUE = 0, POWER = 0; POWER < 15; POWER++, HEX_STRING = HEX_STRING +"f" ) {
  this.TestCase( SECTION, "parseInt("+HEX_STRING+")",    HEX_VALUE,  parseInt(HEX_STRING) );
  HEX_VALUE -= Math.pow(16,POWER)*15;
}

// we should stop parsing when we get to a value that is not a numeric literal for the type we expect

for ( HEX_STRING = "0x0", HEX_VALUE = 0, POWER = 0; POWER < 15; POWER++, HEX_STRING = HEX_STRING +"f" ) {
  this.TestCase( SECTION, "parseInt("+HEX_STRING+"g,16)",    HEX_VALUE,  parseInt(HEX_STRING+"g",16) );
  HEX_VALUE += Math.pow(16,POWER)*15;
}
for ( HEX_STRING = "0x0", HEX_VALUE = 0, POWER = 0; POWER < 15; POWER++, HEX_STRING = HEX_STRING +"f" ) {
  this.TestCase( SECTION, "parseInt("+HEX_STRING+"g,16)",    HEX_VALUE,  parseInt(HEX_STRING+"G",16) );
  HEX_VALUE += Math.pow(16,POWER)*15;
}

for ( HEX_STRING = "-0x0", HEX_VALUE = 0, POWER = 0; POWER < 15; POWER++, HEX_STRING = HEX_STRING +"f" ) {
  this.TestCase( SECTION, "parseInt("+HEX_STRING+")",    HEX_VALUE,  parseInt(HEX_STRING) );
  HEX_VALUE -= Math.pow(16,POWER)*15;
}
for ( HEX_STRING = "-0X0", HEX_VALUE = 0, POWER = 0; POWER < 15; POWER++, HEX_STRING = HEX_STRING +"f" ) {
  this.TestCase( SECTION, "parseInt("+HEX_STRING+")",    HEX_VALUE,  parseInt(HEX_STRING) );
  HEX_VALUE -= Math.pow(16,POWER)*15;
}
for ( HEX_STRING = "-0x0", HEX_VALUE = 0, POWER = 0; POWER < 15; POWER++, HEX_STRING = HEX_STRING +"f" ) {
  this.TestCase( SECTION, "parseInt("+HEX_STRING+",16)",    HEX_VALUE,  parseInt(HEX_STRING,16) );
  HEX_VALUE -= Math.pow(16,POWER)*15;
}
for ( HEX_STRING = "-0x0", HEX_VALUE = 0, POWER = 0; POWER < 15; POWER++, HEX_STRING = HEX_STRING +"f" ) {
  this.TestCase( SECTION, "parseInt("+HEX_STRING+",16)",    HEX_VALUE,  parseInt(HEX_STRING,16) );
  HEX_VALUE -= Math.pow(16,POWER)*15;
}

//  let us do some octal tests.  numbers that start with 0 and do not provid a radix should
//  default to using "0" as a radix.

var OCT_STRING = "0";
var OCT_VALUE = 0;

for ( OCT_STRING = "0", OCT_VALUE = 0, POWER = 0; POWER < 15; POWER++, OCT_STRING = OCT_STRING +"7" ) {
  this.TestCase( SECTION, "parseInt("+OCT_STRING+")",    OCT_VALUE,  parseInt(OCT_STRING) );
  OCT_VALUE += Math.pow(8,POWER)*7;
}

for ( OCT_STRING = "-0", OCT_VALUE = 0, POWER = 0; POWER < 15; POWER++, OCT_STRING = OCT_STRING +"7" ) {
  this.TestCase( SECTION, "parseInt("+OCT_STRING+")",    OCT_VALUE,  parseInt(OCT_STRING) );
  OCT_VALUE -= Math.pow(8,POWER)*7;
}

// should get the same results as above if we provid the radix of 8 (or 010)

for ( OCT_STRING = "0", OCT_VALUE = 0, POWER = 0; POWER < 15; POWER++, OCT_STRING = OCT_STRING +"7" ) {
  this.TestCase( SECTION, "parseInt("+OCT_STRING+",8)",    OCT_VALUE,  parseInt(OCT_STRING,8) );
  OCT_VALUE += Math.pow(8,POWER)*7;
}
for ( OCT_STRING = "-0", OCT_VALUE = 0, POWER = 0; POWER < 15; POWER++, OCT_STRING = OCT_STRING +"7" ) {
  this.TestCase( SECTION, "parseInt("+OCT_STRING+",010)",    OCT_VALUE,  parseInt(OCT_STRING,010) );
  OCT_VALUE -= Math.pow(8,POWER)*7;
}

// we shall stop parsing digits when we get one that isn't a numeric literal of the type we think
// it should be.
for ( OCT_STRING = "0", OCT_VALUE = 0, POWER = 0; POWER < 15; POWER++, OCT_STRING = OCT_STRING +"7" ) {
  this.TestCase( SECTION, "parseInt("+OCT_STRING+"8,8)",    OCT_VALUE,  parseInt(OCT_STRING+"8",8) );
  OCT_VALUE += Math.pow(8,POWER)*7;
}
for ( OCT_STRING = "-0", OCT_VALUE = 0, POWER = 0; POWER < 15; POWER++, OCT_STRING = OCT_STRING +"7" ) {
  this.TestCase( SECTION, "parseInt("+OCT_STRING+"8,010)",    OCT_VALUE,  parseInt(OCT_STRING+"8",010) );
  OCT_VALUE -= Math.pow(8,POWER)*7;
}

this.TestCase( SECTION,
	      "parseInt( '0x' )",             
	      NaN,       
	      parseInt("0x") );

this.TestCase( SECTION,
	      "parseInt( '0X' )",             
	      NaN,       
	      parseInt("0X") );

//this.TestCase( SECTION,
//	      "parseInt( '11111111112222222222' )",   
//	      11111111112222222222,  
//	      parseInt("11111111112222222222") );
//
//this.TestCase( SECTION,
//	      "parseInt( '111111111122222222223' )",   
//	      111111111122222222220,  
//	      parseInt("111111111122222222223") );
//
//this.TestCase( SECTION,
//	      "parseInt( '11111111112222222222',10 )",   
//	      11111111112222222222,  
//	      parseInt("11111111112222222222",10) );
//
//this.TestCase( SECTION,
//	      "parseInt( '111111111122222222223',10 )",   
//	      111111111122222222220,  
//	      parseInt("111111111122222222223",10) );

this.TestCase( SECTION,
	      "parseInt( '01234567890', -1 )", 
	      Number.NaN,   
	      parseInt("01234567890",-1) );

this.TestCase( SECTION,
	      "parseInt( '01234567890', 0 )", 
	      Number.NaN,    
	      parseInt("01234567890",1) );

this.TestCase( SECTION,
	      "parseInt( '01234567890', 1 )", 
	      Number.NaN,    
	      parseInt("01234567890",1) );

this.TestCase( SECTION,
	      "parseInt( '01234567890', 2 )", 
	      1,             
	      parseInt("01234567890",2) );

this.TestCase( SECTION,
	      "parseInt( '01234567890', 3 )", 
	      5,             
	      parseInt("01234567890",3) );

this.TestCase( SECTION,
	      "parseInt( '01234567890', 4 )", 
	      27,            
	      parseInt("01234567890",4) );

this.TestCase( SECTION,
	      "parseInt( '01234567890', 5 )", 
	      194,           
	      parseInt("01234567890",5) );

this.TestCase( SECTION,
	      "parseInt( '01234567890', 6 )", 
	      1865,          
	      parseInt("01234567890",6) );

this.TestCase( SECTION,
	      "parseInt( '01234567890', 7 )", 
	      22875,         
	      parseInt("01234567890",7) );

this.TestCase( SECTION,
	      "parseInt( '01234567890', 8 )", 
	      342391,        
	      parseInt("01234567890",8) );

this.TestCase( SECTION,
	      "parseInt( '01234567890', 9 )", 
	      6053444,       
	      parseInt("01234567890",9) );

this.TestCase( SECTION,
	      "parseInt( '01234567890', 10 )",
	      1234567890,    
	      parseInt("01234567890",10) );

// need more test cases with hex radix

this.TestCase( SECTION,
	      "parseInt( '1234567890', '0xa')",
	      1234567890,
	      parseInt("1234567890","0xa") );

this.TestCase( SECTION,
	      "parseInt( '012345', 11 )",     
	      17715,         
	      parseInt("012345",11) );

this.TestCase( SECTION,
	      "parseInt( '012345', 35 )",     
	      1590195,       
	      parseInt("012345",35) );

this.TestCase( SECTION,
	      "parseInt( '012345', 36 )",     
	      1776965,       
	      parseInt("012345",36) );

this.TestCase( SECTION,
	      "parseInt( '012345', 37 )",     
	      Number.NaN,    
	      parseInt("012345",37) );

// test();

},

/**
   File Name:          15.1.2.2-1.js
   ECMA Section:       15.1.2.2 Function properties of the global object
   parseInt( string, radix )

   Description:        parseInt test cases written by waldemar, and documented in
   http://scopus.mcom.com/bugsplat/show_bug.cgi?id=123874.

   Author:             christine@netscape.com
   Date:               28 october 1997

*/
test_15_1_2_2__2:function(){

var SECTION = "15.1.2.2-2";
var VERSION = "ECMA_1";
var TITLE   = "parseInt(string, radix)";
var BUGNUMBER = "none";

// startTest();

// writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
	      'parseInt("000000100000000100100011010001010110011110001001101010111100",2)',
	      9027215253084860,
	      parseInt("000000100000000100100011010001010110011110001001101010111100",2) );

this.TestCase( SECTION,
	      'parseInt("000000100000000100100011010001010110011110001001101010111101",2)',
	      9027215253084860,
	      parseInt("000000100000000100100011010001010110011110001001101010111101",2));

this.TestCase( SECTION,
	      'parseInt("000000100000000100100011010001010110011110001001101010111111",2)',
	      9027215253084864,
	      parseInt("000000100000000100100011010001010110011110001001101010111111",2) );

this.TestCase( SECTION,
	      'parseInt("0000001000000001001000110100010101100111100010011010101111010",2)',
	      18054430506169720,
	      parseInt("0000001000000001001000110100010101100111100010011010101111010",2) );

this.TestCase( SECTION,
	      'parseInt("0000001000000001001000110100010101100111100010011010101111011",2)',
	      18054430506169724,
	      parseInt("0000001000000001001000110100010101100111100010011010101111011",2));

this.TestCase( SECTION,
	      'parseInt("0000001000000001001000110100010101100111100010011010101111100",2)',
	      18054430506169724,
	      parseInt("0000001000000001001000110100010101100111100010011010101111100",2) );

this.TestCase( SECTION,
	      'parseInt("0000001000000001001000110100010101100111100010011010101111110",2)',
	      18054430506169728,
	      parseInt("0000001000000001001000110100010101100111100010011010101111110",2) );

this.TestCase( SECTION,
	      'parseInt("yz",35)',
	      34,
	      parseInt("yz",35) );

this.TestCase( SECTION,
	      'parseInt("yz",36)',
	      1259,
	      parseInt("yz",36) );

this.TestCase( SECTION,
	      'parseInt("yz",37)',
	      NaN,
	      parseInt("yz",37) );

this.TestCase( SECTION,
	      'parseInt("+77")',
	      77,
	      parseInt("+77") );

this.TestCase( SECTION,
	      'parseInt("-77",9)',
	      -70,
	      parseInt("-77",9) );

this.TestCase( SECTION,
	      'parseInt("\u20001234\u2000")',
	      1234,
	      parseInt("\u20001234\u2000") );

this.TestCase( SECTION,
	      'parseInt("123456789012345678")',
	      123456789012345680,
	      parseInt("123456789012345678") );

this.TestCase( SECTION,
	      'parseInt("9",8)',
	      NaN,
	      parseInt("9",8) );

this.TestCase( SECTION,
	      'parseInt("1e2")',
	      1,
	      parseInt("1e2") );

this.TestCase( SECTION,
	      'parseInt("1.9999999999999999999")',
	      1,
	      parseInt("1.9999999999999999999") );

this.TestCase( SECTION,
	      'parseInt("0x10")',
	      16,
	      parseInt("0x10") );

this.TestCase( SECTION,
	      'parseInt("0x10",10)',
	      0,
	      parseInt("0x10",10));

this.TestCase( SECTION,
	      'parseInt("0022")',
	      18,
	      parseInt("0022"));

this.TestCase( SECTION,
	      'parseInt("0022",10)',
	      22,
	      parseInt("0022",10) );

this.TestCase( SECTION,
	      'parseInt("0x1000000000000080")',
	      1152921504606847000,
	      parseInt("0x1000000000000080") );

this.TestCase( SECTION,
	      'parseInt("0x1000000000000081")',
	      1152921504606847200,
	      parseInt("0x1000000000000081") );

s =
  "0xFFFFFFFFFFFFF80000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"

  s += "0000000000000000000000000000000000000";

this.TestCase( SECTION,
	      "s = " + s +"; -s",
	      -1.7976931348623157e+308,
	      -s );

s =
  "0xFFFFFFFFFFFFF80000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
s += "0000000000000000000000000000000000001";

this.TestCase( SECTION,
	      "s = " + s +"; -s",
	      -1.7976931348623157e+308,
	      -s );


s = "0xFFFFFFFFFFFFFC0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";

s += "0000000000000000000000000000000000000"


this.TestCase( SECTION,
	      "s = " + s + "; -s",
	      -Infinity,
	      -s );

s = "0xFFFFFFFFFFFFFB0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
s += "0000000000000000000000000000000000001";

this.TestCase( SECTION,
	      "s = " + s + "; -s",
	      -1.7976931348623157e+308,
	      -s );

s += "0"

this.TestCase( SECTION,
	      "s = " + s + "; -s",
	      -Infinity,
	      -s );

this.TestCase( SECTION,
	      'parseInt(s)',
	      Infinity,
	      parseInt(s) );

this.TestCase( SECTION,
	      'parseInt(s,32)',
	      0,
	      parseInt(s,32) );

this.TestCase( SECTION,
	      'parseInt(s,36)',
	      Infinity,
	      parseInt(s,36));

// test();


},

/**
   File Name:          15.1.2.3.js
   ECMA Section:       15.1.2.3 Function properties of the global object:
   parseFloat( string )

   Description:        The parseFloat function produces a number value dictated
   by the interpretation of the contents of the string
   argument defined as a decimal literal.

   When the parseFloat function is called, the following
   steps are taken:

   1.  Call ToString( string ).
   2.  Remove leading whitespace Result(1).
   3.  If neither Result(2) nor any prefix of Result(2)
   satisfies the syntax of a StrDecimalLiteral,
   return NaN.
   4.  Compute the longest prefix of Result(2) which might
   be Resusult(2) itself, that satisfies the syntax of
   a StrDecimalLiteral
   5.  Return the number value for the MV of Result(4).

   Note that parseFloate may interpret only a leading
   portion of the string as a number value; it ignores any
   characters that cannot be interpreted as part of the
   notation of a decimal literal, and no indication is given
   that such characters were ignored.

   StrDecimalLiteral::
   Infinity
   DecimalDigits.DecimalDigits opt ExponentPart opt
   .DecimalDigits ExponentPart opt
   DecimalDigits ExponentPart opt

   Author:             christine@netscape.com
   Date:               28 october 1997

*/
test_15_1_2_3__1:function(){


var SECTION = "15.1.2.3-1";
var VERSION = "ECMA_1";
var TITLE   = "parseFloat(string)";
var BUGNUMBER="none";

// startTest();
// writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION, "parseFloat.length",     1,              parseFloat.length );

this.TestCase( SECTION, "parseFloat.length = null; parseFloat.length",   1,      eval("parseFloat.length = null; parseFloat.length") );
this.TestCase( SECTION, "delete parseFloat.length",                      false,  delete parseFloat.length );
this.TestCase( SECTION, "delete parseFloat.length; parseFloat.length",   1,      eval("delete parseFloat.length; parseFloat.length") );
this.TestCase( SECTION, "var MYPROPS=''; for ( var p in parseFloat ) { MYPROPS += p }; MYPROPS", "prototype", eval("var MYPROPS=''; for ( var p in parseFloat ) { MYPROPS += p }; MYPROPS") );

this.TestCase( SECTION, "parseFloat()",          Number.NaN,     parseFloat() );
this.TestCase( SECTION, "parseFloat('')",        Number.NaN,     parseFloat('') );

this.TestCase( SECTION, "parseFloat(' ')",       Number.NaN,     parseFloat(' ') );
this.TestCase( SECTION, "parseFloat(true)",      Number.NaN,     parseFloat(true) );
this.TestCase( SECTION, "parseFloat(false)",     Number.NaN,     parseFloat(false) );
this.TestCase( SECTION, "parseFloat('string')",  Number.NaN,     parseFloat("string") );

this.TestCase( SECTION, "parseFloat('  Infinity')",    Infinity,    parseFloat("Infinity") );
this.TestCase( SECTION, "parseFloat('  Infinity  ')",      Infinity,    parseFloat('  Infinity  ') );

this.TestCase( SECTION, "parseFloat('Infinity')",    Infinity,    parseFloat("Infinity") );
this.TestCase( SECTION, "parseFloat(Infinity)",      Infinity,    parseFloat(Infinity) );


this.TestCase( SECTION, "parseFloat('  +Infinity')",         +Infinity,    parseFloat("+Infinity") );
this.TestCase( SECTION, "parseFloat('  -Infinity  ')",      -Infinity,    parseFloat('  -Infinity  ') );

this.TestCase( SECTION, "parseFloat('+Infinity')",    +Infinity,    parseFloat("+Infinity") );
this.TestCase( SECTION, "parseFloat(-Infinity)",      -Infinity,    parseFloat(-Infinity) );

this.TestCase( SECTION,  "parseFloat('0')",          0,          parseFloat("0") );
this.TestCase( SECTION,  "parseFloat('-0')",         -0,         parseFloat("-0") );
this.TestCase( SECTION,  "parseFloat('+0')",          0,         parseFloat("+0") );

this.TestCase( SECTION,  "parseFloat('1')",          1,          parseFloat("1") );
this.TestCase( SECTION,  "parseFloat('-1')",         -1,         parseFloat("-1") );
this.TestCase( SECTION,  "parseFloat('+1')",          1,         parseFloat("+1") );

this.TestCase( SECTION,  "parseFloat('2')",          2,          parseFloat("2") );
this.TestCase( SECTION,  "parseFloat('-2')",         -2,         parseFloat("-2") );
this.TestCase( SECTION,  "parseFloat('+2')",          2,         parseFloat("+2") );

this.TestCase( SECTION,  "parseFloat('3')",          3,          parseFloat("3") );
this.TestCase( SECTION,  "parseFloat('-3')",         -3,         parseFloat("-3") );
this.TestCase( SECTION,  "parseFloat('+3')",          3,         parseFloat("+3") );

this.TestCase( SECTION,  "parseFloat('4')",          4,          parseFloat("4") );
this.TestCase( SECTION,  "parseFloat('-4')",         -4,         parseFloat("-4") );
this.TestCase( SECTION,  "parseFloat('+4')",          4,         parseFloat("+4") );

this.TestCase( SECTION,  "parseFloat('5')",          5,          parseFloat("5") );
this.TestCase( SECTION,  "parseFloat('-5')",         -5,         parseFloat("-5") );
this.TestCase( SECTION,  "parseFloat('+5')",          5,         parseFloat("+5") );

this.TestCase( SECTION,  "parseFloat('6')",          6,          parseFloat("6") );
this.TestCase( SECTION,  "parseFloat('-6')",         -6,         parseFloat("-6") );
this.TestCase( SECTION,  "parseFloat('+6')",          6,         parseFloat("+6") );

this.TestCase( SECTION,  "parseFloat('7')",          7,          parseFloat("7") );
this.TestCase( SECTION,  "parseFloat('-7')",         -7,         parseFloat("-7") );
this.TestCase( SECTION,  "parseFloat('+7')",          7,         parseFloat("+7") );

this.TestCase( SECTION,  "parseFloat('8')",          8,          parseFloat("8") );
this.TestCase( SECTION,  "parseFloat('-8')",         -8,         parseFloat("-8") );
this.TestCase( SECTION,  "parseFloat('+8')",          8,         parseFloat("+8") );

this.TestCase( SECTION,  "parseFloat('9')",          9,          parseFloat("9") );
this.TestCase( SECTION,  "parseFloat('-9')",         -9,         parseFloat("-9") );
this.TestCase( SECTION,  "parseFloat('+9')",          9,         parseFloat("+9") );

this.TestCase( SECTION,  "parseFloat('3.14159')",    3.14159,    parseFloat("3.14159") );
this.TestCase( SECTION,  "parseFloat('-3.14159')",   -3.14159,   parseFloat("-3.14159") );
this.TestCase( SECTION,  "parseFloat('+3.14159')",   3.14159,    parseFloat("+3.14159") );

this.TestCase( SECTION,  "parseFloat('3.')",         3,          parseFloat("3.") );
this.TestCase( SECTION,  "parseFloat('-3.')",        -3,         parseFloat("-3.") );
this.TestCase( SECTION,  "parseFloat('+3.')",        3,          parseFloat("+3.") );

this.TestCase( SECTION,  "parseFloat('3.e1')",       30,         parseFloat("3.e1") );
this.TestCase( SECTION,  "parseFloat('-3.e1')",      -30,        parseFloat("-3.e1") );
this.TestCase( SECTION,  "parseFloat('+3.e1')",      30,         parseFloat("+3.e1") );

this.TestCase( SECTION,  "parseFloat('3.e+1')",       30,         parseFloat("3.e+1") );
this.TestCase( SECTION,  "parseFloat('-3.e+1')",      -30,        parseFloat("-3.e+1") );
this.TestCase( SECTION,  "parseFloat('+3.e+1')",      30,         parseFloat("+3.e+1") );

this.TestCase( SECTION,  "parseFloat('3.e-1')",       .30,         parseFloat("3.e-1") );
this.TestCase( SECTION,  "parseFloat('-3.e-1')",      -.30,        parseFloat("-3.e-1") );
this.TestCase( SECTION,  "parseFloat('+3.e-1')",      .30,         parseFloat("+3.e-1") );

// StrDecimalLiteral:::  .DecimalDigits ExponentPart opt

this.TestCase( SECTION,  "parseFloat('.00001')",     0.00001,    parseFloat(".00001") );
this.TestCase( SECTION,  "parseFloat('+.00001')",    0.00001,    parseFloat("+.00001") );
this.TestCase( SECTION,  "parseFloat('-0.0001')",    -0.00001,   parseFloat("-.00001") );

this.TestCase( SECTION,  "parseFloat('.01e2')",      1,          parseFloat(".01e2") );
this.TestCase( SECTION,  "parseFloat('+.01e2')",     1,          parseFloat("+.01e2") );
this.TestCase( SECTION,  "parseFloat('-.01e2')",     -1,         parseFloat("-.01e2") );

this.TestCase( SECTION,  "parseFloat('.01e+2')",      1,         parseFloat(".01e+2") );
this.TestCase( SECTION,  "parseFloat('+.01e+2')",     1,         parseFloat("+.01e+2") );
this.TestCase( SECTION,  "parseFloat('-.01e+2')",     -1,        parseFloat("-.01e+2") );

this.TestCase( SECTION,  "parseFloat('.01e-2')",      0.0001,    parseFloat(".01e-2") );
this.TestCase( SECTION,  "parseFloat('+.01e-2')",     0.0001,    parseFloat("+.01e-2") );
this.TestCase( SECTION,  "parseFloat('-.01e-2')",     -0.0001,   parseFloat("-.01e-2") );

//  StrDecimalLiteral:::    DecimalDigits ExponentPart opt

this.TestCase( SECTION,  "parseFloat('1234e5')",     123400000,  parseFloat("1234e5") );
this.TestCase( SECTION,  "parseFloat('+1234e5')",    123400000,  parseFloat("+1234e5") );
this.TestCase( SECTION,  "parseFloat('-1234e5')",    -123400000, parseFloat("-1234e5") );

this.TestCase( SECTION,  "parseFloat('1234e+5')",    123400000,  parseFloat("1234e+5") );
this.TestCase( SECTION,  "parseFloat('+1234e+5')",   123400000,  parseFloat("+1234e+5") );
this.TestCase( SECTION,  "parseFloat('-1234e+5')",   -123400000, parseFloat("-1234e+5") );

this.TestCase( SECTION,  "parseFloat('1234e-5')",     0.01234,  parseFloat("1234e-5") );
this.TestCase( SECTION,  "parseFloat('+1234e-5')",    0.01234,  parseFloat("+1234e-5") );
this.TestCase( SECTION,  "parseFloat('-1234e-5')",    -0.01234, parseFloat("-1234e-5") );


this.TestCase( SECTION,  "parseFloat(0)",          0,          parseFloat(0) );
this.TestCase( SECTION,  "parseFloat(-0)",         -0,         parseFloat(-0) );

this.TestCase( SECTION,  "parseFloat(1)",          1,          parseFloat(1) );
this.TestCase( SECTION,  "parseFloat(-1)",         -1,         parseFloat(-1) );

this.TestCase( SECTION,  "parseFloat(2)",          2,          parseFloat(2) );
this.TestCase( SECTION,  "parseFloat(-2)",         -2,         parseFloat(-2) );

this.TestCase( SECTION,  "parseFloat(3)",          3,          parseFloat(3) );
this.TestCase( SECTION,  "parseFloat(-3)",         -3,         parseFloat(-3) );

this.TestCase( SECTION,  "parseFloat(4)",          4,          parseFloat(4) );
this.TestCase( SECTION,  "parseFloat(-4)",         -4,         parseFloat(-4) );

this.TestCase( SECTION,  "parseFloat(5)",          5,          parseFloat(5) );
this.TestCase( SECTION,  "parseFloat(-5)",         -5,         parseFloat(-5) );

this.TestCase( SECTION,  "parseFloat(6)",          6,          parseFloat(6) );
this.TestCase( SECTION,  "parseFloat(-6)",         -6,         parseFloat(-6) );

this.TestCase( SECTION,  "parseFloat(7)",          7,          parseFloat(7) );
this.TestCase( SECTION,  "parseFloat(-7)",         -7,         parseFloat(-7) );

this.TestCase( SECTION,  "parseFloat(8)",          8,          parseFloat(8) );
this.TestCase( SECTION,  "parseFloat(-8)",         -8,         parseFloat(-8) );

this.TestCase( SECTION,  "parseFloat(9)",          9,          parseFloat(9) );
this.TestCase( SECTION,  "parseFloat(-9)",         -9,         parseFloat(-9) );

this.TestCase( SECTION,  "parseFloat(3.14159)",    3.14159,    parseFloat(3.14159) );
this.TestCase( SECTION,  "parseFloat(-3.14159)",   -3.14159,   parseFloat(-3.14159) );

this.TestCase( SECTION,  "parseFloat(3.)",         3,          parseFloat(3.) );
this.TestCase( SECTION,  "parseFloat(-3.)",        -3,         parseFloat(-3.) );

this.TestCase( SECTION,  "parseFloat(3.e1)",       30,         parseFloat(3.e1) );
this.TestCase( SECTION,  "parseFloat(-3.e1)",      -30,        parseFloat(-3.e1) );

this.TestCase( SECTION,  "parseFloat(3.e+1)",       30,         parseFloat(3.e+1) );
this.TestCase( SECTION,  "parseFloat(-3.e+1)",      -30,        parseFloat(-3.e+1) );

this.TestCase( SECTION,  "parseFloat(3.e-1)",       .30,         parseFloat(3.e-1) );
this.TestCase( SECTION,  "parseFloat(-3.e-1)",      -.30,        parseFloat(-3.e-1) );


this.TestCase( SECTION,  "parseFloat(3.E1)",       30,         parseFloat(3.E1) );
this.TestCase( SECTION,  "parseFloat(-3.E1)",      -30,        parseFloat(-3.E1) );

this.TestCase( SECTION,  "parseFloat(3.E+1)",       30,         parseFloat(3.E+1) );
this.TestCase( SECTION,  "parseFloat(-3.E+1)",      -30,        parseFloat(-3.E+1) );

this.TestCase( SECTION,  "parseFloat(3.E-1)",       .30,         parseFloat(3.E-1) );
this.TestCase( SECTION,  "parseFloat(-3.E-1)",      -.30,        parseFloat(-3.E-1) );

// StrDecimalLiteral:::  .DecimalDigits ExponentPart opt

this.TestCase( SECTION,  "parseFloat(.00001)",     0.00001,    parseFloat(.00001) );
this.TestCase( SECTION,  "parseFloat(-0.0001)",    -0.00001,   parseFloat(-.00001) );

this.TestCase( SECTION,  "parseFloat(.01e2)",      1,          parseFloat(.01e2) );
this.TestCase( SECTION,  "parseFloat(-.01e2)",     -1,         parseFloat(-.01e2) );

this.TestCase( SECTION,  "parseFloat(.01e+2)",      1,         parseFloat(.01e+2) );
this.TestCase( SECTION,  "parseFloat(-.01e+2)",     -1,        parseFloat(-.01e+2) );

this.TestCase( SECTION,  "parseFloat(.01e-2)",      0.0001,    parseFloat(.01e-2) );
this.TestCase( SECTION,  "parseFloat(-.01e-2)",     -0.0001,   parseFloat(-.01e-2) );

//  StrDecimalLiteral:::    DecimalDigits ExponentPart opt

this.TestCase( SECTION,  "parseFloat(1234e5)",     123400000,  parseFloat(1234e5) );
this.TestCase( SECTION,  "parseFloat(-1234e5)",    -123400000, parseFloat(-1234e5) );

this.TestCase( SECTION,  "parseFloat(1234e+5)",    123400000,  parseFloat(1234e+5) );
this.TestCase( SECTION,  "parseFloat(-1234e+5)",   -123400000, parseFloat(-1234e+5) );

this.TestCase( SECTION,  "parseFloat(1234e-5)",     0.01234,  parseFloat(1234e-5) );
this.TestCase( SECTION,  "parseFloat(-1234e-5)",    -0.01234, parseFloat(-1234e-5) );

// hex cases should all return 0  (0 is the longest string that satisfies a StringDecimalLiteral)

this.TestCase( SECTION,  "parseFloat('0x0')",        0,         parseFloat("0x0"));
this.TestCase( SECTION,  "parseFloat('0x1')",        0,         parseFloat("0x1"));
this.TestCase( SECTION,  "parseFloat('0x2')",        0,         parseFloat("0x2"));
this.TestCase( SECTION,  "parseFloat('0x3')",        0,         parseFloat("0x3"));
this.TestCase( SECTION,  "parseFloat('0x4')",        0,         parseFloat("0x4"));
this.TestCase( SECTION,  "parseFloat('0x5')",        0,         parseFloat("0x5"));
this.TestCase( SECTION,  "parseFloat('0x6')",        0,         parseFloat("0x6"));
this.TestCase( SECTION,  "parseFloat('0x7')",        0,         parseFloat("0x7"));
this.TestCase( SECTION,  "parseFloat('0x8')",        0,         parseFloat("0x8"));
this.TestCase( SECTION,  "parseFloat('0x9')",        0,         parseFloat("0x9"));
this.TestCase( SECTION,  "parseFloat('0xa')",        0,         parseFloat("0xa"));
this.TestCase( SECTION,  "parseFloat('0xb')",        0,         parseFloat("0xb"));
this.TestCase( SECTION,  "parseFloat('0xc')",        0,         parseFloat("0xc"));
this.TestCase( SECTION,  "parseFloat('0xd')",        0,         parseFloat("0xd"));
this.TestCase( SECTION,  "parseFloat('0xe')",        0,         parseFloat("0xe"));
this.TestCase( SECTION,  "parseFloat('0xf')",        0,         parseFloat("0xf"));
this.TestCase( SECTION,  "parseFloat('0xA')",        0,         parseFloat("0xA"));
this.TestCase( SECTION,  "parseFloat('0xB')",        0,         parseFloat("0xB"));
this.TestCase( SECTION,  "parseFloat('0xC')",        0,         parseFloat("0xC"));
this.TestCase( SECTION,  "parseFloat('0xD')",        0,         parseFloat("0xD"));
this.TestCase( SECTION,  "parseFloat('0xE')",        0,         parseFloat("0xE"));
this.TestCase( SECTION,  "parseFloat('0xF')",        0,         parseFloat("0xF"));

this.TestCase( SECTION,  "parseFloat('0X0')",        0,         parseFloat("0X0"));
this.TestCase( SECTION,  "parseFloat('0X1')",        0,         parseFloat("0X1"));
this.TestCase( SECTION,  "parseFloat('0X2')",        0,         parseFloat("0X2"));
this.TestCase( SECTION,  "parseFloat('0X3')",        0,         parseFloat("0X3"));
this.TestCase( SECTION,  "parseFloat('0X4')",        0,         parseFloat("0X4"));
this.TestCase( SECTION,  "parseFloat('0X5')",        0,         parseFloat("0X5"));
this.TestCase( SECTION,  "parseFloat('0X6')",        0,         parseFloat("0X6"));
this.TestCase( SECTION,  "parseFloat('0X7')",        0,         parseFloat("0X7"));
this.TestCase( SECTION,  "parseFloat('0X8')",        0,         parseFloat("0X8"));
this.TestCase( SECTION,  "parseFloat('0X9')",        0,         parseFloat("0X9"));
this.TestCase( SECTION,  "parseFloat('0Xa')",        0,         parseFloat("0Xa"));
this.TestCase( SECTION,  "parseFloat('0Xb')",        0,         parseFloat("0Xb"));
this.TestCase( SECTION,  "parseFloat('0Xc')",        0,         parseFloat("0Xc"));
this.TestCase( SECTION,  "parseFloat('0Xd')",        0,         parseFloat("0Xd"));
this.TestCase( SECTION,  "parseFloat('0Xe')",        0,         parseFloat("0Xe"));
this.TestCase( SECTION,  "parseFloat('0Xf')",        0,         parseFloat("0Xf"));
this.TestCase( SECTION,  "parseFloat('0XA')",        0,         parseFloat("0XA"));
this.TestCase( SECTION,  "parseFloat('0XB')",        0,         parseFloat("0XB"));
this.TestCase( SECTION,  "parseFloat('0XC')",        0,         parseFloat("0XC"));
this.TestCase( SECTION,  "parseFloat('0XD')",        0,         parseFloat("0XD"));
this.TestCase( SECTION,  "parseFloat('0XE')",        0,         parseFloat("0XE"));
this.TestCase( SECTION,  "parseFloat('0XF')",        0,         parseFloat("0XF"));
this.TestCase( SECTION,  "parseFloat('  0XF  ')",    0,         parseFloat("  0XF  "));

// hex literals should still succeed

this.TestCase( SECTION,  "parseFloat(0x0)",        0,          parseFloat(0x0));
this.TestCase( SECTION,  "parseFloat(0x1)",        1,          parseFloat(0x1));
this.TestCase( SECTION,  "parseFloat(0x2)",        2,          parseFloat(0x2));
this.TestCase( SECTION,  "parseFloat(0x3)",        3,          parseFloat(0x3));
this.TestCase( SECTION,  "parseFloat(0x4)",        4,          parseFloat(0x4));
this.TestCase( SECTION,  "parseFloat(0x5)",        5,          parseFloat(0x5));
this.TestCase( SECTION,  "parseFloat(0x6)",        6,          parseFloat(0x6));
this.TestCase( SECTION,  "parseFloat(0x7)",        7,          parseFloat(0x7));
this.TestCase( SECTION,  "parseFloat(0x8)",        8,          parseFloat(0x8));
this.TestCase( SECTION,  "parseFloat(0x9)",        9,          parseFloat(0x9));
this.TestCase( SECTION,  "parseFloat(0xa)",        10,         parseFloat(0xa));
this.TestCase( SECTION,  "parseFloat(0xb)",        11,         parseFloat(0xb));
this.TestCase( SECTION,  "parseFloat(0xc)",        12,         parseFloat(0xc));
this.TestCase( SECTION,  "parseFloat(0xd)",        13,         parseFloat(0xd));
this.TestCase( SECTION,  "parseFloat(0xe)",        14,         parseFloat(0xe));
this.TestCase( SECTION,  "parseFloat(0xf)",        15,         parseFloat(0xf));
this.TestCase( SECTION,  "parseFloat(0xA)",        10,         parseFloat(0xA));
this.TestCase( SECTION,  "parseFloat(0xB)",        11,         parseFloat(0xB));
this.TestCase( SECTION,  "parseFloat(0xC)",        12,         parseFloat(0xC));
this.TestCase( SECTION,  "parseFloat(0xD)",        13,         parseFloat(0xD));
this.TestCase( SECTION,  "parseFloat(0xE)",        14,         parseFloat(0xE));
this.TestCase( SECTION,  "parseFloat(0xF)",        15,         parseFloat(0xF));

this.TestCase( SECTION,  "parseFloat(0X0)",        0,          parseFloat(0X0));
this.TestCase( SECTION,  "parseFloat(0X1)",        1,          parseFloat(0X1));
this.TestCase( SECTION,  "parseFloat(0X2)",        2,          parseFloat(0X2));
this.TestCase( SECTION,  "parseFloat(0X3)",        3,          parseFloat(0X3));
this.TestCase( SECTION,  "parseFloat(0X4)",        4,          parseFloat(0X4));
this.TestCase( SECTION,  "parseFloat(0X5)",        5,          parseFloat(0X5));
this.TestCase( SECTION,  "parseFloat(0X6)",        6,          parseFloat(0X6));
this.TestCase( SECTION,  "parseFloat(0X7)",        7,          parseFloat(0X7));
this.TestCase( SECTION,  "parseFloat(0X8)",        8,          parseFloat(0X8));
this.TestCase( SECTION,  "parseFloat(0X9)",        9,          parseFloat(0X9));
this.TestCase( SECTION,  "parseFloat(0Xa)",        10,         parseFloat(0Xa));
this.TestCase( SECTION,  "parseFloat(0Xb)",        11,         parseFloat(0Xb));
this.TestCase( SECTION,  "parseFloat(0Xc)",        12,         parseFloat(0Xc));
this.TestCase( SECTION,  "parseFloat(0Xd)",        13,         parseFloat(0Xd));
this.TestCase( SECTION,  "parseFloat(0Xe)",        14,         parseFloat(0Xe));
this.TestCase( SECTION,  "parseFloat(0Xf)",        15,         parseFloat(0Xf));
this.TestCase( SECTION,  "parseFloat(0XA)",        10,         parseFloat(0XA));
this.TestCase( SECTION,  "parseFloat(0XB)",        11,         parseFloat(0XB));
this.TestCase( SECTION,  "parseFloat(0XC)",        12,         parseFloat(0XC));
this.TestCase( SECTION,  "parseFloat(0XD)",        13,         parseFloat(0XD));
this.TestCase( SECTION,  "parseFloat(0XE)",        14,         parseFloat(0XE));
this.TestCase( SECTION,  "parseFloat(0XF)",        15,         parseFloat(0XF));


// A StringNumericLiteral may not use octal notation

this.TestCase( SECTION,  "parseFloat('00')",        0,         parseFloat("00"));
this.TestCase( SECTION,  "parseFloat('01')",        1,         parseFloat("01"));
this.TestCase( SECTION,  "parseFloat('02')",        2,         parseFloat("02"));
this.TestCase( SECTION,  "parseFloat('03')",        3,         parseFloat("03"));
this.TestCase( SECTION,  "parseFloat('04')",        4,         parseFloat("04"));
this.TestCase( SECTION,  "parseFloat('05')",        5,         parseFloat("05"));
this.TestCase( SECTION,  "parseFloat('06')",        6,         parseFloat("06"));
this.TestCase( SECTION,  "parseFloat('07')",        7,         parseFloat("07"));
this.TestCase( SECTION,  "parseFloat('010')",       10,        parseFloat("010"));
this.TestCase( SECTION,  "parseFloat('011')",       11,        parseFloat("011"));

// A StringNumericLIteral may have any number of leading 0 digits

this.TestCase( SECTION,  "parseFloat('001')",        1,         parseFloat("001"));
this.TestCase( SECTION,  "parseFloat('0001')",       1,         parseFloat("0001"));
this.TestCase( SECTION,  "parseFloat('  0001  ')",       1,         parseFloat("  0001  "));

// an octal numeric literal should be treated as an octal

this.TestCase( SECTION,  "parseFloat(00)",        0,         parseFloat(00));
this.TestCase( SECTION,  "parseFloat(01)",        1,         parseFloat(01));
this.TestCase( SECTION,  "parseFloat(02)",        2,         parseFloat(02));
this.TestCase( SECTION,  "parseFloat(03)",        3,         parseFloat(03));
this.TestCase( SECTION,  "parseFloat(04)",        4,         parseFloat(04));
this.TestCase( SECTION,  "parseFloat(05)",        5,         parseFloat(05));
this.TestCase( SECTION,  "parseFloat(06)",        6,         parseFloat(06));
this.TestCase( SECTION,  "parseFloat(07)",        7,         parseFloat(07));
this.TestCase( SECTION,  "parseFloat(010)",       8,        parseFloat(010));
this.TestCase( SECTION,  "parseFloat(011)",       9,        parseFloat(011));

// A StringNumericLIteral may have any number of leading 0 digits

this.TestCase( SECTION,  "parseFloat(001)",        1,         parseFloat(001));
this.TestCase( SECTION,  "parseFloat(0001)",       1,         parseFloat(0001));

// make sure it's reflexive
this.TestCase( SECTION,  "parseFloat(Math.PI)",      Math.PI,        parseFloat(Math.PI));
this.TestCase( SECTION,  "parseFloat(Math.LN2)",     Math.LN2,       parseFloat(Math.LN2));
this.TestCase( SECTION,  "parseFloat(Math.LN10)",    Math.LN10,      parseFloat(Math.LN10));
this.TestCase( SECTION,  "parseFloat(Math.LOG2E)",   Math.LOG2E,     parseFloat(Math.LOG2E));
this.TestCase( SECTION,  "parseFloat(Math.LOG10E)",  Math.LOG10E,    parseFloat(Math.LOG10E));
this.TestCase( SECTION,  "parseFloat(Math.SQRT2)",   Math.SQRT2,     parseFloat(Math.SQRT2));
this.TestCase( SECTION,  "parseFloat(Math.SQRT1_2)", Math.SQRT1_2,   parseFloat(Math.SQRT1_2));

this.TestCase( SECTION,  "parseFloat(Math.PI+'')",      Math.PI,        parseFloat(Math.PI+''));
this.TestCase( SECTION,  "parseFloat(Math.LN2+'')",     Math.LN2,       parseFloat(Math.LN2+''));
this.TestCase( SECTION,  "parseFloat(Math.LN10+'')",    Math.LN10,      parseFloat(Math.LN10+''));
this.TestCase( SECTION,  "parseFloat(Math.LOG2E+'')",   Math.LOG2E,     parseFloat(Math.LOG2E+''));
this.TestCase( SECTION,  "parseFloat(Math.LOG10E+'')",  Math.LOG10E,    parseFloat(Math.LOG10E+''));
this.TestCase( SECTION,  "parseFloat(Math.SQRT2+'')",   Math.SQRT2,     parseFloat(Math.SQRT2+''));
this.TestCase( SECTION,  "parseFloat(Math.SQRT1_2+'')", Math.SQRT1_2,   parseFloat(Math.SQRT1_2+''));

// test();

},

/**
   File Name:          15.1.2.3-2.js
   ECMA Section:       15.1.2.3 Function properties of the global object:
   parseFloat( string )

   Description:        The parseFloat function produces a number value dictated
   by the interpretation of the contents of the string
   argument defined as a decimal literal.

   When the parseFloat function is called, the following
   steps are taken:

   1.  Call ToString( string ).
   2.  Remove leading whitespace Result(1).
   3.  If neither Result(2) nor any prefix of Result(2)
   satisfies the syntax of a StrDecimalLiteral,
   return NaN.
   4.  Compute the longest prefix of Result(2) which might
   be Resusult(2) itself, that satisfies the syntax of
   a StrDecimalLiteral
   5.  Return the number value for the MV of Result(4).

   Note that parseFloate may interpret only a leading
   portion of the string as a number value; it ignores any
   characters that cannot be interpreted as part of the
   notation of a decimal literal, and no indication is given
   that such characters were ignored.

   StrDecimalLiteral::
   Infinity
   DecimalDigits.DecimalDigits opt ExponentPart opt
   .DecimalDigits ExponentPart opt
   DecimalDigits ExponentPart opt

   Author:             christine@netscape.com
   Date:               28 october 1997

*/
test_15_1_2_3__2:function(){

var SECTION = "15.1.2.3-2";
var VERSION = "ECMA_1";
// startTest();

var BUGNUMBER="none";

this.TestCase( SECTION, "parseFloat(true)",      Number.NaN,     parseFloat(true) );
this.TestCase( SECTION, "parseFloat(false)",     Number.NaN,     parseFloat(false) );
this.TestCase( SECTION, "parseFloat('string')",  Number.NaN,     parseFloat("string") );

this.TestCase( SECTION, "parseFloat('     Infinity')",      Number.POSITIVE_INFINITY,    parseFloat("Infinity") );
//     this.TestCase( SECTION, "parseFloat(Infinity)",      Number.POSITIVE_INFINITY,    parseFloat(Infinity) );

this.TestCase( SECTION,  "parseFloat('          0')",          0,          parseFloat("          0") );
this.TestCase( SECTION,  "parseFloat('          -0')",         -0,         parseFloat("          -0") );
this.TestCase( SECTION,  "parseFloat('          +0')",          0,         parseFloat("          +0") );

this.TestCase( SECTION,  "parseFloat('          1')",          1,          parseFloat("          1") );
this.TestCase( SECTION,  "parseFloat('          -1')",         -1,         parseFloat("          -1") );
this.TestCase( SECTION,  "parseFloat('          +1')",          1,         parseFloat("          +1") );

this.TestCase( SECTION,  "parseFloat('          2')",          2,          parseFloat("          2") );
this.TestCase( SECTION,  "parseFloat('          -2')",         -2,         parseFloat("          -2") );
this.TestCase( SECTION,  "parseFloat('          +2')",          2,         parseFloat("          +2") );

this.TestCase( SECTION,  "parseFloat('          3')",          3,          parseFloat("          3") );
this.TestCase( SECTION,  "parseFloat('          -3')",         -3,         parseFloat("          -3") );
this.TestCase( SECTION,  "parseFloat('          +3')",          3,         parseFloat("          +3") );

this.TestCase( SECTION,  "parseFloat('          4')",          4,          parseFloat("          4") );
this.TestCase( SECTION,  "parseFloat('          -4')",         -4,         parseFloat("          -4") );
this.TestCase( SECTION,  "parseFloat('          +4')",          4,         parseFloat("          +4") );

this.TestCase( SECTION,  "parseFloat('          5')",          5,          parseFloat("          5") );
this.TestCase( SECTION,  "parseFloat('          -5')",         -5,         parseFloat("          -5") );
this.TestCase( SECTION,  "parseFloat('          +5')",          5,         parseFloat("          +5") );

this.TestCase( SECTION,  "parseFloat('          6')",          6,          parseFloat("          6") );
this.TestCase( SECTION,  "parseFloat('          -6')",         -6,         parseFloat("          -6") );
this.TestCase( SECTION,  "parseFloat('          +6')",          6,         parseFloat("          +6") );

this.TestCase( SECTION,  "parseFloat('          7')",          7,          parseFloat("          7") );
this.TestCase( SECTION,  "parseFloat('          -7')",         -7,         parseFloat("          -7") );
this.TestCase( SECTION,  "parseFloat('          +7')",          7,         parseFloat("          +7") );

this.TestCase( SECTION,  "parseFloat('          8')",          8,          parseFloat("          8") );
this.TestCase( SECTION,  "parseFloat('          -8')",         -8,         parseFloat("          -8") );
this.TestCase( SECTION,  "parseFloat('          +8')",          8,         parseFloat("          +8") );

this.TestCase( SECTION,  "parseFloat('          9')",          9,          parseFloat("          9") );
this.TestCase( SECTION,  "parseFloat('          -9')",         -9,         parseFloat("          -9") );
this.TestCase( SECTION,  "parseFloat('          +9')",          9,         parseFloat("          +9") );

this.TestCase( SECTION,  "parseFloat('          3.14159')",    3.14159,    parseFloat("          3.14159") );
this.TestCase( SECTION,  "parseFloat('          -3.14159')",   -3.14159,   parseFloat("          -3.14159") );
this.TestCase( SECTION,  "parseFloat('          +3.14159')",   3.14159,    parseFloat("          +3.14159") );

this.TestCase( SECTION,  "parseFloat('          3.')",         3,          parseFloat("          3.") );
this.TestCase( SECTION,  "parseFloat('          -3.')",        -3,         parseFloat("          -3.") );
this.TestCase( SECTION,  "parseFloat('          +3.')",        3,          parseFloat("          +3.") );

this.TestCase( SECTION,  "parseFloat('          3.e1')",       30,         parseFloat("          3.e1") );
this.TestCase( SECTION,  "parseFloat('          -3.e1')",      -30,        parseFloat("          -3.e1") );
this.TestCase( SECTION,  "parseFloat('          +3.e1')",      30,         parseFloat("          +3.e1") );

this.TestCase( SECTION,  "parseFloat('          3.e+1')",       30,         parseFloat("          3.e+1") );
this.TestCase( SECTION,  "parseFloat('          -3.e+1')",      -30,        parseFloat("          -3.e+1") );
this.TestCase( SECTION,  "parseFloat('          +3.e+1')",      30,         parseFloat("          +3.e+1") );

this.TestCase( SECTION,  "parseFloat('          3.e-1')",       .30,         parseFloat("          3.e-1") );
this.TestCase( SECTION,  "parseFloat('          -3.e-1')",      -.30,        parseFloat("          -3.e-1") );
this.TestCase( SECTION,  "parseFloat('          +3.e-1')",      .30,         parseFloat("          +3.e-1") );

// StrDecimalLiteral:::  .DecimalDigits ExponentPart opt

this.TestCase( SECTION,  "parseFloat('          .00001')",     0.00001,    parseFloat("          .00001") );
this.TestCase( SECTION,  "parseFloat('          +.00001')",    0.00001,    parseFloat("          +.00001") );
this.TestCase( SECTION,  "parseFloat('          -0.0001')",    -0.00001,   parseFloat("          -.00001") );

this.TestCase( SECTION,  "parseFloat('          .01e2')",      1,          parseFloat("          .01e2") );
this.TestCase( SECTION,  "parseFloat('          +.01e2')",     1,          parseFloat("          +.01e2") );
this.TestCase( SECTION,  "parseFloat('          -.01e2')",     -1,         parseFloat("          -.01e2") );

this.TestCase( SECTION,  "parseFloat('          .01e+2')",      1,         parseFloat("          .01e+2") );
this.TestCase( SECTION,  "parseFloat('          +.01e+2')",     1,         parseFloat("          +.01e+2") );
this.TestCase( SECTION,  "parseFloat('          -.01e+2')",     -1,        parseFloat("          -.01e+2") );

this.TestCase( SECTION,  "parseFloat('          .01e-2')",      0.0001,    parseFloat("          .01e-2") );
this.TestCase( SECTION,  "parseFloat('          +.01e-2')",     0.0001,    parseFloat("          +.01e-2") );
this.TestCase( SECTION,  "parseFloat('          -.01e-2')",     -0.0001,   parseFloat("          -.01e-2") );

//  StrDecimalLiteral:::    DecimalDigits ExponentPart opt

this.TestCase( SECTION,  "parseFloat('          1234e5')",     123400000,  parseFloat("          1234e5") );
this.TestCase( SECTION,  "parseFloat('          +1234e5')",    123400000,  parseFloat("          +1234e5") );
this.TestCase( SECTION,  "parseFloat('          -1234e5')",    -123400000, parseFloat("          -1234e5") );

this.TestCase( SECTION,  "parseFloat('          1234e+5')",    123400000,  parseFloat("          1234e+5") );
this.TestCase( SECTION,  "parseFloat('          +1234e+5')",   123400000,  parseFloat("          +1234e+5") );
this.TestCase( SECTION,  "parseFloat('          -1234e+5')",   -123400000, parseFloat("          -1234e+5") );

this.TestCase( SECTION,  "parseFloat('          1234e-5')",     0.01234,  parseFloat("          1234e-5") );
this.TestCase( SECTION,  "parseFloat('          +1234e-5')",    0.01234,  parseFloat("          +1234e-5") );
this.TestCase( SECTION,  "parseFloat('          -1234e-5')",    -0.01234, parseFloat("          -1234e-5") );


this.TestCase( SECTION,  "parseFloat('          .01E2')",      1,          parseFloat("          .01E2") );
this.TestCase( SECTION,  "parseFloat('          +.01E2')",     1,          parseFloat("          +.01E2") );
this.TestCase( SECTION,  "parseFloat('          -.01E2')",     -1,         parseFloat("          -.01E2") );

this.TestCase( SECTION,  "parseFloat('          .01E+2')",      1,         parseFloat("          .01E+2") );
this.TestCase( SECTION,  "parseFloat('          +.01E+2')",     1,         parseFloat("          +.01E+2") );
this.TestCase( SECTION,  "parseFloat('          -.01E+2')",     -1,        parseFloat("          -.01E+2") );

this.TestCase( SECTION,  "parseFloat('          .01E-2')",      0.0001,    parseFloat("          .01E-2") );
this.TestCase( SECTION,  "parseFloat('          +.01E-2')",     0.0001,    parseFloat("          +.01E-2") );
this.TestCase( SECTION,  "parseFloat('          -.01E-2')",     -0.0001,   parseFloat("          -.01E-2") );

//  StrDecimalLiteral:::    DecimalDigits ExponentPart opt
this.TestCase( SECTION,  "parseFloat('          1234E5')",     123400000,  parseFloat("          1234E5") );
this.TestCase( SECTION,  "parseFloat('          +1234E5')",    123400000,  parseFloat("          +1234E5") );
this.TestCase( SECTION,  "parseFloat('          -1234E5')",    -123400000, parseFloat("          -1234E5") );

this.TestCase( SECTION,  "parseFloat('          1234E+5')",    123400000,  parseFloat("          1234E+5") );
this.TestCase( SECTION,  "parseFloat('          +1234E+5')",   123400000,  parseFloat("          +1234E+5") );
this.TestCase( SECTION,  "parseFloat('          -1234E+5')",   -123400000, parseFloat("          -1234E+5") );

this.TestCase( SECTION,  "parseFloat('          1234E-5')",     0.01234,  parseFloat("          1234E-5") );
this.TestCase( SECTION,  "parseFloat('          +1234E-5')",    0.01234,  parseFloat("          +1234E-5") );
this.TestCase( SECTION,  "parseFloat('          -1234E-5')",    -0.01234, parseFloat("          -1234E-5") );


// hex cases should all return NaN

this.TestCase( SECTION,  "parseFloat('          0x0')",        0,         parseFloat("          0x0"));
this.TestCase( SECTION,  "parseFloat('          0x1')",        0,         parseFloat("          0x1"));
this.TestCase( SECTION,  "parseFloat('          0x2')",        0,         parseFloat("          0x2"));
this.TestCase( SECTION,  "parseFloat('          0x3')",        0,         parseFloat("          0x3"));
this.TestCase( SECTION,  "parseFloat('          0x4')",        0,         parseFloat("          0x4"));
this.TestCase( SECTION,  "parseFloat('          0x5')",        0,         parseFloat("          0x5"));
this.TestCase( SECTION,  "parseFloat('          0x6')",        0,         parseFloat("          0x6"));
this.TestCase( SECTION,  "parseFloat('          0x7')",        0,         parseFloat("          0x7"));
this.TestCase( SECTION,  "parseFloat('          0x8')",        0,         parseFloat("          0x8"));
this.TestCase( SECTION,  "parseFloat('          0x9')",        0,         parseFloat("          0x9"));
this.TestCase( SECTION,  "parseFloat('          0xa')",        0,         parseFloat("          0xa"));
this.TestCase( SECTION,  "parseFloat('          0xb')",        0,         parseFloat("          0xb"));
this.TestCase( SECTION,  "parseFloat('          0xc')",        0,         parseFloat("          0xc"));
this.TestCase( SECTION,  "parseFloat('          0xd')",        0,         parseFloat("          0xd"));
this.TestCase( SECTION,  "parseFloat('          0xe')",        0,         parseFloat("          0xe"));
this.TestCase( SECTION,  "parseFloat('          0xf')",        0,         parseFloat("          0xf"));
this.TestCase( SECTION,  "parseFloat('          0xA')",        0,         parseFloat("          0xA"));
this.TestCase( SECTION,  "parseFloat('          0xB')",        0,         parseFloat("          0xB"));
this.TestCase( SECTION,  "parseFloat('          0xC')",        0,         parseFloat("          0xC"));
this.TestCase( SECTION,  "parseFloat('          0xD')",        0,         parseFloat("          0xD"));
this.TestCase( SECTION,  "parseFloat('          0xE')",        0,         parseFloat("          0xE"));
this.TestCase( SECTION,  "parseFloat('          0xF')",        0,         parseFloat("          0xF"));

this.TestCase( SECTION,  "parseFloat('          0X0')",        0,         parseFloat("          0X0"));
this.TestCase( SECTION,  "parseFloat('          0X1')",        0,         parseFloat("          0X1"));
this.TestCase( SECTION,  "parseFloat('          0X2')",        0,         parseFloat("          0X2"));
this.TestCase( SECTION,  "parseFloat('          0X3')",        0,         parseFloat("          0X3"));
this.TestCase( SECTION,  "parseFloat('          0X4')",        0,         parseFloat("          0X4"));
this.TestCase( SECTION,  "parseFloat('          0X5')",        0,         parseFloat("          0X5"));
this.TestCase( SECTION,  "parseFloat('          0X6')",        0,         parseFloat("          0X6"));
this.TestCase( SECTION,  "parseFloat('          0X7')",        0,         parseFloat("          0X7"));
this.TestCase( SECTION,  "parseFloat('          0X8')",        0,         parseFloat("          0X8"));
this.TestCase( SECTION,  "parseFloat('          0X9')",        0,         parseFloat("          0X9"));
this.TestCase( SECTION,  "parseFloat('          0Xa')",        0,         parseFloat("          0Xa"));
this.TestCase( SECTION,  "parseFloat('          0Xb')",        0,         parseFloat("          0Xb"));
this.TestCase( SECTION,  "parseFloat('          0Xc')",        0,         parseFloat("          0Xc"));
this.TestCase( SECTION,  "parseFloat('          0Xd')",        0,         parseFloat("          0Xd"));
this.TestCase( SECTION,  "parseFloat('          0Xe')",        0,         parseFloat("          0Xe"));
this.TestCase( SECTION,  "parseFloat('          0Xf')",        0,         parseFloat("          0Xf"));
this.TestCase( SECTION,  "parseFloat('          0XA')",        0,         parseFloat("          0XA"));
this.TestCase( SECTION,  "parseFloat('          0XB')",        0,         parseFloat("          0XB"));
this.TestCase( SECTION,  "parseFloat('          0XC')",        0,         parseFloat("          0XC"));
this.TestCase( SECTION,  "parseFloat('          0XD')",        0,         parseFloat("          0XD"));
this.TestCase( SECTION,  "parseFloat('          0XE')",        0,         parseFloat("          0XE"));
this.TestCase( SECTION,  "parseFloat('          0XF')",        0,         parseFloat("          0XF"));

// A StringNumericLiteral may not use octal notation

this.TestCase( SECTION,  "parseFloat('          00')",        0,         parseFloat("          00"));
this.TestCase( SECTION,  "parseFloat('          01')",        1,         parseFloat("          01"));
this.TestCase( SECTION,  "parseFloat('          02')",        2,         parseFloat("          02"));
this.TestCase( SECTION,  "parseFloat('          03')",        3,         parseFloat("          03"));
this.TestCase( SECTION,  "parseFloat('          04')",        4,         parseFloat("          04"));
this.TestCase( SECTION,  "parseFloat('          05')",        5,         parseFloat("          05"));
this.TestCase( SECTION,  "parseFloat('          06')",        6,         parseFloat("          06"));
this.TestCase( SECTION,  "parseFloat('          07')",        7,         parseFloat("          07"));
this.TestCase( SECTION,  "parseFloat('          010')",       10,        parseFloat("          010"));
this.TestCase( SECTION,  "parseFloat('          011')",       11,        parseFloat("          011"));

// A StringNumericLIteral may have any number of leading 0 digits

this.TestCase( SECTION,  "parseFloat('          001')",        1,         parseFloat("          001"));
this.TestCase( SECTION,  "parseFloat('          0001')",       1,         parseFloat("          0001"));

// A StringNumericLIteral may have any number of leading 0 digits

this.TestCase( SECTION,  "parseFloat(001)",        1,         parseFloat(001));
this.TestCase( SECTION,  "parseFloat(0001)",       1,         parseFloat(0001));

// make sure it'          s reflexive
this.TestCase( SECTION,  "parseFloat( '                    '          +Math.PI+'          ')",      Math.PI,        parseFloat( '                    '          +Math.PI+'          '));
this.TestCase( SECTION,  "parseFloat( '                    '          +Math.LN2+'          ')",     Math.LN2,       parseFloat( '                    '          +Math.LN2+'          '));
this.TestCase( SECTION,  "parseFloat( '                    '          +Math.LN10+'          ')",    Math.LN10,      parseFloat( '                    '          +Math.LN10+'          '));
this.TestCase( SECTION,  "parseFloat( '                    '          +Math.LOG2E+'          ')",   Math.LOG2E,     parseFloat( '                    '          +Math.LOG2E+'          '));
this.TestCase( SECTION,  "parseFloat( '                    '          +Math.LOG10E+'          ')",  Math.LOG10E,    parseFloat( '                    '          +Math.LOG10E+'          '));
this.TestCase( SECTION,  "parseFloat( '                    '          +Math.SQRT2+'          ')",   Math.SQRT2,     parseFloat( '                    '          +Math.SQRT2+'          '));
this.TestCase( SECTION,  "parseFloat( '                    '          +Math.SQRT1_2+'          ')", Math.SQRT1_2,   parseFloat( '                    '          +Math.SQRT1_2+'          '));

// test();

},

/**
   File Name:          15.1.2.4.js
   ECMA Section:       15.1.2.4  Function properties of the global object
   escape( string )

   Description:
   The escape function computes a new version of a string value in which
   certain characters have been replaced by a hexadecimal escape sequence.
   The result thus contains no special characters that might have special
   meaning within a URL.

   For characters whose Unicode encoding is 0xFF or less, a two-digit
   escape sequence of the form %xx is used in accordance with RFC1738.
   For characters whose Unicode encoding is greater than 0xFF, a four-
   digit escape sequence of the form %uxxxx is used.

   When the escape function is called with one argument string, the
   following steps are taken:

   1.  Call ToString(string).
   2.  Compute the number of characters in Result(1).
   3.  Let R be the empty string.
   4.  Let k be 0.
   5.  If k equals Result(2), return R.
   6.  Get the character at position k within Result(1).
   7.  If Result(6) is one of the 69 nonblank ASCII characters
   ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz
   0123456789 @*_+-./, go to step 14.
   8.  Compute the 16-bit unsigned integer that is the Unicode character
   encoding of Result(6).
   9.  If Result(8), is less than 256, go to step 12.
   10.  Let S be a string containing six characters "%uwxyz" where wxyz are
   four hexadecimal digits encoding the value of Result(8).
   11.  Go to step 15.
   12.  Let S be a string containing three characters "%xy" where xy are two
   hexadecimal digits encoding the value of Result(8).
   13.  Go to step 15.
   14.  Let S be a string containing the single character Result(6).
   15.  Let R be a new string value computed by concatenating the previous value
   of R and S.
   16.  Increase k by 1.
   17.  Go to step 5.

   Author:             christine@netscape.com
   Date:               28 october 1997

*/
test_15_1_2_4:function(){

var SECTION = "15.1.2.4";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "escape(string)";

// writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION, "escape.length",         1,          escape.length );
this.TestCase( SECTION, "escape.length = null; escape.length",   1,  eval("escape.length = null; escape.length") );
this.TestCase( SECTION, "delete escape.length",                  false,  delete escape.length );
this.TestCase( SECTION, "delete escape.length; escape.length",   1,      eval("delete escape.length; escape.length") );
this.TestCase( SECTION, "var MYPROPS=''; for ( var p in escape ) { MYPROPS+= p}; MYPROPS",    "prototype",    eval("var MYPROPS=''; for ( var p in escape ) { MYPROPS+= p}; MYPROPS") );

this.TestCase( SECTION, "escape()",              "undefined",    escape() );
this.TestCase( SECTION, "escape('')",            "",             escape('') );
this.TestCase( SECTION, "escape( null )",        "null",         escape(null) );
this.TestCase( SECTION, "escape( void 0 )",      "undefined",    escape(void 0) );
this.TestCase( SECTION, "escape( true )",        "true",         escape( true ) );
this.TestCase( SECTION, "escape( false )",       "false",        escape( false ) );

this.TestCase( SECTION, "escape( new Boolean(true) )",   "true", escape(new Boolean(true)) );
this.TestCase( SECTION, "escape( new Boolean(false) )",  "false",    escape(new Boolean(false)) );

this.TestCase( SECTION, "escape( Number.NaN  )",                 "NaN",      escape(Number.NaN) );
this.TestCase( SECTION, "escape( -0 )",                          "0",        escape( -0 ) );
this.TestCase( SECTION, "escape( 'Infinity' )",                  "Infinity", escape( "Infinity" ) );
this.TestCase( SECTION, "escape( Number.POSITIVE_INFINITY )",    "Infinity", escape( Number.POSITIVE_INFINITY ) );
this.TestCase( SECTION, "escape( Number.NEGATIVE_INFINITY )",    "-Infinity", escape( Number.NEGATIVE_INFINITY ) );

var ASCII_TEST_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@*_+-./";

this.TestCase( SECTION, "escape( " +ASCII_TEST_STRING+" )",    ASCII_TEST_STRING,  escape( ASCII_TEST_STRING ) );

// ASCII value less than

for ( var CHARCODE = 0; CHARCODE < 32; CHARCODE++ ) {
  this.TestCase( SECTION,
		"escape(String.fromCharCode("+CHARCODE+"))",
		"%"+ToHexString(CHARCODE),
		escape(String.fromCharCode(CHARCODE))  );
}
for ( var CHARCODE = 128; CHARCODE < 256; CHARCODE++ ) {
  this.TestCase( SECTION,
		"escape(String.fromCharCode("+CHARCODE+"))",
		"%"+ToHexString(CHARCODE),
		escape(String.fromCharCode(CHARCODE))  );
}

for ( var CHARCODE = 256; CHARCODE < 1024; CHARCODE++ ) {
  this.TestCase( SECTION,
		"escape(String.fromCharCode("+CHARCODE+"))",
		"%u"+ ToUnicodeString(CHARCODE),
		escape(String.fromCharCode(CHARCODE))  );
}
for ( var CHARCODE = 65500; CHARCODE < 65536; CHARCODE++ ) {
  this.TestCase( SECTION,
		"escape(String.fromCharCode("+CHARCODE+"))",
		"%u"+ ToUnicodeString(CHARCODE),
		escape(String.fromCharCode(CHARCODE))  );
}

// test();

},

/**
   File Name:          15.1.2.5-1.js
   ECMA Section:       15.1.2.5  Function properties of the global object
   unescape( string )

   Description:
   The unescape function computes a new version of a string value in which
   each escape sequences of the sort that might be introduced by the escape
   function is replaced with the character that it represents.

   When the unescape function is called with one argument string, the
   following steps are taken:

   1.  Call ToString(string).
   2.  Compute the number of characters in Result(1).
   3.  Let R be the empty string.
   4.  Let k be 0.
   5.  If k equals Result(2), return R.
   6.  Let c be the character at position k within Result(1).
   7.  If c is not %, go to step 18.
   8.  If k is greater than Result(2)-6, go to step 14.
   9.  If the character at position k+1 within result(1) is not u, go to step
   14.
   10. If the four characters at positions k+2, k+3, k+4, and k+5 within
   Result(1) are not all hexadecimal digits, go to step 14.
   11. Let c be the character whose Unicode encoding is the integer represented
   by the four hexadecimal digits at positions k+2, k+3, k+4, and k+5
   within Result(1).
   12. Increase k by 5.
   13. Go to step 18.
   14. If k is greater than Result(2)-3, go to step 18.
   15. If the two characters at positions k+1 and k+2 within Result(1) are not
   both hexadecimal digits, go to step 18.
   16. Let c be the character whose Unicode encoding is the integer represented
   by two zeroes plus the two hexadecimal digits at positions k+1 and k+2
   within Result(1).
   17. Increase k by 2.
   18. Let R be a new string value computed by concatenating the previous value
   of R and c.
   19. Increase k by 1.
   20. Go to step 5.
   Author:             christine@netscape.com
   Date:               28 october 1997
*/
test_15_1_2_5__1:function(){


var SECTION = "15.1.2.5-1";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "unescape(string)";

// writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION, "unescape.length",       1,               unescape.length );
this.TestCase( SECTION, "unescape.length = null; unescape.length",   1,      eval("unescape.length=null; unescape.length") );
this.TestCase( SECTION, "delete unescape.length",                    false,  delete unescape.length );
this.TestCase( SECTION, "delete unescape.length; unescape.length",   1,      eval("delete unescape.length; unescape.length") );
this.TestCase( SECTION, "var MYPROPS=''; for ( var p in unescape ) { MYPROPS+= p }; MYPROPS",    "prototype", eval("var MYPROPS=''; for ( var p in unescape ) { MYPROPS+= p }; MYPROPS") );

this.TestCase( SECTION, "unescape()",              "undefined",    unescape() );
this.TestCase( SECTION, "unescape('')",            "",             unescape('') );
this.TestCase( SECTION, "unescape( null )",        "null",         unescape(null) );
this.TestCase( SECTION, "unescape( void 0 )",      "undefined",    unescape(void 0) );
this.TestCase( SECTION, "unescape( true )",        "true",         unescape( true ) );
this.TestCase( SECTION, "unescape( false )",       "false",        unescape( false ) );

this.TestCase( SECTION, "unescape( new Boolean(true) )",   "true", unescape(new Boolean(true)) );
this.TestCase( SECTION, "unescape( new Boolean(false) )",  "false",    unescape(new Boolean(false)) );

this.TestCase( SECTION, "unescape( Number.NaN  )",                 "NaN",      unescape(Number.NaN) );
this.TestCase( SECTION, "unescape( -0 )",                          "0",        unescape( -0 ) );
this.TestCase( SECTION, "unescape( 'Infinity' )",                  "Infinity", unescape( "Infinity" ) );
this.TestCase( SECTION, "unescape( Number.POSITIVE_INFINITY )",    "Infinity", unescape( Number.POSITIVE_INFINITY ) );
this.TestCase( SECTION, "unescape( Number.NEGATIVE_INFINITY )",    "-Infinity", unescape( Number.NEGATIVE_INFINITY ) );

var ASCII_TEST_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@*_+-./";

this.TestCase( SECTION, "unescape( " +ASCII_TEST_STRING+" )",    ASCII_TEST_STRING,  unescape( ASCII_TEST_STRING ) );

// escaped chars with ascii values less than 256

for ( var CHARCODE = 0; CHARCODE < 256; CHARCODE++ ) {
  this.TestCase( SECTION,
		"unescape( %"+ ToHexString(CHARCODE)+" )",
		String.fromCharCode(CHARCODE),
		unescape( "%" + ToHexString(CHARCODE) )  );
}

// unicode chars represented by two hex digits
for ( var CHARCODE = 0; CHARCODE < 256; CHARCODE++ ) {
  this.TestCase( SECTION,
		"unescape( %u"+ ToHexString(CHARCODE)+" )",
		"%u"+ToHexString(CHARCODE),
		unescape( "%u" + ToHexString(CHARCODE) )  );
}
/*
  for ( var CHARCODE = 0; CHARCODE < 256; CHARCODE++ ) {
  this.TestCase( SECTION,
  "unescape( %u"+ ToUnicodeString(CHARCODE)+" )",
  String.fromCharCode(CHARCODE),
  unescape( "%u" + ToUnicodeString(CHARCODE) )  );
  }
  for ( var CHARCODE = 256; CHARCODE < 65536; CHARCODE+= 333 ) {
  this.TestCase( SECTION,
  "unescape( %u"+ ToUnicodeString(CHARCODE)+" )",
  String.fromCharCode(CHARCODE),
  unescape( "%u" + ToUnicodeString(CHARCODE) )  );
  }
*/



// test();

},

/**
   File Name:          15.1.2.5-2.js
   ECMA Section:       15.1.2.5  Function properties of the global object
   unescape( string )
   Description:

   This tests the cases where there are fewer than 4 characters following "%u",
   or fewer than 2 characters following "%" or "%u".

   The unescape function computes a new version of a string value in which
   each escape sequences of the sort that might be introduced by the escape
   function is replaced with the character that it represents.

   When the unescape function is called with one argument string, the
   following steps are taken:

   1.  Call ToString(string).
   2.  Compute the number of characters in Result(1).
   3.  Let R be the empty string.
   4.  Let k be 0.
   5.  If k equals Result(2), return R.
   6.  Let c be the character at position k within Result(1).
   7.  If c is not %, go to step 18.
   8.  If k is greater than Result(2)-6, go to step 14.
   9.  If the character at position k+1 within result(1) is not u, go to step
   14.
   10. If the four characters at positions k+2, k+3, k+4, and k+5 within
   Result(1) are not all hexadecimal digits, go to step 14.
   11. Let c be the character whose Unicode encoding is the integer represented
   by the four hexadecimal digits at positions k+2, k+3, k+4, and k+5
   within Result(1).
   12. Increase k by 5.
   13. Go to step 18.
   14. If k is greater than Result(2)-3, go to step 18.
   15. If the two characters at positions k+1 and k+2 within Result(1) are not
   both hexadecimal digits, go to step 18.
   16. Let c be the character whose Unicode encoding is the integer represented
   by two zeroes plus the two hexadecimal digits at positions k+1 and k+2
   within Result(1).
   17. Increase k by 2.
   18. Let R be a new string value computed by concatenating the previous value
   of R and c.
   19. Increase k by 1.
   20. Go to step 5.
   Author:             christine@netscape.com
   Date:               28 october 1997
*/
test_15_1_2_5__2:function(){


var SECTION = "15.1.2.5-2";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "unescape(string)";

// writeHeaderToLog( SECTION + " "+ TITLE);

// since there is only one character following "%", no conversion should occur.

for ( var CHARCODE = 0; CHARCODE < 256; CHARCODE += 16 ) {
  this.TestCase( SECTION,
		"unescape( %"+ (ToHexString(CHARCODE)).substring(0,1) +" )",
		"%"+(ToHexString(CHARCODE)).substring(0,1),
		unescape( "%" + (ToHexString(CHARCODE)).substring(0,1) )  );
}

// since there is only one character following "%u", no conversion should occur.

for ( var CHARCODE = 0; CHARCODE < 256; CHARCODE +=16 ) {
  this.TestCase( SECTION,
		"unescape( %u"+ (ToHexString(CHARCODE)).substring(0,1) +" )",
		"%u"+(ToHexString(CHARCODE)).substring(0,1),
		unescape( "%u" + (ToHexString(CHARCODE)).substring(0,1) )  );
}


// three char unicode string.  no conversion should occur

for ( var CHARCODE = 1024; CHARCODE < 65536; CHARCODE+= 1234 ) {
  this.TestCase
    (   SECTION,
	"unescape( %u"+ (ToUnicodeString(CHARCODE)).substring(0,3)+ " )",

	"%u"+(ToUnicodeString(CHARCODE)).substring(0,3),
	unescape( "%u"+(ToUnicodeString(CHARCODE)).substring(0,3) )
      );
}

// test();

},

/**
   File Name:          15.1.2.5-3.js
   ECMA Section:       15.1.2.5  Function properties of the global object
   unescape( string )

   Description:
   This tests the cases where one of the four characters following "%u" is
   not a hexidecimal character, or one of the two characters following "%"
   or "%u" is not a hexidecimal character.

   The unescape function computes a new version of a string value in which
   each escape sequences of the sort that might be introduced by the escape
   function is replaced with the character that it represents.

   When the unescape function is called with one argument string, the
   following steps are taken:

   1.  Call ToString(string).
   2.  Compute the number of characters in Result(1).
   3.  Let R be the empty string.
   4.  Let k be 0.
   5.  If k equals Result(2), return R.
   6.  Let c be the character at position k within Result(1).
   7.  If c is not %, go to step 18.
   8.  If k is greater than Result(2)-6, go to step 14.
   9.  If the character at position k+1 within result(1) is not u, go to step
   14.
   10. If the four characters at positions k+2, k+3, k+4, and k+5 within
   Result(1) are not all hexadecimal digits, go to step 14.
   11. Let c be the character whose Unicode encoding is the integer represented
   by the four hexadecimal digits at positions k+2, k+3, k+4, and k+5
   within Result(1).
   12. Increase k by 5.
   13. Go to step 18.
   14. If k is greater than Result(2)-3, go to step 18.
   15. If the two characters at positions k+1 and k+2 within Result(1) are not
   both hexadecimal digits, go to step 18.
   16. Let c be the character whose Unicode encoding is the integer represented
   by two zeroes plus the two hexadecimal digits at positions k+1 and k+2
   within Result(1).
   17. Increase k by 2.
   18. Let R be a new string value computed by concatenating the previous value
   of R and c.
   19. Increase k by 1.
   20. Go to step 5.
   Author:             christine@netscape.com
   Date:               28 october 1997
*/
test_15_1_2_5__3:function(){



var SECTION = "15.1.2.5-3";
var VERSION = "ECMA_1";
// startTest();
var TITLE   = "unescape(string)";

// writeHeaderToLog( SECTION + " "+ TITLE);

for ( var CHARCODE = 0, NONHEXCHARCODE = 0; CHARCODE < 256; CHARCODE++, NONHEXCHARCODE++ ) {
  NONHEXCHARCODE = getNextNonHexCharCode( NONHEXCHARCODE );

  this.TestCase( SECTION,
		"unescape( %"+ (ToHexString(CHARCODE)).substring(0,1) +
		String.fromCharCode( NONHEXCHARCODE ) +" )" +
		"[where last character is String.fromCharCode("+NONHEXCHARCODE+")]",
		"%"+(ToHexString(CHARCODE)).substring(0,1)+
		String.fromCharCode( NONHEXCHARCODE ),
		unescape( "%" + (ToHexString(CHARCODE)).substring(0,1)+
			  String.fromCharCode( NONHEXCHARCODE ) )  );
}
for ( var CHARCODE = 0, NONHEXCHARCODE = 0; CHARCODE < 256; CHARCODE++, NONHEXCHARCODE++ ) {
  NONHEXCHARCODE = getNextNonHexCharCode( NONHEXCHARCODE );

  this.TestCase( SECTION,
		"unescape( %u"+ (ToHexString(CHARCODE)).substring(0,1) +
		String.fromCharCode( NONHEXCHARCODE ) +" )" +
		"[where last character is String.fromCharCode("+NONHEXCHARCODE+")]",
		"%u"+(ToHexString(CHARCODE)).substring(0,1)+
		String.fromCharCode( NONHEXCHARCODE ),
		unescape( "%u" + (ToHexString(CHARCODE)).substring(0,1)+
			  String.fromCharCode( NONHEXCHARCODE ) )  );
}

for ( var CHARCODE = 0, NONHEXCHARCODE = 0 ; CHARCODE < 65536; CHARCODE+= 54321, NONHEXCHARCODE++ ) {
  NONHEXCHARCODE = getNextNonHexCharCode( NONHEXCHARCODE );

  this.TestCase( SECTION,
		"unescape( %u"+ (ToUnicodeString(CHARCODE)).substring(0,3) +
		String.fromCharCode( NONHEXCHARCODE ) +" )" +
		"[where last character is String.fromCharCode("+NONHEXCHARCODE+")]",

		String.fromCharCode(eval("0x"+ (ToUnicodeString(CHARCODE)).substring(0,2))) +
		(ToUnicodeString(CHARCODE)).substring(2,3) +
		String.fromCharCode( NONHEXCHARCODE ),

		unescape( "%" + (ToUnicodeString(CHARCODE)).substring(0,3)+
			  String.fromCharCode( NONHEXCHARCODE ) )  );
}

// test();


},

/**
   File Name:          15.1.2.6.js
   ECMA Section:       15.1.2.6 isNaN( x )

   Description:        Applies ToNumber to its argument, then returns true if
   the result isNaN and otherwise returns false.

   Author:             christine@netscape.com
   Date:               28 october 1997

*/
test_15_1_2_6:function(){

var SECTION = "15.1.2.6";
var VERSION = "ECMA_1";
var TITLE   = "isNaN( x )";
var BUGNUMBER = "none";

// startTest();

// writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION, "isNaN.length",      1,                  isNaN.length );
this.TestCase( SECTION, "var MYPROPS=''; for ( var p in isNaN ) { MYPROPS+= p }; MYPROPS", "prototype", eval("var MYPROPS=''; for ( var p in isNaN ) { MYPROPS+= p }; MYPROPS") );
this.TestCase( SECTION, "isNaN.length = null; isNaN.length", 1,      eval("isNaN.length=null; isNaN.length") );
this.TestCase( SECTION, "delete isNaN.length",               false,  delete isNaN.length );
this.TestCase( SECTION, "delete isNaN.length; isNaN.length", 1,      eval("delete isNaN.length; isNaN.length") );

//     this.TestCase( SECTION, "isNaN.__proto__",   Function.prototype, isNaN.__proto__ );

this.TestCase( SECTION, "isNaN()",           true,               isNaN() );
this.TestCase( SECTION, "isNaN( null )",     false,              isNaN(null) );
this.TestCase( SECTION, "isNaN( void 0 )",   true,               isNaN(void 0) );
this.TestCase( SECTION, "isNaN( true )",     false,              isNaN(true) );
this.TestCase( SECTION, "isNaN( false)",     false,              isNaN(false) );
this.TestCase( SECTION, "isNaN( ' ' )",      false,              isNaN( " " ) );

this.TestCase( SECTION, "isNaN( 0 )",        false,              isNaN(0) );
this.TestCase( SECTION, "isNaN( 1 )",        false,              isNaN(1) );
this.TestCase( SECTION, "isNaN( 2 )",        false,              isNaN(2) );
this.TestCase( SECTION, "isNaN( 3 )",        false,              isNaN(3) );
this.TestCase( SECTION, "isNaN( 4 )",        false,              isNaN(4) );
this.TestCase( SECTION, "isNaN( 5 )",        false,              isNaN(5) );
this.TestCase( SECTION, "isNaN( 6 )",        false,              isNaN(6) );
this.TestCase( SECTION, "isNaN( 7 )",        false,              isNaN(7) );
this.TestCase( SECTION, "isNaN( 8 )",        false,              isNaN(8) );
this.TestCase( SECTION, "isNaN( 9 )",        false,              isNaN(9) );

this.TestCase( SECTION, "isNaN( '0' )",        false,              isNaN('0') );
this.TestCase( SECTION, "isNaN( '1' )",        false,              isNaN('1') );
this.TestCase( SECTION, "isNaN( '2' )",        false,              isNaN('2') );
this.TestCase( SECTION, "isNaN( '3' )",        false,              isNaN('3') );
this.TestCase( SECTION, "isNaN( '4' )",        false,              isNaN('4') );
this.TestCase( SECTION, "isNaN( '5' )",        false,              isNaN('5') );
this.TestCase( SECTION, "isNaN( '6' )",        false,              isNaN('6') );
this.TestCase( SECTION, "isNaN( '7' )",        false,              isNaN('7') );
this.TestCase( SECTION, "isNaN( '8' )",        false,              isNaN('8') );
this.TestCase( SECTION, "isNaN( '9' )",        false,              isNaN('9') );


this.TestCase( SECTION, "isNaN( 0x0a )",    false,              isNaN( 0x0a ) );
this.TestCase( SECTION, "isNaN( 0xaa )",    false,              isNaN( 0xaa ) );
this.TestCase( SECTION, "isNaN( 0x0A )",    false,              isNaN( 0x0A ) );
this.TestCase( SECTION, "isNaN( 0xAA )",    false,              isNaN( 0xAA ) );

this.TestCase( SECTION, "isNaN( '0x0a' )",    false,              isNaN( "0x0a" ) );
this.TestCase( SECTION, "isNaN( '0xaa' )",    false,              isNaN( "0xaa" ) );
this.TestCase( SECTION, "isNaN( '0x0A' )",    false,              isNaN( "0x0A" ) );
this.TestCase( SECTION, "isNaN( '0xAA' )",    false,              isNaN( "0xAA" ) );

this.TestCase( SECTION, "isNaN( 077 )",      false,              isNaN( 077 ) );
this.TestCase( SECTION, "isNaN( '077' )",    false,              isNaN( "077" ) );


this.TestCase( SECTION, "isNaN( Number.NaN )",   true,              isNaN(Number.NaN) );
this.TestCase( SECTION, "isNaN( Number.POSITIVE_INFINITY )", false,  isNaN(Number.POSITIVE_INFINITY) );
this.TestCase( SECTION, "isNaN( Number.NEGATIVE_INFINITY )", false,  isNaN(Number.NEGATIVE_INFINITY) );
this.TestCase( SECTION, "isNaN( Number.MAX_VALUE )",         false,  isNaN(Number.MAX_VALUE) );
this.TestCase( SECTION, "isNaN( Number.MIN_VALUE )",         false,  isNaN(Number.MIN_VALUE) );

this.TestCase( SECTION, "isNaN( NaN )",               true,      isNaN(NaN) );
this.TestCase( SECTION, "isNaN( Infinity )",          false,     isNaN(Infinity) );

this.TestCase( SECTION, "isNaN( 'Infinity' )",               false,  isNaN("Infinity") );
this.TestCase( SECTION, "isNaN( '-Infinity' )",              false,  isNaN("-Infinity") );

// test();

},

/**
   File Name:          15.1.2.7.js
   ECMA Section:       15.1.2.7 isFinite(number)

   Description:        Applies ToNumber to its argument, then returns false if
   the result is NaN, Infinity, or -Infinity, and otherwise
   returns true.

   Author:             christine@netscape.com
   Date:               28 october 1997

*/
test_15_1_2_7:function(){

var SECTION = "15.1.2.7";
var VERSION = "ECMA_1";
var TITLE   = "isFinite( x )";
var BUGNUMBER= "none";

// startTest();

// writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION, "isFinite.length",      1,                  isFinite.length );
this.TestCase( SECTION, "isFinite.length = null; isFinite.length",   1,      eval("isFinite.length=null; isFinite.length") );
this.TestCase( SECTION, "delete isFinite.length",                    false,  delete isFinite.length );
this.TestCase( SECTION, "delete isFinite.length; isFinite.length",   1,      eval("delete isFinite.length; isFinite.length") );
this.TestCase( SECTION, "var MYPROPS=''; for ( p in isFinite ) { MYPROPS+= p }; MYPROPS",    "prototype", eval("var MYPROPS=''; for ( p in isFinite ) { MYPROPS += p }; MYPROPS") );

this.TestCase( SECTION,  "isFinite()",           false,              isFinite() );
this.TestCase( SECTION, "isFinite( null )",      true,              isFinite(null) );
this.TestCase( SECTION, "isFinite( void 0 )",    false,             isFinite(void 0) );
this.TestCase( SECTION, "isFinite( false )",     true,              isFinite(false) );
this.TestCase( SECTION, "isFinite( true)",       true,              isFinite(true) );
this.TestCase( SECTION, "isFinite( ' ' )",       true,              isFinite( " " ) );

this.TestCase( SECTION, "isFinite( new Boolean(true) )",     true,   isFinite(new Boolean(true)) );
this.TestCase( SECTION, "isFinite( new Boolean(false) )",    true,   isFinite(new Boolean(false)) );

this.TestCase( SECTION, "isFinite( 0 )",        true,              isFinite(0) );
this.TestCase( SECTION, "isFinite( 1 )",        true,              isFinite(1) );
this.TestCase( SECTION, "isFinite( 2 )",        true,              isFinite(2) );
this.TestCase( SECTION, "isFinite( 3 )",        true,              isFinite(3) );
this.TestCase( SECTION, "isFinite( 4 )",        true,              isFinite(4) );
this.TestCase( SECTION, "isFinite( 5 )",        true,              isFinite(5) );
this.TestCase( SECTION, "isFinite( 6 )",        true,              isFinite(6) );
this.TestCase( SECTION, "isFinite( 7 )",        true,              isFinite(7) );
this.TestCase( SECTION, "isFinite( 8 )",        true,              isFinite(8) );
this.TestCase( SECTION, "isFinite( 9 )",        true,              isFinite(9) );

this.TestCase( SECTION, "isFinite( '0' )",        true,              isFinite('0') );
this.TestCase( SECTION, "isFinite( '1' )",        true,              isFinite('1') );
this.TestCase( SECTION, "isFinite( '2' )",        true,              isFinite('2') );
this.TestCase( SECTION, "isFinite( '3' )",        true,              isFinite('3') );
this.TestCase( SECTION, "isFinite( '4' )",        true,              isFinite('4') );
this.TestCase( SECTION, "isFinite( '5' )",        true,              isFinite('5') );
this.TestCase( SECTION, "isFinite( '6' )",        true,              isFinite('6') );
this.TestCase( SECTION, "isFinite( '7' )",        true,              isFinite('7') );
this.TestCase( SECTION, "isFinite( '8' )",        true,              isFinite('8') );
this.TestCase( SECTION, "isFinite( '9' )",        true,              isFinite('9') );

this.TestCase( SECTION, "isFinite( 0x0a )",    true,                 isFinite( 0x0a ) );
this.TestCase( SECTION, "isFinite( 0xaa )",    true,                 isFinite( 0xaa ) );
this.TestCase( SECTION, "isFinite( 0x0A )",    true,                 isFinite( 0x0A ) );
this.TestCase( SECTION, "isFinite( 0xAA )",    true,                 isFinite( 0xAA ) );

this.TestCase( SECTION, "isFinite( '0x0a' )",    true,               isFinite( "0x0a" ) );
this.TestCase( SECTION, "isFinite( '0xaa' )",    true,               isFinite( "0xaa" ) );
this.TestCase( SECTION, "isFinite( '0x0A' )",    true,               isFinite( "0x0A" ) );
this.TestCase( SECTION, "isFinite( '0xAA' )",    true,               isFinite( "0xAA" ) );

this.TestCase( SECTION, "isFinite( 077 )",       true,               isFinite( 077 ) );
this.TestCase( SECTION, "isFinite( '077' )",     true,               isFinite( "077" ) );

this.TestCase( SECTION, "isFinite( new String('Infinity') )",        false,      isFinite(new String("Infinity")) );
this.TestCase( SECTION, "isFinite( new String('-Infinity') )",       false,      isFinite(new String("-Infinity")) );

this.TestCase( SECTION, "isFinite( 'Infinity' )",        false,      isFinite("Infinity") );
this.TestCase( SECTION, "isFinite( '-Infinity' )",       false,      isFinite("-Infinity") );
this.TestCase( SECTION, "isFinite( Number.POSITIVE_INFINITY )",  false,  isFinite(Number.POSITIVE_INFINITY) );
this.TestCase( SECTION, "isFinite( Number.NEGATIVE_INFINITY )",  false,  isFinite(Number.NEGATIVE_INFINITY) );
this.TestCase( SECTION, "isFinite( Number.NaN )",                false,  isFinite(Number.NaN) );

this.TestCase( SECTION, "isFinite( Infinity )",  false,  isFinite(Infinity) );
this.TestCase( SECTION, "isFinite( -Infinity )",  false,  isFinite(-Infinity) );
this.TestCase( SECTION, "isFinite( NaN )",                false,  isFinite(NaN) );


this.TestCase( SECTION, "isFinite( Number.MAX_VALUE )",          true,  isFinite(Number.MAX_VALUE) );
this.TestCase( SECTION, "isFinite( Number.MIN_VALUE )",          true,  isFinite(Number.MIN_VALUE) );

// test();

}

}}).endType()


function ToUnicodeString( n ) {
  var string = ToHexString(n);

  for ( var PAD = (4 - string.length ); PAD > 0; PAD-- ) {
    string = "0" + string;
  }

  return string;
}
function ToHexString( n ) {
  var hex = new Array();

  for ( var mag = 1; Math.pow(16,mag) <= n ; mag++ ) {
    ;
  }

  for ( index = 0, mag -= 1; mag > 0; index++, mag-- ) {
    hex[index] = Math.floor( n / Math.pow(16,mag) );
    n -= Math.pow(16,mag) * Math.floor( n/Math.pow(16,mag) );
  }

  hex[hex.length] = n % 16;

  var string ="";

  for ( var index = 0 ; index < hex.length ; index++ ) {
    switch ( hex[index] ) {
    case 10:
      string += "A";
      break;
    case 11:
      string += "B";
      break;
    case 12:
      string += "C";
      break;
    case 13:
      string += "D";
      break;
    case 14:
      string += "E";
      break;
    case 15:
      string += "F";
      break;
    default:
      string += hex[index];
    }
  }

  if ( string.length == 1 ) {
    string = "0" + string;
  }
  return string;
}
