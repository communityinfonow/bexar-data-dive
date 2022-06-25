package org.cinow.omh.menu;

import java.util.List;

public interface Category<T extends Item> {
	
	String getId();
	void setId(String id);
	String getName_en();
	void setName_en(String name_en);
	String getName_es();
	void setName_es(String name_es);
	List<T> getItems();
	void setItems(List<T> items);
}
