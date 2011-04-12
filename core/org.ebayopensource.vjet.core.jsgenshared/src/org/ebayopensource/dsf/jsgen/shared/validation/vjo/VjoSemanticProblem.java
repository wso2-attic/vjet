/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo;

import java.text.MessageFormat;

import org.ebayopensource.dsf.jsgen.shared.jstvalidator.DefaultJstProblem;
import org.ebayopensource.dsf.jst.JstProblemId;
import org.ebayopensource.dsf.jst.ProblemSeverity;

/**
 * 
 * 
 *
 * <p>
 * 	Semantic problem are jst problems, they are part of the validation result;
 *  they're also the source of front end UI problem markers
 * </p>
 * 
 * @see VjoValidationResult
 */
public class VjoSemanticProblem extends DefaultJstProblem{

	private static final String[] EMPTY_ARGUMENTS = new String[0];
	
	public VjoSemanticProblem(String[] arguments, JstProblemId id,
			String message, char[] fileName, int sourceStart, int sourceEnd,
			int lineNumber, int col, ProblemSeverity type) {
		
		super(arguments == null ? EMPTY_ARGUMENTS : arguments , 
				id, 
				message, 
				fileName, 
				sourceStart, 
				sourceEnd, 
				lineNumber,
				col, 
				type);

	}
	
	// use to verify some test cases
	public VjoSemanticProblem(JstProblemId id, int lineNumber, int sourceStart, int sourceEnd){
		this(null, id, null, null, sourceStart, sourceEnd, lineNumber, 0, null);
	}
	
	// use to verify some test cases
	public VjoSemanticProblem(JstProblemId id, int lineNumber, int sourceStart, 
			int sourceEnd, ProblemSeverity type){
		this(null, id, null, null, sourceStart, sourceEnd, lineNumber, 0, type);
	}
	
	@Override
	// use to verify some test cases
	public boolean equals(Object o){
		if (o == null){
			return false;
		}
		if (o instanceof VjoSemanticProblem){
			VjoSemanticProblem problem = (VjoSemanticProblem)o;
			return problem.getID() == getID() &&
//				   problem.getSourceEnd() == getSourceEnd() &&
				   problem.getSourceLineNumber() == getSourceLineNumber() 
//				   && problem.getSourceStart() == getSourceStart()
				   && problem.type() == type()
				   ;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return getSourceLineNumber() + 13 * getID().hashCode() + 17 * getSourceStart() + 37 * getSourceEnd();
	}
	
	@Override
	public String getMessage(){
		final String originalMsg = super.getMessage();
		String[] arguments = getArguments();
		if (arguments == null || arguments.length == 0) {
			return originalMsg;
		} else {
			return MessageFormat.format(originalMsg, (Object[])getArguments());
		}
	}
	
	public String toString(){
		final StringBuilder sb = new StringBuilder();
		sb.append("{ID:")
			.append(getID())
			.append(", \nSEVERITY:")
			.append(type())
			.append(", \nLINE_NUM:")
			.append(getSourceLineNumber())
			.append(", \nBEGIN:")
			.append(getSourceStart())
			.append(", \nEND:")
			.append(getSourceEnd())
			.append(", \nDETAIL:")
			.append(getMessage())
			.append("}");
		
		return sb.toString();
	}
}
