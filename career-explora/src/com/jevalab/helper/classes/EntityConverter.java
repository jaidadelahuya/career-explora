package com.jevalab.helper.classes;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Link;
import com.google.appengine.api.datastore.Text;
import com.jevalab.azure.people.Following;
import com.jevalab.azure.people.Friends;
import com.jevalab.azure.persistence.Article;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.Collection;
import com.jevalab.azure.persistence.Comment;
import com.jevalab.azure.persistence.Community;
import com.jevalab.azure.persistence.Discussion;
import com.jevalab.azure.persistence.Unit;

public class EntityConverter {
	
	public static Entity FriendsToEntity (Following f) {
		Entity e = null;
		if(f.getId()==null) {
			e = new Entity(Following.class.getSimpleName());
		}else {
			e = new Entity(f.getId());
		}
		e.setIndexedProperty("following", f.getFollowing());
		e.setIndexedProperty("owner", f.getOwner());
		return e;
	}
	
	public static Entity FollowingToEntity (Friends f) {
		Entity e = null;
		if(f.getId()==null) {
			e = new Entity(Friends.class.getSimpleName());
		}else {
			e = new Entity(f.getId());
		}
		e.setIndexedProperty("friends", f.getFriends());
		return e;
	}
	
	public static Following entityToFollowing (Entity e) {
		Following f = new Following();
		f.setId(e.getKey());
		f.setFollowing((Key) e.getProperty("following"));
		f.setOwner((Key) e.getProperty("owner"));
		return f;
	}
	
	public static Friends entityToFriends (Entity e) {
		Friends f = new Friends();
		f.setId(e.getKey());
		f.setFriends((List<Key>) e.getProperty("friends"));
		return f;
	}
	
	public static AzureUser entityToUser(Entity e) {
		AzureUser u = new AzureUser();
		u.setUserID(e.getKey().getName());
		u.setAuthorized((boolean) e.getProperty("authorized"));
		u.setCountry((String) e.getProperty("Country"));
		u.setCover((String) e.getProperty("Cover"));
		if(e.getProperty("DateOfBirth") instanceof Date) {
			u.setDateOfBirth((Date) e.getProperty("DateOfBirth"));
		}
		u.setEmail((String) e.getProperty("EMail"));
		u.setFirstName((String) e.getProperty("FirstName"));
		u.setGender((String) e.getProperty("Gender"));
		u.setLastName((String) e.getProperty("LastName"));
		u.setLastPasswordChangeDate((Date) e.getProperty("LastPasswordChangeDate"));
		u.setLastSeenDate((Date) e.getProperty("LastSeenDate"));
		u.setLastTestTaken((String) e.getProperty("LastTestTaken"));
		u.setMiddleName((String) e.getProperty("MiddleName"));
		u.setMobile((String) e.getProperty("Mobile"));
		u.setOldPasswords((List<String>) e.getProperty("OldPasswords"));
		u.setPassword((String) e.getProperty("Password"));
		List<String> pids = (List<String>) e.getProperty("PasswordRecoveryIds");
		Set<String> sps = new HashSet<>();
		sps.addAll(pids);
		u.setPasswordRecoveryIds(sps);
		u.setPicture((String) e.getProperty("Picture"));
		u.setSchool((String) e.getProperty("School"));
		u.setState((String) e.getProperty("State"));
		u.setSubscriptionDate((Date) e.getProperty("SubscriptionDate"));
		u.setsClass((String) e.getProperty("Class"));
		u.setAreaOfInterest((List<String>) e.getProperty("AreaOfInterest"));
		Object o = e.getProperty("TakenTalentTest");
		if(o!=null) {
			u.setTakenTalentTest((boolean)o );
		}
		
		u.setUsername((String) e.getProperty("Username"));
		u.setUserPicturesIds((Set<String>) e.getProperty("UserPicturesIds"));
		u.setValidity((String) e.getProperty("Validity"));
		u.setAbout((Text) e.getProperty("About"));
		u.setHobbies((Text) e.getProperty("Hobbies"));
		
		return u;
	}
	
	public static Entity userToEntity(AzureUser user) {
		
		Entity e = new Entity(AzureUser.class.getSimpleName(), user.getUserID());
		e.setProperty(StringConstants.cFirstName, user.getFirstName());
		e.setProperty(StringConstants.cLastName, user.getLastName());
		e.setProperty(StringConstants.cMiddleName, user.getMiddleName());
		e.setUnindexedProperty(StringConstants.cGender, user.getGender());
		e.setProperty(StringConstants.cEMail, user.getEmail());
		e.setProperty(StringConstants.cState, user.getState());
		e.setProperty(StringConstants.cCountry, user.getCountry());
		e.setProperty(StringConstants.cSchool, user.getSchool());
		e.setUnindexedProperty(StringConstants.cLastTestTaken,
				user.getLastTestTaken());
		e.setUnindexedProperty(StringConstants.cDateOfBirth,
				user.getDateOfBirth());
		e.setUnindexedProperty(StringConstants.cLastSeenDate,
				user.getLastSeenDate());
		e.setUnindexedProperty(StringConstants.cAuthorized, user.isAuthorized());
		
		e.setUnindexedProperty(StringConstants.cSubscriptionDate,
				user.getSubscriptionDate());
		e.setUnindexedProperty(StringConstants.cValidity, user.getValidity());
		e.setUnindexedProperty(StringConstants.cPicture, user.getPicture());
		e.setUnindexedProperty(StringConstants.cCover, user.getCover());
		e.setUnindexedProperty(StringConstants.cFriends, user.getFriendsId());
		e.setProperty(StringConstants.cUsername, user.getUsername());
		e.setProperty(StringConstants.cMobile, user.getMobile());
		e.setUnindexedProperty(StringConstants.cPassword, user.getPassword());
		e.setProperty(StringConstants.cPasswordRecoveryIds,
				user.getPasswordRecoveryIds());
		e.setUnindexedProperty(StringConstants.cLastPasswordChangeDate, user.getLastPasswordChangeDate());
		e.setUnindexedProperty(StringConstants.cOldPasswords, user.getOldPasswords());
		e.setUnindexedProperty(StringConstants.cUserPicturesIds,user.getUserPicturesIds() );
		e.setIndexedProperty("Class", user.getsClass());
		e.setIndexedProperty("AreaOfInterest", user.getAreaOfInterest());
		e.setUnindexedProperty("About", user.getAbout());
		e.setUnindexedProperty("Hobbies", user.getHobbies());
		return e;
	}
	
	public static Entity commentToEntity(Comment c) {
		Entity e = new Entity(c.getId());
		e.setIndexedProperty("author", c.getAuthor());
		e.setIndexedProperty("discussion", c.getDiscussion());
		e.setUnindexedProperty("comments", c.getComments());
		e.setIndexedProperty("dateCreated", c.getDateCreated());
		e.setUnindexedProperty("likes",c.getLikes());
		return e;
	}
	
	public static Comment entityToComment(Entity e) {
		Comment c = new Comment();
		c.setId(e.getKey());
		c.setAuthor((Key) e.getProperty("author"));
		c.setComments((List<Key>) e.getProperty("comments"));
		c.setDateCreated((Date) e.getProperty("dateCreated"));
		c.setLikes((long) e.getProperty("likes"));
		c.setDiscussion((Key) e.getProperty("discussion"));
		return c;
	}
	
	public static Collection entityToCollection(Entity e) {
		Collection c = new Collection();
		c.setId(e.getKey());
		c.setBackgroundImage((BlobKey) e.getProperty("backgroundImage"));
		c.setDateCreated((Date) e.getProperty("dateCreated"));
		c.setImage((BlobKey) e.getProperty("image"));
		c.setLongDesc((Text) e.getProperty("longDesc"));
		c.setName((String) e.getProperty("name"));
		c.setOwner((Key) e.getProperty("owner"));
		c.setShortDesc((Text) e.getProperty("shortDesc"));
		c.setTags((List<String>) e.getProperty("tags"));
		c.setVisible((boolean) e.getProperty("visible"));
		c.setDiscussions((List<Key>) e.getProperty("discussion"));
		return c;
	}
	
	public static Entity collectionToEntity(Collection c) {
		Entity e = new Entity(c.getId());
		e.setIndexedProperty("dateCreated", c.getDateCreated());
		e.setIndexedProperty("visible", c.isVisible());
		e.setUnindexedProperty("owner", c.getOwner());
		e.setUnindexedProperty("image", c.getImage());
		e.setUnindexedProperty("backgroundImage", c.getBackgroundImage());
		e.setUnindexedProperty("shortDesc", c.getShortDesc());
		e.setUnindexedProperty("longDesc", c.getLongDesc());
		e.setUnindexedProperty("tags", c.getTags());
		e.setUnindexedProperty("name", c.getName());
		e.setUnindexedProperty("discussion", c.getDiscussions());
		return e;
	}
	
	public static Unit entityToUnit(Entity e) {
		Unit u = new Unit();
		u.setName((String) e.getProperty("name"));
		u.setId(e.getKey());
		u.setCommunity((Key) e.getProperty("community"));
		u.setDiscussions((List<Key>) e.getProperty("discussion"));
		return u;
		
	}
	
	public static Entity unitToEntity(Unit u) {
		Entity e = new Entity(u.getId());
		e.setUnindexedProperty("community", u.getCommunity());
		e.setUnindexedProperty("discussion", u.getDiscussions());
		e.setUnindexedProperty("name", u.getName());
		return e;
	}
	
	public static Entity discussionToEntity (Discussion d) {
		Entity e = new Entity(d.getId());
		e.setIndexedProperty("format", d.getFormat());
		e.setIndexedProperty("dateCreated", d.getDateCreated());
		e.setIndexedProperty("likes", d.getLikes());
		e.setIndexedProperty("shares", d.getShares());
		e.setUnindexedProperty("body", d.getBody());
		e.setIndexedProperty("subscribers", d.getSubscribers());
		e.setUnindexedProperty("image", d.getImage());
		e.setIndexedProperty("owner", d.getOwner());
		e.setIndexedProperty("tags", d.getTags());
		e.setIndexedProperty("title", d.getTitle());
		e.setIndexedProperty("collection", d.getCollection());
		e.setIndexedProperty("unit", d.getUnit());
		e.setUnindexedProperty("link", d.getLink());
		e.setUnindexedProperty("likers", d.getLikers());
		e.setIndexedProperty("parent", d.getParent());
		return e;
	}
	
	public static Discussion entityToDiscussion (Entity e) {
		Discussion d = new Discussion();
		d.setId(e.getKey());
		d.setBody((Text) e.getProperty("body"));
		d.setSubscribers((List<Key>) e.getProperty("subscribers"));
		d.setDateCreated((Date) e.getProperty("dateCreated"));
		d.setFormat((String) e.getProperty("format"));
		d.setImage((BlobKey) e.getProperty("image"));
		d.setLikes((long) e.getProperty("likes"));
		d.setOwner((Key) e.getProperty("owner"));
		d.setShares((long) e.getProperty("shares"));
		d.setTags((List<String>) e.getProperty("tags"));
		d.setTitle((String) e.getProperty("title"));
		d.setUnit((Key) e.getProperty("unit"));
		d.setCollection((Key) e.getProperty("collection"));
		d.setLink((String) e.getProperty("link"));
		d.setLikers((List<Key>) e.getProperty("likers"));
		d.setParent((Key) e.getProperty("parent"));
		return d;
	}
	
	public static Entity communityToEntity(Community c) {
		Entity e = new Entity(c.getId());
		e.setIndexedProperty("dateCreated", c.getDateCreated());
		e.setIndexedProperty("visible", c.isVisible());
		e.setUnindexedProperty("owner", c.getOwner());
		e.setUnindexedProperty("image", c.getImage());
		e.setUnindexedProperty("backgroundImage", c.getBackgroundImage());
		e.setUnindexedProperty("shortDesc", c.getShortDesc());
		e.setUnindexedProperty("longDesc", c.getLongDesc());
		e.setUnindexedProperty("joinMode", c.getJoinMode());
		e.setUnindexedProperty("members", c.getMembers());
		e.setUnindexedProperty("units", c.getUnits());
		e.setUnindexedProperty("tags", c.getTags());
		e.setUnindexedProperty("name", c.getName());
		return e;
	}
	
	public static Community entityToCommunity(Entity e) {
		Community c = new Community();
		c.setId(e.getKey());
		c.setBackgroundImage((BlobKey) e.getProperty("backgroundImage"));
		c.setDateCreated((Date) e.getProperty("dateCreated"));
		c.setImage((BlobKey) e.getProperty("image"));
		c.setJoinMode((String) e.getProperty("joinMode"));
		c.setLongDesc((Text) e.getProperty("longDesc"));
		c.setMembers((List<Key>) e.getProperty("members"));
		c.setName((String) e.getProperty("name"));
		c.setOwner((Key) e.getProperty("owner"));
		c.setShortDesc((Text) e.getProperty("shortDesc"));
		c.setTags((List<String>) e.getProperty("tags"));
		c.setUnits((List<Key>) e.getProperty("units"));
		c.setVisible((boolean) e.getProperty("visible"));
		return c;
	}

	public static Article entityToArticle(Entity e) {
		Article a = new Article();
		a.setKey(e.getKey());
		a.setAuthor((Key) e.getProperty("author"));
		a.setBody((Text) e.getProperty("body"));
		a.setComments((List<Key>) e.getProperty("comments"));
		a.setDate((Date) e.getProperty("date"));
		a.setSubscribers((List<Key>) e.getProperty("subscribers"));
		a.setTitle((String) e.getProperty("title"));
		a.setViews((Long) e.getProperty("views"));
		a.setImageKey((BlobKey) e.getProperty("imageKey"));
		a.setLink((Link) e.getProperty("link"));
		a.setTags((List<String>) e.getProperty("tags"));
		a.setVideo((Text) e.getProperty("video"));
		Object o = e.getProperty("nComments");
		if (o != null) {

			a.setnComments((Long) e.getProperty("nComments"));
		}
		return a;
	}

	public static Entity ArticleToEntity(Article a) {
		Entity e = new Entity(a.getKey());
		e.setIndexedProperty("title", a.getTitle());
		e.setIndexedProperty("date", a.getDate());
		e.setIndexedProperty("views", a.getViews());
		e.setUnindexedProperty("body", a.getBody());
		e.setIndexedProperty("author", a.getAuthor());
		e.setIndexedProperty("category", a.getCategory());
		e.setUnindexedProperty("comments", a.getComments());
		e.setUnindexedProperty("subscribers", a.getSubscribers());
		e.setUnindexedProperty("imageKey", a.getImageKey());
		e.setIndexedProperty("nComment", a.getnComments());
		e.setUnindexedProperty("link", a.getLink());
		e.setIndexedProperty("tags", a.getTags());
		e.setUnindexedProperty("video", a.getVideo());
		return e;
	}
}
