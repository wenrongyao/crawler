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
 * �������
 * 
 * @author Administrator
 *
 */
public class BrowserHttpclient {
	private String url;
	private CloseableHttpClient httpClient;
	private CloseableHttpResponse httpResponse;

	/**
	 * ���췽��
	 * 
	 * @param url
	 *            �������
	 */
	public BrowserHttpclient(String url) throws Exception {
		if (!checkoutUrl(url)) {
			throw new Exception("url�쳣");
		}
		httpClient = HttpClientBuilder.create().build();
		this.url = url;
	}

	/**
	 * ����У��url�ĸ�ʽ�Ƿ���ȷ
	 * 
	 * @param url
	 * @return
	 */
	private boolean checkoutUrl(String url) {
		// У����
		boolean result = true;

		return result;
	}

	/**
	 * get����
	 * 
	 */
	public String httpGet() {
		/*
		 * ��ʱ���� setConnectionRequestTimeout
		 * ��httpclient���л�ȡ���ӵģ�setConnectTimeout�������ֵ�ʱ�䣬setSocketTimeout���ݰ��ļ��ʱ��
		 */
		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(5000)
				.setSocketTimeout(5000).build();
		// ����httpGet��������httpGet�����ͷ����Ϣ
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
