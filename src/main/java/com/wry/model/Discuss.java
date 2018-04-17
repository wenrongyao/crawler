package com.wry.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * ����ʵ�壬��video�Ƕ��һ�Ĺ�ϵ���ɼ����е�һ�����ۡ�
 * 
 * @author Administrator
 *
 */
@Entity
public class Discuss {
	// ����
	private Integer id;
	// ������
	private String author;
	// ��������
	private String comment;
	// ��Ӧ��Ƶ��id�������
	private Video video;
	// ��Ӧ��Ƶ��id�������
	private VideoXiGua videoXigua;
	// �ɼ�����
	private String collectionDate;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@JoinColumn(columnDefinition = "text")
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@ManyToOne(cascade = { CascadeType.ALL })
	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	@ManyToOne(cascade = { CascadeType.ALL })
	public VideoXiGua getVideoXigua() {
		return videoXigua;
	}

	public void setVideoXigua(VideoXiGua videoXigua) {
		this.videoXigua = videoXigua;
	}

	public String getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}

	public Discuss() {
		super();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.collectionDate = sdf.format(date);
	}

	@Override
	public String toString() {
		return "Discuss [author=" + author + ", comment=" + comment + ", video=" + video + ", videoXigua=" + videoXigua
				+ ", collectionDate=" + collectionDate + "]";
	}

}
