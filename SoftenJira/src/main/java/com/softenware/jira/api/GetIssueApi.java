package com.softenware.jira.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.softenware.jira.SoftenJiraConnection.HttpMethod;
import com.softenware.jira.SoftenJiraUtils;

@Configuration
public class GetIssueApi extends BasicJiraApi {

	private static String REST_URL = "/api/2/issue/{issueIdOrKey}";
	
	private String fields;
	private String expands;
	private String properties;
	private boolean updateHistory = false;
	
	private String issueIdOrKey;
	
	public GetIssueApi() {
	}
	
	public GetIssueApi(String issueIdOrKey) {
		setIssueIdOrKey(issueIdOrKey);
	}

	public GetIssueApi(String issueIdOrKey, String fields) {
		setIssueIdOrKey(issueIdOrKey);
		setFields(fields);
	}

	public GetIssueApi(String issueIdOrKey, String fields, String expands, String properties, boolean updateHistory) {
		setIssueIdOrKey(issueIdOrKey);
		setFields(fields);
		setExpands(expands);
		setProperties(properties);
		setUpdateHistory(updateHistory);
	}

	@Override
	protected String getApiUrl() {
		return REST_URL;
	}
	
	@Value("${soften.jira.api.getissue:/api/2/issue/{issueIdOrKey}}")
	public void setUrl(String apiUrl) {
		if (!SoftenJiraUtils.isEmpty(apiUrl))
			REST_URL = apiUrl;
	}

	@Override
	public HttpMethod getMethod() {
		return HttpMethod.GET;
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public String getExpands() {
		return expands;
	}

	public void setExpands(String expands) {
		this.expands = expands;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public boolean isUpdateHistory() {
		return updateHistory;
	}

	public void setUpdateHistory(boolean updateHistory) {
		this.updateHistory = updateHistory;
	}

	public String getIssueIdOrKey() {
		return issueIdOrKey;
	}

	public void setIssueIdOrKey(String issueIdOrKey) {
		this.issueIdOrKey = issueIdOrKey;
	}
}
