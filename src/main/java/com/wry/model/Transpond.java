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
 * ΢��ת��·��
 * 
 * @author Administrator
 *
 */
@Entity
public class Transpond {
	// ����
	private Integer id;
	// ת���˵��ǳ�
	private String name;
	// ��Ӧ��Ƶ��id,���, ���һ
	private Video video;
	// ���ڵ㣬������ӣ�����һ�Զ�
	private Set<Transpond> children;
	// �ӽڵ㣬��Ӧһ�����ڵ㣬������һ
	private Transpond parent;
	// �ɼ�����
	private String collectionDate;

	/**
	 * ���췽��
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
	 * �յĹ��캯��
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
