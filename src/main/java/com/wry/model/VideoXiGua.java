package com.wry.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * ������Ƶ����Ϣ
 * 
 * @author Administrator
 *
 */
@Entity
public class VideoXiGua {
	// ����
	private Integer id;
	// �ɼ�����
	private String collectionDate;
	// ��Ƶ��id�� ���ڲ鿴��Ƶ�ľ�����Ϣʱ�á�
	private String id_str;
	// ��Ч���Ŵ���
	private String play_effective_count;
	// ��Ƶ����ʱ��
	private String datetime;
	// ��Ƶ����
	private String media_name;
	// ��Ƶʱ��
	private String video_duration;
	// ������
//	private String video_like_count;
	// ����
	private String title;
	// ����
	private String comment_count;
	// ��Ƶ�Ĳ�������
	private String url;

	public String getMedia_name() {
		return media_name;
	}

	public void setMedia_name(String media_name) {
		this.media_name = media_name;
	}

	public String getVideo_duration() {
		return video_duration;
	}

	public void setVideo_duration(String video_duration) {
		this.video_duration = video_duration;
	}

//	public String getVideo_like_count() {
//		return video_like_count;
//	}
//
//	public void setVideo_like_count(String video_like_count) {
//		this.video_like_count = video_like_count;
//	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getComment_count() {
		return comment_count;
	}

	public void setComment_count(String comment_count) {
		this.comment_count = comment_count;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPlay_effective_count() {
		return play_effective_count;
	}

	public void setPlay_effective_count(String play_effective_count) {
		this.play_effective_count = play_effective_count;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getId_str() {
		return id_str;
	}

	public void setId_str(String id_str) {
		this.id_str = id_str;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}

	public VideoXiGua() {
		super();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.collectionDate = sdf.format(date);
	}

	@Override
	public String toString() {
		return "VideoXiGua [id_str=" + id_str + ", play_effective_count=" + play_effective_count + ", datetime="
				+ datetime + ", media_name=" + media_name + ", video_duration=" + video_duration + ", title=" + title + ", comment_count=" + comment_count + ", url=" + url + "]";
	}

}
