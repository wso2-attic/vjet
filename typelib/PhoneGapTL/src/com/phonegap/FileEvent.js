vjo.ctype('com.phonegap.FileEvent') //< public
.inherits('Event')
.protos({
	//>@SUPPRESSTYPECHECK
	target:null, //< public [EventTarget+FileEvent.TargetAdd]
	TargetAdd : vjo.ctype()
	.protos({
		error: null, //< public com.phonegap.File.FileError
		result: null //< public Object
	})
	.endType()
})
.options({
	metatype: true
})
.endType();