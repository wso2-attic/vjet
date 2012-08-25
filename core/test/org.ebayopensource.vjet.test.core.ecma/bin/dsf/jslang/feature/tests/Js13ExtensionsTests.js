vjo.ctype("dsf.jslang.feature.tests.Js13ExtensionsTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({
test_proto_10:function(){


/**
File Name:          proto_10.js
Section:
Description:        Determining Instance Relationships

This tests Object Hierarchy and Inheritance, as described in the document
Object Hierarchy and Inheritance in JavaScript, last modified on 12/18/97
15:19:34 on http://devedge.netscape.com/.  Current URL:
http://devedge.netscape.com/docs/manuals/communicator/jsobj/contents.htm

This tests the syntax ObjectName.prototype = new PrototypeObject using the
Employee example in the document referenced above.

Author:             christine@netscape.com
Date:               12 november 1997
*/

var SECTION = "proto_10";
var VERSION = "JS1_3";
var TITLE   = "Determining Instance Relationships";

// start//test();
// writeHeaderToLog( SECTION + " "+ TITLE);

function InstanceOf( object, constructor ) {
while ( object != null ) {
if ( object == constructor.prototype ) {
return true;
}
object = object.__proto__;
}
return false;
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

var pat = new Engineer();//<Engineer

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
test_proto_2:function(){


/**
File Name:          proto_2.js
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

var SECTION = "proto_2";
var VERSION = "JS1_3";
var TITLE   = "new PrototypeObject";

// start//test();
// writeHeaderToLog( SECTION + " "+ TITLE);

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

WorkerBee.prototype = new Employee;

function SalesPerson () {
this.dept = "sales";
this.quota = 100;
}
SalesPerson.prototype = new WorkerBee;

function Engineer () {
this.dept = "engineering";
this.machine = "";
}
Engineer.prototype = new WorkerBee;


var employee    = new Employee();
var manager     = new Manager();
var workerbee   = new WorkerBee();
var salesperson = new SalesPerson();
var engineer    = new Engineer();

this.TestCase( SECTION,
"employee.__proto__ == Employee.prototype",
true,
employee.__proto__ == Employee.prototype );

this.TestCase( SECTION,
"manager.__proto__ == Manager.prototype",
true,
manager.__proto__ == Manager.prototype );

this.TestCase( SECTION,
"workerbee.__proto__ == WorkerBee.prototype",
true,
workerbee.__proto__ == WorkerBee.prototype );

this.TestCase( SECTION,
"salesperson.__proto__ == SalesPerson.prototype",
true,
salesperson.__proto__ == SalesPerson.prototype );

this.TestCase( SECTION,
"engineer.__proto__ == Engineer.prototype",
true,
engineer.__proto__ == Engineer.prototype );

//test();


},
test_proto_5:function(){


/**
File Name:          proto_5.js
Section:
Description:        Logical OR || in Constructors

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

var SECTION = "proto_5";
var VERSION = "JS1_3";
var TITLE   = "Logical OR || in Constructors";

// start//test();
// writeHeaderToLog( SECTION + " "+ TITLE);

function Employee ( name, dept ) {
this.name = name || "";
this.dept = dept || "general";
}
function Manager () {
this.reports = [];
}
Manager.prototype = new Employee();

function WorkerBee ( projs ) {
this.projects = projs || new Array();
}
WorkerBee.prototype = new Employee();

function SalesPerson () {
this.dept = "sales";
this.quota = 100;
}
SalesPerson.prototype = new WorkerBee();

function Engineer ( machine ) {
this.dept = "engineering";
this.machine = machine || "";
}
Engineer.prototype = new WorkerBee();


var pat = new Engineer( "indy" );

var les = new Engineer();

this.TestCase( SECTION,
"var pat = new Engineer(\"indy\"); pat.name",
"",
pat.name );

this.TestCase( SECTION,
"pat.dept",
"engineering",
pat.dept );

this.TestCase( SECTION,
"pat.projects.length",
0,
pat.projects.length );

this.TestCase( SECTION,
"pat.machine",
"indy",
pat.machine );

this.TestCase( SECTION,
"pat.__proto__ == Engineer.prototype",
true,
pat.__proto__ == Engineer.prototype );

this.TestCase( SECTION,
"var les = new Engineer(); les.name",
"",
les.name );

this.TestCase( SECTION,
"les.dept",
"engineering",
les.dept );

this.TestCase( SECTION,
"les.projects.length",
0,
les.projects.length );

this.TestCase( SECTION,
"les.machine",
"",
les.machine );

this.TestCase( SECTION,
"les.__proto__ == Engineer.prototype",
true,
les.__proto__ == Engineer.prototype );


//test();

},
test_script__001:function(){


/**
File Name:          script-001.js
Section:
Description:        new NativeScript object


js> parseInt(123,"hi")
123
js> parseInt(123, "blah")
123
js> s
js: s is not defined
js> s = new Script

undefined;


js> s = new Script()

undefined;


js> s.getJSClass
js> s.getJSClass = Object.prototype.toString
function toString() {
[native code]
}

js> s.getJSClass()
[object Script]
js> s.compile( "return 3+4" )
js: JavaScript exception: javax.javascript.EvaluatorException: "<Scr
js> s.compile( "3+4" )

3 + 4;


js> typeof s
function
js> s()
Jit failure!
invalid opcode: 1
Jit Pass1 Failure!
javax/javascript/gen/c13 initScript (Ljavax/javascript/Scriptable;)V
An internal JIT error has occurred.  Please report this with .class
jit-bugs@itools.symantec.com

7
js> s.compile("3+4")

3 + 4;


js> s()
Jit failure!
invalid opcode: 1
Jit Pass1 Failure!
javax/javascript/gen/c17 initScript (Ljavax/javascript/Scriptable;)V
An internal JIT error has occurred.  Please report this with .class
jit-bugs@itools.symantec.com

7
js> quit()

C:\src\ns_priv\js\tests\ecma>shell

C:\src\ns_priv\js\tests\ecma>java -classpath c:\cafe\java\JavaScope;
:\src\ns_priv\js\tests javax.javascript.examples.Shell
Symantec Java! JustInTime Compiler Version 210.054 for JDK 1.1.2
Copyright (C) 1996-97 Symantec Corporation

js> s = new Script("3+4")

3 + 4;


js> s()
7
js> s2 = new Script();

undefined;


js> s.compile( "3+4")

3 + 4;


js> s()
Jit failure!
invalid opcode: 1
Jit Pass1 Failure!
javax/javascript/gen/c7 initScript (Ljavax/javascript/Scriptable;)V
An internal JIT error has occurred.  Please report this with .class
jit-bugs@itools.symantec.com

7
js> quit()
Author:             christine@netscape.com
Date:               12 november 1997
*/

var SECTION = "script-001";
var VERSION = "JS1_3";
var TITLE   = "NativeScript";
var Script;
// start//test();
// writeHeaderToLog( SECTION + " "+ TITLE);

if (typeof Script == 'undefined')
{
//   print('Test skipped. Script not defined.');
this.TestCase( SECTION,
"var s = new Script(); typeof s",
"Script not supported, test skipped.",
"Script not supported, test skipped." );
}
else
{
var s = new Script();
s.getJSClass = Object.prototype.toString;

this.TestCase( SECTION,
"var s = new Script(); typeof s",
"function",
typeof s );

this.TestCase( SECTION,
"s.getJSClass()",
"[object Script]",
s.getJSClass() );
}

//test();

}}).endType()
