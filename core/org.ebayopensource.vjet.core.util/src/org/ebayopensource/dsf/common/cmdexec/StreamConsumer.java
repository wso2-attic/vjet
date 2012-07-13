package org.ebayopensource.dsf.common.cmdexec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.ebayopensource.dsf.logger.LogLevel;
import org.ebayopensource.dsf.logger.Logger;


// http://www.javaworld.com/javaworld/jw-12-2000/jw-1229-traps_p.html
class StreamConsumer extends Thread {
	InputStream m_is;
	String m_type;
	OutputStream m_os;

	//
	// Constructor(s)
	//
	StreamConsumer(InputStream is, String type) {
		this(is, type, null);
	}

	StreamConsumer(InputStream is, String type, OutputStream redirect) {
		m_is = is;
		m_type = type;
		m_os = redirect;
	}

	//
	// Satisfy Runnable
	//
	public void run() {
		try {
			PrintWriter pw = null;
			if (m_os != null) {
				pw = new PrintWriter(m_os);
			}

			InputStreamReader isr = new InputStreamReader(m_is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				if (pw != null) {
					pw.println(line);
				}
				//System.out.println(m_type + ">" + line);
			}
			if (pw != null) {
				pw.flush();
			}
		}
		catch (IOException ioe) {
			Logger.getInstance(StreamConsumer.class).log(LogLevel.WARN, ioe);
		}
	}
}

