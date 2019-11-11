package com.softenware.jira;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softenware.jira.SoftenJiraConnection.HttpMethod;
import com.softenware.jira.api.GetIssueApi;
import com.softenware.jira.api.IssueDetailApi;
import com.softenware.jira.api.IssueDetailApi.ApplicationType;
import com.softenware.jira.api.IssueDetailApi.ApplicationType.StashDataType;
import com.softenware.jira.api.SearchIssueApi;

@Service
public class TestService {
	
	@Autowired
	private SoftenJiraQuery query;
	
	public void test() {
		List<String> fields = new LinkedList<String>();
		fields.add("key");
		fields.add("status");
		fields.add("updated");
		
		JSONObject result = query.query(new SearchIssueApi(HttpMethod.POST, "updated >= \"2018-08-25 10:00\"", fields));
		System.out.println(result.toString());
		
		result = query.query(new SearchIssueApi(HttpMethod.GET, "updated >= \"2018-08-25 10:00\"", fields));
		System.out.println(result.toString());
		
		result = query.query(new IssueDetailApi("10029", ApplicationType.stash, StashDataType.repository));
		System.out.println(result.toString());
		
		result = query.query(new IssueDetailApi("10029", ApplicationType.stash, StashDataType.pullrequest));
		System.out.println(result.toString());
		
		result = query.query(new GetIssueApi("10029"));
		System.out.println(result.toString());
	}
}
