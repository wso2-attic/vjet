package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.Function;

/*
 * interface MultipleTrackList : TrackList {
  boolean isEnabled(in unsigned long index);
  void enable(in unsigned long index);
  void disable(in unsigned long index);
};


 */
public interface MultipleTrackList extends TrackList {

	@Function boolean isEnabled(long index);
	@Function void enable(long index);
	@Function void disable(long index);
	
}
