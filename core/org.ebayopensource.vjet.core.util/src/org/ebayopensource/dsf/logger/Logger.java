package org.ebayopensource.dsf.logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Handler;
import java.util.logging.LogManager;

import org.ebayopensource.dsf.logger.LoggingContext.ContextLoggingOption;

import org.ebayopensource.dsf.common.exceptions.BaseException;
import org.ebayopensource.dsf.common.exceptions.BaseRuntimeException;

import org.ebayopensource.dsf.common.initialization.InitializationException;

/**
 * Logger is a class that logs messages. It calls JDK 1.5 logger
 * (java.util.logging.Logger) in to actually perform the logging. This class
 * should be a very thin proxy to the jdk logger. There are some methods not
 * really thin, but they are just left for backward compatibility.
 */
public class Logger {
	public static final String DEFAULT_RESOURCE_NAME = "logging.properties";
	public static final String BATCH_RESOURCE_NAME = "batch-logging.properties";
	public static final String NULL_RESOURCE_NAME = "null-logging.properties";
	public static final String CONSOLE_LOGGING_NAME = "console-logging.properties";
	private static String s_resourceName = DEFAULT_RESOURCE_NAME;

	// Loggers are kept in list - start with a reasonable number
	private static final ConcurrentMap<String, Logger> s_loggerList = new ConcurrentHashMap<String, Logger>(
			25);

	// JDK logger
	private final java.util.logging.Logger m_logger;

	// Flag that allows to initialize static data based on configuration once
	private static boolean s_initialized = false;

	/**
	 * Batch apps and unit tests are using this
	 */
	public static synchronized void initLogProperties(String name) {
		if (name == null || s_resourceName.equals(name)) {
			return;
		}

		s_resourceName = name;

		// the file name has changed, let's re-initialize
		if (s_initialized) {
			s_initialized = false;

			try {
				initConfig();
			} catch (Exception e) {
				throw new InitializationException(e);
			}
		}
	}

	/**
	 * Returns an instance of the logger for the given class.
	 * 
	 * @param Class
	 *            callingClass - The class that invoked the logger.
	 */
	public static Logger getInstance(Class<?> callingClass) {
		// We want 1 logger per subsystem
		String subsystem = null;
		if (callingClass != null) {
			if (callingClass.getPackage() != null) {
				subsystem = callingClass.getPackage().getName();
			} else {
				subsystem = callingClass.getName();
			}
		} else {
			subsystem = "";
		}

		return getInstance(subsystem);
	}

	/**
	 * Returns a logger instance for the given subsystem.
	 * 
	 * @param subsystem
	 *            The name of the subsystem for which the logger is being
	 *            instantiated.
	 */
	public static Logger getInstance(String subsystem) {
		Logger logger = s_loggerList.get(subsystem);
		if (logger == null) {
			Logger newLogger = new Logger(subsystem);
			// Create new logger and add it to the loggerList
			logger = s_loggerList.putIfAbsent(subsystem, newLogger);
			if (logger == null) {
				logger = newLogger;
			}
		}
		return logger;
	}

	/**
	 * The constructor is private because we don't want the developer creating
	 * new logger instances for the same subsystem. Instead, an instance can be
	 * accessed using the getInstance() method.
	 * 
	 * @param String
	 *            subsystem - The class that invoked the logger.
	 */
	private Logger(String subsystem) {
		// If logger hasn't been initialized by initialize method,
		// we use a default property file to initialize the logger here.
		try {
			initConfig();
		} catch (Exception e) {
			System.err.println(this.getClass().getName() + " : init error - "
					+ e.getMessage()); // KEEPME
			e.printStackTrace(); // KEEPME
		}

		m_logger = java.util.logging.Logger.getLogger(subsystem);
	}

	/**
	 * Initialize any static fields based on configuration
	 */
	protected static synchronized void initConfig() throws IOException {
		if (s_initialized) {
			return; // already done init
		}

		s_initialized = true;

		LogManager manager = LogManager.getLogManager();

		// JDK logger uses two system properties
		// "java.util.logging.config.class"
		// and "java.util.logging.config.file" to find logging.properties.
		// If one of them are defined, they have precedence.
		String cname = System.getProperty("java.util.logging.config.class");
		String fname = System.getProperty("java.util.logging.config.file");
		if (cname != null || fname != null) {
			manager.readConfiguration();
			return;
		}

		// // If above two system properties are not defined, kernel logger will
		// find
		// // logging.properties in the normal location
		// InputStream resourceStream = ResourceUtil.getResourceAsStream(
		// ConfigurationConstants.CONFIG_DIR, s_resourceName);
		// Collection<String> ebayHandlerNames = new ArrayList<String>();
		// InputStream resourceStream2;
		// try {
		// resourceStream2 = filterProperties(resourceStream, ebayHandlerNames);
		// } finally {
		// if (resourceStream != null) {
		// resourceStream.close();
		// }
		// }
		//
		// try {
		// initConfig(resourceStream2, ebayHandlerNames);
		// } finally {
		// resourceStream2.close();
		// }
	}

	static synchronized void reinit(Properties props) throws IOException {
		s_initialized = true;

		Collection<String> ebayHandlerNames = new ArrayList<String>();
		InputStream is = filterProperties(props, ebayHandlerNames);

		try {
			initConfig(is, ebayHandlerNames);
		} finally {
			is.close();
		}
	}

	private static void initConfig(InputStream is,
			Collection<String> ebayHandlerNames) throws IOException {
		LogManager manager = LogManager.getLogManager();
		manager.readConfiguration(is);

		if (!ebayHandlerNames.isEmpty()) {
			java.util.logging.Logger root = manager.getLogger("");
			if (root == null) {
				throw new IOException(
						"Unthinkable: Root logger is not available");
			}

			for (String handlerName : ebayHandlerNames) {
				try {
					Handler handler = (Handler) Class.forName(handlerName)
							.newInstance();
					root.addHandler(handler);
				} catch (Exception e) {
					e.printStackTrace(); // KEEPME
				}
			}
		}
	}

	private static InputStream filterProperties(InputStream is,
			Collection<String> ebayHandlerNames) throws IOException {
		Properties props = new Properties();
		if (is != null) {
			props.load(is);
		}

		return filterProperties(props, ebayHandlerNames);
	}

	private static InputStream filterProperties(Properties props,
			Collection<String> ebayHandlerNames) throws IOException {
		Properties props2 = new Properties();

		// update the class names

		// remove any eBay handlers from the list as they cannot be loaded
		// in IBM's version of JDK logger due to wrong ClassLoader
		String handlersStr = props2.getProperty("handlers");
		if (handlersStr != null && handlersStr.trim().length() != 0) {
			StringBuilder sb = new StringBuilder(handlersStr.length());
			String[] handlers = parseClassNames(handlersStr);
			for (int i = 0; i < handlers.length; i++) {
				String handler = handlers[i];
				if (handler.startsWith("org.ebayopensource.dsf.logger")) {
					ebayHandlerNames.add(handler);
					continue;
				}

				if (sb.length() != 0) {
					sb.append(' ');
				}
				sb.append(handler);
			}

			props2.setProperty("handlers", sb.toString());
		} else {
			// no handlers at all... add eBay's standard one
			ebayHandlerNames.add(EbayLogFileHandler.class.getName());
		}

		// add level if it was not there
		String levelStr = props2.getProperty(".level");
		if (levelStr == null || levelStr.trim().length() == 0) {
			props2.setProperty(".level", "WARNING");
		}

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		props2.store(os, null);
		byte[] data = os.toByteArray();

		return new ByteArrayInputStream(data);
	}

	private static String[] parseClassNames(String hands) {
		if (hands == null) {
			return new String[0];
		}

		hands = hands.trim();
		int ix = 0;
		Vector<String> result = new Vector<String>();
		while (ix < hands.length()) {
			int end = ix;
			while (end < hands.length()) {
				if (Character.isWhitespace(hands.charAt(end))) {
					break;
				}
				if (hands.charAt(end) == ',') {
					break;
				}
				end++;
			}
			String word = hands.substring(ix, end);
			ix = end + 1;
			word = word.trim();
			if (word.length() == 0) {
				continue;
			}
			result.add(word);
		}
		return result.toArray(new String[result.size()]);
	}

	/**
	 * Specify whether or not this logger should send its output to it's parent
	 * Logger. This means that any LogRecords will also be written to the
	 * parent's Handlers, and potentially to its parent, recursively up the
	 * namespace.
	 * 
	 * @param useParentHandlers
	 *            true if output is to be sent to the logger's parent.
	 * @exception SecurityException
	 *                if a security manager exists and if the caller does not
	 *                have LoggingPermission("control").
	 */
	public synchronized void setUseParentHandlers(boolean useParentHandlers) {
		m_logger.setUseParentHandlers(useParentHandlers);
	}

	/**
	 * Discover whether or not this logger is sending its output to its parent
	 * logger.
	 * 
	 * @return true if output is to be sent to the logger's parent
	 */
	public synchronized boolean getUseParentHandlers() {
		return m_logger.getUseParentHandlers();
	}

	/**
	 * Check if debug logging is enabled.
	 * 
	 * @return boolean - true if debug logging is enabled.
	 */
	public boolean isDebugEnabled() {
		return m_logger.isLoggable(LogLevel.DEBUG.getLevel());
	}

	/**
	 * Check if info logging is enabled.
	 * 
	 * @return boolean - true if info logging is enabled.
	 */
	public boolean isInfoEnabled() {
		return m_logger.isLoggable(LogLevel.INFO.getLevel());
	}

	/**
	 * Check if logging is enabled.
	 * 
	 * @return boolean - true if either Log4j or CAL logging is enabled.
	 */
	public boolean isLogEnabled(LogLevel logLevel) {
		if (logLevel == null) {
			return false;
		}
		// Check if logging is enabled.
		if (m_logger.isLoggable(logLevel.getLevel())) {
			return true;
		}
		return false;
	}

	/**
	 * Log the given message at a given level: dhe added for log English msg,
	 * such as system error msg
	 * 
	 * @param int logLevel - From com.ebay.LogLevel.
	 * @param String
	 *            message - Given message to log.
	 */
	public void log(LogLevel logLevel, String message) {
		if (!isLogEnabled(logLevel)) {
			return;
		}
		m_logger.log(logLevel.getLevel(), message);
	}

	public void log(LogLevel logLevel, String message, String statusString) {
		if (!isLogEnabled(logLevel)) {
			return;
		}

		m_logger.log(logLevel.getLevel(), message);
	}

	public void log(LogLevel logLevel, String message, Object[] messageArgs) {
		if (!isLogEnabled(logLevel)) {
			return;
		}
		m_logger.log(logLevel.getLevel(), message, messageArgs);
	}

	public void log(LogLevel logLevel, String message, Object[] messageArgs,
			boolean includeContextInfo) {
		if (!isLogEnabled(logLevel)) {
			return;
		}
		// Can't pass CAL status string to CALLoggerAdapter via JDK logger.
		// Pass it thru the LoggingContext which is thread local
		ContextLoggingOption current = LoggingContext.getContextLoggingOption();
		try {
			LoggingContext
					.setContextLoggingOption(includeContextInfo ? ContextLoggingOption.ON
							: ContextLoggingOption.OFF);
			m_logger.log(logLevel.getLevel(), message, messageArgs);
		} finally {
			LoggingContext.setContextLoggingOption(current);
		}
	}

	public void log(LogLevel logLevel, String message, Object[] messageArgs,
			Throwable t) {
		if (!isLogEnabled(logLevel)) {
			return;
		}
		// JDK logger doesn't have the same method to delegate.
		// We need to format the message with args. So we need to
		// check log level at here
		final String stringMessage = MessageFormat.format(message, messageArgs);
		m_logger.log(logLevel.getLevel(), stringMessage, t);
	}

	public void log(LogLevel logLevel, String stringMessage, Throwable t) {
		if (!isLogEnabled(logLevel)) {
			return;
		}
		m_logger.log(logLevel.getLevel(), stringMessage, t);
	}

	/**
	 * Support 'Turn off context logging'. This method can be used when Context
	 * contains sensitive parameters that should not be logged as is to CAL.
	 * 
	 * @author mons BUGDB00638313
	 */
	public void log(LogLevel logLevel, String stringMessage, Throwable t,
			boolean includeContextInfo) {
		if (!isLogEnabled(logLevel)) {
			return;
		}

		ContextLoggingOption current = LoggingContext.getContextLoggingOption();
		try {
			LoggingContext
					.setContextLoggingOption(includeContextInfo ? ContextLoggingOption.ON
							: ContextLoggingOption.OFF);
			m_logger.log(logLevel.getLevel(), stringMessage, t);
		} finally {
			LoggingContext.setContextLoggingOption(current);
		}
	}

	/**
	 * Log the given message at a given level: dhe added for log English msg,
	 * such as system error msg
	 * 
	 * @param int logLevel - From com.ebay.LogLevel.
	 * @param String
	 *            message - Given message to log.
	 * @param boolean includeContextInfo - Indicate whether context info should
	 *        be part of the log or not
	 */
	public void log(LogLevel logLevel, String message,
			boolean includeContextInfo) {
		if (!isLogEnabled(logLevel)) {
			return;
		}
		// Can't pass includeContextInfo to Formatter via JDK logger.
		// Set it to the LoggingContext which is thread local
		ContextLoggingOption current = LoggingContext.getContextLoggingOption();
		try {
			LoggingContext
					.setContextLoggingOption(includeContextInfo ? ContextLoggingOption.ON
							: ContextLoggingOption.OFF);
			m_logger.log(logLevel.getLevel(), message);
		} finally {
			LoggingContext.setContextLoggingOption(current);
		}
	}

	public void log(LogLevel logLevel, String message,
			boolean includeContextInfo, String statusString) {
		if (!isLogEnabled(logLevel)) {
			return;
		}
		// Can't pass CAL status string to CALLoggerAdapter via JDK logger.
		// Can't pass includeContextInfo to Formatter via JDK logger.
		// Pass them thru the LoggingContext which is thread local
		ContextLoggingOption current = LoggingContext.getContextLoggingOption();
		try {
			LoggingContext
					.setContextLoggingOption(includeContextInfo ? ContextLoggingOption.ON
							: ContextLoggingOption.OFF);
			LoggingContext.setCalStatus(statusString);
			m_logger.log(logLevel.getLevel(), message);
		} finally {
			LoggingContext.setContextLoggingOption(current);
			LoggingContext.setCalStatus(null);
		}
	}

	public void log(LogLevel logLevel, String message, Throwable e,
			String calSourceName) {
		if (!isLogEnabled(logLevel)) {
			return;
		}
		// Can't pass CAL calEventName to CALLoggerAdapter via JDK logger.
		// Pass it thru the LoggingContext which is thread local
		try {
			LoggingContext.setSourceName(calSourceName);
			m_logger.log(logLevel.getLevel(), message, e);
		} finally {
			LoggingContext.setSourceName(null);
		}
	}

	/**
	 * Log at a given level and with a throwable.
	 * 
	 * @param int logLevel - From com.ebay.LogLevel.
	 * @param Throwable
	 *            t - Given exception.
	 */
	public void log(LogLevel logLevel, Throwable t) {
		if (!isLogEnabled(logLevel)) {
			return;
		}
		m_logger.log(logLevel.getLevel(), "", t);
	}

	/**
	 * Logs the given Throwable with severity LogLevel.FATAL if it is an
	 * instance of RuntimeException or Error. Otherwise, logs with
	 * LogLevel.ERROR.
	 * 
	 * @param t
	 *            Given exception
	 */
	public void log(Throwable t) {
		if (t instanceof BaseException) {
			log(LogLevel.ERROR, t);
			return;
		}
		if (t instanceof BaseRuntimeException) {
			log(LogLevel.FATAL, t);
			return;
		}
		if (t instanceof RuntimeException) {
			log(LogLevel.FATAL, t);
			return;
		}
		if (t instanceof Error) {
			log(LogLevel.FATAL, t);
			return;
		}

		// for others
		log(LogLevel.ERROR, t);
	}

	/**
	 * Log the given debugging message.
	 * 
	 * @param String
	 *            message - Given message to log.
	 */
	public void debug(String message) {
		log(LogLevel.FINE, message);
	}

	/**
	 * Log the given message at fine level
	 * 
	 * @param String
	 *            message - Given message to log.
	 */
	public void fine(String message) {
		log(LogLevel.FINE, message, true);
	}

	/**
	 * Log the given message at finer level
	 * 
	 * @param String
	 *            message - Given message to log.
	 */
	public void finer(String message) {
		log(LogLevel.FINER, message, true);
	}

	/**
	 * Log the given message at finest level
	 * 
	 * @param String
	 *            message - Given message to log.
	 */
	public void finest(String message) {
		log(LogLevel.FINEST, message, true);
	}

	/**
	 * Set the log level specifying which message levels will be logged by this
	 * logger. Message levels lower than this value will be discarded.
	 * <p>
	 * If the new level is null, it means that this node should inherit its
	 * level from its nearest ancestor with a specific (non-null) level value.
	 * 
	 * @param int logLevel - From com.ebay.LogLevel.
	 * @exception SecurityException
	 *                if a security manager exists and if the caller does not
	 *                have LoggingPermission("control").
	 */
	public void setLevel(final LogLevel logLevel) throws SecurityException {
		m_logger.setLevel(logLevel.getLevel());
	}

	/**
	 * Add a log Handler to receive logging messages.
	 * 
	 * @param handler
	 *            a logging Handler
	 * @exception SecurityException
	 *                if a security manager exists and if the caller does not
	 *                have LoggingPermission("control").
	 */
	public void addHandler(Handler handler) throws SecurityException {
		m_logger.addHandler(handler);
	}

	/**
	 * Remove a log Handler.
	 * <P>
	 * Returns silently if the given Handler is not found.
	 * 
	 * @param handler
	 *            a logging Handler
	 * @exception SecurityException
	 *                if a security manager exists and if the caller does not
	 *                have LoggingPermission("control").
	 */
	public void removeHandler(Handler handler) {
		m_logger.removeHandler(handler);
	}

}