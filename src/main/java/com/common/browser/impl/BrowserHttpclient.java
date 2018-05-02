package com.common.browser.impl;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.common.browser.Browser;
import com.wry.log.LogUtil;

/**
 * 浏览器类
 * 
 * @author Administrator
 *
 */
public class BrowserHttpclient implements Browser{
	private CloseableHttpClient httpClient;
	private CloseableHttpResponse httpResponse;

	public BrowserHttpclient() throws Exception {
		httpClient = HttpClientBuilder.create().build();
	}

	/**
	 * get 请求
	 * 
	 * @param url
	 * @param param
	 * @return
	 */
	public String httpGet(String url, Map<String, String> param) {
		/*
		 * 超时设置 setConnectionRequestTimeout
		 * 从httpclient池中获取连接的，setConnectTimeout三次握手的时间，setSocketTimeout数据包的间隔时间
		 */
		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(5000)
				.setSocketTimeout(5000).build();
		// 创建httpGet请求，设置httpGet请求的头部信息
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(config);
		httpGet.addHeader("Cookie", param.get("cookie"));

		String soundCode = null;
		try {
			httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = httpResponse.getEntity();
				soundCode = EntityUtils.toString(entity, "utf-8");
			}
		} catch (ClientProtocolException e) {
			LogUtil.LogInfo(e.getMessage());
		} catch (IOException e) {
			LogUtil.LogInfo(e.getMessage());
		} finally {
			if (httpResponse != null) {
				try {
					httpResponse.close();
					httpResponse = null;
				} catch (IOException e) {
					LogUtil.LogInfo(e.getMessage());
				}
			}
			if (httpClient != null) {
				try {
					httpClient.close();
					httpClient = null;
				} catch (IOException e) {
					LogUtil.LogInfo(e.getMessage());
				}
			}
		}
		return soundCode;
	}

}
