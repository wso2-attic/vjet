/**
 * @author spottavathini
 * Note: It currently has support for only the widely used methods. 
 * Some of the API are not available at this time.
 * It is a simplified version compared to java implementation 
*/

vjo.ctype('vjo.java.util.Date') //< public Date
.props({
	//>public static long UTC(int year, int month, int date, int hrs, int min, int sec)
	//>public static long UTC(int year, int month, int date, int hrs, int min, int sec, int ms)
	UTC: function(year, month, date, hours, minutes, seconds, ms) {
		if (typeof ms == 'undefined') ms = 0;
		return Date.UTC(year, month, date, hours, minutes, seconds, ms);
	},
	
	//>static final long getMillisOf(Date date)
	getMillisOf : function (date) {
		if (date == null) {
			return (new Date()).getTime();
		}
		return date.getTime();
	}
})
.protos({
	_date : undefined,
	
	//> public void contructs()
	//> public void contructs(String dateString)
	//> public void contructs(long date)
	//> public void contructs(int year, int month, int date)
	//> public void contructs(int year, int month, int date, int hrs, int min)
	//> public void contructs(int year, int month, int date, int hrs, int min, int sec)
	constructs : function () {
		if (arguments.length >= 3){
			var y = arguments[0], m = arguments[1], dt = arguments[2],
				h = arguments[3] || 0, mn = arguments[4] || 0,
				s = arguments[5] || 0;
			this._date = new Date(y, m, dt, h, mn, s);
		} else if (arguments.length == 1) {
			this._date = new Date(arguments[0]);
		} else {
			this._date = new Date();
		}
	},
	
	//>public int getFullYear()
	getFullYear : function () {
		return this._date.getFullYear();
	},

	//>public int getYear()
	getYear : function () {
		return this._date.getYear();
	},
	
	//>public void setYear(int year)
	setYear : function (year) {
		this._date.setYear(year);
	},
	
	//>public int getMonth()
	getMonth : function () {
		return this._date.getMonth();
	},
	
	//>public void setMonth(int month)
	setMonth : function (month) {
		this._date.setMonth(month);
	},
	
	//>public int getDate()
	getDate : function () {
		return this._date.getDate();
	},
	
	//>public void setDate(int date)
	setDate : function (date) {
		this._date.setDate(date);
	},

	//>public int getDay()
	getDay : function () {
		return this._date.getDay();
	},
	
	//>public int getHours()
	getHours : function () {
		return this._date.getHours();
	},
	
	//>public void setHours(int hours)
	setHours : function (hours) {
		this._date.setHours(hours);
	},
	
	//>public int getMinutes()
	getMinutes : function () {
		return this._date.getMinutes();
	},
	
	//>public void setMinutes(int minutes)
	setMinutes : function (minutes) {
		this._date.setMinutes(minutes);
	},
	
	//>public int getSeconds()
	getSeconds : function () {
		return this._date.getSeconds();
	},
	
	//>public void setSeconds(int seconds)
	setSeconds : function (seconds) {
		this._date.setSeconds(seconds);
	},
	
	//>public void getTime()
	getTime : function () {
		return this._date.getTime();
	},

	//>public void setTime(long time)
	setTime : function (time) {
		this._date.setTime(time);
	},
	
	//>public String toLocaleString()
	toLocaleString : function () {
		return this._date.toLocaleString();
	},
	
	//>public String toLocaleDateString()
	toLocaleDateString : function () {
		return this._date.toLocaleDateString();
	},

	//>public String toLocaleTimeString()
	toLocaleTimeString : function () {
		return this._date.toLocaleTimeString();
	},

	//>public String toGMTString()
	toGMTString : function () {
		return this._date.toGMTString();
	},
	
	//>public String getTimezoneOffset()
	getTimezoneOffset : function () {
		return this._date.getTimezoneOffset();
	},
	
	//>public boolean before(Date when)
	before : function (when) {
		return (this._date.getTime() < when.getTime());
	},
	
	//>public boolean after(Date when)
	after : function (when) {
		return (this._date.getTime() > when.getTime());
	},
	
	//>public boolean equals(Object obj)
	equals : function (obj) {
		return (obj instanceof this.vj$.Date && this.getTime() == obj.getTime());
	},
	
	//>public String toString()
	toString : function () {
		//TODO - Not same as java
		return this._date.toLocaleString();
	}
})
.endType();