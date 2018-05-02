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
@Table(name = "��Ƶ������Ϣ��΢����Ƶkey")
public class Video {
	// ����
	private Integer id;
	// ��Ƶ����
	private String title;
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
	// ��ƵԴ����
	private String soundUrl;

	@Column(columnDefinition = "��Ƶ����")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(columnDefinition = "���Ŵ���")
	public String getTimesOfPlay() {
		return timesOfPlay;
	}

	@Column(columnDefinition = "��Ƶ��������")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setTimesOfPlay(String timesOfPlay) {
		this.timesOfPlay = timesOfPlay;
	}

	// ��Ƶ���Ŵ���
	private String timesOfPlay;

	public Video() {
		super();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.collectionDate = sdf.format(date);
	}

	@Column(columnDefinition = "��Ƶ����")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(columnDefinition = "������ҳ����")
	public String getAuthorLink() {
		return authorLink;
	}

	public void setAuthorLink(String authorLink) {
		this.authorLink = authorLink;
	}

	@Column(columnDefinition = "��Ƶ����")
	public String getDiscuss() {
		return discuss;
	}

	public void setDiscuss(String discuss) {
		this.discuss = discuss;
	}

	@Column(columnDefinition = "��Ƶת��")
	public String getTranspond() {
		return transpond;
	}

	public void setTranspond(String transpond) {
		this.transpond = transpond;
	}

	@Column(columnDefinition = "��Ƶ������")
	public String getGoodPress() {
		return goodPress;
	}

	public void setGoodPress(String goodPress) {
		this.goodPress = goodPress;
	}

	@Column(columnDefinition = "��Ƶ�ɼ�����")
	public String getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "��Ƶ����")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(columnDefinition = "��Ƶ����")
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
	
	@Column(columnDefinition = "��ƵԴ����")
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
