package org.ebayopensource.dsf.common.resource;

  import java.io.File;
  import java.io.IOException;
  import java.io.InputStream;
  import java.net.URL;
  import java.util.Properties;
  
  
  /** This is the prefered class to access resources in the V3 system.  These
   * routines basically wrap a call to ResourceFactory.getInstance().
   * 
   * During startup, this class will print out messages to indicate where config
   * files had been found.  To disable this feature, it can be turned off
   * programmatically by calling the setDebugStartUp method before startup, or the
   * system property "org.ebayopensource.dsf.common.resource.debugStartUp" can be set to false.
   * 
   */
  public class ResourceUtil {
  
      /**
       * Flag to determine whether we should print out debug messages during startup
       */
      private static boolean s_debugFlag = true;
      
      static {
          String debugProp = System.getProperty("org.ebayopensource.dsf.common.resource.debugStartUp");
          if ((debugProp != null) && (debugProp.length() > 0))  {
              try {
                  s_debugFlag = Boolean.parseBoolean(debugProp);
              } catch (RuntimeException e) {
                  // ignore it in this case
              }
          }
      }
      
      /**
       * Allows a caller to disable or enable the debug messages during startup
       * @param flag - set to false to disable debug messages, set to true to enable
       */
      public static void setDebugStartUp(boolean flag)  {
          s_debugFlag = flag;
      }
      
      /**
       * This returns an input stream for the given resource.
       * 
       * @param relDir - the relative directory where the file should reside
       * @param resourceName - the name of the resource
       * 
       * @return InputStream - input stream to the resource.
       */
      public static InputStream getResourceAsStream(final String relDir,
          final String resourceName) throws IOException
      {
          InputStream is = null;
          final URL resURL = getResource(relDir, resourceName);
          if (resURL != null)  {
              is = resURL.openStream();
          }
          return is;
      }
  
      /** This returns an input stream for the given resource for the given
       * class.
       * 
       * Failure to get the stream will result in a runtime exception.
       * 
       * @param sourceClass - the class relative to which the resource belongs
       * @param resourceName - the name of the resource
       * 
       * @return InputStream - input stream to the resource.
       */
      public static InputStream getResourceAsStream(final Class sourceClass,
          final String resourceName) throws IOException
      {
          InputStream is = null;
          final URL resURL = getResource(sourceClass, resourceName);
          if (resURL != null)  {
              is = resURL.openStream();
          }
          return is;
      }
  
      /**
       * Returns the input stream resolved by {@link #getResourceAsStream}. 
       * Converts <tt>null</tt>s into exceptions to make checking uniform.
       * 
       * @param relDir - the relative directory where the file should reside
       * @param resourceName - the name of the resource
       * 
       * @return input stream to the resource, never null
       * @throws IOException if the resource cannot be found or opened
       */
      public static InputStream getMandatoryResourceAsStream(String relDir,
          String resourceName) throws IOException
      {
          InputStream result = getResourceAsStream(relDir, resourceName);
          if (result != null) {
              return result;
          }
          String err =
              "Unable to find resource "
                  + relDir + File.separator + resourceName;
          throw new IOException(err);
      }
      
      /**
       * Returns the input stream resolved by {@link #getResourceAsStream}. 
       * Converts <tt>null</tt>s into exceptions to make checking uniform.
       * @param sourceClass the class relative to which the resource belongs
       * @param resourceName the name of the resource
       * @return input stream to the resource, never null
       * @throws IOException if there resource cannot be found or open
       */
      public static InputStream getMandatoryResourceAsStream(Class sourceClass,
          String resourceName) throws IOException
      {
          InputStream result = getResourceAsStream(sourceClass, resourceName);
          if (result != null) {
              return result;
          }
          String err =
              "Unable to find resource "
                  + resourceName
                  + " relative to class "
                  + sourceClass.getName();
          throw new IOException(err);
      }
      
      /** This returns an input stream for the default resource for the given
       * class, where the resource name is same as class name .properties suffix.
       * 
       * Failure to get the stream will result in a runtime exception.
       * 
       * @param sourceClass - the class relative to which the resource belongs
       * 
       * @return InputStream - input stream to the resource.
       */
      public static InputStream getResourceAsStream(final Class sourceClass)
          throws IOException
      {
          return getResourceAsStream(sourceClass, getName(sourceClass));
      }
  
      /**
       * This returns a URL for the given resource.
       * 
       * @param relDir - the relative directory where the file should reside
       * @param resourceName - the name of the resource
       * 
       * @return URL - url for the resource
       */
      public static URL getResource(
              final String relDir, 
              final String resourceName)
          throws IOException
      {
          final URL resURL = ResolverFactory.getInstance().getResource(relDir, resourceName);
          if ((s_debugFlag) && (resURL != null))  {
              System.out.println("Found file at " + resURL);
          }
          return resURL;
      }
          
      /**
       * This returns a URL for the given resource for the given class.
       * 
       * @param sourceClass - the class relative to which the resource belongs
       * @param resourceName - the name of the resource
       * 
       * @return URL - url for the resource
       */
      public static URL getResource(
              final Class sourceClass, 
              final String resourceName)
          throws IOException
      {
          final URL resURL = ResolverFactory.getInstance().getResource(sourceClass, resourceName);
          if ((s_debugFlag) && (resURL != null))  {
              // KEEP ME - this was requested by QA so that we could determine where the
              // the config files were found for debug sessions
              System.out.println("Found config file at " + resURL);
          }
          return resURL;
      }
          
      /**
       * This returns a URL for the default resource for the given
       * class, where the resource name is same as class name with a .properties suffix.
       * 
       * @param sourceClass - the class relative to which the resource belongs
       * 
       * @return URL - url for the resource
       */
      public static URL getResource(final Class sourceClass) throws IOException
      {
          return getResource(sourceClass, getName(sourceClass));
      }
  
  
      /**
       * This returns a Properties for the given resource.
       * 
       * Failure to get the stream, or the resource is not a java properties file,
       * will result in a runtime exception.
       * 
       * @param relDir - the relative directory where the file should reside
       * @param resourceName - the name of the resource
       * 
       * @return Properties - resource properties.
       */
      public static Properties getResourceAsProperties(
              final String relDir,
              final String resourceName)
          throws IOException
      {
          final InputStream inputStream = getResourceAsStream(relDir, resourceName);
          if (inputStream == null) {
              return null;
          }
          try {
              final Properties properties = new Properties();
              properties.load(inputStream);
              return properties;
          } finally {
              inputStream.close();
          }
      }
      
      /**
       * This returns a Properties for the given resource for the given class.
       * 
       * Failure to get the stream, or the resource is not in java properties,
       * will result in a runtime exception.
       * 
       * @param sourceClass - the class relative to which the resource belongs
       * @param resourceName - the name of the resource
       * 
       * @return Properties - resource properties.
       */
      public static Properties getResourceAsProperties(
              final Class sourceClass,
              final String resourceName)
          throws IOException
      {
          final InputStream inputStream = getResourceAsStream(sourceClass, resourceName);
          if (inputStream == null) {
              return null;
          }
          try {
              final Properties properties = new Properties();
              properties.load(inputStream);
              return properties;
          } finally {
              inputStream.close();
          }
      }
      
      /** This returns a Properties for the default resource for the given
       * class, where the resource name is same as class name with .properties
       * suffix.
       * 
       * Failure to get the stream, or the resource is not in java properties,
       * will result in a runtime exception.
       * 
       * @param sourceClass - the class relative to which the resource belongs
       * 
       * @return Properties - resource properties.
       */
      public static Properties getResourceAsProperties(final Class sourceClass)
          throws IOException
      {
          return getResourceAsProperties(sourceClass, getName(sourceClass));
      }
  
      private static String getName(Class clz) {
          String name = clz.getName();
          int index = name.lastIndexOf(".");
          if (index != -1) {
              name = name.substring(index + 1);
          }
          return name + ".properties";
      }
  
  
  }