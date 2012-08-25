vjo.ctype("dsf.jslang.feature.tests.Js15DateTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({

constructs:function(){
this.base();
},

/** @Test
File Name:         regress__188211.js
Summary:         Date.prototype.toLocaleString() error on future dates
*/
test_regress__188211: function () {
var SECTION = "regress__188211.js";
//var BUGNUMBER = 188211;
var summary = 'Date.prototype.toLocaleString() error on future dates';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

var dt;

dt = new Date(208e10);
//printStatus(dt+'');
expect = true;
actual = dt.toLocaleString().indexOf('2035') >= 0;
this.TestCase(SECTION, summary + ': new Date(208e10)', expect, actual);

dt = new Date(209e10);
//printStatus(dt+'');
expect = true;
actual = dt.toLocaleString().indexOf('2036') >= 0;
this.TestCase(SECTION, summary + ': new Date(209e10)', expect, actual);
},

/** @Test
File Name:         regress__301738__01.js
Summary:         Date parse compatibilty with MSIE
*/
test_regress__301738__01: function () {
var SECTION = "regress__301738__01.js";
//var BUGNUMBER = 301738;
var summary = 'Date parse compatibilty with MSIE';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

/*
Case 1. The input string contains an English month name.
The form of the string can be month f l, or f month l, or
f l month which each evaluate to the same date.
If f and l are both greater than or equal to 70, or
both less than 70, the date is invalid.
The year is taken to be the greater of the values f, l.
If the year is greater than or equal to 70 and less than 100,
it is considered to be the number of years after 1900.
*/

var month = 'January';//<String
var f;
var l;

f = l = 0;
expect = true;

actual = isNaN(new Date(month + ' ' + f + ' ' + l).toString());
this.TestCase(SECTION,'January 0 0 is invalid', expect, actual);

actual = isNaN(new Date(f + ' ' + l + ' ' + month).toString());
this.TestCase(SECTION,'0 0 January is invalid', expect, actual);

actual = isNaN(new Date(f + ' ' + month + ' ' + l).toString());
this.TestCase(SECTION,'0 January 0 is invalid', expect, actual);

f = l = 70;

actual = isNaN(new Date(month + ' ' + f + ' ' + l).toString());
this.TestCase(SECTION,'January 70 70 is invalid', expect, actual);

actual = isNaN(new Date(f + ' ' + l + ' ' + month).toString());
this.TestCase(SECTION,'70 70 January is invalid', expect, actual);

actual = isNaN(new Date(f + ' ' + month + ' ' + l).toString());
this.TestCase(SECTION,'70 January 70 is invalid', expect, actual);

f = 100;
l = 15;

// year, month, day
expect = new Date(f, 0, l).toString();

actual = new Date(month + ' ' + f + ' ' + l).toString();
this.TestCase(SECTION,'month f l', expect, actual);

actual = new Date(f + ' ' + l + ' ' + month).toString();
this.TestCase(SECTION,'f l month', expect, actual);

actual = new Date(f + ' ' + month + ' ' + l).toString();
this.TestCase(SECTION,'f month l', expect, actual);

f = 80;
l = 15;

// year, month, day
expect = (new Date(f, 0, l)).toString();

actual = (new Date(month + ' ' + f + ' ' + l)).toString();
this.TestCase(SECTION,'month f l', expect, actual);

actual = (new Date(f + ' ' + l + ' ' + month)).toString();
this.TestCase(SECTION,'f l month', expect, actual);

actual = (new Date(f + ' ' + month + ' ' + l)).toString();
this.TestCase(SECTION,'f month l', expect, actual);

f = 2040;
l = 15;

// year, month, day
expect = (new Date(f, 0, l)).toString();

actual = (new Date(month + ' ' + f + ' ' + l)).toString();
this.TestCase(SECTION,'month f l', expect, actual);

actual = (new Date(f + ' ' + l + ' ' + month)).toString();
this.TestCase(SECTION,'f l month', expect, actual);

actual = (new Date(f + ' ' + month + ' ' + l)).toString();
this.TestCase(SECTION,'f month l', expect, actual);

},

/** @Test
File Name:         regress__301738__02.js
Summary:         Date parse compatibilty with MSIE
*/
test_regress__301738__02: function () {
var SECTION = "regress__301738__02.js";
//var BUGNUMBER = 301738;
var summary = 'Date parse compatibilty with MSIE';
var actual = '';
var expect = '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

/*
Case 2. The input string is of the form "f/m/l" where f, m and l are
integers, e.g. 7/16/45.
Adjust the mon, mday and year values to achieve 100% MSIE
compatibility.
a. If 0 <= f < 70, f/m/l is interpreted as month/day/year.
i.  If year < 100, it is the number of years after 1900
ii. If year >= 100, it is the number of years after 0.
b. If 70 <= f < 100
i.  If m < 70, f/m/l is interpreted as
year/month/day where year is the number of years after
1900.
ii. If m >= 70, the date is invalid.
c. If f >= 100
i.  If m < 70, f/m/l is interpreted as
year/month/day where year is the number of years after 0.
ii. If m >= 70, the date is invalid.
*/

var f;
var m;
var l;

function newDate(f, m, l)
{
return new Date(f + '/' + m + '/' + l);
}

function newDesc(f, m, l)
{
return f + '/' + m + '/' + l;
}

// 2.a.i
f = 0;
m = 0;
l = 0;

expect = (new Date(l, f-1, m)).toDateString();
actual = new Date(f, m, l).toDateString();
this.TestCase(SECTION, newDesc(f, m, l), expect, actual);

f = 0;
m = 0;
l = 100;

expect = (new Date(l, f-1, m)).toDateString();
actual = new Date(f, m, l).toDateString();
this.TestCase(SECTION, newDesc(f, m, l), expect, actual);

// 2.a.ii
f = 0;
m = 24;
l = 100;

expect = (new Date(l, f-1, m)).toDateString();
actual = new Date(f, m, l).toDateString();
this.TestCase(SECTION, newDesc(f, m, l), expect, actual);

f = 0;
m = 24;
l = 2100;

expect = (new Date(l, f-1, m)).toDateString();
actual = new Date(f, m, l).toDateString();
this.TestCase(SECTION, newDesc(f, m, l), expect, actual);


// 2.b.i
f = 70;
m = 24;
l = 100;

expect = (new Date(f, m-1, l)).toDateString();
actual = new Date(f, m, l).toDateString();
this.TestCase(SECTION, newDesc(f, m, l), expect, actual);

f = 99;
m = 12;
l = 1;

expect = (new Date(f, m-1, l)).toDateString();
actual = new Date(f, m, l).toDateString();
this.TestCase(SECTION, newDesc(f, m, l), expect, actual);

// 2.b.ii.

f = 99;
m = 70;
l = 1;

expect = true;
actual = isNaN(newDate(f, m, l));
this.TestCase(SECTION, newDesc(f, m, l) + ' is an invalid date', expect, actual);

// 2.c.i

f = 100;
m = 12;
l = 1;

expect = (new Date(f, m-1, l)).toDateString();
actual = new Date(f, m, l).toDateString();
this.TestCase(SECTION, newDesc(f, m, l), expect, actual);

// 2.c.ii

f = 100;
m = 70;
l = 1;

expect = true;
actual = isNaN(new Date(f, m, l).toDateString());
this.TestCase(SECTION, newDesc(f, m, l) + ' is an invalid date', expect, actual);
},

/** @Test
File Name:         regress__309925__01.js
Summary:         Correctly parse Date strings with HH:MM
*/
test_regress__309925__01: function () {
var SECTION = "regress__309925__01.js";
//var BUGNUMBER = 309925;
var summary = 'Correctly parse Date strings with HH:MM';
var actual = new Date('Sep 24, 11:58 105') + '';
var expect = new Date('Sep 24, 11:58:00 105') + '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

this.TestCase(SECTION,summary, expect, actual);

},

/** @Test
File Name:         regress__309925__02.js
Summary:        Correctly parse Date strings with HH:MM(comment)
*/
test_regress__309925__02: function () {
var SECTION = "regress__309925__02.js";
//var BUGNUMBER = 309925;
var summary = 'Correctly parse Date strings with HH:MM(comment)';
var actual = new Date('Sep 24, 11:58(comment) 105') + '';
var expect = new Date('Sep 24, 11:58:00 (comment) 105') + '';

//printBugNumber(BUGNUMBER);
//printStatus (summary);

this.TestCase(SECTION,summary, expect, actual);
},

/** @Test
File Name:         regress__346027.js
Summary:        Date.prototype.setFullYear()
*/
test_regress__346027: function () {
var SECTION = "regress__346027.js";
//var BUGNUMBER = 346027;
var summary = 'Date.prototype.setFullYear()';
var actual = '';
var expect = true;


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
// enterFunc ('test');
// printBugNumber(BUGNUMBER);
// printStatus (summary);

var d = new Date();
d.setFullYear(); //<@SUPRESSTYPECHECK
actual = isNaN(d.getFullYear());

this.TestCase(SECTION,summary, expect, actual);

//exitFunc ('test');
//}
},

/** @Test
File Name:         regress__346363.js
Summary:        Date.prototype.setFullYear()

Lax:
Note:
This method (setFullYear) does not work as expected. if the first d.setFullYear() id commented,
then it sets the full year to 2006. It seems that it does retain the first value set.
*/
test_regress__346363: function () {
var SECTION = "regress__346363.js";
//var BUGNUMBER = 346363;
var summary = 'Date.prototype.setFullYear()';
var actual = '';
var expect = true;


//-----------------------------------------------------------------------------
//test();
//-----------------------------------------------------------------------------

//function test()
//{
// enterFunc ('test');
//printBugNumber(BUGNUMBER);
// printStatus (summary);

var d = new Date();
d.setFullYear(); //<@SUPRESSTYPECHECK
d.setFullYear(2006);
actual = d.getFullYear() == 2006;

this.TestCase(SECTION,summary, expect, actual);

//exitFunc ('test');
//}

}

})
.endType();
