package org.ebayopensource.dsf.common;
  
  /**
   * ANY CHANGES HERE SHOULD BE COPIED TO ESF PROJECT Z.JAVA FILE WHICH IS A
   * COPY OF THIS.  THE COPY WAS NEEDED TO MINIMIZE DEPENDENCIES.
   */
  public final class Z {
      public static final String NL = System.getProperty("line.separator") ;
      
      private StringBuilder m_buffer ;
      
      //
      // Constructor(s)
      //
      public Z() {
          m_buffer = new StringBuilder() ;
      }
      
      public Z(Object o) {
          this() ;
          m_buffer.append(o.toString()) ;
      }
      
      public Z(StringBuilder buffer) {
          m_buffer = buffer ;
      }
      
      //
      // API
      //
      /** Just adds value to output buffer but does not add a NL */
      public void append(Object o) {
          m_buffer.append(o.toString()) ;
      }
      
      /** Same as append(o.toString(), append(NL) */
      public void format(Object o) {
          m_buffer.append(o.toString()) ;
          m_buffer.append(NL) ;
      }
      
      public void format(String label, Object value) {
          m_buffer.append(label + ": " + value + NL) ;
      }
      
      public void format(String label, int value) {
          m_buffer.append(label + ": " + value + NL) ;
      }
  
      public void format(String label, long value) {
          m_buffer.append(label + ": " + value + NL) ;
      }
  
      public void format(String label, double value) {
          m_buffer.append(label + ": " + value + NL) ;
      }
              
      public void format(String label, boolean value) {
          m_buffer.append(label + ": " + value + NL) ;
      }
      
      public StringBuilder getBuffer() {
          return m_buffer ;
      }
      
      //
      // Overrides from Object
      //
      public String toString() {
          return m_buffer.toString() ;
      }
      
      //
      // Statics
      //
      public static String fmt(String label, Object value) {
          return label + ": " + value + NL ;
      }
      
      public static String fmt(String label, int value) {
          return label + ": " + value + NL ;
      }
  
      public static String fmt(String label, long value) {
          return label + ": " + value + NL ;
      }
  
      public static String fmt(String label, double value) {
          return label + ": " + value + NL ;
      }
              
      public static String fmt(String label, boolean value) {
          return label + ": " + value + NL ;
      }
      
      public static class grp{
          public static String fmt(Object value) {
              if (value==null){ return null; }
              return value.toString() ;
          }
          public static String fmt(int value) {
              return ""+value ;
          }
          public static String fmt(double value) {
              return ""+value ;
          }
          public static String fmt(boolean value) {
              return ""+value ;
          }
          
          public static String fmt(String label, Object value) {
              return label + ": " + value ;
          }
          public static String fmt(String label, int value) {
              return label + ": " + value ;
          }
          public static String fmt(String label, double value) {
              return label + ": " + value ;
          }
          public static String fmt(String label, boolean value) {
              return label + ": " + value ;
          }
      }
  }