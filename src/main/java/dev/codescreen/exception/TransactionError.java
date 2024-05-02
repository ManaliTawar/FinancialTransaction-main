package dev.codescreen.exception;

public class TransactionError {

	private String message;
	private String code;

	public TransactionError(final String message, final String code) {
		this.message = message;
		this.code = code;

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
