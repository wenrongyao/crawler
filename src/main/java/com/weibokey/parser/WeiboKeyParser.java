package com.weibokey.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.common.browser.impl.BrowserHttpclient;
import com.common.constant.R;
import com.common.util.LogUtil;
import com.weibokey.model.Discuss;
import com.weibokey.model.Transpond;
import com.weibokey.model.Video;

/**
 * ΢���������ݵĽ�����
 * 
 * @author Administrator
 *
 */
public class WeiboKeyParser {

	/**
	 * �����������С��Ƶ���õ�Դ���Ӻͱ��⣨����������ѡ��ʹ�ã���
	 * 
	 * @param soundCode
	 * @return
	 */
	public List<Video> parseVideos(String soundCode) {
		List<Video> videoList = new LinkedList<Video>();
		try {
			if (soundCode != null && !soundCode.equals("")) {
				Document doc = Jsoup.parse(soundCode);
				Elements elements = doc.getElementsByClass("WB_cardwrap S_bg2 clearfix");
				Video video = null;
				for (Element element : elements) {
					video = new Video();
					// ����
					String title = element.getElementsByClass("comment_txt").text();
					video.setTitle(title);
					// Դ����
					String soundUrl = element.getElementsByClass("feed_from W_textb").get(0).getElementsByTag("a")
							.get(0).attr("href");
					video.setSoundUrl("https:" + soundUrl);
					videoList.add(video);
				}
			}
		} catch (Exception e) {
			LogUtil.LogInfo("΢����Ƶkey��������ƵԴ���ӳ����쳣" + e.getMessage());
		}
		return videoList;
	}

	/**
	 * ����С��Ƶ�������ϸ��Ϣ
	 * 
	 * @param soundCode
	 * @param video
	 */
	public Video parseVideo(String soundCode) {
		Video video = new Video();
		// ����ִ�гɹ��ı�־
		try {
			Document doc = Jsoup.parse(soundCode);
			Elements scriptElements = doc.getElementsByTag("script");
			int index = 0;
			for (int i = scriptElements.size() - 1; i >= 0; i--) {
				String str = scriptElements.get(i).toString();
				if (str.contains("WB_text W_f14")) {
					index = i;
					break;
				}
			}
			String jsonStrTemp = scriptElements.get(index).toString();
			String jsonStr = jsonStrTemp.substring(16, jsonStrTemp.length() - 10);
			JSONObject jsonObj = new JSONObject(jsonStr);
			String html = jsonObj.getString("html");
			doc = Jsoup.parse(html);
			Elements elements = null;
			// String title = doc.getElementsByClass("WB_text W_f14").text();
			elements = doc.getElementsByClass("face").first().getElementsByTag("a");
			String author = elements.attr("title");
			String authorLink = elements.attr("href");
			elements = doc.getElementsByClass("WB_row_line").first().getElementsByTag("li");
			String transpond = elements.get(1).getElementsByTag("em").get(1).text();
			String discuss = elements.get(2).getElementsByTag("em").get(1).text();
			String goodPress = elements.get(3).getElementsByTag("em").get(1).text();
			video.setAuthor(author);
			video.setAuthorLink(authorLink);
			video.setDiscuss(discuss);
			video.setGoodPress(goodPress);
			video.setTranspond(transpond);
			String mid = doc.getElementsByClass("WB_from S_txt2").first().getElementsByTag("a").first().attr("name");
			video.setMid(mid);
		} catch (Exception e) {
			LogUtil.LogInfo("΢����Ƶkey��������Ƶ��Ϣ�쳣" + e.getMessage());
		}
		return video;
	}

	/**
	 * �������۵�����
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
			String url = parseDiscussJson(doc, discussList, video);
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
			LogUtil.LogInfo("���������쳣" + e.getMessage());
		}
		return discussList;
	}

	/**
	 * ����discuss��json��
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
					// ��ȡ��������
					String comment = elementsTemp.get(0).ownText().substring(1);
					// ��ȡ��������
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
			LogUtil.LogInfo("�������۵�json���쳣" + e.getMessage());
		}
		return url;
	}

	/**
	 * ������Ƶת��ҳ��
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
			LogUtil.LogInfo("����ת��ҳ���쳣" + e.getMessage());
		}
		return transpondList;
	}

	/**
	 * ����transpond��json��
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
				// һ��������ת������
				elementsTemp = docTemp.getElementsByTag("a");
				oneChain = new ArrayList<Transpond>();
				int spaceCounter = 1;
				for (int i = 0; i < elementsTemp.size(); i++) {
					Transpond transpond = null;
					String name = null;
					String nameTemp = null;
					// ��һ���ڵ�û���ӽڵ�
					if (i == 0) {
						name = elementsTemp.get(0).text();
						// ��ǰת������
						transpond = new Transpond(name);
					}
					/*
					 * else if (i == elementsTemp.size()) { name = video.getAuthor(); // ��ǰת������
					 * transpond = new Transpond(name); // ��ʱ�����һ���ڵ㣬���ĸ��ڵ���������Լ� oneChain.get(i -
					 * spaceCounter).setParent(transpond); // ��ǰ�ڵ���ǰ��һ���ڵ�ĸ��ڵ� }
					 */
					else {
						nameTemp = elementsTemp.get(i).attr("usercard");
						if (nameTemp != null && !nameTemp.equals("")) {
							name = nameTemp.substring(5);
							// ���ڵ�
							// ��ǰת������
							transpond = new Transpond(name);
							oneChain.get(i - spaceCounter).setParent(transpond);
						}
					}
					if (name != null && !name.equals("")) {
						// ���ú�video�������ϵ
						transpond.setVideo(video);
						oneChain.add(transpond);
					} else {
						spaceCounter += 1;
					}
				}
				// ��һ��������ת��·������������
				trandspondList.add(oneChain.get(0));
			}
			elementsTemp = doc.getElementsByClass("page next S_txt1 S_line1");
			if (elementsTemp.size() != 0) {
				url = elementsTemp.first().getElementsByTag("span").first().attr("action-data");
			} else {
				url = "";
			}
		} catch (Exception e) {
			LogUtil.LogInfo("����ת����json���쳣" + e.getMessage());
		}
		return url;
	}

}
