package org.eclipse.dltk.mod.debug.core.model;

/**
 * Represents an 'complex' script type
 */
public class ComplexScriptType extends AtomicScriptType {

	public ComplexScriptType(String name) {
		super(name);
	}

	public boolean isAtomic() {
		return false;
	}

	public boolean isComplex() {
		return true;
	}

	// eBay mod start
	// public String formatDetails(IScriptValue value) {
	// StringBuffer sb = new StringBuffer();
	// sb.append(getName());
	//
	// String address = value.getMemoryAddress();
	// if (address == null) {
	// address = ScriptModelMessages.unknownMemoryAddress;
	// }
	//
	// sb.append("@" + address); //$NON-NLS-1$
	//
	// return sb.toString();
	// }
	// eBay mod end

	public String formatValue(IScriptValue value) {
		StringBuffer sb = new StringBuffer();
		sb.append(getName());

		appendInstanceId(value, sb);

		return sb.toString();
	}
}
