package com.softenware.jira.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.softenware.jira.SoftenJiraConnection.HttpMethod;
import com.softenware.jira.SoftenJiraUtils;

@Configuration
public class IssueDetailApi extends BasicJiraApi {

	private static String REST_URL = "/dev-status/latest/issue/detail";

	public enum ApplicationType {
		stash;
		
		public enum StashDataType implements DataType {
			repository, pullrequest
		}
	}
	
	public static interface DataType {
	}

	private String issueId;
	private ApplicationType applicationType;
	private DataType dataType;
	
	public IssueDetailApi() {
	}

	public IssueDetailApi(String issueId, ApplicationType applicationType, DataType dataType) {
		setIssueId(issueId);
		setApplicationType(applicationType);
		setDataType(dataType);
	}

	@Override
	protected String getApiUrl() {
		return REST_URL;
	}

	@Value("${soften.jira.api.searchissue:/dev-status/latest/issue/detail}")
	public void setUrl(String apiUrl) {
		if (!SoftenJiraUtils.isEmpty(apiUrl))
			REST_URL = apiUrl;
	}

	@Override
	public HttpMethod getMethod() {
		return HttpMethod.GET;
	}

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public ApplicationType getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(ApplicationType applicationType) {
		this.applicationType = applicationType;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
}
