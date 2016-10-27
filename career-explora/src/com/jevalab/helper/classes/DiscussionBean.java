/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.jevalab.helper.classes;

import java.io.Serializable;

public class DiscussionBean implements Serializable {
	private static final long serialVersionUID = 1241173447436950226L;
	private String pictureUrl;
	private String title;
	private String author;
	private String snippet;
	private String webkey;
	private String authorImage;
	private String remainingSnippet;
	private String commentCursor;
	private String body;
	private String postDate;
	private String link;
	private long likes;
	private long shares;
	private boolean liked;
	private long comments;

	public String toString() {
		return "DiscussionBean [pictureUrl=" + this.pictureUrl + ", title="
				+ this.title + ", author=" + this.author + ", snippet="
				+ this.snippet + ", webkey=" + this.webkey + ", authorImage="
				+ this.authorImage + ", remainingSnippet="
				+ this.remainingSnippet + ", body=" + this.body + ", postDate="
				+ this.postDate + ", link=" + this.link + ", likes="
				+ this.likes + ", shares=" + this.shares + ", liked="
				+ this.liked + "]";
	}

	public String getCommentCursor() {
		return this.commentCursor;
	}

	public void setCommentCursor(String commentCursor) {
		this.commentCursor = commentCursor;
	}

	public long getComments() {
		return this.comments;
	}

	public void setComments(long comments) {
		this.comments = comments;
	}

	public String getPictureUrl() {
		return this.pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSnippet() {
		return this.snippet;
	}

	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}

	public String getWebkey() {
		return this.webkey;
	}

	public void setWebkey(String webkey) {
		this.webkey = webkey;
	}

	public String getAuthorImage() {
		return this.authorImage;
	}

	public void setAuthorImage(String authorImage) {
		this.authorImage = authorImage;
	}

	public String getRemainingSnippet() {
		return this.remainingSnippet;
	}

	public void setRemainingSnippet(String remainingSnippet) {
		this.remainingSnippet = remainingSnippet;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getPostDate() {
		return this.postDate;
	}

	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
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

	public boolean isLiked() {
		return this.liked;
	}

	public void setLiked(boolean liked) {
		this.liked = liked;
	}
}