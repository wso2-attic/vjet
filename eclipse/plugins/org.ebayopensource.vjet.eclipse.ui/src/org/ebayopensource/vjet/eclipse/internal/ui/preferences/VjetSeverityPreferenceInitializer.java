/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: VjetSeverityPreferenceInitializer.java, Jun 25, 2009, 1:45:21 AM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.vjet.eclipse.internal.ui.preferences;
import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.IVjoSemanticRule;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.IVjoSemanticRuleSet;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.VjoSemanticRuleRepo;
import org.ebayopensource.vjet.eclipse.ui.VjetUIPlugin;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.dltk.mod.ui.preferences.PreferenceKey;

/**
 * Class/Interface description
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class VjetSeverityPreferenceInitializer {

private static PreferenceKey[] preferenceKeys = null;
    
private static PreferenceKey[] getPreferenceKeys () {
        if (preferenceKeys == null) {
            initKeys();
        }
        return preferenceKeys;
    }
    
    private static void initKeys() {
        VjoSemanticRuleRepo ruleRepo = VjoSemanticRuleRepo.getInstance();
        ruleRepo.restoreDefaultPolicies();
        String keyName = "";
        List<PreferenceKey> list = new ArrayList<PreferenceKey>();
        for (IVjoSemanticRuleSet ruleSet : ruleRepo.getRuleSets()) {
            for (IVjoSemanticRule<?> rule : ruleSet.getRules()) {
                keyName = ruleSet.getRuleSetName() + "." + rule.getRuleName();
                list.add(new PreferenceKey(VjetUIPlugin.PLUGIN_ID, keyName));
            }
        }
        preferenceKeys = new PreferenceKey[list.size()];
        preferenceKeys = list.toArray(preferenceKeys);
    }

    private static void initSeriorityPreferenceKeys(Preferences pluginPreferences) {
        VjoSemanticRuleRepo ruleRepo = VjoSemanticRuleRepo.getInstance();
        ruleRepo.restoreDefaultPolicies();
        String keyName = "";
        for (IVjoSemanticRuleSet ruleSet : ruleRepo.getRuleSets()) {
            for (IVjoSemanticRule<?> rule : ruleSet.getRules()) {
                keyName = ruleSet.getRuleSetName() + "." + rule.getRuleName();
                pluginPreferences.setDefault(keyName, rule.getGlobalRulePolicy()
                        .getProblemSeverity(null).toString());
            }
        }
    }
}
