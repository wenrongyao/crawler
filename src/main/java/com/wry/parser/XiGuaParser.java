package com.wry.parser;

import java.util.List;

import javax.swing.JTextArea;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wry.model.Discuss;
import com.wry.model.VideoXiGua;

/**
 * 解析抖音源码
 * 
 * @author Administrator
 *
 */
public class XiGuaParser {

	/**
	 * 解析西瓜搜索标签
	 * 
	 * @param soundCode
	 * @return
	 */
	public boolean parserVideoList(String soundCode, List<VideoXiGua> videoList, JTextArea textAreaConsole) {
		try {
			JSONObject jsonObj = new JSONObject(soundCode);
			JSONArray array = jsonObj.getJSONArray("data");
			if (array.length() == 0) {
				return false;
			}
			String id_str = "";
			String play_effective_count = "";
			String datetime = "";
			VideoXiGua video = null;
			for (int i = 0; i < array.length(); i++) {
				jsonObj = new JSONObject(array.get(i).toString());
				id_str = jsonObj.get("id_str").toString();
				play_effective_count = jsonObj.getString("play_effective_count").toString();
				datetime = jsonObj.get("datetime").toString();
				video = new VideoXiGua();
				video.setId_str(id_str);
				video.setPlay_effective_count(play_effective_count);
				video.setDatetime(datetime);
				videoList.add(video);
				textAreaConsole.append(video.getId_str() + "\n");
			}
			return true;
		} catch (JSONException e) {
			// e.printStackTrace();
			return false;
		}

	}

	/**
	 * 解析好最终的视频信息
	 * 
	 * @param video
	 */
	public void parserVideoList(String soundCode, VideoXiGua video) {
		try {
			JSONObject jsonObj = new JSONObject(soundCode);
			String data = jsonObj.get("data").toString();
			jsonObj = new JSONObject(data);
			String media_name = jsonObj.get("media_name").toString();
			String video_duration = jsonObj.get("video_duration").toString();
			String video_like_count = jsonObj.get("video_like_count").toString();
			String title = jsonObj.get("title").toString();
			String comment_count = jsonObj.get("comment_count").toString();
			String url = jsonObj.get("url").toString();
			video.setComment_count(comment_count);
			video.setMedia_name(media_name);
			video.setTitle(title);
			video.setUrl(url);
			video.setVideo_duration(video_duration);
			video.setVideo_like_count(video_like_count);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 解析评论
	 * 
	 * @param soundCode
	 * @param discussList
	 * @return
	 */
	public boolean parserDisscussList(String soundCode, List<Discuss> discussList) {
		try {
			JSONObject jsonObj = new JSONObject(soundCode);
			JSONArray array = jsonObj.getJSONArray("data");
			if (array.length() == 0) {
				return false;
			}
			String text = "";
			String user_name = "";
			String comment = "";
			Discuss discuss = null;
			for (int i = 0; i < array.length(); i++) {
				jsonObj = new JSONObject(array.get(i).toString());
				comment = jsonObj.get("comment").toString();
				jsonObj = new JSONObject(comment);
				text = jsonObj.get("text").toString();
				user_name = jsonObj.get("user_name").toString();
				discuss = new Discuss();
				discuss.setAuthor(user_name);
				discuss.setComment(text);
				discussList.add(discuss);
			}
			return true;
		} catch (JSONException e) {
			return false;
		}
	}
}
