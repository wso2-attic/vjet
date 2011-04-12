/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;


import org.ebayopensource.dsf.active.client.ActiveObject;
import org.ebayopensource.dsf.html.dom.HtmlTypeEnum;
import org.ebayopensource.dsf.html.dom.IDFormControl;
import org.ebayopensource.dsf.jsnative.Element;
import org.ebayopensource.dsf.jsnative.HtmlCollection;
import org.ebayopensource.dsf.jsnative.Node;
import org.ebayopensource.dsf.jsnative.NodeList;
import org.mozilla.mod.javascript.Scriptable;

public class AHtmlCollection extends ActiveObject implements HtmlCollection {
	
	private static final long serialVersionUID = 1L;


	/**
     * Request collection of all anchors in document: &lt;A&gt; elements that
     * have a <code>name</code> attribute.
     */
    static final short        ANCHOR = 1;
    
    
    /**
     * Request collection of all forms in document: &lt;FORM&gt; elements.
     */
    static final short        FORM = 2;
    
    
    /**
     * Request collection of all images in document: &lt;IMAGE&gt; elements.
     */
    static final short        IMAGE = 3;
    
    
    /**
     * Request collection of all Applets in document: &lt;APPLET&gt; and
     * &lt;OBJECT&gt; elements (&lt;OBJECT&gt; must contain an Applet).
     */
    static final short        APPLET = 4;
    
    
    /**
     * Request collection of all links in document: &lt;A&gt; and &lt;AREA&gt;
     * elements (must have a <code>href</code> attribute).
     */
    static final short        LINK = 5;
    
    
    /**
     * Request collection of all options in selection: &lt;OPTION&gt; elments in
     * &lt;SELECT&gt; or &lt;OPTGROUP&gt;.
     */
    static final short        OPTION = 6;
    
    
    /**
     * Request collection of all rows in table: &lt;TR&gt; elements in table or
     * table section.
     */
    static final short        ROW = 7;

    
    /**
     * Request collection of all form elements: &lt;INPUT&gt;, &lt;BUTTON&gt;,
     * &lt;SELECT&gt;, &lt;TEXT&gt; and &lt;TEXTAREA&gt; elements inside form
     * &lt;FORM&gt;.
     */
    static final short        ELEMENT = 8;

    /**
     * Request collection of all frames in document: &lt;FRAME&gt; elements.
     */
    static final short        IFRAME = 9;

    
    /**
     * Request collection of all areas in map: &lt;AREA&gt; element in &lt;MAP&gt;
     * (non recursive).
     */
    static final short        AREA = -1;
    

    /**
     * Request collection of all table bodies in table: &lt;TBODY&gt; element in
     * table &lt;TABLE&gt; (non recursive).
     */
    static final short        TBODY = -2;

    
    /**
     * Request collection of all cells in row: &lt;TD&gt; elements in &lt;TR&gt;
     * (non recursive).
     */
    static final short        CELL = -3;

    
    /**
     * Indicates what this collection is looking for. Holds one of the enumerated
     * values and used by {@link #collectionMatch}. Set by the constructor and
     * determine the collection's use for its life time.
     */
    private short            _lookingFor;
    
    
    /**
     * This is the top level element underneath which the collection exists.
     */
    private AElement            _topLevel;
    
    public static final AHtmlCollection EMPTY_COLLECTION = new AHtmlCollection() {
		private static final long serialVersionUID = 1L;
		public int getLength() {
    		return 0;
    	}
    };
    
    /**
     * Construct a new collection that retrieves element of the specific type
     * (<code>lookingFor</code>) from the specific document portion
     * (<code>topLevel</code>).
     * 
     * @param topLevel The element underneath which the collection exists
     * @param lookingFor Code indicating what elements to look for
     */
    AHtmlCollection( AHtmlElement topLevel, short lookingFor )
    {
        if ( topLevel == null )
            throw new NullPointerException( "HTM011 Argument 'topLevel' is null." );
        _topLevel = topLevel;
       _lookingFor = lookingFor;
       populateScriptable(AHtmlCollection.class, topLevel.getOwnerDocument().getBrowserType());
    }
  
    private AHtmlCollection() {}
    
    /**
     * Returns the length of the collection. This method might traverse the
     * entire document tree.
     * 
     * @return Length of the collection
     */
    public int getLength()
    {
    	if (_topLevel == null) {
    		return 0;
    	}
        // Call recursive function on top-level element.
        return getLength( _topLevel );
    }
    
    public AHtmlElement get(String name) {
    	return (AHtmlElement)get(name, null);
    }
    
    
    public Object get(int index, Scriptable start) {
 	   Object obj = super.get(index, start);
 	   if (obj != NOT_FOUND) {
 			return obj;
 	   }
 		   
 	   obj = item(index);
 	   if (obj != null) {
 		   return obj;
 	   }
 	   return NOT_FOUND;
     }
    
    public Object get(String name, Scriptable start) {
		Object obj = super.get(name, start);
		if (obj != NOT_FOUND) {
			return obj;
		}

		// collection items can be located by 'id' or 'name'
		// attribute. For example:
		// document.forms['formName']
		// document.forms['formId']
		for (int i = 0; i < getLength(); i++) {
			AHtmlElement elem = (AHtmlElement) item(i);
			if (name.equals(elem.getAttribute("id"))) {
				return elem;
			} else if (name.equals(elem.getAttribute("name"))) {
				return elem;
			}
		}

		return NOT_FOUND;
	}
    
    
    @Override
	public void put(int index, Scriptable start, Object value) {
    	if (!(value instanceof ANode) && value != null) {
    		super.put(index, start, value);
    		return;
    	}
    	Node node = item(index);
    	if (node != null) {
    		removeNode(node);
    		if (value != null) {
    			insertNode((Node) value, index);
    		}
    		return;
    	}
    	if (index - getLength() > 1) {
    		growCollection(index - getLength() - 1, value);
    	}
    	appendNode((Node) value);
	}

	@Override
	public void put(String name, Scriptable start, Object value) {
		if (!(value instanceof ANode) && value != null) {
			super.put(name, start, value);
			return;
		}
		if ("namedItem".equals(name)) {
			Node node = namedItem(name);
	    	if (node != null) {
	    		removeNode(node);
	    		if (value != null) {
	    			appendNode((Node) value);
	    		}
	    		return;
	    	}
	    	appendNode((Node) value);
		}
	}

	/**
     * Retrieves the indexed node from the collection. Nodes are numbered in
     * tree order - depth-first traversal order. This method might traverse
     * the entire document tree.
     * 
     * @param index The index of the node to return
     * @return The specified node or null if no such node found
     */
    public final Node item( int index )
    {
        if ( index < 0 )
           return null;
        // Call recursive function on top-level element.
        return item( _topLevel, new CollectionIndex( index ) );
    }
    
    
    /**
     * Retrieves the named node from the collection. The name is matched case
     * sensitive against the <TT>id</TT> attribute of each element in the
     * collection, returning the first match. The tree is traversed in
     * depth-first order. This method might traverse the entire document tree.
     * 
     * @param name The name of the node to return
     * @return The specified node or null if no such node found
     */
    public final Node namedItem( String name )
// quickbug fix http://quickbugs.arch.ebay.com/show_bug.cgi?id=342 AHtmlCollection class has namedItem() and item() that throw e
// quickbug http://quickbugs.arch.ebay.com/show_bug.cgi?id=342 AHtmlCollection class has namedItem() and item() that throw excep
    {
    	
        if ( name == null )
            return null;
        // Call recursive function on top-level element.
        return namedItem( _topLevel, name );
    }
    
	public Object valueOf(String type) {
		if (type.equals("boolean")) {
			return Boolean.TRUE;
		}
		else if (type.equals("string")) {
			return getClass().getName();
		}
		return null;
	}
    
    
    /**
     * Recursive function returns the number of elements of a particular type
     * that exist under the top level element. This is a recursive function
     * and the top level element is passed along.
     * 
     * @param topLevel Top level element from which to scan
     * @return Number of elements
     */
    private int getLength( AElement topLevel )
    {
        int        length;
        Node    node;
    
        synchronized ( topLevel )
        {
            // Always count from zero and traverse all the childs of the
            // current element in the order they appear.
            length = 0;
            node = topLevel.getFirstChild();
            while ( node != null )
            {
                // If a particular node is an element (could be HTML or XML),
		// do two things: if it's the one we're looking for, count
		// another matched element; at any rate, traverse it's
		// children as well.
                if ( node instanceof AElement )
                {
                    if ( collectionMatch( (AElement) node, null ) )
                        ++ length;
                    else if ( recurse() )
                        length += getLength( (AElement) node );
                }
                node = node.getNextSibling(); 
            }
        }
        return length;
    }
    
        
    /**
     * Recursive function returns the numbered element of a particular type
     * that exist under the top level element. This is a recursive function
     * and the top level element is passed along.
     * <p>
     * Note that this function must call itself with an index and get back both
     * the element (if one was found) and the new index which is decremeneted
     * for any like element found. Since integers are only passed by value,
     * this function makes use of a separate class ({@link CollectionIndex})
     * to hold that index.
     * 
     * @param topLevel Top level element from which to scan
     * @param index The index of the item to retreive
     * @return Number of elements
     * @see CollectionIndex
     */
    private Node item( AElement topLevel, CollectionIndex index )
    {
        Node    node;
        Node    result;

        synchronized ( topLevel )
        {
            // Traverse all the childs of the current element in the order
	    // they appear. Count from the index backwards until you reach
	    // matching element with an index of zero. Return that element.
            node = topLevel.getFirstChild();
            while ( node != null )
            {
                // If a particular node is an element (could be HTML or XML),
		// do two things: if it's the one we're looking for, decrease
		// the index and if zero, return this node; at any rate,
		// traverse it's children as well.
                if ( node instanceof AElement )
                {
                    if ( collectionMatch( (AElement) node, null ) )
                    {
                        if ( index.isZero() )
                            return node;
                        index.decrement();
                    } else if ( recurse() )
                    {
                        result = item( (AElement) node, index );
                        if ( result != null )
                            return result;
                    }
                }
                node = node.getNextSibling(); 
            }
        }
        return null;
    }
    
    
    /**
     * Recursive function returns an element of a particular type with the
     * specified name (<TT>id</TT> attribute).
     * 
     * @param topLevel Top level element from which to scan
     * @param name The named element to look for
     * @return The first named element found
     */
    private  Node namedItem( AElement topLevel, String name )
    {
    	if (topLevel == null) {
    		return null;
    	}
        Node    node;
        Node    result;

        synchronized ( topLevel )
        {
            // Traverse all the childs of the current element in the order
	    // they appear.
            node = topLevel.getFirstChild();
            while ( node != null )
            {
                // If a particular node is an element (could be HTML or XML),
                // do two things: if it's the one we're looking for, and the
		// name (id attribute) attribute is the one we're looking for,
		// return this element; otherwise, traverse it's children.
                if ( node instanceof AElement )
                {
                    if ( collectionMatch( (AElement) node, name ) )
                        return node;
                    else if ( recurse() )
                    {
                        result = namedItem( (AElement) node, name );
                        if ( result != null )
                            return result;
                    }
                }
                node = node.getNextSibling(); 
            }
            return node;
        }
    }
    
    
    /**
     * Returns true if scanning methods should iterate through the collection.
     * When looking for elements in the document, recursing is needed to traverse
     * the full document tree. When looking inside a specific element (e.g. for a
     * cell inside a row), recursing can lead to erroneous results.
     * 
     * @return True if methods should recurse to traverse entire tree
     */
    protected boolean recurse()
    {
        return _lookingFor > 0;
    }
    

    /**
     * Determines if current element matches based on what we're looking for.
     * The element is passed along with an optional identifier name. If the
     * element is the one we're looking for, return true. If the name is also
     * specified, the name must match the <code>id</code> attribute
     * (match <code>name</code> first for anchors).
     * 
     * @param elem The current element
     * @param name The identifier name or null
     * @return The element matches what we're looking for
     */
    protected boolean collectionMatch( AElement elem, String name )
    {
        boolean    match;
        
        synchronized ( elem )
        {
            // Begin with no matching. Depending on what we're looking for,
            // attempt to match based on the element type. This is the quickest
            // way to match involving only a cast. Do the expensive string
            // comparison later on.
            match = false;
            switch ( _lookingFor )
            {
            case ANCHOR:
                // Anchor is an <A> element with a 'name' attribute. Otherwise, it's
                // just a link.
                match = ( elem instanceof AHtmlAnchor ) &&
                        elem.getAttribute( "name" ).length() > 0;
                break;
            case FORM:
                // Any <FORM> element.
                match = ( elem instanceof AHtmlForm );
                break;
            case IMAGE:
                // Any <IMG> element. <OBJECT> elements with images are not returned.
                match = ( elem instanceof AHtmlImage || elem instanceof AImage);
                break;
            case APPLET:
                // Any <APPLET> element, and any <OBJECT> element which represents an
                // Applet. This is determined by 'codetype' attribute being
                // 'application/java' or 'classid' attribute starting with 'java:'.
                match = ( elem instanceof AHtmlApplet ) ||
                        ( elem instanceof AHtmlObject &&
                          ( "application/java".equals( elem.getAttribute( "codetype" ) ) ||
                            elem.getAttribute( "classid" ).startsWith( "java:" ) ) );
                break;
            case ELEMENT:
                // All form elements implement IDFormControl for easy identification.
            	match = (elem instanceof IDFormControl);
                break;
            case LINK:
                // Any <A> element, and any <AREA> elements with an 'href' attribute.
                match = ( ( elem instanceof AHtmlAnchor
                            /* || elem instanceof AHtmlArea */ ) &&
                          elem.getAttribute( "href" ).length() > 0 );
                break;
            case AREA:
                // Any <AREA> element.
                match = ( elem instanceof AHtmlArea );
                break;
            case OPTION:
                // Any <OPTION> element.
                match = ( elem instanceof AHtmlOption || elem instanceof AOption);
                break;
            case ROW:
                // Any <TR> element.
                match = ( elem instanceof AHtmlTableRow );
                break;
            case TBODY:
                // Any <TBODY> element (one of three table section types).
                match = ( elem instanceof AHtmlTableSection &&
                          elem.getTagName().equalsIgnoreCase("tbody"));
                break;
            case CELL:
                // Any <TD> element.
                match = ( elem instanceof AHtmlTableCell );
                break;
            case IFRAME:
                // Any <FRAME> element.
                match = ( elem instanceof AHtmlIFrame );
                break;
            }
        
            // If element type was matched and a name was specified, must also match
            // the name against either the 'id' or the 'name' attribute. The 'name'
            // attribute is relevant only for <A> elements for backward compatibility.
            if ( match && name != null )
            {
            	// Search for a node with a matching 'id' attribute. 
            	// If we don't find one, then search for a node with a matching name attribute, 
            	// but only on those elements that are allowed a name attribute.
            	match = name.equalsIgnoreCase( elem.getAttribute( "id" ) );
            	// The 'name' attribute is relevant only for <A> elements
                if (elem instanceof AHtmlAnchor &&
                		name.equalsIgnoreCase(elem.getAttribute( "name" )))  {
                    return true;
                }
                
            }
        }
        return match;
    }
    
	private void growCollection(int size, Object value) {
		if (_topLevel == null) {
			return;
		}
		
        switch ( _lookingFor )
        {
        case ANCHOR:
            createElements(HtmlTypeEnum.A.getName(), size);
            break;
        case FORM:
        	createElements(HtmlTypeEnum.FORM.getName(), size);
            break;
        case IMAGE:
        	createElements(HtmlTypeEnum.IMG.getName(), size);
            break;
        case APPLET:
        	createElements(HtmlTypeEnum.APPLET.getName(), size);
            break;
        case ELEMENT:
        	if (value instanceof AHtmlInput) {
        		createElements(HtmlTypeEnum.INPUT.getName(), size);
        	} else if (value instanceof AHtmlSelect) {
        		createElements(HtmlTypeEnum.SELECT.getName(), size);
        	} else if (value instanceof AHtmlTextArea) {
        		createElements(HtmlTypeEnum.TEXTAREA.getName(), size);
        	}
            break;
        case LINK:
        	createElements(HtmlTypeEnum.LINK.getName(), size);
            break;
        case AREA:
        	createElements(HtmlTypeEnum.AREA.getName(), size);
            break;
        case OPTION:
        	createElements(HtmlTypeEnum.OPTION.getName(), size);
            break;
        case ROW:
        	createElements(HtmlTypeEnum.TR.getName(), size);
            break;
        case TBODY:
        	createElements(HtmlTypeEnum.TBODY.getName(), size);
            break;
        case CELL:
        	createElements(HtmlTypeEnum.TD.getName(), size);
            break;
        case IFRAME:
        	createElements(HtmlTypeEnum.IFRAME.getName(), size);
            break;
        }
		
	}

	private void createElements(String tagName, int size) {
		synchronized ( _topLevel )
        {
			for (int i = 0; i < size; i++) {
				Element e = _topLevel.getOwnerDocument().createElement(tagName);
				_topLevel.appendChild(e);
			}
        }
	}
	
	private void insertNode(Node node, int index) {
		NodeList nl = _topLevel.getChildNodes();
		Node before = nl.item(index);
		_topLevel.insertBefore(node, before);
	}
	
	private void appendNode(Node node) {
		synchronized ( _topLevel ) {
			_topLevel.appendChild(node);
		}
	}
	

	void removeNode(Node node) {
		synchronized ( _topLevel ) {
			_topLevel.removeChild(node);
		}
	}

	AElement getTopLevel() {
		return _topLevel;
	}

    
}


/**
 * {@link CollectionImpl#item} must traverse down the tree and decrement the
 * index until it matches an element who's index is zero. Since integers are
 * passed by value, this class servers to pass the index into each recursion
 * by reference. It encompasses all the operations that need be performed on
 * the index, although direct access is possible.
 * 
 * @xerces.internal
 * 
 * @see CollectionImpl#item
 */
class CollectionIndex
{
    
    
    /**
     * Returns the current index.
     * 
     * @return Current index
     */
    int getIndex()
    {
        return _index;
    }
    
    
    /**
     * Decrements the index by one.
     */
    void decrement()
    {
        -- _index;
    }
    
    
    /**
     * Returns true if index is zero (or negative).
     * 
     * @return True if index is zero
     */
    boolean isZero()
    {
        return _index <= 0;
    }
    
    
    /**
     * Constructs a new index with the specified initial value. The index will
     * then be decremeneted until it reaches zero.
     * 
     * @param index The initial value
     */
    CollectionIndex( int index )
    {
        _index = index;
    }
    
    
    /**
     * Holds the actual value that is passed by reference using this class.
     */
    private int        _index;
    
    
    

}
