vjo.ctype("dsf.jslang.feature.tests.Js12StatementsTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({
test_break:function(){


/**
Filename:     break.js
Description:  'Tests the break statement'

Author:       Nick Lerissa
Date:         March 18, 1998
*/

var SECTION = 'As described in Netscape doc "Whats new in JavaScript 1.2"';
var VERSION = 'no version';
// start//test();
var TITLE   = 'statements: break';

// writeHeaderToLog("Executing script: break.js");
// writeHeaderToLog( SECTION + " "+ TITLE);

var i,j;

for (i = 0; i < 1000; i++)
{
if (i == 100) break;
}

// 'breaking out of "for" loop'
this.TestCase ( SECTION, 'breaking out of "for" loop',
100, i);

j = 2000;

out1:
for (i = 0; i < 1000; i++)
{
if (i == 100)
{
out2:
for (j = 0; j < 1000; j++)
{
if (j == 500) break out1;
}
j = 2001;
}
j = 2002;
}

// 'breaking out of a "for" loop with a "label"'
this.TestCase ( SECTION, 'breaking out of a "for" loop with a "label"',
500, j);

i = 0;

while (i < 1000)
{
if (i == 100) break;
i++;
}

// 'breaking out of a "while" loop'
this.TestCase ( SECTION, 'breaking out of a "while" loop',
100, i );


j = 2000;
i = 0;

out3:
while (i < 1000)
{
if (i == 100)
{
j = 0;
out4:
while (j < 1000)
{
if (j == 500) break out3;
j++;
}
j = 2001;
}
j = 2002;
i++;
}

// 'breaking out of a "while" loop with a "label"'
this.TestCase ( SECTION, 'breaking out of a "while" loop with a "label"',
500, j);

i = 0;

do
{
if (i == 100) break;
i++;
} while (i < 1000);

// 'breaking out of a "do" loop'
this.TestCase ( SECTION, 'breaking out of a "do" loop',
100, i );

j = 2000;
i = 0;

out5:
do
{
if (i == 100)
{
j = 0;
out6:
do
{
if (j == 500) break out5;
j++;
}while (j < 1000);
j = 2001;
}
j = 2002;
i++;
}while (i < 1000);

// 'breaking out of a "do" loop with a "label"'
this.TestCase ( SECTION, 'breaking out of a "do" loop with a "label"',
500, j);

//test();

},
test_continue:function(){


/**
Filename:     continue.js
Description:  'Tests the continue statement'

Author:       Nick Lerissa
Date:         March 18, 1998
*/

var SECTION = 'As described in Netscape doc "Whats new in JavaScript 1.2"';
var VERSION = 'no version';
// start//test();
var TITLE   = 'statements: continue';

// writeHeaderToLog("Executing script: continue.js");
// writeHeaderToLog( SECTION + " "+ TITLE);

var i,j;

j = 0;
for (i = 0; i < 200; i++)
{
if (i == 100)
continue;
j++;
}

// '"continue" in a "for" loop'
this.TestCase ( SECTION, '"continue" in "for" loop',
199, j);


j = 0;
out1:
for (i = 0; i < 1000; i++)
{
if (i == 100)
{
out2:
for (var k = 0; k < 1000; k++)
{
if (k == 500) continue out1;
}
j = 3000;
}
j++;
}

// '"continue" in a "for" loop with a "label"'
this.TestCase ( SECTION, '"continue" in "for" loop with a "label"',
999, j);

i = 0;
j = 1;

while (i != j)
{
i++;
if (i == 100) continue;
j++;
}

// '"continue" in a "while" loop'
this.TestCase ( SECTION, '"continue" in a "while" loop',
100, j );

j = 0;
i = 0;
out3:
while (i < 1000)
{
if (i == 100)
{
var k = 0;
out4:
while (k < 1000)
{
if (k == 500)
{
i++;
continue out3;
}
k++;
}
j = 3000;
}
j++;
i++;
}

// '"continue" in a "while" loop with a "label"'
this.TestCase ( SECTION, '"continue" in a "while" loop with a "label"',
999, j);

i = 0;
j = 1;

do
{
i++;
if (i == 100) continue;
j++;
} while (i != j);


// '"continue" in a "do" loop'
this.TestCase ( SECTION, '"continue" in a "do" loop',
100, j );

j = 0;
i = 0;
out5:
do
{
if (i == 100)
{
var k = 0;
out6:
do
{
if (k == 500)
{
i++;
continue out5;
}
k++;
}while (k < 1000);
j = 3000;
}
j++;
i++;
}while (i < 1000);

// '"continue" in a "do" loop with a "label"'
this.TestCase ( SECTION, '"continue" in a "do" loop with a "label"',
999, j);

//test();

},
test_do_while:function(){


/**
Filename:     do_while.js
Description:  'This tests the new do_while loop'

Author:       Nick Lerissa
Date:         Fri Feb 13 09:58:28 PST 1998
*/

var SECTION = 'As described in Netscape doc "Whats new in JavaScript 1.2"';
var VERSION = 'no version';
// start//test();
var TITLE = 'statements: do_while';

// writeHeaderToLog('Executing script: do_while.js');
// writeHeaderToLog( SECTION + " "+ TITLE);

var done = false;
var x = 0;
do
{
if (x++ == 3) done = true;
} while (!done);

this.TestCase( SECTION, "do_while ",
4, x);

//load('d:/javascript/tests/output/statements/do_while.js')
//test();


},
test_switch:function(){


/**
Filename:     switch.js
Description:  'Tests the switch statement'

http://scopus.mcom.com/bugsplat/show_bug.cgi?id=323696

Author:       Nick Lerissa
Date:         March 19, 1998
*/

var SECTION = 'As described in Netscape doc "Whats new in JavaScript 1.2"';
var VERSION = 'no version';
var TITLE   = 'statements: switch';
var BUGNUMBER="323696";

// start//test();
// writeHeaderToLog("Executing script: switch.js");
// writeHeaderToLog( SECTION + " "+ TITLE);


var var1 = "match string";
var match1 = false;
var match2 = false;
var match3 = false;

switch (var1)
{
case "match string":
match1 = true;
case "bad string 1":
match2 = true;
break;
case "bad string 2":
match3 = true;
}

this.TestCase ( SECTION, 'switch statement',
true, match1);

this.TestCase ( SECTION, 'switch statement',
true, match2);

this.TestCase ( SECTION, 'switch statement',
false, match3);

var var2 = 3;

 match1 = false;
 match2 = false;
 match3 = false;
var match4 = false;
var match5 = false;

switch (var2)
{
case 1:
/*	        switch (var1)
{
case "foo":
match1 = true;
break;
case 3:
match2 = true;
break;
}*/
match3 = true;
break;
case 2:
match4 = true;
break;
case 3:
match5 = true;
break;
}
this.TestCase ( SECTION, 'switch statement',
false, match1);

this.TestCase ( SECTION, 'switch statement',
false, match2);

this.TestCase ( SECTION, 'switch statement',
false, match3);

this.TestCase ( SECTION, 'switch statement',
false, match4);

this.TestCase ( SECTION, 'switch statement',
true, match5);

//test();

},
test_switch2:function(){


/**
Filename:     switch2.js
Description:  'Tests the switch statement'

http://scopus.mcom.com/bugsplat/show_bug.cgi?id=323696

Author:       Norris Boyd
Date:         July 31, 1998
*/

var SECTION = 'As described in Netscape doc "Whats new in JavaScript 1.2"';
var VERSION = 'no version';
var TITLE   = 'statements: switch';
var BUGNUMBER="323626";

// start//test();
// writeHeaderToLog("Executing script: switch2.js");
// writeHeaderToLog( SECTION + " "+ TITLE);

// test defaults not at the end; regression test for a bug that
// nearly made it into 4.06
function f0(i) {
switch(i) {
default:
case "a":
case "b":
return "ab*"
case "c":
return "c";
case "d":
return "d";
}
return "";
}
this.TestCase(SECTION, 'switch statement',
f0("a"), "ab*");

this.TestCase(SECTION, 'switch statement',
f0("b"), "ab*");

this.TestCase(SECTION, 'switch statement',
f0("*"), "ab*");

this.TestCase(SECTION, 'switch statement',
f0("c"), "c");

this.TestCase(SECTION, 'switch statement',
f0("d"), "d");

function f1(i) {
switch(i) {
case "a":
case "b":
default:
return "ab*"
case "c":
return "c";
case "d":
return "d";
}
return "";
}

this.TestCase(SECTION, 'switch statement',
f1("a"), "ab*");

this.TestCase(SECTION, 'switch statement',
f1("b"), "ab*");

this.TestCase(SECTION, 'switch statement',
f1("*"), "ab*");

this.TestCase(SECTION, 'switch statement',
f1("c"), "c");

this.TestCase(SECTION, 'switch statement',
f1("d"), "d");

// Switch on integer; will use TABLESWITCH opcode in C engine
function f2(i) {
switch (i) {
case 0:
case 1:
return 1;
case 2:
return 2;
}
// with no default, control will fall through
return 3;
}

this.TestCase(SECTION, 'switch statement',
f2(0), 1);

this.TestCase(SECTION, 'switch statement',
f2(1), 1);

this.TestCase(SECTION, 'switch statement',
f2(2), 2);

this.TestCase(SECTION, 'switch statement',
f2(3), 3);

// empty switch: make sure expression is evaluated
var se = 0;
switch (se = 1) {
}
this.TestCase(SECTION, 'switch statement',
se, 1);

// only default
se = 0;
switch (se) {
default:
se = 1;
}
this.TestCase(SECTION, 'switch statement',
se, 1);

// in loop, break should only break out of switch
se = 0;
for (var i=0; i < 2; i++) {
switch (i) {
case 0:
case 1:
break;
}
se = 1;
}
this.TestCase(SECTION, 'switch statement',
se, 1);

// test "fall through"
se = 0;
i = 0;
switch (i) {
case 0:
se++;
/* fall through */
case 1:
se++;
break;
}
this.TestCase(SECTION, 'switch statement',
se, 2);
// print("hi");

//test();

// Needed: tests for evaluation time of case expressions.
// This issue was under debate at ECMA, so postponing for now.


}}).endType()
