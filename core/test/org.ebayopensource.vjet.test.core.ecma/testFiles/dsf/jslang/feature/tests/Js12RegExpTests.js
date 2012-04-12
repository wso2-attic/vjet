vjo.ctype("dsf.jslang.feature.tests.Js12RegExpTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({

constructs: function() {
this.base();
},

AddTestCase : function (d, e, a){
this.TestCase("", d, e, a);
},

/**
File Name:          RegExp_dollar_number.js
Description:  'Tests RegExps $1, ..., $9 properties'

Author:       Nick Lerissa
Date:         March 12, 1998
*/
test_RegExp_dollar_number :function(){
var SECTION         =  "RegExp_dollar_number";
var VERSION = 'no version';
var TITLE   = 'RegExp: $1, ..., $9';
//var BUGNUMBER="123802";

//startTest();
//writeHeaderToLog('Executing script: RegExp_dollar_number.js');
//writeHeaderToLog( SECTION + " "+ TITLE);


// 'abcdefghi'.match(/(a(b(c(d(e)f)g)h)i)/); RegExp.$1
'abcdefghi'.match(/(a(b(c(d(e)f)g)h)i)/);
this.TestCase ( SECTION, "'abcdefghi'.match(/(a(b(c(d(e)f)g)h)i)/); RegExp.$1",
'abcdefghi', RegExp.$1);

// 'abcdefghi'.match(/(a(b(c(d(e)f)g)h)i)/); RegExp.$2
this.TestCase ( SECTION, "'abcdefghi'.match(/(a(b(c(d(e)f)g)h)i)/); RegExp.$2",
'bcdefgh', RegExp.$2);

// 'abcdefghi'.match(/(a(b(c(d(e)f)g)h)i)/); RegExp.$3
this.TestCase ( SECTION, "'abcdefghi'.match(/(a(b(c(d(e)f)g)h)i)/); RegExp.$3",
'cdefg', RegExp.$3);

// 'abcdefghi'.match(/(a(b(c(d(e)f)g)h)i)/); RegExp.$4
this.TestCase ( SECTION, "'abcdefghi'.match(/(a(b(c(d(e)f)g)h)i)/); RegExp.$4",
'def', RegExp.$4);

// 'abcdefghi'.match(/(a(b(c(d(e)f)g)h)i)/); RegExp.$5
this.TestCase ( SECTION, "'abcdefghi'.match(/(a(b(c(d(e)f)g)h)i)/); RegExp.$5",
'e', RegExp.$5);

// 'abcdefghi'.match(/(a(b(c(d(e)f)g)h)i)/); RegExp.$6
this.TestCase ( SECTION, "'abcdefghi'.match(/(a(b(c(d(e)f)g)h)i)/); RegExp.$6",
'', RegExp.$6);

var a_to_z = 'abcdefghijklmnopqrstuvwxyz';
var regexp1 = /(a)b(c)d(e)f(g)h(i)j(k)l(m)n(o)p(q)r(s)t(u)v(w)x(y)z/
// 'abcdefghijklmnopqrstuvwxyz'.match(/(a)b(c)d(e)f(g)h(i)j(k)l(m)n(o)p(q)r(s)t(u)v(w)x(y)z/); RegExp.$1
a_to_z.match(regexp1);

this.TestCase ( SECTION, "'" + a_to_z + "'.match((a)b(c)....(y)z); RegExp.$1",
'a', RegExp.$1);
this.TestCase ( SECTION, "'" + a_to_z + "'.match((a)b(c)....(y)z); RegExp.$2",
'c', RegExp.$2);
this.TestCase ( SECTION, "'" + a_to_z + "'.match((a)b(c)....(y)z); RegExp.$3",
'e', RegExp.$3);
this.TestCase ( SECTION, "'" + a_to_z + "'.match((a)b(c)....(y)z); RegExp.$4",
'g', RegExp.$4);
this.TestCase ( SECTION, "'" + a_to_z + "'.match((a)b(c)....(y)z); RegExp.$5",
'i', RegExp.$5);
this.TestCase ( SECTION, "'" + a_to_z + "'.match((a)b(c)....(y)z); RegExp.$6",
'k', RegExp.$6);
this.TestCase ( SECTION, "'" + a_to_z + "'.match((a)b(c)....(y)z); RegExp.$7",
'm', RegExp.$7);
this.TestCase ( SECTION, "'" + a_to_z + "'.match((a)b(c)....(y)z); RegExp.$8",
'o', RegExp.$8);
this.TestCase ( SECTION, "'" + a_to_z + "'.match((a)b(c)....(y)z); RegExp.$9",
'q', RegExp.$9);
/*
this.TestCase ( SECTION, "'" + a_to_z + "'.match((a)b(c)....(y)z); RegExp.$10",
's', RegExp.$10);
*/
//test();

},

/**
File Name:          RegExp_input.js
Description:  'Tests RegExps input property'

Author:       Nick Lerissa
Date:         March 13, 1998
*/
test_RegExp_input :function(){
var SECTION         =  "RegExp_input";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: input';

//writeHeaderToLog('Executing script: RegExp_input.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

RegExp.input = "abcd12357efg";

// RegExp.input = "abcd12357efg"; RegExp.input
RegExp.input = "abcd12357efg";
this.TestCase ( SECTION, "RegExp.input = 'abcd12357efg'; RegExp.input",
"abcd12357efg", RegExp.input);

// RegExp.input = "abcd12357efg"; /\d+/.exec('2345')
RegExp.input = "abcd12357efg";
this.TestCase ( SECTION, "RegExp.input = 'abcd12357efg'; /\\d+/.exec('2345')",
String(["2345"]), String(/\d+/.exec('2345')));

// RegExp.input = "abcd12357efg"; /\d+/.exec()
RegExp.input = "abcd12357efg";
this.TestCase ( SECTION, "RegExp.input = 'abcd12357efg'; /\\d+/.exec()",
String(["12357"]), String(/\d+/.exec()));

// RegExp.input = "abcd12357efg"; /[h-z]+/.exec()
RegExp.input = "abcd12357efg";
this.TestCase ( SECTION, "RegExp.input = 'abcd12357efg'; /[h-z]+/.exec()",
null, /[h-z]+/.exec());

// RegExp.input = "abcd12357efg"; /\d+/.test('2345')
RegExp.input = "abcd12357efg";
this.TestCase ( SECTION, "RegExp.input = 'abcd12357efg'; /\\d+/.test('2345')",
true, /\d+/.test('2345'));

// RegExp.input = "abcd12357efg"; /\d+/.test()
RegExp.input = "abcd12357efg";
this.TestCase ( SECTION, "RegExp.input = 'abcd12357efg'; /\\d+/.test()",
true, /\d+/.test());

// RegExp.input = "abcd12357efg"; (new RegExp('d+')).test()
RegExp.input = "abcd12357efg";
this.TestCase ( SECTION, "RegExp.input = 'abcd12357efg'; (new RegExp('d+')).test()",
true, (new RegExp('d+')).test());

// RegExp.input = "abcd12357efg"; /[h-z]+/.test()
RegExp.input = "abcd12357efg";
this.TestCase ( SECTION, "RegExp.input = 'abcd12357efg'; /[h-z]+/.test()",
false, /[h-z]+/.test());

// RegExp.input = "abcd12357efg"; (new RegExp('[h-z]+')).test()
RegExp.input = "abcd12357efg";
this.TestCase ( SECTION, "RegExp.input = 'abcd12357efg'; (new RegExp('[h-z]+')).test()",
false, (new RegExp('[h-z]+')).test());

//test();

},

/**
File Name:          RegExp_input_as_array.js
Description:  'Tests RegExps $_ property  (same tests as RegExp_input.js but using $_)'

Author:       Nick Lerissa
Date:         March 13, 1998
*/
test_RegExp_input_as_array :function(){
var SECTION         =  "RegExp_input_as_array";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: input';

//writeHeaderToLog('Executing script: RegExp_input.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

RegExp['$_'] = "abcd12357efg";

// RegExp['$_'] = "abcd12357efg"; RegExp['$_']
RegExp['$_'] = "abcd12357efg";
this.TestCase ( SECTION, "RegExp['$_'] = 'abcd12357efg'; RegExp['$_']",
"abcd12357efg", RegExp['$_']);

// RegExp['$_'] = "abcd12357efg"; /\d+/.exec('2345')
RegExp['$_'] = "abcd12357efg";
this.TestCase ( SECTION, "RegExp['$_'] = 'abcd12357efg'; /\\d+/.exec('2345')",
String(["2345"]), String(/\d+/.exec('2345')));

// RegExp['$_'] = "abcd12357efg"; /\d+/.exec()
RegExp['$_'] = "abcd12357efg";
this.TestCase ( SECTION, "RegExp['$_'] = 'abcd12357efg'; /\\d+/.exec()",
String(["12357"]), String(/\d+/.exec()));

// RegExp['$_'] = "abcd12357efg"; /[h-z]+/.exec()
RegExp['$_'] = "abcd12357efg";
this.TestCase ( SECTION, "RegExp['$_'] = 'abcd12357efg'; /[h-z]+/.exec()",
null, /[h-z]+/.exec());

// RegExp['$_'] = "abcd12357efg"; /\d+/.test('2345')
RegExp['$_'] = "abcd12357efg";
this.TestCase ( SECTION, "RegExp['$_'] = 'abcd12357efg'; /\\d+/.test('2345')",
true, /\d+/.test('2345'));

// RegExp['$_'] = "abcd12357efg"; /\d+/.test()
RegExp['$_'] = "abcd12357efg";
this.TestCase ( SECTION, "RegExp['$_'] = 'abcd12357efg'; /\\d+/.test()",
true, /\d+/.test());

// RegExp['$_'] = "abcd12357efg"; /[h-z]+/.test()
RegExp['$_'] = "abcd12357efg";
this.TestCase ( SECTION, "RegExp['$_'] = 'abcd12357efg'; /[h-z]+/.test()",
false, /[h-z]+/.test());

// RegExp['$_'] = "abcd12357efg"; (new RegExp('\d+')).test()
RegExp['$_'] = "abcd12357efg";
this.TestCase ( SECTION, "RegExp['$_'] = 'abcd12357efg'; (new RegExp('\d+')).test()",
true, (new RegExp('\d+')).test());

// RegExp['$_'] = "abcd12357efg"; (new RegExp('[h-z]+')).test()
RegExp['$_'] = "abcd12357efg";
this.TestCase ( SECTION, "RegExp['$_'] = 'abcd12357efg'; (new RegExp('[h-z]+')).test()",
false, (new RegExp('[h-z]+')).test());

//test();

},

/**
File Name:          RegExp_lastMatch.js
Description:  'Tests RegExps lastMatch property'

Author:       Nick Lerissa
Date:         March 12, 1998
*/
test_RegExp_lastMatch :function(){
var SECTION         =  "RegExp_lastMatch";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: lastMatch';

//writeHeaderToLog('Executing script: RegExp_lastMatch.js');
//writeHeaderToLog( SECTION + " "+ TITLE);


// 'foo'.match(/foo/); RegExp.lastMatch
'foo'.match(/foo/);
this.TestCase ( SECTION, "'foo'.match(/foo/); RegExp.lastMatch",
'foo', RegExp.lastMatch);

// 'foo'.match(new RegExp('foo')); RegExp.lastMatch
'foo'.match(new RegExp('foo'));
this.TestCase ( SECTION, "'foo'.match(new RegExp('foo')); RegExp.lastMatch",
'foo', RegExp.lastMatch);

// 'xxx'.match(/bar/); RegExp.lastMatch
'xxx'.match(/bar/);
this.TestCase ( SECTION, "'xxx'.match(/bar/); RegExp.lastMatch",
'foo', RegExp.lastMatch);

// 'xxx'.match(/$/); RegExp.lastMatch
'xxx'.match(/$/);
this.TestCase ( SECTION, "'xxx'.match(/$/); RegExp.lastMatch",
'', RegExp.lastMatch);

// 'abcdefg'.match(/^..(cd)[a-z]+/); RegExp.lastMatch
'abcdefg'.match(/^..(cd)[a-z]+/);
this.TestCase ( SECTION, "'abcdefg'.match(/^..(cd)[a-z]+/); RegExp.lastMatch",
'abcdefg', RegExp.lastMatch);

// 'abcdefgabcdefg'.match(/(a(b(c(d)e)f)g)\1/); RegExp.lastMatch
'abcdefgabcdefg'.match(/(a(b(c(d)e)f)g)\1/);
this.TestCase ( SECTION, "'abcdefgabcdefg'.match(/(a(b(c(d)e)f)g)\\1/); RegExp.lastMatch",
'abcdefgabcdefg', RegExp.lastMatch);

//test();

},

/**
File Name:          RegExp_lastMatch_as_array.js
Description:  'Tests RegExps $& property (same tests as RegExp_lastMatch.js but using $&)'

Author:       Nick Lerissa
Date:         March 13, 1998
*/
test_RegExp_lastMatch_as_array :function(){
var SECTION         =  "RegExp_lastMatch_as_array";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: $&';

//writeHeaderToLog('Executing script: RegExp_lastMatch_as_array.js');
//writeHeaderToLog( SECTION + " "+ TITLE);


// 'foo'.match(/foo/); RegExp['$&']
'foo'.match(/foo/);
this.TestCase ( SECTION, "'foo'.match(/foo/); RegExp['$&']",
'foo', RegExp['$&']);

// 'foo'.match(new RegExp('foo')); RegExp['$&']
'foo'.match(new RegExp('foo'));
this.TestCase ( SECTION, "'foo'.match(new RegExp('foo')); RegExp['$&']",
'foo', RegExp['$&']);

// 'xxx'.match(/bar/); RegExp['$&']
'xxx'.match(/bar/);
this.TestCase ( SECTION, "'xxx'.match(/bar/); RegExp['$&']",
'foo', RegExp['$&']);

// 'xxx'.match(/$/); RegExp['$&']
'xxx'.match(/$/);
this.TestCase ( SECTION, "'xxx'.match(/$/); RegExp['$&']",
'', RegExp['$&']);

// 'abcdefg'.match(/^..(cd)[a-z]+/); RegExp['$&']
'abcdefg'.match(/^..(cd)[a-z]+/);
this.TestCase ( SECTION, "'abcdefg'.match(/^..(cd)[a-z]+/); RegExp['$&']",
'abcdefg', RegExp['$&']);

// 'abcdefgabcdefg'.match(/(a(b(c(d)e)f)g)\1/); RegExp['$&']
'abcdefgabcdefg'.match(/(a(b(c(d)e)f)g)\1/);
this.TestCase ( SECTION, "'abcdefgabcdefg'.match(/(a(b(c(d)e)f)g)\\1/); RegExp['$&']",
'abcdefgabcdefg', RegExp['$&']);

//test();

},

/**
File Name:          RegExp_lastParen.js
Description:  'Tests RegExps lastParen property'

Author:       Nick Lerissa
Date:         March 12, 1998
*/
test_RegExp_lastParen :function(){
var SECTION         =  "RegExp_lastParen";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: lastParen';

//writeHeaderToLog('Executing script: RegExp_lastParen.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

// 'abcd'.match(/(abc)d/); RegExp.lastParen
'abcd'.match(/(abc)d/);
this.TestCase ( SECTION, "'abcd'.match(/(abc)d/); RegExp.lastParen",
'abc', RegExp.lastParen);

// 'abcd'.match(new RegExp('(abc)d')); RegExp.lastParen
'abcd'.match(new RegExp('(abc)d'));
this.TestCase ( SECTION, "'abcd'.match(new RegExp('(abc)d')); RegExp.lastParen",
'abc', RegExp.lastParen);

// 'abcd'.match(/(bcd)e/); RegExp.lastParen
'abcd'.match(/(bcd)e/);
this.TestCase ( SECTION, "'abcd'.match(/(bcd)e/); RegExp.lastParen",
'abc', RegExp.lastParen);

// 'abcdefg'.match(/(a(b(c(d)e)f)g)/); RegExp.lastParen
'abcdefg'.match(/(a(b(c(d)e)f)g)/);
this.TestCase ( SECTION, "'abcdefg'.match(/(a(b(c(d)e)f)g)/); RegExp.lastParen",
'd', RegExp.lastParen);

// 'abcdefg'.match(/(a(b)c)(d(e)f)/); RegExp.lastParen
'abcdefg'.match(/(a(b)c)(d(e)f)/);
this.TestCase ( SECTION, "'abcdefg'.match(/(a(b)c)(d(e)f)/); RegExp.lastParen",
'e', RegExp.lastParen);

// 'abcdefg'.match(/(^)abc/); RegExp.lastParen
'abcdefg'.match(/(^)abc/);
this.TestCase ( SECTION, "'abcdefg'.match(/(^)abc/); RegExp.lastParen",
'', RegExp.lastParen);

// 'abcdefg'.match(/(^a)bc/); RegExp.lastParen
'abcdefg'.match(/(^a)bc/);
this.TestCase ( SECTION, "'abcdefg'.match(/(^a)bc/); RegExp.lastParen",
'a', RegExp.lastParen);

// 'abcdefg'.match(new RegExp('(^a)bc')); RegExp.lastParen
'abcdefg'.match(new RegExp('(^a)bc'));
this.TestCase ( SECTION, "'abcdefg'.match(new RegExp('(^a)bc')); RegExp.lastParen",
'a', RegExp.lastParen);

// 'abcdefg'.match(/bc/); RegExp.lastParen
'abcdefg'.match(/bc/);
this.TestCase ( SECTION, "'abcdefg'.match(/bc/); RegExp.lastParen",
'', RegExp.lastParen);

//test();

},

/**
File Name:          RegExp_lastParen_as_array.js
Description:  'Tests RegExps $+ property (same tests as RegExp_lastParen.js but using $+)'

Author:       Nick Lerissa
Date:         March 13, 1998

*/
test_RegExp_lastParen_as_array :function(){
var SECTION         =  "RegExp_lastParen_as_array";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: $+';

//writeHeaderToLog('Executing script: RegExp_lastParen_as_array.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

// 'abcd'.match(/(abc)d/); RegExp['$+']
'abcd'.match(/(abc)d/);
this.TestCase ( SECTION, "'abcd'.match(/(abc)d/); RegExp['$+']",
'abc', RegExp['$+']);

// 'abcd'.match(/(bcd)e/); RegExp['$+']
'abcd'.match(/(bcd)e/);
this.TestCase ( SECTION, "'abcd'.match(/(bcd)e/); RegExp['$+']",
'abc', RegExp['$+']);

// 'abcdefg'.match(/(a(b(c(d)e)f)g)/); RegExp['$+']
'abcdefg'.match(/(a(b(c(d)e)f)g)/);
this.TestCase ( SECTION, "'abcdefg'.match(/(a(b(c(d)e)f)g)/); RegExp['$+']",
'd', RegExp['$+']);

// 'abcdefg'.match(new RegExp('(a(b(c(d)e)f)g)')); RegExp['$+']
'abcdefg'.match(new RegExp('(a(b(c(d)e)f)g)'));
this.TestCase ( SECTION, "'abcdefg'.match(new RegExp('(a(b(c(d)e)f)g)')); RegExp['$+']",
'd', RegExp['$+']);

// 'abcdefg'.match(/(a(b)c)(d(e)f)/); RegExp['$+']
'abcdefg'.match(/(a(b)c)(d(e)f)/);
this.TestCase ( SECTION, "'abcdefg'.match(/(a(b)c)(d(e)f)/); RegExp['$+']",
'e', RegExp['$+']);

// 'abcdefg'.match(/(^)abc/); RegExp['$+']
'abcdefg'.match(/(^)abc/);
this.TestCase ( SECTION, "'abcdefg'.match(/(^)abc/); RegExp['$+']",
'', RegExp['$+']);

// 'abcdefg'.match(/(^a)bc/); RegExp['$+']
'abcdefg'.match(/(^a)bc/);
this.TestCase ( SECTION, "'abcdefg'.match(/(^a)bc/); RegExp['$+']",
'a', RegExp['$+']);

// 'abcdefg'.match(new RegExp('(^a)bc')); RegExp['$+']
'abcdefg'.match(new RegExp('(^a)bc'));
this.TestCase ( SECTION, "'abcdefg'.match(new RegExp('(^a)bc')); RegExp['$+']",
'a', RegExp['$+']);

// 'abcdefg'.match(/bc/); RegExp['$+']
'abcdefg'.match(/bc/);
this.TestCase ( SECTION, "'abcdefg'.match(/bc/); RegExp['$+']",
'', RegExp['$+']);

//test();

},

/**
File Name:          RegExp_leftContext.js
Description:  'Tests RegExps leftContext property'

Author:       Nick Lerissa
Date:         March 12, 1998
*/
test_RegExp_leftContext :function(){
var SECTION         =  "RegExp_leftContext";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: leftContext';

//writeHeaderToLog('Executing script: RegExp_leftContext.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

// 'abc123xyz'.match(/123/); RegExp.leftContext
'abc123xyz'.match(/123/);
this.TestCase ( SECTION, "'abc123xyz'.match(/123/); RegExp.leftContext",
'abc', RegExp.leftContext);

// 'abc123xyz'.match(/456/); RegExp.leftContext
'abc123xyz'.match(/456/);
this.TestCase ( SECTION, "'abc123xyz'.match(/456/); RegExp.leftContext",
'abc', RegExp.leftContext);

// 'abc123xyz'.match(/abc123xyz/); RegExp.leftContext
'abc123xyz'.match(/abc123xyz/);
this.TestCase ( SECTION, "'abc123xyz'.match(/abc123xyz/); RegExp.leftContext",
'', RegExp.leftContext);

// 'xxxx'.match(/$/); RegExp.leftContext
'xxxx'.match(/$/);
this.TestCase ( SECTION, "'xxxx'.match(/$/); RegExp.leftContext",
'xxxx', RegExp.leftContext);

// 'test'.match(/^/); RegExp.leftContext
'test'.match(/^/);
this.TestCase ( SECTION, "'test'.match(/^/); RegExp.leftContext",
'', RegExp.leftContext);

// 'xxxx'.match(new RegExp('$')); RegExp.leftContext
'xxxx'.match(new RegExp('$'));
this.TestCase ( SECTION, "'xxxx'.match(new RegExp('$')); RegExp.leftContext",
'xxxx', RegExp.leftContext);

// 'test'.match(new RegExp('^')); RegExp.leftContext
'test'.match(new RegExp('^'));
this.TestCase ( SECTION, "'test'.match(new RegExp('^')); RegExp.leftContext",
'', RegExp.leftContext);

//test();

},

/**
File Name:          RegExp_leftContext_as_array.js
Description:  'Tests RegExps leftContext property (same tests as RegExp_leftContext.js but using $`)'

Author:       Nick Lerissa
Date:         March 12, 1998

*/
test_RegExp_leftContext_as_array :function(){
var SECTION         =  "RegExp_leftContext_as_array";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: $`';

//writeHeaderToLog('Executing script: RegExp_leftContext_as_array.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

// 'abc123xyz'.match(/123/); RegExp['$`']
'abc123xyz'.match(/123/);
this.TestCase ( SECTION, "'abc123xyz'.match(/123/); RegExp['$`']",
'abc', RegExp['$`']);

// 'abc123xyz'.match(/456/); RegExp['$`']
'abc123xyz'.match(/456/);
this.TestCase ( SECTION, "'abc123xyz'.match(/456/); RegExp['$`']",
'abc', RegExp['$`']);

// 'abc123xyz'.match(/abc123xyz/); RegExp['$`']
'abc123xyz'.match(/abc123xyz/);
this.TestCase ( SECTION, "'abc123xyz'.match(/abc123xyz/); RegExp['$`']",
'', RegExp['$`']);

// 'xxxx'.match(/$/); RegExp['$`']
'xxxx'.match(/$/);
this.TestCase ( SECTION, "'xxxx'.match(/$/); RegExp['$`']",
'xxxx', RegExp['$`']);

// 'test'.match(/^/); RegExp['$`']
'test'.match(/^/);
this.TestCase ( SECTION, "'test'.match(/^/); RegExp['$`']",
'', RegExp['$`']);

// 'xxxx'.match(new RegExp('$')); RegExp['$`']
'xxxx'.match(new RegExp('$'));
this.TestCase ( SECTION, "'xxxx'.match(new RegExp('$')); RegExp['$`']",
'xxxx', RegExp['$`']);

// 'test'.match(new RegExp('^')); RegExp['$`']
'test'.match(new RegExp('^'));
this.TestCase ( SECTION, "'test'.match(new RegExp('^')); RegExp['$`']",
'', RegExp['$`']);

//test();

},

/**
File Name:          RegExp_multiline.js
Description:  'Tests RegExps multiline property'

Author:       Nick Lerissa
Date:         March 12, 1998

*/
test_RegExp_multiline :function(){
var SECTION         =  "RegExp_multiline";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: multiline';

//writeHeaderToLog('Executing script: RegExp_multiline.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

// First we do a series of tests with RegExp.multiline set to false (default value)
// Following this we do the same tests with RegExp.multiline set true(**).
// RegExp.multiline
this.TestCase ( SECTION, "RegExp.multiline",
false, RegExp.multiline);

// (multiline == false) '123\n456'.match(/^4../)
this.TestCase ( SECTION, "(multiline == false) '123\\n456'.match(/^4../)",
null, '123\n456'.match(/^4../));

// (multiline == false) 'a11\na22\na23\na24'.match(/^a../g)
this.TestCase ( SECTION, "(multiline == false) 'a11\\na22\\na23\\na24'.match(/^a../g)",
String(['a11']), String('a11\na22\na23\na24'.match(/^a../g)));

// (multiline == false) 'a11\na22'.match(/^.+^./)
this.TestCase ( SECTION, "(multiline == false) 'a11\na22'.match(/^.+^./)",
null, 'a11\na22'.match(/^.+^./));

// (multiline == false) '123\n456'.match(/.3$/)
this.TestCase ( SECTION, "(multiline == false) '123\\n456'.match(/.3$/)",
null, '123\n456'.match(/.3$/));

// (multiline == false) 'a11\na22\na23\na24'.match(/a..$/g)
this.TestCase ( SECTION, "(multiline == false) 'a11\\na22\\na23\\na24'.match(/a..$/g)",
String(['a24']), String('a11\na22\na23\na24'.match(/a..$/g)));

// (multiline == false) 'abc\ndef'.match(/c$...$/)
this.TestCase ( SECTION, "(multiline == false) 'abc\ndef'.match(/c$...$/)",
null, 'abc\ndef'.match(/c$...$/));

// (multiline == false) 'a11\na22\na23\na24'.match(new RegExp('a..$','g'))
this.TestCase ( SECTION, "(multiline == false) 'a11\\na22\\na23\\na24'.match(new RegExp('a..$','g'))",
String(['a24']), String('a11\na22\na23\na24'.match(new RegExp('a..$','g'))));

// (multiline == false) 'abc\ndef'.match(new RegExp('c$...$'))
this.TestCase ( SECTION, "(multiline == false) 'abc\ndef'.match(new RegExp('c$...$'))",
null, 'abc\ndef'.match(new RegExp('c$...$')));

// **Now we do the tests with RegExp.multiline set to true
// RegExp.multiline = true; RegExp.multiline
RegExp.multiline = true;
this.TestCase ( SECTION, "RegExp.multiline = true; RegExp.multiline",
true, RegExp.multiline);

// (multiline == true) '123\n456'.match(/^4../)
this.TestCase ( SECTION, "(multiline == true) '123\\n456'.match(/^4../)",
String(['456']), String('123\n456'.match(/^4../)));

// (multiline == true) 'a11\na22\na23\na24'.match(/^a../g)
this.TestCase ( SECTION, "(multiline == true) 'a11\\na22\\na23\\na24'.match(/^a../g)",
String(['a11','a22','a23','a24']), String('a11\na22\na23\na24'.match(/^a../g)));

// (multiline == true) 'a11\na22'.match(/^.+^./)
//this.TestCase ( SECTION, "(multiline == true) 'a11\na22'.match(/^.+^./)",
//                                    String(['a11\na']), String('a11\na22'.match(/^.+^./)));

// (multiline == true) '123\n456'.match(/.3$/)
this.TestCase ( SECTION, "(multiline == true) '123\\n456'.match(/.3$/)",
String(['23']), String('123\n456'.match(/.3$/)));

// (multiline == true) 'a11\na22\na23\na24'.match(/a..$/g)
this.TestCase ( SECTION, "(multiline == true) 'a11\\na22\\na23\\na24'.match(/a..$/g)",
String(['a11','a22','a23','a24']), String('a11\na22\na23\na24'.match(/a..$/g)));

// (multiline == true) 'a11\na22\na23\na24'.match(new RegExp('a..$','g'))
this.TestCase ( SECTION, "(multiline == true) 'a11\\na22\\na23\\na24'.match(new RegExp('a..$','g'))",
String(['a11','a22','a23','a24']), String('a11\na22\na23\na24'.match(new RegExp('a..$','g'))));

// (multiline == true) 'abc\ndef'.match(/c$....$/)
//this.TestCase ( SECTION, "(multiline == true) 'abc\ndef'.match(/c$.+$/)",
//                                    'c\ndef', String('abc\ndef'.match(/c$.+$/)));

RegExp.multiline = false;

//test();

},

/**
File Name:          RegExp_multiline_as_array.js
Description:  'Tests RegExps $* property  (same tests as RegExp_multiline.js but using $*)'

Author:       Nick Lerissa
Date:         March 13, 1998

*/
test_RegExp_multiline_as_array :function(){
var SECTION         =  "RegExp_multiline_as_array";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: $*';

//writeHeaderToLog('Executing script: RegExp_multiline_as_array.js');
//writeHeaderToLog( SECTION + " "+ TITLE);


// First we do a series of tests with RegExp['$*'] set to false (default value)
// Following this we do the same tests with RegExp['$*'] set true(**).
// RegExp['$*']
this.TestCase ( SECTION, "RegExp['$*']",
false, RegExp['$*']);

// (['$*'] == false) '123\n456'.match(/^4../)
this.TestCase ( SECTION, "(['$*'] == false) '123\\n456'.match(/^4../)",
null, '123\n456'.match(/^4../));

// (['$*'] == false) 'a11\na22\na23\na24'.match(/^a../g)
this.TestCase ( SECTION, "(['$*'] == false) 'a11\\na22\\na23\\na24'.match(/^a../g)",
String(['a11']), String('a11\na22\na23\na24'.match(/^a../g)));

// (['$*'] == false) 'a11\na22'.match(/^.+^./)
this.TestCase ( SECTION, "(['$*'] == false) 'a11\na22'.match(/^.+^./)",
null, 'a11\na22'.match(/^.+^./));

// (['$*'] == false) '123\n456'.match(/.3$/)
this.TestCase ( SECTION, "(['$*'] == false) '123\\n456'.match(/.3$/)",
null, '123\n456'.match(/.3$/));

// (['$*'] == false) 'a11\na22\na23\na24'.match(/a..$/g)
this.TestCase ( SECTION, "(['$*'] == false) 'a11\\na22\\na23\\na24'.match(/a..$/g)",
String(['a24']), String('a11\na22\na23\na24'.match(/a..$/g)));

// (['$*'] == false) 'abc\ndef'.match(/c$...$/)
this.TestCase ( SECTION, "(['$*'] == false) 'abc\ndef'.match(/c$...$/)",
null, 'abc\ndef'.match(/c$...$/));

// (['$*'] == false) 'a11\na22\na23\na24'.match(new RegExp('a..$','g'))
this.TestCase ( SECTION, "(['$*'] == false) 'a11\\na22\\na23\\na24'.match(new RegExp('a..$','g'))",
String(['a24']), String('a11\na22\na23\na24'.match(new RegExp('a..$','g'))));

// (['$*'] == false) 'abc\ndef'.match(new RegExp('c$...$'))
this.TestCase ( SECTION, "(['$*'] == false) 'abc\ndef'.match(new RegExp('c$...$'))",
null, 'abc\ndef'.match(new RegExp('c$...$')));

// **Now we do the tests with RegExp['$*'] set to true
// RegExp['$*'] = true; RegExp['$*']
RegExp['$*'] = true;
this.TestCase ( SECTION, "RegExp['$*'] = true; RegExp['$*']",
true, RegExp['$*']);

// (['$*'] == true) '123\n456'.match(/^4../)
this.TestCase ( SECTION, "(['$*'] == true) '123\\n456'.match(/^4../)",
String(['456']), String('123\n456'.match(/^4../)));

// (['$*'] == true) 'a11\na22\na23\na24'.match(/^a../g)
this.TestCase ( SECTION, "(['$*'] == true) 'a11\\na22\\na23\\na24'.match(/^a../g)",
String(['a11','a22','a23','a24']), String('a11\na22\na23\na24'.match(/^a../g)));

// (['$*'] == true) 'a11\na22'.match(/^.+^./)
//this.TestCase ( SECTION, "(['$*'] == true) 'a11\na22'.match(/^.+^./)",
//                                    String(['a11\na']), String('a11\na22'.match(/^.+^./)));

// (['$*'] == true) '123\n456'.match(/.3$/)
this.TestCase ( SECTION, "(['$*'] == true) '123\\n456'.match(/.3$/)",
String(['23']), String('123\n456'.match(/.3$/)));

// (['$*'] == true) 'a11\na22\na23\na24'.match(/a..$/g)
this.TestCase ( SECTION, "(['$*'] == true) 'a11\\na22\\na23\\na24'.match(/a..$/g)",
String(['a11','a22','a23','a24']), String('a11\na22\na23\na24'.match(/a..$/g)));

// (['$*'] == true) 'a11\na22\na23\na24'.match(new RegExp('a..$','g'))
this.TestCase ( SECTION, "(['$*'] == true) 'a11\\na22\\na23\\na24'.match(new RegExp('a..$','g'))",
String(['a11','a22','a23','a24']), String('a11\na22\na23\na24'.match(new RegExp('a..$','g'))));

// (['$*'] == true) 'abc\ndef'.match(/c$....$/)
//this.TestCase ( SECTION, "(['$*'] == true) 'abc\ndef'.match(/c$.+$/)",
//                                    'c\ndef', String('abc\ndef'.match(/c$.+$/)));

RegExp['$*'] = false;

//test();

},

/**
File Name:          RegExp_object.js
Description:  'Tests regular expressions creating RexExp Objects'

Author:       Nick Lerissa
Date:         March 10, 1998

*/
test_RegExp_object :function(){
var SECTION         =  "RegExp_object";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: object';

//writeHeaderToLog('Executing script: RegExp_object.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

var SSN_pattern = new RegExp("\\d{3}-\\d{2}-\\d{4}");

// testing SSN pattern
this.TestCase ( SECTION, "'Test SSN is 123-34-4567'.match(SSN_pattern))",
String(["123-34-4567"]), String('Test SSN is 123-34-4567'.match(SSN_pattern)));

// testing SSN pattern
this.TestCase ( SECTION, "'Test SSN is 123-34-4567'.match(SSN_pattern))",
String(["123-34-4567"]), String('Test SSN is 123-34-4567'.match(SSN_pattern)));

var PHONE_pattern = new RegExp("\\(?(\\d{3})\\)?-?(\\d{3})-(\\d{4})");
// testing PHONE pattern
this.TestCase ( SECTION, "'Our phone number is (408)345-2345.'.match(PHONE_pattern))",
String(["(408)345-2345","408","345","2345"]), String('Our phone number is (408)345-2345.'.match(PHONE_pattern)));

// testing PHONE pattern
this.TestCase ( SECTION, "'The phone number is 408-345-2345!'.match(PHONE_pattern))",
String(["408-345-2345","408","345","2345"]), String('The phone number is 408-345-2345!'.match(PHONE_pattern)));

// testing PHONE pattern
this.TestCase ( SECTION, "String(PHONE_pattern.toString())",
"/\\(?(\\d{3})\\)?-?(\\d{3})-(\\d{4})/", String(PHONE_pattern.toString()));

// testing conversion to String
this.TestCase ( SECTION, "PHONE_pattern + ' is the string'",
"/\\(?(\\d{3})\\)?-?(\\d{3})-(\\d{4})/ is the string",PHONE_pattern + ' is the string');

// testing conversion to int
this.TestCase ( SECTION, "SSN_pattern - 8",
NaN,SSN_pattern - 8);

var testPattern = new RegExp("(\\d+)45(\\d+)90");

//test();

},

/**
File Name:          RegExp_rightContext.js
Description:  'Tests RegExps rightContext property'

Author:       Nick Lerissa
Date:         March 12, 1998

*/
test_RegExp_rightContext :function(){
var SECTION         =  "RegExp_rightContext";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: rightContext';

//writeHeaderToLog('Executing script: RegExp_rightContext.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

// 'abc123xyz'.match(/123/); RegExp.rightContext
'abc123xyz'.match(/123/);
this.TestCase ( SECTION, "'abc123xyz'.match(/123/); RegExp.rightContext",
'xyz', RegExp.rightContext);

// 'abc123xyz'.match(/456/); RegExp.rightContext
'abc123xyz'.match(/456/);
this.TestCase ( SECTION, "'abc123xyz'.match(/456/); RegExp.rightContext",
'xyz', RegExp.rightContext);

// 'abc123xyz'.match(/abc123xyz/); RegExp.rightContext
'abc123xyz'.match(/abc123xyz/);
this.TestCase ( SECTION, "'abc123xyz'.match(/abc123xyz/); RegExp.rightContext",
'', RegExp.rightContext);

// 'xxxx'.match(/$/); RegExp.rightContext
'xxxx'.match(/$/);
this.TestCase ( SECTION, "'xxxx'.match(/$/); RegExp.rightContext",
'', RegExp.rightContext);

// 'test'.match(/^/); RegExp.rightContext
'test'.match(/^/);
this.TestCase ( SECTION, "'test'.match(/^/); RegExp.rightContext",
'test', RegExp.rightContext);

// 'xxxx'.match(new RegExp('$')); RegExp.rightContext
'xxxx'.match(new RegExp('$'));
this.TestCase ( SECTION, "'xxxx'.match(new RegExp('$')); RegExp.rightContext",
'', RegExp.rightContext);

// 'test'.match(new RegExp('^')); RegExp.rightContext
'test'.match(new RegExp('^'));
this.TestCase ( SECTION, "'test'.match(new RegExp('^')); RegExp.rightContext",
'test', RegExp.rightContext);

//test();

},

/**
File Name:          RegExp_rightContext_as_array.js
Description:  'Tests RegExps $\' property (same tests as RegExp_rightContext.js but using $\)'

Author:       Nick Lerissa
Date:         March 12, 1998

*/
test_RegExp_rightContext_as_array :function(){
var SECTION         =  "RegExp_rightContext_as_array";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: $\'';

//writeHeaderToLog('Executing script: RegExp_rightContext.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

// 'abc123xyz'.match(/123/); RegExp['$\'']
'abc123xyz'.match(/123/);
this.TestCase ( SECTION, "'abc123xyz'.match(/123/); RegExp['$\'']",
'xyz', RegExp['$\'']);

// 'abc123xyz'.match(/456/); RegExp['$\'']
'abc123xyz'.match(/456/);
this.TestCase ( SECTION, "'abc123xyz'.match(/456/); RegExp['$\'']",
'xyz', RegExp['$\'']);

// 'abc123xyz'.match(/abc123xyz/); RegExp['$\'']
'abc123xyz'.match(/abc123xyz/);
this.TestCase ( SECTION, "'abc123xyz'.match(/abc123xyz/); RegExp['$\'']",
'', RegExp['$\'']);

// 'xxxx'.match(/$/); RegExp['$\'']
'xxxx'.match(/$/);
this.TestCase ( SECTION, "'xxxx'.match(/$/); RegExp['$\'']",
'', RegExp['$\'']);

// 'test'.match(/^/); RegExp['$\'']
'test'.match(/^/);
this.TestCase ( SECTION, "'test'.match(/^/); RegExp['$\'']",
'test', RegExp['$\'']);

// 'xxxx'.match(new RegExp('$')); RegExp['$\'']
'xxxx'.match(new RegExp('$'));
this.TestCase ( SECTION, "'xxxx'.match(new RegExp('$')); RegExp['$\'']",
'', RegExp['$\'']);

// 'test'.match(new RegExp('^')); RegExp['$\'']
'test'.match(new RegExp('^'));
this.TestCase ( SECTION, "'test'.match(new RegExp('^')); RegExp['$\'']",
'test', RegExp['$\'']);

//test();

},

/**
File Name:          alphanumeric.js
Description:  'Tests regular expressions with \w and \W special characters'

Author:       Nick Lerissa
Date:         March 10, 1998

*/
test_alphanumeric :function(){
var SECTION         =  "alphanumeric";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: \\w and \\W';

//writeHeaderToLog('Executing script: alphanumeric.js');
//writeHeaderToLog( SECTION + " " + TITLE);

var non_alphanumeric = "~`!@#$%^&*()-+={[}]|\\:;'<,>./?\f\n\r\t\v " + '"';
var alphanumeric     = "_abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

// be sure all alphanumerics are matched by \w
this.TestCase ( SECTION,
"'" + alphanumeric + "'.match(new RegExp('\\w+'))",
String([alphanumeric]), String(alphanumeric.match(new RegExp('\\w+'))));

// be sure all non-alphanumerics are matched by \W
this.TestCase ( SECTION,
"'" + non_alphanumeric + "'.match(new RegExp('\\W+'))",
String([non_alphanumeric]), String(non_alphanumeric.match(new RegExp('\\W+'))));

// be sure all non-alphanumerics are not matched by \w
this.TestCase ( SECTION,
"'" + non_alphanumeric + "'.match(new RegExp('\\w'))",
null, non_alphanumeric.match(new RegExp('\\w')));

// be sure all alphanumerics are not matched by \W
this.TestCase ( SECTION,
"'" + alphanumeric + "'.match(new RegExp('\\W'))",
null, alphanumeric.match(new RegExp('\\W')));

var s = non_alphanumeric + alphanumeric;

// be sure all alphanumerics are matched by \w
this.TestCase ( SECTION,
"'" + s + "'.match(new RegExp('\\w+'))",
String([alphanumeric]), String(s.match(new RegExp('\\w+'))));

s = alphanumeric + non_alphanumeric;

// be sure all non-alphanumerics are matched by \W
this.TestCase ( SECTION,
"'" + s + "'.match(new RegExp('\\W+'))",
String([non_alphanumeric]), String(s.match(new RegExp('\\W+'))));

// be sure all alphanumerics are matched by \w (using literals)
this.TestCase ( SECTION,
"'" + s + "'.match(/\w+/)",
String([alphanumeric]), String(s.match(/\w+/)));

s = alphanumeric + non_alphanumeric;

// be sure all non-alphanumerics are matched by \W (using literals)
this.TestCase ( SECTION,
"'" + s + "'.match(/\W+/)",
String([non_alphanumeric]), String(s.match(/\W+/)));

s = 'abcd*&^%$$';
// be sure the following test behaves consistently
this.TestCase ( SECTION,
"'" + s + "'.match(/(\w+)...(\W+)/)",
String([s , 'abcd' , '%$$']), String(s.match(/(\w+)...(\W+)/)));

var i;

// be sure all alphanumeric characters match individually
for (i = 0; i < alphanumeric.length; ++i)
{
s = '#$' + alphanumeric[i] + '%^';
this.TestCase ( SECTION,
"'" + s + "'.match(new RegExp('\\w'))",
String([alphanumeric[i]]), String(s.match(new RegExp('\\w'))));
}
// be sure all non_alphanumeric characters match individually
for (i = 0; i < non_alphanumeric.length; ++i)
{
s = 'sd' + non_alphanumeric[i] + String((i+10) * (i+10) - 2 * (i+10));
this.TestCase ( SECTION,
"'" + s + "'.match(new RegExp('\\W'))",
String([non_alphanumeric[i]]), String(s.match(new RegExp('\\W'))));
}

//test();

},

/**
File Name:          asterisk.js
Description:  'Tests regular expressions containing *'

Author:       Nick Lerissa
Date:         March 10, 1998
*/
test_asterisk :function(){
var SECTION         =  "asterisk";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: *';

//writeHeaderToLog('Executing script: aterisk.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

// 'abcddddefg'.match(new RegExp('d*'))
this.TestCase ( SECTION, "'abcddddefg'.match(new RegExp('d*'))",
String([""]), String('abcddddefg'.match(new RegExp('d*'))));

// 'abcddddefg'.match(new RegExp('cd*'))
this.TestCase ( SECTION, "'abcddddefg'.match(new RegExp('cd*'))",
String(["cdddd"]), String('abcddddefg'.match(new RegExp('cd*'))));

// 'abcdefg'.match(new RegExp('cx*d'))
this.TestCase ( SECTION, "'abcdefg'.match(new RegExp('cx*d'))",
String(["cd"]), String('abcdefg'.match(new RegExp('cx*d'))));

// 'xxxxxxx'.match(new RegExp('(x*)(x+)'))
this.TestCase ( SECTION, "'xxxxxxx'.match(new RegExp('(x*)(x+)'))",
String(["xxxxxxx","xxxxxx","x"]), String('xxxxxxx'.match(new RegExp('(x*)(x+)'))));

// '1234567890'.match(new RegExp('(\\d*)(\\d+)'))
this.TestCase ( SECTION, "'1234567890'.match(new RegExp('(\\d*)(\\d+)'))",
String(["1234567890","123456789","0"]),
String('1234567890'.match(new RegExp('(\\d*)(\\d+)'))));

// '1234567890'.match(new RegExp('(\\d*)\\d(\\d+)'))
this.TestCase ( SECTION, "'1234567890'.match(new RegExp('(\\d*)\\d(\\d+)'))",
String(["1234567890","12345678","0"]),
String('1234567890'.match(new RegExp('(\\d*)\\d(\\d+)'))));

// 'xxxxxxx'.match(new RegExp('(x+)(x*)'))
this.TestCase ( SECTION, "'xxxxxxx'.match(new RegExp('(x+)(x*)'))",
String(["xxxxxxx","xxxxxxx",""]), String('xxxxxxx'.match(new RegExp('(x+)(x*)'))));

// 'xxxxxxyyyyyy'.match(new RegExp('x*y+$'))
this.TestCase ( SECTION, "'xxxxxxyyyyyy'.match(new RegExp('x*y+$'))",
String(["xxxxxxyyyyyy"]), String('xxxxxxyyyyyy'.match(new RegExp('x*y+$'))));

// 'abcdef'.match(/[\d]*[\s]*bc./)
this.TestCase ( SECTION, "'abcdef'.match(/[\\d]*[\\s]*bc./)",
String(["bcd"]), String('abcdef'.match(/[\d]*[\s]*bc./)));

// 'abcdef'.match(/bc..[\d]*[\s]*/)
this.TestCase ( SECTION, "'abcdef'.match(/bc..[\\d]*[\\s]*/)",
String(["bcde"]), String('abcdef'.match(/bc..[\d]*[\s]*/)));

// 'a1b2c3'.match(/.*/)
this.TestCase ( SECTION, "'a1b2c3'.match(/.*/)",
String(["a1b2c3"]), String('a1b2c3'.match(/.*/)));

// 'a0.b2.c3'.match(/[xyz]*1/)
this.TestCase ( SECTION, "'a0.b2.c3'.match(/[xyz]*1/)",
null, 'a0.b2.c3'.match(/[xyz]*1/));

//test();

},

/**
File Name:          backslash.js
Description:  'Tests regular expressions containing \'

Author:       Nick Lerissa
Date:         March 10, 1998
*/
test_backslash :function(){
var SECTION         =  "backslash";
var VERSION = 'no version';
//startTest();
var TITLE = 'RegExp: \\';

//writeHeaderToLog('Executing script: backslash.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

// 'abcde'.match(new RegExp('\e'))
this.TestCase ( SECTION, "'abcde'.match(new RegExp('\e'))",
String(["e"]), String('abcde'.match(new RegExp('\e'))));

// 'ab\\cde'.match(new RegExp('\\\\'))
this.TestCase ( SECTION, "'ab\\cde'.match(new RegExp('\\\\'))",
String(["\\"]), String('ab\\cde'.match(new RegExp('\\\\'))));

// 'ab\\cde'.match(/\\/) (using literal)
this.TestCase ( SECTION, "'ab\\cde'.match(/\\\\/)",
String(["\\"]), String('ab\\cde'.match(/\\/)));

// 'before ^$*+?.()|{}[] after'.match(new RegExp('\^\$\*\+\?\.\(\)\|\{\}\[\]'))
this.TestCase ( SECTION,
"'before ^$*+?.()|{}[] after'.match(new RegExp('\\^\\$\\*\\+\\?\\.\\(\\)\\|\\{\\}\\[\\]'))",
String(["^$*+?.()|{}[]"]),
String('before ^$*+?.()|{}[] after'.match(new RegExp('\\^\\$\\*\\+\\?\\.\\(\\)\\|\\{\\}\\[\\]'))));

// 'before ^$*+?.()|{}[] after'.match(/\^\$\*\+\?\.\(\)\|\{\}\[\]/) (using literal)
this.TestCase ( SECTION,
"'before ^$*+?.()|{}[] after'.match(/\\^\\$\\*\\+\\?\\.\\(\\)\\|\\{\\}\\[\\]/)",
String(["^$*+?.()|{}[]"]),
String('before ^$*+?.()|{}[] after'.match(/\^\$\*\+\?\.\(\)\|\{\}\[\]/)));

//test();

},

/**
File Name:          backspace.js
Description:  'Tests regular expressions containing [\b]'

Author:       Nick Lerissa
Date:         March 10, 1998
*/
test_backspace :function(){
var SECTION         =  "backspace";
var VERSION = 'no version';
//startTest();
var TITLE = 'RegExp: [\b]';

//writeHeaderToLog('Executing script: backspace.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

// 'abc\bdef'.match(new RegExp('.[\b].'))
this.TestCase ( SECTION, "'abc\bdef'.match(new RegExp('.[\\b].'))",
String(["c\bd"]), String('abc\bdef'.match(new RegExp('.[\\b].'))));

// 'abc\\bdef'.match(new RegExp('.[\b].'))
this.TestCase ( SECTION, "'abc\\bdef'.match(new RegExp('.[\\b].'))",
null, 'abc\\bdef'.match(new RegExp('.[\\b].')));

// 'abc\b\b\bdef'.match(new RegExp('c[\b]{3}d'))
this.TestCase ( SECTION, "'abc\b\b\bdef'.match(new RegExp('c[\\b]{3}d'))",
String(["c\b\b\bd"]), String('abc\b\b\bdef'.match(new RegExp('c[\\b]{3}d'))));

// 'abc\bdef'.match(new RegExp('[^\\[\b\\]]+'))
this.TestCase ( SECTION, "'abc\bdef'.match(new RegExp('[^\\[\\b\\]]+'))",
String(["abc"]), String('abc\bdef'.match(new RegExp('[^\\[\\b\\]]+'))));

// 'abcdef'.match(new RegExp('[^\\[\b\\]]+'))
this.TestCase ( SECTION, "'abcdef'.match(new RegExp('[^\\[\\b\\]]+'))",
String(["abcdef"]), String('abcdef'.match(new RegExp('[^\\[\\b\\]]+'))));

// 'abcdef'.match(/[^\[\b\]]+/)
this.TestCase ( SECTION, "'abcdef'.match(/[^\\[\\b\\]]+/)",
String(["abcdef"]), String('abcdef'.match(/[^\[\b\]]+/)));

//test();

},

/**
File Name:          beginLine.js
Description:  'Tests regular expressions containing ^'

Author:       Nick Lerissa
Date:         March 10, 1998

*/
test_beginLine :function(){
var SECTION         =  "beginLine";
var VERSION = 'no version';
//startTest();
var TITLE = 'RegExp: ^';

//writeHeaderToLog('Executing script: beginLine.js');
//writeHeaderToLog( SECTION + " "+ TITLE);


// 'abcde'.match(new RegExp('^ab'))
this.TestCase ( SECTION, "'abcde'.match(new RegExp('^ab'))",
String(["ab"]), String('abcde'.match(new RegExp('^ab'))));

// 'ab\ncde'.match(new RegExp('^..^e'))
this.TestCase ( SECTION, "'ab\ncde'.match(new RegExp('^..^e'))",
null, 'ab\ncde'.match(new RegExp('^..^e')));

// 'yyyyy'.match(new RegExp('^xxx'))
this.TestCase ( SECTION, "'yyyyy'.match(new RegExp('^xxx'))",
null, 'yyyyy'.match(new RegExp('^xxx')));

// '^^^x'.match(new RegExp('^\\^+'))
this.TestCase ( SECTION, "'^^^x'.match(new RegExp('^\\^+'))",
String(['^^^']), String('^^^x'.match(new RegExp('^\\^+'))));

// '^^^x'.match(/^\^+/)
this.TestCase ( SECTION, "'^^^x'.match(/^\\^+/)",
String(['^^^']), String('^^^x'.match(/^\^+/)));

RegExp.multiline = true;
// 'abc\n123xyz'.match(new RegExp('^\d+')) <multiline==true>
this.TestCase ( SECTION, "'abc\n123xyz'.match(new RegExp('^\\d+'))",
String(['123']), String('abc\n123xyz'.match(new RegExp('^\\d+'))));

//test();

},

/**
File Name:          character_class.js
Description:  'Tests regular expressions containing []'

Author:       Nick Lerissa
Date:         March 10, 1998

*/
test_character_class :function(){
var SECTION         =  "character_class";
var VERSION = 'no version';
//startTest();
var TITLE = 'RegExp: []';

//writeHeaderToLog('Executing script: character_class.js');
//writeHeaderToLog( SECTION + " "+ TITLE);


// 'abcde'.match(new RegExp('ab[ercst]de'))
this.TestCase ( SECTION, "'abcde'.match(new RegExp('ab[ercst]de'))",
String(["abcde"]), String('abcde'.match(new RegExp('ab[ercst]de'))));

// 'abcde'.match(new RegExp('ab[erst]de'))
this.TestCase ( SECTION, "'abcde'.match(new RegExp('ab[erst]de'))",
null, 'abcde'.match(new RegExp('ab[erst]de')));

// 'abcdefghijkl'.match(new RegExp('[d-h]+'))
this.TestCase ( SECTION, "'abcdefghijkl'.match(new RegExp('[d-h]+'))",
String(["defgh"]), String('abcdefghijkl'.match(new RegExp('[d-h]+'))));

// 'abc6defghijkl'.match(new RegExp('[1234567].{2}'))
this.TestCase ( SECTION, "'abc6defghijkl'.match(new RegExp('[1234567].{2}'))",
String(["6de"]), String('abc6defghijkl'.match(new RegExp('[1234567].{2}'))));

// '\n\n\abc324234\n'.match(new RegExp('[a-c\d]+'))
this.TestCase ( SECTION, "'\n\n\abc324234\n'.match(new RegExp('[a-c\\d]+'))",
String(["abc324234"]), String('\n\n\abc324234\n'.match(new RegExp('[a-c\\d]+'))));

// 'abc'.match(new RegExp('ab[.]?c'))
this.TestCase ( SECTION, "'abc'.match(new RegExp('ab[.]?c'))",
String(["abc"]), String('abc'.match(new RegExp('ab[.]?c'))));

// 'abc'.match(new RegExp('a[b]c'))
this.TestCase ( SECTION, "'abc'.match(new RegExp('a[b]c'))",
String(["abc"]), String('abc'.match(new RegExp('a[b]c'))));

// 'a1b  b2c  c3d  def  f4g'.match(new RegExp('[a-z][^1-9][a-z]'))
this.TestCase ( SECTION, "'a1b  b2c  c3d  def  f4g'.match(new RegExp('[a-z][^1-9][a-z]'))",
String(["def"]), String('a1b  b2c  c3d  def  f4g'.match(new RegExp('[a-z][^1-9][a-z]'))));

// '123*&$abc'.match(new RegExp('[*&$]{3}'))
this.TestCase ( SECTION, "'123*&$abc'.match(new RegExp('[*&$]{3}'))",
String(["*&$"]), String('123*&$abc'.match(new RegExp('[*&$]{3}'))));

// 'abc'.match(new RegExp('a[^1-9]c'))
this.TestCase ( SECTION, "'abc'.match(new RegExp('a[^1-9]c'))",
String(["abc"]), String('abc'.match(new RegExp('a[^1-9]c'))));

// 'abc'.match(new RegExp('a[^b]c'))
this.TestCase ( SECTION, "'abc'.match(new RegExp('a[^b]c'))",
null, 'abc'.match(new RegExp('a[^b]c')));

// 'abc#$%def%&*@ghi)(*&'.match(new RegExp('[^a-z]{4}'))
this.TestCase ( SECTION, "'abc#$%def%&*@ghi)(*&'.match(new RegExp('[^a-z]{4}'))",
String(["%&*@"]), String('abc#$%def%&*@ghi)(*&'.match(new RegExp('[^a-z]{4}'))));

// 'abc#$%def%&*@ghi)(*&'.match(/[^a-z]{4}/)
this.TestCase ( SECTION, "'abc#$%def%&*@ghi)(*&'.match(/[^a-z]{4}/)",
String(["%&*@"]), String('abc#$%def%&*@ghi)(*&'.match(/[^a-z]{4}/)));

//test();

},

/**
File Name:          compile.js
Description:  'Tests regular expressions method compile'

Author:       Nick Lerissa
Date:         March 10, 1998

*/
test_compile :function(){
var SECTION         =  "compile";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: compile';

//writeHeaderToLog('Executing script: compile.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

var regularExpression = new RegExp();

regularExpression.compile("[0-9]{3}x[0-9]{4}","i");

this.TestCase ( SECTION,
"(compile '[0-9]{3}x[0-9]{4}','i')",
String(["456X7890"]), String('234X456X7890'.match(regularExpression)));

this.TestCase ( SECTION,
"source of (compile '[0-9]{3}x[0-9]{4}','i')",
"[0-9]{3}x[0-9]{4}", regularExpression.source);

this.TestCase ( SECTION,
"global of (compile '[0-9]{3}x[0-9]{4}','i')",
false, regularExpression.global);

this.TestCase ( SECTION,
"ignoreCase of (compile '[0-9]{3}x[0-9]{4}','i')",
true, regularExpression.ignoreCase);

regularExpression.compile("[0-9]{3}X[0-9]{3}","g");

this.TestCase ( SECTION,
"(compile '[0-9]{3}X[0-9]{3}','g')",
String(["234X456"]), String('234X456X7890'.match(regularExpression)));

this.TestCase ( SECTION,
"source of (compile '[0-9]{3}X[0-9]{3}','g')",
"[0-9]{3}X[0-9]{3}", regularExpression.source);

this.TestCase ( SECTION,
"global of (compile '[0-9]{3}X[0-9]{3}','g')",
true, regularExpression.global);

this.TestCase ( SECTION,
"ignoreCase of (compile '[0-9]{3}X[0-9]{3}','g')",
false, regularExpression.ignoreCase);


//test();

},

/**
File Name:          digit.js
Description:  'Tests regular expressions containing \d'

Author:       Nick Lerissa
Date:         March 10, 1998

*/
test_digit :function(){
var SECTION         =  "digit";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: \\d';

//writeHeaderToLog('Executing script: digit.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

var non_digits = "_abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ\f\n\r\t\v~`!@#$%^&*()-+={[}]|\\:;'<,>./? " + '"';

var digits = "1234567890";

// be sure all digits are matched by \d
this.TestCase ( SECTION,
"'" + digits + "'.match(new RegExp('\\d+'))",
String([digits]), String(digits.match(new RegExp('\\d+'))));

// be sure all non-digits are matched by \D
this.TestCase ( SECTION,
"'" + non_digits + "'.match(new RegExp('\\D+'))",
String([non_digits]), String(non_digits.match(new RegExp('\\D+'))));

// be sure all non-digits are not matched by \d
this.TestCase ( SECTION,
"'" + non_digits + "'.match(new RegExp('\\d'))",
null, non_digits.match(new RegExp('\\d')));

// be sure all digits are not matched by \D
this.TestCase ( SECTION,
"'" + digits + "'.match(new RegExp('\\D'))",
null, digits.match(new RegExp('\\D')));

var s = non_digits + digits;

// be sure all digits are matched by \d
this.TestCase ( SECTION,
"'" + s + "'.match(new RegExp('\\d+'))",
String([digits]), String(s.match(new RegExp('\\d+'))));

 s = digits + non_digits;

// be sure all non-digits are matched by \D
this.TestCase ( SECTION,
"'" + s + "'.match(new RegExp('\\D+'))",
String([non_digits]), String(s.match(new RegExp('\\D+'))));

var i;

// be sure all digits match individually
for (i = 0; i < digits.length; ++i)
{
s = 'ab' + digits[i] + 'cd';
this.TestCase ( SECTION,
"'" + s + "'.match(new RegExp('\\d'))",
String([digits[i]]), String(s.match(new RegExp('\\d'))));
this.TestCase ( SECTION,
"'" + s + "'.match(/\\d/)",
String([digits[i]]), String(s.match(/\d/)));
}
// be sure all non_digits match individually
for (i = 0; i < non_digits.length; ++i)
{
s = '12' + non_digits[i] + '34';
this.TestCase ( SECTION,
"'" + s + "'.match(new RegExp('\\D'))",
String([non_digits[i]]), String(s.match(new RegExp('\\D'))));
this.TestCase ( SECTION,
"'" + s + "'.match(/\\D/)",
String([non_digits[i]]), String(s.match(/\D/)));
}

//test();

},

/**
File Name:          dot.js
Description:  'Tests regular expressions containing .'

Author:       Nick Lerissa
Date:         March 10, 1998
*/
test_dot :function(){
var SECTION         =  "dot";
var VERSION = 'no version';
//startTest();
var TITLE = 'RegExp: .';

//writeHeaderToLog('Executing script: dot.js');
//writeHeaderToLog( SECTION + " "+ TITLE);


// 'abcde'.match(new RegExp('ab.de'))
this.TestCase ( SECTION, "'abcde'.match(new RegExp('ab.de'))",
String(["abcde"]), String('abcde'.match(new RegExp('ab.de'))));

// 'line 1\nline 2'.match(new RegExp('.+'))
this.TestCase ( SECTION, "'line 1\nline 2'.match(new RegExp('.+'))",
String(["line 1"]), String('line 1\nline 2'.match(new RegExp('.+'))));

// 'this is a test'.match(new RegExp('.*a.*'))
this.TestCase ( SECTION, "'this is a test'.match(new RegExp('.*a.*'))",
String(["this is a test"]), String('this is a test'.match(new RegExp('.*a.*'))));

// 'this is a *&^%$# test'.match(new RegExp('.+'))
this.TestCase ( SECTION, "'this is a *&^%$# test'.match(new RegExp('.+'))",
String(["this is a *&^%$# test"]), String('this is a *&^%$# test'.match(new RegExp('.+'))));

// '....'.match(new RegExp('.+'))
this.TestCase ( SECTION, "'....'.match(new RegExp('.+'))",
String(["...."]), String('....'.match(new RegExp('.+'))));

// 'abcdefghijklmnopqrstuvwxyz'.match(new RegExp('.+'))
this.TestCase ( SECTION, "'abcdefghijklmnopqrstuvwxyz'.match(new RegExp('.+'))",
String(["abcdefghijklmnopqrstuvwxyz"]), String('abcdefghijklmnopqrstuvwxyz'.match(new RegExp('.+'))));

// 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'.match(new RegExp('.+'))
this.TestCase ( SECTION, "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'.match(new RegExp('.+'))",
String(["ABCDEFGHIJKLMNOPQRSTUVWXYZ"]), String('ABCDEFGHIJKLMNOPQRSTUVWXYZ'.match(new RegExp('.+'))));

// '`1234567890-=~!@#$%^&*()_+'.match(new RegExp('.+'))
this.TestCase ( SECTION, "'`1234567890-=~!@#$%^&*()_+'.match(new RegExp('.+'))",
String(["`1234567890-=~!@#$%^&*()_+"]), String('`1234567890-=~!@#$%^&*()_+'.match(new RegExp('.+'))));

// '|\\[{]};:"\',<>.?/'.match(new RegExp('.+'))
this.TestCase ( SECTION, "'|\\[{]};:\"\',<>.?/'.match(new RegExp('.+'))",
String(["|\\[{]};:\"\',<>.?/"]), String('|\\[{]};:\"\',<>.?/'.match(new RegExp('.+'))));

// '|\\[{]};:"\',<>.?/'.match(/.+/)
this.TestCase ( SECTION, "'|\\[{]};:\"\',<>.?/'.match(/.+/)",
String(["|\\[{]};:\"\',<>.?/"]), String('|\\[{]};:\"\',<>.?/'.match(/.+/)));

//test();

},

/**
File Name:          endLine.js
Description:  'Tests regular expressions containing $'

Author:       Nick Lerissa
Date:         March 10, 1998
*/
test_endLine :function(){
var SECTION         =  "endLine";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: $';

//writeHeaderToLog('Executing script: endLine.js');
//writeHeaderToLog( SECTION + " "+ TITLE);


// 'abcde'.match(new RegExp('de$'))
this.TestCase ( SECTION, "'abcde'.match(new RegExp('de$'))",
String(["de"]), String('abcde'.match(new RegExp('de$'))));

// 'ab\ncde'.match(new RegExp('..$e$'))
this.TestCase ( SECTION, "'ab\ncde'.match(new RegExp('..$e$'))",
null, 'ab\ncde'.match(new RegExp('..$e$')));

// 'yyyyy'.match(new RegExp('xxx$'))
this.TestCase ( SECTION, "'yyyyy'.match(new RegExp('xxx$'))",
null, 'yyyyy'.match(new RegExp('xxx$')));

// 'a$$$'.match(new RegExp('\\$+$'))
this.TestCase ( SECTION, "'a$$$'.match(new RegExp('\\$+$'))",
String(['$$$']), String('a$$$'.match(new RegExp('\\$+$'))));

// 'a$$$'.match(/\$+$/)
this.TestCase ( SECTION, "'a$$$'.match(/\\$+$/)",
String(['$$$']), String('a$$$'.match(/\$+$/)));

RegExp.multiline = true;
// 'abc\n123xyz890\nxyz'.match(new RegExp('\d+$')) <multiline==true>
this.TestCase ( SECTION, "'abc\n123xyz890\nxyz'.match(new RegExp('\\d+$'))",
String(['890']), String('abc\n123xyz890\nxyz'.match(new RegExp('\\d+$'))));

//test();

},

/**
File Name:          everything.js
Description:  'Tests regular expressions'

Author:       Nick Lerissa
Date:         March 24, 1998
*/
test_everything :function(){
var SECTION         =  "everything";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp';

//writeHeaderToLog('Executing script: everything.js');
//writeHeaderToLog( SECTION + " "+ TITLE);


// 'Sally and Fred are sure to come.'.match(/^[a-z\s]*/i)
this.TestCase ( SECTION, "'Sally and Fred are sure to come'.match(/^[a-z\\s]*/i)",
String(["Sally and Fred are sure to come"]), String('Sally and Fred are sure to come'.match(/^[a-z\s]*/i)));

// 'test123W+xyz'.match(new RegExp('^[a-z]*[0-9]+[A-Z]?.(123|xyz)$'))
this.TestCase ( SECTION, "'test123W+xyz'.match(new RegExp('^[a-z]*[0-9]+[A-Z]?.(123|xyz)$'))",
String(["test123W+xyz","xyz"]), String('test123W+xyz'.match(new RegExp('^[a-z]*[0-9]+[A-Z]?.(123|xyz)$'))));

// 'number one 12365 number two 9898'.match(/(\d+)\D+(\d+)/)
this.TestCase ( SECTION, "'number one 12365 number two 9898'.match(/(\d+)\D+(\d+)/)",
String(["12365 number two 9898","12365","9898"]), String('number one 12365 number two 9898'.match(/(\d+)\D+(\d+)/)));

var simpleSentence = /(\s?[^\!\?\.]+[\!\?\.])+/;
// 'See Spot run.'.match(simpleSentence)
this.TestCase ( SECTION, "'See Spot run.'.match(simpleSentence)",
String(["See Spot run.","See Spot run."]), String('See Spot run.'.match(simpleSentence)));

// 'I like it. What's up? I said NO!'.match(simpleSentence)
this.TestCase ( SECTION, "'I like it. What's up? I said NO!'.match(simpleSentence)",
String(["I like it. What's up? I said NO!",' I said NO!']), String('I like it. What\'s up? I said NO!'.match(simpleSentence)));

// 'the quick brown fox jumped over the lazy dogs'.match(/((\w+)\s*)+/)
this.TestCase ( SECTION, "'the quick brown fox jumped over the lazy dogs'.match(/((\\w+)\\s*)+/)",
String(['the quick brown fox jumped over the lazy dogs','dogs','dogs']),String('the quick brown fox jumped over the lazy dogs'.match(/((\w+)\s*)+/)));

//test();

},

/**
File Name:          exec.js
Description:  'Tests regular expressions exec compile'

Author:       Nick Lerissa
Date:         March 10, 1998
*/
test_exec :function(){
var SECTION         =  "exec";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: exec';

//writeHeaderToLog('Executing script: exec.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase ( SECTION,
"/[0-9]{3}/.exec('23 2 34 678 9 09')",
String(["678"]), String(/[0-9]{3}/.exec('23 2 34 678 9 09')));

this.TestCase ( SECTION,
"/3.{4}8/.exec('23 2 34 678 9 09')",
String(["34 678"]), String(/3.{4}8/.exec('23 2 34 678 9 09')));

var re = new RegExp('3.{4}8');
this.TestCase ( SECTION,
"re.exec('23 2 34 678 9 09')",
String(["34 678"]), String(re.exec('23 2 34 678 9 09')));

this.TestCase ( SECTION,
"(/3.{4}8/.exec('23 2 34 678 9 09').length",
1, (/3.{4}8/.exec('23 2 34 678 9 09')).length);

re = new RegExp('3.{4}8');
this.TestCase ( SECTION,
"(re.exec('23 2 34 678 9 09').length",
1, (re.exec('23 2 34 678 9 09')).length);

//test();

},

/**
File Name:          flags.js
Description:  'Tests regular expressions using flags "i" and "g"'

Author:       Nick Lerissa
Date:         March 10, 1998

*/
test_flags :function(){
var SECTION         =  "flags";
var VERSION = 'no version';
//startTest();
var TITLE   = 'regular expression flags with flags "i" and "g"';

//writeHeaderToLog('Executing script: flags.js');
//writeHeaderToLog( SECTION + " "+ TITLE);


// testing optional flag 'i'
this.TestCase ( SECTION, "'aBCdEfGHijKLmno'.match(/fghijk/i)",
String(["fGHijK"]), String('aBCdEfGHijKLmno'.match(/fghijk/i)));

this.TestCase ( SECTION, "'aBCdEfGHijKLmno'.match(new RegExp('fghijk','i'))",
String(["fGHijK"]), String('aBCdEfGHijKLmno'.match(new RegExp("fghijk","i"))));

// testing optional flag 'g'
this.TestCase ( SECTION, "'xa xb xc xd xe xf'.match(/x./g)",
String(["xa","xb","xc","xd","xe","xf"]), String('xa xb xc xd xe xf'.match(/x./g)));

this.TestCase ( SECTION, "'xa xb xc xd xe xf'.match(new RegExp('x.','g'))",
String(["xa","xb","xc","xd","xe","xf"]), String('xa xb xc xd xe xf'.match(new RegExp('x.','g'))));

// testing optional flags 'g' and 'i'
this.TestCase ( SECTION, "'xa Xb xc xd Xe xf'.match(/x./gi)",
String(["xa","Xb","xc","xd","Xe","xf"]), String('xa Xb xc xd Xe xf'.match(/x./gi)));

this.TestCase ( SECTION, "'xa Xb xc xd Xe xf'.match(new RegExp('x.','gi'))",
String(["xa","Xb","xc","xd","Xe","xf"]), String('xa Xb xc xd Xe xf'.match(new RegExp('x.','gi'))));

this.TestCase ( SECTION, "'xa Xb xc xd Xe xf'.match(/x./ig)",
String(["xa","Xb","xc","xd","Xe","xf"]), String('xa Xb xc xd Xe xf'.match(/x./ig)));

this.TestCase ( SECTION, "'xa Xb xc xd Xe xf'.match(new RegExp('x.','ig'))",
String(["xa","Xb","xc","xd","Xe","xf"]), String('xa Xb xc xd Xe xf'.match(new RegExp('x.','ig'))));


//test();

},

/**
File Name:          global.js
Description:  'Tests RegExp attribute global'

Author:       Nick Lerissa
Date:         March 13, 1998
*/
test_global :function(){
var SECTION         =  "global";
var VERSION = 'no version';
//startTest();
var TITLE = 'RegExp: global';

//writeHeaderToLog('Executing script: global.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

// /xyz/g.global
this.TestCase ( SECTION, "/xyz/g.global",
true, /xyz/g.global);

// /xyz/.global
this.TestCase ( SECTION, "/xyz/.global",
false, /xyz/.global);

// '123 456 789'.match(/\d+/g)
this.TestCase ( SECTION, "'123 456 789'.match(/\\d+/g)",
String(["123","456","789"]), String('123 456 789'.match(/\d+/g)));

// '123 456 789'.match(/(\d+)/g)
this.TestCase ( SECTION, "'123 456 789'.match(/(\\d+)/g)",
String(["123","456","789"]), String('123 456 789'.match(/(\d+)/g)));

// '123 456 789'.match(/\d+/)
this.TestCase ( SECTION, "'123 456 789'.match(/\\d+/)",
String(["123"]), String('123 456 789'.match(/\d+/)));

// (new RegExp('[a-z]','g')).global
this.TestCase ( SECTION, "(new RegExp('[a-z]','g')).global",
true, (new RegExp('[a-z]','g')).global);

// (new RegExp('[a-z]','i')).global
this.TestCase ( SECTION, "(new RegExp('[a-z]','i')).global",
false, (new RegExp('[a-z]','i')).global);

// '123 456 789'.match(new RegExp('\\d+','g'))
this.TestCase ( SECTION, "'123 456 789'.match(new RegExp('\\\\d+','g'))",
String(["123","456","789"]), String('123 456 789'.match(new RegExp('\\d+','g'))));

// '123 456 789'.match(new RegExp('(\\d+)','g'))
this.TestCase ( SECTION, "'123 456 789'.match(new RegExp('(\\\\d+)','g'))",
String(["123","456","789"]), String('123 456 789'.match(new RegExp('(\\d+)','g'))));

// '123 456 789'.match(new RegExp('\\d+','i'))
this.TestCase ( SECTION, "'123 456 789'.match(new RegExp('\\\\d+','i'))",
String(["123"]), String('123 456 789'.match(new RegExp('\\d+','i'))));

//test();

},

/**
File Name:          hexadecimal.js
Description:  'Tests regular expressions containing \<number> '

Author:       Nick Lerissa
Date:         March 10, 1998

*/
test_hexadecimal :function(){
var SECTION         =  "hexadecimal";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: \x# (hex) ';

//writeHeaderToLog('Executing script: hexadecimal.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

var testPattern = '\\x41\\x42\\x43\\x44\\x45\\x46\\x47\\x48\\x49\\x4A\\x4B\\x4C\\x4D\\x4E\\x4F\\x50\\x51\\x52\\x53\\x54\\x55\\x56\\x57\\x58\\x59\\x5A';

var testString = "12345ABCDEFGHIJKLMNOPQRSTUVWXYZ67890";

this.TestCase ( SECTION,
"'" + testString + "'.match(new RegExp('" + testPattern + "'))",
String(["ABCDEFGHIJKLMNOPQRSTUVWXYZ"]), String(testString.match(new RegExp(testPattern))));

testPattern = '\\x61\\x62\\x63\\x64\\x65\\x66\\x67\\x68\\x69\\x6A\\x6B\\x6C\\x6D\\x6E\\x6F\\x70\\x71\\x72\\x73\\x74\\x75\\x76\\x77\\x78\\x79\\x7A';

testString = "12345AabcdefghijklmnopqrstuvwxyzZ67890";

this.TestCase ( SECTION,
"'" + testString + "'.match(new RegExp('" + testPattern + "'))",
String(["abcdefghijklmnopqrstuvwxyz"]), String(testString.match(new RegExp(testPattern))));

testPattern = '\\x20\\x21\\x22\\x23\\x24\\x25\\x26\\x27\\x28\\x29\\x2A\\x2B\\x2C\\x2D\\x2E\\x2F\\x30\\x31\\x32\\x33';

testString = "abc !\"#$%&'()*+,-./0123ZBC";

this.TestCase ( SECTION,
"'" + testString + "'.match(new RegExp('" + testPattern + "'))",
String([" !\"#$%&'()*+,-./0123"]), String(testString.match(new RegExp(testPattern))));

testPattern = '\\x34\\x35\\x36\\x37\\x38\\x39\\x3A\\x3B\\x3C\\x3D\\x3E\\x3F\\x40';

testString = "123456789:;<=>?@ABC";

this.TestCase ( SECTION,
"'" + testString + "'.match(new RegExp('" + testPattern + "'))",
String(["456789:;<=>?@"]), String(testString.match(new RegExp(testPattern))));

testPattern = '\\x7B\\x7C\\x7D\\x7E';

testString = "1234{|}~ABC";

this.TestCase ( SECTION,
"'" + testString + "'.match(new RegExp('" + testPattern + "'))",
String(["{|}~"]), String(testString.match(new RegExp(testPattern))));

this.TestCase ( SECTION,
"'canthisbeFOUND'.match(new RegExp('[A-\\x5A]+'))",
String(["FOUND"]), String('canthisbeFOUND'.match(new RegExp('[A-\\x5A]+'))));

this.TestCase ( SECTION,
"'canthisbeFOUND'.match(new RegExp('[\\x61-\\x7A]+'))",
String(["canthisbe"]), String('canthisbeFOUND'.match(new RegExp('[\\x61-\\x7A]+'))));

this.TestCase ( SECTION,
"'canthisbeFOUND'.match(/[\\x61-\\x7A]+/)",
String(["canthisbe"]), String('canthisbeFOUND'.match(/[\x61-\x7A]+/)));

//test();

},

/**
File Name:          ignoreCase.js
Description:  'Tests RegExp attribute ignoreCase'

Author:       Nick Lerissa
Date:         March 13, 1998

*/
test_ignoreCase :function(){
var SECTION         =  "ignoreCase";
var VERSION = 'no version';
//startTest();
var TITLE = 'RegExp: ignoreCase';

//writeHeaderToLog('Executing script: ignoreCase.js');
//writeHeaderToLog( SECTION + " "+ TITLE);


// /xyz/i.ignoreCase
this.TestCase ( SECTION, "/xyz/i.ignoreCase",
true, /xyz/i.ignoreCase);

// /xyz/.ignoreCase
this.TestCase ( SECTION, "/xyz/.ignoreCase",
false, /xyz/.ignoreCase);

// 'ABC def ghi'.match(/[a-z]+/ig)
this.TestCase ( SECTION, "'ABC def ghi'.match(/[a-z]+/ig)",
String(["ABC","def","ghi"]), String('ABC def ghi'.match(/[a-z]+/ig)));

// 'ABC def ghi'.match(/[a-z]+/i)
this.TestCase ( SECTION, "'ABC def ghi'.match(/[a-z]+/i)",
String(["ABC"]), String('ABC def ghi'.match(/[a-z]+/i)));

// 'ABC def ghi'.match(/([a-z]+)/ig)
this.TestCase ( SECTION, "'ABC def ghi'.match(/([a-z]+)/ig)",
String(["ABC","def","ghi"]), String('ABC def ghi'.match(/([a-z]+)/ig)));

// 'ABC def ghi'.match(/([a-z]+)/i)
this.TestCase ( SECTION, "'ABC def ghi'.match(/([a-z]+)/i)",
String(["ABC","ABC"]), String('ABC def ghi'.match(/([a-z]+)/i)));

// 'ABC def ghi'.match(/[a-z]+/)
this.TestCase ( SECTION, "'ABC def ghi'.match(/[a-z]+/)",
String(["def"]), String('ABC def ghi'.match(/[a-z]+/)));

// (new RegExp('xyz','i')).ignoreCase
this.TestCase ( SECTION, "(new RegExp('xyz','i')).ignoreCase",
true, (new RegExp('xyz','i')).ignoreCase);

// (new RegExp('xyz')).ignoreCase
this.TestCase ( SECTION, "(new RegExp('xyz')).ignoreCase",
false, (new RegExp('xyz')).ignoreCase);

// 'ABC def ghi'.match(new RegExp('[a-z]+','ig'))
this.TestCase ( SECTION, "'ABC def ghi'.match(new RegExp('[a-z]+','ig'))",
String(["ABC","def","ghi"]), String('ABC def ghi'.match(new RegExp('[a-z]+','ig'))));

// 'ABC def ghi'.match(new RegExp('[a-z]+','i'))
this.TestCase ( SECTION, "'ABC def ghi'.match(new RegExp('[a-z]+','i'))",
String(["ABC"]), String('ABC def ghi'.match(new RegExp('[a-z]+','i'))));

// 'ABC def ghi'.match(new RegExp('([a-z]+)','ig'))
this.TestCase ( SECTION, "'ABC def ghi'.match(new RegExp('([a-z]+)','ig'))",
String(["ABC","def","ghi"]), String('ABC def ghi'.match(new RegExp('([a-z]+)','ig'))));

// 'ABC def ghi'.match(new RegExp('([a-z]+)','i'))
this.TestCase ( SECTION, "'ABC def ghi'.match(new RegExp('([a-z]+)','i'))",
String(["ABC","ABC"]), String('ABC def ghi'.match(new RegExp('([a-z]+)','i'))));

// 'ABC def ghi'.match(new RegExp('[a-z]+'))
this.TestCase ( SECTION, "'ABC def ghi'.match(new RegExp('[a-z]+'))",
String(["def"]), String('ABC def ghi'.match(new RegExp('[a-z]+'))));

//test();

},

/**
File Name:          interval.js
Description:  'Tests regular expressions containing {}'

Author:       Nick Lerissa
Date:         March 10, 1998
*/
test_interval :function(){
var SECTION         =  "interval";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: {}';

//writeHeaderToLog('Executing script: interval.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

// 'aaabbbbcccddeeeefffff'.match(new RegExp('b{2}c'))
this.TestCase ( SECTION, "'aaabbbbcccddeeeefffff'.match(new RegExp('b{2}c'))",
String(["bbc"]), String('aaabbbbcccddeeeefffff'.match(new RegExp('b{2}c'))));

// 'aaabbbbcccddeeeefffff'.match(new RegExp('b{8}'))
this.TestCase ( SECTION, "'aaabbbbcccddeeeefffff'.match(new RegExp('b{8}'))",
null, 'aaabbbbcccddeeeefffff'.match(new RegExp('b{8}')));

// 'aaabbbbcccddeeeefffff'.match(new RegExp('b{2,}c'))
this.TestCase ( SECTION, "'aaabbbbcccddeeeefffff'.match(new RegExp('b{2,}c'))",
String(["bbbbc"]), String('aaabbbbcccddeeeefffff'.match(new RegExp('b{2,}c'))));

// 'aaabbbbcccddeeeefffff'.match(new RegExp('b{8,}c'))
this.TestCase ( SECTION, "'aaabbbbcccddeeeefffff'.match(new RegExp('b{8,}c'))",
null, 'aaabbbbcccddeeeefffff'.match(new RegExp('b{8,}c')));

// 'aaabbbbcccddeeeefffff'.match(new RegExp('b{2,3}c'))
this.TestCase ( SECTION, "'aaabbbbcccddeeeefffff'.match(new RegExp('b{2,3}c'))",
String(["bbbc"]), String('aaabbbbcccddeeeefffff'.match(new RegExp('b{2,3}c'))));

// 'aaabbbbcccddeeeefffff'.match(new RegExp('b{42,93}c'))
this.TestCase ( SECTION, "'aaabbbbcccddeeeefffff'.match(new RegExp('b{42,93}c'))",
null, 'aaabbbbcccddeeeefffff'.match(new RegExp('b{42,93}c')));

// 'aaabbbbcccddeeeefffff'.match(new RegExp('b{0,93}c'))
this.TestCase ( SECTION, "'aaabbbbcccddeeeefffff'.match(new RegExp('b{0,93}c'))",
String(["bbbbc"]), String('aaabbbbcccddeeeefffff'.match(new RegExp('b{0,93}c'))));

// 'aaabbbbcccddeeeefffff'.match(new RegExp('bx{0,93}c'))
this.TestCase ( SECTION, "'aaabbbbcccddeeeefffff'.match(new RegExp('bx{0,93}c'))",
String(["bc"]), String('aaabbbbcccddeeeefffff'.match(new RegExp('bx{0,93}c'))));

// 'weirwerdf'.match(new RegExp('.{0,93}'))
this.TestCase ( SECTION, "'weirwerdf'.match(new RegExp('.{0,93}'))",
String(["weirwerdf"]), String('weirwerdf'.match(new RegExp('.{0,93}'))));

// 'wqe456646dsff'.match(new RegExp('\d{1,}'))
this.TestCase ( SECTION, "'wqe456646dsff'.match(new RegExp('\\d{1,}'))",
String(["456646"]), String('wqe456646dsff'.match(new RegExp('\\d{1,}'))));

// '123123'.match(new RegExp('(123){1,}'))
this.TestCase ( SECTION, "'123123'.match(new RegExp('(123){1,}'))",
String(["123123","123"]), String('123123'.match(new RegExp('(123){1,}'))));

// '123123x123'.match(new RegExp('(123){1,}x\1'))
this.TestCase ( SECTION, "'123123x123'.match(new RegExp('(123){1,}x\\1'))",
String(["123123x123","123"]), String('123123x123'.match(new RegExp('(123){1,}x\\1'))));

// '123123x123'.match(/(123){1,}x\1/)
this.TestCase ( SECTION, "'123123x123'.match(/(123){1,}x\\1/)",
String(["123123x123","123"]), String('123123x123'.match(/(123){1,}x\1/)));

// 'xxxxxxx'.match(new RegExp('x{1,2}x{1,}'))
this.TestCase ( SECTION, "'xxxxxxx'.match(new RegExp('x{1,2}x{1,}'))",
String(["xxxxxxx"]), String('xxxxxxx'.match(new RegExp('x{1,2}x{1,}'))));

// 'xxxxxxx'.match(/x{1,2}x{1,}/)
this.TestCase ( SECTION, "'xxxxxxx'.match(/x{1,2}x{1,}/)",
String(["xxxxxxx"]), String('xxxxxxx'.match(/x{1,2}x{1,}/)));

//test();

},

/**
File Name:          octal.js
Description:  'Tests regular expressions containing \<number> '

Author:       Nick Lerissa
Date:         March 10, 1998
*/
test_octal :function(){
var SECTION         =  "octal";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: \# (octal) ';

//writeHeaderToLog('Executing script: octal.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

var testPattern = '\\101\\102\\103\\104\\105\\106\\107\\110\\111\\112\\113\\114\\115\\116\\117\\120\\121\\122\\123\\124\\125\\126\\127\\130\\131\\132';

var testString = "12345ABCDEFGHIJKLMNOPQRSTUVWXYZ67890";

this.TestCase ( SECTION,
"'" + testString + "'.match(new RegExp('" + testPattern + "'))",
String(["ABCDEFGHIJKLMNOPQRSTUVWXYZ"]), String(testString.match(new RegExp(testPattern))));

testPattern = '\\141\\142\\143\\144\\145\\146\\147\\150\\151\\152\\153\\154\\155\\156\\157\\160\\161\\162\\163\\164\\165\\166\\167\\170\\171\\172';

testString = "12345AabcdefghijklmnopqrstuvwxyzZ67890";

this.TestCase ( SECTION,
"'" + testString + "'.match(new RegExp('" + testPattern + "'))",
String(["abcdefghijklmnopqrstuvwxyz"]), String(testString.match(new RegExp(testPattern))));

testPattern = '\\40\\41\\42\\43\\44\\45\\46\\47\\50\\51\\52\\53\\54\\55\\56\\57\\60\\61\\62\\63';

testString = "abc !\"#$%&'()*+,-./0123ZBC";

this.TestCase ( SECTION,
"'" + testString + "'.match(new RegExp('" + testPattern + "'))",
String([" !\"#$%&'()*+,-./0123"]), String(testString.match(new RegExp(testPattern))));

testPattern = '\\64\\65\\66\\67\\70\\71\\72\\73\\74\\75\\76\\77\\100';

testString = "123456789:;<=>?@ABC";

this.TestCase ( SECTION,
"'" + testString + "'.match(new RegExp('" + testPattern + "'))",
String(["456789:;<=>?@"]), String(testString.match(new RegExp(testPattern))));

testPattern = '\\173\\174\\175\\176';

testString = "1234{|}~ABC";

this.TestCase ( SECTION,
"'" + testString + "'.match(new RegExp('" + testPattern + "'))",
String(["{|}~"]), String(testString.match(new RegExp(testPattern))));

this.TestCase ( SECTION,
"'canthisbeFOUND'.match(new RegExp('[A-\\132]+'))",
String(["FOUND"]), String('canthisbeFOUND'.match(new RegExp('[A-\\132]+'))));

this.TestCase ( SECTION,
"'canthisbeFOUND'.match(new RegExp('[\\141-\\172]+'))",
String(["canthisbe"]), String('canthisbeFOUND'.match(new RegExp('[\\141-\\172]+'))));

this.TestCase ( SECTION,
"'canthisbeFOUND'.match(/[\\141-\\172]+/)",
String(["canthisbe"]), String('canthisbeFOUND'.match(/[\141-\172]+/)));

//test();

},

/**
File Name:          parentheses.js
Description:  'Tests regular expressions containing ()'

Author:       Nick Lerissa
Date:         March 10, 1998
*/
test_parentheses :function(){
var SECTION         =  "parentheses";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: ()';

//writeHeaderToLog('Executing script: parentheses.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

// 'abc'.match(new RegExp('(abc)'))
this.TestCase ( SECTION, "'abc'.match(new RegExp('(abc)'))",
String(["abc","abc"]), String('abc'.match(new RegExp('(abc)'))));

// 'abcdefg'.match(new RegExp('a(bc)d(ef)g'))
this.TestCase ( SECTION, "'abcdefg'.match(new RegExp('a(bc)d(ef)g'))",
String(["abcdefg","bc","ef"]), String('abcdefg'.match(new RegExp('a(bc)d(ef)g'))));

// 'abcdefg'.match(new RegExp('(.{3})(.{4})'))
this.TestCase ( SECTION, "'abcdefg'.match(new RegExp('(.{3})(.{4})'))",
String(["abcdefg","abc","defg"]), String('abcdefg'.match(new RegExp('(.{3})(.{4})'))));

// 'aabcdaabcd'.match(new RegExp('(aa)bcd\1'))
this.TestCase ( SECTION, "'aabcdaabcd'.match(new RegExp('(aa)bcd\\1'))",
String(["aabcdaa","aa"]), String('aabcdaabcd'.match(new RegExp('(aa)bcd\\1'))));

// 'aabcdaabcd'.match(new RegExp('(aa).+\1'))
this.TestCase ( SECTION, "'aabcdaabcd'.match(new RegExp('(aa).+\\1'))",
String(["aabcdaa","aa"]), String('aabcdaabcd'.match(new RegExp('(aa).+\\1'))));

// 'aabcdaabcd'.match(new RegExp('(.{2}).+\1'))
this.TestCase ( SECTION, "'aabcdaabcd'.match(new RegExp('(.{2}).+\\1'))",
String(["aabcdaa","aa"]), String('aabcdaabcd'.match(new RegExp('(.{2}).+\\1'))));

// '123456123456'.match(new RegExp('(\d{3})(\d{3})\1\2'))
this.TestCase ( SECTION, "'123456123456'.match(new RegExp('(\\d{3})(\\d{3})\\1\\2'))",
String(["123456123456","123","456"]), String('123456123456'.match(new RegExp('(\\d{3})(\\d{3})\\1\\2'))));

// 'abcdefg'.match(new RegExp('a(..(..)..)'))
this.TestCase ( SECTION, "'abcdefg'.match(new RegExp('a(..(..)..)'))",
String(["abcdefg","bcdefg","de"]), String('abcdefg'.match(new RegExp('a(..(..)..)'))));

// 'abcdefg'.match(/a(..(..)..)/)
this.TestCase ( SECTION, "'abcdefg'.match(/a(..(..)..)/)",
String(["abcdefg","bcdefg","de"]), String('abcdefg'.match(/a(..(..)..)/)));

// 'xabcdefg'.match(new RegExp('(a(b(c)))(d(e(f)))'))
this.TestCase ( SECTION, "'xabcdefg'.match(new RegExp('(a(b(c)))(d(e(f)))'))",
String(["abcdef","abc","bc","c","def","ef","f"]), String('xabcdefg'.match(new RegExp('(a(b(c)))(d(e(f)))'))));

// 'xabcdefbcefg'.match(new RegExp('(a(b(c)))(d(e(f)))\2\5'))
this.TestCase ( SECTION, "'xabcdefbcefg'.match(new RegExp('(a(b(c)))(d(e(f)))\\2\\5'))",
String(["abcdefbcef","abc","bc","c","def","ef","f"]), String('xabcdefbcefg'.match(new RegExp('(a(b(c)))(d(e(f)))\\2\\5'))));

// 'abcd'.match(new RegExp('a(.?)b\1c\1d\1'))
this.TestCase ( SECTION, "'abcd'.match(new RegExp('a(.?)b\\1c\\1d\\1'))",
String(["abcd",""]), String('abcd'.match(new RegExp('a(.?)b\\1c\\1d\\1'))));

// 'abcd'.match(/a(.?)b\1c\1d\1/)
this.TestCase ( SECTION, "'abcd'.match(/a(.?)b\\1c\\1d\\1/)",
String(["abcd",""]), String('abcd'.match(/a(.?)b\1c\1d\1/)));

//test();
},

/**
File Name:          plus.js
Description:  'Tests regular expressions containing +'

Author:       Nick Lerissa
Date:         March 10, 1998
*/
test_plus :function(){
var SECTION         =  "plus";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: +';

//writeHeaderToLog('Executing script: plus.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

// 'abcdddddefg'.match(new RegExp('d+'))
this.TestCase ( SECTION, "'abcdddddefg'.match(new RegExp('d+'))",
String(["ddddd"]), String('abcdddddefg'.match(new RegExp('d+'))));

// 'abcdefg'.match(new RegExp('o+'))
this.TestCase ( SECTION, "'abcdefg'.match(new RegExp('o+'))",
null, 'abcdefg'.match(new RegExp('o+')));

// 'abcdefg'.match(new RegExp('d+'))
this.TestCase ( SECTION, "'abcdefg'.match(new RegExp('d+'))",
String(['d']), String('abcdefg'.match(new RegExp('d+'))));

// 'abbbbbbbc'.match(new RegExp('(b+)(b+)(b+)'))
this.TestCase ( SECTION, "'abbbbbbbc'.match(new RegExp('(b+)(b+)(b+)'))",
String(["bbbbbbb","bbbbb","b","b"]), String('abbbbbbbc'.match(new RegExp('(b+)(b+)(b+)'))));

// 'abbbbbbbc'.match(new RegExp('(b+)(b*)'))
this.TestCase ( SECTION, "'abbbbbbbc'.match(new RegExp('(b+)(b*)'))",
String(["bbbbbbb","bbbbbbb",""]), String('abbbbbbbc'.match(new RegExp('(b+)(b*)'))));

// 'abbbbbbbc'.match(new RegExp('b*b+'))
this.TestCase ( SECTION, "'abbbbbbbc'.match(new RegExp('b*b+'))",
String(['bbbbbbb']), String('abbbbbbbc'.match(new RegExp('b*b+'))));

// 'abbbbbbbc'.match(/(b+)(b*)/)
this.TestCase ( SECTION, "'abbbbbbbc'.match(/(b+)(b*)/)",
String(["bbbbbbb","bbbbbbb",""]), String('abbbbbbbc'.match(/(b+)(b*)/)));

// 'abbbbbbbc'.match(new RegExp('b*b+'))
this.TestCase ( SECTION, "'abbbbbbbc'.match(/b*b+/)",
String(['bbbbbbb']), String('abbbbbbbc'.match(/b*b+/)));

//test();

},

/**
File Name:          question_mark.js
Description:  'Tests regular expressions containing ?'

Author:       Nick Lerissa
Date:         March 10, 1998
*/
test_question_mark :function(){
var SECTION         =  "question_mark";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: ?';

//writeHeaderToLog('Executing script: question_mark.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

// 'abcdef'.match(new RegExp('cd?e'))
this.TestCase ( SECTION, "'abcdef'.match(new RegExp('cd?e'))",
String(["cde"]), String('abcdef'.match(new RegExp('cd?e'))));

// 'abcdef'.match(new RegExp('cdx?e'))
this.TestCase ( SECTION, "'abcdef'.match(new RegExp('cdx?e'))",
String(["cde"]), String('abcdef'.match(new RegExp('cdx?e'))));

// 'pqrstuvw'.match(new RegExp('o?pqrst'))
this.TestCase ( SECTION, "'pqrstuvw'.match(new RegExp('o?pqrst'))",
String(["pqrst"]), String('pqrstuvw'.match(new RegExp('o?pqrst'))));

// 'abcd'.match(new RegExp('x?y?z?'))
this.TestCase ( SECTION, "'abcd'.match(new RegExp('x?y?z?'))",
String([""]), String('abcd'.match(new RegExp('x?y?z?'))));

// 'abcd'.match(new RegExp('x?ay?bz?c'))
this.TestCase ( SECTION, "'abcd'.match(new RegExp('x?ay?bz?c'))",
String(["abc"]), String('abcd'.match(new RegExp('x?ay?bz?c'))));

// 'abcd'.match(/x?ay?bz?c/)
this.TestCase ( SECTION, "'abcd'.match(/x?ay?bz?c/)",
String(["abc"]), String('abcd'.match(/x?ay?bz?c/)));

// 'abbbbc'.match(new RegExp('b?b?b?b'))
this.TestCase ( SECTION, "'abbbbc'.match(new RegExp('b?b?b?b'))",
String(["bbbb"]), String('abbbbc'.match(new RegExp('b?b?b?b'))));

// '123az789'.match(new RegExp('ab?c?d?x?y?z'))
this.TestCase ( SECTION, "'123az789'.match(new RegExp('ab?c?d?x?y?z'))",
String(["az"]), String('123az789'.match(new RegExp('ab?c?d?x?y?z'))));

// '123az789'.match(/ab?c?d?x?y?z/)
this.TestCase ( SECTION, "'123az789'.match(/ab?c?d?x?y?z/)",
String(["az"]), String('123az789'.match(/ab?c?d?x?y?z/)));

// '?????'.match(new RegExp('\\??\\??\\??\\??\\??'))
this.TestCase ( SECTION, "'?????'.match(new RegExp('\\??\\??\\??\\??\\??'))",
String(["?????"]), String('?????'.match(new RegExp('\\??\\??\\??\\??\\??'))));

// 'test'.match(new RegExp('.?.?.?.?.?.?.?'))
this.TestCase ( SECTION, "'test'.match(new RegExp('.?.?.?.?.?.?.?'))",
String(["test"]), String('test'.match(new RegExp('.?.?.?.?.?.?.?'))));

//test();

},

/**
File Name:          regress__6359.js
Reference:          ** replace with bugzilla URL or document reference **
Description:        ** replace with description of test **
Author:             ** replace with your e-mail address **
*/
test_regress__6359 :function(){
var SECTION         =  "regress__6359";
var VERSION = "ECMA_2"; // Version of JavaScript or ECMA
var TITLE   = "Regression test for bugzilla # 6359";       // Provide ECMA section title or a description
//var BUGNUMBER = "http://bugzilla.mozilla.org/show_bug.cgi?id=6359";     // Provide URL to bugsplat or bugzilla report

//startTest();               // leave this alone

/*
* Calls to AddTestCase here. AddTestCase is a function that is defined
* in shell.js and takes three arguments:
* - a string representation of what is being tested
* - the expected result
* - the actual result
*
* For example, a test might look like this:
*
* var zip = /[\d]{5}$/;
*
* AddTestCase(
* "zip = /[\d]{5}$/; \"PO Box 12345 Boston, MA 02134\".match(zip)",   // description of the test
*  "02134",                                                           // expected result
*  "PO Box 12345 Boston, MA 02134".match(zip) );                      // actual result
*
*/

this.AddTestCase( '/(a*)b\1+/("baaac").length',
2,
/(a*)b\1+/("baaac").length );

this.AddTestCase( '/(a*)b\1+/("baaac")[0]',
"b",
/(a*)b\1+/("baaac")[0]);

this.AddTestCase( '/(a*)b\1+/("baaac")[1]',
"",
/(a*)b\1+/("baaac")[1]);


//test();       // leave this alone.  this executes the test cases and
// displays results.

},

/**
* File Name:          regress__9141.js
* Reference:          "http://bugzilla.mozilla.org/show_bug.cgi?id=9141";
*  Description:
*  From waldemar@netscape.com:
*
* The following page crashes the system:
*
* <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"
* "http://www.w3.org/TR/REC-html40/loose.dtd">
* <HTML>
* <HEAD>
* </HEAD>
* <BODY>
* <SCRIPT type="text/javascript">
* var s = "x";
*  for (var i = 0; i != 13; i++) s += s;
* var a = /(?:xx|x)*[slash](s);
* var b = /(xx|x)*[slash](s);
* document.write("Results = " + a.length + "," + b.length);
* </SCRIPT>
* </BODY>
*/
test_regress__9141 :function(){
var SECTION         =  "regress__9141";
var VERSION = "ECMA_2"; // Version of JavaScript or ECMA
var TITLE   = "Regression test for bugzilla # 9141";       // Provide ECMA section title or a description
//var BUGNUMBER = "http://bugzilla.mozilla.org/show_bug.cgi?id=9141";     // Provide URL to bugsplat or bugzilla report

//startTest();               // leave this alone

/*
* Calls to AddTestCase here. AddTestCase is a function that is defined
* in shell.js and takes three arguments:
* - a string representation of what is being tested
* - the expected result
* - the actual result
*
* For example, a test might look like this:
*
* var zip = /[\d]{5}$/;
*
* AddTestCase(
* "zip = /[\d]{5}$/; \"PO Box 12345 Boston, MA 02134\".match(zip)",   // description of the test
*  "02134",                                                           // expected result
*  "PO Box 12345 Boston, MA 02134".match(zip) );                      // actual result
*
*/

var s = "x";
for (var i = 0; i != 13; i++) s += s;
var a = /(?:xx|x)*/(s);
var b = /(xx|x)*/(s);

this.AddTestCase( "var s = 'x'; for (var i = 0; i != 13; i++) s += s; " +
"a = /(?:xx|x)*/(s); a.length",
1,
a.length );

this.AddTestCase( "var b = /(xx|x)*/(s); b.length",
2,
b.length );

//test();       // leave this alone.  this executes the test cases and
// displays results.

},

/**
File Name:          simple_form.js
Description:  'Tests regular expressions using simple form: re(...)'

Author:       Nick Lerissa
Date:         March 19, 1998
*/
test_simple_form :function(){
var SECTION         =  "simple_form";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: simple form';

//writeHeaderToLog('Executing script: simple_form.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase ( SECTION,
"/[0-9]{3}/('23 2 34 678 9 09')",
String(["678"]), String(/[0-9]{3}/('23 2 34 678 9 09')));

this.TestCase ( SECTION,
"/3.{4}8/('23 2 34 678 9 09')",
String(["34 678"]), String(/3.{4}8/('23 2 34 678 9 09')));

this.TestCase ( SECTION,
"(/3.{4}8/('23 2 34 678 9 09').length",
1, (/3.{4}8/('23 2 34 678 9 09')).length);

var re = /[0-9]{3}/;
this.TestCase ( SECTION,
"re('23 2 34 678 9 09')",
String(["678"]), String(re('23 2 34 678 9 09')));

re = /3.{4}8/;
this.TestCase ( SECTION,
"re('23 2 34 678 9 09')",
String(["34 678"]), String(re('23 2 34 678 9 09')));

this.TestCase ( SECTION,
"/3.{4}8/('23 2 34 678 9 09')",
String(["34 678"]), String(/3.{4}8/('23 2 34 678 9 09')));

re =/3.{4}8/;
this.TestCase ( SECTION,
"(re('23 2 34 678 9 09').length",
1, (re('23 2 34 678 9 09')).length);

this.TestCase ( SECTION,
"(/3.{4}8/('23 2 34 678 9 09').length",
1, (/3.{4}8/('23 2 34 678 9 09')).length);

//test();

},

/**
File Name:          source.js
Description:  'Tests RegExp attribute source'

Author:       Nick Lerissa
Date:         March 13, 1998
*/
test_source :function(){
var SECTION         =  "source";
var VERSION = 'no version';
//startTest();
var TITLE = 'RegExp: source';

//writeHeaderToLog('Executing script: source.js');
//writeHeaderToLog( SECTION + " "+ TITLE);


// /xyz/g.source
this.TestCase ( SECTION, "/xyz/g.source",
"xyz", /xyz/g.source);

// /xyz/.source
this.TestCase ( SECTION, "/xyz/.source",
"xyz", /xyz/.source);

// /abc\\def/.source
this.TestCase ( SECTION, "/abc\\\\def/.source",
"abc\\\\def", /abc\\def/.source);

// /abc[\b]def/.source
this.TestCase ( SECTION, "/abc[\\b]def/.source",
"abc[\\b]def", /abc[\b]def/.source);

// (new RegExp('xyz')).source
this.TestCase ( SECTION, "(new RegExp('xyz')).source",
"xyz", (new RegExp('xyz')).source);

// (new RegExp('xyz','g')).source
this.TestCase ( SECTION, "(new RegExp('xyz','g')).source",
"xyz", (new RegExp('xyz','g')).source);

// (new RegExp('abc\\\\def')).source
this.TestCase ( SECTION, "(new RegExp('abc\\\\\\\\def')).source",
"abc\\\\def", (new RegExp('abc\\\\def')).source);

// (new RegExp('abc[\\b]def')).source
this.TestCase ( SECTION, "(new RegExp('abc[\\\\b]def')).source",
"abc[\\b]def", (new RegExp('abc[\\b]def')).source);

//test();

},

/**
File Name:          special_characters.js
Description:  'Tests regular expressions containing special characters'

Author:       Nick Lerissa
Date:         March 10, 1998
*/
test_special_characters :function(){
var SECTION         =  "special_characters";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: special_charaters';

//writeHeaderToLog('Executing script: special_characters.js');
//writeHeaderToLog( SECTION + " "+ TITLE);


// testing backslash '\'
this.TestCase ( SECTION, "'^abcdefghi'.match(/\^abc/)", String(["^abc"]), String('^abcdefghi'.match(/\^abc/)));

// testing beginning of line '^'
this.TestCase ( SECTION, "'abcdefghi'.match(/^abc/)", String(["abc"]), String('abcdefghi'.match(/^abc/)));

// testing end of line '$'
this.TestCase ( SECTION, "'abcdefghi'.match(/fghi$/)", String(["ghi"]), String('abcdefghi'.match(/ghi$/)));

// testing repeat '*'
this.TestCase ( SECTION, "'eeeefghi'.match(/e*/)", String(["eeee"]), String('eeeefghi'.match(/e*/)));

// testing repeat 1 or more times '+'
this.TestCase ( SECTION, "'abcdeeeefghi'.match(/e+/)", String(["eeee"]), String('abcdeeeefghi'.match(/e+/)));

// testing repeat 0 or 1 time '?'
this.TestCase ( SECTION, "'abcdefghi'.match(/abc?de/)", String(["abcde"]), String('abcdefghi'.match(/abc?de/)));

// testing any character '.'
this.TestCase ( SECTION, "'abcdefghi'.match(/c.e/)", String(["cde"]), String('abcdefghi'.match(/c.e/)));

// testing remembering ()
this.TestCase ( SECTION, "'abcewirjskjdabciewjsdf'.match(/(abc).+\\1'/)",
String(["abcewirjskjdabc","abc"]), String('abcewirjskjdabciewjsdf'.match(/(abc).+\1/)));

// testing or match '|'
this.TestCase ( SECTION, "'abcdefghi'.match(/xyz|def/)", String(["def"]), String('abcdefghi'.match(/xyz|def/)));

// testing repeat n {n}
this.TestCase ( SECTION, "'abcdeeeefghi'.match(/e{3}/)", String(["eee"]), String('abcdeeeefghi'.match(/e{3}/)));

// testing min repeat n {n,}
this.TestCase ( SECTION, "'abcdeeeefghi'.match(/e{3,}/)", String(["eeee"]), String('abcdeeeefghi'.match(/e{3,}/)));

// testing min/max repeat {min, max}
this.TestCase ( SECTION, "'abcdeeeefghi'.match(/e{2,8}/)", String(["eeee"]), String('abcdeeeefghi'.match(/e{2,8}/)));

// testing any in set [abc...]
this.TestCase ( SECTION, "'abcdefghi'.match(/cd[xey]fgh/)", String(["cdefgh"]), String('abcdefghi'.match(/cd[xey]fgh/)));

// testing any in set [a-z]
this.TestCase ( SECTION, "'netscape inc'.match(/t[r-v]ca/)", String(["tsca"]), String('netscape inc'.match(/t[r-v]ca/)));

// testing any not in set [^abc...]
this.TestCase ( SECTION, "'abcdefghi'.match(/cd[^xy]fgh/)", String(["cdefgh"]), String('abcdefghi'.match(/cd[^xy]fgh/)));

// testing any not in set [^a-z]
this.TestCase ( SECTION, "'netscape inc'.match(/t[^a-c]ca/)", String(["tsca"]), String('netscape inc'.match(/t[^a-c]ca/)));

// testing backspace [\b]
this.TestCase ( SECTION, "'this is b\ba test'.match(/is b[\b]a test/)",
String(["is b\ba test"]), String('this is b\ba test'.match(/is b[\b]a test/)));

// testing word boundary \b
this.TestCase ( SECTION, "'today is now - day is not now'.match(/\bday.*now/)",
String(["day is not now"]), String('today is now - day is not now'.match(/\bday.*now/)));

// control characters???

// testing any digit \d
this.TestCase ( SECTION, "'a dog - 1 dog'.match(/\d dog/)", String(["1 dog"]), String('a dog - 1 dog'.match(/\d dog/)));

// testing any non digit \d
this.TestCase ( SECTION, "'a dog - 1 dog'.match(/\D dog/)", String(["a dog"]), String('a dog - 1 dog'.match(/\D dog/)));

// testing form feed '\f'
this.TestCase ( SECTION, "'a b a\fb'.match(/a\fb/)", String(["a\fb"]), String('a b a\fb'.match(/a\fb/)));

// testing line feed '\n'
this.TestCase ( SECTION, "'a b a\nb'.match(/a\nb/)", String(["a\nb"]), String('a b a\nb'.match(/a\nb/)));

// testing carriage return '\r'
this.TestCase ( SECTION, "'a b a\rb'.match(/a\rb/)", String(["a\rb"]), String('a b a\rb'.match(/a\rb/)));

// testing whitespace '\s'
this.TestCase ( SECTION, "'xa\f\n\r\t\vbz'.match(/a\s+b/)", String(["a\f\n\r\t\vb"]), String('xa\f\n\r\t\vbz'.match(/a\s+b/)));

// testing non whitespace '\S'
this.TestCase ( SECTION, "'a\tb a b a-b'.match(/a\Sb/)", String(["a-b"]), String('a\tb a b a-b'.match(/a\Sb/)));

// testing tab '\t'
this.TestCase ( SECTION, "'a\t\tb a  b'.match(/a\t{2}/)", String(["a\t\t"]), String('a\t\tb a  b'.match(/a\t{2}/)));

// testing vertical tab '\v'
this.TestCase ( SECTION, "'a\v\vb a  b'.match(/a\v{2}/)", String(["a\v\v"]), String('a\v\vb a  b'.match(/a\v{2}/)));

// testing alphnumeric characters '\w'
this.TestCase ( SECTION, "'%AZaz09_$'.match(/\w+/)", String(["AZaz09_"]), String('%AZaz09_$'.match(/\w+/)));

// testing non alphnumeric characters '\W'
this.TestCase ( SECTION, "'azx$%#@*4534'.match(/\W+/)", String(["$%#@*"]), String('azx$%#@*4534'.match(/\W+/)));

// testing back references '\<number>'
this.TestCase ( SECTION, "'test'.match(/(t)es\\1/)", String(["test","t"]), String('test'.match(/(t)es\1/)));

// testing hex excaping with '\'
this.TestCase ( SECTION, "'abcdef'.match(/\x63\x64/)", String(["cd"]), String('abcdef'.match(/\x63\x64/)));

// testing oct excaping with '\'
this.TestCase ( SECTION, "'abcdef'.match(/\\143\\144/)", String(["cd"]), String('abcdef'.match(/\143\144/)));

//test();

},

/**
File Name:          string_replace.js
Description:  'Tests the replace method on Strings using regular expressions'

Author:       Nick Lerissa
Date:         March 11, 1998
*/
test_string_replace :function(){
var SECTION         =  "string_replace";
var VERSION = 'no version';
//startTest();
var TITLE   = 'String: replace';

//writeHeaderToLog('Executing script: string_replace.js');
//writeHeaderToLog( SECTION + " "+ TITLE);


// 'adddb'.replace(/ddd/,"XX")
this.TestCase ( SECTION, "'adddb'.replace(/ddd/,'XX')",
"aXXb", 'adddb'.replace(/ddd/,'XX'));

// 'adddb'.replace(/eee/,"XX")
this.TestCase ( SECTION, "'adddb'.replace(/eee/,'XX')",
'adddb', 'adddb'.replace(/eee/,'XX'));

// '34 56 78b 12'.replace(new RegExp('[0-9]+b'),'**')
this.TestCase ( SECTION, "'34 56 78b 12'.replace(new RegExp('[0-9]+b'),'**')",
"34 56 ** 12", '34 56 78b 12'.replace(new RegExp('[0-9]+b'),'**'));

// '34 56 78b 12'.replace(new RegExp('[0-9]+c'),'XX')
this.TestCase ( SECTION, "'34 56 78b 12'.replace(new RegExp('[0-9]+c'),'XX')",
"34 56 78b 12", '34 56 78b 12'.replace(new RegExp('[0-9]+c'),'XX'));

// 'original'.replace(new RegExp(),'XX')
this.TestCase ( SECTION, "'original'.replace(new RegExp(),'XX')",
"XXoriginal", 'original'.replace(new RegExp(),'XX'));

// 'qwe ert x\t\n 345654AB'.replace(new RegExp('x\s*\d+(..)$'),'****')
this.TestCase ( SECTION, "'qwe ert x\t\n 345654AB'.replace(new RegExp('x\\s*\\d+(..)$'),'****')",
"qwe ert ****", 'qwe ert x\t\n 345654AB'.replace(new RegExp('x\\s*\\d+(..)$'),'****'));


//test();

},

/**
File Name:          string_search.js
Description:  'Tests the search method on Strings using regular expressions'

Author:       Nick Lerissa
Date:         March 12, 1998
*/
test_string_search :function(){
var SECTION         =  "string_search";
var VERSION = 'no version';
//startTest();
var TITLE   = 'String: search';

//writeHeaderToLog('Executing script: string_search.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

// 'abcdefg'.search(/d/)
this.TestCase ( SECTION, "'abcdefg'.search(/d/)",
3, 'abcdefg'.search(/d/));

// 'abcdefg'.search(/x/)
this.TestCase ( SECTION, "'abcdefg'.search(/x/)",
-1, 'abcdefg'.search(/x/));

// 'abcdefg123456hijklmn'.search(/\d+/)
this.TestCase ( SECTION, "'abcdefg123456hijklmn'.search(/\d+/)",
7, 'abcdefg123456hijklmn'.search(/\d+/));

// 'abcdefg123456hijklmn'.search(new RegExp())
this.TestCase ( SECTION, "'abcdefg123456hijklmn'.search(new RegExp())",
0, 'abcdefg123456hijklmn'.search(new RegExp()));

// 'abc'.search(new RegExp('$'))
this.TestCase ( SECTION, "'abc'.search(new RegExp('$'))",
3, 'abc'.search(new RegExp('$')));

// 'abc'.search(new RegExp('^'))
this.TestCase ( SECTION, "'abc'.search(new RegExp('^'))",
0, 'abc'.search(new RegExp('^')));

// 'abc1'.search(/.\d/)
this.TestCase ( SECTION, "'abc1'.search(/.\d/)",
2, 'abc1'.search(/.\d/));

// 'abc1'.search(/\d{2}/)
this.TestCase ( SECTION, "'abc1'.search(/\d{2}/)",
-1, 'abc1'.search(/\d{2}/));

//test();

},

/**
File Name:          test.js
Description:  'Tests regular expressions method compile'

Author:       Nick Lerissa
Date:         March 10, 1998
*/
test_test :function(){
var SECTION         =  "test";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: test';

//writeHeaderToLog('Executing script: test.js');
//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase ( SECTION,
"/[0-9]{3}/.test('23 2 34 678 9 09')",
true, /[0-9]{3}/.test('23 2 34 678 9 09'));

this.TestCase ( SECTION,
"/[0-9]{3}/.test('23 2 34 78 9 09')",
false, /[0-9]{3}/.test('23 2 34 78 9 09'));

this.TestCase ( SECTION,
"/\w+ \w+ \w+/.test('do a test')",
true, /\w+ \w+ \w+/.test("do a test"));

this.TestCase ( SECTION,
"/\w+ \w+ \w+/.test('a test')",
false, /\w+ \w+ \w+/.test("a test"));

this.TestCase ( SECTION,
"(new RegExp('[0-9]{3}')).test('23 2 34 678 9 09')",
true, (new RegExp('[0-9]{3}')).test('23 2 34 678 9 09'));

this.TestCase ( SECTION,
"(new RegExp('[0-9]{3}')).test('23 2 34 78 9 09')",
false, (new RegExp('[0-9]{3}')).test('23 2 34 78 9 09'));

this.TestCase ( SECTION,
"(new RegExp('\\\\w+ \\\\w+ \\\\w+')).test('do a test')",
true, (new RegExp('\\w+ \\w+ \\w+')).test("do a test"));

this.TestCase ( SECTION,
"(new RegExp('\\\\w+ \\\\w+ \\\\w+')).test('a test')",
false, (new RegExp('\\w+ \\w+ \\w+')).test("a test"));

//test();

},

/**
File Name:          toString.js
Description:  'Tests RegExp method toString'

Author:       Nick Lerissa
Date:         March 13, 1998
*/
test_toString :function(){
var SECTION         =  "toString";
var VERSION = 'no version';
//startTest();
var TITLE = 'RegExp: toString';

//writeHeaderToLog('Executing script: toString.js');
//writeHeaderToLog( SECTION + " "+ TITLE);


/*
* var re = new RegExp(); re.toString()  For what to expect,
* see http://bugzilla.mozilla.org/show_bug.cgi?id=225343#c7
*/
var re = new RegExp();
this.TestCase ( SECTION, "var re = new RegExp(); re.toString()",
'/(?:)/', re.toString());

// re = /.+/; re.toString();
re = /.+/;
this.TestCase ( SECTION, "re = /.+/; re.toString()",
'/.+/', re.toString());

// re = /test/gi; re.toString()
re = /test/gi;
this.TestCase ( SECTION, "re = /test/gi; re.toString()",
'/test/gi', re.toString());

// re = /test2/ig; re.toString()
re = /test2/ig;
this.TestCase ( SECTION, "re = /test2/ig; re.toString()",
'/test2/gi', re.toString());

//test();

},

/**
File Name:          vertical_bar.js
Description:  'Tests regular expressions containing |'

Author:       Nick Lerissa
Date:         March 10, 1998
*/
test_vertical_bar :function(){
var SECTION         =  "vertical_bar";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: |';

//writeHeaderToLog('Executing script: vertical_bar.js');
//writeHeaderToLog( SECTION + " "+ TITLE);


// 'abc'.match(new RegExp('xyz|abc'))
this.TestCase ( SECTION, "'abc'.match(new RegExp('xyz|abc'))",
String(["abc"]), String('abc'.match(new RegExp('xyz|abc'))));

// 'this is a test'.match(new RegExp('quiz|exam|test|homework'))
this.TestCase ( SECTION, "'this is a test'.match(new RegExp('quiz|exam|test|homework'))",
String(["test"]), String('this is a test'.match(new RegExp('quiz|exam|test|homework'))));

// 'abc'.match(new RegExp('xyz|...'))
this.TestCase ( SECTION, "'abc'.match(new RegExp('xyz|...'))",
String(["abc"]), String('abc'.match(new RegExp('xyz|...'))));

// 'abc'.match(new RegExp('(.)..|abc'))
this.TestCase ( SECTION, "'abc'.match(new RegExp('(.)..|abc'))",
String(["abc","a"]), String('abc'.match(new RegExp('(.)..|abc'))));

// 'color: grey'.match(new RegExp('.+: gr(a|e)y'))
this.TestCase ( SECTION, "'color: grey'.match(new RegExp('.+: gr(a|e)y'))",
String(["color: grey","e"]), String('color: grey'.match(new RegExp('.+: gr(a|e)y'))));

// 'no match'.match(new RegExp('red|white|blue'))
this.TestCase ( SECTION, "'no match'.match(new RegExp('red|white|blue'))",
null, 'no match'.match(new RegExp('red|white|blue')));

// 'Hi Bob'.match(new RegExp('(Rob)|(Bob)|(Robert)|(Bobby)'))
this.TestCase ( SECTION, "'Hi Bob'.match(new RegExp('(Rob)|(Bob)|(Robert)|(Bobby)'))",
String(["Bob",undefined,"Bob", undefined, undefined]), String('Hi Bob'.match(new RegExp('(Rob)|(Bob)|(Robert)|(Bobby)'))));

// 'abcdef'.match(new RegExp('abc|bcd|cde|def'))
this.TestCase ( SECTION, "'abcdef'.match(new RegExp('abc|bcd|cde|def'))",
String(["abc"]), String('abcdef'.match(new RegExp('abc|bcd|cde|def'))));

// 'Hi Bob'.match(/(Rob)|(Bob)|(Robert)|(Bobby)/)
this.TestCase ( SECTION, "'Hi Bob'.match(/(Rob)|(Bob)|(Robert)|(Bobby)/)",
String(["Bob",undefined,"Bob", undefined, undefined]), String('Hi Bob'.match(/(Rob)|(Bob)|(Robert)|(Bobby)/)));

// 'abcdef'.match(/abc|bcd|cde|def/)
this.TestCase ( SECTION, "'abcdef'.match(/abc|bcd|cde|def/)",
String(["abc"]), String('abcdef'.match(/abc|bcd|cde|def/)));

//test();

},

/**
File Name:          whitespace.js
Description:  'Tests regular expressions containing \f\n\r\t\v\s\S\ '

Author:       Nick Lerissa
Date:         March 10, 1998
*/
test_whitespace :function(){
var SECTION         =  "whitespace";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: \\f\\n\\r\\t\\v\\s\\S ';

//writeHeaderToLog('Executing script: whitespace.js');
//writeHeaderToLog( SECTION + " "+ TITLE);


var non_whitespace = "_abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ~`!@#$%^&*()-+={[}]|\\:;'<,>./?1234567890" + '"';
var whitespace     = "\f\n\r\t\v ";

// be sure all whitespace is matched by \s
this.TestCase ( SECTION,
"'" + whitespace + "'.match(new RegExp('\\s+'))",
String([whitespace]), String(whitespace.match(new RegExp('\\s+'))));

// be sure all non-whitespace is matched by \S
this.TestCase ( SECTION,
"'" + non_whitespace + "'.match(new RegExp('\\S+'))",
String([non_whitespace]), String(non_whitespace.match(new RegExp('\\S+'))));

// be sure all non-whitespace is not matched by \s
this.TestCase ( SECTION,
"'" + non_whitespace + "'.match(new RegExp('\\s'))",
null, non_whitespace.match(new RegExp('\\s')));

// be sure all whitespace is not matched by \S
this.TestCase ( SECTION,
"'" + whitespace + "'.match(new RegExp('\\S'))",
null, whitespace.match(new RegExp('\\S')));

var s = non_whitespace + whitespace;

// be sure all digits are matched by \s
this.TestCase ( SECTION,
"'" + s + "'.match(new RegExp('\\s+'))",
String([whitespace]), String(s.match(new RegExp('\\s+'))));

s = whitespace + non_whitespace;

// be sure all non-whitespace are matched by \S
this.TestCase ( SECTION,
"'" + s + "'.match(new RegExp('\\S+'))",
String([non_whitespace]), String(s.match(new RegExp('\\S+'))));

// '1233345find me345'.match(new RegExp('[a-z\\s][a-z\\s]+'))
this.TestCase ( SECTION, "'1233345find me345'.match(new RegExp('[a-z\\s][a-z\\s]+'))",
String(["find me"]), String('1233345find me345'.match(new RegExp('[a-z\\s][a-z\\s]+'))));

var i;

// be sure all whitespace characters match individually
for (i = 0; i < whitespace.length; ++i)
{
s = 'ab' + whitespace[i] + 'cd';
this.TestCase ( SECTION,
"'" + s + "'.match(new RegExp('\\\\s'))",
String([whitespace[i]]), String(s.match(new RegExp('\\s'))));
this.TestCase ( SECTION,
"'" + s + "'.match(/\s/)",
String([whitespace[i]]), String(s.match(/\s/)));
}
// be sure all non_whitespace characters match individually
for (i = 0; i < non_whitespace.length; ++i)
{
s = '  ' + non_whitespace[i] + '  ';
this.TestCase ( SECTION,
"'" + s + "'.match(new RegExp('\\\\S'))",
String([non_whitespace[i]]), String(s.match(new RegExp('\\S'))));
this.TestCase ( SECTION,
"'" + s + "'.match(/\S/)",
String([non_whitespace[i]]), String(s.match(/\S/)));
}


//test();

},

/**
File Name:          word_boundary.js
Description:  'Tests regular expressions containing \b and \B'

Author:       Nick Lerissa
Date:         March 10, 1998
*/
test_word_boundary :function(){
var SECTION         =  "word_boundary";
var VERSION = 'no version';
//startTest();
var TITLE   = 'RegExp: \\b and \\B';

//writeHeaderToLog('Executing script: word_boundary.js');
//writeHeaderToLog( SECTION + " "+ TITLE);


// 'cowboy boyish boy'.match(new RegExp('\bboy\b'))
this.TestCase ( SECTION, "'cowboy boyish boy'.match(new RegExp('\\bboy\\b'))",
String(["boy"]), String('cowboy boyish boy'.match(new RegExp('\\bboy\\b'))));

var boundary_characters = "\f\n\r\t\v~`!@#$%^&*()-+={[}]|\\:;'<,>./? " + '"';
var non_boundary_characters = '1234567890_abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
var s     = '';
var i;

// testing whether all boundary characters are matched when they should be
for (i = 0; i < boundary_characters.length; ++i)
{
s = '123ab' + boundary_characters.charAt(i) + '123c' + boundary_characters.charAt(i);

this.TestCase ( SECTION,
"'" + s + "'.match(new RegExp('\\b123[a-z]\\b'))",
String(["123c"]), String(s.match(new RegExp('\\b123[a-z]\\b'))));
}

// testing whether all non-boundary characters are matched when they should be
for (i = 0; i < non_boundary_characters.length; ++i)
{
s = '123ab' + non_boundary_characters.charAt(i) + '123c' + non_boundary_characters.charAt(i);

this.TestCase ( SECTION,
"'" + s + "'.match(new RegExp('\\B123[a-z]\\B'))",
String(["123c"]), String(s.match(new RegExp('\\B123[a-z]\\B'))));
}

s = '';

// testing whether all boundary characters are not matched when they should not be
for (i = 0; i < boundary_characters.length; ++i)
{
s += boundary_characters[i] + "a" + i + "b";
}
s += "xa1111bx";

this.TestCase ( SECTION,
"'" + s + "'.match(new RegExp('\\Ba\\d+b\\B'))",
String(["a1111b"]), String(s.match(new RegExp('\\Ba\\d+b\\B'))));

this.TestCase ( SECTION,
"'" + s + "'.match(/\\Ba\\d+b\\B/)",
String(["a1111b"]), String(s.match(/\Ba\d+b\B/)));

s = '';

// testing whether all non-boundary characters are not matched when they should not be
for (i = 0; i < non_boundary_characters.length; ++i)
{
s += non_boundary_characters[i] + "a" + i + "b";
}
s += "(a1111b)";

this.TestCase ( SECTION,
"'" + s + "'.match(new RegExp('\\ba\\d+b\\b'))",
String(["a1111b"]), String(s.match(new RegExp('\\ba\\d+b\\b'))));

this.TestCase ( SECTION,
"'" + s + "'.match(/\\ba\\d+b\\b/)",
String(["a1111b"]), String(s.match(/\ba\d+b\b/)));

//test();

}


})
.endType();
