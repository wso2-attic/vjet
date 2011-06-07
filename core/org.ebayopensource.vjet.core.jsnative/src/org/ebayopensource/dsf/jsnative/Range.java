package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.Property;

/*
 * // Introduced in DOM Level 2:
 interface Range {
 readonly attribute Node             startContainer;
 // raises(DOMException) on retrieval

 readonly attribute long             startOffset;
 // raises(DOMException) on retrieval

 readonly attribute Node             endContainer;
 // raises(DOMException) on retrieval

 readonly attribute long             endOffset;
 // raises(DOMException) on retrieval

 readonly attribute boolean          collapsed;
 // raises(DOMException) on retrieval

 readonly attribute Node             commonAncestorContainer;
 // raises(DOMException) on retrieval

 void               setStart(in Node refNode, 
 in long offset)
 raises(RangeException, 
 DOMException);
 void               setEnd(in Node refNode, 
 in long offset)
 raises(RangeException, 
 DOMException);
 void               setStartBefore(in Node refNode)
 raises(RangeException, 
 DOMException);
 void               setStartAfter(in Node refNode)
 raises(RangeException, 
 DOMException);
 void               setEndBefore(in Node refNode)
 raises(RangeException, 
 DOMException);
 void               setEndAfter(in Node refNode)
 raises(RangeException, 
 DOMException);
 void               collapse(in boolean toStart)
 raises(DOMException);
 void               selectNode(in Node refNode)
 raises(RangeException, 
 DOMException);
 void               selectNodeContents(in Node refNode)
 raises(RangeException, 
 DOMException);

 // CompareHow
 const unsigned short      START_TO_START                 = 0;
 const unsigned short      START_TO_END                   = 1;
 const unsigned short      END_TO_END                     = 2;
 const unsigned short      END_TO_START                   = 3;

 short              compareBoundaryPoints(in unsigned short how, 
 in Range sourceRange)
 raises(DOMException);
 void               deleteContents()
 raises(DOMException);
 DocumentFragment   extractContents()
 raises(DOMException);
 DocumentFragment   cloneContents()
 raises(DOMException);
 void               insertNode(in Node newNode)
 raises(DOMException, 
 RangeException);
 void               surroundContents(in Node newParent)
 raises(DOMException, 
 RangeException);
 Range              cloneRange()
 raises(DOMException);
 DOMString          toString()
 raises(DOMException);
 void               detach()
 raises(DOMException);
 };
 */

public interface Range extends ElementView{

	public static final short START_TO_START = 0;

	// Field descriptor #7 S
	public static final short START_TO_END = 1;

	// Field descriptor #7 S
	public static final short END_TO_END = 2;

	// Field descriptor #7 S
	public static final short END_TO_START = 3;

	@Property
	Node getStartContainer();

	@Property
	long getStartOffset();

	@Property
	Node getEndContainer();

	@Property
	Node getEndOffset();

	@Property
	long getCollapsed();

	@Property
	boolean getCommonAncestorContainer();

	@Function
	void setStart(Node refNode, long offset);

	@Function
	void setEnd(Node refNode, long offset);

	@Function
	void setStartBefore(Node refNode);

	@Function
	void setStartAfter(Node refNode);

	@Function
	void setEndBefore(Node refNode);

	@Function
	void setEndAfter(Node refNode);

	@Function
	void collapse(boolean toStart);

	@Function
	void selectNode(Node refNode);

	@Function
	void selectNodeContents(Node refNode);
	@Function
	short compareBoundaryPoints(short how, Range sourceRange);
	@Function
	void deleteContents();
	@Function
	DocumentFragment extractContents();
	@Function
	DocumentFragment cloneContents();
	@Function
	void insertNode(Node newNode);

	@Function
	void surroundContents(Node newParent);
	@Function
	Range cloneRange();
	@Function
	String toString();
	@Function
	void detach();

}