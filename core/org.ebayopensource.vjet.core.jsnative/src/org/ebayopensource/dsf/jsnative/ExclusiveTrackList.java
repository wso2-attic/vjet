package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.Property;

/*
 * interface ExclusiveTrackList : TrackList {
  readonly attribute unsigned long selectedIndex;
  void select(in unsigned long index);
};
 */
public interface ExclusiveTrackList extends TrackList{

	@Property long getSelectedIndex();
	@Function void select(long index);
	
	
}
