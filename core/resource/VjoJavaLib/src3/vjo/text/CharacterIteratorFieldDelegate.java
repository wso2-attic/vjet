package vjo.java.text;

/*
 * @(#)src/classes/sov/java/text/CharacterIteratorFieldDelegate.java, core, asdev, 20070119 1.6
 * ===========================================================================
 * Licensed Materials - Property of IBM
 * "Restricted Materials of IBM"
 *
 * IBM SDK, Java(tm) 2 Technology Edition, v5.0
 * (C) Copyright IBM Corp. 1998, 2005. All Rights Reserved
 * ===========================================================================
 */

/*
 * ===========================================================================
 (C) Copyright Sun Microsystems Inc, 1992, 2004. All rights reserved.
 * ===========================================================================
 */

import vjo.java.lang.* ;
import vjo.java.lang.Math ;
import vjo.java.lang.StringBuffer ;

import java.text.AttributedCharacterIterator;

import vjo.java.util.ArrayList;

/**
 * CharacterIteratorFieldDelegate combines the notifications from a Format
 * into a resulting <code>AttributedCharacterIterator</code>. The resulting
 * <code>AttributedCharacterIterator</code> can be retrieved by way of
 * the <code>getIterator</code> method.
 *
 * @version 1.1 10/06/00
 */
class CharacterIteratorFieldDelegate implements Format.FieldDelegate {
    /**
     * Array of AttributeStrings. Whenever <code>formatted</code> is invoked
     * for a region > size, a new instance of AttributedString is added to
     * attributedStrings. Subsequent invocations of <code>formatted</code>
     * for existing regions result in invoking addAttribute on the existing
     * AttributedStrings.
     */
    private ArrayList attributedStrings;
    /**
     * Running count of the number of characters that have
     * been encountered.
     */
    private int size;


    CharacterIteratorFieldDelegate() {
        attributedStrings = new ArrayList();
    }

    public void formatted(Format.Field attr, Object value, int start, int end,
    		StringBuffer buffer) {
        if (start != end) {
            if (start < size) {
                // Adjust attributes of existing runs
                int index = size;
                int asIndex = attributedStrings.size() - 1;

                while (start < index) {
                    AttributedString as = (AttributedString)attributedStrings.
                                           get(asIndex--);
                    int newIndex = index - as.length();
                    int aStart = Math.max(0, start - newIndex);

                    as.addAttribute(attr, value, aStart, Math.min(
                                    end - start, as.length() - aStart) +
                                    aStart);
                    index = newIndex;
                }
            }
            if (size < start) {
                // Pad attributes
                attributedStrings.add(new AttributedString(
                                          buffer.substring(size, start)));
                size = start;
            }
            if (size < end) {
                // Add new string
                int aStart = Math.max(start, size);
                AttributedString string = new AttributedString(
                                   buffer.substring(aStart, end));

                string.addAttribute(attr, value);
                attributedStrings.add(string);
                size = end;
            }
        }
    }

    public void formatted(int fieldID, Format.Field attr, Object value,
                          int start, int end, StringBuffer buffer) {
        formatted(attr, value, start, end, buffer);
    }

    /**
     * Returns an <code>AttributedCharacterIterator</code> that can be used
     * to iterate over the resulting formatted String.
     *
     * @pararm string Result of formatting.
     */
    public AttributedCharacterIterator getIterator(String string) {
        // Add the last AttributedCharacterIterator if necessary
        // assert(size <= string.length());
        if (string.length() > size) {
            attributedStrings.add(new AttributedString(
                                  string.substring(size)));
            size = string.length();
        }
        int iCount = attributedStrings.size();
        AttributedCharacterIterator iterators[] = new
                                    AttributedCharacterIterator[iCount];

        for (int counter = 0; counter < iCount; counter++) {
            iterators[counter] = ((AttributedString)attributedStrings.
                                  get(counter)).getIterator();
        }
        return new AttributedString(iterators).getIterator();
    }
}

