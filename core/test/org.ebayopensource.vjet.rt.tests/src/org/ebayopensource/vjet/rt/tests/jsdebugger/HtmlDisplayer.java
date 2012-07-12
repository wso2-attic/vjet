package org.ebayopensource.vjet.rt.tests.jsdebugger;

 
 import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import org.ebayopensource.dsf.active.client.AWindow;
import org.ebayopensource.dsf.active.dom.html.ANode;
import org.ebayopensource.dsf.active.dom.html.ANodeInternal;
import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.common.xml.IIndenter;
import org.ebayopensource.dsf.dom.DNode;
import org.ebayopensource.dsf.html.HtmlWriterHelper;
import org.ebayopensource.dsf.html.dom.DHtmlDocument;
import org.ebayopensource.dsf.util.NodeToDHtmlDocument;
import org.w3c.dom.Node;



/** 
 * This class is used to provide support for displaying HTML.  The HTML can be
 * passed in as a String or in some Element.  The methods are smart enough to be
 * "know" how to properly parent a passed in Element so that in the end we have
 * a full document that can be emitted.  For example passing in a DLi Element '
 * would result in the code creating a DDocument with DHead with DTitle and creating
 * a DBody and DOl where the DLi would be parented into.  This provides a very
 * nice/fast way of seeing rendered HTML from very simple fragments.  The code
 * will clone the passed in Element so the smart parenting will never affect the
 * actual passed in Element.
 * 
 * The possible output mechanisms are:
 * <li>Internet Explorer
 * <li>Firefox
 * <li>Default Browser for your system - will also replace contents if already up
 * <li>Console - Java's System.out
 * <li>Pilot - Display in the all Java Browser
 */
 public class HtmlDisplayer {
     public static DisplayType IE = new DisplayType("IE") ;
     public static DisplayType FIREFOX = new DisplayType("FIREFOX") ; //Not supported
     public static DisplayType DEFAULT = new DisplayType("DEFAULT") ;
     public static DisplayType CONSOLE = new DisplayType("CONSOLE") ;
     public static DisplayType PILOT = new DisplayType("PILOT") ;
     public static String TEMP_DIR_PATH = getTempDirPath() ;
    
//  private static SwingBrowser m_SwingBrowser ;
    
     private static String getTempDirPath() {
         try {
             File temp = File.createTempFile("v4me", ".html");
             temp.deleteOnExit();
             String s = temp.getCanonicalPath() ;
             int end = s.lastIndexOf(File.separatorChar);
             return s.substring(0, end) ;

        }
         catch (Exception e) {
             throw new RuntimeException(e.getMessage());
        }       
    }
    
    /**
     * Displays the component as HTML in the default system browser.  The component
     * can be any BaseHtmlElement and will end up being cloned and properly parented
     * so that a full DHtmlDocument results.  The constructed DHtmlDocument is
     * then emitted as HTML into a temp file and displayed in the default system
     * browser.  If you want to control the actual display to be IE, FireFox
     * or Pilot, use display(BaseHtmlElement, String mechanism) where the mechanism
     * is on the DISPLAY_IN_XXX static constants.
     */
     public static void display(Node component) {
         display(component,  HtmlDisplayer .DEFAULT);
    }
    

    /**
     * Displays the component as HTML in the default system browser.
     */
     public static void display(String html) {
         display(html,  HtmlDisplayer .DEFAULT, "ISO-8859-1");
    }
    
//  public static void displayFile(String filePath) {
//      
//  }
//  
//  public static void displayFile(File file) {
//      
//  }
//  
//  public static void displayUrl(URL url) {
//      
//  }
    
        
    /**
     * Displays the component as HTML in the supplied mechanism.  The component
     * can be any BaseHtmlElement and will end up being cloned and properly parented
     * so that a full DHtmlDocument results.  The constructed DHtmlDocument is
     * then emitted as HTML into a temp file and displayed using the mechanism
     * passed in.  The "mechanism" constants are of the DISPLAY_IN_XXX format
     * found as static constants on this class.
     */
     public static void display(Node component, DisplayType mechanism) {
         DHtmlDocument doc = NodeToDHtmlDocument.createHtmlDocumentContaining(component) ;
        
         display(doc, mechanism);
    }
    

    
    /**
     * Displays the passed in HTML using the passed in mechanism.
     */
     public static void display(String html, DisplayType mechanism, String charset) {
         final boolean deleteTempFileOnVmExit = false ;
        
         if (HtmlDisplayer.DEFAULT == mechanism) {
             final String absFilePath = getFilePathFor(html, deleteTempFileOnVmExit, charset);
             final String url = makeFileUrl(absFilePath) ;
             BrowserManager.displayUrlInDefault(url) ;
             return;         
        }
        
         if (HtmlDisplayer.IE == mechanism) {
             final String absFilePath = getFilePathFor(html, deleteTempFileOnVmExit, charset);
             final String url = makeFileUrl(absFilePath) ;
             BrowserManager.displayUrlInIE(url) ;
             return;
        }
        
        /* Not Supported due to hardcoded paths */
         if (HtmlDisplayer.FIREFOX == mechanism) {
             final String absFilePath = getFilePathFor(html, deleteTempFileOnVmExit, charset);
             final String url = makeFileUrl(absFilePath) ;
             BrowserManager.displayUrlInFirefox(url) ;
             return;
        }
        
         if (HtmlDisplayer.CONSOLE == mechanism) {
             System.out.println(html) ;
             return;
        }
        
//      if (HtmlDisplayer.PILOT == mechanism) {
//          m_SwingBrowser = new SwingBrowser(0, 0,800,700);
//          try {
//              m_SwingBrowser.renderContent(html, "UTF-8") ;
//          }
//          catch(UnsupportedEncodingException e) {
//              throw new DsfRuntimeException(e.getMessage()) ;
//          }   
//          
//          return ;        
//      }
        
         throw new DsfRuntimeException("Unknown out mechanism: " + mechanism);       
    }
    
    /**
     * Translates the passed in DHtmlDocument into HTML and then displays
     * it using the passed in mechanism.
     */ 
     public static void display(DHtmlDocument doc, DisplayType mechanism) {
         display(getHtml(doc), mechanism, "ISO-8859-1") ;
    }
    
    /**
     * 
     * Translate the passed DNode into html using the display mechanism
     * WITHOUT any attemp to parent the node properly. 
     * 
     */
     public static void displayNode(Node node, DisplayType mechanism) {
         display(getHtml(node),mechanism, "ISO-8859-1") ;
    }
//  public static void displayNode(
//      BaseComponent node,
//      DisplayType mechanism,
//      IIndenter indenter)
//  {
//      throw new Error("not implemented!");
//  }

     public static void displayNode(
         Node node,
         DisplayType mechanism,
         IIndenter indenter)
    {
         display(getHtml(node, indenter), mechanism, "ISO-8859-1");
    }

    /**
     * Translates the passed in DHtmlDocument into HTML using the passed in 
     * print mode and then displays it using the passed in mechanism.
     */     
     public static void display(
         DHtmlDocument doc,
         DisplayType mechanism, 
         IIndenter indenter,
         String charset)
    {
         display(getHtml(doc, indenter), mechanism, charset) ;
    }
    
    
    /**
     * Answer the absolute path for a an HTML file that contains the rendered
     * HTML from the passed in doc.  The file is generated by Java's
     * File.createTempFile(...).  We set the deleteOnExit() on the file based on
     * the passed in deleteOnExit parameter.  The generated file will start with
     * "v4me" + generated name + ".html". 
     */
     public static String getFilePathFor(DHtmlDocument doc, boolean deleteOnExit, String charset) {
         final String html = getHtml(doc) ;
         return getFilePathFor(html, deleteOnExit, charset) ;
    }
    
    /**
     * Answer the absolute path for a an HTML file that contains the rendered
     * HTML from the passed in doc.  The file is generated by Java's
     * File.createTempFile(...).  We set the deleteOnExit() on the file based on
     * the passed in deleteOnExit parameter.  The generated file will start with
     * "v4me" + generated name + ".html". 
     */
     public static String getFilePathFor(String html, boolean deleteOnExit, String charset) {        
        // write to temp file
         File temp = null;
         String fullFileName = null;
         try {
             temp = File.createTempFile("v4me", ".html");
             fullFileName = temp.getAbsolutePath();
             if (deleteOnExit) {
                 temp.deleteOnExit();
            }
            
            // Write to temp file

             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(temp),charset));
             out.write(html);
             out.close();
        }
         catch (Exception e) {
             throw new RuntimeException(e.getMessage());
        }
        
         return fullFileName ;
    }

    //
    // Private
    //
    /**
     * First we check to see if "c" can be directly parentable by BODY.  If
     * "c" is directly parentable by BODY, we simply return it.
     * 
     * If not directly parentable, we create the correct parenting so that it
     * can be parented by BODY.  The result of this operation will PARENT the
     * passed in BaseHtmlElement.  Use clone() if you need to keep the element
     * from being impacted by the actual parenting or doit yourself afterwards.
     * 
     */
     public static String getHtml(DHtmlDocument doc) {
         return getHtml(doc, new IIndenter.Pretty());    
    }
    
     public static String getHtml(Node node) {
         return getHtml(node, new IIndenter.Pretty());   
    }
    
     public static String getHtml(Node node, IIndenter indenter) {
         final StringWriter writer = new StringWriter(1000);
         HtmlWriterHelper.write(node, writer, indenter);
         final String html = writer.toString();  
         return html;    
    }

     public static String getHtml(DHtmlDocument doc, IIndenter indenter) {
         final StringWriter writer = new StringWriter(1000);
         HtmlWriterHelper.write(doc, writer, indenter);
         final String html = writer.toString();  
         return html ;   
    }

    /**
     * get HTML from AWindow
     */
     public static String getHtml(AWindow window) {
         return getHtml(ANodeHelper.getDNode((ANode) window.getDocument()), new IIndenter.Pretty()); 
    }

     
     private static String makeFileUrl(String absFilePath) {
         return "file:///" + absFilePath ;
    }
            
//  private static void emitFollowedByDeparent(
//      DHtmlDocument doc,
//      BaseHtmlElement actualRoot) {
//      
//      try {
//          HtmlWriterHelper.write(
//              doc,
//              new PrintWriter(System.out),
//              Indenter.PRETTY);
//      }
//      finally { // deparent topmost parent of component
//          DsfComponent p = actualRoot.parent();
//          if (p != null) {
//              p.getDsfChildren().remove(actualRoot);
//          }
//      }
//  }
    
//  private static void out2(BaseHtmlElement component) {
//      DNode actualRoot = component;
//      while (actualRoot.parent() != null) {
//          actualRoot = (DNode) actualRoot.parent();
//      }
//      DHtmlDocument doc = null;
//      if (actualRoot instanceof DHtmlDocument) {
//          doc = (DHtmlDocument) actualRoot;
//      }
//      else {
//          doc = new DHtmlDocument();
//      }
//      // if actualRoot is a DHead we parent and can display
//      if (actualRoot instanceof DHead) {
//          doc.dsfAddChild(actualRoot);
//          emitFollowedByDeparent(doc, (BaseHtmlElement) actualRoot);
//          return;
//      }
//      // to be well-form HTML document we need a HEAD - we add it here
//      DHead head = new DHead();
//      doc.appendChild(head);
//      DTitle title = new DTitle();
//      DText titleText = new DText("Generated by out");
//      title.appendChild(titleText);
//      head.appendChild(title);
//      // if actualRoot is a BODY or FRAMESET we parent and can display
//      if (actualRoot instanceof DBody || actualRoot instanceof DFrameSet) {
//          doc.dsfAddChild(actualRoot);
//          emitFollowedByDeparent(doc, (BaseHtmlElement) actualRoot);
//          return;
//      }
//      // At this point we have an element that must be further parented
//      // to be hooked into the document.  We will assume that we are going
//      // to add a BODY as that new attachment point.
//      DBody body = new DBody();
//      doc.appendChild(body);
//      DNode forBodyParenting =
//          getSuitableForBodyParenting(actualRoot);
//      body.appendChild(forBodyParenting);
//      emitFollowedByDeparent(doc, (BaseHtmlElement) actualRoot);
//  }
    
    /**
     * Internal type used to make Display type selection "type safe"
     * for programming.  Instances are used in the static final constants for
     * which display mechanism (IE, FIREFOX, CONSOLE, etc...).
     */
     public static final class DisplayType {
         private String m_type ;
        
         DisplayType(String type) {
             m_type = type ;
        }
        
         public String getType() {
             return m_type ;
        }
        @Override
         public String toString() {
             return m_type ;
        }
    }
    
     private static class ANodeHelper extends ANodeInternal {
         private static DNode getDNode(ANode node){
             return ANodeInternal.getInternalNode(node);
        }
    }
}