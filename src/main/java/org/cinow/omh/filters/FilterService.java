package org.cinow.omh.filters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilterService {
	
	@Autowired
	private FilterRepository filterRepository;

	public List<Filter> getFilters(long indicatorId) {
		return this.filterRepository.getFilters(indicatorId);
	}
}
