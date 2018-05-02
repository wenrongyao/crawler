package com.common.browser.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import com.common.browser.Browser;
import com.common.constant.R;

/**
 * ���������phantomjsʵ��
 * 
 * @author Administrator
 *
 */
public class BrowserPhantomjs implements Browser{

	/**
	 * get ����
	 * @param url
	 * @param param
	 * @return
	 */
	public String httpGet(String url, Map<String, String> param) {
		StringBuffer sb = new StringBuffer();
		Runtime rt = Runtime.getRuntime();
		String exec = R.CURRENTURL + "/phantomjs-2.1.1-windows/bin/phantomjs " + param.get("currenturl") + param.get("codejsurl") + " " + url;
System.out.println(exec);
		
		Process p = null;
		BufferedReader br = null;
		try {
			p = rt.exec(exec);
			// Ϊ��ʹphantomjs�Ĵ��������ȫ���߳�����5����
			Thread.sleep(5000);
			br = new BufferedReader(new InputStreamReader(p.getInputStream(), "utf-8"));
			String s = null;
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
					br = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}
}
