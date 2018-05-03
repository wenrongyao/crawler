package com.weiboall.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONException;
import org.json.JSONObject;

import com.common.browser.Browser;
import com.common.browser.impl.BrowserHttpclient;
import com.common.constant.R;
import com.common.util.LogUtil;
import com.weiboall.model.Discuss;
import com.weiboall.model.Transpond;
import com.weiboall.model.Video;
import com.weiboall.parser.WeiboAllParser;
import com.weiboall.service.BerkeleyDBService;

/**
 * ΢����Ƶall�����
 * 
 * @author WRY
 *
 */
public class WeiboAllMain {
	// �������
	private Map<String, String> param = new HashMap<>();

	public WeiboAllMain() {
		super();
		param.put("cookie", R.WEIBOCOOKIE);
	}

	/**
	 * �ɼ�Ŀ�������µ���������
	 * 
	 * @param url
	 *            Ŀ������
	 */
	public void getVideoUrls(String url) {
		Browser bs = new BrowserHttpclient();
		String soundCode = bs.httpGet(url, param);
		WeiboAllParser weiboParser = new WeiboAllParser();
		Map<String, String> titleMap = weiboParser.parserTitleLinks(soundCode);
		BerkeleyDBService urls = new BerkeleyDBService(R.URLS);
		BerkeleyDBService visitedUrls = new BerkeleyDBService(R.VISITEDURLS);
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
		for (Entry<String, String> entry : titleMap.entrySet()) {
			fixedThreadPool.execute(new Runnable() {
				String url;
				Browser bs;
				String soundCode;
				WeiboAllParser weiboParser;

				@Override
				public void run() {
					url = entry.getValue();
					bs = new BrowserHttpclient();
					soundCode = bs.httpGet(url, param);
					weiboParser = new WeiboAllParser();
					Map<String, String> videoLinksMap = weiboParser.parserVideoLinks(soundCode,
							entry.getKey().toLowerCase());
					for (Map.Entry<String, String> entity : videoLinksMap.entrySet()) {
						if (visitedUrls.get(entry.getKey()) == null) {
							urls.put(entity.getKey(), entity.getValue(), false);
						}
					}
				}
			});
		}
	}

	/**
	 * �ɼ�Ŀ�������е�С��Ƶ��Ϣ
	 * 
	 * @param url
	 *            С��Ƶ����
	 * @return С��Ƶ��Ϣ
	 */
	public Video getVideo(String url) {
		BrowserHttpclient bs = new BrowserHttpclient();
		String soundCode = bs.httpGet(url, param);
		// һ�ν���ÿ��С��Ƶҳ��
		WeiboAllParser weiboParser = new WeiboAllParser();
		@SuppressWarnings("static-access")
		Video video = weiboParser.parserVideo(soundCode);
		// ��Ƶ����
		video.setUrl(url);
		// ��Ƶ����
		video.setType(url.substring(url.indexOf("=") + 1));
		return video;
	}

	/**
	 * ��ȡ������Ϣ
	 * 
	 * @param mid
	 *            ����ƴ���������ӵĲ���
	 * @return �����б�
	 */
	@SuppressWarnings("static-access")
	public List<Discuss> getDiscuss(Video video) {
		List<Discuss> discussList = new ArrayList<Discuss>();
		try {
			String url = "https://weibo.com/aj/v6/comment/big?ajwvr=6&id=" + video.getMid()
					+ "&from=singleWeiBo&__rnd=1512440330247";
			BrowserHttpclient bs = new BrowserHttpclient();
			String jsonStr = bs.httpGet(url, param);
			JSONObject jsonObj = new JSONObject(jsonStr);
			jsonObj = (JSONObject) jsonObj.get("data");
			String soundCode = jsonObj.getString("html");
			WeiboAllParser weiboParser = new WeiboAllParser();
			discussList = weiboParser.parserDiscuss(soundCode, video);
		} catch (JSONException e) {
			LogUtil.LogInfo("��ȡ��Ƶ�����쳣����������" + e.getMessage());
		}
		return discussList;
	}

	/**
	 * ��ȡ��Ƶ��ת������
	 * 
	 * @param mid
	 *            ����ƴ��С��Ƶת�����ӵĲ���
	 * @return С��Ƶת���б�
	 */
	@SuppressWarnings("static-access")
	public List<Transpond> getTranspond(Video video) {
		List<Transpond> transpondList = new ArrayList<Transpond>();
		try {
			String url = "https://www.weibo.com/aj/v6/mblog/info/big?ajwvr=6&id=" + video.getMid()
					+ "&__rnd=1512698545178";
			BrowserHttpclient bs = new BrowserHttpclient();
			String jsonStr = bs.httpGet(url, param);
			JSONObject jsonObj = new JSONObject(jsonStr);
			jsonObj = (JSONObject) jsonObj.get("data");
			String soundCode = jsonObj.getString("html");
			WeiboAllParser weiboParser = new WeiboAllParser();
			transpondList = weiboParser.parserTranspond(soundCode, video);
		} catch (JSONException e) {
			LogUtil.LogInfo("��ȡ��Ƶת���쳣����������" + e.getMessage());
		}
		return transpondList;
	}
}
