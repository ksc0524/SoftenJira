package com.softenware.jira;

import java.io.IOException;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.softenware.jira.SoftenJiraConnection.HttpMethod;
import com.softenware.jira.api.SoftenJiraApi;

@Component
public class SoftenJiraQuery {
	
	public static final String DEFAULT_SEARCH_URL = "api/2/search";
	
	@Value("${soften.jira.restContextUrl}")
	private String restContextUrl;
	
	@Value("${soften.jira.userId}")
	private String userId;
	
	@Value("${soften.jira.password}")
	private String password;
	
	@Value("${soften.jira.inCharset:#{null}}")
	private String inCharset;
	
	@Value("${soften.jira.outCharset:#{null}}")
	private String outCharset;

	public SoftenJiraQuery() {
		
	}
	
	public SoftenJiraQuery(String restContextUrl, String userId, String password) {
		this.restContextUrl = restContextUrl;
		this.userId = userId;
		this.password = password;
	}
	
	public JSONObject searchIssues(String jql, int startAt, int maxResults, List<String> fields, boolean validateQuery, String expand) throws IOException {
		return null;
	}
	
	public JSONObject query(SoftenJiraApi api) {
		
		try {
			StringBuilder url = makeUrl(api.getUrl());
			HttpMethod method = api.getMethod();
			
			if (method == HttpMethod.GET) {
				if (url.indexOf("?") < 0)
					url.append('?');
//				else
//					url.append('&');
				
				url.append(api.getParams());
			}
			
			SoftenJiraConnection jiraCon = new SoftenJiraConnection(url.toString(), userId, password);
			
			if (!SoftenJiraUtils.isEmpty(outCharset))
				jiraCon.setOutCharset(outCharset);
			if (!SoftenJiraUtils.isEmpty(inCharset))
				jiraCon.setInCharset(inCharset);
			
			String res = null;
			
			if (method == HttpMethod.POST) {
				res = jiraCon.queryJiraPost(api.getParams());
			} else {
				res = jiraCon.queryJiraGet();
			}
			
			return new JSONObject(res);
		} catch (Exception e) {
			throw new SoftenJiraException(e);
		}
	}
	
	private StringBuilder makeUrl(String apiUrl) {
		StringBuilder url = new StringBuilder(restContextUrl);
		
		boolean ends = restContextUrl.endsWith("/");
		boolean starts = apiUrl.startsWith("/");
		
		if (ends && starts)
			url.deleteCharAt(url.length() - 1);
		else if (!ends && !starts)
			url.append('/');
		
		url.append(apiUrl);
		
		if (apiUrl.endsWith("/"))
			url.deleteCharAt(url.length() - 1);

		return url;
	}

	public String getRestContextUrl() {
		return restContextUrl;
	}

	public void setRestContextUrl(String restContextUrl) {
		this.restContextUrl = restContextUrl;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getInCharset() {
		return inCharset;
	}

	public void setInCharset(String inCharset) {
		this.inCharset = inCharset;
	}

	public String getOutCharset() {
		return outCharset;
	}

	public void setOutCharset(String outCharset) {
		this.outCharset = outCharset;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
