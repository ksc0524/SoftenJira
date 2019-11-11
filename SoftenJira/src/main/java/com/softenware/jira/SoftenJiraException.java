package com.softenware.jira;

public class SoftenJiraException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SoftenJiraException(String msg, Throwable e) {
		super(msg, e);
	}

	public SoftenJiraException(String msg) {
		super(msg);
	}

	public SoftenJiraException(Throwable e) {
		super(e);
	}
}
