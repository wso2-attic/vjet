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


vjo.ctype("astjst.EcmaStatementsTests")
.inherits("astjst.BaseTest")
.protos({

/**
   File Name:          12.10-1.js
   ECMA Section:       12.10 The with statement
   Description:
   WithStatement :
   with ( Expression ) Statement

   The with statement adds a computed object to the front of the scope chain
   of the current execution context, then executes a statement with this
   augmented scope chain, then restores the scope chain.

   Semantics

   The production WithStatement : with ( Expression ) Statement is evaluated
   as follows:
   1.  Evaluate Expression.
   2.  Call GetValue(Result(1)).
   3.  Call ToObject(Result(2)).
   4.  Add Result(3) to the front of the scope chain.
   5.  Evaluate Statement using the augmented scope chain from step 4.
   6.  Remove Result(3) from the front of the scope chain.
   7.  Return Result(5).

   Discussion
   Note that no matter how control leaves the embedded Statement, whether
   normally or by some form of abrupt completion, the scope chain is always
   restored to its former state.

   Author:             christine@netscape.com
   Date:               12 november 1997
*/
	test_12_10__1:function(){
	
var SECTION = "12.10-1";
var VERSION = "ECMA_1";
var TITLE   = "The with statement";


// although the scope chain changes, the this value is immutable for a given
// execution context.

this.TestCase( SECTION,
	      "with( new Number() ) { this +'' }",
	      GLOBAL,
	      eval("with( new Number() ) { this +'' }") );

// the object's functions and properties should override those of the
// global object.

this.TestCase(
  SECTION,
  "var MYOB = new WithObject(true); with (MYOB) { parseInt() }",
  true,
  eval("var MYOB = new WithObject(true); with (MYOB) { parseInt() }") );

this.TestCase(
  SECTION,
  "var MYOB = new WithObject(false); with (MYOB) { NaN }",
  false,
  eval("var MYOB = new WithObject(false); with (MYOB) { NaN }") );

this.TestCase(
  SECTION,
  "var MYOB = new WithObject(NaN); with (MYOB) { Infinity }",
  Number.NaN,
  eval("var MYOB = new WithObject(NaN); with (MYOB) { Infinity }") );

this.TestCase(
  SECTION,
  "var MYOB = new WithObject(false); with (MYOB) { }; Infinity",
  Number.POSITIVE_INFINITY,
  eval("var MYOB = new WithObject(false); with (MYOB) { }; Infinity") );


this.TestCase(
  SECTION,
  "var MYOB = new WithObject(0); with (MYOB) { delete Infinity; Infinity }",
  Number.POSITIVE_INFINITY,
  eval("var MYOB = new WithObject(0); with (MYOB) { delete Infinity; Infinity }") );

// let us leave the with block via a break.

this.TestCase(
  SECTION,
  "var MYOB = new WithObject(0); while (true) { with (MYOB) { Infinity; break; } } Infinity",
  Number.POSITIVE_INFINITY,
  eval("var MYOB = new WithObject(0); while (true) { with (MYOB) { Infinity; break; } } Infinity") );

	},
	/**
   File Name:          12.10-1.js
   ECMA Section:       12.10 The with statement
   Description:

   Author:             christine@netscape.com
   Date:               11 september 1997
*/
	test_12_10:function(){	
	var SECTION = "12.10-1";
var VERSION = "ECMA_1";
var TITLE   = "The with statement";

this.TestCase(   SECTION,
		"var x; with (7) x = valueOf(); typeof x;",
		"number",
		eval("var x; with(7) x = valueOf(); typeof x;") );
	},

/**
   File Name:          12.2-1.js
   ECMA Section:       The variable statement
   Description:

   If the variable statement occurs inside a FunctionDeclaration, the
   variables are defined with function-local scope in that function, as
   described in section 10.1.3. Otherwise, they are defined with global
   scope, that is, they are created as members of the global object, as
   described in section 0. Variables are created when the execution scope
   is entered. A Block does not define a new execution scope. Only Program and
   FunctionDeclaration produce a new scope. Variables are initialized to the
   undefined value when created. A variable with an Initializer is assigned
   the value of its AssignmentExpression when the VariableStatement is executed,
   not when the variable is created.

   Author:             christine@netscape.com
   Date:               12 november 1997
*/
	test_12_2__1:function(){
	
var SECTION = "12.2-1";
var VERSION = "ECMA_1";
var TITLE   = "The variable statement";

this.TestCase(    "SECTION",
		 "var x = 3; function f() { var a = x; var x = 23; return a; }; f()",
		 void 0,
		 eval("var x = 3; function f() { var a = x; var x = 23; return a; }; f()") );
	},

/**
   File Name:          12.5-1.js
   ECMA Section:       The if statement
   Description:

   The production IfStatement : if ( Expression ) Statement else Statement
   is evaluated as follows:

   1.Evaluate Expression.
   2.Call GetValue(Result(1)).
   3.Call ToBoolean(Result(2)).
   4.If Result(3) is false, go to step 7.
   5.Evaluate the first Statement.
   6.Return Result(5).
   7.Evaluate the second Statement.
   8.Return Result(7).

   Author:             christine@netscape.com
   Date:               12 november 1997
*/
	test_12_5__1:function(){	
	
var SECTION = "12.5-1";
var VERSION = "ECMA_1";
var TITLE   = "The if statement";


this.TestCase(   SECTION,
		"var MYVAR; if ( true ) MYVAR='PASSED'; else MYVAR= 'FAILED';",
		"PASSED",
		eval("var MYVAR; if ( true ) MYVAR='PASSED'; else MYVAR= 'FAILED';") );

this.TestCase(  SECTION,
	       "var MYVAR; if ( false ) MYVAR='FAILED'; else MYVAR= 'PASSED';",
	       "PASSED",
	       eval("var MYVAR; if ( false ) MYVAR='FAILED'; else MYVAR= 'PASSED';") );

this.TestCase(   SECTION,
		"var MYVAR; if ( new Boolean(true) ) MYVAR='PASSED'; else MYVAR= 'FAILED';",
		"PASSED",
		eval("var MYVAR; if ( new Boolean(true) ) MYVAR='PASSED'; else MYVAR= 'FAILED';") );

this.TestCase(  SECTION,
	       "var MYVAR; if ( new Boolean(false) ) MYVAR='PASSED'; else MYVAR= 'FAILED';",
	       "PASSED",
	       eval("var MYVAR; if ( new Boolean(false) ) MYVAR='PASSED'; else MYVAR= 'FAILED';") );

this.TestCase(   SECTION,
		"var MYVAR; if ( 1 ) MYVAR='PASSED'; else MYVAR= 'FAILED';",
		"PASSED",
		eval("var MYVAR; if ( 1 ) MYVAR='PASSED'; else MYVAR= 'FAILED';") );

this.TestCase(  SECTION,
	       "var MYVAR; if ( 0 ) MYVAR='FAILED'; else MYVAR= 'PASSED';",
	       "PASSED",
	       eval("var MYVAR; if ( 0 ) MYVAR='FAILED'; else MYVAR= 'PASSED';") );
	},

/**
   File Name:          12.5-2.js
   ECMA Section:       The if statement
   Description:

   The production IfStatement : if ( Expression ) Statement else Statement
   is evaluated as follows:

   1.Evaluate Expression.
   2.Call GetValue(Result(1)).
   3.Call ToBoolean(Result(2)).
   4.If Result(3) is false, go to step 7.
   5.Evaluate the first Statement.
   6.Return Result(5).
   7.Evaluate the second Statement.
   8.Return Result(7).

   Author:             christine@netscape.com
   Date:               12 november 1997
*/
	test_12_5__2:function(){	
		
var SECTION = "12.5-2";
var VERSION = "ECMA_1";
var TITLE = "The if statement" ;

this.TestCase(   SECTION,
		"var MYVAR; if ( true ) MYVAR='PASSED'; MYVAR",
		"PASSED",
		eval("var MYVAR; if ( true ) MYVAR='PASSED'; MYVAR") );

this.TestCase(  SECTION,
	       "var MYVAR; if ( false ) MYVAR='FAILED'; MYVAR;",
	       "PASSED",
	       eval("var MYVAR=\"PASSED\"; if ( false ) MYVAR='FAILED'; MYVAR;") );

this.TestCase(   SECTION,
		"var MYVAR; if ( new Boolean(true) ) MYVAR='PASSED'; MYVAR",
		"PASSED",
		eval("var MYVAR; if ( new Boolean(true) ) MYVAR='PASSED'; MYVAR") );

this.TestCase(   SECTION,
		"var MYVAR; if ( new Boolean(false) ) MYVAR='PASSED'; MYVAR",
		"PASSED",
		eval("var MYVAR; if ( new Boolean(false) ) MYVAR='PASSED'; MYVAR") );

this.TestCase(   SECTION,
		"var MYVAR; if ( 1 ) MYVAR='PASSED'; MYVAR",
		"PASSED",
		eval("var MYVAR; if ( 1 ) MYVAR='PASSED'; MYVAR") );

this.TestCase(  SECTION,
	       "var MYVAR; if ( 0 ) MYVAR='FAILED'; MYVAR;",
	       "PASSED",
	       eval("var MYVAR=\"PASSED\"; if ( 0 ) MYVAR='FAILED'; MYVAR;") );

	},

/**
   File Name:          12.6.1-1.js
   ECMA Section:       The while statement
   Description:


   Author:             christine@netscape.com
   Date:               12 november 1997
*/
	test_12_6_1__1:function(){	
	
var SECTION = "12.6.1-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The While statement";
//writeHeaderToLog( SECTION + " "+ TITLE);


this.TestCase( SECTION,
	      "var MYVAR = 0; while( MYVAR++ < 100) { if ( MYVAR < 100 ) break; } MYVAR ",
	      1,
	      eval("var MYVAR = 0; while( MYVAR++ < 100) { if ( MYVAR < 100 ) break; } MYVAR "));

this.TestCase( SECTION,
	      "var MYVAR = 0; while( MYVAR++ < 100) { if ( MYVAR < 100 ) continue; else break; } MYVAR ",
	      100,
	      eval("var MYVAR = 0; while( MYVAR++ < 100) { if ( MYVAR < 100 ) continue; else break; } MYVAR "));

this.TestCase( SECTION,
	      "function MYFUN( arg1 ) { while ( arg1++ < 100 ) { if ( arg1 < 100 ) return arg1; } }; MYFUN(1)",
	      2,
	      eval("function MYFUN( arg1 ) { while ( arg1++ < 100 ) { if ( arg1 < 100 ) return arg1; } }; MYFUN(1)"));
	},

/**
   File Name:          12.6.2-1.js
   ECMA Section:       12.6.2 The for Statement

   1. first expression is not present.
   2. second expression is not present
   3. third expression is not present


   Author:             christine@netscape.com
   Date:               15 september 1997
*/
	test_12_6_2__1:function(){	
	
var SECTION = "12.6.2-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The for statement";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( "12.6.2-1", "for statement",  99,     testprogram99() );
	},

/**
   File Name:          12.6.2-2.js
   ECMA Section:       12.6.2 The for Statement

   1. first expression is not present.
   2. second expression is not present
   3. third expression is present


   Author:             christine@netscape.com
   Date:               15 september 1997
*/
	test_12_6_2__2:function(){	
	
var SECTION = "12.6.2-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The for statement";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION, "for statement",  99,     testprogram99() );
	},

/**
   File Name:          12.6.2-3.js
   ECMA Section:       12.6.2 The for Statement

   1. first expression is not present.
   2. second expression is present
   3. third expression is present


   Author:             christine@netscape.com
   Date:               15 september 1997
*/
	test_12_6_2__3:function(){	
	var SECTION = "12.6.2-3";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The for statement";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION, "for statement",  100,     testprogram100() );


	},

/**
   File Name:          12.6.2-4.js
   ECMA Section:       12.6.2 The for Statement

   1. first expression is not present.
   2. second expression is present
   3. third expression is present


   Author:             christine@netscape.com
   Date:               15 september 1997
*/

	test_12_6_2__4:function(){	
	
var SECTION = "12.6.2-4";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The for statement";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION, "for statement",  100,     testprogram100() );
	},

/**
   File Name:          12.6.2-5.js
   ECMA Section:       12.6.2 The for Statement

   1. first expression is not present.
   2. second expression is present
   3. third expression is present


   Author:             christine@netscape.com
   Date:               15 september 1997
*/
	test_12_6_2__5:function(){	
	
var SECTION = "12.6.2-5";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The for statement";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION, "for statement",  99,     testprogram99() );


	},

/**
   File Name:          12.6.2-6.js
   ECMA Section:       12.6.2 The for Statement

   1. first expression is present.
   2. second expression is not present
   3. third expression is present


   Author:             christine@netscape.com
   Date:               15 september 1997
*/
	test_12_6_2__6:function(){	
	var SECTION = "12.6.2-6";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The for statement";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( "12.6.2-6", "for statement",  256,     testprogram() );

//test();

	},

/**
   File Name:          12.6.2-7.js
   ECMA Section:       12.6.2 The for Statement

   1. first expression is present.
   2. second expression is not present
   3. third expression is present


   Author:             christine@netscape.com
   Date:               15 september 1997
*/
	test_12_6_2__7:function(){	
	var SECTION = "12.6.2-7";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The for statement";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION, "for statement",  256,     testprogram() );

	},

/**
   File Name:          12.6.2-8.js
   ECMA Section:       12.6.2 The for Statement

   1. first expression is present.
   2. second expression is present
   3. third expression is present


   Author:             christine@netscape.com
   Date:               15 september 1997
*/
	test_12_6_2__8:function(){	
	var SECTION = "12.6.2-8";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The for statement";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION, "for statement",  256,     testprogram() );

	},

/**
   File Name:          12.6.2-9-n.js
   ECMA Section:       12.6.2 The for Statement

   1. first expression is not present.
   2. second expression is not present
   3. third expression is not present


   Author:             christine@netscape.com
   Date:               15 september 1997
*/
	test_12_6_2__9__n:function(){	
	
var SECTION = "12.6.2-9-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The for statement";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "for (i)";
EXPECTED = "error";

this.TestCase( SECTION,
	      "for (i)",
	      "error",
	      eval("for (i) { }") );
	},

/**
   File Name:          12.6.3-1.js
   ECMA Section:       12.6.3 The for...in Statement
   Description:

   Author:             christine@netscape.com
   Date:               11 september 1997
*/
	test_12_6_3__1:function(){	
	
var SECTION = "12.6.3-1";
var VERSION = "ECMA_1";
var TITLE   = "The for..in statement";

this.TestCase( SECTION,
	      "var x; Number.prototype.foo = 34; for ( j in 7 ) x = j; x",
	      "foo",
	      eval("var x; Number.prototype.foo = 34; for ( j in 7 ){x = j;} x") );
	},

/**
   File Name:          12.6.3-10.js
   ECMA Section:       12.6.3 The for...in Statement
   Description:
   The production IterationStatement : for ( LeftHandSideExpression in Expression )
   Statement is evaluated as follows:

   1.  Evaluate the Expression.
   2.  Call GetValue(Result(1)).
   3.  Call ToObject(Result(2)).
   4.  Let C be "normal completion".
   5.  Get the name of the next property of Result(3) that doesn't have the
   DontEnum attribute. If there is no such property, go to step 14.
   6.  Evaluate the LeftHandSideExpression (it may be evaluated repeatedly).
   7.  Call PutValue(Result(6), Result(5)).  PutValue( V, W ):
   1.  If Type(V) is not Reference, generate a runtime error.
   2.  Call GetBase(V).
   3.  If Result(2) is null, go to step 6.
   4.  Call the [[Put]] method of Result(2), passing GetPropertyName(V)
   for the property name and W for the value.
   5.  Return.
   6.  Call the [[Put]] method for the global object, passing
   GetPropertyName(V) for the property name and W for the value.
   7.  Return.
   8.  Evaluate Statement.
   9.  If Result(8) is a value completion, change C to be "normal completion
   after value V" where V is the value carried by Result(8).
   10. If Result(8) is a break completion, go to step 14.
   11. If Result(8) is a continue completion, go to step 5.
   12. If Result(8) is a return completion, return Result(8).
   13. Go to step 5.
   14. Return C.

   Author:             christine@netscape.com
   Date:               11 september 1997
*/
	test_12_6_3__10:function(){	
	var SECTION = "12.6.3-10";
var VERSION = "ECMA_1";
var TITLE   = "The for..in statement";


//  for ( LeftHandSideExpression in Expression )
//  LeftHandSideExpression:NewExpression:MemberExpression

var count = 0;
function f() {     count++; return new Array("h","e","l","l","o"); }

var result = "";
for ( p in f() ) { result += f()[p] };

this.TestCase( SECTION,
	      "count = 0; result = \"\"; "+
	      "function f() { count++; return new Array(\"h\",\"e\",\"l\",\"l\",\"o\"); }"+
	      "for ( p in f() ) { result += f()[p] }; count",
	      6,
	      count );

this.TestCase( SECTION,
	      "result",
	      "hello",
	      result );
		  //  LeftHandSideExpression:NewExpression:MemberExpression [ Expression ]
//  LeftHandSideExpression:NewExpression:MemberExpression . Identifier
//  LeftHandSideExpression:NewExpression:new MemberExpression Arguments
//  LeftHandSideExpression:NewExpression:PrimaryExpression:( Expression )
//  LeftHandSideExpression:CallExpression:MemberExpression Arguments
//  LeftHandSideExpression:CallExpression Arguments
//  LeftHandSideExpression:CallExpression [ Expression ]
//  LeftHandSideExpression:CallExpression . Identifier
	},

/**
   File Name:          12.6.3-11.js
   ECMA Section:       12.6.3 The for...in Statement
   Description:
   The production IterationStatement : for ( LeftHandSideExpression in Expression )
   Statement is evaluated as follows:

   1.  Evaluate the Expression.
   2.  Call GetValue(Result(1)).
   3.  Call ToObject(Result(2)).
   4.  Let C be "normal completion".
   5.  Get the name of the next property of Result(3) that doesn't have the
   DontEnum attribute. If there is no such property, go to step 14.
   6.  Evaluate the LeftHandSideExpression (it may be evaluated repeatedly).
   7.  Call PutValue(Result(6), Result(5)).  PutValue( V, W ):
   1.  If Type(V) is not Reference, generate a runtime error.
   2.  Call GetBase(V).
   3.  If Result(2) is null, go to step 6.
   4.  Call the [[Put]] method of Result(2), passing GetPropertyName(V)
   for the property name and W for the value.
   5.  Return.
   6.  Call the [[Put]] method for the global object, passing
   GetPropertyName(V) for the property name and W for the value.
   7.  Return.
   8.  Evaluate Statement.
   9.  If Result(8) is a value completion, change C to be "normal completion
   after value V" where V is the value carried by Result(8).
   10. If Result(8) is a break completion, go to step 14.
   11. If Result(8) is a continue completion, go to step 5.
   12. If Result(8) is a return completion, return Result(8).
   13. Go to step 5.
   14. Return C.

   Author:             christine@netscape.com
   Date:               11 september 1997
*/
	test_12_6_3__11:function(){	
	var SECTION = "12.6.3-11";
var VERSION = "ECMA_1";
var TITLE   = "The for..in statement";


//    5.  Get the name of the next property of Result(3) that doesn't have the
//        DontEnum attribute. If there is no such property, go to step 14.

var result = "";

for ( p in Number ) { result += String(p) };

this.TestCase( SECTION,
	      "result = \"\"; for ( p in Number ) { result += String(p) };",
	      "",
	      result );
	},

/**
   File Name:          12.6.3-12.js
   ECMA Section:       12.6.3 The for...in Statement
   Description:

   This is a regression test for http://bugzilla.mozilla.org/show_bug.cgi?id=9802.

   The production IterationStatement : for ( LeftHandSideExpression in Expression )
   Statement is evaluated as follows:

   1.  Evaluate the Expression.
   2.  Call GetValue(Result(1)).
   3.  Call ToObject(Result(2)).
   4.  Let C be "normal completion".
   5.  Get the name of the next property of Result(3) that doesn't have the
   DontEnum attribute. If there is no such property, go to step 14.
   6.  Evaluate the LeftHandSideExpression (it may be evaluated repeatedly).
   7.  Call PutValue(Result(6), Result(5)).  PutValue( V, W ):
   1.  If Type(V) is not Reference, generate a runtime error.
   2.  Call GetBase(V).
   3.  If Result(2) is null, go to step 6.
   4.  Call the [[Put]] method of Result(2), passing GetPropertyName(V)
   for the property name and W for the value.
   5.  Return.
   6.  Call the [[Put]] method for the global object, passing
   GetPropertyName(V) for the property name and W for the value.
   7.  Return.
   8.  Evaluate Statement.
   9.  If Result(8) is a value completion, change C to be "normal completion
   after value V" where V is the value carried by Result(8).
   10. If Result(8) is a break completion, go to step 14.
   11. If Result(8) is a continue completion, go to step 5.
   12. If Result(8) is a return completion, return Result(8).
   13. Go to step 5.
   14. Return C.

   Author:             christine@netscape.com
   Date:               11 september 1997
*/
	test_12_6_3__12:function(){	
	
var SECTION = "12.6.3-12";
var VERSION = "ECMA_1";
var TITLE   = "The for..in statement";

var result = "PASSED";

for ( aVar in this ) {
  if (aVar == "aVar") {
    result = "FAILED"
      }
};

this.TestCase(
  SECTION,
  "var result=''; for ( aVar in this ) { " +
  "if (aVar == 'aVar') {return a failure}; result",
  "PASSED",
  result );
	},

/**
   File Name:          12.6.3-1.js
   ECMA Section:       12.6.3 The for...in Statement
   Description:
   The production IterationStatement : for ( LeftHandSideExpression in Expression )
   Statement is evaluated as follows:

   1.  Evaluate the Expression.
   2.  Call GetValue(Result(1)).
   3.  Call ToObject(Result(2)).
   4.  Let C be "normal completion".
   5.  Get the name of the next property of Result(3) that doesn't have the
   DontEnum attribute. If there is no such property, go to step 14.
   6.  Evaluate the LeftHandSideExpression (it may be evaluated repeatedly).
   7.  Call PutValue(Result(6), Result(5)).  PutValue( V, W ):
   1.  If Type(V) is not Reference, generate a runtime error.
   2.  Call GetBase(V).
   3.  If Result(2) is null, go to step 6.
   4.  Call the [[Put]] method of Result(2), passing GetPropertyName(V)
   for the property name and W for the value.
   5.  Return.
   6.  Call the [[Put]] method for the global object, passing
   GetPropertyName(V) for the property name and W for the value.
   7.  Return.
   8.  Evaluate Statement.
   9.  If Result(8) is a value completion, change C to be "normal completion
   after value V" where V is the value carried by Result(8).
   10. If Result(8) is a break completion, go to step 14.
   11. If Result(8) is a continue completion, go to step 5.
   12. If Result(8) is a return completion, return Result(8).
   13. Go to step 5.
   14. Return C.

   Author:             christine@netscape.com
   Date:               11 september 1997
*/
	test_12_6_3__19:function(){	
	
var SECTION = "12.6.3-4";
var VERSION = "ECMA_1";
var TITLE   = "The for..in statement";


//  for ( LeftHandSideExpression in Expression )
//  LeftHandSideExpression:NewExpression:MemberExpression

var count = 0;
function f() {     count++; return new Array("h","e","l","l","o"); }

var result = "";
for ( p in f() ) { result += f()[p] };

this.TestCase( SECTION,
	      "count = 0; result = \"\"; "+
	      "function f() { count++; return new Array(\"h\",\"e\",\"l\",\"l\",\"o\"); }"+
	      "for ( p in f() ) { result += f()[p] }; count",
	      6,
	      count );

this.TestCase( SECTION,
	      "result",
	      "hello",
	      result );
	},

/**
   File Name:          12.6.3-2.js
   ECMA Section:       12.6.3 The for...in Statement
   Description:        Check the Boolean Object


   Author:             christine@netscape.com
   Date:               11 september 1997
*/
	test_12_6_3__2:function(){	
	
var SECTION = "12.6.3-2";
var VERSION = "ECMA_1";
var TITLE   = "The for..in statement";

this.TestCase(   SECTION,
		"Boolean.prototype.foo = 34; for ( j in Boolean ) Boolean[j]",
		34,
		eval("Boolean.prototype.foo = 34; for ( j in Boolean ) Boolean[j] ") );
	},

/**
   File Name:          12.6.3-3.js
   ECMA Section:       for..in loops
   Description:

   This verifies the fix to
   http://scopus.mcom.com/bugsplat/show_bug.cgi?id=112156
   for..in should take general lvalue for first argument

   Author:             christine@netscape.com
   Date:               12 november 1997
*/

	test_12_6_3__3:function(){	
//	var SECTION = "12.6.3-3";
//var VERSION = "ECMA_1";
//var TITLE   = "The for..in statement";
//
//var o = {};
//
//var result = "";
//
//for ( o.a in [1,2,3] ) { result += String( [1,2,3][o.a] ); }
//
//this.TestCase( SECTION,
//	      "for ( o.a in [1,2,3] ) { result += String( [1,2,3][o.a] ); } result",
//	      "123",
//	      result );
	},

/**
   File Name:          12.6.3-1.js
   ECMA Section:       12.6.3 The for...in Statement
   Description:
   The production IterationStatement : for ( LeftHandSideExpression in Expression )
   Statement is evaluated as follows:

   1.  Evaluate the Expression.
   2.  Call GetValue(Result(1)).
   3.  Call ToObject(Result(2)).
   4.  Let C be "normal completion".
   5.  Get the name of the next property of Result(3) that doesn't have the
   DontEnum attribute. If there is no such property, go to step 14.
   6.  Evaluate the LeftHandSideExpression (it may be evaluated repeatedly).
   7.  Call PutValue(Result(6), Result(5)).  PutValue( V, W ):
   1.  If Type(V) is not Reference, generate a runtime error.
   2.  Call GetBase(V).
   3.  If Result(2) is null, go to step 6.
   4.  Call the [[Put]] method of Result(2), passing GetPropertyName(V)
   for the property name and W for the value.
   5.  Return.
   6.  Call the [[Put]] method for the global object, passing
   GetPropertyName(V) for the property name and W for the value.
   7.  Return.
   8.  Evaluate Statement.
   9.  If Result(8) is a value completion, change C to be "normal completion
   after value V" where V is the value carried by Result(8).
   10. If Result(8) is a break completion, go to step 14.
   11. If Result(8) is a continue completion, go to step 5.
   12. If Result(8) is a return completion, return Result(8).
   13. Go to step 5.
   14. Return C.

   Author:             christine@netscape.com
   Date:               11 september 1997
*/
	test_12_6_3__4:function(){	
	var SECTION = "12.6.3-4";
var VERSION = "ECMA_1";
var TITLE   = "The for..in statement";
var BUGNUMBER="http://scopus.mcom.com/bugsplat/show_bug.cgi?id=344855";

//  for ( LeftHandSideExpression in Expression )
//  LeftHandSideExpression:NewExpression:MemberExpression

var o = new MyObject();
var result = 0;

for ( MyObject in o ) {
  result += o[MyObject];
}

this.TestCase( SECTION,
	      "for ( MyObject in o ) { result += o[MyObject] }",
	      6,
	      result );

var result = 0;

for ( value in o ) {
  result += o[value];
}

this.TestCase( SECTION,
	      "for ( value in o ) { result += o[value]",
	      6,
	      result );

var value = "value";
var result = 0;
for ( value in o ) {
  result += o[value];
}

this.TestCase( SECTION,
	      "value = \"value\"; for ( value in o ) { result += o[value]",
	      6,
	      result );

var value = 0;
var result = 0;
for ( value in o ) {
  result += o[value];
}

this.TestCase( SECTION,
	      "value = 0; for ( value in o ) { result += o[value]",
	      6,
	      result );

// this causes a segv

//var ob = { 0:"hello" };
var result = 0;
//for ( ob[0] in o ) {
//  result += o[ob[0]];
//}

//this.TestCase( SECTION,
//	      "ob = { 0:\"hello\" }; for ( ob[0] in o ) { result += o[ob[0]]",
//	      6,
//	      result );

var result = 0;
//for ( ob["0"] in o ) {
//  result += o[ob["0"]];
//}
//
//this.TestCase( SECTION,
//	      "value = 0; for ( ob[\"0\"] in o ) { result += o[o[\"0\"]]",
//	      6,
//	      result );

var result = 0;
var ob = { value:"hello" };
//for ( ob[value] in o ) {
//  result += o[ob[value]];
//}
//
//this.TestCase( SECTION,
//	      "ob = { 0:\"hello\" }; for ( ob[value] in o ) { result += o[ob[value]]",
//	      6,
//	      result );

//var result = 0;
//for ( ob["value"] in o ) {
//  result += o[ob["value"]];
//}
//
//this.TestCase( SECTION,
//	      "value = 0; for ( ob[\"value\"] in o ) { result += o[ob[\"value\"]]",
//	      6,
//	      result );

//var result = 0;
//for ( ob.value in o ) {
//  result += o[ob.value];
//}
//
//this.TestCase( SECTION,
//	      "value = 0; for ( ob.value in o ) { result += o[ob.value]",
//	      6,
//	      result );

//  LeftHandSideExpression:NewExpression:MemberExpression [ Expression ]
//  LeftHandSideExpression:NewExpression:MemberExpression . Identifier
//  LeftHandSideExpression:NewExpression:new MemberExpression Arguments
//  LeftHandSideExpression:NewExpression:PrimaryExpression:( Expression )
//  LeftHandSideExpression:CallExpression:MemberExpression Arguments
//  LeftHandSideExpression:CallExpression Arguments
//  LeftHandSideExpression:CallExpression [ Expression ]
//  LeftHandSideExpression:CallExpression . Identifier
	},

/**
   File Name:          12.6.3-1.js
   ECMA Section:       12.6.3 The for...in Statement
   Description:
   The production IterationStatement : for ( LeftHandSideExpression in Expression )
   Statement is evaluated as follows:

   1.  Evaluate the Expression.
   2.  Call GetValue(Result(1)).
   3.  Call ToObject(Result(2)).
   4.  Let C be "normal completion".
   5.  Get the name of the next property of Result(3) that doesn't have the
   DontEnum attribute. If there is no such property, go to step 14.
   6.  Evaluate the LeftHandSideExpression ( it may be evaluated repeatedly).
   7.  Call PutValue(Result(6), Result(5)).  PutValue( V, W ):
   1.  If Type(V) is not Reference, generate a runtime error.
   2.  Call GetBase(V).
   3.  If Result(2) is null, go to step 6.
   4.  Call the [[Put]] method of Result(2), passing GetPropertyName(V)
   for the property name and W for the value.
   5.  Return.
   6.  Call the [[Put]] method for the global object, passing
   GetPropertyName(V) for the property name and W for the value.
   7.  Return.
   8.  Evaluate Statement.
   9.  If Result(8) is a value completion, change C to be "normal completion
   after value V" where V is the value carried by Result(8).
   10. If Result(8) is a break completion, go to step 14.
   11. If Result(8) is a continue completion, go to step 5.
   12. If Result(8) is a return completion, return Result(8).
   13. Go to step 5.
   14. Return C.

   Author:             christine@netscape.com
   Date:               11 september 1997
*/
	test_12_6_3__5__n:function(){	
	var SECTION = "12.6.3-4";
var VERSION = "ECMA_1";
var TITLE   = "The for..in statement";

//  for ( LeftHandSideExpression in Expression )
//  LeftHandSideExpression:NewExpression:MemberExpression

DESCRIPTION = "more than one member expression";
EXPECTED = "error";

this.TestCase( SECTION,
	      "more than one member expression",
	      "error",
	      eval("var o = new MyObject(); var result = 0; for ( var i, p in this) { result += this[p]; }") );

/*
  var o = new MyObject();
  var result = 0;

  for ( var i, p in this) {
  result += this[p];
  }
*/
	},

/**
   File Name:          12.6.3-1.js
   ECMA Section:       12.6.3 The for...in Statement
   Description:
   The production IterationStatement : for ( LeftHandSideExpression in Expression )
   Statement is evaluated as follows:

   1.  Evaluate the Expression.
   2.  Call GetValue(Result(1)).
   3.  Call ToObject(Result(2)).
   4.  Let C be "normal completion".
   5.  Get the name of the next property of Result(3) that doesn't have the
   DontEnum attribute. If there is no such property, go to step 14.
   6.  Evaluate the LeftHandSideExpression ( it may be evaluated repeatedly).
   7.  Call PutValue(Result(6), Result(5)).  PutValue( V, W ):
   1.  If Type(V) is not Reference, generate a runtime error.
   2.  Call GetBase(V).
   3.  If Result(2) is null, go to step 6.
   4.  Call the [[Put]] method of Result(2), passing GetPropertyName(V)
   for the property name and W for the value.
   5.  Return.
   6.  Call the [[Put]] method for the global object, passing
   GetPropertyName(V) for the property name and W for the value.
   7.  Return.
   8.  Evaluate Statement.
   9.  If Result(8) is a value completion, change C to be "normal completion
   after value V" where V is the value carried by Result(8).
   10. If Result(8) is a break completion, go to step 14.
   11. If Result(8) is a continue completion, go to step 5.
   12. If Result(8) is a return completion, return Result(8).
   13. Go to step 5.
   14. Return C.

   Author:             christine@netscape.com
   Date:               11 september 1997
*/
	test_12_6_3__6__n:function(){
var SECTION = "12.6.3-4";
var VERSION = "ECMA_1";
var TITLE   = "The for..in statement";

//  for ( LeftHandSideExpression in Expression )
//  LeftHandSideExpression:NewExpression:MemberExpression

DESCRIPTION = "bad left-hand side expression";
EXPECTED = "error";

this.TestCase( SECTION,
	      "bad left-hand side expression",
	      "error",
	      eval("var o = new MyObject(); var result = 0; for ( this in o) { result += this[p]; }") );
/*
  var o = new MyObject();
  var result = 0;

  for ( this in o) {
  result += this[p];
  }
*/	
	},

/**
   File Name:          12.6.3-1.js
   ECMA Section:       12.6.3 The for...in Statement
   Description:
   The production IterationStatement : for ( LeftHandSideExpression in Expression )
   Statement is evaluated as follows:

   1.  Evaluate the Expression.
   2.  Call GetValue(Result(1)).
   3.  Call ToObject(Result(2)).
   4.  Let C be "normal completion".
   5.  Get the name of the next property of Result(3) that doesn't have the
   DontEnum attribute. If there is no such property, go to step 14.
   6.  Evaluate the LeftHandSideExpression ( it may be evaluated repeatedly).
   7.  Call PutValue(Result(6), Result(5)).  PutValue( V, W ):
   1.  If Type(V) is not Reference, generate a runtime error.
   2.  Call GetBase(V).
   3.  If Result(2) is null, go to step 6.
   4.  Call the [[Put]] method of Result(2), passing GetPropertyName(V)
   for the property name and W for the value.
   5.  Return.
   6.  Call the [[Put]] method for the global object, passing
   GetPropertyName(V) for the property name and W for the value.
   7.  Return.
   8.  Evaluate Statement.
   9.  If Result(8) is a value completion, change C to be "normal completion
   after value V" where V is the value carried by Result(8).
   10. If Result(8) is a break completion, go to step 14.
   11. If Result(8) is a continue completion, go to step 5.
   12. If Result(8) is a return completion, return Result(8).
   13. Go to step 5.
   14. Return C.

   Author:             christine@netscape.com
   Date:               11 september 1997
*/
	test_12_6_3__7__n:function(){
	
var SECTION = "12.6.3-4";
var VERSION = "ECMA_1";
var TITLE   = "The for..in statement";

//  for ( LeftHandSideExpression in Expression )
//  LeftHandSideExpression:NewExpression:MemberExpression

DESCRIPTION = "bad left-hand side expression";
EXPECTED = "error";

this.TestCase( SECTION,
	      "bad left-hand side expression",
	      "error",
	      eval("var o = new MyObject(); var result = 0; for ( \"a\" in o) { result += this[p]; } ") );

/*
  var o = new MyObject();
  var result = 0;

  for ( "a" in o) {
  result += this[p];
  }
*/

	},

/**
   File Name:          12.6.3-8-n.js
   ECMA Section:       12.6.3 The for...in Statement
   Description:
   The production IterationStatement : for ( LeftHandSideExpression in Expression )
   Statement is evaluated as follows:

   1.  Evaluate the Expression.
   2.  Call GetValue(Result(1)).
   3.  Call ToObject(Result(2)).
   4.  Let C be "normal completion".
   5.  Get the name of the next property of Result(3) that doesn't have the
   DontEnum attribute. If there is no such property, go to step 14.
   6.  Evaluate the LeftHandSideExpression ( it may be evaluated repeatedly).
   7.  Call PutValue(Result(6), Result(5)).  PutValue( V, W ):
   1.  If Type(V) is not Reference, generate a runtime error.
   2.  Call GetBase(V).
   3.  If Result(2) is null, go to step 6.
   4.  Call the [[Put]] method of Result(2), passing GetPropertyName(V)
   for the property name and W for the value.
   5.  Return.
   6.  Call the [[Put]] method for the global object, passing
   GetPropertyName(V) for the property name and W for the value.
   7.  Return.
   8.  Evaluate Statement.
   9.  If Result(8) is a value completion, change C to be "normal completion
   after value V" where V is the value carried by Result(8).
   10. If Result(8) is a break completion, go to step 14.
   11. If Result(8) is a continue completion, go to step 5.
   12. If Result(8) is a return completion, return Result(8).
   13. Go to step 5.
   14. Return C.

   Author:             christine@netscape.com
   Date:               11 september 1997
*/
	test_12_6_3__8__n:function(){	
	var SECTION = "12.6.3-4";
var VERSION = "ECMA_1";
var TITLE   = "The for..in statement";

//  for ( LeftHandSideExpression in Expression )
//  LeftHandSideExpression:NewExpression:MemberExpression

DESCRIPTION = "bad left-hand side expression";
EXPECTED = "error";

this.TestCase( SECTION,
	      "bad left-hand side expression",
	      "error",
	      eval("var o = new MyObject(); var result = 0; for ( 1 in o) { result += this[p]; } ") );

/*
  var o = new MyObject();
  var result = 0;

  for ( 1 in o) {
  result += this[p];
  }
*/
	},

/**
   File Name:          12.6.3-9-n.js
   ECMA Section:       12.6.3 The for...in Statement
   Description:
   The production IterationStatement : for ( LeftHandSideExpression in Expression )
   Statement is evaluated as follows:

   1.  Evaluate the Expression.
   2.  Call GetValue(Result(1)).
   3.  Call ToObject(Result(2)).
   4.  Let C be "normal completion".
   5.  Get the name of the next property of Result(3) that doesn't have the
   DontEnum attribute. If there is no such property, go to step 14.
   6.  Evaluate the LeftHandSideExpression ( it may be evaluated repeatedly).
   7.  Call PutValue(Result(6), Result(5)).  PutValue( V, W ):
   1.  If Type(V) is not Reference, generate a runtime error.
   2.  Call GetBase(V).
   3.  If Result(2) is null, go to step 6.
   4.  Call the [[Put]] method of Result(2), passing GetPropertyName(V)
   for the property name and W for the value.
   5.  Return.
   6.  Call the [[Put]] method for the global object, passing
   GetPropertyName(V) for the property name and W for the value.
   7.  Return.
   8.  Evaluate Statement.
   9.  If Result(8) is a value completion, change C to be "normal completion
   after value V" where V is the value carried by Result(8).
   10. If Result(8) is a break completion, go to step 14.
   11. If Result(8) is a continue completion, go to step 5.
   12. If Result(8) is a return completion, return Result(8).
   13. Go to step 5.
   14. Return C.

   Author:             christine@netscape.com
   Date:               11 september 1997
*/
	test_12_6_3__9__n:function(){	
	var SECTION = "12.6.3-9-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "The for..in statement";

//writeHeaderToLog( SECTION + " "+ TITLE);

//  for ( LeftHandSideExpression in Expression )
//  LeftHandSideExpression:NewExpression:MemberExpression

DESCRIPTION = "object is not defined";
EXPECTED = "error";

this.TestCase( SECTION,
	      "object is not defined",
	      "error",
	      eval("var o = new MyObject(); var result = 0; for ( var o in foo) { result += this[o]; } ") );
/*
  var o = new MyObject();
  var result = 0;

  for ( var o in foo) {
  result += this[o];
  }
*/
	},

/**
   File Name:          12.7-1-n.js
   ECMA Section:       12.7 The continue statement
   Description:

   Author:             christine@netscape.com
   Date:               12 november 1997
*/
	test_12_7__1__n:function(){	
	
var SECTION = "12.7.1-n";
var VERSION = "ECMA_1";
var TITLE   = "The continue statement";

DESCRIPTION = "continue";
EXPECTED = "error";

this.TestCase(   SECTION,
		"continue",
		"error",
		eval("continue") );
	},

/**
   File Name:          12.8-1-n.js
   ECMA Section:       12.8 The break statement
   Description:

   Author:             christine@netscape.com
   Date:               12 november 1997
*/
	test_12_8__1__n:function(){	
	var SECTION = "12.8-1-n";
var VERSION = "ECMA_1";
var TITLE   = "The break in statement";

DESCRIPTION = "break";
EXPECTED = "error";

this.TestCase(   SECTION,
		"break",
		"error",
		eval("break") );
	},

/**
   File Name:          12.9-1-n.js
   ECMA Section:       12.9 The return statement
   Description:

   Author:             christine@netscape.com
   Date:               12 november 1997
*/
	test_12_9__1__n:function(){
	var SECTION = "12.9-1-n";
var VERSION = "ECMA_1";

DESCRIPTION = "return";
EXPECTED = "error";

this.TestCase(   SECTION,
		"return",
		"error",
		eval("return") );
	}
}).endType();

function MyObject() {
  this.value = 2;
  this[0] = 4;
  return this;
}

function WithObject( value ) {
  this.prop1 = 1;
  this.prop2 = new Boolean(true);
  this.prop3 = "a string";
  this.value = value;

  // now we will override global functions

  this.parseInt = new Function( "return this.value" );
  this.NaN = value;
  this.Infinity = value;
  this.unescape = new Function( "return this.value" );
  this.escape   = new Function( "return this.value" );
  this.eval     = new Function( "return this.value" );
  this.parseFloat = new Function( "return this.value" );
  this.isNaN      = new Function( "return this.value" );
  this.isFinite   = new Function( "return this.value" );
}


function testprogram99() {
  myVar = 0;

  for ( ; ; ) {
    if ( ++myVar == 99 )
      break;
  }

  return myVar;
}


function testprogram100() {
  myVar = 0;

  for ( ; myVar < 100 ; myVar++ ) {
    continue;
  }

  return myVar;
}

function testprogram() {
  var myVar;

  for ( myVar=2;  myVar < 256; myVar *= myVar ) {
  }

  return myVar;
}