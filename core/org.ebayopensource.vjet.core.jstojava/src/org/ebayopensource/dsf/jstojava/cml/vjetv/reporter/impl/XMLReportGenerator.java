/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: XMLReportGenerator.java, Jan 28, 2010, 6:58:55 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jstojava.cml.vjetv.reporter.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.ebayopensource.dsf.common.xml.XmlStreamWriter;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.ProblemSeverity;
import org.ebayopensource.dsf.jstojava.cml.vjetv.core.ArgumentsParser;
import org.ebayopensource.dsf.jstojava.cml.vjetv.model.IHeadlessLauncherConfigure;
import org.ebayopensource.dsf.jstojava.cml.vjetv.model.IHeadlessParserConfigure;
import org.ebayopensource.dsf.jstojava.cml.vjetv.model.impl.EVLauncherResult;
import org.ebayopensource.dsf.jstojava.cml.vjetv.util.FileOperator;


/**
 * Class/Interface description
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class XMLReportGenerator {

    /**
     * Generate total status
     * 
     * @param result
     *            {@link EVLauncherResult}
     * @param xsw
     *            {@link XmlStreamWriter}
     * @param conf
     */
    private static void generateTotalStatus(EVLauncherResult result,
            XmlStreamWriter xsw, IHeadlessLauncherConfigure conf) {
        xsw.writeStartElement("TotoalResult");
        createSingleElement(xsw, "ValidatedFiles", "value", conf
                .getValidatedJSFiles().size()
                + "", "Total number of files while validating");
        createSingleElement(xsw, "TotalErrors", "value", result
                .getErrorNumber()
                + "", "Total number of erors in whole vjetv operation");
        createSingleElement(xsw, "TotalWarnings", "value", result
                .getWarningNumber()
                + "", "Total number of warnings in whole vjetv operation");
        createSingleElement(xsw, "StartTime", "value", result.getStartTime()
                + "", null);
        createSingleElement(xsw, "EndTime", "value", result.getEndTime() + "",
                null);
        xsw.writeEndElement();
    }

    /**
     * 
     * Generate all js files
     * 
     * @param result
     *            {@link EVLauncherResult}
     * @param xsw
     *            {@link XmlStreamWriter}
     * @param reportLevel
     *            String
     */
    private static void generateFiles(EVLauncherResult result,
            XmlStreamWriter xsw, String reportLevel) {
        HashMap<File, Object> data = result.getReportData();
        if (data == null)
            return;
        Collection<File> collections = data.keySet();
        File file = null;
        Object value = null;
        xsw.writeStartElement("data");
        xsw.writeComment("Problems detail metadata");
        for (Iterator<File> iterator = collections.iterator(); iterator
                .hasNext();) {
            file = iterator.next();
            value = data.get(file);
            xsw.writeStartElement("ValidatedFiles");
            xsw.writeAttribute("Location", file.getAbsolutePath());
            if (value instanceof List) {
                generateValidatedFileWtihProblems(
                        (List<VjoSemanticProblem>) value, xsw, file,
                        reportLevel);
            } else {
                generateValidatedFileWtihException(value.toString(), xsw, file);
            }

            xsw.writeEndElement();
        }
        xsw.writeEndElement();
    }

    /**
     * Generate validated files problems
     * 
     * @param actualProblemList
     *            {@link List}
     * @param xsw
     *            {@link XmlStreamWriter}
     * @param validatedFile
     *            {@link File}
     * @param reportLevel
     *            String
     */
    private static void generateValidatedFileWtihProblems(
            List<VjoSemanticProblem> actualProblemList, XmlStreamWriter xsw,
            File validatedFile, String reportLevel) {
        String sources = FileOperator.getSourceFromFile(validatedFile);
        Collections.sort(actualProblemList, BaseReporter.COMPARATOR);
        VjoSemanticProblem vjoSemanticProblem;
        for (Iterator<VjoSemanticProblem> iterator = actualProblemList
                .iterator(); iterator.hasNext();) {
            vjoSemanticProblem = iterator.next();
            if (vjoSemanticProblem.type().equals(ProblemSeverity.warning)
                    && IHeadlessParserConfigure.ERROR
                            .equalsIgnoreCase(reportLevel)) {
                continue;
            }
            generateProblem(vjoSemanticProblem, xsw, sources);
        }
    }

    /**
     * Generate validated file when vjetv meeting some exception
     * 
     * @param exceptionMessages
     *            {@link String}
     * @param xsw
     *            {@link XmlStreamWriter}
     * @param validatedFile
     *            {@link File}
     */
    private static void generateValidatedFileWtihException(
            String exceptionMessages, XmlStreamWriter xsw, File validatedFile) {
        createSingleElement(xsw, "Exception", null, exceptionMessages,
                "Exception when launching vjetv");
    }

    /**
     * Generate one problem
     * 
     * @param problem
     *            {@link VjoSemanticProblem}
     * @param xsw
     *            {@link XmlStreamWriter}
     * @param sources
     *            String
     */
    private static void generateProblem(VjoSemanticProblem problem,
            XmlStreamWriter xsw, String sources) {
        xsw.writeStartElement(problem.type().toString());
        createSingleElement(xsw, "id", null, problem.getID().getName(),
                "Problem ID");
        createSingleElement(xsw, "LineNumber", null, problem
                .getSourceLineNumber()
                + "", null);
        createSingleElement(xsw, "SourceCodes", null, FileOperator
                .getSourceLineFromFile(sources, problem.getSourceStart(),
                        problem.getSourceEnd() + 1).trim(), null);
        createSingleElement(xsw, "Messages", null, problem.getMessage().trim(),
                null);
        createSingleElement(xsw, "StartPosition", null, problem
                .getSourceStart()
                + "", null);
        createSingleElement(xsw, "EndPosition", null, problem.getSourceEnd()
                + "", null);
        xsw.writeEndElement();
    }

    /**
     * Create single element
     * 
     * @param xsw
     *            {@link XmlStreamWriter}
     * @param elementName
     *            {@link String}
     * @param attributeName
     *            {@link String}
     * @param value
     *            {@link String}
     * @param comment
     *            {@link String}
     */
    private static void createSingleElement(XmlStreamWriter xsw,
            String elementName, String attributeName, String value,
            String comment) {
        xsw.writeStartElement(elementName);
        if (attributeName != null) {
            xsw.writeAttribute(attributeName, value);
        } else {
            xsw.writeAttribute("value", value);
        }
        if (comment != null) {
            xsw.writeComment(comment);
        }
        xsw.writeEndElement();
    }

    /**
     * @param xsw
     *            void
     */
    private static void generateStartInformation(XmlStreamWriter xsw) {
        xsw.writeRaw("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xsw.writeComment("Ebay vjet validation Result reporter");
    }

    public static void generateXMLFile(IHeadlessLauncherConfigure conf,
            EVLauncherResult result) {
        String reportLevel = conf.getReportLevel();
        File reportedFile = new File(conf.getReportPath());
        FileWriter fw;
        try {
            if (ArgumentsParser.isPathValid(reportedFile.getAbsolutePath(),
                    false)
                    && reportedFile.isDirectory()) {
                reportedFile = new File(reportedFile.getPath()
                        + File.separatorChar + "VjoValidation.xml");
            }
            fw = new FileWriter(reportedFile);
            XmlStreamWriter xsw = new XmlStreamWriter(fw);
            generateStartInformation(xsw);
            xsw.writeStartElement("Report");
            xsw.writeComment("Overall statistics for vjetv results");
            generateTotalStatus(result, xsw, conf);
            generateFiles(result, xsw, reportLevel);
            xsw.writeEndElement();
            xsw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
