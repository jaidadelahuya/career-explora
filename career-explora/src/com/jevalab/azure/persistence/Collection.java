package com.jevalab.azure.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

public class Collection implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6761797625803944003L;

	private Key id, owner;
	private Date dateCreated;
	private BlobKey image, backgroundImage;
	private Text shortDesc, longDesc;
	private boolean visible;
	private String name;
	private List<Key> followers, discussions;
	private List<String> tags;

	@Override
	public String toString() {
		return "Collection [id=" + id + ", owner=" + owner + ", dateCreated="
				+ dateCreated + ", image=" + image + ", backgroundImage="
				+ backgroundImage + ", shortDesc=" + shortDesc + ", longDesc="
				+ longDesc + ", visible=" + visible + ", name=" + name
				+ ", followers=" + followers + ", discussions=" + discussions
				+ ", tags=" + tags + "]";
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

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public BlobKey getImage() {
		return image;
	}

	public void setImage(BlobKey image) {
		this.image = image;
	}

	public BlobKey getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(BlobKey backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public Text getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(Text shortDesc) {
		this.shortDesc = shortDesc;
	}

	public Text getLongDesc() {
		return longDesc;
	}

	public void setLongDesc(Text longDesc) {
		this.longDesc = longDesc;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Key> getFollowers() {
		return followers;
	}

	public void setFollowers(List<Key> followers) {
		this.followers = followers;
	}

	public List<Key> getDiscussions() {
		return discussions;
	}

	public void setDiscussions(List<Key> discussions) {
		this.discussions = discussions;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Collection() {
		this.id = GeneralController.ds.allocateIds(
				Collection.class.getSimpleName(), 1).getStart();
	}

}
