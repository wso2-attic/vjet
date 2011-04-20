/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsrunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class BrowserLauncher implements IBrowserLauncher {
	
	private static final String WIN_ID = "Windows";
	private static final String WIN2K3_ID = "Windows Server 2003";
	
	private static final String WIN_PATH = "rundll32";
	private static final String WIN_FLAG = "url.dll,FileProtocolHandler";
	
	private static final String UNIX_PATH = "netscape";
	private static final String UNIX_FLAG = "-remote openURL";
	
	private static final boolean IS_WINDOWS = BrowserLauncher.isWindowsPlatform() ;
	
	private static final BrowserLauncher s_instance = new BrowserLauncher();
	
	public static BrowserLauncher getInstance() {
		return s_instance;
	}
	
	public Process launch(String url, BrowserType type) {
		if(type==null){
			return displayUrlInDefault(url);
		}else if (type.isIE()) {
			return displayUrlInInternetExplorer(url);
		} else if (type.isFireFox()) {
			return displayUrlInFirefox(url);
		} else {
			return displayUrlInDefault(url);
		}
	}
		
	/**
	 * CommandExecutor.executeCommand(cmd) never returns in IE, so do not wait for error codes.
	 * @param url
	 */
	private static Process displayUrlInInternetExplorer(String url) {
		if (isWindows2K3Platform()) {
			return displayUrlIn("c:\\program files (x86)\\Internet Explorer\\iexplore.exe", url);
		}
		else {
			return displayUrlIn("c:\\program files\\Internet Explorer\\iexplore.exe", url);
		}
	}	
	
	
	private static Process displayUrlInFirefox(String url) {
		if (isWindows2K3Platform()) {
			return displayUrlIn("c:\\program files (x86)\\mozilla firefox\\firefox.exe", url) ;
		}
		else {
			return displayUrlIn("c:\\program files\\mozilla firefox\\firefox.exe", url) ;	
		}
	}	
	
	/**
	 * Uses the passed in displayer executable path to display the URL.  This
	 */
	private static Process displayUrlIn(String displayersExePath, String url) {
		final String cmd[] = {displayersExePath, url} ;
		return executeCommand(cmd) ;		
	}
	
	private static Process displayUrlInDefault(String url) {
		String cmd = null;
		try {
			if (IS_WINDOWS) {
				// cmd = 'rundll32 url.dll,FileProtocolHandler http://...'
				cmd = WIN_PATH + " " + WIN_FLAG;
				return displayUrlIn(cmd, url);
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
				return p;
			}
		}
		catch(IOException x) {
			// couldn't exec browser
			err("Could not launch browser, command=" + cmd);
			err("Encountered exception: " + x);
		}
		return null;
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
	
	private static Process executeCommand(String[] cmd) {	
		try {
			Process proc = Runtime.getRuntime().exec(cmd);
			// any error message?
			StreamConsumer errorStreamConsumer =
				new StreamConsumer(proc.getErrorStream());

			// any output?
			StreamConsumer outputStreamConsumer =
				new StreamConsumer(proc.getInputStream());

			// kick them off
			errorStreamConsumer.start();
			outputStreamConsumer.start();
			return proc;
		}
		catch (Throwable t) {
			//TODO
			t.printStackTrace();
		}
		return null;
	}
	
	private static class StreamConsumer extends Thread {
		InputStream m_is;
		OutputStream m_os;

		StreamConsumer(InputStream is) {
			this(is, null);
		}

		StreamConsumer(InputStream is, OutputStream redirect) {
			m_is = is;
			m_os = redirect;
		}

		public void run() {
			try {
				PrintWriter pw = null;
				if (m_os != null) {
					pw = new PrintWriter(m_os);
				}

				InputStreamReader isr = new InputStreamReader(m_is);
				BufferedReader br = new BufferedReader(isr);
				String line = null;
				while ((line = br.readLine()) != null) {
					if (pw != null) {
						pw.println(line);
					}
				}
				if (pw != null) {
					pw.flush();
				}
			}
			catch (IOException ioe) {
				//TODO
			}
		}
	}
}
