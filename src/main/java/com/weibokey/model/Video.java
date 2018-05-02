package com.weibokey.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 小视频类
 * 
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */

@Entity
@Table(name = "视频基本信息—微博视频key")
public class Video {
	// 主键
	private Integer id;
	// 视频标题
	private String title;
	// 视频创作者
	private String author;
	// 创作者链接
	private String authorLink;
	// 视频讨论
	private String discuss;
	// 视频转发
	private String transpond;
	// 点赞数
	private String goodPress;
	// 采集日期
	private String collectionDate;
	// 视频的mid（用来拼凑链接，数据库不用保存）
	private String mid;
	// 视频链接
	private String url;
	// 视频类型
	private String type;
	// 视频源链接
	private String soundUrl;

	@Column(columnDefinition = "视频类型")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(columnDefinition = "播放次数")
	public String getTimesOfPlay() {
		return timesOfPlay;
	}

	@Column(columnDefinition = "视频播放链接")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setTimesOfPlay(String timesOfPlay) {
		this.timesOfPlay = timesOfPlay;
	}

	// 视频播放次数
	private String timesOfPlay;

	public Video() {
		super();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.collectionDate = sdf.format(date);
	}

	@Column(columnDefinition = "视频作者")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(columnDefinition = "作者主页链接")
	public String getAuthorLink() {
		return authorLink;
	}

	public void setAuthorLink(String authorLink) {
		this.authorLink = authorLink;
	}

	@Column(columnDefinition = "视频评论")
	public String getDiscuss() {
		return discuss;
	}

	public void setDiscuss(String discuss) {
		this.discuss = discuss;
	}

	@Column(columnDefinition = "视频转发")
	public String getTranspond() {
		return transpond;
	}

	public void setTranspond(String transpond) {
		this.transpond = transpond;
	}

	@Column(columnDefinition = "视频点赞数")
	public String getGoodPress() {
		return goodPress;
	}

	public void setGoodPress(String goodPress) {
		this.goodPress = goodPress;
	}

	@Column(columnDefinition = "视频采集日期")
	public String getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "视频主键")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(columnDefinition = "视频标题")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Transient
	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}
	
	@Column(columnDefinition = "视频源链接")
	public String getSoundUrl() {
		return soundUrl;
	}

	public void setSoundUrl(String soundUrl) {
		this.soundUrl = soundUrl;
	}

	@Override
	public String toString() {
		return "Video [title=" + title + ", author=" + author + ", authorLink=" + authorLink + ", discuss=" + discuss
				+ ", transpond=" + transpond + ", goodPress=" + goodPress + ", collectionDate=" + collectionDate
				+ ", mid=" + mid + ", url=" + url + ", type=" + type + ", timesOfPlay=" + timesOfPlay + "]";
	}
}
