package com.imagestore.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ImageFile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private byte[] file;

	private String mimeType;

	private Date dateUpload;

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	public ImageFile(String name, User owner, byte[] file, String mimeType) {
		this.name = name;
		this.user = owner;
		this.file = file;
		this.mimeType = mimeType;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public Date getDateUpload() {
		return dateUpload;
	}

	public void setDateUpload(Date dateUpload) {
		this.dateUpload = dateUpload;
	}

}
