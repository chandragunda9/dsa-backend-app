package com.learning.dsa_backend_app.dto;

import java.io.Serializable;
import java.time.Instant;

public class S3ObjectDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String key;
	private long size;
	private Instant lastModified;

	public S3ObjectDTO(String key, long size, Instant lastModified) {
		super();
		this.key = key;
		this.size = size;
		this.lastModified = lastModified;
	}

	// Getters and setters
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Instant getLastModified() {
		return lastModified;
	}

	public void setLastModified(Instant lastModified) {
		this.lastModified = lastModified;
	}

	@Override
	public String toString() {
		return "S3ObjectDTO [key=" + key + ", size=" + size + ", lastModified=" + lastModified + "]";
	}

}
