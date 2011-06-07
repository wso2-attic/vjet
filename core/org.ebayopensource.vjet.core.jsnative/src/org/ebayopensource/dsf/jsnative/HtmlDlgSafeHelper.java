package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.BrowserSupport;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.OverLoadFunc;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IWillBeScriptable;


@BrowserSupport(BrowserType.IE_6P)
public interface HtmlDlgSafeHelper extends IWillBeScriptable {

	@Property String[] getBlockFormats();
	@Property String[] getFonts();
	
	/**
	 * @param Optional. A Variant that specifies the RGB value of the initial color selected in the color-selection dialog box. The value is specified as 0xrrggbb where rr is the red hex value, gg is the green hex value, and bb is the blue hex value. For a complete list of colors, see Color Table.
	 * @return A Variant that specifies the decimal value of the color chosen in the color-selection dialog box. The value must be converted to its hexadecimal equivalent and is specified as 0xrrggbb.
	 */
	@OverLoadFunc int ChooseColorDlg(int initColor);
	@OverLoadFunc int ChooseColorDlg();
	
	@Function Object getCharset();
	
}
