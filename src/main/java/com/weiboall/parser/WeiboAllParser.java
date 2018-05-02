package com.weiboall.parser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.common.browser.impl.BrowserHttpclient;
import com.common.constant.R;
import com.common.util.CompressUtil;
import com.common.util.LogUtil;
import com.weiboall.model.Discuss;
import com.weiboall.model.Transpond;
import com.weiboall.model.Video;



/**
 * 解析源码
 * 
 * @author Administrator
 *
 */
public class WeiboAllParser {
	/**
	 * 获取标题链接
	 * 
	 * @param soundCode
	 * @return
	 */
	public Map<String, String> parserTitleLinks(String soundCode) {
		Map<String, String> titleMap = new HashMap<String, String>();
		try {
			Document doc = Jsoup.parse(soundCode);
			Elements elements = doc.getElementsByClass("tab_item orange_border");
			for (Element element : elements) {
				String url = element.attr("href");
				String type = element.text();
				if (type != null && !type.trim().equals("") && url != null && !url.equals("")) {
					int start = url.lastIndexOf("/");
					titleMap.put(url.substring(start + 1).toUpperCase(), "https://weibo.com" + url);
				}
			}
		} catch (Exception e) {
			LogUtil.LogInfo("title链接解析异常" + e.getMessage());
		}

		return titleMap;
	}

	// 小视频链接处理 ---开始

	/**
	 * 获取小视频链接
	 * 
	 * @param soundCode
	 *            源码
	 * 
	 * @param type
	 *            视频类型
	 * @return
	 */
	public Map<String, String> parserVideoLinks(String soundCode, String type) {
		int counter = 0;
		String end_id = "";
		Map<String, String> videoLinksMap = new HashMap<String, String>();
		try {
			Document doc = Jsoup.parse(soundCode);
			Elements ul = doc.getElementsByClass("li_list_1");
			for (Element aTag : ul) {
				String innerHtml = aTag.html();
				doc = Jsoup.parse(innerHtml);
				Elements links = doc.getElementsByTag("a");
				for (Element link : links) {
					String url = "https://weibo.com" + link.attr("href");
					String key = CompressUtil.getMD5(url);
					videoLinksMap.put(key, url);
					counter++;
					int page = 2;
					end_id = link.attr("mid");
					while (counter == R.VIDEO_NUMBER && !end_id.equals("")) {
						// ajax请求的链接
						String ajaxUrl = "https://weibo.com/p/aj/v6/mblog/videolist?type=" + type + "&page=" + page
								+ "&end_id=" + end_id + "&__rnd=1511396483798";
						end_id = downloadFile(videoLinksMap, ajaxUrl);
						page++;
					}
				}
			}
		} catch (Exception e) {
			LogUtil.LogInfo("小视频链接解析异常" + e.getMessage());
		}
		return videoLinksMap;
	}

	/**
	 * 处理小视频的ajax请求
	 * 
	 * @param linkMap
	 *            存储小视频链接的map
	 * @param ajaxUrl
	 *            ajax请求的链接
	 */
	private String downloadFile(Map<String, String> linkMap, String ajaxUrl) {
		String end_id = "";
		try {
			BrowserHttpclient bs = new BrowserHttpclient();
			Map<String, String> param = new HashMap<>();
			param.put("cookie", R.WEIBOCOOKIE);
			String jsonStr = bs.httpGet(ajaxUrl, param);
			int counter = 0;
			JSONObject jsonObject = new JSONObject(jsonStr);
			JSONObject dataJson = new JSONObject(jsonObject.get("data").toString());
			Document doc = Jsoup.parse(dataJson.getString("data"));
			Elements links = doc.getElementsByTag("a");
			for (Element link : links) {
				String url = "https://weibo.com" + link.attr("href");
				String key = CompressUtil.getMD5(url);
				linkMap.put(key, url);
				counter++;
				if (counter == R.VIDEO_NUMBER) {
					end_id = link.attr("mid");
				} else {
					end_id = "";
				}
			}
		} catch (Exception e) {
			LogUtil.LogInfo("小视频的ajax请求链接异常" + e.getMessage());
		}
		return end_id;
	}

	// 小视频链接处理 ---小视频链接处理结束

	// 小视频内容处理 ---小视频链接处理开始
	/**
	 * 解析小视频页面
	 * 
	 * @param soundCode
	 * @return
	 */
	public static Video parserVideo(String soundCode) {
		Video video = new Video();
		try {
			Document doc = Jsoup.parse(soundCode);
			Document docTemp = null;
			Elements elements = null;
			// 小视频标题
			elements = doc.getElementsByClass("info_txt W_f14");
			video.setTitle(elements.get(0).text().trim());
			// 作者
			elements = doc.getElementsByClass("W_f14 L_autocut bot_name W_fl");
			video.setAuthor(elements.get(0).text().trim());
			// 作者链接
			elements = doc.getElementsByClass("bot_m W_fl clearfix");
			for (Element element : elements) {
				docTemp = Jsoup.parse(element.html());
				elements = docTemp.getElementsByTag("a");
				video.setAuthorLink("https:" + elements.get(0).attr("href"));
			}
			// 播放次数
			elements = doc.getElementsByClass("bot_number W_fr W_f14");
			docTemp = Jsoup.parse(elements.html());
			elements = docTemp.getElementsByTag("em");
			String str = elements.get(1).text().trim();
			int end = str.indexOf("次");
			video.setTimesOfPlay(str.substring(0, end).trim().replace("万", "0000"));
			// 转发次数
			elements = doc.getElementsByClass("pos");
			docTemp = Jsoup.parse(elements.html());
			elements = docTemp.getElementsByTag("em");
			str = elements.get(1).text().trim();
			if (!str.matches("\\d+")) {
				str = 0 + "";
			}
			video.setTranspond(str);
			// 评论
			str = elements.get(3).text().trim();
			if (!str.matches("\\d+")) {
				str = 0 + "";
			}
			video.setDiscuss(str);
			// 点赞
			str = elements.get(5).text().trim();
			if (!str.matches("\\d+")) {
				str = 0 + "";
			}
			video.setGoodPress(str);
			// 获取视频的mid
			elements = doc.getElementsByClass("WB_row_line WB_row_r3 clearfix S_line2");
			docTemp = Jsoup.parse(elements.html());
			elements = docTemp.getElementsByTag("li").get(0).getElementsByTag("a");
			str = elements.attr("action-data");
			int start = str.indexOf("mid=") + 4;
			end = start + 16;
			video.setMid(str.substring(start, end));
		} catch (Exception e) {
			LogUtil.LogInfo("小视频内容解析异常" + e.getMessage());
		}
		return video;
	}

	/**
	 * 解析评论的内容
	 * 
	 * @param soundCode
	 * @return
	 */
	public static List<Discuss> parserDiscuss(String soundCode, Video video) {
		Map<String, String> param = new HashMap<>();
		param.put("cookie", R.WEIBOCOOKIE);
		List<Discuss> discussList = new ArrayList<Discuss>();
		try {
			Document doc = Jsoup.parse(soundCode);
			String url = parseDiscussJson(doc, discussList,video);
			while (!url.equals("")) {
				String ajaxLink = "https://weibo.com/aj/v6/comment/big?ajwvr=6&" + url
						+ "&from=singleWeiBo&__rnd=1512440330247";
				try {
					String jsonStr = new BrowserHttpclient().httpGet(ajaxLink, param);
					JSONObject jsonObj = new JSONObject(jsonStr);
					jsonObj = (JSONObject) jsonObj.get("data");
					String html2 = jsonObj.getString("html");
					doc = Jsoup.parse(html2);
					url = parseDiscussJson(doc, discussList, video);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			LogUtil.LogInfo("解析评论异常" + e.getMessage());
		}
		return discussList;
	}

	/**
	 * 解析discuss的json串
	 * 
	 * @param doc
	 * @param discussList
	 * @return
	 */
	private static String parseDiscussJson(Document doc, List<Discuss> discussList, Video video) {
		String url = null;
		try {
			Elements elements = doc.getElementsByAttributeValue("node-type", "replywrap");
			Document docTemp = null;
			Elements elementsTemp = null;
			Discuss discuss = null;
			if (elements != null) {
				for (Element element : elements) {
					docTemp = Jsoup.parse(element.html());
					elementsTemp = docTemp.getElementsByTag("div");
					// 获取评论内容
					String comment = elementsTemp.get(0).ownText().substring(1);
					// 获取评论作者
					String str = elementsTemp.get(0).html();
					docTemp = Jsoup.parse(str);
					elementsTemp = docTemp.getElementsByTag("a");
					String author = elementsTemp.get(0).text();
					discuss = new Discuss();
					discuss.setAuthor(author);
					discuss.setComment(comment);
					discuss.setVideo(video);
					discussList.add(discuss);
				}
				elementsTemp = doc.getElementsByAttributeValue("node-type", "comment_loading");
				if (elementsTemp.size() == 0) {
					elementsTemp = doc.getElementsByAttributeValue("action-type", "click_more_comment");
				}
				if (elementsTemp.size() != 0) {
					url = elementsTemp.attr("action-data");
				} else {
					url = "";
				}
			} else {
				url = "";
			}
		} catch (Exception e) {
			LogUtil.LogInfo("解析评论的json串异常" + e.getMessage());
		}
		return url;
	}

	/**
	 * 解析视频转发页面
	 * 
	 * @param soundCode
	 * @param mid
	 * @return
	 */
	public static List<Transpond> parserTranspond(String soundCode, Video video) {
		Map<String, String> param = new HashMap<>();
		param.put("cookie", R.WEIBOCOOKIE);
		List<Transpond> transpondList = new ArrayList<Transpond>();
		try {
			Document doc = Jsoup.parse(soundCode);
			String url = parseTranspondJson(doc, transpondList, video);
			while (url != "") {
				String ajaxLink = "https://www.weibo.com/aj/v6/mblog/info/big?ajwvr=6&" + url + "&__rnd=1512700604825";
				// System.out.println(ajaxLink);
				try {
					String jsonStr = new BrowserHttpclient().httpGet(ajaxLink, param);
					JSONObject jsonObj = new JSONObject(jsonStr);
					jsonObj = (JSONObject) jsonObj.get("data");
					String html2 = jsonObj.getString("html");
					doc = Jsoup.parse(html2);
					url = parseTranspondJson(doc, transpondList, video);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		} catch (Exception e) {
			LogUtil.LogInfo("解析转发页面异常" + e.getMessage());
		}
		return transpondList;
	}

	/**
	 * 解析transpond的json串
	 * 
	 * @param doc
	 * @param trandspondList
	 * @return
	 */
	private static String parseTranspondJson(Document doc, List<Transpond> trandspondList, Video video) {
		String url = null;
		try {
			Elements elements = doc.getElementsByClass("WB_text");
			Document docTemp = null;
			Elements elementsTemp = null;
			List<Transpond> oneChain = null;
			for (Element element : elements) {
				docTemp = Jsoup.parse(element.html());
				// 一次完整的转发链接
				elementsTemp = docTemp.getElementsByTag("a");
				oneChain = new ArrayList<Transpond>();
				int spaceCounter = 1;
				for (int i = 0; i < elementsTemp.size(); i++) {
					Transpond transpond = null;
					String name = null;
					String nameTemp = null;
					// 第一个节点没有子节点
					if (i == 0) {
						name = elementsTemp.get(0).text();
						// 当前转发对象
						transpond = new Transpond(name);
					}
					/*
					 * else if (i == elementsTemp.size()) { name = video.getAuthor(); // 当前转发对象
					 * transpond = new Transpond(name); // 此时是最后一个节点，它的父节点就是作者自己 oneChain.get(i -
					 * spaceCounter).setParent(transpond); // 当前节点是前面一个节点的父节点 }
					 */
					else {
						nameTemp = elementsTemp.get(i).attr("usercard");
						if (nameTemp != null && !nameTemp.equals("")) {
							name = nameTemp.substring(5);
							// 父节点
							// 当前转发对象
							transpond = new Transpond(name);
							oneChain.get(i - spaceCounter).setParent(transpond);
						}
					}
					if (name != null && !name.equals("")) {
						// 设置和video的外键关系
						transpond.setVideo(video);
						oneChain.add(transpond);
					} else {
						spaceCounter += 1;
					}
				}
				// 将一个完整的转发路径存入链表中
				trandspondList.add(oneChain.get(0));
			}
			elementsTemp = doc.getElementsByClass("page next S_txt1 S_line1");
			if (elementsTemp.size() != 0) {
				url = elementsTemp.first().getElementsByTag("span").first().attr("action-data");
			} else {
				url = "";
			}
		} catch (Exception e) {
			LogUtil.LogInfo("解析转发的json串异常" + e.getMessage());
		}
		return url;
	}
	// 小视频内容处理 ---小视频链接处理结束
}
