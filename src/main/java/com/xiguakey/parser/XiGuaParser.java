package com.xiguakey.parser;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.common.util.LogUtil;
import com.xiguakey.model.Discuss;
import com.xiguakey.model.Video;

/**
 * 解析西瓜视频
 * 
 * @author Administrator
 *
 */
public class XiGuaParser {

	/**
	 * 解析西瓜搜索标签，得到视频部分信息
	 * 
	 * @param soundCode
	 * @param videoList2 
	 * @return
	 */
	public boolean parserVideoList(String soundCode, List<Video> videoList) {
		try {
			JSONObject jsonObj = new JSONObject(soundCode);
			JSONArray array = jsonObj.getJSONArray("data");
			if(array.length() == 0) {
				return false;
			}
			String id_str = "";
			String play_effective_count = "";
			String datetime = "";
			Video video = null;
			for (int i = 0; i < array.length(); i++) {
				jsonObj = new JSONObject(array.get(i).toString());
				id_str = jsonObj.get("id_str").toString();
				play_effective_count = jsonObj.getString("play_effective_count").toString();
				datetime = jsonObj.get("datetime").toString();
				video = new Video();
				video.setId_str(id_str);
				video.setPlay_effective_count(play_effective_count);
				video.setDatetime(datetime);
				videoList.add(video);
			}
			return true;
		} catch (JSONException e) {
			LogUtil.LogInfo("西瓜视频，解析视频标签出错" + e.getMessage());
			return false;
		}

	}

	/**
	 * 解析视频，获取视频全部信息。
	 * 
	 * @param video
	 */
	public void parserVideo(String soundCode, Video video) {
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
			LogUtil.LogInfo("西瓜视频，解析视频信息" + e.getMessage());
		}
	}

	/**
	 * 解析评论信息
	 * 
	 * @param soundCode
	 * @param video
	 * @param discussList 
	 * @return
	 */
	public boolean parserDisscussList(String soundCode, Video video, List<Discuss> discussList) {
		try {
			JSONObject jsonObj = new JSONObject(soundCode);
			JSONArray array = jsonObj.getJSONArray("data");
			if(array.length() == 0){
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
				discuss.setVideo(video);
				discussList.add(discuss);
			}
			return true;
		} catch (JSONException e) {
			LogUtil.LogInfo("西瓜视频，解析评论出错" + e.getMessage());
			return false;
		}
	}

}
