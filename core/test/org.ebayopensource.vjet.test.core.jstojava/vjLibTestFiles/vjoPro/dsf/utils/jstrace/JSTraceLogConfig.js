vjo.ctype("vjoPro.dsf.utils.jstrace.JSTraceLogConfig")
.needs("vjoPro.dsf.utils.jstrace.PlainFormatter")
.props({
properties: [

//============================================
//  	JSTrace Logging Configuration File
//============================================

//=========================================
//	!! IMPORTANT !!
//	Format of property lines:
//	["key", "value"],
//	DO add ',' at the end of each config line, leave the endGuard to close array
//=========================================

//============================================
//  	Global properties
//============================================

// "handlers" specifies a '&' separated list of log Handler
// classes.  These handlers will be installed during VM startup.
// Note that these classes must be on the system classpath.
// By default we only configure a ConsoleHandler, which will only
// show messages at the INFO and above levels.
//	["handlers", "vjoPro.dsf.utils.logging.ConsoleHandler"],

// To also add the RemoteHandler, use the following line instead.
//  ["handlers", "vjoPro.dsf.utils.logging.RemoteHandler & vjoPro.dsf.utils.logging.ConsoleHandler"],

// Default global logging level.
// This specifies which kinds of events are logged across
// all loggers.  For any given facility this global level
// can be overriden by a facility specific level
// Note that the ConsoleHandler also has a separate level
// setting to limit messages printed to the console.
//	[".level", "INFO"],

//============================================
// Handler specific properties.
// Describes specific configuration info for Handlers.
//============================================

// Limit the message that are printed on the console to INFO and above.
["vjoPro.dsf.utils.logging.ConsoleHandler.level", "INFO"],
//Local
["vjoPro.dsf.utils.logging.ConsoleHandler.formatter", "vjoPro.dsf.utils.jstrace.PlainFormatter"],
//No need to set handlers for we will use default ConsoleHandler
//	["vjoPro.dsf.utils.jstrace.TraceLogger.handlers", "vjoPro.dsf.utils.logging.ConsoleHandler"],


//============================================
// Facility specific properties.
// Provides extra control for each logger.
//============================================

// For example, set the com.xyz.foo logger to only log SEVERE
// messages:
// ["com.xyz.foo.level", "SEVERE"],

//==============================================
//Ending
//==============================================
["endGuard",""]
]
})
.endType();
