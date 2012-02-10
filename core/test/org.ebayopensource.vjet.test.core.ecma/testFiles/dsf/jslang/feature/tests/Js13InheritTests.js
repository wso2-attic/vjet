vjo.ctype("dsf.jslang.feature.tests.Js13InheritTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({

constructs:function(){
this.base();
},

/** @Test
File Name:         proto_1.js
Section:
Description:        new PrototypeObject

This tests Object Hierarchy and Inheritance, as described in the document
Object Hierarchy and Inheritance in JavaScript, last modified on 12/18/97
15:19:34 on http://devedge.netscape.com/.  Current URL:
http://devedge.netscape.com/docs/manuals/communicator/jsobj/contents.htm

This tests the syntax ObjectName.prototype = new PrototypeObject using the
Employee example in the document referenced above.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_proto_1: function () {
var SECTION = "proto_1.js";
//var VERSION = "JS1_3";
//var TITLE   = "new PrototypeObject";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

function Employee () {
this.name = "";
this.dept = "general";
}
function Manager () {
this.reports = [];
}
Manager.prototype = new Employee();

function WorkerBee () {
this.projects = new Array();
}
WorkerBee.prototype = new Employee();

function SalesPerson () {
this.dept = "sales";
this.quota = 100;
}
SalesPerson.prototype = new WorkerBee();

function Engineer () {
this.dept = "engineering";
this.machine = "";
}
Engineer.prototype = new WorkerBee();

var jim = new Employee();

this.TestCase( SECTION,
"jim = new Employee(); jim.name",
"",
jim.name );


this.TestCase( SECTION,
"jim = new Employee(); jim.dept",
"general",
jim.dept );

var sally = new Manager();

this.TestCase( SECTION,
"sally = new Manager(); sally.name",
"",
sally.name );
this.TestCase( SECTION,
"sally = new Manager(); sally.dept",
"general",
sally.dept );

this.TestCase( SECTION,
"sally = new Manager(); sally.reports.length",
0,
sally.reports.length );

this.TestCase( SECTION,
"sally = new Manager(); typeof sally.reports",
"object",
typeof sally.reports );

var fred = new SalesPerson();

this.TestCase( SECTION,
"fred = new SalesPerson(); fred.name",
"",
fred.name );

this.TestCase( SECTION,
"fred = new SalesPerson(); fred.dept",
"sales",
fred.dept );

this.TestCase( SECTION,
"fred = new SalesPerson(); fred.quota",
100,
fred.quota );

this.TestCase( SECTION,
"fred = new SalesPerson(); fred.projects.length",
0,
fred.projects.length );

var jane = new Engineer();

this.TestCase( SECTION,
"jane = new Engineer(); jane.name",
"",
jane.name );

this.TestCase( SECTION,
"jane = new Engineer(); jane.dept",
"engineering",
jane.dept );

this.TestCase( SECTION,
"jane = new Engineer(); jane.projects.length",
0,
jane.projects.length );

this.TestCase( SECTION,
"jane = new Engineer(); jane.machine",
"",
jane.machine );
//test();
},

/** @Test
File Name:         proto_10.js
Section:
Description:        new PrototypeObject

This tests Object Hierarchy and Inheritance, as described in the document
Object Hierarchy and Inheritance in JavaScript, last modified on 12/18/97
15:19:34 on http://devedge.netscape.com/.  Current URL:
http://devedge.netscape.com/docs/manuals/communicator/jsobj/contents.htm

This tests the syntax ObjectName.prototype = new PrototypeObject using the
Employee example in the document referenced above.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_proto_10: function () {
var SECTION = "proto_10.js";
//var VERSION = "JS1_3";
//var TITLE   = "Determining Instance Relationships";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

function InstanceOf( object, constructor ) {
return object instanceof constructor;
}
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
"InstanceOf( pat, Engineer )",
true,
InstanceOf( pat, Engineer ) );

this.TestCase( SECTION,
"InstanceOf( pat, WorkerBee )",
true,
InstanceOf( pat, WorkerBee ) );

this.TestCase( SECTION,
"InstanceOf( pat, Employee )",
true,
InstanceOf( pat, Employee ) );

this.TestCase( SECTION,
"InstanceOf( pat, Object )",
true,
InstanceOf( pat, Object ) );

this.TestCase( SECTION,
"InstanceOf( pat, SalesPerson )",
false,
InstanceOf ( pat, SalesPerson ) );
//test();
},

/** @Test
File Name:         proto_11.js
Section:
Description:        new PrototypeObject

This tests Object Hierarchy and Inheritance, as described in the document
Object Hierarchy and Inheritance in JavaScript, last modified on 12/18/97
15:19:34 on http://devedge.netscape.com/.  Current URL:
http://devedge.netscape.com/docs/manuals/communicator/jsobj/contents.htm

This tests the syntax ObjectName.prototype = new PrototypeObject using the
Employee example in the document referenced above.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_proto_11: function () {
var SECTION = "proto_11.js";
//var VERSION = "JS1_3";
//var TITLE   = "Global Information in Constructors";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

var idCounter = 1;


function Employee ( name, dept ) {
this.name = name || "";
this.dept = dept || "general";
this.id = idCounter++;
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

var pat = new Employee( "Toonces, Pat", "Tech Pubs" )
var terry = new Employee( "O'Sherry Terry", "Marketing" );

var les = new Engineer( "Morris, Les",  new Array("JavaScript"), "indy" );

this.TestCase( SECTION,
"pat.id",
5,
pat.id );

this.TestCase( SECTION,
"terry.id",
6,
terry.id );

this.TestCase( SECTION,
"les.id",
7,
les.id );
//test();
},

/** @Test
File Name:         proto_12.js
Section:
Description:        new PrototypeObject

This tests Object Hierarchy and Inheritance, as described in the document
Object Hierarchy and Inheritance in JavaScript, last modified on 12/18/97
15:19:34 on http://devedge.netscape.com/.  Current URL:
http://devedge.netscape.com/docs/manuals/communicator/jsobj/contents.htm

This tests the syntax ObjectName.prototype = new PrototypeObject using the
Employee example in the document referenced above.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_proto_12: function () {
var SECTION = "proto_12.js";
//var VERSION = "JS1_3";
//var TITLE   = "No Multiple Inheritance";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

var idCounter = 1;
function Employee ( name, dept ) {
this.name = name || "";
this.dept = dept || "general";
this.id = idCounter++;
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

function Hobbyist( hobby ) {
this.hobby = hobby || "yodeling";
}

function Engineer ( name, projs, machine, hobby ) {
this.base1 = WorkerBee;
this.base1( name, "engineering", projs )

this.base2 = Hobbyist;
this.base2( hobby );

this.projects = projs || new Array();
this.machine = machine || "";
}
Engineer.prototype = new WorkerBee();


var les = new Engineer( "Morris, Les",  new Array("JavaScript"), "indy" );

Hobbyist.prototype.equipment = [ "horn", "mountain", "goat" ];

this.TestCase( SECTION,
"les.name",
"Morris, Les",
les.name );

this.TestCase( SECTION,
"les.dept",
"engineering",
les.dept );

Array.prototype.getClass = Object.prototype.toString;

this.TestCase( SECTION,
"les.projects.getClass()",
"[object Array]",
les.projects.getClass() );

this.TestCase( SECTION,
"les.projects[0]",
"JavaScript",
les.projects[0] );

this.TestCase( SECTION,
"les.machine",
"indy",
les.machine );

this.TestCase( SECTION,
"les.hobby",
"yodeling",
les.hobby );

this.TestCase( SECTION,
"les.equpment",
void 0,
les.equipment );
//test();
},

/** @Test
File Name:         proto_3.js
Section:
Description:        new PrototypeObject

This tests Object Hierarchy and Inheritance, as described in the document
Object Hierarchy and Inheritance in JavaScript, last modified on 12/18/97
15:19:34 on http://devedge.netscape.com/.  Current URL:
http://devedge.netscape.com/docs/manuals/communicator/jsobj/contents.htm

This tests the syntax ObjectName.prototype = new PrototypeObject using the
Employee example in the document referenced above.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_proto_3: function () {
var SECTION = "proto_3.js";
//var VERSION = "JS1_3";
//var TITLE   = "Adding properties to an Instance";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

function Employee () {
this.name = "";
this.dept = "general";
}
function Manager () {
this.reports = [];
}
Manager.prototype = new Employee();

function WorkerBee () {
this.projects = new Array();
}

WorkerBee.prototype = new Employee();

function SalesPerson () {
this.dept = "sales";
this.quota = 100;
}
SalesPerson.prototype = new WorkerBee();

function Engineer () {
this.dept = "engineering";
this.machine = "";
}
Engineer.prototype = new WorkerBee();

var jim = new Employee();
var pat = new Employee();

jim.bonus = 300;

this.TestCase( SECTION,
"jim = new Employee(); jim.bonus = 300; jim.bonus",
300,
jim.bonus );


this.TestCase( SECTION,
"pat = new Employee(); pat.bonus",
void 0,
pat.bonus );
//test();
},

/** @Test
File Name:         proto_4.js
Section:
Description:        new PrototypeObject

This tests Object Hierarchy and Inheritance, as described in the document
Object Hierarchy and Inheritance in JavaScript, last modified on 12/18/97
15:19:34 on http://devedge.netscape.com/.  Current URL:
http://devedge.netscape.com/docs/manuals/communicator/jsobj/contents.htm

This tests the syntax ObjectName.prototype = new PrototypeObject using the
Employee example in the document referenced above.

If you add a property to an object in the prototype chain, instances of
objects that derive from that prototype should inherit that property, even
if they were instatiated after the property was added to the prototype object.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_proto_4: function () {
var SECTION = "proto_4.js";
//var VERSION = "JS1_3";
//var TITLE   = "Adding properties to the prototype";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

function Employee () {
this.name = "";
this.dept = "general";
}
function Manager () {
this.reports = [];
}
Manager.prototype = new Employee();

function WorkerBee () {
this.projects = new Array();
}

WorkerBee.prototype = new Employee();

function SalesPerson () {
this.dept = "sales";
this.quota = 100;
}
SalesPerson.prototype = new WorkerBee();

function Engineer () {
this.dept = "engineering";
this.machine = "";
}
Engineer.prototype = new WorkerBee();

var jim = new Employee();
var terry = new Engineer();
var sean = new SalesPerson();
var wally = new Manager();

Employee.prototype.specialty = "none";

var pat = new Employee();
var leslie = new Engineer();
var bubbles = new SalesPerson();
var furry = new Manager();

Engineer.prototype.specialty = "code";

var chris = new Engineer();


this.TestCase( SECTION,
"jim = new Employee(); jim.specialty",
"none",
jim.specialty );

this.TestCase( SECTION,
"terry = new Engineer(); terry.specialty",
"code",
terry.specialty );

this.TestCase( SECTION,
"sean = new SalesPerson(); sean.specialty",
"none",
sean.specialty );

this.TestCase( SECTION,
"wally = new Manager(); wally.specialty",
"none",
wally.specialty );

this.TestCase( SECTION,
"furry = new Manager(); furry.specialty",
"none",
furry.specialty );

this.TestCase( SECTION,
"pat = new Employee(); pat.specialty",
"none",
pat.specialty );

this.TestCase( SECTION,
"leslie = new Engineer(); leslie.specialty",
"code",
leslie.specialty );

this.TestCase( SECTION,
"bubbles = new SalesPerson(); bubbles.specialty",
"none",
bubbles.specialty );


this.TestCase( SECTION,
"chris = new Employee(); chris.specialty",
"code",
chris.specialty );
//test();
},

/** @Test
File Name:         proto_6.js
Section:
Description:        new PrototypeObject

This tests Object Hierarchy and Inheritance, as described in the document
Object Hierarchy and Inheritance in JavaScript, last modified on 12/18/97
15:19:34 on http://devedge.netscape.com/.  Current URL:
http://devedge.netscape.com/docs/manuals/communicator/jsobj/contents.htm

This tests the syntax ObjectName.prototype = new PrototypeObject using the
Employee example in the document referenced above.

This tests the logical OR opererator || syntax in constructors.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_proto_6: function () {
var SECTION = "proto_6.js";
//var VERSION = "JS1_3";
//var TITLE   = "Logical OR || in constructors";

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

var pat = new Engineer( "Toonces, Pat",
["SpiderMonkey", "Rhino"],
"indy" );

var les = new WorkerBee( "Morris, Les",
"Training",
["Hippo"] )

var terry = new Employee( "Boomberi, Terry",
"Marketing" );

// Pat, the Engineer

this.TestCase( SECTION,
"pat.name",
"Toonces, Pat",
pat.name );

this.TestCase( SECTION,
"pat.dept",
"engineering",
pat.dept );

this.TestCase( SECTION,
"pat.projects.length",
2,
pat.projects.length );

this.TestCase( SECTION,
"pat.projects[0]",
"SpiderMonkey",
pat.projects[0] );

this.TestCase( SECTION,
"pat.projects[1]",
"Rhino",
pat.projects[1] );

this.TestCase( SECTION,
"pat.machine",
"indy",
pat.machine );


// Les, the WorkerBee

this.TestCase( SECTION,
"les.name",
"Morris, Les",
les.name );

this.TestCase( SECTION,
"les.dept",
"Training",
les.dept );

this.TestCase( SECTION,
"les.projects.length",
1,
les.projects.length );

this.TestCase( SECTION,
"les.projects[0]",
"Hippo",
les.projects[0] );

// Terry, the Employee
this.TestCase( SECTION,
"terry.name",
"Boomberi, Terry",
terry.name );

this.TestCase( SECTION,
"terry.dept",
"Marketing",
terry.dept );
//test();
},

/** @Test
File Name:         proto_7.js
Section:
Description:        new PrototypeObject

This tests Object Hierarchy and Inheritance, as described in the document
Object Hierarchy and Inheritance in JavaScript, last modified on 12/18/97
15:19:34 on http://devedge.netscape.com/.  Current URL:
http://devedge.netscape.com/docs/manuals/communicator/jsobj/contents.htm

This tests the syntax ObjectName.prototype = new PrototypeObject using the
Employee example in the document referenced above.

This tests

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_proto_7: function () {
var SECTION = "proto_7.js";
//var VERSION = "JS1_3";
//var TITLE   = "Adding properties to the Prototype Object";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

function Employee ( name, dept ) {
this.name = name || "";
this.dept = dept || "general";
}
function WorkerBee ( name, dept, projs ) {
this.base = Employee;
this.base( name, dept)
this.projects = projs || new Array();
}
WorkerBee.prototype = new Employee();

function Engineer ( name, projs, machine ) {
this.base = WorkerBee;
this.base( name, "engineering", projs )
this.machine = machine || "";
}
// Engineer.prototype = new WorkerBee();

var pat = new Engineer( "Toonces, Pat",
["SpiderMonkey", "Rhino"],
"indy" );

Employee.prototype.specialty = "none";


// Pat, the Engineer

this.TestCase( SECTION,
"pat.name",
"Toonces, Pat",
pat.name );

this.TestCase( SECTION,
"pat.dept",
"engineering",
pat.dept );

this.TestCase( SECTION,
"pat.projects.length",
2,
pat.projects.length );

this.TestCase( SECTION,
"pat.projects[0]",
"SpiderMonkey",
pat.projects[0] );

this.TestCase( SECTION,
"pat.projects[1]",
"Rhino",
pat.projects[1] );

this.TestCase( SECTION,
"pat.machine",
"indy",
pat.machine );

this.TestCase( SECTION,
"pat.specialty",
void 0,
pat.specialty );
//test();
},

/** @Test
File Name:         proto_8.js
Section:
Description:        new PrototypeObject

This tests Object Hierarchy and Inheritance, as described in the document
Object Hierarchy and Inheritance in JavaScript, last modified on 12/18/97
15:19:34 on http://devedge.netscape.com/.  Current URL:
http://devedge.netscape.com/docs/manuals/communicator/jsobj/contents.htm

This tests the syntax ObjectName.prototype = new PrototypeObject using the
Employee example in the document referenced above.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_proto_8: function () {
var SECTION = "proto_8.js";
//var VERSION = "JS1_3";
//var TITLE   = "Adding Properties to the Prototype Object";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

function Employee ( name, dept ) {
this.name = name || "";
this.dept = dept || "general";
}
function WorkerBee ( name, dept, projs ) {
this.base = Employee;
this.base( name, dept)
this.projects = projs || new Array();
}
WorkerBee.prototype = new Employee();

function Engineer ( name, projs, machine ) {
this.base = WorkerBee;
this.base( name, "engineering", projs )
this.machine = machine || "";
}
Engineer.prototype = new WorkerBee();

var pat = new Engineer( "Toonces, Pat",
["SpiderMonkey", "Rhino"],
"indy" );

Employee.prototype.specialty = "none";


// Pat, the Engineer

this. TestCase( SECTION,
"pat.name",
"Toonces, Pat",
pat.name );

this.TestCase( SECTION,
"pat.dept",
"engineering",
pat.dept );

this.TestCase( SECTION,
"pat.projects.length",
2,
pat.projects.length );

this.TestCase( SECTION,
"pat.projects[0]",
"SpiderMonkey",
pat.projects[0] );

this.TestCase( SECTION,
"pat.projects[1]",
"Rhino",
pat.projects[1] );

this.TestCase( SECTION,
"pat.machine",
"indy",
pat.machine );

this.TestCase( SECTION,
"pat.specialty",
"none",
pat.specialty );
//test();
},

/** @Test
File Name:         proto_9.js
Section:
Description:        new PrototypeObject

This tests Object Hierarchy and Inheritance, as described in the document
Object Hierarchy and Inheritance in JavaScript, last modified on 12/18/97
15:19:34 on http://devedge.netscape.com/.  Current URL:
http://devedge.netscape.com/docs/manuals/communicator/jsobj/contents.htm

This tests the syntax ObjectName.prototype = new PrototypeObject using the
Employee example in the document referenced above.

This tests

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_proto_9: function () {
var SECTION = "proto_9.js";
//var VERSION = "JS1_3";
//var TITLE   = "Local versus Inherited Values";

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

function Employee ( name, dept ) {
this.name = name || "";
this.dept = dept || "general";
}
function WorkerBee ( name, dept, projs ) {
this.projects = new Array();
}
WorkerBee.prototype = new Employee();

var pat = new WorkerBee()

Employee.prototype.specialty = "none";
Employee.prototype.name = "Unknown";

Array.prototype.getClass = Object.prototype.toString;

// Pat, the WorkerBee

this.TestCase( SECTION,
"pat.name",
"",
pat.name );

this.TestCase( SECTION,
"pat.dept",
"general",
pat.dept );

this.TestCase( SECTION,
"pat.projects.getClass",
"[object Array]",
pat.projects.getClass() );

this.TestCase( SECTION,
"pat.projects.length",
0,
pat.projects.length );

//test();
}

})
.endType();
