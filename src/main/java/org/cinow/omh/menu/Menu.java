package org.cinow.omh.menu;

import java.util.List;

public interface Menu<T extends Category> {
	List<T> getCategories();
	void setCategories(List<T> categories);
}
