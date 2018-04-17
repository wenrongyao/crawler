package com.wry.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.wry.constant.R;

/**
 * ���������phantomjsʵ��
 * 
 * @author Administrator
 *
 */
public class BrowserPhantomjs {

	/**
	 * ����url������ҳ������ɺ��Դ����
	 * 
	 * @param url
	 * @return
	 */
	public String httpGet(String url) {
		StringBuffer sb = new StringBuffer();
		Runtime rt = Runtime.getRuntime();
		String exec = R.CURRENTURL + "\\phantomjs-2.1.1-windows\\bin\\phantomjs " + R.CURRENTURL + R.JSURL + " " + url;
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
			// TODO Auto-generated catch block
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
