/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

public class JSAction {
    
    public static final int NO_ACTION = 0;
    public static final int GET_COOKIE = 1;
    public static final int SET_COOKIE = 2;
    public static final int GET_LOCATION = 3;
    public static final int SET_LOCATION = 4;
    public static final int OPEN_WINDOW = 5;
    public static final int CLOSE_WINDOW = 6;
    public static final int SUBMIT = 7;
    public static final int RESET = 8;
    public static final int GET_INPUT_VALUE = 9;
    public static final int SET_INPUT_VALUE = 10;
    public static final int GET_INPUT_CHECKED = 11;
    public static final int SET_INPUT_CHECKED = 12;
    public static final int GET_SELECTED_INDEX = 13;
    public static final int SET_SELECTED_INDEX = 14;
    public static final int GET_SELECTED_VALUE = 15;
    public static final int SET_SELECTED_VALUE = 16;
    public static final int GET_OPTION_VALUE = 17;
    public static final int SET_OPTION_VALUE = 18;
    public static final int GET_TEXTAREA_VALUE = 19;
    public static final int SET_TEXTAREA_VALUE = 20;
    public static final int SET_SUBMIT_ACTION = 21;
    public static final int SET_VISIBILITY = 22;
    public static final int HISTORY_BACK = 23;
    public static final int HISTORY_FORWARD = 24;
    public static final int LOCATION_RELOAD = 25;
    
    
    public static final String[] ACTION_NAME =
        {"NO_ACTION",
        "GET_COOKIE",
        "SET_COOKIE",
        "GET_LOCATION",
        "SET_LOCATION",
        "OPEN_WINDOW",
        "CLOSE_WINDOW",
        "SUBMIT",
        "RESET",
        "GET_INPUT_VALUE",
        "SET_INPUT_VALUE",
        "GET_INPUT_CHECKED",
        "SET_INPUT_CHECKED",
        "GET_SELECTED_INDEX",
        "SET_SELECTED_INDEX",
        "GET_SELECTED_VALUE",
        "SET_SELECTED_VALUE",
        "GET_OPTION_VALUE",
        "SET_OPTION_VALUE",
        "GET_TEXTAREA_VALUE",
        "SET_TEXTAREA_VALUE",
        "SET_SUBMIT_ACTION",
        "SET_VISIBILITY",
        "HISTORY_BACK",
        "HISTORY_FORWARD",
        "LOCATION_RELOAD"
        };
    
    
    int m_actionType = JSAction.NO_ACTION;
    String m_actor = "";
    String m_value = null;
    String m_targetId = null;
        
    public JSAction
        (int actionType, String actor, String value, String targetId) {
        m_actionType = actionType;
        m_actor = actor;
        m_value = value;
        m_targetId = targetId;
        //JSDebug.println(toString());
        
    }
    
    public String toString() {
        return "actionType : " + ACTION_NAME[m_actionType]
                + " ;  actor : " + m_actor
                + " ;  value : " + m_value
                + " ;  targetId : " + m_targetId;
    }
}
