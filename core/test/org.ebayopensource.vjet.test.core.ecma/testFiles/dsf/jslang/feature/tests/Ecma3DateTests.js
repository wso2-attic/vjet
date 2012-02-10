vjo.ctype("dsf.jslang.feature.tests.Ecma3DateTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({

constructs: function() {
this.base();
},

inSection : function(x) {
var SECT_PREFIX = 'Section ';
var SECT_SUFFIX = ' of test - ';
return SECT_PREFIX + x + SECT_SUFFIX;
},

now:function () {
var createDateTime = null;
var calendarDefaultTimezone = null;
var d = createDateTime();
d.jsDate = new Date();
return d.getInTimezone(calendarDefaultTimezone());
},

/** @Test
File Name:         15_9_1_2__01.js
Description:        15.9.1.2 - TimeWithinDay(TIME_1900) == 0.
*/
test_15_9_1_2__01: function () {
var SECTION = "15_9_1_2__01.js";
var TIME_1900 = -2208988800000;
var msPerDay = 86400000;
//var BUGNUMBER = 264727;
//var summary = '15.9.1.2 - TimeWithinDay(TIME_1900) == 0';
var actual = '';
var expect = '';

//test();

//function test() {
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

expect = 0;
actual = TimeWithinDay(TIME_1900);
var assertEquals = null;
assertEquals(expect, actual);
this.TestCase(SECTION, expect, expect, actual);
//exitFunc ('test');
//}

function TimeWithinDay( t ) {
var r = t % msPerDay;

if (r < 0) {
r += msPerDay;
}
return r;
}
},

/** @Test
File Name:         15_9_3_2__1.js
Description:        15.9.3.2  new Date(value).
*/
test_15_9_3_2__1: function () {
var SECT_PREFIX = 'Section ';
var SECT_SUFFIX = ' of test - ';
var SECTION = "15_9_3_2__1.js";
//var BUGNUMBER = 273292;
//var summary = '15.9.3.2  new Date(value)';
var actual = '';
var expect = '';
var date1;
var date2;
var i;
var validDateStrings = [
"11/69/2004",
"11/70/2004",
"69/69/2004",
"69/69/69",
"69/69/1969",
"70/69/70",
"70/69/1970",
"70/69/2004"
];

var invalidDateStrings = [
"70/70/70",
"70/70/1970",
"70/70/2004"
];

//printBugNumber(BUGNUMBER);
//printStatus (summary);

expect = 0;

for (i = 0; i < validDateStrings.length; i++) {
date1 = new Date(validDateStrings[i]);
date2 = new Date(date1.toDateString());
actual = date2 - date1;
var assertEquals = null;
assertEquals(expect, actual);
this.TestCase(SECTION, this.inSection(i) + ' ' + validDateStrings[i], expect, actual);
}

expect = true;

var offset = validDateStrings.length;

for (i = 0; i < invalidDateStrings.length; i++) {
date1 = new Date(invalidDateStrings[i]);
actual = isNaN(date1);

this.TestCase(SECTION, this.inSection(i + offset) + ' ' + invalidDateStrings[i] + ' is invalid.', expect, actual);
}

},

/** @Test
File Name:         15_9_4_3.js
Description:       15.9.4.3 - Date.UTC edge-case arguments.
*/
test_15_9_4_3: function () {
var SECTION = "15_9_4_3.js";
//var BUGNUMBER = 363578;
var summary = '15.9.4.3 - Date.UTC edge-case arguments.';
var actual = '';
var expect = '';

//test();

//function test()
// {
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

expect = 31;
actual = (new Date(Date.UTC(2006, 0, 0)).getUTCDate());
this.TestCase(SECTION, summary + ': date 0', expect, actual);

expect = 0;
actual = (new Date(Date.UTC(2006, 0, 0, 0)).getUTCHours());
this.TestCase(SECTION, summary + ': hours 0', expect, actual);

expect = 0;
actual = (new Date(Date.UTC(2006, 0, 0, 0, 0)).getUTCMinutes());
this.TestCase(SECTION, summary + ': minutes 0', expect, actual);

expect = 0;
actual = (new Date(Date.UTC(2006, 0, 0, 0, 0, 0)).getUTCSeconds());
this.TestCase(SECTION, summary + ': seconds 0', expect, actual);

expect = 0;
actual = (new Date(Date.UTC(2006, 0, 0, 0, 0, 0, 0)).getUTCMilliseconds());
this.TestCase(SECTION, summary + ': milliseconds 0', expect, actual);

expect = 30;
actual = (new Date(Date.UTC(2006, 0, -1)).getUTCDate());
this.TestCase(SECTION, summary + ': date -1', expect, actual);

expect = 23;
actual = (new Date(Date.UTC(2006, 0, 0, -1)).getUTCHours());
this.TestCase(SECTION, summary + ': hours -1', expect, actual);

expect = 59;
actual = (new Date(Date.UTC(2006, 0, 0, 0, -1)).getUTCMinutes());
this.TestCase(SECTION, summary + ': minutes -1', expect, actual);

expect = 59;
actual = (new Date(Date.UTC(2006, 0, 0, 0, 0, -1)).getUTCSeconds());
this.TestCase(SECTION, summary + ': seconds -1', expect, actual);

expect = 999;
actual = (new Date(Date.UTC(2006, 0, 0, 0, 0, 0, -1)).getUTCMilliseconds());
this.TestCase(SECTION, summary + ': milliseconds -1', expect, actual);

expect = true;
actual = isNaN(new Date(Date.UTC(2006, 0, undefined)).getUTCDate());
this.TestCase(SECTION, summary + ': date undefined', expect, actual);

expect = true;
actual = isNaN(new Date(Date.UTC(2006, 0, 0, undefined)).getUTCHours());
this.TestCase(SECTION, summary + ': hours undefined', expect, actual);

expect = true;
actual = isNaN(new Date(Date.UTC(2006, 0, 0, 0, undefined)).getUTCMinutes());
this.TestCase(SECTION, summary + ': minutes undefined', expect, actual);

expect = true;
actual = isNaN(new Date(Date.UTC(2006, 0, 0, 0, 0, undefined)).getUTCSeconds());
this.TestCase(SECTION, summary + ': seconds undefined', expect, actual);

expect = true;
actual = isNaN(new Date(Date.UTC(2006, 0, 0, 0, 0, 0, undefined)).getUTCMilliseconds());
this.TestCase(SECTION, summary + ': milliseconds undefined', expect, actual);

expect = true;
actual = isNaN(new Date(Date.UTC(2006, 0, {})).getUTCDate());
this.TestCase(SECTION, summary + ': date {}', expect, actual);

expect = true;
actual = isNaN(new Date(Date.UTC(2006, 0, 0, {})).getUTCHours());
this.TestCase(SECTION, summary + ': hours {}', expect, actual);

expect = true;
actual = isNaN(new Date(Date.UTC(2006, 0, 0, 0, {})).getUTCMinutes());
this.TestCase(SECTION, summary + ': minutes {}', expect, actual);

expect = true;
actual = isNaN(new Date(Date.UTC(2006, 0, 0, 0, 0, {})).getUTCSeconds());
this.TestCase(SECTION, summary + ': seconds {}', expect, actual);

expect = true;
actual = isNaN(new Date(Date.UTC(2006, 0, 0, 0, 0, 0, {})).getUTCMilliseconds());
this.TestCase(SECTION, summary + ': milliseconds {}', expect, actual);

expect = 31;
actual = (new Date(Date.UTC(2006, 0, null)).getUTCDate());
this.TestCase(SECTION, summary + ': date null', expect, actual);

expect = 0;
actual = (new Date(Date.UTC(2006, 0, 0, null)).getUTCHours());
this.TestCase(SECTION, summary + ': hours null', expect, actual);

expect = 0;
actual = (new Date(Date.UTC(2006, 0, 0, 0, null)).getUTCMinutes());
this.TestCase(SECTION, summary + ': minutes null', expect, actual);

expect = 0;
actual = (new Date(Date.UTC(2006, 0, 0, 0, 0, null)).getUTCSeconds());
this.TestCase(SECTION, summary + ': seconds null', expect, actual);

expect = 0;
actual = (new Date(Date.UTC(2006, 0, 0, 0, 0, 0, null)).getUTCMilliseconds());
this.TestCase(SECTION, summary + ': milliseconds null', expect, actual);

expect = true;
actual = isNaN(new Date(Date.UTC(2006, 0, Infinity)).getUTCDate());
this.TestCase(SECTION, summary + ': date Infinity', expect, actual);

expect = true;
actual = isNaN(new Date(Date.UTC(2006, 0, 0, Infinity)).getUTCHours());
this.TestCase(SECTION, summary + ': hours Infinity', expect, actual);

expect = true;
actual = isNaN(new Date(Date.UTC(2006, 0, 0, 0, Infinity)).getUTCMinutes());
this.TestCase(SECTION, summary + ': minutes Infinity', expect, actual);

expect = true;
actual = isNaN(new Date(Date.UTC(2006, 0, 0, 0, 0, Infinity)).getUTCSeconds());
this.TestCase(SECTION, summary + ': seconds Infinity', expect, actual);

expect = true;
actual = isNaN(new Date(Date.UTC(2006, 0, 0, 0, 0, 0, Infinity)).getUTCMilliseconds());
this.TestCase(SECTION, summary + ': milliseconds Infinity', expect, actual);

expect = true;
actual = isNaN(new Date(Date.UTC(2006, 0, -Infinity)).getUTCDate());
this.TestCase(SECTION, summary + ': date -Infinity', expect, actual);

expect = true;
actual = isNaN(new Date(Date.UTC(2006, 0, 0, -Infinity)).getUTCHours());
this.TestCase(SECTION, summary + ': hours -Infinity', expect, actual);

expect = true;
actual = isNaN(new Date(Date.UTC(2006, 0, 0, 0, -Infinity)).getUTCMinutes());
this.TestCase(SECTION, summary + ': minutes -Infinity', expect, actual);

expect = true;
actual = isNaN(new Date(Date.UTC(2006, 0, 0, 0, 0, -Infinity)).getUTCSeconds());
this.TestCase(SECTION, summary + ': seconds -Infinity', expect, actual);

expect = true;
actual = isNaN(new Date(Date.UTC(2006, 0, 0, 0, 0, 0, -Infinity)).getUTCMilliseconds());
this.TestCase(SECTION, summary + ': milliseconds -Infinity', expect, actual);

expect = true;
actual = isNaN(new Date(Date.UTC(2006, 0, NaN)).getUTCDate());
this.TestCase(SECTION, summary + ': date NaN', expect, actual);

expect = true;
actual = isNaN(new Date(Date.UTC(2006, 0, 0, NaN)).getUTCHours());
this.TestCase(SECTION, summary + ': hours NaN', expect, actual);

expect = true;
actual = isNaN(new Date(Date.UTC(2006, 0, 0, 0, NaN)).getUTCMinutes());
this.TestCase(SECTION, summary + ': minutes NaN', expect, actual);

expect = true;
actual = isNaN(new Date(Date.UTC(2006, 0, 0, 0, 0, NaN)).getUTCSeconds());
this.TestCase(SECTION, summary + ': seconds NaN', expect, actual);

expect = true;
actual = isNaN(new Date(Date.UTC(2006, 0, 0, 0, 0, 0, NaN)).getUTCMilliseconds());
this.TestCase(SECTION, summary + ': milliseconds NaN', expect, actual);

//exitFunc ('test');
//}
},

/** @Test
File Name:          15_9_5_3.js
ECMA Section: 15.9.5.3 Date.prototype.toDateString()
Description:
This function returns a string value. The contents of the string are
implementation dependent, but are intended to represent the "date"
portion of the Date in the current time zone in a convenient,
human-readable form.   We can't test the content of the string,
but can verify that the string is parsable by Date.parse

The toDateString function is not generic; it generates a runtime error
if its 'this' value is not a Date object. Therefore it cannot be transferred
to other kinds of objects for use as a method.

Author:  pschwartau@netscape.com
Date:      14 november 2000  (adapted from ecma/Date/15.9.5.2.js)
*/
test_15_9_5_3: function () {
var SECTION = "15_9_5_3.js";
//var VERSION = "ECMA_3";
//var TITLE   = "Date.prototype.toDateString()";

var status = '';
var actual = '';
var expect = '';

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

//first, some generic tests -
status = "typeof (this.now.toDateString())";
actual =   typeof (this.now.toDateString());
expect = "string";
addTestCase();

status = "Date.prototype.toDateString.length";
actual =  Date.prototype.toDateString.length;
expect =  0;
addTestCase();

/*
* Date.parse is accurate to the second; valueOf() to the millisecond.
* Here we expect them to coincide, as we expect a time of exactly
* midnight -
*/
status = "(Date.parse(this.now.toDateString()) - (midnight(this.now)).valueOf()) == 0";
actual =   (Date.parse(this.now.toDateString()) - (midnight(this.now)).valueOf()) == 0;
expect = true;
addTestCase();
var TZ_ADJUST;
var TIME_1900;
var TIME_2000;
var UTC_29_FEB_2000;
var UTC_1_JAN_2005;
// 1970
addDateTestCase(0);
addDateTestCase(TZ_ADJUST);

// 1900
addDateTestCase(TIME_1900);
addDateTestCase(TIME_1900 - TZ_ADJUST);

// 2000
addDateTestCase(TIME_2000);
addDateTestCase(TIME_2000 - TZ_ADJUST);

// 29 Feb 2000
addDateTestCase(UTC_29_FEB_2000);
addDateTestCase(UTC_29_FEB_2000 - 1000);
addDateTestCase(UTC_29_FEB_2000 - TZ_ADJUST);

// 2005
addDateTestCase(UTC_1_JAN_2005);
addDateTestCase(UTC_1_JAN_2005 - 1000);
addDateTestCase(UTC_1_JAN_2005 - TZ_ADJUST);

//test();

function addTestCase() {
this.TestCase(
SECTION,
status,
expect,
actual);
}

function addDateTestCase(date_given_in_milliseconds) {
var givenDate = new Date(date_given_in_milliseconds);

status = 'Date.parse('   +   givenDate   +   ').toDateString())';
actual =  Date.parse(givenDate.toDateString());
expect = Date.parse(midnight(givenDate));
addTestCase();
}

function midnight(givenDate) {
// midnight on the given date -
return new Date(givenDate.getFullYear(), givenDate.getMonth(), givenDate.getDate());
}
},

/** @Test
File Name:    15_9_5_4.js
ECMA Section: 15.9.5.4 Date.prototype.toTimeString()
Description:
This function returns a string value. The contents of the string are
implementation dependent, but are intended to represent the "time"
portion of the Date in the current time zone in a convenient,
human-readable form.   We test the content of the string by checking
that d.toDateString()  +  d.toTimeString()  ==  d.toString()

Author:  pschwartau@netscape.com
Date:    14 november 2000
Revised: 07 january 2002  because of a change in JS Date format:

See http://bugzilla.mozilla.org/show_bug.cgi?id=118266 (SpiderMonkey)
See http://bugzilla.mozilla.org/show_bug.cgi?id=118636 (Rhino)
*/
test_15_9_5_4: function () {
var SECT_PREFIX = 'Section ';
//var VERSION = "ECMA_3";
//var TITLE   = "Date.prototype.toTimeString()";

var status = '';
var actual = '';
var expect = '';
var givenDate;
var year = '';
var regexp = '';
var reducedDateString = '';
var hopeThisIsTimeString = '';
var cnEmptyString = '';
var cnERR ='OOPS! FATAL ERROR: no regexp match in extractTimeString()';

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

// first, a couple of generic tests -
status = "typeof (this.now.toTimeString())";
actual =   typeof (this.now.toTimeString());
expect = "string";
addTestCase();

status = "Date.prototype.toTimeString.length";
actual =  Date.prototype.toTimeString.length;
expect =  0;
addTestCase();
var TZ_ADJUST = null;
var TIME_1900  = null;
var TIME_2000 = null;
var UTC_29_FEB_2000 = null;
var TIME_NOW = null;
var UTC_1_JAN_2005 = null;
// 1970
addDateTestCase(0);
addDateTestCase(TZ_ADJUST);

// 1900
addDateTestCase(TIME_1900);
addDateTestCase(TIME_1900 - TZ_ADJUST);

// 2000
addDateTestCase(TIME_2000);
addDateTestCase(TIME_2000 - TZ_ADJUST);

// 29 Feb 2000
addDateTestCase(UTC_29_FEB_2000);
addDateTestCase(UTC_29_FEB_2000 - 1000);
addDateTestCase(UTC_29_FEB_2000 - TZ_ADJUST);

// Now
addDateTestCase( TIME_NOW);
addDateTestCase( TIME_NOW - TZ_ADJUST);

// 2005
addDateTestCase(UTC_1_JAN_2005);
addDateTestCase(UTC_1_JAN_2005 - 1000);
addDateTestCase(UTC_1_JAN_2005 - TZ_ADJUST);

//test();
var SECTION = null;
function addTestCase() {
this.TestCase(
SECTION,
status,
expect,
actual);
}

function addDateTestCase(date_given_in_milliseconds) {
givenDate = new Date(date_given_in_milliseconds);

status = '('  +  givenDate  +  ').toTimeString()';
actual = givenDate.toTimeString();
expect = extractTimeString(givenDate);
addTestCase();
}

/*
* As of 2002-01-07, the format for JavaScript dates changed.
* See http://bugzilla.mozilla.org/show_bug.cgi?id=118266 (SpiderMonkey)
* See http://bugzilla.mozilla.org/show_bug.cgi?id=118636 (Rhino)

* WAS: Mon Jan 07 13:40:34 GMT-0800 (Pacific Standard Time) 2002
* NOW: Mon Jan 07 2002 13:40:34 GMT-0800 (Pacific Standard Time)
*
* Thus, use a regexp of the form /date.toDateString()(.*)$/
* to capture the TimeString into the first backreference -
*/

function extractTimeString(date) {
regexp = new RegExp(date.toDateString() + '(.*)' + '$');

try {
hopeThisIsTimeString = date.toString().match(regexp)[1];
} catch(e) {
return cnERR;
}

// trim any leading or trailing spaces -
return trimL(trimR(hopeThisIsTimeString));
}

function trimL(s) {
if (!s) {return cnEmptyString;};
for (var i = 0; i!=s.length; i++) {if (s[i] != ' ') {break;}}
return s.substring(i);
}

function trimR(s) {
if (!s) {return cnEmptyString;};
for (var i = (s.length - 1); i!=-1; i--) {if (s[i] != ' ') {break;}}
return s.substring(0, i+1);
}

},

/** @Test
File Name:         15_9_5_5__02.js
Description:       Date.prototype.toLocaleString should not clamp year.
*/
test_15_9_5_5__02: function () {
var SECTION = "15_9_5_5__02.js";
//var BUGNUMBER = 398485;
var summary = 'Date.prototype.toLocaleString should not clamp year';
var actual = '';
var expect = '';

//test();

//function test() {
//enterFunc ('test');
//printBugNumber(BUGNUMBER);
//printStatus (summary);

var d;
var y;
var l;
var maxms = 8640000000000000;

d = new Date(-maxms );
y = d.getFullYear();
l = d.toLocaleString();
//print(l);

actual = y;
expect = -271821;
this.TestCase(SECTION, summary + ': check year', expect, actual);

actual = l.match(new RegExp(y)) + '';
expect = y + '';
this.TestCase(SECTION, summary + ': check toLocaleString', expect, actual);

d = new Date(maxms );
y = d.getFullYear();
l = d.toLocaleString();
//print(l);

actual = y;
expect = 275760;
this.TestCase(SECTION, summary + ': check year', expect, actual);

actual = l.match(new RegExp(y)) + '';
expect = y + '';
this.TestCase(SECTION, summary + ': check toLocaleString', expect, actual);

//exitFunc ('test');
//}
},

/** @Test
File Name:         15_9_5_5.js
ECMA Section: 15.9.5.5 Date.prototype.toLocaleString()
Description:
This function returns a string value. The contents of the string are
implementation dependent, but are intended to represent the "date"
portion of the Date in the current time zone in a convenient,
human-readable form.   We can't test the content of the string,
but can verify that the string is parsable by Date.parse

The toLocaleString function is not generic; it generates a runtime error
if its 'this' value is not a Date object. Therefore it cannot be transferred
to other kinds of objects for use as a method.

Note: This test isn't supposed to work with a non-English locale per spec.

Author:  pschwartau@netscape.com
Date:      14 november 2000
*/
test_15_9_5_5: function () {
var SECTION = "15_9_5_5.js";
//var VERSION = "ECMA_3";
//var TITLE   = "Date.prototype.toLocaleString()";

var msPerHour =   3600000;
var msPerDay =   86400000;
var TZ_DIFF = this.getTimeZoneDiff();
var TZ_ADJUST =  TZ_DIFF * msPerHour;
var TIME_2000  = 946684800000;
var TIME_1900  = -2208988800000;
var UTC_29_FEB_2000 = TIME_2000 + 31*msPerDay + 28*msPerDay;
var UTC_1_JAN_2005 = TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001)
+ this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);


var status = '';
var actual = '';
var expect = '';

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

// first, some generic tests -
status = "typeof (this.now.toLocaleString())";
actual =   typeof (this.now.toLocaleString());
expect = "string";
this.addTestCase(SECTION,status,expect,actual);
//addTestCase();

status = "Date.prototype.toLocaleString.length";
actual =  Date.prototype.toLocaleString.length;
expect =  0;
this.addTestCase(SECTION,status,expect,actual);
//addTestCase();

// Date.parse is accurate to the second;  valueOf() to the millisecond  -
status = "Math.abs(Date.parse(this.now.toLocaleString()) - this.now.valueOf()) < 1000";
actual =   Math.abs(Date.parse(this.now.toLocaleString()) -  this.now.valueOf()) < 1000;
expect = true;
this.addTestCase(SECTION,status,expect,actual);
//addTestCase();

// 1970
this.addDateTestCase(SECTION,0);
this.addDateTestCase(SECTION,TZ_ADJUST);

// 1900
this.addDateTestCase(SECTION,TIME_1900);
this.addDateTestCase(SECTION,TIME_1900 -TZ_ADJUST);

// 2000
this.addDateTestCase(SECTION,TIME_2000);
this.addDateTestCase(SECTION,TIME_2000 -TZ_ADJUST);

// 29 Feb 2000
this.addDateTestCase(SECTION,UTC_29_FEB_2000);
this.addDateTestCase(SECTION,UTC_29_FEB_2000 - 1000);
this.addDateTestCase(SECTION,UTC_29_FEB_2000 - TZ_ADJUST);

// 2005
this.addDateTestCase(SECTION,UTC_1_JAN_2005);
this.addDateTestCase(SECTION,UTC_1_JAN_2005 - 1000);
this.addDateTestCase(SECTION,UTC_1_JAN_2005-TZ_ADJUST);

//test();
},

/** @Test
File Name:         15_9_5_6.js
ECMA Section: 15.9.5.6 Date.prototype.toLocaleDateString()
Description:
This function returns a string value. The contents of the string are
implementation dependent, but are intended to represent the "date"
portion of the Date in the current time zone in a convenient,
human-readable form.   We can't test the content of the string,
but can verify that the string is parsable by Date.parse

The toLocaleDateString function is not generic; it generates a runtime error
if its 'this' value is not a Date object. Therefore it cannot be transferred
to other kinds of objects for use as a method.

Note: This test isn't supposed to work with a non-English locale per spec.

Author:  pschwartau@netscape.com
Date:      14 november 2000
*/
test_15_9_5_6: function () {
var msPerHour =   3600000;
var msPerDay =   86400000;
var TZ_DIFF = this.getTimeZoneDiff();
var TZ_ADJUST =  TZ_DIFF * msPerHour;
var TIME_2000  = 946684800000;
var TIME_1900  = -2208988800000;
var UTC_29_FEB_2000 = TIME_2000 + 31*msPerDay + 28*msPerDay;
var UTC_1_JAN_2005 = TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001)
+ this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);

var SECTION = "15_9_5_6.js";
//var VERSION = "ECMA_3";
//var TITLE   = "Date.prototype.toLocaleDateString()";

var status = '';
var actual = '';
var expect = '';


//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

// first, some generic tests -

status = "typeof (this.now.toLocaleDateString())";
actual =   typeof (this.now.toLocaleDateString());
expect = "string";
this.addTestCase(SECTION,status,expect,actual);

status = "Date.prototype.toLocaleDateString.length";
actual =  Date.prototype.toLocaleDateString.length;
expect =  0;
this.addTestCase(SECTION,status,expect,actual);

/* Date.parse is accurate to the second;  valueOf() to the millisecond.
Here we expect them to coincide, as we expect a time of exactly midnight -  */
status = "(Date.parse(this.now.toLocaleDateString()) - (midnight(this.now)).valueOf()) == 0";
actual =   (Date.parse(this.now.toLocaleDateString()) - (midnight(this.now)).valueOf()) == 0;
expect = true;
this.addTestCase(SECTION,status,expect,actual);



// 1970
this.addDateTestCase(0, 0);
this.addDateTestCase(SECTION, TZ_ADJUST);


// 1900
this.addDateTestCase(SECTION, TIME_1900);
this.addDateTestCase(SECTION, TIME_1900 - TZ_ADJUST);


// 2000
this.addDateTestCase(SECTION, TIME_2000);
this.addDateTestCase(SECTION, TIME_2000 - TZ_ADJUST);


// 29 Feb 2000
this.addDateTestCase(SECTION, UTC_29_FEB_2000);
this.addDateTestCase(SECTION, UTC_29_FEB_2000 - 1000);
this.addDateTestCase(SECTION, UTC_29_FEB_2000 - TZ_ADJUST);


// 2005
this.addDateTestCase(SECTION, UTC_1_JAN_2005);
this.addDateTestCase(SECTION, UTC_1_JAN_2005 - 1000);
this.addDateTestCase(SECTION, UTC_1_JAN_2005 - TZ_ADJUST);



//-----------------------------------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------------------------------

function midnight(givenDate)
{
// midnight on the given date -
return new Date(givenDate.getFullYear(), givenDate.getMonth(), givenDate.getDate());
}
},

/** @Test
File Name:         15_9_5_7.js
ECMA Section: 15.9.5.7 Date.prototype.toLocaleTimeString()
Description:
This function returns a string value. The contents of the string are
implementation dependent, but are intended to represent the "time"
portion of the Date in the current time zone in a convenient,
human-readable form.   We test the content of the string by checking
that

new Date(d.toDateString() + " " + d.toLocaleTimeString()) ==  d

Author:  pschwartau@netscape.com
Date:    14 november 2000
Revised: 07 january 2002  because of a change in JS Date format:
Revised: 21 November 2005 since the string comparison stuff is horked.
bclary

See http://bugzilla.mozilla.org/show_bug.cgi?id=118266 (SpiderMonkey)
See http://bugzilla.mozilla.org/show_bug.cgi?id=118636 (Rhino)
*/
test_15_9_5_7: function () {
var msPerHour =   3600000;
var msPerDay =   86400000;
var TZ_DIFF = this.getTimeZoneDiff();
var TZ_ADJUST =  TZ_DIFF * msPerHour;
var TIME_2000  = 946684800000;
var TIME_1900  = -2208988800000;
var UTC_29_FEB_2000 = TIME_2000 + 31*msPerDay + 28*msPerDay;
var UTC_1_JAN_2005 = TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001)
+ this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);

var SECTION = "15_9_5_7.js";
//var VERSION = "ECMA_3";
//var TITLE   = "Date.prototype.toLocaleTimeString()";

var status = '';
var actual = '';
var expect = '';
var givenDate;
var year = '';
var regexp = '';
var TimeString = '';
var reducedDateString = '';
var hopeThisIsLocaleTimeString = '';
var cnERR ='OOPS! FATAL ERROR: no regexp match in extractLocaleTimeString()';

//startTest();
//writeHeaderToLog( SECTION + " "+ TITLE);

// first, a couple generic tests -

status = "typeof (this.now.toLocaleTimeString())";
actual =   typeof (this.now.toLocaleTimeString());
expect = "string";
this.addTestCase(SECTION,status,expect,actual);

status = "Date.prototype.toLocaleTimeString.length";
actual =  Date.prototype.toLocaleTimeString.length;
expect =  0;
this.addTestCase(SECTION,status,expect,actual);

var TIME_NOW = null;
// 1970
addDateTestCase(0);
addDateTestCase(TZ_ADJUST);

// 1900
addDateTestCase(TIME_1900);
addDateTestCase(TIME_1900 - TZ_ADJUST);

// 2000
addDateTestCase(TIME_2000);
addDateTestCase(TIME_2000 - TZ_ADJUST);

// 29 Feb 2000
addDateTestCase(UTC_29_FEB_2000);
addDateTestCase(UTC_29_FEB_2000 - 1000);
addDateTestCase(UTC_29_FEB_2000 - TZ_ADJUST);

// Now
addDateTestCase( TIME_NOW);
addDateTestCase( TIME_NOW - TZ_ADJUST);

// 2005
addDateTestCase(UTC_1_JAN_2005);
addDateTestCase(UTC_1_JAN_2005 - 1000);
addDateTestCase(UTC_1_JAN_2005 - TZ_ADJUST);

//test();

function addDateTestCase(date_given_in_milliseconds)
{
var s = 'new Date(' +  date_given_in_milliseconds + ')';
givenDate = new Date(date_given_in_milliseconds);

status = 'd = ' + s +
'; d == new Date(d.toDateString() + " " + d.toLocaleTimeString())';
expect = givenDate.toString();
actual = new Date(givenDate.toDateString() +
' ' + givenDate.toLocaleTimeString()).toString();
this.addDateTestCase(SECTION,date_given_in_milliseconds);
}
},

/** @Test
File Name:         regress__452786.js
Summary:	   Do not crash with (new Date()).getMonth.call(new Function())
*/
test_regress__452786: function () {
var SECTION = "regress__452786.js";
//var BUGNUMBER = 452786;
var summary = 'Do not crash with (new Date()).getMonth.call(new Function())';
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

try
{
(new Date()).getMonth.call(new Function());
}
catch(ex)
{
}
this.TestCase(SECTION, summary, expect, actual);

// exitFunc ('test');
//}
},

addDateTestCase: function(SECTION, date_given_in_milliseconds) {
var givenDate = new Date(date_given_in_milliseconds);

status = 'Date.parse('   +   givenDate   +   ').toLocaleString())';
actual =  Date.parse(givenDate.toLocaleString());
expect = date_given_in_milliseconds;
this.addTestCase(SECTION,status,expect,actual);
//addTestCase();
},

addTestCase:function(SECTION, status, expect, actual){
this.TestCase(
SECTION,
status,
expect,
actual);
},

/*
* Originally, the test suite used a hard-coded value TZ_DIFF = -8.
* But that was only valid for testers in the Pacific Standard Time Zone!
* We calculate the proper number dynamically for any tester. We just
* have to be careful not to use a date subject to Daylight Savings Time...
*/
getTimeZoneDiff:function()
{
return -((new Date(2000, 1, 1)).getTimezoneOffset())/60;
},

DaysInYear:function(y) {
if ( y % 4 != 0 ) {
return 365;
}
if ( (y % 4 == 0) && (y % 100 != 0) ) {
return 366;
}
if ( (y % 100 == 0) && (y % 400 != 0) ) {
return 365;
}
if ( (y % 400 == 0) ){
return 366;
} else {
return "ERROR: DaysInYear(" + y + ") case not covered";
}
},

TimeInYear:function ( y ) {
var msPerDay =   86400000;
return (this.DaysInYear(y) * msPerDay );
}
})
.endType();
