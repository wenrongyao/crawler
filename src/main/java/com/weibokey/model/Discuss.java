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
 * 评论实体，与video是多对一的关系，采集所有的一级评论。
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name="评论—微博视频key")
public class Discuss {
	// 主键
	private Integer id;
	// 评论人
	private String author;
	// 评论内容
	private String comment;
	// 对应视频的id（外键）
	private Video video;
	// 采集日期
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
