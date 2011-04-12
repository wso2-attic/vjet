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

public class ExclusionPolicyModel {
	private  List<Pkg> s_pkgs = new ArrayList<Pkg>();
	private  List<String> s_classes = new ArrayList<String>();
	private  List<String> s_classes_regExp = new ArrayList<String>();
	
	
	 boolean isClassExcluded(String clsName){
		for(String classNameExp: s_classes_regExp){
			if(Pattern.matches(classNameExp, clsName)){
				return true;
			}
		}				//		if (s_classes.contains(clsName)){
							//			return true;
							//		}		
		for (Pkg p: s_pkgs){
			if (p.containsClass(clsName)){
				return true;
			}
		}
		
		return false;
	}
	
	
	 void add(String className){
		s_classes.add(className);	
		s_classes_regExp.add(wildcardToRegex(className));
	}
	 void add(Pkg pkg) throws RuntimeException{			
		s_pkgs.add(pkg);		
	}
	/**
	 * Convert the wildcard string to regular expression string for pattern match.
	 * @param wildcard 
	 * @return regular expression string 
	 */
	public  String wildcardToRegex(String wildcard){
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
}
