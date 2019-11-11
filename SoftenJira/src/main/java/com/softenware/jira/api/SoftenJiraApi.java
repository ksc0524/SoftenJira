package com.softenware.jira.api;

import com.softenware.jira.SoftenJiraConnection.HttpMethod;

public interface SoftenJiraApi {
	
	public String getUrl();
	
	public HttpMethod getMethod();

//	public Object get(String key);
	
//	public void set(String key, Object value);
	
	public String getParams();
}
