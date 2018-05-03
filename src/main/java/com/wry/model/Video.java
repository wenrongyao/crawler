package com.wry.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * С��Ƶ��
 * 
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */

@Entity
// �������ݿ����ƣ���Ψһ��Լ����������ֹ�ظ����
// @Table( uniqueConstraints = { @UniqueConstraint(columnNames = "title") })
public class Video {
	// ����
	private Integer id;
	// ��Ƶ����
	private String title;
	// ��ƵԴ����
	private String soundUrl;
	// ��Ƶ������
	private String author;
	// ����������
	private String authorLink;
	// ��Ƶ����
	private String discuss;
	// ��Ƶת��
	private String transpond;
	// ������
	private String goodPress;
	// �ɼ�����
	private String collectionDate;
	// ��Ƶ��mid������ƴ�����ӣ����ݿⲻ�ñ��棩
	private String mid;
	// ��Ƶ����
	private String url;
	// ��Ƶ����
	private String type;
	// ��Ƶ���Ŵ���
	private String timesOfPlay;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTimesOfPlay() {
		return timesOfPlay;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setTimesOfPlay(String timesOfPlay) {
		this.timesOfPlay = timesOfPlay;
	}

	public Video() {
		super();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.collectionDate = sdf.format(date);
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthorLink() {
		return authorLink;
	}

	public void setAuthorLink(String authorLink) {
		this.authorLink = authorLink;
	}

	public String getDiscuss() {
		return discuss;
	}

	public void setDiscuss(String discuss) {
		this.discuss = discuss;
	}

	public String getTranspond() {
		return transpond;
	}

	public void setTranspond(String transpond) {
		this.transpond = transpond;
	}

	public String getGoodPress() {
		return goodPress;
	}

	public void setGoodPress(String goodPress) {
		this.goodPress = goodPress;
	}

	public String getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Transient
	public String getSoundUrl() {
		return soundUrl;
	}

	public void setSoundUrl(String soundUrl) {
		this.soundUrl = soundUrl;
	}

	@Transient
	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	@Override
	public String toString() {
		return "Video [title=" + title + ", soundUrl=" + soundUrl + ", author=" + author
				+ ", authorLink=" + authorLink + ", discuss=" + discuss + ", transpond=" + transpond + ", goodPress="
				+ goodPress + ", collectionDate=" + collectionDate + "]";
	}

}
