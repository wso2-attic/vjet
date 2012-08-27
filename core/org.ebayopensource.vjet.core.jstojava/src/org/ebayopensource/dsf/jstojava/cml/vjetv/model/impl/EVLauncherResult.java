/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/

package org.ebayopensource.dsf.jstojava.cml.vjetv.model.impl;

import java.util.HashMap;

import org.ebayopensource.dsf.jstojava.cml.vjetv.model.IHeadLessLauncherResult;

/**
 * Class/Interface description
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class EVLauncherResult implements IHeadLessLauncherResult {

    /**
     * The value is used for character storage.
     */
    HashMap m_mapObject = null;

    /**
     * The value is used for character storage.
     */
    String m_resultInformation = null;

    /**
     * The value is used for character storage.
     */
    String startTime = null;

    /**
     * The value is used for character storage.
     */
    String endTime = null;

    /**
     * The value is used for character storage.
     */
    private int m_errorNumber = 0;

    /**
     * The value is used for character storage.
     */
    private int m_warningNumber = 0;

    /**
     * @return the errorNumber
     */
    public int getErrorNumber() {
        return m_errorNumber;
    }

    /**
     * @param errorNumber
     *            the errorNumber to set
     */
    public void setErrorNumber(int errorNumber) {
        this.m_errorNumber = errorNumber;
    }

    /**
     * @return the warningNumber
     */
    public int getWarningNumber() {
        return m_warningNumber;
    }

    /**
     * @param warningNumber
     *            the warningNumber to set
     */
    public void setWarningNumber(int warningNumber) {
        this.m_warningNumber = warningNumber;
    }

    @Override
    public HashMap getReportData() {
        return m_mapObject;
    }

    @Override
    public String getResultInformation() {
        return m_resultInformation;
    }

    public void setResultMap(HashMap data) {
        this.m_mapObject = data;
    }

    public void setResultInformation(String resultInformation) {
        this.m_resultInformation = resultInformation;
    }

    public void setEndTime(String endTimeString) {
        this.endTime = endTimeString;
    }

    public void setStartTime(String startTimeString) {
        this.startTime = startTimeString;
    }

    /**
     * This method use to get startTime value
     * 
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * This method use to get endTime value
     * 
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }

}
