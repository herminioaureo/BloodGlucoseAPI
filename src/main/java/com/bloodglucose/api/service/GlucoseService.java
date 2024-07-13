package com.bloodglucose.api.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bloodglucose.api.dto.GlucoseRecord;
import com.bloodglucose.api.entity.GlucoseEntity;
import com.bloodglucose.api.repository.GlucoseRepository;
import com.bloodglucose.api.util.GlucoseConverter;

@Service
public class GlucoseService {

	@Autowired
	private GlucoseConverter converter;
	
	@Autowired
	private GlucoseRepository repository;
	
	public void saveGlucose(GlucoseRecord glucose) throws Exception {
		GlucoseEntity entity = converter.convertToEntity(glucose);
		entity.setUuid(UUID.randomUUID().toString());
		
		repository.save(entity);
	}

	public List<GlucoseRecord> getGlucoseList() {
		List<GlucoseRecord> recordList = converter.convertToRecord(repository.findAll());

		return recordList;
	}

	public List<GlucoseRecord> getGlucoseListByMeal(String meal) {
		List<GlucoseRecord> recordList = converter.convertToRecord(repository.findByMeal(meal));

		return recordList;
	}

	public List<GlucoseRecord> getGlucoseListByDate(Date date) {
		List<GlucoseRecord> recordList = converter.convertToRecord(repository.findByDate(date));

		return recordList;
	}

	public List<GlucoseRecord> getGlucoseListByDateBetwee(Date startDate, Date endDate) {
		List<GlucoseRecord> recordList = converter.convertToRecord(repository.findByDateBetween(startDate, endDate));

		return recordList;
	}
	
}
