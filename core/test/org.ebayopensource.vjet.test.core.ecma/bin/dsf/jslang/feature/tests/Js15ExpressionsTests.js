vjo.ctype("dsf.jslang.feature.tests.Js15ExpressionsTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({

reportCompare :function  (expectedValue, actualValue, statusItems) {
new dsf.jslang.feature.tests.BaseTest().TestCase( ' ',  statusItems, expectedValue,  actualValue);
},



inSection :function (x) {
var SECT_PREFIX = 'Section ';
var SECT_SUFFIX = ' of test - ';
return SECT_PREFIX + x + SECT_SUFFIX;
},

/*
* If available, arr.toSource() gives more detail than arr.toString()
*
* var arr = Array(1,2,'3');
*
* arr.toSource()
* [1, 2, "3"]
*
* arr.toString()
* 1,2,3
*
* But toSource() doesn't exist in Rhino, so use our own imitation, below -
*
*/
formatArray :function (arr)
{
try
{
return arr.toSource();
}
catch(e)
{
return toSource(arr);
}
},





test_regress__192288:function(){

var UBound = 0;
var BUGNUMBER = 192288;
var summary = 'Testing 0/0 inside functions ';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];


function f()
{
return 0/0;
}

status = this.inSection(1);
actual = isNaN(f());
expect = true;
addThis();

status = this.inSection(2);
actual = isNaN(f.apply(this));
expect = true;
addThis();

status = this.inSection(3);
actual = isNaN(eval("f.apply(this)"));
expect = true;
addThis();

status = this.inSection(4);
expect = true;
addThis();

status = this.inSection(5);
actual = isNaN(eval("Function('return 0/0;')()"));
expect = true;
addThis();



//-----------------------------------------------------------------------------
test();
//-----------------------------------------------------------------------------



function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
}


function test()
{
//   enterFunc('test');
//   printBugNumber(BUGNUMBER);
//   printStatus(summary);

for (var i=0; i<UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//   exitFunc ('test');
}

},
test_regress__394673:function(){

//-----------------------------------------------------------------------------
var BUGNUMBER = 394673;
var summary = 'Parsing long chains of "&&" or "||"';
var actual = 'No Error';
var expect = 'No Error';

// printBugNumber(BUGNUMBER);
// printStatus (summary);

var N = 70*1000;
var counter;

counter = 0;
var x = build("&&")();
if (x !== true)
throw "Unexpected result: x="+x;
if (counter != N)
throw "Unexpected counter: counter="+counter;

counter = 0;
x = build("||")();
if (x !== true)
throw "Unexpected result: x="+x;
if (counter != 1)
throw "Unexpected counter: counter="+counter;

function build(operation)
{
var counter;
var a = [];
a.push("return f()");
for (var i = 1; i != N - 1; ++i)
a.push("f()");
a.push("f();");
return new Function(a.join(operation));
}

function f()
{
++counter;
return true;
}

this.reportCompare(expect, actual, summary);

},
test_regress__96526__argsub:function(){

var UBound = 0;
var BUGNUMBER = 96526;
var summary = 'Testing "use" and "set" ops on expressions like a[i][j][k]';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];

var z='magic';
Number.prototype.magic=42;

status = this.inSection(1);
actual = f(2,[1,2,[3,4]]);
expect = 42;
addThis();


function f(j,k)
{
status = this.inSection(2);
actual = this.formatArray(arguments[2]);
expect = this.formatArray([1,2,[3,4]]);
addThis();

status = this.inSection(3);
actual = this.formatArray(arguments[2][j]);
expect = this.formatArray([3,4]);
addThis();

status = this.inSection(4);
actual = arguments[2][j][k];
expect = 4;
addThis();

status = this.inSection(5);
actual = arguments[2][j][k][z];
expect = 42;
addThis();

return arguments[2][j][k][z];
}



//-----------------------------------------------------------------------------
test();
//-----------------------------------------------------------------------------



function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
}


function test()
{
//   enterFunc('test');
//   printBugNumber(BUGNUMBER);
//   printStatus(summary);

for (var i=0; i<UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//   exitFunc ('test');
}

},
test_regress__96526__delelem:function(){

var UBound = 0;
var BUGNUMBER = 96526;
var summary = 'Testing "use" and "set" ops on expressions like a[i][j][k]';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];

var z='magic';
Number.prototype.magic=42;
f(2,1,[-1,0,[1,2,[3,4]]]);

function f(j,k,a)
{
status = this.inSection(1);
actual = this.formatArray(a[2]);
expect = this.formatArray([1,2,[3,4]]);
addThis();

status = this.inSection(2);
actual = this.formatArray(a[2][j]);
expect = this.formatArray([3,4]);
addThis();

status = this.inSection(3);
actual = a[2][j][k];
expect = 4;
addThis();

status = this.inSection(4);
actual = a[2][j][k][z];
expect = 42;
addThis();

delete a[2][j][k];

status = this.inSection(5);
actual = this.formatArray(a[2][j]);
expect = '[3, , ]';
addThis();
}



//-----------------------------------------------------------------------------
test();
//-----------------------------------------------------------------------------



function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
}


function test()
{
//   enterFunc('test');
//   printBugNumber(BUGNUMBER);
//   printStatus(summary);

for (var i=0; i<UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//   exitFunc ('test');
}

},
test_regress__96526__noargsub:function(){

var UBound = 0;
var BUGNUMBER = 96526;
var summary = 'Testing "use" and "set" ops on expressions like a[i][j][k]';
var status = '';
var statusitems = [];
var actual = '';
var actualvalues = [];
var expect= '';
var expectedvalues = [];

var z='magic';
Number.prototype.magic=42;

status = this.inSection(1);
actual = f(2,1,[-1,0,[1,2,[3,4]]]);
expect = 42;
addThis();


function f(j,k,a)
{
status = this.inSection(2);
actual = this.formatArray(a[2]);
expect = this.formatArray([1,2,[3,4]]);
addThis();

status = this.inSection(3);
actual = this.formatArray(a[2][j]);
expect = this.formatArray([3,4]);
addThis();

status = this.inSection(4);
actual = a[2][j][k];
expect = 4;
addThis();

status = this.inSection(5);
actual = a[2][j][k][z];
expect = 42;
addThis();

return a[2][j][k][z];
}



//-----------------------------------------------------------------------------
test();
//-----------------------------------------------------------------------------



function addThis()
{
statusitems[UBound] = status;
actualvalues[UBound] = actual;
expectedvalues[UBound] = expect;
UBound++;
}


function test()
{
//   enterFunc('test');
//   printBugNumber(BUGNUMBER);
//   printStatus(summary);

for (var i=0; i<UBound; i++)
{
this.reportCompare(expectedvalues[i], actualvalues[i], statusitems[i]);
}

//   exitFunc ('test');
}

}}).endType()

