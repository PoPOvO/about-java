package petty.transform;

public class ParameterInvalidException extends Exception {
	private static final long serialVersionUID = -4858479916960434830L;

	public ParameterInvalidException() {
	}

	public ParameterInvalidException(String message) {
		super(message);
	}

	public ParameterInvalidException(Throwable cause) {
		super(cause);
	}

	public ParameterInvalidException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParameterInvalidException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
