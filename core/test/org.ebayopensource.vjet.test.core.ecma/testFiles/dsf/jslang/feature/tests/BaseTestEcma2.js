vjo.ctype("dsf.jslang.feature.tests.BaseTestEcma2")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({
//>public constructs()
constructs:function(){
vjo.sysout.println("base constructor called");

},

//Ecma2ExpressionsTests.js
StrictEquality: function( x, y, expect ) {
result = ( x === y );

this.TestCase(
"", //SECTION
x +" === " + y,
expect,
result );
},

//Ecma2StatementsTests.js
LabeledContinue1: function( limit, expect ) {
i = 0;
woohoo:
do {
i++;
continue woohoo;
} while ( i < limit );

this.TestCase(
"", //SECTION
"do while ( " + i +" < " + limit +" )",
expect,
i );
},

//Ecma2StatementsTests.js
// The labeled statement contains statements after the labeled break.
// Verify that the statements after the break are not executed.

LabeledContinue2: function( limit, expect ) {
i = 0;
result1 = "pass";
result2 = "pass";

woohoo: {
do {
i++;
if ( ! (i < limit) ) {
break woohoo;
result1 = "fail: evaluated statement after a labeled break";
}
} while ( true );

result2 = "failed:  broke out of loop, but not out of labeled block";
}

this.TestCase(
"", //SECTION
"do while ( " + i +" < " + limit +" )",
expect,
i );

this.TestCase(
"", //SECTION
"breaking out of a do... while loop",
"pass",
result1 );


this.TestCase(
"", // SECTION
"breaking out of a labeled do...while loop",
"pass",
result2 );
},

//Ecma2StatementsTests.js
DoWhile3: function( object ) {
var i = 0;

do {
object.value =  --object.value;
i++;
if ( i > 1000 )
break;
} while( object.value );

this.TestCase(
"", //SECTION
"loop iterations",
object.iterations,
i
);

this.TestCase(
"", //SECTION
"object.value",
object.endvalue,
Number( object.value )
);

},

//Ecma2StatementsTests.js
DoWhile4: function( limit, expect ) {
i = 0;
result1 = "pass";
result2 = "failed: broke out of labeled statement unexpectedly";

foo: {
do {
i++;
if ( ! (i < limit) ) {
break;
result1 = "fail: evaluated statement after a labeled break";
}
} while ( true );

result2 = "pass";
}

this.TestCase(
"", //SECTION
"do while ( " + i +" < " + limit +" )",
expect,
i );

this.TestCase(
"", //SECTION
"breaking out of a do... while loop",
"pass",
result1 );


this.TestCase(
"", //SECTION
"breaking out of a labeled do...while loop",
"pass",
result2 );
},

//Ecma2StatementsTests.js
NestedLabel: function() {
i = 0;
result1 = "pass";
result2 = "fail: did not hit code after inner loop";
result3 = "pass";

outer: {
do {
inner: {
//                    print( i );
break inner;
result1 = "fail: did break out of inner label";
}
result2 = "pass";
break outer;
print(i);
} while ( i++ < 100 );

}

result3 = "fail: did not break out of outer label";

this.TestCase(
"", //SECTION
"number of loop iterations",
0,
i );

this.TestCase(
"", //SECTION
"break out of inner loop",
"pass",
result1 );

this.TestCase(
"", //SECTION
"break out of outer loop",
"pass",
result2 );
},

//Ecma2StatementsTests.js
looping: function( object ) {
object.iterations--;

if ( object.iterations <= 0 ) {
return false;
} else {
return true;
}
},

DoWhile6: function( object ) {
var result1 = false;
var result2 = false;

outie: {
innie: {
do {
if ( object.breakOut )
break outie;

if ( object.breakIn )
break innie;

} while ( this.looping(object) );

//  statements should be executed if:
//  do...while exits normally
//  do...while exits abruptly with no label

result1 = true;

}

//  statements should be executed if:
//  do...while breaks out with label "innie"
//  do...while exits normally
//  do...while does not break out with "outie"

result2 = true;
}

this.TestCase(
"", //SECTION
"hit code after loop in inner loop",
( object.breakIn || object.breakOut ) ? false : true ,
result1 );

this.TestCase(
"", //SECTION
"hit code after loop in outer loop",
( object.breakOut ) ? false : true,
result2 );


},

//Ecma2StatementsTests.js
DoWhile7: function( object ) {
result1 = false;
result2 = false;
result3 = false;
result4 = false;

outie:
do {
if ( object.breakOutOne ) {
break outie;
}
result1 = true;

innie:
do {
if ( object.breakOutTwo ) {
break outie;
}
result2 = true;

if ( object.breakIn ) {
break innie;
}
result3 = true;

} while ( false );
if ( object.breakOutThree ) {
break outie;
}
result4 = true;
} while ( false );

this.TestCase(
"", //SECTION,
"break one: ",
(object.breakOutOne) ? false : true,
result1 );

this.TestCase(
"", //SECTION
"break two: ",
(object.breakOutOne||object.breakOutTwo) ? false : true,
result2 );

this.TestCase(
"", //SECTION
"break three: ",
(object.breakOutOne||object.breakOutTwo||object.breakIn) ? false : true,
result3 );

this.TestCase(
"", //SECTION
"break four: ",
(object.breakOutOne||object.breakOutTwo||object.breakOutThree) ? false: true,
result4 );
},

/**
*  Verify that the left side argument is evaluated with every iteration.
*  Verify that the name of each property of the object is assigned to a
*  a property.
*
*/
ForIn_1: function( object ) {
PropertyArray = new Array();
ValueArray = new Array();

for ( PropertyArray[PropertyArray.length] in object ) {
ValueArray[ValueArray.length] =
object[PropertyArray[PropertyArray.length-1]];
}

for ( var i = 0; i < PropertyArray.length; i++ ) {
this.TestCase(
"", //SECTION
"object[" + PropertyArray[i] +"]",
object[PropertyArray[i]],
ValueArray[i]
);
}

this.TestCase(
"", //SECTION
"object.length",
PropertyArray.length,
object.length );
},

/**
*  Similar to ForIn_1, except it should increment the counter variable
*  every time the left hand expression is evaluated.
*/
ForIn_2: function( object ) {
PropertyArray = new Array();
ValueArray = new Array();
var i = 0;

for ( PropertyArray[i++] in object ) {
ValueArray[ValueArray.length] =
object[PropertyArray[PropertyArray.length-1]];
}

for ( i = 0; i < PropertyArray.length; i++ ) {
this.TestCase(
"", //SECTION
"object[" + PropertyArray[i] +"]",
object[PropertyArray[i]],
ValueArray[i]
);
}

this.TestCase(
"", //SECTION
"object.length",
PropertyArray.length,
object.length );
},

/**
*  Break out of a for...in loop
*
*
*/
ForIn_3: function( object ) {
var checkBreak = "pass";
var properties = new Array();
var values = new Array();

for ( properties[properties.length] in object ) {
values[values.length] = object[properties[properties.length-1]];
break;
checkBreak = "fail";
}

this.TestCase(
"", //SECTION
"check break out of for...in",
"pass",
checkBreak );

this.TestCase(
"", //SECTION
"properties.length",
1,
properties.length );

this.TestCase(
"", //SECTION
"object["+properties[0]+"]",
values[0],
object[properties[0]] );
},

/**
*  Break out of a labeled for...in loop.
*/
ForIn_4: function( object ) {
var result1 = 0;
var result2 = 0;
var result3 = 0;
var result4 = 0;
var i = 0;
var property = new Array();

butterbean: {
result1++;

for ( property[i++] in object ) {
result2++;
break;
result4++;
}
result3++;
}

this.TestCase(
"", //SECTION
"verify labeled statement is only executed once",
true,
result1 == 1 );

this.TestCase(
"", //SECTION
"verify statements in for loop are evaluated",
true,
result2 == i );

this.TestCase(
"", //SECTION
"verify break out of labeled for...in loop",
true,
result4 == 0 );

this.TestCase(
"", //SECTION
"verify break out of labeled block",
true,
result3 == 0 );
},

/**
*  Labeled break out of a labeled for...in loop.
*/
ForIn_5: function(object) {
var result1 = 0;
var result2 = 0;
var result3 = 0;
var result4 = 0;
var i = 0;
var property = new Array();

bigredbird: {
result1++;
for ( property[i++] in object ) {
result2++;
break bigredbird;
result4++;
}
result3++;
}

this.TestCase(
"", //SECTION
"verify labeled statement is only executed once",
true,
result1 == 1 );

this.TestCase(
"", //SECTION
"verify statements in for loop are evaluated",
true,
result2 == i );

this.TestCase(
"", //SECTION
"verify break out of labeled for...in loop",
true,
result4 == 0 );

this.TestCase(
"", //SECTION
"verify break out of labeled block",
true,
result3 == 0 );
},

/**
*  Labeled continue from a labeled for...in loop
*/
ForIn_7: function( object ) {
var result1 = 0;
var result2 = 0;
var result3 = 0;
var result4 = 0;
var i = 0;
var property = new Array();

bigredbird:
for ( property[i++] in object ) {
result2++;
continue bigredbird;
result4++;
}

this.TestCase(
"", //SECTION
"verify statements in for loop are evaluated",
true,
result2 == i );

this.TestCase(
"", //SECTION
"verify break out of labeled for...in loop",
true,
result4 == 0 );

this.TestCase(
"", //SECTION
"verify break out of labeled block",
true,
result3 == 1 );
},


/**
*  continue in a for...in loop
*
*/
ForIn_8: function( object ) {
var checkBreak = "pass";
var properties = new Array();
var values = new Array();

for ( properties[properties.length] in object ) {
values[values.length] = object[properties[properties.length-1]];
break;
checkBreak = "fail";
}

this.TestCase(
"", //SECTION
"check break out of for...in",
"pass",
checkBreak );

this.TestCase(
"", //SECTION
"properties.length",
1,
properties.length );

this.TestCase(
"", //SECTION
"object["+properties[0]+"]",
values[0],
object[properties[0]] );
},

/**
*  For ... In in a With Block
*
*/
ForIn_12: function( object) {
with ( object ) {
for ( property in object ) {
this.TestCase(
"", //,SECTION
"with loop in a for...in loop.  ("+object+")["+property +"] == "+
"eval ( " + property +" )",
true,
object[property] == eval(property) );
}
}
},

/**
*  With block in a For...In loop
*
*/
ForIn_22: function(object) {
for ( property in object ) {
with ( object ) {
this.TestCase(
"", //SECTION
"with loop in a for...in loop.  ("+object+")["+property +"] == "+
"eval ( " + property +" )",
true,
object[property] == eval(property) );
}
}
},

LabelTest: function( limit, expect) {
woo: for ( var result = 0; result < 1000; result++ ) { if (result == limit) { break woo; } else { continue woo; } };

this.TestCase(
"", //SECTION
"break out of a labeled for loop: "+ limit,
expect,
result );
},

LabelTest2: function( object, expect1, expect2 ) {
result = "";

yoohoo:  { for ( property in object ) { result += object[property]; }; break yoohoo };

this.TestCase(
"", //SECTION
"yoohoo: for ( property in object ) { result += object[property]; } break yoohoo }",
true,
result == expect1 || result == expect2 );
},

LabelTest22: function( object, expect1, expect2 ) {
result = "";

yoohoo:  { for ( property in object ) { result += object[property]; break yoohoo } }; ;

this.TestCase(
"", //SECTION
"yoohoo: for ( property in object ) { result += object[property]; break yoohoo }}",
true,
result == expect1 || result == expect2 );
},

SwitchTest1: function( input, expect ) {
var result = 0;

switch ( input ) {
case 0:
result += 2;
case 1:
result += 4;
case 2:
result += 8;
case 3:
result += 16;
default:
result += 32;
case 4:
result +=64;
}

this.TestCase(
"", //SECTION
"switch with no breaks, case expressions are numbers.  input is "+
input,
expect,
result );
},

SwitchTest2: function( input, expect ) {
var result = 0;

switch ( input ) {
case 0:
result += 2;
case 1:
result += 4;
break;
case 2:
result += 8;
case 3:
result += 16;
default:
result += 32;
break;
case 4:
result += 64;
}

this.TestCase(
"", //SECTION
"switch with no breaks:  input is " + input,
expect,
result );
},

SwitchTest3: function( input, expect ) {
var result = "";

switch ( input ) {
case "z": result += "z";
case "y": result += "y";
case "x": result += "x";
case "w": result += "w";
default: result += "*";
case "a": result += "a";
case "b": result += "b";
case "c": result += "c";
}

this.TestCase(
"", //SECTION
"switch with no breaks:  input is " + input,
expect,
result );
},

SwitchTest4: function(A, B, input, expect ) {
var result = "";

switch ( input ) {
default:   result += "default"; break;
case "a":  result += "a";       break;
case "b":  result += "b";       break;
case A:    result += "A";       break;
case B:    result += "B";       break;
case new Boolean(true): result += "new TRUE";   break;
case new Boolean(false): result += "new FALSE"; break;
case NULL: result += "NULL";    break;
case UNDEFINED: result += "UNDEFINED"; break;
case true: result += "true";    break;
case false: result += "false";  break;
case TRUE:  result += "TRUE";   break;
case FALSE: result += "FALSE";  break;
case 0:    result += "0";       break;
case 1:    result += "1";       break;
case new Number(0) : result += "new ZERO";  break;
case new Number(1) : result += "new ONE";   break;
case ONE:  result += "ONE";     break;
case ZERO: result += "ZERO";    break;
}

this.TestCase(
"", //SECTION
"switch with no breaks:  input is " + input,
expect,
result );
},

/**
*  Check to see if the input is valid for java.lang.Integer. If it is
*  not valid, throw INVALID_JAVA_INTEGER_VALUE.  If input is valid,
*  return Number( v )
*
*/

newJavaInteger: function( v ) {
value = Number( v );
if ( Math.floor(value) != value || isNaN(value) ) {
throw ( INVALID_JAVA_INTEGER_VALUE );
} else {
return value;
}
},

/**
*  Call newJavaInteger( value ) from within a try block.  Catch any
*  exception, and store it in result.  Verify that we got the right
*  return value from newJavaInteger in cases in which we do not expect
*  exceptions, and that we got the exception in cases where an exception
*  was expected.
*/
TryNewJavaInteger: function( value, expect ) {
var finalTest = false;

try {
result = this.newJavaInteger( value );
} catch ( e ) {
result = String( e );
} finally {
finalTest = true;
}
this.TestCase(
"", //SECTION
"newJavaValue( " + value +" )",
expect,
result);
this.TestCase(
"", //SECTION
"newJavaValue( " + value +" ) hit finally block",
true,
finalTest);

},

/**
*  This function contains a try block with no catch block,
*  but it does have a finally block.  Try to evaluate expressions
*  that do and do not throw exceptions.
*/

TrySomething: function( expression, throwing ) {
innerFinally = "FAIL: DID NOT HIT INNER FINALLY BLOCK";
if (throwing) {
outerCatch = "FAILED: NO EXCEPTION CAUGHT";
} else {
outerCatch = "PASS";
}
outerFinally = "FAIL: DID NOT HIT OUTER FINALLY BLOCK";

try {
try {
eval( expression );
} finally {
innerFinally = "PASS";
}
} catch ( e  ) {
if (throwing) {
outerCatch = "PASS";
} else {
outerCatch = "FAIL: HIT OUTER CATCH BLOCK";
}
} finally {
outerFinally = "PASS";
}


this.TestCase(
"", //SECTION
"eval( " + expression +" )",
"PASS",
innerFinally );
this.TestCase(
"", //SECTION
"eval( " + expression +" )",
"PASS",
outerCatch );
this.TestCase(
"", //SECTION
"eval( " + expression +" )",
"PASS",
outerFinally );
},


Thrower: function( v ) {
throw "Caught " + v;
},

/**
*  Evaluate a string.  Catch any exceptions thrown.  If no exception is
*  expected, verify the result of the evaluation.  If an exception is
*  expected, verify that we got the right exception.
*/

TryToCatch: function( value, expect ) {
try {
result = eval( value );
} catch ( e ) {
result = e;
}

this.TestCase(
"", //SECTION
"eval( " + value +" )",
expect,
result );
},

Eval: function( v ) {
return eval( v );
},

/**
*  Evaluate a string.  Catch any exceptions thrown.  If no exception is
*  expected, verify the result of the evaluation.  If an exception is
*  expected, verify that we got the right exception.
*/

TryToCatch5: function( value, expect ) {
try {
result = this.Eval( value );
} catch ( e ) {
result = e;
}

this.TestCase(
"", //SECTION
"eval( " + value +" )",
expect,
result );
},

/**
*  This function has the try block that has a with block within it.
*  Test cases are added in this function.  Within the with block, the
*  object's "check" function is called.  If the test object's exception
*  property is true, we expect the result to be the exception value.
*  If exception is false, then we expect the result to be the value of
*  the object.
*/
TryWith: function( object ) {
var EXCEPTION_STRING = "Exception thrown:";

try {
with ( object ) {
result = check();
}
} catch ( e ) {
result = e;
}

this.TestCase(
"", //SECTION
"TryWith( " + object.value +" )",
(object.exception ? EXCEPTION_STRING +": " + object.valueOf() : object.valueOf()),
result );
},

/**
*  This function has a for-in statement within a try block.  Test cases
*  are added after the try-catch-finally statement.  Within the for-in
*  block, call a function that can throw an exception.  Verify that any
*  exceptions are properly caught.
*/

TryForIn: function( object ) {
var EXCEPTION_STRING = "Exception thrown:";

try {
for ( p in object ) {
if ( typeof object[p] == "function" ) {
result = object[p]();
}
}
} catch ( e ) {
result = e;
}

this.TestCase(
"", //SECTION
"TryForIn( " + object+ " )",
(object.exception ? EXCEPTION_STRING +": " + object.value : object.value),
result );

},

Integer: function( value, exception ) {
var INVALID_INTEGER_VALUE = "Invalid value for java.lang.Integer constructor";

try {
this.value = checkValue( value );
} catch ( e ) {
this.value = e.toString();
}

this.TestCase(
"", //SECTION
"Integer( " + value +" )",
(exception ? INVALID_INTEGER_VALUE +": " + value : this.value),
this.value );
},



checkValue: function( value ) {
var INVALID_INTEGER_VALUE = "Invalid value for java.lang.Integer constructor";

if ( Math.floor(value) != value || isNaN(value) ) {
throw ( INVALID_INTEGER_VALUE +": " + value );
} else {
return value;
}
},

TryInWhile: function( object ) {
var EXCEPTION_STRING = "Exception thrown: ";
var NO_EXCEPTION_STRING = "No exception thrown: ";

result = null;
while ( true ) {
try {
object.thrower();
result = NO_EXCEPTION_STRING + object.value;
break;
} catch ( e ) {
result = e;
break;
}
}

this.TestCase(
"", //SECTION
"( "+ object  +".thrower() )",
(object.result
? EXCEPTION_STRING + object.value :
NO_EXCEPTION_STRING + object.value),
result );
},

NestedTry: function( object ) {
result = 0;
try {
object.tryOne();
result += 1;
try {
object.tryTwo();
result += 2;
} catch ( e ) {
result +=4;
} finally {
result += 8;
}
} catch ( e ) {
result += 16;
} finally {
result += 32;
}

this.TestCase(
"", //SECTION
object.description,
object.result,
result );
},

/**
*  This function contains a try block with no catch block,
*  but it does have a finally block.  Try to evaluate expressions
*  that do and do not throw exceptions.
*
* The productioni TryStatement Block Finally is evaluated as follows:
* 1. Evaluate Block
* 2. Evaluate Finally
* 3. If Result(2).type is normal return result 1 (in the test case, result 1 has
*    the completion type throw)
* 4. return result 2 (does not get hit in this case)
*
*/

TrySomething: function( expression, throwing ) {
innerFinally = "FAIL: DID NOT HIT INNER FINALLY BLOCK";
if (throwing) {
outerCatch = "FAILED: NO EXCEPTION CAUGHT";
} else {
outerCatch = "PASS";
}
outerFinally = "FAIL: DID NOT HIT OUTER FINALLY BLOCK";


// If the inner finally does not throw an exception, the result
// of the try block should be returned.  (Type of inner return
// value should be throw if finally executes correctly

try {
try {
throw 0;
} finally {
innerFinally = "PASS";
eval( expression );
}
} catch ( e  ) {
if (throwing) {
outerCatch = "PASS";
} else {
outerCatch = "FAIL: HIT OUTER CATCH BLOCK";
}
} finally {
outerFinally = "PASS";
}


this.TestCase(
"", //SECTION
"eval( " + expression +" ): evaluated inner finally block",
"PASS",
innerFinally );
this.TestCase(
"", //SECTION
"eval( " + expression +" ): evaluated outer catch block ",
"PASS",
outerCatch );
this.TestCase(
"", //SECTION
"eval( " + expression +" ):  evaluated outer finally block",
"PASS",
outerFinally );
},

DoWhile: function() {
result = "pass";

while (false) {
result = "fail";
break;
}

this.TestCase(
"", //SECTION
"while statement: don't evaluate statement is expression is false",
"pass",
result );

},

DoWhile2: function( object ) {
result = "pass";

while ( expression = object.whileExpression ) {
eval( object.statements );
}

// verify that the while expression was evaluated

this.TestCase(
"", //SECTION
"verify that while expression was evaluated (should be "+
object.whileExpression +")",
"pass",
(object.whileExpression == expression ||
( isNaN(object.whileExpression) && isNaN(expression) )
) ? "pass" : "fail" );

this.TestCase(
"", //SECTION
object.description,
"pass",
result );
},

DoWhile33: function( object ) {
result = "fail:  statements in while block were not evaluated";

while ( expression = object.whileExpression ) {
eval( object.statements );
break;
}

// verify that the while expression was evaluated

this.TestCase(
"", //SECTION
"verify that while expression was evaluated (should be "+
object.whileExpression +")",
"pass",
(object.whileExpression == expression ||
( isNaN(object.whileExpression) && isNaN(expression) )
) ? "pass" : "fail" );

this.TestCase(
"", //SECTION
object.description,
"pass",
result );
},

/**
*  Break out of a while by calling return.
*
*  Tests:  12.6.2 step 6.
*/
dowhile: function() {
result = "pass";

while (true) {
return result;
result = "fail: hit code after return statement";
break;
}
},

DoWhile_14: function() {
description = "return statement in a while block";

result = this.dowhile();

this.TestCase(
"", //SECTION
"DoWhile_1" + description,
"pass",
result );
},

/**
*  While with a labeled continue statement.  Verify that statements
*  after the continue statement are not evaluated.
*
*  Tests: 12.6.2 step 8.
*
*/
DoWhile_24: function() {
var description = "while with a labeled continue statement";
var result1 = "pass";
var result2 = "fail: did not execute code after loop, but inside label";
var i = 0;
var j = 0;

theloop:
while( i++ < 10  ) {
j++;
continue theloop;
result1 = "failed:  hit code after continue statement";
}
result2 = "pass";

this.TestCase(
"", //SECTION
"DoWhile_2:  " +description + " - code inside the loop, before the continue should be executed ("+j+")",
true,
j == 10 );

this.TestCase(
"", //SECTION
"DoWhile_2:  " +description +" - code after labeled continue should not be executed",
"pass",
result1 );

this.TestCase(
"", //SECTION
"DoWhile_2:  " +description +" - code after loop but inside label should be executed",
"pass",
result2 );
},

/**
*  While with a labeled break.
*
*/
DoWhile_34: function() {
var description = "while with a labeled break statement";
var result1 = "pass";
var result2 = "pass";
var result3 = "fail: did not get to code after label";

woohoo: {
while( true ) {
break woohoo;
result1 = "fail: got to code after a break";
}
result2 = "fail: got to code outside of loop but inside label";
}

result3 = "pass";

this.TestCase(
"", //SECTION
"DoWhile_3: " +description +" - verify break out of loop",
"pass",
result1 );


this.TestCase(
"", //SECTION
"DoWhile_3: " +description +" - verify break out of label",
"pass",
result2 );

this.TestCase(
"", //SECTION
"DoWhile_3: " +description + " - verify correct exit from label",
"pass",
result3 );
},


/**
*  Labled while with an unlabeled break
*
*/
DoWhile_44: function() {
var description = "labeled while with an unlabeled break";
var result1 = "pass";
var result2 = "pass";
var result3 = "fail: did not evaluate statement after label";

woohooboy: {
while( true ) {
break woohooboy;
result1 = "fail: got to code after the break";
}
result2 = "fail: broke out of while, but not out of label";
}
result3 = "pass";

this.TestCase(
"", //SECTION
"DoWhile_4: " +description +" - verify break out of while loop",
"pass",
result1 );

this.TestCase(
"", //SECTION
"DoWhile_4: " +description + " - verify break out of label",
"pass",
result2 );

this.TestCase(
"", //SECTION
"DoWhile_4: " +description +" - verify that statements after label are evaluated",
"pass",
result3 );
},

/**
*  in this case, should behave the same way as
*
*
*/
DoWhile_54: function() {
var description = "while with a labeled continue statement";
var result1 = "pass";
var result2 = "fail: did not execute code after loop, but inside label";
var i = 0;
var j = 0;

theloop: {
j++;
while( i++ < 10  ) {
continue;
result1 = "failed:  hit code after continue statement";
}
result2 = "pass";
}

this.TestCase(
"", //SECTION
"DoWhile_5: " +description + " - continue should not execute statements above the loop",
true,
( j == 1 ) );

this.TestCase(
"", //SECTION
"DoWhile_5: " +description +" - code after labeled continue should not be executed",
"pass",
result1 );

this.TestCase(
"", //SECTION
"DoWhile_5: " +description +" - code after loop but inside label should be executed",
"pass",
result2 );
},

InstanceOf: function( object_1, object_2, expect ) {
result = object_1 instanceof object_2;

this.TestCase(
"", //SECTION
"(" + object_1 + ") instanceof " + object_2,
expect,
result );
},

InstanceOf2: function( object, constructor ) {
while ( object != null ) {
if ( object == constructor.prototype ) {
return true;
}
object = object.__proto__;
}
return false;
}





})
.endType();
