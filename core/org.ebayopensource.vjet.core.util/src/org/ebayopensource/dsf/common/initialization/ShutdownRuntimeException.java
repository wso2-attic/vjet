 package org.ebayopensource.dsf.common.initialization;
  
  import java.io.PrintStream;
  import java.io.PrintWriter;
  import java.io.StringWriter;
  import java.util.Collection;
  import java.util.Iterator;
  
  

  public class ShutdownRuntimeException extends RuntimeException {
  
      final Collection m_exceptions;
  
      public ShutdownRuntimeException(final Collection exceptions) {
          super("Shutdown sequence has failed");
          m_exceptions = exceptions;
      }
  
      Collection getExceptions() {
          return m_exceptions;
      }
  
      public void printStackTrace() {
          printStackTrace(System.err);
      }
  
      public void printStackTrace(PrintStream ps) {
          synchronized (ps) {
              ps.println(this);
              String stackTrace = getStackTraceX();
              ps.print(stackTrace);
          }
      }
  
      public void printStackTrace(PrintWriter pw) {
          synchronized (pw) {
              pw.println(this);
              String stackTrace = getStackTraceX();
              pw.print(stackTrace);
          }
      }
  
      private String getStackTraceX() {
          StringBuilder buf = new StringBuilder();
          buf.append("The following shutdown exceptions occured:\n");
  
          int i = 1;
          Iterator iter = m_exceptions.iterator();
          while (iter.hasNext()) {
              RuntimeException re = (RuntimeException)iter.next();
  
              StringWriter buf2 = new StringWriter();
              PrintWriter pout = new PrintWriter(buf2);
              re.printStackTrace(pout);
              pout.flush();
  
              String message = buf2.toString();
              buf.append("Exception # " + i + ":\r\n");
              buf.append(message);
              i++;
          }
          String stackTrace = buf.toString();
          return stackTrace;
      }
  }