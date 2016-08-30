package com.jevalab.azure.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Link;
import com.google.appengine.api.datastore.Text;

public class Article implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6543815782172323770L;
	
	private Key key;
	private String title;
	private Date date;
	private long views;
	private long nComments;
	private Text body;
	private String category;
	private Key author;
	private List<Key> comments;
	private List<Key> subscribers;
	private BlobKey imageKey;
	private Link link;
	private Text video;
	private List<String> tags;
	private List<Key> likers;

	
	@Override
	public String toString() {
		return "Article [key=" + key + ", title=" + title + ", date=" + date
				+ ", views=" + views + ", nComments=" + nComments + ", body="
				+ body + ", category=" + category + ", tag=" 
				+ ", author=" + author + ", comments=" + comments
				+ ", subscribers=" + subscribers + ", imageKey=" + imageKey
				+ ", link=" + link + ", video=" + video + ", tags=" + tags
				+ "]";
	}
	
	

	
	public List<Key> getLikers() {
		return likers;
	}

	public void setLikers(List<Key> likers) {
		this.likers = likers;
	}




	public Link getLink() {
		return link;
	}


	public void setLink(Link link) {
		this.link = link;
	}


	public Text getVideo() {
		return video;
	}


	public void setVideo(Text video) {
		this.video = video;
	}


	public List<String> getTags() {
		return tags;
	}


	public void setTags(List<String> tags) {
		this.tags = tags;
	}


	public long getnComments() {
		return nComments;
	}

	public void setnComments(long nComments) {
		this.nComments = nComments;
	}

	public String getTitle() {
		
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getViews() {
		return views;
	}

	public void setViews(long views) {
		this.views = views;
	}

	public Text getBody() {
		return body;
	}

	public void setBody(Text body) {
		this.body = body;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	

	public Key getAuthor() {
		return author;
	}

	public void setAuthor(Key author) {
		this.author = author;
	}

	public List<Key> getComments() {
		return comments;
	}

	public void setComments(List<Key> comments) {
		this.comments = comments;
	}

	public List<Key> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(List<Key> subscribers) {
		this.subscribers = subscribers;
	}
	
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Article() {
		this.key = EMF.getDs().allocateIds(
				Article.class.getSimpleName(), 1).getStart();
	}

	public BlobKey getImageKey() {
		return imageKey;
	}

	public void setImageKey(BlobKey imageKey) {
		this.imageKey = imageKey;
	}

}
