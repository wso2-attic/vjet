vjo.ctype("vjoPro.dsf.utils.Date")
/**
* Utility class for date/time operations.
*/
.props({
/**
* Gets local time of the specified time zone.
*
* @param {int} timezone
*       a number that represents the local time zone (an UTC offset value)
* @param {java.util.Date} date
*        an specified UTC time
* @return {Object}
*        converted local time
*/
//> public Object getTimeZoneDate(int,Date);
getTimeZoneDate : function (piOffset,poDate) {
piOffset = piOffset?piOffset:0;
var d = poDate?poDate:new Date();
var localTime = d.getTime();
//1000 milliseconds = 1 second, and 1 minute = 60 seconds.
//Therefore, converting minutes to milliseconds involves multiplying by 60 * 1000 = 60000.
var localOffset = d.getTimezoneOffset() * 60000;
var utc = localTime + localOffset;
//1000 millseconds = 1 second, and 1 hour = 3600  seconds.
// Therefore, converting hours to milliseconds involves multiplying by 3600 * 1000 = 3600000.
return new Date(utc + (3600000*piOffset));
}
})
.endType();


