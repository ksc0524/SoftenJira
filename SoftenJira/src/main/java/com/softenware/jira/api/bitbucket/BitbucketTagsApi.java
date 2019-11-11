package com.softenware.jira.api.bitbucket;

import org.springframework.context.annotation.Configuration;

import com.softenware.jira.SoftenJiraConnection.HttpMethod;
import com.softenware.jira.api.BasicJiraApi;

@Configuration
public class BitbucketTagsApi extends BasicJiraApi {
	
	private static final String REST_URL = "/api/1.0/projects/{project}/repos/{repository}/tags";
	
	/** project key */
	private String project;
	
	/** repository key */
	private String repository;
	
	/** max result size */
	private int limit = 50;
	
	/** start number on all list */
	private int start = 0;
	
	public BitbucketTagsApi() {
	}
	
	public BitbucketTagsApi(String projectKey, String repositoryKey) {
		this.setProject(projectKey);
		this.setRepository(repositoryKey);
	}

	@Override
	protected String getApiUrl() {
		return REST_URL;
	}

	@Override
	public HttpMethod getMethod() {
		return HttpMethod.GET;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String projectKey) {
		this.project = projectKey;
	}

	public String getRepository() {
		return repository;
	}

	public void setRepository(String repositoryKey) {
		this.repository = repositoryKey;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}
}
