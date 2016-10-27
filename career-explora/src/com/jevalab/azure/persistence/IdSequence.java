/**
 * 
 */
package com.jevalab.azure.persistence;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.datanucleus.api.jpa.annotations.Extension;



/**
 * @author JAIDA DE LAHUYA
 *
 */
@Entity
public class IdSequence implements Serializable {

	@Id
	private long id =1;
	
	@Basic
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private long value;

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "IdSequence [value=" + value + "]";
	}
	
	
}
