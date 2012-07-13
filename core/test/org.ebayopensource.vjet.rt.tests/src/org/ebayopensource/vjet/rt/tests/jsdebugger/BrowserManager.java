package org.ebayopensource.vjet.rt.tests.jsdebugger;

 import java.io.File;
 import java.io.FileOutputStream;
 import java.io.IOException;

import org.ebayopensource.dsf.common.cmdexec.CommandExecutor;

/**
* Utility to display a URL in a system browser.  The API's are NOT meant to display
* actual HTML text, but rather a URL that represents that text.  If you need to 
* output HTML in DSF component (document or element) for or just HTML in bytes[],
* String, or Stream form, use HtmlDisplayer.
* 
* Examples:
*   // Display eBay in the default browser and reuse if already open
*   BrowserManager.displayUrlInDefault("http://www.ebay.com");
* 
*   // Display a file in the default browser and reuse if already open
*   BrowserManager.displayUrlInDefault("file://c:/v4docs/index.html");
* 
*   // Display eBay using the browser of your choice and will reuse if already open
*   BrowserManager.displayUrlIn("c:\\mozilla firefox\firefox.exe", "http://www.ebay.com");
* 
*   // You can use the path to whatever you want to display the URL.  Generally
*   // the displayer (browser, explorer, etc...) will be reused if previously launched
*   BrowserManager.displayUrlIn("explorer", "file://c:/v4docs/index.html"); 
* 
* Note - you must include the URL Scheme:
* <li>http://
* <li>https://
* <li>file://
*/
 public class BrowserManager {
//  public static void main(String[] args) {
//      BrowserManager.displayUrlIn("explorer", "http://www.yahoo.com") ;
//  }
     private static final String WIN_ID = "Windows";
     private static final String WIN2K3_ID = "Windows Server 2003";
    
     private static final String WIN_PATH = "rundll32";
     private static final String WIN_FLAG = "url.dll,FileProtocolHandler";
    
     private static final String UNIX_PATH = "netscape";
     private static final String UNIX_FLAG = "-remote openURL";
    
     private static final boolean IS_WINDOWS = BrowserManager.isWindowsPlatform() ;
    
    //
    // API
    //
    /**
    * This will launch the default browser (as registered with the OS).  
    * This should work on  Windows 95/98/NT/2000.  On WIN2K there may be some
    * problems with special chars in the URL...
    * A new browswer is launched each time.  If you want the same IE browser
    * to be used, you need to make IE your default browser and user the
    * displayUrlInDefault() method.
    */
     public static void displayUrlInIE(String url) {
        // Totally cheat here but it works well!
         CommandExecutor.executeCommand("explorer " + url) ;
    }
     public static void displayContentInIE(String content, String extension) {
         displayUrlInIE(createTempFile(content, extension)) ;
    }
    
    /**
     * Display the URL in Internet Explorer
     * @param url
     */
     public static boolean isInternetExplorerAvailable() {
         File file = null;
         if (isWindows2K3Platform()) {
             file = new File("c:\\program files (x86)\\Internet Explorer\\iexplore.exe");
             return file.exists();
        }
         else {
             file = new File("c:\\program files\\Internet Explorer\\iexplore.exe") ;
             return file.exists();
        }
    }
    
    /**
     * CommandExecutor.executeCommand(cmd) never returns in IE, so do not wait for error codes.
     * @param url
     */
     public static Process displayUrlInInternetExplorer(String url) {
         String cmd[] = new String[2];
         if (isWindows2K3Platform()) {
             cmd[0] = "c:\\program files (x86)\\Internet Explorer\\iexplore.exe";
        }
         else {
             cmd[0] = "c:\\program files\\Internet Explorer\\iexplore.exe";
        }
         cmd[1] = url;
         try {
             return Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
             throw new RuntimeException(e);
        }
    }   
     public static void displayContentInInternetExplorer(String content, String extension) {
         displayUrlInInternetExplorer(createTempFile(content, extension)) ;
    }
    
    /**
     * Displays the URL in FireFox.  A new browser instance is created each
     * time. If you want the same FireFox browser to be used, you need to make 
     * FireFox your default browser and user the displayUrlInDefault() method.
     * !!! CURRENTLY NOT SUPPORTED !!!
     */
    
     public static boolean isFirefoxAvailable() {
         File file = null;
         if (isWindows2K3Platform()) {
             file = new File("c:\\program files (x86)\\mozilla firefox\\firefox.exe");
             return file.exists();
        }
         else {
             file = new File("c:\\program files\\mozilla firefox\\firefox.exe") ;
             return file.exists();
        }
    }
    
     public static void displayUrlInFirefox(String url) {
         if (isWindows2K3Platform()) {
             displayUrlIn("c:\\program files (x86)\\mozilla firefox\\firefox.exe", url) ;
        }
         else {
             displayUrlIn("c:\\program files\\mozilla firefox\\firefox.exe", url) ;  
        }
    }
    
// TODO: Need properties or discovery mechanism for actual location
    /**
     * DO NOT USE
     */
     public static void displayUrlInSafari(String url) { 
         if (isWindows2K3Platform()) {
             displayUrlIn("C:\\Program Files (x86)\\Safari\\Safari.exe", url) ;
        }
         else {
             displayUrlIn("c:\\program files\\Safari\\Safari.exe", url) ;    
        }
    }
    // TODO: Need properties or discovery mechanism for actual location
    /**
     * DO NOT USE
     */ 
     public static void displayUrlInChrome(String url) { 
         displayUrlIn("C:\\Documents and Settings\\mpalaima\\Local Settings\\Application Data\\Google\\Chrome\\Application\\chrome.exe", url) ;
    }
    
    //C:\Safari\Safari.exe
    
    //C:\Documents and Settings\mpalaima\Local Settings\Application Data\Google\Chrome\Application
    
     public static void main(String[] args) {
//      displayContentInFirefox("<data/>", "xml") ;
//      displayUrlInInternetExplorer("file:///C:/DOCUME~1/mpalaima/LOCALS~1/Temp/data2487270131632423758.xml") ;
//      displayUrlInIE("file:///C:/DOCUME~1/mpalaima/LOCALS~1/Temp/data2487270131632423758.xml") ;
        
//      //Firefox
//      displayUrlIn("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe","http://www.ebay.com");
//      //IE
//      displayUrlIn("C:\\Program Files\\Internet Explorer\\iexplore.exe","http://www.ebay.com");
//      //Google Chrome
         displayUrlIn("C:\\Documents and Settings\\zyin\\Local Settings\\Application Data\\Google\\Chrome\\Application\\chrome.exe","http://www.ebay.com");
//      //Safai
//      displayUrlIn("C:\\Program Files (x86)\\Safari\\Safari.exe","http://www.ebay.com");
        //displayUrlIn("C:\\Safari\\Safari.exe", "http://www.ebay.com") ;
//      //opera
//      displayUrlIn("C:\\Program Files (x86)\\Opera\\opera.exe","http://www.ebay.com");
    
    }
    
     public static void displayContentInFirefox(String content, String extension) {
         displayUrlInFirefox(createTempFile(content, extension)) ;
    }
    
     private static String createTempFile(String content, String extension) {
         try {
             File temp = File.createTempFile("data", "." + extension) ;
             FileOutputStream fos = new FileOutputStream(temp) ;
             fos.write(content.getBytes()) ;
             fos.close();
             String p = "file:///" + temp.getAbsolutePath() ;
//          temp.deleteOnExit() ;
             return p ;
        }
         catch(Exception e) {
             throw new RuntimeException(e) ;
        }       
    }
    
    /**
     * Displays the URL in Opera.  A new browser instance is created each
     * time. If you want the same Opera browser to be used, you need to make 
     * Opera your default browser and user the displayUrlInDefault() method.
     * !!! CURRENTLY NOT SUPPORTED !!!
     */
     public static boolean isOperaAvailable() {
         File file = null;
         if (isWindows2K3Platform()) {
             file = new File("c:\\program files (x86)\\opera\\opera.exe");
             return file.exists();
        }
         else {
             file = new File("c:\\program files\\opera\\opera.exe") ;
             return file.exists();
        }
    }
    
     public static void displayContentInOpera(String content, String extension) {
         displayUrlInOpera(createTempFile(content, extension)) ;
    }
     public static void displayUrlInOpera(String url) {
         if (isWindows2K3Platform()) {
             displayUrlIn("c:\\program files (x86)\\opera\\opera.exe", url) ;
        }
         else {
             displayUrlIn("c:\\program files\\opera\\opera.exe", url) ;  
        }
    }   
    
    /**
     * Uses the passed in displayer executable path to display the URL.  This
     */
     public static void displayUrlIn(String displayersExePath, String url) {
         final String cmd[] = {displayersExePath, url} ;
         CommandExecutor.executeCommand(cmd) ;       
    }
    
    /**
     * Weak attempt to display a URL in a system independent way.  It is 
     * just a helper only really useful in Windows enviroments.  We should beef
     * this up to cleanly handle Linux.  We dont' even think about MAC...
     * Once the default browser has been created once, this method will continue
     * to reuse it until you close it.
     */
    
     public static void displayContentInDefault(String content, String extension) {
         displayUrlInDefault(createTempFile(content, extension)) ;
    }
     public static void displayUrlInDefault(String url) {
         String cmd = null;
         try {
             if (BrowserManager.IS_WINDOWS) {
                // cmd = 'rundll32 url.dll,FileProtocolHandler http://...'
                 cmd = WIN_PATH + " " + WIN_FLAG + " " + url;
                 Runtime.getRuntime().exec(cmd);
            }
             else {
                // Under Unix, Netscape has to be running for the "-remote"
                // command to work.  So, we try sending the command and
                // check for an exit value.  If the exit command is 0,
                // it worked, otherwise we need to start the browser.
                // cmd = 'netscape -remote openURL(http://www.ebay.com)'
                 cmd = UNIX_PATH + " " + UNIX_FLAG + "(" + url + ")";
                 Process p = Runtime.getRuntime().exec(cmd);
                 try {
                    // wait for exit code -- if it's 0, command worked,
                    // otherwise we need to start the browser up.
                     int exitCode = p.waitFor();
                     if (exitCode != 0) {
                        // Command failed, start up the browser
                        // cmd = 'netscape http://www.javaworld.com'
                         cmd = UNIX_PATH + " "  + url;
                         p = Runtime.getRuntime().exec(cmd);
                    }
                }
                 catch(InterruptedException x) {
                     err("Error launching browser, cmd='" + cmd + "'");
                     err("Encountered exception: " + x);
                }
            }
        }
         catch(IOException x) {
            // couldn't exec browser
             err("Could not launch browser, command=" + cmd);
             err("Encountered exception: " + x);
        }
    }
    
    //
    // Private
    //
    /**
     * @return true if this application is running under a Windows OS
     */
     private static boolean isWindowsPlatform() {
         final String os = System.getProperty("os.name");
         return os != null && os.startsWith(WIN_ID) ;
    }
    
    /**
     * @return true if this application is on a Windows Server 2003 platform.
     * This allows us to assume we are in 64 bit mode. Ignore the possible
     * case of 32-bit Win2K3 systems.
     */
     private static boolean isWindows2K3Platform() {
         final String os = System.getProperty("os.name");
         return os != null && os.startsWith(WIN2K3_ID) ;
    }
    
     private static void err(String message) {
         System.err.println(message) ;
    }
}