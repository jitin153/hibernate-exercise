package com.jitin.hibernatebasics.dto;

import java.util.Date;

import javax.persistence.Embeddable;

@Embeddable
public class Post {
	private String title;
	private String post;
	private Date lastUpdated;

	public Post() {

	}

	public Post(String title, String post, Date lastUpdated) {
		this.title = title;
		this.post = post;
		this.lastUpdated = lastUpdated;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Override
	public String toString() {
		return "Post [title=" + title + ", post=" + post + ", lastUpdated=" + lastUpdated + "]";
	}
	
}
