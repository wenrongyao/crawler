package com.common.browser;

import java.util.Map;

public interface Browser {
	// get����
	public String httpGet(String url, Map<String, String> param);
}
