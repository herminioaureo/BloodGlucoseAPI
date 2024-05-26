package com.bloodglucose.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bloodglucose.api.validator.GlucoseValidator;

import dto.GlucoseRecord;
import service.GlucoseService;

@RestController
@RequestMapping("glucoseapi")
public class Glucose {
	
	@Autowired
	private GlucoseService service;
	
	@Autowired
	private GlucoseValidator validator;
	
	@PostMapping(path = "glucose", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity save(@RequestBody GlucoseRecord glucose) {

		 
		 
		return null;
		 
	 }
	

}
