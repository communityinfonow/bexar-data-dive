package org.cinow.omh.filters;

public enum FilterTypes {
	RACE(1),
	AGE(2),
	SEX(3),
	EDUCATION(4),
	INCOME(5);

	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	private FilterTypes(long id) {
		this.id = id;
	}


}
