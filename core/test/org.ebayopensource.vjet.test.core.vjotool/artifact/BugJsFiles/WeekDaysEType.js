vjo.etype('BugJsFiles.WeekDaysEType') //< public
.props({
	staticProp : 10,//<int
	staticProp1 : "Test", //<String
	staticFunc : function() {
		this.staticProp1.big();
	}
})
.protos({
	weekday : undefined, //< private boolean
	displayName : undefined,
	
	//> private void contructs(boolean, String)
	constructs : function (wkday, dispName) {
		
		this.displayName = dispName;
	},
	
	isWeekday : function () { //<public boolean isWeekday()
		return this.weekday;
	},
	
	getDisplayName : function () {
		return this.displayName;
	}
})
.values({
	MON:[true, 'Monday'],
	TUE:[true, 'Tuesday'],
	WED:[true, 'Wednesday'],
	THU:[true, 'Thursday'],
	FRI:[true, 'Friday'],
	SAT:[false, 'Saturday'],
	SUN:[false, 'Sunday']
})
.endType();