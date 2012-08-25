vjo.ctype("dsf.jslang.feature.tests.Ecma2extensionsTests")
.inherits("dsf.jslang.feature.tests.BaseTestEcma2")
.protos({

constructs: function() {
this.base();
},

/**
*  File Name:          RegExp/constructor-001.js
*  ECMA Section:       15.7.3.3
*  Description:        Based on ECMA 2 Draft 7 February 1999
*
*  Author:             christine@netscape.com
*  Date:               19 February 1999
*/
test_constructor__001: function() {
var SECTION = "RegExp/constructor-001";
var VERSION = "ECMA_2";
var TITLE   = "new RegExp()";

//startTest();

/*
* for each test case, verify:
* - verify that [[Class]] property is RegExp
* - prototype property should be set to RegExp.prototype
* - source is set to the empty string
* - global property is set to false
* - ignoreCase property is set to false
* - multiline property is set to false
* - lastIndex property is set to 0
*/

RegExp.prototype.getClassProperty = Object.prototype.toString;
var re = new RegExp();

this.TestCase("",
"new RegExp().__proto__",
RegExp.prototype,
re.__proto__
);

//test()

},

/**
*  File Name:          RegExp/function-001.js
*  ECMA Section:       15.7.2.1
*  Description:        Based on ECMA 2 Draft 7 February 1999
*
*  Author:             christine@netscape.com
*  Date:               19 February 1999
*/
test_function__001: function() {
var SECTION = "RegExp/function-001";
var VERSION = "ECMA_2";
var TITLE   = "RegExp( pattern, flags )";

//startTest();

/*
* for each test case, verify:
* - verify that [[Class]] property is RegExp
* - prototype property should be set to RegExp.prototype
* - source is set to the empty string
* - global property is set to false
* - ignoreCase property is set to false
* - multiline property is set to false
* - lastIndex property is set to 0
*/

RegExp.prototype.getClassProperty = Object.prototype.toString;
var re = new RegExp();

this.TestCase("",
"new RegExp().__proto__",
RegExp.prototype,
re.__proto__
);

//test();

},

/**
*  File Name:          instanceof-001.js
*  ECMA Section:       11.8.6
*  Description:
*
*  RelationalExpression instanceof Identifier
*
*  Author:             christine@netscape.com
*  Date:               2 September 1998
*/
test_instanceof__001: function() {
var SECTION = "instanceof-001";
var VERSION = "ECMA_2";
var TITLE   = "instanceof";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

function Gen3(value) {
this.value = value;
this.generation = 3;
this.toString = new Function ( "return \"(Gen\"+this.generation+\" instance)\"" );
}
Gen3.name = 3;
Gen3.__proto__.toString = new Function( "return \"(\"+this.name+\" object)\"");

function Gen2(value) {
this.value = value;
this.generation = 2;
}
Gen2.name = 2;
Gen2.prototype = new Gen3();

function Gen1(value) {
this.value = value;
this.generation = 1;
}
Gen1.name = 1;
Gen1.prototype = new Gen2();

function Gen0(value) {
this.value = value;
this.generation = 0;
}
Gen0.name = 0;
Gen0.prototype = new Gen1();


function GenA(value) {
this.value = value;
this.generation = "A";
this.toString = new Function ( "return \"(instance of Gen\"+this.generation+\")\"" );

}
GenA.prototype = new Gen0();
GenA.name = "A";

function GenB(value) {
this.value = value;
this.generation = "B";
this.toString = new Function ( "return \"(instance of Gen\"+this.generation+\")\"" );
}
GenB.name = "B"
GenB.prototype = void 0;

// RelationalExpression is not an object.

this.InstanceOf( true, Boolean, false );
this.InstanceOf( new Boolean(false), Boolean, true );

// __proto__ of RelationalExpression is null.  should return false
genA = new GenA();
genA.__proto__ = null;

this.InstanceOf( genA, GenA, false );

// RelationalExpression.__proto__ ==  (but not ===) Identifier.prototype

this.InstanceOf( new Gen2(), Gen0, false );
this.InstanceOf( new Gen2(), Gen1, false );
this.InstanceOf( new Gen2(), Gen2, true );
this.InstanceOf( new Gen2(), Gen3, true );

// RelationalExpression.__proto__.__proto__ === Identifier.prototype
this.InstanceOf( new Gen0(), Gen0, true );
this.InstanceOf( new Gen0(), Gen1, true );
this.InstanceOf( new Gen0(), Gen2, true );
this.InstanceOf( new Gen0(), Gen3, true );

this.InstanceOf( new Gen0(), Object, true );
this.InstanceOf( new Gen0(), Function, false );

this.InstanceOf( Gen0, Function, true );
this.InstanceOf( Gen0, Object, true );

//test();

},

/**
File Name:          instanceof-002.js
Section:
Description:        Determining Instance Relationships

This test is the same as js1_3/inherit/proto-002, except that it uses
the builtin instanceof operator rather than a user-defined function
called InstanceOf.

This tests Object Hierarchy and Inheritance, as described in the document
Object Hierarchy and Inheritance in JavaScript, last modified on 12/18/97
15:19:34 on http://devedge.netscape.com/.  Current URL:
http://devedge.netscape.com/docs/manuals/communicator/jsobj/contents.htm

This tests the syntax ObjectName.prototype = new PrototypeObject using the
Employee example in the document referenced above.

Author:             christine@netscape.com
Date:               12 november 1997
*/
//    onerror = err;
test_instanceof__002: function() {
var SECTION = "instanceof-002";
var VERSION = "ECMA_2";
var TITLE   = "Determining Instance Relationships";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

function Employee ( name, dept ) {
this.name = name || "";
this.dept = dept || "general";
}

function Manager () {
this.reports = [];
}
Manager.prototype = new Employee();

function WorkerBee ( name, dept, projs ) {
this.base = Employee;
this.base( name, dept)
this.projects = projs || new Array();
}
WorkerBee.prototype = new Employee();

function SalesPerson () {
this.dept = "sales";
this.quota = 100;
}
SalesPerson.prototype = new WorkerBee();

function Engineer ( name, projs, machine ) {
this.base = WorkerBee;
this.base( name, "engineering", projs )
this.machine = machine || "";
}
Engineer.prototype = new WorkerBee();

var pat = new Engineer();

this.TestCase( SECTION,
"pat.__proto__ == Engineer.prototype",
true,
pat.__proto__ == Engineer.prototype );

this.TestCase( SECTION,
"pat.__proto__.__proto__ == WorkerBee.prototype",
true,
pat.__proto__.__proto__ == WorkerBee.prototype );

this.TestCase( SECTION,
"pat.__proto__.__proto__.__proto__ == Employee.prototype",
true,
pat.__proto__.__proto__.__proto__ == Employee.prototype );

this.TestCase( SECTION,
"pat.__proto__.__proto__.__proto__.__proto__ == Object.prototype",
true,
pat.__proto__.__proto__.__proto__.__proto__ == Object.prototype );

this.TestCase( SECTION,
"pat.__proto__.__proto__.__proto__.__proto__.__proto__ == null",
true,
pat.__proto__.__proto__.__proto__.__proto__.__proto__ == null );

this.TestCase( SECTION,
"pat instanceof Engineer",
true,
pat instanceof Engineer );

this.TestCase( SECTION,
"pat instanceof WorkerBee )",
true,
pat instanceof WorkerBee );

this.TestCase( SECTION,
"pat instanceof Employee )",
true,
pat instanceof Employee );

this.TestCase( SECTION,
"pat instanceof Object )",
true,
pat instanceof Object );

this.TestCase( SECTION,
"pat instanceof SalesPerson )",
false,
pat instanceof SalesPerson );
//test();

},

/**
*  File Name:          instanceof-001.js
*  ECMA Section:       11.8.6
*  Description:
*
*  RelationalExpression instanceof Identifier
*
*  Author:             christine@netscape.com
*  Date:               2 September 1998
*/
test_instanceof__003__n: function() {
var SECTION = "instanceof-003-n";
var VERSION = "ECMA_2";
var TITLE   = "instanceof";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

function Gen3(value) {
this.value = value;
this.generation = 3;
this.toString = new Function ( "return \"(Gen\"+this.generation+\" instance)\"" );
}
Gen3.name = 3;
Gen3.__proto__.toString = new Function( "return \"(\"+this.name+\" object)\"");

function Gen2(value) {
this.value = value;
this.generation = 2;
}
Gen2.name = 2;
Gen2.prototype = new Gen3();

function Gen1(value) {
this.value = value;
this.generation = 1;
}
Gen1.name = 1;
Gen1.prototype = new Gen2();

function Gen0(value) {
this.value = value;
this.generation = 0;
}
Gen0.name = 0;
Gen0.prototype = new Gen1();


function GenA(value) {
this.value = value;
this.generation = "A";
this.toString = new Function ( "return \"(instance of Gen\"+this.generation+\")\"" );

}
GenA.prototype = new Gen0();
GenA.name = "A";

function GenB(value) {
this.value = value;
this.generation = "B";
this.toString = new Function ( "return \"(instance of Gen\"+this.generation+\")\"" );
}
GenB.name = "B"
GenB.prototype = void 0;

// Identifier is not a function
DESCRIPTION = "Identifier is not a function";
EXPECTED = "error";

this.InstanceOf( true, true, "error" );

//test();

},

/**
*  File Name:          instanceof-001.js
*  ECMA Section:       11.8.6
*  Description:
*
*  RelationalExpression instanceof Identifier
*
*  Author:             christine@netscape.com
*  Date:               2 September 1998
*/
test_instanceof__003__n_WORKS: function() {
var SECTION = "instanceof-003-n";
var VERSION = "ECMA_2";
var TITLE   = "instanceof";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

function Gen3(value) {
this.value = value;
this.generation = 3;
this.toString = new Function ( "return \"(Gen\"+this.generation+\" instance)\"" );
}
Gen3.name = 3;
Gen3.__proto__.toString = new Function( "return \"(\"+this.name+\" object)\"");

function Gen2(value) {
this.value = value;
this.generation = 2;
}
Gen2.name = 2;
Gen2.prototype = new Gen3();

function Gen1(value) {
this.value = value;
this.generation = 1;
}
Gen1.name = 1;
Gen1.prototype = new Gen2();

function Gen0(value) {
this.value = value;
this.generation = 0;
}
Gen0.name = 0;
Gen0.prototype = new Gen1();


function GenA(value) {
this.value = value;
this.generation = "A";
this.toString = new Function ( "return \"(instance of Gen\"+this.generation+\")\"" );

}
GenA.prototype = new Gen0();
GenA.name = "A";

function GenB(value) {
this.value = value;
this.generation = "B";
this.toString = new Function ( "return \"(instance of Gen\"+this.generation+\")\"" );
}
GenB.name = "B"
GenB.prototype = void 0;

// Identifier is not a function
DESCRIPTION = "Identifier is not a function";
EXPECTED = "error";

try {
this.InstanceOf( true, true, "error" );
fail();
} catch (e) {
assertTrue(e.name == 'TypeError');
assertTrue(e.message == "Can't use instanceof on a non-object.");
}

//test();

},

/**
*  File Name:          instanceof-001.js
*  ECMA Section:       11.8.6
*  Description:
*
*  RelationalExpression instanceof Identifier
*
*  Author:             christine@netscape.com
*  Date:               2 September 1998
*/
test_instanceof__004__n: function() {
var SECTION = "instanceof-004-n";
var VERSION = "ECMA_2";
var TITLE   = "instanceof";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

function Gen3(value) {
this.value = value;
this.generation = 3;
this.toString = new Function ( "return \"(Gen\"+this.generation+\" instance)\"" );
}
Gen3.name = 3;
Gen3.__proto__.toString = new Function( "return \"(\"+this.name+\" object)\"");

function Gen2(value) {
this.value = value;
this.generation = 2;
}
Gen2.name = 2;
Gen2.prototype = new Gen3();

function Gen1(value) {
this.value = value;
this.generation = 1;
}
Gen1.name = 1;
Gen1.prototype = new Gen2();

function Gen0(value) {
this.value = value;
this.generation = 0;
}
Gen0.name = 0;
Gen0.prototype = new Gen1();


function GenA(value) {
this.value = value;
this.generation = "A";
this.toString = new Function ( "return \"(instance of Gen\"+this.generation+\")\"" );

}
GenA.prototype = new Gen0();
GenA.name = "A";

function GenB(value) {
this.value = value;
this.generation = "B";
this.toString = new Function ( "return \"(instance of Gen\"+this.generation+\")\"" );
}
GenB.name = "B"
GenB.prototype = void 0;

// Identifier is not a function

DESCRIPTION = "Identifier is not a function";
EXPECTED = "error";

this.InstanceOf( new Boolean(true), false, "error" );

//test();

},

/**
*  File Name:          instanceof-001.js
*  ECMA Section:       11.8.6
*  Description:
*
*  RelationalExpression instanceof Identifier
*
*  Author:             christine@netscape.com
*  Date:               2 September 1998
*/
test_instanceof__004__n_WORKS: function() {
var SECTION = "instanceof-004-n";
var VERSION = "ECMA_2";
var TITLE   = "instanceof";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

function Gen3(value) {
this.value = value;
this.generation = 3;
this.toString = new Function ( "return \"(Gen\"+this.generation+\" instance)\"" );
}
Gen3.name = 3;
Gen3.__proto__.toString = new Function( "return \"(\"+this.name+\" object)\"");

function Gen2(value) {
this.value = value;
this.generation = 2;
}
Gen2.name = 2;
Gen2.prototype = new Gen3();

function Gen1(value) {
this.value = value;
this.generation = 1;
}
Gen1.name = 1;
Gen1.prototype = new Gen2();

function Gen0(value) {
this.value = value;
this.generation = 0;
}
Gen0.name = 0;
Gen0.prototype = new Gen1();


function GenA(value) {
this.value = value;
this.generation = "A";
this.toString = new Function ( "return \"(instance of Gen\"+this.generation+\")\"" );

}
GenA.prototype = new Gen0();
GenA.name = "A";

function GenB(value) {
this.value = value;
this.generation = "B";
this.toString = new Function ( "return \"(instance of Gen\"+this.generation+\")\"" );
}
GenB.name = "B"
GenB.prototype = void 0;

// Identifier is not a function

DESCRIPTION = "Identifier is not a function";
EXPECTED = "error";

try {
this.InstanceOf( new Boolean(true), false, "error" );
fail();
} catch (e) {
assertTrue(e.name == 'TypeError');
assertTrue(e.message == "Can't use instanceof on a non-object.");
}

//test();

},

/**
*  File Name:          instanceof-001.js
*  ECMA Section:       11.8.6
*  Description:
*
*  RelationalExpression instanceof Identifier
*
*  Author:             christine@netscape.com
*  Date:               2 September 1998
*/
test_instanceof__005__n: function() {
var SECTION = "instanceof-005-n";
var VERSION = "ECMA_2";
var TITLE   = "instanceof";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

function Gen3(value) {
this.value = value;
this.generation = 3;
this.toString = new Function ( "return \"(Gen\"+this.generation+\" instance)\"" );
}
Gen3.name = 3;
Gen3.__proto__.toString = new Function( "return \"(\"+this.name+\" object)\"");

function Gen2(value) {
this.value = value;
this.generation = 2;
}
Gen2.name = 2;
Gen2.prototype = new Gen3();

function Gen1(value) {
this.value = value;
this.generation = 1;
}
Gen1.name = 1;
Gen1.prototype = new Gen2();

function Gen0(value) {
this.value = value;
this.generation = 0;
}
Gen0.name = 0;
Gen0.prototype = new Gen1();


function GenA(value) {
this.value = value;
this.generation = "A";
this.toString = new Function ( "return \"(instance of Gen\"+this.generation+\")\"" );

}
GenA.prototype = new Gen0();
GenA.name = "A";

function GenB(value) {
this.value = value;
this.generation = "B";
this.toString = new Function ( "return \"(instance of Gen\"+this.generation+\")\"" );
}
GenB.name = "B"
GenB.prototype = void 0;


// Identifier is a function, prototype of Identifier is not an object

DESCRIPTION = "Identifier is a function, prototype of Identifier is not an object";
EXPECTED = "error";

this.InstanceOf( new GenB(), GenB, "error" );

//test();

},

/**
*  File Name:          instanceof-001.js
*  ECMA Section:       11.8.6
*  Description:
*
*  RelationalExpression instanceof Identifier
*
*  Author:             christine@netscape.com
*  Date:               2 September 1998
*/
test_instanceof__005__n_WORKS: function() {
var SECTION = "instanceof-005-n";
var VERSION = "ECMA_2";
var TITLE   = "instanceof";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

function Gen3(value) {
this.value = value;
this.generation = 3;
this.toString = new Function ( "return \"(Gen\"+this.generation+\" instance)\"" );
}
Gen3.name = 3;
Gen3.__proto__.toString = new Function( "return \"(\"+this.name+\" object)\"");

function Gen2(value) {
this.value = value;
this.generation = 2;
}
Gen2.name = 2;
Gen2.prototype = new Gen3();

function Gen1(value) {
this.value = value;
this.generation = 1;
}
Gen1.name = 1;
Gen1.prototype = new Gen2();

function Gen0(value) {
this.value = value;
this.generation = 0;
}
Gen0.name = 0;
Gen0.prototype = new Gen1();


function GenA(value) {
this.value = value;
this.generation = "A";
this.toString = new Function ( "return \"(instance of Gen\"+this.generation+\")\"" );

}
GenA.prototype = new Gen0();
GenA.name = "A";

function GenB(value) {
this.value = value;
this.generation = "B";
this.toString = new Function ( "return \"(instance of Gen\"+this.generation+\")\"" );
}
GenB.name = "B"
GenB.prototype = void 0;


// Identifier is a function, prototype of Identifier is not an object

DESCRIPTION = "Identifier is a function, prototype of Identifier is not an object";
EXPECTED = "error";

try {
this.InstanceOf( new GenB(), GenB, "error" );
} catch (e) {
assertTrue(e.name == 'TypeError');
assertTrue(e.message.indexOf("'prototype' property of") >= 0);
assertTrue(e.message.indexOf("is not an object.") >= 0);
}

//test();

},

/**
*  File Name:          instanceof-001.js
*  ECMA Section:       11.8.6
*  Description:
*
*  RelationalExpression instanceof Identifier
*
*  Author:             christine@netscape.com
*  Date:               2 September 1998
*/
test_instanceof__006: function() {
var SECTION = "instanceof-001";
var VERSION = "ECMA_2";
var TITLE   = "instanceof";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

function Gen3(value) {
this.value = value;
this.generation = 3;
this.toString = new Function ( "return \"(Gen\"+this.generation+\" instance)\"" );
}
Gen3.name = 3;
Gen3.__proto__.toString = new Function( "return \"(\"+this.name+\" object)\"");

function Gen2(value) {
this.value = value;
this.generation = 2;
}
Gen2.name = 2;
Gen2.prototype = new Gen3();

function Gen1(value) {
this.value = value;
this.generation = 1;
}
Gen1.name = 1;
Gen1.prototype = new Gen2();

function Gen0(value) {
this.value = value;
this.generation = 0;
}
Gen0.name = 0;
Gen0.prototype = new Gen1();


function GenA(value) {
this.value = value;
this.generation = "A";
this.toString = new Function ( "return \"(instance of Gen\"+this.generation+\")\"" );

}
GenA.prototype = new Gen0();
GenA.name = "A";

function GenB(value) {
this.value = value;
this.generation = "B";
this.toString = new Function ( "return \"(instance of Gen\"+this.generation+\")\"" );
}
GenB.name = "B"
GenB.prototype = void 0;

// RelationalExpression is not an object.

//    InstanceOf( true, Boolean, false );
this.InstanceOf( new Boolean(false), Boolean, true );

//test();

}

})
.endType();








