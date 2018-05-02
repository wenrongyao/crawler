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
 * 微博视频key的入口类
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
	 * 获取搜索得到的视频
	 * @param url 目标链接
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
	 * 获取视频的详细信息
	 * @param url 目标链接
	 * @return
	 */
	public Video getVideo(String url) {
		Browser bs = new BrowserHttpclient();
		String soundCode = bs.httpGet(url, param);
		WeiboKeyParser weiboKeyParser = new WeiboKeyParser();
		return weiboKeyParser.parseVideo(soundCode);
	}
	
	/**
	 * 获取评论信息
	 * 
	 * @param mid
	 *            用于拼凑评论链接的参数
	 * @return 评论列表
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
			LogUtil.LogInfo("获取视频评论异常，请检查网络" + e.getMessage());
		}
		return discussList;
	}

	/**
	 * 获取视频的转发规律
	 * 
	 * @param mid
	 *            用于拼凑小视频转发链接的参数
	 * @return 小视频转发列表
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
			LogUtil.LogInfo("获取视频转发异常，请检查网络" + e.getMessage());
		}
		return transpondList;
	}
}
