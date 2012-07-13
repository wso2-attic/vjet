package org.ebayopensource.dsf.logger;
 import java.io.Serializable;
  import java.util.ArrayList;
  import java.util.List;
  
  
  public class ErrorsInfo implements Serializable {
  
      private List<String> m_errorsLogged = null;
      private boolean m_errorPage = false;
  
    
      private boolean m_isErrorEventLogged = false;
  
   
  
      public void addErrorLog(String error) {
          if (error == null || error.length() == 0) {
              return;
          }
          /*
          if (error.length() > 64) {
              error = error.substring(0, 64);
          }
          */
          if (getErrorsList().size() < 5 && !getErrorsList().contains(error)) {
              getErrorsList().add(error);
          }
      }
  
      /**
       * Set this to true if the response is Error page like PNR page
       * 
       */
      public void setErrorPage() {
          m_errorPage = true;
      }
  
      /**
       * returns true if the PNR page flag is set.
       */
      public boolean isErrorPage() {
          return m_errorPage;
      }
  
      /**
       * returns true if atleast one CALEvent of type "Error" is logged
       */
      public boolean isErrorEventLogged() {
          return m_isErrorEventLogged;
      }
  
      /**
       * Returns the first error logged
       * 
       * @return
       */
      public String getFirstErrorLogged() {
          String firstErrorLogged = "";
          List<String> errorsLogged = getErrorsList();
          if (errorsLogged != null && errorsLogged.size() > 0) {
              String first = errorsLogged.get(0);
              if (first != null) {
                  firstErrorLogged = first;
              }
          }
  
          return firstErrorLogged;
      }
  
      /**
       * Returns concatenated list of all errors logged (separated by ";").
       * 
       * @return
       */
      public String getAllErrorsLogged() {
    	  StringBuilder buffer = getAllErrorsLoggedRB();
          if (buffer != null && buffer.length() > 0) {
              return buffer.toString();
          } else {
              return "";
          }
      }
  
      /**
       * Returns concatenated list of all errors logged (separated by ";").
       * (limited to 128 characters - CAL status field limit) 
       * 
       * @return
       */
      public String getAllErrorsLoggedStatusForCAL() {
          String allErrorsLogged = "NoErrorLogged";
  
          StringBuilder buffer = getAllErrorsLoggedRB();
          if (buffer == null) {
              return allErrorsLogged;
          }
          int bLen = buffer.length();
          //limit the string to 128 characters for CAL status limitation
          if (bLen > 128) {
              allErrorsLogged = buffer.substring(0, 128);
          } else if (bLen > 0) {
              allErrorsLogged = buffer.toString();
          }
  
          return allErrorsLogged;
      }
  
      private StringBuilder getAllErrorsLoggedRB() {
          int size = getErrorsList().size();
          if (size == 0) {
              return null;
          }
          StringBuilder buffer = new StringBuilder();
          for (int i = 0; i < size; i++) {
              buffer.append(getErrorsList().get(i)).append(";");
          }
          return buffer;
      }
  
      public boolean hasErrors() {
          return (getErrorsList().size() > 0);
      }
  
      private List<String> getErrorsList() {
          if (m_errorsLogged == null) {
              m_errorsLogged = new ArrayList<String>(3);
          }
          return m_errorsLogged;
      }
  
      private static final long serialVersionUID = -7404801190649016889L;
  }