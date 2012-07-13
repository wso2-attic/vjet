/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace.handler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Properties;
import java.util.logging.ErrorManager;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

import org.ebayopensource.dsf.logger.EbayLogFileHandler;



public class TraceFileHandler extends StreamHandler {
	
	private MeteredStream meter;
	private boolean append;
	private int limit; // zero => no limit.
	private int count;
	private String pattern;

	//private String lockFileName;
	//private FileOutputStream lockStream;
	private File[] files;

	//private static final int MAX_LOCKS = 100;
	//private static java.util.HashMap locks = new java.util.HashMap();
	// A metered stream is a subclass of OutputStream that
	//   (a) forwards all its output to a target stream
	//   (b) keeps track of how many bytes have been written
	private static class MeteredStream extends OutputStream {
		OutputStream out;
		int written;

		MeteredStream(OutputStream out, int written) {
			this.out = out;
			this.written = written;
		}
		@Override
		public void write(int b) throws IOException {
			out.write(b);
			written++;
		}
		@Override
		public void write(byte[] buff) throws IOException {
			out.write(buff);
			written += buff.length;
		}
		@Override
		public void write(byte[] buff, int off, int len) throws IOException {
			out.write(buff, off, len);
			written += len;
		}
		@Override
		public void flush() throws IOException {
			out.flush();
		}
		@Override
		public void close() throws IOException {
			out.close();
		}
	}

	private void open(File fname, boolean append) throws IOException {
		int len = 0;

		if (append) {
			len = (int) fname.length();
		}

		FileOutputStream fout = new FileOutputStream(fname.toString(), append);
		BufferedOutputStream bout = new BufferedOutputStream(fout);
		meter = new MeteredStream(bout, len);
		setOutputStream(meter);
	}
	static interface IConfigWrapper {
		String getProperty(String key); 
	}
	static class PropertiesConfigWrapper implements IConfigWrapper {
		final Properties m_properties;
		public PropertiesConfigWrapper(final Properties properties){
			m_properties = properties;
		}
		public String getProperty(String key) {
			return m_properties.getProperty(key);
		}
	}
	// Private method to configure a EbayLogFileHandler from LogManager
	// properties and/or default values as specified in the class
	// javadoc.
	private void configure(IConfigWrapper config) {
		final ConfigHelper cfg = new ConfigHelper(config);
		
		String cname = EbayLogFileHandler.class.getName();

		pattern = cfg.getStringProperty(cname + ".pattern", "vjet.log");
		
		limit = cfg.getIntProperty(cname + ".limit", 0);
		if (limit < 0) {
			limit = 0;
		}

		count = cfg.getIntProperty(cname + ".count", 1);
		if (count <= 0) {
			count = 1;
		}

		append = cfg.getBooleanProperty(cname + ".append", false);
		setLevel(cfg.getLevelProperty(cname + ".level", Level.ALL));
		setFilter(cfg.getFilterProperty(cname + ".filter", null));
		
		// This fallback to EbayLogFormatter is very important for
		// the case when the formatter class is not specified. This
		// allows us to skip loading the class from System Classloader
		// that prevent putting this into the app server easily.
//		Formatter formatter = cfg.getFormatterProperty(
//					cname + ".formatter",
//					new EbayLogFormatter());
//					
//		// If specified formatter is instance of EbayLogFormatter,
//		// check if we have a property for formatter layout pattern.
//		if (formatter instanceof EbayLogFormatter) {
//			String layoutpattern = cfg.getStringProperty(cname + ".formatter.layoutpattern", null);
//			if (layoutpattern != null) {
//				EbayLogFormatter f = (EbayLogFormatter) formatter;
//				f.setPattern(layoutpattern);
//			}
//		}
		
		// Set the output formatter
//		setFormatter(formatter);
				
		try {
			setEncoding(cfg.getStringProperty(cname + ".encoding", null));
		} catch (Exception ex) {
			try {
				setEncoding(null);
			} catch (Exception ex2) {
				// doing a setEncoding with null should always work.
				// assert false;
			} // TODO - NOPMD - EmptyCatchBlock - This line created by PMD fixer.
		}
	}

	// This is not a complete constructor. It does not call openFiles.
	// Its callers do.
//	private TraceFileHandler(LogManager manager) {
//		manager.checkAccess();
//		configure(new ConfigWrapper.LogManagerWrapper(manager));
//	}

	/**
	 * Creates a handler with the given configuration. Note that
	 * we broke the fallacious dependency from handler to LogManager.
	 * This kind of circular dependencies make for insecure and hard
	 * to maintain systems.  There is also no manager.checkAccess() here
	 * because this constructor does not touch anything in the logging
	 * subsystem and will not have any security ramifications.
	 * 
	 * @param cfg configuration for this wrapper
	 */
	public TraceFileHandler(Properties props) throws IOException {
		configure(new PropertiesConfigWrapper(props));
		openFiles();
	}

	/**
	 * Construct a default <tt>EbayLogFileHandler</tt>.  This will be configured
	 * entirely from <tt>LogManager</tt> properties (or their default values).
	 * <p>
	 * @exception  IOException if there are IO problems opening the files.
	 * @exception  SecurityException  if a security manager exists and if
	 *             the caller does not have <tt>LoggingPermission("control"))</tt>.
	 */
	public TraceFileHandler() throws IOException, SecurityException {
		openFiles();
	}

	/**
	 * Initialize a <tt>EbayLogFileHandler</tt> to write to the given filename.
	 * <p>
	 * The <tt>EbayLogFileHandler</tt> is configured based on <tt>LogManager</tt>
	 * properties (or their default values) except that the given pattern 
	 * argument is used as the filename pattern, the file limit is
	 * set to no limit, and the file count is set to one.
	 * <p>
	 * There is no limit on the amount of data that may be written,
	 * so use this with care.
	 *
	 * @param pattern  the name of the output file
	 * @exception  IOException if there are IO problems opening the files.
	 * @exception  SecurityException  if a security manager exists and if
	 *             the caller does not have <tt>LoggingPermission("control")</tt>.
	 */
	public TraceFileHandler(String pattern) throws IOException, SecurityException {
		this.pattern = pattern;
		this.limit = 0;
		this.count = 1;
		openFiles();
	}

	/**
	 * Initialize a <tt>EbayLogFileHandler</tt> to write to the given filename,
	 * with optional append.
	 * <p>
	 * The <tt>EbayLogFileHandler</tt> is configured based on <tt>LogManager</tt>
	 * properties (or their default values) except that the given pattern 
	 * argument is used as the filename pattern, the file limit is
	 * set to no limit, the file count is set to one, and the append
	 * mode is set to the given <tt>append</tt> argument.
	 * <p>
	 * There is no limit on the amount of data that may be written,
	 * so use this with care.
	 *
	 * @param pattern  the name of the output file
	 * @param append  specifies append mode
	 * @exception  IOException if there are IO problems opening the files.
	 * @exception  SecurityException  if a security manager exists and if
	 *             the caller does not have <tt>LoggingPermission("control")</tt>.
	 */
	public TraceFileHandler(String pattern, boolean append) throws IOException, SecurityException {
		this.pattern = pattern;
		this.limit = 0;
		this.count = 1;
		this.append = append;
		openFiles();
	}

	/**
	 * Initialize a <tt>EbayLogFileHandler</tt> to write to a set of files.  When
	 * (approximately) the given limit has been written to one file,
	 * another file will be opened.  The output will cycle through a set
	 * of count files.
	 * <p>
	 * The <tt>EbayLogFileHandler</tt> is configured based on <tt>LogManager</tt>
	 * properties (or their default values) except that the given pattern 
	 * argument is used as the filename pattern, the file limit is
	 * set to the limit argument, and the file count is set to the
	 * given count argument.
	 * <p>
	 * The count must be at least 1.
	 *
	 * @param pattern  the pattern for naming the output file
	 * @param limit  the maximum number of bytes to write to any one file
	 * @param count  the number of files to use
	 * @exception  IOException if there are IO problems opening the files.
	 * @exception  SecurityException  if a security manager exists and if
	 *             the caller does not have <tt>LoggingPermission("control")</tt>.
	 * @exception IllegalArgumentException if limit < 0, or count < 1.
	 */
	public TraceFileHandler(String pattern, int limit, int count) throws IOException, SecurityException {
		if ((limit < 0) || (count < 1)) {
			throw new IllegalArgumentException();
		}
	
		this.pattern = pattern;
		this.limit = limit;
		this.count = count;
		openFiles();
	}

	/**
	 * Initialize a <tt>EbayLogFileHandler</tt> to write to a set of files
	 * with optional append.  When (approximately) the given limit has
	 * been written to one file, another file will be opened.  The
	 * output will cycle through a set of count files.
	 * <p>
	 * The <tt>EbayLogFileHandler</tt> is configured based on <tt>LogManager</tt>
	 * properties (or their default values) except that the given pattern 
	 * argument is used as the filename pattern, the file limit is
	 * set to the limit argument, and the file count is set to the
	 * given count argument, and the append mode is set to the given
	 * <tt>append</tt> argument.
	 * <p>
	 * The count must be at least 1.
	 *
	 * @param pattern  the pattern for naming the output file
	 * @param limit  the maximum number of bytes to write to any one file
	 * @param count  the number of files to use
	 * @param append  specifies append mode
	 * @exception  IOException if there are IO problems opening the files.
	 * @exception  SecurityException  if a security manager exists and if
	 *             the caller does not have <tt>LoggingPermission("control")</tt>.
	 * @exception IllegalArgumentException if limit < 0, or count < 1.
	 *
	 */
	public TraceFileHandler(String pattern, int limit, int count, boolean append) throws IOException, SecurityException {
		if ((limit < 0) || (count < 1)) {
			throw new IllegalArgumentException();
		}
	
		this.pattern = pattern;
		this.limit = limit;
		this.count = count;
		this.append = append;
		openFiles();
	}

	// Private method to open the set of output files, based on the
	// configured instance variables.
	private void openFiles() throws IOException {
		if (count < 1) {
			throw new IllegalArgumentException("file count = " + count);
		}

		if (limit < 0) {
			limit = 0;
		}

		// We register our own ErrorManager during initialization
		// so we can record exceptions.
		InitializationErrorManager em = new InitializationErrorManager();
		setErrorManager(em);

		int unique = 0;	// HZ: set to zero since we don't support lock file logic above.
		files = new File[count];

		for (int i = 0; i < count; i++) {
			files[i] = generate(pattern, i, unique);
		}

		// Create the initial log file.
		if (append) {
			open(files[0], true);
		} else {
			rotate();
		}

		// Did we detect any exceptions during initialization?
		Exception ex = em.lastException;

		if (ex != null) {
			if (ex instanceof IOException) {
				throw (IOException) ex;
			} else if (ex instanceof SecurityException) {
				throw (SecurityException) ex;
			} else {
				throw new IOException("Exception: " + ex);
			}
		}

		// Install the normal default ErrorManager.
		setErrorManager(new ErrorManager());
	}

	// Generate a filename from a pattern.
	private File generate(String pattern, int generation, int unique) {
		File file = null;
		String word = "";
		int ix = 0;
		String logDir = System.getProperty("com.ebay.log.dir");

		if (logDir != null) {
			file = new File(logDir);
		}
		
		while (ix < pattern.length()) {
			char ch = pattern.charAt(ix);
			ix++;

			char ch2 = 0;

			if (ix < pattern.length()) {
				ch2 = Character.toLowerCase(pattern.charAt(ix));
			}

			if (ch == '/') {
				if (file == null) {
					file = new File(word);
				} else {
					file = new File(file, word);
				}

				word = "";

				continue;
			} else if (ch == '.'){
				if (generation > 0){
					word = word + "_" + generation + ch;
					continue;
				}
			} else if (ch == '%') {
				if (ch2 == 't') {
					String tmpDir = System.getProperty("java.io.tmpdir");

					if (tmpDir == null) {
						tmpDir = System.getProperty("user.home");
					}

					file = new File(tmpDir);
					ix++;
					word = "";

					continue;
				} else if (ch2 == 'h') {
					file = new File(System.getProperty("user.home"));

					/* isSetUID() native method may not be available in JDK 1.3.1 */

					//if (isSetUID()) {
						// Ok, we are in a set UID program.  For safety's sake
						// we disallow attempts to open files relative to %h.
					//	throw new IOException("can't use %h in set UID program");
					//}
					ix++;
					word = "";

					continue;
				} else if (ch2 == 'g') {
					word = word + generation;
					ix++;

					continue;
				} else if (ch2 == 'u') {
					word = word + unique;
					ix++;

					continue;
				} else if (ch2 == '%') {
					word = word + "%";
					ix++;

					continue;
				}
			}

			word = word + ch;
		}

//		if ((count > 1) && !sawg && (generation > 0)) {
//			word = word + "." + generation;
//		}
//
//		if ((unique > 0) && !sawu) {
//			word = word + "." + unique;
//		}

		if (word.length() > 0) {
			if (file == null) {
				file = new File(word);
			} else {
				file = new File(file, word);
			}
		}

		return file;
	}

	// Rotate the set of output files
	private synchronized void rotate() {
		Level oldLevel = getLevel();
		setLevel(Level.OFF);

		super.close();

		for (int i = count - 2; i >= 0; i--) {
			File f1 = files[i];
			File f2 = files[i + 1];

			if (f1.exists() && f1.length() > 0) {
				if (f2.exists()) {
					f2.delete();
				}
				
				if (!f1.renameTo(f2)) {
					System.err.println("Error renaming file " + f1.getName() + " to " + f2.getName());
				}
			}
		}

		try {
			open(files[0], false);
		} catch (IOException ix) {
			// We don't want to throw an exception here, but we
			// report the exception to any registered ErrorManager.
			reportError(null, ix, ErrorManager.OPEN_FAILURE);
		}

		setLevel(oldLevel);
	}

	/**
	 * Format and publish a <tt>LogRecord</tt>.
	 *
	 * @param  record  description of the log event
	 */
	@Override
	public synchronized void publish(LogRecord record) {
//		if (!isLoggable(record)) {
//			return;
//		}

//		if ((limit > 0) && (meter.written >= limit)) {
			// We performed access checks in the "init" method to make sure
			// we are only initialized from trusted code.  So we assume
			// it is OK to write the target files, even if we are
			// currently being called from untrusted code.
			// So it is safe to raise privilege here.
			AccessController.doPrivileged(new PrivilegedAction<Object>() {
				public Object run() {
					rotate();

					return null;
				}
			});
//		}
			
		super.publish(record);
		flush();
	}

	/**
	 * Close all the files.
	 *
	 * @exception  SecurityException  if a security manager exists and if
	 *             the caller does not have <tt>LoggingPermission("control")</tt>.
	 */
	@Override
	public synchronized void close() throws SecurityException {
		super.close();
	}

	private static class InitializationErrorManager extends ErrorManager {
		Exception lastException;
		@Override
		public void error(String msg, Exception ex, int code) {
			lastException = ex;
		}
	}
	
	// Provides convenience methods given key/value pairs from ConfigWrapper.
	private static class ConfigHelper {	
		final private IConfigWrapper m_cfg;

		public ConfigHelper(IConfigWrapper cfg) {
			m_cfg = cfg;
		}

		// Private method to get a String property.
		// If the property is not defined we return the given
		// default value.
		String getStringProperty(String name, String defaultValue) {
			String val = m_cfg.getProperty(name);
			if (val == null) {
				return defaultValue;
			}
	
			return val.trim();
		}
	
		// Private method to get an integer property.
		// If the property is not defined or cannot be parsed
		// we return the given default value.
		int getIntProperty(String name, int defaultValue) {
			String val = m_cfg.getProperty(name);
			if (val == null) {
				return defaultValue;
			}
	
			try {
				return Integer.parseInt(val.trim());
			} catch (Exception ex) {
				return defaultValue;
			}
		}
	
		// Private method to get a boolean property.
		// If the property is not defined or cannot be parsed
		// we return the given default value.
		boolean getBooleanProperty(String name, boolean defaultValue) {
			String val = m_cfg.getProperty(name);
			if (val == null) {
				return defaultValue;
			}
	
			val = val.toLowerCase();
	
			if (val.equals("true") || val.equals("1")) {
				return true;
			} else if (val.equals("false") || val.equals("0")) {
				return false;
			}
	
			return defaultValue;
		}
	
		// Private method to get a filter property.
		// We return an instance of the class named by the "name" 
		// property. If the property is not defined or has problems
		// we return the defaultValue.
		Filter getFilterProperty(String name, Filter defaultValue) {
			String val = m_cfg.getProperty(name);
			try {
				if (val != null) {
					Class clz = ClassLoader.getSystemClassLoader().loadClass(val);
	
					return (Filter) clz.newInstance();
				}
			} catch (Exception ex) {
				// We got one of a variety of exceptions in creating the
				// class or creating an instance.
				// Drop through.
			} // TODO - NOPMD - EmptyCatchBlock - This line created by PMD fixer.
	
			// We got an exception.  Return the defaultValue.
			return defaultValue;
		}

		// Private method to get a formatter property.
		// We return an instance of the class named by the "name" 
		// property. If the property is not defined or has problems
		// we return the defaultValue.
		Formatter getFormatterProperty(String name, Formatter defaultValue) {
			String val = m_cfg.getProperty(name);
			try {
				if (val != null) {
					Class clz = ClassLoader.getSystemClassLoader().loadClass(val);
	
					return (Formatter) clz.newInstance();
				}
			} catch (Exception ex) {
				// We got one of a variety of exceptions in creating the
				// class or creating an instance.
				// Drop through.
			} // TODO - NOPMD - EmptyCatchBlock - This line created by PMD fixer.
	
			// We got an exception.  Return the defaultValue.
			return defaultValue;
		}
	
		// Private method to get a Level property.
		// If the property is not defined or cannot be parsed
		// we return the given default value.
		Level getLevelProperty(String name, Level defaultValue) {
			String val = m_cfg.getProperty(name);
			if (val == null) {
				return defaultValue;
			}
	
			try {
				return Level.parse(val.trim());
			} catch (Exception ex) {
				return defaultValue;
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.ebay.logger.common.EbayLogHandler#getLoggerClass()
	 */
//	public JdkLoggerClass getLoggerClass() {
//		return JdkLoggerClass.JDK_13_LOGGER;
//	}
	
//	public synchronized void setLevel(int newLevel) throws SecurityException {
//		setLevel(LogLevelMapper.getLevel(newLevel));
//	}

	/* HZ: isSetUID() native method may not be available in JDK 1.3.1 */

	// Private native method to check if we are in a set UID program.
	// private static native boolean isSetUID();
}

