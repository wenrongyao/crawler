package com.common.browser.impl;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.common.browser.Browser;
import com.common.constant.R;

/**
 * 浏览器类用phantomjs实现
 * 
 * @author Administrator
 *
 */
public class BrowserPhantomjs implements Browser {

	/**
	 * get 请求
	 * 
	 * @param url
	 * @param param
	 * @return
	 */
	public String httpGet(String url, Map<String, String> param) {
		DesiredCapabilities dcaps = new DesiredCapabilities();
		// ssl证书支持
		dcaps.setCapability("acceptSslCerts", false);
		// 截屏支持
		dcaps.setCapability("takesScreenshot", false);
		// css搜索支持
		dcaps.setCapability("cssSelectorsEnabled", false);
		// js支持
		dcaps.setJavascriptEnabled(true);
		// 驱动支持
		dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
				R.CURRENTURL + "/phantomjs-2.1.1-windows/bin/phantomjs.exe");
		WebDriver webDriver = null;
		try {
			webDriver = new PhantomJSDriver(dcaps);
			webDriver.get(url);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (webDriver != null) {
				webDriver.close();
			}
		}
		return webDriver.getPageSource();
	}
}
