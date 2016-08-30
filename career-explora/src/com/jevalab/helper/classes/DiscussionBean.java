package com.jevalab.helper.classes;

import java.io.Serializable;
import java.util.List;


public class DiscussionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1241173447436950226L;

	private String pictureUrl, title, author, snippet, webkey, authorImage,
			remainingSnippet;

	private String body;
	private String postDate;
	private String link;
	private long likes;
	private long shares;
	private boolean liked;
	private long comments;
	@Override
	public String toString() {
		return "DiscussionBean [pictureUrl=" + pictureUrl + ", title=" + title
				+ ", author=" + author + ", snippet=" + snippet + ", webkey="
				+ webkey + ", authorImage=" + authorImage
				+ ", remainingSnippet=" + remainingSnippet + ", body=" + body
				+ ", postDate=" + postDate + ", link=" + link + ", likes="
				+ likes + ", shares=" + shares + ", liked=" + liked + "]";
	}
	
	
	public long getComments() {
		return comments;
	}


	public void setComments(long comments) {
		this.comments = comments;
	}


	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getSnippet() {
		return snippet;
	}
	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}
	public String getWebkey() {
		return webkey;
	}
	public void setWebkey(String webkey) {
		this.webkey = webkey;
	}
	public String getAuthorImage() {
		return authorImage;
	}
	public void setAuthorImage(String authorImage) {
		this.authorImage = authorImage;
	}
	public String getRemainingSnippet() {
		return remainingSnippet;
	}
	public void setRemainingSnippet(String remainingSnippet) {
		this.remainingSnippet = remainingSnippet;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getPostDate() {
		return postDate;
	}
	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
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
	public boolean isLiked() {
		return liked;
	}
	public void setLiked(boolean liked) {
		this.liked = liked;
	}
	
	

}
