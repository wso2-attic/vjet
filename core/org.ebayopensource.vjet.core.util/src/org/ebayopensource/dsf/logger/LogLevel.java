package org.ebayopensource.dsf.logger;
  
  import java.util.logging.Level;
  
  
  /**
   * Defines the logging levels for org.ebayopensource.dsf.logger.Logger. 
   * <p>
   * The JDK 1.4 levels in descending order are:
   * <ul>
   * <li>SEVERE (highest value)
   * <li>WARNING
   * <li>INFO
   * <li>CONFIG
   * <li>FINE
   * <li>FINER
   * <li>FINEST  (lowest value)
   * </ul>
   */
  
  public enum LogLevel {
      DEFAULT(-1, null),
      DEBUG(0, Level.FINE),                   //500
      INFO(1, Level.INFO),                    //800
      WARN(2, Level.WARNING),                 //900
      ERROR(3, Level.SEVERE),                 //1000
      FATAL(4, EbayLogLevelExtension.FATAL),  //1100
      CONFIG(5, Level.CONFIG),                //700
      FINE(6, Level.FINE),                    //500
      FINER(7, Level.FINER),                  //400
      FINEST(8, Level.FINEST),                //300
      ALL(9, Level.ALL),                      //MIN_VALUE
      OFF(10, Level.OFF);                     //MAX_VALUE
      
      private Level m_level;
      private int m_ebayLevel;
      
      private LogLevel (int ebayLevel, Level level) {
          m_ebayLevel = ebayLevel;
          m_level = level;
      }
      
      public Level getLevel() {
          return m_level;
      }
      
      public int getLevelValue() {
          return m_level.intValue();
      }
  
      public int getEbayLevelValue() {
          return m_ebayLevel;
      }
  
      private static class EbayLogLevelExtension extends Level {
          public final static Level FATAL =
              new EbayLogLevelExtension("FATAL", 1100);
          protected EbayLogLevelExtension(String name, int value) {
              super(name, value, null);
          }
          private static final long serialVersionUID = 9149560934874662806L;
      }

  }