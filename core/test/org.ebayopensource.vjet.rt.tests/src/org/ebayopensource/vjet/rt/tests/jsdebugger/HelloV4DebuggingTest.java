package org.ebayopensource.vjet.rt.tests.jsdebugger;

import org.ebayopensource.dsf.active.client.AHtmlParser;
import org.ebayopensource.dsf.active.client.AWindow;
import org.ebayopensource.dsf.active.client.ScriptExecutor;
import org.ebayopensource.dsf.html.dom.DForm;
import org.ebayopensource.dsf.html.dom.DInput;
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.html.js.ActiveJsExecutionControlCtx;
import org.ebayopensource.dsf.js.dbgp.JsDebuggerEnabler;
import org.ebayopensource.vjo.runner.VjoRunner;
import org.junit.Test;

import org.ebayopensource.dsf.common.resource.ResourceUtil;

public class HelloV4DebuggingTest {
     public static void main(String[] args) {
         JsDebuggerEnabler.enable();
         
         ActiveJsExecutionControlCtx.ctx().setExecuteJavaScript(true);
        
         String html = createHtml();
        
         AWindow window = (AWindow) AHtmlParser.parse(html, null);
         System.err.println(HtmlDisplayer.getHtml(window));
        
         ScriptExecutor.executeScript("alert('test');", window);
        
         System.out.println(HtmlDisplayer.getHtml(window));
    }
    
     
     @Test
     public void testHelloWorldVjoMain() throws Exception {
         String jsFile = ResourceUtil.getResource(HelloV4DebuggingTest.class, "simplemain.js.txt").getFile();
         String[] args = {jsFile, "test.CTypeA"};
         VjoRunner .main(args);
      
    }
    
    @Test
     public void testVjoMainWithArgs() throws Exception {
         String jsFile = ResourceUtil.getResource(HelloV4DebuggingTest.class, "simplemain2.js").getFile();
         String[] args = {jsFile, "test.CTypeA", "Hello", "World"};
         VjoRunner .main(args);
         
        
    }
    
    @Test
    public void testWindowOnLoad() throws Exception {
    	String htmlFile = ResourceUtil.getResource(HelloV4DebuggingTest.class, "onloadtest.html").getFile();
    	String jsFile = ResourceUtil.getResource(HelloV4DebuggingTest.class, "onloadtest.js").getFile();
    	  String[] args2 = {  "-VloadHtml=" + htmlFile , "-Vbrowser=FIREFOX_3P", "-VdapMode=A", jsFile };
    	
    	VjoRunner.main(args2);
    	
    	
    }

     
     private static String createHtml() {        
         DForm form = new DForm()
            .setHtmlName("myform")
            .setHtmlAction("http://v4.ebay.com")
            .add(new DInput()
                .setHtmlName("abc")
                .setHtmlType(DInput.TYPE_TEXT)
                .setHtmlValue("101"))
            .add(EventType.SUBMIT, "alert('test');");
        
         return HtmlDisplayer.getHtml(form);
       
    }
}
