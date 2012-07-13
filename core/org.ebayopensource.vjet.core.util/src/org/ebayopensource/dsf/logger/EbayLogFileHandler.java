package org.ebayopensource.dsf.logger;
import java.io.IOException;
import java.util.logging.FileHandler;
  
  public class EbayLogFileHandler extends FileHandler {
      /**
       * JDK LogManager uses this method via reflection
       * 
       * <p>
       * LogManager adds this handler to the root logger if we have the following line in the
       * logging.properties;
       * <pre>
       * handlers = org.ebayopensource.dsf.logger.  EbayLogFileHandler  </pre>
       * 
       * This handler will be added to the root if no other handlers are specified.
       * <p>
       * @throws IOException
       * @throws SecurityException
       */
      public EbayLogFileHandler() throws IOException, SecurityException {
          super();
          
      }
  
      public EbayLogFileHandler(String pattern, boolean append) throws IOException, SecurityException {
          super(getDefaultPattern(pattern), append);
      }
  
      public EbayLogFileHandler(String pattern, int limit, int count, boolean append) throws IOException, SecurityException {
          super(getDefaultPattern(pattern), limit, count, append);
      }
  
      public EbayLogFileHandler(String pattern, int limit, int count) throws IOException, SecurityException {
          super(getDefaultPattern(pattern), limit, count);
      }
      
      protected static String getDefaultPattern(String pattern) {
          if (pattern == null) {
              pattern = "vjet.log";
          }
          String logDir = System.getProperty("org.ebayopensource.vjet.log.dir");
          if (logDir != null) {
              return logDir + "/" + pattern;
          }
  
          return pattern;
      }
      
  
      public EbayLogFileHandler(String pattern) throws IOException, SecurityException {
          super(getDefaultPattern(pattern));
      }
  }