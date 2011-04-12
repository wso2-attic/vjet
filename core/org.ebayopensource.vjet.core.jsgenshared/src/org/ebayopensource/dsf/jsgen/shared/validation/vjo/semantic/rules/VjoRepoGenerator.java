/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.IVjoSemanticRule;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.IVjoSemanticRuleSet;

public class VjoRepoGenerator  {

	public void setUp(){
//		Logger.initLogProperties("logging.properties");
	}
	
	public static void main(String[] args) {
	    testPolicy();
    }
	
	public static void testRuleRepo(){
		
		String prefix = "public static final PreferenceKey PREF_PB_";
		
		StringBuffer declairation = new StringBuffer();
		
		StringBuffer getKeys = new StringBuffer("return new Key[] { ");
		StringBuffer storeValue = new StringBuffer();
		
		StringBuffer content = new StringBuffer();

		
		String ruleSetName = "";
		String ruleName = ""; 
			
		for(IVjoSemanticRuleSet ruleSet : VjoSemanticRuleRepo.getInstance().getRuleSets()){
			ruleSetName = ruleSet.getRuleSetName();

			for(IVjoSemanticRule<?> rule : ruleSet.getRules()){
				ruleName = rule.getRuleName().toUpperCase();
				ruleName = ruleName.replace(" ", "_");
				declairation.append(prefix).append(ruleName)
					.append(" = getVJETCoreKey(\"").append(ruleSetName).append(".")
					.append(rule.getRuleName()).append("\");\n");
				getKeys.append(" PREF_PB_").append(ruleName).append(",\n");
				storeValue.append(" storeValue( PREF_PB_").append(ruleName).append(");\n");
				content.append("if (keyName.equals(PREF_PB_").append(ruleName).append(".getName())) {\n")
					.append("	addComboBox(inner, label, PREF_PB_").append(ruleName).append(",\n")
					.append("		errorWarningIgnore, errorWarningIgnoreLabels,\n")
					.append("		defaultIndent);\n")
					.append("}\n");
			}
		}
		
		getKeys.deleteCharAt(getKeys.length() -2 ).append("};");
//		System.out.print(declairation);
//		System.out.println(getKeys);
//		System.out.print(storeValue);
//		System.out.print(content);
		
		
		
		ruleSetName = "Accessibility";
		ruleName = "Property_Is_Not_Visible";
		
//		assertNotNull(VjoSemanticRuleRepo.getInstance().getRule(ruleSetName, ruleName));
	}
	
	
	public static void testPolicy(){
//	    String ruleName = null;
//	    String ruleSetName = null;
//	    for(IVjoSemanticRuleSet ruleSet : VjoSemanticRuleRepo.getInstance().getRuleSets()){
//            ruleSetName = ruleSet.getRuleSetName();
//
//            for(IVjoSemanticRule<?> rule : ruleSet.getRules()){
//                ruleName = rule.getRuleName().toUpperCase();
////                System.out.println(rule); 
////                System.out.println(rule.getRulePolicy(null).getProblemSeverity(null));
////                System.out.println("========================================");
//            }
//	    }
	}
}
