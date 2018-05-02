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
 * ����Դ��
 * 
 * @author Administrator
 *
 */
public class WeiboAllParser {
	/**
	 * ��ȡ��������
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
			LogUtil.LogInfo("title���ӽ����쳣" + e.getMessage());
		}

		return titleMap;
	}

	// С��Ƶ���Ӵ��� ---��ʼ

	/**
	 * ��ȡС��Ƶ����
	 * 
	 * @param soundCode
	 *            Դ��
	 * 
	 * @param type
	 *            ��Ƶ����
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
						// ajax���������
						String ajaxUrl = "https://weibo.com/p/aj/v6/mblog/videolist?type=" + type + "&page=" + page
								+ "&end_id=" + end_id + "&__rnd=1511396483798";
						end_id = downloadFile(videoLinksMap, ajaxUrl);
						page++;
					}
				}
			}
		} catch (Exception e) {
			LogUtil.LogInfo("С��Ƶ���ӽ����쳣" + e.getMessage());
		}
		return videoLinksMap;
	}

	/**
	 * ����С��Ƶ��ajax����
	 * 
	 * @param linkMap
	 *            �洢С��Ƶ���ӵ�map
	 * @param ajaxUrl
	 *            ajax���������
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
			LogUtil.LogInfo("С��Ƶ��ajax���������쳣" + e.getMessage());
		}
		return end_id;
	}

	// С��Ƶ���Ӵ��� ---С��Ƶ���Ӵ������

	// С��Ƶ���ݴ��� ---С��Ƶ���Ӵ���ʼ
	/**
	 * ����С��Ƶҳ��
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
			// С��Ƶ����
			elements = doc.getElementsByClass("info_txt W_f14");
			video.setTitle(elements.get(0).text().trim());
			// ����
			elements = doc.getElementsByClass("W_f14 L_autocut bot_name W_fl");
			video.setAuthor(elements.get(0).text().trim());
			// ��������
			elements = doc.getElementsByClass("bot_m W_fl clearfix");
			for (Element element : elements) {
				docTemp = Jsoup.parse(element.html());
				elements = docTemp.getElementsByTag("a");
				video.setAuthorLink("https:" + elements.get(0).attr("href"));
			}
			// ���Ŵ���
			elements = doc.getElementsByClass("bot_number W_fr W_f14");
			docTemp = Jsoup.parse(elements.html());
			elements = docTemp.getElementsByTag("em");
			String str = elements.get(1).text().trim();
			int end = str.indexOf("��");
			video.setTimesOfPlay(str.substring(0, end).trim().replace("��", "0000"));
			// ת������
			elements = doc.getElementsByClass("pos");
			docTemp = Jsoup.parse(elements.html());
			elements = docTemp.getElementsByTag("em");
			str = elements.get(1).text().trim();
			if (!str.matches("\\d+")) {
				str = 0 + "";
			}
			video.setTranspond(str);
			// ����
			str = elements.get(3).text().trim();
			if (!str.matches("\\d+")) {
				str = 0 + "";
			}
			video.setDiscuss(str);
			// ����
			str = elements.get(5).text().trim();
			if (!str.matches("\\d+")) {
				str = 0 + "";
			}
			video.setGoodPress(str);
			// ��ȡ��Ƶ��mid
			elements = doc.getElementsByClass("WB_row_line WB_row_r3 clearfix S_line2");
			docTemp = Jsoup.parse(elements.html());
			elements = docTemp.getElementsByTag("li").get(0).getElementsByTag("a");
			str = elements.attr("action-data");
			int start = str.indexOf("mid=") + 4;
			end = start + 16;
			video.setMid(str.substring(start, end));
		} catch (Exception e) {
			LogUtil.LogInfo("С��Ƶ���ݽ����쳣" + e.getMessage());
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
	// С��Ƶ���ݴ��� ---С��Ƶ���Ӵ������
}
