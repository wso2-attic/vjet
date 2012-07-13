/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.statistics;

import java.util.concurrent.ConcurrentMap;

import org.ebayopensource.dsf.common.statistics.DarwinStatisticsCtx.DarwinStatisticsCount;
import org.ebayopensource.dsf.logger.LogLevel;
import org.ebayopensource.dsf.logger.Logger;



public class DarwinStatisticsCtxHelper {
	
	private static V4StatisticsContainer s_con = V4StatisticsContainer.getInstance();
	
	public static void countCssRef(String id){
		if(s_con.doStatistics()){
			final ConcurrentMap<String, DarwinStatisticsCount> map = DarwinStatisticsCtx.ctx().getCssStatistics();
			countRef(id, map);
		}
	}
	
	public static void countImageRef(String id){
		if(s_con.doStatistics()){
			final ConcurrentMap<String, DarwinStatisticsCount> map = DarwinStatisticsCtx.ctx().getImageStatistics();
			countRef(id, map);
		}
	}
	
	public static void countLinkRef(String id){
		if(s_con.doStatistics()){
			final ConcurrentMap<String, DarwinStatisticsCount> map = DarwinStatisticsCtx.ctx().getLinkStatistics();				
			if( id.indexOf("/") == 0){
				id = id.substring(1);
			}
			countRef(id, map);
		}
	}
	
	//count usage
	public static void countComponentStatistics(String cmpName){
		if(s_con.doStatistics()){
			final ConcurrentMap<String, DarwinStatisticsCount> map = DarwinStatisticsCtx.ctx().getComponentStatistics();
			countUsage(cmpName, map);
		}
	}

	public static void countContentStatistics(String id){
		if(s_con.doStatistics()){
			final ConcurrentMap<String, DarwinStatisticsCount> map = DarwinStatisticsCtx.ctx().getContentStatistics();
			countUsage(id, map);
		}
	}
	
	public static void countJsStatistics(String cmpName){
		if(s_con.doStatistics()){
			final ConcurrentMap<String, DarwinStatisticsCount> map = DarwinStatisticsCtx.ctx().getJavascriptStatistics();
			if( cmpName != null){
				cmpName = cmpName + "Jsr";
			}
			countUsage(cmpName, map);
		}
	}
	
	public static void countCssStatistics(String id){
		if(s_con.doStatistics()){
			final ConcurrentMap<String, DarwinStatisticsCount> map = DarwinStatisticsCtx.ctx().getCssStatistics();
			countUsage(id, map);
		}
	}
	
	public static void countImageStatistics(String id){
		if(s_con.doStatistics()){
			final ConcurrentMap<String, DarwinStatisticsCount> map = DarwinStatisticsCtx.ctx().getImageStatistics();
			countUsage(id, map);
		}
	}
	
	public static void countLinkStatistics(String id){
		if(s_con.doStatistics()){
			final ConcurrentMap<String, DarwinStatisticsCount> map = DarwinStatisticsCtx.ctx().getLinkStatistics();
			if( id.indexOf("/") == 0){
				id = id.substring(1);
			}
			countUsage(id, map);
		}
	}
	
   public static void countEsfTemplateUsageStatistics(String uri) {
      final ConcurrentMap<String, DarwinStatisticsCount> map = DarwinStatisticsCtx.ctx().getEsfTemplateStatistics();
      try{
         countUsageIncre1(uri, map);
      }
      catch(Exception ex){
         Logger.getInstance(DarwinStatisticsCtxHelper.class).log(LogLevel.ERROR, ex);
      }
   }

   public static void countEsfTemplateUsageFailureStatistics(String uri) {
      final ConcurrentMap<String, DarwinStatisticsCount> map = DarwinStatisticsCtx.ctx().getEsfTemplateFailureStatistics();
      try{
         countUsageIncre1(uri, map);
      }
      catch(Exception ex){
         Logger.getInstance(DarwinStatisticsCtxHelper.class).log(LogLevel.ERROR, ex);
      }
   }
   
   public static void countEsfTemplateRefStatistics(String uri) {
      DarwinStatisticsCtx ctx = DarwinStatisticsCtx.ctx();
      final ConcurrentMap<String, DarwinStatisticsCount> map = ctx.getEsfTemplateStatistics();
      try{
         countRefIncre1(uri, map);
      }
      catch(Exception ex){
         Logger.getInstance(DarwinStatisticsCtxHelper.class).log(LogLevel.ERROR, ex);
      }
   }
   
   public static void countEsfTemplateRefFailureStatistics(String uri) {
      final ConcurrentMap<String, DarwinStatisticsCount> map = DarwinStatisticsCtx.ctx().getEsfTemplateFailureStatistics();
      try{
         countRefIncre1(uri, map);
      }
      catch(Exception ex){
         Logger.getInstance(DarwinStatisticsCtxHelper.class).log(LogLevel.ERROR, ex);
      }
   }
   
   public static void countBizMoUsageStatistics(String uri) {
	   final ConcurrentMap<String, DarwinStatisticsCount> map = DarwinStatisticsCtx.ctx().getBizMoStatistics();
	   try{
		   countUsageIncre1(uri, map);
	   }
	   catch(Exception ex){
		   Logger.getInstance(DarwinStatisticsCtxHelper.class).log(LogLevel.ERROR, ex);
	   }
   }
   
   public static void countBizOpUsageStatistics(String uri) {
      final ConcurrentMap<String, DarwinStatisticsCount> map = DarwinStatisticsCtx.ctx().getBizOpStatistics();
      try{
         countUsageIncre1(uri, map);
      }
      catch(Exception ex){
         Logger.getInstance(DarwinStatisticsCtxHelper.class).log(LogLevel.ERROR, ex);
      }
   }
   
	private static void countUsage(String id, ConcurrentMap<String, DarwinStatisticsCount> map) {
		try{
			if( id != null && id.length() > 0){
				DarwinStatisticsCount count = map.get(id);
				if( count == null){
				    count = new DarwinStatisticsCount();
				    DarwinStatisticsCount old = map.putIfAbsent(id, count);
				    if (old != null) {
				        count = old;
				    }
				}
				
				count.incrementUsage(s_con.getIncrement());
			}
		}
		catch(Exception ex){
			Logger.getInstance(DarwinStatisticsCtxHelper.class).log(LogLevel.ERROR, ex);
		}
	}

	private static void countUsageIncre1(String uri,
			final ConcurrentMap<String, DarwinStatisticsCount> map) {
		if( uri != null && uri.length() > 0){
			   DarwinStatisticsCount count = map.get(uri);
			   if( count == null){
				   count = new DarwinStatisticsCount();
				   DarwinStatisticsCount old = map.putIfAbsent(uri, count);
				   if(old != null) {
					   count = old;
				   }
			   }
			   
			   count.incrementUsage(1);
		   }
	}

	private static void countRef(String id, ConcurrentMap<String, DarwinStatisticsCount> map) {
		try{
			if( id != null && id.length() > 0){
				DarwinStatisticsCount count = map.get(id);
				if( count == null){
				    count = new DarwinStatisticsCount();
				    DarwinStatisticsCount old = map.putIfAbsent(id, count);
				    if (old != null) {
				        count = old;
				    }		
				}
				
				count.incrementRef(s_con.getIncrement());
			}
		}
		catch(Exception ex){
			Logger.getInstance(DarwinStatisticsCtxHelper.class).log(LogLevel.ERROR, ex);
		}
	}

	private static void countRefIncre1(String uri,
			final ConcurrentMap<String, DarwinStatisticsCount> map) {
		if( uri != null && uri.length() > 0){
			   DarwinStatisticsCount count = map.get(uri);
			   if( count == null){
				   count = new DarwinStatisticsCount();
				   DarwinStatisticsCount old = map.putIfAbsent(uri, count);
				   if(old != null) {
					   count = old;
				   }
			   }
			   
			   count.incrementRef(1);
		   }
	}

}
