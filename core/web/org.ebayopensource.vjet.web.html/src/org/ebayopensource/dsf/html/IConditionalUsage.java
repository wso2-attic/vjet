/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html;

import java.io.Serializable;

import org.ebayopensource.dsf.common.Z;

/**
 * This provides an interface for the conditional usage of resources
 * (JS, CSS or Flush) in client device.
 * 
 * It provides generic Condition class and its IE specific subtype.
 * 
 * There are a set of predefined constant for commonly used IE conditions.
 */
public interface IConditionalUsage extends Serializable {
	Condition getCondition();
	public static final String DELIMITER = "###";
	Condition IE6 = new IeCondition("IE 6") {
		private static final long serialVersionUID = 1L;
		@Override
		public boolean matches(CLIENT_TYPE type) {
			return CLIENT_TYPE.IE6 == type;
		}
	};
	Condition IE7 = new IeCondition("IE 7"){
		private static final long serialVersionUID = 1L;
		@Override
		public boolean matches(CLIENT_TYPE type) {
			return CLIENT_TYPE.IE7 == type;
		}
	};
	Condition IE8 = new IeCondition("IE 8"){
		private static final long serialVersionUID = 1L;
		@Override
		public boolean matches(CLIENT_TYPE type) {
			return CLIENT_TYPE.IE8 == type;
		}
	};
	Condition NOT_IE6 = new IeCondition("!IE 6"){
		private static final long serialVersionUID = 1L;
		@Override
		public boolean matches(CLIENT_TYPE type) {
			return CLIENT_TYPE.IE6 != type;
		}
	};
	Condition NOT_IE7 = new IeCondition("!IE 7"){
		private static final long serialVersionUID = 1L;
		@Override
		public boolean matches(CLIENT_TYPE type) {
			return CLIENT_TYPE.IE7 != type;
		}
	};
	Condition NOT_IE8 = new IeCondition("!IE 8"){
		private static final long serialVersionUID = 1L;
		@Override
		public boolean matches(CLIENT_TYPE type) {
			return CLIENT_TYPE.IE8 != type;
		}
	};
	Condition BELOW_IE6 = new IeCondition("lt IE 6"){
		private static final long serialVersionUID = 1L;
		@Override
		public boolean matches(CLIENT_TYPE type) {
			return CLIENT_TYPE.IE5 == type;
		}
	};
	Condition BELOW_IE7 = new IeCondition("lt IE 7"){
		private static final long serialVersionUID = 1L;
		@Override
		public boolean matches(CLIENT_TYPE type) {
			return CLIENT_TYPE.IE5 == type || CLIENT_TYPE.IE6 == type;
		}
	};
	Condition BELOW_IE8 = new IeCondition("lt IE 8"){
		private static final long serialVersionUID = 1L;
		@Override
		public boolean matches(CLIENT_TYPE type) {
			return BELOW_IE7.matches(type) || CLIENT_TYPE.IE7 == type;
		}
	};
	Condition IE5_AND_ABOVE = new IeCondition("gte IE 5"){
		private static final long serialVersionUID = 1L;
		@Override
		public boolean matches(CLIENT_TYPE type) {
			return IE6_AND_ABOVE.matches(type) || CLIENT_TYPE.IE5 == type;
		}
	};
	Condition IE6_AND_ABOVE = new IeCondition("gte IE 6"){
		private static final long serialVersionUID = 1L;
		@Override
		public boolean matches(CLIENT_TYPE type) {
			return IE7_AND_ABOVE.matches(type) || CLIENT_TYPE.IE6 == type;
		}
	};
	Condition IE7_AND_ABOVE = new IeCondition("gte IE 7"){
		private static final long serialVersionUID = 1L;
		@Override
		public boolean matches(CLIENT_TYPE type) {
			return CLIENT_TYPE.IE8 == type || CLIENT_TYPE.IE7 == type;
		}
	};
	abstract class Condition implements Serializable{
		private static final long serialVersionUID = 1L;
		private final String m_begin;
		private final String m_end;
		private final String m_condition;
		public Condition(String begin, String end, String condition) {
			m_begin = begin;
			m_end = end;
			m_condition = condition;
		}
		
		public String getBegin() {
			return m_begin;
		}
		
		public String getEnd() {
			return m_end;
		}
		
		public String getCondition() {
			return m_condition;
		}
		
		/**
		 * If the CLIENT_TYPE passed to this method, satisfies
		 * the condition, return true, else return false
		 * @param type
		 * @return
		 */
		public abstract boolean matches(CLIENT_TYPE type);
		
		//
		// Override(s) from Object
		//
		@Override
		public boolean equals(Object o) {
			if (o == this) {
				return true;
			}
			if (!(o instanceof Condition)) {
				return false;
			}
			Condition other = (Condition)o;
			return m_begin.equals(other.m_begin)
				&& m_end.equals(other.m_end);
		}
		
		@Override
		public String toString() {
			Z z = new Z() ;
			z.format("begin", getBegin()) ;
			z.format("end", getEnd()) ;
			return z.toString() ;
		}
	}
	
	abstract class IeCondition extends Condition {
		private static final long serialVersionUID = 1L;
		private static final String IE_BEGIN_CONDITION_PRE = "[if ";
		private static final String IE_BEGIN_CONDITION_SUF = "]>";
		private static final String IE_END_CONDITION = "<![endif]";

		public IeCondition(String condition) {
			super(IE_BEGIN_CONDITION_PRE + condition + IE_BEGIN_CONDITION_SUF,
				IE_END_CONDITION, condition);
		
		}
	}
	
	public enum CLIENT_TYPE {IE5, IE6, IE7, IE8, OTHER};
	 
	public static class ConditionalSerializer implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

			public static Condition fromText(String text) {
			if(text.equals("IE 6")) {
				return IConditionalUsage.IE6;
			} else if (text.equals("IE 7")) {
				return IConditionalUsage.IE7;
			} else if (text.equals("IE 8")) {
				return IConditionalUsage.IE8;
			} else if (text.equals("!IE 6")) {
				return IConditionalUsage.NOT_IE6;
			} else if (text.equals("!IE 7")) {
				return IConditionalUsage.NOT_IE7;
			} else if (text.equals("!IE 8")) {
				return IConditionalUsage.NOT_IE8;
			} else if (text.equals("lt IE 6")) {
				return IConditionalUsage.BELOW_IE6;
			} else if (text.equals("lt IE 7")) {
				return IConditionalUsage.BELOW_IE7;
			} else if (text.equals("lt IE 8")) {
				return IConditionalUsage.BELOW_IE8;
			} else if (text.equals("gte IE 5")) {
				return IConditionalUsage.IE5_AND_ABOVE;
			} else if (text.equals("gte IE 6")) {
				return IConditionalUsage.IE6_AND_ABOVE;
			} else if (text.equals("gte IE 7")) {
				return IConditionalUsage.IE7_AND_ABOVE;
			}
			return null;
		}
			
		public static String toText(Condition condition) {
			return new StringBuffer(DELIMITER).append(condition.getCondition()).append(DELIMITER).toString();
		}
	}
}
