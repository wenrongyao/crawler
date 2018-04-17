package com.wry.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.wry.constant.R;
import com.wry.log.LogUtil;

/**
 * 浏览器类
 * 
 * @author Administrator
 *
 */
public class BrowserHttpclient {
	private String url;
	private CloseableHttpClient httpClient;
	private CloseableHttpResponse httpResponse;

	/**
	 * 构造方法
	 * 
	 * @param url
	 *            浏览链接
	 */
	public BrowserHttpclient(String url) throws Exception {
		if (!checkoutUrl(url)) {
			throw new Exception("url异常");
		}
		httpClient = HttpClientBuilder.create().build();
		this.url = url;
	}

	/**
	 * 用来校验url的格式是否正确
	 * 
	 * @param url
	 * @return
	 */
	private boolean checkoutUrl(String url) {
		// 校验结果
		boolean result = true;

		return result;
	}

	/**
	 * get请求
	 * 
	 */
	public String httpGet() {
		/*
		 * 超时设置 setConnectionRequestTimeout
		 * 从httpclient池中获取连接的，setConnectTimeout三次握手的时间，setSocketTimeout数据包的间隔时间
		 */
		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(5000)
				.setSocketTimeout(5000).build();
		// 创建httpGet请求，设置httpGet请求的头部信息
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(config);
		httpGet.addHeader("Cookie", R.COOKIE);

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
