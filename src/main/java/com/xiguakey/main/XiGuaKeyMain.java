package com.xiguakey.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.browser.Browser;
import com.common.browser.impl.BrowserHttpclient;
import com.xiguakey.model.Discuss;
import com.xiguakey.model.Video;
import com.xiguakey.parser.XiGuaParser;

/**
 * 西瓜视频入口类
 * 
 * @author WRY
 *
 */
public class XiGuaKeyMain {
	Map<String, String> param = new HashMap<>();

	public XiGuaKeyMain() {
		super();
		param.put("cookie", null);
	}

	/**
	 * 解析视频标签内容
	 * 
	 * @param keyWorld 搜索关键词
	 * @return
	 */
	public List<Video> getVideos(String keyWorld) {
		List<Video> videoList = new ArrayList<>();
		Browser bs = new BrowserHttpclient();
		XiGuaParser xiGuaParser = new XiGuaParser();
		String url = null;
		String soundCode = null;
		int offset = 0;
		while (true) {
			url = "https://security.snssdk.com/video/app/search/search_content/?from=video&keyword=" + keyWorld
					+ "&iid=25377382466&device_id=47753959844&ac=wifi&channel=vivo&aid=32&app_name=video_article&version_code=639&version_name=6.3.9&device_platform=android&ab_version=271217%252C236847%252C246276%252C273539%252C271584%252C271962%252C261931%252C217285%252C249822%252C268198%252C227960%252C249631%252C273774%252C252883%252C255490%252C252979%252C257292%252C249824%252C249819%252C249827%252C249830%252C273215%252C264627%252C272859%252C265856%252C274099%252C150151&device_type=vivo%2BY51e&device_brand=vivo&language=zh&os_api=22&os_version=5.1.1&uuid=862063039571455&openudid=3375f0196b3f6b72&manifest_version_code=239&resolution=540*960&dpi=240&update_version_code=6392&_rticket=1517749692385&fp=zlTqLSPMcWs5FlP5LSU1FYwIFlK1&keyword_type=video&search_sug=1&forum=1&no_outsite_res=0&as=A165FA5727A05BE&cp=5A7780E53B4E7E1&count=10&cur_tab=2&format=json&offset="
					+ offset + "&search_id=&m_tab=&en_qc=1";
			soundCode = bs.httpGet(url, param);
			boolean status = xiGuaParser.parserVideoList(soundCode, videoList);
			if (!status) {
				break;
			}
			offset += 10;
		}
		return videoList;
	}

	/**
	 * 获取视频详细信息
	 * 
	 * @param url
	 * @return
	 */
	public void getVideo(Video video) {
		String url = "https://is.snssdk.com/video/app/article/full/15/1/" + video.getId_str() + "/" + video.getId_str()
				+ "/1/0/?iid=25377382466&device_id=47753959844&ac=wifi&channel=vivo&aid=32&app_name=video_article&version_code=639&version_name=6.3.9&device_platform=android&ab_version=271217%2C236847%2C246276%2C273539%2C271584%2C271962%2C261931%2C217285%2C249822%2C268198%2C227960%2C249631%2C273774%2C252883%2C255490%2C252979%2C257292%2C249824%2C249819%2C249827%2C249830%2C273215%2C264627%2C272859%2C274099%2C150151&ssmix=a&device_type=vivo+Y51e&device_brand=vivo&language=zh&os_api=22&os_version=5.1.1&uuid=862063039571455&openudid=3375f0196b3f6b72&manifest_version_code=239&resolution=540*960&dpi=240&update_version_code=6392&_rticket=1517751215103&fp=zlTqLSPMcWs5FlP5LSU1FYwIFlK1&ts=1517751212&as=a275f017ec6aea0b576406&mas=008974dff05e53b841ddbbf5f83f8fef5aa30e8d165cb964e9a9c9";
		Browser bs = new BrowserHttpclient();
		String soundCode = bs.httpGet(url, param);
		XiGuaParser xiGuaParser = new XiGuaParser();
		xiGuaParser.parserVideo(soundCode, video);
	}

	/**
	 * 解析视频链接
	 * 
	 * @param video
	 * @return
	 */
	public List<Discuss> getDiscuss(Video video) {
		List<Discuss> discussList = new ArrayList<>();
		XiGuaParser xiGuaParser = new XiGuaParser();
		Browser bs = new BrowserHttpclient();
		int offset = 0;
		while (true) {
			String url = "https://is.snssdk.com/article/v2/tab_comments/?group_id=" + video.getId_str() + "&item_id="
					+ video.getId_str() + "&aggr_type=1&count=20&offset=" + offset
					+ "&tab_index=0&iid=25377382466&device_id=47753959844&ac=wifi&channel=vivo&aid=32&app_name=video_article&version_code=639&version_name=6.3.9&device_platform=android&ab_version=271217%2C236847%2C246276%2C273539%2C271584%2C217285%2C249822%2C268198%2C227960%2C249631%2C273774%2C252883%2C255490%2C252979%2C257292%2C249824%2C249819%2C249827%2C249830%2C273215%2C264627%2C272859%2C275010%2C274099%2C150151&ssmix=a&device_type=vivo+Y51e&device_brand=vivo&language=zh&os_api=22&os_version=5.1.1&uuid=862063039571455&openudid=3375f0196b3f6b72&manifest_version_code=239&resolution=540*960&dpi=240&update_version_code=6392&_rticket=1517810590116&fp=zlTqLSPMcWs5FlP5LSU1FYwIFlK1&ts=1517810586&as=a2a5ff578a19daf3a78704&mas=000903536cf86832899fe09fe571a8b3c0da1f2e7211d50b0d7757";
			String soundCode = bs.httpGet(url, param);
			boolean status = xiGuaParser.parserDisscussList(soundCode, video, discussList);
			if (!status) {
				break;
			}
			offset += 20;
		}
		return discussList;
	}
}
