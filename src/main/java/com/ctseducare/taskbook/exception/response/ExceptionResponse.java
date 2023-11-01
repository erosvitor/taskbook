package com.ctseducare.taskbook.exception.response;

import java.io.Serializable;

public class ExceptionResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private int error;
	private String reason;

	public ExceptionResponse(int error, String reason) {
		this.error = error;
		this.reason = reason;
	}

	public int getError() {
		return error;
	}

	public String getReason() {
		return reason;
	}

}