/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;

import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjet.eclipse.core.search.VjoMatch;
import org.ebayopensource.vjet.eclipse.core.test.contentassist.TestConstants;
import org.ebayopensource.vjet.eclipse.internal.ui.search.VjoFindOccurencesEngine;

public class AbstractMarkOccurencesTests extends AbstractVjoModelTests {

	protected void basicTest(IJSSourceModule module, IRegion region,
			List<Region> matches) throws ModelException {
		assertNotNull("Invalid file content, cant find position", region);

		ArrayList<VjoMatch> results = new ArrayList<VjoMatch>();

		VjoFindOccurencesEngine engine = new VjoFindOccurencesEngine();
		List<VjoMatch> arr = engine.findOccurences(module, region);
		assertNotNull("Empty result", arr);
		assertFalse(arr.isEmpty());

		VjoMatch[] vjoMatchs = removeDuplicates(arr);
		Collections.addAll(results, vjoMatchs);

		//assertEquals("Wrong number of matches", matches.size(), results.size());
		compareResults(results, matches, module);
	}

	protected int getOffset(IJSSourceModule module, String sentence)
			throws ModelException {
		int offset = sentence.indexOf("<cursor>");
		assertNotSame("invalid sentence, can't find <cursor>  -- " + sentence,
				-1, offset);

		String realSentence = sentence.replace("<cursor>", "");

		int position = module.getSource().indexOf(realSentence);

		assertNotSame("invalid sentence, can't find in js file" + realSentence,
				-1, position);

		int wholeOffset = position + offset;
		return wholeOffset;
	}

	private VjoMatch[] removeDuplicates(List<VjoMatch> matches) {

		Set<VjoMatch> matchSet = new HashSet<VjoMatch>();
		matchSet.addAll(matches);
		return (VjoMatch[]) matchSet.toArray(new VjoMatch[matchSet.size()]);
	}

	protected void compareResults(ArrayList<VjoMatch> results,
			List<Region> matches, IJSSourceModule module) throws ModelException {

		VjoMatch match;

		boolean hasMatch = true;
		
		for (int i = 0; i < results.size(); i++) {
			match = results.get(i);
			boolean itemMatch = false;
			for (Region region2 : matches) {
				if (region2.getOffset() == match.getOffset()
						&& region2.getLength() == match.getLength()) {
					itemMatch = true;
					matches.remove(region2);
					break;
				}
			}

			if (!itemMatch) {
				hasMatch = false;
				pintOccurenceMarker(module.getSource(), "Wrong match: ",
							match.getOffset(), match.getLength());
			}
		}

		if (matches.size() > 0) {
			hasMatch = false;
			for (Region region2: matches) {
				pintOccurenceMarker(module.getSource(), "Not matched : ",
						region2.getOffset(), region2.getLength());
			}
		}
		assertTrue(hasMatch);
	}

	private void pintOccurenceMarker(String content, String prefix, int offset, int length) {
		if (offset == -1 ) {
			System.out.println("Match's offset is -1!");
			return;
		}
		
		int lineBegin = content.substring(0, offset).lastIndexOf("\n");
		if (lineBegin == -1) {
			lineBegin = 0;
		} else {
			lineBegin ++;
		}
		
		int lineEnd = content.substring(offset + length).indexOf("\n");
		
		if (lineEnd == -1) {
			lineEnd = offset + length;
		} else {
			lineEnd = offset + length + lineEnd -1 ;  
		}
		
		String lineContent = content.substring(lineBegin, offset)
			+ "<cursor>" + content.substring(offset, lineEnd);
		
		System.out.println(prefix + lineContent);

	}
	
	protected Region getFirstRegionInFile(String string, IJSSourceModule module)
			throws ModelException {
		String content = module.getSource();

		int position = content.indexOf(string);

		if (position >= 0) {
			return new Region(position, string.length());
		}
		return null;
	}

	protected Region getLastRegionInFile(String string, IJSSourceModule module)
			throws ModelException {
		String content = module.getSource();

		int position = content.lastIndexOf(string);

		if (position >= 0) {
			return new Region(position, string.length());
		}

		return null;
	}

	public static List<Region> getPositions(String source, String name) {
		int offset = 0;
		List<Region> matches = new ArrayList<Region>();

		while (offset < source.length()) {
			if (source.indexOf(name, offset) >= 0) {
				offset = source.indexOf(name, offset);
				matches.add(new Region(offset, name.length()));
				offset += name.length();
			} else
				break;
		}

		return matches;
	}

	public static List<Region> getPositions(String source, String name,
			String[] excludeNames) {
		int offset = 0;
		List<Region> matches = new ArrayList<Region>();

		while (offset < source.length()) {
			if (source.indexOf(name, offset) >= 0) {
				int newOffset = source.indexOf(name, offset);
				boolean isExclude = false;
				for (String excludeName: excludeNames) {
					int exlength= excludeName.indexOf(name);
					int exStart = newOffset - exlength;
					int exEnd = exStart + excludeName.length();
					if (exStart >=0 &&
							exEnd <= source.length()
							&& source.substring(exStart, exEnd).equals(excludeName)) {
						isExclude = true;
						break;
					}
				}
				if (!isExclude){
					matches.add(new Region(newOffset, name.length()));
				}

				offset = newOffset + name.length();
			} else
				break;
		}

		return matches;
	}

	public static void correctPosition(List<Region> matches, String word,
			int position) {
		Region region = matches.get(position);
		int lengh = word.length();
		matches.set(position, new Region(region.getOffset() - lengh, region
				.getLength()
				+ lengh));
	}

	protected String getProjectName() {
		return TestConstants.PROJECT_NAME_VJETPROJECT;
	}
}
