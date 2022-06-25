package org.cinow.omh.filters;

public enum FilterTypes {
	RACE("1"),
	AGE("2"),
	SEX("3"),
	EDUCATION("4"),
	INCOME("5");

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private FilterTypes(String id) {
		this.id = id;
	}


}
