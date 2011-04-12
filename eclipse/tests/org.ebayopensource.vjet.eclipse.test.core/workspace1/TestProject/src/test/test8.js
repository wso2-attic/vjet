vjo.etype("test.test8").protos({
    m_value : null, //< private boolean
    m_displayName : null,
    constructs : function (val, displayName) { //< private void contructs(boolean, String)
        this.m_value = val;
        this.m_displayName = displayName;
    },
    isWeekday : function () { //>public boolean
        return this.m_value;
    },
    
    isEvenOrdinal : function () {
        return (this.ordinal() % 2 == 0);
    },
    
    getDisplayName : function () {
        return this.name() + " is " + this.m_displayName;
    }
})
.values(
    MON:[true, "Monday"],
    TUE:[true, "Tuesday"], 
    WED:[true, "Wednesday"],  
    THU:[true, "Thursday"],
    FRI:[true, "Friday"], 
    SAT:[false, "Saturday"],
    SUN:[false, "Sunday"]
).;