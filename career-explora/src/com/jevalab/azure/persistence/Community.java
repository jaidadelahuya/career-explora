package com.jevalab.azure.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

public class Community implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -882116411253138185L;
	private Key id, owner;
	private Date dateCreated;
	private BlobKey image, backgroundImage;
	private Text shortDesc, longDesc;
	private boolean visible;
	private String joinMode, name;
	private List<Key> members, units;
	private List<String> tags;

	@Override
	public String toString() {
		return "Community [id=" + id + ", owner=" + owner + ", dateCreated="
				+ dateCreated + ", image=" + image + ", backgroundImage="
				+ backgroundImage + ", shortDesc=" + shortDesc + ", longDesc="
				+ longDesc + ", visible=" + visible + ", joinMode=" + joinMode
				+ ", name=" + name + ", members=" + members + ", units="
				+ units + ", tags=" + tags + "]";
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

	public String getJoinMode() {
		return joinMode;
	}

	public void setJoinMode(String joinMode) {
		this.joinMode = joinMode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Key> getMembers() {
		return members;
	}

	public void setMembers(List<Key> members) {
		this.members = members;
	}

	public List<Key> getUnits() {
		return units;
	}

	public void setUnits(List<Key> units) {
		this.units = units;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Community() {
		this.id = GeneralController.ds.allocateIds(
				Community.class.getSimpleName(), 1).getStart();
	}

}
