package org.ebayopensource.dsf.jsnative.events;

import org.ebayopensource.dsf.jsnative.anno.Function;


/*
 * interface DragEvent : MouseEvent {
  readonly attribute DataTransfer dataTransfer;

  void initDragEvent(in DOMString typeArg, in boolean canBubbleArg, in boolean cancelableArg, in any dummyArg, in long detailArg, in long screenXArg, in long screenYArg, in long clientXArg, in long clientYArg, in boolean ctrlKeyArg, in boolean altKeyArg, in boolean shiftKeyArg, in boolean metaKeyArg, in unsigned short buttonArg, in EventTarget relatedTargetArg, in DataTransfer dataTransferArg);
};
 */
public interface DragEvent extends MouseEvent {

//	@Property DataTransfer getDataTransfer();
	@Function void initDragEvent(String typeArg, boolean canBubbleArg, boolean cancelableArg, Object dummyArg,  long detailArg,  long screenXArg, 
			long screenYArg,  long clientXArg,  long clientYArg, boolean ctrlKeyArg, boolean altKeyArg, boolean shiftKeyArg, boolean metaKeyArg,  short buttonArg,  EventTarget relatedTargetArg,  DataTransfer dataTransferArg);
	
}
