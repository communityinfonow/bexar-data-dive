package org.cinow.omh.menu;

import java.util.List;

public interface Category<T extends Item> {
	
	long getId();
	void setId(long id);
	String getName_en();
	void setName_en(String name_en);
	String getName_es();
	void setName_es(String name_es);
	List<T> getItems();
	void setItems(List<T> items);
}
