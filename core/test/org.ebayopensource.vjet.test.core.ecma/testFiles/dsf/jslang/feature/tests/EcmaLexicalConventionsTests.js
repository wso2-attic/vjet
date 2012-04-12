vjo.ctype("dsf.jslang.feature.tests.EcmaLexicalConventionsTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({

constructs: function() {
this.base();
},

/**
File Name:          7.1-1.js
ECMA Section:       7.1 White Space
Description:        - readability
- separate tokens
- otherwise should be insignificant
- in strings, white space characters are significant
- cannot appear within any other kind of token

white space characters are:
unicode     name            formal name     string representation
\u0009      tab             <TAB>           \t
\u000B      veritical tab   <VT>            \v
\U000C      form feed       <FF>            \f
\u0020      space           <SP>            " "

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_7_1__1: function() {
var SECTION = "7.1-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "White Space";

//writeHeaderToLog( SECTION + " "+ TITLE);

// whitespace between var keyword and identifier

this.TestCase( SECTION,  'var'+'\t'+'MYVAR1=10;MYVAR1',   10, eval('var'+'\t'+'MYVAR1=10;MYVAR1') );
this.TestCase( SECTION,  'var'+'\f'+'MYVAR2=10;MYVAR2',   10, eval('var'+'\f'+'MYVAR2=10;MYVAR2') );
this.TestCase( SECTION,  'var'+'\v'+'MYVAR2=10;MYVAR2',   10, eval('var'+'\v'+'MYVAR2=10;MYVAR2') );
this.TestCase( SECTION,  'var'+'\ '+'MYVAR2=10;MYVAR2',   10, eval('var'+'\ '+'MYVAR2=10;MYVAR2') );

// use whitespace between tokens object name, dot operator, and object property

this.TestCase( SECTION,
"var a = new Array(12345); a\t\v\f .\\u0009\\000B\\u000C\\u0020length",
12345,
eval("var a = new Array(12345); a\t\v\f .\u0009\u0020\u000C\u000Blength") );

//test();

},

/**
File Name:          7.1-2.js
ECMA Section:       7.1 White Space
Description:        - readability
- separate tokens
- otherwise should be insignificant
- in strings, white space characters are significant
- cannot appear within any other kind of token

white space characters are:
unicode     name            formal name     string representation
\u0009      tab             <TAB>           \t
\u000B      veritical tab   <VT>            ??
\U000C      form feed       <FF>            \f
\u0020      space           <SP>            " "

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_7_1__2: function() {
var SECTION = "7.1-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "White Space";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,    "'var'+'\u000B'+'MYVAR1=10;MYVAR1'",   10, eval('var'+'\u000B'+'MYVAR1=10;MYVAR1') );
this.TestCase( SECTION,    "'var'+'\u0009'+'MYVAR2=10;MYVAR2'",   10, eval('var'+'\u0009'+'MYVAR2=10;MYVAR2') );
this.TestCase( SECTION,    "'var'+'\u000C'+'MYVAR3=10;MYVAR3'",   10, eval('var'+'\u000C'+'MYVAR3=10;MYVAR3') );
this.TestCase( SECTION,    "'var'+'\u0020'+'MYVAR4=10;MYVAR4'",   10, eval('var'+'\u0020'+'MYVAR4=10;MYVAR4') );

//test();

},

/**
File Name:          7.1-3.js
ECMA Section:       7.1 White Space
Description:        - readability
- separate tokens
- otherwise should be insignificant
- in strings, white space characters are significant
- cannot appear within any other kind of token

white space characters are:
unicode     name            formal name     string representation
\u0009      tab             <TAB>           \t
\u000B      veritical tab   <VT>            ??
\U000C      form feed       <FF>            \f
\u0020      space           <SP>            " "

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_7_1__3: function() {
var SECTION = "7.1-3";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "White Space";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,    "'var'+'\u000B'+'MYVAR1=10;MYVAR1'",   10, eval('var'+'\u000B'+'MYVAR1=10;MYVAR1') );
this.TestCase( SECTION,    "'var'+'\u0009'+'MYVAR2=10;MYVAR2'",   10, eval('var'+'\u0009'+'MYVAR2=10;MYVAR2') );
this.TestCase( SECTION,    "'var'+'\u000C'+'MYVAR3=10;MYVAR3'",   10, eval('var'+'\u000C'+'MYVAR3=10;MYVAR3') );
this.TestCase( SECTION,    "'var'+'\u0020'+'MYVAR4=10;MYVAR4'",   10, eval('var'+'\u0020'+'MYVAR4=10;MYVAR4') );

// +<white space>+ should be interpreted as the unary + operator twice, not as a post or prefix increment operator

this.TestCase(   SECTION,
"var VAR = 12345; + + VAR",
12345,
eval("var VAR = 12345; + + VAR") );

this.TestCase(   SECTION,
"var VAR = 12345;VAR+ + VAR",
24690,
eval("var VAR = 12345;VAR+ +VAR") );
this.TestCase(   SECTION,
"var VAR = 12345;VAR - - VAR",
24690,
eval("var VAR = 12345;VAR- -VAR") );

//test();

},

/**
File Name:          7.2-1.js
ECMA Section:       7.2 Line Terminators
Description:        - readability
- separate tokens
- may occur between any two tokens
- cannot occur within any token, not even a string
- affect the process of automatic semicolon insertion.

white space characters are:
unicode     name            formal name     string representation
\u000A      line feed       <LF>            \n
\u000D      carriage return <CR>            \r

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_7_2__1: function() {
var SECTION = "7.2-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Line Terminators";

//writeHeaderToLog( SECTION + " "+ TITLE);


this.TestCase( SECTION,    "var a\nb =  5; ab=10;ab;",     10,     eval("var a\nb =  5; ab=10;ab") );
this.TestCase( SECTION,    "var a\nb =  5; ab=10;b;",      5,      eval("var a\nb =  5; ab=10;b") );
this.TestCase( SECTION,    "var a\rb =  5; ab=10;ab;",     10,     eval("var a\rb =  5; ab=10;ab") );
this.TestCase( SECTION,    "var a\rb =  5; ab=10;b;",      5,      eval("var a\rb =  5; ab=10;b") );
this.TestCase( SECTION,    "var a\r\nb =  5; ab=10;ab;",     10,     eval("var a\r\nb =  5; ab=10;ab") );
this.TestCase( SECTION,    "var a\r\nb =  5; ab=10;b;",      5,      eval("var a\r\nb =  5; ab=10;b") );

//test();

},

/**
File Name:          7.2.js
ECMA Section:       7.2 Line Terminators
Description:        - readability
- separate tokens
- may occur between any two tokens
- cannot occur within any token, not even a string
- affect the process of automatic semicolon insertion.

white space characters are:
unicode     name            formal name     string representation
\u000A      line feed       <LF>            \n
\u000D      carriage return <CR>            \r

this test uses onerror to capture line numbers.  because
we use on error, we can only have one test case per file.

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_7_2__2__n: function() {
var SECTION = "7.2-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Line Terminators";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "\r\r\r\nb";
EXPECTED = "error";

this.TestCase( SECTION,   DESCRIPTION,     "error",     eval("\r\r\r\nb"));

//test();

},

/**
File Name:          7.2-3.js
ECMA Section:       7.2 Line Terminators
Description:        - readability
- separate tokens
- may occur between any two tokens
- cannot occur within any token, not even a string
- affect the process of automatic semicolon insertion.

white space characters are:
unicode     name            formal name     string representation
\u000A      line feed       <LF>            \n
\u000D      carriage return <CR>            \r

this test uses onerror to capture line numbers.  because
we use on error, we can only have one test case per file.

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_7_2__3__n: function() {
var SECTION = "7.2-3";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Line Terminators";

//writeHeaderToLog( SECTION + " "+ TITLE);


DESCRIPTION = "\r\nb";
EXPECTED = "error";

this.TestCase( SECTION,    "<cr>a",     "error",     eval("\r\nb"));

//test();

},

/**
File Name:          7.2.js
ECMA Section:       7.2 Line Terminators
Description:        - readability
- separate tokens
- may occur between any two tokens
- cannot occur within any token, not even a string
- affect the process of automatic semicolon insertion.

white space characters are:
unicode     name            formal name     string representation
\u000A      line feed       <LF>            \n
\u000D      carriage return <CR>            \r

this test uses onerror to capture line numbers.  because
we use on error, we can only have one test case per file.

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_7_2__4__n: function() {
var SECTION = "7.2-6";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Line Terminators";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "\nb";
EXPECTED = "error";

this.TestCase( SECTION,    "\nb",     "error",     eval("\nb"));

//test();

},

/**
File Name:          7.2.js
ECMA Section:       7.2 Line Terminators
Description:        - readability
- separate tokens
- may occur between any two tokens
- cannot occur within any token, not even a string
- affect the process of automatic semicolon insertion.

white space characters are:
unicode     name            formal name     string representation
\u000A      line feed       <LF>            \n
\u000D      carriage return <CR>            \r

this test uses onerror to capture line numbers.  because
we use on error, we can only have one test case per file.

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_7_2__5__n: function() {
var SECTION = "7.2-5";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Line Terminators";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION =
EXPECTED = "error";

this.TestCase( SECTION,    "\rb",     "error",    eval("\rb"));

//test();

},

/**
File Name:          7.2-6.js
ECMA Section:       7.2 Line Terminators
Description:        - readability
- separate tokens
- may occur between any two tokens
- cannot occur within any token, not even a string
- affect the process of automatic semicolon insertion.

white space characters are:
unicode     name            formal name     string representation
\u000A      line feed       <LF>            \n
\u000D      carriage return <CR>            \r

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_7_2__6: function() {
var SECTION = "7.2-6";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Line Terminators";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,    "var a\u000Ab =  5; ab=10;ab;",     10,     eval("var a\nb =  5; ab=10;ab") );
this.TestCase( SECTION,    "var a\u000Db =  5; ab=10;b;",      5,      eval("var a\nb =  5; ab=10;b") );

//test();

},

/**
File Name:          7.3-1.js
ECMA Section:       7.3 Comments
Description:


Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_3__1: function() {
var SECTION = "7.3-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Comments";

//writeHeaderToLog( SECTION + " "+ TITLE);

var gTestcases = new Array();
var testcase = new Object();

testcase = this.TestCaseAlloc( SECTION,
"a comment with a line terminator string, and text following",
"pass",
"pass");

// "\u000A" testcase.actual = "fail";
gTestcases[0] = testcase;


testcase = this.TestCaseAlloc( SECTION,
"// test \\n testcase.actual = \"pass\"",
"pass",
"" );
var x = "// test \n testcase.actual = 'pass'";
testcase.actual = eval(x);
gTestcases[1] = testcase;

//test();

// XXX bc replace test()
//function test() {
for ( gTc=0; gTc < gTestcases.length; gTc++ ) {
gTestcases[gTc].passed = this.writeTestCaseResult(
gTestcases[gTc].expect,
gTestcases[gTc].actual,
gTestcases[gTc].description +":  "+
gTestcases[gTc].actual );

gTestcases[gTc].reason += ( gTestcases[gTc].passed ) ? "" : " ignored chars after line terminator of single-line comment";
}
//stopTest();
//return { gTestcases );
//}

},

/**
File Name:          7.3-10.js
ECMA Section:       7.3 Comments
Description:


Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_3__10: function() {
var SECTION = "7.3-10";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Comments";

//writeHeaderToLog( SECTION + " "+ TITLE);

var testcase = this.TestCaseAlloc( SECTION,
"code following multiline comment",
"pass",
"fail");

/*//*/testcase.actual="pass";

assertTrue(this.getTestCaseResult(testcase.expect,testcase.actual));

//test();

},

/**
File Name:          7.3-11.js
ECMA Section:       7.3 Comments
Description:


Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_3__11: function() {
var SECTION = "7.3-11";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Comments";

//writeHeaderToLog( SECTION + " "+ TITLE);

var testcase =  this.TestCaseAlloc( SECTION,
"code following multiline comment",
"pass",
"pass");

////testcase.actual="fail";

assertTrue(this.getTestCaseResult(testcase.expect,testcase.actual));

//test();

},

/**
File Name:          7.3-12.js
ECMA Section:       7.3 Comments
Description:


Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_3__12: function() {
var SECTION = "7.3-12";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Comments";

//writeHeaderToLog( SECTION + " "+ TITLE);

var testcase =  this.TestCaseAlloc( SECTION,
"code following multiline comment",
"pass",
"pass");
/*testcase.actual="fail";**/

assertTrue(this.getTestCaseResult(testcase.expect,testcase.actual));

//test();

},

/**
File Name:          7.3-13-n.js
ECMA Section:       7.3 Comments
Description:


Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_3__13__n: function() {
var SECTION = "7.3-13-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Comments";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "nested comment";
EXPECTED = "error";

this.TestCase( SECTION,
"nested comment",
"error",
eval("/*/*\"fail\";*/*/"));

//test();

},

/**
File Name:          7.3-13-n.js
ECMA Section:       7.3 Comments
Description:


Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_3__13__n_WORKS: function() {
var SECTION = "7.3-13-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Comments";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "nested comment";
EXPECTED = "error";

try {
this.TestCase( SECTION,
"nested comment",
"error",
eval("/*/*\"fail\";*/*/"));
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'syntax error');
}

//test();

},

/**
File Name:          7.3-2.js
ECMA Section:       7.3 Comments
Description:


Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_3__2: function() {
var SECTION = "7.3-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Comments";

//writeHeaderToLog( SECTION + " "+ TITLE);

var testcase = this.TestCaseAlloc( SECTION,
"a comment with a carriage return, and text following",
"pass",
"pass");

// "\u000D" testcase.actual = "fail";

assertTrue(this.getTestCaseResult(testcase.expect,testcase.actual));

//test();

},

/**
File Name:          7.3-3.js
ECMA Section:       7.3 Comments
Description:


Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_3__3: function() {
var SECTION = "7.3-3";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Comments";

//writeHeaderToLog( SECTION + " "+ TITLE);

var testcase =  this.TestCaseAlloc( SECTION,
"source text directly following a single-line comment",
"pass",
"fail");

// a comment string
testcase.actual = "pass";

assertTrue(this.getTestCaseResult(testcase.expect,testcase.actual));

//test();

},

/**
File Name:          7.3-4.js
ECMA Section:       7.3 Comments
Description:


Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_3__4: function() {
var SECTION = "7.3-4";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Comments";

//writeHeaderToLog( SECTION + " "+ TITLE);

var testcase = this.TestCaseAlloc( SECTION,
"multiline comment ",
"pass",
"pass");

/*testcase.actual = "fail";*/

assertTrue(this.getTestCaseResult(testcase.expect,testcase.actual));

//test();
},

/**
File Name:          7.3-5.js
ECMA Section:       7.3 Comments
Description:


Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_3__5: function() {
var SECTION = "7.3-5";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Comments";

//writeHeaderToLog( SECTION + " "+ TITLE);

var testcase = this.TestCaseAlloc( SECTION,
"a comment with a carriage return, and text following",
"pass",
"pass");

// "\u000A" testcase.actual = "fail";

assertTrue(this.getTestCaseResult(testcase.expect,testcase.actual));

//test();

},

/**
File Name:          7.3-6.js
ECMA Section:       7.3 Comments
Description:


Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_3__6: function() {
var SECTION = "7.3-6";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Comments";

//writeHeaderToLog( SECTION + " "+ TITLE);
var testcase = this.TestCaseAlloc( SECTION,
"comment with multiple asterisks",
"pass",
"fail");

/*
***/testcase.actual="pass";

assertTrue(this.getTestCaseResult(testcase.expect,testcase.actual));

//test();

},

/**
File Name:          7.3-7.js
ECMA Section:       7.3 Comments
Description:


Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_3__7: function() {
var SECTION = "7.3-7";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Comments";

//writeHeaderToLog( SECTION + " "+ TITLE);

var testcase = this.TestCaseAlloc( SECTION,
"single line comment following multiline comment",
"pass",
"pass");

/*
***///testcase.actual="fail";

assertTrue(this.getTestCaseResult(testcase.expect,testcase.actual));

//test();

},

/**
File Name:          7.3-7.js
ECMA Section:       7.3 Comments
Description:


Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_3__8: function() {
var SECTION = "7.3-8";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Comments";

//writeHeaderToLog( SECTION + " "+ TITLE);

var testcase = this.TestCaseAlloc( SECTION,
"code following multiline comment",
"pass",
"fail");

/**/testcase.actual="pass";

assertTrue(this.getTestCaseResult(testcase.expect,testcase.actual));

//test();
},

/**
File Name:          7.3-9.js
ECMA Section:       7.3 Comments
Description:


Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_3__9: function() {
var SECTION = "7.3-9";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Comments";

//writeHeaderToLog( SECTION + " "+ TITLE);

var testcase = this.TestCaseAlloc( SECTION,
"code following multiline comment",
"pass",
"fail");

/*/*/testcase.actual="pass";

assertTrue(this.getTestCaseResult(testcase.expect,testcase.actual));

//test();
},

/**
File Name:          7.4.1-1-n.js
ECMA Section:       7.4.1

Description:

Reserved words cannot be used as identifiers.

ReservedWord ::
Keyword
FutureReservedWord
NullLiteral
BooleanLiteral

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_1__1__n: function() {
var SECTION = "7.4.1-1-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var null = true";
EXPECTED = "error";

this.TestCase( SECTION,  "var null = true",     "error",    eval("var null = true") );

//test();

},

/**
File Name:          7.4.1-1-n.js
ECMA Section:       7.4.1

Description:

Reserved words cannot be used as identifiers.

ReservedWord ::
Keyword
FutureReservedWord
NullLiteral
BooleanLiteral

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_1__1__n_WORKS: function() {
var SECTION = "7.4.1-1-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var null = true";
EXPECTED = "error";

try {
this.TestCase( SECTION,  "var null = true",     "error",    eval("var null = true") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.4.1-2.js
ECMA Section:       7.4.1

Description:

Reserved words cannot be used as identifiers.

ReservedWord ::
Keyword
FutureReservedWord
NullLiteral
BooleanLiteral

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_1__2__n: function() {
var SECTION = "7.4.1-2-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var true = false";
EXPECTED = "error";

this.TestCase( SECTION,  "var true = false",     "error",    eval("var true = false") );

//test();

},

/**
File Name:          7.4.1-2.js
ECMA Section:       7.4.1

Description:

Reserved words cannot be used as identifiers.

ReservedWord ::
Keyword
FutureReservedWord
NullLiteral
BooleanLiteral

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_1__2__n_WORKS: function() {
var SECTION = "7.4.1-2-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var true = false";
EXPECTED = "error";

try {
this.TestCase( SECTION,  "var true = false",     "error",    eval("var true = false") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.4.1-3-n.js
ECMA Section:       7.4.1

Description:

Reserved words cannot be used as identifiers.

ReservedWord ::
Keyword
FutureReservedWord
NullLiteral
BooleanLiteral

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_1__3__n: function() {
var SECTION = "7.4.1-3-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

DESCRIPTION = "var false = true";
EXPECTED = "error";

this.TestCase( SECTION,  "var false = true",     "error",    eval("var false = true") );

//test();

},

/**
File Name:          7.4.1-3-n.js
ECMA Section:       7.4.1

Description:

Reserved words cannot be used as identifiers.

ReservedWord ::
Keyword
FutureReservedWord
NullLiteral
BooleanLiteral

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_1__3__n_WORKS: function() {
var SECTION = "7.4.1-3-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

DESCRIPTION = "var false = true";
EXPECTED = "error";

try {
this.TestCase( SECTION,  "var false = true",     "error",    eval("var false = true") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.4.2-1.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__1__n: function() {
var SECTION = "7.4.2-1-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var break = true";
EXPECTED = "error";

this.TestCase( SECTION,  "var break = true",     "error",    eval("var break = true") );

//test();

},

/**
File Name:          7.4.2-1.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__1__n_WORKS: function() {
var SECTION = "7.4.2-1-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var break = true";
EXPECTED = "error";

try {
this.TestCase( SECTION,  "var break = true",     "error",    eval("var break = true") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.4.2-10.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__10__n: function() {
var SECTION = "7.4.1-10-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var if = true";
EXPECTED = "error";

this.TestCase( SECTION,  "var if = true",     "error",    eval("var if = true") );

//test();

},

/**
File Name:          7.4.2-10.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__10__n_WORKS: function() {
var SECTION = "7.4.1-10-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var if = true";
EXPECTED = "error";

try {
this.TestCase( SECTION,  "var if = true",     "error",    eval("var if = true") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.4.2-11-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__11__n: function() {
var SECTION = "7.4.1-11-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var this = true";
EXPECTED = "error";

this.TestCase( SECTION,  "var this = true",     "error",    eval("var this = true") );

//test();

},

/**
File Name:          7.4.2-11-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__11__n_WORKS: function() {
var SECTION = "7.4.1-11-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var this = true";
EXPECTED = "error";

try {
this.TestCase( SECTION,  "var this = true",     "error",    eval("var this = true") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.4.2-12-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__12__n: function() {
var SECTION = "7.4.1-12-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var while = true";
EXPECTED = "error";

this.TestCase( SECTION,  "var while = true",     "error",    eval("var while = true") );

//test();

},

/**
File Name:          7.4.2-12-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__12__n_WORKS: function() {
var SECTION = "7.4.1-12-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var while = true";
EXPECTED = "error";

try {
this.TestCase( SECTION,  "var while = true",     "error",    eval("var while = true") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.4.2-13-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__13__n: function() {
var SECTION = "7.4.1-13-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var else = true";
EXPECTED = "error";

this.TestCase( SECTION,  "var else = true",     "error",    eval("var else = true") );

//test();

},

/**
File Name:          7.4.2-13-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__13__n_WORKS: function() {
var SECTION = "7.4.1-13-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var else = true";
EXPECTED = "error";

try {
this.TestCase( SECTION,  "var else = true",     "error",    eval("var else = true") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.4.2-14-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__14__n: function() {
var SECTION = "7.4.1-14-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var in = true";
EXPECTED = "error";

this. TestCase( SECTION,  "var in = true",     "error",    eval("var in = true") );

//test();

},

/**
File Name:          7.4.2-14-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__14__n_WORKS: function() {
var SECTION = "7.4.1-14-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var in = true";
EXPECTED = "error";

try {
this. TestCase( SECTION,  "var in = true",     "error",    eval("var in = true") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.4.2-15-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__15__n: function() {
var SECTION = "7.4.1-15-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var typeof = true";
EXPECTED = "error";

this.TestCase( SECTION,  "var typeof = true",     "error",    eval("var typeof = true") );

//test();

},

/**
File Name:          7.4.2-15-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__15__n_WORKS: function() {
var SECTION = "7.4.1-15-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var typeof = true";
EXPECTED = "error";

try {
this.TestCase( SECTION,  "var typeof = true",     "error",    eval("var typeof = true") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.4.2-16-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__16__n: function() {
var SECTION = "7.4.1-16-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var with = true";
EXPECTED = "error";

this.TestCase( SECTION,  "var with = true",     "error",    eval("var with = true") );

//test();

},

/**
File Name:          7.4.2-16-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__16__n_WORKS: function() {
var SECTION = "7.4.1-16-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var with = true";
EXPECTED = "error";

try {
this.TestCase( SECTION,  "var with = true",     "error",    eval("var with = true") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.4.2-2-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__2__n: function() {
var SECTION = "7.4.1-2-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var for = true";
EXPECTED = "error";

this.TestCase( SECTION,  "var for = true",     "error",    eval("var for = true") );

//test();

},

/**
File Name:          7.4.2-2-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__2__n_WORKS: function() {
var SECTION = "7.4.1-2-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var for = true";
EXPECTED = "error";

try {
this.TestCase( SECTION,  "var for = true",     "error",    eval("var for = true") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.4.2-3-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__3__n: function() {
var SECTION = "7.4.2-3-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var new = true";
EXPECTED = "error";

this.TestCase( SECTION,  "var new = true",     "error",    eval("var new = true") );

//test();

},

/**
File Name:          7.4.2-3-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__3__n_WORKS: function() {
var SECTION = "7.4.2-3-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var new = true";
EXPECTED = "error";

try {
this.TestCase( SECTION,  "var new = true",     "error",    eval("var new = true") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.4.2-4-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__4__n: function() {
var SECTION = "7.4.2-4-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var var = true";
EXPECTED = "error";

this.TestCase( SECTION,  "var var = true",     "error",    eval("var var = true") );

//test();

},

/**
File Name:          7.4.2-4-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__4__n_WORKS: function() {
var SECTION = "7.4.2-4-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var var = true";
EXPECTED = "error";

try {
this.TestCase( SECTION,  "var var = true",     "error",    eval("var var = true") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.4.2-5-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__5__n: function() {
var SECTION = "7.4.2-5-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var continue = true";
EXPECTED = "error";

this.TestCase( SECTION,  "var continue = true",     "error",    eval("var continue = true") );

//test();

},

/**
File Name:          7.4.2-5-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__5__n_WORKS: function() {
var SECTION = "7.4.2-5-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var continue = true";
EXPECTED = "error";

try {
this.TestCase( SECTION,  "var continue = true",     "error",    eval("var continue = true") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.4.2-6.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__6__n: function() {
var SECTION = "7.4.2-6-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var function = true";
EXPECTED = "error";

this.TestCase( SECTION,  "var function = true",     "error",    eval("var function = true") );

//test();

},

/**
File Name:          7.4.2-6.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__6__n_WORKS: function() {
var SECTION = "7.4.2-6-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var function = true";
EXPECTED = "error";

try {
this.TestCase( SECTION,  "var function = true",     "error",    eval("var function = true") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.4.2-7-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__7__n: function() {
var SECTION = "7.4.2-7";
var VERSION = "ECMA_1";
//startTest();
//writeHeaderToLog( SECTION + " Keywords");

DESCRIPTION = "var return = true";
EXPECTED = "error";

this.TestCase( SECTION,  "var return = true",     "error",    eval("var return = true") );

//test();

},

/**
File Name:          7.4.2-7-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__7__n_WORKS: function() {
var SECTION = "7.4.2-7";
var VERSION = "ECMA_1";
//startTest();
//writeHeaderToLog( SECTION + " Keywords");

DESCRIPTION = "var return = true";
EXPECTED = "error";

try {
this.TestCase( SECTION,  "var return = true",     "error",    eval("var return = true") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.4.2-8-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__8__n: function() {
var SECTION = "7.4.2-8";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Keywords");

DESCRIPTION = "var void = true";
EXPECTED = "error";

this.TestCase( SECTION,  "var void = true",     "error",    eval("var void = true") );

//test();

},

/**
File Name:          7.4.2-8-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__8__n_WORKS: function() {
var SECTION = "7.4.2-8";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Keywords");

DESCRIPTION = "var void = true";
EXPECTED = "error";

try {
this.TestCase( SECTION,  "var void = true",     "error",    eval("var void = true") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.4.2-9-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__9__n: function() {
var SECTION = "7.4.1-9-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var delete = true";
EXPECTED = "error";

this.TestCase( SECTION,  "var delete = true",     "error",    eval("var delete = true") );

//test();

},

/**
File Name:          7.4.2-9-n.js
ECMA Section:       7.4.2

Description:
The following tokens are ECMAScript keywords and may not be used as
identifiers in ECMAScript programs.

Syntax

Keyword :: one of
break          for         new         var
continue       function    return      void
delete         if          this        while
else           in          typeof      with

This test verifies that the keyword cannot be used as an identifier.
Functioinal tests of the keyword may be found in the section corresponding
to the function of the keyword.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_7_4_2__9__n_WORKS: function() {
var SECTION = "7.4.1-9-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Keywords";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var delete = true";
EXPECTED = "error";

try {
this.TestCase( SECTION,  "var delete = true",     "error",    eval("var delete = true") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.4.3-1-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_7_4_3__1__n: function() {
var SECTION = "7.4.3-1-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Future Reserved Words";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var case = true";
EXPECTED = "error";

this.TestCase( SECTION,  "var case = true",     "error",    eval("var case = true") );

//test();

},

/**
File Name:          7.4.3-1-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_7_4_3__1__n_WORKS: function() {
var SECTION = "7.4.3-1-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Future Reserved Words";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var case = true";
EXPECTED = "error";

try {
this.TestCase( SECTION,  "var case = true",     "error",    eval("var case = true") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.4.3-10-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_7_4_3__10__n: function() {
var SECTION = "7.4.3-10-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Future Reserved Words";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var do = true";
EXPECTED = "error";

this.TestCase( SECTION,  "var do = true",     "error",    eval("var do = true") );

//test();

},

/**
File Name:          7.4.3-10-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_7_4_3__10__n_WORKS: function() {
var SECTION = "7.4.3-10-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Future Reserved Words";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var do = true";
EXPECTED = "error";

try {
this.TestCase( SECTION,  "var do = true",     "error",    eval("var do = true") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.4.3-11-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_7_4_3__11__n: function() {
var SECTION = "7.4.3-11-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Future Reserved Words";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var finally = true";
EXPECTED = "error";

this.TestCase( SECTION,  "var finally = true",     "error",    eval("var finally = true") );

//test();

},

/**
File Name:          7.4.3-11-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_7_4_3__11__n_WORKS: function() {
var SECTION = "7.4.3-11-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Future Reserved Words";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var finally = true";
EXPECTED = "error";

try {
this.TestCase( SECTION,  "var finally = true",     "error",    eval("var finally = true") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.4.3-12-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_7_4_3__12__n: function() {
var SECTION = "7.4.3-12-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Future Reserved Words";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var throw = true";
EXPECTED = "error";

this.TestCase( SECTION,  "var throw = true",     "error",    eval("var throw = true") );

//test();

},

/**
File Name:          7.4.3-12-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_7_4_3__12__n_WORKS: function() {
var SECTION = "7.4.3-12-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Future Reserved Words";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var throw = true";
EXPECTED = "error";

try {
this.TestCase( SECTION,  "var throw = true",     "error",    eval("var throw = true") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.4.3-13-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_7_4_3__13__n: function() {
var SECTION = "7.4.3-13-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Future Reserved Words";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var const = true";
EXPECTED = "error";

this.TestCase( SECTION,  "var const = true",     "error",    eval("var const = true") );

//test();

},

/**
File Name:          7.4.3-13-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_7_4_3__13__n_WORKS: function() {
var SECTION = "7.4.3-13-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Future Reserved Words";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var const = true";
EXPECTED = "error";

try {
this.TestCase( SECTION,  "var const = true",     "error",    eval("var const = true") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.4.3-14-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_7_4_3__14__n: function() {
var SECTION = "7.4.3-14-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Future Reserved Words";

//writeHeaderToLog( SECTION + " "+ TITLE);

var actual = 'no error';
var prefValue;

//print("This test requires option javascript.options.strict enabled");

if (!options().match(/strict/))
{
options('strict');
}
if (!options().match(/werror/))
{
options('werror');
}

try
{
eval("var enum = true");
}
catch(e)
{
actual = 'error';
}

DESCRIPTION = "var enum = true";
EXPECTED = "error";

// force exception since this is a negative test
if (actual == 'error')
{
throw actual;
}

this.TestCase( SECTION,
"var enum = true",
"error",
actual );

//test();

},

/**
File Name:          7.4.3-15-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_7_4_3__15__n: function() {
var SECTION = "7.4.3-15-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Future Reserved Words";

//writeHeaderToLog( SECTION + " "+ TITLE);

var actual = 'no error';
var prefValue;

//print("This test requires option javascript.options.strict enabled");

options('strict');
options('werror');

try
{
eval("var import = true");
}
catch(e)
{
actual = 'error';
}

DESCRIPTION = "var import = true";
EXPECTED = "error";

// force exception since this is a negative test
if (actual == 'error')
{
throw actual;
}

this.TestCase( SECTION,
"var import = true",
"error",
actual );

//test();

},

/**
File Name:          lexical-023.js
Corresponds To:     7.4.3-16-n.js
ECMA Section:       7.4.3
Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_7_4_3__16__n: function() {
var SECTION = "lexical-023.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Future Reserved Words";

//writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";
/*
try {
try = true;
} catch ( e ) {
result = expect;
exception = e.toString();
}
*/

DESCRIPTION = "try = true";
EXPECTED = "error";

this.TestCase(
SECTION,
"try = true" +
" (threw " + exception +")",
"error",
eval("try = true") );

//test();

},

/**
File Name:          lexical-023.js
Corresponds To:     7.4.3-16-n.js
ECMA Section:       7.4.3
Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_7_4_3__16__n_WORKS: function() {
var SECTION = "lexical-023.js";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Future Reserved Words";

//writeHeaderToLog( SECTION + " "+ TITLE);

var result = "Failed";
var exception = "No exception thrown";
var expect = "Passed";
/*
try {
try = true;
} catch ( e ) {
result = expect;
exception = e.toString();
}
*/

DESCRIPTION = "try = true";
EXPECTED = "error";

try {
this.TestCase(
SECTION,
"try = true" +
" (threw " + exception +")",
"error",
eval("try = true") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing { before try block');
}

//test();

},

/**
File Name:          7.4.3-2-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_7_4_3__2__n: function() {
var SECTION = "7.4.3-2-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Future Reserved Words";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var debugger = true";
EXPECTED = "error";

this.TestCase( SECTION,  "var debugger = true",     "error",    eval("var debugger = true") );

//test();

},

/**
File Name:          7.4.3-2-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_7_4_3__2__n_WORKS: function() {
var SECTION = "7.4.3-2-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Future Reserved Words";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var debugger = true";
EXPECTED = "error";

try {
this.TestCase( SECTION,  "var debugger = true",     "error",    eval("var debugger = true") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.4.3-3-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_7_4_3__3__n: function() {
var SECTION = "7.4.3-3-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Future Reserved Words";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var export = true";
EXPECTED = "error";

this.TestCase( SECTION,  "var export = true",     "error",    eval("var export = true") );

//test();

},

/**
File Name:          7.4.3-3-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_7_4_3__3__n_WORKS: function() {
var SECTION = "7.4.3-3-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Future Reserved Words";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var export = true";
EXPECTED = "error";

try {
this.TestCase( SECTION,  "var export = true",     "error",    eval("var export = true") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.4.3-4-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_7_4_3__4__n: function() {
var SECTION = "7.4.3-4-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Future Reserved Words";

//writeHeaderToLog( SECTION + " "+ TITLE);

var actual = 'no error';
var prefValue;

//print("This test requires option javascript.options.strict enabled");

if (!options().match(/strict/))
{
options('strict');
}
if (!options().match(/werror/))
{
options('werror');
}

try
{
eval("var super = true");
}
catch(e)
{
actual = 'error';
}

DESCRIPTION = "var super = true"
EXPECTED = "error";

// force exception since this is a negative test
if (actual == 'error')
{
throw actual;
}

this.TestCase( SECTION,
"var super = true",
"error",
actual );

//test();

},

/**
File Name:          7.4.3-5-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_7_4_3__5__n: function() {
var SECTION = "7.4.3-5-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Future Reserved Words";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var catch = true";
EXPECTED = "error";

this.TestCase( SECTION,  "var catch = true",     "error",    eval("var catch = true") );

//test();

},

/**
File Name:          7.4.3-5-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_7_4_3__5__n_WORKS: function() {
var SECTION = "7.4.3-5-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Future Reserved Words";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var catch = true";
EXPECTED = "error";

try {
this.TestCase( SECTION,  "var catch = true",     "error",    eval("var catch = true") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.4.3-6-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_7_4_3__6__n: function() {
var SECTION = "7.4.3-6-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Future Reserved Words";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var default = true";
EXPECTED = "error";

this.TestCase( SECTION,  "var default = true",     "error",    eval("var default = true") );

//test();

},

/**
File Name:          7.4.3-6-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_7_4_3__6__n_WORKS: function() {
var SECTION = "7.4.3-6-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Future Reserved Words";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var default = true";
EXPECTED = "error";

try {
this.TestCase( SECTION,  "var default = true",     "error",    eval("var default = true") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.4.3-7-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_7_4_3__7__n: function() {
var SECTION = "7.4.3-7-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Future Reserved Words";

//writeHeaderToLog( SECTION + " "+ TITLE);

var actual = 'no error';
var prefValue;

//print("This test requires option javascript.options.strict enabled");

if (!options().match(/strict/))
{
options('strict');
}
if (!options().match(/werror/))
{
options('werror');
}

try
{
eval("var extends = true");
}
catch(e)
{
actual = 'error';
}

DESCRIPTION = "var extends = true";
EXPECTED = "error";

// force exception since this is a negative test
if (actual == 'error')
{
throw actual;
}

this.TestCase( SECTION,
"var extends = true",
"error",
actual);

//test();

},

/**
File Name:          7.4.3-8-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_7_4_3__8__n: function() {
var SECTION = "7.4.3-9-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Future Reserved Words";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var switch = true";
EXPECTED = "error";

this.TestCase( SECTION,  "var switch = true",     "error",    eval("var switch = true") );

//test();

},

/**
File Name:          7.4.3-8-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_7_4_3__8__n_WORKS: function() {
var SECTION = "7.4.3-9-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Future Reserved Words";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var switch = true";
EXPECTED = "error";

try {
this.TestCase( SECTION,  "var switch = true",     "error",    eval("var switch = true") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.4.3-9-n.js
ECMA Section:       7.4.3

Description:
The following words are used as keywords in proposed extensions and are
therefore reserved to allow for the possibility of future adoption of
those extensions.

FutureReservedWord :: one of
case    debugger    export      super
catch   default     extends     switch
class   do          finally     throw
const   enum        import      try

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_7_4_3__9__n: function() {
var SECTION = "7.4.3-9-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Future Reserved Words";

//writeHeaderToLog( SECTION + " "+ TITLE);

var actual = 'no error';
var prefValue;

DESCRIPTION = "var class = true";
EXPECTED = "error";


//print("This test requires option javascript.options.strict enabled");

if (!options().match(/strict/))
{
options('strict');
}
if (!options().match(/werror/))
{
options('werror');
}

try
{
eval("var class = true");
}
catch(e)
{
actual = 'error';
}

// force exception since this is a negative test
if (actual == 'error')
{
throw actual;
}

this.TestCase( SECTION,
"var class = true",
"error",
actual );

//test();

},

/**
File Name:          7.5-1.js
ECMA Section:       7.5 Identifiers
Description:        Identifiers are of unlimited length
- can contain letters, a decimal digit, _, or $
- the first character cannot be a decimal digit
- identifiers are case sensitive

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_7_5_1: function() {
var SECTION = "7.5-1";
var VERSION = "ECMA_1";

var TITLE   = "Identifiers";

this.TestCase( SECTION,    "var $123 = 5",      5,       eval("var $123 = 5;$123") );
this.TestCase( SECTION,    "var _123 = 5",      5,       eval("var _123 = 5;_123") );

},

/**
File Name:          7.5-9-n.js
ECMA Section:       7.5 Identifiers
Description:        Identifiers are of unlimited length
- can contain letters, a decimal digit, _, or $
- the first character cannot be a decimal digit
- identifiers are case sensitive

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_7_5_10__n: function() {
var SECTION = "7.5-9-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Identifiers";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var 123=\"hi\"";
EXPECTED = "error";

this.TestCase( SECTION,    "var 123=\"hi\"",   "error",    eval("123 = \"hi\"; array[item] = 123;") );

//test();

},

/**
File Name:          7.5-9-n.js
ECMA Section:       7.5 Identifiers
Description:        Identifiers are of unlimited length
- can contain letters, a decimal digit, _, or $
- the first character cannot be a decimal digit
- identifiers are case sensitive

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_7_5_10__n_WORKS: function() {
var SECTION = "7.5-9-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Identifiers";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var 123=\"hi\"";
EXPECTED = "error";

try {
this.TestCase( SECTION,    "var 123=\"hi\"",   "error",    eval("123 = \"hi\"; array[item] = 123;") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'Invalid assignment left-hand side.');
}

//test();

},

/**
File Name:          7.5-2-n.js
ECMA Section:       7.5 Identifiers
Description:        Identifiers are of unlimited length
- can contain letters, a decimal digit, _, or $
- the first character cannot be a decimal digit
- identifiers are case sensitive

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_7_5_2__n: function() {
var SECTION = "7.5-2-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Identifiers";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var 0abc";
EXPECTED = "error";

this.TestCase( SECTION,    "var 0abc",   "error",    eval("var 0abc") );

//test();

},

/**
File Name:          7.5-2-n.js
ECMA Section:       7.5 Identifiers
Description:        Identifiers are of unlimited length
- can contain letters, a decimal digit, _, or $
- the first character cannot be a decimal digit
- identifiers are case sensitive

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_7_5_2__n_WORKS: function() {
var SECTION = "7.5-2-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Identifiers";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var 0abc";
EXPECTED = "error";

try {
this.TestCase( SECTION,    "var 0abc",   "error",    eval("var 0abc") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.5-2.js
ECMA Section:       7.5 Identifiers
Description:        Identifiers are of unlimited length
- can contain letters, a decimal digit, _, or $
- the first character cannot be a decimal digit
- identifiers are case sensitive

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_7_5_3__n: function() {
var SECTION = "7.5-3-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Identifiers";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var 1abc";
EXPECTED = "error";

this.TestCase( SECTION,    "var 1abc",   "error",    eval("var 1abc") );

//test();

},

/**
File Name:          7.5-2.js
ECMA Section:       7.5 Identifiers
Description:        Identifiers are of unlimited length
- can contain letters, a decimal digit, _, or $
- the first character cannot be a decimal digit
- identifiers are case sensitive

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_7_5_3__n_WORKS: function() {
var SECTION = "7.5-3-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Identifiers";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var 1abc";
EXPECTED = "error";

try {
this.TestCase( SECTION,    "var 1abc",   "error",    eval("var 1abc") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.5-4-n.js
ECMA Section:       7.5 Identifiers
Description:        Identifiers are of unlimited length
- can contain letters, a decimal digit, _, or $
- the first character cannot be a decimal digit
- identifiers are case sensitive

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_7_5_4__n: function() {
var SECTION = "7.5-4-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Identifiers";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var 2abc";
EXPECTED = "error";

this.TestCase( SECTION,    "var 2abc",   "error",    eval("var 2abc") );

//test();

},

/**
File Name:          7.5-4-n.js
ECMA Section:       7.5 Identifiers
Description:        Identifiers are of unlimited length
- can contain letters, a decimal digit, _, or $
- the first character cannot be a decimal digit
- identifiers are case sensitive

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_7_5_4__n_WORKS: function() {
var SECTION = "7.5-4-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Identifiers";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var 2abc";
EXPECTED = "error";

try {
this.TestCase( SECTION,    "var 2abc",   "error",    eval("var 2abc") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.5-5-n.js
ECMA Section:       7.5 Identifiers
Description:        Identifiers are of unlimited length
- can contain letters, a decimal digit, _, or $
- the first character cannot be a decimal digit
- identifiers are case sensitive

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_7_5_5__n: function() {
var SECTION = "7.5-5-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Identifiers";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var 3abc";
EXPECTED = "error";

this.TestCase( SECTION,    "var 3abc",   "error",    eval("var 3abc") );

//test();

},

/**
File Name:          7.5-5-n.js
ECMA Section:       7.5 Identifiers
Description:        Identifiers are of unlimited length
- can contain letters, a decimal digit, _, or $
- the first character cannot be a decimal digit
- identifiers are case sensitive

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_7_5_5__n_WORKS: function() {
var SECTION = "7.5-5-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Identifiers";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var 3abc";
EXPECTED = "error";

try {
this.TestCase( SECTION,    "var 3abc",   "error",    eval("var 3abc") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.5-6.js
ECMA Section:       7.5 Identifiers
Description:        Identifiers are of unlimited length
- can contain letters, a decimal digit, _, or $
- the first character cannot be a decimal digit
- identifiers are case sensitive

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_7_5_6: function() {
var SECTION = "7.5-6";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Identifiers";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,    "var _0abc = 5",   5,    eval("var _0abc = 5; _0abc") );

//test();

},

/**
File Name:          7.5-7.js
ECMA Section:       7.5 Identifiers
Description:        Identifiers are of unlimited length
- can contain letters, a decimal digit, _, or $
- the first character cannot be a decimal digit
- identifiers are case sensitive

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_7_5_7: function() {
var SECTION = "7.5-7";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Identifiers";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,    "var $0abc = 5",   5,    eval("var $0abc = 5; $0abc") );

//test();

},

/**
File Name:          7.5-8-n.js
ECMA Section:       7.5 Identifiers
Description:        Identifiers are of unlimited length
- can contain letters, a decimal digit, _, or $
- the first character cannot be a decimal digit
- identifiers are case sensitive

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_7_5_8__n: function() {
var SECTION = "7.5-8-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Identifiers";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var @0abc = 5; @0abc";
EXPECTED = "error";

this.TestCase( SECTION,    "var @0abc = 5; @0abc",   "error",    eval("var @0abc = 5; @0abc") );

//test();

},

/**
File Name:          7.5-8-n.js
ECMA Section:       7.5 Identifiers
Description:        Identifiers are of unlimited length
- can contain letters, a decimal digit, _, or $
- the first character cannot be a decimal digit
- identifiers are case sensitive

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_7_5_8__n_WORKS: function() {
var SECTION = "7.5-8-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Identifiers";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var @0abc = 5; @0abc";
EXPECTED = "error";

try {
this.TestCase( SECTION,    "var @0abc = 5; @0abc",   "error",    eval("var @0abc = 5; @0abc") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.5-9-n.js
ECMA Section:       7.5 Identifiers
Description:        Identifiers are of unlimited length
- can contain letters, a decimal digit, _, or $
- the first character cannot be a decimal digit
- identifiers are case sensitive

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_7_5_9__n: function() {
var SECTION = "7.5-9-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Identifiers";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var 123=\"hi\"";
EXPECTED = "error";

this.TestCase( SECTION,    "var 123=\"hi\"",   "error",    eval("var 123 = \"hi\";array[item] = 123;") );

//test();

},

/**
File Name:          7.5-9-n.js
ECMA Section:       7.5 Identifiers
Description:        Identifiers are of unlimited length
- can contain letters, a decimal digit, _, or $
- the first character cannot be a decimal digit
- identifiers are case sensitive

Author:             christine@netscape.com
Date:               11 september 1997
*/
test_7_5_9__n_WORKS: function() {
var SECTION = "7.5-9-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Identifiers";

//writeHeaderToLog( SECTION + " "+ TITLE);

DESCRIPTION = "var 123=\"hi\"";
EXPECTED = "error";

try {
this.TestCase( SECTION,    "var 123=\"hi\"",   "error",    eval("var 123 = \"hi\";array[item] = 123;") );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing variable name');
}

//test();

},

/**
File Name:          7.6.js
ECMA Section:       Punctuators
Description:

This tests verifies that all ECMA punctutors are recognized as a
token separator, but does not attempt to verify the functionality
of any punctuator.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_7_6: function() {
var SECTION = "7.6";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Punctuators";

//writeHeaderToLog( SECTION + " "+ TITLE);

// ==
this.TestCase( SECTION,
"var c,d;c==d",
true,
eval("var c,d;c==d") );

// =

this.TestCase( SECTION,
"var a=true;a",
true,
eval("var a=true;a") );

// >
this.TestCase( SECTION,
"var a=true,b=false;a>b",
true,
eval("var a=true,b=false;a>b") );

// <
this.TestCase( SECTION,
"var a=true,b=false;a<b",
false,
eval("var a=true,b=false;a<b") );

// <=
this.TestCase( SECTION,
"var a=0xFFFF,b=0X0FFF;a<=b",
false,
eval("var a=0xFFFF,b=0X0FFF;a<=b") );

// >=
this.TestCase( SECTION,
"var a=0xFFFF,b=0XFFFE;a>=b",
true,
eval("var a=0xFFFF,b=0XFFFE;a>=b") );

// !=
this.TestCase( SECTION,
"var a=true,b=false;a!=b",
true,
eval("var a=true,b=false;a!=b") );

this.TestCase( SECTION,
"var a=false,b=false;a!=b",
false,
eval("var a=false,b=false;a!=b") );
// ,
this.TestCase( SECTION,
"var a=true,b=false;a,b",
false,
eval("var a=true,b=false;a,b") );
// !
this.TestCase( SECTION,
"var a=true,b=false;!a",
false,
eval("var a=true,b=false;!a") );

// ~
this.TestCase( SECTION,
"var a=true;~a",
-2,
eval("var a=true;~a") );
// ?
this.TestCase( SECTION,
"var a=true; (a ? 'PASS' : '')",
"PASS",
eval("var a=true; (a ? 'PASS' : '')") );

// :

this.TestCase( SECTION,
"var a=false; (a ? 'FAIL' : 'PASS')",
"PASS",
eval("var a=false; (a ? 'FAIL' : 'PASS')") );
// .

this.TestCase( SECTION,
"var a=Number;a.NaN",
NaN,
eval("var a=Number;a.NaN") );

// &&
this.TestCase( SECTION,
"var a=true,b=true;if(a&&b)'PASS';else'FAIL'",
"PASS",
eval("var a=true,b=true;if(a&&b)'PASS';else'FAIL'") );

// ||
this.TestCase( SECTION,
"var a=false,b=false;if(a||b)'FAIL';else'PASS'",
"PASS",
eval("var a=false,b=false;if(a||b)'FAIL';else'PASS'") );
// ++
this.TestCase( SECTION,
"var a=false,b=false;++a",
1,
eval("var a=false,b=false;++a") );
// --
this.TestCase( SECTION,
"var a=true,b=false--a",
0,
eval("var a=true,b=false;--a") );
// +

this.TestCase( SECTION,
"var a=true,b=true;a+b",
2,
eval("var a=true,b=true;a+b") );
// -
this.TestCase( SECTION,
"var a=true,b=true;a-b",
0,
eval("var a=true,b=true;a-b") );
// *
this.TestCase( SECTION,
"var a=true,b=true;a*b",
1,
eval("var a=true,b=true;a*b") );
// /
this.TestCase( SECTION,
"var a=true,b=true;a/b",
1,
eval("var a=true,b=true;a/b") );
// &
this.TestCase( SECTION,
"var a=3,b=2;a&b",
2,
eval("var a=3,b=2;a&b") );
// |
this.TestCase( SECTION,
"var a=4,b=3;a|b",
7,
eval("var a=4,b=3;a|b") );

// |
this.TestCase( SECTION,
"var a=4,b=3;a^b",
7,
eval("var a=4,b=3;a^b") );

// %
this.TestCase( SECTION,
"var a=4,b=3;a|b",
1,
eval("var a=4,b=3;a%b") );

// <<
this.TestCase( SECTION,
"var a=4,b=3;a<<b",
32,
eval("var a=4,b=3;a<<b") );

//  >>
this.TestCase( SECTION,
"var a=4,b=1;a>>b",
2,
eval("var a=4,b=1;a>>b") );

//  >>>
this.TestCase( SECTION,
"var a=1,b=1;a>>>b",
0,
eval("var a=1,b=1;a>>>b") );
//  +=
this.TestCase( SECTION,
"var a=4,b=3;a+=b;a",
7,
eval("var a=4,b=3;a+=b;a") );

//  -=
this.TestCase( SECTION,
"var a=4,b=3;a-=b;a",
1,
eval("var a=4,b=3;a-=b;a") );
//  *=
this.TestCase( SECTION,
"var a=4,b=3;a*=b;a",
12,
eval("var a=4,b=3;a*=b;a") );
//  +=
this.TestCase( SECTION,
"var a=4,b=3;a+=b;a",
7,
eval("var a=4,b=3;a+=b;a") );
//  /=
this.TestCase( SECTION,
"var a=12,b=3;a/=b;a",
4,
eval("var a=12,b=3;a/=b;a") );

//  &=
this.TestCase( SECTION,
"var a=4,b=5;a&=b;a",
4,
eval("var a=4,b=5;a&=b;a") );

// |=
this.TestCase( SECTION,
"var a=4,b=5;a&=b;a",
5,
eval("var a=4,b=5;a|=b;a") );
//  ^=
this.TestCase( SECTION,
"var a=4,b=5;a^=b;a",
1,
eval("var a=4,b=5;a^=b;a") );
// %=
this.TestCase( SECTION,
"var a=12,b=5;a%=b;a",
2,
eval("var a=12,b=5;a%=b;a") );
// <<=
this.TestCase( SECTION,
"var a=4,b=3;a<<=b;a",
32,
eval("var a=4,b=3;a<<=b;a") );

//  >>
this.TestCase( SECTION,
"var a=4,b=1;a>>=b;a",
2,
eval("var a=4,b=1;a>>=b;a") );

//  >>>
this.TestCase( SECTION,
"var a=1,b=1;a>>>=b;a",
0,
eval("var a=1,b=1;a>>>=b;a") );

// ()
this.TestCase( SECTION,
"var a=4,b=3;(a)",
4,
eval("var a=4,b=3;(a)") );
// {}
this.TestCase( SECTION,
"var a=4,b=3;{b}",
3,
eval("var a=4,b=3;{b}") );

// []
this.TestCase( SECTION,
"var a=new Array('hi');a[0]",
"hi",
eval("var a=new Array('hi');a[0]") );
// []
this.TestCase( SECTION,
";",
void 0,
eval(";") );

//test();

},

/**
File Name:          7.7.1.js
ECMA Section:       7.7.1 Null Literals

Description:        NullLiteral::
null


The value of the null literal null is the sole value
of the Null type, namely null.

Author:             christine@netscape.com
Date:               21 october 1997
*/
test_7_7_1: function() {
var SECTION = "7.7.1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Null Literals";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION, "null",     null,        null);

//test();

},

/**
File Name:          7.7.2.js
ECMA Section:       7.7.2 Boolean Literals

Description:        BooleanLiteral::
true
false

The value of the Boolean literal true is a value of the
Boolean type, namely true.

The value of the Boolean literal false is a value of the
Boolean type, namely false.

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_7_7_2: function() {
var SECTION = "7.7.2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Boolean Literals";

//writeHeaderToLog( SECTION + " "+ TITLE);

// StringLiteral:: "" and ''

this.TestCase( SECTION, "true",     Boolean(true),     true );
this.TestCase( SECTION, "false",    Boolean(false),    false );

//test();

},

/**
File Name:          7.7.3-1.js
ECMA Section:       7.7.3 Numeric Literals

Description:        A numeric literal stands for a value of the Number type
This value is determined in two steps:  first a
mathematical value (MV) is derived from the literal;
second, this mathematical value is rounded, ideally
using IEEE 754 round-to-nearest mode, to a reprentable
value of of the number type.

These test cases came from Waldemar.

Author:             christine@netscape.com
Date:               12 June 1998
*/
test_7_7_3__1: function() {
var SECTION = "7.7.3-1";
var VERSION = "ECMA_1";
var TITLE   = "Numeric Literals";
var BUGNUMBER="122877";

//startTest();

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"0x12345678",
305419896,
0x12345678 );

this.TestCase( SECTION,
"0x80000000",
2147483648,
0x80000000 );

this.TestCase( SECTION,
"0xffffffff",
4294967295,
0xffffffff );

this.TestCase( SECTION,
"0x100000000",
4294967296,
0x100000000 );

this.TestCase( SECTION,
"077777777777777777",
2251799813685247,
077777777777777777 );

this.TestCase( SECTION,
"077777777777777776",
2251799813685246,
077777777777777776 );

this.TestCase( SECTION,
"0x1fffffffffffff",
9007199254740991,
0x1fffffffffffff );

this.TestCase( SECTION,
"0x20000000000000",
9007199254740992,
0x20000000000000 );

this.TestCase( SECTION,
"0x20123456789abc",
9027215253084860,
0x20123456789abc );

this.TestCase( SECTION,
"0x20123456789abd",
9027215253084860,
0x20123456789abd );

this.TestCase( SECTION,
"0x20123456789abe",
9027215253084862,
0x20123456789abe );

this.TestCase( SECTION,
"0x20123456789abf",
9027215253084864,
0x20123456789abf );

this.TestCase( SECTION,
"0x1000000000000080",
1152921504606847000,
0x1000000000000080 );

this.TestCase( SECTION,
"0x1000000000000081",
1152921504606847200,
0x1000000000000081 );

this.TestCase( SECTION,
"0x1000000000000100",
1152921504606847200,
0x1000000000000100 );

this.TestCase( SECTION,
"0x100000000000017f",
1152921504606847200,
0x100000000000017f );

this.TestCase( SECTION,
"0x1000000000000180",
1152921504606847500,
0x1000000000000180 );

this.TestCase( SECTION,
"0x1000000000000181",
1152921504606847500,
0x1000000000000181 );

this.TestCase( SECTION,
"0x10000000000001f0",
1152921504606847500,
0x10000000000001f0 );

this.TestCase( SECTION,
"0x1000000000000200",
1152921504606847500,
0x1000000000000200 );

this.TestCase( SECTION,
"0x100000000000027f",
1152921504606847500,
0x100000000000027f );

this.TestCase( SECTION,
"0x1000000000000280",
1152921504606847500,
0x1000000000000280 );

this.TestCase( SECTION,
"0x1000000000000281",
1152921504606847700,
0x1000000000000281 );

this.TestCase( SECTION,
"0x10000000000002ff",
1152921504606847700,
0x10000000000002ff );

this.TestCase( SECTION,
"0x1000000000000300",
1152921504606847700,
0x1000000000000300 );

this.TestCase( SECTION,
"0x10000000000000000",
18446744073709552000,
0x10000000000000000 );

//test();

},

/**
File Name:          7.7.3-2.js
ECMA Section:       7.7.3 Numeric Literals

Description:

This is a regression test for
http://scopus.mcom.com/bugsplat/show_bug.cgi?id=122884

Waldemar's comments:

A numeric literal that starts with either '08' or '09' is interpreted as a
decimal literal; it should be an error instead.  (Strictly speaking, according
to ECMA v1 such literals should be interpreted as two integers -- a zero
followed by a decimal number whose first digit is 8 or 9, but this is a bug in
ECMA that will be fixed in v2.  In any case, there is no place in the grammar
where two consecutive numbers would be legal.)

Author:             christine@netscape.com
Date:               15 june 1998

*/
test_7_7_3__2: function() {
var SECTION = "7.7.3-2";
var VERSION = "ECMA_1";
var TITLE   = "Numeric Literals";
var BUGNUMBER="122884";

//startTest();

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"9",
9,
9 );

this.TestCase( SECTION,
"09",
9,
09 );

this.TestCase( SECTION,
"099",
99,
099 );


this.TestCase( SECTION,
"077",
63,
077 );

//test();

},

/**
File Name:          7.7.3.js
ECMA Section:       7.7.3 Numeric Literals

Description:        A numeric literal stands for a value of the Number type
This value is determined in two steps:  first a
mathematical value (MV) is derived from the literal;
second, this mathematical value is rounded, ideally
using IEEE 754 round-to-nearest mode, to a reprentable
value of of the number type.

Author:             christine@netscape.com
Date:               16 september 1997
*/
test_7_7_3: function() {
var SECTION = "7.7.3";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Numeric Literals";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION, "0",     0,      0 );
this.TestCase( SECTION, "1",     1,      1 );
this.TestCase( SECTION, "2",     2,      2 );
this.TestCase( SECTION, "3",     3,      3 );
this.TestCase( SECTION, "4",     4,      4 );
this.TestCase( SECTION, "5",     5,      5 );
this.TestCase( SECTION, "6",     6,      6 );
this.TestCase( SECTION, "7",     7,      7 );
this.TestCase( SECTION, "8",     8,      8 );
this.TestCase( SECTION, "9",     9,      9 );

this.TestCase( SECTION, "0.",     0,      0. );
this.TestCase( SECTION, "1.",     1,      1. );
this.TestCase( SECTION, "2.",     2,      2. );
this.TestCase( SECTION, "3.",     3,      3. );
this.TestCase( SECTION, "4.",     4,      4. );

this.TestCase( SECTION, "0.e0",  0,      0.e0 );
this.TestCase( SECTION, "1.e1",  10,     1.e1 );
this.TestCase( SECTION, "2.e2",  200,    2.e2 );
this.TestCase( SECTION, "3.e3",  3000,   3.e3 );
this.TestCase( SECTION, "4.e4",  40000,  4.e4 );

this.TestCase( SECTION, "0.1e0",  .1,    0.1e0 );
this.TestCase( SECTION, "1.1e1",  11,    1.1e1 );
this.TestCase( SECTION, "2.2e2",  220,   2.2e2 );
this.TestCase( SECTION, "3.3e3",  3300,  3.3e3 );
this.TestCase( SECTION, "4.4e4",  44000, 4.4e4 );

this.TestCase( SECTION, ".1e0",  .1,   .1e0 );
this.TestCase( SECTION, ".1e1",  1,    .1e1 );
this.TestCase( SECTION, ".2e2",  20,   .2e2 );
this.TestCase( SECTION, ".3e3",  300,  .3e3 );
this.TestCase( SECTION, ".4e4",  4000, .4e4 );

this.TestCase( SECTION, "0e0",  0,     0e0 );
this.TestCase( SECTION, "1e1",  10,    1e1 );
this.TestCase( SECTION, "2e2",  200,   2e2 );
this.TestCase( SECTION, "3e3",  3000,  3e3 );
this.TestCase( SECTION, "4e4",  40000, 4e4 );

this.TestCase( SECTION, "0e0",  0,     0e0 );
this.TestCase( SECTION, "1e1",  10,    1e1 );
this.TestCase( SECTION, "2e2",  200,   2e2 );
this.TestCase( SECTION, "3e3",  3000,  3e3 );
this.TestCase( SECTION, "4e4",  40000, 4e4 );

this.TestCase( SECTION, "0E0",  0,     0E0 );
this.TestCase( SECTION, "1E1",  10,    1E1 );
this.TestCase( SECTION, "2E2",  200,   2E2 );
this.TestCase( SECTION, "3E3",  3000,  3E3 );
this.TestCase( SECTION, "4E4",  40000, 4E4 );

this.TestCase( SECTION, "1.e-1",  0.1,     1.e-1 );
this.TestCase( SECTION, "2.e-2",  0.02,    2.e-2 );
this.TestCase( SECTION, "3.e-3",  0.003,   3.e-3 );
this.TestCase( SECTION, "4.e-4",  0.0004,  4.e-4 );

this.TestCase( SECTION, "0.1e-0",  .1,     0.1e-0 );
this.TestCase( SECTION, "1.1e-1",  0.11,   1.1e-1 );
this.TestCase( SECTION, "2.2e-2",  .022,   2.2e-2 );
this.TestCase( SECTION, "3.3e-3",  .0033,  3.3e-3 );
this.TestCase( SECTION, "4.4e-4",  .00044, 4.4e-4 );

this.TestCase( SECTION, ".1e-0",  .1,    .1e-0 );
this.TestCase( SECTION, ".1e-1",  .01,    .1e-1 );
this.TestCase( SECTION, ".2e-2",  .002,   .2e-2 );
this.TestCase( SECTION, ".3e-3",  .0003,  .3e-3 );
this.TestCase( SECTION, ".4e-4",  .00004, .4e-4 );

this.TestCase( SECTION, "1.e+1",  10,     1.e+1 );
this.TestCase( SECTION, "2.e+2",  200,    2.e+2 );
this.TestCase( SECTION, "3.e+3",  3000,   3.e+3 );
this.TestCase( SECTION, "4.e+4",  40000,  4.e+4 );

this.TestCase( SECTION, "0.1e+0",  .1,    0.1e+0 );
this.TestCase( SECTION, "1.1e+1",  11,    1.1e+1 );
this.TestCase( SECTION, "2.2e+2",  220,   2.2e+2 );
this.TestCase( SECTION, "3.3e+3",  3300,  3.3e+3 );
this.TestCase( SECTION, "4.4e+4",  44000, 4.4e+4 );

this.TestCase( SECTION, ".1e+0",  .1,   .1e+0 );
this.TestCase( SECTION, ".1e+1",  1,    .1e+1 );
this.TestCase( SECTION, ".2e+2",  20,   .2e+2 );
this.TestCase( SECTION, ".3e+3",  300,  .3e+3 );
this.TestCase( SECTION, ".4e+4",  4000, .4e+4 );

this.TestCase( SECTION, "0x0",  0,   0x0 );
this.TestCase( SECTION, "0x1",  1,   0x1 );
this.TestCase( SECTION, "0x2",  2,   0x2 );
this.TestCase( SECTION, "0x3",  3,   0x3 );
this.TestCase( SECTION, "0x4",  4,   0x4 );
this.TestCase( SECTION, "0x5",  5,   0x5 );
this.TestCase( SECTION, "0x6",  6,   0x6 );
this.TestCase( SECTION, "0x7",  7,   0x7 );
this.TestCase( SECTION, "0x8",  8,   0x8 );
this.TestCase( SECTION, "0x9",  9,   0x9 );
this.TestCase( SECTION, "0xa",  10,  0xa );
this.TestCase( SECTION, "0xb",  11,  0xb );
this.TestCase( SECTION, "0xc",  12,  0xc );
this.TestCase( SECTION, "0xd",  13,  0xd );
this.TestCase( SECTION, "0xe",  14,  0xe );
this.TestCase( SECTION, "0xf",  15,  0xf );

this.TestCase( SECTION, "0X0",  0,   0X0 );
this.TestCase( SECTION, "0X1",  1,   0X1 );
this.TestCase( SECTION, "0X2",  2,   0X2 );
this.TestCase( SECTION, "0X3",  3,   0X3 );
this.TestCase( SECTION, "0X4",  4,   0X4 );
this.TestCase( SECTION, "0X5",  5,   0X5 );
this.TestCase( SECTION, "0X6",  6,   0X6 );
this.TestCase( SECTION, "0X7",  7,   0X7 );
this.TestCase( SECTION, "0X8",  8,   0X8 );
this.TestCase( SECTION, "0X9",  9,   0X9 );
this.TestCase( SECTION, "0Xa",  10,  0Xa );
this.TestCase( SECTION, "0Xb",  11,  0Xb );
this.TestCase( SECTION, "0Xc",  12,  0Xc );
this.TestCase( SECTION, "0Xd",  13,  0Xd );
this.TestCase( SECTION, "0Xe",  14,  0Xe );
this.TestCase( SECTION, "0Xf",  15,  0Xf );

this.TestCase( SECTION, "0x0",  0,   0x0 );
this.TestCase( SECTION, "0x1",  1,   0x1 );
this.TestCase( SECTION, "0x2",  2,   0x2 );
this.TestCase( SECTION, "0x3",  3,   0x3 );
this.TestCase( SECTION, "0x4",  4,   0x4 );
this.TestCase( SECTION, "0x5",  5,   0x5 );
this.TestCase( SECTION, "0x6",  6,   0x6 );
this.TestCase( SECTION, "0x7",  7,   0x7 );
this.TestCase( SECTION, "0x8",  8,   0x8 );
this.TestCase( SECTION, "0x9",  9,   0x9 );
this.TestCase( SECTION, "0xA",  10,  0xA );
this.TestCase( SECTION, "0xB",  11,  0xB );
this.TestCase( SECTION, "0xC",  12,  0xC );
this.TestCase( SECTION, "0xD",  13,  0xD );
this.TestCase( SECTION, "0xE",  14,  0xE );
this.TestCase( SECTION, "0xF",  15,  0xF );

this.TestCase( SECTION, "0X0",  0,   0X0 );
this.TestCase( SECTION, "0X1",  1,   0X1 );
this.TestCase( SECTION, "0X2",  2,   0X2 );
this.TestCase( SECTION, "0X3",  3,   0X3 );
this.TestCase( SECTION, "0X4",  4,   0X4 );
this.TestCase( SECTION, "0X5",  5,   0X5 );
this.TestCase( SECTION, "0X6",  6,   0X6 );
this.TestCase( SECTION, "0X7",  7,   0X7 );
this.TestCase( SECTION, "0X8",  8,   0X8 );
this.TestCase( SECTION, "0X9",  9,   0X9 );
this.TestCase( SECTION, "0XA",  10,  0XA );
this.TestCase( SECTION, "0XB",  11,  0XB );
this.TestCase( SECTION, "0XC",  12,  0XC );
this.TestCase( SECTION, "0XD",  13,  0XD );
this.TestCase( SECTION, "0XE",  14,  0XE );
this.TestCase( SECTION, "0XF",  15,  0XF );


this.TestCase( SECTION, "00",  0,   00 );
this.TestCase( SECTION, "01",  1,   01 );
this.TestCase( SECTION, "02",  2,   02 );
this.TestCase( SECTION, "03",  3,   03 );
this.TestCase( SECTION, "04",  4,   04 );
this.TestCase( SECTION, "05",  5,   05 );
this.TestCase( SECTION, "06",  6,   06 );
this.TestCase( SECTION, "07",  7,   07 );

this.TestCase( SECTION, "000",  0,   000 );
this.TestCase( SECTION, "011",  9,   011 );
this.TestCase( SECTION, "022",  18,  022 );
this.TestCase( SECTION, "033",  27,  033 );
this.TestCase( SECTION, "044",  36,  044 );
this.TestCase( SECTION, "055",  45,  055 );
this.TestCase( SECTION, "066",  54,  066 );
this.TestCase( SECTION, "077",  63,   077 );

this.TestCase( SECTION, "0.00000000001",  0.00000000001,  0.00000000001 );
this.TestCase( SECTION, "0.00000000001e-2",  0.0000000000001,  0.00000000001e-2 );


this.TestCase( SECTION,
"123456789012345671.9999",
"123456789012345660",
123456789012345671.9999 +"");
this.TestCase( SECTION,
"123456789012345672",
"123456789012345660",
123456789012345672 +"");

this.TestCase(   SECTION,
"123456789012345672.000000000000000000000000000",
"123456789012345660",
123456789012345672.000000000000000000000000000 +"");

this.TestCase( SECTION,
"123456789012345672.01",
"123456789012345680",
123456789012345672.01 +"");

this.TestCase( SECTION,
"123456789012345672.000000000000000000000000001+'' == 123456789012345680 || 123456789012345660",
true,
( 123456789012345672.00000000000000000000000000 +""  == 1234567890 * 100000000 + 12345680 )
||
( 123456789012345672.00000000000000000000000000 +""  == 1234567890 * 100000000 + 12345660) );

this.TestCase( SECTION,
"123456789012345673",
"123456789012345680",
123456789012345673 +"" );

this.TestCase( SECTION,
"-123456789012345671.9999",
"-123456789012345660",
-123456789012345671.9999 +"" );

this.TestCase( SECTION,
"-123456789012345672",
"-123456789012345660",
-123456789012345672+"");

this.TestCase( SECTION,
"-123456789012345672.000000000000000000000000000",
"-123456789012345660",
-123456789012345672.000000000000000000000000000 +"");

this.TestCase( SECTION,
"-123456789012345672.01",
"-123456789012345680",
-123456789012345672.01 +"" );

this.TestCase( SECTION,
"-123456789012345672.000000000000000000000000001 == -123456789012345680 or -123456789012345660",
true,
(-123456789012345672.000000000000000000000000001 +"" == -1234567890 * 100000000 -12345680)
||
(-123456789012345672.000000000000000000000000001 +"" == -1234567890 * 100000000 -12345660));

this.TestCase( SECTION,
-123456789012345673,
"-123456789012345680",
-123456789012345673 +"");

this.TestCase( SECTION,
"12345678901234567890",
"12345678901234567000",
12345678901234567890 +"" );


/*
new TestCase( SECTION, "12345678901234567",         "12345678901234567",        12345678901234567+"" );
new TestCase( SECTION, "123456789012345678",        "123456789012345678",       123456789012345678+"" );
new TestCase( SECTION, "1234567890123456789",       "1234567890123456789",      1234567890123456789+"" );
new TestCase( SECTION, "12345678901234567890",      "12345678901234567890",     12345678901234567890+"" );
new TestCase( SECTION, "123456789012345678900",     "123456789012345678900",    123456789012345678900+"" );
new TestCase( SECTION, "1234567890123456789000",    "1234567890123456789000",   1234567890123456789000+"" );
*/
this.TestCase( SECTION, "0x1",          1,          0x1 );
this.TestCase( SECTION, "0x10",         16,         0x10 );
this.TestCase( SECTION, "0x100",        256,        0x100 );
this.TestCase( SECTION, "0x1000",       4096,       0x1000 );
this.TestCase( SECTION, "0x10000",      65536,      0x10000 );
this.TestCase( SECTION, "0x100000",     1048576,    0x100000 );
this.TestCase( SECTION, "0x1000000",    16777216,   0x1000000 );
this.TestCase( SECTION, "0x10000000",   268435456,  0x10000000 );
/*
new TestCase( SECTION, "0x100000000",          4294967296,      0x100000000 );
new TestCase( SECTION, "0x1000000000",         68719476736,     0x1000000000 );
new TestCase( SECTION, "0x10000000000",        1099511627776,     0x10000000000 );
*/

//test();

},

/**
File Name:          7.7.4.js
ECMA Section:       7.7.4 String Literals

Description:        A string literal is zero or more characters enclosed in
single or double quotes.  Each character may be
represented by an escape sequence.


Author:             christine@netscape.com
Date:               16 september 1997
*/
test_7_7_4: function() {
var SECTION = "7.7.4";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "String Literals";

//writeHeaderToLog( SECTION + " "+ TITLE);

// StringLiteral:: "" and ''

this.TestCase( SECTION, "\"\"",     "",     "" );
this.TestCase( SECTION, "\'\'",     "",      '' );

// DoubleStringCharacters:: DoubleStringCharacter :: EscapeSequence :: CharacterEscapeSequence
this.TestCase( SECTION, "\\\"",        String.fromCharCode(0x0022),     "\"" );
this.TestCase( SECTION, "\\\'",        String.fromCharCode(0x0027),     "\'" );
this.TestCase( SECTION, "\\",         String.fromCharCode(0x005C),     "\\" );
this.TestCase( SECTION, "\\b",        String.fromCharCode(0x0008),     "\b" );
this.TestCase( SECTION, "\\f",        String.fromCharCode(0x000C),     "\f" );
this.TestCase( SECTION, "\\n",        String.fromCharCode(0x000A),     "\n" );
this.TestCase( SECTION, "\\r",        String.fromCharCode(0x000D),     "\r" );
this.TestCase( SECTION, "\\t",        String.fromCharCode(0x0009),     "\t" );
this.TestCase( SECTION, "\\v",        String.fromCharCode(0x000B),        "\v" );

// DoubleStringCharacters:DoubleStringCharacter::EscapeSequence::OctalEscapeSequence

this.TestCase( SECTION, "\\00",      String.fromCharCode(0x0000),    "\00" );
this.TestCase( SECTION, "\\01",      String.fromCharCode(0x0001),    "\01" );
this.TestCase( SECTION, "\\02",      String.fromCharCode(0x0002),    "\02" );
this.TestCase( SECTION, "\\03",      String.fromCharCode(0x0003),    "\03" );
this.TestCase( SECTION, "\\04",      String.fromCharCode(0x0004),    "\04" );
this.TestCase( SECTION, "\\05",      String.fromCharCode(0x0005),    "\05" );
this.TestCase( SECTION, "\\06",      String.fromCharCode(0x0006),    "\06" );
this.TestCase( SECTION, "\\07",      String.fromCharCode(0x0007),    "\07" );

this.TestCase( SECTION, "\\010",      String.fromCharCode(0x0008),    "\010" );
this.TestCase( SECTION, "\\011",      String.fromCharCode(0x0009),    "\011" );
this.TestCase( SECTION, "\\012",      String.fromCharCode(0x000A),    "\012" );
this.TestCase( SECTION, "\\013",      String.fromCharCode(0x000B),    "\013" );
this.TestCase( SECTION, "\\014",      String.fromCharCode(0x000C),    "\014" );
this.TestCase( SECTION, "\\015",      String.fromCharCode(0x000D),    "\015" );
this.TestCase( SECTION, "\\016",      String.fromCharCode(0x000E),    "\016" );
this.TestCase( SECTION, "\\017",      String.fromCharCode(0x000F),    "\017" );
this.TestCase( SECTION, "\\020",      String.fromCharCode(0x0010),    "\020" );
this.TestCase( SECTION, "\\042",      String.fromCharCode(0x0022),    "\042" );

this.TestCase( SECTION, "\\0",      String.fromCharCode(0x0000),    "\0" );
this.TestCase( SECTION, "\\1",      String.fromCharCode(0x0001),    "\1" );
this.TestCase( SECTION, "\\2",      String.fromCharCode(0x0002),    "\2" );
this.TestCase( SECTION, "\\3",      String.fromCharCode(0x0003),    "\3" );
this.TestCase( SECTION, "\\4",      String.fromCharCode(0x0004),    "\4" );
this.TestCase( SECTION, "\\5",      String.fromCharCode(0x0005),    "\5" );
this.TestCase( SECTION, "\\6",      String.fromCharCode(0x0006),    "\6" );
this.TestCase( SECTION, "\\7",      String.fromCharCode(0x0007),    "\7" );

this.TestCase( SECTION, "\\10",      String.fromCharCode(0x0008),    "\10" );
this.TestCase( SECTION, "\\11",      String.fromCharCode(0x0009),    "\11" );
this.TestCase( SECTION, "\\12",      String.fromCharCode(0x000A),    "\12" );
this.TestCase( SECTION, "\\13",      String.fromCharCode(0x000B),    "\13" );
this.TestCase( SECTION, "\\14",      String.fromCharCode(0x000C),    "\14" );
this.TestCase( SECTION, "\\15",      String.fromCharCode(0x000D),    "\15" );
this.TestCase( SECTION, "\\16",      String.fromCharCode(0x000E),    "\16" );
this.TestCase( SECTION, "\\17",      String.fromCharCode(0x000F),    "\17" );
this.TestCase( SECTION, "\\20",      String.fromCharCode(0x0010),    "\20" );
this.TestCase( SECTION, "\\42",      String.fromCharCode(0x0022),    "\42" );

this.TestCase( SECTION, "\\000",      String.fromCharCode(0),        "\000" );
this.TestCase( SECTION, "\\111",      String.fromCharCode(73),       "\111" );
this.TestCase( SECTION, "\\222",      String.fromCharCode(146),      "\222" );
this.TestCase( SECTION, "\\333",      String.fromCharCode(219),      "\333" );

//  following line commented out as it causes a compile time error
//    this.TestCase( SECTION, "\\444",      "444",                         "\444" );

// DoubleStringCharacters:DoubleStringCharacter::EscapeSequence::HexEscapeSequence
/*
new TestCase( SECTION, "\\x0",      String.fromCharCode(0),         "\x0" );
new TestCase( SECTION, "\\x1",      String.fromCharCode(1),         "\x1" );
new TestCase( SECTION, "\\x2",      String.fromCharCode(2),         "\x2" );
new TestCase( SECTION, "\\x3",      String.fromCharCode(3),         "\x3" );
new TestCase( SECTION, "\\x4",      String.fromCharCode(4),         "\x4" );
new TestCase( SECTION, "\\x5",      String.fromCharCode(5),         "\x5" );
new TestCase( SECTION, "\\x6",      String.fromCharCode(6),         "\x6" );
new TestCase( SECTION, "\\x7",      String.fromCharCode(7),         "\x7" );
new TestCase( SECTION, "\\x8",      String.fromCharCode(8),         "\x8" );
new TestCase( SECTION, "\\x9",      String.fromCharCode(9),         "\x9" );
new TestCase( SECTION, "\\xA",      String.fromCharCode(10),         "\xA" );
new TestCase( SECTION, "\\xB",      String.fromCharCode(11),         "\xB" );
new TestCase( SECTION, "\\xC",      String.fromCharCode(12),         "\xC" );
new TestCase( SECTION, "\\xD",      String.fromCharCode(13),         "\xD" );
new TestCase( SECTION, "\\xE",      String.fromCharCode(14),         "\xE" );
new TestCase( SECTION, "\\xF",      String.fromCharCode(15),         "\xF" );

*/
this.TestCase( SECTION, "\\xF0",      String.fromCharCode(240),         "\xF0" );
this.TestCase( SECTION, "\\xE1",      String.fromCharCode(225),         "\xE1" );
this.TestCase( SECTION, "\\xD2",      String.fromCharCode(210),         "\xD2" );
this.TestCase( SECTION, "\\xC3",      String.fromCharCode(195),         "\xC3" );
this.TestCase( SECTION, "\\xB4",      String.fromCharCode(180),         "\xB4" );
this.TestCase( SECTION, "\\xA5",      String.fromCharCode(165),         "\xA5" );
this.TestCase( SECTION, "\\x96",      String.fromCharCode(150),         "\x96" );
this.TestCase( SECTION, "\\x87",      String.fromCharCode(135),         "\x87" );
this.TestCase( SECTION, "\\x78",      String.fromCharCode(120),         "\x78" );
this.TestCase( SECTION, "\\x69",      String.fromCharCode(105),         "\x69" );
this.TestCase( SECTION, "\\x5A",      String.fromCharCode(90),         "\x5A" );
this.TestCase( SECTION, "\\x4B",      String.fromCharCode(75),         "\x4B" );
this.TestCase( SECTION, "\\x3C",      String.fromCharCode(60),         "\x3C" );
this.TestCase( SECTION, "\\x2D",      String.fromCharCode(45),         "\x2D" );
this.TestCase( SECTION, "\\x1E",      String.fromCharCode(30),         "\x1E" );
this.TestCase( SECTION, "\\x0F",      String.fromCharCode(15),         "\x0F" );

// string literals only take up to two hext digits.  therefore, the third character in this string
// should be interpreted as a StringCharacter and not part of the HextEscapeSequence

this.TestCase( SECTION, "\\xF0F",      String.fromCharCode(240)+"F",         "\xF0F" );
this.TestCase( SECTION, "\\xE1E",      String.fromCharCode(225)+"E",         "\xE1E" );
this.TestCase( SECTION, "\\xD2D",      String.fromCharCode(210)+"D",         "\xD2D" );
this.TestCase( SECTION, "\\xC3C",      String.fromCharCode(195)+"C",         "\xC3C" );
this.TestCase( SECTION, "\\xB4B",      String.fromCharCode(180)+"B",         "\xB4B" );
this.TestCase( SECTION, "\\xA5A",      String.fromCharCode(165)+"A",         "\xA5A" );
this.TestCase( SECTION, "\\x969",      String.fromCharCode(150)+"9",         "\x969" );
this.TestCase( SECTION, "\\x878",      String.fromCharCode(135)+"8",         "\x878" );
this.TestCase( SECTION, "\\x787",      String.fromCharCode(120)+"7",         "\x787" );
this.TestCase( SECTION, "\\x696",      String.fromCharCode(105)+"6",         "\x696" );
this.TestCase( SECTION, "\\x5A5",      String.fromCharCode(90)+"5",         "\x5A5" );
this.TestCase( SECTION, "\\x4B4",      String.fromCharCode(75)+"4",         "\x4B4" );
this.TestCase( SECTION, "\\x3C3",      String.fromCharCode(60)+"3",         "\x3C3" );
this.TestCase( SECTION, "\\x2D2",      String.fromCharCode(45)+"2",         "\x2D2" );
this.TestCase( SECTION, "\\x1E1",      String.fromCharCode(30)+"1",         "\x1E1" );
this.TestCase( SECTION, "\\x0F0",      String.fromCharCode(15)+"0",         "\x0F0" );

// G is out of hex range

this.TestCase( SECTION, "\\xG",        "xG",                                 "\xG" );
this.TestCase( SECTION, "\\xCG",       "xCG",      				"\xCG" );

// DoubleStringCharacter::EscapeSequence::CharacterEscapeSequence::\ NonEscapeCharacter
this.TestCase( SECTION, "\\a",    "a",        "\a" );
this.TestCase( SECTION, "\\c",    "c",        "\c" );
this.TestCase( SECTION, "\\d",    "d",        "\d" );
this.TestCase( SECTION, "\\e",    "e",        "\e" );
this.TestCase( SECTION, "\\g",    "g",        "\g" );
this.TestCase( SECTION, "\\h",    "h",        "\h" );
this.TestCase( SECTION, "\\i",    "i",        "\i" );
this.TestCase( SECTION, "\\j",    "j",        "\j" );
this.TestCase( SECTION, "\\k",    "k",        "\k" );
this.TestCase( SECTION, "\\l",    "l",        "\l" );
this.TestCase( SECTION, "\\m",    "m",        "\m" );
this.TestCase( SECTION, "\\o",    "o",        "\o" );
this.TestCase( SECTION, "\\p",    "p",        "\p" );
this.TestCase( SECTION, "\\q",    "q",        "\q" );
this.TestCase( SECTION, "\\s",    "s",        "\s" );
this.TestCase( SECTION, "\\u",    "u",        "\u" );

this.TestCase( SECTION, "\\w",    "w",        "\w" );
this.TestCase( SECTION, "\\x",    "x",        "\x" );
this.TestCase( SECTION, "\\y",    "y",        "\y" );
this.TestCase( SECTION, "\\z",    "z",        "\z" );
this.TestCase( SECTION, "\\9",    "9",        "\9" );

this.TestCase( SECTION, "\\A",    "A",        "\A" );
this.TestCase( SECTION, "\\B",    "B",        "\B" );
this.TestCase( SECTION, "\\C",    "C",        "\C" );
this.TestCase( SECTION, "\\D",    "D",        "\D" );
this.TestCase( SECTION, "\\E",    "E",        "\E" );
this.TestCase( SECTION, "\\F",    "F",        "\F" );
this.TestCase( SECTION, "\\G",    "G",        "\G" );
this.TestCase( SECTION, "\\H",    "H",        "\H" );
this.TestCase( SECTION, "\\I",    "I",        "\I" );
this.TestCase( SECTION, "\\J",    "J",        "\J" );
this.TestCase( SECTION, "\\K",    "K",        "\K" );
this.TestCase( SECTION, "\\L",    "L",        "\L" );
this.TestCase( SECTION, "\\M",    "M",        "\M" );
this.TestCase( SECTION, "\\N",    "N",        "\N" );
this.TestCase( SECTION, "\\O",    "O",        "\O" );
this.TestCase( SECTION, "\\P",    "P",        "\P" );
this.TestCase( SECTION, "\\Q",    "Q",        "\Q" );
this.TestCase( SECTION, "\\R",    "R",        "\R" );
this.TestCase( SECTION, "\\S",    "S",        "\S" );
this.TestCase( SECTION, "\\T",    "T",        "\T" );
this.TestCase( SECTION, "\\U",    "U",        "\U" );
this.TestCase( SECTION, "\\V",    "V",        "\V" );
this.TestCase( SECTION, "\\W",    "W",        "\W" );
this.TestCase( SECTION, "\\X",    "X",        "\X" );
this.TestCase( SECTION, "\\Y",    "Y",        "\Y" );
this.TestCase( SECTION, "\\Z",    "Z",        "\Z" );

// DoubleStringCharacter::EscapeSequence::UnicodeEscapeSequence

this.TestCase( SECTION,  "\\u0020",  " ",        "\u0020" );
this.TestCase( SECTION,  "\\u0021",  "!",        "\u0021" );
this.TestCase( SECTION,  "\\u0022",  "\"",       "\u0022" );
this.TestCase( SECTION,  "\\u0023",  "#",        "\u0023" );
this.TestCase( SECTION,  "\\u0024",  "$",        "\u0024" );
this.TestCase( SECTION,  "\\u0025",  "%",        "\u0025" );
this.TestCase( SECTION,  "\\u0026",  "&",        "\u0026" );
this.TestCase( SECTION,  "\\u0027",  "'",        "\u0027" );
this.TestCase( SECTION,  "\\u0028",  "(",        "\u0028" );
this.TestCase( SECTION,  "\\u0029",  ")",        "\u0029" );
this.TestCase( SECTION,  "\\u002A",  "*",        "\u002A" );
this.TestCase( SECTION,  "\\u002B",  "+",        "\u002B" );
this.TestCase( SECTION,  "\\u002C",  ",",        "\u002C" );
this.TestCase( SECTION,  "\\u002D",  "-",        "\u002D" );
this.TestCase( SECTION,  "\\u002E",  ".",        "\u002E" );
this.TestCase( SECTION,  "\\u002F",  "/",        "\u002F" );
this.TestCase( SECTION,  "\\u0030",  "0",        "\u0030" );
this.TestCase( SECTION,  "\\u0031",  "1",        "\u0031" );
this.TestCase( SECTION,  "\\u0032",  "2",        "\u0032" );
this.TestCase( SECTION,  "\\u0033",  "3",        "\u0033" );
this.TestCase( SECTION,  "\\u0034",  "4",        "\u0034" );
this.TestCase( SECTION,  "\\u0035",  "5",        "\u0035" );
this.TestCase( SECTION,  "\\u0036",  "6",        "\u0036" );
this.TestCase( SECTION,  "\\u0037",  "7",        "\u0037" );
this.TestCase( SECTION,  "\\u0038",  "8",        "\u0038" );
this.TestCase( SECTION,  "\\u0039",  "9",        "\u0039" );

//test();

},

/**
File Name:          7.8.2.js
ECMA Section:       7.8.2 Examples of Automatic Semicolon Insertion
Description:        compare some specific examples of the automatic
insertion rules in the EMCA specification.
Author:             christine@netscape.com
Date:               15 september 1997
*/
test_7_8_2__n: function() {
var SECTION="7.8.2";
var VERSION="ECMA_1";
//  startTest();

//writeHeaderToLog(SECTION+" "+"Examples of Semicolon Insertion");


//    new TestCase( "7.8.2",  "{ 1 \n 2 } 3",      3,         eval("{ 1 \n 2 } 3") );

DESCRIPTION = "{ 1 2 } 3";
EXPECTED = "error";

this.TestCase( "7.8.2",  "{ 1 2 } 3",         "error",   eval("{1 2 } 3")     );

//test();

},

/**
File Name:          7.8.2.js
ECMA Section:       7.8.2 Examples of Automatic Semicolon Insertion
Description:        compare some specific examples of the automatic
insertion rules in the EMCA specification.
Author:             christine@netscape.com
Date:               15 september 1997
*/
test_7_8_2__n_WORKS: function() {
var SECTION="7.8.2";
var VERSION="ECMA_1";
//  startTest();

//writeHeaderToLog(SECTION+" "+"Examples of Semicolon Insertion");


//    new TestCase( "7.8.2",  "{ 1 \n 2 } 3",      3,         eval("{ 1 \n 2 } 3") );

DESCRIPTION = "{ 1 2 } 3";
EXPECTED = "error";

try {
this.TestCase( "7.8.2",  "{ 1 2 } 3",         "error",   eval("{1 2 } 3")     );
fail();
} catch (e) {
assertTrue(e.name == 'SyntaxError');
assertTrue(e.message == 'missing ; before statement');
}

//test();

}

})
.endType();


