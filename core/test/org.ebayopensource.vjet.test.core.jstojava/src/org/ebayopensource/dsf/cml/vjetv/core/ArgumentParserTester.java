/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: ArgumentParserTester1.java, Jan 11, 2010, 8:43:09 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.cml.vjetv.core;

import java.io.File;
import java.util.Iterator;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.IVjoSemanticRule;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.IVjoSemanticRuleSet;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.VjoSemanticRuleRepo;
import org.ebayopensource.dsf.jstojava.cml.vjetv.core.ArgumentsParser;
import org.ebayopensource.dsf.jstojava.cml.vjetv.model.IHeadlessParserConfigure;
import org.ebayopensource.dsf.jstojava.cml.vjetv.model.impl.HeadlessParserConfigure;
import org.ebayopensource.dsf.jstojava.cml.vjetv.util.FileOperator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Class/Interface description
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class ArgumentParserTester {

    public final static String CURRENTPATH = System
            .getProperty(FileOperator.USER_DIR);

    HeadlessParserConfigure m_conf = null;

    public final static String BLANK = ArgumentsParser.BLANK;

    ArgumentsParser m_parser = new ArgumentsParser();

    @Before
    public void setUP() {
        m_conf = new HeadlessParserConfigure();
    }

    @Test
    public void test1() {
        String command = "testFiles" + File.separator + "access";
        m_parser.parser(command.split(BLANK), m_conf);
        m_parser.initEnv(m_conf);
        Assert.assertEquals(83, m_conf.getValidatedJSFiles().size());
    }

    @Test
    public void test2() {
        String command = "testFiles" + File.separator + "access" + File.separator + "*.js";
        m_parser.parser(command.split(BLANK), m_conf);
        m_parser.initEnv(m_conf);
        Assert.assertEquals(83, m_conf.getValidatedJSFiles().size());
    }

    @Test
    public void test3() {
        String command = "testFiles" + File.separator + "access" + File.separator + "dollar" + File.separator + "*.js";
        m_parser.parser(command.split(BLANK), m_conf);
        m_parser.initEnv(m_conf);
        Assert.assertEquals(2, m_conf.getValidatedJSFiles().size());
    }

    @Test
    public void test4() {
        String command = "" + CURRENTPATH + "" + File.separator + "testFiles" + File.separator + "access" + File.separator + "dollar" + File.separator + "*.js";
        m_parser.parser(command.split(BLANK), m_conf);
        m_parser.initEnv(m_conf);
        Assert.assertEquals(2, m_conf.getValidatedJSFiles().size());
    }

    @Test
    public void test5() {
        String command = "" + CURRENTPATH + "" + File.separator + "testFiles" + File.separator + "access" + File.separator + "*.js";
        m_parser.parser(command.split(BLANK), m_conf);
        m_parser.initEnv(m_conf);
        Assert.assertEquals(83, m_conf.getValidatedJSFiles().size());
    }

    @Test
    public void test6() {
        String command = "" + CURRENTPATH + "" + File.separator + "testFiles" + File.separator + "access" + File.separator + "*.js "
                + CURRENTPATH + "" + File.separator + "testFiles" + File.separator + "access" + File.separator + "dollar" + File.separator + "*.js";
        m_parser.parser(command.split(BLANK), m_conf);
        m_parser.initEnv(m_conf);
        Assert.assertEquals(83, m_conf.getValidatedJSFiles().size());
    }

    @Test
    @Ignore("exits program")
    public void test7() {
        String command = "" + CURRENTPATH + "" + File.separator + "testFiles" + File.separator + "access" + File.separator + "*.js;"
                + CURRENTPATH + "" + File.separator + "testFiles" + File.separator + "access" + File.separator + "dollar" + File.separator + "*.js";
        m_parser.parser(command.split(BLANK), m_conf);
        m_parser.initEnv(m_conf);
        Assert.assertEquals(83, m_conf.getValidatedJSFiles().size());
    }

    @Test
    @Ignore("exits program")
    public void test8() {
        String command = "testFiles" + File.separator + "access" + File.separator + "*.js;testFiles" + File.separator + "access" + File.separator + "dollar" + File.separator + "*.js";
        m_parser.parser(command.split(BLANK), m_conf);
        m_parser.initEnv(m_conf);
        Assert.assertEquals(83, m_conf.getValidatedJSFiles().size());
    }

    @Test
    @Ignore("exits program")
    public void test9() {
        String command = "testFiles" + File.separator + "access" + File.separator + "*.js testFiles" + File.separator + "access" + File.separator + "dollar" + File.separator + "*.js";
        m_parser.parser(command.split(BLANK), m_conf);
        m_parser.initEnv(m_conf);
        Assert.assertEquals(83, m_conf.getValidatedJSFiles().size());
    }

    @Test
    public void test10() {
        String command = "testFiles" + File.separator + "access     testFiles" + File.separator + "access" + File.separator + "dollar ";
        m_parser.parser(command.split(BLANK), m_conf);
        m_parser.initEnv(m_conf);
        Assert.assertEquals(83, m_conf.getValidatedJSFiles().size());
    }

    @Test
    public void testBP1() {
        String command = "-bp testFiles " + CURRENTPATH
                + "" + File.separator + "testFiles" + File.separator + "access" + File.separator + "*.js";
        m_parser.parser(command.split(BLANK), m_conf);
        m_parser.initEnv(m_conf);
        Assert.assertEquals(83, m_conf.getValidatedJSFiles().size());
        Assert.assertEquals(1, m_conf.getBuildPath().size());
        for (Iterator iterator = m_conf.getBuildPath().iterator(); iterator
                .hasNext();) {
            File type = (File) iterator.next();
            System.out.println(type.getAbsolutePath());
        }
    }

    @Test
    public void testBP2() {
        String command = "-bp " + CURRENTPATH + "" + File.separator + "testFiles " + CURRENTPATH
                + "" + File.separator + "testFiles" + File.separator + "access" + File.separator + "*.js";
        m_parser.parser(command.split(BLANK), m_conf);
        m_parser.initEnv(m_conf);
        Assert.assertEquals(83, m_conf.getValidatedJSFiles().size());
        Assert.assertEquals(1, m_conf.getBuildPath().size());
    }

    @Test
    @Ignore("exits program")
    public void testBP3() {
        String command = "-bp testFiles;testFiles" + File.separator + "access " + CURRENTPATH
                + "" + File.separator + "testFiles" + File.separator + "access" + File.separator + "*.js";
        m_parser.parser(command.split(BLANK), m_conf);
        m_parser.initEnv(m_conf);
        Assert.assertEquals(83, m_conf.getValidatedJSFiles().size());
        Assert.assertEquals(2, m_conf.getBuildPath().size());
    }

    @Test
    @Ignore("exits program")
    public void testBP4() {
        String command = "-bp " + CURRENTPATH + "" + File.separator + "testFiles;" + CURRENTPATH
                + "" + File.separator + "testFiles" + File.separator + "access " + CURRENTPATH
                + "" + File.separator + "testFiles" + File.separator + "access" + File.separator + "*.js";
        m_parser.parser(command.split(BLANK), m_conf);
        m_parser.initEnv(m_conf);
        Assert.assertEquals(83, m_conf.getValidatedJSFiles().size());
        Assert.assertEquals(2, m_conf.getBuildPath().size());
        for (Iterator iterator = m_conf.getBuildPath().iterator(); iterator
                .hasNext();) {
            File type = (File) iterator.next();
            System.out.println(type.getAbsolutePath());
        }
    }

    @Test
    @Ignore("exits program")
    public void testBP5() {
        String command = "-bp " + CURRENTPATH + "" + File.separator + "testBp" + File.separator + "*.jar;"
                + CURRENTPATH + "" + File.separator + "testFiles" + File.separator + "access " + CURRENTPATH
                + "" + File.separator + "testFiles" + File.separator + "access" + File.separator + "*.js";
        m_parser.parser(command.split(BLANK), m_conf);
        m_parser.initEnv(m_conf);
        Assert.assertEquals(83, m_conf.getValidatedJSFiles().size());
        Assert.assertEquals(6, m_conf.getBuildPath().size());
        for (Iterator iterator = m_conf.getBuildPath().iterator(); iterator
                .hasNext();) {
            File type = (File) iterator.next();
            System.out.println(type.getAbsolutePath());
        }
    }

    public void testBP6() {
        String command = "-bp " + CURRENTPATH + "" + File.separator + "testBp" + File.separator + "*.JAR;"
                + CURRENTPATH + "" + File.separator + "testFiles" + File.separator + "access " + CURRENTPATH
                + "" + File.separator + "testFiles" + File.separator + "access" + File.separator + "*.js";
        m_parser.parser(command.split(BLANK), m_conf);
        m_parser.initEnv(m_conf);
        Assert.assertEquals(83, m_conf.getValidatedJSFiles().size());
        Assert.assertEquals(6, m_conf.getBuildPath().size());
        for (Iterator iterator = m_conf.getBuildPath().iterator(); iterator
                .hasNext();) {
            File type = (File) iterator.next();
            System.out.println(type.getAbsolutePath());
        }
    }

    @Test
    public void testArgFile1() {
        String command = "@testBp" + File.separator + "ArgFile1.txt";
        m_parser.parser(command.split(BLANK), m_conf);
        m_parser.initEnv(m_conf);
        Assert.assertEquals(2, m_conf.getValidatedJSFiles().size());
    }

    @Test
    public void testArgFile2() {
        File f = new File("testBp" + File.separator + "ArgFile2.txt");
        String s = m_parser.readArgFile(f);
        String[] ss = s.split(BLANK);
        Assert.assertEquals(3, ss.length);
    }

    @Test
    @Ignore("argument mismatch")
    public void testArgFile3() {
        File f = new File("testBp" + File.separator + "ArgFile3.txt");
        String s = m_parser.readArgFile(f);
        String[] ss = s.split(BLANK);
        Assert.assertEquals(3, ss.length);
        m_parser.parser(ss, m_conf);
        m_parser.initEnv(m_conf);
        Assert.assertEquals(83, m_conf.getValidatedJSFiles().size());
    }

    @Test
    @Ignore("exits program")
    public void testArgFile3A() {
        String command = "@testBp" + File.separator + "ArgFile3.txt";
        String[] ss = command.split(BLANK);
        m_parser.parser(ss, m_conf);
        m_parser.initEnv(m_conf);
        Assert.assertEquals(83, m_conf.getValidatedJSFiles().size());
    }

    public void testArgFile4() {
        File f = new File("testBp" + File.separator + "ArgFile4.txt");
        String s = m_parser.readArgFile(f);
        String[] ss = s.split(BLANK);
        Assert.assertEquals(3, ss.length);
        m_parser.parser(ss, m_conf);
        m_parser.initEnv(m_conf);
        Assert.assertEquals(1047, m_conf.getValidatedJSFiles().size());
    }

    public void testArgFile4A() {
        String command = "@testBp" + File.separator + "ArgFile4.txt";
        String[] ss = command.split(BLANK);
        m_parser.parser(ss, m_conf);
        m_parser.initEnv(m_conf);
        Assert.assertEquals(1047, m_conf.getValidatedJSFiles().size());
    }

    public void testArgFile5() {
        String command = "@testBp" + File.separator + "ArgFile4.txt @testBp" + File.separator + "ArgFile3.txt";
        String[] ss = command.split(BLANK);
        m_parser.parser(ss, m_conf);
        m_parser.initEnv(m_conf);
        Assert.assertEquals(1047, m_conf.getValidatedJSFiles().size());
    }

    @Test
    public void testArgFile5A() {
        String command = "@testBp" + File.separator + "ArgFile2.txt  ";
        String[] ss = command.split(BLANK);
        m_parser.parser(ss, m_conf);
        m_parser.initEnv(m_conf);
        Assert.assertEquals(2, m_conf.getValidatedJSFiles().size());
    }

    @Test
    @Ignore("exits program")
    public void testArgFile5B() {
        String command = " @testBp" + File.separator + "ArgFile5.txt";
        String[] ss = command.split(BLANK);
        m_parser.parser(ss, m_conf);
        m_parser.initEnv(m_conf);
        Assert.assertEquals(10, m_conf.getValidatedJSFiles().size());
    }

    @Test
    @Ignore("exits program")
    public void testArgFile5C() {
        String command = "@testBp" + File.separator + "ArgFile2.txt @testBp" + File.separator + "ArgFile5.txt";
        String[] ss = command.split(BLANK);
        m_parser.parser(ss, m_conf);
        m_parser.initEnv(m_conf);
        Assert.assertEquals(12, m_conf.getValidatedJSFiles().size());
        Assert.assertEquals(3, m_conf.getBuildPath().size());
    }

    @Test
    @Ignore("exits program")
    public void testArgFile5D() {
        String command = "@testBp" + File.separator + "ArgFile2.txt @testBp" + File.separator + "ArgFile6.txt";
        String[] ss = command.split(BLANK);
        m_parser.parser(ss, m_conf);
        m_parser.initEnv(m_conf);
        Assert.assertEquals(12, m_conf.getValidatedJSFiles().size());
        Assert.assertEquals(6, m_conf.getBuildPath().size());
    }

    @Test
    @Ignore("exits program")
    public void testArgFile6() {
        String command = "@testBp" + File.separator + "ArgFile2.txt @testBp" + File.separator + "ArgFile6.txt -nw";
        String[] ss = command.split(BLANK);
        m_parser.parser(ss, m_conf);
        m_parser.initEnv(m_conf);
        Assert.assertEquals(12, m_conf.getValidatedJSFiles().size());
        Assert.assertEquals(6, m_conf.getBuildPath().size());
        Assert.assertEquals(IHeadlessParserConfigure.ERROR, m_conf
                .getReportLevel());
    }

    @Test
    @Ignore("unsupported feature right now")
    public void testImportPolicy() {
        String command = "-policy " + CURRENTPATH + File.separatorChar
                + "testBp" + File.separatorChar + "policy.pref";
        String[] ss = command.split(BLANK);
        m_parser.parser(ss, m_conf);
        m_parser.initEnv(m_conf);
        VjoSemanticRuleRepo ruleRepo = VjoSemanticRuleRepo.getInstance();
        for (IVjoSemanticRuleSet ruleSet : ruleRepo.getRuleSets()) {
            String ruleSetName = ruleSet.getRuleSetName();
            for (IVjoSemanticRule<?> rule : ruleSet.getRules()) {
                if (ruleSetName.equalsIgnoreCase("TYPE_CHECK")
                        && rule.getRuleName().equalsIgnoreCase("GENERIC_PARAM_NUM_MISMATCH")) {
                    Assert.assertEquals("warning", rule.getGlobalRulePolicy()
                            .getProblemSeverity(null).toString());
                }
            }
        }
    }
}

