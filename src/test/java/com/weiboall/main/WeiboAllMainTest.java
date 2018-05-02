package com.weiboall.main;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.common.constant.R;
import com.weiboall.model.Discuss;
import com.weiboall.model.Transpond;
import com.weiboall.model.Video;
import com.weiboall.service.BerkeleyDBService;

public class WeiboAllMainTest {

	@Test
	public void testWeiboAllMain() {
		WeiboAllMain weiboAllMain = new WeiboAllMain();
		String url = "http://www.weibo.com/tv";
		// 获取所有视频链接
		// weiboAllMain.getVideoUrls(url);
		// 获取视频信息
		BerkeleyDBService berkeleyDbService = new BerkeleyDBService(R.URLS);
		Map<String, String> urlsMap = berkeleyDbService.getAll();
		for (@SuppressWarnings("rawtypes")
		Entry entry : urlsMap.entrySet()) {
			Video video = weiboAllMain.getVideo((String) entry.getValue());
			List<Discuss> discussList = weiboAllMain.getDiscuss(video);
			List<Transpond> transpondList = weiboAllMain.getTranspond(video);
			System.out.println(video);
			for (Discuss discuss : discussList) {
				System.out.println(discuss);
			}
			for (Transpond transpond : transpondList) {
				System.out.println(transpond);
			}
		}
	}

}
