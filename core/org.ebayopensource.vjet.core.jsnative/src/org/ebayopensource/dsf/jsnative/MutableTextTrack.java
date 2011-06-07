package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.Function;
/*
 * interface MutableTextTrack : TextTrack {
 void addCue(in TextTrackCue cue);
 void removeCue(in TextTrackCue cue);
};
 */
public interface MutableTextTrack extends TextTrack {

	@Function void addCue(TextTrackCue cue);
	@Function void removeCue(TextTrackCue cue);
	
	
}
