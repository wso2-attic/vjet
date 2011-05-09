/**
 * The device object describes the device's hardware and software.
 */
vjo.ctype('com.phonegap.Device') //< public
.globals({
	device : null //< com.phonegap.Device
})
.protos({  
	/**
	 * Get the device's model name.
	 */
	name     : null, //< public String
	/**
	 * Get the version of phonegap running on the device.
	 */
	phonegap  : null, //< public String
	/**
	 * Get the device's operating system name.
	 */
	platform : null, //< public String
	/**
	 * Get the device's Universally Unique Identifier (UUID).
	 */
	uuid     : null, //< public String
	/**
	 * Get the operating system version.
	 */
	version  : null //< public String
})
.options({metatype: true})
.endType();