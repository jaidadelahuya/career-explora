/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.jevalab.azure.persistence;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyRange;
import com.google.appengine.api.datastore.Text;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Discussion implements Serializable {
	private static final long serialVersionUID = 4381455774031590445L;
	private Key id;
	private Key owner;
	private String title;
	private String link;
	private Text body;
	private String format;
	private List<Key> subscribers;
	private List<Key> likers;
	private Date dateCreated;
	private List<String> tags;
	private BlobKey image;
	private long likes;
	private long comments;
	private long shares;
	private Key unit;
	private Key collection;
	private Key parent;

	public String toString() {
		return "Discussion [id=" + this.id + ", owner=" + this.owner
				+ ", title=" + this.title + ", body=" + this.body + ", format="
				+ this.format + ", subscribers=" + this.subscribers
				+ ", dateCreated=" + this.dateCreated + ", tags=" + this.tags
				+ ", image=" + this.image + ", likes=" + this.likes
				+ ", shares=" + this.shares + "]";
	}

	public long getComments() {
		return this.comments;
	}

	public void setComments(long comments) {
		this.comments = comments;
	}

	public Key getParent() {
		return this.parent;
	}

	public void setParent(Key parent) {
		this.parent = parent;
	}

	public List<Key> getLikers() {
		return this.likers;
	}

	public void setLikers(List<Key> likers) {
		this.likers = likers;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Key getUnit() {
		return this.unit;
	}

	public void setUnit(Key unit) {
		this.unit = unit;
	}

	public Key getCollection() {
		return this.collection;
	}

	public void setCollection(Key collection) {
		this.collection = collection;
	}

	public Key getId() {
		return this.id;
	}

	public void setId(Key id) {
		this.id = id;
	}

	public Key getOwner() {
		return this.owner;
	}

	public void setOwner(Key owner) {
		this.owner = owner;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Text getBody() {
		return this.body;
	}

	public void setBody(Text body) {
		this.body = body;
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public List<Key> getSubscribers() {
		return this.subscribers;
	}

	public void setSubscribers(List<Key> subscribers) {
		this.subscribers = subscribers;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public List<String> getTags() {
		return this.tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public BlobKey getImage() {
		return this.image;
	}

	public void setImage(BlobKey image) {
		this.image = image;
	}

	public long getLikes() {
		return this.likes;
	}

	public void setLikes(long likes) {
		this.likes = likes;
	}

	public long getShares() {
		return this.shares;
	}

	public void setShares(long shares) {
		this.shares = shares;
	}

	public Discussion() {
		this.id = GeneralController.ds.allocateIds(
				Discussion.class.getSimpleName(), 1L).getStart();
	}
}