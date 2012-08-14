package org.ebayopensource.dsf.jstojava.parser.comments;

public class VjCommentUtil {

	public static boolean isVjetComment(String comment) {
		
		if(!isHTMLTag(comment)){
			return true;
		}
		return false;
	}

	private static boolean isHTMLTag(String comment) {
		int startLessThan = comment.indexOf('<');
		int endGreaterThan = comment.indexOf('>');
		int semiColon = comment.indexOf(';');
		if(semiColon==-1){
			semiColon = comment.length();
		}
		if(semiColon >-1 && semiColon<endGreaterThan){
			return false;
		}
		int secondLessThan = comment.indexOf("<", startLessThan+1);
		if(secondLessThan!=-1 && secondLessThan<semiColon){
			return false;
		}
		
		if(startLessThan>-1 && endGreaterThan>-1 && startLessThan<endGreaterThan){
			if((endGreaterThan - startLessThan) < 10){ // delta has to be less than 10 chars for html tag
				return true;
			}
			
		}
		return false;
	}
	
}
