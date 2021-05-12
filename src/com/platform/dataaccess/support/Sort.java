package com.platform.dataaccess.support;

public class Sort {

	private final String sortName;
	private final String sortOrder;

	public Sort(String sortName) {
		this.sortName = sortName;
		this.sortOrder = null;
	}

	public Sort(String sortName, String sortOrder) {
		this.sortName = sortName;
		this.sortOrder = sortOrder;
	}

	public String getSortName() {
		return sortName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

}
