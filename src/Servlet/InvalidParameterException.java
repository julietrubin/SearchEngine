package Servlet;
/*
 * Thrown when a requested parameter does not exist.
 */
public class InvalidParameterException extends Exception {
	public static final int PARAM_NOT_INT = 0;
	public static final int NO_SUCH_PARAM = 1;

	private int type;

	public InvalidParameterException(int type) {
		this.type = type;
	}

	public int getType() {
		return this.type;
	}

}

