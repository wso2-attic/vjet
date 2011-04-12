/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

public class VjoFiler {
	
	protected static String readFromFile(String fileName) {

		FileInputStream    fis = null;
 	    InputStreamReader  isr = null;
        try {
            String characterEncoding = "utf-8";
    	    StringBuffer       sb = new StringBuffer();
    	    char               buf[] = new char[4096];
    	    int numRead;
    	
    	    fis = new FileInputStream( fileName );
    	    isr = new InputStreamReader( fis, characterEncoding ); 
    	
    	    do {
    	        numRead = isr.read(buf, 0, buf.length);
    	        if ( numRead > 0 )
    	            sb.append( buf, 0, numRead );
    	    } while ( numRead >= 0 );
    	
    	    return sb.toString();
        } catch (Exception e) {
           e.printStackTrace() ;   
           return null;
        }  finally {
        	if (isr != null) {
        		try {
					isr.close();
				} catch (IOException e) { /*ignore*/ } //NOPMD
        	}
        	if (fis != null) {
        		try {
					fis.close();
				} catch (IOException e) {/*ignore*/} //NOPMD
        	}
        }     
    }
	
	static public void writeToFile(URL fileName, String code) {

		FileOutputStream    fis = null;
	    OutputStreamWriter  isr = null;
        try {
        	File file = new File(fileName.toURI());
        	if (!file.getParentFile().exists()) {
        		file.getParentFile().mkdirs();
        	}
        	
            String characterEncoding = "utf-8";

    	    char               buf[] = code.toCharArray(); //new char[4096];
    	
    	    fis = new FileOutputStream( file );
    	    isr = new OutputStreamWriter( fis, characterEncoding ); 
    	
    	    isr.write(buf, 0, buf.length);
    	
        } catch (Exception e) {
           e.printStackTrace() ;   //KEEPME
        }  finally {
        	if (isr != null) {
        		try {
					isr.close();
				} catch (IOException e) { /*ignore*/ } //NOPMD
        	}
        	if (fis != null) {
        		try {
					fis.close();
				} catch (IOException e) {/*ignore*/} //NOPMD
        	}
        }
    }
}
