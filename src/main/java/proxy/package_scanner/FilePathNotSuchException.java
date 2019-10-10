package proxy.package_scanner;

public class FilePathNotSuchException extends Exception {
	private static final long serialVersionUID = 1536955347744737680L;

	public FilePathNotSuchException() {
	}

	public FilePathNotSuchException(String message) {
		super(message);
	}

	public FilePathNotSuchException(Throwable cause) {
		super(cause);
	}

	public FilePathNotSuchException(String message, Throwable cause) {
		super(message, cause);
	}

	public FilePathNotSuchException(String message, Throwable cause, boolean enableSuppression,
									boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
