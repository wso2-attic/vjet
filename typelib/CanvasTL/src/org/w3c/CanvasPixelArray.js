vjo.ctype('org.w3c.CanvasPixelArray') //< public
/**
 * interface CanvasPixelArray {
  readonly attribute unsigned long length;
  getter octet (in unsigned long index);
  setter void (in unsigned long index, in octet value);
};
 */
.protos({
	length:undefined, //< long length;
	// TODO octet?
	//>Object (long index)
	getter:vjo.NEEDS_IMPL,
	//>  void (long index, Object value)
	setter:vjo.NEEDS_IMPL
	
	
})
.options({
	metatype:true
})
.endType();