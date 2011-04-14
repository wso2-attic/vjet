/**
 * <code>$w(String) ? Array</code>
 * <p>
 * Splits a string into an Array, treating all whitespace as delimiters. 
 * Equivalent to Ruby's %w{foo bar} or Perl's qw(foo bar).
 * <p>This is one of those life-savers for people who just hate commas in literal 
 * arrays :-)
 * <p>
 * Examples]
 * <p>
 * <code>
 * $w('apples bananas kiwis')</br>
 * // -> ['apples', 'bananas', 'kiwis']<br>
 * </code>
 * <p>
 * This can slightly shorten code when writing simple iterations:
 * <p>
 * <code>
 * $w('apples bananas kiwis').each(function(fruit){
 * 		var message = 'I like ' + fruit
 * 		// do something with the message
 * })
 * </code>
 * <p>
 * This also becomes sweet when combined with Element functions:
 * <p>
 * <code>
 * $w('ads navbar funkyLinks').each(Element.hide);
 * </code>
 * 
 */
//> public
vjo.ftype('org.prototypejs.$w') 
.props({
	/**
	 * Answer Array of parsed whitespace String.  If not a String
	 * returns empty [].
	 */
	//> public Array _invoke_({Object | String}? value)
	_invoke_: vjo.NEEDS_IMPL
})
.options({
	metatype: true
})
.endType();