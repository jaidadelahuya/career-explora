package com.jevalab.azure.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

public class Discussion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4381455774031590445L;
	private Key id, owner;
	private String title;
	private Text body;
	private String format;
	private List<Key> comments;
	private Date dateCreated;
	private List<String> tags;
	private BlobKey image;
	private long likes;
	private long shares;
	private Key unit;
	private Key collection;

	@Override
	public String toString() {
		return "Discussion [id=" + id + ", owner=" + owner + ", title=" + title
				+ ", body=" + body + ", format=" + format + ", comments="
				+ comments + ", dateCreated=" + dateCreated + ", tags=" + tags
				+ ", image=" + image + ", likes=" + likes + ", shares="
				+ shares + "]";
	}

	
	public Key getUnit() {
		return unit;
	}


	public void setUnit(Key unit) {
		this.unit = unit;
	}


	public Key getCollection() {
		return collection;
	}


	public void setCollection(Key collection) {
		this.collection = collection;
	}


	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}

	public Key getOwner() {
		return owner;
	}

	public void setOwner(Key owner) {
		this.owner = owner;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Text getBody() {
		return body;
	}

	public void setBody(Text body) {
		this.body = body;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public List<Key> getComments() {
		return comments;
	}

	public void setComments(List<Key> comments) {
		this.comments = comments;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public BlobKey getImage() {
		return image;
	}

	public void setImage(BlobKey image) {
		this.image = image;
	}

	public long getLikes() {
		return likes;
	}

	public void setLikes(long likes) {
		this.likes = likes;
	}

	public long getShares() {
		return shares;
	}

	public void setShares(long shares) {
		this.shares = shares;
	}

	public Discussion() {
		id = GeneralController.ds.allocateIds(Discussion.class.getSimpleName(),
				1).getStart();
	}

}