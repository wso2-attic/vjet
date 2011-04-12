/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.vjo;

import static com.ebay.junitnexgen.category.Category.Groups.FAST;
import static com.ebay.junitnexgen.category.Category.Groups.P3;
import static com.ebay.junitnexgen.category.Category.Groups.UNIT;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.IVjoSemanticRule;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.IVjoSemanticRuleSet;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoGroupRulesCache;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticRulePolicy;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoValidationDriver;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoValidationResult;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.VjoSemanticRuleRepo;
import org.ebayopensource.dsf.jst.IJstParseController;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.ts.IJstTypeLoader;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.jstojava.controller.JstParseController;
import org.ebayopensource.dsf.jstojava.loader.DefaultJstTypeLoader;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.ts.type.TypeName;
import com.ebay.junitnexgen.category.Category;
import com.ebay.junitnexgen.category.Description;
import com.ebay.junitnexgen.category.ModuleInfo;
import com.ebay.kernel.resource.ResourceUtil;
import org.ebayopensource.vjo.lib.TsLibLoader;

@Category( { P3, FAST, UNIT })
@ModuleInfo(value = "DsfPrebuild", subModuleId = "JsToJava")
public class VjoValidationTest {

    @Before
    public void setUp() {
        // Logger.initLogProperties("logging.properties");
    }

    @Test
    @Category( { P3, FAST, UNIT })
    @Description("Test vjo semantic rule is not null")
    public void testRuleRepo() {
        Assert.assertNotNull(VjoSemanticRuleRepo.getInstance());
        Assert.assertNotNull(VjoSemanticRuleRepo.getInstance().getRuleSets());

        for (IVjoSemanticRuleSet ruleSet : VjoSemanticRuleRepo.getInstance()
                .getRuleSets()) {
            Assert.assertNotNull(ruleSet);
            Assert.assertNotNull(ruleSet.getRuleSetName());
            Assert.assertNotNull(ruleSet.getRuleSetDescription());

            Assert.assertNotNull(ruleSet.getRules());
            for (IVjoSemanticRule<?> rule : ruleSet.getRules()) {
                Assert.assertNotNull(rule.getRuleName());
                Assert.assertNotNull(rule.getRuleDescription());
                Assert.assertNotNull(rule.getProblemId());
            }
        }

        final String ruleSetName = "Accessibility";
        final String ruleName = "Property_Is_Not_Visible";

        Assert.assertNotNull(VjoSemanticRuleRepo.getInstance().getRule(
                ruleSetName, ruleName));
    }

    IJstParseController getController() {
        return new JstParseController(new VjoParser());
    }

    IJstTypeLoader getLoader() {
        return new DefaultJstTypeLoader();
    }


    @After
    public void tearDown() {

    }
}
