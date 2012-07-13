package org.ebayopensource.dsf.common.resource;
  import java.io.File;
  import java.io.IOException;
  import java.io.InputStream;
  import java.net.URL;
  
  
  /**
   * This implementation just searches the classpath for the resource.
   *
   * @author kquacken
   */
  public class DefaultResolver implements Resolver {
  
      /**
       * Helper method to generate the path name of the resource.
       *
       * @param relDir - the relative directory where the file should reside
       * @param resourceName - the name of the resource
       * 
       * @return String - the path of the external resource
       */
      private String makeResourcePath(
          final String relDir,
          final String resourceName)
      {
          int len = relDir.length() + 1 + resourceName.length();
          final StringBuilder buf = new StringBuilder(len);
          buf.append(relDir);
          buf.append('/');
          buf.append(resourceName);
          
          // for some reason it will not find the file if it is in a jar file
          // and we have the backward slash in the path, so convert all of them
          // to forward slashes.
          return buf.toString().replace('\\', '/');
      }
      
      /**
       * Verifies that the relative path specified does not begin or end
       * with the separator character (File.separator).  If it is valid then the
       * path is directly returned, otherwise a new string is created which is a
       * substring of the current path which does not include the beginning or
       * ending separator.
       * 
       * @param path the relative directory path to check
       * @return a valid path that can be passed to the makeResourcePath method.
       */
      private String checkPath(final String path)  {
          // most of the time they are probably giving us a valid path that does
          // not start with or end with the separator
          boolean startFlag = path.startsWith(File.separator);
          boolean endFlag = path.endsWith(File.separator);
          if ((!startFlag) && (!endFlag))  {
              return(path);
          } else {
              int startOff = 0;
              int endOff = path.length();
              if (startFlag)  {
                  startOff = 1;
              }
              if (endFlag)  {
                  endOff--;
              }
              return(path.substring(startOff, endOff));
          }
      }
  
      /** 
       * Get the stream for the given resource.
       * 
       * This is merely a pass through to ClassLoader.getResourceAsStream().
       * Failure to get the stream will result in a runtime exception.
       * 
       * @param relDir - the relative directory where the file should reside
       * @param resourceName - the name of the resource
       * 
       * @return InputStream - input stream to the resource.
       */
      public InputStream getResourceAsStream(
          final String relDir,
          final String resourceName) throws IOException
      {
          String pathDir = checkPath(relDir);
          String path = makeResourcePath(pathDir, resourceName);
          return(getClass().getClassLoader().getResourceAsStream(path));
      }
  
      /** get the stream for the given resource name for the class.
       * This is merely a pass through to class.getResourceAsStream().
       * Failure to get the stream will result in a runtime exception.
       * 
       * @param sourceClass - the class relative to which the resource belongs
       * @param resourceName - the name of the resource
       * 
       * @return InputStream - input stream to the resource.
       */
      public InputStream getResourceAsStream(
          final Class sourceClass,
          final String resourceName) throws IOException
      {
          return sourceClass.getResourceAsStream(resourceName);
      }
  
      /** This returns a URL for the given resource.
       * 
       * @param relDir - the relative directory where the file should reside
       * @param resourceName - the name of the resource
       * 
       * @return URL - url for the resource
       */
      public URL getResource(final String relDir,
          final String resourceName) throws IOException
      {
          String pathDir = checkPath(relDir);
          String path = makeResourcePath(pathDir, resourceName);
          return(getClass().getClassLoader().getResource(path));
      }
  
      /** This returns a URL for the given resource for the given
       * class.
       * 
       * @param sourceClass - the class relative to which the resource belongs
       * @param resourceName - the name of the resource
       * 
       * @return URL - url for the resource
       */
      public URL getResource(final Class sourceClass,
          final String resourceName) throws IOException
      {
          return sourceClass.getResource(resourceName);
      }
  
      public String toString() {
          return "DefaultResolver";
      }
  }