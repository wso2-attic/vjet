vjo.ctype("dsf.jslang.feature.tests.EcmaDateTests")
.inherits("dsf.jslang.feature.tests.BaseTest")
.protos({

msPerDay : 86400000,
HoursPerDay : 24,
MinutesPerHour : 60,
SecondsPerMinute : 60,
msPerSecond : 1000,
msPerMinute :  60000,  // msPerSecond * SecondsPerMinute
msPerHour : 3600000,

TIME_1970 : 0,
TIME_2000 : 946684800000,
TIME_1900 : -2208988800000,
// now:new Date(),
TIME_NOW : new Date().valueOf(),
TZ_PST : -8,
PST_ADJUST : this.TZ_PST * this.msPerHour,
UTC_FEB_29_2000 : this.TIME_2000 + 31*this.msPerDay + 28*this.msPerDay,

TZ_DIFF : -((new Date(2000, 1, 1).getTimezoneOffset())/60),
TZ_ADJUST : this.TZ_DIFF * this.msPerHour,
PST_DIFF : 0,


//     UTC_JAN_1_2005: this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004),
//	 UTC_JAN_1_2005: this.TIME_2000 + (TimeInYear(2000)) + (TimeInYear(2001)) + (TimeInYear(2002)) + (TimeInYear(2003)) + (TimeInYear(2004)),

time0000 : function ()
{ // calculate time for year 0
for ( var time = 0, year = 1969; year >= 0; year-- ) {
time -= this.TimeInYear(year);
}
return time;
},

DaysInYear:function( y ) {
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

TimeInYear:function( y ) {
return ( this.DaysInYear(y) * this.msPerDay );
},

getTimeZoneDiff:function ()
{
return -((new Date(2000, 1, 1)).getTimezoneOffset())/60;
},


adjustResultArray:function (ResultArray, msMode)
{
var TIME        = 0;
var UTC_YEAR    = 1;
var UTC_MONTH   = 2;
var UTC_DATE    = 3;
var UTC_DAY     = 4;
var UTC_HOURS   = 5;
var YEAR        = 9;
var MONTH       = 10;
var DATE        = 11;
var DAY         = 12;
var HOURS       = 13;


// If the tester's system clock is in PST, no need to continue -
//  if (!PST_DIFF) {return;}

/* The date gTestcases instantiate Date objects in two different ways:
*
*        millisecond mode: e.g.   dt = new Date(10000000);
*        year-month-day mode:  dt = new Date(2000, 5, 1, ...);
*
* In the first case, the date is measured from Time 0 in Greenwich (i.e. UTC).
* In the second case, it is measured with reference to the tester's local timezone.
*
* In the first case we must correct those values expected for local measurements,
* like dt.getHours() etc. No correction is necessary for dt.getUTCHours() etc.
*
* In the second case, it is exactly the other way around -
*/
if (msMode)
{
// The hard-coded UTC milliseconds from Time 0 derives from a UTC date.
// Shift to the right by the offset between UTC and the tester.
var t = ResultArray[TIME]  +  this.TZ_DIFF*this.msPerHour;

// Use our date arithmetic functions to determine the local hour, day, etc.
ResultArray[HOURS] = this.HourFromTime(t);
ResultArray[DAY] = this.WeekDay(t);
ResultArray[DATE] = this.DateFromTime(t);
ResultArray[MONTH] = this.MonthFromTime(t);
ResultArray[YEAR] = this.YearFromTime(t);
}
else
{
// The hard-coded UTC milliseconds from Time 0 derives from a PST date.
// Shift to the left by the offset between PST and the tester.
var t = ResultArray[TIME]  -  this.PST_DIFF*this.msPerHour;

// Use our date arithmetic functions to determine the UTC hour, day, etc.
ResultArray[TIME] = t;
ResultArray[UTC_HOURS] = this.HourFromTime(t);
ResultArray[UTC_DAY] = this.WeekDay(t);
ResultArray[UTC_DATE] = this.DateFromTime(t);
ResultArray[UTC_MONTH] = this.MonthFromTime(t);
ResultArray[UTC_YEAR] = this.YearFromTime(t);
}
},

WeekDay: function ( t ) {
var weekday = (this.Day(t)+4) % 7;
return( weekday < 0 ? 7 + weekday : weekday );
},

HourFromTime:function ( t ) {
var h = Math.floor( t / this.msPerHour ) % this.HoursPerDay;
return ( (h<0) ? this.HoursPerDay + h : h  );
},

DateFromTime:function( t ) {
var day = this.DayWithinYear(t);
var month = this.MonthFromTime(t);

if ( month == 0 ) {
return ( day + 1 );
}
if ( month == 1 ) {
return ( day - 30 );
}
if ( month == 2 ) {
return ( day - 58 - this.InLeapYear(t) );
}
if ( month == 3 ) {
return ( day - 89 - this.InLeapYear(t));
}
if ( month == 4 ) {
return ( day - 119 -  this.InLeapYear(t));
}
if ( month == 5 ) {
return ( day - 150-  this.InLeapYear(t));
}
if ( month == 6 ) {
return ( day - 180-  this.InLeapYear(t));
}
if ( month == 7 ) {
return ( day - 211-  this.InLeapYear(t));
}
if ( month == 8 ) {
return ( day - 242-  this.InLeapYear(t));
}
if ( month == 9 ) {
return ( day - 272-  this.InLeapYear(t));
}
if ( month == 10 ) {
return ( day - 303-  this.InLeapYear(t));
}
if ( month == 11 ) {
return ( day - 333-  this.InLeapYear(t));
}

return ("ERROR:  DateFromTime("+t+") not known" );
},

DayWithinYear:function ( t ) {
return( this.Day(t) - this.DayFromYear(this.YearFromTime(t)));
},

Day: function ( t ) {
return ( Math.floor(t/this.msPerDay ) );
},

DayFromYear:function ( y ) {
return ( 365*(y-1970) +
Math.floor((y-1969)/4) -
Math.floor((y-1901)/100) +
Math.floor((y-1601)/400) );
},

TimeFromYear : function ( y ) {
return ( this.msPerDay * this.DayFromYear(y) );
},

YearFromTime:function ( t ) {
t = Number( t );
var sign = ( t < 0 ) ? -1 : 1;
var year = ( sign < 0 ) ? 1969 : 1970;
for ( var timeToTimeZero = t; ;  ) {
// subtract the current year's time from the time that's left.
timeToTimeZero -= sign * this.TimeInYear(year)

// if there's less than the current year's worth of time left, then break.
if ( sign < 0 ) {
if ( sign * timeToTimeZero <= 0 ) {
break;
} else {
year += sign;
}
} else {
if ( sign * timeToTimeZero < 0 ) {
break;
} else {
year += sign;
}
}
}
return ( year );
},

MonthFromTime:function ( t ) {
// i know i could use switch but i'd rather not until it's part of ECMA
var day = this.DayWithinYear( t );
var leap = this.InLeapYear(t);

if ( (0 <= day) && (day < 31) ) {
return 0;
}
if ( (31 <= day) && (day < (59+leap)) ) {
return 1;
}
if ( ((59+leap) <= day) && (day < (90+leap)) ) {
return 2;
}
if ( ((90+leap) <= day) && (day < (120+leap)) ) {
return 3;
}
if ( ((120+leap) <= day) && (day < (151+leap)) ) {
return 4;
}
if ( ((151+leap) <= day) && (day < (181+leap)) ) {
return 5;
}
if ( ((181+leap) <= day) && (day < (212+leap)) ) {
return 6;
}
if ( ((212+leap) <= day) && (day < (243+leap)) ) {
return 7;
}
if ( ((243+leap) <= day) && (day < (273+leap)) ) {
return 8;
}
if ( ((273+leap) <= day) && (day < (304+leap)) ) {
return 9;
}
if ( ((304+leap) <= day) && (day < (334+leap)) ) {
return 10;
}
if ( ((334+leap) <= day) && (day < (365+leap)) ) {
return 11;
} else {
return "ERROR: MonthFromTime("+t+") not known";
}
},

InLeapYear:function ( t ) {
if ( this.DaysInYear(this.YearFromTime(t)) == 365 ) {
return 0;
}
if ( this.DaysInYear(this.YearFromTime(t)) == 366 ) {
return 1;
} else {
return "ERROR:  InLeapYear("+ t + ") case not covered";
}
},

MakeTime:function ( hour, min, sec, ms ) {
if ( isNaN( hour ) || isNaN( min ) || isNaN( sec ) || isNaN( ms ) ) {
return Number.NaN;
}

hour = this.ToInteger(hour);
min  = this.ToInteger( min);
sec  = this.ToInteger( sec);
ms   = this.ToInteger( ms );

return( (hour*this.msPerHour) + (min*this.msPerMinute) +
(sec*this.msPerSecond) + ms );
},

MakeDay : function ( year, month, date ) {
if ( isNaN(year) || isNaN(month) || isNaN(date) ) {
return Number.NaN;
}
year = this.ToInteger(year);
month = this.ToInteger(month);
date = this.ToInteger(date );

var sign = ( year < 1970 ) ? -1 : 1;
var t =    ( year < 1970 ) ? 1 :  0;
var y =    ( year < 1970 ) ? 1969 : 1970;

var result5 = year + Math.floor( month/12 );
var result6 = month % 12;

if ( year < 1970 ) {
for ( y = 1969; y >= year; y += sign ) {
t += sign * this.TimeInYear(y);
}
} else {
for ( y = 1970 ; y < year; y += sign ) {
t += sign * this.TimeInYear(y);
}
}

var leap = this.InLeapYear( t );

for ( var m = 0; m < month; m++ ) {
t += this.TimeInMonth( m, leap );
}

if ( this.YearFromTime(t) != result5 ) {
return Number.NaN;
}
if ( this.MonthFromTime(t) != result6 ) {
return Number.NaN;
}
if ( this.DateFromTime(t) != 1 ) {
return Number.NaN;
}

return ( (this.Day(t)) + date - 1 );
},

ToInteger:function ( t ) {
t = Number( t );

if ( isNaN( t ) ){
return ( Number.NaN );
}
if ( t == 0 || t == -0 ||
t == Number.POSITIVE_INFINITY || t == Number.NEGATIVE_INFINITY ) {
return 0;
}

var sign = ( t < 0 ) ? -1 : 1;

return ( sign * Math.floor( Math.abs( t ) ) );
},

TimeClip : function ( t ) {
if ( isNaN( t ) ) {
return ( Number.NaN );
}
if ( Math.abs( t ) > 8.64e15 ) {
return ( Number.NaN );
}

return ( this.ToInteger( t ) );
},

TimeInMonth : function ( month, leap ) {
// september april june november
// jan 0  feb 1  mar 2  apr 3   may 4  june 5  jul 6
// aug 7  sep 8  oct 9  nov 10  dec 11

if ( month == 3 || month == 5 || month == 8 || month == 10 ) {
return ( 30*this.msPerDay );
}

// all the rest
if ( month == 0 || month == 2 || month == 4 || month == 6 ||
month == 7 || month == 9 || month == 11 ) {
return ( 31*this.msPerDay );
}

// save february
return ( (leap == 0) ? 28*this.msPerDay : 29*this.msPerDay );
},

LocalTime : function ( t ) {
return ( t + this.LocalTZA() + this.DaylightSavingTA(t) );
},

LocalTZA : function () {
return ( this.TZ_DIFF * this.msPerHour );
},

DaylightSavingTA : function ( t ) {
t = t - this.LocalTZA();

var dst_start = this.GetFirstSundayInApril(t) + 2*this.msPerHour;
var dst_end   = this.GetLastSundayInOctober(t)+ 2*this.msPerHour;

if ( t >= dst_start && t < dst_end ) {
return this.msPerHour;
} else {
return 0;
}

// Daylight Savings Time starts on the first Sunday in April at 2:00AM in
// PST.  Other time zones will need to override this function.

//print( new Date( UTC(dst_start + LocalTZA())) );
var UTC = UTC;
var LocalTZA = LocalTZA;
return UTC(dst_start  + LocalTZA());
},

GetFirstSundayInApril : function ( t ) {
var year = this.YearFromTime(t);
var leap = this.InLeapYear(t);

var april = this.TimeFromYear(year) + this.TimeInMonth(0, leap) + this.TimeInMonth(1,leap) +
this.TimeInMonth(2,leap);

for ( var first_sunday = april; this.WeekDay(first_sunday) > 0;
first_sunday += this.msPerDay )
{
;
}

return first_sunday;
},

GetLastSundayInOctober : function ( t ) {
var year = this.YearFromTime(t);
var leap = this.InLeapYear(t);

for ( var oct = this.TimeFromYear(year), m = 0; m < 9; m++ ) {
oct += this.TimeInMonth(m, leap);
}
for ( var last_sunday = oct + 30*this.msPerDay; this.WeekDay(last_sunday) > 0;
last_sunday -= this.msPerDay )
{
;
}
return last_sunday;
},

GetDSTStart : function ( t )
{
return (this.GetFirstSundayInMonth(t, 2) + 7*this.msPerDay + 2*this.msPerHour - this.LocalTZA());
},

GetDSTEnd : function ( t )
{
return (this.GetFirstSundayInMonth(t, 10) + 2*this.msPerHour - this.LocalTZA());
},

GetFirstSundayInMonth : function ( t, m ) {
var year = this.YearFromTime(t);
var leap = this.InLeapYear(t);

// month m 0..11
// april == 3
// march == 2

// set time to first day of month m
var time = this.TimeFromYear(year);
for (var i = 0; i < m; ++i)
{
time += this.TimeInMonth(i, leap);
}

for ( var first_sunday = time; this.WeekDay(first_sunday) > 0;
first_sunday += this.msPerDay )
{
;
}

return first_sunday;
},

MinFromTime : function ( t ) {
var min = Math.floor( t / this.msPerMinute ) % this.MinutesPerHour;
return( ( min < 0 ) ? this.MinutesPerHour + min : min  );
},

SecFromTime : function ( t ) {
var sec = Math.floor( t / this.msPerSecond ) % this.SecondsPerMinute;
return ( (sec < 0 ) ? this.SecondsPerMinute + sec : sec );
},

msFromTime : function ( t ) {
var ms = t % this.msPerSecond;
return ( (ms < 0 ) ? this.msPerSecond + ms : ms );
},

UTC: function ( t ) {
return ( t - this.LocalTZA() - this.DaylightSavingTA(t - this.LocalTZA()) );
},

TimeWithinDay: function ( t ) {
var r = t % this.msPerDay;
if (r < 0)
{
r += this.msPerDay;
}
return r;
},


/**
File Name:          15.9.1.1-1.js
ECMA Section:       15.9.1.1 Time Range
Description:
- leap seconds are ignored
- assume 86400000 ms / day
- numbers range fom +/- 9,007,199,254,740,991
- ms precision for any instant that is within
approximately +/-285,616 years from 1 jan 1970
UTC
- range of times supported is -100,000,000 days
to 100,000,000 days from 1 jan 1970 12:00 am
- time supported is 8.64e5*10e8 milliseconds from
1 jan 1970 UTC  (+/-273972.6027397 years)

-   this test generates its own data -- it does not
read data from a file.
Author:             christine@netscape.com
Date:               7 july 1997

Static variables:
FOUR_HUNDRED_YEARS

*/

test_15_9_1_1__1:function(){

//  every one hundred years contains:
//    24 years with 366 days
//
//  every four hundred years contains:
//    97 years with 366 days
//   303 years with 365 days
//
//   86400000*365*97    =    3067372800000
//  +86400000*366*303   =  + 9555408000000
//                      =    1.26227808e+13
var FOUR_HUNDRED_YEARS = 1.26227808e+13;
var SECTION         =  "15.9.1.1-1";


var M_SECS;
var CURRENT_YEAR;

for ( M_SECS = 0, CURRENT_YEAR = 1970;
M_SECS < 8640000000000000;
M_SECS += FOUR_HUNDRED_YEARS, CURRENT_YEAR += 400 ) {

this.TestCase( SECTION,
"new Date("+M_SECS+")",
CURRENT_YEAR,
(new Date( M_SECS)).getUTCFullYear() );
}

},



/**
File Name:          15.9.1.1-2.js
ECMA Section:       15.9.1.1 Time Range
Description:
- leap seconds are ignored
- assume 86400000 ms / day
- numbers range fom +/- 9,007,199,254,740,991
- ms precision for any instant that is within
approximately +/-285,616 years from 1 jan 1970
UTC
- range of times supported is -100,000,000 days
to 100,000,000 days from 1 jan 1970 12:00 am
- time supported is 8.64e5*10e8 milliseconds from
1 jan 1970 UTC  (+/-273972.6027397 years)
Author:             christine@netscape.com
Date:               9 july 1997
*/

test_15_9_1_1__2:function(){

//  every one hundred years contains:
//    24 years with 366 days
//
//  every four hundred years contains:
//    97 years with 366 days
//   303 years with 365 days
//
//   86400000*366*97  =    3067372800000
//  +86400000*365*303 =  + 9555408000000
//                    =    1.26227808e+13

var FOUR_HUNDRED_YEARS = 1.26227808e+13;
var SECTION         =  "15.9.1.1-2";

var M_SECS;
var CURRENT_YEAR;

for ( M_SECS = 0, CURRENT_YEAR = 1970;
M_SECS > -8640000000000000;
M_SECS -= FOUR_HUNDRED_YEARS, CURRENT_YEAR -= 400 ) {

this.TestCase( SECTION,
"new Date("+M_SECS+")",
CURRENT_YEAR,
(new Date( M_SECS )).getUTCFullYear() );

}

},

/**
File Name:          15.9.1.13-1.js
ECMA Section:       15.9.1.1 MakeDate(day, time)
Description:

The operator MakeDate calculates a number of milliseconds from its
two arguments, which must be ECMAScript number values. This
operator functions as follows:

1. If day is not finite or time is not finite, return NaN.

2. Compute day * msPerDay + time.

3. Return Result(2).
*/

test_15_9_1_13__1:function(){

var SECTION = "15.9.1.13-1";
this.TestCase( SECTION,
"MakeDate(Number.POSITIVE_INFINITY, 0)",
Number.NaN,
this.MakeDate(Number.POSITIVE_INFINITY, 0));

this.TestCase( SECTION,
"MakeDate(Number.NEGATIVE_INFINITY, 0)",
Number.NaN,
this.MakeDate(Number.NEGATIVE_INFINITY, 0));

this.TestCase( SECTION,
"MakeDate(0, Number.POSITIVE_INFINITY)",
Number.NaN,
this.MakeDate(0, Number.POSITIVE_INFINITY));

this.TestCase( SECTION,
"MakeDate(0, Number.NEGATIVE_INFINITY)",
Number.NaN,
this.MakeDate(0, Number.NEGATIVE_INFINITY));



},

/**
File Name:          15.9.2.1.js
ECMA Section:       15.9.2.1 Date constructor used as a function
Date( year, month, date, hours, minutes, seconds, ms )
Description:        The arguments are accepted, but are completely ignored.
A string is created and returned as if by the
expression (new Date()).toString().

Author:             christine@netscape.com
Date:               28 october 1997

*/

test_15_9_2_1:function(){
var VERSION =   "ECMA_1";

var SECTION =   "15.9.2.1";
var TITLE =     "Date Constructor used as a function";
var TYPEOF  =   "string";
var TOLERANCE = 1000;


// allow up to 1 second difference due to possibility
// the date may change by 1 second in between calls to Date

var d1;
var d2;

// Dates around 1970

d1 = new Date();
d2 = Date.parse(Date(1970,0,1,0,0,0,0));
this.TestCase(SECTION, "Date(1970,0,1,0,0,0,0)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(1969,11,31,15,59,59,999));
this.TestCase(SECTION, "Date(1969,11,31,15,59,59,999)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(1969,11,31,16,0,0,0));
this.TestCase(SECTION, "Date(1969,11,31,16,0,0,0)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(1969,11,31,16,0,0,1));
this.TestCase(SECTION, "Date(1969,11,31,16,0,0,1)", true, d2 - d1 <= 1000);

// Dates around 2000
d1 = new Date();
d2 = Date.parse(Date(1999,11,15,59,59,999));
this.TestCase(SECTION, "Date(1999,11,15,59,59,999)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(1999,11,16,0,0,0,0));
this.TestCase(SECTION, "Date(1999,11,16,0,0,0,0)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(1999,11,31,23,59,59,999));
this.TestCase(SECTION, "Date(1999,11,31,23,59,59,999)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(2000,0,0,0,0,0,0));
this.TestCase(SECTION, "Date(2000,0,1,0,0,0,0)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(2000,0,0,0,0,0,1));
this.TestCase(SECTION, "Date(2000,0,1,0,0,0,1)", true, d2 - d1 <= 1000);

// Dates around 1900

d1 = new Date();
d2 = Date.parse(Date(1899,11,31,23,59,59,999));
this.TestCase(SECTION, "Date(1899,11,31,23,59,59,999)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(1900,0,1,0,0,0,0));
this.TestCase(SECTION, "Date(1900,0,1,0,0,0,0)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(1900,0,1,0,0,0,1));
this.TestCase(SECTION, "Date(1900,0,1,0,0,0,1)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(1899,11,31,16,0,0,0,0));
this.TestCase(SECTION, "Date(1899,11,31,16,0,0,0,0)", true, d2 - d1 <= 1000);

// Dates around feb 29, 2000

d1 = new Date();
d2 = Date.parse(Date(2000,1,29,0,0,0,0));
this.TestCase(SECTION, "Date(2000,1,29,0,0,0,0)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(2000,1,28,23,59,59,999));
this.TestCase(SECTION, "Date(2000,1,28,23,59,59,999)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(2000,1,27,16,0,0,0));
this.TestCase(SECTION, "Date(2000,1,27,16,0,0,0)", true, d2 - d1 <= 1000);

// Dates around jan 1, 2005
d1 = new Date();
d2 = Date.parse(Date(2004,11,31,23,59,59,999));
this.TestCase(SECTION, "Date(2004,11,31,23,59,59,999)",  true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(2005,0,1,0,0,0,0));
this.TestCase(SECTION, "Date(2005,0,1,0,0,0,0)",  true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(2005,0,1,0,0,0,1));
this.TestCase(SECTION, "Date(2005,0,1,0,0,0,1)",  true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(2004,11,31,16,0,0,0,0));
this.TestCase(SECTION, "Date(2004,11,31,16,0,0,0,0)",  true, d2 - d1 <= 1000);

// Dates around jan 1, 2032
d1 = new Date();
d2 = Date.parse(Date(2031,11,31,23,59,59,999));
this.TestCase(SECTION, "Date(2031,11,31,23,59,59,999)",  true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(2032,0,1,0,0,0,0));
this.TestCase(SECTION, "Date(2032,0,1,0,0,0,0)",  true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(2032,0,1,0,0,0,1));
this.TestCase(SECTION, "Date(2032,0,1,0,0,0,1)",  true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(2031,11,31,16,0,0,0,0));
this.TestCase(SECTION, "Date(2031,11,31,16,0,0,0,0)",  true, d2 - d1 <= 1000);

},

/**
File Name:          15.9.2.2.js
ECMA Section:       15.9.2.2 Date constructor used as a function
Date( year, month, date, hours, minutes, seconds )
Description:        The arguments are accepted, but are completely ignored.
A string is created and returned as if by the
expression (new Date()).toString().

Author:             christine@netscape.com
Date:               28 october 1997
Version:            9706

*/

test_15_9_2_2__1:function(){
var VERSION = 9706;

var SECTION = "15.9.2.2";
var TOLERANCE = 100;
var TITLE = "The Date Constructor Called as a Function";


// allow up to 1 second difference due to possibility
// the date may change by 1 second in between calls to Date

var d1;
var d2;

// Dates around 1970

d1 = new Date();
d2 = Date.parse(Date(1970,0,1,0,0,0));
this.TestCase(SECTION, "Date(1970,0,1,0,0,0)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(1969,11,31,15,59,59));
this.TestCase(SECTION, "Date(1969,11,31,15,59,59)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(1969,11,31,16,0,0));
this.TestCase(SECTION, "Date(1969,11,31,16,0,0)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(1969,11,31,16,0,1));
this.TestCase(SECTION, "Date(1969,11,31,16,0,1)", true, d2 - d1 <= 1000);

},

/**
File Name:          15.9.2.2.js
ECMA Section:       15.9.2.2 Date constructor used as a function
Date( year, month, date, hours, minutes, seconds )
Description:        The arguments are accepted, but are completely ignored.
A string is created and returned as if by the
expression (new Date()).toString().

Author:             christine@netscape.com
Date:               28 october 1997
Version:            9706

*/

test_15_9_2_2__2:function(){

var VERSION = 9706;
var SECTION = "15.9.2.2";
var TOLERANCE = 100;
var TITLE = "The Date Constructor Called as a Function";

// allow up to 1 second difference due to possibility
// the date may change by 1 second in between calls to Date

var d1;
var d2;

// Dates around 2000
d1 = new Date();
d2 = Date.parse(Date(1999,11,15,59,59));
this.TestCase( SECTION, "Date(1999,11,15,59,59)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(1999,11,16,0,0,0));
this.TestCase( SECTION, "Date(1999,11,16,0,0,0)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(1999,11,31,23,59,59));
this.TestCase( SECTION, "Date(1999,11,31,23,59,59)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(2000,0,0,0,0,0));
this.TestCase( SECTION, "Date(2000,0,1,0,0,0)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(2000,0,0,0,0,1));
this.TestCase( SECTION, "Date(2000,0,1,0,0,1)", true, d2 - d1 <= 1000)

},

/**
File Name:          15.9.2.2.js
ECMA Section:       15.9.2.2 Date constructor used as a function
Date( year, month, date, hours, minutes, seconds )
Description:        The arguments are accepted, but are completely ignored.
A string is created and returned as if by the
expression (new Date()).toString().

Author:             christine@netscape.com
Date:               28 october 1997
Version:            9706

*/

test_15_9_2_2__3:function(){
var VERSION = 9706;
var SECTION = "15.9.2.2";
var TOLERANCE = 100;
var TITLE = "The Date Constructor Called as a Function";

// allow up to 1 second difference due to possibility
// the date may change by 1 second in between calls to Date

var d1;
var d2;

// Dates around 1900

d1 = new Date();
d2 = Date.parse(Date(1899,11,31,23,59,59));
this.TestCase( SECTION, "Date(1899,11,31,23,59,59)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(1900,0,1,0,0,0));
this.TestCase( SECTION, "Date(1900,0,1,0,0,0)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(1900,0,1,0,0,1) );
this.TestCase( SECTION, "Date(1900,0,1,0,0,1)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(1899,11,31,16,0,0,0));
this.TestCase( SECTION, "Date(1899,11,31,16,0,0,0)", true, d2 - d1 <= 1000);


},

/**
File Name:          15.9.2.2.js
ECMA Section:       15.9.2.2 Date constructor used as a function
Date( year, month, date, hours, minutes, seconds )
Description:        The arguments are accepted, but are completely ignored.
A string is created and returned as if by the
expression (new Date()).toString().

Author:             christine@netscape.com
Date:               28 october 1997
Version:            9706

*/

test_15_9_2_2__4:function(){

var VERSION = 9706;
var SECTION = "15.9.2.2";
var TOLERANCE = 100;
var TITLE = "The Date Constructor Called as a Function";


// allow up to 1 second difference due to possibility
// the date may change by 1 second in between calls to Date

var d1;
var d2;

// Dates around feb 29, 2000

d1 = new Date();
d2 = Date.parse(Date(2000,1,29,0,0,0));
this.TestCase(SECTION, "Date(2000,1,29,0,0,0)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(2000,1,28,23,59,59));
this.TestCase(SECTION, "Date(2000,1,28,23,59,59)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(2000,1,27,16,0,0));
this.TestCase(SECTION, "Date(2000,1,27,16,0,0)", true, d2 - d1 <= 1000);

},

/**
File Name:          15.9.2.2.js
ECMA Section:       15.9.2.2 Date constructor used as a function
Date( year, month, date, hours, minutes, seconds )
Description:        The arguments are accepted, but are completely ignored.
A string is created and returned as if by the
expression (new Date()).toString().

Author:             christine@netscape.com
Date:               28 october 1997
Version:            9706

*/
test_15_9_2_2__5:function(){
var VERSION = 9706;

var SECTION = "15.9.2.2";
var TOLERANCE = 100;
var TITLE = "The Date Constructor Called as a Function";

// allow up to 1 second difference due to possibility
// the date may change by 1 second in between calls to Date

var d1;
var d2;

// Dates around jan 1, 2005

d1 = new Date();
d2 = Date.parse(Date(2004,11,31,23,59,59));
this.TestCase( SECTION, "Date(2004,11,31,23,59,59)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(2005,0,1,0,0,0) );
this.TestCase( SECTION, "Date(2005,0,1,0,0,0)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(2005,0,1,0,0,1) );
this.TestCase( SECTION, "Date(2005,0,1,0,0,1)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(2004,11,31,16,0,0,0));
this.TestCase( SECTION, "Date(2004,11,31,16,0,0,0)", true, d2 - d1 <= 1000);


},

/**
File Name:          15_9_2_2__6.js
ECMA Section:       15.9.2.2 Date constructor used as a function
Date( year, month, date, hours, minutes, seconds )
Description:        The arguments are accepted, but are completely ignored.
A string is created and returned as if by the
expression (new Date()).toString().

Author:             christine@netscape.com
Date:               28 october 1997
Version:            9706

*/
test_15_9_2_2__6:function(){
var VERSION = 9706;
//startTest();
var SECTION = "15.9.2.2";
var TOLERANCE = 100;
var TITLE = "The Date Constructor Called as a Function";

//writeHeaderToLog(SECTION+" "+TITLE );

// allow up to 1 second difference due to possibility
// the date may change by 1 second in between calls to Date

var d1;
var d2;

// Dates around jan 1, 2032
d1 = new Date();
d2 = Date.parse(Date(2031,11,31,23,59,59));
this.TestCase(SECTION, "Date(2031,11,31,23,59,59)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(2032,0,1,0,0,0));
this.TestCase(SECTION, "Date(2032,0,1,0,0,0)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(2032,0,1,0,0,1));
this.TestCase(SECTION, "Date(2032,0,1,0,0,1)", true, d2 - d1 <= 1000);

d1 = new Date();
d2 = Date.parse(Date(2031,11,31,16,0,0,0));
this.TestCase(SECTION, "Date(2031,11,31,16,0,0,0)", true, d2 - d1 <= 1000);

//test();

},

/**
File Name:          15_9_3_1__1.js
ECMA Section:       15.9.3.1 new Date (year, month, date, hours, minutes, seconds, ms)
Description:        The [[Prototype]] property of the newly constructed
object is set to the original Date prototype object,
the one that is the initial value of Date.prototype.

The [[Class]] property of the newly constructed object
is set as follows:
1. Call ToNumber(year)
2. Call ToNumber(month)
3. Call ToNumber(date)
4. Call ToNumber(hours)
5. Call ToNumber(minutes)
6. Call ToNumber(seconds)
7. Call ToNumber(ms)
8.  If Result(1) is NaN and 0 <= ToInteger(Result(1)) <=
99, Result(8) is 1900+ToInteger(Result(1)); otherwise,
Result(8) is Result(1)
9.  Compute MakeDay(Result(8), Result(2), Result(3)
10. Compute MakeTime(Result(4), Result(5), Result(6),
Result(7)
11. Compute MakeDate(Result(9), Result(10))
12. Set the [[Value]] property of the newly constructed
object to TimeClip(UTC(Result(11))).


This tests the returned value of a newly constructed
Date object.

Author:             christine@netscape.com
Date:               7 july 1997
*/
test_15_9_3_1__1:function(){
var SECTION = "15.9.3.1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "new Date( year, month, date, hours, minutes, seconds, ms )";

var TIME        = 0;
var UTC_YEAR    = 1;
var UTC_MONTH   = 2;
var UTC_DATE    = 3;
var UTC_DAY     = 4;
var UTC_HOURS   = 5;
var UTC_MINUTES = 6;
var UTC_SECONDS = 7;
var UTC_MS      = 8;

var YEAR        = 9;
var MONTH       = 10;
var DATE        = 11;
var DAY         = 12;
var HOURS       = 13;
var MINUTES     = 14;
var SECONDS     = 15;
var MS          = 16;
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);

//writeHeaderToLog( SECTION + " "+ TITLE);

// Dates around 1970

addNewTestCase(this,  new Date( 1969,11,31,15,59,59,999),
"new Date( 1969,11,31,15,59,59,999)",
[this.TIME_1970-1,1969,11,31,3,23,59,59,999,1969,11,31,3,15,59,59,999] );

addNewTestCase(this,  new Date( 1969,11,31,23,59,59,999),
"new Date( 1969,11,31,23,59,59,999)",
[this.TIME_1970-this.PST_ADJUST-1,1970,0,1,4,7,59,59,999,1969,11,31,3,23,59,59,999] );

addNewTestCase(this,  new Date( 1970,0,1,0,0,0,0),
"new Date( 1970,0,1,0,0,0,0)",
[this.TIME_1970-this.PST_ADJUST,1970,0,1,4,8,0,0,0,1970,0,1,4,0,0,0,0] );

addNewTestCase(this,  new Date( 1969,11,31,16,0,0,0),
"new Date( 1969,11,31,16,0,0,0)",
[this.TIME_1970,1970,0,1,4,0,0,0,0,1969,11,31,3,16,0,0,0] );

addNewTestCase(this,  new Date(1969,12,1,0,0,0,0),
"new Date(1969,12,1,0,0,0,0)",
[this.TIME_1970-this.PST_ADJUST,1970,0,1,4,8,0,0,0,1970,0,1,4,0,0,0,0] );

addNewTestCase(this,  new Date(1969,11,32,0,0,0,0),
"new Date(1969,11,32,0,0,0,0)",
[this.TIME_1970-this.PST_ADJUST,1970,0,1,4,8,0,0,0,1970,0,1,4,0,0,0,0] );

addNewTestCase(this,  new Date(1969,11,31,24,0,0,0),
"new Date(1969,11,31,24,0,0,0)",
[this.TIME_1970-this.PST_ADJUST,1970,0,1,4,8,0,0,0,1970,0,1,4,0,0,0,0] );

addNewTestCase(this,  new Date(1969,11,31,23,60,0,0),
"new Date(1969,11,31,23,60,0,0)",
[this.TIME_1970-this.PST_ADJUST,1970,0,1,4,8,0,0,0,1970,0,1,4,0,0,0,0] );

addNewTestCase(this,  new Date(1969,11,31,23,59,60,0),
"new Date(1969,11,31,23,59,60,0)",
[this.TIME_1970-this.PST_ADJUST,1970,0,1,4,8,0,0,0,1970,0,1,4,0,0,0,0] );

addNewTestCase(this, new Date(1969,11,31,23,59,59,1000),
"new Date(1969,11,31,23,59,59,1000)",
[this.TIME_1970-this.PST_ADJUST,1970,0,1,4,8,0,0,0,1970,0,1,4,0,0,0,0] );

// Dates around 2000

addNewTestCase(this,  new Date( 1999,11,31,15,59,59,999),
"new Date( 1999,11,31,15,59,59,999)",
[this.TIME_2000-1,1999,11,31,5,23,59,59,999,1999,11,31,5,15,59,59,999] );

addNewTestCase(this,  new Date( 1999,11,31,16,0,0,0),
"new Date( 1999,11,31,16,0,0,0)",
[this.TIME_2000,2000,0,1,6,0,0,0,0,1999,11,31,5, 16,0,0,0] );

addNewTestCase(this,  new Date( 1999,11,31,23,59,59,999),
"new Date( 1999,11,31,23,59,59,999)",
[this.TIME_2000-this.PST_ADJUST-1,2000,0,1,6,7,59,59,999,1999,11,31,5,23,59,59,999] );

addNewTestCase(this,  new Date( 2000,0,1,0,0,0,0),
"new Date( 2000,0,1,0,0,0,0)",
[this.TIME_2000-this.PST_ADJUST,2000,0,1,6,8,0,0,0,2000,0,1,6,0,0,0,0] );

addNewTestCase(this,  new Date( 2000,0,1,0,0,0,1),
"new Date( 2000,0,1,0,0,0,1)",
[this.TIME_2000-this.PST_ADJUST+1,2000,0,1,6,8,0,0,1,2000,0,1,6,0,0,0,1] );

// Dates around 29 Feb 2000

addNewTestCase(this,  new Date(2000,1,28,16,0,0,0),
"new Date(2000,1,28,16,0,0,0)",
[this.UTC_FEB_29_2000,2000,1,29,2,0,0,0,0,2000,1,28,1,16,0,0,0] );

addNewTestCase(this,  new Date(2000,1,29,0,0,0,0),
"new Date(2000,1,29,0,0,0,0)",
[this.UTC_FEB_29_2000-this.PST_ADJUST,2000,1,29,2,8,0,0,0,2000,1,29,2,0,0,0,0] );

addNewTestCase(this,  new Date(2000,1,28,24,0,0,0),
"new Date(2000,1,28,24,0,0,0)",
[this.UTC_FEB_29_2000-this.PST_ADJUST,2000,1,29,2,8,0,0,0,2000,1,29,2,0,0,0,0] );

// Dates around 1900

addNewTestCase( this, new Date(1899,11,31,16,0,0,0),
"new Date(1899,11,31,16,0,0,0)",
[this.TIME_1900,1900,0,1,1,0,0,0,0,1899,11,31,0,16,0,0,0] );

addNewTestCase( this, new Date(1899,11,31,15,59,59,999),
"new Date(1899,11,31,15,59,59,999)",
[this.TIME_1900-1,1899,11,31,0,23,59,59,999,1899,11,31,0,15,59,59,999] );

addNewTestCase(this,  new Date(1899,11,31,23,59,59,999),
"new Date(1899,11,31,23,59,59,999)",
[this.TIME_1900-this.PST_ADJUST-1,1900,0,1,1,7,59,59,999,1899,11,31,0,23,59,59,999] );

addNewTestCase(this,  new Date(1900,0,1,0,0,0,0),
"new Date(1900,0,1,0,0,0,0)",
[this.TIME_1900-this.PST_ADJUST,1900,0,1,1,8,0,0,0,1900,0,1,1,0,0,0,0] );

addNewTestCase( this, new Date(1900,0,1,0,0,0,1),
"new Date(1900,0,1,0,0,0,1)",
[this.TIME_1900-this.PST_ADJUST+1,1900,0,1,1,8,0,0,1,1900,0,1,1,0,0,0,1] );

// Dates around 2005

addNewTestCase(this,  new Date(2005,0,1,0,0,0,0),
"new Date(2005,0,1,0,0,0,0)",
[UTC_JAN_1_2005-this.PST_ADJUST,2005,0,1,6,8,0,0,0,2005,0,1,6,0,0,0,0] );

addNewTestCase(this,  new Date(2004,11,31,16,0,0,0),
"new Date(2004,11,31,16,0,0,0)",
[UTC_JAN_1_2005,2005,0,1,6,0,0,0,0,2004,11,31,5,16,0,0,0] );

//test();


function addNewTestCase( obj, DateCase, DateString, ResultArray ) {
//adjust hard-coded ResultArray for tester's timezone instead of PST
obj.adjustResultArray(ResultArray);

obj.TestCase( SECTION, DateString+".getTime()", ResultArray[TIME],       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()", ResultArray[TIME],       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      ResultArray[UTC_YEAR],  DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         ResultArray[UTC_MONTH],  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          ResultArray[UTC_DATE],   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           ResultArray[UTC_DAY],    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         ResultArray[UTC_HOURS],  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       ResultArray[UTC_MINUTES],DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       ResultArray[UTC_SECONDS],DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  ResultArray[UTC_MS],     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         ResultArray[YEAR],       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            ResultArray[MONTH],      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             ResultArray[DATE],       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              ResultArray[DAY],        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            ResultArray[HOURS],      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          ResultArray[MINUTES],    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          ResultArray[SECONDS],    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     ResultArray[MS],         DateCase.getMilliseconds() );
}

},

/**
File Name:          15_9_3_1__2.js
ECMA Section:       15.9.3.1 new Date (year, month, date, hours, minutes, seconds, ms)
Description:        The [[Prototype]] property of the newly constructed
object is set to the original Date prototype object,
the one that is the initial value of Date.prototype.

The [[Class]] property of the newly constructed object
is set as follows:
1. Call ToNumber(year)
2. Call ToNumber(month)
3. Call ToNumber(date)
4. Call ToNumber(hours)
5. Call ToNumber(minutes)
6. Call ToNumber(seconds)
7. Call ToNumber(ms)
8.  If Result(1) is NaN and 0 <= ToInteger(Result(1)) <=
99, Result(8) is 1900+ToInteger(Result(1)); otherwise,
Result(8) is Result(1)
9.  Compute MakeDay(Result(8), Result(2), Result(3)
10. Compute MakeTime(Result(4), Result(5), Result(6),
Result(7)
11. Compute MakeDate(Result(9), Result(10))
12. Set the [[Value]] property of the newly constructed
object to TimeClip(UTC(Result(11))).


This tests the returned value of a newly constructed
Date object.

Author:             christine@netscape.com
Date:               7 july 1997
*/
test_15_9_3_1__2:function(){
var SECTION = "15.9.3.1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "new Date( year, month, date, hours, minutes, seconds, ms )";

var TIME        = 0;
var UTC_YEAR    = 1;
var UTC_MONTH   = 2;
var UTC_DATE    = 3;
var UTC_DAY     = 4;
var UTC_HOURS   = 5;
var UTC_MINUTES = 6;
var UTC_SECONDS = 7;
var UTC_MS      = 8;

var YEAR        = 9;
var MONTH       = 10;
var DATE        = 11;
var DAY         = 12;
var HOURS       = 13;
var MINUTES     = 14;
var SECONDS     = 15;
var MS          = 16;

//writeHeaderToLog( SECTION + " "+ TITLE);


// all the "ResultArrays" below are hard-coded to Pacific Standard Time values -

// Dates around 2000

addNewTestCase(this, new Date( 1999,11,31,15,59,59,999),
"new Date( 1999,11,31,15,59,59,999)",
[this.TIME_2000-1,1999,11,31,5,23,59,59,999,1999,11,31,5,15,59,59,999] );

addNewTestCase(this, new Date( 1999,11,31,16,0,0,0),
"new Date( 1999,11,31,16,0,0,0)",
[this.TIME_2000,2000,0,1,6,0,0,0,0,1999,11,31,5, 16,0,0,0] );

addNewTestCase(this, new Date( 1999,11,31,23,59,59,999),
"new Date( 1999,11,31,23,59,59,999)",
[this.TIME_2000-this.PST_ADJUST-1,2000,0,1,6,7,59,59,999,1999,11,31,5,23,59,59,999] );

addNewTestCase(this, new Date( 2000,0,1,0,0,0,0),
"new Date( 2000,0,1,0,0,0,0)",
[this.TIME_2000-this.PST_ADJUST,2000,0,1,6,8,0,0,0,2000,0,1,6,0,0,0,0] );

addNewTestCase(this, new Date( 2000,0,1,0,0,0,1),
"new Date( 2000,0,1,0,0,0,1)",
[this.TIME_2000-this.PST_ADJUST+1,2000,0,1,6,8,0,0,1,2000,0,1,6,0,0,0,1] );

//test();

function addNewTestCase(obj,  DateCase, DateString, ResultArray ) {
//adjust hard-coded ResultArray for tester's timezone instead of PST
obj.adjustResultArray(ResultArray);

obj.TestCase( SECTION, DateString+".getTime()", ResultArray[TIME],       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()", ResultArray[TIME],       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      ResultArray[UTC_YEAR],  DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         ResultArray[UTC_MONTH],  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          ResultArray[UTC_DATE],   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           ResultArray[UTC_DAY],    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         ResultArray[UTC_HOURS],  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       ResultArray[UTC_MINUTES],DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       ResultArray[UTC_SECONDS],DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  ResultArray[UTC_MS],     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         ResultArray[YEAR],       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            ResultArray[MONTH],      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             ResultArray[DATE],       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              ResultArray[DAY],        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            ResultArray[HOURS],      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          ResultArray[MINUTES],    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          ResultArray[SECONDS],    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     ResultArray[MS],         DateCase.getMilliseconds() );
}

},

/**
File Name:          15_9_3_1__3.js
ECMA Section:       15.9.3.1 new Date (year, month, date, hours, minutes, seconds, ms)
Description:        The [[Prototype]] property of the newly constructed
object is set to the original Date prototype object,
the one that is the initial value of Date.prototype.

The [[Class]] property of the newly constructed object
is set as follows:
1. Call ToNumber(year)
2. Call ToNumber(month)
3. Call ToNumber(date)
4. Call ToNumber(hours)
5. Call ToNumber(minutes)
6. Call ToNumber(seconds)
7. Call ToNumber(ms)
8.  If Result(1) is NaN and 0 <= ToInteger(Result(1)) <=
99, Result(8) is 1900+ToInteger(Result(1)); otherwise,
Result(8) is Result(1)
9.  Compute MakeDay(Result(8), Result(2), Result(3)
10. Compute MakeTime(Result(4), Result(5), Result(6),
Result(7)
11. Compute MakeDate(Result(9), Result(10))
12. Set the [[Value]] property of the newly constructed
object to TimeClip(UTC(Result(11))).


This tests the returned value of a newly constructed
Date object.

Author:             christine@netscape.com
Date:               7 july 1997
*/
test_15_9_3_1__3:function(){
var SECTION = "15.9.3.1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "new Date( year, month, date, hours, minutes, seconds, ms )";

var TIME        = 0;
var UTC_YEAR    = 1;
var UTC_MONTH   = 2;
var UTC_DATE    = 3;
var UTC_DAY     = 4;
var UTC_HOURS   = 5;
var UTC_MINUTES = 6;
var UTC_SECONDS = 7;
var UTC_MS      = 8;

var YEAR        = 9;
var MONTH       = 10;
var DATE        = 11;
var DAY         = 12;
var HOURS       = 13;
var MINUTES     = 14;
var SECONDS     = 15;
var MS          = 16;

//writeHeaderToLog( SECTION + " "+ TITLE);

// all the "ResultArrays" below are hard-coded to Pacific Standard Time values -
addNewTestCase(this, new Date(2000,1,28,16,0,0,0),
"new Date(2000,1,28,16,0,0,0)",
[this.UTC_FEB_29_2000,2000,1,29,2,0,0,0,0,2000,1,28,1,16,0,0,0] );

addNewTestCase(this, new Date(2000,1,29,0,0,0,0),
"new Date(2000,1,29,0,0,0,0)",
[this.UTC_FEB_29_2000 - this.PST_ADJUST,2000,1,29,2,8,0,0,0,2000,1,29,2,0,0,0,0] );

addNewTestCase(this, new Date(2000,1,28,24,0,0,0),
"new Date(2000,1,28,24,0,0,0)",
[this.UTC_FEB_29_2000 - this.PST_ADJUST,2000,1,29,2,8,0,0,0,2000,1,29,2,0,0,0,0] );

//test();

function addNewTestCase(obj, DateCase, DateString, ResultArray ) {
//adjust hard-coded ResultArray for tester's timezone instead of PST
obj.adjustResultArray(ResultArray);


obj.TestCase( SECTION, DateString+".getTime()", ResultArray[TIME],       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()", ResultArray[TIME],       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      ResultArray[UTC_YEAR],  DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         ResultArray[UTC_MONTH],  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          ResultArray[UTC_DATE],   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           ResultArray[UTC_DAY],    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         ResultArray[UTC_HOURS],  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       ResultArray[UTC_MINUTES],DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       ResultArray[UTC_SECONDS],DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  ResultArray[UTC_MS],     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         ResultArray[YEAR],       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            ResultArray[MONTH],      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             ResultArray[DATE],       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              ResultArray[DAY],        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            ResultArray[HOURS],      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          ResultArray[MINUTES],    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          ResultArray[SECONDS],    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     ResultArray[MS],         DateCase.getMilliseconds() );
}

},

/**
File Name:          15_9_3_1__4.js
ECMA Section:       15.9.3.1 new Date (year, month, date, hours, minutes, seconds, ms)
Description:        The [[Prototype]] property of the newly constructed
object is set to the original Date prototype object,
the one that is the initial value of Date.prototype.

The [[Class]] property of the newly constructed object
is set as follows:
1. Call ToNumber(year)
2. Call ToNumber(month)
3. Call ToNumber(date)
4. Call ToNumber(hours)
5. Call ToNumber(minutes)
6. Call ToNumber(seconds)
7. Call ToNumber(ms)
8.  If Result(1) is NaN and 0 <= ToInteger(Result(1)) <=
99, Result(8) is 1900+ToInteger(Result(1)); otherwise,
Result(8) is Result(1)
9.  Compute MakeDay(Result(8), Result(2), Result(3)
10. Compute MakeTime(Result(4), Result(5), Result(6),
Result(7)
11. Compute MakeDate(Result(9), Result(10))
12. Set the [[Value]] property of the newly constructed
object to TimeClip(UTC(Result(11))).


This tests the returned value of a newly constructed
Date object.

Author:             christine@netscape.com
Date:               7 july 1997
*/
test_15_9_3_1__4:function(){
var SECTION = "15.9.3.1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "new Date( year, month, date, hours, minutes, seconds, ms )";

var TIME        = 0;
var UTC_YEAR    = 1;
var UTC_MONTH   = 2;
var UTC_DATE    = 3;
var UTC_DAY     = 4;
var UTC_HOURS   = 5;
var UTC_MINUTES = 6;
var UTC_SECONDS = 7;
var UTC_MS      = 8;

var YEAR        = 9;
var MONTH       = 10;
var DATE        = 11;
var DAY         = 12;
var HOURS       = 13;
var MINUTES     = 14;
var SECONDS     = 15;
var MS          = 16;

//writeHeaderToLog( SECTION + " "+ TITLE);

// all the "ResultArrays" below are hard-coded to Pacific Standard Time values -

// Dates around 1900

addNewTestCase(this, new Date(1899,11,31,16,0,0,0),
"new Date(1899,11,31,16,0,0,0)",
[this.TIME_1900,1900,0,1,1,0,0,0,0,1899,11,31,0,16,0,0,0] );

addNewTestCase(this, new Date(1899,11,31,15,59,59,999),
"new Date(1899,11,31,15,59,59,999)",
[this.TIME_1900-1,1899,11,31,0,23,59,59,999,1899,11,31,0,15,59,59,999] );

addNewTestCase(this, new Date(1899,11,31,23,59,59,999),
"new Date(1899,11,31,23,59,59,999)",
[this.TIME_1900-this.PST_ADJUST-1,1900,0,1,1,7,59,59,999,1899,11,31,0,23,59,59,999] );

addNewTestCase(this, new Date(1900,0,1,0,0,0,0),
"new Date(1900,0,1,0,0,0,0)",
[this.TIME_1900-this.PST_ADJUST,1900,0,1,1,8,0,0,0,1900,0,1,1,0,0,0,0] );

addNewTestCase(this, new Date(1900,0,1,0,0,0,1),
"new Date(1900,0,1,0,0,0,1)",
[this.TIME_1900-this.PST_ADJUST+1,1900,0,1,1,8,0,0,1,1900,0,1,1,0,0,0,1] );

//test();

function addNewTestCase(obj, DateCase, DateString, ResultArray ) {
//adjust hard-coded ResultArray for tester's timezone instead of PST
obj.adjustResultArray(ResultArray);

obj.TestCase( SECTION, DateString+".getTime()", ResultArray[TIME],       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()", ResultArray[TIME],       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      ResultArray[UTC_YEAR],  DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         ResultArray[UTC_MONTH],  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          ResultArray[UTC_DATE],   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           ResultArray[UTC_DAY],    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         ResultArray[UTC_HOURS],  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       ResultArray[UTC_MINUTES],DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       ResultArray[UTC_SECONDS],DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  ResultArray[UTC_MS],     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         ResultArray[YEAR],       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            ResultArray[MONTH],      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             ResultArray[DATE],       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              ResultArray[DAY],        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            ResultArray[HOURS],      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          ResultArray[MINUTES],    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          ResultArray[SECONDS],    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     ResultArray[MS],         DateCase.getMilliseconds() );
}

},

/**
File Name:          15_9_3_1__5.js
ECMA Section:       15.9.3.1 new Date (year, month, date, hours, minutes, seconds, ms)
Description:        The [[Prototype]] property of the newly constructed
object is set to the original Date prototype object,
the one that is the initial value of Date.prototype.

The [[Class]] property of the newly constructed object
is set as follows:
1. Call ToNumber(year)
2. Call ToNumber(month)
3. Call ToNumber(date)
4. Call ToNumber(hours)
5. Call ToNumber(minutes)
6. Call ToNumber(seconds)
7. Call ToNumber(ms)
8.  If Result(1) is NaN and 0 <= ToInteger(Result(1)) <=
99, Result(8) is 1900+ToInteger(Result(1)); otherwise,
Result(8) is Result(1)
9.  Compute MakeDay(Result(8), Result(2), Result(3)
10. Compute MakeTime(Result(4), Result(5), Result(6),
Result(7)
11. Compute MakeDate(Result(9), Result(10))
12. Set the [[Value]] property of the newly constructed
object to TimeClip(UTC(Result(11))).


This tests the returned value of a newly constructed
Date object.

Author:             christine@netscape.com
Date:               7 july 1997
*/
test_15_9_3_1__5:function(){
var SECTION = "15.9.3.1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "new Date( year, month, date, hours, minutes, seconds, ms )";

var TIME        = 0;
var UTC_YEAR    = 1;
var UTC_MONTH   = 2;
var UTC_DATE    = 3;
var UTC_DAY     = 4;
var UTC_HOURS   = 5;
var UTC_MINUTES = 6;
var UTC_SECONDS = 7;
var UTC_MS      = 8;

var YEAR        = 9;
var MONTH       = 10;
var DATE        = 11;
var DAY         = 12;
var HOURS       = 13;
var MINUTES     = 14;
var SECONDS     = 15;
var MS          = 16;
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
//writeHeaderToLog( SECTION + " "+ TITLE);

// all the "ResultArrays" below are hard-coded to Pacific Standard Time values -

// Dates around 2005

addNewTestCase(this, new Date(2005,0,1,0,0,0,0),
"new Date(2005,0,1,0,0,0,0)",
[UTC_JAN_1_2005-this.PST_ADJUST,2005,0,1,6,8,0,0,0,2005,0,1,6,0,0,0,0] );

addNewTestCase(this, new Date(2004,11,31,16,0,0,0),
"new Date(2004,11,31,16,0,0,0)",
[UTC_JAN_1_2005,2005,0,1,6,0,0,0,0,2004,11,31,5,16,0,0,0] );

//test();

function addNewTestCase(obj, DateCase, DateString, ResultArray ) {
//adjust hard-coded ResultArray for tester's timezone instead of PST
obj.adjustResultArray(ResultArray);

obj.TestCase( SECTION, DateString+".getTime()", ResultArray[TIME],       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()", ResultArray[TIME],       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      ResultArray[UTC_YEAR],  DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         ResultArray[UTC_MONTH],  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          ResultArray[UTC_DATE],   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           ResultArray[UTC_DAY],    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         ResultArray[UTC_HOURS],  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       ResultArray[UTC_MINUTES],DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       ResultArray[UTC_SECONDS],DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  ResultArray[UTC_MS],     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         ResultArray[YEAR],       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            ResultArray[MONTH],      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             ResultArray[DATE],       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              ResultArray[DAY],        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            ResultArray[HOURS],      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          ResultArray[MINUTES],    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          ResultArray[SECONDS],    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     ResultArray[MS],         DateCase.getMilliseconds() );
}

},

/**
File Name:          15_9_3_2__1.js
ECMA Section:       15.9.3.1 new Date (year, month, date, hours, minutes, seconds, ms)
Description:        The [[Prototype]] property of the newly constructed
object is set to the original Date prototype object,
the one that is the initial value of Date.prototype.

The [[Class]] property of the newly constructed object
is set as follows:
1. Call ToNumber(year)
2. Call ToNumber(month)
3. Call ToNumber(date)
4. Call ToNumber(hours)
5. Call ToNumber(minutes)
6. Call ToNumber(seconds)
7. Call ToNumber(ms)
8.  If Result(1)is NaN and 0 <= ToInteger(Result(1)) <=
99, Result(8) is 1900+ToInteger(Result(1)); otherwise,
Result(8) is Result(1)
9.  Compute MakeDay(Result(8), Result(2), Result(3)
10. Compute MakeTime(Result(4), Result(5), Result(6),
Result(7)
11. Compute MakeDate(Result(9), Result(10))
12. Set the [[Value]] property of the newly constructed
object to TimeClip(UTC(Result(11))).


This tests the returned value of a newly constructed
Date object.

Author:             christine@netscape.com
Date:               7 july 1997
*/
test_15_9_3_2__1:function(){
var TIME        = 0;
var UTC_YEAR    = 1;
var UTC_MONTH   = 2;
var UTC_DATE    = 3;
var UTC_DAY     = 4;
var UTC_HOURS   = 5;
var UTC_MINUTES = 6;
var UTC_SECONDS = 7;
var UTC_MS      = 8;

var YEAR        = 9;
var MONTH       = 10;
var DATE        = 11;
var DAY         = 12;
var HOURS       = 13;
var MINUTES     = 14;
var SECONDS     = 15;
var MS          = 16;

//  for TCMS, the gTestcases array must be global.
var SECTION = "15.9.3.1";
var TITLE =   "Date( year, month, date, hours, minutes, seconds )";

//writeHeaderToLog( SECTION+" " +TITLE );

// Dates around 1970

addNewTestCase(this, new Date( 1969,11,31,15,59,59),
"new Date( 1969,11,31,15,59,59)",
[-1000,1969,11,31,3,23,59,59,0,1969,11,31,3,15,59,59,0] );

addNewTestCase(this, new Date( 1969,11,31,16,0,0),
"new Date( 1969,11,31,16,0,0)",
[0,1970,0,1,4,0,0,0,0,1969,11,31,3,16,0,0,0] );

addNewTestCase(this, new Date( 1969,11,31,23,59,59),
"new Date( 1969,11,31,23,59,59)",
[28799000,1970,0,1,4,7,59,59,0,1969,11,31,3,23,59,59,0] );

addNewTestCase(this, new Date( 1970, 0, 1, 0, 0, 0),
"new Date( 1970, 0, 1, 0, 0, 0)",
[28800000,1970,0,1,4,8,0,0,0,1970,0,1,4,0,0,0,0] );

addNewTestCase(this, new Date( 1969,11,31,16,0,0),
"new Date( 1969,11,31,16,0,0)",
[0,1970,0,1,4,0,0,0,0,1969,11,31,3,16,0,0,0] );

//test();

function addNewTestCase(obj, DateCase, DateString, ResultArray ) {
//adjust hard-coded ResultArray for tester's timezone instead of PST
obj.adjustResultArray(ResultArray);


obj.TestCase( SECTION, DateString+".getTime()", ResultArray[TIME],       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()", ResultArray[TIME],       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      ResultArray[UTC_YEAR],   DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         ResultArray[UTC_MONTH],  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          ResultArray[UTC_DATE],   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           ResultArray[UTC_DAY],    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         ResultArray[UTC_HOURS],  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       ResultArray[UTC_MINUTES],DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       ResultArray[UTC_SECONDS],DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  ResultArray[UTC_MS],     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         ResultArray[YEAR],       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            ResultArray[MONTH],      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             ResultArray[DATE],       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              ResultArray[DAY],        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            ResultArray[HOURS],      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          ResultArray[MINUTES],    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          ResultArray[SECONDS],    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     ResultArray[MS],         DateCase.getMilliseconds() );

}

},

/**
File Name:          15_9_3_2__2.js
ECMA Section:       15.9.3.1 new Date (year, month, date, hours, minutes, seconds, ms)
Description:        The [[Prototype]] property of the newly constructed
object is set to the original Date prototype object,
the one that is the initial value of Date.prototype.

The [[Class]] property of the newly constructed object
is set as follows:
1. Call ToNumber(year)
2. Call ToNumber(month)
3. Call ToNumber(date)
4. Call ToNumber(hours)
5. Call ToNumber(minutes)
6. Call ToNumber(seconds)
7. Call ToNumber(ms)
8.  If Result(1)is NaN and 0 <= ToInteger(Result(1)) <=
99, Result(8) is 1900+ToInteger(Result(1)); otherwise,
Result(8) is Result(1)
9.  Compute MakeDay(Result(8), Result(2), Result(3)
10. Compute MakeTime(Result(4), Result(5), Result(6),
Result(7)
11. Compute MakeDate(Result(9), Result(10))
12. Set the [[Value]] property of the newly constructed
object to TimeClip(UTC(Result(11))).


This tests the returned value of a newly constructed
Date object.

Author:             christine@netscape.com
Date:               7 july 1997
*/
test_15_9_3_2__2:function(){
var TIME        = 0;
var UTC_YEAR    = 1;
var UTC_MONTH   = 2;
var UTC_DATE    = 3;
var UTC_DAY     = 4;
var UTC_HOURS   = 5;
var UTC_MINUTES = 6;
var UTC_SECONDS = 7;
var UTC_MS      = 8;

var YEAR        = 9;
var MONTH       = 10;
var DATE        = 11;
var DAY         = 12;
var HOURS       = 13;
var MINUTES     = 14;
var SECONDS     = 15;
var MS          = 16;

//  for TCMS, the gTestcases array must be global.
var SECTION = "15.9.3.1";
var TITLE =   "Date( year, month, date, hours, minutes, seconds )";

//writeHeaderToLog( SECTION+" " +TITLE );

// Dates around 2000

addNewTestCase(this, new Date( 1999,11,31,15,59,59),
"new Date( 1999,11,31,15,59,59)",
[946684799000,1999,11,31,5,23,59,59,0,1999,11,31,5,15,59,59,0] );

addNewTestCase(this, new Date( 1999,11,31,16,0,0),
"new Date( 1999,11,31,16,0,0)",
[946684800000,2000,0,1,6,0,0,0,0,1999,11,31,5, 16,0,0,0] );

addNewTestCase(this, new Date( 2000,0,1,0,0,0),
"new Date( 2000,0,1,0,0,0)",
[946713600000,2000,0,1,6,8,0,0,0,2000,0,1,6,0,0,0,0] );

//test();

function addNewTestCase(obj, DateCase, DateString, ResultArray ) {
//adjust hard-coded ResultArray for tester's timezone instead of PST
obj.adjustResultArray(ResultArray);

obj.TestCase( SECTION, DateString+".getTime()", ResultArray[TIME],       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()", ResultArray[TIME],       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      ResultArray[UTC_YEAR],   DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         ResultArray[UTC_MONTH],  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          ResultArray[UTC_DATE],   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           ResultArray[UTC_DAY],    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         ResultArray[UTC_HOURS],  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       ResultArray[UTC_MINUTES],DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       ResultArray[UTC_SECONDS],DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  ResultArray[UTC_MS],     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         ResultArray[YEAR],       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            ResultArray[MONTH],      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             ResultArray[DATE],       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              ResultArray[DAY],        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            ResultArray[HOURS],      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          ResultArray[MINUTES],    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          ResultArray[SECONDS],    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     ResultArray[MS],         DateCase.getMilliseconds() );

}

},

/**
File Name:          15_9_3_2__3.js
ECMA Section:       15.9.3.1 new Date (year, month, date, hours, minutes, seconds, ms)
Description:        The [[Prototype]] property of the newly constructed
object is set to the original Date prototype object,
the one that is the initial value of Date.prototype.

The [[Class]] property of the newly constructed object
is set as follows:
1. Call ToNumber(year)
2. Call ToNumber(month)
3. Call ToNumber(date)
4. Call ToNumber(hours)
5. Call ToNumber(minutes)
6. Call ToNumber(seconds)
7. Call ToNumber(ms)
8.  If Result(1)is NaN and 0 <= ToInteger(Result(1)) <=
99, Result(8) is 1900+ToInteger(Result(1)); otherwise,
Result(8) is Result(1)
9.  Compute MakeDay(Result(8), Result(2), Result(3)
10. Compute MakeTime(Result(4), Result(5), Result(6),
Result(7)
11. Compute MakeDate(Result(9), Result(10))
12. Set the [[Value]] property of the newly constructed
object to TimeClip(UTC(Result(11))).


This tests the returned value of a newly constructed
Date object.

Author:             christine@netscape.com
Date:               7 july 1997
*/
test_15_9_3_2__3:function(){
var TIME        = 0;
var UTC_YEAR    = 1;
var UTC_MONTH   = 2;
var UTC_DATE    = 3;
var UTC_DAY     = 4;
var UTC_HOURS   = 5;
var UTC_MINUTES = 6;
var UTC_SECONDS = 7;
var UTC_MS      = 8;

var YEAR        = 9;
var MONTH       = 10;
var DATE        = 11;
var DAY         = 12;
var HOURS       = 13;
var MINUTES     = 14;
var SECONDS     = 15;
var MS          = 16;

//  for TCMS, the gTestcases array must be global.
var SECTION = "15.9.3.1";
var TITLE =   "Date( year, month, date, hours, minutes, seconds )";

//writeHeaderToLog( SECTION+" " +TITLE );

// Dates around 1900

addNewTestCase(this, new Date(1899,11,31,16,0,0),
"new Date(1899,11,31,16,0,0)",
[-2208988800000,1900,0,1,1,0,0,0,0,1899,11,31,0,16,0,0,0] );

addNewTestCase(this, new Date(1899,11,31,15,59,59),
"new Date(1899,11,31,15,59,59)",
[-2208988801000,1899,11,31,0,23,59,59,0,1899,11,31,0,15,59,59,0] );

addNewTestCase(this, new Date(1900,0,1,0,0,0),
"new Date(1900,0,1,0,0,0)",
[-2208960000000,1900,0,1,1,8,0,0,0,1900,0,1,1,0,0,0,0] );

addNewTestCase(this, new Date(1900,0,1,0,0,1),
"new Date(1900,0,1,0,0,1)",
[-2208959999000,1900,0,1,1,8,0,1,0,1900,0,1,1,0,0,1,0] );

//test();

function addNewTestCase(obj, DateCase, DateString, ResultArray ) {
//adjust hard-coded ResultArray for tester's timezone instead of PST
obj.adjustResultArray(ResultArray);

obj.TestCase( SECTION, DateString+".getTime()", ResultArray[TIME],       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()", ResultArray[TIME],       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      ResultArray[UTC_YEAR],   DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         ResultArray[UTC_MONTH],  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          ResultArray[UTC_DATE],   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           ResultArray[UTC_DAY],    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         ResultArray[UTC_HOURS],  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       ResultArray[UTC_MINUTES],DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       ResultArray[UTC_SECONDS],DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  ResultArray[UTC_MS],     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         ResultArray[YEAR],       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            ResultArray[MONTH],      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             ResultArray[DATE],       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              ResultArray[DAY],        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            ResultArray[HOURS],      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          ResultArray[MINUTES],    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          ResultArray[SECONDS],    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     ResultArray[MS],         DateCase.getMilliseconds() );

}

},

/**
File Name:          15_9_3_2__4.js
ECMA Section:       15.9.3.1 new Date (year, month, date, hours, minutes, seconds, ms)
Description:        The [[Prototype]] property of the newly constructed
object is set to the original Date prototype object,
the one that is the initial value of Date.prototype.

The [[Class]] property of the newly constructed object
is set as follows:
1. Call ToNumber(year)
2. Call ToNumber(month)
3. Call ToNumber(date)
4. Call ToNumber(hours)
5. Call ToNumber(minutes)
6. Call ToNumber(seconds)
7. Call ToNumber(ms)
8.  If Result(1)is NaN and 0 <= ToInteger(Result(1)) <=
99, Result(8) is 1900+ToInteger(Result(1)); otherwise,
Result(8) is Result(1)
9.  Compute MakeDay(Result(8), Result(2), Result(3)
10. Compute MakeTime(Result(4), Result(5), Result(6),
Result(7)
11. Compute MakeDate(Result(9), Result(10))
12. Set the [[Value]] property of the newly constructed
object to TimeClip(UTC(Result(11))).


This tests the returned value of a newly constructed
Date object.

Author:             christine@netscape.com
Date:               7 july 1997
*/
test_15_9_3_2__4:function(){
var TIME        = 0;
var UTC_YEAR    = 1;
var UTC_MONTH   = 2;
var UTC_DATE    = 3;
var UTC_DAY     = 4;
var UTC_HOURS   = 5;
var UTC_MINUTES = 6;
var UTC_SECONDS = 7;
var UTC_MS      = 8;

var YEAR        = 9;
var MONTH       = 10;
var DATE        = 11;
var DAY         = 12;
var HOURS       = 13;
var MINUTES     = 14;
var SECONDS     = 15;
var MS          = 16;

//  for TCMS, the gTestcases array must be global.
var SECTION = "15.9.3.1";
var TITLE =   "Date( year, month, date, hours, minutes, seconds )";

//writeHeaderToLog( SECTION+" " +TITLE );

var PST_FEB_29_2000 = this.UTC_FEB_29_2000 + 8*this.msPerHour;

// Dates around Feb 29, 2000
addNewTestCase(this, new Date(2000,1,28,16,0,0,0),
"new Date(2000,1,28,16,0,0,0)",
[this.UTC_FEB_29_2000,2000,1,29,2,0,0,0,0,2000,1,28,1,16,0,0,0,0] );

addNewTestCase(this, new Date(2000,1,29,0,0,0,0),
"new Date(2000,1,29,0,0,0,0)",
[PST_FEB_29_2000,2000,1,29,2,8,0,0,0,2000,1,29,2,0,0,0,0] );

addNewTestCase(this, new Date(2000,1,29,24,0,0,0),
"new Date(2000,1,29,24,0,0,0)",
[PST_FEB_29_2000+this.msPerDay,2000,2,1,3,8,0,0,0,2000,2,1,3,0,0,0,0] );

//test();

function addNewTestCase(obj, DateCase, DateString, ResultArray ) {
//adjust hard-coded ResultArray for tester's timezone instead of PST
obj.adjustResultArray(ResultArray);

obj.TestCase( SECTION, DateString+".getTime()", ResultArray[TIME],       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()", ResultArray[TIME],       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      ResultArray[UTC_YEAR],   DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         ResultArray[UTC_MONTH],  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          ResultArray[UTC_DATE],   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           ResultArray[UTC_DAY],    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         ResultArray[UTC_HOURS],  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       ResultArray[UTC_MINUTES],DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       ResultArray[UTC_SECONDS],DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  ResultArray[UTC_MS],     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         ResultArray[YEAR],       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            ResultArray[MONTH],      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             ResultArray[DATE],       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              ResultArray[DAY],        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            ResultArray[HOURS],      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          ResultArray[MINUTES],    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          ResultArray[SECONDS],    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     ResultArray[MS],         DateCase.getMilliseconds() );

}

},

/**
File Name:          15_9_3_2__5.js
ECMA Section:       15.9.3.1 new Date (year, month, date, hours, minutes, seconds, ms)
Description:        The [[Prototype]] property of the newly constructed
object is set to the original Date prototype object,
the one that is the initial value of Date.prototype.

The [[Class]] property of the newly constructed object
is set as follows:
1. Call ToNumber(year)
2. Call ToNumber(month)
3. Call ToNumber(date)
4. Call ToNumber(hours)
5. Call ToNumber(minutes)
6. Call ToNumber(seconds)
7. Call ToNumber(ms)
8.  If Result(1)is NaN and 0 <= ToInteger(Result(1)) <=
99, Result(8) is 1900+ToInteger(Result(1)); otherwise,
Result(8) is Result(1)
9.  Compute MakeDay(Result(8), Result(2), Result(3)
10. Compute MakeTime(Result(4), Result(5), Result(6),
Result(7)
11. Compute MakeDate(Result(9), Result(10))
12. Set the [[Value]] property of the newly constructed
object to TimeClip(UTC(Result(11))).


This tests the returned value of a newly constructed
Date object.

Author:             christine@netscape.com
Date:               7 july 1997
*/
test_15_9_3_2__5:function(){
var TIME        = 0;
var UTC_YEAR    = 1;
var UTC_MONTH   = 2;
var UTC_DATE    = 3;
var UTC_DAY     = 4;
var UTC_HOURS   = 5;
var UTC_MINUTES = 6;
var UTC_SECONDS = 7;
var UTC_MS      = 8;

var YEAR        = 9;
var MONTH       = 10;
var DATE        = 11;
var DAY         = 12;
var HOURS       = 13;
var MINUTES     = 14;
var SECONDS     = 15;
var MS          = 16;
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
//  for TCMS, the gTestcases array must be global.
var SECTION = "15.9.3.1";
var TITLE =   "Date( year, month, date, hours, minutes, seconds )";

//writeHeaderToLog( SECTION+" " +TITLE );

// Dates around Jan 1, 2005

var PST_JAN_1_2005 = UTC_JAN_1_2005 + 8*this.msPerHour;

addNewTestCase(this, new Date(2005,0,1,0,0,0,0),
"new Date(2005,0,1,0,0,0,0)",
[PST_JAN_1_2005,2005,0,1,6,8,0,0,0,2005,0,1,6,0,0,0,0] );

addNewTestCase(this, new Date(2004,11,31,16,0,0,0),
"new Date(2004,11,31,16,0,0,0)",
[UTC_JAN_1_2005,2005,0,1,6,0,0,0,0,2004,11,31,5,16,0,0,0] );

//test();

function addNewTestCase(obj, DateCase, DateString, ResultArray ) {
//adjust hard-coded ResultArray for tester's timezone instead of PST
obj.adjustResultArray(ResultArray);

obj.TestCase( SECTION, DateString+".getTime()", ResultArray[TIME],       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()", ResultArray[TIME],       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      ResultArray[UTC_YEAR],   DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         ResultArray[UTC_MONTH],  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          ResultArray[UTC_DATE],   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           ResultArray[UTC_DAY],    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         ResultArray[UTC_HOURS],  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       ResultArray[UTC_MINUTES],DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       ResultArray[UTC_SECONDS],DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  ResultArray[UTC_MS],     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         ResultArray[YEAR],       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            ResultArray[MONTH],      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             ResultArray[DATE],       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              ResultArray[DAY],        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            ResultArray[HOURS],      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          ResultArray[MINUTES],    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          ResultArray[SECONDS],    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     ResultArray[MS],         DateCase.getMilliseconds() );

}

},

/**
File Name:          15_9_3_8__1.js
ECMA Section:       15.9.3.8 The Date Constructor
new Date( value )
Description:        The [[Prototype]] property of the newly constructed
object is set to the original Date prototype object,
the one that is the initial valiue of Date.prototype.

The [[Class]] property of the newly constructed object is
set to "Date".

The [[Value]] property of the newly constructed object is
set as follows:

1. Call ToPrimitive(value)
2. If Type( Result(1) ) is String, then go to step 5.
3. Let V be  ToNumber( Result(1) ).
4. Set the [[Value]] property of the newly constructed
object to TimeClip(V) and return.
5. Parse Result(1) as a date, in exactly the same manner
as for the parse method.  Let V be the time value for
this date.
6. Go to step 4.

Author:             christine@netscape.com
Date:               28 october 1997
Version:            9706

*/
test_15_9_3_8__1:function(){
var VERSION = "ECMA_1";
//startTest();
var SECTION = "15.9.3.8";
var TYPEOF  = "object";

var TIME        = 0;
var UTC_YEAR    = 1;
var UTC_MONTH   = 2;
var UTC_DATE    = 3;
var UTC_DAY     = 4;
var UTC_HOURS   = 5;
var UTC_MINUTES = 6;
var UTC_SECONDS = 7;
var UTC_MS      = 8;

var YEAR        = 9;
var MONTH       = 10;
var DATE        = 11;
var DAY         = 12;
var HOURS       = 13;
var MINUTES     = 14;
var SECONDS     = 15;
var MS          = 16;


//  for TCMS, the gTestcases array must be global.
var gTc= 0;
var TITLE = "Date constructor:  new Date( value )";
var SECTION = "15.9.3.8";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION +" " + TITLE );

// all the "ResultArrays" below are hard-coded to Pacific Standard Time values -
var TZ_ADJUST = -this.TZ_PST * this.msPerHour;


// Dates around 1970
addNewTestCase(this, new Date(0),
"new Date(0)",
[0,1970,0,1,4,0,0,0,0,1969,11,31,3,16,0,0,0] );

addNewTestCase(this, new Date(1),
"new Date(1)",
[1,1970,0,1,4,0,0,0,1,1969,11,31,3,16,0,0,1] );

addNewTestCase(this, new Date(true),
"new Date(true)",
[1,1970,0,1,4,0,0,0,1,1969,11,31,3,16,0,0,1] );

addNewTestCase(this, new Date(false),
"new Date(false)",
[0,1970,0,1,4,0,0,0,0,1969,11,31,3,16,0,0,0] );

addNewTestCase(this, new Date( (new Date(0)).toString() ),
"new Date(\""+ (new Date(0)).toString()+"\" )",
[0,1970,0,1,4,0,0,0,0,1969,11,31,3,16,0,0,0] );

//test();

function addNewTestCase(obj, DateCase, DateString, ResultArray ) {
//adjust hard-coded ResultArray for tester's timezone instead of PST
obj.adjustResultArray(ResultArray, 'msMode');


obj.TestCase( SECTION, DateString+".getTime()", ResultArray[TIME],       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()", ResultArray[TIME],       DateCase.valueOf() );
obj.TestCase( SECTION, DateString+".getUTCFullYear()",      ResultArray[UTC_YEAR], DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         ResultArray[UTC_MONTH],  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          ResultArray[UTC_DATE],   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           ResultArray[UTC_DAY],    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         ResultArray[UTC_HOURS],  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       ResultArray[UTC_MINUTES],DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       ResultArray[UTC_SECONDS],DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  ResultArray[UTC_MS],     DateCase.getUTCMilliseconds() );
obj.TestCase( SECTION, DateString+".getFullYear()",         ResultArray[YEAR],       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            ResultArray[MONTH],      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             ResultArray[DATE],       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              ResultArray[DAY],        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            ResultArray[HOURS],      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          ResultArray[MINUTES],    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          ResultArray[SECONDS],    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     ResultArray[MS],         DateCase.getMilliseconds() );
}

},

/**
File Name:          15_9_3_8__2.js
ECMA Section:       15.9.3.8 The Date Constructor
new Date( value )
Description:        The [[Prototype]] property of the newly constructed
object is set to the original Date prototype object,
the one that is the initial valiue of Date.prototype.

The [[Class]] property of the newly constructed object is
set to "Date".

The [[Value]] property of the newly constructed object is
set as follows:

1. Call ToPrimitive(value)
2. If Type( Result(1) ) is String, then go to step 5.
3. Let V be  ToNumber( Result(1) ).
4. Set the [[Value]] property of the newly constructed
object to TimeClip(V) and return.
5. Parse Result(1) as a date, in exactly the same manner
as for the parse method.  Let V be the time value for
this date.
6. Go to step 4.

Author:             christine@netscape.com
Date:               28 october 1997
Version:            9706
*/
test_15_9_3_8__2:function(){
var VERSION = "ECMA_1";
//startTest();
var SECTION = "15.9.3.8";
var TYPEOF  = "object";

var TIME        = 0;
var UTC_YEAR    = 1;
var UTC_MONTH   = 2;
var UTC_DATE    = 3;
var UTC_DAY     = 4;
var UTC_HOURS   = 5;
var UTC_MINUTES = 6;
var UTC_SECONDS = 7;
var UTC_MS      = 8;

var YEAR        = 9;
var MONTH       = 10;
var DATE        = 11;
var DAY         = 12;
var HOURS       = 13;
var MINUTES     = 14;
var SECONDS     = 15;
var MS          = 16;


//  for TCMS, the gTestcases array must be global.
var gTc= 0;
var TITLE = "Date constructor:  new Date( value )";
var SECTION = "15.9.3.8";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION +" " + TITLE );

// all the "ResultArrays" below are hard-coded to Pacific Standard Time values -
var TZ_ADJUST =  -this.TZ_PST * this.msPerHour;

addNewTestCase(this, new Date((new Date(0)).toUTCString()),
"new Date(\""+ (new Date(0)).toUTCString()+"\" )",
[0,1970,0,1,4,0,0,0,0,1969,11,31,3,16,0,0,0] );

addNewTestCase(this, new Date((new Date(1)).toString()),
"new Date(\""+ (new Date(1)).toString()+"\" )",
[0,1970,0,1,4,0,0,0,0,1969,11,31,3,16,0,0,0] );

addNewTestCase(this, new Date( TZ_ADJUST ),
"new Date(" + TZ_ADJUST+")",
[TZ_ADJUST,1970,0,1,4,8,0,0,0,1970,0,1,4,0,0,0,0] );

addNewTestCase(this, new Date((new Date(TZ_ADJUST)).toString()),
"new Date(\""+ (new Date(TZ_ADJUST)).toString()+"\")",
[TZ_ADJUST,1970,0,1,4,8,0,0,0,1970,0,1,4,0,0,0,0] );


addNewTestCase(this, new Date( (new Date(TZ_ADJUST)).toUTCString() ),
"new Date(\""+ (new Date(TZ_ADJUST)).toUTCString()+"\")",
[TZ_ADJUST,1970,0,1,4,8,0,0,0,1970,0,1,4,0,0,0,0] );

//test();

function addNewTestCase(obj, DateCase, DateString, ResultArray ) {
//adjust hard-coded ResultArray for tester's timezone instead of PST
obj.adjustResultArray(ResultArray, 'msMode');

obj.TestCase( SECTION, DateString+".getTime()", ResultArray[TIME],       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()", ResultArray[TIME],       DateCase.valueOf() );
obj.TestCase( SECTION, DateString+".getUTCFullYear()",      ResultArray[UTC_YEAR], DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         ResultArray[UTC_MONTH],  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          ResultArray[UTC_DATE],   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           ResultArray[UTC_DAY],    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         ResultArray[UTC_HOURS],  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       ResultArray[UTC_MINUTES],DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       ResultArray[UTC_SECONDS],DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  ResultArray[UTC_MS],     DateCase.getUTCMilliseconds() );
obj.TestCase( SECTION, DateString+".getFullYear()",         ResultArray[YEAR],       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            ResultArray[MONTH],      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             ResultArray[DATE],       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              ResultArray[DAY],        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            ResultArray[HOURS],      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          ResultArray[MINUTES],    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          ResultArray[SECONDS],    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     ResultArray[MS],         DateCase.getMilliseconds() );
}

},

/**
File Name:          15_9_3_8__3.js
ECMA Section:       15.9.3.8 The Date Constructor
new Date( value )
Description:        The [[Prototype]] property of the newly constructed
object is set to the original Date prototype object,
the one that is the initial valiue of Date.prototype.

The [[Class]] property of the newly constructed object is
set to "Date".

The [[Value]] property of the newly constructed object is
set as follows:

1. Call ToPrimitive(value)
2. If Type( Result(1) ) is String, then go to step 5.
3. Let V be  ToNumber( Result(1) ).
4. Set the [[Value]] property of the newly constructed
object to TimeClip(V) and return.
5. Parse Result(1) as a date, in exactly the same manner
as for the parse method.  Let V be the time value for
this date.
6. Go to step 4.

Author:             christine@netscape.com
Date:               28 october 1997
Version:            9706
*/
test_15_9_3_8__3:function(){
var VERSION = "ECMA_1";
//startTest();
var SECTION = "15.9.3.8";
var TYPEOF  = "object";

var TIME        = 0;
var UTC_YEAR    = 1;
var UTC_MONTH   = 2;
var UTC_DATE    = 3;
var UTC_DAY     = 4;
var UTC_HOURS   = 5;
var UTC_MINUTES = 6;
var UTC_SECONDS = 7;
var UTC_MS      = 8;

var YEAR        = 9;
var MONTH       = 10;
var DATE        = 11;
var DAY         = 12;
var HOURS       = 13;
var MINUTES     = 14;
var SECONDS     = 15;
var MS          = 16;


//  for TCMS, the gTestcases array must be global.
var gTc= 0;
var TITLE = "Date constructor:  new Date( value )";
var SECTION = "15.9.3.8";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION +" " + TITLE );

// all the "ResultArrays" below are hard-coded to Pacific Standard Time values -
var TZ_ADJUST =  -this.TZ_PST * this.msPerHour;


// Dates around 2000

addNewTestCase(this, new Date(this.TIME_2000+TZ_ADJUST),
"new Date(" +(this.TIME_2000+TZ_ADJUST)+")",
[this.TIME_2000+TZ_ADJUST,2000,0,1,6,8,0,0,0,2000,0,1,6,0,0,0,0] );

addNewTestCase(this, new Date(this.TIME_2000),
"new Date(" +this.TIME_2000+")",
[this.TIME_2000,2000,0,1,6,0,0,0,0,1999,11,31,5,16,0,0,0] );

addNewTestCase(this, new Date( (new Date(this.TIME_2000+TZ_ADJUST)).toString()),
"new Date(\"" +(new Date(this.TIME_2000+TZ_ADJUST)).toString()+"\")",
[this.TIME_2000+TZ_ADJUST,2000,0,1,6,8,0,0,0,2000,0,1,6,0,0,0,0] );

addNewTestCase(this, new Date((new Date(this.TIME_2000)).toString()),
"new Date(\"" +(new Date(this.TIME_2000)).toString()+"\")",
[this.TIME_2000,2000,0,1,6,0,0,0,0,1999,11,31,5,16,0,0,0] );


addNewTestCase(this,  new Date( (new Date(this.TIME_2000+TZ_ADJUST)).toUTCString()),
"new Date(\"" +(new Date(this.TIME_2000+TZ_ADJUST)).toUTCString()+"\")",
[this.TIME_2000+TZ_ADJUST,2000,0,1,6,8,0,0,0,2000,0,1,6,0,0,0,0] );

addNewTestCase(this, new Date( (new Date(this.TIME_2000)).toUTCString()),
"new Date(\"" +(new Date(this.TIME_2000)).toUTCString()+"\")",
[this.TIME_2000,2000,0,1,6,0,0,0,0,1999,11,31,5,16,0,0,0] );

//test();

function addNewTestCase(obj, DateCase, DateString, ResultArray ) {
//adjust hard-coded ResultArray for tester's timezone instead of PST
obj.adjustResultArray(ResultArray, 'msMode');

obj.TestCase( SECTION, DateString+".getTime()", ResultArray[TIME],       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()", ResultArray[TIME],       DateCase.valueOf() );
obj.TestCase( SECTION, DateString+".getUTCFullYear()",      ResultArray[UTC_YEAR], DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         ResultArray[UTC_MONTH],  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          ResultArray[UTC_DATE],   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           ResultArray[UTC_DAY],    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         ResultArray[UTC_HOURS],  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       ResultArray[UTC_MINUTES],DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       ResultArray[UTC_SECONDS],DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  ResultArray[UTC_MS],     DateCase.getUTCMilliseconds() );
obj.TestCase( SECTION, DateString+".getFullYear()",         ResultArray[YEAR],       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            ResultArray[MONTH],      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             ResultArray[DATE],       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              ResultArray[DAY],        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            ResultArray[HOURS],      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          ResultArray[MINUTES],    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          ResultArray[SECONDS],    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     ResultArray[MS],         DateCase.getMilliseconds() );
}

},

/**
File Name:          15_9_3_8__4.js
ECMA Section:       15.9.3.8 The Date Constructor
new Date( value )
Description:        The [[Prototype]] property of the newly constructed
object is set to the original Date prototype object,
the one that is the initial valiue of Date.prototype.

The [[Class]] property of the newly constructed object is
set to "Date".

The [[Value]] property of the newly constructed object is
set as follows:

1. Call ToPrimitive(value)
2. If Type( Result(1) ) is String, then go to step 5.
3. Let V be  ToNumber( Result(1) ).
4. Set the [[Value]] property of the newly constructed
object to TimeClip(V) and return.
5. Parse Result(1) as a date, in exactly the same manner
as for the parse method.  Let V be the time value for
this date.
6. Go to step 4.

Author:             christine@netscape.com
Date:               28 october 1997
Version:            9706
*/
test_15_9_3_8__4:function(){
var VERSION = "ECMA_1";
//startTest();
var SECTION = "15.9.3.8";
var TYPEOF  = "object";

var TIME        = 0;
var UTC_YEAR    = 1;
var UTC_MONTH   = 2;
var UTC_DATE    = 3;
var UTC_DAY     = 4;
var UTC_HOURS   = 5;
var UTC_MINUTES = 6;
var UTC_SECONDS = 7;
var UTC_MS      = 8;

var YEAR        = 9;
var MONTH       = 10;
var DATE        = 11;
var DAY         = 12;
var HOURS       = 13;
var MINUTES     = 14;
var SECONDS     = 15;
var MS          = 16;


//  for TCMS, the gTestcases array must be global.
var gTc= 0;
var TITLE = "Date constructor:  new Date( value )";
var SECTION = "15.9.3.8";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION +" " + TITLE );

// all the "ResultArrays" below are hard-coded to Pacific Standard Time values -
var TZ_ADJUST =  -this.TZ_PST * this.msPerHour;

// Dates around Feb 29, 2000

var PST_FEB_29_2000 = this.UTC_FEB_29_2000 + TZ_ADJUST;

addNewTestCase(this, new Date(this.UTC_FEB_29_2000),
"new Date("+this.UTC_FEB_29_2000+")",
[this.UTC_FEB_29_2000,2000,1,29,2,0,0,0,0,2000,1,28,1,16,0,0,0] );

addNewTestCase(this, new Date(PST_FEB_29_2000),
"new Date("+PST_FEB_29_2000+")",
[PST_FEB_29_2000,2000,1,29,2,8,0,0,0,2000,1,29,2,0,0,0,0] );

addNewTestCase(this, new Date( (new Date(this.UTC_FEB_29_2000)).toString() ),
"new Date(\""+(new Date(this.UTC_FEB_29_2000)).toString()+"\")",
[this.UTC_FEB_29_2000,2000,1,29,2,0,0,0,0,2000,1,28,1,16,0,0,0] );

addNewTestCase(this, new Date( (new Date(PST_FEB_29_2000)).toString() ),
"new Date(\""+(new Date(PST_FEB_29_2000)).toString()+"\")",
[PST_FEB_29_2000,2000,1,29,2,8,0,0,0,2000,1,29,2,0,0,0,0] );


addNewTestCase(this, new Date( (new Date(this.UTC_FEB_29_2000)).toGMTString() ),
"new Date(\""+(new Date(this.UTC_FEB_29_2000)).toGMTString()+"\")",
[this.UTC_FEB_29_2000,2000,1,29,2,0,0,0,0,2000,1,28,1,16,0,0,0] );

addNewTestCase(this, new Date( (new Date(PST_FEB_29_2000)).toGMTString() ),
"new Date(\""+(new Date(PST_FEB_29_2000)).toGMTString()+"\")",
[PST_FEB_29_2000,2000,1,29,2,8,0,0,0,2000,1,29,2,0,0,0,0] );

//test();

function addNewTestCase(obj, DateCase, DateString, ResultArray ) {
//adjust hard-coded ResultArray for tester's timezone instead of PST
obj.adjustResultArray(ResultArray, 'msMode');

obj.TestCase( SECTION, DateString+".getTime()", ResultArray[TIME],       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()", ResultArray[TIME],       DateCase.valueOf() );
obj.TestCase( SECTION, DateString+".getUTCFullYear()",      ResultArray[UTC_YEAR], DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         ResultArray[UTC_MONTH],  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          ResultArray[UTC_DATE],   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           ResultArray[UTC_DAY],    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         ResultArray[UTC_HOURS],  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       ResultArray[UTC_MINUTES],DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       ResultArray[UTC_SECONDS],DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  ResultArray[UTC_MS],     DateCase.getUTCMilliseconds() );
obj.TestCase( SECTION, DateString+".getFullYear()",         ResultArray[YEAR],       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            ResultArray[MONTH],      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             ResultArray[DATE],       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              ResultArray[DAY],        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            ResultArray[HOURS],      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          ResultArray[MINUTES],    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          ResultArray[SECONDS],    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     ResultArray[MS],         DateCase.getMilliseconds() );
}

},

/**
File Name:          15_9_3_8__5.js
ECMA Section:       15.9.3.8 The Date Constructor
new Date( value )
Description:        The [[Prototype]] property of the newly constructed
object is set to the original Date prototype object,
the one that is the initial valiue of Date.prototype.

The [[Class]] property of the newly constructed object is
set to "Date".

The [[Value]] property of the newly constructed object is
set as follows:

1. Call ToPrimitive(value)
2. If Type( Result(1) ) is String, then go to step 5.
3. Let V be  ToNumber( Result(1) ).
4. Set the [[Value]] property of the newly constructed
object to TimeClip(V) and return.
5. Parse Result(1) as a date, in exactly the same manner
as for the parse method.  Let V be the time value for
this date.
6. Go to step 4.

Author:             christine@netscape.com
Date:               28 october 1997
Version:            9706

*/
test_15_9_3_8__5:function(){
var VERSION = "ECMA_1";
//startTest();
var SECTION = "15.9.3.8";
var TYPEOF  = "object";

var TIME        = 0;
var UTC_YEAR    = 1;
var UTC_MONTH   = 2;
var UTC_DATE    = 3;
var UTC_DAY     = 4;
var UTC_HOURS   = 5;
var UTC_MINUTES = 6;
var UTC_SECONDS = 7;
var UTC_MS      = 8;

var YEAR        = 9;
var MONTH       = 10;
var DATE        = 11;
var DAY         = 12;
var HOURS       = 13;
var MINUTES     = 14;
var SECONDS     = 15;
var MS          = 16;


//  for TCMS, the gTestcases array must be global.
var gTc= 0;
var TITLE = "Date constructor:  new Date( value )";
var SECTION = "15.9.3.8";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION +" " + TITLE );

// all the "ResultArrays" below are hard-coded to Pacific Standard Time values -
var TZ_ADJUST =  -this.TZ_PST * this.msPerHour;


// Dates around 1900

var PST_1900 = this.TIME_1900 + 8*this.msPerHour;

addNewTestCase(this, new Date( this.TIME_1900 ),
"new Date("+this.TIME_1900+")",
[this.TIME_1900,1900,0,1,1,0,0,0,0,1899,11,31,0,16,0,0,0] );

addNewTestCase(this, new Date(PST_1900),
"new Date("+PST_1900+")",
[ PST_1900,1900,0,1,1,8,0,0,0,1900,0,1,1,0,0,0,0] );

addNewTestCase(this, new Date( (new Date(this.TIME_1900)).toString() ),
"new Date(\""+(new Date(this.TIME_1900)).toString()+"\")",
[this.TIME_1900,1900,0,1,1,0,0,0,0,1899,11,31,0,16,0,0,0] );

addNewTestCase(this, new Date( (new Date(PST_1900)).toString() ),
"new Date(\""+(new Date(PST_1900 )).toString()+"\")",
[ PST_1900,1900,0,1,1,8,0,0,0,1900,0,1,1,0,0,0,0] );

addNewTestCase(this, new Date( (new Date(this.TIME_1900)).toUTCString() ),
"new Date(\""+(new Date(this.TIME_1900)).toUTCString()+"\")",
[this.TIME_1900,1900,0,1,1,0,0,0,0,1899,11,31,0,16,0,0,0] );

addNewTestCase(this, new Date( (new Date(PST_1900)).toUTCString() ),
"new Date(\""+(new Date(PST_1900 )).toUTCString()+"\")",
[ PST_1900,1900,0,1,1,8,0,0,0,1900,0,1,1,0,0,0,0] );

//test();

function addNewTestCase(obj, DateCase, DateString, ResultArray ) {
//adjust hard-coded ResultArray for tester's timezone instead of PST
obj.adjustResultArray(ResultArray, 'msMode');

obj.TestCase( SECTION, DateString+".getTime()", ResultArray[TIME],       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()", ResultArray[TIME],       DateCase.valueOf() );
obj.TestCase( SECTION, DateString+".getUTCFullYear()",      ResultArray[UTC_YEAR], DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         ResultArray[UTC_MONTH],  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          ResultArray[UTC_DATE],   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           ResultArray[UTC_DAY],    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         ResultArray[UTC_HOURS],  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       ResultArray[UTC_MINUTES],DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       ResultArray[UTC_SECONDS],DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  ResultArray[UTC_MS],     DateCase.getUTCMilliseconds() );
obj.TestCase( SECTION, DateString+".getFullYear()",         ResultArray[YEAR],       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            ResultArray[MONTH],      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             ResultArray[DATE],       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              ResultArray[DAY],        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            ResultArray[HOURS],      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          ResultArray[MINUTES],    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          ResultArray[SECONDS],    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     ResultArray[MS],         DateCase.getMilliseconds() );
}

},

/**
*  File Name:          15_9_4_2__1.js
*  Reference:          http://bugzilla.mozilla.org/show_bug.cgi?id=4088
*  Description:        Date parsing gets 12:30 AM wrong.
*  New behavior:
*  js> d = new Date('1/1/1999 13:30 AM')
* Invalid Date
* js> d = new Date('1/1/1999 13:30 PM')
* Invalid Date
* js> d = new Date('1/1/1999 12:30 AM')
* Fri Jan 01 00:30:00 GMT-0800 (PST) 1999
* js> d = new Date('1/1/1999 12:30 PM')
* Fri Jan 01 12:30:00 GMT-0800 (PST) 1999
*  Author:             christine@netscape.com

*/
test_15_9_4_2__1:function(){
var SECTION = "15.9.4.2-1";       // provide a document reference (ie, ECMA section)
var VERSION = "ECMA"; // Version of JavaScript or ECMA
var TITLE   = "Regression Test for Date.parse";       // Provide ECMA section title or a description
//var BUGNUMBER = "http://bugzilla.mozilla.org/show_bug.cgi?id=4088";     // Provide URL to bugsplat or bugzilla report

//startTest();               // leave this alone

this.TestCase( SECTION,  "new Date('1/1/1999 12:30 AM').toString()",
new Date(1999,0,1,0,30).toString(),
new Date('1/1/1999 12:30 AM').toString() );

this.TestCase( SECTION,  "new Date('1/1/1999 12:30 PM').toString()",
new Date( 1999,0,1,12,30 ).toString(),
new Date('1/1/1999 12:30 PM').toString() );

this.TestCase( SECTION,  "new Date('1/1/1999 13:30 AM')",
"Invalid Date",
new Date('1/1/1999 13:30 AM').toString() );


this.TestCase( SECTION,  "new Date('1/1/1999 13:30 PM')",
"Invalid Date",
new Date('1/1/1999 13:30 PM').toString() );

//test();       // leave this alone.  this executes the test cases and
// displays results.

},

/**
File Name:          15_9_4_2.js
ECMA Section:       15.9.4.2 Date.parse()
Description:        The parse() function applies the to ToString() operator
to its argument and interprets the resulting string as
a date.  It returns a number, the UTC time value
corresponding to the date.

The string may be interpreted as a local time, a UTC
time, or a time in some other time zone, depending on
the contents of the string.

(need to test strings containing stuff with the time
zone specified, and verify that parse() returns the
correct GMT time)

so for any Date object x, all of these things should
be equal:

value                       tested in function:
x.valueOf()                 test_value()
Date.parse(x.toString())    test_tostring()
Date.parse(x.toGMTString()) test_togmt()

Date.parse(x.toLocaleString()) is not required to
produce the same number value as the preceeding three
expressions.  in general the value produced by
Date.parse is implementation dependent when given any
string value that could not be produced in that
implementation by the toString or toGMTString method.

value                           tested in function:
Date.parse( x.toLocaleString()) test_tolocale()

Author:             christine@netscape.com
Date:               10 july 1997
*/
test_15_9_4_2:function(){
var VERSION = "ECMA_1";
//startTest();
var SECTION = "15.9.4.2";
var TITLE   = "Date.parse()";

var TIME        = 0;
var UTC_YEAR    = 1;
var UTC_MONTH   = 2;
var UTC_DATE    = 3;
var UTC_DAY     = 4;
var UTC_HOURS   = 5;
var UTC_MINUTES = 6;
var UTC_SECONDS = 7;
var UTC_MS      = 8;

var YEAR        = 9;
var MONTH       = 10;
var DATE        = 11;
var DAY         = 12;
var HOURS       = 13;
var MINUTES     = 14;
var SECONDS     = 15;
var MS          = 16;
var TYPEOF  = "object";

//  for TCMS, the gTestcases array must be global.
//writeHeaderToLog("15.9.4.2 Date.parse()" );

// Dates around 1970

addNewTestCase(this, new Date(0),
"new Date(0)",
[0,1970,0,1,4,0,0,0,0,1969,11,31,3,16,0,0,0] );

addNewTestCase(this, new Date(-1),
"new Date(-1)",
[-1,1969,11,31,3,23,59,59,999,1969,11,31,3,15,59,59,999] );
addNewTestCase(this, new Date(28799999),
"new Date(28799999)",
[28799999,1970,0,1,4,7,59,59,999,1969,11,31,3,23,59,59,999] );
addNewTestCase(this, new Date(28800000),
"new Date(28800000)",
[28800000,1970,0,1,4,8,0,0,0,1970,0,1,4,0,0,0,0] );

// Dates around 2000

addNewTestCase(this, new Date(946684799999),
"new Date(946684799999)",
[946684799999,1999,11,31,5,23,59,59,999,1999,11,31,5,15,59,59,999] );
addNewTestCase(this, new Date(946713599999),
"new Date(946713599999)",
[946713599999,2000,0,1,6,7,59,59,999,1999,11,31,5,23,59,59,999] );
addNewTestCase(this, new Date(946684800000),
"new Date(946684800000)",
[946684800000,2000,0,1,6,0,0,0,0,1999,11,31,5, 16,0,0,0] );
addNewTestCase(this, new Date(946713600000),
"new Date(946713600000)",
[946713600000,2000,0,1,6,8,0,0,0,2000,0,1,6,0,0,0,0] );

// Dates around 1900

addNewTestCase(this, new Date(-2208988800000),
"new Date(-2208988800000)",
[-2208988800000,1900,0,1,1,0,0,0,0,1899,11,31,0,16,0,0,0] );

addNewTestCase(this, new Date(-2208988800001),
"new Date(-2208988800001)",
[-2208988800001,1899,11,31,0,23,59,59,999,1899,11,31,0,15,59,59,999] );

addNewTestCase(this, new Date(-2208960000001),
"new Date(-2208960000001)",
[-2208960000001,1900,0,1,1,7,59,59,0,1899,11,31,0,23,59,59,999] );
addNewTestCase(this, new Date(-2208960000000),
"new Date(-2208960000000)",
[-2208960000000,1900,0,1,1,8,0,0,0,1900,0,1,1,0,0,0,0] );
addNewTestCase(this, new Date(-2208959999999),
"new Date(-2208959999999)",
[-2208959999999,1900,0,1,1,8,0,0,1,1900,0,1,1,0,0,0,1] );

// Dates around Feb 29, 2000

var PST_FEB_29_2000 = this.UTC_FEB_29_2000 + 8*this.msPerHour;

addNewTestCase(this, new Date(this.UTC_FEB_29_2000),
"new Date(" + this.UTC_FEB_29_2000 +")",
[this.UTC_FEB_29_2000,2000,0,1,6,0,0,0,0,1999,11,31,5,16,0,0,0] );
addNewTestCase(this, new Date(PST_FEB_29_2000),
"new Date(" + PST_FEB_29_2000 +")",
[PST_FEB_29_2000,2000,0,1,6,8.0,0,0,2000,0,1,6,0,0,0,0]);

// Dates around Jan 1 2005

var PST_JAN_1_2005 = UTC_JAN_1_2005 + 8*this.msPerHour;
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
addNewTestCase(this, new Date(UTC_JAN_1_2005),
"new Date("+ UTC_JAN_1_2005 +")",
[UTC_JAN_1_2005,2005,0,1,6,0,0,0,0,2004,11,31,5,16,0,0,0] );
addNewTestCase(this, new Date(PST_JAN_1_2005),
"new Date("+ PST_JAN_1_2005 +")",
[PST_JAN_1_2005,2005,0,1,6,8,0,0,0,2005,0,1,6,0,0,0,0] );


//test();

function addNewTestCase(obj, DateCase, DateString, ResultArray ) {
DateCase = DateCase;

obj.TestCase( SECTION, DateString+".getTime()", ResultArray[TIME],       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()", ResultArray[TIME],       DateCase.valueOf() );
obj.TestCase( SECTION, "Date.parse(" + DateCase.toString() +")",    Math.floor(ResultArray[TIME]/1000)*1000,  Date.parse(DateCase.toString()) );
obj.TestCase( SECTION, "Date.parse(" + DateCase.toGMTString() +")", Math.floor(ResultArray[TIME]/1000)*1000,  Date.parse(DateCase.toGMTString()) );
}

},

/**
File Name:          15_9_4_3.js
Summary :          Date.UTC( year, month, date, hours, minutes, seconds, ms )
*/
test_15_9_4_3:function(){
var SECTION = "15.9.4.3";
var TITLE = "Date.UTC( year, month, date, hours, minutes, seconds, ms )";

// Dates around 1970

addNewTestCase(this, Date.UTC( 1970,0,1,0,0,0,0),
"Date.UTC( 1970,0,1,0,0,0,0)",
utc(this, 1970,0,1,0,0,0,0) );

addNewTestCase(this, Date.UTC( 1969,11,31,23,59,59,999),
"Date.UTC( 1969,11,31,23,59,59,999)",
utc(this, 1969,11,31,23,59,59,999) );
addNewTestCase(this, Date.UTC( 1972,1,29,23,59,59,999),
"Date.UTC( 1972,1,29,23,59,59,999)",
utc(this, 1972,1,29,23,59,59,999) );
addNewTestCase(this, Date.UTC( 1972,2,1,23,59,59,999),
"Date.UTC( 1972,2,1,23,59,59,999)",
utc(this, 1972,2,1,23,59,59,999) );
addNewTestCase(this, Date.UTC( 1968,1,29,23,59,59,999),
"Date.UTC( 1968,1,29,23,59,59,999)",
utc(this, 1968,1,29,23,59,59,999) );
addNewTestCase(this, Date.UTC( 1968,2,1,23,59,59,999),
"Date.UTC( 1968,2,1,23,59,59,999)",
utc(this, 1968,2,1,23,59,59,999) );
addNewTestCase(this, Date.UTC( 1969,0,1,0,0,0,0),
"Date.UTC( 1969,0,1,0,0,0,0)",
utc(this, 1969,0,1,0,0,0,0) );
addNewTestCase(this, Date.UTC( 1969,11,31,23,59,59,1000),
"Date.UTC( 1969,11,31,23,59,59,1000)",
utc(this, 1970,0,1,0,0,0,0) );
addNewTestCase(this, Date.UTC( 1969,Number.NaN,31,23,59,59,999),
"Date.UTC( 1969,Number.NaN,31,23,59,59,999)",
utc(this, 1969,Number.NaN,31,23,59,59,999) );

// Dates around 2000

addNewTestCase(this, Date.UTC( 1999,11,31,23,59,59,999),
"Date.UTC( 1999,11,31,23,59,59,999)",
utc(this, 1999,11,31,23,59,59,999) );
addNewTestCase(this, Date.UTC( 2000,0,1,0,0,0,0),
"Date.UTC( 2000,0,1,0,0,0,0)",
utc(this, 2000,0,1,0,0,0,0) );

// Dates around 1900
addNewTestCase(this, Date.UTC( 1899,11,31,23,59,59,999),
"Date.UTC( 1899,11,31,23,59,59,999)",
utc(this, 1899,11,31,23,59,59,999) );
addNewTestCase(this, Date.UTC( 1900,0,1,0,0,0,0),
"Date.UTC( 1900,0,1,0,0,0,0)",
utc(this, 1900,0,1,0,0,0,0) );
addNewTestCase(this, Date.UTC( 1973,0,1,0,0,0,0),
"Date.UTC( 1973,0,1,0,0,0,0)",
utc(this, 1973,0,1,0,0,0,0) );
addNewTestCase(this, Date.UTC( 1776,6,4,12,36,13,111),
"Date.UTC( 1776,6,4,12,36,13,111)",
utc(this, 1776,6,4,12,36,13,111) );
addNewTestCase(this, Date.UTC( 2525,9,18,15,30,1,123),
"Date.UTC( 2525,9,18,15,30,1,123)",
utc(this, 2525,9,18,15,30,1,123) );

// Dates around 29 Feb 2000

addNewTestCase(this, Date.UTC( 2000,1,29,0,0,0,0 ),
"Date.UTC( 2000,1,29,0,0,0,0 )",
utc(this, 2000,1,29,0,0,0,0) );
addNewTestCase(this, Date.UTC( 2000,1,29,8,0,0,0 ),
"Date.UTC( 2000,1,29,8,0,0,0 )",
utc(this, 2000,1,29,8,0,0,0) );

// Dates around 1 Jan 2005

addNewTestCase(this, Date.UTC( 2005,0,1,0,0,0,0 ),
"Date.UTC( 2005,0,1,0,0,0,0 )",
utc(this, 2005,0,1,0,0,0,0) );
addNewTestCase(this, Date.UTC( 2004,11,31,16,0,0,0 ),
"Date.UTC( 2004,11,31,16,0,0,0 )",
utc(this, 2004,11,31,16,0,0,0) );

//test();

function addNewTestCase(obj, DateCase, DateString, ExpectDate) {
DateCase = DateCase;

obj.TestCase( SECTION, DateString,         ExpectDate.value,       DateCase );
obj.TestCase( SECTION, DateString,         ExpectDate.value,       DateCase );
}

function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}

function utc( obj, year, month, date, hours, minutes, seconds, ms ) {
d = new MyDate();
d.year      = Number(year);

if (month)
d.month     = Number(month);
if (date)
d.date      = Number(date);
if (hours)
d.hours     = Number(hours);
if (minutes)
d.minutes   = Number(minutes);
if (seconds)
d.seconds   = Number(seconds);
if (ms)
d.ms        = Number(ms);

if ( isNaN(d.year) && 0 <= ToInteger(d.year) && d.year <= 99 ) {
d.year = 1900 + ToInteger(d.year);
}

if (isNaN(month) || isNaN(year) || isNaN(date) || isNaN(hours) ||
isNaN(minutes) || isNaN(seconds) || isNaN(ms) ) {
d.year = Number.NaN;
d.month = Number.NaN;
d.date = Number.NaN;
d.hours = Number.NaN;
d.minutes = Number.NaN;
d.seconds = Number.NaN;
d.ms = Number.NaN;
d.value = Number.NaN;
d.time = Number.NaN;
d.day =Number.NaN;
return d;
}

d.day = obj.MakeDay( d.year, d.month, d.date );
d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = (obj.TimeClip( obj.MakeDate(d.day,d.time)));

return d;
}

function UTCTime( t ) {
sign = ( t < 0 ) ? -1 : 1;
return ( (t +(TZ_DIFF*this.msPerHour)) );
}

},

/**
File Name:          15_9_5_1.js
ECMA Section:       15.9.5.1 Date.prototype.constructor
Description:
The initial value of Date.prototype.constructor is the built-in Date
constructor.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_1:function(){
var SECTION = "15.9.5.1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.constructor";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Date.prototype.constructor == Date",
true,
Date.prototype.constructor == Date );
//test();

},

/**
File Name:          15_9_5_10__1.js
ECMA Section:       15.9.5.10
Description:        Date.prototype.getDate

1.Let t be this time value.
2.If t is NaN, return NaN.
3.Return DateFromTime(LocalTime(t)).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_10__1:function(){
var SECTION = "15.9.5.10";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getDate()";

//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_NOW );

this.TestCase( SECTION,
"(new Date(NaN)).getDate()",
NaN,
(new Date(NaN)).getDate() );

this.TestCase( SECTION,
"Date.prototype.getDate.length",
0,
Date.prototype.getDate.length );
//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += obj.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getDate()",
obj.DateFromTime(obj.LocalTime(d)),
(new Date(d)).getDate() );
}
}

},

/**
File Name:          15_9_5_10__2.js
ECMA Section:       15.9.5.10
Description:        Date.prototype.getDate

1.Let t be this time value.
2.If t is NaN, return NaN.
3.Return DateFromTime(LocalTime(t)).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_10__2:function(){
var SECTION = "15.9.5.10";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getDate()";
var TIME_0000  = this.time0000;
//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, TIME_0000 );

this.TestCase( SECTION,
"(new Date(NaN)).getDate()",
NaN,
(new Date(NaN)).getDate() );

this.TestCase( SECTION,
"Date.prototype.getDate.length",
0,
Date.prototype.getDate.length );
//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getDate()",
obj.DateFromTime(obj.LocalTime(d)),
(new Date(d)).getDate() );
}
}

},

/**
File Name:          15_9_5_10__3.js
ECMA Section:       15.9.5.10
Description:        Date.prototype.getDate

1.Let t be this time value.
2.If t is NaN, return NaN.
3.Return DateFromTime(LocalTime(t)).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_10__3:function(){
var SECTION = "15.9.5.10";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getDate()";

//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_1970 );

this.TestCase( SECTION,
"(new Date(NaN)).getDate()",
NaN,
(new Date(NaN)).getDate() );

this.TestCase( SECTION,
"Date.prototype.getDate.length",
0,
Date.prototype.getDate.length );
//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getDate()",
obj.DateFromTime(obj.LocalTime(d)),
(new Date(d)).getDate() );
}
}

},

/**
File Name:          15_9_5_10__4.js
ECMA Section:       15.9.5.10
Description:        Date.prototype.getDate

1.Let t be this time value.
2.If t is NaN, return NaN.
3.Return DateFromTime(LocalTime(t)).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_10__4:function(){
var SECTION = "15.9.5.10";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getDate()";

//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_1900 );

this.TestCase( SECTION,
"(new Date(NaN)).getDate()",
NaN,
(new Date(NaN)).getDate() );

this.TestCase( SECTION,
"Date.prototype.getDate.length",
0,
Date.prototype.getDate.length );
//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getDate()",
obj.DateFromTime(obj.LocalTime(d)),
(new Date(d)).getDate() );
}
}

},

/**
File Name:          15_9_5_10__5.js
ECMA Section:       15.9.5.10
Description:        Date.prototype.getDate

1.Let t be this time value.
2.If t is NaN, return NaN.
3.Return DateFromTime(LocalTime(t)).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_10__5:function(){
var SECTION = "15.9.5.10";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getDate()";

//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_2000 );

this.TestCase( SECTION,
"(new Date(NaN)).getDate()",
NaN,
(new Date(NaN)).getDate() );

this.TestCase( SECTION,
"Date.prototype.getDate.length",
0,
Date.prototype.getDate.length );
//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getDate()",
obj.DateFromTime(obj.LocalTime(d)),
(new Date(d)).getDate() );
}
}

},

/**
File Name:          15_9_5_10__6.js
ECMA Section:       15.9.5.10
Description:        Date.prototype.getDate

1.Let t be this time value.
2.If t is NaN, return NaN.
3.Return DateFromTime(LocalTime(t)).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_10__6:function(){
var SECTION = "15.9.5.10";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getDate()";

//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.UTC_FEB_29_2000 );

this.TestCase( SECTION,
"(new Date(NaN)).getDate()",
NaN,
(new Date(NaN)).getDate() );

this.TestCase( SECTION,
"Date.prototype.getDate.length",
0,
Date.prototype.getDate.length );
//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getDate()",
obj.DateFromTime(obj.LocalTime(d)),
(new Date(d)).getDate() );
}
}

},

/**
File Name:          15_9_5_10__7.js
ECMA Section:       15.9.5.10
Description:        Date.prototype.getDate

1.Let t be this time value.
2.If t is NaN, return NaN.
3.Return DateFromTime(LocalTime(t)).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_10__7:function(){
var SECTION = "15.9.5.10";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getDate()";
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, UTC_JAN_1_2005 );

this.TestCase( SECTION,
"(new Date(NaN)).getDate()",
NaN,
(new Date(NaN)).getDate() );

this.TestCase( SECTION,
"Date.prototype.getDate.length",
0,
Date.prototype.getDate.length );
//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getDate()",
obj.DateFromTime(obj.LocalTime(d)),
(new Date(d)).getDate() );
}
}

},

/**
File Name:          15_9_5_10__8.js
ECMA Section:       15.9.5.10
Description:        Date.prototype.getDate

1.Let t be this time value.
2.If t is NaN, return NaN.
3.Return DateFromTime(LocalTime(t)).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_10__8:function(){
var SECTION = "15.9.5.10";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getDate()";

//writeHeaderToLog( SECTION + " "+ TITLE);

// some daylight savings time cases

var DST_START_1998 = this.GetDSTStart(this.TimeFromYear(1998));

addTestCase(this, DST_START_1998 );

this.TestCase( SECTION,
"(new Date(NaN)).getDate()",
NaN,
(new Date(NaN)).getDate() );

this.TestCase( SECTION,
"Date.prototype.getDate.length",
0,
Date.prototype.getDate.length );
//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getDate()",
obj.DateFromTime(obj.LocalTime(d)),
(new Date(d)).getDate() );
}
}

},

/**
File Name:          15_9_5_10__9.js
ECMA Section:       15.9.5.10
Description:        Date.prototype.getDate

1.Let t be this time value.
2.If t is NaN, return NaN.
3.Return DateFromTime(LocalTime(t)).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_10__9:function(){
var SECTION = "15.9.5.10";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getDate()";

//writeHeaderToLog( SECTION + " "+ TITLE);

// some daylight savings time cases

var DST_START_1998 = this.GetDSTStart(this.TimeFromYear(1998));

addTestCase(this, DST_START_1998-1 );

this.TestCase( SECTION,
"(new Date(NaN)).getDate()",
NaN,
(new Date(NaN)).getDate() );

this.TestCase( SECTION,
"Date.prototype.getDate.length",
0,
Date.prototype.getDate.length );
//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getDate()",
obj.DateFromTime(obj.LocalTime(d)),
(new Date(d)).getDate() );
}
}

},

/**
File Name:          15_9_5_10__10.js
ECMA Section:       15.9.5.10
Description:        Date.prototype.getDate

1.Let t be this time value.
2.If t is NaN, return NaN.
3.Return DateFromTime(LocalTime(t)).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_10__10:function(){
var SECTION = "15.9.5.10";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getDate()";

//writeHeaderToLog( SECTION + " "+ TITLE);

// some daylight savings time cases

var DST_START_1998 = this.GetDSTStart(this.TimeFromYear(1998));

addTestCase(this, DST_START_1998+1 );

this.TestCase( SECTION,
"(new Date(NaN)).getDate()",
NaN,
(new Date(NaN)).getDate() );

this.TestCase( SECTION,
"Date.prototype.getDate.length",
0,
Date.prototype.getDate.length );
//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getDate()",
obj.DateFromTime(obj.LocalTime(d)),
(new Date(d)).getDate() );
}
}

},

/**
File Name:          15_9_5_10__11.js
ECMA Section:       15.9.5.10
Description:        Date.prototype.getDate

1.Let t be this time value.
2.If t is NaN, return NaN.
3.Return DateFromTime(LocalTime(t)).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_10__11:function(){
var SECTION = "15.9.5.10";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getDate()";

//writeHeaderToLog( SECTION + " "+ TITLE);

// some daylight savings time cases

var DST_END_1998 = this.GetDSTEnd(this.TimeFromYear(1998));

addTestCase(this, DST_END_1998 );

this.TestCase( SECTION,
"(new Date(NaN)).getDate()",
NaN,
(new Date(NaN)).getDate() );

this.TestCase( SECTION,
"Date.prototype.getDate.length",
0,
Date.prototype.getDate.length );
//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getDate()",
obj.DateFromTime(obj.LocalTime(d)),
(new Date(d)).getDate() );
}
}

},

/**
File Name:          15_9_5_10__12.js
ECMA Section:       15.9.5.10
Description:        Date.prototype.getDate

1.Let t be this time value.
2.If t is NaN, return NaN.
3.Return DateFromTime(LocalTime(t)).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_10__12:function(){
var SECTION = "15.9.5.10";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getDate()";

//writeHeaderToLog( SECTION + " "+ TITLE);

// some daylight savings time cases

var DST_END_1998 = this.GetDSTEnd(this.TimeFromYear(1998));

addTestCase(this, DST_END_1998-1 );

this.TestCase( SECTION,
"(new Date(NaN)).getDate()",
NaN,
(new Date(NaN)).getDate() );

this.TestCase( SECTION,
"Date.prototype.getDate.length",
0,
Date.prototype.getDate.length );
//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getDate()",
obj.DateFromTime(obj.LocalTime(d)),
(new Date(d)).getDate() );
}
}

},

/**
File Name:          15_9_5_10__13.js
ECMA Section:       15.9.5.10
Description:        Date.prototype.getDate

1.Let t be this time value.
2.If t is NaN, return NaN.
3.Return DateFromTime(LocalTime(t)).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_10__13:function(){
var SECTION = "15.9.5.10";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getDate()";

//writeHeaderToLog( SECTION + " "+ TITLE);

// some daylight savings time cases

var DST_END_1998 = this.GetDSTEnd(this.TimeFromYear(1998));

addTestCase(this, DST_END_1998+1 );

this.TestCase( SECTION,
"(new Date(NaN)).getDate()",
NaN,
(new Date(NaN)).getDate() );

this.TestCase( SECTION,
"Date.prototype.getDate.length",
0,
Date.prototype.getDate.length );
//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getDate()",
obj.DateFromTime(obj.LocalTime(d)),
(new Date(d)).getDate() );
}
}

},

/**
File Name:          15_9_5_11__1.js
ECMA Section:       15.9.5.11
Description:        Date.prototype.getUTCDate

1.Let t be this time value.
2.If t is NaN, return NaN.
1.Return DateFromTime(t).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_11__1:function(){
var SECTION = "15.9.5.11";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getUTCDate()";

//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_NOW );

//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getUTCDate()",
obj.DateFromTime(d),
(new Date(d)).getUTCDate() );
}
}

},

/**
File Name:          15_9_5_11__2.js
ECMA Section:       15.9.5.11
Description:        Date.prototype.getUTCDate

1.Let t be this time value.
2.If t is NaN, return NaN.
1.Return DateFromTime(t).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_11__2:function(){
var SECTION = "15.9.5.11";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getUTCDate()";
var TIME_0000  = this.time0000;
//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, TIME_0000 );

//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getUTCDate()",
obj.DateFromTime(d),
(new Date(d)).getUTCDate() );
}
}

},

/**
File Name:          15_9_5_11__3.js
ECMA Section:       15.9.5.11
Description:        Date.prototype.getUTCDate

1.Let t be this time value.
2.If t is NaN, return NaN.
1.Return DateFromTime(t).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_11__3:function(){
var SECTION = "15.9.5.11";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getUTCDate()";

//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_1970 );

//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getUTCDate()",
obj.DateFromTime(d),
(new Date(d)).getUTCDate() );
}
}

},

/**
File Name:          15_9_5_11__4.js
ECMA Section:       15.9.5.11
Description:        Date.prototype.getUTCDate

1.Let t be this time value.
2.If t is NaN, return NaN.
1.Return DateFromTime(t).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_11__4:function(){
var SECTION = "15.9.5.11";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getUTCDate()";

//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_1900 );

//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getUTCDate()",
obj.DateFromTime(d),
(new Date(d)).getUTCDate() );
}
}

},

/**
File Name:          15_9_5_11__5.js
ECMA Section:       15.9.5.11
Description:        Date.prototype.getUTCDate

1.Let t be this time value.
2.If t is NaN, return NaN.
1.Return DateFromTime(t).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_11__5:function(){
var SECTION = "15.9.5.11";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getUTCDate()";

//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_2000 );

//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getUTCDate()",
obj.DateFromTime(d),
(new Date(d)).getUTCDate() );
}
}

},

/**
File Name:          15_9_5_11__6.js
ECMA Section:       15.9.5.11
Description:        Date.prototype.getUTCDate

1.Let t be this time value.
2.If t is NaN, return NaN.
1.Return DateFromTime(t).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_11__6:function(){
var SECTION = "15.9.5.11";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getUTCDate()";

//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.UTC_FEB_29_2000 );

//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getUTCDate()",
obj.DateFromTime(d),
(new Date(d)).getUTCDate() );
}
}

},

/**
File Name:          15_9_5_11__7.js
ECMA Section:       15.9.5.11
Description:        Date.prototype.getUTCDate

1.Let t be this time value.
2.If t is NaN, return NaN.
1.Return DateFromTime(t).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_11__7:function(){
var SECTION = "15.9.5.11";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getUTCDate()";
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, UTC_JAN_1_2005 );

//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getUTCDate()",
obj.DateFromTime(d),
(new Date(d)).getUTCDate() );
}
}

},

/**
File Name:          15_9_5_12__1.js
ECMA Section:       15.9.5.12
Description:        Date.prototype.getDay


1.   Let t be this time value.
2.   If t is NaN, return NaN.
3.   Return WeekDay(LocalTime(t)).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_12__1:function(){
var SECTION = "15.9.5.12";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getDay()";

//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_NOW );

//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getDay()",
obj.WeekDay((obj.LocalTime(d))),
(new Date(d)).getDay() );
}
}

},

/**
File Name:          15_9_5_12__2.js
ECMA Section:       15.9.5.12
Description:        Date.prototype.getDay


1.   Let t be this time value.
2.   If t is NaN, return NaN.
3.   Return WeekDay(LocalTime(t)).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_12__2:function(){
var SECTION = "15.9.5.12";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getDay()";
var TIME_0000  = this.time0000;
//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, TIME_0000 );

//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getDay()",
obj.WeekDay((obj.LocalTime(d))),
(new Date(d)).getDay() );
}
}

},

/**
File Name:          15_9_5_12__3.js
ECMA Section:       15.9.5.12
Description:        Date.prototype.getDay


1.   Let t be this time value.
2.   If t is NaN, return NaN.
3.   Return WeekDay(LocalTime(t)).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_12__3:function(){
var SECTION = "15.9.5.12";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getDay()";

//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_1970 );

//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getDay()",
obj.WeekDay((obj.LocalTime(d))),
(new Date(d)).getDay() );
}
}

},

/**
File Name:          15_9_5_12__4.js
ECMA Section:       15.9.5.12
Description:        Date.prototype.getDay


1.   Let t be this time value.
2.   If t is NaN, return NaN.
3.   Return WeekDay(LocalTime(t)).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_12__4:function(){
var SECTION = "15.9.5.12";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getDay()";

//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_1900 );

//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getDay()",
obj.WeekDay((obj.LocalTime(d))),
(new Date(d)).getDay() );
}
}

},

/**
File Name:          15_9_5_12__5.js
ECMA Section:       15.9.5.12
Description:        Date.prototype.getDay


1.   Let t be this time value.
2.   If t is NaN, return NaN.
3.   Return WeekDay(LocalTime(t)).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_12__5:function(){
var SECTION = "15.9.5.12";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getDay()";

//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_2000 );

//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getDay()",
obj.WeekDay((obj.LocalTime(d))),
(new Date(d)).getDay() );
}
}

},

/**
File Name:          15_9_5_12__6.js
ECMA Section:       15.9.5.12
Description:        Date.prototype.getDay


1.   Let t be this time value.
2.   If t is NaN, return NaN.
3.   Return WeekDay(LocalTime(t)).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_12__6:function(){
var SECTION = "15.9.5.12";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getDay()";

//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.UTC_FEB_29_2000 );

//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getDay()",
obj.WeekDay((obj.LocalTime(d))),
(new Date(d)).getDay() );
}
}

},

/**
File Name:          15_9_5_12__7.js
ECMA Section:       15.9.5.12
Description:        Date.prototype.getDay


1.   Let t be this time value.
2.   If t is NaN, return NaN.
3.   Return WeekDay(LocalTime(t)).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_12__7:function(){
var SECTION = "15.9.5.12";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getDay()";
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, UTC_JAN_1_2005 );

//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getDay()",
obj.WeekDay((obj.LocalTime(d))),
(new Date(d)).getDay() );
}
}

},

/**
File Name:          15_9_5_12__8.js
ECMA Section:       15.9.5.12
Description:        Date.prototype.getDay


1.   Let t be this time value.
2.   If t is NaN, return NaN.
3.   Return WeekDay(LocalTime(t)).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_12__8:function(){
var SECTION = "15.9.5.12";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getDay()";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"(new Date(NaN)).getDay()",
NaN,
(new Date(NaN)).getDay() );

this.TestCase( SECTION,
"Date.prototype.getDay.length",
0,
Date.prototype.getDay.length );
//test();

},

/**
File Name:          15_9_5_13__1.js
ECMA Section:       15.9.5.13
Description:        Date.prototype.getUTCDay

1.Let t be this time value.
2.If t is NaN, return NaN.
3.Return WeekDay(t).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_13__1:function(){
var SECTION = "15.9.5.13";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getUTCDay()";

//writeHeaderToLog( SECTION + " "+ TITLE);

// get the current time
var now = (new Date()).valueOf();

addTestCase(this, now );

//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getUTCDay()",
obj.WeekDay((d)),
(new Date(d)).getUTCDay() );
}
}

},

/**
File Name:          15_9_5_13__2.js
ECMA Section:       15.9.5.13
Description:        Date.prototype.getUTCDay

1.Let t be this time value.
2.If t is NaN, return NaN.
3.Return WeekDay(t).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_13__2:function(){
var SECTION = "15.9.5.13";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getUTCDay()";
var TIME_0000  = this.time0000;
//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, TIME_0000 );

//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
this.TestCase( SECTION,
"(new Date("+d+")).getUTCDay()",
obj.WeekDay((d)),
(new Date(d)).getUTCDay() );
}
}

},

/**
File Name:          15_9_5_13__3.js
ECMA Section:       15.9.5.13
Description:        Date.prototype.getUTCDay

1.Let t be this time value.
2.If t is NaN, return NaN.
3.Return WeekDay(t).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_13__3:function(){
var SECTION = "15.9.5.13";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getUTCDay()";

//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_1970 );

//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getUTCDay()",
obj.WeekDay((d)),
(new Date(d)).getUTCDay() );
}
}


},

/**
File Name:          15_9_5_13__4.js
ECMA Section:       15.9.5.13
Description:        Date.prototype.getUTCDay

1.Let t be this time value.
2.If t is NaN, return NaN.
3.Return WeekDay(t).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_13__4:function(){
var SECTION = "15.9.5.13";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getUTCDay()";

//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_1900 );

//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getUTCDay()",
obj.WeekDay((d)),
(new Date(d)).getUTCDay() );
}
}

},

/**
File Name:          15_9_5_13__5.js
ECMA Section:       15.9.5.13
Description:        Date.prototype.getUTCDay

1.Let t be this time value.
2.If t is NaN, return NaN.
3.Return WeekDay(t).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_13__5:function(){
var SECTION = "15.9.5.13";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getUTCDay()";

//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_2000 );

//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getUTCDay()",
obj.WeekDay((d)),
(new Date(d)).getUTCDay() );
}
}

},

/**
File Name:          15_9_5_13__6.js
ECMA Section:       15.9.5.13
Description:        Date.prototype.getUTCDay

1.Let t be this time value.
2.If t is NaN, return NaN.
3.Return WeekDay(t).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_13__6:function(){
var SECTION = "15.9.5.13";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getUTCDay()";

//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.UTC_FEB_29_2000 );

//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
this.TestCase( SECTION,
"(new Date("+d+")).getUTCDay()",
obj.WeekDay((d)),
(new Date(d)).getUTCDay() );
}
}

},

/**
File Name:          15_9_5_13__7.js
ECMA Section:       15.9.5.13
Description:        Date.prototype.getUTCDay

1.Let t be this time value.
2.If t is NaN, return NaN.
3.Return WeekDay(t).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_13__7:function(){
var SECTION = "15.9.5.13";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getUTCDay()";
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, UTC_JAN_1_2005 );

//test();

function addTestCase(obj, t ) {
var start = obj.TimeFromYear(obj.YearFromTime(t));
var stop  = obj.TimeFromYear(obj.YearFromTime(t) + 1);

for (var d = start; d < stop; d += this.msPerDay)
{
obj.TestCase( SECTION,
"(new Date("+d+")).getUTCDay()",
obj.WeekDay((d)),
(new Date(d)).getUTCDay() );
}
}

},

/**
File Name:          15_9_5_13__8.js
ECMA Section:       15.9.5.13
Description:        Date.prototype.getUTCDay

1.Let t be this time value.
2.If t is NaN, return NaN.
3.Return WeekDay(t).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_13__8:function(){
var SECTION = "15.9.5.13";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getUTCDay()";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"(new Date(NaN)).getUTCDay()",
NaN,
(new Date(NaN)).getUTCDay() );

this.TestCase( SECTION,
"Date.prototype.getUTCDay.length",
0,
Date.prototype.getUTCDay.length );

//test();

},

/**
File Name:          15_9_5_14.js
ECMA Section:       15.9.5.14
Description:        Date.prototype.getHours
1.  Let t be this time value.
2.  If t is NaN, return NaN.
3.  Return HourFromTime(LocalTime(t)).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_14:function(){
var SECTION = "15.9.5.14";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getHours()";
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
var TIME_0000  = this.time0000;
//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_NOW );
addTestCase(this, TIME_0000 );
addTestCase(this, this.TIME_1970 );
addTestCase(this, this.TIME_1900 );
addTestCase(this, this.TIME_2000 );
addTestCase(this, this.UTC_FEB_29_2000 );
addTestCase(this, UTC_JAN_1_2005 );

this.TestCase( SECTION,
"(new Date(NaN)).getHours()",
NaN,
(new Date(NaN)).getHours() );

this.TestCase( SECTION,
"Date.prototype.getHours.length",
0,
Date.prototype.getHours.length );
//test();

function addTestCase(obj, t ) {
for ( h = 0; h < 24; h+=4 ) {
t += this.msPerHour;
obj.TestCase( SECTION,
"(new Date("+t+")).getHours()",
obj.HourFromTime((obj.LocalTime(t))),
(new Date(t)).getHours() );
}
}

},

/**
File Name:          15_9_5_15.js
ECMA Section:       15.9.5.15
Description:        Date.prototype.getUTCHours

1.  Let t be this time value.
2.  If t is NaN, return NaN.
3.  Return HourFromTime(t).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_15:function(){
var SECTION = "15.9.5.15";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getUTCHours()";
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
var TIME_0000  = this.time0000;
//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_NOW );
addTestCase(this, TIME_0000 );
addTestCase(this, this.TIME_1970 );
addTestCase(this, this.TIME_1900 );
addTestCase(this, this.TIME_2000 );
addTestCase(this, this.UTC_FEB_29_2000 );
addTestCase(this, UTC_JAN_1_2005 );

this.TestCase( SECTION,
"(new Date(NaN)).getUTCHours()",
NaN,
(new Date(NaN)).getUTCHours() );

this.TestCase( SECTION,
"Date.prototype.getUTCHours.length",
0,
Date.prototype.getUTCHours.length );
//test();

function addTestCase(obj, t ) {
for ( h = 0; h < 24; h+=3 ) {
t += this.msPerHour;
obj.TestCase( SECTION,
"(new Date("+t+")).getUTCHours()",
obj.HourFromTime((t)),
(new Date(t)).getUTCHours() );
}
}

},

/**
File Name:          15_9_5_16.js
ECMA Section:       15.9.5.16
Description:        Date.prototype.getMinutes
1.Let t be this time value.
2.If t is NaN, return NaN.
3.Return MinFromTime(LocalTime(t)).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_16:function(){
var SECTION = "15.9.5.16";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getMinutes()";
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
var TIME_0000  = this.time0000;
//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_NOW );
addTestCase(this, TIME_0000 );
addTestCase(this, this.TIME_1970 );
addTestCase(this, this.TIME_1900 );
addTestCase(this, this.TIME_2000 );
addTestCase(this, this.UTC_FEB_29_2000 );
addTestCase(this, UTC_JAN_1_2005 );

this.TestCase( SECTION,
"(new Date(NaN)).getMinutes()",
NaN,
(new Date(NaN)).getMinutes() );

this.TestCase( SECTION,
"Date.prototype.getMinutes.length",
0,
Date.prototype.getMinutes.length );
//test();

function addTestCase(obj, t ) {
for ( m = 0; m <= 60; m+=10 ) {
t += obj.msPerMinute;
obj.TestCase( SECTION,
"(new Date("+t+")).getMinutes()",
obj.MinFromTime((obj.LocalTime(t))),
(new Date(t)).getMinutes() );
}
}

},

/**
File Name:          15_9_5_17.js
ECMA Section:       15.9.5.17
Description:        Date.prototype.getUTCMinutes

1.  Let t be this time value.
2.  If t is NaN, return NaN.
3.  Return MinFromTime(t).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_17:function(){
var SECTION = "15.9.5.17";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getUTCMinutes()";
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
var TIME_0000  = this.time0000;
//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_NOW );
addTestCase(this, TIME_0000 );
addTestCase(this, this.TIME_1970 );
addTestCase(this, this.TIME_1900 );
addTestCase(this, this.TIME_2000 );
addTestCase(this, this.UTC_FEB_29_2000 );
addTestCase(this, UTC_JAN_1_2005 );

this.TestCase( SECTION,
"(new Date(NaN)).getUTCMinutes()",
NaN,
(new Date(NaN)).getUTCMinutes() );

this.TestCase( SECTION,
"Date.prototype.getUTCMinutes.length",
0,
Date.prototype.getUTCMinutes.length );
//test();

function addTestCase(obj, t ) {
for ( m = 0; m <= 60; m+=10 ) {
t += obj.msPerMinute;
obj.TestCase( SECTION,
"(new Date("+t+")).getUTCMinutes()",
obj.MinFromTime(t),
(new Date(t)).getUTCMinutes() );
}
}

},

/**
File Name:          15_9_5_18.js
ECMA Section:       15.9.5.18
Description:        Date.prototype.getSeconds

1.  Let t be this time value.
2.  If t is NaN, return NaN.
3.  Return SecFromTime(LocalTime(t)).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_18:function(){
var SECTION = "15.9.5.18";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getSeconds()";
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
var TIME_0000  = this.time0000;
//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_NOW );
addTestCase(this, TIME_0000 );
addTestCase(this, this.TIME_1970 );
addTestCase(this, this.TIME_1900 );
addTestCase(this, this.TIME_2000 );
addTestCase(this, this.UTC_FEB_29_2000 );
addTestCase(this, UTC_JAN_1_2005 );

this.TestCase( SECTION,
"(new Date(NaN)).getSeconds()",
NaN,
(new Date(NaN)).getSeconds() );

this.TestCase( SECTION,
"Date.prototype.getSeconds.length",
0,
Date.prototype.getSeconds.length );
//test();

function addTestCase(obj, t ) {
for ( m = 0; m <= 60; m+=10 ) {
t += 1000;
obj.TestCase( SECTION,
"(new Date("+t+")).getSeconds()",
obj.SecFromTime(obj.LocalTime(t)),
(new Date(t)).getSeconds() );
}
}

},

/**
File Name:          15_9_5_19.js
ECMA Section:       15.9.5.19
Description:        Date.prototype.getUTCSeconds

1.  Let t be this time value.
2.  If t is NaN, return NaN.
3.  Return SecFromTime(t).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_19:function(){
var SECTION = "15.9.5.19";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getUTCSeconds()";
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
var TIME_0000  = this.time0000;
//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_NOW );
addTestCase(this, TIME_0000 );
addTestCase(this, this.TIME_1970 );
addTestCase(this, this.TIME_1900 );
addTestCase(this, this.TIME_2000 );
addTestCase(this, this.UTC_FEB_29_2000 );
addTestCase(this, UTC_JAN_1_2005 );

this.TestCase( SECTION,
"(new Date(NaN)).getUTCSeconds()",
NaN,
(new Date(NaN)).getUTCSeconds() );

this.TestCase( SECTION,
"Date.prototype.getUTCSeconds.length",
0,
Date.prototype.getUTCSeconds.length );
//test();

function addTestCase(obj, t ) {
for ( m = 0; m <= 60; m+=10 ) {
t += 1000;
obj.TestCase( SECTION,
"(new Date("+t+")).getUTCSeconds()",
obj.SecFromTime(t),
(new Date(t)).getUTCSeconds() );
}
}

},

/**
File Name:          15_9_5_2__1.js
ECMA Section:       15.9.5.2 Date.prototype.toString
Description:
This function returns a string value. The contents of the string are
implementation dependent, but are intended to represent the Date in a
convenient, human-readable form in the current time zone.

The toString function is not generic; it generates a runtime error if its
this value is not a Date object. Therefore it cannot be transferred to
other kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_2__1:function(){
var SECTION = "15.9.5.2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.toString";
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Date.prototype.toString.length",
0,
Date.prototype.toString.length );

var now = new Date();

// can't test the content of the string, but can verify that the string is
// parsable by Date.parse

this.TestCase( SECTION,
"Math.abs(Date.parse(now.toString()) - now.valueOf()) < 1000",
true,
Math.abs(Date.parse(now.toString()) - now.valueOf()) < 1000 );

this.TestCase( SECTION,
"typeof now.toString()",
"string",
typeof now.toString() );
// 1970

this.TestCase( SECTION,
"Date.parse( (new Date(0)).toString() )",
0,
Date.parse( (new Date(0)).toString() ) );

this.TestCase( SECTION,
"Date.parse( (new Date("+this.TZ_ADJUST+")).toString() )",
this.TZ_ADJUST,
Date.parse( (new Date(this.TZ_ADJUST)).toString() ) );

// 1900
this.TestCase( SECTION,
"Date.parse( (new Date("+this.TIME_1900+")).toString() )",
this.TIME_1900,
Date.parse( (new Date(this.TIME_1900)).toString() ) );

this.TestCase( SECTION,
"Date.parse( (new Date("+this.TIME_1900 -this.TZ_ADJUST+")).toString() )",
this.TIME_1900 -this.TZ_ADJUST,
Date.parse( (new Date(this.TIME_1900 -this.TZ_ADJUST)).toString() ) );

// 2000
this.TestCase( SECTION,
"Date.parse( (new Date("+this.TIME_2000+")).toString() )",
this.TIME_2000,
Date.parse( (new Date(this.TIME_2000)).toString() ) );

this.TestCase( SECTION,
"Date.parse( (new Date("+this.TIME_2000 -this.TZ_ADJUST+")).toString() )",
this.TIME_2000 -this.TZ_ADJUST,
Date.parse( (new Date(this.TIME_2000 -this.TZ_ADJUST)).toString() ) );

// 29 Feb 2000

this.TestCase( SECTION,
"Date.parse( (new Date("+this.UTC_FEB_29_2000+")).toString() )",
this.UTC_FEB_29_2000,
Date.parse( (new Date(this.UTC_FEB_29_2000)).toString() ) );

this.TestCase( SECTION,
"Date.parse( (new Date("+(this.UTC_FEB_29_2000-1000)+")).toString() )",
this.UTC_FEB_29_2000-1000,
Date.parse( (new Date(this.UTC_FEB_29_2000-1000)).toString() ) );


this.TestCase( SECTION,
"Date.parse( (new Date("+(this.UTC_FEB_29_2000-this.TZ_ADJUST)+")).toString() )",
this.UTC_FEB_29_2000-this.TZ_ADJUST,
Date.parse( (new Date(this.UTC_FEB_29_2000-this.TZ_ADJUST)).toString() ) );
// 2O05

this.TestCase( SECTION,
"Date.parse( (new Date("+UTC_JAN_1_2005+")).toString() )",
UTC_JAN_1_2005,
Date.parse( (new Date(UTC_JAN_1_2005)).toString() ) );

this.TestCase( SECTION,
"Date.parse( (new Date("+(UTC_JAN_1_2005-1000)+")).toString() )",
UTC_JAN_1_2005-1000,
Date.parse( (new Date(UTC_JAN_1_2005-1000)).toString() ) );

this.TestCase( SECTION,
"Date.parse( (new Date("+(UTC_JAN_1_2005-this.TZ_ADJUST)+")).toString() )",
UTC_JAN_1_2005-this.TZ_ADJUST,
Date.parse( (new Date(UTC_JAN_1_2005-this.TZ_ADJUST)).toString() ) );

//test();

},

/**
File Name:          15_9_5_2__2__n.js
ECMA Section:       15.9.5.2 Date.prototype.toString
Description:
This function returns a string value. The contents of the string are
implementation dependent, but are intended to represent the Date in a
convenient, human-readable form in the current time zone.

The toString function is not generic; it generates a runtime error if its
this value is not a Date object. Therefore it cannot be transferred to
other kinds of objects for use as a method.


This verifies that calling toString on an object that is not a string
generates a runtime error.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_2__2__n:function(){
var SECTION = "15.9.5.2-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.toString";

//writeHeaderToLog( SECTION + " "+ TITLE);

var OBJ = new MyObject( new Date(0) );

DESCRIPTION = "var OBJ = new MyObject( new Date(0) ); OBJ.toString()";
EXPECTED = "error";

this.TestCase( SECTION,
"var OBJ = new MyObject( new Date(0) ); OBJ.toString()",
"error",
eval("OBJ.toString()") );
//test();

function MyObject( value ) {
this.value = value;
this.valueOf = new Function( "return this.value" );
this.toString = Date.prototype.toString;
return this;
}

},

/**
File Name:          15_9_5_2.js
ECMA Section:       15.9.5.2 Date.prototype.toString
Description:
This function returns a string value. The contents of the string are
implementation dependent, but are intended to represent the Date in a
convenient, human-readable form in the current time zone.

The toString function is not generic; it generates a runtime error if its
this value is not a Date object. Therefore it cannot be transferred to
other kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_2:function(){
var SECTION = "15.9.5.2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.toString";
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"Date.prototype.toString.length",
0,
Date.prototype.toString.length );

var now = new Date();

// can't test the content of the string, but can verify that the string is
// parsable by Date.parse

this.TestCase( SECTION,
"Math.abs(Date.parse(now.toString()) - now.valueOf()) < 1000",
true,
Math.abs(Date.parse(now.toString()) - now.valueOf()) < 1000 );

this.TestCase( SECTION,
"typeof now.toString()",
"string",
typeof now.toString() );
// 1970

this.TestCase( SECTION,
"Date.parse( (new Date(0)).toString() )",
0,
Date.parse( (new Date(0)).toString() ) );

this.TestCase( SECTION,
"Date.parse( (new Date("+this.TZ_ADJUST+")).toString() )",
this.TZ_ADJUST,
Date.parse( (new Date(this.TZ_ADJUST)).toString() ) );

// 1900
this.TestCase( SECTION,
"Date.parse( (new Date("+this.TIME_1900+")).toString() )",
this.TIME_1900,
Date.parse( (new Date(this.TIME_1900)).toString() ) );

this.TestCase( SECTION,
"Date.parse( (new Date("+this.TIME_1900 -this.TZ_ADJUST+")).toString() )",
this.TIME_1900 -this.TZ_ADJUST,
Date.parse( (new Date(this.TIME_1900 -this.TZ_ADJUST)).toString() ) );

// 2000
this.TestCase( SECTION,
"Date.parse( (new Date("+this.TIME_2000+")).toString() )",
this.TIME_2000,
Date.parse( (new Date(this.TIME_2000)).toString() ) );

this.TestCase( SECTION,
"Date.parse( (new Date("+this.TIME_2000 -this.TZ_ADJUST+")).toString() )",
this.TIME_2000 -this.TZ_ADJUST,
Date.parse( (new Date(this.TIME_2000 -this.TZ_ADJUST)).toString() ) );

// 29 Feb 2000

this.TestCase( SECTION,
"Date.parse( (new Date("+this.UTC_FEB_29_2000+")).toString() )",
this.UTC_FEB_29_2000,
Date.parse( (new Date(this.UTC_FEB_29_2000)).toString() ) );

this.TestCase( SECTION,
"Date.parse( (new Date("+(this.UTC_FEB_29_2000-1000)+")).toString() )",
this.UTC_FEB_29_2000-1000,
Date.parse( (new Date(this.UTC_FEB_29_2000-1000)).toString() ) );


this.TestCase( SECTION,
"Date.parse( (new Date("+(this.UTC_FEB_29_2000-this.TZ_ADJUST)+")).toString() )",
this.UTC_FEB_29_2000-this.TZ_ADJUST,
Date.parse( (new Date(this.UTC_FEB_29_2000-this.TZ_ADJUST)).toString() ) );
// 2O05

this.TestCase( SECTION,
"Date.parse( (new Date("+UTC_JAN_1_2005+")).toString() )",
UTC_JAN_1_2005,
Date.parse( (new Date(UTC_JAN_1_2005)).toString() ) );

this.TestCase( SECTION,
"Date.parse( (new Date("+(UTC_JAN_1_2005-1000)+")).toString() )",
UTC_JAN_1_2005-1000,
Date.parse( (new Date(UTC_JAN_1_2005-1000)).toString() ) );

this.TestCase( SECTION,
"Date.parse( (new Date("+(UTC_JAN_1_2005-this.TZ_ADJUST)+")).toString() )",
UTC_JAN_1_2005-this.TZ_ADJUST,
Date.parse( (new Date(UTC_JAN_1_2005-this.TZ_ADJUST)).toString() ) );

//test();

},

/**
File Name:          15_9_5_20.js
ECMA Section:       15.9.5.20
Description:        Date.prototype.getMilliseconds

1.  Let t be this time value.
2.  If t is NaN, return NaN.
3.  Return msFromTime(LocalTime(t)).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_20:function(){
var SECTION = "15.9.5.20";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getMilliseconds()";
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
var TIME_0000  = this.time0000;
//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_NOW );
addTestCase(this, TIME_0000 );
addTestCase(this, this.TIME_1970 );
addTestCase(this, this.TIME_1900 );
addTestCase(this, this.TIME_2000 );
addTestCase(this, this.UTC_FEB_29_2000 );
addTestCase(this, UTC_JAN_1_2005 );

this.TestCase( SECTION,
"(new Date(NaN)).getMilliseconds()",
NaN,
(new Date(NaN)).getMilliseconds() );

this.TestCase( SECTION,
"Date.prototype.getMilliseconds.length",
0,
Date.prototype.getMilliseconds.length );
//test();

function addTestCase(obj, t ) {
for ( m = 0; m <= 1000; m+=100 ) {
t++;
obj.TestCase( SECTION,
"(new Date("+t+")).getMilliseconds()",
obj.msFromTime(obj.LocalTime(t)),
(new Date(t)).getMilliseconds() );
}
}

},

/**
File Name:          15_9_5_21__2.js
ECMA Section:       15.9.5.21
Description:        Date.prototype.getUTCMilliseconds

1.  Let t be this time value.
2.  If t is NaN, return NaN.
3.  Return msFromTime(t).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_21__1:function(){
var SECTION = "15.9.5.21";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getUTCMilliseconds()";

//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_NOW );

//test();
function addTestCase(obj, t ) {
obj.TestCase( SECTION,
"(new Date("+t+")).getUTCMilliseconds()",
obj.msFromTime(t),
(new Date(t)).getUTCMilliseconds() );
}
},

/**
File Name:          15_9_5_21__2.js
ECMA Section:       15.9.5.21
Description:        Date.prototype.getUTCMilliseconds

1.  Let t be this time value.
2.  If t is NaN, return NaN.
3.  Return msFromTime(t).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_21__2:function(){
var SECTION = "15.9.5.21";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getUTCMilliseconds()";
var TIME_0000  = this.time0000;
//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, TIME_0000 );

//test();

function addTestCase(obj, t ) {
obj.TestCase( SECTION,
"(new Date("+t+")).getUTCMilliseconds()",
obj.msFromTime(t),
(new Date(t)).getUTCMilliseconds() );
}

},

/**
File Name:          15_9_5_21__3.js
ECMA Section:       15.9.5.21
Description:        Date.prototype.getUTCMilliseconds

1.  Let t be this time value.
2.  If t is NaN, return NaN.
3.  Return msFromTime(t).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_21__3:function(){
var SECTION = "15.9.5.21";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getUTCMilliseconds()";

//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_1970 );

//test();

function addTestCase(obj, t ) {
obj.TestCase( SECTION,
"(new Date("+t+")).getUTCMilliseconds()",
obj.msFromTime(t),
(new Date(t)).getUTCMilliseconds() );
}

},

/**
File Name:          15_9_5_21__4.js
ECMA Section:       15.9.5.21
Description:        Date.prototype.getUTCMilliseconds

1.  Let t be this time value.
2.  If t is NaN, return NaN.
3.  Return msFromTime(t).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_21__4:function(){
var SECTION = "15.9.5.21";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getUTCMilliseconds()";

//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_1900 );

//test();

function addTestCase(obj, t ) {
obj.TestCase( SECTION,
"(new Date("+t+")).getUTCMilliseconds()",
obj.msFromTime(t),
(new Date(t)).getUTCMilliseconds() );
}

},

/**
File Name:          15_9_5_21__5.js
ECMA Section:       15.9.5.21
Description:        Date.prototype.getUTCMilliseconds

1.  Let t be this time value.
2.  If t is NaN, return NaN.
3.  Return msFromTime(t).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_21__5:function(){
var SECTION = "15.9.5.21";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getUTCMilliseconds()";

//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_2000 );

//test();

function addTestCase(obj, t ) {
obj.TestCase( SECTION,
"(new Date("+t+")).getUTCMilliseconds()",
obj.msFromTime(t),
(new Date(t)).getUTCMilliseconds() );
}

},

/**
File Name:          15_9_5_21__6.js
ECMA Section:       15.9.5.21
Description:        Date.prototype.getUTCMilliseconds

1.  Let t be this time value.
2.  If t is NaN, return NaN.
3.  Return msFromTime(t).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_21__6:function(){
var SECTION = "15.9.5.21";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getUTCMilliseconds()";

//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.UTC_FEB_29_2000 );

//test();

function addTestCase(obj, t ) {
obj.TestCase( SECTION,
"(new Date("+t+")).getUTCMilliseconds()",
obj.msFromTime(t),
(new Date(t)).getUTCMilliseconds() );
}

},

/**
File Name:          15_9_5_21__7.js
ECMA Section:       15.9.5.21
Description:        Date.prototype.getUTCMilliseconds

1.  Let t be this time value.
2.  If t is NaN, return NaN.
3.  Return msFromTime(t).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_21__7:function(){
var SECTION = "15.9.5.21";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getUTCMilliseconds()";
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, UTC_JAN_1_2005 );

//test();

function addTestCase(obj, t ) {
obj.TestCase( SECTION,
"(new Date("+t+")).getUTCMilliseconds()",
obj.msFromTime(t),
(new Date(t)).getUTCMilliseconds() );
}

},

/**
File Name:          15_9_5_21__8.js
ECMA Section:       15.9.5.21
Description:        Date.prototype.getUTCMilliseconds

1.  Let t be this time value.
2.  If t is NaN, return NaN.
3.  Return msFromTime(t).

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_21__8:function(){
var SECTION = "15.9.5.21";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getUTCMilliseconds()";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"(new Date(NaN)).getUTCMilliseconds()",
NaN,
(new Date(NaN)).getUTCMilliseconds() );

this.TestCase( SECTION,
"Date.prototype.getUTCMilliseconds.length",
0,
Date.prototype.getUTCMilliseconds.length );
//test();
},

/**
File Name:          15_9_5_22__1.js
ECMA Section:       15.9.5.22
Description:        Date.prototype.getTimezoneOffset

Returns the difference between local time and UTC time in minutes.
1.  Let t be this time value.
2.  If t is NaN, return NaN.
3.  Return (t - LocalTime(t)) / msPerMinute.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_22__1:function(){
var SECTION = "15.9.5.22";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getTimezoneOffset()";
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
var TIME_0000  = this.time0000;
//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, TIME_0000 );
addTestCase(this, this.TIME_1970 );
addTestCase(this, this.TIME_1900 );
addTestCase(this, this.TIME_2000 );
addTestCase(this, this.UTC_FEB_29_2000 );
addTestCase(this, UTC_JAN_1_2005 );

this.TestCase( SECTION,
"(new Date(NaN)).getTimezoneOffset()",
NaN,
(new Date(NaN)).getTimezoneOffset() );

this.TestCase( SECTION,
"Date.prototype.getTimezoneOffset.length",
0,
Date.prototype.getTimezoneOffset.length );

//test();

function addTestCase(obj, t ) {
for ( m = 0; m <= 1000; m+=100 ) {
t++;
obj.TestCase( SECTION,
"(new Date("+t+")).getTimezoneOffset()",
(t - obj.LocalTime(t)) / obj.msPerMinute,
(new Date(t)).getTimezoneOffset() );
}
}

},

/**
File Name:          15_9_5_22__2.js
ECMA Section:       15.9.5.22
Description:        Date.prototype.getTimezoneOffset

Returns the difference between local time and UTC time in minutes.
1.  Let t be this time value.
2.  If t is NaN, return NaN.
3.  Return (t - LocalTime(t)) / msPerMinute.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_22__2:function(){
var SECTION = "15.9.5.22";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getTimezoneOffset()";
var TIME_0000  = this.time0000;
//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, TIME_0000 );

//test();

function addTestCase(obj, t ) {
for ( m = 0; m <= 1000; m+=100 ) {
t++;
obj.TestCase( SECTION,
"(new Date("+t+")).getTimezoneOffset()",
(t - obj.LocalTime(t)) / obj.msPerMinute,
(new Date(t)).getTimezoneOffset() );
}
}

},

/**
File Name:          15_9_5_22__3.js
ECMA Section:       15.9.5.22
Description:        Date.prototype.getTimezoneOffset

Returns the difference between local time and UTC time in minutes.
1.  Let t be this time value.
2.  If t is NaN, return NaN.
3.  Return (t - LocalTime(t)) / msPerMinute.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_22__3:function(){
var SECTION = "15.9.5.22";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getTimezoneOffset()";

//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_1970 );

//est();

function addTestCase(obj, t ) {
for ( m = 0; m <= 1000; m+=100 ) {
t++;
obj.TestCase( SECTION,
"(new Date("+t+")).getTimezoneOffset()",
(t - obj.LocalTime(t)) / obj.msPerMinute,
(new Date(t)).getTimezoneOffset() );
}
}

},

/**
File Name:          15_9_5_22__4.js
ECMA Section:       15.9.5.22
Description:        Date.prototype.getTimezoneOffset

Returns the difference between local time and UTC time in minutes.
1.  Let t be this time value.
2.  If t is NaN, return NaN.
3.  Return (t - LocalTime(t)) / msPerMinute.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_22__4:function(){
var SECTION = "15.9.5.22";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getTimezoneOffset()";

//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_1900 );

//test();

function addTestCase(obj, t ) {
for ( m = 0; m <= 1000; m+=100 ) {
t++;
obj.TestCase( SECTION,
"(new Date("+t+")).getTimezoneOffset()",
(t - obj.LocalTime(t)) / obj.msPerMinute,
(new Date(t)).getTimezoneOffset() );
}
}

},

/**
File Name:          15_9_5_22__5.js
ECMA Section:       15.9.5.22
Description:        Date.prototype.getTimezoneOffset

Returns the difference between local time and UTC time in minutes.
1.  Let t be this time value.
2.  If t is NaN, return NaN.
3.  Return (t - obj.LocalTime(t)) / msPerMinute.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_22__5:function(){
var SECTION = "15.9.5.22";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getTimezoneOffset()";

//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_2000 );

//test();

function addTestCase(obj, t ) {
for ( m = 0; m <= 1000; m+=100 ) {
t++;
obj.TestCase( SECTION,
"(new Date("+t+")).getTimezoneOffset()",
(t - obj.LocalTime(t)) / obj.msPerMinute,
(new Date(t)).getTimezoneOffset() );
}
}

},

/**
File Name:          15_9_5_22__6.js
ECMA Section:       15.9.5.22
Description:        Date.prototype.getTimezoneOffset

Returns the difference between local time and UTC time in minutes.
1.  Let t be this time value.
2.  If t is NaN, return NaN.
3.  Return (t - obj.LocalTime(t)) / msPerMinute.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_22__6:function(){
var SECTION = "15.9.5.22";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getTimezoneOffset()";

//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.UTC_FEB_29_2000 );

//test();

function addTestCase(obj, t ) {
for ( m = 0; m <= 1000; m+=100 ) {
t++;
obj.TestCase( SECTION,
"(new Date("+t+")).getTimezoneOffset()",
(t - obj.LocalTime(t)) / obj.msPerMinute,
(new Date(t)).getTimezoneOffset() );
}
}

},

/**
File Name:          15_9_5_22__7.js
ECMA Section:       15.9.5.22
Description:        Date.prototype.getTimezoneOffset

Returns the difference between local time and UTC time in minutes.
1.  Let t be this time value.
2.  If t is NaN, return NaN.
3.  Return (t - obj.LocalTime(t)) / msPerMinute.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_22__7:function(){
var SECTION = "15.9.5.22";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getTimezoneOffset()";
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, UTC_JAN_1_2005 );

//test();

function addTestCase(obj, t ) {
for ( m = 0; m <= 1000; m+=100 ) {
t++;
obj.TestCase( SECTION,
"(new Date("+t+")).getTimezoneOffset()",
(t - obj.LocalTime(t)) / obj.msPerMinute,
(new Date(t)).getTimezoneOffset() );
}
}

},

/**
File Name:          15_9_5_22__8.js
ECMA Section:       15.9.5.22
Description:        Date.prototype.getTimezoneOffset

Returns the difference between local time and UTC time in minutes.
1.  Let t be this time value.
2.  If t is NaN, return NaN.
3.  Return (t - obj.LocalTime(t)) / msPerMinute.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_22__8:function(){
var SECTION = "15.9.5.22";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getTimezoneOffset()";

//writeHeaderToLog( SECTION + " "+ TITLE);

this.TestCase( SECTION,
"(new Date(NaN)).getTimezoneOffset()",
NaN,
(new Date(NaN)).getTimezoneOffset() );

this.TestCase( SECTION,
"Date.prototype.getTimezoneOffset.length",
0,
Date.prototype.getTimezoneOffset.length );
//test();

},

/**
File Name:          15_9_5_23__1.js
ECMA Section:       15.9.5.23 Date.prototype.setTime(time)
Description:

1.  If the this value is not a Date object, generate a runtime error.
2.  Call ToNumber(time).
3.  Call TimeClip(Result(1)).
4.  Set the [[Value]] property of the this value to Result(2).
5.  Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_23__1:function(){
var SECTION = "15.9.5.23-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE = "Date.prototype.setTime()";

//writeHeaderToLog( SECTION + " Date.prototype.setTime(time)");

var now = "now";
addTestCase(this, 0, 0 );

//test();

function addTestCase(obj, startTime, setTime ) {
if ( startTime == "now" ) {
DateCase = new Date();
} else {
DateCase = new Date( startTime );
}

DateCase.setTime( setTime );
var DateString = "var d = new Date("+startTime+"); d.setTime("+setTime+"); d" ;
var UTCDate   = UTCDateFromTime (obj, Number(setTime) );
var LocalDate = LocalDateFromTime(obj, Number(setTime) );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,      DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,      DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,       DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,      DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,       DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           UTCDate.day,        DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,      DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,    DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,    DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,         DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,     DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,    DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,     DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              LocalDate.day,      DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,    DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,  DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,  DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,       DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}

function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj, t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj, t) );
}
function UTCDateFromTime(obj, t) {
return ( MyDateFromTime(obj, t) );
}
function MyDateFromTime(obj,  t ) {
var d       = new MyDate();
d.year      = obj.YearFromTime(t);
d.month     = obj.MonthFromTime(t);
d.date      = obj.DateFromTime(t);
d.hours     = obj.HourFromTime(t);
d.minutes   = obj.MinFromTime(t);
d.seconds   = obj.SecFromTime(t);
d.ms        = obj.msFromTime(t);
d.time      = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value     = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day       = obj.WeekDay( d.value );
return (d);
}

},

/**
File Name:          15_9_5_23__10.js
ECMA Section:       15.9.5.23 Date.prototype.setTime(time)
Description:

1.  If the this value is not a Date object, generate a runtime error.
2.  Call ToNumber(time).
3.  Call TimeClip(Result(1)).
4.  Set the [[Value]] property of the this value to Result(2).
5.  Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_23__10:function(){
var SECTION = "15.9.5.23-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE = "Date.prototype.setTime()";

//writeHeaderToLog( SECTION + " Date.prototype.setTime(time)");

var now = "now";
addTestCase(this, now, -2208988800000 );

//test();

function addTestCase(obj, startTime, setTime ) {
if ( startTime == "now" ) {
DateCase = new Date();
} else {
DateCase = new Date( startTime );
}

DateCase.setTime( setTime );
var DateString = "var d = new Date("+startTime+"); d.setTime("+setTime+"); d" ;
var UTCDate   = UTCDateFromTime (obj, Number(setTime) );
var LocalDate = LocalDateFromTime(obj, Number(setTime) );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,      DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,      DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,       DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,      DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,       DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           UTCDate.day,        DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,      DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,    DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,    DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,         DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,     DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,    DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,     DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              LocalDate.day,      DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,    DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,  DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,  DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,       DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}

function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj, t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj, t) );
}
function UTCDateFromTime(obj, t) {
return ( MyDateFromTime(obj, t) );
}
function MyDateFromTime(obj, t ) {
var d       = new MyDate();
d.year      = obj.YearFromTime(t);
d.month     = obj.MonthFromTime(t);
d.date      = obj.DateFromTime(t);
d.hours     = obj.HourFromTime(t);
d.minutes   = obj.MinFromTime(t);
d.seconds   = obj.SecFromTime(t);
d.ms        = obj.msFromTime(t);
d.time      = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value     = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day       = obj.WeekDay( d.value );
return (d);
}

},

/**
File Name:          15_9_5_23__11.js
ECMA Section:       15.9.5.23 Date.prototype.setTime(time)
Description:

1.  If the this value is not a Date object, generate a runtime error.
2.  Call ToNumber(time).
3.  Call TimeClip(Result(1)).
4.  Set the [[Value]] property of the this value to Result(2).
5.  Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_23__11:function(){
var SECTION = "15.9.5.23-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE = "Date.prototype.setTime()";

//writeHeaderToLog( SECTION + " Date.prototype.setTime(time)");

var now = "now";
addTestCase(this, now, -86400000 );

//test();


function addTestCase(obj, startTime, setTime ) {
if ( startTime == "now" ) {
DateCase = new Date();
} else {
DateCase = new Date( startTime );
}

DateCase.setTime( setTime );
var DateString = "var d = new Date("+startTime+"); d.setTime("+setTime+"); d" ;
var UTCDate   = UTCDateFromTime (obj, Number(setTime) );
var LocalDate = LocalDateFromTime(obj, Number(setTime) );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,      DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,      DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,       DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,      DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,       DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           UTCDate.day,        DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,      DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,    DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,    DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,         DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,     DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,    DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,     DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              LocalDate.day,      DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,    DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,  DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,  DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,       DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}
function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj, t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj, t) );
}
function UTCDateFromTime(obj, t) {
return ( MyDateFromTime(obj, t) );
}
function MyDateFromTime(obj, t ) {
var d       = new MyDate();
d.year      = obj.YearFromTime(t);
d.month     = obj.MonthFromTime(t);
d.date      = obj.DateFromTime(t);
d.hours     = obj.HourFromTime(t);
d.minutes   = obj.MinFromTime(t);
d.seconds   = obj.SecFromTime(t);
d.ms        = obj.msFromTime(t);
d.time      = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value     = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day       = obj.WeekDay( d.value );
return (d);
}

},

/**
File Name:          15_9_5_23__12.js
ECMA Section:       15.9.5.23 Date.prototype.setTime(time)
Description:

1.  If the this value is not a Date object, generate a runtime error.
2.  Call ToNumber(time).
3.  Call TimeClip(Result(1)).
4.  Set the [[Value]] property of the this value to Result(2).
5.  Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_23__12:function(){
var SECTION = "15.9.5.23-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE = "Date.prototype.setTime()";

//writeHeaderToLog( SECTION + " Date.prototype.setTime(time)");

var now = "now";
addTestCase(this, now, 946684800000 );

//test();

function addTestCase(obj, startTime, setTime ) {
if ( startTime == "now" ) {
DateCase = new Date();
} else {
DateCase = new Date( startTime );
}

DateCase.setTime( setTime );
var DateString = "var d = new Date("+startTime+"); d.setTime("+setTime+"); d" ;
var UTCDate   = UTCDateFromTime (obj, Number(setTime) );
var LocalDate = LocalDateFromTime(obj, Number(setTime) );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,      DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,      DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,       DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,      DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,       DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           UTCDate.day,        DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,      DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,    DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,    DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,         DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,     DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,    DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,     DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              LocalDate.day,      DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,    DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,  DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,  DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,       DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}
function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj, t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj, t) );
}
function UTCDateFromTime(obj, t) {
return ( MyDateFromTime(obj, t) );
}
function MyDateFromTime(obj, t ) {
var d       = new MyDate();
d.year      = obj.YearFromTime(t);
d.month     = obj.MonthFromTime(t);
d.date      = obj.DateFromTime(t);
d.hours     = obj.HourFromTime(t);
d.minutes   = obj.MinFromTime(t);
d.seconds   = obj.SecFromTime(t);
d.ms        = obj.msFromTime(t);
d.time      = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value     = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day       = obj.WeekDay( d.value );
return (d);
}

},

/**
File Name:          15_9_5_23__13.js
ECMA Section:       15.9.5.23 Date.prototype.setTime(time)
Description:

1.  If the this value is not a Date object, generate a runtime error.
2.  Call ToNumber(time).
3.  Call TimeClip(Result(1)).
4.  Set the [[Value]] property of the this value to Result(2).
5.  Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_23__13:function(){
var SECTION = "15.9.5.23-13";
var VERSION = "ECMA_1";
//startTest();
var TITLE = "Date.prototype.setTime()";

//writeHeaderToLog( SECTION + " Date.prototype.setTime(time)");

var now = "now";
addTestCase(this, now, -2208988800000 );

//test();

function addTestCase(obj, startTime, setTime ) {
if ( startTime == "now" ) {
DateCase = new Date();
} else {
DateCase = new Date( startTime );
}

DateCase.setTime( setTime );
var DateString = "var d = new Date("+startTime+"); d.setTime("+setTime+"); d" ;
var UTCDate   = UTCDateFromTime (obj, Number(setTime) );
var LocalDate = LocalDateFromTime(obj, Number(setTime) );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,      DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,      DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,       DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,      DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,       DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           UTCDate.day,        DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,      DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,    DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,    DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,         DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,     DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,    DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,     DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              LocalDate.day,      DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,    DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,  DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,  DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,       DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}
function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj, t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj, t) );
}
function UTCDateFromTime(obj, t) {
return ( MyDateFromTime(obj, t) );
}
function MyDateFromTime(obj, t ) {
var d       = new MyDate();
d.year      = obj.YearFromTime(t);
d.month     = obj.MonthFromTime(t);
d.date      = obj.DateFromTime(t);
d.hours     = obj.HourFromTime(t);
d.minutes   = obj.MinFromTime(t);
d.seconds   = obj.SecFromTime(t);
d.ms        = obj.msFromTime(t);
d.time      = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value     = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day       = obj.WeekDay( d.value );
return (d);
}

},

/**
File Name:          15_9_5_23__14.js
ECMA Section:       15.9.5.23 Date.prototype.setTime(time)
Description:

1.  If the this value is not a Date object, generate a runtime error.
2.  Call ToNumber(time).
3.  Call TimeClip(Result(1)).
4.  Set the [[Value]] property of the this value to Result(2).
5.  Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_23__14:function(){
var SECTION = "15.9.5.23-14";
var VERSION = "ECMA_1";
//startTest();
var TITLE = "Date.prototype.setTime()";

//writeHeaderToLog( SECTION + " Date.prototype.setTime(time)");

var now = "now";
addTestCase(this, now, 946684800000 );

//test();

function addTestCase(obj, startTime, setTime ) {
if ( startTime == "now" ) {
DateCase = new Date();
} else {
DateCase = new Date( startTime );
}

DateCase.setTime( setTime );
var DateString = "var d = new Date("+startTime+"); d.setTime("+setTime+"); d" ;
var UTCDate   = UTCDateFromTime (obj, Number(setTime) );
var LocalDate = LocalDateFromTime(obj, Number(setTime) );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,      DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,      DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,       DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,      DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,       DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           UTCDate.day,        DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,      DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,    DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,    DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,         DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,     DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,    DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,     DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              LocalDate.day,      DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,    DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,  DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,  DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,       DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}
function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d       = new MyDate();
d.year      = obj.YearFromTime(t);
d.month     = obj.MonthFromTime(t);
d.date      = obj.DateFromTime(t);
d.hours     = obj.HourFromTime(t);
d.minutes   = obj.MinFromTime(t);
d.seconds   = obj.SecFromTime(t);
d.ms        = obj.msFromTime(t);
d.time      = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value     = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day       = obj.WeekDay( d.value );
return (d);
}

},

/**
File Name:          15_9_5_23__15.js
ECMA Section:       15.9.5.23 Date.prototype.setTime(time)
Description:

1.  If the this value is not a Date object, generate a runtime error.
2.  Call ToNumber(time).
3.  Call TimeClip(Result(1)).
4.  Set the [[Value]] property of the this value to Result(2).
5.  Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_23__15:function(){
var SECTION = "15.9.5.23-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE = "Date.prototype.setTime()";

//writeHeaderToLog( SECTION + " Date.prototype.setTime(time)");

var now = "now";
addTestCase(this, now, 0 );

//test();

function addTestCase(obj, startTime, setTime ) {
if ( startTime == "now" ) {
DateCase = new Date();
} else {
DateCase = new Date( startTime );
}

DateCase.setTime( setTime );
var DateString = "var d = new Date("+startTime+"); d.setTime("+setTime+"); d" ;
var UTCDate   = UTCDateFromTime (obj, Number(setTime) );
var LocalDate = LocalDateFromTime(obj, Number(setTime) );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,      DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,      DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,       DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,      DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,       DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           UTCDate.day,        DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,      DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,    DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,    DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,         DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,     DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,    DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,     DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              LocalDate.day,      DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,    DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,  DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,  DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,       DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}
function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d       = new MyDate();
d.year      = obj.YearFromTime(t);
d.month     = obj.MonthFromTime(t);
d.date      = obj.DateFromTime(t);
d.hours     = obj.HourFromTime(t);
d.minutes   = obj.MinFromTime(t);
d.seconds   = obj.SecFromTime(t);
d.ms        = obj.msFromTime(t);
d.time      = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value     = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day       = obj.WeekDay( d.value );
return (d);
}

},

/**
File Name:          15_9_5_23__16.js
ECMA Section:       15.9.5.23 Date.prototype.setTime(time)
Description:

1.  If the this value is not a Date object, generate a runtime error.
2.  Call ToNumber(time).
3.  Call TimeClip(Result(1)).
4.  Set the [[Value]] property of the this value to Result(2).
5.  Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_23__16:function(){
var SECTION = "15.9.5.23-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE = "Date.prototype.setTime()";

//writeHeaderToLog( SECTION + " Date.prototype.setTime(time)");

var now = "now";
addTestCase(this, now, String( this.TIME_1900 ) );

//test();

function addTestCase(obj, startTime, setTime ) {
if ( startTime == "now" ) {
DateCase = new Date();
} else {
DateCase = new Date( startTime );
}

DateCase.setTime( setTime );
var DateString = "var d = new Date("+startTime+"); d.setTime("+setTime+"); d" ;
var UTCDate   = UTCDateFromTime (obj, Number(setTime) );
var LocalDate = LocalDateFromTime(obj, Number(setTime) );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,      DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,      DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,       DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,      DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,       DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           UTCDate.day,        DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,      DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,    DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,    DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,         DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,     DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,    DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,     DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              LocalDate.day,      DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,    DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,  DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,  DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,       DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}
function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d       = new MyDate();
d.year      = obj.YearFromTime(t);
d.month     = obj.MonthFromTime(t);
d.date      = obj.DateFromTime(t);
d.hours     = obj.HourFromTime(t);
d.minutes   = obj.MinFromTime(t);
d.seconds   = obj.SecFromTime(t);
d.ms        = obj.msFromTime(t);
d.time      = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value     = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day       = obj.WeekDay( d.value );
return (d);
}

},

/**
File Name:          15_9_5_23__17.js
ECMA Section:       15.9.5.23 Date.prototype.setTime(time)
Description:

1.  If the this value is not a Date object, generate a runtime error.
2.  Call ToNumber(time).
3.  Call TimeClip(Result(1)).
4.  Set the [[Value]] property of the this value to Result(2).
5.  Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_23__17:function(){
var SECTION = "15.9.5.23-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE = "Date.prototype.setTime()";

//writeHeaderToLog( SECTION + " Date.prototype.setTime(time)");

var now = "now";
addTestCase(this, now, String( this.TZ_DIFF* this.msPerHour ) );

//test();

function addTestCase(obj, startTime, setTime ) {
if ( startTime == "now" ) {
DateCase = new Date();
} else {
DateCase = new Date( startTime );
}

DateCase.setTime( setTime );
var DateString = "var d = new Date("+startTime+"); d.setTime("+setTime+"); d" ;
var UTCDate   = UTCDateFromTime (obj, Number(setTime) );
var LocalDate = LocalDateFromTime(obj, Number(setTime) );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,      DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,      DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,       DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,      DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,       DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           UTCDate.day,        DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,      DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,    DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,    DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,         DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,     DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,    DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,     DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              LocalDate.day,      DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,    DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,  DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,  DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,       DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}
function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d       = new MyDate();
d.year      = obj.YearFromTime(t);
d.month     = obj.MonthFromTime(t);
d.date      = obj.DateFromTime(t);
d.hours     = obj.HourFromTime(t);
d.minutes   = obj.MinFromTime(t);
d.seconds   = obj.SecFromTime(t);
d.ms        = obj.msFromTime(t);
d.time      = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value     = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day       = obj.WeekDay( d.value );
return (d);
}

},

/**
File Name:          15_9_5_23__18.js
ECMA Section:       15.9.5.23 Date.prototype.setTime(time)
Description:

1.  If the this value is not a Date object, generate a runtime error.
2.  Call ToNumber(time).
3.  Call TimeClip(Result(1)).
4.  Set the [[Value]] property of the this value to Result(2).
5.  Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_23__18:function(){
var SECTION = "15.9.5.23-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE = "Date.prototype.setTime()";

//writeHeaderToLog( SECTION + " Date.prototype.setTime(time)");

var now = "now";
addTestCase(this, now, String( this.TIME_2000 ) );

//test();

function addTestCase(obj, startTime, setTime ) {
if ( startTime == "now" ) {
DateCase = new Date();
} else {
DateCase = new Date( startTime );
}

DateCase.setTime( setTime );
var DateString = "var d = new Date("+startTime+"); d.setTime("+setTime+"); d" ;
var UTCDate   = UTCDateFromTime (obj, Number(setTime) );
var LocalDate = LocalDateFromTime(obj, Number(setTime) );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,      DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,      DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,       DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,      DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,       DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           UTCDate.day,        DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,      DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,    DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,    DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,         DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,     DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,    DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,     DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              LocalDate.day,      DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,    DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,  DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,  DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,       DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}
function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d       = new MyDate();
d.year      = obj.YearFromTime(t);
d.month     = obj.MonthFromTime(t);
d.date      = obj.DateFromTime(t);
d.hours     = obj.HourFromTime(t);
d.minutes   = obj.MinFromTime(t);
d.seconds   = obj.SecFromTime(t);
d.ms        = obj.msFromTime(t);
d.time      = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value     = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day       = obj.WeekDay( d.value );
return (d);
}

},

/**
File Name:          15_9_5_23__2.js
ECMA Section:       15.9.5.23
Description:        Date.prototype.setTime

1.  If the this value is not a Date object, generate a runtime error.
2.  Call ToNumber(time).
3.  Call TimeClip(Result(1)).
4.  Set the [[Value]] property of the this value to Result(2).
5.  Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_23__2:function(){
var SECTION = "15.9.5.23-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.setTime()";

//writeHeaderToLog( SECTION + " "+ TITLE);

test_times = new Array( this.TIME_NOW, this.TIME_1970, this.TIME_1900, this.TIME_2000 );

for ( var j = 0; j < test_times.length; j++ ) {
addTestCase(this, new Date(this.TIME_NOW), test_times[j] );
}

this.TestCase( SECTION,
"(new Date(NaN)).setTime()",
NaN,
(new Date(NaN)).setTime() );

this.TestCase( SECTION,
"Date.prototype.setTime.length",
1,
Date.prototype.setTime.length );
//test();

function addTestCase(obj, d, t ) {
obj.TestCase( SECTION,
"( "+d+" ).setTime("+t+")",
t,
d.setTime(t) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t+1.1)+")",
obj.TimeClip(t+1.1),
d.setTime(t+1.1) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t+1)+")",
t+1,
d.setTime(t+1) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t-1)+")",
t-1,
d.setTime(t-1) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t-obj.TZ_ADJUST)+")",
t-obj.TZ_ADJUST,
d.setTime(t-obj.TZ_ADJUST) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t+obj.TZ_ADJUST)+")",
t+obj.TZ_ADJUST,
d.setTime(t+obj.TZ_ADJUST) );
}

},

/**
File Name:          15_9_5_23__3__n.js
ECMA Section:       15.9.5.23
Description:        Date.prototype.setTime

1.  If the this value is not a Date object, generate a runtime error.
2.  Call ToNumber(time).
3.  Call TimeClip(Result(1)).
4.  Set the [[Value]] property of the this value to Result(2).
5.  Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_23__3__n:function(){
var SECTION = "15.9.5.23-3-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.setTime()";

//writeHeaderToLog( SECTION + " "+ TITLE);

var MYDATE = new MyDate(this.TIME_1970);

DESCRIPTION = "MYDATE.setTime(this.TIME_2000)";
EXPECTED = "error";

this.TestCase( SECTION,
"MYDATE.setTime(this.TIME_2000)",
"error",
eval("MYDATE.setTime(this.TIME_2000)") );

//test();

function MyDate(value) {
this.value = value;
this.setTime = Date.prototype.setTime;
return this;
}

},

/**
File Name:          15_9_5_23__4.js
ECMA Section:       15.9.5.23
Description:        Date.prototype.setTime

1.  If the this value is not a Date object, generate a runtime error.
2.  Call ToNumber(time).
3.  Call TimeClip(Result(1)).
4.  Set the [[Value]] property of the this value to Result(2).
5.  Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_23__4:function(){
var SECTION = "15.9.5.23-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.setTime()";
var TIME_0000  = this.time0000;
//writeHeaderToLog( SECTION + " "+ TITLE);
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
test_times = new Array( this.TIME_NOW, TIME_0000, this.TIME_1970, this.TIME_1900, this.TIME_2000,
this.UTC_FEB_29_2000, UTC_JAN_1_2005 );


for ( var j = 0; j < test_times.length; j++ ) {
addTestCase(this, new Date(TIME_0000), test_times[j] );
}

this.TestCase( SECTION,
"(new Date(NaN)).setTime()",
NaN,
(new Date(NaN)).setTime() );

this.TestCase( SECTION,
"Date.prototype.setTime.length",
1,
Date.prototype.setTime.length );
//test();

function addTestCase(obj, d, t ) {
obj.TestCase( SECTION,
"( "+d+" ).setTime("+t+")",
t,
d.setTime(t) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t+1.1)+")",
obj.TimeClip(t+1.1),
d.setTime(t+1.1) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t+1)+")",
t+1,
d.setTime(t+1) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t-1)+")",
t-1,
d.setTime(t-1) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t-obj.TZ_ADJUST)+")",
t-obj.TZ_ADJUST,
d.setTime(t-obj.TZ_ADJUST) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t+obj.TZ_ADJUST)+")",
t+obj.TZ_ADJUST,
d.setTime(t+obj.TZ_ADJUST) );
}

},

/**
File Name:          15_9_5_23__5.js
ECMA Section:       15.9.5.23
Description:        Date.prototype.setTime

1.  If the this value is not a Date object, generate a runtime error.
2.  Call ToNumber(time).
3.  Call TimeClip(Result(1)).
4.  Set the [[Value]] property of the this value to Result(2).
5.  Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_23__5:function(){
var SECTION = "15.9.5.23-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.setTime()";
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
var TIME_0000  = this.time0000;
//writeHeaderToLog( SECTION + " "+ TITLE);

test_times = new Array( this.TIME_NOW, TIME_0000, this.TIME_1970, this.TIME_1900, this.TIME_2000,
this.UTC_FEB_29_2000, UTC_JAN_1_2005 );


for ( var j = 0; j < test_times.length; j++ ) {
addTestCase(this, new Date(this.TIME_1970), test_times[j] );
}


this.TestCase( SECTION,
"(new Date(NaN)).setTime()",
NaN,
(new Date(NaN)).setTime() );

this.TestCase( SECTION,
"Date.prototype.setTime.length",
1,
Date.prototype.setTime.length );
//test();

function addTestCase(obj, d, t ) {
obj.TestCase( SECTION,
"( "+d+" ).setTime("+t+")",
t,
d.setTime(t) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t+1.1)+")",
obj.TimeClip(t+1.1),
d.setTime(t+1.1) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t+1)+")",
t+1,
d.setTime(t+1) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t-1)+")",
t-1,
d.setTime(t-1) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t-obj.TZ_ADJUST)+")",
t-obj.TZ_ADJUST,
d.setTime(t-obj.TZ_ADJUST) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t+obj.TZ_ADJUST)+")",
t+obj.TZ_ADJUST,
d.setTime(t+obj.TZ_ADJUST) );
}

},

/**
File Name:          15_9_5_23__6.js
ECMA Section:       15.9.5.23
Description:        Date.prototype.setTime

1.  If the this value is not a Date object, generate a runtime error.
2.  Call ToNumber(time).
3.  Call TimeClip(Result(1)).
4.  Set the [[Value]] property of the this value to Result(2).
5.  Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_23__6:function(){
var SECTION = "15.9.5.23-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.setTime()";
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
var TIME_0000  = this.time0000;
//writeHeaderToLog( SECTION + " "+ TITLE);

test_times = new Array( this.TIME_NOW, TIME_0000, this.TIME_1970, this.TIME_1900, this.TIME_2000,
this.UTC_FEB_29_2000, UTC_JAN_1_2005 );


for ( var j = 0; j < test_times.length; j++ ) {
addTestCase(this, new Date(this.TIME_1900), test_times[j] );
}


this.TestCase( SECTION,
"(new Date(NaN)).setTime()",
NaN,
(new Date(NaN)).setTime() );

this.TestCase( SECTION,
"Date.prototype.setTime.length",
1,
Date.prototype.setTime.length );
//test();
function addTestCase(obj, d, t ) {
obj.TestCase( SECTION,
"( "+d+" ).setTime("+t+")",
t,
d.setTime(t) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t+1.1)+")",
obj.TimeClip(t+1.1),
d.setTime(t+1.1) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t+1)+")",
t+1,
d.setTime(t+1) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t-1)+")",
t-1,
d.setTime(t-1) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t-obj.TZ_ADJUST)+")",
t-obj.TZ_ADJUST,
d.setTime(t-obj.TZ_ADJUST) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t+obj.TZ_ADJUST)+")",
t+obj.TZ_ADJUST,
d.setTime(t+obj.TZ_ADJUST) );
}

},

/**
File Name:          15_9_5_23__7.js
ECMA Section:       15.9.5.23
Description:        Date.prototype.setTime

1.  If the this value is not a Date object, generate a runtime error.
2.  Call ToNumber(time).
3.  Call TimeClip(Result(1)).
4.  Set the [[Value]] property of the this value to Result(2).
5.  Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_23__7:function(){
var SECTION = "15.9.5.23-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.setTime()";
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
var TIME_0000  = this.time0000;
//writeHeaderToLog( SECTION + " "+ TITLE);

test_times = new Array( this.TIME_NOW, TIME_0000, this.TIME_1970, this.TIME_1900, this.TIME_2000,
this.UTC_FEB_29_2000, UTC_JAN_1_2005 );


for ( var j = 0; j < test_times.length; j++ ) {
addTestCase(this, new Date(this.TIME_2000), test_times[j] );
}


this.TestCase( SECTION,
"(new Date(NaN)).setTime()",
NaN,
(new Date(NaN)).setTime() );

this.TestCase( SECTION,
"Date.prototype.setTime.length",
1,
Date.prototype.setTime.length );
//test();

function addTestCase(obj, d, t ) {
obj.TestCase( SECTION,
"( "+d+" ).setTime("+t+")",
t,
d.setTime(t) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t+1.1)+")",
obj.TimeClip(t+1.1),
d.setTime(t+1.1) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t+1)+")",
t+1,
d.setTime(t+1) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t-1)+")",
t-1,
d.setTime(t-1) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t-obj.TZ_ADJUST)+")",
t-obj.TZ_ADJUST,
d.setTime(t-obj.TZ_ADJUST) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t+obj.TZ_ADJUST)+")",
t+obj.TZ_ADJUST,
d.setTime(t+obj.TZ_ADJUST) );
}

},

/**
File Name:          15_9_5_23__8.js
ECMA Section:       15.9.5.23
Description:        Date.prototype.setTime

1.  If the this value is not a Date object, generate a runtime error.
2.  Call ToNumber(time).
3.  Call TimeClip(Result(1)).
4.  Set the [[Value]] property of the this value to Result(2).
5.  Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_23__8:function(){
var SECTION = "15.9.2.2";
var SECTION = "15.9.5.23-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.setTime()";
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
var TIME_0000  = this.time0000;
//writeHeaderToLog( SECTION + " "+ TITLE);

test_times = new Array( this.TIME_NOW, TIME_0000, this.TIME_1970, this.TIME_1900, this.TIME_2000,
this.UTC_FEB_29_2000, UTC_JAN_1_2005 );


for ( var j = 0; j < test_times.length; j++ ) {
addTestCase(this, new Date(this.UTC_FEB_29_2000), test_times[j] );
}

//test();

function addTestCase(obj, d, t ) {
obj.TestCase( SECTION,
"( "+d+" ).setTime("+t+")",
t,
d.setTime(t) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t+1.1)+")",
obj.TimeClip(t+1.1),
d.setTime(t+1.1) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t+1)+")",
t+1,
d.setTime(t+1) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t-1)+")",
t-1,
d.setTime(t-1) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t-obj.TZ_ADJUST)+")",
t-obj.TZ_ADJUST,
d.setTime(t-obj.TZ_ADJUST) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t+obj.TZ_ADJUST)+")",
t+obj.TZ_ADJUST,
d.setTime(t+obj.TZ_ADJUST) );
}

},

/**
File Name:          15_9_5_23__9.js
ECMA Section:       15.9.5.23
Description:        Date.prototype.setTime

1.  If the this value is not a Date object, generate a runtime error.
2.  Call ToNumber(time).
3.  Call TimeClip(Result(1)).
4.  Set the [[Value]] property of the this value to Result(2).
5.  Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_23__9:function(){
var SECTION = "15.9.5.23-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.setTime()";
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
var TIME_0000  = this.time0000;
//writeHeaderToLog( SECTION + " "+ TITLE);

test_times = new Array( this.TIME_NOW, TIME_0000, this.TIME_1970, this.TIME_1900, this.TIME_2000,
this.UTC_FEB_29_2000, UTC_JAN_1_2005 );


for ( var j = 0; j < test_times.length; j++ ) {
addTestCase(this, new Date(UTC_JAN_1_2005), test_times[j] );
}

//test();

function addTestCase(obj, d, t ) {
obj.TestCase( SECTION,
"( "+d+" ).setTime("+t+")",
t,
d.setTime(t) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t+1.1)+")",
obj.TimeClip(t+1.1),
d.setTime(t+1.1) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t+1)+")",
t+1,
d.setTime(t+1) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t-1)+")",
t-1,
d.setTime(t-1) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t-obj.TZ_ADJUST)+")",
t-obj.TZ_ADJUST,
d.setTime(t-obj.TZ_ADJUST) );

obj.TestCase( SECTION,
"( "+d+" ).setTime("+(t+obj.TZ_ADJUST)+")",
t+obj.TZ_ADJUST,
d.setTime(t+obj.TZ_ADJUST) );
}

},

/**
File Name:          15_9_5_24__1.js
ECMA Section:       15.9.5.24 Date.prototype.setTime(time)
Description:
1.      If the this value is not a Date object, generate a runtime error.
2.      Call ToNumber(time).
3.      Call TimeClip(Result(1)).
4.      Set the [[Value]] property of the this value to Result(2).
5.      Return the value of the [[Value]] property of the this value.
Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_24__1:function(){
var TITLE = "Date.prototype.setTime"
var SECTION = "15.9.5.24-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setMilliseconds(ms)");


addTestCase(this, 0, 0 );

//test();

function addTestCase(obj, startms, newms ) {

var DateCase = new Date( startms );
DateCase.setMilliseconds( newms );
var DateString = "var date = new Date("+ startms +"); date.setMilliseconds("+ newms +"); date";
var UTCDate = UTCDateFromTime(obj, Number(newms) );
var LocalDate = LocalDateFromTime(obj, Number(newms) );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,    DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,   DateCase.getUTCDate() );

obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );

obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}

function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}

},

/**
File Name:          15_9_5_24__2.js
ECMA Section:       15.9.5.24 Date.prototype.setTime(time)
Description:
1.      If the this value is not a Date object, generate a runtime error.
2.      Call ToNumber(time).
3.      Call TimeClip(Result(1)).
4.      Set the [[Value]] property of the this value to Result(2).
5.      Return the value of the [[Value]] property of the this value.
Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_24__2:function(){
var TITLE = "Date.prototype.setTime"
var SECTION = "15.9.5.24-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setMilliseconds(ms)");


addTestCase(this, 0, -86400000 );

//test();

function addTestCase(obj, startms, newms ) {

var DateCase = new Date( startms );
DateCase.setMilliseconds( newms );
var DateString = "var date = new Date("+ startms +"); date.setMilliseconds("+ newms +"); date";
var UTCDate = UTCDateFromTime(obj, Number(newms) );
var LocalDate = LocalDateFromTime(obj, Number(newms) );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,    DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,   DateCase.getUTCDate() );

obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );

obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}

function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}

},

/**
File Name:          15_9_5_24__3.js
ECMA Section:       15.9.5.24 Date.prototype.setTime(time)
Description:
1.      If the this value is not a Date object, generate a runtime error.
2.      Call ToNumber(time).
3.      Call TimeClip(Result(1)).
4.      Set the [[Value]] property of the this value to Result(2).
5.      Return the value of the [[Value]] property of the this value.
Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_24__3:function(){
var TITLE = "Date.prototype.setTime"
var SECTION = "15.9.5.24-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setMilliseconds(ms)");


addTestCase(this, 0, -2208988800000 );

//test();

function addTestCase(obj, startms, newms ) {

var DateCase = new Date( startms );
DateCase.setMilliseconds( newms );
var DateString = "var date = new Date("+ startms +"); date.setMilliseconds("+ newms +"); date";
var UTCDate = UTCDateFromTime(obj, Number(newms) );
var LocalDate = LocalDateFromTime(obj, Number(newms) );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,    DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,   DateCase.getUTCDate() );

obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );

obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}

function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}

},

/**
File Name:          15_9_5_24__4.js
ECMA Section:       15.9.5.24 Date.prototype.setTime(time)
Description:
1.      If the this value is not a Date object, generate a runtime error.
2.      Call ToNumber(time).
3.      Call TimeClip(Result(1)).
4.      Set the [[Value]] property of the this value to Result(2).
5.      Return the value of the [[Value]] property of the this value.
Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_24__4:function(){
var TITLE = "Date.prototype.setTime"
var SECTION = "15.9.5.24-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setMilliseconds(ms)");


addTestCase(this, 0, 946684800000 );

//test();

function addTestCase(obj, startms, newms ) {

var DateCase = new Date( startms );
DateCase.setMilliseconds( newms );
var DateString = "var date = new Date("+ startms +"); date.setMilliseconds("+ newms +"); date";
var UTCDate = UTCDateFromTime(obj, Number(newms) );
var LocalDate = LocalDateFromTime(obj, Number(newms) );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,    DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,   DateCase.getUTCDate() );

obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );

obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}

function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}

},

/**
File Name:          15_9_5_24__5.js
ECMA Section:       15.9.5.24 Date.prototype.setTime(time)
Description:
1.      If the this value is not a Date object, generate a runtime error.
2.      Call ToNumber(time).
3.      Call TimeClip(Result(1)).
4.      Set the [[Value]] property of the this value to Result(2).
5.      Return the value of the [[Value]] property of the this value.
Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_24__5:function(){
var TITLE = "Date.prototype.setTime"
var SECTION = "15.9.5.24-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setMilliseconds(ms)");


addTestCase(this, 0, "0" );

//test();

function addTestCase(obj, startms, newms ) {

var DateCase = new Date( startms );
DateCase.setMilliseconds( newms );
var DateString = "var date = new Date("+ startms +"); date.setMilliseconds("+ newms +"); date";
var UTCDate = UTCDateFromTime(obj, Number(newms) );
var LocalDate = LocalDateFromTime(obj, Number(newms) );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,    DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,   DateCase.getUTCDate() );

obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );

obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}

function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}

},

/**
File Name:          15_9_5_24__6.js
ECMA Section:       15.9.5.24 Date.prototype.setTime(time)
Description:
1.      If the this value is not a Date object, generate a runtime error.
2.      Call ToNumber(time).
3.      Call TimeClip(Result(1)).
4.      Set the [[Value]] property of the this value to Result(2).
5.      Return the value of the [[Value]] property of the this value.
Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_24__6:function(){
var TITLE = "Date.prototype.setTime"
var SECTION = "15.9.5.24-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setMilliseconds(ms)");


addTestCase(this, 0, "-2208988800000" );

//test();

function addTestCase(obj, startms, newms ) {

var DateCase = new Date( startms );
DateCase.setMilliseconds( newms );
var DateString = "var date = new Date("+ startms +"); date.setMilliseconds("+ newms +"); date";
var UTCDate = UTCDateFromTime(obj, Number(newms) );
var LocalDate = LocalDateFromTime(obj, Number(newms) );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,    DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,   DateCase.getUTCDate() );

obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );

obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}

function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}

},

/**
File Name:          15_9_5_24__7.js
ECMA Section:       15.9.5.24 Date.prototype.setTime(time)
Description:
1.      If the this value is not a Date object, generate a runtime error.
2.      Call ToNumber(time).
3.      Call TimeClip(Result(1)).
4.      Set the [[Value]] property of the this value to Result(2).
5.      Return the value of the [[Value]] property of the this value.
Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_24__7:function(){
var TITLE = "Date.prototype.setTime"
var SECTION = "15.9.5.24-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setMilliseconds(ms)");


addTestCase(this, 0, "-86400000" );

//test();

function addTestCase(obj, startms, newms ) {

var DateCase = new Date( startms );
DateCase.setMilliseconds( newms );
var DateString = "var date = new Date("+ startms +"); date.setMilliseconds("+ newms +"); date";
var UTCDate = UTCDateFromTime(obj, Number(newms) );
var LocalDate = LocalDateFromTime(obj, Number(newms) );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,    DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,   DateCase.getUTCDate() );

obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );

obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}

function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}

},

/**
File Name:          15_9_5_24__8.js
ECMA Section:       15.9.5.24 Date.prototype.setTime(time)
Description:
1.      If the this value is not a Date object, generate a runtime error.
2.      Call ToNumber(time).
3.      Call TimeClip(Result(1)).
4.      Set the [[Value]] property of the this value to Result(2).
5.      Return the value of the [[Value]] property of the this value.
Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_24__8:function(){
var TITLE = "Date.prototype.setTime"
var SECTION = "15.9.5.24-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setMilliseconds(ms)");

addTestCase(this, 0, "946684800000" );

//test();

function addTestCase(obj, startms, newms ) {

var DateCase = new Date( startms );
DateCase.setMilliseconds( newms );
var DateString = "var date = new Date("+ startms +"); date.setMilliseconds("+ newms +"); date";
var UTCDate = UTCDateFromTime(obj, Number(newms) );
var LocalDate = LocalDateFromTime(obj, Number(newms) );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,    DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,   DateCase.getUTCDate() );

obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );

obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}

function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}

},

/**
File Name:          15_9_5_25__1.js
ECMA Section:       15.9.5.25 Date.prototype.setUTCMilliseconds(ms)
Description:
1.  Let t be this time value.
2.  Call ToNumber(ms).
3.  Compute MakeTime(obj.HourFromTime(t), obj.MinFromTime(t), obj.SecFromTime(t), Result(2)).
4.  Compute this.MakeDate(Day(t), Result(3)).
5.  Set the [[Value]] property of the this value to TimeClip(Result(4)).
6.  Return the value of the [[Value]] property of the this value.
Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_25__1:function(){
var SECTION = "15.9.5.25-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setUTCMilliseconds(ms)");

addNewTestCase(this, 0, 0, "TDATE = new Date(0);(TDATE).setUTCMilliseconds(0);TDATE",
UTCDateFromTime(this,setUTCMilliseconds(this,0,0)),
LocalDateFromTime(this,setUTCMilliseconds(this,0,0)) );
addNewTestCase(this, 28800000,999,
"TDATE = new Date(28800000);(TDATE).setUTCMilliseconds(999);TDATE",
UTCDateFromTime(this,setUTCMilliseconds(this,28800000,999)),
LocalDateFromTime(this,setUTCMilliseconds(this,28800000,999)) );
addNewTestCase(this, 28800000,-28800000,
"TDATE = new Date(28800000);(TDATE).setUTCMilliseconds(-28800000);TDATE",
UTCDateFromTime(this,setUTCMilliseconds(this,28800000,-28800000)),
LocalDateFromTime(this,setUTCMilliseconds(this,28800000,-28800000)) );
addNewTestCase(this, 946684800000,1234567,
"TDATE = new Date(946684800000);(TDATE).setUTCMilliseconds(this,1234567);TDATE",
UTCDateFromTime(this,setUTCMilliseconds(this,946684800000,1234567)),
LocalDateFromTime(this,setUTCMilliseconds(this,946684800000,1234567)) );
addNewTestCase(this, 946684800000, 123456789,
"TDATE = new Date(946684800000);(TDATE).setUTCMilliseconds(this,123456789);TDATE",
UTCDateFromTime(this,setUTCMilliseconds(this,946684800000,123456789)),
LocalDateFromTime(this,setUTCMilliseconds(this,946684800000,123456789)) );

addNewTestCase(this, -2208988800000,123456789,
"TDATE = new Date(-2208988800000);(TDATE).setUTCMilliseconds(this,123456789);TDATE",
UTCDateFromTime(this,setUTCMilliseconds(this,-2208988800000,123456789)),
LocalDateFromTime(this,setUTCMilliseconds(this,-2208988800000,123456789)) );

addNewTestCase(this, -2208988800000,123456,
"TDATE = new Date(-2208988800000);(TDATE).setUTCMilliseconds(this,123456);TDATE",
UTCDateFromTime(this,setUTCMilliseconds(this,-2208988800000,123456)),
LocalDateFromTime(this,setUTCMilliseconds(this,-2208988800000,123456)) );

addNewTestCase(this, -2208988800000,-123456,
"TDATE = new Date(-2208988800000);(TDATE).setUTCMilliseconds(this,-123456);TDATE",
UTCDateFromTime(this,setUTCMilliseconds(this,-2208988800000,-123456)),
LocalDateFromTime(this,setUTCMilliseconds(this,-2208988800000,-123456)) );

addNewTestCase(this, 0,-999,
"TDATE = new Date(0);(TDATE).setUTCMilliseconds(this,-999);TDATE",
UTCDateFromTime(this,setUTCMilliseconds(this,0,-999)),
LocalDateFromTime(this,setUTCMilliseconds(this,0,-999)) );

//test();

function addNewTestCase(obj, initialTime, ms, DateString, UTCDate, LocalDate) {
DateCase = new Date(initialTime);
DateCase.setUTCMilliseconds(ms);

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,    DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,   DateCase.getUTCDate() );

obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );

obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}
function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}

function SetUTCMilliseconds(obj, T, MS ) {
T = Number( T );
TIME = obj.MakeTime(    obj.HourFromTime(T),
obj.MinFromTime(T),
obj.SecFromTime(T),
MS );
return( obj.MakeDate( Day(T), TIME ));
}

},

/**
File Name:          15_9_5_26__1.js
ECMA Section:       15.9.5.26 Date.prototype.setSeconds(sec [,ms])
Description:

If ms is not specified, this behaves as if ms were specified with the
value getMilliseconds( ).

1.  Let t be the result of obj.LocalTime(this time value).
2.  Call ToNumber(sec).
3.  If ms is not specified, compute obj.msFromTime(t); otherwise, call
ToNumber(ms).
4.  Compute obj.MakeTime(obj.HourFromTime(t), obj.MinFromTime(t), Result(2),
Result(3)).
5.  Compute UTC(obj.MakeDate(Day(t), Result(4))).
6.  Set the [[Value]] property of the this value to TimeClip(Result(5)).
7.  Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_26__1:function(){
var SECTION = "15.9.5.26-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setSeconds(sec [,ms] )");

addNewTestCase(this, 0, 0, 0,
"TDATE = new Date(0);(TDATE).setSeconds(0,0);TDATE",
UTCDateFromTime(this,SetSeconds(this,0,0,0)),
LocalDateFromTime(this,SetSeconds(this,0,0,0)) );

addNewTestCase(this, 28800000,59,999,
"TDATE = new Date(28800000);(TDATE).setSeconds(59,999);TDATE",
UTCDateFromTime(this,SetSeconds(this,28800000,59,999)),
LocalDateFromTime(this,SetSeconds(this,28800000,59,999)) );

addNewTestCase(this, 28800000,999,999,
"TDATE = new Date(28800000);(TDATE).setSeconds(999,999);TDATE",
UTCDateFromTime(this,SetSeconds(this,28800000,999,999)),
LocalDateFromTime(this,SetSeconds(this,28800000,999,999)) );

addNewTestCase(this, 28800000,999, void 0,
"TDATE = new Date(28800000);(TDATE).setSeconds(999);TDATE",
UTCDateFromTime(this,SetSeconds(this,28800000,999,0)),
LocalDateFromTime(this,SetSeconds(this,28800000,999,0)) );

addNewTestCase(this, 28800000,-28800, void 0,
"TDATE = new Date(28800000);(TDATE).setSeconds(-28800);TDATE",
UTCDateFromTime(this,SetSeconds(28800000,-28800)),
LocalDateFromTime(this,SetSeconds(28800000,-28800)) );

addNewTestCase(this, 946684800000,1234567,void 0,
"TDATE = new Date(946684800000);(TDATE).setSeconds(1234567);TDATE",
UTCDateFromTime(this,SetSeconds(946684800000,1234567)),
LocalDateFromTime(this,SetSeconds(946684800000,1234567)) );

addNewTestCase(this, -2208988800000,59,999,
"TDATE = new Date(-2208988800000);(TDATE).setSeconds(59,999);TDATE",
UTCDateFromTime(this,SetSeconds(this,-2208988800000,59,999)),
LocalDateFromTime(this,SetSeconds(this,-2208988800000,59,999)) );

//test();

function addNewTestCase(obj, startTime, sec, ms, DateString,UTCDate, LocalDate) {
DateCase = new Date( startTime );
if ( ms != void 0 ) {
DateCase.setSeconds( sec, ms );
} else {
DateCase.setSeconds( sec );
}

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,    DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,   DateCase.getUTCDate() );

obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );

obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}

function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}
function SetSeconds(obj, t, s, m ) {
var MS   = ( m == void 0 ) ? obj.msFromTime(t) : Number( m );
var TIME = obj.LocalTime( t );
var SEC  = Number(s);
var RESULT4 = obj.MakeTime( obj.HourFromTime( TIME ),
obj.MinFromTime( TIME ),
SEC,
MS );
var UTC_TIME = obj.UTC(obj.MakeDate(obj.Day(TIME), RESULT4));
return ( obj.TimeClip(UTC_TIME) );
}

},

/**
File Name:          15_9_5_27__1.js
ECMA Section:       15.9.5.27 Date.prototype.setUTCSeconds(sec [,ms])
Description:

If ms is not specified, this behaves as if ms were specified with the
value getUTCMilliseconds( ).

1.  Let t be this time value.
2.  Call ToNumber(sec).
3.  If ms is not specified, compute obj.msFromTime(t); otherwise, call
ToNumber(ms)
4.  Compute obj.MakeTime(obj.HourFromTime(t), obj.MinFromTime(t), Result(2), Result(3))
5.  Compute obj.MakeDate(Day(t), Result(4)).
6.  Set the [[Value]] property of the this value to obj.TimeClip(Result(5)).
7.  Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_27__1:function(){
var SECTION = "15.9.5.27-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setUTCSeconds(sec [,ms] )");

addNewTestCase(this, 0, 0, 0, "TDATE = new Date(0);(TDATE).setUTCSeconds(0,0);TDATE",
UTCDateFromTime(this,SetUTCSeconds(this,0,0,0)),
LocalDateFromTime(this,SetUTCSeconds(this,0,0,0)) );

addNewTestCase(this, 28800000,59,999,
"TDATE = new Date(28800000);(TDATE).setUTCSeconds(59,999);TDATE",
UTCDateFromTime(this,SetUTCSeconds(this,28800000,59,999)),
LocalDateFromTime(this,SetUTCSeconds(this,28800000,59,999)) );

addNewTestCase(this, 28800000,999,999,
"TDATE = new Date(28800000);(TDATE).setUTCSeconds(999,999);TDATE",
UTCDateFromTime(this,SetUTCSeconds(this,28800000,999,999)),
LocalDateFromTime(this,SetUTCSeconds(this,28800000,999,999)) );

addNewTestCase(this, 28800000, 999, void 0,
"TDATE = new Date(28800000);(TDATE).setUTCSeconds(999);TDATE",
UTCDateFromTime(this,SetUTCSeconds(this,28800000,999,0)),
LocalDateFromTime(this,SetUTCSeconds(this,28800000,999,0)) );

addNewTestCase(this, 28800000, -28800, void 0,
"TDATE = new Date(28800000);(TDATE).setUTCSeconds(-28800);TDATE",
UTCDateFromTime(this,SetUTCSeconds(28800000,-28800)),
LocalDateFromTime(this,SetUTCSeconds(28800000,-28800)) );

addNewTestCase(this, 946684800000, 1234567, void 0,
"TDATE = new Date(946684800000);(TDATE).setUTCSeconds(1234567);TDATE",
UTCDateFromTime(this,SetUTCSeconds(946684800000,1234567)),
LocalDateFromTime(this,SetUTCSeconds(946684800000,1234567)) );

addNewTestCase(this, -2208988800000,59,999,
"TDATE = new Date(-2208988800000);(TDATE).setUTCSeconds(59,999);TDATE",
UTCDateFromTime(this,SetUTCSeconds(this,-2208988800000,59,999)),
LocalDateFromTime(this,SetUTCSeconds(this,-2208988800000,59,999)) );

//test();

function addNewTestCase(obj, startTime, sec, ms, DateString, UTCDate, LocalDate) {
DateCase = new Date( startTime );
if ( ms == void 0) {
DateCase.setSeconds( sec );
} else {
DateCase.setSeconds( sec, ms );
}

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,    DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,   DateCase.getUTCDate() );

obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );

obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}

function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}

function SetUTCSeconds(obj, t, s, m ) {
var TIME = t;
var SEC  = Number(s);
var MS   = ( m == void 0 ) ? obj.msFromTime(TIME) : Number( m );
var RESULT4 = obj.MakeTime( obj.HourFromTime( TIME ),
obj.MinFromTime( TIME ),
SEC,
MS );
return ( obj.TimeClip(obj.MakeDate(obj.Day(TIME), RESULT4)) );
}

},

/**
File Name:          15_9_5_28__1.js
ECMA Section:       15.9.5.28 Date.prototype.setMinutes(min [, sec [, ms ]] )
Description:
If sec is not specified, this behaves as if sec were specified with the
value getSeconds ( ).

If ms is not specified, this behaves as if ms were specified with the
value getMilliseconds( ).

1.  Let t be the result of obj.LocalTime(this time value).
2.  Call ToNumber(min).
3.  If sec is not specified, compute obj.SecFromTime(t); otherwise, call ToNumber(sec).
4.  If ms is not specified, compute obj.msFromTime(t); otherwise, call ToNumber(ms).
5.  Compute obj.MakeTime(obj.HourFromTime(t), Result(2), Result(3), Result(4)).
6.  Compute UTC(obj.MakeDate(Day(t), Result(5))).
7.  Set the [[Value]] property of the this value to obj.TimeClip(Result(6)).
8.  Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_28__1:function(){
var SECTION = "15.9.5.28-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setMinutes(sec [,ms] )");

addNewTestCase(this, 0, 0, void 0, void 0,
"TDATE = new Date(0);(TDATE).setMinutes(0);TDATE",
UTCDateFromTime(this,SetMinutes(this,0,0,0,0)),
LocalDateFromTime(this,SetMinutes(this,0,0,0,0)) );

addNewTestCase(this, 28800000, 59, 59, void 0,
"TDATE = new Date(28800000);(TDATE).setMinutes(59,59);TDATE",
UTCDateFromTime(this,SetMinutes(this,28800000,59,59)),
LocalDateFromTime(this,SetMinutes(this,28800000,59,59)) );

addNewTestCase(this, 28800000, 59, 59, 999,
"TDATE = new Date(28800000);(TDATE).setMinutes(59,59,999);TDATE",
UTCDateFromTime(this,SetMinutes(this,28800000,59,59,999)),
LocalDateFromTime(this,SetMinutes(this,28800000,59,59,999)) );

addNewTestCase(this, 28800000, 59, void 0, void 0,
"TDATE = new Date(28800000);(TDATE).setMinutes(59);TDATE",
UTCDateFromTime(this,SetMinutes(this,28800000,59,0)),
LocalDateFromTime(this,SetMinutes(this,28800000,59,0)) );

addNewTestCase(this, 28800000, -480, void 0, void 0,
"TDATE = new Date(28800000);(TDATE).setMinutes(-480);TDATE",
UTCDateFromTime(this,SetMinutes(this,28800000,-480)),
LocalDateFromTime(this,SetMinutes(this,28800000,-480)) );

addNewTestCase(this, 946684800000, 1234567, void 0, void 0,
"TDATE = new Date(946684800000);(TDATE).setMinutes(1234567);TDATE",
UTCDateFromTime(this,SetMinutes(this,946684800000,1234567)),
LocalDateFromTime(this,SetMinutes(this,946684800000,1234567)) );

addNewTestCase(this, -2208988800000,59, 59, void 0,
"TDATE = new Date(-2208988800000);(TDATE).setMinutes(59,59);TDATE",
UTCDateFromTime(this,SetMinutes(this,-2208988800000,59,59)),
LocalDateFromTime(this,SetMinutes(this,-2208988800000,59,59)) );

addNewTestCase(this, -2208988800000, 59, 59, 999,
"TDATE = new Date(-2208988800000);(TDATE).setMinutes(59,59,999);TDATE",
UTCDateFromTime(this,SetMinutes(this,-2208988800000,59,59,999)),
LocalDateFromTime(this,SetMinutes(this,-2208988800000,59,59,999)) );

//test();

function addNewTestCase(obj, time, min, sec, ms, DateString, UTCDate, LocalDate) {
DateCase = new Date( time );

if ( sec == void 0 ) {
DateCase.setMinutes( min );
} else {
if ( ms == void 0 ) {
DateCase.setMinutes( min, sec );
} else {
DateCase.setMinutes( min, sec, ms );
}
}

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,    DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,   DateCase.getUTCDate() );

obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );

obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}

function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}

function SetMinutes(obj, t, min, sec, ms ) {
var TIME = obj.LocalTime(t);
var MIN =  Number(min);
var SEC  = ( sec == void 0) ? obj.SecFromTime(TIME) : Number(sec);
var MS   = ( ms == void 0 ) ? obj.msFromTime(TIME)  : Number(ms);
var RESULT5 = obj.MakeTime( obj.HourFromTime( TIME ),
MIN,
SEC,
MS );
return ( obj.TimeClip(obj.UTC( obj.MakeDate(obj.Day(TIME),RESULT5))) );
}

},

/**
File Name:          15_9_5_29__1.js
ECMA Section:       15.9.5.29 Date.prototype.setUTCMinutes(min [, sec [, ms ]] )
Description:
If sec is not specified, this behaves as if sec were specified with the
value getUTCSeconds ( ).

If ms is not specified, this behaves as if ms were specified with the value
getUTCMilliseconds( ).

1.  Let t be this time value.
2.  Call ToNumber(min).
3.  If sec is not specified, compute obj.SecFromTime(t); otherwise, call
ToNumber(sec).
4.  If ms is not specified, compute obj.msFromTime(t); otherwise, call
ToNumber(ms).
5.  Compute obj.MakeTime(obj.HourFromTime(t), Result(2), Result(3), Result(4)).
6.  Compute obj.MakeDate(Day(t), Result(5)).
7.  Set the [[Value]] property of the this value to obj.TimeClip(Result(6)).
8.  Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_29__1:function(){
var SECTION = "15.9.5.29-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setUTCMinutes( min [, sec, ms] )");

addNewTestCase(this, 0, 0, void 0, void 0,
"TDATE = new Date(0);(TDATE).setUTCMinutes(0);TDATE",
UTCDateFromTime(this,SetUTCMinutes(this,0,0,0,0)),
LocalDateFromTime(this,SetUTCMinutes(this,0,0,0,0)) );

addNewTestCase(this, 28800000, 59, 59, void 0,
"TDATE = new Date(28800000);(TDATE).setUTCMinutes(59,59);TDATE",
UTCDateFromTime(this,SetUTCMinutes(this,28800000,59,59)),
LocalDateFromTime(this,SetUTCMinutes(this,28800000,59,59)) );

addNewTestCase(this, 28800000, 59, 59, 999,
"TDATE = new Date(28800000);(TDATE).setUTCMinutes(59,59,999);TDATE",
UTCDateFromTime(this,SetUTCMinutes(this,28800000,59,59,999)),
LocalDateFromTime(this,SetUTCMinutes(this,28800000,59,59,999)) );

addNewTestCase(this, 28800000, 59, void 0, void 0,
"TDATE = new Date(28800000);(TDATE).setUTCMinutes(59);TDATE",
UTCDateFromTime(this,SetUTCMinutes(this,28800000,59)),
LocalDateFromTime(this,SetUTCMinutes(this,28800000,59)) );

addNewTestCase(this, 28800000, -480, 0, 0,
"TDATE = new Date(28800000);(TDATE).setUTCMinutes(-480);TDATE",
UTCDateFromTime(this,SetUTCMinutes(this,28800000,-480)),
LocalDateFromTime(this,SetUTCMinutes(this,28800000,-480)) );

addNewTestCase(this, 946684800000, 1234567, void 0, void 0,
"TDATE = new Date(946684800000);(TDATE).setUTCMinutes(1234567);TDATE",
UTCDateFromTime(this,SetUTCMinutes(this,946684800000,1234567)),
LocalDateFromTime(this,SetUTCMinutes(this,946684800000,1234567)) );

addNewTestCase(this, -2208988800000, 59, 999, void 0,
"TDATE = new Date(-2208988800000);(TDATE).setUTCMinutes(59,999);TDATE",
UTCDateFromTime(this,SetUTCMinutes(this,-2208988800000,59,999)),
LocalDateFromTime(this,SetUTCMinutes(this,-2208988800000,59,999)) );

//test();

function addNewTestCase(obj, time, min, sec, ms, DateString, UTCDate, LocalDate) {
var DateCase = new Date( time );

if ( sec == void 0 ) {
DateCase.setUTCMinutes( min );
} else {
if ( ms == void 0 ) {
DateCase.setUTCMinutes( min, sec );
} else {
DateCase.setUTCMinutes( min, sec, ms );
}
}

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,    DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,   DateCase.getUTCDate() );

obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );

obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}
function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}
function SetUTCMinutes(obj, t, min, sec, ms ) {
var TIME = t;
var MIN =  Number(min);
var SEC  = ( sec == void 0) ? obj.SecFromTime(TIME) : Number(sec);
var MS   = ( ms == void 0 ) ? obj.msFromTime(TIME)  : Number(ms);
var RESULT5 = obj.MakeTime( obj.HourFromTime( TIME ),
MIN,
SEC,
MS );
return ( obj.TimeClip(obj.MakeDate(obj.Day(TIME),RESULT5)) );
}

},

/**
File Name:          15_9_5_3__1__n.js
ECMA Section:       15.9.5.3-1 Date.prototype.valueOf
Description:

The valueOf function returns a number, which is this time value.

The valueOf function is not generic; it generates a runtime error if
its this value is not a Date object.  Therefore it cannot be transferred
to other kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_3__1__n:function(){
var SECTION = "15.9.5.3-1-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.valueOf";

//writeHeaderToLog( SECTION + " "+ TITLE);

var OBJ = new MyObject( new Date(0) );

DESCRIPTION = "var OBJ = new MyObject( new Date(0) ); OBJ.valueOf()";
EXPECTED = "error";

this.TestCase( SECTION,
"var OBJ = new MyObject( new Date(0) ); OBJ.valueOf()",
"error",
eval("OBJ.valueOf()") );
//test();

function MyObject( value ) {
this.value = value;
this.valueOf = Date.prototype.valueOf;
//  The following line causes an infinte loop
//    this.toString = new Function( "return this+\"\";");
return this;
}

},

/**
File Name:          15_9_5_3__2.js
ECMA Section:       15.9.5.3-2 Date.prototype.valueOf
Description:

The valueOf function returns a number, which is this time value.

The valueOf function is not generic; it generates a runtime error if
its this value is not a Date object.  Therefore it cannot be transferred
to other kinds of objects for use as a method.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_3__2:function(){
var SECTION = "15.9.5.3-2";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.valueOf";
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_NOW );
addTestCase(this, this.TIME_1970 );
addTestCase(this, this.TIME_1900 );
addTestCase(this, this.TIME_2000 );
addTestCase(this, this.UTC_FEB_29_2000 );
addTestCase(this, UTC_JAN_1_2005 );

//test();

function addTestCase(obj, t ) {
obj.TestCase( SECTION,
"(new Date("+t+").valueOf()",
t,
(new Date(t)).valueOf() );

obj.TestCase( SECTION,
"(new Date("+(t+1)+").valueOf()",
t+1,
(new Date(t+1)).valueOf() );

obj.TestCase( SECTION,
"(new Date("+(t-1)+").valueOf()",
t-1,
(new Date(t-1)).valueOf() );

obj.TestCase( SECTION,
"(new Date("+(t-obj.TZ_ADJUST)+").valueOf()",
t-obj.TZ_ADJUST,
(new Date(t-obj.TZ_ADJUST)).valueOf() );

obj.TestCase( SECTION,
"(new Date("+(t+obj.TZ_ADJUST)+").valueOf()",
t+obj.TZ_ADJUST,
(new Date(t+obj.TZ_ADJUST)).valueOf() );
}

function MyObject( value ) {
this.value = value;
this.valueOf = Date.prototype.valueOf;
this.toString = new Function( "return this+\"\";");
return this;
}

},

/**
File Name:          15_9_5_30__1.js
ECMA Section:       15.9.5.30 Date.prototype.setHours(hour [, min [, sec [, ms ]]] )
Description:
If min is not specified, this behaves as if min were specified with the
value getMinutes( ). If sec is not specified, this behaves as if sec were
specified with the value getSeconds ( ). If ms is not specified, this
behaves as if ms were specified with the value getMilliseconds( ).

1.  Let t be the result of obj.LocalTime(this time value).
2.  Call ToNumber(hour).
3.  If min is not specified, compute obj.MinFromTime(t); otherwise, call
ToNumber(min).
4.  If sec is not specified, compute obj.SecFromTime(t); otherwise, call
ToNumber(sec).
5.  If ms is not specified, compute obj.msFromTime(t); otherwise, call
ToNumber(ms).
6.  Compute obj.MakeTime(Result(2), Result(3), Result(4), Result(5)).
7.  Compute UTC(obj.MakeDate(Day(t), Result(6))).
8.  Set the [[Value]] property of the this value to obj.TimeClip(Result(7)).
9.  Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_30__1:function(){
var SECTION = "15.9.5.30-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setHours( hour [, min, sec, ms] )");

addNewTestCase(this, 0,0,0,0,void 0,
"TDATE = new Date(0);(TDATE).setHours(0);TDATE" );

addNewTestCase(this, 28800000, 23, 59, 999,void 0,
"TDATE = new Date(28800000);(TDATE).setHours(23,59,999);TDATE" );

addNewTestCase(this, 28800000, 999, 999, void 0, void 0,
"TDATE = new Date(28800000);(TDATE).setHours(999,999);TDATE" );

addNewTestCase(this, 28800000,999,0, void 0, void 0,
"TDATE = new Date(28800000);(TDATE).setHours(999);TDATE" );

addNewTestCase(this, 28800000,-8, void 0, void 0, void 0,
"TDATE = new Date(28800000);(TDATE).setHours(-8);TDATE" );

addNewTestCase(this, 946684800000,8760, void 0, void 0, void 0,
"TDATE = new Date(946684800000);(TDATE).setHours(8760);TDATE" );

addNewTestCase(this, this.TIME_2000 - this.msPerDay, 23, 59, 59, 999,
"d = new Date( " + (this.TIME_2000-this.msPerDay) +"); d.setHours(23,59,59,999)" );

addNewTestCase(this, this.TIME_2000 - this.msPerDay, 23, 59, 59, 1000,
"d = new Date( " + (this.TIME_2000-this.msPerDay) +"); d.setHours(23,59,59,1000)" );

//test();

function addNewTestCase(obj, time, hours, min, sec, ms, DateString) {
var UTCDate =   UTCDateFromTime(obj, SetHours(obj, time, hours, min, sec, ms ));
var LocalDate = LocalDateFromTime(obj, SetHours(obj, time, hours, min, sec, ms ));

var DateCase = new Date( time );

if ( min == void 0 ) {
DateCase.setHours( hours );
} else {
if ( sec == void 0 ) {
DateCase.setHours( hours, min );
} else {
if ( ms == void 0 ) {
DateCase.setHours( hours, min, sec );
} else {
DateCase.setHours( hours, min, sec, ms );
}
}
}


obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,    DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           UTCDate.day,    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              LocalDate.day,        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}

function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.day = obj.WeekDay( t );
d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );

return (d);
}
function SetHours(obj, t, hour, min, sec, ms ) {
var TIME = obj.LocalTime(t);
var HOUR = Number(hour);
var MIN =  ( min == void 0) ? obj.MinFromTime(TIME) : Number(min);
var SEC  = ( sec == void 0) ? obj.SecFromTime(TIME) : Number(sec);
var MS   = ( ms == void 0 ) ? obj.msFromTime(TIME)  : Number(ms);
var RESULT6 = obj.MakeTime( HOUR,
MIN,
SEC,
MS );
var UTC_TIME = obj.UTC(  obj.MakeDate(obj.Day(TIME), RESULT6) );
return ( obj.TimeClip(UTC_TIME) );
}

},

/**
File Name:          15_9_5_31__1.js
ECMA Section:
15.9.5.31 Date.prototype.setUTCHours(hour [, min [, sec [, ms ]]] )

Description:

If min is not specified, this behaves as if min were specified with
the value getUTCMinutes( ).  If sec is not specified, this behaves
as if sec were specified with the value getUTCSeconds ( ).  If ms
is not specified, this behaves as if ms were specified with the
value getUTCMilliseconds( ).

1.Let t be this time value.
2.Call ToNumber(hour).
3.If min is not specified, compute obj.MinFromTime(t);
otherwise, call ToNumber(min).
4.If sec is not specified, compute obj.SecFromTime(t);
otherwise, call ToNumber(sec).
5.If ms is not specified, compute obj.msFromTime(t);
otherwise, call ToNumber(ms).
6.Compute obj.MakeTime(Result(2), Result(3), Result(4), Result(5)).
7.Compute obj.MakeDate(Day(t), Result(6)).
8.Set the [[Value]] property of the this value to obj.TimeClip(Result(7)).

1.Return the value of the [[Value]] property of the this value.
Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_31__1:function(){
var SECTION = "15.9.5.31-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog(SECTION +
//                 " Date.prototype.setUTCHours(hour [, min [, sec [, ms ]]] )");

addNewTestCase(this, 0, 0, void 0, void 0, void 0,
"TDATE = new Date(0);(TDATE).setUTCHours(0);TDATE",
UTCDateFromTime(this,SetUTCHours(this,0,0,0,0)),
LocalDateFromTime(this,SetUTCHours(this,0,0,0,0)) );

addNewTestCase(this, 28800000, 23, 59, 999, void 0,
"TDATE = new Date(28800000);(TDATE).setUTCHours(23,59,999);TDATE",
UTCDateFromTime(this,SetUTCHours(this,28800000,23,59,999)),
LocalDateFromTime(this,SetUTCHours(this,28800000,23,59,999)) );

addNewTestCase(this, 28800000,999,999, void 0, void 0,
"TDATE = new Date(28800000);(TDATE).setUTCHours(999,999);TDATE",
UTCDateFromTime(this,SetUTCHours(this,28800000,999,999)),
LocalDateFromTime(this,SetUTCHours(this,28800000,999,999)) );

addNewTestCase(this, 28800000, 999, void 0, void 0, void 0,
"TDATE = new Date(28800000);(TDATE).setUTCHours(999);TDATE",
UTCDateFromTime(this,SetUTCHours(this,28800000,999,0)),
LocalDateFromTime(this,SetUTCHours(this,28800000,999,0)) );

addNewTestCase(this, 28800000, -8670, void 0, void 0, void 0,
"TDATE = new Date(28800000);(TDATE).setUTCHours(-8670);TDATE",
UTCDateFromTime(this,SetUTCHours(this,28800000,-8670)),
LocalDateFromTime(this,SetUTCHours(this,28800000,-8670)) );

// modify hours to remove dst ambiguity
addNewTestCase(this, 946684800000, 1235567, void 0, void 0, void 0,
"TDATE = new Date(946684800000);(TDATE).setUTCHours(1235567);TDATE",
UTCDateFromTime(this,SetUTCHours(this,946684800000,1235567)),
LocalDateFromTime(this,SetUTCHours(this,946684800000,1235567)) );

addNewTestCase(this, -2208988800000, 59, 999, void 0, void 0,
"TDATE = new Date(-2208988800000);(TDATE).setUTCHours(59,999);TDATE",
UTCDateFromTime(this,SetUTCHours(this,-2208988800000,59,999)),
LocalDateFromTime(this,SetUTCHours(this,-2208988800000,59,999)) );

//test();

function addNewTestCase(obj, time, hours, min, sec, ms, DateString, UTCDate, LocalDate) {

DateCase = new Date(time);
if ( min == void 0 ) {
DateCase.setUTCHours( hours );
} else {
if ( sec == void 0 ) {
DateCase.setUTCHours( hours, min );
} else {
if ( ms == void 0 ) {
DateCase.setUTCHours( hours, min, sec );
} else {
DateCase.setUTCHours( hours, min, sec, ms );
}
}
}

obj.TestCase( SECTION, DateString+".getTime()",            UTCDate.value,
DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",            UTCDate.value,
DateCase.valueOf() );
obj.TestCase( SECTION, DateString+".getUTCFullYear()",     UTCDate.year,
DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",        UTCDate.month,
DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",         UTCDate.date,
DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",          UTCDate.day,
DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",        UTCDate.hours,
DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",      UTCDate.minutes,
DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",      UTCDate.seconds,
DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()", UTCDate.ms,
DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",        LocalDate.year,
DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",           LocalDate.month,
DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",            LocalDate.date,
DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",             LocalDate.day,
DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",           LocalDate.hours,
DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",         LocalDate.minutes,
DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",         LocalDate.seconds,
DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",    LocalDate.ms,
DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;" +
DateString+".toString()",
"[object Date]",
DateCase.toString() );
}
function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyoDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}
function SetUTCHours(obj, t, hour, min, sec, ms ) {
var TIME = t;
var HOUR = Number(hour);
var MIN =  ( min == void 0) ? obj.MinFromTime(TIME) : Number(min);
var SEC  = ( sec == void 0) ? obj.SecFromTime(TIME) : Number(sec);
var MS   = ( ms == void 0 ) ? obj.msFromTime(TIME)  : Number(ms);
var RESULT6 = obj.MakeTime( HOUR,
MIN,
SEC,
MS );
return ( obj.TimeClip(obj.MakeDate(obj.Day(TIME), RESULT6)) );
}

},

/**
File Name:          15_9_5_32__1.js
ECMA Section:       15.9.5.32 Date.prototype.setDate(date)
Description:
1.  Let t be the result of obj.LocalTime(this time value).
2.  Call ToNumber(date).
3.  Compute obj.MakeDay(obj.YearFromTime(t), obj.MonthFromTime(t), Result(2)).
4.  Compute UTC(obj.MakeDate(Result(3), TimeWithinDay(t))).
5.  Set the [[Value]] property of the this value to obj.TimeClip(Result(4)).
6.  Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_32__1:function(){
var SECTION = "15.9.5.32-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setDate(date) ");

addNewTestCase(this, 0, 1,
"TDATE = new Date(0);(TDATE).setDate(1);TDATE" );

//test();

function addNewTestCase(obj, t, d, DateString ) {
var DateCase = new Date( t );
DateCase.setDate( d );

var UTCDate = UTCDateFromTime(obj,SetDate(obj, t, d));
var LocalDate=LocalDateFromTime(obj,SetDate(obj, t,d));


obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,    DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           UTCDate.day,    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              LocalDate.day,        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}
function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}

function SetDate(obj, t, date ) {
var T       = obj.LocalTime( t );
var DATE    = Number( date );
var RESULT3 = obj.MakeDay(obj.YearFromTime(T), obj.MonthFromTime(T), DATE );
var UTC_DATE = obj.UTC( obj.MakeDate(RESULT3, obj.TimeWithinDay(T)) );
return ( obj.TimeClip(UTC_DATE) );
}

},

/**
File Name:          15_9_5_33__1.js
ECMA Section:       15.9.5.33 Date.prototype.setUTCDate(date)
Description:
1.  Let t be this time value.
2.  Call ToNumber(date).
3.  Compute obj.MakeDay(obj.YearFromTime(t), obj.MonthFromTime(t), Result(2)).
4.  Compute obj.MakeDate(Result(3), TimeWithinDay(t)).
5.  Set the [[Value]] property of the this value to obj.TimeClip(Result(4)).
6.  Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_33__1:function(){
var SECTION = "15.9.5.33-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setUTCDate(date) ");

addNewTestCase(this, "TDATE = new Date(0);(TDATE).setUTCDate(31);TDATE",
UTCDateFromTime(this,SetUTCDate(this,0,31)),
LocalDateFromTime(this,SetUTCDate(this,0,31)) );

addNewTestCase(this, "TDATE = new Date(0);(TDATE).setUTCDate(1);TDATE",
UTCDateFromTime(this,SetUTCDate(this,0,1)),
LocalDateFromTime(this,SetUTCDate(this,0,1)) );

addNewTestCase(this, "TDATE = new Date(86400000);(TDATE).setUTCDate(1);TDATE",
UTCDateFromTime(this,SetUTCDate(this,86400000,1)),
LocalDateFromTime(this,SetUTCDate(this,86400000,1)) );

//test();

function addNewTestCase(obj, DateString, UTCDate, LocalDate) {
DateCase = eval( DateString );


obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,    DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           UTCDate.day,    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              LocalDate.day,        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}
function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}
function SetUTCDate(obj, t, date ) {
var T       = t;
var DATE    = Number( date );
var RESULT3 = obj.MakeDay(obj.YearFromTime(T), obj.MonthFromTime(T), DATE );
return ( obj.TimeClip(obj.MakeDate(RESULT3, obj.TimeWithinDay(t))) );
}

},

/**
File Name:          15_9_5_34__1.js
ECMA Section:       15.9.5.34 Date.prototype.setMonth(mon [, date ] )
Description:
If date is not specified, this behaves as if date were specified with the
value getDate( ).

1.  Let t be the result of obj.LocalTime(this time value).
2.  Call ToNumber(date).
3.  If date is not specified, compute obj.DateFromTime(t); otherwise, call ToNumber(date).
4.  Compute obj.MakeDay(obj.YearFromTime(t), Result(2), Result(3)).
5.  Compute UTC(obj.MakeDate(Result(4), TimeWithinDay(t))).
6.  Set the [[Value]] property of the this value to obj.TimeClip(Result(5)).
7.  Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_34__1:function(){
var SECTION = "15.9.5.34-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setMonth(mon [, date ] )");

getFunctionCases(this);

// regression test for http://scopus.mcom.com/bugsplat/show_bug.cgi?id=112404
d = new Date(0);
d.setMonth(1,1,1,1,1,1);

addNewTestCase(this,
"TDATE = new Date(0); TDATE.setMonth(1,1,1,1,1,1); TDATE",
UTCDateFromTime(this,SetMonth(this,0,1,1)),
LocalDateFromTime(this,SetMonth(this,0,1,1)) );


// whatever today is

addNewTestCase(this, "TDATE = new Date(this.TIME_NOW); (TDATE).setMonth(11,31); TDATE",
UTCDateFromTime(this,SetMonth(this,this.TIME_NOW,11,31)),
LocalDateFromTime(this,SetMonth(this,this.TIME_NOW,11,31)) );

// 1970

addNewTestCase(this, "TDATE = new Date(0);(TDATE).setMonth(0,1);TDATE",
UTCDateFromTime(this,SetMonth(this,0,0,1)),
LocalDateFromTime(this,SetMonth(this,0,0,1)) );

addNewTestCase(this, "TDATE = new Date("+this.TIME_1900+"); "+
"(TDATE).setMonth(11,31); TDATE",
UTCDateFromTime(this, SetMonth(this,this.TIME_1900,11,31) ),
LocalDateFromTime(this, SetMonth(this,this.TIME_1900,11,31) ) );

//test();

function addNewTestCase(obj, DateString, UTCDate, LocalDate) {
DateCase = eval( DateString );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,    DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           UTCDate.day,    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              LocalDate.day,        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}

function getFunctionCases(obj) {
// some tests for all functions
obj.TestCase(
SECTION,
"Date.prototype.setMonth.length",
2,
Date.prototype.setMonth.length );

obj.TestCase(
SECTION,
"typeof Date.prototype.setMonth",
"function",
typeof Date.prototype.setMonth );

}

function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( My.DateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}
function SetMonth(obj, t, mon, date ) {
var TIME = obj.LocalTime(t);
var MONTH = Number( mon );
var DATE = ( date == void 0 ) ? obj.DateFromTime(TIME) : Number( date );
var DAY = obj.MakeDay( obj.YearFromTime(TIME), MONTH, DATE );
return ( obj.TimeClip (obj.UTC(obj.MakeDate( DAY, obj.TimeWithinDay(TIME) ))) );
}

},

/**
File Name:          15_9_5_35__1.js
ECMA Section:       15.9.5.35 Date.prototype.setUTCMonth(mon [,date])
Description:
Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_35__1:function(){
var SECTION = "15.9.5.35-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setUTCMonth(mon [,date] ) ");
addNewTestCase(this, "TDATE = new Date(0);(TDATE).setUTCMonth(0);TDATE",
UTCDateFromTime(this,SetUTCMonth(this,0,0)),
LocalDateFromTime(this,SetUTCMonth(this,0,0)) );

addNewTestCase(this, "TDATE = new Date(0);(TDATE).setUTCMonth(11);TDATE",
UTCDateFromTime(this,SetUTCMonth(this,0,11)),
LocalDateFromTime(this,SetUTCMonth(this,0,11)) );

addNewTestCase(this, "TDATE = new Date(0);(TDATE).setUTCMonth(5,4);TDATE",
UTCDateFromTime(this,SetUTCMonth(this,0,5,4)),
LocalDateFromTime(this,SetUTCMonth(this,0,5,4)) );

//test();

function addNewTestCase(obj, DateString, UTCDate, LocalDate) {
DateCase = eval( DateString );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,        DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,       DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,        DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           UTCDate.day,         DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,       DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,     DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,     DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,          DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              LocalDate.day,        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}
function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}
function SetUTCMonth(obj, t, month, date ) {
var T       = t;
var MONTH   = Number( month );
var DATE    = ( date == void 0) ? obj.DateFromTime(T) : Number( date );

var RESULT4 = obj.MakeDay(obj.YearFromTime(T), MONTH, DATE );
var RESULT5 = obj.MakeDate( RESULT4, obj.TimeWithinDay(T));

return ( obj.TimeClip(RESULT5) );
}

},

/**
File Name:          15_9_5_36__1.js
ECMA Section:       15.9.5.36 Date.prototype.setFullYear(year [, mon [, date ]] )
Description:

If mon is not specified, this behaves as if mon were specified with the
value getMonth( ). If date is not specified, this behaves as if date were
specified with the value getDate( ).

1.   Let t be the result of obj.LocalTime(this time value); but if this time
value is NaN, let t be +0.
2.   Call ToNumber(year).
3.   If mon is not specified, compute obj.MonthFromTime(t); otherwise, call
ToNumber(mon).
4.   If date is not specified, compute obj.DateFromTime(t); otherwise, call
ToNumber(date).
5.   Compute obj.MakeDay(Result(2), Result(3), Result(4)).
6.   Compute UTC(obj.MakeDate(Result(5), TimeWithinDay(t))).
7.   Set the [[Value]] property of the this value to obj.TimeClip(Result(6)).
8.   Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

Added test cases for Year 2000 Compatilibity Testing.

*/
test_15_9_5_36__1:function(){
var SECTION = "15.9.5.36-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setFullYear(year [, mon [, date ]] )");


// 1969

addNewTestCase(this, "TDATE = new Date(0);(TDATE).setFullYear(1969);TDATE",
UTCDateFromTime(this,SetFullYear(this,0,1969)),
LocalDateFromTime(this,SetFullYear(this,0,1969)) );

addNewTestCase(this, "TDATE = new Date(0);(TDATE).setFullYear(1969,11);TDATE",
UTCDateFromTime(this,SetFullYear(this,0,1969,11)),
LocalDateFromTime(this,SetFullYear(this,0,1969,11)) );

addNewTestCase(this, "TDATE = new Date(0);(TDATE).setFullYear(1969,11,31);TDATE",
UTCDateFromTime(this,SetFullYear(this,0,1969,11,31)),
LocalDateFromTime(this,SetFullYear(this,0,1969,11,31)) );

//test();

function addNewTestCase(obj, DateString, UTCDate, LocalDate) {
DateCase = eval( DateString );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,    DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           UTCDate.day,    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              LocalDate.day,        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}

function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}
function SetFullYear(obj, t, year, mon, date ) {
var T = ( isNaN(t) ) ? 0 : obj.LocalTime(t) ;
var YEAR = Number( year );
var MONTH = ( mon == void 0 ) ? obj.MonthFromTime(T) : Number( mon );
var DATE = ( date == void 0 ) ? obj.DateFromTime(T)  : Number( date );

var DAY = obj.MakeDay( YEAR, MONTH, DATE );
var UTC_DATE = obj.UTC(obj.MakeDate( DAY, obj.TimeWithinDay(T)));

return ( obj.TimeClip(UTC_DATE) );
}

},

/**
File Name:          15_9_5_36__2.js
ECMA Section:       15.9.5.36 Date.prototype.setFullYear(year [, mon [, date ]] )
Description:

If mon is not specified, this behaves as if mon were specified with the
value getMonth( ). If date is not specified, this behaves as if date were
specified with the value getDate( ).

1.   Let t be the result of obj.LocalTime(this time value); but if this time
value is NaN, let t be +0.
2.   Call ToNumber(year).
3.   If mon is not specified, compute obj.MonthFromTime(t); otherwise, call
ToNumber(mon).
4.   If date is not specified, compute obj.DateFromTime(t); otherwise, call
ToNumber(date).
5.   Compute obj.MakeDay(Result(2), Result(3), Result(4)).
6.   Compute UTC(obj.MakeDate(Result(5), TimeWithinDay(t))).
7.   Set the [[Value]] property of the this value to obj.TimeClip(Result(6)).
8.   Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

Added test cases for Year 2000 Compatilibity Testing.

*/
test_15_9_5_36__2:function(){
var SECTION = "15.9.5.36-2";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setFullYear(year [, mon [, date ]] )");

// 1970

addNewTestCase(this, "TDATE = new Date(0);(TDATE).setFullYear(1970);TDATE",
UTCDateFromTime(this,SetFullYear(this,0,1970)),
LocalDateFromTime(this,SetFullYear(this,0,1970)) );

addNewTestCase(this, "TDATE = new Date(0);(TDATE).setFullYear(1970,0);TDATE",
UTCDateFromTime(this,SetFullYear(this,0,1970,0)),
LocalDateFromTime(this,SetFullYear(this,0,1970,0)) );

addNewTestCase(this, "TDATE = new Date(0);(TDATE).setFullYear(1970,0,1);TDATE",
UTCDateFromTime(this,SetFullYear(this,0,1970,0,1)),
LocalDateFromTime(this,SetFullYear(this,0,1970,0,1)) );

//test();

function addNewTestCase(obj, DateString, UTCDate, LocalDate) {
DateCase = eval( DateString );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,    DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           UTCDate.day,    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              LocalDate.day,        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}

function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}
function SetFullYear(obj, t, year, mon, date ) {
var T = ( isNaN(t) ) ? 0 : obj.LocalTime(t) ;
var YEAR = Number( year );
var MONTH = ( mon == void 0 ) ? obj.MonthFromTime(T) : Number( mon );
var DATE = ( date == void 0 ) ? obj.DateFromTime(T)  : Number( date );

var DAY = obj.MakeDay( YEAR, MONTH, DATE );
var UTC_DATE = obj.UTC(obj.MakeDate( DAY, obj.TimeWithinDay(T)));

return ( obj.TimeClip(UTC_DATE) );
}

},

/**
File Name:          15_9_5_36__3.js
ECMA Section:       15.9.5.36 Date.prototype.setFullYear(year [, mon [, date ]] )
Description:

If mon is not specified, this behaves as if mon were specified with the
value getMonth( ). If date is not specified, this behaves as if date were
specified with the value getDate( ).

1.   Let t be the result of obj.LocalTime(this time value); but if this time
value is NaN, let t be +0.
2.   Call ToNumber(year).
3.   If mon is not specified, compute obj.MonthFromTime(t); otherwise, call
ToNumber(mon).
4.   If date is not specified, compute obj.DateFromTime(t); otherwise, call
ToNumber(date).
5.   Compute obj.MakeDay(Result(2), Result(3), Result(4)).
6.   Compute UTC(obj.MakeDate(Result(5), TimeWithinDay(t))).
7.   Set the [[Value]] property of the this value to obj.TimeClip(Result(6)).
8.   Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

Added test cases for Year 2000 Compatilibity Testing.
*/
test_15_9_5_36__3:function(){
var SECTION = "15.9.5.36-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setFullYear(year [, mon [, date ]] )");

// 1971
addNewTestCase(this, "TDATE = new Date(0);(TDATE).setFullYear(1971);TDATE",
UTCDateFromTime(this,SetFullYear(this,0,1971)),
LocalDateFromTime(this,SetFullYear(this,0,1971)) );

addNewTestCase(this, "TDATE = new Date(0);(TDATE).setFullYear(1971,0);TDATE",
UTCDateFromTime(this,SetFullYear(this,0,1971,0)),
LocalDateFromTime(this,SetFullYear(this,0,1971,0)) );

addNewTestCase(this, "TDATE = new Date(0);(TDATE).setFullYear(1971,0,1);TDATE",
UTCDateFromTime(this,SetFullYear(this,0,1971,0,1)),
LocalDateFromTime(this,SetFullYear(this,0,1971,0,1)) );

//test();

function addNewTestCase(obj, DateString, UTCDate, LocalDate) {
DateCase = eval( DateString );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,    DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           UTCDate.day,    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              LocalDate.day,        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}

function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}
function SetFullYear(obj, t, year, mon, date ) {
var T = ( isNaN(t) ) ? 0 : obj.LocalTime(t) ;
var YEAR = Number( year );
var MONTH = ( mon == void 0 ) ? obj.MonthFromTime(T) : Number( mon );
var DATE = ( date == void 0 ) ? obj.DateFromTime(T)  : Number( date );

var DAY = obj.MakeDay( YEAR, MONTH, DATE );
var UTC_DATE = obj.UTC(obj.MakeDate( DAY, obj.TimeWithinDay(T)));

return ( obj.TimeClip(UTC_DATE) );
}

},

/**
File Name:          15_9_5_36__4.js
ECMA Section:       15.9.5.36 Date.prototype.setFullYear(year [, mon [, date ]] )
Description:

If mon is not specified, this behaves as if mon were specified with the
value getMonth( ). If date is not specified, this behaves as if date were
specified with the value getDate( ).

1.   Let t be the result of obj.LocalTime(this time value); but if this time
value is NaN, let t be +0.
2.   Call ToNumber(year).
3.   If mon is not specified, compute obj.MonthFromTime(t); otherwise, call
ToNumber(mon).
4.   If date is not specified, compute DateFromTime(t); otherwise, call
ToNumber(date).
5.   Compute obj.MakeDay(Result(2), Result(3), Result(4)).
6.   Compute UTC(obj.MakeDate(Result(5), TimeWithinDay(t))).
7.   Set the [[Value]] property of the this value to obj.TimeClip(Result(6)).
8.   Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

Added test cases for Year 2000 Compatilibity Testing.
*/
test_15_9_5_36__4:function(){
var SECTION = "15.9.5.36-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setFullYear(year [, mon [, date ]] )");

// 1999
addNewTestCase(this, "TDATE = new Date(0);(TDATE).setFullYear(1999);TDATE",
UTCDateFromTime(this,SetFullYear(this,0,1999)),
LocalDateFromTime(this,SetFullYear(this,0,1999)) );

addNewTestCase(this, "TDATE = new Date(0);(TDATE).setFullYear(1999,11);TDATE",
UTCDateFromTime(this,SetFullYear(this,0,1999,11)),
LocalDateFromTime(this,SetFullYear(this,0,1999,11)) );

addNewTestCase(this, "TDATE = new Date(0);(TDATE).setFullYear(1999,11,31);TDATE",
UTCDateFromTime(this,SetFullYear(this,0,1999,11,31)),
LocalDateFromTime(this,SetFullYear(this,0,1999,11,31)) );

//test();

function addNewTestCase(obj, DateString, UTCDate, LocalDate) {
DateCase = eval( DateString );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,    DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           UTCDate.day,    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              LocalDate.day,        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}

function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}
function SetFullYear(obj, t, year, mon, date ) {
var T = ( isNaN(t) ) ? 0 : obj.LocalTime(t) ;
var YEAR = Number( year );
var MONTH = ( mon == void 0 ) ? obj.MonthFromTime(T) : Number( mon );
var DATE = ( date == void 0 ) ? obj.DateFromTime(T)  : Number( date );

var DAY = obj.MakeDay( YEAR, MONTH, DATE );
var UTC_DATE = obj.UTC(obj.MakeDate( DAY, obj.TimeWithinDay(T)));

return ( obj.TimeClip(UTC_DATE) );
}

},

/**
File Name:          15_9_5_36__5.js
ECMA Section:       15.9.5.36 Date.prototype.setFullYear(year [, mon [, date ]] )
Description:

If mon is not specified, this behaves as if mon were specified with the
value getMonth( ). If date is not specified, this behaves as if date were
specified with the value getDate( ).

1.   Let t be the result of obj.LocalTime(this time value); but if this time
value is NaN, let t be +0.
2.   Call ToNumber(year).
3.   If mon is not specified, compute obj.MonthFromTime(t); otherwise, call
ToNumber(mon).
4.   If date is not specified, compute obj.DateFromTime(t); otherwise, call
ToNumber(date).
5.   Compute obj.MakeDay(Result(2), Result(3), Result(4)).
6.   Compute UTC(obj.MakeDate(Result(5), TimeWithinDay(t))).
7.   Set the [[Value]] property of the this value to obj.TimeClip(Result(6)).
8.   Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

Added test cases for Year 2000 Compatilibity Testing.
*/
test_15_9_5_36__5:function(){
var SECTION = "15.9.5.36-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setFullYear(year [, mon [, date ]] )");

// 2000
addNewTestCase(this, "TDATE = new Date(0);(TDATE).setFullYear(2000);TDATE",
UTCDateFromTime(this,SetFullYear(this,0,2000)),
LocalDateFromTime(this,SetFullYear(this,0,2000)) );

addNewTestCase(this, "TDATE = new Date(0);(TDATE).setFullYear(2000,0);TDATE",
UTCDateFromTime(this,SetFullYear(this,0,2000,0)),
LocalDateFromTime(this,SetFullYear(this,0,2000,0)) );

addNewTestCase(this, "TDATE = new Date(0);(TDATE).setFullYear(2000,0,1);TDATE",
UTCDateFromTime(this,SetFullYear(this,0,2000,0,1)),
LocalDateFromTime(this,SetFullYear(this,0,2000,0,1)) );

//test();

function addNewTestCase(obj, DateString, UTCDate, LocalDate) {
DateCase = eval( DateString );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,    DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           UTCDate.day,    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              LocalDate.day,        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}

function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}
function SetFullYear(obj, t, year, mon, date ) {
var T = ( isNaN(t) ) ? 0 : obj.LocalTime(t) ;
var YEAR = Number( year );
var MONTH = ( mon == void 0 ) ? obj.MonthFromTime(T) : Number( mon );
var DATE = ( date == void 0 ) ? obj.DateFromTime(T)  : Number( date );

var DAY = obj.MakeDay( YEAR, MONTH, DATE );
var UTC_DATE = obj.UTC(obj.MakeDate( DAY, obj.TimeWithinDay(T)));

return ( obj.TimeClip(UTC_DATE) );
}

},

/**
File Name:          15_9_5_36__6.js
ECMA Section:       15.9.5.36 Date.prototype.setFullYear(year [, mon [, date ]] )
Description:

If mon is not specified, this behaves as if mon were specified with the
value getMonth( ). If date is not specified, this behaves as if date were
specified with the value getDate( ).

1.   Let t be the result of obj.LocalTime(this time value); but if this time
value is NaN, let t be +0.
2.   Call ToNumber(year).
3.   If mon is not specified, compute obj.MonthFromTime(t); otherwise, call
ToNumber(mon).
4.   If date is not specified, compute obj.DateFromTime(t); otherwise, call
ToNumber(date).
5.   Compute obj.MakeDay(Result(2), Result(3), Result(4)).
6.   Compute UTC(obj.MakeDate(Result(5), TimeWithinDay(t))).
7.   Set the [[Value]] property of the this value to obj.TimeClip(Result(6)).
8.   Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

Added test cases for Year 2000 Compatilibity Testing.

*/
test_15_9_5_36__6:function(){
var SECTION = "15.9.5.36-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setFullYear(year [, mon [, date ]] )");

// feb 29, 2000
addNewTestCase(this, "TDATE = new Date(0);(TDATE).setFullYear(2000);TDATE",
UTCDateFromTime(this,SetFullYear(this,0,2000)),
LocalDateFromTime(this,SetFullYear(this,0,2000)) );

addNewTestCase(this, "TDATE = new Date(0);(TDATE).setFullYear(2000,1);TDATE",
UTCDateFromTime(this,SetFullYear(this,0,2000,1)),
LocalDateFromTime(this,SetFullYear(this,0,2000,1)) );

addNewTestCase(this, "TDATE = new Date(0);(TDATE).setFullYear(2000,1,29);TDATE",
UTCDateFromTime(this,SetFullYear(this,0,2000,1,29)),
LocalDateFromTime(this,SetFullYear(this,0,2000,1,29)) );

//test();

function addNewTestCase(obj, DateString, UTCDate, LocalDate) {
DateCase = eval( DateString );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,    DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           UTCDate.day,    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              LocalDate.day,        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}

function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}
function SetFullYear(obj, t, year, mon, date ) {
var T = ( isNaN(t) ) ? 0 : obj.LocalTime(t) ;
var YEAR = Number( year );
var MONTH = ( mon == void 0 ) ? obj.MonthFromTime(T) : Number( mon );
var DATE = ( date == void 0 ) ? obj.DateFromTime(T)  : Number( date );

var DAY = obj.MakeDay( YEAR, MONTH, DATE );
var UTC_DATE = obj.UTC(obj.MakeDate( DAY, obj.TimeWithinDay(T)));

return ( obj.TimeClip(UTC_DATE) );
}

},

/**
File Name:          15_9_5_36__7.js
ECMA Section:       15.9.5.36 Date.prototype.setFullYear(year [, mon [, date ]] )
Description:

If mon is not specified, this behaves as if mon were specified with the
value getMonth( ). If date is not specified, this behaves as if date were
specified with the value getDate( ).

1.   Let t be the result of obj.LocalTime(this time value); but if this time
value is NaN, let t be +0.
2.   Call ToNumber(year).
3.   If mon is not specified, compute obj.MonthFromTime(t); otherwise, call
ToNumber(mon).
4.   If date is not specified, compute obj.DateFromTime(t); otherwise, call
ToNumber(date).
5.   Compute obj.MakeDay(Result(2), Result(3), Result(4)).
6.   Compute obj.UTC(obj.MakeDate(Result(5), TimeWithinDay(t))).
7.   Set the [[Value]] property of the this value to obj.TimeClip(Result(6)).
8.   Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

Added test cases for Year 2000 Compatilibity Testing.

*/
test_15_9_5_36__7:function(){
var SECTION = "15.9.5.36-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setFullYear(year [, mon [, date ]] )");

// Jan 1, 2005
addNewTestCase(this, "TDATE = new Date(0);(TDATE).setFullYear(2005);TDATE",
UTCDateFromTime(this,SetFullYear(this,0,2005)),
LocalDateFromTime(this,SetFullYear(this,0,2005)) );

addNewTestCase(this, "TDATE = new Date(0);(TDATE).setFullYear(2005,0);TDATE",
UTCDateFromTime(this,SetFullYear(this,0,2005,0)),
LocalDateFromTime(this,SetFullYear(this,0,2005,0)) );

addNewTestCase(this, "TDATE = new Date(0);(TDATE).setFullYear(2005,0,1);TDATE",
UTCDateFromTime(this,SetFullYear(this,0,2005,0,1)),
LocalDateFromTime(this,SetFullYear(this,0,2005,0,1)) );

//test();

function addNewTestCase(obj, DateString, UTCDate, LocalDate) {
DateCase = eval( DateString );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,    DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           UTCDate.day,    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              LocalDate.day,        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}

function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}
function SetFullYear(obj, t, year, mon, date ) {
var T = ( isNaN(t) ) ? 0 : obj.LocalTime(t) ;
var YEAR = Number( year );
var MONTH = ( mon == void 0 ) ? obj.MonthFromTime(T) : Number( mon );
var DATE = ( date == void 0 ) ? obj.DateFromTime(T)  : Number( date );

var DAY = obj.MakeDay( YEAR, MONTH, DATE );
var UTC_DATE = obj.UTC(obj.MakeDate( DAY, obj.TimeWithinDay(T)));

return ( obj.TimeClip(UTC_DATE) );
}

},

/**
File Name:          15_9_5_37__1.js
ECMA Section:       15.9.5.37 Date.prototype.setUTCFullYear(year [, mon [, date ]] )
Description:

If mon is not specified, this behaves as if mon were specified with the
value getUTCMonth( ).  If date is not specified, this behaves as if date
were specified with the value getUTCDate( ).

1.   Let t be this time value; but if this time value is NaN, let t be +0.
2.   Call ToNumber(year).
3.   If mon is not specified, compute obj.MonthFromTime(t); otherwise, call
ToNumber(mon).
4.   If date is not specified, compute obj.DateFromTime(t); otherwise, call
ToNumber(date).
5.   Compute obj.MakeDay(Result(2), Result(3), Result(4)).
6.   Compute obj.MakeDate(Result(5), TimeWithinDay(t)).
7.   Set the [[Value]] property of the this value to obj.TimeClip(Result(6)).
8.   Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

Added some Year 2000 test cases.
*/
test_15_9_5_37__1:function(){
var SECTION = "15.9.5.37-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setUTCFullYear(year [, mon [, date ]] )");


// Dates around 1970

addNewTestCase(this, "TDATE = new Date(0);(TDATE).setUTCFullYear(1970);TDATE",
UTCDateFromTime(this,SetUTCFullYear(this,0,1970)),
LocalDateFromTime(this,SetUTCFullYear(this,0,1970)) );

addNewTestCase(this, "TDATE = new Date(0);(TDATE).setUTCFullYear(1971);TDATE",
UTCDateFromTime(this,SetUTCFullYear(this,0,1971)),
LocalDateFromTime(this,SetUTCFullYear(this,0,1971)) );

addNewTestCase(this, "TDATE = new Date(0);(TDATE).setUTCFullYear(1972);TDATE",
UTCDateFromTime(this,SetUTCFullYear(this,0,1972)),
LocalDateFromTime(this,SetUTCFullYear(this,0,1972)) );

addNewTestCase(this, "TDATE = new Date(0);(TDATE).setUTCFullYear(1968);TDATE",
UTCDateFromTime(this,SetUTCFullYear(this,0,1968)),
LocalDateFromTime(this,SetUTCFullYear(this,0,1968)) );

addNewTestCase(this, "TDATE = new Date(0);(TDATE).setUTCFullYear(1969);TDATE",
UTCDateFromTime(this,SetUTCFullYear(this,0,1969)),
LocalDateFromTime(this,SetUTCFullYear(this,0,1969)) );

addNewTestCase(this, "TDATE = new Date(0);(TDATE).setUTCFullYear(1969);TDATE",
UTCDateFromTime(this,SetUTCFullYear(this,0,1969)),
LocalDateFromTime(this,SetUTCFullYear(this,0,1969)) );

//test();

function addNewTestCase(obj, DateString, UTCDate, LocalDate) {
DateCase = eval( DateString );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,    DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           UTCDate.day,    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              LocalDate.day,        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}

function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}
function SetUTCFullYear(obj, t, year, mon, date ) {
var T = ( t != t ) ? 0 : t;
var YEAR = Number(year);
var MONTH = ( mon == void 0 ) ?     obj.MonthFromTime(T) : Number( mon );
var DATE  = ( date == void 0 ) ?    obj.DateFromTime(T)  : Number( date );
var DAY = obj.MakeDay( YEAR, MONTH, DATE );

return ( obj.TimeClip(obj.MakeDate(DAY, obj.TimeWithinDay(T))) );
}

},

/**
File Name:          15_9_5_37__2.js
ECMA Section:       15.9.5.37 Date.prototype.setUTCFullYear(year [, mon [, date ]] )
Description:

If mon is not specified, this behaves as if mon were specified with the
value getUTCMonth( ).  If date is not specified, this behaves as if date
were specified with the value getUTCDate( ).

1.   Let t be this time value; but if this time value is NaN, let t be +0.
2.   Call ToNumber(year).
3.   If mon is not specified, compute obj.MonthFromTime(t); otherwise, call
ToNumber(mon).
4.   If date is not specified, compute obj.DateFromTime(t); otherwise, call
ToNumber(date).
5.   Compute obj.MakeDay(Result(2), Result(3), Result(4)).
6.   Compute obj.MakeDate(Result(5), TimeWithinDay(t)).
7.   Set the [[Value]] property of the this value to obj.TimeClip(Result(6)).
8.   Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

Added some Year 2000 test cases.
*/
test_15_9_5_37__2:function(){
var SECTION = "15.9.5.37-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setUTCFullYear(year [, mon [, date ]] )");


// Dates around 2000

addNewTestCase(this, "TDATE = new Date(0); TDATE.setUTCFullYear(2000);TDATE",
UTCDateFromTime(this,SetUTCFullYear(this,0,2000)),
LocalDateFromTime(this,SetUTCFullYear(this,0,2000)) );

addNewTestCase(this, "TDATE = new Date(0); TDATE.setUTCFullYear(2001);TDATE",
UTCDateFromTime(this,SetUTCFullYear(this,0,2001)),
LocalDateFromTime(this,SetUTCFullYear(this,0,2001)) );

addNewTestCase(this, "TDATE = new Date(0); TDATE.setUTCFullYear(1999);TDATE",
UTCDateFromTime(this,SetUTCFullYear(this,0,1999)),
LocalDateFromTime(this,SetUTCFullYear(this,0,1999)) );

//test();

function addNewTestCase(obj, DateString, UTCDate, LocalDate) {
DateCase = eval( DateString );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,    DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           UTCDate.day,    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              LocalDate.day,        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}

function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}
function SetUTCFullYear(obj, t, year, mon, date ) {
var T = ( t != t ) ? 0 : t;
var YEAR = Number(year);
var MONTH = ( mon == void 0 ) ?     obj.MonthFromTime(T) : Number( mon );
var DATE  = ( date == void 0 ) ?    obj.DateFromTime(T)  : Number( date );
var DAY = obj.MakeDay( YEAR, MONTH, DATE );

return ( obj.TimeClip(obj.MakeDate(DAY, obj.TimeWithinDay(T))) );
}

},

/**
File Name:          15_9_5_37__3.js
ECMA Section:       15.9.5.37 Date.prototype.setUTCFullYear(year [, mon [, date ]] )
Description:

If mon is not specified, this behaves as if mon were specified with the
value getUTCMonth( ).  If date is not specified, this behaves as if date
were specified with the value getUTCDate( ).

1.   Let t be this time value; but if this time value is NaN, let t be +0.
2.   Call ToNumber(year).
3.   If mon is not specified, compute obj.MonthFromTime(t); otherwise, call
ToNumber(mon).
4.   If date is not specified, compute obj.DateFromTime(t); otherwise, call
ToNumber(date).
5.   Compute obj.MakeDay(Result(2), Result(3), Result(4)).
6.   Compute obj.MakeDate(Result(5), TimeWithinDay(t)).
7.   Set the [[Value]] property of the this value to obj.TimeClip(Result(6)).
8.   Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

Added some Year 2000 test cases.

*/
test_15_9_5_37__3:function(){
var SECTION = "15.9.5.37-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setUTCFullYear(year [, mon [, date ]] )");


// Dates around 29 February 2000

var UTC_FEB_29_1972 = this.TIME_1970 + this.TimeInYear(1970) + this.TimeInYear(1971) +
31*this.msPerDay + 28*this.msPerDay;

var PST_FEB_29_1972 = UTC_FEB_29_1972 - this.TZ_DIFF * this.msPerHour;

addNewTestCase(this, "TDATE = new Date("+UTC_FEB_29_1972+"); "+
"TDATE.setUTCFullYear(2000);TDATE",
UTCDateFromTime(this,SetUTCFullYear(this,UTC_FEB_29_1972,2000)),
LocalDateFromTime(this,SetUTCFullYear(this,UTC_FEB_29_1972,2000)) );

addNewTestCase(this, "TDATE = new Date("+PST_FEB_29_1972+"); "+
"TDATE.setUTCFullYear(2000);TDATE",
UTCDateFromTime(this,SetUTCFullYear(this,PST_FEB_29_1972,2000)),
LocalDateFromTime(this,SetUTCFullYear(this,PST_FEB_29_1972,2000)) );

//test();

function addNewTestCase(obj, DateString, UTCDate, LocalDate) {
DateCase = eval( DateString );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,    DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           UTCDate.day,    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              LocalDate.day,        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}

function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}
function SetUTCFullYear(obj, t, year, mon, date ) {
var T = ( t != t ) ? 0 : t;
var YEAR = Number(year);
var MONTH = ( mon == void 0 ) ?     obj.MonthFromTime(T) : Number( mon );
var DATE  = ( date == void 0 ) ?    obj.DateFromTime(T)  : Number( date );
var DAY = obj.MakeDay( YEAR, MONTH, DATE );

return ( obj.TimeClip(obj.MakeDate(DAY, obj.TimeWithinDay(T))) );
}

},

/**
File Name:          15_9_5_37__4.js
ECMA Section:       15.9.5.37 Date.prototype.setUTCFullYear(year [, mon [, date ]] )
Description:

If mon is not specified, this behaves as if mon were specified with the
value getUTCMonth( ).  If date is not specified, this behaves as if date
were specified with the value getUTCDate( ).

1.   Let t be this time value; but if this time value is NaN, let t be +0.
2.   Call ToNumber(year).
3.   If mon is not specified, compute obj.MonthFromTime(t); otherwise, call
ToNumber(mon).
4.   If date is not specified, compute obj.DateFromTime(t); otherwise, call
ToNumber(date).
5.   Compute obj.MakeDay(Result(2), Result(3), Result(4)).
6.   Compute obj.MakeDate(Result(5), TimeWithinDay(t)).
7.   Set the [[Value]] property of the this value to obj.TimeClip(Result(6)).
8.   Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

Added some Year 2000 test cases.

*/
test_15_9_5_37__4:function(){
var SECTION = "15.9.5.37-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setUTCFullYear(year [, mon [, date ]] )");

// Dates around 2005

addNewTestCase(this, "TDATE = new Date(0); TDATE.setUTCFullYear(2005);TDATE",
UTCDateFromTime(this,SetUTCFullYear(this,0,2005)),
LocalDateFromTime(this,SetUTCFullYear(this,0,2005)) );

addNewTestCase(this, "TDATE = new Date(0); TDATE.setUTCFullYear(2004);TDATE",
UTCDateFromTime(this,SetUTCFullYear(this,0,2004)),
LocalDateFromTime(this,SetUTCFullYear(this,0,2004)) );

addNewTestCase(this, "TDATE = new Date(0); TDATE.setUTCFullYear(2006);TDATE",
UTCDateFromTime(this,SetUTCFullYear(this,0,2006)),
LocalDateFromTime(this,SetUTCFullYear(this,0,2006)) );

//test();

function addNewTestCase(obj, DateString, UTCDate, LocalDate) {
DateCase = eval( DateString );


//    fixed_year = ( ExpectDate.year >=1900 || ExpectDate.year < 2000 ) ? ExpectDate.year - 1900 : ExpectDate.year;

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,    DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           UTCDate.day,    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              LocalDate.day,        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}

function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}
function SetUTCFullYear(obj, t, year, mon, date ) {
var T = ( t != t ) ? 0 : t;
var YEAR = Number(year);
var MONTH = ( mon == void 0 ) ?     obj.MonthFromTime(T) : Number( mon );
var DATE  = ( date == void 0 ) ?    obj.DateFromTime(T)  : Number( date );
var DAY = obj.MakeDay( YEAR, MONTH, DATE );

return ( obj.TimeClip(obj.MakeDate(DAY, obj.TimeWithinDay(T))) );
}

},

/**
File Name:          15_9_5_37__5.js
ECMA Section:       15.9.5.37 Date.prototype.setUTCFullYear(year [, mon [, date ]] )
Description:

If mon is not specified, this behaves as if mon were specified with the
value getUTCMonth( ).  If date is not specified, this behaves as if date
were specified with the value getUTCDate( ).

1.   Let t be this time value; but if this time value is NaN, let t be +0.
2.   Call ToNumber(year).
3.   If mon is not specified, compute obj.MonthFromTime(t); otherwise, call
ToNumber(mon).
4.   If date is not specified, compute obj.DateFromTime(t); otherwise, call
ToNumber(date).
5.   Compute obj.MakeDay(Result(2), Result(3), Result(4)).
6.   Compute obj.MakeDate(Result(5), TimeWithinDay(t)).
7.   Set the [[Value]] property of the this value to obj.TimeClip(Result(6)).
8.   Return the value of the [[Value]] property of the this value.

Author:             christine@netscape.com
Date:               12 november 1997

Added some Year 2000 test cases.

*/
test_15_9_5_37__5:function(){
var SECTION = "15.9.5.37-1";
var VERSION = "ECMA_1";
//startTest();

//writeHeaderToLog( SECTION + " Date.prototype.setUTCFullYear(year [, mon [, date ]] )");

// Dates around 1900
addNewTestCase(this, "TDATE = new Date(0); TDATE.setUTCFullYear(1900);TDATE",
UTCDateFromTime(this,SetUTCFullYear(this,0,1900)),
LocalDateFromTime(this,SetUTCFullYear(this,0,1900)) );

addNewTestCase(this, "TDATE = new Date(0); TDATE.setUTCFullYear(1899);TDATE",
UTCDateFromTime(this,SetUTCFullYear(this,0,1899)),
LocalDateFromTime(this,SetUTCFullYear(this,0,1899)) );

addNewTestCase(this, "TDATE = new Date(0); TDATE.setUTCFullYear(1901);TDATE",
UTCDateFromTime(this,SetUTCFullYear(this,0,1901)),
LocalDateFromTime(this,SetUTCFullYear(this,0,1901)) );

//test();

function addNewTestCase(obj, DateString, UTCDate, LocalDate) {
DateCase = eval( DateString );

obj.TestCase( SECTION, DateString+".getTime()",             UTCDate.value,       DateCase.getTime() );
obj.TestCase( SECTION, DateString+".valueOf()",             UTCDate.value,       DateCase.valueOf() );

obj.TestCase( SECTION, DateString+".getUTCFullYear()",      UTCDate.year,    DateCase.getUTCFullYear() );
obj.TestCase( SECTION, DateString+".getUTCMonth()",         UTCDate.month,  DateCase.getUTCMonth() );
obj.TestCase( SECTION, DateString+".getUTCDate()",          UTCDate.date,   DateCase.getUTCDate() );
obj.TestCase( SECTION, DateString+".getUTCDay()",           UTCDate.day,    DateCase.getUTCDay() );
obj.TestCase( SECTION, DateString+".getUTCHours()",         UTCDate.hours,  DateCase.getUTCHours() );
obj.TestCase( SECTION, DateString+".getUTCMinutes()",       UTCDate.minutes,DateCase.getUTCMinutes() );
obj.TestCase( SECTION, DateString+".getUTCSeconds()",       UTCDate.seconds,DateCase.getUTCSeconds() );
obj.TestCase( SECTION, DateString+".getUTCMilliseconds()",  UTCDate.ms,     DateCase.getUTCMilliseconds() );

obj.TestCase( SECTION, DateString+".getFullYear()",         LocalDate.year,       DateCase.getFullYear() );
obj.TestCase( SECTION, DateString+".getMonth()",            LocalDate.month,      DateCase.getMonth() );
obj.TestCase( SECTION, DateString+".getDate()",             LocalDate.date,       DateCase.getDate() );
obj.TestCase( SECTION, DateString+".getDay()",              LocalDate.day,        DateCase.getDay() );
obj.TestCase( SECTION, DateString+".getHours()",            LocalDate.hours,      DateCase.getHours() );
obj.TestCase( SECTION, DateString+".getMinutes()",          LocalDate.minutes,    DateCase.getMinutes() );
obj.TestCase( SECTION, DateString+".getSeconds()",          LocalDate.seconds,    DateCase.getSeconds() );
obj.TestCase( SECTION, DateString+".getMilliseconds()",     LocalDate.ms,         DateCase.getMilliseconds() );

DateCase.toString = Object.prototype.toString;

obj.TestCase( SECTION,
DateString+".toString=Object.prototype.toString;"+DateString+".toString()",
"[object Date]",
DateCase.toString() );
}

function MyDate() {
this.year = 0;
this.month = 0;
this.date = 0;
this.hours = 0;
this.minutes = 0;
this.seconds = 0;
this.ms = 0;
}
function LocalDateFromTime(obj,t) {
t = obj.LocalTime(t);
return ( MyDateFromTime(obj,t) );
}
function UTCDateFromTime(obj,t) {
return ( MyDateFromTime(obj,t) );
}
function MyDateFromTime(obj, t ) {
var d = new MyDate();
d.year = obj.YearFromTime(t);
d.month = obj.MonthFromTime(t);
d.date = obj.DateFromTime(t);
d.hours = obj.HourFromTime(t);
d.minutes = obj.MinFromTime(t);
d.seconds = obj.SecFromTime(t);
d.ms = obj.msFromTime(t);

d.time = obj.MakeTime( d.hours, d.minutes, d.seconds, d.ms );
d.value = obj.TimeClip( obj.MakeDate( obj.MakeDay( d.year, d.month, d.date ), d.time ) );
d.day = obj.WeekDay( d.value );

return (d);
}
function SetUTCFullYear(obj, t, year, mon, date ) {
var T = ( t != t ) ? 0 : t;
var YEAR = Number(year);
var MONTH = ( mon == void 0 ) ?     obj.MonthFromTime(T) : Number( mon );
var DATE  = ( date == void 0 ) ?    obj.DateFromTime(T)  : Number( date );
var DAY = obj.MakeDay( YEAR, MONTH, DATE );

return ( obj.TimeClip(obj.MakeDate(DAY, obj.TimeWithinDay(T))) );
}

},

/**
File Name:          15_9_5_4__1.js
ECMA Section:       15.9.5.4-1 Date.prototype.getTime
Description:

1.  If the this value is not an object whose [[Class]] property is "Date",
generate a runtime error.
2.  Return this time value.
Author:             christine@netscape.com
Date:               12 november 1997
*/
test_15_9_5_4__1:function(){
var SECTION = "15.9.5.4-1";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getTime";
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_NOW );
addTestCase(this, this.TIME_1970 );
addTestCase(this, this.TIME_1900 );
addTestCase(this, this.TIME_2000 );
addTestCase(this, this.UTC_FEB_29_2000 );
addTestCase(this, UTC_JAN_1_2005 );

//test();

function addTestCase(obj, t ) {
obj.TestCase( SECTION,
"(new Date("+t+").getTime()",
t,
(new Date(t)).getTime() );

obj.TestCase( SECTION,
"(new Date("+(t+1)+").getTime()",
t+1,
(new Date(t+1)).getTime() );

obj.TestCase( SECTION,
"(new Date("+(t-1)+").getTime()",
t-1,
(new Date(t-1)).getTime() );

obj.TestCase( SECTION,
"(new Date("+(t-obj.TZ_ADJUST)+").getTime()",
t-obj.TZ_ADJUST,
(new Date(t-obj.TZ_ADJUST)).getTime() );

obj.TestCase( SECTION,
"(new Date("+(t+obj.TZ_ADJUST)+").getTime()",
t+obj.TZ_ADJUST,
(new Date(t+obj.TZ_ADJUST)).getTime() );
}

},

/**
File Name:          15_9_5_4__2__n.js
ECMA Section:       15.9.5.4-1 Date.prototype.getTime
Description:

1.  If the this value is not an object whose [[Class]] property is "Date",
generate a runtime error.
2.  Return this time value.
Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_4__2__n:function(){
var SECTION = "15.9.5.4-2-n";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getTime";

//writeHeaderToLog( SECTION + " "+ TITLE);

var MYDATE = new MyDate( this.TIME_2000 );

DESCRIPTION = "MYDATE.getTime()";
EXPECTED = "error";

this.TestCase( SECTION,
"MYDATE.getTime()",
"error",
eval("MYDATE.getTime()") );

//test();

function MyDate( value ) {
this.value = value;
this.getTime = Date.prototype.getTime;
}

},

/**
File Name:          15_9_5_5.js
ECMA Section:       15.9.5.5
Description:        Date.prototype.getYear

This function is specified here for backwards compatibility only. The
function getFullYear is much to be preferred for nearly all purposes,
because it avoids the "year 2000 problem."

1.  Let t be this time value.
2.  If t is NaN, return NaN.
3.  Return obj.YearFromTime(obj.LocalTime(t)) 1900.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_5:function(){
var SECTION = "15.9.5.5";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getYear()";
var TIME_0000  = this.time0000;
//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_NOW );
addTestCase(this, TIME_0000 );
addTestCase(this, this.TIME_1970 );
addTestCase(this, this.TIME_1900 );
addTestCase(this, this.TIME_2000 );
addTestCase(this, this.UTC_FEB_29_2000 );

this.TestCase( SECTION,
"(new Date(NaN)).getYear()",
NaN,
(new Date(NaN)).getYear() );

this.TestCase( SECTION,
"Date.prototype.getYear.length",
0,
Date.prototype.getYear.length );

//test();

function addTestCase(obj, t ) {
obj.TestCase( SECTION,
"(new Date("+t+")).getYear()",
GetYear(obj.YearFromTime(obj.LocalTime(t))),
(new Date(t)).getYear() );

obj.TestCase( SECTION,
"(new Date("+(t+1)+")).getYear()",
GetYear(obj.YearFromTime(obj.LocalTime(t+1))),
(new Date(t+1)).getYear() );

obj.TestCase( SECTION,
"(new Date("+(t-1)+")).getYear()",
GetYear(obj.YearFromTime(obj.LocalTime(t-1))),
(new Date(t-1)).getYear() );

obj.TestCase( SECTION,
"(new Date("+(t-obj.TZ_ADJUST)+")).getYear()",
GetYear(obj.YearFromTime(obj.LocalTime(t-obj.TZ_ADJUST))),
(new Date(t-obj.TZ_ADJUST)).getYear() );

obj.TestCase( SECTION,
"(new Date("+(t+obj.TZ_ADJUST)+")).getYear()",
GetYear(obj.YearFromTime(obj.LocalTime(t+obj.TZ_ADJUST))),
(new Date(t+obj.TZ_ADJUST)).getYear() );
}
function GetYear( year ) {
return year - 1900;
}

},

/**
File Name:          15_9_5_6.js
ECMA Section:       15.9.5.6
Description:        Date.prototype.getFullYear

1.   Let t be this time value.
2.   If t is NaN, return NaN.
3.   Return obj.YearFromTime(obj.LocalTime(t)).
Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_6:function(){
var SECTION = "15.9.5.6";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getFullYear()";
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
var TIME_0000  = this.time0000;
//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_NOW );
addTestCase(this, TIME_0000 );
addTestCase(this, this.TIME_1970 );
addTestCase(this, this.TIME_1900 );
addTestCase(this, this.TIME_2000 );
addTestCase(this, this.UTC_FEB_29_2000 );
addTestCase(this, UTC_JAN_1_2005 );

this.TestCase( SECTION,
"(new Date(NaN)).getFullYear()",
NaN,
(new Date(NaN)).getFullYear() );

this.TestCase( SECTION,
"Date.prototype.getFullYear.length",
0,
Date.prototype.getFullYear.length );

//test();
function addTestCase(obj, t ) {
obj.TestCase( SECTION,
"(new Date("+t+")).getFullYear()",
obj.YearFromTime(obj.LocalTime(t)),
(new Date(t)).getFullYear() );

obj.TestCase( SECTION,
"(new Date("+(t+1)+")).getFullYear()",
obj.YearFromTime(obj.LocalTime(t+1)),
(new Date(t+1)).getFullYear() );

obj.TestCase( SECTION,
"(new Date("+(t-1)+")).getFullYear()",
obj.YearFromTime(obj.LocalTime(t-1)),
(new Date(t-1)).getFullYear() );

obj.TestCase( SECTION,
"(new Date("+(t-obj.TZ_ADJUST)+")).getFullYear()",
obj.YearFromTime(obj.LocalTime(t-obj.TZ_ADJUST)),
(new Date(t-obj.TZ_ADJUST)).getFullYear() );

obj.TestCase( SECTION,
"(new Date("+(t+obj.TZ_ADJUST)+")).getFullYear()",
obj.YearFromTime(obj.LocalTime(t+obj.TZ_ADJUST)),
(new Date(t+obj.TZ_ADJUST)).getFullYear() );
}

},

/**
File Name:          15_9_5_7.js
ECMA Section:       15.9.5.7
Description:        Date.prototype.getUTCFullYear

1.Let t be this time value.
2.If t is NaN, return NaN.
3.Return obj.YearFromTime(t).
Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_7:function(){
var SECTION = "15.9.5.7";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getUTCFullYear()";
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
var TIME_0000  = this.time0000;
//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_NOW );
addTestCase(this, TIME_0000 );
addTestCase(this, this.TIME_1970 );
addTestCase(this, this.TIME_1900 );
addTestCase(this, this.TIME_2000 );
addTestCase(this, this.UTC_FEB_29_2000 );
addTestCase(this, UTC_JAN_1_2005 );

this.TestCase( SECTION,
"(new Date(NaN)).getUTCFullYear()",
NaN,
(new Date(NaN)).getUTCFullYear() );

this.TestCase( SECTION,
"Date.prototype.getUTCFullYear.length",
0,
Date.prototype.getUTCFullYear.length );

//test();

function addTestCase(obj, t ) {
obj.TestCase( SECTION,
"(new Date("+t+")).getUTCFullYear()",
obj.YearFromTime(t),
(new Date(t)).getUTCFullYear() );

obj.TestCase( SECTION,
"(new Date("+(t+1)+")).getUTCFullYear()",
obj.YearFromTime(t+1),
(new Date(t+1)).getUTCFullYear() );

obj.TestCase( SECTION,
"(new Date("+(t-1)+")).getUTCFullYear()",
obj.YearFromTime(t-1),
(new Date(t-1)).getUTCFullYear() );

obj.TestCase( SECTION,
"(new Date("+(t-obj.TZ_ADJUST)+")).getUTCFullYear()",
obj.YearFromTime(t-obj.TZ_ADJUST),
(new Date(t-obj.TZ_ADJUST)).getUTCFullYear() );

obj.TestCase( SECTION,
"(new Date("+(t+obj.TZ_ADJUST)+")).getUTCFullYear()",
obj.YearFromTime(t+obj.TZ_ADJUST),
(new Date(t+obj.TZ_ADJUST)).getUTCFullYear() );
}

},

/**
File Name:          15_9_5_8.js
ECMA Section:       15.9.5.8
Description:        Date.prototype.getMonth

1.  Let t be this time value.
2.  If t is NaN, return NaN.
3.  Return obj.MonthFromTime(obj.LocalTime(t)).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_8:function(){
var SECTION = "15.9.5.8";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getMonth()";
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
var TIME_0000  = this.time0000;
//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_NOW );
addTestCase(this, TIME_0000 );
addTestCase(this, this.TIME_1970 );
addTestCase(this, this.TIME_1900 );
addTestCase(this, this.TIME_2000 );
addTestCase(this, this.UTC_FEB_29_2000 );
addTestCase(this, UTC_JAN_1_2005 );

this.TestCase( SECTION,
"(new Date(NaN)).getMonth()",
NaN,
(new Date(NaN)).getMonth() );

this.TestCase( SECTION,
"Date.prototype.getMonth.length",
0,
Date.prototype.getMonth.length );
//test();

function addTestCase(obj, t ) {
var leap = obj.InLeapYear(t);

for ( var m = 0; m < 12; m++ ) {

t += obj.TimeInMonth(m, leap);

obj.TestCase( SECTION,
"(new Date("+t+")).getMonth()",
obj.MonthFromTime(obj.LocalTime(t)),
(new Date(t)).getMonth() );

obj.TestCase( SECTION,
"(new Date("+(t+1)+")).getMonth()",
obj.MonthFromTime(obj.LocalTime(t+1)),
(new Date(t+1)).getMonth() );

obj.TestCase( SECTION,
"(new Date("+(t-1)+")).getMonth()",
obj.MonthFromTime(obj.LocalTime(t-1)),
(new Date(t-1)).getMonth() );

obj.TestCase( SECTION,
"(new Date("+(t-obj.TZ_ADJUST)+")).getMonth()",
obj.MonthFromTime(obj.LocalTime(t-obj.TZ_ADJUST)),
(new Date(t-obj.TZ_ADJUST)).getMonth() );

obj.TestCase( SECTION,
"(new Date("+(t+obj.TZ_ADJUST)+")).getMonth()",
obj.MonthFromTime(obj.LocalTime(t+obj.TZ_ADJUST)),
(new Date(t+obj.TZ_ADJUST)).getMonth() );

}
}

},

/**
File Name:          15_9_5_9.js
ECMA Section:       15.9.5.9
Description:        Date.prototype.getUTCMonth

1.  Let t be this time value.
2.  If t is NaN, return NaN.
3.  Return obj.MonthFromTime(t).

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5_9:function(){
var SECTION = "15.9.5.9";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Date.prototype.getUTCMonth()";
var UTC_JAN_1_2005 = this.TIME_2000 + this.TimeInYear(2000) + this.TimeInYear(2001) + this.TimeInYear(2002) + this.TimeInYear(2003) + this.TimeInYear(2004);
var TIME_0000  = this.time0000;
//writeHeaderToLog( SECTION + " "+ TITLE);

addTestCase(this, this.TIME_NOW );
addTestCase(this, TIME_0000 );
addTestCase(this, this.TIME_1970 );
addTestCase(this, this.TIME_1900 );
addTestCase(this, this.TIME_2000 );
addTestCase(this, this.UTC_FEB_29_2000 );
addTestCase(this, UTC_JAN_1_2005 );

this.TestCase( SECTION,
"(new Date(NaN)).getUTCMonth()",
NaN,
(new Date(NaN)).getUTCMonth() );

this.TestCase( SECTION,
"Date.prototype.getUTCMonth.length",
0,
Date.prototype.getUTCMonth.length );
//test();

function addTestCase(obj, t ) {
var leap = obj.InLeapYear(t);

for ( var m = 0; m < 12; m++ ) {

t += obj.TimeInMonth(m, leap);

obj.TestCase( SECTION,
"(new Date("+t+")).getUTCMonth()",
obj.MonthFromTime(t),
(new Date(t)).getUTCMonth() );

obj.TestCase( SECTION,
"(new Date("+(t+1)+")).getUTCMonth()",
obj.MonthFromTime(t+1),
(new Date(t+1)).getUTCMonth() );

obj.TestCase( SECTION,
"(new Date("+(t-1)+")).getUTCMonth()",
obj.MonthFromTime(t-1),
(new Date(t-1)).getUTCMonth() );

obj.TestCase( SECTION,
"(new Date("+(t-obj.TZ_ADJUST)+")).getUTCMonth()",
obj.MonthFromTime(t-obj.TZ_ADJUST),
(new Date(t-obj.TZ_ADJUST)).getUTCMonth() );

obj.TestCase( SECTION,
"(new Date("+(t+obj.TZ_ADJUST)+")).getUTCMonth()",
obj.MonthFromTime(t+obj.TZ_ADJUST),
(new Date(t+obj.TZ_ADJUST)).getUTCMonth() );

}
}

},

/**
File Name:          15_9_5.js
ECMA Section:       15.9.5 Properties of the Date prototype object
Description:

The Date prototype object is itself a Date object (its [[Class]] is
"Date") whose value is NaN.

The value of the internal [[Prototype]] property of the Date prototype
object is the Object prototype object (15.2.3.1).

In following descriptions of functions that are properties of the Date
prototype object, the phrase "this Date object" refers to the object that
is the this value for the invocation of the function; it is an error if
this does not refer to an object for which the value of the internal
[[Class]] property is "Date". Also, the phrase "this time value" refers
to the number value for the time represented by this Date object, that is,
the value of the internal [[Value]] property of this Date object.

Author:             christine@netscape.com
Date:               12 november 1997

*/
test_15_9_5:function(){
var SECTION = "15.9.5";
var VERSION = "ECMA_1";
//startTest();
var TITLE   = "Properties of the Date Prototype Object";

//writeHeaderToLog( SECTION + " "+ TITLE);


Date.prototype.getClass = Object.prototype.toString;

this.TestCase( SECTION,
"Date.prototype.getClass",
"[object Date]",
Date.prototype.getClass() );
this.TestCase( SECTION,
"Date.prototype.valueOf()",
NaN,
Date.prototype.valueOf() );
//test();

}


})
.endType();
