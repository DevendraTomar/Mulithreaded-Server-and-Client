package co.in.client;

public class ClientException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClientException(String msg) {
		super(msg);
	}

	public ClientException(String msg, Exception ex) {
		super(msg, ex);
	}

}
