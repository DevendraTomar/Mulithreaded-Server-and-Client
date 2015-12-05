package co.in.exception;

public class ServerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ServerException(String msg) {
		super(msg);
	}

	public ServerException(String msg, Exception exception) {
		super(msg, exception);
	}

}
