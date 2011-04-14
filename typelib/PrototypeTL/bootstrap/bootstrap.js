/**
 * These are the type extensions for the Prototype Typelib
 */
var typeExtensions = {
    //
    // Native JavaScript Extensions
    //
    'Array'    :[
       'org.prototypejs.nativeext.ArrayExt', 
       'org.prototypejs.Enumerable'
    ], 
    'Date'     : 'org.prototypejs.nativeext.DateExt',
    'Function' : 'org.prototypejs.nativeext.FunctionExt' ,
    'Number'   : 'org.prototypejs.nativeext.NumberExt' ,
    'Object'   : 'org.prototypejs.nativeext.ObjectExt' ,
    'RegExp'   : 'org.prototypejs.nativeext.RegExpExt' ,
    'String'   : 'org.prototypejs.nativeext.StringExt' ,
	
	//
	// DOM/HTML Extensions
	//
	'Document' : 'org.prototypejs.nativeext.documentExt',

	'Event' : [
	    'org.prototypejs.Event_Methods', 
        'org.prototypejs.Event_Ext2'
	],

	'Element' : [
	    'org.prototypejs.nativeext.ElementExt', 
        'org.prototypejs.Element_Methods', 
        'org.prototypejs.Layout_ElementExt', 
        'org.prototypejs.Element_addMethods'
    ]
	
}