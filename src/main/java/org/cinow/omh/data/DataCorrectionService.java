package org.cinow.omh.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataCorrectionService {
	
	@Autowired
	private DataCorrectionRepository dataCorrectionRepository;

	public List<DataCorrection> findDisplayed() {
		return dataCorrectionRepository.findDisplayed();
	}

	public List<DataCorrection> findAll() {
		return dataCorrectionRepository.findAll();
	}

	public void add(DataCorrection dataCorrection) {
		dataCorrectionRepository.add(dataCorrection);
	}

	public void update(DataCorrection dataCorrection) {
		dataCorrectionRepository.update(dataCorrection);
	}
}
