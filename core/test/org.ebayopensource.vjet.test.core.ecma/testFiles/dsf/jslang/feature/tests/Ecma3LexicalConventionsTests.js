vjo.ctype("dsf.jslang.feature.tests.Ecma3LexicalConventionsTests")
.inherits("dsf.jslang.feature.tests.BaseTestEcma3")
.protos({

constructs: function() {
this.base();
},

//-----------------------------------------------------------------------------
test_7_9_1: function() {
//var BUGNUMBER = 402386;
var summary = 'Automatic Semicolon insertion in postfix expressions';
var actual = '';
var expect = '';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

var expr;
var code;

// LeftHandSideExpression [no LineTerminator here] ++

code   = 'expr ++';
expr   = 0;
expect = 1;

try
{
eval(code);
actual = expr;
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': ' + code);

code   = 'expr\n++';
expr   = 0;
expect = 'SyntaxError: syntax error';

try
{
eval(code);
actual = expr;
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': ' + code);

// LeftHandSideExpression [no LineTerminator here] --

code   = 'expr --';
expr   = 0;
expect = -1;

try
{
eval(code);
actual = expr;
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': ' + code);

code   = 'expr\n--';
expr   = 0;
expect = 'SyntaxError: syntax error';

try
{
eval(code);
actual = expr;
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': ' + code);

//

var x = 1;
var y = 1;
code   = '(x\n)-- y';
expect = 'SyntaxError: missing ; before statement';

try
{
eval(code);
actual = expr;
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': ' + code);

code   = '(x)-- y';
expect = 'SyntaxError: missing ; before statement';

try
{
eval(code);
actual = expr;
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': ' + code);

//exitFunc ('test');
//}

},

//-----------------------------------------------------------------------------
test_7_9_1_WORKS: function() {
//var BUGNUMBER = 402386;
var summary = 'Automatic Semicolon insertion in postfix expressions';
var actual = '';
var expect = '';


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

var expr;
var code;

// LeftHandSideExpression [no LineTerminator here] ++

code   = 'expr ++';
expr   = 0;
expect = 1;

try
{
eval(code);
actual = expr;
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': ' + code);

code   = 'expr\n++';
expr   = 0;
//expect = 'SyntaxError: syntax error';
expect = 'SyntaxError: Unexpected end of file';

try
{
eval(code);
actual = expr;
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': ' + code);

// LeftHandSideExpression [no LineTerminator here] --

code   = 'expr --';
expr   = 0;
expect = -1;

try
{
eval(code);
actual = expr;
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': ' + code);

code   = 'expr\n--';
expr   = 0;
//expect = 'SyntaxError: syntax error';
expect = 'SyntaxError: Unexpected end of file';

try
{
eval(code);
actual = expr;
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': ' + code);

//

var x = 1;
var y = 1;
code   = '(x\n)-- y';
expect = 'SyntaxError: missing ; before statement';

try
{
eval(code);
actual = expr;
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': ' + code);

code   = '(x)-- y';
expect = 'SyntaxError: missing ; before statement';

try
{
eval(code);
actual = expr;
}
catch(ex)
{
actual = ex + '';
}
this.reportCompare(expect, actual, summary + ': ' + code);

//exitFunc ('test');
//}

}

})
.endType();


