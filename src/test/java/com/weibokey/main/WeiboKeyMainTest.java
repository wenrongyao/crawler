package com.weibokey.main;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.common.constant.R;
import com.weibokey.model.Video;

public class WeiboKeyMainTest {

	@Test
	public void testWeiboKeyMain() {
		String url = "http://s.weibo.com/weibo/%25E5%258D%2581%25E4%25B9%259D%25E5%25A4%25A7&xsort=hot&hasvideo=1&searchvideo=1&Refer=STopic_box";
		WeiboKeyMain weiboKeyMain = new WeiboKeyMain();
		List<Video> videoList = weiboKeyMain.getVideos(url);
		for (Video video : videoList) {
			System.out.println(video);
		}
	}

	@Test
	public void phantomjsTest() {
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
			webDriver.get(
					"http://s.weibo.com/weibo/%25E5%258D%2581%25E4%25B9%259D%25E5%25A4%25A7&Refer=weibo_weibo&xsort=hot&hasvideo=1&searchvideo=1");
//			System.out.println(webDriver.getPageSource());
			FileWriter fw = new FileWriter("D:/demo.html");
			fw.write(webDriver.getPageSource());
			fw.flush();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (webDriver != null) {
				webDriver.close();
			}
		}
	}

}
