package com.softenware.jira.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.softenware.jira.BasicJiraIssue;
import com.softenware.jira.SoftenJiraUtils;

@Configuration
public class CreateIssueApi extends BasicJiraApi {

	private static String REST_URL = "/api/2/issue";
	
	private BasicJiraIssue issue;

	@Override
	protected String getApiUrl() {
		return REST_URL;
	}

	@Value("${soften.jira.api.searchissue:/api/2/issue}")
	public void setUrl(String apiUrl) {
		if (!SoftenJiraUtils.isEmpty(apiUrl))
			REST_URL = apiUrl;
	}

	public BasicJiraIssue getIssue() {
		return issue;
	}

	public void setIssue(BasicJiraIssue issue) {
		this.issue = issue;
	}
}
