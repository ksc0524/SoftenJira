package com.softenware.jira;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SoftenJiraConnection {
	
	public static String DEFAULT_CHARSET = StandardCharsets.UTF_8.toString();
	
	private static int DEFAULT_BUFFER_SIZE = 4096;
	
	public enum HttpMethod {
		POST, GET, PUT, DELETE
	}
	
	private URL url;
	
	private String basicAuth;
	
	private String outCharset = DEFAULT_CHARSET;
	private String inCharset = DEFAULT_CHARSET;

	public SoftenJiraConnection(String restUrl) throws MalformedURLException {
		url = new URL(restUrl);
	}
	
	public SoftenJiraConnection(String restUrl, String userId, String password) throws MalformedURLException {
		this(restUrl);
		this.setAuthorization(userId, password);
	}
	
	public void setAuthorization(String basicAuth) {
		this.basicAuth = basicAuth;
	}
	
	public void setAuthorization(String userId, String password) {
		this.basicAuth = "Basic " + new String(Base64.getEncoder().encodeToString((userId + ":" + password).getBytes()));
	}
	
	public String getInCharset() {
		return inCharset;
	}

	public String getOutCharset() {
		return outCharset;
	}

	public void setOutCharset(String outCharset) {
		this.outCharset = outCharset;
	}

	public void setInCharset(String charset) {
		this.inCharset = charset;
	}

	public String queryJiraGet() throws IOException {
		return queryJira(HttpMethod.GET, null);
	}
	
	public String queryJiraPost(String reqData) throws IOException {
		return queryJira(HttpMethod.POST, reqData);
	}
	
	private String queryJira(HttpMethod method, String reqData) throws IOException {
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setRequestMethod(method.toString());
		
		con.setRequestProperty("Accept", "*/*");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Authorization", basicAuth);
		
		con.setUseCaches(false);
		con.setDoOutput(true);
		
		if (method == HttpMethod.POST) {
			con.setDoInput(true);
			
			DataOutputStream out = null;
			try {
				out = new DataOutputStream(con.getOutputStream());
				out.write(reqData.getBytes(outCharset));
				out.flush();
			} finally {
				if (out != null)
					out.close();
			}
		} else {
			con.connect();
		}
		
		BufferedReader in = null;
		StringWriter strOut = null;
		try {
			strOut = new StringWriter();
			in = new BufferedReader(new InputStreamReader(con.getInputStream(), inCharset));
			
			char[] buff = new char[DEFAULT_BUFFER_SIZE];
			int count;
			while ((count = in.read(buff)) != -1) {
				if (count > 0) {
					strOut.write(buff, 0, count);
				}
			}
		} finally {
			if (in != null)
				in.close();
			if (strOut != null)
				strOut.close();
		}
		
		if (HttpURLConnection.HTTP_OK == con.getResponseCode()) {
			return strOut.toString();
		} else {
			throw new SoftenJiraException(con.getResponseCode() + con.getResponseMessage() + strOut.toString());
		}
	}
}
