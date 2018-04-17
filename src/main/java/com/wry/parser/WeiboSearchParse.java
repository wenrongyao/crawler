package com.wry.parser;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.wry.model.Video;

/**
 * 微博搜索内容的解析类
 * 
 * @author Administrator
 *
 */
public class WeiboSearchParse {

	/**
	 * 解析搜索后的小视频，得到源链接。
	 * 
	 * @param soundCode
	 * @return
	 */
	public List<Video> parseVideos(String soundCode) {
		List<Video> videoList = new LinkedList<Video>();
		if (soundCode != null && !soundCode.equals("")) {
			Document doc = Jsoup.parse(soundCode);
			Elements elements = doc.getElementsByClass("WB_cardwrap S_bg2 clearfix");
			Video video = null;
			for (Element element : elements) {
				video = new Video();
				// 标题
				String title = element.getElementsByClass("comment_txt").text();
				video.setTitle(title);
				// 源链接
				String soundUrl = element.getElementsByClass("feed_from W_textb").get(0).getElementsByTag("a").get(0)
						.attr("href");
				video.setSoundUrl("https:" + soundUrl);
				videoList.add(video);
			}
		}

		return videoList;
	}

	/**
	 * 解析小视频
	 * 
	 * @param soundCode
	 * @param video
	 */
	public void parseVideo(String soundCode, Video video) {
		// 程序执行成功的标志
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
			// video.setTitle(title);
			String mid = doc.getElementsByClass("WB_from S_txt2").first().getElementsByTag("a").first().attr("name");
			video.setMid(mid);
		} catch (Exception e) {
		}
	}

}
