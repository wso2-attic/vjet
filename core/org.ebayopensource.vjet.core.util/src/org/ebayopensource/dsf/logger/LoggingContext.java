package org.ebayopensource.dsf.logger;
 public class LoggingContext {
  
      private static final String DEFAULT_CONTEXT = "context is not set";
  
      private static ThreadLocalContext s_context = new ThreadLocalContext();
  
      private LoggingInfo m_primary = null;
      private LoggingInfo m_secondary = null;
      private ErrorsInfo m_errorsInfo = null;
      private String m_lastLoggedMessage = null;
      private String m_calStatus = null;
      private String m_sourceName = null;
      private ContextLoggingOption m_contextLoggingOption = ContextLoggingOption.DEFAULT;
  
      public enum ContextLoggingOption {
          DEFAULT, ON, OFF;
      };
  
      private boolean m_isSuccessCalTransactionTree = true;
  
      private LoggingContext() {
      }
      
      public static void resetContext() {
          LoggingContext loggingContext = getContext();
          loggingContext.m_primary = null;
          loggingContext.m_secondary = null;
          loggingContext.m_errorsInfo = null;
          loggingContext.m_lastLoggedMessage = null;
          loggingContext.m_contextLoggingOption = ContextLoggingOption.DEFAULT;
          loggingContext.m_calStatus = null;
          loggingContext.m_sourceName = null;
          loggingContext.m_isSuccessCalTransactionTree = true;
      }
  
      public static void setPrimary(LoggingInfo info) {
          LoggingContext loggingContext = getContext();
          loggingContext.m_primary = info;
          loggingContext.m_secondary = null;
      }
  
      public static void setSecondary(LoggingInfo info) {
          LoggingContext loggingContext = getContext();
          loggingContext.m_secondary = info;
      }
      
      /** indicates whether the context passed in is the default context;
       * i.e. is not really a context.
       * @param context - String
       * @return true indicates that it is the default one (not really a context
       */
      public static boolean isDefaultContext(final String context) {
          // only in the case that it is the same object is it the default.
          return context == DEFAULT_CONTEXT;
      }
  
      public static String getContextInfo() {
          final LoggingContext loggingContext = getContext();
          if (loggingContext.m_secondary != null) {
              return loggingContext.m_secondary.getContent();
          } else if (loggingContext.m_primary != null) {
              return loggingContext.m_primary.getContent();
          } else {
              return DEFAULT_CONTEXT;
          }
      }
  
      /**
       * 
       * returns the ErrorsInfo
       */ 
      public static ErrorsInfo getErrorsInfo() {
          final LoggingContext loggingContext = getContext();
          if (loggingContext != null) {
              if (loggingContext.m_errorsInfo == null) {
                  loggingContext.m_errorsInfo = new ErrorsInfo();
              }
              return loggingContext.m_errorsInfo;
          }
          return new ErrorsInfo();    
      }
      
      public static void clearErrorsInfo() {
          final LoggingContext loggingContext = getContext();
          if (loggingContext != null) {
              loggingContext.m_errorsInfo = null;
          }
      }
      
      /**
       * returns the primary LoggingInfo
       */
      public static LoggingInfo getPrimary() {
          LoggingInfo loggingInfo = null;
          final LoggingContext loggingContext = getContext();
          if (loggingContext != null) {
              loggingInfo = loggingContext.m_primary;
          }
          return loggingInfo;
      }
  
      
      public static String getLastLoggedMessage() {
          final LoggingContext loggingContext = getContext();
          return loggingContext.m_lastLoggedMessage;
      }
  
      public static void setLastLoggedMessage(String lastLoggedMessage) {
          final LoggingContext loggingContext = getContext();
          loggingContext.m_lastLoggedMessage = lastLoggedMessage;
      }
  
      public static ContextLoggingOption getContextLoggingOption() {
          final LoggingContext loggingContext = getContext();
          return (loggingContext.m_contextLoggingOption);
      }
  
      public static void setContextLoggingOption(ContextLoggingOption state) {
          final LoggingContext loggingContext = getContext();
          loggingContext.m_contextLoggingOption = state;
      }
      
      public static String getCalStatus() {
          final LoggingContext loggingContext = getContext();
          return loggingContext.m_calStatus;
      }
  
      public static void setCalStatus(String calStatus) {
          final LoggingContext loggingContext = getContext();
          loggingContext.m_calStatus = calStatus;
      }
  
      public static String getSourceName() {
          final LoggingContext loggingContext = getContext();
          return loggingContext.m_sourceName;
      }
  
      public static void setSourceName(String sourceName) {
          final LoggingContext loggingContext = getContext();
          loggingContext.m_sourceName = sourceName;
      }
  
      public static void setFailureOnCalTransactionTree() {
          final LoggingContext loggingContext = getContext();
          if (loggingContext != null) {
              loggingContext.m_isSuccessCalTransactionTree = false;;
          }
      }
      
      public static boolean isSuccessCalTransactionTree() {
          final LoggingContext loggingContext = getContext();
          if (loggingContext != null) {
              return loggingContext.m_isSuccessCalTransactionTree;
          }
          return true;
      }
  
      private static LoggingContext getContext() {
          return (LoggingContext) s_context.get();
      }
      /**
       * This methods are used only by KernelDAL to logging
       * command name. For example: 
       * Command Name=ViewItemNext
       * 
       * @return it always return a non-null value
       */
      public static String getCommandName() {
          /* string from HTTP context */
          String COMMAND_NAME = "Command Name: ";
          String NO_COMMAND = "NO_COMMAND";
          String context = getContextInfo();
          if (context == null) {
              return NO_COMMAND;
          }
          int pCommand = context.indexOf(COMMAND_NAME);
          if (pCommand == -1) {
              return NO_COMMAND;
          }
          
          /* in case: there is nothing after COMMAND_NAME */
          if ((pCommand + COMMAND_NAME.length()) >= context.length()) {
              return NO_COMMAND;
          }
          int pNewline = context.indexOf("\n", pCommand);
          if (pNewline == -1) {
              return context.substring(pCommand+ COMMAND_NAME.length());
          } else {
              return context.substring(pCommand+ COMMAND_NAME.length(), pNewline );
          }
      }
  
      private static class ThreadLocalContext extends InheritableThreadLocal<LoggingContext> {
          protected LoggingContext initialValue() {
              return new LoggingContext();
          }
  
          protected LoggingContext childValue(LoggingContext parentValue) {
              LoggingContext parentContext = (LoggingContext) parentValue;
              LoggingContext childContext = new LoggingContext();
              childContext.m_primary = parentContext.m_primary;
              childContext.m_secondary = parentContext.m_secondary;
              return childContext;
          }
      }
  }