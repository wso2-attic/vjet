/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.policy;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
/**
 * Helper class for wild card comparision.  Holds list of wildcard string 
 * and provide method to check wildcard matching from the list
 * 
 *
 */
public class WildcardList {
	/**
	 * List to hold wildcard string
	 */
	List<String> wildcardList = new ArrayList<String>();
	/**
	 * This is populated parallely with wildcardList. This holds
	 * regular expression of wildcard string in wildcardList.  
	 * Used internally for wildcard comparision
	 */
	private List<String> wildcardRegularExpressionList = new ArrayList<String>();
	
	/**
	 * Add wildcard string to wildcard list
	 * @param wildcardString
	 */
	public void addWildcard(String wildcardString){
		wildcardList.add(wildcardString);
		//populate matching regular expression for the wildcard string
		wildcardRegularExpressionList.add(wildcardToRegex(wildcardString));
	}
	
	/**
	 * Return true if the given matchingString match with the wildcard list.
	 * This uses regular expression list to match the pattern
	 * @param matchingString
	 * @return true if match else false
	 */
	public boolean contains(String matchingString){
		for(String wildcardRegularExpression: wildcardRegularExpressionList){
			if(Pattern.matches(wildcardRegularExpression, matchingString)) return true;
		}
		return false;	
	}
	
	
	
	/**
	 * Convert the wildcard string to regular expression string for pattern match.
	 * @param wildcard 
	 * @return regular expression string 
	 */
	public static String wildcardToRegex(String wildcard){
        StringBuffer s = new StringBuffer(wildcard.length());
        s.append('^');
        for (int i = 0, is = wildcard.length(); i < is; i++) {
            char c = wildcard.charAt(i);
            switch(c) {
                case '*':
                    s.append(".*");
                    break;
                case '?':
                    s.append(".");
                    break;
                    // escape special regexp-characters
                case '(': case ')': case '[': case ']': case '$':
                case '^': case '.': case '{': case '}': case '|':
                case '\\':
                    s.append("\\");
                    s.append(c);
                    break;
                default:
                    s.append(c);
                    break;
            }
        }
        s.append('$');
        return(s.toString());
    }
	
	public static void main(String[] args) {
		WildcardList wildcardList = new WildcardList();
		wildcardList.addWildcard("*.test.*.*");
		
		System.out.println(wildcardList.contains("pkg.test.Abc.java"));
		System.out.println(wildcardList.contains("pkg.1test.Abc.java"));
		System.out.println(wildcardList.contains("pkg.test.Abc.text"));
	}

}
