package com.wry.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * 微博转发路径
 * 
 * @author Administrator
 *
 */
@Entity
public class Transpond {
	// 主键
	private Integer id;
	// 转发人的昵称
	private String name;
	// 对应视频的id,外键, 多对一
	private Video video;
	// 父节点，多个孩子，自身一对多
	private Set<Transpond> children;
	// 子节点，对应一个父节点，自身多对一
	private Transpond parent;
	// 采集日期
	private String collectionDate;

	/**
	 * 构造方法
	 * 
	 * @param name
	 */
	public Transpond(String name) {
		super();
		this.name = name;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.collectionDate = sdf.format(date);
	}

	/**
	 * 空的构造函数
	 */
	public Transpond() {
	}

	public String getName() {
		return name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	@OneToMany(mappedBy = "parent")
	public Set<Transpond> getChildren() {
		return children;
	}

	public void setChildren(Set<Transpond> children) {
		this.children = children;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "parent_id")
	public Transpond getParent() {
		return parent;
	}

	public void setParent(Transpond parent) {
		this.parent = parent;
	}

	public String getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}

	@Override
	public String toString() {
		return "Transpond [name=" + name + ", video=" + video + ", children=" + children + ", parent="
				+ parent + "]";
	}

}
