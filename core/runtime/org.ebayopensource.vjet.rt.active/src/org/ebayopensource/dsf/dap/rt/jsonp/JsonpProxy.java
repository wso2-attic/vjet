/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt.jsonp;

import org.ebayopensource.dsf.active.client.AWindow;
import org.ebayopensource.dsf.active.util.AsyncTask;
import org.ebayopensource.dsf.dap.rt.DapCtx;
import org.ebayopensource.dsf.dap.rt.DapHttpRequest;
import org.ebayopensource.dsf.dap.rt.DapHttpResponse;
import org.ebayopensource.dsf.dap.rt.IDapHttpClient;
import org.ebayopensource.dsf.services.ConnectionProtocolEnum;

/**
 * A util class to deal with dynamic script added to DOM for Jsonp application.
 * It will leverage DapHttpClient to fetch remote script 
 * and add to window task queue for later evaluation.
 * 
 *
 */
public class JsonpProxy {
	
	public static void evaluate(String src, final AWindow window) {
		if(src != null && src.trim().length() > 0){
			// Call DapHttpClient to fetch the js text
			DapHttpRequest dapReq = buildDapRequest(src);
			AsyncTask asyncTask = new AsyncTask();
			window.addTask(asyncTask);
			JsonpCallback callback = new JsonpCallback(asyncTask);
			DapCtx.ctx().getDapConfig().getHttpClient().send(dapReq, callback);
		}
	}
	
	private static DapHttpRequest buildDapRequest(String src){
		DapHttpRequest dapReq = new DapHttpRequest(
				ConnectionProtocolEnum.GET.getName(), src, true);
//		dapReq.setPayloadType(PayloadTypeEnum.JSON);
		dapReq.setTimeout(100000);
		return dapReq;
	}	

	/**
	 * Callback for async call on DapHttpClient.
	 * Just add the resp script text to window task queue.
	 * 
	 *
	 */
	private static class JsonpCallback implements IDapHttpClient.IDapCallback {

		private AsyncTask m_task;
		
		public JsonpCallback(final AsyncTask task){
			m_task = task;
		}
		
		@Override
		public void onComplete(DapHttpResponse resp) {
			// 1. parse the response to see whether it's a js function
			// 2. if yes, add it to window task queue
			String jsCode = resp.getResponseText();
			if(jsCode != null && jsCode.trim().length() > 0){
				m_task.setJsCode(jsCode);
			}
			else {
				m_task.cancel();
			}
		}

		@Override
		public void onTimedOut() {
			//do nothing, leave it empty
		}
	}
}
