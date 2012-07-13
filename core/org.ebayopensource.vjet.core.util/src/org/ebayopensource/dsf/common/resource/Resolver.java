package org.ebayopensource.dsf.common.resource;
  import java.io.IOException;
  import java.io.InputStream;
  import java.net.URL;
  
  /** Methods to get a resource of a given name for a class.
   *
   */
  public interface Resolver {
  
      /**
       * Deprecated.  Instead of using a class to resolve the resource, it should
       * use the call which specifies the relative directory.
       * 
       * This returns an input stream for the given resource for the given
       * class.
       * 
       * Failure to be able to open the stream will result in an IOException.
       * 
       * @param sourceClass - the class relative to which the resource belongs
       * @param resourceName - the name of the resource
       * 
       * @return InputStream - input stream to the resource.
       */
      InputStream getResourceAsStream(final Class sourceClass,
          final String resourceName) throws IOException;
          
      /**
       * This returns an input stream for the given resource.
       * 
       * Failure to be able to open the stream will result in an IOException.
       * 
       * @param relDir - the relative directory where the file should be found (for example "config/kerneldal")
       * @param resourceName - the name of the resource
       * 
       * @return InputStream - input stream to the resource.
       */
      InputStream getResourceAsStream(final String relDir,
          final String resourceName) throws IOException;
          
      /** 
       * Deprecated.  Instead of using a class to resolve the resource, it should
       * use the call which specifies the relative directory.
       * 
       * This returns a URL for the given resource for the given class.
       * 
       * @param sourceClass - the class relative to which the resource belongs
       * @param resourceName - the name of the resource
       * 
       * @return URL - url for the resource
       */
      URL getResource(final Class sourceClass,
          final String resourceName) throws IOException;
  
      /**
       * This returns a URL for the given resource.
       * 
       * @param relDir - the relative directory where the file should be found (for example "config/kerneldal")
       * @param resourceName - the name of the resource
       * 
       * @return URL - url for the resource
       */
      URL getResource(final String relDir,
          final String resourceName) throws IOException;
  
  }