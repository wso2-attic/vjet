/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.context.subctx.browser.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.ebayopensource.dsf.common.context.subctx.browser.BrowserEnum;
import org.ebayopensource.dsf.common.context.subctx.browser.OsEnum;

/**
 * For a comprehensive list of user-agents, please visit http://www.pgts.com.au/pgtsj/pgtsj0208c.html.
 * There are literally thousands.
 * PLEASE update BrowserSniffingUtilsTest whenever changing this class because they are heavily used by VI.
 */
public class BrowserSniffingUtils {

    private String m_browserName = "Unknown/0.0 (compatible; UNKNOWN 0.0; UNKNOWN OS 0.0)";
    public static final String UNKNOWN = "Unknown";
    public static final String FIREFOX = "FIREFOX";
    public static final String FIREFOX_HTTP_ONLY_INITIAL_VERSION = "2.0.0.5";

    private boolean m_mozillaBrowser = false;
    private String m_browserMajorVersion = null;
    private String m_browserMinorVersion = null;
    private double m_browserFullVersion = -1;
    Map m_browserMap = new HashMap();
    Map m_osNameMap =new HashMap();


    /**
     * Constructor for BrowserSniffingUtils.
     */
    public BrowserSniffingUtils(String theBrowserName) {
        // if ( theBrowserName == null )
        //  throw new IllegalArgumentException("user agent string cannot be null);");
        if (theBrowserName != null)
            m_browserName = theBrowserName.toUpperCase();

        Iterator iter = OsEnum.getIterator(OsEnum.class);
        while (iter.hasNext()){
            OsEnum os = (OsEnum)iter.next();
            if ( m_browserName.indexOf(os.getName()) != -1){
                m_osNameMap.put(os,os);

            }
        }
        iter = BrowserEnum.getIterator(BrowserEnum.class);
        while (iter.hasNext()){
            BrowserEnum browser = (BrowserEnum)iter.next();
            if ( m_browserName.indexOf(browser.getName()) != -1){
                if ( browser == BrowserEnum.MOZILLA){
                    m_mozillaBrowser = true;
                    continue;
                }
                m_browserMap.put(browser, browser);

            }
        }
        // go thru the rest of browser to check if browser is mozilla
        while (iter.hasNext()){
            BrowserEnum browser = (BrowserEnum)iter.next();
                if ( browser == BrowserEnum.MOZILLA){
                    m_mozillaBrowser = true;
                    break;
                }
        }

        // get full version
        m_browserFullVersion = getFullBrowserVersionNumber();
        m_browserMajorVersion = getBrowserMajorVersion();
        m_browserMinorVersion = getBrowserMinorVersion();


    }

    public boolean isMSIE(){
        // adding opera check as for opera isMSIE returning true
        return !isOpera() && !isWebTV() &&
            (m_browserMap.get(BrowserEnum.MSIE) != null || m_browserMap.get(BrowserEnum.INTERNET_EXPLORER)!= null);
    }

    /**
     * This includes Mozilla browsers.
     * @return
     */
    public boolean isNetscape(){
        return !isMSIE() && !isOpera() && !isWebTV() && !isSafari() &&
                //(m_browserName.indexOf(MOZILLA) != -1 ||
                //m_browserName.indexOf(NETSCAPE) != -1 )
                (m_mozillaBrowser ||
                 m_browserMap.get(BrowserEnum.NETSCAPE )!= null );
    }

    public boolean isNetscapeOnly(){
        return m_browserMap.get(BrowserEnum.NETSCAPE) != null ;
    }

    public boolean isMozillaOnly(){
        return !isMSIE() && !isOpera() && !isWebTV() && !isSafari() && !isNetscapeOnly() &&
                m_mozillaBrowser;
    }

    public boolean isFirefoxVersionCompatible(String comparedVersion){
        if(m_browserName.contains(BrowserSniffingUtils.FIREFOX)){
            String firefoxVersion = getFirefoxVersion();
            if(!firefoxVersion.equals("0")){
                String[] st = firefoxVersion.split("[\\.\\+\\s]");
                String[] stCompared = comparedVersion.split("\\.");
                for(int i=0; i < stCompared.length; i++){
                    int verCompared = Integer.parseInt(stCompared[i]);
                    if(i < st.length){
                        int ver = 0;
                        try{
                            ver = Integer.parseInt(st[i]);
                        }
                        catch(NumberFormatException e) {
                            ver = 0; // If non number token is encountered assume it is 0.
                        }
                        if(ver < verCompared){
                            return false;
                        } else if(ver > verCompared) {
                            return true;
                        }
                    }
                    else {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    private String getFirefoxVersion(){
        String versionString = "0";
        int fireFoxPosition = m_browserName.indexOf(BrowserSniffingUtils.FIREFOX);
        if(fireFoxPosition != -1){
            int startIndex = fireFoxPosition + BrowserSniffingUtils.FIREFOX.length() + 1;
            if(startIndex < m_browserName.length())
                versionString = m_browserName.substring(startIndex).trim();
        }
        return versionString;
    }

    /**
     * Since most browsers' HTTP header user-agent string contains "Mozilla/version"
     * in the beginning, check on that is sufficient to detect capabilities (eg. IFrame, JS, DHTML)
     * in other browsers.
     */
    public int getMozillaLevel(){
        // remove the decimal places
        return (int) getFullBrowserVersionNumber();
    }

    /**
     * This method has the capability to strip out the last (and last only) char of the browser
     * version number (eg. 6.0b). If the number is at the end of the user-agent, use "" empty space
     * for end identifier. Note, this method doesn't handle user-agent (we have yet to fine one) where
     * version number is at the end of the String and ends with a character.
     * (eg. "AppleWebKit/106.2 (KHTML, like Gecko) Safari/100.1a").
     *
     *
     * @param identifier1   beginning identifier (eg. "Mozilla/")
     * @param identifier2   end identifier    (eg. " ")
     * @return  the version number string between the beginning identifier and end identifier
     */
    private double parseFineLevel(String identifier1, String identifier2) {
            try{
                int startIndex = m_browserName.indexOf(identifier1) + identifier1.length();
                int endIndex = m_browserName.indexOf(identifier2,startIndex);
                while (true) {
                    /**
                    * this is to handing the following user-agaent: Mozilla/4.0 (compatible; MSIE 6.0b; Windows 98)
                    */
                    char trailingChar = m_browserName.charAt(endIndex);
                    if (Character.isDigit(trailingChar)) {
                           break;
                    }
                    endIndex--;
                }
                //BUGDB00222410, this is not needed, if we have this then the 
                //subsequent substring method will truncate the minor version number.
                //example, instead of '5.5' it will return '5.'

                //char trailingChar = m_browserName.charAt(endIndex);
                //if (!Character.isDigit(trailingChar)) {
                //  endIndex--;   
                //   }

                //this happend if the nubmer is at the end of the string
                //eg. "Mozilla/5.0 (Macintosh; U; PPC Mac OS X;en-us; AppleWebKit/106.2 (KHTML, like Gecko) Safari/100.1",
                if (startIndex == endIndex) {
                    // minus 1 so won't get out of bound.
                    endIndex = m_browserName.length() - 1;
                }
                String numString = m_browserName.substring(startIndex, endIndex + 1);
                return Double.parseDouble(numString);
            }catch(Exception exp)
            {
               //Assume that validation will fail in this case.
               return 0;
            }

        }


    static class BrowserFlags {
        static int ALLBROWSERS          =   0x00000000;
        static int WEBTV                =   0x00000001;
        static int OPERA                =   0x00000002;
        static int NETSCAPE_BEFORE_3    =   0x00000004;
        static int NETSCAPE_3            =   0x00000008;
        static int NETSCAPE_AFTER_3     =   0x00000010;
        static int NETSCAPE_5           =   0x00000020;
        static int NETSCAPE_6           =   0x00000040;
        static int MSIE_BEFORE_3        =   0x00000080;
        static int MSIE_AFTER_3         =   0x00000100;
        static int MSIE_5               =   0x00000200;
        static int MSIE_55              =   0x00000400;
        static int MSIE_6               =   0x00000800;
        static int AOL                  =   0x00001000;
    }

    /**
     * This method returns the string representation of the browser's major version, to get the full
     * double number, call  getFullBrowserVersionNumber().
     */
    public String getBrowserMajorVersion()
    {
        if ( m_browserMajorVersion != null)
            return m_browserMajorVersion;

        /* Safari has a lot of xx.xx.xx version numbers. One example is Safari 3.0 Beta on Windows.*/
        
        if( isNetscape() || isWebTV() || isSafari())
        {
            return getDottedMajorVersion();
        }
        else {
            return  getMajorVersion(getNonDottedFineBrowserVersion());
        }
    }

    /**
     * This method returns the string representation of the browser's minor version, to get the full
     * double number, call  getFullBrowserVersionNumber().
     */
    public String getBrowserMinorVersion()
    {
        if ( m_browserMinorVersion != null)
            return m_browserMinorVersion;

        if( isNetscape() || isWebTV() || isSafari())
        {
            return getDottedMinorVersion();
        }
        else {
            return  getMinorVersion(getNonDottedFineBrowserVersion());
        }
    }

    /**
     * This method is only suitable for browsers having NON-dotted version format (eg, not with 5.2.1), for
     * NS and WebTV, please call getFullBrowserVersionNumber().
     *
     * @return
     */
    public double getNonDottedFineBrowserVersion() {

        double ver = 0;
        //this must go first
        if( isMSIE())
        {
            ver = parseFineLevel("MSIE ",";");
            if( ver <= 0)
            {
                ver = parseFineLevel("MSIE "," ");
            }
        }
        else if ( isMozillaOnly())
        {
            ver = parseFineLevel("MOZILLA/", " ");
        }
        else if( isOpera())
        {// Opera has either "Opera/" or "Opera " format
            if( m_browserName.indexOf("OPERA/") != -1)
            {
                ver = parseFineLevel("OPERA/", " ");
                if( ver <= 0)
                {
                    ver = parseFineLevel("OPERA/", ";");
                }

            }
            else if ( m_browserName.indexOf("OPERA ") != -1)
            {
                ver = parseFineLevel("OPERA ", " ");
            }
        }
        else if ( isSafari())
        {
            ver = parseFineLevel("SAFARI/", "");
        }

//      removed because AOL browser is same as IE.
//      //this needs to be last because we don't want to confuse AOL version with IE version
//      //eg. Mozilla/4.0 (compatible; MSIE 6.0; AOL 7.0; Windows 98)
//      else if( isAOL())
//      {
//          ver = parseFineLevel("AOL/", " ");
//      }
        return ver;
    }

    /**
     *  This method is only called by getBrowserMajorVersion() for non NS and WebTV browser.
     * @param ver  full browser version. 
     * @return
     */
    private String getMajorVersion(double ver)
    {
        String ret = UNKNOWN;
        if( ver > 0)
        {
            ret = "" + (int) ver;
        }
        return ret;
    }

// This implementation doesn't work for MSIE 6.1

//  private String getMinorVersion(double ver)
//  {
//      String ret = "0";
//      if( ver > 0)
//      {
//          int major = (int) ver;
//          double minor = ver - (double) major;   // return 0.099999  instead of 0.1
//          ret = "" + minor;
//          ret = ret.substring(2); // get rid of '0.'
//          if( ret.length() > 2)
//          { // only take 2 positions.
//              ret = ret.substring(0,2);
//          }
//      }
//      return ret;
//  }

    /**
     *  This method is only called by getBrowserMinorVersion() for non NS and WebTV browser.
     *
     * @param ver  full browser version.
     * @return
     */
    private String getMinorVersion(double ver)
        {
            String ret = String.valueOf(ver);
            int dot = ret.indexOf(".");
            ret = ret.substring(dot + 1); // get rid of char before '.'
            if( ret.length() > 2)
            { // only take 2 positions.
                ret = ret.substring(0,2);
            }
            return ret;
        }

    public String getBrowserOS()
    {
        String ret = UNKNOWN;
        if(m_browserName.indexOf("SUNOS") != -1)
        {
            ret = "SunOS";
        }
        else if( m_browserName.indexOf("LINUX") != -1)
        {
            ret = "Linux";
        }
        else if( m_browserName.indexOf("WINDOWS") != -1)
        {
            ret = "Windows";
        }
        else if( m_browserName.indexOf("WIN95") != -1)
        {
            ret = "Win95";
        }
        else if( m_browserName.indexOf("WIN98") != -1)
        {
            ret = "Win98";
        }
        else if( m_browserName.indexOf("WINNT") != -1)
        {
            ret = "WinNT";
        }
        else if( m_browserName.indexOf("MAC") != -1)
        {
            ret = "Mac";
        }
        else if( m_browserName.indexOf("HP-UX") != -1)
        {
            ret = "HP-UX";
        }
        else if( m_browserName.indexOf("OPENBSD") != -1)
        {
            ret = "OpenBSD";
        }

        return ret;
    }



    /**
     * This method is only called by getDottedMajorVersion().
     *
     * @param identifier1
     * @param identifier2
     * @return
     */
    private String parseMajorVersion(String identifier1, String identifier2) {
        try{
            int startIndex = m_browserName.indexOf(identifier1) + identifier1.length();
            int endIndex = m_browserName.indexOf(identifier2,startIndex);
            String numString = m_browserName.substring(startIndex, endIndex);
            return numString;
        }catch(Exception exp)
        {
           //Assume that validation will fail in this case.
           return UNKNOWN;
        }

    }

    /**
     * This method is only called by getDottedMinorVersion().
     *
     * @param identifier1
     * @param identifier2
     * @return
     */
    private String parseMinorVersion(String identifier1, String identifier2) {
        // take at most 2 chars for minor version.
        String minString = UNKNOWN;
        try{
            int startIndex = m_browserName.indexOf(identifier1) + identifier1.length();
            int endIndex = m_browserName.indexOf(identifier2,startIndex);
            if( (endIndex + 2) <= (m_browserName.length()-1))
            {
                if(m_browserName.charAt(endIndex+2) != ' ' &&
                   m_browserName.charAt(endIndex+2) != ';' &&
                   m_browserName.charAt(endIndex+2) != '.')
                { // copy 2 chars
                    minString = m_browserName.substring(endIndex+1, endIndex+3);
                }
                else
                {  // copy one char.
                    minString = m_browserName.substring(endIndex+1, endIndex+2);
                }
            }
            else if ( (endIndex + 1) <= (m_browserName.length()-1))
            {
                minString = m_browserName.substring(endIndex+1, endIndex+2);
            }

        }catch(Exception exp)
        {
        }
        finally {
            return minString;
        }

    }

    /**
     * This method gets the MAJOR version for browsers with dotted formatted version number (eg. 4.0.1).
     * It ONLY support Netscape and WebTV right now.
     *
     * @return
     */
    private String getDottedMajorVersion()
    {
        String ret = UNKNOWN;

        if( m_browserName.indexOf("NETSCAPE/") != -1)
        {
            ret = parseMajorVersion("NETSCAPE/", ".");
        }
        else if (m_browserName.indexOf("NETSCAPE7/") != -1)
        { // 7.x type.
            ret = parseMajorVersion("NETSCAPE7/", ".");
        }
        else if( m_browserName.indexOf("NETSCAPE6/") != -1)
        {//6.x
            ret = parseMajorVersion("NETSCAPE6/", ".");
        }
        else if ( this.isWebTV() ) {
            ret = parseMajorVersion("WEBTV/", ".");
        }
        else if ( this.isSafari() ) {
            ret = parseMajorVersion("SAFARI/", ".");
        }
        else if (m_browserName.indexOf("NETSCAPE") == -1 && m_browserName.indexOf("MOZILLA/") != -1)
        { //4.x use Mozilla to determine the version.
            ret = parseMajorVersion("MOZILLA/", ".");
        }

        return ret;
    }

    /**
     * This method gets the MINOR version for browsers with dotted formatted version number (eg. 4.0.1).
     * It ONLY support Netscape and WebTV right now.
     *
     * @return
     */
    private String getDottedMinorVersion()
    {
        String ret = UNKNOWN;

        if( m_browserName.indexOf("NETSCAPE/") != -1)
        {
            ret = parseMinorVersion("NETSCAPE/", ".");
        }
        else if (m_browserName.indexOf("NETSCAPE7/") != -1)
        { // 7.x type.
            ret = parseMinorVersion("NETSCAPE7/", ".");
        }
        else if( m_browserName.indexOf("NETSCAPE6/") != -1)
        {//6.x
            ret = parseMinorVersion("NETSCAPE6/", ".");
        }
        else if ( this.isWebTV() ) {
            ret = parseMinorVersion("WEBTV/", ".");
        }
        else if ( this.isSafari() ) {
            ret = parseMinorVersion("SAFARI/", ".");
        }
        else if (m_browserName.indexOf("NETSCAPE") == -1 && m_browserName.indexOf("MOZILLA/") != -1)
        { //4.x use Mozilla to determine the version.
            ret = parseMinorVersion("MOZILLA/", ".");
        }

        return ret;
    }

    public String getBrowserVersion() {
        return getBrowserMajorVersion() + "." + getBrowserMinorVersion();
    }


    /**
     * This method returns the version number in one decimal place, it doesn't round up. It supports the
     * dotted format (in NS and WebTV) as well.
     *
     * @return
     */
    public double getFullBrowserVersionNumber() {

        if ( m_browserFullVersion != -1)
            return m_browserFullVersion;
        double finalValue = 0.0;
        try {
            if (isNetscapeOnly() || isWebTV() || isSafari()) {
                finalValue =  Double.parseDouble(getBrowserMajorVersion()) + convertBrowserMinorVersion();
            }else {
                finalValue =  this.getNonDottedFineBrowserVersion();
            }
        } catch (Exception e) {
            //eat up parsing exception
        }
        return finalValue;
    }

    /**
     * this method is needed because getBrowserMinorVersion() returns 2 characters, they could be (for Netscape)
     * "00" (version 7.0.0) or "20" (version 7.2.0) or "04" (version 7.0.4);
     */
    public double convertBrowserMinorVersion() {

        double finalValue = 0.0;
        try {
            String minorVersionStr = getBrowserMinorVersion();
            if (minorVersionStr.startsWith("0")) {
                return finalValue;
            }
            minorVersionStr = "0." + minorVersionStr;
            finalValue = Double.parseDouble(minorVersionStr);
        } catch (Exception e) {
            //eat up parsing exception
        }
        return finalValue;

    }

    boolean isMozillaBrowser()
    {
        return m_mozillaBrowser;
    }
    
    public boolean isWebTV(){
        //return m_browserName.indexOf(WEBTV) != -1;
        return m_browserMap.get(BrowserEnum.WEBTV) != null;           
    }
    

    public boolean isSafari() {
        //return m_browserName.indexOf(SAFARI) != -1;
        return m_browserMap.get( BrowserEnum.SAFARI) != null;
    }

    public boolean isOpera(){
        //return m_browserName.indexOf(OPERA) != -1;
        return m_browserMap.get(BrowserEnum.OPERA) != null ;
    }
    
    /**
     * Browsers belowed are the only browsers we test in QA.
     * Changed back to camel case because XSL check against camel cases.
     * @return
     */
    public String getBrowserVendor()
    {
        String ret = UNKNOWN;
        if( isMSIE())
        {
            ret = "MSIE";
        }
        else if (isNetscapeOnly())
        {
            ret = "Netscape";
        }
        else if( isMozillaOnly())
        {
            ret = "Mozilla";
        }
        else if( isOpera())
        {
            ret = "Opera";
        }
        else if( isWebTV())
        {
            ret = "WebTV";
        }
        else if (isSafari())
        {
            ret = "Safari";
        }
        else if (isAOL())
        {
            ret = "AOL";
        }

        return ret;
    }
    
    public boolean isAOL(){
        //return m_browserName.indexOf(AOL) != -1;
        return m_browserMap.get(BrowserEnum.AOL) != null;
    }
    

    /**
     * Browsers belowed are the only browsers we test in QA.
     * @return
     */
    public String getBrowserVendorForVI()
    {
        String ret = UNKNOWN;
        if( isMSIE())
        {
            ret = BrowserEnum.MSIE.getName();
        }
        else if (isNetscapeOnly())
        {
            ret = BrowserEnum.NETSCAPE.getName();
        }
        else if( isMozillaOnly())
        {
            ret = BrowserEnum.MOZILLA.getName();
        }
        else if( isOpera())
        {
            ret = BrowserEnum.OPERA.getName();
        }
        else if( isWebTV())
        {
            ret = BrowserEnum.WEBTV.getName();
        }
        else if (isSafari())
        {
            ret = BrowserEnum.SAFARI.getName();
        }
        else if (isAOL())
        {
            ret = BrowserEnum.AOL.getName();
        }

        return ret;
    }
}
