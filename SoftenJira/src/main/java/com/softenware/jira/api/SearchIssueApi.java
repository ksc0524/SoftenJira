package com.softenware.jira.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.softenware.jira.SoftenJiraConnection.HttpMethod;
import com.softenware.jira.SoftenJiraException;
import com.softenware.jira.SoftenJiraUtils;

@Configuration
public class SearchIssueApi extends BasicJiraApi {

	private static String REST_URL = "/api/2/search";
	
	private String jql;
	private int startAt = 0;
	private int maxResults = 50;
	private List<String> fields;
	private boolean validateQuery = true;
	private String expand;
	
	public SearchIssueApi() {
	}
	
	public SearchIssueApi(HttpMethod method) {
		setMethod(method);
	}
	
	public SearchIssueApi(String jql) {
		setJql(jql);
	}
	
	public SearchIssueApi(HttpMethod method, String jql) {
		setMethod(method);
		setJql(jql);
	}
	
	public SearchIssueApi(HttpMethod method, String jql, List<String> fields) {
		setMethod(method);
		setJql(jql);
		setFields(fields);
	}
	
	public void set(String jql, int startAt, int maxResults, List<String> fields, boolean validateQuery, String expand) {
		setJql(jql);
		setStartAt(startAt);
		setMaxResults(maxResults);
		setFields(fields);
		setValidateQuery(validateQuery);
		setExpand(expand);
	}
	
	@Override
	protected String getApiUrl() {
		return REST_URL;
	}
	
	@Value("${soften.jira.api.searchissue:/api/2/search}")
	public void setUrl(String apiUrl) {
		if (!SoftenJiraUtils.isEmpty(apiUrl))
			REST_URL = apiUrl;
	}
	
	public void setMethod(HttpMethod method) {
		if (method != HttpMethod.GET && method != HttpMethod.POST)
			throw new SoftenJiraException("This allows only GET or POST.");
		
		this.method = method;
	}

	public String getJql() {
		return jql;
	}

	public void setJql(String jql) {
		this.jql = jql;
	}

	public int getStartAt() {
		return startAt;
	}

	public void setStartAt(int startAt) {
		this.startAt = startAt;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public boolean isValidateQuery() {
		return validateQuery;
	}

	public void setValidateQuery(boolean validateQuery) {
		this.validateQuery = validateQuery;
	}

	public String getExpand() {
		return expand;
	}

	public void setExpand(String expand) {
		this.expand = expand;
	}
}
