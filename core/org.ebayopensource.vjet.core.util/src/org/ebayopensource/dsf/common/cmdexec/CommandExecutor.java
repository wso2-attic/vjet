package org.ebayopensource.dsf.common.cmdexec;

import org.ebayopensource.dsf.logger.LogLevel;
import org.ebayopensource.dsf.logger.Logger;



public final class CommandExecutor {
	public static void main(String[] args) {
		String cmd[] = {
			"explorer.exe",
			"http://www.ebay.com" 
		};
		executeCommand(cmd) ;
	}
	
	public static final int UNEXPECTED_ERROR = Integer.MIN_VALUE ;
	
	/**
	 * Use this method to execute DOS shell command like dir or rename.
	 */
	public static int executeCommand(String command) {
		out(command) ;

		String osName = System.getProperty("os.name");
		String[] cmd = new String[3];

		if (osName.equals("Windows NT") 
			|| osName.equals("Windows 2000")
			|| osName.equals("Windows Server 2003") 
			|| osName.equals("Windows XP"))
		{
			cmd[0] = "cmd.exe";
			cmd[1] = "/C";
			cmd[2] = command ;
		} else if (osName.equals("Windows 95")) {
			cmd[0] = "command.com";
			cmd[1] = "/C";
			cmd[2] = command ;
		}
		
		return executeCommand(cmd) ;
	}
	
	/**
	 * It is important to note that a single command String passed in will
	 * be broken up by the underlying Runtime.getRuntime().exec(cmd) into a
	 * set of Strings based on spaces in the original command String.  This is
	 * many times will fail.  Looking at the example below we can see that the
	 * executable path has spaces in it and if it along with the URL were passed
	 * as a single String the command would have been "c:\program " which is
	 * certainly not what was exepected.
	 * 
	 * Example:
	 *   String cmd[] = {
	 *       "c:\\program files\\mozilla firefox\\firefox.exe",
	 *       "http://www.ebay.com"
	 *   };
	 *   executeCommand(cmd) ;
	 */
	public static int executeCommand(String[] cmd) {	
		int exitCode = UNEXPECTED_ERROR ;
		try {
			Runtime rt = Runtime.getRuntime();
//			out("Execing " + cmd[0] + " " + cmd[1] + " " + cmd[2]);
			Process proc = rt.exec(cmd);
			// any error message?
			StreamConsumer errorStreamConsumer =
				new StreamConsumer(proc.getErrorStream(), "ERROR");

			// any output?
			StreamConsumer outputStreamConsumer =
				new StreamConsumer(proc.getInputStream(), "OUTPUT");

			// kick them off
			errorStreamConsumer.start();
			outputStreamConsumer.start();

			// any error
			exitCode = proc.waitFor();
			out("ExitValue: " + exitCode);
		}
		catch (Throwable t) {
			Logger.getInstance(CommandExecutor.class).log(LogLevel.WARN, t);
		}
		
		return exitCode ;
	}
	
	private static void out(Object o) {
//		System.out.println(o) ;
	}
}
