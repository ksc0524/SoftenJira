package com.softenware.jira.api;

import java.lang.reflect.Method;
import java.net.URLEncoder;

import org.json.JSONObject;

import com.softenware.jira.SoftenJiraConnection.HttpMethod;
import com.softenware.jira.SoftenJiraException;

public abstract class BasicJiraApi implements SoftenJiraApi {
	
	protected HttpMethod method = HttpMethod.POST;
	
	public HttpMethod getMethod() {
		return method;
	}
	
//	public void setMethod(HttpMethod method) {
//		this.method = method;
//	}
	
	@Override
	public String getUrl() {
		String url = getApiUrl();
		
		if (url.indexOf("{") >= 0) {
			int len = url.length();
			boolean onBrace = false;
			StringBuilder result = new StringBuilder();
			StringBuilder var = new StringBuilder();
			char c;
			
			try {
				for (int i = 0 ; i < len ; i++) {
					c = url.charAt(i);
					
					if (onBrace) {
						if (c == '}') {
							onBrace = false;
							var.setCharAt(0, Character.toUpperCase(var.charAt(0)));
							result.append(this.getClass().getMethod("get" + var.toString()).invoke(this));
						} else {
							var.append(c);
						}
					} else {
						if (c == '{') {
							onBrace = true;
							var.setLength(0);
						} else {
							result.append(c);
						}
					}
				}
			} catch (Exception e) {
				throw new SoftenJiraException("No such member: " + var.toString());
			}
			
			return result.toString();
		} else {
			return url;
		}
	}
	
	protected abstract String getApiUrl();
	
//	private List<String> getVariables(String str) {
//		int len = str.length();
//		boolean onBrace = false;
//		StringBuilder var = new StringBuilder();
//		char c;
//		
//		List<String> vars = new LinkedList<String>();
//		for (int i = 0 ; i < len ; i++) {
//			c = str.charAt(i);
//			
//			if (onBrace) {
//				if (c == '}') {
//					onBrace = false;
//					vars.add(var.toString());
//				} else {
//					var.append(c);
//				}
//			} else {
//				if (c == '{') {
//					onBrace = true;
//					var.setLength(0);
//				}
//			}
//		}
//		
//		return vars;
//	}

	@Override
	public String getParams() {
		String name, param;
		Object value;
		StringBuilder sb = new StringBuilder();
		JSONObject json = new JSONObject();
		HttpMethod httpMethod = getMethod();
		
		try {
			for (Method m : this.getClass().getMethods()) {
				name = m.getName();
				
				if (name.startsWith("get") && name.length() > 3 && m.getParameterCount() == 0
						&& !"getMethod".equals(name) && !"getUrl".equals(name) && !"getParams".equals(name) && !"getClass".equals(name)
						&& !existsOnUrl(name)) {
					param = Character.toLowerCase(name.charAt(3)) + (name.length() > 4 ? name.substring(4) : "");
					value = m.invoke(this);
					
					if (value != null) {
						if (httpMethod == HttpMethod.GET) {
							sb.append('&').append(param).append('=').append(URLEncoder.encode(value.toString(), "UTF-8"));
						} else {
							json.put(param, value);
						}
					}
				}
			}
		} catch (Throwable e) {
			throw new SoftenJiraException(e);
		}
		
		return httpMethod == HttpMethod.GET ? sb.toString() : json.toString();
	}
	
	private boolean existsOnUrl(String methodName) {
		return getApiUrl().indexOf("{" + Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4) + "}") >= 0;
	}
}
