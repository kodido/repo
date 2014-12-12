package org.ideasforchange.imenno.logic;

import android.net.Uri;

public class Contact {

	private long id;
	private String name;
	private String lookupKey;
	private String thumbNailUri;

	public Contact(long id, String name, String lookupKey, String thumbNailUri) {
		this.id = id;
		this.name = name;
		this.lookupKey = lookupKey;
		this.thumbNailUri = thumbNailUri;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLookupKey() {
		return lookupKey;
	}

	public String getThumbNailUri() {
		return thumbNailUri;
	}
	
}
