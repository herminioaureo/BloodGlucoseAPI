package com.bloodglucose.api.service;

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
	
}
