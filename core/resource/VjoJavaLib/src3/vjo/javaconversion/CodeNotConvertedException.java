package vjo.javaconversion;

public class CodeNotConvertedException extends RuntimeException {
	public CodeNotConvertedException() { }
	public CodeNotConvertedException(String msg) {
		super(msg) ;
	}
}
