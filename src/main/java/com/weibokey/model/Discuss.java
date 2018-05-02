package com.weibokey.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ����ʵ�壬��video�Ƕ��һ�Ĺ�ϵ���ɼ����е�һ�����ۡ�
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name="���ۡ�΢����Ƶkey")
public class Discuss {
	// ����
	private Integer id;
	// ������
	private String author;
	// ��������
	private String comment;
	// ��Ӧ��Ƶ��id�������
	private Video video;
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
		return "Discuss [author=" + author + ", comment=" + comment + ", video=" + video + ", collectionDate="
				+ collectionDate + "]";
	}

	

}
