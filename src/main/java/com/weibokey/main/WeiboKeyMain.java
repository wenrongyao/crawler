package com.weibokey.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.common.browser.Browser;
import com.common.browser.impl.BrowserHttpclient;
import com.common.browser.impl.BrowserPhantomjs;
import com.common.constant.R;
import com.common.util.LogUtil;
import com.weibokey.model.Discuss;
import com.weibokey.model.Transpond;
import com.weibokey.model.Video;
import com.weibokey.parser.WeiboKeyParser;

/**
 * ΢����Ƶkey�������
 * @author WRY
 *
 */
public class WeiboKeyMain {
	Map<String,String> param = new HashMap<>();
	
	public WeiboKeyMain() {
		super();
		param.put("currenturl", R.CURRENTURL);
		param.put("codejsurl", R.CODEJSURL);
	}
	/**
	 * ��ȡ�����õ�����Ƶ
	 * @param url Ŀ������
	 * @return
	 */
	public List<Video> getVideos(String url){
		Browser bs = new BrowserPhantomjs();
		String soundCode = bs.httpGet(url, param);
System.out.println(soundCode);		
		return null;
//		WeiboKeyParser weiboKeyParser = new WeiboKeyParser();
//		return weiboKeyParser.parseVideos(soundCode);
	}
	
	/**
	 * ��ȡ��Ƶ����ϸ��Ϣ
	 * @param url Ŀ������
	 * @return
	 */
	public Video getVideo(String url) {
		Browser bs = new BrowserHttpclient();
		String soundCode = bs.httpGet(url, param);
		WeiboKeyParser weiboKeyParser = new WeiboKeyParser();
		return weiboKeyParser.parseVideo(soundCode);
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
			WeiboKeyParser weibokeyParser = new WeiboKeyParser();
			discussList = weibokeyParser.parserDiscuss(soundCode, video);
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
			String url = "https://www.weibo.com/aj/v6/mblog/info/big?ajwvr=6&id=" + video.getMid() + "&__rnd=1512698545178";
			BrowserHttpclient bs = new BrowserHttpclient();
			String jsonStr = bs.httpGet(url, param);
			JSONObject jsonObj = new JSONObject(jsonStr);
			jsonObj = (JSONObject) jsonObj.get("data");
			String soundCode = jsonObj.getString("html");
			WeiboKeyParser weiboKeyParser = new WeiboKeyParser();
			transpondList = weiboKeyParser.parserTranspond(soundCode, video);
		} catch (JSONException e) {
			LogUtil.LogInfo("��ȡ��Ƶת���쳣����������" + e.getMessage());
		}
		return transpondList;
	}
}
