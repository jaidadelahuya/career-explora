package com.jevalab.azure.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.Key;

public class Comment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -474288104310177155L;
	
	private Key id;
	private Key author;
	private Key discussion;
	private Date dateCreated;
	private long likes;
	private List<Key> comments;
	public Comment() {
		id = GeneralController.ds.allocateIds(Comment.class.getSimpleName(), 1).getStart();
	}
	@Override
	public String toString() {
		return "Comment [id=" + id + ", author=" + author + ", dateCreated="
				+ dateCreated + ", likes=" + likes + ", comments=" + comments
				+ "]";
	}
	
	
	public Key getDiscussion() {
		return discussion;
	}
	public void setDiscussion(Key discussion) {
		this.discussion = discussion;
	}
	public Key getId() {
		return id;
	}
	public void setId(Key id) {
		this.id = id;
	}
	public Key getAuthor() {
		return author;
	}
	public void setAuthor(Key author) {
		this.author = author;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public long getLikes() {
		return likes;
	}
	public void setLikes(long likes) {
		this.likes = likes;
	}
	public List<Key> getComments() {
		return comments;
	}
	public void setComments(List<Key> comments) {
		this.comments = comments;
	}
	
	
	

}
