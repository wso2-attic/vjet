vjo.ctype("dsf.jslang.feature.tests.Js12OperatorTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({

constructs: function() {
this.base();
},

/**
Filename:     strictEquality.js
Description:  'This tests the operator ==='

Author:       Nick Lerissa
Date:         Fri Feb 13 09:58:28 PST 1998
*/
test_strictEquality: function() {
var SECTION = 'As described in Netscape doc "Whats new in JavaScript 1.2"';
var VERSION = 'no version';
//startTest();
var TITLE = 'operator "==="';

//writeHeaderToLog('Executing script: strictEquality.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION, "('8' === 8)                              ",
false,  ('8' === 8));

this.TestCase( SECTION, "(8 === 8)                                ",
true,   (8 === 8));

this.TestCase( SECTION, "(8 === true)                             ",
false,  (8 === true));

this.TestCase( SECTION, "(new String('') === new String(''))      ",
false,  (new String('') === new String('')));

this.TestCase( SECTION, "(new Boolean(true) === new Boolean(true))",
false,  (new Boolean(true) === new Boolean(true)));

var anObject = { one:1 , two:2 };

this.TestCase( SECTION, "(anObject === anObject)                  ",
true,  (anObject === anObject));

this.TestCase( SECTION, "(anObject === { one:1 , two:2 })         ",
false,  (anObject === { one:1 , two:2 }));

this.TestCase( SECTION, "({ one:1 , two:2 } === anObject)         ",
false,  ({ one:1 , two:2 } === anObject));

this.TestCase( SECTION, "(null === null)                          ",
true,  (null === null));

this.TestCase( SECTION, "(null === 0)                             ",
false,  (null === 0));

this.TestCase( SECTION, "(true === !false)                        ",
true,  (true === !false));

//test();

}

})
.endType();






