package org.ebayopensource.dsf.common;
 public class CallerIntrospector extends SecurityManager {
      static CallerIntrospector instance = new CallerIntrospector();
      private CallerIntrospector() {}
      protected static Class<? extends Object> getCaller(int levelBack) {
          return instance.getClassContext()[levelBack];
      }
      public final static Class<? extends Object> getCallingClass() {
          return CallerIntrospector.getCaller(3);
      }
      public final static Class<? extends Object> getCallingClass(int levelBack) {
          return CallerIntrospector.getCaller(levelBack);
      }
  }