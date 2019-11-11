package com.softenware.jira;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class SoftenJiraUtils {
	private SoftenJiraUtils() {
	}
	
	/**
	 * get value from JSONObject
	 * 
	 * @param json
	 * @param keyExpr Expression of composite of keys. Symbols are '.', '"', '+'. The symbol '"' can be omitted. ex) soften.issues.*.key + " - " + soften.issues.0.updated
	 * @return
	 */
	public static String getJSONValue(JSONObject json, String keyExpr) {
		StringBuilder value = new StringBuilder();
		StringBuilder key = new StringBuilder();
		ArrayList<String> keys = new ArrayList<String>();
		
		boolean inStr = false;
		char c, nextC;
		
		int len = keyExpr.length();
		for (int i = 0 ; i < len ; i++) {
			c = keyExpr.charAt(i);
			if (inStr) {
				if (c == '\\') {
					nextC = keyExpr.charAt(i + 1);
					
					switch (nextC) {
					case '\\':
						value.append('\\');
						break;
					case '\n':
						value.append('\n');
						break;
					case '\r':
						value.append('\r');
						break;
					case '\t':
						value.append('\t');
						break;
					case '\b':
						value.append('\b');
						break;
					case '\f':
						value.append('\f');
						break;
					case '\'':
						value.append('\'');
						break;
					case '\"':
						value.append('\"');
						break;
					default:
						throw new SoftenJiraException("Invalid String format");
					}
					
					i++;
				} else if (c == '"') {
					inStr = false;
				} else {
					value.append(c);
				}
			} else {
				if (c == '"') {
					inStr = true;
				} else if (c == '.') {
					addKey(keys, key);
				} else if (Character.isWhitespace(c) || c == '+') {
					if (i != 0) {
						addKey(keys, key);
						
						if (keys.size() > 0) {
							getJSONValue(value, json, keys);
							keys.clear();
						}
						
						while (Character.isWhitespace(keyExpr.charAt(i + 1)) || keyExpr.charAt(i + 1) == '+')
							i++;
					}
				} else {
					key.append(c);
				}
			}
		}
		
		if (key.length() > 0)
			keys.add(key.toString());
		
		if (keys.size() > 0)
			getJSONValue(value, json, keys);
		
		return value.toString();
	}
	
	public static void getJSONValue(StringBuilder value, Object json, List<String> keys) {
		Object obj = json;
		int len = keys.size();
		String key;
		
		for (int i = 0 ; i < len ; i++) {
			key = keys.get(i);
			
			if (obj instanceof JSONObject) {
				obj = ((JSONObject)obj).get(key);
			} else if (obj instanceof JSONArray) {
				if ("*".equals(key)) {
					JSONArray jsonArr = (JSONArray)obj;
					
					List<String> subKeys = keys.subList(i + 1, keys.size());
					int subLen = jsonArr.length();
					
					for (int j = 0 ; j < subLen ; j++) {
						getJSONValue(value, jsonArr.getJSONObject(j), subKeys);
						value.append(',');
					}
					
					value.deleteCharAt(value.length() - 1);
					
					subKeys.clear();
					
					return;
				} else {
					obj = ((JSONArray)obj).get(Integer.valueOf(key));
				}
			} else {
				throw new SoftenJiraException("Not json type");
			}
		}
		
		value.append(obj.toString());
	}
	
	private static void addKey(List<String> keys, StringBuilder key) {
		if (key.length() == 0)
			return;
//			throw new SoftenJiraException("Invalid expression: empty key");
		
		keys.add(key.toString());
		key.setLength(0);
	}
	
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}
	
	public static void main(String[] args) {
		String data = ""
				+ "	{"
				+ "		\"soften\":{"
				+ "			\"issues\":[{"
				+ "				\"key\":\"Key-1\", \"updated\":\"2019-06-02\""
				+ "			}, {"
				+ "				\"key\":\"Key-2\", \"updated\":\"2019-06-01\""
				+ "			}],"
				+ "			\"list3\":[1, 2, 3]"
				+ "		},"
				+ "		\"lv1\":\"lv1-value\","
				+ "		\"nullable\":null,"
				+ "		\"list1\":[],"
				+ "		\"list2\":[\"A\", \"B\", \"C\"],"
				+ "	}";
		JSONObject json = new JSONObject(data);
		
		String value;
		
		value = getJSONValue(json, "soften.issues.*.key + \" - \" + soften.issues.0.updated");
		System.out.println(value);
		
		value = getJSONValue(json, "lv1");
		System.out.println(value);
		
		value = getJSONValue(json, "nullable");
		System.out.println(value);
		
		value = getJSONValue(json, "list1");
		System.out.println(value);
		
		value = getJSONValue(json, "list2");
		System.out.println(value);
		
		value = getJSONValue(json, "soften.list3");
		System.out.println(value);
	}
}
